<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="org.semanticwb.portal.api.*,org.semanticwb.portal.community.*,org.semanticwb.*,org.semanticwb.model.*,java.util.*"%>
<script type="text/javascript">
    dojo.require("dojox.layout.FloatingPane");
    dojo.require("dijit.form.Button");
    dojo.require("dijit.form.TextBox");

    dojo.require("dijit.Editor");
    dojo.require("dijit._editor.plugins.LinkDialog");
    dojo.require("dijit._editor.plugins.FontChoice");
    dojo.require("dijit._editor.plugins.TextColor");
    dojo.require("dojo.parser");    
    dojo.require("dojox.xml.parser");
</script>
<%
    SWBParamRequest paramRequest=(SWBParamRequest)request.getAttribute("paramRequest");
    User user=paramRequest.getUser();
    WebPage wpage=paramRequest.getWebPage();
    Member member=Member.getMember(user,wpage);
    if(!member.canAdd())
    {
        return;
    }
%>

<form name="frmaddpost" id="frmaddpost" method="post" action="<%=paramRequest.getActionUrl()%>">
    <input type="hidden" name="act" value="<%=request.getParameter("act")%>">
    <div>
        <fieldset><legend></legend>
            <div>
                <p>
                    <label for="title">Título:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label><input id="title" size="50" maxlength="50" name="title" value=""><br>
                    <label for="description">Descripción:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label><input size="50" maxlength="255" id="description" name="description" value=""><br>
                    <label for="content">Contenido de la entrada:&nbsp;&nbsp;&nbsp;</label>
                    <textarea id="content" plugins="['bold','italic','underline', 'strikethrough','|','insertUnorderedList','insertOrderedList','|','createLink','unlink']" dojoType="dijit.Editor" rows="5" cols="23" name="content"></textarea>
                    <script>
                        function validaForma()
                        {                            
                            var title = frmaddpost.title.value;
                            if(!title)
                            {
                                alert('Debe ingresar el título de la entrada');
                                return;
                            }
                            var description = frmaddpost.description.value;
                            if(!description)
                            {
                                alert('Debe ingresar la descripción de la entrada');
                                return;
                            }
                            content = dijit.byId('content').getValue(false);
                            if(!content)
                            {
                                alert('Debe ingresar la entrada del post');
                                return;
                            }
                            var msg='¿Estan los datos correctos de la entrada del blog?';
                            if(confirm(msg))
                            {
                                dojo.byId('content').value=content;
                                dojo.byId('frmaddpost').submit();
                            }
                        }
                    </script>
                </p>
            </div>
        </fieldset>
        <br>
                    <fieldset>
                        <legend><strong>¿Quién puede ver este video?</strong></legend>
                        <ul class="options">
                            <%String chk="checked=\"checked\"";%>
                            <li><label><input type="radio" checked class="radio" name="level" value="0" /> Cualquiera</label></li>
                            <li><label><input type="radio" class="radio" name="level" value="1" /> Sólo los miembros</label></li>
                            <li><label><input type="radio" class="radio" name="level" value="3" /> Sólo yo</label></li>
                        </ul>
                    </fieldset>
                            <br>
            <div class="editarInfo"><p><a onclick="validaForma()" href="#">Guardar</a></p></div>            
            <div class="editarInfo"><p><a href="<%=paramRequest.getRenderUrl()%>">Cancelar</a></p></div>        
    </div>    
</form>
    <br>
    <br>
        