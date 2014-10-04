/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.bsc.parser;

import org.semanticwb.bsc.BSC;
import org.semanticwb.bsc.element.Indicator;
import org.semanticwb.model.Searchable;
import org.semanticwb.model.WebPage;
import org.semanticwb.portal.indexer.parser.GenericParser;

/**
 * Parse utilizado para la indexaci&oacute;n de elementos de indicadores
 * 
 * @author martha.jimenez
 */
public class IndicatorParser extends GenericParser {

    /**
     * Obtiene la url para desplegar el detalle de un Indicador
     * 
     * @param gen Elemento de tipo {@link Indicator}
     * @return String con la url del detalle de un indicador
     */
    @Override
    public String getUrl(Searchable gen) {
        Indicator ind = (Indicator) gen;
        BSC ws = ind.getBSC();
        String path = null;
        if (ws.getWebPage(Indicator.bsc_Indicator.getName()) != null) {
            WebPage wp = ws.getWebPage(Indicator.bsc_Indicator.getName());
            path = wp.getUrl() + "?suri=" + ind.getEncodedURI();
        }
        return path;
    }

    /**
     * Obtiene el tipo de elemento indexado
     * 
     * @param gen Elemento de tipo {@link Indicator}
     * @return String con el tipo de elemento
     */
    @Override
    public String getType(Searchable gen) {
        return "Indicator";
    }
}
