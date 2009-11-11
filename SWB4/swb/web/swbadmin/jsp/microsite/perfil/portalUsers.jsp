        <jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest"/>
        <%@page import="org.semanticwb.model.User"%>
        <%@page import="org.semanticwb.portal.community.*"%>
        <%@page import="org.semanticwb.model.WebPage"%>
        <%@page import="java.util.*"%>
        <%@page import="org.semanticwb.platform.SemanticObject"%>
        <%@page import="org.semanticwb.portal.api.SWBResourceURL"%>
        <%@page import="org.semanticwb.SWBPortal"%>
        <%@page import="org.semanticwb.SWBUtils"%>
        <%@page import="org.semanticwb.model.*"%>
        <%@page import="org.semanticwb.platform.*"%>



        <%!
        private final int I_PAGE_SIZE = 10;
        private final int I_INIT_PAGE = 1;
        %>
        <%
        WebPage webpage=paramRequest.getWebPage();
        WebSite website=webpage.getWebSite();
        User user=paramRequest.getUser();
        if(!user.isSigned()) return;


        String sparams="";boolean existFilter=false;
        if(request.getParameter("alphabet")!=null) {
            existFilter=true;
            sparams=request.getParameter("alphabet");
        }
        //Empieza paginación
        SWBResourceURL urlPag=paramRequest.getRenderUrl();
        SWBResourceURL urlAlphabetic=paramRequest.getRenderUrl();
        SWBResourceURL urlAction=paramRequest.getActionUrl();
        Set setResult=null;
        Iterator<User> itUsers=website.getUserRepository().listUsers();
        //Si se desea filtrar los elementos por una letra del alpabeto seleccionada
        ArrayList aUsers=new ArrayList();
        while(itUsers.hasNext()){
            User oneUser=(User)itUsers.next();
            if(existFilter && oneUser.getFullName().toLowerCase().startsWith(sparams.toLowerCase())) {
                aUsers.add(oneUser);
            }else if(!existFilter){
                aUsers.add(oneUser);
            }
        }

        setResult=sortByFullNameSet(aUsers.iterator(), user.getLanguage());
        int actualPage = 1;
        if (request.getParameter("actualPage") != null) {
            actualPage = Integer.parseInt(request.getParameter("actualPage"));
        }
        //Ya sabiendo cuantos y cuales son los elementos a listar (ya que puede ser una busqueda filtrada), se página
        String pagination = getPageRange(setResult.size(), actualPage);
        int iIniPage = 0;
        int iFinPage = 0;
        int iTotPage = 0;
        int contTokens=0;
        StringTokenizer strTokens=new StringTokenizer(pagination,"|");
        while(strTokens.hasMoreTokens()){
            String token=strTokens.nextToken();
            contTokens++;
            if(contTokens==1) iIniPage=Integer.parseInt(token);
            else if(contTokens==2) iFinPage=Integer.parseInt(token);
            else if(contTokens==3) iTotPage=Integer.parseInt(token);
        }
        %>
        <b>Resultado:</b> <%=setResult.size()%> usuarios
        <ul id="listaAlfabet">
            <li><a <%if(!existFilter){%>class="active"<%}%> href="<%=urlAlphabetic%>">Todos&nbsp;&nbsp;&nbsp;</a></li>
            <li><a <%if(sparams.equals("a")){%>class="active"<%}%> href="<%=urlAlphabetic.setParameter("alphabet", "a")%>">A</a></li>
            <li><a <%if(sparams.equals("b")){%>class="active"<%}%> href="<%=urlAlphabetic.setParameter("alphabet", "b")%>">B</a></li>
            <li><a <%if(sparams.equals("c")){%>class="active"<%}%> href="<%=urlAlphabetic.setParameter("alphabet", "c")%>">C</a></li>
            <li><a <%if(sparams.equals("d")){%>class="active"<%}%> href="<%=urlAlphabetic.setParameter("alphabet", "d")%>">D</a></li>
            <li><a <%if(sparams.equals("e")){%>class="active"<%}%> href="<%=urlAlphabetic.setParameter("alphabet", "e")%>">E</a></li>
            <li><a <%if(sparams.equals("f")){%>class="active"<%}%> href="<%=urlAlphabetic.setParameter("alphabet", "f")%>">F</a></li>
            <li><a <%if(sparams.equals("g")){%>class="active"<%}%> href="<%=urlAlphabetic.setParameter("alphabet", "g")%>">G</a></li>
            <li><a <%if(sparams.equals("h")){%>class="active"<%}%> href="<%=urlAlphabetic.setParameter("alphabet", "h")%>">H</a></li>
            <li><a <%if(sparams.equals("i")){%>class="active"<%}%> href="<%=urlAlphabetic.setParameter("alphabet", "i")%>">I</a></li>
            <li><a <%if(sparams.equals("j")){%>class="active"<%}%> href="<%=urlAlphabetic.setParameter("alphabet", "j")%>">J</a></li>
            <li><a <%if(sparams.equals("k")){%>class="active"<%}%> href="<%=urlAlphabetic.setParameter("alphabet", "k")%>">K</a></li>
            <li><a <%if(sparams.equals("l")){%>class="active"<%}%> href="<%=urlAlphabetic.setParameter("alphabet", "l")%>">L</a></li>
            <li><a <%if(sparams.equals("m")){%>class="active"<%}%> href="<%=urlAlphabetic.setParameter("alphabet", "m")%>">M</a></li>
            <li><a <%if(sparams.equals("n")){%>class="active"<%}%> href="<%=urlAlphabetic.setParameter("alphabet", "n")%>">N</a></li>
            <li><a <%if(sparams.equals("ñ")){%>class="active"<%}%> href="<%=urlAlphabetic.setParameter("alphabet", "ñ")%>">&Ntilde;</a></li>
            <li><a <%if(sparams.equals("o")){%>class="active"<%}%> href="<%=urlAlphabetic.setParameter("alphabet", "o")%>">O</a></li>
            <li><a <%if(sparams.equals("p")){%>class="active"<%}%> href="<%=urlAlphabetic.setParameter("alphabet", "p")%>">P</a></li>
            <li><a <%if(sparams.equals("q")){%>class="active"<%}%> href="<%=urlAlphabetic.setParameter("alphabet", "q")%>">Q</a></li>
            <li><a <%if(sparams.equals("r")){%>class="active"<%}%> href="<%=urlAlphabetic.setParameter("alphabet", "r")%>">R</a></li>
            <li><a <%if(sparams.equals("s")){%>class="active"<%}%> href="<%=urlAlphabetic.setParameter("alphabet", "s")%>">S</a></li>
            <li><a <%if(sparams.equals("t")){%>class="active"<%}%> href="<%=urlAlphabetic.setParameter("alphabet", "t")%>">T</a></li>
            <li><a <%if(sparams.equals("u")){%>class="active"<%}%> href="<%=urlAlphabetic.setParameter("alphabet", "u")%>">U</a></li>
            <li><a <%if(sparams.equals("v")){%>class="active"<%}%> href="<%=urlAlphabetic.setParameter("alphabet", "v")%>">V</a></li>
            <li><a <%if(sparams.equals("w")){%>class="active"<%}%> href="<%=urlAlphabetic.setParameter("alphabet", "w")%>">W</a></li>
            <li><a <%if(sparams.equals("x")){%>class="active"<%}%> href="<%=urlAlphabetic.setParameter("alphabet", "x")%>">X</a></li>
            <li><a <%if(sparams.equals("y")){%>class="active"<%}%> href="<%=urlAlphabetic.setParameter("alphabet", "y")%>">Y</a></li>
            <li><a <%if(sparams.equals("z")){%>class="active"<%}%> href="<%=urlAlphabetic.setParameter("alphabet", "z")%>">Z </a></li>
	    </ul>

        <div class="paginacion">
        <%
        if (actualPage > 1) {
             int gotop = (actualPage - 1);
             urlPag.setParameter("actualPage", ""+gotop);

             %>
                <a class="link" href="<%=urlPag%><%if(existFilter){%>&alphabet=<%=sparams%><%}%>"><img src="<%=SWBPortal.getWebWorkPath()%>/models/<%=website.getId()%>/css/images/pageArrowLeft.gif" alt="anterior"></a>
             <%
        }
        if(iTotPage>1){
            for (int i = 1; i <= iTotPage; i++) {
                if (i == actualPage) {
                    %><strong><%=i%></strong><%;
                } else {
                    urlPag.setParameter("actualPage", "" + i);
                    %>
                        <a href="<%=urlPag%><%if(existFilter){%>&alphabet=<%=sparams%><%}%>"><%=i%></a>
                    <%
                }
            }
        }
        if (actualPage > 0 && (actualPage + 1 <= iTotPage)) {
             int gotop = (actualPage + 1);
             urlPag.setParameter("actualPage", ""+gotop);
             %>
                <a class="link" href="<%=urlPag%><%if(existFilter){%>&alphabet=<%=sparams%><%}%>"><img src="<%=SWBPortal.getWebWorkPath()%>/models/<%=website.getId()%>/css/images/pageArrowRight.gif" alt="anterior"></a>
             <%
        }
        
        %>
        </div>
        <%
        //Termina paginación
        String perfilPath=website.getWebPage("perfil").getUrl();
        String photo=SWBPortal.getContextPath()+"/swbadmin/images/defaultPhoto.jpg";
        %>
        <div id="friendCards">
        <%
        HashMap <String, SemanticProperty> mapa = new HashMap();
        Iterator<SemanticProperty> list = org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#_ExtendedAttributes").listProperties();
        while(list.hasNext())
        {
            SemanticProperty sp=list.next();
            mapa.put(sp.getName(),sp);
        }
        int cont=0;
        Iterator itResult=setResult.iterator();
        while(itResult.hasNext())
        {
            cont++;
            User userprosp=(User)itResult.next();
            if(cont<=iIniPage) continue;
            else if(cont>iFinPage) break;

            if(userprosp.getPhoto()!=null) photo=userprosp.getPhoto();
        %>
            <div class="friendCard">
              <img class="profilePic" src="<%=SWBPortal.getWebWorkPath()+photo%>" alt="usuario">
               <div class="friendCardInfo">
                <a class="ico" href="mailto:<%=userprosp.getEmail()%>"><img src="<%=SWBPortal.getWebWorkPath()%>/models/<%=website.getId()%>/css/images/icoMail.png" alt="enviar un mensaje"></a>
                <a class="ico" href="<%=perfilPath%>?user=<%=userprosp.getEncodedURI()%>"><img src="<%=SWBPortal.getWebWorkPath()%>/models/<%=website.getId()%>/css/images/icoUser.png" alt="ir al perfil"></a>
                <%if(!user.getURI().equals(userprosp.getURI()) && !Friendship.areFriends(user, userprosp, website)&& !FriendshipProspect.findFriendProspectedByRequester(user, userprosp, website)){
                    urlAction.setAction("createProspect");
                    urlAction.setParameter("user", userprosp.getURI());
                %>
                    <a class="ico" href="<%=urlAction%>"><img src="<%=SWBPortal.getWebWorkPath()%>/models/<%=website.getId()%>/css/images/icoMas.png" alt="agregar"></a>
                <%}%>
                <div class="friendCardName">
                  <p><%=userprosp.getFullName()%></p>
                </div>
                <p>Sexo:<%=user.getExtendedAttribute(mapa.get("userSex"))%></p>
                <p>Edad:<%=user.getExtendedAttribute(mapa.get("userAge"))%></p>
            </div>
            </div>
            <%
         }
     %>
     </div>

     <%!
       private String getPageRange(int iSize, int iPageNum) {
            int iTotPage = 0;
            int iPage = I_INIT_PAGE;
            if (iPageNum > 1) {
                iPage = iPageNum;
            }
            if (iSize > I_PAGE_SIZE) {
                iTotPage = iSize / I_PAGE_SIZE;
                int i_ret = iSize % I_PAGE_SIZE;
                if (i_ret > 0) {
                    iTotPage = iTotPage + 1;
                }
            } else {
                iTotPage = 1;
            }
            int iIniPage = (I_PAGE_SIZE * iPage) - I_PAGE_SIZE;
            int iFinPage = I_PAGE_SIZE * iPage;
            if (iSize < I_PAGE_SIZE * iPage) {
                iFinPage = iSize;
            }
            return iIniPage + "|" + iFinPage + "|" + iTotPage;
      }

      public static Set sortByFullNameSet(Iterator it, String lang)
      {
            TreeSet set=new TreeSet(new Comparator()
            {
                public int compare(Object o1, Object o2)
                {
                    User ob1=(User)(o1);
                    User ob2=(User)(o2);
                    int ret=ob1.getFullName().compareTo(ob2.getFullName());
                    return ret;
                }
            });
            while(it.hasNext())
            {
                set.add(it.next());
            }
            return set;
      }

     %>