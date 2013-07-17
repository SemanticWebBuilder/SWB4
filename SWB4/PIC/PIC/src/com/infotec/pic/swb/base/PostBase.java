package com.infotec.pic.swb.base;


public abstract class PostBase extends org.semanticwb.model.SWBClass implements org.semanticwb.model.Hitable,org.semanticwb.model.Searchable,com.infotec.pic.swb.AdditionalFiles,org.semanticwb.model.Expirable,com.infotec.pic.swb.AdditionalImages,org.semanticwb.model.Activeable,org.semanticwb.model.Traceable,com.infotec.pic.swb.AdditionalUrl,org.semanticwb.model.Tagable,org.semanticwb.model.Descriptiveable
{
   /**
   * Catalogo de paises
   */
    public static final org.semanticwb.platform.SemanticClass swb_Country=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Country");
    public static final org.semanticwb.platform.SemanticProperty pic_hasCountryRegion=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/pic.owl#hasCountryRegion");
    public static final org.semanticwb.platform.SemanticClass pic_Maturity=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/pic.owl#Maturity");
    public static final org.semanticwb.platform.SemanticProperty pic_postMaturity=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/pic.owl#postMaturity");
   /**
   * Sector al que pertenece
   */
    public static final org.semanticwb.platform.SemanticClass pic_Sector=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/pic.owl#Sector");
    public static final org.semanticwb.platform.SemanticProperty pic_hasSector=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/pic.owl#hasSector");
    public static final org.semanticwb.platform.SemanticProperty pic_source=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/pic.owl#source");
    public static final org.semanticwb.platform.SemanticClass pic_Comment=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/pic.owl#Comment");
    public static final org.semanticwb.platform.SemanticProperty pic_hasComment=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/pic.owl#hasComment");
    public static final org.semanticwb.platform.SemanticClass pic_Post=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/pic.owl#Post");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/pic.owl#Post");

    public static class ClassMgr
    {
       /**
       * Returns a list of Post for a model
       * @param model Model to find
       * @return Iterator of com.infotec.pic.swb.Post
       */

        public static java.util.Iterator<com.infotec.pic.swb.Post> listPosts(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<com.infotec.pic.swb.Post>(it, true);
        }
       /**
       * Returns a list of com.infotec.pic.swb.Post for all models
       * @return Iterator of com.infotec.pic.swb.Post
       */

        public static java.util.Iterator<com.infotec.pic.swb.Post> listPosts()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<com.infotec.pic.swb.Post>(it, true);
        }

