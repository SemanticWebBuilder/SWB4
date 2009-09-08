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
*  http://www.webbuilder.org.mx 
**/ 
 
﻿using System;
using System.Collections.Generic;
using System.Collections;
using System.Linq;
using System.Text;
using System.IO;

namespace XmlRpcLibrary
{
    public class WBFileUpload
{

    private static String CONTENT = "content";
    private static String FILENAME = "filename";
    //byte[] content = null;

    class CParameter
    {

        public String parametro;
        public List<String> Valor;

        public CParameter()
        {
            parametro = null;
            Valor = new List<String>();
        }
    }

    public WBFileUpload()
    {
        sContentType = null;
        table = null;
        parametros = new List<CParameter>();
        //maxSize = 0x2000000;
        maxSize = 0;
    }

    public void getFiles(Stream @in, String contentType)            
    {
        if (contentType == null)
        {
            return;
        }
        if (!contentType.ToLower().StartsWith("multipart/form-data"))
        {
            return;
        }
        int i = contentType.IndexOf("boundary=");
        if (i == -1)
        {
            return;
        }
        String s = contentType.Substring(i + 9);
        if (s == null)
        {
            return;
        }        
        table = parseMulti(s, @in);

    }

    public byte[] getFileData(String s)
    {
        byte[] ret = null;
        if (table == null)
        {
            return null;
        }
        IEnumerator IEnumerator = table.Keys.GetEnumerator();
        do
        {
            if (!IEnumerator.MoveNext())
            {
                break;
            }
            String s2 = (String)IEnumerator.Current;
            if (s2.Equals(s))
            {
                Object obj = table[s2];
                if (obj is Dictionary<String,Object>)
                {
                    Dictionary<String, Object> hashtable = (Dictionary<String, Object>)obj;
                    obj = hashtable[CONTENT];
                    byte[] abyte0 = (byte[]) obj;
                    ret = abyte0;
                }
            }
        }
        while (true);
        maxSize = ret.Length;
        return ret;
    }

    public int getSize()
    {
        return maxSize;
    }

    public Stream getFileInputStream(String s)
    {
        byte[] data = getFileData(s);
        return new MemoryStream(data);
    }

    public bool saveFile(String s, String s1)
            
    {
        bool flag = false;
        if (table == null)
        {
            return false;
        }
        IEnumerator IEnumerator = table.Keys.GetEnumerator();
        do
        {
            if (!IEnumerator.MoveNext())
            {
                break;
            }
            String s2 = (String) IEnumerator.Current;
            if (s2.Equals(s))
            {
                flag = true;
                Object obj = table[s2];
                if (obj is Hashtable)
                {
                    Hashtable hashtable = (Hashtable) obj;
                    obj = hashtable[CONTENT];
                    byte[] abyte0 = (byte[]) obj;
                    String s3 = (String) hashtable[FILENAME];
                    if (s3 != null)
                    {
                        int i = s3.LastIndexOf("\\");
                        if (i != -1)
                        {
                            s3 = s3.Substring(i + 1);
                        }
                        i = s3.LastIndexOf("/");
                        if (i != -1)
                        {
                            s3 = s3.Substring(i + 1);
                        }
                        FileInfo file = new FileInfo(s1 + s3);
                        Stream fileoutputstream = file.Open(FileMode.Create, FileAccess.Write, FileShare.ReadWrite);
                        fileoutputstream.Write(abyte0, 0, abyte0.Length);
                        fileoutputstream.Close();
                    }
                }
            }
        }
        while (true);
        return flag;
    }

    public bool saveFile(String s, String s1, String s2)
            
