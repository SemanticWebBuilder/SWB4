/*
 * INFOTEC WebBuilder es una herramienta para el desarrollo de portales de conocimiento, colaboraci�n e integraci�n para Internet,
 * la cual, es una creaci�n original del Fondo de Informaci�n y Documentaci�n para la Industria INFOTEC, misma que se encuentra
 * debidamente registrada ante el Registro P�blico del Derecho de Autor de los Estados Unidos Mexicanos con el
 * No. 03-2002-052312015400-14, para la versi�n 1; No. 03-2003-012112473900 para la versi�n 2, y No. 03-2006-012012004000-01
 * para la versi�n 3, respectivamente.
 *
 * INFOTEC pone a su disposici�n la herramienta INFOTEC WebBuilder a trav�s de su licenciamiento abierto al p�blico (�open source�),
 * en virtud del cual, usted podr� usarlo en las mismas condiciones con que INFOTEC lo ha dise�ado y puesto a su disposici�n;
 * aprender de �l; distribuirlo a terceros; acceder a su c�digo fuente y modificarlo, y combinarlo o enlazarlo con otro software,
 * todo ello de conformidad con los t�rminos y condiciones de la LICENCIA ABIERTA AL P�BLICO que otorga INFOTEC para la utilizaci�n
 * de INFOTEC WebBuilder 3.2.
 *
 * INFOTEC no otorga garant�a sobre INFOTEC WebBuilder, de ninguna especie y naturaleza, ni impl�cita ni expl�cita,
 * siendo usted completamente responsable de la utilizaci�n que le d� y asumiendo la totalidad de los riesgos que puedan derivar
 * de la misma.
 *
 * Si usted tiene cualquier duda o comentario sobre INFOTEC WebBuilder, INFOTEC pone a su disposici�n la siguiente
 * direcci�n electr�nica:
 *
 *                                          http://www.webbuilder.org.mx
 */


// Decompiled by Jad v1.5.7d. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   WBFileUpload.java

package org.semanticwb.portal.util;

import java.io.*;
import java.util.*;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;

public class WBFileUpload
{
    private static Logger log = SWBUtils.getLogger(WBFileUpload.class);
    
    String sessid=null;
    
    private String sContentType;
    Vector parametros;
    Hashtable table;
    protected int maxSize;
    
    class CParameter
    {

        public String parametro;
        public ArrayList Valor;

        CParameter(){
            parametro=null;
            Valor=new ArrayList();
        }
    }


    public WBFileUpload()
    {
        sContentType = null;
        table = null;
        parametros = new Vector();
        //maxSize = 0x2000000;
        maxSize = 0;
    }

    private void Guarda(String html, String ruta)
    {
        try
        {
            DataOutputStream fout = new DataOutputStream(new FileOutputStream(ruta));
            fout.writeBytes(html);
            fout.close();
        }
        catch(IOException e) 
        {
            log.error(e);
        }
    }

    public void getFiles(HttpServletRequest httpservletrequest)
        throws IOException
    {
        sessid=httpservletrequest.getSession().getId();
        if(httpservletrequest.getContentType() == null)
            return;
        if(!httpservletrequest.getContentType().toLowerCase().startsWith("multipart/form-data"))
            return;
        int i = httpservletrequest.getContentType().indexOf("boundary=");
        if(i == -1)
            return;
        String s = httpservletrequest.getContentType().substring(i + 9);
        if(s == null)
            return;
        
        try
        {
            table = parseMulti(s, httpservletrequest.getInputStream());
        }
        catch(Throwable e)
        {
            //throwable.printStackTrace(new PrintStream(httpservletresponse.getOutputStream()));
            log.error("Error en WBFileUpload al generar variables de request..", e);
            return;
        }
    }

    public String getContentType()
    {
        return sContentType;
    }

