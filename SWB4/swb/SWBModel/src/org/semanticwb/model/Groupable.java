package org.semanticwb.model;

import org.semanticwb.platform.SemanticIterator;
import java.util.Date;
public interface Groupable 
{

    public SemanticIterator<org.semanticwb.model.ObjectGroup> listGroup();

    public void addGroup(org.semanticwb.model.ObjectGroup objectgroup);

    public void removeAllGroup();

    public void removeGroup(org.semanticwb.model.ObjectGroup objectgroup);

    public ObjectGroup getGroup();
}
