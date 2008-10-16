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


package org.semanticwb.portal.util;



/** Esta clase se encarga de administrar los datos de un usuario final que entra en sesion
 * usando una cookie.
 *
 * This class is in charge to manage final user data using a cookie.
 * @author : Jorge Alberto Jim�nez
 */
public class SWBCookie
{
    private String name;
    private String value;
    private String expires;
    private String path;
    private String host;
    private long resid;

    public SWBCookie()
    {
        name = null;
        value = null;
        expires = null;
        path = "/";
        host = null;
        resid = 0;
    }

    /**
     * @param name
     * @param value
     * @param expires
     * @param path
     * @param host
     * @param resid
     */    
    public SWBCookie(String name, String value, String expires, String path, String host, long resid)
    {
        this.name = name;
        this.value = value;
        this.expires = expires;
        this.path = path;
        this.host = host;
        this.resid = resid;
    }


    /**
     * @return
     */    
    public String getName()
    {
        return name;
    }

    /**
     * @return
     */    
    public String getValue()
    {
        return value;
    }

    /**
     * @return
     */    
    public String getExpires()
    {
        return expires;
    }

    /**
     * @return
     */    
    public String getPath()
    {
        return path;
    }

    /**
     * @return
     */    
    public String getHost()
    {
        return host;
    }

    /**
     * @return
     */    
    public long getResID()
    {
        return resid;
    }

    /**
     * @param name
     */    
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * @param value
     */    
    public void setValue(String value)
    {
        this.value = value;
    }

    /**
     * @param expires
     */    
    public void setExpires(String expires)
    {
        this.expires = expires;
    }

    /**
     * @param path
     */    
    public void setPath(String path)
    {
        this.path = path;
    }

    /**
     * @param host
     */    
    public void setHost(String host)
    {
        this.host = host;
    }

    /**
     * @param resid
     */    
    public void setResID(long resid)
    {
        this.resid = resid;
    }
}