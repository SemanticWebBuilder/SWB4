/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.linkeddata.spider;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Set;
import org.jdom.input.SAXBuilder;
import org.jdom.output.DOMOutputter;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.InputSource;

/**
 *
 * @author victor.lorenzana
 */
public class Spider implements Runnable
{

    private static Logger log = SWBUtils.getLogger(Spider.class);
    public static final String XMLLANG = "xml:lang";
    public static final String DOCTYPE_RFDA = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML+RDFa 1.0//EN\" \"http://www.w3.org/MarkUp/DTD/xhtml-rdfa-1.dtd\">";
    public static final String OWL_SCHEMA_NAMESPACE = "http://www.w3.org/2002/07/owl#";
    public static final String RDF_SYNTAXIS_NAMESPACE = "http://www.w3.org/1999/02/22-rdf-syntax-ns#";
    public static final String RDFS_SCHEMA_NAMESPACE = "http://www.w3.org/2000/01/rdf-schema#";
    private static URI typeProp;
    protected URL url;
    private boolean running = false;
    private final SpiderDomain domain;

    static
    {
        try
        {
            typeProp = new URI(RDF_SYNTAXIS_NAMESPACE + "type");
        }
        catch (Exception e)
        {
            typeProp = null;
//            log.debug(e);

        }

    }
    String[] visit =
    {
        "http://www.w3.org/2000/01/rdf-schema#resource"
    };

    public Spider(URL seedURL, SpiderDomain domain)
    {
        this.domain = domain;
        this.url = seedURL;

    }

    public URL getURL()
    {
        return url;
    }

    @Override
    public void run()
    {
        get();
    }

    public void get()
    {
        running = true;




        fireOnStart(url);
        DocumentInfo docInfo = getContent(url);
        running = false;
        if (domain != null)
        {
            domain.onDone(this);
        }
        if (docInfo != null)
        {
            if (docInfo.contentType.equalsIgnoreCase("application/rdf+xml") || docInfo.contentType.equalsIgnoreCase("application/xml"))
            {
                Document doc = getDocument(docInfo);
                if (doc != null)
                {
                    store(doc, url);
                }
            }
            else if (docInfo.contentType.equalsIgnoreCase("text/html"))
            {

                if (docInfo.content.contains("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML+RDFa") || docInfo.content.contains("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN"))
                {

                    try
                    {
                        RDFAAnalizer analizer = new RDFAAnalizer(docInfo.content, this, this.getURL().toURI());
                        analizer.start();
                    }
                    catch (Exception e)
                    {
                        fireError(e);

                    }

                }
                else if (docInfo.content.contains("<!DOCTYPE html PUBLIC \"-//XML-DEV//DTD XHTML RDDL"))
                {
                    try
                    {
                        RDDLAnalizer analizer = new RDDLAnalizer(docInfo.content, this, this.getURL().toURI());
                        analizer.start();
                    }
                    catch (Exception e)
                    {
                        fireError(e);
                    }
                }
                else
                {
                    fireError(new SpiderException("The document is not supported " + docInfo.content, this));
                }

            }
            else if (docInfo.content.contains("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\""))
            {
            }
            else
            {
                //System.out.println("docInfo.contentType: " + docInfo.contentType + " url: " + url);
                fireError(new SpiderException("Format no supportes " + docInfo.contentType, this));
            }
        }
        fireOnEnd(url);
    }

    public boolean isRunning()
    {
        return running;
    }

    private URI getId(Element element) throws SpiderException
    {
        if (element.hasAttributeNS(RDF_SYNTAXIS_NAMESPACE, "about"))
        {
            String _id = element.getAttributeNS(RDF_SYNTAXIS_NAMESPACE, "about");

            if (_id.startsWith("#"))
            {
                try
                {
                    URI uri = new URI(this.url.toString());
                    URI iduri = new URI(_id);
                    iduri = uri.resolve(iduri);
                }
                catch (URISyntaxException e)
                {
                    fireError(e);
                }
            }
            try
            {
                return new URI(_id);
            }
            catch (URISyntaxException e)
            {
                throw new SpiderException(e, this);
            }
        }
        return null;

    }

    private void store(Document doc, URL url)
    {
        Element rdfElement = doc.getDocumentElement();
        //base = rdfElement.getAttribute("xml:base");
        NodeList childs = rdfElement.getChildNodes();
        for (int ichild = 0; ichild < childs.getLength(); ichild++)
        {
            Node node = childs.item(ichild);
            if (node.getNamespaceURI() != null && node instanceof Element)
            {
                //if (!node.getNamespaceURI().equals(RDF_SCHEMA_NAMESPACE) && !node.getNamespaceURI().equals(RDFS_SCHEMA_NAMESPACE) && !node.getNamespaceURI().equals(OWL_SCHEMA_NAMESPACE))
                {
                    try
                    {
                        addElement((Element) node);
                    }
                    catch (Exception e)
                    {
                    }
                }
            }
            else
            {
                if (node instanceof Element)
                {
                    store((Element) node, url);
                }

            }
        }
    }

