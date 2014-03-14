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

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.GenericObject;
import org.semanticwb.model.Resource;
import org.semanticwb.model.Role;
import org.semanticwb.model.User;
import org.semanticwb.model.UserGroup;
import org.semanticwb.model.VersionInfo;
import org.semanticwb.model.WebPage;
import org.semanticwb.model.WebSite;
import org.semanticwb.platform.SemanticOntology;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;
//import org.semanticwb.process.model.RepositoryDirectory;
import org.semanticwb.model.RepositoryFile;
import org.semanticwb.portal.api.SWBResourceURLImp;

/**
 *
 * @author juan.fernandez
 */
public class LiteFileRepository extends GenericResource
{

    private Logger log = SWBUtils.getLogger(LiteFileRepository.class);
    private SimpleDateFormat format = new SimpleDateFormat("dd/MMM/yy hh:mm");
    private static final String MODE_GETFILE = "getFile";
    private static final String DEFAULT_MIME_TYPE = "application/octet-stream";
    private static final String LVL_VIEW = "prop_view";
    private static final String LVL_MODIFY = "prop_modify";
    private static final String LVL_ADMIN = "prop_admin";
    private static final String CHK_FOLDERSUPPORT = "prop_foldersupport";
    private static final NumberFormat numf = NumberFormat.getNumberInstance();

    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        if (paramRequest.getMode().equals(MODE_GETFILE))
        {
            doGetFile(request, response, paramRequest);
        } else
        {
            super.processRequest(request, response, paramRequest);
        }

    }

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        response.setContentType("text/html; charset=ISO-8859-1");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");

        Resource base = getResourceBase();

        numf.setMaximumFractionDigits(1);

        String path = SWBPlatform.getContextPath() + "/swbadmin/images/repositoryfile/";
        PrintWriter out = response.getWriter();
        String suri = request.getParameter("suri");
        User usr = paramRequest.getUser();
        String lang = usr.getLanguage();

        int luser = getLevelUser(usr);

        WebPage repoDir = paramRequest.getWebPage();

        String action = request.getParameter("act");
        if (null == action)
        {
            action = "";
        }

        if ("".equals(action))
        {

            SWBResourceURL urlorder = paramRequest.getRenderUrl();
            //urlorder.setParameter("parentUUID", parentUUID);

            out.println("<div id=\"ProcessFileRepository\">");
            out.println("<table width=\"100%\">");
            out.println("<thead>");
            out.println("<tr>");
            out.println("<th>");
            out.println("Id");
            out.println("</th>");
            out.println("<th>");
            out.println("<a href=\"" + urlorder + "?orderBy=type\" title=\"Ordenar por tipo\">" + "Tipo" + "</a>");
            out.println("</th>");
            out.println("<th>");
            out.println("<a href=\"" + urlorder + "?orderBy=title\" title=\"Ordenar por nombre\">" + "Nombre" + "</a>");
            out.println("</th>");
            out.println("<th>");
            out.println("Versión");
            out.println("</th>");
            out.println("<th>");
            out.println("<a href=\"" + urlorder + "?orderBy=date\" title=\"Ordenar por fecha de modificación\">" + "Modificado" + "</a>");
            out.println("</th>");
            out.println("<th>");
            out.println("<a href=\"" + urlorder + "?orderBy=usr\" title=\"Ordenar por usuario que lo modificó.\">" + "Modificado por" + "</a>");
            out.println("</th>");
            out.println("<th>");
            out.println("Acción");
            out.println("</th>");
            out.println("</tr>");
            out.println("</thead>");

            out.println("<tbody>");

            Iterator<RepositoryFile> itrf = RepositoryFile.ClassMgr.listRepositoryFileByRepositoryFileDirectory(repoDir);

            ///// ORDENADO DE ARCHIVOS SEGUN OPCIÓN
            String orderBy = request.getParameter("orderBy");
            if (null == orderBy)
            {
                orderBy = "title";
            }

            HashMap<String, RepositoryFile> hmNodes = new HashMap<String, RepositoryFile>();

            while (itrf.hasNext())
            {
                RepositoryFile repoFile = itrf.next();

                VersionInfo version = repoFile.getActualVersion();
                String skey = repoFile.getId();

                if (orderBy.equals("title"))
                {
                    skey = repoFile.getDisplayTitle(lang) + " - " + repoFile.getId();

                } else if (orderBy.equals("date"))
                {
                    //nodo.getProperty("jcr:created").getDate().getTime())
                    skey = version.getCreated().getTime() + " - " + repoFile.getDisplayTitle(lang) + " - " + repoFile.getId();

                } else if (orderBy.equals("type"))
                {
                    String file = version.getVersionFile();
                    String type = getFileName(file);

                    skey = type + "-" + repoFile.getDisplayTitle(lang) + " - " + repoFile.getId();
                    //hmNodes.put(skey, repoFile);
                } else if (orderBy.equals("usr"))
                {
                    User usrc = version.getCreator();

                    skey = " - " + repoFile.getDisplayTitle(lang) + " - " + repoFile.getId();

                    if (usrc != null)
                    {
                        skey = usrc.getFullName() + skey;
                    }
                }
                hmNodes.put(skey, repoFile);
            }

            ArrayList list = new ArrayList(hmNodes.keySet());
            Collections.sort(list);

            //// TERMINA ORDENADO
            /// DESPLIEGUE DE ARCHIVOS ENCONTRADOS
            Iterator<String> lnit = list.iterator();
            while (lnit.hasNext())
            {

                String skey = lnit.next();

                RepositoryFile repositoryFile = hmNodes.get(skey);
                VersionInfo vi = repositoryFile.getLastVersion();

                if (vi == null)
                {
                    repositoryFile.remove();
                    continue;
                }

                out.println("<tr>");
                out.println("<td>");
                String fid = repositoryFile.getId();
                out.println(fid);
                out.println("</td>");

                out.println("<td>");
                SWBResourceURL urldetail = paramRequest.getRenderUrl();
                urldetail.setParameter("act", "detail");
                urldetail.setParameter("fid", fid);

                String file = "";
                String type = "";

                if (vi != null && vi.getVersionFile() != null)
                {
                    file = vi.getVersionFile();
                    type = getFileName(file);
                }

                if (luser > 0)
                {
//                    SWBResourceURL urlview = paramRequest.getRenderUrl();
//                    urlview.setCallMethod(SWBResourceURL.Call_DIRECT);
//                    urlview.setParameter("fid", fid);
//                    urlview.setMode(MODE_GETFILE);
//                    urlview.setParameter("verNum", "" + vi.getVersionNumber());
//
//                    out.println("<a href=\"" + urlview + "\">");
//                    out.println("<img border=0 src='" + path + "" + type + "' alt=\"" + getFileType(file) + "\" />");
//                    out.println("</a>");

                    //String ulrdirecta = SWBPortal.getWebWorkPath()+repositoryFile.getWorkPath()+"/"+vi.getVersionNumber()+"/"+vi.getVersionFile();
                    String ulrdirecta = paramRequest.getRenderUrl().setCallMethod(SWBResourceURL.Call_DIRECT).setMode(MODE_GETFILE).setParameter("fid", repositoryFile.getId()).setParameter("verNum", "" + vi.getVersionNumber()).toString();
                    out.println("<a href=\"" + ulrdirecta + "\">");
                    out.println("<img border=0 src='" + path + "" + type + "' alt=\"" + getFileType(file) + "\" />");
                    out.println("</a>");
                } else
                {
                    out.println("<img border=0 src='" + path + "" + type + "' alt=\"" + getFileType(file) + "\" />");
                }

                out.println("</td>");
                out.println("<td>");
                out.println(repositoryFile.getDisplayTitle(usr.getLanguage()));
                out.println("</td>");
                out.println("<td>");
                out.println(vi.getVersionValue());
                out.println("</td>");
                out.println("<td align=\"center\">");
                out.println(vi != null && vi.getUpdated() != null ? format.format(vi.getUpdated()) : "--");
                out.println("</td>");
                out.println("<td>");
                out.println(vi != null && vi.getModifiedBy() != null && vi.getModifiedBy().getFullName() != null ? vi.getModifiedBy().getFullName() : "--");
                out.println("</td>");

                out.println("<td>");

                out.println("<a href=\"" + urldetail + "\"><img src=\"" + path + "info.gif\" border=\"0\" alt=\"ver detalle\"/>");

                out.println("</a>");

                if (luser == 3 || (vi.getCreator() != null && vi.getCreator().equals(usr) && luser > 1))
                {
                    SWBResourceURL urlremove = paramRequest.getActionUrl();
                    urlremove.setAction("removefile");
                    urlremove.setParameter("act", "remove");
                    urlremove.setParameter("fid", fid);
                    out.println("<a href=\"#\" onclick=\"if(confirm('¿Estás seguro de querer eliminar este archivo?')){window.location='" + urlremove + "';} else {return false;}\">");
                    out.println("<img src=\"" + path + "delete.gif\" border=\"0\" alt=\"eliminar\"/>");
                    out.println("</a>");
                }

                out.println("</td>");

                out.println("</tr>");
            }
            out.println("</tbody>");
            out.println("<tfoot>");
            out.println("<tr>");
            out.println("<td colspan=\"5\">");

            if (luser >= 2)
            {
                SWBResourceURL urlnew = paramRequest.getRenderUrl();
                urlnew.setParameter("act", "new");
                out.println("<button onclick=\"window.location='" + urlnew + "';\">" + "Agregar archivo" + "</button>");

                if (base.getAttribute(CHK_FOLDERSUPPORT, "1").equals("1"))
                {
                    SWBResourceURL urlnewDirectory = paramRequest.getRenderUrl();
                    urlnewDirectory.setParameter("act", "newDirectory");
                    out.println("<button onclick=\"window.location='" + urlnewDirectory + "';\">" + "Agregar carpeta" + "</button>");
                }
            }

            out.println("</td>");
            out.println("</tr>");
            out.println("</tfoot>");
            out.println("</table>");
            out.println("</div>");

        } else if ("detail".equals(action))
        {
            String fid = request.getParameter("fid");
            RepositoryFile repoFile = RepositoryFile.ClassMgr.getRepositoryFile(fid, repoDir.getWebSite());
            out.println("<div id=\"ProcessFileRepository\">");
            out.println("<table>");
            out.println("<tbody>");
            out.println("<tr>");
            out.println("<td align=\"right\">");
            out.println("Título:");
            out.println("</td>");
            out.println("<td>");
            out.println(repoFile.getDisplayTitle(usr.getLanguage()));
            out.println("</td>");
            out.println("</tr>");
            out.println("<tr>");
            out.println("<td align=\"right\">");
            out.println("Descripción:");
            out.println("</td>");
            out.println("<td>");
            out.println(repoFile.getDisplayDescription(usr.getLanguage()));
            out.println("</td>");
            out.println("</tr>");
            out.println("<tr>");
            out.println("<td align=\"right\">");
            out.println("Archivo:");
            out.println("</td>");
            out.println("<td>");
            VersionInfo vl = repoFile.getLastVersion();
            out.println(vl.getVersionFile());
            out.println("</td>");
            out.println("</tr>");
            out.println("<tr>");
            out.println("<td align=\"right\">");
            out.println("Versión:");
            out.println("</td>");
            out.println("<td>");
            out.println(vl.getVersionValue());

            SWBResourceURL urlverhistoy = paramRequest.getRenderUrl();
            urlverhistoy.setParameter("act", "history");
            urlverhistoy.setParameter("fid", fid);

            out.println("(<a href=\"" + urlverhistoy + "\">versiones</a>)");
            out.println("</td>");
            out.println("</tr>");

            if (luser >= 2 || (vl.getCreator() != null && vl.getCreator().equals(usr) && luser > 1))
            {
                out.println("<tr>");
                out.println("<td align=\"right\">");
                out.println("Agregar nueva Versión:");
                out.println("</td>");

                SWBResourceURL urlnewVer = paramRequest.getRenderUrl();

                out.println("<td>");
                out.println("<script type=\"text/javascript\">");
                out.println("function validaVersion()");
                out.println("  {");
                out.println("       if(document.frmNewVersion.newVersion.value=='0')");
                out.println("       {");
                out.println("          alert('Selecciona número de versión a agregar.');");
                out.println("       }");
                out.println("       else { document.frmNewVersion.submit(); }");
                out.println("  }");
                out.println("</script>");
                out.println("<form id=\"frmNewVersion\" name=\"frmNewVersion\" method=\"post\" action=\"" + urlnewVer + "\" onsubmit=\"validaVersion();return false;\">");
                out.println("<input type=\"hidden\" name=\"act\" value=\"new\">");
                out.println("<input type=\"hidden\" name=\"fid\" value=\"" + fid + "\">");
                out.println("<select id=\"newVersion\" name=\"newVersion\" >");
                out.println("<option value=\"0\">--</option>");

                double fver = Double.parseDouble(vl.getVersionValue());
                fver = fver + 0.10D;

                String sfver = numf.format(fver);
                if (sfver.indexOf(".") == -1)
                {
                    sfver = "" + (float) fver;
                }

                int iver = (int) fver;
                iver = iver + 1;

                out.println("<option value=\"fraction\">" + sfver + "</option>");
                out.println("<option value=\"nextInt\">" + (float) iver + "</option>");

                out.println("</select>");
                out.println("<button type=\"submit\">Agregar</button>");
                out.println("</form>");
                out.println("</td>");
                out.println("</tr>");

            }
            out.println("<tr>");
            out.println("<td align=\"right\">");
            out.println("Creado:");
            out.println("</td>");
            out.println("<td>");
            out.println(vl.getCreated() != null ? format.format(vl.getCreated()) : "--");
            out.println("</td>");
            out.println("</tr>");
            out.println("<tr>");
            out.println("<td align=\"right\">");
            out.println("Usuario Creador:");
            out.println("</td>");
            out.println("<td>");
            out.println(vl.getCreator() != null ? vl.getCreator().getFullName() : "--");
            out.println("</td>");
            out.println("</tr>");
            out.println("</tbody>");
            out.println("<tfoot>");
            out.println("<tr>");
            out.println("<td colspan=\"2\" align=\"right\">");

            if (luser == 3 || (vl.getCreator() != null && vl.getCreator().equals(usr) && luser > 1))
            {
                SWBResourceURL urlremove = paramRequest.getActionUrl();
                urlremove.setAction("removefile");
                urlremove.setParameter("act", "remove");
                urlremove.setParameter("fid", fid);
                out.println("<button type=\"button\" onclick=\"if(confirm('¿Estás seguro de querer eliminar este archivo?')){window.location='" + urlremove + "';} else {return false;}\">Eliminar");
                //out.println("<img src=\"" + path + "delete.gif\" border=\"0\" alt=\"eliminar\"/>");
                out.println("</button>");
            }

            SWBResourceURL urlbck = paramRequest.getRenderUrl();
            urlbck.setParameter("act", "");
            out.println("<button onclick=\"window.location='" + urlbck + "';\">Regresar</button>");
            out.println("</td>");
            out.println("</tr>");
            out.println("</tbody>");
            out.println("</table>");
            out.println("</div>");
        } else if ("history".equals(action))
        {
            String fid = request.getParameter("fid");
            RepositoryFile repoFile = RepositoryFile.ClassMgr.getRepositoryFile(fid, repoDir.getWebSite());
            VersionInfo ver = null;
            VersionInfo vl = repoFile.getLastVersion();
            if (null != vl)
            {
                ver = vl;
                while (ver.getPreviousVersion() != null)
                { //
                    ver = ver.getPreviousVersion();
                }
            }
            if (ver != null)
            {
                out.println("<div id=\"ProcessFileRepository\">");
                out.println("<table width=\"100%\">");
                out.println("<thead>");
                out.println("<tr>");
                out.println("<td align=\"right\">");
                out.println("Título:");
                out.println("</td>");
                out.println("<td>");
                out.println(repoFile.getDisplayTitle(usr.getLanguage()));
                out.println("</td>");
                out.println("</tr>");
                out.println("<tr>");
                out.println("<td align=\"right\">");
                out.println("Descripción:");
                out.println("</td>");
                out.println("<td>");
                out.println(repoFile.getDisplayDescription(usr.getLanguage()));
                out.println("</td>");
                out.println("</tr>");
                out.println("<tr>");
                out.println("<td align=\"right\">");
                out.println("Archivo:");
                out.println("</td>");
                out.println("<td>");
                out.println(ver.getVersionFile());
                out.println("</td>");
                out.println("</tr>");
                out.println("<tr>");
                out.println("<th>");
                out.println("&nbsp;");// espacio para liga ver archivo
                out.println("</th>");
                out.println("<th>");
                out.println("Versión");
                out.println("</th>");
                out.println("<th>");
                out.println("Fecha versión");
                out.println("</th>");
                out.println("<th>");
                out.println("Creado por");
                out.println("</th>");
                out.println("<th>");
                out.println("Comentario");
                out.println("</th>");
                out.println("</tr>");
                out.println("</thead>");
                out.println("<tbody>");
                while (ver != null)
                {
                    //lista de las versiones del archivo

                    out.println("<tr>");
                    out.println("<td align=\"center\" >");

                    String file = "";
                    String type = "";

                    if (ver != null && ver.getVersionFile() != null)
                    {
                        file = ver.getVersionFile();
                        type = getFileName(file);
                    }

                    if (luser > 0)
                    {
                        SWBResourceURL urlview = paramRequest.getRenderUrl();
                        urlview.setCallMethod(SWBResourceURL.Call_DIRECT);
                        urlview.setParameter("fid", fid);
                        urlview.setMode(MODE_GETFILE);
                        urlview.setParameter("verNum", "" + ver.getVersionNumber());

                        out.println("<a href=\"" + urlview + "\">");
                        out.println("<img border=0 src='" + path + "" + type + "' alt=\"" + getFileType(file) + "\" />");
                        out.println("</a>");
                    } else
                    {
                        out.println("<img border=0 src='" + path + "" + type + "' alt=\"" + getFileType(file) + "\" />");
                    }

//                    if (luser > 0) {
//                        SWBResourceURL urlview = paramRequest.getRenderUrl();
//                        urlview.setCallMethod(SWBResourceURL.Call_DIRECT);
//                        urlview.setParameter("fid", fid);
//                        urlview.setMode(MODE_GETFILE);
//                        urlview.setParameter("verNum", "" + ver.getVersionNumber());
//
//                        out.println("<a href=\"" + urlview + "\">ver</a>");
//                    } else {
//                        out.println("---");
//                    }
                    out.println("</td>");
                    out.println("<td align=\"center\">");
                    out.println(ver.getVersionValue());
                    out.println("</td>");
                    out.println("<td>");
                    out.println(ver.getCreated() != null ? format.format(ver.getCreated()) : "--");
                    out.println("</td>");
                    out.println("<td>");
                    out.println(ver.getCreator() != null ? ver.getCreator().getFullName() : "--");

                    out.println("</td>");
                    out.println("<td>");
                    out.println(ver.getVersionComment() != null ? ver.getVersionComment() : "--");

                    out.println("</td>");
                    out.println("</tr>");

                    ver = ver.getNextVersion();

                }
                out.println("</tbody>");
                out.println("<tfoot>");
                out.println("<tr>");
                out.println("<td colspan=\"2\" align=\"right\">");
                SWBResourceURL urlbck = paramRequest.getRenderUrl();
                urlbck.setParameter("act", "detail");
                urlbck.setParameter("fid", fid);
                out.println("<button onclick=\"window.location='" + urlbck + "';\">Regresar</button>");
                out.println("</td>");
                out.println("</tr>");
                out.println("</tfoot>");
                out.println("</table>");
                out.println("</div>");
            }

        } else if ("new".equals(action))
        {

            SWBResourceURL urlnew = paramRequest.getActionUrl();
            urlnew.setAction("newfile");
            urlnew.setParameter("act", "newfile");

            String fid = request.getParameter("fid");
            String newVersion = request.getParameter("newVersion");
            String sNxtVersion = "";
            RepositoryFile repoFile = null;
            String stitle = "";
            String sdescription = "";
            VersionInfo vl = null;
            if (null != fid && null != newVersion)
            {
                boolean incremento = Boolean.FALSE;
                if (newVersion.equals("nextInt"))
                {
                    incremento = Boolean.TRUE;
                } else if (newVersion.equals("fraction"))
                {
                    incremento = Boolean.FALSE;
                }

                repoFile = RepositoryFile.ClassMgr.getRepositoryFile(fid, repoDir.getWebSite());

                stitle = repoFile.getDisplayTitle(usr.getLanguage());
                sdescription = repoFile.getDisplayDescription(usr.getLanguage());

                vl = repoFile.getLastVersion();
                double fver = Double.parseDouble(vl.getVersionValue());
                fver = fver + 0.10D;

                String sfver = numf.format(fver);
                if (sfver.indexOf(".") == -1)
                {
                    sfver = "" + (float) fver;
                }

                int iver = (int) fver;
                iver = iver + 1;

                sNxtVersion = sfver;

                if (incremento)
                {
                    sNxtVersion = "" + (float) iver;
                }
            }

            out.println("<script type=\"text/javascript\" >");
            out.println("function valida() ");
            out.println("{ ");
            out.println("   if(document.frmnewdoc.ftitle.value=='') ");
            out.println("     { ");
            out.println("         alert('Defina el título'); ");
            out.println("         return; ");
            out.println("     } ");
            out.println("   if(document.frmnewdoc.fdescription.value=='') ");
            out.println("     { ");
            out.println("         alert('Indique la descripción'); ");
            out.println("         return; ");
            out.println("     } ");
            out.println("   if(document.frmnewdoc.ffile.value=='') ");
            out.println("     { ");
            out.println("         alert('Defina un archivo'); ");
            out.println("         return; ");
            out.println("     } ");

            if (null != fid && null != newVersion)
            {
                out.println("   var filename = document.frmnewdoc.ffile.value;");
                out.println("   if(filename.indexOf('" + vl.getVersionFile() + "')==-1) ");
                out.println("     { ");
                out.println("         alert('Archivo seleccionado inválido. Debe ser " + vl.getVersionFile() + "'); ");
                out.println("         return; ");
                out.println("     } ");
            }

            out.println("   document.frmnewdoc.submit();");
            out.println("} ");
            out.println("</script>");

            out.println("<div id=\"ProcessFileRepository\">");
            out.println("<form id=\"frmnewdoc\" name=\"frmnewdoc\" method=\"post\" action=\"" + urlnew + "\"  enctype=\"multipart/form-data\" onsubmit=\"valida();return false;\">");

            if (null != fid && null != newVersion)
            {
                out.println("<input type=\"hidden\" name=\"newVersion\" value=\"" + newVersion + "\">");
                out.println("<input type=\"hidden\" name=\"fid\" value=\"" + fid + "\">");
                //out.println("<input type=\"hidden\" name=\"fcurrent\" value=\"" + vl.getVersionFile() + "\">");
            }

            out.println("<table>");
            out.println("<tbody>");
            out.println("<tr>");
            out.println("<td align=\"right\">");
            out.println("Título:");
            out.println("</td>");
            out.println("<td>");
            out.println("<input type=\"text\" name=\"ftitle\" value=\"" + stitle + "\">");
            out.println("</td>");
            out.println("</tr>");
            out.println("<tr>");
            out.println("<td align=\"right\">");
            out.println("Descripción:");
            out.println("</td>");
            out.println("<td>");
            out.println("<textarea name=\"fdescription\">" + sdescription + "</textarea>");
            out.println("</td>");
            out.println("</tr>");
            out.println("<tr>");
            out.println("<td align=\"right\">");
            out.println("Comentario:");
            out.println("</td>");
            out.println("<td>");
            out.println("<textarea name=\"fcomment\"></textarea>");
            out.println("</td>");
            out.println("</tr>");

            if (null != fid && null != newVersion)
            {

                out.println("<tr>");
                out.println("<td align=\"right\">");
                out.println("Versión del archivo a agregar:");
                out.println("</td>");
                out.println("<td>");
                out.println(sNxtVersion);
                out.println("</td>");
                out.println("</tr>");
            }

            out.println("<tr>");
            out.println("<td align=\"right\">");
            out.println("Archivo:");
            out.println("</td>");
            out.println("<td>");

            out.println("<input type=\"file\" name=\"ffile\">");
            out.println("</td>");
            out.println("</tr>");

            out.println("</tbody>");
            out.println("<tfoot>");
            out.println("<tr>");
            out.println("<td colspan=\"2\" align=\"right\">");
            out.println("<button type=\"submit\" >Agregar</button>");
            SWBResourceURL urlbck = paramRequest.getRenderUrl();
            urlbck.setParameter("act", "");
            out.println("<button type=\"button\" onclick=\"window.location='" + urlbck + "';\">Regresar</button>");
            out.println("</td>");
            out.println("</tr>");
            out.println("</tbody>");
            out.println("</table>");
            out.println("</form>");
            out.println("</div>");
        } else if ("newDirectory".equals(action))
        {

            SWBResourceURL urlnew = paramRequest.getActionUrl();
            urlnew.setAction("newDir");
            urlnew.setParameter("act", "newDir");

            out.println("<script type=\"text/javascript\" >");
            out.println("function validaDir() ");
            out.println("{ ");
            out.println("   if(document.frmDir.d_title.value=='') ");
            out.println("     { ");
            out.println("         alert('Defina el título'); ");
            out.println("         return; ");
            out.println("     } ");
            out.println("   if(document.frmDir.d_description.value=='') ");
            out.println("     { ");
            out.println("         alert('Indique la descripción'); ");
            out.println("         return; ");
            out.println("     } ");
            out.println("   document.frmDir.submit();");
            out.println("} ");
            out.println("</script>");

            out.println("<div id=\"ProcessFileRepository\">");
            out.println("<form id=\"frmDir\" name=\"frmDir\" method=\"post\" action=\"" + urlnew + "\" onsubmit=\"validaDir();return false;\">");
            out.println("<table>");
            out.println("<tbody>");
            out.println("<tr>");
            out.println("<td colspan=\"2\" align=\"right\">Agregar Nueva Carpeta");
            out.println("</td>");
            out.println("</tr>");
            out.println("<tr>");
            out.println("<td align=\"right\">");
            out.println("Título:");
            out.println("</td>");
            out.println("<td>");
            out.println("<input type=\"text\" name=\"d_title\" value=\"\">");
            out.println("</td>");
            out.println("</tr>");
            out.println("<tr>");
            out.println("<td align=\"right\">");
            out.println("Descripción:");
            out.println("</td>");
            out.println("<td>");
            out.println("<textarea name=\"d_description\"></textarea>");
            out.println("</td>");
            out.println("</tr>");
            out.println("</tbody>");
            out.println("<tfoot>");
            out.println("<tr>");
            out.println("<td colspan=\"2\" align=\"right\">");
            out.println("<button type=\"submit\" >Agregar</button>");
            SWBResourceURL urlbck = paramRequest.getRenderUrl();
            urlbck.setParameter("act", "");
            out.println("<button type=\"button\" onclick=\"window.location='" + urlbck + "';\">Regresar</button>");
            out.println("</td>");
            out.println("</tr>");
            out.println("</tbody>");
            out.println("</table>");
            out.println("</form>");
            out.println("</div>");
        }
    }

    public void doGetFile(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {

        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");

        String fid = request.getParameter("fid");
        String verNumber = request.getParameter("verNum");
        int intVer = 1;
        if (verNumber != null)
        {
            intVer = Integer.parseInt(verNumber);
        }
        RepositoryFile repoFile = RepositoryFile.ClassMgr.getRepositoryFile(fid, paramRequest.getWebPage().getWebSite());
        VersionInfo ver = null;
        VersionInfo vl = repoFile.getLastVersion();
        if (null != vl)
        {
            ver = vl;
            while (ver.getPreviousVersion() != null)
            { //
                if (ver.getVersionNumber() == intVer)
                {
                    break;
                }
                ver = ver.getPreviousVersion();
            }
        }
        try
        {
            response.setContentType(DEFAULT_MIME_TYPE);
            response.setHeader("Content-Disposition", "attachment; filename=\"" + ver.getVersionFile() + "\";");

            OutputStream out = response.getOutputStream();
            //SWBUtils.IO.copyStream(new FileInputStream(SWBPortal.getWorkPath() + repoFile.getWorkPath() + "/" + verNumber + "/" + ver.getVersionFile()), out);
            // hace la corrección
            String dir = SWBPortal.getWorkPath() + repoFile.getWorkPath() + "/" + verNumber + "/";
            File fdir = new File(dir);
            File fileTest = new File(dir + ver.getVersionFile());
            if (!fileTest.exists() && fdir.exists())
            {
                File[] files = fdir.listFiles();
                if (files != null && files.length == 1 && files[0].isFile())
                {
                    String newFile = dir + repoFile.getId();
                    FileInputStream fin = new FileInputStream(files[0]);
                    FileOutputStream fout = new FileOutputStream(new File(newFile));
                    byte[] cont = new byte[1024];
                    int read = fin.read(cont);
                    while (read != -1)
                    {
                        fout.write(cont, 0, read);
                        read = fin.read(cont);
                    }
                    fin.close();
                    fout.close();
                }
                //SWBUtils.IO.copyStream(new FileInputStream(dir + repoFile.getId()), out);
                FileInputStream fin = new FileInputStream(dir + repoFile.getId());
                byte[] cont = new byte[1024];
                int read = fin.read(cont);
                while (read != -1)
                {
                    out.write(cont, 0, read);
                    read = fin.read(cont);
                }
                fin.close();
                out.close();
            } else
            {
                //SWBUtils.IO.copyStream(new FileInputStream(dir + ver.getVersionFile()), out);
                FileInputStream fin = new FileInputStream(dir + ver.getVersionFile());
                byte[] cont = new byte[1024];
                int read = fin.read(cont);
                while (read != -1)
                {
                    out.write(cont, 0, read);
                    read = fin.read(cont);
                }
                fin.close();
                out.close();
            }

        } catch (Exception e)
        {
            log.error("Error al obtener el archivo del Repositorio de documentos.", e);
        }
    }

    @Override
    public void doAdmin(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        response.setContentType("text/html; charset=ISO-8859-1");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");

        String id = getResourceBase().getId();

        PrintWriter out = response.getWriter();
        String accion = paramRequest.getAction();
        if (accion == null)
        {
            accion = "";
        }
        User user = paramRequest.getUser();

        WebPage wpage = paramRequest.getWebPage();

        WebSite wsite = getResourceBase().getWebSite(); //wpage.getWebSite();

        out.println("<div class=\"swbform\">");

        if (accion.equals("edit"))
        {

            SWBResourceURL urlA = paramRequest.getActionUrl();
            urlA.setAction("admin_update");

            out.println("<form id=\"" + id + "_myform_repfile\"  name=\"" + id + "_myform_repfile\" action=\"" + urlA.toString() + "\" method=\"post\" >"); //onsubmit=\"submitForm('" + id + "_myform_repfile');return false;\"

            out.println("<fieldset>");
            out.println("<legend>");
            out.println(paramRequest.getLocaleString("msgFileRepositoryRes"));
            out.println("</legend>");

            out.println("<table width=\"100%\" border=\"0\" >");

            ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            out.println("<tr><td colspan=\"2\"><B>" + paramRequest.getLocaleString("msgRolesDefinitionLevel") + "</B></td></tr>");
            out.println("<tr><td align=\"right\" width=150>" + paramRequest.getLocaleString("msgView") + ":</td>");
            out.println("<td><select name=\"ver\">" + getSelectOptions("ver", wsite, paramRequest) + "</select></td></tr>");
            out.println("<tr><td align=\"right\" width=150>" + paramRequest.getLocaleString("msgModify") + ":</td>");
            out.println("<td><select name=\"modificar\">" + getSelectOptions("modificar", wsite, paramRequest) + "</select></td></tr>");
            out.println("<tr><td align=\"right\"  width=150>" + paramRequest.getLocaleString("msgAdministrate") + ":</td>");
            out.println("<td><select name=\"administrar\">" + getSelectOptions("administrar", wsite, paramRequest) + "</select></td></tr>");

            Resource base = getResourceBase();

            String checked = "checked";
            if (base.getAttribute(CHK_FOLDERSUPPORT, "1").equals("0"))
            {
                checked = "";
            }

            out.println("<tr><td colspan=\"2\"><hr/></td></tr>");
            out.println("<tr><td align=\"right\"  width=150>" + paramRequest.getLocaleString("msgFolderSupport") + ":</td>");
            out.println("<td><input type=\"checkbox\" name=\"foldersupport\" value=\"1\" " + checked + " /></td></tr>");

            out.println("</table>");
            out.println("</fieldset>");
            out.println("<fieldset>");
            out.println("<button dojoType=\"dijit.form.Button\" type=\"submit\" id=\"" + id + "btn\" name=\"btn\" >" + paramRequest.getLocaleString("msgBTNAccept"));
            out.println("</button>");
            out.println("</fieldset>");

            out.println("<fieldset>");
            out.println("<br> * " + paramRequest.getLocaleString("msgNote") + ": " + paramRequest.getLocaleString("msgRolesDependent"));

            out.println("</fieldset>");
            out.println("</form>");
        }

        out.println("</div>");
    }

    public String getSelectOptions(String type, WebSite wsite, SWBParamRequest paramRequest)
    {
        String strTemp = "";
        try
        {

            Resource base = getResourceBase();
            User user = paramRequest.getUser();

            String selectedItem = "";
            if (type.equals("ver"))
            {
                selectedItem = base.getAttribute(LVL_VIEW, "0");
            } else if (type.equals("modificar"))
            {
                selectedItem = base.getAttribute(LVL_MODIFY, "0");

            } else if (type.equals("administrar"))
            {
                selectedItem = base.getAttribute(LVL_ADMIN, "0");
            }

            strTemp = "<option value=\"-1\">" + paramRequest.getLocaleString("msgNoRolesAvailable") + "</option>";

            Iterator<Role> iRoles = wsite.getUserRepository().listRoles(); //DBRole.getInstance().getRoles(topicmap.getDbdata().getRepository());
            StringBuilder strRules = new StringBuilder("");
            strRules.append("\n<option value=\"0\">").append(paramRequest.getLocaleString("msgSelNone")).append("</option>");
            strRules.append("\n<optgroup label=\"Roles\">");
            while (iRoles.hasNext())
            {
                Role oRole = iRoles.next();
                strRules.append("\n<option value=\"").append(oRole.getURI()).append("\" ").append(selectedItem.equals(oRole.getURI()) ? "selected" : "").append(">").append(oRole.getDisplayTitle(user.getLanguage())).append("</option>");
            }
            strRules.append("\n</optgroup>");

            strRules.append("\n<optgroup label=\"User Groups\">");
            Iterator<UserGroup> iugroups = wsite.getUserRepository().listUserGroups();
            while (iugroups.hasNext())
            {
                UserGroup oUG = iugroups.next();
                strRules.append("\n<option value=\"" + oUG.getURI() + "\" " + (selectedItem.equals(oUG.getURI()) ? "selected" : "") + ">" + oUG.getDisplayTitle(user.getLanguage()) + "</option>");
            }
            strRules.append("\n</optgroup>");
            if (strRules.toString().length() > 0)
            {
                strTemp = strRules.toString();
            }

        } catch (Exception e)
        {
            log.error("Error al cargal opciones de selección. getSelectOptions()", e);
        }

        return strTemp;
    }

    public int getLevelUser(User user)
    {
        int level = 0;

        if (null == user)
        {
            return level;
        }

        Resource base = getResourceBase();

        String uriView = base.getAttribute(LVL_VIEW, "0");
        String uriModify = base.getAttribute(LVL_MODIFY, "0");
        String uriAdmin = base.getAttribute(LVL_ADMIN, "0");

        SemanticOntology ont = SWBPlatform.getSemanticMgr().getOntology();
        GenericObject gobj = null;
        try
        {
            gobj = ont.getGenericObject(uriAdmin);
        } catch (Exception e)
        {
            //log.error("Errror getLevelUser()",e);
        }

        UserGroup ugrp = null;
        Role urole = null;

        if (!uriAdmin.equals("0"))
        {
            if (gobj != null)
            {
                if (gobj instanceof UserGroup)
                {
                    ugrp = (UserGroup) gobj;
                    if (user.hasUserGroup(ugrp))
                    {
                        level = 3;
                    }
                } else if (gobj instanceof Role)
                {
                    urole = (Role) gobj;
                    if (user.hasRole(urole))
                    {
                        level = 3;
                    }
                }
            } else
            {
                level = 3;
            }
        } else
        {
            level = 3;
        }

        if (level == 0)
        {
            if (!uriModify.equals("0"))
            {
                gobj = ont.getGenericObject(uriModify);
                if (gobj != null)
                {
                    if (gobj instanceof UserGroup)
                    {
                        ugrp = (UserGroup) gobj;
                        if (user.hasUserGroup(ugrp))
                        {
                            level = 2;
                        }
                    } else if (gobj instanceof Role)
                    {
                        urole = (Role) gobj;
                        if (user.hasRole(urole))
                        {
                            level = 2;
                        }
                    }
                } else
                {
                    level = 2;
                }
            } else
            {
                level = 2;
            }
        }

        if (level == 0)
        {
            if (!uriView.equals("0"))
            {
                gobj = ont.getGenericObject(uriView);
                if (gobj != null)
                {
                    if (gobj instanceof UserGroup)
                    {
                        ugrp = (UserGroup) gobj;
                        if (user.hasUserGroup(ugrp))
                        {
                            level = 1;
                        }
                    } else if (gobj instanceof Role)
                    {
                        urole = (Role) gobj;
                        if (user.hasRole(urole))
                        {
                            level = 1;
                        }
                    }
                } else
                {
                    level = 1;
                }
            } else
            {
                level = 1;
            }
        }

        return level;
    }

    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException
    {

        WebPage wpage = response.getWebPage();
        WebSite wsite = wpage.getWebSite();
        String action = response.getAction();
        if (action == null)
        {
            action = "";
        }

        if ("newfile".equals(action))
        {
            org.semanticwb.portal.util.FileUpload fup = new org.semanticwb.portal.util.FileUpload();
            fup.getFiles(request, null);
            String originalName = fup.getFileName("ffile");
            String ftitle = fup.getValue("ftitle");
            String fdescription = fup.getValue("fdescription");
            String fcomment = fup.getValue("fcomment");

            String fid = fup.getValue("fid");
            String newVersion = fup.getValue("newVersion");

            byte[] bcont = fup.getFileData("ffile");

            WebPage repoDir = response.getWebPage();
            RepositoryFile repoFile = null;
            boolean incremento = Boolean.FALSE;
            if (fid != null)
            {
                repoFile = RepositoryFile.ClassMgr.getRepositoryFile(fid, repoDir.getWebSite());
                if (newVersion != null && newVersion.equals("nextInt"))
                {
                    incremento = Boolean.TRUE;
                }
            } else
            {
                repoFile = RepositoryFile.ClassMgr.createRepositoryFile(repoDir.getWebSite());
                repoFile.setRepositoryFileDirectory(repoDir);
            }

            repoFile.setTitle(ftitle);
            repoFile.setDescription(fdescription);

            if (originalName.lastIndexOf('/') != -1)
            {
                int pos = originalName.lastIndexOf('/');
                originalName = originalName.substring(pos + 1);
            }
            if (originalName.lastIndexOf('\\') != -1)
            {
                int pos = originalName.lastIndexOf('\\');
                originalName = originalName.substring(pos + 1);
            }

            String fname = repoFile.getId();

            storeFile(originalName, fname, new ByteArrayInputStream(bcont), fcomment, incremento, repoFile);

        } else if ("removefile".equals(action))
        {
            String fid = request.getParameter("fid");
            WebPage repoDir = response.getWebPage();
            RepositoryFile repoFile = RepositoryFile.ClassMgr.getRepositoryFile(fid, repoDir.getWebSite());
            repoFile.remove();
        } else if ("admin_update".equals(action))
        {
            String viewrole = request.getParameter("ver");
            String modifyrole = request.getParameter("modificar");
            String adminrole = request.getParameter("administrar");
            String folderSupport = request.getParameter("foldersupport");
            if (folderSupport == null || folderSupport.equals("null"))
            {
                folderSupport = "0";
            }

            System.out.println("admin---=>" + adminrole + ">|||>>>" + folderSupport);

            try
            {
                getResourceBase().setAttribute(LVL_VIEW, viewrole);
                getResourceBase().setAttribute(LVL_MODIFY, modifyrole);
                getResourceBase().setAttribute(LVL_ADMIN, adminrole);
                getResourceBase().setAttribute(CHK_FOLDERSUPPORT, folderSupport);
                getResourceBase().updateAttributesToDB();

            } catch (Exception e)
            {
                log.error("Error al guardar configuración de niveles de usuario de ProcessFileRepository", e);
            }
            response.setMode(SWBActionResponse.Mode_ADMIN);
            response.setAction("edit");
        } else if ("newDir".equals(action))
        {

            String title = request.getParameter("d_title");
            String description = request.getParameter("d_description");
            String idnewwp = SWBPlatform.getIDGenerator().getID(title, null);

            try
            {

                WebPage wp = wsite.getWebPage(idnewwp);

                if (wp != null)
                {
                    idnewwp = "dir_" + idnewwp;
                }

                wp = wsite.getWebPage(idnewwp);

                if (wp == null)
                {
                    wp = wsite.createWebPage(idnewwp);

                    wp.setTitle(title);
                    wp.setDescription(description);
                    wp.setSortName(title);
                    wp.setActive(Boolean.TRUE);
                    wp.setParent(wpage);

                    Resource res = wsite.createResource();
                    res.setResourceType(getResourceBase().getResourceType());
                    res.setTitle("Repositorio de documentos");
                    res.setActive(Boolean.TRUE);

                    res.setAttribute(LVL_VIEW, getResourceBase().getAttribute(LVL_VIEW, "0"));
                    res.setAttribute(LVL_MODIFY, getResourceBase().getAttribute(LVL_MODIFY, "0"));
                    res.setAttribute(LVL_ADMIN, getResourceBase().getAttribute(LVL_ADMIN, "0"));

                    res.updateAttributesToDB();
                    wp.addResource(res);

                    SWBResourceURLImp url = new SWBResourceURLImp(request, res, wp, SWBResourceURL.UrlType_RENDER);
                    response.sendRedirect(url.toString());

                }
            } catch (Exception e)
            {
                log.error("Error al agregar carpeta al repositorio de documentos.");
            }
        }
    }

    public OutputStream storeFile(String originalName, String name, String comment, boolean bigVersionInc, RepositoryFile repoFile) throws FileNotFoundException
    {
        VersionInfo v = VersionInfo.ClassMgr.createVersionInfo(repoFile.getRepositoryFileDirectory().getWebSite());

        v.setVersionFile(originalName);

        numf.setMaximumFractionDigits(1);

        if (comment != null)
        {
            v.setVersionComment(comment);
        }
        VersionInfo vl = repoFile.getLastVersion();
        String sver = "1.0";
        int ver = 1;
        if (vl != null)
        {
            vl.setNextVersion(v);
            v.setPreviousVersion(vl);
            ver = vl.getVersionNumber();
            sver = vl.getVersionValue();

            double f = Double.parseDouble(sver);

            if (bigVersionInc)
            {
                f = (int) f + 1;
                sver = "" + (float) f;
            } else
            {
                f = f + 0.10D;

                String sfver = numf.format(f);
                if (sfver.indexOf(".") == -1)
                {
                    sfver = "" + (float) f;
                }
                sver = sfver;
            }
            ver++;
        }
        v.setVersionNumber(ver);
        v.setVersionValue(sver);
        repoFile.setActualVersion(v);
        repoFile.setLastVersion(v);

        File file = new File(SWBPortal.getWorkPath() + repoFile.getWorkPath() + "/" + ver);
        file.mkdirs();
        return new FileOutputStream(SWBPortal.getWorkPath() + repoFile.getWorkPath() + "/" + ver + "/" + name);
    }

    /**
     * Almacena el archivo en la ruta predefinida del RepositoryFile, Si no
     * existe ninguna version crea una nueva Si existe una version anterior
     * agrega una nueva versión
     *
     * @param originalName
     * @param name
     * @param out
     * @param comment
     * @param bigVersionInc
     */
    public void storeFile(String originalName, String name, InputStream in, String comment, boolean bigVersionInc, RepositoryFile repoFile)
    {
        try
        {
            OutputStream out = storeFile(originalName, name, comment, bigVersionInc, repoFile);
            SWBUtils.IO.copyStream(in, out);
        } catch (IOException e)
        {
            log.error(e);
        }
    }

    public String getFileType(String filename)
    {
        String file = "Document";
        String type = filename.toLowerCase();
        if (type.indexOf(".bmp") != -1)
        {
            file = "Image";
        } else if (type.indexOf(".pdf") != -1)
        {
            file = "Adobe Acrobat";
        } else if (type.indexOf(".xls") != -1 || type.indexOf(".xlsx") != -1)
        {
            file = "Microsoft Excel";
        } else if (type.indexOf(".html") != -1 || type.indexOf(".htm") != -1)
        {
            file = "HTML file";
        } else if (type.indexOf("jpg") != -1 || type.indexOf("jpeg") != -1)
        {
            file = "Image";
        } else if (type.indexOf(".ppt") != -1 || type.indexOf(".pptx") != -1)
        {
            file = "Microsoft Power Point";
        } else if (type.indexOf(".vsd") != -1)
        {
            file = "Microsoft Visio";
        } else if (type.indexOf(".mpp") != -1)
        {
            file = "Microsoft Project";
        } else if (type.indexOf(".mmap") != -1)
        {
            file = "Mind Manager";
        } else if (type.indexOf(".exe") != -1)
        {
            file = "Application";
        } else if (type.indexOf(".txt") != -1)
        {
            file = "Text file";
        } else if (type.indexOf(".properties") != -1)
        {
            file = "Properties file";
        } else if (type.indexOf(".doc") != -1 || type.indexOf(".docx") != -1)
        {
            file = "Microsoft Word";
        } else if (type.indexOf(".xml") != -1)
        {
            file = "XML file";
        } else if (type.indexOf(".gif") != -1 || type.indexOf(".png") != -1)
        {
            file = "Image";
        } else if (type.indexOf(".avi") != -1)
        {
            file = "Media file";
        } else if (type.indexOf(".mp3") != -1)
        {
            file = "Audio file";
        } else if (type.indexOf(".wav") != -1)
        {
            file = "Audio file";
        } else if (type.indexOf(".xsl") != -1)
        {
            file = "XSLT file";
        }
        return file;
    }

    public String getFileName(String filename)
    {
        String file = "ico_default2.gif";
        String type = filename.toLowerCase();
        if (type.indexOf(".bmp") != -1)
        {
            file = "ico_bmp.gif";
        } else if (type.indexOf(".pdf") != -1)
        {
            file = "ico_acrobat.gif";
        } else if (type.indexOf(".xls") != -1 || type.indexOf(".xlsx") != -1)
        {
            file = "ico_excel.gif";
        } else if (type.indexOf(".html") != -1 || type.indexOf(".htm") != -1)
        {
            file = "ico_html.gif";
        } else if (type.indexOf("jpg") != -1 || type.indexOf("jpeg") != -1)
        {
            file = "ico_jpeg.gif";
        } else if (type.indexOf(".ppt") != -1 || type.indexOf(".pptx") != -1)
        {
            file = "ico_powerpoint.gif";
        } else if (type.indexOf(".exe") != -1)
        {
            file = "ico_program.gif";
        } else if (type.indexOf(".txt") != -1 || type.indexOf(".properties") != -1)
        {
            file = "ico_text.gif";
        } else if (type.indexOf(".doc") != -1 || type.indexOf(".docx") != -1)
        {
            file = "ico_word.gif";
        } else if (type.indexOf(".xml") != -1 || type.indexOf(".xsl") != -1)
        {
            file = "ico_xml.gif";
        } else if (type.indexOf(".mmap") != -1)
        {
            file = "ico_mindmanager.GIF";
        } else if (type.indexOf(".gif") != -1)
        {
            file = "ico_gif.gif";
        } else if (type.indexOf(".avi") != -1)
        {
            file = "ico_video.gif";
        } else if (type.indexOf(".mp3") != -1)
        {
            file = "ico_audio.gif";
        } else if (type.indexOf(".wav") != -1)
        {
            file = "ico_audio.gif";
        }
        return file;
    }
}
