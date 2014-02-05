package org.semanticwb.bsc.accessory.base;


public abstract class StateBase extends org.semanticwb.bsc.accessory.BSCAccessory implements org.semanticwb.model.Undeleteable,org.semanticwb.model.UserGroupable,org.semanticwb.bsc.Machinable,org.semanticwb.model.Descriptiveable,org.semanticwb.model.Iconable,org.semanticwb.model.FilterableNode,org.semanticwb.model.Traceable,org.semanticwb.model.Roleable,org.semanticwb.model.Filterable,org.semanticwb.bsc.Help,org.semanticwb.model.Activeable
{
    public static final org.semanticwb.platform.SemanticClass bsc_StateGroup=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#StateGroup");
    public static final org.semanticwb.platform.SemanticProperty bsc_stateGroupInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#stateGroupInv");
   /**
   * Cualquier elemento BSC al que se le puedan asignar estados de semaforización
   */
    public static final org.semanticwb.platform.SemanticClass bsc_Status=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#Status");
    public static final org.semanticwb.platform.SemanticProperty bsc_hasStatusInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#hasStatusInv");
    public static final org.semanticwb.platform.SemanticProperty bsc_icon=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#icon");
    public static final org.semanticwb.platform.SemanticClass bsc_State=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#State");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#State");

    public static class ClassMgr
    {
       /**
       * Returns a list of State for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.bsc.accessory.State
       */

        public static java.util.Iterator<org.semanticwb.bsc.accessory.State> listStates(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.accessory.State>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.bsc.accessory.State for all models
       * @return Iterator of org.semanticwb.bsc.accessory.State
       */

        public static java.util.Iterator<org.semanticwb.bsc.accessory.State> listStates()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.accessory.State>(it, true);
        }

