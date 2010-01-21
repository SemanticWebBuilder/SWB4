package org.semanticwb.jcr283.repository.model.base;


public abstract class BaseBase extends org.semanticwb.jcr283.repository.model.NodeTypes 
{
       public static final org.semanticwb.platform.SemanticClass xsd_String=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.w3.org/2001/XMLSchema#string");
       public static final org.semanticwb.platform.SemanticProperty jcr_primaryType=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.jcp.org/jcr/1.0#primaryType");
       public static final org.semanticwb.platform.SemanticClass nt_Base=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.jcp.org/jcr/nt/1.0#base");
       public static final org.semanticwb.platform.SemanticProperty swbrep_parentNode=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/jcr283#parentNode");
       public static final org.semanticwb.platform.SemanticProperty swbrep_hasNode=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/jcr283#hasNode");
       public static final org.semanticwb.platform.SemanticProperty jcr_mixinTypes=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.jcp.org/jcr/1.0#mixinTypes");
       public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.jcp.org/jcr/nt/1.0#base");
    public static class ClassMgr
    {

       public static java.util.Iterator<org.semanticwb.jcr283.repository.model.Base> listBases(org.semanticwb.model.SWBModel model)
       {
           java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
           return new org.semanticwb.model.GenericIterator<org.semanticwb.jcr283.repository.model.Base>(it, true);
       }

       public static java.util.Iterator<org.semanticwb.jcr283.repository.model.Base> listBases()
       {
           java.util.Iterator it=sclass.listInstances();
           return new org.semanticwb.model.GenericIterator<org.semanticwb.jcr283.repository.model.Base>(it, true);
       }

       public static org.semanticwb.jcr283.repository.model.Base getBase(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.jcr283.repository.model.Base)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
       }

       public static org.semanticwb.jcr283.repository.model.Base createBase(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.jcr283.repository.model.Base)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
       }

       public static void removeBase(String id, org.semanticwb.model.SWBModel model)
       {
           model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
       }

       public static boolean hasBase(String id, org.semanticwb.model.SWBModel model)
       {
           return (getBase(id, model)!=null);
       }
   public static java.util.Iterator<org.semanticwb.jcr283.repository.model.Base> listBaseByParentNode(org.semanticwb.jcr283.repository.model.Base parentnode,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.jcr283.repository.model.Base> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swbrep_parentNode, parentnode.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.jcr283.repository.model.Base> listBaseByParentNode(org.semanticwb.jcr283.repository.model.Base parentnode)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.jcr283.repository.model.Base> it=new org.semanticwb.model.GenericIterator(parentnode.getSemanticObject().getModel().listSubjects(swbrep_parentNode,parentnode.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.jcr283.repository.model.Base> listBaseByNode(org.semanticwb.jcr283.repository.model.Base hasnode,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.jcr283.repository.model.Base> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swbrep_hasNode, hasnode.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.jcr283.repository.model.Base> listBaseByNode(org.semanticwb.jcr283.repository.model.Base hasnode)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.jcr283.repository.model.Base> it=new org.semanticwb.model.GenericIterator(hasnode.getSemanticObject().getModel().listSubjects(swbrep_hasNode,hasnode.getSemanticObject()));
       return it;
   }
    }

    public BaseBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public String getPrimaryType()
    {
        return getSemanticObject().getProperty(jcr_primaryType);
    }

    public void setPrimaryType(String value)
    {
        getSemanticObject().setProperty(jcr_primaryType, value);
    }

    public void setParentNode(org.semanticwb.jcr283.repository.model.Base value)
    {
        getSemanticObject().setObjectProperty(swbrep_parentNode, value.getSemanticObject());
    }

    public void removeParentNode()
    {
        getSemanticObject().removeProperty(swbrep_parentNode);
    }


    public org.semanticwb.jcr283.repository.model.Base getParentNode()
    {
         org.semanticwb.jcr283.repository.model.Base ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swbrep_parentNode);
         if(obj!=null)
         {
             ret=(org.semanticwb.jcr283.repository.model.Base)obj.createGenericInstance();
         }
         return ret;
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.jcr283.repository.model.Base> listNodes()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.jcr283.repository.model.Base>(getSemanticObject().listObjectProperties(swbrep_hasNode));
    }

    public boolean hasNode(org.semanticwb.jcr283.repository.model.Base base)
    {
        if(base==null)return false;
        return getSemanticObject().hasObjectProperty(swbrep_hasNode,base.getSemanticObject());
    }


    public org.semanticwb.jcr283.repository.model.Base getNode()
    {
         org.semanticwb.jcr283.repository.model.Base ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swbrep_hasNode);
         if(obj!=null)
         {
             ret=(org.semanticwb.jcr283.repository.model.Base)obj.createGenericInstance();
         }
         return ret;
    }

    public String getMixinTypes()
    {
        return getSemanticObject().getProperty(jcr_mixinTypes);
    }

    public void setMixinTypes(String value)
    {
        getSemanticObject().setProperty(jcr_mixinTypes, value);
    }

    public org.semanticwb.jcr283.repository.model.Workspace getWorkspace()
    {
        return (org.semanticwb.jcr283.repository.model.Workspace)getSemanticObject().getModel().getModelObject().createGenericInstance();
    }
}
