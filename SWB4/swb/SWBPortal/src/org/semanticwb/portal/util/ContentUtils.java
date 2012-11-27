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
package org.semanticwb.portal.util;

import com.arthurdo.parser.HtmlStreamTokenizer;
import com.arthurdo.parser.HtmlTag;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;
import java.util.StringTokenizer;
import javax.servlet.http.HttpServletRequest;
import org.omg.PortableServer.REQUEST_PROCESSING_POLICY_ID;
import org.semanticwb.Logger;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Resource;
import org.semanticwb.model.WebPage;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

// TODO: Auto-generated Javadoc
/**
 * The Class ContentUtils.
 * 
 * @author Jorge Jiménez
 */
public class ContentUtils {

    /** The norm_font. */
    String norm_font = null;
    
    /** The norm_size. */
    String norm_size = null;
    
    /** The norm_color. */
    String norm_color = null;
    
    /** The tit1_font. */
    String tit1_font = null;
    
    /** The tit1_size. */
    String tit1_size = null;
    
    /** The tit1_color. */
    String tit1_color = null;
    
    /** The tit2_font. */
    String tit2_font = null;
    
    /** The tit2_size. */
    String tit2_size = null;
    
    /** The tit2_color. */
    String tit2_color = null;
    
    /** The tit3_font. */
    String tit3_font = null;
    
    /** The tit3_size. */
    String tit3_size = null;
    
    /** The tit3_color. */
    String tit3_color = null;
    
    /** The css size. */
    static String cssSize = null;
    
    /** The remove links. */
    static boolean removeLinks = false;
    
    /** The flag. */
    static boolean flag = false;
    
    /** The h style objs. */
    HashMap hStyleObjs = null;
    
    /** The h t mh style objs. */
    static HashMap hTMhStyleObjs = new HashMap();
    
    /** The h tm change styles. */
    static HashMap hTMChangeStyles = new HashMap();
    
    /** The schange styles. */
    String schangeStyles = null;
    
    /** The recproperties. */
    static Properties recproperties = null;

    /** The log. */
    private static Logger log = SWBUtils.getLogger(ContentUtils.class);

    /**
     * Metodo que regresa el número de páginas con las que cuenta el contenido.
     * 
     * @param content the content
     * @return the ms content pages number
     */
    private int getMsContentPagesNumber(String content) {
        int pos = content.lastIndexOf("div.Section");
        if (pos > -1) {
            int pos1 = content.indexOf("{", pos);
            int npages = Integer.parseInt(content.substring(pos + 11, pos1 - 1).trim());
            return npages;
        }else {
            pos=content.lastIndexOf("div.WordSection");
            if (pos > -1) {
                int pos1 = content.indexOf("{", pos);
                int npages = Integer.parseInt(content.substring(pos + 15, pos1 - 1).trim());
                return npages;
            }
            return 1;
        }
    }

