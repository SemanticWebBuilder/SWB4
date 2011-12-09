package org.semanticwb.remotetriplestore.protocol;

/**
 *
 * @author serch
 */
public class Command 
{
    public static final String LIST_MODEL_NAMES="LIST_MODEL_NAMES";
    public static final String GET_MODEL="GET_MODEL";
    public static final String CREATE_MODEL="CREATE_MODEL";
    public static final String REMOVE_MODEL="REMOVE_MODEL";
    public static final String GRAPH_BASE_FIND="GRAPH_BASE_FIND";
    public static final String GET_NS_PREFIX_MAP="GET_NS_PREFIX_MAP";
    public static final String GET_NS_PREFIX_URI="GET_NS_PREFIX_URI";
    public static final String SET_NS_PREFIX="SET_NS_PREFIX";
    public static final String REMOVE_NS_PREFIX="REMOVE_NS_PREFIX";
    public static final String GRAPH_ADD="GRAPH_ADD";
    public static final String GRAPH_REMOVE="GRAPH_REMOVE";
    public static final String TRANS_BEGIN="TRANS_BEGIN";
    public static final String TRANS_COMMINT="TRANS_COMMINT";
    public static final String TRANS_ABORT="TRANS_ABORT";
    
    public static final String OOK="OOK";
    public static final String ERROR="ERROR";
    public static final String NULL="[_NULL_]";    
    public static final String CLOSE="[_CLOSE_]";
    
}
