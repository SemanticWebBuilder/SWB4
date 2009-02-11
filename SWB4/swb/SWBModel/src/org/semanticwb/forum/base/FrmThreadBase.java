package org.semanticwb.forum.base;


public class FrmThreadBase extends org.semanticwb.model.SWBClass implements org.semanticwb.model.Iconable,org.semanticwb.model.Traceable,org.semanticwb.model.Activeable,org.semanticwb.model.Descriptiveable
{
    public static final org.semanticwb.platform.SemanticClass swb_User=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#User");
    public static final org.semanticwb.platform.SemanticProperty frm_thlastpostuser=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/forum#thlastpostuser");
    public static final org.semanticwb.platform.SemanticProperty frm_thuser=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/forum#thuser");
    public static final org.semanticwb.platform.SemanticProperty frm_thAttachCount=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/forum#thAttachCount");
    public static final org.semanticwb.platform.SemanticClass frm_FrmThreadTypeCat=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/forum#FrmThreadTypeCat");
    public static final org.semanticwb.platform.SemanticProperty frm_thType=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/forum#thType");
    public static final org.semanticwb.platform.SemanticProperty swb_active=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#active");
    public static final org.semanticwb.platform.SemanticClass frm_FrmFavoriteThread=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/forum#FrmFavoriteThread");
    public static final org.semanticwb.platform.SemanticProperty frm_hasFavThread=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/forum#hasFavThread");
    public static final org.semanticwb.platform.SemanticProperty swb_created=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#created");
    public static final org.semanticwb.platform.SemanticProperty swb_modifiedBy=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#modifiedBy");
    public static final org.semanticwb.platform.SemanticProperty swb_title=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#title");
    public static final org.semanticwb.platform.SemanticProperty swb_iconClass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#iconClass");
    public static final org.semanticwb.platform.SemanticProperty swb_updated=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#updated");
    public static final org.semanticwb.platform.SemanticProperty frm_thReplyCount=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/forum#thReplyCount");
    public static final org.semanticwb.platform.SemanticProperty frm_thBody=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/forum#thBody");
    public static final org.semanticwb.platform.SemanticProperty frm_thViewcount=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/forum#thViewcount");
    public static final org.semanticwb.platform.SemanticClass frm_FrmForum=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/forum#FrmForum");
    public static final org.semanticwb.platform.SemanticProperty frm_thforum=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/forum#thforum");
    public static final org.semanticwb.platform.SemanticClass frm_FrmPriorityCat=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/forum#FrmPriorityCat");
    public static final org.semanticwb.platform.SemanticProperty frm_thPriority=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/forum#thPriority");
    public static final org.semanticwb.platform.SemanticClass frm_FrmPost=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/forum#FrmPost");
    public static final org.semanticwb.platform.SemanticProperty frm_hasPost=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/forum#hasPost");
    public static final org.semanticwb.platform.SemanticProperty swb_creator=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#creator");
    public static final org.semanticwb.platform.SemanticProperty swb_description=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#description");
    public static final org.semanticwb.platform.SemanticProperty frm_thlastpostdate=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/forum#thlastpostdate");
    public static final org.semanticwb.platform.SemanticClass frm_FrmThread=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/forum#FrmThread");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/forum#FrmThread");

