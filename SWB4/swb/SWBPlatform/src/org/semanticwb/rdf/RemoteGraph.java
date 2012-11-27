/*
 * SemanticWebBuilder es una plataforma para el desarrollo de portales y aplicaciones de integración,
 * colaboración y conocimiento, que gracias al uso de tecnología semántica puede generar contextos de
 * información alrededor de algún tema de interés o bien integrar información y aplicaciones de diferentes
 * fuentes, donde a la información se le asigna un significado, de forma que pueda ser interpretada y
 * procesada por personas y/o sistemas, es una creación original del Fondo de Información y Documentación
 * para la Industria INFOTEC, cuyo registro se encuentra actualmente en trámite.
 *
 * INFOTEC pone a su disposición la herramienta SemanticWebBuilder a través de su licenciamiento abierto al público (‘open source’),
 * en virtud del cual, usted podrá usarlo en las mismas condiciones con que INFOTEC lo ha diseñado y puesto a su disposición;
 * aprender de él; distribuirlo a terceros; acceder a su código fuente y modificarlo, y combinarlo o enlazarlo con otro software,
 * todo ello de conformidad con los términos y condiciones de la LICENCIA ABIERTA AL PÚBLICO que otorga INFOTEC para la utilización
 * del SemanticWebBuilder 4.0.
 *
 * INFOTEC no otorga garantía sobre SemanticWebBuilder, de ninguna especie y naturaleza, ni implícita ni explícita,
 * siendo usted completamente responsable de la utilización que le dé y asumiendo la totalidad de los riesgos que puedan derivar
 * de la misma.
 *
 * Si usted tiene cualquier duda o comentario sobre SemanticWebBuilder, INFOTEC pone a su disposición la siguiente
 * dirección electrónica:
 *  http://www.semanticwebbuilder.org
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

// TODO: Auto-generated Javadoc
/**
 * The Class RemoteGraph.
 * 
 * @author javier.solis
 */
public class RemoteGraph extends GraphBase implements Graph
{
    
    /** The uri. */
    private String uri;
    
    /** The query all. */
    private boolean queryAll=false;

    /**
     * Cronstruct a Remote Graph.
     * 
     * @param uri for the Remote Sparql endpoint
     */
    public RemoteGraph(String uri)
    {
        this(uri,false);
    }

    /**
     * Cronstruct a Remote Graph.
     * 
     * @param uri for the Remote Sparql endpoint
     * @param queryAll support for query all triples (default false)
     */
    public RemoteGraph(String uri, boolean queryAll)
    {
        this.uri=uri;
        this.queryAll=queryAll;
    }

    /**
     * Gets the node string.
     * 
     * @param n the n
     * @return the node string
     */
    private String getNodeString(Node n)
    {
        String ret=null;
        if(n.isURI())ret="<"+n.getURI()+">";
        else ret=n.toString();
        //System.out.println("node:"+ret);
        return ret;
    }


    /**
     * Answer an iterator over all the triples held in this graph's non-reified triple store
     * that match <code>m</code>.
     * 
     * @param m the m
     * @return the extended iterator
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

    /**
     * Gets the uri.
     * 
     * @return the uri
     */
    public String getUri() {
        return uri;
    }

}
