package org.semanticwb.model;

public interface Inheritable extends org.semanticwb.model.GenericObject
{
    public static final org.semanticwb.platform.SemanticProperty swb_inherita=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#inherita");
    public static final org.semanticwb.platform.SemanticClass swb_Inheritable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Inheritable");
    public int getInherita();
    public void setInherita(int inherita);
}
