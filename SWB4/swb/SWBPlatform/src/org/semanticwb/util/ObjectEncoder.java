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

public class ObjectEncoder
{
    private String name=null;
    private ArrayList list=new ArrayList();
    
    /** Creates a new instance of ObjectEncoder */
    public ObjectEncoder(String name)
    {
        this.name=name;
    }
    
    public void addInt(int val)
    {
        list.add(""+val);
    }
    
    public void addString(String val)
    {
        list.add(replaceStringChars(val));
    }
    
    public void addLong(long val)
    {
        list.add(""+val);
    }
    
    public void addTimestamp(java.sql.Timestamp ts)
    {
        if(ts==null)list.add("_NULL_"); 
        else list.add(""+ts);
    }
   
    public String replaceStringChars(String str)
    {
        if(str==null)return "_NULL_"; 
        StringBuffer ret=new StringBuffer();
        for(int x=0;x<str.length();x++)
        {
            char ch=str.charAt(x);
            switch(ch)
            {
                case '"':
                    ret.append("\\\"");
                    break;
                case '\r':
                    ret.append("\\r");
                    break;
                case '\n':
                    ret.append("\\n");
                    break;
                default:
                    ret.append(ch);
                    break;
            }
            
        }
        return ret.toString();
    }
    
    @Override
    public String toString()
    {
        StringBuffer ret=new StringBuffer();
        ret.append(name+"(");
        Iterator it=list.iterator();
        while(it.hasNext())
        {
            ret.append("\""+it.next()+"\"");
            if(it.hasNext())ret.append(",");
        }
        ret.append(");");
        return ret.toString();
    }    
}
