package org.semanticwb.social.base;

   /**
   * Interface que contiene propiedades de localización que seran aplicadas a los mensajes que lleguen desde las redes sociales 
   */
public interface GeolocableBase extends org.semanticwb.model.GenericObject
{
   /**
   * Distancia del centro a buscar mensajes 
   */
    public static final org.semanticwb.platform.SemanticProperty social_geoRadio=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#geoRadio");
   /**
   * Longitud del punto central a buscar mensajes 
   */
    public static final org.semanticwb.platform.SemanticProperty social_geoCenterLongitude=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#geoCenterLongitude");
   /**
   * Latitud del punto central a buscar mensajes 
   */
    public static final org.semanticwb.platform.SemanticProperty social_geoCenterLatitude=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#geoCenterLatitude");
   /**
   * Unidad de medida para la distancia entre el centro y el radio. Ej. KM, MI 
   */
    public static final org.semanticwb.platform.SemanticProperty social_geoDistanceUnit=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#geoDistanceUnit");
   /**
   * Interface que contiene propiedades de localización que seran aplicadas a los mensajes que lleguen desde las redes sociales 
   */
    public static final org.semanticwb.platform.SemanticClass social_Geolocable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#Geolocable");

    public float getGeoRadio();

    public void setGeoRadio(float value);

    public float getGeoCenterLongitude();

    public void setGeoCenterLongitude(float value);

    public float getGeoCenterLatitude();

    public void setGeoCenterLatitude(float value);

    public String getGeoDistanceUnit();

    public void setGeoDistanceUnit(String value);
}
