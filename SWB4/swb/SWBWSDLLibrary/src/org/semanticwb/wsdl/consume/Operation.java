/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.wsdl.consume;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.DOMOutputter;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author victor.lorenzana
 */
public class Operation
{
    
    private static final String APPLICATION_XML = "application/xml";
    private static final String CONTENT_TYPE = "Content-Type";
    protected static final String TEXT_XML = "text/xml";
    private static final Logger log = SWBUtils.getLogger(Operation.class);
    private static final String SOAP_ENVELOPE_NAMESPACE = "http://schemas.xmlsoap.org/soap/envelope/";
    private static final String SOAP12_ENVELOPE_NAMESPACE = "http://www.w3.org/2001/12/soap-envelope";
    private static final String SOAP_WSDL_ENVELOPE_NAMESPACE = "http://schemas.xmlsoap.org/wsdl/soap/";
    private static final String SOAP12_NAMESPACE = "http://schemas.xmlsoap.org/wsdl/soap12/";
    private String portType = null;
    private OperationType type = OperationType.NOTIFICATION;
    private final String name;
    private final Element operation;
    private final Input input;
    private final Ouput output;
    

    public Operation(Element operation,org.jdom.Document _jdom) throws ServiceException
    {        
        this.operation = operation;
        if (operation.getParentNode() != null && operation.getParentNode() instanceof Element)
        {
            Element _portType = (Element) operation.getParentNode();
            portType = _portType.getAttribute("name");
        }
        this.name = operation.getAttribute("name") == null ? "" : operation.getAttribute("name");
        NodeList _inputs = operation.getElementsByTagNameNS(operation.getNamespaceURI(), "input");
        Input _input = null;
        for (int i = 0; i < _inputs.getLength(); i++)
        {
            Element einput = (Element) _inputs.item(i);
            _input = new Input(einput, this,_jdom);
        }
        this.input = _input;


        Ouput _output = null;
        NodeList _outputs = operation.getElementsByTagNameNS(operation.getNamespaceURI(), "output");
        for (int i = 0; i < _outputs.getLength(); i++)
        {
            Element eoutput = (Element) _outputs.item(i);
            _output = new Ouput(eoutput, this,_jdom);

        }
        this.output = _output;

        if (input != null && output != null)
        {
            type = OperationType.REQUEST_RESPONSE;
        }
        else if (input == null && output != null)
        {
            type = OperationType.SOLICIT_RESPONSE;
        }
        else if (input != null && output == null)
        {
            type = OperationType.ONE_WAY;
        }
        else
        {
            type = OperationType.NOTIFICATION;
        }
    }

