/*
 * INFOTEC WebBuilder es una herramienta para el desarrollo de portales de conocimiento, colaboración e integración para Internet,
 * la cual, es una creación original del Fondo de Información y Documentación para la Industria INFOTEC, misma que se encuentra
 * debidamente registrada ante el Registro Público del Derecho de Autor de los Estados Unidos Mexicanos con el
 * No. 03-2002-052312015400-14, para la versión 1; No. 03-2003-012112473900 para la versión 2, y No. 03-2006-012012004000-01
 * para la versión 3, respectivamente.
 *
 * INFOTEC pone a su disposición la herramienta INFOTEC WebBuilder a través de su licenciamiento abierto al público (‘open source’),
 * en virtud del cual, usted podrá usarlo en las mismas condiciones con que INFOTEC lo ha diseñado y puesto a su disposición;
 * aprender de él; distribuirlo a terceros; acceder a su código fuente y modificarlo, y combinarlo o enlazarlo con otro software,
 * todo ello de conformidad con los términos y condiciones de la LICENCIA ABIERTA AL PÚBLICO que otorga INFOTEC para la utilización
 * de INFOTEC WebBuilder 3.2.
 *
 * INFOTEC no otorga garantía sobre INFOTEC WebBuilder, de ninguna especie y naturaleza, ni implícita ni explícita,
 * siendo usted completamente responsable de la utilización que le dé y asumiendo la totalidad de los riesgos que puedan derivar
 * de la misma.
 *
 * Si usted tiene cualquier duda o comentario sobre INFOTEC WebBuilder, INFOTEC pone a su disposición la siguiente
 * dirección electrónica:
 *
 *                                          http://www.webbuilder.org.mx
 */


package com.infotec.appfw.util;

import java.io.*;
import java.lang.*;
import java.util.*;

public class QuickSort
{

    public static void quicksort(int[] a)
    {
        quicksort(a, 0, a.length - 1);
    }

    public static void quicksort(String[] a)
    {
        quicksort(a, 0, a.length - 1);
    }

    public static Vector quicksort(Hashtable a)
    {
        Vector n = new Vector();
        String[] b = new String[a.size()];
        int x = 0;
        Enumeration en = a.keys();
        while (en.hasMoreElements())
        {
            b[x] = (String) en.nextElement();
            x++;
        }
        quicksort(b);
        for (x = 0; x < b.length; x++)
        {
            System.out.print(b[x]);
            n.add(a.get(b[x]));
        }
        return n;
    }

    public static void quicksort(String[] a, int low, int high)
    {
        if (low >= high) return;
        int left = low;
        int right = high;
        String pivot = a[(low + high) / 2];
        do
        {
            while (a[left].compareTo(pivot) < 0) left++;
            while (a[right].compareTo(pivot) > 0) right--;
            if (left <= right)
            {
                String temp = a[right];
                a[right--] = a[left];
                a[left++] = temp;
            }
        } while (left <= right);
        if (low < right) quicksort(a, low, right);
        if (high > left) quicksort(a, left, high);
    }

    public static void quicksort(int[] a, int low, int high)
    {
        if (low >= high) return;
        int left = low;
        int right = high;
        int pivot = a[(low + high) / 2];
        do
        {
            while (a[left] < pivot) left++;
            while (a[right] > pivot) right--;
            if (left <= right)
            {
                int temp = a[right];
                a[right--] = a[left];
                a[left++] = temp;
            }
        } while (left <= right);
        if (low < right) quicksort(a, low, right);
        if (high > left) quicksort(a, left, high);
    }

    public static void main(String argv[])
    {
        Hashtable obj = new Hashtable();
        Vector obj2;
        int[] a = {-20, 0, 1, 10, 1000, -10, 10, -45, 5, 6};
        String[] s = {"javier", "ivonne", "search", "rene", "adrian", "joel", "rama", "ere", "eduardo", "jimena"};
        obj.put("javier", "javier");
        obj.put("ivonne", "ivonne");
        obj.put("search", "search");
        obj.put("rene", "rene");
        obj.put("adrian", "adrian");
        obj.put("joel", "joel");
        obj.put("rama", "rama");
        obj.put("ere", "ere");
        obj.put("eduardo", "eduardo");
        obj.put("jimena", "jimena");
        try
        {
            for (int k = 0; k < 10; k++)
            {
                System.out.print(a[k] + ",");
            }
            System.out.println();
            quicksort(a);
            for (int k = 0; k < 10; k++)
            {
                System.out.print(a[k] + ",");
            }
            System.out.println();

            for (int k = 0; k < 10; k++)
            {
                System.out.print(s[k] + ",");
            }
            System.out.println();
            quicksort(s);
            for (int k = 0; k < 10; k++)
            {
                System.out.print(s[k] + ",");
            }
            System.out.println();

            Enumeration en = obj.elements();
            while (en.hasMoreElements())
            {
                System.out.print(en.nextElement() + ",");
            }
            System.out.println();
            obj2 = quicksort(obj);
            en = obj2.elements();
            while (en.hasMoreElements())
            {
                System.out.print(en.nextElement() + ",");
            }
            System.out.println();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}

