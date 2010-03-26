package org.semanticwb.resources.sem.forumcat.base;


public abstract class SWBForumCatResourceBase extends org.semanticwb.portal.api.GenericSemResource 
{
       public static final org.semanticwb.platform.SemanticClass forumCat_Forum=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#Forum");
       public static final org.semanticwb.platform.SemanticProperty forumCat_resForumInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#resForumInv");
       public static final org.semanticwb.platform.SemanticClass forumCat_SWBForumCatResource=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#SWBForumCatResource");
       public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#SWBForumCatResource");

    public SWBForumCatResourceBase()
    {
    }

    public SWBForumCatResourceBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public void setResForumInv(org.semanticwb.resources.sem.forumcat.Forum value)
    {
        getSemanticObject().setObjectProperty(forumCat_resForumInv, value.getSemanticObject());
    }

    public void removeResForumInv()
    {
        getSemanticObject().removeProperty(forumCat_resForumInv);
    }


    public org.semanticwb.resources.sem.forumcat.Forum getResForumInv()
    {
         org.semanticwb.resources.sem.forumcat.Forum ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(forumCat_resForumInv);
         if(obj!=null)
         {
             ret=(org.semanticwb.resources.sem.forumcat.Forum)obj.createGenericInstance();
         }
         return ret;
    }
}
