/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.linkeddata.spider;

import java.net.URI;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author victor.lorenzana
 */
public class Predicates
{
    

    
    private final Map<URI, Set<TripleElement>> predicates = Collections.synchronizedMap(new HashMap<URI, Set<TripleElement>>());

    public void add(TripleElement element)
    {
        Set<TripleElement> elements = Collections.synchronizedSet(new HashSet<TripleElement>());
        if (predicates.containsKey(element.suj))
        {
            elements = predicates.get(element.suj);
        }
        else
        {
            predicates.put(element.suj, elements);
        }
        elements.add(element);


    }

    public synchronized Set<TripleElement> get(URI pred)
    {
        Set<TripleElement> _values = new HashSet<TripleElement>();
        Set<TripleElement> values = new HashSet<TripleElement>();
        if (hasPred(pred))
        {
            values = predicates.get(pred);
        }
        _values.addAll(values);
        return _values;
    }

    public boolean hasPred(URI pred)
    {
        return predicates.containsKey(pred);
    }
}
