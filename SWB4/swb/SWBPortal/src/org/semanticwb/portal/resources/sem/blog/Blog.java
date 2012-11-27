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


// TODO: Auto-generated Javadoc
/**
 * The Class Blog.
 */
public class Blog extends org.semanticwb.portal.resources.sem.blog.base.BlogBase 
{
    /** The log. */
    private Logger log=SWBUtils.getLogger(Blog.class);

     /** The iso8601date format. */
    private SimpleDateFormat iso8601dateFormat;

    /** The add url. */
    public String addURL;

    
    
    /**
     * Instantiates a new blog.
     * 
     * @param base the base
     */
    public Blog(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    /**
     * Gets the level user.
     * 
     * @param user the user
     * @return the level user
     */
    public int getLevelUser(User user)
    {
        GenericIterator<Permision> permissions=listPermissions();
        while(permissions.hasNext())
        {
            Permision permission=permissions.next();            
            if(permission.isIsRol() && !permission.getSecurityId().equals("*"))
            {
                GenericIterator<Role> roles=user.listRoles();
                while(roles.hasNext())
                {
                    Role role=roles.next();                    
                    String name=role.getId()+"_" + role.getUserRepository().getId();
                    if(name.equals(permission.getSecurityId()) && user.isRegistered())
                    {                        
                        return permission.getLevel();
                    }
                }
            }
            else
            {
                String name=user.getId() + "_" + user.getUserRepository().getId();
                if(permission.getSecurityId().equals(name) && !permission.getSecurityId().equals("*") && user.isRegistered())
                {
                    return permission.getLevel();
                }
            }
        }
        
        permissions=listPermissions();
        while(permissions.hasNext())
        {
            Permision permission=permissions.next();
            if(permission.isIsRol() && permission.getSecurityId().equals("*") && user.isRegistered())
            {
                return permission.getLevel();
            }            
        }

        return 0;

    }

    /**
     * Gets the post.
     * 
     * @param postid the postid
     * @return the post
     */
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
     * @param user the user
     * @param format the format
     * @param deleteimage the deleteimage
     * @param editimage the editimage
     * @param addimage the addimage
     * @return the document
     */
    public Document toXML(int nummax,User user,String format,String deleteimage,String editimage,String addimage)
    {
        if(deleteimage==null || deleteimage.equals(""))
        {
            deleteimage=".gif";
        }
        if(editimage==null || editimage.equals(""))
        {
            editimage=".gif";
        }
        if(addimage==null || addimage.equals(""))
        {
            addimage=".gif";
        }


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
            try
            {
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
            catch(Exception e)
            {
                log.error(e);
            }
        }
        return doc;
    }
}
