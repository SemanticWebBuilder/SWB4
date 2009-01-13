/*
 * INFOTEC WebBuilder es una herramienta para el desarrollo de portales de conocimiento, colaboraci�n e integraci�n para Internet,
 * la cual, es una creaci�n original del Fondo de Informaci�n y Documentaci�n para la Industria INFOTEC, misma que se encuentra
 * debidamente registrada ante el Registro P�blico del Derecho de Autor de los Estados Unidos Mexicanos con el
 * No. 03-2002-052312015400-14, para la versi�n 1; No. 03-2003-012112473900 para la versi�n 2, y No. 03-2006-012012004000-01
 * para la versi�n 3, respectivamente.
 *
 * INFOTEC pone a su disposici�n la herramienta INFOTEC WebBuilder a trav�s de su licenciamiento abierto al p�blico (�open source�),
 * en virtud del cual, usted podr� usarlo en las mismas condiciones con que INFOTEC lo ha dise�ado y puesto a su disposici�n;
 * aprender de �l; distribuirlo a terceros; acceder a su c�digo fuente y modificarlo, y combinarlo o enlazarlo con otro software,
 * todo ello de conformidad con los t�rminos y condiciones de la LICENCIA ABIERTA AL P�BLICO que otorga INFOTEC para la utilizaci�n
 * de INFOTEC WebBuilder 3.2.
 *
 * INFOTEC no otorga garant�a sobre INFOTEC WebBuilder, de ninguna especie y naturaleza, ni impl�cita ni expl�cita,
 * siendo usted completamente responsable de la utilizaci�n que le d� y asumiendo la totalidad de los riesgos que puedan derivar
 * de la misma.
 *
 * Si usted tiene cualquier duda o comentario sobre INFOTEC WebBuilder, INFOTEC pone a su disposici�n la siguiente
 * direcci�n electr�nica:
 *
 *                                          http://www.webbuilder.org.mx
 */

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
