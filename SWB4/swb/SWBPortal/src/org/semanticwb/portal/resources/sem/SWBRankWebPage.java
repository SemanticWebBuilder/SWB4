package org.semanticwb.portal.resources.sem;
//org.semanticwb.portal.resources.sem.SWBRankWebPage
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import javax.servlet.http.*;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.ProgressListener;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Resource;
import org.semanticwb.model.WebPage;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.portal.api.*;


public class SWBRankWebPage extends org.semanticwb.portal.resources.sem.base.SWBRankWebPageBase
{
    private static Logger log = SWBUtils.getLogger(SWBRankWebPage.class);
    // http://www.semanticwebbuilder.org/swb4/ontology#rank
    // http://www.semanticwebbuilder.org/swb4/ontology#reviews


    private String fullStarPath;
    private String halfStarPath;
    private String emptyStarPath;
    private static final org.semanticwb.platform.SemanticProperty sp_rank = org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#rank");
    private static final org.semanticwb.platform.SemanticProperty sp_reviews = org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#reviews");

    public SWBRankWebPage()
    {
    }

    public SWBRankWebPage(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    @Override
    public void setResourceBase(Resource base) throws SWBResourceException
    {
        super.setResourceBase(base);
        fullStarPath =  getFullStar();
        halfStarPath = getHalfStar();
        emptyStarPath = getEmptyStar();
        if (null == fullStarPath)
        {
            fullStarPath = "/swbadmin/resources/ranking/fullstar.png";
        }
        if (null == halfStarPath)
        {
            halfStarPath = "/swbadmin/resources/ranking/halfstar.png";
        }
        if (null == emptyStarPath)
        {
            emptyStarPath = "/swbadmin/resources/ranking/emptystar.png";
        }
    }

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        /*

        <script language="javascript" type="text/javascript">
var request = false;
try {
  request = new XMLHttpRequest();
} catch (trymicrosoft) {
  try {
    request = new ActiveXObject("Msxml2.XMLHTTP");
  } catch (othermicrosoft) {
    try {
      request = new ActiveXObject("Microsoft.XMLHTTP");
    } catch (failed) {
      request = false;
    }
  }
}

if (!request)
  alert("Error initializing XMLHttpRequest!");


function getCustomerInfo() {
     var phone = document.getElementById("phone").value;
     var url = "/cgi-local/lookupCustomer.php?phone=" + escape(phone);
     request.open("GET", url, true);
     request.onreadystatechange = updatePage;
     request.send(null);
   }

function vote(val){
    var url = "url+"value="+escape(val);
    request.open("GET", url, true);
    request.onreadystatechange = votedPage;
    request.send(null);
}

function votedPage(){
    var response = request.responseText;
    if ('OK'==response)
        alert('Vote acepted!');
}


</script>
        */
        String tmpUrl = "";
        String URI = request.getParameter("uri");
        SemanticObject obj = SemanticObject.createSemanticObject(URI);// paramRequest.getWebPage().getWebSite().;
        PrintWriter out = response.getWriter();
        int rank = 0;
        if (null!=obj)
        {
            tmpUrl = "+\"&uri=\"+escape('"+URI+"')";
            rank = (int) Math.round(Math.floor(obj.getDoubleProperty(sp_rank)* 10));
        }
        else
        {
         rank = (int) Math.round(Math.floor(paramRequest.getWebPage().getRank() * 10));
        }
        //System.out.println("Rank:" + rank);
        SWBResourceURL url = paramRequest.getActionUrl();
        url.setAction("vote");
        url.setMode(SWBResourceURL.Mode_HELP);
        url.setCallMethod(SWBResourceURL.Call_DIRECT);
        out.println("\n<script language=\"javascript\" type=\"text/javascript\">\nvar request = false;\ntry {\n  request = new XMLHttpRequest();\n} " +
                "catch (trymicrosoft) {\n  try {\n    request = new ActiveXObject(\"Msxml2.XMLHTTP\");\n  } catch (othermicrosoft) {\n    try {\n " +
                "      request = new ActiveXObject(\"Microsoft.XMLHTTP\");\n    } catch (failed) {\n      request = false;\n    }\n  }\n}\nif " +
                "(!request)\n  alert(\"Error initializing XMLHttpRequest!\");\n\nfunction vote(val){\n    if (!invoke) return;\n    var url = \""+url+"?value=\"+escape(val)"+tmpUrl+";\n" +
                "    request.open(\"GET\", url, true);\n    request.onreadystatechange = votedPage;\n    request.send(null);\n}\n\nfunction votedPage(){\n" +
                "    var response = request.responseText;\n    if ('OK'==response)\n        alert('Vote acepted!');\n\n    invoke = false;}\nvar invoke = true;\n</script>\n");
        out.print("<table boder=\"0\" cellpadding=\"0\" cellspacing=\"0\"><tr>");
        for (int i =1;i<=5;i++)
        printStar(i, rank, out, paramRequest);
        out.print("</tr></table>");

    }

