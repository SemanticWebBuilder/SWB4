package org.semanticwb.social.base;


   /**
   * LatLngRadioMap 
   */
public abstract class LatLngRadioMapBase extends org.semanticwb.model.base.FormElementBase 
{
   /**
   * LatLngRadioMap
   */
    public static final org.semanticwb.platform.SemanticClass social_LatLngRadioMap=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#LatLngRadioMap");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#LatLngRadioMap");

    public static class ClassMgr
    {
       /**
       * Returns a list of LatLngRadioMap for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.social.LatLngRadioMap
       */

        public static java.util.Iterator<org.semanticwb.social.LatLngRadioMap> listLatLngRadioMaps(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.LatLngRadioMap>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.social.LatLngRadioMap for all models
       * @return Iterator of org.semanticwb.social.LatLngRadioMap
       */

        public static java.util.Iterator<org.semanticwb.social.LatLngRadioMap> listLatLngRadioMaps()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.LatLngRadioMap>(it, true);
        }
       /**
       * Gets a org.semanticwb.social.LatLngRadioMap
       * @param id Identifier for org.semanticwb.social.LatLngRadioMap
       * @param model Model of the org.semanticwb.social.LatLngRadioMap
       * @return A org.semanticwb.social.LatLngRadioMap
       */
        public static org.semanticwb.social.LatLngRadioMap getLatLngRadioMap(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.LatLngRadioMap)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.social.LatLngRadioMap
       * @param id Identifier for org.semanticwb.social.LatLngRadioMap
       * @param model Model of the org.semanticwb.social.LatLngRadioMap
       * @return A org.semanticwb.social.LatLngRadioMap
       */
        public static org.semanticwb.social.LatLngRadioMap createLatLngRadioMap(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.LatLngRadioMap)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.social.LatLngRadioMap
       * @param id Identifier for org.semanticwb.social.LatLngRadioMap
       * @param model Model of the org.semanticwb.social.LatLngRadioMap
       */
        public static void removeLatLngRadioMap(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.social.LatLngRadioMap
       * @param id Identifier for org.semanticwb.social.LatLngRadioMap
       * @param model Model of the org.semanticwb.social.LatLngRadioMap
       * @return true if the org.semanticwb.social.LatLngRadioMap exists, false otherwise
       */

        public static boolean hasLatLngRadioMap(String id, org.semanticwb.model.SWBModel model)
        {
            return (getLatLngRadioMap(id, model)!=null);
        }
    }

    public static LatLngRadioMapBase.ClassMgr getLatLngRadioMapClassMgr()
    {
        return new LatLngRadioMapBase.ClassMgr();
    }

   /**
   * Constructs a LatLngRadioMapBase with a SemanticObject
   * @param base The SemanticObject with the properties for the LatLngRadioMap
   */
    public LatLngRadioMapBase(org.semanticwb.platform.SemanticObject base)
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
