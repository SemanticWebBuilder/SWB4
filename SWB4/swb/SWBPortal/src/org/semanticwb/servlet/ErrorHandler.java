/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;

/**
 *
 * @author javier.solis
 */
public class ErrorHandler extends HttpServlet
{
    private static Logger log = SWBUtils.getLogger(ErrorHandler.class);
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
     /*   System.out.println("******************************************");
        Enumeration en1 = request.getHeaderNames();
        while (en1.hasMoreElements()){
            String head= (String)en1.nextElement();
            Enumeration en2 = request.getHeaders(head);
            while (en2.hasMoreElements()){
                System.out.println(head+":"+en2.nextElement());
            }
        }
        System.out.println(request.getPathInfo());
        System.out.println("******************************************");
     */   int err=500;
        try
        {
            //String serr=request.getParameter("err");
            String serr=request.getPathInfo().substring(1);
      //      System.out.println("err:"+serr);
        //    System.out.println("query:"+request.getQueryString());
            if(serr!=null)
            {
                err=Integer.parseInt(serr);
            }
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

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    } 

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