        public static org.semanticwb.bsc.accessory.State createState(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.bsc.accessory.State.ClassMgr.createState(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.bsc.accessory.State
       * @param id Identifier for org.semanticwb.bsc.accessory.State
       * @param model Model of the org.semanticwb.bsc.accessory.State
       * @return A org.semanticwb.bsc.accessory.State
       */
        public static org.semanticwb.bsc.accessory.State getState(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.accessory.State)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.bsc.accessory.State
       * @param id Identifier for org.semanticwb.bsc.accessory.State
       * @param model Model of the org.semanticwb.bsc.accessory.State
       * @return A org.semanticwb.bsc.accessory.State
       */
        public static org.semanticwb.bsc.accessory.State createState(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.accessory.State)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.bsc.accessory.State
       * @param id Identifier for org.semanticwb.bsc.accessory.State
       * @param model Model of the org.semanticwb.bsc.accessory.State
       */
        public static void removeState(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.bsc.accessory.State
       * @param id Identifier for org.semanticwb.bsc.accessory.State
       * @param model Model of the org.semanticwb.bsc.accessory.State
       * @return true if the org.semanticwb.bsc.accessory.State exists, false otherwise
       */

        public static boolean hasState(String id, org.semanticwb.model.SWBModel model)
        {
            return (getState(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.bsc.accessory.State with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.bsc.accessory.State
       * @return Iterator with all the org.semanticwb.bsc.accessory.State
       */

        public static java.util.Iterator<org.semanticwb.bsc.accessory.State> listStateByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.accessory.State> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.accessory.State with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.bsc.accessory.State
       */

        public static java.util.Iterator<org.semanticwb.bsc.accessory.State> listStateByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.accessory.State> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.accessory.State with a determined Previus
       * @param value Previus of the type org.semanticwb.bsc.Machinable
       * @param model Model of the org.semanticwb.bsc.accessory.State
       * @return Iterator with all the org.semanticwb.bsc.accessory.State
       */

        public static java.util.Iterator<org.semanticwb.bsc.accessory.State> listStateByPrevius(org.semanticwb.bsc.Machinable value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.accessory.State> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(bsc_previus, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.accessory.State with a determined Previus
       * @param value Previus of the type org.semanticwb.bsc.Machinable
       * @return Iterator with all the org.semanticwb.bsc.accessory.State
       */

        public static java.util.Iterator<org.semanticwb.bsc.accessory.State> listStateByPrevius(org.semanticwb.bsc.Machinable value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.accessory.State> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(bsc_previus,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.accessory.State with a determined UserGroup
       * @param value UserGroup of the type org.semanticwb.model.UserGroup
       * @param model Model of the org.semanticwb.bsc.accessory.State
       * @return Iterator with all the org.semanticwb.bsc.accessory.State
       */

        public static java.util.Iterator<org.semanticwb.bsc.accessory.State> listStateByUserGroup(org.semanticwb.model.UserGroup value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.accessory.State> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasUserGroup, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.accessory.State with a determined UserGroup
       * @param value UserGroup of the type org.semanticwb.model.UserGroup
       * @return Iterator with all the org.semanticwb.bsc.accessory.State
       */

        public static java.util.Iterator<org.semanticwb.bsc.accessory.State> listStateByUserGroup(org.semanticwb.model.UserGroup value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.accessory.State> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasUserGroup,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.accessory.State with a determined StateGroup
       * @param value StateGroup of the type org.semanticwb.bsc.accessory.StateGroup
       * @param model Model of the org.semanticwb.bsc.accessory.State
       * @return Iterator with all the org.semanticwb.bsc.accessory.State
       */

        public static java.util.Iterator<org.semanticwb.bsc.accessory.State> listStateByStateGroup(org.semanticwb.bsc.accessory.StateGroup value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.accessory.State> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(bsc_stateGroupInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.accessory.State with a determined StateGroup
       * @param value StateGroup of the type org.semanticwb.bsc.accessory.StateGroup
       * @return Iterator with all the org.semanticwb.bsc.accessory.State
       */

        public static java.util.Iterator<org.semanticwb.bsc.accessory.State> listStateByStateGroup(org.semanticwb.bsc.accessory.StateGroup value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.accessory.State> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(bsc_stateGroupInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.accessory.State with a determined Next
       * @param value Next of the type org.semanticwb.bsc.Machinable
       * @param model Model of the org.semanticwb.bsc.accessory.State
       * @return Iterator with all the org.semanticwb.bsc.accessory.State
       */

        public static java.util.Iterator<org.semanticwb.bsc.accessory.State> listStateByNext(org.semanticwb.bsc.Machinable value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.accessory.State> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(bsc_next, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.accessory.State with a determined Next
       * @param value Next of the type org.semanticwb.bsc.Machinable
       * @return Iterator with all the org.semanticwb.bsc.accessory.State
       */

        public static java.util.Iterator<org.semanticwb.bsc.accessory.State> listStateByNext(org.semanticwb.bsc.Machinable value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.accessory.State> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(bsc_next,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.accessory.State with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.bsc.accessory.State
       * @return Iterator with all the org.semanticwb.bsc.accessory.State
       */

        public static java.util.Iterator<org.semanticwb.bsc.accessory.State> listStateByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.accessory.State> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.accessory.State with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.bsc.accessory.State
       */

        public static java.util.Iterator<org.semanticwb.bsc.accessory.State> listStateByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.accessory.State> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.accessory.State with a determined Role
       * @param value Role of the type org.semanticwb.model.Role
       * @param model Model of the org.semanticwb.bsc.accessory.State
       * @return Iterator with all the org.semanticwb.bsc.accessory.State
       */

        public static java.util.Iterator<org.semanticwb.bsc.accessory.State> listStateByRole(org.semanticwb.model.Role value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.accessory.State> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasRole, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.accessory.State with a determined Role
       * @param value Role of the type org.semanticwb.model.Role
       * @return Iterator with all the org.semanticwb.bsc.accessory.State
       */

        public static java.util.Iterator<org.semanticwb.bsc.accessory.State> listStateByRole(org.semanticwb.model.Role value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.accessory.State> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasRole,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.accessory.State with a determined Status
       * @param value Status of the type org.semanticwb.bsc.Status
       * @param model Model of the org.semanticwb.bsc.accessory.State
       * @return Iterator with all the org.semanticwb.bsc.accessory.State
       */

        public static java.util.Iterator<org.semanticwb.bsc.accessory.State> listStateByStatus(org.semanticwb.bsc.Status value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.accessory.State> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(bsc_hasStatusInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.accessory.State with a determined Status
       * @param value Status of the type org.semanticwb.bsc.Status
       * @return Iterator with all the org.semanticwb.bsc.accessory.State
       */

        public static java.util.Iterator<org.semanticwb.bsc.accessory.State> listStateByStatus(org.semanticwb.bsc.Status value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.accessory.State> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(bsc_hasStatusInv,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static StateBase.ClassMgr getStateClassMgr()
    {
        return new StateBase.ClassMgr();
    }

   /**
   * Constructs a StateBase with a SemanticObject
   * @param base The SemanticObject with the properties for the State
   */
    public StateBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
   /**
   * Sets the value for the property Previus
   * @param value Previus to set
   */

    public void setPrevius(org.semanticwb.bsc.Machinable value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(bsc_previus, value.getSemanticObject());
        }else
        {
            removePrevius();
        }
    }
   /**
   * Remove the value for Previus property
   */

    public void removePrevius()
    {
        getSemanticObject().removeProperty(bsc_previus);
    }

   /**
   * Gets the Previus
   * @return a org.semanticwb.bsc.Machinable
   */
    public org.semanticwb.bsc.Machinable getPrevius()
    {
         org.semanticwb.bsc.Machinable ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(bsc_previus);
         if(obj!=null)
         {
             ret=(org.semanticwb.bsc.Machinable)obj.createGenericInstance();
         }
         return ret;
    }
   /**
   * Sets the value for the property StateGroup
   * @param value StateGroup to set
   */

    public void setStateGroup(org.semanticwb.bsc.accessory.StateGroup value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(bsc_stateGroupInv, value.getSemanticObject());
        }else
        {
            removeStateGroup();
        }
    }
   /**
   * Remove the value for StateGroup property
   */

    public void removeStateGroup()
    {
        getSemanticObject().removeProperty(bsc_stateGroupInv);
    }

   /**
   * Gets the StateGroup
   * @return a org.semanticwb.bsc.accessory.StateGroup
   */
    public org.semanticwb.bsc.accessory.StateGroup getStateGroup()
    {
         org.semanticwb.bsc.accessory.StateGroup ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(bsc_stateGroupInv);
         if(obj!=null)
         {
             ret=(org.semanticwb.bsc.accessory.StateGroup)obj.createGenericInstance();
         }
         return ret;
    }
   /**
   * Sets the value for the property Next
   * @param value Next to set
   */

    public void setNext(org.semanticwb.bsc.Machinable value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(bsc_next, value.getSemanticObject());
        }else
        {
            removeNext();
        }
    }
   /**
   * Remove the value for Next property
   */

    public void removeNext()
    {
        getSemanticObject().removeProperty(bsc_next);
    }

   /**
   * Gets the Next
   * @return a org.semanticwb.bsc.Machinable
   */
    public org.semanticwb.bsc.Machinable getNext()
    {
         org.semanticwb.bsc.Machinable ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(bsc_next);
         if(obj!=null)
         {
             ret=(org.semanticwb.bsc.Machinable)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the IconClass property
* @return String with the IconClass
*/
    public String getIconClass()
    {
        return getSemanticObject().getProperty(swb_iconClass);
    }

/**
* Sets the IconClass property
* @param value long with the IconClass
*/
    public void setIconClass(String value)
    {
        getSemanticObject().setProperty(swb_iconClass, value);
    }

/**
* Gets the Orden property
* @return int with the Orden
*/
    public int getOrden()
    {
        return getSemanticObject().getIntProperty(bsc_orden);
    }

/**
* Sets the Orden property
* @param value long with the Orden
*/
    public void setOrden(int value)
    {
        getSemanticObject().setIntProperty(bsc_orden, value);
    }
   /**
   * Gets all the org.semanticwb.bsc.Status
   * @return A GenericIterator with all the org.semanticwb.bsc.Status
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.bsc.Status> listStatuses()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.Status>(getSemanticObject().listObjectProperties(bsc_hasStatusInv));
    }

   /**
   * Gets true if has a Status
   * @param value org.semanticwb.bsc.Status to verify
   * @return true if the org.semanticwb.bsc.Status exists, false otherwise
   */
    public boolean hasStatus(org.semanticwb.bsc.Status value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(bsc_hasStatusInv,value.getSemanticObject());
        }
        return ret;
    }

   /**
   * Gets the Status
   * @return a org.semanticwb.bsc.Status
   */
    public org.semanticwb.bsc.Status getStatus()
    {
         org.semanticwb.bsc.Status ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(bsc_hasStatusInv);
         if(obj!=null)
         {
             ret=(org.semanticwb.bsc.Status)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the Icon property
* @return String with the Icon
*/
    public String getIcon()
    {
        return getSemanticObject().getProperty(bsc_icon);
    }

/**
* Sets the Icon property
* @param value long with the Icon
*/
    public void setIcon(String value)
    {
        getSemanticObject().setProperty(bsc_icon, value);
    }
}
