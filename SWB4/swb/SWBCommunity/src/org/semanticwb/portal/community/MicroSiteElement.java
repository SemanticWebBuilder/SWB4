package org.semanticwb.portal.community;

import java.io.IOException;
import java.io.Writer;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.GenericIterator;
import org.semanticwb.model.SWBComparator;
import org.semanticwb.model.Traceable;
import org.semanticwb.model.WebPage;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;


public class MicroSiteElement 
        extends org.semanticwb.portal.community.base.MicroSiteElementBase {
    
    
    public static int VIS_ALL=0;
    public static int VIS_MEMBERS_ONLY=1;
    public static int VIS_FRIENDS=2;
    public static int VIS_JUST_ME=3;

    private long views=0;
    private long timer;                     //valores de sincronizacion de views, hits
    private static long time;               //tiempo en milisegundos por cada actualizacion
    private boolean viewed = false;
//    private final String fullStarPath = "/swbadmin/resources/ranking/fullstar.png";
//    private final String halfStarPath = "/swbadmin/resources/ranking/halfstar.png";
//    private final String emptyStarPath = "/swbadmin/resources/ranking/emptystar.png";
    private static final int COMMENTS_IN_PAGE = 5;
    private static final int PAGE_INDEXES_TO_SHOW = 5;

    
    static {
        time = 1000L * Long.parseLong(
                (String) SWBPlatform.getEnv("swb/accessLogTime", "600"));
    }

    public MicroSiteElement(org.semanticwb.platform.SemanticObject base) {
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
     * Indica si el <code>Member</code> recibido puede ver el elemento
     * @param ele
     * @param mem
     * @return <code>true</code> si el miembro tiene derecho a ver el elemento, <code>false</code>
     * de lo contrario.
     */
    public boolean canView(Member mem) {
        
        boolean ret = false;
        int vis = getVisibility();
        if (vis == VIS_ALL) {
            ret = true;
        } else if (vis == VIS_MEMBERS_ONLY && mem.canView()) {
            ret = true;
        } else if (vis == VIS_JUST_ME && getCreator().equals(mem.getUser())) {
            ret = true;
        }
        return ret;
    }

    /**
     * Indica si el <code>Member</code> recibido puede ver y agregar comentarios
     * @param ele
     * @param mem
     * @return <code>true</code> si el miembro tiene derecho a ver y agregar el elemento, 
     * <code>false</code> de lo contrario.
     */
    public boolean canComment(Member mem) {
        
        boolean ret = false;
        if (mem.canView()) {
            ret = true;
        }
        return ret;
    }

    /**
     * Indica si el <code>Member</code> recibido puede ver, agregar comentarios 
     * y modificar (modificar o eliminar) el elemento
     * @param ele
     * @param mem
     * @return <code>true</code> si el miembro tiene derecho a ver, agregar comentarios 
     * y modificar (modificar o eliminar) el elemento
     */
    public boolean canModify(Member mem) {
        
        boolean ret = false;
        if (mem.getAccessLevel() >= Member.LEVEL_ADMIN) {
            ret = true;
        } else if (mem.getUser().equals(getCreator())) {
            ret = true;
        }
        return ret;
    }

    /**
     * Obtiene el n&uacute;mero de veces que este elemento ha sido desplegado.
     * @return el n&uacute;mero de veces que este elemento ha sido desplegado.
     */
    @Override
    public long getViews() {
        
        if (views == 0) {
            views = super.getViews();
        }
        return views;
    }

    /**
     * Incrementa en uno, el n&uacute;mero de veces que ha sido desplegado este elemento.
     * @return <code>true</code> si el elemento fue desplegado por última vez hace <code>time</code>
     * milisegundos; <code>false</code> de lo contrario.
     */
    public boolean incViews() {
        
        //System.out.println("incViews:"+views);
        viewed = true;
        if (views == 0) {
            views = getViews();
        }
        views += 1;
        long t = System.currentTimeMillis() - timer;
        if (t > time || t < -time) {
            //TODO: evalDate4Views();
            return true;
        }
        return false;
    }

    /**
     * Fija el valor de las veces que se ha mostrado este elemento.
     * @param views n&uacute;mero de veces que se ha mostrado el elemento.
     */
    @Override
    public void setViews(long views) {

        //System.out.println("setViews:"+views);
        super.setViews(views);
        this.views = views;
    }

    /**
     * Actualiza el estado del n&uacute;mero de veces que se ha mostrado el elemento. En
     * base al estado de <code>viewed</code>
     */
    public void updateViews() {

        //System.out.println("updateViews:"+views);
        if (viewed) {
            timer = System.currentTimeMillis();
            if (views > 0) {
                setViews(views);
            }
            viewed = false;
        }
    }

    /**
     * Muestra la información utilizada por las herramientas o recursos generales para
     * los elementos de comunidades, como calificar y reporte de abuso sobre el elemento
     * o comentarios relacionados al mismo.
     * @param request petición del usuario a atender
     * @param out para agregar los datos a desplegar
     * @param paramRequest para obtener referencias a objetos internos
     * @throws org.semanticwb.portal.api.SWBResourceException
     * @throws java.io.IOException
     */
    public void renderGenericElements(HttpServletRequest request,
            Writer out, SWBParamRequest paramRequest)
            throws SWBResourceException, IOException {

        WebPage page = paramRequest.getWebPage();
        Member mem = Member.getMember(paramRequest.getUser(), page);
        String suri = request.getParameter("uri");
        StringBuilder sb = new StringBuilder(500);
        String tmpUrl = "";
        String abusedDesc = this.isAbused() ? "Inapropiado" : "Apropiado";
        int rank = 0;
        long pageNumber = 0;
        boolean showComments = false;

        try {
            pageNumber = Long.parseLong(request.getParameter("pn"));
            showComments = true;
        } catch (Exception e) {
            pageNumber = 1;
        }

        if (suri == null && this != null) {
            suri = this.getURI();
        }
        tmpUrl = "uri=\"+escape('" + suri + "')";
        rank = (int) Math.round(Math.floor(this.getRank()));
        SWBResourceURL url = paramRequest.getActionUrl();
        url.setAction("vote");
        url.setMode(paramRequest.getMode());
        url.setCallMethod(SWBResourceURL.Call_DIRECT);
        sb.append("\n<link rel='stylesheet' type='text/css' href='"
                + SWBPlatform.getContextPath()
                + "/swbadmin/jsp/microsite/css/ciudad_digital.css' />");
        sb.append("\n<script type=\"text/javascript\" src=\""
                + SWBPlatform.getContextPath()
                + "/swbadmin/js/dojo/dojo/dojo.js\" djConfig=\"parseOnLoad: true, isDebug: false\"></script>");

        sb.append("\n<link rel='stylesheet' type='text/css' media='all' href='"
                + SWBPlatform.getContextPath()
                + "/swbadmin/js/dojo/dojox/form/resources/Rating.css' />");
        sb.append("\n<script type=\"text/javascript\">");
         // scan page for widgets and instantiate them
        sb.append("\n  dojo.require(\"dojo.parser\");");
        sb.append("\n  dojo.require(\"dojox.form.Rating\");");
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
        sb.append("\n    //alert('En funcion para votar');");
        sb.append("\n    var url = \"" + url + "?act=vote&value=\"+escape(val)+\"&" + tmpUrl + ";");
        sb.append("\n    request.open(\"GET\", url, true);");
        sb.append("\n    request.onreadystatechange = ranked;");
        sb.append("\n    request.send(null);");
        sb.append("\n}");
        sb.append("\n\nfunction ranked() {");
        sb.append("\n  var response = request.responseText;");
        sb.append("\n  if (count == 0) {");
        sb.append("\n    if(request.readyState!=4) return;");
        sb.append("\n    if(request.status==200) {");
        sb.append("\n      if ('Not OK'!=response && ''!=response) {");
        sb.append("\n          var ranking = Math.floor(response.split('|')[0]);");
        sb.append("\n          var votes = response.split('|')[1];");
        sb.append("\n          document.getElementById(\"reviews\").innerHTML = votes;");
        sb.append("\n          invoke = false;");
        sb.append("\n      } else {");
        sb.append("\n          alert('Lo sentimos, ha ocurrido un problema al contabilizar la calificación!');");
        sb.append("\n      } ");
        sb.append("\n      count++;");
        sb.append("\n    } ");
        sb.append("\n  } ");
        sb.append("\n}");

        url.setAction("abuseReport");
        sb.append("\nvar invokeAbused = true;");
        sb.append("\n\nfunction changeAbusedState() {");
        sb.append("\n    if (!invokeAbused) return;");
        sb.append("\n    var url = \"" + url + "?act=abuseReport&" + tmpUrl + ";" +
                  "\n    request.open(\"GET\", url, true);");
        sb.append("\n    request.onreadystatechange = abusedStateChanged;");
        sb.append("\n    request.send(null);");
        sb.append("\n}");
        sb.append("\n\nfunction abusedStateChanged() {");
        sb.append("\n  if (request.readyState != 4) return;");
        sb.append("\n  if (request.status == 200) {");
        sb.append("\n    var response = request.responseText;");
        sb.append("\n    if ('' != response && 'Not OK' != response) {");
        sb.append("\n      var etiqueta = document.getElementById(\"abused\").innerHTML;");
//        sb.append("\n      alert('response:'+response+', etiqueta:'+etiqueta);");
        sb.append("\n      if (response == 'true') {");
        sb.append("\n        document.getElementById(\"abused\").innerHTML = 'Inapropiado';");
        sb.append("\n      } else {");
        sb.append("\n        document.getElementById(\"abused\").innerHTML = 'Apropiado';");
        sb.append("\n      } ");
        sb.append("\n      invokeAbused = false;");
        sb.append("\n    }");
        sb.append("\n  }");
        sb.append("\n}");
        sb.append("\n\nfunction addComment() {");
        sb.append("\n    document.getElementById(\"addComment\").style.display=\"inline\";");
        sb.append("\n}");
        sb.append("\nfunction showComments() {");
        sb.append("\n    var x = document.getElementById(\"commentsList\").style.display;");
        sb.append("\n    if (x == 'none') {");
        sb.append("\n      document.getElementById(\"commentsList\").style.display=\"inline\";");
        sb.append("\n      document.getElementById(\"ctrlComments\").innerHTML=\"[-]\";");
        sb.append("\n    } else {");
        sb.append("\n      document.getElementById(\"commentsList\").style.display=\"none\";");
        sb.append("\n      document.getElementById(\"ctrlComments\").innerHTML=\"[+]\";");
        sb.append("\n    }");
        sb.append("\n}");

        sb.append("\n\nvar invokeSpam = true;");
        sb.append("\nvar spamId = 0;");
        sb.append("\n\nfunction spam(commentId) {");
        sb.append("\n    spamId = commentId;");
        sb.append("\n    if (!invokeSpam) return;");
        sb.append("\n    var url = \"" + url + "?act=spamReport&commentId=\"+commentId+\"&" + tmpUrl + ";" +
                  "\n    request.open(\"GET\", url, true);");
        sb.append("\n    request.onreadystatechange = spamStateChanged;");
        sb.append("\n    request.send(null);");
        sb.append("\n}");
        sb.append("\n\nfunction spamStateChanged() {");
        sb.append("\n  if (request.readyState != 4) return;");
        sb.append("\n  if (request.status == 200) {");
        sb.append("\n    var response = request.responseText;");
        sb.append("\n    if ('' != response && 'Not OK' != response) {");
        sb.append("\n      var etiqueta = document.getElementById(\"spamMark\"+spamId).innerHTML;");
//        sb.append("\n      alert('response:'+response+', comentario:'+spamId+', etiqueta act:'+etiqueta);");
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

        sb.append("\n<div class=\"common_funcs\">");
        sb.append("\n  <span style=\"float:left; width:200px;\">");
        sb.append("\n      <div class=\"rank_label\">Calificar:</div>");
        if (mem.canView()) {
            sb.append("\n      <div class=\"rank_stars\" dojoType=\"dojox.form.Rating\" numStars=\"5\" value=\"" + rank + "\">"
                    + "\n        <script type=\"dojo/event\" event=\"onChange\">vote(this.value);</script></div>");
        } else {
            sb.append("\n      <div class=\"rank_stars\" dojoType=\"dojox.form.Rating\" numStars=\"5\" value=\"" + rank + "\">"
                    + "\n        <script type=\"dojo/event\" event=\"_onMouse\">return;</script>"
                    + "\n        <script type=\"dojo/event\" event=\"onStarClick\">return;</script></div>");
        }
        sb.append("\n  </span>");
        sb.append("\n  <div class=\"rec_votes\">");
        sb.append("\n    <div class=\"rec_votes_num\" id=\"reviews\">" + this.getReviews() + "</div>");
        sb.append("\n    <div class=\"rec_votes_label\"> votos</div>");
        sb.append("\n  </div>");
        if (mem.canView()) {
            sb.append("\n  <span class=\"abused\"><a href=\"javascript:changeAbusedState();\">P&uacute;blicamente</a>");
        } else {
            sb.append("\n  <span class=\"abused\">P&uacute;blicamente");
        }
        sb.append("\n     <span id=\"abused\">" + abusedDesc + "</span></span>");
        sb.append("\n</div><br/><br/>");
        sb.append("\n<div class=\"comments_head\">");
        sb.append("\n  <span class=\"comments_title_link\">"
                + "<a href=\"javascript:showComments();\" id=\"ctrlComments\">"
                + "[-]</a></span>");
        sb.append("\n  <span class=\"comments_title\">Comentarios</span>");
        //sb.append("\n  <div>&nbsp;</div>");
        url.setAction("addComment");
        url.setCallMethod(SWBResourceURL.Call_CONTENT);
        if (mem.canView()) {
            sb.append("\n  <span class=\"comments_write\"><a href=\"javascript:addComment();\">Escribir comentario</a></span>");
        } else {
            sb.append("\n  <span class=\"comments_write\">&nbsp;</span>");
        }
        sb.append("\n</div>");
        if (mem.canView()) {
            sb.append("\n<div id=\"addComment\">");
            sb.append("\n  <br/>Comentario");
            sb.append("\n  <form name=\"addCommentForm\" action=\"" + url + "\">");
            sb.append("\n    <input type=\"hidden\" name=\"uri\" value=\"" + suri + "\">");
            sb.append("\n    <input type=\"hidden\" name=\"act\" value=\"addComment\">");
            sb.append("\n    <textarea name=\"comentario\" cols=\"40\" rows=\"\"></textarea>");
            sb.append("\n    <input type=\"submit\" value=\"Publicar comentario\">");
            sb.append("\n  </form>");
            sb.append("\n</div>");
        }
        sb.append("\n<div class=\"clearL\"></div>");
        try {
        sb.append(renderListComments(this, mem, pageNumber,
                  paramRequest.getRenderUrl(), suri));
        } catch (Exception e) {
            e.printStackTrace();
        }
        out.write(sb.toString());
    }

    /**
     * Genera listado de comentarios asociados a este <code>MicroSiteElement</code>
     * @param mse elemento al que est&aacute;n asociados los comentarios
     * @param mem <code>Member</code> que realiza la consulta
     * @param page n&uacute;mero de la p&aacute;gina de comentarios a desplegar
     * @param url la utilizada en las ligas del paginado de comentarios
     * @param uri correspondiente al elemento de micrositio en cuesti&oacute;n
     * @param showComments indica si mostrar el listado de comentarios al cargar la p&aacute;gina
     * @return los datos de los comentarios correspondientes a la p&aacute;gina seleccionada
     */
    private String renderListComments(MicroSiteElement mse, Member mem,
            long page, SWBResourceURL url, String uri) {

        //System.out.println("En renderListComments");
        StringBuilder ret = new StringBuilder(800);
        long totalPages = totalPagesNumber(mse);

        ret.append("\n<table width=\"450\"><tr><td>");
        ret.append("\n<div>");
        ret.append("\n  <div id=\"commentsList\">");
        ret.append(getCommentsByPage(mse, page, mem));

        if (totalPages > 1) {
            ret.append("<div class=\"clearL\"></div>");
            ret.append("\n    <div id=\"commentsIndex\">");
            ret.append("\n      <div class=\"commentsIndexContainer\">");
            url.setCallMethod(SWBResourceURL.Call_CONTENT);
            url.setParameter("act", "detail");
            //TODO: colocar el uri codificado para que sea un parametro valido
            url.setParameter("uri", uri);
            if (page > 1) {
                ret.append("<span class=\"commentPageLink\"><a href=\""
                        + url.toString() + "&pn=" + (page - 1)
                        + "\" title=\"P&aacute;gina anterior\">&lt;&lt;</a></span>");
            }
            long ini = 1L;
            long fin = MicroSiteElement.PAGE_INDEXES_TO_SHOW;
            long dif = 0;
            if ((totalPages < MicroSiteElement.PAGE_INDEXES_TO_SHOW)) {
                fin = totalPages;
            }
            if (totalPages > MicroSiteElement.PAGE_INDEXES_TO_SHOW && page > 1) {
                dif = page - 1;
                if (totalPages >= (MicroSiteElement.PAGE_INDEXES_TO_SHOW + dif)) {
                    fin = MicroSiteElement.PAGE_INDEXES_TO_SHOW + dif;
                    ini = 1 + dif;
                } else {
                    fin = totalPages;
                    ini = totalPages - MicroSiteElement.PAGE_INDEXES_TO_SHOW + 1;
                }
            }

            for (long i = ini; i <= fin; i++) {
                if (i != page) {
                    ret.append("<span class=\"commentPageLink\"><a href=\""
                            + url.toString() + "&pn=" + i + "\">"
                            + String.valueOf(i) + "</a></span>");
                } else {
                    ret.append("<span class=\"currentPage\">" + String.valueOf(i)
                               + "</span>");
                }
            }
            if (page < totalPages) {
                ret.append("<span class=\"commentPageLink\"><a href=\""
                        + url.toString() + "&pn=" + (page + 1)
                        + "\" title=\"P&aacute;gina siguiente\">&gt;&gt;</a></span>");
            }
            ret.append("\n        </div>");
            ret.append("\n      </div>");
        }

        ret.append("\n  </div>\n<div>");
        ret.append("\n  </td>\n</tr>\n</table>\n");
        return ret.toString();
    }

    public String getURL()
    {
        return "#";
    }

    /**
     * Calcula el n&uacute;mero total de p&aacute;ginas a desplegar
     * @param mse
     * @return
     */
    private long totalPagesNumber(MicroSiteElement mse) {

        //System.out.println("Cuenta los elementos para determinar las paginas.");
        long totalPages = 1L;
        long comments = 0L;
        GenericIterator<Comment> iterator = mse.listComments();

        while (iterator.hasNext()) {
            iterator.next();
            comments++;
        }
        if (comments > MicroSiteElement.COMMENTS_IN_PAGE) {
            totalPages = comments / MicroSiteElement.COMMENTS_IN_PAGE;
            if (comments % MicroSiteElement.COMMENTS_IN_PAGE > 0) {
                totalPages++;
            }
        }

        //System.out.println("Ya sabe cuantas paginas:" + totalPages);
        return totalPages;
    }

    private String getCommentsByPage(MicroSiteElement mse, long page, Member mem) {

        //System.out.println("En getCommentsByPage");
        Iterator iterator = mse.listComments();
        StringBuilder ret = new StringBuilder(400);
        int ordinal = 0;
        long firstInPage = ((page - 1) * MicroSiteElement.COMMENTS_IN_PAGE) + 1;
        long lastInPage = page * MicroSiteElement.COMMENTS_IN_PAGE;

        iterator = SWBComparator.sortByCreated(iterator, false);

        while (iterator.hasNext()) {
            Comment comment = (Comment) iterator.next();
            ordinal++;

            //System.out.println("Pasa por comentario: " + ordinal);
            if (ordinal < firstInPage) {
                continue;
            } else if (ordinal > lastInPage) {
                break;
            }

            String spamMark = (comment.isSpam() ? "Es spam" : "Marcar como spam");
            ret.append("\n    <div id=\"comment" + comment.getId() + "\" class=\"comment-entry\">");
            ret.append("\n      <div class=\"comment-head\">");
            ret.append("\n        <div class=\"comment-info\">");
            ret.append("\n          " + ordinal + ". ");
            try {
                if (!comment.getCreator().getPhoto().equals("")) {
                    ret.append("<img src=\"" + SWBPlatform.getWebWorkPath() + comment.getCreator().getPhoto() + "\" alt=\"foto\" border=\"0\">");
                }
            } catch (NullPointerException npe) {}
            ret.append("<strong>");
            try {
                if (!comment.getCreator().getFullName().equalsIgnoreCase("")) {
                    ret.append(comment.getCreator().getFullName());
                } else {
                    ret.append("Desconocido");
                }
            } catch (NullPointerException npe) {
                ret.append("Desconocido");
            }
            ret.append("</strong>\n        </div>");
            ret.append("\n        <span class=\"comment-time\"> ("
                    + SWBUtils.TEXT.getTimeAgo(comment.getCreated(), mem.getUser().getLanguage()) + ")</span>");
            if (mem.canView()) {
                ret.append("\n        <span class=\"comment-spam\"><a href=\"javascript:spam("
                        + comment.getId() + ");\" id=\"spamMark"+ comment.getId() + "\">" + spamMark + "</a></span>");
            } else if (comment.isSpam()) {
                ret.append("\n        <span class=\"comment-spam\">" + spamMark + "</span>");
            }
            ret.append("\n      </div>");
            ret.append("\n      <div id=\"comment_body_" + comment.getId() + "\">");
            ret.append("\n        <div class=\"comment-body\">");
            ret.append("\n          <div>" + comment.getDescription() + "</div>");
            ret.append("\n        </div>");
            ret.append("\n      </div>");
            ret.append("\n    </div>");
        }
        return ret.toString();
    }
}
