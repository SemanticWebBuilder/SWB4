/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.rdf;

import com.hp.hpl.jena.graph.Graph;
import com.hp.hpl.jena.graph.Node;
import com.hp.hpl.jena.graph.TripleMatch;
import com.hp.hpl.jena.graph.impl.GraphBase;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;

/**
 *
 * @author javier.solis
 */
public class RemoteGraph extends GraphBase implements Graph
{
    private String uri;
    private boolean queryAll=false;

    /**
     * Cronstruct a Remote Graph
     * @param uri for the Remote Sparql endpoint
     */
    public RemoteGraph(String uri)
    {
        this(uri,false);
    }

    /**
     * Cronstruct a Remote Graph
     * @param uri for the Remote Sparql endpoint
     * @param queryAll support for query all triples (default false)
     */
    public RemoteGraph(String uri, boolean queryAll)
    {
        this.uri=uri;
        this.queryAll=queryAll;
    }

    private String getNodeString(Node n)
    {
        String ret=null;
        if(n.isURI())ret="<"+n.getURI()+">";
        else ret=n.toString();
        //System.out.println("node:"+ret);
        return ret;
    }


    /**
        Answer an iterator over all the triples held in this graph's non-reified triple store
        that match <code>m</code>. 
    */
    @Override
    protected ExtendedIterator graphBaseFind(TripleMatch m)
    {
        String sub="$sub";
        String pre="$pre";
        String obj="$obj";

        if(m.getMatchSubject()!=null)sub=getNodeString(m.getMatchSubject());
        if(m.getMatchPredicate()!=null)pre=getNodeString(m.getMatchPredicate());
        if(m.getMatchObject()!=null)obj=getNodeString(m.getMatchObject());

        System.out.println("query:"+sub+" "+pre+" "+obj);
        //new Exception().printStackTrace();

       	String queryString =
            "CONSTRUCT { "+sub+" "+pre+" "+obj+" }" +
    		"WHERE  " +
            "{"+sub+" "+pre+" "+obj+"}";
       	//String url = "http://dbpedia.org/sparql";
        //String url = "http://www.sparql.org/books";
       	Query query = QueryFactory.create(queryString);
    	QueryExecution qexec = QueryExecutionFactory.sparqlService(uri,query);
        Model model=qexec.execConstruct();
        //model.write(System.out);
        return model.getGraph().find(m);
        //return null;
    }

    public String getUri() {
        return uri;
    }

}
