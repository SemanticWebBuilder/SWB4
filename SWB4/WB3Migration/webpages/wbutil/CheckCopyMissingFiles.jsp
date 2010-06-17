<%--
    Document   : CheckCopyMissingFiles
    Created on : 10/06/2010, 12:15:37 PM
    Author     : juan.fernandez
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="org.semanticwb.*,org.semanticwb.portal.api.*,org.semanticwb.platform.*,org.semanticwb.model.*,org.semanticwb.model.base.*,com.infotec.topicmaps.bean.*,com.infotec.topicmaps.*,java.util.*,com.infotec.wb.core.db.*,org.semanticwb.repository.Workspace" %>
<%@page import="java.io.*, java.net.*, java.util.*, java.sql.*, org.w3c.dom.*"%>
<!-- %@page import="org.semanticwb.migration.office.*"% -->
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <style type="text/css">
            body
            {
                font-family:verdana;
                font-size:12px;
            }
            li  { list-style: none }
        </style>
    </head>
<%
    String path2=null,site=null, webworkPath=null;;
    String action = null;
    if(request.getParameter("act")!=null) action = request.getParameter("act");
    if(request.getParameter("path2")!=null&&request.getParameter("path2").trim().length()>0) path2=request.getParameter("path2");
    if(request.getParameter("site")!=null&&request.getParameter("site").trim().length()>0) site=request.getParameter("site");

    if(webworkPath==null) webworkPath="/work";
