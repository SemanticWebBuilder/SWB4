package org.semanticwb.portal.resources.sem.base;


public class SWBStaticTextBase extends org.semanticwb.portal.api.GenericSemResource 
{
    public static final org.semanticwb.platform.SemanticProperty swbstatictext_text=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/portal/resources/StaticText#text");
    public static final org.semanticwb.platform.SemanticClass swbstatictext_SWBStaticText=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/portal/resources/StaticText#SWBStaticText");

    public SWBStaticTextBase()
    {
    }

    public SWBStaticTextBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/portal/resources/StaticText#SWBStaticText");

    public String getText()
    {
        return getSemanticObject().getProperty(swbstatictext_text);
    }

    public void setText(String text)
    {
        getSemanticObject().setProperty(swbstatictext_text, text);
    }
}
