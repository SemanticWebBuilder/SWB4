package org.semanticwb.remotetriplestore.protocol;

/**
 *
 * @author serch
 */
public enum Command {
    LIST_MODEL_NAMES,
    GET_MODEL,
    CREATE_MODEL,
    REMOVE_MODEL,
    GRAPH_BASE_FIND,
    GET_NS_PREFIX_MAP,
    GET_NS_PREFIX_URI,
    SET_NS_PREFIX,
    REMOVE_NS_PREFIX,
    GRAPH_ADD,
    GRAPH_REMOVE,
    TRANS_BEGIN,
    TRANS_COMMINT,
    TRANS_ABORT
}
