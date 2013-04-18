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
package org.semanticwb.portal.indexer.searcher;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import org.semanticwb.model.Searchable;
import org.semanticwb.model.GenericObject;
import org.semanticwb.model.Resource;
import org.semanticwb.model.WebPage;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.portal.indexer.FileSearchWrapper;
import org.semanticwb.portal.indexer.SWBIndexer;

// TODO: Auto-generated Javadoc
/**
 * Generic indexed document recovered by a search query. A search document contains
 * key information of the real object satisfying the search query and specifies
 * a score to measure the quality of the result.
 * <p>
 * Documento genérico indexado recuperado al ejecutar una búsqueda. El documento
 * contiene la información relevante del objeto real que satisface la búsqueda y
 * especifica una puntuación que mide la calidad del resultado.
 * 
 * @author javier.solis
 */
public class SearchDocument implements Comparable {
    
    /** The URI of the {@link Searchable} object contained in the document. */
    private String m_uri;
    
    /** The summary of the {@link Searchable} object contained in the document. */
    private String m_summary;
    
    /** The score of the {@link Searchable} object contained in the documend. */
    private float m_score;
    
    /** The {@link Searchable} object contained in the document. */
    private Searchable m_sdoc=null;
    
    //Todos los atributos de documento
    private Map map=null;

    /**
     * Instantiates a new search document.
     * <p>
     * Crea una nueva instancia de un documento de búsqueda.
     * 
     * @param uri       the URI of the {@link Searchable} object contained. La
     *                  URI del objeto {@link Searchable} contenido en el documento.
     * @param summary   the summary of the document. El resumen del documento.
     * @param score     the score of the document. La puntuación del documento.
     */
    public SearchDocument(String uri, String summary, float score, Map map) {
        this.m_score=score;
        this.m_uri=uri;
        this.m_summary=summary;
        this.map=map;
    }

    /**
     * Gets the {@link Searchable} object contained in the document.
     * <p>
     * Obtiene el objeto {@link Searchable} contenido en el documento.
     * 
     * @return the {@link Searchable} object. El objeto {@link Searchable}.
     */
    public Searchable getSearchable() {
        if(m_sdoc==null) {
            if(m_uri.startsWith("file:"))
            {
                //WebPage page=null;
                Resource resource = null;
                SemanticObject obj=SemanticObject.createSemanticObject((String)map.get("wuri"));
                if(obj!=null)
                {
                    resource=(Resource)SemanticObject.createSemanticObject((String)map.get("wuri")).createGenericInstance();
                }
                
                m_sdoc=new FileSearchWrapper(new File(m_uri.substring(5)), (String)map.get(SWBIndexer.ATT_TITLE), (String)map.get(SWBIndexer.ATT_DESCRIPTION), (String)map.get(SWBIndexer.ATT_TAGS), (String)map.get(SWBIndexer.ATT_URL), resource);
            }else
            {
                SemanticObject sobj=SemanticObject.createSemanticObject(m_uri);
                if(sobj!=null) {
                    GenericObject inst=sobj.createGenericInstance();
                    if(inst instanceof Searchable) {
                        m_sdoc=(Searchable)inst;
                    }
                }
            }
        }
        return m_sdoc;
    }

    /**
     * Gets the URI of the {@link Searchable} object contained in the document.
     * <p>
     * Obtiene el URI del objeto {@link Searchable} contenido en el documento.
     * 
     * @return  the URI of the {@link Searchable} object. La URI del objeto
     *          {@link Searchable}
     */
    public String getURI() {
        return m_uri;
    }

    /**
     * Sets the URI of the {@link Searchable} object contained in the document.
     * <p>
     * Establece la URI del objeto {@link Searchable} contenido en el documento.
     * 
     * @param uri the URI to set. La URI.
     */
    public void setURI(String uri) {
        this.m_uri = uri;
    }

    /**
     * Gets the summary of the document.
     * <p>
     * Obtiene el resumen del documento.
     * 
     * @return the summary. El resumen.
     */
    public String getSummary() {
        return m_summary;
    }

    /**
     * Sets the summary of the document.
     * <p>
     * Establece el resumen del documento.
     * 
     * @param summary the summary to set. El resumen.
     */
    public void setSummary(String summary) {
        this.m_summary = summary;
    }


    /**
     * Gets the score of the document.
     * <p>
     * Obtiene la puntuación del documento.
     * 
     * @return the score. La puntuación.
     */
    public float getScore() {
        return m_score;
    }

    /**
     * Sets the score of the document.
     * <p>
     * Establece la puntuación del documento.
     * 
     * @param score the score to set. La puntiación.
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

    public Map getMap()
    {
        return map;
    }
        
}
