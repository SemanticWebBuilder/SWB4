package org.semanticwb.bsc.base;

   /**
   * Interfaz que define las relaciones causa - efecto entre Objetivos y Temas. Se utiliza para mostrarse en el Mapa Estrategico 
   */
public interface CausalBase extends org.semanticwb.model.GenericObject
{
   /**
   * Los temas estratégicos agrupan objetivos con fines en común. A su vez, los temas están agrupados dentro de las perspectivas. 
   */
    public static final org.semanticwb.platform.SemanticClass bsc_Theme=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#Theme");
   /**
   * Persiste información de las relaciones causa efecto que existe con otros Objetivos 
   */
    public static final org.semanticwb.platform.SemanticProperty bsc_hasCausalTheme=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#hasCausalTheme");
    public static final org.semanticwb.platform.SemanticClass bsc_Objective=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#Objective");
   /**
   * Persiste información de las relaciones causa efecto que existe con otros Objetivos 
   */
    public static final org.semanticwb.platform.SemanticProperty bsc_hasCausalObjective=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#hasCausalObjective");
   /**
   * Interfaz que define las relaciones causa - efecto entre Objetivos y Temas. Se utiliza para mostrarse en el Mapa Estrategico 
   */
    public static final org.semanticwb.platform.SemanticClass bsc_Causal=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#Causal");

    public org.semanticwb.model.GenericIterator<org.semanticwb.bsc.Theme> listCausalThemes();
    public boolean hasCausalTheme(org.semanticwb.bsc.Theme value);

   /**
   * Adds the CausalTheme
   * @param value An instance of org.semanticwb.bsc.Theme
   */
    public void addCausalTheme(org.semanticwb.bsc.Theme value);

   /**
   * Remove all the values for the property CausalTheme
   */
    public void removeAllCausalTheme();

   /**
   * Remove a value from the property CausalTheme
   * @param value An instance of org.semanticwb.bsc.Theme
   */
    public void removeCausalTheme(org.semanticwb.bsc.Theme value);

/**
* Gets the CausalTheme
* @return a instance of org.semanticwb.bsc.Theme
*/
    public org.semanticwb.bsc.Theme getCausalTheme();

    public org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Objective> listCausalObjectives();
    public boolean hasCausalObjective(org.semanticwb.bsc.element.Objective value);

   /**
   * Adds the CausalObjective
   * @param value An instance of org.semanticwb.bsc.element.Objective
   */
    public void addCausalObjective(org.semanticwb.bsc.element.Objective value);

   /**
   * Remove all the values for the property CausalObjective
   */
    public void removeAllCausalObjective();

   /**
   * Remove a value from the property CausalObjective
   * @param value An instance of org.semanticwb.bsc.element.Objective
   */
    public void removeCausalObjective(org.semanticwb.bsc.element.Objective value);

/**
* Gets the CausalObjective
* @return a instance of org.semanticwb.bsc.element.Objective
*/
    public org.semanticwb.bsc.element.Objective getCausalObjective();
}
