/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.bsc.parser;

import org.semanticwb.bsc.BSC;
import org.semanticwb.bsc.element.Initiative;
import org.semanticwb.model.Searchable;
import org.semanticwb.model.WebPage;
import org.semanticwb.portal.indexer.parser.GenericParser;

/**
 * Parse utilizado para la indexaci&oacute;n de elementos de iniciativas
 * @author martha.jimenez
 */
public class InitiativeParser extends GenericParser {

    /**
     * Obtiene la url para desplegar el detalle de una Iniciativa
     * 
     * @param gen Elemento de tipo {@link Initiative}
     * @return String con la url del detalle de una iniciativa
     */
    @Override
    public String getUrl(Searchable gen) {
        Initiative init = (Initiative) gen;
        BSC ws = init.getBSC();
        String path = null;
        if (ws.getWebPage(Initiative.bsc_Initiative.getName()) != null) {
            WebPage wp = ws.getWebPage(Initiative.bsc_Initiative.getName());
            path = wp.getUrl() + "?suri=" + init.getEncodedURI();
        }
        return path;
    }

    /**
     * Obtiene el tipo de elemento indexado
     * 
     * @param gen Elemento de tipo {@link Initiative}
     * @return String con el tipo de elemento
     */
    @Override
    public String getType(Searchable gen) {
        return "Initiative";
    }
}
