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
package org.semanticwb.portal.resources;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;
import org.semanticwb.Logger;
import org.semanticwb.SWBException;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.AssMember;
import org.semanticwb.model.Association;
import org.semanticwb.model.Resource;
import org.semanticwb.model.SWBComparator;
import org.semanticwb.model.Topic;
import org.semanticwb.model.User;
import org.semanticwb.model.WebPage;
import org.semanticwb.model.WebSite;
import org.semanticwb.portal.api.GenericAdmResource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

// TODO: Auto-generated Javadoc
/** Esta clase se encarga de desplegar y administrar topicos relacionados bajo ciertos
 * criterios de configuraci�n. Utiliza un archivo XSL.
 *
 * This class is in charge to unfold and to manages related topics under certain
 * criteria and configuration. Uses an XSL file.
 * @author : Infotec
 * @since : September 4th 2002, 13:19
 */

public class RelatedTopics extends GenericAdmResource 
{
	
	/** The log. */
	private static Logger log = SWBUtils.getLogger(RelatedTopics.class);
    
    /** The tpl. */
    javax.xml.transform.Templates tpl;
    
    /** The path. */
    String path = SWBPlatform.getContextPath() +"/swbadmin/xsl/SemRelatedTopics/";
	
    /**
     * Crea un nuevo objeto EgobRelatedTopics.
     */
    public RelatedTopics() {
    }
	
    /**
     * Asigna la informaciÛn de la base de datos al recurso.
     * 
     * @param base La informaciÛn del recurso en memoria.
     */
    public void setResourceBase(Resource base)
    {
        try  { super.setResourceBase(base); }
        catch(Exception e) { log.error("Error while setting resource base: "+base.getId() +"-"+ base.getTitle(), e);  }
        if(!"".equals(base.getAttribute("template","").trim()))
        {
            try
            {
                tpl = SWBUtils.XML.loadTemplateXSLT(SWBUtils.IO.getStreamFromString(SWBUtils.IO.getFileFromPath(base.getWorkPath() +"/"+ base.getAttribute("template").trim())));
                path=SWBPortal.getWebWorkPath() +  base.getWorkPath() + "/";
            }
            catch(Exception e) { log.error("Error while loading resource template: "+base.getId(), e); }
        }
        if(tpl==null)
        {
            try { tpl = SWBUtils.XML.loadTemplateXSLT(SWBPortal.getAdminFileStream("/swbadmin/xsl/SemRelatedTopics/RelatedTopics.xslt")); }
            catch(Exception e) { log.error("Error while loading default resource template: "+base.getId(), e); }
        }
    }
	
