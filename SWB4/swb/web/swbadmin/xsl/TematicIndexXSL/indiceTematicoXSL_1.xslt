<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:output method="html" version="1.0" encoding="ISO-8859-1" indent="yes"/>
<xsl:template match="/resource">
	<xsl:if test = "count(father)&gt;0">
		<LINK href="{father/@path}images/TematicIndexXSL.css" rel="stylesheet" type="text/css"></LINK>
		<DIV class="idx_box">
		<TABLE border="0" width="100%" cellpadding="2" cellspacing="3">
		<xsl:for-each select="father">
			<TR><TD colspan="2" class="idx_title">
				<xsl:value-of select="fathertitle" />
			</TD></TR>
			<TR><TD colspan="2" class="idx_description">
				<xsl:if test="@hasfatherdescription ='1'">
					<xsl:value-of select="fatherdescription" disable-output-escaping="yes"/>
				</xsl:if>
				<xsl:if test="@hasfatherdescription ='0'">
					Descripción idioma:<xsl:value-of select="fatherlanguage" />
					<BR/><xsl:value-of select="fatherdescription" disable-output-escaping="yes"/>
				</xsl:if>
			</TD></TR>
			<xsl:for-each select="son">
				<TR><TD colspan="2">
					<A href="{@sonref}" class="idx_link1">
						<FONT style="font-size: 10px;">&gt; </FONT><xsl:value-of select="sontitle" />
					</A>
				</TD></TR>
				<TR class="idx_data"><TD><xsl:text disable-output-escaping="yes">&amp;nbsp;&amp;nbsp;</xsl:text></TD><TD>
					<xsl:if test="@hassondescription ='1'">
						<xsl:value-of select="sondescription" disable-output-escaping="yes"/>
					</xsl:if>
					<xsl:if test="@hassondescription ='0'">
						Descripción idioma:<xsl:value-of select="sonlanguage" />
						<BR/><xsl:value-of select="sondescription" disable-output-escaping="yes"/>
					</xsl:if>
				</TD></TR>
				<xsl:for-each select="grandson">
					<TR><TD colspan="2">
						<A href="{@grandsonref}" class="idx_link2">
							<FONT style="font-size: 10px;">&gt; </FONT><xsl:value-of select="grandsontitle" />
						</A>
					</TD></TR>
					<TR class="idx_data"><TD><xsl:text disable-output-escaping="yes">&amp;nbsp;&amp;nbsp;</xsl:text></TD><TD>
						<xsl:if test="@hasgrandsondescription ='1'">
							<xsl:value-of select="grandsondescription" disable-output-escaping="yes"/>
						</xsl:if>
						<xsl:if test="@hasgrandsondescription ='0'">
							Descripción el idioma:<xsl:value-of select="grandsonlanguage" />
							<BR/><xsl:value-of select="grandsondescription" disable-output-escaping="yes"/>
						</xsl:if>
					</TD></TR>
				</xsl:for-each>
			</xsl:for-each>
		</xsl:for-each>
		</TABLE>
		</DIV>
	</xsl:if>
</xsl:template>
</xsl:stylesheet>