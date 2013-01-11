/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.social.components.resources;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.WebSite;
import org.semanticwb.portal.api.GenericAdmResource;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.lib.SWBResponse;
import org.semanticwb.social.components.tree.ElementTreeNode;
import org.semanticwb.social.utils.SWBSocialResourceUtils;
import java.net.URLDecoder;

/**
 *
 * @author jorge.jimenez
 * @date 10/03/2012
 */
/*
 * Clase de swbsocial que es utilizada como un recurso en la administración de la herramienta, sirve para poder
 * redirigir el control hacia un zul, pasandole los para metros que la herramienta envía desde el árbol, los cuales son:
 * wsite, action, objUri, treeItem.
 */
public class SocialZulResource extends GenericAdmResource
{


    /**
     * objeto encargado de crear mensajes en los archivos log de SemanticWebBuilder (SWB).
     * <p>object that creates messages in SWB's log file.</p>
     */
    private static Logger log = SWBUtils.getLogger(SocialZulResource.class);
    
    
    @Override
    public void render(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        if(!SWBParamRequest.WinState_MINIMIZED.equals(paramRequest.getWindowState()))
        {
            WebSite wsite=WebSite.ClassMgr.getWebSite(request.getParameter("wsite"));
            ElementTreeNode treeItem=SWBSocialResourceUtils.Components.getComponentbyUri(request);

            request.setAttribute("wsite", wsite);
            request.setAttribute("action", request.getParameter("action"));
            if(request.getParameter("objUri")!=null)
            {
                request.setAttribute("objUri", URLDecoder.decode(request.getParameter("objUri")));
            }
            request.setAttribute("treeItem", treeItem);
            //La linea siguiente siempre debe de ir.
            processRequest(request, response, paramRequest);
        }
    }

    /**
     * Realiza la llamada a ejecuci&oacute;n del archivo ZUL especificado en la
     * vista de administraci&oacute;n de este recurso.
     * <p>Performs the execution call of the ZUL file specified in this resource's
     * administration view.</p>
     *
     * @param request la petici&oacute;n HTTP generada por el usuario. <p>the user's HTTP request</p>
     * @param response la respuesta hacia el usuario.<p>the response to the user</p>
     * @param paramRequest the param request
     * @throws java.io.IOException si este recurso no tiene asociado el archivo
     * que se crea cuando se edita el c&oacute;digo. <p>if this resource
     * has no file, which is created after editing code, associated</p>
     * @throws SWBResourceException the sWB resource exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        String path = getResourceBase().getAttribute("zulpath");
        try {
            request.setAttribute("paramRequest", paramRequest);
            RequestDispatcher dispatcher = request.getRequestDispatcher(path);
            if(dispatcher != null) {
                if(getResourceBase().getAttribute("forward")!=null) {
                    dispatcher.forward(request, response);
                }else {
                    dispatcher.include(request, response);
                }
            }
        } catch (Exception e) {
            log.error(paramRequest.getLocaleLogString("Process_Error") + "..." + path, e);
        }
    }

    /**
     * Realiza la llamada a ejecuci&oacute;n del archivo ZUL especificado en la
     * vista de administraci&oacute;n de este recurso.
     * <p>Performs the execution call of the ZUL file specified in this resource's
     * administration view.</p>
     *
     * @param request la petici&oacute;n HTTP generada por el usuario. <p>the
     * user's HTTP request</p>
     * @param actionResponse la respuesta a la acci&oacute;n solicitada por el usuario
     * <p>the response to the action requested by the user.</p>
     * @throws java.io.IOException si hay alg&uacute;n problema mientras se escribe el
     * c&oacute;digo en el archivo.
     * <p>if there is a problem while writing the code in the file.</p>
     * @throws SWBResourceException the sWB resource exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse actionResponse) throws SWBResourceException, IOException {
        String path = getResourceBase().getAttribute("zulactpath");
        if(path == null) {
            return;
        }
        try {
            request.setAttribute("actionResponse", actionResponse);
            RequestDispatcher dispatcher = request.getRequestDispatcher(path);
            if(getResourceBase().getAttribute("forward")!=null) {
                dispatcher.forward(request, new SWBResponse());
            }else {
                dispatcher.include(request, new SWBResponse());
            }
        } catch (Exception e) {
            log.error(actionResponse.getLocaleLogString("Process_Error") + "..." + path, e);
        }
    }
}
