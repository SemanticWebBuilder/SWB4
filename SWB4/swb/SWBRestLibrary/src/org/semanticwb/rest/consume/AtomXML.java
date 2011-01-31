/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.rest.consume;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.List;
import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;

/**
 *
 * @author victor.lorenzana
 */
public class AtomXML extends ApplicationXML
{
    public static final String APPLICATION_ATOM_XML = "application/atom+xml";
    private static final Logger log = SWBUtils.getLogger(AtomXML.class);    
    public static final String ATOM_NS = "http://www.w3.org/2005/Atom";
    private static final String atomchema = AtomXML.loadSchema("atom.txt");
    
    
    public static String loadSchema(String name)
    {
        StringBuilder sb = new StringBuilder();
        InputStream in = AtomXML.class.getResourceAsStream("/org/semanticwb/rest/util/" + name);
        byte[] buffer = new byte[2048];
        try
        {
            int read = in.read(buffer);
            while (read != -1)
            {
                sb.append(new String(buffer, 0, read));
                read = in.read(buffer);
            }
        }
        catch (Exception e)
        {
            log.error(e);
        }
        return sb.toString();
    }
    private static boolean isValidAtom(Document doc)
    {

        SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.RELAXNG_NS_URI);
        StringReader reader = new StringReader(atomchema);
        try
        {
            Source schemaFile = new StreamSource(reader);
            try
            {
                Schema schema = factory.newSchema(schemaFile);
                Validator validator = schema.newValidator();
                DOMSource source = new DOMSource(doc);
                validator.validate(source);
                return true;
            }
            catch (IOException ioe)
            {
                log.debug(ioe);
                return false;
            }
            catch (SAXException saxe)
            {
                log.debug(saxe);
                return false;
            }
        }
        catch (Exception e)
        {
            log.debug(e);
            return false;
        }
    }
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
            if(!isValidAtom(requestAtomDocument))
            {
                throw new RestException("The document is not a valid atom document");
            }
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
