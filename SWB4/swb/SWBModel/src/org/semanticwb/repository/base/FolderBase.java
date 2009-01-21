package org.semanticwb.repository.base;


public class FolderBase extends org.semanticwb.repository.HierarchyNode 
{
    public static final org.semanticwb.platform.SemanticClass nt_Folder=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.jcp.org/jcr/nt/1.0#folder");


    public static org.semanticwb.repository.Folder createFolder(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.repository.Folder)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, nt_Folder), nt_Folder);
    }

    public FolderBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
}
