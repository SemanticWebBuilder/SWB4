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
package org.semanticwb.servlet.internal;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Vector;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.portal.util.MultipartInputStream;

// TODO: Auto-generated Javadoc
/**
 * Uploads file via ajax.
 * 
 * @author jorge.jimenez
 */
public class Upload implements InternalServlet
{

    /** The log. */
    private static Logger log = SWBUtils.getLogger(Upload.class);

    /* (non-Javadoc)
     * @see org.semanticwb.servlet.internal.InternalServlet#doProcess(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.semanticwb.servlet.internal.DistributorParams)
     */
    public void doProcess(HttpServletRequest request, HttpServletResponse response, DistributorParams dparams) throws IOException, ServletException {
        //String repository=com.infotec.wb.core.db.DBUser.getInstance().getDefaultRepository();
        //com.infotec.wb.core.WBUser admuser = com.infotec.wb.core.WBUserMgr.getInstance().getUser(request, repository);



        UploadFile upload = new UploadFile();

        /*
        if (admuser == null)
        {
            String sessionId = null;
            if (request.getHeader("wbcookie") != null)
            {
                sessionId = request.getHeader("wbcookie");
            } else if (request.getRequestURI().indexOf(";jsession=") > -1)
            {
                sessionId = request.getRequestURI().substring(request.getRequestURI().indexOf(";jsession="));
            } else if (request.getParameter("jsession") != null)
            {
                sessionId = request.getParameter("jsession");
            }

            if (sessionId != null)
            {
                int pos = sessionId.indexOf("=");
                if (pos != -1) sessionId = sessionId.substring(pos + 1);
                admuser = com.infotec.wb.core.WBUserMgr.getInstance().getUser(sessionId,repository);
                /*   ******************** checar metodo wb3
                if (admuser != null)
                {
                    com.infotec.wb.core.WBUserMgr.getInstance().setRecHashMap(request.getSession().getId(), admuser);
                }
                * /
            }
        }
        //System.out.println("admuser:"+admuser);
        if (admuser == null)
        {
            response.sendError(500, "El usuario no existe... ");
            return;
        }
        */
        upload.processRequest(request, response);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.servlet.internal.InternalServlet#init(javax.servlet.ServletContext)
     */
    public void init(ServletContext config) throws ServletException {
        log.event("Initializing InternalServlet Upload...");
    }
}

class UploadFile
{
    ServletRequest req;
    java.io.PrintWriter out;
    BufferedInputStream sis;
    public Vector params;

    public String contentType = "";

    public boolean debug = false;
    int length;
    String boundary;
    String realboundary;
    String finalboundary;
    boolean end;

    String path = "/";

    /** Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     */
    public void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, java.io.IOException
    {
        req = request;
        params = new Vector();
        response.setContentType("text/html");
        out = response.getWriter();
        init2();
        process();
        close();
        out.flush();
        out.close();
        params = null;
        req = null;
        out = null;
    }

    public String getParameter(String param)
    {
        Enumeration en = params.elements();
        String ret = null;
        int inc = 0;
        while (en.hasMoreElements())
        {
            Parameter par = (Parameter) en.nextElement();
            if (par.name.equals(param))
            {
                ret = new String(par.value);
                inc++;
                break;
            }
        }
        return ret;
    }

    public String[] getParameters(String param)
    {
        Enumeration en = params.elements();
        int inc = 0;
        while (en.hasMoreElements())
        {
            Parameter par = (Parameter) en.nextElement();
            if (par.name.equals(param))
            {
                inc++;
            }
        }
        if (inc == 0) return null;
        String[] ret = new String[inc];
        en = params.elements();
        inc = 0;
        while (en.hasMoreElements())
        {
            Parameter par = (Parameter) en.nextElement();
            if (par.name.equals(param))
            {
                ret[inc] = new String(par.value);
                inc++;
            }
        }
        return ret;
    }

    public void debug(String s) throws IOException
    {
        if (debug) out.println(s);
    }

    public void init2() throws IOException
    {
        end = false;
        String s1 = req.getContentType();
        length = req.getContentLength();
        boundary = s1.substring(s1.indexOf("boundary=") + 9);
        sis = new BufferedInputStream(req.getInputStream(), boundary.length() + 4);
        //sis = req.getInputStream();
        debug("ContentType:" + s1);
        debug("length:" + length);
        debug("boundary:" + boundary);
        realboundary = "--" + boundary + "\r\n";
        finalboundary = "--" + boundary + "--\r\n";
        debug("realboundary:" + realboundary);
        debug("finalboundary:" + finalboundary);
    }

