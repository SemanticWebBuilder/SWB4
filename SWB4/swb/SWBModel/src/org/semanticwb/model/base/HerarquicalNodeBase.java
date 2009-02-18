package org.semanticwb.model.base;


public class HerarquicalNodeBase extends org.semanticwb.model.SWBClass implements org.semanticwb.model.Sortable,org.semanticwb.model.Iconable,org.semanticwb.model.Descriptiveable
{
    public static final org.semanticwb.platform.SemanticProperty swb_index=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#index");
    public static final org.semanticwb.platform.SemanticProperty swb_iconClass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#iconClass");
    public static final org.semanticwb.platform.SemanticProperty swb_title=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#title");
    public static final org.semanticwb.platform.SemanticClass swb_Class=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Class");
    public static final org.semanticwb.platform.SemanticProperty swbxf_heClass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/xforms/ontology#heClass");
    public static final org.semanticwb.platform.SemanticClass swb_SWBModel=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#SWBModel");
    public static final org.semanticwb.platform.SemanticProperty swbxf_heModel=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/xforms/ontology#heModel");
    public static final org.semanticwb.platform.SemanticProperty swb_description=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#description");
    public static final org.semanticwb.platform.SemanticClass swbxf_HerarquicalNode=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#HerarquicalNode");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#HerarquicalNode");

    public HerarquicalNodeBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public static org.semanticwb.model.HerarquicalNode getHerarquicalNode(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.model.HerarquicalNode)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
    }

    public static java.util.Iterator<org.semanticwb.model.HerarquicalNode> listHerarquicalNodes(org.semanticwb.model.SWBModel model)
    {
        java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.HerarquicalNode>(org.semanticwb.model.HerarquicalNode.class, it, true);
    }

    public static java.util.Iterator<org.semanticwb.model.HerarquicalNode> listHerarquicalNodes()
    {
        java.util.Iterator it=sclass.listInstances();
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.HerarquicalNode>(org.semanticwb.model.HerarquicalNode.class, it, true);
    }

    public static org.semanticwb.model.HerarquicalNode createHerarquicalNode(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.model.HerarquicalNode)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
    }

    public static void removeHerarquicalNode(String id, org.semanticwb.model.SWBModel model)
    {
        model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
    }

    public static boolean hasHerarquicalNode(String id, org.semanticwb.model.SWBModel model)
    {
        return (getHerarquicalNode(id, model)!=null);
    }

    public int getIndex()
    {
        return getSemanticObject().getIntProperty(swb_index);
    }

    public void setIndex(int index)
    {
        getSemanticObject().setLongProperty(swb_index, index);
    }

    public String getIconClass()
    {
        return getSemanticObject().getProperty(swb_iconClass);
    }

    public void setIconClass(String iconClass)
    {
        getSemanticObject().setProperty(swb_iconClass, iconClass);
    }

    public String getTitle()
    {
        return getSemanticObject().getProperty(swb_title);
    }

    public void setTitle(String title)
    {
        getSemanticObject().setProperty(swb_title, title);
    }

    public String getTitle(String lang)
    {
        return getSemanticObject().getProperty(swb_title, null, lang);
    }

    public String getDisplayTitle(String lang)
    {
        return getSemanticObject().getLocaleProperty(swb_title, lang);
    }

    public void setTitle(String title, String lang)
    {
        getSemanticObject().setProperty(swb_title, title, lang);
    }

    public void setHClass(org.semanticwb.platform.SemanticObject semanticobject)
    {
        getSemanticObject().setObjectProperty(swbxf_heClass, semanticobject);
    }

    public void removeHClass()
    {
        getSemanticObject().removeProperty(swbxf_heClass);
    }

    public org.semanticwb.platform.SemanticObject getHClass()
    {
         org.semanticwb.platform.SemanticObject ret=null;
         ret=getSemanticObject().getObjectProperty(swbxf_heClass);
         return ret;
    }

    public void setModel(org.semanticwb.model.SWBModel swbmodel)
    {
        getSemanticObject().setObjectProperty(swbxf_heModel, swbmodel.getSemanticObject());
    }

    public void removeModel()
    {
        getSemanticObject().removeProperty(swbxf_heModel);
    }

    public org.semanticwb.model.SWBModel getModel()
    {
         org.semanticwb.model.SWBModel ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swbxf_heModel);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.SWBModel)obj.getSemanticClass().newGenericInstance(obj);
         }
         return ret;
    }

    public String getDescription()
    {
        return getSemanticObject().getProperty(swb_description);
    }

    public void setDescription(String description)
    {
        getSemanticObject().setProperty(swb_description, description);
    }

    public String getDescription(String lang)
    {
        return getSemanticObject().getProperty(swb_description, null, lang);
    }

    public String getDisplayDescription(String lang)
    {
        return getSemanticObject().getLocaleProperty(swb_description, lang);
    }

    public void setDescription(String description, String lang)
    {
        getSemanticObject().setProperty(swb_description, description, lang);
    }
}
