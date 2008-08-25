/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.blogger;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.Repository;
import javax.jcr.Session;
import javax.jcr.SimpleCredentials;
import javax.jcr.Value;
import javax.jcr.Workspace;
import javax.jcr.query.Query;
import javax.jcr.query.QueryResult;
import org.apache.jackrabbit.core.nodetype.InvalidNodeTypeDefException;
import org.apache.jackrabbit.core.nodetype.NodeTypeDef;
import org.apache.jackrabbit.core.nodetype.NodeTypeManagerImpl;
import org.apache.jackrabbit.core.nodetype.NodeTypeRegistry;
import org.apache.jackrabbit.core.nodetype.compact.CompactNodeTypeDefReader;
import org.semantic.blogger.interfaces.CategoryInfo;
import org.semantic.blogger.interfaces.MetaWeblog;
import org.semantic.blogger.interfaces.Post;
import org.semantic.blogger.interfaces.RepositorySupport;
import org.semantic.blogger.interfaces.UserBlog;

/**
 *
 * @author victor.lorenzana
 */
public class MetaWeblogImp implements MetaWeblog, RepositorySupport
{

    private static final String BLOG_MODEL_PATH = "C:\\repositorio\\blogmodel.cnd";
    public static final String BLOG_MODEL_URI = "http://www.semanticwb.org.mx/model/blog/1.0";
    public static final String BLOG_MODEL_PREFIX = "blognode";
    public static final String BLOG_URI = "http://www.semanticwb.org.mx/blog/1.0/";
    public static final String BLOG_PREFIX = "blog";
    private static Map<String, Repository> repositories;

