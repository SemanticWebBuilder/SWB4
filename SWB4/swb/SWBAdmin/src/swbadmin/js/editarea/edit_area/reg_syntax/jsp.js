editAreaLoader.load_syntax["jsp"] = {
	'COMMENT_SINGLE' : {1 : '//'}
	,'COMMENT_MULTI' : {'<!--' : '-->', '/*' : '*/'}
	,'QUOTEMARKS' : {1: "'", 2: '"'}
	,'KEYWORD_CASE_SENSITIVE' : true
	,'KEYWORDS' : {
		'constants' : [
			'null', 'false', 'true'
		]
		,'types' : [
			'boolean', 'byte', 'char', 'double', 'float',
                        'int', 'long', 'short', 'enum'
		]
		,'statements' : [
			'break', 'case', 'continue', 'default', 'do', 'else',
                        'for', 'goto', 'if', 'sizeof', 'switch', 'while'
		]
 		,'keywords' : [
			'catch', 'const', 'final', 'finally', 'import', 
                        'instanceof', 'new', 'package', 'private', 'protected',
                        'public', 'return', 'static', 'super', 'synchronized',
                        'this', 'throw', 'throws', 'try', 'assert'
		]
		,'objects' : [
			'request', 'response', 'session', 'out'
		]
	}
	,'OPERATORS' :[
		'+', '-', '/', '*', '=', '<', '>', '%', '!', '?', ':', '&', '|'
	]
	,'DELIMITERS' :[
		'(', ')', '[', ']', '{', '}'
	]
	,'REGEXPS' : {
		'precompiler' : {
			'search' : '()(#[^\r\n]*)()'
			,'class' : 'precompiler'
			,'modifiers' : 'g'
			,'execute' : 'before'
		}
		,'directives' : {
			'search' : '(<%@)([a-z][^ \r\n\t>]*)([^(%>)]*(%>))'
			,'class' : 'scripts'
			,'modifiers' : 'gi'
			,'execute' : 'before' // before or after
		}
		,'scriptlets' : {
			'search' : '(<%=)([a-z][^ \r\n\t>]*)([^(>)]*(%>))'
			,'class' : 'scriptlets'
			,'modifiers' : 'gi'
			,'execute' : 'before' // before or after
		}
		,'scripts' : {
			'search' : '(<%)([a-z][^ \r\n\t>]*)([^(>)]*(%>))'
			,'class' : 'scripts'
			,'modifiers' : 'gi'
			,'execute' : 'before' // before or after
		}
		,'tags' : {
			'search' : '(<)(/?[a-z][^ \r\n\t>]*)([^>]*>)'
			,'class' : 'tags'
			,'modifiers' : 'gi'
			,'execute' : 'before' // before or after
		}
		,'attributes' : {
			'search' : '( |\n|\r|\t)([^ \r\n\t=]+)(=)'
			,'class' : 'attributes'
			,'modifiers' : 'g'
			,'execute' : 'before' // before or after
		}
/*		,'precompilerstring' : {
			'search' : '(#[\t ]*include[\t ]*)([^\r\n]*)([^\r\n]*[\r\n])'
			,'class' : 'precompilerstring'
			,'modifiers' : 'g'
			,'execute' : 'before'
		}*/
	}
	,'STYLES' : {
		'COMMENTS': 'color: #CCCCCC;'
		,'QUOTESMARKS': 'color: #6381F8;'
		,'KEYWORDS' : {
			'constants' : 'color: #9933FF;'
			,'types' : 'color: #996633;'
			,'statements' : 'color: #60CA00;'
			,'keywords' : 'color: #3333FF;'
                        ,'objects' : 'color: #22BB22; font-weight:bold'
		}
		,'OPERATORS' : 'color: #FF00FF;'
		,'DELIMITERS' : 'color: #FF9933;'
		,'REGEXPS' : {
			'precompiler' : 'color: #009900;'
			,'attributes': 'color: #B1AC41;'
			,'tags': 'color: #E62253;'
			,'scripts': 'color: #862253;'
			,'precompilerstring' : 'color: #994400;'
		}
	}
};
