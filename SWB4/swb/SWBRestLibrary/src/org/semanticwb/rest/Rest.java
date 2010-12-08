/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.rest;

import java.io.IOException;
import java.net.URI;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.servlet.internal.DistributorParams;
import org.semanticwb.servlet.internal.InternalServlet;

/**
 *
 * @author victor.lorenzana
 */
public class Rest implements InternalServlet{

    RestPublish publish=new RestPublish("rest");
    public void init(ServletContext config) throws ServletException
    {        
        try
        {
            RestPublish.addSemanticClass(new URI("http://www.semanticwebbuilder.org/swb4/community#Clasified"));
            RestPublish.addSemanticClass(new URI("http://www.semanticwebbuilder.org/swb4/community#EventElement"));
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        
    }

    public void doProcess(HttpServletRequest request, HttpServletResponse response, DistributorParams dparams) throws IOException, ServletException
    {
        System.out.println("request.getRequestURI(): "+request.getRequestURI());        
        publish.service(request, response);
        
    }

}
