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

import java.io.InputStream;
import java.util.*;

// TODO: Auto-generated Javadoc
/**
 * The Class SWBBridgeResponse.
 * 
 * @author  Administrador
 */
public class SWBBridgeResponse
{

    /** The response code. */
    private int responseCode = 0;
    
    /** The response message. */
    private String responseMessage = null;
    
    /** The error message. */
    private String errorMessage = null;
    
    /** The content type. */
    private String contentType = null;
    
    /** The headerkey. */
    private ArrayList headerkey = new ArrayList();
    
    /** The headervalue. */
    private ArrayList headervalue = new ArrayList();
    
    private InputStream inputStream=null;

    /**
     * Creates a new instance of WBBridgeResponse.
     */
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

    /**
     * Gets the header field key.
     * 
     * @param pos the pos
     * @return the header field key
     */
    public String getHeaderFieldKey(int pos)
    {
        if (pos > headerkey.size()) return null;
        return (String) headerkey.get(pos - 1);
    }

    /**
     * Gets the header field.
     * 
     * @param key the key
     * @return the header field
     */
    public String getHeaderField(String key)
    {
        int pos = headerkey.indexOf(key);
        if (pos < 0) return null;
        return (String) headervalue.get(pos);
    }

    /**
     * Gets the header field.
     * 
     * @param pos the pos
     * @return the header field
     */
    public String getHeaderField(int pos)
    {
        if (pos > headervalue.size()) return null;
        return (String) headervalue.get(pos - 1);
    }

    /**
     * Gets the header size.
     * 
     * @return the header size
     */
    public int getHeaderSize()
    {
        return headerkey.size();
    }

    /**
     * Adds the header.
     * 
     * @param key the key
     * @param value the value
     */
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

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }
    
    
}
