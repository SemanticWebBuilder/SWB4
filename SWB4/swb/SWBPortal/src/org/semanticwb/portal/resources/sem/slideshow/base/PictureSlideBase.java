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
package org.semanticwb.portal.resources.sem.slideshow.base;


public abstract class PictureSlideBase extends org.semanticwb.model.SWBClass implements org.semanticwb.model.Descriptiveable
{
   /**
   * External URL
   */
    public static final org.semanticwb.platform.SemanticProperty slideshow_externalURL=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/slideshow#externalURL");
   /**
   * Internal URL
   */
    public static final org.semanticwb.platform.SemanticProperty slideshow_internalURL=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/slideshow#internalURL");
   /**
   * Alternative descriptions. Accessibility Guidelines
   */
    public static final org.semanticwb.platform.SemanticProperty slideshow_alt=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/slideshow#alt");
   /**
   * Imagen miniatura
   */
    public static final org.semanticwb.platform.SemanticProperty slideshow_thmbn=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/slideshow#thmbn");
   /**
   * Poster
   */
    public static final org.semanticwb.platform.SemanticProperty slideshow_poster=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/slideshow#poster");
    public static final org.semanticwb.platform.SemanticClass slideshow_PictureSlide=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/slideshow#PictureSlide");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/slideshow#PictureSlide");

    public static class ClassMgr
    {
       /**
       * Returns a list of PictureSlide for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.portal.resources.sem.slideshow.PictureSlide
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.slideshow.PictureSlide> listPictureSlides(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.slideshow.PictureSlide>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.portal.resources.sem.slideshow.PictureSlide for all models
       * @return Iterator of org.semanticwb.portal.resources.sem.slideshow.PictureSlide
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.slideshow.PictureSlide> listPictureSlides()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.slideshow.PictureSlide>(it, true);
        }
       /**
       * Gets a org.semanticwb.portal.resources.sem.slideshow.PictureSlide
       * @param id Identifier for org.semanticwb.portal.resources.sem.slideshow.PictureSlide
       * @param model Model of the org.semanticwb.portal.resources.sem.slideshow.PictureSlide
       * @return A org.semanticwb.portal.resources.sem.slideshow.PictureSlide
       */
        public static org.semanticwb.portal.resources.sem.slideshow.PictureSlide getPictureSlide(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.portal.resources.sem.slideshow.PictureSlide)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.portal.resources.sem.slideshow.PictureSlide
       * @param id Identifier for org.semanticwb.portal.resources.sem.slideshow.PictureSlide
       * @param model Model of the org.semanticwb.portal.resources.sem.slideshow.PictureSlide
       * @return A org.semanticwb.portal.resources.sem.slideshow.PictureSlide
       */
        public static org.semanticwb.portal.resources.sem.slideshow.PictureSlide createPictureSlide(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.portal.resources.sem.slideshow.PictureSlide)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.portal.resources.sem.slideshow.PictureSlide
       * @param id Identifier for org.semanticwb.portal.resources.sem.slideshow.PictureSlide
       * @param model Model of the org.semanticwb.portal.resources.sem.slideshow.PictureSlide
       */
        public static void removePictureSlide(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.portal.resources.sem.slideshow.PictureSlide
       * @param id Identifier for org.semanticwb.portal.resources.sem.slideshow.PictureSlide
       * @param model Model of the org.semanticwb.portal.resources.sem.slideshow.PictureSlide
       * @return true if the org.semanticwb.portal.resources.sem.slideshow.PictureSlide exists, false otherwise
       */

        public static boolean hasPictureSlide(String id, org.semanticwb.model.SWBModel model)
        {
            return (getPictureSlide(id, model)!=null);
        }
    }

   /**
   * Constructs a PictureSlideBase with a SemanticObject
   * @param base The SemanticObject with the properties for the PictureSlide
   */
    public PictureSlideBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

/**
* Gets the ExternalURL property
* @return String with the ExternalURL
*/
    public String getExternalURL()
    {
        return getSemanticObject().getProperty(slideshow_externalURL);
    }

/**
* Sets the ExternalURL property
* @param value long with the ExternalURL
*/
    public void setExternalURL(String value)
    {
        getSemanticObject().setProperty(slideshow_externalURL, value);
    }

/**
* Gets the InternalURL property
* @return String with the InternalURL
*/
    public String getInternalURL()
    {
        return getSemanticObject().getProperty(slideshow_internalURL);
    }

/**
* Sets the InternalURL property
* @param value long with the InternalURL
*/
    public void setInternalURL(String value)
    {
        getSemanticObject().setProperty(slideshow_internalURL, value);
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
* Gets the Alt property
* @return String with the Alt
*/
    public String getAlt()
    {
        return getSemanticObject().getProperty(slideshow_alt);
    }

/**
* Sets the Alt property
* @param value long with the Alt
*/
    public void setAlt(String value)
    {
        getSemanticObject().setProperty(slideshow_alt, value);
    }

    public String getAlt(String lang)
    {
        return getSemanticObject().getProperty(slideshow_alt, null, lang);
    }

    public String getDisplayAlt(String lang)
    {
        return getSemanticObject().getLocaleProperty(slideshow_alt, lang);
    }

    public void setAlt(String alt, String lang)
    {
        getSemanticObject().setProperty(slideshow_alt, alt, lang);
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
* Gets the Thmbn property
* @return String with the Thmbn
*/
    public String getThmbn()
    {
        return getSemanticObject().getProperty(slideshow_thmbn);
    }

/**
* Sets the Thmbn property
* @param value long with the Thmbn
*/
    public void setThmbn(String value)
    {
        getSemanticObject().setProperty(slideshow_thmbn, value);
    }

/**
* Gets the Poster property
* @return String with the Poster
*/
    public String getPoster()
    {
        return getSemanticObject().getProperty(slideshow_poster);
    }

/**
* Sets the Poster property
* @param value long with the Poster
*/
    public void setPoster(String value)
    {
        getSemanticObject().setProperty(slideshow_poster, value);
    }
}
