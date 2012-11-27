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
package org.semanticwb.portal.admin.resources.wbtree;


import org.semanticwb.model.User;
import org.semanticwb.model.WebSite;
import org.w3c.dom.Element;

// TODO: Auto-generated Javadoc
/**
 * The Interface SWBTreeExt.
 * 
 * @author Javier Solis Gonzalez
 */
public interface SWBTreeExt
{
    
    /**
     * inicializa el arbol.
     * 
     * @param user the user
     * @param res the res
     * @param isFilter the is filter
     */
    public void initTree(User user, Element res, boolean isFilter);
    
    /**
     * Adds the server.
     * 
     * @param user the user
     * @param server the server
     * @param isFilter the is filter
     */
    public void addServer(User user, Element server, boolean isFilter);
    
    /**
     * Adds the global.
     * 
     * @param user the user
     * @param global the global
     * @param access the access
     * @param isFilter the is filter
     */
    public void addGlobal(User user, Element global, int access, boolean isFilter);
    
    /**
     * Adds the topic map.
     * 
     * @param user the user
     * @param topicmap the topicmap
     * @param tm the tm
     * @param access the access
     * @param isFilter the is filter
     */
    public void addTopicMap(User user, Element topicmap, WebSite tm, int access, boolean isFilter);
    
    /**
     * Execute command.
     * 
     * @param user the user
     * @param res the res
     * @param cmd the cmd
     * @param id the id
     * @return true, if successful
     */
    public boolean executeCommand(User user, Element res, String cmd, String id);
    
}
