package org.semanticwb.process.model.base;

public interface DiagramLinkableBase extends org.semanticwb.model.GenericObject
{
    public static final org.semanticwb.platform.SemanticProperty swp_diagramLink=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#diagramLink");
    public static final org.semanticwb.platform.SemanticClass swp_DiagramLinkable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#DiagramLinkable");

    public String getDiagramLink();

    public void setDiagramLink(String value);
}
