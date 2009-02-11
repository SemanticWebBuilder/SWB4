package org.semanticwb.model;

public interface Inheritable extends org.semanticwb.model.GenericObject
{
    public static final org.semanticwb.platform.SemanticProperty swb_inherit=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#inherit");
    public static final org.semanticwb.platform.SemanticClass swb_Inheritable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Inheritable");
    public int getInherit();
    public void setInherit(int inherit);
}
