
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
 */
package org.semanticwb.model;

import org.semanticwb.platform.*;
import java.util.*;

// TODO: Auto-generated Javadoc
/**
 * objeto: comparador de topicos, se utiliza para ordenar topicos.
 * 
 * @author Javier Solis Gonzalez
 * @version
 */
public class SWBComparator implements Comparator {
    
    /** The lang. */
    String lang = null;

    /**
     * Creates new WBPriorityComaprator.
     */
    public SWBComparator() {}

    /**
     * Instantiates a new sWB comparator.
     * 
     * @param lang the lang
     */
    public SWBComparator(String lang) {
        this.lang = lang;
    }

    /* (non-Javadoc)
     * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
     */
    public int compare(java.lang.Object obj1, java.lang.Object obj2)
    {
        int            ret;
        SemanticObject sobj1 = null;
        SemanticObject sobj2 = null;

        // System.out.println("obj1:"+obj1.getClass()+" obj2:"+obj2.getClass());
        if (obj1 instanceof GenericObject) {
            sobj1 = ((GenericObject) obj1).getSemanticObject();
        } else if (obj1 instanceof SemanticClass) {
            sobj1 = ((SemanticClass) obj1).getSemanticObject();
        } else {
            sobj1 = (SemanticObject) obj1;
        }

        if (obj2 instanceof GenericObject) {
            sobj2 = ((GenericObject) obj2).getSemanticObject();
        } else if (obj2 instanceof SemanticClass) {
            sobj2 = ((SemanticClass) obj2).getSemanticObject();
        } else {
            sobj2 = (SemanticObject) obj2;
        }

        String        name1 = null;
        String        name2 = null;
        SemanticClass cls1  = sobj1.getSemanticClass();
        SemanticClass cls2  = sobj2.getSemanticClass();

        // System.out.println("cls1:"+cls1+" cls2:"+cls2);
        if ((cls1 != null) || (cls2 != null)) {
            if ((cls1 != null) && (cls2 != null) && cls1.hasProperty(Sortable.swb_index.getName())
                    && cls2.hasProperty(Sortable.swb_index.getName())) {
                return compareSortable(obj1, obj2);
            }

            if ((cls1 != null) && cls1.hasProperty(WebPage.swb_webPageSortName.getName())) {
                name1 = sobj1.getProperty(WebPage.swb_webPageSortName);
            }

            if ((cls2 != null) && cls2.hasProperty(WebPage.swb_webPageSortName.getName())) {
                name2 = sobj2.getProperty(WebPage.swb_webPageSortName);
            }

            // System.out.println("name1:"+name1+" name2:"+name2);
        }

        if (name1 == null) {
            name1 = sobj1.getDisplayName(lang);
        }

        if (name2 == null) {
            name2 = sobj2.getDisplayName(lang);
        }

        // System.out.println("name1:"+name1+" name2:"+name2);
        if ((name1 != null) && (name2 != null)) {
            ret = name1.compareToIgnoreCase(name2);

            if (ret == 0) {
                ret = -1;
            }
        } else {
            ret = -1;
        }

        return ret;
    }

    /**
     * Sort sermantic objects.
     * 
     * @param lang the lang
     * @param its the its
     * @return the iterator
     */
    public static Iterator sortSermanticObjects(String lang, Iterator... its) {
        return sortSermanticObjects(new SWBComparator(lang), its);
    }

    public static Iterator sortSermanticObjects(Comparator comp, Iterator... its) {
        TreeSet set = new TreeSet(comp);

        for (int x = 0; x < its.length; x++) {
            Iterator it = its[x];

            while (it.hasNext()) {
                Object obj = it.next();

                if (obj != null) {
                    set.add(obj);
                } else {
                    System.out.println("Warn: obj==null");
                }
            }
        }
        return set.iterator();
    }

/*
    public static Iterator sortSermanticObjects(Iterator it, String lang)
    {
        TreeSet set=new TreeSet(new SWBComparator(lang));
        while(it.hasNext())
        {
            Object obj=it.next();
            if(obj!=null)set.add(obj);
            else System.out.println("Warn: obj==null");
        }
        return set.iterator();
    }
*/
    /**
 * Sort sermantic objects.
 * 
 * @param it the it
 * @return the iterator
 */
public static Iterator sortSermanticObjects(Iterator it) {
        return sortSermanticObjectsSet(it).iterator();
    }

    /**
     * Sort sermantic objects set.
     * 
     * @param it the it
     * @return the sets the
     */
    public static Set sortSermanticObjectsSet(Iterator it) {
        TreeSet set = new TreeSet(new SWBComparator());

        while (it.hasNext()) {
            Object obj = it.next();

            if (obj != null) {
                set.add(obj);
            }
        }

        return set;
    }

    /**
     * Sort sermantic properties.
     * 
     * @param it the it
     * @return the iterator
     */
    public static Iterator<SemanticProperty> sortSermanticProperties(Iterator<SemanticProperty> it) {
        TreeSet set = new TreeSet(new Comparator<SemanticProperty>() {
            public int compare(SemanticProperty obj1, SemanticProperty obj2) {
                return obj1.getName().compareTo(obj2.getName());
            }
        });

        while (it.hasNext()) {
            set.add(it.next());
        }

        return set.iterator();
    }

    /**
     * Sort sortable object.
     * 
     * @param it the it
     * @return the iterator
     */
    public static Iterator sortSortableObject(Iterator it) {
        return sortSortableObjectSet(it).iterator();
    }

