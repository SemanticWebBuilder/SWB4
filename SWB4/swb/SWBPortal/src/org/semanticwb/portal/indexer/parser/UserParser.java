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
import java.util.Map;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Searchable;
import org.semanticwb.model.User;
import org.semanticwb.portal.indexer.IndexTerm;

// TODO: Auto-generated Javadoc
/**
 * Parser for {@link Searchable} users.
 * <p>
 * Parser de indexación para usuarios de tipo {@link Searchable}.
 * @see org.semanticwb.portal.indexer.parser.GenericParser
 * 
 * @author Hasdai pacheco {haxdai@gmail.com}
 */
public class UserParser extends GenericParser {
    
    /** The log. */
    private static Logger log = SWBUtils.getLogger(UserParser.class);

    /** Constant for the user's name. */
    public static final String ATT_NAME = "name";
    
    /** Constant for the user's e-mail. */
    public static final String ATT_EMAIL = "email";

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
       Map map=super.getIndexTerms(gen);
       try {
           map.put(ATT_NAME, new IndexTerm(ATT_NAME, getName((User)gen), false, IndexTerm.INDEXED_ANALYZED));
           map.put(ATT_EMAIL, new IndexTerm(ATT_EMAIL, getEmail((User)gen), false, IndexTerm.INDEXED_ANALYZED));
       }catch(Exception e) {log.error(e);}

       return map;
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
        return ((User)gen).getUpdated().toString();
    }

    //************ Métodos que no afectan la información del índice ************

    /**
     * Gets the name of the {@link User}.
     * <p>
     * Obtiene el nombre del usuario ({@link User}).
     * 
     * @param user the user
     * @return  the name of the {@link User}. Nombre del usuario.
     */
    public String getName(User user) {
        return user.getFullName();
    }

    /**
     * Gets the e-mail of the {@link User}.
     * <p>
     * Obtiene el e-mail del usuario.
     * 
     * @param user the user
     * @return  the e-mail of the {@link User}. E-mail del usuario.
     */
    public String getEmail (User user) {
        return user.getEmail();
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
        return f.format(((User)gen).getUpdated());
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
        return "User";
    }
}