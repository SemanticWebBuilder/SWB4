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


<%!
    JSONArray getObject(HttpServletRequest request) throws Exception {
        SemanticObject semObj = SemanticObject.getSemanticObject(request.getParameter("objUri"));
        String gender = request.getParameter("gender");
        String schoolGrade = request.getParameter("schoolGrade");
        String slifeStage = request.getParameter("slifeStage");
        String sentimentalRelationShip = request.getParameter("sentimentalRelationShip");
        String scountryState = request.getParameter("scountryState");
        String sinceDate = request.getParameter("sinceDate");
        String toDate = request.getParameter("toDate");
     
        Date dateSince = null;
        Date dateTo = null;

        if (!sinceDate.equals("") && !toDate.equals("")) {
            SimpleDateFormat formatoDelTexto = new SimpleDateFormat("yyyy-MM-dd");
            dateSince = formatoDelTexto.parse(sinceDate);
            dateTo = formatoDelTexto.parse(toDate);
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
            if (passFilters(postIn, gender, schoolGrade, slifeStage, sentimentalRelationShip, scountryState, dateSince, dateTo)) {
                if (postIn.getPostSentimentalType() == 0) {
                    neutrals++;
                } else if (postIn.getPostSentimentalType() == 1) {
                    positives++;
                } else if (postIn.getPostSentimentalType() == 2) {
                    negatives++;
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
            node1.put("color", "#86c440");
            node1.put("chartclass", "possClass");
            node.put(node1);
        }
        if (intPorcentajeNegative > 0) {
            JSONObject node2 = new JSONObject();
            node2.put("label", "Negativos");
            node2.put("value1", "" + negatives);
            node2.put("value2", "" + round(intPorcentajeNegative));
            node2.put("color", "#990000");
            node2.put("chartclass", "negClass");
            node.put(node2);
        }
        if (intPorcentajeNeutral > 0) {
            JSONObject node3 = new JSONObject();
            node3.put("label", "Neutros");
            node3.put("value1", "" + neutrals);
            node3.put("value2", "" + round(intPorcentajeNeutral));
            node3.put("color", "#eae8e3");
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

    private boolean passFilters(PostIn postIn, String gender, String schoolGrade, String slifeStage, String sentimentalRelationShip, String scountryState, Date dateSince, Date dateTo) {
        SocialNetworkUser postInUser = postIn.getPostInSocialNetworkUser();
        CountryState postInCountryState = postIn.getGeoStateMap();
        //System.out.println("GenderPostIn:"+postInUser.getSnu_gender()+",schoolGrade:"+ postInUser.getSnu_education()+",life:"+slifeStage+"Gender:"+gender);

        if (dateSince != null && dateTo != null) {
            
            if ((gender.equals("all") || (postInUser.getSnu_gender() > 0 && postInUser.getSnu_gender() == Integer.parseInt(gender)))
                    && (schoolGrade.equals("all") || (postInUser.getSnu_education() > 0 && postInUser.getSnu_education() == Integer.parseInt(schoolGrade)))
                    && (slifeStage.equals("all") || (postInUser.getSnu_LifeStage() != null && postInUser.getSnu_LifeStage().getId().equals(slifeStage)))
                    && (sentimentalRelationShip.equals("all") || (postInUser.getSnu_relationShipStatus() > 0 && postInUser.getSnu_relationShipStatus() == Integer.parseInt(sentimentalRelationShip)))
                    && (scountryState.equals("all") || (postInCountryState != null && postInCountryState.getId().equals(scountryState)))
                    && (postIn.getCreated().compareTo(dateSince) >= 0) && (postIn.getCreated().compareTo(dateTo) <= 0)) {
                return true;
            }
        } else {
            if ((gender.equals("all") || (postInUser.getSnu_gender() > 0 && postInUser.getSnu_gender() == Integer.parseInt(gender)))
                    && (schoolGrade.equals("all") || (postInUser.getSnu_education() > 0 && postInUser.getSnu_education() == Integer.parseInt(schoolGrade)))
                    && (slifeStage.equals("all") || (postInUser.getSnu_LifeStage() != null && postInUser.getSnu_LifeStage().getId().equals(slifeStage)))
                    && (sentimentalRelationShip.equals("all") || (postInUser.getSnu_relationShipStatus() > 0 && postInUser.getSnu_relationShipStatus() == Integer.parseInt(sentimentalRelationShip)))
                    && (scountryState.equals("all") || (postInCountryState != null && postInCountryState.getId().equals(scountryState)))
                    ) {
                return true;
            }

        }
        return false;
    }

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