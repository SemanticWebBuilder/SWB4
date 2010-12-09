/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.rest;

import java.lang.reflect.Modifier;
import java.sql.Timestamp;
import java.util.Date;
import org.semanticwb.model.GenericObject;
import org.semanticwb.model.base.GenericObjectBase;
import org.json.JSONException;
import org.json.JSONObject;
import org.semanticwb.platform.SemanticLiteral;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import org.w3c.dom.Attr;
import org.w3c.dom.Text;
import org.semanticwb.platform.SemanticObject;
import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.WebSite;
import org.semanticwb.platform.SemanticClass;
import org.semanticwb.platform.SemanticProperty;
import org.semanticwb.platform.SemanticVocabulary;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import static javax.xml.XMLConstants.W3C_XML_SCHEMA_NS_URI;

/**
 *
 * @author victor.lorenzana
 */
public final class SemanticClassPublisher extends RestModule
{

    private static final String XSD_ANYURI = "xsd:anyURI";
    private static final String XSD_BOOLEAN = "xsd:boolean";
    private static final String XSD_BYTE = "xsd:byte";
    private static final String XSD_DATE = "xsd:date";
    private static final String XSD_DATETIME = "xsd:datetime";
    private static final String XSD_DOUBLE = "xsd:double";
    private static final String XSD_FLOAT = "xsd:float";
    private static final String XSD_INT = "xsd:int";
    private static final String XSD_LONG = "xsd:long";
    private static final String XSD_SHORT = "xsd:short";
    private static final String NAME = "name";
    private static final String QUERY = "query";
    private static final String REQUIRED = "required";
    private static final String STATUS = "status";
    private static final String STYLE = "style";
    private static final String TYPE = "type";
    private static final String VALUE = "value";
    public static final String XLINK_NS = "http://www.w3.org/1999/xlink";
    private static final String APPLICATION_JSON = "application/json";
    private static final String APPLICATION_XML = "application/xml";
    public static final String REST_RESOURCES_2010 = "http://www.semanticwb.org/rest/2010";
    private static final String REST_MODELURI = "rest:modeluri";
    private static final String REST_CLASSURI = "rest:classuri";
    private static final String REST_URI = "rest:uri";
    public static final String REST_RESOURCE_PREFIX = "swbrest";
    public static final String XSD_STRING = "xsd:string";
    private static final String REST_ID = "rest:id";
    private static final Logger log = SWBUtils.getLogger(SemanticClassPublisher.class);
    private final Set<SemanticClass> classes = Collections.synchronizedSet(new HashSet<SemanticClass>());

    class DeleteModule extends MethodModuleBase
    {

        private final SemanticClass clazz;

        public DeleteModule(SemanticClass clazz)
        {
            this.clazz = clazz;
        }

        public HTTPMethod getHTTPMethod()
        {
            return HTTPMethod.DELETE;
        }

        public String getId()
        {
            return "delete";
        }

        public void addParameters(Element method)
        {
            Document doc = method.getOwnerDocument();
            String WADL_NS = method.getNamespaceURI();
            Element request = doc.createElementNS(WADL_NS, "request");
            method.appendChild(request);

            Element param = doc.createElementNS(WADL_NS, "param");
            param.setAttribute(NAME, "uri");
            param.setAttribute(STYLE, QUERY);
            param.setAttribute(TYPE, XSD_STRING);
            param.setAttribute(REQUIRED, "true");
            request.appendChild(param);
            MethodModuleBase.configureCommonsElements(method, request, WADL_NS, REST_RESOURCE_PREFIX + ":Deleted");
        }

        public void execute(HttpServletRequest request, HttpServletResponse response, String basepath) throws IOException
        {
            if (request.getParameter("uri") != null)
            {
                String uri = request.getParameter("uri");
                uri = fixURI(uri);
                SemanticObject obj = SemanticObject.createSemanticObject(uri);
                if (obj != null)
                {
                    SemanticClass clazzObject = obj.getSemanticClass();
                    if (!clazzObject.equals(clazz))
                    {
                        response.setStatus(400);
                        showError(request, response, "The object was not found");
                        return;
                    }
                    uri = obj.getShortURI();
                    obj.remove();
                    showDeleted(request, response, true);
                }
                else
                {
                    showDeleted(request, response, false);
                    return;
                }
            }
            else
            {
                showError(request, response, "The parameter uri was not found");
                return;

            }
        }
    }

    class PutModule extends MethodModuleBase
    {

        private final SemanticClass clazz;

        public PutModule(SemanticClass clazz)
        {
            this.clazz = clazz;
        }

        public HTTPMethod getHTTPMethod()
        {
            return HTTPMethod.PUT;
        }

        public String getId()
        {
            return "update";
        }

