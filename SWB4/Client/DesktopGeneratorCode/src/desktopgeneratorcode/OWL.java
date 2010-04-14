/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package desktopgeneratorcode;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import java.util.HashSet;


import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.Namespace;
import org.jdom.input.DOMBuilder;
import org.jdom.xpath.XPath;
import org.semanticwb.SWBUtils;

/**
 *
 * @author victor.lorenzana
 */
public class OWL
{

    private static final String XSD_NAMESPACE = "http://www.w3.org/2001/XMLSchema#";
    private static final String NAMESPACE_URL = "http://www.w3.org/XML/1998/namespace";
    private static final String OWL_NAMESPACE = "http://www.w3.org/2002/07/owl#";
    private static final String RFDS_NAMESPACE = "http://www.w3.org/2000/01/rdf-schema#";
    private static final String RFD_NAMESPACE = "http://www.w3.org/1999/02/22-rdf-syntax-ns#";
    private File location;
    String[] prefixes;
    private String namespace;
    private String[] namespacesrequired;
    private String[] requiredPrefixes;
    private Document doc;
    public OWL(File location) throws GenerationException
    {
        this.location = location;
        if (!location.exists())
        {
            throw new IllegalArgumentException("The file " + location.getAbsolutePath() + " does not exist");
        }
        if (location.isDirectory())
        {
            throw new IllegalArgumentException("The location " + location.getAbsolutePath() + " is a directory");
        }
        if (!location.getName().endsWith(".owl"))
        {
            throw new IllegalArgumentException("The file " + location.getAbsolutePath() + " is not a owl file extension");
        }
        validateOWL();
        this.location = location;
    }

    public String[] getRequiredPrefixes()
    {
        return requiredPrefixes;
    }

    public String getNamespace()
    {
        return namespace;
    }

    public String[] getRequiredNamespaces()
    {
        return this.namespacesrequired;
    }

    public String[] getPrefixes()
    {
        return prefixes;
    }
    public String getPrefix(String namespaceUri)
    {
        List namespaces=doc.getRootElement().getAdditionalNamespaces();
        for(int i=0;i<namespaces.size();i++)
        {
            Namespace ns=(Namespace)namespaces.get(i);
            if(ns.getURI().equals(namespaceUri))
            {
                return ns.getPrefix();
            }
        }
        return null;
    }
    private void validateOWL() throws GenerationException
    {
        HashSet<String> pxs = new HashSet<String>();
        HashSet<String> requiredpxs = new HashSet<String>();
        try
        {
            FileInputStream in = new FileInputStream(location);
            DOMBuilder builder = new DOMBuilder();
            doc = builder.build(SWBUtils.XML.xmlToDom(in));
            if (!doc.hasRootElement())
            {
                throw new GenerationException("The file " + location.getAbsolutePath() + " has not a root element");
            }
            String base = doc.getRootElement().getAttributeValue("base", Namespace.getNamespace("xml", NAMESPACE_URL));
            if (base == null)
            {
                namespace = doc.getBaseURI();
            }
            else
            {
                namespace = base;
            }
            if (namespace != null && !namespace.endsWith("#"))
            {
                namespace += "#";
            }
            Element root = doc.getRootElement();
            if (root == null || !root.getName().equals("RDF") || root.getNamespaceURI() == null || !root.getNamespaceURI().equals(RFD_NAMESPACE))
            {
                throw new GenerationException("The file " + location.getAbsolutePath() + " is not a RDF file");
            }
            List namespaces = root.getAdditionalNamespaces();
            for (int i = 0; i < namespaces.size(); i++)
            {
                Namespace ns = (Namespace) namespaces.get(i);
                if (ns != null && !ns.getURI().equals(XSD_NAMESPACE) && !ns.getURI().equals(OWL_NAMESPACE) && !ns.getURI().equals(RFD_NAMESPACE) && !ns.getURI().equals(RFDS_NAMESPACE))
                {
                    if (ns.getPrefix() != null && !ns.getPrefix().equals(""))
                    {
                        pxs.add(ns.getPrefix());
                    }
                }
            }
            HashSet<String> required = new HashSet<String>();
            try
            {
                XPath xpath = XPath.newInstance("//owl:imports");
                xpath.addNamespace("owl", OWL_NAMESPACE);
                xpath.addNamespace("rdf", RFD_NAMESPACE);
                List imports = xpath.selectNodes(doc);
                for (int i = 0; i < imports.size(); i++)
                {
                    Object obj = imports.get(i);
                    if (obj instanceof Element)
                    {
                        Element element = (Element) obj;
                        String namespaceRequired = element.getAttributeValue("resource", Namespace.getNamespace("rdf", RFD_NAMESPACE));
                        if (!namespaceRequired.endsWith("#"))
                        {
                            namespaceRequired += "#";
                        }
                        if (namespaceRequired != null)
                        {
                            required.add(namespaceRequired);
                            for (int j = 0; j < namespaces.size(); j++)
                            {
                                Namespace ns = (Namespace) namespaces.get(j);
                                if(ns.getURI().equals(namespaceRequired) && ns.getPrefix()!=null)
                                {
                                    requiredpxs.add(ns.getPrefix());
                                }
                            }

                        }
                    }
                    else
                    {
                        throw new GenerationException("The file " + location.getAbsolutePath() + " is not an ontology");
                    }
                }
            }
            catch (Exception e)
            {
                throw new GenerationException("The file " + location.getAbsolutePath() + " is not an ontology", e);
            }
            requiredPrefixes = requiredpxs.toArray(new String[requiredpxs.size()]);
            namespacesrequired = required.toArray(new String[required.size()]);
        }
        catch (FileNotFoundException e)
        {
            throw new GenerationException("The file " + location.getAbsolutePath() + " has an error", e);
        }
        prefixes = pxs.toArray(new String[pxs.size()]);
    }

    public String getLocation()
    {
        try
        {
            return location.getCanonicalPath();
        }
        catch(Exception e)
        {
            return location.getAbsolutePath();
        }
    }

    public String getName()
    {
        return location.getName();
    }

    @Override
    public String toString()
    {
        return this.getName();
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj == null)
        {
            return false;
        }
        if (getClass() != obj.getClass())
        {
            return false;
        }
        final OWL other = (OWL) obj;
        if (this.getName() != other.getName() && (this.getName() == null || !this.getName().equals(other.getName())))
        {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode()
    {
        int hash = 3;
        hash = 41 * hash + (this.getName() != null ? this.getName().hashCode() : 0);
        return hash;
    }
}
