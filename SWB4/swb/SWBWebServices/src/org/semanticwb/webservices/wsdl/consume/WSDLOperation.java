/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.webservices.wsdl.consume;

import org.json.JSONObject;
import org.semanticwb.webservices.Request;
import org.semanticwb.webservices.Operation;
import org.semanticwb.webservices.Response;
import org.semanticwb.webservices.ServiceException;
import org.semanticwb.webservices.ServiceInfo;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 *
 * @author victor.lorenzana
 */
public class WSDLOperation implements Operation
{

    private final String name;
    private final Element binding;
    private final Element portType;
    private final WSDLInput input;
    private final WSDLOutput output;

    public WSDLOperation(Element operation, ServiceInfo service, Element portType, Element binding) throws ServiceException
    {
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

    @Override
    public JSONObject execute(JSONObject object)
    {

        throw new UnsupportedOperationException("Not supported yet.");
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
