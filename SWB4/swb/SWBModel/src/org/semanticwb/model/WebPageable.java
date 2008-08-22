package org.semanticwb.model;

import org.semanticwb.model.GenericIterator;
import java.util.Date;
public interface WebPageable extends GenericObject
{

    public GenericIterator<org.semanticwb.model.WebPage> listWebPage();

    public void addWebPage(org.semanticwb.model.WebPage webpage);

    public void removeAllWebPage();

    public void removeWebPage(org.semanticwb.model.WebPage webpage);

    public WebPage getWebPage();
}
