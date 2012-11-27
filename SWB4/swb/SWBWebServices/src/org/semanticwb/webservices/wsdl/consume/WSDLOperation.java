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

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.DOMOutputter;
import org.json.JSONException;
import org.json.JSONObject;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.webservices.Request;
import org.semanticwb.webservices.Operation;
import org.semanticwb.webservices.Response;
import org.semanticwb.webservices.ServiceException;
import org.semanticwb.webservices.ServiceInfo;
import org.semanticwb.webservices.util.XMLDocumentUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author victor.lorenzana
 */
public class WSDLOperation implements Operation
{

    private static final Logger log = SWBUtils.getLogger(WSDLOperation.class);
    private static final String SOAP_ENVELOPE_NAMESPACE = "http://schemas.xmlsoap.org/soap/envelope/";
    private static final String SOAP12_NAMESPACE = "http://schemas.xmlsoap.org/wsdl/soap12/";
    private static final String SOAP12_ENVELOPE_NAMESPACE = "http://www.w3.org/2001/12/soap-envelope";
    protected static final String TEXT_XML = "text/xml";
    private static final String CONTENT_TYPE = "Content-Type";
    private static final String SOAP_WSDL_ENVELOPE_NAMESPACE = "http://schemas.xmlsoap.org/wsdl/soap/";
    private static final String APPLICATION_XML = "application/xml";
    private final String operationname;
    private final String name;
    private final Element binding;
    private final Element portType;
    private final WSDLInput input;
    private final WSDLOutput output;
    private final Element operation;

    public WSDLOperation(Element operation, ServiceInfo service, Element portType, Element binding) throws ServiceException
    {
        this.operationname=(operation.getAttribute("name") == null ? "" : operation.getAttribute("name"));
        this.operation = operation;
        this.portType = portType;
        this.binding = binding;
        String _binding = binding.getAttribute("name").replace(':', '_');
        String port = portType.getAttribute("name");

        this.name = port + "_" + _binding + "_" + (operation.getAttribute("name") == null ? "" : operation.getAttribute("name"));
        NodeList _inputs = operation.getElementsByTagNameNS(operation.getNamespaceURI(), "input");
        WSDLInput _input = null;
        for (int i = 0; i < _inputs.getLength(); i++)
        {
            Element einput = (Element) _inputs.item(i);
            _input = new WSDLInput(einput, this, service);
        }
        this.input = _input;
        WSDLOutput _output = null;
        NodeList _outputs = operation.getElementsByTagNameNS(operation.getNamespaceURI(), "output");
        for (int i = 0; i < _outputs.getLength(); i++)
        {
            Element eoutput = (Element) _outputs.item(i);
            _output = new WSDLOutput(eoutput, this, service);

        }
        this.output = _output;
    }

    public String getPortType()
    {
        return portType.getAttribute("name");
    }

    public String getBinding()
    {
        return binding.getAttribute("name");
    }

    @Override
    public String getName()
    {
        return name;
    }

    @Override
    public Request getInput()
    {
        return input;
    }

    @Override
    public Response getOutput()
    {
        return output;
    }

    private Document serialize(JSONObject object) throws ServiceException
    {

        Document doc = SWBUtils.XML.getNewDocument();
        for (WSDLParameterDefinition parameter : input.getWSDLParameterDefinitions())
        {
            if (object.has(parameter.getName()))
            {
                try
                {
                    Object value=object.get(parameter.getName());
                    parameter.add(doc, value);                    
                }
                catch(JSONException e)
                {
                    throw new ServiceException(e);
                }
            }
            else
            {
                if(parameter.isRequired())
                {
                    throw new ServiceException("the parameter "+parameter.getName()+" is required");
                }
            }
        }
        return doc;

    }
    
    private Element createEmptyEnvelope(Document doc)
    {
        Element envelope = doc.createElementNS(SOAP_ENVELOPE_NAMESPACE, "Envelope");
        Element header = doc.createElementNS(SOAP_ENVELOPE_NAMESPACE, "Header");
        envelope.appendChild(header);
        Element body = doc.createElementNS(SOAP_ENVELOPE_NAMESPACE, "Body");
        envelope.appendChild(body);
        doc.appendChild(envelope);
        return body;
    }

