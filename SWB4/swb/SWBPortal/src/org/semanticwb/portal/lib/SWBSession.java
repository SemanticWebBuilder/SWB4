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
 * WBSession.java
 *
 * Created on 5 de agosto de 2003, 17:28
 */

package org.semanticwb.portal.lib;

/**
 * Clase que implementa HttpSession, la cual se utiliza para remplazar la
 * Sesi�n por defecto para se enviado al recurso.
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
