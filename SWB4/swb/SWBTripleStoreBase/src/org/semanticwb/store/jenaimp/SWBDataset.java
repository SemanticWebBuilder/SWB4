/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.store.jenaimp;

import com.hp.hpl.jena.graph.Graph;
import com.hp.hpl.jena.graph.Node;
import com.hp.hpl.jena.query.Dataset;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.shared.Lock;
import com.hp.hpl.jena.sparql.core.DatasetGraph;
import com.hp.hpl.jena.sparql.core.DatasetGraphMaker;
import com.hp.hpl.jena.sparql.core.DatasetGraphMap;
import java.util.Iterator;

/**
 *
 * @author javier.solis
 */
public class SWBDataset implements Dataset {

    private SWBTStore store = null;
    private String defaultName=null;
    private SWBDatasetGraph dsgraph=null;

    public SWBDataset(SWBTStore store, String defaultName) {
        this.store = store;
        this.defaultName=defaultName;
    }

    @Override
    public DatasetGraph asDatasetGraph() 
    {
        if(dsgraph==null)
        {
            synchronized(this)
            {
                if(dsgraph==null)
                {
                    //System.out.println("SWBDataset->asDatasetGraph:");
                    //new Exception().printStackTrace();
                    dsgraph=new SWBDatasetGraph(defaultName,Node.createURI(getDefaultModel().getNsPrefixURI(defaultName)),getDefaultModel().getGraph());
                    Iterator<String> it=listNames();
                    while (it.hasNext()) {
                        String name = it.next();
                        if(!name.equals(defaultName))
                        {
                            Model model=getNamedModel(name);                            
                            //System.out.println("SWBDatasource->asDatasetGraph->node:"+name+" "+Node.createURI(model.getNsPrefixURI(name)));
                            dsgraph.addGraph(name,Node.createURI(model.getNsPrefixURI(name)), model.getGraph());
                            //dsgraph.addGraph(Node.createURI(name), model.getGraph());
                        }
                    }
                }
            }
        }
        return dsgraph;
    }

    @Override
    public Model getDefaultModel() {
        //System.out.println("SWBDataset->getDefaultModel:");
        return store.getModel(defaultName);
    }

    @Override
    public Model getNamedModel(String uri) {
        //System.out.println("SWBDataset->getNamedModel:"+uri);
        return store.getModel(uri);
    }

    @Override
    public boolean containsNamedModel(String uri) {
        //System.out.println("SWBDataset->containsNamedModel:"+uri);
        return store.maker.listModelNames().contains(uri);
    }

    @Override
    public Iterator<String> listNames() {
        //System.out.println("SWBDataset->listNames:");
        return store.listModelNames();
    }

    @Override
    public Lock getLock() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void close() {
        store.close();
    }
}
