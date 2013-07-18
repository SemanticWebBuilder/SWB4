package org.semanticwb.portal.resources.sem.base;


public abstract class AudioFileBase extends org.semanticwb.model.SWBClass implements org.semanticwb.model.Activeable,org.semanticwb.model.Descriptiveable,org.semanticwb.model.Expirable,org.semanticwb.model.Rankable,org.semanticwb.model.Iconable,org.semanticwb.model.Traceable
{
    public static final org.semanticwb.platform.SemanticClass audiopdcst_AudioPodCast=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/portal/resources/sems/SWBAudioFile#AudioPodCast");
    public static final org.semanticwb.platform.SemanticProperty audiopdcst_audioPodcastInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/portal/resources/sems/SWBAudioFile#audioPodcastInv");
    public static final org.semanticwb.platform.SemanticProperty audiopdcst_year=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/portal/resources/sems/SWBAudioFile#year");
   /**
   * Autor o participante del contenido
   */
    public static final org.semanticwb.platform.SemanticProperty audiopdcst_author=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/portal/resources/sems/SWBAudioFile#author");
   /**
   * Los audios del podcast se agrupan por temas
   */
    public static final org.semanticwb.platform.SemanticClass audiopdcst_AudioPodcastTheme=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/portal/resources/sems/SWBAudioFile#AudioPodcastTheme");
   /**
   * Tema o categoría del audio del podcast
   */
    public static final org.semanticwb.platform.SemanticProperty audiopdcst_theme=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/portal/resources/sems/SWBAudioFile#theme");
   /**
   * Nombre del archivo de audio
   */
    public static final org.semanticwb.platform.SemanticProperty audiopdcst_filename=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/portal/resources/sems/SWBAudioFile#filename");
    public static final org.semanticwb.platform.SemanticClass audiopdcst_AudioFile=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/portal/resources/sems/SWBAudioFile#AudioFile");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/portal/resources/sems/SWBAudioFile#AudioFile");

