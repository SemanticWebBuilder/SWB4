package org.semanticwb.portal.resources.sem.blog.base;


public abstract class CommentBase extends org.semanticwb.model.SWBClass 
{
    public static final org.semanticwb.platform.SemanticProperty blog_comment=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/wb2.0/blog#comment");
    public static final org.semanticwb.platform.SemanticClass swb_User=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#User");
    public static final org.semanticwb.platform.SemanticProperty blog_userComment=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/wb2.0/blog#userComment");
    public static final org.semanticwb.platform.SemanticProperty blog_fec_altaComment=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/wb2.0/blog#fec_altaComment");
    public static final org.semanticwb.platform.SemanticClass blog_Comment=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/wb2.0/blog#Comment");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/wb2.0/blog#Comment");

    public static class ClassMgr
    {

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.blog.Comment> listComments(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.blog.Comment>(it, true);
        }

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.blog.Comment> listComments()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.blog.Comment>(it, true);
        }

        public static org.semanticwb.portal.resources.sem.blog.Comment createComment(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.portal.resources.sem.blog.Comment.ClassMgr.createComment(String.valueOf(id), model);
        }

        public static org.semanticwb.portal.resources.sem.blog.Comment getComment(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.portal.resources.sem.blog.Comment)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static org.semanticwb.portal.resources.sem.blog.Comment createComment(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.portal.resources.sem.blog.Comment)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        }

        public static void removeComment(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        public static boolean hasComment(String id, org.semanticwb.model.SWBModel model)
        {
            return (getComment(id, model)!=null);
        }

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.blog.Comment> listCommentByUserComment(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.blog.Comment> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(blog_userComment, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.blog.Comment> listCommentByUserComment(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.blog.Comment> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(blog_userComment,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public CommentBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public String getComment()
    {
        return getSemanticObject().getProperty(blog_comment);
    }

    public void setComment(String value)
    {
        getSemanticObject().setProperty(blog_comment, value);
    }

    public void setUserComment(org.semanticwb.model.User value)
    {
        getSemanticObject().setObjectProperty(blog_userComment, value.getSemanticObject());
    }

    public void removeUserComment()
    {
        getSemanticObject().removeProperty(blog_userComment);
    }

    public org.semanticwb.model.User getUserComment()
    {
         org.semanticwb.model.User ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(blog_userComment);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.User)obj.createGenericInstance();
         }
         return ret;
    }

    public java.util.Date getFec_altaComment()
    {
        return getSemanticObject().getDateProperty(blog_fec_altaComment);
    }

    public void setFec_altaComment(java.util.Date value)
    {
        getSemanticObject().setDateProperty(blog_fec_altaComment, value);
    }
}
