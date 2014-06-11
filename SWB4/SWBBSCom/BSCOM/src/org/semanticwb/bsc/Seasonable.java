package org.semanticwb.bsc;

import java.util.Iterator;
import org.semanticwb.bsc.accessory.Period;

public interface Seasonable extends org.semanticwb.bsc.base.SeasonableBase
{
    public BSC getBSC();
    public Iterator<Period> listAvailablePeriods(boolean ascending);
    public Iterator<Period> listAvailablePeriods();
}
