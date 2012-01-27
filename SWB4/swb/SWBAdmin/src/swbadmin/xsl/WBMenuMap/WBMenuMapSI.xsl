<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="xml" omit-xml-declaration="yes" indent="yes" encoding="ISO-8859-1"/>

    <xsl:template match="/menu">        
        <xsl:for-each select="node">
            <xsl:if test="position()=1">
                <xsl:text  disable-output-escaping="yes">
                    &lt;div class="swb-menumap">
                    &lt;ul>
                </xsl:text>
            </xsl:if>
                <xsl:call-template name="node" />
            <xsl:if test="position()=last()">
                <xsl:text  disable-output-escaping="yes">
                    &lt;/ul>
                    &lt;/div>
                </xsl:text>
            </xsl:if>
        </xsl:for-each>
    </xsl:template>

    <xsl:template name="node">
        <xsl:if test="@current='false'">
            <li>
            <a href="{@path}" class="swb-menumap-act" target="{@target}" title="{@name}">
                <xsl:value-of select="@name"/>
            </a>
            <xsl:for-each select="node">
                <xsl:if test="position()=1">
                    <xsl:text  disable-output-escaping="yes">
                        &lt;ul>
                    </xsl:text>
                </xsl:if>
                    <xsl:call-template name="node" />
                <xsl:if test="position()=last()">
                    <xsl:text  disable-output-escaping="yes">
                        &lt;/ul>
                    </xsl:text>
                </xsl:if>
            </xsl:for-each>
            </li>
        </xsl:if>
        <xsl:if test="@current='true'">
            <li class="swb-menumap-cur">
            <xsl:value-of select="@name"/>
            <xsl:for-each select="node">
                <xsl:if test="position()=1">
                    <xsl:text  disable-output-escaping="yes">
                        &lt;ul>
                    </xsl:text>
                </xsl:if>
                    <xsl:call-template name="node" />
                <xsl:if test="position()=last()">
                    <xsl:text  disable-output-escaping="yes">
                        &lt;/ul>
                    </xsl:text>
                </xsl:if>
            </xsl:for-each>
            </li>
        </xsl:if>
    </xsl:template>
</xsl:stylesheet>