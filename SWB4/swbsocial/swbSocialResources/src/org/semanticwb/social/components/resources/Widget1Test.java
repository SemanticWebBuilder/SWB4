/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.social.components.resources;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.model.User;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.zkoss.zk.ui.event.*;

/**
 *
 * @author jorge.jimenez
 */
public class Widget1Test extends GenericResource{

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
       PrintWriter out = response.getWriter();
       String action=request.getParameter("action");
       String objUri=request.getParameter("objUri");
       String wsite=request.getParameter("wsite");
       User user=paramRequest.getUser();
       System.out.println("BrandResource View...action:"+action+",objUri:"+objUri+",wsite:"+wsite);
       System.out.println("User:"+user);
       System.out.println("adminTopic:"+paramRequest.getAdminTopic().getId());
       System.out.println("webPage id:"+paramRequest.getWebPage().getUrl());
       System.out.println("Va a poner evento-1");
       EventQueue eq = EventQueues.lookup("timelineReady",EventQueues.SESSION, true); //create a queue
       eq.publish(new Event("onTimelineReady", null, null));
       System.out.println("Va a poner evento-2");

    }
}
