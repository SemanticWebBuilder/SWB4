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

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
//import org.apache.poi.hpsf.Variant;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Resource;
import org.semanticwb.model.WebPage;
import org.semanticwb.model.WebSite;
import org.semanticwb.portal.api.GenericAdmResource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.util.StringSearch;
import org.w3c.dom.Document;
import org.w3c.dom.Element;



// TODO: Auto-generated Javadoc
/** Esta clase se encarga de desplegar y administrar el buscador del sitio bajo ciertos
 * criterios de configuraci�n. Viene de la versi�n 2 de WebBuilder.
 *
 * This class displays and manages prospector resource under some comfiguration
 * criteria. This resource comes from WebBuilder 2.
 * @author : Javier Solis
 * @since : June 20th 2002, 16:43
 * @modified : Jorge Alberto Jimenez
 */

public class Search extends GenericAdmResource
{
    
    /** The log. */
    private static Logger log = SWBUtils.getLogger(Search.class);
    
    
    /** The tpl. */
    javax.xml.transform.Templates tpl; 
    
    /** The path. */
    String path = SWBPlatform.getContextPath() + "swbadmin/xsl/Search/";

    /**
     * Crea un nuevo objeto Search.
     */
    public Search() {
    }

    /**
     * Asigna la informaci�n de la base de datos al recurso.
     * 
     * @param base the new resource base
     */
    @Override
    public void setResourceBase(Resource base)
    {
        try { super.setResourceBase(base); }
        catch(Exception e) { log.error("Error while setting resource base: "+base.getId() +"-"+ base.getTitle(), e);  }
        if(!"".equals(base.getAttribute("template","").trim()))
        {
            try 
            { 
                tpl = SWBUtils.XML.loadTemplateXSLT(SWBPortal.getFileFromWorkPath(base.getWorkPath() +"/"+ base.getAttribute("template").trim()));
                path= SWBPortal.getWebWorkPath() +  base.getWorkPath() + "/";
            } 
            catch(Exception e) { log.error("Error while loading resource template: "+base.getId(), e); }
        }
        if(tpl==null)
        {
            try { tpl = SWBUtils.XML.loadTemplateXSLT(SWBPortal.getAdminFileStream("/swbadmin/xsl/Search/Search.xslt")); }
            catch(Exception e) { log.error("Error while loading default resource template: "+base.getId(), e); }
        }
    }

