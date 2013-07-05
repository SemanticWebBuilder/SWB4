<%--
    Document   : view Recurso DSPublisherResource
    Created on : 20/05/2013
    Author     : juan.fernandez
--%>

<%@page import="org.semanticwb.SWBUtils"%>
<%@page import="org.semanticwb.model.GenericObject"%>
<%@page import="org.semanticwb.SWBPortal"%>
<%@page import="org.semanticwb.SWBPlatform"%>
<%@page import="org.semanticwb.model.User"%>
<%@page import="org.semanticwb.model.WebSite"%>
<%@page import="org.semanticwb.model.WebPage"%>
<%@page import="com.infotec.lodp.swb.Topic"%>
<%@page import="com.infotec.lodp.swb.LicenseType"%>
<%@page import="com.infotec.lodp.swb.DatasetVersion"%>
<%@page import="com.infotec.lodp.swb.Publisher"%>
<%@page import="com.infotec.lodp.swb.utils.LODPUtils"%>
<%@page import="java.util.Locale"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Iterator"%>
<%@page import="com.infotec.lodp.swb.Tag"%>
<%@page import="com.infotec.lodp.swb.Dataset"%>
<%@page import="org.semanticwb.platform.SemanticObject"%>
<%@page import="org.semanticwb.platform.SemanticOntology"%>

