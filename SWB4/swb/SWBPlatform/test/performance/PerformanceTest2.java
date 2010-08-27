/**  
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
**/ 
 
package performance;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.Iterator;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.platform.SemanticClass;
import org.semanticwb.platform.SemanticOntology;
import org.semanticwb.platform.SemanticProperty;
import org.semanticwb.platform.SemanticVocabulary;

/**
 *
 * @author Jei
 */
public class PerformanceTest2 
{
    private Logger log=SWBUtils.getLogger(PerformanceTest2.class);

    public PerformanceTest2()
    {
    }

    @BeforeClass
    public static void setUpClass() throws Exception
    {
        String base=SWBUtils.getApplicationPath();
        SWBPlatform.createInstance();
        //SWBPlatform.getSemanticMgr().initializeDB();
        SWBPlatform.getSemanticMgr().addBaseOntology(base+"../../../web/WEB-INF/owl/swb.owl");
        SWBPlatform.getSemanticMgr().addBaseOntology(base+"../../../web/WEB-INF/owl/swb_rep.owl");
        SWBPlatform.getSemanticMgr().addBaseOntology(base+"../../../web/WEB-INF/owl/office.owl");
        SWBPlatform.getSemanticMgr().loadBaseVocabulary();
        //SWBPlatform.getSemanticMgr().loadDBModels();
        SWBPlatform.getSemanticMgr().getOntology().rebind();
    }

    @AfterClass
    public static void tearDownClass() throws Exception
    {
    }

    @Before
    public void setUp()
    {
    }

    @After
    public void tearDown()
    {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    public void test()
    {
        Long used=Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
        long ini=used;
        System.out.println("used:"+used);
        long time=System.currentTimeMillis();

        SemanticInstanceCacheMgr cache=new SemanticInstanceCacheMgr();
        
        for(int x=0;x<10000000;x++)
        {
            cache.add("http://www.sep.gob.mx/webpage#WebPage:"+x,"http://www.sep.gob.mx#WebPage");
        }
        
        System.out.println("Time:"+(System.currentTimeMillis()-time));
        used=Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
        System.out.println("used:"+(used-ini));
        System.gc();
        try
        {
            Thread.currentThread().sleep(15000);
        }catch(InterruptedException e){}
        used=Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
        System.out.println("used:"+(used-ini));
        cache.contains(null);
    }
}

class SemanticInstanceCacheMgr
{
    private ConcurrentHashMap<String, SemanticInstanceCache> map=new ConcurrentHashMap();

    private int precount=0;
    private ConcurrentHashMap<String, String> pre=new  ConcurrentHashMap();

    private String compressUri(String uri)
    {
        String ret=uri;
        int i=uri.lastIndexOf("#");
        String base="";
        if(i>-1)
        {
            base = uri.substring(0, i);
            String head=uri.substring(i);
            String com=pre.get(base);
            if(com==null)
            {
                com=""+precount;
                pre.put(base, com);
                precount++;
            }
            ret=com+head;
        }
        //System.out.println(uri+" "+base+" "+ret);
        return ret;
    }

    private String indexUri(String uri)
    {
        String ret=pre.get(uri);
        if(ret==null)
        {
            ret=""+precount;
            pre.put(uri, ret);
            precount++;
        }
        return ret;
    }


    public void add(String uri, String scls)
    {
        uri=compressUri(uri);
        scls=indexUri(scls);
        map.put(uri, new SemanticInstanceCache(uri,scls));
    }

    public void remove(String uri)
    {
        uri=compressUri(uri);
        map.remove(uri);
    }

    public boolean contains(String uri)
    {
        uri=compressUri(uri);
        return map.contains(uri);
    }

    public int size()
    {
        return map.size();
    }
}

class SemanticInstanceCache implements Comparable<SemanticInstanceCache>
{
    private String uri;
    private String sclass;

    public SemanticInstanceCache(String uri, String sclass)
    {
        this.uri=uri;
        this.sclass=sclass;
    }

    public String getSClass()
    {
        return sclass;
    }

    public String getUri()
    {
        return uri;
    }

    @Override
    public String toString()
    {
        return uri;
    }

    @Override
    public boolean equals(Object obj)
    {
        return obj.toString().equals(uri);
    }

    @Override
    public int hashCode()
    {
        return uri.hashCode();
    }

    public int compareTo(SemanticInstanceCache o)
    {
        return uri.compareTo(o.toString());
    }
}
