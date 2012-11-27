/*
 * SemanticWebBuilder es una plataforma para el desarrollo de portales y aplicaciones de integración,
 * colaboración y conocimiento, que gracias al uso de tecnología semántica puede generar contextos de
 * información alrededor de algún tema de interés o bien integrar información y aplicaciones de diferentes
 * fuentes, donde a la información se le asigna un significado, de forma que pueda ser interpretada y
 * procesada por personas y/o sistemas, es una creación original del Fondo de Información y Documentación
 * para la Industria INFOTEC, cuyo registro se encuentra actualmente en trámite.
 *
 * INFOTEC pone a su disposición la herramienta SemanticWebBuilder a través de su licenciamiento abierto al público (‘open source’),
 * en virtud del cual, usted podrá usarlo en las mismas condiciones con que INFOTEC lo ha diseñado y puesto a su disposición;
 * aprender de él; distribuirlo a terceros; acceder a su código fuente y modificarlo, y combinarlo o enlazarlo con otro software,
 * todo ello de conformidad con los términos y condiciones de la LICENCIA ABIERTA AL PÚBLICO que otorga INFOTEC para la utilización
 * del SemanticWebBuilder 4.0.
 *
 * INFOTEC no otorga garantía sobre SemanticWebBuilder, de ninguna especie y naturaleza, ni implícita ni explícita,
 * siendo usted completamente responsable de la utilización que le dé y asumiendo la totalidad de los riesgos que puedan derivar
 * de la misma.
 *
 * Si usted tiene cualquier duda o comentario sobre SemanticWebBuilder, INFOTEC pone a su disposición la siguiente
 * dirección electrónica:
 *  http://www.semanticwebbuilder.org
 */
package org.semanticwb.opensocial.resources;

import org.semanticwb.opensocial.services.Service;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONObject;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.WebSite;
import org.semanticwb.opensocial.model.Gadget;
import org.semanticwb.opensocial.model.data.Person;
import org.semanticwb.opensocial.services.ActivitiesService;
import org.semanticwb.opensocial.services.AlbumsService;
import org.semanticwb.opensocial.services.AppDataService;
import org.semanticwb.opensocial.services.GroupsService;
import org.semanticwb.opensocial.services.MediaItemsService;
import org.semanticwb.opensocial.services.MessagesService;
import org.semanticwb.opensocial.services.PeopleService;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;

/**
 *
 * @author victor.lorenzana
 */
public class RPC
{
    private static final Logger log = SWBUtils.getLogger(RPC.class);
    private static final Map<String, Service> services = Collections.synchronizedMap(new HashMap<String, Service>());

    static
    {
        services.put("people", new PeopleService());
        services.put("appdata", new AppDataService());
        services.put("groups", new GroupsService());
        services.put("activities", new ActivitiesService());
        services.put("albums", new AlbumsService());
        services.put("mediaItems", new MediaItemsService());
        services.put("messages", new MessagesService());
    }

    private JSONObject execute(String method, JSONObject params, String viewer, String owner, WebSite site, Gadget gadget) throws Exception
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
            Person person = Person.ClassMgr.getPerson(userId, site);
            if (person != null)
            {
                params.remove("userId");
                Service service = services.get(objectType);
                try
                {
                    if (method.equals("get"))
                    {
                        return service.get(person, params, site, gadget);
                    }
                    else if (method.equals("update"))
                    {
                        service.update(person, params, site, gadget);
                    }
                    else if (method.equals("create"))
                    {
                        service.create(person, params, site, gadget);
                    }
                    else if (method.equals("delete"))
                    {
                        service.delete(person, params, site, gadget);
                    }
                }
                catch (RPCException e)
                {
                    log.debug(e);                    
                }
            }
        }
        return execute;
    }

    private void sendResponse(String objresponse, HttpServletResponse response) throws IOException
    {
        Charset utf8 = Charset.forName("utf-8");
        response.setContentType("JSON;charset=" + utf8.name());
        OutputStream out = response.getOutputStream();
        out.write(objresponse.getBytes(utf8));
        out.close();
    }

    public void doProcess(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        WebSite site = paramRequest.getWebPage().getWebSite();
        String port = "";
        if (request.getServerPort() != 80)
        {
            port = ":" + request.getServerPort();
        }
        String st = request.getParameter("st");
        log.debug("request.getQueryString(): " + request.getQueryString());

        if (st != null && "application/json".equals(request.getContentType()))
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
            log.debug("request RPC : " + sb.toString());
            String[] values = st.split(":");

            if (values.length >= 7)
            {
                try
                {
                    String urlgadget = values[4];
                    if (values.length == 7)
                    {
                        urlgadget = values[4];
                    }
                    if (values.length == 9)
                    {
                        urlgadget = values[4] + ":" + values[5] + ":" + values[6];
                    }                    
                    URI gadgetURL = new URI(urlgadget);
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
                        log.debug("viewer: " + viewer);
                        log.debug("owner: " + owner);
                        if (sb.toString().startsWith("["))
                        {
                            JSONArray responseJSONObject = new JSONArray();
                            JSONArray requestJSONObject = new JSONArray(sb.toString());
                            for (int i = 0; i < requestJSONObject.length(); i++)
                            {
                                JSONObject obj = requestJSONObject.getJSONObject(i);
                                String method = obj.getString("method");
                                String id = obj.getString("id");
                                JSONObject params = obj.getJSONObject("params");
                                JSONObject responseMethod = execute(method, params, viewer, owner, site, gadget);
                                JSONObject part = new JSONObject();
                                part.put("id", id);
                                part.put("result", responseMethod);
                                responseJSONObject.put(part);
                            }
                            sendResponse(responseJSONObject.toString(4), response);


                        }
                        else
                        {
                            log.warn("warning: " + sb.toString());
                        }
                    }
                }
                catch (Exception e)
                {
                    log.debug(e);
                }
            }
            else
            {
                log.warn("warning values: " + values.length);
            }
        }
        else
        {
            log.warn("warning request.getContentType(): " + request.getContentType());
        }



    }
}
