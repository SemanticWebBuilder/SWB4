package org.semanticwb.repository.base;


public abstract class HierarchyNodeBase extends org.semanticwb.repository.BaseNode implements org.semanticwb.repository.Traceable
{
    public static final org.semanticwb.platform.SemanticClass nt_HierarchyNode=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.jcp.org/jcr/nt/1.0#hierarchyNode");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.jcp.org/jcr/nt/1.0#hierarchyNode");

    public static class ClassMgr
    {

        public static java.util.Iterator<org.semanticwb.repository.HierarchyNode> listHierarchyNodes(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.repository.HierarchyNode>(it, true);
        }

        public static java.util.Iterator<org.semanticwb.repository.HierarchyNode> listHierarchyNodes()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.repository.HierarchyNode>(it, true);
        }

        public static org.semanticwb.repository.HierarchyNode getHierarchyNode(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.repository.HierarchyNode)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static org.semanticwb.repository.HierarchyNode createHierarchyNode(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.repository.HierarchyNode)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        }

        public static void removeHierarchyNode(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        public static boolean hasHierarchyNode(String id, org.semanticwb.model.SWBModel model)
        {
            return (getHierarchyNode(id, model)!=null);
        }

        public static java.util.Iterator<org.semanticwb.repository.HierarchyNode> listHierarchyNodeByParent(org.semanticwb.repository.BaseNode parentnode,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.repository.HierarchyNode> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swbrep_parentNode, parentnode.getSemanticObject()));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.repository.HierarchyNode> listHierarchyNodeByParent(org.semanticwb.repository.BaseNode parentnode)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.repository.HierarchyNode> it=new org.semanticwb.model.GenericIterator(parentnode.getSemanticObject().getModel().listSubjects(swbrep_parentNode,parentnode.getSemanticObject()));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.repository.HierarchyNode> listHierarchyNodeByNode(org.semanticwb.repository.BaseNode hasnodes,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.repository.HierarchyNode> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swbrep_hasNodes, hasnodes.getSemanticObject()));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.repository.HierarchyNode> listHierarchyNodeByNode(org.semanticwb.repository.BaseNode hasnodes)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.repository.HierarchyNode> it=new org.semanticwb.model.GenericIterator(hasnodes.getSemanticObject().getModel().listSubjects(swbrep_hasNodes,hasnodes.getSemanticObject()));
            return it;
        }
    }

    public HierarchyNodeBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public java.util.Date getCreated()
    {
        return getSemanticObject().getDateProperty(jcr_created);
    }

    public void setCreated(java.util.Date value)
    {
        getSemanticObject().setDateProperty(jcr_created, value);
    }
}
