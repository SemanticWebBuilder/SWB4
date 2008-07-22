/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.servlet.internal;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBContext;
import org.semanticwb.SWBUtils;

/**
 *
 * @author Jei
 */
public class Admin implements InternalServlet
{
    static Logger log=SWBUtils.getLogger(Login.class);
    
    public void init(ServletContext config) 
    {
        log.event("Admin initialized...");
    }    
    
    public void doProcess(HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        PrintWriter out=response.getWriter();
        out.print("Hello Admin");
        //SWBContext.getSemanticMgr().debugClasses(SWBContext.getSemanticMgr().getOntology());
        
    }

}
