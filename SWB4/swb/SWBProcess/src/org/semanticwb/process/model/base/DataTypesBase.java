package org.semanticwb.process.model.base;


public abstract class DataTypesBase extends org.semanticwb.model.SWBClass 
{
    public static final org.semanticwb.platform.SemanticClass swp_DataTypes=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#DataTypes");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#DataTypes");

    public static class ClassMgr
    {
       /**
       * Returns a list of DataTypes for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.process.model.DataTypes
       */

        public static java.util.Iterator<org.semanticwb.process.model.DataTypes> listDataTypeses(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataTypes>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.process.model.DataTypes for all models
       * @return Iterator of org.semanticwb.process.model.DataTypes
       */

        public static java.util.Iterator<org.semanticwb.process.model.DataTypes> listDataTypeses()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataTypes>(it, true);
        }
       /**
       * Gets a org.semanticwb.process.model.DataTypes
       * @param id Identifier for org.semanticwb.process.model.DataTypes
       * @param model Model of the org.semanticwb.process.model.DataTypes
       * @return A org.semanticwb.process.model.DataTypes
       */
        public static org.semanticwb.process.model.DataTypes getDataTypes(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.DataTypes)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.process.model.DataTypes
       * @param id Identifier for org.semanticwb.process.model.DataTypes
       * @param model Model of the org.semanticwb.process.model.DataTypes
       * @return A org.semanticwb.process.model.DataTypes
       */
        public static org.semanticwb.process.model.DataTypes createDataTypes(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.DataTypes)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.process.model.DataTypes
       * @param id Identifier for org.semanticwb.process.model.DataTypes
       * @param model Model of the org.semanticwb.process.model.DataTypes
       */
        public static void removeDataTypes(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.process.model.DataTypes
       * @param id Identifier for org.semanticwb.process.model.DataTypes
       * @param model Model of the org.semanticwb.process.model.DataTypes
       * @return true if the org.semanticwb.process.model.DataTypes exists, false otherwise
       */

        public static boolean hasDataTypes(String id, org.semanticwb.model.SWBModel model)
        {
            return (getDataTypes(id, model)!=null);
        }
    }

    public static DataTypesBase.ClassMgr getDataTypesClassMgr()
    {
        return new DataTypesBase.ClassMgr();
    }

   /**
   * Constructs a DataTypesBase with a SemanticObject
   * @param base The SemanticObject with the properties for the DataTypes
   */
    public DataTypesBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
}
