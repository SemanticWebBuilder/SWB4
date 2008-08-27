/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.blogger;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashSet;
import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.Session;
import javax.jcr.Value;
import javax.jcr.query.Query;
import javax.jcr.query.QueryResult;
import org.semantic.blogger.interfaces.CategoryInfo;
import org.semantic.blogger.interfaces.MetaWeblog;
import org.semantic.blogger.interfaces.Post;
import org.semantic.blogger.interfaces.UserBlog;
import org.semanticwb.repository.RepositoryManager;


/**
 *
 * @author victor.lorenzana
 */
public final class MetaWeblogImp implements MetaWeblog
{    
    private static final String BLOG_PREFIX = "blog";    
    private static final String BLOG_MODEL_URI = "http://www.semanticwb.org.mx/model/blog/1.0";
    private static final String BLOG_MODEL_PREFIX = "blognode";
    private static final String BLOG_URI = "http://www.semanticwb.org.mx/blog/1.0/";
    private static final String BLOG_DESCRIPTION = BLOG_PREFIX + ":description";
    private static final String BLOG_CATEGORY = BLOG_PREFIX + ":category";
    private static final String BLOG_NAME = BLOG_PREFIX + ":name";
    private static final String BLOG_TITLE = BLOG_PREFIX + ":title";
    private static final String BLOG_URL = BLOG_PREFIX+":url";
    private static final String BLOG_USERID = BLOG_PREFIX + ":userid";
    private static final String ID_SEPARATOR = ":";
    static
    {
        try
        {
            RepositoryManager.addNamespace(BLOG_MODEL_PREFIX, new URI(BLOG_MODEL_URI));
        }
        catch(Exception e)
        {
            e.printStackTrace(System.out);
        }
        try
        {
            RepositoryManager.addNamespace(BLOG_PREFIX, new URI(BLOG_URI));
        }
        catch(Exception e)
        {
            e.printStackTrace(System.out);
        }
    }

    
    private static String getID(String blogID) throws Exception
    {
        String[] values=blogID.split(ID_SEPARATOR);
        if(values.length==2)
        {
            return values[1];
        }
        else
        {
            throw new Exception("The format for the blogID or postID is invalid");
        }
    }
    private static String getRepositoryName(String blogID) throws Exception
    {
        String[] values=blogID.split(ID_SEPARATOR);
        if(values.length==2)
        {
            return values[0];
        }
        else
        {
            throw new Exception("The format for the blogID or postID is invalid");
        }
    }
    

    
    public void removePost(String postid,String username,String password)  throws Exception
    {
        Session session = null;
        try
        {            
            String repositoryName=getRepositoryName(postid);
            postid=getID(postid);
            session = RepositoryManager.openSession(repositoryName, username, password);
            Node nodePost=session.getNodeByUUID(postid);           
            nodePost.remove();
            session.save();
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
    public void removeBlog(String blogid,String username,String password) throws Exception
    {
        Session session = null;
        try
        {            
            String repositoryName=getRepositoryName(blogid);
            blogid=getID(blogid);
            session = RepositoryManager.openSession(repositoryName, username, password);
            Node nodeBlog=session.getNodeByUUID(blogid);
            if(nodeBlog.hasNode("post"))
            {
                throw new Exception("The blog can not be removed, because the blog caotains posts");
            }            
            nodeBlog.remove();
            session.save();
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
    public String updateBlog(String repositoryName, String name, String url, String username, String password) throws Exception
    {
        return createBlog(repositoryName,name,url,username,password);
    }
    /**
     * Create a blog into the repository
     * @param repositoryName Name of repository to create the blog
     * @param name Name of the blog
     * @param url Url of the blog
     * @param username User name to create the blog into de repository
     * @param password Password for the user to create the blog into the repository
     * @return The UUID created
     * @throws java.lang.Exception
     */
    public String createBlog(String repositoryName, String name, String url, String username, String password) throws Exception
    {        
        Session session = null;
        if(repositoryName.indexOf(ID_SEPARATOR)!=-1)
        {
            throw new Exception("The name of the repository contains the character '"+ ID_SEPARATOR +"' , and is not posible to use this repository to create a blog");
        }
        try
        {
            session = RepositoryManager.openSession(repositoryName, username, password);
            Query query = session.getWorkspace().getQueryManager().createQuery("//blog[@blog:name='" + name + "']", Query.XPATH);
            QueryResult result = query.execute();
            NodeIterator nodeIterator = result.getNodes();
            Node blogNode=null;
            if ( nodeIterator.hasNext() )
            {
                blogNode=nodeIterator.nextNode();
                blogNode.setProperty(BLOG_NAME, name);
                blogNode.setProperty( BLOG_URL,url);
            }
            else
            {
                Node root = session.getRootNode();
                blogNode = root.addNode("blog", "blognode:blogType");
                blogNode.setProperty(BLOG_NAME, name);
                blogNode.setProperty( BLOG_URL,url);
                session.save();                
            }
            return blogNode.getUUID();
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
        HashSet<UserBlog> blogs = new HashSet<UserBlog>();
        Session session = null;
        for ( String repositoryName : RepositoryManager.getRepositoryNames())
        {
            try
            {                
                session = RepositoryManager.openSession(repositoryName,username, password);
                Query query = session.getWorkspace().getQueryManager().createQuery("//blog", Query.XPATH);
                QueryResult result = query.execute();
                NodeIterator nodeIterator = result.getNodes();
                while (nodeIterator.hasNext())
                {
                    UserBlog userblog = new UserBlog();
                    Node nodeBlog = nodeIterator.nextNode();
                    userblog.blogName = nodeBlog.getProperty(BLOG_NAME).getString();
                    userblog.blogid = repositoryName+ID_SEPARATOR+nodeBlog.getUUID();
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
            String repositoryName=getRepositoryName(blogid);
            blogid=getID(blogid);
            session = RepositoryManager.openSession(repositoryName,username, password);
            Node blogNode = session.getNodeByUUID(blogid);
            NodeIterator postNodes = blogNode.getNodes("post");
            while (postNodes.hasNext())
            {
                Node postNode = postNodes.nextNode();
                Post value = new Post();
                ArrayList<String> categories = new ArrayList<String>();
                if ( postNode.hasProperty(BLOG_CATEGORY) )
                {
                    for ( Value categoryValue : postNode.getProperty(BLOG_CATEGORY).getValues() )
                    {
                        categories.add(categoryValue.getString());
                    }
                }
                value.categories = categories.toArray(new String[categories.size()]);
                value.dateCreated = postNode.getProperty("blog:dateCreated").getDate().getTime();
                value.description = postNode.getProperty(BLOG_DESCRIPTION).getString();
                value.postid = repositoryName+ID_SEPARATOR+postNode.getUUID();
                value.title = postNode.getProperty(BLOG_TITLE).getString();
                value.userid = postNode.getProperty(BLOG_USERID).getString();
                posts.add(value);
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
        Post[] postsToReturn = posts.toArray(new Post[posts.size()]);
        Arrays.sort(postsToReturn);
        if ( postsToReturn.length > numberOfPosts )
        {
            Post[] temp = new Post[numberOfPosts];
            System.arraycopy(postsToReturn, 0, temp, 0, numberOfPosts);
            postsToReturn = temp;
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
            String repositoryName=getRepositoryName(postid);
            postid=getID(postid);
            session = RepositoryManager.openSession(repositoryName,userid, password);
            Node postNode = session.getNodeByUUID(postid);
            postNode.checkout();
            postNode.setProperty(BLOG_TITLE, post.title);
            if ( post.userid != null )
            {
                postNode.setProperty(BLOG_USERID, post.userid);
            }
            else
            {
                postNode.setProperty(BLOG_USERID, userid);
            }
            postNode.setProperty(BLOG_DESCRIPTION, post.description);
            if ( post.categories != null )
            {
                postNode.setProperty(BLOG_CATEGORY, post.categories);
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
            String repositoryName=getRepositoryName(userid);
            postid=getID(postid);
            session = RepositoryManager.openSession(repositoryName,userid, password);
            Node postNode = session.getNodeByUUID(postid);
            Post value = new Post();
            ArrayList<String> categories = new ArrayList<String>();
            if ( postNode.hasProperty(BLOG_CATEGORY) )
            {
                for ( Value categoryValue : postNode.getProperty(BLOG_CATEGORY).getValues() )
                {
                    categories.add(categoryValue.getString());
                }
            }
            value.categories = categories.toArray(new String[categories.size()]);
            value.dateCreated = postNode.getProperty("blog:dateCreated").getDate().getTime();
            value.description = postNode.getProperty(BLOG_DESCRIPTION).getString();
            value.postid = postid;
            value.title = postNode.getProperty(BLOG_TITLE).getString();
            value.userid = postNode.getProperty(BLOG_USERID).getString();
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
            String repositoryName=getRepositoryName(blogid);
            blogid=getID(blogid);
            session = RepositoryManager.openSession(repositoryName,userid, password);
            Node blogNode = session.getNodeByUUID(blogid);
            Node postNode = blogNode.addNode("post", "blognode:blogEntry");
            postNode.setProperty(BLOG_TITLE, post.title);
            if ( post.userid != null )
            {
                postNode.setProperty(BLOG_USERID, post.userid);
            }
            else
            {
                postNode.setProperty(BLOG_USERID, userid);
            }
            postNode.setProperty(BLOG_DESCRIPTION, post.description);
            postNode.setProperty("blog:dateCreated", Calendar.getInstance());
            if ( post.categories != null )
            {
                postNode.setProperty(BLOG_CATEGORY, post.categories);
                for ( String category : post.categories )
                {
                    createCategory(blogid, category, category, userid, password);
                }
            }

            session.save();
            return repositoryName+ID_SEPARATOR+postNode.getUUID();
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
            String repositoryName=getRepositoryName(blogid);
            blogid=getID(blogid);
            session = RepositoryManager.openSession(repositoryName,username, password);
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
                categoryNode.setProperty(BLOG_NAME, name);
                categoryNode.setProperty(BLOG_DESCRIPTION, description);
                categoryNode.setProperty("blog:htmlUrl", getHtmlUrlForCategories(blogid));
                categoryNode.setProperty("blog:rssUrl", getRssUrlForCategories(blogid));
                session.save();
            }
            category.categoryId = repositoryName+ID_SEPARATOR+categoryNode.getUUID();
            category.categoryName = categoryNode.getProperty(BLOG_NAME).getString();
            category.description = categoryNode.getProperty(BLOG_DESCRIPTION).getString();
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
        ArrayList<CategoryInfo> categories = new ArrayList<CategoryInfo>();
        Session session = null;
        try
        {
            String repositoryName=getRepositoryName(blogid);
            blogid=getID(blogid);
            session = RepositoryManager.openSession(repositoryName,username, password);
            Query query = session.getWorkspace().getQueryManager().createQuery("//blog/category", Query.XPATH);
            QueryResult result = query.execute();
            NodeIterator nodeIterator = result.getNodes();
            while (nodeIterator.hasNext())
            {
                Node categoryNode = nodeIterator.nextNode();
                CategoryInfo category = new CategoryInfo();
                category.categoryId = repositoryName+ID_SEPARATOR+categoryNode.getUUID();
                category.categoryName = categoryNode.getProperty(BLOG_NAME).getString();
                category.description = categoryNode.getProperty(BLOG_DESCRIPTION).getString();
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
