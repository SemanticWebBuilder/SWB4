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
package org.semanticwb.process.xmlrpc;

import java.util.Date;

/**
 * Almacena la información de una instancia genérica (nodo de flujo o proceso).
 * @author Hasdai Pacheco <ebenezer.sanchez@infotec.com.mx>
 */
public class InstanceInfo {
    /** Identificador. Identificador de la instancia.*/
    public String id;
    /** Título. Título de la instancia.*/
    public String title;
    /** Descripción. Descripción de la instancia.*/
    public String description;
    /** Estado. Código de estado de la instancia (ver @{@link Instance}).*/
    public int status;
    /** Fecha de creación. Fecha en que se creó la instancia.*/
    public Date created;
    /** Fecha de cierre. Fecha en que se cerró la instancia.*/
    public Date closed;
    /** Creador. ID del usuario que creó la tarea (puede ser nulo si se creó de manera automática).*/
    public String creator;
    /** Usuario finalizador. ID del usuario que cerró la tarea.*/
    public String closedBy;
}
