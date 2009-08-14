package org.semanticwb.portal.community;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import javax.servlet.http.HttpServletRequest;
import org.semanticwb.SWBPlatform;
import org.semanticwb.model.GenericIterator;
import org.semanticwb.platform.SemanticObject;
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
            PrintWriter out, MicroSiteElement mse, SWBParamRequest paramRequest)
            throws SWBResourceException {

        String suri = request.getParameter("uri");
        StringBuilder sb = new StringBuilder(500);
        String tmpUrl = "";
        String abusedDesc = mse.isAbused() ? "Inapropiado" : "Apropiado";
        int rank = 0;

        if (suri == null && mse != null) {
            suri = mse.getURI();
        }
        tmpUrl = "uri=\"+escape('" + suri + "')";
        rank = (int) Math.round(Math.floor(mse.getRank() * 10));
        SWBResourceURL url = paramRequest.getActionUrl();
        url.setAction("vote");
        url.setMode(paramRequest.getMode());
        url.setCallMethod(SWBResourceURL.Call_DIRECT);
        out.println("\n<script language=\"javascript\" type=\"text/javascript\">");
        out.println("\nvar request = false;");
        out.println("\ntry {");
        out.println("\n  request = new XMLHttpRequest();");
        out.println("\n} " +
                "catch (trymicrosoft) {");
        out.println("\n  try {");
        out.println("\n    request = new ActiveXObject(\"Msxml2.XMLHTTP\");");
        out.println("\n  } catch (othermicrosoft) {");
        out.println("\n    try {");
        out.println("\n      request = new ActiveXObject(\"Microsoft.XMLHTTP\");");
        out.println("\n    } catch (failed) {");
        out.println("\n      request = false;");
        out.println("\n    }");
        out.println("\n  }");
        out.println("\n}");
        out.println("\nif (!request)");
        out.println("\n  alert(\"Error al inicializar XMLHttpRequest!\");");
        out.println("\n\nfunction vote(val) {");
        out.println("\n    if (!invoke) return;");
        out.println("\n    var url = \"" + url + "?value=\"+escape(val)+\"&" + tmpUrl + ";\n" +
                "    request.open(\"GET\", url, true);");
        out.println("\n    request.onreadystatechange = votedPage;");
        out.println("\n    request.send(null);");
        out.println("\n}");
        out.println("\n\nfunction votedPage() {");
        out.println("\n    var response = request.responseText;");
        out.println("\n    if ('OK'==response)");
        out.println("\n        alert('Voto contabilizado, muchas gracias por tu opinión!');");
        out.println("\n    invoke = false;");
        out.println("\n}");
        out.println("\nvar invoke = true;");
        url.setAction("abuseReport");
        out.println("\nvar invokeAbused = true;");

        out.println("\n\nfunction changeAbusedState() {");
        out.println("\n    if (!invoke) return;");
        out.println("\n    var url = \"" + url + "?" + tmpUrl + ";\n" +
                "    request.open(\"GET\", url, true);");
        out.println("\n    request.onreadystatechange = abuseStateChanged;");
        out.println("\n    request.send(null);");
        out.println("\n}");
        out.println("\n\nfunction abuseStateChanged() {");
        out.println("\n    var response = request.responseText;");
        out.println("\n    if ('OK' == response) {");
        out.println("\n      var etiqueta = document.getElementById(\"abused\").innerHTML;");
        out.println("\n      if (etiqueta == 'Apropiado') {");
        out.println("\n        document.getElementById(\"abused\").innerHTML == 'Inapropiado'");
        out.println("\n      } else {");
        out.println("\n        document.getElementById(\"abused\").innerHTML == 'Apropiado'");
        out.println("\n      } ");
        out.println("\n      invoke = false;");
        out.println("\n    }");
        out.println("\n}");
        out.println("\n\nfunction addComment() {");
/*        out.println("\n    if (document.getElementById(\"addComment\").style.visibility==\"visible\") {");
        out.println("\n      document.getElementById(\"addComment\").style.visibility=\"hidden\";");
        out.println("\n    } else {");*/
        out.println("\n      document.getElementById(\"addComment\").style.display=\"inline\";");
/*        out.println("\n    }");*/
        out.println("\n}");
        out.println("\n</script>\n");

        //TODO: Cambiar los out.println de arriba por sb.append

        sb.append("\n<div>");
        sb.append("\n  <span>Calificar");
        sb.append("\n    <table boder=\"0\" cellpadding=\"0\" cellspacing=\"0\"><tr>");
        for (int i = 1; i <= 5; i++) {
            sb.append(printStar(i, rank));
        }
        sb.append("\n      </tr>\n    </table>");
        sb.append("\n  </span>");
        sb.append("\n  <div>" + mse.getReviews() + " calificaciones</div>");
        sb.append("\n  <span><a href=\"javascript:changeAbusedState();\">P&uacute;blicamente</a> <span id=\"abused\">"
                + abusedDesc + "</span></span>");
        sb.append("\n</div>");
        sb.append("\n<div>");
        sb.append("\n  <span>Comentarios</span>");
        sb.append("\n  <div>&nbsp;</div>");
        url.setAction("addComment");
        sb.append("\n  <span><a href=\"javascript:addComment();\">Publicar comentario</a></span>");
        sb.append("\n</div>");
        sb.append("\n<div id=\"addComment\" style=\"display:none\">");
        sb.append("\n  <br/>Comentario");
        sb.append("\n  <form name=\"addCommentForm\" action=\"" + url + "\">");
        sb.append("\n    <input type=\"hidden\" name=\"uri\" value=\"" + suri + "\">");
        sb.append("\n    <textarea name=\"comentario\" cols=\"40\" rows=\"\"></textarea>");
        sb.append("\n    <input type=\"submit\" value=\"Publicar comentario\">");
        sb.append("\n  </form>");
        sb.append("\n</div>");
        sb.append(renderListComments(mse));

        out.println(sb.toString());
    }

    /**
     * Genera listado de comentarios asociados al MicroSiteElement
     * @param paramRequest
     * @param uri
     * @return
     * @throws org.semanticwb.portal.api.SWBResourceException
     */
    private String renderListComments(MicroSiteElement mse) {

        StringBuilder ret = new StringBuilder(200);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy | HH:mm");
        int ordinal = 1;

        GenericIterator<Comment> iterator = mse.listComments();
        ret.append("<div><table>\n");
        while (iterator.hasNext()) {
            Comment comment = iterator.next();
            ret.append("<tr>\n");
            ret.append("  <td>" + (ordinal++) + ". <strong>"
                    + (comment.getCreator().getLogin().equalsIgnoreCase("")
                       ? "Desconocido"
                       : comment.getCreator().getLogin())
                    + "</strong> (" + sdf.format(comment.getCreated())
                    + ")<br/><div class\"cmnttxt\">" + comment.getDescription()
                    + "</div></td>\n");
            ret.append("</tr>\n");
        }
        ret.append("</table><div>\n");
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
