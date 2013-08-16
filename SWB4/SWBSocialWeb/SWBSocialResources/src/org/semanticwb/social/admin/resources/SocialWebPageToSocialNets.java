/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.social.admin.resources;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javaQuery.j2ee.tinyURL;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.io.SWBFile;
import org.semanticwb.io.SWBFileInputStream;
import org.semanticwb.model.Language;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;
import org.semanticwb.social.Facebook;
import org.semanticwb.social.SocialNetwork;
import org.semanticwb.social.SocialWebPage;
import org.semanticwb.social.Twitter;
import twitter4j.Status;
import twitter4j.StatusUpdate;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

/**
 *
 * @author jorge.jimenez
 */
public class SocialWebPageToSocialNets extends GenericResource {

    private static final String CRLF = "\r\n";
    /**
     * Cadena utilizada en la construccion de peticiones HTTP POST al servidor
     * de Facebook
     */
    private static final String PREF = "--";
    private static Logger log = SWBUtils.getLogger(SocialWebPageToSocialNets.class);

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        final String myPath = SWBPlatform.getContextPath() + "/work/models/" + paramRequest.getWebPage().getWebSiteId() + "/jsp/post/socialWebPageToSocialNets.jsp";
        RequestDispatcher dis = request.getRequestDispatcher(myPath);
         PrintWriter out=response.getWriter();
          if(request.getParameter("statusMsg")!=null)
        {
            out.println("<script type=\"text/javascript\">");
            out.println("   showStatus('"+request.getParameter("statusMsg")+"');");       
            if(request.getParameter("reloadTab")!=null)
            {
                out.println(" reloadTab('" + request.getParameter("reloadTab") + "');");//so
            }            
            out.println("</script>");
            
        }
        if (dis != null) {
            try {
                request.setAttribute("paramRequest", paramRequest);
                dis.include(request, response);
            } catch (Exception ex) {
                log.error(ex);
                ex.printStackTrace(System.out);
            }
        }
    }

    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        if (paramRequest.getMode().equals("preview")) {
            doPreview(request, response, paramRequest);
        } else {
            super.processRequest(request, response, paramRequest);
        }
    }

    private void doPreview(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws IOException {
        RequestDispatcher rd = request.getRequestDispatcher(SWBPlatform.getContextPath() + "/work/models/" + paramRequest.getWebPage().getWebSiteId() + "/jsp/post/Preview.jsp");
        request.setAttribute("suri", request.getParameter("suri"));
        request.setAttribute("suriSite", request.getParameter("suriSite"));
        request.setAttribute("source", "RedSocial");
        request.setAttribute("paramRequest", paramRequest);

        try {
            rd.include(request, response);
        } catch (ServletException ex) {
            log.error("Error  " + ex.getMessage());
        }
    }

    public void doCreatePost(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        RequestDispatcher rd = request.getRequestDispatcher(SWBPlatform.getContextPath() + "/work/models/" + paramRequest.getWebPage().getWebSiteId() + "/jsp/post/typeOfContent.jsp");
        request.setAttribute("contentType", request.getParameter("valor"));
        request.setAttribute("wsite", request.getParameter("wsite"));
        request.setAttribute("objUri", request.getParameter("objUri"));
        //request.setAttribute("action", request.getParameter("action"));
        request.setAttribute("paramRequest", paramRequest);
        try {
            rd.include(request, response);
        } catch (ServletException ex) {
            log.error("Error al enviar los datos a typeOfContent.jsp " + ex.getMessage());
        }
    }

    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
        System.out.println("entro al process action");
        
        if (response.getAction().equals("uploadPhoto")) {
           
            String idSWB = URLDecoder.decode(request.getParameter("swb"));
            SocialWebPage swb = (SocialWebPage) SemanticObject.createSemanticObject(idSWB).createGenericInstance();
            SocialNetwork socialNetwork = null;
            Iterator i = swb.listSocialNetses();
           
            String photoToPublish = SWBPortal.getWorkPath() + swb.getWorkPath() + "/" + "socialwpPhoto_Pagina_Social_" + swb.getSocialwpPhoto();
            System.out.println("phtoopublsh" + photoToPublish);
           
            String suri = request.getParameter("suri");
            Language l = (Language) SemanticObject.createSemanticObject(suri).createGenericInstance();

            StringBuilder address = new StringBuilder(128);
            address.append("http://").append(request.getServerName()).append(":").append(request.getServerPort()).append("/").append(response.getUser().getLanguage()).append("/").append(response.getResourceBase().getWebSiteId()).append("/" + response.getWebPage().getId() + "/_rid/").append(response.getResourceBase().getId()).append("/_mod/").append(response.getMode()).append("/_lang/").append(response.getUser().getLanguage());
   
            
            while (i.hasNext()) {

                socialNetwork = (SocialNetwork) i.next();
                SemanticObject semanticObject = SemanticObject.createSemanticObject(socialNetwork.getURI());

                if (socialNetwork instanceof Facebook) {
                    Facebook facebook = (Facebook) semanticObject.createGenericInstance();
                    postPhoto(facebook, swb, l,  address );
                }

                if (socialNetwork instanceof Twitter) {
                    Twitter twitter = (Twitter) semanticObject.createGenericInstance();
                    postPhoto(twitter, swb, l,  address );
                }
            }
                    response.setMode(SWBResourceURL.Mode_VIEW);
                    response.setRenderParameter("statusMsg", "Publicado");
                    
        }
    }

    public void postPhoto(Facebook facebook, SocialWebPage swb, Language l, StringBuilder address ) {

        Map<String, String> params = new HashMap<String, String>(2);
        if (facebook.getAccessToken() != null) {
            params.put("access_token", facebook.getAccessToken());
        }

        String url = "https://graph.facebook.com/" + facebook.getFacebookUserId() + "/photos";
       

        try {
            String photoToPublish = "";
            String additionalPhotos = "";
            String urlShort = shortUrl(address.toString());
            String description =swb.getDescription(l.getId()) == null ? "" : swb.getDescription(l.getId());
            params.put("message", (description+urlShort) + " " + additionalPhotos);

            photoToPublish = SWBPortal.getWorkPath() + swb.getWorkPath() + "/" + "socialwpPhoto_Pagina_Social_" + swb.getSocialwpPhoto();
            SWBFile photoFile = new SWBFile(photoToPublish);

            if (photoFile.exists()) {
                SWBFileInputStream fileStream = new SWBFileInputStream(photoFile);
                String facebookResponse = "";
                facebookResponse = postFileRequest(params, url,
                        photoToPublish, fileStream, "POST", "photo");               

                
            }

           
        } catch (FileNotFoundException fnfe) {
           
        }  catch (IOException ioe) {
            
        }
    }

    private String postFileRequest(Map<String, String> params, String url, String fileName, InputStream fileStream, String method, String itemToPost) {
        HttpURLConnection conex = null;
        OutputStream urlOut = null;
        InputStream in = null;
        URL serverUrl = null;
        String facebookResponse = "{\"response\" : \"Sin procesar\"}";

        if (method == null) {
            method = "POST";
        }

        try {
            serverUrl = new URL(url);
            String boundary = "---MyFacebookFormBoundary" + Long.toString(System.currentTimeMillis(), 16);
            conex = (HttpURLConnection) serverUrl.openConnection();
            conex.setConnectTimeout(30000);
            conex.setReadTimeout(itemToPost.equals("photo") ? 60000 : 3600000);
            conex.setRequestMethod(method);
            conex.setDoInput(true);
            conex.setDoOutput(true);
            conex.setUseCaches(false);
            conex.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
            conex.setRequestProperty("MIME-version", "1.0");
            conex.connect();
            urlOut = conex.getOutputStream();
            DataOutputStream out = new DataOutputStream(urlOut);
            for (Map.Entry<String, String> entry : params.entrySet()) {
                out.writeBytes(PREF + boundary + CRLF);
                out.writeBytes("Content-Type: text/plain;charset=UTF-8" + CRLF);
                out.writeBytes("Content-disposition: form-data; name=\"" + entry.getKey() + "\"" + CRLF);
                out.writeBytes(CRLF);
                byte[] valueBytes = entry.getValue().toString().getBytes("UTF-8");
                out.write(valueBytes);
                out.writeBytes(CRLF);
            }
            out.writeBytes(PREF + boundary + CRLF);
            out.writeBytes("Content-Type: " + (itemToPost.equals("photo") ? "image" : "file") + CRLF);
            out.writeBytes("Content-disposition: form-data; filename=\"" + fileName + "\"" + CRLF);
            // Write the file
            out.writeBytes(CRLF);
            byte buf[] = new byte[1024];
            int len = 0;
            while (len >= 0) {
                out.write(buf, 0, len);
                len = fileStream.read(buf);
            }
            out.writeBytes(CRLF + PREF + boundary + PREF + CRLF);
            out.flush();
            in = conex.getInputStream();
            facebookResponse = getResponse(in);
        } catch (IOException ioe) {
    
            try {
                facebookResponse = getResponse(conex.getErrorStream());
            } catch (Exception e) {
            }
        } finally {
            close(urlOut);
            close(in);
            if (conex != null) {
                conex.disconnect();
            }
        }
        return facebookResponse;
    }

    private void close(Closeable c) {
        if (c != null) {
            try {
                c.close();
            } catch (IOException ex) {
                log.error("Error at closing object: " + c.getClass().getName(),
                        ex);
            }
        }
    }

    private static String getResponse(InputStream data) throws IOException {

        Reader in = new BufferedReader(new InputStreamReader(data, "UTF-8"));
        StringBuilder response = new StringBuilder(256);
        char[] buffer = new char[1000];
        int charsRead = 0;
        while (charsRead >= 0) {
            response.append(buffer, 0, charsRead);
            charsRead = in.read(buffer);
        }
        in.close();
        return response.toString();
    }

    private void postPhoto(Twitter t, SocialWebPage swp, Language l, StringBuilder address) {
   
        String photoToPublish = "";
        String additionalPhotos = "";
        String urlShort = shortUrl(address.toString());
            
            

        photoToPublish = SWBPortal.getWorkPath() + swp.getWorkPath() + "/" + "socialwpPhoto_Pagina_Social_" + swp.getSocialwpPhoto();
        twitter4j.Twitter twitter = new TwitterFactory().getInstance();
        twitter.setOAuthConsumer(t.getAppKey(), t.getSecretKey());
        AccessToken accessToken = new AccessToken(t.getAccessToken(), t.getAccessTokenSecret());
        String description = swp.getDescription(l.getId()) != null ? swp.getDescription(l.getId()) : "";
        twitter.setOAuthAccessToken(accessToken);

        try {
            twitter.setOAuthAccessToken(accessToken);
            StatusUpdate sup = null;// new StatusUpdate(new String(shortUrl(description).getBytes(), "ISO-8859-1"));
            Status stat = null;

            description = description + (additionalPhotos.trim().length() > 0 ? " " + additionalPhotos : ""); //
            sup = new StatusUpdate(new String((description+urlShort).getBytes(), "ISO-8859-1"));
            //sup.setMedia(new File(photoSend));
            sup.setMedia(new File(photoToPublish));
            stat = twitter.updateStatus(sup);
       } catch (UnsupportedEncodingException ex) {
            log.error("Exception" + ex);
        } catch (TwitterException ex) {
            log.error("Exception" + ex);
            if (401 == ex.getStatusCode()) {
                
            }
        }
    }
    
    private String shortUrl(String urlLong) {

        String mensajeNuevo = "";
        if (urlLong != null && !urlLong.equals("")) {
            String delimiter = " ";
            String[] temp = urlLong.split(delimiter);
            for (int i = 0; i < temp.length; i++) {
                if ((temp[i].startsWith("http://") || temp[i].startsWith("https://")) && ((temp[i].length() > 9))) {
                    tinyURL tU = new tinyURL();
                    temp[i] = tU.getTinyURL(temp[i]);
                }
                mensajeNuevo += temp[i] + " ";
            }
        }
        return mensajeNuevo;
    }
}
