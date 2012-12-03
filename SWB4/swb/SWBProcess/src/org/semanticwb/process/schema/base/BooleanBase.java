package org.semanticwb.process.schema.base;


public abstract class BooleanBase extends org.semanticwb.process.model.DataTypes 
{
    public static final org.semanticwb.platform.SemanticProperty swps_booleanValue=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process/schema#booleanValue");
    public static final org.semanticwb.platform.SemanticClass swps_Boolean=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process/schema#Boolean");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process/schema#Boolean");

    public static class ClassMgr
    {
       /**
       * Returns a list of Boolean for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.process.schema.Boolean
       */

        public static java.util.Iterator<org.semanticwb.process.schema.Boolean> listBooleans(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.schema.Boolean>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.process.schema.Boolean for all models
       * @return Iterator of org.semanticwb.process.schema.Boolean
       */

        public static java.util.Iterator<org.semanticwb.process.schema.Boolean> listBooleans()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.schema.Boolean>(it, true);
        }
       /**
       * Gets a org.semanticwb.process.schema.Boolean
       * @param id Identifier for org.semanticwb.process.schema.Boolean
       * @param model Model of the org.semanticwb.process.schema.Boolean
       * @return A org.semanticwb.process.schema.Boolean
       */
        public static org.semanticwb.process.schema.Boolean getBoolean(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.schema.Boolean)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.process.schema.Boolean
       * @param id Identifier for org.semanticwb.process.schema.Boolean
       * @param model Model of the org.semanticwb.process.schema.Boolean
       * @return A org.semanticwb.process.schema.Boolean
       */
        public static org.semanticwb.process.schema.Boolean createBoolean(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.schema.Boolean)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.process.schema.Boolean
       * @param id Identifier for org.semanticwb.process.schema.Boolean
       * @param model Model of the org.semanticwb.process.schema.Boolean
       */
        public static void removeBoolean(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.process.schema.Boolean
       * @param id Identifier for org.semanticwb.process.schema.Boolean
       * @param model Model of the org.semanticwb.process.schema.Boolean
       * @return true if the org.semanticwb.process.schema.Boolean exists, false otherwise
       */

        public static boolean hasBoolean(String id, org.semanticwb.model.SWBModel model)
        {
            return (getBoolean(id, model)!=null);
        }
    }

    public static BooleanBase.ClassMgr getBooleanClassMgr()
    {
        return new BooleanBase.ClassMgr();
    }

   /**
   * Constructs a BooleanBase with a SemanticObject
   * @param base The SemanticObject with the properties for the Boolean
   */
    public BooleanBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

/**
* Gets the Value property
* @return boolean with the Value
*/
    public boolean isValue()
    {
        return getSemanticObject().getBooleanProperty(swps_booleanValue);
    }

/**
* Sets the Value property
* @param value long with the Value
*/
    public void setValue(boolean value)
    {
        getSemanticObject().setBooleanProperty(swps_booleanValue, value);
    }
}
