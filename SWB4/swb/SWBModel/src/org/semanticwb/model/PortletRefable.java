package org.semanticwb.model;

import org.semanticwb.platform.SemanticIterator;
import java.util.Date;
public interface PortletRefable 
{

    public SemanticIterator<org.semanticwb.model.PortletRef> listPortletRef();

    public void addPortletRef(org.semanticwb.model.PortletRef portletref);

    public void removeAllPortletRef();

    public void removePortletRef(org.semanticwb.model.PortletRef portletref);

    public PortletRef getPortletRef();
}
