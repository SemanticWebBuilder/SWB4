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
package org.semanticwb.webservices.util;

import java.util.HashMap;
import java.util.Map;
import org.jdom.Namespace;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.webservices.ParameterDefinition;

import org.semanticwb.webservices.ServiceException;
import org.semanticwb.webservices.ServiceInfo;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 *
 * @author victor.lorenzana
 */
public class SchemaClass
{

    private static final Logger log = SWBUtils.getLogger(SchemaClass.class);
    private final MemoryClassLoader l = new MemoryClassLoader(getClass().getClassLoader());
    private final Map<String, String> classesToCompile = new HashMap<String, String>();
    private final ServiceInfo service;
    private static final String NL = "\r\n";
    private final Class mainclazz;
    private final HashMap<String,Class> classes=new HashMap<String,Class>();

    public SchemaClass(ParameterDefinition parameter, ServiceInfo service) throws ServiceException
    {
        this(service, parameter.getDefinitionType());
    }

    public SchemaClass(ServiceInfo service, String name) throws ServiceException
    {
        this.service = service;
        Class _mainclazz = null;
        if (XMLDocumentUtil.isBasic(name, service.getJDom()))
        {
            String className = XMLDocumentUtil.basicToObject(name,service);
            try
            {
                _mainclazz = Class.forName(className);
            }
            catch (Exception e)
            {
                throw new ServiceException(e);
            }
        }
        else
        {
            
            Element definition = XMLDocumentUtil.getGlobalElement(name, service);
            int pos = name.indexOf(":");
            if (pos != -1)
            {
                name = name.substring(pos + 1);
            }
            final String className = XMLDocumentUtil.toUpperCase(name);
            
            String tagname = definition.getAttribute("name");
            String code = getCode(definition, className, tagname);
            classesToCompile.put(className, code);
            l.addAll(classesToCompile);

            for (String classNameToAdd : classesToCompile.keySet())
            {
                try
                {
                    Class _clazz = l.loadClass(classNameToAdd);
                    classes.put(classNameToAdd, _clazz);
                    if (classNameToAdd.equals(className))
                    {
                        _mainclazz = _clazz;
                    }
                    
                }
                catch (ClassNotFoundException cnfe)
                {
                    log.error(cnfe);
                    throw new ServiceException(cnfe);
                }
            }
        }

        this.mainclazz = _mainclazz;

    }

    public Class getMainClass()
    {
        return this.mainclazz;
    }

