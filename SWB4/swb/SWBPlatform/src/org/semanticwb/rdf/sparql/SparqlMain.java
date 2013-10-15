/*
 *    Copyright 2007-2012 The sparkle-g Team
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.semanticwb.rdf.sparql;
/**
 * @author Simone Tripodi (simone.tripodi)
 * @author Michele Mostarda (michele.mostarda)
 * @author Juergen Pfundt (Juergen.Pfundt)
 * @version $Id: Sparql.g 523 2012-02-17 23:10:57Z Juergen.Pfundt@gmail.com $
 */
import org.antlr.v4.runtime.*;

import org.stringtemplate.v4.*;

import java.util.List;
import java.util.*;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.RuleNode;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.antlr.v4.runtime.tree.Tree;

public class SparqlMain {

    
    
  public static String getNodeText(Tree t, String ruleNames[]) {
    if (ruleNames != null) {
      if ((t instanceof RuleNode)) {
        int ruleIndex = ((RuleNode)t).getRuleContext().getRuleIndex();
        String ruleName = (String)ruleNames[ruleIndex];
        return "RuleNode:"+ruleName;
      }
      if ((t instanceof ErrorNode)) {
        return "ErrorNode:"+t.toString();
      }
      if ((t instanceof TerminalNode)) {
        Token symbol = ((TerminalNode)t).getSymbol();
        if (symbol != null) {
          String s = symbol.getText();
          return "TerminalNode:"+s;
        }
      }
    }
    return null;
  }
  
  public static void getTreeText(Tree t, String rnames[]) 
  {
        System.out.println("payload:"+t.getPayload().toString());
        System.out.println(getNodeText(t,rnames));

        for(int i=0;i<t.getChildCount();i++)
        {
            Tree tree=(Tree)t.getChild(i);
            getTreeText(tree,rnames);
        }  
  }
    
