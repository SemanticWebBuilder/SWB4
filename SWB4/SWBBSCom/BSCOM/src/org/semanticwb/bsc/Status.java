package org.semanticwb.bsc;

import java.util.List;
import org.semanticwb.bsc.accessory.State;

public interface Status extends org.semanticwb.bsc.base.StatusBase
{
    public State getMinimumState();
    public State getMaximumState();
    public List<State> sortStates();
    public List<State> sortStates(boolean ascendent);
    public List<State> listValidStates();
}
