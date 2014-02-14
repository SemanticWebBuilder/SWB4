package org.semanticwb.bsc.tracing.base;


   /**
   * Permite almacenar los valores que pueden tomar los determinantes definidos para conocer si son suficientes o no los controles de un riesgo 
   */
public abstract class DeterminantValueBase extends org.semanticwb.bsc.tracing.BSCTracing implements org.semanticwb.model.FilterableNode,org.semanticwb.model.UserGroupable,org.semanticwb.model.Activeable,org.semanticwb.model.Descriptiveable,org.semanticwb.model.Traceable,org.semanticwb.model.Roleable,org.semanticwb.model.Filterable,org.semanticwb.bsc.Help
{
   /**
   * Determinante que definen si un control es suficiente o insuficiente para un Riesgo
   */
    public static final org.semanticwb.platform.SemanticClass bsc_Determinant=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#Determinant");
   /**
   * Es el determinante sobre el Control
   */
    public static final org.semanticwb.platform.SemanticProperty bsc_determinant=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#determinant");
   /**
   * Valor sobre el determinante del Control
   */
    public static final org.semanticwb.platform.SemanticProperty bsc_isDeterminant=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#isDeterminant");
   /**
   * Permite almacenar los valores que pueden tomar los determinantes definidos para conocer si son suficientes o no los controles de un riesgo
   */
    public static final org.semanticwb.platform.SemanticClass bsc_DeterminantValue=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#DeterminantValue");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#DeterminantValue");

