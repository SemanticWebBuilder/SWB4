/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.blogger;

import java.io.FileReader;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.jcr.Repository;
import javax.jcr.Session;
import javax.jcr.SimpleCredentials;
import javax.jcr.Workspace;
import org.apache.jackrabbit.core.nodetype.InvalidNodeTypeDefException;
import org.apache.jackrabbit.core.nodetype.NodeTypeDef;
import org.apache.jackrabbit.core.nodetype.NodeTypeManagerImpl;
import org.apache.jackrabbit.core.nodetype.NodeTypeRegistry;
import org.apache.jackrabbit.core.nodetype.compact.CompactNodeTypeDefReader;
import org.semantic.blogger.interfaces.MetaWeblog;
import org.semantic.blogger.interfaces.Post;
import org.semantic.blogger.interfaces.RepositorySupport;
import org.semantic.blogger.interfaces.UserBlog;

/**
 *
 * @author victor.lorenzana
 */
public class MetaWeblogImp implements MetaWeblog,RepositorySupport
{
    private static final String BLOG_MODEL_PATH = "C:\\repositorio\\blogmodel.cnd";
    public static final String BLOG_URI = "http://www.semanticwb.org.mx/model/blog/1.0";
    public static final String BLOG_MODEL_PREFIX = "blognode";
    public static final String BLOG_MODEL_URI = "http://www.semanticwb.org.mx/blog/1.0/";
    public static final String BLOG_PREFIX = "blog";
    private static Map<String, Repository> repositories;
    public static Session openSession(String user,String password) throws Exception
    {
        if ( repositories.size() > 0 )
        {
            Set<String> keys = repositories.keySet();
            Session session = repositories.get(keys.iterator().next()).login(new SimpleCredentials(user, password.toCharArray()));            
            RegisterCustomNodeTypes(session.getWorkspace(), BLOG_MODEL_PATH);
            return session;
        }
        throw new Exception("There are not repositories");
    }
    public static void RegisterCustomNodeTypes(Workspace ws, String cndFileName) throws Exception
    {

        boolean exists = false;
        for ( String uri : ws.getNamespaceRegistry().getURIs() )
        {
            if ( uri.equals(BLOG_URI) )
            {
                exists = true;
                break;
            }
        }
        if ( !exists )
        {
            try
            {
                ws.getNamespaceRegistry().registerNamespace(BLOG_MODEL_PREFIX, BLOG_URI);
            }
            catch ( Exception e )
            {
                e.printStackTrace(System.out);
            }
        }
        exists = false;
        for ( String uri : ws.getNamespaceRegistry().getURIs() )
        {
            if ( uri.equals(BLOG_MODEL_URI) )
            {
                exists = true;
                break;
            }
        }
        if ( !exists )
        {
            try
            {
                ws.getNamespaceRegistry().registerNamespace(BLOG_PREFIX, BLOG_MODEL_URI);
            }
            catch ( Exception e )
            {
                e.printStackTrace(System.out);
            }
        }


        // Read in the CND file
        FileReader fileReader = new FileReader(cndFileName);

        // Create a CompactNodeTypeDefReader
        CompactNodeTypeDefReader cndReader = new CompactNodeTypeDefReader(fileReader, cndFileName);

        // Get the List of NodeTypeDef objects
        List ntdList = cndReader.getNodeTypeDefs();

        // Get the NodeTypeManager from the Workspace.
        // Note that it must be cast from the generic JCR NodeTypeManager to the
        // Jackrabbit-specific implementation.        
        NodeTypeManagerImpl ntmgr = ( NodeTypeManagerImpl ) ws.getNodeTypeManager();

        // Acquire the NodeTypeRegistry
        NodeTypeRegistry ntreg = ntmgr.getNodeTypeRegistry();

        // Loop through the prepared NodeTypeDefs
        for ( Iterator i = ntdList.iterator(); i.hasNext();)
        {
            // Get the NodeTypeDef...
            NodeTypeDef ntd = ( NodeTypeDef ) i.next();
            try
            {
                if ( !ntreg.isRegistered(ntd.getName()) )
                {
                    ntreg.registerNodeType(ntd);
                }
            }
            catch ( InvalidNodeTypeDefException e )
            {
                System.out.println(e.getLocalizedMessage());
            }
        }
    }
    public void setRepositories(Map<String, Repository> repositories)
    {
        if ( MetaWeblogImp.repositories != null )
        {
            throw new IllegalArgumentException("The repository list already exists");
        }
        MetaWeblogImp.repositories = repositories;
    }
    public boolean hasListOfRepositories()
    {
        boolean hasListOfRepositories=false;
        if(repositories!=null)
        {
            hasListOfRepositories=true;
        }
        return hasListOfRepositories;
    }

    public UserBlog[] getUsersBlogs(String appkey, String username, String password)
    {
        HashSet<UserBlog> blogs = new HashSet<UserBlog>();
        UserBlog blog = new UserBlog();
        blog.blogName = "a";
        blog.blogid = "200";
        blog.isAdmin = false;
        blog.url = "http://localhost:8084/TestRPC/Blogger";
        blogs.add(blog);
        return blogs.toArray(new UserBlog[blogs.size()]);
    }

    public Post getRecentPosts(String blogid, String username, String password, int numberOfPosts) throws Exception
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Edit a post sent to the server
     * @param postid The post to be updated
     * @param userid The user ID that is updating the post
     * @param password The password of the user
     * @param post The new version of the post
     * @param publish If the post can be published
     * @return True if the pos was updated
     * @throws java.lang.Exception
     */
    public boolean editPost(String postid, String userid, String password, Post post, boolean publish) throws Exception
    {
        return true;
    }

    /**
     * Gets a post in the repository
     * @param postid ID of the post
     * @param userid User ID
     * @param password Password of the user
     * @return Th
     * @throws java.lang.Exception
     */
    public Post getPost(String postid, String userid, String password) throws Exception
    {
        Post value = new Post();
        value.categories = new String[]{"Michegas", "Mind Bombs", "Rest & Relaxation"};
        value.dateCreated = new Date();
        value.description = "Blogger Ed Cone of Greensboro talks&nbsp; about the several intersections he overlooks. That is: junctions of the public and the personal (which every blogger faces) and more particularly the contrasting voices of a newspaper columnist and a blogger (he is both) and the opportunities for a local conversation in a global medium.";
        value.postid = "1829";
        value.title = "title";
        value.userid = "1015";
        return value;
    }

    public String newPost(String blogid, String userid, String password, Post post, boolean publish) throws Exception
    {
        return "200000"; // return the postId        
    }

    public Post getCategories(String blogid, String username, String password) throws Exception
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