    public byte[] getFileData(String s)
    {
        byte ret[] = null;
        if(table == null)
            return null;
        Enumeration enumeration = table.keys();
        do
        {
            if(!enumeration.hasMoreElements())
                break;
            String s2 = (String)enumeration.nextElement();
            if(s2.equals(s))
            {
                Object obj = table.get(s2);
                if(obj instanceof Hashtable)
                {
                    Hashtable hashtable = (Hashtable)obj;
                    obj = hashtable.get("content");
                    byte abyte0[] = (byte[])obj;
                    ret = abyte0;
                }
            }
        } while(true);
        maxSize=ret.length;
        return ret;
    }

    public int getSize(){
        return maxSize;
    }

    public InputStream getFileInputStream(String s)
    {
        byte data[] = getFileData(s);
        return new ByteArrayInputStream(data);
    }

    public boolean saveFile(String s, String s1)
        throws IOException
    {
        boolean flag = false;
        if(table == null)
            return false;
        Enumeration enumeration = table.keys();
        do
        {
            if(!enumeration.hasMoreElements())
                break;
            String s2 = (String)enumeration.nextElement();
            if(s2.equals(s))
            {
                flag = true;
                Object obj = table.get(s2);
                if(obj instanceof Hashtable)
                {
                    Hashtable hashtable = (Hashtable)obj;
                    obj = hashtable.get("content");
                    byte abyte0[] = (byte[])obj;
                    String s3 = (String)hashtable.get("filename");
                    if(s3 != null)
                    {
                        int i = s3.lastIndexOf("\\");
                        if(i != -1)
                            s3 = s3.substring(i + 1);
                        i = s3.lastIndexOf("/");
                        if(i != -1)
                            s3 = s3.substring(i + 1);
                        FileOutputStream fileoutputstream = new FileOutputStream(String.valueOf(s1) + String.valueOf(s3));
                        fileoutputstream.write(abyte0, 0, abyte0.length);
                        fileoutputstream.close();
                    }
                }
            }
        } while(true);
        return flag;
    }

    public boolean saveFile(String s, String s1, String s2)
        throws IOException
    {
        boolean flag = false;
        if(table == null)
            return false;
        Enumeration enumeration = table.keys();
        do
        {
            if(!enumeration.hasMoreElements())
                break;
            String s3 = (String)enumeration.nextElement();
            if(s3.equals(s))
            {
                flag = true;
                Object obj = table.get(s3);
                if(obj instanceof Hashtable)
                {
                    Hashtable hashtable = (Hashtable)obj;
                    obj = hashtable.get("content");
                    byte abyte0[] = (byte[])obj;
                    String s4 = (String)hashtable.get("filename");
                    if(s4 != null)
                    {
                        int i = s4.lastIndexOf("\\");
                        if(i != -1)
                            s4 = s4.substring(i + 1);
                        i = s4.lastIndexOf("/");
                        if(i != -1)
                            s4 = s4.substring(i + 1);
                        FileOutputStream fileoutputstream = new FileOutputStream(String.valueOf(s1) + String.valueOf(s2));
                        fileoutputstream.write(abyte0, 0, abyte0.length);
                        fileoutputstream.close();
                    }
                }
            }
        } while(true);
        return flag;
    }

    public boolean saveFile(String s, String s1, String smod1, String smod2)
        throws IOException
    {
        boolean flag = false;
        if(table == null)
            return false;
        Enumeration enumeration = table.keys();
        do
        {
            if(!enumeration.hasMoreElements())
                break;
            String s2 = (String)enumeration.nextElement();
            if(s2.equals(s))
            {
                flag = true;
                Object obj = table.get(s2);
                if(obj instanceof Hashtable)
                {
                    Hashtable hashtable = (Hashtable)obj;
                    obj = hashtable.get("content");
                    byte abyte0[] = (byte[])obj;
                    String s3 = (String)hashtable.get("filename");
                    if(s3 != null)
                    {
                        int i = s3.lastIndexOf("\\");
                        if(i != -1)
                            s3 = s3.substring(i + 1);
                        i = s3.lastIndexOf("/");
                        if(i != -1)
                            s3 = s3.substring(i + 1);
                        FileOutputStream fileoutputstream = new FileOutputStream(String.valueOf(s1) + String.valueOf(s3) + String.valueOf(smod1) + String.valueOf(smod2));
                        fileoutputstream.write(abyte0, 0, abyte0.length);
                        fileoutputstream.close();
                    }
                }
            }
        } while(true);
        return flag;
    }

