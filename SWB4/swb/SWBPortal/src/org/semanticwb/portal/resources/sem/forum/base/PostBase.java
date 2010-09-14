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
 * The Class PostBase.
 */
public abstract class PostBase extends org.semanticwb.model.SWBClass implements org.semanticwb.model.Activeable,org.semanticwb.model.Traceable
{
    
    /** The Constant frm_Thread. */
    public static final org.semanticwb.platform.SemanticClass frm_Thread=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwb.org/SWBForum#Thread");
    
    /** The Constant frm_pstThread. */
    public static final org.semanticwb.platform.SemanticProperty frm_pstThread=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwb.org/SWBForum#pstThread");
    
    /** The Constant frm_pstBody. */
    public static final org.semanticwb.platform.SemanticProperty frm_pstBody=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwb.org/SWBForum#pstBody");
    
    /** The Constant frm_Post. */
    public static final org.semanticwb.platform.SemanticClass frm_Post=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwb.org/SWBForum#Post");
    
    /** The Constant frm_pstParentPost. */
    public static final org.semanticwb.platform.SemanticProperty frm_pstParentPost=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwb.org/SWBForum#pstParentPost");
    
    /** The Constant frm_Attachment. */
    public static final org.semanticwb.platform.SemanticClass frm_Attachment=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwb.org/SWBForum#Attachment");
    
    /** The Constant frm_hasAttachments. */
    public static final org.semanticwb.platform.SemanticProperty frm_hasAttachments=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwb.org/SWBForum#hasAttachments");
    
    /** The Constant frm_haschildPost. */
    public static final org.semanticwb.platform.SemanticProperty frm_haschildPost=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwb.org/SWBForum#haschildPost");
    
    /** The Constant sclass. */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwb.org/SWBForum#Post");

    /**
     * The Class ClassMgr.
     */
    public static class ClassMgr
    {

        /**
         * List posts.
         * 
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.Post> listPosts(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Post>(it, true);
        }

        /**
         * List posts.
         * 
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.Post> listPosts()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Post>(it, true);
        }

        /**
         * Creates the post.
         * 
         * @param model the model
         * @return the org.semanticwb.portal.resources.sem.forum. post
         */
        public static org.semanticwb.portal.resources.sem.forum.Post createPost(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.portal.resources.sem.forum.Post.ClassMgr.createPost(String.valueOf(id), model);
        }

        /**
         * Gets the post.
         * 
         * @param id the id
         * @param model the model
         * @return the post
         */
        public static org.semanticwb.portal.resources.sem.forum.Post getPost(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.portal.resources.sem.forum.Post)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        /**
         * Creates the post.
         * 
         * @param id the id
         * @param model the model
         * @return the org.semanticwb.portal.resources.sem.forum. post
         */
        public static org.semanticwb.portal.resources.sem.forum.Post createPost(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.portal.resources.sem.forum.Post)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        }

