package org.semanticwb.portlet.office.base;


public class WordPortletBase extends org.semanticwb.portlet.office.OfficePortlet 
{
    public static final org.semanticwb.platform.SemanticProperty word_paginated=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/content/office/word#paginated");
    public static final org.semanticwb.platform.SemanticProperty word_NumberOfPages=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/content/office/word#NumberOfPages");
    public static final org.semanticwb.platform.SemanticClass swbrep_WordPortlet=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/repository#WordPortlet");

    public WordPortletBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public boolean isPaginated()
    {
        return getSemanticObject().getBooleanProperty(word_paginated);
    }

    public void setPaginated(boolean paginated)
    {
        getSemanticObject().setBooleanProperty(word_paginated, paginated);
    }

    public int getNumberOfPages()
    {
        return getSemanticObject().getIntProperty(word_NumberOfPages);
    }

    public void setNumberOfPages(int NumberOfPages)
    {
        getSemanticObject().setLongProperty(word_NumberOfPages, NumberOfPages);
    }
}