    /**
     * Sort sortable object set.
     * 
     * @param it the it
     * @return the sets the
     */
    public static Set sortSortableObjectSet(Iterator it) {
        TreeSet set = new TreeSet(new Comparator() {
            SWBComparator com = new SWBComparator();
            public int compare(Object o1, Object o2) {
                return com.compareSortable(o1, o2);
            }
        });

        while (it.hasNext()) {
            set.add(it.next());
        }

        return set;
    }

    /**
     * Sort by display name.
     * 
     * @param it the it
     * @param lang the lang
     * @return the iterator
     */
    public static Iterator sortByDisplayName(Iterator it, String lang) {
        return sortByDisplayNameSet(it, lang).iterator();
    }

    /**
     * Sort by display name set.
     * 
     * @param it the it
     * @param lang the lang
     * @return the sets the
     */
    public static Set sortByDisplayNameSet(Iterator it, String lang) {
        TreeSet set = new TreeSet(new SWBComparator(lang));

        while (it.hasNext()) {
            set.add(it.next());
        }

        return set;
    }

    /**
     * Sort by created.
     * 
     * @param it the it
     * @return the iterator
     */
    public static Iterator sortByCreated(Iterator it) {
        return sortByCreated(it, true);
    }

    /**
     * Sort by created set.
     * 
     * @param it the it
     * @return the sets the
     */
    public static Set sortByCreatedSet(Iterator it) {
        return sortByCreatedSet(it, true);
    }

    /**
     * Sort by created.
     * 
     * @param it the it
     * @param ascendente the ascendente
     * @return the iterator
     */
    public static Iterator sortByCreated(Iterator it, boolean ascendente) {
        return sortByCreatedSet(it, ascendente).iterator();
    }

    /**
     * Sort by created set.
     * 
     * @param it the it
     * @param ascendente the ascendente
     * @return the sets the
     */
    public static Set sortByCreatedSet(Iterator it, boolean ascendente) {
        TreeSet set = null;

        if (ascendente) {
            set = new TreeSet(new Comparator() {
                public int compare(Object o1, Object o2) {
                    Date d1;
                    Date d2;
                    if(o1 instanceof SemanticObject)
                    {
                        d1 = ((SemanticObject)o1).getDateProperty(Traceable.swb_created);
                        d2 = ((SemanticObject)o2).getDateProperty(Traceable.swb_created);
                    }else
                    {
                        d1 = ((Traceable)o1).getCreated();
                        d2 = ((Traceable)o2).getCreated();
                    }

                    int ret = d1.after(d2)? 1 : -1;
                    return ret;
                }
            });
        } else {
            set = new TreeSet(new Comparator() {
                public int compare(Object o1, Object o2) {
                    Date d1;
                    Date d2;
                    if(o1 instanceof SemanticObject)
                    {
                        d1 = ((SemanticObject)o1).getDateProperty(Traceable.swb_created);
                        d2 = ((SemanticObject)o2).getDateProperty(Traceable.swb_created);
                    }else
                    {
                        d1 = ((Traceable)o1).getCreated();
                        d2 = ((Traceable)o2).getCreated();
                    }

                    int ret = d1.after(d2)? -1 : 1;
                    return ret;
                }
            });
        }

        while (it.hasNext()) {
            set.add(it.next());
        }

        return set;
    }


    /**
     * Sort by created set.
     *
     * @param it the it
     * @return the sets the
     */
    public static Set sortByLastUpdateSet(Iterator it) {
        return sortByCreatedSet(it, true);
    }


    /**
     * Sort by created set.
     *
     * @param it the it
     * @param ascendente the ascendente
     * @return the sets the
     */
    public static Set sortByLastUpdateSet(Iterator it, boolean ascendente) {
        TreeSet set = null;

        if (ascendente) {
            set = new TreeSet(new Comparator() {
                public int compare(Object o1, Object o2) {
                    if(o1 instanceof Traceable && o2 instanceof Traceable)
                    {
                        Traceable ob1 = (Traceable) (o1);
                        Traceable ob2 = (Traceable) (o2);
                        int       ret = ob1.getUpdated().after(ob2.getUpdated())
                                        ? 1
                                        : -1;

                        return ret;
                    }
                    return 0;

                }
            });
        } else {
            set = new TreeSet(new Comparator() {
                public int compare(Object o1, Object o2) {
                    Traceable ob1 = (Traceable) (o1);
                    Traceable ob2 = (Traceable) (o2);
                    int       ret = ob1.getUpdated().after(ob2.getUpdated())
                                    ? -1
                                    : 1;

                    return ret;
                }
            });
        }

        while (it.hasNext()) {
            set.add(it.next());
        }

        return set;
    }


    /**
     * Compare sortable.
     * 
     * @param o1 the o1
     * @param o2 the o2
     * @return the int
     */
    public int compareSortable(Object o1, Object o2) {
        int ret = 1;
        int v1  = 999999999;
        int v2  = 999999999;

        if (o1 instanceof Sortable) {
            if (o1 != null) {
                v1 = ((Sortable) o1).getIndex();
            }

            if (o2 != null) {
                v2 = ((Sortable) o2).getIndex();
            }
        } else if (o1 instanceof SemanticObject) {
            String t1 = ((SemanticObject) o1).getProperty(Sortable.swb_index);
            String t2 = ((SemanticObject) o2).getProperty(Sortable.swb_index);

            if (t1 != null) {
                v1 = Integer.parseInt(t1);
            }

            if (t2 != null) {
                v2 = Integer.parseInt(t2);
            }
        }

        ret = (v1 < v2)
              ? -1
              : 1;

        return ret;
    }
}
