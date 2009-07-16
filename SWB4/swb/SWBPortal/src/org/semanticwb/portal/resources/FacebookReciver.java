/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.portal.resources;

import com.google.code.facebookapi.FacebookException;
import com.google.code.facebookapi.FacebookXmlRestClient;
import com.google.code.facebookapi.Permission;
import com.google.code.facebookapi.ProfileField;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.portal.api.GenericAdmResource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.w3c.dom.Document;

/**
 *
 * @author Jorge Jiménez
 */
public class FacebookReciver extends GenericAdmResource {

    private static Logger log = SWBUtils.getLogger(Banner.class);
    private static String API_KEY = "9cfacdb5b2aa5c2ccd3ea0d6f8778f1f";
    private static String SECRET_KEY = "916826119a64fac501dc9f69ffb276bb";
    private static final String FACEBOOK_USER_CLIENT = "facebook.user.client";

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        try {
            System.out.println("Entra a FacebookReciver");
            request.setAttribute("paramRequest", paramRequest);
            RequestDispatcher rd = request.getRequestDispatcher("/swbadmin/jsp/facebook/xd_receiver.jsp");
            rd.include(request, response);

            try {
                String sessionKey = "";
                FacebookXmlRestClient userClient = null;
                HttpSession session = request.getSession(true);
                if (request.getParameter("session") == null) {
                    System.out.println("entra a if1:" + session.getAttribute(FACEBOOK_USER_CLIENT));
                    userClient = userClient = (FacebookXmlRestClient) session.getAttribute(FACEBOOK_USER_CLIENT);
                    sessionKey = userClient.getCacheSessionKey();
                } else {
                    System.out.println("entra a else:" + request.getParameter("session"));
                    HashMap hParams = new HashMap();
                    String sessionJ = request.getParameter("session");
                    if (sessionJ != null) {
                        hParams = getParams(sessionJ);
                    }

                    sessionKey = (String) hParams.get("session_key");
                    userClient = new FacebookXmlRestClient(API_KEY, SECRET_KEY, sessionKey);
                    session.setAttribute(FACEBOOK_USER_CLIENT, userClient);
                }
                long facebookUserID = userClient.users_getLoggedInUser();
                System.out.println("facebookUserID-Jorgito:" + facebookUserID);
                if (userClient.friends_get() != null) {
                    System.out.println("Tiene amigos");
                    String friends = SWBUtils.XML.domToXml(userClient.friends_get());
                    System.out.println("friends:" + friends);
                }
                System.out.println("sessionKey-George:" + sessionKey);
                System.out.println("UserInfo:" + printUserInfo(facebookUserID, userClient, sessionKey));

                if (userClient.users_hasAppPermission(Permission.STATUS_UPDATE)) {
                    out.println("Actualiza Status a FaceBook");
                    userClient.users_setStatus("PRUEBA DE STATUS DE JORGE DESDE SWB", false);
                }

            } catch (Exception e) {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error while fetching user's facebook ID");
                log.error("Error while getting cached (supplied by request params) value of the user's facebook ID or while fetching it from the Facebook service if the cached value was not present for some reason. Cached value = {}");
                return;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static FacebookXmlRestClient getUserClient(HttpSession session) {
        return (FacebookXmlRestClient) session.getAttribute(FACEBOOK_USER_CLIENT);
    }

    private String printUserInfo(Long uid, FacebookXmlRestClient client, String sessionKey) throws FacebookException {
        StringBuffer ret = new StringBuffer();
        //init array parameter
        ArrayList<Long> uids = new ArrayList<Long>(1);
        uids.add(uid);
        //init field parameter - we choose all profile infos.
        List<ProfileField> fields = Arrays.asList(ProfileField.values());
        //init the client in order to make the xml request
        client = new FacebookXmlRestClient(API_KEY, SECRET_KEY, sessionKey);
        //get the xml document containing the infos
        Document userInfoDoc = client.users_getInfo(uids, fields);
        //for each info append it to returned string buffer
        for (ProfileField pfield : fields) {
            ret.append(pfield.fieldName()).append(" <b>").append(userInfoDoc.getElementsByTagName(pfield.fieldName()).item(0).getTextContent()).append("</b>");
            ret.append("</br>");
        }
        return ret.toString();
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
