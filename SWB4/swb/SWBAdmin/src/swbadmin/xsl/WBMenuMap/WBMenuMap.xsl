<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="xml" omit-xml-declaration="yes" indent="yes" encoding="ISO-8859-1"/>

    <xsl:template match="/menu">
        <div class="swb-menu-map">
            <xsl:if test="position()=1">
                <xsl:text  disable-output-escaping="yes">&lt;ul></xsl:text>
            </xsl:if>
                <xsl:for-each select="node">
                    <xsl:call-template name="node" />
                </xsl:for-each>
            <xsl:if test="position()=last()">
                <xsl:text  disable-output-escaping="yes">&lt;/ul></xsl:text>
            </xsl:if>
        </div>
    </xsl:template>

    <xsl:template name="node">
        <li>
            <img src="/swbadmin/icons/trans.gif" alt="" height="1">
                    <xsl:attribute name="width"><xsl:value-of select="@level * 10"/></xsl:attribute>
            </img>
            <xsl:if test="@inPath = 'true'">
                <xsl:text disable-output-escaping="yes">
                    &lt;strong>
                </xsl:text>
            </xsl:if>
            <xsl:if test="@current = 'false'">
                <a href="{@path}" class="" target="{@target}" title="{@name}">
                    <xsl:value-of select="@name"/>
                </a>
            </xsl:if>
            <xsl:if test="@current = 'true'">
                <em><xsl:value-of select="@name"/></em>
            </xsl:if>
            <xsl:if test="@inPath = 'true'">
                <xsl:text  disable-output-escaping="yes">
                    &lt;/strong>
                </xsl:text>
            </xsl:if>
        <xsl:for-each select="node">
            <xsl:if test="position()=1">
                <xsl:text  disable-output-escaping="yes">&lt;ul></xsl:text>
            </xsl:if>
                <xsl:call-template name="node" />
            <xsl:if test="position()=last()">
                <xsl:text  disable-output-escaping="yes">&lt;/ul></xsl:text>
            </xsl:if>
        </xsl:for-each>
        </li>
    </xsl:template>
</xsl:stylesheet>