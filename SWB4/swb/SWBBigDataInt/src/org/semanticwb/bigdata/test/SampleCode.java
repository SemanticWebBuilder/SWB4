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


import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Properties;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.log4j.Logger;
import org.openrdf.model.Resource;
import org.openrdf.model.Statement;
import org.openrdf.model.URI;
import org.openrdf.model.Value;
import org.openrdf.model.impl.LiteralImpl;
import org.openrdf.model.impl.StatementImpl;
import org.openrdf.model.impl.URIImpl;
import org.openrdf.model.vocabulary.RDF;
import org.openrdf.model.vocabulary.RDFS;
import org.openrdf.query.Binding;
import org.openrdf.query.BindingSet;
import org.openrdf.query.GraphQuery;
import org.openrdf.query.QueryEvaluationException;
import org.openrdf.query.QueryLanguage;
import org.openrdf.query.TupleQuery;
import org.openrdf.query.TupleQueryResult;
import org.openrdf.query.algebra.evaluation.QueryBindingSet;
import org.openrdf.query.impl.BindingImpl;
import org.openrdf.repository.Repository;
import org.openrdf.repository.RepositoryConnection;
import org.openrdf.repository.RepositoryResult;
import org.openrdf.rio.RDFFormat;
import org.openrdf.rio.RDFWriterRegistry;
import org.openrdf.rio.helpers.StatementCollector;

import com.bigdata.journal.IIndexManager;
import com.bigdata.journal.Journal;
import com.bigdata.journal.StoreTypeEnum;
import com.bigdata.rdf.model.BigdataStatement;
import com.bigdata.rdf.sail.BigdataSail;
import com.bigdata.rdf.sail.BigdataSail.BigdataSailConnection;
import com.bigdata.rdf.sail.BigdataSailRepository;
import com.bigdata.rdf.sail.BigdataSailRepositoryConnection;
import com.bigdata.rdf.store.BD;
import com.bigdata.service.AbstractTransactionService;

/**
 * Demonstrate how to use bigdata.  You are free to use this code for whatever
 * purpose without restriction.
 * 
 * @author mikep
 */
public class SampleCode {
    
    /**
     * Do you have log4j set up correctly?  Bigdata turns its logging level way
     * down by default (WARN).  You should not be seeing INFO or DEBUG log 
     * statements from bigdata - if you are, then this will severely impact
     * performance.
     */
    protected static final Logger log = Logger.getLogger(SampleCode.class);

    /**
     * Load a Properties object from a file.
     * 
     * @param resource
     * @return
     * @throws Exception
     */
    public Properties loadProperties() throws Exception {
        Properties properties = new Properties();
        //InputStream is = getClass().getResourceAsStream(resource);
        //p.load(new InputStreamReader(new BufferedInputStream(is)));
        File journal = new File("/repository/vgn/bigdata.jnl");
        properties.setProperty(BigdataSail.Options.FILE, journal.getAbsolutePath());
        //The persistence engine.  Use 'Disk' for the WORM or 'DiskRW' for the RWStore.

        properties.setProperty("com.bigdata.journal.AbstractJournal.bufferMode", "DiskRW");
        properties.setProperty("com.bigdata.btree.writeRetentionQueue.capacity", "4000");
        properties.setProperty("com.bigdata.btree.BTree.branchingFactor", "128");

        //# 200M initial extent.
        properties.setProperty("com.bigdata.journal.AbstractJournal.initialExtent", "209715200");
        properties.setProperty("com.bigdata.journal.AbstractJournal.maximumExtent", "209715200");

        //##
        //## Setup for QUADS mode without the full text index.
        //##
        properties.setProperty("com.bigdata.rdf.sail.isolatableIndices", "true");
        properties.setProperty("com.bigdata.rdf.sail.truthMaintenance", "false");
        //com.bigdata.rdf.store.AbstractTripleStore.quads=false
        properties.setProperty("com.bigdata.rdf.store.AbstractTripleStore.quads", "true");
        properties.setProperty("com.bigdata.rdf.store.AbstractTripleStore.statementIdentifiers", "false");
        properties.setProperty("com.bigdata.rdf.store.AbstractTripleStore.textIndex", "false");
        properties.setProperty("com.bigdata.rdf.store.AbstractTripleStore.axiomsClass", "com.bigdata.rdf.axioms.NoAxioms");
        properties.setProperty("com.bigdata.rdf.store.AbstractTripleStore.vocabularyClass", "com.bigdata.rdf.vocab.NoVocabulary");
        properties.setProperty("com.bigdata.rdf.store.AbstractTripleStore.justify", "false");
        return properties;
    }
    
