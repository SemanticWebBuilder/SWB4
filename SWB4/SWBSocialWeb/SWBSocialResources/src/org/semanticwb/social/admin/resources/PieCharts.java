/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.social.admin.resources;

import com.hp.hpl.jena.sparql.lib.org.json.JSONArray;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.json.JSONException;
import org.json.JSONObject;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.WebSite;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;
import org.semanticwb.social.MessageIn;
import org.semanticwb.social.PhotoIn;
import org.semanticwb.social.PostIn;
import org.semanticwb.social.Stream;
import org.semanticwb.social.VideoIn;
import org.semanticwb.social.util.SWBSocialUtil;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.social.*;
import java.util.Iterator;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.*;
import org.semanticwb.SWBPortal;
import org.semanticwb.platform.SemanticProperty;
import org.semanticwb.portal.api.*;
import org.semanticwb.social.admin.resources.util.SWBSocialResUtil;
import org.semanticwb.social.Country;
import sun.security.jca.GetInstance;

/**
 *
 * @author jorge.jimenez
 */
public class PieCharts extends GenericResource {

    private static Logger log = SWBUtils.getLogger(PieCharts.class);

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {


        if (request.getParameter("doView") == null) {
            doEdit(request, response, paramRequest);
            return;
        }
        final String myPath = SWBPlatform.getContextPath() + "/work/models/" + paramRequest.getWebPage().getWebSiteId() + "/jsp/stream/pieCharts.jsp";
        RequestDispatcher dis = request.getRequestDispatcher(myPath);
        if (dis != null) {
            try {
                request.setAttribute("paramRequest", paramRequest);
                request.setAttribute("suri", request.getParameter("suri"));
                dis.include(request, response);
            } catch (Exception ex) {
                log.error(ex);
            }
        }

    }

