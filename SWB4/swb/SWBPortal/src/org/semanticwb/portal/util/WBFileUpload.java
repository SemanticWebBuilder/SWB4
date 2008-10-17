/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.portal.util;

import java.io.*;
import java.util.*;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import org.semanticwb.Logger;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;

public class WBFileUpload extends FileUpload
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
        catch(Throwable throwable)
        {
            //throwable.printStackTrace(new PrintStream(httpservletresponse.getOutputStream()));
            log.error("Error en WBFileUpload al generar variables de request..",throwable);
            return;
        }
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
    
    public ArrayList getValues(String s) throws IOException {
        for(int i = 0; i < parametros.size(); i++)
        {
            CParameter cparameter = (CParameter)parametros.elementAt(i);
            if(cparameter.parametro.trim().equals(s.trim()))
                return cparameter.Valor;
        }
      return null;
    }
    

    @Override
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
    @Override
    public java.lang.String getSessid()
    {
        return sessid;
    }
    
}
