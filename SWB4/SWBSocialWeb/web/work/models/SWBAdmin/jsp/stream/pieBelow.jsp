<%-- 
    Document   : pieBelow
    Created on : 3/04/2014, 10:30:22 AM
    Author     : gabriela.rosales
--%>


<%@page import="java.util.Map.Entry"%>
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
    private static int positivesGlobal = 0;
    private static int negativesGlobal = 0;
    private static int neutralsGlobal = 0;

    JSONArray getObject(SemanticObject semObj, String lang, String idModel, String fi) throws Exception {
        ArrayList<PostOut> list = new ArrayList<PostOut>();
        HashMap map = new HashMap();
        WebSite ss = SWBSocialUtil.getConfigWebSite();
        Iterator i = null;

        String filter = reemplazar(fi);

        int neutrals = 0, positives = 0, negatives = 0, totalPost = 0;
        Iterator<PostOut> itObjPostIns = null;

        //  if (semObj.getGenericInstance() instanceof Stream) {
        //    Stream stream = (Stream) semObj.getGenericInstance();
        //  itObjPostIns = stream.listPostInStreamInvs();
        //} else if (semObj.getGenericInstance() instanceof SocialTopic) {
        SocialTopic socialTopic = (SocialTopic) semObj.getGenericInstance();
        itObjPostIns = PostOut.ClassMgr.listPostOutBySocialTopic(socialTopic, socialTopic.getSocialSite());
        //}
        SWBModel model = WebSite.ClassMgr.getWebSite(idModel);


        JSONArray node = new JSONArray();
        float responses = 0;

        while (itObjPostIns.hasNext()) {

            PostOut postIn = itObjPostIns.next();
            SocialNetwork key = null;

            String title = "";
            responses = postIn.getNumTotNewResponses();
                              // while(listaRedes.hasNext() ){

            if (postIn == null) {
                title = "No definido";
                map.put(postIn, map.containsKey(postIn) ? postIn.getNumTotNewResponses() : postIn.getNumTotNewResponses());
            } else {
                // key = postIn.getSocialNetwork();         
                map.put(postIn, map.containsKey(postIn) ? postIn.getNumTotNewResponses() : postIn.getNumTotNewResponses());
                //  }
            }
            totalPost++;

            //  }
            map = sortByComparator(map, true);

        }
        Iterator it = map.entrySet().iterator();
        int size = 0;
        if (filter.equals("all")) {
            while (it.hasNext()) {
               Map.Entry e = (Map.Entry) it.next();
                SemanticObject so = SemanticObject.createSemanticObject(e.getKey().toString());
                PostOut postOut = (PostOut) so.createGenericInstance();
                Float value = Float.parseFloat(new String(e.getValue().toString()));                
                getJson(node, value, totalPost, postOut);
                size++;
                if(size>=10){
                break;
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

    public JSONArray getJson(JSONArray node, Float value, int totalPost, PostOut p) throws Exception {
         float intPorcentaje = 0;
        String nombre = "";

        if (value == 0) {
          return node;
        } else {
        float porcentajeNeutrals = 0;
        float porcentajePositives = 0;
        float porcentajeNegatives = 0;
        int neutrals = 0, positives = 0, negatives = 0, total = 0;

        if (p.getPostSentimentalType() == 0) {
            neutrals++;
        } else if (p.getPostSentimentalType() == 1) {
            positives++;
        } else if (p.getPostSentimentalType() == 2) {
            negatives++;
        }
        positivesGlobal = positivesGlobal + positives;
        negativesGlobal = negativesGlobal + negatives;
        neutralsGlobal = neutralsGlobal + neutrals;


        porcentajeNeutrals = ((float) neutrals * 100) / (float) total;
        porcentajePositives = ((float) positives * 100) / (float) total;
        porcentajeNegatives = ((float) negatives * 100) / (float) total;

        JSONObject node_ = new JSONObject();
        node_.put("label", "" + p.getMsg_Text() );
        node_.put("value1", "" + total);
        node_.put("value2", "" + Math.round(value));
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

   
    public double round(float number) {
        return Math.rint(number * 100) / 100;
    }

    public ArrayList addArray(Object lista, PostOut postIn) {
        Boolean c = lista instanceof ArrayList;
        if (lista == null) {
            lista = new ArrayList<PostIn>();
        }
        ArrayList l = (ArrayList) lista;
        l.add(postIn);
        return l;

    }

    public ArrayList addPostOut(ArrayList lista, PostOut postIn) {

        lista.add(postIn);

        return lista;

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

private static HashMap<String, Float> sortByComparator(Map<String, Float> unsortMap, final boolean order) {

        List<Entry<String, Float>> list = new LinkedList<Entry<String, Float>>(unsortMap.entrySet());

        // Sorting the list based on values
        Collections.sort(list, new Comparator<Entry<String, Float>>() {
            public int compare(Entry<String, Float> o1,
                    Entry<String, Float> o2) {
                if (order) {
                    return o1.getValue().compareTo(o2.getValue());
                } else {
                    return o2.getValue().compareTo(o1.getValue());

                }
            }
        });

        HashMap<String, Float> sortedMap = new LinkedHashMap<String, Float>();
        for (Entry<String, Float> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        return sortedMap;
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