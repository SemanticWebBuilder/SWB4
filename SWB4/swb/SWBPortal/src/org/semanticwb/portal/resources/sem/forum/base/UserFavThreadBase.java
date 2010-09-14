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
 * The Class UserFavThreadBase.
 */
public abstract class UserFavThreadBase extends org.semanticwb.model.SWBClass implements org.semanticwb.model.Traceable
{
    
    /** The Constant swb_User. */
    public static final org.semanticwb.platform.SemanticClass swb_User=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#User");
    
    /** The Constant frm_ftUser. */
    public static final org.semanticwb.platform.SemanticProperty frm_ftUser=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwb.org/SWBForum#ftUser");
    
    /** The Constant frm_Thread. */
    public static final org.semanticwb.platform.SemanticClass frm_Thread=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwb.org/SWBForum#Thread");
    
    /** The Constant frm_ftThread. */
    public static final org.semanticwb.platform.SemanticProperty frm_ftThread=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwb.org/SWBForum#ftThread");
    
    /** The Constant frm_UserFavThread. */
    public static final org.semanticwb.platform.SemanticClass frm_UserFavThread=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwb.org/SWBForum#UserFavThread");
    
    /** The Constant sclass. */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwb.org/SWBForum#UserFavThread");

    /**
     * The Class ClassMgr.
     */
    public static class ClassMgr
    {

        /**
         * List user fav threads.
         * 
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.UserFavThread> listUserFavThreads(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.UserFavThread>(it, true);
        }

        /**
         * List user fav threads.
         * 
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.UserFavThread> listUserFavThreads()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.UserFavThread>(it, true);
        }

        /**
         * Creates the user fav thread.
         * 
         * @param model the model
         * @return the org.semanticwb.portal.resources.sem.forum. user fav thread
         */
        public static org.semanticwb.portal.resources.sem.forum.UserFavThread createUserFavThread(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.portal.resources.sem.forum.UserFavThread.ClassMgr.createUserFavThread(String.valueOf(id), model);
        }

        /**
         * Gets the user fav thread.
         * 
         * @param id the id
         * @param model the model
         * @return the user fav thread
         */
        public static org.semanticwb.portal.resources.sem.forum.UserFavThread getUserFavThread(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.portal.resources.sem.forum.UserFavThread)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        /**
         * Creates the user fav thread.
         * 
         * @param id the id
         * @param model the model
         * @return the org.semanticwb.portal.resources.sem.forum. user fav thread
         */
        public static org.semanticwb.portal.resources.sem.forum.UserFavThread createUserFavThread(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.portal.resources.sem.forum.UserFavThread)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        }

