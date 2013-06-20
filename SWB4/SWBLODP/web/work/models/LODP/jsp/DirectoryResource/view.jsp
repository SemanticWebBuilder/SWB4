<%-- 
    Document   : view
    Created on : 23/05/2013, 11:04:43 AM
    Author     : Lennin
--%>
<%@page import="com.infotec.lodp.swb.Application"%>
<%@page import="com.infotec.lodp.swb.Dataset"%>
<%@page import="java.util.regex.Pattern"%>
<%@page import="org.semanticwb.SWBPlatform"%>
<%@page import="org.semanticwb.platform.SemanticOntology"%>
<%@page import="org.semanticwb.model.GenericObject"%>
<%@page import="java.util.HashMap"%>
<%@page import="org.semanticwb.model.WebPage"%>
<%@page import="org.semanticwb.model.WebSite"%>
<%@page import="com.infotec.lodp.swb.Institution"%>
<%@page import="java.util.Iterator"%>
<%@page import="org.semanticwb.model.Resource"%> 
<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>

<!DOCTYPE html>
<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest" />

<%
    WebPage wpage = paramRequest.getWebPage();
    WebSite wsite = wpage.getWebSite();
    Resource base = paramRequest.getResourceBase();
    SemanticOntology ont = SWBPlatform.getSemanticMgr().getOntology();
    SWBResourceURL urlConsulta = paramRequest.getRenderUrl();
    
    Iterator<Institution> institutionList = Institution.ClassMgr.listInstitutions(wsite);
    Iterator<Institution> itInst = null;

    String npage = request.getParameter("page");
    String strNumItems = base.getAttribute("numpag", "10");
    String action = request.getParameter("act");
    String valor = request.getParameter("val");
    String otro = request.getParameter("otro");
    String txtTitleSearch = paramRequest.getLocaleString("lbl_busquedaPB");
    
    System.out.println("action" + action);
    
    int numPages = 10;
    try {
        numPages = Integer.parseInt(strNumItems);
    } catch (Exception e) {
        numPages = 10;
    }
    
    long intSize = 0;

    if(action == null){
       action = ""; 
    }  
    
    %>
    
    <div class="tab">
        <%if(action != null && action.equals("arregloLetras")){%>
        
         <h4><a class="select"  href="<%=urlConsulta.setParameter("act", "arregloLetras").toString()%>"><%=paramRequest.getLocaleString("lbl_tituloListaPub")%></a></h4>
        
       <%}else{%>
       
        <h4><a href="<%=urlConsulta.setParameter("act", "arregloLetras").toString()%>"><%=paramRequest.getLocaleString("lbl_tituloListaPub")%></a></h4>
        
        <%}%>
        
         <%if(action != null && action.equals("busquedaPublicador")){%>
        
         <h4><a class="select" href="<%=urlConsulta.setParameter("act", "busquedaPublicador").toString()%>"><%=paramRequest.getLocaleString("lbl_titleBusqueda")%></a></h4>
        
       <%}else{%>
        
       <h4><a href="<%=urlConsulta.setParameter("act", "busquedaPublicador").toString()%>"><%=paramRequest.getLocaleString("lbl_titleBusqueda")%></a></h4>
    
       <%}%>
    
    </div>
    

    <%

    String queryinput = request.getParameter("search");

    String queryOriginal = queryinput != null ? queryinput : paramRequest.getLocaleString("lbl_busquedaPB");
    if (null == queryinput) {
        queryinput = "";
    } else {
        queryinput = queryinput.trim().toLowerCase();
        txtTitleSearch = paramRequest.getLocaleString("lbl_busquedaPB");
    }

    // Se guardan ds que coinciden con la búsqueda
    HashMap<String, Institution> hmresults = new HashMap<String, Institution>();

    // revisar DS si hay texto abuscar
    if (queryinput != null && queryinput.trim().length() > 0) {
        
        queryinput = queryinput.replaceAll(",", " ");  // si la búsqueda viene separado por comas, las cambio por espacios en blanco
        String REGEX = "\\s";  //espacio en blanco
        Pattern p = Pattern.compile(REGEX);
        String[] arrKeys = p.split(queryinput); // se separa query por los espacios en blanco

        //Se guardan lista de palabras puestas en la búsqueda
        HashMap<String, String> hmquery = new HashMap();

        // para una única palabra
        if (arrKeys == null && queryinput != null && queryinput.trim().length() > 0) {
            hmquery.put(queryinput, queryinput);
        } else {  // existen mas de una palabra en el queryinput
            for (int j = 0; j < arrKeys.length; j++) {
                String s = arrKeys[j];
                
                if (s != null && s.trim().length() > 0) {
                    if (hmquery.get(s) == null) {
                        hmquery.put(s, s);
                    }
                }
            }
        }
        
        while(institutionList.hasNext()){
            Institution ins = institutionList.next();
            StringBuilder txtAP = new StringBuilder(ins.getInstitutionTitle() != null ? ins.getInstitutionTitle().trim() : "");
            txtAP.append(" ");
            txtAP.append(ins.getInstitutionDescription() != null ? ins.getInstitutionDescription().trim() : "");
            String reviewTXT = txtAP.toString().trim().toLowerCase(); // texto completo en donde se buscará la ocurrencia
           
            if ((reviewQuery(hmquery, reviewTXT)) && hmresults.get(ins.getURI()) == null) {  //||txtAuto.indexOf(queryinput)>-1 
                hmresults.put(ins.getURI(), ins);
            }
        }
        
            if(hmresults.size() > 0 ){
                intSize = hmresults.size();
            }else{
                intSize = 0;
            }
        
        itInst = hmresults.values().iterator();  // pone en el iterador las instituciones obtenidas obtenidas
        }
    
   