    public FrmThreadBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public static org.semanticwb.forum.FrmThread getFrmThread(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.forum.FrmThread)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
    }

    public static java.util.Iterator<org.semanticwb.forum.FrmThread> listFrmThreads(org.semanticwb.model.SWBModel model)
    {
        java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
        return new org.semanticwb.model.GenericIterator<org.semanticwb.forum.FrmThread>(org.semanticwb.forum.FrmThread.class, it, true);
    }

    public static java.util.Iterator<org.semanticwb.forum.FrmThread> listFrmThreads()
    {
        java.util.Iterator it=sclass.listInstances();
        return new org.semanticwb.model.GenericIterator<org.semanticwb.forum.FrmThread>(org.semanticwb.forum.FrmThread.class, it, true);
    }

    public static org.semanticwb.forum.FrmThread createFrmThread(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.forum.FrmThread)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
    }

    public static org.semanticwb.forum.FrmThread createFrmThread(org.semanticwb.model.SWBModel model)
    {
        long id=model.getSemanticObject().getModel().getCounter(sclass);
        return org.semanticwb.forum.FrmThread.createFrmThread(String.valueOf(id), model);
    }

    public static void removeFrmThread(String id, org.semanticwb.model.SWBModel model)
    {
        model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
    }

    public static boolean hasFrmThread(String id, org.semanticwb.model.SWBModel model)
    {
        return (getFrmThread(id, model)!=null);
    }

    public void setLastpostmember(org.semanticwb.model.User user)
    {
        getSemanticObject().setObjectProperty(frm_thlastpostuser, user.getSemanticObject());
    }

    public void removeLastpostmember()
    {
        getSemanticObject().removeProperty(frm_thlastpostuser);
    }

    public org.semanticwb.model.User getLastpostmember()
    {
         org.semanticwb.model.User ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(frm_thlastpostuser);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.User)obj.getSemanticClass().newGenericInstance(obj);
         }
         return ret;
    }

    public void setUser(org.semanticwb.model.User user)
    {
        getSemanticObject().setObjectProperty(frm_thuser, user.getSemanticObject());
    }

    public void removeUser()
    {
        getSemanticObject().removeProperty(frm_thuser);
    }

    public org.semanticwb.model.User getUser()
    {
         org.semanticwb.model.User ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(frm_thuser);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.User)obj.getSemanticClass().newGenericInstance(obj);
         }
         return ret;
    }

    public int getAttachCount()
    {
        return getSemanticObject().getIntProperty(frm_thAttachCount);
    }

    public void setAttachCount(int thAttachCount)
    {
        getSemanticObject().setLongProperty(frm_thAttachCount, thAttachCount);
    }

    public void setType(org.semanticwb.forum.FrmThreadTypeCat frmthreadtypecat)
    {
        getSemanticObject().setObjectProperty(frm_thType, frmthreadtypecat.getSemanticObject());
    }

    public void removeType()
    {
        getSemanticObject().removeProperty(frm_thType);
    }

    public org.semanticwb.forum.FrmThreadTypeCat getType()
    {
         org.semanticwb.forum.FrmThreadTypeCat ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(frm_thType);
         if(obj!=null)
         {
             ret=(org.semanticwb.forum.FrmThreadTypeCat)obj.getSemanticClass().newGenericInstance(obj);
         }
         return ret;
    }

    public boolean isActive()
    {
        return getSemanticObject().getBooleanProperty(swb_active);
    }

    public void setActive(boolean active)
    {
        getSemanticObject().setBooleanProperty(swb_active, active);
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.forum.FrmFavoriteThread> listFavThreads()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.forum.FrmFavoriteThread>(org.semanticwb.forum.FrmFavoriteThread.class, getSemanticObject().listObjectProperties(frm_hasFavThread));
    }

    public boolean hasFavThread(org.semanticwb.forum.FrmFavoriteThread frmfavoritethread)
    {
        if(frmfavoritethread==null)return false;        return getSemanticObject().hasObjectProperty(frm_hasFavThread,frmfavoritethread.getSemanticObject());
    }

    public org.semanticwb.forum.FrmFavoriteThread getFavThread()
    {
         org.semanticwb.forum.FrmFavoriteThread ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(frm_hasFavThread);
         if(obj!=null)
         {
             ret=(org.semanticwb.forum.FrmFavoriteThread)obj.getSemanticClass().newGenericInstance(obj);
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
             ret=(org.semanticwb.model.User)obj.getSemanticClass().newGenericInstance(obj);
         }
         return ret;
    }

    public String getTitle()
    {
        return getSemanticObject().getProperty(swb_title);
    }

    public void setTitle(String title)
    {
        getSemanticObject().setProperty(swb_title, title);
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

    public int getReplyCount()
    {
        return getSemanticObject().getIntProperty(frm_thReplyCount);
    }

    public void setReplyCount(int thReplyCount)
    {
        getSemanticObject().setLongProperty(frm_thReplyCount, thReplyCount);
    }

    public String getBody()
    {
        return getSemanticObject().getProperty(frm_thBody);
    }

    public void setBody(String thBody)
    {
        getSemanticObject().setProperty(frm_thBody, thBody);
    }

    public int getViewcount()
    {
        return getSemanticObject().getIntProperty(frm_thViewcount);
    }

    public void setViewcount(int thViewcount)
    {
        getSemanticObject().setLongProperty(frm_thViewcount, thViewcount);
    }

    public void setForum(org.semanticwb.forum.FrmForum frmforum)
    {
        getSemanticObject().setObjectProperty(frm_thforum, frmforum.getSemanticObject());
    }

    public void removeForum()
    {
        getSemanticObject().removeProperty(frm_thforum);
    }

    public org.semanticwb.forum.FrmForum getForum()
    {
         org.semanticwb.forum.FrmForum ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(frm_thforum);
         if(obj!=null)
         {
             ret=(org.semanticwb.forum.FrmForum)obj.getSemanticClass().newGenericInstance(obj);
         }
         return ret;
    }

    public void setThPriority(org.semanticwb.forum.FrmPriorityCat frmprioritycat)
    {
        getSemanticObject().setObjectProperty(frm_thPriority, frmprioritycat.getSemanticObject());
    }

    public void removeThPriority()
    {
        getSemanticObject().removeProperty(frm_thPriority);
    }

    public org.semanticwb.forum.FrmPriorityCat getThPriority()
    {
         org.semanticwb.forum.FrmPriorityCat ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(frm_thPriority);
         if(obj!=null)
         {
             ret=(org.semanticwb.forum.FrmPriorityCat)obj.getSemanticClass().newGenericInstance(obj);
         }
         return ret;
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.forum.FrmPost> listPosts()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.forum.FrmPost>(org.semanticwb.forum.FrmPost.class, getSemanticObject().listObjectProperties(frm_hasPost));
    }

    public boolean hasPost(org.semanticwb.forum.FrmPost frmpost)
    {
        if(frmpost==null)return false;        return getSemanticObject().hasObjectProperty(frm_hasPost,frmpost.getSemanticObject());
    }

    public org.semanticwb.forum.FrmPost getPost()
    {
         org.semanticwb.forum.FrmPost ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(frm_hasPost);
         if(obj!=null)
         {
             ret=(org.semanticwb.forum.FrmPost)obj.getSemanticClass().newGenericInstance(obj);
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
             ret=(org.semanticwb.model.User)obj.getSemanticClass().newGenericInstance(obj);
         }
         return ret;
    }

    public String getDescription()
    {
        return getSemanticObject().getProperty(swb_description);
    }

    public void setDescription(String description)
    {
        getSemanticObject().setProperty(swb_description, description);
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

    public java.util.Date getLastpostdate()
    {
        return getSemanticObject().getDateProperty(frm_thlastpostdate);
    }

    public void setLastpostdate(java.util.Date thlastpostdate)
    {
        getSemanticObject().setDateProperty(frm_thlastpostdate, thlastpostdate);
    }

    public org.semanticwb.model.WebSite getWebSite()
    {
        return new org.semanticwb.model.WebSite(getSemanticObject().getModel().getModelObject());
    }
}