    //regresa string html de contenido páginado
    /**
     * Metodo que regresa el contenido por página.
     * 
     * @param content the content
     * @param totPages the tot pages
     * @param npage the npage
     * @param webpage the webpage
     * @param base the base
     * @param contentType the content type
     * @param snpages the snpages
     * @param stxtant the stxtant
     * @param stxtsig the stxtsig
     * @param stfont the stfont
     * @param position the position
     * @return the content by page
     */
    private String getContentByPage(HttpServletRequest request, String content, int totPages, int npage, WebPage webpage, Resource base, String contentType, int snpages, String stxtant, String stxtsig, String stfont, int position) {
        StringBuilder strb = new StringBuilder();
        try {

            StringBuilder strb1 = new StringBuilder();

            strb1.append("<table width=\"100%\" id=\"pagStyle\">");
            strb1.append("<tr>");
            strb1.append("<td align=\"center\">");
            //String path = webpage.getUrl() + "?";
            String path = request.getRequestURI() + "?";

            if (npage > 1) {
                strb1.append("<a href=\"").append(path).append("page=").append(npage - 1).append("\"><").append(stfont).append(">").append(stxtant).append("</font></a> ");
            }
            int ini = 1;
            int fin = snpages;
            int dif = 0;
            if ((totPages < snpages)) {
                fin = totPages;
            }
            if (totPages > snpages && npage > 1) {
                dif = npage - 1;
                if (totPages >= (snpages + dif)) {
                    fin = snpages + dif;
                    ini = 1 + dif;
                } else {
                    fin = totPages;
                    ini = totPages - snpages + 1;
                }
            }

            for (int i = ini; i <= fin; i++) {
                if (i != npage) {
                    strb1.append("<a href=\"" + path + "page=" + i + "\"><" + stfont + ">" + String.valueOf(i) + "</font></a> ");
                } else {
                    strb1.append("<font color=\"RED\">" + String.valueOf(i) + " </font>");
                }
            }
            if (npage < totPages) {
                strb1.append("<a href=\"" + path + "page=" + (npage + 1) + "\"><" + stfont + ">" + stxtsig + "</font></a>");
            }
            strb1.append("</td>");
            strb1.append("</tr>");
            strb1.append("</table>");

            String data=null;
            if(contentType.equals("MsWord")) data=getContentByPage(content, npage);
            else if(contentType.equals("OpenOffice")) data=getContentOpenOfficeByPage(content, npage);
            else if(contentType.equals("HtmlContent")) data=getHtmlContentByPage(content, npage);

            if (position == 1) {
                strb.append(data);
                strb.append("<br><br>");
                strb.append(strb1.toString());
            } else if (position == 2) {
                strb.append(strb1.toString());
                strb.append("<br><br>");
                strb.append(data);
            } else if (position == 3) {
                strb.append(strb1.toString());
                strb.append("<br><br>");
                strb.append(data);
                strb.append("<br><br>");
                strb.append(strb1.toString());
            }
        } catch (Exception e) {
            log.error(e);
        }
        return strb.toString();
    }
    private String getContentByPage(HttpServletRequest request,String content, int totPages, int npage, WebPage webpage, Resource base, String contentType, int snpages, String stxtant, String stxtsig, String stfont, int position,String uri) {
        {
            StringBuilder strb = new StringBuilder();
        try {

            StringBuilder strb1 = new StringBuilder();

            strb1.append("<table width=\"100%\" id=\"pagStyle\">");
            strb1.append("<tr>");
            strb1.append("<td align=\"center\">");
            //String path = webpage.getUrl() + "?";
            String path = request.getRequestURI() + "?";

            if (npage > 1) {
                if(uri==null)
                    strb1.append("<a href=\"" + path + "page=" + (npage - 1) + "\"><" + stfont + ">" + stxtant + "</font></a> ");
                else
                    strb1.append("<a href=\"" + path + "page=" + (npage - 1) + "&uri="+ uri +"\"><" + stfont + ">" + stxtant + "</font></a> ");
            }
            int ini = 1;
            int fin = snpages;
            int dif = 0;
            if ((totPages < snpages)) {
                fin = totPages;
            }
            if (totPages > snpages && npage > 1) {
                dif = npage - 1;
                if (totPages >= (snpages + dif)) {
                    fin = snpages + dif;
                    ini = 1 + dif;
                } else {
                    fin = totPages;
                    ini = totPages - snpages + 1;
                }
            }

            for (int i = ini; i <= fin; i++) {
                if (i != npage) {
                    if(uri==null)
                        strb1.append("<a href=\"" + path + "page=" + i + "\"><" + stfont + ">" + String.valueOf(i) + "</font></a> ");
                    else
                        strb1.append("<a href=\"" + path + "page=" + i + "&uri="+ uri +"\"><" + stfont + ">" + String.valueOf(i) + "</font></a> ");
                } else {
                    strb1.append("<font color=\"RED\">" + String.valueOf(i) + " </font>");
                }
            }
            if (npage < totPages) {
                if(uri==null)
                    strb1.append("<a href=\"" + path + "page=" + (npage + 1) + "\"><" + stfont + ">" + stxtsig + "</font></a>");
                else
                    strb1.append("<a href=\"" + path + "page=" + (npage + 1) + "&uri="+ uri +"\"><" + stfont + ">" + stxtsig + "</font></a>");
            }
            strb1.append("</td>");
            strb1.append("</tr>");
            strb1.append("</table>");

            String data=null;
            if(contentType.equals("MsWord")) data=getContentByPage(content, npage);
            else if(contentType.equals("OpenOffice")) data=getContentOpenOfficeByPage(content, npage);
            else if(contentType.equals("HtmlContent")) data=getHtmlContentByPage(content, npage);

            if (position == 1) {
                strb.append(data);
                strb.append("<br><br>");
                strb.append(strb1.toString());
            } else if (position == 2) {
                strb.append(strb1.toString());
                strb.append("<br><br>");
                strb.append(data);
            } else if (position == 3) {
                strb.append(strb1.toString());
                strb.append("<br><br>");
                strb.append(data);
                strb.append("<br><br>");
                strb.append(strb1.toString());
            }
        } catch (Exception e) {
            log.error(e);
        }
        return strb.toString();
        }
    }
    /**
     * Pagination ms word.
     * 
     * @param htmlOut the html out
     * @param page the page
     * @param npage the npage
     * @param base the base
     * @param snpages the snpages
     * @param stxtant the stxtant
     * @param stxtsig the stxtsig
     * @param stfont the stfont
     * @param position the position
     * @return the string
     */
    public String paginationMsWord(HttpServletRequest request,String htmlOut, WebPage page, String npage, Resource base, int snpages, String stxtant, String stxtsig, String stfont, int position) {
        int totPages = getMsContentPagesNumber(htmlOut);
        if (totPages > 1) {
            int ipage = 1;
            if (npage != null && npage.length()>0) {
                ipage = Integer.parseInt(npage);
            } else {
                ipage = 1;
            }
            htmlOut = getContentByPage(request,htmlOut, totPages, ipage, page, base, "MsWord", snpages, stxtant, stxtsig, stfont, position);
        }
        return htmlOut;
    }