    {
        bool flag = false;
        if (table == null)
        {
            return false;
        }
        IEnumerator IEnumerator = table.Keys.GetEnumerator();
        do
        {
            if (!IEnumerator.MoveNext())
            {
                break;
            }
            String s3 = (String) IEnumerator.Current;
            if (s3.Equals(s))
            {
                flag = true;
                Object obj = table[s3];
                if (obj is Hashtable)
                {
                    Hashtable hashtable = (Hashtable) obj;
                    obj = hashtable[CONTENT];
                    byte[] abyte0 = (byte[]) obj;
                    String s4 = (String) hashtable[FILENAME];
                    if (s4 != null)
                    {
                        int i = s4.LastIndexOf("\\");
                        if (i != -1)
                        {
                            s4 = s4.Substring(i + 1);
                        }
                        i = s4.LastIndexOf("/");
                        if (i != -1)
                        {
                            s4 = s4.Substring(i + 1);
                        }
                        FileInfo file = new FileInfo(s1+s2);
                        Stream fileoutputstream = file.Open(FileMode.Create, FileAccess.ReadWrite, FileShare.ReadWrite);
                        fileoutputstream.Write(abyte0, 0, abyte0.Length);
                        fileoutputstream.Close();
                    }
                }
            }
        }
        while (true);
        return flag;
    }

    public bool saveFile(String s, String s1, String smod1, String smod2)
            
    {
        bool flag = false;
        if (table == null)
        {
            return false;
        }
        IEnumerator IEnumerator = table.Keys.GetEnumerator();
        do
        {
            if (!IEnumerator.MoveNext())
            {
                break;
            }
            String s2 = (String) IEnumerator.Current;
            if (s2.Equals(s))
            {
                flag = true;
                Object obj = table[s2];
                if (obj is Hashtable)
                {
                    Hashtable hashtable = (Hashtable) obj;
                    obj = hashtable[CONTENT];
                    byte[] abyte0 = (byte[]) obj;
                    String s3 = (String) hashtable[FILENAME];
                    if (s3 != null)
                    {
                        int i = s3.LastIndexOf("\\");
                        if (i != -1)
                        {
                            s3 = s3.Substring(i + 1);
                        }
                        i = s3.LastIndexOf("/");
                        if (i != -1)
                        {
                            s3 = s3.Substring(i + 1);
                        }
                        FileInfo file=new FileInfo(s1 + s3 + smod1 + smod2);
                        Stream fileoutputstream = file.Open(FileMode.Create,FileAccess.ReadWrite,FileShare.ReadWrite);
                        fileoutputstream.Write(abyte0, 0, abyte0.Length);
                        fileoutputstream.Close();
                    }
                }
            }
        }
        while (true);
        return flag;
    }

    public bool saveFileParsed(String s, String s1, String s0)
            
    {
        bool flag = false;
        if (table == null)
        {
            return false;
        }
        IEnumerator IEnumerator = table.Keys.GetEnumerator();
        do
        {
            if (!IEnumerator.MoveNext())
            {
                break;
            }
            String s2 = (String) IEnumerator.Current;
            if (s2.Equals(s))
            {
                flag = true;
                Object obj = table[s2];
                if (obj is Hashtable)
                {
                    Hashtable hashtable = (Hashtable) obj;
                    obj = hashtable[CONTENT];
                    byte[] abyte0 = (byte[]) obj;
                    String s3 = (String) hashtable[FILENAME];
                    if (s3 != null)
                    {
                        int i = s3.LastIndexOf("\\");
                        if (i != -1)
                        {
                            s3 = s3.Substring(i + 1);
                        }
                        i = s3.LastIndexOf("/");
                        if (i != -1)
                        {
                            s3 = s3.Substring(i + 1);
                        }
                        //String strNoparsed = new String(abyte0);
                        String strNoparsed = System.Text.Encoding.Default.GetString(abyte0);
                        String dataarc = "";
//                        if (s3.endsWith(".xsl") || s3.endsWith(".xslt"))
//                        {
//                            dataarc = WBUtils.getInstance().parseXsl(strNoparsed, s0);
//                        }
//                        else
//                        {
//                            dataarc = WBUtils.getInstance().parseHTML(strNoparsed, s0);
//                        }

                        byte[] abyte1 = System.Text.Encoding.Default.GetBytes(dataarc);
                        FileInfo file = new FileInfo(s1 + s3);
                        Stream fileoutputstream = file.Open(FileMode.Create, FileAccess.ReadWrite, FileShare.ReadWrite);
                        fileoutputstream.Write(abyte1, 0, abyte1.Length);
                        fileoutputstream.Close();
                    }
                }
            }
        }
        while (true);
        return flag;
    }

