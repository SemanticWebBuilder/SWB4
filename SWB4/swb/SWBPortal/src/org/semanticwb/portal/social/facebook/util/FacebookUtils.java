/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.portal.social.facebook.util;

import java.util.ArrayList;
import org.semanticwb.SWBUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author Jorge Jiménez
 */
public class FacebookUtils {

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
