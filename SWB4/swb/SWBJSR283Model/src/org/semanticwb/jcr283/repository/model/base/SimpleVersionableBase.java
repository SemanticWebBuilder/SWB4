package org.semanticwb.jcr283.repository.model.base;

public interface SimpleVersionableBase extends org.semanticwb.jcr283.repository.model.Referenceable
{
    public static final org.semanticwb.platform.SemanticClass xsd_Boolean=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.w3.org/2001/XMLSchema#boolean");
    public static final org.semanticwb.platform.SemanticProperty jcr_isCheckedOut=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.jcp.org/jcr/1.0#isCheckedOut");
    public static final org.semanticwb.platform.SemanticClass mix_SimpleVersionable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.jcp.org/jcr/mix/1.0#simpleVersionable");
    public boolean isIsCheckedOut();
    public void setIsCheckedOut(boolean isCheckedOut);
}
