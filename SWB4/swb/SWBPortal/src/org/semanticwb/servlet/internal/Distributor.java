/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.servlet.internal;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.User;
import org.semanticwb.model.WebPage;

/**
 *
 * @author Jei
 */
public class Distributor implements InternalServlet
{
    static Logger log=SWBUtils.getLogger(Distributor.class);
    String name = "swb";
    boolean agzip = true;
    boolean admin = true;
    boolean secure = false;
    
    public void init(ServletContext scontext) 
    {
        log.event("Distributor initialized...");
        name = SWBPlatform.getEnv("swb/distributor","swb");
        agzip = SWBPlatform.getEnv("swb/responseGZIPEncoding", "true").equalsIgnoreCase("true");
        admin = SWBPlatform.getEnv("swb/administration", "true").equalsIgnoreCase("true");
        secure = SWBPlatform.getEnv("swb/secureAdmin", "false").equalsIgnoreCase("true");
    }
    
    public void doProcess(HttpServletRequest request, HttpServletResponse response, DistributorParams dparams)throws IOException 
    {
        long time=System.currentTimeMillis();
        if(_doProcess(request, response, dparams))
        {
            SWBPlatform.getMonitor().addinstanceHit(System.currentTimeMillis()-time);
        }
    }    
    
    public boolean _doProcess(HttpServletRequest request, HttpServletResponse response, DistributorParams dparams)throws IOException 
    {
        boolean ret=true;
        log.debug("Distributor->doProcess()");
        
        User user = dparams.getUser();
        ArrayList uri = dparams.getResourcesURI();
        WebPage topic = dparams.getTopic();
        WebPage vtopic = dparams.getVirtTopic();
        int ipfilter = dparams.getFiltered();
        boolean onlyContent = dparams.isOnlyContent();

        {
            PrintWriter out=response.getWriter();
            out.println("*********distributor**************");
            out.println("email:" + user.getUsrEmail());
            out.println("session:" + request.getSession().getId());
            //out.println("isRegistered:" + user.isRegistered());
            //out.println("isloged:" + user.isLoged());
            out.println("uri:" + request.getRequestURI());
            out.println("servlet:" + request.getServletPath());
            out.println("topic:" + topic);
            out.println("virtTopic:" + vtopic);
            out.println("ipfilter:" + ipfilter);
            out.println("isSecure:" + request.isSecure());
            out.println("onlyContent:" + onlyContent);
            out.println("*****************************");
        }
        
        return ret;
    }



}
