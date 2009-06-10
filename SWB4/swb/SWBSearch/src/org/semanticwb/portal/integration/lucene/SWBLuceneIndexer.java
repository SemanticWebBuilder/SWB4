package org.semanticwb.portal.integration.lucene;

/*
 * WBLuceneIndexer.java
 *
 * Created on 23 de mayo de 2006, 05:53 PM
 */

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.index.TermDocs;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Hits;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.QueryFilter;
import org.apache.lucene.search.Searcher;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.TermQuery;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.User;
import org.semanticwb.model.WebPage;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.portal.api.SWBParamRequestImp;
import org.semanticwb.portal.api.SWBResource;
import org.semanticwb.portal.indexer.SWBIndexObj;
import org.semanticwb.portal.indexer.SWBIndexObjList;
import org.semanticwb.portal.indexer.SWBIndexer;
import org.semanticwb.portal.integration.lucene.analyzer.LocaleAnalyzer;
import org.semanticwb.portal.integration.lucene.parser.html.HTMLParser;
import org.semanticwb.portal.lib.SWBRequest;
import org.semanticwb.portal.lib.SWBResponse;
import org.semanticwb.servlet.SWBHttpServletRequestWrapper;

/**
 *
 * @author Javier Solis Gonzalez
 */
public class SWBLuceneIndexer extends SWBIndexer
{
    private static Logger log=SWBUtils.getLogger(SWBLuceneIndexer.class);

    public static final String ATT_INT_CATEGORY="intCategory";
    
    String indexPath;
    IndexWriter writer;
    Searcher searcher;
    Analyzer analyzer;
    
    /** Creates a new instance of WBLuceneIndexer */
    public SWBLuceneIndexer() {
        
    }
    
    public void init() {
        //analyzer=new SimpleAnalyzer();
        analyzer=new LocaleAnalyzer();
        
        log.info("Initializing WBLuceneIndexer");
        indexPath=SWBPlatform.getWorkPath()+"/index/"+getName();
        File file=new File(indexPath);
        if(!file.exists()) {
            createIndex();
        }
    }
    
    @Override
    protected void _run() {
        if(getIndexSize()==0)return;
        try {
            if(writer==null) {
                writer = new IndexWriter(indexPath, analyzer, false);
            }
            
            super._run();
            
            if(writer==null) {
                writer = new IndexWriter(indexPath, analyzer, false);
            }
            
            writer.optimize();
        } catch (Exception e) {
            log.error("Error indexing objects...",e);
        } finally {
            if(writer!=null) {
                try {
                    writer.close();
                } catch (Exception e){log.error("Error closing indexer...",e);}
                writer=null;
            }
        }
    }
    
    public void createIndex() {
        close_searcher();
        close_writer();
        
        IndexWriter writer=null;
        try {
            File file=new File(indexPath);
            file.mkdirs();
            writer = new IndexWriter(indexPath, analyzer, true);
        }catch(Exception e){log.error(e);}
        finally {
            if(writer!=null) {
                try {
                    writer.close();
                }catch(Exception e){log.error(e);}
            }
            writer=null;
        }
    }
    
    private void close_searcher() {
        if(searcher!=null) {
            try {
                searcher.close();
                searcher=null;
            }catch(Exception e){log.error(e);}
        }
    }
    
    private void close_writer() {
        if(writer!=null) {
            try {
                writer.close();
                writer=null;
            }catch(Exception e){log.error(e);}
        }
    }
    
    private String replaceUnder(String txt)
    {
        if(txt==null)return null;
        return "X"+txt.replaceAll("_","UnDeR").replaceAll(" ","X X")+"X";
    }            
    
