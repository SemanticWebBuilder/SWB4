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

import java.io.*;
import java.util.*;
import java.net.*;

import java.util.Properties;
import javax.servlet.http.*;

import org.w3c.dom.*;

import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Resource;
import org.semanticwb.model.WebPage;
import org.semanticwb.portal.api.GenericAdmResource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.admin.admresources.util.XmlBundle;

import com.arthurdo.parser.*;
import org.semanticwb.SWBPortal;

// TODO: Auto-generated Javadoc
/**
 * WBUrlContent recupera el contenido de una página web externa y la incrusta como contenido local.
 *
 * WBUrl retrieve a external web page and embeded it like local content.
 *
 * @Autor:  Jorge Jimenez
 */

public class WBUrlContent extends GenericAdmResource {
    
    /** The log. */
    private static Logger log = SWBUtils.getLogger(WBUrlContent.class);
    
    /** The xml. */
    String xml=null;
    
    /** The bundle. */
    XmlBundle bundle=null;
    
    /** The recproperties. */
    static Properties recproperties = null;
    
    /** The msg2. */
    String msg1 = null,msg2 = null;
    
    /** The host. */
    String host = "";
    
    /** The file. */
    String file = "";
    
    /** The paramfrom url. */
    String paramfromUrl = "";    
    