    /**
     * Remueve estilos en las ligas del contenido.
     * 
     * @param content the content
     * @param cadena the cadena
     * @return the string
     */
    public String removeLinks(String content, String cadena) {
        int in = 0;
        while (in < content.length()) {
            int pos = -1;
            pos = content.indexOf(cadena, in);
            if (pos > -1) {
                int pos1 = -1;
                pos1 = content.indexOf(";}", pos);
                if (pos1 > -1) {
                    content = content.substring(0, pos) + content.substring(pos1 + 2);
                }
            } else {
                in = content.length();
            }
        }
        return content;
    }

    /**
     * Gets the content by page.
     * 
     * @param datos the datos
     * @param page the page
     * @return the content by page
     */
    private String getContentByPage(String datos, int page) {
        HtmlTag tag = new HtmlTag();
        StringBuffer ret = new StringBuffer();
        StringBuffer rettmp = new StringBuffer();
        boolean flag = false;
        boolean flag1 = false;
        boolean flag2 = false;
        try {
            HtmlStreamTokenizer tok = new HtmlStreamTokenizer(new java.io.ByteArrayInputStream(datos.getBytes()));
            while (tok.nextToken() != HtmlStreamTokenizer.TT_EOF) {
                int ttype = tok.getTokenType();
                if (ttype == HtmlStreamTokenizer.TT_TAG || ttype == HtmlStreamTokenizer.TT_COMMENT) {
                    tok.parseTag(tok.getStringValue(), tag);
                    if (tok.getRawString().toLowerCase().startsWith("<!--[if")) {
                        continue;
                    }
                    if (tag.getTagString().toLowerCase().equals("div")) { // Si es un tag div
                        flag1 = true;
                        if (!tag.isEndTag()) { //Si no es fin de tag
                            rettmp = new StringBuffer();
                            rettmp.append("<");
                            rettmp.append(tag.getTagString());
                            rettmp.append(" ");
                            Enumeration en = tag.getParamNames();
                            String name = "";
                            String value = "";
                            while (en.hasMoreElements()) { //x cada atributo
                                name = (String) en.nextElement();
                                value = tag.getParam(name);
                                rettmp.append(name);
                                rettmp.append("=\"");
                                if (name.toLowerCase().equals("class")) { //si es atributo class
                                    if (value.toLowerCase().endsWith("section" + page)) {
                                        flag = true;
                                        ret.append(rettmp.toString());
                                        ret.append(value);
                                        ret.append("\" ");
                                        ret.append(">");
                                    }
                                } else if (flag) { //si ya encontro la sección buscada
                                    flag2 = true;
                                    ret.append(rettmp.toString());
                                    ret.append(value);
                                    ret.append("\" ");
                                    ret.append(">");
                                }
                            }
                        } else { //si es fin de tag
                            if (flag && !flag2) { // if que pone el final
                                ret.append(tok.getRawString());
                                ret.append("</body>");
                                ret.append("</html>");
                                break;
                            } else if (flag && flag2) { //Primero pasa x aqui
                                ret.append(tok.getRawString());
                                flag2 = false;
                            }
                        }
                    } else { //Si no es un tag div
                        if (!flag1 || flag) { //Pone todas las lineas adentro del div ya encontrado
                            ret.append(tok.getRawString());
                        }
                    }
                } else if (ttype == HtmlStreamTokenizer.TT_TEXT) { //Pone todas las lineas adentro del div ya encontrado
                    if (!flag1 || flag) {
                        ret.append(tok.getRawString());
                    }
                }
            }
        } catch (NumberFormatException f) {
            log.error(f);
        } catch (Exception e) {
            log.error(e);
        }
        return ret.toString();
    }


