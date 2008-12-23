package org.semanticwb.model;

public interface Hiddenable extends org.semanticwb.model.GenericObject
{
    public static final org.semanticwb.platform.SemanticProperty swb_hidden=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#hidden");
    public static final org.semanticwb.platform.SemanticClass swb_Hiddenable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Hiddenable");
    public boolean isHidden();
    public void setHidden(boolean hidden);
}
