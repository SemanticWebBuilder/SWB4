package org.semanticwb.bsc.formelement.base;


   /**
   * Extrae información de la propiedad indicada en parentProperty y la muestra para que el usuario seleccione un dato 
   */
public abstract class DataFromParentBase extends org.semanticwb.model.SelectOne 
{
    public static final org.semanticwb.platform.SemanticProperty bsc_parentProperty=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#parentProperty");
   /**
   * Extrae información de la propiedad indicada en parentProperty y la muestra para que el usuario seleccione un dato
   */
    public static final org.semanticwb.platform.SemanticClass bsc_DataFromParent=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#DataFromParent");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#DataFromParent");

    public static class ClassMgr
    {
       /**
       * Returns a list of DataFromParent for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.bsc.formelement.DataFromParent
       */

        public static java.util.Iterator<org.semanticwb.bsc.formelement.DataFromParent> listDataFromParents(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.formelement.DataFromParent>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.bsc.formelement.DataFromParent for all models
       * @return Iterator of org.semanticwb.bsc.formelement.DataFromParent
       */

        public static java.util.Iterator<org.semanticwb.bsc.formelement.DataFromParent> listDataFromParents()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.formelement.DataFromParent>(it, true);
        }
       /**
       * Gets a org.semanticwb.bsc.formelement.DataFromParent
       * @param id Identifier for org.semanticwb.bsc.formelement.DataFromParent
       * @param model Model of the org.semanticwb.bsc.formelement.DataFromParent
       * @return A org.semanticwb.bsc.formelement.DataFromParent
       */
        public static org.semanticwb.bsc.formelement.DataFromParent getDataFromParent(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.formelement.DataFromParent)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.bsc.formelement.DataFromParent
       * @param id Identifier for org.semanticwb.bsc.formelement.DataFromParent
       * @param model Model of the org.semanticwb.bsc.formelement.DataFromParent
       * @return A org.semanticwb.bsc.formelement.DataFromParent
       */
        public static org.semanticwb.bsc.formelement.DataFromParent createDataFromParent(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.formelement.DataFromParent)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.bsc.formelement.DataFromParent
       * @param id Identifier for org.semanticwb.bsc.formelement.DataFromParent
       * @param model Model of the org.semanticwb.bsc.formelement.DataFromParent
       */
        public static void removeDataFromParent(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.bsc.formelement.DataFromParent
       * @param id Identifier for org.semanticwb.bsc.formelement.DataFromParent
       * @param model Model of the org.semanticwb.bsc.formelement.DataFromParent
       * @return true if the org.semanticwb.bsc.formelement.DataFromParent exists, false otherwise
       */

        public static boolean hasDataFromParent(String id, org.semanticwb.model.SWBModel model)
        {
            return (getDataFromParent(id, model)!=null);
        }
    }

    public static DataFromParentBase.ClassMgr getDataFromParentClassMgr()
    {
        return new DataFromParentBase.ClassMgr();
    }

   /**
   * Constructs a DataFromParentBase with a SemanticObject
   * @param base The SemanticObject with the properties for the DataFromParent
   */
    public DataFromParentBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

/**
* Gets the ParentProperty property
* @return String with the ParentProperty
*/
    public String getParentProperty()
    {
        return getSemanticObject().getProperty(bsc_parentProperty);
    }

/**
* Sets the ParentProperty property
* @param value long with the ParentProperty
*/
    public void setParentProperty(String value)
    {
        getSemanticObject().setProperty(bsc_parentProperty, value);
    }
}