    /**
     * Predefined styles.
     * 
     * @param content the content
     * @param base the base
     * @param isTpred the is tpred
     * @return the string
     */
    public String predefinedStyles(String content, Resource base, boolean isTpred) {
        String sTmchangeStyles = (String) hTMChangeStyles.get(base.getWebSiteId());
        if ((schangeStyles != null && schangeStyles.equals("true")) || (sTmchangeStyles != null && sTmchangeStyles.equals("true"))) {
            if (flag) {
                if (hTMhStyleObjs.get(base.getWebSiteId()) != null) {
                    content = SWBPortal.removeStylesOutDivs(content, base.getWebSiteId(), hTMhStyleObjs);
                }
            }
        } else if (schangeStyles == null) {
            if (isTpred && flag) {
                content = SWBPortal.removeStylesOutDivs(content, base.getWebSiteId(), hTMhStyleObjs);
            }
        }
        // termina estilo de letra por defecto
        if (removeLinks) {
            ContentUtils contentUtils = new ContentUtils();
            content = contentUtils.removeLinks(content, "a:link,");
            content = contentUtils.removeLinks(content, "a:visited,");
        }

        return content;
    }

    /**
     * Inicializa la clase creando objetos de configuración del recurso.
     * 
     * @param base the base
     * @param className the class name
     */
    public void setResourceBase(Resource base, String className) {
        try {
            if (hTMhStyleObjs.get(base.getWebSiteId()) == null) {
                hStyleObjs = null;
                FileInputStream fptr = null;
                recproperties = new Properties();
                InputStream file = null;
                try {
                    try {
                        String path = SWBPortal.getWorkPath() + "/models/" + base.getWebSiteId() + "/config/resources/" + className + ".properties";
                        fptr = new FileInputStream(path);
                        file = new FileInputStream(path);
                    } catch (Exception e) {
                        //log.error(e);
                    }
                    if (fptr == null) { //busca en raiz de work/config
                        try {
                            String path = SWBPortal.getWorkPath() + "/config/resources/" + className + ".properties";
                            fptr = new FileInputStream(path);
                            file = new FileInputStream(path);
                        } catch (Exception e) {
                            //log.error(e);
                        }
                    }
                    if (fptr != null) {
                        recproperties.load(fptr);
                        removeLinks = Boolean.valueOf(recproperties.getProperty("removeLinks")).booleanValue();
                        cssSize = recproperties.getProperty("cssSize");
                        schangeStyles = recproperties.getProperty("changeStyles");

                        if ((schangeStyles != null && schangeStyles.equals("true")) || (schangeStyles == null)) { //manejo de estilos
                            //lo siguiente permanece para compatibilidad con version 2
                            norm_font = recproperties.getProperty("msw_font");
                            norm_size = recproperties.getProperty("msw_size");
                            norm_color = recproperties.getProperty("msw_color");
                            tit1_font = recproperties.getProperty("msw_t1_font");
                            tit1_size = recproperties.getProperty("msw_t1_size");
                            tit1_color = recproperties.getProperty("msw_t1_color");
                            tit2_font = recproperties.getProperty("msw_t2_font");
                            tit2_size = recproperties.getProperty("msw_t2_size");
                            tit2_color = recproperties.getProperty("msw_t2_color");
                            tit3_font = recproperties.getProperty("msw_t3_font");
                            tit3_size = recproperties.getProperty("msw_t3_size");
                            tit3_color = recproperties.getProperty("msw_t3_color");

                            createStyleObjs();  //crea estilos h1,h2,h3 y normal (compatibilidad con wb2)

                            if (file != null) {    // crea estilos de xml en el archivo Content.properties (wb3)
                                String sdoc = SWBUtils.IO.readInputStream(file);
                                int pos = -1;
                                pos = sdoc.indexOf("<styles>");
                                if (pos > -1) { //existe styles en propesties
                                    int posFin = -1;
                                    posFin = sdoc.indexOf("</styles>");
                                    if (posFin > -1) {
                                        String xml = sdoc.substring(pos, posFin + 9);
                                        if (xml != null) {
                                            Document dom = SWBUtils.XML.xmlToDom(xml);
                                            if (dom != null) {
                                                hStyleObjs = new HashMap();
                                                createStyleObjs(dom);
                                            }
                                        }
                                    }
                                }
                            }
                            if (hStyleObjs != null && hStyleObjs.size() > 0) {
                                hTMhStyleObjs.put(base.getWebSiteId(), hStyleObjs);
                                if (schangeStyles != null) {
                                    hTMChangeStyles.put(base.getWebSiteId(), schangeStyles);
                                }
                            }
                        }
                        flag = true;
                    }
                } catch (Exception e) {
                    log.error(e);
                }
            }
        } catch (Exception e) {
            log.error(e);
        }
    }

