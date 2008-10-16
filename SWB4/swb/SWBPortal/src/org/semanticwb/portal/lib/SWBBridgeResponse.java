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
 * WBBridgeResponse.java
 *
 * Created on 19 de septiembre de 2003, 05:16 AM
 */

package org.semanticwb.portal.lib;

import java.util.*;

/**
 *
 * @author  Administrador
 */
public class SWBBridgeResponse
{

    private int responseCode = 0;
    private String responseMessage = null;
    private String errorMessage = null;
    private String contentType = null;
    private ArrayList headerkey = new ArrayList();
    private ArrayList headervalue = new ArrayList();


    /** Creates a new instance of WBBridgeResponse */
    public SWBBridgeResponse()
    {
    }

    /** Getter for property contentType.
     * @return Value of property contentType.
     */
    public java.lang.String getContentType()
    {
        return contentType;
    }

    /** Setter for property contentType.
     * @param contentType New value of property contentType.
     */
    public void setContentType(java.lang.String contentType)
    {
        this.contentType = contentType;
    }

    /** Getter for property responseCode.
     * @return Value of property responseCode.
     */
    public int getResponseCode()
    {
        return responseCode;
    }

    /** Setter for property responseCode.
     * @param responseCode New value of property responseCode.
     */
    public void setResponseCode(int responseCode)
    {
        this.responseCode = responseCode;
    }

    /** Getter for property responseMessage.
     * @return Value of property responseMessage.
     */
    public java.lang.String getResponseMessage()
    {
        return responseMessage;
    }

    /** Setter for property responseMessage.
     * @param responseMessage New value of property responseMessage.
     */
    public void setResponseMessage(java.lang.String responseMessage)
    {
        this.responseMessage = responseMessage;
    }

    public String getHeaderFieldKey(int pos)
    {
        if (pos > headerkey.size()) return null;
        return (String) headerkey.get(pos - 1);
    }

    public String getHeaderField(String key)
    {
        int pos = headerkey.indexOf(key);
        if (pos < 0) return null;
        return (String) headervalue.get(pos);
    }

    public String getHeaderField(int pos)
    {
        if (pos > headervalue.size()) return null;
        return (String) headervalue.get(pos - 1);
    }

    public int getHeaderSize()
    {
        return headerkey.size();
    }

    public void addHeader(String key, String value)
    {
        headerkey.add(key);
        headervalue.add(value);
    }

    /**
     * Getter for property errorMessage.
     * @return Value of property errorMessage.
     */
    public java.lang.String getErrorMessage()
    {
        return errorMessage;
    }
    
    /**
     * Setter for property errorMessage.
     * @param errorMessage New value of property errorMessage.
     */
    public void setErrorMessage(java.lang.String errorMessage)
    {
        this.errorMessage = errorMessage;
    }
    
}
