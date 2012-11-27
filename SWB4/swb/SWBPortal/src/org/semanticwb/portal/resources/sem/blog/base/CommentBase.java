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
package org.semanticwb.portal.resources.sem.blog.base;


// TODO: Auto-generated Javadoc
/**
 * The Class CommentBase.
 */
public abstract class CommentBase extends org.semanticwb.model.SWBClass 
{
    
    /** The Constant blog_comment. */
    public static final org.semanticwb.platform.SemanticProperty blog_comment=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/wb2.0/blog#comment");
    
    /** The Constant swb_User. */
    public static final org.semanticwb.platform.SemanticClass swb_User=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#User");
    
    /** The Constant blog_userComment. */
    public static final org.semanticwb.platform.SemanticProperty blog_userComment=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/wb2.0/blog#userComment");
    
    /** The Constant blog_fec_altaComment. */
    public static final org.semanticwb.platform.SemanticProperty blog_fec_altaComment=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/wb2.0/blog#fec_altaComment");
    
    /** The Constant blog_Comment. */
    public static final org.semanticwb.platform.SemanticClass blog_Comment=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/wb2.0/blog#Comment");
    
    /** The Constant sclass. */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/wb2.0/blog#Comment");

    /**
     * The Class ClassMgr.
     */
    public static class ClassMgr
    {

        /**
         * List comments.
         * 
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.sem.blog.Comment> listComments(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.blog.Comment>(it, true);
        }

        /**
         * List comments.
         * 
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.sem.blog.Comment> listComments()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.blog.Comment>(it, true);
        }

        /**
         * Creates the comment.
         * 
         * @param model the model
         * @return the org.semanticwb.portal.resources.sem.blog. comment
         */
        public static org.semanticwb.portal.resources.sem.blog.Comment createComment(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.portal.resources.sem.blog.Comment.ClassMgr.createComment(String.valueOf(id), model);
        }

        /**
         * Gets the comment.
         * 
         * @param id the id
         * @param model the model
         * @return the comment
         */
        public static org.semanticwb.portal.resources.sem.blog.Comment getComment(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.portal.resources.sem.blog.Comment)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        /**
         * Creates the comment.
         * 
         * @param id the id
         * @param model the model
         * @return the org.semanticwb.portal.resources.sem.blog. comment
         */
        public static org.semanticwb.portal.resources.sem.blog.Comment createComment(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.portal.resources.sem.blog.Comment)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        }

        /**
         * Removes the comment.
         * 
         * @param id the id
         * @param model the model
         */
        public static void removeComment(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        /**
         * Checks for comment.
         * 
         * @param id the id
         * @param model the model
         * @return true, if successful
         */
        public static boolean hasComment(String id, org.semanticwb.model.SWBModel model)
        {
            return (getComment(id, model)!=null);
        }

        /**
         * List comment by user comment.
         * 
         * @param value the value
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.sem.blog.Comment> listCommentByUserComment(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.blog.Comment> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(blog_userComment, value.getSemanticObject(),sclass));
            return it;
        }

        /**
         * List comment by user comment.
         * 
         * @param value the value
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.sem.blog.Comment> listCommentByUserComment(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.blog.Comment> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(blog_userComment,value.getSemanticObject(),sclass));
            return it;
        }
    }

    /**
     * Instantiates a new comment base.
     * 
     * @param base the base
     */
    public CommentBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    /**
     * Gets the comment.
     * 
     * @return the comment
     */
    public String getComment()
    {
        return getSemanticObject().getProperty(blog_comment);
    }

    /**
     * Sets the comment.
     * 
     * @param value the new comment
     */
    public void setComment(String value)
    {
        getSemanticObject().setProperty(blog_comment, value);
    }

    /**
     * Sets the user comment.
     * 
     * @param value the new user comment
     */
    public void setUserComment(org.semanticwb.model.User value)
    {
        getSemanticObject().setObjectProperty(blog_userComment, value.getSemanticObject());
    }

    /**
     * Removes the user comment.
     */
    public void removeUserComment()
    {
        getSemanticObject().removeProperty(blog_userComment);
    }

    /**
     * Gets the user comment.
     * 
     * @return the user comment
     */
    public org.semanticwb.model.User getUserComment()
    {
         org.semanticwb.model.User ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(blog_userComment);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.User)obj.createGenericInstance();
         }
         return ret;
    }

    /**
     * Gets the fec_alta comment.
     * 
     * @return the fec_alta comment
     */
    public java.util.Date getFec_altaComment()
    {
        return getSemanticObject().getDateProperty(blog_fec_altaComment);
    }

    /**
     * Sets the fec_alta comment.
     * 
     * @param value the new fec_alta comment
     */
    public void setFec_altaComment(java.util.Date value)
    {
        getSemanticObject().setDateProperty(blog_fec_altaComment, value);
    }
}
