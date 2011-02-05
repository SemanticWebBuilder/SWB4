/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.rest.publish;

import java.io.IOException;
import java.net.URI;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.servlet.internal.DistributorParams;
import org.semanticwb.servlet.internal.InternalServlet;

/**
 *
 * @author victor.lorenzana
 */
public class RestInternalServlet implements InternalServlet{

    private static final Logger log = SWBUtils.getLogger(RestInternalServlet.class);
    RestManager manager=new RestManager("rest");
    public void init(ServletContext config) throws ServletException
    {
        SemanticClassPublisher module=new SemanticClassPublisher();
        
        try
        {
            module.addSemanticClass(new URI("http://www.semanticwebbuilder.org/swb4/community#Clasified"),RepresentationMediaType.JSON,RepresentationMediaType.XML);
            module.addSemanticClass(new URI("http://www.semanticwebbuilder.org/swb4/community#EventElement"),RepresentationMediaType.JSON,RepresentationMediaType.XML);
        }
        catch(Exception e)
        {
            log.debug(e);
        }
        RestManager.addModule("so",module);
    }

    public void doProcess(HttpServletRequest request, HttpServletResponse response, DistributorParams dparams) throws IOException, ServletException
    {       

        manager.service(request, response);
    }

}
