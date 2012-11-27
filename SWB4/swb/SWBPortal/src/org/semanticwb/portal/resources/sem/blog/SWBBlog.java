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
package org.semanticwb.portal.resources.sem.blog;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.Templates;
import org.jdom.CDATA;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.output.DOMOutputter;
import org.jdom.output.XMLOutputter;
import org.semanticwb.Logger;
import org.semanticwb.SWBException;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.GenericIterator;
import org.semanticwb.model.Resource;
import org.semanticwb.model.Role;
import org.semanticwb.model.User;
import org.semanticwb.model.UserRepository;
import org.semanticwb.model.WebSite;
import org.semanticwb.portal.admin.admresources.util.WBAdmResourceUtils;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;
import org.semanticwb.portal.util.WBFileUpload;

// TODO: Auto-generated Javadoc
/**
 * The Class SWBBlog.
 * 
 * @author victor.lorenzana
 */
public class SWBBlog extends GenericResource
{

    /** The log. */
    private Logger log = SWBUtils.getLogger(SWBBlog.class);
    /** The Constant defaultFormat. */
    public static final String defaultFormat = "dd 'de' MMMM 'de' yyyy";
    /** The Constant DELETE_FILE. */
    private static final String DELETE_FILE = "deleteblog.bmp";
    /** The Constant ADD_FILE. */
    private static final String ADD_FILE = "addpost.bmp";
    /** The Constant EDIT_FILE. */
    private static final String EDIT_FILE = "editpost.bmp";

    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.GenericResource#processRequest(HttpServletRequest, HttpServletResponse, SWBParamRequest)
     */
    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        if (paramRequest.getMode().equals("viewComents"))
        {
            doViewComments(request, response, paramRequest);
        }
        else if (paramRequest.getMode().equals("ViewCommentsXML"))
        {
            doViewCommentsXML(request, response, paramRequest);
        }
        else if (paramRequest.getMode().equals("ViewBlogXML"))
        {
            doViewBlogXML(request, response, paramRequest);
        }
        else if (paramRequest.getMode().equals("createBlog"))
        {
            doCreateBlog(request, response, paramRequest);
        }
        else if (paramRequest.getMode().equals("asignblog"))
        {
            doAsignblog(request, response, paramRequest);
        }
        else if (paramRequest.getMode().equals("deleteBlog"))
        {
            doDeleteBlog(request, response, paramRequest);
        }
        else if (paramRequest.getMode().equals("permissions"))
        {
            doPermissions(request, response, paramRequest);
        }
        else if (paramRequest.getMode().equals("rol"))
        {
            doAsignRole(request, response, paramRequest);
        }
        else if (paramRequest.getMode().equals("user"))
        {
            doAsignUser(request, response, paramRequest);
        }
        else if (paramRequest.getMode().equals("changeTemplateBlog"))
        {
            doChangeTemplateBlog(request, response, paramRequest);
        }
        else if (paramRequest.getMode().equals("changeTemplateComments"))
        {
            doChangeTemplateComments(request, response, paramRequest);
        }
        else if (paramRequest.getMode().equals("viewTemplateBlog"))
        {
            doViewTemplateBlog(request, response, paramRequest);
        }
        else if (paramRequest.getMode().equals("viewTemplateComments"))
        {
            doViewTemplateComments(request, response, paramRequest);
        }
        else if (paramRequest.getMode().equals("viewTemplateByDefaultBlog"))
        {
            doViewTemplateByDefaultBlog(request, response, paramRequest);
        }
        else if (paramRequest.getMode().equals("viewTemplateByDefaultComments"))
        {
            doViewTemplateByDefaultComments(request, response, paramRequest);
        }
        else if (paramRequest.getMode().equals("editpost"))
        {
            doEditPost(request, response, paramRequest);
        }
        else if (paramRequest.getMode().equals("changeSettings"))
        {
            doChangeSettings(request, response, paramRequest);
        }
        else
        {
            super.processRequest(request, response, paramRequest);
        }
    }

    /**
     * Gets the num max of blogs.
     *
     * @return the num max of blogs
     */
    public int getNumMaxOfBlogs()
    {
        int numofblogs = 5;
        if (this.getResourceBase().getAttribute("numofblogs") != null && !this.getResourceBase().getAttribute("numofblogs").equals(""))
        {
            try
            {
                numofblogs = Integer.parseInt(this.getResourceBase().getAttribute("numofblogs"));
            }
            catch (NumberFormatException e)
            {
                log.error(e);
            }
        }
        return numofblogs;
    }

    /**
     * Gets the num max of comments.
     *
     * @return the num max of comments
     */
    public int getNumMaxOfComments()
    {
        int numofcomments = 5;
        if (this.getResourceBase().getAttribute("numofcomments") != null && !this.getResourceBase().getAttribute("numofcomments").equals(""))
        {
            try
            {
                numofcomments = Integer.parseInt(this.getResourceBase().getAttribute("numofcomments"));
            }
            catch (NumberFormatException e)
            {
                log.error(e);
            }
        }
        return numofcomments;
    }

    /**
     * Exist blog.
     *
     * @param blogId the blog id
     * @return true, if successful
     */
    public boolean existBlog(int blogId)
    {
        return false;
    }

    /**
     * Do edit post.
     *
     * @param request the request
     * @param response the response
     * @param paramRequest the param request
     * @throws SWBResourceException the sWB resource exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void doEditPost(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {

        String mode = request.getParameter("mode");
        String postid = request.getParameter("postid");
        if (mode != null && !mode.equals("") && postid != null && !postid.equals(""))
        {
            if (mode.equals("getTitle"))
            {
                getTitlePost(request, response, paramRequest, postid);
            }
            if (mode.equals("getContent"))
            {
                getContentPost(request, response, paramRequest, postid);

            }
        }

    }

    /**
     * Gets the content post.
     *
     * @param request the request
     * @param response the response
     * @param paramRequest the param request
     * @param postid the postid
     * @return the content post
     * @throws IOException Signals that an I/O exception has occurred.
     */
    private void getContentPost(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest, String postid) throws IOException
    {
        PrintWriter out = response.getWriter();
        WebSite site = paramRequest.getWebPage().getWebSite();
        Post post = Post.ClassMgr.getPost(postid, site);
        String sytylePath = this.getResourceBase().getAttribute("templateblog");

        Document doc = new Document();
        Element content = new Element("content");
        String pathDeleteBlog = getDeleteImagePath(paramRequest);
        String pathEditBlog = getEditImagePath(paramRequest);
        String pathAddBlog = getAddImagePath(paramRequest);
        try
        {
            Iterator<Blog> blogs = Blog.ClassMgr.listBlogByPost(post);
            while (blogs.hasNext())
            {
                Blog blog = blogs.next();
                if (blog != null)
                {
                    Document _doc = blog.toXML(getNumMaxOfBlogs(), paramRequest.getUser(), this.getResourceBase().getAttribute("format", defaultFormat), pathDeleteBlog, pathEditBlog, pathAddBlog);
                    String _text = SWBUtils.TEXT.encode(post.getDescription(), "utf-8");
                    InputStream styleStream = null;
                    if (sytylePath == null)
                    {
                        styleStream = SWBBlog.class.getResourceAsStream("blog.xsl");
                    }
                    else
                    {
                        styleStream = SWBPortal.getFileFromWorkPath(this.getResourceBase().getWorkPath() + "/" + this.getResourceBase().getAttribute("templateblog").trim());
                    }
                    Templates templates = SWBUtils.XML.loadTemplateXSLT(styleStream);
                    DOMOutputter Domout = new DOMOutputter();
                    SWBUtils.XML.transformDom(templates, Domout.output(_doc));
                    doc.addContent(content);
                    content.setText(_text);
                    response.setContentType("text/xml");
                    XMLOutputter xMLOutputter = new XMLOutputter();
                    xMLOutputter.output(doc, out);
                }
            }
        }
        catch (Exception e)
        {
            content.setText("");
            response.setContentType("text/xml");
            XMLOutputter xMLOutputter = new XMLOutputter();
            xMLOutputter.output(doc, out);
        }
        out.close();
    }

    /**
     * Gets the title post.
     *
     * @param request the request
     * @param response the response
     * @param paramRequest the param request
     * @param postid the postid
     * @return the title post
     * @throws IOException Signals that an I/O exception has occurred.
     */
    private void getTitlePost(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest, String postid) throws IOException
    {

        PrintWriter out = response.getWriter();
        UserRepository userRep = paramRequest.getUser().getUserRepository();
        Blog blog = Blog.ClassMgr.getBlog(this.getResourceBase().getAttribute("blogid"), paramRequest.getWebPage().getWebSite());
        if (blog != null)
        {
            Post post = blog.getPost(postid);
            Document doc = new Document();
            Element content = new Element("title");
            doc.addContent(content);
            content.setText(SWBUtils.TEXT.encode(post.getTitle(), "utf-8"));
            response.setContentType("text/xml");
            XMLOutputter xMLOutputter = new XMLOutputter();
            xMLOutputter.output(doc, out);
            out.close();
        }

    }

    /**
     * Do change settings.
     *
     * @param request the request
     * @param response the response
     * @param paramRequest the param request
     * @throws SWBResourceException the sWB resource exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void doChangeSettings(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        PrintWriter out = response.getWriter();
        out.println("<script type=\"text/javascript\" language=\"javascript\">");
        out.println("function validaForma(forma)");
        out.println("{");
        out.println("if(forma.format.value=='')");
        out.println("{");
        out.println("alert('" + paramRequest.getLocaleString("invalidDate") + "');");
        out.println("return;");
        out.println("}");
        out.println("forma.submit();");
        out.println("}");
        out.println("</script>");
        out.println("<div class=\"swbform\"><fieldset><table width='100%'  border='0' cellpadding='5' cellspacing='0'> ");
        SWBResourceURL url = paramRequest.getActionUrl();
        out.println("<form action=" + url + " method='post'>");
        out.println("<tr><td >" + paramRequest.getLocaleString("numOfEntriesToShow") + "</td>");
        out.println("<td><select name='numofblogs'>");
        int numofblogs = getNumMaxOfBlogs();
        for (int i = 1; i <= 20; i++)
        {
            out.println("<option " + (numofblogs == i ? "selected" : "") + "  value='" + i + "'>" + i + "</option>");
        }
        out.println("</select></td></tr>");

        final boolean anonimous = Boolean.parseBoolean(paramRequest.getResourceBase().getAttribute("anonimous", "true"));

        String ckeckedYes = "", checkedNo = "";
        if (anonimous)
        {
            ckeckedYes = "checked=\"checked\"";
        }
        else
        {
            checkedNo = "checked=\"checked\"";
        }

        out.println("<tr><td >" + paramRequest.getLocaleString("anonimous") + "</td>");
        out.println("<td>");
        out.println("<input " + ckeckedYes + " type=\"radio\" name=\"anonimous\" value=\"true\">Sí&nbsp;&nbsp;&nbsp;");
        out.println("<input " + checkedNo + " type=\"radio\" name=\"anonimous\" value=\"false\">No");
        out.println("</td></tr>");
        out.println("<tr><td >" + paramRequest.getLocaleString("numOfCommentsToShow") + "</td>");
        out.println("<td><select name='numofcomments'>");
        int numofcomments = getNumMaxOfComments();
        for (int i = 1; i <= 20; i++)
        {
            out.println("<option " + (numofcomments == i ? "selected" : "") + "  value='" + i + "'>" + i + "</option>");
        }
        out.println("</select></td></tr>");
        out.println("<tr><td >" + paramRequest.getLocaleString("FormatOfDateOfBlobEntry") + ":</td>");
        out.println("<td>");
        String format = this.getResourceBase().getAttribute("format", defaultFormat);
        out.println("<input class='valores' type='text' name='format' value=\"" + format + "\" size='50'>");
        out.println("</td></tr>");

        out.println("<tr><td >" + paramRequest.getLocaleString("FormatOfDateForComments") + ":</td>");
        out.println("<td>");
        String format_comments = this.getResourceBase().getAttribute("format_comments", defaultFormat);
        out.println("<input class='valores' type='text' name='format_comments' value=\"" + format_comments + "\" size='50'>");
        out.println("</td></tr>");

        out.println("<tr>");
        out.println("<td colspan='2'>");

        out.println("<div ><table  border='1' width='100%'  border='0' cellpadding='5' cellspacing='0'>");
        out.println("<tr>");
        out.println("<td >");
        out.println(paramRequest.getLocaleString("dayOfMonth"));
        out.println("</td>");
        out.println("<td >d</td>");
        out.println("</tr>");

        out.println("<tr>");
        out.println("<td >");
        out.println(paramRequest.getLocaleString("month"));
        out.println("</td>");
        out.println("<td >M</td></tr>");

        out.println("<tr>");
        out.println("<td >");
        out.println(paramRequest.getLocaleString("year"));
        out.println("</td>");
        out.println("<td >y </td></tr>");

        out.println("<tr>");
        out.println("<td >");
        out.println(paramRequest.getLocaleString("time24"));
        out.println("</td>");
        out.println("<td >H</td></tr>");

        out.println("<tr>");
        out.println("<td >");
        out.println(paramRequest.getLocaleString("time12"));
        out.println("</td>");
        out.println("<td >h</td></tr>");

        out.println("<tr>");
        out.println("<td >");
        out.println(paramRequest.getLocaleString("minutes"));
        out.println("</td>");
        out.println("<td >m</td></tr>");

        out.println("<tr>");
        out.println("<td >");
        out.println(paramRequest.getLocaleString("seconds"));
        out.println("</td>");
        out.println("<td >s</td></tr>");


        out.println("<tr>");
        out.println("<td  >");
        out.println(paramRequest.getLocaleString("ampm"));
        out.println("</td>");
        out.println("<td >a</td></tr>");


        out.println("<tr>");
        out.println("<td >");
        out.println(paramRequest.getLocaleString("labelNote") + ":");
        out.println("</td>");
        out.println("<td >" + paramRequest.getLocaleString("note") + "</td></tr>");

        out.println("<tr>");
        out.println("<td >");
        out.println(paramRequest.getLocaleString("formatByDefault") + ": ");
        out.println("</td>");
        out.println("<td > " + defaultFormat + "</td></tr>");

        out.println("<tr>");
        out.println("<td >");
        out.println(paramRequest.getLocaleString("example") + ":");
        out.println("</td>");
        out.println("<td >dd/MM/yyyy 'a las' HH:mm:ss</td></tr>");

        out.println("<tr>");
        out.println("<td >");
        out.println(paramRequest.getLocaleString("example") + ":");
        out.println("</td>");
        out.println("<td >dd/MM/yyyy 'a las' hh:mm:ss a</td></tr>");

        out.println("</table></div>");

        out.println("</td>");
        out.println("</tr>");

        out.println("<tr><td colspan='2' ><input class='boton' type='button' value='" + paramRequest.getLocaleString("save") + "' onClick='javascript:validaForma(this.form)'></td></tr>");
        out.println("</form>");
        SWBResourceURL urlback = paramRequest.getRenderUrl();
        urlback.setMode(urlback.Mode_ADMIN);
        out.println("</td></tr><tr><td colspan='2' ><a href='" + urlback + "'>" + paramRequest.getLocaleString("back") + "</a></td></tr>");
        out.println("</table></fieldset></div>");
        out.close();
    }

    /**
     * Do view template by default comments.
     *
     * @param request the request
     * @param response the response
     * @param paramRequest the param request
     * @throws SWBResourceException the sWB resource exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void doViewTemplateByDefaultComments(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        PrintWriter out = response.getWriter();
        response.setContentType("text/xml");
        InputStream styleStream = styleStream = SWBBlog.class.getResourceAsStream("comments.xsl");
        try
        {
            SAXBuilder builder = new SAXBuilder();
            Document docToReturn = builder.build(styleStream);
            XMLOutputter xMLOutputter = new XMLOutputter();
            xMLOutputter.output(docToReturn, out);

        }
        catch (Exception ex)
        {
            log.error(ex);
        }
        out.close();
    }

    /**
     * Do view template by default blog.
     *
     * @param request the request
     * @param response the response
     * @param paramRequest the param request
     * @throws SWBResourceException the sWB resource exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void doViewTemplateByDefaultBlog(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        PrintWriter out = response.getWriter();
        response.setContentType("text/xml");
        InputStream styleStream = Blog.class.getResourceAsStream("blog.xsl");
        try
        {
            SAXBuilder builder = new SAXBuilder();
            Document docToReturn = builder.build(styleStream);
            XMLOutputter xMLOutputter = new XMLOutputter();
            xMLOutputter.output(docToReturn, out);

        }
        catch (Exception ex)
        {
            log.error(ex);
        }
        out.close();
    }

    /**
     * Do view template blog.
     *
     * @param request the request
     * @param response the response
     * @param paramRequest the param request
     * @throws SWBResourceException the sWB resource exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void doViewTemplateBlog(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        PrintWriter out = response.getWriter();
        response.setContentType("text/xml");
        InputStream styleStream = null;
        Resource base = getResourceBase();
        if (this.getResourceBase().getAttribute("templateblog") == null)
        {
            styleStream = Blog.class.getResourceAsStream("blog.xsl");
        }
        else
        {
            try
            {
                styleStream = SWBPortal.getFileFromWorkPath(base.getWorkPath() + "/" + base.getAttribute("templateblog").trim());
            }
            catch (Exception e)
            {
                log.error(e);
            }
        }
        try
        {
            SAXBuilder builder = new SAXBuilder();
            Document docToReturn = builder.build(styleStream);
            XMLOutputter xMLOutputter = new XMLOutputter();
            xMLOutputter.output(docToReturn, out);

        }
        catch (Exception ex)
        {
            log.error(ex);
        }
        out.close();
    }

    /**
     * Do view template comments.
     *
     * @param request the request
     * @param response the response
     * @param paramRequest the param request
     * @throws SWBResourceException the sWB resource exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void doViewTemplateComments(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        PrintWriter out = response.getWriter();
        response.setContentType("text/xml");
        InputStream styleStream = null;
        Resource base = getResourceBase();
        if (this.getResourceBase().getAttribute("templatecomments") == null)
        {
            styleStream = Blog.class.getResourceAsStream("comments.xsl");
        }
        else
        {
            try
            {
                styleStream = SWBPortal.getFileFromWorkPath(base.getWorkPath() + "/" + base.getAttribute("templatecomments").trim());
            }
            catch (Exception e)
            {
                log.error(e);
            }
        }
        try
        {
            SAXBuilder builder = new SAXBuilder();
            Document docToReturn = builder.build(styleStream);
            XMLOutputter xMLOutputter = new XMLOutputter();
            xMLOutputter.output(docToReturn, out);

        }
        catch (Exception ex)
        {
            log.error(ex);
        }
        out.close();
    }

    /**
     * Do asign role.
     *
     * @param request the request
     * @param response the response
     * @param paramRequest the param request
     * @throws SWBResourceException the sWB resource exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void doAsignRole(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {

        User user = paramRequest.getUser();
        UserRepository userrep = paramRequest.getWebPage().getWebSite().getUserRepository();

        PrintWriter out = response.getWriter();
        if (this.getResourceBase().getAttribute("blogid") == null || this.getResourceBase().getAttribute("blogid").equals(""))
        {
            out.println("<div class=\"swbform\"><fieldset><p>" + paramRequest.getLocaleString("noBlogs") + "</p>");
            SWBResourceURL urlback = paramRequest.getRenderUrl();
            urlback.setMode(urlback.Mode_ADMIN);
            out.println("<p ><a href='" + urlback + "'>" + paramRequest.getLocaleString("back") + "</a></p></fieldset></div>");
            return;
        }
        out.println("<div class=\"swbform\"><form action='" + paramRequest.getActionUrl() + "' method='post'><fieldset>");
        out.println("<table width='100%'  border='0' cellpadding='5' cellspacing='0'><tr><td>");
        out.println("<table width='100%'  border='0' cellpadding='5' cellspacing='0'>");

        int level = getLevel("*", true, paramRequest.getWebPage().getWebSite());

        out.println("<tr>");
        out.println("<td >");
        out.println(paramRequest.getLocaleString("all"));
        out.println("<td>");
        out.println("<td >");
        out.println("<select name='role_*'>");
        out.println("<option " + (level == 0 ? "selected" : "") + " value='0'>" + paramRequest.getLocaleString("level0"));
        out.println("<option " + (level == 1 ? "selected" : "") + " value='1'>" + paramRequest.getLocaleString("level1"));
        out.println("<option " + (level == 2 ? "selected" : "") + " value='2'>" + paramRequest.getLocaleString("level2"));
        out.println("<option " + (level == 3 ? "selected" : "") + " value='3'>" + paramRequest.getLocaleString("level3"));
        out.println("</select>");
        out.println("<td>");
        out.println("</tr>");
        Iterator<Role> roles = userrep.listRoles();
        while (roles.hasNext())
        {
            out.println("<tr>");
            out.println("<td  >");
            Role recRole = roles.next();
            level = getLevel(recRole.getId() + "_" + recRole.getUserRepository().getId(), true, paramRequest.getWebPage().getWebSite());
            out.println(recRole.getTitle());
            out.println("<td>");
            out.println("<td  >");
            out.println("<select class='valores' name='role_" + recRole.getId() + "_" + recRole.getUserRepository().getId() + "'>");
            out.println("<option " + (level == 0 ? "selected" : "") + " value='0'>" + paramRequest.getLocaleString("level0"));
            out.println("<option " + (level == 1 ? "selected" : "") + " value='1'>" + paramRequest.getLocaleString("level1"));
            out.println("<option " + (level == 2 ? "selected" : "") + " value='2'>" + paramRequest.getLocaleString("level2"));
            out.println("<option " + (level == 3 ? "selected" : "") + " value='3'>" + paramRequest.getLocaleString("level3"));
            out.println("</select>");
            out.println("<td>");
            out.println("</tr>");
        }
        out.println("</table>");
        out.println("</td></tr><tr><td colspan='2' ><input class='boton' type='submit' name='save' value='" + paramRequest.getLocaleString("saveAsign") + "'></td></tr>");
        SWBResourceURL urlback = paramRequest.getRenderUrl();
        urlback.setMode(urlback.Mode_ADMIN);
        out.println("</td></tr><tr><td colspan='2' ><a href='" + urlback + "'>" + paramRequest.getLocaleString("back") + "</a></td></tr></table></fieldset></form></div>");
        out.close();
    }

    /**
     * Gets the level.
     * 
     * @param userid the userid
     * @param isrool the isrool
     * @param site the site
     * @return the level
     */
    public int getLevel(String userid, boolean isrool, WebSite site)
    {
        int level = 0;

        String blogid = this.getResourceBase().getAttribute("blogid");
        Blog blog = Blog.ClassMgr.getBlog(blogid, site);
        GenericIterator<Permision> permissions = blog.listPermissions();
        while (permissions.hasNext())
        {
            Permision permission = permissions.next();
            if (isrool)
            {
                if (permission.isIsRol() && permission.getSecurityId().equals(userid))
                {
                    return permission.getLevel();
                }
            }
            else
            {
                if (!permission.isIsRol() && permission.getSecurityId().equals(userid))
                {
                    return permission.getLevel();
                }
            }
        }
        /*PreparedStatement pt = con.prepareStatement("select level from wbblogpermissions where blogid=? and userid=? and isrol=?");
        pt.setInt(1, blogid);
        pt.setString(2, userid);
        if (isrool)
        {
        pt.setInt(3, 1);
        }
        else
        {
        pt.setInt(3, 0);
        }
        ResultSet rs = pt.executeQuery();
        if (rs.next())
        {
        level = rs.getInt("level");
        }
        rs.close();
        pt.close();*/

        return level;
    }

    /**
     * Do asign user.
     *
     * @param request the request
     * @param response the response
     * @param paramRequest the param request
     * @throws SWBResourceException the sWB resource exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void doAsignUser(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        User user = paramRequest.getUser();
        UserRepository userrep = paramRequest.getWebPage().getWebSite().getUserRepository();

        PrintWriter out = response.getWriter();
        if (this.getResourceBase().getAttribute("blogid") == null || this.getResourceBase().getAttribute("blogid").equals(""))
        {
            out.println("<div class=\"swbform\"><fieldset><p>" + paramRequest.getLocaleString("noBlogs") + "</p>");
            SWBResourceURL urlback = paramRequest.getRenderUrl();
            urlback.setMode(urlback.Mode_ADMIN);
            out.println("<p ><a href='" + urlback + "'>" + paramRequest.getLocaleString("back") + "</a></p></fieldset></div>");
            return;
        }
        out.println("<div class=\"swbform\"><fieldset><form action='" + paramRequest.getActionUrl() + "' method='post'>");
        out.println("<table width='100%'  border='0' cellpadding='5' cellspacing='0'><tr><td>");
        out.println("<table width='100%'  border='0' cellpadding='5' cellspacing='0'>");

        int level = getLevel("*", false, paramRequest.getWebPage().getWebSite());
        out.println("<tr>");
        out.println("<td >");
        out.println(paramRequest.getLocaleString("all"));
        out.println("<td>");
        out.println("<td >");
        out.println("<select class='valores' name='user_*'>");
        out.println("<option " + (level == 0 ? "selected" : "") + " value='0'>" + paramRequest.getLocaleString("level0") + "");
        out.println("<option " + (level == 1 ? "selected" : "") + " value='1'>" + paramRequest.getLocaleString("level1"));
        out.println("<option " + (level == 2 ? "selected" : "") + " value='2'>" + paramRequest.getLocaleString("level2"));
        out.println("<option " + (level == 3 ? "selected" : "") + " value='3'>" + paramRequest.getLocaleString("level3"));
        out.println("</select>");
        out.println("<td>");
        out.println("</tr>");
        Iterator<User> users = userrep.listUsers();
        while (users.hasNext())
        {
            out.println("<tr>");
            out.println("<td >");
            User recuser = users.next();
            level = getLevel(recuser.getId() + "_" + recuser.getUserRepository().getId(), false, paramRequest.getWebPage().getWebSite());
            out.println(recuser.getName() + " " + recuser.getLastName());
            out.println("<td>");
            out.println("<td>");
            out.println("<select class='valores' name='user_" + recuser.getId() + "_" + recuser.getUserRepository().getId() + "'>");
            out.println("<option " + (level == 0 ? "selected" : "") + " value='0'>" + paramRequest.getLocaleString("level0"));
            out.println("<option " + (level == 1 ? "selected" : "") + " value='1'>" + paramRequest.getLocaleString("level1"));
            out.println("<option " + (level == 2 ? "selected" : "") + " value='2'>" + paramRequest.getLocaleString("level2"));
            out.println("<option " + (level == 3 ? "selected" : "") + " value='3'>" + paramRequest.getLocaleString("level3"));
            out.println("</select>");
            out.println("<td>");
            out.println("</tr>");
        }
        out.println("</table>");
        out.println("</td></tr><tr><td colspan='2' ><input class='boton' type='submit' name='save' value='" + paramRequest.getLocaleString("saveAsign") + "'></td></tr>");
        SWBResourceURL urlback = paramRequest.getRenderUrl();
        urlback.setMode(urlback.Mode_ADMIN);
        out.println("</td></tr><tr><td colspan='2' ><a href='" + urlback + "'>" + paramRequest.getLocaleString("back") + "</a></td></tr></table></form></fieldset></div>");
        out.close();
    }

    /**
     * Do permissions.
     *
     * @param request the request
     * @param response the response
     * @param paramRequest the param request
     * @throws SWBResourceException the sWB resource exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void doPermissions(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        PrintWriter out = response.getWriter();
        out.println("<div class=\"swbform\"><fieldset><form action='" + paramRequest.getActionUrl() + "' method='post'>");
        out.println("<table width='100%'  border='0' cellpadding='5' cellspacing='0'><tr><td >" + paramRequest.getLocaleString("saveAsign") + ":</td><td >");
        out.println("<input class='valores' checked type='radio' name='type' value='rol'>" + paramRequest.getLocaleString("byRol") + "<br><input class='valores' type='radio' name='type' value='user'>" + paramRequest.getLocaleString("byUser") + "</td></tr><tr><td colspan='2' ><input class='boton' type='submit' name='save' value='" + paramRequest.getLocaleString("saveAsign") + "'></td></tr>");
        SWBResourceURL urlback = paramRequest.getRenderUrl();
        urlback.setMode(urlback.Mode_ADMIN);
        out.println("</td></tr><tr><td colspan='2' ><a href='" + urlback + "'>" + paramRequest.getLocaleString("back") + "</a></td></tr></table></form></fieldset></div>");
        out.close();
    }

    /**
     * Do delete blog.
     *
     * @param request the request
     * @param response the response
     * @param paramRequest the param request
     * @throws SWBResourceException the sWB resource exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void doDeleteBlog(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        PrintWriter out = response.getWriter();

        Iterator<Blog> blogs = Blog.ClassMgr.listBlogs(paramRequest.getWebPage().getWebSite());
        if (!blogs.hasNext())
        {
            out.println("<div class=\"swbform\"><fieldset><p>" + paramRequest.getLocaleString("noBlogs") + "</p>");
            SWBResourceURL urlback = paramRequest.getRenderUrl();
            urlback.setMode(urlback.Mode_ADMIN);
            out.println("<p ><a href='" + urlback + "'>" + paramRequest.getLocaleString("back") + "</a></p></fieldset></div>");
            return;
        }
        else
        {
            out.println("<div class=\"swbform\"><fieldset><form action='" + paramRequest.getActionUrl() + "' method='post'>");
            out.println("<table width='100%' cellpadding='5' cellspacing='0'><tr><td >" + paramRequest.getLocaleString("nameBlog") + ":</td><td class='valores'><select name='deleteblog'>");

            while (blogs.hasNext())
            {
                Blog blog = blogs.next();
                out.println("<option value='" + blog.getId() + "'>" + blog.getTitle() + "</option>");
            }
            out.println("</select></td></tr><tr><td colspan='2' ><input type='submit' class='boton' name='save' value='" + paramRequest.getLocaleString("deleteBlog") + "'></td></tr>");
            SWBResourceURL urlback = paramRequest.getRenderUrl();
            urlback.setMode(urlback.Mode_ADMIN);
            out.println("</td></tr><tr><td colspan='2' ><a href='" + urlback + "'>" + paramRequest.getLocaleString("back") + "</a></td></tr></table></form></fieldset></div>");
            out.close();
        }
    }

    /**
     * Do asignblog.
     *
     * @param request the request
     * @param response the response
     * @param paramRequest the param request
     * @throws SWBResourceException the sWB resource exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void doAsignblog(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        PrintWriter out = response.getWriter();


        Iterator<Blog> blogs = Blog.ClassMgr.listBlogs(paramRequest.getWebPage().getWebSite());
        if (!blogs.hasNext())
        {
            out.println("<div class=\"swbform\"><fieldset><p>" + paramRequest.getLocaleString("noBlogs") + "</p>");
            SWBResourceURL urlback = paramRequest.getRenderUrl();
            urlback.setMode(urlback.Mode_ADMIN);
            out.println("<p ><a href='" + urlback + "'>" + paramRequest.getLocaleString("back") + "</a></p></fieldset></div>");
        }
        else
        {
            out.println("<div class=\"swbform\"><fieldset><form action='" + paramRequest.getActionUrl() + "' method='post'>");
            out.println("<table width='100%' cellpadding='5' cellspacing='0'><tr><td >" + paramRequest.getLocaleString("nameBlog") + ":</td><td><select name='blog'>");
            while (blogs.hasNext())
            {
                Blog blog = blogs.next();
                out.println("<option value='" + blog.getId() + "'>" + blog.getTitle() + "</option>");
            }
            out.println("</select></td></tr><tr><td colspan='2' ><input class='boton' type='submit' name='save' value='" + paramRequest.getLocaleString("buttonAsign") + "'></td></tr>");
            SWBResourceURL urlback = paramRequest.getRenderUrl();
            urlback.setMode(urlback.Mode_ADMIN);
            out.println("</td></tr><tr><td colspan='2' ><a href='" + urlback + "'>" + paramRequest.getLocaleString("back") + "</a></td></tr></table></form></fieldset></div>");
        }
        out.close();
    }

    /**
     * Do create blog.
     *
     * @param request the request
     * @param response the response
     * @param paramRequest the param request
     * @throws SWBResourceException the sWB resource exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void doCreateBlog(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        PrintWriter out = response.getWriter();
        try
        {
            createForm(out, paramRequest);
        }
        catch (Exception e)
        {
            log.error(e);
        }
        out.close();
    }

    /**
     * Do view comments xml.
     *
     * @param request the request
     * @param response the response
     * @param paramRequest the param request
     * @throws SWBResourceException the sWB resource exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void doViewCommentsXML(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        PrintWriter out = response.getWriter();
        String sBlogId = request.getParameter("blogid");
        String spostid = request.getParameter("postid");
        if (sBlogId != null && !sBlogId.equals(""))
        {
            if (spostid != null && !spostid.equals(""))
            {
                String postid = spostid;
                String blogid = sBlogId;
                SWBResourceURL url = paramRequest.getRenderUrl();
                url.setMode("viewComents");
                url.setCallMethod(url.Call_CONTENT);
                url.setParameter("viewall", "true");
                url.setParameter("postid", String.valueOf(postid));
                try
                {
                    Document doc = getComments(paramRequest, blogid, postid, paramRequest.getWebPage().getUrl(), true, url.toString());
                    XMLOutputter xMLOutputter = new XMLOutputter();
                    xMLOutputter.output(doc, out);
                }
                catch (Exception e)
                {
                    log.error(e);
                }

            }
        }
        out.close();
    }

    /**
     * Do view blog xml.
     *
     * @param request the request
     * @param response the response
     * @param paramRequest the param request
     * @throws SWBResourceException the sWB resource exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void doViewBlogXML(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        String pathDeleteBlog = getDeleteImagePath(paramRequest);
        String pathEditBlog = getEditImagePath(paramRequest);
        String pathAddBlog = getAddImagePath(paramRequest);
        PrintWriter out = response.getWriter();
        String sBlogId = this.getResourceBase().getAttribute("blogid");
        if (sBlogId != null && !sBlogId.equals(""))
        {
            try
            {
                long blogId = Long.parseLong(sBlogId);
                WebSite site = paramRequest.getWebPage().getWebSite();
                Blog blog = Blog.ClassMgr.getBlog(sBlogId, site);
                Document doc = blog.toXML(getNumMaxOfBlogs(), paramRequest.getUser(), this.getResourceBase().getAttribute("format", defaultFormat), pathDeleteBlog, pathEditBlog, pathAddBlog);
                response.setContentType("text/xml");
                XMLOutputter xMLOutputter = new XMLOutputter();
                xMLOutputter.output(doc, out);
            }
            catch (NumberFormatException nfe) // Error
            {
                log.error(nfe);
            }
            catch (IllegalArgumentException nfe) // Error
            {
                log.error("Error al tratar de buscar un blog, en el recurso " + this.getResourceBase().getId() + ", en la p�gina: " + paramRequest.getWebPage().getId() + ", En el sitio " + paramRequest.getWebPage().getWebSiteId(), nfe);
            }
            catch (Exception nfe) // Error
            {
                log.error(nfe);
            }
        }
        out.close();
    }

    /**
     * Update post.
     *
     * @param request the request
     * @param response the response
     * @param blogid the blogid
     * @param postid the postid
     * @throws SWBResourceException the sWB resource exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void updatePost(HttpServletRequest request, SWBActionResponse response, String blogid, String postid) throws SWBResourceException, IOException
    {
        String pathDeleteBlog = getDeleteImagePath(null);
        String pathEditBlog = getEditImagePath(null);
        String pathAddBlog = getAddImagePath(null);

        String title = request.getParameter("title");
        String description = request.getParameter("description");
        if (title != null && description != null)
        {
            Blog blog = Blog.ClassMgr.getBlog(blogid, response.getWebPage().getWebSite());
            GenericIterator<Post> posts = blog.listPosts();
            while (posts.hasNext())
            {
                Post post = posts.next();
                if (post.getId().equals(postid))
                {
                    post.setTitle(title);
                    post.setDescription(description);

                    Document doc = blog.toXML(getNumMaxOfBlogs(), response.getUser(), this.getResourceBase().getAttribute("format", defaultFormat), pathDeleteBlog, pathEditBlog, pathAddBlog);
                    String sytylePath = this.getResourceBase().getAttribute("templateblog");
                    InputStream styleStream = null;
                    try
                    {
                        if (sytylePath == null)
                        {
                            styleStream = SWBBlog.class.getResourceAsStream("blog.xsl");
                        }
                        else
                        {
                            styleStream = SWBPortal.getFileFromWorkPath(this.getResourceBase().getWorkPath() + "/" + this.getResourceBase().getAttribute("templateblog").trim());
                        }
                        Templates templates = SWBUtils.XML.loadTemplateXSLT(styleStream);
                        DOMOutputter Domout = new DOMOutputter();
                        SWBUtils.XML.transformDom(templates, Domout.output(doc));
                    }
                    catch (Exception e)
                    {
                        log.error(e);
                        post.setDescription("Favor de editar este post, ya que tiene caracteres inválidos, por lo cuál no fue almacenado");
                    }

                    break;
                }
            }
        }

    }

    /**
     * Adds the post.
     *
     * @param request the request
     * @param response the response
     * @param blogid the blogid
     * @throws SWBResourceException the sWB resource exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void addPost(HttpServletRequest request, SWBActionResponse response, String blogid) throws SWBResourceException, IOException
    {
        String pathDeleteBlog = getDeleteImagePath(null);
        String pathEditBlog = getEditImagePath(null);
        String pathAddBlog = getAddImagePath(null);
        String title = request.getParameter("title");
        String description = request.getParameter("description");

        if (title != null && description != null)
        {
            Blog blog = Blog.ClassMgr.getBlog(blogid, response.getWebPage().getWebSite());
            if (blog != null)
            {
                Post post = Post.ClassMgr.createPost(response.getWebPage().getWebSite());
                post.setTitle(title);
                post.setDescription(description);
                post.setUserPost(response.getUser());
                post.setFecha_alta(new Date(System.currentTimeMillis()));
                blog.addPost(post);

                Document doc = blog.toXML(getNumMaxOfBlogs(), response.getUser(), this.getResourceBase().getAttribute("format", defaultFormat), pathDeleteBlog, pathEditBlog, pathAddBlog);
                String sytylePath = this.getResourceBase().getAttribute("templateblog");
                InputStream styleStream = null;
                try
                {
                    if (sytylePath == null)
                    {
                        styleStream = SWBBlog.class.getResourceAsStream("blog.xsl");
                    }
                    else
                    {
                        styleStream = SWBPortal.getFileFromWorkPath(this.getResourceBase().getWorkPath() + "/" + this.getResourceBase().getAttribute("templateblog").trim());
                    }
                    Templates templates = SWBUtils.XML.loadTemplateXSLT(styleStream);
                    DOMOutputter Domout = new DOMOutputter();
                    SWBUtils.XML.transformDom(templates, Domout.output(doc));
                }
                catch (Exception e)
                {
                    log.error(e);
                    post.remove();
                }

            }

            /*Connection con = SWBUtils.DB.getDefaultConnection();
            try
            {
            int postId = 0;
            PreparedStatement pt = con.prepareStatement("select max(postid) as maximo from wbblogpost where blogid=" + blogid);
            ResultSet rs = pt.executeQuery();
            if ( rs.next() )
            {
            postId = rs.getInt("maximo");
            }
            rs.close();
            pt.close();
            postId++;
            pt = con.prepareStatement("insert into wbblogpost(postid,blogid,title,description,fec_alta,userid) values(?,?,?,?,?,?)");
            pt.setInt(1, postId);
            pt.setInt(2, blogid);
            pt.setString(3, title);
            pt.setString(4, description);
            pt.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
            pt.setString(6, response.getUser().getId()+"_"+response.getUser().getUserRepository().getId());
            pt.executeUpdate();
            pt.close();
            }
            catch ( SQLException sqle )
            {
            log.error(sqle);
            }
            finally
            {
            if ( con != null )
            {
            try
            {
            con.close();
            }
            catch ( SQLException sqle )
            {
            log.error(sqle);
            }
            }
            }*/
        }

    }

    /**
     * Insert comment.
     *
     * @param request the request
     * @param response the response
     * @param postid the postid
     * @param blogid the blogid
     * @param comment the comment
     * @throws SWBResourceException the sWB resource exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public synchronized void insertComment(HttpServletRequest request, SWBActionResponse response, String postid, String blogid, String comment) throws SWBResourceException, IOException
    {
        Blog blog = Blog.ClassMgr.getBlog(blogid, response.getWebPage().getWebSite());
        if (blog != null)
        {
            GenericIterator<Post> posts = blog.listPosts();
            while (posts.hasNext())
            {
                Post post = posts.next();
                if (post.getId().equals(postid))
                {
                    Comment ocomment = Comment.ClassMgr.createComment(response.getWebPage().getWebSite());
                    ocomment.setComment(comment);
                    ocomment.setFec_altaComment(new Date(System.currentTimeMillis()));
                    if (response.getUser().getId() != null)
                    {
                        ocomment.setUserComment(response.getUser());
                    }
                    post.addComment(ocomment);
                }
            }
        }
    }

    /**
     * Delete blog.
     *
     * @param request the request
     * @param response the response
     * @param blogid the blogid
     * @throws SWBResourceException the sWB resource exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public synchronized void deleteBlog(HttpServletRequest request, SWBActionResponse response, String blogid) throws SWBResourceException, IOException
    {
        Blog blog = Blog.ClassMgr.getBlog(blogid, response.getWebPage().getWebSite());
        blog.remove();
    }

    /**
     * Delete post.
     * 
     * @param request the request
     * @param response the response
     * @param postid the postid
     * @param blogid the blogid
     * @throws SWBResourceException the sWB resource exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public synchronized void deletePost(HttpServletRequest request, SWBActionResponse response, String postid, String blogid) throws SWBResourceException, IOException
    {
        Blog blog = Blog.ClassMgr.getBlog(blogid, response.getWebPage().getWebSite());
        if (blog != null)
        {
            GenericIterator<Post> posts = blog.listPosts();
            while (posts.hasNext())
            {
                Post post = posts.next();
                if (post.getId().equals(postid))
                {
                    post.remove();
                }
            }
        }

    }

    /**
     * Creates the blog.
     *
     * @param name the name
     * @param asign the asign
     */
    public void createBlog(String name, boolean asign)
    {
        /*Connection con = SWBUtils.DB.getDefaultConnection();
        try
        {
        int blogid = 0;
        PreparedStatement pt = con.prepareStatement("select max(blogid) as maximo from wbblog");
        ResultSet rs = pt.executeQuery();
        if (rs.next())
        {
        blogid = rs.getInt("maximo");
        }
        rs.close();
        pt.close();
        blogid++;
        pt = con.prepareStatement("insert into wbblog(blogid,blogname) values(?,?)");
        pt.setInt(1, blogid);
        pt.setString(2, name);
        pt.executeUpdate();
        pt.close();
        if (asign)
        {
        asignBlog(blogid);
        }

        }
        catch (SQLException sqle)
        {
        log.error(sqle);
        }
        finally
        {
        if (con != null)
        {
        try
        {
        con.close();
        }
        catch (SQLException sqle)
        {
        log.error(sqle);
        }
        }
        }*/
    }

    /**
     * Creates the blog.
     *
     * @param request the request
     * @param response the response
     * @param name the name
     * @param asign the asign
     */
    private synchronized void createBlog(HttpServletRequest request, SWBActionResponse response, String name, boolean asign)
    {
        Blog blog = Blog.ClassMgr.createBlog(response.getWebPage().getWebSite());
        blog.setTitle(name);
        if (asign)
        {
            asignBlog(request, response, blog.getId());
        }
        response.setMode(response.Mode_ADMIN);
        /*Connection con = SWBUtils.DB.getDefaultConnection();
        try
        {
        int blogid = 0;
        PreparedStatement pt = con.prepareStatement("select max(blogid) as maximo from wbblog");
        ResultSet rs = pt.executeQuery();
        if (rs.next())
        {
        blogid = rs.getInt("maximo");
        }
        rs.close();
        pt.close();
        blogid++;
        pt = con.prepareStatement("insert into wbblog(blogid,blogname) values(?,?)");
        pt.setInt(1, blogid);
        pt.setString(2, name);
        pt.executeUpdate();
        pt.close();
        if (asign)
        {
        asignBlog(request, response, blogid);
        }
        response.setMode(response.Mode_ADMIN);

        }
        catch (SQLException sqle)
        {
        log.error(sqle);
        }
        finally
        {
        if (con != null)
        {
        try
        {
        con.close();
        }
        catch (SQLException sqle)
        {
        log.error(sqle);
        }
        }
        }*/
    }

    /**
     * Asign blog.
     *
     * @param blogid the blogid
     */
    public void asignBlog(String blogid)
    {
        this.getResourceBase().setAttribute("blogid", String.valueOf(blogid));
        try
        {
            this.getResourceBase().updateAttributesToDB();
        }
        catch (SWBException afe)
        {
            log.error(afe);
        }
    }

    /**
     * Asign blog.
     *
     * @param request the request
     * @param response the response
     * @param blogid the blogid
     */
    public void asignBlog(HttpServletRequest request, SWBActionResponse response, String blogid)
    {
        this.getResourceBase().setAttribute("blogid", blogid);
        try
        {
            this.getResourceBase().updateAttributesToDB();
            response.setMode(response.Mode_ADMIN);
        }
        catch (SWBException afe)
        {
            log.error(afe);
        }
    }

    /**
     * Asign role.
     *
     * @param request the request
     * @param response the response
     * @param blogid the blogid
     * @throws SWBResourceException the sWB resource exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void asignRole(HttpServletRequest request, SWBActionResponse response, String blogid) throws SWBResourceException, IOException
    {
        Blog blog = Blog.ClassMgr.getBlog(blogid, response.getWebPage().getWebSite());
        if (blog != null)
        {
            Enumeration names = request.getParameterNames();

            while (names.hasMoreElements())
            {

                String name = (String) names.nextElement();
                if (name.startsWith("role_"))
                {
                    String level = request.getParameter(name);
                    name = name.substring(5);
                    int ilevel = Integer.parseInt(level);
                    Permision permission = null;
                    boolean exists = false;
                    GenericIterator<Permision> permissions = blog.listPermissions();
                    while (permissions.hasNext())
                    {
                        Permision temp = permissions.next();
                        if (temp.isIsRol() && temp.getSecurityId().endsWith(name))
                        {
                            exists = true;
                            permission = temp;
                            break;
                        }
                    }
                    if (ilevel > 0)
                    {
                        if (!exists)
                        {
                            permission = Permision.ClassMgr.createPermision(response.getWebPage().getWebSite());
                            permission.setIsRol(true);
                            permission.setSecurityId(name);
                            permission.setLevel(ilevel);
                            blog.addPermission(permission);
                        }
                        else
                        {
                            permission.setLevel(ilevel);
                        }

                    }
                    else // 0 no tiene permisos
                    {
                        if (permission != null)
                        {
                            permission.remove();
                        }
                    }
                }
            }

        }
    }

    /**
     * Asign role.
     * 
     * @param name the name
     * @param level the level
     * @param blogid the blogid
     * @param site the site
     * @throws SWBResourceException the sWB resource exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void asignRole(String name, int level, String blogid, WebSite site) throws SWBResourceException, IOException
    {
        Blog blog = Blog.ClassMgr.getBlog(blogid, site);
        if (blog != null)
        {

            if (name.startsWith("role_"))
            {
                name = name.substring(5);
                Permision permission = null;
                boolean exists = false;
                GenericIterator<Permision> permissions = blog.listPermissions();
                while (permissions.hasNext())
                {
                    Permision temp = permissions.next();
                    if (temp.isIsRol() && temp.getSecurityId().endsWith(name))
                    {
                        exists = true;
                        permission = temp;
                        break;
                    }
                }
                if (level > 0)
                {
                    if (!exists)
                    {
                        permission = Permision.ClassMgr.createPermision(site);
                        permission.setIsRol(true);
                        permission.setSecurityId(name);
                        permission.setLevel(level);
                        blog.addPermission(permission);
                    }
                    else
                    {
                        permission.setLevel(level);
                    }

                }
                else // 0 no tiene permisos
                {
                    if (permission != null)
                    {
                        permission.remove();
                    }
                }
            }


        }
    }

    /**
     * Asign user.
     *
     * @param request the request
     * @param response the response
     * @param blogid the blogid
     * @throws SWBResourceException the sWB resource exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void asignUser(HttpServletRequest request, SWBActionResponse response, String blogid) throws SWBResourceException, IOException
    {
        Blog blog = Blog.ClassMgr.getBlog(blogid, response.getWebPage().getWebSite());
        if (blog != null)
        {
            Enumeration names = request.getParameterNames();

            while (names.hasMoreElements())
            {

                String name = (String) names.nextElement();
                if (name.startsWith("user_"))
                {
                    String level = request.getParameter(name);
                    name = name.substring(5);
                    int ilevel = Integer.parseInt(level);
                    Permision permission = null;
                    boolean exists = false;
                    GenericIterator<Permision> permissions = blog.listPermissions();
                    while (permissions.hasNext())
                    {
                        Permision temp = permissions.next();
                        if (!temp.isIsRol() && temp.getSecurityId().endsWith(name))
                        {
                            exists = true;
                            permission = temp;
                            break;
                        }
                    }
                    if (ilevel > 0)
                    {
                        if (!exists)
                        {
                            permission = Permision.ClassMgr.createPermision(response.getWebPage().getWebSite());
                            permission.setIsRol(false);
                            permission.setSecurityId(name);
                            permission.setLevel(ilevel);
                            blog.addPermission(permission);
                        }
                        else
                        {
                            permission.setLevel(ilevel);
                        }

                    }
                    else // 0 no tiene permisos
                    {
                        if (permission != null)
                        {
                            permission.remove();
                        }
                    }
                }
            }

        }
    }

    /**
     * Adds the template blog.
     *
     * @param request the request
     * @param response the response
     * @param upload the upload
     * @throws SWBResourceException the sWB resource exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void addTemplateBlog(HttpServletRequest request, SWBActionResponse response, WBFileUpload upload) throws SWBResourceException, IOException
    {
        byte[] template = upload.getFileData("templateblog");
        ByteArrayInputStream in = new ByteArrayInputStream(template);
        try
        {
            this.getResourceBase().setAttribute("templateblog", "blog.xsl");
            this.getResourceBase().updateAttributesToDB();
            SWBPortal.writeFileToWorkPath(this.getResourceBase().getWorkPath() + "/blog.xsl", in, response.getUser());
        }
        catch (Exception e)
        {
            log.error(e);
        }

        response.setMode(response.Mode_ADMIN);
    }

    /**
     * Adds the template comments.
     *
     * @param request the request
     * @param response the response
     * @param upload the upload
     * @throws SWBResourceException the sWB resource exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void addTemplateComments(HttpServletRequest request, SWBActionResponse response, WBFileUpload upload) throws SWBResourceException, IOException
    {
        byte[] template = upload.getFileData("templatecomments");
        ByteArrayInputStream in = new ByteArrayInputStream(template);
        try
        {
            this.getResourceBase().setAttribute("templatecomments", "comments.xsl");
            this.getResourceBase().updateAttributesToDB();
            SWBPortal.writeFileToWorkPath(this.getResourceBase().getWorkPath() + "/comments.xsl", in, response.getUser());
        }
        catch (Exception e)
        {
            log.error(e);
        }

        response.setMode(response.Mode_ADMIN);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.GenericResource#processAction(javax.servlet.http.HttpServletRequest, org.semanticwb.portal.api.SWBActionResponse)
     */
    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException
    {
        if (isMultipart(request))
        {
            WBFileUpload upload = new WBFileUpload();
            upload.getFiles(request);
            String templateblog = upload.getFileName("templateblog");
            if (templateblog != null && !templateblog.equals(""))
            {
                addTemplateBlog(request, response, upload);
                response.setMode(response.Mode_ADMIN);
                return;
            }
            String templatecomments = upload.getFileName("templatecomments");
            if (templatecomments != null && !templatecomments.equals(""))
            {
                addTemplateComments(request, response, upload);
                response.setMode(response.Mode_ADMIN);
                return;
            }
        }
        else
        {

            String format_comments = request.getParameter("format_comments");
            if (format_comments != null && !format_comments.equals(""))
            {
                try
                {
                    SimpleDateFormat dateformat = new SimpleDateFormat(format_comments);
                    getResourceBase().setAttribute("format_comments", format_comments);
                    try
                    {
                        getResourceBase().updateAttributesToDB();
                    }
                    catch (SWBException e)
                    {
                        log.error(e);
                    }
                }
                catch (IllegalArgumentException iae)
                {
                    log.error(iae);
                    return;
                }
                response.setMode(response.Mode_ADMIN);
            }
            String format = request.getParameter("format");
            if (format != null && !format.equals(""))
            {
                try
                {                    
                    getResourceBase().setAttribute("format", format);
                    try
                    {
                        getResourceBase().updateAttributesToDB();
                    }
                    catch (SWBException e)
                    {
                        log.error(e);
                    }
                }
                catch (IllegalArgumentException iae)
                {
                    log.error(iae);
                    return;
                }
                response.setMode(response.Mode_ADMIN);
            }
            String deleteblog = request.getParameter("deleteblog");
            if (deleteblog != null && !deleteblog.equals(""))
            {
                deleteBlog(request, response, deleteblog);
            }
            String numofblogs = request.getParameter("numofblogs");
            if (numofblogs != null && !numofblogs.equals(""))
            {
                this.getResourceBase().setAttribute("numofblogs", String.valueOf(Integer.parseInt(numofblogs)));
                try
                {
                    getResourceBase().updateAttributesToDB();
                }
                catch (SWBException e)
                {
                    log.error(e);
                }
                response.setMode(response.Mode_ADMIN);

            }

            String anonimous = request.getParameter("anonimous");
            if (anonimous != null && !anonimous.equals(""))
            {
                this.getResourceBase().setAttribute("anonimous", String.valueOf(Boolean.parseBoolean(anonimous)));
                try
                {
                    getResourceBase().updateAttributesToDB();
                }
                catch (SWBException e)
                {
                    log.error(e);
                }
                response.setMode(response.Mode_ADMIN);
            }
            String numofcomments = request.getParameter("numofcomments");
            if (numofcomments != null && !numofcomments.equals(""))
            {
                this.getResourceBase().setAttribute("numofcomments", String.valueOf(Integer.parseInt(numofcomments)));
                try
                {
                    getResourceBase().updateAttributesToDB();
                }
                catch (SWBException e)
                {
                    log.error(e);
                }
                response.setMode(response.Mode_ADMIN);
                return;
            }


            Enumeration names = request.getParameterNames();
            while (names.hasMoreElements())
            {
                String name = (String) names.nextElement();
                if (name.startsWith("user_"))
                {

                    asignUser(request, response, this.getResourceBase().getAttribute("blogid"));
                    response.setMode(response.Mode_ADMIN);
                    return;
                }
            }
            names = request.getParameterNames();
            while (names.hasMoreElements())
            {
                String name = (String) names.nextElement();
                if (name.startsWith("role_"))
                {

                    asignRole(request, response, this.getResourceBase().getAttribute("blogid"));
                    response.setMode(response.Mode_ADMIN);
                    return;
                }
            }


            String type = request.getParameter("type");
            if (type != null && !type.equals(""))
            {
                response.setMode(type);
                return;
            }

            String blog = request.getParameter("blog");
            if (blog != null && !blog.equals(""))
            {
                asignBlog(request, response, blog);
                return;
            }


            String createBlog = request.getParameter("createblog");
            String asign = request.getParameter("asign");
            String name = request.getParameter("title");
            if (createBlog != null && name != null && !name.equals("") && createBlog.equals("true"))
            {
                createBlog(request, response, name, Boolean.parseBoolean(asign));
                return;
            }
            String sBlogId = response.getResourceBase().getAttribute("blogid");
            String spostid = request.getParameter("postid");
            String comment = request.getParameter("comment");
            String mode = request.getParameter("mode");

            if (sBlogId != null && !sBlogId.equals(""))
            {


                String blogid = sBlogId;
                if (spostid == null || spostid.equals(""))
                {
                    if (mode != null && !mode.equals(""))
                    {
                        if (mode.equals("create"))
                        {
                            addPost(request, response, blogid);
                        }
                    }
                }
                else
                {

                    String postid = spostid;
                    if (mode != null && !mode.equals(""))
                    {

                        if (mode.startsWith("update"))
                        {
                            updatePost(request, response, blogid, postid);
                        }
                        if (mode.startsWith("delete"))
                        {
                            deletePost(request, response, postid, blogid);
                        }
                    }
                    if (comment != null && !comment.equals(""))
                    {
                        insertComment(request, response, postid, blogid, comment);
                        response.setRenderParameter("blogid", String.valueOf(blogid));
                        response.setRenderParameter("postid", String.valueOf(postid));
                    }
                }
            }
        }
    }

    /**
     * Gets the delete image path.
     *
     * @param paramRequest the param request
     * @return the delete image path
     */
    private String getDeleteImagePath(SWBParamRequest paramRequest)
    {
        if(paramRequest==null)
        {
            return null;
        }
        InputStream indeleteblog = SWBBlog.class.getResourceAsStream(DELETE_FILE);
        String pathDeleteBlog = SWBPortal.getWebWorkPath() + this.getResourceBase().getWorkPath() + "/" + DELETE_FILE;
        try
        {
            InputStream in = SWBPortal.getFileFromWorkPath(this.getResourceBase().getWorkPath() + "/" + DELETE_FILE);
            if (in == null)
            {
                SWBPortal.writeFileToWorkPath(this.getResourceBase().getWorkPath() + "/" + DELETE_FILE, indeleteblog, paramRequest.getUser());
            }
        }
        catch (SWBException afe)
        {
            try
            {
                SWBPortal.writeFileToWorkPath(this.getResourceBase().getWorkPath() + "/" + DELETE_FILE, indeleteblog, paramRequest.getUser());
            }
            catch (SWBException afe2)
            {
                log.error(afe2);
            }
        }
        return pathDeleteBlog;
    }

    /**
     * Gets the adds the image path.
     *
     * @param paramRequest the param request
     * @return the adds the image path
     */
    private String getAddImagePath(SWBParamRequest paramRequest)
    {
        if(paramRequest==null)
        {
            return "";
        }
        InputStream indeleteblog = SWBBlog.class.getResourceAsStream(ADD_FILE);
        String pathDeleteBlog = SWBPortal.getWebWorkPath() + this.getResourceBase().getWorkPath() + "/" + ADD_FILE;
        try
        {
            InputStream in = SWBPortal.getFileFromWorkPath(this.getResourceBase().getWorkPath() + "/" + ADD_FILE);
            if (in == null)
            {
                SWBPortal.writeFileToWorkPath(this.getResourceBase().getWorkPath() + "/" + ADD_FILE, indeleteblog, paramRequest.getUser());
            }
        }
        catch (SWBException afe)
        {
            try
            {
                SWBPortal.writeFileToWorkPath(this.getResourceBase().getWorkPath() + "/" + ADD_FILE, indeleteblog, paramRequest.getUser());
            }
            catch (SWBException afe2)
            {
                log.error(afe2);
            }
        }
        return pathDeleteBlog;
    }

    /**
     * Gets the edits the image path.
     *
     * @param paramRequest the param request
     * @return the edits the image path
     */
    private String getEditImagePath(SWBParamRequest paramRequest)
    {
        if(paramRequest==null)
        {
            return "";
        }
        InputStream indeleteblog = SWBBlog.class.getResourceAsStream(EDIT_FILE);
        String pathDeleteBlog = SWBPortal.getWebWorkPath() + this.getResourceBase().getWorkPath() + "/" + EDIT_FILE;
        try
        {
            InputStream in = SWBPortal.getFileFromWorkPath(this.getResourceBase().getWorkPath() + "/" + EDIT_FILE);
            if (in == null)
            {
                SWBPortal.writeFileToWorkPath(this.getResourceBase().getWorkPath() + "/" + EDIT_FILE, indeleteblog, paramRequest.getUser());
            }
        }
        catch (SWBException afe)
        {
            try
            {
                SWBPortal.writeFileToWorkPath(this.getResourceBase().getWorkPath() + "/" + EDIT_FILE, indeleteblog, paramRequest.getUser());
            }
            catch (SWBException afe2)
            {
                log.error(afe2);
            }
        }
        return pathDeleteBlog;
    }

    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.GenericResource#doView(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.semanticwb.portal.api.SWBParamRequest)
     */
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        String pathDeleteBlog = getDeleteImagePath(paramRequest);
        String pathEditBlog = getEditImagePath(paramRequest);
        String pathAddBlog = getAddImagePath(paramRequest);
        PrintWriter out = response.getWriter();
        String sBlogId = this.getResourceBase().getAttribute("blogid");
        if (sBlogId != null && !sBlogId.equals(""))
        {
            try
            {
                Blog blog = Blog.ClassMgr.getBlog(sBlogId, paramRequest.getWebPage().getWebSite());
                Document doc = blog.toXML(getNumMaxOfBlogs(), paramRequest.getUser(), this.getResourceBase().getAttribute("format", defaultFormat), pathDeleteBlog, pathEditBlog, pathAddBlog);
                String sytylePath = this.getResourceBase().getAttribute("templateblog");
                String htmlDocument = null;
                InputStream styleStream = null;
                if (sytylePath == null)
                {
                    styleStream = SWBBlog.class.getResourceAsStream("blog.xsl");
                }
                else
                {
                    styleStream = SWBPortal.getFileFromWorkPath(this.getResourceBase().getWorkPath() + "/" + this.getResourceBase().getAttribute("templateblog").trim());
                }
                Templates templates = SWBUtils.XML.loadTemplateXSLT(styleStream);
                DOMOutputter Domout = new DOMOutputter();
                htmlDocument = SWBUtils.XML.transformDom(templates, Domout.output(doc));
                htmlDocument = htmlDocument.replaceAll("&lt;", "<");
                htmlDocument = htmlDocument.replaceAll("&gt;", ">");
                styleStream.close();
                out.println("<script type=\"text/javascript\">");
                InputStream script = SWBBlog.class.getResourceAsStream("blogscripts.js");
                SWBResourceURL url = paramRequest.getActionUrl();
                byte[] buffer = new byte[2048];
                int read = script.read(buffer);
                while (read != -1)
                {
                    String data = new String(buffer, 0, read, "UTF-8");
                    data = data.replaceAll("\\[urladd\\]", url.toString());
                    data = data.replaceAll("\\[blogid\\]", String.valueOf(blog.getId()));
                    data = data.replaceAll("\\[urldeletepost\\]", url.toString());
                    SWBResourceURL editPost = paramRequest.getRenderUrl();
                    editPost.setMode("editpost");
                    editPost.setCallMethod(url.Call_DIRECT);
                    data = data.replaceAll("\\[urleditpost\\]", editPost.toString());
                    SWBResourceURL urlviewComments = paramRequest.getRenderUrl();
                    urlviewComments.setMode("viewComents");

                    SWBResourceURL urladdComments = paramRequest.getRenderUrl();
                    urladdComments.setMode("addComent");

                    data = data.replaceAll("\\[urlviewComments\\]", urlviewComments.toString());
                    data = data.replaceAll("\\[urladdComments\\]", urladdComments.toString());
                    out.println(data);
                    read = script.read(buffer);
                }
                out.println("</script>");

                out.println(htmlDocument);
            }
            catch (NumberFormatException nfe) // Error
            {
                log.error(nfe);
            }
            catch (Exception nfe) // Error
            {
                log.error(nfe);
            }
        }
        out.close();
    }

    /**
     * Gets the comments.
     *
     * @param paramRequest the param request
     * @param blogid the blogid
     * @param postid the postid
     * @param urlBlog the url blog
     * @param showAll the show all
     * @param urlviewall the urlviewall
     * @return the comments
     * @throws SWBException the sWB exception
     */
    private synchronized Document getComments(SWBParamRequest paramRequest, String blogid, String postid, String urlBlog, boolean showAll, String urlviewall) throws SWBException
    {
        User wbUser = paramRequest.getUser();
        Document doc = new Document();
        Element comments = new Element("comments");
        final boolean anonimous = Boolean.parseBoolean(paramRequest.getResourceBase().getAttribute("anonimous", "true"));
        if (anonimous || paramRequest.getUser().isSigned())
        {
            comments.setAttribute("level", "1");
        }
        else
        {
            comments.setAttribute("level", "0");
        }
        comments.setAttribute("blogid", String.valueOf(blogid));
        comments.setAttribute("postid", String.valueOf(postid));
        if (showAll)
        {
            comments.setAttribute("viewall", "");
        }
        else
        {
            comments.setAttribute("viewall", urlviewall);
        }
        doc.addContent(comments);

        Blog blog = Blog.ClassMgr.getBlog(blogid, paramRequest.getWebPage().getWebSite());
        if (blog != null)
        {
            Post post = blog.getPost(postid);
            try
            {
                comments.setAttribute("title", post.getTitle());
                comments.setAttribute("name", blog.getTitle());
                comments.setAttribute("url", urlBlog);
                comments.setAttribute("comments", String.valueOf(post.getNumberOfComments()));
                comments.setAttribute("id", String.valueOf(post.getId()));
                comments.setAttribute("blogid", String.valueOf(blogid));
                comments.setAttribute("date", new SimpleDateFormat(this.getResourceBase().getAttribute("format", defaultFormat)).format(post.getFecha_alta()));
                comments.setAttribute("author", post.getUserPost().getFullName());
                Element eDescription = new Element("description");
                CDATA cDescription = new CDATA(post.getDescription());
                eDescription.addContent(cDescription);
                comments.addContent(eDescription);


                if (showAll)
                {
                    GenericIterator<Comment> ocomments = post.listComments();
                    while (ocomments.hasNext())
                    {
                        Comment ocomment = ocomments.next();
                        Element comment = new Element("comment");
                        comment.setAttribute("date", new SimpleDateFormat(this.getResourceBase().getAttribute("format_comments", defaultFormat)).format(ocomment.getFec_altaComment()));
                        User recuser = ocomment.getUserComment();

//                        String uid = userid.substring(0, userid.indexOf("_"));
//                        String repid = userid.substring(userid.indexOf("_") + 1);
//                        User recuser = UserRepository.ClassMgr.getUserRepository(repid).getUser(uid);

                        //System.out.println("getComments: recuser: "+recuser);

                        StringBuilder name = new StringBuilder("");
                        if (recuser == null)
                        {
                            name.append(paramRequest.getLocaleString("userAnonimous"));
                        }
                        else
                        {
                            name.append(recuser.getFullName());
                        }
                        String user = name.toString().trim();
                        comment.setAttribute("user", user);
                        comment.setText(ocomment.getComment());
                        comments.addContent(comment);
                    }

                }
                else
                {
                    int numofcomments = getNumMaxOfComments();
                    int i = 1;
                    GenericIterator<Comment> ocomments = post.listComments();
                    while (ocomments.hasNext())
                    {
                        Comment ocomment = ocomments.next();
                        try
                        {
                            Element comment = new Element("comment");
                            comment.setAttribute("date", new SimpleDateFormat(this.getResourceBase().getAttribute("format_comments", defaultFormat)).format(ocomment.getFec_altaComment()));
                            StringBuilder name = new StringBuilder("");
                            if (ocomment.getUserComment() == null)
                            {
                                name.append(paramRequest.getLocaleString("userAnonimous"));
                            }
                            else
                            {
                                name.append(ocomment.getUserComment().getFullName());
                            }
                            String user = name.toString().trim();
                            comment.setAttribute("user", user);
                            comment.setText(ocomment.getComment());
                            comments.addContent(comment);
                            if (i >= numofcomments)
                            {
                                break;
                            }
                            i++;
                        }
                        catch (Exception e)
                        {
                            log.error(e);
                        }
                    }
                }
            }
            catch (Exception e)
            {
                log.error(e);
            }
        }


        return doc;
    }

    /**
     * Do view comments.
     *
     * @param request the request
     * @param response the response
     * @param paramRequest the param request
     * @throws SWBResourceException the sWB resource exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void doViewComments(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        PrintWriter out = response.getWriter();
        String spostid = request.getParameter("postid");
        String sviewall = request.getParameter("viewall");
        boolean viewall = false;
        if (sviewall != null && !sviewall.equals(""))
        {
            viewall = Boolean.parseBoolean(sviewall);
        }
        String sBlogId = this.getResourceBase().getAttribute("blogid", "");
        if (sBlogId != null && !sBlogId.equals(""))
        {
            if (spostid != null && !spostid.equals(""))
            {
                String postid = spostid;
                String blogid = sBlogId;
                SWBResourceURL urlviewComents = paramRequest.getRenderUrl();
                urlviewComents.setMode("viewComents");
                urlviewComents.setCallMethod(urlviewComents.Call_CONTENT);
                urlviewComents.setParameter("viewall", "true");
                urlviewComents.setParameter("postid", String.valueOf(postid));
                Document doc = null;
                try
                {
                    doc = getComments(paramRequest, blogid, postid, paramRequest.getWebPage().getUrl(), viewall, urlviewComents.toString());
                }
                catch (SWBException e)
                {
                    log.error(e);
                }
                String sytylePath = this.getResourceBase().getAttribute("templatecomments");
                String htmlDocument = null;
                InputStream styleStream = null;
                if (sytylePath == null)
                {
                    styleStream = SWBBlog.class.getResourceAsStream("comments.xsl");

                }
                else
                {
                    try
                    {
                        styleStream = SWBPortal.getFileFromWorkPath(this.getResourceBase().getWorkPath() + "/" + this.getResourceBase().getAttribute("templatecomments").trim());
                    }
                    catch (SWBException e)
                    {
                        log.error(e);
                    }
                }
                try
                {
                    Templates templates = SWBUtils.XML.loadTemplateXSLT(styleStream);
                    DOMOutputter Domout = new DOMOutputter();
                    htmlDocument = SWBUtils.XML.transformDom(templates, Domout.output(doc));
                    styleStream.close();
                    out.println("<script type=\"text/javascript\">");
                    InputStream script = SWBBlog.class.getResourceAsStream("comments.js");
                    SWBResourceURL url = paramRequest.getActionUrl();
                    byte[] buffer = new byte[2048];
                    int read = script.read(buffer);
                    while (read != -1)
                    {
                        String data = new String(buffer, 0, read);
                        data = data.replaceAll("\\[urltoAdd\\]", url.toString());
                        url.setMode("viewComents");
                        data = data.replaceAll("\\[url_comment_all\\]", url.toString() + "?viewall=true&postid=" + postid);
                        out.println(data);
                        read = script.read(buffer);
                    }
                    out.println("</script>");
                    htmlDocument = htmlDocument.replaceAll("&lt;", "<");
                    htmlDocument = htmlDocument.replaceAll("&gt;", ">");
                    out.println(htmlDocument);
                }
                catch (Exception e)
                {
                    log.error(e);
                }
            }
        }
    }

    /**
     * Creates the form.
     *
     * @param out the out
     * @param paramRequest the param request
     * @throws SWBResourceException the sWB resource exception
     */
    private void createForm(PrintWriter out, SWBParamRequest paramRequest) throws SWBResourceException
    {
        out.println("<script type=\"text/javascript\">");
        out.println("function createBlog(forma)");
        out.println("{");
        out.println("if(forma.name.value=='')");
        out.println("{");
        out.println("alert('" + paramRequest.getLocaleString("msgBlogName") + "');");
        out.println("return;");
        out.println("}");
        out.println("forma.submit();");
        out.println("}");
        out.println("</script>");
        out.println("<div class=\"swbform\"><form action='" + paramRequest.getActionUrl() + "' method='post'><fieldset>");
        out.println("<input type='hidden' name='createblog' value='true'><table width='100%' cellpadding='5' cellspacing='0'><tr><td >" + paramRequest.getLocaleString("nameBlog") + "</td><td><input class='valores' type='text' name='title' size='50' maxlength='200'></td></tr>");
        out.println("<tr><td colspan='2' ><input class='valores' CHECKED type='radio' name='asign' value='true'>" + paramRequest.getLocaleString("showBlogContent") + "</td></tr>");
        out.println("<tr><td colspan='2' ><input class='valores' type='radio' name='asign' value='false'>" + paramRequest.getLocaleString("notshowBlogContent") + "</td></tr>");
        out.println("<tr><td colspan='2' ><input class='boton' type='button' name='save' value='" + paramRequest.getLocaleString("createBlog") + "' onClick='javascript:createBlog(this.form)'></td></tr>");
        SWBResourceURL urlback = paramRequest.getRenderUrl();
        urlback.setMode(urlback.Mode_ADMIN);
        out.println("</td></tr><tr><td colspan='2' ><a href='" + urlback + "'>" + paramRequest.getLocaleString("back") + "</a></td></tr></table></fieldset></form></div>");
    }

    /**
     * Do change template blog.
     *
     * @param request the request
     * @param response the response
     * @param paramRequest the param request
     * @throws SWBResourceException the sWB resource exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void doChangeTemplateBlog(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        PrintWriter out = response.getWriter();
        SWBResourceURL url = paramRequest.getActionUrl();
        out.println(getScript(paramRequest));
        out.println("<div class=\"swbform\"><form name='frmResource' method='post' enctype='multipart/form-data' action='" + url.toString() + "'><fieldset>");
        out.println("<table width='100%'  border='0' cellpadding='5' cellspacing='0'><tr><td >" + paramRequest.getLocaleString("templatefileBlog") + ":</td><td><input class='valores' type=\"file\" name=\"templateblog\" size=\"40\" onChange=\"isFileType(this, 'xsl|xslt');\" /></td></tr><tr><td colspan='2' >"
                + "<input type='submit' name='btnSave' class='boton' value='" + paramRequest.getLocaleString("save") + "' onClick=\"if(jsValida(this.form)) return true; else return false;\">"
                + "</td></tr>");
        SWBResourceURL urlback = paramRequest.getRenderUrl();
        urlback.setMode(urlback.Mode_ADMIN);
        out.println("</td></tr><tr><td colspan='2' ><a href='" + urlback + "'>" + paramRequest.getLocaleString("back") + "</a></td></tr></table>");

        out.println("</fieldset></form></div>");
    }

    /**
     * Checks if is multipart.
     *
     * @param request the request
     * @return true, if is multipart
     */
    private static boolean isMultipart(HttpServletRequest request)
    {
        boolean isMultipart = false;
        if (request.getContentType() != null)
        {
            if (request.getContentType().indexOf("multipart/form-data") != -1)
            {
                isMultipart = true;
            }
        }
        return isMultipart;
    }

    /**
     * Do change template comments.
     *
     * @param request the request
     * @param response the response
     * @param paramRequest the param request
     * @throws SWBResourceException the sWB resource exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void doChangeTemplateComments(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        PrintWriter out = response.getWriter();
        SWBResourceURL url = paramRequest.getActionUrl();
        out.println(getScript(paramRequest));
        out.println("<div class=\"swbform\"><form name='frmResource' method='post' enctype='multipart/form-data' action='" + url.toString() + "'><fieldset>");
        out.println("<table width='100%'  border='0' cellpadding='5' cellspacing='0'><tr><td >" + paramRequest.getLocaleString("templateFileComments") + ":</td><td><input type=\"file\" name=\"templatecomments\" size=\"40\" onChange=\"isFileType(this, 'xsl|xslt');\" /></td></tr><tr><td colspan='2' >"
                + "<input type='submit' name='btnSave' class='boton' value='" + paramRequest.getLocaleString("save") + "' onClick=\"if(jsValida(this.form)) return true; else return false;\">"
                + "</td></tr>");
        SWBResourceURL urlback = paramRequest.getRenderUrl();
        urlback.setMode(urlback.Mode_ADMIN);
        out.println("</td></tr><tr><td colspan='2' ><a href='" + urlback + "'>" + paramRequest.getLocaleString("back") + "</a></td></tr></table>");
        out.println("</fieldset></form></div>");
    }

    /**
     * Gets the script.
     *
     * @param paramRequest the param request
     * @return the script
     */
    private String getScript(SWBParamRequest paramRequest)
    {
        StringBuilder ret = new StringBuilder("");
        WBAdmResourceUtils admResUtils = new WBAdmResourceUtils();
        try
        {
            ret.append("\n<script language=javascript>");
            ret.append("\nfunction jsValida(pForm) {");
            ret.append("\n   if(!isFileType(pForm.template, 'xsl|xslt')) return false;");
            ret.append("\n   return true; ");
            ret.append("\n }     ");
            ret.append(admResUtils.loadIsFileType());
            ret.append("\n</script>");
        }
        catch (Exception e)
        {
            log.error(e);
        }
        return ret.toString();
    }

    /**
     * Asigna un blog � crea un blog.
     *
     * @param request the request
     * @param response the response
     * @param paramRequest the param request
     * @throws SWBResourceException the sWB resource exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @Override
    public void doAdmin(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        PrintWriter out = response.getWriter();
        out.println("<div class=\"swbform\">");
        out.println("<fieldset>");
        out.println("<table width='100%'  border='0' cellpadding='5' cellspacing='0'> ");
        String sBlogId = this.getResourceBase().getAttribute("blogid");
        if (sBlogId == null || sBlogId.equals(""))
        {
            out.println("<tr><td >");
            createForm(out, paramRequest);
            out.println("</td></tr>");

        }
        else
        {
            try
            {
                Blog blog = Blog.ClassMgr.getBlog(sBlogId, paramRequest.getWebPage().getWebSite()); //new Blog(Integer.parseInt(sBlogId), paramRequest.getUser(), this.getResourceBase().getAttribute("format", defaultFormat), pathDeleteBlog, pathEditBlog,pathAddBlog);
                if (blog != null)
                {
                    out.println("<tr><td >" + paramRequest.getLocaleString("blogAsigned") + ": <b>" + blog.getTitle() + "</td></tr>");
                }
            }
            catch (IllegalArgumentException iae)
            {
                log.error("Error al tratar de buscar un blog, en el recurso " + this.getResourceBase().getId() + ", en la p�gina: " + paramRequest.getWebPage().getId() + ", En el sitio " + paramRequest.getWebPage().getWebSiteId());
                log.error(iae);
                out.println("<tr><td >");
                createForm(out, paramRequest);
                out.println("</td></tr>");
                this.getResourceBase().setAttribute("blogid", null);
                try
                {
                    this.getResourceBase().updateAttributesToDB();
                }
                catch (SWBException e)
                {
                    log.error(e);
                }

            }
        }
        out.println("<tr><td>&nbsp;</td></tr>");

        SWBResourceURL url = paramRequest.getRenderUrl();
        url.setMode("asignblog");
        url.setCallMethod(url.Call_CONTENT);
        out.println("<tr><td ><a href='" + url + "'>" + paramRequest.getLocaleString("asignblog") + "</a></td></tr>");
        url.setMode("createBlog");
        url.setCallMethod(url.Call_CONTENT);
        out.println("<tr><td ><a href='" + url + "'>" + paramRequest.getLocaleString("createblog") + "</a></td></tr>");


        url.setMode("changeSettings");
        url.setCallMethod(url.Call_CONTENT);
        out.println("<tr><td ><a href='" + url + "'>" + paramRequest.getLocaleString("config") + "</a></td></tr>");

        if (sBlogId != null && !sBlogId.equals(""))
        {
            url.setMode("permissions");
            url.setCallMethod(url.Call_CONTENT);
            out.println("<tr><td ><a href='" + url + "?blogid=1&postid=1'>" + paramRequest.getLocaleString("asignpermissions") + "</a></td></tr>");
        }

        out.println("<tr><td>&nbsp;</td></tr>");

        url.setMode("ViewBlogXML");
        url.setCallMethod(url.Call_DIRECT);
        out.println("<tr><td ><a href='" + url + "' target='_blank'>" + paramRequest.getLocaleString("viewXMLBlog") + "</a></td></tr>");

        out.println("<tr><td>&nbsp;</td></tr>");

        url.setMode("viewTemplateBlog");
        url.setCallMethod(url.Call_DIRECT);
        out.println("<tr><td ><a href='" + url + "' target='_blank'>" + paramRequest.getLocaleString("viewTemplateBlog") + "</a></td></tr>");

        url.setMode("changeTemplateBlog");
        url.setCallMethod(url.Call_CONTENT);
        out.println("<tr><td ><a href='" + url + "'>" + paramRequest.getLocaleString("changeTemplateBlog") + "</a></td></tr>");

        out.println("<tr><td>&nbsp;</td></tr>");

        url.setMode("viewTemplateComments");
        url.setCallMethod(url.Call_DIRECT);
        out.println("<tr><td ><a href='" + url + "' target='_blank'>" + paramRequest.getLocaleString("viewTemplateComments") + "</a></td></tr>");


        url.setMode("changeTemplateComments");
        url.setCallMethod(url.Call_CONTENT);
        out.println("<tr><td ><a href='" + url + "'>" + paramRequest.getLocaleString("changeTemplateComments") + "</a></td></tr>");


        out.println("<tr><td>&nbsp;</td></tr>");

        url.setMode("viewTemplateByDefaultBlog");
        url.setCallMethod(url.Call_DIRECT);
        out.println("<tr><td ><a href='" + url + "' target='_blank'>" + paramRequest.getLocaleString("viewBlogTemplateExample") + "</a></td></tr>");

        url.setMode("viewTemplateByDefaultComments");
        url.setCallMethod(url.Call_DIRECT);
        out.println("<tr><td ><a href='" + url + "' target='_blank'>" + paramRequest.getLocaleString("viewCommentsTemplateExample") + "</a></td></tr>");

        out.println("<tr><td>&nbsp;</td></tr>");

        url.setMode("deleteBlog");
        url.setCallMethod(url.Call_CONTENT);
        out.println("<tr><td ><a href='" + url + "'>" + paramRequest.getLocaleString("deleteBlog") + "</a></td></tr>");

        out.println("</table>");
        out.println("</fieldset>");
        out.println("</div>");
        out.close();
    }
}
