package org.semanticwb.model.base;


   /**
   * Superclase de todos los objetos con persistencia en SemanticWebBuilder 
   */
public abstract class SWBClassBase extends org.semanticwb.model.base.GenericObjectBase 
{
   /**
   * Indica si el elemento es válido
   */
    public static final org.semanticwb.platform.SemanticProperty swb_valid=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#valid");
   /**
   * Superclase de todos los objetos con persistencia en SemanticWebBuilder
   */
    public static final org.semanticwb.platform.SemanticClass swb_SWBClass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#SWBClass");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#SWBClass");

    public static class ClassMgr
    {
       /**
       * Returns a list of SWBClass for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.model.SWBClass
       */

        public static java.util.Iterator<org.semanticwb.model.SWBClass> listSWBClasses(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.SWBClass>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.model.SWBClass for all models
       * @return Iterator of org.semanticwb.model.SWBClass
       */

        public static java.util.Iterator<org.semanticwb.model.SWBClass> listSWBClasses()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.SWBClass>(it, true);
        }
       /**
       * Gets a org.semanticwb.model.SWBClass
       * @param id Identifier for org.semanticwb.model.SWBClass
       * @param model Model of the org.semanticwb.model.SWBClass
       * @return A org.semanticwb.model.SWBClass
       */
        public static org.semanticwb.model.SWBClass getSWBClass(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.SWBClass)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.model.SWBClass
       * @param id Identifier for org.semanticwb.model.SWBClass
       * @param model Model of the org.semanticwb.model.SWBClass
       * @return A org.semanticwb.model.SWBClass
       */
        public static org.semanticwb.model.SWBClass createSWBClass(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.SWBClass)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.model.SWBClass
       * @param id Identifier for org.semanticwb.model.SWBClass
       * @param model Model of the org.semanticwb.model.SWBClass
       */
        public static void removeSWBClass(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.model.SWBClass
       * @param id Identifier for org.semanticwb.model.SWBClass
       * @param model Model of the org.semanticwb.model.SWBClass
       * @return true if the org.semanticwb.model.SWBClass exists, false otherwise
       */

        public static boolean hasSWBClass(String id, org.semanticwb.model.SWBModel model)
        {
            return (getSWBClass(id, model)!=null);
        }
    }

   /**
   * Constructs a SWBClassBase with a SemanticObject
   * @param base The SemanticObject with the properties for the SWBClass
   */
    public SWBClassBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

/**
* Gets the Valid property
* @return boolean with the Valid
*/
    public boolean isValid()
    {
        //Override this method in SWBClass object
        return getSemanticObject().getBooleanProperty(swb_valid,false);
    }

/**
* Sets the Valid property
* @param value long with the Valid
*/
    public void setValid(boolean value)
    {
        //Override this method in SWBClass object
        getSemanticObject().setBooleanProperty(swb_valid, value,false);
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
