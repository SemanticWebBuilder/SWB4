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

import com.hp.hpl.jena.query.*;
import com.hp.hpl.jena.rdf.model.RDFNode;

import virtuoso.jena.driver.*;

public class VirtuosoSPARQLExample1 {

	/**
	 * Executes a SPARQL query against a virtuoso url and prints results.
	 */
	public static void main(String[] args) {

/*			STEP 1			*/
		VirtGraph set = new VirtGraph ("jdbc:virtuoso://swb4d.semanticbuilder.com:1111", "dba", "dba");
                VirtModel model=new VirtModel(set);
/*			STEP 2			*/


/*			STEP 3			*/
/*		Select all data in virtuoso	*/
                String query="SELECT * WHERE { GRAPH ?graph { ?s ?p ?o } } limit 100";
		Query sparql = QueryFactory.create(query);

/*			STEP 4			*/
		//VirtuosoQueryExecution vqe = VirtuosoQueryExecutionFactory.create (query, set);
		QueryExecution vqe = QueryExecutionFactory.create(sparql, model);

		ResultSet results = vqe.execSelect();
		while (results.hasNext()) {
			QuerySolution result = results.nextSolution();
		    RDFNode graph = result.get("graph");
		    RDFNode s = result.get("s");
		    RDFNode p = result.get("p");
		    RDFNode o = result.get("o");
		    System.out.println(graph + " { " + s + " " + p + " " + o + " . }");
		}
	}
}
