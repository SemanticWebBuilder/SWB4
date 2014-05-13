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
package org.semanticwb.servlet;

//import com.infotec.wb.core.WBVirtualHostMgr;
import java.io.*;
import java.util.StringTokenizer;
import java.util.concurrent.ConcurrentHashMap;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.*;
import net.fckeditor.connector.SWBConnectorServlet;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.FriendlyURL;
import org.semanticwb.model.Proxy;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.User;
import org.semanticwb.model.WebSite;
import org.semanticwb.portal.SWBIPValidationExeption;
import org.semanticwb.servlet.internal.Admin;
import org.semanticwb.servlet.internal.Distributor;
import org.semanticwb.servlet.internal.DistributorParams;
import org.semanticwb.servlet.internal.EditFile;
import org.semanticwb.servlet.internal.FrmProcess;
import org.semanticwb.servlet.internal.GoogleSiteMap;
import org.semanticwb.servlet.internal.InternalProxy;
import org.semanticwb.servlet.internal.InternalServlet;
import org.semanticwb.servlet.internal.LinkedData;
import org.semanticwb.servlet.internal.Login;
import org.semanticwb.servlet.internal.LongFileUploader;
import org.semanticwb.servlet.internal.Monitor;
import org.semanticwb.servlet.internal.MultipleFileUploader;
import org.semanticwb.servlet.internal.P3PFile;
import org.semanticwb.servlet.internal.RobotFile;
import org.semanticwb.servlet.internal.ShowFile;
import org.semanticwb.servlet.internal.TreeSelectFormElement;
import org.semanticwb.servlet.internal.Work;

/* MAPS74 Dynamic Tracing - Requires Java SDK 7.0 y Solaris* /
import com.sun.tracing.ProviderFactory;
import java.util.concurrent.ConcurrentHashMap;
import org.semanticwb.tracing.SWBProvider;
/* */


// TODO: Auto-generated Javadoc
/**
 * The Class SWBVirtualHostFilter.
 * 
 * @author  Javier Solis Gonzalez (jsolis@infotec.com.mx)
 */
public class SWBVirtualHostFilter implements Filter
{
    /* MAPS74 Dynamic Tracing - Requires Java SDK 7.0 y Solaris* /
    public static SWBProvider provider;
    
    static {
        ProviderFactory factory = ProviderFactory.getDefaultFactory();
        System.out.println("************ Provider: "+provider+ " *******************************");
        System.out.println("SWBProvider: "+ SWBProvider.class);
        provider = factory.createProvider(SWBProvider.class);
        System.out.println("************ Provider: "+provider+ " *******************************");
    }
    /* */
    
    /** The log. */
    static Logger log = SWBUtils.getLogger(SWBVirtualHostFilter.class);
    
    static SWBVirtualHostFilter instance=null;
    
    /** The swb portal. */
    private SWBPortal swbPortal = null;

    private SWBPlatform swbPlatform = null;
    
    /** The int servlets. */
    private ConcurrentHashMap<String, InternalServlet> intServlets = new ConcurrentHashMap<String, InternalServlet>();
    
    /** The dist. */
    private InternalServlet dist=null;
    
    /** The login internal servlet. */
    private Login loginInternalServlet = new Login();
    // The filter configuration object we are associated with.  If
    // this value is null, this filter instance is not currently
    // configured.
    /** The filter config. */
    private FilterConfig filterConfig = null;
    
    /** The fist call. */
    private boolean fistCall = true;
    
    private ConcurrentHashMap<String,String> depurls = null;

