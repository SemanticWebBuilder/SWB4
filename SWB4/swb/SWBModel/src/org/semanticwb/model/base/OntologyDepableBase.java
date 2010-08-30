package org.semanticwb.model.base;

public interface OntologyDepableBase extends org.semanticwb.model.GenericObject
{
    public static final org.semanticwb.platform.SemanticClass swb_Ontology=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Ontology");
    public static final org.semanticwb.platform.SemanticProperty swb_hasOntology=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#hasOntology");
    public static final org.semanticwb.platform.SemanticClass swb_OntologyDepable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#OntologyDepable");

    public org.semanticwb.model.GenericIterator<org.semanticwb.model.Ontology> listOntologies();
    public boolean hasOntology(org.semanticwb.model.Ontology value);

   /**
   * Adds the Ontology
   * @param value An instance of org.semanticwb.model.Ontology
   */
    public void addOntology(org.semanticwb.model.Ontology value);

   /**
   * Remove all the values for the property Ontology
   */
    public void removeAllOntology();

   /**
   * Remove a value from the property Ontology
   * @param value An instance of org.semanticwb.model.Ontology
   */
    public void removeOntology(org.semanticwb.model.Ontology value);

/**
* Gets the Ontology
* @return a instance of org.semanticwb.model.Ontology
*/
    public org.semanticwb.model.Ontology getOntology();
}
