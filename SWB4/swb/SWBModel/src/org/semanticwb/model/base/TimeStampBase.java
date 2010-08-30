package org.semanticwb.model.base;


public abstract class TimeStampBase extends org.semanticwb.model.base.FormElementBase 
{
    public static final org.semanticwb.platform.SemanticClass swbxf_TimeStamp=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#TimeStamp");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#TimeStamp");

    public static class ClassMgr
    {
       /**
       * Returns a list of TimeStamp for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.model.TimeStamp
       */

        public static java.util.Iterator<org.semanticwb.model.TimeStamp> listTimeStamps(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.TimeStamp>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.model.TimeStamp for all models
       * @return Iterator of org.semanticwb.model.TimeStamp
       */

        public static java.util.Iterator<org.semanticwb.model.TimeStamp> listTimeStamps()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.TimeStamp>(it, true);
        }
       /**
       * Gets a org.semanticwb.model.TimeStamp
       * @param id Identifier for org.semanticwb.model.TimeStamp
       * @param model Model of the org.semanticwb.model.TimeStamp
       * @return A org.semanticwb.model.TimeStamp
       */
        public static org.semanticwb.model.TimeStamp getTimeStamp(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.TimeStamp)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.model.TimeStamp
       * @param id Identifier for org.semanticwb.model.TimeStamp
       * @param model Model of the org.semanticwb.model.TimeStamp
       * @return A org.semanticwb.model.TimeStamp
       */
        public static org.semanticwb.model.TimeStamp createTimeStamp(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.TimeStamp)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.model.TimeStamp
       * @param id Identifier for org.semanticwb.model.TimeStamp
       * @param model Model of the org.semanticwb.model.TimeStamp
       */
        public static void removeTimeStamp(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.model.TimeStamp
       * @param id Identifier for org.semanticwb.model.TimeStamp
       * @param model Model of the org.semanticwb.model.TimeStamp
       * @return true if the org.semanticwb.model.TimeStamp exists, false otherwise
       */

        public static boolean hasTimeStamp(String id, org.semanticwb.model.SWBModel model)
        {
            return (getTimeStamp(id, model)!=null);
        }
    }

   /**
   * Constructs a TimeStampBase with a SemanticObject
   * @param base The SemanticObject with the properties for the TimeStamp
   */
    public TimeStampBase(org.semanticwb.platform.SemanticObject base)
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
