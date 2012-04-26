/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.social;

import javaQuery.j2ee.tinyURL;

/**
 *
 * @author martha.jimenez
 */
public class pruebas {
    public static void main(String[] args) {
        System.out.println("NUEVO MENSAJE : "+shortUrl("probando esta http://stackoverflow.com/questions/6694735/invalid-signature-when-uploading-photo-to-flickr-with-java veremos que pasa"));
    }
    private static String shortUrl(String urlLong) {
        String mensajeNuevo="";
       // String shortUrl = "";
        if (urlLong != null && !urlLong.equals("")) {
            String delimiter = " ";
            String[] temp = urlLong.split(delimiter);
            for (int i = 0; i < temp.length; i++) {
                if ((temp[i].startsWith("http://") || temp[i].startsWith("http://")) && ((temp[i].length() > 9))) {
                    tinyURL tU = new tinyURL();
                    temp[i] = tU.getTinyURL(temp[i]);
                }
                mensajeNuevo+=temp[i]+" ";
            }

        }
        return mensajeNuevo;
    }
}