    public String getName()
    {
        return name;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final Operation other = (Operation) obj;
        if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name))
            return false;
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

    public Element getOperationElement()
    {
        return operation;
    }

    public OperationType getOperationType()
    {
        return type;
    }

    public Input getInput()
    {
        return input;
    }

    public Ouput getOutput()
    {
        return output;
    }

    private boolean isValid(Parameter parameter, ParameterDefinition parameterDefinition)
    {
        try
        {
            if (parameter.getValue() != null && parameter.getValue().getClass().getCanonicalName().equals(parameterDefinition.getDefinitionClass().getCanonicalName()))
            {
                return true;
            }
        }
        catch (ClassNotFoundException cnfe)
        {
            return false;
        }
        return false;
    }

    private Document execute12(Document doc, Element binding) throws ServiceException
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

    private Document execute(Document doc, Element binding) throws ServiceException
    {
        URL url = getURL(binding);
        String soapAction = "\"" + getSoapAction(binding) + "\"";
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

    private Document serialize(ArrayList<ParameterToExecute> parameters, boolean issoap12) throws ServiceException
    {

        Document doc = SWBUtils.XML.getNewDocument();
        Element body=null;
        if(issoap12)
        {
            body = createEmptyEnvelope12(doc);
        }
        else
        {
            body = createEmptyEnvelope(doc);
        }
        
        for (ParameterToExecute p : parameters)
        {
            Document docParameter = p.getDefinition().toDocument(p.getParameter());
            Node node = doc.importNode(docParameter.getDocumentElement(), true);
            body.appendChild(node);
        }
        System.out.println("xml : " + SWBUtils.XML.domToXml(doc));
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
    
    public Parameter[] deserialize(Document response) throws RemoteException,ServiceException
    {
        if(RemoteException.isError(response))
        {
            throw RemoteException.createRemoteException(response);
        }
        ArrayList<Parameter> deserialize = new ArrayList<Parameter>();
        for(ParameterDefinition definition : output.getParameters())
        {
            Object value=definition.getValue(response);
            deserialize.add(new Parameter(definition.getName(), value,definition));
        }
        return deserialize.toArray(new Parameter[deserialize.size()]);
    }

    public Parameter[] execute(Parameter... parameters) throws ServiceException,RemoteException
    {
        return execute(Arrays.asList(parameters));
    }

    public Parameter[] execute(List<Parameter> parameters) throws ServiceException,RemoteException
    {
        ArrayList<ParameterToExecute> execute = new ArrayList<ParameterToExecute>();
        for (ParameterDefinition parameterDefinition : input.getParameters())
        {
            boolean exists = false;
            Parameter parameterToAdd = null;
            for (Parameter parameter : parameters)
            {
                if (parameter.getName().equals(parameterDefinition.getName()))
                {
                    exists = true;
                    parameterToAdd = parameter;
                    break;
                }
            }
            if (!exists)
            {
                throw new ServiceException("The parameter " + parameterDefinition.getName() + " was not found");
            }
            if (!isValid(parameterToAdd, parameterDefinition))
            {
                throw new ServiceException("The parameter " + parameterDefinition.getName() + " is not valid required " + parameterDefinition.getType());
            }
            execute.add(new ParameterToExecute(parameterToAdd, parameterDefinition));
        }
        Element binding = selectBinding();
        if (binding == null)
        {
            throw new ServiceException("The service can not be executed (Supported binding was not found)");
        }
        boolean issoap12=isSoap12(binding);
        Document docExecute = serialize(execute, issoap12);
        Document docResponse=null;
        if(issoap12)
        {
            docResponse = execute12(docExecute, binding);
        }
        else
        {
            docResponse = execute(docExecute, binding);
        }
        
        return deserialize(docResponse);

    }
    public boolean isSoap12(Element binding)
    {
        boolean isSoap12=false;
        NodeList bindigs=binding.getElementsByTagNameNS(SOAP12_NAMESPACE, "binding");
        for(int i=0;i<bindigs.getLength();i++)
        {
            if(bindigs.item(i) instanceof Element)
            {
                Element soapbinding=(Element)bindigs.item(i);
                if(soapbinding.getAttribute("transport").equals("http://schemas.xmlsoap.org/soap/http"))
                {
                    return true;
                }
            }
        }
        return isSoap12;
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
                    String prefix = bindingName.substring(0, pos);
                    bindingName = bindingName.substring(pos + 1);
                }
                if (portType != null && portType.equals(portName) && bindingName.equals(_bindingname))
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

    public String getDocumentStyle(Element binding)
    {
        NodeList list = binding.getElementsByTagNameNS(binding.getNamespaceURI(), "operation");
        for (int i = 0; i < list.getLength(); i++)
        {
            Element _operation = (Element) list.item(i);
            String _name = _operation.getAttribute("name");
            if (_name.equals(this.name))
            {
                NodeList soapoperations = _operation.getElementsByTagNameNS(SOAP_WSDL_ENVELOPE_NAMESPACE, "operation");
                for (int j = 0; j < soapoperations.getLength(); i++)
                {
                    Element soapOperation = (Element) soapoperations.item(j);
                    return soapOperation.getAttribute("style");
                }

                soapoperations = _operation.getElementsByTagNameNS(SOAP12_NAMESPACE, "operation");
                for (int j = 0; j < soapoperations.getLength(); i++)
                {
                    Element soapOperation = (Element) soapoperations.item(j);
                    return soapOperation.getAttribute("style");
                }
            }
        }
        return null;

    }

    public String getSoapAction(Element binding)
    {
        NodeList list = binding.getElementsByTagNameNS(binding.getNamespaceURI(), "operation");
        for (int i = 0; i < list.getLength(); i++)
        {
            Element _operation = (Element) list.item(i);
            String _name = _operation.getAttribute("name");
            if (_name.equals(this.name))
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

    private Element selectBinding()
    {
        for (Element binding : getBindings())
        {
            NodeList list = binding.getElementsByTagNameNS(binding.getNamespaceURI(), "operation");
            for (int i = 0; i < list.getLength(); i++)
            {
                Element _operation = (Element) list.item(i);
                String _name = _operation.getAttribute("name");
                if (_name.equals(this.name))
                {
                    NodeList soapbindings = binding.getElementsByTagNameNS(SOAP_WSDL_ENVELOPE_NAMESPACE, "binding");
                    for (int j = 0; j < soapbindings.getLength(); j++)
                    {
                        Element sopabinding = (Element) soapbindings.item(j);
                        String _type = sopabinding.getAttribute("transport");
                        if ("http://schemas.xmlsoap.org/soap/http".equals(_type))
                        {
                            return binding;
                        }
                    }
                    soapbindings = binding.getElementsByTagNameNS(SOAP12_NAMESPACE, "binding");
                    for (int j = 0; j < soapbindings.getLength(); j++)
                    {
                        Element sopabinding = (Element) soapbindings.item(j);
                        String _type = sopabinding.getAttribute("transport");
                        if ("http://schemas.xmlsoap.org/soap/http".equals(_type))
                        {
                            return binding;
                        }
                    }
                }
            }

        }
        return null;
    }

    private Element[] getBindings()
    {
        ArrayList<Element> getBindings = new ArrayList<Element>();
        Document doc = operation.getOwnerDocument();
        NodeList bindings = doc.getElementsByTagNameNS(operation.getNamespaceURI(), "binding");
        for (int i = 0; i < bindings.getLength(); i++)
        {
            Element binding = (Element) bindings.item(i);
            String _type = binding.getAttribute("type");
            if (_type.startsWith("tns:"))
            {
                _type = _type.substring(4);
            }
            if (this.portType.equals(_type))
            {
                getBindings.add(binding);
            }
        }
        return getBindings.toArray(new Element[getBindings.size()]);
    }
}
