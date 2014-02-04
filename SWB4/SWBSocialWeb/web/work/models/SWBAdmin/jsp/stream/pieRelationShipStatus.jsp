<%-- 
    Document   : pieLifeStage
    Created on : 03-oct-2013, 19:51:58
    Author     : gabriela.rosales
--%>
<%@page import="org.semanticwb.social.admin.resources.util.SWBSocialResUtil"%>
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
    JSONArray getObject(SemanticObject semObj, String lang, String filter) throws Exception {

        int single = 0, married = 0, divorced = 0, widowed = 0, undefined = 0, totalPost = 0;
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
            totalPost++;

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
            } else if (postIn.getPostInSocialNetworkUser().getSnu_relationShipStatus() == SocialNetworkUser.USER_RELATION_UNDEFINED || postIn.getPostInSocialNetworkUser().getSnu_relationShipStatus() == 0) {
                undefined++;
                undefinedArray.add(postIn);
            }
        }



        Iterator singleI = singleArray.iterator();
        int neutralsSingle = 0, positivesSingle = 0, negativesSingle = 0, totalPostSingle = 0;
        while (singleI.hasNext()) {
            PostIn pi = (PostIn) singleI.next();
            totalPostSingle++;
            if (pi.getPostSentimentalType() == 0) {
                neutralsSingle++;
            } else if (pi.getPostSentimentalType() == 1) {
                positivesSingle++;
            } else if (pi.getPostSentimentalType() == 2) {
                negativesSingle++;
            }
        }




        Iterator marriedI = marriedArray.iterator();
        int neutralsMarried = 0, positivesMarried = 0, negativesMarried = 0, totalPostMarried = 0;
        while (marriedI.hasNext()) {
            PostIn pi = (PostIn) marriedI.next();
            totalPostMarried++;
            if (pi.getPostSentimentalType() == 0) {
                neutralsMarried++;
            } else if (pi.getPostSentimentalType() == 1) {
                positivesMarried++;
            } else if (pi.getPostSentimentalType() == 2) {
                negativesMarried++;
            }
        }


        Iterator divorcedI = divorcedArray.iterator();
        int neutralsDivorced = 0, positivesDivorced = 0, negativesDivorced = 0, totalPostDivorced = 0;
        while (divorcedI.hasNext()) {
            PostIn pi = (PostIn) divorcedI.next();
            totalPostDivorced++;
            if (pi.getPostSentimentalType() == 0) {
                neutralsDivorced++;
            } else if (pi.getPostSentimentalType() == 1) {
                positivesDivorced++;
            } else if (pi.getPostSentimentalType() == 2) {
                negativesDivorced++;
            }
        }



        Iterator widowedI = widowedArray.iterator();
        int neutralsWidowed = 0, positivesWidowed = 0, negativesWidowed = 0, totalPostWidowed = 0;
        while (widowedI.hasNext()) {
            PostIn pi = (PostIn) widowedI.next();
            totalPostWidowed++;
            if (pi.getPostSentimentalType() == 0) {
                neutralsWidowed++;
            } else if (pi.getPostSentimentalType() == 1) {
                positivesWidowed++;
            } else if (pi.getPostSentimentalType() == 2) {
                negativesWidowed++;
            }
        }


        Iterator undefinedI = undefinedArray.iterator();
        int neutralsUndefined = 0, positivesUndefined = 0, negativesUndefined = 0, totalPostUndefined = 0;
        while (undefinedI.hasNext()) {
            PostIn pi = (PostIn) undefinedI.next();
            totalPostUndefined++;
            if (pi.getPostSentimentalType() == 0) {
                neutralsUndefined++;
            } else if (pi.getPostSentimentalType() == 1) {
                positivesUndefined++;
            } else if (pi.getPostSentimentalType() == 2) {
                negativesUndefined++;
            }
        }



        float intTotalVotos = undefined + single + married + divorced + widowed;
        float intPorcentajeSingle = 0;
        float intPorcentajeMarried = 0;
        float intPorcentajeDivorced = 0;
        float intPorcentajeWidowed = 0;
        float intPorcentajeundefined = 0;
        if (totalPost != 0) {
            intPorcentajeSingle = ((float) single * 100) / (float) totalPost;
            intPorcentajeMarried = ((float) married * 100) / (float) totalPost;
            intPorcentajeDivorced = ((float) divorced * 100) / (float) totalPost;
            intPorcentajeWidowed = ((float) widowed * 100) / (float) totalPost;
            intPorcentajeundefined = ((float) undefined * 100) / (float) totalPost;
        }

        int totalPositives = positivesDivorced + positivesMarried + positivesSingle + positivesUndefined + positivesWidowed;
        int totalNegatives = negativesDivorced + negativesMarried + negativesSingle + negativesUndefined + negativesWidowed;
        int totalNeutrals = neutralsDivorced + neutralsMarried + neutralsSingle + neutralsUndefined + neutralsWidowed;


        JSONArray node = new JSONArray();

        if (filter.equals("all")) {

            if (single == 0 && married == 0 && divorced == 0 && widowed == 0 && undefined == 0) {

                JSONObject node4 = new JSONObject();
                node4.put("label", "Sin Datos");
                node4.put("value1", "0");
                node4.put("value2", "100");
                node4.put("color", "#E6E6E6");
                node4.put("chartclass", "neuClass");
                JSONObject jor = new JSONObject();
                jor.put("positivos", "" + totalPositives);
                jor.put("negativos", "" + totalNegatives);
                jor.put("neutros", "" + totalNeutrals);
                node4.put("valor", jor);
                node.put(node4);
                return node;

            }

            //  if (single > 0) {
            JSONObject node1 = new JSONObject();
            node1.put("label", SWBSocialResUtil.Util.getStringFromGenericLocale("single", lang));
            node1.put("value1", "" + single);
            node1.put("value2", "" + round(intPorcentajeSingle));
            JSONObject joUndefine = new JSONObject();
            joUndefine.put("positivos", "" + positivesSingle);
            joUndefine.put("negativos", "" + negativesSingle);
            joUndefine.put("neutros", "" + neutralsSingle);
            node1.put("valor", joUndefine);
            if (positivesSingle > negativesSingle && positivesSingle > neutralsSingle) {
                node1.put("color", "#008000");
            } else if (negativesSingle > neutralsSingle) {
                node1.put("color", "#FF0000");
            } else {
                node1.put("color", "#FFD700");
            }
            node1.put("label2", SWBSocialResUtil.Util.getStringFromGenericLocale("single", lang) + ": " + single + " " + SWBSocialResUtil.Util.getStringFromGenericLocale("positives", lang) + " : " + positivesSingle + " " + SWBSocialResUtil.Util.getStringFromGenericLocale("negatives", lang) + " : " + negativesSingle + " " + SWBSocialResUtil.Util.getStringFromGenericLocale("neutral", lang) + " : " + neutralsSingle);
            node1.put("chartclass", "possClass");
            JSONObject joTotal = new JSONObject();
            joTotal.put("positivos", "" + totalPositives);
            joTotal.put("negativos", "" + totalNegatives);
            joTotal.put("neutros", "" + totalNeutrals);
            node1.put("label3", joTotal);

            node.put(node1);
            //}

            // if (married > 0) {
            JSONObject node2 = new JSONObject();
            node2.put("label", SWBSocialResUtil.Util.getStringFromGenericLocale("married", lang));
            node2.put("value1", "" + married);
            node2.put("value2", "" + round(intPorcentajeMarried));
            JSONObject joMArried = new JSONObject();
            joMArried.put("positivos", "" + positivesMarried);
            joMArried.put("negativos", "" + negativesMarried);
            joMArried.put("neutros", "" + neutralsMarried);
            node2.put("valor", joMArried);
            if (positivesMarried > negativesMarried && positivesMarried > neutralsMarried) {
                node2.put("color", "#008000");
            } else if (negativesMarried > neutralsMarried) {
                node2.put("color", "#FF0000");
            } else {
                node2.put("color", "#FFD700");
            }
            node2.put("label2", SWBSocialResUtil.Util.getStringFromGenericLocale("married", lang) + ": " + married + " " + SWBSocialResUtil.Util.getStringFromGenericLocale("positives", lang) + "  : " + positivesMarried + " " + SWBSocialResUtil.Util.getStringFromGenericLocale("negatives", lang) + " : " + negativesMarried + " " + SWBSocialResUtil.Util.getStringFromGenericLocale("neutral", lang) + " : " + neutralsMarried);
            node2.put("chartclass", "possClass");
            node2.put("label3", "Total de Post: " + totalPost);

            node.put(node2);
            // }


            // if (widowed > 0) {
            JSONObject node4 = new JSONObject();
            node4.put("label", SWBSocialResUtil.Util.getStringFromGenericLocale("widowed", lang));
            node4.put("value1", "" + widowed);
            node4.put("value2", "" + round(intPorcentajeWidowed));
            JSONObject joWidowed = new JSONObject();
            joWidowed.put("positivos", "" + positivesWidowed);
            joWidowed.put("negativos", "" + negativesWidowed);
            joWidowed.put("neutros", "" + neutralsWidowed);
            node4.put("valor", joWidowed);
            if (positivesWidowed > negativesWidowed && positivesWidowed > neutralsWidowed) {
                node4.put("color", "#008000");
            } else if (negativesWidowed > neutralsWidowed) {
                node4.put("color", "#FF0000");
            } else {
                node4.put("color", "#FFD700");
            }
            node4.put("label2", SWBSocialResUtil.Util.getStringFromGenericLocale("widowed", lang) + ": " + widowed + " " + SWBSocialResUtil.Util.getStringFromGenericLocale("positives", lang) + " : " + positivesWidowed + " " + SWBSocialResUtil.Util.getStringFromGenericLocale("negatives", lang) + " : " + negativesWidowed + " " + SWBSocialResUtil.Util.getStringFromGenericLocale("neutral", lang) + " : " + neutralsWidowed);
            node4.put("chartclass", "possClass");
            node4.put("label3", "Total de Post: " + totalPost);
            node.put(node4);
            //}

            //if (divorced > 0) {
            JSONObject node3 = new JSONObject();
            node3.put("label", SWBSocialResUtil.Util.getStringFromGenericLocale("divorced", lang));
            node3.put("value1", "" + divorced);
            node3.put("value2", "" + round(intPorcentajeDivorced));
            JSONObject joDivorced = new JSONObject();
            joDivorced.put("positivos", "" + positivesDivorced);
            joDivorced.put("negativos", "" + negativesDivorced);
            joDivorced.put("neutros", "" + neutralsDivorced);
            node3.put("valor", joDivorced);
            if (positivesDivorced > negativesDivorced && positivesDivorced > neutralsDivorced) {
                node3.put("color", "#008000");
            } else if (negativesDivorced > neutralsDivorced) {
                node3.put("color", "#FF0000");
            } else {
                node3.put("color", "#FFD700");
            }
            node3.put("label2", SWBSocialResUtil.Util.getStringFromGenericLocale("divorced", lang) + ": " + divorced + " " + SWBSocialResUtil.Util.getStringFromGenericLocale("positives", lang) + "  : " + positivesDivorced + " " + SWBSocialResUtil.Util.getStringFromGenericLocale("negatives", lang) + " : " + negativesDivorced + " " + SWBSocialResUtil.Util.getStringFromGenericLocale("neutral", lang) + " : " + neutralsDivorced);
            node3.put("chartclass", "possClass");
            node3.put("label3", "Total de Post: " + totalPost);

            node.put(node3);
            // }



            // if (undefined > 0) {
            JSONObject node5 = new JSONObject();
            node5.put("label", SWBSocialResUtil.Util.getStringFromGenericLocale("undefinedRelation", lang));
            node5.put("value1", "" + undefined);
            node5.put("value2", "" + round(intPorcentajeundefined));
            JSONObject joUndefined = new JSONObject();
            joUndefined.put("positivos", "" + positivesUndefined);
            joUndefined.put("negativos", "" + negativesUndefined);
            joUndefined.put("neutros", "" + neutralsUndefined);
            node5.put("valor", joUndefined);
            if (positivesUndefined > negativesUndefined && positivesUndefined > neutralsUndefined) {
                node5.put("color", "#008000");
            } else if (negativesUndefined > neutralsUndefined) {
                node5.put("color", "#FF0000");
            } else {
                node5.put("color", "#FFD700");
            }
            node5.put("label2", SWBSocialResUtil.Util.getStringFromGenericLocale("undefinedRelation", lang) + ": " + undefined + " " + SWBSocialResUtil.Util.getStringFromGenericLocale("positives", lang) + "  : " + positivesUndefined + " " + SWBSocialResUtil.Util.getStringFromGenericLocale("negatives", lang) + " : " + negativesUndefined + " " + SWBSocialResUtil.Util.getStringFromGenericLocale("neutral", lang) + " : " + neutralsUndefined);
            node5.put("chartclass", "possClass");
            node5.put("label3", "Total de Post: " + totalPost);
            node.put(node5);
            //}

        } else if (filter.equals("single")) {

            float intPorcentajeSingleNeutrals = 0;
            float intPorcentajeSinglePositives = 0;
            float intPorcentajeSingleNegatives = 0;

            if (positivesSingle == 0 && negativesSingle == 0 && neutralsSingle == 0) {
                JSONObject node3 = new JSONObject();
                node3.put("label", "Sin Datos");
                node3.put("value1", "0");
                node3.put("value2", "100");
                node3.put("color", "#E6E6E6");
                node3.put("chartclass", "neuClass");
                node.put(node3);
                return node;
            }


            if (totalPostSingle != 0) {
                intPorcentajeSingleNeutrals = ((float) neutralsSingle * 100) / (float) totalPostSingle;
                intPorcentajeSinglePositives = ((float) positivesSingle * 100) / (float) totalPostSingle;
                intPorcentajeSingleNegatives = ((float) negativesSingle * 100) / (float) totalPostSingle;
            }

            if (positivesSingle > 0) {
                JSONObject node1 = new JSONObject();
                node1.put("label", "Positivos");
                node1.put("value1", "" + positivesSingle);
                node1.put("value2", "" + round(intPorcentajeSinglePositives));
                node1.put("color", "#008000");
                node1.put("label2", "");
                node1.put("chartclass", "possClass");
                node1.put("label3", "Total de Post: " + totalPost);
                node.put(node1);
            }

            if (negativesSingle > 0) {
                JSONObject node2 = new JSONObject();
                node2.put("label", "Negativos");
                node2.put("value1", "" + negativesSingle);
                node2.put("value2", "" + round(intPorcentajeSingleNegatives));
                node2.put("color", "#FF0000");
                node2.put("label2", "");
                node2.put("chartclass", "possClass");
                node2.put("label3", "Total de Post: " + totalPost);
                node.put(node2);
            }

            if (neutralsSingle > 0) {
                JSONObject node3 = new JSONObject();
                node3.put("label", "Neutros");
                node3.put("value1", "" + neutralsSingle);
                node3.put("value2", "" + round(intPorcentajeSingleNeutrals));
                node3.put("color", "#FFD700");
                node3.put("label2", "");
                node3.put("chartclass", "possClass");
                node3.put("label3", "Total de Post: " + totalPost);
                node.put(node3);
            }


        } else if (filter.equals("married")) {

            if (positivesMarried == 0 && negativesMarried == 0 && neutralsMarried == 0) {
                JSONObject node3 = new JSONObject();
                node3.put("label", "Sin Datos");
                node3.put("value1", "0");
                node3.put("value2", "100");
                node3.put("color", "#E6E6E6");
                node3.put("chartclass", "neuClass");
                node.put(node3);
                return node;
            }

            float intPorcentajeMarriedNeutrals = 0;
            float intPorcentajeMarriedPositives = 0;
            float intPorcentajeMarriedNegatives = 0;

            if (totalPostMarried != 0) {
                intPorcentajeMarriedNeutrals = ((float) neutralsMarried * 100) / (float) totalPostMarried;
                intPorcentajeMarriedPositives = ((float) positivesMarried * 100) / (float) totalPostMarried;
                intPorcentajeMarriedNegatives = ((float) negativesMarried * 100) / (float) totalPostMarried;
            }

            if (positivesMarried > 0) {
                JSONObject node1 = new JSONObject();
                node1.put("label", "Positivos");
                node1.put("value1", "" + positivesMarried);
                node1.put("value2", "" + round(intPorcentajeMarriedPositives));
                node1.put("color", "#008000");
                node1.put("label2", "");
                node1.put("chartclass", "possClass");
                node1.put("label3", "Total de Post: " + totalPost);
                node.put(node1);
            }

            if (negativesMarried > 0) {
                JSONObject node2 = new JSONObject();
                node2.put("label", "Negativos");
                node2.put("value1", "" + negativesMarried);
                node2.put("value2", "" + round(intPorcentajeMarriedNegatives));
                node2.put("color", "#FF0000");
                node2.put("label2", "");
                node2.put("chartclass", "possClass");
                node2.put("label3", "Total de Post: " + totalPost);
                node.put(node2);
            }

            if (neutralsMarried > 0) {
                JSONObject node3 = new JSONObject();
                node3.put("label", "Neutros");
                node3.put("value1", "" + neutralsMarried);
                node3.put("value2", "" + round(intPorcentajeMarriedNeutrals));
                node3.put("color", "#FFD700");
                node3.put("label2", "");
                node3.put("chartclass", "possClass");
                node3.put("label3", "Total de Post: " + totalPost);
                node.put(node3);
            }

        } else if (filter.equals("divorced")) {

            if (positivesDivorced == 0 && negativesDivorced == 0 && neutralsDivorced == 0) {
                JSONObject node3 = new JSONObject();
                node3.put("label", "Sin Datos");
                node3.put("value1", "0");
                node3.put("value2", "100");
                node3.put("color", "#E6E6E6");
                node3.put("chartclass", "neuClass");
                node.put(node3);
                return node;
            }

            float intPorcentajeDivorcedNeutrals = 0;
            float intPorcentajeDivorcedPositives = 0;
            float intPorcentajeDivorcedNegatives = 0;

            if (totalPostDivorced != 0) {
                intPorcentajeDivorcedNeutrals = ((float) neutralsDivorced * 100) / (float) totalPostDivorced;
                intPorcentajeDivorcedPositives = ((float) positivesDivorced * 100) / (float) totalPostDivorced;
                intPorcentajeDivorcedNegatives = ((float) negativesDivorced * 100) / (float) totalPostDivorced;
            }
            if (positivesDivorced > 0) {
                JSONObject node1 = new JSONObject();
                node1.put("label", "Positivos");
                node1.put("value1", "" + positivesDivorced);
                node1.put("value2", "" + round(intPorcentajeDivorcedPositives));
                node1.put("color", "#008000");
                node1.put("label2", "");
                node1.put("chartclass", "possClass");
                node1.put("label3", "Total de Post: " + totalPost);
                node.put(node1);
            }

            if (negativesDivorced > 0) {

                JSONObject node2 = new JSONObject();
                node2.put("label", "Negativos");
                node2.put("value1", "" + negativesDivorced);
                node2.put("value2", "" + round(intPorcentajeDivorcedNegatives));
                node2.put("color", "#FF0000");
                node2.put("label2", "");
                node2.put("chartclass", "possClass");
                node2.put("label3", "Total de Post: " + totalPost);
                node.put(node2);
            }


            if (neutralsDivorced > 0) {
                JSONObject node3 = new JSONObject();
                node3.put("label", "Neutros");
                node3.put("value1", "" + neutralsDivorced);
                node3.put("value2", "" + round(intPorcentajeDivorcedNeutrals));
                node3.put("color", "#FFD700");
                node3.put("label2", "");
                node3.put("chartclass", "possClass");
                node3.put("label3", "Total de Post: " + totalPost);
                node.put(node3);
            }


        } else if (filter.equals("widowed")) {

            if (positivesWidowed == 0 && negativesWidowed == 0 && neutralsWidowed == 0) {
                JSONObject node3 = new JSONObject();
                node3.put("label", "Sin Datos");
                node3.put("value1", "0");
                node3.put("value2", "100");
                node3.put("color", "#E6E6E6");
                node3.put("chartclass", "neuClass");
                node.put(node3);
                return node;
            }

            float intPorcentajeWidowedNeutrals = 0;
            float intPorcentajeWidowedPositives = 0;
            float intPorcentajeWidowedNegatives = 0;

            if (totalPostWidowed != 0) {
                intPorcentajeWidowedNeutrals = ((float) neutralsWidowed * 100) / (float) totalPostWidowed;
                intPorcentajeWidowedPositives = ((float) positivesWidowed * 100) / (float) totalPostWidowed;
                intPorcentajeWidowedNegatives = ((float) negativesWidowed * 100) / (float) totalPostWidowed;
            }

            if (positivesWidowed > 0) {
                JSONObject node1 = new JSONObject();
                node1.put("label", "Positivos");
                node1.put("value1", "" + positivesWidowed);
                node1.put("value2", "" + round(intPorcentajeWidowedPositives));
                node1.put("color", "#008000");
                node1.put("label2", "");
                node1.put("chartclass", "possClass");
                node1.put("label3", "Total de Post: " + totalPost);
                node.put(node1);
            }

            if (negativesWidowed > 0) {
                JSONObject node2 = new JSONObject();
                node2.put("label", "Negativos");
                node2.put("value1", "" + negativesWidowed);
                node2.put("value2", "" + round(intPorcentajeWidowedNegatives));
                node2.put("color", "#FF0000");
                node2.put("label2", "");
                node2.put("chartclass", "possClass");
                node2.put("label3", "Total de Post: " + totalPost);
                node.put(node2);
            }

            if (neutralsWidowed > 0) {
                JSONObject node3 = new JSONObject();
                node3.put("label", "Neutros");
                node3.put("value1", "" + neutralsWidowed);
                node3.put("value2", "" + round(intPorcentajeWidowedNeutrals));
                node3.put("color", "#FFD700");
                node3.put("label2", "");
                node3.put("chartclass", "possClass");
                node3.put("label3", "Total de Post: " + totalPost);
                node.put(node3);
            }


        } else if (filter.equals("undefined")) {

            if (positivesUndefined == 0 && negativesUndefined == 0 && neutralsUndefined == 0) {
                JSONObject node3 = new JSONObject();
                node3.put("label", "Sin Datos");
                node3.put("value1", "0");
                node3.put("value2", "100");
                node3.put("color", "#E6E6E6");
                node3.put("chartclass", "neuClass");
                node.put(node3);
                return node;
            }

            //System.out.println("entro en undefined relation");
            float intPorcentajeUndefinedNeutrals = 0;
            float intPorcentajeUndefinedPositives = 0;
            float intPorcentajeUndefinedNegatives = 0;
            //System.out.println("totalPostUndefined"+totalPostUndefined);
            if (totalPostUndefined != 0) {
                intPorcentajeUndefinedNeutrals = ((float) neutralsUndefined * 100) / (float) totalPostUndefined;
                intPorcentajeUndefinedPositives = ((float) positivesUndefined * 100) / (float) totalPostUndefined;
                intPorcentajeUndefinedNegatives = ((float) negativesUndefined * 100) / (float) totalPostUndefined;
            }
            //System.out.println("positivesUndefined"+positivesUndefined);
            //System.out.println("negativesUndefined"+negativesUndefined);
            //System.out.println("neutralsUndefined"+neutralsUndefined);
            if (positivesUndefined > 0) {
                JSONObject node1 = new JSONObject();
                node1.put("label", "Positivos");
                node1.put("value1", "" + positivesUndefined);
                node1.put("value2", "" + round(intPorcentajeUndefinedPositives));
                node1.put("color", "#008000");
                node1.put("label2", "");
                node1.put("chartclass", "possClass");
                node1.put("label3", "Total de Post: " + totalPost);
                node.put(node1);
            }
            //System.out.println("intPorcentajeUndefinedNegatives"+intPorcentajeUndefinedNegatives);
            if (negativesUndefined > 0) {
                JSONObject node2 = new JSONObject();
                node2.put("label", "Negativos");
                node2.put("value1", "" + negativesUndefined);
                node2.put("value2", "" + round(intPorcentajeUndefinedNegatives));
                node2.put("color", "#FF0000");
                node2.put("label2", "");
                node2.put("chartclass", "possClass");
                node2.put("label3", "Total de Post: " + totalPost);
                node.put(node2);
            }

            if (neutralsUndefined > 0) {
                JSONObject node3 = new JSONObject();
                node3.put("label", "Neutros");
                node3.put("value1", "" + neutralsUndefined);
                node3.put("value2", "" + round(intPorcentajeUndefinedNeutrals));
                node3.put("color", "#FFD700");
                node3.put("label2", "");
                node3.put("chartclass", "possClass");
                node3.put("label3", "Total de Post: " + totalPost);
                node.put(node3);
            }


        } 

        //System.out.println("node: "+node);

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
        //System.out.println("filter en relation ship:" +filter);
        out.println(getObject(semObj, lang, filter));
    }
%>