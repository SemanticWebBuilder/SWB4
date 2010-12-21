<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="html" version="1.0" encoding="ISO-8859-1" indent="yes"/>
    <xsl:template match="/admresource">
        <div class="swbform">
            <xsl:apply-templates />
        </div>
    </xsl:template>
    <xsl:template match="statictext">
        <li>
            <xsl:value-of select="." disable-output-escaping="yes"/>
        </li>
    </xsl:template>
    <xsl:template match="wbmsg">
        <li>
            <xsl:value-of select="." disable-output-escaping="yes"/>
        </li>
    </xsl:template>
    <xsl:template match="script">
	<xsl:text disable-output-escaping="yes">&lt;</xsl:text><xsl:value-of select="name()" />
	<xsl:if test="string-length( @language ) &gt; 0"> language=&quot;<xsl:value-of select="@language" />&quot;</xsl:if>
	<xsl:if test="string-length( @src ) &gt; 0"> src=&quot;<xsl:value-of select="@src" />&quot;</xsl:if>
	<xsl:text disable-output-escaping="yes"> type="text/javascript" &gt;</xsl:text>
            <xsl:value-of select="." disable-output-escaping="yes"/>
	<xsl:text disable-output-escaping="yes">&lt;/</xsl:text><xsl:value-of select="name()" /><xsl:text disable-output-escaping="yes">&gt;</xsl:text>
    </xsl:template>
    <xsl:template match="form">
	<xsl:text disable-output-escaping="yes">&lt;</xsl:text><xsl:value-of select="name()" />
	<xsl:if test="string-length( @id     ) &gt; 0"> id=&quot;<xsl:value-of select="@id" />&quot;</xsl:if>
	<xsl:if test="string-length( @name   ) &gt; 0"> name=&quot;<xsl:value-of select="@name" />&quot;</xsl:if>
	<xsl:if test="string-length( @title  ) &gt; 0"> title=&quot;<xsl:value-of select="@title" />&quot;</xsl:if>
        <xsl:if test="string-length( @dojoType ) &gt; 0"> dojoType=&quot;<xsl:value-of select="@dojoType" />&quot;</xsl:if>
	<xsl:if test="string-length( @method ) &gt; 0"> method=&quot;<xsl:value-of select="@method" />&quot;</xsl:if>
	<xsl:if test="string-length( @enctype) &gt; 0"> enctype=&quot;<xsl:value-of select="@enctype" />&quot;</xsl:if>
	<xsl:if test="string-length( @action ) &gt; 0"> action=&quot;<xsl:value-of select="@action" />&quot;</xsl:if>
	<xsl:if test="string-length( @accept ) &gt; 0"> accept=&quot;<xsl:value-of select="@accept" />&quot;</xsl:if>
	<xsl:if test="string-length( @accept-charset) &gt; 0"> accept-charset=&quot;<xsl:value-of select="@accept-charset" />&quot;</xsl:if>
	<xsl:if test="string-length( @lang   ) &gt; 0"> lang=&quot;<xsl:value-of select="@lang" />&quot;</xsl:if>
	<xsl:if test="@dir = 'ltr' or @dir = 'rtl'"> dir=&quot;<xsl:value-of select="@dir" />&quot;</xsl:if>
	<xsl:if test="string-length( @class  ) &gt; 0"> class=&quot;<xsl:value-of select="@class" />&quot;</xsl:if>
	<xsl:if test="string-length( @style  ) &gt; 0"> style=&quot;<xsl:value-of select="@style" />&quot;</xsl:if>
	<xsl:if test="string-length( @onclick    ) &gt; 0"> onclick=&quot;<xsl:value-of select="@onclick" />&quot;</xsl:if>
	<xsl:if test="string-length( @ondblclick ) &gt; 0"> ondblclick=&quot;<xsl:value-of select="@ondblclick" />&quot;</xsl:if>
	<xsl:if test="string-length( @onmousedown) &gt; 0"> onmousedown=&quot;<xsl:value-of select="@onmousedown" />&quot;</xsl:if>
	<xsl:if test="string-length( @onmouseup  ) &gt; 0"> onmouseup=&quot;<xsl:value-of select="@onmouseup" />&quot;</xsl:if>
	<xsl:if test="string-length( @onmouseover) &gt; 0"> onmouseover=&quot;<xsl:value-of select="@onmouseover" />&quot;</xsl:if>
	<xsl:if test="string-length( @onmousemove) &gt; 0"> onmousemove=&quot;<xsl:value-of select="@onmousemove" />&quot;</xsl:if>
	<xsl:if test="string-length( @onmouseout ) &gt; 0"> onmouseout=&quot;<xsl:value-of select="@onmouseout" />&quot;</xsl:if>
	<xsl:if test="string-length( @onkeypress ) &gt; 0"> onkeypress=&quot;<xsl:value-of select="@onkeypress" />&quot;</xsl:if>
	<xsl:if test="string-length( @onkeydown  ) &gt; 0"> onkeydown=&quot;<xsl:value-of select="@onkeydown" />&quot;</xsl:if>
	<xsl:if test="string-length( @onkeyup    ) &gt; 0"> onkeyup=&quot;<xsl:value-of select="@onkeyup" />&quot;</xsl:if>
	<xsl:if test="string-length( @onsubmit   ) &gt; 0"> onsubmit=&quot;<xsl:value-of select="@onsubmit" />&quot;</xsl:if>
	<xsl:if test="string-length( @onreset    ) &gt; 0"> onreset=&quot;<xsl:value-of select="@onreset" />&quot;</xsl:if>
	<xsl:if test="string-length( @moreattr) &gt; 0"><xsl:text> </xsl:text><xsl:value-of select="@moreattr" /></xsl:if>
        <xsl:text disable-output-escaping="yes">&gt;</xsl:text>
            <xsl:apply-templates />
	<xsl:text disable-output-escaping="yes">&lt;/</xsl:text><xsl:value-of select="name()" /><xsl:text disable-output-escaping="yes">&gt;</xsl:text>
    </xsl:template>
    
    <xsl:template match="div">
        <xsl:text disable-output-escaping="yes">&lt;</xsl:text><xsl:value-of select="name()" />
        <xsl:if test="string-length( @title  ) &gt; 0"> title=&quot;<xsl:value-of select="@title" />&quot;</xsl:if>
        <xsl:if test="string-length( @class  ) &gt; 0"> class=&quot;<xsl:value-of select="@class" />&quot;</xsl:if>
        <xsl:if test="string-length( @open ) &gt; 0"> open=&quot;<xsl:value-of select="@open" />&quot;</xsl:if>
        <xsl:text disable-output-escaping="yes"> dojoType=&quot;dijit.TitlePane&quot; duration=&quot;150&quot; minSize_=&quot;20&quot; splitter_=&quot;true&quot; region=&quot;bottom&quot; &gt;</xsl:text>
            <xsl:apply-templates />
        <xsl:text disable-output-escaping="yes">&lt;/</xsl:text><xsl:value-of select="name()" /><xsl:text disable-output-escaping="yes">&gt;</xsl:text>
    </xsl:template>

    <xsl:template match="fieldset">
        <xsl:text disable-output-escaping="yes">&lt;</xsl:text><xsl:value-of select="name()" />
    	<xsl:if test="string-length( @id     ) &gt; 0"> id=&quot;<xsl:value-of select="@id" />&quot;</xsl:if>
    	<xsl:if test="string-length( @name   ) &gt; 0"> name=&quot;<xsl:value-of select="@name" />&quot;</xsl:if>
    	<xsl:if test="string-length( @title  ) &gt; 0"> title=&quot;<xsl:value-of select="@title" />&quot;</xsl:if>
    	<xsl:if test="string-length( @method ) &gt; 0"> method=&quot;<xsl:value-of select="@method" />&quot;</xsl:if>
    	<xsl:if test="string-length( @enctype) &gt; 0"> enctype=&quot;<xsl:value-of select="@enctype" />&quot;</xsl:if>
    	<xsl:if test="string-length( @action ) &gt; 0"> action=&quot;<xsl:value-of select="@action" />&quot;</xsl:if>
    	<xsl:if test="string-length( @accept ) &gt; 0"> accept=&quot;<xsl:value-of select="@accept" />&quot;</xsl:if>
    	<xsl:if test="string-length( @accept-charset) &gt; 0"> accept-charset=&quot;<xsl:value-of select="@accept-charset" />&quot;</xsl:if>
    	<xsl:if test="string-length( @lang   ) &gt; 0"> lang=&quot;<xsl:value-of select="@lang" />&quot;</xsl:if>
    	<xsl:if test="@dir = 'ltr' or @dir = 'rtl'"> dir=&quot;<xsl:value-of select="@dir" />&quot;</xsl:if>
    	<xsl:if test="string-length( @class  ) &gt; 0"> class=&quot;<xsl:value-of select="@class" />&quot;</xsl:if>
    	<xsl:if test="string-length( @style  ) &gt; 0"> style=&quot;<xsl:value-of select="@style" />&quot;</xsl:if>
    	<xsl:if test="string-length( @onclick    ) &gt; 0"> onclick=&quot;<xsl:value-of select="@onclick" />&quot;</xsl:if>
    	<xsl:if test="string-length( @ondblclick ) &gt; 0"> ondblclick=&quot;<xsl:value-of select="@ondblclick" />&quot;</xsl:if>
    	<xsl:if test="string-length( @onmousedown) &gt; 0"> onmousedown=&quot;<xsl:value-of select="@onmousedown" />&quot;</xsl:if>
    	<xsl:if test="string-length( @onmouseup  ) &gt; 0"> onmouseup=&quot;<xsl:value-of select="@onmouseup" />&quot;</xsl:if>
    	<xsl:if test="string-length( @onmouseover) &gt; 0"> onmouseover=&quot;<xsl:value-of select="@onmouseover" />&quot;</xsl:if>
    	<xsl:if test="string-length( @onmousemove) &gt; 0"> onmousemove=&quot;<xsl:value-of select="@onmousemove" />&quot;</xsl:if>
    	<xsl:if test="string-length( @onmouseout ) &gt; 0"> onmouseout=&quot;<xsl:value-of select="@onmouseout" />&quot;</xsl:if>
    	<xsl:if test="string-length( @onkeypress ) &gt; 0"> onkeypress=&quot;<xsl:value-of select="@onkeypress" />&quot;</xsl:if>
    	<xsl:if test="string-length( @onkeydown  ) &gt; 0"> onkeydown=&quot;<xsl:value-of select="@onkeydown" />&quot;</xsl:if>
    	<xsl:if test="string-length( @onkeyup    ) &gt; 0"> onkeyup=&quot;<xsl:value-of select="@onkeyup" />&quot;</xsl:if>
    	<xsl:if test="string-length( @onsubmit   ) &gt; 0"> onsubmit=&quot;<xsl:value-of select="@onsubmit" />&quot;</xsl:if>
    	<xsl:if test="string-length( @onreset    ) &gt; 0"> onreset=&quot;<xsl:value-of select="@onreset" />&quot;</xsl:if>
    	<xsl:if test="string-length( @moreattr) &gt; 0"><xsl:text> </xsl:text><xsl:value-of select="@moreattr" /></xsl:if>
    	<xsl:text disable-output-escaping="yes">&gt;</xsl:text>
    	<xsl:if test="string-length( @legend   ) &gt; 0">
            <xsl:text disable-output-escaping="yes">&lt;</xsl:text>legend<xsl:text disable-output-escaping="yes">&gt;</xsl:text>
            <xsl:value-of select="@legend" />
            <xsl:text disable-output-escaping="yes">&lt;</xsl:text>/legend<xsl:text disable-output-escaping="yes">&gt;</xsl:text>
    	</xsl:if>
        <ul class="swbform-ul">
            <xsl:apply-templates />
        </ul>
    	<xsl:text disable-output-escaping="yes">&lt;/</xsl:text><xsl:value-of select="name()" /><xsl:text disable-output-escaping="yes">&gt;</xsl:text>
    </xsl:template>
    <xsl:template match="label">
        <li class="swbform-li">
            <label for="" class="swbform-label">
                <xsl:value-of select="node()" disable-output-escaping="yes" />
            </label>
            <xsl:if test="string-length( @required ) &gt; 0">
                <xsl:text disable-output-escaping="yes">&lt;</xsl:text>font color="RED"<xsl:text disable-output-escaping="yes">&gt;</xsl:text>
                    <xsl:value-of select="@required" />
                <xsl:text disable-output-escaping="yes">&lt;</xsl:text>/font<xsl:text disable-output-escaping="yes">&gt;</xsl:text>
            </xsl:if>
            <xsl:apply-templates select="./*" />
        </li>
    </xsl:template>
    <xsl:template match="legend">
        <legend><xsl:value-of select="node()" disable-output-escaping="yes" /></legend>
    </xsl:template>
    <xsl:template match="input[@type = 'submit' or @type = 'reset']">
        <xsl:if test="@type = 'submit'">
            <xsl:text disable-output-escaping="yes">&lt;</xsl:text>li class="swbform-li"<xsl:text disable-output-escaping="yes">&gt;</xsl:text>
        </xsl:if>
        <xsl:text disable-output-escaping="yes">&lt;</xsl:text><xsl:value-of select="name()" /> type=&quot;<xsl:value-of select="@type" />&quot;
        <xsl:if test="string-length( @id    ) &gt; 0"> id=&quot;<xsl:value-of select="@id" />&quot;</xsl:if>
        <xsl:if test="string-length( @name  ) &gt; 0"> name=&quot;<xsl:value-of select="@name" />&quot;</xsl:if>
        <xsl:if test="string-length( @title ) &gt; 0"> title=&quot;<xsl:value-of select="@title" />&quot;</xsl:if>
        <xsl:if test="string-length( @value ) &gt; 0"> value=&quot;&amp;nbsp;&amp;nbsp;<xsl:value-of select="@value" />&amp;nbsp;&amp;nbsp;&quot;</xsl:if>
        <xsl:if test="string-length( @dojoType ) &gt; 0"> dojoType=&quot;<xsl:value-of select="@dojoType" />&quot;</xsl:if>
        <xsl:choose>
            <xsl:when test="string-length(@class)&gt;0"> class=&quot;<xsl:value-of select="@class" />&quot;</xsl:when>
            <xsl:otherwise> class=&quot;boton&quot;</xsl:otherwise>
        </xsl:choose>
        <xsl:if test="string-length( @style ) &gt; 0"> style=&quot;<xsl:value-of select="@style" />&quot;</xsl:if>
        <xsl:if test="string-length( @tabindex  ) &gt; 0"> tabindex=&quot;<xsl:value-of select="@tabindex" />&quot;</xsl:if>
        <xsl:if test="string-length( @accesskey ) &gt; 0"> accesskey=&quot;<xsl:value-of select="@accesskey" />&quot;</xsl:if>
        <xsl:if test="@disabled = 'true'"> disabled</xsl:if>
        <xsl:if test="@readonly = 'true'"> readonly</xsl:if>
        <xsl:if test="string-length( @onclick    ) &gt; 0"> onclick=&quot;<xsl:value-of select="@onclick" />&quot;</xsl:if>
        <xsl:if test="string-length( @ondblclick ) &gt; 0"> ondblclick=&quot;<xsl:value-of select="@ondblclick" />&quot;</xsl:if>
        <xsl:if test="string-length( @onmousedown) &gt; 0"> onmousedown=&quot;<xsl:value-of select="@onmousedown" />&quot;</xsl:if>
        <xsl:if test="string-length( @onmouseup  ) &gt; 0"> onmouseup=&quot;<xsl:value-of select="@onmouseup" />&quot;</xsl:if>
        <xsl:if test="string-length( @onmouseover) &gt; 0"> onmouseover=&quot;<xsl:value-of select="@onmouseover" />&quot;</xsl:if>
        <xsl:if test="string-length( @onmousemove) &gt; 0"> onmousemove=&quot;<xsl:value-of select="@onmousemove" />&quot;</xsl:if>
        <xsl:if test="string-length( @onmouseout ) &gt; 0"> onmouseout=&quot;<xsl:value-of select="@onmouseout" />&quot;</xsl:if>
        <xsl:if test="string-length( @onkeypress ) &gt; 0"> onkeypress=&quot;<xsl:value-of select="@onkeypress" />&quot;</xsl:if>
        <xsl:if test="string-length( @onkeydown  ) &gt; 0"> onkeydown=&quot;<xsl:value-of select="@onkeydown" />&quot;</xsl:if>
        <xsl:if test="string-length( @onkeyup    ) &gt; 0"> onkeyup=&quot;<xsl:value-of select="@onkeyup" />&quot;</xsl:if>
        <xsl:if test="string-length( @onfocus    ) &gt; 0"> onfocus=&quot;<xsl:value-of select="@onfocus" />&quot;</xsl:if>
        <xsl:if test="string-length( @onblur     ) &gt; 0"> onblur=&quot;<xsl:value-of select="@onblur" />&quot;</xsl:if>
        <xsl:if test="string-length( @onselect   ) &gt; 0"> onselect=&quot;<xsl:value-of select="@onselect" />&quot;</xsl:if>
        <xsl:if test="string-length( @onchange   ) &gt; 0"> onchange=&quot;<xsl:value-of select="@onchange" />&quot;</xsl:if>
        <xsl:if test="string-length( @moreattr) &gt; 0"><xsl:text> </xsl:text><xsl:value-of select="@moreattr" /></xsl:if>
        <xsl:text disable-output-escaping="yes">/&gt;</xsl:text>
        <xsl:if test="@type = 'reset'">
            <xsl:text disable-output-escaping="yes">&lt;</xsl:text>/li<xsl:text disable-output-escaping="yes">&gt;</xsl:text>
        </xsl:if>
    </xsl:template>
    <xsl:template match="button[@type = 'submit' or @type = 'reset']">
        <xsl:if test="@type = 'submit'">
            <xsl:text disable-output-escaping="yes">&lt;</xsl:text>li class="swbform-li"<xsl:text disable-output-escaping="yes">&gt;</xsl:text>
        </xsl:if>
        <xsl:text disable-output-escaping="yes">&lt;</xsl:text><xsl:value-of select="name()" /> type=&quot;<xsl:value-of select="@type" />&quot;
        <xsl:if test="string-length( @id    ) &gt; 0"> id=&quot;<xsl:value-of select="@id" />&quot;</xsl:if>
        <xsl:if test="string-length( @name  ) &gt; 0"> name=&quot;<xsl:value-of select="@name" />&quot;</xsl:if>
        <xsl:if test="string-length( @title ) &gt; 0"> title=&quot;<xsl:value-of select="@title" />&quot;</xsl:if>
        <xsl:if test="string-length( @value ) &gt; 0"> value=&quot;&amp;nbsp;&amp;nbsp;<xsl:value-of select="@value" />&amp;nbsp;&amp;nbsp;&quot;</xsl:if>
        <xsl:if test="string-length( @dojoType ) &gt; 0"> dojoType=&quot;<xsl:value-of select="@dojoType" />&quot;</xsl:if>
        <xsl:choose>
            <xsl:when test="string-length(@class)&gt;0"> class=&quot;<xsl:value-of select="@class" />&quot;</xsl:when>
            <xsl:otherwise> class=&quot;boton&quot;</xsl:otherwise>
        </xsl:choose>
        <xsl:if test="string-length( @style ) &gt; 0"> style=&quot;<xsl:value-of select="@style" />&quot;</xsl:if>
        <xsl:if test="string-length( @tabindex  ) &gt; 0"> tabindex=&quot;<xsl:value-of select="@tabindex" />&quot;</xsl:if>
        <xsl:if test="string-length( @accesskey ) &gt; 0"> accesskey=&quot;<xsl:value-of select="@accesskey" />&quot;</xsl:if>
        <xsl:if test="@disabled = 'true'"> disabled</xsl:if>
        <xsl:if test="@readonly = 'true'"> readonly</xsl:if>
        <xsl:if test="string-length( @onclick    ) &gt; 0"> onclick=&quot;<xsl:value-of select="@onclick" />&quot;</xsl:if>
        <xsl:if test="string-length( @ondblclick ) &gt; 0"> ondblclick=&quot;<xsl:value-of select="@ondblclick" />&quot;</xsl:if>
        <xsl:if test="string-length( @onmousedown) &gt; 0"> onmousedown=&quot;<xsl:value-of select="@onmousedown" />&quot;</xsl:if>
        <xsl:if test="string-length( @onmouseup  ) &gt; 0"> onmouseup=&quot;<xsl:value-of select="@onmouseup" />&quot;</xsl:if>
        <xsl:if test="string-length( @onmouseover) &gt; 0"> onmouseover=&quot;<xsl:value-of select="@onmouseover" />&quot;</xsl:if>
        <xsl:if test="string-length( @onmousemove) &gt; 0"> onmousemove=&quot;<xsl:value-of select="@onmousemove" />&quot;</xsl:if>
        <xsl:if test="string-length( @onmouseout ) &gt; 0"> onmouseout=&quot;<xsl:value-of select="@onmouseout" />&quot;</xsl:if>
        <xsl:if test="string-length( @onkeypress ) &gt; 0"> onkeypress=&quot;<xsl:value-of select="@onkeypress" />&quot;</xsl:if>
        <xsl:if test="string-length( @onkeydown  ) &gt; 0"> onkeydown=&quot;<xsl:value-of select="@onkeydown" />&quot;</xsl:if>
        <xsl:if test="string-length( @onkeyup    ) &gt; 0"> onkeyup=&quot;<xsl:value-of select="@onkeyup" />&quot;</xsl:if>
        <xsl:if test="string-length( @onfocus    ) &gt; 0"> onfocus=&quot;<xsl:value-of select="@onfocus" />&quot;</xsl:if>
        <xsl:if test="string-length( @onblur     ) &gt; 0"> onblur=&quot;<xsl:value-of select="@onblur" />&quot;</xsl:if>
        <xsl:if test="string-length( @onselect   ) &gt; 0"> onselect=&quot;<xsl:value-of select="@onselect" />&quot;</xsl:if>
        <xsl:if test="string-length( @onchange   ) &gt; 0"> onchange=&quot;<xsl:value-of select="@onchange" />&quot;</xsl:if>
        <xsl:if test="string-length( @moreattr) &gt; 0"><xsl:text> </xsl:text><xsl:value-of select="@moreattr" /></xsl:if>
        <xsl:text disable-output-escaping="yes">&gt;</xsl:text>
        <xsl:if test="string-length( @value ) &gt; 0"><xsl:value-of select="@value" /></xsl:if>
        <xsl:value-of select="." disable-output-escaping="yes"/>
        <xsl:text disable-output-escaping="yes">&lt;/</xsl:text><xsl:value-of select="name()" /><xsl:text disable-output-escaping="yes">&gt;</xsl:text>
        <xsl:if test="@type = 'reset'">
            <xsl:text disable-output-escaping="yes">&lt;</xsl:text>/li<xsl:text disable-output-escaping="yes">&gt;</xsl:text>
        </xsl:if>
    </xsl:template>
    <xsl:template match="input[@type!='submit' and @type!='reset']">
        <!--xsl:if test="name(parent::*)!='label'">
            <xsl:text disable-output-escaping="yes">&lt;</xsl:text>li class="swbform-li"<xsl:text disable-output-escaping="yes">&gt;</xsl:text>
        </xsl:if-->
        <xsl:text disable-output-escaping="yes">&lt;</xsl:text><xsl:value-of select="name()" />
        <xsl:choose>
            <xsl:when test="string-length(  @type  )   &gt;  0"> type=&quot;<xsl:value-of select="@type" />&quot;</xsl:when>
            <xsl:otherwise> type=&quot;TEXT&quot;</xsl:otherwise>
        </xsl:choose>
        <xsl:if test="string-length( @id    ) &gt; 0"> id=&quot;<xsl:value-of select="@id" />&quot;</xsl:if>
        <xsl:if test="string-length( @name  ) &gt; 0"> name=&quot;<xsl:value-of select="@name" />&quot;</xsl:if>
        <xsl:if test="string-length( @title ) &gt; 0"> title=&quot;<xsl:value-of select="@title" />&quot;</xsl:if>
        <xsl:if test="string-length( @promptMessage ) &gt; 0"> promptMessage=&quot;<xsl:value-of select="@promptMessage" />&quot;</xsl:if>
        <xsl:if test="string-length( @invalidMessage ) &gt; 0"> invalidMessage=&quot;<xsl:value-of select="@invalidMessage" />&quot;</xsl:if>
        <xsl:if test="string-length( @trim ) &gt; 0"> trim=&quot;<xsl:value-of select="@trim" />&quot;</xsl:if>
        <xsl:if test="string-length( @regExp ) &gt; 0"> regExp=&quot;<xsl:value-of select="@regExp" />&quot;</xsl:if>
        <xsl:if test="string-length( @dojoType ) &gt; 0"> dojoType=&quot;<xsl:value-of select="@dojoType" />&quot;</xsl:if>
        <xsl:if test="string-length( @required ) &gt; 0"> required=&quot;<xsl:value-of select="@required" />&quot;</xsl:if>
        <xsl:if test="string-length( @value ) &gt; 0"> value=&quot;<xsl:value-of select="@value" disable-output-escaping="yes"/>&quot;</xsl:if>
        <xsl:if test="string-length( @class ) &gt; 0"> class=&quot;<xsl:value-of select="@class" />&quot;</xsl:if>
        <xsl:if test="string-length( @style ) &gt; 0"> style=&quot;<xsl:value-of select="@style" />&quot;</xsl:if>
        <xsl:if test="string-length( @tabindex  ) &gt; 0"> tabindex=&quot;<xsl:value-of select="@tabindex" />&quot;</xsl:if>
        <xsl:if test="string-length( @accesskey ) &gt; 0"> accesskey=&quot;<xsl:value-of select="@accesskey" />&quot;</xsl:if>
        <xsl:if test="@disabled = 'true'"> disabled</xsl:if>
        <xsl:if test="@readonly = 'true'"> readonly</xsl:if>
        <xsl:if test="@type = 'text' or @type = 'password'">
            <xsl:if test="string-length( @size      ) &gt; 0"> size=&quot;<xsl:value-of select="@size" />&quot;</xsl:if>
            <xsl:if test="string-length( @maxlength ) &gt; 0"> maxlength=&quot;<xsl:value-of select="@maxlength" />&quot;</xsl:if>
            <xsl:if test="@autocomplete = 'on' or @autocomplete = 'off'"> autocomplete=&quot;<xsl:value-of select="@autocomplete" />&quot;</xsl:if>
        </xsl:if>
        <xsl:if test="@checked = 'true' and (@type = 'radio' or @type = 'checkbox')"> checked="checked" </xsl:if>
        <xsl:if test="@type = 'file'">
            <xsl:if test="string-length( @size      ) &gt; 0"> size=&quot;<xsl:value-of select="@size" />&quot;</xsl:if>
            <xsl:if test="string-length( @maxlength ) &gt; 0"> maxlength=&quot;<xsl:value-of select="@maxlength" />&quot;</xsl:if>
            <xsl:if test="string-length( @width  ) &gt; 0"> width=&quot;<xsl:value-of select="@width" />&quot;</xsl:if>
            <xsl:if test="string-length( @height ) &gt; 0"> height=&quot;<xsl:value-of select="@height" />&quot;</xsl:if>
            <xsl:if test="string-length( @accept ) &gt; 0"> accept=&quot;<xsl:value-of select="@accept" />&quot;</xsl:if>
        </xsl:if>
        <xsl:if test="@type = 'image'">
            <xsl:if test="string-length( @src    ) &gt; 0"> src=&quot;<xsl:value-of select="@src" />&quot;</xsl:if>
            <xsl:if test="string-length( @alt    ) &gt; 0"> alt=&quot;<xsl:value-of select="@alt" />&quot;</xsl:if>
            <xsl:if test="string-length( @border ) &gt; 0"> border=&quot;<xsl:value-of select="@border" />&quot;</xsl:if>
            <xsl:if test="string-length( @width  ) &gt; 0"> width=&quot;<xsl:value-of select="@width" />&quot;</xsl:if>
            <xsl:if test="string-length( @height ) &gt; 0"> height=&quot;<xsl:value-of select="@height" />&quot;</xsl:if>
            <xsl:if test="string-length( @hspace ) &gt; 0"> hspace=&quot;<xsl:value-of select="@hspace" />&quot;</xsl:if>
            <xsl:if test="string-length( @vspace ) &gt; 0"> vspace=&quot;<xsl:value-of select="@vspace" />&quot;</xsl:if>
            <xsl:if test="string-length( @usemap ) &gt; 0"> usemap=&quot;<xsl:value-of select="@usemap" />&quot;</xsl:if>
        </xsl:if>
        <xsl:if test="string-length( @onclick    ) &gt; 0"> onclick=&quot;<xsl:value-of select="@onclick" />&quot;</xsl:if>
        <xsl:if test="string-length( @ondblclick ) &gt; 0"> ondblclick=&quot;<xsl:value-of select="@ondblclick" />&quot;</xsl:if>
        <xsl:if test="string-length( @onmousedown) &gt; 0"> onmousedown=&quot;<xsl:value-of select="@onmousedown" />&quot;</xsl:if>
        <xsl:if test="string-length( @onmouseup  ) &gt; 0"> onmouseup=&quot;<xsl:value-of select="@onmouseup" />&quot;</xsl:if>
        <xsl:if test="string-length( @onmouseover) &gt; 0"> onmouseover=&quot;<xsl:value-of select="@onmouseover" />&quot;</xsl:if>
        <xsl:if test="string-length( @onmousemove) &gt; 0"> onmousemove=&quot;<xsl:value-of select="@onmousemove" />&quot;</xsl:if>
        <xsl:if test="string-length( @onmouseout ) &gt; 0"> onmouseout=&quot;<xsl:value-of select="@onmouseout" />&quot;</xsl:if>
        <xsl:if test="string-length( @onkeypress ) &gt; 0"> onkeypress=&quot;<xsl:value-of select="@onkeypress" />&quot;</xsl:if>
        <xsl:if test="string-length( @onkeydown  ) &gt; 0"> onkeydown=&quot;<xsl:value-of select="@onkeydown" />&quot;</xsl:if>
        <xsl:if test="string-length( @onkeyup    ) &gt; 0"> onkeyup=&quot;<xsl:value-of select="@onkeyup" />&quot;</xsl:if>
        <xsl:if test="string-length( @onfocus    ) &gt; 0"> onfocus=&quot;<xsl:value-of select="@onfocus" />&quot;</xsl:if>
        <xsl:if test="string-length( @onblur     ) &gt; 0"> onblur=&quot;<xsl:value-of select="@onblur" />&quot;</xsl:if>
        <xsl:if test="string-length( @onselect   ) &gt; 0"> onselect=&quot;<xsl:value-of select="@onselect" />&quot;</xsl:if>
        <xsl:if test="string-length( @onchange   ) &gt; 0"> onchange=&quot;<xsl:value-of select="@onchange" />&quot;</xsl:if>
        <xsl:if test="string-length( @moreattr) &gt; 0"><xsl:text> </xsl:text><xsl:value-of select="@moreattr" /></xsl:if>
        <xsl:text disable-output-escaping="yes">/&gt;</xsl:text>
        <xsl:if test="@type = 'file'">
            <xsl:if test="string-length( @msg ) &gt; 0"><p><xsl:value-of select="@msg" /></p></xsl:if>
        </xsl:if>
        <!--xsl:if test="name(parent::*)!='label'">
            <xsl:text disable-output-escaping="yes">&lt;</xsl:text>/li<xsl:text disable-output-escaping="yes">&gt;</xsl:text>
        </xsl:if-->
    </xsl:template>
    <xsl:template match="textarea">
        <!--xsl:if test="name(parent::*)!='label'">
            <xsl:text disable-output-escaping="yes">&lt;</xsl:text>li class="swbform-li"<xsl:text disable-output-escaping="yes">&gt;</xsl:text>
        </xsl:if-->
        <xsl:text disable-output-escaping="yes">&lt;</xsl:text><xsl:value-of select="name()" />
        <xsl:if test="string-length( @id    ) &gt; 0"> id=&quot;<xsl:value-of select="@id" />&quot;</xsl:if>
        <xsl:if test="string-length( @name  ) &gt; 0"> name=&quot;<xsl:value-of select="@name" />&quot;</xsl:if>
        <xsl:if test="string-length( @title ) &gt; 0"> title=&quot;<xsl:value-of select="@title" />&quot;</xsl:if>
        <xsl:if test="string-length( @dojoType ) &gt; 0"> dojoType=&quot;<xsl:value-of select="@dojoType" />&quot;</xsl:if>
        <xsl:if test="string-length( promptMessage ) &gt; 0"> promptMessage=&quot;<xsl:value-of select="promptMessage" />&quot;</xsl:if>
        <xsl:if test="string-length( invalidMessage ) &gt; 0"> invalidMessage=&quot;<xsl:value-of select="invalidMessage" />&quot;</xsl:if>
        <xsl:if test="string-length( trim ) &gt; 0"> trim=&quot;<xsl:value-of select="trim" />&quot;</xsl:if>
        <xsl:if test="string-length( regExp ) &gt; 0"> regExp=&quot;<xsl:value-of select="regExp" />&quot;</xsl:if>
        <xsl:if test="string-length( @cols  ) &gt; 0"> cols=&quot;<xsl:value-of select="@cols" />&quot;</xsl:if>
        <xsl:if test="string-length( @rows  ) &gt; 0"> rows=&quot;<xsl:value-of select="@rows" />&quot;</xsl:if>
        <xsl:if test="@wrap = 'off' or @wrap = 'virtual' or @wrap = 'physical'"> wrap=&quot;<xsl:value-of select="@wrap" />&quot;</xsl:if>
        <xsl:if test="string-length( @class ) &gt; 0"> class=&quot;<xsl:value-of select="@class" />&quot;</xsl:if>
        <xsl:if test="string-length( @style ) &gt; 0"> style=&quot;<xsl:value-of select="@style" />&quot;</xsl:if>
        <xsl:if test="string-length( @tabindex  ) &gt; 0"> tabindex=&quot;<xsl:value-of select="@tabindex" />&quot;</xsl:if>
        <xsl:if test="string-length( @accesskey ) &gt; 0"> accesskey=&quot;<xsl:value-of select="@accesskey" />&quot;</xsl:if>
        <xsl:if test="@disabled = 'true'"> disabled</xsl:if>
        <xsl:if test="@readonly = 'true'"> readonly</xsl:if>
        <xsl:if test="string-length( @onclick    ) &gt; 0"> onclick=&quot;<xsl:value-of select="@onclick" />&quot;</xsl:if>
        <xsl:if test="string-length( @ondblclick ) &gt; 0"> ondblclick=&quot;<xsl:value-of select="@ondblclick" />&quot;</xsl:if>
        <xsl:if test="string-length( @onmousedown) &gt; 0"> onmousedown=&quot;<xsl:value-of select="@onmousedown" />&quot;</xsl:if>
        <xsl:if test="string-length( @onmouseup  ) &gt; 0"> onmouseup=&quot;<xsl:value-of select="@onmouseup" />&quot;</xsl:if>
        <xsl:if test="string-length( @onmouseover) &gt; 0"> onmouseover=&quot;<xsl:value-of select="@onmouseover" />&quot;</xsl:if>
        <xsl:if test="string-length( @onmousemove) &gt; 0"> onmousemove=&quot;<xsl:value-of select="@onmousemove" />&quot;</xsl:if>
        <xsl:if test="string-length( @onmouseout ) &gt; 0"> onmouseout=&quot;<xsl:value-of select="@onmouseout" />&quot;</xsl:if>
        <xsl:if test="string-length( @onkeypress ) &gt; 0"> onkeypress=&quot;<xsl:value-of select="@onkeypress" />&quot;</xsl:if>
        <xsl:if test="string-length( @onkeydown  ) &gt; 0"> onkeydown=&quot;<xsl:value-of select="@onkeydown" />&quot;</xsl:if>
        <xsl:if test="string-length( @onkeyup    ) &gt; 0"> onkeyup=&quot;<xsl:value-of select="@onkeyup" />&quot;</xsl:if>
        <xsl:if test="string-length( @onfocus    ) &gt; 0"> onfocus=&quot;<xsl:value-of select="@onfocus" />&quot;</xsl:if>
        <xsl:if test="string-length( @onblur     ) &gt; 0"> onblur=&quot;<xsl:value-of select="@onblur" />&quot;</xsl:if>
        <xsl:if test="string-length( @onselect   ) &gt; 0"> onselect=&quot;<xsl:value-of select="@onselect" />&quot;</xsl:if>
        <xsl:if test="string-length( @onchange   ) &gt; 0"> onchange=&quot;<xsl:value-of select="@onchange" />&quot;</xsl:if>
        <xsl:if test="string-length( @moreattr) &gt; 0"><xsl:text> </xsl:text><xsl:value-of select="@moreattr" /></xsl:if>
        <xsl:text disable-output-escaping="yes">&gt;</xsl:text>
        <xsl:if test="string-length( @value ) &gt; 0"><xsl:value-of select="@value" /></xsl:if>
        <xsl:value-of select="." disable-output-escaping="yes"/>
        <xsl:text disable-output-escaping="yes">&lt;/</xsl:text><xsl:value-of select="name()" /><xsl:text disable-output-escaping="yes">&gt;</xsl:text>
        <!--xsl:if test="name(parent::*)!='label'">
            <xsl:text disable-output-escaping="yes">&lt;</xsl:text>/li<xsl:text disable-output-escaping="yes">&gt;</xsl:text>
        </xsl:if-->
    </xsl:template>
    <xsl:template match="select">
        <!--xsl:if test="name(parent::*)!='label'">
            <xsl:text disable-output-escaping="yes">&lt;</xsl:text>li class="swbform-li"<xsl:text disable-output-escaping="yes">&gt;</xsl:text>
        </xsl:if-->
        <xsl:text disable-output-escaping="yes">&lt;</xsl:text><xsl:value-of select="name()" />
        <xsl:if test="string-length( @id    ) &gt; 0"> id=&quot;<xsl:value-of select="@id" />&quot;</xsl:if>
        <xsl:if test="string-length( @name  ) &gt; 0"> name=&quot;<xsl:value-of select="@name" />&quot;</xsl:if>
        <xsl:if test="string-length( @title ) &gt; 0"> title=&quot;<xsl:value-of select="@title" />&quot;</xsl:if>
        <xsl:if test="string-length( @dojoType ) &gt; 0"> dojoType=&quot;<xsl:value-of select="@dojoType" />&quot;</xsl:if>
        <xsl:if test="string-length( invalidMessage ) &gt; 0"> invalidMessage=&quot;<xsl:value-of select="invalidMessage" />&quot;</xsl:if>
        <xsl:if test="string-length( @size  ) &gt; 0"> size=&quot;<xsl:value-of select="@size" />&quot;</xsl:if>
        <xsl:if test="string-length( @class ) &gt; 0"> class=&quot;<xsl:value-of select="@class" />&quot;</xsl:if>
        <xsl:if test="string-length( @style ) &gt; 0"> style=&quot;<xsl:value-of select="@style" />&quot;</xsl:if>
        <xsl:if test="string-length( @tabindex  ) &gt; 0"> tabindex=&quot;<xsl:value-of select="@tabindex" />&quot;</xsl:if>
        <xsl:if test="string-length( @accesskey ) &gt; 0"> accesskey=&quot;<xsl:value-of select="@accesskey" />&quot;</xsl:if>
        <xsl:if test="@disabled = 'true'"> disabled</xsl:if>
        <xsl:if test="@multiple = 'true'"> multiple</xsl:if>
        <xsl:if test="string-length( @onclick    ) &gt; 0"> onclick=&quot;<xsl:value-of select="@onclick" />&quot;</xsl:if>
        <xsl:if test="string-length( @ondblclick ) &gt; 0"> ondblclick=&quot;<xsl:value-of select="@ondblclick" />&quot;</xsl:if>
        <xsl:if test="string-length( @onmousedown) &gt; 0"> onmousedown=&quot;<xsl:value-of select="@onmousedown" />&quot;</xsl:if>
        <xsl:if test="string-length( @onmouseup  ) &gt; 0"> onmouseup=&quot;<xsl:value-of select="@onmouseup" />&quot;</xsl:if>
        <xsl:if test="string-length( @onmouseover) &gt; 0"> onmouseover=&quot;<xsl:value-of select="@onmouseover" />&quot;</xsl:if>
        <xsl:if test="string-length( @onmousemove) &gt; 0"> onmousemove=&quot;<xsl:value-of select="@onmousemove" />&quot;</xsl:if>
        <xsl:if test="string-length( @onmouseout ) &gt; 0"> onmouseout=&quot;<xsl:value-of select="@onmouseout" />&quot;</xsl:if>
        <xsl:if test="string-length( @onkeypress ) &gt; 0"> onkeypress=&quot;<xsl:value-of select="@onkeypress" />&quot;</xsl:if>
        <xsl:if test="string-length( @onkeydown  ) &gt; 0"> onkeydown=&quot;<xsl:value-of select="@onkeydown" />&quot;</xsl:if>
        <xsl:if test="string-length( @onkeyup    ) &gt; 0"> onkeyup=&quot;<xsl:value-of select="@onkeyup" />&quot;</xsl:if>
        <xsl:if test="string-length( @onfocus    ) &gt; 0"> onfocus=&quot;<xsl:value-of select="@onfocus" />&quot;</xsl:if>
        <xsl:if test="string-length( @onblur     ) &gt; 0"> onblur=&quot;<xsl:value-of select="@onblur" />&quot;</xsl:if>
        <xsl:if test="string-length( @onchange   ) &gt; 0"> onchange=&quot;<xsl:value-of select="@onchange" />&quot;</xsl:if>
        <xsl:if test="string-length( @moreattr) &gt; 0"><xsl:text> </xsl:text><xsl:value-of select="@moreattr" /></xsl:if>
        <xsl:text disable-output-escaping="yes">&gt;</xsl:text>
        <xsl:for-each select="option">
            <xsl:call-template name="options" />
        </xsl:for-each>
        <xsl:text disable-output-escaping="yes">&lt;/</xsl:text><xsl:value-of select="name()" /><xsl:text disable-output-escaping="yes">&gt;</xsl:text>
        <!--xsl:if test="name(parent::*)!='label'">
            <xsl:text disable-output-escaping="yes">&lt;</xsl:text>/li<xsl:text disable-output-escaping="yes">&gt;</xsl:text>
        </xsl:if-->
    </xsl:template>
    <xsl:template name="options">
        <xsl:text disable-output-escaping="yes">&lt;</xsl:text><xsl:value-of select="name()" />
        <xsl:if test="string-length( @id    ) &gt; 0"> id=&quot;<xsl:value-of select="@id" />&quot;</xsl:if>
        <xsl:if test="string-length( @name  ) &gt; 0"> name=&quot;<xsl:value-of select="@name" />&quot;</xsl:if>
        <xsl:if test="string-length( @class ) &gt; 0"> class=&quot;<xsl:value-of select="@class" />&quot;</xsl:if>
        <xsl:if test="string-length( @style ) &gt; 0"> style=&quot;<xsl:value-of select="@style" />&quot;</xsl:if>
        <xsl:if test="string-length( @value ) &gt; 0"> value=&quot;<xsl:value-of select="@value" />&quot;</xsl:if>
        <xsl:if test="@disabled = 'true'"> disabled</xsl:if>
        <xsl:if test="@selected = 'true'"> selected</xsl:if>
        <xsl:if test="string-length( @onclick    ) &gt; 0"> onclick=&quot;<xsl:value-of select="@onclick" />&quot;</xsl:if>
        <xsl:if test="string-length( @ondblclick ) &gt; 0"> ondblclick=&quot;<xsl:value-of select="@ondblclick" />&quot;</xsl:if>
        <xsl:if test="string-length( @onmousedown) &gt; 0"> onmousedown=&quot;<xsl:value-of select="@onmousedown" />&quot;</xsl:if>
        <xsl:if test="string-length( @onmouseup  ) &gt; 0"> onmouseup=&quot;<xsl:value-of select="@onmouseup" />&quot;</xsl:if>
        <xsl:if test="string-length( @onmouseover) &gt; 0"> onmouseover=&quot;<xsl:value-of select="@onmouseover" />&quot;</xsl:if>
        <xsl:if test="string-length( @onmousemove) &gt; 0"> onmousemove=&quot;<xsl:value-of select="@onmousemove" />&quot;</xsl:if>
        <xsl:if test="string-length( @onmouseout ) &gt; 0"> onmouseout=&quot;<xsl:value-of select="@onmouseout" />&quot;</xsl:if>
        <xsl:if test="string-length( @onkeypress ) &gt; 0"> onkeypress=&quot;<xsl:value-of select="@onkeypress" />&quot;</xsl:if>
        <xsl:if test="string-length( @onkeydown  ) &gt; 0"> onkeydown=&quot;<xsl:value-of select="@onkeydown" />&quot;</xsl:if>
        <xsl:if test="string-length( @onkeyup    ) &gt; 0"> onkeyup=&quot;<xsl:value-of select="@onkeyup" />&quot;</xsl:if>
        <xsl:if test="string-length( @moreattr) &gt; 0"><xsl:text> </xsl:text><xsl:value-of select="@moreattr" /></xsl:if>
        <xsl:text disable-output-escaping="yes">&gt;</xsl:text>
            <xsl:value-of select="@value"/>
        <xsl:text disable-output-escaping="yes">&lt;/</xsl:text><xsl:value-of select="name()" /><xsl:text disable-output-escaping="yes">&gt;</xsl:text>
    </xsl:template>
    <xsl:template match="img">
	<xsl:if test="name(parent::*)!='label'">
            <xsl:text disable-output-escaping="yes">&lt;</xsl:text>li class="swbform-li"<xsl:text disable-output-escaping="yes">&gt;</xsl:text>
            <label class="swbform-label"></label>
	</xsl:if>
        <xsl:text disable-output-escaping="yes">&lt;</xsl:text><xsl:value-of select="name()" />
        <xsl:if test="string-length( @id    ) &gt; 0"> id=&quot;<xsl:value-of select="@id" />&quot;</xsl:if>
        <xsl:if test="string-length( @name  ) &gt; 0"> name=&quot;<xsl:value-of select="@name" />&quot;</xsl:if>
        <xsl:if test="string-length( @title ) &gt; 0"> title=&quot;<xsl:value-of select="@title" />&quot;</xsl:if>
        <xsl:if test="string-length( @value ) &gt; 0"> value=&quot;<xsl:value-of select="@value" />&quot;</xsl:if>
        <xsl:if test="string-length( @class ) &gt; 0"> class=&quot;<xsl:value-of select="@class" />&quot;</xsl:if>
        <xsl:if test="string-length( @style ) &gt; 0"> style=&quot;<xsl:value-of select="@style" />&quot;</xsl:if>
        <xsl:if test="string-length( @tabindex  ) &gt; 0"> tabindex=&quot;<xsl:value-of select="@tabindex" />&quot;</xsl:if>
        <xsl:if test="string-length( @accesskey ) &gt; 0"> accesskey=&quot;<xsl:value-of select="@accesskey" />&quot;</xsl:if>
        <xsl:if test="@disabled = 'true'"> disabled</xsl:if>
	<xsl:if test="string-length( @src    ) &gt; 0"> src=&quot;<xsl:value-of select="@src" />&quot;</xsl:if>
	<xsl:if test="string-length( @alt    ) &gt; 0"> alt=&quot;<xsl:value-of select="@alt" />&quot;</xsl:if>
	<xsl:if test="string-length( @border ) &gt; 0"> border=&quot;<xsl:value-of select="@border" />&quot;</xsl:if>
	<xsl:if test="string-length( @width  ) &gt; 0"> width=&quot;<xsl:value-of select="@width" />&quot;</xsl:if>
	<xsl:if test="string-length( @height ) &gt; 0"> height=&quot;<xsl:value-of select="@height" />&quot;</xsl:if>
	<xsl:if test="string-length( @hspace ) &gt; 0"> hspace=&quot;<xsl:value-of select="@hspace" />&quot;</xsl:if>
	<xsl:if test="string-length( @vspace ) &gt; 0"> vspace=&quot;<xsl:value-of select="@vspace" />&quot;</xsl:if>
	<xsl:if test="string-length( @usemap ) &gt; 0"> usemap=&quot;<xsl:value-of select="@usemap" />&quot;</xsl:if>
	<xsl:if test="string-length( @onclick    ) &gt; 0"> onclick=&quot;<xsl:value-of select="@onclick" />&quot;</xsl:if>
	<xsl:if test="string-length( @ondblclick ) &gt; 0"> ondblclick=&quot;<xsl:value-of select="@ondblclick" />&quot;</xsl:if>
	<xsl:if test="string-length( @onmousedown) &gt; 0"> onmousedown=&quot;<xsl:value-of select="@onmousedown" />&quot;</xsl:if>
	<xsl:if test="string-length( @onmouseup  ) &gt; 0"> onmouseup=&quot;<xsl:value-of select="@onmouseup" />&quot;</xsl:if>
	<xsl:if test="string-length( @onmouseover) &gt; 0"> onmouseover=&quot;<xsl:value-of select="@onmouseover" />&quot;</xsl:if>
	<xsl:if test="string-length( @onmousemove) &gt; 0"> onmousemove=&quot;<xsl:value-of select="@onmousemove" />&quot;</xsl:if>
	<xsl:if test="string-length( @onmouseout ) &gt; 0"> onmouseout=&quot;<xsl:value-of select="@onmouseout" />&quot;</xsl:if>
	<xsl:if test="string-length( @onkeypress ) &gt; 0"> onkeypress=&quot;<xsl:value-of select="@onkeypress" />&quot;</xsl:if>
	<xsl:if test="string-length( @onkeydown  ) &gt; 0"> onkeydown=&quot;<xsl:value-of select="@onkeydown" />&quot;</xsl:if>
	<xsl:if test="string-length( @onkeyup    ) &gt; 0"> onkeyup=&quot;<xsl:value-of select="@onkeyup" />&quot;</xsl:if>
	<xsl:if test="string-length( @onfocus    ) &gt; 0"> onfocus=&quot;<xsl:value-of select="@onfocus" />&quot;</xsl:if>
	<xsl:if test="string-length( @onblur     ) &gt; 0"> onblur=&quot;<xsl:value-of select="@onblur" />&quot;</xsl:if>
	<xsl:if test="string-length( @onselect   ) &gt; 0"> onselect=&quot;<xsl:value-of select="@onselect" />&quot;</xsl:if>
	<xsl:if test="string-length( @onchange   ) &gt; 0"> onchange=&quot;<xsl:value-of select="@onchange" />&quot;</xsl:if>
        <xsl:if test="string-length( @moreattr) &gt; 0"><xsl:text> </xsl:text><xsl:value-of select="@moreattr" /></xsl:if>
        <xsl:text disable-output-escaping="yes">&gt;</xsl:text>
	<xsl:if test="name(parent::*)!='label'">
            <xsl:text disable-output-escaping="yes">&lt;</xsl:text>/li<xsl:text disable-output-escaping="yes">&gt;</xsl:text>
	</xsl:if>
    </xsl:template>
    <xsl:template match="applet">
        <xsl:if test="name(parent::*)!='label'">
            <xsl:text disable-output-escaping="yes">&lt;</xsl:text>li class="swbform-li"<xsl:text disable-output-escaping="yes">&gt;</xsl:text>
        </xsl:if>
        <xsl:text disable-output-escaping="yes">&lt;</xsl:text><xsl:value-of select="name()" />
        <xsl:if test="string-length( @id    ) &gt; 0"> id=&quot;<xsl:value-of select="@id" />&quot;</xsl:if>
        <xsl:if test="string-length( @name  ) &gt; 0"> name=&quot;<xsl:value-of select="@name" />&quot;</xsl:if>
        <xsl:if test="string-length( @title ) &gt; 0"> title=&quot;<xsl:value-of select="@title" />&quot;</xsl:if>
        <xsl:if test="@declare = 'declare'"> declare</xsl:if>
        <xsl:if test="string-length( @classid ) &gt; 0"> classid=&quot;<xsl:value-of select="@classid" />&quot;</xsl:if>
        <xsl:if test="string-length( @code) &gt; 0"> code=&quot;<xsl:value-of select="@code" />&quot;</xsl:if>
        <xsl:if test="string-length( @codebase) &gt; 0"> codebase=&quot;<xsl:value-of select="@codebase" />&quot;</xsl:if>
        <xsl:if test="string-length( @data    ) &gt; 0"> data=&quot;<xsl:value-of select="@data" />&quot;</xsl:if>
        <xsl:if test="string-length( @type    ) &gt; 0"> type=&quot;<xsl:value-of select="@type" />&quot;</xsl:if>
        <xsl:if test="string-length( @codetype) &gt; 0"> codetype=&quot;<xsl:value-of select="@codetype" />&quot;</xsl:if>
        <xsl:if test="string-length( @archive ) &gt; 0"> archive=&quot;<xsl:value-of select="@archive" />&quot;</xsl:if>
        <xsl:if test="string-length( @standby ) &gt; 0"> standby=&quot;<xsl:value-of select="@standby" />&quot;</xsl:if>
        <xsl:if test="string-length( @border  ) &gt; 0"> border=&quot;<xsl:value-of select="@border" />&quot;</xsl:if>
        <xsl:if test="string-length( @width   ) &gt; 0"> width=&quot;<xsl:value-of select="@width" />&quot;</xsl:if>
        <xsl:if test="string-length( @height  ) &gt; 0"> height=&quot;<xsl:value-of select="@height" />&quot;</xsl:if>
        <xsl:if test="string-length( @hspace  ) &gt; 0"> hspace=&quot;<xsl:value-of select="@hspace" />&quot;</xsl:if>
        <xsl:if test="string-length( @vspace  ) &gt; 0"> vspace=&quot;<xsl:value-of select="@vspace" />&quot;</xsl:if>
        <xsl:if test="string-length( @usemap  ) &gt; 0"> usemap=&quot;<xsl:value-of select="@usemap" />&quot;</xsl:if>
        <xsl:if test="string-length( @lang    ) &gt; 0"> lang=&quot;<xsl:value-of select="@lang" />&quot;</xsl:if>
        <xsl:if test="string-length( @class   ) &gt; 0"> class=&quot;<xsl:value-of select="@class" />&quot;</xsl:if>
        <xsl:if test="string-length( @style   ) &gt; 0"> style=&quot;<xsl:value-of select="@style" />&quot;</xsl:if>
        <xsl:if test="string-length( @tabindex) &gt; 0"> tabindex=&quot;<xsl:value-of select="@tabindex" />&quot;</xsl:if>
        <xsl:if test="string-length( @accesskey)&gt; 0"> accesskey=&quot;<xsl:value-of select="@accesskey" />&quot;</xsl:if>
        <xsl:if test="string-length( @onclick    ) &gt; 0"> onclick=&quot;<xsl:value-of select="@onclick" />&quot;</xsl:if>
        <xsl:if test="string-length( @ondblclick ) &gt; 0"> ondblclick=&quot;<xsl:value-of select="@ondblclick" />&quot;</xsl:if>
        <xsl:if test="string-length( @onmousedown) &gt; 0"> onmousedown=&quot;<xsl:value-of select="@onmousedown" />&quot;</xsl:if>
        <xsl:if test="string-length( @onmouseup  ) &gt; 0"> onmouseup=&quot;<xsl:value-of select="@onmouseup" />&quot;</xsl:if>
        <xsl:if test="string-length( @onmouseover) &gt; 0"> onmouseover=&quot;<xsl:value-of select="@onmouseover" />&quot;</xsl:if>
        <xsl:if test="string-length( @onmousemove) &gt; 0"> onmousemove=&quot;<xsl:value-of select="@onmousemove" />&quot;</xsl:if>
        <xsl:if test="string-length( @onmouseout ) &gt; 0"> onmouseout=&quot;<xsl:value-of select="@onmouseout" />&quot;</xsl:if>
        <xsl:if test="string-length( @onkeypress ) &gt; 0"> onkeypress=&quot;<xsl:value-of select="@onkeypress" />&quot;</xsl:if>
        <xsl:if test="string-length( @onkeydown  ) &gt; 0"> onkeydown=&quot;<xsl:value-of select="@onkeydown" />&quot;</xsl:if>
        <xsl:if test="string-length( @onkeyup    ) &gt; 0"> onkeyup=&quot;<xsl:value-of select="@onkeyup" />&quot;</xsl:if>
        <xsl:if test="string-length( @moreattr) &gt; 0"><xsl:text> </xsl:text><xsl:value-of select="@moreattr" /></xsl:if>
        <xsl:text disable-output-escaping="yes">&gt;</xsl:text>
        <xsl:apply-templates />
        <xsl:text disable-output-escaping="yes">&lt;/</xsl:text><xsl:value-of select="name()" /><xsl:text disable-output-escaping="yes">&gt;</xsl:text>
        <xsl:if test="name(parent::*)!='label'">
            <xsl:text disable-output-escaping="yes">&lt;</xsl:text>/li<xsl:text disable-output-escaping="yes">&gt;</xsl:text>
        </xsl:if>
    </xsl:template>
    <xsl:template match="object">
        <xsl:if test="name(parent::*)!='label'">
            <xsl:text disable-output-escaping="yes">&lt;</xsl:text>li class="swbform-li"<xsl:text disable-output-escaping="yes">&gt;</xsl:text>
            <label for="pat" class="swbform-label">&amp;nbsp;</label>
        </xsl:if>
        <xsl:text disable-output-escaping="yes">&lt;</xsl:text><xsl:value-of select="name()" />
        <xsl:if test="string-length( @id    ) &gt; 0"> id=&quot;<xsl:value-of select="@id" />&quot;</xsl:if>
        <xsl:if test="string-length( @name  ) &gt; 0"> name=&quot;<xsl:value-of select="@name" />&quot;</xsl:if>
        <xsl:if test="string-length( @title ) &gt; 0"> title=&quot;<xsl:value-of select="@title" />&quot;</xsl:if>
        <xsl:if test="@declare = 'declare'"> declare</xsl:if>
        <xsl:if test="string-length( @classid ) &gt; 0"> classid=&quot;<xsl:value-of select="@classid" />&quot;</xsl:if>
        <xsl:if test="string-length( @codebase) &gt; 0"> codebase=&quot;<xsl:value-of select="@codebase" />&quot;</xsl:if>
        <xsl:if test="string-length( @data    ) &gt; 0"> data=&quot;<xsl:value-of select="@data" />&quot;</xsl:if>
        <xsl:if test="string-length( @type    ) &gt; 0"> type=&quot;<xsl:value-of select="@type" />&quot;</xsl:if>
        <xsl:if test="string-length( @codetype) &gt; 0"> codetype=&quot;<xsl:value-of select="@codetype" />&quot;</xsl:if>
        <xsl:if test="string-length( @archive ) &gt; 0"> archive=&quot;<xsl:value-of select="@archive" />&quot;</xsl:if>
        <xsl:if test="string-length( @standby ) &gt; 0"> standby=&quot;<xsl:value-of select="@standby" />&quot;</xsl:if>
        <xsl:if test="string-length( @border  ) &gt; 0"> border=&quot;<xsl:value-of select="@border" />&quot;</xsl:if>
        <xsl:if test="string-length( @width   ) &gt; 0"> width=&quot;<xsl:value-of select="@width" />&quot;</xsl:if>
        <xsl:if test="string-length( @height  ) &gt; 0"> height=&quot;<xsl:value-of select="@height" />&quot;</xsl:if>
        <xsl:if test="string-length( @hspace  ) &gt; 0"> hspace=&quot;<xsl:value-of select="@hspace" />&quot;</xsl:if>
        <xsl:if test="string-length( @vspace  ) &gt; 0"> vspace=&quot;<xsl:value-of select="@vspace" />&quot;</xsl:if>
        <xsl:if test="string-length( @usemap  ) &gt; 0"> usemap=&quot;<xsl:value-of select="@usemap" />&quot;</xsl:if>
        <xsl:if test="string-length( @lang    ) &gt; 0"> lang=&quot;<xsl:value-of select="@lang" />&quot;</xsl:if>
        <xsl:if test="string-length( @class   ) &gt; 0"> class=&quot;<xsl:value-of select="@class" />&quot;</xsl:if>
        <xsl:if test="string-length( @style   ) &gt; 0"> style=&quot;<xsl:value-of select="@style" />&quot;</xsl:if>
        <xsl:if test="string-length( @tabindex) &gt; 0"> tabindex=&quot;<xsl:value-of select="@tabindex" />&quot;</xsl:if>
        <xsl:if test="string-length( @accesskey)&gt; 0"> accesskey=&quot;<xsl:value-of select="@accesskey" />&quot;</xsl:if>
        <xsl:if test="string-length( @onclick    ) &gt; 0"> onclick=&quot;<xsl:value-of select="@onclick" />&quot;</xsl:if>
        <xsl:if test="string-length( @ondblclick ) &gt; 0"> ondblclick=&quot;<xsl:value-of select="@ondblclick" />&quot;</xsl:if>
        <xsl:if test="string-length( @onmousedown) &gt; 0"> onmousedown=&quot;<xsl:value-of select="@onmousedown" />&quot;</xsl:if>
        <xsl:if test="string-length( @onmouseup  ) &gt; 0"> onmouseup=&quot;<xsl:value-of select="@onmouseup" />&quot;</xsl:if>
        <xsl:if test="string-length( @onmouseover) &gt; 0"> onmouseover=&quot;<xsl:value-of select="@onmouseover" />&quot;</xsl:if>
        <xsl:if test="string-length( @onmousemove) &gt; 0"> onmousemove=&quot;<xsl:value-of select="@onmousemove" />&quot;</xsl:if>
        <xsl:if test="string-length( @onmouseout ) &gt; 0"> onmouseout=&quot;<xsl:value-of select="@onmouseout" />&quot;</xsl:if>
        <xsl:if test="string-length( @onkeypress ) &gt; 0"> onkeypress=&quot;<xsl:value-of select="@onkeypress" />&quot;</xsl:if>
        <xsl:if test="string-length( @onkeydown  ) &gt; 0"> onkeydown=&quot;<xsl:value-of select="@onkeydown" />&quot;</xsl:if>
        <xsl:if test="string-length( @onkeyup    ) &gt; 0"> onkeyup=&quot;<xsl:value-of select="@onkeyup" />&quot;</xsl:if>
        <xsl:if test="string-length( @moreattr) &gt; 0"><xsl:text> </xsl:text><xsl:value-of select="@moreattr" /></xsl:if>
        <xsl:text disable-output-escaping="yes">&gt;</xsl:text>
        <xsl:apply-templates />
        <xsl:text disable-output-escaping="yes">&lt;/</xsl:text><xsl:value-of select="name()" /><xsl:text disable-output-escaping="yes">&gt;</xsl:text>
        <xsl:if test="name(parent::*)!='label'">
            <xsl:text disable-output-escaping="yes">&lt;</xsl:text>/li<xsl:text disable-output-escaping="yes">&gt;</xsl:text>
        </xsl:if>
    </xsl:template>
    <xsl:template match="param">
        <xsl:text disable-output-escaping="yes">&lt;</xsl:text><xsl:value-of select="name()" />
        <xsl:if test="string-length( @id    ) &gt; 0"> id=&quot;<xsl:value-of select="@id" />&quot;</xsl:if>
        <xsl:if test="string-length( @name  ) &gt; 0"> name=&quot;<xsl:value-of select="@name" />&quot;</xsl:if>
        <xsl:if test="string-length( @value ) &gt; 0"> value=&quot;<xsl:value-of select="@value" />&quot;</xsl:if>
        <xsl:if test="string-length( @valuetype)&gt;0"> valuetype=&quot;<xsl:value-of select="@valuetype" />&quot;</xsl:if>
        <xsl:if test="string-length( @type  ) &gt; 0"> type=&quot;<xsl:value-of select="@type" />&quot;</xsl:if>
        <xsl:if test="string-length( @input ) &gt; 0"> input=&quot;<xsl:value-of select="@input" />&quot;</xsl:if>
        <xsl:if test="string-length( @moreattr ) &gt; 0"> moreattr=&quot;<xsl:value-of select="@moreattr" />&quot;</xsl:if>
        <xsl:text disable-output-escaping="yes">/&gt;</xsl:text>
    </xsl:template>
    <xsl:template match="embed">
        <xsl:text disable-output-escaping="yes">&lt;</xsl:text><xsl:value-of select="name()" />
        <xsl:if test="string-length( @name    ) &gt; 0"> name=&quot;<xsl:value-of select="@name" />&quot;</xsl:if>
        <xsl:if test="string-length( @src     ) &gt; 0"> src=&quot;<xsl:value-of select="@src" />&quot;</xsl:if>
        <xsl:if test="string-length( @type    ) &gt; 0"> type=&quot;<xsl:value-of select="@type" />&quot;</xsl:if>
        <xsl:if test="string-length( @width   ) &gt; 0"> width=&quot;<xsl:value-of select="@width" />&quot;</xsl:if>
        <xsl:if test="string-length( @height  ) &gt; 0"> height=&quot;<xsl:value-of select="@height" />&quot;</xsl:if>
        <xsl:if test="string-length( @align   ) &gt; 0"> align=&quot;<xsl:value-of select="@align" />&quot;</xsl:if>
        <xsl:if test="string-length( @pluginspage )&gt; 0"> pluginspage=&quot;<xsl:value-of select="@pluginspage" />&quot;</xsl:if>
        <xsl:if test="string-length( @pluginurl)&gt; 0"> pluginurl=&quot;<xsl:value-of select="@pluginurl" />&quot;</xsl:if>
        <xsl:if test="@hidden = 'true' or @hidden = 'false'"> hidden=&quot;<xsl:value-of select="@hidden" />&quot;</xsl:if>
        <xsl:if test="string-length( @href    ) &gt; 0"> href=&quot;<xsl:value-of select="@href" />&quot;</xsl:if>
        <xsl:if test="string-length( @target  ) &gt; 0"> target=&quot;<xsl:value-of select="@target" />&quot;</xsl:if>
        <xsl:if test="@autostart = 'true' or @autostart = 'false'"> autostart=&quot;<xsl:value-of select="@autostart" />&quot;</xsl:if>
        <xsl:if test="string-length( @loop    ) &gt; 0"> loop=&quot;<xsl:value-of select="@loop" />&quot;</xsl:if>
        <xsl:if test="string-length( @playcount)&gt; 0"> playcount=&quot;<xsl:value-of select="@playcount" />&quot;</xsl:if>
        <xsl:if test="string-length( @volume  ) &gt; 0"> volume=&quot;<xsl:value-of select="@volume" />&quot;</xsl:if>
        <xsl:if test="string-length( @controls) &gt; 0"> controls=&quot;<xsl:value-of select="@controls" />&quot;</xsl:if>
        <xsl:if test="@controller = 'true' or @controller = 'false'"> controller=&quot;<xsl:value-of select="@controller" />&quot;</xsl:if>
        <xsl:if test="@mastersound = 'mastersound'"> mastersound</xsl:if>
        <xsl:if test="string-length( @starttime)&gt; 0"> starttime=&quot;<xsl:value-of select="@starttime" />&quot;</xsl:if>
        <xsl:if test="string-length( @endtime ) &gt; 0"> endtime=&quot;<xsl:value-of select="@endtime" />&quot;</xsl:if>
        <xsl:if test="string-length( @quality ) &gt; 0"> quality=&quot;<xsl:value-of select="@quality" />&quot;</xsl:if>
        <xsl:if test="string-length( @bgcolor ) &gt; 0"> bgcolor=&quot;<xsl:value-of select="@bgcolor" />&quot;</xsl:if>
        <xsl:text disable-output-escaping="yes">/&gt;</xsl:text>
    </xsl:template>
    <xsl:template match="map">
        <xsl:text disable-output-escaping="yes">&lt;</xsl:text><xsl:value-of select="name()" />
        <xsl:if test="string-length( @id    ) &gt; 0"> id=&quot;<xsl:value-of select="@id" />&quot;</xsl:if>
        <xsl:if test="string-length( @name  ) &gt; 0"> name=&quot;<xsl:value-of select="@name" />&quot;</xsl:if>
        <xsl:if test="string-length( @title ) &gt; 0"> title=&quot;<xsl:value-of select="@title" />&quot;</xsl:if>
        <xsl:if test="string-length( @lang    ) &gt; 0"> lang=&quot;<xsl:value-of select="@lang" />&quot;</xsl:if>
        <xsl:if test="string-length( @class   ) &gt; 0"> class=&quot;<xsl:value-of select="@class" />&quot;</xsl:if>
        <xsl:if test="string-length( @style   ) &gt; 0"> style=&quot;<xsl:value-of select="@style" />&quot;</xsl:if>
        <xsl:if test="string-length( @tabindex) &gt; 0"> tabindex=&quot;<xsl:value-of select="@tabindex" />&quot;</xsl:if>
        <xsl:if test="string-length( @accesskey)&gt; 0"> accesskey=&quot;<xsl:value-of select="@accesskey" />&quot;</xsl:if>
        <xsl:if test="string-length( @onclick    ) &gt; 0"> onclick=&quot;<xsl:value-of select="@onclick" />&quot;</xsl:if>
        <xsl:if test="string-length( @ondblclick ) &gt; 0"> ondblclick=&quot;<xsl:value-of select="@ondblclick" />&quot;</xsl:if>
        <xsl:if test="string-length( @onmousedown) &gt; 0"> onmousedown=&quot;<xsl:value-of select="@onmousedown" />&quot;</xsl:if>
        <xsl:if test="string-length( @onmouseup  ) &gt; 0"> onmouseup=&quot;<xsl:value-of select="@onmouseup" />&quot;</xsl:if>
        <xsl:if test="string-length( @onmouseover) &gt; 0"> onmouseover=&quot;<xsl:value-of select="@onmouseover" />&quot;</xsl:if>
        <xsl:if test="string-length( @onmousemove) &gt; 0"> onmousemove=&quot;<xsl:value-of select="@onmousemove" />&quot;</xsl:if>
        <xsl:if test="string-length( @onmouseout ) &gt; 0"> onmouseout=&quot;<xsl:value-of select="@onmouseout" />&quot;</xsl:if>
        <xsl:if test="string-length( @onkeypress ) &gt; 0"> onkeypress=&quot;<xsl:value-of select="@onkeypress" />&quot;</xsl:if>
        <xsl:if test="string-length( @onkeydown  ) &gt; 0"> onkeydown=&quot;<xsl:value-of select="@onkeydown" />&quot;</xsl:if>
        <xsl:if test="string-length( @onkeyup    ) &gt; 0"> onkeyup=&quot;<xsl:value-of select="@onkeyup" />&quot;</xsl:if>
        <xsl:if test="string-length( @moreattr) &gt; 0"><xsl:text> </xsl:text><xsl:value-of select="@moreattr" /></xsl:if>
        <xsl:text disable-output-escaping="yes">&gt;</xsl:text>
        <xsl:apply-templates />
        <xsl:text disable-output-escaping="yes">&lt;/</xsl:text><xsl:value-of select="name()" /><xsl:text disable-output-escaping="yes">&gt;</xsl:text>
    </xsl:template>
    <xsl:template match="area">
        <xsl:text disable-output-escaping="yes">&lt;</xsl:text><xsl:value-of select="name()" />
        <xsl:if test="string-length( @id    ) &gt; 0"> id=&quot;<xsl:value-of select="@id" />&quot;</xsl:if>
        <xsl:if test="string-length( @name  ) &gt; 0"> name=&quot;<xsl:value-of select="@name" />&quot;</xsl:if>
        <xsl:if test="string-length( @title ) &gt; 0"> title=&quot;<xsl:value-of select="@title" />&quot;</xsl:if>
        <xsl:if test="string-length( @shape ) &gt; 0"> shape=&quot;<xsl:value-of select="@shape" />&quot;</xsl:if>
        <xsl:if test="string-length( @coords) &gt; 0"> coords=&quot;<xsl:value-of select="@coords" />&quot;</xsl:if>
        <xsl:if test="string-length( @href  ) &gt; 0"> href=&quot;<xsl:value-of select="@href" />&quot;</xsl:if>
        <xsl:if test="string-length( @alt   ) &gt; 0"> alt=&quot;<xsl:value-of select="@alt" />&quot;</xsl:if>
        <xsl:if test="string-length( @lang    ) &gt; 0"> lang=&quot;<xsl:value-of select="@lang" />&quot;</xsl:if>
        <xsl:if test="string-length( @class   ) &gt; 0"> class=&quot;<xsl:value-of select="@class" />&quot;</xsl:if>
        <xsl:if test="string-length( @style   ) &gt; 0"> style=&quot;<xsl:value-of select="@style" />&quot;</xsl:if>
        <xsl:if test="string-length( @tabindex) &gt; 0"> tabindex=&quot;<xsl:value-of select="@tabindex" />&quot;</xsl:if>
        <xsl:if test="string-length( @accesskey)&gt; 0"> accesskey=&quot;<xsl:value-of select="@accesskey" />&quot;</xsl:if>
        <xsl:if test="string-length( @onclick    ) &gt; 0"> onclick=&quot;<xsl:value-of select="@onclick" />&quot;</xsl:if>
        <xsl:if test="string-length( @ondblclick ) &gt; 0"> ondblclick=&quot;<xsl:value-of select="@ondblclick" />&quot;</xsl:if>
        <xsl:if test="string-length( @onmousedown) &gt; 0"> onmousedown=&quot;<xsl:value-of select="@onmousedown" />&quot;</xsl:if>
        <xsl:if test="string-length( @onmouseup  ) &gt; 0"> onmouseup=&quot;<xsl:value-of select="@onmouseup" />&quot;</xsl:if>
        <xsl:if test="string-length( @onmouseover) &gt; 0"> onmouseover=&quot;<xsl:value-of select="@onmouseover" />&quot;</xsl:if>
        <xsl:if test="string-length( @onmousemove) &gt; 0"> onmousemove=&quot;<xsl:value-of select="@onmousemove" />&quot;</xsl:if>
        <xsl:if test="string-length( @onmouseout ) &gt; 0"> onmouseout=&quot;<xsl:value-of select="@onmouseout" />&quot;</xsl:if>
        <xsl:if test="string-length( @onkeypress ) &gt; 0"> onkeypress=&quot;<xsl:value-of select="@onkeypress" />&quot;</xsl:if>
        <xsl:if test="string-length( @onkeydown  ) &gt; 0"> onkeydown=&quot;<xsl:value-of select="@onkeydown" />&quot;</xsl:if>
        <xsl:if test="string-length( @onkeyup    ) &gt; 0"> onkeyup=&quot;<xsl:value-of select="@onkeyup" />&quot;</xsl:if>
        <xsl:if test="string-length( @onfocus    ) &gt; 0"> onfocus=&quot;<xsl:value-of select="@onfocus" />&quot;</xsl:if>
        <xsl:if test="string-length( @onblur     ) &gt; 0"> onblur=&quot;<xsl:value-of select="@onblur" />&quot;</xsl:if>
        <xsl:if test="string-length( @moreattr) &gt; 0"><xsl:text> </xsl:text><xsl:value-of select="@moreattr" /></xsl:if>
        <xsl:text disable-output-escaping="yes">/&gt;</xsl:text>
    </xsl:template>
    <xsl:template match="calendar">
        <xsl:if test="name(parent::*)!='label'">
            <xsl:text disable-output-escaping="yes">&lt;</xsl:text>li class="swbform-li"<xsl:text disable-output-escaping="yes">&gt;</xsl:text>
        </xsl:if>
        <xsl:text disable-output-escaping="yes">&lt;input type=&quot;text&quot;</xsl:text>
        <xsl:if test="string-length( @id    ) &gt; 0"> id=&quot;<xsl:value-of select="@id" />&quot;</xsl:if>
        <xsl:if test="string-length( @name  ) &gt; 0"> name=&quot;<xsl:value-of select="@name" />&quot;</xsl:if>
        <xsl:if test="string-length( @title ) &gt; 0"> title=&quot;<xsl:value-of select="@title" />&quot;</xsl:if>
        <xsl:if test="string-length( @value ) &gt; 0"> value=&quot;<xsl:value-of select="@value" disable-output-escaping="yes"/>&quot;</xsl:if>
        <xsl:if test="string-length( @class ) &gt; 0"> class=&quot;<xsl:value-of select="@class" />&quot;</xsl:if>
        <xsl:if test="string-length( @style ) &gt; 0"> style=&quot;<xsl:value-of select="@style" />&quot;</xsl:if>
        <xsl:if test="string-length( @tabindex  ) &gt; 0"> tabindex=&quot;<xsl:value-of select="@tabindex" />&quot;</xsl:if>
        <xsl:if test="string-length( @accesskey ) &gt; 0"> accesskey=&quot;<xsl:value-of select="@accesskey" />&quot;</xsl:if>
        <xsl:if test="@disabled = 'true'"> disabled</xsl:if>
        <xsl:if test="@readonly = 'true'"> readonly</xsl:if>
        <xsl:if test="string-length( @size      ) &gt; 0"> size=&quot;<xsl:value-of select="@size" />&quot;</xsl:if>
        <xsl:if test="string-length( @maxlength ) &gt; 0"> maxlength=&quot;<xsl:value-of select="@maxlength" />&quot;</xsl:if>
        <xsl:text disable-output-escaping="yes">/&gt; &lt;img</xsl:text>
        <xsl:if test="string-length( @src    ) &gt; 0"> src=&quot;<xsl:value-of select="@src" />&quot;</xsl:if>
        <xsl:if test="string-length( @align  ) &gt; 0"> align=&quot;<xsl:value-of select="@align" />&quot;</xsl:if>
        <xsl:if test="string-length( @alt    ) &gt; 0"> alt=&quot;<xsl:value-of select="@alt" />&quot;</xsl:if>
        <xsl:if test="string-length( @border ) &gt; 0"> border=&quot;<xsl:value-of select="@border" />&quot;</xsl:if>
        <xsl:if test="string-length( @width  ) &gt; 0"> width=&quot;<xsl:value-of select="@width" />&quot;</xsl:if>
        <xsl:if test="string-length( @height ) &gt; 0"> height=&quot;<xsl:value-of select="@height" />&quot;</xsl:if>
        <xsl:if test="string-length( @hspace ) &gt; 0"> hspace=&quot;<xsl:value-of select="@hspace" />&quot;</xsl:if>
        <xsl:if test="string-length( @vspace ) &gt; 0"> vspace=&quot;<xsl:value-of select="@vspace" />&quot;</xsl:if>
        <xsl:if test="string-length( @onclick    ) &gt; 0"> onclick=&quot;<xsl:value-of select="@onclick" />&quot;</xsl:if>
        <xsl:if test="string-length( @ondblclick ) &gt; 0"> ondblclick=&quot;<xsl:value-of select="@ondblclick" />&quot;</xsl:if>
        <xsl:if test="string-length( @onmousedown) &gt; 0"> onmousedown=&quot;<xsl:value-of select="@onmousedown" />&quot;</xsl:if>
        <xsl:if test="string-length( @onmouseup  ) &gt; 0"> onmouseup=&quot;<xsl:value-of select="@onmouseup" />&quot;</xsl:if>
        <xsl:if test="string-length( @onmouseover) &gt; 0"> onmouseover=&quot;<xsl:value-of select="@onmouseover" />&quot;</xsl:if>
        <xsl:if test="string-length( @onmousemove) &gt; 0"> onmousemove=&quot;<xsl:value-of select="@onmousemove" />&quot;</xsl:if>
        <xsl:if test="string-length( @onmouseout ) &gt; 0"> onmouseout=&quot;<xsl:value-of select="@onmouseout" />&quot;</xsl:if>
        <xsl:if test="string-length( @onkeypress ) &gt; 0"> onkeypress=&quot;<xsl:value-of select="@onkeypress" />&quot;</xsl:if>
        <xsl:if test="string-length( @onkeydown  ) &gt; 0"> onkeydown=&quot;<xsl:value-of select="@onkeydown" />&quot;</xsl:if>
        <xsl:if test="string-length( @onkeyup    ) &gt; 0"> onkeyup=&quot;<xsl:value-of select="@onkeyup" />&quot;</xsl:if>
        <xsl:if test="string-length( @onfocus    ) &gt; 0"> onfocus=&quot;<xsl:value-of select="@onfocus" />&quot;</xsl:if>
        <xsl:if test="string-length( @onblur     ) &gt; 0"> onblur=&quot;<xsl:value-of select="@onblur" />&quot;</xsl:if>
        <xsl:if test="string-length( @onselect   ) &gt; 0"> onselect=&quot;<xsl:value-of select="@onselect" />&quot;</xsl:if>
        <xsl:if test="string-length( @onchange   ) &gt; 0"> onchange=&quot;<xsl:value-of select="@onchange" />&quot;</xsl:if>
        <xsl:if test="string-length( @moreattr) &gt; 0"><xsl:text> </xsl:text><xsl:value-of select="@moreattr" /></xsl:if>
        <xsl:text disable-output-escaping="yes">/&gt; &lt;script</xsl:text>
        <xsl:if test="string-length( @language ) &gt; 0"> language=&quot;<xsl:value-of select="@language" />&quot;</xsl:if>
        <xsl:if test="string-length( @script ) &gt; 0"> src=&quot;<xsl:value-of select="@script" />&quot;</xsl:if>
        <xsl:text disable-output-escaping="yes">&gt; &lt;/script&gt;</xsl:text>
        <xsl:if test="name(parent::*)!='label'">
            <xsl:text disable-output-escaping="yes">&lt;</xsl:text>/li<xsl:text disable-output-escaping="yes">&gt;</xsl:text>
        </xsl:if>
    </xsl:template>
</xsl:stylesheet>