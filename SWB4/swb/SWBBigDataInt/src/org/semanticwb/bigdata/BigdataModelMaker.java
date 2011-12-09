/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.bigdata;

import com.bigdata.rdf.sail.BigdataSail;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.impl.ModelCom;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Properties;

/**
 *
 * @author jei
 */
public class BigdataModelMaker
{
    private String path;

    public BigdataModelMaker(String path)
    {
        this.path=path;
    }

    public Iterator<String> listModelNames()
    {
        ArrayList<String> ret=new ArrayList();
        File dir=new File(path);
        if(dir.isDirectory())
        {
            File[] files=dir.listFiles();
            for(int x=0;x<files.length;x++)
            {
                File f=files[x];
                if(f.isFile() && f.getName().endsWith(".jnl"))
                {
                    ret.add(f.getName().substring(0, f.getName().length()-4));
                }
            }
        }
        return ret.iterator();
    }

    private Properties getProperties(String name, boolean inference)
    {
        Properties properties = new Properties();
        File journal = new File(path+"/"+name+".jnl");
        properties.setProperty(BigdataSail.Options.FILE, journal.getAbsolutePath());
        
        //The persistence engine.  Use 'Disk' for the WORM or 'DiskRW' for the RWStore.
        properties.setProperty("com.bigdata.journal.AbstractJournal.bufferMode","DiskRW");
        properties.setProperty("com.bigdata.btree.writeRetentionQueue.capacity", "4000");
        properties.setProperty("com.bigdata.btree.BTree.branchingFactor", "128");

        //# 200M initial extent.
        properties.setProperty("com.bigdata.journal.AbstractJournal.initialExtent", "100715200");
        properties.setProperty("com.bigdata.journal.AbstractJournal.maximumExtent", "100715200");

        //##
        //## Setup for QUADS mode without the full text index.
        //##
        properties.setProperty("com.bigdata.rdf.sail.truthMaintenance", "false");
        properties.setProperty("com.bigdata.rdf.store.AbstractTripleStore.quads", "true");
        properties.setProperty("com.bigdata.rdf.store.AbstractTripleStore.statementIdentifiers", "false");
        properties.setProperty("com.bigdata.rdf.store.AbstractTripleStore.textIndex", "false");
        properties.setProperty("com.bigdata.rdf.store.AbstractTripleStore.axiomsClass", "com.bigdata.rdf.axioms.NoAxioms");
        
        // turn off the statement identifiers feature for provenance
        //properties.setProperty(BigdataSail.Options.STATEMENT_IDENTIFIERS,"false");
        //properties.setProperty(BigdataSail.Options.STATEMENT_IDENTIFIERS, "true");
        //properties.setProperty(BigdataSail.Options.NATIVE_JOINS,"true");

        //not inference
        //if(!inference)properties.setProperty(BigdataSail.Options.AXIOMS_CLASS,"com.bigdata.rdf.axioms.NoAxioms");

        //fast load

        //set the initial and maximum extent of the journal
        //properties.setProperty(BigdataSail.Options.INITIAL_EXTENT,"209715200");
        //properties.setProperty(BigdataSail.Options.MAXIMUM_EXTENT,"209715200");
        //turn off automatic inference in the SAIL
        //properties.setProperty(BigdataSail.Options.TRUTH_MAINTENANCE,"false");
        //don't store justification chains, meaning retraction requires full manual
        //re-closure of the database
        //properties.setProperty(BigdataSail.Options.JUSTIFY,"false");
        //turn off the free text index
        //properties.setProperty(BigdataSail.Options.TEXT_INDEX,"false");

        return properties;
    }

    public Model getModel(String name, boolean inference)
    {
        boolean ret=false;
        Iterator<String> it=listModelNames();
        while (it.hasNext())
        {
            String aname = it.next();
            if(aname.equals(name))
            {
                ret = true;
            }
        }

        if(ret)
        {
            return createModel(name, inference);
        }
        return null;
    }

    public Model createModel(String name, boolean inference)
    {
        Properties properties=getProperties(name, inference);
        // instantiate a sail
        BigdataSail sail = new BigdataSail(properties);
        //System.out.println("Namespace:"+sail.getDatabase().getNamespace());
        BigdataGraph graph = new BigdataGraph(sail,inference);
        Model model = new ModelCom(graph);
        return model;
    }

}
