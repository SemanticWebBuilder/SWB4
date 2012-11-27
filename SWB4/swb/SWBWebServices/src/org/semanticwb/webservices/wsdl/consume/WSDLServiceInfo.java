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

import java.io.StringReader;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
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
public class WSDLServiceInfo implements ServiceInfo
{

    HashMap<String, WSDLService> services = new HashMap<String, WSDLService>();
    private static final Logger log = SWBUtils.getLogger(WSDLServiceInfo.class);
    private final Document doc;
    private final Document[] schemas;
    private final org.jdom.Document jdom;

    public WSDLServiceInfo(Document doc,URL url) throws ServiceException
    {
        this.doc = doc;
        extractIncludes(doc,url);       

        schemas = XMLDocumentUtil.getSchemas(doc);
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
        this.jdom = _jdom;
        fill(doc, _jdom);
    }

    private void importInLine(Document docinclude, Element grammars)
    {
        Element rootElement = docinclude.getDocumentElement();
        Node importedNode = grammars.getOwnerDocument().importNode(rootElement, true);
        grammars.appendChild(importedNode);
    }
    private void extractIncludes(Document doc,URL url) throws ServiceException
    {
        NodeList nodesgrammars = doc.getElementsByTagNameNS(XMLDocumentUtil.WSDL_NAMESPACE, "types");
        for (int i = 0; i < nodesgrammars.getLength(); i++)
        {
            if (nodesgrammars.item(i) instanceof Element)
            {
                Element grammars = (Element) nodesgrammars.item(i);
                ArrayList<Document> includes = new ArrayList<Document>();
                ArrayList<Element> toDelete = new ArrayList<Element>();
                NodeList nodes = grammars.getElementsByTagNameNS(XMLDocumentUtil.SCHEMA_NAMESPACE, "import");
                if (nodes.getLength() == 0)
                {
                    nodes = grammars.getElementsByTagNameNS(XMLDocumentUtil.SCHEMA_2001_NAMESPACE, "import");
                }
                for (int j = 0; j < nodes.getLength(); j++)
                {
                    if (nodes.item(j) instanceof Element)
                    {
                        Element include = (Element) nodes.item(j);
                        toDelete.add((Element) include.getParentNode());
                        String spath = include.getAttribute("schemaLocation");
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

    private void fill(Document doc, org.jdom.Document _jdom) throws ServiceException
    {
        NodeList _services = doc.getElementsByTagNameNS(XMLDocumentUtil.WSDL_NAMESPACE, "service");
        for (int k = 0; k < _services.getLength(); k++)
        {
            Element _service = (Element) _services.item(k);
            String name = _service.getAttribute("name");
            WSDLService service = new WSDLService(_service, this);
            services.put(name, service);
        }

    }

    public static boolean isWSDL(Document doc)
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
        if (namespace.equals(XMLDocumentUtil.WSDL_NAMESPACE) && XMLDocumentUtil.validate(doc, namespace))
        {
            return true;

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
        return services.values().toArray(new Service[services.size()]);
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
