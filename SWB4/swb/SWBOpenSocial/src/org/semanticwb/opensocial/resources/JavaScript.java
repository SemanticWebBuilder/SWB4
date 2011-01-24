/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.opensocial.resources;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.charset.Charset;
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
        scripts.put("core_rpc.js", SocialContainer.loadScript("core_rpc.js"));
        scripts.put("core_rpc_pubsub_container.js", SocialContainer.loadScript("core_rpc_pubsub_container.js"));
    }

    public void doProcess(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        String script = request.getParameter("script");
        if (script != null && script.endsWith(".js"))
        {
            int pos = script.lastIndexOf("/");
            String fileName = script.substring(pos + 1);
            fileName = SWBUtils.getApplicationPath() + "swbadmin/jsp/opensocial/" + fileName;
            File file = new File(fileName);
            if (file.exists())
            {
                InputStreamReader reader = new FileReader(file);
                log.debug(" reader.getEncoding(): " + reader.getEncoding());
                StringBuilder sb = new StringBuilder();
                char[] buffer = new char[1024 * 8];
                int read = reader.read(buffer);
                while (read != -1)
                {
                    String data = new String(buffer, 0, read);
                    read = reader.read(buffer);
                    sb.append(data);
                }
                String js = sb.toString();
                //String js=scripts.get(fileName);
                if (js != null && !"".equals("js"))
                {

                    SWBResourceURL makeRequest = paramRequest.getRenderUrl();
                    makeRequest.setMode(SocialContainer.Mode_MAKE_REQUEST);
                    makeRequest.setCallMethod(SWBResourceURL.Call_DIRECT);

                    SWBResourceURL proxy = paramRequest.getRenderUrl();
                    proxy.setMode(SocialContainer.Mode_PROXY);
                    proxy.setCallMethod(SWBResourceURL.Call_DIRECT);

                    SWBResourceURL rpc = paramRequest.getRenderUrl();
                    rpc.setMode(SocialContainer.Mode_RPC);
                    rpc.setCallMethod(SWBResourceURL.Call_DIRECT);


                    SWBResourceURL ifr = paramRequest.getRenderUrl();
                    ifr.setMode(SocialContainer.Mode_IFRAME);
                    ifr.setCallMethod(SWBResourceURL.Call_DIRECT);
                    String relaypath = SWBPortal.getContextPath() + "/swbadmin/jsp/opensocial/rpc_relay.html";


                    SWBResourceURL baseurl = paramRequest.getRenderUrl();
                    baseurl.setMode(SWBResourceURL.Mode_VIEW);
                    baseurl.setCallMethod(SWBResourceURL.Call_CONTENT);
                    baseurl.setWindowState(SWBResourceURL.WinState_MAXIMIZED);

                    SWBResourceURL javascript = paramRequest.getRenderUrl();
                    javascript.setMode(SocialContainer.Mode_METADATA);
                    javascript.setCallMethod(SWBResourceURL.Call_DIRECT);
                    String port = "";
                    if (request.getServerPort() != 80)
                    {
                        port = ":" + request.getServerPort();
                    }
                    String host = request.getServerName();
                    /*if("localhost".equals(host))
                    {
                    host="127.0.0.1";
                    }*/
                    String baserequest = request.getScheme() + "://" + host + port;

                    js = js.replace("<%=baseurl%>", baserequest + baseurl.toString());
                    js = js.replace("<%=metadata%>", baserequest + javascript.toString());
                    js = js.replace("<%=ifr%>", baserequest + ifr.toString());
                    js = js.replace("<%=rpc%>", baserequest + rpc.toString());
                    js = js.replace("<%=proxy%>", baserequest + proxy.toString());
                    js = js.replace("<%=makerequest%>", baserequest + makeRequest.toString());
                    js = js.replace("<%=rpc_relay%>", baserequest + relaypath);

                    /*PrintWriter out = response.getWriter();
                    out.write(js);
                    out.close();*/
                    Charset charset = Charset.defaultCharset();
                    response.setContentType("text/javascript;charset=" + charset.name());
                    OutputStream out = response.getOutputStream();
                    out.write(js.getBytes(charset));
                    out.close();


                }
            }
        }
        else
        {
            response.setStatus(404);
        }
    }
}
