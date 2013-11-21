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
        String type = request.getParameter("type");
        String filter = request.getParameter("filter");
        if (filter == null) {
            filter = "";
        }

        String lang = paramRequest.getUser().getLanguage();
        Iterator<PostIn> setso = null;
        if (type.equals("gender")) {
            setso = getListGender(suri, lang, filter);
        } else if (type.equals("education")) {
            setso = getListEducation(suri, lang, filter);
        } else if (type.equals("relation")) {
            setso =  getRelationShip(suri, lang, filter);
        } else if (type.equals("lifeStage")) {
            setso = getLifeStage(suri, lang, filter);
        } else if (type.equals("geoLocation")) {
            setso = getGeoLocation(suri, lang, filter);
        }

        try {

            createExcel(setso, paramRequest, response);

        } catch (Exception e) {
            log.error(e);
        }
    }

    public void createExcel(Iterator<PostIn> setso, SWBParamRequest paramRequest, HttpServletResponse response) {
        try {
            // Defino el Libro de Excel
            // Iterator v = setso.iterator();
            String title = "hello";


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


            while (setso != null && setso.hasNext()) {

                PostIn postIn = (PostIn) setso.next();

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
                createCell(cellStyle, wb, troww, 1, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, postIn instanceof MessageIn ? paramRequest.getLocaleString("message") : postIn instanceof PhotoIn ? paramRequest.getLocaleString("photo") : postIn instanceof VideoIn ? paramRequest.getLocaleString("video") : "---");
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
                createCell(cellStyle, wb, troww, 6, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, postIn.getPostIntesityType() == 0 ? paramRequest.getLocaleString("low") : postIn.getPostIntesityType() == 1 ? paramRequest.getLocaleString("medium") : postIn.getPostIntesityType() == 2 ? paramRequest.getLocaleString("high") : "---");

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
                createCell(cellStyle, wb, troww, 9, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, postIn.getPostInSocialNetworkUser() != null ? postIn.getPostInSocialNetworkUser().getSnu_name() : paramRequest.getLocaleString("withoutUser"));
                Serializable foll = postIn.getPostInSocialNetworkUser() != null ? postIn.getPostInSocialNetworkUser().getFollowers() : paramRequest.getLocaleString("withoutUser");
                createCell(cellStyle, wb, troww, 10, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, foll.toString());
                Serializable amigos = postIn.getPostInSocialNetworkUser() != null ? postIn.getPostInSocialNetworkUser().getFriends() : paramRequest.getLocaleString("withoutUser");
                createCell(cellStyle, wb, troww, 11, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, amigos.toString());

                Serializable klout = postIn.getPostInSocialNetworkUser() != null ? postIn.getPostInSocialNetworkUser().getSnu_klout() : paramRequest.getLocaleString("withoutUser");

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

    private Iterator getListGender(String suri, String lang, String filter) {
 
        SemanticObject semObj = SemanticObject.getSemanticObject(suri);
        ArrayList listTotal = new ArrayList();
        ArrayList listMale = new ArrayList();
        ArrayList listFemale = new ArrayList();
        ArrayList listOther = new ArrayList();

        //String lang = paramRequest.getUser().getLanguage();

        int female = 0, male = 0, other = 0;
        int totalPost = 0;
        ArrayList genderMale = new ArrayList();
        ArrayList genderFemale = new ArrayList();
        ArrayList genderother = new ArrayList();
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

            if (postIn.getPostInSocialNetworkUser().getSnu_gender() == SocialNetworkUser.USER_GENDER_MALE) {
                male++;
                if (filter.equals(SWBSocialResUtil.Util.getStringFromGenericLocale("male", lang))) {
                    listMale.add(postIn);
                }

                listTotal.add(postIn);
            } else if (postIn.getPostInSocialNetworkUser().getSnu_gender() == SocialNetworkUser.USER_GENDER_FEMALE) {
                female++;
                if (filter.equals(SWBSocialResUtil.Util.getStringFromGenericLocale("female", lang))) {
                    listFemale.add(postIn);
                }
                listTotal.add(postIn);
            } else if (postIn.getPostInSocialNetworkUser().getSnu_gender() == SocialNetworkUser.USER_GENDER_UNDEFINED || postIn.getPostInSocialNetworkUser().getSnu_gender() == 0) {
                other++;
                if (filter.equals(SWBSocialResUtil.Util.getStringFromGenericLocale("other", lang))) {
                    listOther.add(postIn);
                }
                listTotal.add(postIn);
            }

        }
        Iterator i = null;
        if (filter.equals(SWBSocialResUtil.Util.getStringFromGenericLocale("male", lang))) {
            i = listMale.iterator();
        } else if (filter.equals(SWBSocialResUtil.Util.getStringFromGenericLocale("female", lang))) {
            i = listFemale.iterator();
        } else if (filter.equals(SWBSocialResUtil.Util.getStringFromGenericLocale("other", lang))) {
            i = listOther.iterator();
        } else {
            i = listTotal.iterator();
        }

        return i;
    }

    private Iterator getListEducation(String suri, String lang, String filter) {
        ArrayList listTotal = new ArrayList();
        SemanticObject semObj = SemanticObject.getSemanticObject(suri);


        int highSchool = 0, college = 0, graduate = 0, undefined = 0, totalPost = 0;
        Iterator<PostIn> itObjPostIns = null;
        if (semObj.getGenericInstance() instanceof Stream) {
            Stream stream = (Stream) semObj.getGenericInstance();
            itObjPostIns = stream.listPostInStreamInvs();
        } else if (semObj.getGenericInstance() instanceof SocialTopic) {
            SocialTopic socialTopic = (SocialTopic) semObj.getGenericInstance();
            itObjPostIns = PostIn.ClassMgr.listPostInBySocialTopic(socialTopic, socialTopic.getSocialSite());
        }

        ArrayList highSchoolArray = new ArrayList();
        ArrayList collegeArray = new ArrayList();
        ArrayList graduateArray = new ArrayList();
        ArrayList undefinedArray = new ArrayList();

        while (itObjPostIns.hasNext()) {
            PostIn postIn = itObjPostIns.next();
            totalPost++;

            if (postIn.getPostInSocialNetworkUser().getSnu_education() == SocialNetworkUser.USER_EDUCATION_HIGHSCHOOL) {
                highSchool++;
                highSchoolArray.add(postIn);
                listTotal.add(postIn);
            } else if (postIn.getPostInSocialNetworkUser().getSnu_education() == SocialNetworkUser.USER_EDUCATION_COLLEGE) {
                college++;
                collegeArray.add(postIn);
                listTotal.add(postIn);
            } else if (postIn.getPostInSocialNetworkUser().getSnu_education() == SocialNetworkUser.USER_EDUCATION_GRADUATE) {
                graduate++;
                graduateArray.add(postIn);
                listTotal.add(postIn);
            } else if (postIn.getPostInSocialNetworkUser().getSnu_education() == SocialNetworkUser.USER_EDUCATION_UNDEFINED || postIn.getPostInSocialNetworkUser().getSnu_education() == 0) {
                undefined++;
                undefinedArray.add(postIn);
                listTotal.add(postIn);
            }

        }

        Iterator i = null;
        if (filter.equals(SWBSocialResUtil.Util.getStringFromGenericLocale("highSchool", lang))) {
            i = highSchoolArray.iterator();
        } else if (filter.equals(SWBSocialResUtil.Util.getStringFromGenericLocale("college", lang))) {
            i = collegeArray.iterator();
        } else if (filter.equals(SWBSocialResUtil.Util.getStringFromGenericLocale("graduate", lang))) {
            i = graduateArray.iterator();
        } else if (filter.equals(SWBSocialResUtil.Util.getStringFromGenericLocale("undefinedEducation", lang))) {
            i = undefinedArray.iterator();
        } else {
            i = listTotal.iterator();
        }

        return i;
    }

    private Iterator getRelationShip(String suri, String lang, String filter) {

        int single = 0, married = 0, divorced = 0, widowed = 0, undefined = 0;
        SemanticObject semObj = SemanticObject.getSemanticObject(suri);

        ArrayList totalPost = new ArrayList();
        ArrayList singleArray = new ArrayList();
        ArrayList marriedArray = new ArrayList();
        ArrayList divorcedArray = new ArrayList();
        ArrayList widowedArray = new ArrayList();
        ArrayList undefinedArray = new ArrayList();

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

            if (postIn.getPostInSocialNetworkUser().getSnu_relationShipStatus() == SocialNetworkUser.USER_RELATION_SINGLE) {
                single++;
                singleArray.add(postIn);
                totalPost.add(postIn);
            } else if (postIn.getPostInSocialNetworkUser().getSnu_relationShipStatus() == SocialNetworkUser.USER_RELATION_MARRIED) {
                married++;
                marriedArray.add(postIn);
                totalPost.add(postIn);
            } else if (postIn.getPostInSocialNetworkUser().getSnu_relationShipStatus() == SocialNetworkUser.USER_RELATION_DIVORCED) {
                divorced++;
                divorcedArray.add(postIn);
                totalPost.add(postIn);
            } else if (postIn.getPostInSocialNetworkUser().getSnu_relationShipStatus() == SocialNetworkUser.USER_RELATION_WIDOWED) {
                widowed++;
                widowedArray.add(postIn);
                totalPost.add(postIn);
            } else if (postIn.getPostInSocialNetworkUser().getSnu_relationShipStatus() == SocialNetworkUser.USER_RELATION_UNDEFINED || postIn.getPostInSocialNetworkUser().getSnu_relationShipStatus() == 0) {
                undefined++;
                undefinedArray.add(postIn);
                totalPost.add(postIn);
            }
        }



        Iterator singleI = singleArray.iterator();
        int neutralsSingle = 0, positivesSingle = 0, negativesSingle = 0;
        while (singleI.hasNext()) {
            PostIn pi = (PostIn) singleI.next();
            if (pi.getPostSentimentalType() == 0) {
                neutralsSingle++;
            } else if (pi.getPostSentimentalType() == 1) {
                positivesSingle++;
            } else if (pi.getPostSentimentalType() == 2) {
                negativesSingle++;
            }
        }


        Iterator marriedI = marriedArray.iterator();
        int neutralsMarried = 0, positivesMarried = 0, negativesMarried = 0;
        while (marriedI.hasNext()) {
            PostIn pi = (PostIn) marriedI.next();
            if (pi.getPostSentimentalType() == 0) {
                neutralsMarried++;
            } else if (pi.getPostSentimentalType() == 1) {
                positivesMarried++;
            } else if (pi.getPostSentimentalType() == 2) {
                negativesMarried++;
            }
        }

        Iterator divorcedI = divorcedArray.iterator();
        int neutralsDivorced = 0, positivesDivorced = 0, negativesDivorced = 0;
        while (divorcedI.hasNext()) {
            PostIn pi = (PostIn) divorcedI.next();
            if (pi.getPostSentimentalType() == 0) {
                neutralsDivorced++;
            } else if (pi.getPostSentimentalType() == 1) {
                positivesDivorced++;
            } else if (pi.getPostSentimentalType() == 2) {
                negativesDivorced++;
            }
        }

        Iterator widowedI = widowedArray.iterator();
        int neutralsWidowed = 0, positivesWidowed = 0, negativesWidowed = 0;
        while (widowedI.hasNext()) {
            PostIn pi = (PostIn) widowedI.next();
            if (pi.getPostSentimentalType() == 0) {
                neutralsWidowed++;
            } else if (pi.getPostSentimentalType() == 1) {
                positivesWidowed++;
            } else if (pi.getPostSentimentalType() == 2) {
                negativesWidowed++;
            }
        }


        Iterator undefinedI = undefinedArray.iterator();
        int neutralsUndefined = 0, positivesUndefined = 0, negativesUndefined = 0;
        while (undefinedI.hasNext()) {
            PostIn pi = (PostIn) undefinedI.next();
            if (pi.getPostSentimentalType() == 0) {
                neutralsUndefined++;
            } else if (pi.getPostSentimentalType() == 1) {
                positivesUndefined++;
            } else if (pi.getPostSentimentalType() == 2) {
                negativesUndefined++;
            }
        }



        Iterator i = null;
        if (filter.equals("")) {
            i = totalPost.iterator();
        } else if (filter.equals(SWBSocialResUtil.Util.getStringFromGenericLocale("single", lang))) {
            i = singleArray.iterator();
        } else if (filter.equals(SWBSocialResUtil.Util.getStringFromGenericLocale("married", lang))) {
            i = marriedArray.iterator();
        } else if (filter.equals(SWBSocialResUtil.Util.getStringFromGenericLocale("divorced", lang))) {
            i = divorcedArray.iterator();
        } else if (filter.equals(SWBSocialResUtil.Util.getStringFromGenericLocale("widowed", lang))) {
            i = widowedArray.iterator();
        } else if (filter.equals(SWBSocialResUtil.Util.getStringFromGenericLocale("undefinedRelation", lang))) {
            i = undefinedArray.iterator();
        }

        return i;
    }

    private Iterator getLifeStage(String suri, String lang, String filter) {
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


        while (itObjPostIns.hasNext()) {
            PostIn postIn = itObjPostIns.next();
           
            
            if (postIn.getPostInSocialNetworkUser().getSnu_LifeStage() == null) {
                nodefined++;
                totalPost.add(postIn);
            }
            if(postIn.getPostInSocialNetworkUser().getSnu_LifeStage() != null){
            if (postIn.getPostInSocialNetworkUser().getSnu_LifeStage().getId().equals("Young")) {
                young++;
                youngArray.add(postIn);
                totalPost.add(postIn);
            } else if (postIn.getPostInSocialNetworkUser().getSnu_LifeStage().getId().equals("Child")) {
                child++;
                childArray.add(postIn);
                totalPost.add(postIn);

            } else if (postIn.getPostInSocialNetworkUser().getSnu_LifeStage().getId().equals("TeenAge")) {
                teenAge++;
                teenAgeArray.add(postIn);
                totalPost.add(postIn);

            } else if (postIn.getPostInSocialNetworkUser().getSnu_LifeStage().getId().equals("YoungAdult")) {
                youngAdult++;
                youngAdultArray.add(postIn);
                totalPost.add(postIn);

            } else if (postIn.getPostInSocialNetworkUser().getSnu_LifeStage().getId().equals("Adult")) {
                adult++;
                adultArray.add(postIn);
                totalPost.add(postIn);

            } else if (postIn.getPostInSocialNetworkUser().getSnu_LifeStage().getId().equals("ThirdAge")) {
                thirdAge++;
                thirdAgeArray.add(postIn);
                totalPost.add(postIn);

            }
            }
        }


        Iterator youngI = youngArray.iterator();

        int neutralsYoung = 0, positivesYoung = 0, negativesYoung = 0;
        while (youngI.hasNext()) {
            PostIn pi = (PostIn) youngI.next();
            if (pi.getPostSentimentalType() == 0) {
                neutralsYoung++;
            } else if (pi.getPostSentimentalType() == 1) {
                positivesYoung++;
            } else if (pi.getPostSentimentalType() == 2) {
                negativesYoung++;
            }
        }


        Iterator childI = childArray.iterator();

        int neutralsChild = 0, positivesChild = 0, negativesChild = 0;
        while (childI.hasNext()) {
            PostIn pi = (PostIn) childI.next();
            if (pi.getPostSentimentalType() == 0) {
                neutralsChild++;
            } else if (pi.getPostSentimentalType() == 1) {
                positivesChild++;
            } else if (pi.getPostSentimentalType() == 2) {
                negativesChild++;
            }
        }

        Iterator teenAgeI = teenAgeArray.iterator();

        int neutralsteenAge = 0, positivesteenAge = 0, negativesteenAge = 0;
        while (teenAgeI.hasNext()) {
            PostIn pi = (PostIn) teenAgeI.next();
            if (pi.getPostSentimentalType() == 0) {
                neutralsteenAge++;
            } else if (pi.getPostSentimentalType() == 1) {
                positivesteenAge++;
            } else if (pi.getPostSentimentalType() == 2) {
                negativesteenAge++;
            }
        }


        Iterator youngAdultI = youngAdultArray.iterator();

        int neutralsyoungAdult = 0, positivesyoungAdult = 0, negativesyoungAdult = 0;
        while (youngAdultI.hasNext()) {
            PostIn pi = (PostIn) youngAdultI.next();
            if (pi.getPostSentimentalType() == 0) {
                neutralsyoungAdult++;
            } else if (pi.getPostSentimentalType() == 1) {
                positivesyoungAdult++;
            } else if (pi.getPostSentimentalType() == 2) {
                negativesyoungAdult++;
            }
        }

        Iterator adultI = adultArray.iterator();

        int neutralsAdult = 0, positivesAdult = 0, negativesAdult = 0;
        while (adultI.hasNext()) {
            PostIn pi = (PostIn) adultI.next();
            if (pi.getPostSentimentalType() == 0) {
                neutralsAdult++;
            } else if (pi.getPostSentimentalType() == 1) {
                positivesAdult++;
            } else if (pi.getPostSentimentalType() == 2) {
                negativesAdult++;
            }
        }

        Iterator thirdAgeI = thirdAgeArray.iterator();

        int neutralsthirdAge = 0, positivesthirdAge = 0, negativesthirdAge = 0;
        while (thirdAgeI.hasNext()) {
            PostIn pi = (PostIn) thirdAgeI.next();
            if (pi.getPostSentimentalType() == 0) {
                neutralsthirdAge++;
            } else if (pi.getPostSentimentalType() == 1) {
                positivesthirdAge++;
            } else if (pi.getPostSentimentalType() == 2) {
                negativesthirdAge++;
            }
        }

        Iterator ii = null;


        if (filter.equals("Young")) {
            ii = youngArray.iterator();
        } else if (filter.equals("Child")) {
            ii = childArray.iterator();
        } else if (filter.equals("TeenAge")) {
            ii = teenAgeArray.iterator();
        } else if (filter.equals("YoungAdult")) {
            ii = youngAdultArray.iterator();
        } else if (filter.equals("ThirdAge")) {
            ii = thirdAgeArray.iterator();
        } else if (filter.equals("Adult")) {
            ii = adultArray.iterator();
        } else {
            ii = totalPost.iterator();
        }

        return ii;
    }

    private Iterator getGeoLocation(String suri, String lang, String filter) {
        SemanticObject semObj = SemanticObject.getSemanticObject(suri);

        int neutrals = 0, positives = 0, negatives = 0;
        Iterator<PostIn> itObjPostIns = null;
        ArrayList totalPost = new ArrayList();

        if (semObj.getGenericInstance() instanceof Stream) {
            Stream stream = (Stream) semObj.getGenericInstance();
            itObjPostIns = stream.listPostInStreamInvs();
        } else if (semObj.getGenericInstance() instanceof SocialTopic) {
            SocialTopic socialTopic = (SocialTopic) semObj.getGenericInstance();
            itObjPostIns = PostIn.ClassMgr.listPostInBySocialTopic(socialTopic, socialTopic.getSocialSite());
        }



        HashMap map = new HashMap();
        int size = 1;



        JSONArray node = new JSONArray();
        ArrayList<String> geoLocation = new ArrayList<String>();
        String cad = "1,0,0,0";
        ArrayList listEdo = new ArrayList();
        HashMap mapEdo = new HashMap();
        ArrayList listPost = new ArrayList();
        while (itObjPostIns.hasNext()) {

            PostIn postIn = itObjPostIns.next();
            CountryState key = postIn.getGeoStateMap();

           String title = "";
            if (key == null) {
                title = "No definido";
            } else {
                title = key.getTitle();

            }
                map.put(title, map.containsKey(title) ? cad(map.get(title).toString(), postIn) : "1,1,0,0");
                mapEdo.put(title, mapEdo.containsKey(title) ? addPostIn(postIn, (ArrayList) mapEdo.get(title)) : addPostIn(postIn, (ArrayList) mapEdo.get(title)));
                totalPost.add(postIn);
            

            size++;
        }

        Iterator i = null;

        Iterator ii = mapEdo.entrySet().iterator();
        while (ii.hasNext()) {
            Map.Entry e = (Map.Entry) ii.next();
            String key = reemplazar(e.getKey().toString());
            if (key.equals(filter)) {
                ArrayList list = (ArrayList) e.getValue();
                i = list.iterator();
            }
        }
        if (i == null) {
            i = totalPost.iterator();
        }

        return i;
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
}
