
package org.semanticwb.model;

import org.semanticwb.platform.*;
import java.util.*;


/** objeto: comparador de topicos, se utiliza para ordenar topicos
 * @author Javier Solis Gonzalez
 * @version
 */
public class SWBComparator implements Comparator
{

    String lang = null;

    /** Creates new WBPriorityComaprator */
    public SWBComparator()
    {
    }

    public SWBComparator(String lang)
    {
        this.lang = lang;
    }

    public int compare(java.lang.Object obj1, java.lang.Object obj2)
    {
        int ret;
        SemanticObject sobj1 = null;
        SemanticObject sobj2 = null;
        //System.out.println("obj1:"+obj1.getClass()+" obj2:"+obj2.getClass());
        if(obj1 instanceof GenericObject) sobj1=((GenericObject)obj1).getSemanticObject();
        else sobj1 = (SemanticObject) obj1;
        if(obj2 instanceof GenericObject) sobj2=((GenericObject)obj2).getSemanticObject();
        else sobj2 = (SemanticObject) obj2;

        String name1=sobj1.getProperty(SWBContext.getVocabulary().swb_webPageSortName);
        //if(name1==null)name1=sobj1.getDisplayName(lang);
        if(name1==null)name1=sobj1.getProperty(SWBContext.getVocabulary().swb_title,null,lang);
        if(name1==null)name1=sobj1.getProperty(SWBContext.getVocabulary().swb_title);
        String name2=sobj2.getProperty(SWBContext.getVocabulary().swb_webPageSortName);
        //if(name2==null)name2=sobj2.getDisplayName(lang);
        if(name2==null)name2=sobj2.getProperty(SWBContext.getVocabulary().swb_title,null,lang);
        if(name2==null)name2=sobj2.getProperty(SWBContext.getVocabulary().swb_title);
        //System.out.println("name1:"+name1+" name2:"+name2);
        if(name1!=null && name2!=null)
        {
            ret = name1.compareToIgnoreCase(name2);
            if (ret == 0 && !sobj1.getURI().equals(sobj2.getURI()))ret = -1;
        }else
        {
            ret=-1;
        }
        return ret;
    }
    
    public static Iterator sortSermanticObjects(Iterator it, String lang)
    {    
        TreeSet set=new TreeSet(new SWBComparator(lang));
        while(it.hasNext())
        {
            set.add(it.next());
        }
        return set.iterator();        
    }
    
    public static Iterator sortSermanticObjects(Iterator it)
    {
        TreeSet set=new TreeSet(new SWBComparator());
        while(it.hasNext())
        {
            set.add(it.next());
        }
        return set.iterator();
    }

    public static Iterator sortSortableObject(Iterator it)
    {
        TreeSet set=new TreeSet(new Comparator()
        {
            public int compare(Object o1, Object o2)
            {
                int ret=1;
                int v1=999999999;
                int v2=999999999;
                if(o1 instanceof Sortable)
                {
                    if(o1!=null)v1=((Sortable)o1).getIndex();
                    if(o2!=null)v2=((Sortable)o2).getIndex();
                }else if(o1 instanceof SemanticObject)
                {
                    String t1=((SemanticObject)o1).getProperty(SWBContext.getVocabulary().swb_index);
                    String t2=((SemanticObject)o2).getProperty(SWBContext.getVocabulary().swb_index);
                    if(t1!=null)v1=Integer.parseInt(t1);
                    if(t2!=null)v2=Integer.parseInt(t2);
                }
                ret=v1<v2?-1:1;
                return ret;
            }
        });
        while(it.hasNext())
        {
            set.add(it.next());
        }
        return set.iterator();

    }

}
