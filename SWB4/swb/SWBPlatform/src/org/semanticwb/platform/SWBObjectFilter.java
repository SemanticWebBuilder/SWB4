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
package org.semanticwb.platform;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.StringTokenizer;

// TODO: Auto-generated Javadoc
/**
 * The Class SWBObjectFilter.
 * 
 * @author javier.solis
 */
public class SWBObjectFilter
{
    
    /**
     * Filter.
     * 
     * @param objects the objects
     * @param filter the filter
     * @return the iterator
     */
    public static Iterator<SemanticObject> filter(Iterator<SemanticObject>objects, String filter)
    {
        if(filter!=null)
        {
            ArrayList<SemanticObject> arr=new ArrayList();
            while(objects.hasNext())
            {
                SemanticObject obj=objects.next();
                if(filter(obj, filter))arr.add(obj);
            }
            return arr.iterator();
        }else
        {
            return objects;
        }
    }


    /**
     * Filter.
     * 
     * @param obj SemanticObject a filtrar
     * @param filter String con filtro
     * @return boolean si clumple o no con el filtro
     */
    public static boolean filter(SemanticObject obj, String filter)
    {
        if(filter==null)return true;
        boolean ret=false;
        StringTokenizer st=new StringTokenizer(filter,",;|&");
        while(st.hasMoreTokens())
        {
            String txt=st.nextToken();
            //System.out.println("txt:"+txt);
            StringTokenizer st2=new StringTokenizer(txt,"=><!");
            while(st2.hasMoreTokens())
            {
                String key=st2.nextToken();
                //System.out.println("key:"+key);
                if(st2.hasMoreTokens())
                {
                    String value=st2.nextToken();
                    String sep=txt.substring(key.length(),txt.length()-value.length());
                    //System.out.println("sep:"+sep);
                    //System.out.println("value:"+value);
                    SemanticClass cls=obj.getSemanticClass();
                    if(cls.hasProperty(key))
                    {
                        String val=obj.getProperty(cls.getProperty(key));
                        if((sep.equals("=") || sep.equals("==")) && value.equals(val))
                        {
                            ret=true;
                        }
                        if((sep.equals("!=") || sep.equals("<>")) && !value.equals(val))
                        {
                            ret=true;
                        }
                        //System.out.println("val:"+val+" "+"sep:"+sep+" value:"+value);
                    }
                }
            }
        }
        return ret;
    }


}
