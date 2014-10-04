/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.bsc.parser;

import org.semanticwb.bsc.BSC;
import org.semanticwb.bsc.element.Agreement;
import org.semanticwb.model.Searchable;
import org.semanticwb.model.WebPage;
import org.semanticwb.portal.indexer.parser.GenericParser;

/**
 * Parse utilizado para la indexaci&oacute;n de elementos de acuerdos
 * 
 * @author martha.jimenez
 */
public class AgreementParser extends GenericParser {

    /**
     * Obtiene la url para desplegar el detalle de un Acuerdo
     * 
     * @param gen Elemento de tipo {@link Agreement}
     * @return String con la url del detalle de un acuerdo
     */
    @Override
    public String getUrl(Searchable gen) {
        Agreement agree = (Agreement) gen;
        BSC ws = agree.getBSC();
        String path = null;
        if(ws.getWebPage(Agreement.bsc_Agreement.getName()) != null) {
            WebPage wp = ws.getWebPage(Agreement.bsc_Agreement.getName());
            path = wp.getUrl() + "?suri=" + agree.getEncodedURI();
        }
        return path;     
    }

    /**
     * Obtiene el tipo de elemento indexado
     * 
     * @param gen Elemento de tipo {@link Agreement}
     * @return String con el tipo de elemento
     */
    @Override
    public String getType(Searchable gen) {
        return "Agreement";
    }
    
    
}
