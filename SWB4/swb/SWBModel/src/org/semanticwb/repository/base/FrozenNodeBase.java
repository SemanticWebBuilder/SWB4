package org.semanticwb.repository.base;


public abstract class FrozenNodeBase extends org.semanticwb.repository.BaseNode implements org.semanticwb.repository.Referenceable
{
    public static final org.semanticwb.platform.SemanticProperty jcr_frozenPrimaryType=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.jcp.org/jcr/1.0#frozenPrimaryType");
    public static final org.semanticwb.platform.SemanticProperty jcr_frozenMixinTypes=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.jcp.org/jcr/1.0#frozenMixinTypes");
    public static final org.semanticwb.platform.SemanticProperty jcr_frozenUuid=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.jcp.org/jcr/1.0#frozenUuid");
    public static final org.semanticwb.platform.SemanticClass nt_FrozenNode=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.jcp.org/jcr/nt/1.0#frozenNode");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.jcp.org/jcr/nt/1.0#frozenNode");

    public static class ClassMgr
    {

        public static java.util.Iterator<org.semanticwb.repository.FrozenNode> listFrozenNodes(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.repository.FrozenNode>(it, true);
        }

        public static java.util.Iterator<org.semanticwb.repository.FrozenNode> listFrozenNodes()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.repository.FrozenNode>(it, true);
        }

        public static org.semanticwb.repository.FrozenNode getFrozenNode(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.repository.FrozenNode)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static org.semanticwb.repository.FrozenNode createFrozenNode(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.repository.FrozenNode)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        }

        public static void removeFrozenNode(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        public static boolean hasFrozenNode(String id, org.semanticwb.model.SWBModel model)
        {
            return (getFrozenNode(id, model)!=null);
        }

        public static java.util.Iterator<org.semanticwb.repository.FrozenNode> listFrozenNodeByParent(org.semanticwb.repository.BaseNode parentnode,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.repository.FrozenNode> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swbrep_parentNode, parentnode.getSemanticObject()));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.repository.FrozenNode> listFrozenNodeByParent(org.semanticwb.repository.BaseNode parentnode)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.repository.FrozenNode> it=new org.semanticwb.model.GenericIterator(parentnode.getSemanticObject().getModel().listSubjects(swbrep_parentNode,parentnode.getSemanticObject()));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.repository.FrozenNode> listFrozenNodeByNode(org.semanticwb.repository.BaseNode hasnodes,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.repository.FrozenNode> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swbrep_hasNodes, hasnodes.getSemanticObject()));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.repository.FrozenNode> listFrozenNodeByNode(org.semanticwb.repository.BaseNode hasnodes)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.repository.FrozenNode> it=new org.semanticwb.model.GenericIterator(hasnodes.getSemanticObject().getModel().listSubjects(swbrep_hasNodes,hasnodes.getSemanticObject()));
            return it;
        }
    }

    public FrozenNodeBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public String getFrozenPrimaryType()
    {
        return getSemanticObject().getProperty(jcr_frozenPrimaryType);
    }

    public void setFrozenPrimaryType(String value)
    {
        getSemanticObject().setProperty(jcr_frozenPrimaryType, value);
    }

    public String getFrozenMixinTypes()
    {
        return getSemanticObject().getProperty(jcr_frozenMixinTypes);
    }

    public void setFrozenMixinTypes(String value)
    {
        getSemanticObject().setProperty(jcr_frozenMixinTypes, value);
    }

    public String getFrozenUuid()
    {
        return getSemanticObject().getProperty(jcr_frozenUuid);
    }

    public void setFrozenUuid(String value)
    {
        getSemanticObject().setProperty(jcr_frozenUuid, value);
    }

    public String getUuid()
    {
        return getSemanticObject().getProperty(jcr_uuid);
    }

    public void setUuid(String value)
    {
        getSemanticObject().setProperty(jcr_uuid, value);
    }
}
