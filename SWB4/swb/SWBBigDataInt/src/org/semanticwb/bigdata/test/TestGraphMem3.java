/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.bigdata.test;

import com.hp.hpl.jena.mem.faster.GraphMemFaster;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.impl.ModelCom;
import java.io.FileInputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 *
 * @author javier.solis.g
 */
public class TestGraphMem3
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        System.out.println("mem:"+(Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory()));
        
        Model model = new ModelCom(new GraphMemFaster());  
        
        long ntime,time=System.nanoTime();

        try
        {
            model.read(new FileInputStream("/Users/javier.solis.g/Desktop/triples/infoboxes-fixed.nt"),null,"N-TRIPLE");
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        
        ntime=System.nanoTime();System.out.println("Load:"+model.size()+" "+((ntime-time)/1000000.0)+"ms");time=System.nanoTime();
        System.out.println("mem:"+(Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory()));time=System.nanoTime();
                
        long x=model.size();
        ntime=System.nanoTime();System.out.println("count:"+x+" "+((ntime-time)/1000000.0)+"ms");time=System.nanoTime();  
        
        
        Iterator it=model.listStatements(model.getResource("<http://dbpedia.org/resource/Metropolitan_Museum_of_Art>"), null, (String)null);
        ntime=System.nanoTime();System.out.println("find subj:"+"x"+" "+((ntime-time)/1000000.0)+"ms");time=System.nanoTime();
        
        it=model.listStatements(model.getResource("<http://dbpedia.org/resource/Metropolitan_Museum_of_Art>"), null, (String)null);
        ntime=System.nanoTime();System.out.println("find subj:"+"x"+" "+((ntime-time)/1000000.0)+"ms");time=System.nanoTime();
        
    }
}
