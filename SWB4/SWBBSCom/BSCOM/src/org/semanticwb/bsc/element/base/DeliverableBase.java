package org.semanticwb.bsc.element.base;


   /**
   * Representa un archivo físico utilizado a manera de evidencia sobre la realización de alguna actividad. 
   */
public abstract class DeliverableBase extends org.semanticwb.bsc.element.BSCElement implements org.semanticwb.model.Referensable,org.semanticwb.model.UserGroupable,org.semanticwb.model.FilterableClass,org.semanticwb.model.Roleable,org.semanticwb.model.Activeable,org.semanticwb.model.FilterableNode,org.semanticwb.model.RuleRefable,org.semanticwb.bsc.Attachmentable,org.semanticwb.model.Descriptiveable,org.semanticwb.bsc.Schedule,org.semanticwb.bsc.Updateable,org.semanticwb.model.Filterable,org.semanticwb.bsc.Help,org.semanticwb.model.Traceable
{
   /**
   * Porcentaje de avance a reportar
   */
    public static final org.semanticwb.platform.SemanticProperty bsc_progress=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#progress");
   /**
   * Ruta del archivo físico asociado
   */
    public static final org.semanticwb.platform.SemanticProperty bsc_filePath=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#filePath");
    public static final org.semanticwb.platform.SemanticClass bsc_Initiative=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#Initiative");
    public static final org.semanticwb.platform.SemanticProperty bsc_initiativeInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#initiativeInv");
    public static final org.semanticwb.platform.SemanticClass bsc_State=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#State");
   /**
   * Estatus asignado por el usuario
   */
    public static final org.semanticwb.platform.SemanticProperty bsc_statusAssigned=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#statusAssigned");
   /**
   * Estatus asignado automáticamente por el sistema
   */
    public static final org.semanticwb.platform.SemanticProperty bsc_autoStatus=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#autoStatus");
   /**
   * Un usuario es una persona que tiene relación con el portal a través de un método de acceso.
   */
    public static final org.semanticwb.platform.SemanticClass swb_User=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#User");
   /**
   * El usuario responsable del seguimiento al entregable
   */
    public static final org.semanticwb.platform.SemanticProperty bsc_responsible=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#responsible");
   /**
   * Representa un archivo físico utilizado a manera de evidencia sobre la realización de alguna actividad.
   */
    public static final org.semanticwb.platform.SemanticClass bsc_Deliverable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#Deliverable");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#Deliverable");

