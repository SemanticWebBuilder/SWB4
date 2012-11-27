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

        properties.setProperty("com.bigdata.journal.AbstractJournal.bufferMode","Disk");
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
        properties.setProperty("com.bigdata.rdf.store.AbstractTripleStore.quads", "false");
        properties.setProperty("com.bigdata.rdf.store.AbstractTripleStore.statementIdentifiers", "false");
        properties.setProperty("com.bigdata.rdf.store.AbstractTripleStore.textIndex", "true");
        properties.setProperty("com.bigdata.rdf.store.AbstractTripleStore.axiomsClass", "com.bigdata.rdf.axioms.NoAxioms");
        properties.setProperty("com.bigdata.rdf.store.AbstractTripleStore.vocabularyClass", "com.bigdata.rdf.vocab.NoVocabulary");
        properties.setProperty("com.bigdata.rdf.store.AbstractTripleStore.justify", "false");
         

        // instantiate a sail
        BigdataSail sail = new BigdataSail(properties);
        System.out.println("NameSapce:"+sail.getDatabase().getNamespace());
        
        /*
        Repository repo = new BigdataSailRepository(sail);
        try {
            repo.initialize();
            RepositoryConnection con = repo.getConnection();
            try {
                con.setAutoCommit(false);
                
                //FileInputStream in=new FileInputStream("/programming/proys/hackaton_data/vgn_db/vgn3.nt");
                //con.add(in, "http://datosabiertos.gob.mx/data/", RDFFormat.N3);
                
                //FileInputStream in=new FileInputStream("/programming/proys/hackaton_data/vgng_db/vgng3.nt");
                //con.add(in, "http://datosabiertos.gob.mx/data/", RDFFormat.N3);
                                
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
        */
        
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
