<%@page contentType="text/html;charset=windows-1252"%>
<%@page import="org.semanticwb.*"%>
<%@page import="java.util.*"%>
<%@page import="java.io.*"%>

<html>
<head><title>JSP Page</title></head>
<body>
Inicia...   

<%
 ArrayList aextensions=new ArrayList();
 aextensions.add(".htm");
 aextensions.add(".html");
 aextensions.add(".xsl");
 aextensions.add(".xslt");

// sites\Visitmexico\resources\LocalContent    E:\WBSectur\wb3\work\resources\LocalContent /work/sites/sep1/resources/LocalContent

 System.out.println("Ruta a revisar para modificar /wb/ y /contexto/ por /swb/ ... :"+SWBPortal.getWorkPath()+"models/COSWB/Template/");
 repaleceStringIntoDirectory(SWBPortal.getWorkPath()+"/models/COSWB/Template/","/OS/wb/","/swb/",aextensions);
 repaleceStringIntoDirectory(SWBPortal.getWorkPath()+"/models/COSWB/Template/","/OS/css/","/css/",aextensions);
 repaleceStringIntoDirectory(SWBPortal.getWorkPath()+"/models/COSWB/Template/","/OS/videos/","/videos/",aextensions);
    
%>

Termina

</body>

</html>

<%!
public void repaleceStringIntoDirectory(String directoryPath,String string1,String string2,ArrayList extensions) 
    {
        java.io.File dir=new java.io.File(directoryPath);
        if(dir!=null && dir.exists() && dir.isDirectory()){ //para el directorio site
            java.io.File[] listado=dir.listFiles();
            for (int i=0; i < listado.length;i++) {
                if(listado[i].isFile()) // es archivo y se parsea directamente
                {
                    String filename=listado[i].getName();
                    if(filename!=null)
                    {
                        int pos=-1;
                        pos=filename.lastIndexOf(".");
                        if(pos>-1)
                        {
                            String fileExtension=filename.substring(pos+1);
                            if(extensions.contains(fileExtension))
                            {
                                String content;
                                content=getFileFromPath(listado[i].getPath());
                                if(content!=null) {
                                    replaceString(content,listado[i].getPath(),string1,string2);
                                }
                            }
                        }
                    }
                }
                else if(listado[i].isDirectory()) 
                {
                    goInsideDirectory(listado[i],string1,string2,extensions);
                }
            }
            
        }
    }

    public void goInsideDirectory(File dir,String string1,String string2,ArrayList extensions) 
    {
        java.io.File[] listado=dir.listFiles();
        for (int i=0; i < listado.length;i++) {
            if(listado[i].isFile()) // es archivo y se parsea directamente
            {
                String filename=listado[i].getName();
                if(filename!=null)
                {
                    int pos=-1;
                    pos=filename.lastIndexOf(".");
                    if(pos>-1)
                    {
                        String fileExtension=filename.substring(pos);
                        if(extensions.contains(fileExtension))
                        {
                            String content;
                            content=getFileFromPath(listado[i].getPath());
                            if(content!=null) {
                                replaceString(content,listado[i].getPath(),string1,string2);
                            }
                        }
                    }
                }
            }
            else if(listado[i].isDirectory()) {
                goInsideDirectory(listado[i],string1,string2,extensions);
            }
        }
    }
    
    
    public String getFileFromPath(String path) 
    {
        StringBuffer ret=new StringBuffer(8192);
        try{
            InputStream file=null;
            file=new FileInputStream(path);
            byte[] bfile= new byte[8192];
            int x;
            while((x=file.read(bfile,0,8192))>-1) {
                ret.append(new String(bfile,0,x));
            }
            file.close();
        }catch(Exception e){}
        return ret.toString();
    }
    
    public void replaceString(String content,String path,String string1,String string2) 
    {
        try{
            content=content.replaceAll(string1,string2);
            byte abyte[]=content.getBytes();
            FileOutputStream fileoutputstream = new FileOutputStream(path);
            fileoutputstream.write(abyte, 0, abyte.length);
            fileoutputstream.close();
        }catch(Exception e) {
            System.out.println(e);
        }
    }

%>