%>
    <body>
        <h2>Revision de rutas en Contenidos para copiar archivos faltantes</h2>
        <%
            if (null == action || path2 == null) {
        %>
        <div>
            <form name="fusrrep" id="fusrrep" method="post" action="">
                <input type="hidden" name="act" value="selsite" />
                <fieldset>
                    <legend>&nbsp;&nbsp;Proporciona la información que se te pide&nbsp;&nbsp;</legend>
                    <ul>
                        <li><label for="path2">Ruta de la carpeta work de wb3.2 a utilizar:</label>
                            <input type="text" id="path2" name="path2" size="70" value="<%=(path2 == null ? "D:/Java/tomcat6.0.20/webapps/ROOT/work/" : path2)%>"></li>
                    </ul>
                </fieldset>
                <fieldset>
                    <button type="submit" name="bsend" id="bsend">Enviar</button>
                </fieldset>
            </form>
        </div>
        <%
            } else if(null != action && action.equals("selsite") && path2!=null&&path2.trim().length()>0){
        %>
                <div>
            <form name="fusrrep" id="fusrrep" method="post" action="">
                <input type="hidden" name="act" value="findfiles" />
                <input type="hidden" name="path2" value="<%=path2%>" />
                <fieldset>
                    <legend>&nbsp;&nbsp;Selecciona sitio a revisar:&nbsp;&nbsp;</legend>
                    <ul>
        <%
                Iterator<WebSite> it = WebSite.ClassMgr.listWebSites();
                while (it.hasNext()) {
                    WebSite ws = it.next();
        %>
                        <li><input type="radio" id="<%=ws.getId()%>" name="site" value="<%=ws.getId()%>" ><label for="<%=ws.getId()%>"><%=ws.getDisplayTitle("es")%>&nbsp;</label></li>
        <%
                }
        %>
                    </ul>
                </fieldset>
                <fieldset>
                    <button type="submit" name="bsend" id="bsend">Enviar</button>
                </fieldset>
            </form>
        </div>

        <%
            } else if(null != action && action.equals("findfiles") && path2!=null&&path2.trim().length()>0){
        %>
        <div>
            <fieldset>
                <legend>Buscando/Revisando archivos....</legend>
            <ul>
        <%
                String separador = "/";
                if(path2!=null&&path2.indexOf("/")!=-1) separador = "/";
                else separador="\\";
                if(path2.lastIndexOf(separador)+1!=path2.length())
                {
                    path2=path2+separador;
                }
                String strocc = "";
                int resver = 1;
                int nres=0;
                int ncorrected=0;
                WebSite ws = WebSite.ClassMgr.getWebSite(site);
                Iterator<Resource> itres = ws.listResources();
                while (itres.hasNext()) {
                    Resource res = itres.next();
                    if(res!=null)
                    {
                        if(res.getResourceType()!=null && !res.getResourceType().getId().equals("HTMLContent")) continue;
                        nres++;
                        if(res instanceof Versionable)
                        {
                            org.semanticwb.model.VersionInfo vi = ((Versionable)res).getActualVersion();
                            if( null!=vi ) resver = vi.getVersionNumber();
                        }
                        String swbPath = SWBPortal.getWorkPath()+res.getWorkPath()+separador+resver+separador;
                        String wb32path = "/work/sites/"+site+"/resources/LocalContent/"+res.getId()+"";
                        System.out.println("Revisando recurso: "+res.getId());
                        String rutaorigen = path2+"/sites/"+site+"/resources/LocalContent/"+res.getId()+"/";
                        String rutadestino = "/work/models/"+site+"/Resource/"+res.getId()+"/"+resver+"/";
                        File dir = new File(swbPath);
                        if(dir!=null && dir.exists()&&dir.isDirectory())
                        {
                            File[] listado = dir.listFiles();
                            for(int i=0; i<listado.length; i++)
                            {
                                if(listado[i].isFile()&&
                                    (listado[i].getName().endsWith(".html")||
                                     listado[i].getName().endsWith(".htm") ||
                                     listado[i].getName().endsWith(".html.orig")||
                                     listado[i].getName().endsWith(".htm.orig")))
                                {
                                    File arch = listado[i];
                                    //System.out.println("Archivo: "+arch.getName());
                                    FileInputStream source = null;
                                    String content = null;
                                    try
                                    {
                                        source = new FileInputStream(arch);
                                        {
                                            content = readInputStream(source);
                                            while(content.indexOf(wb32path)>0)
                                            {
                                                ncorrected++;
                                                strocc=content.substring(content.indexOf(wb32path),content.indexOf(wb32path)+wb32path.length());
                                                int endInx = content.indexOf(wb32path)+wb32path.length();
                                                int endInx2 = endInx;
                                                int numchars=findNextSlash(content,endInx2,0,2,0)+1;

                                                if(numchars==0) break;

                                                strocc=content.substring(content.indexOf(wb32path),endInx+numchars);
                                                rutaorigen = rutaorigen.substring(0,rutaorigen.indexOf("/work"))+strocc;
                                                //copiar de la ruta strocc encontrada a la ruta del recurso de swb4
                                                File fsource = new File(rutaorigen);
                                                if(fsource!=null && fsource.exists() && fsource.isDirectory())
                                                {
                                                    //copiado de archivos
                                                    System.out.println("copiado de archivos");
                                                    File[] listsf = fsource.listFiles();
                                                    for(int j=0; j<listsf.length; j++)
                                                    {
                                                        File sf2cp = listsf[j];
                                                        if(sf2cp!=null)
                                                        {
                                                            if(sf2cp.isFile())
                                                            {
                                                                //revisar que el archivo no exista en el directorio de swb
                                                                File targetFile = new File(swbPath+sf2cp.getName());
                                                                if(targetFile!=null&&!targetFile.exists())
                                                                {
                                                                   copyStream(new FileInputStream(sf2cp), new FileOutputStream(targetFile));
                                                                }
                                                            }
                                                            else
                                                            {
                                                                //copiar estructura
                                                                File targetFile = new File(swbPath+sf2cp.getName());
                                                                copyStructure(sf2cp.getCanonicalPath()+"/", targetFile.getCanonicalPath()+"/");
                                                            }
                                                        }
                                                    }
                                                }
                                                // reemplazar ocurrencia encontrad con ruta actual de swb
                                                System.out.println("corrigiendo rutas...:"+strocc+" , "+rutadestino);
                                                content = content.replaceAll(strocc, rutadestino);
                                            }
                                        }
                                        copyStream(getStreamFromString(content),new FileOutputStream(arch));

                                    } catch (Exception e)
                                    {
                                        System.out.println("Error al revisar/copiar LocalContent."+e.getMessage());
                                    } finally
                                    {
                                        if (source != null)
                                        {
                                            try
                                            {
                                                source.close();
                                            } catch (IOException e)
                                            { // o esta cerrado o hubo un error, no importa
                                            }
                                        }
                                    }

                                }
                            }
                        }
                    }
                }
        %>
        <li>Se revisaron <%=nres%> LocalContent</li>
        <li>Se hicieron <%=ncorrected%> correcciones de rutas a los archivos.</li>

            </ul>
            </fieldset>
        </div>
        <%
            }
        %>
    </body>
