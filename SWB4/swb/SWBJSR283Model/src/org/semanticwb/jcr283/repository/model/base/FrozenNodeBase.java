package org.semanticwb.jcr283.repository.model.base;


public abstract class FrozenNodeBase extends org.semanticwb.jcr283.repository.model.Base 
{
       public static final org.semanticwb.platform.SemanticClass xsd_String=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.w3.org/2001/XMLSchema#string");
       public static final org.semanticwb.platform.SemanticProperty jcr_frozenPrimaryType=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.jcp.org/jcr/1.0#frozenPrimaryType");
       public static final org.semanticwb.platform.SemanticProperty jcr_frozenMixinTypes=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.jcp.org/jcr/1.0#frozenMixinTypes");
       public static final org.semanticwb.platform.SemanticProperty jcr_frozenUuid=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.jcp.org/jcr/1.0#frozenUuid");
       public static final org.semanticwb.platform.SemanticClass nt_FrozenNode=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.jcp.org/jcr/nt/1.0#frozenNode");
       public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.jcp.org/jcr/nt/1.0#frozenNode");
    public static class ClassMgr
    {

       public static java.util.Iterator<org.semanticwb.jcr283.repository.model.FrozenNode> listFrozenNodes(org.semanticwb.model.SWBModel model)
       {
           java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
           return new org.semanticwb.model.GenericIterator<org.semanticwb.jcr283.repository.model.FrozenNode>(it, true);
       }

       public static java.util.Iterator<org.semanticwb.jcr283.repository.model.FrozenNode> listFrozenNodes()
       {
           java.util.Iterator it=sclass.listInstances();
           return new org.semanticwb.model.GenericIterator<org.semanticwb.jcr283.repository.model.FrozenNode>(it, true);
       }

       public static org.semanticwb.jcr283.repository.model.FrozenNode getFrozenNode(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.jcr283.repository.model.FrozenNode)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
       }

       public static org.semanticwb.jcr283.repository.model.FrozenNode createFrozenNode(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.jcr283.repository.model.FrozenNode)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
       }

       public static void removeFrozenNode(String id, org.semanticwb.model.SWBModel model)
       {
           model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
       }

       public static boolean hasFrozenNode(String id, org.semanticwb.model.SWBModel model)
       {
           return (getFrozenNode(id, model)!=null);
       }
   public static java.util.Iterator<org.semanticwb.jcr283.repository.model.FrozenNode> listFrozenNodeByParentNode(org.semanticwb.jcr283.repository.model.Base parentnode,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.jcr283.repository.model.FrozenNode> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swbrep_parentNode, parentnode.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.jcr283.repository.model.FrozenNode> listFrozenNodeByParentNode(org.semanticwb.jcr283.repository.model.Base parentnode)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.jcr283.repository.model.FrozenNode> it=new org.semanticwb.model.GenericIterator(parentnode.getSemanticObject().getModel().listSubjects(swbrep_parentNode,parentnode.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.jcr283.repository.model.FrozenNode> listFrozenNodeByNode(org.semanticwb.jcr283.repository.model.Base hasnode,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.jcr283.repository.model.FrozenNode> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swbrep_hasNode, hasnode.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.jcr283.repository.model.FrozenNode> listFrozenNodeByNode(org.semanticwb.jcr283.repository.model.Base hasnode)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.jcr283.repository.model.FrozenNode> it=new org.semanticwb.model.GenericIterator(hasnode.getSemanticObject().getModel().listSubjects(swbrep_hasNode,hasnode.getSemanticObject()));
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
}