    /**
     * Crea objetos para estilos de letra por defecto de word(h1,h2.h3,h4 y normal), cuando los documentos son de tipo word
     * se puede modificar esta de acuerdo a los estilos definidos en el archivo
     * Content.properties
     */
    private void createStyleObjs() {
        if (hStyleObjs == null) {
            hStyleObjs = new HashMap();
        }
        if (norm_font != null || norm_size != null || norm_color != null) { //crear objeto ContentStyles (normal)
            ContentStyles contentStyle = new ContentStyles();
            contentStyle.setName("MsoNormal");
            if (norm_font != null) {
                contentStyle.setFont(norm_font);
            }
            if (norm_size != null) {
                contentStyle.setSize(norm_size);
            }
            if (norm_color != null) {
                contentStyle.setColor(norm_color);
            }
            hStyleObjs.put(contentStyle.getName(), contentStyle);
        }
        if (tit1_font != null || tit1_size != null || tit1_color != null) { //crear objeto ContentStyles (h1-titulo 1)
            ContentStyles contentStyle = new ContentStyles();
            contentStyle.setName("h1");
            if (tit1_font != null) {
                contentStyle.setFont(tit1_font);
            }
            if (tit1_size != null) {
                contentStyle.setSize(tit1_size);
            }
            if (tit1_color != null) {
                contentStyle.setColor(tit1_color);
            }
            hStyleObjs.put(contentStyle.getName(), contentStyle);
        }

        if (tit2_font != null || tit2_size != null || tit2_color != null) { //crear objeto ContentStyles (h2-titulo 2)
            ContentStyles contentStyle = new ContentStyles();
            contentStyle.setName("h2");
            if (tit2_font != null) {
                contentStyle.setFont(tit2_font);
            }
            if (tit2_size != null) {
                contentStyle.setSize(tit2_size);
            }
            if (tit2_color != null) {
                contentStyle.setColor(tit2_color);
            }
            hStyleObjs.put(contentStyle.getName(), contentStyle);
        }

        if (tit3_font != null || tit3_size != null || tit3_color != null) { //crear objeto ContentStyles (h3-titulo 3)
            ContentStyles contentStyle = new ContentStyles();
            contentStyle.setName("h3");
            if (tit3_font != null) {
                contentStyle.setFont(tit3_font);
            }
            if (tit3_size != null) {
                contentStyle.setSize(tit3_size);
            }
            if (tit3_color != null) {
                contentStyle.setColor(tit3_color);
            }
            hStyleObjs.put(contentStyle.getName(), contentStyle);
        }
    }

