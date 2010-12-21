/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.opensocial.resources;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.GenericIterator;
import org.semanticwb.model.User;
import org.semanticwb.opensocial.model.FeatureDetail;
import org.semanticwb.opensocial.model.Gadget;
import org.semanticwb.opensocial.model.PersonalizedGadged;
import org.semanticwb.opensocial.model.UserPref;
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

    private void adduserPrefs(JSONObject metadata, User user, Gadget gadget, String moduleId) throws JSONException
    {
        // debe obtener las preferencias del usuario al momento de configurar el gadget
        JSONObject adduserPrefs = new JSONObject();
        Iterator<PersonalizedGadged> preferences = PersonalizedGadged.ClassMgr.listPersonalizedGadgedByUser(user);
        while (preferences.hasNext())
        {
            PersonalizedGadged personalizedGadged = preferences.next();
            if (personalizedGadged.getGadget().getURI().equals(gadget.getURI()) && personalizedGadged.getId().equals(moduleId))
            {
                GenericIterator<UserPref> list = personalizedGadged.listUserPrefses();
                while (list.hasNext())
                {
                    UserPref pref = list.next();
                    adduserPrefs.put(pref.getKey(), pref.getValue());
                }
                break;
            }
        }
        metadata.put("userPrefs", adduserPrefs);
    }

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
    private JSONObject getMetadata(HttpServletRequest request, String country, String language, String view, String container, String moduleId, SWBParamRequest paramRequest, Gadget gadget) throws Exception
    {
        String port = "";
        if (request.getServerPort() != 80)
        {
            port = ":" + request.getServerPort();
        }
        URI here = new URI(request.getScheme() + "://" + request.getServerName() + port + request.getRequestURI());
        JSONObject metadata = new JSONObject();
        try
        {

            //String queryString = "?country=" + country + "&lang=" + language + "&view=" + view + "&container=" + container + "&moduleId=" + moduleId;

            if (gadget != null)
            {
                adduserPrefs(metadata, paramRequest.getUser(), gadget, moduleId);
                //String data = "{\"gadgets\":[{\"userPrefs\":{},\"moduleId\":1,\"screenshot\":\"\",\"singleton\":false,\"width\":0,\"authorLink\":\"\",\"links\":{},\"iframeUrl\":\"//http://localhost:8080/swb/gadgets/ifr?url=http%3A%2F%2Flocalhost%3A8080%2Fswb%2Fsamplecontainer%2Fexamples%2FSocialHelloWorld.xml&container=default&view=%25view%25&lang=%25lang%25&country=%25country%25&debug=%25debug%25&nocache=%25nocache%25&v=b4ea67fd7aa33422aa257ee3f534daf0&st=%25st%25\",\"url\":\"http://localhost:8080/swb/samplecontainer/examples/SocialHelloWorld.xml\",\"scaling\":false,\"title\":\"Social Hello World\",\"height\":0,\"titleUrl\":\"\",\"thumbnail\":\"http://localhost:8080/\",\"scrolling\":false,\"views\":{\"default\":{\"preferredHeight\":0,\"quirks\":true,\"type\":\"html\",\"preferredWidth\":0}},\"featureDetails\":{\"dynamic-height\":{\"parameters\":{},\"required\":true},\"osapi\":{\"parameters\":{},\"required\":true},\"core\":{\"parameters\":{},\"required\":true},\"settitle\":{\"parameters\":{},\"required\":true}},\"features\":[\"dynamic-height\",\"osapi\",\"core\",\"settitle\"],\"showStats\":false,\"categories\":[\"\",\"\"],\"showInDirectory\":false,\"authorPhoto\":\"\"}]}";

                metadata.put("title", gadget.getTitle());
                metadata.put("titleUrl", gadget.getTitleUrl());
                metadata.put("description", gadget.getDescription());
                metadata.put("author", gadget.getAuthor());
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
                for (String feature : gadget.getFeatures())
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
                if (views.length == 1)
                {
                    metadata.put("views", views[0].toJSONObject());
                }
                else
                {

                    for (View oview : views)
                    {
                        metadata.accumulate("views", oview.toJSONObject());
                    }

                }
                for (FeatureDetail detail : gadget.getFeatureDetails())
                {
                    metadata.accumulate("featureDetails", detail);
                }


                //metadata.put("url", gadget.getUrl());
                //metadata.put("singleton", false);

                /*metadata.put("authorLink", "");
                metadata.put("links", "");
                metadata.put("url", gadget.getUrl());
                metadata.put("scaling", false);
                
                
                
                
                metadata.put("views", new JSONObject());
                
                metadata.put("showStats",false);
                metadata.put("showInDirectory",false);
                metadata.put("authorPhoto",false);*/



                SWBResourceURL iframeurl = paramRequest.getRenderUrl();
                iframeurl.setMode(SocialContainer.Mode_IFRAME);
                iframeurl.setCallMethod(SWBResourceURL.Call_DIRECT);
                ///&container=default&view=%25view%25&lang=%25lang%25&country=%25country%25&debug=%25debug%25&nocache=%25nocache%25&v=b4ea67fd7aa33422aa257ee3f534daf0&st=%25st%25
                System.out.println("gadget.getUrl(): " + gadget.getUrl());
                iframeurl.setParameter("url", gadget.getUrl());
                iframeurl.setParameter("container", "default");
                iframeurl.setParameter("view", "");
                iframeurl.setParameter("nocache", "");
                iframeurl.setParameter("v", "b4ea67fd7aa33422aa257ee3f534daf0");
                iframeurl.setParameter("st", ""); // Token
                //String iframeUrl="http://localhost:8080/swb/gadgets/ifr?url=http%3A%2F%2Flocalhost%3A8080%2Fswb%2Fsamplecontainer%2Fexamples%2FSocialHelloWorld.xml&container=default&view=%25view%25&lang=%25lang%25&country=%25country%25&debug=%25debug%25&nocache=%25nocache%25&v=b4ea67fd7aa33422aa257ee3f534daf0&st=%25st%25";
                //String iframeUrl="http://localhost:8080"+ SWBPortal.getContextPath()+SWBPortal.getDistributorPath()+"/"+config.getServletContextName()+"/ifr?url="+ URLEncoder.encode(url) +"";
                metadata.put("iframeUrl", iframeurl.toString());
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

    public static void main(String[] args)
    {
        String data = "{\"gadgets\":[{\"userPrefs\":{},\"moduleId\":1,\"screenshot\":\"\",\"singleton\":false,\"width\":0,\"authorLink\":\"\",\"links\":{},\"iframeUrl\":\"//http://localhost:8080/swb/gadgets/ifr?url=http%3A%2F%2Flocalhost%3A8080%2Fswb%2Fsamplecontainer%2Fexamples%2FSocialHelloWorld.xml&container=default&view=%25view%25&lang=%25lang%25&country=%25country%25&debug=%25debug%25&nocache=%25nocache%25&v=b4ea67fd7aa33422aa257ee3f534daf0&st=%25st%25\",\"url\":\"http://localhost:8080/swb/samplecontainer/examples/SocialHelloWorld.xml\",\"scaling\":false,\"title\":\"Social Hello World\",\"height\":0,\"titleUrl\":\"\",\"thumbnail\":\"http://localhost:8080/\",\"scrolling\":false,\"views\":{\"default\":{\"preferredHeight\":0,\"quirks\":true,\"type\":\"html\",\"preferredWidth\":0}},\"featureDetails\":{\"dynamic-height\":{\"parameters\":{},\"required\":true},\"osapi\":{\"parameters\":{},\"required\":true},\"core\":{\"parameters\":{},\"required\":true},\"settitle\":{\"parameters\":{},\"required\":true}},\"features\":[\"dynamic-height\",\"osapi\",\"core\",\"settitle\"],\"showStats\":false,\"categories\":[\"\",\"\"],\"showInDirectory\":false,\"authorPhoto\":\"\"}]}";
        try
        {
            JSONObject obj = new JSONObject(data);
            System.out.println(obj.toString(10));
        }
        catch (Exception e)
        {
        }
    }

    public void doProcess(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
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


                String st = request.getParameter("st");
                System.out.println("st: " + st);
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
                    // peticion
                    //String data="{\"context\":{\"country\":\"default\",\"language\":\"default\",\"view\":\"default\",\"container\":\"default\"},\"gadgets\":[{\"url\":\"http://localhost:8080/swb/samplecontainer/examples/SocialHelloWorld.xml\",\"moduleId\":1}]}";
                    JSONObject context = json.getJSONObject("context");
                    String country = context.getString("country");
                    String language = context.getString("language");
                    String view = context.getString("view");
                    String container = context.getString("container");

                    JSONArray gadgets = json.getJSONArray("gadgets");
                    JSONObject objresponse = new JSONObject();
                    JSONArray array = new JSONArray();

                    objresponse.put("gadgets", array);
                    for (int i = 0; i < gadgets.length(); i++)
                    {
                        JSONObject gadget = gadgets.getJSONObject(i);
                        String url = gadget.getString("url");
                        URI urigadget=new URI(url);
                        if(!urigadget.isAbsolute())
                        {
                            urigadget=here.resolve(urigadget);
                        }                        
                        Gadget g = Gadget.ClassMgr.createGadget(paramRequest.getWebPage().getWebSite());
                        g.setUrl(urigadget.toString());
                        Gadget ogadget = SocialContainer.getGadget(urigadget.toString(), paramRequest.getWebPage().getWebSite());
                        String moduleId = gadget.getString("moduleId");
                        JSONObject metadata = getMetadata(request, country, language, view, container, moduleId, paramRequest, ogadget);
                        array.put(metadata);
                        g.remove();
                    }
                    //String data = "{\"gadgets\":[{\"userPrefs\":{},\"moduleId\":1,\"screenshot\":\"\",\"singleton\":false,\"width\":0,\"authorLink\":\"\",\"links\":{},\"iframeUrl\":\"//http://localhost:8080/swb/gadgets/ifr?url=http%3A%2F%2Flocalhost%3A8080%2Fswb%2Fsamplecontainer%2Fexamples%2FSocialHelloWorld.xml&container=default&view=%25view%25&lang=%25lang%25&country=%25country%25&debug=%25debug%25&nocache=%25nocache%25&v=b4ea67fd7aa33422aa257ee3f534daf0&st=%25st%25\",\"url\":\"http://localhost:8080/swb/samplecontainer/examples/SocialHelloWorld.xml\",\"scaling\":false,\"title\":\"Social Hello World\",\"height\":0,\"titleUrl\":\"\",\"thumbnail\":\"http://localhost:8080/\",\"scrolling\":false,\"views\":{\"default\":{\"preferredHeight\":0,\"quirks\":true,\"type\":\"html\",\"preferredWidth\":0}},\"featureDetails\":{\"dynamic-height\":{\"parameters\":{},\"required\":true},\"osapi\":{\"parameters\":{},\"required\":true},\"core\":{\"parameters\":{},\"required\":true},\"settitle\":{\"parameters\":{},\"required\":true}},\"features\":[\"dynamic-height\",\"osapi\",\"core\",\"settitle\"],\"showStats\":false,\"categories\":[\"\",\"\"],\"showInDirectory\":false,\"authorPhoto\":\"\"}]}";                    
                    response.setContentType("JSON");
                    OutputStream out = response.getOutputStream();
                    out.write(objresponse.toString().getBytes());
                    out.close();
                }
                catch (JSONException jsone)
                {
                    jsone.printStackTrace();
                }
                catch (Exception jsone)
                {
                    jsone.printStackTrace();
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
}