    private Element createEmptyEnvelope12(Document doc)
    {
        Element envelope = doc.createElementNS(SOAP12_ENVELOPE_NAMESPACE, "Envelope");
        Element header = doc.createElementNS(SOAP12_ENVELOPE_NAMESPACE, "Header");
        envelope.appendChild(header);
        Element body = doc.createElementNS(SOAP12_ENVELOPE_NAMESPACE, "Body");
        envelope.appendChild(body);
        doc.appendChild(envelope);
        return body;
    }

    @Override
    public JSONObject execute(JSONObject object) throws ServiceException
    {
        Document body = serialize(object);
        
        if (isSoap())
        {
            Document request=SWBUtils.XML.getNewDocument();
            Element ebody=createEmptyEnvelope(request);
            Node node=ebody.getOwnerDocument().importNode(body.getDocumentElement(), true);
            ebody.appendChild(node);            
            String xml=SWBUtils.XML.domToXml(ebody.getOwnerDocument());            
            Document response = execute(request);
            if(SOAPRemoteException.isError(response))
            {
                throw SOAPRemoteException.createRemoteException(response);
            }
            xml=SWBUtils.XML.domToXml(response);
            return XMLDocumentUtil.toJSON(response);
        }
        else if (isSoap12())
        {
            Document request=SWBUtils.XML.getNewDocument();
            Element ebody=createEmptyEnvelope12(request);
            Node node=ebody.getOwnerDocument().importNode(body.getDocumentElement(), true);
            ebody.appendChild(node);            
            String xml=SWBUtils.XML.domToXml(ebody.getOwnerDocument());
            Document response = execute12(request);
            if(SOAPRemoteException.isError(response))
            {
                throw SOAPRemoteException.createRemoteException(response);
            }
            xml=SWBUtils.XML.domToXml(response);
            return XMLDocumentUtil.toJSON(response);
        }
        else
        {
            throw new ServiceException("The type of service is not supported");
        }


    }

    public URL getURL(Element binding)
    {
        String _bindingname = binding.getAttribute("name");
        Document doc = operation.getOwnerDocument();
        NodeList services = doc.getElementsByTagNameNS(operation.getNamespaceURI(), "service");
        for (int i = 0; i < services.getLength(); i++)
        {
            Element service = (Element) services.item(i);
            NodeList ports = service.getElementsByTagNameNS(operation.getNamespaceURI(), "port");
            for (int j = 0; j < ports.getLength(); j++)
            {
                Element port = (Element) ports.item(j);
                String portName = port.getAttribute("name");
                String bindingName = port.getAttribute("binding");
                int pos = bindingName.indexOf(":");
                if (pos != -1)
                {
                    //String prefix = bindingName.substring(0, pos);
                    bindingName = bindingName.substring(pos + 1);
                }
                if (portType != null && portType.getAttribute("name").equals(portName) && bindingName.equals(_bindingname))
                {
                    NodeList listAddress = port.getElementsByTagNameNS(SOAP_WSDL_ENVELOPE_NAMESPACE, "address");
                    for (int k = 0; k < listAddress.getLength(); k++)
                    {
                        Element address = (Element) listAddress.item(k);
                        try
                        {
                            return new URL(address.getAttribute("location"));
                        }
                        catch (Exception e)
                        {
                            log.debug(e);
                        }
                    }

                }
            }
        }
        return null;
    }

    public String getSoapAction()
    {
        NodeList list = binding.getElementsByTagNameNS(binding.getNamespaceURI(), "operation");
        for (int i = 0; i < list.getLength(); i++)
        {
            Element _operation = (Element) list.item(i);
            String _name = _operation.getAttribute("name");
            if (_name.equals(this.operationname))
            {
                NodeList soapoperations = _operation.getElementsByTagNameNS(SOAP_WSDL_ENVELOPE_NAMESPACE, "operation");
                for (int j = 0; j < soapoperations.getLength(); i++)
                {
                    Element soapOperation = (Element) soapoperations.item(j);
                    return soapOperation.getAttribute("soapAction");
                }

                soapoperations = _operation.getElementsByTagNameNS(SOAP12_NAMESPACE, "operation");
                for (int j = 0; j < soapoperations.getLength(); i++)
                {
                    Element soapOperation = (Element) soapoperations.item(j);
                    return soapOperation.getAttribute("soapAction");
                }
            }
        }
        return null;

    }

