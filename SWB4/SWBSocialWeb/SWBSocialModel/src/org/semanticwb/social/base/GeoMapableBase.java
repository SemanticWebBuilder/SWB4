package org.semanticwb.social.base;

   /**
   * Interface que engloba elementos para poder mapear puntos en un mapa mediante un Bounding Box 
   */
public interface GeoMapableBase extends org.semanticwb.model.GenericObject
{
   /**
   * Latitud de la capital 
   */
    public static final org.semanticwb.platform.SemanticProperty social_capitalLatitude=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#capitalLatitude");
   /**
   * Punto oeste, del punto South West (SW) de un bounding box. 
   */
    public static final org.semanticwb.platform.SemanticProperty social_west=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#west");
   /**
   * Punto norte, del punto North East (NE) de un bounding box. 
   */
    public static final org.semanticwb.platform.SemanticProperty social_north=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#north");
   /**
   * Punto Este, del punto North East (NE) de un bounding box. 
   */
    public static final org.semanticwb.platform.SemanticProperty social_east=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#east");
    public static final org.semanticwb.platform.SemanticProperty social_capitalLongitude=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#capitalLongitude");
   /**
   * Punto sur, del punto South West (SW) de un bounding box. 
   */
    public static final org.semanticwb.platform.SemanticProperty social_south=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#south");
   /**
   * Interface que engloba elementos para poder mapear puntos en un mapa mediante un Bounding Box 
   */
    public static final org.semanticwb.platform.SemanticClass social_GeoMapable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#GeoMapable");

    public float getCapitalLatitude();

    public void setCapitalLatitude(float value);

    public float getWest();

    public void setWest(float value);

    public float getNorth();

    public void setNorth(float value);

    public float getEast();

    public void setEast(float value);

    public float getCapitalLongitude();

    public void setCapitalLongitude(float value);

    public float getSouth();

    public void setSouth(float value);
}