    private void store(Element element, URL url)
    {
        NodeList childs = element.getChildNodes();
        for (int ichild = 0; ichild < childs.getLength(); ichild++)
        {
            Node node = childs.item(ichild);
            if (node.getNamespaceURI() != null && node instanceof Element)
            {
                if (!node.getNamespaceURI().equals(RDF_SYNTAXIS_NAMESPACE) && !node.getNamespaceURI().equals(RDFS_SCHEMA_NAMESPACE) && !node.getNamespaceURI().equals(OWL_SCHEMA_NAMESPACE))
                {
                    try
                    {
                        addElement((Element) node);
                    }
                    catch (Exception e)
                    {
                    }
                }
            }
        }
    }

    public Document getDocument(DocumentInfo documentInfo)
    {
        return getDocument(documentInfo.content);
    }

    public Document getDocument(String content)
    {
        try
        {
            content = content.trim();
            InputSource in = new InputSource(new StringReader(content));
            DOMOutputter out = new DOMOutputter();
            SAXBuilder builder = new SAXBuilder();
            Document response = out.output(builder.build(in));
            return response;
        }
        catch (Exception e)
        {
            fireError(e);
        }
        return null;
    }

    public DocumentInfo getContent(URL url)
    {
        try
        {
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
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
                InputStreamReader reader = new InputStreamReader(con.getInputStream(), charset);
                char[] cbuf = new char[1024 * 50];
                StringBuilder sb = new StringBuilder();
                int read = reader.read(cbuf);
                while (read != -1)
                {
                    sb.append(new String(cbuf, 0, read));
                    read = reader.read(cbuf);
                }
                DocumentInfo info = new DocumentInfo();
                int pos = contentType.indexOf(";");
                if (pos != -1)
                {
                    contentType = contentType.substring(0, pos).trim();
                }
                info.contentType = contentType;
                info.content = sb.toString();
                info.url = url;
//                for (String key : con.getHeaderFields().keySet())
//                {
//                    info.headers.put(key, con.getHeaderFields().get(key));
//                }
                return info;
            }
            else
            {
                fireError(con.getResponseCode());
            }
        }
        catch (IOException ioe)
        {
            fireError(ioe);
        }
        return null;

    }

    private String format(String data)
    {
        StringBuilder sb = new StringBuilder();
        for (Character _char : data.toCharArray())
        {

            if (_char == '\"')
            {
                sb.append("\\\"");
            }
            else if (_char == '\\')
            {
                sb.append("\\\\");
            }
            else if (_char == '\n')
            {
                sb.append("\\\n");
            }
            else if (_char == '\r')
            {
                sb.append("\\\r");
            }
            else if (_char == '\t')
            {
                sb.append("\\\t");
            }
            else
            {
                int ichar = (char) _char;
                if (ichar > 0 && ichar < 127)
                {
                    sb.append(_char);
                }
                else
                {
                    String hex = "\\u" + UnicodeFormatter.charToHex(_char);
                    sb.append(hex);
                }
            }
        }

        return sb.toString();
    }

    public boolean isDataType(Set<TripleElement> elements, URI pred)
    {
        if (pred.toString().startsWith(RDFS_SCHEMA_NAMESPACE))
        {
            for (TripleElement element : elements)
            {
                if (element.pred.toString().equals(RDFS_SCHEMA_NAMESPACE + "range"))
                {
                    if (element.obj.equals(RDFS_SCHEMA_NAMESPACE + "Literal"))
                    {
                        return true;
                    }
                }
            }
        }
        else
        {
            for (TripleElement element : elements)
            {
                if (element.pred.toString().equals("http://www.w3.org/1999/02/22-rdf-syntax-ns#type"))
                {
                    if (element.obj.equals("http://www.w3.org/2002/07/owl#DatatypeProperty"))
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean isRDFProperty(Set<TripleElement> elements)
    {
        for (TripleElement element : elements)
        {
            if (element.pred.toString().equals("http://www.w3.org/1999/02/22-rdf-syntax-ns#type"))
            {
                if (element.obj.equals("http://www.w3.org/1999/02/22-rdf-syntax-ns#Property"))
                {
                    return true;
                }
            }
        }
        return false;
    }

    public String getRange(Set<TripleElement> elements)
    {
        for (TripleElement element : elements)
        {
            if (element.pred.toString().equals("http://www.w3.org/2000/01/rdf-schema#range"))
            {
                return element.obj;
            }
        }
        return null;
    }

//    private boolean isRDFSProperty(URI pred)
//    {
//        if(pred.toString().startsWith(RDFS_SCHEMA_NAMESPACE))
//        {
//            return true;
//        }
//        return false;
//    }
    public synchronized void onTriple(URI suj, URI pred, String obj, Spider source, String lang)
    {

        SpiderManager.loadPredicates(pred);
        Set<TripleElement> elements = SpiderManager.predicates.get(pred);
        StringBuilder sb = new StringBuilder();
        sb.append("<").append(format(suj.toString())).append("> ");
        sb.append("<").append(format(pred.toString())).append("> ");
        if (!elements.isEmpty())
        {

            if (isDataType(elements, pred))
            {
                String range = getRange(elements);
                if (range != null)
                {
                    if (range.equals("http://www.w3.org/2001/XMLSchema#string") || range.equals("http://www.w3.org/2000/01/rdf-schema#Literal"))
                    {
                        if (lang == null)
                        {
                            sb.append("\"").append(format(obj)).append("\" .");
                        }
                        else
                        {
                            sb.append("\"").append(format(obj)).append("\"@").append(lang).append(" .");
                        }
                    }
                    else
                    {
                        if (lang == null)
                        {
                            sb.append("\"").append(format(obj)).append("\"^^<").append(range).append("> .");
                        }
                        else
                        {
                            sb.append("\"").append(format(obj)).append("\"^^<").append(range).append(">@").append(lang).append(" .");
                        }
                    }
                    fireEventNtFormat(sb.toString(), source);
                }

            }
            else if (isRDFProperty(elements))
            {
                if (lang == null)
                {
                    sb.append("\"").append(format(obj)).append("\" .");
                }
                else
                {
                    sb.append("\"").append(format(obj)).append("\"@").append(lang).append(" .");
                }
                fireEventNtFormat(sb.toString(), source);
            }
            else
            {
                sb.append("<").append(format(obj)).append("> .");
                fireEventNtFormat(sb.toString(), source);
            }
        }
        else
        {
            if (lang == null)
            {
                sb.append("\"").append(format(obj)).append("\" .");
            }
            else
            {
                sb.append("\"").append(format(obj)).append("\"@").append(lang).append(" .");
            }
            fireEventNtFormat(sb.toString(), source);

        }
    }

    private void fireError(final int code)
    {
        if (domain != null)
        {
            domain.fireError(code, url);
        }
    }

    public void fireError(final Throwable e)
    {
        if (domain != null)
        {
            domain.fireError(e, url);
        }

    }

    public void fireEventNtFormat(final String row, final Spider spider)
    {
        if (domain != null)
        {
            domain.fireEventNtFormat(row, spider, url);
        }
    }

    public void fireEventnewTriple(final URI suj, final URI pred, final String obj, final Spider spider, final String lang)
    {

        if (domain != null)
        {
            domain.fireEventnewTriple(suj, pred, obj, spider, lang);
        }
        onTriple(suj, pred, obj, spider, lang);

    }

    public void fireEventnewTriple(final URI suj, final URI pred, final URI obj, final Spider spider, final String lang)
    {
        if (pred.toString().equals("http://dbpedia.org/ontology/background"))
        {
            System.out.println("a");
        }
        if (domain != null)
        {
            domain.fireEventnewTriple(suj, pred, obj, spider, lang);
        }
        else
        {
            onTriple(suj, pred, obj, spider, lang);
        }

    }

    public SpiderDomain getDomain()
    {
        return domain;
    }

    public void fireOnStart(final URL url)
    {
        if (domain != null)
        {
            domain.fireOnStart(url);
        }
    }

    public void fireOnEnd(final URL url)
    {
        if (domain != null)
        {
            domain.fireOnEnd(url);

        }
    }

    public void fireVisit(final URI suj,TYPE type,Spider spider)
    {
        boolean fireVisit = true;
        if (domain != null)
        {
            fireVisit = domain.fireVisit(suj,type,spider);
        }
        if (fireVisit)
        {
            SpiderManager.createSpider(url, this);
        }

    }

    private void addProperty(URI suj, Element prop, String lang)
    {
        if (prop.getNamespaceURI() != null)
        {
            try
            {
                URI pred = new URI(prop.getNamespaceURI() + prop.getLocalName());
                if (prop.hasAttributeNS(RDF_SYNTAXIS_NAMESPACE, "resource"))
                {
                    URI obj = new URI(prop.getAttributeNodeNS(RDF_SYNTAXIS_NAMESPACE, "resource").getValue());
                    String _lang = null;
                    if (prop.hasAttribute(XMLLANG))
                    {
                        prop.getAttribute(XMLLANG);
                    }
                    onPred(pred);
                    fireEventnewTriple(suj, pred, obj, this, _lang);
                    addOtherProperties(obj, prop);
                }
                else
                {
                    NodeList childs = prop.getChildNodes();
                    for (int ichild = 0; ichild < childs.getLength(); ichild++)
                    {
                        Node child = childs.item(ichild);
                        if (child.getLocalName() != null && child.getNamespaceURI().equals(RDF_SYNTAXIS_NAMESPACE) && child.getLocalName().equals("Description") && child instanceof Element)
                        {
                            Element description = (Element) child;
                            if (description.hasAttributeNS(RDF_SYNTAXIS_NAMESPACE, "about"))
                            {
                                String id = description.getAttributeNS(RDF_SYNTAXIS_NAMESPACE, "about");
                                if (id.startsWith("#"))
                                {
                                    try
                                    {
                                        URI uri = new URI(this.url.toString());
                                        URI iduri = new URI(id);
                                        iduri = uri.resolve(iduri);
                                    }
                                    catch (URISyntaxException e)
                                    {
                                        fireError(e);

                                    }
                                }
                                URI obj = new URI(id);
                                String _lang = null;
                                if (description.hasAttribute(XMLLANG))
                                {
                                    description.getAttribute(XMLLANG);
                                }
                                onPred(pred);

                                fireEventnewTriple(suj, pred, obj, this, _lang);
                                addOtherProperties(obj, description);
                            }
                        }
                        if (child instanceof Text)
                        {
                            Text text = (Text) child;
                            String data = text.getData();
                            if (data != null && data.indexOf("\n") == -1)
                            {
                                String obj = data;
                                onPred(pred);
                                fireEventnewTriple(suj, pred, obj, this, lang);
                            }
                        }
                    }
                }
            }
            catch (URISyntaxException e)
            {
                fireError(e);
            }

        }
    }

    private void addElement(Element element)
    {
        try
        {
            URI suj = getId(element);
            if (suj != null)
            {
                addOtherProperties(suj, element);
            }
            else
            {
                NodeList childs = element.getChildNodes();
                for (int ichild = 0; ichild < childs.getLength(); ichild++)
                {
                    Node child = childs.item(ichild);
                    if (child.getLocalName() != null && child.getNamespaceURI().equals(RDF_SYNTAXIS_NAMESPACE) && child.getLocalName().equals("Description") && child instanceof Element)
                    {
                        Element description = (Element) child;
                        if (description.hasAttributeNS(RDF_SYNTAXIS_NAMESPACE, "about"))
                        {
                            addOtherProperties(suj, description);
                        }
                    }
                }
            }
        }
//        catch (URISyntaxException e)
//        {
//            fireError(url, e);
//        }
        catch (SpiderException e)
        {
            fireError(e);
        }

    }

    private void addOtherProperties(URI suj, Element description)
    {
        NodeList childs = description.getChildNodes();
        for (int ichild = 0; ichild < childs.getLength(); ichild++)
        {
            Node child = childs.item(ichild);
            if (child instanceof Element && child.getNamespaceURI() != null && child.getLocalName() != null)
            {
                Element prop = (Element) child;
                String _lang = null;
                if (prop.hasAttribute(XMLLANG))
                {
                    prop.getAttribute(XMLLANG);
                }
                addProperty(suj, prop, _lang);
            }
        }
    }

    public void onNewSubject(URI suj,TYPE type,Spider spider)
    {
        boolean _visit = true;
        if (domain != null)
        {
            _visit = domain.onNewSubject(suj,type,spider);
        }
        if (_visit)
        {
            try
            {
                URL newURL = suj.toURL();
                SpiderManager.createSpider(newURL, this);
            }
            catch (Exception e)
            {
                fireError(e);

            }
        }
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj instanceof Spider)
        {
            Spider tmp = (Spider) obj;
            return tmp.url.equals(this.url);
        }
        else
        {
            return false;
        }

    }

    @Override
    public int hashCode()
    {
        return url.toString().hashCode();
    }

    @Override
    public String toString()
    {
        return url.toString();
    }

    public void onTriple(URI suj, URI pred, URI obj, Spider source, String lang)
    {
        StringBuilder sb = new StringBuilder();

        sb.append("<").append(format(suj.toString())).append("> ");
        sb.append("<").append(format(pred.toString())).append("> ");
        sb.append("<").append(format(obj.toString())).append("> .");
        fireEventNtFormat(sb.toString(), source);


    }

    protected void onPred(URI pred)
    {
        SpiderManager.onPred(pred, this);
    }
}
