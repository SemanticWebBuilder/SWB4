package org.semanticwb.portal.resources.sem.forum.base;


public class PostBase extends org.semanticwb.model.SWBClass implements org.semanticwb.model.Iconable,org.semanticwb.model.Activeable,org.semanticwb.model.Traceable
{
    public static final org.semanticwb.platform.SemanticClass frm_Thread=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/forum#Thread");
    public static final org.semanticwb.platform.SemanticProperty frm_postThread=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/forum#postThread");
    public static final org.semanticwb.platform.SemanticProperty frm_postBody=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/forum#postBody");
    public static final org.semanticwb.platform.SemanticProperty swb_active=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#active");
    public static final org.semanticwb.platform.SemanticClass frm_SWBForum=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/forum#SWBForum");
    public static final org.semanticwb.platform.SemanticProperty frm_postForum=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/forum#postForum");
    public static final org.semanticwb.platform.SemanticClass frm_Attachment=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/forum#Attachment");
    public static final org.semanticwb.platform.SemanticProperty frm_hasAttachments=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/forum#hasAttachments");
    public static final org.semanticwb.platform.SemanticProperty swb_created=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#created");
    public static final org.semanticwb.platform.SemanticClass swb_User=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#User");
    public static final org.semanticwb.platform.SemanticProperty swb_modifiedBy=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#modifiedBy");
    public static final org.semanticwb.platform.SemanticProperty swb_iconClass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#iconClass");
    public static final org.semanticwb.platform.SemanticProperty swb_updated=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#updated");
    public static final org.semanticwb.platform.SemanticClass frm_Post=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/forum#Post");
    public static final org.semanticwb.platform.SemanticProperty frm_postParentPost=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/forum#postParentPost");
    public static final org.semanticwb.platform.SemanticProperty swb_creator=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#creator");
    public static final org.semanticwb.platform.SemanticProperty frm_haschildPost=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/forum#haschildPost");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/forum#Post");

