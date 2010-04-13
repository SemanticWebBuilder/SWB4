package org.semanticwb.process.model.base;

public interface LaneableBase extends org.semanticwb.model.GenericObject
{
    public static final org.semanticwb.platform.SemanticClass swp_Lane=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#Lane");
    public static final org.semanticwb.platform.SemanticProperty swp_hasLane=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#hasLane");
    public static final org.semanticwb.platform.SemanticClass swp_Laneable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#Laneable");

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Lane> listLanes();
    public boolean hasLane(org.semanticwb.process.model.Lane value);

    public void addLane(org.semanticwb.process.model.Lane value);

    public void removeAllLane();

    public void removeLane(org.semanticwb.process.model.Lane value);

    public org.semanticwb.process.model.Lane getLane();
}