        /**
         * Removes the user fav thread.
         * 
         * @param id the id
         * @param model the model
         */
        public static void removeUserFavThread(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        /**
         * Checks for user fav thread.
         * 
         * @param id the id
         * @param model the model
         * @return true, if successful
         */
        public static boolean hasUserFavThread(String id, org.semanticwb.model.SWBModel model)
        {
            return (getUserFavThread(id, model)!=null);
        }

        /**
         * List user fav thread by modified by.
         * 
         * @param modifiedby the modifiedby
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.UserFavThread> listUserFavThreadByModifiedBy(org.semanticwb.model.User modifiedby,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.UserFavThread> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, modifiedby.getSemanticObject(),sclass));
            return it;
        }

        /**
         * List user fav thread by modified by.
         * 
         * @param modifiedby the modifiedby
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.UserFavThread> listUserFavThreadByModifiedBy(org.semanticwb.model.User modifiedby)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.UserFavThread> it=new org.semanticwb.model.GenericIterator(modifiedby.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,modifiedby.getSemanticObject(),sclass));
            return it;
        }

        /**
         * List user fav thread by user.
         * 
         * @param ftuser the ftuser
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.UserFavThread> listUserFavThreadByUser(org.semanticwb.model.User ftuser,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.UserFavThread> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(frm_ftUser, ftuser.getSemanticObject(),sclass));
            return it;
        }

        /**
         * List user fav thread by user.
         * 
         * @param ftuser the ftuser
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.UserFavThread> listUserFavThreadByUser(org.semanticwb.model.User ftuser)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.UserFavThread> it=new org.semanticwb.model.GenericIterator(ftuser.getSemanticObject().getModel().listSubjectsByClass(frm_ftUser,ftuser.getSemanticObject(),sclass));
            return it;
        }

        /**
         * List user fav thread by creator.
         * 
         * @param creator the creator
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.UserFavThread> listUserFavThreadByCreator(org.semanticwb.model.User creator,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.UserFavThread> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, creator.getSemanticObject(),sclass));
            return it;
        }

        /**
         * List user fav thread by creator.
         * 
         * @param creator the creator
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.UserFavThread> listUserFavThreadByCreator(org.semanticwb.model.User creator)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.UserFavThread> it=new org.semanticwb.model.GenericIterator(creator.getSemanticObject().getModel().listSubjectsByClass(swb_creator,creator.getSemanticObject(),sclass));
            return it;
        }

        /**
         * List user fav thread by thread.
         * 
         * @param ftthread the ftthread
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.UserFavThread> listUserFavThreadByThread(org.semanticwb.portal.resources.sem.forum.Thread ftthread,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.UserFavThread> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(frm_ftThread, ftthread.getSemanticObject(),sclass));
            return it;
        }

        /**
         * List user fav thread by thread.
         * 
         * @param ftthread the ftthread
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.UserFavThread> listUserFavThreadByThread(org.semanticwb.portal.resources.sem.forum.Thread ftthread)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.UserFavThread> it=new org.semanticwb.model.GenericIterator(ftthread.getSemanticObject().getModel().listSubjectsByClass(frm_ftThread,ftthread.getSemanticObject(),sclass));
            return it;
        }
    }

    /**
     * Instantiates a new user fav thread base.
     * 
     * @param base the base
     */
    public UserFavThreadBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    /**
     * Gets the created.
     * 
     * @return the created
     */
    public java.util.Date getCreated()
    {
        return getSemanticObject().getDateProperty(swb_created);
    }

    /**
     * Sets the created.
     * 
     * @param value the new created
     */
    public void setCreated(java.util.Date value)
    {
        getSemanticObject().setDateProperty(swb_created, value);
    }

    /**
     * Sets the modified by.
     * 
     * @param value the new modified by
     */
    public void setModifiedBy(org.semanticwb.model.User value)
    {
        getSemanticObject().setObjectProperty(swb_modifiedBy, value.getSemanticObject());
    }

    /**
     * Removes the modified by.
     */
    public void removeModifiedBy()
    {
        getSemanticObject().removeProperty(swb_modifiedBy);
    }

    /**
     * Gets the modified by.
     * 
     * @return the modified by
     */
    public org.semanticwb.model.User getModifiedBy()
    {
         org.semanticwb.model.User ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_modifiedBy);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.User)obj.createGenericInstance();
         }
         return ret;
    }

    /**
     * Sets the user.
     * 
     * @param value the new user
     */
    public void setUser(org.semanticwb.model.User value)
    {
        getSemanticObject().setObjectProperty(frm_ftUser, value.getSemanticObject());
    }

    /**
     * Removes the user.
     */
    public void removeUser()
    {
        getSemanticObject().removeProperty(frm_ftUser);
    }

    /**
     * Gets the user.
     * 
     * @return the user
     */
    public org.semanticwb.model.User getUser()
    {
         org.semanticwb.model.User ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(frm_ftUser);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.User)obj.createGenericInstance();
         }
         return ret;
    }

    /**
     * Gets the updated.
     * 
     * @return the updated
     */
    public java.util.Date getUpdated()
    {
        return getSemanticObject().getDateProperty(swb_updated);
    }

    /**
     * Sets the updated.
     * 
     * @param value the new updated
     */
    public void setUpdated(java.util.Date value)
    {
        getSemanticObject().setDateProperty(swb_updated, value);
    }

    /**
     * Sets the creator.
     * 
     * @param value the new creator
     */
    public void setCreator(org.semanticwb.model.User value)
    {
        getSemanticObject().setObjectProperty(swb_creator, value.getSemanticObject());
    }

    /**
     * Removes the creator.
     */
    public void removeCreator()
    {
        getSemanticObject().removeProperty(swb_creator);
    }

    /**
     * Gets the creator.
     * 
     * @return the creator
     */
    public org.semanticwb.model.User getCreator()
    {
         org.semanticwb.model.User ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_creator);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.User)obj.createGenericInstance();
         }
         return ret;
    }

    /**
     * Sets the thread.
     * 
     * @param value the new thread
     */
    public void setThread(org.semanticwb.portal.resources.sem.forum.Thread value)
    {
        getSemanticObject().setObjectProperty(frm_ftThread, value.getSemanticObject());
    }

    /**
     * Removes the thread.
     */
    public void removeThread()
    {
        getSemanticObject().removeProperty(frm_ftThread);
    }

    /**
     * Gets the thread.
     * 
     * @return the thread
     */
    public org.semanticwb.portal.resources.sem.forum.Thread getThread()
    {
         org.semanticwb.portal.resources.sem.forum.Thread ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(frm_ftThread);
         if(obj!=null)
         {
             ret=(org.semanticwb.portal.resources.sem.forum.Thread)obj.createGenericInstance();
         }
         return ret;
    }
}