    /**
     * Do filter.
     * 
     * @param request The servlet request we are processing
     * @param response the response
     * @param chain The filter chain we are processing
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws ServletException the servlet exception
     * @exception IOException if an input/output error occurs
     * @exception ServletException if a servlet error occurs
     */
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException
    {
        boolean processThread = false;
        try
        {
            HttpServletRequest _request = new SWBBaseHttpServletRequestWrapper((HttpServletRequest) request);
            HttpServletResponse _response = (HttpServletResponse) response;
            if(log.isTraceEnabled())log.trace("VirtualHostFilter:doFilter()");

            String lang=null;
            String country=null;

            if (fistCall)
            {
                synchronized(this)
                {
                    if (fistCall)
                    {
                        fistCall = false;
                        swbPortal.setContextPath(_request.getContextPath());
                    }
                }
            }

            String uri = _request.getRequestURI();
            String cntx = _request.getContextPath();
            String path = uri.substring(cntx.length());
            String host = _request.getServerName();
            String iserv = "";
            String iservnext=null;

            Proxy proxy=Proxy.getProxy(host);
            if(proxy!=null)
            {
                User user=SWBPortal.getUserMgr().getUser(_request, (WebSite)proxy.getSemanticObject().getModel().getModelObject().createGenericInstance());
                if(user.haveAccess(proxy))
                {
                    InternalProxy p=new InternalProxy(proxy);
                    if(p.doProcess(_request, _response))
                    {
                        return;
                    }
                }else
                {
                    _response.sendError(403, "No tiene permiso para accesar a la página " + _request.getRequestURI() + "... ");
                    if(log.isDebugEnabled())log.debug("Distributor: SendError 403");                
                    return;
                }
            }
            
            //Filtros
            boolean filter=false;
            if(SWBPortal.isClient())
            {
                boolean adminShow = !SWBPlatform.getEnv("swb/administration","true").equals("false");
                if(!adminShow)
                {
                    if(path.startsWith("/swbadmin"))
                    {
                        if(path.endsWith(".jsp"))
                        {
                            filter=true;
                        }
                        else if(path.endsWith(".xslt"))
                        {
                            filter=true;
                        }
                        else if(path.endsWith(".nt"))
                        {
                            filter=true;
                        }
                        if(path.endsWith("securecode.jsp"))
                        {
                            filter=false;
                        }
                    }
                    if(path.endsWith("/config/login.html"))
                    {
                        filter=true;
                    }
                }
            }
            if(filter)
            {
                _response.sendError(404);
                return;
            }

            if (path == null || path.length() == 0)
            {
                path = "/";
            }
            else
            {
                int j = path.indexOf('/', 1);
                if (j > 0)
                {
                    iserv = path.substring(1, j);
                    iservnext = path.substring(j+1);
                }
                else
                {
                    iserv = path.substring(1);
                }
            }

            if(path.equals("/") && swbPlatform.getEnv("swb/overrideRootIndex","true").equals("true"))
            {
                path="/swb";
                iserv="swb";
            }

    //        log.trace("uri:"+uri);
    //        log.trace("cntx:"+cntx);
    //        log.trace("path:"+path);
    //        log.trace("host:"+host);
    //        log.trace("iserv:"+iserv);

            //System.out.println("uri:"+uri);
            //System.out.println("cntx:"+cntx);
            //System.out.println("path:"+path);
            //System.out.println("host:"+host);
            //System.out.println("iserv:"+iserv);

            boolean isjsp = false;
            InternalServlet serv = intServlets.get(iserv);
            if (serv != null && (path.endsWith(".jsp") || path.endsWith(".groovy")))
            {
                serv = null;
                isjsp = true;
            }

            //Friendly URLs
            if((serv==null || iserv.equals("wb")) && !isjsp)
            {   
                if(depurls!=null)
                {
                    String redpath=depurls.get(path);
                    if(redpath!=null)
                    {
                        _response.sendRedirect(redpath);
                        return;
                    }                              
                }
                FriendlyURL url=FriendlyURL.getFriendlyURL(path, host);                
                if(url!=null)
                {
                    if(!url.isOldURL())
                    {
                        path=url.getWebPage().getRealUrl(null,null);
                        iserv="swb";
                        serv = intServlets.get(iserv);
                        lang=url.getLanguage()!=null?url.getLanguage().getId():null;
                        country=url.getCountry()!=null?url.getCountry().getId():null;
                    }else
                    {
                        _response.sendRedirect(url.getWebPage().getUrl());
                        return;
                    }
                }
            }

            //verifica lenguaje en URI
            if(iserv!=null && (iserv.length()==2 || (iserv.length()==5 && iserv.charAt(2)=='_')))
            {
                serv=dist;
                if(iserv.length()==2 && !iserv.equals("wb"))
                {
                    lang = iserv;
                }else if(!iserv.equals("wb"))
                {
                    lang=iserv.substring(0,2);
                    country=iserv.substring(3);
                }
                if(iservnext!=null)serv = intServlets.get(iservnext);
                if(serv==null)serv=dist;
            }

    //        String real=WBVirtualHostMgr.getInstance().getVirtualHost(path,host);
    //        
    //        if(real!=null)
    //        {
    //            real=real.substring(cntx.length());
    //            AFUtils.debug("Path:"+path+", Real:"+real);
    //            RequestDispatcher rd = filterConfig.getServletContext().getRequestDispatcher(real);
    //            rd.forward(request, response);
    //            return;
    //        }

            Throwable problem = null;
            try
            {
                if (serv != null)
                {
                        /* MAPS74 Dynamic Tracing - Requires Java SDK 7.0  y Solaris* /
                        provider.processInternalServlet(serv.toString());
                        /* */

                    processThread=true;
                    processInternalServlet(serv, _request, _response, path, lang, country, iserv);
                }
                else
                {
                    if (isjsp)
                    {
                        processThread=true;
                        User user=null;
                        if(path.startsWith("/swbadmin"))
                        {
                            user = SWBPortal.getUserMgr().getUser(_request, SWBContext.getAdminWebSite());
                        }else
                        {
                            user = SWBPortal.getUserMgr().getUser(_request, SWBContext.getGlobalWebSite());
                        }
                        SWBContext.setSessionUser(user);
                    }
                    chain.doFilter(_request, response);
                }
            }
            catch (Throwable t)
            {
                problem = t;
            }
            //
            // If there was a problem, we want to rethrow it if it is
            // a known type, otherwise log it.
            //
            if (problem != null)
            {
                if (problem instanceof ServletException)
                {
                    throw (ServletException) problem;
                }
                if (problem instanceof IOException)
                {
                    throw (IOException) problem;
                }
                log.error(problem);
            }
        }finally
        {
            if(processThread)swbPlatform.endThreadRequest();
        }
    }