    public PostBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public static org.semanticwb.portal.resources.sem.forum.Post getPost(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.portal.resources.sem.forum.Post)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
    }

    public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.Post> listPosts(org.semanticwb.model.SWBModel model)
    {
        java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
        return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Post>(org.semanticwb.portal.resources.sem.forum.Post.class, it, true);
    }

    public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.Post> listPosts()
    {
        java.util.Iterator it=sclass.listInstances();
        return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Post>(org.semanticwb.portal.resources.sem.forum.Post.class, it, true);
    }

    public static org.semanticwb.portal.resources.sem.forum.Post createPost(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.portal.resources.sem.forum.Post)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
    }

    public static org.semanticwb.portal.resources.sem.forum.Post createPost(org.semanticwb.model.SWBModel model)
    {
        long id=model.getSemanticObject().getModel().getCounter(sclass);
        return org.semanticwb.portal.resources.sem.forum.Post.createPost(String.valueOf(id), model);
    }

    public static void removePost(String id, org.semanticwb.model.SWBModel model)
    {
        model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
    }

    public static boolean hasPost(String id, org.semanticwb.model.SWBModel model)
    {
        return (getPost(id, model)!=null);
    }

    public void setThread(org.semanticwb.portal.resources.sem.forum.Thread thread)
    {
        getSemanticObject().setObjectProperty(frm_postThread, thread.getSemanticObject());
    }

    public void removeThread()
    {
        getSemanticObject().removeProperty(frm_postThread);
    }

    public org.semanticwb.portal.resources.sem.forum.Thread getThread()
    {
         org.semanticwb.portal.resources.sem.forum.Thread ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(frm_postThread);
         if(obj!=null)
         {
             ret=(org.semanticwb.portal.resources.sem.forum.Thread)obj.createGenericInstance();
         }
         return ret;
    }

    public String getBody()
    {
        return getSemanticObject().getProperty(frm_postBody);
    }

    public void setBody(String postBody)
    {
        getSemanticObject().setProperty(frm_postBody, postBody);
    }

    public boolean isActive()
    {
        return getSemanticObject().getBooleanProperty(swb_active);
    }

    public void setActive(boolean active)
    {
        getSemanticObject().setBooleanProperty(swb_active, active);
    }

    public void setForum(org.semanticwb.portal.resources.sem.forum.SWBForum swbforum)
    {
        getSemanticObject().setObjectProperty(frm_postForum, swbforum.getSemanticObject());
    }

    public void removeForum()
    {
        getSemanticObject().removeProperty(frm_postForum);
    }

    public org.semanticwb.portal.resources.sem.forum.SWBForum getForum()
    {
         org.semanticwb.portal.resources.sem.forum.SWBForum ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(frm_postForum);
         if(obj!=null)
         {
             ret=(org.semanticwb.portal.resources.sem.forum.SWBForum)obj.createGenericInstance();
         }
         return ret;
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Attachment> listAttachmentss()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Attachment>(org.semanticwb.portal.resources.sem.forum.Attachment.class, getSemanticObject().listObjectProperties(frm_hasAttachments));
    }

    public boolean hasAttachments(org.semanticwb.portal.resources.sem.forum.Attachment attachment)
    {
        if(attachment==null)return false;        return getSemanticObject().hasObjectProperty(frm_hasAttachments,attachment.getSemanticObject());
    }

    public org.semanticwb.portal.resources.sem.forum.Attachment getAttachments()
    {
         org.semanticwb.portal.resources.sem.forum.Attachment ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(frm_hasAttachments);
         if(obj!=null)
         {
             ret=(org.semanticwb.portal.resources.sem.forum.Attachment)obj.createGenericInstance();
         }
         return ret;
    }

    public java.util.Date getCreated()
    {
        return getSemanticObject().getDateProperty(swb_created);
    }

    public void setCreated(java.util.Date created)
    {
        getSemanticObject().setDateProperty(swb_created, created);
    }

    public void setModifiedBy(org.semanticwb.model.User user)
    {
        getSemanticObject().setObjectProperty(swb_modifiedBy, user.getSemanticObject());
    }

    public void removeModifiedBy()
    {
        getSemanticObject().removeProperty(swb_modifiedBy);
    }

    public org.semanticwb.model.User getModifiedBy()
    {
         org.semanticwb.model.User ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_modifiedBy);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.User)obj.createGenericInstance();
         }
         return ret;
    }

    public String getIconClass()
    {
        return getSemanticObject().getProperty(swb_iconClass);
    }

    public void setIconClass(String iconClass)
    {
        getSemanticObject().setProperty(swb_iconClass, iconClass);
    }

    public java.util.Date getUpdated()
    {
        return getSemanticObject().getDateProperty(swb_updated);
    }

    public void setUpdated(java.util.Date updated)
    {
        getSemanticObject().setDateProperty(swb_updated, updated);
    }

    public void setParentPost(org.semanticwb.portal.resources.sem.forum.Post post)
    {
        getSemanticObject().setObjectProperty(frm_postParentPost, post.getSemanticObject());
    }

    public void removeParentPost()
    {
        getSemanticObject().removeProperty(frm_postParentPost);
    }

    public org.semanticwb.portal.resources.sem.forum.Post getParentPost()
    {
         org.semanticwb.portal.resources.sem.forum.Post ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(frm_postParentPost);
         if(obj!=null)
         {
             ret=(org.semanticwb.portal.resources.sem.forum.Post)obj.createGenericInstance();
         }
         return ret;
    }

    public void setCreator(org.semanticwb.model.User user)
    {
        getSemanticObject().setObjectProperty(swb_creator, user.getSemanticObject());
    }

    public void removeCreator()
    {
        getSemanticObject().removeProperty(swb_creator);
    }

    public org.semanticwb.model.User getCreator()
    {
         org.semanticwb.model.User ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_creator);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.User)obj.createGenericInstance();
         }
         return ret;
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Post> listchildPosts()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Post>(org.semanticwb.portal.resources.sem.forum.Post.class, getSemanticObject().listObjectProperties(frm_haschildPost));
    }

    public boolean haschildPost(org.semanticwb.portal.resources.sem.forum.Post post)
    {
        if(post==null)return false;        return getSemanticObject().hasObjectProperty(frm_haschildPost,post.getSemanticObject());
    }

    public org.semanticwb.portal.resources.sem.forum.Post getchildPost()
    {
         org.semanticwb.portal.resources.sem.forum.Post ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(frm_haschildPost);
         if(obj!=null)
         {
             ret=(org.semanticwb.portal.resources.sem.forum.Post)obj.createGenericInstance();
         }
         return ret;
    }
}
