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
 * The Class AttachmentBase.
 */
public abstract class AttachmentBase extends org.semanticwb.model.SWBClass implements org.semanticwb.model.Traceable
{
    
    /** The Constant frm_atMimeType. */
    public static final org.semanticwb.platform.SemanticProperty frm_atMimeType=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwb.org/SWBForum#atMimeType");
    
    /** The Constant frm_atFileSize. */
    public static final org.semanticwb.platform.SemanticProperty frm_atFileSize=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwb.org/SWBForum#atFileSize");
    
    /** The Constant frm_Thread. */
    public static final org.semanticwb.platform.SemanticClass frm_Thread=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwb.org/SWBForum#Thread");
    
    /** The Constant frm_atThread. */
    public static final org.semanticwb.platform.SemanticProperty frm_atThread=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwb.org/SWBForum#atThread");
    
    /** The Constant frm_atFileName. */
    public static final org.semanticwb.platform.SemanticProperty frm_atFileName=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwb.org/SWBForum#atFileName");
    
    /** The Constant frm_Post. */
    public static final org.semanticwb.platform.SemanticClass frm_Post=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwb.org/SWBForum#Post");
    
    /** The Constant frm_atPost. */
    public static final org.semanticwb.platform.SemanticProperty frm_atPost=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwb.org/SWBForum#atPost");
    
    /** The Constant frm_Attachment. */
    public static final org.semanticwb.platform.SemanticClass frm_Attachment=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwb.org/SWBForum#Attachment");
    
    /** The Constant sclass. */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwb.org/SWBForum#Attachment");

    /**
     * The Class ClassMgr.
     */
    public static class ClassMgr
    {

        /**
         * List attachments.
         * 
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.Attachment> listAttachments(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Attachment>(it, true);
        }

        /**
         * List attachments.
         * 
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.Attachment> listAttachments()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Attachment>(it, true);
        }

        /**
         * Creates the attachment.
         * 
         * @param model the model
         * @return the org.semanticwb.portal.resources.sem.forum. attachment
         */
        public static org.semanticwb.portal.resources.sem.forum.Attachment createAttachment(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.portal.resources.sem.forum.Attachment.ClassMgr.createAttachment(String.valueOf(id), model);
        }

        /**
         * Gets the attachment.
         * 
         * @param id the id
         * @param model the model
         * @return the attachment
         */
        public static org.semanticwb.portal.resources.sem.forum.Attachment getAttachment(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.portal.resources.sem.forum.Attachment)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        /**
         * Creates the attachment.
         * 
         * @param id the id
         * @param model the model
         * @return the org.semanticwb.portal.resources.sem.forum. attachment
         */
        public static org.semanticwb.portal.resources.sem.forum.Attachment createAttachment(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.portal.resources.sem.forum.Attachment)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        }

