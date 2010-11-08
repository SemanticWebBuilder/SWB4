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
import java.text.SimpleDateFormat;
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

// TODO: Auto-generated Javadoc
/**
 * Parser for {@link Searchable} resources.
 * <p>
 * Parser para recursos de tipo {@link Searchable}.
 * @see org.semanticwb.portal.indexer.parser.GenericParser
 * 
 * @author javier.solis
 */
public class ResourceParser extends GenericParser {

    /** The log. */
    private static Logger log = SWBUtils.getLogger(ResourceParser.class);

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
            Resource res = (Resource) gen;
            if (res.isIndexable()) {
                if (getWebPage(res) != null) {
                    ret = true;
                }
            }
        }
        return ret;
    }

    /**
     * Gets a Map of {@link IndexTerm}s for a {@link Searchable} object.
     * <p>
     * Obtiene un objeto Map con los términos de indexación ({@link IndexTerm})
     * para un objeto {@link Searchable}.
     * 
     * @param gen   the {@link Searchable} object to get {@link IndexTerm}s from.
     * El objeto {@link Searchable} del cual se obtienen los
     * @return      Map of {@link IndexTerm}s for the {@link Searchable} object.
     * Objeto Map con los {@link IndexTerm}s del objeto {@link Searchable}.
     * {@link IndexTerm}s.
     * @see         org.semanticwb.portal.indexer.parser.GenericParser#getIndexTerms(org.semanticwb.model.Searchable)
     */
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

    /**
     * Gets the navigation path to the {@link Searchable} object.
     * <p>
     * Obtiene la ruta de navegación hacia el objeto {@link Searchable}.
     * 
     * @param gen the gen
     * @return  Navigation path to the given {@link Searchable} object.
     * Ruta de navegación hacia el objeto {@link Searchable}.
     * @see     org.semanticwb.portal.indexer.parser.GenericParser#getIndexCategory(org.semanticwb.model.Searchable)
     */
    @Override
    public String getIndexCategory(Searchable gen) {
        String ret = "";
        WebPage page = getWebPage((Resource) gen);
        if (page != null) {
            ret = super.getIndexCategory(page);
        }
        return ret;
    }

    /**
     * Extracts the resource's rendered information. This value is stored in the
     * 
     * @param gen   the {@link Searchable} object. El objeto {@link Searchable}.
     * @return      Rendered data of the {@link Searchable} object. Datos
     * desplegados por el objeto {@link Searchable}.
     * {@link ATT_DATA} term of the index.
     * <p>
     * Extrae la información que un recurso despliega. Este valor se almacena en
     * el término {@link ATT_DATA} del índice.
     * @see         org.semanticwb.portal.indexer.parser.GenericParser#getIndexData(org.semanticwb.model.Searchable)
     */
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
    public String getIndexLastUpdated(Searchable gen) {
        return ((Resource)gen).getUpdated().toString();
    }

//************ Métodos que no afectan la información del índice ************

    /**
 * Gets the {@link WebPage} asociated to the {@link Resource} to be indexed.
 * <p>
 * Obtiene la página web ({@link WebPage}) asociada al recurso ({@link Resource})
 * que se indexará.
 * 
 * @param res the res
 * @return  the {@link WebPage}. La página web asociada al objeto {@link Resource}.
 * {@link Searchable}). El objeto {@link Resource} (debe ser una
 * sub-clase de {@link Searchable}).
 */
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

    /**
     * Gets the URL of the {@link WebPage} associated to the given {@link Searchable}
     * object.
     * <p>
     * Obtiene la URL de la página web ({@link WebPage}) relacionada con el objeto
     * 
     * @param gen the gen
     * @return  URL of the associated {@link Searchable} object's {@link WebPage}.
     * URL del objeto {@link WebPage} asociado al objeto {@link Searchable}.
     * {@link Searchable}.
     * @see     org.semanticwb.portal.indexer.parser.GenericParser#getUrl(org.semanticwb.model.Searchable)
     */
    @Override
    public String getUrl(Searchable gen) {
        String ret = null;
        WebPage page = getWebPage((Resource) gen);
        if (page != null) {
            ret = super.getUrl(page);
        } else if (((Resource)gen).getResourceType().getResourceCollection() != null ) 
        {
            WebPage wp=((Resource)gen).getResourceType().getResourceCollection().getDisplayWebPage();
            if(wp!=null)ret =  wp.getUrl()+"?id=" + ((Resource)gen).getId();
        }
        return ret;
    }

    /**
     * Gets the navigation path to the {@link Searchable} object.
     * <p>
     * Obtiene la ruta de navegación hacia el objeto {@link Searchable}.
     * 
     * @param gen the gen
     * @param lang the lang
     * @return  Navigation path to the {@link Searchable} object in the given
     * language. Ruta de navegación hacia el objeto {@link Searchable}
     * en el idioma especificado.
     * @see     org.semanticwb.portal.indexer.parser.GenericParser#getPath(org.semanticwb.model.Searchable, java.lang.String)
     */
    @Override
    public String getPath(Searchable gen, String lang) {
        String ret = null;
        WebPage page = getWebPage((Resource) gen);
        if (page != null) {
            ret = super.getPath(page, lang);
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
        return "Resource";
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
        return f.format(((Resource)gen).getUpdated());
    }
}