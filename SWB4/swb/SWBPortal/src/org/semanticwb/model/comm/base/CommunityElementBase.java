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
 
package org.semanticwb.model.comm.base;


// TODO: Auto-generated Javadoc
/**
 * The Class CommunityElementBase.
 */
public class CommunityElementBase extends org.semanticwb.model.SWBClass implements org.semanticwb.model.Rankable,org.semanticwb.model.Traceable,org.semanticwb.model.Descriptiveable
{
    
    /** The Constant swb_created. */
    public static final org.semanticwb.platform.SemanticProperty swb_created=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#created");
    
    /** The Constant swb_User. */
    public static final org.semanticwb.platform.SemanticClass swb_User=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#User");
    
    /** The Constant swb_modifiedBy. */
    public static final org.semanticwb.platform.SemanticProperty swb_modifiedBy=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#modifiedBy");
    
    /** The Constant swbcomm_visibility. */
    public static final org.semanticwb.platform.SemanticProperty swbcomm_visibility=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#visibility");
    
    /** The Constant swb_title. */
    public static final org.semanticwb.platform.SemanticProperty swb_title=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#title");
    
    /** The Constant swb_reviews. */
    public static final org.semanticwb.platform.SemanticProperty swb_reviews=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#reviews");
    
    /** The Constant swb_updated. */
    public static final org.semanticwb.platform.SemanticProperty swb_updated=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#updated");
    
    /** The Constant swb_rank. */
    public static final org.semanticwb.platform.SemanticProperty swb_rank=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#rank");
    
    /** The Constant swb_tags. */
    public static final org.semanticwb.platform.SemanticProperty swb_tags=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#tags");
    
    /** The Constant swb_creator. */
    public static final org.semanticwb.platform.SemanticProperty swb_creator=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#creator");
    
    /** The Constant swbcomm_Comment. */
    public static final org.semanticwb.platform.SemanticClass swbcomm_Comment=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#Comment");
    
    /** The Constant swbcomm_hasComment. */
    public static final org.semanticwb.platform.SemanticProperty swbcomm_hasComment=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#hasComment");
    
    /** The Constant swb_description. */
    public static final org.semanticwb.platform.SemanticProperty swb_description=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#description");
    
    /** The Constant swbcomm_CommunityElement. */
    public static final org.semanticwb.platform.SemanticClass swbcomm_CommunityElement=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#CommunityElement");
    
    /** The Constant sclass. */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#CommunityElement");

    /**
     * Instantiates a new community element base.
     * 
     * @param base the base
     */
    public CommunityElementBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    /**
     * List community elements.
     * 
     * @param model the model
     * @return the java.util. iterator
     */
    public static java.util.Iterator<org.semanticwb.model.comm.CommunityElement> listCommunityElements(org.semanticwb.model.SWBModel model)
    {
        java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.comm.CommunityElement>(it, true);
    }

    /**
     * List community elements.
     * 
     * @return the java.util. iterator
     */
    public static java.util.Iterator<org.semanticwb.model.comm.CommunityElement> listCommunityElements()
    {
        java.util.Iterator it=sclass.listInstances();
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.comm.CommunityElement>(it, true);
    }

