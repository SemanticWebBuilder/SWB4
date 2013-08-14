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
package org.semanticwb.portal.resources;

import java.io.IOException;
import java.util.Iterator;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.portal.TemplateImp;
import org.semanticwb.portal.api.GenericAdmResource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.SWBParameters;
import org.semanticwb.portal.lib.SWBResponse;

// TODO: Auto-generated Javadoc
/** Este recurso permite ejecutar un archivo jsp, dando la ruta del archivo a
 * ejecutar dentro del sitio, pudi&eacute;ndose presentar como contenido o como
 * recurso.
 *
 * This resource allows to execute a JSP file, giving the file path to execute
 * within the site, showing it as a content or as a resource.
 *
 * @author Javier Solis Gonz&aacute;lez
 */
public class JSPResource extends GenericAdmResource 
{


    /**
     * objeto encargado de crear mensajes en los archivos log de SemanticWebBuilder (SWB).
     * <p>object that creates messages in SWB's log file.</p>
     */
    private static Logger log = SWBUtils.getLogger(JSPResource.class);
    
    /**
     * Realiza la llamada a ejecuci&oacute;n del archivo JSP especificado en la
     * vista de administraci&oacute;n de este recurso.
     * <p>Performs the execution call of the JSP file specified in this resource's
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
        String path = getResourceBase().getAttribute("jsppath");
        if(path == null) {
            return;
        }        
        path=replaceTags(path,request,paramRequest);
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
     * Realiza la llamada a ejecuci&oacute;n del archivo JSP especificado en la
     * vista de administraci&oacute;n de este recurso.
     * <p>Performs the execution call of the JSP file specified in this resource's
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
        String path = getResourceBase().getAttribute("jspactpath");
        if(path == null) {
            return;
        }
        path=replaceTags(path,request, actionResponse);        
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
    
    

    /**
     * Replace tags.
     * 
     * @param str the str
     * @param request the request
     * @param paramRequest the param request
     * @return the string
     */
    public String replaceTags(String str, HttpServletRequest request, SWBParameters paramRequest)
    {
        if(str==null || str.trim().length()==0)
            return "";
        
        str=str.trim();
        //TODO: codificar cualquier atributo o texto
        if(str.indexOf("{")>-1)
        {
       
            Iterator it=SWBUtils.TEXT.findInterStr(str, "{request.getParameter(\"", "\")}");
            while(it.hasNext())
            {
                String s=(String)it.next();
                str=SWBUtils.TEXT.replaceAll(str, "{request.getParameter(\""+s+"\")}", request.getParameter(replaceTags(s,request,paramRequest)));
            }

            it=SWBUtils.TEXT.findInterStr(str, "{session.getAttribute(\"", "\")}");
            while(it.hasNext())
            {
                String s=(String)it.next();
                str=SWBUtils.TEXT.replaceAll(str, "{session.getAttribute(\""+s+"\")}", (String)request.getSession().getAttribute(replaceTags(s,request,paramRequest)));
            }

            it=SWBUtils.TEXT.findInterStr(str, "{getEnv(\"", "\")}");
            while(it.hasNext())
            {
                String s=(String)it.next();
                str=SWBUtils.TEXT.replaceAll(str, "{getEnv(\""+s+"\")}", SWBPlatform.getEnv(replaceTags(s,request,paramRequest)));
            }

            str=SWBUtils.TEXT.replaceAll(str, "{user.login}", paramRequest.getUser().getLogin());
            str=SWBUtils.TEXT.replaceAll(str, "{user.email}", paramRequest.getUser().getEmail());
            str=SWBUtils.TEXT.replaceAll(str, "{user.language}", paramRequest.getUser().getLanguage());
            str=SWBUtils.TEXT.replaceAll(str, "{user.country}", paramRequest.getUser().getCountry());
            str=SWBUtils.TEXT.replaceAll(str, "{webpath}", SWBPortal.getContextPath());
            str=SWBUtils.TEXT.replaceAll(str, "{distpath}", SWBPortal.getDistributorPath());
            str=SWBUtils.TEXT.replaceAll(str, "{webworkpath}", SWBPortal.getWebWorkPath());
            str=SWBUtils.TEXT.replaceAll(str, "{workpath}", SWBPortal.getWorkPath());
            str=SWBUtils.TEXT.replaceAll(str, "{websiteid}", paramRequest.getWebPage().getWebSiteId());
            str=SWBUtils.TEXT.replaceAll(str, "{topicurl}", paramRequest.getWebPage().getUrl());
            str=SWBUtils.TEXT.replaceAll(str, "{topicid}", paramRequest.getWebPage().getId());
            str=SWBUtils.TEXT.replaceAll(str, "{topic.title}", paramRequest.getWebPage().getDisplayTitle(paramRequest.getUser().getLanguage()));
            if(str.indexOf("{templatepath}")>-1)
            {
                //TODO:pasar template por paramrequest
                TemplateImp template=(TemplateImp)SWBPortal.getTemplateMgr().getTemplate(paramRequest.getUser(), paramRequest.getAdminTopic());
                if(template!=null)
                {
                    str=SWBUtils.TEXT.replaceAll(str, "{templatepath}", template.getActualPath());
                }
            }
        }
        return str;
    }        
    
    
}