    public void processInternalServlet(InternalServlet serv, HttpServletRequest _request, HttpServletResponse _response, String path, String lang, String country, String iserv) throws Throwable
    {
        boolean catchErrors = true;

        if (validateDB(_response))
        {
            String auri = path.substring(iserv.length() + 1);
            DistributorParams dparams = null;
            if (!(serv instanceof Admin))
            {
                try
                {
                    dparams = new DistributorParams(_request, auri, lang, country);
                }catch(SWBIPValidationExeption e)
                {
                    log.warn(e);
                    _response.sendError(403, e.getMessage());
                    return;
                }
            }
            if (catchErrors && serv instanceof Distributor)
            {
                SWBHttpServletResponseWrapper resp = new SWBHttpServletResponseWrapper(_response);
                resp.setTrapResponse(false);
                resp.setTrapContentType(false);
                resp.setTrapSendError(true);
                serv.doProcess(_request, resp, dparams);
                if (resp.isSendError())
                {
                    switch (resp.getError())
                    {                       
                        case 500:
                            log.error("Attempt to process an HTTP-500: "+path + " - " + resp.getError() + ":" + resp.getErrorMsg());
                        case 404:
                            processError(resp.getError(), resp.getErrorMsg(), _response, dparams);
                            log.warn(path + " - " + resp.getError() + ":" + resp.getErrorMsg());
                            break;
                        case 403:
                            loginInternalServlet.doProcess(_request, _response, dparams);
                            break;
                        default:
                            log.warn(path + " - " + resp.getError() + ":" + resp.getErrorMsg());
                            _response.sendError(resp.getError(), resp.getErrorMsg());
                    }
                }
//                else
//                {                    
//                    _response.getOutputStream().write(resp.toByteArray());
//                }
            }
            else
            {
                serv.doProcess(_request, _response, dparams);
            }
        }
    }

    /**
     * Destroy method for this filter.
     */
    public void destroy()
    {
        System.out.println("Stopping SWB...");
        SWBPlatform.getSemanticMgr().close();
    }