    public boolean saveFileParsed(String s, String s1, String s0)
        throws IOException
    {
        boolean flag = false;
        if(table == null)
            return false;
        Enumeration enumeration = table.keys();
        do
        {
            if(!enumeration.hasMoreElements())
                break;
            String s2 = (String)enumeration.nextElement();
            if(s2.equals(s))
            {
                flag = true;
                Object obj = table.get(s2);
                if(obj instanceof Hashtable)
                {
                    Hashtable hashtable = (Hashtable)obj;
                    obj = hashtable.get("content");
                    byte abyte0[] = (byte[])obj;
                    String s3 = (String)hashtable.get("filename");
                    if(s3 != null)
                    {
                        int i = s3.lastIndexOf("\\");
                        if(i != -1)
                            s3 = s3.substring(i + 1);
                        i = s3.lastIndexOf("/");
                        if(i != -1)
                            s3 = s3.substring(i + 1);
                        String strNoparsed = new String(abyte0);
                        
                        String dataarc = "";
                        if(s3.endsWith(".xsl") || s3.endsWith(".xslt")) dataarc=SWBPortal.parseXsl(strNoparsed, s0);
                        else dataarc = SWBPortal.parseHTML(strNoparsed, s0);
                        
                        byte abyte1[] = dataarc.getBytes();
                        FileOutputStream fileoutputstream = new FileOutputStream(String.valueOf(s1) + String.valueOf(s3));
                        fileoutputstream.write(abyte1, 0, abyte1.length);
                        fileoutputstream.close();
                    }
                }
            }
        } while(true);
        return flag;
    }

    public String FindAttaches(String s)
        throws IOException
    {
        boolean flag = false;
        String dataarc = null;
        if(table == null)
            return "";
        Enumeration enumeration = table.keys();
        do
        {
            if(!enumeration.hasMoreElements())
                break;
            String s2 = (String)enumeration.nextElement();
            if(s2.equals(s))
            {
                flag = true;
                Object obj = table.get(s2);
                if(obj instanceof Hashtable)
                {
                    Hashtable hashtable = (Hashtable)obj;
                    obj = hashtable.get("content");
                    byte abyte0[] = (byte[])obj;
                    String strNoparsed = new String(abyte0);
                    dataarc = SWBPortal.FindAttaches(strNoparsed);
                }
            }
        } while(true);
        return dataarc;
    }

    public String getFileName(String s) throws IOException
    {
        if(table == null)
            return null;
        for(Enumeration enumeration = table.keys(); enumeration.hasMoreElements();)
        {
            String s1 = (String)enumeration.nextElement();
            if(s1.equals(s))
            {
                Object obj = table.get(s1);
                if(obj instanceof Hashtable)
                {
                    Hashtable hashtable = (Hashtable)obj;
                    String s2 = (String)hashtable.get("filename");
                    if(s2 == null)
                        return null;
                    if(s2.trim().equals(""))
                        return null;
                    else
                        return s2.trim();
                }
            }
        }

        return null;
    }
    
    public ArrayList getFileNames() throws IOException
    {
        ArrayList afileNames=new ArrayList();
        if(table == null)
            return null;
        for(Enumeration enumeration = table.keys(); enumeration.hasMoreElements();)
        {
            String s1 = (String)enumeration.nextElement();
            afileNames.add(s1);
        }
        return afileNames;
    }
    
    
    public String getContentType(String s)
        throws IOException
    {
        if(table == null)
            return null;
        for(Enumeration enumeration = table.keys(); enumeration.hasMoreElements();)
        {
            String s1 = (String)enumeration.nextElement();
            if(s1.equals(s))
            {
                Object obj = table.get(s1);
                if(obj instanceof Hashtable)
                {
                    Hashtable hashtable = (Hashtable)obj;
                    String s2 = (String)hashtable.get("content-type");
                    if(s2 == null)
                        return null;
                    if(s2.trim().equals(""))
                        return null;
                    else
                        return s2.trim();
                }
            }
        }

        return null;
    }
    
