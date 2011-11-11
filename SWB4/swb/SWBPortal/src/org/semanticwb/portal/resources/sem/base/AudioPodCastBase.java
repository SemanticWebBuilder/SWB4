package org.semanticwb.portal.resources.sem.base;


public abstract class AudioPodCastBase extends org.semanticwb.portal.api.GenericSemResource 
{
    public static final org.semanticwb.platform.SemanticClass swb_Resource=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Resource");
    public static final org.semanticwb.platform.SemanticProperty swb_semanticResourceInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#semanticResourceInv");
    public static final org.semanticwb.platform.SemanticClass audiof_AudioFile=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/portal/resources/sems/SWBAudioFile#AudioFile");
    public static final org.semanticwb.platform.SemanticProperty audiof_hasAudioFile=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/portal/resources/sems/SWBAudioFile#hasAudioFile");
    public static final org.semanticwb.platform.SemanticClass audiof_AudioPodCast=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/portal/resources/sems/SWBAudioFile#AudioPodCast");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/portal/resources/sems/SWBAudioFile#AudioPodCast");

    public AudioPodCastBase()
    {
    }

   /**
   * Constructs a AudioPodCastBase with a SemanticObject
   * @param base The SemanticObject with the properties for the AudioPodCast
   */
    public AudioPodCastBase(org.semanticwb.platform.SemanticObject base)
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
   * Gets all the org.semanticwb.portal.resources.sem.AudioFile
   * @return A GenericIterator with all the org.semanticwb.portal.resources.sem.AudioFile
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.AudioFile> listAudioFiles()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.AudioFile>(getSemanticObject().listObjectProperties(audiof_hasAudioFile));
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
           ret=getSemanticObject().hasObjectProperty(audiof_hasAudioFile,value.getSemanticObject());
        }
        return ret;
    }
   /**
   * Adds a AudioFile
   * @param value org.semanticwb.portal.resources.sem.AudioFile to add
   */

    public void addAudioFile(org.semanticwb.portal.resources.sem.AudioFile value)
    {
        getSemanticObject().addObjectProperty(audiof_hasAudioFile, value.getSemanticObject());
    }
   /**
   * Removes all the AudioFile
   */

    public void removeAllAudioFile()
    {
        getSemanticObject().removeProperty(audiof_hasAudioFile);
    }
   /**
   * Removes a AudioFile
   * @param value org.semanticwb.portal.resources.sem.AudioFile to remove
   */

    public void removeAudioFile(org.semanticwb.portal.resources.sem.AudioFile value)
    {
        getSemanticObject().removeObjectProperty(audiof_hasAudioFile,value.getSemanticObject());
    }

   /**
   * Gets the AudioFile
   * @return a org.semanticwb.portal.resources.sem.AudioFile
   */
    public org.semanticwb.portal.resources.sem.AudioFile getAudioFile()
    {
         org.semanticwb.portal.resources.sem.AudioFile ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(audiof_hasAudioFile);
         if(obj!=null)
         {
             ret=(org.semanticwb.portal.resources.sem.AudioFile)obj.createGenericInstance();
         }
         return ret;
    }
}
