/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.nlp;

/**
 *
 * @author hasdai
 */
// $ANTLR 3.1.2 /home/hasdai/Documentos/sParser.g 2009-03-20 13:29:53

import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

import org.antlr.runtime.tree.*;

public class sParser extends Parser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "WHITESPACE", "SIGN", "SIGL", "SIGG", "SIGE", "SIGLE", "SIGGE", "PREC", "MODT", "PRED", "MODE", "MODO", "BOL", "NUM", "LIT", "ORDOP", "VAR", "DEL", "LPAR", "RPAR", "SIGI", "LIMIT", "SELECT", "MOD", "OBJLIST", "PROPLIST", "ASIGN", "SENTENCE", "COMPL", "COMPG", "COMPLE", "COMPGE", "PRECON", "PREDE", "OFFSET", "ORDER", "COMPNAME", "ASKQUERY", "MODTO", "NAME"
    };
    public static final int SIGN=5;
    public static final int SIGI=24;
    public static final int SIGL=6;
    public static final int SIGE=8;
    public static final int ORDER=39;
    public static final int LIMIT=25;
    public static final int MOD=27;
    public static final int ASKQUERY=41;
    public static final int SIGG=7;
    public static final int DEL=21;
    public static final int ORDOP=19;
    public static final int BOL=16;
    public static final int MODTO=42;
    public static final int OBJLIST=28;
    public static final int COMPL=32;
    public static final int EOF=-1;
    public static final int COMPG=33;
    public static final int NAME=43;
    public static final int LPAR=22;
    public static final int SENTENCE=31;
    public static final int COMPLE=34;
    public static final int OFFSET=38;
    public static final int MODT=12;
    public static final int PROPLIST=29;
    public static final int VAR=20;
    public static final int MODO=15;
    public static final int SELECT=26;
    public static final int MODE=14;
    public static final int PREDE=37;
    public static final int COMPGE=35;
    public static final int PRECON=36;
    public static final int PREC=11;
    public static final int PRED=13;
    public static final int SIGGE=10;
    public static final int LIT=18;
    public static final int WHITESPACE=4;
    public static final int NUM=17;
    public static final int SIGLE=9;
    public static final int COMPNAME=40;
    public static final int ASIGN=30;
    public static final int RPAR=23;

    // delegates
    // delegators


        public sParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public sParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);

        }

    protected TreeAdaptor adaptor = new CommonTreeAdaptor();

    public void setTreeAdaptor(TreeAdaptor adaptor) {
        this.adaptor = adaptor;
    }
    public TreeAdaptor getTreeAdaptor() {
        return adaptor;
    }

    public String[] getTokenNames() { return sParser.tokenNames; }
    public String getGrammarFileName() { return "/home/hasdai/Documentos/sParser.g"; }


    	private int errCount = 0;
    	private boolean isCompound = false;

    	public void displayRecognitionError(String[] tokenNames, RecognitionException e) {
            	errCount++;
        	}

    	public int getErrorCount() {
            	return errCount;
        	}

        	public boolean isCompoundQuery() {
        		return isCompound;
        	}


    public static class squery_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "squery"
    // /home/hasdai/Documentos/sParser.g:31:1: squery : selectquery ;
    public final sParser.squery_return squery() throws RecognitionException {
        sParser.squery_return retval = new sParser.squery_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        sParser.selectquery_return selectquery1 = null;



        try {
            // /home/hasdai/Documentos/sParser.g:32:1: ( selectquery )
            // /home/hasdai/Documentos/sParser.g:32:3: selectquery
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_selectquery_in_squery111);
            selectquery1=selectquery();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, selectquery1.getTree());

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

    public static class selectquery_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "selectquery"
    // /home/hasdai/Documentos/sParser.g:36:1: selectquery : ( limiter )? query ( modifier )? EOF -> ^( SELECT ( limiter )? query ( modifier )? ) ;
    public final sParser.selectquery_return selectquery() throws RecognitionException {
        sParser.selectquery_return retval = new sParser.selectquery_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token EOF5=null;
        sParser.limiter_return limiter2 = null;

        sParser.query_return query3 = null;

        sParser.modifier_return modifier4 = null;


        Object EOF5_tree=null;
        RewriteRuleTokenStream stream_EOF=new RewriteRuleTokenStream(adaptor,"token EOF");
        RewriteRuleSubtreeStream stream_limiter=new RewriteRuleSubtreeStream(adaptor,"rule limiter");
        RewriteRuleSubtreeStream stream_modifier=new RewriteRuleSubtreeStream(adaptor,"rule modifier");
        RewriteRuleSubtreeStream stream_query=new RewriteRuleSubtreeStream(adaptor,"rule query");
        try {
            // /home/hasdai/Documentos/sParser.g:37:1: ( ( limiter )? query ( modifier )? EOF -> ^( SELECT ( limiter )? query ( modifier )? ) )
            // /home/hasdai/Documentos/sParser.g:37:3: ( limiter )? query ( modifier )? EOF
            {
            // /home/hasdai/Documentos/sParser.g:37:3: ( limiter )?
            int alt1=2;
            int LA1_0 = input.LA(1);

            if ( (LA1_0==NUM) ) {
                alt1=1;
            }
            switch (alt1) {
                case 1 :
                    // /home/hasdai/Documentos/sParser.g:0:0: limiter
                    {
                    pushFollow(FOLLOW_limiter_in_selectquery121);
                    limiter2=limiter();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_limiter.add(limiter2.getTree());

                    }
                    break;

            }

            pushFollow(FOLLOW_query_in_selectquery124);
            query3=query();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_query.add(query3.getTree());
            // /home/hasdai/Documentos/sParser.g:37:18: ( modifier )?
            int alt2=2;
            int LA2_0 = input.LA(1);

            if ( ((LA2_0>=MODE && LA2_0<=MODO)) ) {
                alt2=1;
            }
            else if ( (LA2_0==EOF) ) {
                int LA2_2 = input.LA(2);

                if ( (synpred2_sParser()) ) {
                    alt2=1;
                }
            }
            switch (alt2) {
                case 1 :
                    // /home/hasdai/Documentos/sParser.g:0:0: modifier
                    {
                    pushFollow(FOLLOW_modifier_in_selectquery126);
                    modifier4=modifier();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_modifier.add(modifier4.getTree());

                    }
                    break;

            }

            EOF5=(Token)match(input,EOF,FOLLOW_EOF_in_selectquery129); if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_EOF.add(EOF5);



            // AST REWRITE
            // elements: query, limiter, modifier
            // token labels:
            // rule labels: retval
            // token list labels:
            // rule list labels:
            // wildcard labels:
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 37:32: -> ^( SELECT ( limiter )? query ( modifier )? )
            {
                // /home/hasdai/Documentos/sParser.g:37:35: ^( SELECT ( limiter )? query ( modifier )? )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(SELECT, "SELECT"), root_1);

                // /home/hasdai/Documentos/sParser.g:37:44: ( limiter )?
                if ( stream_limiter.hasNext() ) {
                    adaptor.addChild(root_1, stream_limiter.nextTree());

                }
                stream_limiter.reset();
                adaptor.addChild(root_1, stream_query.nextTree());
                // /home/hasdai/Documentos/sParser.g:37:59: ( modifier )?
                if ( stream_modifier.hasNext() ) {
                    adaptor.addChild(root_1, stream_modifier.nextTree());

                }
                stream_modifier.reset();

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
    // $ANTLR end "selectquery"

    public static class limiter_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "limiter"
    // /home/hasdai/Documentos/sParser.g:44:1: limiter : NUM -> ^( LIMIT NUM ) ;
    public final sParser.limiter_return limiter() throws RecognitionException {
        sParser.limiter_return retval = new sParser.limiter_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token NUM6=null;

        Object NUM6_tree=null;
        RewriteRuleTokenStream stream_NUM=new RewriteRuleTokenStream(adaptor,"token NUM");

        try {
            // /home/hasdai/Documentos/sParser.g:45:1: ( NUM -> ^( LIMIT NUM ) )
            // /home/hasdai/Documentos/sParser.g:45:3: NUM
            {
            NUM6=(Token)match(input,NUM,FOLLOW_NUM_in_limiter155); if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_NUM.add(NUM6);



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
            // 45:7: -> ^( LIMIT NUM )
            {
                // /home/hasdai/Documentos/sParser.g:45:10: ^( LIMIT NUM )
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

    public static class query_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "query"
    // /home/hasdai/Documentos/sParser.g:48:1: query : ( objquery | propquery );
    public final sParser.query_return query() throws RecognitionException {
        sParser.query_return retval = new sParser.query_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        sParser.objquery_return objquery7 = null;

        sParser.propquery_return propquery8 = null;



        try {
            // /home/hasdai/Documentos/sParser.g:49:1: ( objquery | propquery )
            int alt3=2;
            alt3 = dfa3.predict(input);
            switch (alt3) {
                case 1 :
                    // /home/hasdai/Documentos/sParser.g:49:3: objquery
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_objquery_in_query172);
                    objquery7=objquery();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, objquery7.getTree());

                    }
                    break;
                case 2 :
                    // /home/hasdai/Documentos/sParser.g:50:3: propquery
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_propquery_in_query176);
                    propquery8=propquery();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, propquery8.getTree());
                    if ( state.backtracking==0 ) {
                      isCompound = true;
                    }

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
    // $ANTLR end "query"

    public static class objquery_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "objquery"
    // /home/hasdai/Documentos/sParser.g:53:1: objquery : ( name PREC proplist -> ^( name ^( PRECON proplist ) ) | name );
    public final sParser.objquery_return objquery() throws RecognitionException {
        sParser.objquery_return retval = new sParser.objquery_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token PREC10=null;
        sParser.name_return name9 = null;

        sParser.proplist_return proplist11 = null;

        sParser.name_return name12 = null;


        Object PREC10_tree=null;
        RewriteRuleTokenStream stream_PREC=new RewriteRuleTokenStream(adaptor,"token PREC");
        RewriteRuleSubtreeStream stream_name=new RewriteRuleSubtreeStream(adaptor,"rule name");
        RewriteRuleSubtreeStream stream_proplist=new RewriteRuleSubtreeStream(adaptor,"rule proplist");
        try {
            // /home/hasdai/Documentos/sParser.g:54:1: ( name PREC proplist -> ^( name ^( PRECON proplist ) ) | name )
            int alt4=2;
            alt4 = dfa4.predict(input);
            switch (alt4) {
                case 1 :
                    // /home/hasdai/Documentos/sParser.g:54:3: name PREC proplist
                    {
                    pushFollow(FOLLOW_name_in_objquery187);
                    name9=name();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_name.add(name9.getTree());
                    PREC10=(Token)match(input,PREC,FOLLOW_PREC_in_objquery189); if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_PREC.add(PREC10);

                    pushFollow(FOLLOW_proplist_in_objquery191);
                    proplist11=proplist();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_proplist.add(proplist11.getTree());


                    // AST REWRITE
                    // elements: proplist, name
                    // token labels:
                    // rule labels: retval
                    // token list labels:
                    // rule list labels:
                    // wildcard labels:
                    if ( state.backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 54:22: -> ^( name ^( PRECON proplist ) )
                    {
                        // /home/hasdai/Documentos/sParser.g:54:25: ^( name ^( PRECON proplist ) )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot(stream_name.nextNode(), root_1);

                        // /home/hasdai/Documentos/sParser.g:54:32: ^( PRECON proplist )
                        {
                        Object root_2 = (Object)adaptor.nil();
                        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(PRECON, "PRECON"), root_2);

                        adaptor.addChild(root_2, stream_proplist.nextTree());

                        adaptor.addChild(root_1, root_2);
                        }

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;}
                    }
                    break;
                case 2 :
                    // /home/hasdai/Documentos/sParser.g:55:3: name
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_name_in_objquery207);
                    name12=name();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, name12.getTree());

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
    // $ANTLR end "objquery"

    public static class propquery_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "propquery"
    // /home/hasdai/Documentos/sParser.g:58:1: propquery : v1= proplist PRED name ( PREC v2= proplist )? -> ^( name ^( PREDE $v1) ( ^( PRECON $v2) )? ) ;
    public final sParser.propquery_return propquery() throws RecognitionException {
        sParser.propquery_return retval = new sParser.propquery_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token PRED13=null;
        Token PREC15=null;
        sParser.proplist_return v1 = null;

        sParser.proplist_return v2 = null;

        sParser.name_return name14 = null;


        Object PRED13_tree=null;
        Object PREC15_tree=null;
        RewriteRuleTokenStream stream_PREC=new RewriteRuleTokenStream(adaptor,"token PREC");
        RewriteRuleTokenStream stream_PRED=new RewriteRuleTokenStream(adaptor,"token PRED");
        RewriteRuleSubtreeStream stream_name=new RewriteRuleSubtreeStream(adaptor,"rule name");
        RewriteRuleSubtreeStream stream_proplist=new RewriteRuleSubtreeStream(adaptor,"rule proplist");
        try {
            // /home/hasdai/Documentos/sParser.g:59:1: (v1= proplist PRED name ( PREC v2= proplist )? -> ^( name ^( PREDE $v1) ( ^( PRECON $v2) )? ) )
            // /home/hasdai/Documentos/sParser.g:59:3: v1= proplist PRED name ( PREC v2= proplist )?
            {
            pushFollow(FOLLOW_proplist_in_propquery218);
            v1=proplist();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_proplist.add(v1.getTree());
            PRED13=(Token)match(input,PRED,FOLLOW_PRED_in_propquery220); if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_PRED.add(PRED13);

            pushFollow(FOLLOW_name_in_propquery222);
            name14=name();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_name.add(name14.getTree());
            // /home/hasdai/Documentos/sParser.g:59:25: ( PREC v2= proplist )?
            int alt5=2;
            int LA5_0 = input.LA(1);

            if ( (LA5_0==PREC) ) {
                alt5=1;
            }
            switch (alt5) {
                case 1 :
                    // /home/hasdai/Documentos/sParser.g:59:26: PREC v2= proplist
                    {
                    PREC15=(Token)match(input,PREC,FOLLOW_PREC_in_propquery225); if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_PREC.add(PREC15);

                    pushFollow(FOLLOW_proplist_in_propquery229);
                    v2=proplist();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_proplist.add(v2.getTree());

                    }
                    break;

            }



            // AST REWRITE
            // elements: v1, name, v2
            // token labels:
            // rule labels: v1, retval, v2
            // token list labels:
            // rule list labels:
            // wildcard labels:
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_v1=new RewriteRuleSubtreeStream(adaptor,"rule v1",v1!=null?v1.tree:null);
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);
            RewriteRuleSubtreeStream stream_v2=new RewriteRuleSubtreeStream(adaptor,"rule v2",v2!=null?v2.tree:null);

            root_0 = (Object)adaptor.nil();
            // 59:45: -> ^( name ^( PREDE $v1) ( ^( PRECON $v2) )? )
            {
                // /home/hasdai/Documentos/sParser.g:59:48: ^( name ^( PREDE $v1) ( ^( PRECON $v2) )? )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot(stream_name.nextNode(), root_1);

                // /home/hasdai/Documentos/sParser.g:59:55: ^( PREDE $v1)
                {
                Object root_2 = (Object)adaptor.nil();
                root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(PREDE, "PREDE"), root_2);

                adaptor.addChild(root_2, stream_v1.nextTree());

                adaptor.addChild(root_1, root_2);
                }
                // /home/hasdai/Documentos/sParser.g:59:68: ( ^( PRECON $v2) )?
                if ( stream_v2.hasNext() ) {
                    // /home/hasdai/Documentos/sParser.g:59:68: ^( PRECON $v2)
                    {
                    Object root_2 = (Object)adaptor.nil();
                    root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(PRECON, "PRECON"), root_2);

                    adaptor.addChild(root_2, stream_v2.nextTree());

                    adaptor.addChild(root_1, root_2);
                    }

                }
                stream_v2.reset();

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
    // $ANTLR end "propquery"

    public static class name_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "name"
    // /home/hasdai/Documentos/sParser.g:62:1: name : (v1= VAR (v2= VAR )+ -> ^( COMPNAME $v1 $v2) | VAR );
    public final sParser.name_return name() throws RecognitionException {
        sParser.name_return retval = new sParser.name_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token v1=null;
        Token v2=null;
        Token VAR16=null;

        Object v1_tree=null;
        Object v2_tree=null;
        Object VAR16_tree=null;
        RewriteRuleTokenStream stream_VAR=new RewriteRuleTokenStream(adaptor,"token VAR");

        try {
            // /home/hasdai/Documentos/sParser.g:63:1: (v1= VAR (v2= VAR )+ -> ^( COMPNAME $v1 $v2) | VAR )
            int alt7=2;
            int LA7_0 = input.LA(1);

            if ( (LA7_0==VAR) ) {
                int LA7_1 = input.LA(2);

                if ( (LA7_1==VAR) ) {
                    alt7=1;
                }
                else if ( (LA7_1==EOF||(LA7_1>=SIGL && LA7_1<=PREC)||(LA7_1>=PRED && LA7_1<=MODO)||LA7_1==DEL||LA7_1==RPAR) ) {
                    alt7=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 7, 1, input);

                    throw nvae;
                }
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 7, 0, input);

                throw nvae;
            }
            switch (alt7) {
                case 1 :
                    // /home/hasdai/Documentos/sParser.g:63:3: v1= VAR (v2= VAR )+
                    {
                    v1=(Token)match(input,VAR,FOLLOW_VAR_in_name263); if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_VAR.add(v1);

                    // /home/hasdai/Documentos/sParser.g:63:12: (v2= VAR )+
                    int cnt6=0;
                    loop6:
                    do {
                        int alt6=2;
                        int LA6_0 = input.LA(1);

                        if ( (LA6_0==VAR) ) {
                            alt6=1;
                        }


                        switch (alt6) {
                    	case 1 :
                    	    // /home/hasdai/Documentos/sParser.g:0:0: v2= VAR
                    	    {
                    	    v2=(Token)match(input,VAR,FOLLOW_VAR_in_name267); if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) stream_VAR.add(v2);


                    	    }
                    	    break;

                    	default :
                    	    if ( cnt6 >= 1 ) break loop6;
                    	    if (state.backtracking>0) {state.failed=true; return retval;}
                                EarlyExitException eee =
                                    new EarlyExitException(6, input);
                                throw eee;
                        }
                        cnt6++;
                    } while (true);



                    // AST REWRITE
                    // elements: v2, v1
                    // token labels: v1, v2
                    // rule labels: retval
                    // token list labels:
                    // rule list labels:
                    // wildcard labels:
                    if ( state.backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleTokenStream stream_v1=new RewriteRuleTokenStream(adaptor,"token v1",v1);
                    RewriteRuleTokenStream stream_v2=new RewriteRuleTokenStream(adaptor,"token v2",v2);
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 63:18: -> ^( COMPNAME $v1 $v2)
                    {
                        // /home/hasdai/Documentos/sParser.g:63:21: ^( COMPNAME $v1 $v2)
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(COMPNAME, "COMPNAME"), root_1);

                        adaptor.addChild(root_1, stream_v1.nextNode());
                        adaptor.addChild(root_1, stream_v2.nextNode());

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;}
                    }
                    break;
                case 2 :
                    // /home/hasdai/Documentos/sParser.g:64:3: VAR
                    {
                    root_0 = (Object)adaptor.nil();

                    VAR16=(Token)match(input,VAR,FOLLOW_VAR_in_name284); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    VAR16_tree = (Object)adaptor.create(VAR16);
                    adaptor.addChild(root_0, VAR16_tree);
                    }

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
    // $ANTLR end "name"

    public static class sent_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "sent"
    // /home/hasdai/Documentos/sParser.g:67:1: sent : ( name SIGE val -> ^( ASIGN name val ) | name SIGL val -> ^( COMPL name val ) | name SIGG val -> ^( COMPG name val ) | name SIGLE val -> ^( COMPLE name val ) | name SIGGE val -> ^( COMPGE name val ) | name );
    public final sParser.sent_return sent() throws RecognitionException {
        sParser.sent_return retval = new sParser.sent_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token SIGE18=null;
        Token SIGL21=null;
        Token SIGG24=null;
        Token SIGLE27=null;
        Token SIGGE30=null;
        sParser.name_return name17 = null;

        sParser.val_return val19 = null;

        sParser.name_return name20 = null;

        sParser.val_return val22 = null;

        sParser.name_return name23 = null;

        sParser.val_return val25 = null;

        sParser.name_return name26 = null;

        sParser.val_return val28 = null;

        sParser.name_return name29 = null;

        sParser.val_return val31 = null;

        sParser.name_return name32 = null;


        Object SIGE18_tree=null;
        Object SIGL21_tree=null;
        Object SIGG24_tree=null;
        Object SIGLE27_tree=null;
        Object SIGGE30_tree=null;
        RewriteRuleTokenStream stream_SIGLE=new RewriteRuleTokenStream(adaptor,"token SIGLE");
        RewriteRuleTokenStream stream_SIGL=new RewriteRuleTokenStream(adaptor,"token SIGL");
        RewriteRuleTokenStream stream_SIGE=new RewriteRuleTokenStream(adaptor,"token SIGE");
        RewriteRuleTokenStream stream_SIGG=new RewriteRuleTokenStream(adaptor,"token SIGG");
        RewriteRuleTokenStream stream_SIGGE=new RewriteRuleTokenStream(adaptor,"token SIGGE");
        RewriteRuleSubtreeStream stream_val=new RewriteRuleSubtreeStream(adaptor,"rule val");
        RewriteRuleSubtreeStream stream_name=new RewriteRuleSubtreeStream(adaptor,"rule name");
        try {
            // /home/hasdai/Documentos/sParser.g:68:1: ( name SIGE val -> ^( ASIGN name val ) | name SIGL val -> ^( COMPL name val ) | name SIGG val -> ^( COMPG name val ) | name SIGLE val -> ^( COMPLE name val ) | name SIGGE val -> ^( COMPGE name val ) | name )
            int alt8=6;
            alt8 = dfa8.predict(input);
            switch (alt8) {
                case 1 :
                    // /home/hasdai/Documentos/sParser.g:68:3: name SIGE val
                    {
                    pushFollow(FOLLOW_name_in_sent293);
                    name17=name();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_name.add(name17.getTree());
                    SIGE18=(Token)match(input,SIGE,FOLLOW_SIGE_in_sent295); if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_SIGE.add(SIGE18);

                    pushFollow(FOLLOW_val_in_sent297);
                    val19=val();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_val.add(val19.getTree());


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
                    // 68:17: -> ^( ASIGN name val )
                    {
                        // /home/hasdai/Documentos/sParser.g:68:20: ^( ASIGN name val )
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
                    // /home/hasdai/Documentos/sParser.g:69:3: name SIGL val
                    {
                    pushFollow(FOLLOW_name_in_sent311);
                    name20=name();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_name.add(name20.getTree());
                    SIGL21=(Token)match(input,SIGL,FOLLOW_SIGL_in_sent313); if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_SIGL.add(SIGL21);

                    pushFollow(FOLLOW_val_in_sent315);
                    val22=val();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_val.add(val22.getTree());


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
                    // 69:17: -> ^( COMPL name val )
                    {
                        // /home/hasdai/Documentos/sParser.g:69:20: ^( COMPL name val )
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
                case 3 :
                    // /home/hasdai/Documentos/sParser.g:70:3: name SIGG val
                    {
                    pushFollow(FOLLOW_name_in_sent329);
                    name23=name();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_name.add(name23.getTree());
                    SIGG24=(Token)match(input,SIGG,FOLLOW_SIGG_in_sent331); if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_SIGG.add(SIGG24);

                    pushFollow(FOLLOW_val_in_sent333);
                    val25=val();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_val.add(val25.getTree());


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
                    // 70:17: -> ^( COMPG name val )
                    {
                        // /home/hasdai/Documentos/sParser.g:70:20: ^( COMPG name val )
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
                case 4 :
                    // /home/hasdai/Documentos/sParser.g:71:3: name SIGLE val
                    {
                    pushFollow(FOLLOW_name_in_sent347);
                    name26=name();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_name.add(name26.getTree());
                    SIGLE27=(Token)match(input,SIGLE,FOLLOW_SIGLE_in_sent349); if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_SIGLE.add(SIGLE27);

                    pushFollow(FOLLOW_val_in_sent351);
                    val28=val();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_val.add(val28.getTree());


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
                    // 71:18: -> ^( COMPLE name val )
                    {
                        // /home/hasdai/Documentos/sParser.g:71:21: ^( COMPLE name val )
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
                case 5 :
                    // /home/hasdai/Documentos/sParser.g:72:3: name SIGGE val
                    {
                    pushFollow(FOLLOW_name_in_sent365);
                    name29=name();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_name.add(name29.getTree());
                    SIGGE30=(Token)match(input,SIGGE,FOLLOW_SIGGE_in_sent367); if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_SIGGE.add(SIGGE30);

                    pushFollow(FOLLOW_val_in_sent369);
                    val31=val();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_val.add(val31.getTree());


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
                    // 72:18: -> ^( COMPGE name val )
                    {
                        // /home/hasdai/Documentos/sParser.g:72:21: ^( COMPGE name val )
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
                case 6 :
                    // /home/hasdai/Documentos/sParser.g:73:3: name
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_name_in_sent383);
                    name32=name();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, name32.getTree());

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

    public static class proplist_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "proplist"
    // /home/hasdai/Documentos/sParser.g:75:1: proplist : ( sent DEL proplist | sent );
    public final sParser.proplist_return proplist() throws RecognitionException {
        sParser.proplist_return retval = new sParser.proplist_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token DEL34=null;
        sParser.sent_return sent33 = null;

        sParser.proplist_return proplist35 = null;

        sParser.sent_return sent36 = null;


        Object DEL34_tree=null;

        try {
            // /home/hasdai/Documentos/sParser.g:76:1: ( sent DEL proplist | sent )
            int alt9=2;
            alt9 = dfa9.predict(input);
            switch (alt9) {
                case 1 :
                    // /home/hasdai/Documentos/sParser.g:76:3: sent DEL proplist
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_sent_in_proplist391);
                    sent33=sent();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, sent33.getTree());
                    DEL34=(Token)match(input,DEL,FOLLOW_DEL_in_proplist393); if (state.failed) return retval;
                    pushFollow(FOLLOW_proplist_in_proplist396);
                    proplist35=proplist();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, proplist35.getTree());

                    }
                    break;
                case 2 :
                    // /home/hasdai/Documentos/sParser.g:77:3: sent
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_sent_in_proplist400);
                    sent36=sent();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, sent36.getTree());

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
    // $ANTLR end "proplist"

    public static class val_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "val"
    // /home/hasdai/Documentos/sParser.g:80:1: val : ( NUM | LIT | BOL );
    public final sParser.val_return val() throws RecognitionException {
        sParser.val_return retval = new sParser.val_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token set37=null;

        Object set37_tree=null;

        try {
            // /home/hasdai/Documentos/sParser.g:81:1: ( NUM | LIT | BOL )
            // /home/hasdai/Documentos/sParser.g:
            {
            root_0 = (Object)adaptor.nil();

            set37=(Token)input.LT(1);
            if ( (input.LA(1)>=BOL && input.LA(1)<=LIT) ) {
                input.consume();
                if ( state.backtracking==0 ) adaptor.addChild(root_0, (Object)adaptor.create(set37));
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

    public static class modifier_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "modifier"
    // /home/hasdai/Documentos/sParser.g:86:1: modifier : ( ordterm )? ( offsetterm )? ;
    public final sParser.modifier_return modifier() throws RecognitionException {
        sParser.modifier_return retval = new sParser.modifier_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        sParser.ordterm_return ordterm38 = null;

        sParser.offsetterm_return offsetterm39 = null;



        try {
            // /home/hasdai/Documentos/sParser.g:87:1: ( ( ordterm )? ( offsetterm )? )
            // /home/hasdai/Documentos/sParser.g:87:3: ( ordterm )? ( offsetterm )?
            {
            root_0 = (Object)adaptor.nil();

            // /home/hasdai/Documentos/sParser.g:87:3: ( ordterm )?
            int alt10=2;
            int LA10_0 = input.LA(1);

            if ( (LA10_0==MODO) ) {
                alt10=1;
            }
            switch (alt10) {
                case 1 :
                    // /home/hasdai/Documentos/sParser.g:0:0: ordterm
                    {
                    pushFollow(FOLLOW_ordterm_in_modifier427);
                    ordterm38=ordterm();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, ordterm38.getTree());

                    }
                    break;

            }

            // /home/hasdai/Documentos/sParser.g:87:12: ( offsetterm )?
            int alt11=2;
            int LA11_0 = input.LA(1);

            if ( (LA11_0==MODE) ) {
                alt11=1;
            }
            switch (alt11) {
                case 1 :
                    // /home/hasdai/Documentos/sParser.g:0:0: offsetterm
                    {
                    pushFollow(FOLLOW_offsetterm_in_modifier430);
                    offsetterm39=offsetterm();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, offsetterm39.getTree());

                    }
                    break;

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
    // $ANTLR end "modifier"

    public static class offsetterm_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "offsetterm"
    // /home/hasdai/Documentos/sParser.g:90:1: offsetterm : MODE NUM -> ^( OFFSET NUM ) ;
    public final sParser.offsetterm_return offsetterm() throws RecognitionException {
        sParser.offsetterm_return retval = new sParser.offsetterm_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token MODE40=null;
        Token NUM41=null;

        Object MODE40_tree=null;
        Object NUM41_tree=null;
        RewriteRuleTokenStream stream_MODE=new RewriteRuleTokenStream(adaptor,"token MODE");
        RewriteRuleTokenStream stream_NUM=new RewriteRuleTokenStream(adaptor,"token NUM");

        try {
            // /home/hasdai/Documentos/sParser.g:91:1: ( MODE NUM -> ^( OFFSET NUM ) )
            // /home/hasdai/Documentos/sParser.g:91:3: MODE NUM
            {
            MODE40=(Token)match(input,MODE,FOLLOW_MODE_in_offsetterm440); if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_MODE.add(MODE40);

            NUM41=(Token)match(input,NUM,FOLLOW_NUM_in_offsetterm442); if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_NUM.add(NUM41);



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
            // 91:12: -> ^( OFFSET NUM )
            {
                // /home/hasdai/Documentos/sParser.g:91:15: ^( OFFSET NUM )
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
    // /home/hasdai/Documentos/sParser.g:94:1: ordterm : MODO LPAR ordlist RPAR -> ^( ORDER ordlist ) ;
    public final sParser.ordterm_return ordterm() throws RecognitionException {
        sParser.ordterm_return retval = new sParser.ordterm_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token MODO42=null;
        Token LPAR43=null;
        Token RPAR45=null;
        sParser.ordlist_return ordlist44 = null;


        Object MODO42_tree=null;
        Object LPAR43_tree=null;
        Object RPAR45_tree=null;
        RewriteRuleTokenStream stream_RPAR=new RewriteRuleTokenStream(adaptor,"token RPAR");
        RewriteRuleTokenStream stream_MODO=new RewriteRuleTokenStream(adaptor,"token MODO");
        RewriteRuleTokenStream stream_LPAR=new RewriteRuleTokenStream(adaptor,"token LPAR");
        RewriteRuleSubtreeStream stream_ordlist=new RewriteRuleSubtreeStream(adaptor,"rule ordlist");
        try {
            // /home/hasdai/Documentos/sParser.g:95:1: ( MODO LPAR ordlist RPAR -> ^( ORDER ordlist ) )
            // /home/hasdai/Documentos/sParser.g:95:3: MODO LPAR ordlist RPAR
            {
            MODO42=(Token)match(input,MODO,FOLLOW_MODO_in_ordterm459); if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_MODO.add(MODO42);

            LPAR43=(Token)match(input,LPAR,FOLLOW_LPAR_in_ordterm461); if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_LPAR.add(LPAR43);

            pushFollow(FOLLOW_ordlist_in_ordterm463);
            ordlist44=ordlist();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_ordlist.add(ordlist44.getTree());
            RPAR45=(Token)match(input,RPAR,FOLLOW_RPAR_in_ordterm465); if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_RPAR.add(RPAR45);



            // AST REWRITE
            // elements: ordlist
            // token labels:
            // rule labels: retval
            // token list labels:
            // rule list labels:
            // wildcard labels:
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 95:26: -> ^( ORDER ordlist )
            {
                // /home/hasdai/Documentos/sParser.g:95:29: ^( ORDER ordlist )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(ORDER, "ORDER"), root_1);

                adaptor.addChild(root_1, stream_ordlist.nextTree());

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

    public static class ordlist_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "ordlist"
    // /home/hasdai/Documentos/sParser.g:98:1: ordlist : ( name DEL ordlist | name );
    public final sParser.ordlist_return ordlist() throws RecognitionException {
        sParser.ordlist_return retval = new sParser.ordlist_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token DEL47=null;
        sParser.name_return name46 = null;

        sParser.ordlist_return ordlist48 = null;

        sParser.name_return name49 = null;


        Object DEL47_tree=null;

        try {
            // /home/hasdai/Documentos/sParser.g:99:1: ( name DEL ordlist | name )
            int alt12=2;
            alt12 = dfa12.predict(input);
            switch (alt12) {
                case 1 :
                    // /home/hasdai/Documentos/sParser.g:99:3: name DEL ordlist
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_name_in_ordlist482);
                    name46=name();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, name46.getTree());
                    DEL47=(Token)match(input,DEL,FOLLOW_DEL_in_ordlist484); if (state.failed) return retval;
                    pushFollow(FOLLOW_ordlist_in_ordlist487);
                    ordlist48=ordlist();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, ordlist48.getTree());

                    }
                    break;
                case 2 :
                    // /home/hasdai/Documentos/sParser.g:100:3: name
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_name_in_ordlist491);
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
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "ordlist"

    // $ANTLR start synpred2_sParser
    public final void synpred2_sParser_fragment() throws RecognitionException {
        // /home/hasdai/Documentos/sParser.g:37:18: ( modifier )
        // /home/hasdai/Documentos/sParser.g:37:18: modifier
        {
        pushFollow(FOLLOW_modifier_in_synpred2_sParser126);
        modifier();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred2_sParser

    // Delegated rules

    public final boolean synpred2_sParser() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred2_sParser_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }


    protected DFA3 dfa3 = new DFA3(this);
    protected DFA4 dfa4 = new DFA4(this);
    protected DFA8 dfa8 = new DFA8(this);
    protected DFA9 dfa9 = new DFA9(this);
    protected DFA12 dfa12 = new DFA12(this);
    static final String DFA3_eotS =
        "\5\uffff";
    static final String DFA3_eofS =
        "\1\uffff\2\4\2\uffff";
    static final String DFA3_minS =
        "\1\24\2\6\2\uffff";
    static final String DFA3_maxS =
        "\1\24\2\25\2\uffff";
    static final String DFA3_acceptS =
        "\3\uffff\1\2\1\1";
    static final String DFA3_specialS =
        "\5\uffff}>";
    static final String[] DFA3_transitionS = {
            "\1\1",
            "\5\3\1\4\1\uffff\1\3\2\4\4\uffff\1\2\1\3",
            "\5\3\1\4\1\uffff\1\3\2\4\4\uffff\1\2\1\3",
            "",
            ""
    };

    static final short[] DFA3_eot = DFA.unpackEncodedString(DFA3_eotS);
    static final short[] DFA3_eof = DFA.unpackEncodedString(DFA3_eofS);
    static final char[] DFA3_min = DFA.unpackEncodedStringToUnsignedChars(DFA3_minS);
    static final char[] DFA3_max = DFA.unpackEncodedStringToUnsignedChars(DFA3_maxS);
    static final short[] DFA3_accept = DFA.unpackEncodedString(DFA3_acceptS);
    static final short[] DFA3_special = DFA.unpackEncodedString(DFA3_specialS);
    static final short[][] DFA3_transition;

    static {
        int numStates = DFA3_transitionS.length;
        DFA3_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA3_transition[i] = DFA.unpackEncodedString(DFA3_transitionS[i]);
        }
    }

    class DFA3 extends DFA {

        public DFA3(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 3;
            this.eot = DFA3_eot;
            this.eof = DFA3_eof;
            this.min = DFA3_min;
            this.max = DFA3_max;
            this.accept = DFA3_accept;
            this.special = DFA3_special;
            this.transition = DFA3_transition;
        }
        public String getDescription() {
            return "48:1: query : ( objquery | propquery );";
        }
    }
    static final String DFA4_eotS =
        "\5\uffff";
    static final String DFA4_eofS =
        "\1\uffff\1\2\1\uffff\1\2\1\uffff";
    static final String DFA4_minS =
        "\1\24\1\13\1\uffff\1\13\1\uffff";
    static final String DFA4_maxS =
        "\2\24\1\uffff\1\24\1\uffff";
    static final String DFA4_acceptS =
        "\2\uffff\1\2\1\uffff\1\1";
    static final String DFA4_specialS =
        "\5\uffff}>";
    static final String[] DFA4_transitionS = {
            "\1\1",
            "\1\4\2\uffff\2\2\4\uffff\1\3",
            "",
            "\1\4\2\uffff\2\2\4\uffff\1\3",
            ""
    };

    static final short[] DFA4_eot = DFA.unpackEncodedString(DFA4_eotS);
    static final short[] DFA4_eof = DFA.unpackEncodedString(DFA4_eofS);
    static final char[] DFA4_min = DFA.unpackEncodedStringToUnsignedChars(DFA4_minS);
    static final char[] DFA4_max = DFA.unpackEncodedStringToUnsignedChars(DFA4_maxS);
    static final short[] DFA4_accept = DFA.unpackEncodedString(DFA4_acceptS);
    static final short[] DFA4_special = DFA.unpackEncodedString(DFA4_specialS);
    static final short[][] DFA4_transition;

    static {
        int numStates = DFA4_transitionS.length;
        DFA4_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA4_transition[i] = DFA.unpackEncodedString(DFA4_transitionS[i]);
        }
    }

    class DFA4 extends DFA {

        public DFA4(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 4;
            this.eot = DFA4_eot;
            this.eof = DFA4_eof;
            this.min = DFA4_min;
            this.max = DFA4_max;
            this.accept = DFA4_accept;
            this.special = DFA4_special;
            this.transition = DFA4_transition;
        }
        public String getDescription() {
            return "53:1: objquery : ( name PREC proplist -> ^( name ^( PRECON proplist ) ) | name );";
        }
    }
    static final String DFA8_eotS =
        "\11\uffff";
    static final String DFA8_eofS =
        "\1\uffff\2\6\6\uffff";
    static final String DFA8_minS =
        "\1\24\2\6\6\uffff";
    static final String DFA8_maxS =
        "\1\24\2\25\6\uffff";
    static final String DFA8_acceptS =
        "\3\uffff\1\2\1\5\1\1\1\6\1\3\1\4";
    static final String DFA8_specialS =
        "\11\uffff}>";
    static final String[] DFA8_transitionS = {
            "\1\1",
            "\1\3\1\7\1\5\1\10\1\4\2\uffff\3\6\4\uffff\1\2\1\6",
            "\1\3\1\7\1\5\1\10\1\4\2\uffff\3\6\4\uffff\1\2\1\6",
            "",
            "",
            "",
            "",
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
            return "67:1: sent : ( name SIGE val -> ^( ASIGN name val ) | name SIGL val -> ^( COMPL name val ) | name SIGG val -> ^( COMPG name val ) | name SIGLE val -> ^( COMPLE name val ) | name SIGGE val -> ^( COMPGE name val ) | name );";
        }
    }
    static final String DFA9_eotS =
        "\17\uffff";
    static final String DFA9_eofS =
        "\1\uffff\1\11\1\uffff\1\11\6\uffff\5\11";
    static final String DFA9_minS =
        "\1\24\1\6\1\20\1\6\3\20\1\uffff\1\20\1\uffff\5\15";
    static final String DFA9_maxS =
        "\1\24\1\25\1\22\1\25\3\22\1\uffff\1\22\1\uffff\5\25";
    static final String DFA9_acceptS =
        "\7\uffff\1\1\1\uffff\1\2\5\uffff";
    static final String DFA9_specialS =
        "\17\uffff}>";
    static final String[] DFA9_transitionS = {
            "\1\1",
            "\1\4\1\6\1\2\1\10\1\5\2\uffff\3\11\4\uffff\1\3\1\7",
            "\3\12",
            "\1\4\1\6\1\2\1\10\1\5\2\uffff\3\11\4\uffff\1\3\1\7",
            "\3\13",
            "\3\14",
            "\3\15",
            "",
            "\3\16",
            "",
            "\3\11\5\uffff\1\7",
            "\3\11\5\uffff\1\7",
            "\3\11\5\uffff\1\7",
            "\3\11\5\uffff\1\7",
            "\3\11\5\uffff\1\7"
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
            return "75:1: proplist : ( sent DEL proplist | sent );";
        }
    }
    static final String DFA12_eotS =
        "\5\uffff";
    static final String DFA12_eofS =
        "\1\uffff\2\3\2\uffff";
    static final String DFA12_minS =
        "\3\24\2\uffff";
    static final String DFA12_maxS =
        "\1\24\2\27\2\uffff";
    static final String DFA12_acceptS =
        "\3\uffff\1\2\1\1";
    static final String DFA12_specialS =
        "\5\uffff}>";
    static final String[] DFA12_transitionS = {
            "\1\1",
            "\1\2\1\4\1\uffff\1\3",
            "\1\2\1\4\1\uffff\1\3",
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
            return "98:1: ordlist : ( name DEL ordlist | name );";
        }
    }


    public static final BitSet FOLLOW_selectquery_in_squery111 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_limiter_in_selectquery121 = new BitSet(new long[]{0x0000000000100000L});
    public static final BitSet FOLLOW_query_in_selectquery124 = new BitSet(new long[]{0x000000000000C000L});
    public static final BitSet FOLLOW_modifier_in_selectquery126 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_selectquery129 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NUM_in_limiter155 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_objquery_in_query172 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_propquery_in_query176 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_name_in_objquery187 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_PREC_in_objquery189 = new BitSet(new long[]{0x0000000000100000L});
    public static final BitSet FOLLOW_proplist_in_objquery191 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_name_in_objquery207 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_proplist_in_propquery218 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_PRED_in_propquery220 = new BitSet(new long[]{0x0000000000100000L});
    public static final BitSet FOLLOW_name_in_propquery222 = new BitSet(new long[]{0x0000000000000802L});
    public static final BitSet FOLLOW_PREC_in_propquery225 = new BitSet(new long[]{0x0000000000100000L});
    public static final BitSet FOLLOW_proplist_in_propquery229 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_VAR_in_name263 = new BitSet(new long[]{0x0000000000100000L});
    public static final BitSet FOLLOW_VAR_in_name267 = new BitSet(new long[]{0x0000000000100002L});
    public static final BitSet FOLLOW_VAR_in_name284 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_name_in_sent293 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_SIGE_in_sent295 = new BitSet(new long[]{0x0000000000070000L});
    public static final BitSet FOLLOW_val_in_sent297 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_name_in_sent311 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_SIGL_in_sent313 = new BitSet(new long[]{0x0000000000070000L});
    public static final BitSet FOLLOW_val_in_sent315 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_name_in_sent329 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_SIGG_in_sent331 = new BitSet(new long[]{0x0000000000070000L});
    public static final BitSet FOLLOW_val_in_sent333 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_name_in_sent347 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_SIGLE_in_sent349 = new BitSet(new long[]{0x0000000000070000L});
    public static final BitSet FOLLOW_val_in_sent351 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_name_in_sent365 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_SIGGE_in_sent367 = new BitSet(new long[]{0x0000000000070000L});
    public static final BitSet FOLLOW_val_in_sent369 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_name_in_sent383 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_sent_in_proplist391 = new BitSet(new long[]{0x0000000000200000L});
    public static final BitSet FOLLOW_DEL_in_proplist393 = new BitSet(new long[]{0x0000000000100000L});
    public static final BitSet FOLLOW_proplist_in_proplist396 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_sent_in_proplist400 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_val0 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ordterm_in_modifier427 = new BitSet(new long[]{0x0000000000004002L});
    public static final BitSet FOLLOW_offsetterm_in_modifier430 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_MODE_in_offsetterm440 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_NUM_in_offsetterm442 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_MODO_in_ordterm459 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_LPAR_in_ordterm461 = new BitSet(new long[]{0x0000000000100000L});
    public static final BitSet FOLLOW_ordlist_in_ordterm463 = new BitSet(new long[]{0x0000000000800000L});
    public static final BitSet FOLLOW_RPAR_in_ordterm465 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_name_in_ordlist482 = new BitSet(new long[]{0x0000000000200000L});
    public static final BitSet FOLLOW_DEL_in_ordlist484 = new BitSet(new long[]{0x0000000000100000L});
    public static final BitSet FOLLOW_ordlist_in_ordlist487 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_name_in_ordlist491 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_modifier_in_synpred2_sParser126 = new BitSet(new long[]{0x0000000000000002L});

}