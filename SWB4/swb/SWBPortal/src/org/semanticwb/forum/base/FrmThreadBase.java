package org.semanticwb.forum.base;


public class FrmThreadBase extends org.semanticwb.model.WebPage implements org.semanticwb.model.RoleRefable,org.semanticwb.model.Filterable,org.semanticwb.model.Referensable,org.semanticwb.model.Trashable,org.semanticwb.model.Indexable,org.semanticwb.model.Descriptiveable,org.semanticwb.model.UserGroupRefable,org.semanticwb.model.Traceable,org.semanticwb.model.Activeable,org.semanticwb.model.Viewable,org.semanticwb.model.Resourceable,org.semanticwb.model.PFlowRefable,org.semanticwb.model.Hiddenable,org.semanticwb.model.Undeleteable,org.semanticwb.model.Calendarable,org.semanticwb.model.RuleRefable,org.semanticwb.model.TemplateRefable
{
    public static final org.semanticwb.platform.SemanticProperty frm_thIcon=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/forum#thIcon");
    public static final org.semanticwb.platform.SemanticClass swb_User=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#User");
    public static final org.semanticwb.platform.SemanticProperty frm_thlastpostuser=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/forum#thlastpostuser");
    public static final org.semanticwb.platform.SemanticProperty frm_thuser=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/forum#thuser");
    public static final org.semanticwb.platform.SemanticProperty frm_thAttachCount=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/forum#thAttachCount");
    public static final org.semanticwb.platform.SemanticClass frm_FrmThreadTypeCat=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/forum#FrmThreadTypeCat");
    public static final org.semanticwb.platform.SemanticProperty frm_thType=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/forum#thType");
    public static final org.semanticwb.platform.SemanticProperty frm_thReplyCount=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/forum#thReplyCount");
    public static final org.semanticwb.platform.SemanticClass frm_FrmUserThread=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/forum#FrmUserFavThread");
    public static final org.semanticwb.platform.SemanticProperty frm_hasFavThreads=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/forum#hasFavThreads");
    public static final org.semanticwb.platform.SemanticProperty frm_thBody=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/forum#thBody");
    public static final org.semanticwb.platform.SemanticProperty frm_thViewcount=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/forum#thViewcount");
    public static final org.semanticwb.platform.SemanticClass frm_FrmForum=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/forum#FrmForum");
    public static final org.semanticwb.platform.SemanticProperty frm_thforum=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/forum#thforum");
    public static final org.semanticwb.platform.SemanticClass frm_FrmPriorityCat=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/forum#FrmPriorityCat");
    public static final org.semanticwb.platform.SemanticProperty frm_thPriority=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/forum#thPriority");
    public static final org.semanticwb.platform.SemanticClass frm_FrmPost=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/forum#FrmPost");
    public static final org.semanticwb.platform.SemanticProperty frm_hasPost=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/forum#hasPost");
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

    public String getIcon()
    {
        return getSemanticObject().getProperty(frm_thIcon);
    }

    public void setIcon(String thIcon)
    {
        getSemanticObject().setProperty(frm_thIcon, thIcon);
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
             ret=(org.semanticwb.model.User)obj.createGenericInstance();
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
             ret=(org.semanticwb.model.User)obj.createGenericInstance();
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

    public void setType(org.semanticwb.portal.resources.sem.forum.FrmThreadTypeCat frmthreadtypecat)
    {
        getSemanticObject().setObjectProperty(frm_thType, frmthreadtypecat.getSemanticObject());
    }

    public void removeType()
    {
        getSemanticObject().removeProperty(frm_thType);
    }

    public org.semanticwb.portal.resources.sem.forum.FrmThreadTypeCat getType()
    {
         org.semanticwb.portal.resources.sem.forum.FrmThreadTypeCat ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(frm_thType);
         if(obj!=null)
         {
             ret=(org.semanticwb.portal.resources.sem.forum.FrmThreadTypeCat)obj.createGenericInstance();
         }
         return ret;
    }

    public int getReplyCount()
    {
        return getSemanticObject().getIntProperty(frm_thReplyCount);
    }

    public void setReplyCount(int thReplyCount)
    {
        getSemanticObject().setLongProperty(frm_thReplyCount, thReplyCount);
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.FrmUserThread> listFavThreadss()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.FrmUserThread>(org.semanticwb.portal.resources.sem.forum.FrmUserThread.class, getSemanticObject().listObjectProperties(frm_hasFavThreads));
    }

    public boolean hasFavThreads(org.semanticwb.portal.resources.sem.forum.FrmUserThread frmuserthread)
    {
        if(frmuserthread==null)return false;        return getSemanticObject().hasObjectProperty(frm_hasFavThreads,frmuserthread.getSemanticObject());
    }

    public org.semanticwb.portal.resources.sem.forum.FrmUserThread getFavThreads()
    {
         org.semanticwb.portal.resources.sem.forum.FrmUserThread ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(frm_hasFavThreads);
         if(obj!=null)
         {
             ret=(org.semanticwb.portal.resources.sem.forum.FrmUserThread)obj.createGenericInstance();
         }
         return ret;
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

    public void setForum(org.semanticwb.portal.resources.sem.forum.FrmForum frmforum)
    {
        getSemanticObject().setObjectProperty(frm_thforum, frmforum.getSemanticObject());
    }

    public void removeForum()
    {
        getSemanticObject().removeProperty(frm_thforum);
    }

    public org.semanticwb.portal.resources.sem.forum.FrmForum getForum()
    {
         org.semanticwb.portal.resources.sem.forum.FrmForum ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(frm_thforum);
         if(obj!=null)
         {
             ret=(org.semanticwb.portal.resources.sem.forum.FrmForum)obj.createGenericInstance();
         }
         return ret;
    }

    public void setThPriority(ororg.semanticwb.portal.resources.sem.forum.FrmPriorityCat frmprioritycat)
    {
        getSemanticObject().setObjectProperty(frm_thPriority, frmprioritycat.getSemanticObject());
    }

    public void removeThPriority()
    {
        getSemanticObject().removeProperty(frm_thPriority);
    }

    public ororg.semanticwb.portal.resources.sem.forum.FrmPriorityCat getThPriority()
    {
         ororg.semanticwb.portal.resources.sem.forum.FrmPriorityCat ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(frm_thPriority);
         if(obj!=null)
         {
             ret=(ororg.semanticwb.portal.resources.sem.forum.FrmPriorityCat)obj.createGenericInstance();
         }
         return ret;
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.FrmPost> listPosts()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.FrmPost>(org.semanticwb.portal.resources.sem.forum.FrmPost.class, getSemanticObject().listObjectProperties(frm_hasPost));
    }

    public boolean hasPost(org.semanticwb.portal.resources.sem.forum.FrmPost frmpost)
    {
        if(frmpost==null)return false;        return getSemanticObject().hasObjectProperty(frm_hasPost,frmpost.getSemanticObject());
    }

    public org.semanticwb.portal.resources.sem.forum.FrmPost getPost()
    {
         org.semanticwb.portal.resources.sem.forum.FrmPost ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(frm_hasPost);
         if(obj!=null)
         {
             ret=(org.semanticwb.portal.resources.sem.forum.FrmPost)obj.createGenericInstance();
         }
         return ret;
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
        return (org.semanticwb.model.WebSite)getSemanticObject().getModel().getModelObject().createGenericInstance();
    }
}
