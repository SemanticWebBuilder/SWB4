/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.css.parser;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author victor.lorenzana
 */
public class Selector {

    private String name;
    private Set<Attribute> atts=new HashSet<Attribute>();
    Selector(String name,Set<Attribute> atts)
    {
        this.name=name;
        this.atts=atts;
    }
    public Attribute[] getAttributes()
    {
        return atts.toArray(new Attribute[atts.size()]);
    }
    
}
