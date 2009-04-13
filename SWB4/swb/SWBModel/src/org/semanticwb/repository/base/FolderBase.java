package org.semanticwb.repository.base;


public class FolderBase extends org.semanticwb.repository.HierarchyNode implements org.semanticwb.repository.Traceable
{
    public static final org.semanticwb.platform.SemanticClass nt_Folder=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.jcp.org/jcr/nt/1.0#folder");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.jcp.org/jcr/nt/1.0#folder");

    public FolderBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public static java.util.Iterator<org.semanticwb.repository.Folder> listFolders(org.semanticwb.model.SWBModel model)
    {
        java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
        return new org.semanticwb.model.GenericIterator<org.semanticwb.repository.Folder>(it, true);
    }

    public static java.util.Iterator<org.semanticwb.repository.Folder> listFolders()
    {
        java.util.Iterator it=sclass.listInstances();
        return new org.semanticwb.model.GenericIterator<org.semanticwb.repository.Folder>(it, true);
    }

    public static org.semanticwb.repository.Folder getFolder(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.repository.Folder)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
    }

    public static org.semanticwb.repository.Folder createFolder(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.repository.Folder)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
    }

    public static void removeFolder(String id, org.semanticwb.model.SWBModel model)
    {
        model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
    }

    public static boolean hasFolder(String id, org.semanticwb.model.SWBModel model)
    {
        return (getFolder(id, model)!=null);
    }
}
