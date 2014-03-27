package org.semanticwb.bsc.formelement.base;


public abstract class SelectUserGroupBase extends org.semanticwb.model.SelectOne 
{
    public static final org.semanticwb.platform.SemanticProperty bsc_users_ById=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#users_ById");
    public static final org.semanticwb.platform.SemanticClass bsc_SelectUserGroup=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#SelectUserGroup");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#SelectUserGroup");

    public static class ClassMgr
    {
       /**
       * Returns a list of SelectUserGroup for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.bsc.formelement.SelectUserGroup
       */

        public static java.util.Iterator<org.semanticwb.bsc.formelement.SelectUserGroup> listSelectUserGroups(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.formelement.SelectUserGroup>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.bsc.formelement.SelectUserGroup for all models
       * @return Iterator of org.semanticwb.bsc.formelement.SelectUserGroup
       */

        public static java.util.Iterator<org.semanticwb.bsc.formelement.SelectUserGroup> listSelectUserGroups()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.formelement.SelectUserGroup>(it, true);
        }
       /**
       * Gets a org.semanticwb.bsc.formelement.SelectUserGroup
       * @param id Identifier for org.semanticwb.bsc.formelement.SelectUserGroup
       * @param model Model of the org.semanticwb.bsc.formelement.SelectUserGroup
       * @return A org.semanticwb.bsc.formelement.SelectUserGroup
       */
        public static org.semanticwb.bsc.formelement.SelectUserGroup getSelectUserGroup(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.formelement.SelectUserGroup)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.bsc.formelement.SelectUserGroup
       * @param id Identifier for org.semanticwb.bsc.formelement.SelectUserGroup
       * @param model Model of the org.semanticwb.bsc.formelement.SelectUserGroup
       * @return A org.semanticwb.bsc.formelement.SelectUserGroup
       */
        public static org.semanticwb.bsc.formelement.SelectUserGroup createSelectUserGroup(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.formelement.SelectUserGroup)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.bsc.formelement.SelectUserGroup
       * @param id Identifier for org.semanticwb.bsc.formelement.SelectUserGroup
       * @param model Model of the org.semanticwb.bsc.formelement.SelectUserGroup
       */
        public static void removeSelectUserGroup(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.bsc.formelement.SelectUserGroup
       * @param id Identifier for org.semanticwb.bsc.formelement.SelectUserGroup
       * @param model Model of the org.semanticwb.bsc.formelement.SelectUserGroup
       * @return true if the org.semanticwb.bsc.formelement.SelectUserGroup exists, false otherwise
       */

        public static boolean hasSelectUserGroup(String id, org.semanticwb.model.SWBModel model)
        {
            return (getSelectUserGroup(id, model)!=null);
        }
    }

    public static SelectUserGroupBase.ClassMgr getSelectUserGroupClassMgr()
    {
        return new SelectUserGroupBase.ClassMgr();
    }

   /**
   * Constructs a SelectUserGroupBase with a SemanticObject
   * @param base The SemanticObject with the properties for the SelectUserGroup
   */
    public SelectUserGroupBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

/**
* Gets the Users_ById property
* @return String with the Users_ById
*/
    public String getUsers_ById()
    {
        return getSemanticObject().getProperty(bsc_users_ById);
    }

/**
* Sets the Users_ById property
* @param value long with the Users_ById
*/
    public void setUsers_ById(String value)
    {
        getSemanticObject().setProperty(bsc_users_ById, value);
    }
}
