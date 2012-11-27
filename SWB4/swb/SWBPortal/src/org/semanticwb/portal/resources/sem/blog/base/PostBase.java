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
 * The Class PostBase.
 */
public abstract class PostBase extends org.semanticwb.model.SWBClass implements org.semanticwb.model.Descriptiveable
{
    
    /** The Constant swb_User. */
    public static final org.semanticwb.platform.SemanticClass swb_User=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#User");
    
    /** The Constant blog_UserPost. */
    public static final org.semanticwb.platform.SemanticProperty blog_UserPost=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/wb2.0/blog#UserPost");
    
    /** The Constant blog_fecha_alta. */
    public static final org.semanticwb.platform.SemanticProperty blog_fecha_alta=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/wb2.0/blog#fecha_alta");
    
    /** The Constant blog_Comment. */
    public static final org.semanticwb.platform.SemanticClass blog_Comment=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/wb2.0/blog#Comment");
    
    /** The Constant blog_hasComment. */
    public static final org.semanticwb.platform.SemanticProperty blog_hasComment=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/wb2.0/blog#hasComment");
    
    /** The Constant blog_Post. */
    public static final org.semanticwb.platform.SemanticClass blog_Post=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/wb2.0/blog#Post");
    
    /** The Constant sclass. */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/wb2.0/blog#Post");

    /**
     * The Class ClassMgr.
     */
    public static class ClassMgr
    {

        /**
         * List posts.
         * 
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.sem.blog.Post> listPosts(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.blog.Post>(it, true);
        }

        /**
         * List posts.
         * 
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.sem.blog.Post> listPosts()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.blog.Post>(it, true);
        }

        /**
         * Creates the post.
         * 
         * @param model the model
         * @return the org.semanticwb.portal.resources.sem.blog. post
         */
        public static org.semanticwb.portal.resources.sem.blog.Post createPost(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.portal.resources.sem.blog.Post.ClassMgr.createPost(String.valueOf(id), model);
        }

        /**
         * Gets the post.
         * 
         * @param id the id
         * @param model the model
         * @return the post
         */
        public static org.semanticwb.portal.resources.sem.blog.Post getPost(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.portal.resources.sem.blog.Post)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        /**
         * Creates the post.
         * 
         * @param id the id
         * @param model the model
         * @return the org.semanticwb.portal.resources.sem.blog. post
         */
        public static org.semanticwb.portal.resources.sem.blog.Post createPost(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.portal.resources.sem.blog.Post)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        }

        /**
         * Removes the post.
         * 
         * @param id the id
         * @param model the model
         */
        public static void removePost(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        /**
         * Checks for post.
         * 
         * @param id the id
         * @param model the model
         * @return true, if successful
         */
        public static boolean hasPost(String id, org.semanticwb.model.SWBModel model)
        {
            return (getPost(id, model)!=null);
        }

        /**
         * List post by user post.
         * 
         * @param value the value
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.sem.blog.Post> listPostByUserPost(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.blog.Post> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(blog_UserPost, value.getSemanticObject(),sclass));
            return it;
        }

        /**
         * List post by user post.
         * 
         * @param value the value
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.sem.blog.Post> listPostByUserPost(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.blog.Post> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(blog_UserPost,value.getSemanticObject(),sclass));
            return it;
        }

        /**
         * List post by comment.
         * 
         * @param value the value
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.sem.blog.Post> listPostByComment(org.semanticwb.portal.resources.sem.blog.Comment value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.blog.Post> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(blog_hasComment, value.getSemanticObject(),sclass));
            return it;
        }

        /**
         * List post by comment.
         * 
         * @param value the value
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.sem.blog.Post> listPostByComment(org.semanticwb.portal.resources.sem.blog.Comment value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.blog.Post> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(blog_hasComment,value.getSemanticObject(),sclass));
            return it;
        }
    }

    /**
     * Instantiates a new post base.
     * 
     * @param base the base
     */
    public PostBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    /**
     * Sets the user post.
     * 
     * @param value the new user post
     */
    public void setUserPost(org.semanticwb.model.User value)
    {
        getSemanticObject().setObjectProperty(blog_UserPost, value.getSemanticObject());
    }

    /**
     * Removes the user post.
     */
    public void removeUserPost()
    {
        getSemanticObject().removeProperty(blog_UserPost);
    }

    /**
     * Gets the user post.
     * 
     * @return the user post
     */
    public org.semanticwb.model.User getUserPost()
    {
         org.semanticwb.model.User ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(blog_UserPost);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.User)obj.createGenericInstance();
         }
         return ret;
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
     * Gets the fecha_alta.
     * 
     * @return the fecha_alta
     */
    public java.util.Date getFecha_alta()
    {
        return getSemanticObject().getDateProperty(blog_fecha_alta);
    }

    /**
     * Sets the fecha_alta.
     * 
     * @param value the new fecha_alta
     */
    public void setFecha_alta(java.util.Date value)
    {
        getSemanticObject().setDateProperty(blog_fecha_alta, value);
    }

    /**
     * List comments.
     * 
     * @return the org.semanticwb.model. generic iterator
     */
    public org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.blog.Comment> listComments()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.blog.Comment>(getSemanticObject().listObjectProperties(blog_hasComment));
    }

    /**
     * Checks for comment.
     * 
     * @param value the value
     * @return true, if successful
     */
    public boolean hasComment(org.semanticwb.portal.resources.sem.blog.Comment value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(blog_hasComment,value.getSemanticObject());
        }
        return ret;
    }

    /**
     * Adds the comment.
     * 
     * @param value the value
     */
    public void addComment(org.semanticwb.portal.resources.sem.blog.Comment value)
    {
        getSemanticObject().addObjectProperty(blog_hasComment, value.getSemanticObject());
    }

    /**
     * Removes the all comment.
     */
    public void removeAllComment()
    {
        getSemanticObject().removeProperty(blog_hasComment);
    }

    /**
     * Removes the comment.
     * 
     * @param value the value
     */
    public void removeComment(org.semanticwb.portal.resources.sem.blog.Comment value)
    {
        getSemanticObject().removeObjectProperty(blog_hasComment,value.getSemanticObject());
    }

    /**
     * Gets the comment.
     * 
     * @return the comment
     */
    public org.semanticwb.portal.resources.sem.blog.Comment getComment()
    {
         org.semanticwb.portal.resources.sem.blog.Comment ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(blog_hasComment);
         if(obj!=null)
         {
             ret=(org.semanticwb.portal.resources.sem.blog.Comment)obj.createGenericInstance();
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
