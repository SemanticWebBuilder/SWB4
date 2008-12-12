/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.office.comunication;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Calendar;
import javax.jcr.ItemExistsException;
import javax.jcr.ItemNotFoundException;
import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.Property;
import javax.jcr.PropertyIterator;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.Value;
import javax.jcr.lock.LockException;
import javax.jcr.version.Version;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.office.interfaces.IOfficeDocument;
import org.semanticwb.repository.RepositoryManagerLoader;
import org.semanticwb.xmlrpc.Part;
import org.semanticwb.xmlrpc.XmlRpcObject;

/**
 *
 * @author victor.lorenzana
 */
public class OfficeDocument extends XmlRpcObject implements IOfficeDocument
{

    private static Logger log = SWBUtils.getLogger(OfficeDocument.class);
    private static final String DEFAULT_MIME_TYPE = "application/octet-stream";
    private static final RepositoryManagerLoader loader = RepositoryManagerLoader.getInstance();

    public String publish(String title, String description, String repositoryName, String categoryID, String type, String nodeType) throws Exception
    {
        Session session = null;
        Node categoryNode = null;
        try
        {
            session = loader.openSession(repositoryName, "", "");
            categoryNode = session.getNodeByUUID(categoryID);
            if (!categoryNode.isLocked())
            {
                categoryNode.lock(false, false); // blocks the nodeContent
            }
            else
            {
                throw new Exception("The node is locked");
            }
            try
            {
                String cm_title = loader.getOfficeManager(repositoryName).getPropertyTitleType();
                String cm_description = loader.getOfficeManager(repositoryName).getPropertyDescriptionType();
                Node contentNode = categoryNode.addNode(nodeType, nodeType);
                contentNode.setProperty(cm_title, title);
                String cm_type = loader.getOfficeManager(repositoryName).getPropertyType();
                contentNode.setProperty(cm_type, type);
                contentNode.setProperty(cm_description, description);                                
                for (Part part : parts)
                {
                    String mimeType = DEFAULT_MIME_TYPE;
                    if (config!=null && config.getServletContext() != null)
                    {
                        mimeType = config.getServletContext().getMimeType(part.getName());
                        if (mimeType == null)
                        {
                            mimeType = DEFAULT_MIME_TYPE;
                        }
                    }                    
                    Node resNode = contentNode.addNode("jcr:content", "nt:resource");
                    resNode.setProperty("jcr:mimeType", mimeType);
                    resNode.setProperty("jcr:encoding", "");
                    InputStream in = new ByteArrayInputStream(part.getContent());
                    resNode.setProperty("jcr:data", in);
                    in.close();
                    Calendar lastModified = Calendar.getInstance();
                    lastModified.setTimeInMillis(System.currentTimeMillis());
                    resNode.setProperty("jcr:lastModified", lastModified);
                    categoryNode.save();                                                            
                }                
                Version version=contentNode.checkin();
                log.debug("Version created with number "+version.getName());
                return contentNode.getUUID();
            }
            catch (ItemExistsException e)
            {
                throw new Exception("Ya existe un contenido con ese nombre", e);
            }
            catch (Exception e)
            {
                e.printStackTrace(System.out);
                throw e;
            }
            finally
            {
                categoryNode.unlock();
            }

        }
        catch (ItemNotFoundException infe)
        {
            throw new Exception("La categoria indica no existe", infe);
        }
        catch (LockException e)
        {
            e.printStackTrace(System.out);
            throw e;
        }
        catch (Exception e)
        {
            if (categoryNode != null)
            {
                categoryNode.unlock();
            }
            e.printStackTrace(System.out);
            throw e;
        }
        finally
        {
            if (session != null)
            {
                session.logout();
            }
        }
    }

    private static Node getNodeFromVersion(Version version) throws Exception
    {
        NodeIterator frozenNodes = version.getNodes();
        while (frozenNodes.hasNext())
        {
            Node frozenNode = frozenNodes.nextNode();
            Node contentNode = frozenNode.getNodes().nextNode();
            return contentNode;
        }
        throw new Exception("Node not found from a version");
    }

    /**
     * Update a Content
     * @param contentId ID of the content, the id is a UUID
     * @return The version name created
     * @throws java.lang.Exception
     */
    public String updateContent(String contentId) throws Exception
    {
        Session session = null;
        try
        {
            session = loader.openSession("", "", "");
            Node nodeContent = session.getNodeByUUID(contentId);
            if (!nodeContent.isLocked())
            {
                nodeContent.lock(false, true); // blocks the nodeContent

            }
            else
            {
                throw new Exception("The content is locked");
            }
            if (!nodeContent.isCheckedOut())
            {
                nodeContent.checkout();
                try
                {   
                    for (Part part : parts)
                    {
                        String mimeType = this.config.getServletContext().getMimeType(part.getName());
                        if (mimeType == null)
                        {
                            mimeType = DEFAULT_MIME_TYPE;
                        }
                        
                        
                        Node resNode = nodeContent.getNode("jcr:content");
                        resNode.setProperty("jcr:mimeType", mimeType);
                        resNode.setProperty("jcr:encoding", "");
                        InputStream in = new ByteArrayInputStream(part.getContent());
                        resNode.getProperty("jcr:data").setValue(in);
                        Calendar lastModified = Calendar.getInstance();
                        lastModified.setTimeInMillis(System.currentTimeMillis());
                        resNode.setProperty("jcr:lastModified", lastModified);
                        session.save();                        
                    }                    
                    session.save();
                }
                catch (RepositoryException e)
                {
                    e.printStackTrace(System.out);
                    throw e;
                }
                finally
                {
                    Version version = nodeContent.checkin();
                    log.debug("version created " + version.getName());
                    session.save();
                    return version.getName();
                }
            }
            else
            {
                throw new Exception("El contenido esta siendo editado, intente más tarde");
            }
        }
        catch (ItemNotFoundException infe)
        {
            throw new Exception("El contenido no se encuentró en el repositorio.", infe);
        }
        finally
        {
            if (session != null)
            {
                session.logout();
            }
        }

    }

    public String getPath(String contentID)
    {
        return "/";
    }

    public boolean exists(String contentId) throws Exception
    {
        boolean exists = false;
        Session session = null;
        try
        {
            session = loader.openSession("", "", "");
            session.getNodeByUUID(contentId);
            exists = true;
        }
        catch (ItemNotFoundException pnfe)
        {
            exists = false;
        }
        finally
        {
            if (session != null)
            {
                session.logout();
            }
        }
        return exists;

    }

    public void setTitle(String contentID, String title)
    {
    }

    public void setDescription(String contentID, String description)
    {
    }

    public void sendToAuthorize()
    {
    }

    public void setPagination(String contentId) throws Exception
    {
    }

    public void setActive(String contentID, boolean active)
    {
    }

    public boolean getActive(String contentID)
    {
        return false;
    }

    public void delete(String contentID)
    {
    }
}

