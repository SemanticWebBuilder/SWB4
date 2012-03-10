/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.bigdata.test;

import com.bigdata.rdf.sail.BigdataSail;
import com.hp.hpl.jena.mem.faster.GraphMemFaster;
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
public class TestGrapMem {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        System.out.println("mem:"+(Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory()));
        
        Model model = new ModelCom(new GraphMemFaster());  
        
        long ntime,time=System.nanoTime();
      
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
