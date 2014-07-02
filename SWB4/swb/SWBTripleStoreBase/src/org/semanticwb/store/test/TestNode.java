/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.store.test;

import org.semanticwb.store.Node;
import org.semanticwb.store.Triple;

/**
 *
 * @author javier.solis.g
 */
public class TestNode
{
    public static void main(String args[])
    {
        Triple t=new Triple("<http://www.semanticwb.org/SWBAdmin#swb_ResourceType:Schedule> <http://www.semanticwebbuilder.org/swb4/ontology#resourceMode> \"1\"^^<http://www.w3.org/2001/XMLSchema#long> .");
        System.out.println(t);
        
        Node n=Node.parseNode("<http://www.semanticwb.org/SWBAdmin#WebPage:WBAd_mnui_web_properties>");
        System.out.println(n);
        
        n=Node.parseNode("\"web.properties\"");
        System.out.println(n);
       
        n=Node.parseNode("\"1\"^^<http://www.w3.org/2001/XMLSchema#long>");
        System.out.println(n);        
               
        n=Node.parseNode("\"1\"@en^^<http://www.w3.org/2001/XMLSchema#long>");
        System.out.println(n);
    }
    
}
