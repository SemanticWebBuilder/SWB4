package org.semanticwb.process.model.base;

public interface ChoreographyCompartmentableBase extends org.semanticwb.model.GenericObject
{
    public static final org.semanticwb.platform.SemanticClass swp_ChoreographyCompartment=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#ChoreographyCompartment");
    public static final org.semanticwb.platform.SemanticProperty swp_choreographyCompartment=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#choreographyCompartment");
    public static final org.semanticwb.platform.SemanticClass swp_ChoreographyCompartmentable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#ChoreographyCompartmentable");

    public void setChoreographyCompartment(org.semanticwb.process.model.ChoreographyCompartment choreographycompartment);

    public void removeChoreographyCompartment();

    public org.semanticwb.process.model.ChoreographyCompartment getChoreographyCompartment();
}
