package org.semanticwb.portal.community;

import org.semanticwb.model.WebPage;

public class DocumentElement extends org.semanticwb.portal.community.base.DocumentElementBase 
{
    public DocumentElement(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    @Override
    public WebPage getWebPage()
    {
        return super.getDocumentWebPage();
    }
}
