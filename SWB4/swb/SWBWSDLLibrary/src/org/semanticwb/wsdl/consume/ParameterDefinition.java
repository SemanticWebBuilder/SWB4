/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.wsdl.consume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.jdom.Namespace;
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
    private final HashMap<Class, ClassInfo> classInfoDictionary = new HashMap<Class, ClassInfo>();
    private final Map<String, String> classesToCompile = new HashMap<String, String>();
    private Class clazz;
    private final MemoryClassLoader l = new MemoryClassLoader(getClass().getClassLoader());
    private final org.jdom.Document jdom;
    private final ArrayList<Element> schemas = new ArrayList<Element>();

    public ParameterDefinition(Element part, org.jdom.Document _jdom) throws ServiceException
    {
        Document doc = part.getOwnerDocument();
        getSchemas(doc);
        this.jdom = _jdom;
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
            String _tagname=name;
            _type = part.getAttribute("type");
            String varType = getType(_type);
            String _varname="_"+_name.toLowerCase();
            String _namespace = getNamespaceType(_type);
            if (SCHEMA_NAMESPACE.equals(_namespace))
            {
//                StringBuilder sb=new StringBuilder();
//                
//                String minOccurs = part.getAttribute("minOccurs");
//                String maxOccurs = part.getAttribute("maxOccurs");
//                if ("0".equals(minOccurs) && "unbounded".equals(maxOccurs))
//                {
//                    sb.append("@Tagname(name=\"").append(_tagname).append("\")" + NL);
//                    sb.append(" public final java.util.ArrayList<").append(varType).append("> ").append(_varname).append("=new java.util.ArrayList<").append(varType).append(">();" + NL);
//                }
//                if (("".equals(minOccurs) || "1".equals(minOccurs) || "0".equals(minOccurs)) && ("1".equals(maxOccurs) || "".equals(maxOccurs)))
//                {
//                    sb.append("@Tagname(name=\"").append(_tagname).append("\")" + NL);
//                    if ("1".equals(minOccurs) && "1".equals(maxOccurs))
//                    {
//                        sb.append("@Required" + NL);
//                    }
//                    sb.append(" public ").append(varType).append(" ").append(_varname).append(";" + NL);
//                }
            }
            else
            {
                definition = XMLDocumentUtil.getElementByType(schemas, _type, _jdom);
                if (definition == null)
                {
                    throw new ServiceException("The element definition " + _type + " was not found");
                }
                Element schema = getSchema(definition);
                if (schema == null)
                {
                    schema = doc.getDocumentElement();
                }
                String elementName = definition.getAttribute("name");
                this.namespace = schema.getAttribute("targetNamespace");
                final String className = toUpperCase(elementName);
                final String code = getCode(definition, className, elementName);
                classesToCompile.put(className, code);
                l.addAll(classesToCompile);

                for (String classNameToAdd : classesToCompile.keySet())
                {
                    try
                    {
                        Class _clazz = l.loadClass(classNameToAdd);
                        if (classNameToAdd.equals(className))
                        {
                            this.clazz = _clazz;
                        }

                        //ClassInfo info = new ClassInfo(_clazz, code,elementName,namespace,tagname);
                        ClassInfo info = new ClassInfo(_clazz, code, elementName, namespace, this);
                        this.classInfoDictionary.put(_clazz, info);
                    }
                    catch (ClassNotFoundException cnfe)
                    {
                        log.error(cnfe);
                        throw new ServiceException(cnfe);
                    }
                }
            }
        }
        if (!part.getAttribute("element").equals(""))
        {
            _type = part.getAttribute("element");
            //Element[] definitions = XMLDocumentUtil.getElement(_type, part.getOwnerDocument(), "element");
            definition = XMLDocumentUtil.getGlobalElement(schemas, _type, _jdom);
            if (definition == null)
            {
                throw new ServiceException("The element definition " + _type + " was not found");
            }
            Element schema = getSchema(definition);
            if (schema == null)
            {
                schema = doc.getDocumentElement();
            }
            String elementName = definition.getAttribute("name");
            this.namespace = schema.getAttribute("targetNamespace");
            final String className = toUpperCase(elementName);
            final String code = getCode(definition, className, elementName);
            classesToCompile.put(className, code);
            l.addAll(classesToCompile);

            for (String classNameToAdd : classesToCompile.keySet())
            {
                try
                {
                    Class _clazz = l.loadClass(classNameToAdd);
                    if (classNameToAdd.equals(className))
                    {
                        this.clazz = _clazz;
                    }

                    //ClassInfo info = new ClassInfo(_clazz, code,elementName,namespace,tagname);
                    ClassInfo info = new ClassInfo(_clazz, code, elementName, namespace, this);
                    this.classInfoDictionary.put(_clazz, info);
                }
                catch (ClassNotFoundException cnfe)
                {
                    log.error(cnfe);
                    throw new ServiceException(cnfe);
                }
            }





            if (_type.startsWith("tns:"))
            {
                _type = _type.substring(4);
            }

        }
        this.type = _type;

    }

    private void getSchemas(Document doc)
    {
        NodeList _schemas = doc.getElementsByTagNameNS(SCHEMA_NAMESPACE, "schema");
        for (int i = 0; i < _schemas.getLength(); i++)
        {
            schemas.add((Element) _schemas.item(i));
        }
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

    private String getName(String name)
    {
        String _name = name;
        int pos = name.indexOf(":");
        if (pos != -1)
        {
            String prefix = name.substring(0, pos);
            _name = name.substring(pos + 1);
        }
        return _name;
    }

    private String getNamespaceType(String name)
    {
        String _namespace = "";
        int pos = name.indexOf(":");
        if (pos != -1)
        {
            String prefix = name.substring(0, pos);
            Namespace ns = jdom.getRootElement().getNamespace(prefix);
            _namespace = ns == null ? "" : ns.getURI();
        }
        return _namespace;
    }

    private String getType(Element element)
    {
        String _type = element.getAttribute("type");
        if (_type.equals(""))
        {
            NodeList childs = element.getChildNodes();
            for (int i = 0; i < childs.getLength(); i++)
            {
                if (childs.item(i) instanceof Element)
                {
                    Element childElement = (Element) childs.item(i);
                    if (childElement.getLocalName().equals("restriction"))
                    {
                        String base = childElement.getAttribute("base");
                        return getType(base);
                    }
                }
            }
        }
        return _type;
    }

    private String getNamespaceType(Element element)
    {
        String _type = element.getAttribute("type");
        if (_type.equals(""))
        {
            NodeList childs = element.getChildNodes();
            for (int i = 0; i < childs.getLength(); i++)
            {
                if (childs.item(i) instanceof Element)
                {
                    Element childElement = (Element) childs.item(i);
                    if (childElement.getLocalName().equals("restriction"))
                    {
                        String base = childElement.getAttribute("base");
                        return getNamespaceType(base);
                    }
                }
            }
        }
        _type = getNamespaceType(_type);
        return _type;
    }

    private String getType(String name)
    {
        String varType = name;

        String uri = "";
        int pos = name.indexOf(":");
        if (pos != -1)
        {
            String prefix = name.substring(0, pos);
            varType = name.substring(pos + 1);
            Namespace ns = jdom.getRootElement().getNamespace(prefix);
            uri = ns == null ? "" : ns.getURI();
        }
        if (uri != null && uri.equals(SCHEMA_NAMESPACE))
        {

            if ("string".equals(varType))
            {
                varType = "String";
            }
            else if ("int".equals(varType))
            {
                varType = "int";
            }
            else if ("long".equals(varType))
            {
                varType = "long";
            }
            else if ("boolean".equals(varType))
            {
                varType = "boolean";
            }
            else if ("float".equals(varType))
            {
                varType = "float";
            }
            else if ("short".equals(varType))
            {
                varType = "short";
            }
            else if ("byte".equals(varType))
            {
                varType = "byte";
            }
            else if ("double".equals(varType))
            {
                varType = "double";
            }
            else if ("datetime".equals(varType))
            {
                varType = "java.util.Date";
            }
            else
            {
                varType = "String";
            }
        }
        return varType;

    }

    public Boolean isRef(Element element)
    {
        String ref = element.getAttribute("ref");
        return !"".equals(ref);
    }

    private void checkRestrictions(Element element, StringBuilder sb)
    {
        NodeList childs = element.getChildNodes();
        for (int i = 0; i < childs.getLength(); i++)
        {
            if (childs.item(i) instanceof Element)
            {
                Element childElement = (Element) childs.item(i);
                if (childElement.getLocalName().equals("restriction"))
                {
                    NodeList enumerations = childElement.getElementsByTagNameNS(SCHEMA_NAMESPACE, "enumeration");
                    if (enumerations.getLength() > 0)
                    {
                        StringBuilder values = new StringBuilder();
                        for (int j = 0; j < enumerations.getLength(); j++)
                        {
                            Element enumeration = (Element) enumerations.item(j);
                            String value = enumeration.getAttribute("value");
                            values.append("\"");
                            values.append(value);
                            values.append("\"");
                            values.append(",");
                        }
                        if (values.length() > 0)
                        {
                            values.deleteCharAt(values.length() - 1);
                        }
                        sb.append("@EnnumerationRestriction(values={").append(values.toString()).append("})");
                    }
                }
            }
        }
    }

    private void getCode(Element element, String className, StringBuilder sb) throws ServiceException
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
            boolean isEnumeration = false;
            NodeList childs = element.getChildNodes();
            for (int i = 0; i < childs.getLength(); i++)
            {
                if (childs.item(i) instanceof Element)
                {
                    Element childElement = (Element) childs.item(i);
                    if (childElement.getLocalName().equals("enumeration"))
                    {
                        isEnumeration = true;
                    }
                }
            }


            if (isEnumeration)
            {
                String _tagname = ((Element) element.getParentNode()).getAttribute("name");
                String _varname = "_"+_tagname.toLowerCase();
                String base = element.getAttribute("base");
                String varType = getType(base);
                String minOccurs = element.getAttribute("minOccurs");
                String maxOccurs = element.getAttribute("maxOccurs");
                if ("0".equals(minOccurs) && "unbounded".equals(maxOccurs))
                {
                    sb.append("@Tagname(name=\"").append(_tagname).append("\")" + NL);
                    sb.append(" public final java.util.ArrayList<").append(varType).append("> ").append(_varname).append("=new java.util.ArrayList<").append(varType).append(">();" + NL);
                }
                if (("".equals(minOccurs) || "1".equals(minOccurs) || "0".equals(minOccurs)) && ("1".equals(maxOccurs) || "".equals(maxOccurs)))
                {
                    sb.append("@Tagname(name=\"").append(_tagname).append("\")" + NL);
                    if ("1".equals(minOccurs) && "1".equals(maxOccurs))
                    {
                        sb.append("@Required" + NL);
                    }
                    sb.append(" public ").append(varType).append(" ").append(_varname).append(";" + NL);
                }
            }

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
            if (isRef(element))
            {
                return;
            }
            String _tagname = element.getAttribute("name");
            if (_tagname.equals(""))
            {
                return;
            }
            String _className = toUpperCase(_tagname);
            String _varname = "_"+_tagname.toLowerCase();
            String _type = element.getAttribute("type");
            String varType = getType(_type);
            String uri = getNamespaceType(_type);
            boolean isBasic = false;
            if (uri.equals(SCHEMA_NAMESPACE))
            {
                isBasic = true;
            }
            if (varType.equals(""))
            {
                String code = getCode(element, className, _tagname);
                classesToCompile.put(_className, code);
                String minOccurs = element.getAttribute("minOccurs");
                String maxOccurs = element.getAttribute("maxOccurs");

                checkRestrictions(element, sb);

                if ("0".equals(minOccurs) && "unbounded".equals(maxOccurs))
                {
                    sb.append("@Tagname(name=\"").append(_tagname).append("\")" + NL);
                    sb.append(" public final java.util.ArrayList<").append(_className).append("> ").append(_varname).append("=new java.util.ArrayList<").append(_className).append(">();" + NL);
                }
                if (("".equals(minOccurs) || "1".equals(minOccurs) || "0".equals(minOccurs)) && ("1".equals(maxOccurs) || "".equals(maxOccurs)))
                {
                    sb.append("@Tagname(name=\"").append(_tagname).append("\")" + NL);
                    if ("1".equals(minOccurs) && "1".equals(maxOccurs))
                    {
                        sb.append("@Required" + NL);
                    }
                    sb.append(" public ").append(_className).append(" ").append(_varname).append(";" + NL);
                }
                return;
            }
            if (!isBasic)
            {

                //XMLDocumentUtil.getElement(
                //                Element[] elements = XMLDocumentUtil.getElement(varType, element.getOwnerDocument(), "element");
                //                if (elements.length == 0)
                //                {
                //                    elements = XMLDocumentUtil.getElement(varType, element.getOwnerDocument(), "complexType");
                //                }
                //                if (elements.length == 0)
                //                {
                //                    elements = XMLDocumentUtil.getElement(varType, element.getOwnerDocument(), "simpleType");
                //                }
                Element elementToCode = XMLDocumentUtil.getElementByType(schemas, _type, jdom);

                if (elementToCode == null)
                {
                    throw new ServiceException("The element " + _type + " was not found");
                }


                _type = getType(elementToCode);
                uri = getNamespaceType(elementToCode);
                _className = toUpperCase(elementToCode.getAttribute("name"));
                checkRestrictions(elementToCode, sb);
                if (uri.equals(SCHEMA_NAMESPACE))
                {
                    _className = _type;
                    _varname = _tagname.toLowerCase();
                    String minOccurs = element.getAttribute("minOccurs");
                    String maxOccurs = element.getAttribute("maxOccurs");
                    if ("0".equals(minOccurs) && "unbounded".equals(maxOccurs))
                    {
                        sb.append("@Tagname(name=\"").append(_tagname).append("\")" + NL);
                        sb.append(" public final java.util.ArrayList<").append(_className).append("> ").append(_varname).append("=new java.util.ArrayList<").append(_className).append(">();" + NL);
                    }
                    if (("".equals(minOccurs) || "1".equals(minOccurs) || "0".equals(minOccurs)) && ("1".equals(maxOccurs) || "".equals(maxOccurs)))
                    {
                        sb.append("@Tagname(name=\"").append(_tagname).append("\")" + NL);
                        if ("1".equals(minOccurs) && "1".equals(maxOccurs))
                        {
                            sb.append("@Required" + NL);
                        }
                        sb.append(" public ").append(_className).append(" ").append(_varname).append(";" + NL);
                    }

                }
                else
                {

                    String code = getCode(elementToCode, _className, _tagname);
                    classesToCompile.put(_className, code);
                    String minOccurs = element.getAttribute("minOccurs");
                    String maxOccurs = element.getAttribute("maxOccurs");
                    if ("0".equals(minOccurs) && "unbounded".equals(maxOccurs))
                    {
                        sb.append("@Tagname(name=\"").append(_tagname).append("\")" + NL);
                        sb.append(" public final java.util.ArrayList<").append(_className).append("> ").append(_varname).append("=new java.util.ArrayList<").append(_className).append(">();" + NL);
                    }
                    if (("".equals(minOccurs) || "1".equals(minOccurs) || "0".equals(minOccurs)) && ("1".equals(maxOccurs) || "".equals(maxOccurs)))
                    {
                        sb.append("@Tagname(name=\"").append(_tagname).append("\")" + NL);
                        if ("1".equals(minOccurs) && "1".equals(maxOccurs))
                        {
                            sb.append("@Required" + NL);
                        }
                        sb.append(" public ").append(_className).append(" ").append(_varname).append(";" + NL);
                    }
                }



            }
            else
            {
                String minOccurs = element.getAttribute("minOccurs");
                String maxOccurs = element.getAttribute("maxOccurs");
                if ("0".equals(minOccurs) && "unbounded".equals(maxOccurs))
                {
                    sb.append("@Tagname(name=\"").append(_tagname).append("\")" + NL);
                    sb.append(" public final java.util.ArrayList<").append(varType).append("> ").append(_varname).append("=new java.util.ArrayList<").append(varType).append(">();" + NL);
                }
                if (("".equals(minOccurs) || "1".equals(minOccurs) || "0".equals(minOccurs)) && ("1".equals(maxOccurs) || "".equals(maxOccurs)))
                {
                    sb.append("@Tagname(name=\"").append(_tagname).append("\")" + NL);
                    sb.append(" public ").append(varType).append(" ").append(_varname).append(";" + NL);
                }
            }



        }
    }

    private String getCode(Element element, String className, String tagname) throws ServiceException
    {
        StringBuilder sb = new StringBuilder();
        sb.append("import org.semanticwb.wsdl.consume.Required;" + NL);
        sb.append("import org.semanticwb.wsdl.consume.Tagname;" + NL);
        sb.append("import org.semanticwb.wsdl.consume.EnnumerationRestriction;" + NL);
        sb.append("" + NL);
        sb.append("@Tagname(name=\"").append(tagname).append("\")" + NL);
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
        String _type = element.getAttribute("type");
        if (!_type.equals(""))
        {
            if (XMLDocumentUtil.isBasic(_type, jdom))
            {
                String _tagname = tagname;
                String _varname = "_"+_tagname.toLowerCase();
                String _varType = getType(_type);
                String minOccurs = element.getAttribute("minOccurs");
                String maxOccurs = element.getAttribute("maxOccurs");
                if ("0".equals(minOccurs) && "unbounded".equals(maxOccurs))
                {
                    sb.append("@Tagname(name=\"").append(_tagname).append("\")" + NL);
                    sb.append(" public final java.util.ArrayList<").append(_varType).append("> ").append(_varname).append("=new java.util.ArrayList<").append(_varType).append(">();" + NL);
                }
                if (("".equals(minOccurs) || "1".equals(minOccurs) || "0".equals(minOccurs)) && ("1".equals(maxOccurs) || "".equals(maxOccurs)))
                {
                    sb.append("@Tagname(name=\"").append(_varType).append("\")" + NL);
                    if ("1".equals(minOccurs) && "1".equals(maxOccurs))
                    {
                        sb.append("@Required" + NL);
                    }
                    sb.append(" public ").append(_varType).append(" ").append(_varname).append(";" + NL);
                }
            }
            else
            {
                Element elementToCode = XMLDocumentUtil.getElementByType(schemas, _type, jdom);
                if (elementToCode == null)
                {
                    throw new ServiceException("The element with name " + _type + " was not found");
                }
                String _tagname = elementToCode.getAttribute("name");
                String _className = toUpperCase(_tagname);
                String code = getCode(elementToCode, _className, _tagname);
                this.classesToCompile.put(_className, code);
                String _varname = "_"+_tagname.toLowerCase();
                String minOccurs = element.getAttribute("minOccurs");
                String maxOccurs = element.getAttribute("maxOccurs");
                if ("0".equals(minOccurs) && "unbounded".equals(maxOccurs))
                {
                    sb.append("@Tagname(name=\"").append(_tagname).append("\")" + NL);
                    sb.append(" public final java.util.ArrayList<").append(_className).append("> ").append(_varname).append("=new java.util.ArrayList<").append(_className).append(">();" + NL);
                }
                if (("".equals(minOccurs) || "1".equals(minOccurs) || "0".equals(minOccurs)) && ("1".equals(maxOccurs) || "".equals(maxOccurs)))
                {
                    sb.append("@Tagname(name=\"").append(_className).append("\")" + NL);
                    if ("1".equals(minOccurs) && "1".equals(maxOccurs))
                    {
                        sb.append("@Required" + NL);
                    }
                    sb.append(" public ").append(_className).append(" ").append(_varname).append(";" + NL);
                }
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

    public Object newInstance()
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

        return classInfoDictionary.get(clazz);
    }

    public ClassInfo getInfo(String clazz) throws ServiceException
    {
        try
        {
            Class _clazz = l.loadClass(clazz);
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
            Class _clazz = l.loadClass(clazz);
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

    public Document toDocument(Parameter parameter) throws ServiceException
    {
        Document doc = SWBUtils.XML.getNewDocument();
        Object value = parameter.getValue();
        ClassInfo info = getInfo(value.getClass());
        Element child = info.createElement(value, doc);
        doc.appendChild(child);
        return doc;
    }

    public Object getValue(Document document) throws ServiceException
    {
        System.out.println(" xml response: " + SWBUtils.XML.domToXml(document));
        NodeList list = document.getElementsByTagNameNS(namespace, type);
        if (list.getLength() > 0)
        {
            for (int i = 0; i < list.getLength(); i++)
            {
                Element element = (Element) list.item(i);
                ClassInfo info = getInfo(clazz);
                PropertyInfo[] properties = info.getProperties();
                Object instance = this.newInstance();
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

    @Override
    public String toString()
    {
        return "ParameterDefinition{" + "name=" + name + "clazz=" + clazz + "l=" + l + '}';
    }

    public PropertyInfo[] getProperties()
    {
        ClassInfo info = getInfo(this.clazz);
        return info.getProperties();
    }

    public PropertyInfo getPropertyInfoByName(String name)
    {
        if (name == null)
        {
            throw new NullPointerException("The name can not be null");
        }
        ClassInfo info = getInfo(this.clazz);
        for (PropertyInfo prop : info.getProperties())
        {
            if (name.equalsIgnoreCase(prop.getName()))
            {
                return prop;
            }
        }
        return null;
    }
}
