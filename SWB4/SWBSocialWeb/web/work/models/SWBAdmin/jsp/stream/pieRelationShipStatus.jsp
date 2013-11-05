<%-- 
    Document   : pieLifeStage
    Created on : 03-oct-2013, 19:51:58
    Author     : gabriela.rosales
--%>
<%@page import="com.sun.corba.se.impl.ior.WireObjectKeyTemplate"%>
<%@page import="org.semanticwb.social.util.SWBSocialUtil"%>
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
    JSONArray getObject(SemanticObject semObj, String lang) throws Exception {
      
        int  single = 0, married = 0, divorced = 0, widowed = 0, undefined = 0;
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
            } else if (postIn.getPostInSocialNetworkUser().getSnu_relationShipStatus() == SocialNetworkUser.USER_RELATION_MARRIED) {
                married++;
                marriedArray.add(postIn);
            } else if (postIn.getPostInSocialNetworkUser().getSnu_relationShipStatus() == SocialNetworkUser.USER_RELATION_DIVORCED) {
                divorced++;
                divorcedArray.add(postIn);
            } else if (postIn.getPostInSocialNetworkUser().getSnu_relationShipStatus() == SocialNetworkUser.USER_RELATION_WIDOWED) {
                widowed++;
                widowedArray.add(postIn);
            } else if (postIn.getPostInSocialNetworkUser().getSnu_relationShipStatus() == SocialNetworkUser.USER_RELATION_UNDEFINED) {
                undefined++;
                undefinedArray.add(postIn);
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


        float intTotalVotos = undefined + single + married + divorced + widowed;

        //Positivo
        float intPorcentajeSingle = ((float) single * 100) / (float) intTotalVotos;

        //Negativo
        float intPorcentajeMarried = ((float) married * 100) / (float) intTotalVotos;

        //Neutro
        float intPorcentajeDivorced = ((float) divorced * 100) / (float) intTotalVotos;

        float intPorcentajeWidowed = ((float) widowed * 100) / (float) intTotalVotos;


        float intPorcentajeundefined = ((float) undefined * 100) / (float) intTotalVotos;

      
        JSONArray node = new JSONArray();


        if (single > 0) {
            JSONObject node1 = new JSONObject();
            node1.put("label", SWBSocialUtil.Util.getStringFromGenericLocale("single", lang) );
            node1.put("value1", "" + single);
            node1.put("value2", "" + round(intPorcentajeSingle));
            if (positivesSingle > negativesSingle && positivesSingle > neutralsSingle) {
                node1.put("color", "#86c440");
            } else if (negativesSingle > neutralsSingle) {
                node1.put("color", "#990000");
            } else {
                node1.put("color", "#eae8e3");
            }
            node1.put("label2", SWBSocialUtil.Util.getStringFromGenericLocale("single", lang)+": " + single +  " " +SWBSocialUtil.Util.getStringFromGenericLocale("positives", lang)+" : " + positivesSingle +  " " +SWBSocialUtil.Util.getStringFromGenericLocale("negatives", lang)+" : " + negativesSingle +  " " +SWBSocialUtil.Util.getStringFromGenericLocale("neutral", lang)+" : " + neutralsSingle);
            node1.put("chartclass", "possClass");
            node.put(node1);
        }

        if (married > 0) {
            JSONObject node2 = new JSONObject();
            node2.put("label", SWBSocialUtil.Util.getStringFromGenericLocale("married", lang));
            node2.put("value1", "" + married);
            node2.put("value2", "" + round(intPorcentajeMarried));
            if (positivesMarried > negativesMarried && positivesMarried > neutralsMarried) {
                node2.put("color", "#86c440");
            } else if (negativesMarried > neutralsMarried) {
                node2.put("color", "#990000");
            } else {
                node2.put("color", "#eae8e3");
            }
            node2.put("label2", SWBSocialUtil.Util.getStringFromGenericLocale("married", lang)+": " + married + " " + SWBSocialUtil.Util.getStringFromGenericLocale("positives", lang)+"  : " + positivesMarried +  " " +SWBSocialUtil.Util.getStringFromGenericLocale("negatives", lang)+" : " + negativesMarried +  " " +SWBSocialUtil.Util.getStringFromGenericLocale("neutral", lang)+" : " + neutralsMarried);
            node2.put("chartclass", "possClass");
            node.put(node2);
        }

        if (divorced > 0) {
            JSONObject node3 = new JSONObject();
            node3.put("label", SWBSocialUtil.Util.getStringFromGenericLocale("divorced", lang));
            node3.put("value1", "" + divorced);
            node3.put("value2", "" + round(intPorcentajeDivorced));
            if (positivesDivorced > negativesDivorced && positivesDivorced > neutralsDivorced) {
                node3.put("color", "#86c440");
            } else if (negativesDivorced > neutralsDivorced) {
                node3.put("color", "#990000");
            } else {
                node3.put("color", "#eae8e3");
            }
            node3.put("label2", SWBSocialUtil.Util.getStringFromGenericLocale("divorced", lang)+": " + divorced +  " " +SWBSocialUtil.Util.getStringFromGenericLocale("positives", lang)+"  : " + positivesDivorced +  " " +SWBSocialUtil.Util.getStringFromGenericLocale("negatives", lang)+" : " + negativesDivorced + " " + SWBSocialUtil.Util.getStringFromGenericLocale("neutral", lang)+" : " + neutralsDivorced);
            node3.put("chartclass", "possClass");
            node.put(node3);
        }

        if (widowed > 0) {
            JSONObject node4 = new JSONObject();
            node4.put("label", SWBSocialUtil.Util.getStringFromGenericLocale("widowed", lang));
            node4.put("value1", "" + widowed);
            node4.put("value2", "" + round(intPorcentajeWidowed));
            if (positivesWidowed > negativesWidowed && positivesWidowed > neutralsWidowed) {
                node4.put("color", "#86c440");
            } else if (negativesWidowed > neutralsWidowed) {
                node4.put("color", "#990000");
            } else {
                node4.put("color", "#eae8e3");
            }
            node4.put("label2", SWBSocialUtil.Util.getStringFromGenericLocale("widowed", lang)+": " + widowed + " " + SWBSocialUtil.Util.getStringFromGenericLocale("positives", lang)+" : " + positivesWidowed + " " +SWBSocialUtil.Util.getStringFromGenericLocale("negatives", lang)+ " : " + negativesWidowed + " " + SWBSocialUtil.Util.getStringFromGenericLocale("neutral", lang)+" : " + neutralsWidowed);
            node4.put("chartclass", "possClass");
            node.put(node4);
        }

        if (undefined > 0) {
            JSONObject node5 = new JSONObject();
            node5.put("label", SWBSocialUtil.Util.getStringFromGenericLocale("undefinedRelation", lang));
            node5.put("value1", "" + undefined);
            node5.put("value2", "" + round(intPorcentajeundefined));
            if (positivesUndefined > negativesUndefined && positivesUndefined > neutralsUndefined) {
                node5.put("color", "#86c440");
            } else if (negativesUndefined > neutralsUndefined) {
                node5.put("color", "#990000");
            } else {
                node5.put("color", "#eae8e3");
            }
            node5.put("label2", SWBSocialUtil.Util.getStringFromGenericLocale("undefinedRelation", lang)+": " + undefined + " " + SWBSocialUtil.Util.getStringFromGenericLocale("positives", lang)+"  : " + positivesUndefined+  " " +SWBSocialUtil.Util.getStringFromGenericLocale("negatives", lang)+" : " + negativesUndefined+  " " +SWBSocialUtil.Util.getStringFromGenericLocale("neutral", lang)+" : " + neutralsUndefined);
            node5.put("chartclass", "possClass");
            node.put(node5);
        }
        
        if(single == 0 && married == 0 && divorced==0 && widowed==0 && undefined==0 ){
                   
            JSONObject node3=new JSONObject();
            node3.put("label", SWBSocialUtil.Util.getStringFromGenericLocale("neutral", lang)); 
            node3.put("value1", "0");
            node3.put("value2", "100");
            node3.put("color", "#eae8e3");
            node3.put("chartclass", "neuClass");
            node3.put("label2", "Sin datos para procesar");

            node.put(node3);
        
        }

        return node;
    }

    public double round(float number) {
        return Math.rint(number * 100) / 100;
    }
%>
<%
    if (request.getParameter("objUri") != null) {
        SemanticObject semObj = SemanticObject.getSemanticObject(request.getParameter("objUri"));
        String lang = request.getParameter("lang");
        out.println(getObject(semObj, lang));
    }
%>