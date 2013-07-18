package org.semanticwb.portal.resources.sem.base;


public abstract class SWBCommentToElementBase extends org.semanticwb.portal.api.GenericSemResource 
{
    public static final org.semanticwb.platform.SemanticProperty swb_res_cmts_maxLength=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/portal/resources/sems/SWBCommentToElement#maxLength");
    public static final org.semanticwb.platform.SemanticProperty swb_res_cmts_renderCommentsList=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/portal/resources/sems/SWBCommentToElement#renderCommentsList");
    public static final org.semanticwb.platform.SemanticProperty swb_res_cmts_blockSize=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/portal/resources/sems/SWBCommentToElement#blockSize");
    public static final org.semanticwb.platform.SemanticClass swb_Resource=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Resource");
    public static final org.semanticwb.platform.SemanticProperty swb_semanticResourceInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#semanticResourceInv");
    public static final org.semanticwb.platform.SemanticProperty swb_res_cmts_maxConsecutive=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/portal/resources/sems/SWBCommentToElement#maxConsecutive");
    public static final org.semanticwb.platform.SemanticClass swb_res_cmts_CommentToElement=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/portal/resources/sems/SWBCommentToElement#CommentToElement");
    public static final org.semanticwb.platform.SemanticProperty swb_res_cmts_hasComment=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/portal/resources/sems/SWBCommentToElement#hasComment");
    public static final org.semanticwb.platform.SemanticClass swb_res_cmts_SWBCommentToElement=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/portal/resources/sems/SWBCommentToElement#SWBCommentToElement");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/portal/resources/sems/SWBCommentToElement#SWBCommentToElement");

    public SWBCommentToElementBase()
    {
    }

   /**
   * Constructs a SWBCommentToElementBase with a SemanticObject
   * @param base The SemanticObject with the properties for the SWBCommentToElement
   */
    public SWBCommentToElementBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    /*
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() 
    {
        return getSemanticObject().hashCode();
    }

    /*
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) 
    {
        if(obj==null)return false;
        return hashCode()==obj.hashCode();
    }

/**
* Gets the MaxLength property
* @return int with the MaxLength
*/
    public int getMaxLength()
    {
        return getSemanticObject().getIntProperty(swb_res_cmts_maxLength);
    }

/**
* Sets the MaxLength property
* @param value long with the MaxLength
*/
    public void setMaxLength(int value)
    {
        getSemanticObject().setIntProperty(swb_res_cmts_maxLength, value);
    }

/**
* Gets the RenderCommentsList property
* @return String with the RenderCommentsList
*/
    public String getRenderCommentsList()
    {
        return getSemanticObject().getProperty(swb_res_cmts_renderCommentsList);
    }

/**
* Sets the RenderCommentsList property
* @param value long with the RenderCommentsList
*/
    public void setRenderCommentsList(String value)
    {
        getSemanticObject().setProperty(swb_res_cmts_renderCommentsList, value);
    }

/**
* Gets the BlockSize property
* @return int with the BlockSize
*/
    public int getBlockSize()
    {
        return getSemanticObject().getIntProperty(swb_res_cmts_blockSize);
    }

/**
* Sets the BlockSize property
* @param value long with the BlockSize
*/
    public void setBlockSize(int value)
    {
        getSemanticObject().setIntProperty(swb_res_cmts_blockSize, value);
    }
   /**
   * Sets the value for the property Resource
   * @param value Resource to set
   */

    public void setResource(org.semanticwb.model.Resource value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swb_semanticResourceInv, value.getSemanticObject());
        }else
        {
            removeResource();
        }
    }
   /**
   * Remove the value for Resource property
   */

    public void removeResource()
    {
        getSemanticObject().removeProperty(swb_semanticResourceInv);
    }

   /**
   * Gets the Resource
   * @return a org.semanticwb.model.Resource
   */
    public org.semanticwb.model.Resource getResource()
    {
         org.semanticwb.model.Resource ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_semanticResourceInv);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.Resource)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the MaxConsecutive property
* @return int with the MaxConsecutive
*/
    public int getMaxConsecutive()
    {
        return getSemanticObject().getIntProperty(swb_res_cmts_maxConsecutive);
    }

/**
* Sets the MaxConsecutive property
* @param value long with the MaxConsecutive
*/
    public void setMaxConsecutive(int value)
    {
        getSemanticObject().setIntProperty(swb_res_cmts_maxConsecutive, value);
    }
   /**
   * Gets all the org.semanticwb.portal.resources.sem.CommentToElement
   * @return A GenericIterator with all the org.semanticwb.portal.resources.sem.CommentToElement
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.CommentToElement> listComments()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.CommentToElement>(getSemanticObject().listObjectProperties(swb_res_cmts_hasComment));
    }

   /**
   * Gets true if has a Comment
   * @param value org.semanticwb.portal.resources.sem.CommentToElement to verify
   * @return true if the org.semanticwb.portal.resources.sem.CommentToElement exists, false otherwise
   */
    public boolean hasComment(org.semanticwb.portal.resources.sem.CommentToElement value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swb_res_cmts_hasComment,value.getSemanticObject());
        }
        return ret;
    }
   /**
   * Adds a Comment
   * @param value org.semanticwb.portal.resources.sem.CommentToElement to add
   */

    public void addComment(org.semanticwb.portal.resources.sem.CommentToElement value)
    {
        getSemanticObject().addObjectProperty(swb_res_cmts_hasComment, value.getSemanticObject());
    }
   /**
   * Removes all the Comment
   */

    public void removeAllComment()
    {
        getSemanticObject().removeProperty(swb_res_cmts_hasComment);
    }
   /**
   * Removes a Comment
   * @param value org.semanticwb.portal.resources.sem.CommentToElement to remove
   */

    public void removeComment(org.semanticwb.portal.resources.sem.CommentToElement value)
    {
        getSemanticObject().removeObjectProperty(swb_res_cmts_hasComment,value.getSemanticObject());
    }

   /**
   * Gets the Comment
   * @return a org.semanticwb.portal.resources.sem.CommentToElement
   */
    public org.semanticwb.portal.resources.sem.CommentToElement getComment()
    {
         org.semanticwb.portal.resources.sem.CommentToElement ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_res_cmts_hasComment);
         if(obj!=null)
         {
             ret=(org.semanticwb.portal.resources.sem.CommentToElement)obj.createGenericInstance();
         }
         return ret;
    }
}
