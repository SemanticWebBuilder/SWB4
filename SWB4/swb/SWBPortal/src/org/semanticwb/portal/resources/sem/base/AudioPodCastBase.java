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