    protected void writeObject(SWBIndexObj obj) {
        //System.out.println("writeObject:"+obj.getId());
        close_searcher();
        try {
            if(writer==null) {
                writer = new IndexWriter(indexPath, analyzer, false);
            }
            
            Document doc = new Document();
            String id=obj.getId();
            String type=obj.getType();
            String topicid=obj.getTopicID();
            String topicmapid=obj.getTopicMapID();
            String resid=obj.getResId();
            String url=obj.getUrl();
            String lang=obj.getLang();
            String title=obj.getTitle();
            String summary=obj.getSummary();
            String category=obj.getCategory();
            String data=obj.getData();
            
            //validacion para busqueda
            String intCategory=replaceUnder(category);
            
            if(id!=null)doc.add(new Field(ATT_ID,id,Field.Store.YES, Field.Index.UN_TOKENIZED));
            if(type!=null)doc.add(new Field(ATT_TYPE,type,Field.Store.YES, Field.Index.UN_TOKENIZED));
            if(topicid!=null)doc.add(new Field(ATT_TOPIC,topicid,Field.Store.YES, Field.Index.UN_TOKENIZED));
            if(topicmapid!=null)doc.add(new Field(ATT_TOPICMAP,topicmapid,Field.Store.YES, Field.Index.UN_TOKENIZED));  //For Filter by topicmap
            if(resid!=null)doc.add(new Field(ATT_RESID,resid,Field.Store.YES, Field.Index.UN_TOKENIZED));
            if(lang!=null)doc.add(new Field(ATT_LANG,lang,Field.Store.YES, Field.Index.UN_TOKENIZED));
            if(url!=null)doc.add(new Field(ATT_URL,url,Field.Store.YES, Field.Index.NO));
            if(topicmapid!=null)doc.add(new Field("i"+ATT_TOPICMAP, topicmapid,Field.Store.YES, Field.Index.TOKENIZED));  //For serch by topicmap
            if(title!=null)doc.add(new Field(ATT_TITLE,title,Field.Store.YES, Field.Index.TOKENIZED));
            if(category!=null) {
                doc.add(new Field(ATT_CATEGORY,category,Field.Store.YES,Field.Index.UN_TOKENIZED));
                doc.add(new Field(ATT_INT_CATEGORY,intCategory,Field.Store.YES,Field.Index.TOKENIZED));
            }else {
                doc.add(new Field(ATT_CATEGORY,"",Field.Store.YES,Field.Index.UN_TOKENIZED));
                doc.add(new Field(ATT_INT_CATEGORY,"",Field.Store.YES,Field.Index.TOKENIZED));
            }
            if(data!=null)doc.add(new Field(ATT_DATA,data,Field.Store.YES, Field.Index.TOKENIZED));
            if(summary!=null)doc.add(new Field(ATT_SUMMARY,summary,Field.Store.YES, Field.Index.NO));
            
            writer.addDocument(doc);
            
            //writer.optimize();
            //writer.close();
        } catch (Exception e) {
            log.error("Error indexing object:"+obj,e);
        }
    }
    
    public void parseObject(SWBIndexObj obj) {
        if(obj.getType().equals(SWBIndexObj.TYPE_FILE)) {
            parseFile(obj);
        }else if(obj.getType().equals(SWBIndexObj.TYPE_TOPIC)) {
            parseTopic(obj);
        }else if(obj.getType().equals(SWBIndexObj.TYPE_CONTENT)) {
            parseContent(obj);
        }else if(obj.getType().equals(SWBIndexObj.TYPE_URL)) {
            parseURL(obj);
        }else if(obj.getType().equals(SWBIndexObj.TYPE_DATA)) {
            parseData(obj);
        }else if(obj.getType().equals(SWBIndexObj.TYPE_USER)) {
            parseUser(obj);
        }else if(obj.getType().equals(SWBIndexObj.TYPE_HTML)) {
            parseHTML(obj);
        }
    }
      
    protected void parseFile(SWBIndexObj obj) {
        try{
            String sfile=obj.getId();
            int pos=-1;
            pos=sfile.lastIndexOf(".");
            if(pos>-1){
                String sdata=null;
                DocumentExtractorSrv docExtracSrv=new DocumentExtractorSrv();
                File file=new File(sfile);
                String sext=sfile.substring(pos+1);
                if(sext.toLowerCase().trim().equals("xls")){ // excell file type
                    sdata=docExtracSrv.excelExtractor(file);
                    if(sdata!=null && sdata.trim().length()>0){
                        obj.setData(sdata);
                    }
                }else if(sext.toLowerCase().trim().equals("doc")){ // word file type
                    sdata=docExtracSrv.wordExtractor(file);
                    if(sdata!=null && sdata.trim().length()>0){
                        obj.setData(sdata);
                    }
                }else if(sext.toLowerCase().trim().equals("ppt")){ // powerpoint file type
                    sdata=docExtracSrv.pptExtractor(file);
                    if(sdata!=null && sdata.trim().length()>0){
                        obj.setData(sdata);
                    }
                }else if(sext.toLowerCase().trim().equals("pdf")){ // pdf file type
                    sdata=docExtracSrv.pdfExtractor(file);
                    if(sdata!=null && sdata.trim().length()>0){
                        obj.setData(sdata);
                    }
                }
            }
        }catch(Exception e){
            log.error(e);
        }
    }
    
    protected void parseTopic(SWBIndexObj obj)
    {
        
    }
    
