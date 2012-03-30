/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.linkeddata.spider;

import com.arthurdo.parser.HtmlTag;

/**
 *
 * @author victor.lorenzana
 */
public class HTMLElement {
    public HtmlTag tag;
    public HTMLElement parent;

    @Override
    public String toString()
    {
        return tag.getTagString();
    }

    
}