    @Override
    public void doEdit(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        out.println("<iframe width=\"100%\" height=\"100%\" src=\"" + paramRequest.getRenderUrl().setMode(SWBResourceURL.Mode_VIEW).setParameter("doView", "1").setParameter("suri", request.getParameter("suri")) + "\"></iframe> ");
    }

    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        if (paramRequest.getMode().equals("InfographBar")) {
            doPreview(request, response, paramRequest);
        } else if (paramRequest.getMode().equals("showGraphBar") || paramRequest.getMode().equals("anioMes")) {
            showGraphBar(request, response, paramRequest);
        } else if (paramRequest.getMode().equals("exportExcel")) {
            try {
                doGenerateReport(request, response, paramRequest);
            } catch (Exception e) {
                log.error(e);
            }
        } else if (paramRequest.getMode().equals("doview")) {
            doView(request, response, paramRequest);
        } else {
            super.processRequest(request, response, paramRequest);
        }
    }

    private void doPreview(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws IOException {
        RequestDispatcher rd = request.getRequestDispatcher(SWBPlatform.getContextPath() + "/work/models/" + paramRequest.getWebPage().getWebSiteId() + "/jsp/stream/graphBarFilter.jsp");
        request.setAttribute("suri", request.getParameter("suri"));
        request.setAttribute("selected", request.getParameter("selected"));
        request.setAttribute("selectAnio2", request.getParameter("selectAnio2"));
        request.setAttribute("selectMes", request.getParameter("selectMes"));
        request.setAttribute("paramRequest", paramRequest);
        try {
            rd.include(request, response);
        } catch (ServletException ex) {
            log.error("Error  " + ex.getMessage());
        }
    }

    private void showGraphBar(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws IOException, SWBResourceException {
        PrintWriter out = response.getWriter();

        if (request.getParameter("doViewGraph") == null) {
            doEditGraph(request, response, paramRequest);
            return;
        }

        final String myPath = SWBPlatform.getContextPath() + "/work/models/" + paramRequest.getWebPage().getWebSiteId() + "/jsp/stream/showGraphBar.jsp";
        RequestDispatcher dis = request.getRequestDispatcher(myPath);
        if (dis != null) {
            try {
                request.setAttribute("suri", request.getParameter("suri"));
                request.setAttribute("paramRequest", paramRequest);
                dis.include(request, response);
            } catch (Exception ex) {
                log.error(ex);
            }
        }
    }

    public void doEditGraph(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();

        String selectedanio = request.getParameter("selectedAnio") == null ? "" : request.getParameter("selectedAnio");
        String selectAnio = request.getParameter("selectAnio") == null ? "" : request.getParameter("selectAnio");
        String selectMes = request.getParameter("selectMes") == null ? "" : request.getParameter("selectMes");
        out.println("<iframe  id=\"inneriframe\" src=\"" + paramRequest.getRenderUrl().setMode("showGraphBar").setParameter("doViewGraph", "1").setParameter("suri", request.getParameter("suri")).setParameter("selectedAnio", selectedanio).setParameter("selectAnio", selectAnio).setParameter("selectMes", selectMes) + "\"  frameborder=\"0\" width=\"100%\"   marginheight=\"0\" marginwidth=\"0\"  scrolling=\"no\"></iframe>"); //frameborder=\"0\" style=\"overflow:hidden;overflow-x:hidden;overflow-y:hidden;height:100%;width:100%;position:absolute;top:0px;left:0px;right:0px;bottom:0px\" height=\"100%\" width=\"100%\" ></iframe> ");


    }

    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
        String action = response.getAction();
        if (action.equals("")) {
            response.setAction("export");
        }
    }

    private void doGenerateReport(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws JSONException, IOException, com.hp.hpl.jena.sparql.lib.org.json.JSONException {
        String args = request.getParameter("args");
        String suri = request.getParameter("suri");
        String country = request.getParameter("country");

        String title = "";

        if (SemanticObject.getSemanticObject(suri).getGenericInstance() instanceof Stream) {
            Stream stream = (Stream) SemanticObject.getSemanticObject(suri).getGenericInstance();
            title = stream.getTitle();

        } else if (SemanticObject.getSemanticObject(suri).getGenericInstance() instanceof SocialTopic) {
            SocialTopic sTopic = (SocialTopic) SemanticObject.getSemanticObject(suri).getGenericInstance();
            title = sTopic.getTitle();
        }


        String type = request.getParameter("type");
        String filter = request.getParameter("filter");
        String filterGeneral = request.getParameter("filterGeneral");
        if (filter == null) {
            filter = "";
        }

        String lang = paramRequest.getUser().getLanguage();
        Iterator<PostIn> setso = null;
        if (type.equals("gender")) {
            setso = getListGender(suri, lang, filter, filterGeneral);
        } else if (type.equals("education")) {
            setso = getListEducation(suri, lang, filter, filterGeneral);
        } else if (type.equals("relation")) {
            setso = getRelationShip(suri, lang, filter, filterGeneral);
        } else if (type.equals("lifeStage")) {
            setso = getLifeStage(suri, lang, filter, filterGeneral);
        } else if (type.equals("geoLocation")) { //mexico
            HashMap map = new HashMap();
            WebSite ss = SWBSocialUtil.getConfigWebSite();
            Iterator i = null;

            i = CountryState.ClassMgr.listCountryStates(ss);
            while (i.hasNext()) {
                CountryState c = (CountryState) i.next();
                if (c.getCountry().getId().equals(country)) {
                    map.put(reemplazar(c.getTitle()), new ArrayList<PostIn>());
                }
            }
            setso = getGeoLocation(suri, lang, filter, filterGeneral, map, country);
           // System.out.println(" FILTER JAVA : " + filter);
            //System.out.println(" FILTER GENERAL JAVA : " + filterGeneral);
            ArrayList lista = new ArrayList<PostIn>();
            if (filterGeneral.equals("all")) {
                while (setso.hasNext()) {
                    Map.Entry e = (Map.Entry) setso.next();
                    //System.out.println("valor : " + e.getValue());
                    ArrayList list = (ArrayList) e.getValue();
                    if (list.isEmpty()) {
                        continue;
                    }
                    for (int j = 0; j < list.size(); j++) {
                       // System.out.println("agreago a lista de post : " + list.get(j));
                        if (filter.equals("")) {
                            lista.add(list.get(j));
                        } else if (filter.equals(e.getKey())) {
                            lista.add(list.get(j));
                        }
                    }
                }
                setso = lista.iterator();
            } else {
                while (setso.hasNext()) {
                    Map.Entry e = (Map.Entry) setso.next();
                    //System.out.println("...................." + e.getKey() + " - " + e.getValue());

                    if (filterGeneral.equals(e.getKey())) {
                        ArrayList list = (ArrayList) e.getValue();
                        if (list.isEmpty()) {
                            continue;
                        }
                        for (int j = 0; j < list.size(); j++) {
                            PostIn p = (PostIn) list.get(j);
                            if (filter.equals("")) {
                                lista.add(list.get(j));
                            } else {
                                if (filter.equals("Positivos") && p.getPostSentimentalType() == 1) {
                                    lista.add(list.get(j));
                                } else if (filter.equals("Negativos") && p.getPostSentimentalType() == 2) {
                                    lista.add(list.get(j));
                                } else if (filter.equals("Neutros") && p.getPostSentimentalType() == 0) {
                                    lista.add(list.get(j));
                                }
                            }
                        }
                    }

                }
                setso = lista.iterator();
            }
        } else if (type.equals("geolocationCountry")) { //colecciones dadas de alta en countrys
            // setso = getGeoLocation(suri, lang, filter, filterGeneral);
            HashMap map = new HashMap();
            WebSite ss = SWBSocialUtil.getConfigWebSite();
            Iterator i = null;

            i = Country.ClassMgr.listCountries(ss);
            while (i.hasNext()) {
                Country c = (Country) i.next();
                map.put(reemplazar(c.getTitle()), new ArrayList<PostIn>());

            }
            map.put("No definido", new ArrayList<PostIn>());
            setso = getGeoLocationCountrys(suri, lang, filter, filterGeneral, map, country);
            //System.out.println(" FILTER JAVA : " + filter);
            //System.out.println(" FILTER GENERAL JAVA : " + filterGeneral);
            ArrayList lista = new ArrayList<PostIn>();
            if (reemplazar(filterGeneral).equals("all")) {
                while (setso.hasNext()) {
                    Map.Entry e = (Map.Entry) setso.next();
                    //      System.out.println("key : " + e.getKey());
                    //    System.out.println("valor : " + e.getValue());
                    ArrayList list = (ArrayList) e.getValue();
                    if (list.isEmpty()) {
                        continue;
                    }
                    for (int j = 0; j < list.size(); j++) {
                        //      System.out.println("agreago a lista de post : " + list.get(j));
                        if (filter.equals("")) {
                            lista.add(list.get(j));
                        } else if (filter.equals(e.getKey())) {
                            lista.add(list.get(j));
                        }
                    }
                }
                setso = lista.iterator();
            } else {
                while (setso.hasNext()) {
                    Map.Entry e = (Map.Entry) setso.next();
                    //System.out.println("...................." + e.getKey() + " - " + e.getValue());

                    if (reemplazar(filterGeneral).equals(e.getKey())) {
                        ArrayList list = (ArrayList) e.getValue();
                        if (list.isEmpty()) {
                            continue;
                        }
                        for (int j = 0; j < list.size(); j++) {
                            PostIn p = (PostIn) list.get(j);
                            if (filter.equals("")) {
                                lista.add(list.get(j));
                            } else {
                                if (filter.equals("Positivos") && p.getPostSentimentalType() == 1) {
                                    lista.add(list.get(j));
                                } else if (filter.equals("Negativos") && p.getPostSentimentalType() == 2) {
                                    lista.add(list.get(j));
                                } else if (filter.equals("Neutros") && p.getPostSentimentalType() == 0) {
                                    lista.add(list.get(j));
                                }
                            }
                        }
                    }

                }
                setso = lista.iterator();
            }
        } else if (type.equals("languages")) { //lengajes
            //setso = getGeoLocation(suri, lang, filter, filterGeneral);
            HashMap map = new HashMap();
            WebSite ss = SWBSocialUtil.getConfigWebSite();
            Iterator i = null;

            i = Language.ClassMgr.listLanguages(ss);
            while (i.hasNext()) {
                Language language = (Language) i.next();
                map.put(reemplazar(language.getTitle()), new ArrayList<PostIn>());
            }
            map.put("No definido", new ArrayList<PostIn>());

            setso = getLanguage(suri, lang, filter, filterGeneral, map);
            //System.out.println(" FILTER JAVA language : " + filter);
           // System.out.println(" FILTER GENERAL JAVA language : " + filterGeneral);
            ArrayList lista = new ArrayList<PostIn>();
            if (reemplazar(filterGeneral).equals("all")) {
                while (setso.hasNext()) {
                    Map.Entry e = (Map.Entry) setso.next();
                    //       System.out.println("valor : " + e.getValue());
                    //     System.out.println("key : " + e.getKey());
                    ArrayList list = (ArrayList) e.getValue();
                    if (list.isEmpty()) {
                        continue;
                    }
                    for (int j = 0; j < list.size(); j++) {

                        if (filter.equals("")) {
                            //           System.out.println("se agreago a vacio");

                            lista.add(list.get(j));
                        } else if (filter.equals(e.getKey())) {
                            //                                     System.out.println("se agreago a igual a key");

                            lista.add(list.get(j));
                        }
                    }
                }
                setso = lista.iterator();
            } else {
                while (setso.hasNext()) {
                    Map.Entry e = (Map.Entry) setso.next();
                    //System.out.println("...................." + e.getKey() + " - " + e.getValue());
                    if (reemplazar(filterGeneral).equals(e.getKey())) {
                        ArrayList list = (ArrayList) e.getValue();
                        if (list.isEmpty()) {
                            continue;
                        }
                        for (int j = 0; j < list.size(); j++) {
                            PostIn p = (PostIn) list.get(j);
                            if (filter.equals("")) {
                                lista.add(list.get(j));
                            } else {
                                if (filter.equals("Positivos") && p.getPostSentimentalType() == 1) {
                                    lista.add(list.get(j));
                                } else if (filter.equals("Negativos") && p.getPostSentimentalType() == 2) {
                                    lista.add(list.get(j));
                                } else if (filter.equals("Neutros") && p.getPostSentimentalType() == 0) {
                                    lista.add(list.get(j));
                                }
                            }
                        }
                    }


                }
                setso = lista.iterator();
            }
        }

        try {

            createExcel(setso, paramRequest, response, title);

        } catch (Exception e) {
            log.error(e);
        }
    }

    public void createExcel(Iterator setso, SWBParamRequest paramRequest, HttpServletResponse response, String titleR) {
        try {
            //System.out.println("entro a generar excel");
            // Defino el Libro de Excel
            // Iterator v = setso.iterator();
            String title = titleR;


            HSSFWorkbook wb = new HSSFWorkbook();

            // Creo la Hoja en Excel
            Sheet sheet = wb.createSheet("Mensajes " + title);


            sheet.setDisplayGridlines(false);
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 13));

            // creo una nueva fila
            Row trow = sheet.createRow((short) 0);
            createTituloCell(wb, trow, 0, CellStyle.ALIGN_CENTER,
                    CellStyle.VERTICAL_CENTER, "Mensajes " + title);

            // Creo la cabecera de mi listado en Excel
            Row row = sheet.createRow((short) 2);

            // Creo las celdas de mi fila, se puede poner un diseño a la celda

            CellStyle cellStyle = wb.createCellStyle();

            createHead(wb, row, 0, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, "Mensaje");
            createHead(wb, row, 1, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, "Tipo");
            createHead(wb, row, 2, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, "Red");
            createHead(wb, row, 3, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, "Tema");
            createHead(wb, row, 4, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, "Creación");
            createHead(wb, row, 5, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, "Sentimiento");
            createHead(wb, row, 6, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, "Intensidad");
            createHead(wb, row, 7, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, "Emot");
            createHead(wb, row, 8, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, "RT/Likes");
            createHead(wb, row, 9, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, "Usuario");
            createHead(wb, row, 10, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, "Seguidores");
            createHead(wb, row, 11, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, "Amigos");
            //createHead(wb, row, 12, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, "Klout");
            createHead(wb, row, 12, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, "Lugar");
            createHead(wb, row, 13, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, "Prioritario");

            String lang = paramRequest.getUser().getLanguage();

            //Número de filas
            int i = 3;
            Iterator listIterator = null;


            while (setso.hasNext()) {
                //Map.Entry pairs = (Map.Entry) setso.next();
                // System.out.println("pairs" + pairs);
                PostIn postIn = (PostIn) setso.next();

                //   ArrayList lista = (ArrayList) pairs.getValue();
                // System.out.println("lista size" + lista.size());

                //if (lista.isEmpty()) {
                //    continue;
                //  }

                //listIterator = lista.iterator();
                // while (listIterator.hasNext()) {
                // System.out.println("--------------->" +  listIterator.next());
                //PostIn postIn = (PostIn) listIterator.next();
                //  System.out.println("--------------->" + postIn);
                // PostIn postIn = null;//(PostIn) pairs.getValue();
                Row troww = sheet.createRow((short) i);

                if (postIn.getMsg_Text() != null) {
                    if (postIn.getMsg_Text().length() > 2000) {
                        createCell(cellStyle, wb, troww, 0, CellStyle.ALIGN_LEFT, CellStyle.VERTICAL_CENTER, postIn.getMsg_Text().substring(0, 2000));

                    } else {
                        createCell(cellStyle, wb, troww, 0, CellStyle.ALIGN_LEFT, CellStyle.VERTICAL_CENTER, postIn.getMsg_Text());
                    }

                } /*else if (postIn.getDescription() != null) {
                 if (postIn.getDescription().length() > 200) {
                 createCell(cellStyle, wb, troww, 0, CellStyle.ALIGN_LEFT, CellStyle.VERTICAL_CENTER, postIn.getDescription().substring(0, 200));

                 } else {
                 createCell(cellStyle, wb, troww, 0, CellStyle.ALIGN_LEFT, CellStyle.VERTICAL_CENTER, postIn.getDescription());
                 }
                 } */ else if (postIn.getTags() != null) {
                    if (postIn.getTags().length() > 200) {
                        createCell(cellStyle, wb, troww, 0, CellStyle.ALIGN_LEFT, CellStyle.VERTICAL_CENTER, postIn.getTags().substring(0, 200));

                    } else {
                        createCell(cellStyle, wb, troww, 0, CellStyle.ALIGN_LEFT, CellStyle.VERTICAL_CENTER, postIn.getTags());
                    }
                } else {
                    createCell(cellStyle, wb, troww, 0, CellStyle.ALIGN_LEFT, CellStyle.VERTICAL_CENTER, "---");

                }
                // createCell(cellStyle, wb, troww, 1, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, postIn instanceof MessageIn ? paramRequest.getLocaleString("message") : postIn instanceof PhotoIn ? paramRequest.getLocaleString("photo") : postIn instanceof VideoIn ? paramRequest.getLocaleString("video") : "---");
                createCell(cellStyle, wb, troww, 1, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, postIn instanceof MessageIn ? "Mensaje" : postIn instanceof PhotoIn ? "Foto" : postIn instanceof VideoIn ? "Video" : "---");
                createCell(cellStyle, wb, troww, 2, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, postIn.getPostInSocialNetwork().getDisplayTitle(lang));


                if (postIn.getSocialTopic() != null) {
                    createCell(cellStyle, wb, troww, 3, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, postIn.getSocialTopic().getDisplayTitle(lang));
                } else {
                    createCell(cellStyle, wb, troww, 3, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, "---");
                }
                createCell(cellStyle, wb, troww, 4, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, SWBUtils.TEXT.getTimeAgo(postIn.getPi_created(), lang));

                String path = "";

                if (postIn.getPostSentimentalType() == 0) {
                    createCell(cellStyle, wb, troww, 5, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, "----");
                } else if (postIn.getPostSentimentalType() == 1) {
                    createCell(cellStyle, wb, troww, 5, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, "Positivo");
                } else if (postIn.getPostSentimentalType() == 2) {
                    createCell(cellStyle, wb, troww, 5, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, "Negativo");
                }
                createCell(cellStyle, wb, troww, 6, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, postIn.getPostIntesityType() == 0 ? SWBSocialResUtil.Util.getStringFromGenericLocale("low", lang) : postIn.getPostIntesityType() == 1 ? SWBSocialResUtil.Util.getStringFromGenericLocale("low", lang) : postIn.getPostIntesityType() == 2 ? SWBSocialResUtil.Util.getStringFromGenericLocale("low", lang) : "---");

                if (postIn.getPostSentimentalEmoticonType() == 1) {
                    createCell(cellStyle, wb, troww, 7, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, "Positivo");

                } else if (postIn.getPostSentimentalEmoticonType() == 2) {
                    createCell(cellStyle, wb, troww, 7, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, "Negativo");
                } else if (postIn.getPostSentimentalEmoticonType() == 0) {

                    createCell(cellStyle, wb, troww, 7, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, "---");
                }
                int postS = postIn.getPostShared();
                String postShared = Integer.toString(postS);
                createCell(cellStyle, wb, troww, 8, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, postShared);
                createCell(cellStyle, wb, troww, 9, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, postIn.getPostInSocialNetworkUser() != null ? postIn.getPostInSocialNetworkUser().getSnu_name() : SWBSocialResUtil.Util.getStringFromGenericLocale("withoutUser", lang));
                Serializable foll = postIn.getPostInSocialNetworkUser() != null ? postIn.getPostInSocialNetworkUser().getFollowers() : SWBSocialResUtil.Util.getStringFromGenericLocale("withoutUser", lang);
                createCell(cellStyle, wb, troww, 10, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, foll.toString());
                Serializable amigos = postIn.getPostInSocialNetworkUser() != null ? postIn.getPostInSocialNetworkUser().getFriends() : SWBSocialResUtil.Util.getStringFromGenericLocale("withoutUser", lang);
                createCell(cellStyle, wb, troww, 11, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, amigos.toString());

                Serializable klout = postIn.getPostInSocialNetworkUser() != null ? postIn.getPostInSocialNetworkUser().getSnu_klout() : SWBSocialResUtil.Util.getStringFromGenericLocale("withoutUser", lang);

                //createCell(cellStyle, wb, troww, 12, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, klout.toString());
                createCell(cellStyle, wb, troww, 12, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, postIn.getPostPlace() == null ? "---" : postIn.getPostPlace());
                createCell(cellStyle, wb, troww, 13, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, postIn.isIsPrioritary() ? "SI" : "NO");

                i++;

            }



            // Definimos el tamaño de las celdas, podemos definir un tamaña especifico o hacer que 
            //la celda se acomode según su tamaño
            Sheet ssheet = wb.getSheetAt(0);

            //ssheet.setColumnWidth(0, 256 * 40);
            ssheet.autoSizeColumn(0);
            ssheet.autoSizeColumn(1);
            ssheet.autoSizeColumn(2);
            ssheet.autoSizeColumn(3);
            ssheet.autoSizeColumn(4);
            ssheet.autoSizeColumn(5);
            ssheet.autoSizeColumn(6);
            ssheet.autoSizeColumn(7);
            ssheet.autoSizeColumn(8);
            ssheet.autoSizeColumn(9);
            ssheet.autoSizeColumn(10);
            ssheet.autoSizeColumn(11);
            ssheet.autoSizeColumn(12);
            ssheet.autoSizeColumn(13);
            //ssheet.autoSizeColumn(14);
            //  }

            OutputStream ou = response.getOutputStream();
            response.setHeader("Cache-Control", "no-cache");
            response.setHeader("Pragma", "no-cache");
            response.setHeader("Content-Disposition", "attachment; filename=\"Mensajes.xls\";");
            response.setContentType("application/octet-stream");
            wb.write(ou);
            ou.close();

        } catch (Exception e) {
            log.error(e);
        }
    }

    public static void createTituloCell(HSSFWorkbook wb, Row row, int column, short halign, short valign, String strContenido) {


        CreationHelper ch = wb.getCreationHelper();
        Cell cell = row.createCell(column);
        cell.setCellValue(ch.createRichTextString(strContenido));

        HSSFFont cellFont = wb.createFont();
        cellFont.setFontHeightInPoints((short) 11);
        cellFont.setFontName(HSSFFont.FONT_ARIAL);
        cellFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

        CellStyle cellStyle = wb.createCellStyle();
        cellStyle.setAlignment(halign);
        cellStyle.setVerticalAlignment(valign);
        cellStyle.setFont(cellFont);
        cellStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
        cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        cell.setCellStyle(cellStyle);

    }

    public static void createHead(HSSFWorkbook wb, Row row, int column, short halign, short valign, String strContenido) {


        CreationHelper ch = wb.getCreationHelper();
        Cell cell = row.createCell(column);
        cell.setCellValue(ch.createRichTextString(strContenido));

        HSSFFont cellFont = wb.createFont();
        cellFont.setFontHeightInPoints((short) 11);
        cellFont.setFontName(HSSFFont.FONT_ARIAL);
        cellFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

        CellStyle cellStyle = wb.createCellStyle();
        cellStyle.setAlignment(halign);
        cellStyle.setVerticalAlignment(valign);
        cellStyle.setFont(cellFont);
        cellStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
        cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        cellStyle.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
        cellStyle.setBottomBorderColor((short) 8);
        cellStyle.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
        cellStyle.setLeftBorderColor((short) 8);
        cellStyle.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
        cellStyle.setRightBorderColor((short) 8);
        cellStyle.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
        cellStyle.setTopBorderColor((short) 8);

        cellStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
        cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        cell.setCellStyle(cellStyle);

    }

    public static void createCell(CellStyle cellStyle, Workbook wb, Row row, int column, short halign, short valign, String strContenido) {


        CreationHelper ch = wb.getCreationHelper();
        Cell cell = row.createCell(column);

        cell.setCellValue(ch.createRichTextString(strContenido));
        cellStyle.setAlignment(halign);
        cellStyle.setVerticalAlignment(valign);
        cellStyle.setBorderBottom(HSSFCellStyle.BORDER_DOTTED);
        cellStyle.setBottomBorderColor((short) 8);
        cellStyle.setBorderLeft(HSSFCellStyle.BORDER_DOTTED);
        cellStyle.setLeftBorderColor((short) 8);
        cellStyle.setBorderRight(HSSFCellStyle.BORDER_DOTTED);
        cellStyle.setRightBorderColor((short) 8);
        cellStyle.setBorderTop(HSSFCellStyle.BORDER_DOTTED);
        cellStyle.setTopBorderColor((short) 8);


        cell.setCellStyle(cellStyle);

    }

    private String getRequest(String url) throws IOException {

        //CharSequence paramString = (null == params) ? "" : delimit(params.entrySet(), "&", "=", true);
        URL serverUrl = new URL(url);

        HttpURLConnection conex = null;
        InputStream in = null;
        String response = null;

        try {
            conex = (HttpURLConnection) serverUrl.openConnection();

            conex.setConnectTimeout(30000);
            conex.setReadTimeout(60000);
            conex.setRequestMethod("GET");
            conex.setDoOutput(true);
            conex.connect();
            in = conex.getInputStream();
            response = getResponse(in);

        } catch (java.io.IOException ioe) {
            response = getResponse(conex.getErrorStream());
            ioe.printStackTrace();
        } finally {
            close(in);
            if (conex != null) {
                conex.disconnect();
            }
        }
        if (response == null) {
            response = "";
        }
        return response;
    }

    private static String getResponse(InputStream data) throws IOException {

        Reader in = new BufferedReader(new InputStreamReader(data, "UTF-8"));
        StringBuilder response = new StringBuilder(256);
        char[] buffer = new char[1000];
        int charsRead = 0;
        while (charsRead >= 0) {
            response.append(buffer, 0, charsRead);
            charsRead = in.read(buffer);
        }
        in.close();
        return response.toString();
    }

    private void close(Closeable c) {
        if (c != null) {
            try {
                c.close();
            } catch (IOException ex) {
                log.error("Error at closing object: " + c.getClass().getName(),
                        ex);
            }
        }
    }

    private Iterator getListGender(String suri, String lang, String filter, String filterGeneral) {

        SemanticObject semObj = SemanticObject.getSemanticObject(suri);
        ArrayList listTotal = new ArrayList();
        ArrayList listMale = new ArrayList();
        ArrayList listFemale = new ArrayList();
        ArrayList listOther = new ArrayList();

        //String lang = paramRequest.getUser().getLanguage();


        int totalPost = 0;
        ArrayList genderMale = new ArrayList();
        ArrayList genderFemale = new ArrayList();
        ArrayList genderother = new ArrayList();
        Iterator<PostIn> itObjPostIns = null;
        ArrayList all = new ArrayList();
        ArrayList allMale = new ArrayList();
        ArrayList allFemale = new ArrayList();
        ArrayList allNodefine = new ArrayList();
        ArrayList male = new ArrayList();
        ArrayList malePositives = new ArrayList();
        ArrayList maleNegatives = new ArrayList();
        ArrayList maleNeutrals = new ArrayList();
        ArrayList female = new ArrayList();
        ArrayList femalePositives = new ArrayList();
        ArrayList femaleNegatives = new ArrayList();
        ArrayList femaleNeutrals = new ArrayList();
        ArrayList nodefine = new ArrayList();
        ArrayList nodefinePositives = new ArrayList();
        ArrayList nodefineNegatives = new ArrayList();
        ArrayList nodefineNeutrals = new ArrayList();

        if (semObj.getGenericInstance() instanceof Stream) {
            Stream stream = (Stream) semObj.getGenericInstance();
            itObjPostIns = stream.listPostInStreamInvs();
        } else if (semObj.getGenericInstance() instanceof SocialTopic) {
            SocialTopic socialTopic = (SocialTopic) semObj.getGenericInstance();
            itObjPostIns = PostIn.ClassMgr.listPostInBySocialTopic(socialTopic, socialTopic.getSocialSite());
        }


        while (itObjPostIns.hasNext()) {
            PostIn postIn = itObjPostIns.next();

            if (filterGeneral.equals("all")) {
                //System.out.println("entro en filte r general all");
                if (postIn.getPostInSocialNetworkUser().getSnu_gender() == SocialNetworkUser.USER_GENDER_MALE || postIn.getPostInSocialNetworkUser().getSnu_gender() == SocialNetworkUser.USER_GENDER_FEMALE || postIn.getPostInSocialNetworkUser().getSnu_gender() == SocialNetworkUser.USER_GENDER_UNDEFINED || postIn.getPostInSocialNetworkUser().getSnu_gender() == 0) {
                    all.add(postIn);
                }

            }

            if (filterGeneral.equals("male")) {
                //System.out.println("entro en filter general male");
                if (postIn.getPostInSocialNetworkUser().getSnu_gender() == SocialNetworkUser.USER_GENDER_MALE) {
                    male.add(postIn);
                }


            }

            if (filterGeneral.equals("female")) {
                //System.out.println("entro en filter general female");
                if (postIn.getPostInSocialNetworkUser().getSnu_gender() == SocialNetworkUser.USER_GENDER_FEMALE) {
                    female.add(postIn);
                }
            }

            if (filterGeneral.equals("nodefine")) {
                //System.out.println("entro en no define filter genral");
                if (postIn.getPostInSocialNetworkUser().getSnu_gender() == SocialNetworkUser.USER_GENDER_UNDEFINED || postIn.getPostInSocialNetworkUser().getSnu_gender() == 0) {
                    nodefine.add(postIn);
                }
            }
        }

        if (filterGeneral.equals("all")) {
            Iterator allI = all.iterator();

            while (allI.hasNext()) {
                PostIn postInAll = (PostIn) allI.next();

                if (filter.equals("Masculino") && postInAll.getPostInSocialNetworkUser().getSnu_gender() == SocialNetworkUser.USER_GENDER_MALE) {
                    // System.out.println("agrego a all male");
                    allMale.add(postInAll);

                } else if (filter.equals("Femenino") && postInAll.getPostInSocialNetworkUser().getSnu_gender() == SocialNetworkUser.USER_GENDER_FEMALE) {
                    //System.out.println("agrego a allfemale");
                    allFemale.add(postInAll);

                } else if (filter.equals("No definido") && postInAll.getPostInSocialNetworkUser().getSnu_gender() == SocialNetworkUser.USER_GENDER_UNDEFINED || postInAll.getPostInSocialNetworkUser().getSnu_gender() == 0) {
                    // System.out.println("agregar a all nodefine");
                    allNodefine.add(postInAll);
                }
            }
        } else if (filterGeneral.equals("male")) {


            Iterator maleI = male.iterator();

            while (maleI.hasNext()) {
                PostIn postInMale = (PostIn) maleI.next();

                if (filter.equals("Positivos") && postInMale.getPostSentimentalType() == 1) {
                    malePositives.add(postInMale);

                } else if (filter.equals("Negativos") && postInMale.getPostSentimentalType() == 2) {
                    maleNegatives.add(postInMale);

                } else if (filter.equals("Neutros") && postInMale.getPostSentimentalType() == 0) {
                    maleNeutrals.add(postInMale);
                }
            }
        } else if (filterGeneral.equals("female")) {
            Iterator femaleI = female.iterator();

            while (femaleI.hasNext()) {
                PostIn postInFemale = (PostIn) femaleI.next();

                if (filter.equals("Positivos") && postInFemale.getPostSentimentalType() == 1) {
                    //System.out.println("entro en positivos femmenino y 1");
                    femalePositives.add(postInFemale);

                } else if (filter.equals("Negativos") && postInFemale.getPostSentimentalType() == 2) {
                    // System.out.println("entro en positivos femmenino y 2");
                    femaleNegatives.add(postInFemale);

                } else if (filter.equals("Neutros") && postInFemale.getPostSentimentalType() == 0) {
                    //System.out.println("entro en positivos femmenino y 0");
                    femaleNeutrals.add(postInFemale);
                }
            }
        } else if (filterGeneral.equals("nodefine")) {


            Iterator nodefineI = nodefine.iterator();

            while (nodefineI.hasNext()) {
                PostIn postInnoDefine = (PostIn) nodefineI.next();

                if (filter.equals("Positivos") && postInnoDefine.getPostSentimentalType() == 1) {
                    nodefinePositives.add(postInnoDefine);

                } else if (filter.equals("Negativos") && postInnoDefine.getPostSentimentalType() == 2) {
                    nodefineNegatives.add(postInnoDefine);

                } else if (filter.equals("Neutros") && postInnoDefine.getPostSentimentalType() == 0) {
                    nodefineNeutrals.add(postInnoDefine);
                }
            }

        }


        Iterator i = null;
        if (filterGeneral.equals("all")) {
            i = all.iterator();
            if (filter.equals("Masculino")) {
                //System.out.println("entro e i male");
                i = allMale.iterator();
            } else if (filter.equals("Femenino")) {
                //  System.out.println(" entro en i female");
                i = allFemale.iterator();
            } else if (filter.equals("No definido")) {
                // System.out.println("entro en i no define");
                i = allNodefine.iterator();
            }

        } else if (filterGeneral.equals("male")) {
            i = male.iterator();
            if (filter.equals("Positivos")) {
                // System.out.println("x");
                i = malePositives.iterator();
            } else if (filter.equals("Negativos")) {
                // System.out.println("y");
                i = maleNegatives.iterator();
            } else if (filter.equals("Neutros")) {
                //  System.out.println("z");
                i = maleNeutrals.iterator();
            }
        } else if (filterGeneral.equals("female")) {
            i = female.iterator();

            if (filter.equals("Positivos")) {
                //System.out.println("xf");
                i = femalePositives.iterator();
            } else if (filter.equals("Negativos")) {
                //System.out.println("yf");
                i = femaleNegatives.iterator();
            } else if (filter.equals("Neutros")) {
                //   System.out.println("zf");
                i = femaleNeutrals.iterator();
            }
        } else if (filterGeneral.equals("nodefine")) {
            i = nodefine.iterator();
            if (filter.equals("Positivos")) {
                //System.out.println("xn");
                i = nodefinePositives.iterator();
            } else if (filter.equals("Negativos")) {
                // System.out.println("yn");
                i = nodefineNegatives.iterator();
            } else if (filter.equals("Neutros")) {
                //System.out.println("zn");
                i = nodefineNeutrals.iterator();
            }
        }

        return i;
    }

    private Iterator getListEducation(String suri, String lang, String filter, String filterGeneral) {
        ArrayList listTotal = new ArrayList();
        SemanticObject semObj = SemanticObject.getSemanticObject(suri);
        //  System.out.println("entro en get listeducation");
        //   System.out.println("filter : " + filter);
        //   System.out.println("filter General : " + filterGeneral);

        ArrayList all = new ArrayList();
        ArrayList highSchool = new ArrayList();
        ArrayList college = new ArrayList();
        ArrayList graduate = new ArrayList();
        ArrayList undefined = new ArrayList();
        ArrayList allSecundaria = new ArrayList();
        ArrayList allMedioSuperior = new ArrayList();
        ArrayList allGraduado = new ArrayList();
        ArrayList allUdefined = new ArrayList();
        ArrayList positives = new ArrayList();
        ArrayList negatives = new ArrayList();
        ArrayList neutrals = new ArrayList();



        // int highSchool = 0, college = 0, graduate = 0, undefined = 0, totalPost = 0;
        Iterator<PostIn> itObjPostIns = null;
        if (semObj.getGenericInstance() instanceof Stream) {
            Stream stream = (Stream) semObj.getGenericInstance();
            itObjPostIns = stream.listPostInStreamInvs();
        } else if (semObj.getGenericInstance() instanceof SocialTopic) {
            SocialTopic socialTopic = (SocialTopic) semObj.getGenericInstance();
            itObjPostIns = PostIn.ClassMgr.listPostInBySocialTopic(socialTopic, socialTopic.getSocialSite());
        }


        while (itObjPostIns.hasNext()) {
            PostIn postIn = itObjPostIns.next();
            //totalPost++;

            if (filterGeneral.equals("all")) {
                if (postIn.getPostInSocialNetworkUser().getSnu_education() == SocialNetworkUser.USER_EDUCATION_HIGHSCHOOL
                        || postIn.getPostInSocialNetworkUser().getSnu_education() == SocialNetworkUser.USER_EDUCATION_COLLEGE
                        || postIn.getPostInSocialNetworkUser().getSnu_education() == SocialNetworkUser.USER_EDUCATION_GRADUATE
                        || postIn.getPostInSocialNetworkUser().getSnu_education() == SocialNetworkUser.USER_EDUCATION_UNDEFINED || postIn.getPostInSocialNetworkUser().getSnu_education() == 0) {
                    all.add(postIn);
                }

            } else if (filterGeneral.equals("secundaria")) {
                if (postIn.getPostInSocialNetworkUser().getSnu_education() == SocialNetworkUser.USER_EDUCATION_HIGHSCHOOL) {
                    highSchool.add(postIn);
                }

            } else if (filterGeneral.equals("mediosuperior")) {
                if (postIn.getPostInSocialNetworkUser().getSnu_education() == SocialNetworkUser.USER_EDUCATION_COLLEGE) {
                    college.add(postIn);
                }
            } else if (filterGeneral.equals("graduado")) {
                if (postIn.getPostInSocialNetworkUser().getSnu_education() == SocialNetworkUser.USER_EDUCATION_GRADUATE) {
                    graduate.add(postIn);
                }
            } else if (filterGeneral.equals("undefined")) {
                if (postIn.getPostInSocialNetworkUser().getSnu_education() == SocialNetworkUser.USER_EDUCATION_UNDEFINED || postIn.getPostInSocialNetworkUser().getSnu_education() == 0) {
                    undefined.add(postIn);
                }
            }
        }

        if (filterGeneral.equals("all")) {


            Iterator allI = all.iterator();

            while (allI.hasNext()) {
                PostIn postIn = (PostIn) allI.next();

                if (filter.equals(SWBSocialResUtil.Util.getStringFromGenericLocale("highSchool", lang)) && postIn.getPostInSocialNetworkUser().getSnu_education() == SocialNetworkUser.USER_EDUCATION_HIGHSCHOOL) {
                    //  System.out.println("agrego a all secundaria");
                    allSecundaria.add(postIn);

                } else if (filter.equals(SWBSocialResUtil.Util.getStringFromGenericLocale("college", lang)) && postIn.getPostInSocialNetworkUser().getSnu_education() == SocialNetworkUser.USER_EDUCATION_COLLEGE) {
                    // System.out.println("agrego a mediosuperioe");
                    allMedioSuperior.add(postIn);

                } else if (filter.equals(SWBSocialResUtil.Util.getStringFromGenericLocale("graduate", lang)) && postIn.getPostInSocialNetworkUser().getSnu_education() == SocialNetworkUser.USER_EDUCATION_GRADUATE) {
                    //  System.out.println("agregar a all graduado");
                    allGraduado.add(postIn);
                } else if (filter.equals(SWBSocialResUtil.Util.getStringFromGenericLocale("undefinedEducation", lang)) && postIn.getPostInSocialNetworkUser().getSnu_education() == SocialNetworkUser.USER_EDUCATION_UNDEFINED || postIn.getPostInSocialNetworkUser().getSnu_education() == 0) {
                    //   System.out.println("agregar a all undefine");
                    allUdefined.add(postIn);
                }
            }
        } else if (filterGeneral.equals("secundaria")) {

            Iterator allI = highSchool.iterator();
            while (allI.hasNext()) {
                PostIn postIn = (PostIn) allI.next();

                if (filter.equals("Positivos") && postIn.getPostSentimentalType() == 1) {
                    //       System.out.println("agrego a high positivos");
                    positives.add(postIn);

                } else if (filter.equals("Negativos") && postIn.getPostSentimentalType() == 2) {
                    //     System.out.println("agrego a high negativos");
                    negatives.add(postIn);

                } else if (filter.equals("Neutros") && postIn.getPostSentimentalType() == 0) {
                    System.out.println("agregar a high neutros");
                    neutrals.add(postIn);
                }

            }


        } else if (filterGeneral.equals("mediosuperior")) {


            Iterator allI = college.iterator();

            while (allI.hasNext()) {
                PostIn postIn = (PostIn) allI.next();

                if (filter.equals("Positivos") && postIn.getPostSentimentalType() == 1) {
                    //    System.out.println("agrego a mediosuperior positivos");
                    positives.add(postIn);

                } else if (filter.equals("Negativos") && postIn.getPostSentimentalType() == 2) {
                    //     System.out.println("agrego a mediosuperior negativos");
                    negatives.add(postIn);

                } else if (filter.equals("Neutros") && postIn.getPostSentimentalType() == 0) {
                    //   System.out.println("agregar a mediosuperior neutros");
                    neutrals.add(postIn);
                }

            }


        } else if (filterGeneral.equals("graduado")) {


            Iterator allI = college.iterator();

            while (allI.hasNext()) {
                PostIn postIn = (PostIn) allI.next();

                if (filter.equals("Positivos") && postIn.getPostSentimentalType() == 1) {
                    //   System.out.println("agrego a mediosuperior positivos");
                    positives.add(postIn);

                } else if (filter.equals("Negativos") && postIn.getPostSentimentalType() == 2) {
                    // System.out.println("agrego a mediosuperior negativos");
                    negatives.add(postIn);

                } else if (filter.equals("Neutros") && postIn.getPostSentimentalType() == 0) {
                    //System.out.println("agregar a mediosuperior neutros");
                    neutrals.add(postIn);
                }

            }
        } else if (filterGeneral.equals("undefined")) {


            Iterator allI = undefined.iterator();

            while (allI.hasNext()) {
                PostIn postIn = (PostIn) allI.next();

                if (filter.equals("Positivos") && postIn.getPostSentimentalType() == 1) {
                    //    System.out.println("agrego a mediosuperior positivos");
                    positives.add(postIn);

                } else if (filter.equals("Negativos") && postIn.getPostSentimentalType() == 2) {
                    //  System.out.println("agrego a mediosuperior negativos");
                    negatives.add(postIn);

                } else if (filter.equals("Neutros") && postIn.getPostSentimentalType() == 0) {
                    //  System.out.println("agregar a mediosuperior neutros");
                    neutrals.add(postIn);
                }

            }
        }


        Iterator i = null;
        if (filterGeneral.equals("all")) {
            i = all.iterator();
            if (filter.equals(SWBSocialResUtil.Util.getStringFromGenericLocale("highSchool", lang))) {
                //  System.out.println("entro e i secundaria");
                i = allSecundaria.iterator();
            } else if (filter.equals(SWBSocialResUtil.Util.getStringFromGenericLocale("college", lang))) {
                //   System.out.println(" entro en i mediosuperior");
                i = allMedioSuperior.iterator();
            } else if (filter.equals(SWBSocialResUtil.Util.getStringFromGenericLocale("graduate", lang))) {
                //   System.out.println("entro en i no define");
                i = allGraduado.iterator();
            } else if (filter.equals(SWBSocialResUtil.Util.getStringFromGenericLocale("undefinedEducation", lang))) {
                //  System.out.println("entro en i no define");
                i = allUdefined.iterator();
            }
        } else if (filterGeneral.equals("secundaria")) {
            i = highSchool.iterator();
            if (filter.equals("Positivos")) {
                //   System.out.println("x");
                i = positives.iterator();
            } else if (filter.equals("Negativos")) {
                //  System.out.println("y");
                i = negatives.iterator();
            } else if (filter.equals("Neutros")) {
                //  System.out.println("z");
                i = neutrals.iterator();
            }
        } else if (filterGeneral.equals("mediosuperior")) {
            i = college.iterator();
            if (filter.equals("Positivos")) {
                //   System.out.println("xf");
                i = positives.iterator();
            } else if (filter.equals("Negativos")) {
                //   System.out.println("yf");
                i = negatives.iterator();
            } else if (filter.equals("Neutros")) {
                //    System.out.println("zf");
                i = neutrals.iterator();
            }
        } else if (filterGeneral.equals("graduado")) {
            i = graduate.iterator();
            if (filter.equals("Positivos")) {
                //  System.out.println("xf");
                i = positives.iterator();
            } else if (filter.equals("Negativos")) {
                //    System.out.println("xf");
                i = negatives.iterator();
            } else if (filter.equals("Neutros")) {
                //   System.out.println("xf");
                i = neutrals.iterator();
            }
        } else if (filterGeneral.equals("undefined")) {
            i = undefined.iterator();
            if (filter.equals("Positivos")) {
                //  System.out.println("zf");
                i = positives.iterator();
            } else if (filter.equals("Negativos")) {
                //  System.out.println("zf");
                i = negatives.iterator();
            } else if (filter.equals("Neutros")) {
                //  System.out.println("zf");
                i = neutrals.iterator();
            }
        }

        return i;
    }

    private Iterator getRelationShip(String suri, String lang, String filter, String filterGeneral) {
        //   System.out.println("filter " + filter);
        // System.out.println("filter genneral " + filterGeneral);

        int single = 0, married = 0, divorced = 0, widowed = 0, undefined = 0;
        SemanticObject semObj = SemanticObject.getSemanticObject(suri);

        ArrayList all = new ArrayList();
        ArrayList allSingle = new ArrayList();
        ArrayList allMarried = new ArrayList();
        ArrayList allWidowed = new ArrayList();
        ArrayList allDivorced = new ArrayList();
        ArrayList allUndefined = new ArrayList();
        ArrayList singleArray = new ArrayList();
        ArrayList marriedArray = new ArrayList();
        ArrayList divorcedArray = new ArrayList();
        ArrayList widowedArray = new ArrayList();
        ArrayList undefinedArray = new ArrayList();

        ArrayList positives = new ArrayList();
        ArrayList negatives = new ArrayList();
        ArrayList neutrals = new ArrayList();

        Iterator<PostIn> itObjPostIns = null;
        if (semObj.getGenericInstance() instanceof Stream) {
            Stream stream = (Stream) semObj.getGenericInstance();
            itObjPostIns = stream.listPostInStreamInvs();
        } else if (semObj.getGenericInstance() instanceof SocialTopic) {
            SocialTopic socialTopic = (SocialTopic) semObj.getGenericInstance();
            itObjPostIns = PostIn.ClassMgr.listPostInBySocialTopic(socialTopic, socialTopic.getSocialSite());
        }

        while (itObjPostIns.hasNext()) {
            PostIn postIn = itObjPostIns.next();

            if (filterGeneral.equals("all")) {
                if (postIn.getPostInSocialNetworkUser().getSnu_relationShipStatus() == SocialNetworkUser.USER_RELATION_SINGLE
                        || postIn.getPostInSocialNetworkUser().getSnu_relationShipStatus() == SocialNetworkUser.USER_RELATION_MARRIED
                        || postIn.getPostInSocialNetworkUser().getSnu_relationShipStatus() == SocialNetworkUser.USER_RELATION_DIVORCED
                        || postIn.getPostInSocialNetworkUser().getSnu_relationShipStatus() == SocialNetworkUser.USER_RELATION_WIDOWED
                        || postIn.getPostInSocialNetworkUser().getSnu_relationShipStatus() == SocialNetworkUser.USER_RELATION_UNDEFINED || postIn.getPostInSocialNetworkUser().getSnu_relationShipStatus() == 0) {
                    //  System.out.println("agregar todos");
                    all.add(postIn);
                }
            } else if (filterGeneral.equals("single")) {
                if (postIn.getPostInSocialNetworkUser().getSnu_relationShipStatus() == SocialNetworkUser.USER_RELATION_SINGLE) {
                    //  System.out.println("agregar single");
                    singleArray.add(postIn);
                }
            } else if (filterGeneral.equals("married")) {
                if (postIn.getPostInSocialNetworkUser().getSnu_relationShipStatus() == SocialNetworkUser.USER_RELATION_MARRIED) {
                    //   System.out.println("agregar a married");
                    marriedArray.add(postIn);
                }
            } else if (filterGeneral.equals("widowed")) {
                if (postIn.getPostInSocialNetworkUser().getSnu_relationShipStatus() == SocialNetworkUser.USER_RELATION_WIDOWED) {
                    // System.out.println("agregar widowed");
                    widowedArray.add(postIn);
                }
            } else if (filterGeneral.equals("divorced")) {
                if (postIn.getPostInSocialNetworkUser().getSnu_relationShipStatus() == SocialNetworkUser.USER_RELATION_DIVORCED) {
                    //  System.out.println("agregar divorced");
                    divorcedArray.add(postIn);
                }
            } else if (filterGeneral.equals("undefined")) {
                if (postIn.getPostInSocialNetworkUser().getSnu_relationShipStatus() == SocialNetworkUser.USER_RELATION_UNDEFINED || postIn.getPostInSocialNetworkUser().getSnu_relationShipStatus() == 0) {
                    //   System.out.println("agregar undefined");
                    undefinedArray.add(postIn);
                }
            }

        }



        if (filterGeneral.equals("all")) {
            Iterator allI = all.iterator();
            while (allI.hasNext()) {
                PostIn postIn = (PostIn) allI.next();
                //  System.out.println("SWBSocialResUtil.Util.getStringFromGenericLocale(\"undefinedRelation\", lang)" + SWBSocialResUtil.Util.getStringFromGenericLocale("undefinedRelation", lang));
                if (filter.equals(SWBSocialResUtil.Util.getStringFromGenericLocale("single", lang)) && postIn.getPostInSocialNetworkUser().getSnu_relationShipStatus() == SocialNetworkUser.USER_RELATION_SINGLE) {
                    //    System.out.println("agrego a all single");
                    allSingle.add(postIn);

                } else if (filter.equals(SWBSocialResUtil.Util.getStringFromGenericLocale("married", lang)) && postIn.getPostInSocialNetworkUser().getSnu_relationShipStatus() == SocialNetworkUser.USER_RELATION_MARRIED) {
                    //     System.out.println("agrego a married");
                    allMarried.add(postIn);

                } else if (filter.equals(SWBSocialResUtil.Util.getStringFromGenericLocale("widowed", lang)) && postIn.getPostInSocialNetworkUser().getSnu_relationShipStatus() == SocialNetworkUser.USER_RELATION_WIDOWED) {
                    //    System.out.println("agregar a all widowed");
                    allWidowed.add(postIn);
                } else if (filter.equals(SWBSocialResUtil.Util.getStringFromGenericLocale("divorced", lang)) && postIn.getPostInSocialNetworkUser().getSnu_relationShipStatus() == SocialNetworkUser.USER_RELATION_DIVORCED) {
                    //   System.out.println("agregar a all divorced");
                    allDivorced.add(postIn);
                } else if (filter.equals(SWBSocialResUtil.Util.getStringFromGenericLocale("undefinedRelation", lang)) && postIn.getPostInSocialNetworkUser().getSnu_relationShipStatus() == SocialNetworkUser.USER_RELATION_UNDEFINED || postIn.getPostInSocialNetworkUser().getSnu_relationShipStatus() == 0) {
                    // System.out.println("agregar a all undefined");
                    allUndefined.add(postIn);
                }
            }

        } else if (filterGeneral.equals("single")) {

            Iterator i = singleArray.iterator();

            while (i.hasNext()) {
                PostIn postIn = (PostIn) i.next();

                if (filter.equals("Positivos") && postIn.getPostSentimentalType() == 1) {
                    positives.add(postIn);

                } else if (filter.equals("Negativos") && postIn.getPostSentimentalType() == 2) {
                    negatives.add(postIn);

                } else if (filter.equals("Neutros") && postIn.getPostSentimentalType() == 0) {
                    neutrals.add(postIn);
                }
            }

        } else if (filterGeneral.equals("married")) {

            Iterator i = marriedArray.iterator();

            while (i.hasNext()) {
                PostIn postIn = (PostIn) i.next();

                if (filter.equals("Positivos") && postIn.getPostSentimentalType() == 1) {
                    positives.add(postIn);

                } else if (filter.equals("Negativos") && postIn.getPostSentimentalType() == 2) {
                    negatives.add(postIn);

                } else if (filter.equals("Neutros") && postIn.getPostSentimentalType() == 0) {
                    neutrals.add(postIn);
                }
            }

        } else if (filterGeneral.equals("widowed")) {

            Iterator i = widowedArray.iterator();

            while (i.hasNext()) {
                PostIn postIn = (PostIn) i.next();

                if (filter.equals("Positivos") && postIn.getPostSentimentalType() == 1) {
                    positives.add(postIn);

                } else if (filter.equals("Negativos") && postIn.getPostSentimentalType() == 2) {
                    negatives.add(postIn);

                } else if (filter.equals("Neutros") && postIn.getPostSentimentalType() == 0) {
                    neutrals.add(postIn);
                }
            }

        } else if (filterGeneral.equals("divorced")) {

            Iterator i = divorcedArray.iterator();

            while (i.hasNext()) {
                PostIn postIn = (PostIn) i.next();

                if (filter.equals("Positivos") && postIn.getPostSentimentalType() == 1) {
                    positives.add(postIn);

                } else if (filter.equals("Negativos") && postIn.getPostSentimentalType() == 2) {
                    negatives.add(postIn);

                } else if (filter.equals("Neutros") && postIn.getPostSentimentalType() == 0) {
                    neutrals.add(postIn);
                }
            }

        } else if (filterGeneral.equals("undefined")) {

            Iterator i = undefinedArray.iterator();

            while (i.hasNext()) {
                PostIn postIn = (PostIn) i.next();

                if (filter.equals("Positivos") && postIn.getPostSentimentalType() == 1) {
                    positives.add(postIn);

                } else if (filter.equals("Negativos") && postIn.getPostSentimentalType() == 2) {
                    negatives.add(postIn);

                } else if (filter.equals("Neutros") && postIn.getPostSentimentalType() == 0) {
                    neutrals.add(postIn);
                }
            }

        }

        Iterator i = null;
        if (filterGeneral.equals("all")) {
            i = all.iterator();

            if (filter.equals(SWBSocialResUtil.Util.getStringFromGenericLocale("single", lang))) {
                //      System.out.println(" i single");
                i = allSingle.iterator();

            } else if (filter.equals(SWBSocialResUtil.Util.getStringFromGenericLocale("married", lang))) {
                //     System.out.println(" i married");
                i = allMarried.iterator();

            } else if (filter.equals(SWBSocialResUtil.Util.getStringFromGenericLocale("widowed", lang))) {
                //   System.out.println("i viudo");

                i = allWidowed.iterator();

            } else if (filter.equals(SWBSocialResUtil.Util.getStringFromGenericLocale("divorced", lang))) {
                //    System.out.println("i divorced");
                i = allDivorced.iterator();

            } else if (filter.equals(SWBSocialResUtil.Util.getStringFromGenericLocale("undefinedRelation", lang))) {
                //  System.out.println(" i undefined");
                i = allUndefined.iterator();

            }


        } else if (filterGeneral.equals("single")) {
            i = singleArray.iterator();

            if (filter.equals("Positivos")) {
                //  System.out.println("xd");
                i = positives.iterator();
            } else if (filter.equals("Negativos")) {
                //  System.out.println("yd");
                i = negatives.iterator();
            } else if (filter.equals("Neutros")) {
                //   System.out.println("zd");
                i = neutrals.iterator();
            }

        } else if (filterGeneral.equals("married")) {
            i = marriedArray.iterator();
            if (filter.equals("Positivos")) {
                //     System.out.println("x");
                i = positives.iterator();
            } else if (filter.equals("Negativos")) {
                //   System.out.println("y");
                i = negatives.iterator();
            } else if (filter.equals("Neutros")) {
                //   System.out.println("z");
                i = neutrals.iterator();
            }
        } else if (filterGeneral.equals("divorced")) {
            i = divorcedArray.iterator();
            if (filter.equals("Positivos")) {
                // System.out.println("xd");
                i = positives.iterator();
            } else if (filter.equals("Negativos")) {
                //  System.out.println("yd");
                i = negatives.iterator();
            } else if (filter.equals("Neutros")) {
                //System.out.println("zd");
                i = neutrals.iterator();
            }

        } else if (filterGeneral.equals("widowed")) {
            i = widowedArray.iterator();
            if (filter.equals("Positivos")) {
                //    System.out.println("xw");
                i = positives.iterator();
            } else if (filter.equals("Negativos")) {
                //   System.out.println("yw");
                i = negatives.iterator();
            } else if (filter.equals("Neutros")) {
                //   System.out.println("zw");
                i = neutrals.iterator();
            }
            i = widowedArray.iterator();

        } else if (filterGeneral.equals("undefined")) {
            i = undefinedArray.iterator();
            if (filter.equals("Positivos")) {
                //  System.out.println("xu");
                i = positives.iterator();
            } else if (filter.equals("Negativos")) {
                //   System.out.println("yu");
                i = negatives.iterator();
            } else if (filter.equals("Neutros")) {
                //   System.out.println("zu");
                i = neutrals.iterator();
            }

        }

        return i;
    }

    private Iterator getLifeStage(String suri, String lang, String filter, String filterGeneral) {
        //  System.out.println("filter " + filter);
        // System.out.println("filter General " + filterGeneral);
        SemanticObject semObj = SemanticObject.getSemanticObject(suri);

        int young = 0, child = 0, teenAge = 0, youngAdult = 0, adult = 0, thirdAge = 0, nodefined = 0;
        Iterator<PostIn> itObjPostIns = null;
        if (semObj.getGenericInstance() instanceof Stream) {
            Stream stream = (Stream) semObj.getGenericInstance();
            itObjPostIns = stream.listPostInStreamInvs();
        } else if (semObj.getGenericInstance() instanceof SocialTopic) {
            SocialTopic socialTopic = (SocialTopic) semObj.getGenericInstance();
            itObjPostIns = PostIn.ClassMgr.listPostInBySocialTopic(socialTopic, socialTopic.getSocialSite());
        }


        ArrayList totalPost = new ArrayList();
        ArrayList youngArray = new ArrayList();
        ArrayList childArray = new ArrayList();
        ArrayList teenAgeArray = new ArrayList();
        ArrayList youngAdultArray = new ArrayList();
        ArrayList thirdAgeArray = new ArrayList();
        ArrayList adultArray = new ArrayList();
        ArrayList noDefine = new ArrayList();

        ArrayList all = new ArrayList();
        ArrayList allchild = new ArrayList();
        ArrayList allYoung = new ArrayList();
        ArrayList allTeenAge = new ArrayList();
        ArrayList allYoungAdult = new ArrayList();
        ArrayList allAdult = new ArrayList();
        ArrayList allThirdAge = new ArrayList();
        ArrayList allnodefine = new ArrayList();


        ArrayList positives = new ArrayList();
        ArrayList negatives = new ArrayList();
        ArrayList neutrals = new ArrayList();


        //    System.out.println("antes del while");
        while (itObjPostIns.hasNext()) {
            PostIn postIn = itObjPostIns.next();
            //   System.out.println("enel while");
            if (filterGeneral.equals("all")) {
                if (postIn.getPostInSocialNetworkUser().getSnu_LifeStage() == null
                        || postIn.getPostInSocialNetworkUser().getSnu_LifeStage().getId().equals("Child")
                        || postIn.getPostInSocialNetworkUser().getSnu_LifeStage().getId().equals("TeenAge")
                        || postIn.getPostInSocialNetworkUser().getSnu_LifeStage().getId().equals("YoungAdult")
                        || postIn.getPostInSocialNetworkUser().getSnu_LifeStage().getId().equals("Adult")
                        || postIn.getPostInSocialNetworkUser().getSnu_LifeStage().getId().equals("ThirdAge")
                        || postIn.getPostInSocialNetworkUser().getSnu_LifeStage().getId().equals("Young")) {

                    all.add(postIn);

                }

            } else if (filterGeneral.equals("child")) {
                if (postIn.getPostInSocialNetworkUser().getSnu_LifeStage() != null) {
                    if (postIn.getPostInSocialNetworkUser().getSnu_LifeStage().getId().equals("Child")) {
                        childArray.add(postIn);
                    }
                }

            } else if (filterGeneral.equals("young")) {
                //      System.out.println("entro a youngarray");
                if (postIn.getPostInSocialNetworkUser().getSnu_LifeStage() != null) {
                    if (postIn.getPostInSocialNetworkUser().getSnu_LifeStage().getId().equals("Young")) {
                        youngArray.add(postIn);
                    }
                }

            } else if (filterGeneral.equals("teenAge")) {
                if (postIn.getPostInSocialNetworkUser().getSnu_LifeStage() != null) {
                    if (postIn.getPostInSocialNetworkUser().getSnu_LifeStage().getId().equals("TeenAge")) {
                        teenAgeArray.add(postIn);
                    }
                }

            } else if (filterGeneral.equals("youngAdult")) {
                if (postIn.getPostInSocialNetworkUser().getSnu_LifeStage() != null) {
                    if (postIn.getPostInSocialNetworkUser().getSnu_LifeStage().getId().equals("YoungAdult")) {
                        youngAdultArray.add(postIn);
                    }
                }

            } else if (filterGeneral.equals("adult")) {
                if (postIn.getPostInSocialNetworkUser().getSnu_LifeStage() != null) {
                    if (postIn.getPostInSocialNetworkUser().getSnu_LifeStage().getId().equals("Adult")) {
                        adultArray.add(postIn);
                    }
                }


            } else if (filterGeneral.equals("thirdAge")) {
                if (postIn.getPostInSocialNetworkUser().getSnu_LifeStage() != null) {
                    if (postIn.getPostInSocialNetworkUser().getSnu_LifeStage().getId().equals("ThirdAge")) {
                        thirdAgeArray.add(postIn);
                    }
                }

            } else if (filterGeneral.equals("nodefine")) {
                if (postIn.getPostInSocialNetworkUser().getSnu_LifeStage() == null) {
                    noDefine.add(postIn);
                }


            }
        }



        if (filterGeneral.equals("all")) {
            Iterator allI = all.iterator();

            while (allI.hasNext()) {
                PostIn postIn = (PostIn) allI.next();

                //     System.out.println("eentro alll");
                if (postIn.getPostInSocialNetworkUser().getSnu_LifeStage() != null) {

                    if (filter.equals("Child") && postIn.getPostInSocialNetworkUser().getSnu_LifeStage().getId().equals("Child")) {
                        //      System.out.println("agrego a all male");
                        childArray.add(postIn);

                    } else if (filter.equals("Young") && postIn.getPostInSocialNetworkUser().getSnu_LifeStage().getId().equals("Young")) {
                        //        System.out.println("agrego a allfemale");
                        youngArray.add(postIn);

                    } else if (filter.equals("TeenAge") && postIn.getPostInSocialNetworkUser().getSnu_LifeStage().getId().equals("TeenAge")) {
                        //    System.out.println("agregar a all teenage");
                        teenAgeArray.add(postIn);
                    } else if (filter.equals("YoungAdult") && postIn.getPostInSocialNetworkUser().getSnu_LifeStage().getId().equals("YoungAdult")) {
                        //    System.out.println("agregar a all youngadult");
                        youngAdultArray.add(postIn);
                    } else if (filter.equals("Adult") && postIn.getPostInSocialNetworkUser().getSnu_LifeStage().getId().equals("Adult")) {
                        //      System.out.println("agregar a all adult");
                        adultArray.add(postIn);
                    } else if (filter.equals("ThirdAge") && postIn.getPostInSocialNetworkUser().getSnu_LifeStage().getId().equals("ThirdAge")) {
                        //      System.out.println("agregar a all thirdage");
                        thirdAgeArray.add(postIn);
                    }
                } else {
                    if (filter.equals(SWBSocialResUtil.Util.getStringFromGenericLocale("nodefine", lang)) && postIn.getPostInSocialNetworkUser().getSnu_LifeStage() == null) {
                        //    System.out.println("agregar a all nodefine");
                        noDefine.add(postIn);
                    }

                }
            }
        } else if (filterGeneral.equals("child")) {
            //   System.out.println("entro child");

            Iterator i = childArray.iterator();
            while (i.hasNext()) {
                PostIn postInMale = (PostIn) i.next();

                if (filter.equals("Positivos") && postInMale.getPostSentimentalType() == 1) {
                    positives.add(postInMale);

                } else if (filter.equals("Negativos") && postInMale.getPostSentimentalType() == 2) {
                    negatives.add(postInMale);

                } else if (filter.equals("Neutros") && postInMale.getPostSentimentalType() == 0) {
                    neutrals.add(postInMale);
                }
            }

        } else if (filterGeneral.equals("young")) {
            //    System.out.println("entro young");
            Iterator i = youngArray.iterator();
            while (i.hasNext()) {
                PostIn postInMale = (PostIn) i.next();

                if (filter.equals("Positivos") && postInMale.getPostSentimentalType() == 1) {
                    positives.add(postInMale);

                } else if (filter.equals("Negativos") && postInMale.getPostSentimentalType() == 2) {
                    negatives.add(postInMale);

                } else if (filter.equals("Neutros") && postInMale.getPostSentimentalType() == 0) {
                    neutrals.add(postInMale);
                }
            }

        } else if (filterGeneral.equals("teenAge")) {
            //   System.out.println("entro teenage");
            Iterator i = teenAgeArray.iterator();
            while (i.hasNext()) {
                PostIn postInMale = (PostIn) i.next();

                if (filter.equals("Positivos") && postInMale.getPostSentimentalType() == 1) {
                    positives.add(postInMale);

                } else if (filter.equals("Negativos") && postInMale.getPostSentimentalType() == 2) {
                    negatives.add(postInMale);

                } else if (filter.equals("Neutros") && postInMale.getPostSentimentalType() == 0) {
                    neutrals.add(postInMale);
                }
            }


        } else if (filterGeneral.equals("youngAdult")) {
            //     System.out.println("entro youngaadult");
            Iterator i = youngAdultArray.iterator();
            while (i.hasNext()) {
                PostIn postInMale = (PostIn) i.next();

                if (filter.equals("Positivos") && postInMale.getPostSentimentalType() == 1) {
                    positives.add(postInMale);

                } else if (filter.equals("Negativos") && postInMale.getPostSentimentalType() == 2) {
                    negatives.add(postInMale);

                } else if (filter.equals("Neutros") && postInMale.getPostSentimentalType() == 0) {
                    neutrals.add(postInMale);
                }
            }

        } else if (filterGeneral.equals("adult")) {
            //    System.out.println("entro adult");
            Iterator i = adultArray.iterator();
            while (i.hasNext()) {
                PostIn postInMale = (PostIn) i.next();

                if (filter.equals("Positivos") && postInMale.getPostSentimentalType() == 1) {
                    positives.add(postInMale);

                } else if (filter.equals("Negativos") && postInMale.getPostSentimentalType() == 2) {
                    negatives.add(postInMale);

                } else if (filter.equals("Neutros") && postInMale.getPostSentimentalType() == 0) {
                    neutrals.add(postInMale);
                }
            }

        } else if (filterGeneral.equals("thirdAge")) {
            //    System.out.println("entro a thirdage");
            Iterator i = thirdAgeArray.iterator();
            while (i.hasNext()) {
                PostIn postInMale = (PostIn) i.next();

                if (filter.equals("Positivos") && postInMale.getPostSentimentalType() == 1) {
                    positives.add(postInMale);

                } else if (filter.equals("Negativos") && postInMale.getPostSentimentalType() == 2) {
                    negatives.add(postInMale);

                } else if (filter.equals("Neutros") && postInMale.getPostSentimentalType() == 0) {
                    neutrals.add(postInMale);
                }
            }

        } else if (filterGeneral.equals("nodefine")) {
            //   System.out.println("entro no define");
            Iterator i = noDefine.iterator();
            while (i.hasNext()) {
                PostIn postInMale = (PostIn) i.next();

                if (filter.equals("Positivos") && postInMale.getPostSentimentalType() == 1) {
                    positives.add(postInMale);

                } else if (filter.equals("Negativos") && postInMale.getPostSentimentalType() == 2) {
                    negatives.add(postInMale);

                } else if (filter.equals("Neutros") && postInMale.getPostSentimentalType() == 0) {
                    neutrals.add(postInMale);
                }
            }

        }





        Iterator ii = null;

        if (filterGeneral.equals("all")) {

            if (filter.equals("")) {
                ii = all.iterator();
            } else if (filter.equals("Young")) {
                //     System.out.println("all young");
                ii = youngArray.iterator();
            } else if (filter.equals("Child")) {
                //      System.out.println("all child");
                ii = childArray.iterator();
            } else if (filter.equals("TeenAge")) {
                //      System.out.println("all tenage");
                ii = teenAgeArray.iterator();
            } else if (filter.equals("YoungAdult")) {
                //     System.out.println("allyoungadult");
                ii = youngAdultArray.iterator();
            } else if (filter.equals("ThirdAge")) {
                //      System.out.println("all thirdahe");
                ii = thirdAgeArray.iterator();
            } else if (filter.equals("Adult")) {
                //    System.out.println("alladult");
                ii = adultArray.iterator();
            } else {
                //     System.out.println("allnoefine");
                ii = noDefine.iterator();
            }


        } else if (filterGeneral.equals("child")) {
            if (filter.equals("")) {
                ii = childArray.iterator();
            } else if (filter.equals("Positivos")) {
                //    System.out.println("x");
                ii = positives.iterator();
            } else if (filter.equals("Negativos")) {
                //     System.out.println("y");
                ii = negatives.iterator();
            } else if (filter.equals("Neutros")) {
                //   System.out.println("z");
                ii = neutrals.iterator();
            }



        } else if (filterGeneral.equals("young")) {

            if (filter.equals("")) {
                ii = youngArray.iterator();
            } else if (filter.equals("Positivos")) {
                //   System.out.println("xy");
                ii = positives.iterator();
            } else if (filter.equals("Negativos")) {
                //    System.out.println("yy");
                ii = negatives.iterator();
            } else if (filter.equals("Neutros")) {
                //   System.out.println("zy");
                ii = neutrals.iterator();
            }

        } else if (filterGeneral.equals("teenAge")) {
            if (filter.equals("")) {
                ii = teenAgeArray.iterator();
            } else if (filter.equals("Positivos")) {
                //   System.out.println("xt");
                ii = positives.iterator();
            } else if (filter.equals("Negativos")) {
                //    System.out.println("yt");
                ii = negatives.iterator();
            } else if (filter.equals("Neutros")) {
                //    System.out.println("zt");
                ii = neutrals.iterator();
            }
        } else if (filterGeneral.equals("youngAdult")) {

            if (filter.equals("")) {
                ii = youngAdultArray.iterator();
            } else if (filter.equals("Positivos")) {
                //     System.out.println("xya");
                ii = positives.iterator();
            } else if (filter.equals("Negativos")) {
                //  System.out.println("yya");
                ii = negatives.iterator();
            } else if (filter.equals("Neutros")) {
                //   System.out.println("za");
                ii = neutrals.iterator();
            }

        } else if (filterGeneral.equals("adult")) {
            if (filter.equals("")) {
                ii = adultArray.iterator();
            } else if (filter.equals("Positivos")) {
                // System.out.println("xa");
                ii = positives.iterator();
            } else if (filter.equals("Negativos")) {
                //    System.out.println("ya");
                ii = negatives.iterator();
            } else if (filter.equals("Neutros")) {
                //    System.out.println("za");
                ii = neutrals.iterator();
            }

        } else if (filterGeneral.equals("thirdAge")) {
            if (filter.equals("")) {
                ii = thirdAgeArray.iterator();
            } else if (filter.equals("Positivos")) {
                //  System.out.println("xt");
                ii = positives.iterator();
            } else if (filter.equals("Negativos")) {
                //   System.out.println("yt");
                ii = negatives.iterator();
            } else if (filter.equals("Neutros")) {
                //    System.out.println("zt");
                ii = neutrals.iterator();
            }
        } else if (filterGeneral.equals("nodefine")) {
            if (filter.equals("")) {
                ii = noDefine.iterator();
            } else if (filter.equals("Positivos")) {
                //   System.out.println("xn");
                ii = positives.iterator();
            } else if (filter.equals("Negativos")) {
                //  System.out.println("yn");
                ii = negatives.iterator();
            } else if (filter.equals("Neutros")) {
                //   System.out.println("zn");
                ii = neutrals.iterator();
            }
        }
        return ii;
    }

    private Iterator getGeoLocation(String suri, String lang, String filter, String filterGeneral, HashMap map, String country) {
        SemanticObject semObj = SemanticObject.getSemanticObject(suri);
        Iterator<PostIn> itObjPostIns = null;
        JSONArray node = new JSONArray();


        if (semObj.getGenericInstance() instanceof Stream) {
            Stream stream = (Stream) semObj.getGenericInstance();
            itObjPostIns = stream.listPostInStreamInvs();
        } else if (semObj.getGenericInstance() instanceof SocialTopic) {
            SocialTopic socialTopic = (SocialTopic) semObj.getGenericInstance();
            itObjPostIns = PostIn.ClassMgr.listPostInBySocialTopic(socialTopic, socialTopic.getSocialSite());
        }

        Iterator i = null;
        int totalPost = 0;

        //System.out.println("antes de while");
        while (itObjPostIns.hasNext()) {
            PostIn postIn = itObjPostIns.next();
            CountryState key = postIn.getGeoStateMap();
            //System.out.println("++++++++++++" + key);
            String title = "";

            if (key != null) {
                Country countryy = key.getCountry();
                if (countryy.getId().equals(country)) {
                    if (key == null) {
                        title = "No definido";
                    } else {
                        title = reemplazar(key.getTitle());
                    }
                    // System.out.println("title " + title);
                    // map.put(title, map.containsKey(title) ? Integer.parseInt(map.get(title).toString()) + 1 : 0);
                    map.put(title, map.containsKey(title) ? addArray(map.get(title), postIn, title) : new ArrayList<PostIn>());
                    totalPost++;
                }
            }
        }


        i = map.entrySet().iterator();
        // System.out.println("Regresa i :"+ i);

        return i;
    }

    private Iterator getLanguage(String suri, String lang, String filter, String filterGeneral, HashMap map) {
        SemanticObject semObj = SemanticObject.getSemanticObject(suri);
        Iterator<PostIn> itObjPostIns = null;
        JSONArray node = new JSONArray();


        if (semObj.getGenericInstance() instanceof Stream) {
            Stream stream = (Stream) semObj.getGenericInstance();
            itObjPostIns = stream.listPostInStreamInvs();
        } else if (semObj.getGenericInstance() instanceof SocialTopic) {
            SocialTopic socialTopic = (SocialTopic) semObj.getGenericInstance();
            itObjPostIns = PostIn.ClassMgr.listPostInBySocialTopic(socialTopic, socialTopic.getSocialSite());
        }

        Iterator i = null;
        int totalPost = 0;

        //System.out.println("antes de while");
        while (itObjPostIns.hasNext()) {
            PostIn postIn = itObjPostIns.next();
            //CountryState key = postIn.getGeoStateMap();
            Language key = postIn.getMsg_lang();
            //System.out.println("++++++++++++" + key);
            String title = "";

            if (key != null) {
                title = reemplazar(key.getTitle());
                // System.out.println("title " + title);
                // map.put(title, map.containsKey(title) ? Integer.parseInt(map.get(title).toString()) + 1 : 0);
                map.put(title, map.containsKey(title) ? addArray(map.get(title), postIn, title) : new ArrayList<PostIn>());
                totalPost++;

            } else {
                title = "No definido";
                map.put(title, map.containsKey(title) ? addArray(map.get(title), postIn, title) : new ArrayList<PostIn>());
                totalPost++;
            }
        }


        i = map.entrySet().iterator();
        // System.out.println("Regresa i :"+ i);

        return i;
    }

    public ArrayList addArray(Object lista, PostIn postIn, String title) {
        //System.out.println("entro: " + lista + "" + postIn);
        Boolean c = lista instanceof ArrayList;
        //System.out.println("INSTaNCIA: " + c);
        if (lista == null) {
            lista = new ArrayList<PostIn>();
        }
        ArrayList l = (ArrayList) lista;
        //System.out.println("TITULO: " + title);
        //System.out.println("TITULO POST: " + postIn.getGeoStateMap().getTitle());
        l.add(postIn);


        return l;

    }

    public void getPositivesNegativesNeutros(ArrayList list, ArrayList positives, ArrayList negatives, ArrayList neutrals, String filter) {

        Iterator i = list.iterator();
        while (i.hasNext()) {
            PostIn postIn = (PostIn) i.next();

            if (filter.equals("Positivos") && postIn.getPostSentimentalType() == 1) {
                positives.add(postIn);

            } else if (filter.equals("Negativos") && postIn.getPostSentimentalType() == 2) {
                negatives.add(postIn);

            } else if (filter.equals("Neutros") && postIn.getPostSentimentalType() == 0) {
                neutrals.add(postIn);
            }
        }
    }

    public String reemplazar(String cadena) {

        String original = "áàäéèëíìïóòöúùuñÁÀÄÉÈËÍÌÏÓÒÖÚÙÜÑçÇ";
        // Cadena de caracteres ASCII que reemplazarán los originales.
        String ascii = "aaaeeeiiiooouuunAAAEEEIIIOOOUUUNcC";
        String output = cadena;
        for (int i = 0; i < original.length(); i++) {
            // Reemplazamos los caracteres especiales.
            output = output.replace(original.charAt(i), ascii.charAt(i));
        }//for i

        return output;
    }

    public ArrayList addPostIn(PostIn postIn, ArrayList list) {

        if (list == null) {
            list = new ArrayList();
        }
        list.add(postIn);
        return list;
    }

    public String cad(String cadena, PostIn pi) {
        Integer total;
        Integer positives = 0;
        Integer negatives = 0;
        Integer neutrals = 0;
        String[] phrasesStream = cadena.split(",");


        total = Integer.parseInt(phrasesStream[0]) + 1;

        if (pi.getPostSentimentalType() == 0) {
            neutrals = Integer.parseInt(phrasesStream[1]) + 1;
        } else {
            neutrals = Integer.parseInt(phrasesStream[1]);
        }
        if (pi.getPostSentimentalType() == 1) {
            positives = Integer.parseInt(phrasesStream[2]) + 1;
        } else {
            positives = Integer.parseInt(phrasesStream[2]);
        }
        if (pi.getPostSentimentalType() == 2) {
            negatives = Integer.parseInt(phrasesStream[3]) + 1;
        } else {
            negatives = Integer.parseInt(phrasesStream[3]);
        }
        String cade = "";
        positives.toString();
        cade += total.toString() + ",";
        cade += neutrals.toString() + ",";
        cade += positives.toString() + ",";
        cade += negatives.toString();
        return cade;
    }

    private Iterator<PostIn> getGeoLocationCountrys(String suri, String lang, String filter, String filterGeneral, HashMap map, String country) {
        SemanticObject semObj = SemanticObject.getSemanticObject(suri);
        Iterator<PostIn> itObjPostIns = null;
        JSONArray node = new JSONArray();


        if (semObj.getGenericInstance() instanceof Stream) {
            Stream stream = (Stream) semObj.getGenericInstance();
            itObjPostIns = stream.listPostInStreamInvs();
        } else if (semObj.getGenericInstance() instanceof SocialTopic) {
            SocialTopic socialTopic = (SocialTopic) semObj.getGenericInstance();
            itObjPostIns = PostIn.ClassMgr.listPostInBySocialTopic(socialTopic, socialTopic.getSocialSite());
        }

        Iterator i = null;
        int totalPost = 0;
        String title = "";
        //System.out.println("antes de while");
        while (itObjPostIns.hasNext()) {
            PostIn postIn = itObjPostIns.next();

            if (postIn.getGeoStateMap() == null) {
                title = "No definido";
                map.put(title, map.containsKey(title) ? addArray(map.get(title), postIn, title) : new ArrayList<PostIn>());
            } else {
                Country key = postIn.getGeoStateMap().getCountry();
                //System.out.println("++++++++++++" + key);     
                title = reemplazar(key.getTitle());
                //  if (key.getId().equals("")) {                   
                // System.out.println("title " + title);
                // map.put(title, map.containsKey(title) ? Integer.parseInt(map.get(title).toString()) + 1 : 0);
                map.put(title, map.containsKey(title) ? addArray(map.get(title), postIn, title) : new ArrayList<PostIn>());
                totalPost++;

                //}
            }
        }


        i = map.entrySet().iterator();
        // System.out.println("Regresa i :"+ i);

        return i;
    }
}
