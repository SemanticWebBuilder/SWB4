package org.semanticwb.servlet;

//import com.infotec.wb.core.WBVirtualHostMgr;
import java.io.*;
import java.util.HashMap;
import javax.servlet.http.*;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import org.semanticwb.SWBPlatform;
import org.semanticwb.Logger;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.User;
import org.semanticwb.servlet.internal.Admin;
import org.semanticwb.servlet.internal.Distributor;
import org.semanticwb.servlet.internal.DistributorParams;
import org.semanticwb.servlet.internal.EditFile;
import org.semanticwb.servlet.internal.GateWayOffice;
import org.semanticwb.servlet.internal.InternalServlet;
import org.semanticwb.servlet.internal.Login;
import org.semanticwb.servlet.internal.Upload;
import org.semanticwb.servlet.internal.UploadFormElement;

/**
 *
 * @author  Javier Solis Gonzalez (jsolis@infotec.com.mx)
 */
public class SWBVirtualHostFilter implements Filter
{

    static Logger log = SWBUtils.getLogger(SWBVirtualHostFilter.class);
    private SWBPlatform swbPlatform = null;
    private HashMap<String, InternalServlet> intServlets = new HashMap();
    private Login loginInternalServlet = new Login();
    // The filter configuration object we are associated with.  If
    // this value is null, this filter instance is not currently
    // configured.
    private FilterConfig filterConfig = null;
    private boolean fistCall = true;

