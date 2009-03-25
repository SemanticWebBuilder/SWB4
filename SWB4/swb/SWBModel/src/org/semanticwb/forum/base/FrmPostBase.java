package org.semanticwb.forum.base;


public class FrmPostBase extends org.semanticwb.model.SWBClass implements org.semanticwb.model.Iconable,org.semanticwb.model.Activeable,org.semanticwb.model.Traceable
{
    public static final org.semanticwb.platform.SemanticClass frm_FrmThread=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/forum#FrmThread");
    public static final org.semanticwb.platform.SemanticProperty frm_postThread=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/forum#postThread");
    public static final org.semanticwb.platform.SemanticProperty frm_postBody=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/forum#postBody");
    public static final org.semanticwb.platform.SemanticProperty swb_active=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#active");
    public static final org.semanticwb.platform.SemanticClass frm_FrmForum=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/forum#FrmForum");
    public static final org.semanticwb.platform.SemanticProperty frm_postForum=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/forum#postForum");
    public static final org.semanticwb.platform.SemanticClass frm_FrmAttachments=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/forum#FrmAttachment");
    public static final org.semanticwb.platform.SemanticProperty frm_hasAttachments=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/forum#hasAttachments");
    public static final org.semanticwb.platform.SemanticProperty swb_created=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#created");
    public static final org.semanticwb.platform.SemanticClass swb_User=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#User");
    public static final org.semanticwb.platform.SemanticProperty swb_modifiedBy=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#modifiedBy");
    public static final org.semanticwb.platform.SemanticProperty swb_iconClass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#iconClass");
    public static final org.semanticwb.platform.SemanticProperty swb_updated=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#updated");
    public static final org.semanticwb.platform.SemanticClass frm_FrmPost=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/forum#FrmPost");
    public static final org.semanticwb.platform.SemanticProperty frm_postParentPost=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/forum#postParentPost");
    public static final org.semanticwb.platform.SemanticProperty swb_creator=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#creator");
    public static final org.semanticwb.platform.SemanticProperty frm_haschildPost=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/forum#haschildPost");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/forum#FrmPost");

    public FrmPostBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public static org.semanticwb.forum.FrmPost getFrmPost(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.forum.FrmPost)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
    }

    public static java.util.Iterator<org.semanticwb.forum.FrmPost> listFrmPosts(org.semanticwb.model.SWBModel model)
    {
        java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
        return new org.semanticwb.model.GenericIterator<org.semanticwb.forum.FrmPost>(org.semanticwb.forum.FrmPost.class, it, true);
    }

    public static java.util.Iterator<org.semanticwb.forum.FrmPost> listFrmPosts()
    {
        java.util.Iterator it=sclass.listInstances();
        return new org.semanticwb.model.GenericIterator<org.semanticwb.forum.FrmPost>(org.semanticwb.forum.FrmPost.class, it, true);
    }

    public static org.semanticwb.forum.FrmPost createFrmPost(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.forum.FrmPost)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
    }

    public static org.semanticwb.forum.FrmPost createFrmPost(org.semanticwb.model.SWBModel model)
    {
        long id=model.getSemanticObject().getModel().getCounter(sclass);
        return org.semanticwb.forum.FrmPost.createFrmPost(String.valueOf(id), model);
    }

    public static void removeFrmPost(String id, org.semanticwb.model.SWBModel model)
    {
        model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
    }

    public static boolean hasFrmPost(String id, org.semanticwb.model.SWBModel model)
    {
        return (getFrmPost(id, model)!=null);
    }

    public void setThread(org.semanticwb.forum.FrmThread frmthread)
    {
        getSemanticObject().setObjectProperty(frm_postThread, frmthread.getSemanticObject());
    }

    public void removeThread()
    {
        getSemanticObject().removeProperty(frm_postThread);
    }

    public org.semanticwb.forum.FrmThread getThread()
    {
         org.semanticwb.forum.FrmThread ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(frm_postThread);
         if(obj!=null)
         {
             ret=(org.semanticwb.forum.FrmThread)obj.createGenericInstance();
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

    public void setForum(org.semanticwb.forum.FrmForum frmforum)
    {
        getSemanticObject().setObjectProperty(frm_postForum, frmforum.getSemanticObject());
    }

    public void removeForum()
    {
        getSemanticObject().removeProperty(frm_postForum);
    }

    public org.semanticwb.forum.FrmForum getForum()
    {
         org.semanticwb.forum.FrmForum ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(frm_postForum);
         if(obj!=null)
         {
             ret=(org.semanticwb.forum.FrmForum)obj.createGenericInstance();
         }
         return ret;
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.forum.FrmAttachments> listAttachmentss()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.forum.FrmAttachments>(org.semanticwb.forum.FrmAttachments.class, getSemanticObject().listObjectProperties(frm_hasAttachments));
    }

    public boolean hasAttachments(org.semanticwb.forum.FrmAttachments frmattachments)
    {
        if(frmattachments==null)return false;        return getSemanticObject().hasObjectProperty(frm_hasAttachments,frmattachments.getSemanticObject());
    }

    public org.semanticwb.forum.FrmAttachments getAttachments()
    {
         org.semanticwb.forum.FrmAttachments ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(frm_hasAttachments);
         if(obj!=null)
         {
             ret=(org.semanticwb.forum.FrmAttachments)obj.createGenericInstance();
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

    public void setParentPost(org.semanticwb.forum.FrmPost frmpost)
    {
        getSemanticObject().setObjectProperty(frm_postParentPost, frmpost.getSemanticObject());
    }

    public void removeParentPost()
    {
        getSemanticObject().removeProperty(frm_postParentPost);
    }

    public org.semanticwb.forum.FrmPost getParentPost()
    {
         org.semanticwb.forum.FrmPost ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(frm_postParentPost);
         if(obj!=null)
         {
             ret=(org.semanticwb.forum.FrmPost)obj.createGenericInstance();
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

    public org.semanticwb.model.GenericIterator<org.semanticwb.forum.FrmPost> listchildPosts()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.forum.FrmPost>(org.semanticwb.forum.FrmPost.class, getSemanticObject().listObjectProperties(frm_haschildPost));
    }

    public boolean haschildPost(org.semanticwb.forum.FrmPost frmpost)
    {
        if(frmpost==null)return false;        return getSemanticObject().hasObjectProperty(frm_haschildPost,frmpost.getSemanticObject());
    }

    public org.semanticwb.forum.FrmPost getchildPost()
    {
         org.semanticwb.forum.FrmPost ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(frm_haschildPost);
         if(obj!=null)
         {
             ret=(org.semanticwb.forum.FrmPost)obj.createGenericInstance();
         }
         return ret;
    }

    public org.semanticwb.model.WebSite getWebSite()
    {
        return (org.semanticwb.model.WebSite)getSemanticObject().getModel().getModelObject().createGenericInstance();
    }
}