        public void addParameters(Element method)
        {
            Document doc = method.getOwnerDocument();
            String WADL_NS = method.getNamespaceURI();
            Element request = doc.createElementNS(WADL_NS, "request");
            method.appendChild(request);
            Element param = doc.createElementNS(WADL_NS, "param");
            param.setAttribute(NAME, REST_URI);
            param.setAttribute(STYLE, QUERY);
            param.setAttribute(TYPE, XSD_ANYURI);

            request.appendChild(param);

            Iterator<SemanticProperty> props = clazz.listProperties();
            while (props.hasNext())
            {
                SemanticProperty prop = props.next();
                if (!prop.hasInverse())
                {
                    if (prop.isDataTypeProperty())
                    {
                        String type = XSD_STRING;
                        if (prop.isBoolean())
                        {
                            type = SemanticVocabulary.XMLS_BOOLEAN;
                        }
                        else if (prop.isBinary())
                        {
                            type = SemanticVocabulary.XMLS_BASE64BINARY;
                        }
                        else if (prop.isByte())
                        {
                            type = SemanticVocabulary.XMLS_BYTE;
                        }
                        else if (prop.isDate())
                        {
                            type = SemanticVocabulary.XMLS_DATE;
                        }
                        else if (prop.isDateTime())
                        {
                            type = SemanticVocabulary.XMLS_DATETIME;
                        }
                        else if (prop.isDouble())
                        {
                            type = SemanticVocabulary.XMLS_DOUBLE;
                        }
                        else if (prop.isFloat())
                        {
                            type = SemanticVocabulary.XMLS_FLOAT;
                        }
                        else if (prop.isInt())
                        {
                            type = SemanticVocabulary.XMLS_INT;
                        }
                        else if (prop.isLong())
                        {
                            type = SemanticVocabulary.XMLS_LONG;
                        }
                        else if (prop.isShort())
                        {
                            type = SemanticVocabulary.XMLS_SHORT;
                        }
                        type = type.replace(W3C_XML_SCHEMA_NS_URI + "#", "xsd:");
                        param = doc.createElementNS(WADL_NS, "param");
                        param.setAttribute(NAME, prop.getName());
                        param.setAttribute(STYLE, QUERY);
                        param.setAttribute(TYPE, type);
                        if (prop.getName().startsWith("has"))
                        {
                            param.setAttribute("repeating", "true");
                        }
                        request.appendChild(param);
                    }
                    else
                    {
                        param = doc.createElementNS(WADL_NS, "param");
                        param.setAttribute(NAME, prop.getName());
                        param.setAttribute(STYLE, QUERY);
                        param.setAttribute(TYPE, XSD_ANYURI);
                        if (prop.getName().startsWith("has"))
                        {
                            param.setAttribute("repeating", "true");
                        }
                        request.appendChild(param);
                    }
                }

            }
            configureCommonsElements(method, request, WADL_NS, REST_RESOURCE_PREFIX + ":Updated");
        }

        public void execute(HttpServletRequest request, HttpServletResponse response, String basepath) throws IOException
        {
            String uri = request.getParameter(REST_URI);
            if (uri == null || uri.equals(""))
            {
                showError(request, response, "The uri parameter was not found");
            }
            else
            {
                uri = fixURI(uri);
                SemanticObject obj = SemanticObject.createSemanticObject(uri);
                if (obj != null)
                {
                    if (!obj.getSemanticClass().equals(clazz))
                    {
                        response.setStatus(400);
                        showError(request, response, "The object was not found");
                        return;
                    }
                    try
                    {
                        updateProperties(request, obj);
                        showUpdated(request, response, true);
                    }
                    catch (Exception e)
                    {
                        response.setStatus(400);
                        showError(request, response, e.getMessage());
                        return;
                    }
                }
                else
                {
                    showUpdated(request, response, false);
                }
            }
        }
    }

    class PostModule extends MethodModuleBase
    {

        private final SemanticClass clazz;

        public PostModule(SemanticClass clazz)
        {
            this.clazz = clazz;
        }

        public void addParameters(Element method)
        {
            Document doc = method.getOwnerDocument();
            String WADL_NS = method.getNamespaceURI();
            Element request = doc.createElementNS(WADL_NS, "request");
            method.appendChild(request);




            Iterator<SemanticProperty> props = clazz.listProperties();
            while (props.hasNext())
            {
                SemanticProperty prop = props.next();
                if (!prop.hasInverse())
                {
                    if (prop.isDataTypeProperty())
                    {
                        String type = XSD_STRING;
                        if (prop.isBoolean())
                        {
                            type = SemanticVocabulary.XMLS_BOOLEAN;
                        }
                        else if (prop.isBinary())
                        {
                            type = SemanticVocabulary.XMLS_BASE64BINARY;
                        }
                        else if (prop.isByte())
                        {
                            type = SemanticVocabulary.XMLS_BYTE;
                        }
                        else if (prop.isDate())
                        {
                            type = SemanticVocabulary.XMLS_DATE;
                        }
                        else if (prop.isDateTime())
                        {
                            type = SemanticVocabulary.XMLS_DATETIME;
                        }
                        else if (prop.isDouble())
                        {
                            type = SemanticVocabulary.XMLS_DOUBLE;
                        }
                        else if (prop.isFloat())
                        {
                            type = SemanticVocabulary.XMLS_FLOAT;
                        }
                        else if (prop.isInt())
                        {
                            type = SemanticVocabulary.XMLS_INT;
                        }
                        else if (prop.isLong())
                        {
                            type = SemanticVocabulary.XMLS_LONG;
                        }
                        else if (prop.isShort())
                        {
                            type = SemanticVocabulary.XMLS_SHORT;
                        }
                        type = type.replace(W3C_XML_SCHEMA_NS_URI + "#", "xsd:");
                        Element param = doc.createElementNS(WADL_NS, "param");
                        param.setAttribute(NAME, prop.getName());
                        param.setAttribute(STYLE, QUERY);
                        param.setAttribute(TYPE, type);
                        if (prop.getName().startsWith("has"))
                        {
                            param.setAttribute("repeating", "true");
                        }
                        request.appendChild(param);
                    }
                    else
                    {
                        Element param = doc.createElementNS(WADL_NS, "param");
                        param.setAttribute(NAME, prop.getName());
                        param.setAttribute(STYLE, QUERY);
                        param.setAttribute(TYPE, XSD_ANYURI);
                        if (prop.getName().startsWith("has"))
                        {
                            param.setAttribute("repeating", "true");
                        }
                        request.appendChild(param);
                    }
                }

            }
//            Element param = doc.createElementNS(WADL_NS, "param");
//            param.setAttribute(NAME, REST_CLASSURI);
//            param.setAttribute(STYLE, QUERY);
//            param.setAttribute("fixed", clazz.getURI());
//            param.setAttribute(TYPE, XSD_STRING);
//            param.setAttribute(REQUIRED, "true");
//            request.appendChild(param);

            Element param = doc.createElementNS(WADL_NS, "param");
            param.setAttribute(NAME, REST_MODELURI);
            param.setAttribute(STYLE, QUERY);
            param.setAttribute(TYPE, XSD_STRING);
            param.setAttribute(REQUIRED, "true");
            request.appendChild(param);

            Iterator<WebSite> sites = WebSite.ClassMgr.listWebSites();
            while (sites.hasNext())
            {
                WebSite site = sites.next();
                if (!(site.getURI().equals(SWBContext.getAdminWebSite().getURI()) || site.getURI().equals(SWBContext.getGlobalWebSite().getURI())))
                {
                    Element option = doc.createElementNS(WADL_NS, "option");
                    option.setAttribute(VALUE, site.getShortURI());
                    param.appendChild(option);
                }
            }

            if (!clazz.isAutogenId())
            {
                param = doc.createElementNS(WADL_NS, "param");
                param.setAttribute(NAME, REST_ID);
                param.setAttribute(STYLE, QUERY);
                param.setAttribute(TYPE, XSD_STRING);
                param.setAttribute(REQUIRED, "true");
                request.appendChild(param);
            }

            configureCommonsElements(method, request, WADL_NS, REST_RESOURCE_PREFIX + ":Created");
        }

