parser grammar sParser;

options {
	backtrack = true;
	tokenVocab = sLexer;
	output = AST;
}

tokens {
	LIMIT; SELECT; MOD; OBJLIST; PROPLIST; ASIGN; SENTENCE; COMPL; COMPG; COMPLE; COMPGE;
	PRECON; PREDE; OFFSET; ORDER; COMPNAME; ASKQUERY; MODTO; NAME;
}

@header {
	import java.util.ArrayList;
	import java.util.Iterator;
}

@members {
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
}

//Rule for Query ::= Prologue (SelectQuery | ConstructQuery | DescribeQuery | AskQuery)
squery
:	selectquery
;

//Rule for SelectQuery ::= 'SELECT' ('DISTINCT' | 'REDUCED')? (Var+ | '*') DataSetClause* WhereClause SolutionModifier
//DISTINCT, REDUCED and DataSetClause nonterminals are ignored.
//SolutionModifier is splitted in limiter and modifier, WhereClause is replaced by query
selectquery
:	limiter? query modifier? EOF -> ^(SELECT limiter? query modifier?)
;

//Rule for LimitClause ::= 'LIMIT' INTEGER
limiter
:	NUM -> ^(LIMIT NUM)
;

//Rule for WhereClause as an object with properties or a list of
//properties for an object (with properties).
query
:	objquery
	|propquery {isCompound = true;}
;

//Subrule for WhereClause for an object with properties
objquery
:	name PREC proplist -> ^(name ^(PRECON proplist))
	|name
;

//Subrule for WhereClause for a list of properties of an object (with properties)
propquery
:
	|v1=proplist PRED name (PREC v2=proplist)? -> ^(name ^(PREDE $v1) ^(PRECON $v2?))
;

//Rule for VARNAME, a single identifier or a composed identifier
name
:	v1=VAR v2=VAR+ -> ^(COMPNAME $v1 $v2)
	|LBRK v1=VAR v2=VAR+ RBRK -> ^(COMPNAME $v1 $v2)
	|VAR
;

//Rule for a sentence (NAME | NAME operator VALUE)
sent
:	name SIGE val -> ^(ASIGN name val)
	|name SIGL val -> ^(COMPL name val)//pendiente para filters
	|name SIGG val -> ^(COMPG name val)//pendiente para filters
	|name SIGLE val -> ^(COMPLE name val)//pendiente para filters
	|name SIGGE val -> ^(COMPGE name val)//pendiente para filters
	|name
;

//Subrule for objquery or propquery, a list of sentences separated by commas
proplist
:	sent DEL! proplist
	|sent
;

//A primitive type (NUM = integer, float, LIT = String surrounded by quotes, BOL = boolean)
val
: 	NUM
	|LIT
	|BOL
;

//Rule for SolutionModifier ::= OrderClause? LimitOffsetClauses?
modifier
:	ordterm? offsetterm?
;

//Rule for OffsetClause ::= 'OFFSET' INTEGER
offsetterm
:	MODE NUM -> ^(OFFSET NUM)
;

//Rule for OrderClause ::= 'ORDER' 'BY' OrderCondition+
ordterm
:	MODO LPAR ordlist RPAR -> ^(ORDER ordlist)
;

//Rule for OrderCondition ::= (('ASC' | 'DESC') BracketedExpression) | (Constraint | Var)
//Only Var is considered, and the ordering is descendent by default
ordlist
:	name DEL! ordlist
	|name
;