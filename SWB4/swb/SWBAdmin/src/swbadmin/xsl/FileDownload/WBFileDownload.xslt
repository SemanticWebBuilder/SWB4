<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:output method="html" encoding="ISO-8859-1"/>
<xsl:template match="/FileDownload">
<DIV class="wmn_box">
<TABLE border="1" width="100%" >
	<TR>
		<TD colspan="5">
				<xsl:value-of select="@text" disable-output-escaping="yes"/>
		</TD>
	</TR>
	<TR>	
		<TD>
				<A href="{@path}">
					<xsl:value-of select="@name"/>
				</A>
		</TD>
		<TD>
				<xsl:value-of select="@length"/>
		</TD>
		<TD>
				<A href="{@url}">
					<xsl:value-of select="@nHits"/>
				</A>
		</TD>
		<TD>
				<xsl:value-of select="@tArchitecture"/>
		</TD>
		<TD>
				<xsl:value-of select="@textension"/>
		</TD>

	</TR>
	
</TABLE>
</DIV>
</xsl:template>

</xsl:stylesheet>


	
