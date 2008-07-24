package org.semanticwb.model;

import org.semanticwb.platform.SemanticIterator;
import java.util.Date;
public interface Groupable 
{
    public SemanticIterator<org.semanticwb.model.ObjectGroup> listObjectGroup();
    public void addObjectGroup(org.semanticwb.model.ObjectGroup objectgroup);
    public void removeAllObjectGroup();
    public ObjectGroup getObjectGroup();
}
