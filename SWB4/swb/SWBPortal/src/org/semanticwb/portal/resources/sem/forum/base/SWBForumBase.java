package org.semanticwb.portal.resources.sem.forum.base;


public abstract class SWBForumBase extends org.semanticwb.portal.api.GenericSemResource 
{
       public static final org.semanticwb.platform.SemanticClass frm_Thread=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwb.org/SWBForum#Thread");
       public static final org.semanticwb.platform.SemanticProperty frm_hasThreads=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwb.org/SWBForum#hasThreads");
       public static final org.semanticwb.platform.SemanticProperty frm_acceptGuessUsers=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwb.org/SWBForum#acceptGuessUsers");
       public static final org.semanticwb.platform.SemanticClass frm_SWBForum=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwb.org/SWBForum#SWBForum");
       public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwb.org/SWBForum#SWBForum");

    public SWBForumBase()
    {
    }

    public SWBForumBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Thread> listThreadses()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Thread>(getSemanticObject().listObjectProperties(frm_hasThreads));
    }

    public boolean hasThreads(org.semanticwb.portal.resources.sem.forum.Thread thread)
    {
        if(thread==null)return false;
        return getSemanticObject().hasObjectProperty(frm_hasThreads,thread.getSemanticObject());
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

    public boolean isAcceptGuessUsers()
    {
        return getSemanticObject().getBooleanProperty(frm_acceptGuessUsers);
    }

    public void setAcceptGuessUsers(boolean value)
    {
        getSemanticObject().setBooleanProperty(frm_acceptGuessUsers, value);
    }
}
