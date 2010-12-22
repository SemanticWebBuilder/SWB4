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
import org.semanticwb.opensocial.services.AppData;
import org.semanticwb.opensocial.services.People;
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
        services.put("people", new People());
        services.put("appdata", new AppData());
    }

    private JSONObject execute(String method, JSONObject params, String viewer, String owner, WebSite site) throws Exception
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
                return service.get(userId, params, site);
            }
        }
        return execute;
    }

    private void sendResponse(String objresponse, HttpServletResponse response) throws IOException
    {
        response.setContentType("JSON");
        OutputStream out = response.getOutputStream();

        System.out.println("response:");
        System.out.println(objresponse);
        out.write(objresponse.getBytes());

        out.close();
    }

    public void doProcess(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
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

            String[] values = st.split(":");
            System.out.println("values.length: " + values.length);
            if (values.length == 7)
            {
                try
                {
                    URI gadgetURL = new URI(values[4]);
                    String viewer = values[1];
                    String owner = values[0];
                    System.out.println("viewer: " + viewer);
                    System.out.println("owner: " + owner);
                    if (sb.toString().startsWith("{"))
                    {
                        JSONObject obj = new JSONObject(sb.toString());
                        String method = obj.getString("method");
                        String id = obj.getString("id");
                        JSONObject params = obj.getJSONObject("params");
                        JSONObject responseMethod = execute(method, params, viewer, owner, paramRequest.getWebPage().getWebSite());
                    }
                    else
                    {

                        JSONArray responseJSONObject = new JSONArray();

                        JSONArray requestJSONObject = new JSONArray(sb.toString());
                        System.out.println(requestJSONObject.toString(6));
                        System.out.println("request RPC : " + requestJSONObject.toString(6));
                        for (int i = 0; i < requestJSONObject.length(); i++)
                        {
                            JSONObject obj = requestJSONObject.getJSONObject(i);
                            String method = obj.getString("method");
                            String id = obj.getString("id");
                            JSONObject params = obj.getJSONObject("params");
                            JSONObject responseMethod = execute(method, params, viewer, owner, paramRequest.getWebPage().getWebSite());
                            JSONObject part = new JSONObject();
                            part.put("id", id);
                            part.put("result", responseMethod);                            
                            responseJSONObject.put(part);
                        }
                        sendResponse(responseJSONObject.toString(4), response);
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
