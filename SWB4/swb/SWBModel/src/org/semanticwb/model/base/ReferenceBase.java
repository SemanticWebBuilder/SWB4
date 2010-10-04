package org.semanticwb.model.base;


   /**
   * Super Clase de objetos que sirven de referencia a otro objeto, nomalmente se aplica para activar o desactivar la referencia 
   */
public abstract class ReferenceBase extends org.semanticwb.model.SWBClass implements org.semanticwb.model.Activeable
{
   /**
   * Super Clase de objetos que sirven de referencia a otro objeto, nomalmente se aplica para activar o desactivar la referencia
   */
    public static final org.semanticwb.platform.SemanticClass swb_Reference=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Reference");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Reference");

    public static class ClassMgr
    {
       /**
       * Returns a list of Reference for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.model.Reference
       */

        public static java.util.Iterator<org.semanticwb.model.Reference> listReferences(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.Reference>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.model.Reference for all models
       * @return Iterator of org.semanticwb.model.Reference
       */

        public static java.util.Iterator<org.semanticwb.model.Reference> listReferences()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.Reference>(it, true);
        }

        public static org.semanticwb.model.Reference createReference(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.model.Reference.ClassMgr.createReference(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.model.Reference
       * @param id Identifier for org.semanticwb.model.Reference
       * @param model Model of the org.semanticwb.model.Reference
       * @return A org.semanticwb.model.Reference
       */
        public static org.semanticwb.model.Reference getReference(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.Reference)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.model.Reference
       * @param id Identifier for org.semanticwb.model.Reference
       * @param model Model of the org.semanticwb.model.Reference
       * @return A org.semanticwb.model.Reference
       */
        public static org.semanticwb.model.Reference createReference(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.Reference)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.model.Reference
       * @param id Identifier for org.semanticwb.model.Reference
       * @param model Model of the org.semanticwb.model.Reference
       */
        public static void removeReference(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.model.Reference
       * @param id Identifier for org.semanticwb.model.Reference
       * @param model Model of the org.semanticwb.model.Reference
       * @return true if the org.semanticwb.model.Reference exists, false otherwise
       */

        public static boolean hasReference(String id, org.semanticwb.model.SWBModel model)
        {
            return (getReference(id, model)!=null);
        }
    }

   /**
   * Constructs a ReferenceBase with a SemanticObject
   * @param base The SemanticObject with the properties for the Reference
   */
    public ReferenceBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

/**
* Gets the Active property
* @return boolean with the Active
*/
    public boolean isActive()
    {
        return getSemanticObject().getBooleanProperty(swb_active);
    }

/**
* Sets the Active property
* @param value long with the Active
*/
    public void setActive(boolean value)
    {
        getSemanticObject().setBooleanProperty(swb_active, value);
    }
}
