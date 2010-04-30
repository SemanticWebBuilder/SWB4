package org.semanticwb.process.model.base;


public abstract class DefinitionBase extends org.semanticwb.process.model.BPMNBaseElement implements org.semanticwb.process.model.Documentable
{
    public static final org.semanticwb.platform.SemanticClass swp_Extension=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#Extension");
    public static final org.semanticwb.platform.SemanticProperty swp_hasExtension=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#hasExtension");
    public static final org.semanticwb.platform.SemanticClass swp_Rootable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#Rootable");
    public static final org.semanticwb.platform.SemanticProperty swp_hasRootElement=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#hasRootElement");
    public static final org.semanticwb.platform.SemanticClass swp_Relationship=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#Relationship");
    public static final org.semanticwb.platform.SemanticProperty swp_hasRelationship=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#hasRelationship");
    public static final org.semanticwb.platform.SemanticProperty swp_typeLanguage=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#typeLanguage");
    public static final org.semanticwb.platform.SemanticProperty swp_targetNameSpace=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#targetNameSpace");
    public static final org.semanticwb.platform.SemanticClass swp_ProcessImport=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#ProcessImport");
    public static final org.semanticwb.platform.SemanticProperty swp_hasImport=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#hasImport");
    public static final org.semanticwb.platform.SemanticClass swp_BPMNDiagram=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#BPMNDiagram");
    public static final org.semanticwb.platform.SemanticProperty swp_hasDiagram=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#hasDiagram");
    public static final org.semanticwb.platform.SemanticClass swp_Definition=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#Definition");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#Definition");

    public static class ClassMgr
    {

        public static java.util.Iterator<org.semanticwb.process.model.Definition> listDefinitions(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Definition>(it, true);
        }

        public static java.util.Iterator<org.semanticwb.process.model.Definition> listDefinitions()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Definition>(it, true);
        }

        public static org.semanticwb.process.model.Definition createDefinition(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.Definition.ClassMgr.createDefinition(String.valueOf(id), model);
        }

