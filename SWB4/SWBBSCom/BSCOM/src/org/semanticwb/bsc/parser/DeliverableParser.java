package org.semanticwb.bsc.parser;

import org.semanticwb.bsc.BSC;
import org.semanticwb.bsc.element.Deliverable;
import org.semanticwb.model.Searchable;
import org.semanticwb.model.WebPage;
import org.semanticwb.portal.indexer.parser.GenericParser;


/**
 * Parse utilizado para la indexaci&oacute;n de elementos de entregables
 * 
 * @author martha.jimenez
 */
public class DeliverableParser extends GenericParser {

    /**
     * Obtiene la url para desplegar el detalle de un Entregable
     * 
     * @param gen Elemento de tipo {@link Deliverable}
     * @return String con la url del detalle de un entregable
     */
    @Override
    public String getUrl(Searchable gen) {
        Deliverable del = (Deliverable) gen;
        BSC ws = del.getBSC();
        String path = null;
        if (ws.getWebPage(Deliverable.bsc_Deliverable.getName()) != null) {
            WebPage wp = ws.getWebPage(Deliverable.bsc_Deliverable.getName());
            path = wp.getUrl() + "?suri=" + del.getEncodedURI();
        }
        return path;
    }

    /**
     * Obtiene el tipo de elemento indexado
     * 
     * @param gen Elemento de tipo {@link Deliverable}
     * @return String con el tipo de elemento
     */
    @Override
    public String getType(Searchable gen) {
        return "Deliverable";
    }
}
