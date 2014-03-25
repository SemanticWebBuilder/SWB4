<%-- 
    Document   : streamSentimentData
    Created on : 08-ago-2013, 11:51:58
    Author     : jorge.jimenez
--%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page contentType="text/json" pageEncoding="UTF-8"%> 
<%@page import="org.semanticwb.platform.SemanticObject"%>
<%@page import="org.semanticwb.social.*"%>
<%@page import="java.util.Iterator"%>
<%@page import="org.semanticwb.SWBUtils"%>
<%@page import="org.semanticwb.model.*"%>
<%@page import="org.semanticwb.SWBPortal"%> 
<%@page import="org.semanticwb.platform.SemanticProperty"%>
<%@page import="org.semanticwb.portal.api.*"%>
<%@page import="org.json.*"%>
<%@page import="java.util.*"%>
<%@page import="static org.semanticwb.social.admin.resources.Reporter.*"%>


<%!
    JSONArray getObject(HttpServletRequest request) throws Exception {
        String genderParams[] = getValidParams(request.getParameterValues("gender"));
        String schoolGradeParams[] = getValidParams(request.getParameterValues("schoolGrade"));
        String slifeStageParams[] = getValidParams(request.getParameterValues("slifeStage"));
        String sentimentalRelationShipParams[] = getValidParams(request.getParameterValues("sentimentalRelationShip"));
        String scountryStateParams[] = getValidParams(request.getParameterValues("scountryState"));
        
        /*for(int i = 0; i < genderParams.length; i++){
            System.out.println("gender_" + genderParams[i]);
        }        
        for(int i = 0; i < schoolGradeParams.length; i++){
            System.out.println("schoolGradeParams_" + schoolGradeParams[i]);
        }
        for(int i = 0; i < slifeStageParams.length; i++){
            System.out.println("slifeStageParams_" + slifeStageParams[i]);
        }        
        for(int i = 0; i < sentimentalRelationShipParams.length; i++){
            System.out.println("sentimentalRelationShipParams_" + sentimentalRelationShipParams[i]);
        }
        for(int i = 0; i < scountryStateParams.length; i++){
            System.out.println("scountryStateParams_" + scountryStateParams[i]);
        }*/
        
        SemanticObject semObj = SemanticObject.getSemanticObject(request.getParameter("objUri"));
        /*String gender = request.getParameter("gender");
        String schoolGrade = request.getParameter("schoolGrade");
        String slifeStage = request.getParameter("slifeStage");
        String sentimentalRelationShip = request.getParameter("sentimentalRelationShip");
        String scountryState = request.getParameter("scountryState");*/
        String sinceDate = request.getParameter("sinceDate");
        String toDate = request.getParameter("toDate");

        Date dateSince = null;
        Date dateTo = null;

        if (!sinceDate.equals("") && !toDate.equals("")) {            
            SimpleDateFormat formatSince = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat formatTo = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            toDate += " 23:59:59";
            dateSince = formatSince.parse(sinceDate);
            dateTo = formatTo.parse(toDate);
        }


        //WebSite wsite=WebSite.ClassMgr.getWebSite(stream.getSemanticObject().getModel().getName());
        //System.out.println("Entra 3");
        int neutrals = 0, positives = 0, negatives = 0;
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
                                        if (postIn.getPostSentimentalType() == 0) {
                                            neutrals++;
                                        } else if (postIn.getPostSentimentalType() == 1) {
                                            positives++;
                                        } else if (postIn.getPostSentimentalType() == 2) {
                                            negatives++;
                                        }
                                    }
                                }                                
                            }
                        }
                    }
                }
            }
        }
               
        float intTotalVotos = positives + negatives + neutrals;

        //Positivo
        float intPorcentajePositive = ((float) positives * 100) / (float) intTotalVotos;

        //System.out.println("Votos Positivos:"+positives+", porcentaje:"+intPorcentajePositive); 

        //Negativo
        float intPorcentajeNegative = ((float) negatives * 100) / (float) intTotalVotos;

        //System.out.println("Votos negatives"+negatives+", porcentaje:"+intPorcentajeNegative); 

        //Neutro
        float intPorcentajeNeutral = ((float) neutrals * 100) / (float) intTotalVotos;

        //System.out.println("Votos neutrals"+neutrals+", porcentaje:"+intPorcentajeNeutral);         


        //System.out.println("Entra 4:"+positives+","+negatives+","+neutrals);
        JSONArray node = new JSONArray();

        if (intPorcentajePositive > 0) {
            JSONObject node1 = new JSONObject();
            node1.put("label", "Positivos");
            node1.put("value1", "" + positives);
            node1.put("value2", "" + round(intPorcentajePositive));
            node1.put("color", "#008000");
            node1.put("chartclass", "possClass");
            node.put(node1);
        }
        if (intPorcentajeNegative > 0) {
            JSONObject node2 = new JSONObject();
            node2.put("label", "Negativos");
            node2.put("value1", "" + negatives);
            node2.put("value2", "" + round(intPorcentajeNegative));
            node2.put("color", "#FF0000");
            node2.put("chartclass", "negClass");
            node.put(node2);
        }
        if (intPorcentajeNeutral > 0) {
            JSONObject node3 = new JSONObject();
            node3.put("label", "Neutros");
            node3.put("value1", "" + neutrals);
            node3.put("value2", "" + round(intPorcentajeNeutral));
            node3.put("color", "#FFD700");
            node3.put("chartclass", "neuClass");
            node.put(node3);
        }

        if (positives == 0 && negatives == 0 && neutrals == 0) {
            //System.out.println("Entra a ObSentData TODOS 0");
            JSONObject node3 = new JSONObject();
            node3.put("label", "Neutros");
            node3.put("value1", "0");
            node3.put("value2", "100");
            node3.put("color", "#eae8e3");
            node3.put("chartclass", "neuClass");
            node.put(node3);
        }
        return node;
    }

    /*private boolean passFilters(PostIn postIn, String gender, String schoolGrade, String slifeStage, String sentimentalRelationShip, String scountryState, Date dateSince, Date dateTo) {
        SocialNetworkUser postInUser = postIn.getPostInSocialNetworkUser();
        CountryState postInCountryState = postIn.getGeoStateMap();
        boolean genderZeroEqualsThree = false;       
        if(postInUser.getSnu_gender() == 0 && gender.equals("3")){
            genderZeroEqualsThree = true;
        }

        if (dateSince != null && dateTo != null) {
            System.out.print("dates");
            if ((gender.equals("all") || genderZeroEqualsThree || (postInUser.getSnu_gender() > 0 && postInUser.getSnu_gender() == Integer.parseInt(gender)))
                    && (schoolGrade.equals("all") || (postInUser.getSnu_education() > 0 && postInUser.getSnu_education() == Integer.parseInt(schoolGrade)))
                    && (slifeStage.equals("all") || (postInUser.getSnu_LifeStage() != null && postInUser.getSnu_LifeStage().getId().equals(slifeStage)))
                    && (sentimentalRelationShip.equals("all") || (postInUser.getSnu_relationShipStatus() > 0 && postInUser.getSnu_relationShipStatus() == Integer.parseInt(sentimentalRelationShip)))
                    && (scountryState.equals("all") || (postInCountryState != null && postInCountryState.getId().equals(scountryState)))
                    && (postIn.getPi_created().compareTo(dateSince) >= 0) && (postIn.getPi_created().compareTo(dateTo) <= 0)) {
                    System.out.println("       valid1");
                return true;
            } else if ((gender.equals("all") || genderZeroEqualsThree || (postInUser.getSnu_gender() > 0 && postInUser.getSnu_gender() == Integer.parseInt(gender)))
                    && (schoolGrade.equals("all") || (postInUser.getSnu_education() > 0 && postInUser.getSnu_education() == Integer.parseInt(schoolGrade)))
                    && (slifeStage.equals("all") || (postInUser.getSnu_LifeStage() == null && slifeStage.equals("noDefinido")  ))
                    && (sentimentalRelationShip.equals("all") || (postInUser.getSnu_relationShipStatus() > 0 && postInUser.getSnu_relationShipStatus() == Integer.parseInt(sentimentalRelationShip)))
                    && (scountryState.equals("all") || (postInCountryState == null && scountryState.equals("estadonoDefinido")))
                    && (postIn.getPi_created().compareTo(dateSince) >= 0) && (postIn.getPi_created().compareTo(dateTo) <= 0)) {
                System.out.println("       valid2");
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
    }*/

    private double round(float number) {
        return Math.rint(number * 100) / 100;
    }
%>
<%
    //System.out.println("Entra 0jjjjjjjjjjj");
    if (request.getParameter("objUri") != null) {
        //System.out.println("Entra 1:"+request.getParameter("objUri"));
        //System.out.println("Entra 2:"+lang);
        out.println(getObject(request));
    }
%>