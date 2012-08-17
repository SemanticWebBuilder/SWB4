/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.linkeddata.spider.repository;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author karen.najera
 */
public class DocumentTriples {

    private Map<URL, Set<Triple>> documentTriples = Collections.synchronizedMap(new HashMap<URL, Set<Triple>>());

    /*
     * @ add set of triples
     */
    public synchronized void add(String s_context, Set<Triple> triples) {
        URL context = null;
        try {
            int pos = s_context.indexOf("#");
            if (pos != -1) {
                context = new URL(s_context.substring(0, pos));
            } else {
                context = new URL(s_context);
            }
            documentTriples.put(context, triples);
        } catch (MalformedURLException ex) {
            System.err.println("Error DocumentTriples add: " + ex);
        }
    }

    /*
     * @ add a triple
     * 
     */
    public synchronized void add(String s_context, Triple triple) {
        URL context = null;
        try {
            int pos = s_context.indexOf("#");
            if (pos != -1) {
                context = new URL(s_context.substring(0, pos));
            } else {
                context = new URL(s_context);
            }
            Set<Triple> values = getTriples(s_context);
            values.add(triple);
            documentTriples.put(context, values);
        } catch (MalformedURLException ex) {
            System.err.println("Error DocumentTriples add: " + ex);
        }
    }

    /*
     * @ add set of triples
     */
    public synchronized void remove(String s_context) {
        URL context = null;
        try {
            int pos = s_context.indexOf("#");
            if (pos != -1) {
                context = new URL(s_context.substring(0, pos));
            } else {
                context = new URL(s_context);
            }
            if (hasURL(context.toString())) {
                documentTriples.remove(context);
            }
        } catch (MalformedURLException ex) {
            System.err.println("Error DocumentTriples add: " + ex);
        }
    }

    public URL getURL(String s_context) {
        URL context = null;
        try {
            int pos = s_context.indexOf("#");
            if (pos != -1) {
                context = new URL(s_context.substring(0, pos));
            } else {
                context = new URL(s_context);
            }
        } catch (MalformedURLException ex) {
            System.err.println("Error DocumentTriples get: " + ex);
        }
        if (hasURL(context.toString())) {
            return context;
        } else {
            return null;
        }

    }

    public Set<Triple> getTriples(String s_context) {
        Set<Triple> values = new HashSet<Triple>();
        URL context = null;
        try {
            int pos = s_context.indexOf("#");
            if (pos != -1) {
                context = new URL(s_context.substring(0, pos));
            } else {
                context = new URL(s_context);
            }
        } catch (MalformedURLException ex) {
            System.err.println("Error DocumentTriples get: " + ex);
        }

        if (hasURL(context.toString())) {
            values = documentTriples.get(context);
        }
        return values;
    }

    public Triple getTriple(String s_context, String s_suj, String s_pred, String s_obj) {
        Set<Triple> triples = new HashSet<Triple>();
        Triple triple = null;
        URL context = null;
        try {
            int pos = s_context.indexOf("#");
            if (pos != -1) {
                context = new URL(s_context.substring(0, pos));
            } else {
                context = new URL(s_context);
            }
        } catch (MalformedURLException ex) {
            System.err.println("Error DocumentTriples get: " + ex);
        }

        if (hasURL(context.toString())) {
            triples = documentTriples.get(context);
            Iterator<Triple> iter = triples.iterator();
            while (iter.hasNext()) {
                Triple tr = iter.next();
                if (s_suj.equals(tr.suj) && s_pred.equals(tr.pred) && s_obj.equals(tr.obj)) {
                    triple = new Triple(s_suj, s_pred, s_obj, true, false);
                }
            }
        }
        return triple;
    }

    public boolean hasTriple(String s_context, String s_suj, String s_pred, String s_obj) {
        boolean hasTriple = false;
        URL context = null;
        try {
            int pos = s_context.indexOf("#");
            if (pos != -1) {
                context = new URL(s_context.substring(0, pos));
            } else {
                context = new URL(s_context);
            }
        } catch (MalformedURLException ex) {
            System.err.println("Error DocumentTriples get: " + ex);
        }

        if (hasURL(context.toString())) {
            Set<Triple> triples = getTriples(s_context);

            Iterator<Triple> iter = triples.iterator();
            while (iter.hasNext()) {
                Triple tr = iter.next();
                if (s_suj.equals(tr.suj) && s_pred.equals(tr.pred) && s_obj.equals(tr.obj)) {
                    hasTriple = true;
                    tr.isOnDatabase = true;
                }
            }
        }
        return hasTriple;
    }

    public boolean hasURL(String s_context) {
        URL context = null;
        try {
            int pos = s_context.indexOf("#");
            if (pos != -1) {
                context = new URL(s_context.substring(0, pos));
            } else {
                context = new URL(s_context);
            }
        } catch (MalformedURLException ex) {
            System.err.println("Error DocumentTriples get: " + ex);
        }

        return documentTriples.containsKey(context);
    }
}
