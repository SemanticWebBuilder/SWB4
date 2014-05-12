package org.semanticwb.bsc.tracing.base;


   /**
   * Gestiona la información de un control en un Riesgo. 
   */
public abstract class ControlBase extends org.semanticwb.bsc.tracing.BSCTracing implements org.semanticwb.model.UserGroupable,org.semanticwb.model.Filterable,org.semanticwb.bsc.Recognizable,org.semanticwb.bsc.Help,org.semanticwb.model.Roleable,org.semanticwb.model.FilterableNode,org.semanticwb.model.Activeable,org.semanticwb.model.Traceable,org.semanticwb.model.Descriptiveable
{
   /**
   * Permite almacenar los valores que pueden tomar los determinantes definidos para conocer si son suficientes o no los controles de un riesgo
   */
    public static final org.semanticwb.platform.SemanticClass bsc_DeterminantValue=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#DeterminantValue");
   /**
   * Persiste los valores específicos de los determinantes para un riesgo
   */
    public static final org.semanticwb.platform.SemanticProperty bsc_hasDeterminantValue=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#hasDeterminantValue");
   /**
   * Es de solo lectura en la administración y es la clasificación del Control
   */
    public static final org.semanticwb.platform.SemanticProperty bsc_controlType=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#controlType");
   /**
   * Es de solo lectura. Y permite determinar si un control es suficiente o deficiente para controlar un riesgo
   */
    public static final org.semanticwb.platform.SemanticProperty bsc_determiningControl=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#determiningControl");
   /**
   * Clase que define un factor de riesgo.
   */
    public static final org.semanticwb.platform.SemanticClass bsc_Factor=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#Factor");
    public static final org.semanticwb.platform.SemanticProperty bsc_hasControlInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#hasControlInv");
   /**
   * Gestiona la información de un control en un Riesgo.
   */
    public static final org.semanticwb.platform.SemanticClass bsc_Control=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#Control");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#Control");

