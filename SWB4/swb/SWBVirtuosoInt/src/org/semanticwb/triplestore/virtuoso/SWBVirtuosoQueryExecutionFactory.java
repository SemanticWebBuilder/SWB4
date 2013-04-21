/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.triplestore.virtuoso;

import com.hp.hpl.jena.query.Dataset;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.sparql.core.DatasetImpl;
import com.hp.hpl.jena.sparql.engine.http.QueryEngineHTTP;
import com.hp.hpl.jena.sparql.util.Context;
import com.hp.hpl.jena.util.FileManager;
import java.util.List;

/**
 *
 * @author javier.solis.g
 */
public class SWBVirtuosoQueryExecutionFactory
{

    private SWBVirtuosoQueryExecutionFactory()
    {
    }

    static public SWBVirtuosoQueryExecution create(Query query, SWBVirtGraph graph)
    {
	SWBVirtuosoQueryExecution ret = new SWBVirtuosoQueryExecution (query.toString(), graph);
        return ret;
    }

    static public SWBVirtuosoQueryExecution create(String query, SWBVirtGraph graph)
    {
	SWBVirtuosoQueryExecution ret = new SWBVirtuosoQueryExecution (query, graph);
        return ret;
    }

/* TODO */

    static public QueryExecution create(Query query, Dataset dataset)
    {
	if (dataset instanceof SWBVirtDataSource) {
	  SWBVirtuosoQueryExecution ret = new SWBVirtuosoQueryExecution (query.toString(), (SWBVirtGraph)dataset);
          return ret;
	} else {
          return make(query, dataset) ;
	}
    }

    static public QueryExecution create(String queryStr, Dataset dataset)
    {
	if (dataset instanceof SWBVirtDataSource) {
	  SWBVirtuosoQueryExecution ret = new SWBVirtuosoQueryExecution (queryStr, (SWBVirtGraph)dataset);
          return ret;
	} else {
          return make(makeQuery(queryStr), dataset) ;
	}
    }

    static public QueryExecution create(Query query, FileManager fm)
    {
        checkArg(query) ;
        QueryExecution qe = make(query) ;
        if ( fm != null )
            qe.setFileManager(fm) ;
        return qe ;
    }

    static public QueryExecution create(String queryStr, FileManager fm)
    {
        checkArg(queryStr) ;
        return create(makeQuery(queryStr), fm) ;
    }

    // ---------------- Query + Model

    static public QueryExecution create(Query query, Model model)
    {
        checkArg(query) ;
        checkArg(model) ;

	if (model.getGraph() instanceof SWBVirtGraph) {
	  SWBVirtuosoQueryExecution ret = new SWBVirtuosoQueryExecution (query.toString(), (SWBVirtGraph)model.getGraph());
          return ret;
	} else {
          return make(query, new DatasetImpl(model)) ;
	}
    }

    static public QueryExecution create(String queryStr, Model model)
    {
        checkArg(queryStr) ;
        checkArg(model) ;
	if (model.getGraph() instanceof SWBVirtGraph) {
	  SWBVirtuosoQueryExecution ret = new SWBVirtuosoQueryExecution (queryStr, (SWBVirtGraph)model.getGraph());
          return ret;
	} else {
          return create(makeQuery(queryStr), model) ;
	}
    }

    static public QueryExecution create(Query query, QuerySolution initialBinding)
    {
        checkArg(query) ;
        QueryExecution qe = make(query) ;
        if ( initialBinding != null )
            qe.setInitialBinding(initialBinding) ;
        return qe ;
    }

    static public QueryExecution create(String queryStr, QuerySolution initialBinding)
    {
        checkArg(queryStr) ;
        return create(makeQuery(queryStr), initialBinding) ;
    }

//??
    static public QueryExecution create(Query query, Dataset dataset, QuerySolution initialBinding)
    {
        checkArg(query) ;
        QueryExecution qe = make(query, dataset) ;
        if ( initialBinding != null )
            qe.setInitialBinding(initialBinding) ;
        return qe ;
    }

//??
    static public QueryExecution create(String queryStr, Dataset dataset, QuerySolution initialBinding)
    {
        checkArg(queryStr) ;
        return create(makeQuery(queryStr), dataset, initialBinding) ;
    }

    // ---------------- Remote query execution

    static public QueryExecution sparqlService(String service, Query query)
    {
        checkNotNull(service, "URL for service is null") ;
        checkArg(query) ;
        return makeServiceRequest(service, query) ;
    }

    static public QueryExecution sparqlService(String service, Query query, String defaultGraph)
    {
        checkNotNull(service, "URL for service is null") ;
        //checkNotNull(defaultGraph, "IRI for default graph is null") ;
        checkArg(query) ;
        QueryEngineHTTP qe = makeServiceRequest(service, query) ;
        qe.addDefaultGraph(defaultGraph) ;
        return qe ;
    }

    static public QueryExecution sparqlService(String service, Query query, List defaultGraphURIs,
	    List namedGraphURIs)
    {
        checkNotNull(service, "URL for service is null") ;
        //checkNotNull(defaultGraphURIs, "List of default graph URIs is null") ;
        //checkNotNull(namedGraphURIs, "List of named graph URIs is null") ;
        checkArg(query) ;

        QueryEngineHTTP qe = makeServiceRequest(service, query) ;

        if ( defaultGraphURIs != null )
            qe.setDefaultGraphURIs(defaultGraphURIs) ;
        if ( namedGraphURIs != null )
            qe.setNamedGraphURIs(namedGraphURIs) ;
        return qe ;
    }

    // ---------------- Internal routines

    // Make query

    static private Query makeQuery(String queryStr)
    {
        return QueryFactory.create(queryStr) ;
    }

    // ---- Make executions

    static private QueryExecution make(Query query)
    {
        return make(query, null) ;
    }

    static private QueryExecution make(Query query, Dataset dataset)
    {
	 return make(query, dataset, null);
    }

    static private QueryExecution make(Query query, Dataset dataset, Context context)
    {
	    return null ;
    }

    static private QueryEngineHTTP makeServiceRequest(String service, Query query)
    {
        return new QueryEngineHTTP(service, query) ;
    }

    static private void checkNotNull(Object obj, String msg)
    {
        if ( obj == null )
            throw new IllegalArgumentException(msg) ;
    }

    static private void checkArg(Model model)
    { checkNotNull(model, "Model is a null pointer") ; }

    static private void checkArg(String queryStr)
    { checkNotNull(queryStr, "Query string is null") ; }

    static private void checkArg(Query query)
    { checkNotNull(query, "Query is null") ; }
}