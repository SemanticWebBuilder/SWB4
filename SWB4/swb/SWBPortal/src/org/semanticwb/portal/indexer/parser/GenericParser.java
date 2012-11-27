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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
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

// TODO: Auto-generated Javadoc
/**
 * Generic parser for {@link Searchable} objects. A parser extracts all the
 * needed information from a {@link Searchable} object for indexing purposes.
 * The information is retrieved as a map of {@link IndexTerm}s.
 * <p>
 * Parser genérico para objetos de tipo {@link Searchable}. El parser tiene la
 * función de extraer toda la información necesaria del objeto para su indexado.
 * La información se obtiene en un objeto Map de objetos {@link IndexTerm}.
 *
 * @author javier.solis
 */
public class GenericParser
{
    
    /** The log. */
    private static Logger log=SWBUtils.getLogger(GenericParser.class);
    
    /**
     * Checks wheter a {@link Searchable} object can be indexed.
     * <p>
     * Verifica si un objeto {@link Searchable} puede ser indexado.
     * 
     * @param gen   the {@link Searchable} object. El objeto {@link Searchable}.
     * @return      {@code true} if the {@link Searchable} object can be indexed,
     *              {@code false} otherwise. {@code true} si el objeto puede
     *              indexarse, {@code false} de otro modo.
     */
    public boolean canIndex(Searchable gen) {
        return gen.isValid();
    }

    /**
     * Checks wheter a {@link User} has access to the {@link Searchable} object.
     * <p>
     * Verifica si un usuario tiene acceso al objeto {@link Searchable}.
     * 
     * @param gen   the {@link Searchable} object. El objeto {@link Searchable}.
     * @param user  the {@link User} for whom acces is being checked. El usuario
     * @return      {@code true} if the user has access to the given
     * {@link User} para el cual se verifica el acceso.
     * {@link Searchable} object, {@code false} otherwise.
     * {@code true} si el usuario tiene acceso al objeto
     * {@link Searchable}, {@code false} de otro modo.
     */
    public boolean canUserView(Searchable gen, User user) {
        return  gen.isValid() && user.haveAccess(gen);
    }

    /**
     * Gets the Map of {@link IndexTerm}s for a {@link Searchable} object.
     * Subclasses must override this method to add extra information to the
     * index.
     * <p>
     * Obtiene un objeto Map con los términos de indexación ({@link IndexTerm})
     * para un objeto {@link Searchable}. Las subclases de la clase GenericParser
     * deben sobreescribir este método para agregar términos adicionales al índice.
     * 
     * @param gen   the {@link Searchable} object to get {@link IndexTerm}s from.
     * El objeto {@link Searchable} del cual se obtienen los
     * @return      Map of {@link IndexTerm}s for the {@link Searchable} object.
     * Objeto Map con los {@link IndexTerm}s del objeto.
     * {@link IndexTerm}s.
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
        map.put(SWBIndexer.ATT_UPDATED, new IndexTerm(SWBIndexer.ATT_UPDATED, getIndexLastUpdated(gen), true, IndexTerm.INDEXED_NO_ANALYZED));
        return map;
    }

    /**
     * Gets the name of the model of a {@link Searchable} object. This value is
     * stored in the {@link ATT_MODEL} term in the index and allows the search
     * engine to filter results by a given model name.
     * <p>
     * Obtiene el nombre del modelo de un objeto {@link Searchable}. Este valor
     * se almacena en el término {@link ATT_MODEL} del índice, permitiendo al motor
     * de búsqueda filtrar los resultados por nombre de modelo.
     * 
     * @param gen   the {@link Searchable} object. El objeto {@link Searchable}.
     * @return      ID of the model to which the given {@link Searchable} object
     *              belongs. ID del modelo al que pertenece el objeto
     *              {@link Searchable}.
     */
    public String getIndexModel(Searchable gen) {
        SemanticObject sobj = gen.getSemanticObject();
        return sobj.getModel().getName();
    }

    /**
     * Gets the title or {@code displayName} of a {@link Searchable} object. This
     * value is stored in the {@link ATT_TITLE} term of the index.
     * <p>
     * Obtiene el título o {@code displayName} de un objeto {@link Searchable}.
     * Este valor se almacena en el término {@link ATT_TITLE} del índice.
     * 
     * @param gen   the {@link Searchable} object. El objeto {@link Searchable}.
     * @return      Title or {@code displayName} of the {@link Searchable} object.
     *              Título o {@code displayName} del objeto {@link Searchable}.
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
     * Gets the description of a {@link Searchable} object. This value is stored
     * in the {@link ATT_DESCRIPTION} term in the index.
     * <p>
     * Obtiene la descripción de un objeto {@link Searchable}. Este valor se
     * almacena en el término {@link ATT_DESCRIPTION} del índice.
     * 
     * @param gen   the {@link Searchable} object. El objeto {@link Searchable}.
     * @return      Description of the {@link Searchable} object. Descripción
     *              del objeto {@link Searchable}.
     */
    public String getIndexDescription(Searchable gen) {
        String ret = "";
        if (gen != null) {
            SemanticObject sobj = gen.getSemanticObject();
            SemanticProperty prop = Descriptiveable.swb_description;
            if (gen instanceof Descriptiveable && prop != null) {
                ret = sobj.getPropertyIndexData(prop);
            }
        }
        return ret;
    }