    public static class ClassMgr
    {
       /**
       * Returns a list of Control for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.bsc.tracing.Control
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.Control> listControls(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.Control>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.bsc.tracing.Control for all models
       * @return Iterator of org.semanticwb.bsc.tracing.Control
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.Control> listControls()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.Control>(it, true);
        }

        public static org.semanticwb.bsc.tracing.Control createControl(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.bsc.tracing.Control.ClassMgr.createControl(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.bsc.tracing.Control
       * @param id Identifier for org.semanticwb.bsc.tracing.Control
       * @param model Model of the org.semanticwb.bsc.tracing.Control
       * @return A org.semanticwb.bsc.tracing.Control
       */
        public static org.semanticwb.bsc.tracing.Control getControl(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.tracing.Control)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.bsc.tracing.Control
       * @param id Identifier for org.semanticwb.bsc.tracing.Control
       * @param model Model of the org.semanticwb.bsc.tracing.Control
       * @return A org.semanticwb.bsc.tracing.Control
       */
        public static org.semanticwb.bsc.tracing.Control createControl(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.tracing.Control)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.bsc.tracing.Control
       * @param id Identifier for org.semanticwb.bsc.tracing.Control
       * @param model Model of the org.semanticwb.bsc.tracing.Control
       */
        public static void removeControl(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.bsc.tracing.Control
       * @param id Identifier for org.semanticwb.bsc.tracing.Control
       * @param model Model of the org.semanticwb.bsc.tracing.Control
       * @return true if the org.semanticwb.bsc.tracing.Control exists, false otherwise
       */

        public static boolean hasControl(String id, org.semanticwb.model.SWBModel model)
        {
            return (getControl(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.bsc.tracing.Control with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.bsc.tracing.Control
       * @return Iterator with all the org.semanticwb.bsc.tracing.Control
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.Control> listControlByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.Control> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.tracing.Control with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.bsc.tracing.Control
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.Control> listControlByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.Control> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.tracing.Control with a determined DeterminantValue
       * @param value DeterminantValue of the type org.semanticwb.bsc.tracing.DeterminantValue
       * @param model Model of the org.semanticwb.bsc.tracing.Control
       * @return Iterator with all the org.semanticwb.bsc.tracing.Control
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.Control> listControlByDeterminantValue(org.semanticwb.bsc.tracing.DeterminantValue value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.Control> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(bsc_hasDeterminantValue, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.tracing.Control with a determined DeterminantValue
       * @param value DeterminantValue of the type org.semanticwb.bsc.tracing.DeterminantValue
       * @return Iterator with all the org.semanticwb.bsc.tracing.Control
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.Control> listControlByDeterminantValue(org.semanticwb.bsc.tracing.DeterminantValue value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.Control> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(bsc_hasDeterminantValue,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.tracing.Control with a determined UserGroup
       * @param value UserGroup of the type org.semanticwb.model.UserGroup
       * @param model Model of the org.semanticwb.bsc.tracing.Control
       * @return Iterator with all the org.semanticwb.bsc.tracing.Control
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.Control> listControlByUserGroup(org.semanticwb.model.UserGroup value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.Control> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasUserGroup, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.tracing.Control with a determined UserGroup
       * @param value UserGroup of the type org.semanticwb.model.UserGroup
       * @return Iterator with all the org.semanticwb.bsc.tracing.Control
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.Control> listControlByUserGroup(org.semanticwb.model.UserGroup value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.Control> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasUserGroup,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.tracing.Control with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.bsc.tracing.Control
       * @return Iterator with all the org.semanticwb.bsc.tracing.Control
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.Control> listControlByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.Control> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.tracing.Control with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.bsc.tracing.Control
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.Control> listControlByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.Control> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.tracing.Control with a determined Role
       * @param value Role of the type org.semanticwb.model.Role
       * @param model Model of the org.semanticwb.bsc.tracing.Control
       * @return Iterator with all the org.semanticwb.bsc.tracing.Control
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.Control> listControlByRole(org.semanticwb.model.Role value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.Control> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasRole, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.tracing.Control with a determined Role
       * @param value Role of the type org.semanticwb.model.Role
       * @return Iterator with all the org.semanticwb.bsc.tracing.Control
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.Control> listControlByRole(org.semanticwb.model.Role value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.Control> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasRole,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.tracing.Control with a determined Factor
       * @param value Factor of the type org.semanticwb.bsc.tracing.Factor
       * @param model Model of the org.semanticwb.bsc.tracing.Control
       * @return Iterator with all the org.semanticwb.bsc.tracing.Control
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.Control> listControlByFactor(org.semanticwb.bsc.tracing.Factor value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.Control> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(bsc_hasControlInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.tracing.Control with a determined Factor
       * @param value Factor of the type org.semanticwb.bsc.tracing.Factor
       * @return Iterator with all the org.semanticwb.bsc.tracing.Control
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.Control> listControlByFactor(org.semanticwb.bsc.tracing.Factor value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.Control> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(bsc_hasControlInv,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static ControlBase.ClassMgr getControlClassMgr()
    {
        return new ControlBase.ClassMgr();
    }

   /**
   * Constructs a ControlBase with a SemanticObject
   * @param base The SemanticObject with the properties for the Control
   */
    public ControlBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
   /**
   * Gets all the org.semanticwb.bsc.tracing.DeterminantValue
   * @return A GenericIterator with all the org.semanticwb.bsc.tracing.DeterminantValue
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.DeterminantValue> listDeterminantValues()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.DeterminantValue>(getSemanticObject().listObjectProperties(bsc_hasDeterminantValue));
    }

   /**
   * Gets true if has a DeterminantValue
   * @param value org.semanticwb.bsc.tracing.DeterminantValue to verify
   * @return true if the org.semanticwb.bsc.tracing.DeterminantValue exists, false otherwise
   */
    public boolean hasDeterminantValue(org.semanticwb.bsc.tracing.DeterminantValue value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(bsc_hasDeterminantValue,value.getSemanticObject());
        }
        return ret;
    }
   /**
   * Adds a DeterminantValue
   * @param value org.semanticwb.bsc.tracing.DeterminantValue to add
   */

    public void addDeterminantValue(org.semanticwb.bsc.tracing.DeterminantValue value)
    {
        getSemanticObject().addObjectProperty(bsc_hasDeterminantValue, value.getSemanticObject());
    }
   /**
   * Removes all the DeterminantValue
   */

    public void removeAllDeterminantValue()
    {
        getSemanticObject().removeProperty(bsc_hasDeterminantValue);
    }
   /**
   * Removes a DeterminantValue
   * @param value org.semanticwb.bsc.tracing.DeterminantValue to remove
   */

    public void removeDeterminantValue(org.semanticwb.bsc.tracing.DeterminantValue value)
    {
        getSemanticObject().removeObjectProperty(bsc_hasDeterminantValue,value.getSemanticObject());
    }

   /**
   * Gets the DeterminantValue
   * @return a org.semanticwb.bsc.tracing.DeterminantValue
   */
    public org.semanticwb.bsc.tracing.DeterminantValue getDeterminantValue()
    {
         org.semanticwb.bsc.tracing.DeterminantValue ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(bsc_hasDeterminantValue);
         if(obj!=null)
         {
             ret=(org.semanticwb.bsc.tracing.DeterminantValue)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the ControlType property
* @return String with the ControlType
*/
    public String getControlType()
    {
        return getSemanticObject().getProperty(bsc_controlType);
    }

/**
* Sets the ControlType property
* @param value long with the ControlType
*/
    public void setControlType(String value)
    {
        getSemanticObject().setProperty(bsc_controlType, value);
    }

/**
* Gets the DeterminingControl property
* @return String with the DeterminingControl
*/
    public String getDeterminingControl()
    {
        return getSemanticObject().getProperty(bsc_determiningControl);
    }

/**
* Sets the DeterminingControl property
* @param value long with the DeterminingControl
*/
    public void setDeterminingControl(String value)
    {
        getSemanticObject().setProperty(bsc_determiningControl, value);
    }

/**
* Gets the Prefix property
* @return String with the Prefix
*/
    public String getPrefix()
    {
        //Override this method in Control object
        return getSemanticObject().getProperty(bsc_prefix,false);
    }

/**
* Sets the Prefix property
* @param value long with the Prefix
*/
    public void setPrefix(String value)
    {
        //Override this method in Control object
        getSemanticObject().setProperty(bsc_prefix, value,false);
    }
   /**
   * Sets the value for the property Factor
   * @param value Factor to set
   */

    public void setFactor(org.semanticwb.bsc.tracing.Factor value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(bsc_hasControlInv, value.getSemanticObject());
        }else
        {
            removeFactor();
        }
    }
   /**
   * Remove the value for Factor property
   */

    public void removeFactor()
    {
        getSemanticObject().removeProperty(bsc_hasControlInv);
    }

   /**
   * Gets the Factor
   * @return a org.semanticwb.bsc.tracing.Factor
   */
    public org.semanticwb.bsc.tracing.Factor getFactor()
    {
         org.semanticwb.bsc.tracing.Factor ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(bsc_hasControlInv);
         if(obj!=null)
         {
             ret=(org.semanticwb.bsc.tracing.Factor)obj.createGenericInstance();
         }
         return ret;
    }

   /**
   * Gets the BSC
   * @return a instance of org.semanticwb.bsc.BSC
   */
    public org.semanticwb.bsc.BSC getBSC()
    {
        return (org.semanticwb.bsc.BSC)getSemanticObject().getModel().getModelObject().createGenericInstance();
    }
}
