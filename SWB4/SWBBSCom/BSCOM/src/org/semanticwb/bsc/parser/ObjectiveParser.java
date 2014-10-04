/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.bsc.parser;

import org.semanticwb.bsc.BSC;
import org.semanticwb.bsc.element.Objective;
import org.semanticwb.model.Searchable;
import org.semanticwb.model.WebPage;
import org.semanticwb.portal.indexer.parser.GenericParser;

/**
 * Parse utilizado para la indexaci&oacute;n de elementos de objetivos
 * 
 * @author martha.jimenez
 */
public class ObjectiveParser extends GenericParser {

    /**
     * Obtiene la url para desplegar el detalle de un Objetivo
     * 
     * @param gen Elemento de tipo {@link Objective}
     * @return String con la url del detalle de un objetivo
     */
    @Override
    public String getUrl(Searchable gen) {
        Objective obj = (Objective) gen;
        BSC ws = obj.getBSC();
        String path = null;
        if (ws.getWebPage(Objective.bsc_Objective.getName()) != null) {
            WebPage wp = ws.getWebPage(Objective.bsc_Objective.getName());
            path = wp.getUrl() + "?suri=" + obj.getEncodedURI();
        }
        return path;
    }

    /**
     * Obtiene el tipo de elemento indexado
     * 
     * @param gen Elemento de tipo {@link Objective}
     * @return String con el tipo de elemento
     */
    @Override
    public String getType(Searchable gen) {
        return "Objective";
    }

}
