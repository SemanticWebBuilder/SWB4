/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.opensocial.util;

/**
 *
 * @author victor.lorenzana
 */
public class HtmlScape implements Scape {

    public String scape(String value)
    {
        return EscapeChars.forHTML(value);
    }

}