    /**
     * Add a statement to a repository.
     * 
     * @param repo
     * @throws Exception
     */
    public void loadSomeData(Repository repo) throws Exception {
        RepositoryConnection cxn = repo.getConnection();
        cxn.setAutoCommit(false);
        try {
            Resource s = new URIImpl("http://www.bigdata.com/rdf#Mike");
            URI p = new URIImpl("http://www.bigdata.com/rdf#loves");
            Value o = new URIImpl("http://www.bigdata.com/rdf#RDF");
            Statement stmt = new StatementImpl(s, p, o);
            cxn.add(stmt);
            cxn.commit();
        } catch (Exception ex) {
            cxn.rollback();
            throw ex;
        } finally {
            // close the repository connection
            cxn.close();
        }
    }

    /**
     * Load a document into a repository.
     * 
     * @param repo
     * @param resource
     * @param baseURL
     * @throws Exception
     */
    public void loadSomeDataFromADocument(Repository repo, String resource, 
        String baseURL) throws Exception {
        
        RepositoryConnection cxn = repo.getConnection();
        cxn.setAutoCommit(false);
        try {
            InputStream is = getClass().getResourceAsStream(resource);
            if (is == null && new File(resource).exists())
                is = new FileInputStream(resource);
            if (is == null)
                throw new Exception("Could not locate resource: " + resource);
            Reader reader = new InputStreamReader(new BufferedInputStream(is));
            cxn.add(reader, baseURL, RDFFormat.RDFXML);
            cxn.commit();
        } catch (Exception ex) {
            cxn.rollback();
            throw ex;
        } finally {
            // close the repository connection
            cxn.close();
        }
        
    }
    
    /**
     * Read some statements from a repository.
     * 
     * @param repo
     * @param uri
     * @throws Exception
     */
    public void readSomeData(Repository repo, URI uri) throws Exception {
        
        /*
         * With MVCC, you read from a historical state to avoid blocking and
         * being blocked by writers.  BigdataSailRepository.getQueryConnection
         * gives you a view of the repository at the last commit point.
         */
        RepositoryConnection cxn;
        if (repo instanceof BigdataSailRepository) { 
            cxn = ((BigdataSailRepository) repo).getReadOnlyConnection();
        } else {
            cxn = repo.getConnection();
        }
        
        try {
            
            RepositoryResult<Statement> stmts = 
                cxn.getStatements(uri, null, null, true /* include inferred */);
            while (stmts.hasNext()) {
                Statement stmt = stmts.next();
                Resource s = stmt.getSubject();
                URI p = stmt.getPredicate();
                Value o = stmt.getObject();
                // do something with the statement
                log.info(stmt);
                
                // cast to BigdataStatement to get at additional information
                BigdataStatement bdStmt = (BigdataStatement) stmt;
                if (bdStmt.isExplicit()) {
                    // do one thing
                } else if (bdStmt.isInferred()) {
                    // do another thing
                } else { // bdStmt.isAxiom()
                    // do something else
                }
                log.info(bdStmt.getStatementType());
            }
            
        } finally {
            // close the repository connection
            cxn.close();
        }
        
    }
    
