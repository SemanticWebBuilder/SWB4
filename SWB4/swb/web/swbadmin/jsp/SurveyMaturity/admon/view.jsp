<%@page import="org.semanticwb.survey.Admin"%>
<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest"/>
<%
            SWBResourceURL url = paramRequest.getRenderUrl();
            SWBResourceURL urlAction = paramRequest.getActionUrl();
%>
<html>
    <head>
        <script type="text/javascript">dojoConfig = {parseOnLoad: true}</script>
        <script type="text/javascript">
            function post(parametros)
            {
                var content='';
                
                for (var key in parametros)
                {                    
                    var value=parametros[key];
                    content+=key+':\''+encodeURIComponent(value)+'\',';
                }
                var url='<%=urlAction%>';
                var strToEval='dojo.xhrPost({form: url,timeout: 3000,content: {'+ content +' }})';
                
                try
                {
                    eval(strToEval);
                }
                catch(err)
                {
                    alert(err);
                }
                
                
            }
            function doGet(url,funcion)
            {
                var content='';

                for (var key in parametros)
                {
                    var value=parametros[key];
                    content+=key+':\''+encodeURIComponent(value)+'\',';
                }                
                var strToEval='dojo.xhrGet({form: url,timeout: 3000,load:function(data){funcion();},content: {'+ content +' }})';

                try
                {
                    eval(strToEval);
                }
                catch(err)
                {
                    alert(err);
                }


            }
            function sendform(id,funcion)
            {
                var _form = dojo.byId(id);
                
                var xhrArgs = {
                    form: _form,
                    handleAs: "text",
                    load: function(data){
                        //dojo.byId("response").innerHTML = "Form posted.";
                        funcion();
                    },
                    error: function(error){
                        alert('error'+error);
                        // We'll 404 in the demo, but that's okay.  We don't have a 'postIt' service on the
                        // docs server.
                        //dojo.byId("response").innerHTML = "Form posted.";
                    }
                }
                
                var deferred = dojo.xhrPost(xhrArgs);
                
                return deferred;
            }

            function sendform(id)
            {
                var _form = dojo.byId(id);

                var xhrArgs = {
                    form: _form,
                    handleAs: "text",
                    load: function(data){
                        //dojo.byId("response").innerHTML = "Form posted.";
                        
                    },
                    error: function(error){
                        alert('error'+error);
                        // We'll 404 in the demo, but that's okay.  We don't have a 'postIt' service on the
                        // docs server.
                        //dojo.byId("response").innerHTML = "Form posted.";
                    }
                }

                var deferred = dojo.xhrPost(xhrArgs);

                return deferred;
            }
            

            
            

            function getValueEditor(id)
            {
                var html=dijit.byId(id).getValue(true);
                html=cleanEditor(html);
                return html;
            }
            function resetEditor(id)
            {
                dijit.byId(id).setValue('');
            }
            String.prototype.endsWith = function(suffix) {
                return this.match(suffix+"$") == suffix;
            };
            String.prototype.startsWith = function(str)
            {
                return (this.match("^"+str)==str)
            }
            String.prototype.isEmpty = function(str)
            {
                return (this.length==0)
            }
            function cleanEditor(content)
            {
                var replace='<br _moz_editor_bogus_node="TRUE" />';
                content=content.replace(replace,'');
                if(content=='<br />')
                {
                    content='';
                }
                return content;
            }
            function reloadAdmonQuestion()
            {
            <%
                        url.setMode(Admin.MODE_ADMON_QUESTION);
                        url.setCallMethod(SWBResourceURL.Call_DIRECT);

            %>
                    var url='<%=url%>';//'work/models/admon/admonquestion.jsp';
                    reload(url,'admonquestion');
                }
                function reloadAdmonSection()
                {
            <%
                        url.setMode(Admin.MODE_ADMON_SECTION);
                        url.setCallMethod(SWBResourceURL.Call_DIRECT);

            %>
                    var url='<%=url%>';//'work/models/admon/admonquestion.jsp';
                    reload(url,'admonSection');
                }
                function reloadAdmonSurvey()
                {
            <%
                        url.setMode(Admin.MODE_ADMON_SURVEY);
                        url.setCallMethod(SWBResourceURL.Call_DIRECT);

            %>
                    var url='<%=url%>';//'work/models/admon/admonquestion.jsp';
                    reload(url,'addSurvey');
                }
                function reloadAdmonParte()
                {
            <%
                        url.setMode(Admin.MODE_ADMON_PART);
                        url.setCallMethod(SWBResourceURL.Call_DIRECT);

            %>
                    var url='<%=url%>';//'work/models/admon/admonquestion.jsp';
                    reload(url,'admonParte');
                }
                function reload(url,id)
                {
                    
                    dojo.xhrGet({
                        // The URL to request
                        url: url,
                        // The method that handles the request's successful result
                        // Handle the response any way you'd like!
                        load: function(result) {
                            dojo.byId(id).innerHTML = result;
                        }
                    });
                }
        </script>
    </head>
    <body class="claro">
        <div dojoType="dijit.layout.TabContainer" style="width: 900px; height: 2000px;" tabStrip="true">
            <div id="admonquestion" dojoType="dijit.layout.ContentPane" title="Preguntas" selected="true">
                <jsp:include page="admonquestion.jsp" flush="true"/>
            </div>
            <div id="addSurvey" dojoType="dijit.layout.ContentPane" title="Cuestionario" selected="false">
                <jsp:include page="admonSurvey.jsp" flush="true"/>
            </div>
            <div id="admonSection" dojoType="dijit.layout.ContentPane" title="Secci&oacute;n" selected="false">
                <jsp:include page="admonSection.jsp" flush="true"/>

            </div>
            <div id="admonParte" dojoType="dijit.layout.ContentPane" title="Parte" selected="false">
                <jsp:include page="admonParte.jsp" flush="true"/>
            </div>
        </div>

        <div id="dialogaddCuestion" dojoType="dijit.Dialog" title="Agregar Pregunta">
            <jsp:include flush="true" page="addquestion.jsp" />
        </div>
        <div id="dialogaddSurvey" dojoType="dijit.Dialog" title="Agregar Cuestionario">
            <jsp:include flush="true" page="addSurvey.jsp" />
        </div>
        <div id="dialogAdmonSection" dojoType="dijit.Dialog" title="Agregar Secci&oacute;n">
            <jsp:include flush="true" page="addSection.jsp" />
        </div>
        <div id="dialogAdmonParte" dojoType="dijit.Dialog" title="Agregar Parte">
            <jsp:include flush="true" page="addPart.jsp" />
        </div>
    </body>


</html>

