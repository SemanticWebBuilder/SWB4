package org.semanticwb.repository;

import java.util.HashSet;
import java.util.Iterator;
import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.Namespace;
import org.jdom.output.XMLOutputter;
import org.semanticwb.SWBException;
import org.semanticwb.model.GenericIterator;
import org.semanticwb.platform.SemanticClass;
import org.semanticwb.platform.SemanticLiteral;
import org.semanticwb.repository.base.*;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticProperty;

public class Workspace extends WorkspaceBase
{

    private static final String SYSTEM_VIEW_NAMESPACE = "http://www.jcp.org/jcr/sv/1.0";
    private static final String SYSTEM_VIEW_PREFIX = "sv";
    private static final Namespace NAMESPACE = Namespace.getNamespace(SYSTEM_VIEW_PREFIX, SYSTEM_VIEW_NAMESPACE);
    private static final HashSet<String> excludeInternalView = new HashSet<String>();

    static
    {
        excludeInternalView.add("swbrep");
    //excludeInternalView.add("jcr");
    }

    public Workspace(SemanticObject base)
    {
        super(base);
    }

    public Document getDocumentView()
    {
        Document document = new Document();
        BaseNode root = this.getRoot();
        appendNodeInternalView(root, document, false);
        return document;
    }

    static void appendNode(BaseNode node, Element parent)
    {
        SemanticClass clazz = node.getSemanticObject().getSemanticClass();
        String uri = getUri(clazz.getURI());
        Namespace ns = Namespace.getNamespace(clazz.getPrefix(), uri);
        Element element = new Element(clazz.getName(), ns);        
        element.setAttribute("id",node.getId());
        appendPropertiesToNode(node, element);
        parent.addContent(element);
        GenericIterator<BaseNode> itChilds = node.listNodes();
        while (itChilds.hasNext())
        {
            BaseNode child = itChilds.next();
            appendNode(child, element);
        }
    }

    private static final Iterator<SemanticProperty> listSemanticProperties(BaseNode node)
    {
        HashSet<SemanticProperty> propertiesToReturn = new HashSet<SemanticProperty>();
        Iterator<SemanticClass> classes = node.getSemanticObject().listSemanticClasses();
        while (classes.hasNext())
        {
            SemanticClass clazz = classes.next();
            Iterator<SemanticProperty> properties = clazz.listProperties();
            while (properties.hasNext())
            {
                SemanticProperty property = properties.next();

                propertiesToReturn.add(property);

            }
        }
        return propertiesToReturn.iterator();
    }

    private static void appendPropertiesToNode(BaseNode node, Element nodeElement)
    {          
        Iterator<SemanticProperty> properties = listSemanticProperties(node);
        while (properties.hasNext())
        {
            SemanticProperty property = properties.next();
            if ( property != null )
            {
                boolean exclude = false;
                String name = property.getPrefix() + ":" + property.getName();
                if ( name.equals("swbrep:parentNode") )
                {
                    exclude = true;
                }
                if ( !exclude )
                {
                    if ( property.isObjectProperty() )
                    {
                        SemanticObject object = node.getSemanticObject().getObjectProperty(property);
                        if ( object != null )
                        {
                            BaseNode nodeproperty = new BaseNode(object);
                            appendNode(nodeproperty, nodeElement);
                        }
                    }
                    else
                    {
                        String uri = getUri(property.getURI());
                        Namespace ns = Namespace.getNamespace(property.getPrefix(), uri);
                        try
                        {
                            Iterator<SemanticLiteral> literals = node.getSemanticObject().listLiteralProperties(property);
                            while (literals.hasNext())
                            {
                                SemanticLiteral literal = literals.next();

                                if ( literal != null && literal.getString() != null )
                                {
                                    nodeElement.setAttribute(property.getName(), literal.getString(), ns);
                                }
                            }
                        }
                        catch ( Throwable ex )
                        {
                            ex.printStackTrace(System.out);
                        }
                    }
                }
            }
        }
    }

    private static void appendNode(BaseNode node, Document document)
    {
        Element element = new Element(getName(node.getName()));
        element.setAttribute("id",node.getId());
        appendPropertiesToNode(node, element);
        document.setRootElement(element);
        GenericIterator<BaseNode> itChilds = node.listNodes();
        while (itChilds.hasNext())
        {
            BaseNode child = itChilds.next();
            appendNode(child, element);
        }
    }

    public Document toXML()
    {
        Document document = new Document();        
        BaseNode root = this.getRoot();
        appendNode(root, document);
        try
        {
            XMLOutputter out = new XMLOutputter();
            out.output(document, System.out);
        }
        catch ( Exception e )
        {
            e.printStackTrace(System.out);
        }
        return document;
    }

