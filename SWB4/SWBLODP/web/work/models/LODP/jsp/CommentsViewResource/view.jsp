<%-- 
Document   : view
Created on : 14/05/2013, 07:26:08 PM
Author     : rene.jara
--%>
<%@page import="java.util.List"%>
<%@page import="com.infotec.lodp.swb.Publisher"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.infotec.lodp.swb.resources.CommentsViewResource"%>
<%@page import="com.infotec.lodp.swb.Developer"%>
<%@page import="com.infotec.lodp.swb.Comment"%>
<%@page import="java.util.Iterator"%>
<%@page import="org.semanticwb.platform.SemanticObject"%>
<%@page import="org.semanticwb.platform.SemanticOntology"%>
<%@page import="com.infotec.lodp.swb.Application"%>
<%@page import="com.infotec.lodp.swb.Dataset"%>
<%@page import="org.semanticwb.*"%>
<%@page import="org.semanticwb.model.*"%>
<%@page import="org.semanticwb.portal.api.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest" /><html>
    <%
                WebPage wpage = paramRequest.getWebPage();
                WebSite wsite = wpage.getWebSite();
                User usr = paramRequest.getUser();
                String contextPath = SWBPlatform.getContextPath();
                String context = SWBPortal.getContextPath();
                Resource base = paramRequest.getResourceBase();
                String repositoryId = wpage.getWebSite().getUserRepository().getId();

                String suri = request.getParameter("suri");
                String action = request.getParameter("act");

                SemanticOntology ont = SWBPlatform.getSemanticMgr().getOntology();
                SemanticObject sobj = null;
                if (suri != null && !suri.equals("") && action != null && action.equals("detail")) {
                    GenericObject gobj = null;
                    Iterator<Comment> itco = null;
                    if (suri != null && !suri.equals("")) {
                        gobj = ont.getGenericObject(SemanticObject.shortToFullURI(suri));
                        if (gobj != null && gobj instanceof Application) {
                            Application ap = (Application) gobj;
                            itco = ap.listComments();

                        } else if (gobj != null && gobj instanceof Dataset) {
                            Dataset ds = (Dataset) gobj;
                            itco = ds.listComments();
                        }
                    }

                    String name = "";
                    String email = "";
                    int nInappropriate;
                    gobj = usr.getSemanticObject().getGenericInstance();
                    if (gobj instanceof Developer) {
                        Developer de = (Developer) gobj;
                        name = de.getFullName();
                        email = de.getEmail();
                    } else if (gobj instanceof Publisher) {
                        Publisher pu = (Publisher) gobj;
                        name = pu.getFullName();
                        email = "---";//pu.getEmail();
                    }
                    if (itco != null) {
    %>
    <div class="tit">
    <h4>Comentarios</h4>
    </div>
    <script type="text/javascript">
        //        <!--
        // scan page for widgets and instantiate them
        dojo.require("dojo.parser");
        //        dojo.require("dijit._Calendar");
        //        dojo.require("dijit.ProgressBar");
        dojo.require("dijit.TitlePane");
        dojo.require("dijit.TooltipDialog");
        //        dojo.require("dijit.Dialog");
        // editor:
        //        dojo.require("dijit.Editor");

        // various Form elemetns
        dojo.require("dijit.form.Form");
        dojo.require("dijit.form.CheckBox");
        dojo.require("dijit.form.Textarea");
        //        dojo.require("dijit.form.FilteringSelect");
        //        dojo.require("dijit.form.TextBox");
        //        dojo.require("dijit.form.DateTextBox");
        //        dojo.require("dijit.form.TimeTextBox");
        //        dojo.require("dijit.form.Button");
        //        dojo.require("dijit.form.NumberSpinner");
        //        dojo.require("dijit.form.Slider");
        //        dojo.require("dojox.form.BusyButton");
        //        dojo.require("dojox.form.TimeSpinner");
        dojo.require("dijit.form.ValidationTextBox");
        //        dojo.require("dijit.layout.ContentPane");
        //        dojo.require("dijit.form.NumberTextBox");
        //        dojo.require("dijit.form.DropDownButton");

        function enviarco() {
            var objd=dijit.byId('form1co');
            if(objd.validate())
            {
                if(isEmpty('comment')) {
                    alert('<%=paramRequest.getLocaleString("lblCommentFault")%>');
                }else{
                    if (!validateReadAgree()){
                        alert('<%=paramRequest.getLocaleString("msgErrAgreement")%>');
                        return false;
                    }else{
                        return true;
                    }
                }
            }else {
                alert("<%=paramRequest.getLocaleString("msgErrForm")%>");
            }
            return false;
        }
        function validateReadAgree(){
            var ret=false;
            ret=dijit.byId("acept").checked;
            return ret;
        }
        function changeSecureCodeImage(imgid) {
            var img = dojo.byId(imgid);
            if(img) {
                var rn = Math.floor(Math.random()*99999);
                img.src = "<%=context%>/swbadmin/jsp/securecode.jsp?sAttr=cdlog&nc="+rn;
            }
        }
        function isValidThisEmail(){
            var valid=false;
            var email = dijit.byId( "email" );
            var strEmail = email.getValue();
            if(strEmail!=""){
                if(isValidEmail(strEmail)){
                    valid=true;
                }else{
                    email.displayMessage( "<%=paramRequest.getLocaleString("lblEmailFault")%>" );
                }
            }
            return valid;
        }
        //        -->
    </script>
    <div>
        <%
            try {
                nInappropriate = Integer.parseInt(base.getAttribute("inappropriate", "1"));
            } catch (NumberFormatException ignored) {
                nInappropriate = 1;
            }
            ArrayList<Comment> alco = CommentsViewResource.listComments(itco, nInappropriate);
            Iterator<Comment> itvco = alco.iterator();
            int tRec = alco.size();
            int nPag = 0;
            try {
                nPag = Integer.parseInt(request.getParameter("npag"));
            } catch (NumberFormatException ignored) {
                nPag = 0;
            }
            int nRecPPag = 0;
            try {
                nRecPPag = Integer.parseInt(base.getAttribute("num_comments_p_page", "5"));
            } catch (NumberFormatException ignored) {
                nRecPPag = 5;
            }
            int iRec = ((nPag) * nRecPPag);
            int fRec = iRec + nRecPPag;
            int tPag = (int) (tRec / nRecPPag);
            if ((tRec % nRecPPag) > 0) {
                tPag++;
            }
            SWBResourceURLImp urlina = new SWBResourceURLImp(request, base, wpage, SWBResourceURLImp.UrlType_ACTION);
            urlina.setAction(CommentsViewResource.Action_INAPPROPRIATE);
            urlina.setParameter("suri", suri);
            List lii = (List) request.getSession().getAttribute("io");
            for (int x = iRec; x < fRec && x < tRec; x++) {
                Comment co = alco.get(x);
                urlina.setParameter("cid", co.getId());
                urlina.setParameter("npag", nPag + "");
                boolean canRank = false;
                if (lii == null || !lii.contains(co.getId())) {
                    canRank = true;
                }
        %>
        <div class="comentario">
            <p class="comentador"><strong><%=co.getCommUserName()%></strong><em><%=co.getCommUserEmail()%></em></p>
            <p class="comentariotxt"><%=co.getComment()%></p>
            <%
            if(canRank){
            %>
            <p class="inapropiado"><a href="<%=urlina%>" title="Notificar como comentario inapropiado"><span>Comentario inapropiado</span></a></p>
            <%
            }
            %>
        </div>
        <%
            }
        %>
        <div class="paginacom">
            <ul>
                <%
                    String rurl = wpage.getUrl();
                    rurl += "?act=detail";
                    rurl += "&suri=" + suri;
                    rurl += "&npag=";
                    for (int x = 0; x < tPag; x++) {
                        if (x == nPag) {
                %>
                <li><span><%=(x + 1)%></span></li>
                <%
                } else {
                %>
                <li><a href="<%=rurl + x%>"><%=(x + 1)%></a></li>
                <%
                        }
                    }
                %>
            </ul>
        </div>
    <%
        }
        SWBResourceURLImp urladd = new SWBResourceURLImp(request, base, wpage, SWBResourceURLImp.UrlType_ACTION);
        urladd.setAction(CommentsViewResource.Action_COMMENT);
        urladd.setParameter("suri", suri);
    %>
    <div id="comentar" class="formas">
        <form id="form1co" dojoType="dijit.form.Form" class="swbform" action="<%=urladd%>" method="post">
            <div class="comentar">
                <div class="com1">
                    <p>
                        <label for="name"><b>*</b><%=paramRequest.getLocaleString("lblName")%></label>
                        <input type="text" name="name" id="name" dojoType="dijit.form.ValidationTextBox" value="<%=name%>" <%=name!=null&&!name.equals("")?"readonly=readonly":""%> required="true" _promptMessage="<%=paramRequest.getLocaleString("promptMsgName")%>" invalidMessage="<%=paramRequest.getLocaleString("lblNameFault")%>" trim="true" regExp="[a-zA-Z\u00C0-\u00FF' ]+"/>
                    </p>
                    <p>
                        <label for="email"><b>*</b><%=paramRequest.getLocaleString("lblEmail")%></label>
                        <input type="text" name="email" id="email" dojoType="dijit.form.ValidationTextBox" value="<%=email%>" <%=email!=null&&!email.equals("")?"readonly=readonly":""%> required="true" _promptMessage="<%=paramRequest.getLocaleString("promptMsgEmail")%>" invalidMessage="<%=paramRequest.getLocaleString("lblEmailFault")%>" isValid="return isValidThisEmail()" trim="true"/>
                    </p>
                </div>
                <div class="com2">
                    <p>
                        <label for="comment"><b>*</b><%=paramRequest.getLocaleString("lblComment")%></label>
                        <textarea id="comment" name="comment" data-dojo-type="dijit.form.Textarea"  required="true" promptMessage="<%=paramRequest.getLocaleString("promptMsgComment")%>" invalidMessage="<%=paramRequest.getLocaleString("lblCommentFault")%>" trim="true" regExp="[a-zA-Z]+"></textarea>
                    </p>
                </div>
                <div class="captcha">
                    <img src="<%=context%>/swbadmin/jsp/securecode.jsp?sAttr=cdlog" id="imgseccode" width="200" height="100" alt="" />
                    <label for="cmnt_seccode"><b>*</b><%=paramRequest.getLocaleString("lblCaptcha")%></label>
                    <input type="text" name="cmnt_seccode" id="cmnt_seccode" maxlength="8" value="" dojoType="dijit.form.ValidationTextBox" required="true" _promptMessage="<%=paramRequest.getLocaleString("promptMsgCaptcha")%>" invalidMessage="<%=paramRequest.getLocaleString("msgErrSecureCodeRequired")%>" trim="true"/>
                    <p><%=paramRequest.getLocaleString("lblTryRead")%><a href="#" onclick="changeSecureCodeImage('imgseccode');"><%=paramRequest.getLocaleString("lblTryAnotherText")%></a></p>
                </div>
                <div class="terminos">
                    <p>
                        <a href="#" _onclick="openSplash();return false;"><%=paramRequest.getLocaleString("lblLinkAgreement")%></a>
                        <input type="checkbox" name="acept" id="acept" value="true" dojoType="dijit.form.CheckBox" required="true" _promptMessage="<%=paramRequest.getLocaleString("lblAgreement")%>" invalidMessage="<%=paramRequest.getLocaleString("lblAgreement")%>" isValid="return confirm('this.checkbox.value==true')"/>
                        <label for="acept"><b>*</b><%=paramRequest.getLocaleString("lblAgreement")%></label>
                    </p>
                </div>
            </div>
            <div>
                <input type="submit" onclick="return enviarco()" value="<%=paramRequest.getLocaleString("lblSubmit")%>"  class="boton-subir"/>
                <input type="reset" value="<%=paramRequest.getLocaleString("lblReset")%>" class="boton-cancelar"/>
            </div>
        </form>
    </div>
</div>
    <%
                }
    %>
