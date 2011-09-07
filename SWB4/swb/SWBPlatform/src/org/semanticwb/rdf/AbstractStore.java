/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.rdf;
import com.hp.hpl.jena.query.Dataset;
import com.hp.hpl.jena.rdf.model.Model;
import java.util.Iterator;

/**
 *
 * @author jei
 */
public interface AbstractStore
{
    public void init();

    public void removeModel(String name);

    public Model loadModel(String name);

    public Model getModel(String name);
    
    public Iterator<String> listModelNames();

    public void close();

    public Dataset getDataset();

}
