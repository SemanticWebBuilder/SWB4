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
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;

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
    
    public void doProcess(HttpServletRequest request, HttpServletResponse response)throws IOException 
    {
        long time=System.currentTimeMillis();
        if(_doProcess(request, response))
        {
            SWBPlatform.getMonitor().addinstanceHit(System.currentTimeMillis()-time);
        }
    }    
    
    public boolean _doProcess(HttpServletRequest request, HttpServletResponse response)throws IOException 
    {
        boolean ret=true;
        log.debug("Distributor->doProcess()");
        
        //DistributorParams dparams = new DistributorParams(request);
/*
        WBUser user = dparams.getUser();
        ArrayList uri = dparams.getResourcesURI();
        Topic topic = dparams.getTopic();
        Topic vtopic = dparams.getVirtTopic();
        int ipfilter = dparams.getFiltered();
        boolean onlyContent = dparams.isOnlyContent();

        if (AFUtils.debug > 2) {
            System.out.println("*********distributor**************");
            System.out.println("email:" + user.getEmail());
            System.out.println("session:" + request.getSession().getId());
            System.out.println("isRegistered:" + user.isRegistered());
            System.out.println("isloged:" + user.isLoged());
            System.out.println("uri:" + request.getRequestURI());
            System.out.println("servlet:" + request.getServletPath());
            System.out.println("topic:" + topic);
            System.out.println("virtTopic:" + vtopic);
            System.out.println("ipfilter:" + ipfilter);
            System.out.println("isSecure:" + request.isSecure());
            System.out.println("*****************************");
        }        
        
        */
        
        
        return ret;
    }



}