    private void printStar(int current, int rank, PrintWriter out, SWBParamRequest paramRequest)
    {
        /*SWBResourceURL url = paramRequest.getActionUrl();
        url.setAction("vote");
        url.setParameter("value", "" + current);
        */
        String url = "javascript:vote("+current+");";
        int midl = (current*10)-7;
        int midt = (current*10)-2;
        String imgRank = emptyStarPath;
        if (rank>=midl&&rank<=midt) imgRank = halfStarPath;
        if (rank>midt) imgRank = fullStarPath;
        out.print("<td><a href=\"" + url + "\" title=\"Give "+current+" star(s)\"><img border=\"0\" src=\"" + SWBPlatform.getContextPath()+imgRank +"\" alt=\"has "+((0.0f + rank)/10.0f)+" star(s)\"/></a></td>");

    }

    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException
    {
        String URI = request.getParameter("uri");
        SemanticObject obj = null;
        if (null!=URI) obj = SemanticObject.createSemanticObject(URI);
        Cookie[] cookies= request.getCookies();
        for (Cookie cookie: cookies){
            //System.out.println(cookie.getName());
            SemanticObject tmp = response.getWebPage().getSemanticObject();
            if (null!=obj) tmp = obj;
         if (cookie.getName().equals(tmp.getId()+"_voted"))
         {
             response.setMode(SWBResourceURL.Mode_EDIT);
             //System.out.println("VotedOff ");
             return;
         }

        }
        int vote = 0;
        //System.out.println("value got: "+request.getParameter("value"));
        if (null!=request.getParameter("value")){
            try {vote = Integer.parseInt(request.getParameter("value"));} catch (Exception ne){}
        WebPage ws = response.getWebPage();
        //System.out.println("ws: "+ws+" vote:"+vote);
        double rank = 0;
        long rev = 0;
        String tmpUrl = "";
        
        if (null==obj)
        {
            rank = ws.getRank();
            rev = ws.getReviews();
        }
        else
        {
            rank = obj.getDoubleProperty(sp_rank);
            rev = obj.getLongProperty(sp_reviews);
            response.setRenderParameter("uri", URI);
        }
        rank = rank * rev;
        rev++;
        rank = rank + vote;
        rank = rank / rev;

        if (null==obj)
        {
        ws.setRank(rank);
        ws.setReviews(rev);
        }
        else
        {
            obj.setDoubleProperty(sp_rank, rank);
            obj.setLongProperty(sp_reviews, rev);
        }
        //System.out.println("rev:"+rev+" rank:"+rank);
        }

    }

    @Override
    public void doHelp(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        String URI = request.getParameter("uri");
        SemanticObject obj = null;
        if (null!=URI) obj = SemanticObject.createSemanticObject(URI);
        else obj = paramRequest.getWebPage().getSemanticObject();
        Cookie cookie = new Cookie(obj.getId()+"_voted", "true");
        cookie.setPath("/");
        response.addCookie(cookie);
            //response.setHeader(fullStarPath, fullStarPath);
             PrintWriter out = response.getWriter();
             out.print("OK");
    }

    @Override
    public void doEdit(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
             PrintWriter out = response.getWriter();
             out.print("Not OK");
    }

