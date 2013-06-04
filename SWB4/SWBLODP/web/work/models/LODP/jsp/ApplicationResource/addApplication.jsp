<%-- 
    Document   : addApplication
    Created on : 8/05/2013, 03:34:59 PM
    Author     : Lennin
--%>
<%@page import="com.infotec.lodp.swb.Publisher"%>
<%@page import="com.infotec.lodp.swb.Developer"%>
<%@page import="com.infotec.lodp.swb.resources.ApplicationResource"%>
<%@page import="com.infotec.lodp.swb.Dataset"%>
<%@page import="com.infotec.lodp.swb.resources.DataSetResource"%>
<%@page import="java.net.URLDecoder"%>
<%@page import="org.semanticwb.platform.SemanticObject"%>
<%@page import="org.semanticwb.model.User"%>
<%@page import="org.semanticwb.model.WebSite"%>
<%@page import="org.semanticwb.model.WebPage"%>
<%@page import="java.util.Comparator"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Collections"%>
<%@page import="com.infotec.lodp.swb.LicenseType"%>
<%@page import="java.util.Iterator"%>
<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>
<!DOCTYPE html>
<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest" />


<%
    SWBResourceURL renderURL = paramRequest.getRenderUrl();  //para regresar a la vista principal
    SWBResourceURL actionURL = paramRequest.getActionUrl(); // para rocesar la accion guardar
    String url = actionURL.setAction(SWBResourceURL.Action_ADD).toString();
    String uri = request.getParameter("uri");
    User user = paramRequest.getUser();
    WebPage wpage = paramRequest.getWebPage();
    WebSite wsite = wpage.getWebSite();
    String cancelar = "cancel";
    
    if(null!=user&&user.isSigned()&& (user.getSemanticObject().createGenericInstance() instanceof Developer || user.getSemanticObject().createGenericInstance() instanceof Publisher)){
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
    
    function enviarAPP() {
      
        var ob=dijit.byId('newApplication');
                      
        if(ob.validate()){
            
                if (!validateReadAgree()){
                    alert('Para agregar una aplicacion es necesario aceptar los términos y condiciones');
                }else if(!valTexArea()){
                   alert ("Introduzca caracteres válidos no se aceptan caracteres especiales en la descripcion de la aplicación");
                }else {
                     return true;
                }
                
        return false;
    }
    }
    
    function validateReadAgree(){
        var ret=false;        
//        ret = document.getElementById('terminos').checked;
        ret=dijit.byId("terminos").checked;
        return ret;
    }
    
    function valTexArea (){
        
        var valid = false;
        var desc = dijit.byId( "descripcion" );
        var strDesc = desc.getValue();
        var regExPattern = /^[A-Za-z0-9 áéíóúAÉÍÓÚÑñ' \t\n,'':;.""]*$/;
        
        if(strDesc!=""){
            if( regExPattern.test(strDesc)){   
                 valid=true;
            }
//            else{
//                desc.displayMessage( "<%--=paramRequest.getLocaleString("lbl_textAreaFault")--%>" );
//            }
        }
        
         return valid;
    }
    -->
</script>
 
 
 <p>
    <h1>
       <%=paramRequest.getLocaleString("lbl_tituloSubirApp")%>
     </h1>
 </p>
        
 <form id="newApplication" dojoType="dijit.form.Form" action="<%=url%>" method="post" >
            <div> 
                <p>
                   <label><b>*</b><%=paramRequest.getLocaleString("lbl_appTitulo")%></label>
                   <input type="text" name="titleApp"  dojoType="dijit.form.ValidationTextBox" required="true" promptMessage="<%=paramRequest.getLocaleString("lbl_promtTitleAPP")%>" invalidMessage="<%=paramRequest.getLocaleString("lbl_titleFault")%> " trim="true" regExp="[a-zA-Z\u00C0-\u00FF' ]+" />
                </p>

                <p>
                   <label><b>*</b><%=paramRequest.getLocaleString("lbl_appDescripcion")%></label>
                   <textarea name="descripcion" id="descripcion" dojoType="dijit.form.Textarea" required="true" promptMessage="Ingresa la descripcion de tu aplicacion" invalidMessage="Datos invalidos" trim="true" ></textarea>
                </p>
                
                <p>
                    <label><b>*</b><%=paramRequest.getLocaleString("lbl_appDS")%></label>
                    <select name="dataSet" dojoType="dijit.form.FilteringSelect" required="true">
                        <option value="">Selecciona...</option>
                            <%
                                Iterator<Dataset> itDt = Dataset.ClassMgr.listDatasets(wsite);
                                List<Dataset> dataSet = new ArrayList<Dataset>();
                                while(itDt.hasNext()){
                                    dataSet.add(itDt.next());
                                }
                                Collections.sort(dataSet , new PageComparatorDataSet());
                                itDt=dataSet.iterator();
                                while (itDt.hasNext()) {
                                    Dataset lic = itDt.next();
                            %>
                        <option value="<%=lic.getId()%>"><%=lic.getDatasetTitle()%></option>
                            <%
                                }
                            %>
                    </select>
                </p>               
            </div>            
            <div>
                <p>
                    <label><b>*</b><%=paramRequest.getLocaleString("lbl_appAutor")%></label>
                    <input type="text" name="usuario" disabled="true" value="<%=user.getFullName()%>"/>
                </p>

                <p>
                    <label><b>*</b><%=paramRequest.getLocaleString("lbl_appLicencia")%></label>
                    <select name="licencia" dojoType="dijit.form.FilteringSelect" required="true">
                        <option value="">Selecciona....</option>
                            <%
                                Iterator<LicenseType> itLic = LicenseType.ClassMgr.listLicenseTypes(wsite);
                                List<LicenseType> licArray = new ArrayList<LicenseType>();
                                while(itLic.hasNext()){
                                    licArray.add(itLic.next());
                                }
                                Collections.sort(licArray , new PageComparator());
                                itLic=licArray.iterator();
                                while (itLic.hasNext()) {
                                    LicenseType lic = itLic.next();
                            %>
                        <option value="<%=lic.getId()%>"><%=lic.getLicenseTitle()%></option>
                            <%
                                }
                            %>
                    </select>

                </p>

                <p>                
                    <label><b>*</b><%=paramRequest.getLocaleString("lbl_appURL")%></label>
                    <input type="text" name="url" dojoType="dijit.form.ValidationTextBox" required="true" promptMessage="Ingresa la url de tu aplicacion para descargar" invalidMessage="Url invalida" trim="true" />
                </p>
            </div>
                    
            <div>
                <p class="icv-3col">
                    
                </p>
                <p>
                    <input type="checkbox" name="terminos" id="terminos" maxlength="8" value="true" dojoType="dijit.form.CheckBox" required="true" _promptMessage="<%=paramRequest.getLocaleString("lbl_agreement")%>" invalidMessage="<%=paramRequest.getLocaleString("lbl_agreement")%>" isValid="return confirm('this.checkbox.value==true')"/>
                    <a href="<%=renderURL.setMode(ApplicationResource.MODE_TERMINOS)%>" ><label for="terminos"><%=paramRequest.getLocaleString("lbl_appTerminosLicencia")%></label></a>         
                </p>
            </div>      
                   
            <div>
                <p>
                    <input type="submit" onclick="return enviarAPP()" value="<%=paramRequest.getLocaleString("btn_appGuardar")%>" />
                    <input type="button" value="<%=paramRequest.getLocaleString("btn_appCancelar")%>" onclick="javascript:document.back.submit()"/>
                </p>
            </div>
        </form>
        <form action="<%=actionURL.setMode(SWBResourceURL.Mode_VIEW)%>" method="post" name="back"></form>
            
        <a href="<%=renderURL.setMode(SWBResourceURL.Mode_VIEW).setParameter("cancel", cancelar) %>">
               Regresar
            </a>
               <%}else{ %>
        <%=paramRequest.getLocaleString("lbl_appUserLoggeo")%>
    <%    }%>

<%!
    
    public class PageComparator implements Comparator<LicenseType>{
        public int compare(LicenseType o1, LicenseType o2){
            return o1.getLicenseTitle().compareTo(o2.getLicenseTitle());
        }
    }
    
    public class PageComparatorDataSet implements Comparator<Dataset>{
        public int compare(Dataset o1, Dataset o2){
            return o1.getDatasetTitle().compareTo(o2.getDatasetTitle());
        }
    }
    
    
%>