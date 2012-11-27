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
package org.semanticwb.portal.resources.wiki;

import info.bliki.wiki.filter.Encoder;
import info.bliki.wiki.model.Configuration;
import info.bliki.wiki.model.ImageFormat;
import info.bliki.wiki.tags.WPATag;
import java.util.Locale;
import org.htmlcleaner.ContentToken;
import org.semanticwb.SWBPlatform;
import org.semanticwb.model.WebPage;
import org.semanticwb.portal.api.SWBParamRequest;

// TODO: Auto-generated Javadoc
/**
 * The Class WikiModel.
 * 
 * @author Javier Solis Gonzalez
 */
public class WikiModel extends info.bliki.wiki.model.WikiModel
{
    
    /** The params request. */
    private SWBParamRequest paramsRequest;
    
    /** The request. */
    private javax.servlet.http.HttpServletRequest request;
    
    /**
     * Instantiates a new wiki model.
     * 
     * @param imageBaseURL the image base url
     * @param linkBaseURL the link base url
     */
    public WikiModel(String imageBaseURL, String linkBaseURL) 
    {
        super(imageBaseURL, linkBaseURL);
    }
    
    /**
     * Instantiates a new wiki model.
     * 
     * @param request the request
     * @param paramsRequest the params request
     * @param imageBaseURL the image base url
     * @param linkBaseURL the link base url
     */
    public WikiModel(javax.servlet.http.HttpServletRequest request, SWBParamRequest paramsRequest, String imageBaseURL, String linkBaseURL)
    {
        super(Configuration.DEFAULT_CONFIGURATION, new Locale(paramsRequest.getUser().getLanguage()), imageBaseURL, linkBaseURL);
        this.paramsRequest=paramsRequest;
        this.request=request;
        Configuration.DEFAULT_CONFIGURATION.addTokenTag("resource",new ResourceTag());
    }

    
    /* (non-Javadoc)
     * @see info.bliki.wiki.model.WikiModel#appendInternalLink(java.lang.String, java.lang.String, java.lang.String)
     */
    /**
     * Append internal link.
     * 
     * @param link the link
     * @param hashSection the hash section
     * @param linkText the link text
     */
    @Override
    public void appendInternalLink(String link, String hashSection, String linkText) 
    {
            String encodedtopic="";
            WebPage topic=null;
            String lang="es";
            if(paramsRequest!=null)lang=paramsRequest.getUser().getLanguage();
            if(link.indexOf(':')>=0)
            {
                encodedtopic= Encoder.encodeTitleUrl(link);
            }else
            {
                encodedtopic = SWBPlatform.getIDGenerator().getID(link,null,false);
                if(paramsRequest!=null)topic=paramsRequest.getWebPage().getWebSite().getWebPage(encodedtopic);
            }

            if (replaceColon()) 
            {
                    encodedtopic = encodedtopic.replaceAll(":", "/");
            }
            String hrefLink = fExternalWikiBaseURL.replace("${title}", encodedtopic);
            
            WPATag aTagNode = new WPATag();
            append(aTagNode);
            aTagNode.addAttribute("id", "w", true);
            String href = hrefLink;
            if (hashSection != null) {
                    href = href + '#' + hashSection;
            }
            aTagNode.addAttribute("href", href, true);
            aTagNode.addObjectAttribute("wikilink", link);

            //System.out.println("link:"+link+" "+hashSection+" "+linkText);
            if(topic!=null && link.equals(linkText))linkText=topic.getDisplayName(lang);
            
            ContentToken text = new ContentToken(linkText);
            aTagNode.addChild(text);            
    }
    
    /* (non-Javadoc)
     * @see info.bliki.wiki.model.AbstractWikiModel#appendInternalImageLink(java.lang.String, java.lang.String, info.bliki.wiki.model.ImageFormat)
     */
    /**
     * Append internal image link.
     * 
     * @param hrefImageLink the href image link
     * @param srcImageLink the src image link
     * @param imageFormat the image format
     */
    @Override
    public void appendInternalImageLink(String hrefImageLink, String srcImageLink, ImageFormat imageFormat)
    {
        int i=hrefImageLink.indexOf(':');
        String ref=hrefImageLink;
        if(i>=0)ref=hrefImageLink.substring(i+1);
        //System.out.println("hrefImageLink:"+hrefImageLink+" "+ref+" "+srcImageLink+" "+imageFormat);
        super.appendInternalImageLink(ref, srcImageLink, imageFormat);
    }

    /**
     * Gets the params request.
     * 
     * @return the params request
     */
    public SWBParamRequest getParamsRequest()
    {
        return paramsRequest;
    }

    /**
     * Gets the request.
     * 
     * @return the request
     */
    public javax.servlet.http.HttpServletRequest getRequest()
    {
        return request;
    }
    
}
