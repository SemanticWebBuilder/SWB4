package org.semanticwb.process.model.base;


public abstract class ProcessInterfaceBase extends org.semanticwb.process.model.BPMNBaseElement implements org.semanticwb.process.model.Documentable,org.semanticwb.model.Descriptiveable
{
    public static final org.semanticwb.platform.SemanticClass swp_Operation=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#Operation");
    public static final org.semanticwb.platform.SemanticProperty swp_hasOperationRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#hasOperationRef");
    public static final org.semanticwb.platform.SemanticClass swp_Callable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#Callable");
    public static final org.semanticwb.platform.SemanticProperty swp_hasCallableElement=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#hasCallableElement");
    public static final org.semanticwb.platform.SemanticClass swp_ProcessInterface=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#ProcessInterface");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#ProcessInterface");

    public static class ClassMgr
    {

        public static java.util.Iterator<org.semanticwb.process.model.ProcessInterface> listProcessInterfaces(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessInterface>(it, true);
        }

        public static java.util.Iterator<org.semanticwb.process.model.ProcessInterface> listProcessInterfaces()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessInterface>(it, true);
        }

        public static org.semanticwb.process.model.ProcessInterface createProcessInterface(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.ProcessInterface.ClassMgr.createProcessInterface(String.valueOf(id), model);
        }

        public static org.semanticwb.process.model.ProcessInterface getProcessInterface(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.ProcessInterface)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static org.semanticwb.process.model.ProcessInterface createProcessInterface(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.ProcessInterface)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        }

        public static void removeProcessInterface(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        public static boolean hasProcessInterface(String id, org.semanticwb.model.SWBModel model)
        {
            return (getProcessInterface(id, model)!=null);
        }

        public static java.util.Iterator<org.semanticwb.process.model.ProcessInterface> listProcessInterfaceByDocumentation(org.semanticwb.process.model.Documentation value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessInterface> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ProcessInterface> listProcessInterfaceByDocumentation(org.semanticwb.process.model.Documentation value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessInterface> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ProcessInterface> listProcessInterfaceByOperationRef(org.semanticwb.process.model.Operation value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessInterface> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasOperationRef, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ProcessInterface> listProcessInterfaceByOperationRef(org.semanticwb.process.model.Operation value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessInterface> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasOperationRef,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ProcessInterface> listProcessInterfaceByExtensionDefinition(org.semanticwb.process.model.ExtensionDefinition value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessInterface> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionDefinition, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ProcessInterface> listProcessInterfaceByExtensionDefinition(org.semanticwb.process.model.ExtensionDefinition value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessInterface> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionDefinition,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ProcessInterface> listProcessInterfaceByCallableElement(org.semanticwb.process.model.Callable value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessInterface> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasCallableElement, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ProcessInterface> listProcessInterfaceByCallableElement(org.semanticwb.process.model.Callable value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessInterface> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasCallableElement,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ProcessInterface> listProcessInterfaceByExtensionValue(org.semanticwb.process.model.ExtensionAttributeValue value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessInterface> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionValue, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ProcessInterface> listProcessInterfaceByExtensionValue(org.semanticwb.process.model.ExtensionAttributeValue value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessInterface> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionValue,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public ProcessInterfaceBase(org.semanticwb.platform.SemanticObject base)
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

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Operation> listOperationRefs()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Operation>(getSemanticObject().listObjectProperties(swp_hasOperationRef));
    }

    public boolean hasOperationRef(org.semanticwb.process.model.Operation value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swp_hasOperationRef,value.getSemanticObject());
        }
        return ret;
    }

    public void addOperationRef(org.semanticwb.process.model.Operation value)
    {
        getSemanticObject().addObjectProperty(swp_hasOperationRef, value.getSemanticObject());
    }

    public void removeAllOperationRef()
    {
        getSemanticObject().removeProperty(swp_hasOperationRef);
    }

    public void removeOperationRef(org.semanticwb.process.model.Operation value)
    {
        getSemanticObject().removeObjectProperty(swp_hasOperationRef,value.getSemanticObject());
    }

    public org.semanticwb.process.model.Operation getOperationRef()
    {
         org.semanticwb.process.model.Operation ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_hasOperationRef);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.Operation)obj.createGenericInstance();
         }
         return ret;
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Callable> listCallableElements()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Callable>(getSemanticObject().listObjectProperties(swp_hasCallableElement));
    }

    public boolean hasCallableElement(org.semanticwb.process.model.Callable value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swp_hasCallableElement,value.getSemanticObject());
        }
        return ret;
    }

    public void addCallableElement(org.semanticwb.process.model.Callable value)
    {
        getSemanticObject().addObjectProperty(swp_hasCallableElement, value.getSemanticObject());
    }

    public void removeAllCallableElement()
    {
        getSemanticObject().removeProperty(swp_hasCallableElement);
    }

    public void removeCallableElement(org.semanticwb.process.model.Callable value)
    {
        getSemanticObject().removeObjectProperty(swp_hasCallableElement,value.getSemanticObject());
    }

    public org.semanticwb.process.model.Callable getCallableElement()
    {
         org.semanticwb.process.model.Callable ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_hasCallableElement);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.Callable)obj.createGenericInstance();
         }
         return ret;
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

    public org.semanticwb.process.model.ProcessSite getProcessSite()
    {
        return (org.semanticwb.process.model.ProcessSite)getSemanticObject().getModel().getModelObject().createGenericInstance();
    }
}
