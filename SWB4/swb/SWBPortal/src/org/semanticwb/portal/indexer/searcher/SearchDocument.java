/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.portal.indexer.searcher;

import org.semanticwb.model.Searchable;
import org.semanticwb.model.GenericObject;
import org.semanticwb.platform.SemanticObject;

/**
 *
 * @author javier.solis
 */
public class SearchDocument implements Comparable
{
    private String m_uri;
    private String m_summary;
    private float m_score;
    private Searchable m_sdoc=null;

    public SearchDocument(String uri, String summary, float score)
    {
        this.m_score=score;
        this.m_uri=uri;
        this.m_summary=summary;
    }

    public Searchable getSearchable()
    {
        if(m_sdoc==null)
        {
            SemanticObject sobj=SemanticObject.createSemanticObject(m_uri);
            if(sobj!=null)
            {
                GenericObject inst=sobj.createGenericInstance();
                if(inst instanceof Searchable)
                {
                    m_sdoc=(Searchable)inst;
                }
            }
        }
        return m_sdoc;
    }

    /**
     * @return the uri
     */
    public String getURI() {
        return m_uri;
    }

    /**
     * @param uri the uri to set
     */
    public void setURI(String uri) {
        this.m_uri = uri;
    }

    /**
     * @return the summary
     */
    public String getSummary() {
        return m_summary;
    }

    /**
     * @param summary the summary to set
     */
    public void setSummary(String summary) {
        this.m_summary = summary;
    }


    /**
     * @return the score
     */
    public float getScore() {
        return m_score;
    }

    /**
     * @param score the score to set
     */
    public void setScore(float score) {
        this.m_score = score;
    }

    public int compareTo(Object o) {
        return 1;
    }
}
