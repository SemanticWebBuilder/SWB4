/*
 * INFOTEC WebBuilder es una herramienta para el desarrollo de portales de conocimiento, colaboración e integración para Internet,
 * la cual, es una creación original del Fondo de Información y Documentación para la Industria INFOTEC, misma que se encuentra
 * debidamente registrada ante el Registro Público del Derecho de Autor de los Estados Unidos Mexicanos con el
 * No. 03-2002-052312015400-14, para la versión 1; No. 03-2003-012112473900 para la versión 2, y No. 03-2006-012012004000-01
 * para la versión 3, respectivamente.
 *
 * INFOTEC pone a su disposición la herramienta INFOTEC WebBuilder a través de su licenciamiento abierto al público (‘open source’),
 * en virtud del cual, usted podrá usarlo en las mismas condiciones con que INFOTEC lo ha diseñado y puesto a su disposición;
 * aprender de él; distribuirlo a terceros; acceder a su código fuente y modificarlo, y combinarlo o enlazarlo con otro software,
 * todo ello de conformidad con los términos y condiciones de la LICENCIA ABIERTA AL PÚBLICO que otorga INFOTEC para la utilización
 * de INFOTEC WebBuilder 3.2.
 *
 * INFOTEC no otorga garantía sobre INFOTEC WebBuilder, de ninguna especie y naturaleza, ni implícita ni explícita,
 * siendo usted completamente responsable de la utilización que le dé y asumiendo la totalidad de los riesgos que puedan derivar
 * de la misma.
 *
 * Si usted tiene cualquier duda o comentario sobre INFOTEC WebBuilder, INFOTEC pone a su disposición la siguiente
 * dirección electrónica:
 *
 *                                          http://www.webbuilder.org.mx
 */



/*
 * XMLParser.java
 *
 * Created on 8 de agosto de 2002, 13:27
 */
package applets.commons;

import java.io.*;
import java.util.*;
/**
 * Proporciona un parser para XML.
 *
 * It provides a XML parser
 *
 * @author  Administrador
 * @version 
 */
public class WBXMLParser {
    
    private String encoding=null;
    
    /** Creates new XMLParser */
    public WBXMLParser() {
    }
    
    public WBTreeNode createNode(String auxtag)
    {
        //System.out.println("createNode:"+auxtag);
        WBTreeNode auxnode=new WBTreeNode();
        auxnode.setType(WBTreeNode.TAG);
        
        boolean name=true;
        boolean attname=false;
        boolean value=false;
        boolean commi=false;
        String att=null;
        
        StringTokenizer st=new StringTokenizer(auxtag," =\"", true);
        while(st.hasMoreTokens())
        {
            String tok=st.nextToken();
            if(tok.equals(" "))
            {
                
            }else if(tok.equals("="))
            {
                attname=false;
                value=true;
            }else if(tok.equals("\""))
            {
                String val=tok;
                while(st.hasMoreTokens())
                {
                    tok=st.nextToken();
                    val+=tok;
                    if(tok.equals("\""))break;
                }
                //System.out.println("value="+val);
                auxnode.addAttribute(att,replaceStrTags(val));
                att=null;
                value=false;
                attname=true;
            }else
            {
                if(name)
                {
                    auxnode.setName(tok);
                    //System.out.println("name="+tok);
                    name=false;
                    attname=true;
                }else if(attname)
                {
                    //System.out.println("attname="+tok);
                    if(att!=null)
                    {
                        auxnode.addAttribute(att,"");
                    }
                    att=tok;
                    //attname=false;
                }else if(value)
                {
                    //System.out.println("value="+tok);
                    auxnode.addAttribute(att,tok);
                    att=null;
                    value=false;
                    attname=true;
                }
            }
            
        }
        
        
        
        
        
        return auxnode;
    }
    
