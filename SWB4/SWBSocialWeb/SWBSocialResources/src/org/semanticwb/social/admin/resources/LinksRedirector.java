/**  
* SWB Social es una plataforma que descentraliza la publicación, seguimiento y monitoreo hacia las principales redes sociales. 
* SWB Social escucha y entiende opiniones acerca de una organización, sus productos, sus servicios e inclusive de su competencia, 
* detectando en la información sentimientos, influencia, geolocalización e idioma, entre mucha más información relevante que puede ser 
* útil para la toma de decisiones. 
* 
* SWB Social, es una herramienta basada en la plataforma SemanticWebBuilder. SWB Social, como SemanticWebBuilder, es una creación original 
* del Fondo de Información y Documentación para la Industria INFOTEC, cuyo registro se encuentra actualmente en trámite. 
* 
* INFOTEC pone a su disposición la herramienta SWB Social a través de su licenciamiento abierto al público (‘open source’), 
* en virtud del cual, usted podrá usarla en las mismas condiciones con que INFOTEC la ha diseñado y puesto a su disposición; 
* aprender de élla; distribuirla a terceros; acceder a su código fuente y modificarla, y combinarla o enlazarla con otro software, 
* todo ello de conformidad con los términos y condiciones de la LICENCIA ABIERTA AL PÚBLICO que otorga INFOTEC para la utilización 
* del SemanticWebBuilder 4.0. y SWB Social 1.0
* 
* INFOTEC no otorga garantía sobre SWB Social, de ninguna especie y naturaleza, ni implícita ni explícita, 
* siendo usted completamente responsable de la utilización que le dé y asumiendo la totalidad de los riesgos que puedan derivar 
* de la misma. 
* 
* Si usted tiene cualquier duda o comentario sobre SemanticWebBuilder o SWB Social, INFOTEC pone a su disposición la siguiente 
* dirección electrónica: 
*  http://www.semanticwebbuilder.org
**/ 
 
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.social.admin.resources;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.SWBPortal;
import org.semanticwb.model.WebSite;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.social.PostOutLinksHits;
import org.semanticwb.social.PostOutLinksHitsIp;
import org.semanticwb.social.PostOut;
import org.semanticwb.social.SocialNetwork;

/**
 *
 * @author jorge.jimenez
 */
public class LinksRedirector extends GenericResource {

    //private static Logger log = SWBUtils.getLogger(LinksRedirector.class);

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        ////System.out.println("Entra a LinksRedirector/doView/uri:"+request.getParameter("uri")+", neturi:"+request.getParameter("neturi")+", code:"+request.getParameter("code"));
        if(request.getParameter("uri")!=null && request.getParameter("neturi")!=null && request.getParameter("code")!=null)
        {
            SemanticObject semObj=SemanticObject.createSemanticObject(request.getParameter("uri"));
            if(semObj!=null)
            {
                PostOut postOut=(PostOut)semObj.createGenericInstance(); 
                ////System.out.println("Entra a LinksRedirector/doView/postOut:"+postOut);
                if(postOut!=null)
                {
                    SemanticObject semObjNet=SemanticObject.createSemanticObject(request.getParameter("neturi"));
                    SocialNetwork socialNetwork=(SocialNetwork)semObjNet.createGenericInstance();
                    if(socialNetwork!=null)
                    {
                        WebSite wsite=WebSite.ClassMgr.getWebSite(semObj.getModel().getName());
                        Iterator<PostOutLinksHits> itLinks=PostOutLinksHits.ClassMgr.listPostOutLinksHitsByPostOut(postOut, wsite);
                        while(itLinks.hasNext())
                        {
                            PostOutLinksHits link=itLinks.next();
                            ////System.out.println("Entra a LinksRedirector/doView/link:"+link);
                            if(link.getSocialNet()!=null && link.getSocialNet().getURI().equals(socialNetwork.getURI()) && link.getPol_code().equals(request.getParameter("code")))
                            {
                                link.setPol_hits(link.getPol_hits()+1);
                                //Ver si guardo o no la ip de cada usuario que da click en una liga en cada red, pudieran ser millones de triples a generar
                                //pero por otro lado puede ser un buen dato, despues revisar si la red social me puede enviar el user de la red social
                                //y yo pueda extraer sus datos, esto sería mas valioso, pero no lo tengo por el momento.
                                //Por el momento con la ip de los usuarios podría saber su ubicación y talvez ubicarlos en un mapa de google maps. (No esta por el mpmento)
                                if(SWBPortal.getEnv("swbsocial/allowLinkHitsUserInfo", "false").equalsIgnoreCase("true"))
                                {
                                    ////System.out.println("Remote Addres:"+request.getRemoteAddr());
                                    /*
                                    Enumeration<String> enHeaders=request.getHeaderNames();
                                    while(enHeaders.hasMoreElements())
                                    {
                                        String headerName=enHeaders.nextElement();
                                        //System.out.println("headerName:"+headerName+",value:"+request.getHeader(headerName));
                                    }
                                    Enumeration<String> enAttrs=request.getAttributeNames();
                                    while(enAttrs.hasMoreElements())
                                    {
                                        String attr=enAttrs.nextElement();
                                        //System.out.println("attr:"+attr+",value:"+request.getAttribute(attr));
                                    }
                                    * */
                                    
                                    PostOutLinksHitsIp postOutHitIp=PostOutLinksHitsIp.ClassMgr.createPostOutLinksHitsIp(wsite); 
                                    postOutHitIp.setUserIP(request.getRemoteAddr());
                                    link.addUserIp(postOutHitIp);
                                }

                                PrintWriter out=response.getWriter(); 
                                ////System.out.println("Entra a LinksRedirector/doView/REDIRECT A:"+link.getTargetUrl());
                                out.println("<meta http-equiv=\"refresh\" content=\"0;url="+link.getTargetUrl()+"\">");
                                break;
                           }
                        }
                    }
                }
            }
        }

    }
}
