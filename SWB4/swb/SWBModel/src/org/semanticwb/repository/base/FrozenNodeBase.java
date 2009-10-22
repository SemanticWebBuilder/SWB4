package org.semanticwb.repository.base;


public class FrozenNodeBase extends org.semanticwb.repository.BaseNode implements org.semanticwb.repository.Referenceable
{
    public static class ClassMgr
    {
       public static final org.semanticwb.platform.SemanticClass nt_BaseNode=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.jcp.org/jcr/nt/1.0#base");
       public static final org.semanticwb.platform.SemanticProperty swbrep_parentNode=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/repository#parentNode");
       public static final org.semanticwb.platform.SemanticProperty jcr_primaryType=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.jcp.org/jcr/1.0#primaryType");
       public static final org.semanticwb.platform.SemanticProperty jcr_frozenPrimaryType=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.jcp.org/jcr/1.0#frozenPrimaryType");
       public static final org.semanticwb.platform.SemanticProperty jcr_frozenMixinTypes=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.jcp.org/jcr/1.0#frozenMixinTypes");
       public static final org.semanticwb.platform.SemanticProperty jcr_frozenUuid=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.jcp.org/jcr/1.0#frozenUuid");
       public static final org.semanticwb.platform.SemanticProperty jcr_uuid=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.jcp.org/jcr/1.0#uuid");
       public static final org.semanticwb.platform.SemanticProperty swbrep_path=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/repository#path");
       public static final org.semanticwb.platform.SemanticProperty swbrep_name=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/repository#name");
       public static final org.semanticwb.platform.SemanticProperty swbrep_hasNodes=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/repository#hasNodes");
       public static final org.semanticwb.platform.SemanticProperty jcr_mixinTypes=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.jcp.org/jcr/1.0#mixinTypes");
       public static final org.semanticwb.platform.SemanticClass nt_FrozenNode=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.jcp.org/jcr/nt/1.0#frozenNode");
       public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.jcp.org/jcr/nt/1.0#frozenNode");

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
    }

    public FrozenNodeBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public String getFrozenPrimaryType()
    {
        return getSemanticObject().getProperty(ClassMgr.jcr_frozenPrimaryType);
    }

    public void setFrozenPrimaryType(String value)
    {
        getSemanticObject().setProperty(ClassMgr.jcr_frozenPrimaryType, value);
    }

    public String getFrozenMixinTypes()
    {
        return getSemanticObject().getProperty(ClassMgr.jcr_frozenMixinTypes);
    }

    public void setFrozenMixinTypes(String value)
    {
        getSemanticObject().setProperty(ClassMgr.jcr_frozenMixinTypes, value);
    }

    public String getFrozenUuid()
    {
        return getSemanticObject().getProperty(ClassMgr.jcr_frozenUuid);
    }

    public void setFrozenUuid(String value)
    {
        getSemanticObject().setProperty(ClassMgr.jcr_frozenUuid, value);
    }

    public String getUuid()
    {
        return getSemanticObject().getProperty(ClassMgr.jcr_uuid);
    }

    public void setUuid(String value)
    {
        getSemanticObject().setProperty(ClassMgr.jcr_uuid, value);
    }
}
