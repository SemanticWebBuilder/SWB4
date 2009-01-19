package org.semanticwb.model;

public interface Portletable extends org.semanticwb.model.GenericObject
{
    public static final org.semanticwb.platform.SemanticClass swb_Portlet=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Portlet");
    public static final org.semanticwb.platform.SemanticProperty swb_hasPortlet=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#hasPortlet");
    public static final org.semanticwb.platform.SemanticClass swb_Portletable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Portletable");

    public org.semanticwb.model.GenericIterator<org.semanticwb.model.Portlet> listPortlets();
    public boolean hasPortlet(org.semanticwb.model.Portlet portlet);

    public void addPortlet(org.semanticwb.model.Portlet portlet);

    public void removeAllPortlet();

    public void removePortlet(org.semanticwb.model.Portlet portlet);

    public Portlet getPortlet();
}
