package org.semanticwb.portal.community;

import java.io.IOException;
import java.io.Writer;
import java.text.SimpleDateFormat;
import javax.servlet.http.HttpServletRequest;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.GenericIterator;
import org.semanticwb.model.User;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;


public class MicroSiteElement extends org.semanticwb.portal.community.base.MicroSiteElementBase 
{
    public static int VIS_ALL=0;
    public static int VIS_MEMBERS_ONLY=1;
    public static int VIS_FRIENDS=2;
    public static int VIS_JUST_ME=3;

    private long views=0;
    private long timer;                     //valores de sincronizacion de views, hits
    private static long time;               //tiempo en milisegundos por cada actualizacion
    private boolean viewed = false;
    private final String fullStarPath = "/swbadmin/resources/ranking/fullstar.png";
    private final String halfStarPath = "/swbadmin/resources/ranking/halfstar.png";
    private final String emptyStarPath = "/swbadmin/resources/ranking/emptystar.png";

    static
    {
        time = 1000L * Long.parseLong((String) SWBPlatform.getEnv("swb/accessLogTime","600"));
    }



    public MicroSiteElement(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

//    /**
//     * return
//     * 0=no access
//     * 1=view
//     * 2=rank
//     * 3=edit
//     * @param ele
//     * @param mem
//     * @return
//     */
//    public static int getAccessLevel(Member mem)
//    {
//        return 3;
//        /*
//        int ret=0;
//        int vis=ele.getVisibility();
//        if(mem!=null)
//        if(vis==MicroSiteUtils.VIS_ALL)ret=true;
//        else if(vis==MicroSiteUtils.VIS_MEMBERS_ONLY && mem!=null)ret=true;
//        //TODO: Friends
//        else if(vis==MicroSiteUtils.VIS_JUST_ME && mem!=null)
//        {
//            if(ele.getCreator().equals(mem.getUser()))ret=true;
//        }
//        return ret;
//        */
//    }

    /**
     * Solo Ver el elemento
     * @param ele
     * @param mem
     * @return
     */
    public boolean canView(Member mem)
    {
        boolean ret=false;
        int vis=getVisibility();
        if(vis==VIS_ALL)ret=true;
        else if(vis==VIS_MEMBERS_ONLY && mem.canView())ret=true;
        else if(vis==VIS_JUST_ME && getCreator().equals(mem.getUser()))ret=true;
        return ret;
    }

    /**
     * Solo ver y agregar comentarios
     * @param ele
     * @param mem
     * @return
     */
    public boolean canComment(Member mem)
    {
        boolean ret=false;
        if(mem.canView())ret=true;
        return ret;
    }

    /**
     * Ver, Agregar comentarios y modificar (modificarr, eliminar) el elemento
     * @param ele
     * @param mem
     * @return
     */
    public boolean canModify(Member mem)
    {
        boolean ret=false;
        if(mem.getAccessLevel()>=mem.LEVEL_ADMIN)ret=true;
        else if(mem.getUser().equals(getCreator()))ret=true;
        return ret;
    }

    @Override
    public long getViews()
    {
        if(views==0)views=super.getViews();
        return views;
    }

    public boolean incViews()
    {
        //System.out.println("incViews:"+views);
        viewed = true;
        if(views==0)views=getViews();
        views+=1;
        long t = System.currentTimeMillis() - timer;
        if (t > time || t < -time)
        {
            //TODO: evalDate4Views();
            return true;
        }
        return false;
    }

    @Override
    public void setViews(long views)
    {
        //System.out.println("setViews:"+views);
        super.setViews(views);
        this.views=views;
    }

    public void updateViews()
    {
        //System.out.println("updateViews:"+views);
        if(viewed)
        {
            timer = System.currentTimeMillis();
            if(views>0)setViews(views);
            viewed = false;
        }
    }

    public void renderGenericElements(HttpServletRequest request,
            Writer out, SWBParamRequest paramRequest)
            throws SWBResourceException, IOException {

        String suri = request.getParameter("uri");
        StringBuilder sb = new StringBuilder(500);
        String tmpUrl = "";
        String abusedDesc = this.isAbused() ? "Inapropiado" : "Apropiado";
        int rank = 0;

        if (suri == null && this != null) {
            suri = this.getURI();
        }
        tmpUrl = "uri=\"+escape('" + suri + "')";
        rank = (int) Math.round(Math.floor(this.getRank() * 10));  //this.getRank() * 10
        SWBResourceURL url = paramRequest.getActionUrl();
        url.setAction("vote");
        url.setMode(paramRequest.getMode());
        url.setCallMethod(SWBResourceURL.Call_DIRECT);
        sb.append("\n<script type=\"text/javascript\" src=\""
                + SWBPlatform.getContextPath()
                + "/swbadmin/js/dojo/dojo/dojo.js\" djConfig=\"parseOnLoad: true, isDebug: false\"></script>");

        sb.append("\n<link rel='stylesheet' type='text/css' media='all' href='"
                + SWBPlatform.getContextPath()
                + "/swbadmin/js/dojo/dojox/form/resources/Rating.css' />");
        sb.append("\n<script type=\"text/javascript\">");
        sb.append("\n  dojo.require(\"dojo.parser\"); // scan page for widgets and instantiate them");
        sb.append("\n  dojo.require(\"dojox.form.Rating\"); // scan page for widgets and instantiate them");
        sb.append("\n</script>");

        sb.append("\n<script language=\"javascript\" type=\"text/javascript\">");
        sb.append("  //dojo.require(\"dojo.parser\");");
        sb.append("\nvar request = false;");
        sb.append("\ntry {");
        sb.append("\n  request = new XMLHttpRequest();");
        sb.append("\n} " +
                "catch (trymicrosoft) {");
        sb.append("\n  try {");
        sb.append("\n    request = new ActiveXObject(\"Msxml2.XMLHTTP\");");
        sb.append("\n  } catch (othermicrosoft) {");
        sb.append("\n    try {");
        sb.append("\n      request = new ActiveXObject(\"Microsoft.XMLHTTP\");");
        sb.append("\n    } catch (failed) {");
        sb.append("\n      request = false;");
        sb.append("\n    }");
        sb.append("\n  }");
        sb.append("\n}");
        sb.append("\nif (!request)");
        sb.append("\n  alert(\"Error al inicializar XMLHttpRequest!\");");
        sb.append("\nvar invoke = true;");
        sb.append("\nvar count = 0;");
        sb.append("\n\nfunction vote(val) {");
        sb.append("\n    if (!invoke) return;");
        sb.append("\n    var url = \"" + url + "?act=vote&value=\"+escape(val)+\"&" + tmpUrl + ";\n" +
                "    request.open(\"GET\", url, true);");
        sb.append("\n    request.onreadystatechange = ranked;");
        sb.append("\n    request.send(null);");
        sb.append("\n}");
        sb.append("\n\nfunction ranked() {");
        sb.append("\n    var response = request.responseText;");
        sb.append("\n    if (count == 0) {");
        sb.append("\n      if ('Not OK'!=response) {");
        sb.append("\n          alert('Calificación contabilizada, muchas gracias por tu opinión!');");
        sb.append("\n          invoke = false;");
        sb.append("\n      } else {");
        sb.append("\n          alert('Lo sentimos, ha ocurrido un problema al contabilizar la calificación!');");
        sb.append("\n      } ");
        sb.append("\n      count++;");
        sb.append("\n    } ");
        sb.append("\n}");

        url.setAction("abuseReport");
        sb.append("\nvar invokeAbused = true;");
        sb.append("\n\nfunction changeAbusedState() {");
        sb.append("\n    if (!invokeAbused) return;");
        sb.append("\n    var url = \"" + url + "?act=abuseReport&" + tmpUrl + ";\n" +
                "    request.open(\"GET\", url, true);");
        sb.append("\n    request.onreadystatechange = abusedStateChanged;");
        sb.append("\n    request.send(null);");
        sb.append("\n}");
        sb.append("\n\nfunction abusedStateChanged() {");
        sb.append("\n  if(request.readyState!=4) return;");
        sb.append("\n  if(request.status==200) {");
        sb.append("\n    var response = request.responseText;");
        sb.append("\n    if ('' != response && 'Not OK' != response) {");
        sb.append("\n      var etiqueta = document.getElementById(\"abused\").innerHTML;");
        sb.append("\n      //alert('response:'+response+', etiqueta:'+etiqueta);");
        sb.append("\n      if (response == 'true') {");
        sb.append("\n        document.getElementById(\"abused\").innerHTML = 'Inapropiado';");
        sb.append("\n      } else {");
        sb.append("\n        document.getElementById(\"abused\").innerHTML = 'Apropiado';");
        sb.append("\n      } ");
//        sb.append("\n      invokeAbused = false;");
        sb.append("\n    }");
        sb.append("\n  }");
        sb.append("\n}");
        sb.append("\n\nfunction addComment() {");
/*        sb.append("\n    if (document.getElementById(\"addComment\").style.visibility==\"visible\") {");
        sb.append("\n      document.getElementById(\"addComment\").style.visibility=\"hidden\";");
        sb.append("\n    } else {");*/
        sb.append("\n      document.getElementById(\"addComment\").style.display=\"inline\";");
/*        sb.append("\n    }");*/
        sb.append("\n}");

        sb.append("\nvar invokeSpam = true;");
        sb.append("\nvar spamId = 0;");
        sb.append("\n\nfunction spam(commentId) {");
        sb.append("\n    spamId = commentId;");
        sb.append("\n    if (!invokeSpam) return;");
        sb.append("\n    var url = \"" + url + "?act=spamReport&commentId=\"+commentId+\"&" + tmpUrl + ";\n" +
                "    request.open(\"GET\", url, true);");
        sb.append("\n    request.onreadystatechange = spamStateChanged;");
        sb.append("\n    request.send(null);");
        sb.append("\n}");
        sb.append("\n\nfunction spamStateChanged() {");
        sb.append("\n  if(request.readyState!=4) return;");
        sb.append("\n  if(request.status==200) {");
        sb.append("\n    var response = request.responseText;");
        sb.append("\n    if ('' != response && 'Not OK' != response) {");
        sb.append("\n      var etiqueta = document.getElementById(\"spamMark\"+spamId).innerHTML;");
        sb.append("\n      //alert('response:'+response+', comentario:'+spamId+', etiqueta act:'+etiqueta);");
        sb.append("\n      if (response == 'false') {");
        sb.append("\n        document.getElementById(\"spamMark\"+spamId).innerHTML = 'Marcar como spam';");
        sb.append("\n      } else {");
        sb.append("\n        document.getElementById(\"spamMark\"+spamId).innerHTML = 'Es spam';");
        sb.append("\n      } ");
        sb.append("\n      invokeSpam = false;");
        sb.append("\n    }");
        sb.append("\n  }");
        sb.append("\n  invokeSpam = true;");
        sb.append("\n}");


        sb.append("\n</script>\n");

        sb.append("\n<div>");
        sb.append("\n  <span style=\"float:left; width:200px;\">");
//        sb.append("\n    <table boder=\"0\" cellpadding=\"0\" cellspacing=\"0\"><tr>");
//        sb.append("\n      <td>Calificar:</td>");
/*        for (int i = 1; i <= 5; i++) {
            sb.append(printStar(i, rank));
        }*/
        sb.append("\n      <div>Calificar:</div>");
        sb.append("\n      <div dojoType=\"dojox.form.Rating\" numStars=\"5\" value=\"" + rank + "\">"
                + "<script type=\"dojo/event\" event=\"onChange\">vote(this.value);/*alert(\"valor:\"+this.value);dojo.query('#rating1Value')[0].innerHTML = this.value;*/</script></div>");
//        sb.append("\n       Rating:<div dojoType=\"dojox.form.Rating\" numStars=\"5\" value=\"" + rank + "\">"
//                + "<script type=\"dojo/event\" event=\"onChange\">alert(\"valor:\"+this.value);vote(this.value);/*dojo.query('#rating1Value')[0].innerHTML = this.value;*/</script></div>");
        //sb.append("\n      </tr>\n    </table>");
        sb.append("\n  </span>");
        sb.append("\n  <div style=\"float:left; width:200px;\">" + this.getReviews() + " calificaciones</div>");
        sb.append("\n  <span style=\"float:left\"><a href=\"javascript:changeAbusedState();\">P&uacute;blicamente</a> <span id=\"abused\">"
                + abusedDesc + "</span></span>");
        sb.append("\n</div><br/><br/>");
        sb.append("\n<div>");
        sb.append("\n  <span style=\"float:left; width:300px;\">Comentarios</span>");
        //sb.append("\n  <div>&nbsp;</div>");
        url.setAction("addComment");
        url.setCallMethod(SWBResourceURL.Call_CONTENT);
        sb.append("\n  <span style=\"float:left; width:300px;\"><a href=\"javascript:addComment();\">Escribir comentario</a></span>");
        sb.append("\n</div><br/><br/>");
        sb.append("\n<div id=\"addComment\" style=\"display:none\">");
        sb.append("\n  <br/>Comentario");
        sb.append("\n  <form name=\"addCommentForm\" action=\"" + url + "\">");
        sb.append("\n    <input type=\"hidden\" name=\"uri\" value=\"" + suri + "\">");
        sb.append("\n    <input type=\"hidden\" name=\"act\" value=\"addComment\">");
        sb.append("\n    <textarea name=\"comentario\" cols=\"40\" rows=\"\"></textarea>");
        sb.append("\n    <input type=\"submit\" value=\"Publicar comentario\">");
        sb.append("\n  </form>");
        sb.append("\n</div>");
        sb.append(renderListComments(this, paramRequest.getUser()));

        out.write(sb.toString());
    }

    /**
     * Genera listado de comentarios asociados al MicroSiteElement
     * @param paramRequest
     * @param uri
     * @return
     * @throws org.semanticwb.portal.api.SWBResourceException
     */
    private String renderListComments(MicroSiteElement mse, User user) {

        StringBuilder ret = new StringBuilder(200);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy | HH:mm");
        int ordinal = 1;

        GenericIterator<Comment> iterator = mse.listComments();
        ret.append("<div>\n");
        ret.append("\n  <div id=\"commentsList\">");
        while (iterator.hasNext()) {
            Comment comment = iterator.next();
            String spamMark = (comment.isSpam() ? "Es spam" : "Marcar como spam");
            ret.append("\n    <div id=\"comment" + comment.getId() + "\" class=\"comment-entry\">");
            ret.append("\n      <div class=\"comment-head\">");
            ret.append("\n        <div class=\"comment-info\">");
            ret.append("\n         " + (ordinal++) + ". <strong>"
                    + (comment.getCreator().getLogin().equalsIgnoreCase("")
                       ? "Desconocido"
                       : comment.getCreator().getLogin())
                    + "</strong></div>");
            ret.append("          <div class=\"comment-time\"> ("
                    + SWBUtils.TEXT.getTimeAgo(comment.getCreated(), user.getLanguage()) + ")</div>");
            ret.append("          <span class=\"comment-spam\"><a href=\"javascript:spam("
                    + comment.getId() + ");\" id=\"spamMark"+ comment.getId() + "\">" + spamMark + "</a></spam>");
            ret.append("\n        <div class=\"clearL\"></div>");
            ret.append("\n      </div>");
            ret.append("\n      <div id=\"comment_body_" + comment.getId() + "\">");
            ret.append("\n        <div class=\"comment-body\">");
            ret.append("\n          <div>" + comment.getDescription() + "</div>");
            ret.append("\n        </div>");
            ret.append("\n        <div id=\"div_comment_form_id_" + comment.getId() + "\"></div>");
            ret.append("\n      </div>");
            ret.append("\n    </div>");
        }
        ret.append("\n  </div>\n<div>\n");
        return ret.toString();
    }

    private String printStar(int current, int rank) {

        String url = "javascript:vote(" + current + ");";
        int midl = (current * 10) - 7;
        int midt = (current * 10) - 2;
        String imgRank = emptyStarPath;
        if (rank >= midl && rank <= midt) {
            imgRank = halfStarPath;
        }
        if (rank > midt) {
            imgRank = fullStarPath;
        }
        return ("<td><a href=\"" + url + "\" title=\"Asigna " + current
                + " estrella" + (current > 1 ? "s" : "") + "\"><img border=\"0\" src=\""
                + SWBPlatform.getContextPath() + imgRank + "\" alt=\"has "
                + ((0.0f + rank) / 10.0f) + " star(s)\"/></a></td>");

    }

}
