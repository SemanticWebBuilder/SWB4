package org.semanticwb.process.model.base;


public abstract class MessageFlowAssociationBase extends org.semanticwb.process.model.BPMNBaseElement implements org.semanticwb.process.model.Documentable
{
    public static final org.semanticwb.platform.SemanticClass swp_MessageFlow=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#MessageFlow");
    public static final org.semanticwb.platform.SemanticProperty swp_outerMessageFlowRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#outerMessageFlowRef");
    public static final org.semanticwb.platform.SemanticProperty swp_innerMessageFlowRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#innerMessageFlowRef");
    public static final org.semanticwb.platform.SemanticClass swp_MessageFlowAssociation=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#MessageFlowAssociation");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#MessageFlowAssociation");

    public static class ClassMgr
    {

        public static java.util.Iterator<org.semanticwb.process.model.MessageFlowAssociation> listMessageFlowAssociations(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MessageFlowAssociation>(it, true);
        }

        public static java.util.Iterator<org.semanticwb.process.model.MessageFlowAssociation> listMessageFlowAssociations()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MessageFlowAssociation>(it, true);
        }

        public static org.semanticwb.process.model.MessageFlowAssociation createMessageFlowAssociation(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.MessageFlowAssociation.ClassMgr.createMessageFlowAssociation(String.valueOf(id), model);
        }

        public static org.semanticwb.process.model.MessageFlowAssociation getMessageFlowAssociation(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.MessageFlowAssociation)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static org.semanticwb.process.model.MessageFlowAssociation createMessageFlowAssociation(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.MessageFlowAssociation)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        }

        public static void removeMessageFlowAssociation(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        public static boolean hasMessageFlowAssociation(String id, org.semanticwb.model.SWBModel model)
        {
            return (getMessageFlowAssociation(id, model)!=null);
        }

        public static java.util.Iterator<org.semanticwb.process.model.MessageFlowAssociation> listMessageFlowAssociationByDocumentation(org.semanticwb.process.model.Documentation value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MessageFlowAssociation> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.MessageFlowAssociation> listMessageFlowAssociationByDocumentation(org.semanticwb.process.model.Documentation value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MessageFlowAssociation> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.MessageFlowAssociation> listMessageFlowAssociationByOuterMessageFlowRef(org.semanticwb.process.model.MessageFlow value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MessageFlowAssociation> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_outerMessageFlowRef, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.MessageFlowAssociation> listMessageFlowAssociationByOuterMessageFlowRef(org.semanticwb.process.model.MessageFlow value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MessageFlowAssociation> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_outerMessageFlowRef,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.MessageFlowAssociation> listMessageFlowAssociationByExtensionDefinition(org.semanticwb.process.model.ExtensionDefinition value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MessageFlowAssociation> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionDefinition, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.MessageFlowAssociation> listMessageFlowAssociationByExtensionDefinition(org.semanticwb.process.model.ExtensionDefinition value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MessageFlowAssociation> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionDefinition,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.MessageFlowAssociation> listMessageFlowAssociationByInnerMessageFlowRef(org.semanticwb.process.model.MessageFlow value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MessageFlowAssociation> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_innerMessageFlowRef, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.MessageFlowAssociation> listMessageFlowAssociationByInnerMessageFlowRef(org.semanticwb.process.model.MessageFlow value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MessageFlowAssociation> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_innerMessageFlowRef,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.MessageFlowAssociation> listMessageFlowAssociationByExtensionValue(org.semanticwb.process.model.ExtensionAttributeValue value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MessageFlowAssociation> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionValue, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.MessageFlowAssociation> listMessageFlowAssociationByExtensionValue(org.semanticwb.process.model.ExtensionAttributeValue value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MessageFlowAssociation> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionValue,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public MessageFlowAssociationBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public void setOuterMessageFlowRef(org.semanticwb.process.model.MessageFlow value)
    {
        getSemanticObject().setObjectProperty(swp_outerMessageFlowRef, value.getSemanticObject());
    }

    public void removeOuterMessageFlowRef()
    {
        getSemanticObject().removeProperty(swp_outerMessageFlowRef);
    }

    public org.semanticwb.process.model.MessageFlow getOuterMessageFlowRef()
    {
         org.semanticwb.process.model.MessageFlow ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_outerMessageFlowRef);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.MessageFlow)obj.createGenericInstance();
         }
         return ret;
    }

    public void setInnerMessageFlowRef(org.semanticwb.process.model.MessageFlow value)
    {
        getSemanticObject().setObjectProperty(swp_innerMessageFlowRef, value.getSemanticObject());
    }

    public void removeInnerMessageFlowRef()
    {
        getSemanticObject().removeProperty(swp_innerMessageFlowRef);
    }

    public org.semanticwb.process.model.MessageFlow getInnerMessageFlowRef()
    {
         org.semanticwb.process.model.MessageFlow ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_innerMessageFlowRef);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.MessageFlow)obj.createGenericInstance();
         }
         return ret;
    }
}
