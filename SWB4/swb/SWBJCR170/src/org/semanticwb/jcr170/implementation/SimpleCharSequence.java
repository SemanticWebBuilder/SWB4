/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.jcr170.implementation;


/**
 *
 * @author victor.lorenzana
 */
public class SimpleCharSequence implements CharSequence {

    private String value;
    public SimpleCharSequence(String value)
    {
        this.value=value;
    }
    public int length()
    {
        return value.length();
    }

    public char charAt(int index)
    {
        return value.toCharArray()[index];
    }

    public CharSequence subSequence(int start, int end)
    {
        return new SimpleCharSequence(value.substring(start, end));
    }

}