    @Override
    public void doAdmin(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
            PrintWriter out = response.getWriter();

            out.println("            <script language=\"javascript\" type=\"text/javascript\" src=\"" + SWBPlatform.getContextPath() + "/swbadmin/js/upload.js\"></script>");
            out.println("	<style type=\"text/css\">\n @import \"" + SWBPlatform.getContextPath() + "/swbadmin/css/upload.css\";\n </style>");
            out.println("    <script type=\"text/javascript\">");
            out.println("  dojo.require(\"dojo.parser\");");
            out.println("    var uploads_in_progress = 0;");
            out.println();
            out.println("    function beginAsyncUpload(ul,sid) {");
            out.println("      ul.form.submit();");
            out.println("    	uploads_in_progress = uploads_in_progress + 1;");
            out.println("    	var pb = document.getElementById(ul.name + \"_progress\");");
            out.println("    	pb.parentNode.style.display='block';");
            out.println("    	new ProgressTracker(sid,{");
            out.println("    		progressBar: pb,");
            out.println("    		onComplete: function() {");
            out.println("    			var inp_id = pb.id.replace(\"_progress\",\"\");");
            out.println("    			uploads_in_progress = uploads_in_progress - 1;");
            out.println("    			var inp = document.getElementById(inp_id);");
            out.println("    			if(inp) {");
            out.println("    				inp.value = sid;");
            out.println("    			}");
            out.println("    			pb.parentNode.style.display='none';");
            out.println("    		},");
            out.println("    		onFailure: function(msg) {");
            out.println("    			pb.parentNode.style.display='none';");
            out.println("    			alert(msg);");
            out.println("    			uploads_in_progress = uploads_in_progress - 1;");
            out.println("    		},");
            out.println("            url: '"+paramRequest.getRenderUrl().setMode(SWBResourceURL.Mode_ADMHLP)
                    .setCallMethod(SWBResourceURL.Call_DIRECT).setAction(SWBResourceURL.Action_EDIT)+"'");
            out.println("    	});");
            out.println("    }");
            out.println();
            out.println("	</script>");
            out.println("<div class=\"swbform\">");
            out.println("    <fieldset>");
            out.println("    <table>");
            out.println("                <tr><td width=\"200px\" align=\"right\">");
            out.println("                    <label>Identificador &nbsp;</label>");
            out.println("                </td><td>");
            out.println("                    <span>"+getResourceBase().getId()+"</span>");
            out.println("                </td></tr>");
            out.println("            </table>");
            out.println("            </fieldset>");
            out.println("            <fieldset>");
            out.println("                <legend>Datos Generales</legend>");
            out.println("                <table>");
            out.println("                    <tr><td width=\"200px\" align=\"right\"><label for=\"fullStar\">Imagen Estrella Completa &nbsp;</label></td>");
            out.println("                    <td><iframe id=\"fullStarTransferFrame\" name=\"fullStarTransferFrame\" src=\"\" style=\"display:none\" ></iframe>");
            out.println("                        <form enctype=\"multipart/form-data\"");
            out.println("                        action=\""+paramRequest.getRenderUrl().setMode(SWBResourceURL.Mode_ADMHLP)
                    .setCallMethod(SWBResourceURL.Call_DIRECT).setAction(SWBResourceURL.Action_ADD)+"\"");
            out.println("                        method=\"post\" target=\"fullStarTransferFrame\" >");
            out.println("                        <input type=\"file\" name=\"fullStar\"");
            out.println("                        onchange=\"beginAsyncUpload(this,'fullStar');\" />");
            out.println("                        <div class=\"progresscontainer\" style=\"display: none;\"><div class=\"progressbar\" id=\"fullStar_progress\"></div></div>");
            out.println("                        </form>");
            out.println("                    </td></tr>");
            out.println("                    <tr><td width=\"200px\" align=\"right\"><label for=\"halfStar\">Imagen de Media Estrella &nbsp;</label></td>");
            out.println("                    <td><iframe id=\"halfStarTransferFrame\" name=\"halfStarTransferFrame\" src=\"\" style=\"display:none\" ></iframe>");
            out.println("                        <form enctype=\"multipart/form-data\"");
            out.println("                        action=\""+paramRequest.getRenderUrl().setMode(SWBResourceURL.Mode_ADMHLP)
                    .setCallMethod(SWBResourceURL.Call_DIRECT).setAction(SWBResourceURL.Action_ADD)+"\"");
            out.println("                        method=\"post\" target=\"halfStarTransferFrame\" >");
            out.println("                        <input type=\"file\" name=\"halfStar\"");
            out.println("                        onchange=\"beginAsyncUpload(this,'halfStar');\" />");
            out.println("                        <div class=\"progresscontainer\" style=\"display: none;\"><div class=\"progressbar\" id=\"halfStar_progress\"></div></div>");
            out.println("                        </form>");
            out.println("                    </td></tr>");
            out.println("                    <tr><td width=\"200px\" align=\"right\"><label for=\"emptyStar\">Imagen de Estrella vacía &nbsp;</label></td>");
            out.println("                    <td><iframe id=\"emptyStarTransferFrame\" name=\"emptyStarTransferFrame\" src=\"\" style=\"display:none\" ></iframe>");
            out.println("                        <form enctype=\"multipart/form-data\"");
            out.println("                        action=\""+paramRequest.getRenderUrl().setMode(SWBResourceURL.Mode_ADMHLP)
                    .setCallMethod(SWBResourceURL.Call_DIRECT).setAction(SWBResourceURL.Action_ADD)+"\"");
            out.println("                        method=\"post\" target=\"emptyStarTransferFrame\" >");
            out.println("                        <input type=\"file\" name=\"emptyStar\"");
            out.println("                        onchange=\"beginAsyncUpload(this,'emptyStar');\" />");
            out.println("                        <div class=\"progresscontainer\" style=\"display: none;\"><div class=\"progressbar\" id=\"emptyStar_progress\"></div></div>");
            out.println("                        </form>");
            out.println("                    </td></tr>");
            out.println("                </table>");
            out.println("            </fieldset>");
            out.println("</div>");


    }