    /**
     * Execute a "select" query.
     * 
     * @param repo
     * @param query
     * @param ql
     * @throws Exception
     */
    public void executeSelectQuery(Repository repo, String query, 
        QueryLanguage ql) throws Exception {
        
        /*
         * With MVCC, you read from a historical state to avoid blocking and
         * being blocked by writers.  BigdataSailRepository.getQueryConnection
         * gives you a view of the repository at the last commit point.
         */
        RepositoryConnection cxn;
        if (repo instanceof BigdataSailRepository) { 
            cxn = ((BigdataSailRepository) repo).getReadOnlyConnection();
        } else {
            cxn = repo.getConnection();
        }
        
        try {

            final TupleQuery tupleQuery = cxn.prepareTupleQuery(ql, query);
            tupleQuery.setIncludeInferred(true /* includeInferred */);
            TupleQueryResult result = tupleQuery.evaluate();
            // do something with the results
            while (result.hasNext()) {
                BindingSet bindingSet = result.next();
                log.info(bindingSet);
            }
            
        } finally {
            // close the repository connection
            cxn.close();
        }
        
    }
        
    /**
     * Execute a "construct" query.
     * 
     * @param repo
     * @param query
     * @param ql
     * @throws Exception
     */
    public void executeConstructQuery(Repository repo, String query, 
        QueryLanguage ql) throws Exception {
        
        /*
         * With MVCC, you read from a historical state to avoid blocking and
         * being blocked by writers.  BigdataSailRepository.getQueryConnection
         * gives you a view of the repository at the last commit point.
         */
        RepositoryConnection cxn;
        if (repo instanceof BigdataSailRepository) { 
            cxn = ((BigdataSailRepository) repo).getReadOnlyConnection();
        } else {
            cxn = repo.getConnection();
        }
        
        try {

            // silly construct queries, can't guarantee distinct results
            final Set<Statement> results = new LinkedHashSet<Statement>();
            final GraphQuery graphQuery = cxn.prepareGraphQuery(ql, query);
            graphQuery.setIncludeInferred(true /* includeInferred */);
            graphQuery.evaluate(new StatementCollector(results));
            // do something with the results
            for (Statement stmt : results) {
                log.info(stmt);
            }

        } finally {
            // close the repository connection
            cxn.close();
        }
        
    }

    /**
     * Demonstrate execution of a free-text query.
     * 
     * @param repo
     * @throws Exception
     */
    public boolean executeFreeTextQuery(Repository repo) throws Exception {
        if (((BigdataSailRepository) repo).getDatabase().getLexiconRelation()
                .getSearchEngine() == null) {
            /*
             * Only if the free text index exists.
             */
            return false;
        }
        RepositoryConnection cxn = repo.getConnection();
        cxn.setAutoCommit(false);
        try {
            cxn.add(new URIImpl("http://www.bigdata.com/A"), RDFS.LABEL,
                    new LiteralImpl("Yellow Rose"));
            cxn.add(new URIImpl("http://www.bigdata.com/B"), RDFS.LABEL,
                    new LiteralImpl("Red Rose"));
            cxn.add(new URIImpl("http://www.bigdata.com/C"), RDFS.LABEL,
                    new LiteralImpl("Old Yellow House"));
            cxn.add(new URIImpl("http://www.bigdata.com/D"), RDFS.LABEL,
                    new LiteralImpl("Loud Yell"));
            cxn.commit();
        } catch (Exception ex) {
            cxn.rollback();
            throw ex;
        } finally {
            // close the repository connection
            cxn.close();
        }
        
        String query = "select ?x where { ?x <"+BD.SEARCH+"> \"Yell\" . }";
        executeSelectQuery(repo, query, QueryLanguage.SPARQL);
        // will match A, C, and D
        return true;
    }