    /**
     * Gets the community element.
     * 
     * @param id the id
     * @param model the model
     * @return the community element
     */
    public static org.semanticwb.model.comm.CommunityElement getCommunityElement(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.model.comm.CommunityElement)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
    }

    /**
     * Creates the community element.
     * 
     * @param id the id
     * @param model the model
     * @return the org.semanticwb.model.comm. community element
     */
    public static org.semanticwb.model.comm.CommunityElement createCommunityElement(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.model.comm.CommunityElement)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
    }

    /**
     * Removes the community element.
     * 
     * @param id the id
     * @param model the model
     */
    public static void removeCommunityElement(String id, org.semanticwb.model.SWBModel model)
    {
        model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
    }

    /**
     * Checks for community element.
     * 
     * @param id the id
     * @param model the model
     * @return true, if successful
     */
    public static boolean hasCommunityElement(String id, org.semanticwb.model.SWBModel model)
    {
        return (getCommunityElement(id, model)!=null);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.TraceableBase#getCreated()
     */
    public java.util.Date getCreated()
    {
        return getSemanticObject().getDateProperty(swb_created);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.TraceableBase#setCreated(java.util.Date)
     */
    public void setCreated(java.util.Date created)
    {
        getSemanticObject().setDateProperty(swb_created, created);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.TraceableBase#setModifiedBy(org.semanticwb.model.User)
     */
    public void setModifiedBy(org.semanticwb.model.User user)
    {
        getSemanticObject().setObjectProperty(swb_modifiedBy, user.getSemanticObject());
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.TraceableBase#removeModifiedBy()
     */
    public void removeModifiedBy()
    {
        getSemanticObject().removeProperty(swb_modifiedBy);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.TraceableBase#getModifiedBy()
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
     * Gets the visibility.
     * 
     * @return the visibility
     */
    public int getVisibility()
    {
        return getSemanticObject().getIntProperty(swbcomm_visibility);
    }

    /**
     * Sets the visibility.
     * 
     * @param visibility the new visibility
     */
    public void setVisibility(int visibility)
    {
        getSemanticObject().setLongProperty(swbcomm_visibility, visibility);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.DescriptiveableBase#getTitle()
     */
    public String getTitle()
    {
        return getSemanticObject().getProperty(swb_title);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.DescriptiveableBase#setTitle(java.lang.String)
     */
    public void setTitle(String title)
    {
        getSemanticObject().setProperty(swb_title, title);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.DescriptiveableBase#getTitle(java.lang.String)
     */
    public String getTitle(String lang)
    {
        return getSemanticObject().getProperty(swb_title, null, lang);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.DescriptiveableBase#getDisplayTitle(java.lang.String)
     */
    public String getDisplayTitle(String lang)
    {
        return getSemanticObject().getLocaleProperty(swb_title, lang);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.DescriptiveableBase#setTitle(java.lang.String, java.lang.String)
     */
    public void setTitle(String title, String lang)
    {
        getSemanticObject().setProperty(swb_title, title, lang);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.RankableBase#getReviews()
     */
    public long getReviews()
    {
        return getSemanticObject().getLongProperty(swb_reviews);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.RankableBase#setReviews(long)
     */
    public void setReviews(long reviews)
    {
        getSemanticObject().setLongProperty(swb_reviews, reviews);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.TraceableBase#getUpdated()
     */
    public java.util.Date getUpdated()
    {
        return getSemanticObject().getDateProperty(swb_updated);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.TraceableBase#setUpdated(java.util.Date)
     */
    public void setUpdated(java.util.Date updated)
    {
        getSemanticObject().setDateProperty(swb_updated, updated);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.RankableBase#getRank()
     */
    public double getRank()
    {
        return getSemanticObject().getDoubleProperty(swb_rank);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.RankableBase#setRank(double)
     */
    public void setRank(double rank)
    {
        getSemanticObject().setDoubleProperty(swb_rank, rank);
    }

    /**
     * Gets the tags.
     * 
     * @return the tags
     */
    public String getTags()
    {
        return getSemanticObject().getProperty(swb_tags);
    }

    /**
     * Sets the tags.
     * 
     * @param tags the new tags
     */
    public void setTags(String tags)
    {
        getSemanticObject().setProperty(swb_tags, tags);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.TraceableBase#setCreator(org.semanticwb.model.User)
     */
    public void setCreator(org.semanticwb.model.User user)
    {
        getSemanticObject().setObjectProperty(swb_creator, user.getSemanticObject());
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.TraceableBase#removeCreator()
     */
    public void removeCreator()
    {
        getSemanticObject().removeProperty(swb_creator);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.TraceableBase#getCreator()
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
     * List comments.
     * 
     * @return the org.semanticwb.model. generic iterator
     */
    public org.semanticwb.model.GenericIterator<org.semanticwb.model.comm.Comment> listComments()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.comm.Comment>(getSemanticObject().listObjectProperties(swbcomm_hasComment));
    }

    /**
     * Checks for comment.
     * 
     * @param comment the comment
     * @return true, if successful
     */
    public boolean hasComment(org.semanticwb.model.comm.Comment comment)
    {
        if(comment==null)return false;        return getSemanticObject().hasObjectProperty(swbcomm_hasComment,comment.getSemanticObject());
    }

    /**
     * Adds the comment.
     * 
     * @param comment the comment
     */
    public void addComment(org.semanticwb.model.comm.Comment comment)
    {
        getSemanticObject().addObjectProperty(swbcomm_hasComment, comment.getSemanticObject());
    }

    /**
     * Removes the all comment.
     */
    public void removeAllComment()
    {
        getSemanticObject().removeProperty(swbcomm_hasComment);
    }

    /**
     * Removes the comment.
     * 
     * @param comment the comment
     */
    public void removeComment(org.semanticwb.model.comm.Comment comment)
    {
        getSemanticObject().removeObjectProperty(swbcomm_hasComment,comment.getSemanticObject());
    }

    /**
     * Gets the comment.
     * 
     * @return the comment
     */
    public org.semanticwb.model.comm.Comment getComment()
    {
         org.semanticwb.model.comm.Comment ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swbcomm_hasComment);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.comm.Comment)obj.createGenericInstance();
         }
         return ret;
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.DescriptiveableBase#getDescription()
     */
    public String getDescription()
    {
        return getSemanticObject().getProperty(swb_description);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.DescriptiveableBase#setDescription(java.lang.String)
     */
    public void setDescription(String description)
    {
        getSemanticObject().setProperty(swb_description, description);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.DescriptiveableBase#getDescription(java.lang.String)
     */
    public String getDescription(String lang)
    {
        return getSemanticObject().getProperty(swb_description, null, lang);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.DescriptiveableBase#getDisplayDescription(java.lang.String)
     */
    public String getDisplayDescription(String lang)
    {
        return getSemanticObject().getLocaleProperty(swb_description, lang);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.DescriptiveableBase#setDescription(java.lang.String, java.lang.String)
     */
    public void setDescription(String description, String lang)
    {
        getSemanticObject().setProperty(swb_description, description, lang);
    }
}
