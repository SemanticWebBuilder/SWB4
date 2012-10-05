/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.social.util;

/**
 *
 * @author jorge.jimenez
 */

/*
 * Clase de tipo pojo que sirve en la clasificación de mensajes provenientes del listener
 */
public class NormalizerCharDuplicate {

    String normalizedPhrase=null;
    boolean isCharDuplicate=false;
    
    public NormalizerCharDuplicate(String normalizedPhrase, boolean isCharDuplicate)
    {
        this.normalizedPhrase=normalizedPhrase;
        this.isCharDuplicate=isCharDuplicate;
    }

    public String getNormalizedPhrase()
    {
        return normalizedPhrase;
    }

    public boolean isCharDuplicate()
    {
        return isCharDuplicate;
    }

}
