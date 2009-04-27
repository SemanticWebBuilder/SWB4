package org.semanticwb.portal.resources.sem.forum.base;


public class ThreadBase extends org.semanticwb.model.WebPage implements org.semanticwb.model.Resourceable,org.semanticwb.model.UserGroupRefable,org.semanticwb.model.Calendarable,org.semanticwb.model.RoleRefable,org.semanticwb.model.Filterable,org.semanticwb.model.PFlowRefable,org.semanticwb.model.TemplateRefable,org.semanticwb.model.Traceable,org.semanticwb.model.Trashable,org.semanticwb.model.Referensable,org.semanticwb.model.Undeleteable,org.semanticwb.model.Descriptiveable,org.semanticwb.model.Activeable,org.semanticwb.model.RuleRefable,org.semanticwb.model.Hiddenable,org.semanticwb.model.Rankable,org.semanticwb.model.Viewable,org.semanticwb.model.Indexable
{
    public static final org.semanticwb.platform.SemanticClass swb_User=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#User");
    public static final org.semanticwb.platform.SemanticProperty frm_thLastPostMember=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwb.org/SWBForum#thLastPostMember");
    public static final org.semanticwb.platform.SemanticClass frm_SWBForum=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwb.org/SWBForum#SWBForum");
    public static final org.semanticwb.platform.SemanticProperty frm_thForum=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwb.org/SWBForum#thForum");
    public static final org.semanticwb.platform.SemanticProperty frm_thViewCount=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwb.org/SWBForum#thViewCount");
    public static final org.semanticwb.platform.SemanticProperty frm_thReplyCount=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwb.org/SWBForum#thReplyCount");
    public static final org.semanticwb.platform.SemanticProperty frm_thLastPostDate=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwb.org/SWBForum#thLastPostDate");
    public static final org.semanticwb.platform.SemanticProperty frm_thBody=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwb.org/SWBForum#thBody");
    public static final org.semanticwb.platform.SemanticClass frm_Post=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwb.org/SWBForum#Post");
    public static final org.semanticwb.platform.SemanticProperty frm_hasPost=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwb.org/SWBForum#hasPost");
    public static final org.semanticwb.platform.SemanticClass frm_Thread=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwb.org/SWBForum#Thread");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwb.org/SWBForum#Thread");

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

    public static org.semanticwb.portal.resources.sem.forum.Thread createThread(org.semanticwb.model.SWBModel model)
    {
        long id=model.getSemanticObject().getModel().getCounter(sclass);
        return org.semanticwb.portal.resources.sem.forum.Thread.createThread(String.valueOf(id), model);
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

    public void setLastPostMember(org.semanticwb.model.User user)
    {
        getSemanticObject().setObjectProperty(frm_thLastPostMember, user.getSemanticObject());
    }

    public void removeLastPostMember()
    {
        getSemanticObject().removeProperty(frm_thLastPostMember);
    }

    public org.semanticwb.model.User getLastPostMember()
    {
         org.semanticwb.model.User ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(frm_thLastPostMember);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.User)obj.createGenericInstance();
         }
         return ret;
    }

    public void setForum(org.semanticwb.portal.resources.sem.forum.SWBForum swbforum)
    {
        getSemanticObject().setObjectProperty(frm_thForum, swbforum.getSemanticObject());
    }

    public void removeForum()
    {
        getSemanticObject().removeProperty(frm_thForum);
    }

    public org.semanticwb.portal.resources.sem.forum.SWBForum getForum()
    {
         org.semanticwb.portal.resources.sem.forum.SWBForum ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(frm_thForum);
         if(obj!=null)
         {
             ret=(org.semanticwb.portal.resources.sem.forum.SWBForum)obj.createGenericInstance();
         }
         return ret;
    }

    public int getViewCount()
    {
        return getSemanticObject().getIntProperty(frm_thViewCount);
    }

    public void setViewCount(int thViewCount)
    {
        getSemanticObject().setLongProperty(frm_thViewCount, thViewCount);
    }

    public int getReplyCount()
    {
        return getSemanticObject().getIntProperty(frm_thReplyCount);
    }

    public void setReplyCount(int thReplyCount)
    {
        getSemanticObject().setLongProperty(frm_thReplyCount, thReplyCount);
    }

    public java.util.Date getLastPostDate()
    {
        return getSemanticObject().getDateProperty(frm_thLastPostDate);
    }

    public void setLastPostDate(java.util.Date thLastPostDate)
    {
        getSemanticObject().setDateProperty(frm_thLastPostDate, thLastPostDate);
    }

    public String getBody()
    {
        return getSemanticObject().getProperty(frm_thBody);
    }

    public void setBody(String thBody)
    {
        getSemanticObject().setProperty(frm_thBody, thBody);
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
}
