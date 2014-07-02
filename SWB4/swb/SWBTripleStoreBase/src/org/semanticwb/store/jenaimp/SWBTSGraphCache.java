/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.store.jenaimp;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.hp.hpl.jena.graph.Node;
import com.hp.hpl.jena.graph.Triple;
import com.hp.hpl.jena.graph.TripleMatch;
import com.hp.hpl.jena.graph.TripleMatchFilter;
import com.hp.hpl.jena.graph.impl.GraphBase;
import com.hp.hpl.jena.graph.query.QueryHandler;
import com.hp.hpl.jena.mem.ArrayBunch;
import com.hp.hpl.jena.mem.HashedTripleBunch;
import com.hp.hpl.jena.mem.TripleBunch;
import com.hp.hpl.jena.shared.PrefixMapping;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;
import com.hp.hpl.jena.util.iterator.NullIterator;
import com.hp.hpl.jena.util.iterator.SingletonIterator;
import java.util.concurrent.TimeUnit;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.rdf.GraphExt;


/**
 *
 * @author jei
 */
public class SWBTSGraphCache extends GraphBase implements GraphExt
{

    private static Logger log = SWBUtils.getLogger(SWBTSGraphCache.class);
    private SWBTSGraph g;
    //private GraphMemFaster c = null;
    //private LinkedList<String> list=null;
    //private HashSet<String> set=null;
    private int maxSize = 0;
    private LoadingCache<Node, TripleBunch> cache = null;

    public SWBTSGraphCache(final SWBTSGraph g, int maxSize)
    {
        this.g = g;
        //c = new GraphMemFaster();
        //list=new LinkedList();
        //set=new HashSet();
        this.maxSize = maxSize;

        cache = CacheBuilder.newBuilder()
                .maximumSize(maxSize)
                .expireAfterAccess(5, TimeUnit.MINUTES)
//                .removalListener(new RemovalListener<String, TripleBunch>()
//                {
//                    @Override
//                    public void onRemoval(RemovalNotification<String, TripleBunch> rn)
//                    {
//                        String s = rn.getKey();
//                        System.out.print("remove:" + s);
//                    }
//                })
                .build(new CacheLoader<Node, TripleBunch>()
                {
                    @Override
                    public TripleBunch load(Node key) throws Exception
                    {
                        //System.out.println("cache load:" + key+" "+cache.size());
                        ExtendedIterator<Triple> it=g.find(key, Node.ANY, Node.ANY);
                        int x=0;
                        TripleBunch s=new ArrayBunch();
                        while (it.hasNext())
                        {
                            Triple triple = it.next();
                            //System.out.println("cache add triple:"+triple+" "+x);
                            x++;
                            if(x==9)s = new HashedTripleBunch(s);
                            s.add(triple);
                        }
                        it.close();
                        return s;
                    }
                });
    }

    @Override
    protected ExtendedIterator<Triple> graphBaseFind(TripleMatch tm)
    {
        Triple tp=tm.asTriple();
        org.semanticwb.store.Triple t= SWBTSUtils.toSWBTriple(tp);
       
        //System.out.println("graphBaseFindC:"+tm+"-->"+t); 
        if (t.getSubject()!=null)
        {
//            if(t.getProperty()!=null && t.getObject()!=null)
//            {
//                if(g.getGraphBase().contains(t))
//                {
//                    return new SingletonIterator<>(tp);
//                }else
//                {
//                    return NullIterator.<Triple>instance();  
//                }
//            }
            
            try
            {
                TripleBunch s = cache.get(tp.getSubject());
                //System.out.println("get:"+tp.getSubject()+" "+s.size());
                //Esta vacio
                if(s.size()==0)return NullIterator.<Triple>instance();    
                //Buscando un triple
                if(t.getProperty()!=null && t.getObject()!=null)
                {
                    if(s.contains(tp))
                    {
                        return new SWBTSDeleteableIterator(new SingletonIterator<Triple>(tp),g);
                    }else return NullIterator.<Triple>instance();    
                }else if(t.getProperty()==null && t.getObject()==null)
                {
                    return new SWBTSDeleteableIterator(s.iterator(),g);
                }
                return new SWBTSDeleteableIterator(s.iterator().filterKeep(new TripleMatchFilter(tp)),g);
            } catch (Exception e) //Error getting cache element
            {
                e.printStackTrace();
            }
        }
        //
        {
            //ExtendedIterator<Triple> it = new SWBTSIterator(g.getGraphBase().findTriples(t));
            return g.graphBaseFind(tm);
        }
    }

    @Override
    public void performAdd(Triple t)
    {
        //System.out.println("performAddC:"+t);        
        TripleBunch s = cache.getIfPresent(t.getSubject());
        if (s != null)
        {
            if(!s.contains(t))s.add(t);
        }
        g.performAdd(t);
    }

    @Override
    public void performDelete(Triple t)
    {
        //System.out.println("performDeleteC:"+t);
        TripleBunch s = cache.getIfPresent(t.getSubject());
        if (s != null)
        {
            s.remove(t);
        }
        g.performDelete(t);
    }

    public String getName()
    {
        return g.getName();
    }

    @Override
    public void close()
    {
        g.close();
    }

    @Override
    public PrefixMapping getPrefixMapping()
    {
        return g.getPrefixMapping();
    }

    @Override
    public QueryHandler queryHandler()
    {
        return g.queryHandler();
    }

    @Override
    public long count(TripleMatch tm, String stype)
    {
        return g.count(tm, stype);
    }

    @Override
    public ExtendedIterator<Triple> find(TripleMatch tm, String stype, Long limit, Long offset, String sortby)
    {
        return g.find(tm, stype, limit, offset, sortby);
    }

    @Override
    protected int graphBaseSize()
    {
        return g.size();
    }
    
    
    
    
    
    
}
