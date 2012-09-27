/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.social.admin.resources;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.model.User;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;

/**
 *
 * @author jorge.jimenez
 */
public class BrandResource extends GenericResource{

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
       String action=request.getParameter("action");
       String objUri=request.getParameter("objUri");
       String wsite=request.getParameter("wsite");
       User user=paramRequest.getUser();
       System.out.println("BrandResource View...action:"+action+",objUri:"+objUri+",wsite:"+wsite);
       System.out.println("User:"+user);
       System.out.println("adminTopic:"+paramRequest.getAdminTopic().getId());
       System.out.println("webPage id:"+paramRequest.getWebPage().getUrl());
       PrintWriter out = response.getWriter();
       out.println("Contenido de Prueba Genérico....");
    }
    
    @Override
    public void doAdmin(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
       System.out.println("BrandResource Admin...");
    }

}