package org.semanticwb.bsc.formelement.base;


public abstract class SelectUsersBase extends org.semanticwb.model.SelectOne 
{
    public static final org.semanticwb.platform.SemanticProperty bsc_areaSelect=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#areaSelect");
    public static final org.semanticwb.platform.SemanticClass bsc_SelectUsers=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#SelectUsers");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#SelectUsers");

    public static class ClassMgr
    {
       /**
       * Returns a list of SelectUsers for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.bsc.formelement.SelectUsers
       */

        public static java.util.Iterator<org.semanticwb.bsc.formelement.SelectUsers> listSelectUserses(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.formelement.SelectUsers>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.bsc.formelement.SelectUsers for all models
       * @return Iterator of org.semanticwb.bsc.formelement.SelectUsers
       */

        public static java.util.Iterator<org.semanticwb.bsc.formelement.SelectUsers> listSelectUserses()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.formelement.SelectUsers>(it, true);
        }
       /**
       * Gets a org.semanticwb.bsc.formelement.SelectUsers
       * @param id Identifier for org.semanticwb.bsc.formelement.SelectUsers
       * @param model Model of the org.semanticwb.bsc.formelement.SelectUsers
       * @return A org.semanticwb.bsc.formelement.SelectUsers
       */
        public static org.semanticwb.bsc.formelement.SelectUsers getSelectUsers(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.formelement.SelectUsers)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.bsc.formelement.SelectUsers
       * @param id Identifier for org.semanticwb.bsc.formelement.SelectUsers
       * @param model Model of the org.semanticwb.bsc.formelement.SelectUsers
       * @return A org.semanticwb.bsc.formelement.SelectUsers
       */
        public static org.semanticwb.bsc.formelement.SelectUsers createSelectUsers(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.formelement.SelectUsers)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.bsc.formelement.SelectUsers
       * @param id Identifier for org.semanticwb.bsc.formelement.SelectUsers
       * @param model Model of the org.semanticwb.bsc.formelement.SelectUsers
       */
        public static void removeSelectUsers(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.bsc.formelement.SelectUsers
       * @param id Identifier for org.semanticwb.bsc.formelement.SelectUsers
       * @param model Model of the org.semanticwb.bsc.formelement.SelectUsers
       * @return true if the org.semanticwb.bsc.formelement.SelectUsers exists, false otherwise
       */

        public static boolean hasSelectUsers(String id, org.semanticwb.model.SWBModel model)
        {
            return (getSelectUsers(id, model)!=null);
        }
    }

    public static SelectUsersBase.ClassMgr getSelectUsersClassMgr()
    {
        return new SelectUsersBase.ClassMgr();
    }

   /**
   * Constructs a SelectUsersBase with a SemanticObject
   * @param base The SemanticObject with the properties for the SelectUsers
   */
    public SelectUsersBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

/**
* Gets the AreaSelect property
* @return String with the AreaSelect
*/
    public String getAreaSelect()
    {
        return getSemanticObject().getProperty(bsc_areaSelect);
    }

/**
* Sets the AreaSelect property
* @param value long with the AreaSelect
*/
    public void setAreaSelect(String value)
    {
        getSemanticObject().setProperty(bsc_areaSelect, value);
    }
}
