/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.wsdl.consume;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 *
 * @author victor.lorenzana
 */
public class RemoteException extends Exception {

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
    public static RemoteException createRemoteException(Document doc)
    {
        RemoteException remoteException=null;
        NodeList faults=doc.getElementsByTagNameNS(SOAP_ENVELOPE_NAMESPACE, "Fault");
        if(faults.getLength()>0)
        {
            Element fault=(Element)faults.item(0);
            if(fault.getElementsByTagNameNS(SOAP_ENVELOPE_NAMESPACE, "faultstring").getLength()>0)
            {
                Element faultstring=(Element)fault.getElementsByTagNameNS(SOAP_ENVELOPE_NAMESPACE, "faultstring").item(0);
                String string=faultstring.getTextContent();
                remoteException=new RemoteException(string);
            }
            if(fault.getElementsByTagName("faultstring").getLength()>0)
            {
                Element faultstring=(Element)fault.getElementsByTagName("faultstring").item(0);
                String string=faultstring.getTextContent();
                remoteException=new RemoteException(string);
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
    public RemoteException(String message)
    {
        super(message);
    }
    public RemoteException(Throwable e)
    {
        super(e);
    }
    public RemoteException(String msg,Throwable e)
    {
        super(msg,e);
    }
    
}
