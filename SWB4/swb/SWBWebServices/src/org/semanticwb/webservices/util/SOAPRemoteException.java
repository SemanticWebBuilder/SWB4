/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.webservices.util;

import java.rmi.RemoteException;
import org.semanticwb.webservices.ServiceException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 *
 * @author victor.lorenzana
 */
public class SOAPRemoteException extends ServiceException
{
    private String code;
    private String detail;
    private static final String SOAP_ENVELOPE_NAMESPACE="http://schemas.xmlsoap.org/soap/envelope/";
    public static boolean isError(Document doc)
    {        
        if(doc.getElementsByTagNameNS(SOAP_ENVELOPE_NAMESPACE, "Fault").getLength()>0)
        {
            return true;
        }
        return false;
    }
    public void setCode(String code)
    {
        this.code=code;
    }
    public String getCode()
    {
        return code;
    }
    public void setDetail(String detail)
    {
        this.detail=detail;
    }
    public String getDetail()
    {
        return detail;
    }
    public static SOAPRemoteException createRemoteException(Document doc)
    {
        SOAPRemoteException remoteException=null;
        NodeList faults=doc.getElementsByTagNameNS(SOAP_ENVELOPE_NAMESPACE, "Fault");
        if(faults.getLength()>0)
        {
            Element fault=(Element)faults.item(0);
            if(fault.getElementsByTagNameNS(SOAP_ENVELOPE_NAMESPACE, "faultstring").getLength()>0)
            {
                Element faultstring=(Element)fault.getElementsByTagNameNS(SOAP_ENVELOPE_NAMESPACE, "faultstring").item(0);
                String string=faultstring.getTextContent();
                remoteException=new SOAPRemoteException(string);
            }
            if(fault.getElementsByTagName("faultstring").getLength()>0)
            {
                Element faultstring=(Element)fault.getElementsByTagName("faultstring").item(0);
                String string=faultstring.getTextContent();
                remoteException=new SOAPRemoteException(string);
            }
            if(fault.getElementsByTagNameNS(SOAP_ENVELOPE_NAMESPACE, "detail").getLength()>0)
            {
                Element faultstring=(Element)fault.getElementsByTagNameNS(SOAP_ENVELOPE_NAMESPACE, "detail").item(0);
                String string=faultstring.getTextContent();
                if(remoteException!=null)
                {
                   remoteException.setDetail(string);
                }
            }
            if(fault.getElementsByTagName("detail").getLength()>0)
            {
                Element faultstring=(Element)fault.getElementsByTagName("detail").item(0);
                String string=faultstring.getTextContent();
                if(remoteException!=null)
                {
                   remoteException.setDetail(string);
                }
            }
            if(fault.getElementsByTagNameNS(SOAP_ENVELOPE_NAMESPACE, "faultcode").getLength()>0)
            {
                Element faultCode=(Element)fault.getElementsByTagNameNS(SOAP_ENVELOPE_NAMESPACE, "faultcode").item(0);
                String code=faultCode.getTextContent();
                if(remoteException!=null)
                {
                   remoteException.setCode(code); 
                }

            }
            if(fault.getElementsByTagName("faultcode").getLength()>0)
            {
                Element faultCode=(Element)fault.getElementsByTagName("faultcode").item(0);
                String code=faultCode.getTextContent();
                if(remoteException!=null)
                {
                   remoteException.setCode(code);
                }

            }
        }
        return remoteException;
    }
    public SOAPRemoteException(String message)
    {
        super(message);
    }
    public SOAPRemoteException(Throwable e)
    {
        super(e);
    }
    public SOAPRemoteException(String msg,Throwable e)
    {
        super(msg,e);
    }
}
