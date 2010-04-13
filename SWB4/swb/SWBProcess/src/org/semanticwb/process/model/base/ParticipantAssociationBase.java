package org.semanticwb.process.model.base;


public abstract class ParticipantAssociationBase extends org.semanticwb.process.model.BPMNBaseElement implements org.semanticwb.process.model.Documentable
{
    public static final org.semanticwb.platform.SemanticClass swp_Participant=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#Participant");
    public static final org.semanticwb.platform.SemanticProperty swp_innerParticipantRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#innerParticipantRef");
    public static final org.semanticwb.platform.SemanticProperty swp_outerParticipantRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#outerParticipantRef");
    public static final org.semanticwb.platform.SemanticClass swp_ParticipantAssociation=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#ParticipantAssociation");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#ParticipantAssociation");

    public static class ClassMgr
    {

        public static java.util.Iterator<org.semanticwb.process.model.ParticipantAssociation> listParticipantAssociations(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ParticipantAssociation>(it, true);
        }

        public static java.util.Iterator<org.semanticwb.process.model.ParticipantAssociation> listParticipantAssociations()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ParticipantAssociation>(it, true);
        }

        public static org.semanticwb.process.model.ParticipantAssociation createParticipantAssociation(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.ParticipantAssociation.ClassMgr.createParticipantAssociation(String.valueOf(id), model);
        }

        public static org.semanticwb.process.model.ParticipantAssociation getParticipantAssociation(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.ParticipantAssociation)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static org.semanticwb.process.model.ParticipantAssociation createParticipantAssociation(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.ParticipantAssociation)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        }

        public static void removeParticipantAssociation(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        public static boolean hasParticipantAssociation(String id, org.semanticwb.model.SWBModel model)
        {
            return (getParticipantAssociation(id, model)!=null);
        }

        public static java.util.Iterator<org.semanticwb.process.model.ParticipantAssociation> listParticipantAssociationByDocumentation(org.semanticwb.process.model.Documentation value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ParticipantAssociation> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ParticipantAssociation> listParticipantAssociationByDocumentation(org.semanticwb.process.model.Documentation value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ParticipantAssociation> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ParticipantAssociation> listParticipantAssociationByInnerParticipantRef(org.semanticwb.process.model.Participant value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ParticipantAssociation> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_innerParticipantRef, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ParticipantAssociation> listParticipantAssociationByInnerParticipantRef(org.semanticwb.process.model.Participant value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ParticipantAssociation> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_innerParticipantRef,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ParticipantAssociation> listParticipantAssociationByOuterParticipantRef(org.semanticwb.process.model.Participant value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ParticipantAssociation> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_outerParticipantRef, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ParticipantAssociation> listParticipantAssociationByOuterParticipantRef(org.semanticwb.process.model.Participant value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ParticipantAssociation> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_outerParticipantRef,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ParticipantAssociation> listParticipantAssociationByExtensionDefinition(org.semanticwb.process.model.ExtensionDefinition value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ParticipantAssociation> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionDefinition, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ParticipantAssociation> listParticipantAssociationByExtensionDefinition(org.semanticwb.process.model.ExtensionDefinition value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ParticipantAssociation> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionDefinition,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ParticipantAssociation> listParticipantAssociationByExtensionValue(org.semanticwb.process.model.ExtensionAttributeValue value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ParticipantAssociation> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionValue, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ParticipantAssociation> listParticipantAssociationByExtensionValue(org.semanticwb.process.model.ExtensionAttributeValue value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ParticipantAssociation> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionValue,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public ParticipantAssociationBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public void setInnerParticipantRef(org.semanticwb.process.model.Participant value)
    {
        getSemanticObject().setObjectProperty(swp_innerParticipantRef, value.getSemanticObject());
    }

    public void removeInnerParticipantRef()
    {
        getSemanticObject().removeProperty(swp_innerParticipantRef);
    }

    public org.semanticwb.process.model.Participant getInnerParticipantRef()
    {
         org.semanticwb.process.model.Participant ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_innerParticipantRef);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.Participant)obj.createGenericInstance();
         }
         return ret;
    }

    public void setOuterParticipantRef(org.semanticwb.process.model.Participant value)
    {
        getSemanticObject().setObjectProperty(swp_outerParticipantRef, value.getSemanticObject());
    }

    public void removeOuterParticipantRef()
    {
        getSemanticObject().removeProperty(swp_outerParticipantRef);
    }

    public org.semanticwb.process.model.Participant getOuterParticipantRef()
    {
         org.semanticwb.process.model.Participant ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_outerParticipantRef);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.Participant)obj.createGenericInstance();
         }
         return ret;
    }
}
