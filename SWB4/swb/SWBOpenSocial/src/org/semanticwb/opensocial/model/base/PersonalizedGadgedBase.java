package org.semanticwb.opensocial.model.base;


public abstract class PersonalizedGadgedBase extends org.semanticwb.model.SWBClass 
{
   /**
   * Un usuario es una persona que tiene relación con el portal a través de un método de acceso.
   */
    public static final org.semanticwb.platform.SemanticClass swb_User=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#User");
    public static final org.semanticwb.platform.SemanticProperty opensocial_user=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/opensocial#user");
    public static final org.semanticwb.platform.SemanticClass opensocial_UserPref=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/opensocial#UserPref");
    public static final org.semanticwb.platform.SemanticProperty opensocial_hasUserPrefs=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/opensocial#hasUserPrefs");
    public static final org.semanticwb.platform.SemanticClass opensocial_Gadget=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/opensocial#Gadget");
    public static final org.semanticwb.platform.SemanticProperty opensocial_gadget=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/opensocial#gadget");
    public static final org.semanticwb.platform.SemanticClass opensocial_PersonalizedGadged=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/opensocial#PersonalizedGadged");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/opensocial#PersonalizedGadged");

    public static class ClassMgr
    {
       /**
       * Returns a list of PersonalizedGadged for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.opensocial.model.PersonalizedGadged
       */

        public static java.util.Iterator<org.semanticwb.opensocial.model.PersonalizedGadged> listPersonalizedGadgeds(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.opensocial.model.PersonalizedGadged>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.opensocial.model.PersonalizedGadged for all models
       * @return Iterator of org.semanticwb.opensocial.model.PersonalizedGadged
       */

        public static java.util.Iterator<org.semanticwb.opensocial.model.PersonalizedGadged> listPersonalizedGadgeds()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.opensocial.model.PersonalizedGadged>(it, true);
        }

