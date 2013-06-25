<%-- 
    Document   : view
    Created on : 6/05/2013, 08:26:08 PM
    Author     : rene.jara
--%>
<%@page import="com.infotec.lodp.swb.Institution"%>
<%@page import="java.util.Iterator"%>
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
    String url = paramRequest.getActionUrl().setAction(paramRequest.Action_ADD).toString();
    String repositoryId = wpage.getWebSite().getUserRepository().getId();
    String firstName = "";
    String rfc ="";
    String phone="";
    String position="";
    String uniAdm ="";
    String dirAdm="";
    String certificate ="";
    String bossName ="";
    String bossEmail="";
    String institutionId="";
    if (request.getParameter("firstName") != null) {
        firstName = request.getParameter("firstName");
    }
    String lastName = "";
    if (request.getParameter("lastName") != null) {
        lastName = request.getParameter("lastName");
    }
    String secondLastName = "";
    if (request.getParameter("secondLastName") != null) {
        secondLastName = request.getParameter("secondLastName");
    }
    String email = "";
    if (request.getParameter("email") != null) {
        email = request.getParameter("email");
    }
    String login = "";
    if (request.getParameter("user") != null) {
        login = request.getParameter("user");
    }
    if (request.getParameter("rfc") != null) {
        rfc = request.getParameter("rfc");
    }
    if (request.getParameter("phone") != null) {
        phone = request.getParameter("phone");
    }
    if (request.getParameter("position") != null) {
        position = request.getParameter("position");
    }
    if (request.getParameter("uniAdm") != null) {
        uniAdm = request.getParameter("uniAdm");
    }
    if (request.getParameter("dirAdm") != null) {
        dirAdm = request.getParameter("dirAdm");
    }
    if (request.getParameter("certificate") != null) {
        certificate = request.getParameter("certificate");
    }
    if (request.getParameter("bossName") != null) {
        bossName = request.getParameter("bossName");
    }
    if (request.getParameter("bossEmail") != null) {
        bossEmail = request.getParameter("bossEmail");
    }
    if (request.getParameter("institutionId") != null) {
        institutionId = request.getParameter("institutionId");
    }

       if (request.getParameter("msg") != null) {
        String strMsg = request.getParameter("msg");
//        strMsg = strMsg.replace("<br>", "\\n\\r");
%>
<div>
    <%=strMsg%>
</div>
<%
    }

%>
<script type="text/javascript">
    <!--
    // scan page for widgets and instantiate them
    dojo.require("dojo.parser");
    dojo.require("dijit._Calendar");
    dojo.require("dijit.ProgressBar");
    dojo.require("dijit.TitlePane");
    dojo.require("dijit.TooltipDialog");
    dojo.require("dijit.Dialog");
    // editor:
    dojo.require("dijit.Editor");

    // various Form elemetns
    dojo.require("dijit.form.Form");
    dojo.require("dijit.form.CheckBox");
    dojo.require("dijit.form.Textarea");
    dojo.require("dijit.form.FilteringSelect");
    dojo.require("dijit.form.TextBox");
    dojo.require("dijit.form.DateTextBox");
    dojo.require("dijit.form.TimeTextBox");
    dojo.require("dijit.form.Button");
    dojo.require("dijit.form.NumberSpinner");
    dojo.require("dijit.form.Slider");
    dojo.require("dojox.form.BusyButton");
    dojo.require("dojox.form.TimeSpinner");
    dojo.require("dijit.form.ValidationTextBox");
    dojo.require("dijit.layout.ContentPane");
    dojo.require("dijit.form.NumberTextBox");
    dojo.require("dijit.form.DropDownButton");

    function enviar() {
        var objd=dijit.byId('form1ru');

        if(objd.validate())
        {
           return true;
        }else {
            alert("Datos incompletos");
        }
        return false;
    }
    function isValidThisEmail(){
        var valid=false;
        var email = dijit.byId( "email" );
        var strEmail = email.getValue();
        if(strEmail!=""){
            if(isValidEmail(strEmail)){
                if(canAddEmail('<%=repositoryId%>',strEmail)){
                    valid=true;
                }else{
                    email.displayMessage( "<%=paramRequest.getLocaleString("lblEmailDupl")%>" );
                }
            }else{
                email.displayMessage( "<%=paramRequest.getLocaleString("lblEmailFault")%>" );
            }
        }
        return valid;
    }
    function isValidLogin(){
        var valid=false;
        var login = dijit.byId( "user" );
        var filter = /^[a-zA-Z0-9.@]+$/;
        var strLogin = login.getValue();
        if(strLogin!=""){
            if(filter.test(strLogin)&&strLogin.length>2){
                if(canAddLogin('<%=repositoryId%>',strLogin)){
                    valid=true;
                }else{
                    login.displayMessage( "<%=paramRequest.getLocaleString("lblLoginDupl")%>" );
                }
            }else{
                login.displayMessage( "<%=paramRequest.getLocaleString("lblLoginFault")%>" );
            }
        }
        return valid;
    }

    function isValidPass() {
        var valid = false;
        var passwd = dijit.byId("passwd").getValue();
        var login = dijit.byId( "user" ).getValue();
        if(!isEmpty(passwd) && passwd!=login){
            valid = true;
        }
        return valid;
    }
    -->
