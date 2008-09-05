package org.semanticwb.model;

import org.semanticwb.model.GenericIterator;
import java.util.Date;
public interface Groupable extends GenericObject
{

    public GenericIterator<org.semanticwb.model.ObjectGroup> listGroups();

    public void addGroup(org.semanticwb.model.ObjectGroup objectgroup);

    public void removeAllGroup();

    public void removeGroup(org.semanticwb.model.ObjectGroup objectgroup);

    public ObjectGroup getGroup();
}
