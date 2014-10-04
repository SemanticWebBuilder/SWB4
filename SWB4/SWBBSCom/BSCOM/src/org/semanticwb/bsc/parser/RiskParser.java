/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.bsc.parser;

import org.semanticwb.bsc.BSC;
import org.semanticwb.bsc.element.Risk;
import org.semanticwb.model.Searchable;
import org.semanticwb.model.WebPage;
import org.semanticwb.portal.indexer.parser.GenericParser;

/**
 * Parse utilizado para la indexaci&oacute;n de elementos de riesgos
 * 
 * @author martha.jimenez
 */
public class RiskParser extends GenericParser {

    /**
     * Obtiene la url para desplegar el detalle de un Riesgo
     * 
     * @param gen Elemento de tipo {@link Risk}
     * @return String con la url del detalle de un riesgo
     */
    @Override
    public String getUrl(Searchable gen) {
        Risk risk = (Risk) gen;
        BSC ws = risk.getBSC();
        String path = null;
        if (ws.getWebPage(Risk.bsc_Risk.getName()) != null) {
            WebPage wp = ws.getWebPage(Risk.bsc_Risk.getName());
            path = wp.getUrl() + "?suri=" + risk.getEncodedURI();
        }
        return path;
    }

    /**
     * Obtiene el tipo de elemento indexado
     * 
     * @param gen Elemento de tipo {@link Risk}
     * @return String con el tipo de elemento
     */
    @Override
    public String getType(Searchable gen) {
        return "Risk";
    }
}