    public WBTreeNode parse(String xml)
    {
        WBTreeNode node=new WBTreeNode();
        WBTreeNode auxnode=node;
        
        StringBuffer aux=new StringBuffer();
        
        int len=xml.length();
        char bxml[]=new char[len];
        xml.getChars(0,len,bxml,0);
        boolean tag=false;
        boolean data=false;
        for(int x=0;x<len;x++)
        {
           if(bxml[x]=='<')
           {
               tag=true;
               if(data==true)
               {
                   if(aux.toString().trim().length()!=0)
                   {
                        auxnode=auxnode.addNode();
                        auxnode.setType(WBTreeNode.TEXT);
                        auxnode.setText(replaceStrTags(aux.toString()));
                        auxnode=auxnode.getParent();
                        //System.out.println("text:("+aux+")");
                   }
                   aux=new StringBuffer();
               }
               data=false;
           }
           else if(bxml[x]=='>')
           {
                String auxtag=aux.toString();
                tag=false;
                data=true;
                if(auxtag.startsWith("/"))
                {
                    String temptag=auxtag.substring(1);
                    //System.out.println("temptag:"+temptag+" "+auxnode.getName());
                    if(auxnode.getName().equals(temptag))
                    {
                        auxnode=auxnode.getParent();
                        //System.out.println("endtag:("+temptag+")");
                    }else
                    {
                        System.out.println("Error al parsear xml");
                        return null;
                    }
                }
                else if(auxtag.endsWith("/"))
                {
                    String temptag=auxtag.substring(0,auxtag.length()-1);
                    auxnode.addNode(this.createNode(temptag));
/*                    
                    auxnode=auxnode.addNode();
                    auxnode.setName(temptag);
                    auxnode.setType(WBTreeNode.TAG);
                    auxnode=auxnode.getParent();
*/
                    //System.out.println("startendtag:("+temptag+")");
                }
                else if(auxtag.startsWith("?xml"))
                {
                    auxtag=auxtag.substring(1,auxtag.length()-1);
                    StringTokenizer st=new StringTokenizer(auxtag," =");
                    String temptag=st.nextToken();
                    if(auxnode.getParent()==null)
                    {
                        auxnode.setType(WBTreeNode.XMLTAG);
                        auxnode.setName(temptag);
                        //System.out.println("xmltag:("+temptag+")");
                        while(st.hasMoreTokens())
                        {
                            String attname=st.nextToken();
                            String attvalue=st.nextToken();
                            //System.out.println("attribute: ("+attname+")=("+attvalue+")");
                            auxnode.addAttribute(attname,replaceStrTags(attvalue));
                        }
                        encoding=auxnode.getAttribute("encoding");
                        if(encoding!=null)
                        {
                            try
                            {
                                //System.out.println("encoding:"+encoding);
                                ByteArrayInputStream inps=new ByteArrayInputStream(xml.getBytes());
                                InputStreamReader inpsr=new InputStreamReader(inps,encoding);
                                len=inpsr.read(bxml,0,len);
                            }catch(Exception e){System.out.println("Error al codificar xml..."+e);}
                        }
                        
                    }
                    else
                    {
                        System.out.println("Error al parsear xml");
                        return null;
                    }
                }
                else 
                {
                    WBTreeNode newNode=this.createNode(auxtag);
                    auxnode.addNode(newNode);
                    auxnode=newNode;
/*                    
                    StringTokenizer st=new StringTokenizer(auxtag," =");
                    String temptag=st.nextToken();
                    auxnode=auxnode.addNode();
                    auxnode.setName(temptag);
                    auxnode.setType(WBTreeNode.TAG);
                    //System.out.println("starttag:("+temptag+")");
                    while(st.hasMoreTokens())
                    {
                        String attname=st.nextToken();
                        String attvalue=st.nextToken();
                        //System.out.println("attribute: "+attname+"="+attvalue);
                        auxnode.addAttribute(attname,attvalue);
                    }
*/
                }
                aux=new StringBuffer();
           }
           else 
           {
               aux.append((char)bxml[x]);
           }
        }
        return node;
    }
    
    static public String encode(String data,String enc) throws java.io.UnsupportedEncodingException, java.io.IOException
    {
        ByteArrayOutputStream sw=new java.io.ByteArrayOutputStream();
        OutputStreamWriter out=new OutputStreamWriter(sw,enc);
        out.write(data);
        out.flush();
        return new String(sw.toByteArray());
    }
    
    static public String replaceStrTags(String txt)
    {
        StringBuffer str=new StringBuffer(txt);
        for(int x=0;x<str.length();x++)
        {
            char ch=str.charAt(x);
            if(ch=='&' && str.charAt(x+1)!='#')
            {
                if((str.length()>=x+4) && str.substring(x,x+4).equals("&lt;"))
                {
                    str.replace(x,x+4,"<");
                }
                else if((str.length()>=x+4) && str.substring(x,x+4).equals("&gt;"))
                {
                    str.replace(x,x+4,">");
                }
                else if((str.length()>=x+5) && str.substring(x,x+5).equals("&amp;"))
                {
                    str.replace(x,x+5,"&");
                }
                else if((str.length()>=x+6) && str.substring(x,x+6).equals("&quot;"))
                {
                    str.replace(x,x+6,"\"");
                }else
                {
                    System.out.println("Codificación no validada:"+str.substring(x));
                }
            }
        }
        return replaceAmpTags(str);
    }
    
    static public String replaceAmpTags(StringBuffer str)
    {
        for(int x=0;x<str.length();x++)
        {
            char ch=str.charAt(x);
            if(ch=='&')
            {
                int i=str.indexOf(";",x);
                if(i>2 && str.charAt(x+1)=='#')
                {
                    //System.out.println("x:"+x+" "+i);
                    try
                    {
                        int v=Integer.parseInt(str.substring(x+2,i)); 
                        //System.out.println(v);
                        str.replace(x,i+1,""+(char)v);
                    }catch(Exception noe){};
                }
            }
        }
        return str.toString();
    }
    
    static public String replaceAmpChars(String txt)
    {
        return replaceAmpChars(txt,255);
    }
    
    static public String replaceAmpChars(String txt, int graterthan)
    {
        StringBuffer str=new StringBuffer(txt);
        for(int x=0;x<str.length();x++)
        {
            char ch=str.charAt(x);
            System.out.println("char:"+(int)ch+" "+ch);
            if(((int)ch)>graterthan)
            {
                str.replace(x,x+1,"&#"+(int)ch+";");
            }
        }
        return str.toString();
    }    
    
    static public String replaceStrChars(String str)
    {
        str=replaceAmpChars(str);
        StringBuffer ret=new StringBuffer();

        // split tokens
        StringTokenizer tokenizer = new StringTokenizer(str, " \t@%^&()-+=|\\{}[].;\"<>", true );
        while( tokenizer.hasMoreTokens() )    
        {
            // next token
            String token = tokenizer.nextToken();

            // replace '\t' by the content of "tabulation"
            if(token.startsWith("\t"))
            {
                ret.append("    ");
                continue;
            }

            // replace '<' by '&lt;'
            if(token.startsWith("<"))
            {
                ret.append("&lt;");
                continue;
            }

            // replace '>' by '&gt;'
            if( token.startsWith(">"))
            {
                ret.append("&gt;");
                continue;
            }

            // replace '&' by '&amp;'
            if(token.startsWith("&"))
            {
                ret.append("&amp;");
                continue;
            }

            // replace '"' by '&quot;'
            if(token.startsWith("\""))
            {
                ret.append("&quot;");
                continue;
            }
            ret.append(token);
        }
        return str=replaceAmpChars(ret.toString(),127);
        
    }
    
}
