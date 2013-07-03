package org.semanticwb.portal.resources.sem.base;


   /**
   * Los audios del podcast se agrupan por temas 
   */
public abstract class AudioPodcastThemeBase extends org.semanticwb.model.SWBClass implements org.semanticwb.model.Activeable,org.semanticwb.model.Descriptiveable
{
    public static final org.semanticwb.platform.SemanticClass audiopdcst_AudioFile=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/portal/resources/sems/SWBAudioFile#AudioFile");
    public static final org.semanticwb.platform.SemanticProperty audiopdcst_hasAudioFileInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/portal/resources/sems/SWBAudioFile#hasAudioFileInv");
   /**
   * Los audios del podcast se agrupan por temas
   */
    public static final org.semanticwb.platform.SemanticClass audiopdcst_AudioPodcastTheme=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/portal/resources/sems/SWBAudioFile#AudioPodcastTheme");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/portal/resources/sems/SWBAudioFile#AudioPodcastTheme");

    public static class ClassMgr
    {
       /**
       * Returns a list of AudioPodcastTheme for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.portal.resources.sem.AudioPodcastTheme
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.AudioPodcastTheme> listAudioPodcastThemes(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.AudioPodcastTheme>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.portal.resources.sem.AudioPodcastTheme for all models
       * @return Iterator of org.semanticwb.portal.resources.sem.AudioPodcastTheme
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.AudioPodcastTheme> listAudioPodcastThemes()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.AudioPodcastTheme>(it, true);
        }

        public static org.semanticwb.portal.resources.sem.AudioPodcastTheme createAudioPodcastTheme(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.portal.resources.sem.AudioPodcastTheme.ClassMgr.createAudioPodcastTheme(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.portal.resources.sem.AudioPodcastTheme
       * @param id Identifier for org.semanticwb.portal.resources.sem.AudioPodcastTheme
       * @param model Model of the org.semanticwb.portal.resources.sem.AudioPodcastTheme
       * @return A org.semanticwb.portal.resources.sem.AudioPodcastTheme
       */
        public static org.semanticwb.portal.resources.sem.AudioPodcastTheme getAudioPodcastTheme(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.portal.resources.sem.AudioPodcastTheme)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.portal.resources.sem.AudioPodcastTheme
       * @param id Identifier for org.semanticwb.portal.resources.sem.AudioPodcastTheme
       * @param model Model of the org.semanticwb.portal.resources.sem.AudioPodcastTheme
       * @return A org.semanticwb.portal.resources.sem.AudioPodcastTheme
       */
        public static org.semanticwb.portal.resources.sem.AudioPodcastTheme createAudioPodcastTheme(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.portal.resources.sem.AudioPodcastTheme)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.portal.resources.sem.AudioPodcastTheme
       * @param id Identifier for org.semanticwb.portal.resources.sem.AudioPodcastTheme
       * @param model Model of the org.semanticwb.portal.resources.sem.AudioPodcastTheme
       */
        public static void removeAudioPodcastTheme(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.portal.resources.sem.AudioPodcastTheme
       * @param id Identifier for org.semanticwb.portal.resources.sem.AudioPodcastTheme
       * @param model Model of the org.semanticwb.portal.resources.sem.AudioPodcastTheme
       * @return true if the org.semanticwb.portal.resources.sem.AudioPodcastTheme exists, false otherwise
       */

        public static boolean hasAudioPodcastTheme(String id, org.semanticwb.model.SWBModel model)
        {
            return (getAudioPodcastTheme(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.portal.resources.sem.AudioPodcastTheme with a determined AudioFile
       * @param value AudioFile of the type org.semanticwb.portal.resources.sem.AudioFile
       * @param model Model of the org.semanticwb.portal.resources.sem.AudioPodcastTheme
       * @return Iterator with all the org.semanticwb.portal.resources.sem.AudioPodcastTheme
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.AudioPodcastTheme> listAudioPodcastThemeByAudioFile(org.semanticwb.portal.resources.sem.AudioFile value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.AudioPodcastTheme> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(audiopdcst_hasAudioFileInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.portal.resources.sem.AudioPodcastTheme with a determined AudioFile
       * @param value AudioFile of the type org.semanticwb.portal.resources.sem.AudioFile
       * @return Iterator with all the org.semanticwb.portal.resources.sem.AudioPodcastTheme
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.AudioPodcastTheme> listAudioPodcastThemeByAudioFile(org.semanticwb.portal.resources.sem.AudioFile value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.AudioPodcastTheme> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(audiopdcst_hasAudioFileInv,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static AudioPodcastThemeBase.ClassMgr getAudioPodcastThemeClassMgr()
    {
        return new AudioPodcastThemeBase.ClassMgr();
    }

   /**
   * Constructs a AudioPodcastThemeBase with a SemanticObject
   * @param base The SemanticObject with the properties for the AudioPodcastTheme
   */
    public AudioPodcastThemeBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
   /**
   * Gets all the org.semanticwb.portal.resources.sem.AudioFile
   * @return A GenericIterator with all the org.semanticwb.portal.resources.sem.AudioFile
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.AudioFile> listAudioFiles()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.AudioFile>(getSemanticObject().listObjectProperties(audiopdcst_hasAudioFileInv));
    }

   /**
   * Gets true if has a AudioFile
   * @param value org.semanticwb.portal.resources.sem.AudioFile to verify
   * @return true if the org.semanticwb.portal.resources.sem.AudioFile exists, false otherwise
   */
    public boolean hasAudioFile(org.semanticwb.portal.resources.sem.AudioFile value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(audiopdcst_hasAudioFileInv,value.getSemanticObject());
        }
        return ret;
    }

   /**
   * Gets the AudioFile
   * @return a org.semanticwb.portal.resources.sem.AudioFile
   */
    public org.semanticwb.portal.resources.sem.AudioFile getAudioFile()
    {
         org.semanticwb.portal.resources.sem.AudioFile ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(audiopdcst_hasAudioFileInv);
         if(obj!=null)
         {
             ret=(org.semanticwb.portal.resources.sem.AudioFile)obj.createGenericInstance();
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
}
