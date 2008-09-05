package org.semanticwb.model;

import org.semanticwb.model.GenericIterator;
import java.util.Date;
public interface Portletable extends GenericObject
{

    public GenericIterator<org.semanticwb.model.Portlet> listPortlets();

    public void addPortlet(org.semanticwb.model.Portlet portlet);

    public void removeAllPortlet();

    public void removePortlet(org.semanticwb.model.Portlet portlet);

    public Portlet getPortlet();
}
