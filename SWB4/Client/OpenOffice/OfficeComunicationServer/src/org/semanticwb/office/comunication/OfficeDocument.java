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
import javax.jcr.Session;
import javax.jcr.lock.LockException;
import javax.jcr.version.Version;
import javax.jcr.version.VersionIterator;
import org.semanticwb.office.interfaces.IOfficeDocument;
import org.semanticwb.xmlrpc.Part;
import org.semanticwb.xmlrpc.XmlRpcObject;
import sun.net.www.MimeTable;

/**
 *
 * @author victor.lorenzana
 */
public class OfficeDocument extends XmlRpcObject implements IOfficeDocument, RepositorySupport
{

    Session session;

    public void setSession(Session session)
    {
        if ( session == null )
        {
            throw new IllegalArgumentException("The session can be null");
        }
        this.session = session;
    }

    public void logout()
    {
        session.logout();
    }

    public String publish(String title, String description, String categoryID, String type) throws Exception
    {
        try
        {
            Node node = session.getNodeByUUID(categoryID);
            if ( !node.isLocked() )
            {
                node.lock(false, true); // blocks the node
            }
            try
            {
                Node newNode = node.addNode("swb:contentType", "swb:contentType");
                newNode.checkout();
                newNode.setProperty("cm:title", title);
                newNode.setProperty("cm:type", type);
                newNode.setProperty("cm:description", description);
                Node nodeContents=newNode.addNode("Contents","nt:folder");
                nodeContents.addMixin("mix:versionable");
                nodeContents.addMixin("mix:lockable");
                nodeContents.addMixin("mix:referenceable");
                nodeContents.checkout();
                for ( Part part : parts )
                {
                    MimeTable mt = MimeTable.getDefaultTable();
                    String mimeType = mt.getContentTypeFor(part.getName());
                    if ( mimeType == null )
                    {
                        mimeType = "application/octet-stream";
                    }

                    Node nodePart = nodeContents.addNode(part.getName(), "nt:file");
                    Node resNode = nodePart.addNode("jcr:content", "nt:resource");
                    resNode.setProperty("jcr:mimeType", mimeType);
                    resNode.setProperty("jcr:encoding", "");
                    InputStream in = new ByteArrayInputStream(part.getContent());
                    resNode.setProperty("jcr:data", in);
                    in.close();
                    Calendar lastModified = Calendar.getInstance();
                    lastModified.setTimeInMillis(System.currentTimeMillis());
                    resNode.setProperty("jcr:lastModified", lastModified);                                   
                }
                session.save();
                nodeContents.checkin();                
                newNode.checkin();
                return newNode.getUUID();
            }
            catch ( ItemExistsException e )
            {
                throw new Exception("Ya existe un contenido con ese título", e);
            }
            catch ( Exception e )
            {
                e.printStackTrace(System.out);
                throw e;
            }
            finally
            {
                node.unlock();
            }

        }
        catch ( ItemNotFoundException infe )
        {
            throw new Exception("La categoria indica no existe", infe);
        }
        catch ( LockException e )
        {
            e.printStackTrace(System.out);
            throw e;
        }
        catch ( Exception e )
        {
            e.printStackTrace(System.out);
            throw e;
        }
    }

    public String updateContent(String contentId) throws Exception
    {

        try
        {
            Node node = session.getNodeByUUID(contentId);            
            if ( !node.isLocked() )
            {
                node.lock(false, true); // blocks the node
            }
            else
            {
                throw new Exception("El contenido esta bloqueado");
            }
            if ( !node.isCheckedOut() )
            {
                node.checkout();
                Node nodeContents=node.getNode("Contents");
                nodeContents.checkout();
                try
                {
                    for ( Part part : parts )
                    {
                        MimeTable mt = MimeTable.getDefaultTable();
                        String mimeType = mt.getContentTypeFor(part.getName());
                        if ( mimeType == null )
                        {
                            mimeType = "application/octet-stream";
                        }                                                 
                        Node nodePart = nodeContents.getNode(part.getName());
                        Node resNode = nodePart.getNode("jcr:content");
                        
                        resNode.setProperty("jcr:mimeType", mimeType);
                        resNode.setProperty("jcr:encoding", "");                        
                        InputStream in = new ByteArrayInputStream(part.getContent());                        
                        resNode.getProperty("jcr:data").setValue(in);                        
                        Calendar lastModified = Calendar.getInstance();
                        lastModified.setTimeInMillis(System.currentTimeMillis());
                        resNode.setProperty("jcr:lastModified", lastModified);                       
                    }
                    session.save();
                }
                catch ( Exception e )
                {
                    e.printStackTrace(System.out);
                    throw e;
                }
                finally
                {                    
                    Version version = node.checkin();
                    
                    System.out.println("exportSystemView");
                    session.exportSystemView(node.getPath(), System.out, true, false);
                    System.out.println("");                    
                    session.save();                    
                    //node.getVersionHistory().addVersionLabel(version.getName(), label, true);
                    return version.getName();
                    /*System.out.println(version.getName());
                    VersionIterator it=node.getVersionHistory().getAllVersions();
                    while(it.hasNext())
                    {
                        Version oldversion=it.nextVersion();
                        System.out.println(oldversion.getName());
                    }*/
                }
            }
            else
            {
                throw new Exception("El contenido esta siendo editado, intente más tarde");
            }
        }
        catch ( ItemNotFoundException infe )
        {
            throw new Exception("El contenido no se encuentró en el repositorio.", infe);
        }

    }

    public String getPath(String contentID)
    {
        return "/";
    }

    public boolean exists(String contentId) throws Exception
    {
        try
        {
            session.getNodeByUUID(contentId);
            return true;
        }
        catch ( ItemNotFoundException pnfe )
        {
            return false;
        }
    }

    public void setTitle(String contentID, String title)
    {

    }

    public void setDescription(String contentID, String description)
    {

    }

    public void setPath(String contentID, String path)
    {

    }

    public void sendToAuthorize()
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

