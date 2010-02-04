package org.semanticwb.process.model.base;

public interface PerformableBase extends org.semanticwb.model.GenericObject
{
    public static final org.semanticwb.platform.SemanticProperty swp_hasPerformer=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#hasPerformer");
    public static final org.semanticwb.platform.SemanticClass swp_Performable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#Performable");

    public java.util.Iterator<String> listPerformers();

    public void addPerformer(String performer);
    public void removeAllPerformer();
    public void removePerformer(String performer);
}
