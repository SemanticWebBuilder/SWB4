<%-- 
    Document   : pieLifeStage
    Created on : 03-oct-2013, 19:51:58
    Author     : gabriela.rosales
--%>

<%@page import="org.semanticwb.social.admin.resources.util.SWBSocialResUtil"%>
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
    JSONArray getObject(SemanticObject semObj, String lang, String filter) throws Exception {

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
            } else if (postIn.getPostInSocialNetworkUser().getSnu_education() == SocialNetworkUser.USER_EDUCATION_COLLEGE) {
                college++;
                collegeArray.add(postIn);
            } else if (postIn.getPostInSocialNetworkUser().getSnu_education() == SocialNetworkUser.USER_EDUCATION_GRADUATE) {
                graduate++;
                graduateArray.add(postIn);
            } else if (postIn.getPostInSocialNetworkUser().getSnu_education() == SocialNetworkUser.USER_EDUCATION_UNDEFINED || postIn.getPostInSocialNetworkUser().getSnu_education() == 0) {
                undefined++;
                undefinedArray.add(postIn);
            }



        }


        Iterator ihighSchool = highSchoolArray.iterator();
        int neutralshighSchool = 0, positiveshighSchool = 0, negativeshighSchool = 0, totalPosthighSchool = 0;
        while (ihighSchool.hasNext()) {
            PostIn postIn = (PostIn) ihighSchool.next();
            totalPosthighSchool++;
            if (postIn.getPostSentimentalType() == 0) {
                neutralshighSchool++;
            } else if (postIn.getPostSentimentalType() == 1) {
                positiveshighSchool++;
            } else if (postIn.getPostSentimentalType() == 2) {
                negativeshighSchool++;
            }
        }

        Iterator icollegeArray = collegeArray.iterator();
        int neutralscollege = 0, positivescollege = 0, negativescollege = 0, totalPostcollege = 0;
        while (icollegeArray.hasNext()) {
            PostIn postIn = (PostIn) icollegeArray.next();
            totalPostcollege++;
            if (postIn.getPostSentimentalType() == 0) {
                neutralscollege++;
            } else if (postIn.getPostSentimentalType() == 1) {
                positivescollege++;
            } else if (postIn.getPostSentimentalType() == 2) {
                negativescollege++;
            }
        }

        Iterator igraduateArray = graduateArray.iterator();
        int neutralsgraduate = 0, positivesgraduate = 0, negativesgraduate = 0, totalPostgraduate = 0;
        while (igraduateArray.hasNext()) {
            PostIn postIn = (PostIn) igraduateArray.next();
            totalPostgraduate++;
            if (postIn.getPostSentimentalType() == 0) {
                neutralsgraduate++;
            } else if (postIn.getPostSentimentalType() == 1) {
                positivesgraduate++;
            } else if (postIn.getPostSentimentalType() == 2) {
                negativesgraduate++;
            }
        }

        Iterator iundefinedArray = undefinedArray.iterator();
        int neutralsundefined = 0, positivesundefined = 0, negativesundefined = 0, totalPostundefine = 0;
        while (iundefinedArray.hasNext()) {
            PostIn postIn = (PostIn) iundefinedArray.next();
            totalPostundefine++;
            if (postIn.getPostSentimentalType() == 0) {
                neutralsundefined++;
            } else if (postIn.getPostSentimentalType() == 1) {
                positivesundefined++;
            } else if (postIn.getPostSentimentalType() == 2) {
                negativesundefined++;
            }
        }

        float intTotalVotos = undefined + highSchool + college + graduate;
        float intPorcentajehighSchool = 0;
        float intPorcentajecollege = ((float) college * 100) / (float) totalPost;
        float intPorcentajegraduate = 0;
        float intPorcentajeundefined = 0;
        if (totalPost != 0) {
            intPorcentajehighSchool = ((float) highSchool * 100) / (float) totalPost;
            intPorcentajecollege = ((float) college * 100) / (float) totalPost;
            intPorcentajegraduate = ((float) graduate * 100) / (float) totalPost;
            intPorcentajeundefined = ((float) undefined * 100) / (float) totalPost;
        }

        //System.out.println("neutralshighSchool" + neutralshighSchool);
        //System.out.println("totalPosthighSchool" + totalPosthighSchool);
        int totalPositives = positivescollege + positivesgraduate + positiveshighSchool + positivesundefined;
        int totalNegatives = negativescollege + negativesgraduate + negativeshighSchool + negativesundefined;
        int totalNeutrals = negativescollege + neutralsgraduate + neutralshighSchool + neutralsundefined;



        JSONArray node = new JSONArray();


        if (filter.equals("all")) {            
            
                         // if (highSchool > 0) {
            JSONObject node1 = new JSONObject();
            node1.put("label", SWBSocialResUtil.Util.getStringFromGenericLocale("highSchool", lang));
            node1.put("value1", "" + highSchool);
            node1.put("value2", "" + round(intPorcentajehighSchool));
            JSONObject jor = new JSONObject();
            jor.put("positivos", "" + positiveshighSchool);
            jor.put("negativos", "" + negativeshighSchool);
            jor.put("neutros", "" + neutralshighSchool);
            node1.put("valor", jor);
            if (positiveshighSchool > negativeshighSchool && positiveshighSchool > neutralshighSchool) {
                node1.put("color", "#008000");
            } else if (negativeshighSchool > neutralshighSchool) {
                node1.put("color", "#FF0000");
            } else {
                node1.put("color", "#FFD700");
            }
            node1.put("label2", SWBSocialResUtil.Util.getStringFromGenericLocale("highSchool", lang) + " :" + highSchool + " " + SWBSocialResUtil.Util.getStringFromGenericLocale("positives", lang) + " :" + positiveshighSchool + " " + SWBSocialResUtil.Util.getStringFromGenericLocale("negatives", lang) + " : " + negativeshighSchool + SWBSocialResUtil.Util.getStringFromGenericLocale("neutral", lang) + "  :" + neutralshighSchool);
            JSONObject joTotal = new JSONObject();
            joTotal.put("positivos", "" + totalPositives);
            joTotal.put("negativos", "" + totalNegatives);
            joTotal.put("neutros", "" + totalNeutrals);
            node1.put("label3", joTotal);
            node1.put("chartclass", "possClass");
            node.put(node1);
            // }

            // if (college > 0) {
            JSONObject node2 = new JSONObject();
            node2.put("label", SWBSocialResUtil.Util.getStringFromGenericLocale("college", lang));
            node2.put("value1", "" + college);
            node2.put("value2", "" + round(intPorcentajecollege));
            JSONObject joCollege = new JSONObject();
            joCollege.put("positivos", "" + positivescollege);
            joCollege.put("negativos", "" + negativescollege);
            joCollege.put("neutros", "" + neutralscollege);
            node2.put("valor", joCollege);
            if (positivescollege > negativescollege && positivescollege > neutralscollege) {
                node2.put("color", "#008000");
            } else if (negativescollege > neutralscollege) {
                node2.put("color", "#FF0000");
            } else {
                node2.put("color", "#FFD700");
            }
            node2.put("label2", SWBSocialResUtil.Util.getStringFromGenericLocale("college", lang) + " :" + college + " " + SWBSocialResUtil.Util.getStringFromGenericLocale("positives", lang) + " :" + positivescollege + " " + SWBSocialResUtil.Util.getStringFromGenericLocale("negatives", lang) + " :" + negativescollege + " " + SWBSocialResUtil.Util.getStringFromGenericLocale("neutral", lang) + "  :" + neutralscollege);
            node2.put("chartclass", "possClass");
            node2.put("label3", "Total de Post: " + totalPost);

            node.put(node2);
            // }

            // if (graduate > 0) {
            JSONObject node3 = new JSONObject();
            node3.put("label", SWBSocialResUtil.Util.getStringFromGenericLocale("graduate", lang));
            node3.put("value1", "" + graduate);
            node3.put("value2", "" + round(intPorcentajegraduate));
            JSONObject joGraduate = new JSONObject();
            joGraduate.put("positivos", "" + positivesgraduate);
            joGraduate.put("negativos", "" + negativesgraduate);
            joGraduate.put("neutros", "" + neutralsgraduate);
            node3.put("valor", joGraduate);

            if (positivesgraduate > negativesgraduate && positivesgraduate > neutralsgraduate) {
                node3.put("color", "#008000");
            } else if (negativesgraduate > neutralsgraduate) {
                node3.put("color", "#FF0000");
            } else {
                node3.put("color", "#FFD700");
            }
            node3.put("label2", SWBSocialResUtil.Util.getStringFromGenericLocale("graduate", lang) + " :" + graduate + " " + SWBSocialResUtil.Util.getStringFromGenericLocale("positives", lang) + " :" + positivesgraduate + " " + SWBSocialResUtil.Util.getStringFromGenericLocale("negatives", lang) + " : " + negativesgraduate + " " + SWBSocialResUtil.Util.getStringFromGenericLocale("neutral", lang) + "  :" + neutralsgraduate);
            node3.put("chartclass", "possClass");
            node3.put("label3", "Total de Post: " + totalPost);

            node.put(node3);
            // }

            // if (undefined > 0) {
            JSONObject node4 = new JSONObject();
            node4.put("label", SWBSocialResUtil.Util.getStringFromGenericLocale("undefinedEducation", lang));
            node4.put("value1", "" + undefined);
            node4.put("value2", "" + round(intPorcentajeundefined));
            JSONObject joUndefine = new JSONObject();
            joUndefine.put("positivos", "" + positivesundefined);
            joUndefine.put("negativos", "" + negativesundefined);
            joUndefine.put("neutros", "" + neutralsundefined);
            node4.put("valor", joUndefine);
            if (positivesundefined > negativesundefined && positivesundefined > neutralsundefined) {
                node4.put("color", "#008000");
            } else if (negativesundefined > neutralsundefined) {
                node4.put("color", "#FF0000");
            } else {
                node4.put("color", "#FFD700");
            }
            node4.put("label2", SWBSocialResUtil.Util.getStringFromGenericLocale("undefinedEducation", lang) + " :" + undefined + " " + SWBSocialResUtil.Util.getStringFromGenericLocale("positives", lang) + ":" + positivesundefined + " " + SWBSocialResUtil.Util.getStringFromGenericLocale("negatives", lang) + " :" + negativesundefined + " " + SWBSocialResUtil.Util.getStringFromGenericLocale("neutral", lang) + " :" + neutralsundefined);
            node4.put("chartclass", "possClass");
            node4.put("label3", "Total de Post: " + totalPost);

            node.put(node4);
            //  }

        } else if (filter.equals("secundaria")) {

            float intPorcentajehighSchoolNeutrals = 0;
            float intPorcentajehighSchoolPositives = 0;
            float intPorcentajehighSchoolNegatives = 0;


            if (totalPosthighSchool != 0) {
                intPorcentajehighSchoolNeutrals = ((float) neutralshighSchool * 100) / (float) totalPosthighSchool;
                intPorcentajehighSchoolPositives = ((float) positiveshighSchool * 100) / (float) totalPosthighSchool;
                intPorcentajehighSchoolNegatives = ((float) negativeshighSchool * 100) / (float) totalPosthighSchool;
            }

            //System.out.println("Entro en  secundaria");
            //System.out.println("-->" + intPorcentajehighSchoolNeutrals);
            //System.out.println("-->" + round(intPorcentajehighSchoolNeutrals));
            if (negativeshighSchool == 0 && positiveshighSchool == 0 && neutralshighSchool == 0) {
                JSONObject node3 = new JSONObject();
                node3.put("label", "Sin Datos");
                node3.put("value1", "0");
                node3.put("value2", "100");
                node3.put("color", "#E6E6E6");
                node3.put("chartclass", "neuClass");
                 JSONObject jor = new JSONObject();
                jor.put("positivos", "" + totalPositives);
                jor.put("negativos", "" + totalNegatives);
                jor.put("neutros", "" + totalNeutrals);
                node3.put("valor", jor);
                node.put(node3);
                return node;
            }


            if (neutralshighSchool > 0) {
                JSONObject node4 = new JSONObject();
                node4.put("label", "Neutros");
                node4.put("value1", "" + neutralshighSchool);
                node4.put("value2", "" + round(intPorcentajehighSchoolNeutrals));
                node4.put("color", "#FFD700");
                node4.put("label2", "");
                node4.put("chartclass", "possClass");
                node4.put("label3", "Total de Post: " + totalPost);
                node.put(node4);
            }

            if (positiveshighSchool > 0) {
                JSONObject node5 = new JSONObject();
                node5.put("label", "Positivos");
                node5.put("value1", "" + positiveshighSchool);
                node5.put("value2", "" + round(intPorcentajehighSchoolPositives));
                node5.put("color", "#008000");
                node5.put("label2", "");
                node5.put("chartclass", "possClass");
                node5.put("label3", "Total de Post: " + totalPost);
                node.put(node5);
            }

            if (negativeshighSchool > 0) {
                JSONObject node6 = new JSONObject();
                node6.put("label", "Negativos");
                node6.put("value1", "" + negativeshighSchool);
                node6.put("value2", "" + round(intPorcentajehighSchoolNegatives));
                node6.put("color", "#FF0000");
                node6.put("label2", "");
                node6.put("chartclass", "possClass");
                node6.put("label3", "Total de Post: " + totalPost);
                node.put(node6);
            }


        } else if (filter.equals("mediosuperior")) {

            //System.out.println("Entro en  mediosuperior");
            float intPorcentajecollegeNeutrals = 0;
            float intPorcentajecollegePositives = 0;
            float intPorcentajecollegeNegatives = 0;

            if (totalPostcollege != 0) {
                intPorcentajecollegeNeutrals = ((float) neutralscollege * 100) / (float) totalPostcollege;
                intPorcentajecollegePositives = ((float) positivescollege * 100) / (float) totalPostcollege;
                intPorcentajecollegeNegatives = ((float) negativescollege * 100) / (float) totalPostcollege;

            }

            if (neutralscollege == 0 && positivescollege == 0 && negativescollege == 0) {
                JSONObject node4 = new JSONObject();
                node4.put("label", "Sin Datos");
                node4.put("value1", "0");
                node4.put("value2", "100");
                node4.put("color", "#E6E6E6");
                node4.put("chartclass", "neuClass");
                node.put(node4);
            }

            if (neutralscollege > 0) {
                JSONObject node4 = new JSONObject();
                node4.put("label", "Neutros");
                node4.put("value1", "" + neutralscollege);
                node4.put("value2", "" + round(intPorcentajecollegeNeutrals));
                node4.put("color", "#FFD700");
                node4.put("label2", "");
                node4.put("chartclass", "possClass");
                node4.put("label3", "Total de Post: " + totalPost);
                node.put(node4);
            }

            if (positivescollege > 0) {
                JSONObject node5 = new JSONObject();
                node5.put("label", "Positivos");
                node5.put("value1", "" + positivescollege);
                node5.put("value2", "" + round(intPorcentajecollegePositives));
                node5.put("color", "#008000");
                node5.put("label2", "");
                node5.put("chartclass", "possClass");
                node5.put("label3", "Total de Post: " + totalPost);
                node.put(node5);
            }

            if (negativescollege > 0) {
                JSONObject node6 = new JSONObject();
                node6.put("label", "Negativos");
                node6.put("value1", "" + negativescollege);
                node6.put("value2", "" + round(intPorcentajecollegeNegatives));
                node6.put("color", "#FF0000");
                node6.put("label2", "");
                node6.put("chartclass", "possClass");
                node6.put("label3", "Total de Post: " + totalPost);
                node.put(node6);
            }



        } else if (filter.equals("graduado")) {


            //  System.out.println("Entro en  graduado");


            float intPorcentajegraduateNeutrals = 0;
            float intPorcentajegraduatePositives = 0;
            float intPorcentajegraduateNegatives = 0;

            if (totalPostgraduate != 0) {
                intPorcentajegraduateNeutrals = ((float) neutralsgraduate * 100) / (float) totalPostgraduate;
                intPorcentajegraduatePositives = ((float) positivesgraduate * 100) / (float) totalPostgraduate;
                intPorcentajegraduateNegatives = ((float) negativesgraduate * 100) / (float) totalPostgraduate;
            }

            if (neutralsgraduate == 0 && positivesgraduate == 0 && negativesgraduate == 0) {
                JSONObject node4 = new JSONObject();
                node4.put("label", "Sin Datos");
                node4.put("value1", "0");
                node4.put("value2", "100");
                node4.put("color", "#E6E6E6");
                node4.put("chartclass", "neuClass");
                node.put(node4);
            }

            if (neutralsgraduate > 0) {
                JSONObject node4 = new JSONObject();
                node4.put("label", "Neutros");
                node4.put("value1", "" + neutralsgraduate);
                node4.put("value2", "" + round(intPorcentajegraduateNeutrals));
                node4.put("label2", "");
                node4.put("color", "#FFD700");
                node4.put("chartclass", "possClass");
                node4.put("label3", "Total de Post: " + totalPost);
                node.put(node4);
            }

            if (positivesgraduate > 0) {
                JSONObject node5 = new JSONObject();
                node5.put("label", "Positivos");
                node5.put("value1", "" + positivesgraduate);
                node5.put("value2", "" + round(intPorcentajegraduatePositives));
                node5.put("color", "#008000");
                node5.put("label2", "");
                node5.put("chartclass", "possClass");
                node5.put("label3", "Total de Post: " + totalPost);
                node.put(node5);
            }

            if (negativesgraduate > 0) {
                JSONObject node6 = new JSONObject();
                node6.put("label", "Negativos");
                node6.put("value1", "" + negativesgraduate);
                node6.put("value2", "" + round(intPorcentajegraduateNegatives));
                node6.put("color", "#FF0000");
                node6.put("label2", "");
                node6.put("chartclass", "possClass");
                node6.put("label3", "Total de Post: " + totalPost);
                node.put(node6);
            }



        } else if (filter.equals("undefined")) {


            // System.out.println("Entro en  undefine");
            float intPorcentajeundefineNeutrals = 0;
            float intPorcentajeundefinePositives = 0;
            float intPorcentajeundefineNegatives = 0;

            if (totalPostundefine != 0) {
                intPorcentajeundefineNeutrals = ((float) neutralsundefined * 100) / (float) totalPostundefine;
                intPorcentajeundefinePositives = ((float) positivesundefined * 100) / (float) totalPostundefine;
                intPorcentajeundefineNegatives = ((float) negativesundefined * 100) / (float) totalPostundefine;
            }

            if (neutralsundefined == 0 && positivesundefined == 0 && negativesundefined == 0) {
                JSONObject node4 = new JSONObject();
                node4.put("label", "Sin Datos");
                node4.put("value1", "0");
                node4.put("value2", "100");
                node4.put("color", "#E6E6E6");
                node4.put("chartclass", "neuClass");
                node.put(node4);
            }

            if (neutralsundefined > 0) {
                JSONObject node4 = new JSONObject();
                node4.put("label", "Neutros");
                node4.put("value1", "" + neutralsundefined);
                node4.put("value2", "" + round(intPorcentajeundefineNeutrals));
                node4.put("color", "#FFD700");
                node4.put("label2", "");
                node4.put("chartclass", "possClass");
                node4.put("label3", "Total de Post: " + totalPost);
                node.put(node4);
            }

            if (positivesundefined > 0) {
                JSONObject node5 = new JSONObject();
                node5.put("label", "Positivos");
                node5.put("value1", "" + positivesundefined);
                node5.put("value2", "" + round(intPorcentajeundefinePositives));
                node5.put("color", "#008000");
                node5.put("label2", "");
                node5.put("chartclass", "possClass");
                node5.put("label3", "Total de Post: " + totalPost);
                node.put(node5);
            }

            if (negativesundefined > 0) {
                JSONObject node6 = new JSONObject();
                node6.put("label", "Negativos");
                node6.put("value1", "" + negativesundefined);
                node6.put("value2", "" + round(intPorcentajeundefineNegatives));
                node6.put("color", "#FF0000");
                node6.put("label2", "");
                node6.put("chartclass", "possClass");
                node6.put("label3", "Total de Post: " + totalPost);
                node.put(node6);
            }



        } 
        //System.out.println("node en education \n" + node + "\n");
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
        String filter = request.getParameter("filter");
        //System.out.println("filter pieducation: "+filter);
        out.println(getObject(semObj, lang, filter));
    }
%>