    public Document getDocumentInternalView()
    {
        Document document = new Document();
        BaseNode root = this.getRoot();
        appendNodeInternalView(root, document, true);
        try
        {
            XMLOutputter out = new XMLOutputter();
            out.output(document, System.out);
        }
        catch ( Exception e )
        {
            e.printStackTrace(System.out);
        }
        return document;
    }

    private static String getUri(String uri)
    {
        int pos = uri.indexOf("#");
        if ( pos != -1 )
        {
            uri = uri.substring(0, pos);
        }
        return uri;
    }

    private static void appendPropertiesToNodeInternalView(BaseNode node, Element nodeElement)
    {
        Iterator<SemanticProperty> properties = listSemanticProperties(node);
        while (properties.hasNext())
        {
            SemanticProperty property = properties.next();
            if ( !property.isObjectProperty() )
            {
                String uri = getUri(property.getURI());
                Namespace ns = Namespace.getNamespace(property.getPrefix(), uri);
                boolean exclude = false;
                for ( String prefix : excludeInternalView )
                {
                    if ( prefix.equals(ns.getPrefix()) )
                    {
                        exclude = true;
                    }
                }

                if ( !exclude )
                {
                    String value = node.getSemanticObject().getProperty(property);
                    if ( value != null )
                    {
                        nodeElement.setAttribute(property.getName(), value, ns);
                    }

                }
            }
        }
    }

    static void appendNodeInternalView(BaseNode node, Element parent, boolean internal)
    {
        Element element = new Element(getName(node.getName()));        
        if(internal)
        {
            element.setAttribute("id", node.getId());
        }
        appendPropertiesToNodeInternalView(node, element);
        parent.addContent(element);
        GenericIterator<BaseNode> itChilds = node.listNodes();
        while (itChilds.hasNext())
        {
            BaseNode child = itChilds.next();
            if(!child.isProtected())
            {
                appendNodeInternalView(child, element, internal);
            }
        }
    }

    private static void appendNodeInternalView(BaseNode node, Document document, boolean internal)
    {
        Element element = new Element(getName(node.getName()));        
        if(internal)
        {
            element.setAttribute("id", node.getId());
        }
        appendPropertiesToNodeInternalView(node, element);
        document.addContent(element);
        GenericIterator<BaseNode> itChilds = node.listNodes();
        while (itChilds.hasNext())
        {
            BaseNode child = itChilds.next();
            if(!child.isProtected())
            {
                appendNodeInternalView(child, element, internal);
            }
        }
    }

    private static String getName(String name)
    {
        int pos = name.indexOf(":");
        if ( pos != -1 )
        {
            name = name.substring(pos + 1);
        }
        return name;
    }

    private static void appendPropertiesToNodeSystemView(BaseNode node, Element nodeElement)
    {
        Iterator<SemanticProperty> properties = node.getSemanticObject().getSemanticClass().listProperties();
        while (properties.hasNext())
        {
            SemanticProperty property = properties.next();
            Element propertyElement = new Element("property", NAMESPACE);
            nodeElement.addContent(propertyElement);
            propertyElement.setAttribute(new Attribute("name", property.getName(), NAMESPACE));
            String value = node.getProperty(property.getName(), "");
            Element eValue = new Element("value", NAMESPACE);
            propertyElement.addContent(eValue);
            eValue.setText(value);
        }
    }

    private static void appendNodeSystemView(BaseNode node, Element parent)
    {
        Element element = new Element("node", NAMESPACE);
        element.setAttribute(new Attribute("name", node.getName(), NAMESPACE));
        appendPropertiesToNodeSystemView(node, element);
        parent.addContent(element);
        GenericIterator<BaseNode> itChilds = node.listNodes();
        while (itChilds.hasNext())
        {
            BaseNode child = itChilds.next();
            appendNodeSystemView(child, element);
        }

    }

    private static void appendNodeSystemView(BaseNode node, Document document)
    {
        Element element = new Element("node", NAMESPACE);
        element.setAttribute(new Attribute("name", node.getName(), NAMESPACE));
        appendPropertiesToNodeSystemView(node, element);
        document.addContent(element);
        GenericIterator<BaseNode> itChilds = node.listNodes();
        while (itChilds.hasNext())
        {
            BaseNode child = itChilds.next();
            appendNodeSystemView(child, element);
        }
    }

    public Document getSystemView()
    {
        Document document = new Document();
        BaseNode root = this.getRoot();
        appendNodeSystemView(root, document);
        return document;
    }

    public BaseNode getBaseNodeFromType(String id, String type) throws SWBException
    {
        SemanticClass clazz = BaseNode.getSemanticClass(type);
        String uri = getSemanticObject().getModel().getObjectUri(id, clazz);
        SemanticObject object = getSemanticObject().getModel().getSemanticObject(uri);
        return new BaseNode(object);
    }
}
