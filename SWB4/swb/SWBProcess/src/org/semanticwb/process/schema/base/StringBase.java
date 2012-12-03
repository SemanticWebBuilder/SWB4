package org.semanticwb.process.schema.base;


public abstract class StringBase extends org.semanticwb.process.model.DataTypes 
{
    public static final org.semanticwb.platform.SemanticProperty swps_stringValue=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process/schema#stringValue");
    public static final org.semanticwb.platform.SemanticClass swps_String=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process/schema#String");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process/schema#String");

    public static class ClassMgr
    {
       /**
       * Returns a list of String for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.process.schema.String
       */

        public static java.util.Iterator<org.semanticwb.process.schema.String> listStrings(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.schema.String>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.process.schema.String for all models
       * @return Iterator of org.semanticwb.process.schema.String
       */

        public static java.util.Iterator<org.semanticwb.process.schema.String> listStrings()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.schema.String>(it, true);
        }
       /**
       * Gets a org.semanticwb.process.schema.String
       * @param id Identifier for org.semanticwb.process.schema.String
       * @param model Model of the org.semanticwb.process.schema.String
       * @return A org.semanticwb.process.schema.String
       */
        public static org.semanticwb.process.schema.String getString(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.schema.String)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.process.schema.String
       * @param id Identifier for org.semanticwb.process.schema.String
       * @param model Model of the org.semanticwb.process.schema.String
       * @return A org.semanticwb.process.schema.String
       */
        public static org.semanticwb.process.schema.String createString(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.schema.String)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.process.schema.String
       * @param id Identifier for org.semanticwb.process.schema.String
       * @param model Model of the org.semanticwb.process.schema.String
       */
        public static void removeString(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.process.schema.String
       * @param id Identifier for org.semanticwb.process.schema.String
       * @param model Model of the org.semanticwb.process.schema.String
       * @return true if the org.semanticwb.process.schema.String exists, false otherwise
       */

        public static boolean hasString(String id, org.semanticwb.model.SWBModel model)
        {
            return (getString(id, model)!=null);
        }
    }

    public static StringBase.ClassMgr getStringClassMgr()
    {
        return new StringBase.ClassMgr();
    }

   /**
   * Constructs a StringBase with a SemanticObject
   * @param base The SemanticObject with the properties for the String
   */
    public StringBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

/**
* Gets the Value property
* @return String with the Value
*/
    public String getValue()
    {
        return getSemanticObject().getProperty(swps_stringValue);
    }

/**
* Sets the Value property
* @param value long with the Value
*/
    public void setValue(String value)
    {
        getSemanticObject().setProperty(swps_stringValue, value);
    }
}
