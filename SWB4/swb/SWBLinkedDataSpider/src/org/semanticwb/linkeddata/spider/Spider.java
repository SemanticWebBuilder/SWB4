/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.linkeddata.spider;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.HashSet;
import java.util.Set;
import org.jdom.input.SAXBuilder;
import org.jdom.output.DOMOutputter;
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
public class Spider implements SpiderEventListener, Runnable
{

    public static final String XMLLANG = "xml:lang";
    public static Predicates predicates = new Predicates();
    public static final String DOCTYPE_RFDA = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML+RDFa 1.0//EN\" \"http://www.w3.org/MarkUp/DTD/xhtml-rdfa-1.dtd\">";
    public static final String OWL_SCHEMA_NAMESPACE = "http://www.w3.org/2002/07/owl#";
    public static final String RDF_SCHEMA_NAMESPACE = "http://www.w3.org/1999/02/22-rdf-syntax-ns#";
    public static final String RDFS_SCHEMA_NAMESPACE = "http://www.w3.org/2000/01/rdf-schema#";
    private static URI typeProp;

    static
    {
        try
        {
            typeProp = new URI(RDF_SCHEMA_NAMESPACE + "type");
        }
        catch (Exception e)
        {
            typeProp = null;
            e.printStackTrace();
        }

    }
    String[] visit =
    {
        "http://www.w3.org/2000/01/rdf-schema#resource"
    };
    private HashSet<SpiderEventListener> listeners = new HashSet<SpiderEventListener>();
    private URL url;
    private boolean running = false;

    public Set<SpiderEventListener> getListeners()
    {
        return listeners;
    }

    public Spider(URL seedURL)
    {
        String _url = seedURL.toString();
        if (_url.contains("#"))
        {
            int pos = _url.indexOf("#");
            if (pos != -1)
            {
                _url = _url.substring(0, pos);
            }
        }
        try
        {
            this.url = new URL(_url);
        }
        catch (Exception e)
        {
            this.url = seedURL;
        }

    }

    public URL getURL()
    {
        return url;
    }

    public void addSpiderListener(SpiderEventListener event)
    {
        listeners.add(event);
    }

    public void removeSpiderListener(SpiderEventListener event)
    {
        listeners.remove(event);
    }

    @Override
    public void run()
    {
        get();
    }

    public void get()
    {
        running = true;
        this.listeners.add(this);
        if (!SpiderManager.visited.contains(url))
        {
            SpiderManager.visited.add(url);
            fireOnStart(url);
            DocumentInfo docInfo = getContent(url);
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
                    try
                    {
                        RDFAAnalizer analizer = new RDFAAnalizer(docInfo.content, this, this.getURL().toURI());
                        analizer.start();
                    }
                    catch (Exception e)
                    {
                        try
                        {
                            RDDLAnalizer analizer = new RDDLAnalizer(docInfo.content, this, this.getURL().toURI());
                            analizer.start();
                        }
                        catch (Exception e2)
                        {
                            e.printStackTrace();
                        }
                    }
                }
                else
                {
                    System.out.println("docInfo.contentType: " + docInfo.contentType + " url: " + url);
                }
            }
            fireOnEnd(url);

