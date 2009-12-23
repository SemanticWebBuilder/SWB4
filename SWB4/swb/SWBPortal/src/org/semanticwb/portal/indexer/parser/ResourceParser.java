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

import java.io.StringReader;
import java.util.Iterator;
import java.util.Map;
import org.semanticwb.Logger;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.base.util.parser.html.HTMLParser;
import org.semanticwb.model.Language;
import org.semanticwb.model.Resource;
import org.semanticwb.model.ResourceType;
import org.semanticwb.model.Resourceable;
import org.semanticwb.model.Searchable;
import org.semanticwb.model.User;
import org.semanticwb.model.WebPage;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.portal.api.SWBParamRequestImp;
import org.semanticwb.portal.api.SWBResource;
import org.semanticwb.portal.indexer.IndexTerm;
import org.semanticwb.portal.indexer.SWBIndexer;
import org.semanticwb.portal.lib.SWBRequest;
import org.semanticwb.portal.lib.SWBResponse;
import org.semanticwb.servlet.SWBHttpServletRequestWrapper;

/**
 * Parser for {@link Searchable} resources.
 * 
 * @author javier.solis
 */
public class ResourceParser extends GenericParser {

    private static Logger log = SWBUtils.getLogger(ResourceParser.class);

    @Override
    public boolean canIndex(Searchable gen) {
        boolean ret = gen.isValid();
        if (ret) {
            Resource res = (Resource) gen;
            if (res.isIndexable()) {
                if (getWebPage(res) != null) {
                    ret = true;
                }
            }
        }
        return ret;
    }

    private WebPage getWebPage(Resource res) {
        WebPage ret = null;
        if (res.getResourceType() != null && res.getResourceType().getResourceMode() == ResourceType.MODE_CONTENT) {
            Iterator<Resourceable> it = res.listResourceables();
            while (it.hasNext()) {
                Resourceable ob = it.next();
                if (ob instanceof WebPage) {
                    ret = (WebPage) ob;
                    break;
                }
            }
        }
        return ret;
    }

    @Override
    public Map<String, IndexTerm> getIndexTerms(Searchable gen) {
        Map map = super.getIndexTerms(gen);
        try {
            HTMLParser parser = new HTMLParser(new StringReader(getIndexData(gen)));
            map.put(SWBIndexer.ATT_DATA, new IndexTerm(SWBIndexer.ATT_DATA, parser.getText(), false, IndexTerm.INDEXED_ANALYZED));
            map.put(SWBIndexer.ATT_SUMMARY, new IndexTerm(SWBIndexer.ATT_SUMMARY, parser.getSummary(), true, IndexTerm.INDEXED_NO));
        } catch (Exception e) {
        }       //Error de parseo no se registra
        return map;
    }

    @Override
    public String getIndexCategory(Searchable gen) {
        String ret = "";
        WebPage page = getWebPage((Resource) gen);
        if (page != null) {
            ret = super.getIndexCategory(page);
        }
        return ret;
    }

    @Override
    public String getIndexData(Searchable gen) {
        String ret = "";
        Resource base = (Resource) gen;
        SWBHttpServletRequestWrapper request = new SWBHttpServletRequestWrapper(new SWBRequest());
        request.addParameter("WBIndexer", "indexing");

        WebPage topic = getWebPage(base);
        if (topic != null) {
            SWBResource res = null;
            try {
                res = SWBPortal.getResourceMgr().getResource(base);
                try {
                    SWBResponse resp = new SWBResponse(null);
                    User user = new User(new SemanticObject(topic.getWebSite().getUserRepository().getSemanticObject().getModel(), User.swb_User));
                    //User user=new User(topic.getMap().getDbdata().getRepository());
                    if (base.getLanguage() != null) {
                        user.setLanguage(base.getLanguage().getId());
                    } else {
                        Language lng = topic.getWebSite().getLanguage();
                        if (lng != null) {
                            user.setLanguage(lng.getId());
                        } else {
                            user.setLanguage("es");
                        }
                    }
                    SWBParamRequestImp resParams = new SWBParamRequestImp(request, res.getResourceBase(), topic, user);
                    resParams.setCallMethod(SWBParamRequestImp.Call_CONTENT);
                    resParams.setMode(SWBParamRequestImp.Mode_INDEX);
                    res.render(request, resp, resParams);
                    ret = resp.toString();
                } catch (Exception e) {
                    log.error(e);
                }
            } catch (Exception e) {
                log.error(e);
            }
        }

        return ret;
    }

    @Override
    public String getUrl(Searchable gen) {
        String ret = null;
        WebPage page = getWebPage((Resource) gen);
        if (page != null) {
            ret = super.getUrl(page);
        }
        return ret;
    }

    @Override
    public String getType(Searchable gen) {
        return "Resource";
    }

    @Override
    public String getPath(Searchable gen, String lang) {
        String ret = null;
        WebPage page = getWebPage((Resource) gen);
        if (page != null) {
            ret = super.getPath(page, lang);
        }
        return ret;
    }
}
