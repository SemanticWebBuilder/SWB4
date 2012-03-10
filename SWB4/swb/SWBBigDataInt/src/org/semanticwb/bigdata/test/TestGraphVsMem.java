/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.bigdata.test;

import com.bigdata.rdf.sail.BigdataSail;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.rdf.model.impl.ModelCom;
import com.hp.hpl.jena.vocabulary.RDF;
import java.io.File;
import java.util.Date;
import java.util.Iterator;
import java.util.Properties;
import org.openrdf.query.BindingSet;
import org.openrdf.query.QueryEvaluationException;
import org.openrdf.query.QueryLanguage;
import org.openrdf.query.TupleQueryResult;
import org.semanticwb.bigdata.BigdataGraph;

/**
 *
 * @author jei
 */
public class TestGraphVsMem {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        System.out.println("mem:"+(Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory()));
        
        Properties properties = new Properties();
        File journal = new File("/data.jnl");
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
        properties.setProperty("com.bigdata.rdf.sail.isolatableIndices", "true");
        properties.setProperty("com.bigdata.rdf.sail.truthMaintenance", "false");
        properties.setProperty("com.bigdata.rdf.store.AbstractTripleStore.quads", "true");
        properties.setProperty("com.bigdata.rdf.store.AbstractTripleStore.statementIdentifiers", "false");
        properties.setProperty("com.bigdata.rdf.store.AbstractTripleStore.textIndex", "false");
        properties.setProperty("com.bigdata.rdf.store.AbstractTripleStore.axiomsClass", "com.bigdata.rdf.axioms.NoAxioms");
        properties.setProperty("com.bigdata.rdf.store.AbstractTripleStore.vocabularyClass", "com.bigdata.rdf.vocab.NoVocabulary");
        properties.setProperty("com.bigdata.rdf.store.AbstractTripleStore.justify", "false");
         

        //fast load

        // set the initial and maximum extent of the journal
        //properties.setProperty(BigdataSail.Options.INITIAL_EXTENT,"209715200");
        //properties.setProperty(BigdataSail.Options.MAXIMUM_EXTENT,"209715200");
        // turn off automatic inference in the SAIL
        //properties.setProperty(BigdataSail.Options.TRUTH_MAINTENANCE,"false");
        // don't store justification chains, meaning retraction requires full manual
        // re-closure of the database
        //properties.setProperty(BigdataSail.Options.JUSTIFY,"false");
        // turn off the free text index
        //properties.setProperty(BigdataSail.Options.TEXT_INDEX,"false");

        // instantiate a sail
        BigdataSail sail = new BigdataSail(properties);
        System.out.println("NameSapce:"+sail.getDatabase().getNamespace());
        BigdataGraph graph = new BigdataGraph(sail,false);
        Model model = new ModelCom(graph);  
        
        long ntime,time=System.nanoTime();
/*        
        model.begin();        
        
        int x=0;
        
        for(x=0;x<1000000;x++)
        {            
            model.add(model.getResource("http://www.google.com#webpage:page"+x), model.getProperty("http://www.google.com#type"), model.getResource("http://www.google.com#webpage"));
            model.add(model.getResource("http://www.google.com#webpage:page"+x), model.getProperty("http://www.google.com#name"), model.createLiteral("Pagina "+x));
            model.add(model.getResource("http://www.google.com#webpage:page"+x), model.getProperty("http://www.google.com#active"), model.createLiteral("true"));
            model.add(model.getResource("http://www.google.com#webpage:page"+x), model.getProperty("http://www.google.com#valid"), model.createLiteral("true"));
            model.add(model.getResource("http://www.google.com#webpage:page"+x), model.getProperty("http://www.google.com#date"), model.createLiteral((new Date()).toString()));
            if(x%100000==0)System.out.println("Loading triples:"+x);
        }
        
        model.commit();
*/        
        ntime=System.nanoTime();System.out.println("Load:"+model.size()+" "+((ntime-time)/1000000.0)+"ms");time=System.nanoTime();
        System.out.println("mem:"+(Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory()));time=System.nanoTime();
        
        //x=store.count();
        //ntime=System.nanoTime();System.out.println("count:"+x+" "+((ntime-time)/1000000.0)+"ms");time=System.nanoTime();        
        
        Iterator it=model.listStatements(model.getResource("http://www.google.com#webpage:page50000"), null, (String)null);
        ntime=System.nanoTime();System.out.println("find subj:"+"x"+" "+((ntime-time)/1000000.0)+"ms");time=System.nanoTime();

        it=model.listStatements(null, model.getProperty("http://www.google.com#type"), (String)null);
        ntime=System.nanoTime();System.out.println("find prop:"+"x"+" "+((ntime-time)/1000000.0)+"ms");time=System.nanoTime();
        
        it=model.listStatements(null, null, "true");
        ntime=System.nanoTime();System.out.println("find obj:"+"0"+" "+((ntime-time)/1000000.0)+"ms");time=System.nanoTime();
        
        it=model.listStatements(model.getResource("http://www.google.com#webpage:page50000"), model.getProperty("http://www.google.com#type"), (String)null);
        ntime=System.nanoTime();System.out.println("find subj prop:"+"0"+" "+((ntime-time)/1000000.0)+"ms");time=System.nanoTime();

        it=model.listStatements(null, model.getProperty("http://www.google.com#type"), "true");
        ntime=System.nanoTime();System.out.println("find prop obj:"+"0"+" "+((ntime-time)/1000000.0)+"ms");time=System.nanoTime();
        
        it=model.listStatements(model.getResource("http://www.google.com#webpage:page50000"), null, "true");
        ntime=System.nanoTime();System.out.println("find subj obj:"+"0"+" "+((ntime-time)/1000000.0)+"ms");time=System.nanoTime();
        
        it=model.listStatements(model.getResource("http://www.google.com#webpage:page50000"), model.getProperty("http://www.google.com#type"), "true");
        ntime=System.nanoTime();System.out.println("find triple:"+"0"+" "+((ntime-time)/1000000.0)+"ms");time=System.nanoTime();
        
        it=model.listStatements(model.getResource("http://www.google.com#webpage:page50000"), null, (String)null);
        ntime=System.nanoTime();System.out.println("find subj:"+"x"+" "+((ntime-time)/1000000.0)+"ms");time=System.nanoTime();
        
        System.out.println("mem:"+(Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory()));                       
        
        model.close();

    }



}
