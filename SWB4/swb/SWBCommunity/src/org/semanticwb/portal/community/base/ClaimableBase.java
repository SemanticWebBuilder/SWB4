package org.semanticwb.portal.community.base;

public interface ClaimableBase extends org.semanticwb.model.GenericObject
{
    public static final org.semanticwb.platform.SemanticProperty swbcomm_claimable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#claimable");
    public static final org.semanticwb.platform.SemanticProperty swbcomm_claimJustify=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#claimJustify");
    public static final org.semanticwb.platform.SemanticClass swb_User=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#User");
    public static final org.semanticwb.platform.SemanticProperty swbcomm_claimer=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#claimer");
    public static final org.semanticwb.platform.SemanticClass swbcomm_Claimable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#Claimable");
    public boolean isClaimable();
    public void setClaimable(boolean claimable);
    public String getClaimJustify();
    public void setClaimJustify(String claimJustify);

    public void setClaimer(org.semanticwb.model.User user);

    public void removeClaimer();

    public org.semanticwb.model.User getClaimer();
}