    /**
     * Crea objetos de para estilos de letra que no son de defecto de word, cuando los documentos son de tipo word
     * se puede modificar esta de acuerdo a los estilos definidos en el archivo
     * Content.properties
     * 
     * @param dom the dom
     */
    private void createStyleObjs(Document dom) {
        NodeList nlistStyles = dom.getChildNodes().item(0).getChildNodes();
        for (int i = 0; i < nlistStyles.getLength(); i++) {
            if (nlistStyles.item(i).getNodeName().equalsIgnoreCase("style")) {
                ContentStyles contentStyle = new ContentStyles();
                NodeList nlistStyleChilds = nlistStyles.item(i).getChildNodes();
                for (int j = 0; j < nlistStyleChilds.getLength(); j++) {
                    if (nlistStyleChilds.item(j).getNodeName().equalsIgnoreCase("name")) {
                        if (nlistStyleChilds.item(j).getFirstChild().getNodeValue() != null && nlistStyleChilds.item(j).getFirstChild().getNodeValue().trim().length() > 0) {
                            contentStyle.setName(nlistStyleChilds.item(j).getFirstChild().getNodeValue());
                        }
                    } else if (nlistStyleChilds.item(j).getNodeName().equalsIgnoreCase("font")) {
                        if (nlistStyleChilds.item(j).getFirstChild().getNodeValue() != null && nlistStyleChilds.item(j).getFirstChild().getNodeValue().trim().length() > 0) {
                            contentStyle.setFont(nlistStyleChilds.item(j).getFirstChild().getNodeValue());
                        }
                    } else if (nlistStyleChilds.item(j).getNodeName().equalsIgnoreCase("size")) {
                        if (nlistStyleChilds.item(j).getFirstChild().getNodeValue() != null && nlistStyleChilds.item(j).getFirstChild().getNodeValue().trim().length() > 0) {
                            contentStyle.setSize(nlistStyleChilds.item(j).getFirstChild().getNodeValue());
                        }
                    } else if (nlistStyleChilds.item(j).getNodeName().equalsIgnoreCase("color")) {
                        if (nlistStyleChilds.item(j).getFirstChild().getNodeValue() != null && nlistStyleChilds.item(j).getFirstChild().getNodeValue().trim().length() > 0) {
                            contentStyle.setColor(nlistStyleChilds.item(j).getFirstChild().getNodeValue());
                        }
                    }
                }
                if (contentStyle.getName() != null) {
                    StringTokenizer st = new StringTokenizer(contentStyle.getName(), ",;");
                    while (st.hasMoreTokens()) {
                        String n = st.nextToken();
                        if (!hStyleObjs.containsKey(n)) {
                            hStyleObjs.put(n, contentStyle);
                        }
                    }
                }
            }
        }
    }

                //////////METODOS PARA MANEJO DE OPEN OFFICE////////////////////////////


