package org.semanticwb.portal.resources.sem;
//org.semanticwb.portal.resources.sem.SWBRankWebPage
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.*;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Resource;
import org.semanticwb.model.WebPage;
import org.semanticwb.model.WebSite;
import org.semanticwb.portal.api.*;
import org.semanticwb.portal.lib.WBResponse;

public class SWBRankWebPage extends org.semanticwb.portal.resources.sem.base.SWBRankWebPageBase
{

    private String fullStarPath;
    private String halfStarPath;
    private String emptyStarPath;

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

        PrintWriter out = response.getWriter();
        int rank = (int) Math.round(Math.floor(paramRequest.getTopic().getRank() * 10));
        //System.out.println("Rank:" + rank);
        SWBResourceURL url = paramRequest.getActionUrl();
        url.setAction("vote");
        url.setMode(SWBResourceURL.Mode_HELP);
        url.setCallMethod(SWBResourceURL.Call_DIRECT);
        out.println("\n<script language=\"javascript\" type=\"text/javascript\">\nvar request = false;\ntry {\n  request = new XMLHttpRequest();\n} " +
                "catch (trymicrosoft) {\n  try {\n    request = new ActiveXObject(\"Msxml2.XMLHTTP\");\n  } catch (othermicrosoft) {\n    try {\n " +
                "      request = new ActiveXObject(\"Microsoft.XMLHTTP\");\n    } catch (failed) {\n      request = false;\n    }\n  }\n}\nif " +
                "(!request)\n  alert(\"Error initializing XMLHttpRequest!\");\n\nfunction vote(val){\n    if (!invoke) return;\n    var url = \""+url+"?value=\"+escape(val);\n" +
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
        Cookie[] cookies= request.getCookies();
        for (Cookie cookie: cookies){
            //System.out.println(cookie.getName());
         if (cookie.getName().equals(response.getTopic().getId()+"_voted"))
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
        WebPage ws = response.getTopic();
        //System.out.println("ws: "+ws+" vote:"+vote);
        double rank = ws.getRank();
        long rev = ws.getReviews();
        rank = rank * rev;
        rev++;
        rank = rank + vote;
        rank = rank / rev;
        ws.setRank(rank);
        ws.setReviews(rev);
        //System.out.println("rev:"+rev+" rank:"+rank);
        }

    }

    @Override
    public void doHelp(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        Cookie cookie = new Cookie(paramRequest.getTopic().getId()+"_voted", "true");
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

    }



}
