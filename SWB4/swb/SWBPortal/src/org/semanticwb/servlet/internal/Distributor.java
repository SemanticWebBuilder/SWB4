/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.servlet.internal;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.User;
import org.semanticwb.model.WebPage;
import org.semanticwb.model.WebSite;
import org.semanticwb.portal.resources.SWBResourceModes;

/**
 *
 * @author Jei
 */
public class Distributor implements InternalServlet
{
    static Logger log=SWBUtils.getLogger(Distributor.class);
    String name = "swb";
    boolean agzip = true;
    boolean admin = true;
    boolean secure = false;
    
    WebSite admMap=null;
    
    public void init(ServletContext scontext) 
    {
        log.event("Initializing InternalServlet Distributor...");
        name = SWBPlatform.getEnv("swb/distributor","swb");
        agzip = SWBPlatform.getEnv("swb/responseGZIPEncoding", "true").equalsIgnoreCase("true");
        admin = SWBPlatform.getEnv("swb/administration", "true").equalsIgnoreCase("true");
        secure = SWBPlatform.getEnv("swb/secureAdmin", "false").equalsIgnoreCase("true");
        admMap=SWBContext.getAdminWebSite();
    }
    
    public void doProcess(HttpServletRequest request, HttpServletResponse response, DistributorParams dparams)throws IOException 
    {
        long time=System.currentTimeMillis();
        if(_doProcess(request, response, dparams))
        {
            SWBPortal.getMonitor().addinstanceHit(System.currentTimeMillis()-time);
        }
    }    
    
