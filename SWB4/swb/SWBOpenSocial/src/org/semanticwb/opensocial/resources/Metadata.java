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

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.nio.charset.Charset;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.User;
import org.semanticwb.model.WebSite;
import org.semanticwb.opensocial.model.FeatureDetail;
import org.semanticwb.opensocial.model.Gadget;
import org.semanticwb.opensocial.model.View;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;

/**
 *
 * @author victor.lorenzana
 */
public class Metadata
{

    private static final Logger log = SWBUtils.getLogger(Metadata.class);

    /**
     *
     * @param request
     * @param country
     * @param language
     * @param view
     * @param container
     * @param moduleId Número de gadget, cuando el mismo gadget puede tenerse más de una vez
     * @param paramRequest
     * @param gadget
     * @return
     * @throws Exception
     */
    private JSONObject getMetadata(HttpServletRequest request, String moduleId, SocialUser user, Gadget gadget, SWBResourceURL renderURL, WebSite site,String language,String country) throws Exception
    {
        String port = "";
        if (request.getServerPort() != 80)
        {
            port = ":" + request.getServerPort();
        }
        URI here = new URI(request.getScheme() + "://" + request.getServerName() + port + request.getRequestURI());
        JSONObject metadata = new JSONObject();
        try
        {   if (gadget != null)
            {
                if (user != null)
                {
                    metadata.put("userPrefs", user.getJSONUserPrefs(gadget, moduleId));
                }
                String title=gadget.getTitle(user,language,country, moduleId);
                String description=gadget.getDescription(user,language, country, moduleId);
                metadata.put("title", title==null?"":title);
                metadata.put("moduleId", moduleId);
                metadata.put("titleUrl", gadget.getTitleUrl() == null ? "" : gadget.getTitleUrl().toString());
                metadata.put("description", description== null ? "" : description);
                metadata.put("author", gadget.getAuthor() == null ? "" : gadget.getAuthor());
                metadata.put("screenshot", gadget.getScreenshot() != null ? gadget.getScreenshot().toString() : "");
                metadata.put("author_email", gadget.getAuthorEmail() != null ? gadget.getAuthorEmail() : "");
                metadata.put("thumbnail", gadget.getThumbnail() != null ? gadget.getThumbnail().toString() : "");
                metadata.put("width", gadget.getWidth());
                metadata.put("height", gadget.getHeight());
                URI uri = new URI(gadget.getUrl());
                if (!uri.isAbsolute())
                {
                    uri = here.resolve(uri);
                }
                metadata.put("url", uri.toURL());

                JSONArray features = new JSONArray();
                for (String feature : gadget.getAllFeatures())
                {
                    features.put(feature);
                }
                metadata.put("features", features);
                JSONArray categories = new JSONArray();
                for (String category : gadget.getCategories())
                {
                    categories.put(category);
                }
                metadata.put("categories", categories);

                View[] views = gadget.getViews();
                JSONObject jviews = new JSONObject();
                metadata.put("views", jviews);
                for (View oview : views)
                {
                    jviews.put(oview.getName(), oview.toJSONObject());
                }
                for (FeatureDetail detail : gadget.getFeatureDetails())
                {
                    metadata.accumulate("featureDetails", detail.toJSONObject());
                }                
            }
            else
            {
                throw new Exception("The gadget was not found");
            }
        }
        catch (Exception e)
        {
            log.debug(e);
            throw e;
        }
        return metadata;
    }

    public void doProcess(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        WebSite site = paramRequest.getWebPage().getWebSite();
        if (paramRequest.getCallMethod() == paramRequest.Call_DIRECT && request.getContentType().startsWith("application/javascript"))
        {

            String port = "";
            if (request.getServerPort() != 80)
            {
                port = ":" + request.getServerPort();
            }
            try
            {
                URI here = new URI(request.getScheme() + "://" + request.getServerName() + port + request.getRequestURI());


                
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
                try
                {

                    JSONObject json = new JSONObject(sb.toString());
                    JSONObject context = json.getJSONObject("context");
                    String country = context.getString("country");
                    String language = context.getString("language");
                    String view = context.getString("view");
                    String container = context.getString("container");

                    JSONArray gadgets = json.getJSONArray("gadgets");
                    JSONObject objresponse = new JSONObject();
                    JSONArray array = new JSONArray();

                    objresponse.put("gadgets", array);
                    SocialUser socialuser = null;
                    User user = paramRequest.getUser();                    

                    socialuser = SocialContainer.getSocialUser(user, request.getSession(),site);
                    if (socialuser != null)
                    {
                        for (int i = 0; i < gadgets.length(); i++)
                        {
                            JSONObject gadget = gadgets.getJSONObject(i);
                            String url = gadget.getString("url");
                            URI urigadget = new URI(url);
                            if (!urigadget.isAbsolute())
                            {
                                urigadget = here.resolve(urigadget);
                            }
                            Gadget ogadget = SocialContainer.getGadget(urigadget.toString(), paramRequest.getWebPage().getWebSite());
                            String moduleId = gadget.getString("moduleId");
                            if (ogadget != null)
                            {
                                JSONObject metadata = getMetadata(request, moduleId, socialuser, ogadget, paramRequest.getRenderUrl(), site,language, country);
                                array.put(metadata);
                            }
                            else
                            {
                                throw new Exception("The gadget was not found with url" + url);
                            }
                        }
                    }
                    Charset utf8 = Charset.forName("utf-8");
                    response.setContentType("JSON;charset=" + utf8.name() + "");
                    OutputStream out = response.getOutputStream();
                    out.write(objresponse.toString().getBytes(utf8));
                    out.close();

                }
                catch (JSONException jsone)
                {
                    log.error(jsone);                    
                }
                catch (Exception jsone)
                {
                    log.error(jsone);                    
                }
            }
            catch (Exception e)
            {
                log.error(e);                
            }
        }
    }
}
