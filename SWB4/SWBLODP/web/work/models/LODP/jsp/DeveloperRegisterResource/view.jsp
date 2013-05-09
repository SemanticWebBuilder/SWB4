<%-- 
    Document   : view
    Created on : 6/05/2013, 08:26:08 PM
    Author     : rene.jara
--%>
<%@page import="org.semanticwb.*"%>
<%@page import="org.semanticwb.model.*"%>
<%@page import="org.semanticwb.portal.api.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest" /><html>
<%
    WebPage wpage = paramRequest.getWebPage();
    WebSite wsite = wpage.getWebSite();
    User usr = paramRequest.getUser();
    /*Resource base = paramRequest.getResourceBase();
    String roladmin = base.getAttribute("rolid", "");
    Role role = wsite.getUserRepository().getRole(roladmin);
    */
    String contextPath = SWBPlatform.getContextPath();
    String context = SWBPortal.getContextPath();
    String url = paramRequest.getActionUrl().setAction(paramRequest.Action_ADD).toString();
    String repositoryId = wpage.getWebSite().getUserRepository().getId();

    if (request.getParameter("alertmsg") != null) {
        String strMsg = request.getParameter("alertmsg");
        strMsg = strMsg.replace("<br>", "\\n\\r");
%>
<script type="text/javascript">
    alert('<%=strMsg%>');
</script>
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
            if(isEmpty('cmnt_seccode')) {
                alert('Para registrarte es necesario que escribas el texto de la imagen.\\nEn caso de no ser claro puedes cambiarlo haciendo clic en <<Cambiar imagen>>.');
            }else{
                if (!validateReadAgree()){
                    alert('Para registrarte es necesario que aceptar los términos y condiciones');
                }else{
                   return true;
                    //alert("enviar");
                }
            }
        }else {
            alert("Datos incompletos");
        }
        return false;
    }
    function validateReadAgree(){
        var ret=false;
        ret=dijit.byId("acept").checked;
        //alert(ret);
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
        var email = dijit.byId( "email2" );
        var strEmail = email.getValue();
        if(strEmail!=""){
            //alert(strEmail);
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
        var login = dijit.byId( "login" );
        var filter = /^[a-zA-Z0-9.@]+$/;
        var strLogin = login.getValue();
        if(strLogin!=""){
            if(filter.test(strLogin)){
                if(canAddLogin('<%=repositoryId%>',strLogin)){
                    valid=true;
                }else{
                    login.displayMessage( "Nombre de usuario duplicado" );
                }
            }else{
                login.displayMessage( "Nombre de usuario inválido" );
            }
        }
        return valid;
    }

    function isValidPass() {
        var valid = false;
        var passwd = dijit.byId("passwd").getValue();
        var login = dijit.byId( "login" ).getValue();
        if(!isEmpty(passwd) && passwd!=login){
            valid = true;
        }
        return valid;
    }
    -->
</script>
<h1>Hello World!</h1>
    <div>
        <form id="form1ru" dojoType="dijit.form.Form" class="swbform" action="<%=url%>" method="post">
            <div>
                <p>
                    <label for="lastName"><b>*</b>Apellido Paterno</label>
                    <input type="text" name="lastName" id="lastName" dojoType="dijit.form.ValidationTextBox" value="<%//=lastName%>" required="true" promptMessage="" invalidMessage="" trim="true" regExp="[a-zA-Z\u00C0-\u00FF' ]+" />
                </p>
                <p>
                    <label for="secondLastName">Apellido Materno</label>
                    <input type="text" name="secondLastName" id="secondLastName" dojoType="dijit.form.ValidationTextBox" value="<%//=secondLastName%>" required="false" promptMessage="" trim="true" regExp="[a-zA-Z\u00C0-\u00FF' ]+"/>
                </p>
            </div>
            <div>
                <p>
                    <label for="firstName"><b>*</b>Nombre</label>
                    <input type="text" name="firstName" id="firstName" dojoType="dijit.form.ValidationTextBox" value="<%//=firstName%>"  required="true" promptMessage="" invalidMessage="" trim="true" regExp="[a-zA-Z\u00C0-\u00FF' ]+"/>
                </p>
                <p>
                    <label for="email"><b>*</b>Correo electronico de contacto</label>
                    <input type="text" name="email" id="email2" dojoType="dijit.form.ValidationTextBox" value="<%//=email%>" maxlength="60" required="true" promptMessage="" invalidMessage="" isValid="return isValidThisEmail()" trim="true"/>
                </p>
            </div>
            <div>
                <p>
                    <label for="login"><b>*</b>Usuario</label>
                    <input type="text" name="login" id="login" dojoType="dijit.form.ValidationTextBox" value="" maxlength="18" required="true" promptMessage="" invalidMessage=""  isValid="return isValidLogin()" trim="true" />
                </p>
                <p>
                    <label for="passwd"><b>*</b>Contraseña</label>
                    <input type="password" name="passwd" id="passwd" dojoType="dijit.form.ValidationTextBox" value="" maxlength="12" required="true" promptMessage="" invalidMessage="" isValid="return isValidPass();" trim="true" />
                </p>
                <div class="clearer">&nbsp;</div>
            </div>
            <div>
                <p>
                    <img src="<%=context%>/swbadmin/jsp/securecode.jsp?sAttr=cdlog" id="imgseccode" width="200" height="100" alt="" />
                    <br/>¿No lo puedes ver?&nbsp;<a href="#" onclick="changeSecureCodeImage('imgseccode');">Intenta otro texto</a>
                </p>
                <p>
                    <label for="cmnt_seccode"><b>*</b>Ingresa el texto de la imagen</label>
                    <input type="text" name="cmnt_seccode" id="cmnt_seccode" maxlength="8" value="" dojoType="dijit.form.ValidationTextBox" required="true" promptMessage="" invalidMessage="" trim="true"/>
                </p>
            </div>
            <div>
                <p class="icv-3col">
                    <a href="#" onclick="openSplash();return false;">Términos y condiciones</a>
                </p>
                <p>
                    <label for="acept"><b>*</b>Acepto los términos y condiciones:</label>
                    <input type="checkbox" name="acept" id="acept" maxlength="8" value="true" dojoType="dijit.form.CheckBox" required="true" promptMessage="promp" invalidMessage="invalid" isValid="return confirm('this.checkbox.value==true')"/>
                </p>
            </div>
            <div class="centro">
                <input type="reset" value="Borrar"/>
                <input type="submit" onclick="return enviar()" value="Enviar"/>
            </div>
        </form>
    </div>

