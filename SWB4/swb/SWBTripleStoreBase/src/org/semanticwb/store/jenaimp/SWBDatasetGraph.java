/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.store.jenaimp;

import com.hp.hpl.jena.graph.Graph;
import com.hp.hpl.jena.graph.Node;
import com.hp.hpl.jena.sparql.core.DatasetGraphMap;
import java.util.HashMap;

/**
 *
 * @author javiersolis
 */
public class SWBDatasetGraph extends DatasetGraphMap
{
    private HashMap<String,Node> names=new HashMap();
    private String defaultName=null;
    private Node defaultNode=null;
    private Graph defaultGraph=null;
    
    public SWBDatasetGraph(String defaultName, Node defaultNode, Graph defaultGraph)
    { 
        super(defaultGraph);
        this.defaultName=defaultName;
        this.defaultNode=defaultNode;
        this.defaultGraph=defaultGraph;
        names.put(defaultName, defaultNode);
    }

    public void addGraph(String name, Node graphName, Graph graph) 
    {
        names.put(name, graphName);
        super.addGraph(graphName, graph); //To change body of generated methods, choose Tools | Templates.
    }

//    @Override
//    public Graph getDefaultGraph() {
//        System.out.println("SWBDatasetGraph->getDefaultGraph");
//        return super.getDefaultGraph(); //To change body of generated methods, choose Tools | Templates.
//    }

//    @Override
//    public boolean containsGraph(Node graphNode) {
//        System.out.println("SWBDatasetGraph->containsGraph:"+graphNode);
//        return super.containsGraph(graphNode); //To change body of generated methods, choose Tools | Templates.
//    }

    @Override
    public Graph getGraph(Node graphNode) 
    {
        //System.out.println("SWBDatasetGraph->getGraph:"+graphNode);
        if(graphNode.getURI().startsWith("file:"))
        {
            String name=graphNode.getURI().substring(graphNode.getURI().lastIndexOf("/")+1);
            if(names.containsKey(name))
            {
                graphNode=names.get(name);
            }
        }       
        if(graphNode.equals(defaultNode))return defaultGraph;
        return super.getGraph(graphNode); //To change body of generated methods, choose Tools | Templates.
    }

//    @Override
//    public Iterator<Quad> find(Node g, Node s, Node p, Node o) {
//        System.out.println("SWBDatasetGraph->find:"+g+" "+s+" "+p+" "+o);
//        return super.find(g, s, p, o); //To change body of generated methods, choose Tools | Templates.
//    }
    
}
