package org.semanticwb.model;

public interface PortletRefable extends org.semanticwb.model.GenericObject
{
    public static final org.semanticwb.platform.SemanticClass swb_PortletRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#PortletRef");
    public static final org.semanticwb.platform.SemanticProperty swb_hasPortletRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#hasPortletRef");
    public static final org.semanticwb.platform.SemanticClass swb_PortletRefable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#PortletRefable");

    public org.semanticwb.model.GenericIterator<org.semanticwb.model.PortletRef> listPortletRefs();
    public boolean hasPortletRef(org.semanticwb.model.PortletRef portletref);

    public void addPortletRef(org.semanticwb.model.PortletRef portletref);

    public void removeAllPortletRef();

    public void removePortletRef(org.semanticwb.model.PortletRef portletref);

    public PortletRef getPortletRef();
}
