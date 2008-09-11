/*
 *  XFormsFilter
 *  Copyright (C) 2006 Adam Retter, Devon Portal Project <adam.retter@devon.gov.uk>
 */

package org.chiba.web.filter;

import org.chiba.web.WebFactory;
import org.chiba.xml.xforms.config.XFormsConfigException;

import javax.servlet.*;
import java.io.IOException;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;

/**
 * A Servlet Filter to provide XForms functionality to existing Servlets
 *
 * @author Adam Retter <adam.retter@devon.gov.uk>, Joern Turner
 * @version 1.3
 * @serial 2007-02-19T13:51
 */
@SuppressWarnings({"JavadocReference"})
public class XFormsFilter implements Filter {
    Logger log=SWBUtils.getLogger(XFormsFilter.class);
    private WebFactory webFactory;
    private String defaultRequestEncoding = "UTF-8";


    /**
     * Filter initialisation
     *
     * @see http://java.sun.com/j2ee/sdk_1.3/techdocs/api/javax/servlet/Filter.html#init(javax.servlet.FilterConfig)
     */
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("ENTRA AL FILTRO HOY J");
        webFactory = new WebFactory();
        webFactory.setServletContext(filterConfig.getServletContext());
        try {
            webFactory.initConfiguration();
            defaultRequestEncoding = webFactory.getConfig().getProperty("defaultRequestEncoding", defaultRequestEncoding);
            //webFactory.initLogging(this.getClass());
            webFactory.initTransformerService();
            webFactory.initXFormsSessionManager();
        } catch (XFormsConfigException e) {
            throw new ServletException(e);
        }
    }

    /**
     * Filter shutdown
     *
     * @see http://java.sun.com/j2ee/sdk_1.3/techdocs/api/javax/servlet/Filter.html#destroy()
     */
    public void destroy() {
        webFactory.destroyXFormsSessionManager();
    }


    /**
     * The actual filtering method
     *
     * @see http://java.sun.com/j2ee/sdk_1.3/techdocs/api/javax/servlet/Filter.html#doFilter(javax.servlet.ServletRequest,%20javax.servlet.ServletResponse,%20javax.servlet.FilterChain)
     */
    public void doFilter(ServletRequest srvRequest, ServletResponse srvResponse, FilterChain filterChain) throws IOException, ServletException {
       
    }

}