    /**
     * Init method for this filter.
     * 
     * @param filterConfig the filter config
     * @throws ServletException the servlet exception
     */
    public void init(FilterConfig filterConfig) throws ServletException
    {
        instance=this;
        try
        {
            log.event("************************************");
            log.event("Initializing SemanticWebBuilder...");
            this.filterConfig = filterConfig;
            if (filterConfig != null)
            {
                log.event("Initializing VirtualHostFilter...");
                String prefix = filterConfig.getServletContext().getRealPath("/");
                SWBUtils.createInstance(prefix);
                swbPortal = SWBPortal.createInstance(filterConfig.getServletContext(),this);
                swbPlatform = SWBPlatform.createInstance();
            }

            InternalServlet serv = new Distributor();
            intServlets.put("swb", serv);
            intServlets.put("wb", serv);
            intServlets.put("wb2", serv);
            serv.init(filterConfig.getServletContext());
            dist=serv;

            InternalServlet login = new Login();
            intServlets.put("login", login);
            login.init(filterConfig.getServletContext());
            loginInternalServlet.init(filterConfig.getServletContext());
            loginInternalServlet.setHandleError(true);

            try
            {
                Class gtwOfficeClass = Class.forName("org.semanticwb.servlet.internal.GateWayOffice");
                InternalServlet gtwOffice = (InternalServlet) gtwOfficeClass.newInstance();
                intServlets.put("gtw", gtwOffice);
                gtwOffice.init(filterConfig.getServletContext());
            }
            catch (Exception cnfe)
            {
                log.error(cnfe);
            }
            InternalServlet treeSelectFormElement = new TreeSelectFormElement();
            intServlets.put("treeSelect", treeSelectFormElement);
            treeSelectFormElement.init(filterConfig.getServletContext());

            try
            {
                InternalServlet monitor = new Monitor();
                intServlets.put("swbmonitor.ssl", monitor);
                intServlets.put("ping", monitor);
                monitor.init(filterConfig.getServletContext());
            }catch(Exception e){log.error(e);}            
            
            
            //InternalServlet upload = new Upload();
            //intServlets.put("wbupload", upload);
            //upload.init(filterConfig.getServletContext());

            InternalServlet editFile = new EditFile();
            intServlets.put("editfile", editFile);
            editFile.init(filterConfig.getServletContext());
            
            InternalServlet showFile = new ShowFile();
            intServlets.put("showfile", showFile);
            showFile.init(filterConfig.getServletContext());            

//            InternalServlet UploadFormElement = new UploadFormElement();
//            intServlets.put("Upload", UploadFormElement);
//            UploadFormElement.init(filterConfig.getServletContext());

            if(SWBPortal.getWorkPathType().equals(SWBPortal.WORKPATH_ABSOLUTE))
            {
                InternalServlet work = new Work();
                intServlets.put("work", work);
                work.init(filterConfig.getServletContext());
            }
            
            InternalServlet FCKEditorConnector = new SWBConnectorServlet();
            intServlets.put("FCKEditorConnector", FCKEditorConnector);
            FCKEditorConnector.init(filterConfig.getServletContext());

            InternalServlet frmprocess = new FrmProcess();
            intServlets.put("frmprocess", frmprocess);
            frmprocess.init(filterConfig.getServletContext());

            //TODO:Admin servlet
            InternalServlet admin = new Admin();
            intServlets.put("wbadmin", admin);
            intServlets.put("swbadmin", admin);
            admin.init(filterConfig.getServletContext());

            InternalServlet googleMap = new GoogleSiteMap();
            intServlets.put("sitemap.txt", googleMap);
            intServlets.put("sitemap.xml", googleMap);
            googleMap.init(filterConfig.getServletContext());

            InternalServlet fileUploader = new MultipleFileUploader();
            intServlets.put("multiuploader", fileUploader);
            fileUploader.init(filterConfig.getServletContext());
            
            //Test Service
//            InternalServlet longfileUploader = new LongFileUploader();
//            intServlets.put("bduplaoder", longfileUploader);
//            longfileUploader.init(filterConfig.getServletContext());

            InternalServlet robot = new RobotFile();
            intServlets.put("robots.txt", robot);
            robot.init(filterConfig.getServletContext());

            InternalServlet p3p = new P3PFile();
            intServlets.put("w3c", p3p);
            p3p.init(filterConfig.getServletContext());
            
            InternalServlet linkedData = new LinkedData();
            intServlets.put("resource", linkedData);
            linkedData.init(filterConfig.getServletContext());            

            File file = new File(SWBUtils.getApplicationPath() + "/WEB-INF/depurls.txt");
            if (file.exists())
            {
                depurls = new ConcurrentHashMap<String, String>();
                log.event("Loading Deprecated Urls...");
                try
                {
                    FileInputStream in = new FileInputStream(file);
                    DataInputStream din = new DataInputStream(in);
                    String line = null;
                    while ((line = din.readLine()) != null)
                    {
                        line=line.trim();
                        if(line.length()>0)
                        {
                            StringTokenizer st = new StringTokenizer(line, "|,\t");
                            while (st.hasMoreTokens())
                            {
                                String tk1 = st.nextToken().trim();
                                String tk2 = st.nextToken().trim();
                                if(!tk1.equals(tk2))
                                {
                                    depurls.put(tk1, tk2);
                                }
                            }
                        }
                    }
                    din.close();
                    in.close();
                } catch (Exception e)
                {
                    depurls = null;
                    log.error("Error loading Deprecated Urls...", e);
                }
            }

            log.event("SemanticWebBuilder started...");
            log.event("************************************");

        }catch(Throwable e)
        {
            e.printStackTrace();
            log.error("Error initializing SemanticWebBuilder...",e);
        }

    }