    /**
     *
     * @param request The servlet request we are processing
     * @param response 
     * @param chain The filter chain we are processing
     *
     * @exception IOException if an input/output error occurs
     * @exception ServletException if a servlet error occurs
     */
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException
    {
        HttpServletRequest _request = (HttpServletRequest) request;
        HttpServletResponse _response = (HttpServletResponse) response;
        log.trace("VirtualHostFilter:doFilter()");

        boolean catchErrors=true;

        if (fistCall)
        {
            swbPlatform.setContextPath(_request.getContextPath());
            fistCall = false;
        }

        String uri = _request.getRequestURI();
        String cntx = _request.getContextPath();
        String path = uri.substring(cntx.length());
        String host = request.getServerName();
        String iserv = "";

        if (path == null || path.length() == 0)
        {
            path = "/";
        } else
        {
            int j = path.indexOf('/', 1);
            if (j > 0)
            {
                iserv = path.substring(1, j);
            } else
            {
                iserv = path.substring(1);
            }
        }

//        log.trace("uri:"+uri);
//        log.trace("cntx:"+cntx);
//        log.trace("path:"+path);
//        log.trace("host:"+host);
//        log.trace("iserv:"+iserv);
        boolean isjsp = false;
        InternalServlet serv = intServlets.get(iserv);
        if (serv != null && path.endsWith(".jsp"))
        {
            serv = null;
            isjsp = true;
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
                if (validateDB(_response))
                {
                    String auri = path.substring(iserv.length() + 1);
                    DistributorParams dparams = null;
                    if (!(serv instanceof Admin))
                    {
                        dparams = new DistributorParams(_request, auri);
                    }
                    if(catchErrors)
                    {
                        SWBHttpServletResponseWrapper resp = new SWBHttpServletResponseWrapper(_response);
                        resp.setTrapSendError(true);
                        serv.doProcess(_request, resp, dparams);
                        if (resp.isSendError())
                        {
                            switch (resp.getError())
                            {
                                case 500:
                                case 404:
                                    processError(resp.getError(), resp.getErrorMsg(), _response);
                                    log.error(path+" - "+resp.getError()+":"+resp.getErrorMsg());
                                    break;
                                case 403:
                                    loginInternalServlet.doProcess(_request, _response, dparams);
                                    break;
                                default:
                                    _response.sendError(resp.getError(), resp.getErrorMsg());
                            }
                        } else
                        {
                            _response.getOutputStream().write(resp.toByteArray());
                        }
                    }else
                    {
                        serv.doProcess(_request, _response, dparams);
                    }
                }
            } else
            {
                if (isjsp)
                {
                    User user = SWBPortal.getUserMgr().getUser(_request, SWBContext.getGlobalWebSite());
                    SWBPortal.setSessionUser(user);
                }
                chain.doFilter(request, response);
            }
        } catch (Throwable t)
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
    }

    /**
     * Destroy method for this filter
     *
     */
    public void destroy()
    {
    }

    /**
     * Init method for this filter
     *
     * @param filterConfig 
     */
    public void init(FilterConfig filterConfig) throws ServletException
    {
        log.event("************************************");
        log.event("Initializing SemanticWebBuilder...");
        this.filterConfig = filterConfig;
        if (filterConfig != null)
        {
            log.event("Initializing VirtualHostFilter...");
            String prefix = filterConfig.getServletContext().getRealPath("/");
            SWBUtils.createInstance(prefix);
            swbPlatform = SWBPlatform.createInstance(filterConfig.getServletContext());
        }

        InternalServlet serv = new Distributor();
        intServlets.put("swb", serv);
        intServlets.put("wb", serv);
        intServlets.put("wb2", serv);
        serv.init(filterConfig.getServletContext());

        InternalServlet login = new Login();
        intServlets.put("login", login);
        login.init(filterConfig.getServletContext());
        loginInternalServlet.init(filterConfig.getServletContext());
        loginInternalServlet.setHandleError(true);

        InternalServlet gtwOffice = new GateWayOffice();
        intServlets.put("gtw", gtwOffice);
        gtwOffice.init(filterConfig.getServletContext());

        InternalServlet upload = new Upload();
        intServlets.put("wbupload", upload);
        upload.init(filterConfig.getServletContext());

        /*InternalServlet editFile = new EditFile();
        intServlets.put("editfile", editFile);
        editFile.init(filterConfig.getServletContext());

        InternalServlet UploadFormElement = new UploadFormElement();
        intServlets.put("Upload", UploadFormElement);
        UploadFormElement.init(filterConfig.getServletContext());*/

        //TODO:Admin servlet
        InternalServlet admin = new Admin();
        intServlets.put("wbadmin", admin);
        intServlets.put("swbadmin", admin);
        admin.init(filterConfig.getServletContext());
        log.event("SemanticWebBuilder started...");
        log.event("************************************");


    }

    /**
     * Return a String representation of this object.
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

    private boolean validateDB(HttpServletResponse response) throws IOException
    {
        boolean ret = true;
        if (!SWBPlatform.haveDB())
        {
            log.debug("SendError 500: Default SemanticWebBuilder database not found...");
            response.sendError(500, "Default WebBuilder database not found...");
            ret = false;
        }
        if (!SWBPlatform.haveDBTables())
        {
            log.debug("SendError 500: Default SemanticWebBuilder database tables not found...");
            response.sendError(500, "Default WebBuilder database tables not found...");
            ret = false;
        }
        return ret;
    }

    public void processError(int err, String errMsg, HttpServletResponse response)
            throws ServletException, IOException
    {
        log.debug("SendError " + err + ": " + errMsg);
        String path = "/config/" + err + ".html";
        String msg = null;
        try
        {
            //msg = WBUtils.getInstance().getFileFromWorkPath2(path);
            msg = SWBUtils.IO.getFileFromPath(SWBUtils.getApplicationPath() + "/work/" + path);
            //msg = WBUtils.getInstance().parseHTML(msg, WBUtils.getInstance().getWebWorkPath() + "/config/images/");
            msg = SWBPortal.UTIL.parseHTML(msg, SWBPlatform.getWebWorkPath() + "/config/images/");
        } catch (Exception e)
        {
            log.error("Error lo load error message...", e);
        }
        response.setStatus(err);
        response.setContentType("text/html");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        PrintWriter out = response.getWriter();
        out.println(msg);
        out.close();
    }
}
