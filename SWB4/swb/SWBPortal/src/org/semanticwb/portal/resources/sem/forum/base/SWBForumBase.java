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
package org.semanticwb.portal.resources.sem.forum.base;


// TODO: Auto-generated Javadoc
/**
 * The Class SWBForumBase.
 */
public abstract class SWBForumBase extends org.semanticwb.portal.api.GenericSemResource 
{
    
    /** The Constant frm_acceptGuessUsers. */
    public static final org.semanticwb.platform.SemanticProperty frm_acceptGuessUsers=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwb.org/SWBForum#acceptGuessUsers");
    
    /** The Constant frm_notifyThreadCreation. */
    public static final org.semanticwb.platform.SemanticProperty frm_notifyThreadCreation=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwb.org/SWBForum#notifyThreadCreation");
    
    /** The Constant swb_Role. */
    public static final org.semanticwb.platform.SemanticClass swb_Role=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Role");
    
    /** The Constant frm_adminRole. */
    public static final org.semanticwb.platform.SemanticProperty frm_adminRole=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwb.org/SWBForum#adminRole");
    
    /** The Constant frm_Thread. */
    public static final org.semanticwb.platform.SemanticClass frm_Thread=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwb.org/SWBForum#Thread");
    
    /** The Constant frm_hasThreads. */
    public static final org.semanticwb.platform.SemanticProperty frm_hasThreads=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwb.org/SWBForum#hasThreads");
    
    /** The Constant frm_SWBForum. */
    public static final org.semanticwb.platform.SemanticClass frm_SWBForum=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwb.org/SWBForum#SWBForum");
    
    /** The Constant sclass. */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwb.org/SWBForum#SWBForum");

    /**
     * Instantiates a new sWB forum base.
     */
    public SWBForumBase()
    {
    }

    /**
     * Instantiates a new sWB forum base.
     * 
     * @param base the base
     */
    public SWBForumBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    /**
     * Checks if is accept guess users.
     * 
     * @return true, if is accept guess users
     */
    public boolean isAcceptGuessUsers()
    {
        return getSemanticObject().getBooleanProperty(frm_acceptGuessUsers);
    }

    /**
     * Sets the accept guess users.
     * 
     * @param value the new accept guess users
     */
    public void setAcceptGuessUsers(boolean value)
    {
        getSemanticObject().setBooleanProperty(frm_acceptGuessUsers, value);
    }

    /**
     * Checks if is notify thread creation.
     * 
     * @return true, if is notify thread creation
     */
    public boolean isNotifyThreadCreation()
    {
        return getSemanticObject().getBooleanProperty(frm_notifyThreadCreation);
    }

    /**
     * Sets the notify thread creation.
     * 
     * @param value the new notify thread creation
     */
    public void setNotifyThreadCreation(boolean value)
    {
        getSemanticObject().setBooleanProperty(frm_notifyThreadCreation, value);
    }

    /**
     * Sets the admin role.
     * 
     * @param value the new admin role
     */
    public void setAdminRole(org.semanticwb.model.Role value)
    {
        getSemanticObject().setObjectProperty(frm_adminRole, value.getSemanticObject());
    }

    /**
     * Removes the admin role.
     */
    public void removeAdminRole()
    {
        getSemanticObject().removeProperty(frm_adminRole);
    }

    /**
     * Gets the admin role.
     * 
     * @return the admin role
     */
    public org.semanticwb.model.Role getAdminRole()
    {
         org.semanticwb.model.Role ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(frm_adminRole);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.Role)obj.createGenericInstance();
         }
         return ret;
    }

    /**
     * List threadses.
     * 
     * @return the org.semanticwb.model. generic iterator
     */
    public org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Thread> listThreadses()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Thread>(getSemanticObject().listObjectProperties(frm_hasThreads));
    }

    /**
     * Checks for threads.
     * 
     * @param thread the thread
     * @return true, if successful
     */
    public boolean hasThreads(org.semanticwb.portal.resources.sem.forum.Thread thread)
    {
        boolean ret=false;
        if(thread!=null)
        {
           ret=getSemanticObject().hasObjectProperty(frm_hasThreads,thread.getSemanticObject());
        }
        return ret;
    }

    /**
     * Gets the threads.
     * 
     * @return the threads
     */
    public org.semanticwb.portal.resources.sem.forum.Thread getThreads()
    {
         org.semanticwb.portal.resources.sem.forum.Thread ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(frm_hasThreads);
         if(obj!=null)
         {
             ret=(org.semanticwb.portal.resources.sem.forum.Thread)obj.createGenericInstance();
         }
         return ret;
    }
}