        public static org.semanticwb.process.model.Definition getDefinition(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.Definition)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static org.semanticwb.process.model.Definition createDefinition(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.Definition)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        }

        public static void removeDefinition(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        public static boolean hasDefinition(String id, org.semanticwb.model.SWBModel model)
        {
            return (getDefinition(id, model)!=null);
        }

        public static java.util.Iterator<org.semanticwb.process.model.Definition> listDefinitionByExtension(org.semanticwb.process.model.Extension value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Definition> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtension, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Definition> listDefinitionByExtension(org.semanticwb.process.model.Extension value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Definition> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtension,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Definition> listDefinitionByRootElement(org.semanticwb.process.model.Rootable value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Definition> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasRootElement, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Definition> listDefinitionByRootElement(org.semanticwb.process.model.Rootable value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Definition> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasRootElement,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Definition> listDefinitionByDocumentation(org.semanticwb.process.model.Documentation value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Definition> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Definition> listDefinitionByDocumentation(org.semanticwb.process.model.Documentation value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Definition> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Definition> listDefinitionByRelationship(org.semanticwb.process.model.Relationship value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Definition> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasRelationship, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Definition> listDefinitionByRelationship(org.semanticwb.process.model.Relationship value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Definition> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasRelationship,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Definition> listDefinitionByImport(org.semanticwb.process.model.ProcessImport value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Definition> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasImport, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Definition> listDefinitionByImport(org.semanticwb.process.model.ProcessImport value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Definition> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasImport,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Definition> listDefinitionByExtensionDefinition(org.semanticwb.process.model.ExtensionDefinition value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Definition> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionDefinition, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Definition> listDefinitionByExtensionDefinition(org.semanticwb.process.model.ExtensionDefinition value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Definition> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionDefinition,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Definition> listDefinitionByDiagram(org.semanticwb.process.model.BPMNDiagram value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Definition> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasDiagram, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Definition> listDefinitionByDiagram(org.semanticwb.process.model.BPMNDiagram value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Definition> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasDiagram,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Definition> listDefinitionByExtensionValue(org.semanticwb.process.model.ExtensionAttributeValue value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Definition> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionValue, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Definition> listDefinitionByExtensionValue(org.semanticwb.process.model.ExtensionAttributeValue value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Definition> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionValue,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public DefinitionBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Extension> listExtensions()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Extension>(getSemanticObject().listObjectProperties(swp_hasExtension));
    }

    public boolean hasExtension(org.semanticwb.process.model.Extension value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swp_hasExtension,value.getSemanticObject());
        }
        return ret;
    }

    public void addExtension(org.semanticwb.process.model.Extension value)
    {
        getSemanticObject().addObjectProperty(swp_hasExtension, value.getSemanticObject());
    }

    public void removeAllExtension()
    {
        getSemanticObject().removeProperty(swp_hasExtension);
    }

    public void removeExtension(org.semanticwb.process.model.Extension value)
    {
        getSemanticObject().removeObjectProperty(swp_hasExtension,value.getSemanticObject());
    }

    public org.semanticwb.process.model.Extension getExtension()
    {
         org.semanticwb.process.model.Extension ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_hasExtension);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.Extension)obj.createGenericInstance();
         }
         return ret;
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Rootable> listRootElements()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Rootable>(getSemanticObject().listObjectProperties(swp_hasRootElement));
    }

    public boolean hasRootElement(org.semanticwb.process.model.Rootable value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swp_hasRootElement,value.getSemanticObject());
        }
        return ret;
    }

    public void addRootElement(org.semanticwb.process.model.Rootable value)
    {
        getSemanticObject().addObjectProperty(swp_hasRootElement, value.getSemanticObject());
    }

    public void removeAllRootElement()
    {
        getSemanticObject().removeProperty(swp_hasRootElement);
    }

    public void removeRootElement(org.semanticwb.process.model.Rootable value)
    {
        getSemanticObject().removeObjectProperty(swp_hasRootElement,value.getSemanticObject());
    }

    public org.semanticwb.process.model.Rootable getRootElement()
    {
         org.semanticwb.process.model.Rootable ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_hasRootElement);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.Rootable)obj.createGenericInstance();
         }
         return ret;
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Relationship> listRelationships()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Relationship>(getSemanticObject().listObjectProperties(swp_hasRelationship));
    }

    public boolean hasRelationship(org.semanticwb.process.model.Relationship value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swp_hasRelationship,value.getSemanticObject());
        }
        return ret;
    }

    public void addRelationship(org.semanticwb.process.model.Relationship value)
    {
        getSemanticObject().addObjectProperty(swp_hasRelationship, value.getSemanticObject());
    }

    public void removeAllRelationship()
    {
        getSemanticObject().removeProperty(swp_hasRelationship);
    }

    public void removeRelationship(org.semanticwb.process.model.Relationship value)
    {
        getSemanticObject().removeObjectProperty(swp_hasRelationship,value.getSemanticObject());
    }

    public org.semanticwb.process.model.Relationship getRelationship()
    {
         org.semanticwb.process.model.Relationship ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_hasRelationship);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.Relationship)obj.createGenericInstance();
         }
         return ret;
    }

    public String getTypeLanguage()
    {
        return getSemanticObject().getProperty(swp_typeLanguage);
    }

    public void setTypeLanguage(String value)
    {
        getSemanticObject().setProperty(swp_typeLanguage, value);
    }

    public String getTargetNameSpace()
    {
        return getSemanticObject().getProperty(swp_targetNameSpace);
    }

    public void setTargetNameSpace(String value)
    {
        getSemanticObject().setProperty(swp_targetNameSpace, value);
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessImport> listImports()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessImport>(getSemanticObject().listObjectProperties(swp_hasImport));
    }

    public boolean hasImport(org.semanticwb.process.model.ProcessImport value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swp_hasImport,value.getSemanticObject());
        }
        return ret;
    }

    public void addImport(org.semanticwb.process.model.ProcessImport value)
    {
        getSemanticObject().addObjectProperty(swp_hasImport, value.getSemanticObject());
    }

    public void removeAllImport()
    {
        getSemanticObject().removeProperty(swp_hasImport);
    }

    public void removeImport(org.semanticwb.process.model.ProcessImport value)
    {
        getSemanticObject().removeObjectProperty(swp_hasImport,value.getSemanticObject());
    }

    public org.semanticwb.process.model.ProcessImport getImport()
    {
         org.semanticwb.process.model.ProcessImport ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_hasImport);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.ProcessImport)obj.createGenericInstance();
         }
         return ret;
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.BPMNDiagram> listDiagrams()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.BPMNDiagram>(getSemanticObject().listObjectProperties(swp_hasDiagram));
    }

    public boolean hasDiagram(org.semanticwb.process.model.BPMNDiagram value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swp_hasDiagram,value.getSemanticObject());
        }
        return ret;
    }

    public void addDiagram(org.semanticwb.process.model.BPMNDiagram value)
    {
        getSemanticObject().addObjectProperty(swp_hasDiagram, value.getSemanticObject());
    }

    public void removeAllDiagram()
    {
        getSemanticObject().removeProperty(swp_hasDiagram);
    }

    public void removeDiagram(org.semanticwb.process.model.BPMNDiagram value)
    {
        getSemanticObject().removeObjectProperty(swp_hasDiagram,value.getSemanticObject());
    }

    public org.semanticwb.process.model.BPMNDiagram getDiagram()
    {
         org.semanticwb.process.model.BPMNDiagram ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_hasDiagram);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.BPMNDiagram)obj.createGenericInstance();
         }
         return ret;
    }

    public org.semanticwb.process.model.ProcessSite getProcessSite()
    {
        return (org.semanticwb.process.model.ProcessSite)getSemanticObject().getModel().getModelObject().createGenericInstance();
    }
}