        /**
         * Removes the post.
         * 
         * @param id the id
         * @param model the model
         */
        public static void removePost(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        /**
         * Checks for post.
         * 
         * @param id the id
         * @param model the model
         * @return true, if successful
         */
        public static boolean hasPost(String id, org.semanticwb.model.SWBModel model)
        {
            return (getPost(id, model)!=null);
        }

        /**
         * List post by modified by.
         * 
         * @param modifiedby the modifiedby
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.Post> listPostByModifiedBy(org.semanticwb.model.User modifiedby,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Post> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, modifiedby.getSemanticObject(),sclass));
            return it;
        }

        /**
         * List post by modified by.
         * 
         * @param modifiedby the modifiedby
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.Post> listPostByModifiedBy(org.semanticwb.model.User modifiedby)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Post> it=new org.semanticwb.model.GenericIterator(modifiedby.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,modifiedby.getSemanticObject(),sclass));
            return it;
        }

        /**
         * List post by thread.
         * 
         * @param pstthread the pstthread
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.Post> listPostByThread(org.semanticwb.portal.resources.sem.forum.Thread pstthread,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Post> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(frm_pstThread, pstthread.getSemanticObject(),sclass));
            return it;
        }

        /**
         * List post by thread.
         * 
         * @param pstthread the pstthread
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.Post> listPostByThread(org.semanticwb.portal.resources.sem.forum.Thread pstthread)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Post> it=new org.semanticwb.model.GenericIterator(pstthread.getSemanticObject().getModel().listSubjectsByClass(frm_pstThread,pstthread.getSemanticObject(),sclass));
            return it;
        }

        /**
         * List post by parent post.
         * 
         * @param pstparentpost the pstparentpost
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.Post> listPostByParentPost(org.semanticwb.portal.resources.sem.forum.Post pstparentpost,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Post> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(frm_pstParentPost, pstparentpost.getSemanticObject(),sclass));
            return it;
        }

        /**
         * List post by parent post.
         * 
         * @param pstparentpost the pstparentpost
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.Post> listPostByParentPost(org.semanticwb.portal.resources.sem.forum.Post pstparentpost)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Post> it=new org.semanticwb.model.GenericIterator(pstparentpost.getSemanticObject().getModel().listSubjectsByClass(frm_pstParentPost,pstparentpost.getSemanticObject(),sclass));
            return it;
        }

        /**
         * List post by creator.
         * 
         * @param creator the creator
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.Post> listPostByCreator(org.semanticwb.model.User creator,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Post> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, creator.getSemanticObject(),sclass));
            return it;
        }

        /**
         * List post by creator.
         * 
         * @param creator the creator
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.Post> listPostByCreator(org.semanticwb.model.User creator)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Post> it=new org.semanticwb.model.GenericIterator(creator.getSemanticObject().getModel().listSubjectsByClass(swb_creator,creator.getSemanticObject(),sclass));
            return it;
        }

        /**
         * List post by attachments.
         * 
         * @param hasattachments the hasattachments
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.Post> listPostByAttachments(org.semanticwb.portal.resources.sem.forum.Attachment hasattachments,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Post> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(frm_hasAttachments, hasattachments.getSemanticObject(),sclass));
            return it;
        }

        /**
         * List post by attachments.
         * 
         * @param hasattachments the hasattachments
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.Post> listPostByAttachments(org.semanticwb.portal.resources.sem.forum.Attachment hasattachments)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Post> it=new org.semanticwb.model.GenericIterator(hasattachments.getSemanticObject().getModel().listSubjectsByClass(frm_hasAttachments,hasattachments.getSemanticObject(),sclass));
            return it;
        }

        /**
         * List post by child post.
         * 
         * @param haschildpost the haschildpost
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.Post> listPostByChildPost(org.semanticwb.portal.resources.sem.forum.Post haschildpost,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Post> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(frm_haschildPost, haschildpost.getSemanticObject(),sclass));
            return it;
        }

        /**
         * List post by child post.
         * 
         * @param haschildpost the haschildpost
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.Post> listPostByChildPost(org.semanticwb.portal.resources.sem.forum.Post haschildpost)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Post> it=new org.semanticwb.model.GenericIterator(haschildpost.getSemanticObject().getModel().listSubjectsByClass(frm_haschildPost,haschildpost.getSemanticObject(),sclass));
            return it;
        }
    }

    /**
     * Instantiates a new post base.
     * 
     * @param base the base
     */
    public PostBase(org.semanticwb.platform.SemanticObject base)
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
     * Sets the thread.
     * 
     * @param value the new thread
     */
    public void setThread(org.semanticwb.portal.resources.sem.forum.Thread value)
    {
        getSemanticObject().setObjectProperty(frm_pstThread, value.getSemanticObject());
    }

    /**
     * Removes the thread.
     */
    public void removeThread()
    {
        getSemanticObject().removeProperty(frm_pstThread);
    }

    /**
     * Gets the thread.
     * 
     * @return the thread
     */
    public org.semanticwb.portal.resources.sem.forum.Thread getThread()
    {
         org.semanticwb.portal.resources.sem.forum.Thread ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(frm_pstThread);
         if(obj!=null)
         {
             ret=(org.semanticwb.portal.resources.sem.forum.Thread)obj.createGenericInstance();
         }
         return ret;
    }

    /**
     * Gets the body.
     * 
     * @return the body
     */
    public String getBody()
    {
        return getSemanticObject().getProperty(frm_pstBody);
    }

    /**
     * Sets the body.
     * 
     * @param value the new body
     */
    public void setBody(String value)
    {
        getSemanticObject().setProperty(frm_pstBody, value);
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
     * Sets the parent post.
     * 
     * @param value the new parent post
     */
    public void setParentPost(org.semanticwb.portal.resources.sem.forum.Post value)
    {
        getSemanticObject().setObjectProperty(frm_pstParentPost, value.getSemanticObject());
    }

    /**
     * Removes the parent post.
     */
    public void removeParentPost()
    {
        getSemanticObject().removeProperty(frm_pstParentPost);
    }

    /**
     * Gets the parent post.
     * 
     * @return the parent post
     */
    public org.semanticwb.portal.resources.sem.forum.Post getParentPost()
    {
         org.semanticwb.portal.resources.sem.forum.Post ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(frm_pstParentPost);
         if(obj!=null)
         {
             ret=(org.semanticwb.portal.resources.sem.forum.Post)obj.createGenericInstance();
         }
         return ret;
    }

    /**
     * Checks if is active.
     * 
     * @return true, if is active
     */
    public boolean isActive()
    {
        return getSemanticObject().getBooleanProperty(swb_active);
    }

    /**
     * Sets the active.
     * 
     * @param value the new active
     */
    public void setActive(boolean value)
    {
        getSemanticObject().setBooleanProperty(swb_active, value);
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
     * List attachmentses.
     * 
     * @return the org.semanticwb.model. generic iterator
     */
    public org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Attachment> listAttachmentses()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Attachment>(getSemanticObject().listObjectProperties(frm_hasAttachments));
    }

    /**
     * Checks for attachments.
     * 
     * @param attachment the attachment
     * @return true, if successful
     */
    public boolean hasAttachments(org.semanticwb.portal.resources.sem.forum.Attachment attachment)
    {
        boolean ret=false;
        if(attachment!=null)
        {
           ret=getSemanticObject().hasObjectProperty(frm_hasAttachments,attachment.getSemanticObject());
        }
        return ret;
    }

    /**
     * Gets the attachments.
     * 
     * @return the attachments
     */
    public org.semanticwb.portal.resources.sem.forum.Attachment getAttachments()
    {
         org.semanticwb.portal.resources.sem.forum.Attachment ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(frm_hasAttachments);
         if(obj!=null)
         {
             ret=(org.semanticwb.portal.resources.sem.forum.Attachment)obj.createGenericInstance();
         }
         return ret;
    }

    /**
     * Listchild posts.
     * 
     * @return the org.semanticwb.model. generic iterator
     */
    public org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Post> listchildPosts()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Post>(getSemanticObject().listObjectProperties(frm_haschildPost));
    }

    /**
     * Haschild post.
     * 
     * @param post the post
     * @return true, if successful
     */
    public boolean haschildPost(org.semanticwb.portal.resources.sem.forum.Post post)
    {
        boolean ret=false;
        if(post!=null)
        {
           ret=getSemanticObject().hasObjectProperty(frm_haschildPost,post.getSemanticObject());
        }
        return ret;
    }

    /**
     * Gets the child post.
     * 
     * @return the child post
     */
    public org.semanticwb.portal.resources.sem.forum.Post getchildPost()
    {
         org.semanticwb.portal.resources.sem.forum.Post ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(frm_haschildPost);
         if(obj!=null)
         {
             ret=(org.semanticwb.portal.resources.sem.forum.Post)obj.createGenericInstance();
         }
         return ret;
    }
}
