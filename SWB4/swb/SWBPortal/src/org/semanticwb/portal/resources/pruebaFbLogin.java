/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.portal.resources;

import com.google.code.facebookapi.FacebookException;
import com.google.code.facebookapi.FacebookXmlRestClient;
import com.google.code.facebookapi.IFacebookRestClient;
import com.google.code.facebookapi.ProfileField;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.portal.api.GenericAdmResource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.social.facebook.util.FacebookUtils;
import org.w3c.dom.Document;

/**
 *
 * @author Jorge Jiménez
 */
public class pruebaFbLogin extends GenericAdmResource {

    private static Logger log = SWBUtils.getLogger(pruebaFbLogin.class);
    private static String API_KEY = "9cfacdb5b2aa5c2ccd3ea0d6f8778f1f";
    private static String SECRET_KEY = "916826119a64fac501dc9f69ffb276bb";
    private static final String FACEBOOK_USER_CLIENT = "facebook.user.client";

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        try {
            PrintWriter out=response.getWriter();
            FacebookUtils facebookUtils=new FacebookUtils();
            HttpSession session = request.getSession(true);
            IFacebookRestClient<Document> userClient = getUserClient(session);
            if (userClient != null) {
                long facebookUserID = userClient.users_getLoggedInUser();
                if (userClient.friends_get() != null) {
                    String friends = SWBUtils.XML.domToXml(userClient.friends_get());
                    //System.out.println("Mis amigos:"+friends);
                }
                
                Iterator <String>itAlbums=facebookUtils.getAlbums(userClient.photos_getAlbums(facebookUserID)).iterator();
                while(itAlbums.hasNext()){
                    String aid=itAlbums.next();
                    Iterator <String>itPhotos=facebookUtils.getPhotos(userClient.photos_getByAlbum(new Long(aid).longValue())).iterator();
                    while(itPhotos.hasNext()){
                        String photoUrl=itPhotos.next();
                        out.println("<img src=\""+photoUrl+"\"/>");
                    }
                }

             //System.out.println("UserInfo:" + printUserInfo(userClient.getCacheUserId(), (FacebookXmlRestClient)userClient, userClient.getCacheSessionKey()));
            }
        } catch (Exception e) {
            log.error(e);
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
}
