<%@page contentType="text/html"%>
<%@page pageEncoding="ISO-8859-1"%>
<%@page import="org.semanticwb.*,org.semanticwb.platform.*,org.semanticwb.portal.*"%>
<%@page import="org.semanticwb.model.*,java.util.*,org.semanticwb.base.util.*"%>
<%@page import="org.semanticwb.nlp.*,java.io.StringWriter" %>
<%

    Lexicon lex = new Lexicon("es");

    Iterator<SemanticClass> sit = SWBPlatform.getSemanticMgr().getVocabulary().listSemanticClasses();
    while (sit.hasNext()) {
        SemanticClass sc = sit.next();
        out.println("id:<font color='red'>" + sc.getClassId() + "</font>  name:" + sc.getDisplayName(lex.getLanguage()) + "<br>");

        Iterator<SemanticProperty> pit = sc.listProperties();
        while(pit.hasNext()) {
            SemanticProperty sp = pit.next();
            if(sp.isObjectProperty()) {
               StringBuffer bf = new StringBuffer();
               bf.append(sp.getRangeClass());

                SemanticClass rg = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass(bf.toString());
                if(rg == null) {
                    out.println("--->null");
                } else {
                out.println("<font color='blue'>  " + sp.getDisplayName(lex.getLanguage()) +
                       "["  + lex.getObjWordTag(rg.getDisplayName(lex.getLanguage())).getType() + "]</font><br>");
                }
            } else {
                out.println("<font color='green'>  " + sp.getDisplayName(lex.getLanguage()) + "</font><br>");
            }
        }
    }

    
    String q = "[Página Web] con [Usuario Creador]";
    out.println("<form><fieldset title='query:'>" + q + "</fieldset>");
    tTranslator tr = new tTranslator(lex);
    String query = lex.getPrefixString() + tr.translateSentence(q);
    out.println("<fieldset>" + query.replace("<", "&lt;").replace(">", "&gt;").replace("\n", "<br>") + "</fieldset> ");

    out.println("</form>");
%>
