parser grammar ComplexParser;

options {
	backtrack = true;
	tokenVocab = SpanishLexer;
	output = AST;
}

tokens {
	LIMIT; SELECT; ASIGN; COMPL; COMPG; COMPLE; COMPGE; COMPAS;
	PRECON; PREDE; OFFSET; ORDER; COMPNAME; MODTO; NAME; COMPRNG;
	INTERVAL;DEFINE;
}

@members {
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
}

/*Main query, it can be an object query or a properties query or a definition query*/
squery
:	dquery EOF -> ^(SELECT ^(dquery))
	|limiter? oquery modifier? EOF -> ^(SELECT limiter? oquery modifier?)
	|limiter? pquery modifier? EOF -> ^(SELECT limiter? pquery modifier?)
;

/*A describe query are the words 'qué es' and a name enclosed in quotation marks*/
dquery
:	OQM? MODD name CQM -> ^(DEFINE name)
;

/*A limiter is a number*/
limiter
:	NUM -> ^(LIMIT NUM)
;

/* A modifier can be an order clause followed by an optional offsetclause or a single
 * offsetterm.
 */
modifier
:	ordterm offsetterm?
	|offsetterm
;

/*An offset term is the keyword 'desde' followed by a num.*/
offsetterm
:	MODE NUM -> ^(OFFSET NUM)
;

/*An ordterm is a list of names enclosed in parenthesis.*/
ordterm
:	MODO LPAR plist RPAR -> ^(ORDER plist)
;

/* An object query can be a sentence, a name or an object with a list of
 * nested properties (querylist). Parnthesis are considered for delimiting
 * an object query.
 */
oquery
:	//sent
	|name
	|name PREC querylist {precon = true;} -> ^(name ^(PRECON querylist))
	|LPAR! oquery RPAR!
;

/*A query list or nested query can be a list of object queries or a single object query */
querylist
:	oquery DEL! querylist
	|sent DEL! querylist
	|oquery
	|sent
;

/*A properties query is a list of properties of an object. The object could have more properties.*/
pquery
:	plist PRED oquery {prede = true;} -> ^(oquery ^(PREDE plist))
	|MODT PRED oquery {prede = true;} -> ^(oquery ^(PREDE MODTO))
;

/*A properties list is a list of names or a single name.*/
plist
:	name DEL! plist
	|name
;

/* A name is a sequence of literals or a single literal*/
name
:	BVAR
	|VAR
;

/*A sentence is a name followed by a sign and a value*/
sent
:	name SIGE val -> ^(ASIGN name val)
	|VAR SIGL val -> ^(COMPL VAR val)
	|name SIGL val -> ^(COMPL name val)
	|VAR SIGG val -> ^(COMPG VAR val)
	|name SIGG val -> ^(COMPG name val)
	|VAR SIGLE val -> ^(COMPLE VAR val)
	|name SIGLE val -> ^(COMPLE name val)
	|VAR SIGGE val -> ^(COMPGE VAR val)
	|name SIGGE val -> ^(COMPGE name val)
        |VAR MODC val -> ^(COMPAS VAR val)
        |name MODC val -> ^(COMPAS name val)
        |name MODN val DELY val VAR? -> ^(COMPRNG name ^(INTERVAL val val))
;

/*A value can be a literal (double quoted strings), a boolean or a num*/
val
:	LIT
	|BOL
	|NUM
;