        public HTTPMethod getHTTPMethod()
        {
            return HTTPMethod.POST;
        }

        public String getId()
        {
            return "add";
        }

        public void execute(HttpServletRequest request, HttpServletResponse response, String basepath) throws IOException
        {
            String modeluri = request.getParameter(REST_MODELURI);
            if (modeluri == null)
            {
                response.setStatus(400);
                showError(request, response, "The parameter modeluri is required");
                return;
            }
            if (!clazz.isAutogenId())
            {
                if (request.getParameter("id") == null)
                {
                    response.setStatus(400);
                    showError(request, response, "The parameter id is required");
                    return;
                }
            }
            modeluri = fixURI(modeluri);
            SemanticObject objmodel = SemanticObject.createSemanticObject(modeluri);
            if (objmodel == null)
            {
                response.setStatus(400);
                showError(request, response, "The model " + request.getParameter(REST_MODELURI) + " was not found");
                return;
            }
            if (!objmodel.getSemanticClass().isSWBModel())
            {
                response.setStatus(400);
                showError(request, response, "The object " + request.getParameter(REST_MODELURI) + " is not a model");
                return;
            }

            GenericObject model = (GenericObjectBase) objmodel.createGenericInstance();
            if (!(model instanceof WebSite))
            {
                response.setStatus(400);
                showError(request, response, "The model " + request.getParameter(REST_MODELURI) + " was not found");
                return;
            }
            WebSite site = (WebSite) model;
            if (site.getURI().equals(SWBContext.getAdminWebSite().getURI()) || site.getURI().equals(SWBContext.getGlobalWebSite().getURI()))
            {
                response.setStatus(400);
                showError(request, response, "The model " + request.getParameter(REST_MODELURI) + " was not found");
                return;
            }
            String id = null;
            if (clazz.isAutogenId())
            {
                id = String.valueOf(site.getSemanticObject().getModel().getCounter(clazz));
            }
            else
            {
                id = request.getParameter(REST_ID);
                if (id == null)
                {
                    response.setStatus(400);
                    showError(request, response, "The object parameter rest:id was not found");
                    return;
                }
                SemanticObject objtest = site.getSemanticObject().getModel().createSemanticObjectById(id, clazz);
                if (objtest != null)
                {
                    response.setStatus(400);
                    showError(request, response, "The parameter rest:id is invalid, the id already exists");
                    return;
                }
            }
            GenericObject newobj = site.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, clazz), clazz);
            // update properties
            try
            {
                updateProperties(request, newobj.getSemanticObject());
                showCreted(request, response, newobj.getURI());
            }
            catch (Exception e)
            {
                newobj.getSemanticObject().remove();
                response.setStatus(400);
                showError(request, response, e.getMessage());
                return;
            }
        }
    }

    class GetMethodModule extends MethodModuleBase
    {

        private final SemanticClass clazz;

        public GetMethodModule(SemanticClass clazz)
        {
            this.clazz = clazz;
        }

        public void addParameters(Element method)
        {
            Document doc = method.getOwnerDocument();
            String WADL_NS = method.getNamespaceURI();
            Element request = doc.createElementNS(WADL_NS, "request");
            method.appendChild(request);

            Element param = doc.createElementNS(WADL_NS, "param");
            param.setAttribute(NAME, "uri");
            param.setAttribute(STYLE, QUERY);
            param.setAttribute(TYPE, XSD_STRING);
            param.setAttribute(REQUIRED, "true");
            request.appendChild(param);
            configureCommonsElements(method, request, WADL_NS, clazz.getPrefix() + ":" + clazz.getName());
        }

        public HTTPMethod getHTTPMethod()
        {
            return HTTPMethod.GET;
        }

        public String getId()
        {
            return "get";
        }

        public void execute(HttpServletRequest request, HttpServletResponse response, String basepath) throws IOException
        {
            String uri = request.getParameter("uri");
            if (uri == null || uri.trim().equals(""))
            {
                response.setStatus(400);
                showError(request, response, "The parameter uri was not found");
            }
            uri = fixURI(uri);
            SemanticObject obj = SemanticObject.createSemanticObject(uri);
            if (obj != null)
            {
                SemanticClass clazzObject = obj.getSemanticClass();
                if (!clazzObject.equals(clazz))
                {
                    response.setStatus(400);
                    showError(request, response, "The object was not found");
                    return;
                }
                showObject(request, response, obj, basepath);
            }
            else
            {
                response.setStatus(400);
                showError(request, response, "The objct with uri " + request.getParameter("uri") + " was not found");
            }
        }
    }

    class SemanticResourceModule extends ResourceModule
    {

        private final SemanticClass clazz;

        public SemanticResourceModule(SemanticClass clazz)
        {
            this.clazz = clazz;
        }

        @Override
        public String getId()
        {
            return clazz.getPrefix() + "_" + clazz.getName();
        }
    }

    class MethodModule extends MethodModuleBase
    {

        private final java.lang.reflect.Method m;
        private final SemanticClass clazz;
        public MethodModule(java.lang.reflect.Method m,SemanticClass clazz)
        {
            this.m = m;
            this.clazz=clazz;
        }

        public String getId()
        {
            return m.getName();
        }

        public HTTPMethod getHTTPMethod()
        {
            return HTTPMethod.GET;
        }

        public void addParameters(Element method)
        {
            Document doc=method.getOwnerDocument();
            String WADL_NS=method.getNamespaceURI();
            Element request = doc.createElementNS(WADL_NS, "request");
            method.appendChild(request);

            Element param = doc.createElementNS(WADL_NS, "param");
            param.setAttribute(NAME, "method");
            param.setAttribute(STYLE, QUERY);
            param.setAttribute("fixed", m.getName());
            param.setAttribute(TYPE, XSD_STRING);
            param.setAttribute(REQUIRED, "true");
            request.appendChild(param);

            
            for (Class classparam : m.getParameterTypes())
            {
                if (isGenericObject(classparam))
                {
                    param = doc.createElementNS(WADL_NS, "param");
                    param.setAttribute(NAME, classparam.getName().toLowerCase());
                    param.setAttribute(STYLE, QUERY);
                    param.setAttribute(TYPE, XSD_ANYURI);
                    param.setAttribute(REQUIRED, "true");
                    request.appendChild(param);
                }
                else
                {
                    param = doc.createElementNS(WADL_NS, "param");
                    param.setAttribute(NAME, classparam.getName().toLowerCase());
                    param.setAttribute(STYLE, QUERY);
                    param.setAttribute(TYPE, classToxsd(classparam));
                    param.setAttribute(REQUIRED, "true");
                    request.appendChild(param);
                }
            }
            configureCommonsElements(method, request, WADL_NS, REST_RESOURCE_PREFIX + ":" + m.getName());
        }

        public void execute(HttpServletRequest request, HttpServletResponse response, String basePath) throws IOException
        {
            
        }
    }

    class SemanticFunctionsResourceModule extends ResourceModule
    {

        private final SemanticClass clazz;

        public SemanticFunctionsResourceModule(SemanticClass clazz)
        {
            this.clazz = clazz;
            try
            {
                Class clazzjava = Class.forName(clazz.getClassName());
                Class superclazz = clazzjava.getSuperclass();
                if (superclazz.getName().endsWith("Base"))
                {
                    for (Class c : superclazz.getDeclaredClasses())
                    {
                        if (c.getName().endsWith("ClassMgr"))
                        {
                            Class mgr = c;

                            for (java.lang.reflect.Method m : mgr.getDeclaredMethods())
                            {
                                if (Modifier.isPublic(m.getModifiers()) && Modifier.isStatic(m.getModifiers()) && (m.getName().startsWith("has") || m.getName().startsWith("list")))
                                {
                                    if (!hasModel(m))
                                    {
                                        MethodModule method = new MethodModule(m,clazz);
                                        this.methods.put(method.getId(), method);
                                    }
                                }
                            }
                        }
                    }
                }
            }
            catch (ClassNotFoundException clnfe)
            {
                log.error(clnfe);
            }
            // adds methods
        }

        @Override
        public String getId()
        {
            return "funtions";
        }
    }

    public SemanticClassPublisher()
    {
        for (SemanticClass clazz : classes)
        {
            addSemanticClass(clazz);
        }
    }

    public void addSemanticClass(SemanticClass clazz)
    {
        classes.add(clazz);
        SemanticResourceModule resourceModule = new SemanticResourceModule(clazz);

        GetMethodModule get = new GetMethodModule(clazz);
        resourceModule.methods.put(get.getId(), get);

        PostModule post = new PostModule(clazz);
        resourceModule.methods.put(post.getId(), post);

        PutModule put = new PutModule(clazz);
        resourceModule.methods.put(put.getId(), put);

        DeleteModule delete = new DeleteModule(clazz);
        resourceModule.methods.put(delete.getId(), delete);

        // add subResourcemodules

        SemanticFunctionsResourceModule functions = new SemanticFunctionsResourceModule(clazz);

        resourceModule.subResources.put(functions.getId(), functions);

        resourceModules.put(resourceModule.getId(), resourceModule);
    }

    public void removeSemanticClass(SemanticClass clazz)
    {
        SemanticResourceModule resourceModule = new SemanticResourceModule(clazz);
        resourceModules.remove(resourceModule.getId());
        classes.remove(clazz);
    }

    public void addSemanticClass(URI clazz)
    {
        SemanticClass clazzToAdd = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass(clazz.toString());
        if (clazzToAdd != null)
        {
            addSemanticClass(clazzToAdd);
        }
    }

    @Override
    protected void addAditionalNamespaces(Element application)
    {
        application.setAttribute("xmlns:swbrest", REST_RESOURCES_2010);
        Set<String> prefixes = new HashSet<String>();

        for (SemanticClass clazz : classes)
        {
            if (!prefixes.contains(clazz.getPrefix()))
            {
                application.setAttribute("xmlns:" + clazz.getPrefix(), clazz.getURI());
                prefixes.add(clazz.getPrefix());
            }
        }
    }

    @Override
    protected void addIncludes(Element grammars, HttpServletRequest servletRequest)
    {
        Document doc = grammars.getOwnerDocument();
        String WADL_NS = grammars.getNamespaceURI();
        Element include = doc.createElementNS(WADL_NS, "include");
        grammars.appendChild(include);
        include.setAttribute("href", servletRequest.getRequestURI() + "?error=xsd");

        include = doc.createElementNS(WADL_NS, "include");
        grammars.appendChild(include);
        include.setAttribute("href", servletRequest.getRequestURI() + "?created=xsd");

        include = doc.createElementNS(WADL_NS, "include");
        grammars.appendChild(include);
        include.setAttribute("href", servletRequest.getRequestURI() + "?updated=xsd");

        include = doc.createElementNS(WADL_NS, "include");
        grammars.appendChild(include);
        include.setAttribute("href", servletRequest.getRequestURI() + "?deleted=xsd");




        for (SemanticClass clazz : classes)
        {
            try
            {
                include = doc.createElementNS(WADL_NS, "include");
                grammars.appendChild(include);
                include.setAttribute("href", servletRequest.getRequestURI() + "?clsmgr=xsd&classuri=" + URLEncoder.encode(clazz.getURI(), "utf-8"));
            }
            catch (Exception e)
            {
                log.error(e);
            }


            String xsd = URLEncoder.encode(clazz.getURI());
            include = doc.createElementNS(WADL_NS, "include");
            grammars.appendChild(include);
            include.setAttribute("href", servletRequest.getRequestURI() + "?xsd=" + xsd);
        }
    }

    private static String fixURI(String uri)
    {
        if (uri != null && uri.indexOf(":") != -1)
        {
            if (uri.indexOf("%3A") != -1)
            {
                uri = uri.replace("%3A", ":");
            }
            if (uri.indexOf("%23") != -1)
            {
                uri = uri.replace("%23", "#");
            }
            if (uri.indexOf("#") == -1)
            {
                uri = SemanticObject.shortToFullURI(uri);
            }
        }
        return uri;
    }

    private void showError(HttpServletRequest request, HttpServletResponse response, String msg) throws IOException
    {
        String[] error = new String[1];
        error[0] = msg;
        showError(response, error);
    }

    private void showError(HttpServletResponse response, String[] msg) throws IOException
    {
        Document doc = getError(msg);
        showDocument(response, doc);
    }

    public Document getError(String[] errors)
    {
        Document doc = SWBUtils.XML.getNewDocument();
        Element eError = doc.createElement("Error");

        Attr xmlns = doc.createAttribute("xmlns");
        xmlns.setValue(REST_RESOURCES_2010);
        eError.setAttributeNode(xmlns);

        doc.appendChild(eError);
        for (String error : errors)
        {
            Element message = doc.createElement("Message");
            Text text = doc.createTextNode(error);
            message.appendChild(text);
            eError.appendChild(message);
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

    private void showObject(HttpServletRequest request, HttpServletResponse response, SemanticObject obj, String basepath) throws IOException
    {
        if ("json".equals(request.getParameter("format")))
        {

            try
            {
                showJSON(response, serializeAsJSON(obj, basepath));
            }
            catch (Exception e)
            {
                showError(request, response, e.getMessage());
            }
        }
        else
        {
            Document doc = serializeAsXML(obj, basepath);
            showDocument(response, doc);
        }

    }

    private void serializeAsJSON(SemanticObject obj, JSONObject jSONObject, String basepath) throws JSONException
    {
        Iterator<SemanticProperty> props = obj.listProperties();
        while (props.hasNext())
        {
            SemanticProperty prop = props.next();
            if (!prop.hasInverse())
            {
                if (prop.isDataTypeProperty())
                {
                    if (prop.getName().startsWith("has"))
                    {
                        Iterator<SemanticLiteral> values = obj.listLiteralProperties(prop);
                        while (values.hasNext())
                        {
                            SemanticLiteral value = values.next();
                            String name = prop.getName();
                            String data = value.getString();
                            jSONObject.accumulate(name, data);
                        }
                    }
                    else
                    {
                        String name = prop.getName();
                        String data = obj.getProperty(prop);
                        jSONObject.accumulate(name, data);
                    }
                }
                else
                {
                    SemanticClass range = prop.getRangeClass();
                    if (range != null)
                    {
                        if (prop.getName().startsWith("has"))
                        {
                            Iterator<SemanticObject> values = obj.listObjectProperties(prop);
                            while (values.hasNext())
                            {
                                SemanticObject value = values.next();
                                if (value != null)
                                {
                                    String name = prop.getName();
                                    JSONObject data = new JSONObject();
                                    data.put("uri", value.getURI());
                                    data.put("href", getPathForObject(value, basepath));
                                    jSONObject.accumulate(name, data);
                                }
                            }
                        }
                        else
                        {
                            SemanticObject value = obj.getObjectProperty(prop);
                            if (value != null)
                            {
                                String name = prop.getName();
                                JSONObject data = new JSONObject();
                                data.put("uri", value.getURI());
                                data.put("href", getPathForObject(value, basepath));
                                jSONObject.accumulate(name, data);
                            }
                        }
                    }
                }
            }
        }
    }

    private String getPathForObject(SemanticObject obj, String basepath)
    {
        //String path = request.getRequestURI() + "?uri=" + obj.getShortURI();

        String path = basepath + "/" + obj.getSemanticClass().getPrefix() + "_" + obj.getSemanticClass().getName() + "/" + obj.getShortURI();

        return path;
    }

    private void showJSON(HttpServletResponse response, JSONObject jSONObject) throws IOException
    {
        PrintWriter out = response.getWriter();
        String charset = Charset.defaultCharset().name();
        response.setContentType(APPLICATION_XML + "; charset=" + charset);
        String xml = jSONObject.toString();
        out.print(xml);
        out.close();
    }

    private Document serializeAsXML(SemanticObject obj, String basdepath)
    {
        Document doc = SWBUtils.XML.getNewDocument();
        Element name = doc.createElementNS(obj.getSemanticClass().getURI(), obj.getSemanticClass().getName());
        name.setAttribute("xmlns:xlink", XLINK_NS);
        name.setAttribute("xmlns", obj.getSemanticClass().getURI());
        doc.appendChild(name);
        serialize(obj, name, basdepath);
        return doc;
    }

    private JSONObject serializeAsJSON(SemanticObject obj, String basepath) throws Exception
    {
        JSONObject jSONObject = new JSONObject();
        serializeAsJSON(obj, jSONObject, basepath);
        return jSONObject;
    }

    private void serialize(SemanticObject obj, Element name, String basepath)
    {
        Document doc = name.getOwnerDocument();
        Iterator<SemanticProperty> props = obj.listProperties();
        while (props.hasNext())
        {
            SemanticProperty prop = props.next();
            if (!prop.hasInverse())
            {
                if (prop.isDataTypeProperty())
                {
                    if (prop.getName().startsWith("has"))
                    {
                        Iterator<SemanticLiteral> values = obj.listLiteralProperties(prop);
                        while (values.hasNext())
                        {
                            SemanticLiteral value = values.next();
                            Element eprop = doc.createElementNS(obj.getSemanticClass().getURI(), prop.getName());
                            name.appendChild(eprop);
                            Text tvalue = doc.createTextNode(value.getString());
                            eprop.appendChild(tvalue);
                        }
                    }
                    else
                    {
                        Element eprop = doc.createElementNS(obj.getSemanticClass().getURI(), prop.getName());
                        name.appendChild(eprop);
                        Text value = doc.createTextNode(obj.getProperty(prop));
                        eprop.appendChild(value);
                    }
                }
                else
                {
                    SemanticClass range = prop.getRangeClass();
                    if (range != null)
                    {
                        if (prop.getName().startsWith("has"))
                        {
                            Iterator<SemanticObject> values = obj.listObjectProperties(prop);
                            while (values.hasNext())
                            {
                                SemanticObject value = values.next();
                                if (value != null)
                                {
                                    Element eprop = doc.createElementNS(obj.getSemanticClass().getURI(), prop.getName());
                                    name.appendChild(eprop);
                                    eprop.setAttributeNS(XLINK_NS, "xlink:href", getPathForObject(value, basepath));
                                    Text data = doc.createTextNode(value.getURI());
                                    eprop.appendChild(data);
                                }
                            }
                        }
                        else
                        {
                            Element eprop = doc.createElementNS(obj.getSemanticClass().getURI(), prop.getName());
                            name.appendChild(eprop);
                            SemanticObject value = obj.getObjectProperty(prop);
                            if (value != null)
                            {
                                eprop.setAttributeNS(XLINK_NS, "xlink:href", getPathForObject(value, basepath));
                                Text data = doc.createTextNode(value.getURI());
                                eprop.appendChild(data);
                            }
                        }
                    }
                }
            }
        }
    }

    private void updateProperties(HttpServletRequest request, SemanticObject obj) throws Exception
    {
        Iterator<SemanticProperty> props = obj.listProperties();
        while (props.hasNext())
        {
            SemanticProperty prop = props.next();
            if (!prop.hasInverse())
            {
                String[] values = request.getParameterValues(prop.getName());
                if (prop.isDataTypeProperty())
                {
                    String type = XSD_STRING;
                    if (prop.isBoolean())
                    {
                        type = SemanticVocabulary.XMLS_BOOLEAN;
                    }
                    else if (prop.isBinary())
                    {
                        type = SemanticVocabulary.XMLS_BASE64BINARY;
                    }
                    else if (prop.isByte())
                    {
                        type = SemanticVocabulary.XMLS_BYTE;
                    }
                    else if (prop.isDate())
                    {
                        type = SemanticVocabulary.XMLS_DATE;
                    }
                    else if (prop.isDateTime())
                    {
                        type = SemanticVocabulary.XMLS_DATETIME;
                    }
                    else if (prop.isDouble())
                    {
                        type = SemanticVocabulary.XMLS_DOUBLE;
                    }
                    else if (prop.isFloat())
                    {
                        type = SemanticVocabulary.XMLS_FLOAT;
                    }
                    else if (prop.isInt())
                    {
                        type = SemanticVocabulary.XMLS_INT;
                    }
                    else if (prop.isLong())
                    {
                        type = SemanticVocabulary.XMLS_LONG;
                    }
                    else if (prop.isShort())
                    {
                        type = SemanticVocabulary.XMLS_SHORT;
                    }
                    type = type.replace(W3C_XML_SCHEMA_NS_URI + "#", "xsd:");
                    if (prop.getName().startsWith("has"))
                    {
                        if (values != null)
                        {
                            // Validate
                            for (String value : values)
                            {
                                validate(value, type);
                            }
                            // set the values
                            for (String value : values)
                            {
                                SemanticLiteral literal = SemanticLiteral.valueOf(prop, value);
                                obj.addLiteralProperty(prop, literal);
                            }

                        }

                    }
                    else
                    {
                        if (values != null)
                        {
                            if (values.length != 1)
                            {
                                throw new Exception("The property " + prop.getName() + " has single value");
                            }
                            if (values[0] != null)
                            {
                                Object value = get(values[0], type);
                                if (value instanceof Date)
                                {
                                    obj.setDateProperty(prop, (Date) value);
                                }
                                if (value instanceof Timestamp)
                                {
                                    obj.setDateTimeProperty(prop, (Timestamp) value);
                                }
                                if (value instanceof Boolean)
                                {
                                    obj.setBooleanProperty(prop, ((Boolean) value).booleanValue());
                                }
                                else
                                {
                                    obj.setProperty(prop, value.toString());
                                }
                            }


                        }

                    }
                }
                else
                {
                    if (prop.getName().startsWith("has"))
                    {
                        if (values != null)
                        {
                            // Validate
                            for (String value : values)
                            {
                                String uri = value;
                                uri = fixURI(uri);
                                SemanticObject testobj = SemanticObject.createSemanticObject(uri);
                                if (testobj == null)
                                {
                                    throw new Exception("The object with uri " + value + " was not found");
                                }
                                obj.addObjectProperty(prop, testobj);

                            }

                        }

                    }
                    else
                    {
                        if (values != null)
                        {
                            if (values.length != 1)
                            {
                                throw new Exception("The property " + prop.getName() + " has single value");
                            }
                            if (values[0] != null)
                            {
                                String uri = values[0];
                                uri = fixURI(uri);
                                SemanticObject testobj = SemanticObject.createSemanticObject(uri);
                                if (testobj == null)
                                {
                                    throw new Exception("The object with uri " + values[0] + " was not found");
                                }
                                obj.addObjectProperty(prop, testobj);
                            }

                        }

                    }
                }
            }
        }
    }

    private void showCreted(HttpServletRequest request, HttpServletResponse response, String uri) throws IOException
    {
        if ("json".equals(request.getParameter("format")))
        {
            try
            {
                showJSON(response, getCreatedAsJSON(uri));
            }
            catch (Exception e)
            {
                showError(request, response, e.getMessage());
            }
        }
        else
        {
            Document doc = getCreatedAsXML(uri);
            showDocument(response, doc);
        }

    }

    private void validate(String value, String dataType) throws Exception
    {
        dataType = dataType.replace("xsd:", SemanticVocabulary.XMLS_URI);
        if (dataType.equals(SemanticVocabulary.XMLS_BOOLEAN))
        {
            if (!(value.equals("true") || value.equals("false")))
            {
                throw new Exception("The value is invalid");
            }
        }
        if (dataType.equals(SemanticVocabulary.XMLS_DATE))
        {
            SWBUtils.TEXT.iso8601DateParse(value);
        }
        if (dataType.equals(SemanticVocabulary.XMLS_DATETIME))
        {
            Timestamp.valueOf(value);
        }
        if (dataType.equals(SemanticVocabulary.XMLS_BYTE))
        {
            Byte.parseByte(value);
        }
        if (dataType.equals(SemanticVocabulary.XMLS_DOUBLE))
        {
            Double.parseDouble(value);
        }
        if (dataType.equals(SemanticVocabulary.XMLS_FLOAT))
        {
            Float.parseFloat(value);
        }
        if (dataType.equals(SemanticVocabulary.XMLS_INT) || dataType.equals(SemanticVocabulary.XMLS_INTEGER))
        {
            Integer.parseInt(value);
        }
        if (dataType.equals(SemanticVocabulary.XMLS_LONG))
        {
            Long.parseLong(value);
        }
        if (dataType.equals(SemanticVocabulary.XMLS_SHORT))
        {
            Short.parseShort(value);
        }
    }

    public static Object get(String value, String dataType) throws Exception
    {
        dataType = dataType.replace("xsd:", SemanticVocabulary.XMLS_URI);
        if (dataType.equals(SemanticVocabulary.XMLS_BOOLEAN))
        {
            return Boolean.parseBoolean(value);
        }
        if (dataType.equals(SemanticVocabulary.XMLS_DATE))
        {
            return SWBUtils.TEXT.iso8601DateParse(value);
        }
        if (dataType.equals(SemanticVocabulary.XMLS_DATETIME))
        {
            return Timestamp.valueOf(value);
        }
        if (dataType.equals(SemanticVocabulary.XMLS_BYTE))
        {
            return Byte.parseByte(value);
        }
        if (dataType.equals(SemanticVocabulary.XMLS_DOUBLE))
        {
            return Double.parseDouble(value);
        }
        if (dataType.equals(SemanticVocabulary.XMLS_FLOAT))
        {
            return Float.parseFloat(value);
        }
        if (dataType.equals(SemanticVocabulary.XMLS_INT) || dataType.equals(SemanticVocabulary.XMLS_INTEGER))
        {
            return Integer.parseInt(value);
        }
        if (dataType.equals(SemanticVocabulary.XMLS_LONG))
        {
            return Long.parseLong(value);
        }
        if (dataType.equals(SemanticVocabulary.XMLS_SHORT))
        {
            return Short.parseShort(value);
        }
        if (dataType.equals(SemanticVocabulary.XMLS_URI))
        {
            String uri = value;
            uri = fixURI(uri);
            SemanticObject ovalue = SemanticObject.createSemanticObject(uri);
            if (ovalue == null)
            {
                throw new Exception("The object with uri " + value + " was not found");
            }
            return ovalue;
        }
        return value;
    }

    public Document getCreatedAsXML(String uri)
    {
        Document doc = SWBUtils.XML.getNewDocument();
        Element created = doc.createElement("Created");
        doc.appendChild(created);
        Attr xmlns = doc.createAttribute("xmlns");
        xmlns.setValue(REST_RESOURCES_2010);
        created.appendChild(xmlns);
        Text data = doc.createTextNode(uri);
        created.appendChild(data);
        return doc;
    }

    public JSONObject getCreatedAsJSON(String uri) throws RestException
    {
        JSONObject jSONObject = new JSONObject();
        try
        {
            jSONObject.put("Created", uri);
        }
        catch (Exception e)
        {
            throw new RestException(e);
        }
        return jSONObject;
    }

    private void showUpdated(HttpServletRequest request, HttpServletResponse response, boolean isUpdated) throws IOException
    {
        if ("json".equals(request.getParameter("format")))
        {
            try
            {
                showJSON(response, getUpdatedAsJSON(isUpdated));
            }
            catch (Exception e)
            {
                showError(request, response, e.getMessage());
            }
        }
        else
        {
            Document doc = getUpdatedAsXml(isUpdated);
            showDocument(response, doc);
        }

    }

    private JSONObject getUpdatedAsJSON(boolean isUpdated) throws RestException
    {
        JSONObject jSONObject = new JSONObject();
        try
        {
            jSONObject.put("Updated", isUpdated);
        }
        catch (Exception e)
        {
            throw new RestException(e);
        }
        return jSONObject;
    }

    private Document getUpdatedAsXml(boolean isUpdated)
    {
        Document doc = SWBUtils.XML.getNewDocument();
        Element updated = doc.createElement("Updated");
        Attr xmlns = doc.createAttribute("xmlns");
        xmlns.setValue(REST_RESOURCES_2010);
        updated.appendChild(xmlns);
        doc.appendChild(updated);
        Text data = doc.createTextNode(Boolean.toString(isUpdated));
        updated.appendChild(data);
        return doc;
    }

    private void showDeleted(HttpServletRequest request, HttpServletResponse response, boolean isdeleted) throws IOException
    {
        if ("json".equals(request.getParameter("format")))
        {
            try
            {
                showJSON(response, getDeletedAsJSON(isdeleted));
            }
            catch (Exception e)
            {
                showError(request, response, e.getMessage());
            }
        }
        else
        {
            Document doc = getDeletedAsXML(isdeleted);
            showDocument(response, doc);
        }

    }

    public Document getDeletedAsXML(boolean isdeleted)
    {
        Document doc = SWBUtils.XML.getNewDocument();
        Element deleted = doc.createElement("Deleted");
        Attr xmlns = doc.createAttribute("xmlns");
        xmlns.setValue(REST_RESOURCES_2010);
        deleted.appendChild(xmlns);
        doc.appendChild(deleted);
        Text data = doc.createTextNode(Boolean.toString(isdeleted));
        deleted.appendChild(data);
        return doc;
    }

    public JSONObject getDeletedAsJSON(boolean isdeleted) throws RestException
    {
        JSONObject jSONObject = new JSONObject();
        try
        {
            jSONObject.put("Deleted", isdeleted);
        }
        catch (Exception e)
        {
            throw new RestException(e);
        }
        return jSONObject;
    }

    private static boolean isGenericObject(Class clazz)
    {
        try
        {
            clazz.asSubclass(GenericObject.class);
            return true;
        }
        catch (ClassCastException e)
        {
            //e.printStackTrace();
            return false;
        }
        catch (Exception e)
        {
            log.error(e);
        }
        return false;
    }

    public static String classToxsd(Class clazz)
    {
        String getXSDType = XSD_STRING;
        if (clazz.equals(Boolean.class))
        {
            getXSDType = XSD_BOOLEAN;
        }
        else if (clazz.equals(Byte.class))
        {
            getXSDType = XSD_BYTE;
        }
        else if (clazz.equals(Date.class))
        {
            getXSDType = XSD_DATE;
        }
        else if (clazz.equals(Timestamp.class))
        {
            getXSDType = XSD_DATETIME;
        }
        else if (clazz.equals(Double.class))
        {
            getXSDType = XSD_DOUBLE;
        }
        else if (clazz.equals(Float.class))
        {
            getXSDType = XSD_FLOAT;
        }
        else if (clazz.equals(Integer.class))
        {
            getXSDType = XSD_INT;
        }
        else if (clazz.equals(Long.class))
        {
            getXSDType = XSD_LONG;
        }
        else if (clazz.equals(Short.class))
        {
            getXSDType = XSD_SHORT;
        }
        return getXSDType;

    }

    private boolean hasModel(java.lang.reflect.Method method)
    {
        for (Class parameterClass : method.getParameterTypes())
        {
            if (parameterClass.equals(org.semanticwb.model.SWBModel.class))
            {
                return true;
            }
        }
        return false;
    }
}
