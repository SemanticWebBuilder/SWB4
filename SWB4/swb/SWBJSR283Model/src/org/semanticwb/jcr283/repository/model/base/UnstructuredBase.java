package org.semanticwb.jcr283.repository.model.base;


public abstract class UnstructuredBase extends org.semanticwb.jcr283.repository.model.Base 
{
       public static final org.semanticwb.platform.SemanticClass nt_Unstructured=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.jcp.org/jcr/nt/1.0#unstructured");
       public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.jcp.org/jcr/nt/1.0#unstructured");
    public static class ClassMgr
    {

       public static java.util.Iterator<org.semanticwb.jcr283.repository.model.Unstructured> listUnstructureds(org.semanticwb.model.SWBModel model)
       {
           java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
           return new org.semanticwb.model.GenericIterator<org.semanticwb.jcr283.repository.model.Unstructured>(it, true);
       }

       public static java.util.Iterator<org.semanticwb.jcr283.repository.model.Unstructured> listUnstructureds()
       {
           java.util.Iterator it=sclass.listInstances();
           return new org.semanticwb.model.GenericIterator<org.semanticwb.jcr283.repository.model.Unstructured>(it, true);
       }

       public static org.semanticwb.jcr283.repository.model.Unstructured getUnstructured(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.jcr283.repository.model.Unstructured)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
       }

       public static org.semanticwb.jcr283.repository.model.Unstructured createUnstructured(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.jcr283.repository.model.Unstructured)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
       }

       public static void removeUnstructured(String id, org.semanticwb.model.SWBModel model)
       {
           model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
       }

       public static boolean hasUnstructured(String id, org.semanticwb.model.SWBModel model)
       {
           return (getUnstructured(id, model)!=null);
       }
    }

    public UnstructuredBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public org.semanticwb.jcr283.repository.model.Workspace getWorkspace()
    {
        return (org.semanticwb.jcr283.repository.model.Workspace)getSemanticObject().getModel().getModelObject().createGenericInstance();
    }
}
