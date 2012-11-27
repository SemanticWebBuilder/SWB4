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
package org.semanticwb.portal.resources.sem.blog.base;


// TODO: Auto-generated Javadoc
/**
 * The Class BlogBase.
 */
public abstract class BlogBase extends org.semanticwb.model.SWBClass implements org.semanticwb.model.Descriptiveable
{
    
    /** The Constant blog_Permision. */
    public static final org.semanticwb.platform.SemanticClass blog_Permision=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/wb2.0/blog#Permision");
    
    /** The Constant blog_hasPermission. */
    public static final org.semanticwb.platform.SemanticProperty blog_hasPermission=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/wb2.0/blog#hasPermission");
    
    /** The Constant blog_Post. */
    public static final org.semanticwb.platform.SemanticClass blog_Post=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/wb2.0/blog#Post");
    
    /** The Constant blog_hasPost. */
    public static final org.semanticwb.platform.SemanticProperty blog_hasPost=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/wb2.0/blog#hasPost");
    
    /** The Constant blog_Blog. */
    public static final org.semanticwb.platform.SemanticClass blog_Blog=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/wb2.0/blog#Blog");
    
    /** The Constant sclass. */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/wb2.0/blog#Blog");

    /**
     * The Class ClassMgr.
     */
    public static class ClassMgr
    {

        /**
         * List blogs.
         * 
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.sem.blog.Blog> listBlogs(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.blog.Blog>(it, true);
        }

        /**
         * List blogs.
         * 
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.sem.blog.Blog> listBlogs()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.blog.Blog>(it, true);
        }

        /**
         * Creates the blog.
         * 
         * @param model the model
         * @return the org.semanticwb.portal.resources.sem.blog. blog
         */
        public static org.semanticwb.portal.resources.sem.blog.Blog createBlog(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.portal.resources.sem.blog.Blog.ClassMgr.createBlog(String.valueOf(id), model);
        }

        /**
         * Gets the blog.
         * 
         * @param id the id
         * @param model the model
         * @return the blog
         */
        public static org.semanticwb.portal.resources.sem.blog.Blog getBlog(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.portal.resources.sem.blog.Blog)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        /**
         * Creates the blog.
         * 
         * @param id the id
         * @param model the model
         * @return the org.semanticwb.portal.resources.sem.blog. blog
         */
        public static org.semanticwb.portal.resources.sem.blog.Blog createBlog(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.portal.resources.sem.blog.Blog)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        }

        /**
         * Removes the blog.
         * 
         * @param id the id
         * @param model the model
         */
        public static void removeBlog(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        /**
         * Checks for blog.
         * 
         * @param id the id
         * @param model the model
         * @return true, if successful
         */
        public static boolean hasBlog(String id, org.semanticwb.model.SWBModel model)
        {
            return (getBlog(id, model)!=null);
        }

        /**
         * List blog by permission.
         * 
         * @param value the value
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.sem.blog.Blog> listBlogByPermission(org.semanticwb.portal.resources.sem.blog.Permision value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.blog.Blog> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(blog_hasPermission, value.getSemanticObject(),sclass));
            return it;
        }

        /**
         * List blog by permission.
         * 
         * @param value the value
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.sem.blog.Blog> listBlogByPermission(org.semanticwb.portal.resources.sem.blog.Permision value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.blog.Blog> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(blog_hasPermission,value.getSemanticObject(),sclass));
            return it;
        }

        /**
         * List blog by post.
         * 
         * @param value the value
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.sem.blog.Blog> listBlogByPost(org.semanticwb.portal.resources.sem.blog.Post value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.blog.Blog> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(blog_hasPost, value.getSemanticObject(),sclass));
            return it;
        }

        /**
         * List blog by post.
         * 
         * @param value the value
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.sem.blog.Blog> listBlogByPost(org.semanticwb.portal.resources.sem.blog.Post value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.blog.Blog> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(blog_hasPost,value.getSemanticObject(),sclass));
            return it;
        }
    }

    /**
     * Instantiates a new blog base.
     * 
     * @param base the base
     */
    public BlogBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    /**
     * Gets the title.
     * 
     * @return the title
     */
    public String getTitle()
    {
        return getSemanticObject().getProperty(swb_title);
    }

    /**
     * Sets the title.
     * 
     * @param value the new title
     */
    public void setTitle(String value)
    {
        getSemanticObject().setProperty(swb_title, value);
    }

    /**
     * Gets the title.
     * 
     * @param lang the lang
     * @return the title
     */
    public String getTitle(String lang)
    {
        return getSemanticObject().getProperty(swb_title, null, lang);
    }

    /**
     * Gets the display title.
     * 
     * @param lang the lang
     * @return the display title
     */
    public String getDisplayTitle(String lang)
    {
        return getSemanticObject().getLocaleProperty(swb_title, lang);
    }

    /**
     * Sets the title.
     * 
     * @param title the title
     * @param lang the lang
     */
    public void setTitle(String title, String lang)
    {
        getSemanticObject().setProperty(swb_title, title, lang);
    }

    /**
     * List permissions.
     * 
     * @return the org.semanticwb.model. generic iterator
     */
    public org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.blog.Permision> listPermissions()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.blog.Permision>(getSemanticObject().listObjectProperties(blog_hasPermission));
    }

