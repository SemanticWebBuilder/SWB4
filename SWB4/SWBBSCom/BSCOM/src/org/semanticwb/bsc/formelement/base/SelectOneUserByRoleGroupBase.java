package org.semanticwb.bsc.formelement.base;


public abstract class SelectOneUserByRoleGroupBase extends org.semanticwb.model.SelectOne 
{
    public static final org.semanticwb.platform.SemanticProperty bsc_so_roleIds=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#so_roleIds");
    public static final org.semanticwb.platform.SemanticProperty bsc_so_userGroupId=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#so_userGroupId");
    public static final org.semanticwb.platform.SemanticProperty bsc_modelId=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#modelId");
    public static final org.semanticwb.platform.SemanticClass bsc_SelectOneUserByRoleGroup=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#SelectOneUserByRoleGroup");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#SelectOneUserByRoleGroup");

    public static class ClassMgr
    {
       /**
       * Returns a list of SelectOneUserByRoleGroup for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.bsc.formelement.SelectOneUserByRoleGroup
       */

        public static java.util.Iterator<org.semanticwb.bsc.formelement.SelectOneUserByRoleGroup> listSelectOneUserByRoleGroups(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.formelement.SelectOneUserByRoleGroup>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.bsc.formelement.SelectOneUserByRoleGroup for all models
       * @return Iterator of org.semanticwb.bsc.formelement.SelectOneUserByRoleGroup
       */

        public static java.util.Iterator<org.semanticwb.bsc.formelement.SelectOneUserByRoleGroup> listSelectOneUserByRoleGroups()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.formelement.SelectOneUserByRoleGroup>(it, true);
        }
       /**
       * Gets a org.semanticwb.bsc.formelement.SelectOneUserByRoleGroup
       * @param id Identifier for org.semanticwb.bsc.formelement.SelectOneUserByRoleGroup
       * @param model Model of the org.semanticwb.bsc.formelement.SelectOneUserByRoleGroup
       * @return A org.semanticwb.bsc.formelement.SelectOneUserByRoleGroup
       */
        public static org.semanticwb.bsc.formelement.SelectOneUserByRoleGroup getSelectOneUserByRoleGroup(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.formelement.SelectOneUserByRoleGroup)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.bsc.formelement.SelectOneUserByRoleGroup
       * @param id Identifier for org.semanticwb.bsc.formelement.SelectOneUserByRoleGroup
       * @param model Model of the org.semanticwb.bsc.formelement.SelectOneUserByRoleGroup
       * @return A org.semanticwb.bsc.formelement.SelectOneUserByRoleGroup
       */
        public static org.semanticwb.bsc.formelement.SelectOneUserByRoleGroup createSelectOneUserByRoleGroup(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.formelement.SelectOneUserByRoleGroup)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.bsc.formelement.SelectOneUserByRoleGroup
       * @param id Identifier for org.semanticwb.bsc.formelement.SelectOneUserByRoleGroup
       * @param model Model of the org.semanticwb.bsc.formelement.SelectOneUserByRoleGroup
       */
        public static void removeSelectOneUserByRoleGroup(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.bsc.formelement.SelectOneUserByRoleGroup
       * @param id Identifier for org.semanticwb.bsc.formelement.SelectOneUserByRoleGroup
       * @param model Model of the org.semanticwb.bsc.formelement.SelectOneUserByRoleGroup
       * @return true if the org.semanticwb.bsc.formelement.SelectOneUserByRoleGroup exists, false otherwise
       */

        public static boolean hasSelectOneUserByRoleGroup(String id, org.semanticwb.model.SWBModel model)
        {
            return (getSelectOneUserByRoleGroup(id, model)!=null);
        }
    }

    public static SelectOneUserByRoleGroupBase.ClassMgr getSelectOneUserByRoleGroupClassMgr()
    {
        return new SelectOneUserByRoleGroupBase.ClassMgr();
    }

   /**
   * Constructs a SelectOneUserByRoleGroupBase with a SemanticObject
   * @param base The SemanticObject with the properties for the SelectOneUserByRoleGroup
   */
    public SelectOneUserByRoleGroupBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

/**
* Gets the FilterRoleIds property
* @return String with the FilterRoleIds
*/
    public String getFilterRoleIds()
    {
        return getSemanticObject().getProperty(bsc_so_roleIds);
    }

/**
* Sets the FilterRoleIds property
* @param value long with the FilterRoleIds
*/
    public void setFilterRoleIds(String value)
    {
        getSemanticObject().setProperty(bsc_so_roleIds, value);
    }

/**
* Gets the FilterUserGroupId property
* @return String with the FilterUserGroupId
*/
    public String getFilterUserGroupId()
    {
        return getSemanticObject().getProperty(bsc_so_userGroupId);
    }

/**
* Sets the FilterUserGroupId property
* @param value long with the FilterUserGroupId
*/
    public void setFilterUserGroupId(String value)
    {
        getSemanticObject().setProperty(bsc_so_userGroupId, value);
    }

/**
* Gets the FilterModelId property
* @return String with the FilterModelId
*/
    public String getFilterModelId()
    {
        return getSemanticObject().getProperty(bsc_modelId);
    }

/**
* Sets the FilterModelId property
* @param value long with the FilterModelId
*/
    public void setFilterModelId(String value)
    {
        getSemanticObject().setProperty(bsc_modelId, value);
    }
}
