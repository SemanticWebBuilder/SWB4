/*
 * SemanticWebBuilder es una plataforma para el desarrollo de portales y aplicaciones de integración,
 * colaboración y conocimiento, que gracias al uso de tecnología semántica puede generar contextos de
 * información alrededor de algún tema de interés o bien integrar información y aplicaciones de diferentes
 * fuentes, donde a la información se le asigna un significado, de forma que pueda ser interpretada y
 * procesada por personas y/o sistemas, es una creación original del Fondo de Información y Documentación
 * para la Industria INFOTEC, cuyo registro se encuentra actualmente en trámite.
 *
 * INFOTEC pone a su disposición la herramienta SemanticWebBuilder a través de su licenciamiento abierto al público (‘open source’),
 * en virtud del cual, usted podrá usarlo en las mismas condiciones con que INFOTEC lo ha diseñado y puesto a su disposición;
 * aprender de él; distribuirlo a terceros; acceder a su código fuente y modificarlo, y combinarlo o enlazarlo con otro software,
 * todo ello de conformidad con los términos y condiciones de la LICENCIA ABIERTA AL PÚBLICO que otorga INFOTEC para la utilización
 * del SemanticWebBuilder 4.0.
 *
 * INFOTEC no otorga garantía sobre SemanticWebBuilder, de ninguna especie y naturaleza, ni implícita ni explícita,
 * siendo usted completamente responsable de la utilización que le dé y asumiendo la totalidad de los riesgos que puedan derivar
 * de la misma.
 *
 * Si usted tiene cualquier duda o comentario sobre SemanticWebBuilder, INFOTEC pone a su disposición la siguiente
 * dirección electrónica:
 *  http://www.semanticwebbuilder.org
 */
package org.semanticwb.portal.resources.blog;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;
import org.jdom.CDATA;
import org.jdom.Document;
import org.jdom.Element;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Role;
import org.semanticwb.model.User;

// TODO: Auto-generated Javadoc
/**
 * The Class Blog.
 * 
 * @author victor.lorenzana
 */
public class Blog
{
    
    /** The log. */
    private Logger log=SWBUtils.getLogger(Blog.class);
    
    /** The posts. */
    ArrayList posts = new ArrayList();
    
    /** The blog id. */
    private long blogID;
    
    /** The name. */
    private String name;
    
    /** The add url. */
    public String addURL;
    
    /** The level user. */
    private int levelUser;
    
    /** The iso8601date format. */
    private SimpleDateFormat iso8601dateFormat;
    
    /** The deleteimage. */
    private String deleteimage;
    
    /** The editimage. */
    private String editimage;
    
    /** The addimage. */
    private String addimage;
    
    /**
     * Instantiates a new blog.
     * 
     * @param blogID the blog id
     * @param user the user
     * @param format the format
     * @param deleteimage the deleteimage
     * @param editimage the editimage
     * @param addimage the addimage
     */
    public Blog(long blogID, User user,String format,String deleteimage,String editimage,String addimage)
    {
        String lang="es";
        if(user!=null && user.getLanguage()!=null)
        {
            lang=user.getLanguage();
        }
        try
        {
            iso8601dateFormat = new SimpleDateFormat(format,new Locale(lang));        
        }
        catch(Exception e)
        {
            iso8601dateFormat = new SimpleDateFormat(format,new Locale("es"));        
        }
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

    /**
     * New blog.
     * 
     * @param name the name
     * @return the blog
     */
    public static Blog newBlog(String name)
    {
        return null;
    }

    /**
     * Gets the blog id.
     * 
     * @return the blog id
     */
    public long getBlogID()
    {
        return blogID;
    }

    /**
     * Gets the name.
     * 
     * @return the name
     */
    public String getName()
    {
        return name;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    public String toString()
    {
        return name;
    }

    /**
     * To xml.
     * 
     * @param nummax the nummax
     * @return the document
     */
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
