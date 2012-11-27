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
package org.semanticwb.portal.social.facebook.resources;

import com.google.code.facebookapi.FacebookXmlRestClient;
import com.google.code.facebookapi.IFacebookRestClient;
import com.google.code.facebookapi.ProfileField;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Resource;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.social.facebook.Fb_User;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;



// TODO: Auto-generated Javadoc
/**
 * Resource that manage the integration between facebook and semanticwebbuilder (facebook java api)
 * and returns the user facebook friends (that is in the facebook session).
 * 
 * @author Jorge Jiménez
 */
public class FB_MyFriends extends GenericResource {

    /** The log. */
    private static Logger log = SWBUtils.getLogger(FB_MyPhotos.class);
    
    /** The Constant FACEBOOK_USER_CLIENT. */
    private static final String FACEBOOK_USER_CLIENT = "facebook.user.client";
   
    /**
     * Sets the resource base.
     * 
     * @param base the new resource base
     */
    @Override
    public void setResourceBase(Resource base) {
        try {
            super.setResourceBase(base);           
        } catch (Exception e) {
            log.error("Error while setting resource base: " + base.getId() + "-" + base.getTitle(), e);
        }
    }

    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.GenericResource#doView(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.semanticwb.portal.api.SWBParamRequest)
     */
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        try {
            HttpSession session = request.getSession(true);
            IFacebookRestClient<Document> userClient = getUserClient(session);
            if (userClient != null) {
                String api_key=userClient.getApiKey();
                String secret_key=userClient.getSecret();
                ArrayList<Fb_User> aUserData=new ArrayList();
                if (userClient.friends_get() != null) {
                    NodeList nlistUsers = userClient.friends_get().getElementsByTagName("uid");
                    for (int i = 0; i < nlistUsers.getLength(); i++) {
                        Fb_User userData=new Fb_User();
                        Long luid = new Long(nlistUsers.item(i).getFirstChild().getNodeValue());
                        ArrayList<Long> uids = new ArrayList<Long>(1);
                        uids.add(luid);
                        List<ProfileField> fields = Arrays.asList(ProfileField.values());
                        userClient = new FacebookXmlRestClient(api_key, secret_key, userClient.getCacheSessionKey());
                        Document userInfoDoc = userClient.users_getInfo(uids, fields);
                        if(userInfoDoc.getElementsByTagName("user").getLength()>0)
                        {
                            NodeList nUsrNodes=userInfoDoc.getElementsByTagName("user").item(0).getChildNodes();
                            for(int y=0;y<nUsrNodes.getLength();y++){
                                if(nUsrNodes.item(y).getNodeName().equals("name")) {
                                    userData.setName(nUsrNodes.item(y).getFirstChild().getNodeValue());
                                }
                                else if (nUsrNodes.item(y).getNodeName().equals("profile_url") && nUsrNodes.item(y).getFirstChild() != null) {
                                    userData.setProfile_url( nUsrNodes.item(y).getFirstChild().getNodeValue());
                                }
                                else if (nUsrNodes.item(y).getNodeName().equals("pic_with_logo") && nUsrNodes.item(y).getFirstChild() != null) {
                                    userData.setPic_with_logo(nUsrNodes.item(y).getFirstChild().getNodeValue());
                                }else if (nUsrNodes.item(y).getNodeName().equals("birthday") && nUsrNodes.item(y).getFirstChild() != null) {
                                    userData.setBirthday(nUsrNodes.item(y).getFirstChild().getNodeValue());
                                }
                                else if(nUsrNodes.item(y).getNodeName().equals("hometown_location")) {
                                    NodeList nHomeTown=nUsrNodes.item(y).getChildNodes();
                                    for(int z=0;z<nHomeTown.getLength();z++){
                                        if(nHomeTown.item(z).getNodeName().equals("city") && nHomeTown.item(z).getFirstChild()!=null) userData.setCity(nHomeTown.item(z).getFirstChild().getNodeValue());
                                        else if(nHomeTown.item(z).getNodeName().equals("state") && nHomeTown.item(z).getFirstChild()!=null) userData.setState(nHomeTown.item(z).getFirstChild().getNodeValue());
                                        else if(nHomeTown.item(z).getNodeName().equals("country") && nHomeTown.item(z).getFirstChild()!=null) userData.setCountry(nHomeTown.item(z).getFirstChild().getNodeValue());
                                    }
                                }
                            }
                            aUserData.add(userData);
                        }
                    }
                    request.setAttribute("aUserData", aUserData);
                    RequestDispatcher rd = request.getRequestDispatcher("/swbadmin/jsp/facebook/fb_MyFriends.jsp");
                    rd.include(request, response);
                }
            }
        } catch (Exception e) {
            log.error(e);
        }
    }

    /**
     * Gets the user client.
     * 
     * @param session the session
     * @return the user client
     */
    static FacebookXmlRestClient getUserClient(HttpSession session) {
        return (FacebookXmlRestClient) session.getAttribute(FACEBOOK_USER_CLIENT);
    }
}
