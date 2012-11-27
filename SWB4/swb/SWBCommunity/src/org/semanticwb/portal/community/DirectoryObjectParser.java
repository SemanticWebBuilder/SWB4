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
package org.semanticwb.portal.community;

import java.util.HashMap;
import org.semanticwb.SWBPortal;
import org.semanticwb.model.Searchable;
import org.semanticwb.model.WebPage;
import org.semanticwb.portal.indexer.parser.GenericParser;

/**
 *
 * @author Hasdai Pacheco {haxdai@gmail.com}
 */
public class DirectoryObjectParser extends GenericParser {

    @Override
    public String getType(Searchable gen) {
        return "DirectoryObject";
    }

    @Override
    public String getSummary(Searchable gen, String lang) {
        return ((DirectoryObject)gen).getDescription();
    }

    @Override
    public String getUrl(Searchable gen) {
        return ((DirectoryObject)gen).getWebPage().getUrl() + "?act=detail&uri=" +gen.getSemanticObject().getEncodedURI();
    }

    @Override
    public String getPath(Searchable gen, String lang) {
        String ret = null;

        HashMap arg = new HashMap();
        arg.put("separator", " | ");
        arg.put("links", "false");
        arg.put("language", lang);
        WebPage page=((DirectoryObject)gen).getWebPage();
        ret=page.getPath(arg);

        return ret;
    }

    @Override
    public String getIndexDescription(Searchable gen) {
        return ((DirectoryObject)gen).getDescription();
    }

    @Override
    public String getIndexTags(Searchable gen) {
        return ((DirectoryObject)gen).getTags();
    }

    @Override
    public String getImage(Searchable gen) {
        return SWBPortal.getWebWorkPath() + "/" + 
                gen.getSemanticObject().getWorkPath() + "/" +
                gen.getSemanticObject().getProperty(DirectoryObject.swbcomm_dirPhoto);
    }

    private WebPage getWebPage(Searchable gen) {
        return ((DirectoryObject)gen).getWebPage();
    }

    @Override
    public String getIndexCategory(Searchable gen)
    {
        String ret="";
        WebPage page=getWebPage(gen);
        if(page!=null)
        {
            ret=super.getIndexCategory(page);
        }
        return ret;
    }
}