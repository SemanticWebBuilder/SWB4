package org.semanticwb.model;

import org.semanticwb.model.GenericIterator;
import java.util.Date;
public interface Viewable extends GenericObject
{
    public int getViews();
    public void setViews(int views);
    public int getHits();
    public void setHits(int hits);
}
