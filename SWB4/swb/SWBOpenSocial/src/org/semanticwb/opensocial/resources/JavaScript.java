/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.opensocial.resources;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;

/**
 *
 * @author victor.lorenzana
 */
public final class JavaScript
{

    private static final Map<String, String> scripts = new HashMap<String, String>();
    private static final Logger log = SWBUtils.getLogger(JavaScript.class);
    static
    {
        scripts.put("core_rpc.js",SocialContainer.loadScript("core_rpc.js"));
        scripts.put("core_rpc_pubsub_container.js",SocialContainer.loadScript("core_rpc_pubsub_container.js"));        
    }

    

    public void doProcess(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        String script = request.getParameter("script");
        if (script!=null && script.endsWith(".js"))
        {
            int pos = script.lastIndexOf("/");
            String fileName = script.substring(pos + 1);            
            String js=scripts.get(fileName);
            if(js!=null)
            {

                SWBResourceURL makeRequest=paramRequest.getRenderUrl();
                makeRequest.setMode(SocialContainer.Mode_MAKE_REQUEST);
                makeRequest.setCallMethod(SWBResourceURL.Call_DIRECT);

                SWBResourceURL proxy=paramRequest.getRenderUrl();
                proxy.setMode(SocialContainer.Mode_PROXY);
                proxy.setCallMethod(SWBResourceURL.Call_DIRECT);

                SWBResourceURL rpc=paramRequest.getRenderUrl();
                rpc.setMode(SocialContainer.Mode_RPC);
                rpc.setCallMethod(SWBResourceURL.Call_DIRECT);


                SWBResourceURL ifr=paramRequest.getRenderUrl();
                ifr.setMode(SocialContainer.Mode_IFRAME);
                ifr.setCallMethod(SWBResourceURL.Call_DIRECT);
                String relaypath=SWBPortal.getContextPath()+"/swbadmin/jsp/opensocial/rpc_relay.html";
                

                SWBResourceURL javascript=paramRequest.getRenderUrl();
                javascript.setMode(SocialContainer.Mode_METADATA);
                javascript.setCallMethod(SWBResourceURL.Call_DIRECT);
                js=js.replace("<%=metadata%>", javascript.toString());
                js=js.replace("<%=ifr%>", ifr.toString());
                js=js.replace("<%=rpc%>", rpc.toString());
                js=js.replace("<%=proxy%>", proxy.toString());
                js=js.replace("<%=makerequest%>", makeRequest.toString());
                js=js.replace("<%=rpc_relay%>",relaypath);

                PrintWriter out=response.getWriter();
                out.write(js);
                out.close();
            }
        }
        else
        {
            response.setStatus(404);
        }
    }
}
