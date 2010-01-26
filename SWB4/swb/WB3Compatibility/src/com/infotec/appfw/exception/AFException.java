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


/*
 * AFException.java
 *
 * Created on 30 de mayo de 2002, 11:06 AM
 */

package com.infotec.appfw.exception;

import java.io.*;
import org.semanticwb.SWBException;

/**
 * Infotec Application Framwork Exception
 * @author Javier Solis Gonzalez
 * @version 1.1
 */
public class AFException extends SWBException
{

    private java.util.Date timeerror;
    private String methodname;
    private Exception e = null;;

    /**
     *
     **/

    public AFException(String msg, String method)
    {
        super(msg);
        //AFUtils.log(this,msg+":"+method);
        timeerror = new java.util.Date();
        methodname = method;
    }

    public AFException(SWBException e)
    {
        super(e.getMessage());
        this.e = e;
        //AFUtils.log(this,msg+":"+method);
        timeerror = new java.util.Date();
    }

    public AFException(String msg, String method, Exception e)
    {
        super(e.getMessage() + "-->" + msg);
        this.e = e;
        //AFUtils.log(this,msg+":"+method);
        timeerror = new java.util.Date();
        methodname = method;
    }

    public java.util.Date getTimeError()
    {
        return timeerror;
    }

    public String getMethodName()
    {
        return methodname;
    }

    public StackTraceElement[] getStackTrace()
    {
        if (e != null)
            return e.getStackTrace();
        else
            return super.getStackTrace();
    }

    public void printStackTrace()
    {
        if (e != null)
            e.printStackTrace();
        else
            super.printStackTrace();
    }

    public void printStackTrace(PrintStream s)
    {
        if (e != null)
            e.printStackTrace(s);
        else
            super.printStackTrace(s);
    }

    public void printStackTrace(PrintWriter s)
    {
        if (e != null)
            e.printStackTrace(s);
        else
            super.printStackTrace(s);
    }

}


