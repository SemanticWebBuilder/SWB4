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
