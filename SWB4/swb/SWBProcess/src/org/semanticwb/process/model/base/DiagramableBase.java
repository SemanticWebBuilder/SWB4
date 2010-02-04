package org.semanticwb.process.model.base;

public interface DiagramableBase extends org.semanticwb.model.GenericObject
{
    public static final org.semanticwb.platform.SemanticClass swp_GraphicalElement=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#GraphicalElement");
    public static final org.semanticwb.platform.SemanticProperty swp_hasGraphicalElement=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#hasGraphicalElement");
    public static final org.semanticwb.platform.SemanticClass swp_Diagramable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#Diagramable");

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.GraphicalElement> listGraphicalElements();
    public boolean hasGraphicalElement(org.semanticwb.process.model.GraphicalElement graphicalelement);

    public void addGraphicalElement(org.semanticwb.process.model.GraphicalElement graphicalelement);

    public void removeAllGraphicalElement();

    public void removeGraphicalElement(org.semanticwb.process.model.GraphicalElement graphicalelement);

    public org.semanticwb.process.model.GraphicalElement getGraphicalElement();
}
