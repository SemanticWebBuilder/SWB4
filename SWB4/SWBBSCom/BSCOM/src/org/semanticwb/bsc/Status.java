package org.semanticwb.bsc;

import java.util.List;
import org.semanticwb.bsc.accessory.State;

public interface Status extends org.semanticwb.bsc.base.StatusBase
{
    public List<State> listValidStates();
}
