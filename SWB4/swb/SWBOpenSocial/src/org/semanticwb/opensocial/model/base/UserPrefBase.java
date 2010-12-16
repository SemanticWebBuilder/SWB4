package org.semanticwb.opensocial.model.base;


public abstract class UserPrefBase extends org.semanticwb.model.SWBClass 
{
    public static final org.semanticwb.platform.SemanticProperty opensocial_name=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/opensocial#name");
    public static final org.semanticwb.platform.SemanticProperty opensocial_value=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/opensocial#value");
    public static final org.semanticwb.platform.SemanticClass opensocial_UserPref=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/opensocial#UserPref");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/opensocial#UserPref");

    public static class ClassMgr
    {
       /**
       * Returns a list of UserPref for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.opensocial.model.UserPref
       */

        public static java.util.Iterator<org.semanticwb.opensocial.model.UserPref> listUserPrefs(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.opensocial.model.UserPref>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.opensocial.model.UserPref for all models
       * @return Iterator of org.semanticwb.opensocial.model.UserPref
       */

        public static java.util.Iterator<org.semanticwb.opensocial.model.UserPref> listUserPrefs()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.opensocial.model.UserPref>(it, true);
        }

        public static org.semanticwb.opensocial.model.UserPref createUserPref(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.opensocial.model.UserPref.ClassMgr.createUserPref(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.opensocial.model.UserPref
       * @param id Identifier for org.semanticwb.opensocial.model.UserPref
       * @param model Model of the org.semanticwb.opensocial.model.UserPref
       * @return A org.semanticwb.opensocial.model.UserPref
       */
        public static org.semanticwb.opensocial.model.UserPref getUserPref(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.opensocial.model.UserPref)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.opensocial.model.UserPref
       * @param id Identifier for org.semanticwb.opensocial.model.UserPref
       * @param model Model of the org.semanticwb.opensocial.model.UserPref
       * @return A org.semanticwb.opensocial.model.UserPref
       */
        public static org.semanticwb.opensocial.model.UserPref createUserPref(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.opensocial.model.UserPref)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.opensocial.model.UserPref
       * @param id Identifier for org.semanticwb.opensocial.model.UserPref
       * @param model Model of the org.semanticwb.opensocial.model.UserPref
       */
        public static void removeUserPref(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.opensocial.model.UserPref
       * @param id Identifier for org.semanticwb.opensocial.model.UserPref
       * @param model Model of the org.semanticwb.opensocial.model.UserPref
       * @return true if the org.semanticwb.opensocial.model.UserPref exists, false otherwise
       */

        public static boolean hasUserPref(String id, org.semanticwb.model.SWBModel model)
        {
            return (getUserPref(id, model)!=null);
        }
    }

   /**
   * Constructs a UserPrefBase with a SemanticObject
   * @param base The SemanticObject with the properties for the UserPref
   */
    public UserPrefBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

/**
* Gets the Name property
* @return String with the Name
*/
    public String getName()
    {
        return getSemanticObject().getProperty(opensocial_name);
    }

/**
* Sets the Name property
* @param value long with the Name
*/
    public void setName(String value)
    {
        getSemanticObject().setProperty(opensocial_name, value);
    }

/**
* Gets the Value property
* @return String with the Value
*/
    public String getValue()
    {
        return getSemanticObject().getProperty(opensocial_value);
    }

/**
* Sets the Value property
* @param value long with the Value
*/
    public void setValue(String value)
    {
        getSemanticObject().setProperty(opensocial_value, value);
    }
}
