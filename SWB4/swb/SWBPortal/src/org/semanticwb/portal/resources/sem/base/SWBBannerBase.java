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


// TODO: Auto-generated Javadoc
/**
 * The Class SWBBannerBase.
 */
public class SWBBannerBase extends org.semanticwb.portal.api.GenericSemResource 
{
    
    /** The Constant swbbanner_width. */
    public static final org.semanticwb.platform.SemanticProperty swbbanner_width=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/portal/resources/SWBBanner#width");
    
    /** The Constant swbbanner_height. */
    public static final org.semanticwb.platform.SemanticProperty swbbanner_height=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/portal/resources/SWBBanner#height");
    
    /** The Constant swbbanner_image. */
    public static final org.semanticwb.platform.SemanticProperty swbbanner_image=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/portal/resources/SWBBanner#image");
    
    /** The Constant swbbanner_SWBBanner. */
    public static final org.semanticwb.platform.SemanticClass swbbanner_SWBBanner=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/portal/resources/SWBBanner#SWBBanner");

    /**
     * Instantiates a new sWB banner base.
     */
    public SWBBannerBase()
    {
    }

    /**
     * Instantiates a new sWB banner base.
     * 
     * @param base the base
     */
    public SWBBannerBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
    
    /** The Constant sclass. */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/portal/resources/SWBBanner#SWBBanner");

    /**
     * Gets the width.
     * 
     * @return the width
     */
    public int getWidth()
    {
        return getSemanticObject().getIntProperty(swbbanner_width);
    }

    /**
     * Sets the width.
     * 
     * @param width the new width
     */
    public void setWidth(int width)
    {
        getSemanticObject().setLongProperty(swbbanner_width, width);
    }

    /**
     * Gets the height.
     * 
     * @return the height
     */
    public int getHeight()
    {
        return getSemanticObject().getIntProperty(swbbanner_height);
    }

    /**
     * Sets the height.
     * 
     * @param height the new height
     */
    public void setHeight(int height)
    {
        getSemanticObject().setLongProperty(swbbanner_height, height);
    }

    /**
     * Gets the image.
     * 
     * @return the image
     */
    public String getImage()
    {
        return getSemanticObject().getProperty(swbbanner_image);
    }

    /**
     * Sets the image.
     * 
     * @param image the new image
     */
    public void setImage(String image)
    {
        getSemanticObject().setProperty(swbbanner_image, image);
    }
}
