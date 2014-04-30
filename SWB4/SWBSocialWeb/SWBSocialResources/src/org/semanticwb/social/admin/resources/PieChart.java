/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.social.admin.resources;

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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.collections4.IteratorUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONException;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.WebSite;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;
import org.semanticwb.social.MessageIn;
import org.semanticwb.social.PhotoIn;
import org.semanticwb.social.PostIn;
import org.semanticwb.social.SWBSocial;
import org.semanticwb.social.SocialNetworkUser;
import org.semanticwb.social.SocialTopic;
import org.semanticwb.social.Stream;
import org.semanticwb.social.VideoIn;
import org.semanticwb.social.admin.resources.util.SWBSocialResUtil;

/**
 *
 * @author jorge.jimenez
 */
public class PieChart extends GenericResource {

    private static Logger log = SWBUtils.getLogger(PieChart.class);

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        if (request.getParameter("doView") == null) {
            doEdit(request, response, paramRequest);
            return;
        }
        final String myPath = SWBPlatform.getContextPath() + "/work/models/" + paramRequest.getWebPage().getWebSiteId() + "/jsp/stream/pieChart.jsp";
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
        } else if(paramRequest.getMode().equals("showBarByDay")){
            showBarByDay(request, response, paramRequest);
        } else if (paramRequest.getMode().equals("showGraphBar") || paramRequest.getMode().equals("anioMes")) {
            showGraphBar(request, response, paramRequest);
        } else if (paramRequest.getMode().equals("exportExcel")) {
            try {
                doGenerateReport(request, response, paramRequest);
            } catch (Exception e) {
                log.error(e);
            }
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
    
    private void showBarByDay(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws IOException, SWBResourceException {

        if (request.getParameter("doViewGraph") == null) {
            doEditGraphByDay(request, response, paramRequest);
            return;
        }

        final String myPath = SWBPlatform.getContextPath() + "/work/models/" + paramRequest.getWebPage().getWebSiteId() + "/jsp/stream/postInByHour.jsp";
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

    public void doEditGraphByDay(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();

        String selectedAnio =request.getParameter("selectedAnio") == null ? "" : request.getParameter("selectedAnio");
        String selectedMes = request.getParameter("selectedMes") == null ? "" : request.getParameter("selectedMes");
        String selectedDia = request.getParameter("selectedDia") == null ? "" : request.getParameter("selectedDia");
        out.println("<iframe  id=\"inneriframe1\" src=\"" + paramRequest.getRenderUrl().setMode("showBarByDay").setParameter("doViewGraph", "1").setParameter("suri", request.getParameter("suri")).setParameter("selectedAnio", selectedAnio).setParameter("selectedMes", selectedMes).setParameter("selectedDia", selectedDia) + "\"  frameborder=\"0\" width=\"100%\"   marginheight=\"0\" marginwidth=\"0\"  scrolling=\"no\"></iframe>"); //frameborder=\"0\" style=\"overflow:hidden;overflow-x:hidden;overflow-y:hidden;height:100%;width:100%;position:absolute;top:0px;left:0px;right:0px;bottom:0px\" height=\"100%\" width=\"100%\" ></iframe> ");


    }
    private void doGenerateReport(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws JSONException, IOException, com.hp.hpl.jena.sparql.lib.org.json.JSONException {
        //HashMap hmapResult = filtros(swbSocialUser, webSite, searchWord, request, stream, page);
        String suri = request.getParameter("suri");
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
        Iterator<PostIn> setso = null;

        String lang = paramRequest.getUser().getLanguage();

        if (type.equals("socialNetwork")) {
            setso = getSocialNetwork(suri, lang, filterGeneral, filter);
        } else if (type.equals("graphBar")) {
            setso = getGraphBar(request, suri, lang, filterGeneral, filter);
        } else if (type.equals("graphBarByHour")) {
            setso = getGraphBarByHour(request);
        } else {
            setso = getListSentiment(suri, lang, filter);

        }

        try {

            createExcel(setso, paramRequest, response, title);

        } catch (Exception e) {
            log.error(e);
        }
    }

    private Iterator getListSentiment(String suri, String lang, String filter) {

        SemanticObject semObj = SemanticObject.getSemanticObject(suri);
        int neutrals = 0, positives = 0, negatives = 0;

        ArrayList positivesArray = new ArrayList();
        ArrayList negativesArray = new ArrayList();
        ArrayList neutralsArray = new ArrayList();
        ArrayList totalArray = new ArrayList();
        Iterator i = null;


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
            if (postIn != null) {
                if (postIn.getPostSentimentalType() == 0) {
                    neutrals++;
                    neutralsArray.add(postIn);
                } else if (postIn.getPostSentimentalType() == 1) {
                    positives++;
                    positivesArray.add(postIn);
                } else if (postIn.getPostSentimentalType() == 2) {
                    negatives++;
                    negativesArray.add(postIn);
                }
                totalArray.add(postIn);
            }
        }


        if (filter.equals(SWBSocialResUtil.Util.getStringFromGenericLocale("neutral", lang))) {
            i = neutralsArray.iterator();
        } else if (filter.equals(SWBSocialResUtil.Util.getStringFromGenericLocale("positives", lang))) {
            i = positivesArray.iterator();
        } else if (filter.equals(SWBSocialResUtil.Util.getStringFromGenericLocale("negatives", lang))) {
            i = negativesArray.iterator();
        } else {
            i = totalArray.iterator();
        }

        return i;
    }

    private Iterator getSocialNetwork(String suri, String lang, String filterGeneral, String filter) {

        SemanticObject semObj = SemanticObject.getSemanticObject(suri);
        int neutrals = 0, positives = 0, negatives = 0;

        ArrayList positivesArray = new ArrayList();
        ArrayList negativesArray = new ArrayList();
        ArrayList neutralsArray = new ArrayList();
        ArrayList totalArray = new ArrayList();
        Iterator i = null;


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
            if (postIn != null && postIn.getPostInSocialNetwork() != null) {
                if ( filter.equals(postIn.getPostInSocialNetwork().getTitle()) ) {
                    totalArray.add(postIn);
                    if (postIn.getPostSentimentalType() == 0) {
                        neutrals++;
                        neutralsArray.add(postIn);
                    } else if (postIn.getPostSentimentalType() == 1) {
                        positives++;
                        positivesArray.add(postIn);
                    } else if (postIn.getPostSentimentalType() == 2) {
                        negatives++;
                        negativesArray.add(postIn);
                    }
                }
            }
        }


        if (filter.equals(SWBSocialResUtil.Util.getStringFromGenericLocale("neutral", lang))) {
            i = neutralsArray.iterator();
        } else if (filter.equals(SWBSocialResUtil.Util.getStringFromGenericLocale("positives", lang))) {
            i = positivesArray.iterator();
        } else if (filter.equals(SWBSocialResUtil.Util.getStringFromGenericLocale("negatives", lang))) {
            i = negativesArray.iterator();
        } else {
            i = totalArray.iterator();
        }

        return i;
    }

    public void createExcel(Iterator<PostIn> setso, SWBParamRequest paramRequest, HttpServletResponse response, String t) {
        try {
            // Defino el Libro de Excel
            // Iterator v = setso.iterator();
            String title = t;


            List list = IteratorUtils.toList(setso);
            Iterator<PostIn> setso1 = list.iterator();
          //  long size = SWBUtils.Collections.sizeOf(list.iterator());
            long limite = 65535;



            Workbook wb = null;
            if (list.size() <= limite) {

                wb = new HSSFWorkbook();
            } else if (list.size()  > limite) {

                wb = new XSSFWorkbook();
            }

            // Creo la Hoja en Excel
            Sheet sheet = wb.createSheet("Mensajes " + title);


            sheet.setDisplayGridlines(false);
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 13));

            // creo una nueva fila
            Row trow = sheet.createRow(0);
            createTituloCell(wb, trow, 0, CellStyle.ALIGN_CENTER,
                    CellStyle.VERTICAL_CENTER, "Mensajes " + title);

            // Creo la cabecera de mi listado en Excel
            Row row = sheet.createRow(2);

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


            while (setso1.hasNext()) {

                PostIn postIn = (PostIn) setso1.next();

                Row troww = sheet.createRow(i);

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
                createCell(cellStyle, wb, troww, 1, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, postIn instanceof MessageIn ? SWBSocialResUtil.Util.getStringFromGenericLocale("message", lang) : postIn instanceof PhotoIn ? SWBSocialResUtil.Util.getStringFromGenericLocale("photo", lang) : postIn instanceof VideoIn ? SWBSocialResUtil.Util.getStringFromGenericLocale("video", lang) : "---");
                if (postIn.getPostInSocialNetwork() != null) {
                    createCell(cellStyle, wb, troww, 2, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, postIn.getPostInSocialNetwork().getDisplayTitle(lang));
                } else {
                    createCell(cellStyle, wb, troww, 2, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, "---");
                }

                if (postIn.getSocialTopic() != null) {
                    createCell(cellStyle, wb, troww, 3, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, postIn.getSocialTopic().getDisplayTitle(lang));
                } else {
                    createCell(cellStyle, wb, troww, 3, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, "---");
                }
                SimpleDateFormat df = new SimpleDateFormat();
                //createCell(cellStyle, wb, troww, 4, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, SWBUtils.TEXT.getTimeAgo(postIn.getPi_created(), lang));
                createCell(cellStyle, wb, troww, 4, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, df.format(postIn.getPi_createdInSocialNet()));
                String path = "";

                if (postIn.getPostSentimentalType() == 0) {
                    createCell(cellStyle, wb, troww, 5, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, "----");
                } else if (postIn.getPostSentimentalType() == 1) {
                    createCell(cellStyle, wb, troww, 5, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, "Positivo");
                } else if (postIn.getPostSentimentalType() == 2) {
                    createCell(cellStyle, wb, troww, 5, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, "Negativo");
                }
                createCell(cellStyle, wb, troww, 6, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, postIn.getPostIntesityType() == 0 ? SWBSocialResUtil.Util.getStringFromGenericLocale("low", lang) : postIn.getPostIntesityType() == 1 ? SWBSocialResUtil.Util.getStringFromGenericLocale("medium", lang) : postIn.getPostIntesityType() == 2 ? SWBSocialResUtil.Util.getStringFromGenericLocale("high", lang) : "---");

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

            OutputStream ou = response.getOutputStream();
            response.setHeader("Cache-Control", "no-cache");
            response.setHeader("Pragma", "no-cache");
            if (list.size() <= limite) {
                response.setHeader("Content-Disposition", "attachment; filename=\"Mensajes.xls\";");
            } else {
                response.setHeader("Content-Disposition", "attachment; filename=\"Mensajes.xlsx\";");
            }
            response.setContentType("application/octet-stream");
            wb.write(ou);
            ou.close();

        } catch (Exception e) {
            log.error(e);
        }
    }

    public static void createTituloCell(Workbook wb, Row row, int column, short halign, short valign, String strContenido) {


        CreationHelper ch = wb.getCreationHelper();
        Cell cell = row.createCell(column);
        cell.setCellValue(ch.createRichTextString(strContenido));

        Font cellFont = wb.createFont();
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

    public static void createHead(Workbook wb, Row row, int column, short halign, short valign, String strContenido) {


        CreationHelper ch = wb.getCreationHelper();
        Cell cell = row.createCell(column);
        cell.setCellValue(ch.createRichTextString(strContenido));

        Font cellFont = wb.createFont();
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

    private Iterator<PostIn> getGraphBar(HttpServletRequest request, String suri, String lang, String filterGeneral, String filter) {
        SemanticObject semObj = SemanticObject.getSemanticObject(suri);
        ArrayList totalArray = new ArrayList();
        Iterator i = null;
        String selectedAnio = request.getParameter("selectedAnio");
        String selectAnio = request.getParameter("selectAnio");
        String selectMes = request.getParameter("selectMes");
        String selectDay = request.getParameter("selectDay");
        String selectMonth2 = request.getParameter("selectMonth2");


        Iterator<PostIn> itObjPostIns = null;
        if (semObj.getGenericInstance() instanceof Stream) {
            Stream stream = (Stream) semObj.getGenericInstance();
            itObjPostIns = stream.listPostInStreamInvs();
        } else if (semObj.getGenericInstance() instanceof SocialTopic) {
            SocialTopic socialTopic = (SocialTopic) semObj.getGenericInstance();
            itObjPostIns = PostIn.ClassMgr.listPostInBySocialTopic(socialTopic, socialTopic.getSocialSite());
        }


        Calendar calendario = Calendar.getInstance();


        String anio = request.getParameter("selectedAnio");
        if (selectedAnio.equals("")) {
            selectedAnio = String.valueOf(calendario.get(Calendar.YEAR));
        }
        Date date; // your date
        Calendar cal = Calendar.getInstance();

        while (itObjPostIns.hasNext()) {
            PostIn postIn = itObjPostIns.next();
            if (postIn != null && postIn.getPostInSocialNetwork() != null) {
                cal.setTime(postIn.getPi_created());

                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH) + 1;
                int day = cal.get(Calendar.DAY_OF_MONTH);

                if (!selectedAnio.equals("") && !selectMes.equals("") && selectDay.equals("")) {
                    if (year == Integer.parseInt(selectedAnio) && month == Integer.parseInt(selectMes)) {
                        totalArray.add(postIn);
                    }
                } else if (!selectedAnio.equals("") && !selectMonth2.equals("") && !selectDay.equals("")) {
                    if (year == Integer.parseInt(selectedAnio) && month == Integer.parseInt(selectMonth2) + 1 && day == Integer.parseInt(selectDay)) {
                        totalArray.add(postIn);
                    }
                }
            }
        }
        i = totalArray.iterator();
        return i;
    }
    
    private Iterator<PostIn> getGraphBarByHour(HttpServletRequest request) {
        String suri = request.getParameter("suri");
        SemanticObject semObj = SemanticObject.getSemanticObject(suri);
        ArrayList tmpArray = new ArrayList();
        ArrayList finalArray = new ArrayList();
        Iterator it = null;

        String selectedYear = request.getParameter("selectedYear") == null ? "" : request.getParameter("selectedYear");
        String selectedMonth = request.getParameter("selectedMonth") == null ? "" : request.getParameter("selectedMonth");
        String selectedDay = request.getParameter("selectedDay") == null ? "" : request.getParameter("selectedDay");
        String selectedHourTmp = request.getParameter("selectedHour") == null ? "" : request.getParameter("selectedHour");
        //System.out.println("SELECTED HOUR:" + selectedHourTmp);
        int selectedHour = Integer.parseInt(selectedHourTmp);
        String fullDate = "";
        
        fullDate += selectedYear + "-" + (selectedMonth.length() == 1 ? "0" + selectedMonth : selectedMonth) +
                 "-" + (selectedDay.length() == 1 ? "0" + selectedDay : selectedDay);        

        if (semObj.getGenericInstance() instanceof Stream) {
            Stream stream = (Stream) semObj.getGenericInstance();
            tmpArray = getPostInByStreamAndDay(stream, fullDate);            
        } else if (semObj.getGenericInstance() instanceof SocialTopic) {
            SocialTopic socialTopic = (SocialTopic) semObj.getGenericInstance();
            tmpArray = getPostInBySocialTopicAndDay(socialTopic, fullDate);
        }

        for(int i = 0; i < tmpArray.size(); i++){
            SemanticObject sobj =(SemanticObject) tmpArray.get(i);
            PostIn postIn = (PostIn)sobj.createGenericInstance();
            Calendar calendario = GregorianCalendar.getInstance();
            calendario.setTime(postIn.getPi_createdInSocialNet());
            int hourOfDay = calendario.get(Calendar.HOUR_OF_DAY);
            
            if(hourOfDay == selectedHour){
                finalArray.add(postIn);
            }
        }        
        it = finalArray.iterator();
        return it;
    }
    
    /**
    * 
    * @param stream
    * @param a date in the format yyyy-mm-dd
    * @return the posts created some day.
    */
   public static ArrayList getPostInByStreamAndDay(org.semanticwb.social.Stream stream, String date)
   {
       //System.out.println("entrando por los datos!");
       if(date == null || date.isEmpty()){
           return null;
       }
       String query=
          "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
          "PREFIX social: <http://www.semanticwebbuilder.org/swb4/social#>\n" +
          "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\n" +
          "\n";

          query+="select ?semObj" +"\n";
          query+=
          "where {\n" +
          " ?semObj social:postInStream <"+ stream.getURI()+">. \n" + 
          " ?semObj social:pi_createdInSocialNet ?postInCreated. \n" +
          " FILTER regex(?postInCreated, \"" + date + "\", \"i\") \n" +
          "  }\n";

          WebSite wsite=WebSite.ClassMgr.getWebSite(stream.getSemanticObject().getModel().getName());
          return SWBSocial.executeQueryArraySemObj(query, wsite);
    }
   
   /**
    * 
    * @param socialTopic
    * @param a date in the format yyyy-mm-dd
    * @return the posts created some day.
    */
   public static ArrayList getPostInBySocialTopicAndDay(org.semanticwb.social.SocialTopic socialTopic, String date)
   {
       //System.out.println("entrando por los datos!");
       if(date == null || date.isEmpty()){
           return null;
       }
       String query=
          "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
          "PREFIX social: <http://www.semanticwebbuilder.org/swb4/social#>\n" +
          "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\n" +
          "\n";

          query+="select ?semObj" +"\n";
          query+=
          "where {\n" +
          " ?semObj social:socialTopic <"+ socialTopic.getURI()+">. \n" + 
          " ?semObj social:pi_createdInSocialNet ?postInCreated. \n" +
          " FILTER regex(?postInCreated, \"" + date + "\", \"i\") \n" +
          "  }\n";

          WebSite wsite=WebSite.ClassMgr.getWebSite(socialTopic.getSemanticObject().getModel().getName());
          return SWBSocial.executeQueryArraySemObj(query, wsite);
    }
}
