package org.semanticwb.model;

import org.semanticwb.model.GenericIterator;
import java.util.Date;
public interface WebPageable extends GenericObject
{

    public void setWebPage(org.semanticwb.model.WebPage webpage);

    public void removeWebPage();

    public WebPage getWebPage();
}