    /**
                 * Pagination open office.
                 * 
                 * @param htmlOut the html out
                 * @param page the page
                 * @param npage the npage
                 * @param base the base
                 * @param snpages the snpages
                 * @param stxtant the stxtant
                 * @param stxtsig the stxtsig
                 * @param stfont the stfont
                 * @param position the position
                 * @return the string
                 */
     public String paginationOpenOffice(HttpServletRequest request,String htmlOut, WebPage page, String npage, Resource base, int snpages, String stxtant, String stxtsig, String stfont, int position) {
        String toFind="<P STYLE=\"page-break-before: always\">";
        int totPages = getOpenOfficeContentPagesNumber(htmlOut, toFind);
        if (totPages > 1) {
            int ipage = 1;
            if (npage != null) {
                ipage = Integer.parseInt(npage);
            } else {
                ipage = 1;
            }
            String headers=getHeaders(htmlOut);
            if(headers!=null && htmlOut!=null && htmlOut.trim().length()>0){

                htmlOut=htmlOut.substring(htmlOut.indexOf("<BODY"));
            }
            htmlOut = headers+getContentByPage(request,htmlOut, totPages, ipage, page, base, "OpenOffice", snpages, stxtant, stxtsig, stfont, position);
        }
        return htmlOut;
    }

     /**
      * Gets the headers.
      * 
      * @param html the html
      * @return the headers
      */
     private String getHeaders(String html) {
         if(html!=null && html.trim().length()>0){
            return html.substring(0, html.indexOf("<BODY"));
         }
         return null;
     }


     /**
      * Metodo que regresa el número de páginas con las que cuenta el contenido.
      * 
      * @param content the content
      * @param toFind the to find
      * @return the open office content pages number
      */
    private int getOpenOfficeContentPagesNumber(String content, String toFind) {
        int ipages=1;
        int off = 0;
        int f = 0;
        do {
            f = content.indexOf(toFind, off);
            if (f > 0) {
                ipages++;
                off = f + toFind.length();
            }
        } while (f > 0);
        return ipages;
    }

     /**
      * Gets the content open office by page.
      * 
      * @param datos the datos
      * @param page2ret the page2ret
      * @return the content open office by page
      */
    private String getContentOpenOfficeByPage(String datos, int page2ret) {
        HtmlTag tag = new HtmlTag();
        StringBuffer ret = new StringBuffer();
        int page=1;
        try {
            HtmlStreamTokenizer tok = new HtmlStreamTokenizer(new java.io.ByteArrayInputStream(datos.getBytes()));
            while (tok.nextToken() != HtmlStreamTokenizer.TT_EOF) {
                int ttype = tok.getTokenType();
                if (ttype == HtmlStreamTokenizer.TT_TAG || ttype == HtmlStreamTokenizer.TT_COMMENT) {
                    tok.parseTag(tok.getStringValue(), tag);
                    if (tok.getRawString().toLowerCase().startsWith("<!--[if")) {
                        continue;
                    }
                    if (tag.getTagString().toLowerCase().equals("p"))
                    {
                        if(tag.getParam("STYLE")!=null && tag.getParam("STYLE").equals("page-break-before: always")) { // Si es un tag div
                            page++;
                            if(page>page2ret){
                                /*
                                String str="<P><BR><BR>"+(char)13+(char)10+"</P>"+(char)13+(char)10;
                                if(ret.toString().endsWith(str)){
                                    String rettmp=ret.toString().substring(0, ret.length()-19);
                                    ret=new StringBuffer();
                                    ret.append(rettmp);
                                }*/
                                ret.append("</BODY>");
                                ret.append("</HTML>");
                                break;
                            }else{
                                ret = new StringBuffer();
                            }
                        }else{
                            ret.append(tok.getRawString());
                        }
                    } else {
                            ret.append(tok.getRawString());
                    }
                } else if (ttype == HtmlStreamTokenizer.TT_TEXT) { //Pone todas las lineas adentro del div ya encontrado
                        ret.append(tok.getRawString());
                }
            }
        } catch (NumberFormatException f) {
            log.error(f);
        } catch (Exception e) {
            log.error(e);
        }
        return ret.toString();
    }

