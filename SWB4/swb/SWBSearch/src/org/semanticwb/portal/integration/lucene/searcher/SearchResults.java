/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.portal.integration.lucene.searcher;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.search.Hits;
import org.semanticwb.model.SWBComparator;
import org.semanticwb.platform.SemanticObject;

/**
 *
 * @author Hasdai Pacheco {haxdai@gmail.com}
 */
public class SearchResults {
    public final int SORT_NOSORT = 0;
    public final int SORT_BYASCDATE = 1;
    public final int SORT_BYDESDATE = 2;
    public final int SORT_BYTITLE = 3;
    private int lastSort = SORT_NOSORT;
    private int lastPage = 0;
    private ArrayList<SemanticObject> objHits = null;
    private Iterator<SemanticObject> sortedIt = null;
    private Iterator<SemanticObject> paged = null;
    private ArrayList<Document> docs = null;
    private String lang = null;
    
    
    public SearchResults (Hits hits, String lang) throws CorruptIndexException, IOException {
        objHits = new ArrayList<SemanticObject>();
        docs = new ArrayList<Document>();
        this.lang = lang;

        for (int i = 0; i < hits.length(); i++) {
            Document doc = hits.doc(i);
            docs.add(doc);
            if (doc.getField("uri") != null) {
                SemanticObject so = SemanticObject.createSemanticObject(doc.getField("uri").stringValue());
                if (so != null) objHits.add(so);
            }
        }
        sortedIt = objHits.iterator();
    }

    public int numDocs () {
        return docs.size();
    }

    public int numObjects () {
        return objHits.size();
    }

    public Iterator<Document> listDocuments () {
        return docs.iterator();
    }

    public Iterator<SemanticObject> listSemanticObjects () {
        return listSemanticObjects(SORT_NOSORT);
    }

    public Iterator<SemanticObject> listSemanticObjects (int sortType) {
        if (lastSort != sortType) {
            if (sortType == SORT_BYASCDATE) {
                sortedIt = SWBComparator.sortByCreated(objHits.iterator(), true);
                lastSort = SORT_BYASCDATE;
            }
            if (sortType == SORT_BYDESDATE) {
                sortedIt = SWBComparator.sortByCreated(objHits.iterator(), false);
                lastSort = SORT_BYDESDATE;
            }
            if (sortType == SORT_BYTITLE) {
                sortedIt = SWBComparator.sortByDisplayName(objHits.iterator(), lang);
                lastSort = SORT_BYTITLE;
            }
            if (sortType == SORT_NOSORT) {
                sortedIt = objHits.iterator();
                lastSort = SORT_NOSORT;
            }
        }
        return sortedIt;
    }

    public Iterator<SemanticObject> listSemanticObjects (int sortType, int page, int docsPerPage) {
        if (page == lastPage) {
            return paged;
        }

        ArrayList<SemanticObject> objs = new ArrayList<SemanticObject>();
        int offset = (page - 1) * docsPerPage;

        for (int i = 0; i < docsPerPage; i++) {
            if ((offset + i) < objHits.size()) {
                objs.add(objHits.get(offset + i));
            }
        }
        lastPage = page;
        return objs.iterator();
    }
}
