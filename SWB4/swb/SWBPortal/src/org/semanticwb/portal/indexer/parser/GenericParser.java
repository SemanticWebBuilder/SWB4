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

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.semanticwb.model.Descriptiveable;
import org.semanticwb.model.Searchable;
import org.semanticwb.model.Tagable;
import org.semanticwb.model.User;
import org.semanticwb.model.WebPage;
import org.semanticwb.platform.SemanticClass;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticProperty;
import org.semanticwb.portal.indexer.IndexTerm;
import org.semanticwb.portal.indexer.SWBIndexer;

/**
 * Generic parser for {@link Searchable} objects. A parser extracts all the
 * needed information from a {@link Searchable} object for indexing purposes.
 * The information is retrieved as a map of {@link IndexTerm}s.
 *
 * @author javier.solis
 */
public class GenericParser
{
    /**
     * Checks wheter a {@link Searchable} object can be indexed.
     *
     * @param   gen {@link Searchable} object.
     * @return  {@code true} if the {@link Searchable} object can be indexed,
     *          {@code false} otherwise.
     */
    public boolean canIndex(Searchable gen) {
        return gen.isValid();
    }

    /**
     * Checks wheter a {@link User} has access to the {@link Searchable} object .
     *
     * @param   gen {@link Searchable} object.
     * @param   user {@link User} to check access for.
     * @return  {@code true} if the user has access to the given
     *          {@link Searchable} object, {@code false} otherwise.
     */
    public boolean canUserView(Searchable gen, User user) {
        return  gen.isValid() && user.haveAccess(gen);
    }

    /**
     * Gets the map of {@link IndexTerm}s for a {@link Searchable} object.
     * Subclasses must override this method to add extra information to the
     * index.
     *
     * @param   gen {@link Searchable} object to extract index information from.
     * @return  Map of {@link IndexTerm}s for the {@link Searchable} object.
     */
    public Map<String,IndexTerm> getIndexTerms(Searchable gen) {
        HashMap<String,IndexTerm> map=new HashMap();

        map.put(SWBIndexer.ATT_URI, new IndexTerm(SWBIndexer.ATT_URI, gen.getURI(), true, IndexTerm.INDEXED_NO_ANALYZED));
        map.put(SWBIndexer.ATT_CLASS, new IndexTerm(SWBIndexer.ATT_CLASS, getIndexClass(gen), false, IndexTerm.INDEXED_TOKENIZED_NO_ANALYZED));
        map.put(SWBIndexer.ATT_CATEGORY, new IndexTerm(SWBIndexer.ATT_CATEGORY, getIndexCategory(gen), false, IndexTerm.INDEXED_TOKENIZED_NO_ANALYZED));
        map.put(SWBIndexer.ATT_MODEL, new IndexTerm(SWBIndexer.ATT_MODEL, getIndexModel(gen), false, IndexTerm.INDEXED_ANALYZED));
        map.put(SWBIndexer.ATT_TITLE, new IndexTerm(SWBIndexer.ATT_TITLE, getIndexTitle(gen), false, IndexTerm.INDEXED_ANALYZED));
        map.put(SWBIndexer.ATT_DESCRIPTION, new IndexTerm(SWBIndexer.ATT_DESCRIPTION, getIndexDescription(gen), false, IndexTerm.INDEXED_ANALYZED));
        map.put(SWBIndexer.ATT_TAGS, new IndexTerm(SWBIndexer.ATT_TAGS, getIndexTags(gen), false, IndexTerm.INDEXED_ANALYZED));
        return map;
    }

    /**
     * Gets the model of a {@link Searchable} object.
     *
     * @param   gen {@link Searchable} object to get model from.
     * @return  ID of the model to which the given {@link Searchable} object
     *          belongs.
     */
    public String getIndexModel(Searchable gen) {
        SemanticObject sobj = gen.getSemanticObject();
        return sobj.getModel().getName();
    }

    /**
     * Gets the title or display name of a {@link Searchable} object.
     *
     * @param   gen {@link Searchable} object.
     * @return  Title or display name of the {@link Searchable} object.
     */
    public String getIndexTitle(Searchable gen) {
        String ret = "";
        if (gen != null) {
            SemanticObject sobj = gen.getSemanticObject();
            SemanticProperty prop = sobj.getSemanticClass().getDisplayNameProperty();
            if (prop != null) {
                ret = sobj.getPropertyIndexData(prop);
            }
        }
        return ret;
    }

    /**
     * Gets the description of a {@link Searchable} object.
     *
     * @param   gen {@link Searchable} object.
     * @return  Description of the {@link Searchable} object.
     */
    public String getIndexDescription(Searchable gen) {
        String ret = "";
        if (gen != null) {
            SemanticObject sobj = gen.getSemanticObject();
            if (gen instanceof Descriptiveable) {
                SemanticProperty prop = Descriptiveable.swb_description;
                if (prop != null) {
                    ret = sobj.getPropertyIndexData(prop);
                }
            }
        }
        return ret;
    }

