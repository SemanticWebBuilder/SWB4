package org.semanticwb.process.model.base;


public abstract class RelationshipBase extends org.semanticwb.process.model.BPMNBaseElement implements org.semanticwb.model.Descriptiveable,org.semanticwb.process.model.Documentable
{
    public static final org.semanticwb.platform.SemanticClass owl_Class=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.w3.org/2002/07/owl#Class");
    public static final org.semanticwb.platform.SemanticProperty swp_hasTarget=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#hasTarget");
    public static final org.semanticwb.platform.SemanticProperty swp_relType=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#relType");
    public static final org.semanticwb.platform.SemanticProperty swp_relDirection=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#relDirection");
    public static final org.semanticwb.platform.SemanticProperty swp_hasSource=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#hasSource");
    public static final org.semanticwb.platform.SemanticClass swp_Relationship=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#Relationship");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#Relationship");

    public static class ClassMgr
    {

        public static java.util.Iterator<org.semanticwb.process.model.Relationship> listRelationships(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Relationship>(it, true);
        }

        public static java.util.Iterator<org.semanticwb.process.model.Relationship> listRelationships()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Relationship>(it, true);
        }

        public static org.semanticwb.process.model.Relationship createRelationship(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.Relationship.ClassMgr.createRelationship(String.valueOf(id), model);
        }

        public static org.semanticwb.process.model.Relationship getRelationship(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.Relationship)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static org.semanticwb.process.model.Relationship createRelationship(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.Relationship)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        }

        public static void removeRelationship(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        public static boolean hasRelationship(String id, org.semanticwb.model.SWBModel model)
        {
            return (getRelationship(id, model)!=null);
        }

        public static java.util.Iterator<org.semanticwb.process.model.Relationship> listRelationshipByDocumentation(org.semanticwb.process.model.Documentation value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Relationship> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Relationship> listRelationshipByDocumentation(org.semanticwb.process.model.Documentation value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Relationship> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Relationship> listRelationshipByExtensionDefinition(org.semanticwb.process.model.ExtensionDefinition value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Relationship> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionDefinition, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Relationship> listRelationshipByExtensionDefinition(org.semanticwb.process.model.ExtensionDefinition value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Relationship> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionDefinition,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Relationship> listRelationshipByExtensionValue(org.semanticwb.process.model.ExtensionAttributeValue value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Relationship> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionValue, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Relationship> listRelationshipByExtensionValue(org.semanticwb.process.model.ExtensionAttributeValue value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Relationship> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionValue,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public RelationshipBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public String getTitle()
    {
        return getSemanticObject().getProperty(swb_title);
    }

    public void setTitle(String value)
    {
        getSemanticObject().setProperty(swb_title, value);
    }

    public String getTitle(String lang)
    {
        return getSemanticObject().getProperty(swb_title, null, lang);
    }

    public String getDisplayTitle(String lang)
    {
        return getSemanticObject().getLocaleProperty(swb_title, lang);
    }

    public void setTitle(String title, String lang)
    {
        getSemanticObject().setProperty(swb_title, title, lang);
    }

    public org.semanticwb.platform.SemanticIterator<org.semanticwb.platform.SemanticObject> listTargets()
    {
        com.hp.hpl.jena.rdf.model.StmtIterator stit=getSemanticObject().getRDFResource().listProperties(swp_hasTarget.getRDFProperty());
        return new org.semanticwb.platform.SemanticIterator<org.semanticwb.platform.SemanticObject>(stit);
    }

    public void addTarget(org.semanticwb.platform.SemanticObject value)
    {
        getSemanticObject().addObjectProperty(swp_hasTarget, value);
    }

    public void removeAllTarget()
    {
        getSemanticObject().removeProperty(swp_hasTarget);
    }

    public void removeTarget(org.semanticwb.platform.SemanticObject semanticobject)
    {
        getSemanticObject().removeObjectProperty(swp_hasTarget,semanticobject);
    }

    public org.semanticwb.platform.SemanticObject getTarget()
    {
         org.semanticwb.platform.SemanticObject ret=null;
         ret=getSemanticObject().getObjectProperty(swp_hasTarget);
         return ret;
    }

    public String getRelType()
    {
        return getSemanticObject().getProperty(swp_relType);
    }

    public void setRelType(String value)
    {
        getSemanticObject().setProperty(swp_relType, value);
    }

    public String getRelDirection()
    {
        return getSemanticObject().getProperty(swp_relDirection);
    }

    public void setRelDirection(String value)
    {
        getSemanticObject().setProperty(swp_relDirection, value);
    }

    public String getDescription()
    {
        return getSemanticObject().getProperty(swb_description);
    }

    public void setDescription(String value)
    {
        getSemanticObject().setProperty(swb_description, value);
    }

    public String getDescription(String lang)
    {
        return getSemanticObject().getProperty(swb_description, null, lang);
    }

    public String getDisplayDescription(String lang)
    {
        return getSemanticObject().getLocaleProperty(swb_description, lang);
    }

    public void setDescription(String description, String lang)
    {
        getSemanticObject().setProperty(swb_description, description, lang);
    }

    public org.semanticwb.platform.SemanticIterator<org.semanticwb.platform.SemanticObject> listSources()
    {
        com.hp.hpl.jena.rdf.model.StmtIterator stit=getSemanticObject().getRDFResource().listProperties(swp_hasSource.getRDFProperty());
        return new org.semanticwb.platform.SemanticIterator<org.semanticwb.platform.SemanticObject>(stit);
    }

    public void addSource(org.semanticwb.platform.SemanticObject value)
    {
        getSemanticObject().addObjectProperty(swp_hasSource, value);
    }

    public void removeAllSource()
    {
        getSemanticObject().removeProperty(swp_hasSource);
    }

    public void removeSource(org.semanticwb.platform.SemanticObject semanticobject)
    {
        getSemanticObject().removeObjectProperty(swp_hasSource,semanticobject);
    }

    public org.semanticwb.platform.SemanticObject getSource()
    {
         org.semanticwb.platform.SemanticObject ret=null;
         ret=getSemanticObject().getObjectProperty(swp_hasSource);
         return ret;
    }
}
