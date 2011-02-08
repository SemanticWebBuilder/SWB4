/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.rest.publish;

import java.util.Arrays;
import java.util.Collection;
import java.util.Set;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import java.util.List;
import java.util.ArrayList;
import org.semanticwb.model.GenericIterator;

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
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Iterator;
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
import org.semanticwb.rest.util.HTTPMethod;
import org.semanticwb.rest.consume.RestException;
import org.semanticwb.rest.util.DateToXsdDatetimeFormatter;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import static javax.xml.XMLConstants.W3C_XML_SCHEMA_NS_URI;

/**
 *
 * @author victor.lorenzana
 */
public final class SemanticClassPublisher extends RestModule
{

    private static final String VALUE = "value";
    private static final String ELEMENT = "element";
    private static final String XSD_PREFIX = "xsd";
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
    private static final String TYPE = "type";
    private static final String APPLICATION_XML = "application/xml";
    public static final String REST_RESOURCES_2010 = "http://www.semanticwb.org/rest/2010";
    private static final String REST_MODELURI = "rest:modeluri";
    private static final String REST_URI = "rest:uri";
    public static final String REST_RESOURCE_PREFIX = "swbrest";
    public static final String XSD_STRING = "xsd:string";
    private static final String REST_ID = "rest:id";
    private static final Logger log = SWBUtils.getLogger(SemanticClassPublisher.class);
    private final Set<PublishDefinition> definitions = Collections.synchronizedSet(new HashSet<PublishDefinition>());

    @Override
    public void showData(HttpServletRequest request, HttpServletResponse response, String basepath) throws IOException
    {
        if (request.getParameter(XSD_PREFIX) != null)
        {
            String uri = request.getParameter(XSD_PREFIX);
            SemanticObject obj = SemanticObject.createSemanticObject(uri);
            if (obj != null)
            {
                SemanticClass clazz = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass(uri);
                showXSD(request, response, clazz);
            }

        }
        else if (request.getParameter("error") != null)
        {
            showErrorXSD(request, response);
        }
        else if (request.getParameter("created") != null)
        {
            showCreatedXSDResponse(response);
        }
        else if (request.getParameter("updated") != null)
        {
            showUpdatedXSDResponse(response);
        }
        else if (request.getParameter("deleted") != null)
        {
            showDeletedXSDResponse(response);
        }
        else if (request.getParameter("clsmgr") != null)
        {

            String uri = request.getParameter("classuri");
            uri = fixURI(uri);
            SemanticClass clazz = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass(uri);
            if (clazz != null)
            {
                showCLSMGRXSD(response, clazz);
            }
            else
            {
                showError(request, response, "The class with uri " + request.getParameter("classuri") + " was not found");
            }

        }
        else
        {
            showWADL(request, response, basepath);
        }
    }

    class DeleteModule extends MethodModuleBase
    {

        private final PublishDefinition definition;

        public DeleteModule(PublishDefinition definition)
        {
            this.definition = definition;
        }

        public HTTPMethod getHTTPMethod()
        {
            return HTTPMethod.DELETE;
        }

        public String getId()
        {
            return "delete_" + definition.getSemanticClass().getPrefix() + "_" + definition.getSemanticClass().getName();
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
            param.setAttribute(TYPE, XSD_STRING);
            param.setAttribute(REQUIRED, "true");
            request.appendChild(param);

            param = doc.createElementNS(WADL_NS, "param");
            param.setAttribute(NAME, "format");
            param.setAttribute(STYLE, QUERY);
            request.appendChild(param);

            for (Representation rep : definition.getRepresentations())
            {
                rep.addWADL(param, request, method, getHTTPMethod(), REST_RESOURCE_PREFIX + ":Deleted");
            }

            //MethodModuleBase.configureCommonsElements(definition, method, request, WADL_NS, REST_RESOURCE_PREFIX + ":Deleted");
        }

