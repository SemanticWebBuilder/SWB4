/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.wsdl.util;

import java.util.ArrayList;
import java.util.Arrays;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 *
 * @author victor.lorenzana
 */
public class XMLDocumentUtil
{

    public static Element[] getElement(String prefix, String name, Document doc, Element element,String type)
    {
        ArrayList<Element> getElement = new ArrayList<Element>();
        String nameElement = element.getAttribute("name");
        String prefixElement = element.getPrefix();

        String oldprefix=prefix;
        if (prefixElement == null)
        {
            prefixElement = "";
        }
        if (prefix == null)
        {
            prefix = prefixElement;
        }
        if (nameElement.equals(name) && prefix.equals(prefixElement) && type.equals(element.getLocalName()))
        {
            getElement.add(element);
        }
        prefix=oldprefix;
        NodeList list = element.getChildNodes();
        for (int i = 0; i < list.getLength(); i++)
        {
            if (list.item(i) instanceof Element)
            {
                Element child = (Element) list.item(i);
                getElement.addAll(Arrays.asList(getElement(prefix, name, doc, child,type)));
            }
        }

        return getElement.toArray(new Element[getElement.size()]);
    }

    public static Element[] getElement(String name, Document doc,String type)
    {
        ArrayList<Element> getElement = new ArrayList<Element>();
        String prefix = null;
        int pos = name.indexOf(":");
        String localname = name;
        if (pos != -1)
        {
            prefix = name.substring(0, pos);
            localname = name.substring(pos + 1);
            if ("tns".equals(prefix))
            {
                prefix = null;
            }
        }
        else
        {
            prefix = null;
        }
        getElement.addAll(Arrays.asList(getElement(prefix, localname, doc, doc.getDocumentElement(),type)));
        return getElement.toArray(new Element[getElement.size()]);
    }
}