    /**
     * Gets the dom.
     * 
     * @param request the request
     * @param response the response
     * @param paramRequest the param request
     * @return the dom
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws SWBResourceException the sWB resource exception
     */
    public org.w3c.dom.Document getDom(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException 
    {
        try
        {
            Document doc = SWBUtils.XML.getNewDocument();
            WebSite tm = paramRequest.getWebPage().getWebSite();
            String scope = paramRequest.getUser().getLanguage();
            if (scope == null) {
                scope = tm.getLanguage().getId();
            }

            HashMap args = new HashMap();
            args.put("separator", " | ");
            args.put("links", "false");
            args.put("language", paramRequest.getUser().getLanguage());

            Element search = doc.createElement("SEARCH");
            doc.appendChild(search);            
            search.setAttribute("path", path);
            
            String q = request.getParameter("q");
            String start = request.getParameter("s");
            if (q != null)
            {
                search.setAttribute("words", q);
                search.setAttribute("wordsEnc", java.net.URLEncoder.encode(q));
                //search.setAttribute("path",AFUtils.getInstance().getWebPath()+AFUtils.getInstance().getEnv("wb/distributor")+"/"+tm.getId()+"/");
                search.setAttribute("work", SWBPortal.getWebWorkPath());
                search.setAttribute("url", paramRequest.getWebPage().getUrl(scope,false));

                StringTokenizer st = new StringTokenizer(q, " ");
                Vector w = new Vector();
                while (st.hasMoreTokens())
                {
                    w.addElement(st.nextToken());
                }

                int seg = 10;
                int cont = 0;
                boolean find = true;

                // ********************** Temas **********************
                int i = 0;        // contador del segmento
                int s = 1;        // start
                try { if (start != null) {
                    s = Integer.parseInt(start); }
                } 
                catch (Exception e) { log.error("", e); }
                
                Iterator <WebPage> it = tm.listWebPages();
                while (it.hasNext())
                {
                    WebPage t1 =  it.next();
                    if (!t1.isActive()){ 
                        continue;
                    }
                    
                    StringBuffer names = new StringBuffer("");
                    if (t1.getId() != null)
                    {
                         /* TODO: VER 4
                        Iterator na = t1.getBaseNames().iterator();
                        while (na.hasNext())
                        {
                            BaseName bn = (BaseName) na.next();
                            names.append(bn.getBaseNameString() + " / ");

                            Iterator va = bn.getVariants().iterator();
                            while (va.hasNext())
                            {
                                Variant v = (Variant) va.next();
                                VariantName vn = v.getVariantName();
                                names.append(vn.getResource() + " ");
                            }
                        }
                        **/
                        
                        String desc = null;
                        StringBuffer descb = new StringBuffer("");
/*
                        Iterator ito=t1.getOccurrences().iterator();
                        while(ito.hasNext())
                        {
                            Occurrence occ=(Occurrence)ito.next();
                            if(occ.getDbdata().getActive()==1)
                            {
                                if(occ.getInstanceOf()!=null && occ.getInstanceOf().getTopicRef()!=null)
                                {
                                    String tpid=occ.getInstanceOf().getTopicRef().getId();
                                    if(tpid.startsWith("IDM_WB"))
                                    {
                                        descb.append(occ.getResourceData()+" / ");
                                        if(tpid.equals(scope.getId()))
                                        {
                                            desc=occ.getResourceData();
                                        }
                                    }else if(tpid.equals("REC_WBContent"))
                                    {
                                        try
                                        {
                                            RecResource rec=DBResource.getInstance().getResource(Long.parseLong(occ.getResourceData()));
                                            descb.append(rec.getDescription());
                                        }catch(Exception e){}
                                    }
                                }else AFUtils.log("Error: La occurrencia:"+occ.getId()+" no tiene padre...",true);
                            }
                        }
 */
                        Enumeration itw = w.elements();
                        while (itw.hasMoreElements())
                        {
                            String str = (String) itw.nextElement();
                            StringSearch iter = new StringSearch();
                            if (!iter.compare(names.toString() + descb.toString(), str)) {
                                find = false;
                            }
                        }

                        if (find)
                        {
                            cont++;
                            if (cont >= s && i < seg)
                            {
                                i++;
                                Element theme = doc.createElement("Topic");
                                search.appendChild(theme);
                                addElem(doc, theme, "id", t1.getId());
                                addElem(doc, theme, "name", t1.getDisplayName(scope));
                                //addElem(doc,theme,"names",names.toString());
                                addElem(doc, theme, "names", t1.getPath(args));
                                desc=t1.getDescription(scope);  
                                if (desc != null && !"".equals(desc)) {
                                    addElem(doc, theme, "desc", desc);
                                }
                            }
                        }
                        find = true;
                    }
                }
                search.setAttribute("size", "" + cont);
                if (i > 0) {
                    search.setAttribute("seg", "" + (s + i - 1));
                }
                else {
                    search.setAttribute("seg", "" + (s + i));
                }
                search.setAttribute("off", "" + s);
                
                if (cont - 1 > s + seg) {
                    search.setAttribute("next", "" + (s + seg));
                }
                if (s > 1)
                {
                    int as = s - seg;
                    if (as < 1) {
                        as = 1;
                    }
                    search.setAttribute("back", "" + as);
                }

                // ********************** Paginación **********************
                if (cont > seg)
                {
                    int p = (cont + seg - 1) / seg;
                    for (int z = 0; z < p; z++)
                    {
                        Element page = doc.createElement("page");
                        search.appendChild(page);
                        addElem(doc, page, "id", "" + (z + 1));
                        if (s != ((z * seg) + 1)) {
                            addElem(doc, page, "start", "" + ((z * seg) + 1));
                        }
                        else {
                            addElem(doc, page, "nostart", "" + ((z * seg) + 1));
                        }
                    }
                }
            }
            return doc;
        }
        catch (Exception e) { log.error("Error while generating DOM in resource "+ getResourceBase().getResourceType().getResourceClassName() +" with identifier " + getResourceBase().getId() + " - " + getResourceBase().getTitle(), e); }
        return null; 
    }

    /**
     * Do xml.
     * 
     * @param request the request
     * @param response the response
     * @param paramRequest the param request
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws SWBResourceException the sWB resource exception
     */    
    @Override
    public void doXML(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException 
    {
        try
        {
            org.w3c.dom.Document dom=getDom(request, response, paramRequest);
            if(dom!=null) {
                response.getWriter().println(SWBUtils.XML.domToXml(dom));
            }
        }
        catch(Exception e){ log.error(e); }        
    } 
    
    /**
     * Do view.
     * 
     * @param request the request
     * @param response the response
     * @param paramRequest the param request
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws SWBResourceException the sWB resource exception
     */
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException 
    {
        try
        {
            Document dom =getDom(request, response, paramRequest);
            //System.out.println(AFUtils.getInstance().DomtoXml(dom));
            if(dom != null)  {
                response.getWriter().print(SWBUtils.XML.transformDom(tpl, dom));
            }
        }
        catch(Exception e) { log.error(e); }
    }

    /**
     * Adds the elem.
     * 
     * @param doc the doc
     * @param parent the parent
     * @param elemName the elem name
     * @param elemValue the elem value
     */      
    private void addElem(Document doc, Element parent, String elemName, String elemValue)
    {
        Element elem = doc.createElement(elemName);
        elem.appendChild(doc.createTextNode(elemValue));
        parent.appendChild(elem);
    }
}
