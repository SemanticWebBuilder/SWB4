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
package org.semanticwb.portal.integration.lucene;

/*
 * WBLuceneIndexer.java
 *
 * Created on 23 de mayo de 2006, 05:53 PM
 */

import com.hp.hpl.jena.query.larq.IndexBuilderString;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Fieldable;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Searchable;
import org.semanticwb.model.User;
import org.semanticwb.portal.indexer.IndexTerm;
import org.semanticwb.portal.indexer.SWBIndexer;
import org.semanticwb.portal.indexer.parser.GenericParser;
import org.semanticwb.portal.indexer.searcher.SearchDocument;
import org.semanticwb.portal.indexer.searcher.SearchQuery;
import org.semanticwb.portal.indexer.searcher.SearchResults;
import org.semanticwb.portal.indexer.searcher.SearchTerm;
import org.semanticwb.portal.integration.lucene.analyzer.LocaleAnalyzer;

// TODO: Auto-generated Javadoc
/**
 * The Class SWBLuceneIndexer.
 * 
 * @author Javier Solis Gonzalez
 * @modified by Hasdai Pacheco {ebenezer.sanchez@infotec.com.mx}
 */
public class SWBLuceneIndexer extends SWBIndexer
{
    /** Max TopDocuments resulting for a search query*/
    public static final int MAX_RESULTS = 1000000;
    /** Current lucene version*/
    public static final Version LUCENE_VERSION = Version.LUCENE_36;
    /** The log. */
    private static Logger log=SWBUtils.getLogger(SWBLuceneIndexer.class);
    
    /** The df. */
    private SimpleDateFormat df=new SimpleDateFormat("yyyyMMddHHmmss");
    
    /** The index path. */
    private String indexPath;

    /** The writer. */
    private IndexWriter writer;
    
    // The reader. */
    //private IndexReader reader;

    /** The larq builder. */
    private IndexBuilderString larqBuilder;

    /** The searcher. */
    private IndexSearcher searcher;
    
    /** The analyzer. */
    private Analyzer analyzer;

    /** The remove model. */
    private String removeModel=null;
    
    /** The optimize. */
    private boolean optimize=false;
    
    /** The create index. */
    private boolean createIndex=false;
    
    /**
     * Creates a new instance of WBLuceneIndexer.
     */
    public SWBLuceneIndexer() {
        
    }
    
    /**
     * Inits the.
     */
    @Override
    public void init()
    {
        //analyzer=new SimpleAnalyzer();
        analyzer=new LocaleAnalyzer();
        
        log.info("Initializing WBLuceneIndexer");
        indexPath=SWBPortal.getWorkPath()+"/index/"+getName();
        File file=new File(indexPath);
        try {
            if(!IndexReader.indexExists(FSDirectory.open(file)))
            {
                createIndex();
            }
        } catch (Exception e) {
            
        }
    }
    
    /**
     * Init_writer.
     */
    private void init_writer()
    {
        //System.out.println("init_writer");
        if(writer==null)
        {
            try {
                //writer = new IndexWriter(indexPath, analyzer, createIndex);
                IndexWriterConfig cfg = new IndexWriterConfig(LUCENE_VERSION, analyzer);
                if (createIndex) {
                    cfg.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
                } else  {
                    cfg.setOpenMode(IndexWriterConfig.OpenMode.APPEND);
                }
                writer = new IndexWriter(FSDirectory.open(new File(indexPath)), cfg);
                larqBuilder = new IndexBuilderString(writer);
            } catch (Exception e) {
                log.error("Error creating index...",e);
            }
            createIndex=false;
        }
    }
    
    /**
     * Close_writer.
     */
    private void close_writer() {
        //System.out.println("close_writer");
        if(writer!=null) {
            try {
                larqBuilder.closeWriter();
                writer.close();
            }catch(Exception e){log.error(e);}
            finally
            {
                writer=null;
            }
        }
    }

    /**
     * Init_reader.
     */
//    private void init_reader()
//    {
//        //System.out.println("init_reader");
//        if(reader==null)
//        {
//            try {
//                reader = IndexReader.open(FSDirectory.open(new File(indexPath)));
//            }catch(Exception e){log.error(e);}
//        }
//    }

    /**
     * Close_reader.
     */
//    private void close_reader()
//    {
//        //System.out.println("close_reader");
//        if(reader!=null)
//        {
//            try {
//                reader.close();
//            }catch(Exception e){log.error(e);}
//            finally
//            {
//                reader=null;
//            }
//        }
//    }


