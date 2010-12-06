/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.rest;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.List;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

/**
 *
 * @author victor.lorenzana
 */
public class AtomXML extends ApplicationXML
{
    public static final String APPLICATION_ATOM_XML = "application/atom+xml";
    private static final Logger log = SWBUtils.getLogger(AtomXML.class);    
    public static final String ATOM_NS = "http://www.w3.org/2005/Atom";
      
    
    @Override
    protected Document constructParameters(List<ParameterValue> values) throws RestException
    {
        Document doc = SWBUtils.XML.getNewDocument();
        Element feed = doc.createElementNS(ATOM_NS, "feed");
        doc.appendChild(feed);
        try
        {
            for (Parameter parameter : this.getRequiredParameters())
            {
                for (ParameterValue pvalue : values)
                {
                    if (pvalue.getName().equals(parameter.getName()))
                    {
                        Element eparam = doc.createElementNS(ATOM_NS, parameter.getName());
                        Text data = doc.createTextNode(pvalue.getValue().toString());
                        eparam.appendChild(data);
                    }
                }
            }
            for (Parameter parameter : this.getOptionalParameters())
            {
                for (ParameterValue pvalue : values)
                {
                    if (pvalue.getName().equals(parameter.getName()))
                    {
                        Element eparam = doc.createElementNS(ATOM_NS, parameter.getName());
                        Text data = doc.createTextNode(pvalue.getValue().toString());
                        eparam.appendChild(data);
                    }
                }
            }
            for (Parameter parameter : this.parameters)
            {
                if (parameter.isFixed())
                {
                    Element eparam = doc.createElementNS(ATOM_NS, parameter.getName());
                    Text data = doc.createTextNode(parameter.getFixedValue());
                    eparam.appendChild(data);
                }
            }


            for (Parameter parameter : this.method.getRequiredParameters())
            {
                for (ParameterValue pvalue : values)
                {
                    if (pvalue.getName().equals(parameter.getName()))
                    {
                        Element eparam = doc.createElementNS(ATOM_NS, parameter.getName());
                        Text data = doc.createTextNode(pvalue.getValue().toString());
                        eparam.appendChild(data);
                    }
                }
            }
            for (Parameter parameter : this.method.getOptionalParameters())
            {
                for (ParameterValue pvalue : values)
                {
                    if (pvalue.getName().equals(parameter.getName()))
                    {
                        Element eparam = doc.createElementNS(ATOM_NS, parameter.getName());
                        Text data = doc.createTextNode(pvalue.getValue().toString());
                        eparam.appendChild(data);
                    }
                }
            }
            for (Parameter parameter : this.method.getAllParameters())
            {
                if (parameter.isFixed())
                {
                    Element eparam = doc.createElementNS(ATOM_NS, parameter.getName());
                    Text data = doc.createTextNode(parameter.getFixedValue());
                    eparam.appendChild(data);
                }
            }

        }
        catch (Exception e)
        {
            log.debug(e);
            throw new RestException(e);
        }
        return doc;
    }
    @Override
    public RepresentationResponse request(List<ParameterValue> values) throws RestException
    {
        checkParameters(values);
        URL _url = this.getMethod().getResource().getPath();
        try
        {
            HttpURLConnection con = (HttpURLConnection) _url.openConnection();
            con.setRequestMethod(this.getMethod().getHTTPMethod().toString());
            String charset = Charset.defaultCharset().name();
            con.setRequestProperty(CONTENT_TYPE,APPLICATION_ATOM_XML+ "; charset=" + charset);
            con.setDoOutput(true);
            con.setDoInput(true);
            OutputStream out = con.getOutputStream();
            Document requestAtomDocument = constructParameters(values);
            String xml = SWBUtils.XML.domToXml(requestAtomDocument, charset, true);
            out.write(xml.getBytes());
            out.close();
            return super.processResponse(con);
        }
        catch (Exception ioe)
        {
            throw new ExecutionRestException(this.getMethod().getHTTPMethod(), _url, ioe);
        }
    }

    
}
