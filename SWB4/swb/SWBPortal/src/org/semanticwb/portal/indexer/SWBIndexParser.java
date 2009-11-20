/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.portal.indexer;

/**
 *
 * @author javier.solis
 */
public interface SWBIndexParser
{
    /**
     * Define si el elemento puede o no ser indexado por el buscador
     * @return
     */
    public boolean canSearchIndex();

    /**
     * Regresa palabras a indexar utilizadas como categoria para el buscador
     * @return
     */
    public String getSearchCategories();

    /**
     * Regresa titulo para ser indexado por el buscador
     * @return
     */
    public String getSearchTitle();

    /**
     * Regresa titulo para despliege en los resultados del buscador
     * @param lang
     * @return
     */
    public String getSearchDisplayTitle(String lang);

    /**
     * Regresa tags a ser indexados por el buscador
     * @return
     */
    public String getSearchTags();

    /**
     * Regresa URL del detalle del elemento a mostrar en los resultados del buscador
     * @return
     */
    public String getSearchURL();

    /**
     * Regresa información para ser indexada por el buscador
     * @return
     */
    public String getSearchData();

    /**
     * Regresa contenido a mostrarse en los resultados del buscador
     * @param lang
     * @return
     */
    public String getSearchDisplaySummary(String lang);

    /**
     * Regresa url de la imagen a ser mostrada en los resultados del buscador
     * puede regresar null, si no se tiene una imagen
     * @param lang
     * @return
     */
    public String getSearchDisplayImage();
}
