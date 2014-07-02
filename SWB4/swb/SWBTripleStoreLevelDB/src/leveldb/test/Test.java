package leveldb.test;

import java.io.IOException;
import java.util.HashMap;
import org.semanticwb.store.Graph;
import org.semanticwb.store.Triple;
import org.semanticwb.store.TripleIterator;
import org.semanticwb.store.leveldb.GraphImp;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author javier.solis.g
 */
public class Test
{
    public static void main(String[] args) throws IOException
    {
        System.out.println("Init...");
        long time = System.currentTimeMillis();
        
        HashMap<String,String> params=new HashMap();
        params.put("path", "/data/leveldb");
                
        Graph graph = new GraphImp("swb",params);
        graph.setTransactionEnabled(false);
        //graph.setEncodeURIs(false);

        System.out.println("time db:" + (System.currentTimeMillis() - time));
        time = System.currentTimeMillis();
        
        try
        {
            System.out.println("size:" + graph.count());

            System.out.println("time size:" + (System.currentTimeMillis() - time));
            time = System.currentTimeMillis();

    //************* TEST  

            if (graph.count() < 5000000)
            {
                try
                {
                    graph.load("/data/bench/infoboxes-fixed.nt", graph.count(), graph.count() + 5000000);
                } catch (Exception e)
                {
                    e.printStackTrace();
                }

                System.out.println("time insert:" + (System.currentTimeMillis() - time));
                time = System.currentTimeMillis();
            }

            System.out.println(graph.count());          
            System.out.println("time size:" + (System.currentTimeMillis() - time));
            time = System.currentTimeMillis();
            
            int z=1;            
            long x=0;
            
            x=0;
            for (z = 0; z < 1000; z++)
            {               
                TripleIterator it=graph.findTriples(new Triple("<http://www4.wiwiss.fu-berlin.de/bizer/bsbm/v01/instances/ProductType" + z + ">", "<http://www.w3.org/1999/02/22-rdf-syntax-ns#type>", "<http://www4.wiwiss.fu-berlin.de/bizer/bsbm/v01/vocabulary/ProductType>"));                            
                x=it.count();
//                while (it.hasNext())
//                {
//                    Triple triple = it.next();
//                    //System.out.println(triple);
//                    x++;
//                }
            }

            System.out.println("time find 1:" + x+" "+(System.currentTimeMillis() - time));
            time = System.currentTimeMillis();
            
            x=0; 
//            for (z = 0; z < 1000; z++)
            {
                TripleIterator it=graph.findTriples(new Triple("<http://www4.wiwiss.fu-berlin.de/bizer/bsbm/v01/instances/ProductType" + z + ">", null,null),true);            
//                x=it.count();
                while (it.hasNext())
                {
                    Triple triple = it.next();
                    System.out.println(triple);
                    x++;
                }
            }

            System.out.println("time find 2:" + x+" "+ (System.currentTimeMillis() - time));
            time = System.currentTimeMillis();

            x=0; 
//            for (z = 0; z < 1000; z++)
            {
                TripleIterator it=graph.findTriples(new Triple(null, "<http://www.w3.org/2000/01/rdf-schema#label>", "\"richer\""),null,100L,0L,true);            
//                x=it.count();
                while (it.hasNext())
                {
                    Triple triple = it.next();
                    System.out.println(triple);
                    x++;
                }
            }

            System.out.println("time find 3:" + x+" "+ (System.currentTimeMillis() - time));
            time = System.currentTimeMillis();

//*
            //Test remove
            if (false)
            {
                graph.removeTriples(new Triple(null, "<http://www.w3.org/2000/01/rdf-schema#label>", "\"richer\""));

                System.out.println("time remove:" + (System.currentTimeMillis() - time));
                time = System.currentTimeMillis();

                x=0; 
                //for (z = 0; z < 1000; z++)
                {
                    TripleIterator it=graph.findTriples(new Triple(null, "<http://www.w3.org/2000/01/rdf-schema#label>", "\"richer\""));            
                    x=it.count();
                }

                System.out.println("time find remove:" + x+" "+ (System.currentTimeMillis() - time));
                time = System.currentTimeMillis();

            }
            
            //Thread.sleep(20000L);
            
    //*/
            
            
            
        }catch(Exception e)
        {
            e.printStackTrace();
        }finally
        {
            time = System.currentTimeMillis();
            graph.close();
            System.out.println("time fin:" + (System.currentTimeMillis() - time));
        }

    }
}
    

