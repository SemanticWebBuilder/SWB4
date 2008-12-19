/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.portal.resources;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;

/**
 *
 * @author Jei
 */
public class Test extends GenericResource
{
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException 
    {
        PrintWriter out=response.getWriter();
        out.println("Hola Mundo<br>");
        out.println("id:"+paramRequest.getResourceBase().getId());
        out.println("<a href='"+paramRequest.getRenderUrl().setMode(SWBResourceURL.Mode_EDIT)+"'>edit</a><br>");
        out.println("<a href='"+paramRequest.getRenderUrl().setCallMethod(SWBResourceURL.Call_DIRECT)+"'>Direct</a><br>");
        out.println("<br>");
    }

    @Override
    public void doEdit(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException 
    {
        PrintWriter out=response.getWriter();
        out.println("Hola Edit<br>");
        out.println("id:"+paramRequest.getResourceBase().getId());
        out.println("<a href='"+paramRequest.getRenderUrl().setMode(SWBResourceURL.Mode_VIEW)+"'>View</a><br>");
        out.println("<br>");
    }
    
    
    @Override
    public void doAdmin(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException 
    {
        
        PrintWriter out=response.getWriter();
        /*
        out.println("Hola Mundo Jorge<br>");
        out.println("id:"+paramRequest.getResourceBase().getId());
        out.println("<a href='"+paramRequest.getRenderUrl().setMode(SWBResourceURL.Mode_EDIT)+"'>edit</a><br>");
        out.println("<a href='"+paramRequest.getRenderUrl().setCallMethod(SWBResourceURL.Call_DIRECT)+"'>Direct</a><br>");
        out.println("<br>");
        **/
         
        String str="Updates to the Original Java Specification Request (JSR)" +
"The following information has been updated from the original JSR."+
"2.11 Please describe the anticipated schedule for the development of this specification."+
"Community Draft submitted: oct-2003"+
"Community Review closed: dec-2003"+
"The following information has been updated from the original JSR."+
"2.11 Please describe the anticipated schedule for the development of this specification."+
"Community Draft submitted: oct-2003"+
"Community Review closed: dec-2003"+
                "The following information has been updated from the original JSR."+
"2.11 Please describe the anticipated schedule for the development of this specification."+
"Community Draft submitted: oct-2003"+
"Community Review closed: dec-2003"+
                "The following information has been updated from the original JSR."+
"2.11 Please describe the anticipated schedule for the development of this specification."+
"Community Draft submitted: oct-2003"+
"Community Review closed: dec-2003"+
                "The following information has been updated from the original JSR."+
"2.11 Please describe the anticipated schedule for the development of this specification."+
"Community Draft submitted: oct-2003"+
"Community Review closed: dec-2003"+
                "The following information has been updated from the original JSR."+
"2.11 Please describe the anticipated schedule for the development of this specification."+
"Community Draft submitted: oct-2003"+
"Community Review closed: dec-2003"+
                "The following information has been updated from the original JSR."+
"2.11 Please describe the anticipated schedule for the development of this specification."+
"Community Draft submitted: oct-2003"+
"Community Review closed: dec-2003"+
                "The following information has been updated from the original JSR."+
"2.11 Please describe the anticipated schedule for the development of this specification."+
"Community Draft submitted: oct-2003"+
"Community Review closed: dec-2003"+
                "The following information has been updated from the original JSR."+
"2.11 Please describe the anticipated schedule for the development of this specification."+
"Community Draft submitted: oct-2003"+
"Community Review closed: dec-2003"+
                "The following information has been updated from the original JSR."+
"2.11 Please describe the anticipated schedule for the development of this specification."+
"Community Draft submitted: oct-2003"+
"Community Review closed: dec-2003"+
                "The following information has been updated from the original JSR."+
"2.11 Please describe the anticipated schedule for the development of this specification."+
"Community Draft submitted: oct-2003"+
"Community Review closed: dec-2003"+
                "The following information has been updated from the original JSR."+
"2.11 Please describe the anticipated schedule for the development of this specification."+
"Community Draft submitted: oct-2003"+
"Community Review closed: dec-2003"+
                "The following information has been updated from the original JSR."+
"2.11 Please describe the anticipated schedule for the development of this specification."+
"Community Draft submitted: oct-2003"+
"Community Review closed: dec-2003"+
                "The following information has been updated from the original JSR."+
"2.11 Please describe the anticipated schedule for the development of this specification."+
"Community Draft submitted: oct-2003"+
"Community Review closed: dec-2003"+
                "The following information has been updated from the original JSR."+
"2.11 Please describe the anticipated schedule for the development of this specification."+
"Community Draft submitted: oct-2003"+
"Community Review closed: dec-2003"+
                "The following information has been updated from the original JSR."+
"2.11 Please describe the anticipated schedule for the development of this specification."+
"Community Draft submitted: oct-2003"+
"Community Review closed: dec-2003"+
                "The following information has been updated from the original JSR."+
"2.11 Please describe the anticipated schedule for the development of this specification."+
"Community Draft submitted: oct-2003"+
"Community Review closed: dec-2003"+
                "The following information has been updated from the original JSR."+
"2.11 Please describe the anticipated schedule for the development of this specification."+
"Community Draft submitted: oct-2003"+
"Community Review closed: dec-2003"+
                "The following information has been updated from the original JSR."+
"2.11 Please describe the anticipated schedule for the development of this specification."+
"Community Draft submitted: oct-2003"+
"Community Review closed: dec-2003"+"The following information has been updated from the original JSR."+
"2.11 Please describe the anticipated schedule for the development of this specification."+
"Community Draft submitted: oct-2003"+
"Community Review closed: dec-2003"+"The following information has been updated from the original JSR."+
"2.11 Please describe the anticipated schedule for the development of this specification."+
"Community Draft submitted: oct-2003"+
"Community Review closed: dec-2003"+"The following information has been updated from the original JSR."+
"2.11 Please describe the anticipated schedule for the development of this specification."+
"Community Draft submitted: oct-2003"+
"Community Review closed: dec-2003"+
                "The following information has been updated from the original JSR."+
"2.11 Please describe the anticipated schedule for the development of this specification."+
"Community Draft submitted: oct-2003"+
"Community Review closed: dec-2003"+
                "The following information has been updated from the original JSR."+
"2.11 Please describe the anticipated schedule for the development of this specification."+
"Community Draft submitted: oct-2003"+
"Community Review closed: dec-2003"+
                "The following information has been updated from the original JSR."+
"2.11 Please describe the anticipated schedule for the development of this specification."+
"Community Draft submitted: oct-2003"+
"Community Review closed: dec-2003"+"The following information has been updated from the original JSR."+
"2.11 Please describe the anticipated schedule for the development of this specification."+
"Community Draft submitted: oct-2003"+
"Community Review closed: dec-2003"+"The following information has been updated from the original JSR."+
"2.11 Please describe the anticipated schedule for the development of this specification."+
"Community Draft submitted: oct-2003"+
"Community Review closed: dec-2003"+"The following information has been updated from the original JSR."+
"2.11 Please describe the anticipated schedule for the development of this specification."+
"Community Draft submitted: oct-2003"+
"Community Review closed: dec-2003"+"The following information has been updated from the original JSR."+
"2.11 Please describe the anticipated schedule for the development of this specification."+
"Community Draft submitted: oct-2003"+
"Community Review closed: dec-2003"+
                "The following information has been updated from the original JSR."+
"2.11 Please describe the anticipated schedule for the development of this specification."+
"Community Draft submitted: oct-2003"+
"Community Review closed: dec-2003"+
                "The following information has been updated from the original JSR."+
"2.11 Please describe the anticipated schedule for the development of this specification."+
"Community Draft submitted: oct-2003"+
"Community Review closed: dec-2003"+
                "The following information has been updated from the original JSR."+
"2.11 Please describe the anticipated schedule for the development of this specification."+
"Community Draft submitted: oct-2003"+
"Community Review closed: dec-2003"+"The following information has been updated from the original JSR."+
"2.11 Please describe the anticipated schedule for the development of this specification."+
"Community Draft submitted: oct-2003"+
"Community Review closed: dec-2003"+"The following information has been updated from the original JSR."+
"2.11 Please describe the anticipated schedule for the development of this specification."+
"Community Draft submitted: oct-2003"+
"Community Review closed: dec-2003"+"The following information has been updated from the original JSR."+
"2.11 Please describe the anticipated schedule for the development of this specification."+
"Community Draft submitted: oct-2003"+
"Community Review closed: dec-2003"+"The following information has been updated from the original JSR."+
"2.11 Please describe the anticipated schedule for the development of this specification."+
"Community Draft submitted: oct-2003"+
"Community Review closed: dec-2003"+"The following information has been updated from the original JSR."+
"2.11 Please describe the anticipated schedule for the development of this specification."+
"Community Draft submitted: oct-2003"+
"Community Review closed: dec-2003"+"The following information has been updated from the original JSR."+
"2.11 Please describe the anticipated schedule for the development of this specification."+
"Community Draft submitted: oct-2003"+
"Community Review closed: dec-2003"+
                "The following information has been updated from the original JSR."+
"2.11 Please describe the anticipated schedule for the development of this specification."+
"Community Draft submitted: oct-2003"+
"Community Review closed: dec-2003"+
                "The following information has been updated from the original JSR."+
"2.11 Please describe the anticipated schedule for the development of this specification."+
"Community Draft submitted: oct-2003"+
"Community Review closed: dec-2003"+
                "The following information has been updated from the original JSR."+
"2.11 Please describe the anticipated schedule for the development of this specification."+
"Community Draft submitted: oct-2003"+
"Community Review closed: dec-2003"+
                "The following information has been updated from the original JSR."+
"2.11 Please describe the anticipated schedule for the development of this specification."+
"Community Draft submitted: oct-2003"+
"Community Review closed: dec-2003"+"The following information has been updated from the original JSR."+
"2.11 Please describe the anticipated schedule for the development of this specification."+
"Community Draft submitted: oct-2003"+
"Community Review closed: dec-2003"+"The following information has been updated from the original JSR."+
"2.11 Please describe the anticipated schedule for the development of this specification."+
"Community Draft submitted: oct-2003"+
"Community Review closed: dec-2003"+"The following information has been updated from the original JSR."+
"2.11 Please describe the anticipated schedule for the development of this specification."+
"Community Draft submitted: oct-2003"+
"Community Review closed: dec-2003"+"The following information has been updated from the original JSR."+
"2.11 Please describe the anticipated schedule for the development of this specification."+
"Community Draft submitted: oct-2003"+
"Community Review closed: dec-2003"+"The following information has been updated from the original JSR."+
"2.11 Please describe the anticipated schedule for the development of this specification."+
"Community Draft submitted: oct-2003"+
"Community Review closed: dec-2003"+
                "The following information has been updated from the original JSR."+
"2.11 Please describe the anticipated schedule for the development of this specification."+
"Community Draft submitted: oct-2003"+
"Community Review closed: dec-2003"+
                "The following information has been updated from the original JSR."+
"2.11 Please describe the anticipated schedule for the development of this specification."+
"Community Draft submitted: oct-2003"+
"Community Review closed: dec-2003"+"The following information has been updated from the original JSR."+
"2.11 Please describe the anticipated schedule for the development of this specification."+
"Community Draft submitted: oct-2003"+
"Community Review closed: dec-2003"+"The following information has been updated from the original JSR."+
"2.11 Please describe the anticipated schedule for the development of this specification."+
"Community Draft submitted: oct-2003"+
"Community Review closed: dec-2003"+"The following information has been updated from the original JSR."+
"2.11 Please describe the anticipated schedule for the development of this specification."+
"Community Draft submitted: oct-2003"+
"Community Review closed: dec-2003"+"The following information has been updated from the original JSR."+
"2.11 Please describe the anticipated schedule for the development of this specification."+
"Community Draft submitted: oct-2003"+
"Community Review closed: dec-2003";             
    out.println(str);
}
    
}
