package org.semanticwb.repository.base;


public class FolderBase extends org.semanticwb.repository.HierarchyNode 
{
    public static final org.semanticwb.platform.SemanticClass nt_Folder=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.jcp.org/jcr/nt/1.0#folder");

    public FolderBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
}
