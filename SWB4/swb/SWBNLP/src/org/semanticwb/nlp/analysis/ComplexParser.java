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
// $ANTLR 3.1.2 /home/hasdai/Documentos/INFOTEC/ComplexParser.g 2009-07-14 17:20:45

import org.antlr.runtime.*;
import org.antlr.runtime.tree.*;

public class ComplexParser extends Parser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "WHITESPACE", "SIGN", "SIGL", "SIGG", "SIGE", "SIGLE", "SIGGE", "PREC", "MODT", "PRED", "PREN", "MODE", "MODO", "MODC", "BOL", "NUM", "LIT", "LBRK", "RBRK", "BVAR", "ORDOP", "VAR", "LPAR", "RPAR", "DEL", "SIGI", "LIMIT", "SELECT", "ASIGN", "COMPL", "COMPG", "COMPLE", "COMPGE", "PRECON", "PREDE", "OFFSET", "ORDER", "COMPNAME", "MODTO", "NAME", "ADJASIGN", "PREEN", "LIKE"
    };
    public static final int SIGL=6;
    public static final int PREC=11;
    public static final int LPAR=26;
    public static final int MODE=15;
    public static final int COMPGE=36;
    public static final int SIGI=29;
    public static final int ORDER=40;
    public static final int COMPNAME=41;
    public static final int SELECT=31;
    public static final int COMPG=34;
    public static final int SIGN=5;
    public static final int COMPL=33;
    public static final int DEL=28;
    public static final int SIGE=8;
    public static final int PRECON=37;
    public static final int RBRK=22;
    public static final int PREEN=45;
    public static final int ADJASIGN=44;
    public static final int ASIGN=32;
    public static final int MODO=16;
    public static final int RPAR=27;
    public static final int SIGGE=10;
    public static final int SIGG=7;
    public static final int OFFSET=39;
    public static final int PREDE=38;
    public static final int COMPLE=35;
    public static final int LIT=20;
    public static final int BOL=18;
    public static final int PRED=13;
    public static final int LIKE=46;
    public static final int SIGLE=9;
    public static final int WHITESPACE=4;
    public static final int ORDOP=24;
    public static final int VAR=25;
    public static final int MODT=12;
    public static final int MODC=17;
    public static final int PREN=14;
    public static final int LBRK=21;
    public static final int EOF=-1;
    public static final int NUM=19;
    public static final int MODTO=42;
    public static final int NAME=43;
    public static final int BVAR=23;
    public static final int LIMIT=30;

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
                    int LA5_3 = input.LA(3);

                    if ( (LA5_3==PRED||LA5_3==DEL) ) {
                        alt5=2;
                    }
                    else if ( (LA5_3==EOF||LA5_3==PREC||(LA5_3>=PREN && LA5_3<=MODO)||LA5_3==BVAR||LA5_3==VAR) ) {
                        alt5=1;
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

                if ( (LA5_3==PRED||LA5_3==DEL) ) {
                    alt5=2;
                }
                else if ( (LA5_3==EOF||LA5_3==PREC||(LA5_3>=PREN && LA5_3<=MODO)||LA5_3==BVAR||LA5_3==VAR) ) {
                    alt5=1;
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
                            pushFollow(FOLLOW_limiter_in_squery108);
                            limiter1=limiter();

                            state._fsp--;
                            if (state.failed) return retval;
                            if ( state.backtracking==0 ) stream_limiter.add(limiter1.getTree());

                            }
                            break;

                    }

                    pushFollow(FOLLOW_oquery_in_squery111);
                    oquery2=oquery();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_oquery.add(oquery2.getTree());
                    // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:52:19: ( modifier )?
                    int alt2=2;
                    int LA2_0 = input.LA(1);

                    if ( ((LA2_0>=MODE && LA2_0<=MODO)) ) {
                        alt2=1;
                    }
                    switch (alt2) {
                        case 1 :
                            // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:0:0: modifier
                            {
                            pushFollow(FOLLOW_modifier_in_squery113);
                            modifier3=modifier();

                            state._fsp--;
                            if (state.failed) return retval;
                            if ( state.backtracking==0 ) stream_modifier.add(modifier3.getTree());

                            }
                            break;

                    }

                    EOF4=(Token)match(input,EOF,FOLLOW_EOF_in_squery116); if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_EOF.add(EOF4);



                    // AST REWRITE
                    // elements: limiter, oquery, modifier
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
                            pushFollow(FOLLOW_limiter_in_squery134);
                            limiter5=limiter();

                            state._fsp--;
                            if (state.failed) return retval;
                            if ( state.backtracking==0 ) stream_limiter.add(limiter5.getTree());

                            }
                            break;

                    }

                    pushFollow(FOLLOW_pquery_in_squery137);
                    pquery6=pquery();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_pquery.add(pquery6.getTree());
                    // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:53:19: ( modifier )?
                    int alt4=2;
                    int LA4_0 = input.LA(1);

                    if ( ((LA4_0>=MODE && LA4_0<=MODO)) ) {
                        alt4=1;
                    }
                    switch (alt4) {
                        case 1 :
                            // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:0:0: modifier
                            {
                            pushFollow(FOLLOW_modifier_in_squery139);
                            modifier7=modifier();

                            state._fsp--;
                            if (state.failed) return retval;
                            if ( state.backtracking==0 ) stream_modifier.add(modifier7.getTree());

                            }
                            break;

                    }

                    EOF8=(Token)match(input,EOF,FOLLOW_EOF_in_squery142); if (state.failed) return retval;
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
            NUM9=(Token)match(input,NUM,FOLLOW_NUM_in_limiter167); if (state.failed) return retval;
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

                    pushFollow(FOLLOW_ordterm_in_modifier186);
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
                            pushFollow(FOLLOW_offsetterm_in_modifier188);
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

                    pushFollow(FOLLOW_offsetterm_in_modifier193);
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
            MODE13=(Token)match(input,MODE,FOLLOW_MODE_in_offsetterm204); if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_MODE.add(MODE13);

            NUM14=(Token)match(input,NUM,FOLLOW_NUM_in_offsetterm206); if (state.failed) return retval;
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
            MODO15=(Token)match(input,MODO,FOLLOW_MODO_in_ordterm225); if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_MODO.add(MODO15);

            LPAR16=(Token)match(input,LPAR,FOLLOW_LPAR_in_ordterm227); if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_LPAR.add(LPAR16);

            pushFollow(FOLLOW_plist_in_ordterm229);
            plist17=plist();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_plist.add(plist17.getTree());
            RPAR18=(Token)match(input,RPAR,FOLLOW_RPAR_in_ordterm231); if (state.failed) return retval;
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
    // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:83:1: oquery : ( | name | name PREN name -> ^( PREEN name name ) | name PREN name PREC querylist -> ^( PRECON ^( PREEN name name ) querylist ) | name name PREC querylist -> ^( PRECON ^( ADJASIGN name name ) querylist ) | name PREC querylist -> ^( name ^( PRECON querylist ) ) | LPAR oquery RPAR );
    public final ComplexParser.oquery_return oquery() throws RecognitionException {
        ComplexParser.oquery_return retval = new ComplexParser.oquery_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token PREN21=null;
        Token PREN24=null;
        Token PREC26=null;
        Token PREC30=null;
        Token PREC33=null;
        Token LPAR35=null;
        Token RPAR37=null;
        ComplexParser.name_return name19 = null;

        ComplexParser.name_return name20 = null;

        ComplexParser.name_return name22 = null;

        ComplexParser.name_return name23 = null;

        ComplexParser.name_return name25 = null;

        ComplexParser.querylist_return querylist27 = null;

        ComplexParser.name_return name28 = null;

        ComplexParser.name_return name29 = null;

        ComplexParser.querylist_return querylist31 = null;

        ComplexParser.name_return name32 = null;

        ComplexParser.querylist_return querylist34 = null;

        ComplexParser.oquery_return oquery36 = null;


        Object PREN21_tree=null;
        Object PREN24_tree=null;
        Object PREC26_tree=null;
        Object PREC30_tree=null;
        Object PREC33_tree=null;
        Object LPAR35_tree=null;
        Object RPAR37_tree=null;
        RewriteRuleTokenStream stream_PREN=new RewriteRuleTokenStream(adaptor,"token PREN");
        RewriteRuleTokenStream stream_PREC=new RewriteRuleTokenStream(adaptor,"token PREC");
        RewriteRuleSubtreeStream stream_querylist=new RewriteRuleSubtreeStream(adaptor,"rule querylist");
        RewriteRuleSubtreeStream stream_name=new RewriteRuleSubtreeStream(adaptor,"rule name");
        try {
            // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:84:1: ( | name | name PREN name -> ^( PREEN name name ) | name PREN name PREC querylist -> ^( PRECON ^( PREEN name name ) querylist ) | name name PREC querylist -> ^( PRECON ^( ADJASIGN name name ) querylist ) | name PREC querylist -> ^( name ^( PRECON querylist ) ) | LPAR oquery RPAR )
            int alt8=7;
            alt8 = dfa8.predict(input);
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

                    pushFollow(FOLLOW_name_in_oquery253);
                    name19=name();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, name19.getTree());

                    }
                    break;
                case 3 :
                    // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:86:3: name PREN name
                    {
                    pushFollow(FOLLOW_name_in_oquery257);
                    name20=name();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_name.add(name20.getTree());
                    PREN21=(Token)match(input,PREN,FOLLOW_PREN_in_oquery259); if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_PREN.add(PREN21);

                    pushFollow(FOLLOW_name_in_oquery261);
                    name22=name();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_name.add(name22.getTree());


                    // AST REWRITE
                    // elements: name, name
                    // token labels:
                    // rule labels: retval
                    // token list labels:
                    // rule list labels:
                    // wildcard labels:
                    if ( state.backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 86:18: -> ^( PREEN name name )
                    {
                        // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:86:21: ^( PREEN name name )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(PREEN, "PREEN"), root_1);

                        adaptor.addChild(root_1, stream_name.nextTree());
                        adaptor.addChild(root_1, stream_name.nextTree());

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;}
                    }
                    break;
                case 4 :
                    // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:87:3: name PREN name PREC querylist
                    {
                    pushFollow(FOLLOW_name_in_oquery275);
                    name23=name();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_name.add(name23.getTree());
                    PREN24=(Token)match(input,PREN,FOLLOW_PREN_in_oquery277); if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_PREN.add(PREN24);

                    pushFollow(FOLLOW_name_in_oquery279);
                    name25=name();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_name.add(name25.getTree());
                    PREC26=(Token)match(input,PREC,FOLLOW_PREC_in_oquery281); if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_PREC.add(PREC26);

                    pushFollow(FOLLOW_querylist_in_oquery283);
                    querylist27=querylist();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_querylist.add(querylist27.getTree());


                    // AST REWRITE
                    // elements: querylist, name, name
                    // token labels:
                    // rule labels: retval
                    // token list labels:
                    // rule list labels:
                    // wildcard labels:
                    if ( state.backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 87:33: -> ^( PRECON ^( PREEN name name ) querylist )
                    {
                        // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:87:36: ^( PRECON ^( PREEN name name ) querylist )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(PRECON, "PRECON"), root_1);

                        // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:87:45: ^( PREEN name name )
                        {
                        Object root_2 = (Object)adaptor.nil();
                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(PREEN, "PREEN"), root_2);

                        adaptor.addChild(root_2, stream_name.nextTree());
                        adaptor.addChild(root_2, stream_name.nextTree());

                        adaptor.addChild(root_1, root_2);
                        }
                        adaptor.addChild(root_1, stream_querylist.nextTree());

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;}
                    }
                    break;
                case 5 :
                    // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:88:3: name name PREC querylist
                    {
                    pushFollow(FOLLOW_name_in_oquery303);
                    name28=name();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_name.add(name28.getTree());
                    pushFollow(FOLLOW_name_in_oquery305);
                    name29=name();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_name.add(name29.getTree());
                    PREC30=(Token)match(input,PREC,FOLLOW_PREC_in_oquery307); if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_PREC.add(PREC30);

                    pushFollow(FOLLOW_querylist_in_oquery309);
                    querylist31=querylist();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_querylist.add(querylist31.getTree());
                    if ( state.backtracking==0 ) {
                      precon = true;
                    }


                    // AST REWRITE
                    // elements: name, querylist, name
                    // token labels:
                    // rule labels: retval
                    // token list labels:
                    // rule list labels:
                    // wildcard labels:
                    if ( state.backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 88:45: -> ^( PRECON ^( ADJASIGN name name ) querylist )
                    {
                        // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:88:48: ^( PRECON ^( ADJASIGN name name ) querylist )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(PRECON, "PRECON"), root_1);

                        // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:88:57: ^( ADJASIGN name name )
                        {
                        Object root_2 = (Object)adaptor.nil();
                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(ADJASIGN, "ADJASIGN"), root_2);

                        adaptor.addChild(root_2, stream_name.nextTree());
                        adaptor.addChild(root_2, stream_name.nextTree());

                        adaptor.addChild(root_1, root_2);
                        }
                        adaptor.addChild(root_1, stream_querylist.nextTree());

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;}
                    }
                    break;
                case 6 :
                    // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:89:3: name PREC querylist
                    {
                    pushFollow(FOLLOW_name_in_oquery330);
                    name32=name();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_name.add(name32.getTree());
                    PREC33=(Token)match(input,PREC,FOLLOW_PREC_in_oquery332); if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_PREC.add(PREC33);

                    pushFollow(FOLLOW_querylist_in_oquery334);
                    querylist34=querylist();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_querylist.add(querylist34.getTree());
                    if ( state.backtracking==0 ) {
                      precon = true;
                    }


                    // AST REWRITE
                    // elements: name, querylist
                    // token labels:
                    // rule labels: retval
                    // token list labels:
                    // rule list labels:
                    // wildcard labels:
                    if ( state.backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 89:40: -> ^( name ^( PRECON querylist ) )
                    {
                        // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:89:43: ^( name ^( PRECON querylist ) )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot(stream_name.nextNode(), root_1);

                        // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:89:50: ^( PRECON querylist )
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
                case 7 :
                    // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:90:3: LPAR oquery RPAR
                    {
                    root_0 = (Object)adaptor.nil();

                    LPAR35=(Token)match(input,LPAR,FOLLOW_LPAR_in_oquery352); if (state.failed) return retval;
                    pushFollow(FOLLOW_oquery_in_oquery355);
                    oquery36=oquery();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, oquery36.getTree());
                    RPAR37=(Token)match(input,RPAR,FOLLOW_RPAR_in_oquery357); if (state.failed) return retval;

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
    // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:94:1: querylist : ( oquery DEL querylist | sent DEL querylist | oquery | sent );
    public final ComplexParser.querylist_return querylist() throws RecognitionException {
        ComplexParser.querylist_return retval = new ComplexParser.querylist_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token DEL39=null;
        Token DEL42=null;
        ComplexParser.oquery_return oquery38 = null;

        ComplexParser.querylist_return querylist40 = null;

        ComplexParser.sent_return sent41 = null;

        ComplexParser.querylist_return querylist43 = null;

        ComplexParser.oquery_return oquery44 = null;

        ComplexParser.sent_return sent45 = null;


        Object DEL39_tree=null;
        Object DEL42_tree=null;

        try {
            // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:95:1: ( oquery DEL querylist | sent DEL querylist | oquery | sent )
            int alt9=4;
            alt9 = dfa9.predict(input);
            switch (alt9) {
                case 1 :
                    // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:95:3: oquery DEL querylist
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_oquery_in_querylist369);
                    oquery38=oquery();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, oquery38.getTree());
                    DEL39=(Token)match(input,DEL,FOLLOW_DEL_in_querylist371); if (state.failed) return retval;
                    pushFollow(FOLLOW_querylist_in_querylist374);
                    querylist40=querylist();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, querylist40.getTree());

                    }
                    break;
                case 2 :
                    // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:96:3: sent DEL querylist
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_sent_in_querylist378);
                    sent41=sent();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, sent41.getTree());
                    DEL42=(Token)match(input,DEL,FOLLOW_DEL_in_querylist380); if (state.failed) return retval;
                    pushFollow(FOLLOW_querylist_in_querylist383);
                    querylist43=querylist();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, querylist43.getTree());

                    }
                    break;
                case 3 :
                    // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:97:3: oquery
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_oquery_in_querylist387);
                    oquery44=oquery();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, oquery44.getTree());

                    }
                    break;
                case 4 :
                    // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:98:3: sent
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_sent_in_querylist391);
                    sent45=sent();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, sent45.getTree());

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
    // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:102:1: pquery : ( plist PRED oquery -> ^( oquery ^( PREDE plist ) ) | MODT PRED oquery -> ^( oquery ^( PREDE MODTO ) ) );
    public final ComplexParser.pquery_return pquery() throws RecognitionException {
        ComplexParser.pquery_return retval = new ComplexParser.pquery_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token PRED47=null;
        Token MODT49=null;
        Token PRED50=null;
        ComplexParser.plist_return plist46 = null;

        ComplexParser.oquery_return oquery48 = null;

        ComplexParser.oquery_return oquery51 = null;


        Object PRED47_tree=null;
        Object MODT49_tree=null;
        Object PRED50_tree=null;
        RewriteRuleTokenStream stream_MODT=new RewriteRuleTokenStream(adaptor,"token MODT");
        RewriteRuleTokenStream stream_PRED=new RewriteRuleTokenStream(adaptor,"token PRED");
        RewriteRuleSubtreeStream stream_plist=new RewriteRuleSubtreeStream(adaptor,"rule plist");
        RewriteRuleSubtreeStream stream_oquery=new RewriteRuleSubtreeStream(adaptor,"rule oquery");
        try {
            // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:103:1: ( plist PRED oquery -> ^( oquery ^( PREDE plist ) ) | MODT PRED oquery -> ^( oquery ^( PREDE MODTO ) ) )
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
                    // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:103:3: plist PRED oquery
                    {
                    pushFollow(FOLLOW_plist_in_pquery402);
                    plist46=plist();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_plist.add(plist46.getTree());
                    PRED47=(Token)match(input,PRED,FOLLOW_PRED_in_pquery404); if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_PRED.add(PRED47);

                    pushFollow(FOLLOW_oquery_in_pquery406);
                    oquery48=oquery();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_oquery.add(oquery48.getTree());
                    if ( state.backtracking==0 ) {
                      prede = true;
                    }


                    // AST REWRITE
                    // elements: oquery, plist
                    // token labels:
                    // rule labels: retval
                    // token list labels:
                    // rule list labels:
                    // wildcard labels:
                    if ( state.backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 103:37: -> ^( oquery ^( PREDE plist ) )
                    {
                        // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:103:40: ^( oquery ^( PREDE plist ) )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot(stream_oquery.nextNode(), root_1);

                        // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:103:49: ^( PREDE plist )
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
                    // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:104:3: MODT PRED oquery
                    {
                    MODT49=(Token)match(input,MODT,FOLLOW_MODT_in_pquery424); if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_MODT.add(MODT49);

                    PRED50=(Token)match(input,PRED,FOLLOW_PRED_in_pquery426); if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_PRED.add(PRED50);

                    pushFollow(FOLLOW_oquery_in_pquery428);
                    oquery51=oquery();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_oquery.add(oquery51.getTree());
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
                    // 104:36: -> ^( oquery ^( PREDE MODTO ) )
                    {
                        // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:104:39: ^( oquery ^( PREDE MODTO ) )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot(stream_oquery.nextNode(), root_1);

                        // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:104:48: ^( PREDE MODTO )
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
    // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:108:1: plist : ( name DEL plist | name );
    public final ComplexParser.plist_return plist() throws RecognitionException {
        ComplexParser.plist_return retval = new ComplexParser.plist_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token DEL53=null;
        ComplexParser.name_return name52 = null;

        ComplexParser.plist_return plist54 = null;

        ComplexParser.name_return name55 = null;


        Object DEL53_tree=null;

        try {
            // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:109:1: ( name DEL plist | name )
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
                    // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:109:3: name DEL plist
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_name_in_plist453);
                    name52=name();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, name52.getTree());
                    DEL53=(Token)match(input,DEL,FOLLOW_DEL_in_plist455); if (state.failed) return retval;
                    pushFollow(FOLLOW_plist_in_plist458);
                    plist54=plist();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, plist54.getTree());

                    }
                    break;
                case 2 :
                    // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:110:3: name
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_name_in_plist462);
                    name55=name();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, name55.getTree());

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
    // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:114:1: name : ( BVAR | VAR );
    public final ComplexParser.name_return name() throws RecognitionException {
        ComplexParser.name_return retval = new ComplexParser.name_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token set56=null;

        Object set56_tree=null;

        try {
            // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:115:1: ( BVAR | VAR )
            // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:
            {
            root_0 = (Object)adaptor.nil();

            set56=(Token)input.LT(1);
            if ( input.LA(1)==BVAR||input.LA(1)==VAR ) {
                input.consume();
                if ( state.backtracking==0 ) adaptor.addChild(root_0, (Object)adaptor.create(set56));
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
    // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:120:1: sent : ( name SIGE val -> ^( ASIGN name val ) | VAR SIGL val -> ^( COMPL VAR val ) | name SIGL val -> ^( COMPL name val ) | VAR SIGG val -> ^( COMPG VAR val ) | name SIGG val -> ^( COMPG name val ) | VAR SIGLE val -> ^( COMPLE VAR val ) | name SIGLE val -> ^( COMPLE name val ) | VAR SIGGE val -> ^( COMPGE VAR val ) | name SIGGE val -> ^( COMPGE name val ) | VAR MODC val -> ^( LIKE VAR val ) | name MODC val -> ^( LIKE name val ) | name val -> ^( ASIGN name val ) | NUM name -> ^( ASIGN name NUM ) );
    public final ComplexParser.sent_return sent() throws RecognitionException {
        ComplexParser.sent_return retval = new ComplexParser.sent_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token SIGE58=null;
        Token VAR60=null;
        Token SIGL61=null;
        Token SIGL64=null;
        Token VAR66=null;
        Token SIGG67=null;
        Token SIGG70=null;
        Token VAR72=null;
        Token SIGLE73=null;
        Token SIGLE76=null;
        Token VAR78=null;
        Token SIGGE79=null;
        Token SIGGE82=null;
        Token VAR84=null;
        Token MODC85=null;
        Token MODC88=null;
        Token NUM92=null;
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

        ComplexParser.val_return val86 = null;

        ComplexParser.name_return name87 = null;

        ComplexParser.val_return val89 = null;

        ComplexParser.name_return name90 = null;

        ComplexParser.val_return val91 = null;

        ComplexParser.name_return name93 = null;


        Object SIGE58_tree=null;
        Object VAR60_tree=null;
        Object SIGL61_tree=null;
        Object SIGL64_tree=null;
        Object VAR66_tree=null;
        Object SIGG67_tree=null;
        Object SIGG70_tree=null;
        Object VAR72_tree=null;
        Object SIGLE73_tree=null;
        Object SIGLE76_tree=null;
        Object VAR78_tree=null;
        Object SIGGE79_tree=null;
        Object SIGGE82_tree=null;
        Object VAR84_tree=null;
        Object MODC85_tree=null;
        Object MODC88_tree=null;
        Object NUM92_tree=null;
        RewriteRuleTokenStream stream_VAR=new RewriteRuleTokenStream(adaptor,"token VAR");
        RewriteRuleTokenStream stream_MODC=new RewriteRuleTokenStream(adaptor,"token MODC");
        RewriteRuleTokenStream stream_SIGL=new RewriteRuleTokenStream(adaptor,"token SIGL");
        RewriteRuleTokenStream stream_SIGGE=new RewriteRuleTokenStream(adaptor,"token SIGGE");
        RewriteRuleTokenStream stream_SIGE=new RewriteRuleTokenStream(adaptor,"token SIGE");
        RewriteRuleTokenStream stream_NUM=new RewriteRuleTokenStream(adaptor,"token NUM");
        RewriteRuleTokenStream stream_SIGLE=new RewriteRuleTokenStream(adaptor,"token SIGLE");
        RewriteRuleTokenStream stream_SIGG=new RewriteRuleTokenStream(adaptor,"token SIGG");
        RewriteRuleSubtreeStream stream_val=new RewriteRuleSubtreeStream(adaptor,"rule val");
        RewriteRuleSubtreeStream stream_name=new RewriteRuleSubtreeStream(adaptor,"rule name");
        try {
            // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:121:1: ( name SIGE val -> ^( ASIGN name val ) | VAR SIGL val -> ^( COMPL VAR val ) | name SIGL val -> ^( COMPL name val ) | VAR SIGG val -> ^( COMPG VAR val ) | name SIGG val -> ^( COMPG name val ) | VAR SIGLE val -> ^( COMPLE VAR val ) | name SIGLE val -> ^( COMPLE name val ) | VAR SIGGE val -> ^( COMPGE VAR val ) | name SIGGE val -> ^( COMPGE name val ) | VAR MODC val -> ^( LIKE VAR val ) | name MODC val -> ^( LIKE name val ) | name val -> ^( ASIGN name val ) | NUM name -> ^( ASIGN name NUM ) )
            int alt12=13;
            alt12 = dfa12.predict(input);
            switch (alt12) {
                case 1 :
                    // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:121:3: name SIGE val
                    {
                    pushFollow(FOLLOW_name_in_sent488);
                    name57=name();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_name.add(name57.getTree());
                    SIGE58=(Token)match(input,SIGE,FOLLOW_SIGE_in_sent490); if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_SIGE.add(SIGE58);

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
                    // 121:17: -> ^( ASIGN name val )
                    {
                        // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:121:20: ^( ASIGN name val )
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
                    // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:122:3: VAR SIGL val
                    {
                    VAR60=(Token)match(input,VAR,FOLLOW_VAR_in_sent506); if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_VAR.add(VAR60);

                    SIGL61=(Token)match(input,SIGL,FOLLOW_SIGL_in_sent508); if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_SIGL.add(SIGL61);

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
                    // 122:16: -> ^( COMPL VAR val )
                    {
                        // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:122:19: ^( COMPL VAR val )
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
                    // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:123:3: name SIGL val
                    {
                    pushFollow(FOLLOW_name_in_sent524);
                    name63=name();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_name.add(name63.getTree());
                    SIGL64=(Token)match(input,SIGL,FOLLOW_SIGL_in_sent526); if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_SIGL.add(SIGL64);

                    pushFollow(FOLLOW_val_in_sent528);
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
                    // 123:17: -> ^( COMPL name val )
                    {
                        // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:123:20: ^( COMPL name val )
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
                    // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:124:3: VAR SIGG val
                    {
                    VAR66=(Token)match(input,VAR,FOLLOW_VAR_in_sent542); if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_VAR.add(VAR66);

                    SIGG67=(Token)match(input,SIGG,FOLLOW_SIGG_in_sent544); if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_SIGG.add(SIGG67);

                    pushFollow(FOLLOW_val_in_sent546);
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
                    // 124:16: -> ^( COMPG VAR val )
                    {
                        // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:124:19: ^( COMPG VAR val )
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
                    // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:125:3: name SIGG val
                    {
                    pushFollow(FOLLOW_name_in_sent560);
                    name69=name();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_name.add(name69.getTree());
                    SIGG70=(Token)match(input,SIGG,FOLLOW_SIGG_in_sent562); if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_SIGG.add(SIGG70);

                    pushFollow(FOLLOW_val_in_sent564);
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
                    // 125:17: -> ^( COMPG name val )
                    {
                        // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:125:20: ^( COMPG name val )
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
                    // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:126:3: VAR SIGLE val
                    {
                    VAR72=(Token)match(input,VAR,FOLLOW_VAR_in_sent578); if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_VAR.add(VAR72);

                    SIGLE73=(Token)match(input,SIGLE,FOLLOW_SIGLE_in_sent580); if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_SIGLE.add(SIGLE73);

                    pushFollow(FOLLOW_val_in_sent582);
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
                    // 126:17: -> ^( COMPLE VAR val )
                    {
                        // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:126:20: ^( COMPLE VAR val )
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
                    // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:127:3: name SIGLE val
                    {
                    pushFollow(FOLLOW_name_in_sent596);
                    name75=name();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_name.add(name75.getTree());
                    SIGLE76=(Token)match(input,SIGLE,FOLLOW_SIGLE_in_sent598); if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_SIGLE.add(SIGLE76);

                    pushFollow(FOLLOW_val_in_sent600);
                    val77=val();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_val.add(val77.getTree());


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
                    // 127:18: -> ^( COMPLE name val )
                    {
                        // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:127:21: ^( COMPLE name val )
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
                    // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:128:3: VAR SIGGE val
                    {
                    VAR78=(Token)match(input,VAR,FOLLOW_VAR_in_sent614); if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_VAR.add(VAR78);

                    SIGGE79=(Token)match(input,SIGGE,FOLLOW_SIGGE_in_sent616); if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_SIGGE.add(SIGGE79);

                    pushFollow(FOLLOW_val_in_sent618);
                    val80=val();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_val.add(val80.getTree());


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
                    // 128:17: -> ^( COMPGE VAR val )
                    {
                        // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:128:20: ^( COMPGE VAR val )
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
                    // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:129:3: name SIGGE val
                    {
                    pushFollow(FOLLOW_name_in_sent632);
                    name81=name();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_name.add(name81.getTree());
                    SIGGE82=(Token)match(input,SIGGE,FOLLOW_SIGGE_in_sent634); if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_SIGGE.add(SIGGE82);

                    pushFollow(FOLLOW_val_in_sent636);
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
                    // 129:18: -> ^( COMPGE name val )
                    {
                        // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:129:21: ^( COMPGE name val )
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
                    // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:130:3: VAR MODC val
                    {
                    VAR84=(Token)match(input,VAR,FOLLOW_VAR_in_sent650); if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_VAR.add(VAR84);

                    MODC85=(Token)match(input,MODC,FOLLOW_MODC_in_sent652); if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_MODC.add(MODC85);

                    pushFollow(FOLLOW_val_in_sent654);
                    val86=val();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_val.add(val86.getTree());


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
                    // 130:16: -> ^( LIKE VAR val )
                    {
                        // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:130:19: ^( LIKE VAR val )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(LIKE, "LIKE"), root_1);

                        adaptor.addChild(root_1, stream_VAR.nextNode());
                        adaptor.addChild(root_1, stream_val.nextTree());

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;}
                    }
                    break;
                case 11 :
                    // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:131:3: name MODC val
                    {
                    pushFollow(FOLLOW_name_in_sent668);
                    name87=name();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_name.add(name87.getTree());
                    MODC88=(Token)match(input,MODC,FOLLOW_MODC_in_sent670); if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_MODC.add(MODC88);

                    pushFollow(FOLLOW_val_in_sent672);
                    val89=val();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_val.add(val89.getTree());


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
                    // 131:17: -> ^( LIKE name val )
                    {
                        // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:131:20: ^( LIKE name val )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(LIKE, "LIKE"), root_1);

                        adaptor.addChild(root_1, stream_name.nextTree());
                        adaptor.addChild(root_1, stream_val.nextTree());

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;}
                    }
                    break;
                case 12 :
                    // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:132:3: name val
                    {
                    pushFollow(FOLLOW_name_in_sent686);
                    name90=name();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_name.add(name90.getTree());
                    pushFollow(FOLLOW_val_in_sent688);
                    val91=val();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_val.add(val91.getTree());


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
                    // 132:12: -> ^( ASIGN name val )
                    {
                        // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:132:15: ^( ASIGN name val )
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
                case 13 :
                    // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:133:3: NUM name
                    {
                    NUM92=(Token)match(input,NUM,FOLLOW_NUM_in_sent702); if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_NUM.add(NUM92);

                    pushFollow(FOLLOW_name_in_sent704);
                    name93=name();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_name.add(name93.getTree());


                    // AST REWRITE
                    // elements: name, NUM
                    // token labels:
                    // rule labels: retval
                    // token list labels:
                    // rule list labels:
                    // wildcard labels:
                    if ( state.backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 133:12: -> ^( ASIGN name NUM )
                    {
                        // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:133:15: ^( ASIGN name NUM )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(ASIGN, "ASIGN"), root_1);

                        adaptor.addChild(root_1, stream_name.nextTree());
                        adaptor.addChild(root_1, stream_NUM.nextNode());

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
    // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:137:1: val : ( LIT | BOL | NUM );
    public final ComplexParser.val_return val() throws RecognitionException {
        ComplexParser.val_return retval = new ComplexParser.val_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token set94=null;

        Object set94_tree=null;

        try {
            // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:138:1: ( LIT | BOL | NUM )
            // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:
            {
            root_0 = (Object)adaptor.nil();

            set94=(Token)input.LT(1);
            if ( (input.LA(1)>=BOL && input.LA(1)<=LIT) ) {
                input.consume();
                if ( state.backtracking==0 ) adaptor.addChild(root_0, (Object)adaptor.create(set94));
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

    // $ANTLR start synpred14_ComplexParser
    public final void synpred14_ComplexParser_fragment() throws RecognitionException {
        // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:95:3: ( oquery DEL querylist )
        // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:95:3: oquery DEL querylist
        {
        pushFollow(FOLLOW_oquery_in_synpred14_ComplexParser369);
        oquery();

        state._fsp--;
        if (state.failed) return ;
        match(input,DEL,FOLLOW_DEL_in_synpred14_ComplexParser371); if (state.failed) return ;
        pushFollow(FOLLOW_querylist_in_synpred14_ComplexParser374);
        querylist();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred14_ComplexParser

    // $ANTLR start synpred15_ComplexParser
    public final void synpred15_ComplexParser_fragment() throws RecognitionException {
        // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:96:3: ( sent DEL querylist )
        // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:96:3: sent DEL querylist
        {
        pushFollow(FOLLOW_sent_in_synpred15_ComplexParser378);
        sent();

        state._fsp--;
        if (state.failed) return ;
        match(input,DEL,FOLLOW_DEL_in_synpred15_ComplexParser380); if (state.failed) return ;
        pushFollow(FOLLOW_querylist_in_synpred15_ComplexParser383);
        querylist();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred15_ComplexParser

    // $ANTLR start synpred16_ComplexParser
    public final void synpred16_ComplexParser_fragment() throws RecognitionException {
        // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:97:3: ( oquery )
        // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:97:3: oquery
        {
        pushFollow(FOLLOW_oquery_in_synpred16_ComplexParser387);
        oquery();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred16_ComplexParser

    // $ANTLR start synpred21_ComplexParser
    public final void synpred21_ComplexParser_fragment() throws RecognitionException {
        // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:122:3: ( VAR SIGL val )
        // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:122:3: VAR SIGL val
        {
        match(input,VAR,FOLLOW_VAR_in_synpred21_ComplexParser506); if (state.failed) return ;
        match(input,SIGL,FOLLOW_SIGL_in_synpred21_ComplexParser508); if (state.failed) return ;
        pushFollow(FOLLOW_val_in_synpred21_ComplexParser510);
        val();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred21_ComplexParser

    // $ANTLR start synpred22_ComplexParser
    public final void synpred22_ComplexParser_fragment() throws RecognitionException {
        // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:123:3: ( name SIGL val )
        // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:123:3: name SIGL val
        {
        pushFollow(FOLLOW_name_in_synpred22_ComplexParser524);
        name();

        state._fsp--;
        if (state.failed) return ;
        match(input,SIGL,FOLLOW_SIGL_in_synpred22_ComplexParser526); if (state.failed) return ;
        pushFollow(FOLLOW_val_in_synpred22_ComplexParser528);
        val();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred22_ComplexParser

    // $ANTLR start synpred23_ComplexParser
    public final void synpred23_ComplexParser_fragment() throws RecognitionException {
        // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:124:3: ( VAR SIGG val )
        // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:124:3: VAR SIGG val
        {
        match(input,VAR,FOLLOW_VAR_in_synpred23_ComplexParser542); if (state.failed) return ;
        match(input,SIGG,FOLLOW_SIGG_in_synpred23_ComplexParser544); if (state.failed) return ;
        pushFollow(FOLLOW_val_in_synpred23_ComplexParser546);
        val();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred23_ComplexParser

    // $ANTLR start synpred24_ComplexParser
    public final void synpred24_ComplexParser_fragment() throws RecognitionException {
        // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:125:3: ( name SIGG val )
        // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:125:3: name SIGG val
        {
        pushFollow(FOLLOW_name_in_synpred24_ComplexParser560);
        name();

        state._fsp--;
        if (state.failed) return ;
        match(input,SIGG,FOLLOW_SIGG_in_synpred24_ComplexParser562); if (state.failed) return ;
        pushFollow(FOLLOW_val_in_synpred24_ComplexParser564);
        val();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred24_ComplexParser

    // $ANTLR start synpred25_ComplexParser
    public final void synpred25_ComplexParser_fragment() throws RecognitionException {
        // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:126:3: ( VAR SIGLE val )
        // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:126:3: VAR SIGLE val
        {
        match(input,VAR,FOLLOW_VAR_in_synpred25_ComplexParser578); if (state.failed) return ;
        match(input,SIGLE,FOLLOW_SIGLE_in_synpred25_ComplexParser580); if (state.failed) return ;
        pushFollow(FOLLOW_val_in_synpred25_ComplexParser582);
        val();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred25_ComplexParser

    // $ANTLR start synpred26_ComplexParser
    public final void synpred26_ComplexParser_fragment() throws RecognitionException {
        // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:127:3: ( name SIGLE val )
        // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:127:3: name SIGLE val
        {
        pushFollow(FOLLOW_name_in_synpred26_ComplexParser596);
        name();

        state._fsp--;
        if (state.failed) return ;
        match(input,SIGLE,FOLLOW_SIGLE_in_synpred26_ComplexParser598); if (state.failed) return ;
        pushFollow(FOLLOW_val_in_synpred26_ComplexParser600);
        val();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred26_ComplexParser

    // $ANTLR start synpred27_ComplexParser
    public final void synpred27_ComplexParser_fragment() throws RecognitionException {
        // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:128:3: ( VAR SIGGE val )
        // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:128:3: VAR SIGGE val
        {
        match(input,VAR,FOLLOW_VAR_in_synpred27_ComplexParser614); if (state.failed) return ;
        match(input,SIGGE,FOLLOW_SIGGE_in_synpred27_ComplexParser616); if (state.failed) return ;
        pushFollow(FOLLOW_val_in_synpred27_ComplexParser618);
        val();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred27_ComplexParser

    // $ANTLR start synpred28_ComplexParser
    public final void synpred28_ComplexParser_fragment() throws RecognitionException {
        // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:129:3: ( name SIGGE val )
        // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:129:3: name SIGGE val
        {
        pushFollow(FOLLOW_name_in_synpred28_ComplexParser632);
        name();

        state._fsp--;
        if (state.failed) return ;
        match(input,SIGGE,FOLLOW_SIGGE_in_synpred28_ComplexParser634); if (state.failed) return ;
        pushFollow(FOLLOW_val_in_synpred28_ComplexParser636);
        val();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred28_ComplexParser

    // $ANTLR start synpred29_ComplexParser
    public final void synpred29_ComplexParser_fragment() throws RecognitionException {
        // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:130:3: ( VAR MODC val )
        // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:130:3: VAR MODC val
        {
        match(input,VAR,FOLLOW_VAR_in_synpred29_ComplexParser650); if (state.failed) return ;
        match(input,MODC,FOLLOW_MODC_in_synpred29_ComplexParser652); if (state.failed) return ;
        pushFollow(FOLLOW_val_in_synpred29_ComplexParser654);
        val();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred29_ComplexParser

    // $ANTLR start synpred30_ComplexParser
    public final void synpred30_ComplexParser_fragment() throws RecognitionException {
        // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:131:3: ( name MODC val )
        // /home/hasdai/Documentos/INFOTEC/ComplexParser.g:131:3: name MODC val
        {
        pushFollow(FOLLOW_name_in_synpred30_ComplexParser668);
        name();

        state._fsp--;
        if (state.failed) return ;
        match(input,MODC,FOLLOW_MODC_in_synpred30_ComplexParser670); if (state.failed) return ;
        pushFollow(FOLLOW_val_in_synpred30_ComplexParser672);
        val();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred30_ComplexParser

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
    public final boolean synpred16_ComplexParser() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred16_ComplexParser_fragment(); // can never throw exception
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
    public final boolean synpred30_ComplexParser() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred30_ComplexParser_fragment(); // can never throw exception
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


    protected DFA8 dfa8 = new DFA8(this);
    protected DFA9 dfa9 = new DFA9(this);
    protected DFA12 dfa12 = new DFA12(this);
    static final String DFA8_eotS =
        "\13\uffff";
    static final String DFA8_eofS =
        "\1\1\1\uffff\1\4\5\uffff\1\12\2\uffff";
    static final String DFA8_minS =
        "\1\17\1\uffff\1\13\3\uffff\1\27\1\uffff\1\13\2\uffff";
    static final String DFA8_maxS =
        "\1\34\1\uffff\1\34\3\uffff\1\31\1\uffff\1\34\2\uffff";
    static final String DFA8_acceptS =
        "\1\uffff\1\1\1\uffff\1\7\1\2\1\5\1\uffff\1\6\1\uffff\1\4\1\3";
    static final String DFA8_specialS =
        "\13\uffff}>";
    static final String[] DFA8_transitionS = {
            "\2\1\6\uffff\1\2\1\uffff\1\2\1\3\2\1",
            "",
            "\1\7\2\uffff\1\6\2\4\6\uffff\1\5\1\uffff\1\5\1\uffff\2\4",
            "",
            "",
            "",
            "\1\10\1\uffff\1\10",
            "",
            "\1\11\3\uffff\2\12\12\uffff\2\12",
            "",
            ""
    };

    static final short[] DFA8_eot = DFA.unpackEncodedString(DFA8_eotS);
    static final short[] DFA8_eof = DFA.unpackEncodedString(DFA8_eofS);
    static final char[] DFA8_min = DFA.unpackEncodedStringToUnsignedChars(DFA8_minS);
    static final char[] DFA8_max = DFA.unpackEncodedStringToUnsignedChars(DFA8_maxS);
    static final short[] DFA8_accept = DFA.unpackEncodedString(DFA8_acceptS);
    static final short[] DFA8_special = DFA.unpackEncodedString(DFA8_specialS);
    static final short[][] DFA8_transition;

    static {
        int numStates = DFA8_transitionS.length;
        DFA8_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA8_transition[i] = DFA.unpackEncodedString(DFA8_transitionS[i]);
        }
    }

    class DFA8 extends DFA {

        public DFA8(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 8;
            this.eot = DFA8_eot;
            this.eof = DFA8_eof;
            this.min = DFA8_min;
            this.max = DFA8_max;
            this.accept = DFA8_accept;
            this.special = DFA8_special;
            this.transition = DFA8_transition;
        }
        public String getDescription() {
            return "83:1: oquery : ( | name | name PREN name -> ^( PREEN name name ) | name PREN name PREC querylist -> ^( PRECON ^( PREEN name name ) querylist ) | name name PREC querylist -> ^( PRECON ^( ADJASIGN name name ) querylist ) | name PREC querylist -> ^( name ^( PRECON querylist ) ) | LPAR oquery RPAR );";
        }
    }
    static final String DFA9_eotS =
        "\15\uffff";
    static final String DFA9_eofS =
        "\1\6\14\uffff";
    static final String DFA9_minS =
        "\1\17\5\0\7\uffff";
    static final String DFA9_maxS =
        "\1\34\5\0\7\uffff";
    static final String DFA9_acceptS =
        "\6\uffff\1\3\3\uffff\1\1\1\2\1\4";
    static final String DFA9_specialS =
        "\1\uffff\1\0\1\1\1\2\1\3\1\4\7\uffff}>";
    static final String[] DFA9_transitionS = {
            "\2\6\2\uffff\1\5\3\uffff\1\4\1\uffff\1\2\1\3\1\6\1\1",
            "\1\uffff",
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
            return "94:1: querylist : ( oquery DEL querylist | sent DEL querylist | oquery | sent );";
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
                        if ( (synpred14_ComplexParser()) ) {s = 10;}

                        else if ( (synpred16_ComplexParser()) ) {s = 6;}


                        input.seek(index9_1);
                        if ( s>=0 ) return s;
                        break;
                    case 1 :
                        int LA9_2 = input.LA(1);


                        int index9_2 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred14_ComplexParser()) ) {s = 10;}

                        else if ( (synpred15_ComplexParser()) ) {s = 11;}

                        else if ( (synpred16_ComplexParser()) ) {s = 6;}

                        else if ( (true) ) {s = 12;}


                        input.seek(index9_2);
                        if ( s>=0 ) return s;
                        break;
                    case 2 :
                        int LA9_3 = input.LA(1);


                        int index9_3 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred14_ComplexParser()) ) {s = 10;}

                        else if ( (synpred16_ComplexParser()) ) {s = 6;}


                        input.seek(index9_3);
                        if ( s>=0 ) return s;
                        break;
                    case 3 :
                        int LA9_4 = input.LA(1);


                        int index9_4 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred14_ComplexParser()) ) {s = 10;}

                        else if ( (synpred15_ComplexParser()) ) {s = 11;}

                        else if ( (synpred16_ComplexParser()) ) {s = 6;}

                        else if ( (true) ) {s = 12;}


                        input.seek(index9_4);
                        if ( s>=0 ) return s;
                        break;
                    case 4 :
                        int LA9_5 = input.LA(1);


                        int index9_5 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred15_ComplexParser()) ) {s = 11;}

                        else if ( (true) ) {s = 12;}


                        input.seek(index9_5);
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
    static final String DFA12_eotS =
        "\32\uffff";
    static final String DFA12_eofS =
        "\32\uffff";
    static final String DFA12_minS =
        "\1\23\2\6\1\uffff\5\22\7\uffff\5\0\5\uffff";
    static final String DFA12_maxS =
        "\1\31\2\24\1\uffff\5\24\7\uffff\5\0\5\uffff";
    static final String DFA12_acceptS =
        "\3\uffff\1\15\5\uffff\1\1\1\14\1\3\1\5\1\13\1\11\1\7\5\uffff\1\2"+
        "\1\4\1\6\1\10\1\12";
    static final String DFA12_specialS =
        "\20\uffff\1\3\1\4\1\1\1\2\1\0\5\uffff}>";
    static final String[] DFA12_transitionS = {
            "\1\3\3\uffff\1\2\1\uffff\1\1",
            "\1\4\1\5\1\11\1\6\1\7\6\uffff\1\10\3\12",
            "\1\13\1\14\1\11\1\17\1\16\6\uffff\1\15\3\12",
            "",
            "\3\20",
            "\3\21",
            "\3\22",
            "\3\23",
            "\3\24",
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

    static final short[] DFA12_eot = DFA.unpackEncodedString(DFA12_eotS);
    static final short[] DFA12_eof = DFA.unpackEncodedString(DFA12_eofS);
    static final char[] DFA12_min = DFA.unpackEncodedStringToUnsignedChars(DFA12_minS);
    static final char[] DFA12_max = DFA.unpackEncodedStringToUnsignedChars(DFA12_maxS);
    static final short[] DFA12_accept = DFA.unpackEncodedString(DFA12_acceptS);
    static final short[] DFA12_special = DFA.unpackEncodedString(DFA12_specialS);
    static final short[][] DFA12_transition;

    static {
        int numStates = DFA12_transitionS.length;
        DFA12_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA12_transition[i] = DFA.unpackEncodedString(DFA12_transitionS[i]);
        }
    }

    class DFA12 extends DFA {

        public DFA12(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 12;
            this.eot = DFA12_eot;
            this.eof = DFA12_eof;
            this.min = DFA12_min;
            this.max = DFA12_max;
            this.accept = DFA12_accept;
            this.special = DFA12_special;
            this.transition = DFA12_transition;
        }
        public String getDescription() {
            return "120:1: sent : ( name SIGE val -> ^( ASIGN name val ) | VAR SIGL val -> ^( COMPL VAR val ) | name SIGL val -> ^( COMPL name val ) | VAR SIGG val -> ^( COMPG VAR val ) | name SIGG val -> ^( COMPG name val ) | VAR SIGLE val -> ^( COMPLE VAR val ) | name SIGLE val -> ^( COMPLE name val ) | VAR SIGGE val -> ^( COMPGE VAR val ) | name SIGGE val -> ^( COMPGE name val ) | VAR MODC val -> ^( LIKE VAR val ) | name MODC val -> ^( LIKE name val ) | name val -> ^( ASIGN name val ) | NUM name -> ^( ASIGN name NUM ) );";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 :
                        int LA12_20 = input.LA(1);


                        int index12_20 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred29_ComplexParser()) ) {s = 25;}

                        else if ( (synpred30_ComplexParser()) ) {s = 13;}


                        input.seek(index12_20);
                        if ( s>=0 ) return s;
                        break;
                    case 1 :
                        int LA12_18 = input.LA(1);


                        int index12_18 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred25_ComplexParser()) ) {s = 23;}

                        else if ( (synpred26_ComplexParser()) ) {s = 15;}


                        input.seek(index12_18);
                        if ( s>=0 ) return s;
                        break;
                    case 2 :
                        int LA12_19 = input.LA(1);


                        int index12_19 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred27_ComplexParser()) ) {s = 24;}

                        else if ( (synpred28_ComplexParser()) ) {s = 14;}


                        input.seek(index12_19);
                        if ( s>=0 ) return s;
                        break;
                    case 3 :
                        int LA12_16 = input.LA(1);


                        int index12_16 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred21_ComplexParser()) ) {s = 21;}

                        else if ( (synpred22_ComplexParser()) ) {s = 11;}


                        input.seek(index12_16);
                        if ( s>=0 ) return s;
                        break;
                    case 4 :
                        int LA12_17 = input.LA(1);


                        int index12_17 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred23_ComplexParser()) ) {s = 22;}

                        else if ( (synpred24_ComplexParser()) ) {s = 12;}


                        input.seek(index12_17);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 12, _s, input);
            error(nvae);
            throw nvae;
        }
    }


    public static final BitSet FOLLOW_limiter_in_squery108 = new BitSet(new long[]{0x0000000006818000L});
    public static final BitSet FOLLOW_oquery_in_squery111 = new BitSet(new long[]{0x0000000000018000L});
    public static final BitSet FOLLOW_modifier_in_squery113 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_squery116 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_limiter_in_squery134 = new BitSet(new long[]{0x0000000002801000L});
    public static final BitSet FOLLOW_pquery_in_squery137 = new BitSet(new long[]{0x0000000000018000L});
    public static final BitSet FOLLOW_modifier_in_squery139 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_squery142 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NUM_in_limiter167 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ordterm_in_modifier186 = new BitSet(new long[]{0x0000000000018002L});
    public static final BitSet FOLLOW_offsetterm_in_modifier188 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_offsetterm_in_modifier193 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_MODE_in_offsetterm204 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_NUM_in_offsetterm206 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_MODO_in_ordterm225 = new BitSet(new long[]{0x0000000004000000L});
    public static final BitSet FOLLOW_LPAR_in_ordterm227 = new BitSet(new long[]{0x0000000002800000L});
    public static final BitSet FOLLOW_plist_in_ordterm229 = new BitSet(new long[]{0x0000000008000000L});
    public static final BitSet FOLLOW_RPAR_in_ordterm231 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_name_in_oquery253 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_name_in_oquery257 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_PREN_in_oquery259 = new BitSet(new long[]{0x0000000002800000L});
    public static final BitSet FOLLOW_name_in_oquery261 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_name_in_oquery275 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_PREN_in_oquery277 = new BitSet(new long[]{0x0000000002800000L});
    public static final BitSet FOLLOW_name_in_oquery279 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_PREC_in_oquery281 = new BitSet(new long[]{0x0000000016880000L});
    public static final BitSet FOLLOW_querylist_in_oquery283 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_name_in_oquery303 = new BitSet(new long[]{0x0000000002800000L});
    public static final BitSet FOLLOW_name_in_oquery305 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_PREC_in_oquery307 = new BitSet(new long[]{0x0000000016880000L});
    public static final BitSet FOLLOW_querylist_in_oquery309 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_name_in_oquery330 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_PREC_in_oquery332 = new BitSet(new long[]{0x0000000016880000L});
    public static final BitSet FOLLOW_querylist_in_oquery334 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LPAR_in_oquery352 = new BitSet(new long[]{0x000000000E800000L});
    public static final BitSet FOLLOW_oquery_in_oquery355 = new BitSet(new long[]{0x0000000008000000L});
    public static final BitSet FOLLOW_RPAR_in_oquery357 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_oquery_in_querylist369 = new BitSet(new long[]{0x0000000010000000L});
    public static final BitSet FOLLOW_DEL_in_querylist371 = new BitSet(new long[]{0x0000000016880000L});
    public static final BitSet FOLLOW_querylist_in_querylist374 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_sent_in_querylist378 = new BitSet(new long[]{0x0000000010000000L});
    public static final BitSet FOLLOW_DEL_in_querylist380 = new BitSet(new long[]{0x0000000016880000L});
    public static final BitSet FOLLOW_querylist_in_querylist383 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_oquery_in_querylist387 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_sent_in_querylist391 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_plist_in_pquery402 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_PRED_in_pquery404 = new BitSet(new long[]{0x0000000006800000L});
    public static final BitSet FOLLOW_oquery_in_pquery406 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_MODT_in_pquery424 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_PRED_in_pquery426 = new BitSet(new long[]{0x0000000006800000L});
    public static final BitSet FOLLOW_oquery_in_pquery428 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_name_in_plist453 = new BitSet(new long[]{0x0000000010000000L});
    public static final BitSet FOLLOW_DEL_in_plist455 = new BitSet(new long[]{0x0000000002800000L});
    public static final BitSet FOLLOW_plist_in_plist458 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_name_in_plist462 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_name0 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_name_in_sent488 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_SIGE_in_sent490 = new BitSet(new long[]{0x00000000001C0000L});
    public static final BitSet FOLLOW_val_in_sent492 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_VAR_in_sent506 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_SIGL_in_sent508 = new BitSet(new long[]{0x00000000001C0000L});
    public static final BitSet FOLLOW_val_in_sent510 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_name_in_sent524 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_SIGL_in_sent526 = new BitSet(new long[]{0x00000000001C0000L});
    public static final BitSet FOLLOW_val_in_sent528 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_VAR_in_sent542 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_SIGG_in_sent544 = new BitSet(new long[]{0x00000000001C0000L});
    public static final BitSet FOLLOW_val_in_sent546 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_name_in_sent560 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_SIGG_in_sent562 = new BitSet(new long[]{0x00000000001C0000L});
    public static final BitSet FOLLOW_val_in_sent564 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_VAR_in_sent578 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_SIGLE_in_sent580 = new BitSet(new long[]{0x00000000001C0000L});
    public static final BitSet FOLLOW_val_in_sent582 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_name_in_sent596 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_SIGLE_in_sent598 = new BitSet(new long[]{0x00000000001C0000L});
    public static final BitSet FOLLOW_val_in_sent600 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_VAR_in_sent614 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_SIGGE_in_sent616 = new BitSet(new long[]{0x00000000001C0000L});
    public static final BitSet FOLLOW_val_in_sent618 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_name_in_sent632 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_SIGGE_in_sent634 = new BitSet(new long[]{0x00000000001C0000L});
    public static final BitSet FOLLOW_val_in_sent636 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_VAR_in_sent650 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_MODC_in_sent652 = new BitSet(new long[]{0x00000000001C0000L});
    public static final BitSet FOLLOW_val_in_sent654 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_name_in_sent668 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_MODC_in_sent670 = new BitSet(new long[]{0x00000000001C0000L});
    public static final BitSet FOLLOW_val_in_sent672 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_name_in_sent686 = new BitSet(new long[]{0x00000000001C0000L});
    public static final BitSet FOLLOW_val_in_sent688 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NUM_in_sent702 = new BitSet(new long[]{0x0000000002800000L});
    public static final BitSet FOLLOW_name_in_sent704 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_val0 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_oquery_in_synpred14_ComplexParser369 = new BitSet(new long[]{0x0000000010000000L});
    public static final BitSet FOLLOW_DEL_in_synpred14_ComplexParser371 = new BitSet(new long[]{0x0000000016880000L});
    public static final BitSet FOLLOW_querylist_in_synpred14_ComplexParser374 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_sent_in_synpred15_ComplexParser378 = new BitSet(new long[]{0x0000000010000000L});
    public static final BitSet FOLLOW_DEL_in_synpred15_ComplexParser380 = new BitSet(new long[]{0x0000000016880000L});
    public static final BitSet FOLLOW_querylist_in_synpred15_ComplexParser383 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_oquery_in_synpred16_ComplexParser387 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_VAR_in_synpred21_ComplexParser506 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_SIGL_in_synpred21_ComplexParser508 = new BitSet(new long[]{0x00000000001C0000L});
    public static final BitSet FOLLOW_val_in_synpred21_ComplexParser510 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_name_in_synpred22_ComplexParser524 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_SIGL_in_synpred22_ComplexParser526 = new BitSet(new long[]{0x00000000001C0000L});
    public static final BitSet FOLLOW_val_in_synpred22_ComplexParser528 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_VAR_in_synpred23_ComplexParser542 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_SIGG_in_synpred23_ComplexParser544 = new BitSet(new long[]{0x00000000001C0000L});
    public static final BitSet FOLLOW_val_in_synpred23_ComplexParser546 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_name_in_synpred24_ComplexParser560 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_SIGG_in_synpred24_ComplexParser562 = new BitSet(new long[]{0x00000000001C0000L});
    public static final BitSet FOLLOW_val_in_synpred24_ComplexParser564 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_VAR_in_synpred25_ComplexParser578 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_SIGLE_in_synpred25_ComplexParser580 = new BitSet(new long[]{0x00000000001C0000L});
    public static final BitSet FOLLOW_val_in_synpred25_ComplexParser582 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_name_in_synpred26_ComplexParser596 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_SIGLE_in_synpred26_ComplexParser598 = new BitSet(new long[]{0x00000000001C0000L});
    public static final BitSet FOLLOW_val_in_synpred26_ComplexParser600 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_VAR_in_synpred27_ComplexParser614 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_SIGGE_in_synpred27_ComplexParser616 = new BitSet(new long[]{0x00000000001C0000L});
    public static final BitSet FOLLOW_val_in_synpred27_ComplexParser618 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_name_in_synpred28_ComplexParser632 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_SIGGE_in_synpred28_ComplexParser634 = new BitSet(new long[]{0x00000000001C0000L});
    public static final BitSet FOLLOW_val_in_synpred28_ComplexParser636 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_VAR_in_synpred29_ComplexParser650 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_MODC_in_synpred29_ComplexParser652 = new BitSet(new long[]{0x00000000001C0000L});
    public static final BitSet FOLLOW_val_in_synpred29_ComplexParser654 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_name_in_synpred30_ComplexParser668 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_MODC_in_synpred30_ComplexParser670 = new BitSet(new long[]{0x00000000001C0000L});
    public static final BitSet FOLLOW_val_in_synpred30_ComplexParser672 = new BitSet(new long[]{0x0000000000000002L});
}