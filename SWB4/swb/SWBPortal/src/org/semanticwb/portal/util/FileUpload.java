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

import java.io.*;
import java.util.*;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;

// TODO: Auto-generated Javadoc
/**
 * Utileria para el envio de archivos al servidor.
 * Class for send files to server
 */
public class FileUpload
{
    
    /** The log. */
    private static Logger log = SWBUtils.getLogger(FileUpload.class);
    
    /** The sessid. */
    String sessid = null;

    /**
     * The Class CParameter.
     */
    class CParameter
    {

        /** The parametro. */
        public String parametro;
        
        /** The Valor. */
        public String Valor;

        /**
         * Instantiates a new c parameter.
         */
        CParameter()
        {
        }
    }


    /**
     * Instantiates a new file upload.
     */
    public FileUpload()
    {
        sContentType = null;
        table = null;
        parametros = new Vector();
        //maxSize = 0x2000000;
        maxSize = 0;
    }

    /**
     * Guarda.
     * 
     * @param html the html
     * @param ruta the ruta
     */
    private void Guarda(String html, String ruta)
    {
        try
        {
            DataOutputStream fout = new DataOutputStream(new FileOutputStream(ruta));
            fout.writeBytes(html);
            fout.close();
        } catch (IOException e)
        {
            log.error(e);
        }
    }

    /**
     * Gets the files.
     * 
     * @param httpservletrequest the httpservletrequest
     * @param httpservletresponse the httpservletresponse
     * @return the files
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void getFiles(HttpServletRequest httpservletrequest, HttpServletResponse httpservletresponse)
            throws IOException
    {
        sessid = httpservletrequest.getSession().getId();
        if (httpservletrequest.getContentType() == null)
            return;
        if (!httpservletrequest.getContentType().toLowerCase().startsWith("multipart/form-data"))
            return;
        int i = httpservletrequest.getContentType().indexOf("boundary=");
        if (i == -1)
            return;
        String s = httpservletrequest.getContentType().substring(i + 9);
        if (s == null)
            return;

        //System.out.println("1**********");
        try
        {
            //System.out.println("2**********");
            table = parseMulti(s, httpservletrequest.getInputStream());
            //System.out.println("3**********");
        } catch (Throwable throwable)
        {
            log.error(throwable);
            throwable.printStackTrace(httpservletresponse.getWriter());
            return;
        }
    }

    /**
     * Gets the content type.
     * 
     * @return the content type
     */
    public String getContentType()
    {
        return sContentType;
    }

    /**
     * Gets the file data.
     * 
     * @param s the s
     * @return the file data
     */
    public byte[] getFileData(String s)
    {
        byte ret[] = null;
        if (table == null)
            return null;
        Enumeration enumeration = table.keys();
        do
        {
            if (!enumeration.hasMoreElements())
                break;
            String s2 = (String) enumeration.nextElement();
            if (s2.equals(s))
            {
                Object obj = table.get(s2);
                if (obj instanceof Hashtable)
                {
                    Hashtable hashtable = (Hashtable) obj;
                    obj = hashtable.get("content");
                    byte abyte0[] = (byte[]) obj;
                    ret = abyte0;
                }
            }
        } while (true);
        maxSize = ret.length;
        return ret;
    }

    /**
     * Gets the size.
     * 
     * @return the size
     */
    public int getSize()
    {
        return maxSize;
    }

    /**
     * Gets the file input stream.
     * 
     * @param s the s
     * @return the file input stream
     */
    public InputStream getFileInputStream(String s)
    {
        byte data[] = getFileData(s);
        return new ByteArrayInputStream(data);
    }

