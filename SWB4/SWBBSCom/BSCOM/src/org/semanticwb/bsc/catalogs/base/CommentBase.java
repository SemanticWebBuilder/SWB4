package org.semanticwb.bsc.catalogs.base;


   /**
   * Define las características de un Comentario. 
   */
public abstract class CommentBase extends org.semanticwb.bsc.catalogs.Catalog implements org.semanticwb.model.Activeable,org.semanticwb.model.Filterable,org.semanticwb.model.FilterableNode,org.semanticwb.model.Traceable,org.semanticwb.model.Descriptiveable
{
   /**
   * Define las características de un Comentario.
   */
    public static final org.semanticwb.platform.SemanticClass bsc_Comment=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#Comment");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#Comment");

    public static class ClassMgr
    {
       /**
       * Returns a list of Comment for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.bsc.catalogs.Comment
       */

        public static java.util.Iterator<org.semanticwb.bsc.catalogs.Comment> listComments(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.catalogs.Comment>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.bsc.catalogs.Comment for all models
       * @return Iterator of org.semanticwb.bsc.catalogs.Comment
       */

        public static java.util.Iterator<org.semanticwb.bsc.catalogs.Comment> listComments()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.catalogs.Comment>(it, true);
        }

        public static org.semanticwb.bsc.catalogs.Comment createComment(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.bsc.catalogs.Comment.ClassMgr.createComment(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.bsc.catalogs.Comment
       * @param id Identifier for org.semanticwb.bsc.catalogs.Comment
       * @param model Model of the org.semanticwb.bsc.catalogs.Comment
       * @return A org.semanticwb.bsc.catalogs.Comment
       */
        public static org.semanticwb.bsc.catalogs.Comment getComment(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.catalogs.Comment)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.bsc.catalogs.Comment
       * @param id Identifier for org.semanticwb.bsc.catalogs.Comment
       * @param model Model of the org.semanticwb.bsc.catalogs.Comment
       * @return A org.semanticwb.bsc.catalogs.Comment
       */
        public static org.semanticwb.bsc.catalogs.Comment createComment(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.catalogs.Comment)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.bsc.catalogs.Comment
       * @param id Identifier for org.semanticwb.bsc.catalogs.Comment
       * @param model Model of the org.semanticwb.bsc.catalogs.Comment
       */
        public static void removeComment(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.bsc.catalogs.Comment
       * @param id Identifier for org.semanticwb.bsc.catalogs.Comment
       * @param model Model of the org.semanticwb.bsc.catalogs.Comment
       * @return true if the org.semanticwb.bsc.catalogs.Comment exists, false otherwise
       */

        public static boolean hasComment(String id, org.semanticwb.model.SWBModel model)
        {
            return (getComment(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.bsc.catalogs.Comment with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.bsc.catalogs.Comment
       * @return Iterator with all the org.semanticwb.bsc.catalogs.Comment
       */

        public static java.util.Iterator<org.semanticwb.bsc.catalogs.Comment> listCommentByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.catalogs.Comment> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.catalogs.Comment with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.bsc.catalogs.Comment
       */

        public static java.util.Iterator<org.semanticwb.bsc.catalogs.Comment> listCommentByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.catalogs.Comment> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.catalogs.Comment with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.bsc.catalogs.Comment
       * @return Iterator with all the org.semanticwb.bsc.catalogs.Comment
       */

        public static java.util.Iterator<org.semanticwb.bsc.catalogs.Comment> listCommentByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.catalogs.Comment> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.catalogs.Comment with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.bsc.catalogs.Comment
       */

        public static java.util.Iterator<org.semanticwb.bsc.catalogs.Comment> listCommentByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.catalogs.Comment> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static CommentBase.ClassMgr getCommentClassMgr()
    {
        return new CommentBase.ClassMgr();
    }

   /**
   * Constructs a CommentBase with a SemanticObject
   * @param base The SemanticObject with the properties for the Comment
   */
    public CommentBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
}
