package org.semanticwb.model.base;


public abstract class SelectClassBase extends org.semanticwb.model.base.FormElementBase 
{
    public static final org.semanticwb.platform.SemanticClass swbxf_SelectClass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#SelectClass");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#SelectClass");

    public static class ClassMgr
    {
       /**
       * Returns a list of SelectClass for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.model.SelectClass
       */

        public static java.util.Iterator<org.semanticwb.model.SelectClass> listSelectClasses(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.SelectClass>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.model.SelectClass for all models
       * @return Iterator of org.semanticwb.model.SelectClass
       */

        public static java.util.Iterator<org.semanticwb.model.SelectClass> listSelectClasses()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.SelectClass>(it, true);
        }
       /**
       * Gets a org.semanticwb.model.SelectClass
       * @param id Identifier for org.semanticwb.model.SelectClass
       * @param model Model of the org.semanticwb.model.SelectClass
       * @return A org.semanticwb.model.SelectClass
       */
        public static org.semanticwb.model.SelectClass getSelectClass(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.SelectClass)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.model.SelectClass
       * @param id Identifier for org.semanticwb.model.SelectClass
       * @param model Model of the org.semanticwb.model.SelectClass
       * @return A org.semanticwb.model.SelectClass
       */
        public static org.semanticwb.model.SelectClass createSelectClass(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.SelectClass)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.model.SelectClass
       * @param id Identifier for org.semanticwb.model.SelectClass
       * @param model Model of the org.semanticwb.model.SelectClass
       */
        public static void removeSelectClass(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.model.SelectClass
       * @param id Identifier for org.semanticwb.model.SelectClass
       * @param model Model of the org.semanticwb.model.SelectClass
       * @return true if the org.semanticwb.model.SelectClass exists, false otherwise
       */

        public static boolean hasSelectClass(String id, org.semanticwb.model.SWBModel model)
        {
            return (getSelectClass(id, model)!=null);
        }
    }

   /**
   * Constructs a SelectClassBase with a SemanticObject
   * @param base The SemanticObject with the properties for the SelectClass
   */
    public SelectClassBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public void remove()
    {
        getSemanticObject().remove();
    }

    public java.util.Iterator<org.semanticwb.model.GenericObject> listRelatedObjects()
    {
        return new org.semanticwb.model.GenericIterator(getSemanticObject().listRelatedObjects(),true);
    }
}
