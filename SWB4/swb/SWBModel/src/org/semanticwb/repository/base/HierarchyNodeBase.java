package org.semanticwb.repository.base;


public class HierarchyNodeBase extends org.semanticwb.repository.BaseNode implements org.semanticwb.repository.Traceable
{
    public static final org.semanticwb.platform.SemanticProperty jcr_created=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.jcp.org/jcr/1.0#created");
    public static final org.semanticwb.platform.SemanticClass nt_HierarchyNode=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.jcp.org/jcr/nt/1.0#hierarchyNode");


    public static org.semanticwb.repository.HierarchyNode createHierarchyNode(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.repository.HierarchyNode)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, nt_HierarchyNode), nt_HierarchyNode);
    }

    public HierarchyNodeBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public java.util.Date getCreated()
    {
        return getSemanticObject().getDateProperty(jcr_created);
    }

    public void setCreated(java.util.Date created)
    {
        getSemanticObject().setDateProperty(jcr_created, created);
    }
}
