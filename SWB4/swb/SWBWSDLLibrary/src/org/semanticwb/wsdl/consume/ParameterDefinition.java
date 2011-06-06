/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.wsdl.consume;

import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;
import org.jdom.Namespace;
import org.jdom.input.SAXBuilder;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.wsdl.util.MemoryClassLoader;
import org.semanticwb.wsdl.util.XMLDocumentUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author victor.lorenzana
 */
public class ParameterDefinition
{

    private static final Logger log = SWBUtils.getLogger(Operation.class);
    private static final String SCHEMA_NAMESPACE = "http://www.w3.org/2001/XMLSchema";
    private static final String NL = "\r\n";
    private final String name;
    private final String type;
    //private final Element part;
    private Element definition;
    private String namespace;
    private final HashMap<Class, ClassInfo> classes = new HashMap<Class, ClassInfo>();
    private final Map<String, String> _classes = new HashMap<String, String>();
    private final Map<String, Class> classDictionary = new HashMap<String, Class>();
    private final Map<String, String> tagnames = new HashMap<String, String>();
    private Class clazz;
    private final MemoryClassLoader l = new MemoryClassLoader(getClass().getClassLoader());
    private final Operation operation;

    public ParameterDefinition(Element part, Operation operation) throws ServiceException
    {
        this.operation = operation;
        //this.part = part;
        String _name = "";
        if (part.getAttribute("name") != null)
        {
            _name = part.getAttribute("name");
        }
        this.name = _name;
        String _type = null;
        if (!part.getAttribute("type").equals(""))
        {
            _type = part.getAttribute("type");
        }
        if (!part.getAttribute("element").equals(""))
        {
            _type = part.getAttribute("element");
            Element[] definitions = XMLDocumentUtil.getElement(_type, part.getOwnerDocument(), "element");
            if (definitions.length > 0)
            {
                definition = definitions[0];
                Element schema = getSchema(definition);
                if (schema == null)
                {
                    Document doc = definition.getOwnerDocument();
                    schema = doc.getDocumentElement();
                }
                String elementName = definition.getAttribute("name");
                this.namespace = schema.getAttribute("targetNamespace");
                final String className = toUpperCase(elementName);
                tagnames.put(className, elementName);
                final String code = getCode(definition, className);
                _classes.put(className, code);
                l.addAll(_classes);

                for (String classNameToAdd : _classes.keySet())
                {
                    try
                    {
                        Class _clazz = l.loadClass(classNameToAdd);
                        this.classDictionary.put(classNameToAdd, _clazz);
                        if (classNameToAdd.equals(className))
                        {
                            this.clazz = _clazz;
                        }

                        //ClassInfo info = new ClassInfo(_clazz, code,elementName,namespace,tagname);
                        ClassInfo info = new ClassInfo(_clazz, code, elementName, namespace, this, tagnames);
                        this.classes.put(_clazz, info);
                    }
                    catch (ClassNotFoundException cnfe)
                    {
                        log.error(cnfe);
                        throw new ServiceException(cnfe);
                    }
                }




            }
            if (_type.startsWith("tns:"))
            {
                _type = _type.substring(4);
            }

        }
        this.type = _type;

    }

    public final Element getSchema(final Node element)
    {
        if (element == null)
        {
            return null;
        }
        Node parent = element.getParentNode();
        if (parent == null)
        {
            return null;
        }
        if (parent instanceof Element)
        {
            Element testSchema = (Element) parent;
            if (testSchema.getNamespaceURI().equals(element.getNamespaceURI()) && testSchema.getLocalName().equals("schema"))
            {
                return testSchema;
            }
            parent = parent.getParentNode();
            return getSchema(parent);
        }
        else
        {
            return getSchema(parent);
        }
    }

    public static String toUpperCase(String data)
    {
        String letter = data.substring(0, 1);
        return "Class" + letter.toUpperCase() + data.substring(1).toLowerCase();
    }