</html>
<%!
        int bufferSize = 8192;

        int findNextSlash(String content, int endInx, int find, int limit, int numchars)
        {
            try
            {
                numchars++;
                if(content.substring(endInx, endInx+1).equals("/"))
                {
                    endInx++;
                    find++;
                }
                if(find<limit)
                {
                    numchars=findNextSlash(content,endInx+1,find,limit,numchars);
                }
            }
            catch(Exception e)
                {
                    numchars=-1;
                }
            return numchars;
        }

        void copyStream(InputStream in, OutputStream out) throws IOException
        {
            copyStream(in, out, bufferSize);
        }

        void copyStream(InputStream in, OutputStream out, int bufferSize) throws IOException
        {
            if (in == null)
            {
                throw new IOException("Input Stream null");
            }
            if (out == null)
            {
                throw new IOException("Ouput Stream null");
            }
            byte[] bfile = new byte[bufferSize];
            int x;
            while ((x = in.read(bfile, 0, bufferSize)) > -1)
            {
                out.write(bfile, 0, x);
            }
            in.close();
            out.flush();
            out.close();
        }


        boolean copyStructure(String source, String target)
        {
            boolean ret = false;
            try
            {
                copyStructure(source, target, false, "", "");
                ret = true;
            } catch (Exception e)
            {
                //SWBUtils.log.error(e);
            }
            return ret;
        }


        boolean copyStructure(String source, String target, boolean ChangePath, String sourcePath, String targetPath)
        {
            try
            {
                File ftarget = new File(target);
                if (!ftarget.exists())
                {
                    ftarget.mkdirs();
                }
                File dir = new File(source);
                if (dir != null && dir.exists() && dir.isDirectory())
                {
                    File[] listado = dir.listFiles();
                    for (int i = 0; i < listado.length; i++)
                    {
                        try
                        {
                            if (listado[i].isFile())
                            {
                                File targetFile = new File(target + listado[i].getName());
                                if (targetFile.length() == 0)
                                {
                                    copy(source + listado[i].getName(), target + listado[i].getName(), ChangePath, sourcePath, targetPath);
                                }
                            }
                            if (listado[i].isDirectory())
                            {
                                String newpath = listado[i].getPath();
                                File f = new File(target + listado[i].getName());
                                f.mkdirs();
                                boolean flag = copyStructure(newpath + "/", target + listado[i].getName() + "/", ChangePath, sourcePath, targetPath);
                                if (flag)
                                {
                                    listado[i].delete();
                                }
                            }
                        } catch (Exception e)
                        {
                            //SWBUtils.log.error(e);
                            return false;
                        }
                    }
                }
            } catch (Exception e)
            {
                //SWBUtils.log.error(e);
                return false;
            }
            return true;
        }

        InputStream getStreamFromString(String str)
        {
            InputStream ret = null;
            if (str != null)
            {
                ret = new ByteArrayInputStream(str.getBytes());
            }
            return ret;
        }

        void copy(String source_name, String dest_name,
                boolean ChangePath, String sourcePath, String targetPath)
                throws IOException
        {

            File source_file = new File(source_name);
            File destination_file = new File(dest_name);
            FileInputStream source = null;
            java.io.FileOutputStream destination = null;

            try
            {
                source = new FileInputStream(source_file);
                destination = new FileOutputStream(destination_file);
                if (ChangePath && (source_file.getName().endsWith(".htm")
                        || source_file.getName().endsWith(".html")
                        || source_file.getName().endsWith(".html.orig")
                        || source_file.getName().endsWith(".htm.orig")))
                {
                    String content = readInputStream(source);
                    content = content.replaceAll(sourcePath, targetPath);
                    copyStream(getStreamFromString(content), destination);
                } else
                {
                    copyStream(source, destination);
                }
            } catch (Exception e)
            {
                //SWBUtils.log.error(e);
            } finally
            {
                if (source != null)
                {
                    try
                    {
                        source.close();
                    } catch (IOException e)
                    { // o esta cerrado o hubo un error, no importa
                    }
                }
                if (destination != null)
                {
                    try
                    {
                        destination.close();
                    } catch (IOException e)
                    {// o esta cerrado o hubo un error, no importa
                    }
                }
            }
        }


        String readInputStream(InputStream in) throws IOException
        {
            if (in == null)
            {
                throw new IOException("Input Stream null");
            }
            StringBuffer buf = new StringBuffer();
            byte[] bfile = new byte[bufferSize];
            int x;
            while ((x = in.read(bfile, 0, bufferSize)) > -1)
            {
                String aux = new String(bfile, 0, x);
                buf.append(aux);
            }
            in.close();
            return buf.toString();
        }
%>
