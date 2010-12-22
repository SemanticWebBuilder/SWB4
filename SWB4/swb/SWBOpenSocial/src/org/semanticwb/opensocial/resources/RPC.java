/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.opensocial.resources;

import org.semanticwb.opensocial.services.Service;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONObject;
import org.semanticwb.model.WebSite;
import org.semanticwb.opensocial.model.Gadget;
import org.semanticwb.opensocial.services.AppDataService;
import org.semanticwb.opensocial.services.PeopleService;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;

/**
 *
 * @author victor.lorenzana
 */
public class RPC
{

    private static final Map<String, Service> services = Collections.synchronizedMap(new HashMap<String, Service>());

    static
    {
        services.put("people", new PeopleService());
        services.put("appdata", new AppDataService());
    }

    private JSONObject execute(String method, JSONObject params, String viewer, String owner, WebSite site,Gadget gadget) throws Exception
    {
        JSONObject execute = new JSONObject();
        int pos = method.indexOf(".");
        if (pos != -1)
        {
            String objectType = method.substring(0, pos);
            method = method.substring(pos + 1);
            String userId = params.getString("userId");
            if (userId.equals("@me"))
            {
                userId = owner;
            }
            if (userId.equals("@viewer"))
            {
                userId = viewer;
            }
            params.remove("userId");
            Service service = services.get(objectType);
            if (method.equals("get"))
            {
                return service.get(userId, params, site,gadget);
            }
            if (method.equals("update"))
            {
                service.update(userId, params, site,gadget);
            }
        }
        return execute;
    }

    private void sendResponse(String objresponse, HttpServletResponse response) throws IOException
    {
        response.setContentType("JSON");
        OutputStream out = response.getOutputStream();
        out.write(objresponse.getBytes());
        out.close();
    }

    public void doProcess(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        WebSite site=paramRequest.getWebPage().getWebSite();
        String port = "";
        if (request.getServerPort() != 80)
        {
            port = ":" + request.getServerPort();
        }
        String st = request.getParameter("st");
        System.out.println("st: " + st); // security token
        if (st != null && request.getContentType().equals("application/json"))
        {
            InputStream in = request.getInputStream();
            StringBuilder sb = new StringBuilder();
            byte[] buffer = new byte[1028];
            int read = in.read(buffer);
            while (read != -1)
            {
                sb.append(new String(buffer, 0, read));
                read = in.read(buffer);
            }
            in.close();
            System.out.println("request RPC : " + sb.toString());
            String[] values = st.split(":");

            if (values.length == 7)
            {
                try
                {
                    URI gadgetURL = new URI(values[4]);
                    URI here = new URI(request.getScheme() + "://" + request.getServerName() + port + request.getRequestURI());
                    if (!gadgetURL.isAbsolute())
                    {
                        gadgetURL = here.resolve(gadgetURL);
                    }
                    Gadget gadget = SocialContainer.getGadget(gadgetURL.toString(), site);
                    if (gadget != null)
                    {
                        String viewer = values[1];
                        String owner = values[0];
                        if (sb.toString().startsWith("{"))
                        {
                            JSONObject obj = new JSONObject(sb.toString());
                            String method = obj.getString("method");
                            String id = obj.getString("id");
                            JSONObject params = obj.getJSONObject("params");
                            JSONObject responseMethod = execute(method, params, viewer, owner, site,gadget);
                        }
                        else
                        {
                            JSONArray responseJSONObject = new JSONArray();
                            JSONArray requestJSONObject = new JSONArray(sb.toString());
                            for (int i = 0; i < requestJSONObject.length(); i++)
                            {
                                JSONObject obj = requestJSONObject.getJSONObject(i);
                                String method = obj.getString("method");
                                String id = obj.getString("id");
                                JSONObject params = obj.getJSONObject("params");
                                JSONObject responseMethod = execute(method, params, viewer, owner, site,gadget);
                                JSONObject part = new JSONObject();
                                part.put("id", id);
                                part.put("result", responseMethod);
                                responseJSONObject.put(part);
                            }
                            sendResponse(responseJSONObject.toString(4), response);
                        }
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }



    }
}
