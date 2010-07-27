/**  
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
**/ 
 
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
import java.util.Date;
import java.util.Iterator;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Hits;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.Searcher;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.store.FSDirectory;
import org.semanticwb.Logger;
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

/**
 *
 * @author Javier Solis Gonzalez
 * @modified by Hasdai Pacheco {haxdai@gmail.com}
 */
public class SWBLuceneIndexer extends SWBIndexer
{

    private static Logger log=SWBUtils.getLogger(SWBLuceneIndexer.class);
    private SimpleDateFormat df=new SimpleDateFormat("yyyyMMddHHmmss");
    
    private String indexPath;

    private IndexWriter writer;
    private IndexReader reader;

    private IndexBuilderString larqBuilder;

    private Searcher searcher;
    private Analyzer analyzer;

    private String removeModel=null;
    private boolean optimize=false;
    private boolean createIndex=false;
    
    /** Creates a new instance of WBLuceneIndexer */
    public SWBLuceneIndexer() {
        
    }

    private String getDate(Date date)
    {
        return df.format(date);
    }
    
    public void init()
    {
        //analyzer=new SimpleAnalyzer();
        analyzer=new LocaleAnalyzer();
        
        log.info("Initializing WBLuceneIndexer");
        indexPath=SWBPortal.getWorkPath()+"/index/"+getName();
        File file=new File(indexPath);
        if(!IndexReader.indexExists(file))
        {
            createIndex();
        }
    }
    
    private void init_writer()
    {
        //System.out.println("init_writer");
        if(writer==null) 
        {
            try {
                writer = new IndexWriter(indexPath, analyzer, createIndex);
                larqBuilder = new IndexBuilderString(writer);
            } catch (Exception e) {
                log.error("Error creating index...",e);
            }
            createIndex=false;
        }
    }
    
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

    private void init_reader()
    {
        //System.out.println("init_reader");
        if(reader==null)
        {
            try {
                reader = IndexReader.open(indexPath);
            }catch(Exception e){log.error(e);}
        }
    }

    private void close_reader()
    {
        //System.out.println("close_reader");
        if(reader!=null)
        {
            try {
                reader.close();
            }catch(Exception e){log.error(e);}
            finally
            {
                reader=null;
            }
        }
    }


    @Override
    protected void _run() 
    {
        //System.out.println("_run");
        if(getIndexSize()>0)
        {
            //Eliminar elementos
            try {
                init_reader();
                removeRun();                //Eliminar elementos
                if(removeModel!=null)       //Eliminar modelos
                {
                    reader.deleteDocuments(new Term(ATT_MODEL, removeModel));
                    removeModel=null;
                }
            } catch (Exception e) {
                log.error("Error removing objects...",e);
            } finally {
                close_reader();
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
        if(optimize)
        {
            try {
                init_writer();
                writer.optimize();
            } catch (Exception e) {
                log.error("Error optimizing objects...",e);
            } finally {
                close_writer();
                reset_searcher();
            }
            optimize=false;
        }
    }

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

    @Override
    protected void removeSearchableObj(String uri)
    {
        //System.out.println("removeSearchableObj:"+uri);
        try
        {
            reader.deleteDocuments(new Term(ATT_URI, uri));
        }catch(Exception ex) {log.error(ex);}
    }

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

    public void remove()
    {
        //TODO:
    }
    
    public void reset() 
    {
        createIndex();
    }
    
    public void unLock() throws IOException {
        IndexReader.unlock(org.apache.lucene.store.FSDirectory.getDirectory(indexPath, true));
    }
    
    public boolean isLocked() throws IOException {
        return IndexReader.isLocked(indexPath);
    }
    
    
    public void optimize() 
    {
        optimize=true;
    }

    @Override
    public String getIndexPath()
    {
        return indexPath;
    }

    @Override
    public void removeModel(String modelid) 
    {
        removeModel=modelid;
    }

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
                bq.add(new QueryParser(t.getField(), analyzer).parse(txt), operation);
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
                        sortfs[i] = new SortField(field.substring(ATT_INV.length()), true);
                    } else {
                        sortfs[i] = new SortField(field);
                    }
                }
            }
            sort = new Sort(sortfs);
        }

        try {
            try {
                Hits hits = null;
                //Para que no haya problemas al cerrar el reader de abajo
                if(searcher==null) {
                    searcher = new IndexSearcher(FSDirectory.getDirectory((indexPath)));
                }
                
                if (sort == null) {
                    hits = searcher.search(getQuery(query)); //y el filtro?
                } else {
                    hits = searcher.search(getQuery(query), sort); //y el filtro?
                }

                for (int i = 0; i < hits.length(); i++) {
                    Document doc = hits.doc(i);
                    //System.out.println("doc:"+doc);
                    ret.add(new SearchDocument(doc.get(ATT_URI), doc.get(ATT_SUMMARY), hits.score(i))); //Es correcto este score?
                }
            } catch(Exception e) {
                log.error(e);
            }
        } catch(Exception e) {
            log.error(e);
        }
        return ret;
    }

    @Override
    public SearchResults search(SearchQuery query, User user) 
    {
        return search(query, user, null);
    }

    @Override
    protected void createIndex() 
    {
        createIndex=true;
    }
}