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
package org.semanticwb.portal;

import java.util.Iterator;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.AdminFilter;
import org.semanticwb.model.HerarquicalNode;
import org.semanticwb.model.SWBModel;
import org.semanticwb.model.User;
import org.semanticwb.model.WebPage;
import org.semanticwb.platform.SemanticClass;
import org.semanticwb.platform.SemanticObject;

// TODO: Auto-generated Javadoc
/**
 * The Class SWBAdminFilterMgr.
 * 
 * @author javier.solis
 */
public class SWBAdminFilterMgr
{
    
    /** The log. */
    private static Logger log = SWBUtils.getLogger(SWBAdminFilterMgr.class);

    /** The Constant NO_ACCESS. */
    public static final int NO_ACCESS = 0;
    
    /** The Constant PARCIAL_ACCESS. */
    public static final int PARCIAL_ACCESS = 1;
    
    /** The Constant FULL_ACCESS. */
    public static final int FULL_ACCESS = 2;

    /**
     * Instantiates a new sWB admin filter mgr.
     */
    public SWBAdminFilterMgr()
    {

    }

    /**
     * Inits the.
     */
    public void init()
    {
        log.event("Initializing SWBAdminFilterMgr...");
    }

    /**
     * Have class action.
     * 
     * @param user the user
     * @param cls the cls
     * @param action the action
     * @return true, if successful
     */
    public boolean haveClassAction(User user, SemanticClass cls, String action)
    {
        boolean ret=false;
        Iterator<AdminFilter> it=user.listAdminFilters();
        if(!it.hasNext())ret=true;
        while (it.hasNext())
        {
            AdminFilter adminFilter = it.next();
            ret=adminFilter.haveClassAction(cls, action);
            if(ret==true)break;
        }
        return ret;
    }

    /**
     * Have access to web page.
     * 
     * @param user the user
     * @param page the page
     * @return true, if successful
     */
    public boolean haveAccessToWebPage(User user, WebPage page)
    {
        boolean ret=false;
        Iterator<AdminFilter> it=user.listAdminFilters();
        if(!it.hasNext())ret=true;
        while (it.hasNext())
        {
            AdminFilter adminFilter = it.next();
            ret=adminFilter.haveAccessToWebPage(page);
            if(ret==true)break;
        }
        return ret;
    }

    /**
     * Alguno de los hijos de este nodo tiene acceso.
     * 
     * @param user the user
     * @param obj the obj
     * @return true, if successful
     */
    public boolean haveChildAccessToSemanticObject(User user, SemanticObject obj)
    {
        boolean ret=false;
        Iterator<AdminFilter> it=user.listAdminFilters();
        if(!it.hasNext())ret=true;
        while (it.hasNext())
        {
            AdminFilter adminFilter = it.next();
            ret=adminFilter.haveChildAccessToSemanticObject(obj);
            if(ret==true)break;
        }
        return ret;
    }

    /**
     * Tiene acceso este nodo o algun padre o algun hijo.
     * 
     * @param user the user
     * @param obj the obj
     * @return true, if successful
     */
    public boolean haveTreeAccessToSemanticObject(User user, SemanticObject obj)
    {
        boolean ret=false;
        Iterator<AdminFilter> it=user.listAdminFilters();
        if(!it.hasNext())ret=true;
        while (it.hasNext())
        {
            AdminFilter adminFilter = it.next();
            ret=adminFilter.haveTreeAccessToSemanticObject(obj);
            if(ret==true)break;
        }
        return ret;
    }

    /**
     * Tiene acceso este nodo o algun padre.
     * 
     * @param user the user
     * @param obj the obj
     * @return true, if successful
     */
    public boolean haveAccessToSemanticObject(User user, SemanticObject obj)
    {
        boolean ret=false;
        Iterator<AdminFilter> it=user.listAdminFilters();
        if(!it.hasNext())ret=true;
        while (it.hasNext())
        {
            AdminFilter adminFilter = it.next();
            ret=adminFilter.haveAccessToSemanticObject(obj);
            if(ret==true)break;
        }
        return ret;
    }


    /**
     * Have access to herarquical node.
     * 
     * @param user the user
     * @param modelUri the model uri
     * @param obj the obj
     * @return true, if successful
     */
    public boolean haveAccessToHerarquicalNode(User user, String modelUri, HerarquicalNode obj)
    {
        boolean ret=false;
        Iterator<AdminFilter> it=user.listAdminFilters();
        if(!it.hasNext())ret=true;
        while (it.hasNext())
        {
            AdminFilter adminFilter = it.next();
            ret=adminFilter.haveAccessToHerarquicalNode(modelUri,obj);
            if(ret==true)break;
        }
        return ret;
    }


}