<%@page import="org.semanticwb.portal.api.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest" /><html>
<%
   //System.out.println("en edit.JSP -- agregar o editar dataset"); 
    WebPage wpage = paramRequest.getWebPage();
    WebSite wsite = wpage.getWebSite();
    User usr = paramRequest.getUser();
    Publisher pub = LODPUtils.getPublisher(usr);
    if (pub == null) {
        // no disponible la pagina para usuarios que no sean publicadores
         out.println("<h2>"+paramRequest.getLocaleString("msg_bePublisher")+"....</h2>");
        return;
    }
    String repositoryId = wpage.getWebSite().getUserRepository().getId();
    String suri = request.getParameter("suri");

    String action = request.getParameter("act");
    if(null==action) action="";
    if (null==suri) {
        suri = "";
    }

    SimpleDateFormat sdf = new SimpleDateFormat("dd/MMMMM/yyyy h:mm a", new Locale("es"));
    boolean isNew = Boolean.TRUE;
    SemanticOntology ont = SWBPlatform.getSemanticMgr().getOntology();
    SemanticObject so = null;
    GenericObject go = null;
    
    if(suri.trim().length()>0) suri = SemanticObject.shortToFullURI(suri);
    
    Dataset ds = null;
    go = ont.getGenericObject(suri);
    if(go!=null&& go instanceof Dataset){
        ds = (Dataset)go;
        // cargar información del Dataset
        action = SWBResourceURL.Action_EDIT;
        isNew = Boolean.FALSE;
    } 
    
    String autoapprove = paramRequest.getResourceBase().getAttribute("autoapprove","0");
        boolean isAutoApprove = Boolean.FALSE;
        if(null!=autoapprove && "1".equals(autoapprove)){
            isAutoApprove = Boolean.TRUE;
        }
    
    String dstitle = "";
    String dsdescription = "";
    String dsemail = "";
    String dscreated = "";
    String dsupdated = "";
    String dsformat = "";
    String dssize = "";
    String dsversion = "";
    String dspubname= "";
    String dstoplevelname = "";
    String dswebsite = "";
    String dsurl = "";
    
    dspubname = pub.getFullName();
    dswebsite = pub.getPubInstitution()!=null&&pub.getPubInstitution().getInstitutionHome()!=null?pub.getPubInstitution().getInstitutionHome():"---";
    
    boolean isActive = Boolean.TRUE;
    boolean isApproved = Boolean.FALSE;
    
    boolean isReviewed = Boolean.FALSE;
    
    if (!isNew) { 
        
        //System.out.println("Cargando datos DataSET.....");
        //Se cargan datos del dataset en variables
        Publisher pub_ds = ds.getPublisher();
        
        dstitle=ds.getDatasetTitle()!=null?ds.getDatasetTitle():"";
        dsdescription=ds.getDatasetDescription()!=null?ds.getDatasetDescription():"";
        
        dsemail=pub_ds!=null&&pub_ds.getEmail()!=null?pub_ds.getEmail():"";
        
        dscreated=ds.getDatasetCreated()!=null?sdf.format(ds.getDatasetCreated()):"";
        dsupdated=ds.getDatasetUpdated()!=null?sdf.format(ds.getDatasetUpdated()):"";
        
        dsformat=ds.getDatasetFormat()!=null?ds.getDatasetFormat():"";
        dssize = ds.getDatasetSize()!=null?ds.getDatasetSize():"---";
        //System.out.println("Size: "+dssize);
        DatasetVersion dsver = ds.getActualVersion();
        dsversion = null!=dsver?""+dsver.getVersion():"0"; 
        
        
        dspubname=pub_ds!=null&&pub_ds.getFullName()!=null?pub_ds.getFullName():"---";
        
        dstoplevelname=ds.getInstitution()!=null&&ds.getInstitution().getTopLevelName()!=null?ds.getInstitution().getTopLevelName():"---";
        
        dsurl = ds.getDatasetURL()!=null?ds.getDatasetURL():"---";
        dswebsite = ds.getInstitution()!=null&&ds.getInstitution().getInstitutionHome()!=null?ds.getInstitution().getInstitutionHome():"";
        
        isApproved = ds.isApproved();
        isActive = ds.isDatasetActive();
        isReviewed = ds.isReviewed();
    }

    if (request.getParameter("msg") != null) {
        String strMsg = request.getParameter("msg");
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
    //dojo.require("dijit._Calendar");
    //dojo.require("dijit.ProgressBar");
    dojo.require("dijit.TitlePane");
    dojo.require("dijit.TooltipDialog");
    dojo.require("dijit.Dialog");
    // editor:
    //dojo.require("dijit.Editor");

    // various Form elemetns
    dojo.require("dijit.form.Form");
    dojo.require("dijit.form.CheckBox");
    dojo.require("dijit.form.Textarea");
    dojo.require("dijit.form.FilteringSelect");
    dojo.require("dijit.form.MultiSelect");
    dojo.require("dijit.form.TextBox");
    //dojo.require("dijit.form.DateTextBox");
    dojo.require("dijit.form.TimeTextBox");
    dojo.require("dijit.form.Button");
    //dojo.require("dijit.form.NumberSpinner");
    //dojo.require("dijit.form.Slider");
    dojo.require("dojox.form.BusyButton");
    //dojo.require("dojox.form.TimeSpinner");
    dojo.require("dijit.form.ValidationTextBox");
    //dojo.require("dijit.layout.ContentPane");
    //dojo.require("dijit.form.NumberTextBox");
    dojo.require("dijit.form.DropDownButton");

    function enviar() {
        var objd=dijit.byId('form1newDS');

        if(objd.validate())
        {
            if(!isValidTag()) {
                alert('Selecciona de la lista por lo menos una etiqueta.'); //<%//=paramRequest.getLocaleString("promptMsgCaptcha")%>
                return false;
            }
                if (!isValidTopic()){
                    alert('Selecciona de la lista por lo menos un tema');
                    return false;
                }
                return true;
        }else {
            alert("Datos incompletos");
        }
        return false;
    }

    function isValidTag() {
        var valid = false;
        var sellabels = dijit.byId("dslabels").domNode;
        if(sellabels.selectedIndex != -1){
            valid = true;
        }
        return valid;
    }
        function isValidTopic() {
        var valid = false;
        var sellabels = dijit.byId("dstopic").domNode;
        if(sellabels.selectedIndex != -1){
            valid = true;
        }
        return valid;
    }
        function isValidLicense() {
        var valid = false;
        var sellabels = dijit.byId("dslicense").domNode;
        if(sellabels.selectedIndex != -1){
            valid = true;
        }
        return valid;
    }
    
      function addversion(addr) {
        var sellabels = dijit.byId("dsvercomment");
        if(sellabels.selectedIndex != -1){
            valid = true;
        }
        return valid;
    }
    -->
</script>
    <div id="subedataset" class="formas">
        <%
            String claseStyle = "subefile1";
            SWBResourceURL url = paramRequest.getActionUrl();
            if(isNew){
                url.setAction(SWBResourceURL.Action_ADD);
            } else {
                url.setAction(SWBResourceURL.Action_EDIT);
                claseStyle = "subefile2";
            }
        %>
        <form id="form1newDS" dojoType="dijit.form.Form" class="swbform" action="<%=url.toString()%>" method="post">
            <%
            if(!isNew){
                %>
                <input type="hidden" name="suri" value="<%=ds.getShortURI()%>"/>
                <%
            }
        %>
        <div class="<%=claseStyle%>">
                
                    <label for="dstitle"><b>*</b><%=paramRequest.getLocaleString("lbl_title")%></label>
                    <input type="text" name="dstitle" id="dstitle" dojoType="dijit.form.ValidationTextBox" value="<%=dstitle%>" required="true"  invalidMessage="<%=paramRequest.getLocaleString("lbl_titlemissing")%>" trim="true" _regExp="[a-zA-Z\u00C0-\u00FF' ]+" />
                    <label for="dsdescription"><b>*</b><%=paramRequest.getLocaleString("lbl_description")%></label>
                    <textarea name="dsdescription" id="dsdescription" data-dojo-yype="dijit.form.TextArea" value="<%=dsdescription%>" required="true"  invalidMessage="<%=paramRequest.getLocaleString("lbl_descriptionmissing")%>" trim="true" _regExp="[a-zA-Z\u00C0-\u00FF' ]+" rows="5" cols="50"><%=dsdescription%></textarea>
                <!-- Temas -->
                    <label for="dstopic"><b>*</b><%=paramRequest.getLocaleString("lbl_topic")%></label>
                    <select multiple size="5" id="dstopic" dojoType="dijit.form.MultiSelect" name="dstopic" required="true" invalidMessage="<%=paramRequest.getLocaleString("lbl_topicmissing")%>" isValid="return isValidTopic()"  >
                            <%

                       String selected="";
                    Iterator<Topic> ittopic = Topic.ClassMgr.listTopics(wsite);  
                    while(ittopic.hasNext()){
                        Topic topic = ittopic.next();
                        if(null!=ds&&ds.hasTopic(topic)){
                            selected="selected";
                        }
                        
                    %>   
                    
                    <option value="<%=topic.getShortURI()%>" title="<%=topic.getTopicDescription()!=null?topic.getTopicDescription():topic.getTopicTitle()%>" <%=selected%>><%=topic.getTopicTitle()%> </option>
                    <%
                        selected="";
                }
                    %>
                    </select>
             <!-- Etiquetas -->
                    <label for="dslabels"><b>*</b><%=paramRequest.getLocaleString("lbl_labels")%></label>
                    <select multiple size="5" id="dslabels" dojoType="dijit.form.MultiSelect" name="dslabels" required="true" invalidMessage="<%=paramRequest.getLocaleString("lbl_tagmissing")%>"  isValid="return isValidTag()" >
                    <%
                      selected = "";
                      Iterator<Tag> ittag = Tag.ClassMgr.listTags(wsite);
                      while(ittag.hasNext()){  
                          Tag tag = ittag.next();
                          selected = "";
                          if(SWBResourceURL.Action_EDIT.equals(action) && null!=ds && ds.hasTag(tag) ){
                              selected = "selected";
                          }   
                    %>
                    <option value="<%=tag.getShortURI()%>" <%=selected%>><%=tag.getTagName()%> </option>
                    <%
                      }
                    %>
                    </select>
                    <label for="dslicense"><b>*</b><%=paramRequest.getLocaleString("lbl_license")%></label>
                    <select   id="dslicense" dojoType="dijit.form.FilteringSelect" name="dslicense" required="true" invalidMessage="<%=paramRequest.getLocaleString("lbl_licensemissing")%>"  _isValid="return isValidLicense()" >
                    <%
                        String ltypeid = "";
                        if(!isNew && ds!=null&&ds.getLicense()!=null){
                            ltypeid=ds.getLicense().getURI();
                        }
                        selected="";
                    Iterator<LicenseType> itltype = LicenseType.ClassMgr.listLicenseTypes(wsite);  
                    while(itltype.hasNext()){
                        LicenseType ltype = itltype.next();
                        if(ltypeid!=null&&ltypeid.equals(ltype.getURI())){
                            selected="selected";
                        }
                        
                    %>
                    <option value="<%=ltype.getShortURI()%>" title="<%=ltype.getLicenseDescription()!=null?ltype.getLicenseDescription():ltype.getLicenseTitle()%>" <%=selected%>><%=ltype.getLicenseTitle()%> </option>
                <%
                        selected="";
                }
                    %>
                    </select>

                    

                <%
                      if(!isNew)
                      {
                %>
                <label for="dsactive"><%=paramRequest.getLocaleString("lbl_active")%></label>
                <input type="checkbox" name="dsactive" id="dsactive" dojoType="dijit.form.CheckBox" value="true" <%=ds.isDatasetActive()?"checked":""%>  />
                <div class="linea"></div>
                <div class="datafijo">
        	<ul>
                    <li class="datofijo1"><strong><%=paramRequest.getLocaleString("lbl_weburl")%>:</strong><a href="<%=dswebsite%>" target="_blank"><%=dswebsite%></a></li>
                    <li class="datofijo2"><strong><%=paramRequest.getLocaleString("lbl_technicallink")%>:</strong><%=dspubname%></li>
                    <li class="datofijo3"><strong><%=paramRequest.getLocaleString("lbl_email")%>:</strong><a href="mailto:<%=dsemail%>" ><%=dsemail%></a></li>
                    <li class="datofijo4"><strong><%=paramRequest.getLocaleString("lbl_format")%>:</strong><%=dsformat%></li>
                    <li class="datofijo5"><strong><%=paramRequest.getLocaleString("lbl_size")%>:</strong><%=dssize%></li>
                    <li class="datofijo6"><strong><%=paramRequest.getLocaleString("lbl_version")%>:</strong><%=dsversion%></li>
                    <%
                          if(!isAutoApprove){
                    %>
                    <li class="datofijo7"><strong><%=paramRequest.getLocaleString("lbl_approve")%>:</strong><%=ds.isApproved()?"Sí":"No"%></li>
                    <%
                          }
                    %>
                    <li class="datofijo8"><strong><%=paramRequest.getLocaleString("lbl_created")%>:</strong><%=dscreated%></li>
                    <li class="datofijo9"><strong><%=paramRequest.getLocaleString("lbl_updated")%>:</strong><%=dsupdated%></li>
                    <li class="datofijo10"><strong><%=paramRequest.getLocaleString("lbl_urlendpoint")%></strong><%=dsurl%></li>
                </ul>
                </div>
                <%
                }
                %>
            </div>
            
                <%
                      if(!isNew){
                          out.println("<div id=\"botones2\">");
                      }
                      
                    SWBResourceURL urlbck = paramRequest.getRenderUrl();
                    urlbck.setParameter("act", "myds");
                    urlbck.setMode(SWBResourceURL.Mode_VIEW);
                
                %>
                <input type="button" value="<%=paramRequest.getLocaleString("lblCancel")%>" onclick="window.location='<%=urlbck.toString()%>';" class="boton-cancelar" /> 
                <%
                 if(!isNew){

                     SWBResourceURL urlnewversion = paramRequest.getActionUrl();
                     urlnewversion.setAction("addVersion"); 
                     urlnewversion.setParameter("suri", ds.getShortURI());
                     
                %>
                <input type="button" value="<%=paramRequest.getLocaleString("lbl_updatefile")%>" onclick="window.location='<%=urlnewversion.toString()%>';" class="boton-actualizar" /> 
                <%
                 } 
                %>
                <input type="submit" onclick="return enviar()" value="<%=paramRequest.getLocaleString("lblSubmit")%>" class="boton-subir" />
            <%
                 if(!isNew){
                          out.println("</div>");
                      }
            %>
        </form>
    </div>
<div class="clear">&nbsp;</div> 