    //////////METODOS PARA MANEJO DE HTMLCONTENT////////////////////////////


     /**
     * Pagination html content.
     * 
     * @param htmlOut the html out
     * @param page the page
     * @param npage the npage
     * @param base the base
     * @param snpages the snpages
     * @param stxtant the stxtant
     * @param stxtsig the stxtsig
     * @param stfont the stfont
     * @param position the position
     * @return the string
     */
    public String paginationHtmlContent(HttpServletRequest request,String htmlOut, WebPage page, String npage, Resource base, int snpages, String stxtant, String stxtsig, String stfont, int position) {
        int totPages = getHtmlContentPagesNumber(htmlOut);
        if (totPages > 1) {
            int ipage = 1;
            if (npage != null) {
                ipage = Integer.parseInt(npage);
            } else {
                ipage = 1;
            }
            htmlOut = getContentByPage(request,htmlOut, totPages, ipage, page, base, "HtmlContent", snpages, stxtant, stxtsig, stfont, position);
        }
        return htmlOut;
    }

    public String paginationHtmlContent(HttpServletRequest request,String htmlOut, WebPage page, String npage, Resource base, int snpages, String stxtant, String stxtsig, String stfont, int position,String uri) {
        int totPages = getHtmlContentPagesNumber(htmlOut);
        if (totPages > 1) {
            int ipage = 1;
            if (npage != null) {
                ipage = Integer.parseInt(npage);
            } else {
                ipage = 1;
            }
            htmlOut = getContentByPage(request,htmlOut, totPages, ipage, page, base, "HtmlContent", snpages, stxtant, stxtsig, stfont, position,uri);
        }
        return htmlOut;
    }

      /**
       * Metodo que regresa el número de páginas con las que cuenta el contenido.
       * 
       * @param content the content
       * @return the html content pages number
       */
    private int getHtmlContentPagesNumber(String content) {
        Iterator <String>itStr=SWBUtils.TEXT.findInterStr(content, "div style=\"","always");
        int size = 1;
        while (itStr.hasNext()) {
            String str=itStr.next();
            if(str!=null && str.equals("page-break-after: ")){
                    size++;
            }
        }


        return size;
    }

    /**
     * Gets the html content by page.
     * 
     * @param datos the datos
     * @param page the page
     * @return the html content by page
     */
    /*
    private String getHtmlContentByPage(String datos, int page){
        int off = 0;
        int cont = 0;
        String matchPatron="<div style=\"page-break-after: always\">";
        if(datos.indexOf(matchPatron)==-1) matchPatron="<div style=\"page-break-after: always;\">";

        int f = -1;
        String data="";
        do {
            f = datos.indexOf(matchPatron, off);
            if (f >-1) {
                cont++;
                data=datos.substring(off, f);
                if(page==cont) break;
                off = f + matchPatron.length();
            }else if(off>0){
                data=datos.substring(off-matchPatron.length());
            }
            else { //Solo hay una pagina
                data=datos;
            }
        } while (f > -1);
        return data;
    }
    */

    private String getHtmlContentByPage(String datos, int page){
        int off = 0;
        int cont = 0;
        //String matchPatron="<div style=\"page-break-after: always\">";
        //if(datos.indexOf(matchPatron)==-1) matchPatron="<div style=\"page-break-after: always;\">";
        String matchPatron="<div style=\"page-break-after: always";

        int f = -1;
        String data="";
        do {
            f = datos.indexOf(matchPatron, off);
            if (f >-1) {
                cont++;
                data=datos.substring(off, f);
                if(page==cont) break;
                int divFin=datos.indexOf("</div>", f);
                if(divFin>-1){
                    off = divFin+6;
                }else off = f + matchPatron.length();
            }else if(off>0){
                data=datos.substring(off);
            }
            else { //Solo hay una pagina
                data=datos;
            }
        } while (f > -1);
        return data;
    }
}
