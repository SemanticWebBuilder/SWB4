package org.semanticwb.process.model;


public class Instance extends org.semanticwb.process.model.base.InstanceBase 
{
    public final static int STATUS_INIT=0;
    public final static int STATUS_PROCESSING=1;
    public final static int STATUS_STOPED=2;
    public final static int STATUS_ABORTED=3;
    public final static int STATUS_CLOSED=4;
    public final static int STATUS_OPEN=5;

    public final static String ACTION_ACCEPT="accept";
    public final static String ACTION_REJECT="reject";
    public final static String ACTION_CANCEL="cancel";

    public Instance(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
}
