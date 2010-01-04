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


