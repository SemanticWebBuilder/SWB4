package org.semanticwb.portal.resources.sem.forum.base;


public class ThreadBase extends org.semanticwb.model.WebPage implements org.semanticwb.model.Resourceable,org.semanticwb.model.Filterable,org.semanticwb.model.Referensable,org.semanticwb.model.Descriptiveable,org.semanticwb.model.TemplateRefable,org.semanticwb.model.RuleRefable,org.semanticwb.model.PFlowRefable,org.semanticwb.model.Trashable,org.semanticwb.model.Calendarable,org.semanticwb.model.Indexable,org.semanticwb.model.RoleRefable,org.semanticwb.model.Undeleteable,org.semanticwb.model.UserGroupRefable,org.semanticwb.model.Traceable,org.semanticwb.model.Hiddenable,org.semanticwb.model.Activeable,org.semanticwb.model.Viewable
{
    public static final org.semanticwb.platform.SemanticProperty frm_thIcon=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/forum#thIcon");
    public static final org.semanticwb.platform.SemanticProperty frm_thViewcount=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/forum#thViewcount");
    public static final org.semanticwb.platform.SemanticClass swb_User=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#User");
    public static final org.semanticwb.platform.SemanticProperty frm_thlastpostuser=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/forum#thlastpostuser");
    public static final org.semanticwb.platform.SemanticProperty frm_thuser=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/forum#thuser");
    public static final org.semanticwb.platform.SemanticProperty frm_thAttachCount=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/forum#thAttachCount");
    public static final org.semanticwb.platform.SemanticClass frm_ThreadTypeCat=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/forum#ThreadTypeCat");
    public static final org.semanticwb.platform.SemanticProperty frm_thType=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/forum#thType");
    public static final org.semanticwb.platform.SemanticProperty frm_thReplyCount=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/forum#thReplyCount");
    public static final org.semanticwb.platform.SemanticClass frm_UserFavThread=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/forum#UserFavThread");
    public static final org.semanticwb.platform.SemanticProperty frm_hasFavThreads=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/forum#hasFavThreads");
    public static final org.semanticwb.platform.SemanticProperty frm_thBody=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/forum#thBody");
    public static final org.semanticwb.platform.SemanticClass frm_SWBForum=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/forum#SWBForum");
    public static final org.semanticwb.platform.SemanticProperty frm_thforum=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/forum#thforum");
    public static final org.semanticwb.platform.SemanticClass frm_PriorityCat=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/forum#PriorityCat");
    public static final org.semanticwb.platform.SemanticProperty frm_thPriority=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/forum#thPriority");
    public static final org.semanticwb.platform.SemanticClass frm_Post=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/forum#Post");
    public static final org.semanticwb.platform.SemanticProperty frm_hasPost=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/forum#hasPost");
    public static final org.semanticwb.platform.SemanticProperty frm_thlastpostdate=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/forum#thlastpostdate");
    public static final org.semanticwb.platform.SemanticClass frm_Thread=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/forum#Thread");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/forum#Thread");

    public ThreadBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.Thread> listThreads(org.semanticwb.model.SWBModel model)
    {
        java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
        return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Thread>(it, true);
    }

    public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.Thread> listThreads()
    {
        java.util.Iterator it=sclass.listInstances();
        return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Thread>(it, true);
    }

    public static org.semanticwb.portal.resources.sem.forum.Thread getThread(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.portal.resources.sem.forum.Thread)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
    }

    public static org.semanticwb.portal.resources.sem.forum.Thread createThread(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.portal.resources.sem.forum.Thread)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
    }

    public static void removeThread(String id, org.semanticwb.model.SWBModel model)
    {
        model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
    }

    public static boolean hasThread(String id, org.semanticwb.model.SWBModel model)
    {
        return (getThread(id, model)!=null);
    }

    public String getIcon()
    {
        return getSemanticObject().getProperty(frm_thIcon);
    }

    public void setIcon(String thIcon)
    {
        getSemanticObject().setProperty(frm_thIcon, thIcon);
    }

    public int getViewcount()
    {
        return getSemanticObject().getIntProperty(frm_thViewcount);
    }

    public void setViewcount(int thViewcount)
    {
        getSemanticObject().setLongProperty(frm_thViewcount, thViewcount);
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

    public void setType(org.semanticwb.portal.resources.sem.forum.ThreadTypeCat threadtypecat)
    {
        getSemanticObject().setObjectProperty(frm_thType, threadtypecat.getSemanticObject());
    }

    public void removeType()
    {
        getSemanticObject().removeProperty(frm_thType);
    }

    public org.semanticwb.portal.resources.sem.forum.ThreadTypeCat getType()
    {
         org.semanticwb.portal.resources.sem.forum.ThreadTypeCat ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(frm_thType);
         if(obj!=null)
         {
             ret=(org.semanticwb.portal.resources.sem.forum.ThreadTypeCat)obj.createGenericInstance();
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

    public org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.UserFavThread> listFavThreadss()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.UserFavThread>(getSemanticObject().listObjectProperties(frm_hasFavThreads));
    }

    public boolean hasFavThreads(org.semanticwb.portal.resources.sem.forum.UserFavThread userfavthread)
    {
        if(userfavthread==null)return false;        return getSemanticObject().hasObjectProperty(frm_hasFavThreads,userfavthread.getSemanticObject());
    }

    public org.semanticwb.portal.resources.sem.forum.UserFavThread getFavThreads()
    {
         org.semanticwb.portal.resources.sem.forum.UserFavThread ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(frm_hasFavThreads);
         if(obj!=null)
         {
             ret=(org.semanticwb.portal.resources.sem.forum.UserFavThread)obj.createGenericInstance();
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

    public void setForum(org.semanticwb.portal.resources.sem.forum.SWBForum swbforum)
    {
        getSemanticObject().setObjectProperty(frm_thforum, swbforum.getSemanticObject());
    }

    public void removeForum()
    {
        getSemanticObject().removeProperty(frm_thforum);
    }

    public org.semanticwb.portal.resources.sem.forum.SWBForum getForum()
    {
         org.semanticwb.portal.resources.sem.forum.SWBForum ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(frm_thforum);
         if(obj!=null)
         {
             ret=(org.semanticwb.portal.resources.sem.forum.SWBForum)obj.createGenericInstance();
         }
         return ret;
    }

    public void setThPriority(org.semanticwb.portal.resources.sem.forum.PriorityCat prioritycat)
    {
        getSemanticObject().setObjectProperty(frm_thPriority, prioritycat.getSemanticObject());
    }

    public void removeThPriority()
    {
        getSemanticObject().removeProperty(frm_thPriority);
    }

    public org.semanticwb.portal.resources.sem.forum.PriorityCat getThPriority()
    {
         org.semanticwb.portal.resources.sem.forum.PriorityCat ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(frm_thPriority);
         if(obj!=null)
         {
             ret=(org.semanticwb.portal.resources.sem.forum.PriorityCat)obj.createGenericInstance();
         }
         return ret;
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Post> listPosts()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Post>(getSemanticObject().listObjectProperties(frm_hasPost));
    }

    public boolean hasPost(org.semanticwb.portal.resources.sem.forum.Post post)
    {
        if(post==null)return false;        return getSemanticObject().hasObjectProperty(frm_hasPost,post.getSemanticObject());
    }

    public org.semanticwb.portal.resources.sem.forum.Post getPost()
    {
         org.semanticwb.portal.resources.sem.forum.Post ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(frm_hasPost);
         if(obj!=null)
         {
             ret=(org.semanticwb.portal.resources.sem.forum.Post)obj.createGenericInstance();
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
}