	/**
	 *
	 * @param args
	 */
	public static void main(String args[]) throws Exception {

//		System.out.println("Work on file " + args[0]);

		int lineWidth = 80;
		if (args.length >= 2) {
			lineWidth = Integer.parseInt(args[1]);
		}

		SparqlLexer lex = null;
		try {                        
                        String q="PREFIX map: <http://datosabiertos.gob.mx/ontology/mapas.owl#>\n" +
"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
"\n" +
"SELECT \n" +
"    (CONCAT(?descripcionRecursoGeografico,\" (\",?tituloCapa,\")\") as ?titulo)\n" +
"    (CONCAT(\n" +
"        \"<h2>\",?titulo,\"</h2>\",\n" +
"        \"Estado:\",?estado,\"<br/>\",\n" +
"        \"Colonia:\",?colonia,\"<br/>\",\n" +
"        \"Calle:\",?calle,\"<br/>\",\n" +
"        \"CP:\",?cp,\"<br/>\",\n" +
"        \"(\",?latitud,\", \",?longitud,\")\"\n" +
"    ) as ?descripcion) \n" +
"    ?latitud \n" +
"    ?longitud \n" +
"WHERE {\n" +
"    ?uri rdf:type map:RecursoGeografico .\n" +
"    ?uri map:capa ?capa .\n" +
"    ?capa map:titulo ?tituloCapa .\n" +
"    ?uri map:descripcionRecursoGeografico ?descripcionRecursoGeografico .\n" +
"    ?uri map:estado ?estado .\n" +
"    ?uri map:colonia ?colonia .\n" +
"    ?uri map:calle ?calle .\n" +
"    ?uri map:cp ?cp .\n" +
"    ?uri map:latitud ?latitud .\n" +
"    ?uri map:longitud ?longitud .\n" +
"    filter( (?latitud>\"19.2\"^^xsd:double)  &&  (?latitud<\"19.3\"^^xsd:double)  && (?longitud<\"-99.1\"^^xsd:double)  &&  (?longitud>\"-99.2\"^^xsd:double) ) .\n" +
"}\n" +
"LIMIT 100";
			//lex = new SparqlLexer(new ANTLRFileStream(args[0]));
                        lex = new SparqlLexer(new ANTLRInputStream("select (count(*) as ?c) ?s ?p ?o where {?s a ?o; hola:asd <http://sdf.ser:sadasd>. ?s ?p2 ?o2}"));
                        //lex = new SparqlLexer(new ANTLRInputStream(q));
                        
		} catch (Exception ex) {
			Logger.getLogger(SparqlMain.class.getName()).log(Level.SEVERE, null, ex);
		}
		CommonTokenStream tokens = new CommonTokenStream(lex);

		System.out.println("Tokens: -------------------------------");

		tokens.fill();
		System.out.println("Number of tokens " + tokens.getTokens().size());

		List tokenList = tokens.getTokens();

		System.out.println("TokenList: -------------------------------");
		Iterator it = tokenList.iterator();
		while (it.hasNext()) {
			Token t = (Token) it.next();
			System.out.println(t.toString());
		}
		System.out.flush();
                
		System.out.println("Input from token list: -------------------------------");

		it = tokenList.iterator();
		while (it.hasNext()) {
			Token t = (Token) it.next();
			if (t.getType() != SparqlParser.EOF) {
				if (t.getType() == SparqlParser.WS || t.getType() == SparqlParser.COMMENT) {
					String s = t.getText();
					s = s.replace("\r\n", "\n");
					System.out.print(s);
				} else {
					System.out.print(t.getText());
				}
			}
		}
		System.out.flush();

		SparqlParser parser = new SparqlParser(tokens);
		parser.setBuildParseTree(true);
		
		System.out.println("Start parsing: -------------------------------");
		System.out.flush();

		ParserRuleContext t = parser.query();

		System.out.flush();
		System.out.println("Parse tree: -------------------------------");
		System.out.println(t.toStringTree(parser));

                int x=t.getRuleIndex();
                String rnames[]=parser.getRuleNames();
                
                getTreeText(t, rnames);
                
                
                
//if(true)return;
                
		// visualize parse tree in dialog box 
		t.inspect(parser);

		if (parser.getNumberOfSyntaxErrors() <= 0) {

			//ParseTreeWalker walker = new ParseTreeWalker();

			String groupFile = "/programming/proys/SWB4/swb/SWBPlatform/src/org/semanticwb/rdf/sparql/ident.stg";
			if (args.length > 1) {
				groupFile = args[1];
			}
			System.out.println("Read StringTemplate Group File: " + groupFile + "-------------------------------");

			STGroup g = new STGroupFile(groupFile);
//			IdentVisitor visitor = new IdentVisitor();
//			visitor.setSTGroup(g);
//			ST query = visitor.visit(t);
//
//			System.out.println("Emit reformatted query: -------------------------------");
//
//			System.out.println(query.render(lineWidth));
//
//			System.out.println("Emit original query: -------------------------------");
//
//			String q = query.render(lineWidth);
//
//			/* get common token stream */
//			File tmpFile = File.createTempFile("query_", ".rq");
//			FileOutputStream fo = new FileOutputStream(tmpFile);
//			OutputStreamWriter ow = new OutputStreamWriter(fo, "UTF8");
//			ow.write(q);
//			ow.close();
//			/* transformation pipline
//			 * step 1: Unicode pre-processing
//			 * step 2: Lexical analysis
//			 */
//			lex = new SparqlLexer(new ANTLRFileStream(tmpFile.getCanonicalPath(), "UTF8"));
			tokens = new CommonTokenStream(lex);

			List formattedTokenList = tokens.getTokens();

			it = tokenList.iterator();
			Iterator fit = formattedTokenList.iterator();			

			while (it.hasNext()) {
				Token originalToken = (Token) it.next();
				if (originalToken.getType() != SparqlParser.EOF) {
					if (originalToken.getType() == SparqlParser.WS || originalToken.getType() == SparqlParser.COMMENT) {
						String s = originalToken.getText();
						s = s.replace("\r\n", "\n");
						System.out.print(s);
					} else {
						System.out.print(originalToken.getText());
					}
				}
			}
			System.out.flush();

		}
		System.out.println("-------------------------------");
		System.out.println("Number of errors encountered: " + parser.getNumberOfSyntaxErrors());
	}
}
