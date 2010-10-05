/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.rest;

import java.util.ArrayList;
import java.util.List;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author victor.lorenzana
 */
public class Parts {
    Element element;
    public Parts(Element element)
    {
        this.element=element;
    }
    public String getp()
    {
        return element.getAttribute("p");
    }
    public List<Part> listPart()
    {
        ArrayList<Part> elements=new ArrayList<Part>();
        NodeList nodes=element.getChildNodes();
        for(int i=0;i<nodes.getLength();i++)
        {
            Node node=nodes.item(i);
            if(node instanceof Element)
            {
                Element e=(Element)node;
                elements.add(new Part(e));
            }
        }
        return elements;
    }
    @Override
    public String toString()
    {
        return element.getTextContent();
    }
}
