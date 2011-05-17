package org.semanticwb.remotetriplestore.protocol;

import java.io.Serializable;

/**
 *
 * @author serch
 */
public class SWBRTSCmd implements Serializable
{
    static final long serialVersionUID = 8001L;
    public Command cmd;
    public int paramNumber;
}
