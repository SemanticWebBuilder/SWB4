/*
 * SemanticWebBuilder es una plataforma para el desarrollo de portales y aplicaciones de integración,
 * colaboración y conocimiento, que gracias al uso de tecnología semántica puede generar contextos de
 * información alrededor de algún tema de interés o bien integrar información y aplicaciones de diferentes
 * fuentes, donde a la información se le asigna un significado, de forma que pueda ser interpretada y
 * procesada por personas y/o sistemas, es una creación original del Fondo de Información y Documentación
 * para la Industria INFOTEC, cuyo registro se encuentra actualmente en trámite.
 *
 * INFOTEC pone a su disposición la herramienta SemanticWebBuilder a través de su licenciamiento abierto al público (‘open source’),
 * en virtud del cual, usted podrá usarlo en las mismas condiciones con que INFOTEC lo ha diseñado y puesto a su disposición;
 * aprender de él; distribuirlo a terceros; acceder a su código fuente y modificarlo, y combinarlo o enlazarlo con otro software,
 * todo ello de conformidad con los términos y condiciones de la LICENCIA ABIERTA AL PÚBLICO que otorga INFOTEC para la utilización
 * del SemanticWebBuilder 4.0.
 *
 * INFOTEC no otorga garantía sobre SemanticWebBuilder, de ninguna especie y naturaleza, ni implícita ni explícita,
 * siendo usted completamente responsable de la utilización que le dé y asumiendo la totalidad de los riesgos que puedan derivar
 * de la misma.
 *
 * Si usted tiene cualquier duda o comentario sobre SemanticWebBuilder, INFOTEC pone a su disposición la siguiente
 * dirección electrónica:
 *  http://www.semanticwebbuilder.org
 */
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
