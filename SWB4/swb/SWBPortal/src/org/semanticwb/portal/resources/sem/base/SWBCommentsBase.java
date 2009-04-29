package org.semanticwb.portal.resources.sem.base;


public class SWBCommentsBase extends org.semanticwb.portal.api.GenericSemResource 
{
    public static final org.semanticwb.platform.SemanticClass swb_res_cmts_Comment=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/portal/resources/sems/SWBComments#Comment");
    public static final org.semanticwb.platform.SemanticProperty swb_res_cmts_hasComment=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/portal/resources/sems/SWBComments#hasComment");
    public static final org.semanticwb.platform.SemanticClass swb_res_cmts_SWBComments=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/portal/resources/sems/SWBComments#SWBComments");

    public SWBCommentsBase()
    {
    }

    public SWBCommentsBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/portal/resources/sems/SWBComments#SWBComments");

    public org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.Comment> listComments()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.Comment>(getSemanticObject().listObjectProperties(swb_res_cmts_hasComment));
    }

    public boolean hasComment(org.semanticwb.portal.resources.sem.Comment comment)
    {
        if(comment==null)return false;        return getSemanticObject().hasObjectProperty(swb_res_cmts_hasComment,comment.getSemanticObject());
    }

    public void addComment(org.semanticwb.portal.resources.sem.Comment comment)
    {
        getSemanticObject().addObjectProperty(swb_res_cmts_hasComment, comment.getSemanticObject());
    }

    public void removeAllComment()
    {
        getSemanticObject().removeProperty(swb_res_cmts_hasComment);
    }

    public void removeComment(org.semanticwb.portal.resources.sem.Comment comment)
    {
        getSemanticObject().removeObjectProperty(swb_res_cmts_hasComment,comment.getSemanticObject());
    }

    public org.semanticwb.portal.resources.sem.Comment getComment()
    {
         org.semanticwb.portal.resources.sem.Comment ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_res_cmts_hasComment);
         if(obj!=null)
         {
             ret=(org.semanticwb.portal.resources.sem.Comment)obj.createGenericInstance();
         }
         return ret;
    }
}