    /**
     * Return a String representation of this object.
     * 
     * @return the string
     */
    public String toString()
    {

        if (filterConfig == null)
        {
            return ("VirtualHostFilter()");
        }
        StringBuffer sb = new StringBuffer("VirtualHostFilter(");
        sb.append(filterConfig);
        sb.append(")");
        return (sb.toString());

    }

    /**
     * Validate db.
     * 
     * @param response the response
     * @return true, if successful
     * @throws IOException Signals that an I/O exception has occurred.
     */
    private boolean validateDB(HttpServletResponse response) throws IOException
    {
        boolean ret = true;
        if (!SWBPortal.haveDB())
        {
            log.debug("SendError 500: Default SemanticWebBuilder database not found...");
            response.sendError(500, "Default WebBuilder database not found...");
            ret = false;
        }
        if (!SWBPortal.haveDBTables())
        {
            log.debug("SendError 500: Default SemanticWebBuilder database tables not found...");
            response.sendError(500, "Default WebBuilder database tables not found...");
            ret = false;
        }
        return ret;
    }

    /**
     * Process error.
     *
     * @param err the err
     * @param errMsg the err msg
     * @param response the response
     * @param dparams the dparams
     * @throws ServletException the servlet exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void processError(int err, String errMsg, HttpServletResponse response, DistributorParams dparams)
            throws ServletException, IOException
    {
        String modelid=dparams.getModelId();
        if(log.isDebugEnabled())log.debug("SendError " + err + ": " + errMsg);
        String path = "/config/" + err;
        String msg = null;
        try
        {
            //System.out.println("modelid:"+modelid);
            if(modelid!=null)
            {
                msg = SWBUtils.IO.getFileFromPath(SWBPortal.getWorkPath() + "/models/"+modelid + path + "_"+dparams.getUser().getLanguage()+".html");
                if(msg==null)msg = SWBUtils.IO.getFileFromPath(SWBPortal.getWorkPath() + "/models/"+modelid + path + ".html");
                if(msg!=null)
                {
                    msg = SWBPortal.UTIL.parseHTML(msg, SWBPortal.getWebWorkPath() + "/models/"+modelid+"/config/images/");
                }
            }
            if(msg==null)
            {
                msg = SWBUtils.IO.getFileFromPath(SWBPortal.getWorkPath() + path + "_" + dparams.getUser().getLanguage() + ".html");
                if(msg==null)msg = SWBUtils.IO.getFileFromPath(SWBPortal.getWorkPath() + path + ".html");
                if(msg!=null)
                {
                    msg = SWBPortal.UTIL.parseHTML(msg, SWBPortal.getWebWorkPath() + "/config/images/");
                }
            }

            if(msg==null)msg="";
            //System.out.println("msg:"+msg);
        }
        catch (Exception e)
        {
            log.error("Error lo load error message...", e);
        }
        response.setStatus(err);
        response.setContentType("text/html");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        OutputStream out = response.getOutputStream();
        out.write(msg.getBytes());
        //out.flush();
        //out.close();
    }
    
    public static SWBVirtualHostFilter getInstance()
    {
        return instance;
    }

    /**
     * Adds the mapping.
     * 
     * @param map the map
     * @param iServlet the i servlet
     */
    public void addMapping(String map, InternalServlet iServlet) throws ServletException
    {
        if (!intServlets.containsKey(map) && iServlet!=null)
        intServlets.put(map, iServlet);
        iServlet.init(filterConfig.getServletContext());
    }
}
