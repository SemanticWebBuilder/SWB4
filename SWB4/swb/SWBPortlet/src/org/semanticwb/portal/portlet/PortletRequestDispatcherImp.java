/**
  * Copyright 2003 IBM Corporation and Sun Microsystems, Inc.
  * All rights reserved.
  * Use is subject to license terms.
  */

package org.semanticwb.portal.portlet;

import java.io.IOException;
import java.util.Map;
import javax.portlet.*;
import javax.servlet.RequestDispatcher;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;

/**
 * The <code>PortletRequestDispatcher</code> interface 
 * defines an object that receives requests from the client
 * and sends them to the specified resources (such as a servlet,
 * HTML file, or JSP file) on the server. The portlet
 * container creates the <code>PortletRequestDispatcher</code> object,
 * which is used as a wrapper around a server resource located
 * at a particular path or given by a particular name.
 *
 */
 
public class PortletRequestDispatcherImp implements PortletRequestDispatcher
{
    public static Logger log=SWBUtils.getLogger(PortletRequestDispatcherImp.class);
    
    private RequestDispatcher requestDispatcher;
    private Map queryParams;
    
    public PortletRequestDispatcherImp(RequestDispatcher requestDispatcher)
    {
        this.requestDispatcher=requestDispatcher;
    }
    
    public PortletRequestDispatcherImp(RequestDispatcher requestDispatcher, Map queryParams) {
        this(requestDispatcher);
        this.queryParams = queryParams;

    }    

    /**
     *
     * Includes the content of a resource (servlet, JSP page,
     * HTML file) in the response. In essence, this method enables 
     * programmatic server-side includes.
     * <p>
     * The included servlet cannot set or change the response status code
     * or set headers; any attempt to make a change is ignored.
     *
     *
     * @param request 			a {@link RenderRequest} object 
     *					that contains the client request
     *
     * @param response 			a {@link RenderResponse} object 
     * 					that contains the render response
     *
     * @exception PortletException 	if the included resource throws a ServletException, 
     *                                  or other exceptions that are not Runtime-
     *                                  or IOExceptions.
     *
     * @exception java.io.IOException	if the included resource throws this exception
     *
     *
     */
     
    public void include(RenderRequest request, RenderResponse response)
    throws PortletException, java.io.IOException
    {
        try
        {
//            System.out.println("1");
            this.requestDispatcher.include((javax.servlet.http.HttpServletRequest)request,
            (javax.servlet.http.HttpServletResponse)response);
//            System.out.println("2");
        }
        catch (java.io.IOException e)
        {
//            System.out.println("3:"+e);
            throw e;
        }
        catch (javax.servlet.ServletException e)
        {
//            System.out.println("4:"+e);
            if (e.getRootCause()!=null)
            {
                log.error(e);
                throw new PortletException(e.getRootCause());
            }
            else
            {
                log.error(e);
                throw new PortletException(e);
            }
        }    
    }

    public void include(PortletRequest arg0, PortletResponse arg1) throws PortletException, IOException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void forward(PortletRequest arg0, PortletResponse arg1) throws PortletException, IOException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}








