
package org.semanticwb.model;

import org.semanticwb.platform.*;
import java.util.*;
import org.semanticwb.model.base.GenericObjectBase;


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
        String name1=sobj1.getProperty(SWBContext.getVocabulary().webPageSortName);
        if(name1==null)name1=sobj1.getProperty(SWBContext.getVocabulary().title,null,lang);
        if(name1==null)name1=sobj1.getProperty(SWBContext.getVocabulary().title);
        String name2=sobj2.getProperty(SWBContext.getVocabulary().webPageSortName);
        if(name2==null)name2=sobj2.getProperty(SWBContext.getVocabulary().title,null,lang);
        if(name2==null)name2=sobj2.getProperty(SWBContext.getVocabulary().title);
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

}
