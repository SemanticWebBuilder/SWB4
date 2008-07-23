package org.semanticwb.model;

import org.semanticwb.platform.SemanticIterator;
import java.util.Date;
public interface WebPageable 
{
    public SemanticIterator<org.semanticwb.model.WebPage> listWebPage();
    public void addWebPage(org.semanticwb.model.WebPage webpage);
    public void removeAllWebPage();
    public WebPage getWebPage();
}
