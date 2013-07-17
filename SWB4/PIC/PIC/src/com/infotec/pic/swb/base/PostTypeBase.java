package com.infotec.pic.swb.base;


public abstract class PostTypeBase extends com.infotec.pic.swb.Catalog implements org.semanticwb.model.Traceable,org.semanticwb.model.Descriptiveable
{
    public static final org.semanticwb.platform.SemanticClass pic_PostType=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/pic.owl#PostType");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/pic.owl#PostType");

    public static class ClassMgr
    {
       /**
       * Returns a list of PostType for a model
       * @param model Model to find
       * @return Iterator of com.infotec.pic.swb.PostType
       */

        public static java.util.Iterator<com.infotec.pic.swb.PostType> listPostTypes(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<com.infotec.pic.swb.PostType>(it, true);
        }
       /**
       * Returns a list of com.infotec.pic.swb.PostType for all models
       * @return Iterator of com.infotec.pic.swb.PostType
       */

        public static java.util.Iterator<com.infotec.pic.swb.PostType> listPostTypes()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<com.infotec.pic.swb.PostType>(it, true);
        }
       /**
       * Gets a com.infotec.pic.swb.PostType
       * @param id Identifier for com.infotec.pic.swb.PostType
       * @param model Model of the com.infotec.pic.swb.PostType
       * @return A com.infotec.pic.swb.PostType
       */
        public static com.infotec.pic.swb.PostType getPostType(String id, org.semanticwb.model.SWBModel model)
        {
            return (com.infotec.pic.swb.PostType)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a com.infotec.pic.swb.PostType
       * @param id Identifier for com.infotec.pic.swb.PostType
       * @param model Model of the com.infotec.pic.swb.PostType
       * @return A com.infotec.pic.swb.PostType
       */
        public static com.infotec.pic.swb.PostType createPostType(String id, org.semanticwb.model.SWBModel model)
        {
            return (com.infotec.pic.swb.PostType)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a com.infotec.pic.swb.PostType
       * @param id Identifier for com.infotec.pic.swb.PostType
       * @param model Model of the com.infotec.pic.swb.PostType
       */
        public static void removePostType(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a com.infotec.pic.swb.PostType
       * @param id Identifier for com.infotec.pic.swb.PostType
       * @param model Model of the com.infotec.pic.swb.PostType
       * @return true if the com.infotec.pic.swb.PostType exists, false otherwise
       */

        public static boolean hasPostType(String id, org.semanticwb.model.SWBModel model)
        {
            return (getPostType(id, model)!=null);
        }
       /**
       * Gets all com.infotec.pic.swb.PostType with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the com.infotec.pic.swb.PostType
       * @return Iterator with all the com.infotec.pic.swb.PostType
       */

        public static java.util.Iterator<com.infotec.pic.swb.PostType> listPostTypeByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.PostType> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.PostType with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the com.infotec.pic.swb.PostType
       */

        public static java.util.Iterator<com.infotec.pic.swb.PostType> listPostTypeByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.PostType> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.PostType with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the com.infotec.pic.swb.PostType
       * @return Iterator with all the com.infotec.pic.swb.PostType
       */

        public static java.util.Iterator<com.infotec.pic.swb.PostType> listPostTypeByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.PostType> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.PostType with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the com.infotec.pic.swb.PostType
       */

        public static java.util.Iterator<com.infotec.pic.swb.PostType> listPostTypeByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.PostType> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static PostTypeBase.ClassMgr getPostTypeClassMgr()
    {
        return new PostTypeBase.ClassMgr();
    }

   /**
   * Constructs a PostTypeBase with a SemanticObject
   * @param base The SemanticObject with the properties for the PostType
   */
    public PostTypeBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
}
