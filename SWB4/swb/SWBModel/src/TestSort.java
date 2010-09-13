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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;

// TODO: Auto-generated Javadoc
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * The Class TestSort.
 * 
 * @author javier.solis
 */
public class TestSort
{
    
    /** The n. */
    static int n=1000000;

    /**
     * Arr.
     */
    public static void arr()
    {
        Random ran=new Random();
        long ini=System.currentTimeMillis();
        ArrayList arr=new ArrayList();
        for(int x=0;x<n;x++)
        {
            arr.add(new CacheNode("sep:/ontology/class:"+x,ran.nextLong(),ran.nextLong()));
        }
        System.out.println("Create list:"+(System.currentTimeMillis()-ini)+" "+(Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory()));

        ini=System.currentTimeMillis();
        Iterator it=arr.iterator();
        while (it.hasNext())
        {
            Object obj = it.next();
        }
        System.out.println("List iterator:"+(System.currentTimeMillis()-ini)+" "+(Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory()));

        ini=System.currentTimeMillis();
        for(int x=0;x<n;x++)
        {
            Object obj = arr.get(x);
        }
        System.out.println("List arr:"+(System.currentTimeMillis()-ini)+" "+(Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory()));

        ini=System.currentTimeMillis();
        TreeSet sort=new TreeSet(new Comparator()
        {
            public int compare(Object o1, Object o2)
            {
                CacheNode n1=(CacheNode)o1;
                CacheNode n2=(CacheNode)o2;
                return n1.getValues()[0]>n2.getValues()[0]?1:-1;
            }
        });
        for(int x=0;x<n;x++)
        {
            sort.add(arr.get(x));
        }
        System.out.println("Sort:"+(System.currentTimeMillis()-ini)+" "+(Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory()));

        ini=System.currentTimeMillis();
        it=sort.iterator();
        while (it.hasNext())
        {
            Object obj = it.next();
        }
        System.out.println("List sort iterator:"+(System.currentTimeMillis()-ini)+" "+(Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory()));
    }

    /**
     * Map.
     */
    public static void map()
    {
        Random ran=new Random();
        long ini=System.currentTimeMillis();
        HashMap map=new HashMap();
        for(int x=0;x<n;x++)
        {
            CacheNode node=new CacheNode("sep:/ontology/class:"+x,ran.nextLong(),ran.nextLong(),ran.nextLong());
            map.put(node.getUri(),node);
        }
        System.out.println("Create map:"+(System.currentTimeMillis()-ini)+" "+(Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory()));

        ini=System.currentTimeMillis();
        Iterator it=map.values().iterator();
        while (it.hasNext())
        {
            Object obj = it.next();
        }
        System.out.println("map iterator:"+(System.currentTimeMillis()-ini)+" "+(Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory()));

        ini=System.currentTimeMillis();
        TreeSet sort=new TreeSet(new Comparator()
        {
            public int compare(Object o1, Object o2)
            {
                CacheNode n1=(CacheNode)o1;
                CacheNode n2=(CacheNode)o2;
                return n1.getValues()[0]>n2.getValues()[0]?1:-1;
            }
        });
        sort.addAll(map.values());
        System.out.println("Sort map:"+(System.currentTimeMillis()-ini)+" "+(Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory()));

        ini=System.currentTimeMillis();
        it=sort.iterator();
        while (it.hasNext())
        {
            Object obj = it.next();
        }
        System.out.println("List sort iterator:"+(System.currentTimeMillis()-ini)+" "+(Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory()));

        ini=System.currentTimeMillis();
        sort=new TreeSet(new Comparator()
        {
            public int compare(Object o1, Object o2)
            {
                CacheNode n1=(CacheNode)o1;
                CacheNode n2=(CacheNode)o2;
                return n1.getValues()[1]>n2.getValues()[1]?1:-1;
            }
        });
        sort.addAll(map.values());
        System.out.println("Sort map2:"+(System.currentTimeMillis()-ini)+" "+(Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory()));

        ini=System.currentTimeMillis();
        it=sort.iterator();
        while (it.hasNext())
        {
            Object obj = it.next();
        }
        System.out.println("List sort iterator 2:"+(System.currentTimeMillis()-ini)+" "+(Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory()));

    }
    

