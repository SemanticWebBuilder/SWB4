
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