    @Override
    public void doAdminHlp(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        final Percentage per = new Percentage();
        if (SWBResourceURL.Action_ADD.equals(paramRequest.getAction()))
        {
            try
            {
                boolean isMultipart = ServletFileUpload.isMultipartContent(request);
                HashMap<String,String> params = new HashMap<String,String>();
                // Create a factory for disk-based file items
                FileItemFactory factory = new DiskFileItemFactory(10*1024*1024,new File(SWBPlatform.getWorkPath()+"/tmp"));
                // Create a new file upload handler
                ServletFileUpload upload = new ServletFileUpload(factory);
                //Create a progress listener
                ProgressListener progressListener = new ProgressListener(){
                    private long kBytes = -1;
                public void update(long pBytesRead, long pContentLength, int pItems) {
                    long mBytes = pBytesRead / 10000;
                    if (kBytes == mBytes) {
                    return;
                    }
                    kBytes = mBytes;
                    int percent = (int)(pBytesRead * 100 / pContentLength);
                    per.setPercentage(percent);
                }
                };
                upload.setProgressListener(progressListener);
                // Parse the request
                List items = upload.parseRequest(request); /* FileItem */
                FileItem currentFile = null;
                // Process the uploaded items
                Iterator iter = items.iterator();
                while (iter.hasNext()) {
                    FileItem item = (FileItem) iter.next();

                    if (item.isFormField()) {
                        String name = item.getFieldName();
                        String value = item.getString();
                        params.put(name, value);
                    } else {
                        currentFile = item;
//                        String fieldName = item.getFieldName();
//                        String fileName = item.getName();
//                        String contentType = item.getContentType();
//                        boolean isInMemory = item.isInMemory();
//                        long sizeInBytes = item.getSize();
//                        File uploadedFile = new File();
//                        item.write(uploadedFile);
                    }
                }
                request.getSession(true).setAttribute(currentFile.getFieldName(), per);
                String path = SWBPlatform.getWorkPath()+getResourceBase().getWorkPath();
                File file = new File(path);
                if (!file.exists()) file.mkdirs();
                String name = currentFile.getFieldName()+currentFile.getName().substring(currentFile.getName().lastIndexOf("."));
                currentFile.write(new File(path+"/"+name));
                path = SWBPlatform.getWebWorkPath()+getResourceBase().getWorkPath();
                if (currentFile.getName().equals("fullStar"))
                {
                    setFullStar(path+name);
                    fullStarPath=getFullStar();
                }
                if (currentFile.getName().equals("halfStar"))
                {
                    setHalfStar(path+name);
                    halfStarPath=getHalfStar();
                }
                if (currentFile.getName().equals("emptyStar"))
                {
                    setEmptyStar(path+name);
                    emptyStarPath=getEmptyStar();
                }
                per.setPercentage(100);

            } catch (Exception ex)
            {
                log.error(ex);
            }

        }else if (SWBResourceURL.Action_EDIT.equals(paramRequest.getAction()))
        {
            Percentage pers = (Percentage) request.getSession(true).getAttribute(request.getParameter("sid"));
            PrintWriter out = response.getWriter();
            if (null!=pers)
            {
                out.println(pers.getPercentage());
            }
            else
            {
                out.println(0);
            }
        }
    }

 
    private class Percentage
    {
        int per = 0;
        public void setPercentage(int per)
        {
            this.per = per;
        }
        public int getPercentage()
        {
            return per;
        }
    }


}
