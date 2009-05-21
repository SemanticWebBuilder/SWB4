package org.semanticwb.model.catalogs;

public interface GeoTaggable extends org.semanticwb.model.GenericObject
{
    public static final org.semanticwb.platform.SemanticProperty swbc_latitud=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwb.org/swbcatalogs#latitud");
    public static final org.semanticwb.platform.SemanticProperty swbc_longitud=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwb.org/swbcatalogs#longitud");
    public static final org.semanticwb.platform.SemanticProperty swbc_altitud=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwb.org/swbcatalogs#altitud");
    public static final org.semanticwb.platform.SemanticClass swbc_GeoTaggable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwb.org/swbcatalogs#GeoTaggable");
    public String getLatitud();
    public void setLatitud(String latitud);
    public String getLongitud();
    public void setLongitud(String longitud);
    public String getAltitud();
    public void setAltitud(String altitud);
}
