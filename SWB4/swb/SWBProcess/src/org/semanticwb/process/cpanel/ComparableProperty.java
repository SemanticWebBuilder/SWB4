/*
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
package org.semanticwb.process.cpanel;

import java.util.*;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
/**
 *
 * @author haydee.vertti
 */
public class ComparableProperty {
    private String name;
    private String URI;
    private int orderOnTask;
    private static Logger log = SWBUtils.getLogger(ComparableProperty.class);

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
                log.error("Error en ComparableProperty.sortComparableProperty", e);
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
