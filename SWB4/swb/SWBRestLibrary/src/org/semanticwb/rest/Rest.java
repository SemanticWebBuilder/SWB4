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

    RestManager manager=new RestManager("rest");
    public void init(ServletContext config) throws ServletException
    {
        SemanticClassPublisher module=new SemanticClassPublisher();
        
        try
        {
            module.addSemanticClass(new URI("http://www.semanticwebbuilder.org/swb4/community#Clasified"));
            module.addSemanticClass(new URI("http://www.semanticwebbuilder.org/swb4/community#EventElement"));
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        RestManager.addModule("so",module);
    }

    public void doProcess(HttpServletRequest request, HttpServletResponse response, DistributorParams dparams) throws IOException, ServletException
    {
        System.out.println("request.getRequestURI(): "+request.getRequestURI());        
        manager.service(request, response);
        
    }

}