    /**
     * _run.
     */
    @Override
    protected void _run() 
    {
        try
        {
            //System.out.println("_run");
            if(getIndexSize()>0)
            {
                //Eliminar elementos
                try {
                    //122init_reader();
                    init_writer();
                    removeRun();                //Eliminar elementos
                    if(removeModel!=null)       //Eliminar modelos
                    {
                        writer.deleteDocuments(new Term(ATT_MODEL, removeModel));
                        removeModel=null;
                    }
                } catch (Exception e) {
                    log.error("Error removing objects...",e);
                } finally {
                    close_writer();
                }
                //Indexar elementos
                try {
                    init_writer();
                    writeRun();
                    optimize=true;
                } catch (Exception e) {
                    log.error("Error indexing objects...",e);
                } finally {
                    close_writer();
                    reset_searcher();
                }
            }

            //Optimizar
//            if(optimize)
//            {
//                try {
//                    init_writer();
//                    //writer.optimize();
//                } catch (Exception e) {
//                    log.error("Error optimizing objects...",e);
//                } finally {
//                    close_writer();
//                    reset_searcher();
//                }
//                optimize=false;
//            }
        }finally
        {
            SWBPlatform.createInstance().endThreadRequest();
        }
    }

    /**
     * Reset_searcher.
     */
    private void reset_searcher()
    {
        if(searcher!=null)
        {
            try
            {
                searcher.close();
            }catch(Exception e){log.error(e);}
            finally
            {
                searcher=null;
            }
        }
    }

    /**
     * Removes the searchable obj.
     * 
     * @param uri the uri
     */
    @Override
    protected void removeSearchableObj(String uri)
    {
        //System.out.println("removeSearchableObj:"+uri);
        try
        {
            writer.deleteDocuments(new Term(ATT_URI, uri));
        }catch(Exception ex) {log.error(ex);}
    }

    /**
     * Write searchable obj.
     * 
     * @param obj the obj
     */
    @Override
    protected void writeSearchableObj(Searchable obj)
    {
        //System.out.println("writeSearchableObj:"+obj.getURI());
        GenericParser parser=getParser(obj);
        if(parser!=null)
        {
            try
            {
                Document doc = new Document();
                Iterator<IndexTerm> it=parser.getIndexTerms(obj).values().iterator();
                while (it.hasNext())
                {
                    IndexTerm indexTerm = it.next();
                    if(indexTerm.getText()!=null)
                    {
                        Field.Store store=Field.Store.YES;
                        if(!indexTerm.getStored()) {
                            store=Field.Store.NO;
                        }

                        Field.Index index=Field.Index.ANALYZED;
                        if(indexTerm.getIndexed()==IndexTerm.INDEXED_NO)index=Field.Index.NO;
                        else if(indexTerm.getIndexed()==IndexTerm.INDEXED_NO_ANALYZED) {
                            index=Field.Index.NOT_ANALYZED;
                        }

                        doc.add(new Field(indexTerm.getField(),indexTerm.getText(),store, index));
                    }
                }
                writer.addDocument(doc);
            }catch(Exception e){log.error(e);}
        }
    }

    /**
     * Removes the.
     */
    @Override
    public void remove()
    {
        //TODO:
    }
    
    /**
     * Reset.
     */
    @Override
    public void reset() 
    {
        createIndex();
    }
    
    /**
     * Un lock.
     * 
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @Override
    public void unLock() throws IOException {
        //IndexReader.unlock(org.apache.lucene.store.FSDirectory.getDirectory(indexPath, true));
        IndexWriter.unlock(FSDirectory.open(new File(indexPath)));
    }
    
    /**
     * Checks if is locked.
     * 
     * @return true, if is locked
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @Override
    public boolean isLocked() throws IOException {
        return IndexWriter.isLocked(FSDirectory.open(new File(indexPath)));
        //return IndexReader.isLocked(indexPath);
    }
    
    
    /**
     * Optimize.
     */
    @Override
    public void optimize() 
    {
        optimize=true;
    }

    /**
     * Gets the index path.
     * 
     * @return the index path
     */
    @Override
    public String getIndexPath()
    {
        return indexPath;
    }
    
    /**
     * Sets the index path.
     * 
     */
    public void setIndexPath(String indexPath)
    {
        this.indexPath=indexPath;
    }    

    /**
     * Removes the model.
     * 
     * @param modelid the modelid
     */
    @Override
    public void removeModel(String modelid) 
    {
        removeModel=modelid;
    }

