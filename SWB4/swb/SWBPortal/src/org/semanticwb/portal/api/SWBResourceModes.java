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
package org.semanticwb.portal.api;

// TODO: Auto-generated Javadoc
/**
 * Interfase que define las constantes requeridas por WBPartameter.
 * 
 * @author Javier Solis Gonzalez
 */
public interface SWBResourceModes
{
    
    /** The Constant WinState_NORMAL. */
    public static final String WinState_NORMAL="normal";
    
    /** The Constant WinState_MAXIMIZED. */
    public static final String WinState_MAXIMIZED="maximized";
    
    /** The Constant WinState_MINIMIZED. */
    public static final String WinState_MINIMIZED="minimized";    
    
    /** The Constant Mode_EDIT. */
    public static final String Mode_EDIT="edit";
    
    /** The Constant Mode_VIEW. */
    public static final String Mode_VIEW="view";
    
    /** The Constant Mode_HELP. */
    public static final String Mode_HELP="help";
    
    /** The Constant Mode_ADMIN. */
    public static final String Mode_ADMIN="admin";
    
    /** The Constant Mode_ADMHLP. */
    public static final String Mode_ADMHLP="admhlp";
    
    /** The Constant Mode_XML. */
    public static final String Mode_XML="XML";
    
    /** The Constant Mode_INDEX. */
    public static final String Mode_INDEX="index";
    
    /** The Constant Action_EDIT. */
    public static final String Action_EDIT="edit";
    
    /** The Constant Action_ADD. */
    public static final String Action_ADD="add";
    
    /** The Constant Action_REMOVE. */
    public static final String Action_REMOVE="remove";

    /** The Constant UsrLevel_NONE. */
    public static final int UsrLevel_NONE=0;
    
    /** The Constant UsrLevel_SEE. */
    public static final int UsrLevel_SEE=1;
    
    /** The Constant UsrLevel_CREATE. */
    public static final int UsrLevel_CREATE=2;
    
    /** The Constant UsrLevel_MODIFY. */
    public static final int UsrLevel_MODIFY=3;
    
    /** The Constant UsrLevel_REMOVE. */
    public static final int UsrLevel_REMOVE=4;
    
    /** The Constant UsrLevel_ADMIN. */
    public static final int UsrLevel_ADMIN=5;
    
    /** The Constant Call_CONTENT. */
    public static final int Call_CONTENT=1;
    
    /** The Constant Call_STRATEGY. */
    public static final int Call_STRATEGY=2;
    
    /** The Constant Call_DIRECT. */
    public static final int Call_DIRECT=3;    
    
    /** The Constant UrlType_RENDER. */
    public static final int UrlType_RENDER=0;
    
    /** The Constant UrlType_ACTION. */
    public static final int UrlType_ACTION=1;
}
