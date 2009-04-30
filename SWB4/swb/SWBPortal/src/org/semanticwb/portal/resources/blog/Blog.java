/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.portal.resources.blog;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import org.jdom.CDATA;
import org.jdom.Document;
import org.jdom.Element;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Role;
import org.semanticwb.model.User;

/**
 *
 * @author victor.lorenzana
 */
public class Blog
{
    
    private Logger log=SWBUtils.getLogger(Blog.class);
    ArrayList posts = new ArrayList();
    private long blogID;
    private String name;
    public String addURL;
    private int levelUser;
    private SimpleDateFormat iso8601dateFormat;
    private String deleteimage;
    private String editimage;
    private String addimage;
    public Blog(long blogID, User user,String format,String deleteimage,String editimage,String addimage)
    {
        iso8601dateFormat = new SimpleDateFormat(format);
        this.blogID = blogID;        
        this.deleteimage=deleteimage;
        this.editimage=editimage;
        this.addimage=addimage;
        Connection con = SWBUtils.DB.getDefaultConnection();
        if ( con == null )
        {
            throw new IllegalArgumentException("No se puede conectar a la base de datos"+SWBUtils.DB.getDatabaseName());
        }
        try
        {
            PreparedStatement pt = con.prepareStatement("select * from wbblog where blogid=" + blogID);
            ResultSet rs = pt.executeQuery();
            if ( rs.next() )
            {
                name = rs.getString("blogname");
            }
            else
            {
                throw new IllegalArgumentException("No existe el blog " + blogID);
            }
            rs.close();
            pt.close();
            pt = con.prepareStatement("select postid from wbblogpost where blogid=" + blogID + " order by fec_alta desc");
            rs = pt.executeQuery();
            while (rs.next())
            {
                Post post = new Post(rs.getLong("postid"), blogID,user.getUserRepository());
                this.posts.add(post);
            }
            rs.close();
            pt.close();

            // Todos tienen acceso sin importar el usuario
            pt = con.prepareStatement("select * from wbblogpermissions where blogid=? and userid=? and isrol=?");
            pt.setLong(1, blogID);
            pt.setString(2, "*");
            pt.setInt(3, 0);
            rs = pt.executeQuery();
            if ( rs.next() )
            {
                if ( rs.getInt("level") > this.levelUser )
                {
                    this.levelUser = rs.getInt("level");
                }
            }
            rs.close();
            pt.close();
            
            
            // Todos tienen acceso sin importar el rol
            pt = con.prepareStatement("select * from wbblogpermissions where blogid=? and userid=? and isrol=?");
            pt.setLong(1, blogID);
            pt.setString(2, "*");
            pt.setInt(3, 1);
            rs = pt.executeQuery();
            if ( rs.next() )
            {
                if ( rs.getInt("level") > this.levelUser )
                {
                    this.levelUser = rs.getInt("level");
                }
            }
            rs.close();
            pt.close();
            
            // cumple con el usuario

            pt = con.prepareStatement("select * from wbblogpermissions where blogid=? and userid=? and isrol=?");
            pt.setLong(1, blogID);
            pt.setString(2, user.getId()+"_"+user.getUserRepository().getId());
            pt.setInt(3, 0);
            rs = pt.executeQuery();
            if ( rs.next() )
            {
                if ( rs.getInt("level") > this.levelUser )
                {
                    this.levelUser = rs.getInt("level");
                }
            }
            rs.close();
            pt.close();

            // cumple con el rol
            Iterator<Role> roles = user.listRoles();
            while (roles.hasNext())
            {
                Role goRol = roles.next();
                //Integer rol = ( Integer ) roles.next();
                String srol = goRol.getId();
                pt = con.prepareStatement("select level from wbblogpermissions where blogid=? and userid=? and isrol=?");
                pt.setLong(1, blogID);
                pt.setString(2, srol+"_"+user.getUserRepository().getId());
                pt.setInt(3, 1);
                rs = pt.executeQuery();
                if ( rs.next() )
                {
                    if ( rs.getInt("level") > this.levelUser )
                    {
                        this.levelUser = rs.getInt("level");
                    }
                }
                rs.close();
                pt.close();
            }

        }
        catch ( SQLException sqle )
        {
            throw new IllegalArgumentException(sqle.getMessage());
        }
        finally
        {
            try
            {
                con.close();
            }
            catch ( SQLException sqle )
            {
                log.error(sqle);
            }
        }
    }

    public static Blog newBlog(String name)
    {
        return null;
    }

    public long getBlogID()
    {
        return blogID;
    }

    public String getName()
    {
        return name;
    }

    public String toString()
    {
        return name;
    }

    public Document toXML(int nummax)
    {
        Document doc = new Document();
        Element blog = new Element("blog");
        blog.setAttribute("level", String.valueOf(levelUser));
        blog.setAttribute("name", this.name);
        blog.setAttribute("id", String.valueOf(this.blogID));
        blog.setAttribute("deleteimage", deleteimage);
        blog.setAttribute("editimage", editimage);
        blog.setAttribute("addimage", addimage);
        blog.setAttribute("webpath", SWBPlatform.getContextPath());
        if ( addURL != null )
        {
            blog.setAttribute("add", addURL);
        }
        doc.addContent(blog);
        int iposts=nummax;
        if(this.posts.size()<nummax)
        {
            iposts=this.posts.size();
        }
        for(int i=0;i<iposts;i++)
        {            
            Post post = (Post)this.posts.get(i);
            if(post==null)
            {
                break;
            }
            Element ePost = new Element("post");
            ePost.setAttribute("title", post.getTitle());
            ePost.setAttribute("comments", String.valueOf(post.getNumberOfComments()));
            ePost.setAttribute("id", String.valueOf(post.getPostID()));
            ePost.setAttribute("blogid", String.valueOf(this.blogID));
            ePost.setAttribute("date", iso8601dateFormat.format(post.getDate()));
            ePost.setAttribute("author", post.getUser());
            Element eDescription = new Element("description");
            CDATA cDescription = new CDATA(post.getDescription());
            eDescription.addContent(cDescription);
            ePost.addContent(eDescription);
            blog.addContent(ePost);           
        }
        return doc;
    }
}
