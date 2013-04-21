/*
 *  $Id: VirtuosoSPARQLExample1.java,v 1.4 2008/04/10 07:26:30 source Exp $
 *
 *  This file is part of the OpenLink Software Virtuoso Open-Source (VOS)
 *  project.
 *
 *  Copyright (C) 1998-2008 OpenLink Software
 *
 *  This project is free software; you can redistribute it and/or modify it
 *  under the terms of the GNU General Public License as published by the
 *  Free Software Foundation; only version 2 of the License, dated June 1991.
 *
 *  This program is distributed in the hope that it will be useful, but
 *  WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 *  General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License along
 *  with this program; if not, write to the Free Software Foundation, Inc.,
 *  51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA
 *
 */

package org.semanticwb.triplestore.virtuoso.test;

import com.hp.hpl.jena.graph.Node;
import com.hp.hpl.jena.graph.Triple;
import com.hp.hpl.jena.graph.impl.LiteralLabelFactory;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;
import java.util.ArrayList;

import virtuoso.jena.driver.*;

public class Test1 {

	/**
	 * Executes a SPARQL query against a virtuoso url and prints results.
	 */
	public static void main(String[] args) 
        {
		VirtGraph set = new VirtGraph ("jdbc:virtuoso://swb4d.semanticbuilder.com:1111", "dba", "dba");
                
                /*
		Query sparql = QueryFactory.create("SELECT DISTINCT ?g\n" +
                    "WHERE {\n" +
                    "  GRAPH ?g {\n" +
                    "    ?s ?p ?o\n" +
                    "  }\n" +
                    "}");

		VirtuosoQueryExecution vqe = VirtuosoQueryExecutionFactory.create (sparql, set);

		ResultSet results = vqe.execSelect();
		while (results.hasNext()) {
                    QuerySolution result = results.nextSolution();
		    RDFNode graph = result.get("g");
		    System.out.println(graph);
		}
                */
                
                
            VirtGraph graph = new VirtGraph ("Example3", "jdbc:virtuoso://swb4d.semanticbuilder.com:1111", "dba", "dba");
            
            VirtModel model=new VirtModel(graph);
            
            graph.clear ();

            System.out.println("graph.isEmpty() = " + graph.isEmpty());

            ArrayList<Triple> list = new ArrayList();
            //graph.getTransactionHandler().begin();
            for(int x=0;x<10000;x++)
            {
                list.add(new Triple(Node.createURI("http://example3.org/#res"+x), Node.createURI("http://example3.org/#prop1"), Node.createLiteral("Hola "+x)));
                list.add(new Triple(Node.createURI("http://example3.org/#res"+x), Node.createURI("http://example3.org/#prop2"), Node.createLiteral(LiteralLabelFactory.create(Boolean.valueOf(false)))));
                list.add(new Triple(Node.createURI("http://example3.org/#res"+x), Node.createURI("http://example3.org/#prop3"), Node.createURI("http://example3.org/#obj3")));
                list.add(new Triple(Node.createURI("http://example3.org/#res"+x), Node.createURI("http://example3.org/#prop4"), Node.createURI("http://example3.org/#obj4")));
                if(x%100==0)System.out.println("X:"+x);
            }
            graph.getBulkUpdateHandler().add(list);
            //graph.getTransactionHandler().commit();
            System.out.println("End load.");
                        
            ExtendedIterator<Triple> it=graph.find(Node.createURI("http://example3.org/#res1"), Node.createURI("http://example3.org/#prop2"), Node.ANY);
            while (it.hasNext())
            {
                Triple triple = it.next();
                System.out.println(triple);
            }
            
            
            //model.setNsPrefix("Example3", "http://example3.org/#");
            
            System.out.println("ns:"+model.getNsPrefixURI("Example3"));
            
            /*
            String str1 = "DB.DBA.XML_SELECT_ALL_NS_DECLS (3)";
            try
            {
                Statement localStatement = graph.getConnection().createStatement();
                java.sql.ResultSet localResultSet = localStatement.executeQuery(str1);
                while (localResultSet.next())
                {
                  String str2 = localResultSet.getString(1);
                  String str3 = localResultSet.getString(2);
                  if ((str3 != null) && (str3 != null))
                        System.out.println(str2 +" "+ str3);
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            } 
            * */
            

	}
}