    /**
     * Demonstrate execution of statement level provenance.
     * 
     * @param repo
     * @throws Exception
     */
    public boolean executeProvenanceQuery(Repository repo) throws Exception {
        
        if(!((BigdataSailRepository)repo).getDatabase().isStatementIdentifiers()) {
            // IFF the KB is using the provenance mode,
            return false;
        }
        RepositoryConnection cxn = repo.getConnection();
        cxn.setAutoCommit(false);
        try {
            cxn.remove((Resource)null, (URI)null, (Value)null);
            cxn.commit();
            
            cxn.add(getReader(getClass(), "provenance.rdf"), 
                "", RDFFormat.RDFXML);
            cxn.commit();
            
            RepositoryResult<Statement> results = 
                cxn.getStatements(null, null, null, false);
            while(results.hasNext()) {
                log.info(results.next());
            }
            
        } catch (Exception ex) {
            cxn.rollback();
            throw ex;
        } finally {
            // close the repository connection
            cxn.close();
        }

        /*
         * With MVCC, you read from a historical state to avoid blocking and
         * being blocked by writers.  BigdataSailRepository.getQueryConnection
         * gives you a view of the repository at the last commit point.
         */
        if (repo instanceof BigdataSailRepository) { 
            cxn = ((BigdataSailRepository) repo).getReadOnlyConnection();
        } else {
            cxn = repo.getConnection();
        }

        try {

            RepositoryResult<Statement> results = 
                cxn.getStatements(null, null, null, false);
            while(results.hasNext()) {
                log.info(results.next());
            }
            
        } finally {
            // close the repository connection
            cxn.close();
        }
        
        String NS = "http://www.bigdata.com/rdf#";
        String MIKE = NS + "Mike";
        String LOVES = NS + "loves";
        String RDF = NS + "RDF";
        String query = 
            "construct { ?sid ?p ?o } " +
            "where { " +
            "  ?sid ?p ?o ." +
            "  graph ?sid { <"+MIKE+"> <"+LOVES+"> <"+RDF+"> } " +
            "}";
        executeConstructQuery(repo, query, QueryLanguage.SPARQL);
        // should see the provenance information for { Mike loves RDF }
        
        return true;
        
    }

    /**
     * Demonstrate execution of historical query using a read-only transaction.
     * <p>
     * Note: Bigdata preserves historical commit points until their release age
     * expires. This behavior is controlled by the deployment mode (RW, WORM, or
     * cluster) and by
     * {@link AbstractTransactionService.Options#MIN_RELEASE_AGE}. Except for
     * the WORM deployment mode, you MUST guard a historical commit point on
     * which you want to read using a read-lock. The read-lock itself is just a
     * read-only connection. It can be obtained for any historical commit point
     * that you want to "pin" and can be released once you are no longer need to
     * "pin" that commit point.
     * <p>
     * The read-lock is not required for the WORM deployment because it never
     * releases historical commit points.
     * 
     * @param repo
     * @throws Exception
     */
    public void executeHistoricalQuery(final Repository repo) throws Exception {

        if (!(repo instanceof BigdataSailRepository)) {
            return;
        }
        
        final IIndexManager indexManager = ((BigdataSailRepository) repo)
                .getDatabase().getIndexManager();
        
        final boolean isJournal = indexManager instanceof Journal;
        
        final boolean isWorm = isJournal
                && ((Journal) indexManager).getBufferStrategy().getBufferMode()
                        .getStoreType() == StoreTypeEnum.WORM;

        final URI MIKE = new URIImpl(BD.NAMESPACE+"Mike");
        final URI BRYAN = new URIImpl(BD.NAMESPACE+"Bryan");
        final URI PERSON = new URIImpl(BD.NAMESPACE+"Person");
        
        final RepositoryConnection cxn = repo.getConnection();
        try {
            cxn.setAutoCommit(false);

            cxn.remove((Resource)null, (URI)null, (Value)null);
            cxn.commit();
            
            cxn.add(MIKE, RDF.TYPE, PERSON);
            cxn.commit();

            final long time = System.currentTimeMillis();
            
            // Need a readLock connection if not a Worm store
            final RepositoryConnection readLock = isWorm ? null :
                ((BigdataSailRepository) repo).getReadOnlyConnection();
            
            final RepositoryConnection history;
            try {

                Thread.sleep(1000);

                cxn.add(BRYAN, RDF.TYPE, PERSON);
                cxn.commit();

                history = ((BigdataSailRepository) repo)
                        .getReadOnlyConnection(time);

            } finally {
                
                if (readLock != null)
                    readLock.close();
                
            }
            
            final String query = 
                "select ?s " +
                "where { " +
                "  ?s <"+RDF.TYPE+"> <"+PERSON+"> " +
                "}";
            
            try {

                final TupleQuery tupleQuery = 
                    history.prepareTupleQuery(QueryLanguage.SPARQL, query);
                tupleQuery.setIncludeInferred(false /* includeInferred */);
                TupleQueryResult result = tupleQuery.evaluate();

                Collection<BindingSet> answer = new LinkedList<BindingSet>();
                answer.add(createBindingSet(
                        new BindingImpl("s", MIKE)));
                
                compare(result, answer);
                
            } finally {
                // close the repository connection
                history.close();
            }

            // should see only Mike, not Bryan
            
        } catch (Exception ex) {
            cxn.rollback();
            throw ex;
        } finally {
            // close the repository connection
            cxn.close();
        }

    }

