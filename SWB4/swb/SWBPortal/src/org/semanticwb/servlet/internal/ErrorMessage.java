package org.semanticwb.servlet.internal;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;

/**
 * Servlet utilizado para el envio de errores.
 * @author Javier Solis Gonzalez
 * @version
 */
public class ErrorMessage implements InternalServlet
{
    static Logger log=SWBUtils.getLogger(ErrorMessage.class);

    /** Initializes the servlet.
     */
    
    public void init(ServletContext config) throws ServletException
    {
        
    }
    
    /** Destroys the servlet.
     */
    
    public void destroy()
    {
        
    }
    
    /** Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
    {
        int err=500;
        try
        {
            err=Integer.parseInt(request.getParameter("err"));
        }catch(Exception noe){}
        String path="/config/"+err+".html";
        String msg = null;
        try {
            //msg = WBUtils.getInstance().getFileFromWorkPath2(path);
            msg = SWBUtils.IO.getFileFromPath(SWBUtils.getApplicationPath()+"/work/"+path);
            //msg = WBUtils.getInstance().parseHTML(msg, WBUtils.getInstance().getWebWorkPath() + "/config/images/");
            msg = SWBPortal.UTIL.parseHTML(msg, SWBPlatform.getWebWorkPath() + "/config/images/");
        }
        catch (Exception e) {
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
    
    /** Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     */
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
    {
        processRequest(request, response);
    }
    
    /** Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     */
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
    {
        processRequest(request, response);
    }
    
    /** Returns a short description of the servlet.
     */
    
    public String getServletInfo()
    {
        return "Error Message";
    }

    public void doProcess(HttpServletRequest request, HttpServletResponse response, DistributorParams dparams) throws IOException, ServletException
    {
        processRequest(request, response);
        //throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
