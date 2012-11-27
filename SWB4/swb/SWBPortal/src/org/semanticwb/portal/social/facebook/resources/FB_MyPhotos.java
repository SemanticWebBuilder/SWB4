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
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.social.facebook.Fb_Photo;
import org.semanticwb.portal.social.facebook.util.FacebookUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

// TODO: Auto-generated Javadoc
/**
 * Resource that manage the integration between facebook and semanticwebbuilder (facebook java api)
 * and returns the user facebook photos (that is in the facebook session).
 * 
 * @author Jorge Jiménez
 */
public class FB_MyPhotos extends GenericResource {

    /** The log. */
    private static Logger log = SWBUtils.getLogger(FB_MyPhotos.class);
    
    /** The Constant FACEBOOK_USER_CLIENT. */
    private static final String FACEBOOK_USER_CLIENT = "facebook.user.client";

    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.GenericResource#doView(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.semanticwb.portal.api.SWBParamRequest)
     */
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        try {
            FacebookUtils facebookUtils = new FacebookUtils();
            HttpSession session = request.getSession(true);
            IFacebookRestClient<Document> userClient = getUserClient(session);
            if (userClient != null) {
                long facebookUserID = userClient.users_getLoggedInUser();
                
                ArrayList<Fb_Photo> aPhotos = new ArrayList();
                Iterator<String> itAlbums = facebookUtils.getAlbums(userClient.photos_getAlbums(facebookUserID)).iterator();
                while (itAlbums.hasNext()) {
                    String aid = itAlbums.next();
                    Document domPhotos = userClient.photos_getByAlbum(new Long(aid).longValue());
                    
                    NodeList nListPhotos = domPhotos.getElementsByTagName("photo");
                    for (int a = 0; a < nListPhotos.getLength(); a++) {
                        Fb_Photo photo = new Fb_Photo();
                        NodeList nPhotoChilds = nListPhotos.item(a).getChildNodes();
                        for(int b=0;b<nPhotoChilds.getLength();b++)
                        {
                            Node nPhotoChild=nPhotoChilds.item(b);
                            if (nPhotoChild.getNodeName().equals("pid")) {
                                photo.setPid(nPhotoChild.getFirstChild().getNodeValue());
                            } else if (nPhotoChild.getNodeName().equals("aid") && nPhotoChild.getFirstChild() != null) {
                                photo.setAid(nPhotoChild.getFirstChild().getNodeValue());
                            } else if (nPhotoChild.getNodeName().equals("owner") && nPhotoChild.getFirstChild() != null) {
                                photo.setOwner(nPhotoChild.getFirstChild().getNodeValue());
                            } else if (nPhotoChild.getNodeName().equals("src_small") && nPhotoChild.getFirstChild() != null) {
                                photo.setSrc_small(nPhotoChild.getFirstChild().getNodeValue());
                            } else if (nPhotoChild.getNodeName().equals("src_big") && nPhotoChild.getFirstChild() != null) {
                                photo.setSrc_big(nPhotoChild.getFirstChild().getNodeValue());
                            } else if (nPhotoChild.getNodeName().equals("src") && nPhotoChild.getFirstChild() != null) {
                                photo.setSrc(nPhotoChild.getFirstChild().getNodeValue());
                            } else if (nPhotoChild.getNodeName().equals("link") && nPhotoChild.getFirstChild() != null) {
                                photo.setLink(nPhotoChild.getFirstChild().getNodeValue());
                            } else if (nPhotoChild.getNodeName().equals("caption") && nPhotoChild.getFirstChild() != null) {
                                photo.setCaption(nPhotoChild.getFirstChild().getNodeValue());
                            } else if (nPhotoChild.getNodeName().equals("created") && nPhotoChild.getFirstChild() != null) {
                                photo.setCreated(nPhotoChild.getFirstChild().getNodeValue());
                            } else if (nPhotoChild.getNodeName().equals("modified") && nPhotoChild.getFirstChild() != null) {
                                photo.setModified(nPhotoChild.getFirstChild().getNodeValue());
                            }
                        }
                        aPhotos.add(photo);
                    }
                }
                request.setAttribute("aPhotos", aPhotos);
                RequestDispatcher rd = request.getRequestDispatcher("/swbadmin/jsp/facebook/fb_MyPhotos.jsp");
                rd.include(request, response);
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


