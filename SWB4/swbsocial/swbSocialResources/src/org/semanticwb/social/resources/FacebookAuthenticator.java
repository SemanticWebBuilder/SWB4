package org.semanticwb.social.resources;


import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Iterator;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.WebSite;
import org.semanticwb.portal.admin.resources.reports.datadetail.SessionDataDetail;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.social.Facebook;


/**
 *
 * @author jose.jimenez
 */
public class FacebookAuthenticator extends GenericResource {

    
    private static Logger log = SWBUtils.getLogger(FacebookAuthenticator.class);
    
    private static final char[] HEXADECIMAL = { '0', '1', '2', '3', '4', '5',
                                                '6', '7', '8', '9', 'a', 'b',
                                                'c', 'd', 'e', 'f' };

    
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response,
            SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        
        String jspResponse = "/swbadmin/jsp/social/authentication/FacebookAuth.jsp";
        
        RequestDispatcher dis = request.getRequestDispatcher(jspResponse);
        try {
            request.setAttribute("paramRequest", paramRequest);
            request.setAttribute("hashedId", hash(Double.toString(Math.random())));
            
            if (request.getParameter("faceAccount") != null) {
                request.getSession(true).setAttribute("facebookId",
                        request.getParameter("faceAccount"));
            }
            
            dis.include(request, response);
        } catch (Exception e) {
            log.error(e);
        }
    }

    @Override
    public void processAction(HttpServletRequest request,
            SWBActionResponse response) throws SWBResourceException, IOException {
        
        WebSite wsite = response.getWebPage().getWebSite();
        String action = response.getAction();
        HttpSession session = request.getSession();
        
        if (action.equals("saveToken")) {
            //Se almacena el access_token asignado por Facebook
            if (((String) session.getAttribute("facebookId")) != null) {
                String facebookAccId = (String) session.getAttribute("facebookId");
                
                Iterator itFacebookAccs = Facebook.ClassMgr.listFacebooks();
                Facebook facebookInst = null;
                while (itFacebookAccs.hasNext()) {
                    facebookInst = (Facebook) itFacebookAccs.next();
                    if (facebookInst.getId().equals(facebookAccId)) {
                        break;
                    }
                }
                if (facebookInst != null) {
                    String accessToken = request.getParameter("token");
                    String facebookUserId = request.getParameter("userId");
                    facebookInst.setAccessToken(accessToken);
                    facebookInst.setFacebookUserId(facebookUserId);
                    if (session.getAttribute("baseSeconds") != null &&
                            session.getAttribute("secsToExpiration") != null) {
                        long secondsToExpiration = Long.parseLong(
                                (String) session.getAttribute("baseSeconds"))
                                + Long.parseLong(
                                (String) session.getAttribute("secsToExpiration"));
                        facebookInst.setTokenExpirationDate(new Date(secondsToExpiration));
                    }
                }
                //Se eliminan parametros usados en el proceso de autenticacion
                session.removeAttribute("accessToken");
                session.removeAttribute("baseSeconds");
                session.removeAttribute("secsToExpiration");
                session.removeAttribute("facebookId");
            }
        }
    }
    
    /**
     * Genera un hash de la cadena recibida utilizando el algoritmo MD5
     * @param stringToHash cadena a representar en un hash
     * @return la representaci&oacute;n en hash del objeto {@code String} cadena recibida
     */
    public String hash(String stringToHash)  {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] bytes = md.digest(stringToHash.getBytes());
            StringBuilder sb = new StringBuilder(2 * bytes.length);
            for (int i = 0; i < bytes.length; i++) {
                int low = (int)(bytes[i] & 0x0f);
                int high = (int)((bytes[i] & 0xf0) >> 4);
                sb.append(HEXADECIMAL[high]);
                sb.append(HEXADECIMAL[low]);
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            //exception handling goes here
            return null;
        }
    }
}