    /**
     * Gets the tags of a {@link Searchable} object (in case it is a subclass
     * of {@link Taggable}). This value is stored in the {@link ATT_TAGS} term
     * in the index.
     * <p>
     * Obtiene las palabras clave de un objeto {@link Searchable} (en caso de
     * que éste sea una subclase de la clase {@link Taggable}). Este valor se
     * almacena en el término {@link ATT_TAGS} del índice.
     * 
     * @param gen   the {@link Searchable} object. El objeto {@link Searchable}.
     * @return      Tags of the {@link Searchable} object. Tags del objeto
     *              {@link Searchable}.
     */
    public String getIndexTags(Searchable gen) {
        String ret = "";
        if (gen != null) {
            SemanticObject sobj = gen.getSemanticObject();
            SemanticProperty prop = Tagable.swb_tags;
            if (gen instanceof Tagable && prop != null) {
                ret = sobj.getPropertyIndexData(prop);
            }
        }
        return ret;
    }

    /**
     * Gets the render data of a {@link Searchable} object. {@link ResourceParser}
     * overrides this method to extract the resource's rendered information. This
     * value is stored in the {@link ATT_DATA} term of the index.
     * <p>
     * Obtiene los datos de despliegue de un objeto {@link Searchable}. La clase
     * 
     * @param gen   the {@link Searchable} object. El objeto {@link Searchable}.
     * @return      Rendered data of the {@link Searchable} object. Datos
     * desplegados por el objeto {@link Searchable}.
     * {@link ResourceParser} sobreescribe este método para extraer la información
     * que un recurso despliega. Este valor se almacena en el término
     * {@link ATT_DATA} del índice.
     */
    public String getIndexData(Searchable gen) {
        return "";
    }

    /**
     * Gets the class names (as in the ontology model) of the given {@link Searchable}
     * object and all its superclasses (preceded by the ontology prefix and
     * separated by a space). This names are stored in the {@link ATT_CLASS} term
     * in the index and they enable the search engine to filter {@link Searchable}
     * objects by a given class name.
     * <p>
     * Obtiene el nombre de la clase (tal y como aparece en la ontología) del objeto
     * 
     * @param gen   the {@link Searchable} object. El objeto {@link Searchable}.
     * @return      Name of all {@link Searchable} object's superclases
     * (including the name of the searchable's class). Nombre de las
     * superclases del objeto (incluyendo el nombre de clase del mismo).
     * {@link Searchable} proporcionado y de todas sus superclases (precedidos por
     * el prefijo de la ontología y separados por espacios). Estos nombres se
     * almacenan en el término {@link ATT_CLASS} del índice y permiten al motor
     * de búsqueda filtrar objetos de tipo {@link Searchable} de acuerdo a un
     * nombre de clase dado.
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
     * Recursively gets the names of all searchable {@link WebPage} objects
     * hierarchically related to the {@link Searchable} object's {@link WebPage}.
     * In other words, gets a space-separated String with the navigation path to
     * the given {@link Searchable} object. This value is stored in the
     * 
     * @param gen   the {@link Searchable} object. El objeto {@link Searchable}.
     * @param list  Lista para validar referencia ciclicas
     * @return      Navigation path to the {@link Searchable} object. Ruta de
     * navegación hacia el objeto {@link Searchable}.
     * {@link ATT_CATEGORY} term in the index and allows the search engine to
     * filter results by category (channel).
     * <p>
     * Obtiene de manera recursiva los nombres de todos los objetos de tipo
     * {@link WebPage} relacionados de manera jerárquica con la página web del
     * objeto {@link Searchable}. en otras palabras, obtiene la ruta de
     * navegación hacia el objeto {@link Searchable}. Este valor se almacena en
     * el término {@link ATT_CATEGORY} del índice y permite al motor de búsqueda
     * filtrar resultados por categoría (canal).
     */
    private String _getIndexCategory(Searchable gen, ArrayList list) {
        String ret = gen.getId();
        list.add(gen);
        SemanticObject obj = gen.getSemanticObject();
        Iterator<SemanticObject> it = obj.listHerarquicalParents();
        if (it.hasNext()) {
            SemanticObject parent = it.next();
            if (parent.instanceOf(Searchable.swb_Searchable))
            {
                if(!list.contains(parent))
                {
                    ret = _getIndexCategory((Searchable) parent.createGenericInstance(),list) + " " + ret;
                }else
                {
                    log.error("Ciclic reference found:"+gen.getURI()+" --> "+parent.getURI());
                }
            }
        }
        return ret;
    }