    private Document execute(Document doc) throws ServiceException
    {
        URL url = getURL(binding);
        String soapAction = "\"" + getSoapAction() + "\"";
        try
        {
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("SOAPAction", soapAction);
            {
                String charset = Charset.forName("utf-8").name();
                con.setRequestProperty(CONTENT_TYPE, "text/xml" + "; charset=" + charset);
                con.setDoOutput(true);
                con.setDoInput(true);
                OutputStream out = con.getOutputStream();
                Document soapdocument = doc;
                String xml = SWBUtils.XML.domToXml(soapdocument, charset, true);
                out.write(xml.getBytes());
                out.close();
            }
            if (con.getResponseCode() == 200)
            {
                Charset charset = Charset.forName("utf-8");
                String contentType = con.getContentType();
                if (contentType != null)
                {
                    int pos = contentType.indexOf("charset=");
                    if (pos != -1)
                    {
                        String scharset = contentType.substring(pos + 8).trim();
                        contentType = contentType.substring(0, pos).trim();
                        if (contentType.endsWith(";"))
                        {
                            contentType = contentType.substring(0, contentType.length() - 1);
                        }
                        charset = Charset.forName(scharset);
                    }
                }

                if (contentType != null && (contentType.equalsIgnoreCase(APPLICATION_XML) || contentType.equalsIgnoreCase(TEXT_XML)))
                {
                    InputStreamReader reader = new InputStreamReader(con.getInputStream(), charset);
                    DOMOutputter dOMOutputter = new DOMOutputter();
                    SAXBuilder builder = new SAXBuilder();
                    Document response = dOMOutputter.output(builder.build(reader));
                    reader.close();
                    return response;
                }
                else
                {
                    throw new ServiceException("The response has a not valid Content-Type header: " + con.getHeaderField(CONTENT_TYPE) + "(only " + APPLICATION_XML + " are valid)");
                }
            }
            else
            {

                Charset charset = Charset.forName("utf-8");
                String contentType = con.getContentType();
                if (contentType != null)
                {
                    int pos = contentType.indexOf("charset=");
                    if (pos != -1)
                    {
                        String scharset = contentType.substring(pos + 8).trim();
                        contentType = contentType.substring(0, pos).trim();
                        if (contentType.endsWith(";"))
                        {
                            contentType = contentType.substring(0, contentType.length() - 1);
                        }
                        charset = Charset.forName(scharset);
                    }
                }
                String detail = "";
                if (contentType != null && (contentType.equalsIgnoreCase(APPLICATION_XML) || contentType.equalsIgnoreCase(TEXT_XML)))
                {
                    InputStreamReader reader = new InputStreamReader(con.getErrorStream(), charset);
                    DOMOutputter dOMOutputter = new DOMOutputter();
                    SAXBuilder builder = new SAXBuilder();
                    Document response = dOMOutputter.output(builder.build(reader));
                    System.out.println("xml error: " + SWBUtils.XML.domToXml(response));
                    return response;
                }
                else if (contentType != null && contentType.equalsIgnoreCase("text/html"))
                {
                    InputStreamReader reader = new InputStreamReader(con.getErrorStream(), charset);
                    char[] buffer = new char[1024];
                    int read = reader.read(buffer);
                    StringBuilder sb = new StringBuilder();
                    while (read != -1)
                    {
                        sb.append(new String(buffer, 0, read));
                        read = reader.read(buffer);
                    }
                    detail = sb.toString();
                }
                else
                {
                }
                throw new ServiceException("The response is " + con.getResponseCode(), detail);
            }
        }
        catch (IOException e)
        {
            throw new ServiceException(e);
        }
        catch (JDOMException e)
        {
            throw new ServiceException(e);
        }
    }

