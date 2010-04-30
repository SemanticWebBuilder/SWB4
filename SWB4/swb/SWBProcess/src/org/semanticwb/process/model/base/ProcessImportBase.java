package org.semanticwb.process.model.base;


public abstract class ProcessImportBase extends org.semanticwb.process.model.BPMNElement 
{
    public static final org.semanticwb.platform.SemanticProperty swp_importType=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#importType");
    public static final org.semanticwb.platform.SemanticProperty swp_location=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#location");
    public static final org.semanticwb.platform.SemanticProperty swp_namespace=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#namespace");
    public static final org.semanticwb.platform.SemanticClass swp_ProcessImport=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#ProcessImport");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#ProcessImport");

    public static class ClassMgr
    {

        public static java.util.Iterator<org.semanticwb.process.model.ProcessImport> listProcessImports(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessImport>(it, true);
        }

        public static java.util.Iterator<org.semanticwb.process.model.ProcessImport> listProcessImports()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessImport>(it, true);
        }

        public static org.semanticwb.process.model.ProcessImport createProcessImport(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.ProcessImport.ClassMgr.createProcessImport(String.valueOf(id), model);
        }

        public static org.semanticwb.process.model.ProcessImport getProcessImport(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.ProcessImport)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static org.semanticwb.process.model.ProcessImport createProcessImport(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.ProcessImport)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        }

        public static void removeProcessImport(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        public static boolean hasProcessImport(String id, org.semanticwb.model.SWBModel model)
        {
            return (getProcessImport(id, model)!=null);
        }
    }

    public ProcessImportBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public String getImportType()
    {
        return getSemanticObject().getProperty(swp_importType);
    }

    public void setImportType(String value)
    {
        getSemanticObject().setProperty(swp_importType, value);
    }

    public String getLocation()
    {
        return getSemanticObject().getProperty(swp_location);
    }

    public void setLocation(String value)
    {
        getSemanticObject().setProperty(swp_location, value);
    }

    public String getNamespace()
    {
        return getSemanticObject().getProperty(swp_namespace);
    }

    public void setNamespace(String value)
    {
        getSemanticObject().setProperty(swp_namespace, value);
    }

    public org.semanticwb.process.model.ProcessSite getProcessSite()
    {
        return (org.semanticwb.process.model.ProcessSite)getSemanticObject().getModel().getModelObject().createGenericInstance();
    }
}
