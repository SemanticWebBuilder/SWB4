
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author javier.solis
 */
public class TestSort
{

    public static void main(String args[])
    {
        System.out.println("Ini:"+Runtime.getRuntime().freeMemory());
        Random ran=new Random();
        long ini=System.currentTimeMillis();
        ConcurrentHashMap<String, Note> map=new ConcurrentHashMap();
        for(int x=0;x<1000000;x++)
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
        while (it.hasNext())
        {
            Note note = it.next();
        }
        System.out.println("List Sort:"+(System.currentTimeMillis()-ini)+" "+Runtime.getRuntime().freeMemory());

        ini=System.currentTimeMillis();
        Object arr[]=set.toArray();
        for(int x=0;x<arr.length;x++)
        {
            Note note = (Note)arr[x];
        }
        System.out.println("List arr:"+(System.currentTimeMillis()-ini)+" "+Runtime.getRuntime().freeMemory());

        
        ini=System.currentTimeMillis();
        for(int x=0;x<1000000;x++)
        {

            Note note=map.get("http://itz.com/note:"+x);
        }
        System.out.println("Search:"+(System.currentTimeMillis()-ini)+" "+Runtime.getRuntime().freeMemory());
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