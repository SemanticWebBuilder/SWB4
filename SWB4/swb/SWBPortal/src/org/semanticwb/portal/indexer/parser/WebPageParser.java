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
package org.semanticwb.portal.indexer.parser;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Searchable;
import org.semanticwb.model.WebPage;

// TODO: Auto-generated Javadoc
/**
 * Parser for {@link Searchable} web pages.
 * <p>
 * Parser para páginas web de tipo {@link Searchable}.
 * @see org.semanticwb.portal.indexer.parser.GenericParser
 * 
 * @author javier.solis
 */
public class WebPageParser extends GenericParser {

    /** The log. */
    private static Logger log = SWBUtils.getLogger(WebPageParser.class);

    /**
     * Checks wheter a {@link Searchable} object can be indexed.
     * <p>
     * Verifica si un objeto {@link Searchable} puede ser indexado.
     *
     * @param gen   the {@link Searchable} object. El objeto {@link Searchable}.
     * @return      {@code true} if the {@link Searchable} object can be indexed,
     *              {@code false} otherwise. {@code true} si el objeto puede
     *              indexarse, {@code false} de otro modo.
     * @see         org.semanticwb.portal.indexer.parser.GenericParser#canIndex(org.semanticwb.model.Searchable)
     */
    @Override
    public boolean canIndex(Searchable gen) {
        boolean ret = gen.isValid();
        if (ret) {
            WebPage page = (WebPage) gen;
            if (page.isIndexable()) {
                ret = true;
            }
        }
        return ret;
    }

    /**
     * Gets the date of the last change done to the {@link Searchable} object.
     * <p>
     * Obtiene la fecha de la última modificación hecha al objeto
     * 
     * @param gen the gen
     * @return  Last {@link Searchable} object's update date.
     * Fecha de última actualización del objeto {@link Searchable}.
     * {@link Searchable}.
     */
    @Override
    public String getIndexLastUpdated(Searchable gen) 
    {
        Date date=((WebPage)gen).getUpdated();
        if(date!=null) return date.toString();
        else return "";
    }

    //************ Métodos que no afectan la información del índice ************

    /**
     * Gets the summary of the {@link Searchable} object.
     * <p>
     * Obtiene el resúmen del objeto {@link Searchable}.
     * 
     * @param gen the gen
     * @param lang the lang
     * @return  Summary of the {@link Searchable} object in the given language.
     * Resumen del objeto {@link Searchable} en el idioma del usuario.
     * @see     org.semanticwb.portal.indexer.parser.GenericParser#getSummary(org.semanticwb.model.Searchable, java.lang.String)
     */
    @Override
    public String getSummary(Searchable gen, String lang) {
        String ret = null;
        WebPage page = (WebPage) gen;
        if (page != null) {
            try {
                ret = SWBUtils.TEXT.parseHTML(page.getDisplayDescription(lang));
            } catch (Exception e) {
                log.error(e);
            }
        }
        return ret;
    }

    /**
     * Gets the String type of the {@link Searchable} object.
     * <p>
     * Obtiene la cadena del tipo de objeto {@link Searchable}.
     * 
     * @param gen the gen
     * @return  String type of the {@link Searchable} object. String con el tipo
     * del objeto {@link Searchable}.
     * @see     org.semanticwb.portal.indexer.parser.GenericParser#getType(org.semanticwb.model.Searchable)
     */
    @Override
    public String getType(Searchable gen) {
        return "WebPage";
    }

    /**
     * Gets the last update date of the {@link Searchable} object in format
     * yyy-MM-dd HH:mm:ss.
     * <p>
     * Obtiene la fecha de la última actualización del objeto {@link Searchable}
     * en el formato yyy-MM-dd HH:mm:ss.
     * @param gen the {@link Searchable} object.
     * @return last update date of the {@link Searchable} object.
     */
    @Override
    public String getUpdated(Searchable gen) {
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return f.format(((WebPage)gen).getUpdated());
    }
}