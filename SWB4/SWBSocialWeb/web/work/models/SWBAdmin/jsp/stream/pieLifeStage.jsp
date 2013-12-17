<%-- 
    Document   : profileGeoLocation
    Created on : 07-oct-2013, 19:51:58
    Author     : gabriela.rosales
--%>

<%@page import="org.semanticwb.social.admin.resources.util.SWBSocialResUtil"%>
<%@page contentType="text/json" pageEncoding="UTF-8"%> 
<%@page import="org.semanticwb.social.util.SWBSocialUtil"%>
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
    JSONArray getObject(SemanticObject semObj, String lang, String idModel, String filter) throws Exception {


        int young = 0, child = 0, teenAge = 0, youngAdult = 0, adult = 0, thirdAge = 0, totalPost = 0, nodefined = 0;
        Iterator<PostIn> itObjPostIns = null;
        if (semObj.getGenericInstance() instanceof Stream) {
            Stream stream = (Stream) semObj.getGenericInstance();
            itObjPostIns = stream.listPostInStreamInvs();
        } else if (semObj.getGenericInstance() instanceof SocialTopic) {
            SocialTopic socialTopic = (SocialTopic) semObj.getGenericInstance();
            itObjPostIns = PostIn.ClassMgr.listPostInBySocialTopic(socialTopic, socialTopic.getSocialSite());
        }


        ArrayList youngArray = new ArrayList();
        ArrayList childArray = new ArrayList();
        ArrayList teenAgeArray = new ArrayList();
        ArrayList youngAdultArray = new ArrayList();
        ArrayList thirdAgeArray = new ArrayList();
        ArrayList adultArray = new ArrayList();
        ArrayList noDefine = new ArrayList();
        String youngTitle = "Young";
        String youngAdultTitle = "YoungAdult";
        String childTitle = "Child";
        String teenTitle = "TeenAge";
        String adultTitle = "Adult";
        String thirdTitle = "ThirdAge";

        while (itObjPostIns.hasNext()) {
            PostIn postIn = itObjPostIns.next();
            totalPost++;
            if (postIn.getPostInSocialNetworkUser().getSnu_LifeStage() == null) {
                nodefined++;
                noDefine.add(postIn);
            } else if (postIn.getPostInSocialNetworkUser().getSnu_LifeStage().getId().equals("Young")) {
                young++;
                youngArray.add(postIn);
                //youngTitle = postIn.getPostInSocialNetworkUser().getSnu_LifeStage().getTitle(lang);
            } else if (postIn.getPostInSocialNetworkUser().getSnu_LifeStage().getId().equals("Child")) {
                child++;
                childArray.add(postIn);
                //childTitle = postIn.getPostInSocialNetworkUser().getSnu_LifeStage().getTitle(lang);
            } else if (postIn.getPostInSocialNetworkUser().getSnu_LifeStage().getId().equals("TeenAge")) {
                teenAge++;
                teenAgeArray.add(postIn);
                //teenTitle = postIn.getPostInSocialNetworkUser().getSnu_LifeStage().getTitle(lang);
            } else if (postIn.getPostInSocialNetworkUser().getSnu_LifeStage().getId().equals("YoungAdult")) {
                youngAdult++;
                youngAdultArray.add(postIn);
                //youngAdultTitle = postIn.getPostInSocialNetworkUser().getSnu_LifeStage().getTitle(lang);
            } else if (postIn.getPostInSocialNetworkUser().getSnu_LifeStage().getId().equals("Adult")) {
                adult++;
                adultArray.add(postIn);
                //adultTitle = postIn.getPostInSocialNetworkUser().getSnu_LifeStage().getTitle(lang);
            } else if (postIn.getPostInSocialNetworkUser().getSnu_LifeStage().getId().equals("ThirdAge")) {
                thirdAge++;
                thirdAgeArray.add(postIn);
                //thirdTitle = postIn.getPostInSocialNetworkUser().getSnu_LifeStage().getTitle(lang);
            }

        }


        Iterator childI = childArray.iterator();
        int neutralsChild = 0, positivesChild = 0, negativesChild = 0, totalChild = 0;
        while (childI.hasNext()) {
            PostIn pi = (PostIn) childI.next();
            totalChild++;
            if (pi.getPostSentimentalType() == 0) {
                neutralsChild++;
            } else if (pi.getPostSentimentalType() == 1) {
                positivesChild++;
            } else if (pi.getPostSentimentalType() == 2) {
                negativesChild++;
            }
        }


        Iterator youngI = youngArray.iterator();
        int neutralsYoung = 0, positivesYoung = 0, negativesYoung = 0, totalYoung = 0;
        while (youngI.hasNext()) {
            PostIn pi = (PostIn) youngI.next();
            totalYoung++;
            if (pi.getPostSentimentalType() == 0) {
                neutralsYoung++;
            } else if (pi.getPostSentimentalType() == 1) {
                positivesYoung++;
            } else if (pi.getPostSentimentalType() == 2) {
                negativesYoung++;
            }
        }


        Iterator noDefined = noDefine.iterator();
        int neutralsnoDefine = 0, positivesnoDefine = 0, negativesnoDefine = 0, totalnoDefine = 0;
        while (noDefined.hasNext()) {
            PostIn pi = (PostIn) noDefined.next();
            totalnoDefine++;
            if (pi.getPostSentimentalType() == 0) {
                neutralsnoDefine++;
            } else if (pi.getPostSentimentalType() == 1) {
                positivesnoDefine++;
            } else if (pi.getPostSentimentalType() == 2) {
                negativesnoDefine++;
            }
        }

        Iterator teenAgeI = teenAgeArray.iterator();
        int neutralsteenAge = 0, positivesteenAge = 0, negativesteenAge = 0, totalTeenAge = 0;
        while (teenAgeI.hasNext()) {
            PostIn pi = (PostIn) teenAgeI.next();
            totalTeenAge++;
            if (pi.getPostSentimentalType() == 0) {
                neutralsteenAge++;
            } else if (pi.getPostSentimentalType() == 1) {
                positivesteenAge++;
            } else if (pi.getPostSentimentalType() == 2) {
                negativesteenAge++;
            }
        }


        Iterator youngAdultI = youngAdultArray.iterator();
        int neutralsyoungAdult = 0, positivesyoungAdult = 0, negativesyoungAdult = 0, totalYoungAdult = 0;
        while (youngAdultI.hasNext()) {
            PostIn pi = (PostIn) youngAdultI.next();
            totalYoungAdult++;
            if (pi.getPostSentimentalType() == 0) {
                neutralsyoungAdult++;
            } else if (pi.getPostSentimentalType() == 1) {
                positivesyoungAdult++;
            } else if (pi.getPostSentimentalType() == 2) {
                negativesyoungAdult++;
            }
        }


        Iterator adultI = adultArray.iterator();
        int neutralsAdult = 0, positivesAdult = 0, negativesAdult = 0, totalAdult = 0;
        while (adultI.hasNext()) {
            PostIn pi = (PostIn) adultI.next();
            totalAdult++;
            if (pi.getPostSentimentalType() == 0) {
                neutralsAdult++;
            } else if (pi.getPostSentimentalType() == 1) {
                positivesAdult++;
            } else if (pi.getPostSentimentalType() == 2) {
                negativesAdult++;
            }
        }


        Iterator thirdAgeI = thirdAgeArray.iterator();
        int neutralsthirdAge = 0, positivesthirdAge = 0, negativesthirdAge = 0, totalThird = 0;
        while (thirdAgeI.hasNext()) {
            PostIn pi = (PostIn) thirdAgeI.next();
            totalThird++;
            if (pi.getPostSentimentalType() == 0) {
                neutralsthirdAge++;
            } else if (pi.getPostSentimentalType() == 1) {
                positivesthirdAge++;
            } else if (pi.getPostSentimentalType() == 2) {
                negativesthirdAge++;
            }
        }



        float intTotalVotos = young + child + teenAge + youngAdult + adult + thirdAge + nodefined;
        float intPorcentajenodefined = ((float) nodefined * 100) / (float) totalPost;
        float intPorcentajeYoung = ((float) young * 100) / (float) totalPost;
        float intPorcentajeChild = ((float) child * 100) / (float) totalPost;
        float intPorcentajeTeenAge = ((float) teenAge * 100) / (float) totalPost;
        float intPorcentajeYoungAdult = ((float) youngAdult * 100) / (float) totalPost;
        float intPorcentajeAdult = ((float) adult * 100) / (float) totalPost;
        float intPorcentajeThirdAge = ((float) thirdAge * 100) / (float) totalPost;


        JSONArray node = new JSONArray();

        if (filter.equals("all")) {
            //if (child > 0) {
            JSONObject node1 = new JSONObject();
            node1.put("label", childTitle);
            node1.put("value1", "" + child);
            node1.put("value2", "" + round(intPorcentajeChild));
            if (positivesChild > negativesChild && positivesChild > neutralsChild) {
                node1.put("color", "#008000");
            } else if (negativesChild > neutralsChild) {
                node1.put("color", "#FF0000");
            } else {
                node1.put("color", "#FFD700");
            }
            node1.put("label2", childTitle + " " + child + " " + SWBSocialResUtil.Util.getStringFromGenericLocale("positives", lang) + " : " + positivesChild + " " + SWBSocialResUtil.Util.getStringFromGenericLocale("negatives", lang) + " : " + negativesChild + " " + SWBSocialResUtil.Util.getStringFromGenericLocale("neutral", lang) + " : " + neutralsChild);
            node1.put("chartclass", "possClass");
            node1.put("label3", "Total de Post: " + totalPost);
            node.put(node1);
            //}

            // if (young > 0) {
            JSONObject node2 = new JSONObject();
            node2.put("label", youngTitle);
            node2.put("value1", "" + young);
            node2.put("value2", "" + round(intPorcentajeYoung));
            if (positivesYoung > negativesYoung && positivesYoung > neutralsYoung) {
                node2.put("color", "#008000");
            } else if (negativesYoung > neutralsYoung) {
                node2.put("color", "#FF0000");
            } else {
                node2.put("color", "#FFD700");
            }
            node2.put("label2", youngTitle + " " + young + " " + SWBSocialResUtil.Util.getStringFromGenericLocale("positives", lang) + " : " + positivesYoung + " " + SWBSocialResUtil.Util.getStringFromGenericLocale("negatives", lang) + " : " + negativesYoung + " " + SWBSocialResUtil.Util.getStringFromGenericLocale("neutral", lang) + " : " + neutralsYoung);
            node2.put("chartclass", "possClass");
            node2.put("label3", "Total de Post: " + totalPost);

            node.put(node2);
            //}


            //if (teenAge > 0) {
            JSONObject node3 = new JSONObject();
            node3.put("label", teenTitle);
            node3.put("value1", "" + teenAge);
            node3.put("value2", "" + round(intPorcentajeTeenAge));
            if (positivesteenAge > negativesteenAge && positivesteenAge > neutralsteenAge) {
                node3.put("color", "#008000");
            } else if (negativesteenAge > neutralsteenAge) {
                node3.put("color", "#FF0000");
            } else {
                node3.put("color", "#FFD700");
            }
            node3.put("label2", teenTitle + " " + teenAge + " " + SWBSocialResUtil.Util.getStringFromGenericLocale("positives", lang) + " : " + positivesteenAge + " " + SWBSocialResUtil.Util.getStringFromGenericLocale("negatives", lang) + " : " + negativesteenAge + " " + SWBSocialResUtil.Util.getStringFromGenericLocale("neutral", lang) + " : " + neutralsteenAge);
            node3.put("chartclass", "possClass");
            node3.put("label3", "Total de Post: " + totalPost);

            node.put(node3);
            //}

            //   if (youngAdult > 0) {
            JSONObject node4 = new JSONObject();
            node4.put("label", youngAdultTitle);
            node4.put("value1", "" + youngAdult);
            node4.put("value2", "" + round(intPorcentajeYoungAdult));
            if (positivesyoungAdult > negativesyoungAdult && positivesyoungAdult > neutralsyoungAdult) {
                node4.put("color", "#008000");
            } else if (negativesyoungAdult > neutralsyoungAdult) {
                node4.put("color", "#990000");
            } else {
                node4.put("color", "#FFD700");
            }
            node4.put("label2", youngAdultTitle + "  " + youngAdult + " " + SWBSocialResUtil.Util.getStringFromGenericLocale("positives", lang) + " : " + positivesyoungAdult + " " + SWBSocialResUtil.Util.getStringFromGenericLocale("negatives", lang) + " : " + negativesyoungAdult + " " + SWBSocialResUtil.Util.getStringFromGenericLocale("neutral", lang) + " : " + neutralsyoungAdult);
            node4.put("chartclass", "possClass");
            node4.put("label3", "Total de Post: " + totalPost);

            node.put(node4);
            // }


            // if (adult > 0) {
            JSONObject node5 = new JSONObject();
            node5.put("label", adultTitle);
            node5.put("value1", "" + adult);
            node5.put("value2", "" + round(intPorcentajeAdult));
            if (positivesAdult > negativesAdult && positivesAdult > neutralsAdult) {
                node5.put("color", "#008000");
            } else if (negativesAdult > neutralsAdult) {
                node5.put("color", "#FF0000");
            } else {
                node5.put("color", "#FFD700");
            }
            node5.put("label2", adultTitle + " " + adult + " " + SWBSocialResUtil.Util.getStringFromGenericLocale("positives", lang) + " : " + positivesAdult + " " + SWBSocialResUtil.Util.getStringFromGenericLocale("negatives", lang) + " : " + negativesAdult + " " + SWBSocialResUtil.Util.getStringFromGenericLocale("neutral", lang) + " : " + neutralsAdult);
            node5.put("chartclass", "possClass");
            node5.put("label3", "Total de Post: " + totalPost);

            node.put(node5);
            //}


            //if (thirdAge > 0) {
            JSONObject node6 = new JSONObject();
            node6.put("label", thirdTitle);
            node6.put("value1", "" + thirdAge);
            node6.put("value2", "" + round(intPorcentajeThirdAge));
            if (positivesthirdAge > negativesthirdAge && positivesthirdAge > neutralsthirdAge) {
                node6.put("color", "#008000");
            } else if (negativesthirdAge > neutralsthirdAge) {
                node6.put("color", "#FF0000");
            } else {
                node6.put("color", "#FFD700");
            }
            node6.put("label2", thirdTitle + " " + thirdAge + " " + SWBSocialResUtil.Util.getStringFromGenericLocale("positives", lang) + " : " + positivesthirdAge + " " + SWBSocialResUtil.Util.getStringFromGenericLocale("negatives", lang) + " : " + negativesthirdAge + " " + SWBSocialResUtil.Util.getStringFromGenericLocale("neutral", lang) + " : " + neutralsthirdAge);
            node6.put("chartclass", "possClass");
            node6.put("label3", "Total de Post: " + totalPost);

            node.put(node6);
            // }

            //  if (nodefined > 0) {
            JSONObject node7 = new JSONObject();
            node7.put("label", SWBSocialResUtil.Util.getStringFromGenericLocale("nodefine", lang));
            node7.put("value1", "" + nodefined);
            node7.put("value2", "" + round(intPorcentajenodefined));
            if (positivesnoDefine > negativesnoDefine && positivesnoDefine > neutralsnoDefine) {
                node7.put("color", "#008000");
            } else if (negativesnoDefine > neutralsnoDefine) {
                node7.put("color", "#FF0000");
            } else {
                node7.put("color", "#FFD700");
            }

            String label2 = SWBSocialResUtil.Util.getStringFromGenericLocale("nodefine", lang) + ": " + nodefined + "     -     " + SWBSocialResUtil.Util.getStringFromGenericLocale("positives", lang) + "  : " + positivesnoDefine + " " + SWBSocialResUtil.Util.getStringFromGenericLocale("negatives", lang) + "  :" + negativesnoDefine + " " + SWBSocialResUtil.Util.getStringFromGenericLocale("neutral", lang) + " : " + neutralsnoDefine;
            node7.put("label2", label2);
            node7.put("chartclass", "possClass");
            node7.put("label3", "Total de Post: " + totalPost);
            node.put(node7);



            // }
        } else if (filter.equals("child")) {

            float intPorcentajeChildNeutrals = 0;
            float intPorcentajeChildPositives = 0;
            float intPorcentajeChildNegatives = 0;

            if (totalChild != 0) {
                intPorcentajeChildNeutrals = ((float) neutralsChild * 100) / (float) totalChild;
                intPorcentajeChildPositives = ((float) positivesChild * 100) / (float) totalChild;
                intPorcentajeChildNegatives = ((float) negativesChild * 100) / (float) totalChild;
            }

            if (neutralsChild > 0) {
                JSONObject node4 = new JSONObject();
                node4.put("label", "Neutros");
                node4.put("value1", "" + neutralsChild);
                node4.put("value2", "" + round(intPorcentajeChildNeutrals));
                node4.put("label2", "");
                node4.put("color", "#FFD700");
                node4.put("chartclass", "possClass");
                node4.put("label3", "Total de Post: " + totalPost);
                node.put(node4);
            }

            if (positivesChild > 0) {
                JSONObject node5 = new JSONObject();
                node5.put("label", "Positivos");
                node5.put("value1", "" + positivesChild);
                node5.put("value2", "" + round(intPorcentajeChildPositives));
                node5.put("color", "#008000");
                node5.put("label2", "");
                node5.put("chartclass", "possClass");
                node5.put("label3", "Total de Post: " + totalPost);
                node.put(node5);
            }

            if (negativesChild > 0) {
                JSONObject node6 = new JSONObject();
                node6.put("label", "Negativos");
                node6.put("value1", "" + negativesChild);
                node6.put("value2", "" + round(intPorcentajeChildNegatives));
                node6.put("color", "#FF0000");
                node6.put("label2", "");
                node6.put("chartclass", "possClass");
                node6.put("label3", "Total de Post: " + totalPost);
                node.put(node6);
            }

        } else if (filter.equals("young")) {

            float intPorcentajeYoungNeutrals = 0;
            float intPorcentajeYoungPositives = 0;
            float intPorcentajeYoungNegatives = 0;

            if (totalYoung != 0) {
                intPorcentajeYoungNeutrals = ((float) neutralsYoung * 100) / (float) totalYoung;
                intPorcentajeYoungPositives = ((float) positivesYoung * 100) / (float) totalYoung;
                intPorcentajeYoungNegatives = ((float) negativesYoung * 100) / (float) totalYoung;
            }

            if (neutralsYoung > 0) {
                JSONObject node4 = new JSONObject();
                node4.put("label", "Neutros");
                node4.put("value1", "" + neutralsYoung);
                node4.put("value2", "" + round(intPorcentajeYoungNeutrals));
                node4.put("label2", "");
                node4.put("color", "#FFD700");
                node4.put("chartclass", "possClass");
                node4.put("label3", "Total de Post: " + totalPost);
                node.put(node4);
            }

            if (positivesYoung > 0) {
                JSONObject node5 = new JSONObject();
                node5.put("label", "Positivos");
                node5.put("value1", "" + positivesYoung);
                node5.put("value2", "" + round(intPorcentajeYoungPositives));
                node5.put("color", "#008000");
                node5.put("label2", "");
                node5.put("chartclass", "possClass");
                node5.put("label3", "Total de Post: " + totalPost);
                node.put(node5);
            }

            if (negativesYoung > 0) {
                JSONObject node6 = new JSONObject();
                node6.put("label", "Negativos");
                node6.put("value1", "" + negativesYoung);
                node6.put("value2", "" + round(intPorcentajeYoungNegatives));
                node6.put("color", "#FF0000");
                node6.put("label2", "");
                node6.put("chartclass", "possClass");
                node6.put("label3", "Total de Post: " + totalPost);
                node.put(node6);
            }


        } else if (filter.equals("teenAge")) {

            float intPorcentajeTeenAgeNeutrals = 0;
            float intPorcentajeTeenAgePositives = 0;
            float intPorcentajeTeenAgeNegatives = 0;

            if (totalTeenAge != 0) {
                intPorcentajeTeenAgeNeutrals = ((float) neutralsteenAge * 100) / (float) totalTeenAge;
                intPorcentajeTeenAgePositives = ((float) positivesteenAge * 100) / (float) totalTeenAge;
                intPorcentajeTeenAgeNegatives = ((float) negativesteenAge * 100) / (float) totalTeenAge;
            }

            if (neutralsteenAge > 0) {
                JSONObject node4 = new JSONObject();
                node4.put("label", "Neutros");
                node4.put("value1", "" + neutralsteenAge);
                node4.put("value2", "" + round(intPorcentajeTeenAgeNeutrals));
                node4.put("label2", "");
                node4.put("color", "#FFD700");
                node4.put("chartclass", "possClass");
                node4.put("label3", "Total de Post: " + totalPost);
                node.put(node4);
            }

            if (positivesteenAge > 0) {
                JSONObject node5 = new JSONObject();
                node5.put("label", "Positivos");
                node5.put("value1", "" + positivesteenAge);
                node5.put("value2", "" + round(intPorcentajeTeenAgePositives));
                node5.put("color", "#008000");
                node5.put("label2", "");
                node5.put("chartclass", "possClass");
                node5.put("label3", "Total de Post: " + totalPost);
                node.put(node5);
            }


            if (negativesteenAge > 0) {
                JSONObject node6 = new JSONObject();
                node6.put("label", "Negativos");
                node6.put("value1", "" + negativesteenAge);
                node6.put("value2", "" + round(intPorcentajeTeenAgeNegatives));
                node6.put("color", "#FF0000");
                node6.put("label2", "");
                node6.put("chartclass", "possClass");
                node6.put("label3", "Total de Post: " + totalPost);
                node.put(node6);
            }

        } else if (filter.equals("youngAdult")) {

            float intPorcentajeYoungAdultNeutrals = 0;
            float intPorcentajeYoungAdultPositives = 0;
            float intPorcentajeYoungAdultNegatives = 0;

            if (totalYoungAdult != 0) {
                intPorcentajeYoungAdultNeutrals = ((float) neutralsyoungAdult * 100) / (float) totalYoungAdult;
                intPorcentajeYoungAdultPositives = ((float) positivesyoungAdult * 100) / (float) totalYoungAdult;
                intPorcentajeYoungAdultNegatives = ((float) negativesyoungAdult * 100) / (float) totalYoungAdult;
            }

            if (totalYoungAdult > 0) {
                JSONObject node4 = new JSONObject();
                node4.put("label", "Neutros");
                node4.put("value1", "" + totalYoungAdult);
                node4.put("value2", "" + round(intPorcentajeYoungAdultNeutrals));
                node4.put("label2", "");
                node4.put("color", "#FFD700");
                node4.put("chartclass", "possClass");
                node4.put("label3", "Total de Post: " + totalPost);
                node.put(node4);
            }


            if (positivesyoungAdult > 0) {
                JSONObject node5 = new JSONObject();
                node5.put("label", "Positivos");
                node5.put("value1", "" + positivesyoungAdult);
                node5.put("value2", "" + round(intPorcentajeYoungAdultPositives));
                node5.put("color", "#008000");
                node5.put("label2", "");
                node5.put("chartclass", "possClass");
                node5.put("label3", "Total de Post: " + totalPost);
                node.put(node5);
            }

            if (negativesyoungAdult > 0) {
                JSONObject node6 = new JSONObject();
                node6.put("label", "Negativos");
                node6.put("value1", "" + negativesyoungAdult);
                node6.put("value2", "" + round(intPorcentajeYoungAdultNegatives));
                node6.put("color", "#FF0000");
                node6.put("label2", "");
                node6.put("chartclass", "possClass");
                node6.put("label3", "Total de Post: " + totalPost);
                node.put(node6);
            }

        } else if (filter.equals("adult")) {

            float intPorcentajeAdultNeutrals = 0;
            float intPorcentajeAdultPositives = 0;
            float intPorcentajeAdultNegatives = 0;

            if (totalAdult != 0) {
                intPorcentajeAdultNeutrals = ((float) neutralsAdult * 100) / (float) totalAdult;
                intPorcentajeAdultPositives = ((float) positivesAdult * 100) / (float) totalAdult;
                intPorcentajeAdultNegatives = ((float) negativesAdult * 100) / (float) totalAdult;
            }

            if (neutralsAdult > 0) {
                JSONObject node4 = new JSONObject();
                node4.put("label", "Neutros");
                node4.put("value1", "" + neutralsAdult);
                node4.put("value2", "" + round(intPorcentajeAdultNeutrals));
                node4.put("label2", "");
                node4.put("color", "#FFD700");
                node4.put("chartclass", "possClass");
                node4.put("label3", "Total de Post: " + totalPost);
                node.put(node4);
            }

            if (positivesAdult > 0) {
                JSONObject node5 = new JSONObject();
                node5.put("label", "Positivos");
                node5.put("value1", "" + positivesAdult);
                node5.put("value2", "" + round(intPorcentajeAdultPositives));
                node5.put("color", "#008000");
                node5.put("label2", "");
                node5.put("chartclass", "possClass");
                node5.put("label3", "Total de Post: " + totalPost);
                node.put(node5);
            }
            if (negativesAdult > 0) {

                JSONObject node6 = new JSONObject();
                node6.put("label", "Negativos");
                node6.put("value1", "" + negativesAdult);
                node6.put("value2", "" + round(intPorcentajeAdultNegatives));
                node6.put("color", "#FF0000");
                node6.put("label2", "");
                node6.put("chartclass", "possClass");
                node6.put("label3", "Total de Post: " + totalPost);
                node.put(node6);
            }

        } else if (filter.equals("thirdAge")) {

            float intPorcentajeThirdNeutrals = 0;
            float intPorcentajeThirdPositives = 0;
            float intPorcentajeThirdNegatives = 0;

            if (totalAdult != 0) {
                intPorcentajeThirdNeutrals = ((float) neutralsthirdAge * 100) / (float) totalThird;
                intPorcentajeThirdPositives = ((float) positivesthirdAge * 100) / (float) totalThird;
                intPorcentajeThirdNegatives = ((float) negativesthirdAge * 100) / (float) totalThird;
            }

            if (neutralsthirdAge > 0) {
                JSONObject node4 = new JSONObject();
                node4.put("label", "Neutros");
                node4.put("value1", "" + neutralsthirdAge);
                node4.put("value2", "" + round(intPorcentajeThirdNeutrals));
                node4.put("label2", "");
                node4.put("color", "#FFD700");
                node4.put("chartclass", "possClass");
                node4.put("label3", "Total de Post: " + totalPost);
                node.put(node4);
            }

            if (positivesthirdAge > 0) {
                JSONObject node5 = new JSONObject();
                node5.put("label", "Positivos");
                node5.put("value1", "" + positivesthirdAge);
                node5.put("value2", "" + round(intPorcentajeThirdPositives));
                node5.put("color", "#008000");
                node5.put("label2", "");
                node5.put("chartclass", "possClass");
                node5.put("label3", "Total de Post: " + totalPost);
                node.put(node5);
            }

            if (negativesthirdAge > 0) {
                JSONObject node6 = new JSONObject();
                node6.put("label", "Negativos");
                node6.put("value1", "" + negativesthirdAge);
                node6.put("value2", "" + round(intPorcentajeThirdNegatives));
                node6.put("color", "#FF0000");
                node6.put("label2", "");
                node6.put("chartclass", "possClass");
                node6.put("label3", "Total de Post: " + totalPost);
                node.put(node6);
            }

        } else if (filter.equals("nodefine")) {

            float intPorcentajenoDefineNeutrals = 0;
            float intPorcentajenoDefinePositives = 0;
            float intPorcentajenoDefineNegatives = 0;

            if (totalnoDefine != 0) {
                intPorcentajenoDefineNeutrals = ((float) neutralsnoDefine * 100) / (float) totalnoDefine;
                intPorcentajenoDefinePositives = ((float) positivesnoDefine * 100) / (float) totalnoDefine;
                intPorcentajenoDefineNegatives = ((float) negativesnoDefine * 100) / (float) totalnoDefine;
            }

            if (neutralsnoDefine > 0) {
                JSONObject node4 = new JSONObject();
                node4.put("label", "Neutros");
                node4.put("value1", "" + neutralsnoDefine);
                node4.put("value2", "" + round(intPorcentajenoDefineNeutrals));
                node4.put("label2", "");
                node4.put("color", "#FFD700");
                node4.put("chartclass", "possClass");
                node4.put("label3", "Total de Post: " + totalPost);
                node.put(node4);
            }

            if (positivesnoDefine > 0) {
                JSONObject node5 = new JSONObject();
                node5.put("label", "Positivos");
                node5.put("value1", "" + positivesnoDefine);
                node5.put("value2", "" + round(intPorcentajenoDefinePositives));
                node5.put("color", "#008000");
                node5.put("label2", "");
                node5.put("chartclass", "possClass");
                node5.put("label3", "Total de Post: " + totalPost);
                node.put(node5);
            }

            if (negativesnoDefine > 0) {

                JSONObject node6 = new JSONObject();
                node6.put("label", "Negativos");
                node6.put("value1", "" + negativesnoDefine);
                node6.put("value2", "" + round(intPorcentajenoDefineNegatives));
                node6.put("color", "#FF0000");
                node6.put("label2", "");
                node6.put("chartclass", "possClass");
                node6.put("label3", "Total de Post: " + totalPost);
                node.put(node6);
            }

        } else if (filter.equals("x")) {

            if (child == 0 && young == 0 && teenAge == 0 && youngAdult == 0 && adult == 0 && thirdAge == 0 && nodefined == 0) {
                //System.out.println("ENTROOOOOOOOOOOOOOOLIFE");
                node.remove(6);
                node.remove(5);
                node.remove(4);
                node.remove(3);
                node.remove(2);
                node.remove(1);
                node.remove(0);

                JSONObject node8 = new JSONObject();
                node8.put("label", SWBSocialResUtil.Util.getStringFromGenericLocale("nodefined", lang));
                node8.put("value1", "0");
                node8.put("value2", "100");
                if (positivesnoDefine > negativesnoDefine && positivesnoDefine > neutralsnoDefine) {
                    node8.put("color", "#86c440");
                } else if (negativesnoDefine > neutralsnoDefine) {
                    node8.put("color", "#990000");
                } else {
                    node8.put("color", "#eae8e3");
                }
                node8.put("chartclass", "neuClass");
                node8.put("label2", "Sin datos para procesar");
                node8.put("label3", "Total de Post: " + totalPost);

                node.put(node8);

            }

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
        String idModel = request.getParameter("idModel");
        String filter = request.getParameter("filter");
        //System.out.println("filter en life : "+filter);
        out.println(getObject(semObj, lang, idModel, filter));
    }
%>