package org.semanticwb.repository.base;


public class FileBase extends org.semanticwb.repository.HierarchyNode 
{
    public static final org.semanticwb.platform.SemanticClass nt_File=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.jcp.org/jcr/nt/1.0#file");


    public static org.semanticwb.repository.File createFile(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.repository.File)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, nt_File), nt_File);
    }

    public FileBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
}
