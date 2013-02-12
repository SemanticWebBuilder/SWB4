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
package org.semanticwb.process.utils;

import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.Map;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Role;
import org.semanticwb.model.Searchable;
import org.semanticwb.model.User;
import org.semanticwb.model.UserGroup;
import org.semanticwb.portal.indexer.IndexTerm;
import org.semanticwb.portal.indexer.parser.GenericParser;
import org.semanticwb.process.model.ItemAwareStatus;
import org.semanticwb.process.model.RepositoryDirectory;
import org.semanticwb.process.model.RepositoryFile;

/**
 * Parser para indexado y búsqueda de objetos {@link RepositoryFile}.
 * @author Hasdai Pacheco {ebenezer.sanchez@infotec.com.mx}
 */
public class RepositoryFileParser extends GenericParser {
    private static final String ATT_USRGROUP = "fileOwnerUserGroup";
    private static final String ATT_STATUS = "reStatus";
    private static Logger log = SWBUtils.getLogger(RepositoryFileParser.class);
    
    @Override
    public Map<String, IndexTerm> getIndexTerms(Searchable gen) {
       Map map=super.getIndexTerms(gen);
       try {
           map.put(ATT_USRGROUP, new IndexTerm(ATT_USRGROUP, getOwnerUserGroup((RepositoryFile)gen), false, IndexTerm.INDEXED_ANALYZED));
           map.put(ATT_STATUS, new IndexTerm(ATT_STATUS, getRepositoryFileStatus((RepositoryFile)gen), false, IndexTerm.INDEXED_ANALYZED));
       }catch(Exception e) {log.error(e);}
       return map;
    }
    
    /**
     * Obtiene el nombre del estatus del {@link RepositoryFile}.
     * @param rf {@link RepositoryFile}
     * @return Status del {@link RepositoryFile}
     */
    public String getRepositoryFileStatus(RepositoryFile rf) {
        String ret = "";
        ItemAwareStatus status = rf.getStatus();
        if (status != null) {
            String title = status.getTitle();
            if (title != null) {
                ret = title;
            }
        }
        return ret;
    }
    
    /**
     * Obtiene el nombre del grupo de usuarios propietario del {@link RepositoryFile}.
     * @param rf {@link RepositoryFile}
     * @return Grupo de usuarios propietario del {@link RepositoryFile}
     */
    public String getOwnerUserGroup(RepositoryFile rf) {
        String ret = "";
        UserGroup ug = rf.getOwnerUserGroup();
        if (ug != null) {
            String title = rf.getOwnerUserGroup().getTitle();
            if (title != null) {
                ret = title;
            }
        }
        return ret;
    }
    
    @Override
    public boolean canIndex(Searchable gen) {
        return true;
    }
    
    @Override
    public String getIndexLastUpdated(Searchable gen) {
        return ((RepositoryFile)gen).getUpdated().toString();
    }
    
    @Override
    public String getSummary(Searchable gen, String lang) {
        String ret = null;
        RepositoryFile file = (RepositoryFile) gen;
        if (file != null) {
            try {
                ret = SWBUtils.TEXT.parseHTML(file.getDisplayDescription(lang));
            } catch (Exception e) {
                log.error(e);
            }
        }
        return ret;
    }
    
    @Override
    public String getType(Searchable gen) {
        return "RepositoryFile";
    }
    
    @Override
    public String getUpdated(Searchable gen) {
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return f.format(((RepositoryFile)gen).getUpdated());
    }

    @Override
    public String getUrl(Searchable gen) {
        String ret = "";
        RepositoryFile rf = (RepositoryFile)gen;
        RepositoryDirectory rd = rf.getRepositoryDirectory();
        if (rd != null) {
            ret = rd.getUrl() + "?act=detail&fid=" + rf.getEncodedURI();
        }
        return ret;
    }

    @Override
    public String getIndexTitle(Searchable gen) {
        String ret = "";
        RepositoryFile rf = (RepositoryFile) gen;
        if (rf.getTitle() != null) {
            ret = rf.getTitle();
        }
        return ret;
    }

    @Override
    public boolean canUserView(Searchable gen, User user) {
        boolean ret = false;
        //Si el creador del archivo tiene el mismo grupo de usuarios que el usuario en sesión
        //El usuario puede ver la sección
        //TODO: Revisar condiciones de acuerdo a las reglas de negocio aplicables para SWB Process
        RepositoryFile rf = (RepositoryFile) gen;
        if (user.haveAccess(rf.getRepositoryDirectory())) {
            User creator = rf.getCreator();
            if (creator != null) {
                Iterator<Role> roles = creator.listRoles();
                if (roles != null && roles.hasNext()) {
                    while (roles.hasNext()) {
                        Role role = roles.next();
                        if (user.hasRole(role)) {
                            ret = true;
                            break;
                        }
                    }
                }
            }
        }
        return ret;
    }

//    @Override
//    public String getIndexData(Searchable gen) {
//        String ret = "";
//        RepositoryFile rf = (RepositoryFile) gen;
//        User creator = rf.getCreator();
//        if (creator != null) {
//            ret = creator.getFullName();
//        }
//        creator = rf.getActualVersion().getCreator();
//        if (creator != null) {
//            ret += " " + creator.getFullName();
//        }
//        return ret;
//    }
}
