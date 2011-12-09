/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.bigdata;

import com.bigdata.rdf.model.BigdataStatement;
import com.bigdata.rdf.sail.BigdataSail;
import com.bigdata.rdf.store.BigdataStatementIterator;
import com.hp.hpl.jena.shared.PrefixMapping;
import java.util.Iterator;
import java.util.Map;
import com.hp.hpl.jena.rdf.model.impl.Util;
import java.util.HashMap;
import org.openrdf.model.impl.ValueFactoryImpl;

/**
 *
 * @author jei
 */
public class BigdataPrefixMapping implements PrefixMapping
{

    private BigdataSail sail;

    public BigdataPrefixMapping(BigdataSail sail)
    {
        this.sail = sail;

        BigdataStatementIterator it=sail.getDatabase().getStatements(null, new ValueFactoryImpl().createURI("http://www.swb.org#prefix"), null);
        while (it.hasNext())
        {
            BigdataStatement bigdataStatement = it.next();
            String uri=bigdataStatement.getObject().stringValue();
            String prefix=bigdataStatement.getSubject().toString().substring(22);
            //System.out.println(prefix+" "+uri);
            sail.getDatabase().addNamespace(uri, prefix);
        }
    }

    public PrefixMapping setNsPrefix(String prefix, String uri)
    {
        //System.out.println("setNsPrefix:"+prefix+" "+uri);
        try
        {
            sail.getDatabase().addNamespace(uri, prefix);
            //sail.getDatabase().addStatement(new ValueFactoryImpl().createURI("http://prefix.swb.org#"+prefix), new ValueFactoryImpl().createURI("http://www.swb.org#prefix"), new ValueFactoryImpl().createLiteral(uri));
            //sail.getDatabase().commit();
            BigdataSail.BigdataSailConnection con=sail.getConnection();
            con.addStatement(new ValueFactoryImpl().createURI("http://prefix.swb.org#"+prefix), new ValueFactoryImpl().createURI("http://www.swb.org#prefix"), new ValueFactoryImpl().createLiteral(uri));
            con.commit();
            con.close();
        }catch(Exception e){e.printStackTrace();}
        return this;
    }

    public PrefixMapping removeNsPrefix(String prefix)
    {
        try
        {
            sail.getDatabase().removeNamespace(prefix);
            //sail.getDatabase().removeStatements(new ValueFactoryImpl().createURI("http://prefix.swb.org#"+prefix), new ValueFactoryImpl().createURI("http://www.swb.org#prefix"), null);
            //sail.getDatabase().commit();
            BigdataSail.BigdataSailConnection con=sail.getConnection();
            con.removeStatements(new ValueFactoryImpl().createURI("http://prefix.swb.org#"+prefix), new ValueFactoryImpl().createURI("http://www.swb.org#prefix"), null);
            con.commit();
            con.close();
        }catch(Exception e){e.printStackTrace();}
        return this;
    }

    public PrefixMapping setNsPrefixes(PrefixMapping other)
    {
        return setNsPrefixes(other.getNsPrefixMap());
    }

    public PrefixMapping setNsPrefixes(Map<String, String> map)
    {
        Iterator<String> it = map.keySet().iterator();
        while (it.hasNext())
        {
            String prefix = it.next();
            setNsPrefix(prefix, map.get(prefix));
        }
        return this;
    }

    public PrefixMapping withDefaultMappings(PrefixMapping map)
    {
        for (Map.Entry<String, String> e: map.getNsPrefixMap().entrySet())
            {
            String prefix = e.getKey(), uri = e.getValue();
            if (getNsPrefixURI( prefix ) == null && getNsURIPrefix( uri ) == null)
                setNsPrefix( prefix, uri );
            }
        return this;
    }

    public String getNsPrefixURI(String prefix)
    {
        String str=sail.getDatabase().getNamespace(prefix);
        return str;
    }

    public String getNsURIPrefix(String uri)
    {
        Map.Entry<String, String> e = findMapping(uri, false);
        if (e != null)
        {
            return e.getKey();
        }
        return null;
    }

    public Map<String, String> getNsPrefixMap()
    {
       
        Map<String,String> m=new HashMap<String, String>();
        Iterator<Map.Entry<String,String>> it=sail.getDatabase().getNamespaces().entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, String> entry = it.next();
            m.put(entry.getValue(), entry.getKey());
        }
        //System.out.println("getNsPrefixMap:"+sail.getDatabase().getNamespaces());
        return m;
    }

    public String expandPrefix(String prefixed)
    {
        int colon = prefixed.indexOf(':');
        if (colon < 0)
        {
            return prefixed;
        } else
        {
            String uri = getNsPrefixURI(prefixed.substring(0, colon));
            return uri == null ? prefixed : uri + prefixed.substring(colon + 1);
        }
    }

    public String shortForm(String uri)
    {
        Map.Entry<String, String> e = findMapping(uri, true);
        return e == null ? uri : e.getKey() + ":" + uri.substring((e.getValue()).length());
    }

    public String qnameFor(String uri)
    {
        int split = Util.splitNamespace(uri);
        String ns = uri.substring(0, split), local = uri.substring(split);
        if (local.equals(""))
        {
            return null;
        }
        String prefix = getNsURIPrefix(ns);
        return prefix == null ? null : prefix + ":" + local;
    }

    public PrefixMapping lock()
    {
        //TODO
        //throw new UnsupportedOperationException("Not supported yet.");
        return this;
    }

    public boolean samePrefixMappingAs(PrefixMapping other)
    {
        return other instanceof BigdataPrefixMapping
            ? equals( (BigdataPrefixMapping) other )
            : equalsByMap( other )
            ;
    }

    protected final boolean equalsByMap( PrefixMapping other )
        { return getNsPrefixMap().equals( other.getNsPrefixMap() ); }

    /**
    Answer a prefixToURI entry in which the value is an initial substring of <code>uri</code>.
    If <code>partial</code> is false, then the value must equal <code>uri</code>.

    Does a linear search of the entire prefixToURI, so not terribly efficient for large maps.

    @param uri the value to search for
    @param partial true if the match can be any leading substring, false for exact match
    @return some entry (k, v) such that uri starts with v [equal for partial=false]
     */
    private Map.Entry<String, String> findMapping(String uri, boolean partial)
    {
        Map<String, String> map = getNsPrefixMap();
        Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();
        while (it.hasNext())
        {
            Map.Entry<String, String> e = it.next();
            String ss = e.getValue();
            if (uri.startsWith(ss) && (partial || ss.length() == uri.length()))
            {
                return e;
            }
        }
        return null;
    }
}
