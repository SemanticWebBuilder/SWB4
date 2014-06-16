package org.semanticwb.bsc;

import org.semanticwb.bsc.accessory.Period;

public interface Detailed extends org.semanticwb.bsc.base.DetailedBase
{
    public String getIconClass();
    public String getIconClass(Period period);
}