    private void getCode(Element element, String className, StringBuilder sb)
    {
        String _name = element.getLocalName();
        if (_name.equals("complexType"))
        {
            NodeList childs = element.getChildNodes();
            for (int i = 0; i < childs.getLength(); i++)
            {
                if (childs.item(i) instanceof Element)
                {
                    Element childElement = (Element) childs.item(i);
                    getCode(childElement, className, sb);

                }
            }
        }
        if (_name.equals("restriction"))
        {

        }
        if (_name.equals("simpleType"))
        {
            NodeList childs = element.getChildNodes();
            for (int i = 0; i < childs.getLength(); i++)
            {
                if (childs.item(i) instanceof Element)
                {
                    Element childElement = (Element) childs.item(i);
                    getCode(childElement, className, sb);

                }
            }
        }
        if (_name.equals("sequence"))
        {
            NodeList childs = element.getChildNodes();
            for (int i = 0; i < childs.getLength(); i++)
            {
                if (childs.item(i) instanceof Element)
                {
                    Element childElement = (Element) childs.item(i);
                    getCode(childElement, className, sb);

                }
            }
        }
        if (_name.equals("element"))
        {
            String _tagname = element.getAttribute("name");
            if (_tagname.equals(""))
            {
                String ref = element.getAttribute("ref");
                int pos = ref.indexOf(":");
                if (pos != -1)
                {
                    String prefix = ref.substring(0, pos);
                    String localname = ref.substring(pos + 1);
                    Document doc = element.getOwnerDocument();
                    String xml = SWBUtils.XML.domToXml(doc, "utf-8", true);
                    StringReader r = new StringReader(xml);
                    SAXBuilder builder = new SAXBuilder();
                    String uri = null;
                    try
                    {
                        org.jdom.Document jdom = builder.build(r);
                        Namespace ns = jdom.getRootElement().getNamespace(prefix);
                        uri = ns.getURI();
                    }
                    catch (Exception e)
                    {
                        log.error(e);
                    }
                    if (localname.equals("schema") && SCHEMA_NAMESPACE.equals(uri))
                    {
                    }
                    else
                    {
                    }
                }

            }
            String varname = element.getAttribute("name").toLowerCase();
            tagnames.put(varname, _tagname);
            String varType = element.getAttribute("type");
            if (varType.equals(""))
            {
                String _className = toUpperCase(_tagname);
                String code = getCode(element, className);
                _classes.put(_className, code);
                String minOccurs = element.getAttribute("minOccurs");
                String maxOccurs = element.getAttribute("maxOccurs");
                String _varname = varname;
                if ("0".equals(minOccurs) && "unbounded".equals(maxOccurs))
                {
                    sb.append(" public final java.util.ArrayList<").append(_className).append("> ").append(_varname).append("=new java.util.ArrayList<").append(_className).append(">();" + NL);
                }
                if (("".equals(minOccurs) || "1".equals(minOccurs) || "0".equals(minOccurs)) && ("1".equals(maxOccurs) || "".equals(maxOccurs)))
                {
                    if("1".equals(minOccurs) && "1".equals(maxOccurs))
                    {
                        sb.append("@Required" + NL);
                    }
                    sb.append(" public ").append(_className).append(" ").append(_varname).append(";" + NL);
                }
                return;
            }
            Document doc = element.getOwnerDocument();
            boolean isBasic = false;
            int pos = varType.indexOf(":");
            if (pos != -1)
            {

                String prefix = varType.substring(0, pos);
                varType = varType.substring(pos + 1);
                if (!prefix.equals("tns"))
                {
                    String xml = SWBUtils.XML.domToXml(doc, "utf-8", true);
                    StringReader r = new StringReader(xml);
                    SAXBuilder builder = new SAXBuilder();
                    try
                    {
                        org.jdom.Document jdom = builder.build(r);
                        Namespace ns = jdom.getRootElement().getNamespace(prefix);
                        String uri = ns.getURI();
                        if (uri != null && uri.equals(SCHEMA_NAMESPACE))
                        {
                            isBasic = true;
                            if ("string".equals(varType))
                            {
                                varType = "String";
                            }
                            if ("int".equals(varType))
                            {
                                varType = "int";
                            }
                            if ("long".equals(varType))
                            {
                                varType = "long";
                            }
                            if ("boolean".equals(varType))
                            {
                                varType = "boolean";
                            }
                            if ("float".equals(varType))
                            {
                                varType = "float";
                            }
                            if ("short".equals(varType))
                            {
                                varType = "short";
                            }
                            if ("byte".equals(varType))
                            {
                                varType = "byte";
                            }
                            if ("datetime".equals(varType))
                            {
                                varType = "java.util.Date";
                            }
                        }
                    }
                    catch (Exception e)
                    {
                        log.error(e);
                    }

                }
            }
            if (!isBasic)
            {

                String _varname = _tagname.toLowerCase();
                String _className = toUpperCase(_tagname);
                tagnames.put(_className, _tagname);
                tagnames.put(_varname, _tagname);
                Element[] elements = XMLDocumentUtil.getElement(varType, element.getOwnerDocument(), "element");
                if (elements.length == 0)
                {
                    elements = XMLDocumentUtil.getElement(varType, element.getOwnerDocument(), "complexType");
                }
                if (elements.length == 0)
                {
                    elements = XMLDocumentUtil.getElement(varType, element.getOwnerDocument(), "simpleType");
                }
                if (elements.length > 0)
                {
                    Element elementToCode = elements[0];
                    String code = getCode(elementToCode, _className);
                    _classes.put(_className, code);
                }

                String minOccurs = element.getAttribute("minOccurs");
                String maxOccurs = element.getAttribute("maxOccurs");
                if ("0".equals(minOccurs) && "unbounded".equals(maxOccurs))
                {
                    sb.append(" public final java.util.ArrayList<").append(_className).append("> ").append(_varname).append("=new java.util.ArrayList<").append(_className).append(">();" + NL);
                }
                if (("".equals(minOccurs) || "1".equals(minOccurs) || "0".equals(minOccurs)) && ("1".equals(maxOccurs) || "".equals(maxOccurs)))
                {
                    if("1".equals(minOccurs) && "1".equals(maxOccurs))
                    {
                        sb.append("@Required" + NL);
                    }
                    sb.append(" public ").append(_className).append(" ").append(_varname).append(";" + NL);
                }


            }
            else
            {
                String tagname = element.getAttribute("name");
                tagnames.put(varname.toLowerCase(), tagname);
                String minOccurs = element.getAttribute("minOccurs");
                String maxOccurs = element.getAttribute("maxOccurs");
                if ("0".equals(minOccurs) && "unbounded".equals(maxOccurs))
                {
                    sb.append(" public final java.util.ArrayList<").append(varType).append("> ").append(varname.toLowerCase()).append("=new java.util.ArrayList<").append(varType).append(">();" + NL);
                }
                if (("".equals(minOccurs) || "1".equals(minOccurs) || "0".equals(minOccurs)) && ("1".equals(maxOccurs) || "".equals(maxOccurs)))
                {
                    sb.append(" public ").append(varType).append(" ").append(varname.toLowerCase()).append(";" + NL);
                }
            }

        }
    }

