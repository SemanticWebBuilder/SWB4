package org.semanticwb.model.base;


   /**
   * Elemento usado para validar la posibilidad de ser o no activado del elemento, ejemplo: cuando el elmenento esta en flujo no puede ser activado 
   */
public abstract class ActivateBase extends org.semanticwb.model.base.FormElementBase 
{
   /**
   * Elemento usado para validar la posibilidad de ser o no activado del elemento, ejemplo: cuando el elmenento esta en flujo no puede ser activado
   */
    public static final org.semanticwb.platform.SemanticClass swbxf_Activate=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#Activate");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#Activate");

    public static class ClassMgr
    {
       /**
       * Returns a list of Activate for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.model.Activate
       */

        public static java.util.Iterator<org.semanticwb.model.Activate> listActivates(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.Activate>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.model.Activate for all models
       * @return Iterator of org.semanticwb.model.Activate
       */

        public static java.util.Iterator<org.semanticwb.model.Activate> listActivates()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.Activate>(it, true);
        }
       /**
       * Gets a org.semanticwb.model.Activate
       * @param id Identifier for org.semanticwb.model.Activate
       * @param model Model of the org.semanticwb.model.Activate
       * @return A org.semanticwb.model.Activate
       */
        public static org.semanticwb.model.Activate getActivate(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.Activate)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.model.Activate
       * @param id Identifier for org.semanticwb.model.Activate
       * @param model Model of the org.semanticwb.model.Activate
       * @return A org.semanticwb.model.Activate
       */
        public static org.semanticwb.model.Activate createActivate(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.Activate)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.model.Activate
       * @param id Identifier for org.semanticwb.model.Activate
       * @param model Model of the org.semanticwb.model.Activate
       */
        public static void removeActivate(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.model.Activate
       * @param id Identifier for org.semanticwb.model.Activate
       * @param model Model of the org.semanticwb.model.Activate
       * @return true if the org.semanticwb.model.Activate exists, false otherwise
       */

        public static boolean hasActivate(String id, org.semanticwb.model.SWBModel model)
        {
            return (getActivate(id, model)!=null);
        }
    }

    public static ActivateBase.ClassMgr getActivateClassMgr()
    {
        return new ActivateBase.ClassMgr();
    }

   /**
   * Constructs a ActivateBase with a SemanticObject
   * @param base The SemanticObject with the properties for the Activate
   */
    public ActivateBase(org.semanticwb.platform.SemanticObject base)
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
