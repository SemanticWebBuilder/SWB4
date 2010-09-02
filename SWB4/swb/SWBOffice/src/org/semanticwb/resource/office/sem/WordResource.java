/**  
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
 **/
package org.semanticwb.resource.office.sem;

import com.arthurdo.parser.HtmlStreamTokenizer;
import com.arthurdo.parser.HtmlTag;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import javax.servlet.http.*;
import org.semanticwb.Logger;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Resource;
import org.semanticwb.model.User;
import org.semanticwb.model.WebPage;
import org.semanticwb.office.comunication.OfficeDocument;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.portal.api.*;
import org.semanticwb.portal.util.ContentUtils;

/**
 *
 * @author Jorge Jiménez
 */
public class WordResource extends org.semanticwb.resource.office.sem.base.WordResourceBase
{

    private static final OfficeDocument document = new OfficeDocument();
    private static final ContentUtils contentUtils = new ContentUtils();
    int snpages = 15;
    String stxtant = "Anterior";
    String stxtsig = "Siguiente";
    String stfont = "font face=\"Verdana, Arial, Helvetica, sans-serif\" size=\"2\" color=\"#000000\"";
    int position = 1;

    public WordResource()
    {
        super();
    }

    public WordResource(SemanticObject obj)
    {
        super(obj);
    }
    private static Logger log = SWBUtils.getLogger(WordResource.class);

    protected void beforePrintDocument(PrintWriter out)
    {
    }

    protected void afterPrintDocument(PrintWriter out)
    {
    }

    protected void printDocument(PrintWriter out, String path, String workpath, String html)
    {
        out.write(html);
    }