    /**
     * Calls the recursive method to get the navigation path of the.
     * 
     * @param gen the gen
     * @return  Navigation path to the given {@link Searchable} object.
     * Ruta de navegación hacia el objeto {@link Searchable}.
     * {@link Searchable} object.
     * <p>
     * Llama al método recursivo para obtener la ruta de navegación hacia el
     * objeto {@link Searchable}.
     */
    public String getIndexCategory(Searchable gen) {
        return _getIndexCategory(gen, new ArrayList());
    }

    /**
     * Gets the date of the last change done to the {@link Searchable} object.
     * This method MUST be overriden in specific parsers to accurately get the
     * updated date.
     * <p>
     * Obtiene la fecha de la última modificación hecha al objeto
     * 
     * @param gen the gen
     * @return  Last {@link Searchable} object's update date.
     * Fecha de última actualización del objeto {@link Searchable}.
     * {@link Searchable}. Este método DEBE sobreescribirse en los parsers
     * genéricos para obtener de manera correcta la fecha de actualización.
     */
    public String getIndexLastUpdated(Searchable gen) {
        return "";
    }

    //************ Métodos que no afectan la información del índice ************

    /**
     * Gets the title or {@code displayName} of the {@link Searchable} object for
     * the search result snippet.
     * <p>
     * Obtiene el título o {@code displayName} del objeto {@link Searchable} para
     * mostrar el resultado de la búsqueda.
     * 
     * @param gen the gen
     * @param lang the lang
     * @return  Title or {@code displayName} of the {@link Searchable} object in
     * the given language. Título o {@code displayName} del objeto
     * {@link Searchable} en el idioma especificado.
     */
    public String getTitle(Searchable gen, String lang) {
        return gen.getSemanticObject().getDisplayName(lang);
    }

    /**
     * Gets the navigation path to the {@link Searchable} object for the search
     * result snippet.
     * <p>
     * Obtiene la ruta de navegación del objeto {@link Searchable} para mostrar
     * el resultado de la búsqueda.
     * 
     * @param gen the gen
     * @param lang the lang
     * @return  Navigation path to the {@link Searchable} object in the given
     * language. Ruta de navegación hacia el objeto {@link Searchable}
     * en el idioma especificado.
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
     * <p>
     * Obtiene el resúmen del objeto {@link Searchable} para mostrar el
     * resultado de la búsqueda.
     * 
     * @param gen the gen
     * @param lang the lang
     * @return  Summary of the {@link Searchable} object in the given language.
     * Resumen del objeto {@link Searchable} en el idioma del usuario.
     */
    public String getSummary(Searchable gen, String lang) {
        return null;
    }

    /**
     * Path to the image of the {@link Searchable} object (if any) for the
     * search result snippet.
     * <p>
     * Ruta de la imagen del objeto {@link Searchable} (si ésta existe) para
     * mostrar el resultado de la búsqueda.
     * 
     * @param gen the gen
     * @return  Path to the image of the {@link Searchable} object if it was
     * defined, null otherwise. Ruta al archivo de imagen del objeto
     * {@link Searchable} (si fue definida) o null.
     */
    public String getImage(Searchable gen) {
        return null;
    }

    /**
     * Gets the URL of the {@link WebPage} associated to the given.
     * 
     * @param gen the gen
     * @return  URL of the associated {@link Searchable} object's {@link WebPage}.
     * URL del objeto {@link WebPage} asociado al objeto {@link Searchable}.
     * {@link Searchable} object for the search result snippet.
     * <p>
     * Obtiene la URL de la página web ({@link WebPage}) relacionada con el
     * objeto {@link Searchable} para desplegar el resultado de la búsqueda.
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
     * <p>
     * Obtiene la cadena del tipo de objeto {@link Searchable} para mostrar el
     * resultado de la búsqueda.
     * 
     * @param gen the gen
     * @return  String type of the {@link Searchable} object. String con el tipo
     * del objeto {@link Searchable}.
     */
    public String getType(Searchable gen) {
        return "Generic";
    }

    /**
     * Gets the {@link Searchable} object's type label for the search result
     * snippet.
     * <p>
     * Obtiene la etiqueta de despliegue del tipo de objeto {@link Searchable}
     * para mostrar en el resultado de la búsqueda.
     * 
     * @param gen the gen
     * @return  String type label of the {@link Searchable} object. String con
     * la etiqueta para el tipo del objeto {@link Searchable}.
     */
    public String getTypeDisplayLabel(Searchable gen) {
        return "Genérico";
    }

    /**
     * Gets the last update date of the {@link Searchable} object.
     * <p>
     * Obtiene la fecha de la última actualización del objeto {@link Searchable}
     * @param gen the {@link Searchable} object.
     * @return last update date of the {@link Searchable} object.
     */
    public String getUpdated(Searchable gen) {
        return "";
    }
}
