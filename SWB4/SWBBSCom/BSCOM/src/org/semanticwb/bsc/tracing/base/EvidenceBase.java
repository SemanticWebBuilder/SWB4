package org.semanticwb.bsc.tracing.base;


   /**
   * Clase que permite definir los atributos de las evidencias de un Indicador 
   */
public abstract class EvidenceBase extends org.semanticwb.bsc.tracing.BSCTracing implements org.semanticwb.model.UserGroupable,org.semanticwb.model.Activeable,org.semanticwb.model.Filterable,org.semanticwb.model.FilterableNode,org.semanticwb.model.Roleable,org.semanticwb.bsc.Help,org.semanticwb.model.Traceable,org.semanticwb.model.Descriptiveable
{
   /**
   * Persiste el documento, memorandum o adjunto asociado a un Indicador
   */
    public static final org.semanticwb.platform.SemanticProperty bsc_attached=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#attached");
   /**
   * Clase que permite definir los atributos de las evidencias de un Indicador
   */
    public static final org.semanticwb.platform.SemanticClass bsc_Evidence=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#Evidence");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#Evidence");

    public static class ClassMgr
    {
       /**
       * Returns a list of Evidence for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.bsc.tracing.Evidence
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.Evidence> listEvidences(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.Evidence>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.bsc.tracing.Evidence for all models
       * @return Iterator of org.semanticwb.bsc.tracing.Evidence
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.Evidence> listEvidences()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.Evidence>(it, true);
        }

        public static org.semanticwb.bsc.tracing.Evidence createEvidence(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.bsc.tracing.Evidence.ClassMgr.createEvidence(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.bsc.tracing.Evidence
       * @param id Identifier for org.semanticwb.bsc.tracing.Evidence
       * @param model Model of the org.semanticwb.bsc.tracing.Evidence
       * @return A org.semanticwb.bsc.tracing.Evidence
       */
        public static org.semanticwb.bsc.tracing.Evidence getEvidence(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.tracing.Evidence)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.bsc.tracing.Evidence
       * @param id Identifier for org.semanticwb.bsc.tracing.Evidence
       * @param model Model of the org.semanticwb.bsc.tracing.Evidence
       * @return A org.semanticwb.bsc.tracing.Evidence
       */
        public static org.semanticwb.bsc.tracing.Evidence createEvidence(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.tracing.Evidence)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.bsc.tracing.Evidence
       * @param id Identifier for org.semanticwb.bsc.tracing.Evidence
       * @param model Model of the org.semanticwb.bsc.tracing.Evidence
       */
        public static void removeEvidence(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.bsc.tracing.Evidence
       * @param id Identifier for org.semanticwb.bsc.tracing.Evidence
       * @param model Model of the org.semanticwb.bsc.tracing.Evidence
       * @return true if the org.semanticwb.bsc.tracing.Evidence exists, false otherwise
       */

        public static boolean hasEvidence(String id, org.semanticwb.model.SWBModel model)
        {
            return (getEvidence(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.bsc.tracing.Evidence with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.bsc.tracing.Evidence
       * @return Iterator with all the org.semanticwb.bsc.tracing.Evidence
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.Evidence> listEvidenceByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.Evidence> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.tracing.Evidence with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.bsc.tracing.Evidence
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.Evidence> listEvidenceByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.Evidence> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.tracing.Evidence with a determined UserGroup
       * @param value UserGroup of the type org.semanticwb.model.UserGroup
       * @param model Model of the org.semanticwb.bsc.tracing.Evidence
       * @return Iterator with all the org.semanticwb.bsc.tracing.Evidence
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.Evidence> listEvidenceByUserGroup(org.semanticwb.model.UserGroup value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.Evidence> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasUserGroup, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.tracing.Evidence with a determined UserGroup
       * @param value UserGroup of the type org.semanticwb.model.UserGroup
       * @return Iterator with all the org.semanticwb.bsc.tracing.Evidence
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.Evidence> listEvidenceByUserGroup(org.semanticwb.model.UserGroup value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.Evidence> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasUserGroup,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.tracing.Evidence with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.bsc.tracing.Evidence
       * @return Iterator with all the org.semanticwb.bsc.tracing.Evidence
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.Evidence> listEvidenceByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.Evidence> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.tracing.Evidence with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.bsc.tracing.Evidence
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.Evidence> listEvidenceByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.Evidence> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.tracing.Evidence with a determined Role
       * @param value Role of the type org.semanticwb.model.Role
       * @param model Model of the org.semanticwb.bsc.tracing.Evidence
       * @return Iterator with all the org.semanticwb.bsc.tracing.Evidence
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.Evidence> listEvidenceByRole(org.semanticwb.model.Role value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.Evidence> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasRole, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.tracing.Evidence with a determined Role
       * @param value Role of the type org.semanticwb.model.Role
       * @return Iterator with all the org.semanticwb.bsc.tracing.Evidence
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.Evidence> listEvidenceByRole(org.semanticwb.model.Role value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.Evidence> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasRole,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static EvidenceBase.ClassMgr getEvidenceClassMgr()
    {
        return new EvidenceBase.ClassMgr();
    }

   /**
   * Constructs a EvidenceBase with a SemanticObject
   * @param base The SemanticObject with the properties for the Evidence
   */
    public EvidenceBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

/**
* Gets the Attached property
* @return String with the Attached
*/
    public String getAttached()
    {
        return getSemanticObject().getProperty(bsc_attached);
    }

/**
* Sets the Attached property
* @param value long with the Attached
*/
    public void setAttached(String value)
    {
        getSemanticObject().setProperty(bsc_attached, value);
    }
}
