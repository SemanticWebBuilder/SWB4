lexer grammar EnglishLexer;

WHITESPACE
	:	(' '|'\t'|'\f'|'\n'|'\r'){ $channel=HIDDEN; };
SIGN	:	'+' | '-';
SIGL	:	'<';
SIGG	:	'>';
SIGE	:	'=';
SIGLE	:	'<=';
SIGGE	:	'>=';
PREC	:	'with';
MODT	:	'all';
PRED	:	'of';
PREN	:	'in';
MODE	:	'from';
MODO	:	'order'|'ordered';
MODC   	:    	'like';
BOL	:	'true'|'false';
NUM	:	SIGN? ('0'..'9'+ | ('0'..'9'* '.' '0'..'9'+));
LIT	:	'"'(~('"'|'\n'|'\r'|'\t'))*'"';
BVAR	:	LBRK(~(']'|'['|'\n'|'\r'|'\t'))*RBRK;
ORDOP	:	'asc'|'des';
VAR	:	('0'..'9'|'a'..'z'|'A'..'Z'|'_'|'-')+;
LPAR	:	'(';
RPAR	:	')';
LBRK	:	'[';
RBRK	:	']';
DEL	:	',';
SIGI	:	'\?';