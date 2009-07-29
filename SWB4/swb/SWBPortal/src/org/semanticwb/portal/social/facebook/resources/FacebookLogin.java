/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.portal.social.facebook.resources;

import com.google.code.facebookapi.FacebookXmlRestClient;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.StringTokenizer;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Resource;
import org.semanticwb.portal.api.GenericAdmResource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;

/**
 *
 * @author Jorge Jiménez
 */
public class FacebookLogin extends GenericAdmResource {

    private static Logger log = SWBUtils.getLogger(FacebookLogin.class);
    private static String API_KEY = "";
    private static String SECRET_KEY = "";
    private static String redirect = "";
    private static final String FACEBOOK_USER_CLIENT = "facebook.user.client";

    /**
     * @param base
     */
    @Override
    public void setResourceBase(Resource base) {
        try {
            super.setResourceBase(base);
            API_KEY=base.getAttribute("api_key");
            SECRET_KEY=base.getAttribute("secret_key");
        }catch(Exception e) {
            log.error("Error while setting resource base: "+base.getId() +"-"+ base.getTitle(), e);
        }
    }

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        redirect=paramRequest.getResourceBase().getAttribute("redirect",paramRequest.getWebPage().getUrl());
       
        PrintWriter out = response.getWriter();
        String sessionKey = "";
        HttpSession session = request.getSession(true);
        try {
            if (request.getParameter("swb") == null && getUserClient(session, redirect) == null) {
                out.println(getStr1(redirect));
            } else if (request.getParameter("session") == null) {
                if(request.getParameter("swb")==null) out.println(getStr2(redirect));
                out.println("<script src=\"http://static.ak.connect.facebook.com/js/api_lib/v0.4/XdCommReceiver.js\" type=\"text/javascript\"></script>");
            } else {
                out.println("<script src=\"http://static.ak.connect.facebook.com/js/api_lib/v0.4/XdCommReceiver.js\" type=\"text/javascript\"></script>");
                HashMap hParams = new HashMap();
                String sessionJ = request.getParameter("session");
                if (sessionJ != null) {
                    hParams = getParams(sessionJ);
                }
                sessionKey = (String) hParams.get("session_key");
                FacebookXmlRestClient userClient = new FacebookXmlRestClient(API_KEY, SECRET_KEY, sessionKey);
                session.setAttribute(FACEBOOK_USER_CLIENT, userClient);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error(ex);
        }
    }

    static FacebookXmlRestClient getUserClient(HttpSession session, String redirect) {
        return (FacebookXmlRestClient) session.getAttribute(FACEBOOK_USER_CLIENT);
    }

     private String getStr1(String redirect){
        StringBuffer strbf=new StringBuffer();
        strbf.append("<div id=\"user\"><fb:login-button onlogin=\"update_user_box();\"></fb:login-button>");
        strbf.append("</div>");
        strbf.append("<script type=\"text/javascript\">");
        strbf.append("function update_user_box() {");
        strbf.append("var user_box = document.getElementById(\"user\");");
        strbf.append("user_box.innerHTML =\"");
        strbf.append("<span>");
        strbf.append("<fb:profile-pic uid=loggedinuser facebook-logo=true></fb:profile-pic>");
        strbf.append("Bienvenido, <fb:name uid=loggedinuser useyou=false></fb:name>. Ahora estas firmado con tu cuenta de Facebook.");
        strbf.append("</span>\";");
        strbf.append("FB.XFBML.Host.parseDomTree();");
        strbf.append("}");
        strbf.append("</script>");
        strbf.append("<script type=\"text/javascript\" src=\"http://static.ak.connect.facebook.com/js/api_lib/v0.4/FeatureLoader.js.php\"></script>");
        strbf.append("<script type=\"text/javascript\">");
        strbf.append("FB.init(\""+API_KEY+"\",\""+redirect+"?swb=1\");");
        strbf.append("</script>");
        return strbf.toString();
    }


    private String getStr2(String redirect){
        StringBuffer strbf=new StringBuffer();
        strbf.append("<div id=\"user\">");
        strbf.append("</div>");
        strbf.append("<script type=\"text/javascript\">");
        strbf.append("var user_box = document.getElementById(\"user\");");
        strbf.append("user_box.innerHTML =\"");
        strbf.append("<span>");
        strbf.append("<fb:profile-pic uid=loggedinuser facebook-logo=true></fb:profile-pic>");
        strbf.append("Bienvenido, <fb:name uid=loggedinuser useyou=false></fb:name>. Ahora estas firmado con tu cuenta de Facebook.");
        strbf.append("</span>\";");
        strbf.append("FB.XFBML.Host.parseDomTree();");
        strbf.append("</script>");
        strbf.append("<script type=\"text/javascript\" src=\"http://static.ak.connect.facebook.com/js/api_lib/v0.4/FeatureLoader.js.php\"></script>");
        strbf.append("<script type=\"text/javascript\">");
        strbf.append("FB.init(\""+API_KEY+"\",\""+redirect+"?swb=1\");");
        strbf.append("</script>");
        return strbf.toString();
    }

    private HashMap getParams(String param) {
        HashMap hParams = new HashMap();
        param = SWBUtils.TEXT.findInterStr(param, "{", "}").next();
        if (param != null) {
            StringTokenizer strTokens = new StringTokenizer(param, ",");
            while (strTokens.hasMoreTokens()) {
                String token = strTokens.nextToken();
                if (token == null) {
                    continue;
                }
                String key = null;
                String value = null;
                int cont = 0;
                StringTokenizer strToken2 = new StringTokenizer(token, ":");
                while (strToken2.hasMoreTokens()) {
                    String token2 = strToken2.nextToken();
                    if (token2 == null) {
                        continue;
                    }
                    cont++;
                    if (cont == 1) {
                        key = token2;
                    } else if (cont == 2) {
                        value = token2;
                    }
                }
                key = key.substring(1, key.lastIndexOf("\""));
                if (value.startsWith("\"")) {
                    value = value.substring(1, value.lastIndexOf("\""));
                }
                hParams.put(key, value);
            }
        }
        return hParams;
    }
}