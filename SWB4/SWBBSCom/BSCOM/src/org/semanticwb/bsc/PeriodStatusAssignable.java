package org.semanticwb.bsc;

import org.semanticwb.bsc.accessory.Period;
import org.semanticwb.bsc.accessory.State;
import org.semanticwb.bsc.tracing.PeriodStatus;

   /**
   * Cualquier elemento BSC al que se le puedan agregar mediciones 
   */
public interface PeriodStatusAssignable extends org.semanticwb.bsc.base.PeriodStatusAssignableBase
{
    public State getMinimumState();
    public State getMaximumState();
    public PeriodStatus getPeriodStatus(Period period);
}