    public static class ClassMgr
    {
       /**
       * Returns a list of Deliverable for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.bsc.element.Deliverable
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Deliverable> listDeliverables(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Deliverable>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.bsc.element.Deliverable for all models
       * @return Iterator of org.semanticwb.bsc.element.Deliverable
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Deliverable> listDeliverables()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Deliverable>(it, true);
        }

        public static org.semanticwb.bsc.element.Deliverable createDeliverable(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.bsc.element.Deliverable.ClassMgr.createDeliverable(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.bsc.element.Deliverable
       * @param id Identifier for org.semanticwb.bsc.element.Deliverable
       * @param model Model of the org.semanticwb.bsc.element.Deliverable
       * @return A org.semanticwb.bsc.element.Deliverable
       */
        public static org.semanticwb.bsc.element.Deliverable getDeliverable(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.element.Deliverable)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.bsc.element.Deliverable
       * @param id Identifier for org.semanticwb.bsc.element.Deliverable
       * @param model Model of the org.semanticwb.bsc.element.Deliverable
       * @return A org.semanticwb.bsc.element.Deliverable
       */
        public static org.semanticwb.bsc.element.Deliverable createDeliverable(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.element.Deliverable)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.bsc.element.Deliverable
       * @param id Identifier for org.semanticwb.bsc.element.Deliverable
       * @param model Model of the org.semanticwb.bsc.element.Deliverable
       */
        public static void removeDeliverable(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.bsc.element.Deliverable
       * @param id Identifier for org.semanticwb.bsc.element.Deliverable
       * @param model Model of the org.semanticwb.bsc.element.Deliverable
       * @return true if the org.semanticwb.bsc.element.Deliverable exists, false otherwise
       */

        public static boolean hasDeliverable(String id, org.semanticwb.model.SWBModel model)
        {
            return (getDeliverable(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.bsc.element.Deliverable with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.bsc.element.Deliverable
       * @return Iterator with all the org.semanticwb.bsc.element.Deliverable
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Deliverable> listDeliverableByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Deliverable> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Deliverable with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.bsc.element.Deliverable
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Deliverable> listDeliverableByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Deliverable> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Deliverable with a determined UserGroup
       * @param value UserGroup of the type org.semanticwb.model.UserGroup
       * @param model Model of the org.semanticwb.bsc.element.Deliverable
       * @return Iterator with all the org.semanticwb.bsc.element.Deliverable
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Deliverable> listDeliverableByUserGroup(org.semanticwb.model.UserGroup value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Deliverable> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasUserGroup, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Deliverable with a determined UserGroup
       * @param value UserGroup of the type org.semanticwb.model.UserGroup
       * @return Iterator with all the org.semanticwb.bsc.element.Deliverable
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Deliverable> listDeliverableByUserGroup(org.semanticwb.model.UserGroup value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Deliverable> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasUserGroup,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Deliverable with a determined Attachments
       * @param value Attachments of the type org.semanticwb.bsc.catalogs.Attachment
       * @param model Model of the org.semanticwb.bsc.element.Deliverable
       * @return Iterator with all the org.semanticwb.bsc.element.Deliverable
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Deliverable> listDeliverableByAttachments(org.semanticwb.bsc.catalogs.Attachment value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Deliverable> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(bsc_hasAttachments, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Deliverable with a determined Attachments
       * @param value Attachments of the type org.semanticwb.bsc.catalogs.Attachment
       * @return Iterator with all the org.semanticwb.bsc.element.Deliverable
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Deliverable> listDeliverableByAttachments(org.semanticwb.bsc.catalogs.Attachment value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Deliverable> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(bsc_hasAttachments,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Deliverable with a determined Initiative
       * @param value Initiative of the type org.semanticwb.bsc.element.Initiative
       * @param model Model of the org.semanticwb.bsc.element.Deliverable
       * @return Iterator with all the org.semanticwb.bsc.element.Deliverable
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Deliverable> listDeliverableByInitiative(org.semanticwb.bsc.element.Initiative value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Deliverable> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(bsc_initiativeInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Deliverable with a determined Initiative
       * @param value Initiative of the type org.semanticwb.bsc.element.Initiative
       * @return Iterator with all the org.semanticwb.bsc.element.Deliverable
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Deliverable> listDeliverableByInitiative(org.semanticwb.bsc.element.Initiative value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Deliverable> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(bsc_initiativeInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Deliverable with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.bsc.element.Deliverable
       * @return Iterator with all the org.semanticwb.bsc.element.Deliverable
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Deliverable> listDeliverableByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Deliverable> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Deliverable with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.bsc.element.Deliverable
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Deliverable> listDeliverableByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Deliverable> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Deliverable with a determined Role
       * @param value Role of the type org.semanticwb.model.Role
       * @param model Model of the org.semanticwb.bsc.element.Deliverable
       * @return Iterator with all the org.semanticwb.bsc.element.Deliverable
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Deliverable> listDeliverableByRole(org.semanticwb.model.Role value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Deliverable> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasRole, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Deliverable with a determined Role
       * @param value Role of the type org.semanticwb.model.Role
       * @return Iterator with all the org.semanticwb.bsc.element.Deliverable
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Deliverable> listDeliverableByRole(org.semanticwb.model.Role value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Deliverable> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasRole,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Deliverable with a determined RuleRef
       * @param value RuleRef of the type org.semanticwb.model.RuleRef
       * @param model Model of the org.semanticwb.bsc.element.Deliverable
       * @return Iterator with all the org.semanticwb.bsc.element.Deliverable
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Deliverable> listDeliverableByRuleRef(org.semanticwb.model.RuleRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Deliverable> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasRuleRef, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Deliverable with a determined RuleRef
       * @param value RuleRef of the type org.semanticwb.model.RuleRef
       * @return Iterator with all the org.semanticwb.bsc.element.Deliverable
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Deliverable> listDeliverableByRuleRef(org.semanticwb.model.RuleRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Deliverable> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasRuleRef,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Deliverable with a determined StatusAssigned
       * @param value StatusAssigned of the type org.semanticwb.bsc.accessory.State
       * @param model Model of the org.semanticwb.bsc.element.Deliverable
       * @return Iterator with all the org.semanticwb.bsc.element.Deliverable
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Deliverable> listDeliverableByStatusAssigned(org.semanticwb.bsc.accessory.State value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Deliverable> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(bsc_statusAssigned, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Deliverable with a determined StatusAssigned
       * @param value StatusAssigned of the type org.semanticwb.bsc.accessory.State
       * @return Iterator with all the org.semanticwb.bsc.element.Deliverable
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Deliverable> listDeliverableByStatusAssigned(org.semanticwb.bsc.accessory.State value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Deliverable> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(bsc_statusAssigned,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Deliverable with a determined AutoStatus
       * @param value AutoStatus of the type org.semanticwb.bsc.accessory.State
       * @param model Model of the org.semanticwb.bsc.element.Deliverable
       * @return Iterator with all the org.semanticwb.bsc.element.Deliverable
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Deliverable> listDeliverableByAutoStatus(org.semanticwb.bsc.accessory.State value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Deliverable> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(bsc_autoStatus, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Deliverable with a determined AutoStatus
       * @param value AutoStatus of the type org.semanticwb.bsc.accessory.State
       * @return Iterator with all the org.semanticwb.bsc.element.Deliverable
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Deliverable> listDeliverableByAutoStatus(org.semanticwb.bsc.accessory.State value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Deliverable> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(bsc_autoStatus,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Deliverable with a determined Responsible
       * @param value Responsible of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.bsc.element.Deliverable
       * @return Iterator with all the org.semanticwb.bsc.element.Deliverable
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Deliverable> listDeliverableByResponsible(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Deliverable> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(bsc_responsible, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Deliverable with a determined Responsible
       * @param value Responsible of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.bsc.element.Deliverable
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Deliverable> listDeliverableByResponsible(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Deliverable> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(bsc_responsible,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static DeliverableBase.ClassMgr getDeliverableClassMgr()
    {
        return new DeliverableBase.ClassMgr();
    }

   /**
   * Constructs a DeliverableBase with a SemanticObject
   * @param base The SemanticObject with the properties for the Deliverable
   */
    public DeliverableBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

/**
* Gets the Progress property
* @return int with the Progress
*/
    public int getProgress()
    {
        return getSemanticObject().getIntProperty(bsc_progress);
    }

/**
* Sets the Progress property
* @param value long with the Progress
*/
    public void setProgress(int value)
    {
        getSemanticObject().setIntProperty(bsc_progress, value);
    }

/**
* Gets the PlannedEnd property
* @return java.util.Date with the PlannedEnd
*/
    public java.util.Date getPlannedEnd()
    {
        return getSemanticObject().getDateProperty(bsc_plannedEnd);
    }

/**
* Sets the PlannedEnd property
* @param value long with the PlannedEnd
*/
    public void setPlannedEnd(java.util.Date value)
    {
        getSemanticObject().setDateProperty(bsc_plannedEnd, value);
    }

/**
* Gets the FilePath property
* @return String with the FilePath
*/
    public String getFilePath()
    {
        return getSemanticObject().getProperty(bsc_filePath);
    }

/**
* Sets the FilePath property
* @param value long with the FilePath
*/
    public void setFilePath(String value)
    {
        getSemanticObject().setProperty(bsc_filePath, value);
    }
   /**
   * Gets all the org.semanticwb.bsc.catalogs.Attachment
   * @return A GenericIterator with all the org.semanticwb.bsc.catalogs.Attachment
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.bsc.catalogs.Attachment> listAttachmentses()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.catalogs.Attachment>(getSemanticObject().listObjectProperties(bsc_hasAttachments));
    }

   /**
   * Gets true if has a Attachments
   * @param value org.semanticwb.bsc.catalogs.Attachment to verify
   * @return true if the org.semanticwb.bsc.catalogs.Attachment exists, false otherwise
   */
    public boolean hasAttachments(org.semanticwb.bsc.catalogs.Attachment value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(bsc_hasAttachments,value.getSemanticObject());
        }
        return ret;
    }
   /**
   * Adds a Attachments
   * @param value org.semanticwb.bsc.catalogs.Attachment to add
   */

    public void addAttachments(org.semanticwb.bsc.catalogs.Attachment value)
    {
        getSemanticObject().addObjectProperty(bsc_hasAttachments, value.getSemanticObject());
    }
   /**
   * Removes all the Attachments
   */

    public void removeAllAttachments()
    {
        getSemanticObject().removeProperty(bsc_hasAttachments);
    }
   /**
   * Removes a Attachments
   * @param value org.semanticwb.bsc.catalogs.Attachment to remove
   */

    public void removeAttachments(org.semanticwb.bsc.catalogs.Attachment value)
    {
        getSemanticObject().removeObjectProperty(bsc_hasAttachments,value.getSemanticObject());
    }

   /**
   * Gets the Attachments
   * @return a org.semanticwb.bsc.catalogs.Attachment
   */
    public org.semanticwb.bsc.catalogs.Attachment getAttachments()
    {
         org.semanticwb.bsc.catalogs.Attachment ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(bsc_hasAttachments);
         if(obj!=null)
         {
             ret=(org.semanticwb.bsc.catalogs.Attachment)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the Recommendations property
* @return String with the Recommendations
*/
    public String getRecommendations()
    {
        return getSemanticObject().getProperty(bsc_recommendations);
    }

/**
* Sets the Recommendations property
* @param value long with the Recommendations
*/
    public void setRecommendations(String value)
    {
        getSemanticObject().setProperty(bsc_recommendations, value);
    }
   /**
   * Sets the value for the property Initiative
   * @param value Initiative to set
   */

    public void setInitiative(org.semanticwb.bsc.element.Initiative value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(bsc_initiativeInv, value.getSemanticObject());
        }else
        {
            removeInitiative();
        }
    }
   /**
   * Remove the value for Initiative property
   */

    public void removeInitiative()
    {
        getSemanticObject().removeProperty(bsc_initiativeInv);
    }

   /**
   * Gets the Initiative
   * @return a org.semanticwb.bsc.element.Initiative
   */
    public org.semanticwb.bsc.element.Initiative getInitiative()
    {
         org.semanticwb.bsc.element.Initiative ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(bsc_initiativeInv);
         if(obj!=null)
         {
             ret=(org.semanticwb.bsc.element.Initiative)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the PlannedStart property
* @return java.util.Date with the PlannedStart
*/
    public java.util.Date getPlannedStart()
    {
        return getSemanticObject().getDateProperty(bsc_plannedStart);
    }

/**
* Sets the PlannedStart property
* @param value long with the PlannedStart
*/
    public void setPlannedStart(java.util.Date value)
    {
        getSemanticObject().setDateProperty(bsc_plannedStart, value);
    }

/**
* Gets the ActualEnd property
* @return java.util.Date with the ActualEnd
*/
    public java.util.Date getActualEnd()
    {
        return getSemanticObject().getDateProperty(bsc_actualEnd);
    }

/**
* Sets the ActualEnd property
* @param value long with the ActualEnd
*/
    public void setActualEnd(java.util.Date value)
    {
        getSemanticObject().setDateProperty(bsc_actualEnd, value);
    }

/**
* Gets the Analysis property
* @return String with the Analysis
*/
    public String getAnalysis()
    {
        return getSemanticObject().getProperty(bsc_analysis);
    }

/**
* Sets the Analysis property
* @param value long with the Analysis
*/
    public void setAnalysis(String value)
    {
        getSemanticObject().setProperty(bsc_analysis, value);
    }
   /**
   * Sets the value for the property StatusAssigned
   * @param value StatusAssigned to set
   */

    public void setStatusAssigned(org.semanticwb.bsc.accessory.State value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(bsc_statusAssigned, value.getSemanticObject());
        }else
        {
            removeStatusAssigned();
        }
    }
   /**
   * Remove the value for StatusAssigned property
   */

    public void removeStatusAssigned()
    {
        getSemanticObject().removeProperty(bsc_statusAssigned);
    }

   /**
   * Gets the StatusAssigned
   * @return a org.semanticwb.bsc.accessory.State
   */
    public org.semanticwb.bsc.accessory.State getStatusAssigned()
    {
         org.semanticwb.bsc.accessory.State ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(bsc_statusAssigned);
         if(obj!=null)
         {
             ret=(org.semanticwb.bsc.accessory.State)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the ActualStart property
* @return java.util.Date with the ActualStart
*/
    public java.util.Date getActualStart()
    {
        return getSemanticObject().getDateProperty(bsc_actualStart);
    }

/**
* Sets the ActualStart property
* @param value long with the ActualStart
*/
    public void setActualStart(java.util.Date value)
    {
        getSemanticObject().setDateProperty(bsc_actualStart, value);
    }
   /**
   * Sets the value for the property AutoStatus
   * @param value AutoStatus to set
   */

    public void setAutoStatus(org.semanticwb.bsc.accessory.State value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(bsc_autoStatus, value.getSemanticObject());
        }else
        {
            removeAutoStatus();
        }
    }
   /**
   * Remove the value for AutoStatus property
   */

    public void removeAutoStatus()
    {
        getSemanticObject().removeProperty(bsc_autoStatus);
    }

   /**
   * Gets the AutoStatus
   * @return a org.semanticwb.bsc.accessory.State
   */
    public org.semanticwb.bsc.accessory.State getAutoStatus()
    {
         org.semanticwb.bsc.accessory.State ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(bsc_autoStatus);
         if(obj!=null)
         {
             ret=(org.semanticwb.bsc.accessory.State)obj.createGenericInstance();
         }
         return ret;
    }
   /**
   * Sets the value for the property Responsible
   * @param value Responsible to set
   */

    public void setResponsible(org.semanticwb.model.User value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(bsc_responsible, value.getSemanticObject());
        }else
        {
            removeResponsible();
        }
    }
   /**
   * Remove the value for Responsible property
   */

    public void removeResponsible()
    {
        getSemanticObject().removeProperty(bsc_responsible);
    }

   /**
   * Gets the Responsible
   * @return a org.semanticwb.model.User
   */
    public org.semanticwb.model.User getResponsible()
    {
         org.semanticwb.model.User ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(bsc_responsible);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.User)obj.createGenericInstance();
         }
         return ret;
    }
}