    /**
     * Run a simple LUBM load and query benchmark.
     * 
     * @param lubmResource the ZIP file containing the LUBM data files
     * @param filter helps filter out non-data files in the ZIP file
     * @throws Exception
     */
/*    public void doLUBMTest(final String lubmResource) 
        throws Exception {
        
        /*
         We are going to use the "fast load" mode for this LUBM test.  In fast 
         mode, we lose certain features, like the full text index and statement 
         identifiers.  The database also does not do inference automatically, so
         we have to tell the inference engine explicitly when to compute 
         closure.  Also, there is no recording of justification chains for 
         inferences, so this mode is extremely bad for retraction.  If we were 
         to retract a statement, we would have to tell the inference engine to 
         remove all inferences and completely re-compute the closure for the 
         entire database!
         */
/*        final Properties properties = loadProperties("fastload.properties");
        
        if (properties.getProperty(com.bigdata.journal.Options.FILE) == null) {
            /*
             * Create a backing temporary file iff none was specified in the
             * properties file.
             */
 /*           final File journal = File.createTempFile("bigdata", ".jnl");
            journal.deleteOnExit();
            properties.setProperty(BigdataSail.Options.FILE, journal
                    .getAbsolutePath());
        }
        
        // instantiate a sail
        BigdataSail sail = new BigdataSail(properties);
        Repository repo = new BigdataSailRepository(sail);
        repo.initialize();

        RepositoryConnection cxn = repo.getConnection();
        cxn.setAutoCommit(false);
        try {
            // fast range count!
            long stmtsBefore = sail.getDatabase().getStatementCount();
//            // full index scan!
//            long stmtsBefore = cxn.size();
            log.info("statements before: " + stmtsBefore);
            long start = System.currentTimeMillis();
            
            // first add the LUBM ontology
            cxn.add(getReader(getClass(), "univ-bench.owl"), LUBM.NS,
                    RDFFormat.RDFXML);
            
            // then process the LUBM sample data files one at a time
            InputStream is = getClass().getResourceAsStream(lubmResource);
            ZipInputStream zis = 
                new ZipInputStream(new BufferedInputStream(is));
            ZipEntry ze = null;
            while ((ze = zis.getNextEntry()) != null) {
                if (ze.isDirectory()) {
                    continue;
                }
                String name = ze.getName();
                log.info(name);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte[] bytes = new byte[4096];
                int count;
                while ((count = zis.read(bytes, 0, 4096)) != -1) {
                    baos.write(bytes, 0, count);
                }
                baos.close();
                Reader reader = new InputStreamReader(
                    new ByteArrayInputStream(baos.toByteArray())
                    );
                cxn.add(reader, LUBM.NS, RDFFormat.RDFXML);
            }
            zis.close();
            
            // autocommit is false, we need to commit our SAIL "transaction"
            cxn.commit();
            
            // when we are in "fast load" mode there is no automatic inference
            // as statements are loaded.  we therefore must invoke the inference
            // engine ourselves when we are done loading data.
            BigdataSailConnection sailCxn = (BigdataSailConnection)
                ((BigdataSailRepositoryConnection) cxn).getSailConnection();
            sailCxn.computeClosure();
            sailCxn.getTripleStore().commit();

            // gather statistics
            long elapsed = System.currentTimeMillis() - start;
            // fast range count!
            long stmtsAfter = ((BigdataSailRepository)repo).getDatabase().getStatementCount();
//            // full index scan!
//            long stmtsAfter = cxn.size();
            long stmtsAdded = stmtsAfter - stmtsBefore;
            int throughput =
                    (int) ((double) stmtsAdded / (double) elapsed * 1000d);
            log.info("statements after: " + stmtsAfter);
            log.info("loaded: " + stmtsAdded + " in " + elapsed
                    + " millis: " + throughput + " stmts/sec");
            
            // ok, now let's do one of the LUBM queries
            /*
            [query4]
            PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
            PREFIX ub: <http://www.lehigh.edu/~zhp2/2004/0401/univ-bench.owl#>
            SELECT ?x ?y1 ?y2 ?y3
            WHERE{
                ?x a ub:Professor;
                    ub:worksFor <http://www.Department0.University0.edu>;
                    ub:name ?y1;
                    ub:emailAddress ?y2;
                    ub:telephone ?y3.
            }
            */
            // build LUBM query 4 using the handy SparqlBuilder utility
            // note: SparqlBuilder is for "construct" queries only
            // but you could modify it easily to do "select" instead
/*            SparqlBuilder sb = new SparqlBuilder();
            sb.addTriplePattern("?x", RDF.TYPE, LUBM.PROFESSOR);
            sb.addTriplePattern("?x", LUBM.WORKS_FOR, 
                    new URIImpl("http://www.Department0.University0.edu"));
            sb.addTriplePattern("?x", LUBM.NAME, "?y1");
            sb.addTriplePattern("?x", LUBM.EMAIL_ADDRESS, "?y2");
            sb.addTriplePattern("?x", LUBM.TELEPHONE, "?y3");

            log.info("evaluating LUBM query 4...");
            start = System.currentTimeMillis();
            
            final GraphQuery graphQuery = 
                cxn.prepareGraphQuery(QueryLanguage.SPARQL, sb.toString());
            final StringWriter sw = new StringWriter();
            graphQuery.evaluate(RDFWriterRegistry.getInstance().get(RDFFormat.RDFXML).getWriter(sw));
            
            elapsed = System.currentTimeMillis() - start;
            log.info("done. evaluted in " + elapsed + " millis");
            
            // if you want to see the output, here it is:
            log.info(sw.toString());
                        
        } catch (Exception ex) {
            cxn.rollback();
            throw ex;
        } finally {
            // close the repository connection
            cxn.close();
        }
     }        
     * 
     */

    
    public void doU10() throws Exception {
        //doLUBMTest("U10.zip");
    }

