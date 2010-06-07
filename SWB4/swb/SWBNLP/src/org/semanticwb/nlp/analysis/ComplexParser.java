/**
 * SemanticWebBuilder es una plataforma para el desarrollo de portales y aplicaciones de integración,
 * colaboración y conocimiento, que gracias al uso de tecnología semántica puede generar contextos de
 * información alrededor de algún tema de interés o bien integrar información y aplicaciones de diferentes
 * fuentes, donde a la información se le asigna un significado, de forma que pueda ser interpretada y
 * procesada por personas y/o sistemas, es una creación original del Fondo de Información y Documentación
 * para la Industria INFOTEC, cuyo registro se encuentra actualmente en trámite.
 *
 * INFOTEC pone a su disposición la herramienta SemanticWebBuilder a través de su licenciamiento abierto al público (‘open source’),
 * en virtud del cual, usted podrá usarlo en las mismas condiciones con que INFOTEC lo ha diseñado y puesto a su disposición;
 * aprender de él; distribuirlo a terceros; acceder a su código fuente y modificarlo, y combinarlo o enlazarlo con otro software,
 * todo ello de conformidad con los términos y condiciones de la LICENCIA ABIERTA AL PÚBLICO que otorga INFOTEC para la utilización
 * del SemanticWebBuilder 4.0.
 *
 * INFOTEC no otorga garantía sobre SemanticWebBuilder, de ninguna especie y naturaleza, ni implícita ni explícita,
 * siendo usted completamente responsable de la utilización que le dé y asumiendo la totalidad de los riesgos que puedan derivar
 * de la misma.
 *
 * Si usted tiene cualquier duda o comentario sobre SemanticWebBuilder, INFOTEC pone a su disposición la siguiente
 * dirección electrónica:
 *  http://www.semanticwebbuilder.org
 **/
package org.semanticwb.nlp.analysis;


/**
 * An antlr natural language SparQl query parser. Most of the code for this
 * class and nested classes was generated automatically by antlr.
 * Visit http://www.antlr.org for details about each undocumented class element.
 * <p>
 * Un analizador de consultas SparQl en lenguaje natural generado por antlr.
 * La mayoría del código de esta clase y las clases anidadas fue generado
 * automáticamente por antlr. Visite http://www.antlr.org para obtener detalles
 * acerca de los elementos de esta clase que no están documentados.
 *
 * @author Hasdai Pacheco {haxdai@gmail.com}
 */
// $ANTLR 3.2 Sep 23, 2009 12:02:23 /home/hasdai/Documentos/INFOTEC/SWB4/swb/SWBNLP/src/org/semanticwb/nlp/analysis/grammar/ComplexParser.g 2010-06-07 13:59:59

import org.antlr.runtime.*;
import org.antlr.runtime.Token;
import org.antlr.runtime.tree.*;

