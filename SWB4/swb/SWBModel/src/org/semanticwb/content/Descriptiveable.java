package org.semanticwb.content;

public interface Descriptiveable extends org.semanticwb.model.GenericObject
{
    public static final org.semanticwb.platform.SemanticProperty cm_user=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwb.org.mx/swb4/content#user");
    public static final org.semanticwb.platform.SemanticProperty cm_title=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwb.org.mx/swb4/content#title");
    public static final org.semanticwb.platform.SemanticProperty cm_description=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwb.org.mx/swb4/content#description");
    public static final org.semanticwb.platform.SemanticClass cm_Descriptiveable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwb.org.mx/swb4/content#Descriptiveable");
    public String getUser();
    public void setUser(String user);
    public String getTitle();
    public void setTitle(String title);
    public String getDescription();
    public void setDescription(String description);
}
