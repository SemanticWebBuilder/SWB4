lexer grammar SimpleLexer;

WHITESPACE
	:	(' '|'\t'|'\f'|'\n'|'\r'){ $channel=HIDDEN; };
SIGN	:	'+' | '-';
SIGL	:	'<';
SIGG	:	'>';
SIGE	:	'=';
SIGLE	:	'<=';
SIGGE	:	'>=';
PREC	:	'CON' | 'Con' | 'con';
MODT	:	'TODO' | 'Todo' | 'todo';
PRED	:	'DE' | 'De' | 'de';
MODE	:	'DESDE' | 'Desde' | 'desde';
MODO	:	'ORDENAR' | 'Ordenar' | 'ordenar';
MODC    :       'COMO' | 'Como' | 'como';
BOL	:	'true' | 'TRUE' | 'false' | 'FALSE' | 'True' | 'False';
NUM	:	SIGN? ('0'..'9'+ | ('0'..'9'* '.' '0'..'9'+));
LIT	:	'"'(~('"'|'\n'|'\r'|'\t'))*'"';
BVAR	:	LBRK(~(']'|'['|'\n'|'\r'|'\t'))*RBRK;
ORDOP	:	'ASC'|'DES';
VAR	:	('0'..'9'|'a'..'z'|'A'..'Z'|'_'|'-'|'á'|'é'|'í'|'ó'|'ú'|'\u00C1'|'É'|'Í'|'Ó'|'Ú'|'Ñ'|'ñ')+;
LPAR	:	'(';
RPAR	:	')';
LBRK	:	'[';
RBRK	:	']';
DEL	:	',';
SIGI	:	'\?';