    /**
     * Gets the dom.
     * 
     * @param request the request
     * @param response the response
     * @param paramsRequest the params request
     * @return the dom
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws SWBException the sWB exception
     */
    public org.w3c.dom.Document getDom(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response, SWBParamRequest paramsRequest) throws SWBException, java.io.IOException
    {
        Resource base=getResourceBase();
        try
        {
            User user = paramsRequest.getUser();
            Document  dom = SWBUtils.XML.getNewDocument();
            Element el = dom.createElement("reltopics");
            if(!"".equals(base.getAttribute("title", "").trim()))  el.setAttribute("title", base.getAttribute("title").trim());
            el.setAttribute("path", path);
            if(paramsRequest.getArguments().get("link")!=null) el.setAttribute("link", (String)paramsRequest.getArguments().get("link"));
            dom.appendChild(el);
			
            WebSite tm = paramsRequest.getWebPage().getWebSite();
			//  WebPage scope = tm.getTopicLang(user.getLanguage());
            WebPage tpid = null;
            if (paramsRequest.getArguments().get("topic") != null) {
                tpid = tm.getWebPage((String) paramsRequest.getArguments().get("topic"));
            }
            else tpid = tm.getWebPage(paramsRequest.getWebPage().getId());
			
            HashMap sort = new HashMap();
            Topic topicofSort = null;
            String strSort = base.getAttribute("sort", "0");
			
            Iterator<AssMember> it = tpid.listAssMembers(); //getAssociations().iterator();
            while (it.hasNext())
            {
                AssMember ass = it.next();
                if (ass != null)
                {   Association cass = ass.getAssociation();
                    Iterator<AssMember> mem = cass.listMembers(); //getMembers().iterator();
                    while (mem.hasNext())
                    {
                        AssMember m = mem.next();
                        if (!m.getMember().getId().equals(tpid.getId())) //getTopicRefs().containsValue(tpid))
                        {
                            if (strSort.equals("1")) {   // Ordenar por role
                                topicofSort = m.getRole();// m.getRoleSpec().getTopicRef();
                            }
                            else { // Default: ordernar pot tipo de asociaciÛn
                                topicofSort = cass.getType(); //getType();
                            }
                            if (sort.containsKey(topicofSort))
                            {
                                Set arr = (TreeSet) sort.get(topicofSort);
                                arr.add(m.getMember());// getTopicRefs().values());
                            }
                            else
                            {
                                Set arr = new TreeSet(new SWBComparator(user.getLanguage()));
                                sort.put(topicofSort, arr);
                                arr.add(m.getMember());
                            }
                        }
                    }
                }
            }
            if (!sort.isEmpty())
            {
                try
                {
                    String target=base.getAttribute("target", "0").trim();
                    Set s = new TreeSet(new SWBComparator(user.getLanguage()));
                    s.addAll(sort.keySet());
                    Iterator ind = s.iterator();
                    while (ind.hasNext())
                    {
                        WebPage t = (WebPage) ind.next();
                        el = dom.createElement("padre");
                        el.setAttribute("nombre", t.getDisplayName(user.getLanguage()));
						//                        if(t.getIcon()!=null){
						//                            el.setAttribute("icon",t.getIcon());
						//                        }
                        dom.getChildNodes().item(0).appendChild(el);
						
                        Iterator itt = ((Set) sort.get(t)).iterator();
                        while (itt.hasNext())
                        {
                            WebPage tpla = (WebPage) itt.next();
                            // Validar si queremos comprobar acceso...
                            if (user.haveAccess(tpla) && tpla.isActive())
                            {
                                Element hijo = dom.createElement("hijo");
                                hijo.setAttribute("url", tpla.getUrl(user.getLanguage(),false));
                                hijo.setAttribute("nombre", tpla.getDisplayName(user.getLanguage()));
								//                                if(tpla.getIcon() !=null ) {
								//                                    hijo.setAttribute("icon", tpla.getIcon());
								//                                }
                                hijo.setAttribute("target", target);
                                el.appendChild(hijo);
                            }
                        }
                    }
                }
                catch (Exception e) { log.error("Error while adding elements in resource "+ getResourceBase().getTitle(), e); }
            }
            return dom;
        }
        catch (Exception e) { log.error("Error while generating DOM in resource "+ getResourceBase().getTitle(), e); }
        return null;
    }
	
    /**
     * Do xml.
     * 
     * @param request the request
     * @param response the response
     * @param paramsRequest the params request
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @Override
    public void doXML(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response, SWBParamRequest paramsRequest) throws java.io.IOException
    {
        try
        {
            Document dom=getDom(request, response, paramsRequest);
            if(dom!=null) response.getWriter().println(SWBUtils.XML.domToXml(dom));
        }
        catch(Exception e){ log.error(e); }
    }
	
    /**
     * Do view.
     * 
     * @param request the request
     * @param response the response
     * @param reqParams the req params
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @Override
    public void doView(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response, SWBParamRequest reqParams) throws java.io.IOException
    {
        try
        {
            Document dom =getDom(request, response, reqParams);
            //System.out.println(AFUtils.getInstance().DomtoXml(dom));
            if(dom != null)  response.getWriter().print(SWBUtils.XML.transformDom(tpl, dom));
        }
        catch(Exception e) { log.error(e); }
    }
}
