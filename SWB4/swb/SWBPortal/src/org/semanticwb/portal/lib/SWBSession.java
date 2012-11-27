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
package org.semanticwb.portal.lib;

// TODO: Auto-generated Javadoc
/**
 * Clase que implementa HttpSession, la cual se utiliza para remplazar la
 * Sesión por defecto para se enviado al recurso.
 * @author  Administrador
 */
public class SWBSession implements javax.servlet.http.HttpSession
{

    /**
     * Creates a new instance of WBSession.
     */
    public SWBSession()
    {
    }

    /* (non-Javadoc)
     * @see javax.servlet.http.HttpSession#getAttribute(java.lang.String)
     */
    /**
     * Gets the attribute.
     * 
     * @param str the str
     * @return the attribute
     */
    public Object getAttribute(String str)
    {
        return null;
    }

    /* (non-Javadoc)
     * @see javax.servlet.http.HttpSession#getAttributeNames()
     */
    /**
     * Gets the attribute names.
     * 
     * @return the attribute names
     */
    public java.util.Enumeration getAttributeNames()
    {
        return (new java.util.Vector()).elements();
    }

    /* (non-Javadoc)
     * @see javax.servlet.http.HttpSession#getCreationTime()
     */
    /**
     * Gets the creation time.
     * 
     * @return the creation time
     */
    public long getCreationTime()
    {
        return new java.util.Date().getTime();
    }

    /* (non-Javadoc)
     * @see javax.servlet.http.HttpSession#getId()
     */
    /**
     * Gets the id.
     * 
     * @return the id
     */
    public String getId()
    {
        return "";
    }

    /* (non-Javadoc)
     * @see javax.servlet.http.HttpSession#getLastAccessedTime()
     */
    /**
     * Gets the last accessed time.
     * 
     * @return the last accessed time
     */
    public long getLastAccessedTime()
    {
        return 0;
    }

    /* (non-Javadoc)
     * @see javax.servlet.http.HttpSession#getMaxInactiveInterval()
     */
    /**
     * Gets the max inactive interval.
     * 
     * @return the max inactive interval
     */
    public int getMaxInactiveInterval()
    {
        return 30 * 1000;
    }

    /* (non-Javadoc)
     * @see javax.servlet.http.HttpSession#getServletContext()
     */
    /**
     * Gets the servlet context.
     * 
     * @return the servlet context
     */
    public javax.servlet.ServletContext getServletContext()
    {
        return null;
    }

    /* (non-Javadoc)
     * @see javax.servlet.http.HttpSession#getSessionContext()
     */
    /**
     * Gets the session context.
     * 
     * @return the session context
     */
    public javax.servlet.http.HttpSessionContext getSessionContext()
    {
        return null;
    }

    /* (non-Javadoc)
     * @see javax.servlet.http.HttpSession#getValue(java.lang.String)
     */
    /**
     * Gets the value.
     * 
     * @param str the str
     * @return the value
     */
    public Object getValue(String str)
    {
        return null;
    }

    /* (non-Javadoc)
     * @see javax.servlet.http.HttpSession#getValueNames()
     */
    /**
     * Gets the value names.
     * 
     * @return the value names
     */
    public String[] getValueNames()
    {
        return null;
    }

    /* (non-Javadoc)
     * @see javax.servlet.http.HttpSession#invalidate()
     */
    /**
     * Invalidate.
     */
    public void invalidate()
    {
    }

    /* (non-Javadoc)
     * @see javax.servlet.http.HttpSession#isNew()
     */
    /**
     * Checks if is new.
     * 
     * @return true, if is new
     */
    public boolean isNew()
    {
        return true;
    }

    /* (non-Javadoc)
     * @see javax.servlet.http.HttpSession#putValue(java.lang.String, java.lang.Object)
     */
    /**
     * Put value.
     * 
     * @param str the str
     * @param obj the obj
     */
    public void putValue(String str, Object obj)
    {
    }

    /* (non-Javadoc)
     * @see javax.servlet.http.HttpSession#removeAttribute(java.lang.String)
     */
    /**
     * Removes the attribute.
     * 
     * @param str the str
     */
    public void removeAttribute(String str)
    {
    }

    /* (non-Javadoc)
     * @see javax.servlet.http.HttpSession#removeValue(java.lang.String)
     */
    /**
     * Removes the value.
     * 
     * @param str the str
     */
    public void removeValue(String str)
    {
    }

    /* (non-Javadoc)
     * @see javax.servlet.http.HttpSession#setAttribute(java.lang.String, java.lang.Object)
     */
    /**
     * Sets the attribute.
     * 
     * @param str the str
     * @param obj the obj
     */
    public void setAttribute(String str, Object obj)
    {
    }

    /* (non-Javadoc)
     * @see javax.servlet.http.HttpSession#setMaxInactiveInterval(int)
     */
    /**
     * Sets the max inactive interval.
     * 
     * @param param the new max inactive interval
     */
    public void setMaxInactiveInterval(int param)
    {
    }

}