    public static class ClassMgr
    {
       /**
       * Returns a list of AudioFile for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.portal.resources.sem.AudioFile
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.AudioFile> listAudioFiles(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.AudioFile>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.portal.resources.sem.AudioFile for all models
       * @return Iterator of org.semanticwb.portal.resources.sem.AudioFile
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.AudioFile> listAudioFiles()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.AudioFile>(it, true);
        }

        public static org.semanticwb.portal.resources.sem.AudioFile createAudioFile(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.portal.resources.sem.AudioFile.ClassMgr.createAudioFile(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.portal.resources.sem.AudioFile
       * @param id Identifier for org.semanticwb.portal.resources.sem.AudioFile
       * @param model Model of the org.semanticwb.portal.resources.sem.AudioFile
       * @return A org.semanticwb.portal.resources.sem.AudioFile
       */
        public static org.semanticwb.portal.resources.sem.AudioFile getAudioFile(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.portal.resources.sem.AudioFile)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.portal.resources.sem.AudioFile
       * @param id Identifier for org.semanticwb.portal.resources.sem.AudioFile
       * @param model Model of the org.semanticwb.portal.resources.sem.AudioFile
       * @return A org.semanticwb.portal.resources.sem.AudioFile
       */
        public static org.semanticwb.portal.resources.sem.AudioFile createAudioFile(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.portal.resources.sem.AudioFile)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.portal.resources.sem.AudioFile
       * @param id Identifier for org.semanticwb.portal.resources.sem.AudioFile
       * @param model Model of the org.semanticwb.portal.resources.sem.AudioFile
       */
        public static void removeAudioFile(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.portal.resources.sem.AudioFile
       * @param id Identifier for org.semanticwb.portal.resources.sem.AudioFile
       * @param model Model of the org.semanticwb.portal.resources.sem.AudioFile
       * @return true if the org.semanticwb.portal.resources.sem.AudioFile exists, false otherwise
       */

        public static boolean hasAudioFile(String id, org.semanticwb.model.SWBModel model)
        {
            return (getAudioFile(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.portal.resources.sem.AudioFile with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.portal.resources.sem.AudioFile
       * @return Iterator with all the org.semanticwb.portal.resources.sem.AudioFile
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.AudioFile> listAudioFileByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.AudioFile> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.portal.resources.sem.AudioFile with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.portal.resources.sem.AudioFile
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.AudioFile> listAudioFileByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.AudioFile> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.portal.resources.sem.AudioFile with a determined AudioPodcast
       * @param value AudioPodcast of the type org.semanticwb.portal.resources.sem.AudioPodCast
       * @param model Model of the org.semanticwb.portal.resources.sem.AudioFile
       * @return Iterator with all the org.semanticwb.portal.resources.sem.AudioFile
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.AudioFile> listAudioFileByAudioPodcast(org.semanticwb.portal.resources.sem.AudioPodCast value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.AudioFile> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(audiopdcst_audioPodcastInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.portal.resources.sem.AudioFile with a determined AudioPodcast
       * @param value AudioPodcast of the type org.semanticwb.portal.resources.sem.AudioPodCast
       * @return Iterator with all the org.semanticwb.portal.resources.sem.AudioFile
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.AudioFile> listAudioFileByAudioPodcast(org.semanticwb.portal.resources.sem.AudioPodCast value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.AudioFile> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(audiopdcst_audioPodcastInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.portal.resources.sem.AudioFile with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.portal.resources.sem.AudioFile
       * @return Iterator with all the org.semanticwb.portal.resources.sem.AudioFile
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.AudioFile> listAudioFileByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.AudioFile> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.portal.resources.sem.AudioFile with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.portal.resources.sem.AudioFile
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.AudioFile> listAudioFileByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.AudioFile> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.portal.resources.sem.AudioFile with a determined Theme
       * @param value Theme of the type org.semanticwb.portal.resources.sem.AudioPodcastTheme
       * @param model Model of the org.semanticwb.portal.resources.sem.AudioFile
       * @return Iterator with all the org.semanticwb.portal.resources.sem.AudioFile
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.AudioFile> listAudioFileByTheme(org.semanticwb.portal.resources.sem.AudioPodcastTheme value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.AudioFile> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(audiopdcst_theme, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.portal.resources.sem.AudioFile with a determined Theme
       * @param value Theme of the type org.semanticwb.portal.resources.sem.AudioPodcastTheme
       * @return Iterator with all the org.semanticwb.portal.resources.sem.AudioFile
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.AudioFile> listAudioFileByTheme(org.semanticwb.portal.resources.sem.AudioPodcastTheme value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.AudioFile> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(audiopdcst_theme,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static AudioFileBase.ClassMgr getAudioFileClassMgr()
    {
        return new AudioFileBase.ClassMgr();
    }

   /**
   * Constructs a AudioFileBase with a SemanticObject
   * @param base The SemanticObject with the properties for the AudioFile
   */
    public AudioFileBase(org.semanticwb.platform.SemanticObject base)
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
   * Sets the value for the property AudioPodcast
   * @param value AudioPodcast to set
   */

    public void setAudioPodcast(org.semanticwb.portal.resources.sem.AudioPodCast value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(audiopdcst_audioPodcastInv, value.getSemanticObject());
        }else
        {
            removeAudioPodcast();
        }
    }
   /**
   * Remove the value for AudioPodcast property
   */

    public void removeAudioPodcast()
    {
        getSemanticObject().removeProperty(audiopdcst_audioPodcastInv);
    }

   /**
   * Gets the AudioPodcast
   * @return a org.semanticwb.portal.resources.sem.AudioPodCast
   */
    public org.semanticwb.portal.resources.sem.AudioPodCast getAudioPodcast()
    {
         org.semanticwb.portal.resources.sem.AudioPodCast ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(audiopdcst_audioPodcastInv);
         if(obj!=null)
         {
             ret=(org.semanticwb.portal.resources.sem.AudioPodCast)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the Rank property
* @return double with the Rank
*/
    public double getRank()
    {
        return getSemanticObject().getDoubleProperty(swb_rank);
    }

/**
* Sets the Rank property
* @param value long with the Rank
*/
    public void setRank(double value)
    {
        getSemanticObject().setDoubleProperty(swb_rank, value);
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
* Gets the Year property
* @return String with the Year
*/
    public String getYear()
    {
        //Override this method in AudioFile object
        return getSemanticObject().getProperty(audiopdcst_year,false);
    }

/**
* Sets the Year property
* @param value long with the Year
*/
    public void setYear(String value)
    {
        //Override this method in AudioFile object
        getSemanticObject().setProperty(audiopdcst_year, value,false);
    }

/**
* Gets the Reviews property
* @return long with the Reviews
*/
    public long getReviews()
    {
        return getSemanticObject().getLongProperty(swb_reviews);
    }

/**
* Sets the Reviews property
* @param value long with the Reviews
*/
    public void setReviews(long value)
    {
        getSemanticObject().setLongProperty(swb_reviews, value);
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

/**
* Gets the IconClass property
* @return String with the IconClass
*/
    public String getIconClass()
    {
        return getSemanticObject().getProperty(swb_iconClass);
    }

/**
* Sets the IconClass property
* @param value long with the IconClass
*/
    public void setIconClass(String value)
    {
        getSemanticObject().setProperty(swb_iconClass, value);
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
* Gets the Author property
* @return String with the Author
*/
    public String getAuthor()
    {
        return getSemanticObject().getProperty(audiopdcst_author);
    }

/**
* Sets the Author property
* @param value long with the Author
*/
    public void setAuthor(String value)
    {
        getSemanticObject().setProperty(audiopdcst_author, value);
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
   * Sets the value for the property Theme
   * @param value Theme to set
   */

    public void setTheme(org.semanticwb.portal.resources.sem.AudioPodcastTheme value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(audiopdcst_theme, value.getSemanticObject());
        }else
        {
            removeTheme();
        }
    }
   /**
   * Remove the value for Theme property
   */

    public void removeTheme()
    {
        getSemanticObject().removeProperty(audiopdcst_theme);
    }

   /**
   * Gets the Theme
   * @return a org.semanticwb.portal.resources.sem.AudioPodcastTheme
   */
    public org.semanticwb.portal.resources.sem.AudioPodcastTheme getTheme()
    {
         org.semanticwb.portal.resources.sem.AudioPodcastTheme ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(audiopdcst_theme);
         if(obj!=null)
         {
             ret=(org.semanticwb.portal.resources.sem.AudioPodcastTheme)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the Filename property
* @return String with the Filename
*/
    public String getFilename()
    {
        return getSemanticObject().getProperty(audiopdcst_filename);
    }

/**
* Sets the Filename property
* @param value long with the Filename
*/
    public void setFilename(String value)
    {
        getSemanticObject().setProperty(audiopdcst_filename, value);
    }
}
