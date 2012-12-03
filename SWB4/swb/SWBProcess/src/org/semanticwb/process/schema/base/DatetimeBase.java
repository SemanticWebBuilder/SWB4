package org.semanticwb.process.schema.base;


public abstract class DatetimeBase extends org.semanticwb.process.model.DataTypes 
{
    public static final org.semanticwb.platform.SemanticProperty swps_datetimeVaue=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process/schema#datetimeVaue");
    public static final org.semanticwb.platform.SemanticClass swps_Datetime=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process/schema#Datetime");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process/schema#Datetime");

    public static class ClassMgr
    {
       /**
       * Returns a list of Datetime for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.process.schema.Datetime
       */

        public static java.util.Iterator<org.semanticwb.process.schema.Datetime> listDatetimes(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.schema.Datetime>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.process.schema.Datetime for all models
       * @return Iterator of org.semanticwb.process.schema.Datetime
       */

        public static java.util.Iterator<org.semanticwb.process.schema.Datetime> listDatetimes()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.schema.Datetime>(it, true);
        }
       /**
       * Gets a org.semanticwb.process.schema.Datetime
       * @param id Identifier for org.semanticwb.process.schema.Datetime
       * @param model Model of the org.semanticwb.process.schema.Datetime
       * @return A org.semanticwb.process.schema.Datetime
       */
        public static org.semanticwb.process.schema.Datetime getDatetime(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.schema.Datetime)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.process.schema.Datetime
       * @param id Identifier for org.semanticwb.process.schema.Datetime
       * @param model Model of the org.semanticwb.process.schema.Datetime
       * @return A org.semanticwb.process.schema.Datetime
       */
        public static org.semanticwb.process.schema.Datetime createDatetime(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.schema.Datetime)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.process.schema.Datetime
       * @param id Identifier for org.semanticwb.process.schema.Datetime
       * @param model Model of the org.semanticwb.process.schema.Datetime
       */
        public static void removeDatetime(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.process.schema.Datetime
       * @param id Identifier for org.semanticwb.process.schema.Datetime
       * @param model Model of the org.semanticwb.process.schema.Datetime
       * @return true if the org.semanticwb.process.schema.Datetime exists, false otherwise
       */

        public static boolean hasDatetime(String id, org.semanticwb.model.SWBModel model)
        {
            return (getDatetime(id, model)!=null);
        }
    }

    public static DatetimeBase.ClassMgr getDatetimeClassMgr()
    {
        return new DatetimeBase.ClassMgr();
    }

   /**
   * Constructs a DatetimeBase with a SemanticObject
   * @param base The SemanticObject with the properties for the Datetime
   */
    public DatetimeBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

/**
* Gets the Value property
* @return java.util.Date with the Value
*/
    public java.util.Date getValue()
    {
        return getSemanticObject().getDateProperty(swps_datetimeVaue);
    }

/**
* Sets the Value property
* @param value long with the Value
*/
    public void setValue(java.util.Date value)
    {
        getSemanticObject().setDateProperty(swps_datetimeVaue, value);
    }
}
