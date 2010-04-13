package org.semanticwb.process.model.base;

public interface LaneCompartmentableBase extends org.semanticwb.model.GenericObject
{
    public static final org.semanticwb.platform.SemanticClass swp_LaneCompartment=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#LaneCompartment");
    public static final org.semanticwb.platform.SemanticProperty swp_hasLaneCompartment=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#hasLaneCompartment");
    public static final org.semanticwb.platform.SemanticClass swp_LaneCompartmentable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#LaneCompartmentable");

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.LaneCompartment> listLaneCompartments();
    public boolean hasLaneCompartment(org.semanticwb.process.model.LaneCompartment value);

    public void addLaneCompartment(org.semanticwb.process.model.LaneCompartment value);

    public void removeAllLaneCompartment();

    public void removeLaneCompartment(org.semanticwb.process.model.LaneCompartment value);

    public org.semanticwb.process.model.LaneCompartment getLaneCompartment();
}
