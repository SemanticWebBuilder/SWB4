/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.css.parser;

import java.util.HashSet;
import java.util.StringTokenizer;

/**
 *
 * @author victor.lorenzana
 */
public class Attribute {
    private String name;
    private HashSet<String> values=new HashSet<String>();
    public Attribute(String name,String value)
    {
        this.name=name;
        StringTokenizer st=new StringTokenizer(value," ");
        while(st.hasMoreTokens())
        {
            String nvalue=st.nextToken().trim();            
            values.add(nvalue);
        }
    }
    public String getName()
    {
        return name;
    }
    public String[] getValues()
    {
        return values.toArray(new String[values.size()]);
    }
}
