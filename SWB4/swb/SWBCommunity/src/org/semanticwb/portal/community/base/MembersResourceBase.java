package org.semanticwb.portal.community.base;


public abstract class MembersResourceBase extends org.semanticwb.portal.community.CommunityResource 
{
    public static final org.semanticwb.platform.SemanticClass swbcomm_MembersResource=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#MembersResource");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#MembersResource");

    public MembersResourceBase()
    {
    }

    public MembersResourceBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
}
