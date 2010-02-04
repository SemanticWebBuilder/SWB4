package org.semanticwb.process.model;


public class Activity extends org.semanticwb.process.model.base.ActivityBase 
{
    public final static String STATUS_NONE = "none";
    public final static String STATUS_READY = "ready";
    public final static String STATUS_ACTIVE = "active";
    public final static String STATUS_CANCELLED = "cancelled";
    public final static String STATUS_ABORTING = "aborting";
    public final static String STATUS_ABORTED = "aborted";
    public final static String STATUS_COMPLETING = "completing";
    public final static String STATUS_COMPLETED = "completed";

    public final static String ACTION_ACCEPT="accept";
    public final static String ACTION_REJECT="reject";
    public final static String ACTION_CANCEL="cancel";

    public Activity(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
}
