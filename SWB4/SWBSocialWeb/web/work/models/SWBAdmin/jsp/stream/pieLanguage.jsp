<%-- 
    Document   : pieLanguage
    Created on : 25/03/2014, 08:12:10 PM
    Author     : gabriela.rosales
--%>

<%@page import="org.semanticwb.security.limiter.SWBUserAction"%>
<%@page import="java.lang.reflect.Array"%>
<%@page import="java.util.Map.Entry"%>
<%@page import="com.sun.accessibility.internal.resources.accessibility"%>
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
<%@page import="org.semanticwb.social.Country"%>
<%@page import="org.semanticwb.model.Collection"%>
<%@page import="org.semanticwb.social.util.SWBSocialUtil"%>
<%@page import="java.util.*"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Arrays"%>



<%!    private static int positivesGlobal = 0;
    private static int negativesGlobal = 0;
    private static int neutralsGlobal = 0;

    JSONArray getObject(SemanticObject semObj, String lang, String idModel, String fi) throws Exception {
        ArrayList<PostIn> list = new ArrayList<PostIn>();

        HashMap map = new HashMap();
        WebSite ss = SWBSocialUtil.getConfigWebSite();
        Iterator i = null;
        i = Language.ClassMgr.listLanguages(ss);
        while (i.hasNext()) {
            Language language = (Language) i.next();
            map.put(reemplazar(language.getTitle()), new ArrayList<PostIn>());

        }

        map.put("No definido", new ArrayList<PostIn>());

        String filter = reemplazar(fi);

        int neutrals = 0, positives = 0, negatives = 0, totalPost = 0;
        Iterator<PostIn> itObjPostIns = null;

        if (semObj.getGenericInstance() instanceof Stream) {
            Stream stream = (Stream) semObj.getGenericInstance();
            itObjPostIns = stream.listPostInStreamInvs();
        } else if (semObj.getGenericInstance() instanceof SocialTopic) {
            SocialTopic socialTopic = (SocialTopic) semObj.getGenericInstance();
            itObjPostIns = PostIn.ClassMgr.listPostInBySocialTopic(socialTopic, socialTopic.getSocialSite());
        }
        SWBModel model = WebSite.ClassMgr.getWebSite(idModel);
        Iterator c = CountryState.ClassMgr.listCountryStates(model);
        Iterator coun = Country.ClassMgr.listCountries(model);

        JSONArray node = new JSONArray();

        while (itObjPostIns.hasNext()) {

            PostIn postIn = itObjPostIns.next();
            Language key = null;

            String title = "";
            if (postIn.getMsg_lang() == null) {
                title = "No definido";
                map.put(title, map.containsKey(title) ? addArray(map.get(title), postIn, title) : new ArrayList<PostIn>());
            } else {

                key = postIn.getMsg_lang();
                title = reemplazar(key.getTitle());

                map.put(title, map.containsKey(title) ? addArray(map.get(title), postIn, title) : new ArrayList<PostIn>());
                //  }
            }
            totalPost++;
        }
        System.out.println("ANTES DE ENVIAT TOTAL POST" + totalPost);
        Iterator it = map.entrySet().iterator();
        if (filter.equals("all")) {
            while (it.hasNext()) {
                Map.Entry e = (Map.Entry) it.next();
                ArrayList lista = (ArrayList) e.getValue();
                getJson(node, lista, totalPost, e.getKey().toString());

            }
        } else {
            System.out.println("7");
            while (it.hasNext()) {
                Map.Entry e = (Map.Entry) it.next();
                if (filter.equals(e.getKey().toString())) {
                    ArrayList lista = (ArrayList) e.getValue();
                    getJson(node, lista, totalPost, e.getKey().toString(), filter);
                }
            }
        }

        positivesGlobal = 0;
        negativesGlobal = 0;
        neutralsGlobal = 0;
        if (node.length() == 0) {
            JSONObject node3 = new JSONObject();
            node3.put("label", "Sin Datos");
            node3.put("value1", "0");
            node3.put("value2", "100");
            node3.put("color", "#E6E6E6");
            node3.put("chartclass", "neuClass");
            JSONObject jor = new JSONObject();
            jor.put("positivos", "" + 0);
            jor.put("negativos", "" + 0);
            jor.put("neutros", "" + 0);
            node3.put("valor", jor);
            node.put(node3);
        }

        return node;

    }

    public JSONArray getJson(JSONArray node, ArrayList list, int totalPost, String nombre) throws Exception {
        float intPorcentaje = 0;
        if (totalPost > 0) {
            intPorcentaje = ((float) list.size() * 100) / (float) totalPost; //total de post de mexico
        }

        if (intPorcentaje == 0) {
            return node;
        } else {
            float porcentajeNeutrals = 0;
            float porcentajePositives = 0;
            float porcentajeNegatives = 0;
            int neutrals = 0, positives = 0, negatives = 0, total = 0;

            Iterator i = list.iterator();

            while (i.hasNext()) {
                PostIn p = (PostIn) i.next();
                if (p.getMsg_lang() == null) {
                    nombre = "No definido";
                } else {
                    nombre = p.getMsg_lang().getTitle();
                }
                total++;
                if (p.getPostSentimentalType() == 0) {
                    neutrals++;
                } else if (p.getPostSentimentalType() == 1) {
                    positives++;
                } else if (p.getPostSentimentalType() == 2) {
                    negatives++;
                }
            }
            positivesGlobal = positivesGlobal + positives;
            negativesGlobal = negativesGlobal + negatives;
            neutralsGlobal = neutralsGlobal + neutrals;


            porcentajeNeutrals = ((float) neutrals * 100) / (float) total;
            porcentajePositives = ((float) positives * 100) / (float) total;
            porcentajeNegatives = ((float) negatives * 100) / (float) total;

            JSONObject node_ = new JSONObject();
            node_.put("label", "" + nombre);
            node_.put("value1", "" + total);
            node_.put("value2", "" + round(intPorcentaje));
            JSONObject joChild = new JSONObject();
            joChild.put("positivos", "" + positivesGlobal);
            joChild.put("negativos", "" + negativesGlobal);
            joChild.put("neutros", "" + neutralsGlobal);
            node_.put("valor", joChild);
            if (positives > negatives && positives > neutrals) {
                node_.put("color", "#008000");
            } else if (negatives > neutrals) {
                node_.put("color", "#FF0000");
            } else {
                node_.put("color", "#FFD700");
            }
            node_.put("label2", "" + nombre + ": " + total + " -     Positivos : " + positives + "  Negativos: " + negatives + "  Neutros : " + neutrals);
            node_.put("chartclass", "possClass");
            if (true) {
                JSONObject joTotal = new JSONObject();
                joTotal.put("positivos", "" + positives);
                joTotal.put("negativos", "" + negatives);
                joTotal.put("neutros", "" + neutrals);
                node_.put("label3", joTotal);
            }
            node.put(node_);
            return node;
        }
    }

    public JSONArray getJson(JSONArray node, ArrayList list, int totalPost, String nombre, String filter) throws Exception {
        float intPorcentaje = ((float) list.size() * 100) / (float) totalPost; //total de post de mexico
        float porcentajeNeutrals = 0;
        float porcentajePositives = 0;
        float porcentajeNegatives = 0;
        int neutrals = 0, positives = 0, negatives = 0, total = 0;

        Iterator i = list.iterator();

        while (i.hasNext()) {
            PostIn p = (PostIn) i.next();
            total++;
            if (p.getPostSentimentalType() == 0) {
                neutrals++;
            } else if (p.getPostSentimentalType() == 1) {
                positives++;
            } else if (p.getPostSentimentalType() == 2) {
                negatives++;
            }
        }
        porcentajeNeutrals = ((float) neutrals * 100) / (float) total;
        porcentajePositives = ((float) positives * 100) / (float) total;
        porcentajeNegatives = ((float) negatives * 100) / (float) total;

        if (negatives == 0 && positives == 0 && neutrals == 0) {
            JSONObject node3 = new JSONObject();
            node3.put("label", "Sin Datos");
            node3.put("value1", "0");
            node3.put("value2", "100");
            node3.put("color", "#E6E6E6");
            node3.put("chartclass", "neuClass");
            JSONObject jor = new JSONObject();
            jor.put("positivos", "" + positives);
            jor.put("negativos", "" + negatives);
            jor.put("neutros", "" + neutrals);
            node3.put("valor", jor);
            node.put(node3);
            return node;
        }
        if (neutrals > 0) {
            JSONObject node4 = new JSONObject();
            node4.put("label", "Neutros");
            node4.put("value1", "" + neutrals);
            node4.put("value2", "" + round(porcentajeNeutrals));
            node4.put("color", "#FFD700");
            node4.put("label2", "");
            node4.put("chartclass", "possClass");
            node4.put("label3", "Total de Post: " + totalPost);
            node.put(node4);

        }
        if (negatives > 0) {
            JSONObject node6 = new JSONObject();
            node6.put("label", "Negativos");
            node6.put("value1", "" + negatives);
            node6.put("value2", "" + round(porcentajeNegatives));
            node6.put("color", "#FF0000");
            node6.put("label2", "");
            node6.put("chartclass", "possClass");
            node6.put("label3", "Total de Post: " + totalPost);
            node.put(node6);
        }
        if (positives > 0) {
            JSONObject node5 = new JSONObject();
            node5.put("label", "Positivos");
            node5.put("value1", "" + positives);
            node5.put("value2", "" + round(porcentajePositives));
            node5.put("color", "#008000");
            node5.put("label2", "");
            node5.put("chartclass", "possClass");
            node5.put("label3", "Total de Post: " + totalPost);
            node.put(node5);
        }

        return node;

    }

    public JSONArray getJson(JSONArray node, String label, int total, float porcentaje, int positives, int negatives, int neutrals, int totalPost, boolean first, int totalPositives, int totalNegatives, int totalNeutrals) throws Exception {

        JSONObject node_ = new JSONObject();
        node_.put("label", "" + label);
        node_.put("value1", "" + total);
        node_.put("value2", "" + round(porcentaje));
        JSONObject joChild = new JSONObject();
        joChild.put("positivos", "" + positives);
        joChild.put("negativos", "" + negatives);
        joChild.put("neutros", "" + neutrals);
        node_.put("valor", joChild);
        if (positives > negatives && positives > neutrals) {
            node_.put("color", "#008000");
        } else if (negatives > neutrals) {
            node_.put("color", "#FF0000");
        } else {
            node_.put("color", "#FFD700");
        }
        node_.put("label2", "" + label + ": " + total + " -     Positivos : " + positives + "  Negativos: " + negatives + "  Neutros : " + neutrals);
        node_.put("chartclass", "possClass");
        if (first) {
            JSONObject joTotal = new JSONObject();
            joTotal.put("positivos", "" + totalPositives);
            joTotal.put("negativos", "" + totalNegatives);
            joTotal.put("neutros", "" + totalNeutrals);
            node_.put("label3", joTotal);
        }
        node.put(node_);
        return node;


    }

    public double round(float number) {
        return Math.rint(number * 100) / 100;
    }

    public ArrayList addArray(Object lista, PostIn postIn, String title) {
        Boolean c = lista instanceof ArrayList;
        if (lista == null) {
            lista = new ArrayList<PostIn>();
        }
        ArrayList l = (ArrayList) lista;
        l.add(postIn);
        return l;

    }

    public String reemplazar(String cadena) {

        String original = "áàäéèëíìïóòöúùuñÁÀÄÉÈËÍÌÏÓÒÖÚÙÜÑçÇ";
        // Cadena de caracteres ASCII que reemplazarán los originales.
        String ascii = "aaaeeeiiiooouuunAAAEEEIIIOOOUUUNcC";
        String output = cadena;
        for (int i = 0; i < original.length(); i++) {
            // Reemplazamos los caracteres especiales.
            output = output.replace(original.charAt(i), ascii.charAt(i));
        }//for i

        return output;
    }
%>
<%

    if (request.getParameter("objUri") != null) {
        SemanticObject semObj = SemanticObject.getSemanticObject(request.getParameter("objUri"));
        String lang = request.getParameter("lang");
        String idModel = request.getParameter("idModel");
        String filter = request.getParameter("filter");
        out.println(getObject(semObj, lang, idModel, filter));
    }
%>

