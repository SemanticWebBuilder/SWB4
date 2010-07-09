/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.process.cpanel;

import java.util.*;
import org.semanticwb.process.model.*;
import org.semanticwb.model.*;
/**
 *
 * @author haydee.vertti
 */
public class ComparableProperty {
    private String name;
    private String URI;
    private int orderOnTask;

    public ComparableProperty(){
    }

    public ComparableProperty(String name)
    {
        this.name = name;
        this.URI = "";
        this.orderOnTask = 0;
    }

    public ComparableProperty(String name, String URI)
    {
        this.name = name;
        this.URI = URI;
        this.orderOnTask = 0;
    }

    public ComparableProperty(String name, int order)
    {
        this.name = name;
        this.URI = "";
        this.orderOnTask = order;
    }


    public ComparableProperty(String name, String URI, int order)
    {
        this.name = name;
        this.URI = URI;
        this.orderOnTask = order;
    }

    public String getName(){
        return this.name;
    }

    public String getURI(){
        return this.URI;
    }

    public int getOrder(){
        return this.orderOnTask;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setURI(String URI){
        this.URI = URI;
    }

    public void setOrder(int order){
        this.orderOnTask = order;
    }

        /**
        * Ordena un vector de acuerdo al valor de la propiedad  order
        * proporcionado por el usuario
        *
        * @param            Vector de objetos tipo ComparableProperty
        * @see
        */
        public static void sortComparableProperty(Vector v){
            try {
                ComparablePropertyOrderComparator comparator =
                        new ComparablePropertyOrderComparator();
                Collections.sort(v,comparator);
            } catch(Exception e){
                System.out.println("Error en ComparableProperty.sortComparableProperty:" +
                        e.getMessage());
                //log.error("Error en ComparableProperty.sortComparableProperty", e);
            }
        }

        /**
        * Implementación de Comparator para objetos de tipo ComparableProperty
        */
        static class ComparablePropertyOrderComparator implements Comparator{
            public int compare(Object obj1, Object obj2)
            {
                int result = 0;
                ComparableProperty pProp1 = (ComparableProperty)obj1;
                ComparableProperty pProp2 = (ComparableProperty)obj2;
                Integer order1 = pProp1.getOrder();
                Integer order2 = pProp2.getOrder();
                String strName1 = pProp1.getName();
                String strName2 = pProp2.getName();
                result = order1.compareTo(order2);
                if(result == 0){
                    result = strName1.compareTo(strName2);
                }
                return result;
            }
        }
}
