/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.social.admin.resources;

import com.hp.hpl.jena.sparql.lib.org.json.JSONArray;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.servlet.RequestDispatcher;
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
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.*;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;
import org.semanticwb.social.*;
import org.semanticwb.social.admin.resources.util.SWBSocialResUtil;
import org.semanticwb.social.util.SWBSocialUtil;

/**
 *
 * @author gabriela.rosales
 */
public class PieSocialTopic extends GenericResource {

    private static Logger log = SWBUtils.getLogger(PieSocialTopic.class);

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        if (request.getParameter("doView") == null) {
            doEdit(request, response, paramRequest);
            return;
        }
        final String myPath = SWBPlatform.getContextPath() + "/work/models/" + paramRequest.getWebPage().getWebSiteId() + "/jsp/stream/pieSocialTopic.jsp";
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
        if (paramRequest.getMode().equals("exportExcel")) {
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

    private void doGenerateReport(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws JSONException, IOException, com.hp.hpl.jena.sparql.lib.org.json.JSONException {
        String args = request.getParameter("args");
        String suri = request.getParameter("suri");
        User u =  null;


        String title = "";


        SocialTopic sTopic = (SocialTopic) SemanticObject.createSemanticObject(suri).getGenericInstance();
        title = sTopic.getTitle();


        String type = request.getParameter("type");
        String filter = request.getParameter("filter");
        String filterGeneral = request.getParameter("filterGeneral");
        if (filter == null) {
            filter = "";
        }



        Iterator<PostIn> setso = null;
        if (type.equals("socialTopicSocialNetwork")) {
            HashMap map = new HashMap();
            WebSite ss = SWBSocialUtil.getConfigWebSite();

            setso = getListSocialTopicSocialNetwork(suri, filter, filterGeneral, map);

            ArrayList lista = new ArrayList<PostIn>();
            if (filterGeneral.equals("all")) {
                while (setso.hasNext()) {
                    Map.Entry e = (Map.Entry) setso.next();
                    ArrayList list = (ArrayList) e.getValue();
                    if (list.isEmpty()) {
                        continue;
                    }
                    for (int j = 0; j < list.size(); j++) {
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
                    if (filterGeneral.equals(e.getKey())) {
                        ArrayList list = (ArrayList) e.getValue();
                        if (list.isEmpty()) {
                            continue;
                        }
                        for (int j = 0; j < list.size(); j++) {
                            PostOut p = (PostOut) list.get(j);
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
        } else if (type.equals("socialTopicSendUser")) { // usuarios
            HashMap map = new HashMap();
            WebSite ss = SWBSocialUtil.getConfigWebSite();

            setso = getListSocialTopicSendUser(suri, filter, filterGeneral, map);

            ArrayList lista = new ArrayList<PostIn>();
            if (filterGeneral.equals("all")) {
                while (setso.hasNext()) {
                    Map.Entry e = (Map.Entry) setso.next();
                    ArrayList list = (ArrayList) e.getValue();
                    SemanticObject sem = SemanticObject.createSemanticObject(e.getKey().toString().trim());
                    User user = (User) sem.createGenericInstance();

                    if (list.isEmpty()) {
                        continue;
                    }
                    for (int j = 0; j < list.size(); j++) {
                        if (filter.equals("")) {
                            lista.add(list.get(j));
                        } else if (filter.equals(user.getName())) {
                            lista.add(list.get(j));
                        }
                    }
                }
                setso = lista.iterator();
            } else {
                while (setso.hasNext()) {
                    Map.Entry e = (Map.Entry) setso.next();
                    SemanticObject sem = SemanticObject.createSemanticObject(e.getKey().toString().trim());
                    User user = (User) sem.createGenericInstance();

                    if (filterGeneral.equals(user.getName())) {
                        ArrayList list = (ArrayList) e.getValue();
                        if (list.isEmpty()) {
                            continue;
                        }
                        for (int j = 0; j < list.size(); j++) {
                            PostOut p = (PostOut) list.get(j);
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
        } else if (type.equals("socialTopicTopTen")) {

            HashMap map = new HashMap();
            WebSite ss = SWBSocialUtil.getConfigWebSite();

            setso = getListSocialTopicTopTen(suri, filter, filterGeneral, map , false);
            ArrayList lista = new ArrayList<PostIn>();
            int size = 0;
            if (filterGeneral.equals("all")) {
                while (setso.hasNext()) {
                    Map.Entry e = (Map.Entry) setso.next();
                    SemanticObject sem = SemanticObject.createSemanticObject(e.getKey().toString().trim());
                    PostOut p = (PostOut) sem.createGenericInstance();
                    size++;

                    if (filter.equals("")) {
                         if(p.getNumTotNewResponses() >0){
                        lista.add(p);
                         }
                    } else if (filter.equals(reemplazar(p.getMsg_Text()))) {
                         if(p.getNumTotNewResponses() >0){
                        lista.add(p);
                         }
                    }
                    if (size >= 10) {
                        break;
                    }

                }
                setso = lista.iterator();
            } else {
                while (setso.hasNext()) {
                    Map.Entry e = (Map.Entry) setso.next();
                    SemanticObject sem = SemanticObject.createSemanticObject(e.getKey().toString().trim());
                    PostOut p = (PostOut) sem.createGenericInstance();

                    if (filterGeneral.equals(p.getMsg_Text())) {

                        if (filter.equals("")) {
                            lista.add(p);
                        } else {
                            if (filter.equals("Positivos") && p.getPostSentimentalType() == 1) {
                                lista.add(p);
                            } else if (filter.equals("Negativos") && p.getPostSentimentalType() == 2) {
                                lista.add(p);
                            } else if (filter.equals("Neutros") && p.getPostSentimentalType() == 0) {
                                lista.add(p);
                            }
                        }

                    }

                }
                setso = lista.iterator();
            }

        } else if (type.equals("socialTopicTopBellow")) { //
            HashMap map = new HashMap();
            WebSite ss = SWBSocialUtil.getConfigWebSite();

            setso = getListSocialTopicTopTen(suri, filter, filterGeneral, map, true);
            ArrayList lista = new ArrayList<PostIn>();
            int size = 0;

            if (filterGeneral.equals("all")) {
                while (setso.hasNext()) {
                    Map.Entry e = (Map.Entry) setso.next();
                    SemanticObject sem = SemanticObject.createSemanticObject(e.getKey().toString().trim());
                    PostOut p = (PostOut) sem.createGenericInstance();
                    size++;

                    if (filter.equals("")) {
                        if(p.getNumTotNewResponses() >0){
                        lista.add(p);
                        }
                    } else if (filter.equals(reemplazar(p.getMsg_Text()))) {
                        if(p.getNumTotNewResponses() >0){
                        lista.add(p);
                        }
                    }
                    if (size >= 10) {
                        break;
                    }

                }
                setso = lista.iterator();
            } else {
                while (setso.hasNext()) {
                    Map.Entry e = (Map.Entry) setso.next();
                    SemanticObject sem = SemanticObject.createSemanticObject(e.getKey().toString().trim());
                    PostOut p = (PostOut) sem.createGenericInstance();

                    if (filterGeneral.equals(p.getMsg_Text())) {

                        if (filter.equals("")) {
                            lista.add(p);
                        } else {
                            if (filter.equals("Positivos") && p.getPostSentimentalType() == 1) {
                                lista.add(p);
                            } else if (filter.equals("Negativos") && p.getPostSentimentalType() == 2) {
                                lista.add(p);
                            } else if (filter.equals("Neutros") && p.getPostSentimentalType() == 0) {
                                lista.add(p);
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

            String title = titleR;


            HSSFWorkbook wb = new HSSFWorkbook();

            // Creo la Hoja en Excel
            Sheet sheet = wb.createSheet("Mensajes " + title);


            sheet.setDisplayGridlines(false);
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 8));

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
            //createHead(wb, row, 8, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, "RT/Likes");
            createHead(wb, row, 8, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, "Usuario");
            //createHead(wb, row, 10, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, "Seguidores");
            //createHead(wb, row, 11, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, "Amigos");
            //createHead(wb, row, 12, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, "Klout");
            //createHead(wb, row, 12, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, "Lugar");
            //createHead(wb, row, 13, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, "Prioritario");

            String lang = paramRequest.getUser().getLanguage();

            //Número de filas
            int i = 3;


            while (setso.hasNext()) {
                PostOut postIn = (PostOut) setso.next();

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
                createCell(cellStyle, wb, troww, 1, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, postIn instanceof Message ? "Mensaje" : postIn instanceof Photo ? "Foto" : postIn instanceof Video ? "Video" : "---");
                createCell(cellStyle, wb, troww, 2, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, postIn.getSocialNetwork().getDisplayTitle(lang));


                if (postIn.getSocialTopic() != null) {
                    createCell(cellStyle, wb, troww, 3, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, postIn.getSocialTopic().getDisplayTitle(lang));
                } else {
                    createCell(cellStyle, wb, troww, 3, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, "---");
                }
                createCell(cellStyle, wb, troww, 4, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, SWBUtils.TEXT.getTimeAgo(postIn.getCreated(), lang));

                String path = "";

                if (postIn.getPostSentimentalType() == 0) {
                    createCell(cellStyle, wb, troww, 5, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, "----");
                } else if (postIn.getPostSentimentalType() == 1) {
                    createCell(cellStyle, wb, troww, 5, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, "Positivo");
                } else if (postIn.getPostSentimentalType() == 2) {
                    createCell(cellStyle, wb, troww, 5, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, "Negativo");
                }
                createCell(cellStyle, wb, troww, 6, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, postIn.getPostIntesityType() == 0 ? SWBSocialResUtil.Util.getStringFromGenericLocale("low", lang) : postIn.getPostIntesityType() == 1 ? SWBSocialResUtil.Util.getStringFromGenericLocale("low", lang) : postIn.getPostIntesityType() == 2 ? SWBSocialResUtil.Util.getStringFromGenericLocale("low", lang) : "---");

                if (postIn.getPostSentimentalType() == 1) {
                    createCell(cellStyle, wb, troww, 7, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, "Positivo");

                } else if (postIn.getPostSentimentalType() == 2) {
                    createCell(cellStyle, wb, troww, 7, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, "Negativo");
                } else if (postIn.getPostSentimentalType() == 0) {

                    createCell(cellStyle, wb, troww, 7, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, "---");
                }
                //  int postS = postIn.get getPostShared();
                String postShared = "";// Integer.toString(postS);
               // createCell(cellStyle, wb, troww, 8, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, postShared);
                createCell(cellStyle, wb, troww, 8, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, postIn.getCreator()  != null ? postIn.getCreator().getName() : SWBSocialResUtil.Util.getStringFromGenericLocale("withoutUser", lang));
                //  Serializable foll = postIn.getSocialNetwork() != null ? postIn.getSocialNetwork().llowers() : SWBSocialResUtil.Util.getStringFromGenericLocale("withoutUser", lang);
                //createCell(cellStyle, wb, troww, 10, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, "");
                //Serializable amigos = postIn.getSocialNetwork() != null ? postIn.getSocialNetwork().getFriends() : SWBSocialResUtil.Util.getStringFromGenericLocale("withoutUser", lang);
                //createCell(cellStyle, wb, troww, 11, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, "");
                //createCell(cellStyle, wb, troww, 12, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, klout.toString());
                //createCell(cellStyle, wb, troww, 12, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, postIn == null ? "---" : "");
                //createCell(cellStyle, wb, troww, 13, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, postIn.isIsPrioritary() ? "SI" : "NO");

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
            //ssheet.autoSizeColumn(9);
            //ssheet.autoSizeColumn(10);
            //ssheet.autoSizeColumn(11);
            //ssheet.autoSizeColumn(12);
            //ssheet.autoSizeColumn(13);
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

    private Iterator getListSocialTopicSocialNetwork(String suri, String filter, String filterGeneral, HashMap map) {
        SemanticObject semObj = SemanticObject.getSemanticObject(suri);
        Iterator<PostOut> itObjPostIns = null;
        JSONArray node = new JSONArray();
        SocialTopic socialTopic = (SocialTopic) semObj.getGenericInstance();
        itObjPostIns = PostOut.ClassMgr.listPostOutBySocialTopic(socialTopic, socialTopic.getSocialSite());

        Iterator i = null;
        int totalPost = 0;

        while (itObjPostIns.hasNext()) {
            PostOut postIn = itObjPostIns.next();
            SocialNetwork key = null;

            String title = "";
            Iterator<SocialNetwork> listaRedes = postIn.listSocialNetworks();
            while (listaRedes.hasNext()) {
                SocialNetwork sn = listaRedes.next();
                if (sn == null) {
                    title = "No definido";
                    map.put(title, map.containsKey(title) ? addArray(map.get(title), postIn, title) : new ArrayList<PostOut>());
                } else {
                    title = sn.getTitle();
                    map.put(title, map.containsKey(title) ? addArray(map.get(title), postIn, title) : addPostOut(new ArrayList(), postIn, title));
                }
                totalPost++;
            }
        }

        i = map.entrySet().iterator();

        return i;
    }

    private Iterator getListSocialTopicSendUser(String suri, String filter, String filterGeneral, HashMap map) {
        SemanticObject semObj = SemanticObject.getSemanticObject(suri);
        Iterator<PostOut> itObjPostIns = null;
        JSONArray node = new JSONArray();
        SocialTopic socialTopic = (SocialTopic) semObj.getGenericInstance();
        itObjPostIns = PostOut.ClassMgr.listPostOutBySocialTopic(socialTopic, socialTopic.getSocialSite());

        Iterator i = null;
        int totalPost = 0;

        while (itObjPostIns.hasNext()) {
            PostOut postIn = itObjPostIns.next();
            SocialNetwork key = null;

            String title = "";
            User u = postIn.getCreator();
            if (u == null) {
                title = "No definido";
                map.put(u, map.containsKey(u) ? addArray(map.get(title), postIn, u) : new ArrayList<PostOut>());
            } else {
                map.put(u, map.containsKey(u) ? addArray(map.get(u), postIn, u) : addPostOut(new ArrayList(), postIn, u));
            }
            totalPost++;

        }
        i = map.entrySet().iterator();

        return i;
    }

    public ArrayList addArray(Object lista, PostOut postIn, String title) {
        Boolean c = lista instanceof ArrayList;
        if (lista == null) {
            lista = new ArrayList<PostIn>();
        }
        ArrayList l = (ArrayList) lista;
        l.add(postIn);
        return l;

    }

    public ArrayList addPostOut(ArrayList lista, PostOut postIn, String title) {

        lista.add(postIn);
        return lista;

    }

    public ArrayList addArray(Object lista, PostOut postIn, User title) {
        Boolean c = lista instanceof ArrayList;
        if (lista == null) {
            lista = new ArrayList<PostIn>();
        }
        ArrayList l = (ArrayList) lista;
        l.add(postIn);
        return l;

    }

    public ArrayList addPostOut(ArrayList lista, PostOut postIn, User title) {
        
        lista.add(postIn);
        return lista;

    }

    private Iterator<PostIn> getListSocialTopicTopTen(String suri, String filter, String filterGeneral, HashMap map, boolean tipo) {
        SemanticObject semObj = SemanticObject.getSemanticObject(suri);
        Iterator<PostOut> itObjPostIns = null;
        JSONArray node = new JSONArray();
        Iterator i = null;

        SocialTopic socialTopic = (SocialTopic) semObj.getGenericInstance();
        itObjPostIns = PostOut.ClassMgr.listPostOutBySocialTopic(socialTopic, socialTopic.getSocialSite());


        while (itObjPostIns.hasNext()) {

            PostOut postIn = itObjPostIns.next();
            String title = "";          

            if (postIn == null) {
                title = "No definido";
                map.put(postIn, map.containsKey(postIn) ? postIn.getNumTotNewResponses() : postIn.getNumTotNewResponses());
            } else {
     map.put(postIn, map.containsKey(postIn) ? postIn.getNumTotNewResponses() : postIn.getNumTotNewResponses());
            }
            map = sortByComparator(map, tipo);

        }
        i = map.entrySet().iterator();

        return i;
    }

    private static HashMap<String, Float> sortByComparator(Map<String, Float> unsortMap, final boolean order) {

        List<Entry<String, Float>> list = new LinkedList<Entry<String, Float>>(unsortMap.entrySet());

        // Sorting the list based on values
        Collections.sort(list, new Comparator<Entry<String, Float>>() {
            public int compare(Entry<String, Float> o1,
                    Entry<String, Float> o2) {
                if (order) {
                    return o1.getValue().compareTo(o2.getValue());
                } else {
                    return o2.getValue().compareTo(o1.getValue());

                }
            }
        });
        HashMap<String, Float> sortedMap = new LinkedHashMap<String, Float>();
        for (Entry<String, Float> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }


        return sortedMap;
    }
    
    
       public String reemplazar(String cadena) {

        String original = "áàäéèëíìïóòöúùuñÁÀÄÉÈËÍÌÏÓÒÖÚÙÜÑçÇ";
        String ascii = "aaaeeeiiiooouuunAAAEEEIIIOOOUUUNcC";
        String output = cadena;
        for (int i = 0; i < original.length(); i++) {
            output = output.replace(original.charAt(i), ascii.charAt(i));
        }//for i

        return output;
    }
       
}