        public void execute(HttpServletRequest request, HttpServletResponse response, String basepath) throws IOException
        {
            if (request.getParameter(REST_URI) != null)
            {
                String uri = request.getParameter(REST_URI);
                uri = fixURI(uri);
                log.debug("getting object with uri " + uri+" method "+getHTTPMethod());
                SemanticObject obj = SemanticObject.createSemanticObject(uri);
                if (obj != null)
                {
                    SemanticClass clazzObject = obj.getSemanticClass();
                    if (!clazzObject.equals(definition.getSemanticClass()))
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

        private final PublishDefinition definition;

        public PutModule(PublishDefinition definition)
        {
            this.definition = definition;
        }

        public HTTPMethod getHTTPMethod()
        {
            return HTTPMethod.PUT;
        }

        public String getId()
        {
            return "update_" + definition.getSemanticClass().getPrefix() + "_" + definition.getSemanticClass().getName();
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
            param.setAttribute("required", "true");

            request.appendChild(param);

            Iterator<SemanticProperty> props = definition.getSemanticClass().listProperties();
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
            param = doc.createElementNS(WADL_NS, "param");
            param.setAttribute(NAME, "format");
            param.setAttribute(STYLE, QUERY);
            request.appendChild(param);
            for (Representation rep : definition.getRepresentations())
            {
                rep.addWADL(param, request, method, getHTTPMethod(), REST_RESOURCE_PREFIX + ":Updated");
            }
            //configureCommonsElements(definition, method, request, WADL_NS, REST_RESOURCE_PREFIX + ":Updated");
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
                log.debug("getting object with uri " + uri+ " method: "+getHTTPMethod());
                SemanticObject obj = SemanticObject.createSemanticObject(uri);
                if (obj != null)
                {
                    log.debug("The object with uri :"+obj.getURI()+" was found");
                    if (!obj.getSemanticClass().equals(definition.getSemanticClass()))
                    {
                        log.debug("The object is no the corrcet type, uri :"+obj.getURI());
                        response.setStatus(400);
                        showError(request, response, "The object was not found");
                        return;
                    }
                    try
                    {
                        log.debug("updating properties for the object uri :"+obj.getURI());
                        updateProperties(request, obj);
                        log.debug("properties updated for the object uri :"+obj.getURI());
                        showUpdated(request, response, true);
                    }
                    catch (Exception e)
                    {
                        log.debug("Error, uri :"+obj.getURI());
                        log.debug(e);
                        response.setStatus(400);
                        showError(request, response, e.getMessage());
                        return;
                    }
                }
                else
                {
                    log.debug("The object with uri :"+obj.getURI()+" was not found");
                    showUpdated(request, response, false);
                }
            }
        }
    }

    class PostModule extends MethodModuleBase
    {

        private final PublishDefinition definition;

        public PostModule(PublishDefinition definition)
        {
            this.definition = definition;
        }

        public void addParameters(Element method)
        {
            Document doc = method.getOwnerDocument();
            String WADL_NS = method.getNamespaceURI();
            Element request = doc.createElementNS(WADL_NS, "request");
            method.appendChild(request);





            Iterator<SemanticProperty> props = definition.getSemanticClass().listProperties();
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

            if (!definition.getSemanticClass().isAutogenId())
            {
                param = doc.createElementNS(WADL_NS, "param");
                param.setAttribute(NAME, REST_ID);
                param.setAttribute(STYLE, QUERY);
                param.setAttribute(TYPE, XSD_STRING);
                param.setAttribute(REQUIRED, "true");
                request.appendChild(param);
            }
            param = doc.createElementNS(WADL_NS, "param");
            param.setAttribute(NAME, "format");
            param.setAttribute(STYLE, QUERY);
            request.appendChild(param);
            for (Representation rep : definition.getRepresentations())
            {
                rep.addWADL(param, request, method, getHTTPMethod(), REST_RESOURCE_PREFIX + ":Created");
            }
            //configureCommonsElements(definition, method, request, WADL_NS, REST_RESOURCE_PREFIX + ":Created");
        }

        public HTTPMethod getHTTPMethod()
        {
            return HTTPMethod.POST;
        }

        public String getId()
        {
            return "add_" + definition.getSemanticClass().getPrefix() + "_" + definition.getSemanticClass().getName();
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
            if (!definition.getSemanticClass().isAutogenId())
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
            if (definition.getSemanticClass().isAutogenId())
            {
                id = String.valueOf(site.getSemanticObject().getModel().getCounter(definition.getSemanticClass()));
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
                SemanticObject objtest = site.getSemanticObject().getModel().createSemanticObjectById(id, definition.getSemanticClass());
                if (objtest != null)
                {
                    response.setStatus(400);
                    showError(request, response, "The parameter rest:id is invalid, the id already exists");
                    return;
                }
            }
            GenericObject newobj = site.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, definition.getSemanticClass()), definition.getSemanticClass());
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

        private final PublishDefinition definition;

        public GetMethodModule(PublishDefinition definition)
        {
            this.definition = definition;
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
            param.setAttribute(TYPE, XSD_STRING);
            param.setAttribute(REQUIRED, "true");
            request.appendChild(param);

            param = doc.createElementNS(WADL_NS, "param");
            param.setAttribute(NAME, "format");
            param.setAttribute(STYLE, QUERY);
            request.appendChild(param);

            for (Representation rep : definition.getRepresentations())
            {
                rep.addWADL(param, request, method, getHTTPMethod(), definition.getSemanticClass().getPrefix() + ":" + definition.getSemanticClass().getName());
            }
            //configureCommonsElements(definition, method, request, WADL_NS, definition.getSemanticClass().getPrefix() + ":" + definition.getSemanticClass().getName());
        }

        public HTTPMethod getHTTPMethod()
        {
            return HTTPMethod.GET;
        }

        public String getId()
        {
            return "get_" + definition.getSemanticClass().getPrefix() + "_" + definition.getSemanticClass().getName();
        }

        public void execute(HttpServletRequest request, HttpServletResponse response, String basepath) throws IOException
        {
            String uri = request.getParameter(REST_URI);
            log.debug("triyng to update uri "+uri);
            if (uri == null || uri.trim().equals(""))
            {
                response.setStatus(400);
                showError(request, response, "The parameter uri was not found");
            }
            uri = fixURI(uri);
            log.debug("getting object with uri " + uri+" to update method: "+getHTTPMethod());
            SemanticObject obj = SemanticObject.createSemanticObject(uri);
            if (obj != null)
            {
                log.debug("The object with uri "+obj.getURI()+" exists");
                SemanticClass clazzObject = obj.getSemanticClass();
                if (!clazzObject.equals(definition.getSemanticClass()))
                {
                    log.debug("The object " + obj.getURI() + " has the semanticclass " + clazzObject.getURI() + "  and the definition has semanticclass " + definition.getSemanticClass().getURI());
                    response.setStatus(400);
                    showError(request, response, "The object was not found");
                    return;
                }
                showObject(request, response, obj, basepath);
            }
            else
            {
                log.debug("The object with uri "+uri+" was not found");
                response.setStatus(400);
                showError(request, response, "The objct with uri " + request.getParameter(REST_URI) + " was not found");
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

    class ModelMethodModule extends MethodModule
    {

        public ModelMethodModule(Method m, PublishDefinition definition)
        {
            super(m, definition);
        }

        @Override
        public String getId()
        {
            return "_" + m.getName() + definition.getSemanticClass().getPrefix() + "_" + definition.getSemanticClass().getName();
        }
    }

    class MethodModule extends MethodModuleBase
    {

        protected final Method m;
        protected final PublishDefinition definition;

        public MethodModule(Method m, PublishDefinition definition)
        {
            this.m = m;
            this.definition = definition;
        }

        public String getId()
        {
            return definition.getSemanticClass().getPrefix() + "_" + definition.getSemanticClass().getName() + m.getName();
        }

        public HTTPMethod getHTTPMethod()
        {
            return HTTPMethod.GET;
        }

        public void addParameters(Element method)
        {
            Document doc = method.getOwnerDocument();
            String WADL_NS = method.getNamespaceURI();
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
            param = doc.createElementNS(WADL_NS, "param");
            param.setAttribute(NAME, "format");
            param.setAttribute(STYLE, QUERY);
            request.appendChild(param);
            for (Representation rep : definition.getRepresentations())
            {
                rep.addWADL(param, request, method, getHTTPMethod(), REST_RESOURCE_PREFIX + ":" + m.getName());
            }
            //configureCommonsElements(definition, method, request, WADL_NS, REST_RESOURCE_PREFIX + ":" + m.getName());
        }

        private Object convert(String value, Class clazz) throws Exception
        {
            if (isGenericObject(clazz))
            {
                GenericObject go = SemanticObject.createSemanticObject(value).createGenericInstance();
                return go;
            }
            else
            {
                String type = classToxsd(clazz);
                return get(value, type);
            }
        }

        private Object[] getParameters(Method m, HttpServletRequest request) throws Exception
        {
            ArrayList<Object> getParameters = new ArrayList<Object>();
            for (Class parameter : m.getParameterTypes())
            {
                String parameterName = parameter.getName();
                String value = request.getParameter(parameterName);
                if (value != null)
                {
                    Object parameterValue = convert(value, parameter);
                    getParameters.add(parameterValue);
                }
                else
                {
                    throw new Exception();
                }
            }
            return getParameters.toArray(new Object[getParameters.size()]);
        }

        private boolean checkParameters(Method m, HttpServletRequest request)
        {
            for (Class parameter : m.getParameterTypes())
            {
                String parameterName = parameter.getName();
                String value = request.getParameter(parameterName);
                if (value != null)
                {
                    try
                    {
                        Object parameterValue = convert(value, parameter);
                        if (parameterValue == null)
                        {
                            return false;
                        }
                    }
                    catch (Exception e)
                    {
                        log.error(e);
                        return false;
                    }
                }
                else
                {
                    return false;
                }
            }
            return true;
        }

        private Document executeMethod(HttpServletRequest request, String basePath) throws Exception
        {
            checkParameters(m, request);
            Object[] args = getParameters(m, request);
            Object resinvoke = m.invoke(null, args);
            log.debug("resinvoke: " + resinvoke);
            if (resinvoke != null)
            {
                if (resinvoke instanceof SemanticObject)
                {
                    log.debug("resinvoke is SemanticObject: ");
                    SemanticObject so = (SemanticObject) resinvoke;
                    return serializeAsXML(so, basePath);
                }
                else if (resinvoke instanceof GenericIterator)
                {
                    log.debug("resinvoke is GenericIterator: ");
                    Document doc = SWBUtils.XML.getNewDocument();


                    String prefix = "";
                    Element res = null;

                    GenericIterator gi = (GenericIterator) resinvoke;
                    if (gi.hasNext())
                    {
                        GenericObject go = gi.next();
                        SemanticObject obj = go.getSemanticObject();

                        prefix = obj.getSemanticClass().getPrefix();

                        res = doc.createElementNS(REST_RESOURCES_2010, m.getName());
                        res.setPrefix(prefix);
                        doc.appendChild(res);

                        Attr xmlns = doc.createAttribute("xmlns");
                        xmlns.setValue(REST_RESOURCES_2010);
                        res.setAttributeNode(xmlns);

                        Element name = doc.createElementNS(REST_RESOURCES_2010, obj.getSemanticClass().getName());
                        name.setPrefix(prefix);

                        Attr shortURI = doc.createAttributeNS(REST_RESOURCES_2010, "shortURI");
                        shortURI.setValue(obj.getShortURI());
                        shortURI.setPrefix(prefix);
                        name.setAttributeNodeNS(shortURI);

                        Attr href = doc.createAttributeNS(REST_RESOURCES_2010, "href");
                        href.setValue(getPathForObject(obj, basePath));
                        href.setPrefix(prefix);
                        name.setAttributeNodeNS(href);


                        Text data = doc.createTextNode(obj.getURI());
                        name.appendChild(data);
                        res.appendChild(name);
                    }
                    while (gi.hasNext())
                    {
                        GenericObject go = gi.next();
                        SemanticObject obj = go.getSemanticObject();
                        Element name = doc.createElementNS(REST_RESOURCES_2010, obj.getSemanticClass().getName());
                        name.setPrefix(prefix);

                        Attr shortURI = doc.createAttributeNS(REST_RESOURCES_2010, "shortURI");
                        shortURI.setValue(obj.getURI());
                        shortURI.setPrefix(prefix);
                        name.setAttributeNodeNS(shortURI);

                        Attr href = doc.createAttributeNS(REST_RESOURCES_2010, "href");
                        href.setValue(getPathForObject(obj, basePath));
                        href.setPrefix(prefix);
                        name.setAttributeNodeNS(href);



                        Text data = doc.createTextNode(obj.getURI());
                        name.appendChild(data);
                        res.appendChild(name);
                    }
                    return doc;
                }
                else
                {
                    throw new Exception("The type of the result is not supported");
                }
            }
            else
            {
                throw new Exception("Error can not execute the method");
            }


        }

        public void execute(HttpServletRequest request, HttpServletResponse response, String basePath) throws IOException
        {
            String methodName = request.getParameter("method");
            if (m.getName().equals(methodName))
            {
                log.debug("executing method : " + m.getName() + " ...");
                try
                {
                    Document doc = executeMethod(request, basePath);
                    showDocument(response, doc);
                    log.debug("method : " + m.getName() + " executed");
                }
                catch (Exception e)
                {
                    showError(request, response, e.getMessage());
                    return;
                }
            }
        }
    }

    class SemanticFunctionsResourceModule extends ResourceModule
    {

        private final PublishDefinition definition;

        public SemanticFunctionsResourceModule(PublishDefinition definition)
        {
            this.definition = definition;
            try
            {
                Class clazzjava = Class.forName(definition.getSemanticClass().getClassName());
                Class superclazz = clazzjava.getSuperclass();
                if (superclazz.getName().endsWith("Base"))
                {
                    for (Class c : superclazz.getDeclaredClasses())
                    {
                        if (c.getName().endsWith("ClassMgr"))
                        {
                            Class mgr = c;

                            for (Method m : mgr.getDeclaredMethods())
                            {
                                if (Modifier.isPublic(m.getModifiers()) && Modifier.isStatic(m.getModifiers()) && (m.getName().startsWith("has") || m.getName().startsWith("list")))
                                {
                                    if (!hasModel(m))
                                    {
                                        MethodModule method = new MethodModule(m, definition);
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

        private boolean existsMethodName(String methodName)
        {
            try
            {
                Class clazzjava = Class.forName(definition.getSemanticClass().getClassName());
                Class superclazz = clazzjava.getSuperclass();
                if (superclazz.getName().endsWith("Base"))
                {
                    for (Class c : superclazz.getDeclaredClasses())
                    {
                        if (c.getName().endsWith("ClassMgr"))
                        {
                            Class mgr = c;
                            for (Method m : mgr.getDeclaredMethods())
                            {
                                if (methodName.equals(methodName) && Modifier.isPublic(m.getModifiers()) && Modifier.isStatic(m.getModifiers()) && (m.getName().startsWith("has") || m.getName().startsWith("list")))
                                {
                                    return true;
                                }
                            }
                        }
                    }
                }

            }
            catch (ClassNotFoundException cnfe)
            {
                log.error(cnfe);
            }
            return false;
        }

        @Override
        public void service(HttpServletRequest request, HttpServletResponse response, String servet, List<String> path, String basepath) throws IOException
        {
            String method = request.getParameter("method");
            if (method == null || method.trim().equals(""))
            {
                response.setStatus(400);
                showError(request, response, "The parameter method was not found");
                return;
            }
            if (!existsMethodName(method))
            {
                response.setStatus(400);
                showError(request, response, "The method was not found");
                return;
            }

            super.service(request, response, servet, path, basepath);
        }

        @Override
        public String getId()
        {
            return "functionsOf_" + definition.getSemanticClass().getPrefix() + "_" + definition.getSemanticClass().getName();
        }
    }

    class SemanticModelResourceModule extends ResourceModule
    {

        private final PublishDefinition definition;

        @Override
        public void service(HttpServletRequest request, HttpServletResponse response, String servet, List<String> path, String basepath) throws IOException
        {
            String method = request.getParameter("method");
            if (method == null || method.trim().equals(""))
            {
                response.setStatus(400);
                showError(request, response, "The parameter method was not found");
                return;
            }
            if (!existsMethodName(method))
            {
                response.setStatus(400);
                showError(request, response, "The method was not found");
                return;
            }

            super.service(request, response, servet, path, basepath);
        }

        private boolean existsMethodName(String methodName)
        {
            try
            {
                Class clazzjava = Class.forName(definition.getSemanticClass().getClassName());
                Class superclazz = clazzjava.getSuperclass();
                if (superclazz.getName().endsWith("Base"))
                {
                    for (Class c : superclazz.getDeclaredClasses())
                    {
                        if (c.getName().endsWith("ClassMgr"))
                        {
                            Class mgr = c;
                            for (Method m : mgr.getDeclaredMethods())
                            {
                                if (methodName.equals(methodName) && Modifier.isPublic(m.getModifiers()) && Modifier.isStatic(m.getModifiers()) && (m.getName().startsWith("has") || m.getName().startsWith("list")))
                                {
                                    return true;
                                }
                            }
                        }
                    }
                }

            }
            catch (ClassNotFoundException cnfe)
            {
                log.error(cnfe);
            }
            return false;
        }

        public SemanticModelResourceModule(PublishDefinition definition)
        {
            // adds methods
            this.definition = definition;
            try
            {
                Class clazzjava = Class.forName(definition.getSemanticClass().getClassName());
                Class superclazz = clazzjava.getSuperclass();
                if (superclazz.getName().endsWith("Base"))
                {
                    for (Class c : superclazz.getDeclaredClasses())
                    {
                        if (c.getName().endsWith("ClassMgr"))
                        {
                            Class mgr = c;

                            for (Method m : mgr.getDeclaredMethods())
                            {
                                if (Modifier.isPublic(m.getModifiers()) && Modifier.isStatic(m.getModifiers()) && (m.getName().startsWith("has") || m.getName().startsWith("list")))
                                {
                                    if (hasModel(m))
                                    {
                                        ModelMethodModule method = new ModelMethodModule(m, definition);
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

        }

        @Override
        public String getId()
        {
            return "ModelFunctionsOf_" + definition.getSemanticClass().getPrefix() + "_" + definition.getSemanticClass().getName();
        }
    }

    public SemanticClassPublisher()
    {
        for (PublishDefinition definition : definitions)
        {
            addSemanticClass(definition.getSemanticClass());
        }
    }

    public void addSemanticClass(PublishDefinition definition)
    {
        definitions.add(definition);
        SemanticClass clazz = definition.getSemanticClass();
        SemanticResourceModule resourceModule = new SemanticResourceModule(clazz);

        GetMethodModule get = new GetMethodModule(definition);
        resourceModule.methods.put(get.getId(), get);

        PostModule post = new PostModule(definition);
        resourceModule.methods.put(post.getId(), post);

        PutModule put = new PutModule(definition);
        resourceModule.methods.put(put.getId(), put);

        DeleteModule delete = new DeleteModule(definition);
        resourceModule.methods.put(delete.getId(), delete);

        // add subResourcemodules

        SemanticFunctionsResourceModule functions = new SemanticFunctionsResourceModule(definition);

        resourceModule.subResources.put(functions.getId(), functions);

        SemanticModelResourceModule model = new SemanticModelResourceModule(definition);
        functions.subResources.put(model.getId(), model);

        resourceModules.put(resourceModule.getId(), resourceModule);
    }

    public void addSemanticClass(SemanticClass clazz, RepresentationMediaType... publishType)
    {
        Collection<RepresentationMediaType> types = new ArrayList<RepresentationMediaType>();
        types.addAll(Arrays.asList(publishType));
        EnumSet<RepresentationMediaType> enumset = EnumSet.copyOf(types);
        addSemanticClass(clazz, enumset);
    }

    public void addSemanticClass(SemanticClass clazz, Set<RepresentationMediaType> publishType)
    {
        PublishDefinition definition = new PublishDefinition(clazz, publishType);
        addSemanticClass(definition);
    }

    public void addSemanticClass(SemanticClass clazz)
    {
        PublishDefinition definition = new PublishDefinition(clazz);
        addSemanticClass(definition);
    }

    public void removeSemanticClass(SemanticClass clazz)
    {
        SemanticResourceModule resourceModule = new SemanticResourceModule(clazz);
        resourceModules.remove(resourceModule.getId());
        for (PublishDefinition def : definitions)
        {
            if (def.getSemanticClass().getURI().equals(clazz.getURI()))
            {
                definitions.remove(def);
                break;
            }
        }
    }

    public void addSemanticClass(URI clazz, RepresentationMediaType... publishType)
    {
        SemanticClass clazzToAdd = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass(clazz.toString());
        if (clazzToAdd != null)
        {
            addSemanticClass(clazzToAdd, publishType);
        }
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

        for (PublishDefinition definition : definitions)
        {
            SemanticClass clazz = definition.getSemanticClass();
            if (!prefixes.contains(clazz.getPrefix()))
            {
                String uri = clazz.getURI();
                int pos = uri.indexOf("#");
                if (pos != -1)
                {
                    uri = uri.substring(0, pos);
                }
                application.setAttribute("xmlns:" + clazz.getPrefix(), uri);
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

        for (PublishDefinition definition : definitions)
        {
            for (Representation representation : definition.getRepresentations())
            {
                representation.addIncludes(grammars, servletRequest);
            }
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
        Element eError = doc.createElementNS(REST_RESOURCES_2010, "Error");
        Attr xmlns = doc.createAttribute("xmlns");
        xmlns.setValue(REST_RESOURCES_2010);
        eError.setAttributeNode(xmlns);

        doc.appendChild(eError);
        for (String error : errors)
        {
            Element message = doc.createElementNS(REST_RESOURCES_2010, "Message");
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
        if ("json".equalsIgnoreCase(request.getParameter("format")))
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
                                    data.put("shorturi", value.getShortURI());
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
                                data.put("shorturi", value.getShortURI());
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
        ///swb/rest/so/swbcomm_EventElement?uri=http%3A%2F%2Fwww.reg_digital_demo.swb%23swbcomm_EventElement%3A35&uri=http%3A%2F%2Fwww.reg_digital_demo.swb%23swbcomm_EventElement%3A35&
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
        String namespace = obj.getSemanticClass().getURI();
        int pos = namespace.indexOf("#");
        if (pos != -1)
        {
            namespace = namespace.substring(0, pos);
        }
        Element name = doc.createElementNS(namespace, obj.getSemanticClass().getName());

        name.setPrefix(obj.getSemanticClass().getPrefix());
        name.setAttribute("xmlns", namespace);
        name.setAttribute("xmlns:" + obj.getSemanticClass().getPrefix(), namespace);
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
        String prefix = obj.getSemanticClass().getPrefix();
        String namespace = obj.getSemanticClass().getURI();
        int pos = namespace.indexOf("#");
        if (pos != -1)
        {
            namespace = namespace.substring(0, pos);
        }
        Document doc = name.getOwnerDocument();
        DateToXsdDatetimeFormatter f = new DateToXsdDatetimeFormatter();
        //Iterator<SemanticProperty> props = obj.listProperties();
        Iterator<SemanticProperty> props = obj.getSemanticClass().listProperties(); // list in the same order that xsd
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
                            if (value.getString() != null && !"".equals(value.getString()))
                            {
                                Element eprop = doc.createElementNS(namespace, prop.getName());
                                eprop.setPrefix(prefix);
                                name.appendChild(eprop);
                                String _value = value.getString();
                                if (prop.isDate())
                                {
                                    Date date_value = value.getDate();
                                    _value = f.formatDate(date_value);

                                }
                                if (prop.isDateTime())
                                {
                                    Timestamp date_value = value.getDateTime();
                                    _value = f.formatDateTime(new Date(date_value.getTime()));
                                }
                                Text tvalue = doc.createTextNode(_value);
                                eprop.appendChild(tvalue);
                            }
                        }
                    }
                    else
                    {
                        String _value = obj.getProperty(prop);
                        if (_value != null && !"".equals(_value))
                        {
                            if (prop.isDate())
                            {
                                Date date_value = obj.getDateProperty(prop);
                                _value = f.formatDate(date_value);

                            }
                            if (prop.isDateTime())
                            {
                                Timestamp date_value = obj.getDateTimeProperty(prop);
                                _value = f.formatDateTime(new Date(date_value.getTime()));
                            }
                            Element eprop = doc.createElementNS(namespace, prop.getName());
                            eprop.setPrefix(prefix);
                            name.appendChild(eprop);
                            Text tvalue = doc.createTextNode(_value);
                            eprop.appendChild(tvalue);
                        }
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
                                    Element eprop = doc.createElementNS(namespace, prop.getName());
                                    eprop.setPrefix(prefix);
                                    name.appendChild(eprop);

                                    /*Attr href = doc.createAttributeNS(namespace, "href");
                                    href.setPrefix(prefix);
                                    href.setValue(getPathForObject(value, basepath));
                                    eprop.setAttributeNode(href);*/
                                    //eprop.setAttributeNS(REST_RESOURCES_2010, "href", getPathForObject(value, basepath));

                                    Text data = doc.createTextNode(value.getURI());
                                    eprop.appendChild(data);
                                }
                            }
                        }
                        else
                        {
                            Element eprop = doc.createElementNS(namespace, prop.getName());
                            eprop.setPrefix(prefix);
                            name.appendChild(eprop);
                            SemanticObject value = obj.getObjectProperty(prop);
                            if (value != null)
                            {
                                //eprop.setAttributeNS(namespace, "href", getPathForObject(value, basepath));
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
        Iterator<SemanticProperty> props = obj.getSemanticClass().listProperties();
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
                                log.debug("new value for property "+prop.getName()+" value: "+value.toString());
                                obj.addLiteralProperty(prop, literal);
                            }

                        }

                    }
                    else
                    {
                        if (values != null)
                        {
                            if (values.length > 1)
                            {
                                log.debug("values.length: "+values.length);
                                throw new Exception("The property " + prop.getName() + " has single value");
                            }
                            if (values[0] != null)
                            {
                                Object value = get(values[0], type);
                                if (value instanceof Date)
                                {
                                    log.debug("new value for property "+prop.getName()+" value: "+value.toString());
                                    obj.setDateProperty(prop, (Date) value);
                                }
                                if (value instanceof Timestamp)
                                {
                                    log.debug("new value for property "+prop.getName()+" value: "+value.toString());
                                    obj.setDateTimeProperty(prop, (Timestamp) value);
                                }
                                if (value instanceof Boolean)
                                {
                                    log.debug("new value for property "+prop.getName()+" value: "+value.toString());
                                    obj.setBooleanProperty(prop, ((Boolean) value).booleanValue());
                                }
                                else
                                {
                                    log.debug("new value for property "+prop.getName()+" value: "+value.toString());
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
                                    log.debug("The object with uri " + uri + " was not found");
                                    throw new Exception("The object with uri " + uri + " was not found");
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
        if ("json".equalsIgnoreCase(request.getParameter("format")))
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
        /*Document doc = SWBUtils.XML.getNewDocument();
        Element created = doc.createElement("Created");
        doc.appendChild(created);
        Attr xmlns = doc.createAttribute("xmlns");
        xmlns.setValue(REST_RESOURCES_2010);
        created.appendChild(xmlns);
        Text data = doc.createTextNode(uri);
        created.appendChild(data);
        return doc;*/

        Document doc = SWBUtils.XML.getNewDocument();
        String prefix="created";
        Element updated = doc.createElementNS(REST_RESOURCES_2010,"Created");
        updated.setPrefix(prefix);

        Attr xmlns = doc.createAttribute("xmlns");
        xmlns.setValue(REST_RESOURCES_2010);
        updated.setAttributeNode(xmlns);

        updated.setAttribute("xmlns:"+prefix, REST_RESOURCES_2010);


        doc.appendChild(updated);
        Text data = doc.createTextNode(uri);
        updated.appendChild(data);
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
        if ("json".equalsIgnoreCase(request.getParameter("format")))
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
        String prefix="updated";
        Element updated = doc.createElementNS(REST_RESOURCES_2010,"Updated");
        updated.setPrefix(prefix);

        Attr xmlns = doc.createAttribute("xmlns");
        xmlns.setValue(REST_RESOURCES_2010);
        updated.setAttributeNode(xmlns);

        updated.setAttribute("xmlns:"+prefix, REST_RESOURCES_2010);
        

        doc.appendChild(updated);
        Text data = doc.createTextNode(Boolean.toString(isUpdated));
        updated.appendChild(data);
        return doc;
    }

    private void showDeleted(HttpServletRequest request, HttpServletResponse response, boolean isdeleted) throws IOException
    {
        if ("json".equalsIgnoreCase(request.getParameter("format")))
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
        String prefix="deleted";
        Element updated = doc.createElementNS(REST_RESOURCES_2010,"Deleted");
        updated.setPrefix(prefix);

        Attr xmlns = doc.createAttribute("xmlns");
        xmlns.setValue(REST_RESOURCES_2010);
        updated.setAttributeNode(xmlns);

        updated.setAttribute("xmlns:"+prefix, REST_RESOURCES_2010);


        doc.appendChild(updated);
        Text data = doc.createTextNode(Boolean.toString(isdeleted));
        updated.appendChild(data);
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

    private boolean hasModel(Method method)
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

    private void showXSD(HttpServletRequest request, HttpServletResponse response, SemanticClass clazz) throws IOException
    {
        String version = "2009";
        if ("2006".equals(request.getParameter("version")))
        {
            version = "2006";
        }
        Document doc = getXSD(clazz, version);
        showDocument(response, doc);
    }

    private void showErrorXSD(HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        Document doc = getErrorXSD();
        showDocument(response, doc);
    }

    private void showCreatedXSDResponse(HttpServletResponse response) throws IOException
    {
        Document doc = getCreatedXSD();
        showDocument(response, doc);
    }

    private void showUpdatedXSDResponse(HttpServletResponse response) throws IOException
    {
        Document doc = getUpdatedXSD();
        showDocument(response, doc);
    }

    private void showDeletedXSDResponse(HttpServletResponse response) throws IOException
    {
        Document doc = getDeletedXSD();
        showDocument(response, doc);
    }

    private void showCLSMGRXSD(HttpServletResponse response, SemanticClass clazz) throws IOException
    {
        Document doc = getClsMgrXSD(clazz);
        showDocument(response, doc);
    }

    private Document getXSD(final SemanticClass clazz, final String version)
    {
        Document doc = SWBUtils.XML.getNewDocument();
        Element schema = doc.createElementNS(W3C_XML_SCHEMA_NS_URI, "schema");
        String uri = clazz.getURI();
        int pos = uri.indexOf("#");
        if (pos != -1)
        {
            uri = uri.substring(0, pos);
        }
        schema.setAttribute("targetNamespace", uri);
        Attr attr = doc.createAttribute("xmlns");
        attr.setValue(clazz.getURI());
        schema.setAttributeNode(attr);

        schema.setAttribute("xmlns:" + clazz.getPrefix(), clazz.getURI());

        schema.setAttribute("xmlns:swbrest", REST_RESOURCES_2010);
        doc.appendChild(schema);
        schema.setPrefix(XSD_PREFIX);
        addXSD(schema, clazz);

        return doc;
    }

    private Document getErrorXSD()
    {
        Document doc = SWBUtils.XML.getNewDocument();
        Element schema = doc.createElementNS(W3C_XML_SCHEMA_NS_URI, "schema");
        schema.setAttribute("targetNamespace", REST_RESOURCES_2010);
        Attr attr = doc.createAttribute("xmlns");
        attr.setValue(REST_RESOURCES_2010);
        schema.setAttributeNode(attr);

        doc.appendChild(schema);
        schema.setPrefix(XSD_PREFIX);
        Element element = doc.createElementNS(W3C_XML_SCHEMA_NS_URI, ELEMENT);
        schema.appendChild(element);
        element.setAttribute(NAME, "Error");
        element.setPrefix(XSD_PREFIX);
        Element complexType = doc.createElementNS(W3C_XML_SCHEMA_NS_URI, "complexType");
        complexType.setPrefix(XSD_PREFIX);
        element.appendChild(complexType);

        Element sequence = doc.createElementNS(W3C_XML_SCHEMA_NS_URI, "sequence");
        sequence.setPrefix(XSD_PREFIX);
        complexType.appendChild(sequence);


        Element message = doc.createElementNS(W3C_XML_SCHEMA_NS_URI, ELEMENT);
        message.setPrefix(XSD_PREFIX);
        message.setAttribute(NAME, "Message");
        message.setAttribute(TYPE, XSD_STRING);
        sequence.appendChild(message);
        message.setAttribute("minOccurs", "1");
        message.setAttribute("maxOccurs", "unbounded");
        return doc;
    }

    private Document getCreatedXSD()
    {
        Document doc = SWBUtils.XML.getNewDocument();
        Element schema = doc.createElementNS(W3C_XML_SCHEMA_NS_URI, "schema");
        schema.setAttribute("targetNamespace", REST_RESOURCES_2010);

        Attr attr = doc.createAttribute("xmlns");
        attr.setValue(REST_RESOURCES_2010);
        schema.setAttributeNode(attr);

        doc.appendChild(schema);
        schema.setPrefix(XSD_PREFIX);
        Element element = doc.createElementNS(W3C_XML_SCHEMA_NS_URI, ELEMENT);
        schema.appendChild(element);
        element.setAttribute(NAME, "Created");
        element.setAttribute(TYPE, XSD_ANYURI);
        element.setPrefix(XSD_PREFIX);
        return doc;
    }

    private void addXSD(final Element schema, final SemanticClass clazz)
    {
        Document doc = schema.getOwnerDocument();
        boolean exists = false;
        NodeList childs = schema.getChildNodes();
        for (int i = 0; i < childs.getLength(); i++)
        {
            Node node = childs.item(i);
            if (node instanceof Element)
            {
                Element element = (Element) node;
                String name = element.getAttribute(NAME);
                if (name.equals(clazz.getName()))
                {
                    exists = true;
                    break;
                }
            }
        }

        if (!exists && clazz.isSWBClass())
        {
            HashSet<SemanticClass> ranges = new HashSet<SemanticClass>();
            Element element = doc.createElementNS(W3C_XML_SCHEMA_NS_URI, ELEMENT);
            schema.appendChild(element);
            element.setAttribute(NAME, clazz.getName());
            element.setPrefix(XSD_PREFIX);
            addPropertiesToXSD(clazz, element, ranges);
        }
    }

    private Document getUpdatedXSD()
    {
        Document doc = SWBUtils.XML.getNewDocument();
        Element schema = doc.createElementNS(W3C_XML_SCHEMA_NS_URI, "schema");
        schema.setAttribute("targetNamespace", REST_RESOURCES_2010);
        Attr attr = doc.createAttribute("xmlns");
        attr.setValue(REST_RESOURCES_2010);
        schema.setAttributeNode(attr);

        doc.appendChild(schema);
        schema.setPrefix(XSD_PREFIX);
        Element element = doc.createElementNS(W3C_XML_SCHEMA_NS_URI, ELEMENT);
        schema.appendChild(element);
        element.setAttribute(NAME, "Updated");
        element.setAttribute(TYPE, XSD_BOOLEAN);
        element.setPrefix(XSD_PREFIX);

        return doc;
    }

    private Document getDeletedXSD()
    {
        Document doc = SWBUtils.XML.getNewDocument();
        Element schema = doc.createElementNS(W3C_XML_SCHEMA_NS_URI, "schema");
        schema.setAttribute("targetNamespace", REST_RESOURCES_2010);
        Attr attr = doc.createAttribute("xmlns");
        attr.setValue(REST_RESOURCES_2010);
        schema.setAttributeNode(attr);

        doc.appendChild(schema);
        schema.setPrefix(XSD_PREFIX);
        Element element = doc.createElementNS(W3C_XML_SCHEMA_NS_URI, ELEMENT);
        schema.appendChild(element);
        element.setAttribute(NAME, "Deleted");
        element.setAttribute(TYPE, XSD_BOOLEAN);
        element.setPrefix(XSD_PREFIX);
        return doc;
    }

    private Document getClsMgrXSD(SemanticClass clazz)
    {
        Document doc = SWBUtils.XML.getNewDocument();
        Element schema = doc.createElementNS(W3C_XML_SCHEMA_NS_URI, "schema");
        schema.setAttribute("targetNamespace", REST_RESOURCES_2010);

        Attr attr = doc.createAttribute("xmlns");
        attr.setValue(REST_RESOURCES_2010);
        schema.setAttributeNode(attr);
        doc.appendChild(schema);
        HashSet<SemanticClass> ranges = new HashSet<SemanticClass>();
        addClassManagerResultToXSD(schema, clazz, ranges);
        return doc;
    }

    private void addPropertiesToXSD(SemanticClass clazz, Element element, HashSet<SemanticClass> ranges)
    {
        Document doc = element.getOwnerDocument();
        Element complexType = doc.createElementNS(W3C_XML_SCHEMA_NS_URI, "complexType");
        complexType.setPrefix(XSD_PREFIX);
        element.appendChild(complexType);

        Element sequence = doc.createElementNS(W3C_XML_SCHEMA_NS_URI, "sequence");


        sequence.setPrefix(XSD_PREFIX);
        complexType.appendChild(sequence);

        Iterator<SemanticProperty> props = clazz.listProperties();
        while (props.hasNext())
        {
            SemanticProperty prop = props.next();
            if (!prop.hasInverse())
            {
                if (prop.isDataTypeProperty())
                {
                    Element property = doc.createElementNS(W3C_XML_SCHEMA_NS_URI, ELEMENT);
                    property.setPrefix(XSD_PREFIX);
                    property.setAttribute("form", "qualified");
                    //property.setAttribute("use", "required");
                    property.setAttribute(NAME, prop.getName());
                    sequence.appendChild(property);
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
                    property.setAttribute(TYPE, type);
                    if (prop.getName().startsWith("has"))
                    {
                        property.setAttribute("minOccurs", "0");
                        property.setAttribute("maxOccurs", "unbounded");
                    }
                    else
                    {
                        property.setAttribute("minOccurs", "0");
                        property.setAttribute("maxOccurs", "1");
                    }

                }
                else
                {
                    SemanticClass range = prop.getRangeClass();
                    if (range != null)
                    {
                        ranges.add(range);
                        Element property = doc.createElementNS(W3C_XML_SCHEMA_NS_URI, ELEMENT);
                        sequence.appendChild(property);
                        property.setAttribute(NAME, prop.getName());
                        property.setAttribute("form", "qualified");
                        property.setAttribute("type", XSD_ANYURI);
                        if (prop.getName().startsWith("has"))
                        {
                            property.setAttribute("minOccurs", "0");
                            property.setAttribute("maxOccurs", "unbounded");
                        }
                        else
                        {
                            property.setAttribute("minOccurs", "0");
                            property.setAttribute("maxOccurs", "1");
                        }
                        property.setPrefix(XSD_PREFIX);

                        /*complexType = doc.createElementNS(W3C_XML_SCHEMA_NS_URI, "complexType");
                        complexType.setPrefix(XSD_PREFIX);
                        property.appendChild(complexType);

                        Element simpleContent = doc.createElementNS(W3C_XML_SCHEMA_NS_URI, "simpleContent");
                        simpleContent.setPrefix(XSD_PREFIX);
                        complexType.appendChild(simpleContent);

                        Element extension = doc.createElementNS(W3C_XML_SCHEMA_NS_URI, "extension");
                        extension.setPrefix(XSD_PREFIX);
                        extension.setAttribute("base", XSD_ANYURI);
                        simpleContent.appendChild(extension);

                        Element attribute = doc.createElementNS(W3C_XML_SCHEMA_NS_URI, "attribute");
                        attribute.setPrefix(XSD_PREFIX);
                        extension.appendChild(attribute);
                        attribute.setAttribute(NAME, "href");
                        attribute.setAttribute("use", "required");
                        attribute.setAttribute("form", "qualified");
                        attribute.setAttribute(TYPE, XSD_ANYURI);*/
                    }
                }
            }
        }
    }

    private void addClassManagerResultToXSD(final Element schema, final SemanticClass clazz, HashSet<SemanticClass> ranges)
    {
        Document doc = schema.getOwnerDocument();
        try
        {
            Class main = Class.forName(clazz.getClassName());
            Class msgr = getClassManager(main);
            HashSet<String> names = new HashSet<String>();
            for (Method m : msgr.getDeclaredMethods())
            {
                if (Modifier.isPublic(m.getModifiers()) && Modifier.isStatic(m.getModifiers()) && (m.getName().startsWith("has") || m.getName().startsWith("list")))
                {
                    String name = m.getName();
                    if (!names.contains(name))
                    {
                        names.add(name);
                        Class returnType = m.getReturnType();
                        Element methodElement = doc.createElementNS(W3C_XML_SCHEMA_NS_URI, ELEMENT);
                        methodElement.setPrefix(XSD_PREFIX);
                        methodElement.setAttribute(NAME, name);
                        schema.appendChild(methodElement);
                        if (returnType instanceof Class && isGenericObject(returnType)) // is GenericObject
                        {
                            // gets SemanticClass;
                            Field sclass = ((Class) returnType).getField("sclass");
                            Object objClass = sclass.get(null);
                            if (objClass instanceof SemanticClass)
                            {
                                SemanticClass returnSemanticClazz = (SemanticClass) objClass;
                                addPropertiesToXSD(returnSemanticClazz, methodElement, ranges);
                            }
                        }
                        else if (returnType.equals(Iterator.class)) // GenericIterator
                        {
                            if (m.getGenericReturnType() instanceof ParameterizedType)
                            {
                                if (((ParameterizedType) m.getGenericReturnType()).getActualTypeArguments() != null && ((ParameterizedType) m.getGenericReturnType()).getActualTypeArguments().length > 0)
                                {
                                    Type actual = ((ParameterizedType) m.getGenericReturnType()).getActualTypeArguments()[0];
                                    if (actual instanceof Class)
                                    {
                                        Class _returnType = (Class) actual;
                                        Field sclass = ((Class) _returnType).getField("sclass");
                                        Object objClass = sclass.get(null);
                                        if (objClass instanceof SemanticClass)
                                        {
                                            SemanticClass returnSemanticClazz = (SemanticClass) objClass;
                                            Element complex = doc.createElementNS(W3C_XML_SCHEMA_NS_URI, "complexType");
                                            complex.setPrefix(XSD_PREFIX);
                                            methodElement.appendChild(complex);

                                            Element sequence = doc.createElementNS(W3C_XML_SCHEMA_NS_URI, "sequence");
                                            sequence.setPrefix(XSD_PREFIX);
                                            complex.appendChild(sequence);




                                            Element child = doc.createElementNS(W3C_XML_SCHEMA_NS_URI, ELEMENT);
                                            child.setPrefix(XSD_PREFIX);
                                            child.setAttribute(NAME, returnSemanticClazz.getName());
                                            child.setAttribute("form", "qualified");
                                            child.setAttribute("maxOccurs", "unbounded");
                                            child.setAttribute("minOccurs", "0");
                                            //child.setAttribute(TYPE, XSD_ANYURI);
                                            Element complexType = doc.createElementNS(W3C_XML_SCHEMA_NS_URI, "complexType");
                                            child.appendChild(complexType);

                                            Element simpleContent = doc.createElementNS(W3C_XML_SCHEMA_NS_URI, "simpleContent");
                                            simpleContent.setPrefix(XSD_PREFIX);
                                            complexType.appendChild(simpleContent);

                                            Element extension = doc.createElementNS(W3C_XML_SCHEMA_NS_URI, "extension");
                                            extension.setAttribute("base", XSD_ANYURI);
                                            extension.setPrefix(XSD_PREFIX);
                                            simpleContent.appendChild(extension);


                                            Element shortURI = doc.createElementNS(W3C_XML_SCHEMA_NS_URI, "attribute");
                                            shortURI.setPrefix(XSD_PREFIX);
                                            shortURI.setAttribute("form", "qualified");
                                            shortURI.setAttribute("use", "required");
                                            shortURI.setAttribute(NAME, "shortURI");
                                            shortURI.setAttribute(TYPE, XSD_STRING);
                                            extension.appendChild(shortURI);

                                            Element href = doc.createElementNS(W3C_XML_SCHEMA_NS_URI, "attribute");
                                            href.setPrefix(XSD_PREFIX);
                                            href.setAttribute("form", "qualified");
                                            href.setAttribute("use", "required");
                                            href.setAttribute(NAME, "href");
                                            href.setAttribute(TYPE, XSD_ANYURI);
                                            extension.appendChild(href);
                                            sequence.appendChild(child);
                                        }
                                    }
                                }

                            }
                        }
                        else
                        {
                            if (returnType instanceof Class)
                            {
                                methodElement.setAttribute(TYPE, classToxsd((Class) returnType));
                            }
                        }
                    }
                }
            }
        }
        catch (Exception cnfe)
        {
            log.error(cnfe);
        }

    }

    private Class getClassManager(Class clazz) throws Exception
    {
        Class superclazz = clazz.getSuperclass();
        if (superclazz.getName().endsWith("Base"))
        {
            for (Class c : superclazz.getDeclaredClasses())
            {
                if (c.getName().endsWith("ClassMgr"))
                {
                    return c;
                }
            }
        }
        throw new Exception("The class is not a SemanticClass");
    }
}
