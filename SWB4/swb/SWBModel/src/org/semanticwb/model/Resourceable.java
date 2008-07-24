package org.semanticwb.model;

import org.semanticwb.platform.SemanticIterator;
import java.util.Date;
public interface Resourceable 
{
    public SemanticIterator<org.semanticwb.model.Resource> listResource();
    public void addResource(org.semanticwb.model.Resource resource);
    public void removeAllResource();
    public Resource getResource();
}
