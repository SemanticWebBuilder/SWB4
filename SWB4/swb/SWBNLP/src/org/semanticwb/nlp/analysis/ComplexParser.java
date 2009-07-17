/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.nlp.analysis;

/**
 * An antlr natural language SparQl query parser.
 * Un analizador de consultas SparQl en lenguaje natural generado por antlr.
 * 
 * @author Hasdai Pacheco {haxdai@gmail.com}
 */
// $ANTLR 3.1.2 /home/hasdai/Documentos/INFOTEC/ComplexParser.g 2009-07-17 11:16:40

import org.antlr.runtime.*;

import org.antlr.runtime.tree.*;

public class ComplexParser extends Parser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "WHITESPACE", "SIGN", "SIGL", "SIGG", "SIGE", "SIGLE", "SIGGE", "PREC", "MODT", "PRED", "PREN", "MODE", "MODN", "MODO", "MODC", "BOL", "NUM", "LIT", "LBRK", "RBRK", "BVAR", "ORDOP", "DELY", "VAR", "LPAR", "RPAR", "DEL", "SIGI", "LIMIT", "SELECT", "ASIGN", "COMPL", "COMPG", "COMPLE", "COMPGE", "COMPAS", "PRECON", "PREDE", "OFFSET", "ORDER", "COMPNAME", "MODTO", "NAME", "COMPRNG", "INTERVAL"
    };
    public static final int INTERVAL=48;
    public static final int SIGL=6;
    public static final int PREC=11;
    public static final int LPAR=28;
    public static final int MODE=15;
    public static final int COMPGE=38;
    public static final int DELY=26;
    public static final int SIGI=31;
    public static final int ORDER=43;
    public static final int COMPNAME=44;
    public static final int SELECT=33;
    public static final int COMPRNG=47;
    public static final int COMPG=36;
    public static final int SIGN=5;
    public static final int COMPL=35;
    public static final int DEL=30;
    public static final int SIGE=8;
    public static final int PRECON=40;
    public static final int RBRK=23;
    public static final int ASIGN=34;
    public static final int MODO=17;
    public static final int RPAR=29;
    public static final int SIGGE=10;
    public static final int MODN=16;
    public static final int SIGG=7;
    public static final int OFFSET=42;
    public static final int PREDE=41;
    public static final int COMPLE=37;
    public static final int LIT=21;
    public static final int BOL=19;
    public static final int PRED=13;
    public static final int SIGLE=9;
    public static final int WHITESPACE=4;
    public static final int ORDOP=25;
    public static final int VAR=27;
    public static final int MODT=12;
    public static final int MODC=18;
    public static final int PREN=14;
    public static final int LBRK=22;
    public static final int COMPAS=39;
    public static final int EOF=-1;
    public static final int NUM=20;
    public static final int MODTO=45;
    public static final int NAME=46;
    public static final int BVAR=24;
    public static final int LIMIT=32;

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

    public String[] getTokenNames() { return ComplexParser.tokenNames; }
    public String getGrammarFileName() { return "/home/hasdai/Documentos/INFOTEC/ComplexParser.g"; }


    	private int errCount = 0;
    	private boolean precon = false;
    	private boolean prede = false;

    	/*
    	 * Overrides default displayRecognitionError method in ANTLR to count total
    	 * recognition errors.
    	 */
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
        public Object getTree() { return tree; }
    };

    // $ANTLR start "squery"
    // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:51:1: squery : ( ( limiter )? oquery ( modifier )? EOF -> ^( SELECT ( limiter )? oquery ( modifier )? ) | ( limiter )? pquery ( modifier )? EOF -> ^( SELECT ( limiter )? pquery ( modifier )? ) );
    public final ComplexParser.squery_return squery() throws RecognitionException {
        ComplexParser.squery_return retval = new ComplexParser.squery_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token EOF4=null;
        Token EOF8=null;
        ComplexParser.limiter_return limiter1 = null;

        ComplexParser.oquery_return oquery2 = null;

        ComplexParser.modifier_return modifier3 = null;

        ComplexParser.limiter_return limiter5 = null;

        ComplexParser.pquery_return pquery6 = null;

        ComplexParser.modifier_return modifier7 = null;


        Object EOF4_tree=null;
        Object EOF8_tree=null;
        RewriteRuleTokenStream stream_EOF=new RewriteRuleTokenStream(adaptor,"token EOF");
        RewriteRuleSubtreeStream stream_oquery=new RewriteRuleSubtreeStream(adaptor,"rule oquery");
        RewriteRuleSubtreeStream stream_modifier=new RewriteRuleSubtreeStream(adaptor,"rule modifier");
        RewriteRuleSubtreeStream stream_pquery=new RewriteRuleSubtreeStream(adaptor,"rule pquery");
        RewriteRuleSubtreeStream stream_limiter=new RewriteRuleSubtreeStream(adaptor,"rule limiter");
        try {
            // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:52:1: ( ( limiter )? oquery ( modifier )? EOF -> ^( SELECT ( limiter )? oquery ( modifier )? ) | ( limiter )? pquery ( modifier )? EOF -> ^( SELECT ( limiter )? pquery ( modifier )? ) )
            int alt5=2;
            switch ( input.LA(1) ) {
            case NUM:
                {
                switch ( input.LA(2) ) {
                case BVAR:
                case VAR:
                    {
                    int LA5_3 = input.LA(3);

                    if ( (LA5_3==EOF||LA5_3==PREC||LA5_3==MODE||LA5_3==MODO) ) {
                        alt5=1;
                    }
                    else if ( (LA5_3==PRED||LA5_3==DEL) ) {
                        alt5=2;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return retval;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 5, 3, input);

                        throw nvae;
                    }
                    }
                    break;
                case MODT:
                    {
                    alt5=2;
                    }
                    break;
                case EOF:
                case MODE:
                case MODO:
                case LPAR:
                    {
                    alt5=1;
                    }
                    break;
                default:
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 5, 1, input);

                    throw nvae;
                }

                }
                break;
            case EOF:
            case MODE:
            case MODO:
            case LPAR:
                {
                alt5=1;
                }
                break;
            case BVAR:
            case VAR:
                {
                int LA5_3 = input.LA(2);

                if ( (LA5_3==EOF||LA5_3==PREC||LA5_3==MODE||LA5_3==MODO) ) {
                    alt5=1;
                }
                else if ( (LA5_3==PRED||LA5_3==DEL) ) {
                    alt5=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 5, 3, input);

                    throw nvae;
                }
                }
                break;
            case MODT:
                {
                alt5=2;
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
                    // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:52:3: ( limiter )? oquery ( modifier )? EOF
                    {
                    // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:52:3: ( limiter )?
                    int alt1=2;
                    int LA1_0 = input.LA(1);

                    if ( (LA1_0==NUM) ) {
                        alt1=1;
                    }
                    switch (alt1) {
                        case 1 :
                            // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:0:0: limiter
                            {
                            pushFollow(FOLLOW_limiter_in_squery109);
                            limiter1=limiter();

                            state._fsp--;
                            if (state.failed) return retval;
                            if ( state.backtracking==0 ) stream_limiter.add(limiter1.getTree());

                            }
                            break;

                    }

                    pushFollow(FOLLOW_oquery_in_squery112);
                    oquery2=oquery();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_oquery.add(oquery2.getTree());
                    // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:52:19: ( modifier )?
                    int alt2=2;
                    int LA2_0 = input.LA(1);

                    if ( (LA2_0==MODE||LA2_0==MODO) ) {
                        alt2=1;
                    }
                    switch (alt2) {
                        case 1 :
                            // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:0:0: modifier
                            {
                            pushFollow(FOLLOW_modifier_in_squery114);
                            modifier3=modifier();

                            state._fsp--;
                            if (state.failed) return retval;
                            if ( state.backtracking==0 ) stream_modifier.add(modifier3.getTree());

                            }
                            break;

                    }

                    EOF4=(Token)match(input,EOF,FOLLOW_EOF_in_squery117); if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_EOF.add(EOF4);



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
                    // 52:33: -> ^( SELECT ( limiter )? oquery ( modifier )? )
                    {
                        // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:52:36: ^( SELECT ( limiter )? oquery ( modifier )? )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(SELECT, "SELECT"), root_1);

                        // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:52:45: ( limiter )?
                        if ( stream_limiter.hasNext() ) {
                            adaptor.addChild(root_1, stream_limiter.nextTree());

                        }
                        stream_limiter.reset();
                        adaptor.addChild(root_1, stream_oquery.nextTree());
                        // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:52:61: ( modifier )?
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
                case 2 :
                    // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:53:3: ( limiter )? pquery ( modifier )? EOF
                    {
                    // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:53:3: ( limiter )?
                    int alt3=2;
                    int LA3_0 = input.LA(1);

                    if ( (LA3_0==NUM) ) {
                        alt3=1;
                    }
                    switch (alt3) {
                        case 1 :
                            // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:0:0: limiter
                            {
                            pushFollow(FOLLOW_limiter_in_squery135);
                            limiter5=limiter();

                            state._fsp--;
                            if (state.failed) return retval;
                            if ( state.backtracking==0 ) stream_limiter.add(limiter5.getTree());

                            }
                            break;

                    }

                    pushFollow(FOLLOW_pquery_in_squery138);
                    pquery6=pquery();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_pquery.add(pquery6.getTree());
                    // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:53:19: ( modifier )?
                    int alt4=2;
                    int LA4_0 = input.LA(1);

                    if ( (LA4_0==MODE||LA4_0==MODO) ) {
                        alt4=1;
                    }
                    switch (alt4) {
                        case 1 :
                            // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:0:0: modifier
                            {
                            pushFollow(FOLLOW_modifier_in_squery140);
                            modifier7=modifier();

                            state._fsp--;
                            if (state.failed) return retval;
                            if ( state.backtracking==0 ) stream_modifier.add(modifier7.getTree());

                            }
                            break;

                    }

                    EOF8=(Token)match(input,EOF,FOLLOW_EOF_in_squery143); if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_EOF.add(EOF8);



                    // AST REWRITE
                    // elements: pquery, limiter, modifier
                    // token labels:
                    // rule labels: retval
                    // token list labels:
                    // rule list labels:
                    // wildcard labels:
                    if ( state.backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 53:33: -> ^( SELECT ( limiter )? pquery ( modifier )? )
                    {
                        // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:53:36: ^( SELECT ( limiter )? pquery ( modifier )? )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(SELECT, "SELECT"), root_1);

                        // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:53:45: ( limiter )?
                        if ( stream_limiter.hasNext() ) {
                            adaptor.addChild(root_1, stream_limiter.nextTree());

                        }
                        stream_limiter.reset();
                        adaptor.addChild(root_1, stream_pquery.nextTree());
                        // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:53:61: ( modifier )?
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
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "squery"

    public static class limiter_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "limiter"
    // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:57:1: limiter : NUM -> ^( LIMIT NUM ) ;
    public final ComplexParser.limiter_return limiter() throws RecognitionException {
        ComplexParser.limiter_return retval = new ComplexParser.limiter_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token NUM9=null;

        Object NUM9_tree=null;
        RewriteRuleTokenStream stream_NUM=new RewriteRuleTokenStream(adaptor,"token NUM");

        try {
            // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:58:1: ( NUM -> ^( LIMIT NUM ) )
            // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:58:3: NUM
            {
            NUM9=(Token)match(input,NUM,FOLLOW_NUM_in_limiter168); if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_NUM.add(NUM9);



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
            // 58:7: -> ^( LIMIT NUM )
            {
                // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:58:10: ^( LIMIT NUM )
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
            reportError(re);
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
    // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:64:1: modifier : ( ordterm ( offsetterm )? | offsetterm );
    public final ComplexParser.modifier_return modifier() throws RecognitionException {
        ComplexParser.modifier_return retval = new ComplexParser.modifier_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        ComplexParser.ordterm_return ordterm10 = null;

        ComplexParser.offsetterm_return offsetterm11 = null;

        ComplexParser.offsetterm_return offsetterm12 = null;



        try {
            // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:65:1: ( ordterm ( offsetterm )? | offsetterm )
            int alt7=2;
            int LA7_0 = input.LA(1);

            if ( (LA7_0==MODO) ) {
                alt7=1;
            }
            else if ( (LA7_0==MODE) ) {
                alt7=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 7, 0, input);

                throw nvae;
            }
            switch (alt7) {
                case 1 :
                    // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:65:3: ordterm ( offsetterm )?
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_ordterm_in_modifier187);
                    ordterm10=ordterm();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, ordterm10.getTree());
                    // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:65:11: ( offsetterm )?
                    int alt6=2;
                    int LA6_0 = input.LA(1);

                    if ( (LA6_0==MODE) ) {
                        alt6=1;
                    }
                    switch (alt6) {
                        case 1 :
                            // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:0:0: offsetterm
                            {
                            pushFollow(FOLLOW_offsetterm_in_modifier189);
                            offsetterm11=offsetterm();

                            state._fsp--;
                            if (state.failed) return retval;
                            if ( state.backtracking==0 ) adaptor.addChild(root_0, offsetterm11.getTree());

                            }
                            break;

                    }


                    }
                    break;
                case 2 :
                    // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:66:3: offsetterm
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_offsetterm_in_modifier194);
                    offsetterm12=offsetterm();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, offsetterm12.getTree());

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
            reportError(re);
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
    // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:70:1: offsetterm : MODE NUM -> ^( OFFSET NUM ) ;
    public final ComplexParser.offsetterm_return offsetterm() throws RecognitionException {
        ComplexParser.offsetterm_return retval = new ComplexParser.offsetterm_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token MODE13=null;
        Token NUM14=null;

        Object MODE13_tree=null;
        Object NUM14_tree=null;
        RewriteRuleTokenStream stream_MODE=new RewriteRuleTokenStream(adaptor,"token MODE");
        RewriteRuleTokenStream stream_NUM=new RewriteRuleTokenStream(adaptor,"token NUM");

        try {
            // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:71:1: ( MODE NUM -> ^( OFFSET NUM ) )
            // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:71:3: MODE NUM
            {
            MODE13=(Token)match(input,MODE,FOLLOW_MODE_in_offsetterm205); if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_MODE.add(MODE13);

            NUM14=(Token)match(input,NUM,FOLLOW_NUM_in_offsetterm207); if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_NUM.add(NUM14);



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
            // 71:12: -> ^( OFFSET NUM )
            {
                // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:71:15: ^( OFFSET NUM )
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
            reportError(re);
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
    // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:75:1: ordterm : MODO LPAR plist RPAR -> ^( ORDER plist ) ;
    public final ComplexParser.ordterm_return ordterm() throws RecognitionException {
        ComplexParser.ordterm_return retval = new ComplexParser.ordterm_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token MODO15=null;
        Token LPAR16=null;
        Token RPAR18=null;
        ComplexParser.plist_return plist17 = null;


        Object MODO15_tree=null;
        Object LPAR16_tree=null;
        Object RPAR18_tree=null;
        RewriteRuleTokenStream stream_MODO=new RewriteRuleTokenStream(adaptor,"token MODO");
        RewriteRuleTokenStream stream_RPAR=new RewriteRuleTokenStream(adaptor,"token RPAR");
        RewriteRuleTokenStream stream_LPAR=new RewriteRuleTokenStream(adaptor,"token LPAR");
        RewriteRuleSubtreeStream stream_plist=new RewriteRuleSubtreeStream(adaptor,"rule plist");
        try {
            // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:76:1: ( MODO LPAR plist RPAR -> ^( ORDER plist ) )
            // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:76:3: MODO LPAR plist RPAR
            {
            MODO15=(Token)match(input,MODO,FOLLOW_MODO_in_ordterm226); if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_MODO.add(MODO15);

            LPAR16=(Token)match(input,LPAR,FOLLOW_LPAR_in_ordterm228); if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_LPAR.add(LPAR16);

            pushFollow(FOLLOW_plist_in_ordterm230);
            plist17=plist();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_plist.add(plist17.getTree());
            RPAR18=(Token)match(input,RPAR,FOLLOW_RPAR_in_ordterm232); if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_RPAR.add(RPAR18);



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
            // 76:24: -> ^( ORDER plist )
            {
                // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:76:27: ^( ORDER plist )
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
            reportError(re);
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
    // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:83:1: oquery : ( | name | name PREC querylist -> ^( name ^( PRECON querylist ) ) | LPAR oquery RPAR );
    public final ComplexParser.oquery_return oquery() throws RecognitionException {
        ComplexParser.oquery_return retval = new ComplexParser.oquery_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token PREC21=null;
        Token LPAR23=null;
        Token RPAR25=null;
        ComplexParser.name_return name19 = null;

        ComplexParser.name_return name20 = null;

        ComplexParser.querylist_return querylist22 = null;

        ComplexParser.oquery_return oquery24 = null;


        Object PREC21_tree=null;
        Object LPAR23_tree=null;
        Object RPAR25_tree=null;
        RewriteRuleTokenStream stream_PREC=new RewriteRuleTokenStream(adaptor,"token PREC");
        RewriteRuleSubtreeStream stream_querylist=new RewriteRuleSubtreeStream(adaptor,"rule querylist");
        RewriteRuleSubtreeStream stream_name=new RewriteRuleSubtreeStream(adaptor,"rule name");
        try {
            // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:84:1: ( | name | name PREC querylist -> ^( name ^( PRECON querylist ) ) | LPAR oquery RPAR )
            int alt8=4;
            switch ( input.LA(1) ) {
            case EOF:
            case MODE:
            case MODO:
            case RPAR:
            case DEL:
                {
                alt8=1;
                }
                break;
            case BVAR:
            case VAR:
                {
                int LA8_2 = input.LA(2);

                if ( (LA8_2==EOF||LA8_2==MODE||LA8_2==MODO||(LA8_2>=RPAR && LA8_2<=DEL)) ) {
                    alt8=2;
                }
                else if ( (LA8_2==PREC) ) {
                    alt8=3;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 8, 2, input);

                    throw nvae;
                }
                }
                break;
            case LPAR:
                {
                alt8=4;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 8, 0, input);

                throw nvae;
            }

            switch (alt8) {
                case 1 :
                    // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:85:2:
                    {
                    root_0 = (Object)adaptor.nil();

                    }
                    break;
                case 2 :
                    // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:85:3: name
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_name_in_oquery254);
                    name19=name();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, name19.getTree());

                    }
                    break;
                case 3 :
                    // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:86:3: name PREC querylist
                    {
                    pushFollow(FOLLOW_name_in_oquery258);
                    name20=name();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_name.add(name20.getTree());
                    PREC21=(Token)match(input,PREC,FOLLOW_PREC_in_oquery260); if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_PREC.add(PREC21);

                    pushFollow(FOLLOW_querylist_in_oquery262);
                    querylist22=querylist();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_querylist.add(querylist22.getTree());
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
                    // 86:40: -> ^( name ^( PRECON querylist ) )
                    {
                        // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:86:43: ^( name ^( PRECON querylist ) )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot(stream_name.nextNode(), root_1);

                        // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:86:50: ^( PRECON querylist )
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
                    // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:87:3: LPAR oquery RPAR
                    {
                    root_0 = (Object)adaptor.nil();

                    LPAR23=(Token)match(input,LPAR,FOLLOW_LPAR_in_oquery280); if (state.failed) return retval;
                    pushFollow(FOLLOW_oquery_in_oquery283);
                    oquery24=oquery();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, oquery24.getTree());
                    RPAR25=(Token)match(input,RPAR,FOLLOW_RPAR_in_oquery285); if (state.failed) return retval;

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
            reportError(re);
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
    // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:91:1: querylist : ( oquery DEL querylist | sent DEL querylist | oquery | sent );
    public final ComplexParser.querylist_return querylist() throws RecognitionException {
        ComplexParser.querylist_return retval = new ComplexParser.querylist_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token DEL27=null;
        Token DEL30=null;
        ComplexParser.oquery_return oquery26 = null;

        ComplexParser.querylist_return querylist28 = null;

        ComplexParser.sent_return sent29 = null;

        ComplexParser.querylist_return querylist31 = null;

        ComplexParser.oquery_return oquery32 = null;

        ComplexParser.sent_return sent33 = null;


        Object DEL27_tree=null;
        Object DEL30_tree=null;

        try {
            // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:92:1: ( oquery DEL querylist | sent DEL querylist | oquery | sent )
            int alt9=4;
            alt9 = dfa9.predict(input);
            switch (alt9) {
                case 1 :
                    // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:92:3: oquery DEL querylist
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_oquery_in_querylist297);
                    oquery26=oquery();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, oquery26.getTree());
                    DEL27=(Token)match(input,DEL,FOLLOW_DEL_in_querylist299); if (state.failed) return retval;
                    pushFollow(FOLLOW_querylist_in_querylist302);
                    querylist28=querylist();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, querylist28.getTree());

                    }
                    break;
                case 2 :
                    // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:93:3: sent DEL querylist
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_sent_in_querylist306);
                    sent29=sent();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, sent29.getTree());
                    DEL30=(Token)match(input,DEL,FOLLOW_DEL_in_querylist308); if (state.failed) return retval;
                    pushFollow(FOLLOW_querylist_in_querylist311);
                    querylist31=querylist();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, querylist31.getTree());

                    }
                    break;
                case 3 :
                    // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:94:3: oquery
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_oquery_in_querylist315);
                    oquery32=oquery();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, oquery32.getTree());

                    }
                    break;
                case 4 :
                    // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:95:3: sent
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_sent_in_querylist319);
                    sent33=sent();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, sent33.getTree());

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
            reportError(re);
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
    // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:99:1: pquery : ( plist PRED oquery -> ^( oquery ^( PREDE plist ) ) | MODT PRED oquery -> ^( oquery ^( PREDE MODTO ) ) );
    public final ComplexParser.pquery_return pquery() throws RecognitionException {
        ComplexParser.pquery_return retval = new ComplexParser.pquery_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token PRED35=null;
        Token MODT37=null;
        Token PRED38=null;
        ComplexParser.plist_return plist34 = null;

        ComplexParser.oquery_return oquery36 = null;

        ComplexParser.oquery_return oquery39 = null;


        Object PRED35_tree=null;
        Object MODT37_tree=null;
        Object PRED38_tree=null;
        RewriteRuleTokenStream stream_MODT=new RewriteRuleTokenStream(adaptor,"token MODT");
        RewriteRuleTokenStream stream_PRED=new RewriteRuleTokenStream(adaptor,"token PRED");
        RewriteRuleSubtreeStream stream_plist=new RewriteRuleSubtreeStream(adaptor,"rule plist");
        RewriteRuleSubtreeStream stream_oquery=new RewriteRuleSubtreeStream(adaptor,"rule oquery");
        try {
            // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:100:1: ( plist PRED oquery -> ^( oquery ^( PREDE plist ) ) | MODT PRED oquery -> ^( oquery ^( PREDE MODTO ) ) )
            int alt10=2;
            int LA10_0 = input.LA(1);

            if ( (LA10_0==BVAR||LA10_0==VAR) ) {
                alt10=1;
            }
            else if ( (LA10_0==MODT) ) {
                alt10=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 10, 0, input);

                throw nvae;
            }
            switch (alt10) {
                case 1 :
                    // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:100:3: plist PRED oquery
                    {
                    pushFollow(FOLLOW_plist_in_pquery330);
                    plist34=plist();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_plist.add(plist34.getTree());
                    PRED35=(Token)match(input,PRED,FOLLOW_PRED_in_pquery332); if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_PRED.add(PRED35);

                    pushFollow(FOLLOW_oquery_in_pquery334);
                    oquery36=oquery();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_oquery.add(oquery36.getTree());
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
                    // 100:37: -> ^( oquery ^( PREDE plist ) )
                    {
                        // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:100:40: ^( oquery ^( PREDE plist ) )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot(stream_oquery.nextNode(), root_1);

                        // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:100:49: ^( PREDE plist )
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
                    // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:101:3: MODT PRED oquery
                    {
                    MODT37=(Token)match(input,MODT,FOLLOW_MODT_in_pquery352); if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_MODT.add(MODT37);

                    PRED38=(Token)match(input,PRED,FOLLOW_PRED_in_pquery354); if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_PRED.add(PRED38);

                    pushFollow(FOLLOW_oquery_in_pquery356);
                    oquery39=oquery();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_oquery.add(oquery39.getTree());
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
                    // 101:36: -> ^( oquery ^( PREDE MODTO ) )
                    {
                        // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:101:39: ^( oquery ^( PREDE MODTO ) )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot(stream_oquery.nextNode(), root_1);

                        // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:101:48: ^( PREDE MODTO )
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
            reportError(re);
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
    // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:105:1: plist : ( name DEL plist | name );
    public final ComplexParser.plist_return plist() throws RecognitionException {
        ComplexParser.plist_return retval = new ComplexParser.plist_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token DEL41=null;
        ComplexParser.name_return name40 = null;

        ComplexParser.plist_return plist42 = null;

        ComplexParser.name_return name43 = null;


        Object DEL41_tree=null;

        try {
            // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:106:1: ( name DEL plist | name )
            int alt11=2;
            int LA11_0 = input.LA(1);

            if ( (LA11_0==BVAR||LA11_0==VAR) ) {
                int LA11_1 = input.LA(2);

                if ( (LA11_1==DEL) ) {
                    alt11=1;
                }
                else if ( (LA11_1==EOF||LA11_1==PRED||LA11_1==RPAR) ) {
                    alt11=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 11, 1, input);

                    throw nvae;
                }
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 11, 0, input);

                throw nvae;
            }
            switch (alt11) {
                case 1 :
                    // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:106:3: name DEL plist
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_name_in_plist381);
                    name40=name();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, name40.getTree());
                    DEL41=(Token)match(input,DEL,FOLLOW_DEL_in_plist383); if (state.failed) return retval;
                    pushFollow(FOLLOW_plist_in_plist386);
                    plist42=plist();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, plist42.getTree());

                    }
                    break;
                case 2 :
                    // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:107:3: name
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_name_in_plist390);
                    name43=name();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, name43.getTree());

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
            reportError(re);
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
    // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:111:1: name : ( BVAR | VAR );
    public final ComplexParser.name_return name() throws RecognitionException {
        ComplexParser.name_return retval = new ComplexParser.name_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token set44=null;

        Object set44_tree=null;

        try {
            // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:112:1: ( BVAR | VAR )
            // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:
            {
            root_0 = (Object)adaptor.nil();

            set44=(Token)input.LT(1);
            if ( input.LA(1)==BVAR||input.LA(1)==VAR ) {
                input.consume();
                if ( state.backtracking==0 ) adaptor.addChild(root_0, (Object)adaptor.create(set44));
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
            reportError(re);
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
    // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:117:1: sent : ( name SIGE val -> ^( ASIGN name val ) | VAR SIGL val -> ^( COMPL VAR val ) | name SIGL val -> ^( COMPL name val ) | VAR SIGG val -> ^( COMPG VAR val ) | name SIGG val -> ^( COMPG name val ) | VAR SIGLE val -> ^( COMPLE VAR val ) | name SIGLE val -> ^( COMPLE name val ) | VAR SIGGE val -> ^( COMPGE VAR val ) | name SIGGE val -> ^( COMPGE name val ) | VAR MODC val -> ^( COMPAS VAR val ) | name MODC val -> ^( COMPAS name val ) | name MODN val DELY val ( VAR )? -> ^( COMPRNG name ^( INTERVAL val val ) ) );
    public final ComplexParser.sent_return sent() throws RecognitionException {
        ComplexParser.sent_return retval = new ComplexParser.sent_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token SIGE46=null;
        Token VAR48=null;
        Token SIGL49=null;
        Token SIGL52=null;
        Token VAR54=null;
        Token SIGG55=null;
        Token SIGG58=null;
        Token VAR60=null;
        Token SIGLE61=null;
        Token SIGLE64=null;
        Token VAR66=null;
        Token SIGGE67=null;
        Token SIGGE70=null;
        Token VAR72=null;
        Token MODC73=null;
        Token MODC76=null;
        Token MODN79=null;
        Token DELY81=null;
        Token VAR83=null;
        ComplexParser.name_return name45 = null;

        ComplexParser.val_return val47 = null;

        ComplexParser.val_return val50 = null;

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

        ComplexParser.name_return name78 = null;

        ComplexParser.val_return val80 = null;

        ComplexParser.val_return val82 = null;


        Object SIGE46_tree=null;
        Object VAR48_tree=null;
        Object SIGL49_tree=null;
        Object SIGL52_tree=null;
        Object VAR54_tree=null;
        Object SIGG55_tree=null;
        Object SIGG58_tree=null;
        Object VAR60_tree=null;
        Object SIGLE61_tree=null;
        Object SIGLE64_tree=null;
        Object VAR66_tree=null;
        Object SIGGE67_tree=null;
        Object SIGGE70_tree=null;
        Object VAR72_tree=null;
        Object MODC73_tree=null;
        Object MODC76_tree=null;
        Object MODN79_tree=null;
        Object DELY81_tree=null;
        Object VAR83_tree=null;
        RewriteRuleTokenStream stream_VAR=new RewriteRuleTokenStream(adaptor,"token VAR");
        RewriteRuleTokenStream stream_MODC=new RewriteRuleTokenStream(adaptor,"token MODC");
        RewriteRuleTokenStream stream_SIGL=new RewriteRuleTokenStream(adaptor,"token SIGL");
        RewriteRuleTokenStream stream_DELY=new RewriteRuleTokenStream(adaptor,"token DELY");
        RewriteRuleTokenStream stream_SIGGE=new RewriteRuleTokenStream(adaptor,"token SIGGE");
        RewriteRuleTokenStream stream_MODN=new RewriteRuleTokenStream(adaptor,"token MODN");
        RewriteRuleTokenStream stream_SIGE=new RewriteRuleTokenStream(adaptor,"token SIGE");
        RewriteRuleTokenStream stream_SIGLE=new RewriteRuleTokenStream(adaptor,"token SIGLE");
        RewriteRuleTokenStream stream_SIGG=new RewriteRuleTokenStream(adaptor,"token SIGG");
        RewriteRuleSubtreeStream stream_val=new RewriteRuleSubtreeStream(adaptor,"rule val");
        RewriteRuleSubtreeStream stream_name=new RewriteRuleSubtreeStream(adaptor,"rule name");
        try {
            // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:118:1: ( name SIGE val -> ^( ASIGN name val ) | VAR SIGL val -> ^( COMPL VAR val ) | name SIGL val -> ^( COMPL name val ) | VAR SIGG val -> ^( COMPG VAR val ) | name SIGG val -> ^( COMPG name val ) | VAR SIGLE val -> ^( COMPLE VAR val ) | name SIGLE val -> ^( COMPLE name val ) | VAR SIGGE val -> ^( COMPGE VAR val ) | name SIGGE val -> ^( COMPGE name val ) | VAR MODC val -> ^( COMPAS VAR val ) | name MODC val -> ^( COMPAS name val ) | name MODN val DELY val ( VAR )? -> ^( COMPRNG name ^( INTERVAL val val ) ) )
            int alt13=12;
            alt13 = dfa13.predict(input);
            switch (alt13) {
                case 1 :
                    // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:118:3: name SIGE val
                    {
                    pushFollow(FOLLOW_name_in_sent416);
                    name45=name();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_name.add(name45.getTree());
                    SIGE46=(Token)match(input,SIGE,FOLLOW_SIGE_in_sent418); if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_SIGE.add(SIGE46);

                    pushFollow(FOLLOW_val_in_sent420);
                    val47=val();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_val.add(val47.getTree());


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
                    // 118:17: -> ^( ASIGN name val )
                    {
                        // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:118:20: ^( ASIGN name val )
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
                    // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:119:3: VAR SIGL val
                    {
                    VAR48=(Token)match(input,VAR,FOLLOW_VAR_in_sent434); if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_VAR.add(VAR48);

                    SIGL49=(Token)match(input,SIGL,FOLLOW_SIGL_in_sent436); if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_SIGL.add(SIGL49);

                    pushFollow(FOLLOW_val_in_sent438);
                    val50=val();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_val.add(val50.getTree());


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
                    // 119:16: -> ^( COMPL VAR val )
                    {
                        // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:119:19: ^( COMPL VAR val )
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
                    // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:120:3: name SIGL val
                    {
                    pushFollow(FOLLOW_name_in_sent452);
                    name51=name();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_name.add(name51.getTree());
                    SIGL52=(Token)match(input,SIGL,FOLLOW_SIGL_in_sent454); if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_SIGL.add(SIGL52);

                    pushFollow(FOLLOW_val_in_sent456);
                    val53=val();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_val.add(val53.getTree());


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
                    // 120:17: -> ^( COMPL name val )
                    {
                        // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:120:20: ^( COMPL name val )
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
                    // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:121:3: VAR SIGG val
                    {
                    VAR54=(Token)match(input,VAR,FOLLOW_VAR_in_sent470); if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_VAR.add(VAR54);

                    SIGG55=(Token)match(input,SIGG,FOLLOW_SIGG_in_sent472); if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_SIGG.add(SIGG55);

                    pushFollow(FOLLOW_val_in_sent474);
                    val56=val();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_val.add(val56.getTree());


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
                    // 121:16: -> ^( COMPG VAR val )
                    {
                        // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:121:19: ^( COMPG VAR val )
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
                    // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:122:3: name SIGG val
                    {
                    pushFollow(FOLLOW_name_in_sent488);
                    name57=name();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_name.add(name57.getTree());
                    SIGG58=(Token)match(input,SIGG,FOLLOW_SIGG_in_sent490); if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_SIGG.add(SIGG58);

                    pushFollow(FOLLOW_val_in_sent492);
                    val59=val();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_val.add(val59.getTree());


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
                    // 122:17: -> ^( COMPG name val )
                    {
                        // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:122:20: ^( COMPG name val )
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
                    // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:123:3: VAR SIGLE val
                    {
                    VAR60=(Token)match(input,VAR,FOLLOW_VAR_in_sent506); if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_VAR.add(VAR60);

                    SIGLE61=(Token)match(input,SIGLE,FOLLOW_SIGLE_in_sent508); if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_SIGLE.add(SIGLE61);

                    pushFollow(FOLLOW_val_in_sent510);
                    val62=val();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_val.add(val62.getTree());


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
                    // 123:17: -> ^( COMPLE VAR val )
                    {
                        // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:123:20: ^( COMPLE VAR val )
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
                    // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:124:3: name SIGLE val
                    {
                    pushFollow(FOLLOW_name_in_sent524);
                    name63=name();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_name.add(name63.getTree());
                    SIGLE64=(Token)match(input,SIGLE,FOLLOW_SIGLE_in_sent526); if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_SIGLE.add(SIGLE64);

                    pushFollow(FOLLOW_val_in_sent528);
                    val65=val();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_val.add(val65.getTree());


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
                    // 124:18: -> ^( COMPLE name val )
                    {
                        // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:124:21: ^( COMPLE name val )
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
                    // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:125:3: VAR SIGGE val
                    {
                    VAR66=(Token)match(input,VAR,FOLLOW_VAR_in_sent542); if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_VAR.add(VAR66);

                    SIGGE67=(Token)match(input,SIGGE,FOLLOW_SIGGE_in_sent544); if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_SIGGE.add(SIGGE67);

                    pushFollow(FOLLOW_val_in_sent546);
                    val68=val();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_val.add(val68.getTree());


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
                    // 125:17: -> ^( COMPGE VAR val )
                    {
                        // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:125:20: ^( COMPGE VAR val )
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
                    // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:126:3: name SIGGE val
                    {
                    pushFollow(FOLLOW_name_in_sent560);
                    name69=name();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_name.add(name69.getTree());
                    SIGGE70=(Token)match(input,SIGGE,FOLLOW_SIGGE_in_sent562); if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_SIGGE.add(SIGGE70);

                    pushFollow(FOLLOW_val_in_sent564);
                    val71=val();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_val.add(val71.getTree());


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
                    // 126:18: -> ^( COMPGE name val )
                    {
                        // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:126:21: ^( COMPGE name val )
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
                    // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:127:10: VAR MODC val
                    {
                    VAR72=(Token)match(input,VAR,FOLLOW_VAR_in_sent585); if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_VAR.add(VAR72);

                    MODC73=(Token)match(input,MODC,FOLLOW_MODC_in_sent587); if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_MODC.add(MODC73);

                    pushFollow(FOLLOW_val_in_sent589);
                    val74=val();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_val.add(val74.getTree());


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
                    // 127:23: -> ^( COMPAS VAR val )
                    {
                        // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:127:26: ^( COMPAS VAR val )
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
                    // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:128:10: name MODC val
                    {
                    pushFollow(FOLLOW_name_in_sent610);
                    name75=name();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_name.add(name75.getTree());
                    MODC76=(Token)match(input,MODC,FOLLOW_MODC_in_sent612); if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_MODC.add(MODC76);

                    pushFollow(FOLLOW_val_in_sent614);
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
                    // 128:24: -> ^( COMPAS name val )
                    {
                        // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:128:27: ^( COMPAS name val )
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
                    // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:129:10: name MODN val DELY val ( VAR )?
                    {
                    pushFollow(FOLLOW_name_in_sent635);
                    name78=name();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_name.add(name78.getTree());
                    MODN79=(Token)match(input,MODN,FOLLOW_MODN_in_sent637); if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_MODN.add(MODN79);

                    pushFollow(FOLLOW_val_in_sent639);
                    val80=val();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_val.add(val80.getTree());
                    DELY81=(Token)match(input,DELY,FOLLOW_DELY_in_sent641); if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_DELY.add(DELY81);

                    pushFollow(FOLLOW_val_in_sent643);
                    val82=val();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_val.add(val82.getTree());
                    // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:129:33: ( VAR )?
                    int alt12=2;
                    int LA12_0 = input.LA(1);

                    if ( (LA12_0==VAR) ) {
                        alt12=1;
                    }
                    switch (alt12) {
                        case 1 :
                            // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:0:0: VAR
                            {
                            VAR83=(Token)match(input,VAR,FOLLOW_VAR_in_sent645); if (state.failed) return retval;
                            if ( state.backtracking==0 ) stream_VAR.add(VAR83);


                            }
                            break;

                    }



                    // AST REWRITE
                    // elements: val, val, name
                    // token labels:
                    // rule labels: retval
                    // token list labels:
                    // rule list labels:
                    // wildcard labels:
                    if ( state.backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 129:38: -> ^( COMPRNG name ^( INTERVAL val val ) )
                    {
                        // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:129:41: ^( COMPRNG name ^( INTERVAL val val ) )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(COMPRNG, "COMPRNG"), root_1);

                        adaptor.addChild(root_1, stream_name.nextTree());
                        // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:129:56: ^( INTERVAL val val )
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
            reportError(re);
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
    // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:133:1: val : ( LIT | BOL | NUM );
    public final ComplexParser.val_return val() throws RecognitionException {
        ComplexParser.val_return retval = new ComplexParser.val_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token set84=null;

        Object set84_tree=null;

        try {
            // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:134:1: ( LIT | BOL | NUM )
            // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:
            {
            root_0 = (Object)adaptor.nil();

            set84=(Token)input.LT(1);
            if ( (input.LA(1)>=BOL && input.LA(1)<=LIT) ) {
                input.consume();
                if ( state.backtracking==0 ) adaptor.addChild(root_0, (Object)adaptor.create(set84));
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
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "val"

    // $ANTLR start synpred11_ComplexParser
    public final void synpred11_ComplexParser_fragment() throws RecognitionException {
        // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:92:3: ( oquery DEL querylist )
        // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:92:3: oquery DEL querylist
        {
        pushFollow(FOLLOW_oquery_in_synpred11_ComplexParser297);
        oquery();

        state._fsp--;
        if (state.failed) return ;
        match(input,DEL,FOLLOW_DEL_in_synpred11_ComplexParser299); if (state.failed) return ;
        pushFollow(FOLLOW_querylist_in_synpred11_ComplexParser302);
        querylist();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred11_ComplexParser

    // $ANTLR start synpred12_ComplexParser
    public final void synpred12_ComplexParser_fragment() throws RecognitionException {
        // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:93:3: ( sent DEL querylist )
        // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:93:3: sent DEL querylist
        {
        pushFollow(FOLLOW_sent_in_synpred12_ComplexParser306);
        sent();

        state._fsp--;
        if (state.failed) return ;
        match(input,DEL,FOLLOW_DEL_in_synpred12_ComplexParser308); if (state.failed) return ;
        pushFollow(FOLLOW_querylist_in_synpred12_ComplexParser311);
        querylist();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred12_ComplexParser

    // $ANTLR start synpred13_ComplexParser
    public final void synpred13_ComplexParser_fragment() throws RecognitionException {
        // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:94:3: ( oquery )
        // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:94:3: oquery
        {
        pushFollow(FOLLOW_oquery_in_synpred13_ComplexParser315);
        oquery();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred13_ComplexParser

    // $ANTLR start synpred18_ComplexParser
    public final void synpred18_ComplexParser_fragment() throws RecognitionException {
        // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:119:3: ( VAR SIGL val )
        // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:119:3: VAR SIGL val
        {
        match(input,VAR,FOLLOW_VAR_in_synpred18_ComplexParser434); if (state.failed) return ;
        match(input,SIGL,FOLLOW_SIGL_in_synpred18_ComplexParser436); if (state.failed) return ;
        pushFollow(FOLLOW_val_in_synpred18_ComplexParser438);
        val();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred18_ComplexParser

    // $ANTLR start synpred19_ComplexParser
    public final void synpred19_ComplexParser_fragment() throws RecognitionException {
        // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:120:3: ( name SIGL val )
        // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:120:3: name SIGL val
        {
        pushFollow(FOLLOW_name_in_synpred19_ComplexParser452);
        name();

        state._fsp--;
        if (state.failed) return ;
        match(input,SIGL,FOLLOW_SIGL_in_synpred19_ComplexParser454); if (state.failed) return ;
        pushFollow(FOLLOW_val_in_synpred19_ComplexParser456);
        val();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred19_ComplexParser

    // $ANTLR start synpred20_ComplexParser
    public final void synpred20_ComplexParser_fragment() throws RecognitionException {
        // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:121:3: ( VAR SIGG val )
        // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:121:3: VAR SIGG val
        {
        match(input,VAR,FOLLOW_VAR_in_synpred20_ComplexParser470); if (state.failed) return ;
        match(input,SIGG,FOLLOW_SIGG_in_synpred20_ComplexParser472); if (state.failed) return ;
        pushFollow(FOLLOW_val_in_synpred20_ComplexParser474);
        val();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred20_ComplexParser

    // $ANTLR start synpred21_ComplexParser
    public final void synpred21_ComplexParser_fragment() throws RecognitionException {
        // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:122:3: ( name SIGG val )
        // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:122:3: name SIGG val
        {
        pushFollow(FOLLOW_name_in_synpred21_ComplexParser488);
        name();

        state._fsp--;
        if (state.failed) return ;
        match(input,SIGG,FOLLOW_SIGG_in_synpred21_ComplexParser490); if (state.failed) return ;
        pushFollow(FOLLOW_val_in_synpred21_ComplexParser492);
        val();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred21_ComplexParser

    // $ANTLR start synpred22_ComplexParser
    public final void synpred22_ComplexParser_fragment() throws RecognitionException {
        // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:123:3: ( VAR SIGLE val )
        // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:123:3: VAR SIGLE val
        {
        match(input,VAR,FOLLOW_VAR_in_synpred22_ComplexParser506); if (state.failed) return ;
        match(input,SIGLE,FOLLOW_SIGLE_in_synpred22_ComplexParser508); if (state.failed) return ;
        pushFollow(FOLLOW_val_in_synpred22_ComplexParser510);
        val();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred22_ComplexParser

    // $ANTLR start synpred23_ComplexParser
    public final void synpred23_ComplexParser_fragment() throws RecognitionException {
        // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:124:3: ( name SIGLE val )
        // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:124:3: name SIGLE val
        {
        pushFollow(FOLLOW_name_in_synpred23_ComplexParser524);
        name();

        state._fsp--;
        if (state.failed) return ;
        match(input,SIGLE,FOLLOW_SIGLE_in_synpred23_ComplexParser526); if (state.failed) return ;
        pushFollow(FOLLOW_val_in_synpred23_ComplexParser528);
        val();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred23_ComplexParser

    // $ANTLR start synpred24_ComplexParser
    public final void synpred24_ComplexParser_fragment() throws RecognitionException {
        // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:125:3: ( VAR SIGGE val )
        // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:125:3: VAR SIGGE val
        {
        match(input,VAR,FOLLOW_VAR_in_synpred24_ComplexParser542); if (state.failed) return ;
        match(input,SIGGE,FOLLOW_SIGGE_in_synpred24_ComplexParser544); if (state.failed) return ;
        pushFollow(FOLLOW_val_in_synpred24_ComplexParser546);
        val();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred24_ComplexParser

    // $ANTLR start synpred25_ComplexParser
    public final void synpred25_ComplexParser_fragment() throws RecognitionException {
        // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:126:3: ( name SIGGE val )
        // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:126:3: name SIGGE val
        {
        pushFollow(FOLLOW_name_in_synpred25_ComplexParser560);
        name();

        state._fsp--;
        if (state.failed) return ;
        match(input,SIGGE,FOLLOW_SIGGE_in_synpred25_ComplexParser562); if (state.failed) return ;
        pushFollow(FOLLOW_val_in_synpred25_ComplexParser564);
        val();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred25_ComplexParser

    // $ANTLR start synpred26_ComplexParser
    public final void synpred26_ComplexParser_fragment() throws RecognitionException {
        // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:127:10: ( VAR MODC val )
        // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:127:10: VAR MODC val
        {
        match(input,VAR,FOLLOW_VAR_in_synpred26_ComplexParser585); if (state.failed) return ;
        match(input,MODC,FOLLOW_MODC_in_synpred26_ComplexParser587); if (state.failed) return ;
        pushFollow(FOLLOW_val_in_synpred26_ComplexParser589);
        val();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred26_ComplexParser

    // $ANTLR start synpred27_ComplexParser
    public final void synpred27_ComplexParser_fragment() throws RecognitionException {
        // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:128:10: ( name MODC val )
        // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:128:10: name MODC val
        {
        pushFollow(FOLLOW_name_in_synpred27_ComplexParser610);
        name();

        state._fsp--;
        if (state.failed) return ;
        match(input,MODC,FOLLOW_MODC_in_synpred27_ComplexParser612); if (state.failed) return ;
        pushFollow(FOLLOW_val_in_synpred27_ComplexParser614);
        val();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred27_ComplexParser

    // Delegated rules

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
    public final boolean synpred19_ComplexParser() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred19_ComplexParser_fragment(); // can never throw exception
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
    public final boolean synpred12_ComplexParser() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred12_ComplexParser_fragment(); // can never throw exception
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
    public final boolean synpred18_ComplexParser() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred18_ComplexParser_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred11_ComplexParser() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred11_ComplexParser_fragment(); // can never throw exception
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


    protected DFA9 dfa9 = new DFA9(this);
    protected DFA13 dfa13 = new DFA13(this);
    static final String DFA9_eotS =
        "\14\uffff";
    static final String DFA9_eofS =
        "\1\5\13\uffff";
    static final String DFA9_minS =
        "\1\17\4\0\7\uffff";
    static final String DFA9_maxS =
        "\1\36\4\0\7\uffff";
    static final String DFA9_acceptS =
        "\5\uffff\1\3\3\uffff\1\1\1\2\1\4";
    static final String DFA9_specialS =
        "\1\uffff\1\0\1\1\1\2\1\3\7\uffff}>";
    static final String[] DFA9_transitionS = {
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

    static final short[] DFA9_eot = DFA.unpackEncodedString(DFA9_eotS);
    static final short[] DFA9_eof = DFA.unpackEncodedString(DFA9_eofS);
    static final char[] DFA9_min = DFA.unpackEncodedStringToUnsignedChars(DFA9_minS);
    static final char[] DFA9_max = DFA.unpackEncodedStringToUnsignedChars(DFA9_maxS);
    static final short[] DFA9_accept = DFA.unpackEncodedString(DFA9_acceptS);
    static final short[] DFA9_special = DFA.unpackEncodedString(DFA9_specialS);
    static final short[][] DFA9_transition;

    static {
        int numStates = DFA9_transitionS.length;
        DFA9_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA9_transition[i] = DFA.unpackEncodedString(DFA9_transitionS[i]);
        }
    }

    class DFA9 extends DFA {

        public DFA9(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 9;
            this.eot = DFA9_eot;
            this.eof = DFA9_eof;
            this.min = DFA9_min;
            this.max = DFA9_max;
            this.accept = DFA9_accept;
            this.special = DFA9_special;
            this.transition = DFA9_transition;
        }
        public String getDescription() {
            return "91:1: querylist : ( oquery DEL querylist | sent DEL querylist | oquery | sent );";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 :
                        int LA9_1 = input.LA(1);


                        int index9_1 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred11_ComplexParser()) ) {s = 9;}

                        else if ( (synpred13_ComplexParser()) ) {s = 5;}


                        input.seek(index9_1);
                        if ( s>=0 ) return s;
                        break;
                    case 1 :
                        int LA9_2 = input.LA(1);


                        int index9_2 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred11_ComplexParser()) ) {s = 9;}

                        else if ( (synpred12_ComplexParser()) ) {s = 10;}

                        else if ( (synpred13_ComplexParser()) ) {s = 5;}

                        else if ( (true) ) {s = 11;}


                        input.seek(index9_2);
                        if ( s>=0 ) return s;
                        break;
                    case 2 :
                        int LA9_3 = input.LA(1);


                        int index9_3 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred11_ComplexParser()) ) {s = 9;}

                        else if ( (synpred13_ComplexParser()) ) {s = 5;}


                        input.seek(index9_3);
                        if ( s>=0 ) return s;
                        break;
                    case 3 :
                        int LA9_4 = input.LA(1);


                        int index9_4 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred11_ComplexParser()) ) {s = 9;}

                        else if ( (synpred12_ComplexParser()) ) {s = 10;}

                        else if ( (synpred13_ComplexParser()) ) {s = 5;}

                        else if ( (true) ) {s = 11;}


                        input.seek(index9_4);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 9, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String DFA13_eotS =
        "\31\uffff";
    static final String DFA13_eofS =
        "\31\uffff";
    static final String DFA13_minS =
        "\1\30\2\6\5\23\7\uffff\5\0\5\uffff";
    static final String DFA13_maxS =
        "\1\33\2\22\5\25\7\uffff\5\0\5\uffff";
    static final String DFA13_acceptS =
        "\10\uffff\1\1\1\14\1\7\1\5\1\3\1\13\1\11\5\uffff\1\2\1\4\1\6\1\10"+
        "\1\12";
    static final String DFA13_specialS =
        "\17\uffff\1\0\1\2\1\1\1\4\1\3\5\uffff}>";
    static final String[] DFA13_transitionS = {
            "\1\2\2\uffff\1\1",
            "\1\3\1\4\1\10\1\5\1\6\5\uffff\1\11\1\uffff\1\7",
            "\1\14\1\13\1\10\1\12\1\16\5\uffff\1\11\1\uffff\1\15",
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

    static final short[] DFA13_eot = DFA.unpackEncodedString(DFA13_eotS);
    static final short[] DFA13_eof = DFA.unpackEncodedString(DFA13_eofS);
    static final char[] DFA13_min = DFA.unpackEncodedStringToUnsignedChars(DFA13_minS);
    static final char[] DFA13_max = DFA.unpackEncodedStringToUnsignedChars(DFA13_maxS);
    static final short[] DFA13_accept = DFA.unpackEncodedString(DFA13_acceptS);
    static final short[] DFA13_special = DFA.unpackEncodedString(DFA13_specialS);
    static final short[][] DFA13_transition;

    static {
        int numStates = DFA13_transitionS.length;
        DFA13_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA13_transition[i] = DFA.unpackEncodedString(DFA13_transitionS[i]);
        }
    }

    class DFA13 extends DFA {

        public DFA13(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 13;
            this.eot = DFA13_eot;
            this.eof = DFA13_eof;
            this.min = DFA13_min;
            this.max = DFA13_max;
            this.accept = DFA13_accept;
            this.special = DFA13_special;
            this.transition = DFA13_transition;
        }
        public String getDescription() {
            return "117:1: sent : ( name SIGE val -> ^( ASIGN name val ) | VAR SIGL val -> ^( COMPL VAR val ) | name SIGL val -> ^( COMPL name val ) | VAR SIGG val -> ^( COMPG VAR val ) | name SIGG val -> ^( COMPG name val ) | VAR SIGLE val -> ^( COMPLE VAR val ) | name SIGLE val -> ^( COMPLE name val ) | VAR SIGGE val -> ^( COMPGE VAR val ) | name SIGGE val -> ^( COMPGE name val ) | VAR MODC val -> ^( COMPAS VAR val ) | name MODC val -> ^( COMPAS name val ) | name MODN val DELY val ( VAR )? -> ^( COMPRNG name ^( INTERVAL val val ) ) );";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 :
                        int LA13_15 = input.LA(1);


                        int index13_15 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred18_ComplexParser()) ) {s = 20;}

                        else if ( (synpred19_ComplexParser()) ) {s = 12;}


                        input.seek(index13_15);
                        if ( s>=0 ) return s;
                        break;
                    case 1 :
                        int LA13_17 = input.LA(1);


                        int index13_17 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred22_ComplexParser()) ) {s = 22;}

                        else if ( (synpred23_ComplexParser()) ) {s = 10;}


                        input.seek(index13_17);
                        if ( s>=0 ) return s;
                        break;
                    case 2 :
                        int LA13_16 = input.LA(1);


                        int index13_16 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred20_ComplexParser()) ) {s = 21;}

                        else if ( (synpred21_ComplexParser()) ) {s = 11;}


                        input.seek(index13_16);
                        if ( s>=0 ) return s;
                        break;
                    case 3 :
                        int LA13_19 = input.LA(1);


                        int index13_19 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred26_ComplexParser()) ) {s = 24;}

                        else if ( (synpred27_ComplexParser()) ) {s = 13;}


                        input.seek(index13_19);
                        if ( s>=0 ) return s;
                        break;
                    case 4 :
                        int LA13_18 = input.LA(1);


                        int index13_18 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred24_ComplexParser()) ) {s = 23;}

                        else if ( (synpred25_ComplexParser()) ) {s = 14;}


                        input.seek(index13_18);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 13, _s, input);
            error(nvae);
            throw nvae;
        }
    }


    public static final BitSet FOLLOW_limiter_in_squery109 = new BitSet(new long[]{0x0000000019028000L});
    public static final BitSet FOLLOW_oquery_in_squery112 = new BitSet(new long[]{0x0000000000028000L});
    public static final BitSet FOLLOW_modifier_in_squery114 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_squery117 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_limiter_in_squery135 = new BitSet(new long[]{0x0000000009001000L});
    public static final BitSet FOLLOW_pquery_in_squery138 = new BitSet(new long[]{0x0000000000028000L});
    public static final BitSet FOLLOW_modifier_in_squery140 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_squery143 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NUM_in_limiter168 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ordterm_in_modifier187 = new BitSet(new long[]{0x0000000000028002L});
    public static final BitSet FOLLOW_offsetterm_in_modifier189 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_offsetterm_in_modifier194 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_MODE_in_offsetterm205 = new BitSet(new long[]{0x0000000000100000L});
    public static final BitSet FOLLOW_NUM_in_offsetterm207 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_MODO_in_ordterm226 = new BitSet(new long[]{0x0000000010000000L});
    public static final BitSet FOLLOW_LPAR_in_ordterm228 = new BitSet(new long[]{0x0000000009000000L});
    public static final BitSet FOLLOW_plist_in_ordterm230 = new BitSet(new long[]{0x0000000020000000L});
    public static final BitSet FOLLOW_RPAR_in_ordterm232 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_name_in_oquery254 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_name_in_oquery258 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_PREC_in_oquery260 = new BitSet(new long[]{0x0000000059000000L});
    public static final BitSet FOLLOW_querylist_in_oquery262 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LPAR_in_oquery280 = new BitSet(new long[]{0x0000000039000000L});
    public static final BitSet FOLLOW_oquery_in_oquery283 = new BitSet(new long[]{0x0000000020000000L});
    public static final BitSet FOLLOW_RPAR_in_oquery285 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_oquery_in_querylist297 = new BitSet(new long[]{0x0000000040000000L});
    public static final BitSet FOLLOW_DEL_in_querylist299 = new BitSet(new long[]{0x0000000059000000L});
    public static final BitSet FOLLOW_querylist_in_querylist302 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_sent_in_querylist306 = new BitSet(new long[]{0x0000000040000000L});
    public static final BitSet FOLLOW_DEL_in_querylist308 = new BitSet(new long[]{0x0000000059000000L});
    public static final BitSet FOLLOW_querylist_in_querylist311 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_oquery_in_querylist315 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_sent_in_querylist319 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_plist_in_pquery330 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_PRED_in_pquery332 = new BitSet(new long[]{0x0000000019000000L});
    public static final BitSet FOLLOW_oquery_in_pquery334 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_MODT_in_pquery352 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_PRED_in_pquery354 = new BitSet(new long[]{0x0000000019000000L});
    public static final BitSet FOLLOW_oquery_in_pquery356 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_name_in_plist381 = new BitSet(new long[]{0x0000000040000000L});
    public static final BitSet FOLLOW_DEL_in_plist383 = new BitSet(new long[]{0x0000000009000000L});
    public static final BitSet FOLLOW_plist_in_plist386 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_name_in_plist390 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_name0 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_name_in_sent416 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_SIGE_in_sent418 = new BitSet(new long[]{0x0000000000380000L});
    public static final BitSet FOLLOW_val_in_sent420 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_VAR_in_sent434 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_SIGL_in_sent436 = new BitSet(new long[]{0x0000000000380000L});
    public static final BitSet FOLLOW_val_in_sent438 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_name_in_sent452 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_SIGL_in_sent454 = new BitSet(new long[]{0x0000000000380000L});
    public static final BitSet FOLLOW_val_in_sent456 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_VAR_in_sent470 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_SIGG_in_sent472 = new BitSet(new long[]{0x0000000000380000L});
    public static final BitSet FOLLOW_val_in_sent474 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_name_in_sent488 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_SIGG_in_sent490 = new BitSet(new long[]{0x0000000000380000L});
    public static final BitSet FOLLOW_val_in_sent492 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_VAR_in_sent506 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_SIGLE_in_sent508 = new BitSet(new long[]{0x0000000000380000L});
    public static final BitSet FOLLOW_val_in_sent510 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_name_in_sent524 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_SIGLE_in_sent526 = new BitSet(new long[]{0x0000000000380000L});
    public static final BitSet FOLLOW_val_in_sent528 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_VAR_in_sent542 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_SIGGE_in_sent544 = new BitSet(new long[]{0x0000000000380000L});
    public static final BitSet FOLLOW_val_in_sent546 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_name_in_sent560 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_SIGGE_in_sent562 = new BitSet(new long[]{0x0000000000380000L});
    public static final BitSet FOLLOW_val_in_sent564 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_VAR_in_sent585 = new BitSet(new long[]{0x0000000000040000L});
    public static final BitSet FOLLOW_MODC_in_sent587 = new BitSet(new long[]{0x0000000000380000L});
    public static final BitSet FOLLOW_val_in_sent589 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_name_in_sent610 = new BitSet(new long[]{0x0000000000040000L});
    public static final BitSet FOLLOW_MODC_in_sent612 = new BitSet(new long[]{0x0000000000380000L});
    public static final BitSet FOLLOW_val_in_sent614 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_name_in_sent635 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_MODN_in_sent637 = new BitSet(new long[]{0x0000000000380000L});
    public static final BitSet FOLLOW_val_in_sent639 = new BitSet(new long[]{0x0000000004000000L});
    public static final BitSet FOLLOW_DELY_in_sent641 = new BitSet(new long[]{0x0000000000380000L});
    public static final BitSet FOLLOW_val_in_sent643 = new BitSet(new long[]{0x0000000008000002L});
    public static final BitSet FOLLOW_VAR_in_sent645 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_val0 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_oquery_in_synpred11_ComplexParser297 = new BitSet(new long[]{0x0000000040000000L});
    public static final BitSet FOLLOW_DEL_in_synpred11_ComplexParser299 = new BitSet(new long[]{0x0000000059000000L});
    public static final BitSet FOLLOW_querylist_in_synpred11_ComplexParser302 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_sent_in_synpred12_ComplexParser306 = new BitSet(new long[]{0x0000000040000000L});
    public static final BitSet FOLLOW_DEL_in_synpred12_ComplexParser308 = new BitSet(new long[]{0x0000000059000000L});
    public static final BitSet FOLLOW_querylist_in_synpred12_ComplexParser311 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_oquery_in_synpred13_ComplexParser315 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_VAR_in_synpred18_ComplexParser434 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_SIGL_in_synpred18_ComplexParser436 = new BitSet(new long[]{0x0000000000380000L});
    public static final BitSet FOLLOW_val_in_synpred18_ComplexParser438 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_name_in_synpred19_ComplexParser452 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_SIGL_in_synpred19_ComplexParser454 = new BitSet(new long[]{0x0000000000380000L});
    public static final BitSet FOLLOW_val_in_synpred19_ComplexParser456 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_VAR_in_synpred20_ComplexParser470 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_SIGG_in_synpred20_ComplexParser472 = new BitSet(new long[]{0x0000000000380000L});
    public static final BitSet FOLLOW_val_in_synpred20_ComplexParser474 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_name_in_synpred21_ComplexParser488 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_SIGG_in_synpred21_ComplexParser490 = new BitSet(new long[]{0x0000000000380000L});
    public static final BitSet FOLLOW_val_in_synpred21_ComplexParser492 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_VAR_in_synpred22_ComplexParser506 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_SIGLE_in_synpred22_ComplexParser508 = new BitSet(new long[]{0x0000000000380000L});
    public static final BitSet FOLLOW_val_in_synpred22_ComplexParser510 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_name_in_synpred23_ComplexParser524 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_SIGLE_in_synpred23_ComplexParser526 = new BitSet(new long[]{0x0000000000380000L});
    public static final BitSet FOLLOW_val_in_synpred23_ComplexParser528 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_VAR_in_synpred24_ComplexParser542 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_SIGGE_in_synpred24_ComplexParser544 = new BitSet(new long[]{0x0000000000380000L});
    public static final BitSet FOLLOW_val_in_synpred24_ComplexParser546 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_name_in_synpred25_ComplexParser560 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_SIGGE_in_synpred25_ComplexParser562 = new BitSet(new long[]{0x0000000000380000L});
    public static final BitSet FOLLOW_val_in_synpred25_ComplexParser564 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_VAR_in_synpred26_ComplexParser585 = new BitSet(new long[]{0x0000000000040000L});
    public static final BitSet FOLLOW_MODC_in_synpred26_ComplexParser587 = new BitSet(new long[]{0x0000000000380000L});
    public static final BitSet FOLLOW_val_in_synpred26_ComplexParser589 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_name_in_synpred27_ComplexParser610 = new BitSet(new long[]{0x0000000000040000L});
    public static final BitSet FOLLOW_MODC_in_synpred27_ComplexParser612 = new BitSet(new long[]{0x0000000000380000L});
    public static final BitSet FOLLOW_val_in_synpred27_ComplexParser614 = new BitSet(new long[]{0x0000000000000002L});

}