    /** The name class. */
    String nameClass="WBUrlContent";
   
    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.GenericAdmResource#setResourceBase(org.semanticwb.model.Resource)
     */
    public void setResourceBase(Resource base) throws SWBResourceException {
        super.setResourceBase(base);
        FileInputStream fptr = null;
        recproperties = new Properties();
        try {
                int pos=-1;
                pos=this.getClass().getName().lastIndexOf(".");
                if(pos>-1){
                    nameClass=this.getClass().getName().substring(pos+1);
                }
                try {
                    fptr =  new FileInputStream(SWBPortal.getWorkPath()+"/sites/"+base.getWebSiteId()+"/config/resources/"+nameClass+".properties");
                }catch(Exception e) {
                }
                if(fptr==null) { //busca en raiz de work/config
                    try {
                        fptr = new FileInputStream(SWBPortal.getWorkPath()+"/work/config/resources/"+nameClass+".properties");
                    }catch(Exception e) {
                    }
                }
                if (fptr != null) {
                    recproperties.load(fptr);
                    msg1 = recproperties.getProperty("FuneteOriginal");
                    msg2 = recproperties.getProperty("InfoOriginal");
                }
        }catch(Exception e){
            log.error("Error while loading resource properties file: "+base.getId() +"-"+ base.getTitle(), e);
        }
    }    
    
    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.GenericAdmResource#doView(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.semanticwb.portal.api.SWBParamRequest)
     */
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        Resource base=getResourceBase();
        if(base.getAttribute("msg1")!=null) msg1=base.getAttribute("msg1");
        if(base.getAttribute("msg2")!=null) msg2=base.getAttribute("msg2");
        if(msg1==null) msg1 = paramRequest.getLocaleString("msg1") + ":";
        if(msg2==null) msg2 = paramRequest.getLocaleString("msg2") + ":";
        StringBuffer ret = new StringBuffer();
        try{
            //TODO. cambiar SWBUtils.XML.xmlToDom(base.getXml()) por el método base.getDom()
            Document dom = SWBUtils.XML.xmlToDom(base.getXml());
            if(dom == null) {
                throw new SWBResourceException("Dom nulo");
            }
            ret.append("<!-- <content id=\"" + base.getId() + "\"/> -->");
            String param = "";
            StringBuffer othersparam = new StringBuffer();
            try {
                if(request.getParameter("urlwb") == null) {
                    NodeList url = dom.getElementsByTagName("url");
                    if(url.getLength() > 0) {
                        String surl = url.item(0).getChildNodes().item(0).getNodeValue();
                        ret.append(CorrigeRuta(surl, paramRequest.getWebPage(), param, othersparam, request,paramRequest));
                    }
                }else {
                    Enumeration en = request.getParameterNames();
                    boolean flag = true;
                    while(en.hasMoreElements()) {
                        String paramN = en.nextElement().toString();
                        if(!paramN.equals("urlwb") && !paramN.equals("param") && !paramN.equals("wbresid")) {
                            String[] paramval = request.getParameterValues(paramN);
                            for(int i = 0; i < paramval.length; i++) {
                                if(flag) {
                                    othersparam.append("?" + paramN + "=" + paramval[i]);
                                    flag = false;
                                }else {
                                    othersparam.append("&" + paramN + "=" + paramval[i]);
                                }
                            }
                        }
                    }
                    
                    String urlwb = "";
                    String wbresid = "";
                    if(request.getParameter("urlwb")!=null) {
                        urlwb = DeCodificaCadena(request.getParameter("urlwb"), paramRequest.getWebPage());
                    }else {
                        response.getWriter().print(ret.toString());
                    }
                    if(request.getParameter("param")!=null) {
                        param = DeCodificaCadena(request.getParameter("param"), paramRequest.getWebPage());
                    }
                    if(request.getParameter("wbresid")!=null) {
                        wbresid = request.getParameter("wbresid");
                    }else {
                        wbresid = base.getId();
                    }
                    if(!urlwb.equals("") && wbresid.equals(base.getId())) {
                        ret.append(CorrigeRuta(urlwb, paramRequest.getWebPage(), param, othersparam, request,paramRequest));
                    }                    
                }
            }catch (Exception f) {
                ret.append("page is not in well formed..");
                ret.append("<nocache/>");
                log.error("Error in UrlContent resource while decoding page: "+paramRequest.getWebPage().getId(), f);
            }
        }catch (Exception e) { 
            log.error("Error in resource WBUrlContent while bringing HTML ", e);
        }        
        PrintWriter out = response.getWriter();
        out.println(ret.toString());
    }
    
    
    /**
     * Parsea el flujo del contenido remoto parseando rutas y ligas y generando el
     * resultado final del mismo.
     * 
     * @param surl the surl
     * @param topic the topic
     * @param param the param
     * @param othersparam the othersparam
     * @param request the request
     * @param paramRequest the param request
     * @return the string buffer
     * @throws SWBResourceException the sWB resource exception
     * @return
     */    
    public StringBuffer CorrigeRuta(String surl, WebPage topic, String param, StringBuffer othersparam, HttpServletRequest request, SWBParamRequest paramRequest) throws SWBResourceException {
        Resource base=getResourceBase();
        String OriPath = surl;
        String cadCod = null;
        
        //TODO. cambiar SWBUtils.XML.xmlToDom(base.getXml()) por el método base.getDom()
        Document dom = SWBUtils.XML.xmlToDom(base.getXml());
        
        HtmlTag tag = new HtmlTag();
        int pos = -1;
        host = "";
        file = "";
        paramfromUrl = "";
        String para = "";
        String urlfin = "";
        String surlHex = "";
        String hostHex = "";
        String valueHex = "";
        String surlfile = "";
        String surlfileHex = "";
        String sruta = "";
        StringBuffer ret = new StringBuffer();
        NodeList tagbcawb = dom.getElementsByTagName("tagbcawb");
        String stagbcawb = "bca";
        if(tagbcawb.getLength() > 0)
            if(!tagbcawb.item(0).getChildNodes().item(0).getNodeValue().equals(""))
                stagbcawb = tagbcawb.item(0).getChildNodes().item(0).getNodeValue();
        NodeList NInfoSource = dom.getElementsByTagName("InfoSource");
        int iInfoSource = -1;
        if(NInfoSource.getLength() > 0)
            iInfoSource = Integer.parseInt(NInfoSource.item(0).getChildNodes().item(0).getNodeValue().trim());
        
        String baserut = (String) SWBPlatform.getContextPath();
        sruta = (String) SWBPlatform.getEnv("wb/distributor") + "/";
        //String envia = baserut + sruta + topic.getMap().getId() + "/" + topic.getId() + "/" + base.getId();
        String envia = paramRequest.getRenderUrl().toString();
        if (!surl.toLowerCase().startsWith("http://") && !surl.toLowerCase().startsWith("https://"))
            surl = "http://" + surl;
        
        pos = surl.lastIndexOf("/");
        if (pos != -1 && pos > 6) {
            file = surl.substring(pos + 1);
            
            surl = surl.substring(0, pos + 1);
        }
        pos = -1;
        if (surl.startsWith("http://"))
            pos = surl.indexOf("/", 7);
        else if (surl.startsWith("https://"))
            pos = surl.indexOf("/", 8);
        if (pos != -1) {
            host = surl.substring(0, pos);
        } else {
            surl = surl + "/";
            host = surl;
        }
        String ssign = "";
        if (!param.equals(""))
            ssign = "?";
        String sothersparam = "";
        if (othersparam != null)
            sothersparam = othersparam.toString();
        
        sothersparam = sothersparam.replaceAll(" ", "%20");
        
        
        if (file.indexOf("?") > -1 && sothersparam != null && sothersparam.length() > 0 && sothersparam.charAt(0) == '?') sothersparam = "&" + sothersparam.substring(1);
        
        
        if (request.getMethod().equals("POST"))
            urlfin = surl + file + sothersparam;
        else
            urlfin = surl + file + ssign + param + sothersparam;
        
        pos = -1;
        pos = file.indexOf("?");
        if (pos > -1) {
            surlfile = surl + file.substring(0, pos);
        } else
            surlfile = surl + file;
        
        try {
            URL pagina = new URL(urlfin);
            String hostCookie = pagina.getHost();
            String cookie = (String) request.getSession().getAttribute("Cookie:" + hostCookie);
            String sheader = request.getHeader("user-agent");
             
            URLConnection conex = pagina.openConnection();
            if (cookie != null) conex.setRequestProperty("Cookie", cookie);
            if (sheader != null) conex.setRequestProperty("user-agent", sheader);
            
            //Paso de parametros por metodo POST
            if (request.getMethod().equals("POST") && sothersparam.length() > 0) {
                conex.setDoInput(true);
                conex.setDoOutput(true);
                conex.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                DataOutputStream dos = new DataOutputStream(conex.getOutputStream());
                //String post = "param1=" + URLEncoder.encode( paramVariable1 ) + "&param2=" + URLEncoder.encode( paramVariable2 );
                if (sothersparam.charAt(0) == '?')
                    sothersparam = sothersparam.substring(1);
                dos.writeBytes(sothersparam);
                dos.flush();
                dos.close();
            }
            // Termina paso de parametros por metodo POST
            
            DataInputStream datos = new DataInputStream(conex.getInputStream());
            HtmlStreamTokenizer tok = new HtmlStreamTokenizer(datos);
            boolean framewb = false;
            while (tok.nextToken() != HtmlStreamTokenizer.TT_EOF) {
                int ttype = tok.getTokenType();
                if (ttype == HtmlStreamTokenizer.TT_TAG || ttype == HtmlStreamTokenizer.TT_COMMENT) {
                    tok.parseTag(tok.getStringValue(), tag);
                    if (tok.getRawString().toLowerCase().startsWith("<!--[if")) continue;
                    if (tag.getTagString().toLowerCase().equals("frameset") || tag.getTagString().toLowerCase().equals("frame")) {
                        if (tag.getTagString().toLowerCase().equals("frame")) {
                            if (tag.getParam("name").equalsIgnoreCase("bottom")) {
                                framewb = true;
                                ret.append("<script>");
                                ret.append("window.location=\'/frame.jsp?url=" + tag.getParam("src") + "\',\'_new\',\'width=800,height=600, left=0,top=0,screenX=0,screenY=0,outerwidth=800,outerheight=600,resizable=yes,status,toolbar=no,scrollbars\';");
                                ret.append("</script>");
                            }
                        } else {
                            if (tag.isEndTag() && !framewb) {
                                ret.append("<script>");
                                ret.append("window.open(\'" + urlfin + "\',\'_newcnt\',\'width=800,height=600, left=0,top=0,screenX=0,screenY=0,outerwidth=800,outerheight=600,resizable=yes,status,toolbar=no,scrollbars\');");
                                ret.append("</script>");
                            }
                        }
                    }
                    // agregado para soporte con flash
                    if (tag.getTagString().toLowerCase().equals("param") || tag.getTagString().toLowerCase().equals("embed")) {
                        if (!tag.isEndTag()) {
                            ret.append("<");
                            ret.append(tag.getTagString());
                            ret.append(" ");
                            Enumeration en = tag.getParamNames();
                            while (en.hasMoreElements()) {
                                String name = (String) en.nextElement();
                                String value = tag.getParam(name);
                                if (name.toLowerCase().equals("value") || name.toLowerCase().equals("src")) {
                                    
                                    if (value.indexOf(".") != -1) {
                                        if (!value.startsWith("http://") && !value.startsWith("https://") && !value.startsWith("/") && !value.startsWith("mailto:") && !value.startsWith("javascript:") && !value.startsWith("ftp:") && !value.startsWith("telnet:") && !value.startsWith("#")) {
                                            ret.append(name + "=\"" + surl + "" + value + "\"");
                                        } else {
                                            ret.append(name + "=\"" + value + "\"");
                                        }
                                    } else {
                                        ret.append(name + "=\"" + value + "\"");
                                    }
                                } else {
                                    ret.append(name + "=\"" + value + "\"");
                                }
                                ret.append(" ");
                            }
                            ret.append(">");
                        }
                    }
                    // Termina soporte con Flash
                    
                    else if (tag.getTagString().toLowerCase().equals("img")
                    || tag.getTagString().toLowerCase().equals("applet")
                    || tag.getTagString().toLowerCase().equals("script")
                    || tag.getTagString().toLowerCase().equals("link")
                    || tag.getTagString().toLowerCase().equals("td")
                    || tag.getTagString().toLowerCase().equals("table")
                    || tag.getTagString().toLowerCase().equals("body")
                    || tag.getTagString().toLowerCase().equals("input")
                    || tag.getTagString().toLowerCase().equals("a")
                    || tag.getTagString().toLowerCase().equals("form")
                    || tag.getTagString().toLowerCase().equals("area")
                    || tag.getTagString().toLowerCase().equals("meta")
                    || tag.getTagString().toLowerCase().equals(stagbcawb)
                    ) {
                        if (tag.getTagString().toLowerCase().equals(stagbcawb)) {
                            if (!tag.isEndTag()) {
                                ret = null;
                                ret = new StringBuffer();
                                if (iInfoSource == 1) {
                                    ret.append("\n<br><font face=\"Arial\" color=\"#666666\" size=\"1\">" + msg1 + "</font><a href=\"" + host + "\" target=\"newWindowOriginal\"><font face=\"Arial\" color=\"#666666\" size=\"1\">" + host + "</font></a>,<br>");
                                    ret.append("<font face=\"Arial\" color=\"#666666\" size=\"1\">" + msg2 + "</font><a href=\"" + OriPath + "\" target=\"newWindowOriginal\"><font face=\"Arial\" color=\"#666666\" size=\"1\">" + OriPath + "</font></a><p>");
                                }
                            } else {
                                ret.append(tok.getRawString());
                                break;
                            }
                            
                        }
                        if (!tag.isEndTag()) {
                            ret.append("<");
                            ret.append(tag.getTagString());
                            ret.append(" ");
                            Enumeration en = tag.getParamNames();
                            String name = "";
                            String value = "";
                            String JSvalue = "";
                            
                            String actionval = "";
                            boolean flag = false;
                            while (en.hasMoreElements()) {
                                flag = false;
                                boolean fJs = false;
                                name = (String) en.nextElement();
                                value = tag.getParam(name);
                                pos = value.indexOf("?");
                                paramfromUrl = "";
                                para = "";
                                if (pos != -1 && pos > 0) {
                                    paramfromUrl = value.substring(pos + 1);
                                    JSvalue = value;
                                    value = value.substring(0, pos);
                                }
                                if (value.startsWith("http://localhost") || value.startsWith("https://localhost")) {
                                    pos = -1;
                                    pos = value.indexOf("/", 7); // 7 es numero m�gico
                                    if (pos != -1)
                                        value = host + value.substring(pos + 1);
                                }
                                if (paramfromUrl != "") para = CodificaCadena(paramfromUrl);
                                if (surl != null) surlHex = CodificaCadena(surl);
                                if (host != null) hostHex = CodificaCadena(host);
                                if (value != null) valueHex = CodificaCadena(value);
                                if (surlfile != null) surlfileHex = CodificaCadena(surlfile);
                                ret.append(name);
                                ret.append("=\"");
                                boolean hexa = true;
                                String cadena = null; // por que esta tabulaci�n?
                                int lenghthost = 0;
                                if (value != null && host != null && value.length() >= host.length() && value.startsWith("http://")) {
                                    lenghthost = host.length();
                                    cadena = value.substring(0, lenghthost);
                                    if (!host.equals(cadena))
                                        hexa = false;
                                } else if (value != null && host != null && value.length() < host.length() && value.startsWith("http://"))
                                    hexa = false;
                                
                                //Para imprimir codificaci�n correcta
                                if (tag.getTagString().toLowerCase().equals("meta")) {
                                    int posCodifica = -1;
                                    posCodifica = tag.toString().indexOf("charset=");
                                    if (posCodifica != -1) {
                                        cadCod = tag.toString();
                                    }
                                }
                                
                                //Para eliminar ../
                                value = takeOffString(value, "../", 2);
                                
                                if ((name.toLowerCase().equals("src") || name.toLowerCase().equals("background") || name.toLowerCase().equals("codebase") || (tag.getTagString().toLowerCase().equals("link") && name.toLowerCase().equals("href"))) && !value.startsWith("http://") && !value.startsWith("https://") && !value.startsWith("/")) {
                                    ret.append(surl);
                                } else if ((name.toLowerCase().equals("src") || name.toLowerCase().equals("background") || name.toLowerCase().equals("codebase")) && value.startsWith("/")) {
                                    ret.append(host);
                                } else if (name.toLowerCase().equals("href") && !value.endsWith(".pdf") && !value.endsWith(".doc") && !value.endsWith(".zip")) {
                                    if ((name.toLowerCase().equals("href")) && !value.startsWith("http://") && !value.startsWith("https://") && !value.startsWith("/") && !value.startsWith("mailto:") && !value.startsWith("javascript:") && !value.startsWith("ftp:") && !value.startsWith("telnet:") && !value.startsWith("#") && !value.startsWith("?"))
                                        ret.append(envia + "?urlwb=" + surlHex);
                                    else if ((name.toLowerCase().equals("href")) && value.startsWith("/"))
                                        ret.append(envia + "?urlwb=" + hostHex);
                                    else if ((name.toLowerCase().equals("href")) && (value.startsWith("http://") || value.startsWith("https://")) && hexa)
                                        ret.append(envia + "?urlwb=");
                                    else if (name.toLowerCase().equals("href") && value.startsWith("?")) {
                                        ret.append(envia + "?urlwb=" + surlfileHex);
                                    }
                                } else if (name.toLowerCase().equals("href") && (value.endsWith(".pdf") || value.endsWith(".doc") || value.endsWith(".zip"))) {
                                    if ((name.toLowerCase().equals("href")) && !value.startsWith("http://") && !value.startsWith("https://") && !value.startsWith("/") && !value.toLowerCase().startsWith("javascript:"))
                                        ret.append(surl);
                                    else if ((name.toLowerCase().equals("href")) && value.startsWith("/")) ret.append(host);
                                    hexa = false;
                                } else if ((name.toLowerCase().equals("action")) && !value.startsWith("http://") && !value.startsWith("https://") && !value.startsWith("/") && !value.toLowerCase().startsWith("javascript:")) {
                                    if (value.equals("")) {
                                        ret.append(envia + "?urlwb=" + urlfin.replace('?', '&'));
                                        actionval = "<input type=hidden name=urlwb value=" + surlHex + valueHex + ">";
                                    } else
                                        ret.append(envia + "?urlwb=" + surlHex);
                                    actionval = "<input type=hidden name=urlwb value=" + surlHex + valueHex + ">";
                                } else if (name.toLowerCase().equals("action") && value.startsWith("/")) {
                                    ret.append(envia + "?urlwb=" + hostHex);
                                    actionval = "<input type=hidden name=urlwb value=" + hostHex + valueHex + ">";
                                } else if (name.toLowerCase().equals("action") && (value.startsWith("http://") || value.startsWith("https://"))) {
                                    ret.append(envia + "?urlwb=");
                                    actionval = "<input type=hidden name=urlwb value=>";
                                } else if (name.toLowerCase().equals("onmouseover") || name.toLowerCase().equals("onload") || name.toLowerCase().equals("onmouseout") || name.toLowerCase().equals("onclick")) {
                                    if (value.indexOf(".gif") > -1 || value.indexOf(".jpg") > -1) {
                                        if(value.startsWith("/"))
                                        {
                                            String out = findImagesInScript(value, ".gif'", host);
                                            out = findImagesInScript(out, ".jpg'", host);
                                            value = out;
                                        }else
                                        {
                                            String out = findImagesInScript(value, ".gif'", surl);
                                            out = findImagesInScript(out, ".jpg'", surl);
                                            value = out;
                                        }
                                    } else
                                        fJs = true;
                                }
                                if (((name.toLowerCase().equals("href") && !tag.getTagString().toLowerCase().equals("link")) || name.toLowerCase().equals("action")) && !value.startsWith("mailto:") && !value.startsWith("javascript:") && !value.startsWith("ftp:") && !value.startsWith("telnet:") && !value.startsWith("#") && hexa) {
                                    ret.append(valueHex);
                                    flag = true;
                                } else {
                                    ret.append(value);
                                }
                                if (!fJs && !paramfromUrl.equals("") && !para.toString().equals("")) {
                                    ret.append("&param=" + para);
                                }
                                if (flag) {
                                    ret.append("&wbresid=" + base.getId());
                                }
                                if (fJs && paramfromUrl != null && paramfromUrl != "")
                                    ret.append("?" + paramfromUrl);
                                ret.append("\" ");
                            }
                            ret.append(">");
                            if (tag.getTagString().toLowerCase().equals("form")) ret.append(actionval);
                        } else {
                            ret.append(tok.getRawString());
                        }
                    } else {
                        if (tok.getRawString().indexOf("location=") > -1) {
                            String v = takeOffString(tok.getRawString(), "../", 2);
                            ret.append(ParseWord(v, "location=", envia + "?urlwb=" + surl));
                        } else
                            ret.append(tok.getRawString());
                    }
                } else if (ttype == HtmlStreamTokenizer.TT_TEXT) {
                    if (tok.getRawString().indexOf("location=") > -1) {
                        ret.append(ParseWord(tok.getRawString(), "location=", envia + "?urlwb=" + CodificaCadena(surl)));
                    } else {
                        ret.append(tok.getRawString());
                    }
                }
            }
            if (conex.getHeaderField("Set-Cookie") != null) {
                cookie = conex.getHeaderField("Set-Cookie");
                if (cookie != null)
                    request.getSession().setAttribute("Cookie:" + hostCookie, cookie.substring(0, cookie.indexOf(';')));
            }
        } catch (MalformedURLException e) {
            log.error("External page error: "+topic.getId(), e);
            ret.append(paramRequest.getLocaleLogString("externalpagenotavailable"));
            ret.append("<nocache/>");
        } catch (IOException g) {
            log.error("External page not available: "+topic.getId(), g);
            ret.append(paramRequest.getLocaleLogString("externalpagenotavailable"));
            ret.append("<nocache/>");
        } catch (NumberFormatException f) {
            log.error("Error while decoding external page", f);
            ret.append(paramRequest.getLocaleLogString("externalpagenotavailable"));
            ret.append("<nocache/>");
        } catch (Exception e) {
            log.error("Error while getting external page", e);
            ret.append(paramRequest.getLocaleLogString("externalpagenotavailable"));
            return null;
        }
        if (cadCod != null) {
            StringBuffer ret1 = new StringBuffer();
            int posCod = -1;
            pos = cadCod.indexOf("charset=");
            if (pos != -1) {
                try {
                    if(base.getAttribute("charset")==null){
                        String Codif = cadCod.substring(pos + 8, cadCod.indexOf("\"", pos + 8));
                        if (Codif == null || Codif.equals("")) Codif = "iso-8859-1";
                        ret1.append(SWBUtils.TEXT.decode(ret.toString(), Codif));
                    }else{
                        ret1.append(SWBUtils.TEXT.decode(ret.toString(), base.getAttribute("charset")));
                    }
                } catch (Exception e) {
                    log.error("Error while coding external page.. ", e);
                }
            }
            if (ret1 != null)
                return ret1;
        }else if(base.getAttribute("charset")!=null){
            StringBuffer ret1 = new StringBuffer();
            try {
                ret1.append(SWBUtils.TEXT.decode(ret.toString(), base.getAttribute("charset")));
            } catch (Exception e) {
                    log.error("Error while coding external page.. ", e);
            }
            if (ret1 != null)
                return ret1;
        }
        return ret;
    }
    
    
    /**
     * Parsea ciertas cadenas de strings.
     * 
     * @param value the value
     * @param takeOff the take off
     * @param itakeOff the itake off
     * @return the string
     * @return
     */    
    public String takeOffString(String value, String takeOff, int itakeOff) {
        int pos = value.indexOf(takeOff);
        if (pos != -1) {
            int i = 0;
            String valueCad = null;
            do {
                pos = -1;
                pos = value.indexOf(takeOff, i);
                if (pos != -1) {
                    valueCad = value.substring(0, i) + value.substring(i, pos) + value.substring(pos + itakeOff);
                    value = valueCad;
                }
                i = pos + itakeOff;
            } while (pos != -1);
        }
        return value;
    }
    
    /**
     * Parses the word.
     * 
     * @param value the value
     * @param ext the ext
     * @param ruta the ruta
     * @return the string
     */
    String ParseWord(String value, String ext, String ruta) { // Parsea locations de javascript.
        StringBuffer aux = new StringBuffer(value.length());
        int off = 0;
        int f = 0;
        int i = 0;
        int h = -1;
        String cadena = null,cadcomplet = null;
        do {
            f = value.indexOf(ext, off);
            i = value.indexOf(";", f);
            if (f > 0 && i >= 0) {
                i++;
                int flag = 0;
                if ((value.charAt(f + 9) == '"' || value.charAt(f + 9) == '\'') && !value.substring(f + 10).startsWith("http")) {
                    String s = "'\");";
                    if (value.charAt(i - 2) == '\"') {
                        cadena = value.substring(f + 10, i - 2);
                        flag = 1;
                    } else if (value.substring(i - 4, i).equals(s)) {
                        cadena = value.substring(f + 10, i - 4);
                        flag = 2;
                    } else {
                        cadena = value.substring(f + 10, i - 1);
                    }
                    int pos = cadena.indexOf("?");
                    String paramfromUrl = null;
                    String para = null;
                    if (pos != -1) {
                        paramfromUrl = cadena.substring(pos + 1);
                        cadena = cadena.substring(0, pos);
                    }
                    
                    if (paramfromUrl != null) para = paramfromUrl;
                    cadena = CodificaCadena(cadena);
                    if (para != null) cadena = cadena + "&param=" + para;
                    cadcomplet = value.substring(off, f + 10) + ruta + cadena;
                    if (flag == 1)
                        cadcomplet += "\"";
                    else if (flag == 2) cadcomplet += "\'\")";
                    cadcomplet += ";";
                } else if (value.charAt(f + 9) != '"' && !value.substring(f + 9).startsWith("http")) {
                    cadena = value.substring(f + 9, i);
                    cadcomplet = value.substring(off, f + 9) + ruta + cadena;
                } else //Para rutas que empiezen con http o https
                    cadcomplet = value.substring(off, i);
                
                aux.append(cadcomplet);
                off = i;
            }
        } while (f > 0);
        aux.append(value.substring(off));
        return aux.toString();
    }
    
    
    /**
     * Busca imagenes en javascripts, si las encuentra parsea sus rutas.
     * 
     * @param value the value
     * @param ext the ext
     * @param ruta the ruta
     * @return the string
     */    
    private String findImagesInScript(String value, String ext, String ruta) {
        StringBuffer aux = new StringBuffer(value.length());
        int off = 0;
        int f = 0;
        int i = 0;
        int j = 0;
        do {
            f = value.indexOf(ext, off);
            i = value.lastIndexOf("'", f);
            j = value.lastIndexOf("/", f);
            if (f > 0 && i >= 0) {
                i++;
                if (value.startsWith("http://", i)) {
                    aux.append(value.substring(off, f + ext.length()));
                } else {
                    //aux.append(value.substring(off,i)+ruta+value.substring(j+1,f+ext.length()));
                    aux.append(value.substring(off, i) + ruta + value.substring(i, f + ext.length()));
                }
                /*
                else if(value.startsWith("/",i)){
                    aux.append(value.substring(off,i)+ruta+value.substring(i,f+ext.length()));
                }
                 */
                off = f + ext.length();
            }
        } while (f > 0 && i > 0);
        aux.append(value.substring(off));
        return aux.toString();
    }
    
    
    /**
     * Codifica un String.
     * @param cadena El String a codificar.
     * @return Regresa la representaci�n en String del argumento en hexadecimal (base 16).
     * @see java.lang.Integer#toHexString(int)
     */
    public String CodificaCadena(String cadena) {
        StringBuffer cadeCodHex = new StringBuffer();
        byte[] cadenaB = cadena.getBytes();
        for (int i = 0; i < cadenaB.length; i++) {
            Integer numbyte = new Integer(cadenaB[i]);
            cadeCodHex.append(Integer.toHexString(numbyte.intValue()).toUpperCase());
        }
        return cadeCodHex.toString();
    }
    
    /**
     * Decodifica un string.
     * 
     * @param cadena the cadena
     * @param topic the topic
     * @return the string
     * @throws SWBResourceException the sWB resource exception
     * @return
     */    
    public String DeCodificaCadena(String cadena, WebPage topic) throws SWBResourceException {
        StringBuffer cadeDecoHex = new StringBuffer();
        try {
            for (int i = 0; i < cadena.length(); i = i + 2) {
                int pedazo = Integer.parseInt(cadena.substring(i, i + 2), 16);
                cadeDecoHex.append((char) pedazo);
            }
        } catch (NumberFormatException h) {
            log.error("Error while coding external page:"+topic.getId(), h);
            return cadena;
        }
        return cadeDecoHex.toString();
    }
    
    /**
     * Gets the url encode params.
     * 
     * @param params the params
     * @return the url encode params
     */
    String getUrlEncodeParams(String params) {
        //String post = "_url_gracias=" + URLEncoder.encode( "gracias.html" ) + "&que=" + URLEncoder.encode( "Comentario" );
        if (params != null && params.length() > 1) {
            if (params.charAt(0) == '?') params = params.substring(1);
            StringTokenizer st = new StringTokenizer(params, "&");
            while (st.hasMoreElements()) {
                String token = st.nextToken();
                if (token == null) {
                    continue;
                }
                StringTokenizer st1 = new StringTokenizer(token, "=");
                while (st1.hasMoreElements()) {
                    String token1 = st1.nextToken();
                    if (token1 == null) {
                        continue;
                    }
                }
            }
        }
        return params;
    }    
}