    public void doU1() throws Exception {
        //doLUBMTest("U1.zip");
    }

    public Reader getReader(Class c, String resource) {
        InputStream is = c.getResourceAsStream(resource);
        return new InputStreamReader(new BufferedInputStream(is));
    }
    
    /**
     * Are you running with the -server JVM option?  You should be.  Also, try
     * increasing heap size with -Xmx512m or even -Xmx1024m if you have enough
     * RAM.
     * 
     * @param args
     */
    public static void main(final String[] args) {
        // use one of our pre-configured option-sets or "modes"
//        final String propertiesFile = "fullfeature.properties";
//         final String propertiesFile = "rdfonly.properties";
//         final String propertiesFile = "fastload.properties";
         final String propertiesFile = "quads.properties";
        try {
            
            final SampleCode sampleCode = new SampleCode();

            log.info("Reading properties from file: " + propertiesFile);

            final Properties properties = sampleCode.loadProperties();

            if (properties.getProperty(com.bigdata.journal.Options.FILE) == null) {
                /*
                 * Create a backing file iff none was specified in the
                 * properties file.
                 */
                File journal = new File("/repository/vgn/bigdata123.jnl");
                log.info(journal.getAbsolutePath());
                properties.setProperty(BigdataSail.Options.FILE, journal
                        .getAbsolutePath());
            }
            if (properties
                    .getProperty(AbstractTransactionService.Options.MIN_RELEASE_AGE) == null) {
                // Retain old commit points for at least 60s.
                properties.setProperty(
                        AbstractTransactionService.Options.MIN_RELEASE_AGE,
                        "60000");
            }
            
            // instantiate a sail
            final BigdataSail sail = new BigdataSail(properties);
            final Repository repo = new BigdataSailRepository(sail);
            try {
            
                repo.initialize();

                // demonstrate some basic functionality
                final URI MIKE = new URIImpl(
                        "http://www.bigdata.com/rdf#Mike");
                sampleCode.loadSomeData(repo);
                System.out.println("Loaded sample data.");
                sampleCode.readSomeData(repo, MIKE);
                //sampleCode.executeSelectQuery(repo,
                //        "select ?p ?o where { <" + MIKE.toString()
                 //               + "> ?p ?o . }", QueryLanguage.SPARQL);
                //System.out.println("Did SELECT query.");
                //sampleCode.executeConstructQuery(repo,
                //        "construct { <" + MIKE.toString()
                //                + "> ?p ?o . } where { <" + MIKE.toString()
                //                + "> ?p ?o . }", QueryLanguage.SPARQL);
               // System.out.println("Did CONSTRUCT query.");
                if (sampleCode.executeFreeTextQuery(repo)) {
                    System.out.println("Did free text query.");
                }
                if (sampleCode.executeProvenanceQuery(repo)) {
                    System.out.println("Did provenance query.");
                }
                sampleCode.executeHistoricalQuery(repo);
                System.out.println("Did historical query.");

                System.out.println("done.");

                // run one of the LUBM tests
                // sampleCode.doU10(); // I see loaded: 1752215 in 116563 millis: 15032 stmts/sec, what do you see?
                // sampleCode.doU1();

            } finally {
                repo.shutDown();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    protected BindingSet createBindingSet(final Binding... bindings) {
        final QueryBindingSet bindingSet = new QueryBindingSet();
        if (bindings != null) {
            for (Binding b : bindings) {
                bindingSet.addBinding(b);
            }
        }
        return bindingSet;
    }
    
    protected void compare(final TupleQueryResult result,
            final Collection<BindingSet> answer)
            throws QueryEvaluationException {

        try {
            
            final Collection<BindingSet> extraResults = new LinkedList<BindingSet>();
            Collection<BindingSet> missingResults = new LinkedList<BindingSet>();
    
            int resultCount = 0;
            int nmatched = 0;
            while (result.hasNext()) {
                BindingSet bindingSet = result.next();
                resultCount++;
                boolean match = false;
                if(log.isInfoEnabled())
                    log.info(bindingSet);
                Iterator<BindingSet> it = answer.iterator();
                while (it.hasNext()) {
                    if (it.next().equals(bindingSet)) {
                        it.remove();
                        match = true;
                        nmatched++;
                        break;
                    }
                }
                if (match == false) {
                    extraResults.add(bindingSet);
                }
            }
            missingResults = answer;
    
            for (BindingSet bs : extraResults) {
                if (log.isInfoEnabled()) {
                    log.info("extra result: " + bs);
                }
            }
            
            for (BindingSet bs : missingResults) {
                if (log.isInfoEnabled()) {
                    log.info("missing result: " + bs);
                }
            }
            
            if (!extraResults.isEmpty() || !missingResults.isEmpty()) {
                throw new RuntimeException("matchedResults=" + nmatched + ", extraResults="
                        + extraResults.size() + ", missingResults="
                        + missingResults.size());
            }

        } finally {
            
            result.close();
            
        }
        
    }
    
}
