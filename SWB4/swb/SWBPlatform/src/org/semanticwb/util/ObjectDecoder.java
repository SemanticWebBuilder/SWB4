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
 

package org.semanticwb.util;

import java.util.*;

import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;

public class ObjectDecoder {
    private static Logger log = SWBUtils.getLogger(ObjectDecoder.class);
    
    private String name=null;
    private ArrayList list=new ArrayList();    
    
    /** Creates a new instance of ObjectDecoder */
    public ObjectDecoder(String str)
    {
        StringBuffer aux=new StringBuffer();
        boolean isName=true;
        boolean isElement=false;
        for(int x=0;x<str.length();x++)
        {
            char ch=str.charAt(x);
            switch(ch)
            {
                case '(':
                    if(isName)
                    {
                        name=aux.toString();
                        isName=false;
                    }else
                    {
                        aux.append(ch);
                    }
                    break;
                case '"':
                    if(!isElement)
                    {
                        isElement=true;
                        aux=new StringBuffer();
                    }else if(isElement && str.charAt(x-1)!='\\')
                    {
                        isElement=false;
                        list.add(aux.toString());
                    }else
                    {
                        aux.append(ch);
                    }
                    break;
                default:
                    aux.append(ch);
                    break;
            }
       
        }
    }
    
    public String getName()
    {
        return name;
    }
    
    public String replaceStringChars(String str)
    {
        if(str.equals("_NULL_"))return null;
        StringBuffer ret=new StringBuffer();
        for(int x=0;x<str.length();x++)
        {
            char ch=str.charAt(x);
            switch(ch)
            {
                case '\\':
                    if(str.charAt(x+1)=='"')
                    {
                        ret.append("\"");
                    }else if(str.charAt(x+1)=='r')
                    {
                        ret.append("\r");
                    }else if(str.charAt(x+1)=='n')
                    {
                        ret.append("\n");
                    }
                    x++;
                    break;
                default:
                    ret.append(ch);
                    break;
            }
            
        }
        return ret.toString();
    }    
    
    public int getInt(int pos)
    {
        return Integer.parseInt((String)list.get(pos));
    }
    
    public String getString(int pos)
    {
        try
        {
            return replaceStringChars((String)list.get(pos));
        }catch(Exception e) { 
            log.error(e);
        }
        return null;
    }
    
    public long getLong(int pos)
    {
        return Long.parseLong((String)list.get(pos));
    }
    
    public java.sql.Timestamp getTimeStamp(int pos)
    {
        String aux=(String)list.get(pos);
        if("_NULL_".equals(aux))
        {
            return null;
        }else
        {
            return java.sql.Timestamp.valueOf(aux);
        }
    }
    
    public int getSize()
    {
        return list.size();
    }
}
