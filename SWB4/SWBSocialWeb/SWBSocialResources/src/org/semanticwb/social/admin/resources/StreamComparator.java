/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.social.admin.resources;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.servlet.RequestDispatcher;
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
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.semanticwb.SWBPlatform;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;
import org.semanticwb.social.Country;
import org.semanticwb.social.CountryState;
import org.semanticwb.social.MessageIn;
import org.semanticwb.social.PhotoIn;
import org.semanticwb.social.PostIn;
import org.semanticwb.social.SocialNetwork;
import org.semanticwb.social.Stream;
import org.semanticwb.social.VideoIn;
import static org.semanticwb.social.admin.resources.ImportFanPages.log;
import org.semanticwb.social.admin.resources.util.SWBSocialResUtil;
import org.semanticwb.social.util.SWBSocialUtil;

/**
 *
 * @author francisco.jimenez
 */
public class StreamComparator extends GenericResource {
    
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        
        String suri = request.getParameter("suri");

        if (request.getParameter("doView") == null) {
            doEdit(request, response, paramRequest);
            return;
        }
        final String myPath = SWBPlatform.getContextPath() + "/work/models/" + paramRequest.getWebPage().getWebSiteId() + "/jsp/stream/streamComparator.jsp";
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

    public void doGlobalReport(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        final String path = SWBPlatform.getContextPath() + "/work/models/" + paramRequest.getWebPage().getWebSiteId() + "/jsp/stream/streamComparatorGlobal.jsp";
        request.setAttribute("paramRequest", paramRequest);
        
        try{
            RequestDispatcher rd = request.getRequestDispatcher(path);
            rd.include(request, response);
        }catch(Exception e){
            log.error("Error in stream comparator: " + e);
        }
    }

    public void doExportExcel(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        String type = request.getParameter("type");
        String streamSuri = request.getParameter("suri");//The stream
        String suriSemObj = request.getParameter("suriSemObj");// could be the socialNet, CountryState or Country
        String sentiment = request.getParameter("sentiment");//sentiment(only for the first char)-> by sentiment
        String title = "";
        
        
        if(streamSuri != null && !streamSuri.trim().isEmpty()){
            if(SemanticObject.getSemanticObject(URLDecoder.decode(streamSuri)).getGenericInstance() instanceof Stream){
                Stream stream =(Stream) SemanticObject.getSemanticObject(URLDecoder.decode(streamSuri)).getGenericInstance();
                
                Iterator<PostIn> result = null;

                if (type.equals("bySocialNet")) {
                    if(SemanticObject.getSemanticObject(URLDecoder.decode(suriSemObj)).getGenericInstance() instanceof SocialNetwork){
                        SocialNetwork socialNet =(SocialNetwork) SemanticObject.getSemanticObject(URLDecoder.decode(suriSemObj)).getGenericInstance();
                        ArrayList<SemanticObject> postIns = SWBSocialUtil.sparql.getPostInbyStreamAndSocialNetwork(stream, socialNet);
                        ArrayList<PostIn> resultByNet = new ArrayList<PostIn>();
                        title = socialNet.getTitle();

                        for (SemanticObject postIn : postIns) {
                            PostIn sobj = (PostIn) postIn.createGenericInstance();
                            resultByNet.add(sobj);
                        }
                        result = resultByNet.iterator();
                    }
                    //setso = getSocialNetwork(suri, lang, filterGeneral, filter);
                } else if (type.equals("bySentiment")) {
                    int sentimentInt  = -1;
                    try{
                        sentimentInt = Integer.parseInt(sentiment);
                    }catch(NumberFormatException nfe){
                        /*ignore*/
                    }
                    
                    result = getListSentiment(stream, sentimentInt);
                } else if (type.equals("byGeoRefMx")) {
                    if(SemanticObject.getSemanticObject(URLDecoder.decode(suriSemObj)).getGenericInstance() instanceof CountryState){
                        CountryState countryState =(CountryState) SemanticObject.getSemanticObject(URLDecoder.decode(suriSemObj)).getGenericInstance();
                        result = getListByCountryStateMx(stream, countryState);
                    }
                } else if(type.equals("byGeoRefGlobal")){
                    if(suriSemObj.equalsIgnoreCase("all")){                        
                        result = getListByCountry(stream, null);
                    }else if(SemanticObject.getSemanticObject(URLDecoder.decode(suriSemObj)).getGenericInstance() instanceof Country){                        
                        Country country =(Country) SemanticObject.getSemanticObject(URLDecoder.decode(suriSemObj)).getGenericInstance();
                        result = getListByCountry(stream, country);
                    }
                }else if(type.equals("fullStream")){
                    result = stream.listPostInStreamInvs();
                }

                try {
                    createExcel(result, paramRequest, response, title);
                } catch (Exception e) {
                    log.error(e);
                }
            }
        }


        
    }
    
    private Iterator<PostIn> getListSentiment(Stream stream, int sentiment) {
        ArrayList<PostIn> result = new ArrayList<PostIn>();      
        Iterator<PostIn> itObjPostIns = stream.listPostInStreamInvs();
        while (itObjPostIns.hasNext()) {
            PostIn postIn = itObjPostIns.next();
            if (postIn != null) {
                if (postIn.getPostSentimentalType() == sentiment) {
                    result.add(postIn);
                }                
            }
        }
        return result.iterator();
    }

    
    private Iterator<PostIn> getListByCountryStateMx(Stream stream, CountryState countryState) {
        ArrayList<PostIn> result = new ArrayList<PostIn>();      
        Iterator<PostIn> itObjPostIns = stream.listPostInStreamInvs();
        
        while (itObjPostIns.hasNext()) {
            PostIn postIn = itObjPostIns.next();
            if (postIn.getGeoStateMap() != null) {
                if (postIn.getGeoStateMap().equals(countryState)) {
                    result.add(postIn);
                }                
            }
        }
        return result.iterator();
    }
    
    private Iterator<PostIn> getListByCountry(Stream stream, Country country) {
        ArrayList<PostIn> result = new ArrayList<PostIn>();      
        Iterator<PostIn> itObjPostIns = stream.listPostInStreamInvs();
        
        while (itObjPostIns.hasNext()) {
            PostIn postIn = itObjPostIns.next();
            if(country == null){//export all undefined
                if(postIn.getGeoStateMap() == null){
                    result.add(postIn);
                }
            }else {
                System.out.print("country:");
                if(postIn.getGeoStateMap() != null) {
                    System.out.println("|" + postIn.getGeoStateMap().getCountry()+ "VS" + country);
                    if (postIn.getGeoStateMap().getCountry().equals(country)) {
                        result.add(postIn);
                    }
                }                
            }
        }
        return result.iterator();
    }
    
    
    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        String mode = paramRequest.getMode();
        if(mode.equals("globalReport")){
            doGlobalReport(request, response, paramRequest);
        }else if (mode.equals("exportExcel")){
            try {
                doExportExcel(request, response, paramRequest);
            } catch (Exception e) {
                log.error(e);
            }
        }else{
            super.processRequest(request, response, paramRequest); //To change body of generated methods, choose Tools | Templates.
        }
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

                } else if (postIn.getTags() != null) {
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
}
