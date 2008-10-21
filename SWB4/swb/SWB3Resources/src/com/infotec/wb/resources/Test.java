/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.infotec.wb.resources;

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
    
    
    
    
}
