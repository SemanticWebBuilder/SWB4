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
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import org.jdom.input.SAXBuilder;
import org.jdom.output.DOMOutputter;
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
public class Spider implements SpiderEventListener
{

    public static final String DOCTYPE_RFDA = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML+RDFa 1.0//EN\" \"http://www.w3.org/MarkUp/DTD/xhtml-rdfa-1.dtd\">";
    public static final String OWL_SCHEMA_NAMESPACE = "http://www.w3.org/2002/07/owl#";
    public static final String RDF_SCHEMA_NAMESPACE = "http://www.w3.org/1999/02/22-rdf-syntax-ns#";
    public static final String RDFS_SCHEMA_NAMESPACE = "http://www.w3.org/2000/01/rdf-schema#";
    private static URI typeProp;
    public static final Set<URL> visited = Collections.synchronizedSet(new HashSet<URL>());
    public static HashSet<Spider> spiders = new HashSet<Spider>();
    public static HashSet<Spider> predicados = new HashSet<Spider>();
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
    private HashSet<SpiderEventListener> events = new HashSet<SpiderEventListener>();
    private URL url;

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

    public void addTripleEvent(SpiderEventListener event)
    {
        events.add(event);
    }

    public void removeTripleEvent(SpiderEventListener event)
    {
        events.remove(event);
    }

