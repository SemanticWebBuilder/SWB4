package org.semanticwb.process.model.base;


public abstract class PartnerRoleBase extends org.semanticwb.process.model.Participant implements org.semanticwb.process.model.Documentable,org.semanticwb.process.model.ProcessReferensable,org.semanticwb.model.Descriptiveable,org.semanticwb.process.model.Messageable
{
    public static final org.semanticwb.platform.SemanticClass swb_Role=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Role");
    public static final org.semanticwb.platform.SemanticProperty swp_role=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#role");
    public static final org.semanticwb.platform.SemanticClass swp_PartnerRole=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#PartnerRole");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#PartnerRole");

    public static class ClassMgr
    {

        public static java.util.Iterator<org.semanticwb.process.model.PartnerRole> listPartnerRoles(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.PartnerRole>(it, true);
        }

        public static java.util.Iterator<org.semanticwb.process.model.PartnerRole> listPartnerRoles()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.PartnerRole>(it, true);
        }

        public static org.semanticwb.process.model.PartnerRole createPartnerRole(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.PartnerRole.ClassMgr.createPartnerRole(String.valueOf(id), model);
        }

        public static org.semanticwb.process.model.PartnerRole getPartnerRole(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.PartnerRole)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static org.semanticwb.process.model.PartnerRole createPartnerRole(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.PartnerRole)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        }

        public static void removePartnerRole(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        public static boolean hasPartnerRole(String id, org.semanticwb.model.SWBModel model)
        {
            return (getPartnerRole(id, model)!=null);
        }

        public static java.util.Iterator<org.semanticwb.process.model.PartnerRole> listPartnerRoleByEndPointRef(org.semanticwb.process.model.EndPoint value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.PartnerRole> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasEndPointRef, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.PartnerRole> listPartnerRoleByEndPointRef(org.semanticwb.process.model.EndPoint value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.PartnerRole> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasEndPointRef,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.PartnerRole> listPartnerRoleByDocumentation(org.semanticwb.process.model.Documentation value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.PartnerRole> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.PartnerRole> listPartnerRoleByDocumentation(org.semanticwb.process.model.Documentation value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.PartnerRole> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.PartnerRole> listPartnerRoleByRole(org.semanticwb.model.Role value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.PartnerRole> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_role, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.PartnerRole> listPartnerRoleByRole(org.semanticwb.model.Role value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.PartnerRole> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_role,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.PartnerRole> listPartnerRoleByProcessRef(org.semanticwb.process.model.Process value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.PartnerRole> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_processRef, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.PartnerRole> listPartnerRoleByProcessRef(org.semanticwb.process.model.Process value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.PartnerRole> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_processRef,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.PartnerRole> listPartnerRoleByMessageRef(org.semanticwb.process.model.Message value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.PartnerRole> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_messageRef, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.PartnerRole> listPartnerRoleByMessageRef(org.semanticwb.process.model.Message value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.PartnerRole> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_messageRef,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.PartnerRole> listPartnerRoleByExtensionDefinition(org.semanticwb.process.model.ExtensionDefinition value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.PartnerRole> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionDefinition, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.PartnerRole> listPartnerRoleByExtensionDefinition(org.semanticwb.process.model.ExtensionDefinition value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.PartnerRole> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionDefinition,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.PartnerRole> listPartnerRoleByParticipantMultiplicity(org.semanticwb.process.model.ParticipantMultiplicity value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.PartnerRole> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_participantMultiplicity, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.PartnerRole> listPartnerRoleByParticipantMultiplicity(org.semanticwb.process.model.ParticipantMultiplicity value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.PartnerRole> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_participantMultiplicity,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.PartnerRole> listPartnerRoleByExtensionValue(org.semanticwb.process.model.ExtensionAttributeValue value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.PartnerRole> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionValue, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.PartnerRole> listPartnerRoleByExtensionValue(org.semanticwb.process.model.ExtensionAttributeValue value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.PartnerRole> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionValue,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.PartnerRole> listPartnerRoleByInterfaceRef(org.semanticwb.process.model.ProcessInterface value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.PartnerRole> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasInterfaceRef, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.PartnerRole> listPartnerRoleByInterfaceRef(org.semanticwb.process.model.ProcessInterface value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.PartnerRole> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasInterfaceRef,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public PartnerRoleBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public void setRole(org.semanticwb.model.Role value)
    {
        getSemanticObject().setObjectProperty(swp_role, value.getSemanticObject());
    }

    public void removeRole()
    {
        getSemanticObject().removeProperty(swp_role);
    }

    public org.semanticwb.model.Role getRole()
    {
         org.semanticwb.model.Role ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_role);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.Role)obj.createGenericInstance();
         }
         return ret;
    }

    public org.semanticwb.process.model.ProcessSite getProcessSite()
    {
        return (org.semanticwb.process.model.ProcessSite)getSemanticObject().getModel().getModelObject().createGenericInstance();
    }
}
