package org.semanticwb.bsc.accessory;

import org.semanticwb.SWBPortal;


public class State extends org.semanticwb.bsc.accessory.base.StateBase 
{
    public State(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    @Override
    public String getIcon() {
        return SWBPortal.getWebWorkPath()+this.getWorkPath()+"/"+getIcon();
    }
}
