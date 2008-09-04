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
import org.semanticwb.SWBException;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.Template;
import org.semanticwb.model.User;
import org.semanticwb.model.WebPage;
import org.semanticwb.model.WebSite;
import org.semanticwb.portal.TemplateImp;
import org.semanticwb.portal.api.SWBActionResponseImp;
import org.semanticwb.portal.api.SWBParamRequestImp;
import org.semanticwb.portal.api.SWBResourceModes;
import org.semanticwb.portal.api.SWBResource;
import org.semanticwb.servlet.SWBHttpServletResponseWrapper;

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
        long tini=System.currentTimeMillis();
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
//            if (webpage != null && webpage.getWebSite() == admMap && vwebpage == null) {
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
                    String rid = dparams.getAccResourceID();
                    HashMap resp = dparams.getResourceURI(rid);
                    int mto = SWBUtils.TEXT.getInt((String)resp.get(DistributorParams.URLP_METHOD), SWBResourceModes.Call_CONTENT);
                    String mdo = (String) resp.get(DistributorParams.URLP_MODE);
                    String wst = (String) resp.get(DistributorParams.URLP_WINSTATE);
                    String act = (String) resp.get(DistributorParams.URLP_ACTION);
                    String idtm = dparams.getAccResourceTMID();
                    String extParams=dparams.getNotAccResourceURI(rid);

                    SWBResource base = SWBPortal.getResourceMgr().getResource(idtm, rid);
                    if (!webpage.getWebSite().equals(SWBContext.getAdminWebSite()))
                    {
                        if(base == null)
                        {
                            response.sendError(404, "No tiene permiso para accesar a la pagina " + request.getRequestURI() + ", (Control de IPs)... ");
                            log.debug("Distributor: SendError 404");
                            return false;
                        }else 
                            //TODO:Revisar seguridad
                            //if(!base.getResourceBase().haveAccess(user))
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

                    if (mto == SWBResourceModes.Call_DIRECT) //call direct
                    {
                        //TODO:Falta envio de log de accesos
                        SWBResource currResource = SWBPortal.getResourceMgr().getResource(idtm, rid);
                        //SWBResponse resp=new SWBResponse(response);
                        SWBParamRequestImp resParams = new SWBParamRequestImp(request, currResource.getResourceBase(), webpage, user);
                        //resParams.setArguments(args);
                        resParams.setExtURIParams(extParams);
                        if (act != null) resParams.setAction(act);
                        resParams.setCallMethod(mto);
                        if (mdo != null) resParams.setMode(mdo);
                        if (wst != null) resParams.setWindowState(wst);
                        if (vwebpage != null) {
                            resParams.setVirtualTopic(vwebpage);
                        }
                        resParams.setOnlyContent(onlyContent);
                        SWBPortal.getResourceMgr().getResourceTraceMgr().renderTraced(currResource, request, response, resParams);
                        return true;
                    } else if (SWBResourceModes.WinState_MAXIMIZED.equals(wst)) //WinState_MAXIMIZED
                    {
                        SWBHttpServletResponseWrapper res = new SWBHttpServletResponseWrapper(response);
                        SWBResource currResource = SWBPortal.getResourceMgr().getResource(idtm, rid);
                        //SWBResponse resp=new SWBResponse(response);
                        SWBParamRequestImp resParams = new SWBParamRequestImp(request, currResource.getResourceBase(), webpage, user);
                        //resParams.setArguments(args);
                        resParams.setExtURIParams(extParams);
                        if (act != null) resParams.setAction(act);
                        resParams.setCallMethod(mto);
                        if (mdo != null) resParams.setMode(mdo);
                        if (wst != null) resParams.setWindowState(wst);
                        if (vwebpage != null) {
                            resParams.setVirtualTopic(vwebpage);
                        }
                        resParams.setOnlyContent(onlyContent);
                        SWBPortal.getResourceMgr().getResourceTraceMgr().renderTraced(currResource, request, res, resParams);
                        content = res.toString();
                        if(res.isSendRedirect())
                        {
                            return false;
                        }
                    }
                } catch (Throwable e) 
                {
                    log.error(e);
                    log.debug("Distributor: SendError 500");
                    response.sendError(500, "Error to process request:" + request.getRequestURI());
                    return false;
                }
            } else if (dparams.getAccType() == DistributorParams.ACC_TYPE_ACTION) // process action
            {
                try {
                    String rid = dparams.getAccResourceID();
                    String idtm = dparams.getAccResourceTMID();
                    HashMap resp = dparams.getResourceURI(rid);
                    int mto = SWBUtils.TEXT.getInt((String) resp.get(DistributorParams.URLP_METHOD), SWBResourceModes.Call_CONTENT);
                    String mdo = (String) resp.get(DistributorParams.URLP_MODE);
                    String wst = (String) resp.get(DistributorParams.URLP_WINSTATE);
                    String act = (String) resp.get(DistributorParams.URLP_ACTION);
                    String extParams=dparams.getNotAccResourceURI(rid);

                    SWBResource currResource = SWBPortal.getResourceMgr().getResource(idtm, rid);
                    if(currResource==null)
                    {
                        log.warn("Error al procesar el URL:"+request.getRequestURL());
                        response.sendError(404, "No se encontro:" + request.getRequestURI() + "<BR>");
                        return false;
                    }
                    SWBActionResponseImp resParams = new SWBActionResponseImp(response);
                    if (act != null) resParams.setAction(act);
                    resParams.setExtURIParams(extParams);
                    resParams.setCallMethod(mto);
                    if (mdo != null) resParams.setMode(mdo);
                    if (wst != null) resParams.setWindowState(wst);
                    resParams.setResourceBase(currResource.getResourceBase());
                    resParams.setTopic(webpage);
                    if (vwebpage != null) {
                        resParams.setVirtualTopic(vwebpage);
                    }
                    resParams.setOnlyContent(onlyContent);
                    resParams.setUser(user);
                    //resParams.setUserLevel();
                    log.debug("Invoke ProcessAcion");
                    currResource.processAction(request, resParams);
                    log.debug("SendRedirect:"+resParams.toString());
                    response.sendRedirect(resParams.toString());
                    log.debug("Exit Distributor");
                    return true;
                } catch (Throwable e) {
                    log.error(e);
                    log.debug("Distributor: SendError 500");
                    response.sendError(500, "No es posible procesar el requerimiento:" + request.getRequestURI() + "<BR>" + e);
                    return false;
                }
            }

            try//Traer template y comprimir salida..
            {
                TemplateImp currTemplate = (TemplateImp)SWBPortal.getTemplateMgr().getTemplate(user, webpage);
                if(currTemplate==null)
                {
                    log.warn("No se encontro template para la seccion:" + webpage.getId());
                    response.sendError(500, "La pagina " + request.getRequestURI() + " no esta disponible por el momento... ");
                    log.debug("Distributor: SendError 500");
                    return false;
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

                SWBHttpServletResponseWrapper res = new SWBHttpServletResponseWrapper(response);
                try {
                    //System.out.println("DistributorImp->onlyContent:"+onlyContent);
                    PrintWriter out=res.getWriter();
                    //out.println("\n<!--Time: " + (System.currentTimeMillis() - tini) + "ms - " + webpage + "--> ");  //TODO encontrar una forma de configurar esto...
                    //out.println("Muestra Plantilla:"+currTemplate);
                    if (ipfilter == -1) {
                        if (onlyContent) {
                            currTemplate.buildContents(request, res, out, dparams, true, content);
                        } else {
                            currTemplate.build(request, res, out, user, webpage, true, content, dparams);
                        }
                    } else {
                        if (onlyContent) {
                            currTemplate.buildContents(request, res, out, dparams, false, content);
                        } else {
                            currTemplate.build(request, res, out, user, webpage, false, content, dparams);
                        }
                    }
                } catch (Exception e) {
                    log.error("Error al procesar template para la seccion:" + webpage.getId(),e);
                    response.sendError(500, "La pagina " + request.getRequestURI() + " no esta disponible por el momento... ");
                    //if(gzip)garr.close();
                    return false;
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
                out.println("\n<!--Time: " + tfin + "ms - " + webpage + "--> ");  //TODO: encontrar una forma de configurar esto...

                //if (gzip)
                {
                    //garr.close();
                    out.close();
                }
            } catch (Exception e) {
                log.error("Get template and compress output...",e);
                return false;
            }
        } catch (Throwable e) 
        {
            log.error(e);
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
