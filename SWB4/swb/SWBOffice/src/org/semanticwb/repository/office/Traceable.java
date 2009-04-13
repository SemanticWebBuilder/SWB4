package org.semanticwb.repository.office;

public interface Traceable extends org.semanticwb.model.GenericObject
{
    public static final org.semanticwb.platform.SemanticProperty office_user=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/office#user");
    public static final org.semanticwb.platform.SemanticClass office_Traceable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/office#Traceable");
    public String getUser();
    public void setUser(String user);
}
