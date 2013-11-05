<%-- 
    Document   : pieLifeStage
    Created on : 03-oct-2013, 19:51:58
    Author     : gabriela.rosales
--%>
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
        
        int highSchool = 0, college = 0, graduate = 0, undefined = 0;
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


            if (postIn.getPostInSocialNetworkUser().getSnu_education() == SocialNetworkUser.USER_EDUCATION_HIGHSCHOOL) {
                highSchool++;
                highSchoolArray.add(postIn);
            } else if (postIn.getPostInSocialNetworkUser().getSnu_education() == SocialNetworkUser.USER_EDUCATION_COLLEGE) {
                college++;
                collegeArray.add(postIn);
            } else if (postIn.getPostInSocialNetworkUser().getSnu_education() == SocialNetworkUser.USER_EDUCATION_GRADUATE) {
                graduate++;
                graduateArray.add(postIn);
            } else if (postIn.getPostInSocialNetworkUser().getSnu_education() == SocialNetworkUser.USER_EDUCATION_UNDEFINED) {
                undefined++;
                undefinedArray.add(postIn);
            }



        }


        Iterator ihighSchool = highSchoolArray.iterator();
       
        int neutralshighSchool = 0, positiveshighSchool = 0, negativeshighSchool = 0;
        while (ihighSchool.hasNext()) {
            PostIn postIn = (PostIn) ihighSchool.next();
            if (postIn.getPostSentimentalType() == 0) {
                neutralshighSchool++;
            } else if (postIn.getPostSentimentalType() == 1) {
                positiveshighSchool++;
            } else if (postIn.getPostSentimentalType() == 2) {
                negativeshighSchool++;
            }
        }
     
        Iterator icollegeArray = collegeArray.iterator();

        int neutralscollege = 0, positivescollege = 0, negativescollege = 0;
        while (icollegeArray.hasNext()) {
            PostIn postIn = (PostIn) icollegeArray.next();
            if (postIn.getPostSentimentalType() == 0) {
                neutralscollege++;
            } else if (postIn.getPostSentimentalType() == 1) {
                positivescollege++;
            } else if (postIn.getPostSentimentalType() == 2) {
                negativescollege++;
            }
        }

        Iterator igraduateArray = graduateArray.iterator();
      
        int neutralsgraduate = 0, positivesgraduate = 0, negativesgraduate = 0;
        while (igraduateArray.hasNext()) {
            PostIn postIn = (PostIn) igraduateArray.next();
            if (postIn.getPostSentimentalType() == 0) {
                neutralsgraduate++;
            } else if (postIn.getPostSentimentalType() == 1) {
                positivesgraduate++;
            } else if (postIn.getPostSentimentalType() == 2) {
                negativesgraduate++;
            }
        }

        Iterator iundefinedArray = undefinedArray.iterator();
      
        int neutralsundefined = 0, positivesundefined = 0, negativesundefined = 0;
        while (iundefinedArray.hasNext()) {
            PostIn postIn = (PostIn) iundefinedArray.next();
            if (postIn.getPostSentimentalType() == 0) {
                neutralsundefined++;
            } else if (postIn.getPostSentimentalType() == 1) {
                positivesundefined++;
            } else if (postIn.getPostSentimentalType() == 2) {
                negativesundefined++;
            }
        }
      
        float intTotalVotos = undefined + highSchool + college + graduate;

        //Positivo
      
        float intPorcentajehighSchool = ((float) highSchool * 100) / (float) intTotalVotos;

        //Negativo
       
        float intPorcentajecollege = ((float) college * 100) / (float) intTotalVotos;        

        //Neutro        
        float intPorcentajegraduate = ((float) graduate * 100) / (float) intTotalVotos;
    
        float intPorcentajeundefined = ((float) undefined * 100) / (float) intTotalVotos;

      
        JSONArray node = new JSONArray();


        if (highSchool > 0) {
            JSONObject node1 = new JSONObject();
            node1.put("label", SWBSocialUtil.Util.getStringFromGenericLocale("highSchool", lang));
            node1.put("value1", "" + highSchool);
            node1.put("value2", "" + round(intPorcentajehighSchool));
            if (positiveshighSchool > negativeshighSchool && positiveshighSchool > neutralshighSchool) {
                node1.put("color", "#86c440");
            } else if (negativeshighSchool > neutralshighSchool) {
                node1.put("color", "#990000");
            } else {
                node1.put("color", "#eae8e3");
            }
            node1.put("label2", SWBSocialUtil.Util.getStringFromGenericLocale("highSchool", lang)+" :" + highSchool +  " " + SWBSocialUtil.Util.getStringFromGenericLocale("positives", lang)+" :" + positiveshighSchool +  " " +SWBSocialUtil.Util.getStringFromGenericLocale("negatives", lang)+ " : " + negativeshighSchool + SWBSocialUtil.Util.getStringFromGenericLocale("neutral", lang)+"  :" + neutralshighSchool);

            node1.put("chartclass", "possClass");
            node.put(node1);
        }

        if (college > 0) {
            JSONObject node2 = new JSONObject();
            node2.put("label", SWBSocialUtil.Util.getStringFromGenericLocale("college", lang));
            node2.put("value1", "" + college);
            node2.put("value2", "" + round(intPorcentajecollege));
            if (positivescollege > negativescollege && positivescollege > neutralscollege) {
                node2.put("color", "#86c440");
            } else if (negativescollege > neutralscollege) {
                node2.put("color", "#990000");
            } else {
                node2.put("color", "#eae8e3");
            }
            node2.put("label2", SWBSocialUtil.Util.getStringFromGenericLocale("college", lang)+" :" + college +  " " +SWBSocialUtil.Util.getStringFromGenericLocale("positives", lang)+ " :" + positivescollege +  " " +  SWBSocialUtil.Util.getStringFromGenericLocale("negatives", lang)+" :" + negativescollege +  " " +SWBSocialUtil.Util.getStringFromGenericLocale("neutral", lang)+"  :" + neutralscollege);
            node2.put("chartclass", "possClass");
            node.put(node2);
        }

        if (graduate > 0) {
            JSONObject node3 = new JSONObject();
            node3.put("label", SWBSocialUtil.Util.getStringFromGenericLocale("graduate", lang));
            node3.put("value1", "" + graduate);
            node3.put("value2", "" + round(intPorcentajegraduate));
            if (positivesgraduate > negativesgraduate && positivesgraduate > neutralsgraduate) {
                node3.put("color", "#86c440");
            } else if (negativesgraduate > neutralsgraduate) {
                node3.put("color", "#990000");
            } else {
                node3.put("color", "#eae8e3");
            }
            node3.put("label2", SWBSocialUtil.Util.getStringFromGenericLocale("graduate", lang)+" :" + graduate + " " + SWBSocialUtil.Util.getStringFromGenericLocale("positives", lang)+ " :" + positivesgraduate  + " " + SWBSocialUtil.Util.getStringFromGenericLocale("negatives", lang)+ " : " + negativesgraduate +  " " + SWBSocialUtil.Util.getStringFromGenericLocale("neutral", lang)+"  :" + neutralsgraduate);
            node3.put("chartclass", "possClass");
            node.put(node3);
        }

        if (undefined > 0) {
            JSONObject node4 = new JSONObject();
            node4.put("label", SWBSocialUtil.Util.getStringFromGenericLocale("undefinedEducation", lang));
            node4.put("value1", "" + undefined);
            node4.put("value2", "" + round(intPorcentajeundefined));
            if (positivesundefined > negativesundefined && positivesundefined > neutralsundefined) {
                node4.put("color", "#86c440");
            } else if (negativesundefined > neutralsundefined) {
                node4.put("color", "#990000");
            } else {
                node4.put("color", "#eae8e3");
            }
            node4.put("label2", SWBSocialUtil.Util.getStringFromGenericLocale("undefinedEducation", lang)+" :" + undefined + " " + SWBSocialUtil.Util.getStringFromGenericLocale("positives", lang)+":" + positivesundefined  + " " + SWBSocialUtil.Util.getStringFromGenericLocale("negatives", lang)+" :" + negativesundefined  +  " " + SWBSocialUtil.Util.getStringFromGenericLocale("neutral", lang)+ " :" + neutralsundefined);
            node4.put("chartclass", "possClass");
            node.put(node4);
        }
        
         if(highSchool == 0 && college == 0 &&  graduate ==0 && undefined==0 ){
                   
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