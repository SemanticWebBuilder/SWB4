package org.semanticwb.process.base;


public class ProcessObjectBase extends org.semanticwb.model.base.GenericObjectBase 
{
    public static class ClassMgr
    {
       public static final org.semanticwb.platform.SemanticClass swbps_ProcessObject=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ProcessObject");
       public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ProcessObject");

       public static java.util.Iterator<org.semanticwb.process.ProcessObject> listProcessObjects(org.semanticwb.model.SWBModel model)
       {
           java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
           return new org.semanticwb.model.GenericIterator<org.semanticwb.process.ProcessObject>(it, true);
       }

       public static java.util.Iterator<org.semanticwb.process.ProcessObject> listProcessObjects()
       {
           java.util.Iterator it=sclass.listInstances();
           return new org.semanticwb.model.GenericIterator<org.semanticwb.process.ProcessObject>(it, true);
       }

       public static org.semanticwb.process.ProcessObject getProcessObject(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.process.ProcessObject)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
       }

       public static org.semanticwb.process.ProcessObject createProcessObject(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.process.ProcessObject)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
       }

       public static void removeProcessObject(String id, org.semanticwb.model.SWBModel model)
       {
           model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
       }

       public static boolean hasProcessObject(String id, org.semanticwb.model.SWBModel model)
       {
           return (getProcessObject(id, model)!=null);
       }
    }

    public ProcessObjectBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public void remove()
    {
        getSemanticObject().remove();
    }

    public java.util.Iterator<org.semanticwb.model.GenericObject> listRelatedObjects()
    {
        return new org.semanticwb.model.GenericIterator(getSemanticObject().listRelatedObjects(),true);
    }

    public org.semanticwb.process.ProcessSite getProcessSite()
    {
        return (org.semanticwb.process.ProcessSite)getSemanticObject().getModel().getModelObject().createGenericInstance();
    }
}