    protected void parseContent(SWBIndexObj obj) {
        SWBHttpServletRequestWrapper request = new SWBHttpServletRequestWrapper(new SWBRequest());
        request.addParameter("WBIndexer", "indexing");
        
        WebPage topic=SWBContext.getWebSite(obj.getTopicMapID()).getWebPage(obj.getTopicID());
        
        String sid=obj.getId().substring(obj.getTopicMapID().length()+1);
        SWBResource res = null;
        try {
            res = SWBPortal.getResourceMgr().getResource(obj.getTopicMapID(),sid);
            try {
                //content=res.getHtml(request,response,new WBUser(),tp,new HashMap());
                SWBResponse resp=new SWBResponse(null);
                User user=new User(new SemanticObject(topic.getWebSite().getUserRepository().getSemanticObject().getModel(),User.swb_User));
                //User user=new User(topic.getMap().getDbdata().getRepository());
                if(obj.getLang()!=null) {
                    user.setLanguage(obj.getLang());
                }else {
                    user.setLanguage(topic.getWebSite().getLanguage().getId());
                }
                SWBParamRequestImp resParams = new SWBParamRequestImp(request,res.getResourceBase(),topic,user);
                resParams.setCallMethod(SWBParamRequestImp.Call_CONTENT);
                resParams.setMode(SWBParamRequestImp.Mode_INDEX);
                res.render(request,resp, resParams);
                obj.setData(resp.toString());
            }catch(Exception e) {
                log.error(e);
            }
        } catch (Exception e) {
            log.error(e);
        }
        
        if(obj.getData()!=null)parseHTML(obj);
        
    }
    
    protected void parseURL(SWBIndexObj obj) {
        
    }
    
    protected void parseData(SWBIndexObj obj) {
        
    }
    
    protected void parseUser(SWBIndexObj obj) {
        
    }
    
    protected void parseHTML(SWBIndexObj obj) {
        Reader pcont=null;
        String summ=null;
        try {
            HTMLParser parser = new HTMLParser(new StringReader(obj.getData()));
            pcont=parser.getReader();
            summ=parser.getSummary();
        }catch(Exception e){}
        if(pcont!=null) {
            try {
                int charValue = 0;
                StringBuffer sb = new StringBuffer(1024);
                while((charValue = pcont.read()) != -1) {
                    sb.append((char)charValue);
                }
                obj.setData(sb.toString());
            }catch(Exception e){log.error(e);}
        }
        if(summ!=null) {
            obj.setSummary(summ);
        }
    }
    
    public List search4Id(String id) {
        ArrayList list=new ArrayList();
        try {
            if(IndexReader.indexExists(indexPath)) {
                IndexReader reader = IndexReader.open(indexPath);
                TermDocs docs=reader.termDocs(new Term(ATT_ID, id));
                while(docs.next()) {
                    Document doc=reader.document(docs.doc());
                    list.add(new SWBLuceneIndexObj(doc));
                }
                reader.close();
            }
        }catch(Exception e){log.error(e);}
        return list;
    }
    
    
    protected SWBIndexObjList searchObj(String queryString, SWBIndexObj obj, User user, int page, int pindex)
    {
        System.out.println("searchObj:"+queryString+" "+obj+" "+user+" "+page+" "+pindex);
        SWBIndexObjList list=new SWBIndexObjList(user,page,pindex);
        if (queryString != null && queryString.length() > 0) {
            try {
                long starttime = System.currentTimeMillis();
                if(searcher==null) {
                    searcher = new IndexSearcher(indexPath);
                }
                
                String tmid=null;
                String category=null;
                
                if(obj!=null) {
                    tmid=obj.getTopicMapID();
                    category=obj.getCategory();
                    
                    //repace characters for lucene
                    category=replaceUnder(category);
                }
                
                //System.out.println("tmid:"+tmid);
                //System.out.println("category:"+category);                
                
                //LocaleAnalyzer analyzer = new LocaleAnalyzer();
                
                BooleanQuery query = new BooleanQuery();
                //query.add(new TermQuery(new Term(ATT_TOPICMAP, queryString)), BooleanClause.Occur.SHOULD);
                query.add(new QueryParser(ATT_TITLE, analyzer).parse(queryString), BooleanClause.Occur.SHOULD);
                query.add(new QueryParser(ATT_DATA, analyzer).parse(queryString), BooleanClause.Occur.SHOULD);
                
                Sort sort=new Sort(new SortField[]{SortField.FIELD_SCORE,new SortField(ATT_CATEGORY)});
                
                Hits hits = null;
                if (tmid != null) {
                    if (category != null) {
                        BooleanQuery query2 = new BooleanQuery();
                        query2.add(new TermQuery(new Term(ATT_TOPICMAP, tmid)), BooleanClause.Occur.MUST);
                        query2.add(new QueryParser(ATT_INT_CATEGORY, analyzer).parse(category), BooleanClause.Occur.MUST);
                        hits = searcher.search(query, new QueryFilter(query2),sort);
                    }
                    else {
                        hits = searcher.search(query, new QueryFilter(new TermQuery(new Term(ATT_TOPICMAP, tmid))),sort);
                    }
                }
                else {
                    hits = searcher.search(query,sort);
                }
                
                long endtime = System.currentTimeMillis();
                
                //System.out.println("time:" + (endtime - starttime) / 1000F);
                //System.out.println("words:"+queryString);
                //System.out.println("hits:"+hits.length());
                
                list=new SWBLuceneIndexObjList(user,hits,page,pindex);
                
                //searcher.close();
                //System.out.println("time:" + (System.currentTimeMillis()-endtime) / 1000F);
            }catch(Exception e){log.error(e);}
        }
        return list;
    }
    
