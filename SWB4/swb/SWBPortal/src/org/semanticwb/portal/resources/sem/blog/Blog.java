package org.semanticwb.portal.resources.sem.blog;

import java.text.SimpleDateFormat;
import org.jdom.CDATA;
import org.jdom.Document;
import org.jdom.Element;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.GenericIterator;
import org.semanticwb.model.Role;
import org.semanticwb.model.User;


public class Blog extends org.semanticwb.portal.resources.sem.blog.base.BlogBase 
{
    /** The log. */
    private Logger log=SWBUtils.getLogger(Blog.class);

     /** The iso8601date format. */
    private SimpleDateFormat iso8601dateFormat;

    /** The add url. */
    public String addURL;

    
    
    public Blog(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public int getLevelUser(User user)
    {
        GenericIterator<Permision> permissions=listPermissions();
        while(permissions.hasNext())
        {
            Permision permission=permissions.next();
            if(permission.isIsRol())
            {
                GenericIterator<Role> roles=user.listRoles();
                while(roles.hasNext())
                {
                    Role role=roles.next();
                    if(role.getId().equals(permission.getSecurityId()))
                    {
                        return permission.getLevel();
                    }
                }
            }
            else
            {
                if(permission.getSecurityId().equals(user.getId()))
                {
                    return permission.getLevel();
                }
            }
        }
        return 0;

    }

    public Post getPost(String postid)
    {
        GenericIterator<Post> posts=listPosts();
        while(posts.hasNext())
        {
            Post post=posts.next();
            if(post.getId().equals(postid))
            {
                return post;
            }
        }
        return null;
    }
    /**
     * To xml.
     *
     * @param nummax the nummax
     * @return the document
     */
    public Document toXML(int nummax,User user,String format,String deleteimage,String editimage,String addimage)
    {
        iso8601dateFormat = new SimpleDateFormat(format);
        Document doc = new Document();
        Element blog = new Element("blog");
        blog.setAttribute("level", String.valueOf(getLevelUser(user)));
        blog.setAttribute("name", this.getTitle());
        blog.setAttribute("id", String.valueOf(this.getId()));
        blog.setAttribute("deleteimage", deleteimage);
        blog.setAttribute("editimage", editimage);
        blog.setAttribute("addimage", addimage);
        blog.setAttribute("webpath", SWBPlatform.getContextPath());
        if ( addURL != null )
        {
            blog.setAttribute("add", addURL);
        }
        doc.addContent(blog);
        int iposts=0;
        GenericIterator<Post> posts=this.listPosts();
        while(posts.hasNext())
        {
            posts.next();
            iposts++;
        }
        if(iposts>nummax)
        {
            iposts=nummax;
        }
        posts=this.listPosts();
        while(posts.hasNext())
        {
            Post post = posts.next();
            if(post==null)
            {
                break;
            }
            Element ePost = new Element("post");
            ePost.setAttribute("title", post.getTitle());
            ePost.setAttribute("comments", String.valueOf(post.getNumberOfComments()));
            ePost.setAttribute("id", post.getId());
            ePost.setAttribute("blogid", this.getId());
            ePost.setAttribute("date", iso8601dateFormat.format(post.getFecha_alta()));
            ePost.setAttribute("author", post.getUserPost().getFullName());
            Element eDescription = new Element("description");
            CDATA cDescription = new CDATA(post.getDescription());
            eDescription.addContent(cDescription);
            ePost.addContent(eDescription);
            blog.addContent(ePost);
        }
        return doc;
    }
}