    public String getFileName(String s) 
    {
        if (table == null)
        {
            return null;
        }
        for (IEnumerator IEnumerator = table.Keys.GetEnumerator(); IEnumerator.MoveNext();)
        {
            String s1 = (String) IEnumerator.Current;
            if (s1.Equals(s))
            {
                Object obj = table[s1];
                if (obj is Hashtable)
                {
                    Hashtable hashtable = (Hashtable) obj;
                    String s2 = (String) hashtable[FILENAME];
                    if (s2 == null)
                    {
                        return null;
                    }
                    if (s2.Trim().Equals(""))
                    {
                        return null;
                    }
                    else
                    {
                        return s2.Trim();
                    }
                }
            }
        }

        return null;
    }

    /**
     * Gets the list of files
     * @return List of files
     * @throws java.io.IOException If is not possible to read the files
     */
    public List<String> getFileNames() 
    {
        List<String> afileNames = new List<String>();
        if (table == null)
        {
            return null;
        }
        for (IEnumerator IEnumerator = table.Keys.GetEnumerator(); IEnumerator.MoveNext();)
        {
            String s1 = (String) IEnumerator.Current;
            afileNames.Add(s1);
        }
        return afileNames;
    }

    public String getContentType(String s)
            
    {
        if (table == null)
        {
            return null;
        }
        for (IEnumerator IEnumerator = table.Keys.GetEnumerator(); IEnumerator.MoveNext();)
        {
            String s1 = (String) IEnumerator.Current;
            if (s1.Equals(s))
            {
                Object obj = table[s1];
                if (obj is Hashtable)
                {
                    Hashtable hashtable = (Hashtable) obj;
                    String s2 = (String) hashtable["content-type"];
                    if (s2 == null)
                    {
                        return null;
                    }
                    if (s2.Trim().Equals(""))
                    {
                        return null;
                    }
                    else
                    {
                        return s2.Trim();
                    }
                }
            }
        }

        return null;
    }

    public List<String> getValue(String s) 
    {
        for (int i = 0; i < parametros.Count; i++)
        {
            CParameter cparameter = (CParameter) parametros[i];
            if (cparameter.parametro.Trim().Equals(s.Trim()))
            {
                return cparameter.Valor;
            }
        }
        return null;
    }

    public List<String> getParamNames() 
    {
        List<String> aparams = new List<String>();
        for (int i = 0; i < parametros.Count; i++)
        {
            CParameter cparameter = (CParameter) parametros[i];
            aparams.Add(cparameter.parametro.Trim());
        }
        return aparams;
    }

    public int readLine(byte[] b, int off, int len, Stream @in) 
    {

        if (len <= 0)
        {
            return 0;
        }
        int count = 0, c=0;
        c = @in.ReadByte();
        while (c != -1)
        {
            b[off++] = (byte) c;
            count++;
            if (c == '\n' || count == len)
            {
                break;
            }
            c = @in.ReadByte();
        }
        return count > 0 ? count : -1;
    }

