<%-- 
    Document   : pieGender
    Created on : 03-oct-2013, 19:51:58
    Author     : gabriela.rosales
--%>
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

        int neutrals = 0, positives = 0, negatives = 0, female = 0, male = 0, other = 0;
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
                genderMale.add(postIn);
            } else if (postIn.getPostInSocialNetworkUser().getSnu_gender() == SocialNetworkUser.USER_GENDER_FEMALE) {
                female++;
                genderFemale.add(postIn);
            } else if (postIn.getPostInSocialNetworkUser().getSnu_gender() == SocialNetworkUser.USER_GENDER_UNDEFINED) {
                other++;
                genderother.add(postIn);
            }

        }

        Iterator gMale = genderMale.iterator();
        int neutralsMale = 0, positivesMale = 0, negativesMale = 0;
        while (gMale.hasNext()) {
            PostIn postIn = (PostIn) gMale.next();
            if (postIn.getPostSentimentalType() == 0) {
                neutralsMale++;
            } else if (postIn.getPostSentimentalType() == 1) {
                positivesMale++;
            } else if (postIn.getPostSentimentalType() == 2) {
                negativesMale++;
            }
        }
   
       
        Iterator gFemale = genderFemale.iterator();
        int neutralsFemale = 0, positivesFemale = 0, negativesFemale = 0;
        while (gFemale.hasNext()) {
            PostIn postIn = (PostIn) gFemale.next();
            if (postIn.getPostSentimentalType() == 0) {
                neutralsFemale++;
            } else if (postIn.getPostSentimentalType() == 1) {
                positivesFemale++;
            } else if (postIn.getPostSentimentalType() == 2) {
                negativesFemale++;
            }
        }

      
        Iterator gOther = genderother.iterator();
        int neutralsOther = 0, positivesOther = 0, negativesOther = 0;

       
        while (gOther.hasNext()) {
            PostIn postIn = (PostIn) gFemale.next();
            if (postIn.getPostSentimentalType() == 0) {
                neutralsOther++;
            } else if (postIn.getPostSentimentalType() == 1) {
                positivesOther++;
            } else if (postIn.getPostSentimentalType() == 2) {
                negativesOther++;
            }
        }
       
        float intTotalVotos = male + female + other;

        //Positivo
        float intPorcentajeMale = ((float) male * 100) / (float) intTotalVotos;

        //System.out.println("Votos Positivos:"+positives+", porcentaje:"+intPorcentajePositive); 

        //Negativo
        float intPorcentajeFemale = ((float) female * 100) / (float) intTotalVotos;

        //System.out.println("Votos negatives"+negatives+", porcentaje:"+intPorcentajeNegative); 

        //Neutro
         float intPorcentajeOther = ((float) other * 100) / (float) intTotalVotos;

        JSONArray node = new JSONArray();

        if (male > 0) {
            
            JSONObject node1 = new JSONObject();
            node1.put("label", "Masculino");
            node1.put("value1", "" + male);
            node1.put("value2", "" + round(intPorcentajeMale));

            if (positivesMale > negativesMale && positivesMale > neutralsMale) {
                node1.put("color", "#86c440");
            } else if (negativesMale > neutralsMale) {
                node1.put("color", "#990000");
            } else {
                node1.put("color", "#eae8e3");
            }
            node1.put("label2", "Masculino " + male + "  Positivos: " + positivesMale + " Negativos :" + negativesMale + " Neutros: " + neutralsMale);
            node1.put("chartclass", "possClass");
            node.put(node1);
        }

        if (female > 0) {
            JSONObject node2 = new JSONObject();
            node2.put("label", "Femenino: ");
            node2.put("value1", "" + female);
            node2.put("value2", "" + round(intPorcentajeFemale));
            if (positivesFemale > negativesFemale && positivesFemale > neutralsFemale) {
                node2.put("color", "#86c440");
            } else if (negativesFemale > neutralsFemale) {
                node2.put("color", "#990000");
            } else {
                node2.put("color", "#eae8e3");
            }
            node2.put("label2", "Femenino: " + female + "     Positivos: " + positivesFemale + " Negativos :" + negativesFemale + " Neutros: " + neutralsFemale);
            node2.put("chartclass", "possClass");
            node.put(node2);
        }

        if (other > 0) {
            JSONObject node3 = new JSONObject();
            node3.put("label", "Otro: " );
            node3.put("value1", "" + other);
            node3.put("value2", "" + round(intPorcentajeOther));

            if (positivesOther > negativesOther && positivesOther > neutralsOther) {
                node3.put("color", "#86c440");
            } else if (negativesOther > neutralsOther) {
                node3.put("color", "#990000");
            } else {
                node3.put("color", "#eae8e3");
            }
            node3.put("label", "Otro: " + other + "  Positivos: " + positivesOther + " Negativos :" + negativesOther + " Neutros: " + neutralsOther);
            node3.put("chartclass", "possClass");
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