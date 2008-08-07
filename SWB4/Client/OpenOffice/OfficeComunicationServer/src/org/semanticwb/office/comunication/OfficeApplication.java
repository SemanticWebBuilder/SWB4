/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.office.comunication;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.Session;
import javax.jcr.query.Query;
import javax.jcr.query.QueryResult;
import javax.jcr.query.RowIterator;
import org.semanticwb.office.interfaces.IOfficeApplication;
import org.semanticwb.xmlrpc.XmlRpcObject;

/**
 *
 * @author victor.lorenzana
 */
public class OfficeApplication extends XmlRpcObject implements RepositorySupport,IOfficeApplication
{
    private Session session;
    public boolean isValidVersion(double version)
    {
        return IOfficeApplication.version>=version;
    } 
    public void createPage(String title,String id,String description) throws Exception
    {
        
    }
    public void changePassword(String newPassword) throws Exception
    {
        
    }
    public void setSession(Session session)
    {
        if ( session == null )
        {
            throw new IllegalArgumentException("The session can be null");
        }
        this.session = session;
    }
    public boolean existsPage(String id) throws Exception
    {
        return false;
    }
    
    public void logout()
    {
        session.logout();
    }
    public String createCategory(String title,String description) throws Exception
    {
        String UUID="";        
        try
        {
            Query query=session.getWorkspace().getQueryManager().createQuery("//swb:categoryType[@cm:title='"+ title +"']", Query.XPATH);
            QueryResult result=query.execute();
            NodeIterator nodeIterator=result.getNodes();
            if(!nodeIterator.hasNext())
            {
                Node root= session.getRootNode();
                Node newNode = root.addNode("swb:categoryType", "swb:categoryType");
                newNode.setProperty("cm:title", title);            
                newNode.setProperty("cm:description", description);            
                root.save();
                UUID=newNode.getUUID();
            }
            else
            {
                UUID=nodeIterator.nextNode().getUUID();
            }
        }
        catch ( Exception e )
        {
            e.printStackTrace(System.out);
            throw e;
        }  
        return UUID;
    }
}
