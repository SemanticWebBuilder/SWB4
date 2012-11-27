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
package org.semanticwb.portal.resources.sem.video.base;


// TODO: Auto-generated Javadoc
/**
 * The Class SWBVideoResourceBase.
 */
public abstract class SWBVideoResourceBase extends org.semanticwb.portal.api.GenericSemResource 
{
    
    /** The Constant swbv_VideoElement. */
    public static final org.semanticwb.platform.SemanticClass swbv_VideoElement=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwb.org/SWBVideo#VideoElement");
    
    /** The Constant swb_hasVideo. */
    public static final org.semanticwb.platform.SemanticProperty swb_hasVideo=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#hasVideo");
    
    /** The Constant swbv_SWBVideoResource. */
    public static final org.semanticwb.platform.SemanticClass swbv_SWBVideoResource=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwb.org/SWBVideo#SWBVideoResource");
    
    /** The Constant sclass. */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwb.org/SWBVideo#SWBVideoResource");

    /**
     * Instantiates a new sWB video resource base.
     */
    public SWBVideoResourceBase()
    {
    }

    /**
     * Instantiates a new sWB video resource base.
     * 
     * @param base the base
     */
    public SWBVideoResourceBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    /**
     * List videos.
     * 
     * @return the org.semanticwb.model. generic iterator
     */
    public org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.video.VideoElement> listVideos()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.video.VideoElement>(getSemanticObject().listObjectProperties(swb_hasVideo));
    }

    /**
     * Checks for video.
     * 
     * @param value the value
     * @return true, if successful
     */
    public boolean hasVideo(org.semanticwb.portal.resources.sem.video.VideoElement value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swb_hasVideo,value.getSemanticObject());
        }
        return ret;
    }

    /**
     * Adds the video.
     * 
     * @param value the value
     */
    public void addVideo(org.semanticwb.portal.resources.sem.video.VideoElement value)
    {
        getSemanticObject().addObjectProperty(swb_hasVideo, value.getSemanticObject());
    }

    /**
     * Removes the all video.
     */
    public void removeAllVideo()
    {
        getSemanticObject().removeProperty(swb_hasVideo);
    }

    /**
     * Removes the video.
     * 
     * @param value the value
     */
    public void removeVideo(org.semanticwb.portal.resources.sem.video.VideoElement value)
    {
        getSemanticObject().removeObjectProperty(swb_hasVideo,value.getSemanticObject());
    }

    /**
     * Gets the video.
     * 
     * @return the video
     */
    public org.semanticwb.portal.resources.sem.video.VideoElement getVideo()
    {
         org.semanticwb.portal.resources.sem.video.VideoElement ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_hasVideo);
         if(obj!=null)
         {
             ret=(org.semanticwb.portal.resources.sem.video.VideoElement)obj.createGenericInstance();
         }
         return ret;
    }
}