    /**
     * Gets the query.
     * 
     * @param query the query
     * @return the query
     */
    private Query getQuery(SearchQuery query)
    {
        BooleanQuery bq = new BooleanQuery();
        Iterator<SearchTerm> it = query.listSearchTerms();
        while (it.hasNext())
        {
            SearchTerm t = it.next();
            Occur operation = Occur.MUST;
            if (t.getOperation() == SearchTerm.OPER_OR) {
                operation = Occur.SHOULD;
            }
            if (t.getOperation() == SearchTerm.OPER_NOT) {
                operation = Occur.MUST_NOT;
            }
            //System.out.println("add term:"+t.getField()+" "+t.getText()+" "+t.getOperation());
            String txt=t.getText();
            try
            {
                //bq.add(new QueryParser(t.getField(), analyzer).parse(txt), operation);
                txt=QueryParser.escape(txt);
                bq.add(new QueryParser(LUCENE_VERSION, t.getField(), analyzer).parse(txt), operation);
            }catch(Exception e){log.error(e);}
        }
        Iterator<SearchQuery> it2 = query.listQueries();
        while (it2.hasNext())
        {
            SearchQuery t = it2.next();
            Occur operation = Occur.MUST;
            if (t.getOperation() == SearchQuery.OPER_OR) {
                operation = Occur.SHOULD;
            }
            if (t.getOperation() == SearchQuery.OPER_NOT) {
                operation = Occur.MUST_NOT;
            }
            //System.out.println("add term:"+t.getField()+" "+t.getText()+" "+t.getOperation());
            try
            {
                bq.add(new BooleanClause(getQuery(t), operation));
            }catch(Exception e){log.error(e);}
        }
        return bq;
    }

    /**
     * Search.
     * 
     * @param query the query
     * @param user the user
     * @param sortFields the sort fields
     * @return the search results
     */
    @Override
    public SearchResults search(SearchQuery query, User user, String[] sortFields) {
        SearchResults ret = new SearchResults(user);
        Sort sort = null;

        if (sortFields != null) {
            //Build Sort attributes list
            SortField sortfs[] = new SortField[sortFields.length];
            for (int i = 0; i < sortFields.length; i++) {
                String field = sortFields[i];
                if (field.equals(ATT_SCORE)) {
                    sortfs[i]=SortField.FIELD_SCORE;
                } else {
                    //Reverse sort?
                    if (field.startsWith(ATT_INV)) {
                        //sortfs[i] = new SortField(field.substring(ATT_INV.length()), true);
                        sortfs[i] = new SortField(field.substring(ATT_INV.length()), new Locale(user.getLanguage()!=null?user.getLanguage():"es"), true);
                    } else {
                        //sortfs[i] = new SortField(field);
                        sortfs[i] = new SortField(field, new Locale(user.getLanguage()!=null?user.getLanguage():"es"));
                    }
                }
            }
            sort = new Sort(sortfs);
        }

        try {
            try {
                //Hits hits = null;
                TopDocs hits = null;
                //Para que no haya problemas al cerrar el reader de abajo
                if(searcher==null) {
                    //searcher = new IndexSearcher(FSDirectory.getDirectory((indexPath)));
                    searcher = new IndexSearcher(IndexReader.open(FSDirectory.open(new File(indexPath))));
                }
                
                if (sort == null) {
                    //hits = searcher.search(getQuery(query)); //y el filtro?
                    hits = searcher.search(getQuery(query), 100000); //y el filtro?
                } else {
                    //hits = searcher.search(getQuery(query), sort); //y el filtro?
                    hits = searcher.search(getQuery(query), 100000, sort); //y el filtro?
                }
//                for (int i = 0; i < hits.length(); i++) {
//                    Document doc = hits.doc(i);
//                    //System.out.println("doc:"+doc);
//                    ret.add(new SearchDocument(doc.get(ATT_URI), doc.get(ATT_SUMMARY), hits.score(i))); //Es correcto este score?
//                }
                ScoreDoc [] topDocs = hits.scoreDocs;
                float maxScore = 0;
                float minScore = 0;
                if (topDocs != null && topDocs.length > 0) {
                    maxScore = topDocs[0].score;
                    if (topDocs.length > 1) {
                        minScore = topDocs[topDocs.length - 1].score;
                    }
                }
                
                for (int i = 0; i < topDocs.length; i++) {
                    Document doc = searcher.doc(topDocs[i].doc);
                    float normScore = (topDocs[i].score - minScore)/(maxScore - minScore);
                    
                    HashMap map=new HashMap();
                    Iterator<Fieldable> it=doc.getFields().iterator();
                    while (it.hasNext())
                    {
                        Fieldable fieldable = it.next();
                        map.put(fieldable.name(), fieldable.stringValue());
                    }
                    
                    ret.add(new SearchDocument(doc.get(ATT_URI), doc.get(ATT_SUMMARY), normScore, map)); //Es correcto este score?
                }
            } catch(Exception e) {
                log.error(e);
            }
        } catch(Exception e) {
            log.error(e);
        }
        return ret;
    }

    /**
     * Search.
     * 
     * @param query the query
     * @param user the user
     * @return the search results
     */
    @Override
    public SearchResults search(SearchQuery query, User user) 
    {
        return search(query, user, null);
    }

    /**
     * Creates the index.
     */
    @Override
    protected void createIndex() 
    {
        createIndex=true;
    }
}