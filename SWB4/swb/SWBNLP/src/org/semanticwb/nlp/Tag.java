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
    private String URI;
    private String clsId;

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

    public String getId() {
        return clsId;
    }

    public void setId(String classId) {
        clsId = classId;
    }

    public String getURI() {
        return URI;
    }

    public void setURI(String uri) {
        URI = uri;
    }

    public boolean equals(Tag tag) {
        boolean ret = false;
        if (tag.getTag().equalsIgnoreCase(label) &&
                tag.getURI().equals(URI)) {
            ret = true;
        }
        return ret;
    }
}
