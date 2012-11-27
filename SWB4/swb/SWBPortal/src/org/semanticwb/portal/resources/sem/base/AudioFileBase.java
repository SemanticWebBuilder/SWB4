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


public abstract class AudioFileBase extends org.semanticwb.model.SWBClass implements org.semanticwb.model.Rankable,org.semanticwb.model.Traceable,org.semanticwb.model.Expirable,org.semanticwb.model.Descriptiveable,org.semanticwb.model.Iconable
{
   /**
   * El audio reproduce automáticamente
   */
    public static final org.semanticwb.platform.SemanticProperty audiof_autoplay=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/portal/resources/sems/SWBAudioFile#autoplay");
   /**
   * Participante del contenido
   */
    public static final org.semanticwb.platform.SemanticProperty audiof_author=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/portal/resources/sems/SWBAudioFile#author");
   /**
   * Mostrar consola
   */
    public static final org.semanticwb.platform.SemanticProperty audiof_showcontroller=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/portal/resources/sems/SWBAudioFile#showcontroller");
   /**
   * Length of podcast
   */
    public static final org.semanticwb.platform.SemanticProperty audiof_duration=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/portal/resources/sems/SWBAudioFile#duration");
   /**
   * Nombre del recurso
   */
    public static final org.semanticwb.platform.SemanticProperty audiof_filename=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/portal/resources/sems/SWBAudioFile#filename");
    public static final org.semanticwb.platform.SemanticClass audiof_AudioFile=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/portal/resources/sems/SWBAudioFile#AudioFile");
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
* Gets the Autoplay property
* @return boolean with the Autoplay
*/
    public boolean isAutoplay()
    {
        return getSemanticObject().getBooleanProperty(audiof_autoplay);
    }

/**
* Sets the Autoplay property
* @param value long with the Autoplay
*/
    public void setAutoplay(boolean value)
    {
        getSemanticObject().setBooleanProperty(audiof_autoplay, value);
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
        return getSemanticObject().getProperty(audiof_author);
    }

/**
* Sets the Author property
* @param value long with the Author
*/
    public void setAuthor(String value)
    {
        getSemanticObject().setProperty(audiof_author, value);
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
* Gets the Showcontroller property
* @return boolean with the Showcontroller
*/
    public boolean isShowcontroller()
    {
        return getSemanticObject().getBooleanProperty(audiof_showcontroller);
    }

/**
* Sets the Showcontroller property
* @param value long with the Showcontroller
*/
    public void setShowcontroller(boolean value)
    {
        getSemanticObject().setBooleanProperty(audiof_showcontroller, value);
    }

/**
* Gets the Duration property
* @return String with the Duration
*/
    public String getDuration()
    {
        return getSemanticObject().getProperty(audiof_duration);
    }

/**
* Sets the Duration property
* @param value long with the Duration
*/
    public void setDuration(String value)
    {
        getSemanticObject().setProperty(audiof_duration, value);
    }

/**
* Gets the Filename property
* @return String with the Filename
*/
    public String getFilename()
    {
        return getSemanticObject().getProperty(audiof_filename);
    }

/**
* Sets the Filename property
* @param value long with the Filename
*/
    public void setFilename(String value)
    {
        getSemanticObject().setProperty(audiof_filename, value);
    }
}
