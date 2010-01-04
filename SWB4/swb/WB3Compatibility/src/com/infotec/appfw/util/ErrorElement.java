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


/*
 * ErrorElement.java
 *
 * Created on 9 de febrero de 2005, 06:36 PM
 */

package com.infotec.appfw.util;

/**
 * Objeto: Representa y contiene una exception junto con un identificador y la
 * fecha en la que se genero.
 * @author Javier Solis Gonzalez
 */
public class ErrorElement
{
    static long counter;

    long id=0;
    String msg=null;
    java.util.Date date;
    Throwable throwable;
    
    /** Creates a new instance of ErrorElement */
    public ErrorElement(Throwable e, String msg)
    {
        id=getCounter();
        date=new java.util.Date();
        throwable=e;
        this.msg=msg;
    }
    
    public static synchronized long getCounter()
    {
        return counter++;
    }
    
    /**
     * Getter for property id.
     * @return Value of property id.
     */
    public long getId()
    {
        return id;
    }
    
    
    /**
     * Getter for property date.
     * @return Value of property date.
     */
    public java.util.Date getDate()
    {
        return date;
    }
    
    /**
     * Getter for property throwable.
     * @return Value of property throwable.
     */
    public java.lang.Throwable getThrowable()
    {
        return throwable;
    }    
    
    public String getMessage()
    {
        if(msg!=null)return msg;
        return throwable.toString();
    }
    
    public String getStackTrace()
    {
        return printThrowable(throwable);
    }
    
    private String printThrowable(Throwable th)
    {
        StringBuffer bug=new StringBuffer();
        if(th!=null)
        {
            bug.append(date + ": " + th.getMessage()+"\n");
            StackTraceElement elements[] = th.getStackTrace();
            bug.append("// " + th +"\n");
            for (int x = 0; x < elements.length; x++)
            {
                bug.append("// " + elements[x]+"\n");
            }
            
            Throwable rth=th.getCause();
            if(rth!=null && rth!=th)
            {
                bug.append("\nRoot Cause:\n\n");
                bug.append(printThrowable(rth));
            }
        }
        return bug.toString();
    }
   
}
