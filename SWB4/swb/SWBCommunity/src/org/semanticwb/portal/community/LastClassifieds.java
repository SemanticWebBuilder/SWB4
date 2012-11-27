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
package org.semanticwb.portal.community;


import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.portal.api.GenericAdmResource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.lib.SWBResponse;

/** Este recurso permite ejecutar un archivo jsp, dando la ruta del archivo a
 * ejecutar dentro del sitio, pudi&eacute;ndose presentar como contenido o como
 * recurso.
 *
 * This resource allows to execute a JSP file, giving the file path to execute
 * within the site, showing it as a content or as a resource.
 *
 * @author Jose Jimenez
 */
public class LastClassifieds extends GenericAdmResource {


    /**
     * objeto encargado de crear mensajes en los archivos log de SemanticWebBuilder (SWB).
     * <p>object that creates messages in SWB's log file.</p>
     */
    private static Logger log = SWBUtils.getLogger(LastClassifieds.class);

    /**
     * Realiza la llamada a ejecuci&oacute;n del archivo JSP especificado en la
     * vista de administraci&oacute;n de este recurso.
     * <p>Performs the execution call of the JSP file specified in this resource's
     * administration view.</p>
     * @param request la petici&oacute;n HTTP generada por el usuario. <p>the user's HTTP request</p>
     * @param response la respuesta hacia el usuario.<p>the response to the user</p>
     * @param paramsRequest el objeto generado por SWB y asociado a la petici&oacute;n
     *        del usuario.<p>the object gnerated by SWB and asociated to the user's request</p>
     * @throws org.semanticwb.portal.api.SWBResourceException
     * @throws java.io.IOException si este recurso no tiene asociado el archivo
     *         que se crea cuando se edita el c&oacute;digo. <p>if this resource
     *         has no file, which is created after editing code, associated</p>
     */
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response,
            SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        String path = getResourceBase().getAttribute("jsppath");
        try {
            request.setAttribute("paramRequest", paramRequest);
            RequestDispatcher dispatcher = request.getRequestDispatcher(path);
            if (dispatcher != null) {
                //System.out.println("forward:"+getResourceBase().getAttribute("forward"));
                if (getResourceBase().getAttribute("forward") != null) {
                    dispatcher.forward(request, response);
                } else {
                    dispatcher.include(request, response);
                }
                //new Exception().printStackTrace();
            }
        } catch (Exception e) {
            log.error(paramRequest.getLocaleLogString("Process_Error") + "..." + path, e);
        }
    }

    /**
     * Realiza la llamada a ejecuci&oacute;n del archivo JSP especificado en la
     * vista de administraci&oacute;n de este recurso.
     * <p>Performs the execution call of the JSP file specified in this resource's
     * administration view.</p>
     * @param request la petici&oacute;n HTTP generada por el usuario. <p>the
     *                user's HTTP request</p>
     * @param actionResponse la respuesta a la acci&oacute;n solicitada por el usuario
     *        <p>the response to the action requested by the user.</p>
     * @throws org.semanticwb.portal.api.SWBResourceException
     * @throws java.io.IOException si hay alg&uacute;n problema mientras se escribe el
     *         c&oacute;digo en el archivo.
     *         <p>if there is a problem while writing the code in the file.</p>
     */
    @Override
    public void processAction(HttpServletRequest request,
            SWBActionResponse actionResponse)
            throws SWBResourceException, IOException
    {
        String path = getResourceBase().getAttribute("jspactpath");
        if (path == null) {
            return;
        }
        try {
            request.setAttribute("actionResponse", actionResponse);
            RequestDispatcher dispatcher = request.getRequestDispatcher(path);
            if (getResourceBase().getAttribute("forward") != null) {
                dispatcher.forward(request, new SWBResponse());
            } else {
                dispatcher.include(request, new SWBResponse());
            }
        } catch (Exception e) {
            log.error(actionResponse.getLocaleLogString("Process_Error") + "..." + path, e);
        }
    }
}
