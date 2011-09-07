package org.semanticwb.remotetriplestore.protocol;

import java.io.Serializable;

/**
 *
 * @author serch
 */
public class TripleString implements Serializable{
    static final long serialVersionUID = 8002L;
    public String subj;
    public String prop;
    public String obj;
}