    public static org.semanticwb.resource.office.sem.WordResource createWordResource(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.resource.office.sem.WordResource) model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
    }

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {

        Resource base = paramRequest.getResourceBase();
        WebPage page = paramRequest.getWebPage();
        String version = getVersionToShow();
        String contentId = getContent();
        String repositoryName = getRepositoryName();

        try
        {
            User user = paramRequest.getUser();
            String file = document.getContentFile(repositoryName, contentId, version, user);
            if (file != null)
            {
                file = file.replace(".doc", ".html");
                file = java.net.URLDecoder.decode(file, "utf-8");
                String path = SWBPortal.getWorkPath();
                if (path.endsWith("/"))
                {
                    path = path.substring(0, path.length() - 1);
                    path += getResourceBase().getWorkPath() + "/" + file;
                }
                else
                {
                    path += getResourceBase().getWorkPath() + "/" + file;
                }

                File filecontent = new File(path);
                if (filecontent.exists())
                {
                    String workpath = SWBPortal.getWebWorkPath() + getResourceBase().getWorkPath() + "/";
                    StringBuilder html = new StringBuilder();
                    try
                    {
                        FileInputStream in = new FileInputStream(path);
                        byte[] buffer = new byte[2048];
                        int read = in.read(buffer);
                        while (read != -1)
                        {
                            html.append(new String(buffer, 0, read));
                            read = in.read(buffer);
                        }
                        String htmlOut = null;
                        boolean deletestyles = false;
                        try
                        {
                            deletestyles = this.isDeletestyles();
                        }
                        catch (Exception e)
                        {
                            log.error(e);
                        }
                        if (isPages() && getNpages() > 0)
                        {
                            htmlOut = SWBPortal.UTIL.parseHTML(html.toString(), workpath, getNpages());
                        }
                        else
                        {
                            htmlOut = SWBPortal.UTIL.parseHTML(html.toString(), workpath);
                        }
                        PrintWriter out = response.getWriter();
                        beforePrintDocument(out);

                        //Agregado por Jorge Jiménez para poner estilos predefinidos y paginación MsWord y OpenOffice (5/07/2009)
                        if (paramRequest.getLocaleString("txtant") != null)
                        {
                            stxtant = paramRequest.getLocaleString("txtant");
                        }
                        if (paramRequest.getLocaleString("txtsig") != null)
                        {
                            stxtsig = paramRequest.getLocaleString("txtsig");
                        }
                        if (getNpages() > 0)
                        {
                            snpages = getNpages();
                        }
                        if (getTxtant() != null && getTxtant().trim().length() > 0)
                        {
                            stxtant = getTxtant();
                        }
                        if (getTxtsig() != null && getTxtsig().trim().length() > 0)
                        {
                            stxtsig = getTxtsig();
                        }
                        if (getTfont() != null && getTfont().trim().length() > 0)
                        {
                            stfont = getTfont();
                        }
                        if (getPosition() > 0)
                        {
                            position = getPosition();
                        }

                        boolean iswordcontent = true;
                        int posword = htmlOut.toLowerCase().indexOf("name=\"generator\" content=\"openoffice.org"); //detección de si el contenido es de openOffice
                        if (posword > -1)
                        {
                            iswordcontent = false;
                        }

                        if (iswordcontent)
                        { //Contenido MsWord
                            htmlOut = contentUtils.predefinedStyles(htmlOut, base, isTpred()); //Estilos predefinidos
                            if (isPages())
                            {
                                htmlOut = contentUtils.paginationMsWord(htmlOut, page, request.getParameter("page"), base, snpages, stxtant, stxtsig, stfont, position);
                            } //Paginación
                        }
                        else if (isPages())
                        { //Contenido OpenOffice
                            htmlOut = contentUtils.paginationOpenOffice(htmlOut, page, request.getParameter("page"), base, snpages, stxtant, stxtsig, stfont, position); //Paginación
                        }
                        //Termina Agregado por Jorge Jiménez (5/07/2009)
                        // eliminar <head><body>, etc



                        htmlOut = cleanHTML(htmlOut, deletestyles);
                        printDocument(out, path, workpath, htmlOut);
                        afterPrintDocument(out);
                        out.close();
                    }
                    catch (Exception e)
                    {
                        log.error(e);
                    }

                }
                else
                {
                    log.error("Contenido no encontrado en ruta: " + filecontent.getAbsolutePath() + ": " + getContent() + "@" + getRepositoryName());
                }
            }
        }
        catch (Exception e)
        {
            log.error(e);
        }
    }

    public static String cleanHTML(String datos, boolean deletesytyles)
    {
        HtmlStreamTokenizer tok = new HtmlStreamTokenizer(new ByteArrayInputStream(datos.getBytes()));
        StringBuilder ret = new StringBuilder();
        HtmlTag tag = new HtmlTag();
        boolean omit = false;
        try
        {
            while (tok.nextToken() != HtmlStreamTokenizer.TT_EOF)
            {
                int ttype = tok.getTokenType();
                if (ttype == HtmlStreamTokenizer.TT_TAG || ttype == HtmlStreamTokenizer.TT_COMMENT)
                {
                    if (ttype == HtmlStreamTokenizer.TT_COMMENT && tok.getRawString().equals("<!-- -->"))
                    {
                        continue;
                    }
                    tok.parseTag(tok.getStringValue(), tag);

                    if (tok.getRawString().toLowerCase().startsWith("<!--[if"))
                    {
                        continue;
                    }
                    else if (deletesytyles && tag.getTagString().toLowerCase().equals("style") && !tag.isEndTag())
                    {
                        omit = true;
                    }
                    else if (deletesytyles && tag.getTagString().toLowerCase().equals("style") && tag.isEndTag())
                    {
                        omit = false;
                    }
                    else if (deletesytyles && (tag.getTagString().toLowerCase().equals("font") || tag.getTagString().toLowerCase().equals("o:p")))
                    {
                    }
                    else if (deletesytyles && (tag.getTagString().toLowerCase().equals("b") || tag.getTagString().toLowerCase().equals("br") || tag.getTagString().toLowerCase().equals("div") || tag.getTagString().toLowerCase().equals("td") || tag.getTagString().toLowerCase().equals("tr") || tag.getTagString().toLowerCase().equals("table") || tag.getTagString().toLowerCase().equals("p") || tag.getTagString().toLowerCase().equals("span")) && !tag.isEndTag() && !tag.isEmpty())
                    {
                        boolean exists = false;
                        int params = tag.getParamCount();
                        for (int i = 0; i < params; i++)
                        {
                            String name = tag.getParamName(i);
                            if (name.toLowerCase().equals("style") || name.toLowerCase().equals("lang") || name.toLowerCase().equals("class"))
                            {
                                exists = true;
                            }
                        }
                        if (!exists)
                        {
                            ret.append(tok.getRawString());
                        }
                        else
                        {
                            ret.append("<");
                            ret.append(tag.getTagString());
                            Enumeration names = tag.getParamNames();
                            while (names.hasMoreElements())
                            {
                                String name = names.nextElement().toString();
                                String svalue = tag.getParam(name);
                                if (!(name.toLowerCase().equals("style") || name.toLowerCase().equals("lang") || name.toLowerCase().equals("class")))
                                {
                                    ret.append(" ");
                                    ret.append(name);
                                    ret.append("=\"");
                                    ret.append(svalue);
                                    ret.append("\"");
                                }
                            }
                            ret.append(">");
                        }
                    }
                    else if (tag.getTagString().toLowerCase().equals("body") || tag.getTagString().toLowerCase().equals("head") || tag.getTagString().toLowerCase().equals("title") || tag.getTagString().toLowerCase().equals("meta") || tag.getTagString().toLowerCase().equals("html") || tag.getTagString().toLowerCase().equals("link"))
                    {
                        if (tag.getTagString().toLowerCase().equals("title") && !tag.isEndTag())
                        {
                            tok.nextToken();
                            tok.parseTag(tok.getStringValue(), tag);
                            ttype = tok.getTokenType();
                            if (ttype == HtmlStreamTokenizer.TT_TEXT)
                            {
                                tok.nextToken();
                                tok.parseTag(tok.getStringValue(), tag);
                                ttype = tok.getTokenType();
                                if (ttype == HtmlStreamTokenizer.TT_TAG && tag.isEndTag() && tag.getTagString().toLowerCase().equals("title"))
                                {
                                    continue;
                                }
                            }
                            else if (ttype == HtmlStreamTokenizer.TT_TAG && tag.isEndTag() && tag.getTagString().toLowerCase().equals("title"))
                            {
                                continue;

                            }

                        }
                        else
                        {
                            continue;
                        }
                    }
                    else if (tag.getTagString().toLowerCase().equals("a"))
                    {
                        String value = tag.getParam("href");
                        if (value != null && value.startsWith("docrep://")) // liga al repositorio
                        {
                            value = value.substring(8);
                            if (value.startsWith("//"))
                            {
                                value = value.substring(1);
                            }
                            String path = SWBPortal.getDistributorPath() + value;
                            ret.append("<a");
                            Enumeration names = tag.getParamNames();
                            while (names.hasMoreElements())
                            {
                                String name = names.nextElement().toString();
                                String svalue = tag.getParam(name);
                                if (!name.toLowerCase().equals("href"))
                                {
                                    if (deletesytyles && !(name.toLowerCase().equals("style") || name.toLowerCase().equals("lang") || name.toLowerCase().equals("class")))
                                    {
                                        ret.append(" ");
                                        ret.append(name);
                                        ret.append("=\"");
                                        ret.append(svalue);
                                        ret.append("\"");
                                    }
                                    else
                                    {
                                        ret.append(" ");
                                        ret.append(name);
                                        ret.append("=\"");
                                        ret.append(svalue);
                                        ret.append("\"");
                                    }
                                }
                                else
                                {
                                    ret.append(" ");
                                    ret.append(name);
                                    ret.append("=\"");
                                    ret.append(path);
                                    ret.append("\"");
                                }


                            }
                            ret.append(">");
                        }
                        else
                        {
                            if (!omit)
                                ret.append(tok.getRawString());
                        }
                    }
                    else
                    {
                        if (!omit)
                            ret.append(tok.getRawString());
                    }
                }
                else if (ttype == HtmlStreamTokenizer.TT_TEXT)
                {
                    if (!omit)
                        ret.append(tok.getRawString());
                }

            }
        }
        catch (Exception e)
        {
            log.error(e);
        }
        return ret.toString();
    }

    /**
     * Inicializa la clase creando objetos de configuración del recurso
     */
    @Override
    public void setResourceBase(Resource base)
    {
        try
        {
            ContentUtils contentUtils = new ContentUtils();
            super.setResourceBase(base);
            contentUtils.setResourceBase(base, "Content");
        }
        catch (Exception e)
        {
            log.error(e);
        }

    }
}
