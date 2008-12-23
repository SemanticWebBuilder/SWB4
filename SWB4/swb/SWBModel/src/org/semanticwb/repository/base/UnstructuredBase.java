package org.semanticwb.repository.base;


public class UnstructuredBase extends org.semanticwb.repository.BaseNode 
{
    public static final org.semanticwb.platform.SemanticClass nt_Unstructured=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.jcp.org/jcr/nt/1.0#unstructured");

    public UnstructuredBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public org.semanticwb.repository.Workspace getWorkspace()
    {
        return new org.semanticwb.repository.Workspace(getSemanticObject().getModel().getModelObject());
    }
}