    /**
     * Checks for permission.
     * 
     * @param value the value
     * @return true, if successful
     */
    public boolean hasPermission(org.semanticwb.portal.resources.sem.blog.Permision value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(blog_hasPermission,value.getSemanticObject());
        }
        return ret;
    }

    /**
     * Adds the permission.
     * 
     * @param value the value
     */
    public void addPermission(org.semanticwb.portal.resources.sem.blog.Permision value)
    {
        getSemanticObject().addObjectProperty(blog_hasPermission, value.getSemanticObject());
    }

    /**
     * Removes the all permission.
     */
    public void removeAllPermission()
    {
        getSemanticObject().removeProperty(blog_hasPermission);
    }

    /**
     * Removes the permission.
     * 
     * @param value the value
     */
    public void removePermission(org.semanticwb.portal.resources.sem.blog.Permision value)
    {
        getSemanticObject().removeObjectProperty(blog_hasPermission,value.getSemanticObject());
    }

    /**
     * Gets the permission.
     * 
     * @return the permission
     */
    public org.semanticwb.portal.resources.sem.blog.Permision getPermission()
    {
         org.semanticwb.portal.resources.sem.blog.Permision ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(blog_hasPermission);
         if(obj!=null)
         {
             ret=(org.semanticwb.portal.resources.sem.blog.Permision)obj.createGenericInstance();
         }
         return ret;
    }

    /**
     * List posts.
     * 
     * @return the org.semanticwb.model. generic iterator
     */
    public org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.blog.Post> listPosts()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.blog.Post>(getSemanticObject().listObjectProperties(blog_hasPost));
    }

    /**
     * Checks for post.
     * 
     * @param value the value
     * @return true, if successful
     */
    public boolean hasPost(org.semanticwb.portal.resources.sem.blog.Post value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(blog_hasPost,value.getSemanticObject());
        }
        return ret;
    }

    /**
     * Adds the post.
     * 
     * @param value the value
     */
    public void addPost(org.semanticwb.portal.resources.sem.blog.Post value)
    {
        getSemanticObject().addObjectProperty(blog_hasPost, value.getSemanticObject());
    }

    /**
     * Removes the all post.
     */
    public void removeAllPost()
    {
        getSemanticObject().removeProperty(blog_hasPost);
    }

    /**
     * Removes the post.
     * 
     * @param value the value
     */
    public void removePost(org.semanticwb.portal.resources.sem.blog.Post value)
    {
        getSemanticObject().removeObjectProperty(blog_hasPost,value.getSemanticObject());
    }

    /**
     * Gets the post.
     * 
     * @return the post
     */
    public org.semanticwb.portal.resources.sem.blog.Post getPost()
    {
         org.semanticwb.portal.resources.sem.blog.Post ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(blog_hasPost);
         if(obj!=null)
         {
             ret=(org.semanticwb.portal.resources.sem.blog.Post)obj.createGenericInstance();
         }
         return ret;
    }

    /**
     * Gets the description.
     * 
     * @return the description
     */
    public String getDescription()
    {
        return getSemanticObject().getProperty(swb_description);
    }

    /**
     * Sets the description.
     * 
     * @param value the new description
     */
    public void setDescription(String value)
    {
        getSemanticObject().setProperty(swb_description, value);
    }

    /**
     * Gets the description.
     * 
     * @param lang the lang
     * @return the description
     */
    public String getDescription(String lang)
    {
        return getSemanticObject().getProperty(swb_description, null, lang);
    }

    /**
     * Gets the display description.
     * 
     * @param lang the lang
     * @return the display description
     */
    public String getDisplayDescription(String lang)
    {
        return getSemanticObject().getLocaleProperty(swb_description, lang);
    }

    /**
     * Sets the description.
     * 
     * @param description the description
     * @param lang the lang
     */
    public void setDescription(String description, String lang)
    {
        getSemanticObject().setProperty(swb_description, description, lang);
    }
}
