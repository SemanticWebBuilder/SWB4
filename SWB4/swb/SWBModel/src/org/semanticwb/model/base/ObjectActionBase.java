package org.semanticwb.model.base;


public class ObjectActionBase extends org.semanticwb.model.WebPage implements org.semanticwb.model.Sortable,org.semanticwb.model.Iconable
{
    public static final org.semanticwb.platform.SemanticProperty swb_index=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#index");
    public static final org.semanticwb.platform.SemanticProperty swb_iconClass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#iconClass");
    public static final org.semanticwb.platform.SemanticProperty swbxf_actGroup=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/xforms/ontology#actGroup");
    public static final org.semanticwb.platform.SemanticProperty swbxf_actionURL=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/xforms/ontology#actionURL");
    public static final org.semanticwb.platform.SemanticClass swbxf_ObjectAction=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#ObjectAction");

    public ObjectActionBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
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

    public String getActGroup()
    {
        return getSemanticObject().getProperty(swbxf_actGroup);
    }

    public void setActGroup(String actGroup)
    {
        getSemanticObject().setProperty(swbxf_actGroup, actGroup);
    }

    public String getActionURL()
    {
        return getSemanticObject().getProperty(swbxf_actionURL);
    }

    public void setActionURL(String actionURL)
    {
        getSemanticObject().setProperty(swbxf_actionURL, actionURL);
    }
}
