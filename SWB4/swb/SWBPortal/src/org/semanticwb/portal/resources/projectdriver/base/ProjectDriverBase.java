package org.semanticwb.portal.resources.projectdriver.base;


public abstract class ProjectDriverBase extends org.semanticwb.portal.api.GenericSemResource 
{
    public static final org.semanticwb.platform.SemanticClass swbproy_ProjectDriver=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swbproy#ProjectDriver");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swbproy#ProjectDriver");

    public ProjectDriverBase()
    {
    }

    public ProjectDriverBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
}
