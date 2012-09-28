/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.bigdata.test;

import com.bigdata.rdf.sail.BigdataSail;
import com.bigdata.rdf.sail.BigdataSailRepository;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.rdf.model.impl.ModelCom;
import com.hp.hpl.jena.vocabulary.RDF;
import java.io.File;
import java.io.FileInputStream;
import java.util.Iterator;
import java.util.Properties;
import org.openrdf.OpenRDFException;
import org.openrdf.query.BindingSet;
import org.openrdf.query.QueryEvaluationException;
import org.openrdf.query.QueryLanguage;
import org.openrdf.query.TupleQueryResult;
import org.openrdf.repository.Repository;
import org.openrdf.repository.RepositoryConnection;
import org.openrdf.rio.RDFFormat;
import org.semanticwb.bigdata.BigdataGraph;

/**
 *
 * @author jei
 */
public class TestBigData {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        Properties properties = new Properties();
        File journal = new File("/Users/javier.solis.g/bigdata.jnl");
        properties.setProperty(BigdataSail.Options.FILE, journal.getAbsolutePath());
        //The persistence engine.  Use 'Disk' for the WORM or 'DiskRW' for the RWStore.

        properties.setProperty("com.bigdata.journal.AbstractJournal.bufferMode","DiskRW");
        properties.setProperty("com.bigdata.btree.writeRetentionQueue.capacity", "4000");
        properties.setProperty("com.bigdata.btree.BTree.branchingFactor", "128");

        //# 200M initial extent.
        properties.setProperty("com.bigdata.journal.AbstractJournal.initialExtent", "209715200");
        properties.setProperty("com.bigdata.journal.AbstractJournal.maximumExtent", "209715200");

        //##
        //## Setup for QUADS mode without the full text index.
        //##
        properties.setProperty("com.bigdata.rdf.sail.isolatableIndices", "true");
        properties.setProperty("com.bigdata.rdf.sail.truthMaintenance", "false");
        //com.bigdata.rdf.store.AbstractTripleStore.quads=false
        properties.setProperty("com.bigdata.rdf.store.AbstractTripleStore.quads", "true");
        properties.setProperty("com.bigdata.rdf.store.AbstractTripleStore.statementIdentifiers", "false");
        properties.setProperty("com.bigdata.rdf.store.AbstractTripleStore.textIndex", "false");
        properties.setProperty("com.bigdata.rdf.store.AbstractTripleStore.axiomsClass", "com.bigdata.rdf.axioms.NoAxioms");
        properties.setProperty("com.bigdata.rdf.store.AbstractTripleStore.vocabularyClass", "com.bigdata.rdf.vocab.NoVocabulary");
        properties.setProperty("com.bigdata.rdf.store.AbstractTripleStore.justify", "false");
         

        // instantiate a sail
        BigdataSail sail = new BigdataSail(properties);
        System.out.println("NameSapce:"+sail.getDatabase().getNamespace());
        
        
        Repository repo = new BigdataSailRepository(sail);
        try {
            repo.initialize();
            RepositoryConnection con = repo.getConnection();
            try {
                con.setAutoCommit(false);
                
//                FileInputStream in=new FileInputStream("/programming/proys/hackaton/vgn_db/vgn2.nt");
//                con.add(in, "http://www.vgn.swb#", RDFFormat.N3);
                
//                FileInputStream in=new FileInputStream("/programming/proys/hackaton/vgng_db/vgng2.nt");
//                con.add(in, "http://www.vgng.swb#", RDFFormat.N3);
                
                //URL url = new URL("http://example.org/example/remote");
                //con.add(url, url.toString(), RDFFormat.RDFXML);
                
                con.commit();
            }
            finally {
                con.close();
            }
        }
        catch (Exception e) {
        // handle exception
            e.printStackTrace();
        }       
        
        System.out.println("Size:"+sail.getDatabase().getStatementCount()+" "+sail.getDatabase().getURICount());
        
                
//        BigdataGraph graph = new BigdataGraph(sail,false);
//        Model model = new ModelCom(graph);
        
//        model.removeAll();        
//        model.begin();
//        try
//        {
//            FileInputStream in=new FileInputStream("/programming/proys/hackaton/vgng_db/vgng2.nt");
//            model.read(in, null, "N3");
//        }catch(Exception e){e.printStackTrace();}                
//        model.commit();
                
//        Iterator<Statement> it=model.listStatements();
//        while (it.hasNext())
//        {
//            Statement statement = it.next();
//            System.out.println(statement);
//        }  
//        System.out.println(model.size());
//
//        model.close();

    }



}
