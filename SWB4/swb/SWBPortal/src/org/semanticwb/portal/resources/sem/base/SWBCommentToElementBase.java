package org.semanticwb.portal.resources.sem.base;


public class SWBCommentToElementBase extends org.semanticwb.portal.api.GenericSemResource 
{
    public static final org.semanticwb.platform.SemanticClass swb_res_cmts_CommentToElement=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/portal/resources/sems/SWBCommentToElement#CommentToElement");
    public static final org.semanticwb.platform.SemanticProperty swb_res_cmts_hasComment=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/portal/resources/sems/SWBCommentToElement#hasComment");
    public static final org.semanticwb.platform.SemanticClass swb_res_cmts_SWBCommentToElement=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/portal/resources/sems/SWBCommentToElement#SWBCommentToElement");

    public SWBCommentToElementBase()
    {
    }

    public SWBCommentToElementBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/portal/resources/sems/SWBCommentToElement#SWBCommentToElement");

    public org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.CommentToElement> listComments()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.CommentToElement>(getSemanticObject().listObjectProperties(swb_res_cmts_hasComment));
    }

    public boolean hasComment(org.semanticwb.portal.resources.sem.CommentToElement commenttoelement)
    {
        if(commenttoelement==null)return false;
        return getSemanticObject().hasObjectProperty(swb_res_cmts_hasComment,commenttoelement.getSemanticObject());
    }

    public void addComment(org.semanticwb.portal.resources.sem.CommentToElement commenttoelement)
    {
        getSemanticObject().addObjectProperty(swb_res_cmts_hasComment, commenttoelement.getSemanticObject());
    }

    public void removeAllComment()
    {
        getSemanticObject().removeProperty(swb_res_cmts_hasComment);
    }

    public void removeComment(org.semanticwb.portal.resources.sem.CommentToElement commenttoelement)
    {
        getSemanticObject().removeObjectProperty(swb_res_cmts_hasComment,commenttoelement.getSemanticObject());
    }

    public org.semanticwb.portal.resources.sem.CommentToElement getComment()
    {
         org.semanticwb.portal.resources.sem.CommentToElement ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_res_cmts_hasComment);
         if(obj!=null)
         {
             ret=(org.semanticwb.portal.resources.sem.CommentToElement)obj.createGenericInstance();
         }
         return ret;
    }
}