    public static class ClassMgr
    {
       /**
       * Returns a list of DeterminantValue for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.bsc.tracing.DeterminantValue
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.DeterminantValue> listDeterminantValues(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.DeterminantValue>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.bsc.tracing.DeterminantValue for all models
       * @return Iterator of org.semanticwb.bsc.tracing.DeterminantValue
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.DeterminantValue> listDeterminantValues()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.DeterminantValue>(it, true);
        }

        public static org.semanticwb.bsc.tracing.DeterminantValue createDeterminantValue(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.bsc.tracing.DeterminantValue.ClassMgr.createDeterminantValue(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.bsc.tracing.DeterminantValue
       * @param id Identifier for org.semanticwb.bsc.tracing.DeterminantValue
       * @param model Model of the org.semanticwb.bsc.tracing.DeterminantValue
       * @return A org.semanticwb.bsc.tracing.DeterminantValue
       */
        public static org.semanticwb.bsc.tracing.DeterminantValue getDeterminantValue(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.tracing.DeterminantValue)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.bsc.tracing.DeterminantValue
       * @param id Identifier for org.semanticwb.bsc.tracing.DeterminantValue
       * @param model Model of the org.semanticwb.bsc.tracing.DeterminantValue
       * @return A org.semanticwb.bsc.tracing.DeterminantValue
       */
        public static org.semanticwb.bsc.tracing.DeterminantValue createDeterminantValue(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.tracing.DeterminantValue)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.bsc.tracing.DeterminantValue
       * @param id Identifier for org.semanticwb.bsc.tracing.DeterminantValue
       * @param model Model of the org.semanticwb.bsc.tracing.DeterminantValue
       */
        public static void removeDeterminantValue(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.bsc.tracing.DeterminantValue
       * @param id Identifier for org.semanticwb.bsc.tracing.DeterminantValue
       * @param model Model of the org.semanticwb.bsc.tracing.DeterminantValue
       * @return true if the org.semanticwb.bsc.tracing.DeterminantValue exists, false otherwise
       */

        public static boolean hasDeterminantValue(String id, org.semanticwb.model.SWBModel model)
        {
            return (getDeterminantValue(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.bsc.tracing.DeterminantValue with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.bsc.tracing.DeterminantValue
       * @return Iterator with all the org.semanticwb.bsc.tracing.DeterminantValue
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.DeterminantValue> listDeterminantValueByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.DeterminantValue> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.tracing.DeterminantValue with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.bsc.tracing.DeterminantValue
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.DeterminantValue> listDeterminantValueByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.DeterminantValue> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.tracing.DeterminantValue with a determined UserGroup
       * @param value UserGroup of the type org.semanticwb.model.UserGroup
       * @param model Model of the org.semanticwb.bsc.tracing.DeterminantValue
       * @return Iterator with all the org.semanticwb.bsc.tracing.DeterminantValue
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.DeterminantValue> listDeterminantValueByUserGroup(org.semanticwb.model.UserGroup value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.DeterminantValue> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasUserGroup, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.tracing.DeterminantValue with a determined UserGroup
       * @param value UserGroup of the type org.semanticwb.model.UserGroup
       * @return Iterator with all the org.semanticwb.bsc.tracing.DeterminantValue
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.DeterminantValue> listDeterminantValueByUserGroup(org.semanticwb.model.UserGroup value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.DeterminantValue> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasUserGroup,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.tracing.DeterminantValue with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.bsc.tracing.DeterminantValue
       * @return Iterator with all the org.semanticwb.bsc.tracing.DeterminantValue
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.DeterminantValue> listDeterminantValueByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.DeterminantValue> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.tracing.DeterminantValue with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.bsc.tracing.DeterminantValue
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.DeterminantValue> listDeterminantValueByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.DeterminantValue> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.tracing.DeterminantValue with a determined Determinant
       * @param value Determinant of the type org.semanticwb.bsc.tracing.Determinant
       * @param model Model of the org.semanticwb.bsc.tracing.DeterminantValue
       * @return Iterator with all the org.semanticwb.bsc.tracing.DeterminantValue
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.DeterminantValue> listDeterminantValueByDeterminant(org.semanticwb.bsc.tracing.Determinant value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.DeterminantValue> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(bsc_determinant, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.tracing.DeterminantValue with a determined Determinant
       * @param value Determinant of the type org.semanticwb.bsc.tracing.Determinant
       * @return Iterator with all the org.semanticwb.bsc.tracing.DeterminantValue
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.DeterminantValue> listDeterminantValueByDeterminant(org.semanticwb.bsc.tracing.Determinant value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.DeterminantValue> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(bsc_determinant,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.tracing.DeterminantValue with a determined Role
       * @param value Role of the type org.semanticwb.model.Role
       * @param model Model of the org.semanticwb.bsc.tracing.DeterminantValue
       * @return Iterator with all the org.semanticwb.bsc.tracing.DeterminantValue
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.DeterminantValue> listDeterminantValueByRole(org.semanticwb.model.Role value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.DeterminantValue> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasRole, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.tracing.DeterminantValue with a determined Role
       * @param value Role of the type org.semanticwb.model.Role
       * @return Iterator with all the org.semanticwb.bsc.tracing.DeterminantValue
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.DeterminantValue> listDeterminantValueByRole(org.semanticwb.model.Role value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.DeterminantValue> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasRole,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static DeterminantValueBase.ClassMgr getDeterminantValueClassMgr()
    {
        return new DeterminantValueBase.ClassMgr();
    }

   /**
   * Constructs a DeterminantValueBase with a SemanticObject
   * @param base The SemanticObject with the properties for the DeterminantValue
   */
    public DeterminantValueBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
   /**
   * Sets the value for the property Determinant
   * @param value Determinant to set
   */

    public void setDeterminant(org.semanticwb.bsc.tracing.Determinant value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(bsc_determinant, value.getSemanticObject());
        }else
        {
            removeDeterminant();
        }
    }
   /**
   * Remove the value for Determinant property
   */

    public void removeDeterminant()
    {
        getSemanticObject().removeProperty(bsc_determinant);
    }

   /**
   * Gets the Determinant
   * @return a org.semanticwb.bsc.tracing.Determinant
   */
    public org.semanticwb.bsc.tracing.Determinant getDeterminant()
    {
         org.semanticwb.bsc.tracing.Determinant ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(bsc_determinant);
         if(obj!=null)
         {
             ret=(org.semanticwb.bsc.tracing.Determinant)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the IsDeterminant property
* @return String with the IsDeterminant
*/
    public String getIsDeterminant()
    {
        return getSemanticObject().getProperty(bsc_isDeterminant);
    }

/**
* Sets the IsDeterminant property
* @param value long with the IsDeterminant
*/
    public void setIsDeterminant(String value)
    {
        getSemanticObject().setProperty(bsc_isDeterminant, value);
    }
}
