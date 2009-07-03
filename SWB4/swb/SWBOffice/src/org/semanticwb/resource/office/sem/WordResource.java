package org.semanticwb.resource.office.sem;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.*;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Resource;
import org.semanticwb.model.User;
import org.semanticwb.model.WebPage;
import org.semanticwb.office.comunication.OfficeDocument;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.portal.api.*;

public class WordResource extends org.semanticwb.resource.office.sem.base.WordResourceBase 
{
    public WordResource()
    {
        super();
    }
    public WordResource(SemanticObject obj)
    {
        super(obj);
    }
    private static Logger log = SWBUtils.getLogger(WordResource.class);
    protected void beforePrintDocument(PrintWriter out)
    {

    }

    protected void afterPrintDocument(PrintWriter out)
    {

    }

    protected void printDocument(PrintWriter out, String path, String workpath,String html)
    {
        out.write(html);
    }

    public static org.semanticwb.resource.office.sem.WordResource createWordResource(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.resource.office.sem.WordResource)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
    }

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
            WebPage page=paramRequest.getTopic();
            String version = getVersionToShow();
            String contentId = getContent();
            String repositoryName = getRepositoryName();
            OfficeDocument document = new OfficeDocument();
            try
            {
                User user = paramRequest.getUser();
                String file = document.getContentFile(repositoryName, contentId, version, user);
                if (file != null)
                {

                    file = file.replace(".doc", ".html");
                    String path = SWBPlatform.getWorkPath();
                    if (path.endsWith("/"))
                    {
                        path = path.substring(0, path.length() - 1);
                        path += getResourceBase().getWorkPath() + "/" + file;
                    }
                    else
                    {
                        path += getResourceBase().getWorkPath() + "/" + file;
                    }

                    File filecontent = new File(path);
                    if (filecontent.exists())
                    {
                        String workpath = SWBPlatform.getWebWorkPath() + getResourceBase().getWorkPath() + "/";
                        StringBuffer html = new StringBuffer();
                        try
                        {
                            FileInputStream in = new FileInputStream(path);
                            byte[] buffer = new byte[2048];
                            int read = in.read(buffer);
                            while (read != -1)
                            {
                                html.append(new String(buffer, 0, read));
                                read = in.read(buffer);
                            }
                            String htmlOut = null;                            
                            if (isPaginated() && getNumberOfPages() > 0)
                            {
                                htmlOut = SWBPortal.UTIL.parseHTML(html.toString(), workpath, getNumberOfPages());
                            }
                            else
                            {
                                htmlOut = SWBPortal.UTIL.parseHTML(html.toString(), workpath);
                            }
                            PrintWriter out = response.getWriter();
                            beforePrintDocument( out);
                            htmlOut=pagination(htmlOut, page);
                            printDocument(out, path, workpath,htmlOut);
                            afterPrintDocument(out);
                            out.close();
                        }
                        catch (Exception e)
                        {
                            log.error(e);
                        }

                    }
                    else
                    {
                        log.error("Contenido no encontrado en ruta: " + filecontent.getAbsolutePath() + ": " + getContent() + "@" + getRepositoryName());
                    }
                }
            }
            catch (Exception e)
            {
                log.error(e);
            }
    }

    private String pagination(String htmlOut, WebPage page)
    {
        int itpage=getContentPagesNumber(htmlOut);
        System.out.println("itpage:"+itpage);
        if(itpage>1){
            int ipage=1;
            htmlOut=getContentByPage(htmlOut,itpage,ipage,page);
            System.out.println("htmlOut-final:"+htmlOut);
        }
        return htmlOut;
    }


    /**
     * Metodo que regresa el número de páginas con las que cuenta el contenido
     */
    private int getContentPagesNumber(String content){
        int pos=content.lastIndexOf("div.Section");
        if(pos>-1){
            int pos1=content.indexOf("{",pos);
            int npages=Integer.parseInt(content.substring(pos+11,pos1-1).trim());
            return npages;
        }
        return 1;
    }

    //regresa string html de contenido páginado
    /**
     * Metodo que regresa el contenido por página
     */
    private String getContentByPage(String content,int itpage,int ipage,WebPage webpage){
        StringBuffer strb=new StringBuffer();
        try{
            Resource base=getResourceBase();
            int snpages=Integer.parseInt(base.getAttribute("npages","15"));
            String stxtant=base.getAttribute("txtant", "Anterior");
            String stxtsig=base.getAttribute("txtsig", "Siguiente");
            String stfont=base.getAttribute("tfont", "font face=\"Verdana, Arial, Helvetica, sans-serif\" size=\"2\" color=\"#000000\"");
            int position=Integer.parseInt(base.getAttribute("position", "1"));

            StringBuffer strb1=new StringBuffer();

            strb1.append("<table width=\"100%\">");
            strb1.append("<tr>");
            strb1.append("<td align=\"center\">");
            String path=webpage.getUrl()+"?";

//            if(sredAdmin!=null && actual==0) { //crea path para ultima versión
//                path=sredAdmin+"&wblastversion=1&";
//                if(request.getParameter("idcontent")!=null) path=path+"&idcontent="+request.getParameter("idcontent")+"&";
//            }else if(sredAdmin!=null && actual==1){ //crea path para versión actual
//                path=sredAdmin+"&wbactualversion=1&urladmin="+sredAdmin+"&";
//            }else if(sredAdmin!=null && actual==2){  //crea path para versión que se le indique
//                path=sredAdmin+"&wbresversion="+iversion+"&urladmin="+sredAdmin+"&";
//            }
            
            if(ipage>1) {
                strb1.append("<a href=\""+path+"page="+(ipage-1)+"\"><"+stfont+">"+stxtant+"</font></a> ");
            }
            int ini=1;
            int fin=snpages;
            int dif=0;
            if((itpage<snpages)) fin=itpage;
            if(itpage>snpages && ipage>1){
                dif=ipage-1;
                if(itpage>=(snpages+dif)){
                    fin=snpages+dif;
                    ini=1+dif;
                }else{
                    fin=itpage;
                    ini=itpage-snpages+1;
                }
            }

            for(int i=ini;i<=fin;i++){
                if(i!=ipage){
                    strb1.append("<a href=\""+path+"page="+i+"\"><"+stfont+">"+String.valueOf(i)+"</font></a> ");
                }
                else
                    strb1.append("<font color=\"RED\">"+String.valueOf(i)+" </font>");
            }
            if(ipage<itpage)  {
                strb1.append("<a href=\""+path+"page="+(ipage+1)+"\"><"+stfont+">"+stxtsig+"</font></a>");
            }
            strb1.append("</td>");
            strb1.append("</tr>");
            strb1.append("</table>");

            if(position==1){
                strb.append(SWBPortal.UTIL.getContentByPage(content,ipage));
                strb.append("<br><br>");
                strb.append(strb1.toString());
            }else if(position==2){
                strb.append(strb1.toString());
                strb.append("<br><br>");
                strb.append(SWBPortal.UTIL.getContentByPage(content,ipage));
            }else if(position==3){
                strb.append(strb1.toString());
                strb.append("<br><br>");
                strb.append(SWBPortal.UTIL.getContentByPage(content,ipage));
                strb.append("<br><br>");
                strb.append(strb1.toString());
            }
        }catch(Exception e){
            log.error(e);
        }
        return strb.toString();
    }

}
