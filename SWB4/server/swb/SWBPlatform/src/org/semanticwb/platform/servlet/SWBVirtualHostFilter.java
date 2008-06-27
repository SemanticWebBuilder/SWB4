
package org.semanticwb.platform.servlet;

//import com.infotec.wb.core.WBVirtualHostMgr;
import java.io.*;
import javax.servlet.http.*;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import org.semanticwb.SWBContext;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;

/**
 *
 * @author  Javier Solis Gonzalez (jsolis@infotec.com.mx)
 */

public class SWBVirtualHostFilter implements Filter
{
    static Logger log=SWBUtils.getLogger(SWBVirtualHostFilter.class);    
    private SWBContext swbContext=null;
    
    
    // The filter configuration object we are associated with.  If
    // this value is null, this filter instance is not currently
    // configured.
    private FilterConfig filterConfig = null;
    
    private boolean fistCall=true;
    
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
        HttpServletRequest _request=(HttpServletRequest)request;
        HttpServletResponse _response=(HttpServletResponse)response;
        
        log.trace("VirtualHostFilter:doFilter()");
        
        if(fistCall)
        {
            swbContext.setContextPath(_request.getContextPath());
            fistCall=false;
        }
        
        String uri=_request.getRequestURI();
        String cntx=_request.getContextPath();
        String path=uri.substring(cntx.length());
        String host=request.getServerName();
        
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
            chain.doFilter(request, response);
        }
        catch(Throwable t)
        {
            problem = t;
        }
        
        //
        // If there was a problem, we want to rethrow it if it is
        // a known type, otherwise log it.
        //
        if (problem != null)
        {
            if (problem instanceof ServletException) throw (ServletException)problem;
            if (problem instanceof IOException) throw (IOException)problem;
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
    public void init(FilterConfig filterConfig)
    {
        this.filterConfig = filterConfig;
        if (filterConfig != null)
        {
            log.event("Initializing VirtualHostFilter...");
            String prefix =  filterConfig.getServletContext().getRealPath("/");
            SWBUtils.createInstance(prefix);
            swbContext=SWBContext.createInstance(filterConfig.getServletContext());
        }
    }
    
    /**
     * Return a String representation of this object.
     */
    public String toString()
    {
        
        if (filterConfig == null) return ("VirtualHostFilter()");
        StringBuffer sb = new StringBuffer("VirtualHostFilter(");
        sb.append(filterConfig);
        sb.append(")");
        return (sb.toString());
        
    }
    

}