    private String getCode(Element element, String className, String tagname) throws ServiceException
    {
        StringBuilder sb = new StringBuilder();
        sb.append("import org.semanticwb.webservices.util.Required;" + NL);
        sb.append("import org.semanticwb.webservices.util.Tagname;" + NL);
        sb.append("import org.semanticwb.webservices.util.EnnumerationRestriction;" + NL);
        sb.append("" + NL);
        classesToCompile.put(className, "");
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
            if (XMLDocumentUtil.isBasic(_type, service.getJDom()))
            {
                String _tagname = tagname;
                String _varname = "_" + _tagname.toLowerCase();
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
                Element elementToCode = XMLDocumentUtil.getElementByType(_type, service,element);
                if (elementToCode == null)
                {
                    throw new ServiceException("The element with name " + _type + " was not found");
                }
                String _tagname = elementToCode.getAttribute("name");
                String _className = XMLDocumentUtil.toUpperCase(_tagname);

                if (!classesToCompile.containsKey(_className))
                {
                    String code = getCode(elementToCode, _className, _tagname);
                    classesToCompile.put(_className, code);
                }

                String _varname = "_" + _tagname.toLowerCase();
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
                String _varname = "_" + _tagname.toLowerCase();
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
            if (!element.getAttribute("ref").equals(""))
            {
                String ref = element.getAttribute("ref");
                String minOccurs = element.getAttribute("minOccurs");
                String maxOccurs = element.getAttribute("maxOccurs");
                element = XMLDocumentUtil.getGlobalElement(ref, service);
                if (element == null)
                {
                    throw new ServiceException("The element " + ref + " was not found");
                }
                element.setAttribute("minOccurs", minOccurs);
                element.setAttribute("maxOccurs", maxOccurs);

            }
            String _tagname = element.getAttribute("name");
            if (_tagname.equals(""))
            {
                return;
            }
            String _className = XMLDocumentUtil.toUpperCase(_tagname);
            String _varname = "_" + _tagname.toLowerCase();
            String _type = element.getAttribute("type");
            String varType = getType(_type);
            String uri = getNamespaceType(_type);
            boolean isBasic = false;
            if (uri.equals(XMLDocumentUtil.SCHEMA_NAMESPACE))
            {
                isBasic = true;
            }
            if (varType.equals(""))
            {

                if (element.getAttribute("name").equals(""))
                {

                    if (!classesToCompile.containsKey(_className))
                    {
                        String code = getCode(element, _className, _tagname);
                        classesToCompile.put(_className, code);
                    }

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
                else
                {
                    _tagname = element.getAttribute("name");
                    _className = XMLDocumentUtil.toUpperCase(_tagname);
                    if (!classesToCompile.containsKey(_className))
                    {
                        String code = getCode(element, _className, _tagname);
                        classesToCompile.put(_className, code);
                    }
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
            }
            if (!isBasic)
            {

                Element elementToCode = XMLDocumentUtil.getElementByType(_type, service,element);

                if (elementToCode == null)
                {
                    throw new ServiceException("The element " + _type + " was not found");
                }


                _type = getType(elementToCode);
                uri = getNamespaceType(elementToCode);
                _className = XMLDocumentUtil.toUpperCase(elementToCode.getAttribute("name"));
                checkRestrictions(elementToCode, sb);
                if (uri.equals(XMLDocumentUtil.SCHEMA_NAMESPACE))
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



                    if (!classesToCompile.containsKey(_className))
                    {
                        String code = getCode(elementToCode, _className, _tagname);
                        classesToCompile.put(_className, code);
                    }
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
                    if (varType.equals("String") || varType.equals("java.util.Date"))
                    {
                        sb.append(" public final java.util.ArrayList<").append(varType).append("> ").append(_varname).append("=new java.util.ArrayList<").append(varType).append(">();" + NL);
                    }
                    else
                    {
                        varType = XMLDocumentUtil.basicToObject(varType,service);
                        sb.append(" public final java.util.ArrayList<").append(varType).append("> ").append(_varname).append("=new java.util.ArrayList<").append(varType).append(">();" + NL);
                    }
                }
                if (("".equals(minOccurs) || "1".equals(minOccurs) || "0".equals(minOccurs)) && ("1".equals(maxOccurs) || "".equals(maxOccurs)))
                {
                    sb.append("@Tagname(name=\"").append(_tagname).append("\")" + NL);
                    sb.append(" public ").append(varType).append(" ").append(_varname).append(";" + NL);
                }
            }



        }
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
            Namespace ns = service.getJDom().getRootElement().getNamespace(prefix);
            uri = ns == null ? "" : ns.getURI();
        }
        if (uri != null && uri.equals(XMLDocumentUtil.SCHEMA_NAMESPACE))
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

    private String getNamespaceType(String name)
    {
        String _namespace = "";
        int pos = name.indexOf(":");
        if (pos != -1)
        {
            String prefix = name.substring(0, pos);
            Namespace ns = service.getJDom().getRootElement().getNamespace(prefix);
            _namespace = ns == null ? "" : ns.getURI();
        }
        return _namespace;
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
                    NodeList enumerations = childElement.getElementsByTagNameNS(XMLDocumentUtil.SCHEMA_NAMESPACE, "enumeration");
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
    public MemoryClassLoader getMemoryClassLoader()
    {
        return l;
    }
    public Class[] getCreatedClasses()
    {
        return classes.values().toArray(new Class[classes.size()]);
    }
    
    public Class getClassForName(String name) throws ClassNotFoundException
    {
        if(classes.containsKey(name))
        {
            return classes.get(name);
        }
        else
        {
            return l.loadClass(name);
        }
    }
    
    public String getCode()
    {
        return classesToCompile.get(this.mainclazz.getName());
    }
    public String getCode(String name)
    {
        return classesToCompile.get(name);
    }
}
