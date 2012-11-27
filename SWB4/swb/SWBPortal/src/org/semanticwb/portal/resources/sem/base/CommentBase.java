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
package org.semanticwb.portal.resources.sem.base;


// TODO: Auto-generated Javadoc
/**
 * The Class CommentBase.
 */
public class CommentBase extends org.semanticwb.model.SWBClass implements org.semanticwb.model.Traceable
{
    
    /** The Constant swb_created. */
    public static final org.semanticwb.platform.SemanticProperty swb_created=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#created");
    
    /** The Constant swb_User. */
    public static final org.semanticwb.platform.SemanticClass swb_User=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#User");
    
    /** The Constant swb_modifiedBy. */
    public static final org.semanticwb.platform.SemanticProperty swb_modifiedBy=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#modifiedBy");
    
    /** The Constant swb_res_cmts_comment. */
    public static final org.semanticwb.platform.SemanticProperty swb_res_cmts_comment=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/portal/resources/sems/SWBComments#comment");
    
    /** The Constant swb_updated. */
    public static final org.semanticwb.platform.SemanticProperty swb_updated=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#updated");
    
    /** The Constant swb_res_cmts_wsid. */
    public static final org.semanticwb.platform.SemanticProperty swb_res_cmts_wsid=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/portal/resources/sems/SWBComments#wsid");
    
    /** The Constant swb_res_cmts_wpid. */
    public static final org.semanticwb.platform.SemanticProperty swb_res_cmts_wpid=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/portal/resources/sems/SWBComments#wpid");
    
    /** The Constant swb_creator. */
    public static final org.semanticwb.platform.SemanticProperty swb_creator=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#creator");
    
    /** The Constant swb_res_cmts_Comment. */
    public static final org.semanticwb.platform.SemanticClass swb_res_cmts_Comment=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/portal/resources/sems/SWBComments#Comment");
    
    /** The Constant sclass. */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/portal/resources/sems/SWBComments#Comment");

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
     * List comments.
     * 
     * @param model the model
     * @return the java.util. iterator
     */
    public static java.util.Iterator<org.semanticwb.portal.resources.sem.Comment> listComments(org.semanticwb.model.SWBModel model)
    {
        java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
        return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.Comment>(it, true);
    }

    /**
     * List comments.
     * 
     * @return the java.util. iterator
     */
    public static java.util.Iterator<org.semanticwb.portal.resources.sem.Comment> listComments()
    {
        java.util.Iterator it=sclass.listInstances();
        return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.Comment>(it, true);
    }

    /**
     * Creates the comment.
     * 
     * @param model the model
     * @return the org.semanticwb.portal.resources.sem. comment
     */
    public static org.semanticwb.portal.resources.sem.Comment createComment(org.semanticwb.model.SWBModel model)
    {
        long id=model.getSemanticObject().getModel().getCounter(sclass);
        return org.semanticwb.portal.resources.sem.Comment.createComment(String.valueOf(id), model);
    }

    /**
     * Gets the comment.
     * 
     * @param id the id
     * @param model the model
     * @return the comment
     */
    public static org.semanticwb.portal.resources.sem.Comment getComment(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.portal.resources.sem.Comment)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
    }

    /**
     * Creates the comment.
     * 
     * @param id the id
     * @param model the model
     * @return the org.semanticwb.portal.resources.sem. comment
     */
    public static org.semanticwb.portal.resources.sem.Comment createComment(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.portal.resources.sem.Comment)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
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

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.TraceableBase#getCreated()
     */
    /**
     * Gets the created.
     * 
     * @return the created
     */
    public java.util.Date getCreated()
    {
        return getSemanticObject().getDateProperty(swb_created);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.TraceableBase#setCreated(java.util.Date)
     */
    /**
     * Sets the created.
     * 
     * @param created the new created
     */
    public void setCreated(java.util.Date created)
    {
        getSemanticObject().setDateProperty(swb_created, created);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.TraceableBase#setModifiedBy(org.semanticwb.model.User)
     */
    /**
     * Sets the modified by.
     * 
     * @param user the new modified by
     */
    public void setModifiedBy(org.semanticwb.model.User user)
    {
        getSemanticObject().setObjectProperty(swb_modifiedBy, user.getSemanticObject());
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.TraceableBase#removeModifiedBy()
     */
    /**
     * Removes the modified by.
     */
    public void removeModifiedBy()
    {
        getSemanticObject().removeProperty(swb_modifiedBy);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.TraceableBase#getModifiedBy()
     */
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
     * Gets the comment.
     * 
     * @return the comment
     */
    public String getComment()
    {
        return getSemanticObject().getProperty(swb_res_cmts_comment);
    }

    /**
     * Sets the comment.
     * 
     * @param comment the new comment
     */
    public void setComment(String comment)
    {
        getSemanticObject().setProperty(swb_res_cmts_comment, comment);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.TraceableBase#getUpdated()
     */
    /**
     * Gets the updated.
     * 
     * @return the updated
     */
    public java.util.Date getUpdated()
    {
        return getSemanticObject().getDateProperty(swb_updated);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.TraceableBase#setUpdated(java.util.Date)
     */
    /**
     * Sets the updated.
     * 
     * @param updated the new updated
     */
    public void setUpdated(java.util.Date updated)
    {
        getSemanticObject().setDateProperty(swb_updated, updated);
    }

    /**
     * Gets the wsid.
     * 
     * @return the wsid
     */
    public String getWsid()
    {
        return getSemanticObject().getProperty(swb_res_cmts_wsid);
    }

    /**
     * Sets the wsid.
     * 
     * @param wsid the new wsid
     */
    public void setWsid(String wsid)
    {
        getSemanticObject().setProperty(swb_res_cmts_wsid, wsid);
    }

    /**
     * Gets the wpid.
     * 
     * @return the wpid
     */
    public String getWpid()
    {
        return getSemanticObject().getProperty(swb_res_cmts_wpid);
    }

    /**
     * Sets the wpid.
     * 
     * @param wpid the new wpid
     */
    public void setWpid(String wpid)
    {
        getSemanticObject().setProperty(swb_res_cmts_wpid, wpid);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.TraceableBase#setCreator(org.semanticwb.model.User)
     */
    /**
     * Sets the creator.
     * 
     * @param user the new creator
     */
    public void setCreator(org.semanticwb.model.User user)
    {
        getSemanticObject().setObjectProperty(swb_creator, user.getSemanticObject());
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.TraceableBase#removeCreator()
     */
    /**
     * Removes the creator.
     */
    public void removeCreator()
    {
        getSemanticObject().removeProperty(swb_creator);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.TraceableBase#getCreator()
     */
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
}
