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

import javax.servlet.http.*;
import java.io.*;


import java.util.*;

import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.base.util.SFBase64;
import org.semanticwb.model.Resource;
import org.semanticwb.model.Template;
import org.semanticwb.model.User;
import org.semanticwb.model.WebPage;
import org.semanticwb.portal.TemplateImp;
import org.semanticwb.portal.lib.SWBResponse;
import org.semanticwb.portal.api.*;
import org.semanticwb.portal.lib.SWBBridgeResponse;
import org.semanticwb.portal.lib.SWBRequest;
import org.semanticwb.portal.util.SWBBridge;
import org.semanticwb.servlet.SWBHttpServletRequestWrapper;
import org.semanticwb.servlet.internal.DistributorParams;



// TODO: Auto-generated Javadoc
/**
 * Esta clase se encarga de desplegar y administrar un servicio SOP
 * 
 * This class displays and manages a SOP service.
 * 
 * @author : Javier Solis
 * @since September 4th 2002, 13:09
 */

public class RemoteWebApp extends GenericAdmResource
{
    
    /** The log. */
    private static Logger log = SWBUtils.getLogger(RemoteWebApp.class);
    
    /** The encryptor. */
    private org.semanticwb.util.Encryptor encryptor = null;

    //****************** login http://host:port/smc/logout.jsp  ********************************//
    
    /**
     * Crea un nuevo objeto SOPResource.
     */
    public RemoteWebApp()
    {
        //pedir llave en la administracion
        byte key[] = new java.math.BigInteger("05fe858d86df4b909a8c87cb8d9ad596", 16).toByteArray();
        encryptor = new org.semanticwb.util.Encryptor(key);
    }

