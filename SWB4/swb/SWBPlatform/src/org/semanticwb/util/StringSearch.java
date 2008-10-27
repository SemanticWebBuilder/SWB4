/*
 * INFOTEC WebBuilder es una herramienta para el desarrollo de portales de conocimiento, colaboración e integración para Internet,
 * la cual, es una creación original del Fondo de Información y Documentación para la Industria INFOTEC, misma que se encuentra
 * debidamente registrada ante el Registro Público del Derecho de Autor de los Estados Unidos Mexicanos con el
 * No. 03-2002-052312015400-14, para la versión 1; No. 03-2003-012112473900 para la versión 2, y No. 03-2006-012012004000-01
 * para la versión 3, respectivamente.
 *
 * INFOTEC pone a su disposición la herramienta INFOTEC WebBuilder a través de su licenciamiento abierto al público (‘open source’),
 * en virtud del cual, usted podrá usarlo en las mismas condiciones con que INFOTEC lo ha diseñado y puesto a su disposición;
 * aprender de él; distribuirlo a terceros; acceder a su código fuente y modificarlo, y combinarlo o enlazarlo con otro software,
 * todo ello de conformidad con los términos y condiciones de la LICENCIA ABIERTA AL PÚBLICO que otorga INFOTEC para la utilización
 * de INFOTEC WebBuilder 3.2.
 *
 * INFOTEC no otorga garantía sobre INFOTEC WebBuilder, de ninguna especie y naturaleza, ni implícita ni explícita,
 * siendo usted completamente responsable de la utilización que le dé y asumiendo la totalidad de los riesgos que puedan derivar
 * de la misma.
 *
 * Si usted tiene cualquier duda o comentario sobre INFOTEC WebBuilder, INFOTEC pone a su disposición la siguiente
 * dirección electrónica:
 *
 *                                          http://www.webbuilder.org.mx
 */


/*
 * StringSearch2.java
 *
 * Created on 11 de abril de 2002, 11:04
 */

package org.semanticwb.util;

import java.text.*;

/**
 * Busca una cadena en un string no importando caracteres especiales.
 * @author Javier Solis Gonzalez
 * @version 1.0
 */
public class StringSearch
{

    // Member variables for storing precomputed pattern data
    private int patLen;
    private int[] patElem;
    private int[] shifts;
    int weight = Collator.PRIMARY;

    private CollationElementIterator iter;

    /** Creates new StringSearch2 */
    public StringSearch()
    {

    }

    static public void main(String[] args)
    {
        String text = "Javier Solís";
        String pattern = "solis";
        StringSearch s = new StringSearch();
        System.out.println(s.find(text, pattern));
        System.out.println(s.compare(text, pattern));
    }

    public boolean compare(String text, String pattern)
    {
        RuleBasedCollator c = (RuleBasedCollator) Collator.getInstance();
        CollationElementIterator patIter = c.getCollationElementIterator(pattern);
        CollationElementIterator targIter = c.getCollationElementIterator(text);

        for (int i = 0; i < text.length(); i++)
        {
            targIter.setOffset(i);
            patIter.reset();                    // Or setOffset(0)
            if (match(targIter, patIter))
            {
                return true;
            }
        }
        return false;
    }

    // Map a collation element to an array index
    int hash(int order)
    {
        return CollationElementIterator.primaryOrder(order) % 256;
    }

    // Initialize the Boyer-Moore shift tables
    void initialize(RuleBasedCollator c, String pat)
    {
        // First find out how many elements we're dealing with
        patLen = 0;
        CollationElementIterator iter = c.getCollationElementIterator(pat);
        while (iter.next() != CollationElementIterator.NULLORDER)
            patLen++;

        // Allocate space to store the pattern elements and the shift tables
        patElem = new int[patLen];
        shifts = new int[256];

        // Elements not found in the pattern get the maximum shift distance
        for (int i = 0; i < 256; i++)
        {
            shifts[i] = patLen;
        }

        // Now compute the shift distances for the elements in the pattern.
        // While we're at it, save the elements themselves for quick access.
        // The "-1" is in the calculation because Java indices are 0-based.
        iter.reset();
        for (int i = 0; i < patLen; i++)
        {
            patElem[i] = iter.next();
            int index = hash(patElem[i]);
            shifts[index] = Math.min(shifts[index], patLen - i - 1);
        }
    }

    public int find(String text, String pattern)
    {
        RuleBasedCollator coll = (RuleBasedCollator) Collator.getInstance();
        CollationElementIterator targIter = coll.getCollationElementIterator(text);
        CollationElementIterator iter = coll.getCollationElementIterator(text);

        // build the shift table and the constants we need
        initialize(coll, pattern);
        int mask = getMask(Collator.PRIMARY);
        int done = CollationElementIterator.NULLORDER & mask;

        // Start at the text position corresponding to the end of the pattern
        int textIndex = pattern.length();

        while (textIndex <= text.length())
        {
            boolean getPattern = true, getTarget = true;
            int targetElement = 0, patternElement = 0;

            iter.setOffset(textIndex);
            int patIndex = pattern.length();

            // Iterate backward until we hit the beginning of the pattern
            while (patIndex > 0)
            {
                if (getTarget) {
                    targetElement = targIter.previous() & mask;
                }
                if (getPattern) {
                    patternElement = patElem[--patIndex] & mask;
                }
                getTarget = getPattern = true;

                if (targetElement == 0)
                {
                    getPattern = false;            // skip targetElement
                } else if (patternElement == 0)
                {
                    getTarget = false;             // skip patternElement
                } else if (targetElement != patternElement)
                {
                    // There is a mismatch at this position.  Decide how far
                    // over to shift the pattern, then try again.
                    textIndex = iter.getOffset() +
                            shifts[hash(targetElement)];
                    break;
                }
            }
            if (patIndex == 0)
            {
                // We made it back to the beginning of the pattern,
                // which means we matched it all.  Return the location.
                return targIter.getOffset();
            }
            // Otherwise, we're here because of a mismatch, so keep going....
        }
        return -1;            // No match.
    }

    // Return a mask for the part of the order we're interested in
    public int getMask(int weight)
    {
        switch (weight)
        {
            case Collator.PRIMARY:
                return 0xFFFF0000;
            case Collator.SECONDARY:
                return 0xFFFFFF00;
            default:
                return 0xFFFFFFFF;
        }
    }

    public boolean match(CollationElementIterator text, CollationElementIterator pattern)
    {
        int mask = getMask(weight);
        int done = CollationElementIterator.NULLORDER & mask;

        boolean getPattern = true, getTarget = true;
        int patternElement = 0, targetElement = 0;

        while (true)
        {
            if (getPattern) {
                patternElement = pattern.next() & mask;
            }
            if (getTarget) {
                targetElement = text.next() & mask;
            }
            getTarget = getPattern = true;  // By default get both

            if (patternElement == done)
            {
                break;			            // End of pattern
            } else if (targetElement == 0)
            {
                getPattern = false;		    // skip targetElement
            } else if (patternElement == 0)
            {
                getTarget = false;		    // skip patternElement
            } else if (targetElement != patternElement)
            {
                return false;                         // Mismatch
            }
        }
        return true;                                // No mismatches
    }

/*
    boolean match(CollationElementIterator text,
                  CollationElementIterator pattern)
    {
        while (true) {
            int patternElement = pattern.next();
            int targetElement  = text.next();
            if (patternElement == CollationElementIterator.NULLORDER) {
                break;           // End of the pattern
            } else if (patternElement != targetElement) {
                return false;    // Mismatch
            }
        }
        return true;             // No mismatches
    }
*/


}