        public static com.infotec.pic.swb.Post createPost(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return com.infotec.pic.swb.Post.ClassMgr.createPost(String.valueOf(id), model);
        }
       /**
       * Gets a com.infotec.pic.swb.Post
       * @param id Identifier for com.infotec.pic.swb.Post
       * @param model Model of the com.infotec.pic.swb.Post
       * @return A com.infotec.pic.swb.Post
       */
        public static com.infotec.pic.swb.Post getPost(String id, org.semanticwb.model.SWBModel model)
        {
            return (com.infotec.pic.swb.Post)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a com.infotec.pic.swb.Post
       * @param id Identifier for com.infotec.pic.swb.Post
       * @param model Model of the com.infotec.pic.swb.Post
       * @return A com.infotec.pic.swb.Post
       */
        public static com.infotec.pic.swb.Post createPost(String id, org.semanticwb.model.SWBModel model)
        {
            return (com.infotec.pic.swb.Post)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a com.infotec.pic.swb.Post
       * @param id Identifier for com.infotec.pic.swb.Post
       * @param model Model of the com.infotec.pic.swb.Post
       */
        public static void removePost(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a com.infotec.pic.swb.Post
       * @param id Identifier for com.infotec.pic.swb.Post
       * @param model Model of the com.infotec.pic.swb.Post
       * @return true if the com.infotec.pic.swb.Post exists, false otherwise
       */

        public static boolean hasPost(String id, org.semanticwb.model.SWBModel model)
        {
            return (getPost(id, model)!=null);
        }
       /**
       * Gets all com.infotec.pic.swb.Post with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the com.infotec.pic.swb.Post
       * @return Iterator with all the com.infotec.pic.swb.Post
       */

        public static java.util.Iterator<com.infotec.pic.swb.Post> listPostByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.Post> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.Post with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the com.infotec.pic.swb.Post
       */

        public static java.util.Iterator<com.infotec.pic.swb.Post> listPostByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.Post> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.Post with a determined CountryRegion
       * @param value CountryRegion of the type org.semanticwb.model.Country
       * @param model Model of the com.infotec.pic.swb.Post
       * @return Iterator with all the com.infotec.pic.swb.Post
       */

        public static java.util.Iterator<com.infotec.pic.swb.Post> listPostByCountryRegion(org.semanticwb.model.Country value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.Post> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(pic_hasCountryRegion, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.Post with a determined CountryRegion
       * @param value CountryRegion of the type org.semanticwb.model.Country
       * @return Iterator with all the com.infotec.pic.swb.Post
       */

        public static java.util.Iterator<com.infotec.pic.swb.Post> listPostByCountryRegion(org.semanticwb.model.Country value)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.Post> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(pic_hasCountryRegion,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.Post with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the com.infotec.pic.swb.Post
       * @return Iterator with all the com.infotec.pic.swb.Post
       */

        public static java.util.Iterator<com.infotec.pic.swb.Post> listPostByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.Post> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.Post with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the com.infotec.pic.swb.Post
       */

        public static java.util.Iterator<com.infotec.pic.swb.Post> listPostByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.Post> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.Post with a determined Maturity
       * @param value Maturity of the type com.infotec.pic.swb.Maturity
       * @param model Model of the com.infotec.pic.swb.Post
       * @return Iterator with all the com.infotec.pic.swb.Post
       */

        public static java.util.Iterator<com.infotec.pic.swb.Post> listPostByMaturity(com.infotec.pic.swb.Maturity value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.Post> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(pic_postMaturity, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.Post with a determined Maturity
       * @param value Maturity of the type com.infotec.pic.swb.Maturity
       * @return Iterator with all the com.infotec.pic.swb.Post
       */

        public static java.util.Iterator<com.infotec.pic.swb.Post> listPostByMaturity(com.infotec.pic.swb.Maturity value)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.Post> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(pic_postMaturity,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.Post with a determined Sector
       * @param value Sector of the type com.infotec.pic.swb.Sector
       * @param model Model of the com.infotec.pic.swb.Post
       * @return Iterator with all the com.infotec.pic.swb.Post
       */

        public static java.util.Iterator<com.infotec.pic.swb.Post> listPostBySector(com.infotec.pic.swb.Sector value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.Post> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(pic_hasSector, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.Post with a determined Sector
       * @param value Sector of the type com.infotec.pic.swb.Sector
       * @return Iterator with all the com.infotec.pic.swb.Post
       */

        public static java.util.Iterator<com.infotec.pic.swb.Post> listPostBySector(com.infotec.pic.swb.Sector value)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.Post> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(pic_hasSector,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.Post with a determined Comment
       * @param value Comment of the type com.infotec.pic.swb.Comment
       * @param model Model of the com.infotec.pic.swb.Post
       * @return Iterator with all the com.infotec.pic.swb.Post
       */

        public static java.util.Iterator<com.infotec.pic.swb.Post> listPostByComment(com.infotec.pic.swb.Comment value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.Post> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(pic_hasComment, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.Post with a determined Comment
       * @param value Comment of the type com.infotec.pic.swb.Comment
       * @return Iterator with all the com.infotec.pic.swb.Post
       */

        public static java.util.Iterator<com.infotec.pic.swb.Post> listPostByComment(com.infotec.pic.swb.Comment value)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.Post> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(pic_hasComment,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static PostBase.ClassMgr getPostClassMgr()
    {
        return new PostBase.ClassMgr();
    }

   /**
   * Constructs a PostBase with a SemanticObject
   * @param base The SemanticObject with the properties for the Post
   */
    public PostBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
   /**
   * Sets the value for the property ModifiedBy
   * @param value ModifiedBy to set
   */

    public void setModifiedBy(org.semanticwb.model.User value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swb_modifiedBy, value.getSemanticObject());
        }else
        {
            removeModifiedBy();
        }
    }
   /**
   * Remove the value for ModifiedBy property
   */

    public void removeModifiedBy()
    {
        getSemanticObject().removeProperty(swb_modifiedBy);
    }

   /**
   * Gets the ModifiedBy
   * @return a org.semanticwb.model.User
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
* Gets the Updated property
* @return java.util.Date with the Updated
*/
    public java.util.Date getUpdated()
    {
        return getSemanticObject().getDateProperty(swb_updated);
    }

/**
* Sets the Updated property
* @param value long with the Updated
*/
    public void setUpdated(java.util.Date value)
    {
        getSemanticObject().setDateProperty(swb_updated, value);
    }

/**
* Gets the Created property
* @return java.util.Date with the Created
*/
    public java.util.Date getCreated()
    {
        return getSemanticObject().getDateProperty(swb_created);
    }

/**
* Sets the Created property
* @param value long with the Created
*/
    public void setCreated(java.util.Date value)
    {
        getSemanticObject().setDateProperty(swb_created, value);
    }

/**
* Gets the Description property
* @return String with the Description
*/
    public String getDescription()
    {
        return getSemanticObject().getProperty(swb_description);
    }

/**
* Sets the Description property
* @param value long with the Description
*/
    public void setDescription(String value)
    {
        getSemanticObject().setProperty(swb_description, value);
    }

    public String getDescription(String lang)
    {
        return getSemanticObject().getProperty(swb_description, null, lang);
    }

    public String getDisplayDescription(String lang)
    {
        return getSemanticObject().getLocaleProperty(swb_description, lang);
    }

    public void setDescription(String description, String lang)
    {
        getSemanticObject().setProperty(swb_description, description, lang);
    }

/**
* Gets the Expiration property
* @return java.util.Date with the Expiration
*/
    public java.util.Date getExpiration()
    {
        return getSemanticObject().getDateProperty(swb_expiration);
    }

/**
* Sets the Expiration property
* @param value long with the Expiration
*/
    public void setExpiration(java.util.Date value)
    {
        getSemanticObject().setDateProperty(swb_expiration, value);
    }
   /**
   * Gets all the org.semanticwb.model.Country
   * @return A GenericIterator with all the org.semanticwb.model.Country
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.model.Country> listCountryRegions()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.Country>(getSemanticObject().listObjectProperties(pic_hasCountryRegion));
    }

   /**
   * Gets true if has a CountryRegion
   * @param value org.semanticwb.model.Country to verify
   * @return true if the org.semanticwb.model.Country exists, false otherwise
   */
    public boolean hasCountryRegion(org.semanticwb.model.Country value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(pic_hasCountryRegion,value.getSemanticObject());
        }
        return ret;
    }
   /**
   * Adds a CountryRegion
   * @param value org.semanticwb.model.Country to add
   */

    public void addCountryRegion(org.semanticwb.model.Country value)
    {
        getSemanticObject().addObjectProperty(pic_hasCountryRegion, value.getSemanticObject());
    }
   /**
   * Removes all the CountryRegion
   */

    public void removeAllCountryRegion()
    {
        getSemanticObject().removeProperty(pic_hasCountryRegion);
    }
   /**
   * Removes a CountryRegion
   * @param value org.semanticwb.model.Country to remove
   */

    public void removeCountryRegion(org.semanticwb.model.Country value)
    {
        getSemanticObject().removeObjectProperty(pic_hasCountryRegion,value.getSemanticObject());
    }

   /**
   * Gets the CountryRegion
   * @return a org.semanticwb.model.Country
   */
    public org.semanticwb.model.Country getCountryRegion()
    {
         org.semanticwb.model.Country ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(pic_hasCountryRegion);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.Country)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the Url property
* @return String with the Url
*/
    public String getUrl()
    {
        return getSemanticObject().getProperty(pic_url);
    }

/**
* Sets the Url property
* @param value long with the Url
*/
    public void setUrl(String value)
    {
        getSemanticObject().setProperty(pic_url, value);
    }

/**
* Gets the Hits property
* @return long with the Hits
*/
    public long getHits()
    {
        //Override this method in Post object
        return getSemanticObject().getLongProperty(swb_hits,false);
    }

/**
* Sets the Hits property
* @param value long with the Hits
*/
    public void setHits(long value)
    {
        //Override this method in Post object
        getSemanticObject().setLongProperty(swb_hits, value,false);
    }

/**
* Gets the Active property
* @return boolean with the Active
*/
    public boolean isActive()
    {
        return getSemanticObject().getBooleanProperty(swb_active);
    }

/**
* Sets the Active property
* @param value long with the Active
*/
    public void setActive(boolean value)
    {
        getSemanticObject().setBooleanProperty(swb_active, value);
    }

    public java.util.Iterator<String> listFiles()
    {
        java.util.ArrayList<String> values=new java.util.ArrayList<String>();
        java.util.Iterator<org.semanticwb.platform.SemanticLiteral> it=getSemanticObject().listLiteralProperties(pic_hasFile);
        while(it.hasNext())
        {
                org.semanticwb.platform.SemanticLiteral literal=it.next();
                values.add(literal.getString());
        }
        return values.iterator();
    }

    public void addFile(String value)
    {
        getSemanticObject().addLiteralProperty(pic_hasFile, new org.semanticwb.platform.SemanticLiteral(value));
    }

    public void removeAllFile()
    {
        getSemanticObject().removeProperty(pic_hasFile);
    }

    public void removeFile(String value)
    {
        getSemanticObject().removeLiteralProperty(pic_hasFile,new org.semanticwb.platform.SemanticLiteral(value));
    }
   /**
   * Sets the value for the property Creator
   * @param value Creator to set
   */

    public void setCreator(org.semanticwb.model.User value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swb_creator, value.getSemanticObject());
        }else
        {
            removeCreator();
        }
    }
   /**
   * Remove the value for Creator property
   */

    public void removeCreator()
    {
        getSemanticObject().removeProperty(swb_creator);
    }

   /**
   * Gets the Creator
   * @return a org.semanticwb.model.User
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
* Gets the Title property
* @return String with the Title
*/
    public String getTitle()
    {
        return getSemanticObject().getProperty(swb_title);
    }

/**
* Sets the Title property
* @param value long with the Title
*/
    public void setTitle(String value)
    {
        getSemanticObject().setProperty(swb_title, value);
    }

    public String getTitle(String lang)
    {
        return getSemanticObject().getProperty(swb_title, null, lang);
    }

    public String getDisplayTitle(String lang)
    {
        return getSemanticObject().getLocaleProperty(swb_title, lang);
    }

    public void setTitle(String title, String lang)
    {
        getSemanticObject().setProperty(swb_title, title, lang);
    }

/**
* Gets the MaxHits property
* @return long with the MaxHits
*/
    public long getMaxHits()
    {
        return getSemanticObject().getLongProperty(swb_maxHits);
    }

/**
* Sets the MaxHits property
* @param value long with the MaxHits
*/
    public void setMaxHits(long value)
    {
        getSemanticObject().setLongProperty(swb_maxHits, value);
    }

/**
* Gets the Tags property
* @return String with the Tags
*/
    public String getTags()
    {
        return getSemanticObject().getProperty(swb_tags);
    }

/**
* Sets the Tags property
* @param value long with the Tags
*/
    public void setTags(String value)
    {
        getSemanticObject().setProperty(swb_tags, value);
    }

    public String getTags(String lang)
    {
        return getSemanticObject().getProperty(swb_tags, null, lang);
    }

    public String getDisplayTags(String lang)
    {
        return getSemanticObject().getLocaleProperty(swb_tags, lang);
    }

    public void setTags(String tags, String lang)
    {
        getSemanticObject().setProperty(swb_tags, tags, lang);
    }

    public java.util.Iterator<String> listImages()
    {
        java.util.ArrayList<String> values=new java.util.ArrayList<String>();
        java.util.Iterator<org.semanticwb.platform.SemanticLiteral> it=getSemanticObject().listLiteralProperties(pic_hasImage);
        while(it.hasNext())
        {
                org.semanticwb.platform.SemanticLiteral literal=it.next();
                values.add(literal.getString());
        }
        return values.iterator();
    }

    public void addImage(String value)
    {
        getSemanticObject().addLiteralProperty(pic_hasImage, new org.semanticwb.platform.SemanticLiteral(value));
    }

    public void removeAllImage()
    {
        getSemanticObject().removeProperty(pic_hasImage);
    }

    public void removeImage(String value)
    {
        getSemanticObject().removeLiteralProperty(pic_hasImage,new org.semanticwb.platform.SemanticLiteral(value));
    }
   /**
   * Sets the value for the property Maturity
   * @param value Maturity to set
   */

    public void setMaturity(com.infotec.pic.swb.Maturity value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(pic_postMaturity, value.getSemanticObject());
        }else
        {
            removeMaturity();
        }
    }
   /**
   * Remove the value for Maturity property
   */

    public void removeMaturity()
    {
        getSemanticObject().removeProperty(pic_postMaturity);
    }

   /**
   * Gets the Maturity
   * @return a com.infotec.pic.swb.Maturity
   */
    public com.infotec.pic.swb.Maturity getMaturity()
    {
         com.infotec.pic.swb.Maturity ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(pic_postMaturity);
         if(obj!=null)
         {
             ret=(com.infotec.pic.swb.Maturity)obj.createGenericInstance();
         }
         return ret;
    }
   /**
   * Gets all the com.infotec.pic.swb.Sector
   * @return A GenericIterator with all the com.infotec.pic.swb.Sector
   */

    public org.semanticwb.model.GenericIterator<com.infotec.pic.swb.Sector> listSectors()
    {
        return new org.semanticwb.model.GenericIterator<com.infotec.pic.swb.Sector>(getSemanticObject().listObjectProperties(pic_hasSector));
    }

   /**
   * Gets true if has a Sector
   * @param value com.infotec.pic.swb.Sector to verify
   * @return true if the com.infotec.pic.swb.Sector exists, false otherwise
   */
    public boolean hasSector(com.infotec.pic.swb.Sector value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(pic_hasSector,value.getSemanticObject());
        }
        return ret;
    }
   /**
   * Adds a Sector
   * @param value com.infotec.pic.swb.Sector to add
   */

    public void addSector(com.infotec.pic.swb.Sector value)
    {
        getSemanticObject().addObjectProperty(pic_hasSector, value.getSemanticObject());
    }
   /**
   * Removes all the Sector
   */

    public void removeAllSector()
    {
        getSemanticObject().removeProperty(pic_hasSector);
    }
   /**
   * Removes a Sector
   * @param value com.infotec.pic.swb.Sector to remove
   */

    public void removeSector(com.infotec.pic.swb.Sector value)
    {
        getSemanticObject().removeObjectProperty(pic_hasSector,value.getSemanticObject());
    }

   /**
   * Gets the Sector
   * @return a com.infotec.pic.swb.Sector
   */
    public com.infotec.pic.swb.Sector getSector()
    {
         com.infotec.pic.swb.Sector ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(pic_hasSector);
         if(obj!=null)
         {
             ret=(com.infotec.pic.swb.Sector)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the Source property
* @return String with the Source
*/
    public String getSource()
    {
        return getSemanticObject().getProperty(pic_source);
    }

/**
* Sets the Source property
* @param value long with the Source
*/
    public void setSource(String value)
    {
        getSemanticObject().setProperty(pic_source, value);
    }
   /**
   * Gets all the com.infotec.pic.swb.Comment
   * @return A GenericIterator with all the com.infotec.pic.swb.Comment
   */

    public org.semanticwb.model.GenericIterator<com.infotec.pic.swb.Comment> listComments()
    {
        return new org.semanticwb.model.GenericIterator<com.infotec.pic.swb.Comment>(getSemanticObject().listObjectProperties(pic_hasComment));
    }

   /**
   * Gets true if has a Comment
   * @param value com.infotec.pic.swb.Comment to verify
   * @return true if the com.infotec.pic.swb.Comment exists, false otherwise
   */
    public boolean hasComment(com.infotec.pic.swb.Comment value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(pic_hasComment,value.getSemanticObject());
        }
        return ret;
    }
   /**
   * Adds a Comment
   * @param value com.infotec.pic.swb.Comment to add
   */

    public void addComment(com.infotec.pic.swb.Comment value)
    {
        getSemanticObject().addObjectProperty(pic_hasComment, value.getSemanticObject());
    }
   /**
   * Removes all the Comment
   */

    public void removeAllComment()
    {
        getSemanticObject().removeProperty(pic_hasComment);
    }
   /**
   * Removes a Comment
   * @param value com.infotec.pic.swb.Comment to remove
   */

    public void removeComment(com.infotec.pic.swb.Comment value)
    {
        getSemanticObject().removeObjectProperty(pic_hasComment,value.getSemanticObject());
    }

   /**
   * Gets the Comment
   * @return a com.infotec.pic.swb.Comment
   */
    public com.infotec.pic.swb.Comment getComment()
    {
         com.infotec.pic.swb.Comment ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(pic_hasComment);
         if(obj!=null)
         {
             ret=(com.infotec.pic.swb.Comment)obj.createGenericInstance();
         }
         return ret;
    }
}