        public static org.semanticwb.opensocial.model.PersonalizedGadged createPersonalizedGadged(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.opensocial.model.PersonalizedGadged.ClassMgr.createPersonalizedGadged(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.opensocial.model.PersonalizedGadged
       * @param id Identifier for org.semanticwb.opensocial.model.PersonalizedGadged
       * @param model Model of the org.semanticwb.opensocial.model.PersonalizedGadged
       * @return A org.semanticwb.opensocial.model.PersonalizedGadged
       */
        public static org.semanticwb.opensocial.model.PersonalizedGadged getPersonalizedGadged(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.opensocial.model.PersonalizedGadged)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.opensocial.model.PersonalizedGadged
       * @param id Identifier for org.semanticwb.opensocial.model.PersonalizedGadged
       * @param model Model of the org.semanticwb.opensocial.model.PersonalizedGadged
       * @return A org.semanticwb.opensocial.model.PersonalizedGadged
       */
        public static org.semanticwb.opensocial.model.PersonalizedGadged createPersonalizedGadged(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.opensocial.model.PersonalizedGadged)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.opensocial.model.PersonalizedGadged
       * @param id Identifier for org.semanticwb.opensocial.model.PersonalizedGadged
       * @param model Model of the org.semanticwb.opensocial.model.PersonalizedGadged
       */
        public static void removePersonalizedGadged(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.opensocial.model.PersonalizedGadged
       * @param id Identifier for org.semanticwb.opensocial.model.PersonalizedGadged
       * @param model Model of the org.semanticwb.opensocial.model.PersonalizedGadged
       * @return true if the org.semanticwb.opensocial.model.PersonalizedGadged exists, false otherwise
       */

        public static boolean hasPersonalizedGadged(String id, org.semanticwb.model.SWBModel model)
        {
            return (getPersonalizedGadged(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.opensocial.model.PersonalizedGadged with a determined User
       * @param value User of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.opensocial.model.PersonalizedGadged
       * @return Iterator with all the org.semanticwb.opensocial.model.PersonalizedGadged
       */

        public static java.util.Iterator<org.semanticwb.opensocial.model.PersonalizedGadged> listPersonalizedGadgedByUser(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.opensocial.model.PersonalizedGadged> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(opensocial_user, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.opensocial.model.PersonalizedGadged with a determined User
       * @param value User of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.opensocial.model.PersonalizedGadged
       */

        public static java.util.Iterator<org.semanticwb.opensocial.model.PersonalizedGadged> listPersonalizedGadgedByUser(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.opensocial.model.PersonalizedGadged> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(opensocial_user,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.opensocial.model.PersonalizedGadged with a determined UserPrefs
       * @param value UserPrefs of the type org.semanticwb.opensocial.model.UserPref
       * @param model Model of the org.semanticwb.opensocial.model.PersonalizedGadged
       * @return Iterator with all the org.semanticwb.opensocial.model.PersonalizedGadged
       */

        public static java.util.Iterator<org.semanticwb.opensocial.model.PersonalizedGadged> listPersonalizedGadgedByUserPrefs(org.semanticwb.opensocial.model.UserPref value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.opensocial.model.PersonalizedGadged> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(opensocial_hasUserPrefs, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.opensocial.model.PersonalizedGadged with a determined UserPrefs
       * @param value UserPrefs of the type org.semanticwb.opensocial.model.UserPref
       * @return Iterator with all the org.semanticwb.opensocial.model.PersonalizedGadged
       */

        public static java.util.Iterator<org.semanticwb.opensocial.model.PersonalizedGadged> listPersonalizedGadgedByUserPrefs(org.semanticwb.opensocial.model.UserPref value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.opensocial.model.PersonalizedGadged> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(opensocial_hasUserPrefs,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.opensocial.model.PersonalizedGadged with a determined Gadget
       * @param value Gadget of the type org.semanticwb.opensocial.model.Gadget
       * @param model Model of the org.semanticwb.opensocial.model.PersonalizedGadged
       * @return Iterator with all the org.semanticwb.opensocial.model.PersonalizedGadged
       */

        public static java.util.Iterator<org.semanticwb.opensocial.model.PersonalizedGadged> listPersonalizedGadgedByGadget(org.semanticwb.opensocial.model.Gadget value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.opensocial.model.PersonalizedGadged> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(opensocial_gadget, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.opensocial.model.PersonalizedGadged with a determined Gadget
       * @param value Gadget of the type org.semanticwb.opensocial.model.Gadget
       * @return Iterator with all the org.semanticwb.opensocial.model.PersonalizedGadged
       */

        public static java.util.Iterator<org.semanticwb.opensocial.model.PersonalizedGadged> listPersonalizedGadgedByGadget(org.semanticwb.opensocial.model.Gadget value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.opensocial.model.PersonalizedGadged> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(opensocial_gadget,value.getSemanticObject(),sclass));
            return it;
        }
    }

   /**
   * Constructs a PersonalizedGadgedBase with a SemanticObject
   * @param base The SemanticObject with the properties for the PersonalizedGadged
   */
    public PersonalizedGadgedBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
   /**
   * Sets the value for the property User
   * @param value User to set
   */

    public void setUser(org.semanticwb.model.User value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(opensocial_user, value.getSemanticObject());
        }else
        {
            removeUser();
        }
    }
   /**
   * Remove the value for User property
   */

    public void removeUser()
    {
        getSemanticObject().removeProperty(opensocial_user);
    }

   /**
   * Gets the User
   * @return a org.semanticwb.model.User
   */
    public org.semanticwb.model.User getUser()
    {
         org.semanticwb.model.User ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(opensocial_user);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.User)obj.createGenericInstance();
         }
         return ret;
    }
   /**
   * Gets all the org.semanticwb.opensocial.model.UserPref
   * @return A GenericIterator with all the org.semanticwb.opensocial.model.UserPref
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.opensocial.model.UserPref> listUserPrefses()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.opensocial.model.UserPref>(getSemanticObject().listObjectProperties(opensocial_hasUserPrefs));
    }

   /**
   * Gets true if has a UserPrefs
   * @param value org.semanticwb.opensocial.model.UserPref to verify
   * @return true if the org.semanticwb.opensocial.model.UserPref exists, false otherwise
   */
    public boolean hasUserPrefs(org.semanticwb.opensocial.model.UserPref value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(opensocial_hasUserPrefs,value.getSemanticObject());
        }
        return ret;
    }
   /**
   * Adds a UserPrefs
   * @param value org.semanticwb.opensocial.model.UserPref to add
   */

    public void addUserPrefs(org.semanticwb.opensocial.model.UserPref value)
    {
        getSemanticObject().addObjectProperty(opensocial_hasUserPrefs, value.getSemanticObject());
    }
   /**
   * Removes all the UserPrefs
   */

    public void removeAllUserPrefs()
    {
        getSemanticObject().removeProperty(opensocial_hasUserPrefs);
    }
   /**
   * Removes a UserPrefs
   * @param value org.semanticwb.opensocial.model.UserPref to remove
   */

    public void removeUserPrefs(org.semanticwb.opensocial.model.UserPref value)
    {
        getSemanticObject().removeObjectProperty(opensocial_hasUserPrefs,value.getSemanticObject());
    }

   /**
   * Gets the UserPrefs
   * @return a org.semanticwb.opensocial.model.UserPref
   */
    public org.semanticwb.opensocial.model.UserPref getUserPrefs()
    {
         org.semanticwb.opensocial.model.UserPref ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(opensocial_hasUserPrefs);
         if(obj!=null)
         {
             ret=(org.semanticwb.opensocial.model.UserPref)obj.createGenericInstance();
         }
         return ret;
    }
   /**
   * Sets the value for the property Gadget
   * @param value Gadget to set
   */

    public void setGadget(org.semanticwb.opensocial.model.Gadget value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(opensocial_gadget, value.getSemanticObject());
        }else
        {
            removeGadget();
        }
    }
   /**
   * Remove the value for Gadget property
   */

    public void removeGadget()
    {
        getSemanticObject().removeProperty(opensocial_gadget);
    }

   /**
   * Gets the Gadget
   * @return a org.semanticwb.opensocial.model.Gadget
   */
    public org.semanticwb.opensocial.model.Gadget getGadget()
    {
         org.semanticwb.opensocial.model.Gadget ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(opensocial_gadget);
         if(obj!=null)
         {
             ret=(org.semanticwb.opensocial.model.Gadget)obj.createGenericInstance();
         }
         return ret;
    }
}
