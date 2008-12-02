/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.servlet.internal;

import java.io.IOException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Jei
 */
public interface InternalServlet 
{
    public void init(ServletContext config) throws ServletException;
    
    public void doProcess(HttpServletRequest request, HttpServletResponse response, DistributorParams dparams) throws IOException,ServletException;
}
