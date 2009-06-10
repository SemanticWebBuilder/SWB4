/*
 * WBSession.java
 *
 * Created on 5 de agosto de 2003, 17:28
 */

package org.semanticwb.portal.lib;

/**
 * Clase que implementa HttpSession, la cual se utiliza para remplazar la
 * Sesión por defecto para se enviado al recurso.
 * @author  Administrador
 */
public class SWBSession implements javax.servlet.http.HttpSession
{

    /** Creates a new instance of WBSession */
    public SWBSession()
    {
    }

    public Object getAttribute(String str)
    {
        return null;
    }

    public java.util.Enumeration getAttributeNames()
    {
        return (new java.util.Vector()).elements();
    }

    public long getCreationTime()
    {
        return new java.util.Date().getTime();
    }

    public String getId()
    {
        return "";
    }

    public long getLastAccessedTime()
    {
        return 0;
    }

    public int getMaxInactiveInterval()
    {
        return 30 * 1000;
    }

    public javax.servlet.ServletContext getServletContext()
    {
        return null;
    }

    public javax.servlet.http.HttpSessionContext getSessionContext()
    {
        return null;
    }

    public Object getValue(String str)
    {
        return null;
    }

    public String[] getValueNames()
    {
        return null;
    }

    public void invalidate()
    {
    }

    public boolean isNew()
    {
        return true;
    }

    public void putValue(String str, Object obj)
    {
    }

    public void removeAttribute(String str)
    {
    }

    public void removeValue(String str)
    {
    }

    public void setAttribute(String str, Object obj)
    {
    }

    public void setMaxInactiveInterval(int param)
    {
    }

}