    public boolean _doProcess(HttpServletRequest request, HttpServletResponse response, DistributorParams dparams)throws IOException 
    {
        boolean ret=true;
        log.debug("Distributor->doProcess()");
        
        try
        {
            User user = dparams.getUser();
            ArrayList uri = dparams.getResourcesURI();
            WebPage webpage = dparams.getWebPage();
            WebPage vwebpage = dparams.getVirtWebPage();
            int ipfilter = dparams.getFiltered();
            boolean onlyContent = dparams.isOnlyContent();

            {
                log.trace("*********distributor**************");
                log.trace("email:" + user.getUsrEmail());
                log.trace("session:" + request.getSession().getId());
                //out.println("isRegistered:" + user.isRegistered());
                //out.println("isloged:" + user.isLoged());
                log.trace("uri:" + request.getRequestURI());
                log.trace("servlet:" + request.getServletPath());
                log.trace("webpage:" + webpage);
                log.trace("virtWebpage:" + vwebpage);
                log.trace("ipfilter:" + ipfilter);
                log.trace("isSecure:" + request.isSecure());
                log.trace("onlyContent:" + onlyContent);
                log.trace("*****************************");
            }

            if (secure && webpage!=null && webpage.getWebSite().equals(admMap))
            {
                if (!request.isSecure()) 
                {
                    log.debug("Distributor: SendError 404");
                    response.sendError(404, "Not https protocol...");
                    return false;
                }
            }       

            if (ipfilter == 1) 
            {
                log.debug("Distributor: SendError 404");
                response.sendError(404, "No tiene permiso para accesar a la pagina " + request.getRequestURI() + ", (IP Filter)... ");
                return false;
            }        
        
//            //TODO:cambiar por opcion en el topico needVirtualTopic            
//            if (webpage != null && webpage.getWebSite() == admMap && vtopic == null) {
//                if (inti_StatusTopic == webpage || webpage.isChildof(sys_Topics) || webpage.isChildof(sys_Contents) || webpage.isChildof(info_Topic))
//                {
//                    log.debug("Distributor: SendError 404");
//                    response.sendError(404, "No tiene permiso para accesar a la pagina " + request.getRequestURI());
//                    return false;
//                }
//            }

            if (webpage == null || (!admin && webpage.getWebSite().equals(admMap))) 
            {
                response.sendError(404, "La pagina " + request.getRequestURI() + " no existe... ");
                log.debug("Distributor: SendError 404");
                return false;
            } else if (!webpage.getWebSite().isActive() || !webpage.isActive()) 
            {
                response.sendError(404, "La pagina " + request.getRequestURI() + " no esta disponible por el momento... ");
                log.debug("Distributor: SendError 404");
                return false;
            }

            //TODO:validar permisos a paginas 
//            if (!user.haveAccess(webpage)) 
//            {
//                log.debug("Distributor->Don't access");
//                Iterator it = webpage.getConfigData(TopicMap.CNF_WBSecAction);
//                if (it.hasNext()) {
//                    //System.out.println("hasData");
//                    String data = (String) it.next();
//                    log.debug("Distributor->data:" + data);
//                    if (data.startsWith("SR:")) {
//                        response.sendRedirect(data.substring(3));
//                    } else if (data.startsWith("SE:")) {
//                        int err = Integer.parseInt(data.substring(3));
//                        if (err == 403)
//                            sendError403(request, response);
//                        else
//                            response.sendError(err);
//                    }
//                } else {
//                    log.debug("Distributor->send403");
//                    sendError403(request, response);
//                }
//
//                return false;
//            }

            String content = null;

            if (dparams.getAccType() == DistributorParams.ACC_TYPE_RENDER) // es un recurso
            {
                try {
                    long rid = dparams.getAccResourceID();
                    HashMap resp = dparams.getResourceURI(rid);
                    int mto = SWBUtils.TEXT.getInt((String)resp.get(DistributorParams.URLP_METHOD), SWBResourceModes.Call_CONTENT);
                    String mdo = (String) resp.get(DistributorParams.URLP_MODE);
                    String wst = (String) resp.get(DistributorParams.URLP_WINSTATE);
                    String act = (String) resp.get(DistributorParams.URLP_ACTION);
                    String idtm = dparams.getAccResourceTMID();
                    String extParams=dparams.getNotAccResourceURI(rid);

                    WBResource base = ResourceMgr.getInstance().getResource(idtm, rid);
                    if (webpage.getWebSite() != TopicMgr.getInstance().getAdminTopicMap())
                    {
                        if(base == null)
                        {
                            response.sendError(404, "No tiene permiso para accesar a la pagina " + request.getRequestURI() + ", (Control de IPs)... ");
                            log.debug("Distributor: SendError 404");
                            return false;
                        }else if(!base.getResourceBase().haveAccess(user))
                        {
                            if(request.getMethod().equalsIgnoreCase("POST"))
                            {
                                response.sendRedirect(webpage.getUrl());
                                log.debug("Distributor: Resource "+base.getResourceBase().getId()+" restricted, send redirect to webpage:"+webpage.getUrl());
                            }else
                            {
                                response.sendError(403, "No tiene permiso para accesar a la pagina " + request.getRequestURI() + ", (Control de IPs)... ");
                                log.debug("Distributor: SendError 403");
                            }
                            return false;
                        }
                    }                    

                    if (mto == WBResourceModes.Call_DIRECT) //call direct
                    {
                        //TODO:Falta envio de log de accesos
                        WBResource currResource = ResourceMgr.getInstance().getResource(idtm, rid);
                        //WBResponse resp=new WBResponse(response);
                        WBParamRequestImp resParams = new WBParamRequestImp(request, currResource.getResourceBase(), webpage, user);
                        //resParams.setArguments(args);
                        resParams.setExtURIParams(extParams);
                        if (act != null) resParams.setAction(act);
                        resParams.setCallMethod(mto);
                        if (mdo != null) resParams.setMode(mdo);
                        if (wst != null) resParams.setWindowState(wst);
                        if (vtopic != null) {
                            resParams.setVirtualTopic(vtopic);
                        }
                        resParams.setOnlyContent(onlyContent);
                        ResourceMgr.getInstance().getResourceTraceMgr().renderTraced(currResource, request, response, resParams);
                        return System.currentTimeMillis()-tini;
                    } else if (WBResourceModes.WinState_MAXIMIZED.equals(wst)) //WinState_MAXIMIZED
                    {
                        WBResponse res = new WBResponse(response);
                        WBResource currResource = ResourceMgr.getInstance().getResource(idtm, rid);
                        //WBResponse resp=new WBResponse(response);
                        WBParamRequestImp resParams = new WBParamRequestImp(request, currResource.getResourceBase(), webpage, user);
                        //resParams.setArguments(args);
                        resParams.setExtURIParams(extParams);
                        if (act != null) resParams.setAction(act);
                        resParams.setCallMethod(mto);
                        if (mdo != null) resParams.setMode(mdo);
                        if (wst != null) resParams.setWindowState(wst);
                        if (vtopic != null) {
                            resParams.setVirtualTopic(vtopic);
                        }
                        resParams.setOnlyContent(onlyContent);
                        ResourceMgr.getInstance().getResourceTraceMgr().renderTraced(currResource, request, res, resParams);
                        content = res.toString();
                        if(res.isSendRedirect())
                        {
                            return false;
                        }
                    }
                } catch (TemplateInterruptedException noe) {
                    return false;
                } catch (Throwable e) {
                    AFUtils.log(e);
                    log.debug("Distributor: SendError 500");
                    response.sendError(500, "No es posible procesar el requerimiento:" + request.getRequestURI());
                    return System.currentTimeMillis()-tini;
                }
            } else if (dparams.getAccType() == dparams.ACC_TYPE_ACTION) // process action
            {
                try {
                    long rid = dparams.getAccResourceID();
                    String idtm = dparams.getAccResourceTMID();
                    HashMap resp = dparams.getResourceURI(rid);
                    int mto = dparams.getInt((String) resp.get(dparams.URLP_METHOD), WBResourceModes.Call_CONTENT);
                    String mdo = (String) resp.get(dparams.URLP_MODE);
                    String wst = (String) resp.get(dparams.URLP_WINSTATE);
                    String act = (String) resp.get(dparams.URLP_ACTION);
                    String extParams=dparams.getNotAccResourceURI(rid);

                    WBResource currResource = ResourceMgr.getInstance().getResource(idtm, rid);
                    if(currResource==null)
                    {
                        AFUtils.log("Error al procesar el URL:"+request.getRequestURL());
                        response.sendError(404, "No se encontro:" + request.getRequestURI() + "<BR>");
                        return false;
                    }
                    WBActionResponseImp resParams = new WBActionResponseImp(response);
                    if (act != null) resParams.setAction(act);
                    resParams.setExtURIParams(extParams);
                    resParams.setCallMethod(mto);
                    if (mdo != null) resParams.setMode(mdo);
                    if (wst != null) resParams.setWindowState(wst);
                    resParams.setResourceBase(currResource.getResourceBase());
                    resParams.setTopic(webpage);
                    if (vtopic != null) {
                        resParams.setVirtualTopic(vtopic);
                    }
                    resParams.setOnlyContent(onlyContent);
                    resParams.setUser(user);
                    //resParams.setUserLevel();
                    log.debug("Invoke ProcessAcion");
                    currResource.processAction(request, resParams);
                    log.debug("SendRedirect:"+resParams.toString());
                    response.sendRedirect(resParams.toString());
                    log.debug("Exit Distributor");
                    return System.currentTimeMillis()-tini;
                } catch (Throwable e) {
                    AFUtils.log(e);
                    log.debug("Distributor: SendError 500");
                    response.sendError(500, "No es posible procesar el requerimiento:" + request.getRequestURI() + "<BR>" + e);
                    return System.currentTimeMillis()-tini;
                }
            }

            try//Traer template y comprimir salida..
            {
                Template currTemplate = null;
                try {
                    /*
                     * El template Manager SIEMPRE devolverá un template, por lo que debemos definir el
                     * caso especial en que no haya nada de nada para que devuelva un template que solo
                     * tenga el tag de contenido.
                     */
                    currTemplate = tmpMngr.getTemplate(user, webpage);
                    /*
                     *
                     *   En el remoto caso de que surja un error critico (por lo cual no se pueda
                     * asignar templeates en este punto se suspende la ejecución, logueando el error
                     * y disparando un mensaje de sitio en mantenimiento <--- Checar si los demás están de acuerdo en este particular
                     *
                     */
                } catch (AFException e) {
                    AFUtils.log("No se encontro template para la seccion:" + webpage.getId());
                    response.sendError(500, "La pagina " + request.getRequestURI() + " no esta disponible por el momento... ");
                    log.debug("Distributor: SendError 500");
                    return System.currentTimeMillis()-tini;
                }

                boolean gzip = false;
                if (agzip) {
                    if (request.getHeader("Via") != null
                            || request.getHeader("X-Forwarded-For") != null
                            || request.getHeader("Cache-Control") != null) {
                        //using proxy -> no zip
                    } else {
                        String accept = request.getHeader("Accept-Encoding");
                        if (accept != null && accept.toLowerCase().indexOf("gzip") != -1) {
                            gzip = true;
                        }
                    }
                }

                WBHttpServletResponseWrapper res = new WBHttpServletResponseWrapper(response);
                try {
                    //System.out.println("DistributorImp->onlyContent:"+onlyContent);
                    PrintWriter out=res.getWriter();
                    //out.println("\n<!--Time: " + (System.currentTimeMillis() - tini) + "ms - " + webpage + "--> ");  //TODO encontrar una forma de configurar esto...
                    if (ipfilter == -1) {
                        if (onlyContent) {
                            Template.buildContents(request, res, out, dparams, true, content);
                        } else {
                            currTemplate.build(request, res, out, user, webpage, true, content, dparams);
                        }
                    } else {
                        if (onlyContent) {
                            Template.buildContents(request, res, out, dparams, false, content);
                        } else {
                            currTemplate.build(request, res, out, user, webpage, false, content, dparams);
                        }
                    }
                } catch (com.infotec.wb.exception.TemplateInterruptedException tie) {
                    //if(gzip)garr.close();
                    //return;
                } catch (Exception e) {
                    AFUtils.log(e, "Error al procesar template para la seccion:" + webpage.getId(), true);
                    response.sendError(500, "La pagina " + request.getRequestURI() + " no esta disponible por el momento... ");
                    //if(gzip)garr.close();
                    return System.currentTimeMillis()-tini;
                }
                

                if(res.isSendRedirect())
                {
                    return false;
                }
                
                response.setContentType("text/html");
                java.util.zip.GZIPOutputStream garr = null;
                PrintWriter out = null;
                
                if (gzip) {
                    response.setHeader("Content-Encoding", "gzip");
                    garr = new java.util.zip.GZIPOutputStream(response.getOutputStream());
                    out = new PrintWriter(garr, true);
                } else {
                    out = response.getWriter();
                }                

                //out.println("\n<!--Time: " + (System.currentTimeMillis() - tini) + "ms - " + webpage + "--> ");  //TODO encontrar una forma de configurar esto...
                out.print(res.toString());
                long tfin = System.currentTimeMillis() - tini;
                out.println("\n<!--Time: " + tfin + "ms - " + webpage + "--> ");  //TODO encontrar una forma de configurar esto...

                //if (gzip)
                {
                    //garr.close();
                    out.close();
                }
            } catch (Exception e) {
                AFUtils.log(e, "Traer template y comprimir salida...", true);
                return System.currentTimeMillis()-tini;
            }
        } catch (Throwable e) 
        {
            log.log(e);
            return false;
        }
        return ret;
    }
    
    private void sendError403(HttpServletRequest request, HttpServletResponse response) 
    {
        try
        {
            response.sendError(403, "No tiene permiso para accesar a la pagina " + request.getRequestURI()+"... ");
        }catch(IOException e)
        {
            log.error(e);
        }
    }
 



}