            running = false;
        }




    }

    public boolean isRunning()
    {
        return running;
    }

    private URI getId(Element element) throws SpiderException
    {
        if (element.hasAttributeNS(RDF_SCHEMA_NAMESPACE, "about"))
        {
            String _id = element.getAttributeNS(RDF_SCHEMA_NAMESPACE, "about");
//            if (_id.startsWith("#"))
//            {
//                _id = element.getNamespaceURI() + element.getLocalName() + _id;
//            }
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
                if (!node.getNamespaceURI().equals(RDF_SCHEMA_NAMESPACE) && !node.getNamespaceURI().equals(RDFS_SCHEMA_NAMESPACE) && !node.getNamespaceURI().equals(OWL_SCHEMA_NAMESPACE))
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
                char[] cbuf = new char[1024];
                StringBuilder sb = new StringBuilder();
                int read = reader.read(cbuf);
                while (read != -1)
                {
                    sb.append(new String(cbuf, 0, read));
                    read = reader.read(cbuf);
                }
                DocumentInfo info = new DocumentInfo();
                info.contentType = contentType;
                info.content = sb.toString();
                info.url = url;
                for (String key : con.getHeaderFields().keySet())
                {
                    info.headers.put(key, con.getHeaderFields().get(key));
                }
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

    public boolean isVisit(URI url)
    {
        for (String value : visit)
        {
            if (url.toString().equals(value.toString()))
            {
                return true;
            }
        }
        return false;
    }

    /*public void onPred(URI suj, URI pred, String obj, Spider source)
    {
    TripleElement element=new TripleElement(suj, pred, obj);
    values.put(suj,element);
    }*/
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

    public boolean isDataType(Set<TripleElement> elements)
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

    public synchronized void onTriple(URI suj, URI pred, String obj, Spider source, String lang)
    {

        Set<TripleElement> elements = predicates.get(pred);
        if (!elements.isEmpty())
        {
            StringBuilder sb = new StringBuilder();
            sb.append("<").append(format(suj.toString())).append("> ");
            sb.append("<").append(format(pred.toString())).append("> ");
            if (isDataType(elements))
            {
                String range = getRange(elements);
                if (range != null)
                {
                    if (range.equals("http://www.w3.org/2001/XMLSchema#string"))
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
                else
                {
                    System.out.print("a");
                    range = getRange(elements);
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

//        try
//        {
//            URL newURL = pred.toURL();
//            if (!SpiderManager.visited.contains(newURL))
//            {
//                Spider spider = new Spider(newURL);
//                for (SpiderEventListener listener : listeners)
//                {
//                    spider.addSpiderListener(listener);
//                }
//                SpiderManager.addSpider(spider);
//            }
//
//        }
//        catch (Exception e)
//        {
//            fireError(e);
//
//        }
        if (isVisit(suj))
        {
            try
            {
                Spider spider = new Spider(suj.toURL());
                for (SpiderEventListener listener : listeners)
                {
                    spider.addSpiderListener(listener);
                }
                SpiderManager.addSpider(spider);
            }
            catch (MalformedURLException mfe)
            {
                fireError(mfe);

            }
        }
    }

    private void fireError(final int code)
    {
        for (final SpiderEventListener listener : listeners)
        {
            Runnable r = new Runnable()
            {

                public void run()
                {
                    try
                    {
                        listener.onError(url, code);
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            };
            Thread t = new Thread(r);
            t.start();
        }
    }

    public void fireError(final Throwable e)
    {
        for (final SpiderEventListener listener : listeners)
        {
            Runnable r = new Runnable()
            {

                public void run()
                {
                    try
                    {
                        listener.onError(url, e);
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            };
            Thread t = new Thread(r);
            t.start();

        }
    }

    public void fireEventNtFormat(final String row, final Spider spider)
    {
        for (final SpiderEventListener listener : listeners)
        {
            Runnable r = new Runnable()
            {

                public void run()
                {
                    try
                    {
                        //listener.onTriple(suj, pred, obj,spider);
                        listener.onNTFormat(row);
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            };
            Thread t = new Thread(r);
            t.start();

        }
    }

    public void fireEventnewTriple(final URI suj, final URI pred, final String obj, final Spider spider, final String lang)
    {
        for (final SpiderEventListener listener : listeners)
        {
            Runnable r = new Runnable()
            {

                public void run()
                {
                    try
                    {
                        //listener.onTriple(suj, pred, obj,spider);
                        listener.onTriple(suj, pred, obj, spider, lang);
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            };
            Thread t = new Thread(r);
            t.start();

        }
    }

    public void fireOnStart(final URL url)
    {
        for (final SpiderEventListener listener : listeners)
        {
            Runnable r = new Runnable()
            {

                public void run()
                {
                    try
                    {
                        listener.onStart(url);
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            };
            Thread t = new Thread(r);
            t.start();

        }

    }

    public void fireOnEnd(final URL url)
    {
        for (final SpiderEventListener listener : listeners)
        {
            Runnable r = new Runnable()
            {

                public void run()
                {
                    try
                    {
                        listener.onEnd(url);
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            };
            Thread t = new Thread(r);
            t.start();

        }

    }

    public void fireVisit(final URI suj)
    {
        for (final SpiderEventListener listener : listeners)
        {
            Runnable r = new Runnable()
            {

                public void run()
                {
                    try
                    {
                        listener.visit(suj);
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            };
            Thread t = new Thread(r);
            t.start();

        }


    }

    public void onError(URL url, int error)
    {
    }

    public void onError(URL url, Throwable e)
    {
    }

    private void addProperty(URI suj, Element prop, String lang)
    {
        if (prop.getNamespaceURI() != null)
        {
            try
            {
                URI pred = new URI(prop.getNamespaceURI() + prop.getLocalName());
                if (prop.hasAttributeNS(RDF_SCHEMA_NAMESPACE, "resource"))
                {
                    String obj = prop.getAttributeNodeNS(RDF_SCHEMA_NAMESPACE, "resource").getValue();
                    String _lang = null;
                    if (prop.hasAttribute(XMLLANG))
                    {
                        prop.getAttribute(XMLLANG);
                    }
                    URI _newsuj = new URI(obj);
                    fireEventnewTriple(_newsuj, typeProp, pred.toString(), this, _lang);

                    fireEventnewTriple(suj, pred, obj, this, _lang);


                    URI _newUri = new URI(obj);
                    addOtherProperties(_newUri, prop);
                }
                else
                {
                    NodeList childs = prop.getChildNodes();
                    for (int ichild = 0; ichild < childs.getLength(); ichild++)
                    {
                        Node child = childs.item(ichild);
                        if (child.getLocalName() != null && child.getNamespaceURI().equals(RDF_SCHEMA_NAMESPACE) && child.getLocalName().equals("Description") && child instanceof Element)
                        {
                            Element description = (Element) child;
                            if (description.hasAttributeNS(RDF_SCHEMA_NAMESPACE, "about"))
                            {
                                String obj = description.getAttributeNS(RDF_SCHEMA_NAMESPACE, "about");
                                String _lang = null;
                                if (description.hasAttribute(XMLLANG))
                                {
                                    description.getAttribute(XMLLANG);
                                }

                                fireEventnewTriple(suj, pred, obj, this, _lang);
                                URI _newsuj = new URI(obj);
                                fireEventnewTriple(_newsuj, typeProp, pred.toString(), this, _lang);
                                addOtherProperties(_newsuj, description);
                            }
                        }
                        if (child instanceof Text)
                        {
                            Text text = (Text) child;
                            String data = text.getData();
                            if (data != null && data.indexOf("\n") == -1)
                            {
                                String obj = data;
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

                /*try
                {

                URI type = new URI(element.getNamespaceURI() + element.getLocalName());
                String _lang = element.getAttribute("xml:lang");
                fireEventnewTriple(suj, typeProp, type.toString(), this, _lang);
                NodeList childs = element.getChildNodes();
                for (int ichild = 0; ichild < childs.getLength(); ichild++)
                {
                Node node = childs.item(ichild);
                if (node instanceof Element)
                {
                addProperty(suj, (Element) node, _lang);
                }
                }
                }
                catch (URISyntaxException e)
                {
                fireError(e);

                }*/
            }
            else
            {
                NodeList childs = element.getChildNodes();
                for (int ichild = 0; ichild < childs.getLength(); ichild++)
                {
                    Node child = childs.item(ichild);
                    if (child.getLocalName() != null && child.getNamespaceURI().equals(RDF_SCHEMA_NAMESPACE) && child.getLocalName().equals("Description") && child instanceof Element)
                    {
                        Element description = (Element) child;
                        if (description.hasAttributeNS(RDF_SCHEMA_NAMESPACE, "about"))
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

    public void visit(URI suj)
    {
        try
        {
            URL newURL = suj.toURL();
            if (!SpiderManager.visited.contains(newURL))
            {
                Spider spider = new Spider(newURL);
                for (SpiderEventListener listener : listeners)
                {
                    spider.addSpiderListener(listener);
                }

                SpiderManager.addSpider(spider);
            }

        }
        catch (Exception e)
        {
            fireError(e);

        }
    }

    public void onStart(URL url)
    {
    }

    public void onEnd(URL url)
    {
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

    public void onNTFormat(String row)
    {
    }
}
