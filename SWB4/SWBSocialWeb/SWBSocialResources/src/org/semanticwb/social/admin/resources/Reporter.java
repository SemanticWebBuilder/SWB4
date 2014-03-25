/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.social.admin.resources;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
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
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Descriptiveable;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;
import org.semanticwb.social.CountryState;
import org.semanticwb.social.MessageIn;
import org.semanticwb.social.PhotoIn;
import org.semanticwb.social.PostIn;
import org.semanticwb.social.SocialNetworkUser;
import org.semanticwb.social.SocialTopic;
import org.semanticwb.social.Stream;
import org.semanticwb.social.VideoIn;
import org.semanticwb.social.admin.resources.util.SWBSocialResUtil;
import org.semanticwb.social.util.SWBSocialUtil;

/**
 *
 * @author jorge.jimenez
 */
public class Reporter extends GenericResource {

    private static Logger log = SWBUtils.getLogger(PieChart.class);

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        if (request.getParameter("doView") == null) {
            doEdit(request, response, paramRequest);
            return;
        }
        final String myPath = SWBPlatform.getContextPath() + "/work/models/" + paramRequest.getWebPage().getWebSiteId() + "/jsp/stream/reporter.jsp";
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
        out.println("<iframe width=\"100%\" height=\"100%\" src=\"" + paramRequest.getRenderUrl().setMode(SWBResourceURL.Mode_VIEW).setParameter("doView", "1").setParameter("suri", request.getParameter("suri")) + "\">");
        out.println("<script type=\"text/javascript\" src=\"/swbadmin/js/dojo/dojo/dojo.js\"></script>");
        out.println("<script type=\"text/javascript\">");
        out.println("dojo.require(\"dijit.form.DateTextBox\");");
    out.println("</script>");
        out.println("</iframe> ");
    }

    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        if (paramRequest.getMode().equals("view")) {
            doView(request, response, paramRequest);
        } else if (paramRequest.getMode().equals("export")) {
            doGenerateReport(request, response, paramRequest);
        }
    }

    private void doGenerateReport(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) {
        String sinceDate = request.getParameter("sinceDate");
        String toDate = request.getParameter("toDate");

        Date dateSince = null;
        Date dateTo = null;
        try{
            if (!sinceDate.equals("") && !toDate.equals("")) {            
                SimpleDateFormat formatSince = new SimpleDateFormat("yyyy-MM-dd");
                SimpleDateFormat formatTo = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                toDate += " 23:59:59";
                dateSince = formatSince.parse(sinceDate);
                dateTo = formatTo.parse(toDate);
            }
        }catch(ParseException pe){
            log.warn("Invalid dates - ignored!");
        }
        
        
        String genderParams[] = getValidParams(request.getParameterValues("gender"));
        String schoolGradeParams[] = getValidParams(request.getParameterValues("schoolGrade"));
        String slifeStageParams[] = getValidParams(request.getParameterValues("lifeStage"));
        String sentimentalRelationShipParams[] = getValidParams(request.getParameterValues("sentimentalRelationShip"));
        String scountryStateParams[] = getValidParams(request.getParameterValues("countryState"));

        /*for(int i = 0; i < genderParams.length; i++){
            System.out.println("gender___" + genderParams[i]);
        }
        
        for(int i = 0; i < schoolGradeParams.length; i++){
            System.out.println("schoolGradeParams___" + schoolGradeParams[i]);
        }
        
        for(int i = 0; i < slifeStageParams.length; i++){
            System.out.println("slifeStageParams___" + slifeStageParams[i]);
        }
        
        for(int i = 0; i < sentimentalRelationShipParams.length; i++){
            System.out.println("sentimentalRelationShipParams___" + sentimentalRelationShipParams[i]);
        }
        
        for(int i = 0; i < scountryStateParams.length; i++){
            System.out.println("scountryStateParams___" + scountryStateParams[i]);
        }*/
        /*String gender = request.getParameter("gender");
        String schoolGrade = request.getParameter("schoolGrade");
        String slifeStage = request.getParameter("lifeStage");
        String sentimentalRelationShip = request.getParameter("sentimentalRelationShip");
        String scountryState = request.getParameter("countryState");*/
        String lang = request.getParameter("lang");
        SemanticObject semObj = SemanticObject.getSemanticObject(request.getParameter("objUri"));

        String title = "";
        if (semObj.getGenericInstance() instanceof Descriptiveable) {
            title = ((Descriptiveable) semObj.getGenericInstance()).getDisplayTitle(lang);
        }
        ArrayList<PostIn> postin = new ArrayList<PostIn>();
       
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
            //makes all the possible combinations to verify if the PostIn is valid
            boolean isValid = false;
            for(int genderi = 0; genderi < genderParams.length; genderi++){
                if(isValid)break;
                for(int schooli = 0; schooli < schoolGradeParams.length; schooli++){
                    if(isValid)break;
                    for(int lifei = 0; lifei < slifeStageParams.length; lifei++){
                        if(isValid)break;
                        for(int sentimenti = 0; sentimenti < sentimentalRelationShipParams.length; sentimenti++){
                            if(isValid)break;
                            for(int countryi = 0; countryi < scountryStateParams.length; countryi++){
                                if(isValid){
                                    break;
                                }else{
                                    if(passFilters(postIn, genderParams[genderi], schoolGradeParams[schooli], slifeStageParams[lifei], sentimentalRelationShipParams[sentimenti], scountryStateParams[countryi], dateSince, dateTo)){
                                        isValid = true;                                        
                                        postin.add(postIn);
                                    }
                                }                                
                            }
                        }
                    }
                }
            }
        }
        
        /*while (itObjPostIns.hasNext()) {
            PostIn postIn = itObjPostIns.next();
            if (passFilters(postIn, gender, schoolGrade, slifeStage, sentimentalRelationShip, scountryState, dateSince, dateTo)) {
                postin.add(postIn);
            }
        }*/
        createExcel(postin, paramRequest, response, lang, title);
    }

    public static boolean passFilters(PostIn postIn, String gender, String schoolGrade, String slifeStage, String sentimentalRelationShip, String scountryState, Date dateSince, Date dateTo) {
        SocialNetworkUser postInUser = postIn.getPostInSocialNetworkUser();
        CountryState postInCountryState = postIn.getGeoStateMap();
        boolean genderZeroEqualsThree = false;       
        if(postInUser.getSnu_gender() == 0 && gender.equals("3")){
            genderZeroEqualsThree = true;
        }

        if (dateSince != null && dateTo != null) {
            if ((gender.equals("all") || genderZeroEqualsThree || (postInUser.getSnu_gender() > 0 && postInUser.getSnu_gender() == Integer.parseInt(gender)))
                    && (schoolGrade.equals("all") || (postInUser.getSnu_education() > 0 && postInUser.getSnu_education() == Integer.parseInt(schoolGrade)))
                    && (slifeStage.equals("all") || (postInUser.getSnu_LifeStage() != null && postInUser.getSnu_LifeStage().getId().equals(slifeStage)))
                    && (sentimentalRelationShip.equals("all") || (postInUser.getSnu_relationShipStatus() > 0 && postInUser.getSnu_relationShipStatus() == Integer.parseInt(sentimentalRelationShip)))
                    && (scountryState.equals("all") || (postInCountryState != null && postInCountryState.getId().equals(scountryState)))
                    && (postIn.getPi_created().compareTo(dateSince) >= 0) && (postIn.getPi_created().compareTo(dateTo) <= 0)) {
                return true;
            } else if ((gender.equals("all") || genderZeroEqualsThree || (postInUser.getSnu_gender() > 0 && postInUser.getSnu_gender() == Integer.parseInt(gender)))
                    && (schoolGrade.equals("all") || (postInUser.getSnu_education() > 0 && postInUser.getSnu_education() == Integer.parseInt(schoolGrade)))
                    && (slifeStage.equals("all") || (postInUser.getSnu_LifeStage() == null && slifeStage.equals("noDefinido")  ))
                    && (sentimentalRelationShip.equals("all") || (postInUser.getSnu_relationShipStatus() > 0 && postInUser.getSnu_relationShipStatus() == Integer.parseInt(sentimentalRelationShip)))
                    && (scountryState.equals("all") || (postInCountryState == null && scountryState.equals("estadonoDefinido")))
                    && (postIn.getPi_created().compareTo(dateSince) >= 0) && (postIn.getPi_created().compareTo(dateTo) <= 0)) {
                return true;
            }
        } else {
            if ((gender.equals("all") || genderZeroEqualsThree || (postInUser.getSnu_gender() > 0 && postInUser.getSnu_gender() == Integer.parseInt(gender)))
                    && (schoolGrade.equals("all") || (postInUser.getSnu_education() > 0 && postInUser.getSnu_education() == Integer.parseInt(schoolGrade)))
                    && (slifeStage.equals("all") || (postInUser.getSnu_LifeStage() != null && postInUser.getSnu_LifeStage().getId().equals(slifeStage)))
                    && (sentimentalRelationShip.equals("all") || (postInUser.getSnu_relationShipStatus() > 0 && postInUser.getSnu_relationShipStatus() == Integer.parseInt(sentimentalRelationShip)))
                    && (scountryState.equals("all") || (postInCountryState != null && postInCountryState.getId().equals(scountryState)))) {
                return true;
            } else if ((gender.equals("all") || genderZeroEqualsThree || (postInUser.getSnu_gender() > 0 && postInUser.getSnu_gender() == Integer.parseInt(gender)))
                    && (schoolGrade.equals("all") || (postInUser.getSnu_education() > 0 && postInUser.getSnu_education() == Integer.parseInt(schoolGrade)))
                    && (slifeStage.equals("all") || (postInUser.getSnu_LifeStage() == null && slifeStage.equals("noDefinido")  ))
                    && (sentimentalRelationShip.equals("all") || (postInUser.getSnu_relationShipStatus() > 0 && postInUser.getSnu_relationShipStatus() == Integer.parseInt(sentimentalRelationShip)))
                    && (scountryState.equals("all") || (postInCountryState == null && scountryState.equals("estadonoDefinido")))) {
                return true;
            }

        }
        return false;
    }
    
    /*private boolean passFilters(PostIn postIn, String gender, String schoolGrade, String slifeStage, String sentimentalRelationShip, String scountryState) {
        SocialNetworkUser postInUser = postIn.getPostInSocialNetworkUser();
        CountryState postInCountryState = postIn.getGeoStateMap();
       
        if ((gender.equals("all") || (postInUser.getSnu_gender() > 0 && postInUser.getSnu_gender() == Integer.parseInt(gender)))
                && (schoolGrade.equals("all") || (postInUser.getSnu_education() > 0 && postInUser.getSnu_education() == Integer.parseInt(schoolGrade)))
                && (slifeStage.equals("all") || (postInUser.getSnu_LifeStage() != null && postInUser.getSnu_LifeStage().getId().equals(slifeStage)))
                && (sentimentalRelationShip.equals("all") || (postInUser.getSnu_relationShipStatus() > 0 && postInUser.getSnu_relationShipStatus() == Integer.parseInt(sentimentalRelationShip)))
                && (scountryState.equals("all") || (postInCountryState != null && postInCountryState.getId().equals(scountryState)))) {
            return true;
        }
        return false;
    }*/

    public void createExcel(ArrayList<PostIn> postin, SWBParamRequest paramRequest, HttpServletResponse response, String lang, String title) {
        try {
            // Defino el Libro de Excel
            Iterator v = postin.iterator();
            //String title = "No definido";


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
            createHead(wb, row, 12, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, "Lugar");
            createHead(wb, row, 13, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, "Prioritario");



            //Número de filas
            int i = 3;


            while (v.hasNext()) {
                PostIn postIn = (PostIn) v.next();

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
                } */else if (postIn.getTags() != null) {
                    if (postIn.getTags().length() > 200) {
                        createCell(cellStyle, wb, troww, 0, CellStyle.ALIGN_LEFT, CellStyle.VERTICAL_CENTER, postIn.getTags().substring(0, 200));

                    } else {
                        createCell(cellStyle, wb, troww, 0, CellStyle.ALIGN_LEFT, CellStyle.VERTICAL_CENTER, postIn.getTags());
                    }
                } else {
                    createCell(cellStyle, wb, troww, 0, CellStyle.ALIGN_LEFT, CellStyle.VERTICAL_CENTER, "---");

                }
                createCell(cellStyle, wb, troww, 1, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, postIn instanceof MessageIn ? SWBSocialResUtil.Util.getStringFromGenericLocale("message", lang) : postIn instanceof PhotoIn ? SWBSocialResUtil.Util.getStringFromGenericLocale("photo", lang) : postIn instanceof VideoIn ? SWBSocialResUtil.Util.getStringFromGenericLocale("video", lang) : "---");
                createCell(cellStyle, wb, troww, 2, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, postIn.getPostInSocialNetwork().getDisplayTitle(lang));


                if (postIn.getSocialTopic() != null) {
                    createCell(cellStyle, wb, troww, 3, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, postIn.getSocialTopic().getDisplayTitle(lang));
                } else {
                    createCell(cellStyle, wb, troww, 3, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, "---");
                }
                SimpleDateFormat sdf = new SimpleDateFormat("dd MMM YYYY HH:mm:ss");
                createCell(cellStyle, wb, troww, 4, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, sdf.format(postIn.getPi_created()));

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

    public static boolean isAllSelected(String params[]){
        boolean isAllSelected = false;
        if(params == null){//If the param is null then use all
            isAllSelected = true;
        }else{
            for(int i = 0; i < params.length ; i++){
                if(params[i].equalsIgnoreCase("all")){
                    isAllSelected = true;
                }
            }
        }
        return isAllSelected;
    }

    public static String[] getValidParams(String params[]){
        if(isAllSelected(params)){//Si el valor es null o si esta 'Todo' seleccionado
            String [] all = {"all"};
            return all;
        }else{
            return params;
        }
    }
}
