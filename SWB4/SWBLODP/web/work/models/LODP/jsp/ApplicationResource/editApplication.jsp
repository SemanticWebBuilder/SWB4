<%-- 
    Document   : editApplication
    Created on : 9/05/2013, 10:18:46 AM
    Author     : Lennin
--%>

<%@page import="org.semanticwb.model.GenericObject"%>
<%@page import="com.infotec.lodp.swb.utils.LODPUtils"%>
<%@page import="com.infotec.lodp.swb.Category"%>
<%@page import="com.infotec.lodp.swb.Publisher"%>
<%@page import="com.infotec.lodp.swb.Developer"%>
<%@page import="com.infotec.lodp.swb.Dataset"%>
<%@page import="com.infotec.lodp.swb.Application"%>
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
    
    String uri = request.getParameter("uri");
    SemanticObject semObj = SemanticObject.createSemanticObject(URLDecoder.decode(SemanticObject.shortToFullURI(uri)));
    Application apl = (Application)semObj.createGenericInstance();
    User user = paramRequest.getUser();
    WebPage wpage = paramRequest.getWebPage();
    WebSite wsite = wpage.getWebSite();
    Iterator<Category> itCat = Category.ClassMgr.listCategories(wsite);
    String idCat = "";
    String descCat = "";
    String ciudadanas = "Ciudadanas";
    String servPub = "Servicio Publico";
    
    GenericObject ob = apl.getAppAuthor().createGenericInstance();
    String fullName = "";
            
    if(ob instanceof Developer  ){
        Developer db = (Developer)ob ;
        fullName = db.getFullName();
    }
    
    if(ob instanceof Publisher){
        Publisher db = (Publisher)ob ;
        fullName = db.getFullName();
    }
    
    Publisher pub = LODPUtils.getPublisher(user);
    Developer dev = LODPUtils.getDeveloper(user);
    
    while(itCat.hasNext()){
        Category cat = itCat.next();
        
        if(cat.getCatName()!=null){
        
            if (pub != null) {
                if(cat.getCatName().equals(servPub)){
                    descCat = cat.getCatName();
                    idCat = cat.getId();
                }
            }

            if (dev != null) {
               if(cat.getCatName().equals(ciudadanas)){
                    descCat = cat.getCatName();
                    idCat = cat.getId();
                }
            }
        }
    }
    
  
 %>
<p>
    <h1>
       <%=paramRequest.getLocaleString("lbl_editAPP")%>
     </h1>
 </p>
        
        <form id="nuevoContacto" action="<%=actionURL.setAction(SWBResourceURL.Action_EDIT).setParameter("uri", apl.getEncodedURI())%>" method="post">
            <div>
                <p>
                    <label><b>*</b><%=paramRequest.getLocaleString("lbl_appTitulo")%></label>
                    <input type="text" name="titleApp" value="<%=apl.getAppTitle()%>"/>
                </p>
                <p>
                    <label><b>*</b><%=paramRequest.getLocaleString("lbl_appDescripcion")%></label>
                    <textarea name="descripcion" id="descripcion"><%=apl.getAppDescription()%></textarea>
                </p>
                <p>
                    <label><b>*</b><%=paramRequest.getLocaleString("lbl_category")%></label>
                    <input type="text" name="category" disabled="true" value="<%=descCat%>"/>
                    <input type="hidden" name="idCat" value="<%=idCat%>"/>
                </p>
                <p>
                    <label><b>*</b><%=paramRequest.getLocaleString("lbl_appDS")%></label>
                    <select name="dataSet" dojoType="dijit.form.FilteringSelect">
                        <option value="-1">Selecciona....</option>
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
                                    String selectedDS = "";
                                    if(apl.getRelatedDataset().getId().equals(lic.getId())){
                                        selectedDS = "selected";
                                    }
                            %>
                        <option value="<%=lic.getId()%>" <%=selectedDS%>><%=lic.getDatasetTitle()%></option>
                            <%
                                }
                            %>
                    </select>

                </p>
            </div>
            <div>
                <p>
                    <label><b>*</b><%=paramRequest.getLocaleString("lbl_appAutor")%></label>
                    <input type="text" name="usuario" disabled="true" value="<%=fullName%>"/>
                </p>
                <p>
                    <label><b>*</b><%=paramRequest.getLocaleString("lbl_appLicencia")%></label>
                    <select name="licencia" dojoType="dijit.form.FilteringSelect">
                        <option value="-1">Selecciona....</option>
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
                                    String selectedLic = "";
                                    if(apl.getAppLicense().getId().equals(lic.getId())){
                                        selectedLic = "selected";
                                    }
                            %>
                        <option value="<%=lic.getId()%>" <%=selectedLic%>><%=lic.getLicenseTitle()%></option>
                            <%
                                }
                            %>
                    </select>
                </p>
                <p>
                    <label><b>*</b><%=paramRequest.getLocaleString("lbl_appURL")%></label>
                    <input type="text" name="url" value="<%=apl.getAppURL()%>"/>
                </p>
            </div>
            <div>
                <p>
                   <input type="submit" value="<%=paramRequest.getLocaleString("btn_appActualizar")%>"/>
                   <input type="button" value="<%=paramRequest.getLocaleString("btn_appCancelar")%>" onclick="javascript:document.back.submit()"/> 
                </p>
            </div>
        </form>           
             <a href="<%=renderURL.setMode(SWBResourceURL.Mode_VIEW)%>">
               Regresar
            </a>

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
