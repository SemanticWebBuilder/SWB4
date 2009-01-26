/*
 * INFOTEC WebBuilder es una herramienta para el desarrollo de portales de conocimiento, colaboraci�n e integraci�n para Internet,
 * la cual, es una creaci�n original del Fondo de Informaci�n y Documentaci�n para la Industria INFOTEC, misma que se encuentra
 * debidamente registrada ante el Registro P�blico del Derecho de Autor de los Estados Unidos Mexicanos con el
 * No. 03-2002-052312015400-14, para la versi�n 1; No. 03-2003-012112473900 para la versi�n 2, y No. 03-2006-012012004000-01
 * para la versi�n 3, respectivamente.
 *
 * INFOTEC pone a su disposici�n la herramienta INFOTEC WebBuilder a trav�s de su licenciamiento abierto al p�blico (�open source�),
 * en virtud del cual, usted podr� usarlo en las mismas condiciones con que INFOTEC lo ha dise�ado y puesto a su disposici�n;
 * aprender de �l; distribuirlo a terceros; acceder a su c�digo fuente y modificarlo, y combinarlo o enlazarlo con otro software,
 * todo ello de conformidad con los t�rminos y condiciones de la LICENCIA ABIERTA AL P�BLICO que otorga INFOTEC para la utilizaci�n
 * de INFOTEC WebBuilder 3.2.
 *
 * INFOTEC no otorga garant�a sobre INFOTEC WebBuilder, de ninguna especie y naturaleza, ni impl�cita ni expl�cita,
 * siendo usted completamente responsable de la utilizaci�n que le d� y asumiendo la totalidad de los riesgos que puedan derivar
 * de la misma.
 *
 * Si usted tiene cualquier duda o comentario sobre INFOTEC WebBuilder, INFOTEC pone a su disposici�n la siguiente
 * direcci�n electr�nica:
 *
 *                                          http://www.webbuilder.org.mx
 */

/*
 * WikiModel.java
 *
 * Created on 28 de mayo de 2008, 08:30 PM
 *
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

/**
 *
 * @author Javier Solis Gonzalez
 */
public class WikiModel extends info.bliki.wiki.model.WikiModel
{
    private SWBParamRequest paramsRequest;
    private javax.servlet.http.HttpServletRequest request;
    
    public WikiModel(String imageBaseURL, String linkBaseURL) 
    {
        super(imageBaseURL, linkBaseURL);
    }
    
    public WikiModel(javax.servlet.http.HttpServletRequest request, SWBParamRequest paramsRequest, String imageBaseURL, String linkBaseURL)
    {
        super(Configuration.DEFAULT_CONFIGURATION, new Locale(paramsRequest.getUser().getLanguage()), imageBaseURL, linkBaseURL);
        this.paramsRequest=paramsRequest;
        this.request=request;
        Configuration.DEFAULT_CONFIGURATION.addTokenTag("resource",new ResourceTag());
    }

    
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
                if(paramsRequest!=null)topic=paramsRequest.getTopic().getWebSite().getWebPage(encodedtopic);
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
    
    @Override
    public void appendInternalImageLink(String hrefImageLink, String srcImageLink, ImageFormat imageFormat)
    {
        int i=hrefImageLink.indexOf(':');
        String ref=hrefImageLink;
        if(i>=0)ref=hrefImageLink.substring(i+1);
        //System.out.println("hrefImageLink:"+hrefImageLink+" "+ref+" "+srcImageLink+" "+imageFormat);
        super.appendInternalImageLink(ref, srcImageLink, imageFormat);
    }

    public SWBParamRequest getParamsRequest()
    {
        return paramsRequest;
    }

    public javax.servlet.http.HttpServletRequest getRequest()
    {
        return request;
    }
    
}
