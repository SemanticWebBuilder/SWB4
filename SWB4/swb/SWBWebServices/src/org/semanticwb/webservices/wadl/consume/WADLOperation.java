/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.webservices.wadl.consume;

import org.json.JSONObject;
import org.semanticwb.SWBUtils;
import org.semanticwb.webservices.Request;
import org.semanticwb.webservices.Operation;
import org.semanticwb.webservices.Response;
import org.semanticwb.webservices.ServiceException;
import org.semanticwb.webservices.ServiceInfo;
import org.semanticwb.webservices.util.HTTPMethod;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 *
 * @author victor.lorenzana
 */
public class WADLOperation implements Operation
{

    private final String id;
    private final WADLRequest input;
    private final WADLResponse output;
    private final HTTPMethod httpMethod;

    public WADLOperation(Element method, ServiceInfo service) throws ServiceException
    {
        id = method.getAttribute("id");
        String name = method.getAttribute("name");

        HTTPMethod _httpMethod = HTTPMethod.GET;
        if (name.equals(HTTPMethod.DELETE.toString()))
        {
            _httpMethod = HTTPMethod.DELETE;
        }
        if (name.equals(HTTPMethod.POST.toString()))
        {
            _httpMethod = HTTPMethod.POST;
        }
        if (name.equals(HTTPMethod.PUT.toString()))
        {
            _httpMethod = HTTPMethod.PUT;
        }
        this.httpMethod = _httpMethod;
        WADLRequest _input = null;
        NodeList nodesRequest = method.getElementsByTagNameNS(method.getNamespaceURI(), "request");
        if (nodesRequest.getLength() > 0)
        {
            Element request = (Element) nodesRequest.item(0);
            _input = new WADLRequest(request, this, service);
        }
        this.input = _input;
        WADLResponse _output = null;
        NodeList listResponse = method.getElementsByTagNameNS(method.getNamespaceURI(), "response");
        for (int i = 0; i < listResponse.getLength(); i++)
        {
            Element response = (Element) listResponse.item(i);
            String status = response.getAttribute("status");
            if ("200".equals(status))
            {
                if(_output==null)
                {
                    _output = new WADLResponse(response, this, service);
                }
                else
                {
                    if(!_output.hasDefinition())
                    {
                        _output = new WADLResponse(response, this, service);
                    }
                }
            }
        }
        this.output = _output;
    }

    public HTTPMethod getHTTPMethod()
    {
        return httpMethod;
    }

    @Override
    public String getName()
    {
        return id;
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

    private Document toDocument(JSONObject object)
    {
        Document doc=SWBUtils.XML.getNewDocument();
        return doc;
    }
    @Override
    public JSONObject execute(JSONObject object) throws ServiceException
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
        final WADLOperation other = (WADLOperation) obj;
        if ((this.id == null) ? (other.id != null) : !this.id.equals(other.id))
        {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode()
    {
        int hash = 5;
        hash = 61 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString()
    {
        return id.toString();
    }
}
