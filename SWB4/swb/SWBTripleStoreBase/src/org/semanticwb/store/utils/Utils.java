/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.store.utils;

import java.security.MessageDigest;

/**
 *
 * @author javier.solis.g
 */
public class Utils
{
    private final static char[] ALPHABET =
    {
        '0', '1', '2', '3', '4', '5',
        '6', '7', '8', '9', '@', 
        'A', 'B', 'C', 'D', 'E', 'F', 'G',
        'H', 'I', 'J', 'K', 'L', 'M', 'N',
        'O', 'P', 'Q', 'R', 'S', 'T', 'U',
        'V', 'W', 'X', 'Y', 'Z', '_',
        'a', 'b', 'c', 'd', 'e', 'f', 'g',
        'h', 'i', 'j', 'k', 'l', 'm', 'n',
        'o', 'p', 'q', 'r', 's', 't', 'u',
        'v', 'w', 'x', 'y', 'z'
    };    
    
    private final static byte[] INVERSE=new byte[127];
    
    static
    {
        for(byte x=0;x<ALPHABET.length;x++)
        {
            INVERSE[(byte)ALPHABET[x]]=x;
        }
    }
    
    /**
     * Codifica un long a string que puede ser ordenado
     * @param l
     * @return 
     */
    public static String encodeSortableLong(long l)
    {
        StringBuilder str=new StringBuilder();
        byte x=0;
        do
        {
            char c=ALPHABET[(byte)(l&63)];
            str.append(c);
            l=l>>6;
            x++;
        }while(l>0);
        for(;x<6;x++){str.append('0');}
        return str.reverse().toString();        
    }
    
    /*
     * Codifica un long a string para reducir su tamaño
     */
    public static String encodeLong(long l)
    {   
        StringBuilder str=new StringBuilder();
        byte x=0;
        do
        {
            char c=ALPHABET[(byte)(l&63)];
            str.append(c);
            l=l>>6;
            x++;
        }while(l>0);
        return str.reverse().toString();
    }    
    
    /**
     * Decodifica un string a long
     * @param str
     * @return 
     */
    public static long decodeLong(String str)
    {   
        long l=0;
        int i=0;
        do
        {
            byte x=INVERSE[str.charAt(i)];
            l=(l<<6)+x;
            i++;
        }while(i<str.length());
        return l;
    }      
    
    /**
     * Codifica un string para eliminar caracteres especiales
     * @param str
     * @return 
     */
    public static String encodeText(String str)
    {
        if(str==null)return "";
        StringBuilder ret = new StringBuilder();
        for (int x = 0; x < str.length(); x++)
        {
            char ch = str.charAt(x);
            if (ch == '&')
            {
                ret.append("&#38;");
            }else  if (ch == '"')
            {
                ret.append("&#34;");
            }else if (ch > 126 || ch < 32)
            {
                ret.append("&#");
                ret.append( (int) ch);
                ret.append(";");
            } else
            {
                ret.append(ch);
            }
        }
        return ret.toString();
    }

    /**
     * Decodifica un string y recupera sus caracteres especiales
     * @param str
     * @return 
     */
    public static String decodeText(String str)
    {
        if(str==null)return "";
        StringBuilder ret = new StringBuilder();
        int l = str.length();
        for (int x = 0; x < l; x++)
        {
            char ch = str.charAt(x);
            boolean addch = false;
            if (ch == '&' && (x + 1) < l && str.charAt(x + 1) == '#')
            {
                int i = str.indexOf(";", x);
                if (i > x)
                {
                    try
                    {
                        int v = Integer.parseInt(str.substring(x + 2, i));
                        ret.append((char) v);
                        x = i;
                        addch = true;
                    } catch (NumberFormatException e)
                    { //Si no se puede parsear no se agrega
                    }
                }
            }
            if (!addch)
            {
                ret.append(ch);
            }
        }
        return ret.toString();
    }    
    
    private static MessageDigest md;

    /**
     * Obtiene el codigo hash de un string
     * @param txt
     * @return 
     */
    public static synchronized String getHash(String txt)
    {        
        try {
            if(md==null) md = MessageDigest.getInstance("SHA");
            byte b[]=md.digest(txt.getBytes("UTF8"));
            String str=new sun.misc.BASE64Encoder().encode(b);
            return str;
        } catch (Exception e) {
            e.printStackTrace();
        }            
        return null;
    }
    
    /**
     * Funcion startWidth de dos arreglos de bytes
     * @param arr
     * @param str
     * @return 
     */
    public static boolean startWidth(byte arr[], byte str[])
    {
        if(arr==null || str==null)return false;
        
        if(str.length>arr.length)return false;
        
        for(int x=0;x<str.length;x++)
        {
            if(str[x]!=arr[x])return false;
        }
        return true;
    }
    
    /**
     * convierte un string a long positivo, si no es un long regresa null 
     * @param str
     * @return 
     */
    public static Long parseLong(String str)
    {
        long ret=0;
        for(int x=0;x<str.length();x++)
        {
            char c=str.charAt(x);
            if(c>'9' || c<'0')return null;
            ret=ret*10+(byte)(c-'0');
        }
        return ret;
    }    
    
    
    /**
     * Convierte un string con o sin signo, con o sin punto decimal y con o sin comas, a un numero long o double
     * @param txt
     * @return 
     */
    public static Number parseNumber(String txt)
    {        
        byte b=0;
        long l=0;
        long d=0;
        boolean s=false;
        boolean p=false;
        long pp=1;
        for(int x=0;x<txt.length();x++)
        {
            char c=txt.charAt(x);
            if(c=='-')s=true;
            else if(c>='0' && c<='9')
            {
                b=(byte)(c-'0');
                if(!p)
                {
                    l=l*10+b;
                }else
                {   
                    pp=pp*10;
                    d=d*10+b;
                }
            }else if(c=='.')
            {
                p=true;
            }
        }
        if(s)
        {
            if(p)return -(((double)d)/pp+l);
            return -l;
        }
        
        if(p)return ((double)d)/pp+l;
        return l;
    }    
    
}