    /**
     * Gets the tags of a {@link Searchable} object (in case it is a subclass
     * of {@link Taggable}).
     *
     * @param   gen {@link Searchable} object.
     * @return  Tags of the {@link Searchable} object.
     */
    public String getIndexTags(Searchable gen) {
        String ret = "";
        if (gen != null) {
            SemanticObject sobj = gen.getSemanticObject();
            if (gen instanceof Tagable) {
                SemanticProperty prop = Tagable.swb_tags;
                if (prop != null) {
                    ret = sobj.getPropertyIndexData(prop);
                }
            }
        }
        return ret;
    }

    /**
     * Gets the render data of a {@link Searchable} object. {@link ResourceParser}
     * overrides this method to extract the resource's rendered information.
     *
     * @param   gen {@link Searchable} object.
     * @return  Rendered data of the {@link Searchable} object.
     */
    public String getIndexData(Searchable gen) {
        return "";
    }

    /**
     * Gets the class names (as in the ontology model) of all the superclasses
     * (preceded by the ontology prefix and separated by a space) of the given
     * {@link Searchable} object, including the name of the searchable's model
     * class. This names allows the searcher to filter {@link Searchable}
     * objects by a given class name.
     *
     * @param   gen {@link Searchable} object.
     * @return  Name of all {@link Searchable} object's superclases (including
     *          the name of the searchable's class).
     */
    public String getIndexClass(Searchable gen) {
        String ret = "";
        SemanticClass cls = gen.getSemanticObject().getSemanticClass();
        if (cls != null) {
            ret += cls.getPrefix() + cls.getClassName();
        }
        Iterator<SemanticClass> it = cls.listSuperClasses();
        while (it.hasNext()) {
            SemanticClass semanticClass = it.next();
            ret += " " + semanticClass.getPrefix() + semanticClass.getClassName();
        }
        return ret;
    }

    /**
     * Recursively gets the names of all searchable {@link WebPage} objects hierarchically
     * related to the {@link Searchable} object's {@link WebPage}. In other words,
     * gets a space-separated string with the navigation path to the given
     * {@link Searchable} object.
     *
     * @param   gen {@link Searchable} object.
     * @return  Navigation path to the given {@link Searchable} object.
     */
    private String _getIndexCategory(Searchable gen) {
        String ret = gen.getId();
        SemanticObject obj = gen.getSemanticObject();
        Iterator<SemanticObject> it = obj.listHerarquicalParents();
        if (it.hasNext()) {
            SemanticObject parent = it.next();
            if (parent.instanceOf(Searchable.swb_Searchable)) {
                ret = _getIndexCategory((Searchable) parent.createGenericInstance()) + " " + ret;
            }
        }
        return ret;
    }

    /**
     * Calls the recursive method to get the names of the searchable
     * {@link WebPage} objects hierarchically related to the {@link Searchable}
     * object's {@link WebPage}.
     *
     * @param   gen {@link Searchable} object.
     * @return  Navigation path to the given {@link Searchable} object.
     */
    public String getIndexCategory(Searchable gen) {
        return _getIndexCategory(gen);
    }

    //************************ No Indexado ***********************************

    /**
     * Gets the title or display name of the {@link Searchable} object for
     * the search result snippet.
     * 
     * @param   gen {@link Searchable} object.
     * @param   lang Language to display title in.
     * @return  Title or display name of the {@link Searchable} object in the 
     *          given language.
     */
    public String getTitle(Searchable gen, String lang) {
        return gen.getSemanticObject().getDisplayName(lang);
    }

    /**
     * Gets the navigation path to the {@link Searchable} object for the search
     * result snippet.
     *
     * @param   gen {@link Searchable} object.
     * @param   lang lang Language to display path in.
     * @return  navigation path to the {@link Searchable} object in the given
     *          language.
     */
    public String getPath(Searchable gen, String lang) {
        String ret = null;

        if (gen instanceof WebPage) {
            HashMap arg = new HashMap();
            arg.put("separator", " | ");
            arg.put("links", "false");
            arg.put("language", lang);
            WebPage page = (WebPage) gen;
            ret = page.getPath(arg);
        }
        return ret;
    }

    /**
     * Gets the summary of the {@link Searchable} object for the search result
     * snippet.
     *
     * @param   gen {@link Searchable} object.
     * @param   lang Language to display summary in.
     * @return  Summary of the {@link Searchable} object in the given language.
     */
    public String getSummary(Searchable gen, String lang) {
        return null;
    }

    /**
     * Path to the image of the {@link Searchable} object (if any) for the
     * search result snippet.
     *
     * @param   gen {@link Searchable} object.
     * @return  Path to the image of the {@link Searchable} object if it was
     *          defined, null otherwise.
     *
     */
    public String getImage(Searchable gen) {
        return null;
    }

    /**
     * Gets the URL of the {@link WebPage} associated to the given
     * {@link Searchable} object for the search result snippet.
     *
     * @param   gen {@link Searchable} object.
     * @return  URL of the associated {@link Searchable} object's {@link WebPage}.
     */
    public String getUrl(Searchable gen) {
        String ret = null;
        if (gen instanceof WebPage) {
            WebPage page = (WebPage) gen;
            ret = page.getUrl();
        }
        return ret;
    }

    /**
     * Gets the String type of the {@link Searchable} object for the search
     * result snippet.
     * 
     * @param   gen {@link Searchable} object.
     * @return  String type of the {@link Searchable} object.
     */
    public String getType(Searchable gen) {
        return "Generic";
    }
}
