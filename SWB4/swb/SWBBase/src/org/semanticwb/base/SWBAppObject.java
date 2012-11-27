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
package org.semanticwb.base;


// TODO: Auto-generated Javadoc
/**
 * Application object that need to perform some operations constantly in time
 * or by events that are very common in the entire application, hence will remain
 * alive while the application is running.
 * <p>Objeto de aplicaci&oacute;n que necesita realizar algunas operaciones constantemente
 * en el tiempo o controlada por eventos que son muy comunes en toda la aplicaci&oacute;n,
 * por lo que permanecer&aacute; vivo mientras la aplicaci&oacute;n est&eacute; en
 * ejecuci&oacute;n.</p>
 * @author Javier Solis Gonzalez
 * @version 1.1
 */
public interface SWBAppObject {


    /**
     * Initializes the state of this object and prepares any element in the environment
     * necesary for its proper operation.
     * <p>Inicializa el estado de este objeto y prepara cualquier elemento en el
     * ambiente de la aplicaci&oacute;n necesario para su adecuada operaci&oacute;n.</p>
     */
    void init();

    /**
     * Finalizes the state of this object and prepares any element of the application
     * environment to stop interacting with this object.
     * <p>Finaliza el estado de este objeto y prepara cualquier elemento del ambiente
     * de la aplicaci&oacute;n para dejar de interactuar con este objeto.</p>
     */
    public void destroy();

    /**
     * Updates the state of this object.
     * <p>Actualiza el estado de este objeto.</p>
     */
    public void refresh();
}

