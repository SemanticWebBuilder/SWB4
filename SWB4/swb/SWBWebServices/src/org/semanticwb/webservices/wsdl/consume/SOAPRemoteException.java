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
package org.semanticwb.webservices.wsdl.consume;

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
