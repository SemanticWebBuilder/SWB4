package org.semanticwb.repository.base;


public class FileBase extends org.semanticwb.repository.HierarchyNode implements org.semanticwb.repository.Traceable
{
    public static final org.semanticwb.platform.SemanticClass nt_File=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.jcp.org/jcr/nt/1.0#file");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.jcp.org/jcr/nt/1.0#file");

    public FileBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public static org.semanticwb.repository.File getFile(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.repository.File)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
    }

    public static java.util.Iterator<org.semanticwb.repository.File> listFiles(org.semanticwb.model.SWBModel model)
    {
        java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
        return new org.semanticwb.model.GenericIterator<org.semanticwb.repository.File>(org.semanticwb.repository.File.class, it, true);
    }

    public static java.util.Iterator<org.semanticwb.repository.File> listFiles()
    {
        java.util.Iterator it=sclass.listInstances();
        return new org.semanticwb.model.GenericIterator<org.semanticwb.repository.File>(org.semanticwb.repository.File.class, it, true);
    }

    public static org.semanticwb.repository.File createFile(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.repository.File)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
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
