/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.nlp;

/**
 *
 * @author Hasdai Pacheco {haxdai@gmail.com}
 */
public class Tag {
    private String label;

    public Tag () {
        this("");
    }

    public Tag (String t) {
        label = t;
    }

    public void setTag (String t) {
        label = t;
    }

    public String getTag() {
        return label;
    }

}