    /**
     * Process action.
     * 
     * @param request the request
     * @param response the response
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws SWBResourceException the sWB resource exception
     */    
    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException
    {
        
    }
    
    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.GenericAdmResource#doView(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.semanticwb.portal.api.SWBParamRequest)
     */
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        doView(request,response,paramRequest,true);
    }
    
    /**
     * Gets the time.
     * 
     * @param iniTime the ini time
     * @return the time
     */
    public String getTime(long iniTime)
    {
        return "<!-- Time: "+(System.currentTimeMillis()-iniTime)+" -->";
    }
    
    /**
     * Do view.
     * 
     * @param request the request
     * @param response the response
     * @param paramRequest the param request
     * @param userIns the user ins
     * @throws SWBResourceException the sWB resource exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest, boolean userIns) throws SWBResourceException, IOException
    {
        long iniTime=System.currentTimeMillis();        
        //PrintWriter ret=response.getWriter();
        WebPage topic=paramRequest.getWebPage();
        User user=paramRequest.getUser();
        Resource base=paramRequest.getResourceBase();
        Map args=paramRequest.getArguments();
        try
        {
            String server=base.getAttribute("server","http://localhost:1081");
            String iniPath=base.getAttribute("iniPath","/");
            String encode=base.getAttribute("encode","utf-8");
            String basePath=base.getAttribute("basePath");
            String replace=base.getAttribute("replace");
            String direct=base.getAttribute("direct");
            String userinsert=base.getAttribute("userinsert");
            String loginurl=base.getAttribute("loginurl");
            String initext=base.getAttribute("initext",null);
            String endtext=base.getAttribute("endtext",null);
            String headers=base.getAttribute("headers");
            //String removePath=base.getAttribute("removePath","http://200.33.31.6;http://172.20.174.55");
            String cookies=base.getAttribute("cookies");
            String instance=base.getAttribute("instance",""+base.getId());
            
            String uri = request.getRequestURI();
            
            server=replaceTags(server, request, paramRequest);
            iniPath=replaceTags(iniPath, request, paramRequest);
            basePath=replaceTags(basePath, request, paramRequest);
            replace=replaceTags(replace, request, paramRequest);
            direct=replaceTags(direct, request, paramRequest);
            userinsert=replaceTags(userinsert, request, paramRequest);
            loginurl=replaceTags(loginurl, request, paramRequest);
            headers=replaceTags(headers, request, paramRequest);
            cookies=replaceTags(cookies, request, paramRequest);
            
            String surl=null;
            String buri=uri;
            if(uri!=null)
            {
            //System.out.println("uri:"+uri);
                int i=uri.indexOf("_url");
                if(i>-1)
                {
                    surl=uri.substring(i+4);
                    buri=uri.substring(0,i+4);
                    //buri=paramsRequest.getRenderUrl().setCallMethod(paramsRequest.Call_DIRECT).toString()+"/_url";
                }
            }
            
            //System.out.println("surl:"+surl);
            
            if(surl==null)
            {
                //response.sendError(500);
                String url=paramRequest.getRenderUrl().setCallMethod(paramRequest.Call_DIRECT).toString()+"/_url"+iniPath;
                if(paramRequest.getResourceBase().getAttribute("firstredirect")==null)
                {
                    //response.getWriter().print("<META HTTP-EQUIV=\"Refresh\" CONTENT=\"0; URL="+url+"\"><script>location.href='"+url+"';</script>");
                    //return;
                    //System.out.println(""+System.currentTimeMillis()+": "+"Send Redirect url:"+url);
                    //response.reset();
                    //response.resetBuffer();
                    response.sendRedirect(url);
                    //throw new TemplateInterruptedException();
                    return;
                }else
                {
                    surl=iniPath;
                    buri=paramRequest.getRenderUrl().setCallMethod(paramRequest.Call_DIRECT).toString()+"/_url";
                }
            }
            //Validacion de espacion en uri
            surl=surl.replaceAll(" ", "%20");
            String remoteURL = server + surl;

            boolean resCall = false;
            if (paramRequest.getCallMethod()==paramRequest.Call_DIRECT)
            {
                resCall = true;
            } else
            {
                resCall = false;
            }
            
            //System.out.println("resCall:"+resCall);

            //System.out.println("remoteURL:"+remoteURL);
            //System.out.println("base.getId():"+base.getId());
            
            if(userinsert!=null && request.getSession(true).getAttribute("WBCookieMgrUI")==null)
            {
//                WBCookieMgr cookiemgr = (WBCookieMgr) request.getSession().getAttribute("WBCookieMgr");
//                if (cookiemgr != null)
//                {
//                    cookiemgr.removeInstanceCookies(instance);
//                }
                   
                request.getSession(true).setAttribute("WBCookieMgrUI", "true");
                //System.out.println(""+System.currentTimeMillis()+": "+"User forbidden...");
                ByteArrayOutputStream aout = new ByteArrayOutputStream(5000);
                SWBBridge abridge = new SWBBridge();
                //if (user.isLoged())
                {
                    addUserAttributes(abridge,paramRequest.getUser());
                }
                //System.out.println(""+System.currentTimeMillis()+": "+"userinsert:"+userinsert);
                SWBBridgeResponse ares = abridge.bridge(userinsert, null, new SWBRequest(request), aout, instance);
                String acode = "" + ares.getResponseCode();                
                if (acode.startsWith("2"))
                {
                     doView(request,response,paramRequest,false);
                }else
                {
                    //bloque de error duplicado
                    String err = "";//"Error:" + acode + " " + ares.getResponseMessage();
                    log.warn("Resource "+getResourceBase().getId()+" Error:" + acode + " " + ares.getResponseMessage());

                    String errm=ares.getErrorMessage();
                    if(errm!=null)err+="\n"+errm;

                    if (resCall)
                    {
                        Template tpl=SWBPortal.getTemplateMgr().getTemplate(user, topic);
                        
                        ((TemplateImp)tpl).build(request, response, response.getWriter(), user, topic, true, err+getTime(iniTime));
                        response.getWriter().write(getTime(iniTime));
                    } else
                    {
                        response.getWriter().print(err);
                    }        
                    //*********
                }
                return;
            }

            ByteArrayOutputStream out = new ByteArrayOutputStream(5000);
            SWBBridge bridge = new SWBBridge();
            bridge.setAcceptEncoding(false);
            //if (user.isLoged())
            {
                addHeaders(bridge,headers);
                addCookies(bridge,cookies);
            }
            //System.out.println(""+System.currentTimeMillis()+": "+"remoteURL:"+remoteURL);
            SWBBridgeResponse res = bridge.bridge(remoteURL, loginurl, request, out, instance);
            String code = "" + res.getResponseCode();

            //System.out.println(""+System.currentTimeMillis()+": "+"getResponseCode:"+code);
            
            if (code.startsWith("3"))
            {
                String redi = res.getHeaderField("Location");
                if (redi != null)
                {
                    //redi = removePaths(removePath,redi);
                    redi = replaceStr(replace,redi);
                    redi = replacePaths(basePath, redi, buri);
                    response.sendRedirect(redi);
                    return;
                } else
                {
                    response.sendError(res.getResponseCode());
                }
            } else if (code.startsWith("2"))
            {
                String contentType = res.getContentType();
                //System.out.println(""+System.currentTimeMillis()+": "+"Content-Type:"+contentType);
                //if (isDirect(direct, remoteURL) || contentType.toLowerCase().equals("text/xml") || contentType.toLowerCase().indexOf("text") == -1)
                boolean isHtml=false;
                if(contentType!=null)
                    isHtml=contentType.toLowerCase().indexOf("text/html")!=-1 || contentType.toLowerCase().indexOf("text/plain")!=-1;
                if (isDirect(direct, remoteURL) || contentType==null || !isHtml )
                {
                    if(contentType!=null && (contentType.toLowerCase().indexOf("text/css")>=0
                                             ||contentType.toLowerCase().indexOf("text/javascript")>=0))
                    {
                        response.setContentType(contentType);
//                        for(int x=0;x<res.getHeaderSize();x++)
//                        {
//                            response.setHeader(res.getHeaderFieldKey(x+1), res.getHeaderField(x+1));
//                        }                        
                        String content = "";
                        if(encode!=null)
                        {
                            content=out.toString(encode);
                        }else
                        {
                            content=out.toString();
                        }
                        //System.out.println("*********x1\n"+content);
                        content = replaceStr(replace,content);
                        content = replacePaths(basePath, content, buri);
                        //System.out.println("*********x1\n"+content);
                        response.getOutputStream().write(content.getBytes());
                        
                    }else
                    {
                        for(int x=0;x<res.getHeaderSize();x++)
                        {
                            response.setHeader(res.getHeaderFieldKey(x+1), res.getHeaderField(x+1));
                        }
                        response.getOutputStream().write(out.toByteArray());
                    }
                } else
                {
                    response.setContentType(contentType);
                    //System.out.println(""+System.currentTimeMillis()+": before-parse");
                    //String content = out.toString().replaceAll(servletRemote, servletName);
                    String content = "";
                    if(encode!=null)
                    {
                        content=out.toString(encode);
                    }else
                    {
                        content=out.toString();
                    }
                    //content = removePaths(removePath,content);
                    content = cropStr(initext,endtext,content);
                    content = replaceStr(replace,content);
                    content = replacePaths(basePath, content, buri);
                    
                    if(resCall)
                    {
                        Template tpl=SWBPortal.getTemplateMgr().getTemplate(user, topic);
                        
                        DistributorParams distparams=new DistributorParams(request);
                        Iterator pit=distparams.getExtURIParams().iterator();
                        while(pit.hasNext())
                        {
                            String param=(String)pit.next();
                            //System.out.println("param:"+param);
                            if(param.equals("_tpl") && pit.hasNext())
                            {
                                try
                                {
                                    Template t=topic.getWebSite().getTemplate((String)pit.next());
                                    Template aux=SWBPortal.getTemplateMgr().getTemplateImp(t);
                                    if(aux!=null)tpl=aux;
                                    //System.out.println("tpl:"+t+" "+tpl);
                                }catch(Exception e){log.error(e);}
                            }
                        }
                        boolean onlyContent = distparams.isOnlyContent();
                        
                        //other contents
                        //System.out.println(""+System.currentTimeMillis()+": "+"othercont:"+paramsRequest.getResourceBase().getAttribute("othercont", null));
                        if(paramRequest.getResourceBase().getAttribute("othercont", null)!=null)
                        {
                            //System.out.println("othercont:on");
                            StringBuffer rr=new StringBuffer();
                            Iterator it= SWBPortal.getResourceMgr().getContents(user, topic, new HashMap(), tpl);
                            while (it.hasNext())
                            {
                                SWBResource wbres = (SWBResource) it.next();
                                if (wbres != this && wbres.getResourceBase().getIndex() > 0)
                                {
                                    String rid=wbres.getResourceBase().getId();
                                    String mdo=null;
                                    String wst=null;
                                    String act=null;
                                    WebPage vtopic=null;
                                    //boolean onlyContent = false;
                                    if(distparams!=null)
                                    {
                                        vtopic=distparams.getVirtWebPage();
                                        //onlyContent = distparams.isOnlyContent();
                                        HashMap resp=distparams.getResourceURI(rid);
                                        if(resp!=null && distparams.getResourceTMID(rid).equals(wbres.getResourceBase().getWebSiteId()))
                                        {
                                            mdo=(String)resp.get(distparams.URLP_MODE);
                                            wst=(String)resp.get(distparams.URLP_WINSTATE);
                                            act=(String)resp.get(distparams.URLP_ACTION);
                                        }
                                    }

                                    //System.out.println("rid:"+rid);
                                    //System.out.println("distparams.getAccResourceID():"+distparams.getAccResourceID());

                                    SWBResponse res2=new SWBResponse(response);
                                    javax.servlet.http.HttpServletRequest req=request;
                                    if(distparams!=null && !distparams.getAccResourceID().equals("0") && !rid.equals(distparams.getAccResourceID()))
                                    {
                                        req=new SWBHttpServletRequestWrapper(request,user.getLanguage(),topic.getWebSiteId(),true);
                                    }else
                                    {
                                        req=new SWBHttpServletRequestWrapper(request,user.getLanguage(),topic.getWebSiteId(),false);
                                    }
                                    SWBParamRequestImp resParams = new SWBParamRequestImp(req,wbres.getResourceBase(),topic,user);
                                    resParams.setArguments(args);
                                    resParams.setCallMethod(resParams.Call_CONTENT);
                                    if(act!=null)resParams.setAction(act);
                                    if(mdo!=null)resParams.setMode(mdo);
                                    if(wst!=null)resParams.setWindowState(wst);                                        
                                    if(vtopic!=null)
                                    {
                                        resParams.setVirtualTopic(vtopic);
                                    }
                                    resParams.setOnlyContent(onlyContent);

                                    SWBPortal.getResourceMgr().getResourceTraceMgr().renderTraced(wbres, req, res2, resParams);

                                    String intraBR=(String)args.get("intrabr");
                                    if(intraBR==null || intraBR.equalsIgnoreCase("true") && rr.length()>0)
                                    {
                                        rr.append("<BR>");
                                    }
                                    //System.out.println("add content 2");
                                    rr.append(res2.toString());
                                }else if(wbres == this)
                                {
                                    //System.out.println("add content 1");
                                    String intraBR=(String)args.get("intrabr");
                                    if(intraBR==null || intraBR.equalsIgnoreCase("true") && rr.length()>0)
                                    {
                                        rr.append("<BR>");
                                    }
                                    
                                    rr.append(content);
                                }
                            }
                            content=rr.toString();
                        }                        
                        if(onlyContent)
                        {
                            ((TemplateImp)tpl).buildContents(request, response, response.getWriter(), distparams, true, content+getTime(iniTime));
                            response.getWriter().write(getTime(iniTime));
                        }else
                        {
                            ((TemplateImp)tpl).build(request, response, response.getWriter(), user, topic, true, content+getTime(iniTime),distparams);
                            response.getWriter().write(getTime(iniTime));
                        }
                    } else
                    {
                        response.getWriter().print(content);
                    }
                }
            } else if(userIns && code.startsWith("403") && userinsert!=null)
            {
//                WBCookieMgr cookiemgr = (WBCookieMgr) request.getSession().getAttribute("WBCookieMgr");
//                if (cookiemgr != null)
//                {
//                    cookiemgr.removeInstanceCookies(instance);
//                }
                //System.out.println("User forbidden...");
                ByteArrayOutputStream aout = new ByteArrayOutputStream(5000);
                SWBBridge abridge = new SWBBridge();
                //if (user.isLoged())
                {
                    addUserAttributes(abridge,paramRequest.getUser());
                }
                //System.out.println("userinsert:"+userinsert);
                SWBBridgeResponse ares = abridge.bridge(userinsert, null, new SWBRequest(request), aout, instance);
                String acode = "" + ares.getResponseCode();                
                if (acode.startsWith("2"))
                {
                    if("POST".equals(request.getMethod()))
                    {
                        //response.sendRedirect(request.getRequestURI().toString());
                        String url=paramRequest.getRenderUrl().setCallMethod(paramRequest.Call_DIRECT).toString()+"/_url"+iniPath;
                        response.sendRedirect(url);
                    }else
                    {
                        doView(request,response,paramRequest,false);
                    }
                    return;
                }else
                {
                    //bloque de error duplicado
                    String err = "";//"Error:" + acode + " " + ares.getResponseMessage();
                    log.warn("Resource "+getResourceBase().getId()+" Error:" + acode + " " + ares.getResponseMessage());

                    String errm=ares.getErrorMessage();
                    if(errm!=null)err+="\n"+errm;

                    if (resCall)
                    {
                        Template tpl = SWBPortal.getTemplateMgr().getTemplate(user, topic);
                        ((TemplateImp)tpl).build(request, response, response.getWriter(), user, topic, true, err+getTime(iniTime));
                        response.getWriter().write(getTime(iniTime));
                    } else
                    {
                        response.getWriter().print(err);
                    }        
                    //*********
                }
                
            }else
            {
                //bloque de error duplicado
                String err = "";//"Error:" + code + " " + res.getResponseMessage();
                log.warn("Resource "+getResourceBase().getId()+" Error:" + code + " " + res.getResponseMessage());

                String errm=res.getErrorMessage();
                if(errm!=null)err+="\n"+errm;
                
                if (resCall)
                {
                    Template tpl = SWBPortal.getTemplateMgr().getTemplate(user, topic);
                    ((TemplateImp)tpl).build(request, response, response.getWriter(), user, topic, true, err+getTime(iniTime));
                    response.getWriter().write(getTime(iniTime));
                } else
                {
                    response.getWriter().print(err);
                }
                //*********
            }
        } catch (Exception e)
        {
            log.error("Error al traer busqueda del servidor" + "...", e);
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
    public String replaceTags(String str, HttpServletRequest request, SWBParamRequest paramRequest)
    {
        //System.out.print("\nstr:"+str+"-->");
        if(str==null || str.trim().length()==0)return null;
        str=str.trim();
        //TODO: codificar cualquier atributo o texto
        Iterator it=SWBUtils.TEXT.findInterStr(str, "{encode(\"", "\")}");
        while(it.hasNext())
        {
            String s=(String)it.next();
            str=SWBUtils.TEXT.replaceAll(str, "{encode(\""+s+"\")}", encryptor.encode(replaceTags(s,request,paramRequest)));
        }

        it=SWBUtils.TEXT.findInterStr(str, "{encodeB64(\"", "\")}");
        while(it.hasNext())
        {
            String s=(String)it.next();
            str=SWBUtils.TEXT.replaceAll(str, "{encodeB64(\""+s+"\")}", SFBase64.encodeString(replaceTags(s,request,paramRequest)));
        }
        
        it=SWBUtils.TEXT.findInterStr(str, "{request.getParameter(\"", "\")}");
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
        //System.out.println(str);
        return str;
    }
    
    
    /**
     * Crop str.
     * 
     * @param initext the initext
     * @param endtext the endtext
     * @param content the content
     * @return the string
     */
    public String cropStr(String initext, String endtext, String content)
    {
        if(initext==null && endtext==null)return content;
        //ini
        if(initext!=null)
        {
            StringTokenizer st = new StringTokenizer(initext, ";,");
            while (st.hasMoreTokens())
            {
                String a1;
                String a2;
                String wp = st.nextToken();
                int i=wp.indexOf("|");
                if(i>-1)
                {
                    a1=wp.substring(0,i);
                    a2=wp.substring(i+1);
                }else
                {
                    a1=wp;
                    a2="";
                }
                int ini=content.indexOf(a1);
                if(ini>=0)
                {
                    content=a2+content.substring(ini+a1.length());
                    //break;
                }
            }    
        }
        //end
        if(endtext!=null)
        {
            StringTokenizer st = new StringTokenizer(endtext, ";,");
            while (st.hasMoreTokens())
            {
                String a1;
                String a2;
                String wp = st.nextToken();
                int i=wp.indexOf("|");
                if(i>-1)
                {
                    a1=wp.substring(0,i);
                    a2=wp.substring(i+1);
                }else
                {
                    a1=wp;
                    a2="";
                }
                int end=content.indexOf(a1);
                if(end>=0)
                {
                    content=content.substring(0,end)+a2;
                    //break;
                }
            }    
        }        
        return content;
    }       
    
    /**
     * Replace str.
     * 
     * @param replace the replace
     * @param content the content
     * @return the string
     */
    public String replaceStr(String replace, String content)
    {
        if(replace==null)return content;
        StringTokenizer st = new StringTokenizer(replace, ";,");
        while (st.hasMoreTokens())
        {
            String wp = st.nextToken();
            int i=wp.indexOf("|");
            if(i>-1)
            {
                String a1=wp.substring(0,i);
                String a2=wp.substring(i+1);
                content = content.replaceAll(a1, a2);
            }else
            {
                content = content.replaceAll(wp, "");
            }
        }    
        return content;
    }    
    
    /**
     * Checks if is direct.
     * 
     * @param direct the direct
     * @param url the url
     * @return true, if is direct
     */
    public boolean isDirect(String direct, String url)
    {
        if(direct==null)return false;
        StringTokenizer st = new StringTokenizer(direct, ";,");
        while (st.hasMoreTokens())
        {
            String wp = st.nextToken();
            if(url.indexOf(wp)>-1)return true;
        }    
        return false;
    }      
    
    /**
     * Replace paths.
     * 
     * @param basePath the base path
     * @param content the content
     * @param buri the buri
     * @return the string
     */
    public String replacePaths(String basePath, String content, String buri)
    {
//        if(basePath==null || buri==null)return content;
//        StringTokenizer st = new StringTokenizer(basePath, ";,");
//        while (st.hasMoreTokens())
//        {
//            String wp = st.nextToken();
//            content = content.replaceAll(wp, buri + wp);
//        }    
//        return content;
        
        if(basePath==null || buri==null)return content;
        
        int i=-1;
        String match=getCloser(content, 0, basePath);
        if(match!=null)i=content.indexOf(match);
        int y=0;
        while(i>=0)
        {
            content=content.substring(0,i)+buri+content.substring(i);
            y=i+buri.length()+match.length();
            match=getCloser(content, y, basePath);
            if(match!=null)i=content.indexOf(match,y);
            else i=-1;
        }
        return content;        
        
    }
    
    /**
     * Gets the closer.
     * 
     * @param content the content
     * @param off the off
     * @param basePath the base path
     * @return the closer
     */
    private String getCloser(String content, int off, String basePath)
    {
        String ret=null;
        int y=-1;
        StringTokenizer st = new StringTokenizer(basePath, ";,");
        while (st.hasMoreTokens())
        {
            String match = st.nextToken();
            int i=content.indexOf(match,off);
            if(i>-1 && (y==-1 || i<y))
            {
                y=i;
                ret=match;
            }
        }
        return ret;
    }
    
    /**
     * Removes the paths.
     * 
     * @param removePath the remove path
     * @param content the content
     * @return the string
     */
    public String removePaths(String removePath, String content)
    {
        if(removePath==null)return content;
        StringTokenizer st = new StringTokenizer(removePath, ";,");
        while (st.hasMoreTokens())
        {
            String wp = st.nextToken();
            content = content.replaceAll(wp, "");
        }    
        return content;
    }    
    
    
    /**
     * Adds the cookies.
     * 
     * @param bridge the bridge
     * @param cookies the cookies
     */
    public void addCookies(SWBBridge bridge, String cookies)
    {
        if(cookies==null)return;
        StringTokenizer st = new StringTokenizer(cookies, ";,");
        while (st.hasMoreTokens())
        {
            String cookie = st.nextToken();
            if(cookie.trim().length()>0)
            {
                bridge.addCookie(cookie);
            }
        }    
    }     
    
    /**
     * Adds the headers.
     * 
     * @param bridge the bridge
     * @param headers the headers
     */
    public void addHeaders(SWBBridge bridge, String headers)
    {
        if(headers==null)return;
        StringTokenizer st = new StringTokenizer(headers, ";,");
        while (st.hasMoreTokens())
        {
            String header = st.nextToken();
            int i=header.indexOf("=");
            if(i>0)
            {
                String name=header.substring(0,i);
                String value=header.substring(i+1);
                bridge.addHeader(name, value);
            }
        }    
    }      
    
    /**
     * Adds the user attributes.
     * 
     * @param bridge the bridge
     * @param user the user
     */
    public void addUserAttributes(SWBBridge bridge, User user)
    {/* TODO:VER 4
        HashMap map=new HashMap();
        Enumeration en=user.getPropertyNames();
        while(en.hasMoreElements())
        {
            String name=(String)en.nextElement();
            String value=user.getAttribute(name);
            if(name!=null && value!=null)
            {
                map.put(name,value);
            }
        }
        String roles="";
        Iterator it=user.getRoles();
        while(it.hasNext())
        {
            int roleid=((Integer)it.next()).intValue();
            RecRole role=DBRole.getInstance().getRole(roleid, user.getRepository());
            roles+="|"+role.getName();
        }
        if(roles.length()>0)
        {
            roles=roles.substring(1);
            map.put("ROLES",roles);
        }
        
        try
        {
//            ByteArrayOutputStream f = new ByteArrayOutputStream();
//            ObjectOutput s = new ObjectOutputStream(f);
//            s.writeObject(map);
//            s.flush();
//            s.close();       
//            bridge.setPostData(f.toByteArray());
            bridge.addGetParameter("obj", AFUtils.encodeObject(map));
        }catch(Exception e){AFUtils.log(e);}
      * */
    }
    
    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.GenericResource#getResourceCacheID(javax.servlet.http.HttpServletRequest, org.semanticwb.portal.api.SWBParamRequest)
     */
    public String getResourceCacheID(HttpServletRequest request, SWBParamRequest paramRequest) throws SWBResourceException
    {
        String retValue=null;
        if (paramRequest.getCallMethod()!=paramRequest.Call_DIRECT)
        {
            retValue = super.getResourceCacheID(request, paramRequest);
        }
        return retValue;
    }

}