    public void remove() {
        close_writer();
        close_searcher();
        //borrar
    }
    
    public void reset() {
        createIndex();
    }
    
    public void unLock() throws IOException {
        close_writer();
        close_searcher();
        IndexReader.unlock(org.apache.lucene.store.FSDirectory.getDirectory(indexPath, true));
    }
    
    public boolean isLocked() throws IOException {
        return IndexReader.isLocked(indexPath);
    }
    
    protected void removeObj(SWBIndexObj obj) {
        //System.out.println("removeObj:"+obj);
        close_writer();
        close_searcher();
        try {
            if(IndexReader.indexExists(indexPath)) 
            {
                IndexReader reader = IndexReader.open(indexPath);
                
                if(obj.TYPE_TOPIC.equals(obj.getType()) && obj.getTopicID()!=null)
                {
                    TermDocs docs = reader.termDocs(new Term(ATT_TOPIC, obj.getTopicID()));
                    while (docs.next())
                    {
                        int d = docs.doc();
                        //System.out.println("Document:"+d);
                        if (!reader.isDeleted(d))
                        {
                            Document doc = reader.document(d);
                            SWBIndexObj obj2=new SWBLuceneIndexObj(doc);
                            //System.out.println("obj2:"+obj2);
                            if(obj.getTopicMapID()!=null && obj.getTopicMapID().equals(obj2.getTopicMapID()))
                            {
                                //System.out.println("borrar topic:"+obj.getTopicMapID()+" "+obj.getTopicID());
                                reader.deleteDocument(d);
                            }
                        }
                    }
                }else if(obj.TYPE_CONTENT.equals(obj.getType()) && obj.getResId()!=null)
                {
                    TermDocs docs = reader.termDocs(new Term(ATT_RESID, obj.getResId()));
                    while (docs.next())
                    {
                        int d = docs.doc();
                        //System.out.println("Document:"+d);
                        if (!reader.isDeleted(d))
                        {
                            Document doc = reader.document(d);
                            SWBIndexObj obj2=new SWBLuceneIndexObj(doc);
                            if(obj.getResId()!=null 
                            && obj.getResId().equals(obj2.getResId())
                            && obj.getTopicMapID()!=null 
                            && obj.getTopicMapID().equals(obj2.getTopicMapID()))
                            {
                                reader.deleteDocument(d);
                            }
                        }
                    }
                }else {
                    reader.deleteDocuments(new Term(ATT_ID, obj.getId()));
                }
                reader.close();
            }
        }catch(Exception e){log.error(e);}
    }
    
    protected void removeAll(String topicmapid) {
        close_writer();
        close_searcher();
        try {
            if(IndexReader.indexExists(indexPath)) {
                IndexReader reader = IndexReader.open(indexPath);
                reader.deleteDocuments(new Term(ATT_TOPICMAP, topicmapid));
                reader.close();
            }
        }catch(Exception e){log.error(e);}
    }
    
    public void optimize() {
        if(writer==null) {
            try {
                writer = new IndexWriter(indexPath, analyzer, false);
                writer.optimize();
            }catch(Exception e){log.error(e);}
            finally {
                if(writer!=null) {
                    try {
                        writer.close();
                    }catch(Exception e){log.error(e);}
                }
                writer=null;
            }
        }
    }
    
}

