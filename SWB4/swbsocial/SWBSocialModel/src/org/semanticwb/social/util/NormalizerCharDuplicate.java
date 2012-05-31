/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.social.util;

/**
 *
 * @author jorge.jimenez
 */
public class NormalizerCharDuplicate {

    String normalizedWord=null;
    boolean isCharDuplicate=false;
    
    public NormalizerCharDuplicate(String normalizedWord, boolean isCharDuplicate)
    {
        this.normalizedWord=normalizedWord;
        this.isCharDuplicate=isCharDuplicate;
    }

    public String getNormalizedWord()
    {
        return normalizedWord;
    }

    public boolean isCharDuplicate()
    {
        return isCharDuplicate;
    }

}
