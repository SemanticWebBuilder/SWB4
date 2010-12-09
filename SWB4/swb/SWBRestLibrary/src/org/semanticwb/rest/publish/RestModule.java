/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.rest.publish;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.SWBUtils;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import static javax.xml.XMLConstants.W3C_XML_SCHEMA_NS_URI;
import static javax.xml.XMLConstants.W3C_XML_SCHEMA_INSTANCE_NS_URI;
import static org.semanticwb.rest.util.XMLConstants.*;
/**
 *
 * @author victor.lorenzana
 */
public abstract class RestModule
{

    
    private static final String APPLICATION_XML = "application/xml";
    protected final Map<String, ResourceModule> resourceModules = Collections.synchronizedMap(new HashMap<String, ResourceModule>());

    public void addResourceModule(String path, ResourceModule resourceModule)
    {
        resourceModules.put(path, resourceModule);
    }

    public void service(HttpServletRequest request, HttpServletResponse response, String servet, List<String> path, String basepath) throws IOException
    {
        if (path.isEmpty())
        {

            if (request.getParameterMap().isEmpty())
            {
                showWADL(request, response, basepath);
            }
            else
            {
                showData(request, response, basepath);
            }
        }
        else
        {
            String moduleId = path.get(0);
            if (resourceModules.containsKey(moduleId))
            {
                ResourceModule module = resourceModules.get(moduleId);
                basepath += "/" + path.remove(0);
                module.service(request, response, servet, path, basepath);
            }
            else
            {
                response.setStatus(404);
            }
        }
    }
    public abstract void showData(HttpServletRequest request, HttpServletResponse response, String basepath) throws IOException;

    protected void showWADL(HttpServletRequest request, HttpServletResponse response, String basepath) throws IOException
    {
        String version = "2009";
        if ("2006".equals(request.getParameter("version")))
        {
            version = "2006";
        }
        Document doc = getWADL(request, version, basepath);
        showDocument(response, doc);
    }

    protected Document getWADL(HttpServletRequest servletRequest, String version, String basePath)
    {
        String WADL_NS = WADL_NS_2009;
        String WADL_XSD_LOCATION = WADL_XSD_LOCATION_2009;
        if ("2006".equals(version))
        {
            WADL_NS = WADL_NS_2006;
            WADL_XSD_LOCATION = WADL_XSD_LOCATION_2006;
        }
        Document doc = SWBUtils.XML.getNewDocument();
        Element application = doc.createElementNS(WADL_NS, "application");
        doc.appendChild(application);

        application.setAttribute("xmlns:xsi", W3C_XML_SCHEMA_INSTANCE_NS_URI);
        application.setAttribute("xmlns:xsd", W3C_XML_SCHEMA_NS_URI);
        addAditionalNamespaces(application);


        Attr attr = doc.createAttribute("xmlns");
        attr.setValue(WADL_NS);
        application.setAttributeNode(attr);

        Attr schemaLocation = doc.createAttributeNS(W3C_XML_SCHEMA_INSTANCE_NS_URI, "schemaLocation");
        schemaLocation.setValue(WADL_NS + " " + WADL_XSD_LOCATION);
        schemaLocation.setPrefix("xsi");
        application.setAttributeNodeNS(schemaLocation);

        Element grammars = doc.createElementNS(WADL_NS, "grammars");
        application.appendChild(grammars);
        addIncludes(grammars, servletRequest);

        Element resources = doc.createElementNS(WADL_NS, "resources");
        resources.setAttribute("base", basePath);
        application.appendChild(resources);

        for (String path : resourceModules.keySet())
        {
            ResourceModule resourceModule = resourceModules.get(path);
            Element resource = doc.createElementNS(WADL_NS, "resource");
            resources.appendChild(resource);
            resource.setAttribute("path", path);
            resource.setAttribute("id", resourceModule.getId());
            resourceModule.addMethods(resource);
        }

        return doc;
    }

    private void showDocument(HttpServletResponse response, Document doc) throws IOException
    {
        PrintWriter out = response.getWriter();
        String charset = Charset.defaultCharset().name();
        response.setContentType(APPLICATION_XML + "; charset=" + charset);
        String xml = SWBUtils.XML.domToXml(doc, charset, true);
        out.print(xml);
        out.close();
    }

    protected abstract void addAditionalNamespaces(Element application);

    protected abstract void addIncludes(Element grammars, HttpServletRequest request);
}