    /**
     * Save file.
     * 
     * @param s the s
     * @param s1 the s1
     * @return true, if successful
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public boolean saveFile(String s, String s1)
            throws IOException
    {
        boolean flag = false;
        if (table == null)
            return false;
        Enumeration enumeration = table.keys();
        do
        {
            if (!enumeration.hasMoreElements())
                break;
            String s2 = (String) enumeration.nextElement();
            if (s2.equals(s))
            {
                flag = true;
                Object obj = table.get(s2);
                if (obj instanceof Hashtable)
                {
                    Hashtable hashtable = (Hashtable) obj;
                    obj = hashtable.get("content");
                    byte abyte0[] = (byte[]) obj;
                    String s3 = (String) hashtable.get("filename");
                    if (s3 != null)
                    {
                        int i = s3.lastIndexOf("\\");
                        if (i != -1)
                            s3 = s3.substring(i + 1);
                        i = s3.lastIndexOf("/");
                        if (i != -1)
                            s3 = s3.substring(i + 1);
                        FileOutputStream fileoutputstream = new FileOutputStream(String.valueOf(s1) + String.valueOf(s3));
                        fileoutputstream.write(abyte0, 0, abyte0.length);
                        fileoutputstream.close();
                    }
                }
            }
        } while (true);
        return flag;
    }

    /**
     * Save file.
     * 
     * @param s the s
     * @param s1 the s1
     * @param s2 the s2
     * @return true, if successful
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public boolean saveFile(String s, String s1, String s2)
            throws IOException
    {
        boolean flag = false;
        if (table == null)
            return false;
        Enumeration enumeration = table.keys();
        do
        {
            if (!enumeration.hasMoreElements())
                break;
            String s3 = (String) enumeration.nextElement();
            if (s3.equals(s))
            {
                flag = true;
                Object obj = table.get(s3);
                if (obj instanceof Hashtable)
                {
                    Hashtable hashtable = (Hashtable) obj;
                    obj = hashtable.get("content");
                    byte abyte0[] = (byte[]) obj;
                    String s4 = (String) hashtable.get("filename");
                    if (s4 != null)
                    {
                        int i = s4.lastIndexOf("\\");
                        if (i != -1)
                            s4 = s4.substring(i + 1);
                        i = s4.lastIndexOf("/");
                        if (i != -1)
                            s4 = s4.substring(i + 1);
                        FileOutputStream fileoutputstream = new FileOutputStream(String.valueOf(s1) + String.valueOf(s2));
                        fileoutputstream.write(abyte0, 0, abyte0.length);
                        fileoutputstream.close();
                    }
                }
            }
        } while (true);
        return flag;
    }

    /**
     * Save file.
     * 
     * @param s the s
     * @param s1 the s1
     * @param smod1 the smod1
     * @param smod2 the smod2
     * @return true, if successful
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public boolean saveFile(String s, String s1, String smod1, String smod2)
            throws IOException
    {
        boolean flag = false;
        if (table == null)
            return false;
        Enumeration enumeration = table.keys();
        do
        {
            if (!enumeration.hasMoreElements())
                break;
            String s2 = (String) enumeration.nextElement();
            if (s2.equals(s))
            {
                flag = true;
                Object obj = table.get(s2);
                if (obj instanceof Hashtable)
                {
                    Hashtable hashtable = (Hashtable) obj;
                    obj = hashtable.get("content");
                    byte abyte0[] = (byte[]) obj;
                    String s3 = (String) hashtable.get("filename");
                    if (s3 != null)
                    {
                        int i = s3.lastIndexOf("\\");
                        if (i != -1)
                            s3 = s3.substring(i + 1);
                        i = s3.lastIndexOf("/");
                        if (i != -1)
                            s3 = s3.substring(i + 1);
                        FileOutputStream fileoutputstream = new FileOutputStream(String.valueOf(s1) + String.valueOf(s3) + String.valueOf(smod1) + String.valueOf(smod2));
                        fileoutputstream.write(abyte0, 0, abyte0.length);
                        fileoutputstream.close();
                    }
                }
            }
        } while (true);
        return flag;
    }

    /**
     * Save file parsed.
     * 
     * @param s the s
     * @param s1 the s1
     * @param s0 the s0
     * @return true, if successful
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public boolean saveFileParsed(String s, String s1, String s0)
            throws IOException
    {
        boolean flag = false;
        if (table == null)
            return false;
        Enumeration enumeration = table.keys();
        do
        {
            if (!enumeration.hasMoreElements())
                break;
            String s2 = (String) enumeration.nextElement();
            if (s2.equals(s))
            {
                flag = true;
                Object obj = table.get(s2);
                if (obj instanceof Hashtable)
                {
                    Hashtable hashtable = (Hashtable) obj;
                    obj = hashtable.get("content");
                    byte abyte0[] = (byte[]) obj;
                    String s3 = (String) hashtable.get("filename");
                    if (s3 != null)
                    {
                        int i = s3.lastIndexOf("\\");
                        if (i != -1)
                            s3 = s3.substring(i + 1);
                        i = s3.lastIndexOf("/");
                        if (i != -1)
                            s3 = s3.substring(i + 1);
                        String strNoparsed = new String(abyte0);
                        String dataarc = SWBPortal.UTIL.parseHTML(strNoparsed, s0);
                        byte abyte1[] = dataarc.getBytes();
                        FileOutputStream fileoutputstream = new FileOutputStream(String.valueOf(s1) + String.valueOf(s3));
                        fileoutputstream.write(abyte1, 0, abyte1.length);
                        fileoutputstream.close();
                    }
                }
            }
        } while (true);
        return flag;
    }

    /**
     * Find attaches.
     * 
     * @param s the s
     * @return the string
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public String FindAttaches(String s)
            throws IOException
    {
        boolean flag = false;
        String dataarc = null;
        if (table == null)
            return "";
        Enumeration enumeration = table.keys();
        do
        {
            if (!enumeration.hasMoreElements())
                break;
            String s2 = (String) enumeration.nextElement();
            if (s2.equals(s))
            {
                flag = true;
                Object obj = table.get(s2);
                if (obj instanceof Hashtable)
                {
                    Hashtable hashtable = (Hashtable) obj;
                    obj = hashtable.get("content");
                    byte abyte0[] = (byte[]) obj;
                    String strNoparsed = new String(abyte0);
                    dataarc = SWBPortal.UTIL.FindAttaches(strNoparsed);
                }
            }
        } while (true);
        return dataarc;
    }

    /**
     * Gets the file name.
     * 
     * @param s the s
     * @return the file name
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public String getFileName(String s)
            throws IOException
    {
        if (table == null)
            return null;
        for (Enumeration enumeration = table.keys(); enumeration.hasMoreElements();)
        {
            String s1 = (String) enumeration.nextElement();
            if (s1.equals(s))
            {
                Object obj = table.get(s1);
                if (obj instanceof Hashtable)
                {
                    Hashtable hashtable = (Hashtable) obj;
                    String s2 = (String) hashtable.get("filename");
                    if (s2 == null)
                        return null;
                    if (s2.trim().equals(""))
                        return null;
                    else
                        return s2.trim();
                }
            }
        }

        return null;
    }

    /**
     * Gets the content type.
     * 
     * @param s the s
     * @return the content type
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public String getContentType(String s)
            throws IOException
    {
        if (table == null)
            return null;
        for (Enumeration enumeration = table.keys(); enumeration.hasMoreElements();)
        {
            String s1 = (String) enumeration.nextElement();
            if (s1.equals(s))
            {
                Object obj = table.get(s1);
                if (obj instanceof Hashtable)
                {
                    Hashtable hashtable = (Hashtable) obj;
                    String s2 = (String) hashtable.get("content-type");
                    if (s2 == null)
                        return null;
                    if (s2.trim().equals(""))
                        return null;
                    else
                        return s2.trim();
                }
            }
        }

        return null;
    }

    /**
     * Gets the value.
     * 
     * @param s the s
     * @return the value
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public String getValue(String s)
            throws IOException
    {
        for (int i = 0; i < parametros.size(); i++)
        {
            CParameter cparameter = (CParameter) parametros.elementAt(i);
            if (cparameter.parametro.trim().equals(s.trim()))
                return cparameter.Valor.trim();
        }

        return null;
    }

        /**
     * Gets the value.
     * 
     * @param s the s
     * @return the value
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public String getValues(String s)
            throws IOException
    {
            String params9 = s; 
            String valTmp = null;
            CParameter cparam = null;
            for (int x = 0; x < parametros.size(); x++)
            {
                cparam = (CParameter) parametros.elementAt(x);
                String valparam = cparam.Valor;
                if (cparam.parametro.trim().equals(params9)){
                    if(valparam!=null&&valparam.trim().length()>0&&!valparam.equals("null")){
                        if(valTmp==null){
                            valTmp = valparam;
                        } else {
                            valTmp = valTmp+","+valparam;
                        }
                    }
                }
            }
        
        return valTmp;
    }
    
    
    /**
     * Parses the multi.
     * 
     * @param s the s
     * @param servletinputstream the servletinputstream
     * @return the hashtable
     * @throws IOException Signals that an I/O exception has occurred.
     */
    Hashtable parseMulti(String s, ServletInputStream servletinputstream)
            throws IOException
    {
        char c = '\u2000';
        Hashtable hashtable = new Hashtable();
        String s1 = "--".concat(String.valueOf(String.valueOf(s)));
        byte abyte0[] = new byte[c];
        int i = servletinputstream.readLine(abyte0, 0, abyte0.length);
        if (i == -1)
            throw new IllegalArgumentException("InputStream truncated");
        String s2 = new String(abyte0, 0, 0, i);
        if (!s2.startsWith(s1))
            throw new IllegalArgumentException("MIME boundary missing: ".concat(String.valueOf(String.valueOf(s2))));
        do
        {
            String s3;
            String s6;
            ByteArrayOutputStream bytearrayoutputstream;
            String s7;
            String s8;
            do
            {
                s7 = null;
                s8 = null;
                bytearrayoutputstream = new ByteArrayOutputStream();
                Object obj = null;
                int j = servletinputstream.readLine(abyte0, 0, abyte0.length);
                if (j == -1)
                    return hashtable;
                s3 = new String(abyte0, 0, 0, j - 2);
                s6 = s3.toLowerCase();
            } while (!s6.startsWith("content-disposition"));
            int l = s6.indexOf("content-disposition: ");
            int i1 = s6.indexOf(";");
            if (l == -1 || i1 == -1)
                throw new IllegalArgumentException("Content Disposition line misformatted: ".concat(String.valueOf(String.valueOf(s3))));
            String s10 = s6.substring(l + 21, i1);
            if (!s10.equals("form-data"))
                throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer("Content Disposition of ")).append(s10).append(" is not supported"))));
            int j1 = s6.indexOf("name=\"", i1);
            int k1 = s6.indexOf("\"", j1 + 7);
            if (j1 == -1 || k1 == -1)
                throw new IllegalArgumentException("Content Disposition line misformatted: ".concat(String.valueOf(String.valueOf(s3))));
            String s9 = s3.substring(j1 + 6, k1);
            int l1 = s6.indexOf("filename=\"", k1 + 2);
            int i2 = s6.indexOf("\"", l1 + 10);
            if (l1 != -1 && i2 != -1)
                s7 = s3.substring(l1 + 10, i2);
            int k = servletinputstream.readLine(abyte0, 0, abyte0.length);
            if (k == -1)
                return hashtable;
            s3 = new String(abyte0, 0, 0, k - 2);
            s6 = s3.toLowerCase();
            for (; sContentType == null; sContentType = s6) ;
            if (s6.startsWith("content-type"))
            {
                int j2 = s6.indexOf(" ");
                if (j2 == -1)
                    throw new IllegalArgumentException("Content-Type line misformatted: ".concat(String.valueOf(String.valueOf(s3))));
                s8 = s6.substring(j2 + 1);
                k = servletinputstream.readLine(abyte0, 0, abyte0.length);
                if (k == -1)
                    return hashtable;
                s3 = new String(abyte0, 0, 0, k - 2);
                if (s3.length() != 0)
                    throw new IllegalArgumentException("Unexpected line in MIMEpart header: ".concat(String.valueOf(String.valueOf(s3))));
            } else if (s3.length() != 0)
                throw new IllegalArgumentException("Misformatted line following disposition: ".concat(String.valueOf(String.valueOf(s3))));
            boolean flag = true;
            boolean flag1 = true;
            byte abyte1[] = new byte[c];
            int k2 = 0;
            k = servletinputstream.readLine(abyte0, 0, abyte0.length);
            if (k == -1)
                return hashtable;
            s3 = new String(abyte0, 0, 0, k);
            
            CParameter cparameter = new CParameter();
            cparameter.parametro = s9.trim();
            cparameter.Valor = s3;
            
            if (!s3.startsWith(s1))
            {
                System.arraycopy(abyte0, 0, abyte1, 0, k);
                k2 = k;
                k = servletinputstream.readLine(abyte0, 0, abyte0.length);
                if (k == -1)
                    return hashtable;
                String s4 = new String(abyte0, 0, 0, k);
                flag1 = false;
                if (s4.startsWith(s1))
                    flag = false;
            } else
            {
                flag = false;
            }
            do
            {
                if (!flag)
                    break;
                bytearrayoutputstream.write(abyte1, 0, k2);
                System.arraycopy(abyte0, 0, abyte1, 0, k);
                k2 = k;
                k = servletinputstream.readLine(abyte0, 0, abyte0.length);
                if (k == -1)
                    return hashtable;
                String s5 = new String(abyte0, 0, 0, k);
                if (s5.startsWith(s1))
                    flag = false;
            } while (true);
            if (!flag1 && k2 > 2)
            {
                bytearrayoutputstream.write(abyte1, 0, k2 - 2);
                cparameter.Valor = new String(bytearrayoutputstream.toString());
            }
            if (s7 == null)
            {
                if (hashtable.get(s9) == null)
                {
                    String as[] = new String[1];
                    as[0] = bytearrayoutputstream.toString();
                    hashtable.put(s9, as);
                } else
                {
                    Object obj1 = hashtable.get(s9);
                    if (obj1 instanceof String[])
                    {
                        String as1[] = (String[]) obj1;
                        String as2[] = new String[as1.length + 1];
                        System.arraycopy(as1, 0, as2, 0, as1.length);
                        as2[as1.length] = bytearrayoutputstream.toString();
                        hashtable.put(s9, as2);
                    }
//                    else
//                    {
//                        throw new IllegalArgumentException("failure in parseMulti hashtable building code"+obj1.getClass().getName());
//                    }
                }
            } else
            {
                Hashtable hashtable1 = new Hashtable(4);
                hashtable1.put("name", s9);
                hashtable1.put("filename", s7);
                if (s8 == null)
                    s8 = "application/octet-stream";
                hashtable1.put("content-type", s8);
                hashtable1.put("content", bytearrayoutputstream.toByteArray());
                hashtable.put(s9, hashtable1);
            }
            parametros.add(cparameter);
        } while (true);
    }

    /** Getter for property sessid.
     * @return Value of property sessid.
     *
     */
    public java.lang.String getSessid()
    {
        return sessid;
    }

    /** The s content type. */
    private String sContentType;
    
    /** The parametros. */
    Vector parametros;
    
    /** The table. */
    Hashtable table;
    
    /** The max size. */
    protected int maxSize;
}