    public void close() throws IOException
    {
        sis.close();
    }

    public void processFile(String name) throws IOException
    {
        debug("processFile=" + name);
        String s;

        MultipartInputStream mu = new MultipartInputStream(sis, boundary.getBytes());

        debug(path + name);
        SWBUtils.IO.createDirectory(path);
        FileOutputStream f = new FileOutputStream(path + name);
        debug("*****************************************");
        try
        {
            int ch;
            byte b[] = new byte[5000];
            while ((ch = mu.read(b, 0, b.length)) != -1)
            {
                //debug((char)ch);
                f.write(b, 0, ch);
            }
        } catch (IOException e)
        {
            System.out.println(e);
        }
        debug("*****************************************");
        f.close();

    }

    public void processParam(String name) throws IOException
    {
        debug("processParam=" + name);
        String s;
        StringBuffer st = new StringBuffer();
        while ((s = readLine()) != null)
        {
            if (s.equalsIgnoreCase(finalboundary))
            {
                end = true;
                break;
            }
            if (s.equalsIgnoreCase(realboundary))
            {
                break;
            }
            st.append(s);
        }
        st.setLength(st.length() - 2);
        if (name.equals("path"))
        {
            path = st.toString();
            path = path.replace('\\', '/');
            if (!path.endsWith("/")) path += "/";
        }
        if (name.equals("relpath"))
        {
            path = SWBPortal.getWorkPath() + st.toString();
            path = path.replace('\\', '/');
            if (!path.endsWith("/")) path += "/";
        }
        debug(name + "=" + st.toString());
        params.addElement(new Parameter(name, st.toString()));
    }

    public void processHeader() throws IOException
    {
        debug("processHeader");
        String s;
        boolean flag = false,flag2 = false;
        String fname = "";
        String pname = "";

        while ((s = readLine()) != null)
        {
            if (s.equalsIgnoreCase(finalboundary))
            {
                end = true;
                break;
            }
            if (s.equals("\r\n"))
            {
                break;
            }
            String s3 = s.toLowerCase();
            int i;
            if ((i = s3.indexOf("content-disposition: form-data;")) > -1)
            {
                flag2 = true;
                i = s3.indexOf("name=\"");
                int j = s3.indexOf("\"", i + 7);
                if (i != -1 && j != -1) pname = s3.substring(i + 6, j);
                i = s3.indexOf("filename=\"");
                j = s3.indexOf("\"", i + 11);
                if (i != -1 && j != -1)
                {
                    fname = s.substring(i + 10, j);
                    int l = Math.max(fname.lastIndexOf(47), fname.lastIndexOf(92));
                    if (l != -1) fname = fname.substring(l + 1);
                    flag = true;
                }
            }
            if ((i = s3.indexOf("content-type:")) > -1)
            {
                contentType = s3.substring(14);
            }
        }
        if (!flag2) throw new IOException("Content disposition not found");
        if (flag)
        {
            processFile(fname);
        } else
        {
            processParam(pname);
        }
    }

    public void process() throws IOException
    {
        String s;
        s = readLine();
        if (s.equalsIgnoreCase(finalboundary))
        {
            end = true;
            return;
        }
        if (!s.equalsIgnoreCase(realboundary)) throw new IOException("Corrupt Format, no leading boundary");
        do
        {
            processHeader();
        } while (!end);
    }

    public String readLine() throws IOException
    {
        StringBuffer st = new StringBuffer();
        int got = 0;
        int ch;
        while (got < 8192)
        {
            if ((ch = sis.read()) == -1)
            {
                return (got == 0) ? null : st.toString();
            }
            if (ch == '\r')
            {
                st.append((char) ch);
                ch = sis.read();
                st.append((char) ch);
                if (ch == '\n')
                {
                    break;
                }
            }
            st.append((char) ch);
        }
        return st.toString();
    }

}


/*
 * UploadMed.java
 *
 * Created on May 17, 2001, 12:20 PM
 */

class Parameter
{
    public String name;
    public String value;

    public Parameter(String name, String value)
    {
        this.name = name;
        this.value = value;
    }

    public String toString()
    {
        return name + "=" + value;
    }
}


