package org.semanticwb.process.model.base;

public interface InformationAssociableBase extends org.semanticwb.model.GenericObject
{
    public static final org.semanticwb.platform.SemanticProperty swp_dataState=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#dataState");
    public static final org.semanticwb.platform.SemanticClass swp_ItemDefinition=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#ItemDefinition");
    public static final org.semanticwb.platform.SemanticProperty swp_itemSubjectRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#itemSubjectRef");
    public static final org.semanticwb.platform.SemanticClass swp_InformationAssociable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#InformationAssociable");

    public String getDataState();

    public void setDataState(String value);

    public void setItemSubjectRef(org.semanticwb.process.model.ItemDefinition itemdefinition);

    public void removeItemSubjectRef();

    public org.semanticwb.process.model.ItemDefinition getItemSubjectRef();
}
