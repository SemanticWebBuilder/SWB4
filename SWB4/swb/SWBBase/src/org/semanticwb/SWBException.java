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
package org.semanticwb;


// TODO: Auto-generated Javadoc
/**
 * Generic exception in the SemanticWebBuilder API.
 * <p>La excepci&oacute;n gen&eacute;rica en el API de SemanticWebBuilder.</p>
 * @author Javier Solis Gonzalez
 * @version 1.1
 */
public class SWBException extends java.lang.Exception
{


    /**
     * Generates an instance of this exception with a specified detail message.
     * <p>Genera una instancia de esta excepci&oacute;n con un mensaje de detalle
     * especificado.</p>
     * @param msg the detail message (which is saved for later retrieval by the
     *            {@link Throwable.getMessage() Throwable.getMessage()} method)
     */
    public SWBException(String msg)
    {
        super(msg);
    }

    /**
     * Generates an instance of this exception with the specified detail message and cause.
     * <p>Genera una instancia de esta excepci&oacute;n con el mensaje de detalle
     * y causa especificados.</p>
     * @param msg the detail message (which is saved for later retrieval by the
     *            Throwable.getMessage() method)
     * @param e the cause (which is saved for later retrieval by the {@link Throwable.getCause() Throwable.getCause()}
     *          method). (A {@code null} value is permitted, and indicates that
     *          the cause is nonexistent or unknown.)
     */
    public SWBException(String msg, Exception e)
    {
        super(msg, e);
    }

}


