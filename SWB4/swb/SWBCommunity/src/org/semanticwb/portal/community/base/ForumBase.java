package org.semanticwb.portal.community.base;


public class ForumBase extends org.semanticwb.portal.community.CommunityResource 
{
    public static final org.semanticwb.platform.SemanticClass swbcomm_Forum=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#Forum");

    public ForumBase()
    {
    }

    public ForumBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#Forum");
}