    public ArrayList getValue(String s) throws IOException {
        for(int i = 0; i < parametros.size(); i++)
        {
            CParameter cparameter = (CParameter)parametros.elementAt(i);
            if(cparameter.parametro.trim().equals(s.trim()))
                return cparameter.Valor;
        }
      return null;
    }
    
    
    public ArrayList getParamNames() throws IOException
    {
        ArrayList aparams=new ArrayList();
        for(int i = 0; i < parametros.size(); i++)
        {
            CParameter cparameter = (CParameter)parametros.elementAt(i);
            aparams.add(cparameter.parametro.trim());
        }
        return aparams;
    }

    Hashtable parseMulti(String s, ServletInputStream servletinputstream) 
    {
     try{   
        char c = '\u2000';
        Hashtable hashtable = new Hashtable();
        String s1 = "--".concat(String.valueOf(String.valueOf(s)));
        byte abyte0[] = new byte[c];
        int i = servletinputstream.readLine(abyte0, 0, abyte0.length);
        if(i == -1)
            throw new IllegalArgumentException("InputStream truncated");
        String s2 = new String(abyte0, 0, 0, i);
        if(!s2.startsWith(s1))
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
                if(j == -1)
                    return hashtable;
                s3 = new String(abyte0, 0, 0, j - 2);
                s6 = s3.toLowerCase();
            } while(!s6.startsWith("content-disposition"));
            int l = s6.indexOf("content-disposition: ");
            int i1 = s6.indexOf(";");
            if(l == -1 || i1 == -1)
                throw new IllegalArgumentException("Content Disposition line misformatted: ".concat(String.valueOf(String.valueOf(s3))));
            String s10 = s6.substring(l + 21, i1);
            if(!s10.equals("form-data"))
                throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer("Content Disposition of ")).append(s10).append(" is not supported"))));
            int j1 = s6.indexOf("name=\"", i1);
            int k1 = s6.indexOf("\"", j1 + 7);
            if(j1 == -1 || k1 == -1)
                throw new IllegalArgumentException("Content Disposition line misformatted: ".concat(String.valueOf(String.valueOf(s3))));
            String s9 = s3.substring(j1 + 6, k1);
            int l1 = s6.indexOf("filename=\"", k1 + 2);
            int i2 = s6.indexOf("\"", l1 + 10);
            if(l1 != -1 && i2 != -1)
                s7 = s3.substring(l1 + 10, i2);
            int k = servletinputstream.readLine(abyte0, 0, abyte0.length);
            if(k == -1)
                return hashtable;
            s3 = new String(abyte0, 0, 0, k - 2);
            s6 = s3.toLowerCase();
            for(; sContentType == null; sContentType = s6);
            if(s6.startsWith("content-type"))
            {
                int j2 = s6.indexOf(" ");
                if(j2 == -1)
                    throw new IllegalArgumentException("Content-Type line misformatted: ".concat(String.valueOf(String.valueOf(s3))));
                s8 = s6.substring(j2 + 1);
                k = servletinputstream.readLine(abyte0, 0, abyte0.length);
                if(k == -1)
                    return hashtable;
                s3 = new String(abyte0, 0, 0, k - 2);
                if(s3.length() != 0)
                    throw new IllegalArgumentException("Unexpected line in MIMEpart header: ".concat(String.valueOf(String.valueOf(s3))));
            } else
            if(s3.length() != 0)
                throw new IllegalArgumentException("Misformatted line following disposition: ".concat(String.valueOf(String.valueOf(s3))));
            boolean flag = true;
            boolean flag1 = true;
            byte abyte1[] = new byte[c];
            int k2 = 0;
            k = servletinputstream.readLine(abyte0, 0, abyte0.length);
            if(k == -1)
                return hashtable;
            s3 = new String(abyte0, 0, 0, k);
            CParameter cparameter=FindParemeter(s9.trim());
            if(cparameter!=null){
                cparameter.Valor.add(s3);
            }
            else{
                cparameter = new CParameter();
                cparameter.parametro = s9.trim();
                cparameter.Valor.add(s3);
            }
            if(!s3.startsWith(s1))
            {
                System.arraycopy(abyte0, 0, abyte1, 0, k);
                k2 = k;
                k = servletinputstream.readLine(abyte0, 0, abyte0.length);
                if(k == -1)
                    return hashtable;
                String s4 = new String(abyte0, 0, 0, k);
                flag1 = false;
                if(s4.startsWith(s1))
                    flag = false;
            } else
            {
                flag = false;
            }
            int line=0;
            do
            {
                if(!flag)
                    break;
                bytearrayoutputstream.write(abyte1, 0, k2);
                System.arraycopy(abyte0, 0, abyte1, 0, k);
                k2 = k;
                k = servletinputstream.readLine(abyte0, 0, abyte0.length);
                if(k == -1)
                    return hashtable;
                String s5 = new String(abyte0, 0, 0, k);
                if(s5.startsWith(s1))
                    flag = false;
                line++;
            } while(true);
            //if(!flag1 && k2 > 2)
            if(!flag1 && (k2 > 2 || line > 0))
            {
                    bytearrayoutputstream.write(abyte1, 0, k2 - 2);
                     if(!CheckValue(cparameter,bytearrayoutputstream.toString())){
                        cparameter.Valor.set(0,new String(bytearrayoutputstream.toString()));
                     }
            }
            if(s7 == null)
            {
                if(hashtable.get(s9) == null)
                {
                    String as[] = new String[1];
                    as[0] = bytearrayoutputstream.toString();
                    hashtable.put(s9, as);
                } else
                {
                    Object obj1 = hashtable.get(s9);
                    if(obj1 instanceof String[])
                    {
                        String as1[] = (String[])obj1;
                        String as2[] = new String[as1.length + 1];
                        System.arraycopy(as1, 0, as2, 0, as1.length);
                        as2[as1.length] = bytearrayoutputstream.toString();
                        hashtable.put(s9, as2);
                    } else
                    {
                        throw new IllegalArgumentException("failure in parseMulti hashtable building code");
                    }
                }
            } else
            {
                Hashtable hashtable1 = new Hashtable(4);
                hashtable1.put("name", s9);
                hashtable1.put("filename", s7);
                if(s8 == null)
                    s8 = "application/octet-stream";
                hashtable1.put("content-type", s8);
                hashtable1.put("content", bytearrayoutputstream.toByteArray());
                hashtable.put(s9, hashtable1);
            }
            if(FindParemeter(cparameter.parametro.trim())==null)
                parametros.add(cparameter);
        } while(true);
     }catch(Exception e){log.error(e);}
     return null;
    }

    
    private CParameter FindParemeter(String parameter){
        for(int i = 0; i < parametros.size(); i++){
            CParameter cparameter = (CParameter)parametros.elementAt(i);
            if(cparameter.parametro.trim().equals(parameter))
                return cparameter;
        }
      return null;
    }
    
    private boolean CheckValue(CParameter cParameter,String value){
        Iterator values=cParameter.Valor.iterator();
        while(values.hasNext()){
            String svalue=(String)values.next();
            if(svalue.trim().equalsIgnoreCase(value.trim())) return true;
        }
        return false;
    }
    
    
    /** Getter for property sessid.
     * @return Value of property sessid.
     *
     */
    public java.lang.String getSessid()
    {
        return sessid;
    }    
    
}
