package org.semanticwb.process.schema.base;


public abstract class IntegerBase extends org.semanticwb.process.model.DataTypes 
{
    public static final org.semanticwb.platform.SemanticProperty swps_intValue=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process/schema#intValue");
    public static final org.semanticwb.platform.SemanticClass swps_Integer=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process/schema#Integer");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process/schema#Integer");

    public static class ClassMgr
    {
       /**
       * Returns a list of Integer for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.process.schema.Integer
       */

        public static java.util.Iterator<org.semanticwb.process.schema.Integer> listIntegers(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.schema.Integer>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.process.schema.Integer for all models
       * @return Iterator of org.semanticwb.process.schema.Integer
       */

        public static java.util.Iterator<org.semanticwb.process.schema.Integer> listIntegers()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.schema.Integer>(it, true);
        }
       /**
       * Gets a org.semanticwb.process.schema.Integer
       * @param id Identifier for org.semanticwb.process.schema.Integer
       * @param model Model of the org.semanticwb.process.schema.Integer
       * @return A org.semanticwb.process.schema.Integer
       */
        public static org.semanticwb.process.schema.Integer getInteger(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.schema.Integer)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.process.schema.Integer
       * @param id Identifier for org.semanticwb.process.schema.Integer
       * @param model Model of the org.semanticwb.process.schema.Integer
       * @return A org.semanticwb.process.schema.Integer
       */
        public static org.semanticwb.process.schema.Integer createInteger(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.schema.Integer)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.process.schema.Integer
       * @param id Identifier for org.semanticwb.process.schema.Integer
       * @param model Model of the org.semanticwb.process.schema.Integer
       */
        public static void removeInteger(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.process.schema.Integer
       * @param id Identifier for org.semanticwb.process.schema.Integer
       * @param model Model of the org.semanticwb.process.schema.Integer
       * @return true if the org.semanticwb.process.schema.Integer exists, false otherwise
       */

        public static boolean hasInteger(String id, org.semanticwb.model.SWBModel model)
        {
            return (getInteger(id, model)!=null);
        }
    }

    public static IntegerBase.ClassMgr getIntegerClassMgr()
    {
        return new IntegerBase.ClassMgr();
    }

   /**
   * Constructs a IntegerBase with a SemanticObject
   * @param base The SemanticObject with the properties for the Integer
   */
    public IntegerBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

/**
* Gets the Value property
* @return int with the Value
*/
    public int getValue()
    {
        return getSemanticObject().getIntProperty(swps_intValue);
    }

/**
* Sets the Value property
* @param value long with the Value
*/
    public void setValue(int value)
    {
        getSemanticObject().setIntProperty(swps_intValue, value);
    }
}
