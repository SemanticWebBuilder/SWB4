package org.semanticwb.model;

import org.semanticwb.platform.SemanticIterator;
import java.util.Date;
public interface ResourceRefable 
{
    public SemanticIterator<org.semanticwb.model.ResourceRef> listResourceRef();
    public void addResourceRef(org.semanticwb.model.ResourceRef resourceref);
    public void removeAllResourceRef();
    public ResourceRef getResourceRef();
}
