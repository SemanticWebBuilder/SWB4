package org.semanticwb.model.base;

   /**
   * Interfaz que define propiedades para elementos que pueden ser ubicados en un mapa 
   */
public interface GeolocalizableBase extends org.semanticwb.model.GenericObject
{
    public static final org.semanticwb.platform.SemanticProperty swb_longitude=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#longitude");
    public static final org.semanticwb.platform.SemanticProperty swb_geoStep=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#geoStep");
    public static final org.semanticwb.platform.SemanticProperty swb_latitude=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#latitude");
   /**
   * Interfaz que define propiedades para elementos que pueden ser ubicados en un mapa 
   */
    public static final org.semanticwb.platform.SemanticClass swb_Geolocalizable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Geolocalizable");

    public double getLongitude();

    public void setLongitude(double value);

    public int getStep();

    public void setStep(int value);

    public double getLatitude();

    public void setLatitude(double value);
}
