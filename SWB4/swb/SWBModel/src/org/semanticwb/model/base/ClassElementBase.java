package org.semanticwb.model.base;


public abstract class ClassElementBase extends org.semanticwb.model.Text 
{
    public static final org.semanticwb.platform.SemanticClass swbxf_ClassElement=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#ClassElement");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#ClassElement");

    public static class ClassMgr
    {
       /**
       * Returns a list of ClassElement for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.model.ClassElement
       */

        public static java.util.Iterator<org.semanticwb.model.ClassElement> listClassElements(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.ClassElement>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.model.ClassElement for all models
       * @return Iterator of org.semanticwb.model.ClassElement
       */

        public static java.util.Iterator<org.semanticwb.model.ClassElement> listClassElements()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.ClassElement>(it, true);
        }
       /**
       * Gets a org.semanticwb.model.ClassElement
       * @param id Identifier for org.semanticwb.model.ClassElement
       * @param model Model of the org.semanticwb.model.ClassElement
       * @return A org.semanticwb.model.ClassElement
       */
        public static org.semanticwb.model.ClassElement getClassElement(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.ClassElement)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.model.ClassElement
       * @param id Identifier for org.semanticwb.model.ClassElement
       * @param model Model of the org.semanticwb.model.ClassElement
       * @return A org.semanticwb.model.ClassElement
       */
        public static org.semanticwb.model.ClassElement createClassElement(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.ClassElement)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.model.ClassElement
       * @param id Identifier for org.semanticwb.model.ClassElement
       * @param model Model of the org.semanticwb.model.ClassElement
       */
        public static void removeClassElement(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.model.ClassElement
       * @param id Identifier for org.semanticwb.model.ClassElement
       * @param model Model of the org.semanticwb.model.ClassElement
       * @return true if the org.semanticwb.model.ClassElement exists, false otherwise
       */

        public static boolean hasClassElement(String id, org.semanticwb.model.SWBModel model)
        {
            return (getClassElement(id, model)!=null);
        }
    }

   /**
   * Constructs a ClassElementBase with a SemanticObject
   * @param base The SemanticObject with the properties for the ClassElement
   */
    public ClassElementBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
}
