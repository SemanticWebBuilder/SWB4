package org.semanticwb.model;

public interface Mapable extends org.semanticwb.model.GenericObject
{
    public static final org.semanticwb.platform.SemanticProperty swb_longitude=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#longitude");
    public static final org.semanticwb.platform.SemanticProperty swb_latitude=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#latitude");
    public static final org.semanticwb.platform.SemanticClass swb_Mapable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Mapable");
    public double getLongitude();
    public void setLongitude(double longitude);
    public double getLatitude();
    public void setLatitude(double latitude);
}
