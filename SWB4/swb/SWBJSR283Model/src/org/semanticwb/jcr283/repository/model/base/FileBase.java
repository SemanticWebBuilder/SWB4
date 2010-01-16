package org.semanticwb.jcr283.repository.model.base;


public abstract class FileBase extends org.semanticwb.jcr283.repository.model.HierarchyNode 
{
       public static final org.semanticwb.platform.SemanticClass nt_File=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.jcp.org/jcr/nt/1.0#file");
       public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.jcp.org/jcr/nt/1.0#file");
    public static class ClassMgr
    {

       public static java.util.Iterator<org.semanticwb.jcr283.repository.model.File> listFiles(org.semanticwb.model.SWBModel model)
       {
           java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
           return new org.semanticwb.model.GenericIterator<org.semanticwb.jcr283.repository.model.File>(it, true);
       }

       public static java.util.Iterator<org.semanticwb.jcr283.repository.model.File> listFiles()
       {
           java.util.Iterator it=sclass.listInstances();
           return new org.semanticwb.model.GenericIterator<org.semanticwb.jcr283.repository.model.File>(it, true);
       }

       public static org.semanticwb.jcr283.repository.model.File getFile(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.jcr283.repository.model.File)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
       }

       public static org.semanticwb.jcr283.repository.model.File createFile(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.jcr283.repository.model.File)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
       }

       public static void removeFile(String id, org.semanticwb.model.SWBModel model)
       {
           model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
       }

       public static boolean hasFile(String id, org.semanticwb.model.SWBModel model)
       {
           return (getFile(id, model)!=null);
       }
    }

    public FileBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
}
