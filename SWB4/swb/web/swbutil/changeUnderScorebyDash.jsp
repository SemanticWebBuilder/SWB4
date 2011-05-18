<%@page contentType="text/html;charset=windows-1252"%>
<%@page import="java.io.*"%>
<%@page import="java.util.*"%>

<html>
<head><title>JSP Page</title></head>
<body>

    <form name="envia" action="#">
      <br>
      Ruta del archivo .nt:<input type="text" name="pathFile">
      <br>
      <input type="submit" name="send" value="Enviar">
    </form>

<%
    if(request.getParameter("pathFile")!=null)
    {
        try{
            String pathFile=request.getParameter("pathFile");
            FileInputStream fptr;
            DataInputStream f;
            String linea = null;
            fptr = new FileInputStream(WBUtils.getInstance().getAppPath()+request.getParameter("pathFile"));
            f = new DataInputStream(fptr);
            int cont=0;
            File file=new File(WBUtils.getInstance().getAppPath()+request.getParameter("pathFile")+"_new");
            FileOutputStream outFile=new FileOutputStream(file);
            do {
                cont++;
                linea = f.readLine();
                System.out.println("lineaJ:"+linea);
                if (linea!=null)
                {
                    int pos=-1;
                    pos=linea.indexOf("http://www.visit.swb#WebPage:");
                    if (pos>-1)
                    {
                            linea=replazeWebPageID(linea,pos); //reemplaza en webpages
                    }
                    pos=-1;
                    pos=linea.indexOf("<topic childs=");
                    if (pos>-1)
                    {
                            linea=replazeFilterWebPageID(linea,pos); //reemplaza en webpages
                    }
                    linea=linea+"\n";
                    outFile.write(linea.toString().getBytes("utf-8"));
                }               
            } while (linea != null);
            fptr.close();
            outFile.flush();
            outFile.close();
            out.println("<br><br><b>Termina..</b>");
        }catch(Exception e){
            System.out.println(e);
            e.printStackTrace();
        }
    }

%>

<%!
    public String replazeWebPageID(String linea, int pos)
    {
        try{
            int pos1=-1;
            pos1=linea.indexOf(">", pos);
            if(pos1>-1)
            {
                String slinea=linea.substring(pos, pos1);
                //System.out.println("slinea:"+slinea);
                slinea=slinea.replaceAll("_", "-");
                if(pos>0){
                    linea=linea.substring(0, pos)+slinea+linea.substring(pos1);
                }else linea=slinea+linea.substring(pos1);
                int posTmp=-1;
                posTmp=linea.indexOf("http://www.visit.swb#WebPage:", pos1);
                if (posTmp>-1)
                {
                        linea=replazeWebPageID(linea,posTmp); //reemplaza en webpages
                }
            }
        }catch(Exception e){
            e.printStackTrace(System.out);
        }
        return linea;
    }


    public String replazeFilterWebPageID(String linea, int pos)
    {
        try{
            int pos1=-1;
            pos1=linea.indexOf(" id=\\\"", pos);
            if(pos1>-1)
            {
                int pos2=-1;
                pos2=linea.indexOf("\\\"", pos1+6);
                String sid=linea.substring(pos1+6, pos2);
                //System.out.println("sid:"+sid);
                sid=sid.replaceAll("_", "-");
                linea=linea.substring(0,pos1+6)+sid+linea.substring(pos2);
                int posTmp=linea.indexOf("<topic childs=", pos2);
                if (posTmp>-1)
                {
                        linea=replazeFilterWebPageID(linea,posTmp); //reemplaza en webpages
                }
            }
        }catch(Exception e){
            e.printStackTrace(System.out);
        }
        return linea;
    }
%>
</body>
</html>