%>


<%
    if (action.equals("arregloLetras")) {
        urlConsulta.setParameter("act", "arregloLetras");    
%>

<div id="directorio">
    <div id="abcde">
        
       <%if(valor != null && valor.equals("A")){%>
        
        <span><%=valor%></span>
        
       <%}else{%>
            
        <a href="<%=urlConsulta.setParameter("val", "A").toString() %>"><%=paramRequest.getLocaleString("lbl_A")%></a>
            
       <%}%>
        
       <%if(valor != null && valor.equals("B")){%>
        
        <span><%=valor%></span>
        
       <%}else{%>
            
        <a href="<%=urlConsulta.setParameter("val", "B").toString() %>"><%=paramRequest.getLocaleString("lbl_B")%></a> 
            
       <%}%>
        
       <%if(valor != null && valor.equals("C")){%>
        
        <span><%=valor%></span>
        
       <%}else{%>
       
        <a href="<%=urlConsulta.setParameter("val", "C").toString() %>"><%=paramRequest.getLocaleString("lbl_C")%></a>
        
        <%}%>
        
        <%if(valor != null && valor.equals("D")){%>
        
        <span><%=valor%></span>
        
        <%}else{%>
        
        <a href="<%=urlConsulta.setParameter("val", "D").toString() %>"><%=paramRequest.getLocaleString("lbl_D")%></a>
        
        <%}%>
        <%if(valor != null && valor.equals("E")){%>
        
        <span><%=valor%></span>
        
        <%}else{%>
         
        <a href="<%=urlConsulta.setParameter("val", "E").toString() %>"><%=paramRequest.getLocaleString("lbl_E")%></a>
        
        <%}%>
        <%if(valor != null && valor.equals("F")){%>
        
        <span><%=valor%></span>
        
        <%}else{%>
        
        <a href="<%=urlConsulta.setParameter("val", "F").toString() %>"><%=paramRequest.getLocaleString("lbl_F")%></a>
        
        <%}%>
        <%if(valor != null && valor.equals("G")){%>
        
        <span><%=valor%></span>
        
        <%}else{%>
        
        <a href="<%=urlConsulta.setParameter("val", "G").toString() %>"><%=paramRequest.getLocaleString("lbl_G")%></a>
        
        <%}%>
        <%if(valor != null && valor.equals("H")){%>
        
        <span><%=valor%></span>
        
        <%}else{%>
        
        <a href="<%=urlConsulta.setParameter("val", "H").toString() %>"><%=paramRequest.getLocaleString("lbl_H")%></a>
        
        <%}%>
        <%if(valor != null && valor.equals("I")){%>
        
        <span><%=valor%></span>
        
        <%}else{%>
        
        <a href="<%=urlConsulta.setParameter("val", "I").toString() %>"><%=paramRequest.getLocaleString("lbl_I")%></a>
        
        <%}%>
        <%if(valor != null && valor.equals("J")){%>
        
        <span><%=valor%></span>
        
        <%}else{%>
        
        <a href="<%=urlConsulta.setParameter("val", "J").toString() %>"><%=paramRequest.getLocaleString("lbl_J")%></a>
        
        <%}%>
        <%if(valor != null && valor.equals("K")){%>
        
        <span><%=valor%></span>
        
        <%}else{%>
        
        <a href="<%=urlConsulta.setParameter("val", "K").toString() %>"><%=paramRequest.getLocaleString("lbl_K")%></a>
        
        <%}%>
        <%if(valor != null && valor.equals("L")){%>
        
        <span><%=valor%></span>
        
        <%}else{%>
        
        <a href="<%=urlConsulta.setParameter("val", "L").toString() %>"><%=paramRequest.getLocaleString("lbl_L")%></a>
        
        <%}%>
        <%if(valor != null && valor.equals("M")){%>
        
        <span><%=valor%></span>
        
        <%}else{%>
        
        <a href="<%=urlConsulta.setParameter("val", "M").toString() %>"><%=paramRequest.getLocaleString("lbl_M")%></a>
        
        <%}%>
        <%if(valor != null && valor.equals("N")){%>
        
        <span><%=valor%></span>
        
        <%}else{%>
        
        <a href="<%=urlConsulta.setParameter("val", "N").toString() %>"><%=paramRequest.getLocaleString("lbl_N")%></a>
        
        <%}%>
        <%if(valor != null && valor.equals("O")){%>
        
        <span><%=valor%></span>
        
        <%}else{%>
        
        <a href="<%=urlConsulta.setParameter("val", "O").toString() %>"><%=paramRequest.getLocaleString("lbl_O")%></a>
        
        <%}%>
        <%if(valor != null && valor.equals("P")){%>
        
        <span><%=valor%></span>
        
        <%}else{%>
        
        <a href="<%=urlConsulta.setParameter("val", "P").toString() %>"><%=paramRequest.getLocaleString("lbl_P")%></a>
        
        <%}%>
        <%if(valor != null && valor.equals("Q")){%>
        
        <span><%=valor%></span>
        
        <%}else{%>
        
        <a href="<%=urlConsulta.setParameter("val", "Q").toString() %>"><%=paramRequest.getLocaleString("lbl_Q")%></a>
        
        <%}%>
        <%if(valor != null && valor.equals("R")){%>
        
        <span><%=valor%></span>
        
        <%}else{%>
        
        <a href="<%=urlConsulta.setParameter("val", "R").toString() %>"><%=paramRequest.getLocaleString("lbl_R")%></a>
        
        <%}%>
        <%if(valor != null && valor.equals("S")){%>
        
        <span><%=valor%></span>
        
        <%}else{%>
        
        <a href="<%=urlConsulta.setParameter("val", "S").toString() %>"><%=paramRequest.getLocaleString("lbl_S")%></a>
        
        <%}%>
        <%if(valor != null && valor.equals("T")){%>
        
        <span><%=valor%></span>
        
        <%}else{%>
        
        <a href="<%=urlConsulta.setParameter("val", "T").toString() %>"><%=paramRequest.getLocaleString("lbl_T")%></a>
        
        <%}%>
        <%if(valor != null && valor.equals("U")){%>
        
        <span><%=valor%></span>
        
        <%}else{%>
        
        <a href="<%=urlConsulta.setParameter("val", "U").toString() %>"><%=paramRequest.getLocaleString("lbl_U")%></a>
        
        <%}%>
        <%if(valor != null && valor.equals("V")){%>
        
        <span><%=valor%></span>
        
        <%}else{%>
        
        <a href="<%=urlConsulta.setParameter("val", "V").toString() %>"><%=paramRequest.getLocaleString("lbl_V")%></a>
        
        <%}%>
        <%if(valor != null && valor.equals("W")){%>
        
        <span><%=valor%></span>
        
        <%}else{%>
        
        <a href="<%=urlConsulta.setParameter("val", "W").toString() %>"><%=paramRequest.getLocaleString("lbl_W")%></a>
        
        <%}%>
        <%if(valor != null && valor.equals("X")){%>
        
        <span><%=valor%></span>
        
        <%}else{%>
        
        <a href="<%=urlConsulta.setParameter("val", "X").toString() %>"><%=paramRequest.getLocaleString("lbl_X")%></a>
        
        <%}%>
        <%if(valor != null && valor.equals("Y")){%>
        
        <span><%=valor%></span>
        
        <%}else{%>
        
        <a href="<%=urlConsulta.setParameter("val", "Y").toString() %>"><%=paramRequest.getLocaleString("lbl_Y")%></a>
        
        <%}%>
        <%if(valor != null && valor.equals("Z")){%>
        
        <span><%=valor%></span>
        
        <%}else{%>
        
        <a href="<%=urlConsulta.setParameter("val", "Z").toString() %>"><%=paramRequest.getLocaleString("lbl_Z")%></a>
        
        <%}%>
        <%if(valor != null && valor.equals("")){%>
        
        <span><%=paramRequest.getLocaleString("lbl_otro")%></span>
        
        <%}else{%>
        
        <a href="<%=urlConsulta.setParameter("val", "").toString() %>"><%=paramRequest.getLocaleString("lbl_otro")%></a>
        
        <%}%>
    </div>
        
    <div id="listapub">
           
        <%

       if(valor != null){

           Iterator<Institution> listInst = Institution.ClassMgr.listInstitutions(wsite);
           HashMap<String, Institution> hmInst = new HashMap<String, Institution>();
           String letra= "";
           Iterator<Institution> genericListInst  = null;
            
           if(!(valor.equals(""))){

                while(listInst.hasNext()){
                    Institution ints = listInst.next();

                    if(ints.getInstitutionTitle().startsWith(valor)){
                        hmInst.put(ints.getURI(), ints);
                    }                      
                }           
                
            }else {

                while(listInst.hasNext()){
                    Institution ints = listInst.next();
                    if(!(ints.getInstitutionTitle().startsWith("A") ||
                         ints.getInstitutionTitle().startsWith("B") ||
                         ints.getInstitutionTitle().startsWith("C") ||
                         ints.getInstitutionTitle().startsWith("D") ||
                         ints.getInstitutionTitle().startsWith("E") ||
                         ints.getInstitutionTitle().startsWith("F") ||
                         ints.getInstitutionTitle().startsWith("G") ||
                         ints.getInstitutionTitle().startsWith("H") ||
                         ints.getInstitutionTitle().startsWith("I") ||
                         ints.getInstitutionTitle().startsWith("J") ||
                         ints.getInstitutionTitle().startsWith("K") ||
                         ints.getInstitutionTitle().startsWith("L") ||
                         ints.getInstitutionTitle().startsWith("M") ||
                         ints.getInstitutionTitle().startsWith("N") ||
                         ints.getInstitutionTitle().startsWith("O") ||
                         ints.getInstitutionTitle().startsWith("P") ||
                         ints.getInstitutionTitle().startsWith("Q") ||
                         ints.getInstitutionTitle().startsWith("R") ||
                         ints.getInstitutionTitle().startsWith("S") ||
                         ints.getInstitutionTitle().startsWith("T") ||
                         ints.getInstitutionTitle().startsWith("U") ||
                         ints.getInstitutionTitle().startsWith("V") ||
                         ints.getInstitutionTitle().startsWith("W") ||
                         ints.getInstitutionTitle().startsWith("X") ||
                         ints.getInstitutionTitle().startsWith("Y") ||
                         ints.getInstitutionTitle().startsWith("Z"))){
                        hmInst.put(ints.getURI(), ints);
                    }                      
                }
            }
           intSize = hmInst.size();
           genericListInst = hmInst.values().iterator();
           
           //if(!valor.equals("")){
           
        %>
<!--            <h3>
               <%//=paramRequest.getLocaleString("lbl_pbInicio")%> "<%//=valor%> "
            </h3>   -->
        <%
          // } else {
        %>
<!--            <h3>
               <%//=paramRequest.getLocaleString("lbl_otroPB")%>
            </h3> -->
        <%
               
           //}

           //PAGINACION
            int ps = numPages;
            long l = intSize;

            int p = 0;

            if (npage != null) {
                p = Integer.parseInt(npage);
            }
            int x = 0;
           
           if(genericListInst.hasNext()){
              while(genericListInst.hasNext()){
                  
//                    //PAGINACION ////////////////////
                    if (x < p * ps) {
                        x++;
                        continue;
                    }
                    if (x == (p * ps + ps) || x == l) {
                        break;
                    }
                    x++;
//                    /////////////////////////////////
                  
                Institution liInst  = genericListInst.next();
                SWBResourceURL urlDetail = paramRequest.getRenderUrl();
                urlDetail.setParameter("act", "detail");
                urlDetail.setParameter("suri", liInst.getURI());
        %>
        
        <ul>
            <li><a href="<%=urlDetail.setMode("DETAIL").toString()%>"><%=liInst.getInstitutionTitle()%></a></li>
        </ul>
        
        <%}}else{%>
        
        <ul>
            <li><%=paramRequest.getLocaleString("lbl_noEncontrado")%></li>
        </ul>
        
        <%}%>
   </div>
        
   <div class="pager">
    <p>
        <%
            if (p > 0 || x < l) //Requiere paginacion
            {

                int pages = (int) (l / ps);
                if ((l % ps) > 0) {
                    pages++;
                }
%>
    <div class="pager-total">Página <%=p%>/<%=pages%></div>
    <div class="pager-index">
<%

                //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

                int inicia = 0;
                int finaliza = pages;
                int rangoinicial = p - 5;
                int rangofinal = p + 5;
                if (pages <= 10) {
                    inicia = 0;
                    finaliza = pages;
                } else {
                    if (rangoinicial < 0) {
                        inicia = 0;
                        finaliza = Math.abs(rangoinicial) + rangofinal;
                    } else if (rangofinal > pages) {
                        inicia = pages - 10;
                        finaliza = pages;
                    } else {
                        inicia = rangoinicial;
                        finaliza = rangofinal;
                    }
                }

                if (pages > 10) {
                    SWBResourceURL urlNext = paramRequest.getRenderUrl();
                    urlNext.setParameter("page", "" + 0);
                    if (null != valor) {
                        urlNext.setParameter("val", valor);
                    }
                    if (null != otro) {
                        urlNext.setParameter("otro", otro);
                    }
                    if (null != action) {
                        urlNext.setParameter("act", action);
                    }
                    if (queryinput != null && queryinput.trim().length() > 0) {
                        urlNext.setParameter("search", queryinput);
                    }

                    out.println("<a href=\"#\" onclick=\"window.location='" + urlNext + "';\">"+paramRequest.getLocaleString("pg_gofirst")+"</a> ");
                }

                for (int z = inicia; z < finaliza; z++) {
                    SWBResourceURL urlNext = paramRequest.getRenderUrl();
                    urlNext.setParameter("page", "" + z);
                   if (null != valor) {
                        urlNext.setParameter("val", valor);
                    }
                    if (null != otro) {
                        urlNext.setParameter("otro", otro);
                    }
                    if (null != action) {
                        urlNext.setParameter("act", action);
                    }
                    if (queryinput != null && queryinput.trim().length() > 0) {
                        urlNext.setParameter("search", queryinput);
                    }
                    if (z != p) {
                        out.println("<a href=\"#\" onclick=\"window.location='" + urlNext + "';\">" + (z + 1) + "</a> ");
                    } else {
                        out.println((z + 1) + " ");
                    }

                }
                if (pages > 10) {
                    SWBResourceURL urlNext = paramRequest.getRenderUrl();
                    urlNext.setParameter("page", "" + (pages - 1));
                    if (null != valor) {
                        urlNext.setParameter("val", valor);
                    }
                    if (null != otro) {
                        urlNext.setParameter("otro", otro);
                    }
                    if (null != action) {
                        urlNext.setParameter("act", action);
                    }
                    if (queryinput != null && queryinput.trim().length() > 0) {
                        urlNext.setParameter("search", queryinput);
                    }
                    out.println("<a href=\"#\" onclick=\"window.location='" + urlNext + "';\">"+paramRequest.getLocaleString("pg_golast")+"</a> ");
                }
            }
        %>
    </div>
</div>

<%}}else if (action.equals("busquedaPublicador")) {%>
   
  <div class="buscar_inst">
    <form method="post" action="" id="ints_search">
        <%--
            if (filterby != null && filterby.trim().length() > 0) {
        --%>
        <!--<input type="hidden" name="filter" value="<%--=filterby--%>"/>-->
        <%--
            }
        --%>
        <label for="txt_search"><%=txtTitleSearch%></label>
        <input type="text" name="search" value="<%=queryOriginal%>" onfocus="if (this.value == '<%=paramRequest.getLocaleString("lbl_busquedaPB")%>') {this.value = ''};">
        <button type="submit"><%=paramRequest.getLocaleString("lbl_busquedaPB")%></button>
      </form>
            <%
                    // si hubo búsqueda
                if (null != queryinput && queryinput.trim().length() > 0) {
                        if (intSize > 0) {
            %>
                    <br/>
                      <%=paramRequest.getLocaleString("lbl_searchPub")%> <%=intSize%> <%=paramRequest.getLocaleString("lbl_busPB")%>
            <%
                } else {
            %>
                    <br/>
                    <%=paramRequest.getLocaleString("lbl_sinResultadosPB")%>
            <%     
                }
                }
             if(itInst!=null){
               while(itInst.hasNext()){
                Institution itIn  = itInst.next();
                SWBResourceURL urlDetailBusqueda = paramRequest.getRenderUrl();
                urlDetailBusqueda.setParameter("act", "detail");
                urlDetailBusqueda.setParameter("suri", itIn.getURI());
                    
            %>        
         
         <ul> 
            <li>
                <a href="<%=urlDetailBusqueda.setMode("DETAIL").toString()%>"><%=itIn.getInstitutionTitle()%></a>
            </li>
        </ul>
            
            <%
                }}
            %>
        
    
</div>

<%}%>
            
<%!
    public boolean reviewQuery(HashMap<String, String> hm, String texto) {
        boolean res = Boolean.FALSE;

        if (null != hm) {
            Iterator<String> itstr = hm.keySet().iterator();
            while (itstr.hasNext()) {
                String skey = itstr.next();

                if (texto.indexOf(skey) > -1) {
                    res = Boolean.TRUE;
                    break;
                }
            }
        }
        return res;
    }

%>

