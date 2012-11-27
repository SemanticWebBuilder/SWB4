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
package org.semanticwb.portal.social.facebook.util;

import java.util.ArrayList;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

// TODO: Auto-generated Javadoc
/**
 * The Class FacebookUtils.
 * 
 * @author Jorge Jiménez
 */
public class FacebookUtils {

    /**
     * Gets the albums.
     * 
     * @param dom the dom
     * @return the albums
     */
    public ArrayList getAlbums(Document dom) {
        ArrayList aAlbums = new ArrayList();
        NodeList nList = dom.getElementsByTagName("album");
        for (int i = 0; i < nList.getLength(); i++) {
            NodeList nAlbumNodes = nList.item(i).getChildNodes();
            for (int y = 0; y <= nAlbumNodes.getLength(); y++) {
                Node nAid = nAlbumNodes.item(y);
                if (nAid != null && nAid.getNodeName().equals("aid")) {
                    aAlbums.add(nAid.getFirstChild().getNodeValue());
                    break;
                }
            }
        }
        return aAlbums;
    }

    /**
     * Gets the photos.
     * 
     * @param dom the dom
     * @return the photos
     */
    public ArrayList getPhotos(Document dom) {
        ArrayList aAlbums = new ArrayList();
        NodeList nList = dom.getElementsByTagName("photo");
        for (int i = 0; i < nList.getLength(); i++) {
            NodeList nPhotosNodes = nList.item(i).getChildNodes();
            for (int y = 0; y <= nPhotosNodes.getLength(); y++) {
                Node nPid = nPhotosNodes.item(y);
                if (nPid != null && nPid.getNodeName().equals("src_small")) {
                    aAlbums.add(nPid.getFirstChild().getNodeValue());
                    break;
                }
            }
        }
        return aAlbums;
    }
   
}
