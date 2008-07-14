/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.servlet.internal;

import java.io.IOException;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;

/**
 *
 * @author Jei
 */
public class Login implements InternalServlet
{
    static Logger log=SWBUtils.getLogger(Login.class);
    
    public void init(ServletContext config) 
    {
        log.event("Login initialized...");
    }    

    public void doProcess(HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        response.getWriter().print("Hello Login");
        //SWBContext.getSemanticMgr().getAdminModel().createResource(SWBContext.getSemanticMgr().getOntology().getResource("http://www.semanticwebbuilder.org/swb4/ontology#WebPage"));
    }        
}
