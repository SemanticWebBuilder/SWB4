package org.semanticwb.portal.resources.sem.base;


public class SWBBookmarkBase extends org.semanticwb.portal.api.GenericSemResource 
{
    public static final org.semanticwb.platform.SemanticProperty swb_res_bkm_sortType=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/portal/resources/SWBBookmark#sortType");
    public static final org.semanticwb.platform.SemanticClass swb_res_bkm_BookmarkGroup=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/portal/resources/SWBBookmark#BookmarkGroup");
    public static final org.semanticwb.platform.SemanticProperty swb_res_bkm_hasBookmarkGroup=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/portal/resources/SWBBookmark#hasBookmarkGroup");
    public static final org.semanticwb.platform.SemanticClass swb_res_bkm_SWBBookmark=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/portal/resources/SWBBookmark#SWBBookmark");

    public SWBBookmarkBase()
    {
    }

    public SWBBookmarkBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/portal/resources/SWBBookmark#SWBBookmark");

    public int getSortType()
    {
        return getSemanticObject().getIntProperty(swb_res_bkm_sortType);
    }

    public void setSortType(int sortType)
    {
        getSemanticObject().setLongProperty(swb_res_bkm_sortType, sortType);
    }
}