</script>
    <div id="registro" class="formas">
        <form id="form1ru" dojoType="dijit.form.Form" class="swbform" action="<%=url%>" method="post">
            <div class="registro">
                <div class="reg1">
                    <p>
                        <label for="lastName"><b>*</b><%=paramRequest.getLocaleString("lblLastName")%></label>
                        <input type="text" name="lastName" id="lastName" dojoType="dijit.form.ValidationTextBox" value="<%=lastName%>" required="true" _promptMessage="<%=paramRequest.getLocaleString("promptMsgLastName")%>" invalidMessage="<%=paramRequest.getLocaleString("lblLastNameFault")%>" trim="true" regExp="[a-zA-Z\u00C0-\u00FF' ]+" />
                    </p>
                    <p>
                        <label for="secondLastName"><%=paramRequest.getLocaleString("lblSecondLastName")%></label>
                        <input type="text" name="secondLastName" id="secondLastName" dojoType="dijit.form.ValidationTextBox" value="<%=secondLastName%>" required="false" _promptMessage="<%=paramRequest.getLocaleString("promptMsgSecondLastName")%>" invalidMessage="<%=paramRequest.getLocaleString("lblSecondLastNameFault")%>" trim="true" regExp="[a-zA-Z\u00C0-\u00FF' ]+"/>
                    </p>
                    <p>
                        <label for="firstName"><b>*</b><%=paramRequest.getLocaleString("lblFirstName")%></label>
                        <input type="text" name="firstName" id="firstName" dojoType="dijit.form.ValidationTextBox" value="<%=firstName%>"  required="true" _promptMessage="<%=paramRequest.getLocaleString("promptMsgFirstName")%>" invalidMessage="<%=paramRequest.getLocaleString("lblFirstNameFault")%>" trim="true" regExp="[a-zA-Z\u00C0-\u00FF' ]+"/>
                    </p>
                </div>
                <div class="reg2">
                    <p>
                        <label for="email"><b>*</b><%=paramRequest.getLocaleString("lblEmail")%></label>
                        <input type="text" name="email" id="email" dojoType="dijit.form.ValidationTextBox" value="<%=email%>" maxlength="60" required="true" _promptMessage="<%=paramRequest.getLocaleString("promptMsgEmail")%>" invalidMessage="<%=paramRequest.getLocaleString("lblEmailFault")%>" isValid="return isValidThisEmail()" trim="true"/>
                    </p>
                    <p>
                        <label for="user"><b>*</b><%=paramRequest.getLocaleString("lblLogin")%></label>
                        <input type="text" name="user" id="user" dojoType="dijit.form.ValidationTextBox" value="<%=login%>" maxlength="18" required="true" _promptMessage="<%=paramRequest.getLocaleString("promptMsgLogin")%>" invalidMessage="<%=paramRequest.getLocaleString("lblLoginFault")%>"  isValid="return isValidLogin()" trim="true" />
                    </p>
                    <p>
                        <label for="passwd"><b>*</b><%=paramRequest.getLocaleString("lblPassword")%></label>
                        <input type="password" name="passwd" id="passwd" dojoType="dijit.form.ValidationTextBox" value="" maxlength="12" required="true" _promptMessage="<%=paramRequest.getLocaleString("promptMsgPassword")%>" invalidMessage="<%=paramRequest.getLocaleString("lblPasswordFault")%>" isValid="return isValidPass();" trim="true" />
                    </p>
                </div>
                <div class="captcha">
                </div>
                <div class="terminos">
                </div>
                <div class="reg1">
                    <p>
                        <label for="rfc"><b>*</b><%=paramRequest.getLocaleString("lblRfc")%></label>
                        <input type="text" name="rfc" id="rfc" dojoType="dijit.form.ValidationTextBox" value="<%=rfc%>" required="true" _promptMessage="<%=paramRequest.getLocaleString("promptMsgRfc")%>" invalidMessage="<%=paramRequest.getLocaleString("lblRfcFault")%>" trim="true" uppercase="true" regExp="[A-Z]{4}[0-9]{6}[A-Z0-9]{3}" />
                    </p>
                    <p>
                        <label for="phone"><b>*</b><%=paramRequest.getLocaleString("lblPhone")%></label>
                        <input type="text" name="phone" id="phone" dojoType="dijit.form.ValidationTextBox" value="<%=phone%>" required="true" _promptMessage="<%=paramRequest.getLocaleString("promptMsgPhone")%>" invalidMessage="<%=paramRequest.getLocaleString("lblPhoneFault")%>" trim="true" regExp="\d{8,13}"/>
                    </p>
                    <p>
                        <label for="position"><b>*</b><%=paramRequest.getLocaleString("lblPosition")%></label>
                        <input type="text" name="position" id="position" dojoType="dijit.form.ValidationTextBox" value="<%=position%>"  required="true" _promptMessage="<%=paramRequest.getLocaleString("promptMsgPosition")%>" invalidMessage="<%=paramRequest.getLocaleString("lblPositionFault")%>" trim="true" regExp="[a-zA-Z\u00C0-\u00FF' ]+"/>
                    </p>
                </div>
                <div class="reg2">
                    <p>
                        <label for="uniAdm"><b>*</b><%=paramRequest.getLocaleString("lblUniAdm")%></label>
                        <input type="text" name="uniAdm" id="uniAdm" dojoType="dijit.form.ValidationTextBox" value="<%=uniAdm%>" required="true" _promptMessage="<%=paramRequest.getLocaleString("promptMsgUniAdm")%>" invalidMessage="<%=paramRequest.getLocaleString("lblUniAdmFault")%>" trim="true" regExp="[a-zA-Z\u00C0-\u00FF' ]+" />
                    </p>
                    <p>
                        <label for="dirAdm"><b>*</b><%=paramRequest.getLocaleString("lblDirAdm")%></label>
                        <input type="text" name="dirAdm" id="dirAdm" dojoType="dijit.form.ValidationTextBox" value="<%=dirAdm%>" required="true" _promptMessage="<%=paramRequest.getLocaleString("promptMsgDirAdm")%>" invalidMessage="<%=paramRequest.getLocaleString("lblDirAdmFault")%>" trim="true" regExp="[a-zA-Z\u00C0-\u00FF' ]+"/>
                    </p>
                    <p>
                        <label for="certificate"><b>*</b><%=paramRequest.getLocaleString("lblCertificate")%></label>
                        <input type="text" name="certificate" id="certificate" dojoType="dijit.form.ValidationTextBox" value="<%=certificate%>"  required="true" _promptMessage="<%=paramRequest.getLocaleString("promptMsgCertificate")%>" invalidMessage="<%=paramRequest.getLocaleString("lblCertificateFault")%>" trim="true" regExp="[a-zA-Z\u00C0-\u00FF' ]+"/>
                    </p>
                </div>
                <div class="captcha">
                </div>
                <div class="terminos">
                </div>
                <div class="reg1">
                    <p>
                        <label for="bossName"><b>*</b><%=paramRequest.getLocaleString("lblBossName")%></label>
                        <input type="text" name="bossName" id="bossName" dojoType="dijit.form.ValidationTextBox" value="<%=bossName%>" required="true" _promptMessage="<%=paramRequest.getLocaleString("promptMsgBossName")%>" invalidMessage="<%=paramRequest.getLocaleString("lblBossNameFault")%>" trim="true" regExp="[a-zA-Z\u00C0-\u00FF' ]+" />
                    </p>
                    <p>
                        <label for="bossEmail"><b>*</b><%=paramRequest.getLocaleString("lblBossEmail")%></label>
                        <input type="text" name="bossEmail" id="bossEmail" dojoType="dijit.form.ValidationTextBox" value="<%=bossEmail%>" required="true" _promptMessage="<%=paramRequest.getLocaleString("promptMsgBossEmail")%>" invalidMessage="<%=paramRequest.getLocaleString("lblBossEmailFault")%>" trim="true" isValid="return isValidEmail(dijit.byId('bossEmail').getValue())"/>
                    </p>
                    <p>
                        <label for="institutionId"><b>*</b><%=paramRequest.getLocaleString("lblInstitution")%></label>
                        <select name="institutionId" id="institutionId" dojoType="dijit.form.FilteringSelect" required="true" promptMessage="<%=paramRequest.getLocaleString("promptMsgInstitution")%>">
                                <%
                                            Iterator<Institution> itin = Institution.ClassMgr.listInstitutions(wsite);
//                                            Iterator<EntidadFederativa> iten = SWBComparator.sortByDisplayName(EntidadFederativa.ClassMgr.listEntidadFederativas(ws),user.getLanguage());
                                            while (itin.hasNext()) {
                                                Institution institution = itin.next();%>
                                <option value="<%=institution.getId()%>" <%=institution.getId().equals(institutionId) ? "selected" : ""%>><%=institution.getInstitutionTitle()%></option>
                            <%
                                        }
                            %>
                        </select>
                    </p>
                </div> 

                <div class="captcha">
                </div>
                <div class="terminos">
                </div>
            </div>
            <div>
                <input type="submit" class="boton-subir"  onclick="return enviar()" value="<%=paramRequest.getLocaleString("lblSubmit")%>"/>
                <input type="reset" class="boton-cancelar" value="<%=paramRequest.getLocaleString("lblReset")%>"/>
            </div>
        </form>
    </div>

