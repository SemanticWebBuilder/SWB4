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
package org.semanticwb.bigdata.test;

import com.bigdata.rdf.sail.BigdataSail;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.rdf.model.impl.ModelCom;
import com.hp.hpl.jena.vocabulary.RDF;
import java.io.File;
import java.util.Properties;
import org.openrdf.query.BindingSet;
import org.openrdf.query.QueryEvaluationException;
import org.openrdf.query.QueryLanguage;
import org.openrdf.query.TupleQueryResult;
import org.semanticwb.bigdata.BigdataGraph;

/**
 *
 * @author jei
 */
public class TestGraph {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        Properties properties = new Properties();
        File journal = new File("/data.jnl");
        properties.setProperty(BigdataSail.Options.FILE, journal.getAbsolutePath());
        //The persistence engine.  Use 'Disk' for the WORM or 'DiskRW' for the RWStore.
        properties.setProperty("com.bigdata.journal.AbstractJournal.bufferMode","DiskRW");
        properties.setProperty("com.bigdata.btree.writeRetentionQueue.capacity", "4000");
        properties.setProperty("com.bigdata.btree.BTree.branchingFactor", "128");

        //# 200M initial extent.
        properties.setProperty("com.bigdata.journal.AbstractJournal.initialExtent", "100715200");
        properties.setProperty("com.bigdata.journal.AbstractJournal.maximumExtent", "100715200");

        //##
        //## Setup for QUADS mode without the full text index.
        //##
        properties.setProperty("com.bigdata.rdf.sail.isolatableIndices", "true");
        properties.setProperty("com.bigdata.rdf.sail.truthMaintenance", "false");
        properties.setProperty("com.bigdata.rdf.store.AbstractTripleStore.quads", "true");
        properties.setProperty("com.bigdata.rdf.store.AbstractTripleStore.statementIdentifiers", "false");
        properties.setProperty("com.bigdata.rdf.store.AbstractTripleStore.textIndex", "false");
        properties.setProperty("com.bigdata.rdf.store.AbstractTripleStore.axiomsClass", "com.bigdata.rdf.axioms.NoAxioms");
        properties.setProperty("com.bigdata.rdf.store.AbstractTripleStore.vocabularyClass", "com.bigdata.rdf.vocab.NoVocabulary");
        properties.setProperty("com.bigdata.rdf.store.AbstractTripleStore.justify", "false");
         

        //fast load

        // set the initial and maximum extent of the journal
        //properties.setProperty(BigdataSail.Options.INITIAL_EXTENT,"209715200");
        //properties.setProperty(BigdataSail.Options.MAXIMUM_EXTENT,"209715200");
        // turn off automatic inference in the SAIL
        //properties.setProperty(BigdataSail.Options.TRUTH_MAINTENANCE,"false");
        // don't store justification chains, meaning retraction requires full manual
        // re-closure of the database
        //properties.setProperty(BigdataSail.Options.JUSTIFY,"false");
        // turn off the free text index
        //properties.setProperty(BigdataSail.Options.TEXT_INDEX,"false");

        // instantiate a sail
        BigdataSail sail = new BigdataSail(properties);
        System.out.println("NameSapce:"+sail.getDatabase().getNamespace());
        BigdataGraph graph = new BigdataGraph(sail,false);
        Model model = new ModelCom(graph);

        int seg=6;

        long time=System.currentTimeMillis();
        Resource res=model.getResource("http://www.softjei.com#Person"+(((seg)*1000000)+401799));
        StmtIterator stit=model.listStatements(res, null, (RDFNode)null);
        boolean init=true;
        if(stit.hasNext())init=false;
        while (stit.hasNext())
        {
            Statement statement = stit.next();
            System.out.println("Statement:"+statement);
//            if(statement.getObject().toString().equals("ok"))
//            {
//                System.out.println("remove");
//                stit.remove();
//            }
        }
        System.out.println("getStatements1:"+(System.currentTimeMillis()-time));
        time=System.currentTimeMillis();

