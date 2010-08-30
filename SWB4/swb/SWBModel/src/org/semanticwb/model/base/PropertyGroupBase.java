package org.semanticwb.model.base;


public abstract class PropertyGroupBase extends org.semanticwb.model.SWBClass implements org.semanticwb.model.Sortable
{
    public static final org.semanticwb.platform.SemanticClass swbxf_PropertyGroup=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#PropertyGroup");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#PropertyGroup");

    public static class ClassMgr
    {
       /**
       * Returns a list of PropertyGroup for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.model.PropertyGroup
       */

        public static java.util.Iterator<org.semanticwb.model.PropertyGroup> listPropertyGroups(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.PropertyGroup>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.model.PropertyGroup for all models
       * @return Iterator of org.semanticwb.model.PropertyGroup
       */

        public static java.util.Iterator<org.semanticwb.model.PropertyGroup> listPropertyGroups()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.PropertyGroup>(it, true);
        }
       /**
       * Gets a org.semanticwb.model.PropertyGroup
       * @param id Identifier for org.semanticwb.model.PropertyGroup
       * @param model Model of the org.semanticwb.model.PropertyGroup
       * @return A org.semanticwb.model.PropertyGroup
       */
        public static org.semanticwb.model.PropertyGroup getPropertyGroup(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.PropertyGroup)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.model.PropertyGroup
       * @param id Identifier for org.semanticwb.model.PropertyGroup
       * @param model Model of the org.semanticwb.model.PropertyGroup
       * @return A org.semanticwb.model.PropertyGroup
       */
        public static org.semanticwb.model.PropertyGroup createPropertyGroup(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.PropertyGroup)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.model.PropertyGroup
       * @param id Identifier for org.semanticwb.model.PropertyGroup
       * @param model Model of the org.semanticwb.model.PropertyGroup
       */
        public static void removePropertyGroup(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.model.PropertyGroup
       * @param id Identifier for org.semanticwb.model.PropertyGroup
       * @param model Model of the org.semanticwb.model.PropertyGroup
       * @return true if the org.semanticwb.model.PropertyGroup exists, false otherwise
       */

        public static boolean hasPropertyGroup(String id, org.semanticwb.model.SWBModel model)
        {
            return (getPropertyGroup(id, model)!=null);
        }
    }

   /**
   * Constructs a PropertyGroupBase with a SemanticObject
   * @param base The SemanticObject with the properties for the PropertyGroup
   */
    public PropertyGroupBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

/**
* Gets the Index property
* @return int with the Index
*/
    public int getIndex()
    {
        return getSemanticObject().getIntProperty(swb_index);
    }

/**
* Sets the Index property
* @param value long with the Index
*/
    public void setIndex(int value)
    {
        getSemanticObject().setIntProperty(swb_index, value);
    }
}
