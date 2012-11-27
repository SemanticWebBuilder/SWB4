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
 * The Class SWBStaticTextBase.
 */
public class SWBStaticTextBase extends org.semanticwb.portal.api.GenericSemResource 
{
    
    /** The Constant swbstatictext_text. */
    public static final org.semanticwb.platform.SemanticProperty swbstatictext_text=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/portal/resources/StaticText#text");
    
    /** The Constant swbstatictext_SWBStaticText. */
    public static final org.semanticwb.platform.SemanticClass swbstatictext_SWBStaticText=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/portal/resources/StaticText#SWBStaticText");

    /**
     * Instantiates a new sWB static text base.
     */
    public SWBStaticTextBase()
    {
    }

    /**
     * Instantiates a new sWB static text base.
     * 
     * @param base the base
     */
    public SWBStaticTextBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
    
    /** The Constant sclass. */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/portal/resources/StaticText#SWBStaticText");

    /**
     * Gets the text.
     * 
     * @return the text
     */
    public String getText()
    {
        return getSemanticObject().getProperty(swbstatictext_text);
    }

    /**
     * Sets the text.
     * 
     * @param text the new text
     */
    public void setText(String text)
    {
        getSemanticObject().setProperty(swbstatictext_text, text);
    }
}
