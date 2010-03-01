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

package org.semanticwb.portal.indexer.searcher;

import org.semanticwb.model.Searchable;
import org.semanticwb.model.GenericObject;
import org.semanticwb.platform.SemanticObject;

// TODO: Auto-generated Javadoc
/**
 * The Class SearchDocument.
 * 
 * @author javier.solis
 */
public class SearchDocument implements Comparable
{
    
    /** The m_uri. */
    private String m_uri;
    
    /** The m_summary. */
    private String m_summary;
    
    /** The m_score. */
    private float m_score;
    
    /** The m_sdoc. */
    private Searchable m_sdoc=null;

    /**
     * Instantiates a new search document.
     * 
     * @param uri the uri
     * @param summary the summary
     * @param score the score
     */
    public SearchDocument(String uri, String summary, float score)
    {
        this.m_score=score;
        this.m_uri=uri;
        this.m_summary=summary;
    }

    /**
     * Gets the searchable.
     * 
     * @return the searchable
     */
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
     * Gets the uRI.
     * 
     * @return the uri
     */
    public String getURI() {
        return m_uri;
    }

    /**
     * Sets the uRI.
     * 
     * @param uri the uri to set
     */
    public void setURI(String uri) {
        this.m_uri = uri;
    }

    /**
     * Gets the summary.
     * 
     * @return the summary
     */
    public String getSummary() {
        return m_summary;
    }

    /**
     * Sets the summary.
     * 
     * @param summary the summary to set
     */
    public void setSummary(String summary) {
        this.m_summary = summary;
    }


    /**
     * Gets the score.
     * 
     * @return the score
     */
    public float getScore() {
        return m_score;
    }

    /**
     * Sets the score.
     * 
     * @param score the score to set
     */
    public void setScore(float score) {
        this.m_score = score;
    }

    /* (non-Javadoc)
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    public int compareTo(Object o) {
        return 1;
    }
}
