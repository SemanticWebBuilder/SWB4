<%--
    Document   : MigrationTool
    Created on : 8/09/2009, 12:38:24 PM
    Author     : juan.fernandez
--%>

<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="org.semanticwb.*,org.semanticwb.portal.api.*,org.semanticwb.platform.*,org.semanticwb.model.*,org.semanticwb.model.base.*,com.infotec.topicmaps.bean.*,com.infotec.topicmaps.*,java.util.*,com.infotec.wb.core.db.*,org.semanticwb.repository.Workspace" %>
<%@page import="com.infotec.wb.core.*, com.infotec.wb.core.db.*,com.infotec.wb.util.*,com.infotec.topicmaps.*,com.infotec.topicmaps.db.*,com.infotec.topicmaps.bean.*"%>
<%@page import="com.infotec.appfw.util.*,com.infotec.appfw.lib.DBPool.*,com.infotec.wb.lib.*, com.infotec.wb.services.*, com.infotec.wb.services.util.*"%>
<%@page import="java.io.*, java.net.*, java.util.*, java.sql.*, org.w3c.dom.*"%>
<%@page import="org.semanticwb.migration.office.*"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
        <title>Migration Tool WB3.2 to SemanticWebBuilder 4.0</title>
        <style type="text/css">
            body
            {
                font-family:verdana;
                font-size:12px;
            }
            li  { list-style: none }
        </style>
    </head>
    <%

    int numroles=0, numusrs=0;
    String path2=null,site=null,nspace=null,usrrep=null, webworkPath=null;;
    String action = null;
    boolean isImported = false, showReport=false;
    if(request.getParameter("act")!=null) action = request.getParameter("act");
    if(request.getParameter("isImported")!=null) isImported = request.getParameter("isImported").equals("true")?true:false;

    if(request.getParameter("path2")!=null&&request.getParameter("path2").trim().length()>0) path2=request.getParameter("path2");
    if(request.getParameter("site")!=null&&request.getParameter("site").trim().length()>0) site=request.getParameter("site");
    if(request.getParameter("usrrep")!=null&&request.getParameter("usrrep").trim().length()>0) usrrep=request.getParameter("usrrep");

    if(webworkPath==null) webworkPath="/work";


    //System.out.println("action:"+action);
    %>
    <body>
        <h2>Migration Tool from WebBuilder 3.2 to SemanticWebBuilder 4.0</h2>
        <%
            if (null == action || path2 == null) {
        %>
        <div>
            <form name="fusrrep" id="fusrrep" method="post" action="">
                <input type="hidden" name="act" value="usrrep" />
                <fieldset>
                    <legend>&nbsp;&nbsp;Proporciona la información que se te pide&nbsp;&nbsp;</legend>
                    <ul>
                        <li><label for="path2">Ruta "C:\swb\work\models\" a utilizar para SWB 4.0:</label><input type="text" id="path2" name="path2" size="70" value="<%=(path2 == null ? "D:\\Java\\SemanticWebBuilder\\SWB4\\swb\\build\\web\\work\\models\\" : path2)%>"></li>
                    </ul>
                </fieldset>
                <fieldset>
                    <button type="submit" name="bsend" id="bsend">Enviar</button>
                </fieldset>
            </form>
        </div>
        <%
            } else if ("usrrep".equals(action) || usrrep == null) {
                // Se inicializa SemanticWebBuilder para revisar la existencia de repositorio de usuarios existentes
                String prefix = getServletContext().getRealPath("/");
                try {
                    SWBUtils.createInstance(prefix);
                } catch (Exception e) {
                    System.out.println("Warning. Loading SWBUtils.");
                }
                System.out.println("SWBUtils Instance loaded....");
                try {
                    //SWBPlatform.createInstance(getServletContext());
                    //SWBPlatform.createInstance();
                    SWBPortal.createInstance(getServletContext());
                    SWBPortal.setUseDB(true);


                    String base=SWBUtils.getApplicationPath();
                    System.out.println("appPath: "+base);
                    SWBPlatform.createInstance();
                    SWBPlatform.createInstance().setContextPath(getServletContext().getContextPath());

                    SWBPlatform.getSemanticMgr().initializeDB();
                    SWBPlatform.getSemanticMgr().addBaseOntology(base+"WEB-INF/owl/swb.owl");
                    SWBPlatform.getSemanticMgr().addBaseOntology(base+"WEB-INF/owl/swb_rep.owl");
                    SWBPlatform.getSemanticMgr().addBaseOntology(base+"WEB-INF/owl/office.owl");
                    SWBPlatform.getSemanticMgr().addBaseOntology(base+"WEB-INF/owl/swb_usuarios.owl");
                    SWBPlatform.getSemanticMgr().addBaseOntology(base+"WEB-INF/owl/swb_userTypes.owl");
                    SWBPlatform.getSemanticMgr().loadBaseVocabulary();
                    SWBPlatform.getSemanticMgr().loadDBModels();
                    SWBPlatform.getSemanticMgr().getOntology().rebind();
                    System.out.println("SWBPlatform instance loaded....");

                } catch (Exception e) {
                    System.out.println("Warning.... Loading SWBPlatform...."+e.getMessage());
                }



                HashMap hmurep4 = new HashMap();
                try {
                    Iterator<UserRepository> itusrRep = UserRepository.ClassMgr.listUserRepositories();
                    while (itusrRep.hasNext()) {
                        UserRepository usrRep = itusrRep.next();
                        hmurep4.put(usrRep.getId(), usrRep);
                    }
                    System.out.println("SWB4 UserRepositories loaded....");
                } catch (Exception ne) {
                    System.out.println("ERROR.... SWB4 UserRepositories NOT loaded....");
                }



                HashMap hmurep3 = new HashMap();
                try {
                    Iterator ituRep = DBUser.getRepositories();
                    while (ituRep.hasNext()) {
                        DBUserRepository urep = (DBUserRepository) ituRep.next();
                        hmurep3.put(urep.getName(), urep);
                    }

                    System.out.println("WB3 User - Repositories loaded....");
                } catch (Exception ne) {
                    System.out.println("ERROR.... WB3 - UserRepositories NOT loaded....");
                }

        %>
        <div>
            <form name="fstep1" id="fstep1" method="post" action="">
                <input type="hidden" name="act" value="step1" />
                <input type="hidden" name="path2" value="<%=path2%>" />
                <fieldset>
                    <legend>&nbsp;&nbsp;Selecciona el repositorio de usuarios a copiar, este se utilizará para el sitio a migrar&nbsp;&nbsp;</legend>
                    <ul>
                        <%
                //Lista de repositorios de usuarios existentes a migrar
                Iterator itur = hmurep3.values().iterator();
                while (itur.hasNext()) {
                    DBUserRepository urep = (DBUserRepository) itur.next();
                    String migrado = "<font color=\"green\">Repositorio disponible</font>";
                    String disabled = "";
                    if (hmurep4.get(urep.getName() + "_usr") != null) {
                        migrado = "<font color=\"red\">Repositorio migrado</font>(En este sólo se le pueden copiar roles y usuarios si no tiene)";
                    }
                        %>
                        <li><input type="radio" id="<%=urep.getName()%>" name="usrrep" value="<%=urep.getName()%>" <%=disabled%>><label for="<%=urep.getName()%>"><%=urep.getName()%>&nbsp;<i><%=migrado%></i></label></li>
                            <%
                }
                            %>
                    </ul>
                </fieldset>
                <fieldset><legend>&nbsp;Copiar Roles y Usuarios&nbsp;</legend>
                    <p>&nbsp;&nbsp;&nbsp;&nbsp;Se copian únicamente si el repositorio de usuarios a migrar está vacio.</p>
                    <ul>
                        <li><input type="checkbox" id="inclroles" name="inclroles" value="true"><label for="inclroles">Migrar roles&nbsp;</label></li>
                        <li><input type="checkbox" id="inclusers" name="inclusers" value="true"><label for="inclusers">Migrar usuarios&nbsp;</label></li>
                    </ul>
                </fieldset>
                <fieldset>
                    <legend>&nbsp;&nbsp;Repositorios de usuarios existentes en SemanticWebBuilder.&nbsp;&nbsp;</legend>
                    <p>&nbsp;&nbsp;&nbsp;&nbsp;Selecciona uno para utilizarlo en el sitio a migrar.</p>
                    <ul>
                        <%
                //Lista de repositorios de usuarios existentes
                itur = hmurep4.values().iterator();
                while (itur.hasNext()) {
                    UserRepository urep = (UserRepository) itur.next();

                        %>
                        <li><input type="radio" id="<%=urep.getId()%>" name="usrrep" value="swb4|<%=urep.getId()%>" ><label for="<%=urep.getId()%>"><%=urep.getTitle()%>&nbsp;</label></li>
                            <%
                }
                            %>
                    </ul>
                </fieldset>
                <fieldset>
                    <button type="submit" name="bsend" id="bsend">Enviar</button>
                </fieldset>
            </form>
        </div>
        <%
            } else if ("step1".equals(action) || site == null) {

                // Sección para ejecutar la migración del repositorio de usuarios, usuarios y roles
                String inclrole = request.getParameter("inclroles");
                String incluser = request.getParameter("inclusers");

                String msgReportRoles = "No se encontraron roles a importar al repositorio de usuarios", msgReportUsers = "No se encontraron usuarios a importar al repositorio de usuarios";

                showReport = false;
                if (!usrrep.startsWith("swb4|")) {
                    //System.out.println("Antes de UserRepository...");

                    if (DBUser.getInstance(usrrep) != null && UserRepository.ClassMgr.getUserRepository(usrrep + "_usr") == null) {
                        createUserRep(usrrep);
                        showReport = true;
                    }

                    //System.out.println("despues de UserRepository...");

                    if (inclrole != null && inclrole.equals("true")) {
                        numroles = addElements2UserRep(usrrep, "roles");
                        showReport = true;
                        if (numroles > 0) {
                            msgReportRoles = "Se importaron " + numroles + " roles al repositorio de usuarios.";
                        }
                    }
                    if (incluser != null && incluser.equals("true")) {
                        numusrs = addElements2UserRep(usrrep, "users");
                        showReport = true;
                        if (numusrs > 0) {
                            msgReportUsers = "Se importaron " + numusrs + " usuarios al repositorio de usuarios.";
                        }
                    }

                }


        %>
        <div>
            <%
                if (showReport) {
            %>

            <fieldset>
                <legend>&nbsp;&nbsp;Resultado de la importación del repositorio de usuarios <%=usrrep%>&nbsp;&nbsp;</legend>
                <ul>
                    <li><%=msgReportRoles%></li>
                    <li><%=msgReportUsers%></li>
                </ul>
            </fieldset>
            <%
                }
                if (usrrep.indexOf("_usr") == -1) {
                    usrrep = usrrep + "_usr";
                }
            %>

            <form name="fstep2" id="fstep2" method="post" action="">
                <input type="hidden" name="act" value="step2" />
                <input type="hidden" name="path2" value="<%=path2%>" />
                <input type="hidden" name="usrrep" value="<%=usrrep%>" />

                <fieldset>
                    <legend>Selecciona el sitio a migrar:</legend>
                    <ul>
                        <%
                WebSite webs = null;
                //Lista de sitios existentes a migrar
                int numsites = 0;
                Iterator ittm = TopicMgr.getInstance().getTopicMaps();
                while (ittm.hasNext()) {
                    TopicMap tm = (TopicMap) ittm.next();
                    webs = SWBContext.getWebSite(tm.getId());
                    String str_migrado = "";
                    if (null != webs) {
                        str_migrado = " Este sitio ya se migró...";
                    }
                    if (!tm.getId().equals(TopicMgr.TM_GLOBAL)) {
                        numsites++;
                        String activo = "<font color=\"green\">Sitio activo</font>";
                        if (tm.getDbdata().getActive() == 0) {
                            activo = "<font color=\"red\">Sitio inactivo</font>";
                        }
                        %>
                        <li><input type="radio" id="<%=tm.getId()%>" name="site" value="<%=tm.getId()%>"><label for="<%=tm.getId()%>"><%=tm.getDbdata().getTitle()%>&nbsp;<i><%=activo + str_migrado%></i></label></li>
                            <%
                    }
                }
                if (numsites == 0) {
                            %>
                        <li><p>No se encontraron sitios a migrar.</p></li>
                        <%                }
                        %>
                    </ul>
                </fieldset>
                <fieldset>
                    <button type="submit" name="bsend" id="bsend">Enviar</button>
                </fieldset>
            </form>
        </div>

        <%
            } else if ("step2".equals(action)) {
        %>
        <div>
            <form name="fstep3" id="fstep3" method="post" action="">

                <%

                TopicMap tm = TopicMgr.getInstance().getTopicMap(site);
                String repName = tm.getDbdata().getRepository();

                UserRepository urep = UserRepository.ClassMgr.getUserRepository(repName + "_usr");
                WebSite wsite = null;
                try {
                    wsite = SWBContext.getWebSite(site);
                } catch (Exception e) {
                    System.out.println("Error al revisar la existencia del sitio: " + site);
                }
                if (wsite != null && null != urep) { //se migró sitio y rep. usuarios relacionado
                    wsite.getSemanticObject().getModel().setTraceable(false);

                %>
                <input type="hidden" name="act" value="step3" />
                <input type="hidden" name="path2" value="<%=path2%>" />
                <input type="hidden" name="usrrep" value="<%=usrrep%>" />
                <input type="hidden" name="site" value="<%=site%>" />
                <input type="hidden" name="isImported" value="true" />
                <fieldset>
                    <legend>Sitio <%=tm.getDbdata().getTitle()%></legend>
                    <ul>
                        <li>Este sitio ya se encuentra en la BD, puedes seleccionar otro o continuar con los catálogos.</li>
                    </ul>
                </fieldset>
                <fieldset>
                    <button type="submit" name="bsend" id="bsend">Continuar con cat&aacute;logos</button>
                    <button type="button" name="bback" id="bback" onclick="javascript:history.back();">Regresar</button>
                </fieldset>
                <%
                        } else if (wsite != null && null == urep) {

                %>

                <fieldset>
                    <legend>Sitio <%=tm.getDbdata().getTitle()%></legend>
                    <ul>
                        <li>Este sitio ya se encuentra en la BD, pero falta migrar el repositorio de usuarios (<%=repName%>) asociado a este sitio.</li>
                    </ul>
                </fieldset>
                <fieldset>
                    <legend></legend>
                    <button type="button" name="bback" id="bback" onclick="javascript:history.back();">Regresar para importar otro repositorio de usuarios</button>
                </fieldset>
                <%
                        } else {
                            int nsecs = tm.getHome().getChildAll().size();
                            int nasoc = tm.getAssociations().size();
                %>
                <input type="hidden" name="act" value="step3" />
                <input type="hidden" name="path2" value="<%=path2%>" />
                <input type="hidden" name="usrrep" value="<%=usrrep%>" />
                <input type="hidden" name="site" value="<%=site%>" />
                <input type="hidden" name="isImported" value="false" />
                <fieldset>
                    <legend>Sitio <%=tm.getDbdata().getTitle()%></legend>
                    <ul>
                        <li>Se importarán <%=nsecs + 1%> secciones que componen la estructura del sitio.</li>
                        <li>Se importarán <%=nasoc%> asociaciones encontradas en el sitio.</li>
                    </ul>
                </fieldset>
                <fieldset>
                    <button type="submit" name="bsend" id="bsend">Continuar con importación del sitio y secciones</button>
                    <button type="button" name="bback" id="bback" onclick="javascript:history.back();">Regresar</button>
                </fieldset>
                <%
                        }
                %>
            </form>
        </div>
        <%
            } // Importación de la estructura del sitio
            else if ("step3".equals(action)) {
        %>
        <div>
            <fieldset>
                <%
                TopicMap tm = TopicMgr.getInstance().getTopicMap(site);
                com.infotec.topicmaps.Topic tp = tm.getHome();

                HashMap hmcat = new HashMap();
                int nwp = 0;
                int nwsasoc = 0;
                int nptype = 0;
                WebSite ws = null;
                WebPage home = null;
                if (isImported) {
                    ws = WebSite.ClassMgr.getWebSite(tm.getId());
                    ws.getSemanticObject().getModel().setTraceable(false);

                    home = ws.getHomePage();

                    //obteniendo num. de elementos existentes migrados
                    hmcat = getNumCatElements(ws);

                    //obteniendo numero de webpages importados
                    nwp = getNumWP(ws);
                    //obteniendo numero de asociaciones importadas
                    nwsasoc = getNumWSAsoc(ws);
                %>
                <legend>La estructura del sitio ya se migró.</legend>
                <p>Tiene <%=nwp%> secciones
                <%
                if(nwsasoc==0) out.println(".");
                else out.println("y, "+nwsasoc+" asociones");
                %>
                </p>
                <%

                        } else {
                            nspace = "http://www." + tm.getId() + ".swb#";
                            ws = WebSite.ClassMgr.createWebSite(tm.getId(), nspace);
                            ws.getSemanticObject().getModel().setTraceable(false);

                            //Crea grupo de templates de defecto
                            TemplateGroup grp = ws.createTemplateGroup();
                            grp.setTitle("Default");

                            home = copyValues(tp, ws);

                            home.setUndeleteable(true);
                            home.setSortName("z");



                            ws.setTitle(tm.getDbdata().getTitle());

                            ws.setActive(tm.isActive());
                            ws.setDeleted(tm.getDbdata().getDeleted() == 1 ? true : false);
                            ws.setDescription(tm.getDbdata().getDescription());

                            ws.setCreated(tm.getDbdata().getCreated());
                            ws.setHomePage(home);
                            ws.setUpdated(new java.util.Date());

                            assignUserRep2WebSite(ws, usrrep);

                            //Creación del repositorio de documentos JSR-170
                            createRepositoryFileWebSite(ws);

                            //creacion de elementos por defecto del sitio
                            //createWebSitesDefaultElements(ws, nspace);

                            //importando estructura
                            tp2wp(tp, home, ws);

                            System.out.println("Se importo la estructura del sitio");

                            //importando asociaciones
/*
                            HashMap hmasocwb3 = tm.getAssociations();
                            Iterator it = hmasocwb3.values().iterator();
                            while (it.hasNext())
                            {
                               System.out.println("Obteniendo asociacion.");
                               com.infotec.topicmaps.Association elem = (com.infotec.topicmaps.Association)it.next();
                               com.infotec.topicmaps.Topic tpTipo = elem.getType();
                               if(tpTipo!=null)
                               {
                                   System.out.println("Obteniendo WP tipo p/asoc.");
                                   org.semanticwb.model.Topic wpTipo = ws.getWebPage(tpTipo.getId());
                                   if(wpTipo!=null)
                                   {
                                       System.out.println("Creando asociacion.");
                                       org.semanticwb.model.Association asoc = ws.createAssociation(elem.getId());
                                       asoc.setType(wpTipo);
                                       Iterator itmember = elem.getMembers().iterator();
                                       //Agregando los miembros existentes ligados a este tipo de asociacion
                                       System.out.println("Antes de revisar miembros de la asociacion.");
                                       while(itmember.hasNext())
                                       {
                                           com.infotec.topicmaps.Member mem = (com.infotec.topicmaps.Member)itmember.next();
                                           System.out.println("Obteniendo miembro.");

                                           org.semanticwb.model.AssMember assmember = ws.createAssMember(mem.getId());
                                           com.infotec.topicmaps.RoleSpec rolmem = mem.getRoleSpec();
                                           Iterator memite = mem.getTopicRefs().keySet().iterator();
                                           String topicoid = null;
                                           if(memite.hasNext()) topicoid = (String) memite.next();
                                           if(null!=topicoid && rolmem!=null)
                                           {
                                               //se agrega rol del miembre
                                               assmember.setRole(ws.getWebPage(rolmem.getTopicRef().getId()));
                                               //se agrega el topico
                                               assmember.setMember(ws.getWebPage(topicoid));
                                               // se agrega miembreo al tipo de asociacion
                                               System.out.println("Agregando miembro p/asociacion.");
                                               asoc.addMember(assmember);
                                           }
                                       }
                                   }
                               }
                            }
 * System.out.println("Se importo las asociaciones del sitio");
 */

                            //obteniendo el numero de asociaciones importadas
                            nwsasoc = getNumWSAsoc(ws);

                            //obteniendo numero de webpages importados
                            nwp = getNumWP(ws);

                            //revisando para agregar virtuales
                            nptype = virtualTopic2VirtualWebPage(tp, home, ws); // se obtiene el numero de padres virtuales agregados
%>
                <legend>&nbsp;&nbsp;Resultado de la creación de la estructura del sitio&nbsp;&nbsp;</legend>
                <p>Se importaron <%=nwp%> secciones.</p>
                <p>Se establecieron <%=nptype%> relaciones entre las secciones creadas.</p>
                <!-- <p>Se importaron <%=nwsasoc%> asociaciones entre las secciones.</p> -->
                <%
                }
                %>
            </fieldset>
            <form name="fstep4" id="fstep4" method="post" >
                <input type="hidden" name="act" value="step4" />
                <input type="hidden" name="path2" value="<%=path2%>" />
                <input type="hidden" name="usrrep" value="<%=usrrep%>" />
                <input type="hidden" name="site" value="<%=site%>" />
                <input type="hidden" name="isImported" value="<%=isImported%>" />

                <fieldset style="background-color:#F7BE81;"><legend>&nbsp;&nbsp;Lista de Catálogos - Paso 1 de 3&nbsp;&nbsp;</legend>
                    <ul>
                        <li><input type="checkbox" name="catalog" id="cat1" value="language" <%=hmcat.get("language") != null ? "disabled" : "checked"%>><label for="cat1">Lenguajes <%=hmcat.get("language") != null ? "(" + hmcat.get("language") + ")" : ""%></label></li>
                        <li><input type="checkbox" name="catalog" id="cat2" value="device" <%=hmcat.get("device") != null ? "disabled" : "checked"%>><label for="cat2">Dispositivos <%=hmcat.get("device") != null ? "(" + hmcat.get("device") + ")" : ""%></label></li>
                        <li><input type="checkbox" name="catalog" id="cat9" value="dns" <%=hmcat.get("dns") != null ? "disabled" : "checked"%>><label for="cat9">DNS <%=hmcat.get("dns") != null ? "(" + hmcat.get("dns") + ")" : ""%></label></li>
                        <li><input type="checkbox" name="catalog" id="cat11" value="rules" <%=hmcat.get("rules") != null ? "disabled" : "checked"%>><label for="cat11">Reglas <%=hmcat.get("rules") != null ? "(" + hmcat.get("rules") + ")" : ""%></label></li>
                        <li><input type="checkbox" name="catalog" id="cat4" value="pflow" <%=hmcat.get("pflow") != null ? "disabled" : "checked"%>><label for="cat4">Flujos de publicación <%=hmcat.get("pflow") != null ? "(" + hmcat.get("pflow") + ")" : ""%></label></li>
                        <li><input type="checkbox" name="catalog" id="cat3" value="resourcetype" <%=hmcat.get("resourcetype") != null ? "disabled" : "checked"%>><label for="cat3">Tipos de recursos <%=hmcat.get("resourcetype") != null ? "(" + hmcat.get("resourcetype") + ")" : ""%></label></li>
                        <li><input type="checkbox" name="catalog" id="cat6" value="grouptemplate" <%=hmcat.get("grouptemplate") != null ? "disabled" : "checked"%>><label for="cat6">Grupos de plantillas <%=hmcat.get("grouptemplate") != null ? "(" + hmcat.get("grouptemplate") + ")" : ""%></label></li>
                        <!-- li><input type="checkbox" name="catalog" id="cat8" value="camp"><label for="cat8">Campañas</label></li -->
                    </ul>
                </fieldset>
                <fieldset><legend>&nbsp;&nbsp;Lista de Catálogos - Paso 2&nbsp;&nbsp;</legend>
                    <ul>
                        <li><input type="checkbox" name="catalog" id="cat8" value="resourcesubtype" disabled><label for="cat8">Sub-Tipos de recursos <%=hmcat.get("resourcesubtype") != null ? "(" + hmcat.get("resourcesubtype") + ")" : ""%></label></li>
                        <li><input type="checkbox" name="catalog" id="cat5" value="template" disabled><label for="cat5">Plantillas <%=hmcat.get("template") != null ? "(" + hmcat.get("template") + ")" : ""%></label></li>
                    </ul>
                </fieldset>
                <fieldset><legend>&nbsp;&nbsp;Lista de Catálogos - Paso 3&nbsp;&nbsp;</legend>
                    <ul>
                        <li><input type="checkbox" name="catalog" id="cat7" value="resource" disabled><label for="cat7">Recursos <%=hmcat.get("resource") != null ? "(" + hmcat.get("resource") + ")" : ""%></label></li>
                        <li><input type="checkbox" name="wpassoc" id="cat10" value="assoc" disabled><label for="cat10">Revisar Asociaciones de Recursos con Secciones (se debe hacer sólo una vez, si ya se acabaron de migrar todos los recursos)</label></li>
                    </ul>
                </fieldset>
                <fieldset>
                    <button name="b_send" id="b_send" type="submit">Continuar al paso 2</button>
                </fieldset>
            </form>
        </div>
        <%
            } // Impportación de catálogos del paso 1
            else if ("step4".equals(action)) {
                TopicMap tm = TopicMgr.getInstance().getTopicMap(site);
                WebSite ws = null;
                ws = WebSite.ClassMgr.getWebSite(tm.getId());
                ws.getSemanticObject().getModel().setTraceable(false);

                int numLang = 0, numDev = 0, numResType = 0, numResSubType = 0, numPflow = 0, numTemplates = 0, numGTemplate, numres = 0, numDns = 0, numRules = 0, numRoles = 0;

                int idmax = 0;
                String[] arrCat = request.getParameterValues("catalog");
                if (null != arrCat) {
                    for (int i = 0; i < arrCat.length; i++) {

                        out.println(" val : " + arrCat[i]);
                        if ("language".equals(arrCat[i])) {

                            Collection colLang = DBCatalogs.getInstance().getLanguages(site).values();
                            numLang = colLang.size();
                            Iterator itlang = colLang.iterator();
                            while (itlang.hasNext()) {
                                RecLanguage rlang = (RecLanguage) itlang.next();
                                Language lang = ws.getLanguage(rlang.getLang());
                                if (null == lang) {
                                    lang = ws.createLanguage(rlang.getLang());
                                    lang.setTitle(rlang.getTitle());
                                }
                            }
                        }
                        if ("device".equals(arrCat[i])) {

                            idmax = 0;
                            Collection colLang = DBCatalogs.getInstance().getDevices().values();
                            numDev = colLang.size();
                            Iterator itlang = colLang.iterator();
                            while (itlang.hasNext()) {
                                RecDevice rdev = (RecDevice) itlang.next();
                                Device dev = ws.createDevice(Integer.toString(rdev.getId()));
                                dev.setTitle(rdev.getName());
                                dev.setDescription(rdev.getDescription());
                                dev.setUserAgent(rdev.getStrMatch());
                                if (rdev.getId() > idmax) {
                                    idmax = rdev.getId();
                                }
                            }
                            idmax++;
                            ws.getSemanticObject().getModel().setCounterValue(Device.sclass.getClassGroupId(), idmax);
                        }
                        if ("resourcetype".equals(arrCat[i])) {

                            String idName = null;
                            // Copiando tipos de recursos de GLOBAL
                            Collection colresty = DBResourceType.getInstance().getResourceTypes(TopicMgr.TM_GLOBAL).values();
                            numResType = colresty.size();
                            Iterator itresty = colresty.iterator();
                            while (itresty.hasNext()) {
                                RecResourceType rrtyp = (RecResourceType) itresty.next();

                                idName = rrtyp.getName();
                                idName = idName.trim();
                                idName = idName.replaceAll(" ", "");

                                if (!idName.equals("ExcelContent") && !idName.equals("PPTContent")) {
                                    ResourceType rtype = ws.createResourceType(idName);
                                    rtype.setTitle(rrtyp.getDisplayName());
                                    rtype.setDescription(rrtyp.getDescription());
                                    String strObjClass = rrtyp.getObjclass();
                                    if(strObjClass.equals("com.infotec.wb.resources.IndiceTematicoXSL")) strObjClass = "com.infotec.wb.resources.TematicIndexXSL";
                                    else if(strObjClass.equals("com.infotec.wb.resources.Imprimir")) strObjClass = "com.infotec.wb.resources.Print";
                                    else if(strObjClass.equals("com.infotec.wb.resources.Recomendar")) strObjClass = "org.semanticwb.portal.resources.Recommend";
                                    else if(strObjClass.equals("com.infotec.wb.resources.Recommend")) strObjClass = "org.semanticwb.portal.resources.Recommend";
                                    else if(strObjClass.equals("com.infotec.wb.resources.MenuMap")) strObjClass = "com.infotec.wb.resources.WBMenuMap";
                                    else if(strObjClass.equals("com.infotec.wb.resources.WBSearch")) strObjClass = "org.semanticwb.portal.resources.WBSearch";
                                    else if(strObjClass.equals("com.infotec.wb.resources.Login")) strObjClass = "org.semanticwb.portal.resources.Login";
                                    else if(strObjClass.equals("com.infotec.wb.resources.Content")) strObjClass = "org.semanticwb.portal.resources.sem.HTMLContent";
                                    else if(strObjClass.equals("com.infotec.wb.resources.UserRegistration")) strObjClass = "org.semanticwb.portal.resources.UserRegistration";
                                    rtype.setResourceClassName(strObjClass);
                                    String strObjBundle = rrtyp.getObjclass();
                                    if(strObjBundle.equals("com.infotec.wb.resources.IndiceTematicoXSL")) strObjBundle = "com.infotec.wb.resources.TematicIndexXSL";
                                    else if(strObjBundle.equals("com.infotec.wb.resources.Imprimir")) strObjBundle = "com.infotec.wb.resources.Print";
                                    else if(strObjBundle.equals("com.infotec.wb.resources.Recomendar")) strObjBundle = "org.semanticwb.portal.resources.Recommend";
                                    else if(strObjBundle.equals("com.infotec.wb.resources.Recommend")) strObjBundle = "org.semanticwb.portal.resources.Recommend";
                                    else if(strObjBundle.equals("com.infotec.wb.resources.MenuMap")) strObjBundle = "com.infotec.wb.resources.WBMenuMap";
                                    else if(strObjBundle.equals("com.infotec.wb.resources.WBSearch")) strObjBundle = "org.semanticwb.portal.resources.WBSearch";
                                    else if(strObjBundle.equals("com.infotec.wb.resources.Login")) strObjBundle = "org.semanticwb.portal.resources.Login";
                                    else if(strObjBundle.equals("com.infotec.wb.resources.Content")) strObjBundle = "org.semanticwb.portal.resources.sem.HTMLContent";
                                    else if(strObjBundle.equals("com.infotec.wb.resources.UserRegistration")) strObjBundle = "org.semanticwb.portal.resources.UserRegistration";
                                    rtype.setResourceBundle(strObjBundle);
                                    rtype.setResourceMode(rrtyp.getType());
                                    rtype.setResourceCache(rrtyp.getCache());
                                }
                            }

                            // COPIANDO TIPO DE RECURSOS DEL SITIO SELECCIONADO
                            colresty = DBResourceType.getInstance().getResourceTypes(site).values();
                            itresty = colresty.iterator();
                            while (itresty.hasNext()) {
                                RecResourceType rrtyp = (RecResourceType) itresty.next();

                                idName = rrtyp.getName();
                                idName = idName.trim();
                                idName = idName.replaceAll(" ", "");

                                String clsBundle = rrtyp.getBundle();
                                String clsName = rrtyp.getObjclass();

                                //Revisar su equivalente de WB3.2 a SWB4.0

                                if (idName.equals("Content") && clsName.equals("com.infotec.wb.resources.Content")) {
                                    clsName = "org.semanticwb.portal.resources.sem.HTMLContent";
                                    idName = "HTMLContent";
                                }

                                ResourceType rtype = ws.createResourceType(idName);
                                rtype.setTitle(rrtyp.getDisplayName());
                                //rtype.setTitle(rrtyp.getName());
                                rtype.setDescription(rrtyp.getDescription());
                                rtype.setResourceClassName(clsName);
                                rtype.setResourceBundle(clsBundle);
                                rtype.setResourceMode(rrtyp.getType());
                            }
                        }

                        if ("pflow".equals(arrCat[i])) {
                            idmax = 0;
                            Enumeration enupf = DBPFlow.getInstance().getPFlows(site);
                            while (enupf.hasMoreElements()) {
                                RecPFlow rpf = (RecPFlow) enupf.nextElement();
                                org.semanticwb.model.PFlow pf = ws.createPFlow(Integer.toString(rpf.getId()));
                                pf.setTitle(rpf.getTitle());
                                pf.setDescription(rpf.getDescription());
                                pf.setXml(rpf.getXml());
                                pf.setActive(true);
                                pf.setValid(true);
                                numPflow++;
                                if (rpf.getId() > idmax) {
                                    idmax = rpf.getId();
                                }
                            }
                            idmax++;
                            ws.getSemanticObject().getModel().setCounterValue(org.semanticwb.model.PFlow.sclass.getClassGroupId(), idmax);
                        }

                        if ("grouptemplate".equals(arrCat[i])) {

                            idmax = 0;
                            Collection coll = DBCatalogs.getInstance().getGrpTemplates(site).values();
                            numGTemplate = coll.size();
                            Iterator it = coll.iterator();
                            while (it.hasNext()) {
                                RecGrpTemplate rgt = (RecGrpTemplate) it.next();
                                TemplateGroup tgp = ws.createTemplateGroup(Integer.toString(rgt.getId()));
                                tgp.setTitle(rgt.getTitle());
                                tgp.setDescription(rgt.getDescription());
                                if (rgt.getId() > idmax) {
                                    idmax = rgt.getId();
                                }
                            }
                            idmax++;
                            ws.getSemanticObject().getModel().setCounterValue(TemplateGroup.sclass.getClassGroupId(), idmax);
                        }
                        if ("dns".equals(arrCat[i])) {

                            idmax = 0;
                            Collection coll = DBCatalogs.getInstance().getDnss(site).values();
                            numDns = coll.size();
                            Iterator it = coll.iterator();
                            while (it.hasNext()) {
                                RecDns rgt = (RecDns) it.next();
                                Dns dns = ws.createDns(Integer.toString(rgt.getId()));
                                dns.setDns(rgt.getDns());
                                dns.setDefault(rgt.getIsDefault() == 1 ? true : false);
                                dns.setWebPage(ws.getWebPage(rgt.getTopic()));
                                if (rgt.getId() > idmax) {
                                    idmax = rgt.getId();
                                }
                            }
                            idmax++;
                            ws.getSemanticObject().getModel().setCounterValue(TemplateGroup.sclass.getClassGroupId(), idmax);
                        }

                        if ("rules".equals(arrCat[i])) {
                            idmax = 0;
                            Enumeration enur = DBRule.getInstance().getRules(site);
                            while (enur.hasMoreElements()) {
                                RecRule rr = (RecRule) enur.nextElement();
                                org.semanticwb.model.Rule rule = ws.createRule(Integer.toString(rr.getId()));
                                rule.setTitle(rr.getTitle());
                                rule.setDescription(rr.getDescription());
                                //System.out.println("XML:" + rr.getXml());
                                if (rr.getXml() != null) {
                                    String str_xml = rr.getXml();
                                    HashMap hm = loadUsrPropertyMatch();
                                    Iterator itprop = hm.keySet().iterator();
                                    while (itprop.hasNext()) {
                                        String key = (String) itprop.next();
                                        str_xml = str_xml.replaceAll(key, (String) hm.get(key));
                                    }

                                    rule.getSemanticObject().setProperty(Rule.swb_xml, str_xml);
                                }
                                rule.setValid(true);
                                numRules++;
                                if (rr.getId() > idmax) {
                                    idmax = rr.getId();
                                }
                            }
                            idmax++;
                            ws.getSemanticObject().getModel().setCounterValue(Rule.sclass.getClassGroupId(), idmax);
                        }
                    }
                }
        %>
        <div>
            <%
                HashMap hmcat = new HashMap();

                int nwp = 0;
                int nptype = 0;
                //WebSite ws = null;
                WebPage home = null;
                ws = WebSite.ClassMgr.getWebSite(tm.getId());
                ws.getSemanticObject().getModel().setTraceable(false);
                home = ws.getHomePage();
                //obteniendo num. de elementos existentes migrados
                hmcat = getNumCatElements(ws);

            %>
            <form name="fstep5" id="fstep5" method="post" action="" >
                <input type="hidden" name="act" value="step5" />
                <input type="hidden" name="path2" value="<%=path2%>" />
                <input type="hidden" name="usrrep" value="<%=usrrep%>" />
                <input type="hidden" name="site" value="<%=site%>" />
                <input type="hidden" name="isImported" value="<%=isImported%>" />

                <fieldset style="background-color:#A9F5D0;"><legend>&nbsp;&nbsp;Lista de Catálogos - Paso 1 (Completado)&nbsp;&nbsp;</legend>
                    <ul>
                        <li><input type="checkbox" name="catalog" id="cat1" value="language" disabled><label for="cat1">Lenguajes <%=hmcat.get("language") != null ? "(" + hmcat.get("language") + ")" : ""%></label></li>
                        <li><input type="checkbox" name="catalog" id="cat2" value="device" disabled><label for="cat2">Dispositivos <%=hmcat.get("device") != null ? "(" + hmcat.get("device") + ")" : ""%></label></li>
                        <li><input type="checkbox" name="catalog" id="cat9" value="dns" disabled><label for="cat9">DNS <%=hmcat.get("dns") != null ? "(" + hmcat.get("dns") + ")" : ""%></label></li>
                        <li><input type="checkbox" name="catalog" id="cat11" value="rules" disabled><label for="cat11">Reglas <%=hmcat.get("rules") != null ? "(" + hmcat.get("rules") + ")" : ""%></label></li>
                        <li><input type="checkbox" name="catalog" id="cat4" value="pflow" disabled><label for="cat4">Flujos de publicación <%=hmcat.get("pflow") != null ? "(" + hmcat.get("pflow") + ")" : ""%></label></li>
                        <li><input type="checkbox" name="catalog" id="cat3" value="resourcetype" disabled><label for="cat3">Tipos de recursos <%=hmcat.get("resourcetype") != null ? "(" + hmcat.get("resourcetype") + ")" : ""%></label></li>
                        <li><input type="checkbox" name="catalog" id="cat6" value="grouptemplate" disabled><label for="cat6">Grupos de plantillas <%=hmcat.get("grouptemplate") != null ? "(" + hmcat.get("grouptemplate") + ")" : ""%></label></li>
                        <!-- li><input type="checkbox" name="catalog" id="cat8" value="camp"><label for="cat8">Campañas</label></li -->
                    </ul>
                </fieldset>
                <fieldset style="background-color:#F7BE81;"><legend>&nbsp;&nbsp;Lista de Catálogos - Paso 2 de 3&nbsp;&nbsp;</legend>
                    <%
                boolean doResSubType = true;
                if (hmcat.get("resourcetype") == null) {
                    doResSubType = false;
                    %>
                    <p>Si no hay Tipos de Recursos, no se podrán migrar subtipos de recursos</p>
                    <%
                }
                boolean doTemplate = true;
                if (hmcat.get("grouptemplate") == null) {
                    doTemplate = true;
                    %>
                    <p>Si no hay Grupos de Plantillas, no se podrán migrar las plantillas</p>
                    <%
                }
                    %>
                    <ul>
                        <li><input type="checkbox" name="catalog" id="cat8" value="resourcesubtype" <%=(hmcat.get("resourcesubtype") != null || !doResSubType ? "disabled" : "checked")%>><label for="cat8">Sub-Tipos de recursos <%=hmcat.get("resourcesubtype") != null ? "(" + hmcat.get("resourcesubtype") + ")" : ""%></label></li>
                        <li><input type="checkbox" name="catalog" id="cat5" value="template" <%=(hmcat.get("template") != null || !doTemplate ? "disabled" : "checked")%>><label for="cat5">Plantillas <%=hmcat.get("template") != null ? "(" + hmcat.get("template") + ")" : ""%></label></li>
                    </ul>
                </fieldset>
                <fieldset><legend>&nbsp;&nbsp;Lista de Catálogos - Paso 3&nbsp;&nbsp;</legend>
                    <ul>
                        <li><input type="checkbox" name="catalog" id="cat7" value="resource" disabled><label for="cat7">Recursos <%=hmcat.get("resource") != null ? "(" + hmcat.get("resource") + ")" : ""%></label></li>
                        <li><input type="checkbox" name="wpassoc" id="cat10" value="assoc" disabled><label for="cat10">Revisar Asociaciones de Recursos con Secciones (se debe hacer sólo una vez, si ya se acabaron de migrar todos los recursos)</label></li>
                    </ul>
                </fieldset>
                <fieldset>
                    <button name="b_send" id="b_send" type="submit">Continuar</button>
                </fieldset>
            </form>
        </div>
        <%
            } // Impportación de catálogos del paso 2
            else if ("step5".equals(action)) {
                TopicMap tm = TopicMgr.getInstance().getTopicMap(site);
                WebSite ws = null;
                ws = WebSite.ClassMgr.getWebSite(tm.getId());
                ws.getSemanticObject().getModel().setTraceable(false);

                int numLang = 0, numDev = 0, numResType = 0, numResSubType = 0, numPflow = 0, numTemplates = 0, numGTemplate, numres = 0, numDns = 0, numRules = 0, numRoles = 0;

                int idmax = 0;
                String[] arrCat = request.getParameterValues("catalog");
                if (null != arrCat) {
                    for (int i = 0; i < arrCat.length; i++) {

                        out.println(" val : " + arrCat[i]);

                        if ("resourcesubtype".equals(arrCat[i])) {
                            idmax = 0;
                            Collection colLang = DBCatalogs.getInstance().getSubTypes(site).values();
                            numResSubType = colLang.size();
                            Iterator itsty = colLang.iterator();
                            while (itsty.hasNext()) {
                                RecSubType rsty = (RecSubType) itsty.next();
                                String typemap=rsty.getTypeMap();
                                //String idNameST = rsty.getTitle();
                                //idNameST = idNameST.trim();
                                //idNameST = idNameST.replaceAll(" ", "");
                                ResourceSubType styp = ws.createResourceSubType(Integer.toString(rsty.getId()));
                                //ResourceSubType styp = ws.createResourceSubType(idNameST);

                                styp.setTitle(rsty.getTitle());
                                styp.setDescription(rsty.getDescription());

                                RecResourceType recrestype = null;
                                if(typemap.equals(TopicMgr.TM_GLOBAL))
                                {
                                    recrestype = DBResourceType.getInstance().getResourceType(TopicMgr.TM_GLOBAL, rsty.getType());
                                }
                                else {
                                    recrestype = DBResourceType.getInstance().getResourceType(site, rsty.getType());
                                }

                                if (recrestype != null) {
                                    String idName = recrestype.getName();
                                    idName = idName.trim();
                                    idName = idName.replaceAll(" ", "");

                                    ResourceType rtype = ws.getResourceType(idName);

                                    if (rtype != null) {
                                        styp.setType(rtype);
                                    }
                                } else continue;
                            }
                        }
                        if ("template".equals(arrCat[i])) {
                            idmax = 0;
                            //Enumeration enutpl = DBTemplate.getInstance().getTemplates(site);

                            Collection colTpl = TemplateMgr.getInstance().getTemplates(site).values();
                            Iterator ittpl = colTpl.iterator();
                            while (ittpl.hasNext()) {
                                com.infotec.wb.core.Template templ = (com.infotec.wb.core.Template) ittpl.next();

                                if(templ.getDeleted()==1) continue;

                                RecTemplate rtpl = templ.getRecTemplate();

                                org.semanticwb.model.Template tpl = ws.createTemplate(Integer.toString(rtpl.getId()));
                                tpl.setTitle(rtpl.getTitle());
                                tpl.setDescription(rtpl.getDescription());
                                tpl.setActive(rtpl.getActive() == 1 ? true : false);

                                TemplateGroup tplgp = ws.getTemplateGroup(Integer.toString(rtpl.getGrpid()));

                                if (null != tplgp) {
                                    tpl.setGroup(tplgp);
                                }


                                System.out.println("filename: "+templ.getFileName(rtpl.getActualversion())+", version actual: "+rtpl.getActualversion());


                                VersionInfo vi = ws.createVersionInfo();
                                vi.setVersionFile(templ.getFileName(rtpl.getActualversion()));
                                vi.setVersionNumber(1);
                                vi.setVersionComment("Template importado");

                                tpl.setActualVersion(vi);
                                tpl.setLastVersion(vi);

                                //copiado de archivos

                                int versionactual = rtpl.getActualversion();
                                int i_tplId = rtpl.getId();

                                String sourceDirectory = "/sites/" + site + "/templates/" + i_tplId + "/" + versionactual + "/";
                                String targetDirectory = path2 + site + "/Template/" + i_tplId + "/1/";
                                String destinationPath = "/models/"+site + "/Template/" + i_tplId + "/1/";

                                if (path2 != null && path2.trim().length() > 0) {
                                    // copiando version actual
                                    File sourcePath = new File(WBUtils.getInstance().getWorkPath() + sourceDirectory);

                                    if (sourcePath.exists() && sourcePath.isDirectory()) //revisar si existe la fuente, si no hace nada y regresa false
                                    {
                                        File targetPath = new File(targetDirectory);
                                        if (!targetPath.exists()) // si no existe el target lo crea
                                        {
                                            targetPath.mkdirs();
                                        }
                                        if (targetPath != null && targetPath.exists()) //si existe el target
                                        {
                                            String sourceWebPath =  AFUtils.getInstance().getEnv("wb/sourceWebPath");
                                            System.out.println("sourceWebPath:"+sourceWebPath);
                                            //if(null!=sourceWebPath&&sourceWebPath.trim().length()==0)
                                                sourceWebPath="";

                                            String sourceParserPath = sourceWebPath+WBUtils.getInstance().getWebWorkPath() + sourceDirectory;
                                            System.out.println("Ruta origen:"+sourceParserPath);

                                            String destinationWebWorkPath =  AFUtils.getInstance().getEnv("swb/webWorkPath");
                                            if(null!=destinationWebWorkPath&&destinationWebWorkPath.trim().length()==0) destinationWebWorkPath="";

                                            String destinationWebPath =  AFUtils.getInstance().getEnv("swb/destinationWebPath");
                                            if(null!=destinationWebPath&&destinationWebPath.trim().length()==0) destinationWebPath="";

                                            String targetParserPath = destinationWebPath+destinationWebWorkPath + destinationPath;
                                            System.out.println("Nueva Ruta:"+targetParserPath);

                                            AFUtils.getInstance().copyStructure(WBUtils.getInstance().getWorkPath() + sourceDirectory, targetDirectory, true, sourceParserPath, targetParserPath);
                                        }
                                    }
                                }

                                if (rtpl.getId() > idmax) {
                                    idmax = rtpl.getId();
                                }
                                numTemplates++;

                                if (templ.getInterval() != null) {
                                    //System.out.println("Revisando Intervalo Template... ");

                                    String xmlconfig = templ.getXml();

                                    if (xmlconfig != null) {
                                        if (xmlconfig.indexOf("<interval>") > 0) {
                                            String intervalo = xmlconfig.substring(xmlconfig.indexOf("<interval>"), xmlconfig.lastIndexOf("</interval>") + 11);
                                            //System.out.println("Intervalo (Template): " + intervalo);

                                            Document dom = SWBUtils.XML.xmlToDom(intervalo);
                                            String titulo = dom.getElementsByTagName("title").item(0).getFirstChild().getNodeValue();
                                            //System.out.println("Titulo (Template): " + titulo);

                                            org.semanticwb.model.Calendar schedule = ws.createCalendar();
                                            schedule.setActive(Boolean.TRUE);
                                            schedule.setTitle(titulo);
                                            schedule.getSemanticObject().setProperty(org.semanticwb.model.Calendar.swb_xml, intervalo);

                                            CalendarRef calRef = CalendarRef.ClassMgr.createCalendarRef(ws);
                                            calRef.setCalendar(schedule);
                                            calRef.setActive(Boolean.TRUE);
                                            tpl.addCalendarRef(calRef);
                                        }
                                    }
                                }


                                // Revisando roles asociados al template
                                // Roles
                                Iterator itroles = templ.getRoles();
                                while (itroles.hasNext()) {
                                    Integer irole = (Integer) itroles.next();
                                    //System.out.println("template/role: " + irole.toString());
                                    String usrreptemp = usrrep;
                                    if (usrrep.startsWith("swb4|")) {
                                        usrreptemp = usrrep.substring(usrrep.indexOf("swb4|") + 5);
                                    }
                                    org.semanticwb.model.Role rrole = UserRepository.ClassMgr.getUserRepository(usrreptemp).getRole(Integer.toString(irole.intValue()));
                                    if (rrole != null) {
                                        RoleRef rolref = ws.createRoleRef();
                                        rolref.setRole(rrole);
                                        rolref.setActive(Boolean.TRUE);
                                        tpl.addRoleRef(rolref);
                                    }
                                }

                                //Revisando reglas asociadas al template
                                Iterator itrules = templ.getRules();
                                while (itrules.hasNext()) {
                                    Integer irule = (Integer) itrules.next();
                                    org.semanticwb.model.Rule rrule = ws.getRule(Integer.toString(irule.intValue()));
                                    RuleRef rulref = ws.createRuleRef();
                                    rulref.setRule(rrule);
                                    rulref.setActive(Boolean.TRUE);
                                    tpl.addRuleRef(rulref);
                                }


                            }
                            idmax++;
                            ws.getSemanticObject().getModel().setCounterValue(org.semanticwb.model.Template.sclass.getClassGroupId(), idmax);

                            //Revisando y relacionando templates con las secciones

                            //Para el home
                            setWebPageAssociation(tm.getHome(), ws, TopicMap.CNF_WBTemplate, usrrep);

                            //Para el resto de la estructura del sitio
                            reviewWebPageAsoc(tm.getHome(), ws, TopicMap.CNF_WBTemplate, usrrep);
                        }
                    }
                }
        %>
        <div>
            <%
                HashMap hmcat = new HashMap();
                int nwp = 0;
                int nptype = 0;
                ws = WebSite.ClassMgr.getWebSite(tm.getId());
                ws.getSemanticObject().getModel().setTraceable(false);
                //obteniendo num. de elementos existentes migrados
                hmcat = getNumCatElements(ws);
            %>
            <form name="fstep6" id="fstep6" method="post" >
                <input type="hidden" name="act" value="step6" />
                <input type="hidden" name="path2" value="<%=path2%>" />
                <input type="hidden" name="usrrep" value="<%=usrrep%>" />
                <input type="hidden" name="site" value="<%=site%>" />
                <input type="hidden" name="isImported" value="<%=isImported%>" />

                <fieldset  style="background-color:#A9F5D0;"><legend>&nbsp;&nbsp;Lista de Catálogos - Paso 1 (Completado)&nbsp;&nbsp;</legend>
                    <ul>
                        <li><input type="checkbox" name="catalog" id="cat1" value="language" disabled><label for="cat1">Lenguajes <%=hmcat.get("language") != null ? "(" + hmcat.get("language") + ")" : ""%></label></li>
                        <li><input type="checkbox" name="catalog" id="cat2" value="device" disabled><label for="cat2">Dispositivos <%=hmcat.get("device") != null ? "(" + hmcat.get("device") + ")" : ""%></label></li>
                        <li><input type="checkbox" name="catalog" id="cat9" value="dns" disabled><label for="cat9">DNS <%=hmcat.get("dns") != null ? "(" + hmcat.get("dns") + ")" : ""%></label></li>
                        <li><input type="checkbox" name="catalog" id="cat11" value="rules" disabled><label for="cat11">Reglas <%=hmcat.get("rules") != null ? "(" + hmcat.get("rules") + ")" : ""%></label></li>
                        <li><input type="checkbox" name="catalog" id="cat4" value="pflow" disabled><label for="cat4">Flujos de publicación <%=hmcat.get("pflow") != null ? "(" + hmcat.get("pflow") + ")" : ""%></label></li>
                        <li><input type="checkbox" name="catalog" id="cat3" value="resourcetype" disabled><label for="cat3">Tipos de recursos <%=hmcat.get("resourcetype") != null ? "(" + hmcat.get("resourcetype") + ")" : ""%></label></li>
                        <li><input type="checkbox" name="catalog" id="cat6" value="grouptemplate" disabled><label for="cat6">Grupos de plantillas <%=hmcat.get("grouptemplate") != null ? "(" + hmcat.get("grouptemplate") + ")" : ""%></label></li>
                        <!-- li><input type="checkbox" name="catalog" id="cat8" value="camp"><label for="cat8">Campañas</label></li -->
                    </ul>
                </fieldset>
                <fieldset  style="background-color:#A9F5D0;"><legend>&nbsp;&nbsp;Lista de Catálogos - Paso 2 (Completado)&nbsp;&nbsp;</legend>
                    <ul>
                        <li><input type="checkbox" name="catalog" id="cat8" value="resourcesubtype" disabled><label for="cat8">Sub-Tipos de recursos <%=hmcat.get("resourcesubtype") != null ? "(" + hmcat.get("resourcesubtype") + ")" : ""%></label></li>
                        <li><input type="checkbox" name="catalog" id="cat5" value="template" disabled><label for="cat5">Plantillas <%=hmcat.get("template") != null ? "(" + hmcat.get("template") + ")" : ""%></label></li>
                    </ul>
                </fieldset>

                <fieldset style="background-color:#F7BE81;"><legend>&nbsp;&nbsp;Lista de Catálogos - Paso 3 de 3&nbsp;&nbsp;</legend>
                    <%
                boolean doResType = true;
                if (hmcat.get("resourcetype") == null) {
                    doResType = false;
                    %>
                    <p>Si no hay Tipos de Recursos, no se podrán migrar subtipos de recursos, ni recursos.</p>
                    <%
                }
                    %>
                    <p>La migración de recursos, posiblemente deba hacerse en varios intentos, esto es por el copiado de archivos y el volumen de información que se tenga, ya que puede acabarse la sesión y no terminen de migrarse todos los recursos, al correr el script nuevamente, copiará los recursos faltantes sólamente.</p>
                    <ul>
                        <li><input type="checkbox" name="catalog" id="cat7" value="resource" ><label for="cat7">Recursos <%=hmcat.get("resource") != null ? "(" + hmcat.get("resource") + ")" : ""%></label></li>
                        <li><input type="checkbox" name="wpassoc" id="cat10" value="assoc" ><label for="cat10">Revisar Asociaciones de Recursos con Secciones (se debe hacer sólo una vez, si ya se acabaron de migrar todos los recursos)</label></li>
                    </ul>
                </fieldset>
                <fieldset>
                    <button name="b_send" id="b_send" type="submit">Continuar</button>
                </fieldset>
            </form>
        </div>
        <%
            } // Impportación de catálogos paso 3 de 3 (Recursos)
            else if ("step6".equals(action)) {

                HashMap hmpfres = new HashMap();

                boolean reviewWP = false;
                TopicMap tm = TopicMgr.getInstance().getTopicMap(site);
                WebSite ws = null;
                ws = WebSite.ClassMgr.getWebSite(tm.getId());
                ws.getSemanticObject().getModel().setTraceable(false);

                String usrreptemp = usrrep;
                if (usrrep.startsWith("swb4|")) {
                    usrreptemp = usrrep.substring(usrrep.indexOf("swb4|") + 5);
                }
                UserRepository urep = UserRepository.ClassMgr.getUserRepository(usrreptemp);
                int numLang = 0, numDev = 0, numResType = 0, numResSubType = 0, numPflow = 0, numTemplates = 0, numGTemplate, numres = 0, numDns = 0, numRules = 0, numRoles = 0;

                long idmax = 0;
                String[] arrCat = request.getParameterValues("catalog");
                String wpassoc = request.getParameter("wpassoc");
                if(null!=wpassoc&&wpassoc.equals("assoc")) reviewWP=true;
                if (null != arrCat) {
                    for (int i = 0; i < arrCat.length; i++) {

                        out.println(" val : " + arrCat[i]);

                        if ("resource".equals(arrCat[i])) {
                            idmax = 0;
                            int numRes= 0;
                            Collection colres = com.infotec.wb.core.ResourceMgr.getInstance().getResources(site).values();
                            numres = colres.size();
                            Iterator itres = colres.iterator();

                            SemanticOntology ont = SWBPlatform.getSemanticMgr().getOntology();
                            while (itres.hasNext()) {
                                WBResource rres = (WBResource) itres.next();
                                com.infotec.wb.core.db.RecResource rresb = rres.getResourceBase().getRecResource();

                                numRes++;
                                if((numRes%10)==0)System.out.println("========>"+numRes);
                                try{
                                    org.semanticwb.model.Resource res = null;
                                    String user = (String) session.getAttribute("user");
                                    String password = (String) session.getAttribute("password");
                                    String siteid = tm.getId();

                                    res = ws.getResource(Long.toString(rresb.getId()));

                                    if(res==null)
                                        {
                                            try
                                            {
                                                res = MigrateOfficeContents.migrateResourceFrom32(rres, siteid, new File(path2));
                                            }
                                            catch(Exception e)
                                            {
                                                System.out.println("Error al migrar LocalContent."+e.getMessage());
                                                System.out.println(e.getStackTrace());
                                                res=null;
                                            }
                                            if (res == null) {
                                                res = ws.createResource(Long.toString(rresb.getId()));
                                                String idName = rresb.getResourceType().getName();
                                                idName = idName.trim();
                                                idName = idName.replaceAll(" ", "");

                                                String clsName = rresb.getResourceType().getObjclass();

                                                // Validacion de recursos de WB3.2 a SWB4.0
                                                if (clsName.equals("com.infotec.wb.resources.Content")||clsName.equals("com.infotec.wb.resources.PPTContent")||clsName.equals("com.infotec.wb.resources.ExcelContent")) {
                                                    idName = "HTMLContent";
                                                }
                                                org.semanticwb.model.ResourceType restype = ws.getResourceType(idName);

                                                if(restype!=null)out.println("restype: "+restype.getTitle()+" "+restype.getResourceClassName());
                                                if (restype == null)
                                                {
                                                    restype = ws.createResourceType("HTMLContent");
                                                    restype.setResourceClassName("org.semanticwb.portal.resources.sem.HTMLContent");
                                                    restype.setResourceBundle("org.semanticwb.portal.resources.sem.HTMLContent");
                                                    restype.setResourceMode(1);
                                                    //ptype.setResourceCache(600);
                                                    restype.setTitle("HTMLContent");
                                                }


                                                res.setResourceType(restype);

                                                RecSubType rsty = DBCatalogs.getInstance().getSubType(site,rresb.getIdSubType());

                                                if(rsty!=null)
                                                {
                                                    org.semanticwb.model.ResourceSubType resSubtype = ws.getResourceSubType(Integer.toString(rsty.getId()));  //rsty.getTitle()
                                                    if (null != resSubtype) {
                                                        res.setResourceSubType(resSubtype);
                                                    }
                                                }

                                                // validando tipo de contenido para agregar version 1 al recurso
                                                String actVersion = "";
                                                String initVersion = "";
                                                if(idName.equals("HTMLContent"))
                                                {
                                                    actVersion = Integer.toString(rresb.getActualversion())+"/";
                                                    initVersion = "1/";
                                                }



                                                String sourceWebPath = "/work/sites/" + site + "/resources/" + rresb.getResourceType().getName() + "/" + rresb.getId() + "/"+actVersion; //WBUtils.getInstance().getWebWorkPath() +
                                                String targetWebPath = "/work/models/" + site + "/Resource/"  + rresb.getId() + "/"+initVersion; //+ restype.getId() + "/"

                                                //System.out.println("source WEBPATH:"+sourceWebPath);
                                                //System.out.println("target WEBPATH:"+targetWebPath);


                                                res.setTitle(rresb.getTitle());
                                                res.setDescription(rresb.getDescription());
                                                //res.setActive(rresb.getActive() == 1 ? true : false);
                                                if (null != rresb.getXml()) {

                                                    String xmlTemp = rresb.getXml();
                                                    //Document domdoc = SWBUtils.XML.xmlToDom(xmlTemp);
                                                    String fileName = null;
                                                    xmlTemp = xmlTemp.replaceAll("/work/sites/"+site+"/", "/work/models/"+site+"/");
                                                    // validando tipo de contenido para obtener el nombre del archivo

                                                    if(idName.equals("HTMLContent")) //&& domdoc!=null
                                                    {
                                                        //System.out.println("Es HTMLContent...");
                                                        fileName = rres.getResourceBase().getAttribute("url"+rresb.getActualversion());
                                                        //System.out.println("Obteniendo el nombre del archivo...."+fileName);
                                                        ///String id = res.getURI();
                                                        SWBResource go = SWBPortal.getResourceMgr().getResource(res);
                                                        if(go!=null && go instanceof Versionable)
                                                        {
                                                            Versionable gov = (Versionable) go;
                                                            VersionInfo vi = VersionInfo.ClassMgr.createVersionInfo(ws);
                                                            vi.setVersionNumber(1);
                                                            vi.setVersionComment("contenido migrado");
                                                            vi.setVersionFile(fileName);
                                                            gov.setActualVersion(vi);
                                                            gov.setLastVersion(vi);

                                                            //System.out.println("Asignando versiones...");
                                                        }
                                                    }
                                                    res.getSemanticObject().setProperty(org.semanticwb.model.Resource.swb_xml, xmlTemp);
                                                }

                                               
                                                if (rresb.getId() > idmax) {
                                                    idmax = rresb.getId();
                                                }
                                                numres++;

                                                //Copiando archivos

                                                String sourceDirectory = "/sites/" + site + "/resources/" + rresb.getResourceType().getName() + "/" + rresb.getId() + "/"+actVersion;
                                                String targetDirectory = path2 + site + "/Resource/" + rresb.getId() + "/" + initVersion; //"1/"; + restype.getId() + "/"

                                                if (path2 != null && path2.trim().length() > 0) {
                                                    // copiando version actual
                                                    File sourcePath = new File(WBUtils.getInstance().getWorkPath() + sourceDirectory);
                                                    if (sourcePath.exists() && sourcePath.isDirectory()) //revisar si existe la fuente, si no hace nada y regresa false
                                                    {
                                                        File targetPath = new File(targetDirectory);
                                                        if (!targetPath.exists()) // si no existe el target lo crea
                                                        {
                                                            targetPath.mkdirs();
                                                        }
                                                        if (targetPath != null && targetPath.exists()) //si existe el target
                                                        {

                                                            System.out.println("AFUtils.getInstance().copyStructure("+WBUtils.getInstance().getWorkPath() + sourceDirectory+","+targetDirectory+",true,"+ WBUtils.getInstance().getWebWorkPath() + sourceDirectory+","+targetWebPath+")");

                                                            AFUtils.getInstance().copyStructure(WBUtils.getInstance().getWorkPath() + sourceDirectory, targetDirectory, true, sourceWebPath, targetWebPath);
                                                        }
                                                    }
                                                }

                                            }
                                             if(res!=null) res.setActive(rresb.getActive() == 1 ? true : false);

                                            //System.out.println("Reviando roles...." + rresb.getId());
                                            Iterator itroles = rres.getResourceBase().getRoles();
                                            while (itroles.hasNext()) {
                                                Integer irole = (Integer) itroles.next();
                                                //System.out.println("role: " + irole.toString());
                                                org.semanticwb.model.Role rrole = urep.getRole(irole.toString());
                                                if (rrole != null) {
                                                    RoleRef rolref = ws.createRoleRef();
                                                    rolref.setRole(rrole);
                                                    rolref.setActive(Boolean.TRUE);
                                                    res.addRoleRef(rolref);
                                                }
                                            }

                                            if (rres.getResourceBase().getInterval() != null) {
                                                //System.out.println("Revisando Intervalo... ");

                                                String xmlconfig = rresb.getXmlConf();

                                                if (xmlconfig != null) {
                                                    if (xmlconfig.indexOf("<interval>") > 0) {
                                                        String intervalo = xmlconfig.substring(xmlconfig.indexOf("<interval>"), xmlconfig.lastIndexOf("</interval>") + 11);
                                                        //System.out.println("Intervalo: " + intervalo);

                                                        Document dom = SWBUtils.XML.xmlToDom(intervalo);
                                                        String titulo = dom.getElementsByTagName("title").item(0).getFirstChild().getNodeValue();
                                                        //System.out.println("Titulo: " + titulo);

                                                        org.semanticwb.model.Calendar schedule = ws.createCalendar();
                                                        schedule.setActive(Boolean.TRUE);
                                                        schedule.setTitle(titulo);
                                                        schedule.getSemanticObject().setProperty(org.semanticwb.model.Calendar.swb_xml, intervalo);

                                                        CalendarRef calRef = CalendarRef.ClassMgr.createCalendarRef(ws);
                                                        calRef.setCalendar(schedule);
                                                        calRef.setActive(Boolean.TRUE);
                                                        res.addCalendarRef(calRef);

                                                    }
                                                }
                                            }

                                            if (rres.getResourceBase().getFilterMap() != null) {
                                                //System.out.println("Revisando Filtro... ");

                                                String xmlconfig = rresb.getXmlConf();

                                                if (xmlconfig != null) {
                                                    if (xmlconfig.indexOf("<filter>") > 0) {
                                                        String filtro = xmlconfig.substring(xmlconfig.indexOf("<filter>"), xmlconfig.lastIndexOf("</filter>") + 9);
                                                        //System.out.println("Filtro: " + filtro);

                                                        Document dom = SWBUtils.XML.xmlToDom(filtro);

                                                        ResourceFilter resfilter = ws.createResourceFilter();
                                                        resfilter.getSemanticObject().setProperty(ResourceFilter.swb_xml, SWBUtils.XML.domToXml(dom));
                                                        res.setResourceFilter(resfilter);
                                                    }
                                                }
                                            }

                                            //System.out.println("Revisando reglas asociadas al recurso");
                                            //Revisando reglas asociadas al recurso
                                            Iterator itrules = rres.getResourceBase().getRules();
                                            while (itrules.hasNext()) {
                                                Integer irule = (Integer) itrules.next();
                                                org.semanticwb.model.Rule rrule = ws.getRule(Integer.toString(irule.intValue()));
                                                RuleRef rulref = ws.createRuleRef();
                                                rulref.setRule(rrule);
                                                rulref.setActive(Boolean.TRUE);
                                                res.addRuleRef(rulref);
                                            }

                                            //System.out.println("Revisando PFLows asociados al recurso");
                                            //Revisando PFLows asociados al recurso

                                            PFlowSrv pfsrv = new PFlowSrv();
                                            HashMap hmpf = PFlowMgr.getInstance().getPFlows(site);
                                            Iterator itpflow = hmpf.values().iterator();
                                            while (itpflow.hasNext()) {
                                                com.infotec.wb.core.PFlow pf = (com.infotec.wb.core.PFlow) itpflow.next();
                                                org.semanticwb.model.PFlow spf = ws.getPFlow(Integer.toString(pf.getId()));

                                                if (null != spf) {
                                                    Iterator itocc = pfsrv.getContentsAtFlow(pf);
                                                    while (itocc.hasNext()) {
                                                        Occurrence occ = (Occurrence) itocc.next();
                                                        if (occ.getResourceData() != null) {
                                                            org.semanticwb.model.Resource sres = ws.getResource(occ.getResourceData());
                                                            //RecResource recres = DBResource.getInstance().getResource(occ.getDbdata().getIdtm(), Long.parseLong(occ.getResourceData()));
                                                            if (null != sres) {
                                                                PFlowRef spfr = ws.createPFlowRef();
                                                                spfr.setActive(occ.isActive());
                                                                spfr.setPflow(spf);
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    catch(Exception ex)
                                    {
                                        System.out.println("Exception... al migrar recurso ...id:"+rresb.getId()+"\n\r"+ex.getMessage());
                                        System.out.println(ex.getStackTrace());
                                    }

                            }  // while
                            idmax++;
                            ws.getSemanticObject().getModel().setCounterValue(org.semanticwb.model.Resource.sclass.getClassGroupId(), idmax);

                        } //if ---resource---
                    } //for
                } // if array

                System.out.println("Terminando de generar los recursos");

                if(reviewWP)
                {
                    System.out.println("Revisando y relacionando recursos con las secciones");
                    //Revisando y relacionando recursos con las secciones

                    //Para el home
                    setWebPageAssociation(tm.getHome(), ws, TopicMap.REC_WBContent, usrrep);

                    //Para el resto de la estructura del sitio
                    reviewWebPageAsoc(tm.getHome(), ws, TopicMap.REC_WBContent, usrrep);

                    // Asociaciones de los webpages si existen asociaciones con roles y reglas
                    System.out.println("Revisando asociaciones de roles con webpages");

                    //Para el home se revisan asociaciones con roles
                    setWebPageAssociation(tm.getHome(), ws, TopicMap.CNF_WBRole, usrrep);

                    //Para el resto de la estructura del sitio las asociaciones de roles
                    reviewWebPageAsoc(tm.getHome(), ws, TopicMap.CNF_WBRole, usrrep);

                    System.out.println("Revisando asociaciones de reglas con webpages");
                    //Para el home se revisan las asociaciones con las reglas
                    setWebPageAssociation(tm.getHome(), ws, TopicMap.CNF_WBRule, usrrep);

                    //Para el resto de la estructura del sitio las asociaciones con reglas
                    reviewWebPageAsoc(tm.getHome(), ws, TopicMap.CNF_WBRule, usrrep);

                    System.out.println("Revisando asociaciones de PFlows con webpages");
                    //Para el home se revisan las asociaciones con los flujos de publicacion
                    setWebPageAssociation(tm.getHome(), ws, TopicMap.CNF_WBPFlow, usrrep);

                    //Para el resto de la estructura del sitio las asociaciones con los flujos de publicacion
                    reviewWebPageAsoc(tm.getHome(), ws, TopicMap.CNF_WBPFlow, usrrep);


                    System.out.println("Terminando de Revisar asociaciones con webpages");
                }

        %>
        <div>
            <%
                HashMap hmcat = new HashMap();
                int nwp = 0;
                int nptype = 0;
                ws = WebSite.ClassMgr.getWebSite(tm.getId());
                ws.getSemanticObject().getModel().setTraceable(false);
                //obteniendo num. de elementos existentes migrados
                hmcat = getNumCatElements(ws);
            %>
            <form name="fstep7" id="fstep7" method="post" >
                <input type="hidden" name="act" value="step7" />
                <input type="hidden" name="path2" value="<%=path2%>" />
                <input type="hidden" name="usrrep" value="<%=usrrep%>" />
                <input type="hidden" name="site" value="<%=site%>" />
                <input type="hidden" name="isImported" value="<%=isImported%>" />
            <fieldset  style="background-color:#A9F5D0;"><legend>&nbsp;&nbsp;Lista de Catálogos - Paso 1 (Completado)&nbsp;&nbsp;</legend>
                <ul>
                    <li><input type="checkbox" name="catalog" id="cat1" value="language" disabled><label for="cat1">Lenguajes <%=hmcat.get("language") != null ? "(" + hmcat.get("language") + ")" : ""%></label></li>
                    <li><input type="checkbox" name="catalog" id="cat2" value="device" disabled><label for="cat2">Dispositivos <%=hmcat.get("device") != null ? "(" + hmcat.get("device") + ")" : ""%></label></li>
                    <li><input type="checkbox" name="catalog" id="cat9" value="dns" disabled><label for="cat9">DNS <%=hmcat.get("dns") != null ? "(" + hmcat.get("dns") + ")" : ""%></label></li>
                    <li><input type="checkbox" name="catalog" id="cat11" value="rules" disabled><label for="cat11">Reglas <%=hmcat.get("rules") != null ? "(" + hmcat.get("rules") + ")" : ""%></label></li>
                    <li><input type="checkbox" name="catalog" id="cat4" value="pflow" disabled><label for="cat4">Flujos de publicación <%=hmcat.get("pflow") != null ? "(" + hmcat.get("pflow") + ")" : ""%></label></li>
                    <li><input type="checkbox" name="catalog" id="cat3" value="resourcetype" disabled><label for="cat3">Tipos de recursos <%=hmcat.get("resourcetype") != null ? "(" + hmcat.get("resourcetype") + ")" : ""%></label></li>
                    <li><input type="checkbox" name="catalog" id="cat6" value="grouptemplate" disabled><label for="cat6">Grupos de plantillas <%=hmcat.get("grouptemplate") != null ? "(" + hmcat.get("grouptemplate") + ")" : ""%></label></li>
                    <!-- li><input type="checkbox" name="catalog" id="cat8" value="camp"><label for="cat8">Campañas</label></li -->
                </ul>
            </fieldset>
            <fieldset  style="background-color:#A9F5D0;"><legend>&nbsp;&nbsp;Lista de Catálogos - Paso 2 (Completado)&nbsp;&nbsp;</legend>
                <ul>
                    <li><input type="checkbox" name="catalog" id="cat8" value="resourcesubtype" disabled><label for="cat8">Sub-Tipos de recursos <%=hmcat.get("resourcesubtype") != null ? "(" + hmcat.get("resourcesubtype") + ")" : ""%></label></li>
                    <li><input type="checkbox" name="catalog" id="cat5" value="template" disabled><label for="cat5">Plantillas <%=hmcat.get("template") != null ? "(" + hmcat.get("template") + ")" : ""%></label></li>
                </ul>
            </fieldset>
            <fieldset  style="background-color:#A9F5D0;"><legend>&nbsp;&nbsp;Lista de Catálogos - Paso 3 (Completado)&nbsp;&nbsp;</legend>
                <ul>
                    <li><input type="checkbox" name="catalog" id="cat7" value="resource" disabled><label for="cat7">Recursos <%=hmcat.get("resource") != null ? "(" + hmcat.get("resource") + ")" : ""%></label></li>
                    <li><input type="checkbox" name="wpassoc" id="cat10" value="assoc" disabled><label for="cat10">Revisar Asociaciones de Recursos con Secciones (se debe hacer sólo una vez, si y solo si ya se acabaron de migrar todos los recursos)</label></li>
                </ul>
            </fieldset>
            <fieldset  style="background-color:#F7BE81;"><legend>&nbsp;&nbsp;Revision y generación de Asociaciones entre secciones&nbsp;&nbsp;</legend>
                <ul>
                    <li><input type="checkbox" name="catalog" id="cat13" value="resdata" checked><label for="cat13">Datos de recursos</label></li>
                    <li><input type="checkbox" name="catalog" id="cat12" value="assocsbtwp" checked><label for="cat12">Revisar Asociaciones entre Secciones (se debe hacer sólo una vez)</label></li>
                </ul>
            </fieldset>
            <fieldset style="background-color:#F7BE81;">
                <!--form id="inicio" name="finicio" method="post" action="" -->
                    <button name="b_send" id="b_send" type="submit" >Revisar asociaciones entre secciones</button>
            </fieldset>
            </form>
        </div>
        <%
            }
    // Impportación de ASOCIACIONES ENTRE SECCIONES Y COPIADO DE DATOS DE LOS RECURSOS
            else if ("step7".equals(action))
            {
                boolean reviewWP = false;
                TopicMap tm = TopicMgr.getInstance().getTopicMap(site);
                WebSite ws = null;
                ws = WebSite.ClassMgr.getWebSite(tm.getId());
                ws.getSemanticObject().getModel().setTraceable(false);

                /////////////// RREVISION E IMPORTACION DE ASOCIACIONES ENTRE SECCIONES

                //importando asociaciones

                //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                //
                //
                //  Eliminando asociaciones del sitio de SWB4 para hacer pruebas y no tener que importar nuevamente todo el sitio.
                //
                //

/*
                HashMap<String,WebPage> hmTypes = new HashMap();
		int asocex =0;
                Iterator<org.semanticwb.model.Association> itassoc = ws.listAssociations();
                while (itassoc.hasNext())
                {
		    asocex++;
                    org.semanticwb.model.Association assoc = itassoc.next();
                    System.out.println("Tipo Assoc: " +assoc.getType().getId());
                    hmTypes.put(assoc.getType().getId(), ws.getWebPage(assoc.getType().getId()));
                    assoc.remove();
                    System.out.println("quitando..."+asocex);
                }

                System.out.println("Eliminando WP tipos...");
                Iterator<String> ittyp = hmTypes.keySet().iterator();
                while (ittyp.hasNext()) {
                    String key = ittyp.next();
                    System.out.println("WP ID: "+key);
                    WebPage wp = hmTypes.get(key);
                    wp.remove();
                }

 * */
                //
                //
                //
                //////////////////////////////////////////////////////////////////////////////////////////////////////////////////

                long idmax = 0;
                String[] arrCat = request.getParameterValues("catalog");
                String wpassoc = request.getParameter("wpassoc");
                if(null!=wpassoc&&wpassoc.equals("assoc")) reviewWP=true;
                if (null != arrCat)
                {
                    for (int i = 0; i < arrCat.length; i++) {

                        out.println(" val : " + arrCat[i]);

                        if ("resdata".equals(arrCat[i]))
                        {
                            Connection conn = null;
                            PreparedStatement pst = null;
                            ResultSet rs = null;
                            try
                            {
                                long resid = 0;
                                String residtm="", topicmapid="", topicid="", userid="", usertp="", data="";
                                org.semanticwb.model.User swbusr = null;
                                org.semanticwb.model.WebPage swbwp = null;
                                org.semanticwb.model.Resource swbres = null;
                                conn = AFUtils.getDBConnection("wb","Copiado de datos de recursos");
                                pst = conn.prepareStatement("select resid, residtm, topicmapid, topicid, userid, usertp, data, lastupdate from wbresourcedata where residtm = ?");
                                pst.setString(1, site);
                                rs = pst.executeQuery();
                                while(rs.next())
                                {
                                    swbusr = null;
                                    swbwp = null;
                                    swbres = null;
                                    resid = rs.getLong("resid");
                                    residtm = rs.getString("residtm");
                                    topicmapid = rs.getString("topicmapid");
                                    topicid = rs.getString("topicid");
                                    userid = rs.getString("userid");
                                    usertp = rs.getString("usertp");
                                    data = rs.getString("data");
                                    //validando usuario
                                    if(userid!=null&&userid.trim().length()>1&&userid.indexOf("_")>0)
                                    {
                                        userid=userid.substring(0,userid.lastIndexOf("_"));
                                        System.out.println("userid:"+userid);
                                        swbusr = ws.getUserRepository().getUser(userid);
                                    }
                                    //validando webpage
                                    if(topicid!=null&&topicid.trim().length()>1)
                                    {
                                        System.out.println("wpid:"+topicid);
                                        swbwp=ws.getWebPage(topicid);
                                    }
                                    //obteniendo recurso en swb
                                    swbres = ws.getResource(Long.toString(resid));
                                    if(swbres!=null&&null!=data&&data.trim().length()>0)
                                    {
                                        if(null!=swbusr&&null!=swbwp)
                                        {
                                            swbres.setData(swbusr, swbwp, data);
                                        }
                                        else if(null==swbusr&&null!=swbwp)
                                        {
                                            swbres.setData(swbwp, data);
                                        }
                                        else if(null!=swbusr&&null==swbwp)
                                        {
                                            swbres.setData(swbusr, data);
                                        }
                                        else
                                        {
                                            swbres.setData(data);
                                        }
                                        swbres.updateAttributesToDB();
                                    }
                                }
                                rs.close();
                                pst.close();
                                conn.close();
                                System.out.println("Terminando copiado de datos de los recursos.");
                            }
                            catch (Exception e)
                            {
                                System.out.println("Error al copiar datos de recursos.");
                            }
                            finally
                            {
                                if(rs!=null) rs.close();
                                if(pst!=null) pst.close();
                                if(conn!=null) conn.close();
                            }
                        }
                        else if ("assocsbtwp".equals(arrCat[i]))
                        {
                            HashMap hmasocwb3 = tm.getAssociations();
                            Iterator it = hmasocwb3.values().iterator();
                            while (it.hasNext())
                            {
                               //System.out.println("Obteniendo asociacion.");
                               com.infotec.topicmaps.Association elem = (com.infotec.topicmaps.Association)it.next();

                               com.infotec.topicmaps.Topic tpTipo = elem.getType();
                               if(tpTipo!=null)
                               {
                                   //System.out.println("Obteniendo WP tipo p/asoc. igual al tipo original: "+tpTipo.getId());
                                   org.semanticwb.model.Topic wpTipo = ws.getWebPage(tpTipo.getId());

                                   if(wpTipo==null)
                                   {
                                       WebPage wpage = copyValues(tpTipo, ws);
                                       //parentReview(tpTipo,wpage);
                                       wpTipo = ws.getWebPage(tpTipo.getId());
                                   }
                                   if(wpTipo!=null)
                                   {
                                       WebPage wpType = ws.getWebPage(wpTipo.getId());
                                       parentReview(tpTipo,wpType);
                                       //System.out.println("Creando asociacion.");
                                       org.semanticwb.model.Association asoc = ws.createAssociation(elem.getId());
                                       asoc.setType(wpTipo);

                                       Iterator itmember = elem.getMembers().iterator();
                                       //Agregando los miembros existentes ligados a este tipo de asociacion
                                       //System.out.println("Antes de revisar miembros de la asociacion.");
                                       while(itmember.hasNext())
                                       {
                                           com.infotec.topicmaps.Member mem = (com.infotec.topicmaps.Member)itmember.next();
                                           //System.out.println("Obteniendo miembro.");

                                           org.semanticwb.model.AssMember assmember = ws.createAssMember(mem.getId());
                                           com.infotec.topicmaps.RoleSpec rolmem = mem.getRoleSpec();
                                           Iterator memite = mem.getTopicRefs().keySet().iterator();
                                           String topicoid = null;
                                           if(memite.hasNext()) topicoid = (String) memite.next();
                                           WebPage wpTopic = null;
                                           if(topicoid!=null)
                                           {
                                               wpTopic = ws.getWebPage(topicoid);
                                               com.infotec.topicmaps.Topic tpmem = tm.getTopic(topicoid);
                                               if(null==wpTopic && tpmem!=null)
                                               {
                                                   //System.out.println("============================== ParentReview Member-Assoc.");
                                                   wpTopic = copyValues(tpmem, ws);
                                                   parentReview(tpmem, wpTopic);
                                               }

                                           }
                                           WebPage wpRole = null;
                                           if(rolmem!=null)
                                           {
                                               //System.out.println("Assoc. TopicRef: "+rolmem.getTopicRef().getId());
                                               wpRole = ws.getWebPage(rolmem.getTopicRef().getId());
                                               com.infotec.topicmaps.Topic tpmem = rolmem.getTopicRef();
                                               if(null==wpTopic && tpmem!=null)
                                               {
                                                    //System.out.println("============================== ParentReview Role-Assoc.");
                                                    wpRole = copyValues(tpmem, ws);
                                                    parentReview(tpmem, wpRole);
                                               }
                                           }
                                           if(null!=wpTopic && wpRole!=null)
                                           {
                                               //se agrega rol del miembre
                                               assmember.setRole(wpRole);
                                               //se agrega el topico
                                               assmember.setMember(wpTopic);
                                               // se agrega miembreo al tipo de asociacion
                                               //System.out.println("Agregando miembro p/asociacion.");
                                               asoc.addMember(assmember);
                                           }
                                       }
                                   }
                               }
                            }
                            System.out.println("Se importo las asociaciones del sitio");
                        }
                    }
                }

                // modificanco pflows por id de tipo de recurso
                Iterator<org.semanticwb.model.PFlow> itpf = ws.listPFlows();
                while(itpf.hasNext())
                {
                    org.semanticwb.model.PFlow pf = itpf.next();
                    String xmltmp = pf.getXml();
                    if(null!=xmltmp)
                    {
                        try
                        {
                            Document dom = SWBUtils.XML.xmlToDom(xmltmp);
                            NodeList nl = dom.getElementsByTagName("resourceType");
                            for(int i=0; i<nl.getLength(); i++)
                            {
                                Node nodo = nl.item(i);
                                String idrestype = nodo.getAttributes().getNamedItem("id").getNodeValue();
                                String namerestype = nodo.getAttributes().getNamedItem("name").getNodeValue();
                                String tmrestype = nodo.getAttributes().getNamedItem("topicmap").getNodeValue();

                                //////////////////////////////////////////
                                // para obtener el tipo de recurso correspondiente
                                ///////////////////////////////////////////////////

                                RecResourceType recrestype = null;
                                if(tmrestype.equals(TopicMgr.TM_GLOBAL))
                                {
                                    recrestype = DBResourceType.getInstance().getResourceType(TopicMgr.TM_GLOBAL, Integer.parseInt(idrestype));
                                }
                                else {
                                    recrestype = DBResourceType.getInstance().getResourceType(site, Integer.parseInt(idrestype));
                                }

                                if (recrestype != null) {
                                    String idName = recrestype.getName();
                                    idName = idName.trim();
                                    idName = idName.replaceAll(" ", "");

                                    ResourceType rtype = ws.getResourceType(idName);

                                    if (rtype != null) {
                                        String buscar = "<resourceType id=\""+idrestype+"\" name=\""+namerestype+"\" topicmap=\""+tmrestype+"\"/>";
                                        String reemplazar = "<resourceType id=\""+rtype.getId()+"\" name=\""+rtype.getTitle()+"\" topicmap=\""+ws.getId()+"\"/>";
                                        xmltmp = xmltmp.replaceAll(buscar, reemplazar);
                                    }
                                }
                                //////////////////////////////////////////

                            }
                            pf.setXml(xmltmp);
                            

                        }
                        catch(Exception e)
                        {
                            System.out.println("Error al revisar el PFlow");
                        }
                    }
                }


                
                
                //obteniendo el numero de asociaciones importadas
                int nwsasoc = 0;
                nwsasoc = getNumWSAsoc(ws);

    /////////////////////////////////////////////////////////////
        %>
        <div>
            <%
                HashMap hmcat = new HashMap();
                int nwp = 0;
                int nptype = 0;

                //obteniendo num. de elementos existentes migrados
                hmcat = getNumCatElements(ws);
            %>

            <fieldset  style="background-color:#A9F5D0;"><legend>&nbsp;&nbsp;Lista de Catálogos - Paso 1 (Completado)&nbsp;&nbsp;</legend>
                <ul>
                    <li><input type="checkbox" name="catalog" id="cat1" value="language" disabled><label for="cat1">Lenguajes <%=hmcat.get("language") != null ? "(" + hmcat.get("language") + ")" : ""%></label></li>
                    <li><input type="checkbox" name="catalog" id="cat2" value="device" disabled><label for="cat2">Dispositivos <%=hmcat.get("device") != null ? "(" + hmcat.get("device") + ")" : ""%></label></li>
                    <li><input type="checkbox" name="catalog" id="cat9" value="dns" disabled><label for="cat9">DNS <%=hmcat.get("dns") != null ? "(" + hmcat.get("dns") + ")" : ""%></label></li>
                    <li><input type="checkbox" name="catalog" id="cat11" value="rules" disabled><label for="cat11">Reglas <%=hmcat.get("rules") != null ? "(" + hmcat.get("rules") + ")" : ""%></label></li>
                    <li><input type="checkbox" name="catalog" id="cat4" value="pflow" disabled><label for="cat4">Flujos de publicación <%=hmcat.get("pflow") != null ? "(" + hmcat.get("pflow") + ")" : ""%></label></li>
                    <li><input type="checkbox" name="catalog" id="cat3" value="resourcetype" disabled><label for="cat3">Tipos de recursos <%=hmcat.get("resourcetype") != null ? "(" + hmcat.get("resourcetype") + ")" : ""%></label></li>
                    <li><input type="checkbox" name="catalog" id="cat6" value="grouptemplate" disabled><label for="cat6">Grupos de plantillas <%=hmcat.get("grouptemplate") != null ? "(" + hmcat.get("grouptemplate") + ")" : ""%></label></li>
                    <!-- li><input type="checkbox" name="catalog" id="cat8" value="camp"><label for="cat8">Campañas</label></li -->
                </ul>
            </fieldset>
            <fieldset  style="background-color:#A9F5D0;"><legend>&nbsp;&nbsp;Lista de Catálogos - Paso 2 (Completado)&nbsp;&nbsp;</legend>
                <ul>
                    <li><input type="checkbox" name="catalog" id="cat8" value="resourcesubtype" disabled><label for="cat8">Sub-Tipos de recursos <%=hmcat.get("resourcesubtype") != null ? "(" + hmcat.get("resourcesubtype") + ")" : ""%></label></li>
                    <li><input type="checkbox" name="catalog" id="cat5" value="template" disabled><label for="cat5">Plantillas <%=hmcat.get("template") != null ? "(" + hmcat.get("template") + ")" : ""%></label></li>
                </ul>
            </fieldset>
            <fieldset  style="background-color:#A9F5D0;"><legend>&nbsp;&nbsp;Lista de Catálogos - Paso 3 (Completado)&nbsp;&nbsp;</legend>
                <ul>
                    <li><input type="checkbox" name="catalog" id="cat7" value="resource" disabled><label for="cat7">Recursos <%=hmcat.get("resource") != null ? "(" + hmcat.get("resource") + ")" : ""%></label></li>
                    <li><input type="checkbox" name="wpassoc" id="cat10" value="assoc" disabled><label for="cat10">Revisar Asociaciones de Recursos con Secciones (se debe hacer sólo una vez, si y solo si ya se acabaron de migrar todos los recursos)</label></li>
                </ul>
            </fieldset>
            <fieldset  style="background-color:#A9F5D0;"><legend>&nbsp;&nbsp;Revision y generación de Asociaciones entre secciones&nbsp;&nbsp;</legend>
                <ul>
                    <li><input type="checkbox" name="resdata" id="cat13" value="resdata" disabled><label for="cat13">Datos de recursos</label></li>
                    <li><input type="checkbox" name="assocsbtwp" id="cat12" value="assocsbtwp" disabled><label for="cat12">Revisar Asociaciones entre Secciones (se debe hacer sólo una vez) (<%=nwsasoc%>)</label></li>
                </ul>
            </fieldset>
            <fieldset style="background-color:#F7BE81;">
                <form id="inicio" name="finicio" method="post" action="">
                    <button name="b_send" id="b_send" type="submit" >Terminar</button>
                </form>
            </fieldset>

        </div>
        <%
            }
        %>
    </body>
</html>

<%!

    // revisa estructura de los tópicos utilizados para las asociaciones
    void parentReview(com.infotec.topicmaps.Topic tpTipo,WebPage wpage)
    {
        System.out.println("Parent Review..."+tpTipo.getId()+", "+wpage.getId());
        WebPage wp = null;
        if(tpTipo != null && wpage !=null)
        {
           if(tpTipo.getType()!=null)
           {
               wp = wpage.getWebSite().getWebPage(tpTipo.getType().getId());
               if(null==wp)
               {
                  wp = copyValues(tpTipo.getType(), wpage.getWebSite());
               }
               if(null!=wp)
               {
                  if(wpage.getParent()==null) wpage.setParent(wp);
                  parentReview(tpTipo.getType(), wp);
               }
           }
        }
    }

    void setWebPageAssociation(com.infotec.topicmaps.Topic tp, WebSite ws, String occType, String UserRepId)
    {
        WebPage wp = ws.getWebPage(tp.getId()); // obtiene el WebPage home
        if(wp!=null)
        {
            Iterator itocc = tp.getOccurrencesOfType(occType);
            while(itocc.hasNext())
            {

                Occurrence occ = (Occurrence) itocc.next();
                String idElement = occ.getResourceData();

                //System.out.println("idElement: "+idElement);

                if(occType.equals(TopicMap.CNF_WBTemplate))
                {
                    org.semanticwb.model.Template template = ws.getTemplate(idElement);
                    if(template!=null)
                    {
                        TemplateRef temRef = ws.createTemplateRef();
                        temRef.setActive(occ.isActive());
                        temRef.setTemplate(template);
                        wp.addTemplateRef(temRef);
                    }
                } else if(occType.equals(TopicMap.CNF_WBRole))
                {
                    String usrreptemp = UserRepId;
                    if(UserRepId.startsWith("swb4|")) usrreptemp = UserRepId.substring(UserRepId.indexOf("swb4|")+5);
                    System.out.println("Asoc Roles con WebPages.");
                    UserRepository urep = UserRepository.ClassMgr.getUserRepository(usrreptemp);
                    System.out.println("User Rep:"+(urep!=null?"true":"false"));
                    org.semanticwb.model.Role role = urep.getRole(idElement);
                    if(role!=null)
                    {
                        //System.out.println("Rol != null ("+role.getId()+")");
                        RoleRef rolRef = ws.createRoleRef();
                        //System.out.println("RolRef != null ("+rolRef.getId()+")");
                        rolRef.setActive(occ.isActive());
                        rolRef.setRole(role);
                        wp.addRoleRef(rolRef);
                    }
                } else if(occType.equals(TopicMap.CNF_WBRule))
                {
                    org.semanticwb.model.Rule rule = ws.getRule(idElement);
                    if(rule!=null)
                    {
                        RuleRef rulRef = ws.createRuleRef();
                        rulRef.setActive(occ.isActive());
                        rulRef.setRule(rule);
                        wp.addRuleRef(rulRef);
                    }
                } else if(occType.equals(TopicMap.REC_WBContent))
                {
                    // no debe repetir los contenidos de office
                    String siteid=tp.getMap().getId();
                    WBResource wbresource=ResourceMgr.getInstance().getResource(siteid, Long.parseLong(idElement));
                    if(null!=wbresource&&!MigrateOfficeContents.isOfficeDocument(wbresource,siteid))
                    {
                        org.semanticwb.model.Resource resource = ws.getResource(idElement);

                        if(resource!=null)
                        {
                            wp.addResource(resource);
                            try {
                                resource.setActive(occ.isActive());
                            } catch (Exception e) {
                                System.out.println("Error al activar el recurso..."+resource.getId());
                                                       }
                        }
                    }
                } else if(occType.equals(TopicMap.CNF_WBPFlow))
                {
                   org.semanticwb.model.PFlow pflow = ws.getPFlow(idElement);
                   if(pflow!=null)
                   {
                       PFlowRef pfRef = ws.createPFlowRef();
                       pfRef.setActive(occ.isActive());
                       pfRef.setPflow(pflow);
                       wp.addPFlowRef(pfRef);
                   }
                }

            }
        }
    }

    //MIGRACION DE LA Referencias DE TOPICOS A a los WEBPAGES
    boolean reviewWebPageAsoc(com.infotec.topicmaps.Topic tp, WebSite ws, String occType, String UserRepId)
    {
        if(tp!=null)
        {
            Iterator it = tp.getSortChild(false);
            while(it.hasNext())
            {
                com.infotec.topicmaps.Topic tpc = (com.infotec.topicmaps.Topic) it.next();
                if(tpc.isChildof(tp))
                {
                    setWebPageAssociation(tpc, ws, occType, UserRepId);
                    reviewWebPageAsoc(tpc, ws, occType, UserRepId);
                }
            }
        }
        return true;
    }


    //MIGRACION DE LA ESTRUCTURA DE TOPICOS A ESTRUCTURA DE WEBPAGES
    boolean tp2wp(com.infotec.topicmaps.Topic tp, WebPage wpp, WebSite ws)
    {
        if(tp!=null)
        {
            Iterator it = tp.getSortChild(false);
            while(it.hasNext())
            {
                com.infotec.topicmaps.Topic tpc = (com.infotec.topicmaps.Topic) it.next();
                if(tpc.isChildof(tp))
                {
                    WebPage wpage = copyValues(tpc, ws);
                    if(wpage!=null)
                    {
                        wpage.setParent(wpp);
                        tp2wp(tpc,wpage,ws);
                    }
                }
            }
        }
        return true;
    }

    //AGREGANDO PADRES VIRTUALES A SECCIONES
    int virtualTopic2VirtualWebPage( com.infotec.topicmaps.Topic tp, WebPage wpp, WebSite ws)
    {
        int iret=0;
        if(tp!=null)
        {
            Iterator it = tp.getChildAll().iterator();
            while(it.hasNext())
            {
                com.infotec.topicmaps.Topic tpc = (com.infotec.topicmaps.Topic) it.next();
                Iterator ittypes = tpc.getTypes();
                WebPage wpage = ws.getWebPage(tpc.getId());
                while(ittypes.hasNext())
                {
                    com.infotec.topicmaps.Topic tptype = (com.infotec.topicmaps.Topic)ittypes.next();
                    if(tptype!=null&&tptype!=tpc.getType())
                    {
                        WebPage wpvp = ws.getWebPage(tptype.getId());
                        if(wpvp!=null)
                        {
                            wpage.addVirtualParent(wpvp);
                            iret++;
                        }
                    }
                }
            }
        }
        return iret;
    }

    // COPIANDO SECCIONES CON SU INFORMACIÓN EN DIFERENTES IDIOMAS
    WebPage copyValues( com.infotec.topicmaps.Topic topic, WebSite ws)
    {
        WebPage wp = null;
        if(topic!=null&&!topic.isDeleted())
        {
            String id=topic.getId();
            wp = ws.getWebPage(id);
            if(wp==null)
            {
            String estitle=topic.getDisplayLangName("es");
            String entitle=topic.getDisplayLangName("en");
            String pttitle=topic.getDisplayLangName("pt");
            String zhtitle=topic.getDisplayLangName("zh");
            String detitle=topic.getDisplayLangName("de");
            String frtitle=topic.getDisplayLangName("fr");
            String ittitle=topic.getDisplayLangName("it");
            String jatitle=topic.getDisplayLangName("ja");

            String esdesc=topic.getDescription(topic.getMap().getTopicLang("es"));
            String endesc=topic.getDescription(topic.getMap().getTopicLang("en"));
            String ptdesc=topic.getDescription(topic.getMap().getTopicLang("pt"));
            String zhdesc=topic.getDescription(topic.getMap().getTopicLang("zh"));
            String dedesc=topic.getDescription(topic.getMap().getTopicLang("de"));
            String frdesc=topic.getDescription(topic.getMap().getTopicLang("fr"));
            String itdesc=topic.getDescription(topic.getMap().getTopicLang("it"));
            String jadesc=topic.getDescription(topic.getMap().getTopicLang("ja"));



            wp = ws.createWebPage(id);
            wp.setTitle(topic.getDisplayName());
            wp.setDescription(topic.getDescription());

            if(isText(estitle))wp.setTitle(estitle,"es");
            if(isText(entitle))wp.setTitle(entitle,"en");
            if(isText(pttitle))wp.setTitle(pttitle,"pt");
            if(isText(zhtitle))wp.setTitle(zhtitle,"zh");
            if(isText(detitle))wp.setTitle(detitle,"de");
            if(isText(frtitle))wp.setTitle(frtitle,"fr");
            if(isText(ittitle))wp.setTitle(ittitle,"it");
            if(isText(jatitle))wp.setTitle(jatitle,"ja");

            if(isText(esdesc))wp.setDescription(esdesc,"es");
            if(isText(endesc))wp.setDescription(endesc,"en");
            if(isText(ptdesc))wp.setDescription(ptdesc,"pt");
            if(isText(zhdesc))wp.setDescription(zhdesc,"zh");
            if(isText(dedesc))wp.setDescription(dedesc,"de");
            if(isText(frdesc))wp.setDescription(frdesc,"fr");
            if(isText(itdesc))wp.setDescription(itdesc,"it");
            if(isText(jadesc))wp.setDescription(jadesc,"ja");


            wp.setActive(topic.getDbdata().getActive()==1);
            wp.setDeleted(topic.getDbdata().getDeleted()==1);
            //System.out.println("Topic is active :"+topic.isActive());
            wp.setHidden(topic.getDbdata().getHidden()==1);
            java.util.Date dt=topic.getDbdata().getCreated();
            if(dt!=null)wp.setCreated(dt);
            wp.setUpdated(new java.util.Date());
            if(topic.getSortName()!=null) wp.setSortName(topic.getSortName());

            //Revisando si tiene una ruta virtual o una ruta amigable asociada
            if(topic.getSubjectIdentity()!=null)
            {
                String redirectUrl =  topic.getSubjectIdentity().getResourceRef();
                if(null!=redirectUrl)
                {
                    if(redirectUrl.startsWith("furl:"))
                    {
                        redirectUrl = redirectUrl.substring(redirectUrl.indexOf("furl:")+5);
                        wp.setWebPageURL(redirectUrl);
                        wp.setWebPageURLType(2); // ruta amigable
                    }
                    else
                    {
                        wp.setWebPageURL(redirectUrl);
                        wp.setWebPageURLType(1); // ruta redireccion
                    }
                }
            }


            //Revisando Intervalo Topic - WebPage...

            Iterator itocc = topic.getOccurrencesOfType(TopicMap.CNF_WBCalendar);
            while(itocc.hasNext())
            {
                Occurrence occ = (Occurrence) itocc.next();
                String xmlconfig = occ.getResourceData();

                //System.out.println("XML intervalo: "+xmlconfig);
                if(null!=xmlconfig&&xmlconfig.trim().length()>0)
                {
                    String intervalo = xmlconfig.substring(xmlconfig.indexOf("<interval>"),xmlconfig.lastIndexOf("</interval>")+11);
                    //System.out.println("Intervalo (Topic): "+intervalo);

                    Document dom = SWBUtils.XML.xmlToDom(intervalo);
                    String titulo=dom.getElementsByTagName("title").item(0).getFirstChild().getNodeValue();
                    //System.out.println("Titulo (Topic): "+titulo);

                    org.semanticwb.model.Calendar schedule = ws.createCalendar();
                    schedule.setActive(Boolean.TRUE);
                    schedule.setTitle(titulo);
                    schedule.getSemanticObject().setProperty(org.semanticwb.model.Calendar.swb_xml, intervalo);

                    CalendarRef calRef = CalendarRef.ClassMgr.createCalendarRef(ws);
                    calRef.setCalendar(schedule);
                    calRef.setActive(Boolean.TRUE);
                    wp.addCalendarRef(calRef);
                }
             }

            }
        }
        return wp;
    }

    //REVISANDO SI YA SE MIGRÓ ALGUN CATALOGO
    HashMap  getNumCatElements(WebSite ws)
    {
        HashMap hm = new HashMap();
        //language
        int n = 0;
        Iterator<Language> it = ws.listLanguages();
        while(it.hasNext())
        {
            Language lang = it.next();
            n++;
        }
        if(n>0) hm.put("language",Integer.toString(n));
        //device
        n = 0;
        Iterator<Device> itd = ws.listDevices();
        while(itd.hasNext())
        {
            Device dev = itd.next();
            n++;
        }
        if(n>0) hm.put("device",Integer.toString(n));
        //rules
        n = 0;
        Iterator<Rule> itr = ws.listRules();
        while(itr.hasNext())
        {
            Rule r = itr.next();
            n++;
        }
        if(n>0) hm.put("rules",Integer.toString(n));
        //dns
        n = 0;
        Iterator<Dns> itdn = ws.listDnses();
        while(itdn.hasNext())
        {
            Dns dns = itdn.next();
            n++;
        }
        if(n>0) hm.put("dns",Integer.toString(n));
        //camp
        /*
        n = 0;
        Iterator<Device> itd = ws.listDevices();
        while(itd.hasNext())
        {
            Device dev = itd.next();
            n++;
        }
        if(n>0) hm.put("device",Integer.toString(n));
        */
        //resourcetype
        n = 0;
        Iterator<ResourceType> itrt = ws.listResourceTypes();
        while(itrt.hasNext())
        {
            ResourceType rest = itrt.next();
            //System.out.println("ResourceType: "+rest.getId());
            if(!rest.getId().equals("ExcelContent")&&!rest.getId().equals("PPTContent")&&!rest.getId().equals("WordContent"))
                n++;
        }
        if(n>0) hm.put("resourcetype",Integer.toString(n));
        //resourcesubtype
        n = 0;
        Iterator<ResourceSubType> itrst = ws.listResourceSubTypes();
        while(itrst.hasNext())
        {
            ResourceSubType resst = itrst.next();
            n++;
        }
        if(n>0) hm.put("resourcesubtype",Integer.toString(n));
        //pflow
        n = 0;
        Iterator<org.semanticwb.model.PFlow> itpf = ws.listPFlows();
        while(itpf.hasNext())
        {
            org.semanticwb.model.PFlow pf = itpf.next();
            n++;
        }
        if(n>0) hm.put("pflow",Integer.toString(n));
        //template
        n = 0;
        Iterator<org.semanticwb.model.Template> itt = ws.listTemplates();
        while(itt.hasNext())
        {
            org.semanticwb.model.Template tpl = itt.next();
            n++;
        }
        if(n>0) hm.put("template",Integer.toString(n));
        //grouptemplate
        n = 0;
        Iterator<TemplateGroup> ittplg = ws.listTemplateGroups();
        while(ittplg.hasNext())
        {
            TemplateGroup tplg = ittplg.next();
            n++;
        }
        if(n>0) hm.put("grouptemplate",Integer.toString(n));
        //resource
        n = 0;
        Iterator<org.semanticwb.model.Resource> itres = ws.listResources();
        while(itres.hasNext())
        {
            org.semanticwb.model.Resource res = itres.next();
            n++;
        }
        if(n>0) hm.put("resource",Integer.toString(n));

        return hm;
    }

    //REVISANDO SI EL TEXTO ES VÁLIDO
    boolean isText(String txt)
    {
        if(txt==null || txt.length()==0)
        {
            return false;
        }
        return true;
    }

    //CONTANDO EL NUMERO DE PAGINAS EXISTENTES
    int getNumWP(WebSite ws)
    {
        int iret = 0;
        Iterator it= ws.listWebPages();
        while(it.hasNext())
        {
            Object obj = it.next();
            iret++;
        }
        return iret;
    }

    //CONTANDO EL NUMERO DE ASOCIACIONES EXISTENTES
    int getNumWSAsoc(WebSite ws)
    {
        int iret = 0;
        Iterator it= ws.listAssociations();
        while(it.hasNext())
        {
            Object obj = it.next();
            iret++;
        }
        return iret;
    }

    //CREACION DE REPOSITORIO DE USUARIOS
    void createUserRep(String usrRepId)
    {

        //System.out.println("rep id:"+usrRepId);
        //Crea repositorio de usuarios
        UserRepository newUsrRep = null;

        if (usrRepId!=null) { //Utilizara un repositorio exclusivo
            newUsrRep = SWBContext.getUserRepository(usrRepId+"_usr");
            if (newUsrRep==null) {
                newUsrRep = SWBContext.createUserRepository(usrRepId + "_usr", "http://user." + usrRepId + ".swb#");
                newUsrRep.getSemanticObject().getModel().setTraceable(false);
                newUsrRep.setTitle("Repositorio de usuarios(" + usrRepId + ")");
                newUsrRep.setTitle("Repositorio de usuarios(" + usrRepId + ")", "es");
                newUsrRep.setTitle("Users Repository(" + usrRepId + ")", "en");
                newUsrRep.setUndeleteable(true);

                //MAPS74 - Cambiado a semantic prop
                newUsrRep.setAuthMethod("FORM");
                newUsrRep.setLoginContext("swb4TripleStoreModule");
                newUsrRep.setCallBackHandlerClassName("org.semanticwb.security.auth.SWB4CallbackHandlerLoginPasswordImp");
            }
            if(newUsrRep!=null)newUsrRep.getSemanticObject().getModel().setTraceable(true);
        }
    }

    //ASIGNACIÓN DE REPOSITORIO DE USUARIOS AL NUEVO SITIO
    void assignUserRep2WebSite(WebSite site, String usrRepId)
    {
        String usrreptemp = usrRepId;

        if(usrRepId.startsWith("swb4|")) usrreptemp = usrRepId.substring(usrRepId.indexOf("swb4|")+5);
        else if(usrRepId.indexOf("_usr")==-1) usrreptemp = usrreptemp + "_usr";

        site.getSemanticObject().getModel().setTraceable(false);
        //Revisa repositorio de usuarios para el sitio

        UserRepository exitUsrRep = SWBContext.getUserRepository(usrreptemp);
        site.setUserRepository(exitUsrRep);

        // para que sea exclusivo
        //site.addSubModel(exitUsrRep);
    }

    //AGREGANDO ELEMENTOS ROLES Y USUARIOS AL REPOSITORIO DE USUARIOS
    int addElements2UserRep(String usrRepId, String type)
    {
        String usrreptemp = usrRepId;
        if(usrRepId.startsWith("swb4|"))
        {
            usrreptemp = usrRepId.substring(usrRepId.indexOf("swb4|")+5);
        }
        else if(usrRepId.indexOf("_usr")==-1) usrreptemp = usrreptemp + "_usr";
        int numelem=-1;
        if(usrRepId!=null&&usrRepId.trim().length()>0&&type!=null&&type.trim().length()>0)
        {
            numelem=0;
            UserRepository urep = SWBContext.getUserRepository(usrreptemp);

            if("roles".equals(type))
            {
                Iterator<org.semanticwb.model.Role> itrole = urep.listRoles();

                if(!itrole.hasNext()) // validando que el repositoio este vacio para copiar roles
                {
                    Iterator itroles = RoleMgr.getInstance().getRoles(usrRepId).values().iterator();
                    while(itroles.hasNext())
                    {
                        com.infotec.wb.core.Role wbr = (com.infotec.wb.core.Role) itroles.next();
                        org.semanticwb.model.Role swbr = org.semanticwb.model.Role.ClassMgr.createRole(Integer.toString(wbr.getId()), urep);
                        swbr.setTitle(wbr.getName());
                        swbr.setDescription(wbr.getDescription());
                        numelem++;
                    }
                }
            } else if("users".equals(type))
            {
                Enumeration enusrs = DBUser.getInstance(usrRepId).getUsers();
                while(enusrs.hasMoreElements())
                {
                    RecUser rusr = (RecUser) enusrs.nextElement();
                    if(null!=rusr&&null!=urep)
                    {
                        // copiado de datos de un usuario a otro
                        org.semanticwb.model.User usr = org.semanticwb.model.User.ClassMgr.createUser(Long.toString(rusr.getId()), urep);
                        usr.setLogin(rusr.getLogin());
                        usr.setPassword(rusr.getPassword());
                        usr.setFirstName(rusr.getFirstName());
                        usr.setLastName(rusr.getLastName());
                        usr.setSecondLastName(rusr.getMiddleName());
                        usr.setLanguage(rusr.getLanguage());
                        usr.setEmail(rusr.getEmail());
                        usr.setActive(rusr.getActive()==1);
                        if (rusr.getConfirmValue()!=null&&rusr.getConfirmValue().indexOf("|")>-1){
                            int ques = 0;
                            try { ques = Integer.valueOf(rusr.getConfirmValue().substring(0,rusr.getConfirmValue().indexOf("|")));}
                            catch (Exception ne){}
                            usr.setSecurityQuestion(ques);
                            usr.setSecurityAnswer(rusr.getConfirmValue().substring(rusr.getConfirmValue().indexOf("|")+1));
                        } else if (null!=rusr.getConfirmValue()) {
                            usr.setSecurityAnswer(rusr.getConfirmValue());
                        }
                        usr.setPasswordChanged(rusr.getPasswordChanged());
                        if (null!=rusr.getXml()){
                            com.infotec.wb.core.WBUser user = new com.infotec.wb.core.WBUser(rusr);
                            Iterator attlist = DBUser.getInstance(user.getRepository()).getUserAttrsList().keySet().iterator();
                            while (attlist.hasNext()){
                                String attact = (String)attlist.next();
                                String curratt = user.getAttribute(attact);
                                if (null != curratt){
                                    org.semanticwb.platform.SemanticProperty property=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#userExt_"+attact);
                                    try {usr.getSemanticObject().setProperty(property, curratt);} catch (Exception ne) {}
                                }
                            }
                        }

                        numelem++;
                        Iterator itwbroles = rusr.getRoles();
                        while(itwbroles.hasNext())
                        {
                            Integer wbroleid = (Integer) itwbroles.next();
                            org.semanticwb.model.Role srole = urep.getRole(wbroleid.toString());
                            if(null!=srole)
                            {
                                usr.addRole(srole);
                            }
                        }
                    }

                }
            }

        }
        return numelem;
    }


    void createRepositoryFileWebSite(WebSite wsite)
    {
        String title = wsite.getTitle();
        String id = wsite.getId();
        wsite.getSemanticObject().getModel().setTraceable(false);

        //creación de repositorio de documentos
        Workspace workspace = SWBContext.createWorkspace(id + "_rep", "http://repository." + id + ".swb#");
        workspace.getSemanticObject().getModel().setTraceable(false);
        workspace.setTitle("Repositorio de documentos(" + title + ")", "es");
        workspace.setTitle("Documents Repository(" + title + ")", "en");

        wsite.addSubModel(workspace);
        workspace.getSemanticObject().getModel().setTraceable(true);

        //Crea grupo de templates de defecto
        //TemplateGroup grp = wsite.createTemplateGroup();
        //grp.setTitle("Default");
    }

    //COPIADO DE ARCHIVOS
    boolean migrateFiles(String sourceDirectory,String targetDirectory)
    {
        File sourcePath=new File(WBUtils.getInstance().getWebWorkPath()+sourceDirectory);
        if(sourcePath!=null && sourcePath.exists() && sourcePath.isDirectory()) //revisar si existe la fuente, si no hace nada y regresa false
        {
            //File targetPath=new File(WBUtils.getInstance().getWorkPath()+targetDirectory);
            File targetPath=new File(targetDirectory);
            if(!targetPath.exists()) // si no existe el target lo crea
            {
                targetPath.mkdirs();
            }
            if(targetPath!=null && targetPath.exists()) //si existe el target
            {
                //return AFUtils.getInstance().copyStructure(WBUtils.getInstance().getWebWorkPath()+sourceDirectory, targetDirectory, true, sourceWebWorkPath+sourceDirectory, WBUtils.getInstance().getWebWorkPath()+targetDirectory);
            }
        }
        return false;
    }


    //AGREGANDO ELEMENTOS POR DEFECTO AL NUEVO SITIO
    void createWebSitesDefaultElements(WebSite site, String ns)
    {
        try {
                //Crea recursos de defecto
                if (site.getResourceType("ExcelContent") == null) {
                    ResourceType ptype = site.createResourceType("ExcelContent");
                    ptype.setResourceClassName("org.semanticwb.resource.office.sem.ExcelResource");
                    ptype.setResourceBundle("org.semanticwb.resource.office.sem.ExcelResource");
                    ptype.setResourceMode(1);
                    ptype.setTitle("ExcelContent");
                }

                if (site.getResourceType("WordContent") == null) {
                    ResourceType ptype = site.createResourceType("WordContent");
                    ptype.setResourceClassName("org.semanticwb.resource.office.sem.WordResource");
                    ptype.setResourceBundle("org.semanticwb.resource.office.sem.WordResource");
                    ptype.setResourceMode(1);
                    ptype.setTitle("WordContent");
                }

                if (site.getResourceType("PPTContent") == null) {
                    ResourceType ptype = site.createResourceType("PPTContent");
                    ptype.setResourceClassName("org.semanticwb.resource.office.sem.PPTResource");
                    ptype.setResourceBundle("org.semanticwb.resource.office.sem.PPTResource");
                    ptype.setResourceMode(1);
                    ptype.setTitle("PPTContent");
                }

                if (site.getResourceType("Banner") == null) {
                    ResourceType ptype = site.createResourceType("Banner");
                    ptype.setResourceClassName("org.semanticwb.portal.resources.Banner");
                    ptype.setResourceBundle("org.semanticwb.portal.resources.Banner");
                    ptype.setResourceMode(2);
                    ptype.setTitle("Banner");
                }

                if (site.getResourceType("HTMLContent") == null) {
                    ResourceType ptype = site.createResourceType("HTMLContent");
                    ptype.setResourceClassName("org.semanticwb.portal.resources.sem.HTMLContent");
                    ptype.setResourceBundle("org.semanticwb.portal.resources.sem.HTMLContent");
                    ptype.setResourceMode(1);
                    //ptype.setResourceCache(600);
                    ptype.setTitle("HTMLContent");
                }

                if (site.getResourceType("Promo") == null) {
                    ResourceType ptype = site.createResourceType("Promo");
                    ptype.setResourceClassName("org.semanticwb.portal.resources.Promo");
                    ptype.setResourceBundle("org.semanticwb.portal.resources.Promo");
                    ptype.setResourceMode(2);
                    ptype.setTitle("Promo");
                }

                if (site.getResourceType("AdvancedSearch") == null) {
                    ResourceType ptype = site.createResourceType("AdvancedSearch");
                    ptype.setResourceClassName("org.semanticwb.portal.resources.AdvancedSearch");
                    ptype.setResourceBundle("org.semanticwb.portal.resources.AdvancedSearch");
                    ptype.setResourceMode(ResourceType.MODE_SYSTEM);
                    ptype.setTitle("AdvancedSearch");
                }

                if (site.getResourceType("Print") == null) {
                    ResourceType ptype = site.createResourceType("Print");
                    ptype.setResourceClassName("org.semanticwb.portal.resources.Print");
                    ptype.setResourceBundle("org.semanticwb.portal.resources.Print");
                    ptype.setResourceMode(2);
                    ptype.setTitle("Print");
                }

                if (site.getResourceType("Window") == null) {
                    ResourceType ptype = site.createResourceType("Window");
                    ptype.setResourceClassName("org.semanticwb.portal.resources.Window");
                    ptype.setResourceBundle("org.semanticwb.portal.resources.Window");
                    ptype.setResourceMode(2);
                    ptype.setTitle("Window");
                }

                if (site.getResourceType("StaticText") == null) {
                    ResourceType ptype = site.createResourceType("StaticText");
                    ptype.setResourceClassName("org.semanticwb.portal.resources.StaticText");
                    ptype.setResourceBundle("org.semanticwb.portal.resources.StaticText");
                    ptype.setResourceMode(2);
                    ptype.setTitle("StaticText");
                }

                if (site.getResourceType("SiteMap") == null) {
                    ResourceType ptype = site.createResourceType("SiteMap");
                    ptype.setResourceClassName("org.semanticwb.portal.resources.WBSiteMap");
                    ptype.setResourceBundle("org.semanticwb.portal.resources.WBSiteMap");
                    ptype.setResourceMode(3);
                    ptype.setTitle("SiteMap");
                }

                if (site.getResourceType("Recommend") == null) {

                    ResourceType ptype = site.createResourceType("Recommend");
                    ptype.setResourceClassName("org.semanticwb.portal.resources.Recommend");
                    ptype.setResourceBundle("org.semanticwb.portal.resources.Recommend");
                    ptype.setResourceMode(2);
                    ptype.setTitle("Recommend");
                }

                if (site.getResourceType("QueryResource") == null) {

                    ResourceType ptype = site.createResourceType("QueryResource");
                    ptype.setResourceClassName("org.semanticwb.portal.resources.QueryResource");
                    ptype.setResourceBundle("org.semanticwb.portal.resources.QueryResource");
                    ptype.setResourceMode(3);
                    ptype.setTitle("QueryResource");
                }

                if (site.getResourceType("VirtualResource") == null) {

                    ResourceType ptype = site.createResourceType("VirtualResource");
                    ptype.setResourceClassName("org.semanticwb.portal.resources.VirtualResource");
                    ptype.setResourceBundle("org.semanticwb.portal.resources.VirtualResource");
                    ptype.setResourceMode(1);
                    ptype.setTitle("VirtualResource");
                }

                if (site.getResourceType("RecommendSwf") == null) {

                    ResourceType ptype = site.createResourceType("RecommendSwf");
                    ptype.setResourceClassName("org.semanticwb.portal.resources.RecommendSwf");
                    ptype.setResourceBundle("org.semanticwb.portal.resources.RecommendSwf");
                    ptype.setResourceMode(2);
                    ptype.setTitle("RecommendSwf");
                }

                if (site.getResourceType("Poll") == null) {

                    ResourceType ptype = site.createResourceType("Poll");
                    ptype.setResourceClassName("org.semanticwb.portal.resources.Poll");
                    ptype.setResourceBundle("org.semanticwb.portal.resources.Poll");
                    ptype.setResourceMode(2);
                    ptype.setTitle("Poll");
                }

                if (site.getResourceType("Menu") == null) {
                    ResourceType ptype = site.createResourceType("Menu");
                    ptype.setResourceClassName("org.semanticwb.portal.resources.WBMenu");
                    ptype.setResourceBundle("org.semanticwb.portal.resources.WBMenu");
                    ptype.setResourceMode(ptype.MODE_STRATEGY);
                    ptype.setTitle("Menu");
                }

                if (site.getResourceType("MenuMap") == null) {

                    ResourceType ptype = site.createResourceType("MenuMap");
                    ptype.setResourceClassName("org.semanticwb.portal.resources.WBMenuMap");
                    ptype.setResourceBundle("org.semanticwb.portal.resources.WBMenuMap");
                    ptype.setResourceMode(2);
                    ptype.setTitle("MenuMap");
                }

                if (site.getResourceType("MenuNivel") == null) {
                    ResourceType ptype = site.createResourceType("MenuNivel");
                    ptype.setResourceClassName("org.semanticwb.portal.resources.WBMenuNivel");
                    ptype.setResourceBundle("org.semanticwb.portal.resources.WBMenuNivel");
                    ptype.setResourceMode(2);
                    ptype.setTitle("MenuNivel");
                }

                if (site.getResourceType("JSPResource") == null) {

                    ResourceType ptype = site.createResourceType("JSPResource");
                    ptype.setResourceClassName("org.semanticwb.portal.resources.JSPResource");
                    ptype.setResourceBundle("org.semanticwb.portal.resources.JSPResource");
                    ptype.setResourceMode(ResourceType.MODE_SYSTEM);
                    ptype.setTitle("JSPResource");
                }

                if (site.getResourceType("PHPResource") == null) {

                    ResourceType ptype = site.createResourceType("PHPResource");
                    ptype.setResourceClassName("org.semanticwb.portal.resources.PHPResource");
                    ptype.setResourceBundle("org.semanticwb.portal.resources.PHPResource");
                    ptype.setResourceMode(ResourceType.MODE_SYSTEM);
                    ptype.setTitle("PHPResource");
                }

                if (site.getResourceType("PythonResource") == null) {

                    ResourceType ptype = site.createResourceType("PythonResource");
                    ptype.setResourceClassName("org.semanticwb.portal.resources.PythonResource");
                    ptype.setResourceBundle("org.semanticwb.portal.resources.PythonResource");
                    ptype.setResourceMode(ResourceType.MODE_SYSTEM);
                    ptype.setTitle("PythonResource");
                }

                if (site.getResourceType("GroovyResource") == null) {

                    ResourceType ptype = site.createResourceType("GroovyResource");
                    ptype.setResourceClassName("org.semanticwb.portal.resources.GroovyResource");
                    ptype.setResourceBundle("org.semanticwb.portal.resources.GroovyResource");
                    ptype.setResourceMode(ResourceType.MODE_SYSTEM);
                    ptype.setTitle("GroovyResource");
                }

                if (site.getResourceType("GroovyEditor") == null)
                {
                    ResourceType ptype = site.createResourceType("GroovyEditor");
                    ptype.setResourceClassName("org.semanticwb.portal.resources.GroovyEditor");
                    ptype.setResourceBundle("org.semanticwb.portal.resources.GroovyEditor");
                    ptype.setResourceMode(ResourceType.MODE_SYSTEM);
                    ptype.setTitle("GroovyEditor");
                }

                if (site.getResourceType("JSPEditor") == null)
                {
                    ResourceType ptype = site.createResourceType("JSPEditor");
                    ptype.setResourceClassName("org.semanticwb.portal.resources.JSPEditor");
                    ptype.setResourceBundle("org.semanticwb.portal.resources.JSPEditor");
                    ptype.setResourceMode(ResourceType.MODE_SYSTEM);
                    ptype.setTitle("JSPEditor");
                }

                if (site.getResourceType("PythonEditor") == null)
                {
                    ResourceType ptype = site.createResourceType("PythonEditor");
                    ptype.setResourceClassName("org.semanticwb.portal.resources.PythonEditor");
                    ptype.setResourceBundle("org.semanticwb.portal.resources.PythonEditor");
                    ptype.setResourceMode(ResourceType.MODE_SYSTEM);
                    ptype.setTitle("PythonEditor");
                }

                if (site.getResourceType("PHPEditor") == null)
                {
                    ResourceType ptype = site.createResourceType("PHPEditor");
                    ptype.setResourceClassName("org.semanticwb.portal.resources.PHPEditor");
                    ptype.setResourceBundle("org.semanticwb.portal.resources.PHPEditor");
                    ptype.setResourceMode(ResourceType.MODE_SYSTEM);
                    ptype.setTitle("PHPEditor");
                }

                if (site.getResourceType("Language") == null)
                {
                    ResourceType ptype = site.createResourceType("Language");
                    ptype.setResourceClassName("org.semanticwb.portal.resources.Language");
                    ptype.setResourceBundle("org.semanticwb.portal.resources.Language");
                    ptype.setResourceMode(ResourceType.MODE_STRATEGY);
                    ptype.setTitle("Language");
                }

                if (site.getResourceType("TematicIndexXSL") == null) {
                    ResourceType ptype = site.createResourceType("TematicIndexXSL");
                    ptype.setResourceClassName("org.semanticwb.portal.resources.TematicIndexXSL");
                    ptype.setResourceBundle("org.semanticwb.portal.resources.TematicIndexXSL");
                    ptype.setResourceMode(1);
                    ptype.setTitle("TematicIndexXSL");
                }


                if (site.getResourceType("FrameContent") == null) {
                    ResourceType ptype = site.createResourceType("FrameContent");
                    ptype.setResourceClassName("org.semanticwb.portal.resources.FrameContent");
                    ptype.setResourceBundle("org.semanticwb.portal.resources.FrameContent");
                    ptype.setResourceMode(1);
                    ptype.setTitle("FrameContent");
                }

                if (site.getResourceType("IFrameContent") == null) {
                    ResourceType ptype = site.createResourceType("IFrameContent");
                    ptype.setResourceClassName("org.semanticwb.portal.resources.IFrameContent");
                    ptype.setResourceBundle("org.semanticwb.portal.resources.IFrameContent");
                    ptype.setResourceMode(1);
                    ptype.setTitle("IFrameContent");
                }

                if (site.getResourceType("CommentSwf") == null) {
                    ResourceType ptype = site.createResourceType("CommentSwf");
                    ptype.setResourceClassName("org.semanticwb.portal.resources.CommentSwf");
                    ptype.setResourceBundle("org.semanticwb.portal.resources.CommentSwf");
                    ptype.setResourceMode(2);
                    ptype.setTitle("CommentSwf");
                }

                if (site.getResourceType("RemoteWebApp") == null) {
                    ResourceType ptype = site.createResourceType("RemoteWebApp");
                    ptype.setResourceClassName("org.semanticwb.portal.resources.RemoteWebApp");
                    ptype.setResourceBundle("org.semanticwb.portal.resources.RemoteWebApp");
                    ptype.setResourceMode(3);
                    ptype.setTitle("RemoteWebApp");
                }

                if (site.getResourceType("UrlContent") == null) {
                    ResourceType ptype = site.createResourceType("UrlContent");
                    ptype.setResourceClassName("org.semanticwb.portal.resources.WBUrlContent");
                    ptype.setResourceBundle("org.semanticwb.portal.resources.WBUrlContent");
                    ptype.setResourceMode(3);
                    ptype.setTitle("UrlContent");
                }

                if (site.getResourceType("Search") == null) {
                    ResourceType ptype = site.createResourceType("Search");
                    ptype.setResourceClassName("org.semanticwb.portal.resources.WBSearch");
                    ptype.setResourceBundle("org.semanticwb.portal.resources.WBSearch");
                    ptype.setResourceMode(3);
                    ptype.setTitle("Search");
                }

                if (site.getResourceType("GoogleGadget") == null) {
                    ResourceType ptype = site.createResourceType("GoogleGadget");
                    ptype.setResourceClassName("org.semanticwb.portal.resources.googlegadgets.GoogleGadget");
                    ptype.setResourceBundle("org.semanticwb.portal.resources.googlegadgets.GoogleGadget");
                    ptype.setResourceMode(3);
                    ptype.setTitle("GoogleGadget");
                }

                if (site.getResourceType("SparQLQueryResource") == null) {
                    ResourceType ptype = site.createResourceType("SparQLQueryResource");
                    ptype.setResourceClassName("org.semanticwb.portal.resources.SparqlQueryResource");
                    ptype.setResourceBundle("org.semanticwb.portal.resources.SparqlQueryResource");
                    ptype.setResourceMode(3);
                    ptype.setTitle("SparQLQueryResource");
                }

                if (site.getResourceType("Wiki") == null) {
                    ResourceType ptype = site.createResourceType("Wiki");
                    ptype.setResourceClassName("org.semanticwb.portal.resources.wiki.Wiki");
                    ptype.setResourceBundle("org.semanticwb.portal.resources.wiki.Wiki");
                    ptype.setResourceMode(3);
                    ptype.setTitle("Wiki");
                }

                if (site.getResourceType("PDFContent") == null) {
                    ResourceType ptype = site.createResourceType("PDFContent");
                    ptype.setResourceClassName("org.semanticwb.portal.resources.PDFContent");
                    ptype.setResourceBundle("org.semanticwb.portal.resources.PDFContent");
                    ptype.setResourceMode(ResourceType.MODE_CONTENT);
                    ptype.setTitle("PDFContent");
                }

                if (site.getResourceType("Login") == null) {
                    ResourceType ptype = site.createResourceType("Login");
                    ptype.setResourceClassName("org.semanticwb.portal.resources.Login");
                    ptype.setResourceBundle("org.semanticwb.portal.resources.Login");
                    ptype.setResourceMode(ResourceType.MODE_STRATEGY);
                    ptype.setTitle("Login");
                }

                if (site.getResourceType("UserRegistration") == null) {
                    ResourceType ptype = site.createResourceType("UserRegistration");
                    ptype.setResourceClassName("org.semanticwb.portal.resources.UserRegistration");
                    ptype.setResourceBundle("org.semanticwb.portal.resources.UserRegistration");
                    ptype.setResourceMode(ResourceType.MODE_SYSTEM);
                    ptype.setTitle("UserRegistration");
                }

                if (site.getResourceType("RSSResource") == null) {
                    ResourceType ptype = site.createResourceType("RSSResource");
                    ptype.setResourceClassName("org.semanticwb.portal.resources.RSSResource");
                    ptype.setResourceBundle("org.semanticwb.portal.resources.RSSResource");
                    ptype.setResourceMode(ResourceType.MODE_CONTENT);
                    ptype.setTitle("RSSResource");
                }

                if (site.getResourceType("DataBaseResource") == null) {
                    ResourceType ptype = site.createResourceType("DataBaseResource");
                    ptype.setResourceClassName("com.infotec.wb.resources.database.DataBaseResource");
                    ptype.setResourceBundle("com.infotec.wb.resources.database.DataBaseResource");
                    ptype.setResourceMode(ResourceType.MODE_SYSTEM);
                    ptype.setTitle("DataBaseResource");
                }

                if (site.getResourceType("Blog") == null) {
                    ResourceType ptype = site.createResourceType("Blog");
                    ptype.setResourceClassName("org.semanticwb.portal.resources.blog.BlogResource");
                    ptype.setResourceBundle("org.semanticwb.portal.resources.blog.BlogResource");
                    ptype.setResourceMode(3);
                    ptype.setTitle("Blog");
                }

                if (site.getResourceType("Comments") == null) {
                    ResourceType ptype = site.createResourceType("Comments");
                    ptype.setResourceClassName("org.semanticwb.portal.resources.sem.SWBComments");
                    ptype.setResourceBundle("org.semanticwb.portal.resources.sem.SWBComments");
                    ptype.setResourceOWL("org.semanticwb.portal.resources.sem.SWBComments");
                    ptype.setResourceMode(ResourceType.MODE_SYSTEM);
                    ptype.setTitle("Comments");
                }

                if (site.getResourceType("Forum") == null) {
                    ResourceType ptype = site.createResourceType("Forum");
                    ptype.setResourceClassName("org.semanticwb.portal.resources.sem.forum.SWBForum");
                    ptype.setResourceBundle("org.semanticwb.portal.resources.sem.forum.SWBForum");
                    ptype.setResourceOWL("org.semanticwb.portal.resources.sem.forum.SWBForum");
                    ptype.setResourceMode(ResourceType.MODE_SYSTEM);
                    ptype.setTitle("Forum");
                }

                if (site.getResourceType("Directory") == null) {
                    ResourceType ptype = site.createResourceType("Directory");
                    ptype.setResourceClassName("org.semanticwb.portal.resources.sem.directory.Directory");
                    ptype.setResourceBundle("org.semanticwb.portal.resources.sem.directory.Directory");
                    ptype.setResourceOWL("org.semanticwb.portal.resources.sem.directory.Directory");
                    ptype.setResourceMode(ResourceType.MODE_SYSTEM);
                    ptype.setTitle("Directory");
                }

                if (site.getResourceType("Events") == null) {
                    ResourceType ptype = site.createResourceType("Events");
                    ptype.setResourceClassName("org.semanticwb.portal.resources.sem.events.Events");
                    ptype.setResourceBundle("org.semanticwb.portal.resources.sem.events.Events");
                    ptype.setResourceOWL("org.semanticwb.portal.resources.sem.events.Events");
                    ptype.setResourceMode(ResourceType.MODE_SYSTEM);
                    ptype.setTitle("Events");
                }

                if (site.getResourceType("WebPageOnLineCreate") == null) {
                    ResourceType ptype = site.createResourceType("WebPageOnLineCreate");
                    ptype.setResourceClassName("org.semanticwb.portal.resources.WebPageOnLineCreate");
                    ptype.setResourceBundle("org.semanticwb.portal.resources.WebPageOnLineCreate");
                    ptype.setResourceMode(ResourceType.MODE_SYSTEM);
                    ptype.setTitle("WebPageOnLineCreate");
                }

                if (site.getResourceType("WebPageOnLineCreate") == null) {
                    ResourceType ptype = site.createResourceType("WebPageOnLineCreate");
                    ptype.setResourceClassName("org.semanticwb.portal.resources.WebPageOnLineCreate");
                    ptype.setResourceBundle("org.semanticwb.portal.resources.WebPageOnLineCreate");
                    ptype.setResourceMode(ResourceType.MODE_SYSTEM);
                    ptype.setTitle("WebPageOnLineCreate");
                }

                if (site.getResourceType("RSSResource") == null) {
                    ResourceType ptype = site.createResourceType("WebPageOnLineCreate");
                    ptype.setResourceClassName("org.semanticwb.portal.resources.RSSResource");
                    ptype.setResourceBundle("org.semanticwb.portal.resources.RSSResource");
                    ptype.setResourceMode(ResourceType.MODE_CONTENT);
                    ptype.setTitle("RSSResource");
                }

                if (site.getResourceType("InlineEdit") == null) {
                    ResourceType ptype = site.createResourceType("InlineEdit");
                    ptype.setResourceClassName("org.semanticwb.portal.resources.InlineEdit");
                    ptype.setResourceBundle("org.semanticwb.portal.resources.InlineEdit");
                    ptype.setResourceMode(ResourceType.MODE_STRATEGY);
                    ptype.setTitle("InlineEdit");
                }

                if (site.getResourceType("ImageGallery") == null) {
                    ResourceType ptype = site.createResourceType("ImageGallery");
                    ptype.setResourceClassName("org.semanticwb.portal.resources.ImageGallery");
                    ptype.setResourceBundle("org.semanticwb.portal.resources.ImageGallery");
                    ptype.setResourceMode(ResourceType.MODE_CONTENT);
                    ptype.setTitle("ImageGallery");
                }

                //Creacion de tipos de recursos por compatibilidad con WB3.2

                if (site.getResourceType("BannerWB3") == null) {
                    ResourceType ptype = site.createResourceType("BannerWB3");
                    ptype.setResourceClassName("com.infotec.wb.resources.Banner");
                    ptype.setResourceBundle("com.infotec.wb.resources.Banner");
                    ptype.setResourceMode(ResourceType.MODE_STRATEGY);
                    ptype.setTitle("Banner(WB3)");
                }

                if (site.getResourceType("ChangePasswordWB3") == null) {
                    ResourceType ptype = site.createResourceType("ChangePasswordWB3");
                    ptype.setResourceClassName("com.infotec.wb.resources.ChangePassword");
                    ptype.setResourceBundle("com.infotec.wb.resources.ChangePassword");
                    ptype.setResourceMode(ResourceType.MODE_STRATEGY);
                    ptype.setTitle("ChangePassword(WB3)");
                }
                if (site.getResourceType("CommentWB3") == null) {
                    ResourceType ptype = site.createResourceType("CommentWB3");
                    ptype.setResourceClassName("com.infotec.wb.resources.Comment");
                    ptype.setResourceBundle("com.infotec.wb.resources.Comment");
                    ptype.setResourceMode(ResourceType.MODE_STRATEGY);
                    ptype.setTitle("Comment(WB3)");
                }
                if (site.getResourceType("CommentSWBFWB3") == null) {
                    ResourceType ptype = site.createResourceType("CommentSWFWB3");
                    ptype.setResourceClassName("com.infotec.wb.resources.CommentSWF");
                    ptype.setResourceBundle("com.infotec.wb.resources.CommentSWF");
                    ptype.setResourceMode(ResourceType.MODE_STRATEGY);
                    ptype.setTitle("CommentSWF(WB3)");
                }
                if (site.getResourceType("ControlsWB3") == null) {
                    ResourceType ptype = site.createResourceType("ControlsWB3");
                    ptype.setResourceClassName("com.infotec.wb.resources.Controls");
                    ptype.setResourceBundle("com.infotec.wb.resources.Controls");
                    ptype.setResourceMode(ResourceType.MODE_STRATEGY);
                    ptype.setTitle("Controls(WB3)");
                }
                if (site.getResourceType("CountDownResourceWB3") == null) {
                    ResourceType ptype = site.createResourceType("CountDownResourceWB3");
                    ptype.setResourceClassName("com.infotec.wb.resources.CountDownResource");
                    ptype.setResourceBundle("com.infotec.wb.resources.CountDownResource");
                    ptype.setResourceMode(ResourceType.MODE_STRATEGY);
                    ptype.setTitle("CountDownResource(WB3)");
                }
                if (site.getResourceType("FavoriteTopicsWB3") == null) {
                    ResourceType ptype = site.createResourceType("FavoriteTopicsWB3");
                    ptype.setResourceClassName("com.infotec.wb.resources.FavoriteTopics");
                    ptype.setResourceBundle("com.infotec.wb.resources.FavoriteTopics");
                    ptype.setResourceMode(ResourceType.MODE_STRATEGY);
                    ptype.setTitle("FavoriteTopics(WB3)");
                }
                if (site.getResourceType("FrameContentWB3") == null) {
                    ResourceType ptype = site.createResourceType("FrameContentWB3");
                    ptype.setResourceClassName("com.infotec.wb.resources.FrameContent");
                    ptype.setResourceBundle("com.infotec.wb.resources.FrameContent");
                    ptype.setResourceMode(ResourceType.MODE_STRATEGY);
                    ptype.setTitle("FrameContent(WB3)");
                }
                if (site.getResourceType("HtmlContentWB3") == null) {
                    ResourceType ptype = site.createResourceType("HtmlContentWB3");
                    ptype.setResourceClassName("com.infotec.wb.resources.HtmlContent");
                    ptype.setResourceBundle("com.infotec.wb.resources.HtmlContent");
                    ptype.setResourceMode(ResourceType.MODE_CONTENT);
                    ptype.setTitle("HtmlContent(WB3)");
                }
                if (site.getResourceType("IFrameContentWB3") == null) {
                    ResourceType ptype = site.createResourceType("IFrameContentWB3");
                    ptype.setResourceClassName("com.infotec.wb.resources.IFrameContent");
                    ptype.setResourceBundle("com.infotec.wb.resources.IFrameContent");
                    ptype.setResourceMode(ResourceType.MODE_CONTENT);
                    ptype.setTitle("IFrameContent(WB3)");;
                }
                if (site.getResourceType("JSPResourceWB3") == null) {
                    ResourceType ptype = site.createResourceType("JSPResourceWB3");
                    ptype.setResourceClassName("com.infotec.wb.resources.JSPResource");
                    ptype.setResourceBundle("com.infotec.wb.resources.JSPResource");
                    ptype.setResourceMode(ResourceType.MODE_CONTENT);
                    ptype.setTitle("JSPResource(WB3)");
                }
                if (site.getResourceType("LanguageWB3") == null) {
                    ResourceType ptype = site.createResourceType("LanguageWB3");
                    ptype.setResourceClassName("com.infotec.wb.resources.Language");
                    ptype.setResourceBundle("com.infotec.wb.resources.Language");
                    ptype.setResourceMode(ResourceType.MODE_SYSTEM);
                    ptype.setTitle("Language(WB3)");
                }
                if (site.getResourceType("LoginWB3") == null) {
                    ResourceType ptype = site.createResourceType("LoginWB3");
                    ptype.setResourceClassName("com.infotec.wb.resources.Login");
                    ptype.setResourceBundle("com.infotec.wb.resources.Login");
                    ptype.setResourceMode(ResourceType.MODE_STRATEGY);
                    ptype.setTitle("Login(WB3)");
                }
                if (site.getResourceType("LogoutWB3") == null) {
                    ResourceType ptype = site.createResourceType("LogoutWB3");
                    ptype.setResourceClassName("com.infotec.wb.resources.Logout");
                    ptype.setResourceBundle("com.infotec.wb.resources.Logout");
                    ptype.setResourceMode(ResourceType.MODE_STRATEGY);
                    ptype.setTitle("Logout(WB3)");
                }
                if (site.getResourceType("MapTreeWB3") == null) {
                    ResourceType ptype = site.createResourceType("MapTreeWB3");
                    ptype.setResourceClassName("com.infotec.wb.resources.MapTree");
                    ptype.setResourceBundle("com.infotec.wb.resources.MapTree");
                    ptype.setResourceMode(ResourceType.MODE_SYSTEM);
                    ptype.setTitle("MapTree(WB3)");
                }
                if (site.getResourceType("MenuWB3") == null) {
                    ResourceType ptype = site.createResourceType("MenuWB3");
                    ptype.setResourceClassName("com.infotec.wb.resources.Menu");
                    ptype.setResourceBundle("com.infotec.wb.resources.Menu");
                    ptype.setResourceMode(ResourceType.MODE_SYSTEM);
                    ptype.setTitle("Menu(WB3)");
                }
                if (site.getResourceType("PDFContentWB3") == null) {
                    ResourceType ptype = site.createResourceType("PDFContentWB3");
                    ptype.setResourceClassName("com.infotec.wb.resources.PDFContent");
                    ptype.setResourceBundle("com.infotec.wb.resources.PDFContent");
                    ptype.setResourceMode(ResourceType.MODE_CONTENT);
                    ptype.setTitle("PDFContent(WB3)");
                }
                if (site.getResourceType("PollWB3") == null) {
                    ResourceType ptype = site.createResourceType("PollWB3");
                    ptype.setResourceClassName("com.infotec.wb.resources.Poll");
                    ptype.setResourceBundle("com.infotec.wb.resources.Poll");
                    ptype.setResourceMode(ResourceType.MODE_SYSTEM);
                    ptype.setTitle("Poll(WB3)");
                }
                if (site.getResourceType("PrintWB3") == null) {
                    ResourceType ptype = site.createResourceType("PrintWB3");
                    ptype.setResourceClassName("com.infotec.wb.resources.Print");
                    ptype.setResourceBundle("com.infotec.wb.resources.Print");
                    ptype.setResourceMode(ResourceType.MODE_SYSTEM);
                    ptype.setTitle("Print(WB3)");
                }
                if (site.getResourceType("PromoWB3") == null) {
                    ResourceType ptype = site.createResourceType("PromoWB3");
                    ptype.setResourceClassName("com.infotec.wb.resources.Promo");
                    ptype.setResourceBundle("com.infotec.wb.resources.Promo");
                    ptype.setResourceMode(ResourceType.MODE_SYSTEM);
                    ptype.setTitle("Promo(WB3)");
                }
                if (site.getResourceType("QueryResourceWB3") == null) {
                    ResourceType ptype = site.createResourceType("QueryResourceWB3");
                    ptype.setResourceClassName("com.infotec.wb.resources.QueryResource");
                    ptype.setResourceBundle("com.infotec.wb.resources.QueryResource");
                    ptype.setResourceMode(ResourceType.MODE_CONTENT);
                    ptype.setTitle("QueryResource(WB3)");
                }
                if (site.getResourceType("RSSResourceWB3") == null) {
                    ResourceType ptype = site.createResourceType("RSSResourceWB3");
                    ptype.setResourceClassName("com.infotec.wb.resources.RSSResource");
                    ptype.setResourceBundle("com.infotec.wb.resources.RSSResource");
                    ptype.setResourceMode(ResourceType.MODE_SYSTEM);
                    ptype.setTitle("RSSResource(WB3)");
                }
                if (site.getResourceType("RelatedTopicsWB3") == null) {
                    ResourceType ptype = site.createResourceType("RelatedTopicsWB3");
                    ptype.setResourceClassName("com.infotec.wb.resources.RelatedTopics");
                    ptype.setResourceBundle("com.infotec.wb.resources.RelatedTopics");
                    ptype.setResourceMode(ResourceType.MODE_SYSTEM);
                    ptype.setTitle("RelatedTopics(WB3)");
                }
                if (site.getResourceType("RemoteWebAppWB3") == null) {
                    ResourceType ptype = site.createResourceType("RemoteWebAppWB3");
                    ptype.setResourceClassName("com.infotec.wb.resources.RemoteWebApp");
                    ptype.setResourceBundle("com.infotec.wb.resources.RemoteWebApp");
                    ptype.setResourceMode(ResourceType.MODE_SYSTEM);
                    ptype.setTitle("RemoteWebApp(WB3)");
                }
                if (site.getResourceType("SearchWB3") == null) {
                    ResourceType ptype = site.createResourceType("SearchWB3");
                    ptype.setResourceClassName("com.infotec.wb.resources.WBSearch");
                    ptype.setResourceBundle("com.infotec.wb.resources.WBSearch");
                    ptype.setResourceMode(ResourceType.MODE_SYSTEM);
                    ptype.setTitle("WBSearch(WB3)");
                }
                if (site.getResourceType("SendRedirectWB3") == null) {
                    ResourceType ptype = site.createResourceType("SendRedirectWB3");
                    ptype.setResourceClassName("com.infotec.wb.resources.SendRedirect");
                    ptype.setResourceBundle("com.infotec.wb.resources.SendRedirect");
                    ptype.setResourceMode(ResourceType.MODE_SYSTEM);
                    ptype.setTitle("SendRedirect(WB3)");
                }
                if (site.getResourceType("StaticTextWB3") == null) {
                    ResourceType ptype = site.createResourceType("StaticTextWB3");
                    ptype.setResourceClassName("com.infotec.wb.resources.StaticText");
                    ptype.setResourceBundle("com.infotec.wb.resources.StaticText");
                    ptype.setResourceMode(ResourceType.MODE_SYSTEM);
                    ptype.setTitle("StaticText(WB3)");
                }
                if (site.getResourceType("TematicIndexXSLWB3") == null) {
                    ResourceType ptype = site.createResourceType("TematicIndexXSLWB3");
                    ptype.setResourceClassName("com.infotec.wb.resources.TematicIndexXSL");
                    ptype.setResourceBundle("com.infotec.wb.resources.TematicIndexXSL");
                    ptype.setResourceMode(ResourceType.MODE_SYSTEM);
                    ptype.setTitle("TematicIndexXSL(WB3)");
                }
                if (site.getResourceType("VirtualResourceWB3") == null) {
                    ResourceType ptype = site.createResourceType("VirtualResourceWB3");
                    ptype.setResourceClassName("com.infotec.wb.resources.VirtualResource");
                    ptype.setResourceBundle("com.infotec.wb.resources.VirtualResource");
                    ptype.setResourceMode(ResourceType.MODE_SYSTEM);
                    ptype.setTitle("VirtualResource(WB3)");
                }
                if (site.getResourceType("WBDateWB3") == null) {
                    ResourceType ptype = site.createResourceType("WBDateWB3");
                    ptype.setResourceClassName("com.infotec.wb.resources.WBDate");
                    ptype.setResourceBundle("com.infotec.wb.resources.WBDate");
                    ptype.setResourceMode(ResourceType.MODE_SYSTEM);
                    ptype.setTitle("WBDate(WB3)");
                }
                if (site.getResourceType("WBMenuWB3") == null) {
                    ResourceType ptype = site.createResourceType("WBMenuWB3");
                    ptype.setResourceClassName("com.infotec.wb.resources.WBMenu");
                    ptype.setResourceBundle("com.infotec.wb.resources.WBMenu");
                    ptype.setResourceMode(ResourceType.MODE_SYSTEM);
                    ptype.setTitle("WBMenu(WB3)");
                }
                if (site.getResourceType("WBMenuMapWB3") == null) {
                    ResourceType ptype = site.createResourceType("WBMenuMapWB3");
                    ptype.setResourceClassName("com.infotec.wb.resources.WBMenuMap");
                    ptype.setResourceBundle("com.infotec.wb.resources.WBMenuMap");
                    ptype.setResourceMode(ResourceType.MODE_SYSTEM);
                    ptype.setTitle("WBMenuMap(WB3)");
                }
                if (site.getResourceType("WBMenuNivelWB3") == null) {
                    ResourceType ptype = site.createResourceType("WBMenuNivelWB3");
                    ptype.setResourceClassName("com.infotec.wb.resources.WBMenuNivel");
                    ptype.setResourceBundle("com.infotec.wb.resources.WBMenuNivel");
                    ptype.setResourceMode(ResourceType.MODE_SYSTEM);
                    ptype.setTitle("WBMenuNivel(WB3)");
                }
                if (site.getResourceType("WBSiteMapWB3") == null) {
                    ResourceType ptype = site.createResourceType("WBSiteMapWB3");
                    ptype.setResourceClassName("com.infotec.wb.resources.WBSiteMap");
                    ptype.setResourceBundle("com.infotec.wb.resources.WBSiteMap");
                    ptype.setResourceMode(ResourceType.MODE_SYSTEM);
                    ptype.setTitle("WBSiteMap(WB3)");
                }
                if (site.getResourceType("WBUrlContentWB3") == null) {
                    ResourceType ptype = site.createResourceType("WBUrlContentWB3");
                    ptype.setResourceClassName("com.infotec.wb.resources.WBUrlContent");
                    ptype.setResourceBundle("com.infotec.wb.resources.WBUrlContent");
                    ptype.setResourceMode(ResourceType.MODE_SYSTEM);
                    ptype.setTitle("WBUrlContent(WB3)");
                }
                if (site.getResourceType("WindowWB3") == null) {
                    ResourceType ptype = site.createResourceType("WindowWB3");
                    ptype.setResourceClassName("com.infotec.wb.resources.Window");
                    ptype.setResourceBundle("com.infotec.wb.resources.Window");
                    ptype.setResourceMode(ResourceType.MODE_SYSTEM);
                    ptype.setTitle("Window(WB3)");
                }
                if (site.getResourceType("DataBaseResourceWB3") == null) {
                    ResourceType ptype = site.createResourceType("DataBaseResourceWB3");
                    ptype.setResourceClassName("com.infotec.wb.resources.database.DataBaseResource");
                    ptype.setResourceBundle("com.infotec.wb.resources.database.DataBaseResource");
                    ptype.setResourceMode(ResourceType.MODE_SYSTEM);
                    ptype.setTitle("DataBaseResource(WB3)");
                }
                if (site.getResourceType("MainSurveyWB3") == null) {
                    ResourceType ptype = site.createResourceType("MainSurveyWB3");
                    ptype.setResourceClassName("com.infotec.wb.resources.survey.MainSurvey");
                    ptype.setResourceBundle("com.infotec.wb.resources.survey.MainSurvey");
                    ptype.setResourceMode(ResourceType.MODE_SYSTEM);
                    ptype.setTitle("MainSurvey(WB3)");
                }

                //Crea dispositivos de defecto
                if (!site.hasDevice("pc")) {
                    Device dev = site.createDevice("pc");
                    dev.setTitle("PC");
                    dev.setUserAgent("Mozilla");
                    Device dev2 = site.createDevice("iexplorer");
                    dev2.setTitle("Internet Explorer");
                    dev2.setUserAgent("Mozilla MSIE");
                    dev2.setParent(dev);
                    dev2 = site.createDevice("firefox");
                    dev2.setTitle("Mozilla FireFox");
                    dev2.setUserAgent("Mozilla Firefox");
                    dev2.setParent(dev);
                    dev2 = site.createDevice("safari");
                    dev2.setTitle("Safari");
                    dev2.setUserAgent("Mozilla Safari");
                    dev2.setParent(dev);

                    dev = site.createDevice("pda");
                    dev.setTitle("PDA");
                    dev2 = site.createDevice("blackberry");
                    dev2.setTitle("BlackBerry");
                    dev2.setUserAgent("BlackBerry");
                    dev2.setParent(dev);
                    dev2 = site.createDevice("iphone");
                    dev2.setTitle("iPhone");
                    dev2.setUserAgent("Mozilla iPhone Safari");
                    dev2.setParent(dev);
                    dev2 = site.createDevice("wince");
                    dev2.setTitle("Windows CE");
                    dev2.setUserAgent("Mozilla Windows CE");
                    dev2.setParent(dev);
                    dev2 = site.createDevice("palmos");
                    dev2.setTitle("PalmOS");
                    dev2.setUserAgent("Mozilla PalmOS");
                    dev2.setParent(dev);
                    Device dev3 = site.createDevice("avantgo");
                    dev3.setTitle("AvantGo");
                    dev3.setUserAgent("Mozilla AvantGo");
                    dev3.setParent(dev2);
                    dev3 = site.createDevice("eudoraweb");
                    dev3.setTitle("EudoraWeb");
                    dev3.setUserAgent("Mozilla PalmOS EudoraWeb");
                    dev3.setParent(dev2);

                    dev = site.createDevice("phone");
                    dev.setTitle("Phone");
                    dev2 = site.createDevice("midp");
                    dev2.setTitle("MIDP");
                    dev2.setUserAgent("MIDP");
                    dev2.setParent(dev);
                    dev2 = site.createDevice("mmp");
                    dev2.setTitle("MMP");
                    dev2.setUserAgent("MMP");
                    dev2.setParent(dev);
                    dev2 = site.createDevice("opera");
                    dev2.setTitle("Opera Mobi");
                    dev2.setUserAgent("Opera Mobi");
                    dev2.setParent(dev);
                }

                //Crear lenguajes por defecto
                Language lang = site.createLanguage("es");
                lang.setTitle("Español");
                lang.setTitle("Español", "es");
                lang.setTitle("Spanish", "en");
                lang = site.createLanguage("en");
                lang.setTitle("Ingles");
                lang.setTitle("Ingles", "es");
                lang.setTitle("English", "en");

                site.getSemanticObject().getModel().setTraceable(false);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    HashMap loadUsrPropertyMatch()
    {
        HashMap hm = new HashMap();

        hm.put("isregistered","SWBIsRegistered");
        hm.put("isloged","SWBIsSigned");
        hm.put("device","SWBDevice");
        hm.put("CNF_WBRole","SWBRole");
        hm.put("FNAME","usrFirstName");
        hm.put("USERTYPE","hasUserType");
        hm.put("LANGUAGE","usrLanguage");
        hm.put("LOGIN","usrLogin");
        hm.put("EMAIL","usrEmail");
        hm.put("LNAME","usrLastName");
        hm.put("CNF_WBRule","SWBRule");
        hm.put("MNAME","usrSecondLastName");
        hm.put("CONFIRMVALUE","usrSecurityAnswer");
        return hm;
    }



%>