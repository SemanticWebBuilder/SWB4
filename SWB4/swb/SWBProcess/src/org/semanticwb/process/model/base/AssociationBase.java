package org.semanticwb.process.model.base;


public abstract class AssociationBase extends org.semanticwb.process.model.Artifact implements org.semanticwb.process.model.Documentable
{
    public static final org.semanticwb.platform.SemanticClass swp_BPMNBaseElement=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#BPMNBaseElement");
    public static final org.semanticwb.platform.SemanticProperty swp_associationSourceRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#associationSourceRef");
    public static final org.semanticwb.platform.SemanticProperty swp_associationDirection=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#associationDirection");
    public static final org.semanticwb.platform.SemanticProperty swp_associationTargetRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#associationTargetRef");
    public static final org.semanticwb.platform.SemanticClass swp_Association=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#Association");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#Association");

    public static class ClassMgr
    {

        public static java.util.Iterator<org.semanticwb.process.model.Association> listAssociations(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Association>(it, true);
        }

        public static java.util.Iterator<org.semanticwb.process.model.Association> listAssociations()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Association>(it, true);
        }

        public static org.semanticwb.process.model.Association createAssociation(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.Association.ClassMgr.createAssociation(String.valueOf(id), model);
        }

        public static org.semanticwb.process.model.Association getAssociation(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.Association)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static org.semanticwb.process.model.Association createAssociation(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.Association)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        }

        public static void removeAssociation(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        public static boolean hasAssociation(String id, org.semanticwb.model.SWBModel model)
        {
            return (getAssociation(id, model)!=null);
        }

        public static java.util.Iterator<org.semanticwb.process.model.Association> listAssociationByAssociationSourceRef(org.semanticwb.process.model.BPMNBaseElement value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Association> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_associationSourceRef, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Association> listAssociationByAssociationSourceRef(org.semanticwb.process.model.BPMNBaseElement value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Association> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_associationSourceRef,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Association> listAssociationByDocumentation(org.semanticwb.process.model.Documentation value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Association> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Association> listAssociationByDocumentation(org.semanticwb.process.model.Documentation value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Association> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Association> listAssociationByAssociationTargetRef(org.semanticwb.process.model.BPMNBaseElement value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Association> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_associationTargetRef, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Association> listAssociationByAssociationTargetRef(org.semanticwb.process.model.BPMNBaseElement value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Association> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_associationTargetRef,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Association> listAssociationByExtensionDefinition(org.semanticwb.process.model.ExtensionDefinition value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Association> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionDefinition, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Association> listAssociationByExtensionDefinition(org.semanticwb.process.model.ExtensionDefinition value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Association> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionDefinition,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Association> listAssociationByExtensionValue(org.semanticwb.process.model.ExtensionAttributeValue value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Association> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionValue, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Association> listAssociationByExtensionValue(org.semanticwb.process.model.ExtensionAttributeValue value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Association> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionValue,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public AssociationBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public void setAssociationSourceRef(org.semanticwb.process.model.BPMNBaseElement value)
    {
        getSemanticObject().setObjectProperty(swp_associationSourceRef, value.getSemanticObject());
    }

    public void removeAssociationSourceRef()
    {
        getSemanticObject().removeProperty(swp_associationSourceRef);
    }

    public org.semanticwb.process.model.BPMNBaseElement getAssociationSourceRef()
    {
         org.semanticwb.process.model.BPMNBaseElement ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_associationSourceRef);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.BPMNBaseElement)obj.createGenericInstance();
         }
         return ret;
    }

    public String getAssociationDirection()
    {
        return getSemanticObject().getProperty(swp_associationDirection);
    }

    public void setAssociationDirection(String value)
    {
        getSemanticObject().setProperty(swp_associationDirection, value);
    }

    public void setAssociationTargetRef(org.semanticwb.process.model.BPMNBaseElement value)
    {
        getSemanticObject().setObjectProperty(swp_associationTargetRef, value.getSemanticObject());
    }

    public void removeAssociationTargetRef()
    {
        getSemanticObject().removeProperty(swp_associationTargetRef);
    }

    public org.semanticwb.process.model.BPMNBaseElement getAssociationTargetRef()
    {
         org.semanticwb.process.model.BPMNBaseElement ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_associationTargetRef);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.BPMNBaseElement)obj.createGenericInstance();
         }
         return ret;
    }
}
