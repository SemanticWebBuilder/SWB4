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
package org.semanticwb.webservices.wadl.consume;

import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import org.jdom.input.SAXBuilder;
import org.json.JSONObject;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.webservices.Service;
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
public class WADLServiceInfo implements ServiceInfo
{

    private static final Logger log = SWBUtils.getLogger(WADLServiceInfo.class);
    private final HashMap<String, WADLResource> resources = new HashMap<String, WADLResource>();
    private final String WADL_NS;
    private URL resourcesBasePath;
    

    private final Document doc;
    private final Document[] schemas;
    private final org.jdom.Document jdom;
    public WADLServiceInfo(Document doc, URL url) throws ServiceException
    {
        this.doc=doc;
        WADL_NS = getNamespace(doc);
        extractIncludes(url, this.doc);
        schemas=XMLDocumentUtil.getSchemas(doc);
        String xml = SWBUtils.XML.domToXml(doc, "utf-8", true);
        StringReader r = new StringReader(xml);
        SAXBuilder builder = new SAXBuilder();
        org.jdom.Document _jdom = null;
        try
        {
            _jdom = builder.build(r);
        }
        catch (Exception e)
        {
            log.error(e);
            throw new ServiceException(e);
        }
        this.jdom=_jdom;
        
        fill(doc, url);
    }

    private String getNamespace(Document doc)
    {
        String attname = "";
        String prefix = doc.getDocumentElement().getPrefix();
        if (prefix == null)
        {
            attname = "xmlns";
        }
        else
        {
            attname = "xmlns:" + prefix;
        }

        return doc.getDocumentElement().getAttribute(attname);
        
    }

    private void fill(Document doc, URL url) throws ServiceException
    {
        
        NodeList lresources = doc.getElementsByTagNameNS(WADL_NS, "resources");
        for (int i = 0; i < lresources.getLength(); i++)
        {
            if (lresources.item(i) instanceof Element)
            {
                Element eresources = (Element) lresources.item(i);
                NodeList childs = eresources.getChildNodes();
                for (int j = 0; j < childs.getLength(); j++)
                {
                    if (childs.item(j) instanceof Element && ((Element) childs.item(j)).getNamespaceURI().equals(WADL_NS) && ((Element) childs.item(j)).getLocalName().equals("resource"))
                    {
                        Element eresource = ((Element) childs.item(j));
                        String base = eresource.getAttribute("base");
                        try
                        {
                            URI baseuri = new URI(base);
                            if (baseuri.isAbsolute())
                            {
                                resourcesBasePath = baseuri.toURL();
                            }
                            else
                            {
                                URI tempbase = url.toURI();
                                if (!tempbase.toString().endsWith("/"))
                                {
                                    String newpath = tempbase.toString() + "/";
                                    tempbase = new URI(newpath);
                                }
                                resourcesBasePath = tempbase.resolve(baseuri).normalize().toURL();
                            }
                            String xml = SWBUtils.XML.domToXml(doc, "utf-8", true);
                            StringReader r = new StringReader(xml);
                            SAXBuilder builder = new SAXBuilder();
                            org.jdom.Document _jdom = null;
                            try
                            {
                                _jdom = builder.build(r);
                            }
                            catch (Exception e)
                            {
                                log.error(e);
                                throw new ServiceException(e);
                            }

                            WADLResource resource = new WADLResource(eresource, resourcesBasePath, this);
                            String id = eresource.getAttribute("id");
                            //Resource resource = Resource.createResourceInfo(eresource, resourcesBasePath, this);
                            this.resources.put(id, resource);

                        }
                        catch (MalformedURLException mfue)
                        {
                            throw new ServiceException(mfue);
                        }
                        catch (URISyntaxException e)
                        {
                            throw new ServiceException(e);
                        }
                    }
                }
            }
        }
    }

    private void extractIncludes(URL url,Document doc) throws ServiceException
    {
        NodeList nodesgrammars = doc.getElementsByTagNameNS(WADL_NS, "grammars");
        for (int i = 0; i < nodesgrammars.getLength(); i++)
        {
            if (nodesgrammars.item(i) instanceof Element)
            {
                Element grammars = (Element) nodesgrammars.item(i);
                ArrayList<Document> includes = new ArrayList<Document>();
                ArrayList<Element> toDelete = new ArrayList<Element>();
                NodeList nodes = grammars.getElementsByTagNameNS(WADL_NS, "include");
                for (int j = 0; j < nodes.getLength(); j++)
                {
                    if (nodes.item(j) instanceof Element)
                    {
                        Element include = (Element) nodes.item(j);
                        String spath = include.getAttribute("href");
                        URL path = url;
                        try
                        {
                            URI uriPath = new URI(spath);
                            if (!uriPath.isAbsolute())
                            {
                                URI base = url.toURI();
                                if (!url.toString().endsWith("/"))
                                {
                                    String newpath = url.toString() + "/";
                                    base = new URI(newpath);
                                }
                                URI temp = base.resolve(uriPath);
                                path = temp.normalize().toURL();
                            }
                            else
                            {
                                path = uriPath.toURL();
                            }
                        }
                        catch (Exception e)
                        {                            
                            log.debug(e);
                        }
                        try
                        {
                            Document docinclude = XMLDocumentUtil.getDocument(path);
                            includes.add(docinclude);
                        }
                        catch (Exception e)
                        {
                            throw new ServiceException(e);
                        }

                    }
                }

                for (Element e : toDelete)
                {
                    grammars.removeChild(e);
                }
                for (Document includedoc : includes)
                {
                    importInLine(includedoc, grammars);
                }

            }
        }
    }
    private void importInLine(Document docinclude, Element grammars)
    {
        Element rootElement = docinclude.getDocumentElement();
        Node importedNode = grammars.getOwnerDocument().importNode(rootElement, true);
        grammars.appendChild(importedNode);
    }

    public static boolean isWADL(Document doc)
    {
        String attname = "";
        String prefix = doc.getDocumentElement().getPrefix();
        if (prefix == null)
        {
            attname = "xmlns";
        }
        else
        {
            attname = "xmlns:" + prefix;
        }

        String namespace = doc.getDocumentElement().getAttribute(attname);
        if ((namespace.equals(XMLDocumentUtil.WADL_NS_2006) || namespace.equals(XMLDocumentUtil.WADL_NS_2009)))
        {
            if (XMLDocumentUtil.validate(doc,namespace))
            {
                return true;
            }
        }
        return false;
        

    }

    @Override
    public Document toDom(JSONObject object)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public JSONObject toJSON(Document object)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public JSONObject execute(JSONObject object)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Service[] getServices()
    {
        ArrayList<Service> services = new ArrayList<Service>();
        for (WADLResource resource : this.resources.values())
        {
            services.add(resource);
            services.addAll(Arrays.asList(resource.getServices()));
        }
        return services.toArray(new Service[services.size()]);
    }

    @Override
    public Document getDocument()
    {
        return doc;
    }

    @Override
    public Document[] getSchemas()
    {
        return schemas;
    }

    @Override
    public org.jdom.Document getJDom()
    {
        return jdom;
    }
}
