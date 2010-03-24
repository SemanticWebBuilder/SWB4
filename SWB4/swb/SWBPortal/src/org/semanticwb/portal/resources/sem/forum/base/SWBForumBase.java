package org.semanticwb.portal.resources.sem.forum.base;


public abstract class SWBForumBase extends org.semanticwb.portal.api.GenericSemResource 
{
    public static final org.semanticwb.platform.SemanticProperty frm_acceptGuessUsers=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwb.org/SWBForum#acceptGuessUsers");
    public static final org.semanticwb.platform.SemanticProperty frm_notifyThreadCreation=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwb.org/SWBForum#notifyThreadCreation");
    public static final org.semanticwb.platform.SemanticClass swb_Role=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Role");
    public static final org.semanticwb.platform.SemanticProperty frm_adminRole=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwb.org/SWBForum#adminRole");
    public static final org.semanticwb.platform.SemanticClass frm_Thread=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwb.org/SWBForum#Thread");
    public static final org.semanticwb.platform.SemanticProperty frm_hasThreads=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwb.org/SWBForum#hasThreads");
    public static final org.semanticwb.platform.SemanticClass frm_SWBForum=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwb.org/SWBForum#SWBForum");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwb.org/SWBForum#SWBForum");

    public SWBForumBase()
    {
    }

    public SWBForumBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public boolean isAcceptGuessUsers()
    {
        return getSemanticObject().getBooleanProperty(frm_acceptGuessUsers);
    }

    public void setAcceptGuessUsers(boolean value)
    {
        getSemanticObject().setBooleanProperty(frm_acceptGuessUsers, value);
    }

    public boolean isNotifyThreadCreation()
    {
        return getSemanticObject().getBooleanProperty(frm_notifyThreadCreation);
    }

    public void setNotifyThreadCreation(boolean value)
    {
        getSemanticObject().setBooleanProperty(frm_notifyThreadCreation, value);
    }

    public void setAdminRole(org.semanticwb.model.Role value)
    {
        getSemanticObject().setObjectProperty(frm_adminRole, value.getSemanticObject());
    }

    public void removeAdminRole()
    {
        getSemanticObject().removeProperty(frm_adminRole);
    }

    public org.semanticwb.model.Role getAdminRole()
    {
         org.semanticwb.model.Role ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(frm_adminRole);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.Role)obj.createGenericInstance();
         }
         return ret;
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Thread> listThreadses()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Thread>(getSemanticObject().listObjectProperties(frm_hasThreads));
    }

    public boolean hasThreads(org.semanticwb.portal.resources.sem.forum.Thread thread)
    {
        boolean ret=false;
        if(thread!=null)
        {
           ret=getSemanticObject().hasObjectProperty(frm_hasThreads,thread.getSemanticObject());
        }
        return ret;
    }

    public org.semanticwb.portal.resources.sem.forum.Thread getThreads()
    {
         org.semanticwb.portal.resources.sem.forum.Thread ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(frm_hasThreads);
         if(obj!=null)
         {
             ret=(org.semanticwb.portal.resources.sem.forum.Thread)obj.createGenericInstance();
         }
         return ret;
    }
}
