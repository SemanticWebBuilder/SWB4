package org.semanticwb.model;

import org.semanticwb.model.GenericIterator;
import java.util.Date;
public interface PortletRefable extends GenericObject
{

    public GenericIterator<org.semanticwb.model.PortletRef> listPortletRefs();

    public void addPortletRef(org.semanticwb.model.PortletRef portletref);

    public void removeAllPortletRef();

    public void removePortletRef(org.semanticwb.model.PortletRef portletref);

    public PortletRef getPortletRef();
}