    /**
     * The main method.
     * 
     * @param args the arguments
     */
    public static void main(String args[])
    {
        //arr();
        map();

//        ini=System.currentTimeMillis();
//        for(int x=0;x<n;x++)
//        {
//            arr.contains(("http://www.semanticwebbuilder.org/ontology/class:"+x));
//            //arr.contains(("http://www.semanticwebbuilder.org/ontology/class:"+x).hashCode());
//        }
//        System.out.println("Search:"+(System.currentTimeMillis()-ini)+" "+(Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory()));


        /*
        ConcurrentHashMap<String, Note> map=new ConcurrentHashMap();
        for(int x=0;x<n;x++)
        {
            Note note=new Note();
            note.setUri("http://itz.com/note:"+x);
            note.setTitle("Note "+x);
            note.setId(ran.nextLong());
            note.setDate(new Date(ini+x));
            map.put(note.getUri(), note);
        }
        System.out.println("Create:"+(System.currentTimeMillis()-ini)+" "+Runtime.getRuntime().freeMemory());

        ini=System.currentTimeMillis();
        Iterator<Note> it=map.values().iterator();
        while (it.hasNext())
        {
            Note note = it.next();
        }
        System.out.println("List map:"+(System.currentTimeMillis()-ini)+" "+Runtime.getRuntime().freeMemory());

        ini=System.currentTimeMillis();
        TreeSet<Note> set=new TreeSet(new Comparator()
        {
            public int compare(Object o1, Object o2)
            {
                Note n1=(Note)o1;
                Note n2=(Note)o2;
                //return (n1.getId()>n2.getId())?1:-1;
                //return n1.getTitle().compareTo(n2.getTitle());
                return n1.getDate().getTime()>n2.getDate().getTime()?1:-1;
            }
        });
        set.addAll(map.values());
        System.out.println("Sort map:"+(System.currentTimeMillis()-ini)+" "+Runtime.getRuntime().freeMemory());
        ini=System.currentTimeMillis();
        it=set.iterator();
        int z=0;
        while (it.hasNext())
        {
            Note note = it.next();
            if(note.getTitle().indexOf("500000")>0)z++;
        }
        System.out.println("List filter Sort:"+(System.currentTimeMillis()-ini)+" "+Runtime.getRuntime().freeMemory()+" "+z);

        ini=System.currentTimeMillis();
        Object arr[]=set.toArray();
        for(int x=0;x<arr.length;x++)
        {
            Note note = (Note)arr[x];
        }
        System.out.println("List arr:"+(System.currentTimeMillis()-ini)+" "+Runtime.getRuntime().freeMemory());

        
        ini=System.currentTimeMillis();
        for(int x=0;x<n;x++)
        {

            Note note=map.get("http://itz.com/note:"+x);
        }
        System.out.println("Search:"+(System.currentTimeMillis()-ini)+" "+Runtime.getRuntime().freeMemory());

        */

    }

}


class CacheNode
{
    private String uri;
    private long values[];

    public CacheNode(String uri,long... values)
    {
        this.uri=uri;
        this.values=values;
    }

    /**
     * @return the uri
     */
    public String getUri() {
        return uri;
    }

    /**
     * @param uri the uri to set
     */
    public void setUri(String uri) {
        this.uri = uri;
    }

    /**
     * @return the title
     */
    public long[] getValues() {
        return values;
    }

    /**
     * @param title the title to set
     */
    public void setValue(long values[]) {
        this.values = values;
    }
}


class Note
{
    private String uri;
    private String title;
    private Date date;
    private long id;

    /**
     * @return the uri
     */
    public String getUri() {
        return uri;
    }

    /**
     * @param uri the uri to set
     */
    public void setUri(String uri) {
        this.uri = uri;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the date
     */
    public Date getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(long id) {
        this.id = id;
    }
    

}