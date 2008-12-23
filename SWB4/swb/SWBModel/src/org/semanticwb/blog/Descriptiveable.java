package org.semanticwb.blog;

public interface Descriptiveable extends org.semanticwb.model.GenericObject
{
    public static final org.semanticwb.platform.SemanticProperty blog_description=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwb.org.mx/swb4/blog#description");
    public static final org.semanticwb.platform.SemanticProperty blog_name=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwb.org.mx/swb4/blog#name");
    public static final org.semanticwb.platform.SemanticClass blog_Descriptiveable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwb.org.mx/swb4/blog#Descriptiveable");
    public String getDescription();
    public void setDescription(String description);
    public String getName();
    public void setName(String name);
}
