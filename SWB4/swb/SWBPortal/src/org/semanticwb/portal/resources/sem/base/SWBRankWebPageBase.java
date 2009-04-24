package org.semanticwb.portal.resources.sem.base;


public class SWBRankWebPageBase extends org.semanticwb.portal.api.GenericSemResource 
{
    public static final org.semanticwb.platform.SemanticProperty rankwebpage_fullStar=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/portal/resources/SWBRankWebPage#fullStar");
    public static final org.semanticwb.platform.SemanticProperty rankwebpage_emptyStar=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/portal/resources/SWBRankWebPage#emptyStar");
    public static final org.semanticwb.platform.SemanticProperty rankwebpage_halfStar=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/portal/resources/SWBRankWebPage#halfStar");
    public static final org.semanticwb.platform.SemanticClass rankwebpage_SWBRankWebPage=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/portal/resources/SWBRankWebPage#SWBRankWebPage");

    public SWBRankWebPageBase()
    {
    }

    public SWBRankWebPageBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/portal/resources/SWBRankWebPage#SWBRankWebPage");

    public String getFullStar()
    {
        return getSemanticObject().getProperty(rankwebpage_fullStar);
    }

    public void setFullStar(String fullStar)
    {
        getSemanticObject().setProperty(rankwebpage_fullStar, fullStar);
    }

    public String getEmptyStar()
    {
        return getSemanticObject().getProperty(rankwebpage_emptyStar);
    }

    public void setEmptyStar(String emptyStar)
    {
        getSemanticObject().setProperty(rankwebpage_emptyStar, emptyStar);
    }

    public String getHalfStar()
    {
        return getSemanticObject().getProperty(rankwebpage_halfStar);
    }

    public void setHalfStar(String halfStar)
    {
        getSemanticObject().setProperty(rankwebpage_halfStar, halfStar);
    }
}
