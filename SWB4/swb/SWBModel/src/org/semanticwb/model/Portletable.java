package org.semanticwb.model;

import org.semanticwb.platform.SemanticIterator;
import java.util.Date;
public interface Portletable 
{

    public SemanticIterator<org.semanticwb.model.Portlet> listPortlet();

    public void addPortlet(org.semanticwb.model.Portlet portlet);

    public void removeAllPortlet();

    public void removePortlet(org.semanticwb.model.Portlet portlet);

    public Portlet getPortlet();
}
