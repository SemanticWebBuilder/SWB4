package org.semanticwb.resources.sem.forumcat.base;


public abstract class PublishQuestionBase extends org.semanticwb.portal.api.GenericSemResource 
{
    public static final org.semanticwb.platform.SemanticProperty forumCat_forumCatID=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#forumCatID");
    public static final org.semanticwb.platform.SemanticClass swb_Resource=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Resource");
    public static final org.semanticwb.platform.SemanticProperty swb_semanticResourceInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#semanticResourceInv");
    public static final org.semanticwb.platform.SemanticClass forumCat_PublishQuestion=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#PublishQuestion");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#PublishQuestion");

    public PublishQuestionBase()
    {
    }

   /**
   * Constructs a PublishQuestionBase with a SemanticObject
   * @param base The SemanticObject with the properties for the PublishQuestion
   */
    public PublishQuestionBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

/**
* Gets the ForumCatID property
* @return String with the ForumCatID
*/
    public String getForumCatID()
    {
        return getSemanticObject().getProperty(forumCat_forumCatID);
    }

/**
* Sets the ForumCatID property
* @param value long with the ForumCatID
*/
    public void setForumCatID(String value)
    {
        getSemanticObject().setProperty(forumCat_forumCatID, value);
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
}
