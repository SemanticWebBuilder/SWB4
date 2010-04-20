package org.semanticwb.portal.resources.sem.blog.base;


public abstract class BlogBase extends org.semanticwb.model.SWBClass implements org.semanticwb.model.Descriptiveable
{
    public static final org.semanticwb.platform.SemanticClass blog_Permision=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/wb2.0/blog#Permision");
    public static final org.semanticwb.platform.SemanticProperty blog_hasPermission=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/wb2.0/blog#hasPermission");
    public static final org.semanticwb.platform.SemanticClass blog_Post=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/wb2.0/blog#Post");
    public static final org.semanticwb.platform.SemanticProperty blog_hasPost=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/wb2.0/blog#hasPost");
    public static final org.semanticwb.platform.SemanticClass blog_Blog=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/wb2.0/blog#Blog");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/wb2.0/blog#Blog");

    public static class ClassMgr
    {

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.blog.Blog> listBlogs(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.blog.Blog>(it, true);
        }

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.blog.Blog> listBlogs()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.blog.Blog>(it, true);
        }

        public static org.semanticwb.portal.resources.sem.blog.Blog createBlog(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.portal.resources.sem.blog.Blog.ClassMgr.createBlog(String.valueOf(id), model);
        }

        public static org.semanticwb.portal.resources.sem.blog.Blog getBlog(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.portal.resources.sem.blog.Blog)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static org.semanticwb.portal.resources.sem.blog.Blog createBlog(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.portal.resources.sem.blog.Blog)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        }

        public static void removeBlog(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        public static boolean hasBlog(String id, org.semanticwb.model.SWBModel model)
        {
            return (getBlog(id, model)!=null);
        }

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.blog.Blog> listBlogByPermission(org.semanticwb.portal.resources.sem.blog.Permision value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.blog.Blog> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(blog_hasPermission, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.blog.Blog> listBlogByPermission(org.semanticwb.portal.resources.sem.blog.Permision value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.blog.Blog> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(blog_hasPermission,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.blog.Blog> listBlogByPost(org.semanticwb.portal.resources.sem.blog.Post value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.blog.Blog> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(blog_hasPost, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.blog.Blog> listBlogByPost(org.semanticwb.portal.resources.sem.blog.Post value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.blog.Blog> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(blog_hasPost,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public BlogBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public String getTitle()
    {
        return getSemanticObject().getProperty(swb_title);
    }

    public void setTitle(String value)
    {
        getSemanticObject().setProperty(swb_title, value);
    }

    public String getTitle(String lang)
    {
        return getSemanticObject().getProperty(swb_title, null, lang);
    }

    public String getDisplayTitle(String lang)
    {
        return getSemanticObject().getLocaleProperty(swb_title, lang);
    }

    public void setTitle(String title, String lang)
    {
        getSemanticObject().setProperty(swb_title, title, lang);
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.blog.Permision> listPermissions()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.blog.Permision>(getSemanticObject().listObjectProperties(blog_hasPermission));
    }

    public boolean hasPermission(org.semanticwb.portal.resources.sem.blog.Permision value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(blog_hasPermission,value.getSemanticObject());
        }
        return ret;
    }

    public void addPermission(org.semanticwb.portal.resources.sem.blog.Permision value)
    {
        getSemanticObject().addObjectProperty(blog_hasPermission, value.getSemanticObject());
    }

    public void removeAllPermission()
    {
        getSemanticObject().removeProperty(blog_hasPermission);
    }

    public void removePermission(org.semanticwb.portal.resources.sem.blog.Permision value)
    {
        getSemanticObject().removeObjectProperty(blog_hasPermission,value.getSemanticObject());
    }

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

    public org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.blog.Post> listPosts()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.blog.Post>(getSemanticObject().listObjectProperties(blog_hasPost));
    }

    public boolean hasPost(org.semanticwb.portal.resources.sem.blog.Post value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(blog_hasPost,value.getSemanticObject());
        }
        return ret;
    }

    public void addPost(org.semanticwb.portal.resources.sem.blog.Post value)
    {
        getSemanticObject().addObjectProperty(blog_hasPost, value.getSemanticObject());
    }

    public void removeAllPost()
    {
        getSemanticObject().removeProperty(blog_hasPost);
    }

    public void removePost(org.semanticwb.portal.resources.sem.blog.Post value)
    {
        getSemanticObject().removeObjectProperty(blog_hasPost,value.getSemanticObject());
    }

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

    public String getDescription()
    {
        return getSemanticObject().getProperty(swb_description);
    }

    public void setDescription(String value)
    {
        getSemanticObject().setProperty(swb_description, value);
    }

    public String getDescription(String lang)
    {
        return getSemanticObject().getProperty(swb_description, null, lang);
    }

    public String getDisplayDescription(String lang)
    {
        return getSemanticObject().getLocaleProperty(swb_description, lang);
    }

    public void setDescription(String description, String lang)
    {
        getSemanticObject().setProperty(swb_description, description, lang);
    }
}
