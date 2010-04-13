package org.semanticwb.process.model.base;

public interface CollapsableBase extends org.semanticwb.model.GenericObject
{
    public static final org.semanticwb.platform.SemanticProperty swp_collapsed=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#collapsed");
    public static final org.semanticwb.platform.SemanticClass swp_Collapsable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#Collapsable");

    public boolean isCollapsed();

    public void setCollapsed(boolean value);
}