        /**
         * Removes the attachment.
         * 
         * @param id the id
         * @param model the model
         */
        public static void removeAttachment(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        /**
         * Checks for attachment.
         * 
         * @param id the id
         * @param model the model
         * @return true, if successful
         */
        public static boolean hasAttachment(String id, org.semanticwb.model.SWBModel model)
        {
            return (getAttachment(id, model)!=null);
        }

        /**
         * List attachment by modified by.
         * 
         * @param modifiedby the modifiedby
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.Attachment> listAttachmentByModifiedBy(org.semanticwb.model.User modifiedby,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Attachment> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, modifiedby.getSemanticObject(),sclass));
            return it;
        }

        /**
         * List attachment by modified by.
         * 
         * @param modifiedby the modifiedby
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.Attachment> listAttachmentByModifiedBy(org.semanticwb.model.User modifiedby)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Attachment> it=new org.semanticwb.model.GenericIterator(modifiedby.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,modifiedby.getSemanticObject(),sclass));
            return it;
        }

        /**
         * List attachment by thread.
         * 
         * @param atthread the atthread
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.Attachment> listAttachmentByThread(org.semanticwb.portal.resources.sem.forum.Thread atthread,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Attachment> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(frm_atThread, atthread.getSemanticObject(),sclass));
            return it;
        }

        /**
         * List attachment by thread.
         * 
         * @param atthread the atthread
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.Attachment> listAttachmentByThread(org.semanticwb.portal.resources.sem.forum.Thread atthread)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Attachment> it=new org.semanticwb.model.GenericIterator(atthread.getSemanticObject().getModel().listSubjectsByClass(frm_atThread,atthread.getSemanticObject(),sclass));
            return it;
        }

        /**
         * List attachment by creator.
         * 
         * @param creator the creator
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.Attachment> listAttachmentByCreator(org.semanticwb.model.User creator,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Attachment> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, creator.getSemanticObject(),sclass));
            return it;
        }

        /**
         * List attachment by creator.
         * 
         * @param creator the creator
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.Attachment> listAttachmentByCreator(org.semanticwb.model.User creator)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Attachment> it=new org.semanticwb.model.GenericIterator(creator.getSemanticObject().getModel().listSubjectsByClass(swb_creator,creator.getSemanticObject(),sclass));
            return it;
        }

        /**
         * List attachment by post.
         * 
         * @param atpost the atpost
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.Attachment> listAttachmentByPost(org.semanticwb.portal.resources.sem.forum.Post atpost,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Attachment> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(frm_atPost, atpost.getSemanticObject(),sclass));
            return it;
        }

        /**
         * List attachment by post.
         * 
         * @param atpost the atpost
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.sem.forum.Attachment> listAttachmentByPost(org.semanticwb.portal.resources.sem.forum.Post atpost)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.forum.Attachment> it=new org.semanticwb.model.GenericIterator(atpost.getSemanticObject().getModel().listSubjectsByClass(frm_atPost,atpost.getSemanticObject(),sclass));
            return it;
        }
    }

    /**
     * Instantiates a new attachment base.
     * 
     * @param base the base
     */
    public AttachmentBase(org.semanticwb.platform.SemanticObject base)
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
     * Gets the mime type.
     * 
     * @return the mime type
     */
    public String getMimeType()
    {
        return getSemanticObject().getProperty(frm_atMimeType);
    }

    /**
     * Sets the mime type.
     * 
     * @param value the new mime type
     */
    public void setMimeType(String value)
    {
        getSemanticObject().setProperty(frm_atMimeType, value);
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
     * Gets the file size.
     * 
     * @return the file size
     */
    public int getFileSize()
    {
        return getSemanticObject().getIntProperty(frm_atFileSize);
    }

    /**
     * Sets the file size.
     * 
     * @param value the new file size
     */
    public void setFileSize(int value)
    {
        getSemanticObject().setIntProperty(frm_atFileSize, value);
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
     * Sets the thread.
     * 
     * @param value the new thread
     */
    public void setThread(org.semanticwb.portal.resources.sem.forum.Thread value)
    {
        getSemanticObject().setObjectProperty(frm_atThread, value.getSemanticObject());
    }

    /**
     * Removes the thread.
     */
    public void removeThread()
    {
        getSemanticObject().removeProperty(frm_atThread);
    }

    /**
     * Gets the thread.
     * 
     * @return the thread
     */
    public org.semanticwb.portal.resources.sem.forum.Thread getThread()
    {
         org.semanticwb.portal.resources.sem.forum.Thread ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(frm_atThread);
         if(obj!=null)
         {
             ret=(org.semanticwb.portal.resources.sem.forum.Thread)obj.createGenericInstance();
         }
         return ret;
    }

    /**
     * Gets the file name.
     * 
     * @return the file name
     */
    public String getFileName()
    {
        return getSemanticObject().getProperty(frm_atFileName);
    }

    /**
     * Sets the file name.
     * 
     * @param value the new file name
     */
    public void setFileName(String value)
    {
        getSemanticObject().setProperty(frm_atFileName, value);
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
     * Sets the post.
     * 
     * @param value the new post
     */
    public void setPost(org.semanticwb.portal.resources.sem.forum.Post value)
    {
        getSemanticObject().setObjectProperty(frm_atPost, value.getSemanticObject());
    }

    /**
     * Removes the post.
     */
    public void removePost()
    {
        getSemanticObject().removeProperty(frm_atPost);
    }

    /**
     * Gets the post.
     * 
     * @return the post
     */
    public org.semanticwb.portal.resources.sem.forum.Post getPost()
    {
         org.semanticwb.portal.resources.sem.forum.Post ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(frm_atPost);
         if(obj!=null)
         {
             ret=(org.semanticwb.portal.resources.sem.forum.Post)obj.createGenericInstance();
         }
         return ret;
    }
}