public class ComplexParser extends Parser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "WHITESPACE", "SIGN", "SIGL", "SIGG", "SIGE", "SIGLE", "SIGGE", "MODD", "PREC", "MODT", "PRED", "PREN", "MODE", "MODN", "MODO", "MODC", "BOL", "NUM", "LIT", "LBRK", "RBRK", "BVAR", "ORDOP", "DELY", "VAR", "LPAR", "RPAR", "DEL", "CQM", "OQM", "LIMIT", "SELECT", "ASIGN", "COMPL", "COMPG", "COMPLE", "COMPGE", "COMPAS", "PRECON", "PREDE", "OFFSET", "ORDER", "COMPNAME", "MODTO", "NAME", "COMPRNG", "INTERVAL", "DEFINE"
    };
    public static final int SIGN=5;
    public static final int SIGL=6;
    public static final int ORDER=45;
    public static final int SIGE=8;
    public static final int COMPAS=41;
    public static final int LIMIT=34;
    public static final int SIGG=7;
    public static final int DEL=31;
    public static final int ORDOP=26;
    public static final int BOL=20;
    public static final int MODTO=47;
    public static final int COMPL=37;
    public static final int DEFINE=51;
    public static final int EOF=-1;
    public static final int DELY=27;
    public static final int INTERVAL=50;
    public static final int COMPG=38;
    public static final int NAME=48;
    public static final int LPAR=29;
    public static final int OFFSET=44;
    public static final int COMPLE=39;
    public static final int MODT=13;
    public static final int VAR=28;
    public static final int MODN=17;
    public static final int MODO=18;
    public static final int MODC=19;
    public static final int MODD=11;
    public static final int SELECT=35;
    public static final int RBRK=24;
    public static final int MODE=16;
    public static final int PREDE=43;
    public static final int COMPGE=40;
    public static final int BVAR=25;
    public static final int PRECON=42;
    public static final int PREN=15;
    public static final int PREC=12;
    public static final int LBRK=23;
    public static final int PRED=14;
    public static final int SIGGE=10;
    public static final int LIT=22;
    public static final int WHITESPACE=4;
    public static final int CQM=32;
    public static final int NUM=21;
    public static final int COMPNAME=46;
    public static final int SIGLE=9;
    public static final int ASIGN=36;
    public static final int RPAR=30;
    public static final int OQM=33;
    public static final int COMPRNG=49;

    // delegates
    // delegators


        public ComplexParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public ComplexParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);

        }

    protected TreeAdaptor adaptor = new CommonTreeAdaptor();

    public void setTreeAdaptor(TreeAdaptor adaptor) {
        this.adaptor = adaptor;
    }
    public TreeAdaptor getTreeAdaptor() {
        return adaptor;
    }

    @Override
    public String[] getTokenNames() { return ComplexParser.tokenNames; }
    	private int errCount = 0;
    	private boolean precon = false;
    	private boolean prede = false;

    	/*
    	 * Overrides default displayRecognitionError method in ANTLR to count total
    	 * recognition errors.
    	 */
    @Override
    	public void displayRecognitionError(String[] tokenNames, RecognitionException e) {
            	errCount++;
        	}

        	/*
        	 *  Gets the number of total recognition errors.
        	 */
        	public int getErrorCount() {
        		return errCount;
        	}

        	/*
        	 * Verifies if query AST has node 'PRECON'
        	 */
    	public boolean hasPrecon() {
    		return precon;
    	}

    	/*
        	 * Verifies if query AST has node 'PREDE'
        	 */
    	public boolean hasPrede() {
    		return prede;
    	}


    public static class squery_return extends ParserRuleReturnScope {
        Object tree;
        @Override
        public Object getTree() { return tree; }
    };

    // $ANTLR start "squery"
    public final ComplexParser.squery_return squery() throws RecognitionException {
        ComplexParser.squery_return retval = new ComplexParser.squery_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token EOF2=null;
        Token EOF6=null;
        Token EOF10=null;
        ComplexParser.dquery_return dquery1 = null;

        ComplexParser.limiter_return limiter3 = null;

        ComplexParser.oquery_return oquery4 = null;

        ComplexParser.modifier_return modifier5 = null;

        ComplexParser.limiter_return limiter7 = null;

        ComplexParser.pquery_return pquery8 = null;

        ComplexParser.modifier_return modifier9 = null;


        Object EOF2_tree=null;
        Object EOF6_tree=null;
        Object EOF10_tree=null;
        RewriteRuleTokenStream stream_EOF=new RewriteRuleTokenStream(adaptor,"token EOF");
        RewriteRuleSubtreeStream stream_limiter=new RewriteRuleSubtreeStream(adaptor,"rule limiter");
        RewriteRuleSubtreeStream stream_modifier=new RewriteRuleSubtreeStream(adaptor,"rule modifier");
        RewriteRuleSubtreeStream stream_pquery=new RewriteRuleSubtreeStream(adaptor,"rule pquery");
        RewriteRuleSubtreeStream stream_dquery=new RewriteRuleSubtreeStream(adaptor,"rule dquery");
        RewriteRuleSubtreeStream stream_oquery=new RewriteRuleSubtreeStream(adaptor,"rule oquery");
        try {
            int alt5=3;
            switch ( input.LA(1) ) {
            case MODD:
            case OQM:
                {
                alt5=1;
                }
                break;
            case NUM:
                {
                switch ( input.LA(2) ) {
                case BVAR:
                case VAR:
                    {
                    int LA5_4 = input.LA(3);

                    if ( (LA5_4==EOF||LA5_4==PREC||LA5_4==MODE||LA5_4==MODO) ) {
                        alt5=2;
                    }
                    else if ( (LA5_4==PRED||LA5_4==DEL) ) {
                        alt5=3;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return retval;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 5, 4, input);

                        throw nvae;
                    }
                    }
                    break;
                case MODT:
                    {
                    alt5=3;
                    }
                    break;
                case EOF:
                case MODE:
                case MODO:
                case LPAR:
                    {
                    alt5=2;
                    }
                    break;
                default:
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 5, 2, input);

                    throw nvae;
                }

                }
                break;
            case EOF:
            case MODE:
            case MODO:
            case LPAR:
                {
                alt5=2;
                }
                break;
            case BVAR:
            case VAR:
                {
                int LA5_4 = input.LA(2);

                if ( (LA5_4==EOF||LA5_4==PREC||LA5_4==MODE||LA5_4==MODO) ) {
                    alt5=2;
                }
                else if ( (LA5_4==PRED||LA5_4==DEL) ) {
                    alt5=3;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 5, 4, input);

                    throw nvae;
                }
                }
                break;
            case MODT:
                {
                alt5=3;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 5, 0, input);

                throw nvae;
            }

            switch (alt5) {
                case 1 :
                    {
                    pushFollow(FOLLOW_dquery_in_squery110);
                    dquery1=dquery();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_dquery.add(dquery1.getTree());
                    EOF2=(Token)match(input,EOF,FOLLOW_EOF_in_squery112); if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_EOF.add(EOF2);



                    // AST REWRITE
                    // elements: dquery
                    // token labels:
                    // rule labels: retval
                    // token list labels:
                    // rule list labels:
                    // wildcard labels:
                    if ( state.backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 52:14: -> ^( DEFINE dquery )
                    {
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(DEFINE, "DEFINE"), root_1);

                        adaptor.addChild(root_1, stream_dquery.nextTree());

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;}
                    }
                    break;
                case 2 :
                    {
                    int alt1=2;
                    int LA1_0 = input.LA(1);

                    if ( (LA1_0==NUM) ) {
                        alt1=1;
                    }
                    switch (alt1) {
                        case 1 :
                            {
                            pushFollow(FOLLOW_limiter_in_squery124);
                            limiter3=limiter();

                            state._fsp--;
                            if (state.failed) return retval;
                            if ( state.backtracking==0 ) stream_limiter.add(limiter3.getTree());

                            }
                            break;

                    }

                    pushFollow(FOLLOW_oquery_in_squery127);
                    oquery4=oquery();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_oquery.add(oquery4.getTree());
                    int alt2=2;
                    int LA2_0 = input.LA(1);

                    if ( (LA2_0==MODE||LA2_0==MODO) ) {
                        alt2=1;
                    }
                    switch (alt2) {
                        case 1 :
                            {
                            pushFollow(FOLLOW_modifier_in_squery129);
                            modifier5=modifier();

                            state._fsp--;
                            if (state.failed) return retval;
                            if ( state.backtracking==0 ) stream_modifier.add(modifier5.getTree());

                            }
                            break;

                    }

                    EOF6=(Token)match(input,EOF,FOLLOW_EOF_in_squery132); if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_EOF.add(EOF6);



                    // AST REWRITE
                    // elements: oquery, modifier, limiter
                    // token labels:
                    // rule labels: retval
                    // token list labels:
                    // rule list labels:
                    // wildcard labels:
                    if ( state.backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 53:33: -> ^( SELECT ( limiter )? oquery ( modifier )? )
                    {
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(SELECT, "SELECT"), root_1);

                        if ( stream_limiter.hasNext() ) {
                            adaptor.addChild(root_1, stream_limiter.nextTree());

                        }
                        stream_limiter.reset();
                        adaptor.addChild(root_1, stream_oquery.nextTree());
                        if ( stream_modifier.hasNext() ) {
                            adaptor.addChild(root_1, stream_modifier.nextTree());

                        }
                        stream_modifier.reset();

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;}
                    }
                    break;
                case 3 :
                    {
                    int alt3=2;
                    int LA3_0 = input.LA(1);

                    if ( (LA3_0==NUM) ) {
                        alt3=1;
                    }
                    switch (alt3) {
                        case 1 :
                            {
                            pushFollow(FOLLOW_limiter_in_squery150);
                            limiter7=limiter();

                            state._fsp--;
                            if (state.failed) return retval;
                            if ( state.backtracking==0 ) stream_limiter.add(limiter7.getTree());

                            }
                            break;

                    }

                    pushFollow(FOLLOW_pquery_in_squery153);
                    pquery8=pquery();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_pquery.add(pquery8.getTree());
                    int alt4=2;
                    int LA4_0 = input.LA(1);

                    if ( (LA4_0==MODE||LA4_0==MODO) ) {
                        alt4=1;
                    }
                    switch (alt4) {
                        case 1 :
                            {
                            pushFollow(FOLLOW_modifier_in_squery155);
                            modifier9=modifier();

                            state._fsp--;
                            if (state.failed) return retval;
                            if ( state.backtracking==0 ) stream_modifier.add(modifier9.getTree());

                            }
                            break;

                    }

                    EOF10=(Token)match(input,EOF,FOLLOW_EOF_in_squery158); if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_EOF.add(EOF10);



                    // AST REWRITE
                    // elements: modifier, pquery, limiter
                    // token labels:
                    // rule labels: retval
                    // token list labels:
                    // rule list labels:
                    // wildcard labels:
                    if ( state.backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 54:33: -> ^( SELECT ( limiter )? pquery ( modifier )? )
                    {
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(SELECT, "SELECT"), root_1);

                        if ( stream_limiter.hasNext() ) {
                            adaptor.addChild(root_1, stream_limiter.nextTree());

                        }
                        stream_limiter.reset();
                        adaptor.addChild(root_1, stream_pquery.nextTree());
                        if ( stream_modifier.hasNext() ) {
                            adaptor.addChild(root_1, stream_modifier.nextTree());

                        }
                        stream_modifier.reset();

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;}
                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            //reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "squery"

    public static class dquery_return extends ParserRuleReturnScope {
        Object tree;
        @Override
        public Object getTree() { return tree; }
    };

    // $ANTLR start "dquery"
    public final ComplexParser.dquery_return dquery() throws RecognitionException {
        ComplexParser.dquery_return retval = new ComplexParser.dquery_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token OQM11=null;
        Token MODD12=null;
        Token CQM14=null;
        ComplexParser.name_return name13 = null;


        Object OQM11_tree=null;
        Object MODD12_tree=null;
        Object CQM14_tree=null;
        RewriteRuleTokenStream stream_OQM=new RewriteRuleTokenStream(adaptor,"token OQM");
        RewriteRuleTokenStream stream_MODD=new RewriteRuleTokenStream(adaptor,"token MODD");
        RewriteRuleTokenStream stream_CQM=new RewriteRuleTokenStream(adaptor,"token CQM");
        RewriteRuleSubtreeStream stream_name=new RewriteRuleSubtreeStream(adaptor,"rule name");
        try {
            {
            int alt6=2;
            int LA6_0 = input.LA(1);

            if ( (LA6_0==OQM) ) {
                alt6=1;
            }
            switch (alt6) {
                case 1 :
                    {
                    OQM11=(Token)match(input,OQM,FOLLOW_OQM_in_dquery183); if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_OQM.add(OQM11);


                    }
                    break;

            }

            MODD12=(Token)match(input,MODD,FOLLOW_MODD_in_dquery186); if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_MODD.add(MODD12);

            pushFollow(FOLLOW_name_in_dquery188);
            name13=name();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_name.add(name13.getTree());
            CQM14=(Token)match(input,CQM,FOLLOW_CQM_in_dquery190); if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_CQM.add(CQM14);



            // AST REWRITE
            // elements: name
            // token labels:
            // rule labels: retval
            // token list labels:
            // rule list labels:
            // wildcard labels:
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 59:22: -> ^( name )
            {
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot(stream_name.nextNode(), root_1);

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;}
            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            //reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "dquery"

    public static class limiter_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "limiter"
    public final ComplexParser.limiter_return limiter() throws RecognitionException {
        ComplexParser.limiter_return retval = new ComplexParser.limiter_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token NUM15=null;

        Object NUM15_tree=null;
        RewriteRuleTokenStream stream_NUM=new RewriteRuleTokenStream(adaptor,"token NUM");

        try {
            {
            NUM15=(Token)match(input,NUM,FOLLOW_NUM_in_limiter207); if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_NUM.add(NUM15);



            // AST REWRITE
            // elements: NUM
            // token labels:
            // rule labels: retval
            // token list labels:
            // rule list labels:
            // wildcard labels:
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 64:7: -> ^( LIMIT NUM )
            {
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(LIMIT, "LIMIT"), root_1);

                adaptor.addChild(root_1, stream_NUM.nextNode());

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;}
            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            //reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "limiter"

    public static class modifier_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "modifier"
    public final ComplexParser.modifier_return modifier() throws RecognitionException {
        ComplexParser.modifier_return retval = new ComplexParser.modifier_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        ComplexParser.ordterm_return ordterm16 = null;

        ComplexParser.offsetterm_return offsetterm17 = null;

        ComplexParser.offsetterm_return offsetterm18 = null;



        try {
            int alt8=2;
            int LA8_0 = input.LA(1);

            if ( (LA8_0==MODO) ) {
                alt8=1;
            }
            else if ( (LA8_0==MODE) ) {
                alt8=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 8, 0, input);

                throw nvae;
            }
            switch (alt8) {
                case 1 :
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_ordterm_in_modifier226);
                    ordterm16=ordterm();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, ordterm16.getTree());
                    int alt7=2;
                    int LA7_0 = input.LA(1);

                    if ( (LA7_0==MODE) ) {
                        alt7=1;
                    }
                    switch (alt7) {
                        case 1 :
                            {
                            pushFollow(FOLLOW_offsetterm_in_modifier228);
                            offsetterm17=offsetterm();

                            state._fsp--;
                            if (state.failed) return retval;
                            if ( state.backtracking==0 ) adaptor.addChild(root_0, offsetterm17.getTree());

                            }
                            break;

                    }


                    }
                    break;
                case 2 :
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_offsetterm_in_modifier233);
                    offsetterm18=offsetterm();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, offsetterm18.getTree());

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            //reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "modifier"

    public static class offsetterm_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "offsetterm"
    public final ComplexParser.offsetterm_return offsetterm() throws RecognitionException {
        ComplexParser.offsetterm_return retval = new ComplexParser.offsetterm_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token MODE19=null;
        Token NUM20=null;

        Object MODE19_tree=null;
        Object NUM20_tree=null;
        RewriteRuleTokenStream stream_MODE=new RewriteRuleTokenStream(adaptor,"token MODE");
        RewriteRuleTokenStream stream_NUM=new RewriteRuleTokenStream(adaptor,"token NUM");

        try {
            {
            MODE19=(Token)match(input,MODE,FOLLOW_MODE_in_offsetterm244); if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_MODE.add(MODE19);

            NUM20=(Token)match(input,NUM,FOLLOW_NUM_in_offsetterm246); if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_NUM.add(NUM20);



            // AST REWRITE
            // elements: NUM
            // token labels:
            // rule labels: retval
            // token list labels:
            // rule list labels:
            // wildcard labels:
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 77:12: -> ^( OFFSET NUM )
            {
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(OFFSET, "OFFSET"), root_1);

                adaptor.addChild(root_1, stream_NUM.nextNode());

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;}
            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            //reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "offsetterm"

    public static class ordterm_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "ordterm"
    public final ComplexParser.ordterm_return ordterm() throws RecognitionException {
        ComplexParser.ordterm_return retval = new ComplexParser.ordterm_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token MODO21=null;
        Token LPAR22=null;
        Token RPAR24=null;
        ComplexParser.plist_return plist23 = null;


        Object MODO21_tree=null;
        Object LPAR22_tree=null;
        Object RPAR24_tree=null;
        RewriteRuleTokenStream stream_RPAR=new RewriteRuleTokenStream(adaptor,"token RPAR");
        RewriteRuleTokenStream stream_MODO=new RewriteRuleTokenStream(adaptor,"token MODO");
        RewriteRuleTokenStream stream_LPAR=new RewriteRuleTokenStream(adaptor,"token LPAR");
        RewriteRuleSubtreeStream stream_plist=new RewriteRuleSubtreeStream(adaptor,"rule plist");
        try {
            {
            MODO21=(Token)match(input,MODO,FOLLOW_MODO_in_ordterm265); if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_MODO.add(MODO21);

            LPAR22=(Token)match(input,LPAR,FOLLOW_LPAR_in_ordterm267); if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_LPAR.add(LPAR22);

            pushFollow(FOLLOW_plist_in_ordterm269);
            plist23=plist();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_plist.add(plist23.getTree());
            RPAR24=(Token)match(input,RPAR,FOLLOW_RPAR_in_ordterm271); if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_RPAR.add(RPAR24);



            // AST REWRITE
            // elements: plist
            // token labels:
            // rule labels: retval
            // token list labels:
            // rule list labels:
            // wildcard labels:
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 82:24: -> ^( ORDER plist )
            {
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(ORDER, "ORDER"), root_1);

                adaptor.addChild(root_1, stream_plist.nextTree());

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;}
            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            //reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "ordterm"

    public static class oquery_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "oquery"
    public final ComplexParser.oquery_return oquery() throws RecognitionException {
        ComplexParser.oquery_return retval = new ComplexParser.oquery_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token PREC27=null;
        Token LPAR29=null;
        Token RPAR31=null;
        ComplexParser.name_return name25 = null;

        ComplexParser.name_return name26 = null;

        ComplexParser.querylist_return querylist28 = null;

        ComplexParser.oquery_return oquery30 = null;


        Object PREC27_tree=null;
        Object LPAR29_tree=null;
        Object RPAR31_tree=null;
        RewriteRuleTokenStream stream_PREC=new RewriteRuleTokenStream(adaptor,"token PREC");
        RewriteRuleSubtreeStream stream_name=new RewriteRuleSubtreeStream(adaptor,"rule name");
        RewriteRuleSubtreeStream stream_querylist=new RewriteRuleSubtreeStream(adaptor,"rule querylist");
        try {
            int alt9=4;
            switch ( input.LA(1) ) {
            case EOF:
            case MODE:
            case MODO:
            case RPAR:
            case DEL:
                {
                alt9=1;
                }
                break;
            case BVAR:
            case VAR:
                {
                int LA9_2 = input.LA(2);

                if ( (LA9_2==PREC) ) {
                    alt9=3;
                }
                else if ( (LA9_2==EOF||LA9_2==MODE||LA9_2==MODO||(LA9_2>=RPAR && LA9_2<=DEL)) ) {
                    alt9=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 9, 2, input);

                    throw nvae;
                }
                }
                break;
            case LPAR:
                {
                alt9=4;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 9, 0, input);

                throw nvae;
            }

            switch (alt9) {
                case 1 :
                    {
                    root_0 = (Object)adaptor.nil();

                    }
                    break;
                case 2 :
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_name_in_oquery293);
                    name25=name();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, name25.getTree());

                    }
                    break;
                case 3 :
                    {
                    pushFollow(FOLLOW_name_in_oquery297);
                    name26=name();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_name.add(name26.getTree());
                    PREC27=(Token)match(input,PREC,FOLLOW_PREC_in_oquery299); if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_PREC.add(PREC27);

                    pushFollow(FOLLOW_querylist_in_oquery301);
                    querylist28=querylist();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_querylist.add(querylist28.getTree());
                    if ( state.backtracking==0 ) {
                      precon = true;
                    }


                    // AST REWRITE
                    // elements: querylist, name
                    // token labels:
                    // rule labels: retval
                    // token list labels:
                    // rule list labels:
                    // wildcard labels:
                    if ( state.backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 92:40: -> ^( name ^( PRECON querylist ) )
                    {
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot(stream_name.nextNode(), root_1);

                        {
                        Object root_2 = (Object)adaptor.nil();
                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(PRECON, "PRECON"), root_2);

                        adaptor.addChild(root_2, stream_querylist.nextTree());

                        adaptor.addChild(root_1, root_2);
                        }

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;}
                    }
                    break;
                case 4 :
                    {
                    root_0 = (Object)adaptor.nil();

                    LPAR29=(Token)match(input,LPAR,FOLLOW_LPAR_in_oquery319); if (state.failed) return retval;
                    pushFollow(FOLLOW_oquery_in_oquery322);
                    oquery30=oquery();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, oquery30.getTree());
                    RPAR31=(Token)match(input,RPAR,FOLLOW_RPAR_in_oquery324); if (state.failed) return retval;

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            //reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "oquery"

    public static class querylist_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "querylist"
    public final ComplexParser.querylist_return querylist() throws RecognitionException {
        ComplexParser.querylist_return retval = new ComplexParser.querylist_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token DEL33=null;
        Token DEL36=null;
        ComplexParser.oquery_return oquery32 = null;

        ComplexParser.querylist_return querylist34 = null;

        ComplexParser.sent_return sent35 = null;

        ComplexParser.querylist_return querylist37 = null;

        ComplexParser.oquery_return oquery38 = null;

        ComplexParser.sent_return sent39 = null;


        Object DEL33_tree=null;
        Object DEL36_tree=null;

        try {
            int alt10=4;
            alt10 = dfa10.predict(input);
            switch (alt10) {
                case 1 :
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_oquery_in_querylist336);
                    oquery32=oquery();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, oquery32.getTree());
                    DEL33=(Token)match(input,DEL,FOLLOW_DEL_in_querylist338); if (state.failed) return retval;
                    pushFollow(FOLLOW_querylist_in_querylist341);
                    querylist34=querylist();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, querylist34.getTree());

                    }
                    break;
                case 2 :
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_sent_in_querylist345);
                    sent35=sent();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, sent35.getTree());
                    DEL36=(Token)match(input,DEL,FOLLOW_DEL_in_querylist347); if (state.failed) return retval;
                    pushFollow(FOLLOW_querylist_in_querylist350);
                    querylist37=querylist();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, querylist37.getTree());

                    }
                    break;
                case 3 :
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_oquery_in_querylist354);
                    oquery38=oquery();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, oquery38.getTree());

                    }
                    break;
                case 4 :
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_sent_in_querylist358);
                    sent39=sent();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, sent39.getTree());

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            //reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "querylist"

    public static class pquery_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "pquery"
    public final ComplexParser.pquery_return pquery() throws RecognitionException {
        ComplexParser.pquery_return retval = new ComplexParser.pquery_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token PRED41=null;
        Token MODT43=null;
        Token PRED44=null;
        ComplexParser.plist_return plist40 = null;

        ComplexParser.oquery_return oquery42 = null;

        ComplexParser.oquery_return oquery45 = null;


        Object PRED41_tree=null;
        Object MODT43_tree=null;
        Object PRED44_tree=null;
        RewriteRuleTokenStream stream_MODT=new RewriteRuleTokenStream(adaptor,"token MODT");
        RewriteRuleTokenStream stream_PRED=new RewriteRuleTokenStream(adaptor,"token PRED");
        RewriteRuleSubtreeStream stream_plist=new RewriteRuleSubtreeStream(adaptor,"rule plist");
        RewriteRuleSubtreeStream stream_oquery=new RewriteRuleSubtreeStream(adaptor,"rule oquery");
        try {
            int alt11=2;
            int LA11_0 = input.LA(1);

            if ( (LA11_0==BVAR||LA11_0==VAR) ) {
                alt11=1;
            }
            else if ( (LA11_0==MODT) ) {
                alt11=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 11, 0, input);

                throw nvae;
            }
            switch (alt11) {
                case 1 :
                    {
                    pushFollow(FOLLOW_plist_in_pquery369);
                    plist40=plist();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_plist.add(plist40.getTree());
                    PRED41=(Token)match(input,PRED,FOLLOW_PRED_in_pquery371); if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_PRED.add(PRED41);

                    pushFollow(FOLLOW_oquery_in_pquery373);
                    oquery42=oquery();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_oquery.add(oquery42.getTree());
                    if ( state.backtracking==0 ) {
                      prede = true;
                    }


                    // AST REWRITE
                    // elements: plist, oquery
                    // token labels:
                    // rule labels: retval
                    // token list labels:
                    // rule list labels:
                    // wildcard labels:
                    if ( state.backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 106:37: -> ^( oquery ^( PREDE plist ) )
                    {
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot(stream_oquery.nextNode(), root_1);

                        {
                        Object root_2 = (Object)adaptor.nil();
                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(PREDE, "PREDE"), root_2);

                        adaptor.addChild(root_2, stream_plist.nextTree());

                        adaptor.addChild(root_1, root_2);
                        }

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;}
                    }
                    break;
                case 2 :
                    {
                    MODT43=(Token)match(input,MODT,FOLLOW_MODT_in_pquery391); if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_MODT.add(MODT43);

                    PRED44=(Token)match(input,PRED,FOLLOW_PRED_in_pquery393); if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_PRED.add(PRED44);

                    pushFollow(FOLLOW_oquery_in_pquery395);
                    oquery45=oquery();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_oquery.add(oquery45.getTree());
                    if ( state.backtracking==0 ) {
                      prede = true;
                    }


                    // AST REWRITE
                    // elements: oquery
                    // token labels:
                    // rule labels: retval
                    // token list labels:
                    // rule list labels:
                    // wildcard labels:
                    if ( state.backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 107:36: -> ^( oquery ^( PREDE MODTO ) )
                    {
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot(stream_oquery.nextNode(), root_1);

                        {
                        Object root_2 = (Object)adaptor.nil();
                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(PREDE, "PREDE"), root_2);

                        adaptor.addChild(root_2, (Object)adaptor.create(MODTO, "MODTO"));

                        adaptor.addChild(root_1, root_2);
                        }

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;}
                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            //reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "pquery"

    public static class plist_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "plist"
    public final ComplexParser.plist_return plist() throws RecognitionException {
        ComplexParser.plist_return retval = new ComplexParser.plist_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token DEL47=null;
        ComplexParser.name_return name46 = null;

        ComplexParser.plist_return plist48 = null;

        ComplexParser.name_return name49 = null;


        Object DEL47_tree=null;

        try {
            int alt12=2;
            int LA12_0 = input.LA(1);

            if ( (LA12_0==BVAR||LA12_0==VAR) ) {
                int LA12_1 = input.LA(2);

                if ( (LA12_1==EOF||LA12_1==PRED||LA12_1==RPAR) ) {
                    alt12=2;
                }
                else if ( (LA12_1==DEL) ) {
                    alt12=1;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 12, 1, input);

                    throw nvae;
                }
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 12, 0, input);

                throw nvae;
            }
            switch (alt12) {
                case 1 :
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_name_in_plist420);
                    name46=name();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, name46.getTree());
                    DEL47=(Token)match(input,DEL,FOLLOW_DEL_in_plist422); if (state.failed) return retval;
                    pushFollow(FOLLOW_plist_in_plist425);
                    plist48=plist();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, plist48.getTree());

                    }
                    break;
                case 2 :
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_name_in_plist429);
                    name49=name();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, name49.getTree());

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            //reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "plist"

    public static class name_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "name"
    public final ComplexParser.name_return name() throws RecognitionException {
        ComplexParser.name_return retval = new ComplexParser.name_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token set50=null;

        Object set50_tree=null;

        try {
            {
            root_0 = (Object)adaptor.nil();

            set50=(Token)input.LT(1);
            if ( input.LA(1)==BVAR||input.LA(1)==VAR ) {
                input.consume();
                if ( state.backtracking==0 ) adaptor.addChild(root_0, (Object)adaptor.create(set50));
                state.errorRecovery=false;state.failed=false;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }


            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            //reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "name"

    public static class sent_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "sent"
    public final ComplexParser.sent_return sent() throws RecognitionException {
        ComplexParser.sent_return retval = new ComplexParser.sent_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token SIGE52=null;
        Token VAR54=null;
        Token SIGL55=null;
        Token SIGL58=null;
        Token VAR60=null;
        Token SIGG61=null;
        Token SIGG64=null;
        Token VAR66=null;
        Token SIGLE67=null;
        Token SIGLE70=null;
        Token VAR72=null;
        Token SIGGE73=null;
        Token SIGGE76=null;
        Token VAR78=null;
        Token MODC79=null;
        Token MODC82=null;
        Token MODN85=null;
        Token DELY87=null;
        Token VAR89=null;
        ComplexParser.name_return name51 = null;

        ComplexParser.val_return val53 = null;

        ComplexParser.val_return val56 = null;

        ComplexParser.name_return name57 = null;

        ComplexParser.val_return val59 = null;

        ComplexParser.val_return val62 = null;

        ComplexParser.name_return name63 = null;

        ComplexParser.val_return val65 = null;

        ComplexParser.val_return val68 = null;

        ComplexParser.name_return name69 = null;

        ComplexParser.val_return val71 = null;

        ComplexParser.val_return val74 = null;

        ComplexParser.name_return name75 = null;

        ComplexParser.val_return val77 = null;

        ComplexParser.val_return val80 = null;

        ComplexParser.name_return name81 = null;

        ComplexParser.val_return val83 = null;

        ComplexParser.name_return name84 = null;

        ComplexParser.val_return val86 = null;

        ComplexParser.val_return val88 = null;


        Object SIGE52_tree=null;
        Object VAR54_tree=null;
        Object SIGL55_tree=null;
        Object SIGL58_tree=null;
        Object VAR60_tree=null;
        Object SIGG61_tree=null;
        Object SIGG64_tree=null;
        Object VAR66_tree=null;
        Object SIGLE67_tree=null;
        Object SIGLE70_tree=null;
        Object VAR72_tree=null;
        Object SIGGE73_tree=null;
        Object SIGGE76_tree=null;
        Object VAR78_tree=null;
        Object MODC79_tree=null;
        Object MODC82_tree=null;
        Object MODN85_tree=null;
        Object DELY87_tree=null;
        Object VAR89_tree=null;
        RewriteRuleTokenStream stream_SIGLE=new RewriteRuleTokenStream(adaptor,"token SIGLE");
        RewriteRuleTokenStream stream_MODN=new RewriteRuleTokenStream(adaptor,"token MODN");
        RewriteRuleTokenStream stream_VAR=new RewriteRuleTokenStream(adaptor,"token VAR");
        RewriteRuleTokenStream stream_SIGL=new RewriteRuleTokenStream(adaptor,"token SIGL");
        RewriteRuleTokenStream stream_MODC=new RewriteRuleTokenStream(adaptor,"token MODC");
        RewriteRuleTokenStream stream_SIGE=new RewriteRuleTokenStream(adaptor,"token SIGE");
        RewriteRuleTokenStream stream_DELY=new RewriteRuleTokenStream(adaptor,"token DELY");
        RewriteRuleTokenStream stream_SIGG=new RewriteRuleTokenStream(adaptor,"token SIGG");
        RewriteRuleTokenStream stream_SIGGE=new RewriteRuleTokenStream(adaptor,"token SIGGE");
        RewriteRuleSubtreeStream stream_val=new RewriteRuleSubtreeStream(adaptor,"rule val");
        RewriteRuleSubtreeStream stream_name=new RewriteRuleSubtreeStream(adaptor,"rule name");
        try {
            int alt14=12;
            alt14 = dfa14.predict(input);
            switch (alt14) {
                case 1 :
                    {
                    pushFollow(FOLLOW_name_in_sent455);
                    name51=name();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_name.add(name51.getTree());
                    SIGE52=(Token)match(input,SIGE,FOLLOW_SIGE_in_sent457); if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_SIGE.add(SIGE52);

                    pushFollow(FOLLOW_val_in_sent459);
                    val53=val();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_val.add(val53.getTree());


                    // AST REWRITE
                    // elements: val, name
                    // token labels:
                    // rule labels: retval
                    // token list labels:
                    // rule list labels:
                    // wildcard labels:
                    if ( state.backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 124:17: -> ^( ASIGN name val )
                    {
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(ASIGN, "ASIGN"), root_1);

                        adaptor.addChild(root_1, stream_name.nextTree());
                        adaptor.addChild(root_1, stream_val.nextTree());

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;}
                    }
                    break;
                case 2 :
                    {
                    VAR54=(Token)match(input,VAR,FOLLOW_VAR_in_sent473); if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_VAR.add(VAR54);

                    SIGL55=(Token)match(input,SIGL,FOLLOW_SIGL_in_sent475); if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_SIGL.add(SIGL55);

                    pushFollow(FOLLOW_val_in_sent477);
                    val56=val();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_val.add(val56.getTree());


                    // AST REWRITE
                    // elements: VAR, val
                    // token labels:
                    // rule labels: retval
                    // token list labels:
                    // rule list labels:
                    // wildcard labels:
                    if ( state.backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 125:16: -> ^( COMPL VAR val )
                    {
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(COMPL, "COMPL"), root_1);

                        adaptor.addChild(root_1, stream_VAR.nextNode());
                        adaptor.addChild(root_1, stream_val.nextTree());

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;}
                    }
                    break;
                case 3 :
                    {
                    pushFollow(FOLLOW_name_in_sent491);
                    name57=name();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_name.add(name57.getTree());
                    SIGL58=(Token)match(input,SIGL,FOLLOW_SIGL_in_sent493); if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_SIGL.add(SIGL58);

                    pushFollow(FOLLOW_val_in_sent495);
                    val59=val();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_val.add(val59.getTree());


                    // AST REWRITE
                    // elements: name, val
                    // token labels:
                    // rule labels: retval
                    // token list labels:
                    // rule list labels:
                    // wildcard labels:
                    if ( state.backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 126:17: -> ^( COMPL name val )
                    {
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(COMPL, "COMPL"), root_1);

                        adaptor.addChild(root_1, stream_name.nextTree());
                        adaptor.addChild(root_1, stream_val.nextTree());

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;}
                    }
                    break;
                case 4 :
                    {
                    VAR60=(Token)match(input,VAR,FOLLOW_VAR_in_sent509); if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_VAR.add(VAR60);

                    SIGG61=(Token)match(input,SIGG,FOLLOW_SIGG_in_sent511); if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_SIGG.add(SIGG61);

                    pushFollow(FOLLOW_val_in_sent513);
                    val62=val();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_val.add(val62.getTree());


                    // AST REWRITE
                    // elements: VAR, val
                    // token labels:
                    // rule labels: retval
                    // token list labels:
                    // rule list labels:
                    // wildcard labels:
                    if ( state.backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 127:16: -> ^( COMPG VAR val )
                    {
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(COMPG, "COMPG"), root_1);

                        adaptor.addChild(root_1, stream_VAR.nextNode());
                        adaptor.addChild(root_1, stream_val.nextTree());

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;}
                    }
                    break;
                case 5 :
                    {
                    pushFollow(FOLLOW_name_in_sent527);
                    name63=name();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_name.add(name63.getTree());
                    SIGG64=(Token)match(input,SIGG,FOLLOW_SIGG_in_sent529); if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_SIGG.add(SIGG64);

                    pushFollow(FOLLOW_val_in_sent531);
                    val65=val();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_val.add(val65.getTree());


                    // AST REWRITE
                    // elements: val, name
                    // token labels:
                    // rule labels: retval
                    // token list labels:
                    // rule list labels:
                    // wildcard labels:
                    if ( state.backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 128:17: -> ^( COMPG name val )
                    {
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(COMPG, "COMPG"), root_1);

                        adaptor.addChild(root_1, stream_name.nextTree());
                        adaptor.addChild(root_1, stream_val.nextTree());

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;}
                    }
                    break;
                case 6 :
                    {
                    VAR66=(Token)match(input,VAR,FOLLOW_VAR_in_sent545); if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_VAR.add(VAR66);

                    SIGLE67=(Token)match(input,SIGLE,FOLLOW_SIGLE_in_sent547); if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_SIGLE.add(SIGLE67);

                    pushFollow(FOLLOW_val_in_sent549);
                    val68=val();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_val.add(val68.getTree());


                    // AST REWRITE
                    // elements: val, VAR
                    // token labels:
                    // rule labels: retval
                    // token list labels:
                    // rule list labels:
                    // wildcard labels:
                    if ( state.backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 129:17: -> ^( COMPLE VAR val )
                    {
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(COMPLE, "COMPLE"), root_1);

                        adaptor.addChild(root_1, stream_VAR.nextNode());
                        adaptor.addChild(root_1, stream_val.nextTree());

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;}
                    }
                    break;
                case 7 :
                    {
                    pushFollow(FOLLOW_name_in_sent563);
                    name69=name();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_name.add(name69.getTree());
                    SIGLE70=(Token)match(input,SIGLE,FOLLOW_SIGLE_in_sent565); if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_SIGLE.add(SIGLE70);

                    pushFollow(FOLLOW_val_in_sent567);
                    val71=val();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_val.add(val71.getTree());


                    // AST REWRITE
                    // elements: val, name
                    // token labels:
                    // rule labels: retval
                    // token list labels:
                    // rule list labels:
                    // wildcard labels:
                    if ( state.backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 130:18: -> ^( COMPLE name val )
                    {
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(COMPLE, "COMPLE"), root_1);

                        adaptor.addChild(root_1, stream_name.nextTree());
                        adaptor.addChild(root_1, stream_val.nextTree());

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;}
                    }
                    break;
                case 8 :
                    {
                    VAR72=(Token)match(input,VAR,FOLLOW_VAR_in_sent581); if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_VAR.add(VAR72);

                    SIGGE73=(Token)match(input,SIGGE,FOLLOW_SIGGE_in_sent583); if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_SIGGE.add(SIGGE73);

                    pushFollow(FOLLOW_val_in_sent585);
                    val74=val();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_val.add(val74.getTree());


                    // AST REWRITE
                    // elements: val, VAR
                    // token labels:
                    // rule labels: retval
                    // token list labels:
                    // rule list labels:
                    // wildcard labels:
                    if ( state.backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 131:17: -> ^( COMPGE VAR val )
                    {
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(COMPGE, "COMPGE"), root_1);

                        adaptor.addChild(root_1, stream_VAR.nextNode());
                        adaptor.addChild(root_1, stream_val.nextTree());

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;}
                    }
                    break;
                case 9 :
                    {
                    pushFollow(FOLLOW_name_in_sent599);
                    name75=name();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_name.add(name75.getTree());
                    SIGGE76=(Token)match(input,SIGGE,FOLLOW_SIGGE_in_sent601); if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_SIGGE.add(SIGGE76);

                    pushFollow(FOLLOW_val_in_sent603);
                    val77=val();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_val.add(val77.getTree());


                    // AST REWRITE
                    // elements: name, val
                    // token labels:
                    // rule labels: retval
                    // token list labels:
                    // rule list labels:
                    // wildcard labels:
                    if ( state.backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 132:18: -> ^( COMPGE name val )
                    {
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(COMPGE, "COMPGE"), root_1);

                        adaptor.addChild(root_1, stream_name.nextTree());
                        adaptor.addChild(root_1, stream_val.nextTree());

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;}
                    }
                    break;
                case 10 :
                    {
                    VAR78=(Token)match(input,VAR,FOLLOW_VAR_in_sent624); if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_VAR.add(VAR78);

                    MODC79=(Token)match(input,MODC,FOLLOW_MODC_in_sent626); if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_MODC.add(MODC79);

                    pushFollow(FOLLOW_val_in_sent628);
                    val80=val();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_val.add(val80.getTree());


                    // AST REWRITE
                    // elements: VAR, val
                    // token labels:
                    // rule labels: retval
                    // token list labels:
                    // rule list labels:
                    // wildcard labels:
                    if ( state.backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 133:23: -> ^( COMPAS VAR val )
                    {
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(COMPAS, "COMPAS"), root_1);

                        adaptor.addChild(root_1, stream_VAR.nextNode());
                        adaptor.addChild(root_1, stream_val.nextTree());

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;}
                    }
                    break;
                case 11 :
                    {
                    pushFollow(FOLLOW_name_in_sent649);
                    name81=name();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_name.add(name81.getTree());
                    MODC82=(Token)match(input,MODC,FOLLOW_MODC_in_sent651); if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_MODC.add(MODC82);

                    pushFollow(FOLLOW_val_in_sent653);
                    val83=val();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_val.add(val83.getTree());


                    // AST REWRITE
                    // elements: val, name
                    // token labels:
                    // rule labels: retval
                    // token list labels:
                    // rule list labels:
                    // wildcard labels:
                    if ( state.backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 134:24: -> ^( COMPAS name val )
                    {
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(COMPAS, "COMPAS"), root_1);

                        adaptor.addChild(root_1, stream_name.nextTree());
                        adaptor.addChild(root_1, stream_val.nextTree());

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;}
                    }
                    break;
                case 12 :
                    {
                    pushFollow(FOLLOW_name_in_sent674);
                    name84=name();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_name.add(name84.getTree());
                    MODN85=(Token)match(input,MODN,FOLLOW_MODN_in_sent676); if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_MODN.add(MODN85);

                    pushFollow(FOLLOW_val_in_sent678);
                    val86=val();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_val.add(val86.getTree());
                    DELY87=(Token)match(input,DELY,FOLLOW_DELY_in_sent680); if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_DELY.add(DELY87);

                    pushFollow(FOLLOW_val_in_sent682);
                    val88=val();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_val.add(val88.getTree());
                    int alt13=2;
                    int LA13_0 = input.LA(1);

                    if ( (LA13_0==VAR) ) {
                        alt13=1;
                    }
                    switch (alt13) {
                        case 1 :
                            {
                            VAR89=(Token)match(input,VAR,FOLLOW_VAR_in_sent684); if (state.failed) return retval;
                            if ( state.backtracking==0 ) stream_VAR.add(VAR89);


                            }
                            break;

                    }



                    // AST REWRITE
                    // elements: name, val, val
                    // token labels:
                    // rule labels: retval
                    // token list labels:
                    // rule list labels:
                    // wildcard labels:
                    if ( state.backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 135:38: -> ^( COMPRNG name ^( INTERVAL val val ) )
                    {
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(COMPRNG, "COMPRNG"), root_1);

                        adaptor.addChild(root_1, stream_name.nextTree());
                        {
                        Object root_2 = (Object)adaptor.nil();
                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(INTERVAL, "INTERVAL"), root_2);

                        adaptor.addChild(root_2, stream_val.nextTree());
                        adaptor.addChild(root_2, stream_val.nextTree());

                        adaptor.addChild(root_1, root_2);
                        }

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;}
                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            //reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "sent"

    public static class val_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "val"
    public final ComplexParser.val_return val() throws RecognitionException {
        ComplexParser.val_return retval = new ComplexParser.val_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token set90=null;

        Object set90_tree=null;

        try {
            {
            root_0 = (Object)adaptor.nil();

            set90=(Token)input.LT(1);
            if ( (input.LA(1)>=BOL && input.LA(1)<=LIT) ) {
                input.consume();
                if ( state.backtracking==0 ) adaptor.addChild(root_0, (Object)adaptor.create(set90));
                state.errorRecovery=false;state.failed=false;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }


            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            //reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "val"

    // $ANTLR start synpred13_ComplexParser
    public final void synpred13_ComplexParser_fragment() throws RecognitionException {
        {
        pushFollow(FOLLOW_oquery_in_synpred13_ComplexParser336);
        oquery();

        state._fsp--;
        if (state.failed) return ;
        match(input,DEL,FOLLOW_DEL_in_synpred13_ComplexParser338); if (state.failed) return ;
        pushFollow(FOLLOW_querylist_in_synpred13_ComplexParser341);
        querylist();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred13_ComplexParser

    // $ANTLR start synpred14_ComplexParser
    public final void synpred14_ComplexParser_fragment() throws RecognitionException {
        {
        pushFollow(FOLLOW_sent_in_synpred14_ComplexParser345);
        sent();

        state._fsp--;
        if (state.failed) return ;
        match(input,DEL,FOLLOW_DEL_in_synpred14_ComplexParser347); if (state.failed) return ;
        pushFollow(FOLLOW_querylist_in_synpred14_ComplexParser350);
        querylist();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred14_ComplexParser

    // $ANTLR start synpred15_ComplexParser
    public final void synpred15_ComplexParser_fragment() throws RecognitionException {
        {
        pushFollow(FOLLOW_oquery_in_synpred15_ComplexParser354);
        oquery();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred15_ComplexParser

    // $ANTLR start synpred20_ComplexParser
    public final void synpred20_ComplexParser_fragment() throws RecognitionException {
        {
        match(input,VAR,FOLLOW_VAR_in_synpred20_ComplexParser473); if (state.failed) return ;
        match(input,SIGL,FOLLOW_SIGL_in_synpred20_ComplexParser475); if (state.failed) return ;
        pushFollow(FOLLOW_val_in_synpred20_ComplexParser477);
        val();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred20_ComplexParser

    // $ANTLR start synpred21_ComplexParser
    public final void synpred21_ComplexParser_fragment() throws RecognitionException {
        {
        pushFollow(FOLLOW_name_in_synpred21_ComplexParser491);
        name();

        state._fsp--;
        if (state.failed) return ;
        match(input,SIGL,FOLLOW_SIGL_in_synpred21_ComplexParser493); if (state.failed) return ;
        pushFollow(FOLLOW_val_in_synpred21_ComplexParser495);
        val();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred21_ComplexParser

    // $ANTLR start synpred22_ComplexParser
    public final void synpred22_ComplexParser_fragment() throws RecognitionException {
        {
        match(input,VAR,FOLLOW_VAR_in_synpred22_ComplexParser509); if (state.failed) return ;
        match(input,SIGG,FOLLOW_SIGG_in_synpred22_ComplexParser511); if (state.failed) return ;
        pushFollow(FOLLOW_val_in_synpred22_ComplexParser513);
        val();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred22_ComplexParser

    // $ANTLR start synpred23_ComplexParser
    public final void synpred23_ComplexParser_fragment() throws RecognitionException {
        {
        pushFollow(FOLLOW_name_in_synpred23_ComplexParser527);
        name();

        state._fsp--;
        if (state.failed) return ;
        match(input,SIGG,FOLLOW_SIGG_in_synpred23_ComplexParser529); if (state.failed) return ;
        pushFollow(FOLLOW_val_in_synpred23_ComplexParser531);
        val();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred23_ComplexParser

    // $ANTLR start synpred24_ComplexParser
    public final void synpred24_ComplexParser_fragment() throws RecognitionException {
        {
        match(input,VAR,FOLLOW_VAR_in_synpred24_ComplexParser545); if (state.failed) return ;
        match(input,SIGLE,FOLLOW_SIGLE_in_synpred24_ComplexParser547); if (state.failed) return ;
        pushFollow(FOLLOW_val_in_synpred24_ComplexParser549);
        val();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred24_ComplexParser

    // $ANTLR start synpred25_ComplexParser
    public final void synpred25_ComplexParser_fragment() throws RecognitionException {
        {
        pushFollow(FOLLOW_name_in_synpred25_ComplexParser563);
        name();

        state._fsp--;
        if (state.failed) return ;
        match(input,SIGLE,FOLLOW_SIGLE_in_synpred25_ComplexParser565); if (state.failed) return ;
        pushFollow(FOLLOW_val_in_synpred25_ComplexParser567);
        val();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred25_ComplexParser

    // $ANTLR start synpred26_ComplexParser
    public final void synpred26_ComplexParser_fragment() throws RecognitionException {
        {
        match(input,VAR,FOLLOW_VAR_in_synpred26_ComplexParser581); if (state.failed) return ;
        match(input,SIGGE,FOLLOW_SIGGE_in_synpred26_ComplexParser583); if (state.failed) return ;
        pushFollow(FOLLOW_val_in_synpred26_ComplexParser585);
        val();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred26_ComplexParser

    // $ANTLR start synpred27_ComplexParser
    public final void synpred27_ComplexParser_fragment() throws RecognitionException {
        {
        pushFollow(FOLLOW_name_in_synpred27_ComplexParser599);
        name();

        state._fsp--;
        if (state.failed) return ;
        match(input,SIGGE,FOLLOW_SIGGE_in_synpred27_ComplexParser601); if (state.failed) return ;
        pushFollow(FOLLOW_val_in_synpred27_ComplexParser603);
        val();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred27_ComplexParser

    // $ANTLR start synpred28_ComplexParser
    public final void synpred28_ComplexParser_fragment() throws RecognitionException {
        {
        match(input,VAR,FOLLOW_VAR_in_synpred28_ComplexParser624); if (state.failed) return ;
        match(input,MODC,FOLLOW_MODC_in_synpred28_ComplexParser626); if (state.failed) return ;
        pushFollow(FOLLOW_val_in_synpred28_ComplexParser628);
        val();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred28_ComplexParser

    // $ANTLR start synpred29_ComplexParser
    public final void synpred29_ComplexParser_fragment() throws RecognitionException {
        {
        pushFollow(FOLLOW_name_in_synpred29_ComplexParser649);
        name();

        state._fsp--;
        if (state.failed) return ;
        match(input,MODC,FOLLOW_MODC_in_synpred29_ComplexParser651); if (state.failed) return ;
        pushFollow(FOLLOW_val_in_synpred29_ComplexParser653);
        val();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred29_ComplexParser

    // Delegated rules

    public final boolean synpred28_ComplexParser() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred28_ComplexParser_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred29_ComplexParser() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred29_ComplexParser_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred13_ComplexParser() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred13_ComplexParser_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred25_ComplexParser() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred25_ComplexParser_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred14_ComplexParser() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred14_ComplexParser_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred21_ComplexParser() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred21_ComplexParser_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred22_ComplexParser() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred22_ComplexParser_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred26_ComplexParser() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred26_ComplexParser_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred23_ComplexParser() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred23_ComplexParser_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred27_ComplexParser() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred27_ComplexParser_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred20_ComplexParser() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred20_ComplexParser_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred24_ComplexParser() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred24_ComplexParser_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred15_ComplexParser() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred15_ComplexParser_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }


    protected DFA10 dfa10 = new DFA10(this);
    protected DFA14 dfa14 = new DFA14(this);
    static final String DFA10_eotS =
        "\14\uffff";
    static final String DFA10_eofS =
        "\1\5\13\uffff";
    static final String DFA10_minS =
        "\1\20\4\0\7\uffff";
    static final String DFA10_maxS =
        "\1\37\4\0\7\uffff";
    static final String DFA10_acceptS =
        "\5\uffff\1\3\3\uffff\1\1\1\2\1\4";
    static final String DFA10_specialS =
        "\1\uffff\1\0\1\1\1\2\1\3\7\uffff}>";
    static final String[] DFA10_transitionS = {
            "\1\5\1\uffff\1\5\6\uffff\1\4\2\uffff\1\2\1\3\1\5\1\1",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA10_eot = DFA.unpackEncodedString(DFA10_eotS);
    static final short[] DFA10_eof = DFA.unpackEncodedString(DFA10_eofS);
    static final char[] DFA10_min = DFA.unpackEncodedStringToUnsignedChars(DFA10_minS);
    static final char[] DFA10_max = DFA.unpackEncodedStringToUnsignedChars(DFA10_maxS);
    static final short[] DFA10_accept = DFA.unpackEncodedString(DFA10_acceptS);
    static final short[] DFA10_special = DFA.unpackEncodedString(DFA10_specialS);
    static final short[][] DFA10_transition;

    static {
        int numStates = DFA10_transitionS.length;
        DFA10_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA10_transition[i] = DFA.unpackEncodedString(DFA10_transitionS[i]);
        }
    }

    class DFA10 extends DFA {

        public DFA10(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 10;
            this.eot = DFA10_eot;
            this.eof = DFA10_eof;
            this.min = DFA10_min;
            this.max = DFA10_max;
            this.accept = DFA10_accept;
            this.special = DFA10_special;
            this.transition = DFA10_transition;
        }
        @Override
        public String getDescription() {
            return "97:1: querylist : ( oquery DEL querylist | sent DEL querylist | oquery | sent );";
        }
        @Override
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 :
                        int LA10_1 = input.LA(1);


                        int index10_1 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred13_ComplexParser()) ) {s = 9;}

                        else if ( (synpred15_ComplexParser()) ) {s = 5;}


                        input.seek(index10_1);
                        if ( s>=0 ) return s;
                        break;
                    case 1 :
                        int LA10_2 = input.LA(1);


                        int index10_2 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred13_ComplexParser()) ) {s = 9;}

                        else if ( (synpred14_ComplexParser()) ) {s = 10;}

                        else if ( (synpred15_ComplexParser()) ) {s = 5;}

                        else if ( (true) ) {s = 11;}


                        input.seek(index10_2);
                        if ( s>=0 ) return s;
                        break;
                    case 2 :
                        int LA10_3 = input.LA(1);


                        int index10_3 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred13_ComplexParser()) ) {s = 9;}

                        else if ( (synpred15_ComplexParser()) ) {s = 5;}


                        input.seek(index10_3);
                        if ( s>=0 ) return s;
                        break;
                    case 3 :
                        int LA10_4 = input.LA(1);


                        int index10_4 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred13_ComplexParser()) ) {s = 9;}

                        else if ( (synpred14_ComplexParser()) ) {s = 10;}

                        else if ( (synpred15_ComplexParser()) ) {s = 5;}

                        else if ( (true) ) {s = 11;}


                        input.seek(index10_4);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 10, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String DFA14_eotS =
        "\31\uffff";
    static final String DFA14_eofS =
        "\31\uffff";
    static final String DFA14_minS =
        "\1\31\2\6\5\24\7\uffff\5\0\5\uffff";
    static final String DFA14_maxS =
        "\1\34\2\23\5\26\7\uffff\5\0\5\uffff";
    static final String DFA14_acceptS =
        "\10\uffff\1\14\1\1\1\3\1\7\1\5\1\13\1\11\5\uffff\1\2\1\4\1\6\1\10"+
        "\1\12";
    static final String DFA14_specialS =
        "\17\uffff\1\0\1\4\1\1\1\2\1\3\5\uffff}>";
    static final String[] DFA14_transitionS = {
            "\1\2\2\uffff\1\1",
            "\1\3\1\4\1\11\1\5\1\6\6\uffff\1\10\1\uffff\1\7",
            "\1\12\1\14\1\11\1\13\1\16\6\uffff\1\10\1\uffff\1\15",
            "\3\17",
            "\3\20",
            "\3\21",
            "\3\22",
            "\3\23",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA14_eot = DFA.unpackEncodedString(DFA14_eotS);
    static final short[] DFA14_eof = DFA.unpackEncodedString(DFA14_eofS);
    static final char[] DFA14_min = DFA.unpackEncodedStringToUnsignedChars(DFA14_minS);
    static final char[] DFA14_max = DFA.unpackEncodedStringToUnsignedChars(DFA14_maxS);
    static final short[] DFA14_accept = DFA.unpackEncodedString(DFA14_acceptS);
    static final short[] DFA14_special = DFA.unpackEncodedString(DFA14_specialS);
    static final short[][] DFA14_transition;

    static {
        int numStates = DFA14_transitionS.length;
        DFA14_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA14_transition[i] = DFA.unpackEncodedString(DFA14_transitionS[i]);
        }
    }

    class DFA14 extends DFA {

        public DFA14(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 14;
            this.eot = DFA14_eot;
            this.eof = DFA14_eof;
            this.min = DFA14_min;
            this.max = DFA14_max;
            this.accept = DFA14_accept;
            this.special = DFA14_special;
            this.transition = DFA14_transition;
        }
        @Override
        public String getDescription() {
            return "123:1: sent : ( name SIGE val -> ^( ASIGN name val ) | VAR SIGL val -> ^( COMPL VAR val ) | name SIGL val -> ^( COMPL name val ) | VAR SIGG val -> ^( COMPG VAR val ) | name SIGG val -> ^( COMPG name val ) | VAR SIGLE val -> ^( COMPLE VAR val ) | name SIGLE val -> ^( COMPLE name val ) | VAR SIGGE val -> ^( COMPGE VAR val ) | name SIGGE val -> ^( COMPGE name val ) | VAR MODC val -> ^( COMPAS VAR val ) | name MODC val -> ^( COMPAS name val ) | name MODN val DELY val ( VAR )? -> ^( COMPRNG name ^( INTERVAL val val ) ) );";
        }
        @Override
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 :
                        int LA14_15 = input.LA(1);


                        int index14_15 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred20_ComplexParser()) ) {s = 20;}

                        else if ( (synpred21_ComplexParser()) ) {s = 10;}


                        input.seek(index14_15);
                        if ( s>=0 ) return s;
                        break;
                    case 1 :
                        int LA14_17 = input.LA(1);


                        int index14_17 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred24_ComplexParser()) ) {s = 22;}

                        else if ( (synpred25_ComplexParser()) ) {s = 11;}


                        input.seek(index14_17);
                        if ( s>=0 ) return s;
                        break;
                    case 2 :
                        int LA14_18 = input.LA(1);


                        int index14_18 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred26_ComplexParser()) ) {s = 23;}

                        else if ( (synpred27_ComplexParser()) ) {s = 14;}


                        input.seek(index14_18);
                        if ( s>=0 ) return s;
                        break;
                    case 3 :
                        int LA14_19 = input.LA(1);


                        int index14_19 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred28_ComplexParser()) ) {s = 24;}

                        else if ( (synpred29_ComplexParser()) ) {s = 13;}


                        input.seek(index14_19);
                        if ( s>=0 ) return s;
                        break;
                    case 4 :
                        int LA14_16 = input.LA(1);


                        int index14_16 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred22_ComplexParser()) ) {s = 21;}

                        else if ( (synpred23_ComplexParser()) ) {s = 12;}


                        input.seek(index14_16);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 14, _s, input);
            error(nvae);
            throw nvae;
        }
    }


    public static final BitSet FOLLOW_dquery_in_squery110 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_squery112 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_limiter_in_squery124 = new BitSet(new long[]{0x0000000032050000L});
    public static final BitSet FOLLOW_oquery_in_squery127 = new BitSet(new long[]{0x0000000000050000L});
    public static final BitSet FOLLOW_modifier_in_squery129 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_squery132 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_limiter_in_squery150 = new BitSet(new long[]{0x0000000012002000L});
    public static final BitSet FOLLOW_pquery_in_squery153 = new BitSet(new long[]{0x0000000000050000L});
    public static final BitSet FOLLOW_modifier_in_squery155 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_squery158 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_OQM_in_dquery183 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_MODD_in_dquery186 = new BitSet(new long[]{0x0000000012000000L});
    public static final BitSet FOLLOW_name_in_dquery188 = new BitSet(new long[]{0x0000000100000000L});
    public static final BitSet FOLLOW_CQM_in_dquery190 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NUM_in_limiter207 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ordterm_in_modifier226 = new BitSet(new long[]{0x0000000000050002L});
    public static final BitSet FOLLOW_offsetterm_in_modifier228 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_offsetterm_in_modifier233 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_MODE_in_offsetterm244 = new BitSet(new long[]{0x0000000000200000L});
    public static final BitSet FOLLOW_NUM_in_offsetterm246 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_MODO_in_ordterm265 = new BitSet(new long[]{0x0000000020000000L});
    public static final BitSet FOLLOW_LPAR_in_ordterm267 = new BitSet(new long[]{0x0000000012000000L});
    public static final BitSet FOLLOW_plist_in_ordterm269 = new BitSet(new long[]{0x0000000040000000L});
    public static final BitSet FOLLOW_RPAR_in_ordterm271 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_name_in_oquery293 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_name_in_oquery297 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_PREC_in_oquery299 = new BitSet(new long[]{0x00000000B2000000L});
    public static final BitSet FOLLOW_querylist_in_oquery301 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LPAR_in_oquery319 = new BitSet(new long[]{0x0000000072000000L});
    public static final BitSet FOLLOW_oquery_in_oquery322 = new BitSet(new long[]{0x0000000040000000L});
    public static final BitSet FOLLOW_RPAR_in_oquery324 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_oquery_in_querylist336 = new BitSet(new long[]{0x0000000080000000L});
    public static final BitSet FOLLOW_DEL_in_querylist338 = new BitSet(new long[]{0x00000000B2000000L});
    public static final BitSet FOLLOW_querylist_in_querylist341 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_sent_in_querylist345 = new BitSet(new long[]{0x0000000080000000L});
    public static final BitSet FOLLOW_DEL_in_querylist347 = new BitSet(new long[]{0x00000000B2000000L});
    public static final BitSet FOLLOW_querylist_in_querylist350 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_oquery_in_querylist354 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_sent_in_querylist358 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_plist_in_pquery369 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_PRED_in_pquery371 = new BitSet(new long[]{0x0000000032000000L});
    public static final BitSet FOLLOW_oquery_in_pquery373 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_MODT_in_pquery391 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_PRED_in_pquery393 = new BitSet(new long[]{0x0000000032000000L});
    public static final BitSet FOLLOW_oquery_in_pquery395 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_name_in_plist420 = new BitSet(new long[]{0x0000000080000000L});
    public static final BitSet FOLLOW_DEL_in_plist422 = new BitSet(new long[]{0x0000000012000000L});
    public static final BitSet FOLLOW_plist_in_plist425 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_name_in_plist429 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_name0 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_name_in_sent455 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_SIGE_in_sent457 = new BitSet(new long[]{0x0000000000700000L});
    public static final BitSet FOLLOW_val_in_sent459 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_VAR_in_sent473 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_SIGL_in_sent475 = new BitSet(new long[]{0x0000000000700000L});
    public static final BitSet FOLLOW_val_in_sent477 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_name_in_sent491 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_SIGL_in_sent493 = new BitSet(new long[]{0x0000000000700000L});
    public static final BitSet FOLLOW_val_in_sent495 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_VAR_in_sent509 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_SIGG_in_sent511 = new BitSet(new long[]{0x0000000000700000L});
    public static final BitSet FOLLOW_val_in_sent513 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_name_in_sent527 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_SIGG_in_sent529 = new BitSet(new long[]{0x0000000000700000L});
    public static final BitSet FOLLOW_val_in_sent531 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_VAR_in_sent545 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_SIGLE_in_sent547 = new BitSet(new long[]{0x0000000000700000L});
    public static final BitSet FOLLOW_val_in_sent549 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_name_in_sent563 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_SIGLE_in_sent565 = new BitSet(new long[]{0x0000000000700000L});
    public static final BitSet FOLLOW_val_in_sent567 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_VAR_in_sent581 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_SIGGE_in_sent583 = new BitSet(new long[]{0x0000000000700000L});
    public static final BitSet FOLLOW_val_in_sent585 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_name_in_sent599 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_SIGGE_in_sent601 = new BitSet(new long[]{0x0000000000700000L});
    public static final BitSet FOLLOW_val_in_sent603 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_VAR_in_sent624 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_MODC_in_sent626 = new BitSet(new long[]{0x0000000000700000L});
    public static final BitSet FOLLOW_val_in_sent628 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_name_in_sent649 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_MODC_in_sent651 = new BitSet(new long[]{0x0000000000700000L});
    public static final BitSet FOLLOW_val_in_sent653 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_name_in_sent674 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_MODN_in_sent676 = new BitSet(new long[]{0x0000000000700000L});
    public static final BitSet FOLLOW_val_in_sent678 = new BitSet(new long[]{0x0000000008000000L});
    public static final BitSet FOLLOW_DELY_in_sent680 = new BitSet(new long[]{0x0000000000700000L});
    public static final BitSet FOLLOW_val_in_sent682 = new BitSet(new long[]{0x0000000010000002L});
    public static final BitSet FOLLOW_VAR_in_sent684 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_val0 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_oquery_in_synpred13_ComplexParser336 = new BitSet(new long[]{0x0000000080000000L});
    public static final BitSet FOLLOW_DEL_in_synpred13_ComplexParser338 = new BitSet(new long[]{0x00000000B2000000L});
    public static final BitSet FOLLOW_querylist_in_synpred13_ComplexParser341 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_sent_in_synpred14_ComplexParser345 = new BitSet(new long[]{0x0000000080000000L});
    public static final BitSet FOLLOW_DEL_in_synpred14_ComplexParser347 = new BitSet(new long[]{0x00000000B2000000L});
    public static final BitSet FOLLOW_querylist_in_synpred14_ComplexParser350 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_oquery_in_synpred15_ComplexParser354 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_VAR_in_synpred20_ComplexParser473 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_SIGL_in_synpred20_ComplexParser475 = new BitSet(new long[]{0x0000000000700000L});
    public static final BitSet FOLLOW_val_in_synpred20_ComplexParser477 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_name_in_synpred21_ComplexParser491 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_SIGL_in_synpred21_ComplexParser493 = new BitSet(new long[]{0x0000000000700000L});
    public static final BitSet FOLLOW_val_in_synpred21_ComplexParser495 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_VAR_in_synpred22_ComplexParser509 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_SIGG_in_synpred22_ComplexParser511 = new BitSet(new long[]{0x0000000000700000L});
    public static final BitSet FOLLOW_val_in_synpred22_ComplexParser513 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_name_in_synpred23_ComplexParser527 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_SIGG_in_synpred23_ComplexParser529 = new BitSet(new long[]{0x0000000000700000L});
    public static final BitSet FOLLOW_val_in_synpred23_ComplexParser531 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_VAR_in_synpred24_ComplexParser545 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_SIGLE_in_synpred24_ComplexParser547 = new BitSet(new long[]{0x0000000000700000L});
    public static final BitSet FOLLOW_val_in_synpred24_ComplexParser549 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_name_in_synpred25_ComplexParser563 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_SIGLE_in_synpred25_ComplexParser565 = new BitSet(new long[]{0x0000000000700000L});
    public static final BitSet FOLLOW_val_in_synpred25_ComplexParser567 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_VAR_in_synpred26_ComplexParser581 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_SIGGE_in_synpred26_ComplexParser583 = new BitSet(new long[]{0x0000000000700000L});
    public static final BitSet FOLLOW_val_in_synpred26_ComplexParser585 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_name_in_synpred27_ComplexParser599 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_SIGGE_in_synpred27_ComplexParser601 = new BitSet(new long[]{0x0000000000700000L});
    public static final BitSet FOLLOW_val_in_synpred27_ComplexParser603 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_VAR_in_synpred28_ComplexParser624 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_MODC_in_synpred28_ComplexParser626 = new BitSet(new long[]{0x0000000000700000L});
    public static final BitSet FOLLOW_val_in_synpred28_ComplexParser628 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_name_in_synpred29_ComplexParser649 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_MODC_in_synpred29_ComplexParser651 = new BitSet(new long[]{0x0000000000700000L});
    public static final BitSet FOLLOW_val_in_synpred29_ComplexParser653 = new BitSet(new long[]{0x0000000000000002L});
}