    Dictionary<String, Object> parseMulti(String s, Stream servletinputstream) 
    {

        char c = '\u2000';
        Dictionary<String, Object> hashtable = new Dictionary<String, Object>();
        String s1 = "--"+s;
        byte[] abyte0 = new byte[c];
        int i = readLine(abyte0, 0, abyte0.Length, servletinputstream);
        if (i == -1)
        {
            throw new Exception("InputStream truncated");
        }
        //String s2 = new String(abyte0, 0, 0, i);
        String s2 = System.Text.Encoding.Default.GetString(abyte0,0, i);
        if (!s2.StartsWith(s1))
        {
            throw new Exception("MIME boundary missing: "+s2);
        }
        do
        {
            String s3;
            String s6;
            MemoryStream bytearrayoutputstream;
            String s7;
            String s8;
            do
            {
                s7 = null;
                s8 = null;
                bytearrayoutputstream = new System.IO.MemoryStream();
                //Object obj = null;
                int j = readLine(abyte0, 0, abyte0.Length, servletinputstream);
                if (j == -1)
                {
                    return hashtable;
                }
                //s3 = new String(abyte0, 0, 0, j - 2);
                s3 = System.Text.Encoding.Default.GetString(abyte0, 0, j - 2);
                s6 = s3.ToLower();
            }
            while (!s6.StartsWith("content-disposition"));
            int l = s6.IndexOf("content-disposition: ");
            int i1 = s6.IndexOf(";");
            if (l == -1 || i1 == -1)
            {
                throw new Exception("Content Disposition line misformatted: "+s3);
            }
            String s10 = s6.Substring(l + 21, i1 - 21-l);
            if (!s10.Equals("form-data"))
            {
                throw new Exception("Content Disposition of "+s10+" is not supported");
            }
            int j1 = s6.IndexOf("name=\"", i1);
            int k1 = s6.IndexOf("\"", j1 + 7);
            if (j1 == -1 || k1 == -1)
            {
                throw new Exception("Content Disposition line misformatted: "+s3);
            }
            String s9 = s3.Substring(j1 + 6, k1-6-j1);
            int l1 = s6.IndexOf("filename=\"", k1 + 2);
            int i2 = s6.IndexOf("\"", l1 + 10);
            if (l1 != -1 && i2 != -1)
            {
                s7 = s3.Substring(l1 + 10, i2-10-l1);
            }
            int k = readLine(abyte0, 0, abyte0.Length, servletinputstream);
            if (k == -1)
            {
                return hashtable;
            }
            //s3 = new String(abyte0, 0, 0, k - 2);
            s3 = System.Text.Encoding.Default.GetString(abyte0, 0, k - 2);
            s6 = s3.ToLower();
            for (; sContentType == null; sContentType = s6)
            {
                ;
            }
            if (s6.StartsWith("content-type"))
            {
                int j2 = s6.IndexOf(" ");
                if (j2 == -1)
                {
                    throw new Exception("Content-Type line misformatted: "+s3);
                }
                s8 = s6.Substring(j2 + 1);
                k = readLine(abyte0, 0, abyte0.Length, servletinputstream);
                if (k == -1)
                {
                    return hashtable;
                }
                //s3 = new String(abyte0, 0, 0, k - 2);
                s3 = System.Text.Encoding.Default.GetString(abyte0, 0, k-2);
                if (s3.Length != 0)
                {
                    throw new Exception("Unexpected line in MIMEpart header: "+s3);
                }
            }
            else
            {
                if (s3.Length != 0)
                {
                    throw new Exception("Misformatted line following disposition: "+s3);
                }
            }
            bool flag = true;
            bool flag1 = true;
            byte[] abyte1 = new byte[c];
            int k2 = 0;
            k = readLine(abyte0, 0, abyte0.Length, servletinputstream);
            if (k == -1)
            {
                return hashtable;
            }
            //s3 = new String(abyte0, 0, 0, k);
            s3 = System.Text.Encoding.Default.GetString(abyte0, 0, k);
            CParameter cparameter = FindParameter(s9.Trim());
            if (cparameter != null)
            {
                cparameter.Valor.Add(s3);
            }
            else
            {
                cparameter = new CParameter();
                cparameter.parametro = s9.Trim();
                cparameter.Valor.Add(s3);
            }
            if (!s3.StartsWith(s1))
            {
                Array.Copy(abyte0, 0, abyte1, 0, k);
                k2 = k;
                k = readLine(abyte0, 0, abyte0.Length, servletinputstream);
                if (k == -1)
                {
                    return hashtable;
                }
                //String s4 = new String(abyte0, 0, 0, k);
                String s4 = System.Text.Encoding.Default.GetString(abyte0, 0, k);
                flag1 = false;
                if (s4.StartsWith(s1))
                {
                    flag = false;
                }
            }
            else
            {
                flag = false;
            }
            int line = 0;
            do
            {
                if (!flag)
                {
                    break;
                }
                bytearrayoutputstream.Write(abyte1, 0, k2);
                Array.Copy(abyte0, 0, abyte1, 0, k);
                k2 = k;
                k = readLine(abyte0, 0, abyte0.Length, servletinputstream);
                if (k == -1)
                {
                    return hashtable;
                }
                //String s5 = new String(abyte0, 0, 0, k);
                String s5 = System.Text.Encoding.Default.GetString(abyte0, 0, k);
                if (s5.StartsWith(s1))
                {
                    flag = false;
                }
                line++;
            }
            while (true);
            //if(!flag1 && k2 > 2)
            if (!flag1 && (k2 > 2 || line > 0))
            {
                bytearrayoutputstream.Write(abyte1, 0, k2 - 2);
                if (!CheckValue(cparameter, bytearrayoutputstream.ToString()))
                {
                    //cparameter.Valor[0]=new String(bytearrayoutputstream.ToString());
                    cparameter.Valor[0] = System.Text.Encoding.Default.GetString(bytearrayoutputstream.ToArray());
                }
            }
            if (s7 == null)
            {
                if (hashtable[s9] == null)
                {
                    String[] @as = new String[1];
                    @as[0] = bytearrayoutputstream.ToString();
                    hashtable.Add(s9, @as);
                }
                else
                {
                    Object obj1 = hashtable[s9];
                    if (obj1 is String[])
                    {
                        String[] as1 = (String[]) obj1;
                        String[] as2 = new String[as1.Length + 1];
                        Array.Copy(as1, 0, as2, 0, as1.Length);
                        as2[as1.Length] = bytearrayoutputstream.ToString();
                        hashtable.Add(s9, as2);
                    }
                    else
                    {
                        throw new Exception("failure in parseMulti hashtable building code");
                    }
                }
            }
            else
            {
                Dictionary<String, Object> hashtable1 = new Dictionary<String, Object>(4);
                hashtable1.Add("name", s9);
                hashtable1.Add(FILENAME, s7);
                if (s8 == null)
                {
                    s8 = "application/octet-stream";
                }
                hashtable1.Add("content-type", s8);
                hashtable1.Add(CONTENT, bytearrayoutputstream.ToArray());
                hashtable.Add(s9, hashtable1);
            }
            if (FindParameter(cparameter.parametro.Trim()) == null)
            {
                parametros.Add(cparameter);
            }
        }
        while (true);



    }

    private CParameter FindParameter(String parameter)
    {
        CParameter findParameter = null;
        for (int i = 0; i < parametros.Count; i++)
        {
            CParameter cparameter = (CParameter) parametros[i];
            if (cparameter.parametro.Trim().Equals(parameter))
            {
                findParameter = cparameter;
            }
        }
        return findParameter;
    }

    private bool CheckValue(CParameter cParameter, String value)
    {
        bool checkValue = false;
        IEnumerator values = cParameter.Valor.GetEnumerator();
        while (values.MoveNext())
        {
            String svalue = (String)values.Current;
            if (svalue.Trim().Equals(value.Trim(),StringComparison.InvariantCultureIgnoreCase))
            {
                checkValue = true;
            }
        }
        return checkValue;
    }
    private String sContentType;
    List<CParameter> parametros;
    Dictionary<String,Object> table;
    protected int maxSize;
}
}