    public static Session openSession(String user, String password) throws Exception
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
                ws.getNamespaceRegistry().registerNamespace(BLOG_MODEL_PREFIX, BLOG_MODEL_URI);
            }
            catch ( Exception e )
            {
                e.printStackTrace(System.out);
            }
        }
        exists = false;
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
                ws.getNamespaceRegistry().registerNamespace(BLOG_PREFIX, BLOG_URI);
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
        boolean hasListOfRepositories = false;
        if ( repositories != null )
        {
            hasListOfRepositories = true;
        }
        return hasListOfRepositories;
    }
    
    /**
     * Create a blog into the repository
     * @param name Name of the blog
     * @param url Url of the blog
     * @param username User name to create the blog into de repository
     * @param password Password for the user to create the blog into the repository
     * @return The UUID created
     * @throws java.lang.Exception
     */
    public String createBlog(String name, String url, String username, String password) throws Exception
    {
        Session session = null;
        try
        {
            session = openSession(username, password);
            Query query = session.getWorkspace().getQueryManager().createQuery("//blog[@blog:name='" + name + "']", Query.XPATH);
            QueryResult result = query.execute();
            NodeIterator nodeIterator = result.getNodes();
            if ( nodeIterator.hasNext() )
            {
                return nodeIterator.nextNode().getUUID();
            }
            else
            {
                Node root = session.getRootNode();
                Node blogNode = root.addNode("blog", "blognode:blogType");
                blogNode.setProperty("blog:name", name);
                blogNode.setProperty("blog:url", url);
                session.save();
                return blogNode.getUUID();
            }
        }
        catch ( Exception ex )
        {
            throw ex;
        }
        finally
        {
            if ( session != null )
            {
                session.logout();
            }
        }

    }

    /**
     * 
     * Returns tthe blogs that an user can publish posts
     * @param appkey Is not used
     * @param username User
     * @param password //Password
     * @return An array of blogs that the user can publish
     * @see UserBlog
     */
    public UserBlog[] getUsersBlogs(String appkey, String username, String password) throws Exception
    {
        createBlog("Blog victor", "http://www.infotec.com.mx/victor", username, password);
        HashSet<UserBlog> blogs = new HashSet<UserBlog>();
        Session session = null;
        try
        {
            session = openSession(username, password);
            Query query = session.getWorkspace().getQueryManager().createQuery("//blog", Query.XPATH);
            QueryResult result = query.execute();
            NodeIterator nodeIterator = result.getNodes();
            while (nodeIterator.hasNext())
            {
                UserBlog userblog = new UserBlog();
                Node nodeBlog = nodeIterator.nextNode();
                userblog.blogName = nodeBlog.getProperty("blog:name").getString();
                userblog.blogid = nodeBlog.getUUID();
                userblog.isAdmin = true;
                userblog.url = "http://localhost:8084/TestRPC/Blogger";
                blogs.add(userblog);
            }
        }
        catch ( Exception ex )
        {
            throw ex;
        }
        finally
        {
            if ( session != null )
            {
                session.logout();
            }
        }
        return blogs.toArray(new UserBlog[blogs.size()]);
    }

    /**
     * Retrieves a list of posts that were created recently. The results are returned in descending chronolocial order with the most recent post first in the list.
     * @param blogid the Blog ID
     * @param username The user name
     * @param password The password
     * @param numberOfPosts Number of máx posts to retrive
     * @return The list of recently posts
     * @throws java.lang.Exception
     */
    public Post[] getRecentPosts(String blogid, String username, String password, int numberOfPosts) throws Exception
    {        
        ArrayList<Post> posts = new ArrayList<Post>();
        Session session = null;
        try
        {
            session = openSession(username, password);
            Node blogNode = session.getNodeByUUID(blogid);
            NodeIterator postNodes = blogNode.getNodes("post");
            int numPost = 1;
            while (postNodes.hasNext())
            {
                if ( numPost < numberOfPosts )
                {
                    Node postNode = postNodes.nextNode();
                    Post value = new Post();
                    ArrayList<String> categories = new ArrayList<String>();
                    for ( Value categoryValue : postNode.getProperty("blog:category").getValues() )
                    {
                        categories.add(categoryValue.getString());
                    }
                    value.categories = categories.toArray(new String[categories.size()]);
                    value.dateCreated = postNode.getProperty("blog:dateCreated").getDate().getTime();
                    value.description = postNode.getProperty("blog:title").getString();
                    value.postid = postNode.getUUID();
                    value.title = postNode.getProperty("blog:description").getString();
                    value.userid = postNode.getProperty("blog:userid").getString();
                    posts.add(value);
                    numPost++;
                }
                else
                {
                    break;
                }

            }
        }
        catch ( Exception ex )
        {
            throw ex;
        }
        finally
        {
            if ( session != null )
            {
                session.logout();
            }
        }
        Post[] postsToReturn=posts.toArray(new Post[posts.size()]);
        Arrays.sort(postsToReturn);
        if(postsToReturn.length>numberOfPosts)
        {
            Post[] temp=new Post[numberOfPosts];
            System.arraycopy(postsToReturn, 0, temp, 0, numberOfPosts);
            postsToReturn=temp;            
        }
        return postsToReturn;
    }

    private String getHtmlUrlForCategories(String blogid)
    {
        return "Http";
    }

    private String getRssUrlForCategories(String blogid)
    {
        return "Http";
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
        Session session = null;
        try
        {
            session = openSession(userid, password);
            Node postNode = session.getNodeByUUID(postid);
            postNode.checkout();
            postNode.setProperty("blog:title", post.title);
            if ( post.userid != null )
            {
                postNode.setProperty("blog:userid", post.userid);
            }
            else
            {
                postNode.setProperty("blog:userid", userid);
            }
            postNode.setProperty("blog:description", post.description);
            if ( post.categories != null )
            {
                postNode.setProperty("blog:category", post.categories);
                for ( String category : post.categories )
                {
                    createCategory(postNode.getParent().getUUID(), category, category, userid, password);
                }
            }
            session.save();
            postNode.checkin();
            return true;
        }
        catch ( Exception ex )
        {
            throw ex;
        }
        finally
        {
            if ( session != null )
            {
                session.logout();
            }
        }
    }

    /**
     * Gets a post in the repository
     * @param postid ID of the post
     * @param userid User ID to access to the repository
     * @param password Password of the user
     * @return Th
     * @throws java.lang.Exception
     */
    public Post getPost(String postid, String userid, String password) throws Exception
    {
        Session session = null;
        try
        {
            session = openSession(userid, password);
            Node postNode = session.getNodeByUUID(postid);
            Post value = new Post();
            ArrayList<String> categories = new ArrayList<String>();
            for ( Value categoryValue : postNode.getProperty("").getValues() )
            {
                categories.add(categoryValue.getString());
            }
            value.categories = categories.toArray(new String[categories.size()]);
            value.dateCreated = postNode.getProperty("blog:dateCreated").getDate().getTime();
            value.description = postNode.getProperty("blog:title").getString();
            value.postid = postid;
            value.title = postNode.getProperty("blog:description").getString();
            value.userid = postNode.getProperty("blog:userid").getString();
            return value;
        }
        catch ( Exception ex )
        {
            throw ex;
        }
        finally
        {
            if ( session != null )
            {
                session.logout();
            }
        }
    }

    /**
     * Create a new post into a blog
     * @param blogid The blog ID
     * @param userid The userID
     * @param password The password
     * @param post The post information
     * @param publish If the post must be published
     * @return The Post ID
     * @throws java.lang.Exception
     * @see Post
     */
    public String newPost(String blogid, String userid, String password, Post post, boolean publish) throws Exception
    {
        Session session = null;
        try
        {
            session = openSession(userid, password);
            Node blogNode = session.getNodeByUUID(blogid);
            Node postNode = blogNode.addNode("post", "blognode:blogEntry");
            postNode.setProperty("blog:title", post.title);
            if ( post.userid != null )
            {
                postNode.setProperty("blog:userid", post.userid);
            }
            else
            {
                postNode.setProperty("blog:userid", userid);
            }
            postNode.setProperty("blog:description", post.description);
            postNode.setProperty("blog:dateCreated", Calendar.getInstance());
            if ( post.categories != null )
            {
                postNode.setProperty("blog:category", post.categories);
                for ( String category : post.categories )
                {
                    createCategory(blogid, category, category, userid, password);
                }
            }

            session.save();
            return postNode.getUUID();
        }
        catch ( Exception ex )
        {
            throw ex;
        }
        finally
        {
            if ( session != null )
            {
                session.logout();
            }
        }
    }
    /**
     * Create a Category into the blog repository
     * @param blogid The blog Id where the category is going to be created
     * @param name Name of the categoey
     * @param description The description of the category
     * @param username The UserName to access to the repository
     * @param password The Password to access to the repository
     * @return A CategoryInfo with the information created
     * @throws java.lang.Exception
     */
    public CategoryInfo createCategory(String blogid, String name, String description, String username, String password) throws Exception
    {
        Session session = null;
        try
        {
            session = openSession(username, password);
            Query query = session.getWorkspace().getQueryManager().createQuery("//blog/category[blog:name='" + name + "']", Query.XPATH);
            QueryResult result = query.execute();
            NodeIterator nodeIterator = result.getNodes();
            CategoryInfo category = new CategoryInfo();
            Node categoryNode = null;
            if ( nodeIterator.hasNext() )
            {
                categoryNode = nodeIterator.nextNode();
            }
            else
            {
                Node nodeBlog = session.getNodeByUUID(blogid);
                categoryNode = nodeBlog.addNode("category", "blognode:categoryType");
                categoryNode.setProperty("blog:name", name);
                categoryNode.setProperty("blog:description", description);
                categoryNode.setProperty("blog:htmlUrl", getHtmlUrlForCategories(blogid));
                categoryNode.setProperty("blog:rssUrl", getRssUrlForCategories(blogid));
                session.save();
            }
            category.categoryId = categoryNode.getUUID();
            category.categoryName = categoryNode.getProperty("blog:name").getString();
            category.description = categoryNode.getProperty("blog:description").getString();
            category.htmlUrl = categoryNode.getProperty("blog:htmlUrl").getString();
            category.rssUrl = categoryNode.getProperty("blog:rssUrl").getString();
            return category;
        }
        catch ( Exception ex )
        {
            throw ex;
        }
        finally
        {
            if ( session != null )
            {
                session.logout();
            }
        }
    }
    /**
     * Gets the categories created into the blog repository
     * @param blogid The BlogID of the repository
     * @param username The userName to access to repository
     * @param password The password of the user to access to the repository
     * @return An array of CategoryInfo, with the information about of the Categories
     * @throws java.lang.Exception
     */
    public CategoryInfo[] getCategories(String blogid, String username, String password) throws Exception
    {
        createCategory(blogid, "Categoria 1", "description", username, password);
        ArrayList<CategoryInfo> categories = new ArrayList<CategoryInfo>();
        Session session = null;
        try
        {
            session = openSession(username, password);
            Query query = session.getWorkspace().getQueryManager().createQuery("//blog/category", Query.XPATH);
            QueryResult result = query.execute();
            NodeIterator nodeIterator = result.getNodes();
            while (nodeIterator.hasNext())
            {
                Node categoryNode = nodeIterator.nextNode();
                CategoryInfo category = new CategoryInfo();
                category.categoryId = categoryNode.getUUID();
                category.categoryName = categoryNode.getProperty("blog:name").getString();
                category.description = categoryNode.getProperty("blog:description").getString();
                category.htmlUrl = categoryNode.getProperty("blog:htmlUrl").getString();
                category.rssUrl = categoryNode.getProperty("blog:rssUrl").getString();
                categories.add(category);
            }

        }
        catch ( Exception ex )
        {
            throw ex;
        }
        finally
        {
            if ( session != null )
            {
                session.logout();
            }
        }
        return categories.toArray(new CategoryInfo[categories.size()]);
    }
}
