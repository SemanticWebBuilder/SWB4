/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.portal.resources;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.model.WebPage;
import org.semanticwb.portal.api.*;

/**
 *
 * @author Jei
 */
public class Menu extends GenericResource
{

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException 
    {
        PrintWriter out=response.getWriter();
        WebPage topic=paramRequest.getTopic();
        WebPage parent=topic.getParent();
        if(parent!=null)
        {
            out.println("<BR><a href='"+parent.getUrl()+"'>"+parent.getTitle()+"</a>");
        }
        out.println("<BR>--><a href='"+topic.getUrl()+"'>"+topic.getTitle()+"</a>");
        Iterator<WebPage> it=topic.listChilds();
        while(it.hasNext())
        {
            WebPage child=it.next();
            out.println("<BR>----><a href='"+child.getUrl()+"'>"+child.getTitle()+"</a>");
        }
    }
    
}
