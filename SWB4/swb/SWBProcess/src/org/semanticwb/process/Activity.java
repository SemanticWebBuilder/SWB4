package org.semanticwb.process;

public interface Activity extends org.semanticwb.process.base.ActivityBase
{
    public final static int STATUS_INIT=0;
    public final static int STATUS_PROCESSING=1;
    public final static int STATUS_STOPED=2;
    public final static int STATUS_ABORTED=3;
    public final static int STATUS_CLOSED=4;
    public final static int STATUS_OPEN=5;
}
