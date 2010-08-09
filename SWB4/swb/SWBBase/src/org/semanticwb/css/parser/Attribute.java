/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.css.parser;

/**
 *
 * @author victor.lorenzana
 */
public class Attribute {
    private String name;
    private String value;
    public Attribute(String name,String value)
    {
        this.name=name;
        this.value=value;
    }
    public String getName()
    {
        return name;
    }
    public String getValue()
    {
        return value;
    }
}
