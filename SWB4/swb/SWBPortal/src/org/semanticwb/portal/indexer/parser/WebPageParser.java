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

package org.semanticwb.portal.indexer.parser;

import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Searchable;
import org.semanticwb.model.WebPage;

/**
 * Parser for searchable web pages.
 * 
 * @author javier.solis
 */
public class WebPageParser extends GenericParser {

    private static Logger log = SWBUtils.getLogger(WebPageParser.class);

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

    @Override
    public String getType(Searchable gen) {
        return "WebPage";
    }
}