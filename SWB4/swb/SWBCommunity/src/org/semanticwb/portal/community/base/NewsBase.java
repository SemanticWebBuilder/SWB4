package org.semanticwb.portal.community.base;


public class NewsBase extends org.semanticwb.portal.community.CommunityResource 
{
    public static final org.semanticwb.platform.SemanticClass swbcomm_News=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#News");

    public NewsBase()
    {
    }

    public NewsBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#News");
}
