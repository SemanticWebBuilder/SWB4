<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="xml" omit-xml-declaration="yes" indent="yes" encoding="ISO-8859-1"/>
    
    <xsl:template match="/poll">
        <div class="swb-poll">
            <xsl:apply-templates select="title"/>
            <xsl:apply-templates select="imgTitle"/>
            <xsl:apply-templates select="question"/>
            <xsl:apply-templates select="options"/>
            <xsl:apply-templates select="vote"/>
            <xsl:apply-templates select="results"/>
            <xsl:apply-templates select="links"/>
            <br />
        </div>
    </xsl:template>

    <xsl:template match="title">
        <p class="swb-poll-title">
            <xsl:apply-templates/>
        </p>
    </xsl:template>
    <xsl:template match="imgTitle">
        <img src="{@src}" alt="{@alt}"/>
    </xsl:template>
    <xsl:template match="question">
        <p class="swb-poll-quest">
            <xsl:apply-templates/>
        </p>
    </xsl:template>

    <xsl:template match="options">
        <ul>
        <xsl:for-each select="option">
            <li><xsl:call-template name="option" /></li>
        </xsl:for-each>
        </ul>
    </xsl:template>
    <xsl:template name="option">
        <label for="{@id}"><xsl:value-of select="option"/>
            <input type="radio" name="{@name}" id="{@id}" value="{@value}" />
            <xsl:apply-templates/>
        </label>
    </xsl:template>

    <xsl:template match="vote">
        <xsl:if test = "string-length(@src)&gt;0">
            <p class="swb-poll-link">
                <a href="#" onclick="{@action}">
                    <img src="{@src}" alt="vota" />
                </a>
            </p>
        </xsl:if>
        <xsl:if test = "string-length(@src)=0">
            <p class="swb-poll-link">
                <a href="#" onclick="{@action}">
                    <img src="{@path}images/votar.png" alt="vota" />
                </a>
            </p>
        </xsl:if>
    </xsl:template>
    <xsl:template match="results">
        <p class="swb-poll-link">
            <a href="javascript:{@action}" title="{@title}">
                <xsl:apply-templates/>
            </a>
        </p>
    </xsl:template>
    <xsl:template match="links">
        <xsl:for-each select="link">
            <p class="swb-poll-link">
                <a href="{@url}" title="{@title}">
                    <xsl:apply-templates/>
                </a>
            </p>
        </xsl:for-each>
    </xsl:template>
</xsl:stylesheet>