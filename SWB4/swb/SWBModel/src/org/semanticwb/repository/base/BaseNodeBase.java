package org.semanticwb.repository.base;


public class BaseNodeBase extends org.semanticwb.model.base.GenericObjectBase 
{
    public static final org.semanticwb.platform.SemanticProperty jcr_primaryType=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.jcp.org/jcr/1.0#primaryType");
    public static final org.semanticwb.platform.SemanticClass nt_BaseNode=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.jcp.org/jcr/nt/1.0#base");
    public static final org.semanticwb.platform.SemanticProperty swbrep_parentNode=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/repository#parentNode");
    public static final org.semanticwb.platform.SemanticProperty swbrep_path=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/repository#path");
    public static final org.semanticwb.platform.SemanticProperty swbrep_name=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/repository#name");
    public static final org.semanticwb.platform.SemanticProperty swbrep_hasNodes=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/repository#hasNodes");
    public static final org.semanticwb.platform.SemanticProperty jcr_mixinTypes=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.jcp.org/jcr/1.0#mixinTypes");


    public static org.semanticwb.repository.BaseNode createBaseNode(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.repository.BaseNode)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, nt_BaseNode), nt_BaseNode);
    }

    public BaseNodeBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public String getPrimaryType()
    {
        return getSemanticObject().getProperty(jcr_primaryType);
    }

    public void setPrimaryType(String primaryType)
    {
        getSemanticObject().setProperty(jcr_primaryType, primaryType);
    }

    public void setParent(org.semanticwb.repository.BaseNode basenode)
    {
        getSemanticObject().setObjectProperty(swbrep_parentNode, basenode.getSemanticObject());
    }

    public void removeParent()
    {
        getSemanticObject().removeProperty(swbrep_parentNode);
    }

    public org.semanticwb.repository.BaseNode getParent()
    {
         org.semanticwb.repository.BaseNode ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swbrep_parentNode);
         if(obj!=null)
         {
             ret=(org.semanticwb.repository.BaseNode)obj.getSemanticClass().newGenericInstance(obj);
         }
         return ret;
    }

    public String getPath()
    {
        return getSemanticObject().getProperty(swbrep_path);
    }

    public void setPath(String path)
    {
        getSemanticObject().setProperty(swbrep_path, path);
    }

    public String getName()
    {
        return getSemanticObject().getProperty(swbrep_name);
    }

    public void setName(String name)
    {
        getSemanticObject().setProperty(swbrep_name, name);
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.repository.BaseNode> listNodes()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.repository.BaseNode>(org.semanticwb.repository.BaseNode.class, getSemanticObject().listObjectProperties(swbrep_hasNodes));
    }

    public boolean hasNode(org.semanticwb.repository.BaseNode basenode)
    {
        if(basenode==null)return false;        return getSemanticObject().hasObjectProperty(swbrep_hasNodes,basenode.getSemanticObject());
    }

    public org.semanticwb.repository.BaseNode getNode()
    {
         org.semanticwb.repository.BaseNode ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swbrep_hasNodes);
         if(obj!=null)
         {
             ret=(org.semanticwb.repository.BaseNode)obj.getSemanticClass().newGenericInstance(obj);
         }
         return ret;
    }

    public java.util.Iterator<String> listMixinTypes()
    {
        java.util.ArrayList<String> values=new java.util.ArrayList<String>();
        java.util.Iterator<org.semanticwb.platform.SemanticLiteral> it=getSemanticObject().listLiteralProperties(jcr_mixinTypes);
        while(it.hasNext())
        {
                org.semanticwb.platform.SemanticLiteral literal=it.next();
                values.add(literal.getString());
        }
        return values.iterator();
    }

    public void addMixinType(String mixintype)
    {
        getSemanticObject().setProperty(jcr_mixinTypes, mixintype);
    }

    public void removeAllMixinType()
    {
        getSemanticObject().removeProperty(jcr_mixinTypes);
    }

    public void removeMixinType(String mixintype)
    {
        getSemanticObject().removeProperty(jcr_mixinTypes,mixintype);
    }

    public void remove()
    {
        getSemanticObject().remove();
    }

    public java.util.Iterator<org.semanticwb.model.GenericObject> listRelatedObjects()
    {
        return new org.semanticwb.model.GenericIterator((org.semanticwb.platform.SemanticClass)null, getSemanticObject().listRelatedObjects(),true);
    }

    public org.semanticwb.repository.Workspace getWorkspace()
    {
        return new org.semanticwb.repository.Workspace(getSemanticObject().getModel().getModelObject());
    }
}
