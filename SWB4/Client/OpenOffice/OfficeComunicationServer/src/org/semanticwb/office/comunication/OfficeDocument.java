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

    private static final String CM_CONTENT = "cm:Content";
    private static final String CM_DESCRIPTION = "cm:description";
    private static final String CM_TITLE = "cm:title";
    private static final String CM_TYPE = "cm:type";
    private static final String CONTENT_NAME = "Content";
    private static final String DEFAULT_MIME_TYPE = "application/octet-stream";    
    private static final String CM_PART = "cm:Part";
    private static final String JCR_CONTENT = "jcr:content";
    private static final String NT_RESOURCE = "nt:resource";
    private static final RepositoryManagerLoader loader=RepositoryManagerLoader.getInstance();
    public String publish(String title, String description, String repositoryName, String categoryID, String type) throws Exception
    {
        Session session = null;
        try
        {
            session = loader.openSession(repositoryName, "", "");
            Node categoryNode = session.getNodeByUUID(categoryID);
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
                Node contentNode = categoryNode.addNode( CONTENT_NAME,CM_CONTENT);
                contentNode.setProperty(CM_TITLE, title);
                contentNode.setProperty(CM_TYPE, type);
                contentNode.setProperty(CM_DESCRIPTION, description);
                String[] values = new String[parts.size()];
                int iPart = 0;
                for (Part part : parts)
                {
                    String mimeType = config.getServletContext().getMimeType(part.getName());
                    if (mimeType == null)
                    {
                        mimeType = DEFAULT_MIME_TYPE;
                    }

                    Node nodePart = contentNode.addNode(part.getName(), CM_PART);
                    Node resNode = nodePart.addNode( JCR_CONTENT,NT_RESOURCE);
                    resNode.setProperty("jcr:mimeType", mimeType);
                    resNode.setProperty("jcr:encoding", "");
                    InputStream in = new ByteArrayInputStream(part.getContent());
                    resNode.setProperty("jcr:data", in);
                    in.close();
                    Calendar lastModified = Calendar.getInstance();
                    lastModified.setTimeInMillis(System.currentTimeMillis());
                    resNode.setProperty("jcr:lastModified", lastModified);
                    categoryNode.save();
                    Version versionPart = nodePart.checkin();
                    values[iPart] = versionPart.getUUID();
                    iPart++;
                }
                session.save();
                contentNode.checkin();
                return contentNode.getUUID();
            }
            catch (ItemExistsException e)
            {
                throw new Exception("Ya existe un contenido con ese título", e);
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
                    String[] values = new String[parts.size()];
                    int iPart = 0;
                    for (Part part : parts)
                    {   
                        String mimeType =this.config.getServletContext().getMimeType(part.getName());
                        if (mimeType == null)
                        {
                            mimeType = DEFAULT_MIME_TYPE;
                        }
                        Node nodePart = null;
                        nodePart = nodeContent.getNode(part.getName());
                        nodePart.checkout();
                        Node resNode = nodePart.getNode("jcr:content");
                        resNode.setProperty("jcr:mimeType", mimeType);
                        resNode.setProperty("jcr:encoding", "");
                        InputStream in = new ByteArrayInputStream(part.getContent());
                        resNode.getProperty("jcr:data").setValue(in);
                        Calendar lastModified = Calendar.getInstance();
                        lastModified.setTimeInMillis(System.currentTimeMillis());
                        resNode.setProperty("jcr:lastModified", lastModified);
                        session.save();
                        Version versionPart = nodePart.checkin();
                        values[iPart] = versionPart.getUUID();
                        iPart++;


                    }
                    nodeContent.setProperty("cm:part", values);
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
                    System.out.println("Version: " + version.getName());
                    PropertyIterator propIt = nodeContent.getProperties("cm:part");
                    while (propIt.hasNext())
                    {
                        Property property = propIt.nextProperty();
                        for (Value value : property.getValues())
                        {
                            String UUDI = value.getString();
                            Node versionPart = session.getNodeByUUID(UUDI);
                            if (versionPart instanceof Version)
                            {
                                //System.out.println("Part UUID: " + UUDI);
                                System.out.println("name of version: " + versionPart.getName());
                                Node node = getNodeFromVersion((Version) versionPart);
                                System.out.println("node.getName(): " + node.getName());
                            }
                        }
                    }
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

