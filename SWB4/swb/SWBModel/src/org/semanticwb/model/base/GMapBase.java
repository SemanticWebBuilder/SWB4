package org.semanticwb.model.base;


   /**
   * Elemento que muestra un componente grafico (Google Maps) para geolocalizar un punto 
   */
public abstract class GMapBase extends org.semanticwb.model.base.FormElementBase 
{
    public static final org.semanticwb.platform.SemanticProperty swbxf_initLatitude=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/xforms/ontology#initLatitude");
    public static final org.semanticwb.platform.SemanticProperty swb_initLongitude=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#initLongitude");
    public static final org.semanticwb.platform.SemanticProperty swb_initStep=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#initStep");
   /**
   * Elemento que muestra un componente grafico (Google Maps) para geolocalizar un punto
   */
    public static final org.semanticwb.platform.SemanticClass swbxf_GMap=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#GMap");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#GMap");

    public static class ClassMgr
    {
       /**
       * Returns a list of GMap for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.model.GMap
       */

        public static java.util.Iterator<org.semanticwb.model.GMap> listGMaps(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.GMap>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.model.GMap for all models
       * @return Iterator of org.semanticwb.model.GMap
       */

        public static java.util.Iterator<org.semanticwb.model.GMap> listGMaps()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.GMap>(it, true);
        }
       /**
       * Gets a org.semanticwb.model.GMap
       * @param id Identifier for org.semanticwb.model.GMap
       * @param model Model of the org.semanticwb.model.GMap
       * @return A org.semanticwb.model.GMap
       */
        public static org.semanticwb.model.GMap getGMap(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.GMap)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.model.GMap
       * @param id Identifier for org.semanticwb.model.GMap
       * @param model Model of the org.semanticwb.model.GMap
       * @return A org.semanticwb.model.GMap
       */
        public static org.semanticwb.model.GMap createGMap(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.GMap)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.model.GMap
       * @param id Identifier for org.semanticwb.model.GMap
       * @param model Model of the org.semanticwb.model.GMap
       */
        public static void removeGMap(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.model.GMap
       * @param id Identifier for org.semanticwb.model.GMap
       * @param model Model of the org.semanticwb.model.GMap
       * @return true if the org.semanticwb.model.GMap exists, false otherwise
       */

        public static boolean hasGMap(String id, org.semanticwb.model.SWBModel model)
        {
            return (getGMap(id, model)!=null);
        }
    }

   /**
   * Constructs a GMapBase with a SemanticObject
   * @param base The SemanticObject with the properties for the GMap
   */
    public GMapBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

/**
* Gets the InitLatitude property
* @return double with the InitLatitude
*/
    public double getInitLatitude()
    {
        return getSemanticObject().getDoubleProperty(swbxf_initLatitude);
    }

/**
* Sets the InitLatitude property
* @param value long with the InitLatitude
*/
    public void setInitLatitude(double value)
    {
        getSemanticObject().setDoubleProperty(swbxf_initLatitude, value);
    }

/**
* Gets the InitLongitude property
* @return double with the InitLongitude
*/
    public double getInitLongitude()
    {
        return getSemanticObject().getDoubleProperty(swb_initLongitude);
    }

/**
* Sets the InitLongitude property
* @param value long with the InitLongitude
*/
    public void setInitLongitude(double value)
    {
        getSemanticObject().setDoubleProperty(swb_initLongitude, value);
    }

/**
* Gets the InitStep property
* @return int with the InitStep
*/
    public int getInitStep()
    {
        return getSemanticObject().getIntProperty(swb_initStep);
    }

/**
* Sets the InitStep property
* @param value long with the InitStep
*/
    public void setInitStep(int value)
    {
        getSemanticObject().setIntProperty(swb_initStep, value);
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
