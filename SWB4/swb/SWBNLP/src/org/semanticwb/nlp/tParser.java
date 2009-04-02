/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.nlp;
/**
 *
 * @author hasdai
 */
// $ANTLR 3.1.2 /home/hasdai/Documentos/tParser.g 2009-04-02 12:24:13

import org.antlr.runtime.*;
import org.antlr.runtime.tree.*;

public class tParser extends Parser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "WHITESPACE", "SIGN", "SIGL", "SIGG", "SIGE", "SIGLE", "SIGGE", "PREC", "MODT", "PRED", "MODE", "MODO", "BOL", "NUM", "LIT", "LBRK", "RBRK", "BVAR", "ORDOP", "VAR", "LPAR", "RPAR", "DEL", "SIGI", "LIMIT", "SELECT", "ASIGN", "COMPL", "COMPG", "COMPLE", "COMPGE", "PRECON", "PREDE", "OFFSET", "ORDER", "COMPNAME", "MODTO", "NAME"
    };
    public static final int COMPGE=34;
    public static final int SIGN=5;
    public static final int PRECON=35;
    public static final int BVAR=21;
    public static final int SIGI=27;
    public static final int SIGL=6;
    public static final int ORDER=38;
    public static final int SIGE=8;
    public static final int LIMIT=28;
    public static final int DEL=26;
    public static final int SIGG=7;
    public static final int PREC=11;
    public static final int LBRK=19;
    public static final int SIGGE=10;
    public static final int PRED=13;
    public static final int ORDOP=22;
    public static final int LIT=18;
    public static final int WHITESPACE=4;
    public static final int BOL=16;
    public static final int MODTO=40;
    public static final int COMPL=31;
    public static final int EOF=-1;
    public static final int COMPG=32;
    public static final int NUM=17;
    public static final int NAME=41;
    public static final int COMPNAME=39;
    public static final int SIGLE=9;
    public static final int LPAR=24;
    public static final int OFFSET=37;
    public static final int COMPLE=33;
    public static final int ASIGN=30;
    public static final int MODT=12;
    public static final int RPAR=25;
    public static final int VAR=23;
    public static final int MODO=15;
    public static final int SELECT=29;
    public static final int MODE=14;
    public static final int RBRK=20;
    public static final int PREDE=36;

    // delegates
    // delegators


        public tParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public tParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);

        }

    protected TreeAdaptor adaptor = new CommonTreeAdaptor();

    public void setTreeAdaptor(TreeAdaptor adaptor) {
        this.adaptor = adaptor;
    }
    public TreeAdaptor getTreeAdaptor() {
        return adaptor;
    }

    public String[] getTokenNames() { return tParser.tokenNames; }
    public String getGrammarFileName() { return "/home/hasdai/Documentos/tParser.g"; }


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
    // /home/hasdai/Documentos/tParser.g:50:1: squery : ( ( limiter )? oquery ( modifier )? EOF -> ^( SELECT ( limiter )? oquery ( modifier )? ) | ( limiter )? pquery ( modifier )? EOF -> ^( SELECT ( limiter )? pquery ( modifier )? ) );
    public final tParser.squery_return squery() throws RecognitionException {
        tParser.squery_return retval = new tParser.squery_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token EOF4=null;
        Token EOF8=null;
        tParser.limiter_return limiter1 = null;

        tParser.oquery_return oquery2 = null;

        tParser.modifier_return modifier3 = null;

        tParser.limiter_return limiter5 = null;

        tParser.pquery_return pquery6 = null;

        tParser.modifier_return modifier7 = null;


        Object EOF4_tree=null;
        Object EOF8_tree=null;
        RewriteRuleTokenStream stream_EOF=new RewriteRuleTokenStream(adaptor,"token EOF");
        RewriteRuleSubtreeStream stream_limiter=new RewriteRuleSubtreeStream(adaptor,"rule limiter");
        RewriteRuleSubtreeStream stream_modifier=new RewriteRuleSubtreeStream(adaptor,"rule modifier");
        RewriteRuleSubtreeStream stream_pquery=new RewriteRuleSubtreeStream(adaptor,"rule pquery");
        RewriteRuleSubtreeStream stream_oquery=new RewriteRuleSubtreeStream(adaptor,"rule oquery");
        try {
            // /home/hasdai/Documentos/tParser.g:51:1: ( ( limiter )? oquery ( modifier )? EOF -> ^( SELECT ( limiter )? oquery ( modifier )? ) | ( limiter )? pquery ( modifier )? EOF -> ^( SELECT ( limiter )? pquery ( modifier )? ) )
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
                    else if ( (LA5_3==EOF||LA5_3==PREC||(LA5_3>=MODE && LA5_3<=MODO)) ) {
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
                else if ( (LA5_3==EOF||LA5_3==PREC||(LA5_3>=MODE && LA5_3<=MODO)) ) {
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
                    // /home/hasdai/Documentos/tParser.g:51:3: ( limiter )? oquery ( modifier )? EOF
                    {
                    // /home/hasdai/Documentos/tParser.g:51:3: ( limiter )?
                    int alt1=2;
                    int LA1_0 = input.LA(1);

                    if ( (LA1_0==NUM) ) {
                        alt1=1;
                    }
                    switch (alt1) {
                        case 1 :
                            // /home/hasdai/Documentos/tParser.g:0:0: limiter
                            {
                            pushFollow(FOLLOW_limiter_in_squery98);
                            limiter1=limiter();

                            state._fsp--;
                            if (state.failed) return retval;
                            if ( state.backtracking==0 ) stream_limiter.add(limiter1.getTree());

                            }
                            break;

                    }

                    pushFollow(FOLLOW_oquery_in_squery101);
                    oquery2=oquery();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_oquery.add(oquery2.getTree());
                    // /home/hasdai/Documentos/tParser.g:51:19: ( modifier )?
                    int alt2=2;
                    int LA2_0 = input.LA(1);

                    if ( ((LA2_0>=MODE && LA2_0<=MODO)) ) {
                        alt2=1;
                    }
                    switch (alt2) {
                        case 1 :
                            // /home/hasdai/Documentos/tParser.g:0:0: modifier
                            {
                            pushFollow(FOLLOW_modifier_in_squery103);
                            modifier3=modifier();

                            state._fsp--;
                            if (state.failed) return retval;
                            if ( state.backtracking==0 ) stream_modifier.add(modifier3.getTree());

                            }
                            break;

                    }

                    EOF4=(Token)match(input,EOF,FOLLOW_EOF_in_squery106); if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_EOF.add(EOF4);



                    // AST REWRITE
                    // elements: modifier, oquery, limiter
                    // token labels:
                    // rule labels: retval
                    // token list labels:
                    // rule list labels:
                    // wildcard labels:
                    if ( state.backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 51:33: -> ^( SELECT ( limiter )? oquery ( modifier )? )
                    {
                        // /home/hasdai/Documentos/tParser.g:51:36: ^( SELECT ( limiter )? oquery ( modifier )? )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(SELECT, "SELECT"), root_1);

                        // /home/hasdai/Documentos/tParser.g:51:45: ( limiter )?
                        if ( stream_limiter.hasNext() ) {
                            adaptor.addChild(root_1, stream_limiter.nextTree());

                        }
                        stream_limiter.reset();
                        adaptor.addChild(root_1, stream_oquery.nextTree());
                        // /home/hasdai/Documentos/tParser.g:51:61: ( modifier )?
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
                    // /home/hasdai/Documentos/tParser.g:52:3: ( limiter )? pquery ( modifier )? EOF
                    {
                    // /home/hasdai/Documentos/tParser.g:52:3: ( limiter )?
                    int alt3=2;
                    int LA3_0 = input.LA(1);

                    if ( (LA3_0==NUM) ) {
                        alt3=1;
                    }
                    switch (alt3) {
                        case 1 :
                            // /home/hasdai/Documentos/tParser.g:0:0: limiter
                            {
                            pushFollow(FOLLOW_limiter_in_squery124);
                            limiter5=limiter();

                            state._fsp--;
                            if (state.failed) return retval;
                            if ( state.backtracking==0 ) stream_limiter.add(limiter5.getTree());

                            }
                            break;

                    }

                    pushFollow(FOLLOW_pquery_in_squery127);
                    pquery6=pquery();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_pquery.add(pquery6.getTree());
                    // /home/hasdai/Documentos/tParser.g:52:19: ( modifier )?
                    int alt4=2;
                    int LA4_0 = input.LA(1);

                    if ( ((LA4_0>=MODE && LA4_0<=MODO)) ) {
                        alt4=1;
                    }
                    switch (alt4) {
                        case 1 :
                            // /home/hasdai/Documentos/tParser.g:0:0: modifier
                            {
                            pushFollow(FOLLOW_modifier_in_squery129);
                            modifier7=modifier();

                            state._fsp--;
                            if (state.failed) return retval;
                            if ( state.backtracking==0 ) stream_modifier.add(modifier7.getTree());

                            }
                            break;

                    }

                    EOF8=(Token)match(input,EOF,FOLLOW_EOF_in_squery132); if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_EOF.add(EOF8);



                    // AST REWRITE
                    // elements: limiter, pquery, modifier
                    // token labels:
                    // rule labels: retval
                    // token list labels:
                    // rule list labels:
                    // wildcard labels:
                    if ( state.backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 52:33: -> ^( SELECT ( limiter )? pquery ( modifier )? )
                    {
                        // /home/hasdai/Documentos/tParser.g:52:36: ^( SELECT ( limiter )? pquery ( modifier )? )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(SELECT, "SELECT"), root_1);

                        // /home/hasdai/Documentos/tParser.g:52:45: ( limiter )?
                        if ( stream_limiter.hasNext() ) {
                            adaptor.addChild(root_1, stream_limiter.nextTree());

                        }
                        stream_limiter.reset();
                        adaptor.addChild(root_1, stream_pquery.nextTree());
                        // /home/hasdai/Documentos/tParser.g:52:61: ( modifier )?
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
    // /home/hasdai/Documentos/tParser.g:56:1: limiter : NUM -> ^( LIMIT NUM ) ;
    public final tParser.limiter_return limiter() throws RecognitionException {
        tParser.limiter_return retval = new tParser.limiter_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token NUM9=null;

        Object NUM9_tree=null;
        RewriteRuleTokenStream stream_NUM=new RewriteRuleTokenStream(adaptor,"token NUM");

        try {
            // /home/hasdai/Documentos/tParser.g:57:1: ( NUM -> ^( LIMIT NUM ) )
            // /home/hasdai/Documentos/tParser.g:57:3: NUM
            {
            NUM9=(Token)match(input,NUM,FOLLOW_NUM_in_limiter157); if (state.failed) return retval;
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
            // 57:7: -> ^( LIMIT NUM )
            {
                // /home/hasdai/Documentos/tParser.g:57:10: ^( LIMIT NUM )
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
    // /home/hasdai/Documentos/tParser.g:63:1: modifier : ( ordterm ( offsetterm )? | offsetterm );
    public final tParser.modifier_return modifier() throws RecognitionException {
        tParser.modifier_return retval = new tParser.modifier_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        tParser.ordterm_return ordterm10 = null;

        tParser.offsetterm_return offsetterm11 = null;

        tParser.offsetterm_return offsetterm12 = null;



        try {
            // /home/hasdai/Documentos/tParser.g:64:1: ( ordterm ( offsetterm )? | offsetterm )
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
                    // /home/hasdai/Documentos/tParser.g:64:3: ordterm ( offsetterm )?
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_ordterm_in_modifier176);
                    ordterm10=ordterm();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, ordterm10.getTree());
                    // /home/hasdai/Documentos/tParser.g:64:11: ( offsetterm )?
                    int alt6=2;
                    int LA6_0 = input.LA(1);

                    if ( (LA6_0==MODE) ) {
                        alt6=1;
                    }
                    switch (alt6) {
                        case 1 :
                            // /home/hasdai/Documentos/tParser.g:0:0: offsetterm
                            {
                            pushFollow(FOLLOW_offsetterm_in_modifier178);
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
                    // /home/hasdai/Documentos/tParser.g:65:3: offsetterm
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_offsetterm_in_modifier183);
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
    // /home/hasdai/Documentos/tParser.g:69:1: offsetterm : MODE NUM -> ^( OFFSET NUM ) ;
    public final tParser.offsetterm_return offsetterm() throws RecognitionException {
        tParser.offsetterm_return retval = new tParser.offsetterm_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token MODE13=null;
        Token NUM14=null;

        Object MODE13_tree=null;
        Object NUM14_tree=null;
        RewriteRuleTokenStream stream_MODE=new RewriteRuleTokenStream(adaptor,"token MODE");
        RewriteRuleTokenStream stream_NUM=new RewriteRuleTokenStream(adaptor,"token NUM");

        try {
            // /home/hasdai/Documentos/tParser.g:70:1: ( MODE NUM -> ^( OFFSET NUM ) )
            // /home/hasdai/Documentos/tParser.g:70:3: MODE NUM
            {
            MODE13=(Token)match(input,MODE,FOLLOW_MODE_in_offsetterm194); if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_MODE.add(MODE13);

            NUM14=(Token)match(input,NUM,FOLLOW_NUM_in_offsetterm196); if (state.failed) return retval;
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
            // 70:12: -> ^( OFFSET NUM )
            {
                // /home/hasdai/Documentos/tParser.g:70:15: ^( OFFSET NUM )
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
    // /home/hasdai/Documentos/tParser.g:74:1: ordterm : MODO LPAR plist RPAR -> ^( ORDER plist ) ;
    public final tParser.ordterm_return ordterm() throws RecognitionException {
        tParser.ordterm_return retval = new tParser.ordterm_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token MODO15=null;
        Token LPAR16=null;
        Token RPAR18=null;
        tParser.plist_return plist17 = null;


        Object MODO15_tree=null;
        Object LPAR16_tree=null;
        Object RPAR18_tree=null;
        RewriteRuleTokenStream stream_RPAR=new RewriteRuleTokenStream(adaptor,"token RPAR");
        RewriteRuleTokenStream stream_MODO=new RewriteRuleTokenStream(adaptor,"token MODO");
        RewriteRuleTokenStream stream_LPAR=new RewriteRuleTokenStream(adaptor,"token LPAR");
        RewriteRuleSubtreeStream stream_plist=new RewriteRuleSubtreeStream(adaptor,"rule plist");
        try {
            // /home/hasdai/Documentos/tParser.g:75:1: ( MODO LPAR plist RPAR -> ^( ORDER plist ) )
            // /home/hasdai/Documentos/tParser.g:75:3: MODO LPAR plist RPAR
            {
            MODO15=(Token)match(input,MODO,FOLLOW_MODO_in_ordterm215); if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_MODO.add(MODO15);

            LPAR16=(Token)match(input,LPAR,FOLLOW_LPAR_in_ordterm217); if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_LPAR.add(LPAR16);

            pushFollow(FOLLOW_plist_in_ordterm219);
            plist17=plist();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_plist.add(plist17.getTree());
            RPAR18=(Token)match(input,RPAR,FOLLOW_RPAR_in_ordterm221); if (state.failed) return retval;
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
            // 75:24: -> ^( ORDER plist )
            {
                // /home/hasdai/Documentos/tParser.g:75:27: ^( ORDER plist )
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
    // /home/hasdai/Documentos/tParser.g:82:1: oquery : ( | name | name PREC querylist -> ^( name ^( PRECON querylist ) ) | LPAR oquery RPAR );
    public final tParser.oquery_return oquery() throws RecognitionException {
        tParser.oquery_return retval = new tParser.oquery_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token PREC21=null;
        Token LPAR23=null;
        Token RPAR25=null;
        tParser.name_return name19 = null;

        tParser.name_return name20 = null;

        tParser.querylist_return querylist22 = null;

        tParser.oquery_return oquery24 = null;


        Object PREC21_tree=null;
        Object LPAR23_tree=null;
        Object RPAR25_tree=null;
        RewriteRuleTokenStream stream_PREC=new RewriteRuleTokenStream(adaptor,"token PREC");
        RewriteRuleSubtreeStream stream_name=new RewriteRuleSubtreeStream(adaptor,"rule name");
        RewriteRuleSubtreeStream stream_querylist=new RewriteRuleSubtreeStream(adaptor,"rule querylist");
        try {
            // /home/hasdai/Documentos/tParser.g:83:1: ( | name | name PREC querylist -> ^( name ^( PRECON querylist ) ) | LPAR oquery RPAR )
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

                if ( (LA8_2==EOF||(LA8_2>=MODE && LA8_2<=MODO)||(LA8_2>=RPAR && LA8_2<=DEL)) ) {
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
                    // /home/hasdai/Documentos/tParser.g:84:2:
                    {
                    root_0 = (Object)adaptor.nil();

                    }
                    break;
                case 2 :
                    // /home/hasdai/Documentos/tParser.g:84:3: name
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_name_in_oquery243);
                    name19=name();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, name19.getTree());

                    }
                    break;
                case 3 :
                    // /home/hasdai/Documentos/tParser.g:85:3: name PREC querylist
                    {
                    pushFollow(FOLLOW_name_in_oquery247);
                    name20=name();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_name.add(name20.getTree());
                    PREC21=(Token)match(input,PREC,FOLLOW_PREC_in_oquery249); if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_PREC.add(PREC21);

                    pushFollow(FOLLOW_querylist_in_oquery251);
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
                    // 85:40: -> ^( name ^( PRECON querylist ) )
                    {
                        // /home/hasdai/Documentos/tParser.g:85:43: ^( name ^( PRECON querylist ) )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot(stream_name.nextNode(), root_1);

                        // /home/hasdai/Documentos/tParser.g:85:50: ^( PRECON querylist )
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
                    // /home/hasdai/Documentos/tParser.g:86:3: LPAR oquery RPAR
                    {
                    root_0 = (Object)adaptor.nil();

                    LPAR23=(Token)match(input,LPAR,FOLLOW_LPAR_in_oquery269); if (state.failed) return retval;
                    pushFollow(FOLLOW_oquery_in_oquery272);
                    oquery24=oquery();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, oquery24.getTree());
                    RPAR25=(Token)match(input,RPAR,FOLLOW_RPAR_in_oquery274); if (state.failed) return retval;

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
    // /home/hasdai/Documentos/tParser.g:90:1: querylist : ( oquery DEL querylist | sent DEL querylist | oquery | sent );
    public final tParser.querylist_return querylist() throws RecognitionException {
        tParser.querylist_return retval = new tParser.querylist_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token DEL27=null;
        Token DEL30=null;
        tParser.oquery_return oquery26 = null;

        tParser.querylist_return querylist28 = null;

        tParser.sent_return sent29 = null;

        tParser.querylist_return querylist31 = null;

        tParser.oquery_return oquery32 = null;

        tParser.sent_return sent33 = null;


        Object DEL27_tree=null;
        Object DEL30_tree=null;

        try {
            // /home/hasdai/Documentos/tParser.g:91:1: ( oquery DEL querylist | sent DEL querylist | oquery | sent )
            int alt9=4;
            alt9 = dfa9.predict(input);
            switch (alt9) {
                case 1 :
                    // /home/hasdai/Documentos/tParser.g:91:3: oquery DEL querylist
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_oquery_in_querylist286);
                    oquery26=oquery();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, oquery26.getTree());
                    DEL27=(Token)match(input,DEL,FOLLOW_DEL_in_querylist288); if (state.failed) return retval;
                    pushFollow(FOLLOW_querylist_in_querylist291);
                    querylist28=querylist();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, querylist28.getTree());

                    }
                    break;
                case 2 :
                    // /home/hasdai/Documentos/tParser.g:92:3: sent DEL querylist
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_sent_in_querylist295);
                    sent29=sent();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, sent29.getTree());
                    DEL30=(Token)match(input,DEL,FOLLOW_DEL_in_querylist297); if (state.failed) return retval;
                    pushFollow(FOLLOW_querylist_in_querylist300);
                    querylist31=querylist();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, querylist31.getTree());

                    }
                    break;
                case 3 :
                    // /home/hasdai/Documentos/tParser.g:93:3: oquery
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_oquery_in_querylist304);
                    oquery32=oquery();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, oquery32.getTree());

                    }
                    break;
                case 4 :
                    // /home/hasdai/Documentos/tParser.g:94:3: sent
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_sent_in_querylist308);
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
    // /home/hasdai/Documentos/tParser.g:98:1: pquery : ( plist PRED oquery -> ^( oquery ^( PREDE plist ) ) | MODT PRED oquery -> ^( oquery ^( PREDE MODTO ) ) );
    public final tParser.pquery_return pquery() throws RecognitionException {
        tParser.pquery_return retval = new tParser.pquery_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token PRED35=null;
        Token MODT37=null;
        Token PRED38=null;
        tParser.plist_return plist34 = null;

        tParser.oquery_return oquery36 = null;

        tParser.oquery_return oquery39 = null;


        Object PRED35_tree=null;
        Object MODT37_tree=null;
        Object PRED38_tree=null;
        RewriteRuleTokenStream stream_MODT=new RewriteRuleTokenStream(adaptor,"token MODT");
        RewriteRuleTokenStream stream_PRED=new RewriteRuleTokenStream(adaptor,"token PRED");
        RewriteRuleSubtreeStream stream_plist=new RewriteRuleSubtreeStream(adaptor,"rule plist");
        RewriteRuleSubtreeStream stream_oquery=new RewriteRuleSubtreeStream(adaptor,"rule oquery");
        try {
            // /home/hasdai/Documentos/tParser.g:99:1: ( plist PRED oquery -> ^( oquery ^( PREDE plist ) ) | MODT PRED oquery -> ^( oquery ^( PREDE MODTO ) ) )
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
                    // /home/hasdai/Documentos/tParser.g:99:3: plist PRED oquery
                    {
                    pushFollow(FOLLOW_plist_in_pquery319);
                    plist34=plist();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_plist.add(plist34.getTree());
                    PRED35=(Token)match(input,PRED,FOLLOW_PRED_in_pquery321); if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_PRED.add(PRED35);

                    pushFollow(FOLLOW_oquery_in_pquery323);
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
                    // 99:37: -> ^( oquery ^( PREDE plist ) )
                    {
                        // /home/hasdai/Documentos/tParser.g:99:40: ^( oquery ^( PREDE plist ) )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot(stream_oquery.nextNode(), root_1);

                        // /home/hasdai/Documentos/tParser.g:99:49: ^( PREDE plist )
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
                    // /home/hasdai/Documentos/tParser.g:100:3: MODT PRED oquery
                    {
                    MODT37=(Token)match(input,MODT,FOLLOW_MODT_in_pquery341); if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_MODT.add(MODT37);

                    PRED38=(Token)match(input,PRED,FOLLOW_PRED_in_pquery343); if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_PRED.add(PRED38);

                    pushFollow(FOLLOW_oquery_in_pquery345);
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
                    // 100:36: -> ^( oquery ^( PREDE MODTO ) )
                    {
                        // /home/hasdai/Documentos/tParser.g:100:39: ^( oquery ^( PREDE MODTO ) )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot(stream_oquery.nextNode(), root_1);

                        // /home/hasdai/Documentos/tParser.g:100:48: ^( PREDE MODTO )
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
    // /home/hasdai/Documentos/tParser.g:104:1: plist : ( name DEL plist | name );
    public final tParser.plist_return plist() throws RecognitionException {
        tParser.plist_return retval = new tParser.plist_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token DEL41=null;
        tParser.name_return name40 = null;

        tParser.plist_return plist42 = null;

        tParser.name_return name43 = null;


        Object DEL41_tree=null;

        try {
            // /home/hasdai/Documentos/tParser.g:105:1: ( name DEL plist | name )
            int alt11=2;
            int LA11_0 = input.LA(1);

            if ( (LA11_0==BVAR||LA11_0==VAR) ) {
                int LA11_1 = input.LA(2);

                if ( (LA11_1==EOF||LA11_1==PRED||LA11_1==RPAR) ) {
                    alt11=2;
                }
                else if ( (LA11_1==DEL) ) {
                    alt11=1;
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
                    // /home/hasdai/Documentos/tParser.g:105:3: name DEL plist
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_name_in_plist370);
                    name40=name();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, name40.getTree());
                    DEL41=(Token)match(input,DEL,FOLLOW_DEL_in_plist372); if (state.failed) return retval;
                    pushFollow(FOLLOW_plist_in_plist375);
                    plist42=plist();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, plist42.getTree());

                    }
                    break;
                case 2 :
                    // /home/hasdai/Documentos/tParser.g:106:3: name
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_name_in_plist379);
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
    // /home/hasdai/Documentos/tParser.g:110:1: name : ( BVAR | VAR );
    public final tParser.name_return name() throws RecognitionException {
        tParser.name_return retval = new tParser.name_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token set44=null;

        Object set44_tree=null;

        try {
            // /home/hasdai/Documentos/tParser.g:111:1: ( BVAR | VAR )
            // /home/hasdai/Documentos/tParser.g:
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
    // /home/hasdai/Documentos/tParser.g:116:1: sent : ( name SIGE val -> ^( ASIGN name val ) | VAR SIGL val -> ^( COMPL VAR val ) | name SIGL val -> ^( COMPL name val ) | VAR SIGG val -> ^( COMPG VAR val ) | name SIGG val -> ^( COMPG name val ) | VAR SIGLE val -> ^( COMPLE VAR val ) | name SIGLE val -> ^( COMPLE name val ) | VAR SIGGE val -> ^( COMPGE VAR val ) | name SIGGE val -> ^( COMPGE name val ) );
    public final tParser.sent_return sent() throws RecognitionException {
        tParser.sent_return retval = new tParser.sent_return();
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
        tParser.name_return name45 = null;

        tParser.val_return val47 = null;

        tParser.val_return val50 = null;

        tParser.name_return name51 = null;

        tParser.val_return val53 = null;

        tParser.val_return val56 = null;

        tParser.name_return name57 = null;

        tParser.val_return val59 = null;

        tParser.val_return val62 = null;

        tParser.name_return name63 = null;

        tParser.val_return val65 = null;

        tParser.val_return val68 = null;

        tParser.name_return name69 = null;

        tParser.val_return val71 = null;


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
        RewriteRuleTokenStream stream_SIGLE=new RewriteRuleTokenStream(adaptor,"token SIGLE");
        RewriteRuleTokenStream stream_VAR=new RewriteRuleTokenStream(adaptor,"token VAR");
        RewriteRuleTokenStream stream_SIGL=new RewriteRuleTokenStream(adaptor,"token SIGL");
        RewriteRuleTokenStream stream_SIGE=new RewriteRuleTokenStream(adaptor,"token SIGE");
        RewriteRuleTokenStream stream_SIGG=new RewriteRuleTokenStream(adaptor,"token SIGG");
        RewriteRuleTokenStream stream_SIGGE=new RewriteRuleTokenStream(adaptor,"token SIGGE");
        RewriteRuleSubtreeStream stream_val=new RewriteRuleSubtreeStream(adaptor,"rule val");
        RewriteRuleSubtreeStream stream_name=new RewriteRuleSubtreeStream(adaptor,"rule name");
        try {
            // /home/hasdai/Documentos/tParser.g:117:1: ( name SIGE val -> ^( ASIGN name val ) | VAR SIGL val -> ^( COMPL VAR val ) | name SIGL val -> ^( COMPL name val ) | VAR SIGG val -> ^( COMPG VAR val ) | name SIGG val -> ^( COMPG name val ) | VAR SIGLE val -> ^( COMPLE VAR val ) | name SIGLE val -> ^( COMPLE name val ) | VAR SIGGE val -> ^( COMPGE VAR val ) | name SIGGE val -> ^( COMPGE name val ) )
            int alt12=9;
            alt12 = dfa12.predict(input);
            switch (alt12) {
                case 1 :
                    // /home/hasdai/Documentos/tParser.g:117:3: name SIGE val
                    {
                    pushFollow(FOLLOW_name_in_sent405);
                    name45=name();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_name.add(name45.getTree());
                    SIGE46=(Token)match(input,SIGE,FOLLOW_SIGE_in_sent407); if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_SIGE.add(SIGE46);

                    pushFollow(FOLLOW_val_in_sent409);
                    val47=val();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_val.add(val47.getTree());


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
                    // 117:17: -> ^( ASIGN name val )
                    {
                        // /home/hasdai/Documentos/tParser.g:117:20: ^( ASIGN name val )
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
                    // /home/hasdai/Documentos/tParser.g:118:3: VAR SIGL val
                    {
                    VAR48=(Token)match(input,VAR,FOLLOW_VAR_in_sent423); if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_VAR.add(VAR48);

                    SIGL49=(Token)match(input,SIGL,FOLLOW_SIGL_in_sent425); if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_SIGL.add(SIGL49);

                    pushFollow(FOLLOW_val_in_sent427);
                    val50=val();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_val.add(val50.getTree());


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
                    // 118:16: -> ^( COMPL VAR val )
                    {
                        // /home/hasdai/Documentos/tParser.g:118:19: ^( COMPL VAR val )
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
                    // /home/hasdai/Documentos/tParser.g:119:3: name SIGL val
                    {
                    pushFollow(FOLLOW_name_in_sent441);
                    name51=name();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_name.add(name51.getTree());
                    SIGL52=(Token)match(input,SIGL,FOLLOW_SIGL_in_sent443); if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_SIGL.add(SIGL52);

                    pushFollow(FOLLOW_val_in_sent445);
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
                    // 119:17: -> ^( COMPL name val )
                    {
                        // /home/hasdai/Documentos/tParser.g:119:20: ^( COMPL name val )
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
                    // /home/hasdai/Documentos/tParser.g:120:3: VAR SIGG val
                    {
                    VAR54=(Token)match(input,VAR,FOLLOW_VAR_in_sent459); if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_VAR.add(VAR54);

                    SIGG55=(Token)match(input,SIGG,FOLLOW_SIGG_in_sent461); if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_SIGG.add(SIGG55);

                    pushFollow(FOLLOW_val_in_sent463);
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
                    // 120:16: -> ^( COMPG VAR val )
                    {
                        // /home/hasdai/Documentos/tParser.g:120:19: ^( COMPG VAR val )
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
                    // /home/hasdai/Documentos/tParser.g:121:3: name SIGG val
                    {
                    pushFollow(FOLLOW_name_in_sent477);
                    name57=name();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_name.add(name57.getTree());
                    SIGG58=(Token)match(input,SIGG,FOLLOW_SIGG_in_sent479); if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_SIGG.add(SIGG58);

                    pushFollow(FOLLOW_val_in_sent481);
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
                    // 121:17: -> ^( COMPG name val )
                    {
                        // /home/hasdai/Documentos/tParser.g:121:20: ^( COMPG name val )
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
                    // /home/hasdai/Documentos/tParser.g:122:3: VAR SIGLE val
                    {
                    VAR60=(Token)match(input,VAR,FOLLOW_VAR_in_sent495); if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_VAR.add(VAR60);

                    SIGLE61=(Token)match(input,SIGLE,FOLLOW_SIGLE_in_sent497); if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_SIGLE.add(SIGLE61);

                    pushFollow(FOLLOW_val_in_sent499);
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
                    // 122:17: -> ^( COMPLE VAR val )
                    {
                        // /home/hasdai/Documentos/tParser.g:122:20: ^( COMPLE VAR val )
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
                    // /home/hasdai/Documentos/tParser.g:123:3: name SIGLE val
                    {
                    pushFollow(FOLLOW_name_in_sent513);
                    name63=name();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_name.add(name63.getTree());
                    SIGLE64=(Token)match(input,SIGLE,FOLLOW_SIGLE_in_sent515); if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_SIGLE.add(SIGLE64);

                    pushFollow(FOLLOW_val_in_sent517);
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
                    // 123:18: -> ^( COMPLE name val )
                    {
                        // /home/hasdai/Documentos/tParser.g:123:21: ^( COMPLE name val )
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
                    // /home/hasdai/Documentos/tParser.g:124:3: VAR SIGGE val
                    {
                    VAR66=(Token)match(input,VAR,FOLLOW_VAR_in_sent531); if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_VAR.add(VAR66);

                    SIGGE67=(Token)match(input,SIGGE,FOLLOW_SIGGE_in_sent533); if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_SIGGE.add(SIGGE67);

                    pushFollow(FOLLOW_val_in_sent535);
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
                    // 124:17: -> ^( COMPGE VAR val )
                    {
                        // /home/hasdai/Documentos/tParser.g:124:20: ^( COMPGE VAR val )
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
                    // /home/hasdai/Documentos/tParser.g:125:3: name SIGGE val
                    {
                    pushFollow(FOLLOW_name_in_sent549);
                    name69=name();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_name.add(name69.getTree());
                    SIGGE70=(Token)match(input,SIGGE,FOLLOW_SIGGE_in_sent551); if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_SIGGE.add(SIGGE70);

                    pushFollow(FOLLOW_val_in_sent553);
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
                    // 125:18: -> ^( COMPGE name val )
                    {
                        // /home/hasdai/Documentos/tParser.g:125:21: ^( COMPGE name val )
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
    // /home/hasdai/Documentos/tParser.g:129:1: val : ( LIT | BOL | NUM );
    public final tParser.val_return val() throws RecognitionException {
        tParser.val_return retval = new tParser.val_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token set72=null;

        Object set72_tree=null;

        try {
            // /home/hasdai/Documentos/tParser.g:130:1: ( LIT | BOL | NUM )
            // /home/hasdai/Documentos/tParser.g:
            {
            root_0 = (Object)adaptor.nil();

            set72=(Token)input.LT(1);
            if ( (input.LA(1)>=BOL && input.LA(1)<=LIT) ) {
                input.consume();
                if ( state.backtracking==0 ) adaptor.addChild(root_0, (Object)adaptor.create(set72));
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

    // $ANTLR start synpred11_tParser
    public final void synpred11_tParser_fragment() throws RecognitionException {
        // /home/hasdai/Documentos/tParser.g:91:3: ( oquery DEL querylist )
        // /home/hasdai/Documentos/tParser.g:91:3: oquery DEL querylist
        {
        pushFollow(FOLLOW_oquery_in_synpred11_tParser286);
        oquery();

        state._fsp--;
        if (state.failed) return ;
        match(input,DEL,FOLLOW_DEL_in_synpred11_tParser288); if (state.failed) return ;
        pushFollow(FOLLOW_querylist_in_synpred11_tParser291);
        querylist();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred11_tParser

    // $ANTLR start synpred12_tParser
    public final void synpred12_tParser_fragment() throws RecognitionException {
        // /home/hasdai/Documentos/tParser.g:92:3: ( sent DEL querylist )
        // /home/hasdai/Documentos/tParser.g:92:3: sent DEL querylist
        {
        pushFollow(FOLLOW_sent_in_synpred12_tParser295);
        sent();

        state._fsp--;
        if (state.failed) return ;
        match(input,DEL,FOLLOW_DEL_in_synpred12_tParser297); if (state.failed) return ;
        pushFollow(FOLLOW_querylist_in_synpred12_tParser300);
        querylist();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred12_tParser

    // $ANTLR start synpred13_tParser
    public final void synpred13_tParser_fragment() throws RecognitionException {
        // /home/hasdai/Documentos/tParser.g:93:3: ( oquery )
        // /home/hasdai/Documentos/tParser.g:93:3: oquery
        {
        pushFollow(FOLLOW_oquery_in_synpred13_tParser304);
        oquery();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred13_tParser

    // $ANTLR start synpred18_tParser
    public final void synpred18_tParser_fragment() throws RecognitionException {
        // /home/hasdai/Documentos/tParser.g:118:3: ( VAR SIGL val )
        // /home/hasdai/Documentos/tParser.g:118:3: VAR SIGL val
        {
        match(input,VAR,FOLLOW_VAR_in_synpred18_tParser423); if (state.failed) return ;
        match(input,SIGL,FOLLOW_SIGL_in_synpred18_tParser425); if (state.failed) return ;
        pushFollow(FOLLOW_val_in_synpred18_tParser427);
        val();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred18_tParser

    // $ANTLR start synpred19_tParser
    public final void synpred19_tParser_fragment() throws RecognitionException {
        // /home/hasdai/Documentos/tParser.g:119:3: ( name SIGL val )
        // /home/hasdai/Documentos/tParser.g:119:3: name SIGL val
        {
        pushFollow(FOLLOW_name_in_synpred19_tParser441);
        name();

        state._fsp--;
        if (state.failed) return ;
        match(input,SIGL,FOLLOW_SIGL_in_synpred19_tParser443); if (state.failed) return ;
        pushFollow(FOLLOW_val_in_synpred19_tParser445);
        val();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred19_tParser

    // $ANTLR start synpred20_tParser
    public final void synpred20_tParser_fragment() throws RecognitionException {
        // /home/hasdai/Documentos/tParser.g:120:3: ( VAR SIGG val )
        // /home/hasdai/Documentos/tParser.g:120:3: VAR SIGG val
        {
        match(input,VAR,FOLLOW_VAR_in_synpred20_tParser459); if (state.failed) return ;
        match(input,SIGG,FOLLOW_SIGG_in_synpred20_tParser461); if (state.failed) return ;
        pushFollow(FOLLOW_val_in_synpred20_tParser463);
        val();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred20_tParser

    // $ANTLR start synpred21_tParser
    public final void synpred21_tParser_fragment() throws RecognitionException {
        // /home/hasdai/Documentos/tParser.g:121:3: ( name SIGG val )
        // /home/hasdai/Documentos/tParser.g:121:3: name SIGG val
        {
        pushFollow(FOLLOW_name_in_synpred21_tParser477);
        name();

        state._fsp--;
        if (state.failed) return ;
        match(input,SIGG,FOLLOW_SIGG_in_synpred21_tParser479); if (state.failed) return ;
        pushFollow(FOLLOW_val_in_synpred21_tParser481);
        val();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred21_tParser

    // $ANTLR start synpred22_tParser
    public final void synpred22_tParser_fragment() throws RecognitionException {
        // /home/hasdai/Documentos/tParser.g:122:3: ( VAR SIGLE val )
        // /home/hasdai/Documentos/tParser.g:122:3: VAR SIGLE val
        {
        match(input,VAR,FOLLOW_VAR_in_synpred22_tParser495); if (state.failed) return ;
        match(input,SIGLE,FOLLOW_SIGLE_in_synpred22_tParser497); if (state.failed) return ;
        pushFollow(FOLLOW_val_in_synpred22_tParser499);
        val();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred22_tParser

    // $ANTLR start synpred23_tParser
    public final void synpred23_tParser_fragment() throws RecognitionException {
        // /home/hasdai/Documentos/tParser.g:123:3: ( name SIGLE val )
        // /home/hasdai/Documentos/tParser.g:123:3: name SIGLE val
        {
        pushFollow(FOLLOW_name_in_synpred23_tParser513);
        name();

        state._fsp--;
        if (state.failed) return ;
        match(input,SIGLE,FOLLOW_SIGLE_in_synpred23_tParser515); if (state.failed) return ;
        pushFollow(FOLLOW_val_in_synpred23_tParser517);
        val();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred23_tParser

    // $ANTLR start synpred24_tParser
    public final void synpred24_tParser_fragment() throws RecognitionException {
        // /home/hasdai/Documentos/tParser.g:124:3: ( VAR SIGGE val )
        // /home/hasdai/Documentos/tParser.g:124:3: VAR SIGGE val
        {
        match(input,VAR,FOLLOW_VAR_in_synpred24_tParser531); if (state.failed) return ;
        match(input,SIGGE,FOLLOW_SIGGE_in_synpred24_tParser533); if (state.failed) return ;
        pushFollow(FOLLOW_val_in_synpred24_tParser535);
        val();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred24_tParser

    // Delegated rules

    public final boolean synpred13_tParser() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred13_tParser_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred24_tParser() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred24_tParser_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred20_tParser() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred20_tParser_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred19_tParser() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred19_tParser_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred22_tParser() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred22_tParser_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred21_tParser() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred21_tParser_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred12_tParser() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred12_tParser_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred11_tParser() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred11_tParser_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred23_tParser() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred23_tParser_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred18_tParser() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred18_tParser_fragment(); // can never throw exception
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
    protected DFA12 dfa12 = new DFA12(this);
    static final String DFA9_eotS =
        "\14\uffff";
    static final String DFA9_eofS =
        "\1\5\13\uffff";
    static final String DFA9_minS =
        "\1\16\4\0\7\uffff";
    static final String DFA9_maxS =
        "\1\32\4\0\7\uffff";
    static final String DFA9_acceptS =
        "\5\uffff\1\3\3\uffff\1\1\1\2\1\4";
    static final String DFA9_specialS =
        "\1\uffff\1\0\1\1\1\2\1\3\7\uffff}>";
    static final String[] DFA9_transitionS = {
            "\2\5\5\uffff\1\4\1\uffff\1\2\1\3\1\5\1\1",
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
            return "90:1: querylist : ( oquery DEL querylist | sent DEL querylist | oquery | sent );";
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
                        if ( (synpred11_tParser()) ) {s = 9;}

                        else if ( (synpred13_tParser()) ) {s = 5;}


                        input.seek(index9_1);
                        if ( s>=0 ) return s;
                        break;
                    case 1 :
                        int LA9_2 = input.LA(1);


                        int index9_2 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred11_tParser()) ) {s = 9;}

                        else if ( (synpred12_tParser()) ) {s = 10;}

                        else if ( (synpred13_tParser()) ) {s = 5;}

                        else if ( (true) ) {s = 11;}


                        input.seek(index9_2);
                        if ( s>=0 ) return s;
                        break;
                    case 2 :
                        int LA9_3 = input.LA(1);


                        int index9_3 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred11_tParser()) ) {s = 9;}

                        else if ( (synpred13_tParser()) ) {s = 5;}


                        input.seek(index9_3);
                        if ( s>=0 ) return s;
                        break;
                    case 3 :
                        int LA9_4 = input.LA(1);


                        int index9_4 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred11_tParser()) ) {s = 9;}

                        else if ( (synpred12_tParser()) ) {s = 10;}

                        else if ( (synpred13_tParser()) ) {s = 5;}

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
    static final String DFA12_eotS =
        "\24\uffff";
    static final String DFA12_eofS =
        "\24\uffff";
    static final String DFA12_minS =
        "\1\25\2\6\4\20\5\uffff\4\0\4\uffff";
    static final String DFA12_maxS =
        "\1\27\2\12\4\22\5\uffff\4\0\4\uffff";
    static final String DFA12_acceptS =
        "\7\uffff\1\1\1\7\1\11\1\5\1\3\4\uffff\1\2\1\4\1\6\1\10";
    static final String DFA12_specialS =
        "\14\uffff\1\0\1\1\1\3\1\2\4\uffff}>";
    static final String[] DFA12_transitionS = {
            "\1\2\1\uffff\1\1",
            "\1\3\1\4\1\7\1\5\1\6",
            "\1\13\1\12\1\7\1\10\1\11",
            "\3\14",
            "\3\15",
            "\3\16",
            "\3\17",
            "",
            "",
            "",
            "",
            "",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
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
            return "116:1: sent : ( name SIGE val -> ^( ASIGN name val ) | VAR SIGL val -> ^( COMPL VAR val ) | name SIGL val -> ^( COMPL name val ) | VAR SIGG val -> ^( COMPG VAR val ) | name SIGG val -> ^( COMPG name val ) | VAR SIGLE val -> ^( COMPLE VAR val ) | name SIGLE val -> ^( COMPLE name val ) | VAR SIGGE val -> ^( COMPGE VAR val ) | name SIGGE val -> ^( COMPGE name val ) );";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 :
                        int LA12_12 = input.LA(1);


                        int index12_12 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred18_tParser()) ) {s = 16;}

                        else if ( (synpred19_tParser()) ) {s = 11;}


                        input.seek(index12_12);
                        if ( s>=0 ) return s;
                        break;
                    case 1 :
                        int LA12_13 = input.LA(1);


                        int index12_13 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred20_tParser()) ) {s = 17;}

                        else if ( (synpred21_tParser()) ) {s = 10;}


                        input.seek(index12_13);
                        if ( s>=0 ) return s;
                        break;
                    case 2 :
                        int LA12_15 = input.LA(1);


                        int index12_15 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred24_tParser()) ) {s = 19;}

                        else if ( (true) ) {s = 9;}


                        input.seek(index12_15);
                        if ( s>=0 ) return s;
                        break;
                    case 3 :
                        int LA12_14 = input.LA(1);


                        int index12_14 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred22_tParser()) ) {s = 18;}

                        else if ( (synpred23_tParser()) ) {s = 8;}


                        input.seek(index12_14);
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


    public static final BitSet FOLLOW_limiter_in_squery98 = new BitSet(new long[]{0x0000000001A0C000L});
    public static final BitSet FOLLOW_oquery_in_squery101 = new BitSet(new long[]{0x000000000000C000L});
    public static final BitSet FOLLOW_modifier_in_squery103 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_squery106 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_limiter_in_squery124 = new BitSet(new long[]{0x0000000000A01000L});
    public static final BitSet FOLLOW_pquery_in_squery127 = new BitSet(new long[]{0x000000000000C000L});
    public static final BitSet FOLLOW_modifier_in_squery129 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_squery132 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NUM_in_limiter157 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ordterm_in_modifier176 = new BitSet(new long[]{0x000000000000C002L});
    public static final BitSet FOLLOW_offsetterm_in_modifier178 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_offsetterm_in_modifier183 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_MODE_in_offsetterm194 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_NUM_in_offsetterm196 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_MODO_in_ordterm215 = new BitSet(new long[]{0x0000000001000000L});
    public static final BitSet FOLLOW_LPAR_in_ordterm217 = new BitSet(new long[]{0x0000000000A00000L});
    public static final BitSet FOLLOW_plist_in_ordterm219 = new BitSet(new long[]{0x0000000002000000L});
    public static final BitSet FOLLOW_RPAR_in_ordterm221 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_name_in_oquery243 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_name_in_oquery247 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_PREC_in_oquery249 = new BitSet(new long[]{0x0000000005A00000L});
    public static final BitSet FOLLOW_querylist_in_oquery251 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LPAR_in_oquery269 = new BitSet(new long[]{0x0000000003A00000L});
    public static final BitSet FOLLOW_oquery_in_oquery272 = new BitSet(new long[]{0x0000000002000000L});
    public static final BitSet FOLLOW_RPAR_in_oquery274 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_oquery_in_querylist286 = new BitSet(new long[]{0x0000000004000000L});
    public static final BitSet FOLLOW_DEL_in_querylist288 = new BitSet(new long[]{0x0000000005A00000L});
    public static final BitSet FOLLOW_querylist_in_querylist291 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_sent_in_querylist295 = new BitSet(new long[]{0x0000000004000000L});
    public static final BitSet FOLLOW_DEL_in_querylist297 = new BitSet(new long[]{0x0000000005A00000L});
    public static final BitSet FOLLOW_querylist_in_querylist300 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_oquery_in_querylist304 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_sent_in_querylist308 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_plist_in_pquery319 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_PRED_in_pquery321 = new BitSet(new long[]{0x0000000001A00000L});
    public static final BitSet FOLLOW_oquery_in_pquery323 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_MODT_in_pquery341 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_PRED_in_pquery343 = new BitSet(new long[]{0x0000000001A00000L});
    public static final BitSet FOLLOW_oquery_in_pquery345 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_name_in_plist370 = new BitSet(new long[]{0x0000000004000000L});
    public static final BitSet FOLLOW_DEL_in_plist372 = new BitSet(new long[]{0x0000000000A00000L});
    public static final BitSet FOLLOW_plist_in_plist375 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_name_in_plist379 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_name0 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_name_in_sent405 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_SIGE_in_sent407 = new BitSet(new long[]{0x0000000000070000L});
    public static final BitSet FOLLOW_val_in_sent409 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_VAR_in_sent423 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_SIGL_in_sent425 = new BitSet(new long[]{0x0000000000070000L});
    public static final BitSet FOLLOW_val_in_sent427 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_name_in_sent441 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_SIGL_in_sent443 = new BitSet(new long[]{0x0000000000070000L});
    public static final BitSet FOLLOW_val_in_sent445 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_VAR_in_sent459 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_SIGG_in_sent461 = new BitSet(new long[]{0x0000000000070000L});
    public static final BitSet FOLLOW_val_in_sent463 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_name_in_sent477 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_SIGG_in_sent479 = new BitSet(new long[]{0x0000000000070000L});
    public static final BitSet FOLLOW_val_in_sent481 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_VAR_in_sent495 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_SIGLE_in_sent497 = new BitSet(new long[]{0x0000000000070000L});
    public static final BitSet FOLLOW_val_in_sent499 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_name_in_sent513 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_SIGLE_in_sent515 = new BitSet(new long[]{0x0000000000070000L});
    public static final BitSet FOLLOW_val_in_sent517 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_VAR_in_sent531 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_SIGGE_in_sent533 = new BitSet(new long[]{0x0000000000070000L});
    public static final BitSet FOLLOW_val_in_sent535 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_name_in_sent549 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_SIGGE_in_sent551 = new BitSet(new long[]{0x0000000000070000L});
    public static final BitSet FOLLOW_val_in_sent553 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_val0 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_oquery_in_synpred11_tParser286 = new BitSet(new long[]{0x0000000004000000L});
    public static final BitSet FOLLOW_DEL_in_synpred11_tParser288 = new BitSet(new long[]{0x0000000005A00000L});
    public static final BitSet FOLLOW_querylist_in_synpred11_tParser291 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_sent_in_synpred12_tParser295 = new BitSet(new long[]{0x0000000004000000L});
    public static final BitSet FOLLOW_DEL_in_synpred12_tParser297 = new BitSet(new long[]{0x0000000005A00000L});
    public static final BitSet FOLLOW_querylist_in_synpred12_tParser300 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_oquery_in_synpred13_tParser304 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_VAR_in_synpred18_tParser423 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_SIGL_in_synpred18_tParser425 = new BitSet(new long[]{0x0000000000070000L});
    public static final BitSet FOLLOW_val_in_synpred18_tParser427 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_name_in_synpred19_tParser441 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_SIGL_in_synpred19_tParser443 = new BitSet(new long[]{0x0000000000070000L});
    public static final BitSet FOLLOW_val_in_synpred19_tParser445 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_VAR_in_synpred20_tParser459 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_SIGG_in_synpred20_tParser461 = new BitSet(new long[]{0x0000000000070000L});
    public static final BitSet FOLLOW_val_in_synpred20_tParser463 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_name_in_synpred21_tParser477 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_SIGG_in_synpred21_tParser479 = new BitSet(new long[]{0x0000000000070000L});
    public static final BitSet FOLLOW_val_in_synpred21_tParser481 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_VAR_in_synpred22_tParser495 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_SIGLE_in_synpred22_tParser497 = new BitSet(new long[]{0x0000000000070000L});
    public static final BitSet FOLLOW_val_in_synpred22_tParser499 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_name_in_synpred23_tParser513 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_SIGLE_in_synpred23_tParser515 = new BitSet(new long[]{0x0000000000070000L});
    public static final BitSet FOLLOW_val_in_synpred23_tParser517 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_VAR_in_synpred24_tParser531 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_SIGGE_in_synpred24_tParser533 = new BitSet(new long[]{0x0000000000070000L});
    public static final BitSet FOLLOW_val_in_synpred24_tParser535 = new BitSet(new long[]{0x0000000000000002L});
}