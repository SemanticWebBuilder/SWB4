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
public class TestSWBAdmin
{
    public static void main(String[] args) throws IOException
    {
        System.out.println("Init...");
        long time = System.currentTimeMillis();
        
        HashMap<String,String> params=new HashMap();
        params.put("path", "/data/leveldb");
                
        Graph graph = new GraphImp("swbadmin",params);
        graph.setTransactionEnabled(false);
        //graph.setEncodeURIs(false);

        System.out.println("time db:" + (System.currentTimeMillis() - time));
        time = System.currentTimeMillis();
        
        try
        {
            long c=graph.count();
            System.out.println("size:" + c);

            System.out.println("time size:" + (System.currentTimeMillis() - time));
            time = System.currentTimeMillis();

    //************* TEST  

            if (c == 0)
            {
                try
                {
                    graph.load("/data/bench/SWBAdmin.nt", c, c + 5000000);
                } catch (Exception e)
                {
                    e.printStackTrace();
                }

                System.out.println("time insert:" + (System.currentTimeMillis() - time));
                time = System.currentTimeMillis();
                
                System.out.println(graph.count());          
                System.out.println("time size:" + (System.currentTimeMillis() - time));
                time = System.currentTimeMillis();
            }

            
            int z=1;            
            long x=0;
            
            x=0;
            //for (z = 0; z < 1000; z++)
            {               
                TripleIterator it=graph.findTriples(new Triple("<http://www.semanticwb.org/SWBAdmin#WebPage:home>", null, null));                            
                //x=it.count();
                while (it.hasNext())
                {
                    Triple triple = it.next();
                    System.out.println(triple);
                    x++;
                }
            }
            
            System.out.println("time find 1:" + x+" "+(System.currentTimeMillis() - time));
            time = System.currentTimeMillis();

            x=0;
            //for (z = 0; z < 1000; z++)
            {               
                TripleIterator it=graph.findTriples(new Triple(null, "<http://www.semanticwebbuilder.org/swb4/ontology#active>", null));                            
                //x=it.count();
                while (it.hasNext())
                {
                    Triple triple = it.next();
                    System.out.println(triple);
                    x++;
                }
            }
            
            System.out.println("time find 1:" + x+" "+(System.currentTimeMillis() - time));
            time = System.currentTimeMillis();
            
            
            
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
    

