package org.semanticwb.portal.resources.sem.blog.base;


public abstract class PostBase extends org.semanticwb.model.SWBClass implements org.semanticwb.model.Descriptiveable
{
    public static final org.semanticwb.platform.SemanticClass swb_User=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#User");
    public static final org.semanticwb.platform.SemanticProperty blog_UserPost=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/wb2.0/blog#UserPost");
    public static final org.semanticwb.platform.SemanticProperty blog_fecha_alta=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/wb2.0/blog#fecha_alta");
    public static final org.semanticwb.platform.SemanticClass blog_Comment=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/wb2.0/blog#Comment");
    public static final org.semanticwb.platform.SemanticProperty blog_hasComment=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/wb2.0/blog#hasComment");
    public static final org.semanticwb.platform.SemanticClass blog_Post=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/wb2.0/blog#Post");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/wb2.0/blog#Post");

    public static class ClassMgr
    {

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.blog.Post> listPosts(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.blog.Post>(it, true);
        }

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.blog.Post> listPosts()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.blog.Post>(it, true);
        }

        public static org.semanticwb.portal.resources.sem.blog.Post createPost(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.portal.resources.sem.blog.Post.ClassMgr.createPost(String.valueOf(id), model);
        }

        public static org.semanticwb.portal.resources.sem.blog.Post getPost(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.portal.resources.sem.blog.Post)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static org.semanticwb.portal.resources.sem.blog.Post createPost(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.portal.resources.sem.blog.Post)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        }

        public static void removePost(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        public static boolean hasPost(String id, org.semanticwb.model.SWBModel model)
        {
            return (getPost(id, model)!=null);
        }

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.blog.Post> listPostByUserPost(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.blog.Post> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(blog_UserPost, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.blog.Post> listPostByUserPost(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.blog.Post> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(blog_UserPost,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.blog.Post> listPostByComment(org.semanticwb.portal.resources.sem.blog.Comment value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.blog.Post> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(blog_hasComment, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.blog.Post> listPostByComment(org.semanticwb.portal.resources.sem.blog.Comment value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.blog.Post> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(blog_hasComment,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public PostBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public void setUserPost(org.semanticwb.model.User value)
    {
        getSemanticObject().setObjectProperty(blog_UserPost, value.getSemanticObject());
    }

    public void removeUserPost()
    {
        getSemanticObject().removeProperty(blog_UserPost);
    }

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

    public java.util.Date getFecha_alta()
    {
        return getSemanticObject().getDateProperty(blog_fecha_alta);
    }

    public void setFecha_alta(java.util.Date value)
    {
        getSemanticObject().setDateProperty(blog_fecha_alta, value);
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.blog.Comment> listComments()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.blog.Comment>(getSemanticObject().listObjectProperties(blog_hasComment));
    }

    public boolean hasComment(org.semanticwb.portal.resources.sem.blog.Comment value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(blog_hasComment,value.getSemanticObject());
        }
        return ret;
    }

    public void addComment(org.semanticwb.portal.resources.sem.blog.Comment value)
    {
        getSemanticObject().addObjectProperty(blog_hasComment, value.getSemanticObject());
    }

    public void removeAllComment()
    {
        getSemanticObject().removeProperty(blog_hasComment);
    }

    public void removeComment(org.semanticwb.portal.resources.sem.blog.Comment value)
    {
        getSemanticObject().removeObjectProperty(blog_hasComment,value.getSemanticObject());
    }

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
