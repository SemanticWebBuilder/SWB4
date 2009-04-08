package org.semanticwb.portal.resources.sem.base;


public class SWBCommentBase extends org.semanticwb.portal.api.GenericSemResource 
{
    public static final org.semanticwb.platform.SemanticClass swbr_cmt_CmtComment=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/portal/resources/SWBComment#CmtComment");
    public static final org.semanticwb.platform.SemanticProperty swbr_cmt_hasComment=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/portal/resources/SWBComment#hasComment");
    public static final org.semanticwb.platform.SemanticClass swbr_cmt_SWBComment=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/portal/resources/SWBComment#SWBComment");

    public SWBCommentBase()
    {
    }

    public SWBCommentBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/portal/resources/SWBComment#SWBComment");

    public org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.CmtComment> listComments()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.CmtComment>(org.semanticwb.portal.resources.sem.CmtComment.class, getSemanticObject().listObjectProperties(swbr_cmt_hasComment));
    }

    public boolean hasComment(org.semanticwb.portal.resources.sem.CmtComment cmtcomment)
    {
        if(cmtcomment==null)return false;        return getSemanticObject().hasObjectProperty(swbr_cmt_hasComment,cmtcomment.getSemanticObject());
    }

    public void addComment(org.semanticwb.portal.resources.sem.CmtComment cmtcomment)
    {
        getSemanticObject().addObjectProperty(swbr_cmt_hasComment, cmtcomment.getSemanticObject());
    }

    public void removeAllComment()
    {
        getSemanticObject().removeProperty(swbr_cmt_hasComment);
    }

    public void removeComment(org.semanticwb.portal.resources.sem.CmtComment cmtcomment)
    {
        getSemanticObject().removeObjectProperty(swbr_cmt_hasComment,cmtcomment.getSemanticObject());
    }

    public org.semanticwb.portal.resources.sem.CmtComment getComment()
    {
         org.semanticwb.portal.resources.sem.CmtComment ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swbr_cmt_hasComment);
         if(obj!=null)
         {
             ret=(org.semanticwb.portal.resources.sem.CmtComment)obj.createGenericInstance();
         }
         return ret;
    }
}
