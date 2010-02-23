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
 */
package org.semanticwb.model;

//~--- non-JDK imports --------------------------------------------------------
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.base.SWBContextBase;
import org.semanticwb.platform.SemanticClass;
import org.semanticwb.platform.SemanticModel;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticVocabulary;
import org.semanticwb.platform.SessionUser;

//~--- JDK imports ------------------------------------------------------------

import java.security.Principal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

public class SWBContext extends SWBContextBase {

    public static String USERREPOSITORY_ADMIN = "uradm";
    public static String USERREPOSITORY_DEFAULT = "urswb";
    public static String WEBSITE_ADMIN = "SWBAdmin";
    public static String WEBSITE_GLOBAL = "SWBGlobal";
    public static String WEBSITE_ONTEDITOR = "SWBOntEdit";
    private static SWBContext instance = null;
    private static Logger log = SWBUtils.getLogger(SWBContext.class);
    private static HashMap<String, SessionUser> m_sessions = new HashMap();
    private static ArrayList<String> filtered = new ArrayList();

    static {
        log.event("Initializing SemanticWebBuilder Context...");
        filtered.add(WEBSITE_ADMIN);
        filtered.add(WEBSITE_ONTEDITOR);
        filtered.add(WEBSITE_GLOBAL);
    }

    private SWBContext() {
    }

    static public synchronized SWBContext createInstance() {
        if (instance == null) {
            instance = new SWBContext();
        }

        return instance;
    }

    public static WebSite getAdminWebSite() {
        return getWebSite(WEBSITE_ADMIN);
    }

    public static WebSite getOntEditor() {
        return getWebSite(WEBSITE_ONTEDITOR);
    }

    public static WebSite getGlobalWebSite() {
        return getWebSite(WEBSITE_GLOBAL);
    }

    public static UserRepository getDefaultRepository() {
        return getUserRepository(USERREPOSITORY_DEFAULT);
    }

    public static UserRepository getAdminRepository() {
        return getUserRepository(USERREPOSITORY_ADMIN);
    }

    public static FormView getFormView(String id) {
        FormView view = null;

        if (id != null) {
            SemanticObject obj = SemanticObject.createSemanticObject(SemanticVocabulary.SWBXF_URI + id);

            if (obj != null) {
                view = (FormView) obj.createGenericInstance();
            }

            // System.out.println("id:"+id+" obj:"+obj);
        }

        return view;
    }

    public static java.util.Iterator<org.semanticwb.model.WebSite> listWebSites() {
        return listWebSites(false);
    }

    public static java.util.Iterator<org.semanticwb.model.WebSite> listWebSites(boolean direct)
    {
        boolean adminShow = !SWBPlatform.getEnv("swb/adminShow",
                "false").equals("false");
        ArrayList<org.semanticwb.model.WebSite> arr = new ArrayList();
        Iterator<Entry<String, SemanticModel>> it = SWBPlatform.getSemanticMgr().getModels().iterator();

        while (it.hasNext())
        {
            Entry<String, SemanticModel> entry = it.next();
            SemanticModel model = entry.getValue();

            if ((model != null) && (model.getModelObject() != null))
            {
                GenericObject gen = model.getModelObject().createGenericInstance();

                // System.out.println("gen:"+gen+" "+adminShow);
                if (gen instanceof WebSite)
                {
                    if (adminShow)
                    {
                        arr.add((WebSite) gen);
                    } else if (!filtered.contains(gen.getId()))
                    {
                        arr.add((WebSite) gen);
                    }
                }
            }
        }
        return arr.iterator();
    }

    public static void setSessionUser(User user) {
        if (user != null) {
            SessionUser sess = m_sessions.get(Thread.currentThread().getName());

            if (sess == null) {
                m_sessions.put(Thread.currentThread().getName(),
                        new SessionUser(user, user.getUserRepository().getId()));
            } else {
                sess.setUser(user, user.getUserRepository().getId());
            }
        }
    }

    public static User getSessionUser() {
        return getSessionUser(null);
    }

    /**
     * Regresa usuario Administrador si esta firmado y tiene permisos de administracion, de lo contrario regresa null
     * @return
     */
    public static User getAdminUser() 
    {
        User ret = null;
        User user = null;
        SessionUser sess = m_sessions.get(Thread.currentThread().getName());

        if (sess != null) {
            user = (User) sess.getUser(SWBContext.USERREPOSITORY_ADMIN);
        }

        if ((user != null) && user.isSigned()) {
            UserRepository rep = SWBContext.getAdminRepository();
            UserGroup admin = UserGroup.ClassMgr.getUserGroup("admin", rep);

            if (user.hasUserGroup(admin)) {
                ret=user;
            }
        }

        return ret;
    }

    public static User getSessionUser(String usrrep) {
        Principal user = null;
        SessionUser sess = m_sessions.get(Thread.currentThread().getName());

        if (sess != null) {
            user = sess.getUser(usrrep);
        }

        return (User) user;
    }

    public static long getSessionUserID() {
        long ret = 0;
        //Principal user = null;
        SessionUser sess = m_sessions.get(Thread.currentThread().getName());

        if (sess != null) {
            ret = sess.geRequestID();
        }

        return ret;
    }

    /**
     *
     */
    public static class UTILS {

        public static String getIconClass(SemanticObject obj) {

            // System.out.println("getIconClass:"+obj);
            String ret = null;
            SemanticClass cls = obj.getSemanticClass();

            if (cls.hasProperty(Iconable.swb_iconClass.getName())) {
                ret = obj.getProperty(Iconable.swb_iconClass);
            }

            if (ret == null) {
                SemanticObject cobj = cls.getDisplayObject();

                if (cobj != null) {
                    ret = cobj.getProperty(DisplayObject.swb_iconClass);
                }
            }

            if (ret == null) {
                ret = "swbIcon" + cls.getName();
            }

            // System.out.println("getIconClass:1");
            if (cls.hasProperty(Activeable.swb_active.getName())
                  && !obj.getBooleanProperty(Activeable.swb_active))
            {
                ret += "U";
            }else if (cls.hasProperty(SWBClass.swb_valid.getName())
                  && !obj.getBooleanProperty(SWBClass.swb_valid))
            {
                ret += "W";
            }else if (cls.hasProperty(RoleRefable.swb_hasRoleRef.getName())
                  && obj.listValidObjectProperties(RoleRefable.swb_hasRoleRef).hasNext())
            {
                    ret += "F";
            }else if (cls.hasProperty(UserGroupRefable.swb_hasUserGroupRef.getName())
                  && obj.listValidObjectProperties(UserGroupRefable.swb_hasUserGroupRef).hasNext())
            {
                    ret += "F";
            }else if (cls.hasProperty(RuleRefable.swb_hasRuleRef.getName())
                  && obj.listValidObjectProperties(RuleRefable.swb_hasRuleRef).hasNext())
            {
                    ret += "F";
            }else if (cls.hasProperty(CalendarRefable.swb_hasCalendarRef.getName())
                  && obj.listValidObjectProperties(CalendarRefable.swb_hasCalendarRef).hasNext())
            {
                    ret += "F";
            }else if (cls.hasProperty(Versionable.swb_actualVersion.getName())
                  && obj.getObjectProperty(Versionable.swb_actualVersion) == null)
            {
                    ret += "W";
            }
            return ret;
        }
    }
}
