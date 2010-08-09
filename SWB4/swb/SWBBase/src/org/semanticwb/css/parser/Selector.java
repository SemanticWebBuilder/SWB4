/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.css.parser;

import java.util.Set;

/**
 *
 * @author victor.lorenzana
 */
public class Selector {

    private String name;
    private Set<Attribute> atts;
    Selector(String name,Set<Attribute> atts)
    {
        this.name=name;
    }
    public Attribute[] getAttributes()
    {
        return atts.toArray(new Attribute[atts.size()]);
    }
    
}
