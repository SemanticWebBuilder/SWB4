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
    String contextPath = SWBPlatform.getContextPath();
    String context = SWBPortal.getContextPath();
 
    String repositoryId = wpage.getWebSite().getUserRepository().getId();
    String suri = request.getParameter("suri");
    
    //System.out.println("URI:"+suri);
    
    String action = request.getParameter("act");
    if(null==action) action="";
    if (null==suri) {
        suri = "";
    }
    
    
    
    
    //12 Jun 2013, 11:35
    //SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy, h:mm a", new Locale("es"));
    //12 jun 2013
    //SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy", new Locale("es"));
    //12/junio/2013
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MMMMM/yyyy h:mm a", new Locale("es"));
    boolean isNew = Boolean.TRUE;
    SemanticOntology ont = SWBPlatform.getSemanticMgr().getOntology();
    SemanticObject so = null;
    GenericObject go = null;
    
    suri = SemanticObject.shortToFullURI(suri);
    
    Dataset ds = null;
    go = ont.getGenericObject(suri);
    if(go!=null&& go instanceof Dataset){
        ds = (Dataset)go;
        // cargar información del Dataset
        action = SWBResourceURL.Action_EDIT;
        isNew = Boolean.FALSE;
    } 
    
    String dstitle = "";
    String dsdescription = "";
    String dsemail = "";
    String dscreated = "";
    String dsupdated = "";
    String dsformat = "";
    String dsversion = "";
    String dspubname= "";
    String dstoplevelname = "";
    String dswebsite = "";
    String dsurl = "";
    
    boolean isActive = Boolean.FALSE;
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
        
        DatasetVersion dsver = ds.getActualVersion();
        dsversion = null!=dsver?""+dsver.getVersion():"0"; 
        
        
        dspubname=pub_ds!=null&&pub_ds.getFullName()!=null?pub_ds.getFullName():"---";
        
        dstoplevelname=ds.getInstitution()!=null&&ds.getInstitution().getTopLevelName()!=null?ds.getInstitution().getTopLevelName():"---";
        
        dsurl = ds.getDatasetURL();
        dswebsite = ds.getInstitution()!=null&&ds.getInstitution().getInstitutionHome()!=null?ds.getInstitution().getInstitutionHome():"";
        
        isApproved = ds.isApproved();
        isActive = ds.isDatasetActive();
        isReviewed = ds.isReviewed();
    }

    if (request.getParameter("msg") != null) {
        String strMsg = request.getParameter("msg");
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
    dojo.require("dijit.form.MultiSelect");
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
                alert('<%//=paramRequest.getLocaleString("promptMsgCaptcha")%>');
            }else{
                if (!validateReadAgree()){
                    alert('<%//=paramRequest.getLocaleString("msgErrAgreement")%>');
                }else{
                   return true;
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
        return ret;
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
                    email.displayMessage( "<%//=paramRequest.getLocaleString("lblEmailDupl")%>" );
                }
            }else{
                email.displayMessage( "<%//=paramRequest.getLocaleString("lblEmailFault")%>" );
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
            if(filter.test(strLogin)&&strLogin.length>2){
                if(canAddLogin('<%=repositoryId%>',strLogin)){
                    valid=true;
                }else{
                    login.displayMessage( "<%//=paramRequest.getLocaleString("lblLoginDupl")%>" );
                }
            }else{
                login.displayMessage( "<%//=paramRequest.getLocaleString("lblLoginFault")%>" );
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
    <div>
        <%
            SWBResourceURL url = paramRequest.getActionUrl();
            if(!isNew){
                url.setAction(SWBResourceURL.Action_ADD);
            } else {
                url.setAction(SWBResourceURL.Action_EDIT);
            }
        %>
        <form id="form1newDS" dojoType="dijit.form.Form" class="swbform" action="<%=url.toString()%>" method="post">
            <%
            if(!isNew){
                %>
                <input type="hidden" name="suri" value="<%=ds.getEncodedURI()%>"/>
                <%
            }
        %>
            <div>
                <p>
                    <label for="dstitle"><b>*</b><%=paramRequest.getLocaleString("lbl_title")%></label>
                    <input type="text" name="dstitle" id="dstitle" dojoType="dijit.form.ValidationTextBox" value="<%=dstitle%>" required="true"  invalidMessage="<%=paramRequest.getLocaleString("lbl_titlemissing")%>" trim="true" _regExp="[a-zA-Z\u00C0-\u00FF' ]+" />
                </p>
                <p>
                    <label for="dsdescription"><%=paramRequest.getLocaleString("lbl_description")%></label>
                    <textarea name="dsdescription" id="dsdescription" dojoType="dijit.form.Textarea" value="<%=dsdescription%>" required="false" invalidMessage="<%=paramRequest.getLocaleString("lbl_descriptionmissing")%>" trim="true" rows="20" cols="50"><%=dsdescription%></textarea>
                </p>
             <!-- Etiquetas -->
                <p>
                    <label for="dslabels"><b>*</b><%=paramRequest.getLocaleString("lbl_labels")%></label>
                    <select multiple size="5" id="dslabels" dojoType="dijit.form.MultiSelect" name="dslabels" required="true" invalidMessage="<%=paramRequest.getLocaleString("lbl_tagmissing")%>"  _isValid="return isValidThisEmail()" >
                              <option value=""> </option>
                    <%
                        String selected = "";
                      Iterator<Tag> ittag = Tag.ClassMgr.listTags(wsite);
                      while(ittag.hasNext()){  
                          Tag tag = ittag.next();
                          selected = "";
                          if(SWBResourceURL.Action_EDIT.equals(action) && null!=ds && ds.hasTag(tag) ){
                              selected = "selected";
                          }   
                    %>
                    <option value="<%=tag.getEncodedURI()%>" <%=selected%>><%=tag.getTagName()%> </option>
                    <%
                      }
                    %>
                    </select>
                </p>
                <%
                      if(!isNew)
                      {
                %>
                <p>
                    <label for="dsformat"><b>*</b><%=paramRequest.getLocaleString("lbl_format")%></label>
                    <input type="text" name="dsformat" id="dsformat" dojoType="dijit.form.ValidationTextBox" value="<%=dsformat%>" maxlength="60" required="true" _invalidMessage="<%//=paramRequest.getLocaleString("lblFormatMissing")%>"  readonly="true"/>
                </p>
                <p>
                    <label for="dsversion"><b>*</b><%=paramRequest.getLocaleString("lbl_version")%></label>
                    <input type="text" name="dsversion" id="dsversion" dojoType="dijit.form.ValidationTextBox" value="<%=dsversion%>" maxlength="18" required="true" _invalidMessage="<%//=paramRequest.getLocaleString("lbl_versionMissing")%>"  readonly="true" />
                </p>
                <%
                }
                %>
                <p>
                    <label for="dstopic"><b>*</b><%=paramRequest.getLocaleString("lbl_topic")%></label>
                    <select multiple size="5" id="dstopic" dojoType="dijit.form.MultiSelect" name="dstopic" required="true" invalidMessage="<%=paramRequest.getLocaleString("lbl_topicmissing")%>"  >
                              <option value=""> </option>
                            <%

                        selected="";
                    Iterator<Topic> ittopic = Topic.ClassMgr.listTopics(wsite);  
                    while(ittopic.hasNext()){
                        Topic topic = ittopic.next();
                        if(null!=ds&&ds.hasTopic(topic)){
                            selected="selected";
                        }
                        
                    %>   
                    
                    <option value="<%=topic.getURI()%>" title="<%=topic.getTopicDescription()!=null?topic.getTopicDescription():topic.getTopicTitle()%>" <%=selected%>><%=topic.getTopicTitle()%> </option>
                    <%
                        selected="";
                }
                    %>
                    </select>
                </p>
                    <p>
                    <label for="dslicense"><b>*</b><%=paramRequest.getLocaleString("lbl_license")%></label>
                    <select multiple size="5" id="dslicense" dojoType="dijit.form.FilteringSelect" name="dslicense" required="true" invalidMessage="<%=paramRequest.getLocaleString("lbl_licensemissing")%>"  _isValid="return isValidThisEmail()" >
                              <option value=""> </option>
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
                    <option value="<%=ltype.getURI()%>" title="<%=ltype.getLicenseDescription()!=null?ltype.getLicenseDescription():ltype.getLicenseTitle()%>" <%=selected%>><%=ltype.getLicenseTitle()%> </option>
                <%
                        selected="";
                }
                    %>
                    </select>
                    </p>
                    <p>
                    <label for="dstoplevelname"><b>*</b><%=paramRequest.getLocaleString("lbl_toplevellink")%></label>
                    <input type="text" name="dstoplevelname" id="dstoplevelname" dojoType="dijit.form.ValidationTextBox" value="<%=dstoplevelname%>" required="true" invalidMessage="<%=paramRequest.getLocaleString("lbl_toplevellinkmissing")%>"  />
                </p>
                    <%
                if(!isNew){
                %>
                <p>
                    <label for="dscreator"><b>*</b><%=paramRequest.getLocaleString("lbl_creator")%></label>
                    <input type="text" name="dscreator" id="dscreator" dojoType="dijit.form.ValidationTextBox" value="<%=dspubname%>" readonly="true" />
                </p>
                <p>
                    <label for="dsemail"><b>*</b><%=paramRequest.getLocaleString("lbl_email")%></label>
                    <input type="text" name="dsemail" id="dscreator" dojoType="dijit.form.ValidationTextBox" value="<%=dsemail%>" readonly="true" />
                </p>
                <p>
                    <label for="dscreated"><b>*</b><%=paramRequest.getLocaleString("lbl_created")%></label>
                    <input type="text" name="dscreated" id="dscreated" dojoType="dijit.form.ValidationTextBox" value="<%=dscreated%>" readonly="true" />
                </p>
                <p>
                    <label for="dsupdated"><b>*</b><%=paramRequest.getLocaleString("lbl_updated")%></label>
                    <input type="text" name="dsupdated" id="dsupdated" dojoType="dijit.form.ValidationTextBox" value="<%=dsupdated%>" readonly="true" />
                </p>
                <p>
                    <label for="dsurl"><b>*</b><%=paramRequest.getLocaleString("lbl_urlendpoint")%></label>
                    <input type="text" name="dsurl" id="dsurl" dojoType="dijit.form.ValidationTextBox" value="<%=dsurl%>" maxlength="12" readonly="true" />
                </p>
                <%
                }
                %>
                <p>
                    <label for="dspuburl"><b>*</b><%=paramRequest.getLocaleString("lbl_weburl")%></label>
                    <input type="text" name="dspuburl" id="login" dojoType="dijit.form.ValidationTextBox" value="<%=dswebsite%>" maxlength="18" required="true" invalidMessage="<%=paramRequest.getLocaleString("lbl_puburlmissing")%>"  isValid="return isValidLogin()" trim="true" />
                </p>
                
            </div>

            <div class="centro">
                <input type="reset" value="<%=paramRequest.getLocaleString("lblReset")%>"/>
                <input type="submit" onclick="return enviar()" value="<%=paramRequest.getLocaleString("lblSubmit")%>"/>
            </div>
        </form>
    </div>

