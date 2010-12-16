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
import org.semanticwb.SWBUtils;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;

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