    public void start()
    {
        this.events.add(this);
        if (!visited.contains(url))
        {
            visited.add(url);
            fireOnStart(url);
            DocumentInfo docInfo = getContent(url);
            if (docInfo != null)
            {
                if (docInfo.contentType.equalsIgnoreCase("application/rdf+xml"))
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
                        e.printStackTrace();
                    }
                }
                else
                {
                    System.out.println("docInfo.contentType: " + docInfo.contentType);
                }
            }
            fireOnEnd(url);
            for(Spider spider : predicados)
            {
                spider.start();
            }
            for(Spider spider : spiders)
            {
                spider.start();
            }
        }
        


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
                throw new SpiderException(e);
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

    public boolean isVisit(URI pred)
    {
        for (String value : visit)
        {
            if (pred.toString().equals(value.toString()))
            {
                return true;
            }
        }
        return false;
    }

    public void onTriple(URI suj, URI pred, String obj, URL url)
    {

        try
        {
            URL newURL = pred.toURL();
            if (!visited.contains(newURL))
            {
                Spider spider = new Spider(newURL);
                for(SpiderEventListener listener : events)
                {
                    spider.addTripleEvent(listener);
                }
                predicados.add(spider);
            }

        }
        catch (Exception e)
        {
            fireError(e);

        }
        if (isVisit(pred))
        {
            try
            {
                Spider spider = new Spider(new URL(obj));
                for (SpiderEventListener listener : events)
                {
                    spider.addTripleEvent(listener);
                }
                spiders.add(spider);
            }
            catch (MalformedURLException mfe)
            {
                fireError(mfe);

            }
        }
    }

    private void fireError(final int code)
    {
        for (final SpiderEventListener listener : events)
        {
            Runnable r = new Runnable()
            {

                public void run()
                {
                    listener.onError(url, code);
                }
            };
            Thread t = new Thread(r);
            t.start();
        }
    }

    public void fireError(final Throwable e)
    {
        for (final SpiderEventListener listener : events)
        {
            Runnable r = new Runnable()
            {

                public void run()
                {
                    listener.onError(url, e);
                }
            };
            Thread t = new Thread(r);
            t.start();

        }
    }

    public void fireEventnewTriple(final URI suj, final URI pred, final String obj)
    {
//        for (final TripleEventListener listener : events)
//        {
//            Runnable r = new Runnable()
//            {
//
//                public void run()
//                {
//                    listener.onTriple(suj, pred, obj, url);
//                }
//            };
//            Thread t = new Thread(r);
//            t.start();
//
//        }

        for (final SpiderEventListener listener : events)
        {
            listener.onTriple(suj, pred, obj, url);
        }
    }

    public void fireOnStart(URL url)
    {
        for (final SpiderEventListener listener : events)
        {
            listener.onStart(this.url);
        }
    }

    public void fireOnEnd(URL url)
    {
        for (final SpiderEventListener listener : events)
        {
            try
            {
                listener.onEnd(this.url);
            }
            catch(Exception e)
            {
                
            }
        }
    }
    public void fireVisit(final URI suj)
    {
//        for (final TripleEventListener listener : events)
//        {
//            Runnable r = new Runnable()
//            {
//
//                public void run()
//                {
//                    listener.onTriple(suj, pred, obj, url);
//                }
//            };
//            Thread t = new Thread(r);
//            t.start();
//
//        }

        for (final SpiderEventListener listener : events)
        {
            try
            {
                listener.visit(suj);
            }
            catch(Exception e)
            {
                
            }
        }
    }

    public void fireEventnewTripleAndFollow(final URI suj, final URI pred, final URI obj)
    {
//        for (final TripleEventListener listener : events)
//        {
//            Runnable r = new Runnable()
//            {
//
//                public void run()
//                {
//                    listener.onTriple(suj, pred, obj, url);
//                }
//            };
//            Thread t = new Thread(r);
//            t.start();
//
//        }

        for (final SpiderEventListener listener : events)
        {
            try
            {
                listener.onTripleAndFollow(suj, pred, obj, url);
            }
            catch(Exception e)
            {
                
            }
        }
    }

    public void onError(URL url, int error)
    {
    }

    public void onError(URL url, Throwable e)
    {
    }

    private void addProperty(URI suj, Element prop)
    {
        if (prop.getNamespaceURI() != null)
        {
            try
            {
                URI pred = new URI(prop.getNamespaceURI() + prop.getLocalName());
                if (prop.hasAttributeNS(RDF_SCHEMA_NAMESPACE, "resource"))
                {
                    String obj = prop.getAttributeNodeNS(RDF_SCHEMA_NAMESPACE, "resource").getValue();

                    URI _newsuj = new URI(obj);
                    fireEventnewTriple(_newsuj, typeProp, pred.toString());

                    fireEventnewTriple(suj, pred, obj);


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
                                fireEventnewTriple(suj, pred, obj);
                                URI _newsuj = new URI(obj);
                                fireEventnewTriple(_newsuj, typeProp, pred.toString());
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
                                fireEventnewTriple(suj, pred, obj);
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
                try
                {

                    URI type = new URI(element.getNamespaceURI() + element.getLocalName());
                    fireEventnewTriple(suj, typeProp, type.toString());
                    NodeList childs = element.getChildNodes();
                    for (int ichild = 0; ichild < childs.getLength(); ichild++)
                    {
                        Node node = childs.item(ichild);
                        if (node instanceof Element)
                        {
                            addProperty(suj, (Element) node);
                        }
                    }
                }
                catch (URISyntaxException e)
                {
                    fireError(e);

                }
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
                            try
                            {
                                suj = new URI(description.getAttributeNS(RDF_SCHEMA_NAMESPACE, "about"));
                                URI type = new URI(element.getNamespaceURI() + element.getLocalName());
                                fireEventnewTriple(suj, typeProp, type.toString());
                            }
                            catch (URISyntaxException e)
                            {
                                fireError(e);
                            }

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
                addProperty(suj, prop);
            }
        }
    }

    
    public void onTripleAndFollow(URI suj, URI pred, URI obj, URL url)
    {
        try
        {
            URL newURL = obj.toURL();
            if (!visited.contains(newURL))
            {
                Spider spider = new Spider(newURL);
                for(SpiderEventListener listener : events)
                {
                    spider.addTripleEvent(listener);
                }
                predicados.add(spider);
            }

        }
        catch (Exception e)
        {
            fireError(e);

        }

        try
        {
            URL newURL = pred.toURL();
            if (!visited.contains(newURL))
            {
                Spider spider = new Spider(newURL);
                for(SpiderEventListener listener : events)
                {
                    spider.addTripleEvent(listener);
                }
                spiders.add(spider);
            }

        }
        catch (Exception e)
        {
            fireError(e);

        }

    }

    public void visit(URI suj)
    {
        try
        {
            URL newURL = suj.toURL();
            if (!visited.contains(newURL))
            {
                Spider spider = new Spider(newURL);
                for(SpiderEventListener listener : events)
                {
                    spider.addTripleEvent(listener);
                }
                spiders.add(spider);
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
        if(obj instanceof Spider)
        {
            Spider tmp=(Spider)obj;
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
    /*public void onTriple(URI suj, URI pred, String obj, URL url)
    {
    throw new UnsupportedOperationException("Not supported yet.");
    }*/


}
