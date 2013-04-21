/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.triplestore.virtuoso;

import com.hp.hpl.jena.graph.Node;
import com.hp.hpl.jena.graph.Triple;
import com.hp.hpl.jena.shared.JenaException;
import com.hp.hpl.jena.shared.PrefixMapping;
import com.hp.hpl.jena.shared.impl.PrefixMappingImpl;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Iterator;
import java.util.Map;

/**
 *
 * @author javier.solis.g
 */
public class SWBVirtPrefixMapping extends PrefixMappingImpl
{

    protected SWBVirtGraph m_graph = null;

    /**
     * Constructor for a persistent prefix mapping.
     *
     */
    public SWBVirtPrefixMapping(SWBVirtGraph graph)
    {
        super();
        m_graph = graph;

        // Populate the prefix map using data from the 
        // persistent graph properties
        String query = "DB.DBA.XML_SELECT_ALL_NS_DECLS (3)";
        try
        {
            Connection con=m_graph.m_transactionHandler.getConnection();
            Statement stmt = m_graph.createStatement(con);
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next())
            {
                String prefix = rs.getString(1);
                String uri = rs.getString(2);
                if (uri != null && uri != null)
                {
                    super.setNsPrefix(prefix, uri);
                }
            }
            
            rs.close();
            stmt.close();
            con.close();
            
            ExtendedIterator<Triple> it = graph.find(Node.ANY, Node.createURI("http://www.swb.org/#prefix"), Node.ANY);
            while (it.hasNext())
            {
                Triple triple = it.next();
                triple.getSubject().getURI();
                super.setNsPrefix(triple.getObject().getLiteralValue().toString(), triple.getSubject().getURI());
            }            
            
        } catch (Exception e)
        {
            throw new JenaException(e);
        }
    }

    public PrefixMapping removeNsPrefix(String prefix)
    {
        String query = "DB.DBA.XML_REMOVE_NS_BY_PREFIX(?, 1)";
        super.removeNsPrefix(prefix);

        try
        {
            Connection con=m_graph.m_transactionHandler.getConnection();
            PreparedStatement ps = m_graph.prepareStatement(con,query);
            ps.setString(1, prefix);
            ps.execute();
            ps.close();
            con.close();
        } catch (Exception e)
        {
            throw new JenaException(e);
        }
        
        m_graph.remove(new Triple(Node.ANY, Node.createURI("http://www.swb.org/#prefix"), Node.createLiteral(prefix)));        

        return this;
    }

    /* (non-Javadoc)
     * Override the default implementation so we can catch the write operation
     * and update the persistent store.
     * @see com.hp.hpl.jena.shared.PrefixMapping#setNsPrefix(java.lang.String, java.lang.String)
     */
    public PrefixMapping setNsPrefix(String prefix, String uri)
    {
        super.setNsPrefix(prefix, uri);

        String query = "DB.DBA.XML_SET_NS_DECL(?, ?, 1)";

        // All went well, so persist the prefix by adding it to the graph properties
        // (the addPrefix call will overwrite any existing mapping with the same prefix
        // so it matches the behaviour of the prefixMappingImpl).
        try
        {
            Connection con=m_graph.m_transactionHandler.getConnection();
            PreparedStatement ps = m_graph.prepareStatement(con,query);
            ps.setString(1, prefix);
            ps.setString(2, uri);
            ps.execute();
            ps.close();
            con.close();
        } catch (Exception e)
        {
            throw new JenaException(e.toString());
        }
        
        m_graph.add(new Triple(Node.createURI(uri), Node.createURI("http://www.swb.org/#prefix"), Node.createLiteral(prefix)));        
        
        return this;
    }

    public PrefixMapping setNsPrefixes(PrefixMapping other)
    {
        return setNsPrefixes(other.getNsPrefixMap());
    }

    public PrefixMapping setNsPrefixes(Map other)
    {
        checkUnlocked();
        Iterator it = other.entrySet().iterator();
        while (it.hasNext())
        {
            Map.Entry e = (Map.Entry) it.next();
            setNsPrefix((String) e.getKey(), (String) e.getValue());
        }
        return this;
    }
}
