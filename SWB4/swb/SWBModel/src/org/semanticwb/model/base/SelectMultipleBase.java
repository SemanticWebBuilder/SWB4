package org.semanticwb.model.base;


public abstract class SelectMultipleBase extends org.semanticwb.model.base.FormElementBase 
{
    public static final org.semanticwb.platform.SemanticProperty swbxf_sm_userRepository=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/xforms/ontology#sm_userRepository");
    public static final org.semanticwb.platform.SemanticProperty swbxf_sm_globalScope=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/xforms/ontology#sm_globalScope");
    public static final org.semanticwb.platform.SemanticProperty swbxf_sm_nullSuport=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/xforms/ontology#sm_nullSuport");
    public static final org.semanticwb.platform.SemanticClass swbxf_SelectMultiple=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#SelectMultiple");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#SelectMultiple");

    public static class ClassMgr
    {
       /**
       * Returns a list of SelectMultiple for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.model.SelectMultiple
       */

        public static java.util.Iterator<org.semanticwb.model.SelectMultiple> listSelectMultiples(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.SelectMultiple>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.model.SelectMultiple for all models
       * @return Iterator of org.semanticwb.model.SelectMultiple
       */

        public static java.util.Iterator<org.semanticwb.model.SelectMultiple> listSelectMultiples()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.SelectMultiple>(it, true);
        }
       /**
       * Gets a org.semanticwb.model.SelectMultiple
       * @param id Identifier for org.semanticwb.model.SelectMultiple
       * @param model Model of the org.semanticwb.model.SelectMultiple
       * @return A org.semanticwb.model.SelectMultiple
       */
        public static org.semanticwb.model.SelectMultiple getSelectMultiple(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.SelectMultiple)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.model.SelectMultiple
       * @param id Identifier for org.semanticwb.model.SelectMultiple
       * @param model Model of the org.semanticwb.model.SelectMultiple
       * @return A org.semanticwb.model.SelectMultiple
       */
        public static org.semanticwb.model.SelectMultiple createSelectMultiple(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.SelectMultiple)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.model.SelectMultiple
       * @param id Identifier for org.semanticwb.model.SelectMultiple
       * @param model Model of the org.semanticwb.model.SelectMultiple
       */
        public static void removeSelectMultiple(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.model.SelectMultiple
       * @param id Identifier for org.semanticwb.model.SelectMultiple
       * @param model Model of the org.semanticwb.model.SelectMultiple
       * @return true if the org.semanticwb.model.SelectMultiple exists, false otherwise
       */

        public static boolean hasSelectMultiple(String id, org.semanticwb.model.SWBModel model)
        {
            return (getSelectMultiple(id, model)!=null);
        }
    }

   /**
   * Constructs a SelectMultipleBase with a SemanticObject
   * @param base The SemanticObject with the properties for the SelectMultiple
   */
    public SelectMultipleBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

/**
* Gets the Sm_userRepository property
* @return boolean with the Sm_userRepository
*/
    public boolean isSm_userRepository()
    {
        return getSemanticObject().getBooleanProperty(swbxf_sm_userRepository);
    }

/**
* Sets the Sm_userRepository property
* @param value long with the Sm_userRepository
*/
    public void setSm_userRepository(boolean value)
    {
        getSemanticObject().setBooleanProperty(swbxf_sm_userRepository, value);
    }

/**
* Gets the GlobalScope property
* @return boolean with the GlobalScope
*/
    public boolean isGlobalScope()
    {
        return getSemanticObject().getBooleanProperty(swbxf_sm_globalScope);
    }

/**
* Sets the GlobalScope property
* @param value long with the GlobalScope
*/
    public void setGlobalScope(boolean value)
    {
        getSemanticObject().setBooleanProperty(swbxf_sm_globalScope, value);
    }

/**
* Gets the NullSupport property
* @return boolean with the NullSupport
*/
    public boolean isNullSupport()
    {
        return getSemanticObject().getBooleanProperty(swbxf_sm_nullSuport);
    }

/**
* Sets the NullSupport property
* @param value long with the NullSupport
*/
    public void setNullSupport(boolean value)
    {
        getSemanticObject().setBooleanProperty(swbxf_sm_nullSuport, value);
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