        if(init)
        {
            model.begin();
            time=System.currentTimeMillis();
            for(int x=(seg*1000000);x<1000000*(seg+1);x++)
            {
                res=model.getResource("http://www.softjei.com#Person"+x);
                //if(!model.contains(res, RDF.type, model.getResource("http://www.lehigh.edu/~zhp2/2004/0401/univ-bench.owl#Person")))
                {
                    res.addProperty(RDF.type, model.getResource("http://www.lehigh.edu/~zhp2/2004/0401/univ-bench.owl#Person"));
                    res.addProperty(model.getProperty("http://www.lehigh.edu/~zhp2/2004/0401/univ-bench.owl#title"), "Persona "+x);
                    res.addLiteral(model.getProperty("http://www.lehigh.edu/~zhp2/2004/0401/univ-bench.owl#age"), x%100);
                    res.addLiteral(model.getProperty("http://www.lehigh.edu/~zhp2/2004/0401/univ-bench.owl#time"), System.currentTimeMillis());
                    if(x>400000+((seg+1)*1000000) && x<450000+((seg+1)*1000000))res.addLiteral(model.getProperty("http://www.lehigh.edu/~zhp2/2004/0401/univ-bench.owl#active"), true);
                    else res.addLiteral(model.getProperty("http://www.lehigh.edu/~zhp2/2004/0401/univ-bench.owl#active"), false);
                }
                if(x%1000==0)System.out.println(x);
            }
            model.commit();
            System.out.println("load:"+(System.currentTimeMillis()-time));
            time=System.currentTimeMillis();
        }

        time=System.currentTimeMillis();
        model.begin();
        res.addLiteral(model.getProperty("http://www.softjei.com#ok"), true);
        model.add(res, model.getProperty("http://www.softjei.com#maried"), model.createLiteral("ok1"));
        model.add(res, model.getProperty("http://www.softjei.com#maried"), model.createLiteral("ok2"));
        model.add(res, model.getProperty("http://www.softjei.com#maried"), model.createLiteral("ok3"));
        model.add(res, model.getProperty("http://www.softjei.com#maried"), model.createLiteral("ok4"));
        model.commit();
        System.out.println("addStatement:"+(System.currentTimeMillis()-time));
        time=System.currentTimeMillis();

        time=System.currentTimeMillis();
        model.begin();
        model.remove(res, model.getProperty("http://www.softjei.com#maried"), model.createLiteral("ok2"));
        model.remove(res, model.getProperty("http://www.softjei.com#maried"), model.createLiteral("ok3"));
        model.commit();
        System.out.println("removeStatement:"+(System.currentTimeMillis()-time));
        time=System.currentTimeMillis();

        time=System.currentTimeMillis();
        stit=model.listStatements(res, null, (RDFNode)null);
        while (stit.hasNext())
        {
            Statement statement = stit.next();
            System.out.println("Statement:"+statement);
        }
        System.out.println("getStatements2:"+(System.currentTimeMillis()-time));
        time=System.currentTimeMillis();

        time=System.currentTimeMillis();
        System.out.println(model.size());
        System.out.println("count:"+(System.currentTimeMillis()-time));
        time=System.currentTimeMillis();

        time=System.currentTimeMillis();
        model.removeAll(res, model.getProperty("http://www.softjei.com#maried"), null);
        System.out.println("removeAll:"+(System.currentTimeMillis()-time));
        time=System.currentTimeMillis();

        //SparQL
/*
        String query=
            //"select ?age " +
            "select ?time ?s ?age " +
            //"select ?s " +
            "where { " +
            "  ?s <http://www.lehigh.edu/~zhp2/2004/0401/univ-bench.owl#time> ?time . " +
            "  ?s <http://www.lehigh.edu/~zhp2/2004/0401/univ-bench.owl#active> true . " +
            "  ?s <http://www.lehigh.edu/~zhp2/2004/0401/univ-bench.owl#age> ?age . " +
            "  FILTER (?age > 10) . " +
            "  FILTER (?age < 30) . " +
            //"  FILTER (?age = 30) . " +
            //" filter (?time > 129826447539) . " +
            "  ?s <"+RDF.type+"> <http://www.lehigh.edu/~zhp2/2004/0401/univ-bench.owl#Person> . " +
            //"  ?s <http://www.lehigh.edu/~zhp2/2004/0401/univ-bench.owl#researchInterest> ?res . " +
            "} " +
            //"order by desc(?time) " +
            "limit 20 offset 0 " +
            "";

        time=System.currentTimeMillis();
        TupleQueryResult result=graph.executeQuery(query, QueryLanguage.SPARQL, false);
        try
        {
            while (result.hasNext())
            {
                BindingSet bindingSet = result.next();
                System.out.println("bindingSet:" + bindingSet);
            }
            //result.close();
        } catch (QueryEvaluationException ex)
        {
            ex.printStackTrace();
        }
        System.out.println("query:"+(System.currentTimeMillis()-time));
        time=System.currentTimeMillis();

        time=System.currentTimeMillis();
        result=graph.executeQuery(query, QueryLanguage.SPARQL, false);
        try
        {
            while (result.hasNext())
            {
                BindingSet bindingSet = result.next();
                System.out.println("bindingSet:" + bindingSet);
            }
            //result.close();
        } catch (QueryEvaluationException ex)
        {
            ex.printStackTrace();
        }
        System.out.println("query:"+(System.currentTimeMillis()-time));
        time=System.currentTimeMillis();
*/
        model.close();

    }



}