    private Document execute12(Document doc) throws ServiceException
    {
        URL url = getURL(binding);

        try
        {
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            {
                String charset = Charset.forName("utf-8").name();
                con.setRequestProperty(CONTENT_TYPE, "text/xml" + "; charset=" + charset);
                con.setDoOutput(true);
                con.setDoInput(true);
                OutputStream out = con.getOutputStream();
                Document soapdocument = doc;
                String xml = SWBUtils.XML.domToXml(soapdocument, charset, true);
                out.write(xml.getBytes());
                out.close();
            }
            if (con.getResponseCode() == 200)
            {
                Charset charset = Charset.forName("utf-8");
                String contentType = con.getContentType();
                if (contentType != null)
                {
                    int pos = contentType.indexOf("charset=");
                    if (pos != -1)
                    {
                        String scharset = contentType.substring(pos + 8).trim();
                        charset = Charset.forName(scharset);
                    }
                }

                if (contentType != null && (contentType.equalsIgnoreCase(APPLICATION_XML) || contentType.equalsIgnoreCase(TEXT_XML)))
                {
                    InputStream in = con.getInputStream();
                    InputStreamReader reader = new InputStreamReader(con.getInputStream(), charset);
                    DOMOutputter dOMOutputter = new DOMOutputter();
                    SAXBuilder builder = new SAXBuilder();
                    Document response = dOMOutputter.output(builder.build(reader));
                    in.close();
                    return response;
                }
                else
                {
                    throw new ServiceException("The response has a not valid Content-Type header: " + con.getHeaderField(CONTENT_TYPE) + "(only " + APPLICATION_XML + " are valid)");
                }
            }
            else
            {

                Charset charset = Charset.forName("utf-8");
                String contentType = con.getContentType();
                if (contentType != null)
                {
                    int pos = contentType.indexOf("charset=");
                    if (pos != -1)
                    {
                        String scharset = contentType.substring(pos + 8).trim();
                        contentType = contentType.substring(0, pos).trim();
                        if (contentType.endsWith(";"))
                        {
                            contentType = contentType.substring(0, contentType.length() - 1);
                        }
                        charset = Charset.forName(scharset);
                    }
                }
                String detail = "";
                if (contentType != null && (contentType.equalsIgnoreCase(APPLICATION_XML) || contentType.equalsIgnoreCase(TEXT_XML)))
                {
                    InputStreamReader reader = new InputStreamReader(con.getErrorStream(), charset);
                    DOMOutputter dOMOutputter = new DOMOutputter();
                    SAXBuilder builder = new SAXBuilder();
                    Document response = dOMOutputter.output(builder.build(reader));
                    System.out.println("xml error: " + SWBUtils.XML.domToXml(response));
                    return response;
                }
                else if (contentType != null && contentType.equalsIgnoreCase("text/html"))
                {
                    InputStreamReader reader = new InputStreamReader(con.getErrorStream(), charset);
                    char[] buffer = new char[1024];
                    int read = reader.read(buffer);
                    StringBuilder sb = new StringBuilder();
                    while (read != -1)
                    {
                        sb.append(new String(buffer, 0, read));
                        read = reader.read(buffer);
                    }
                    detail = sb.toString();
                }
                else
                {
                }
                throw new ServiceException("The response is " + con.getResponseCode(), detail);
            }
        }
        catch (IOException e)
        {
            throw new ServiceException(e);
        }
        catch (JDOMException e)
        {
            throw new ServiceException(e);
        }
    }

    public boolean isSoap()
    {
        boolean isSoap = false;
        NodeList bindigs = binding.getElementsByTagNameNS(SOAP_WSDL_ENVELOPE_NAMESPACE, "binding");
        for (int i = 0; i < bindigs.getLength(); i++)
        {
            if (bindigs.item(i) instanceof Element)
            {
                Element soapbinding = (Element) bindigs.item(i);
                if (soapbinding.getAttribute("transport").equals("http://schemas.xmlsoap.org/soap/http"))
                {
                    return true;
                }
            }
        }
        return isSoap;
    }

    public boolean isSoap12()
    {
        boolean isSoap12 = false;
        NodeList bindigs = binding.getElementsByTagNameNS(SOAP12_NAMESPACE, "binding");
        for (int i = 0; i < bindigs.getLength(); i++)
        {
            if (bindigs.item(i) instanceof Element)
            {
                Element soapbinding = (Element) bindigs.item(i);
                if (soapbinding.getAttribute("transport").equals("http://schemas.xmlsoap.org/soap/http"))
                {
                    return true;
                }
            }
        }
        return isSoap12;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj == null)
        {
            return false;
        }
        if (getClass() != obj.getClass())
        {
            return false;
        }
        final WSDLOperation other = (WSDLOperation) obj;
        if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name))
        {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 29 * hash + (this.name != null ? this.name.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString()
    {
        return name.toString();
    }
}