    private String getCode(Element element, String className)
    {
        StringBuilder sb = new StringBuilder();
        sb.append("import org.semanticwb.wsdl.consume.Required;" + NL);
        sb.append("" + NL);
        sb.append("public class ").append(className).append(" {" + NL);
        sb.append("" + NL);
        sb.append("     public ").append(className).append("()" + NL);
        sb.append("     {" + NL);
        sb.append("" + NL);
        sb.append("     }" + NL);
        NodeList childs = element.getChildNodes();
        for (int i = 0; i < childs.getLength(); i++)
        {
            if (childs.item(i) instanceof Element)
            {
                Element childElement = (Element) childs.item(i);
                getCode(childElement, className, sb);

            }
        }
        sb.append("     @Override" + NL);
        sb.append("     public String toString()" + NL);
        sb.append("     {" + NL);
        sb.append("         return \"").append(className).append("\";" + NL);
        sb.append("     }" + NL);

        sb.append("}" + NL);


        return sb.toString();
    }

    public Class getDefinitionClass() throws ClassNotFoundException
    {
        return clazz;
    }

    public Object getInstance()
    {
        try
        {
            return clazz.newInstance();
        }
        catch (Exception e)
        {
            log.error(e);
        }
        return null;
    }

    public ClassInfo getInfo(Class clazz)
    {
        return classes.get(clazz);
    }

    public ClassInfo getInfo(String clazz) throws ServiceException
    {
        try
        {
            Class _clazz = classDictionary.get(clazz);
            return getInfo(_clazz);
        }
        catch (Exception e)
        {
            throw new ServiceException(e);
        }
    }

    public Class loadClass(String clazz) throws ServiceException
    {
        try
        {
            Class _clazz = classDictionary.get(clazz);
            if (_clazz == null)
            {
                return Class.forName(clazz);
            }
            return _clazz;
        }
        catch (Exception e)
        {
            throw new ServiceException(e);
        }
    }

    public Element getElementDefinition()
    {
        return definition;
    }

    public String getName()
    {
        return name;
    }

    public String getType()
    {
        return type;
    }

    public String getNameSpace()
    {
        return namespace;
    }

    public Document toDocument(Parameter parameter)
    {
        Document doc = SWBUtils.XML.getNewDocument();
        Object value = parameter.getValue();
        ClassInfo info = getInfo(value.getClass());
        //Element element=doc.createElementNS(namespace, parameter.getName());
        Element child = info.createElement(value, doc);
        //element.appendChild(child);
        doc.appendChild(child);

        System.out.println("xml: " + SWBUtils.XML.domToXml(doc));
        return doc;
    }

    public Object getValue(Document document) throws ServiceException
    {
        System.out.println(" xml response: " + SWBUtils.XML.domToXml(document));
        //String tagname=tagnames.get(toUpperCase(type));
        NodeList list = document.getElementsByTagNameNS(namespace, type);
        if (list.getLength() > 0)
        {
            for (int i = 0; i < list.getLength(); i++)
            {
                Element element = (Element) list.item(i);
                ClassInfo info = getInfo(clazz);
                PropertyInfo[] properties = info.getProperties();
                Object instance = this.getInstance();
                if (properties.length == 0)
                {
                }
                else
                {
                    for (PropertyInfo prop : properties)
                    {
                        Object objprop = prop.getValue(element, instance);
                        prop.fill(instance, objprop);
                    }
                }
                return instance;
            }
        }
        return null;
    }
}
