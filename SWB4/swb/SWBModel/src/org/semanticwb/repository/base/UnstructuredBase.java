package org.semanticwb.repository.base;


public class UnstructuredBase extends org.semanticwb.repository.BaseNode 
{
    public static final org.semanticwb.platform.SemanticClass nt_Unstructured=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.jcp.org/jcr/nt/1.0#unstructured");


    public static org.semanticwb.repository.Unstructured createUnstructured(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.repository.Unstructured)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, nt_Unstructured), nt_Unstructured);
    }

    public UnstructuredBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public org.semanticwb.repository.Workspace getWorkspace()
    {
        return new org.semanticwb.repository.Workspace(getSemanticObject().getModel().getModelObject());
    }
}
