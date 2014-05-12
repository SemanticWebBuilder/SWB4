package org.semanticwb.bsc.accessory.base;


   /**
   * Determinante que define un criterio para evaluar si un control es suficiente o insuficiente para un Riesgo. Un riesgo puede tener varios determinantes. 
   */
public abstract class DeterminantBase extends org.semanticwb.bsc.accessory.BSCAccessory implements org.semanticwb.model.UserGroupable,org.semanticwb.model.Filterable,org.semanticwb.bsc.Help,org.semanticwb.model.Undeleteable,org.semanticwb.model.Roleable,org.semanticwb.model.FilterableNode,org.semanticwb.model.Activeable,org.semanticwb.model.Traceable,org.semanticwb.model.Descriptiveable
{
   /**
   * Determinante que define un criterio para evaluar si un control es suficiente o insuficiente para un Riesgo. Un riesgo puede tener varios determinantes.
   */
    public static final org.semanticwb.platform.SemanticClass bsc_Determinant=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#Determinant");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#Determinant");

    public static class ClassMgr
    {
       /**
       * Returns a list of Determinant for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.bsc.accessory.Determinant
       */

        public static java.util.Iterator<org.semanticwb.bsc.accessory.Determinant> listDeterminants(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.accessory.Determinant>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.bsc.accessory.Determinant for all models
       * @return Iterator of org.semanticwb.bsc.accessory.Determinant
       */

        public static java.util.Iterator<org.semanticwb.bsc.accessory.Determinant> listDeterminants()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.accessory.Determinant>(it, true);
        }

        public static org.semanticwb.bsc.accessory.Determinant createDeterminant(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.bsc.accessory.Determinant.ClassMgr.createDeterminant(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.bsc.accessory.Determinant
       * @param id Identifier for org.semanticwb.bsc.accessory.Determinant
       * @param model Model of the org.semanticwb.bsc.accessory.Determinant
       * @return A org.semanticwb.bsc.accessory.Determinant
       */
        public static org.semanticwb.bsc.accessory.Determinant getDeterminant(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.accessory.Determinant)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.bsc.accessory.Determinant
       * @param id Identifier for org.semanticwb.bsc.accessory.Determinant
       * @param model Model of the org.semanticwb.bsc.accessory.Determinant
       * @return A org.semanticwb.bsc.accessory.Determinant
       */
        public static org.semanticwb.bsc.accessory.Determinant createDeterminant(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.accessory.Determinant)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.bsc.accessory.Determinant
       * @param id Identifier for org.semanticwb.bsc.accessory.Determinant
       * @param model Model of the org.semanticwb.bsc.accessory.Determinant
       */
        public static void removeDeterminant(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.bsc.accessory.Determinant
       * @param id Identifier for org.semanticwb.bsc.accessory.Determinant
       * @param model Model of the org.semanticwb.bsc.accessory.Determinant
       * @return true if the org.semanticwb.bsc.accessory.Determinant exists, false otherwise
       */

        public static boolean hasDeterminant(String id, org.semanticwb.model.SWBModel model)
        {
            return (getDeterminant(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.bsc.accessory.Determinant with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.bsc.accessory.Determinant
       * @return Iterator with all the org.semanticwb.bsc.accessory.Determinant
       */

        public static java.util.Iterator<org.semanticwb.bsc.accessory.Determinant> listDeterminantByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.accessory.Determinant> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.accessory.Determinant with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.bsc.accessory.Determinant
       */

        public static java.util.Iterator<org.semanticwb.bsc.accessory.Determinant> listDeterminantByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.accessory.Determinant> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.accessory.Determinant with a determined UserGroup
       * @param value UserGroup of the type org.semanticwb.model.UserGroup
       * @param model Model of the org.semanticwb.bsc.accessory.Determinant
       * @return Iterator with all the org.semanticwb.bsc.accessory.Determinant
       */

        public static java.util.Iterator<org.semanticwb.bsc.accessory.Determinant> listDeterminantByUserGroup(org.semanticwb.model.UserGroup value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.accessory.Determinant> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasUserGroup, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.accessory.Determinant with a determined UserGroup
       * @param value UserGroup of the type org.semanticwb.model.UserGroup
       * @return Iterator with all the org.semanticwb.bsc.accessory.Determinant
       */

        public static java.util.Iterator<org.semanticwb.bsc.accessory.Determinant> listDeterminantByUserGroup(org.semanticwb.model.UserGroup value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.accessory.Determinant> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasUserGroup,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.accessory.Determinant with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.bsc.accessory.Determinant
       * @return Iterator with all the org.semanticwb.bsc.accessory.Determinant
       */

        public static java.util.Iterator<org.semanticwb.bsc.accessory.Determinant> listDeterminantByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.accessory.Determinant> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.accessory.Determinant with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.bsc.accessory.Determinant
       */

        public static java.util.Iterator<org.semanticwb.bsc.accessory.Determinant> listDeterminantByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.accessory.Determinant> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.accessory.Determinant with a determined Role
       * @param value Role of the type org.semanticwb.model.Role
       * @param model Model of the org.semanticwb.bsc.accessory.Determinant
       * @return Iterator with all the org.semanticwb.bsc.accessory.Determinant
       */

        public static java.util.Iterator<org.semanticwb.bsc.accessory.Determinant> listDeterminantByRole(org.semanticwb.model.Role value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.accessory.Determinant> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasRole, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.accessory.Determinant with a determined Role
       * @param value Role of the type org.semanticwb.model.Role
       * @return Iterator with all the org.semanticwb.bsc.accessory.Determinant
       */

        public static java.util.Iterator<org.semanticwb.bsc.accessory.Determinant> listDeterminantByRole(org.semanticwb.model.Role value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.accessory.Determinant> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasRole,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static DeterminantBase.ClassMgr getDeterminantClassMgr()
    {
        return new DeterminantBase.ClassMgr();
    }

   /**
   * Constructs a DeterminantBase with a SemanticObject
   * @param base The SemanticObject with the properties for the Determinant
   */
    public DeterminantBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
}
