<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:output method="html" encoding="ISO-8859-1"/>
<xsl:template match="/">
<xsl:for-each select="resource">
	<xsl:for-each select="survey">
		<script type="text/javascript">
			function DoInit(){
			   window.document.frmSurvey.wb_surveyinit.value = 1;
			   window.document.frmSurvey.submit();
			}
			function DoNext(){
				window.document.frmSurvey.wb_move.value = 1;
			 	if (!ValidateForm()){
					return;
			  	}
		  		else{
   					window.document.frmSurvey.submit();
			  	}
			}
			function DoBack(){
			   window.document.frmSurvey.wb_move.value = 0;
			   window.document.frmSurvey.submit();
			}
			function DoFinish(){
			   window.document.frmSurvey.wb_move.value = 1;
			   window.document.frmSurvey.wb_marked_questions.value = 1;
			   if (!ValidateForm()){
					return;
			  	}
		  		else{
   					window.document.frmSurvey.submit();
			  	}
			}
			function DoCloseSurvey(){
			   window.document.frmSurvey.wb_move.value = 1;
			   window.document.frmSurvey.wb_isfinished.value = 1;
			   window.document.frmSurvey.submit();
			}
			function GoRemaining(question,section){
			   window.document.frmSurvey.wb_survey_question.value = question;
			   window.document.frmSurvey.wb_remaining_question.value = 1;
			   window.document.frmSurvey.wb_move.value = 0;
			   window.document.frmSurvey.wb_section.value = section;
			   window.document.frmSurvey.submit();
			}
			function DoPrintResult(path,id){
			   var params = path + "?";
			   params = params + "wb_responseid=" + id;
			     window.open(params,"ResultWindow","width=600, height=550, scrollbars, resizable, alwaysRaised, menubar");
			}
		</script>
		<style type="text/css">
			.Estilo1 {font-size: 14px; color: #0033CC; font-weight: bold; font-family: Arial, Helvetica, sans-serif;}
			.Estilo2 {font-family: Arial, Helvetica, sans-serif; font-size: 14px; color: #A41C1C;}
			.Estilo3 {font-family: Arial, Helvetica, sans-serif; font-size: 18px; font-weight: bold; color: #000000;}
			.Estilo4 {font-family: Arial, Helvetica, sans-serif; font-size: 24px; font-weight: bold; color: #A41C1C;}
			.Estilo5 {font-family: Arial, Helvetica, sans-serif; font-weight: bold; font-size: 16px; color: #a41c1c;}
			.Estilo6 {font-family: Arial, Helvetica, sans-serif; font-size: 14px; color: #B48222; font-weight: bold;}
			.Estilo7 {font-family: Arial, Helvetica, sans-serif; font-size: 16px; color: #990000;}
			.Estilo8 {font-family: Arial, Helvetica, sans-serif; font-size: 16px; color: #990000; font-weight: bold; }
		</style>
		<table width="100%" border="0" cellspacing="0" cellpadding="0">

		<!-- Starts wellcome message code -->
		<xsl:if test="@page ='wellcome'">
			<form method="Post" action="{@address}" id="frmSurvey" name="frmSurvey">
			<xsl:for-each select="wellcome">
				<input type="hidden" name="wb_surveyinit" value="{@surveyInit}"></input>
			</xsl:for-each>
			<tr>
			<td width="40" rowspan="2">
			</td>
			<td>
			<p align="center"><font face="Verdana, Arial, Helvetica, sans-serif" size="1" color="black">
			Esta es la última fase para completar tu autocapacitación, sólo deberás contestar las preguntas que aparecerán en pantalla y obtener una calificación mínima de <strong><xsl:value-of select="@minScore" /></strong> para poder certificarte en este proceso.
			</font></p>
			<p align="center"><font face="Verdana, Arial, Helvetica, sans-serif" size="1" color="black">
			Contarás con un tiempo máximo de <strong><xsl:value-of select="@timeAnswer" /> minutos </strong> para resolver el examen y deberás tomar en cuenta que tu tiempo empieza a correr a partir de que presiones el botón de "iniciar".
			</font></p>
			<p align="center"><font face="Verdana, Arial, Helvetica, sans-serif" size="1" color="black">
			Te informamos que, por lo pronto, sólo tendrás <strong> <xsl:value-of select="@maxAnswer" /> oportunidad </strong> para presentar el examen. En caso de no aprobarlo, podrás volverlo a presentar <strong>una vez más </strong> en fechas posteriores (se te hará llegar una notificación con la información).
			</font></p>
			<div align="center"><span><font face="Verdana, Arial, Helvetica, sans-serif" size="1" color="black">
			¡Suerte!
			</font></span></div>
			</td>
			</tr>
			<tr>
			<td align="center">
			<br/><input type="button" class="boton" onClick="DoInit()" value="Iniciar" name="btnInit"></input>
			</td>
			</tr>
			</form>
		</xsl:if>
		<!-- Ends wellcome message-->

		<!-- Starts question code -->
		<xsl:if test="@page ='question'">
			<!--<form name="frmSurvey" id="frmSurvey" action="{@action}" method="Post" onSubmit="return ValidateForm();">-->
			<form name="frmSurvey" id="frmSurvey" action="{@action}" method="Post">
			<xsl:for-each select="hidden">
				<input type="hidden" name="wb_survey_question" value="{@surveyQuestion}"></input>
				<input type="hidden" name="wb_move" value="{@move}"></input>
				<input type="hidden" name="wb_marked_questions" value="{@markedQuestions}"></input>
				<input type="hidden" name="wb_surveyinit" value="{@surveyInit}"></input>
				<input type="hidden" name="wb_survey_type" value="{@surveyType}"></input>
				<input type="hidden" name="wb_section" value="{@surveySection}"></input>
				<xsl:if test="@surveyType ='2'">
					<input type="hidden" name="wb_secuential" value="{@surveySecuential}"></input>
				</xsl:if>
			</xsl:for-each>



			<TR>
			<TD colspan="3" align="center" >

			<!--  Image example
			<img src="{//resource/survey/@path}images/cal.gif"></img>
			-->
					<FONT face="Verdana, Arial, Helvetica, sans-serif" size="2" color="#666699">
				<B><xsl:value-of select="@title" /></B>
				</FONT>
			</TD>
			</TR>
			<xsl:if test="description !='0'">
				<TR>
				<TD width="10">&#160;</TD>
				<TD colspan="2" align="left">
					<FONT face="Verdana, Arial, Helvetica, sans-serif" size="1" color="#666699">
					&#160;&#160;&#160;&#160;&#160;<B><xsl:value-of select="@description" /></B>
					</FONT>
				</TD>
				</TR>
			</xsl:if>
			<xsl:if test="instruction !='0'">
					<TR>
					<TD width="10">&#160;</TD>
					<TD colspan="2" align="left"><FONT face="Verdana, Arial, Helvetica, sans-serif" size="1" color="#666699">
						&#160;&#160;&#160;&#160;&#160;<B><xsl:value-of select="@instructions" /></B>
					</FONT></TD>
					</TR>
			</xsl:if>

			<TR>
			<TD width="10">&#160;</TD>
			<TD colspan="2" align="left">
				<FONT face="Verdana, Arial, Helvetica, sans-serif" size="1" color="#666699">
					<B><xsl:value-of select="@required" /></B>
				</FONT>
			</TD>
			</TR>
			<xsl:if test = "count(group)&gt;0">
				<xsl:for-each select="group">
					<TR>
					<TD colspan="3">&#160;</TD>
					</TR>
					<TR>
					<TD width="10">&#160;</TD>
					<TD colspan="2" align="left">
						<FONT face="Verdana, Arial, Helvetica, sans-serif" size="1" color="#666699">
							<B>Grupo <xsl:value-of select="@id" />:&#160;<xsl:value-of select="title" /></B>
						</FONT>
					</TD>
					</TR>
					<TR>
					<TD width="10">&#160;</TD>
					<TD colspan="2" align="left">
						<FONT face="Verdana, Arial, Helvetica, sans-serif" size="1" color="#666699">
						&#160;&#160;&#160;&#160;&#160;<B><xsl:value-of select="description" /></B>
						</FONT>
					</TD>
					</TR>
					<!-- Comienza question-->
					<xsl:if test = "count(question)&gt;0">
						<xsl:for-each select="question">
							<TR>
							<TD width="10">&#160;</TD>
							<TD colspan="2" align="left">
								<FONT face="Verdana, Arial, Helvetica, sans-serif" size="1" color="black">
								<B><xsl:value-of select="description" /></B>
								</FONT>
							</TD>
							</TR>
							<TR>
							<TD width="10">&#160;</TD>
							<TD colspan="2" align="left">
								<FONT face="Verdana, Arial, Helvetica, sans-serif" size="1" color="black">
								<B><xsl:value-of select="@numsec" />.&#160;<xsl:value-of select="text" />
									<xsl:if test="@required ='1'">
										&#160;*
									</xsl:if>
								</B>
								</FONT>
							</TD>
							</TR>
							<!-- Comienza freqanswer -->
							<xsl:for-each select="freqanswerid">
								<xsl:if test="@controlid ='1'">
									<TR>
									<TD width="10">&#160;</TD>
									<TD colspan="2" align="left">
									<xsl:if test="@validate ='0'">
										<input type="text" name="quest{@numque}" size="50" value="{@value}" onKeyPress="javascript: CheckChar()"/>
									</xsl:if>
									<xsl:if test="@validate ='1'">
										<!--<input type="text" name="quest{@numque}" size="50" value="{@value}" onKeyPress="javascript: CheckChar()" onblur="{@jsvalid}" />-->
										<input type="text" name="quest{@numque}" size="50" value="{@value}" onblur="{@jsvalid}" />
									</xsl:if>
									</TD>
									</TR>
								</xsl:if>
								<xsl:if test="@controlid ='2'">
									<TR>
									<TD width="10">&#160;</TD>
									<TD colspan="2" align="left">
										<textarea rows="5"  cols="50" name="quest{@numque}" onKeyPress="javascript: CheckChar()"><xsl:value-of select="@value" /></textarea>
									</TD>
									</TR>
								</xsl:if>
								<xsl:if test="@controlid ='3'">
									<INPUT type="hidden" name="quest{@numque}"  value=""/>
									<xsl:for-each select="option">
										<TR>
										<TD width="10">&#160;</TD>
										<TD colspan="2" align="left">
										<xsl:if test="@selected ='0'">
											<INPUT type="checkbox" name="Squest{@numque}" value="1"/>&#160;<font size="2" color="#666699"><xsl:value-of select="@secopt" />)&#160;<xsl:apply-templates/></font>
										</xsl:if>
										<xsl:if test="@selected ='1'">
											<INPUT type="checkbox" name="Squest{@numque}" value="1" checked="true" />&#160;<font size="2" color="#666699"><xsl:value-of select="@secopt" />)&#160;<xsl:apply-templates/></font>
										</xsl:if>
										</TD>
										</TR>
									</xsl:for-each>
									<script language="javascript">
									<xsl:for-each select="jscode">

												<xsl:apply-templates />

									</xsl:for-each></script>
								</xsl:if>
								<xsl:if test="@controlid ='4'">
									<INPUT type="hidden" name="quest{@numque}"  value=""/>
									<xsl:for-each select="option">
										<TR>
										<TD width="10">&#160;</TD>
										<TD colspan="2" align="left">
										<xsl:if test="@selected ='0'">
											<INPUT type="radio" name="Squest{@numque}" value="{@numopt}" />&#160;<font size="2" color="#666699"><xsl:value-of select="@secopt" />)&#160;<xsl:apply-templates/></font>
										</xsl:if>
										<xsl:if test="@selected ='1'">
											<INPUT type="radio" name="Squest{@numque}" value="{@numopt}" checked="true" />&#160;<font size="2" color="#666699"><xsl:value-of select="@secopt" />)&#160;<xsl:apply-templates/></font>
										</xsl:if>
										</TD>
										</TR>
									</xsl:for-each>
									<script language="javascript">
									<xsl:for-each select="jscode">

												<xsl:apply-templates />

									</xsl:for-each></script>
								</xsl:if>
								<xsl:if test="@controlid ='5'">
									<INPUT type="hidden" name="quest{@numque}"  value=""/>
									<TR>
									<TD width="10">&#160;</TD>
									<TD colspan="2" align="left">
									<SELECT name="Squest{@numque}" size="5" multiple="true">
									<xsl:for-each select="option">
										<xsl:if test="@selected ='0'">
											<OPTION value="{@numopt}"><xsl:apply-templates/></OPTION>
										</xsl:if>
										<xsl:if test="@selected ='1'">
											<OPTION value="{@numopt}" selected="true"><xsl:apply-templates/></OPTION>
										</xsl:if>
									</xsl:for-each>
									</SELECT>
									</TD>
									</TR>
									<script language="javascript">
									<xsl:for-each select="jscode">
										<xsl:apply-templates />
									</xsl:for-each></script>
								</xsl:if>
								<xsl:if test="@controlid ='6'">
									<INPUT type="hidden" name="quest{@numque}"  value=""/>
									<TR>
									<TD width="10">&#160;</TD>
									<TD colspan="2" align="left">
									<SELECT name="Squest{@numque}">
									<OPTION value="0"></OPTION>
									<xsl:for-each select="option">
										<xsl:if test="@selected ='0'">
											<OPTION value="{@numopt}"><xsl:apply-templates/></OPTION>
										</xsl:if>
										<xsl:if test="@selected ='1'">
											<OPTION value="{@numopt}" selected="true"><xsl:apply-templates/></OPTION>
										</xsl:if>
									</xsl:for-each>
									</SELECT>
									</TD>
									</TR>
									<script language="javascript">
									<xsl:for-each select="jscode">
										<xsl:apply-templates />
									</xsl:for-each></script>
								</xsl:if>
								<xsl:if test="@controlid ='7'">
									<TR>
									<TD width="10">&#160;</TD>
									<TD colspan="2" align="left">
									<INPUT type="text" name="quest{@numque}" size="15" value="{@value}" readonly="true" /><A href="javascript:show_calendar('frmSurvey.quest{@numque}');" onmouseover="window.status='Selecciona fecha';return true;" onmouseout="window.status='';return true;"><IMG src="{@img}" width="24" height="22" border="0"></IMG></A>
									</TD>
									</TR>
								</xsl:if>
							</xsl:for-each>
							<!-- Termina freqanswer -->

							<!-- Comienza sonquestion-->
							<xsl:if test = "count(questionson)&gt;0">
								<xsl:for-each select="questionson">
									<TR>
									<TD width="10">&#160;</TD>
									<TD colspan="2" align="left">
										<FONT face="Verdana, Arial, Helvetica, sans-serif" size="1" color="black">
										<B><xsl:value-of select="description" /></B>
										</FONT>
									</TD>
									</TR>
									<TR>
									<TD width="10">&#160;</TD>
									<TD colspan="2" align="left">
										<FONT face="Verdana, Arial, Helvetica, sans-serif" size="1" color="black">
										<B>&#160;&#160;&#160;&#160;&#160;<xsl:value-of select="text" />
											<xsl:if test="@required ='1'">
												&#160;*
											</xsl:if>
										</B>
										</FONT>
									</TD>
									</TR>
									<!-- Comienza sonfreqanswer -->
									<xsl:for-each select="freqansweridson">
										<xsl:if test="@controlid ='1'">
											<TR>
											<TD width="10">&#160;</TD>
											<TD colspan="2" align="left">
											<xsl:if test="@validate ='0'">
												<input type="text" name="quest{@numque}" size="50" value="{@value}" onKeyPress="javascript: CheckChar()"/>
											</xsl:if>
											<xsl:if test="@validate ='1'">
												<input type="text" name="quest{@numque}" size="50" value="{@value}" onKeyPress="javascript: CheckChar()" onblur="{@jsvalid}" />
											</xsl:if>
											</TD>
											</TR>
										</xsl:if>
										<xsl:if test="@controlid ='2'">
											<TR>
											<TD width="10">&#160;</TD>
											<TD colspan="2" align="left">
												<textarea rows="5" cols="50" name="quest{@numque}" onKeyPress="javascript: CheckChar()"><xsl:value-of select="@value" /></textarea>
											</TD>
											</TR>
										</xsl:if>
										<xsl:if test="@controlid ='3'">
											<INPUT type="hidden" name="quest{@numque}"  value=""/>
											<xsl:for-each select="option">
												<TR>
												<TD width="10">&#160;</TD>
												<TD colspan="2" align="left">
												<xsl:if test="@selected ='0'">
													<INPUT type="checkbox" name="Squest{@numque}" value="1"/>&#160;<font size="2" color="#666699"><xsl:value-of select="@secopt" />)&#160;<xsl:apply-templates/></font>
												</xsl:if>
												<xsl:if test="@selected ='1'">
													<INPUT type="checkbox" name="Squest{@numque}" value="1" checked="true" />&#160;<font size="2" color="#666699"><xsl:value-of select="@secopt" />)&#160;<xsl:apply-templates/></font>
												</xsl:if>
												</TD>
												</TR>
											</xsl:for-each>
											<script language="javascript">
											<xsl:for-each select="jscode">
												<xsl:apply-templates />
											</xsl:for-each></script>
										</xsl:if>
										<xsl:if test="@controlid ='4'">
											<INPUT type="hidden" name="quest{@numque}"  value=""/>
											<xsl:for-each select="option">
												<TR>
												<TD width="10">&#160;</TD>
												<TD colspan="2" align="left">
												<xsl:if test="@selected ='0'">
													<INPUT type="radio" name="Squest{@numque}" value="{@numopt}" />&#160;<font size="2" color="#666699"><xsl:value-of select="@secopt" />)&#160;<xsl:apply-templates/></font>
												</xsl:if>
												<xsl:if test="@selected ='1'">
													<INPUT type="radio" name="Squest{@numque}" value="{@numopt}" checked="true" />&#160;<font size="2" color="#666699"><xsl:value-of select="@secopt" />)&#160;<xsl:apply-templates/></font>
												</xsl:if>
												</TD>
												</TR>
											</xsl:for-each>
											<script language="javascript">
											<xsl:for-each select="jscode">
												<xsl:apply-templates />
											</xsl:for-each></script>
										</xsl:if>
										<xsl:if test="@controlid ='5'">
											<INPUT type="hidden" name="quest{@numque}"  value=""/>
											<TR>
											<TD width="10">&#160;</TD>
											<TD colspan="2" align="left">
											<SELECT name="Squest{@numque}" size="5" multiple="true">
											<xsl:for-each select="option">
												<xsl:if test="@selected ='0'">
													<OPTION value="{@numopt}"><xsl:apply-templates/></OPTION>
												</xsl:if>
												<xsl:if test="@selected ='1'">
													<OPTION value="{@numopt}" selected="true"><xsl:apply-templates/></OPTION>
												</xsl:if>
											</xsl:for-each>
											</SELECT>
											</TD>
											</TR>
											<script language="javascript">
											<xsl:for-each select="jscode">

													<xsl:apply-templates />

											</xsl:for-each></script>
										</xsl:if>
										<xsl:if test="@controlid ='6'">
											<INPUT type="hidden" name="quest{@numque}"  value=""/>
											<TR>
											<TD width="10">&#160;</TD>
											<TD colspan="2" align="left">
											<SELECT name="Squest{@numque}">
											<OPTION value="0"></OPTION>
											<xsl:for-each select="option">
												<xsl:if test="@selected ='0'">
													<OPTION value="{@numopt}"><xsl:apply-templates/></OPTION>
												</xsl:if>
												<xsl:if test="@selected ='1'">
													<OPTION value="{@numopt}" selected="true"><xsl:apply-templates/></OPTION>
												</xsl:if>
											</xsl:for-each>
											</SELECT>
											</TD>
											</TR>
											<script language="javascript">
											<xsl:for-each select="jscode">
												<xsl:apply-templates />
											</xsl:for-each></script>
										</xsl:if>
										<xsl:if test="@controlid ='7'">
											<TR>
											<TD width="10">&#160;</TD>
											<TD colspan="2" align="left">
											<INPUT type="text" name="quest{@numque}" size="15" value="{@value}" readonly="true" /><A href="javascript:show_calendar('frmSurvey.quest{@numque}');" onmouseover="window.status='Selecciona fecha';return true;" onmouseout="window.status='';return true;"><IMG src="{@img}" width="24" height="22" border="0"></IMG></A>
											</TD>
											</TR>
										</xsl:if>
									</xsl:for-each>
									<!-- Termina sonfreqanswer -->
								</xsl:for-each>
							</xsl:if>
							<!-- Termina sonquestion -->

							<!-- Marcar pregunta como pendiente -->
							<tr>
								<td colspan="3">&#160;</td>
							</tr>
							<tr>
							<td width="10">&#160;</td>
							<td colspan="2" align="left">
							<xsl:if test="@pending ='0'">
								<input type="checkbox" name="wb_mark{@numque}" value="1"></input>
							</xsl:if>
							<xsl:if test="@pending ='1'">
								<input type="checkbox" name="wb_mark{@numque}" value="1" checked="true"></input>
							</xsl:if>
							&#160;<font face="Verdana, Arial, Helvetica, sans-serif" size="1" color="black">
							Dejar pendiente la pregunta <xsl:value-of select="@numsec" />
							</font>
							</td>
							</tr>
							<tr>
								<td colspan="3">&#160;</td>
							</tr>
							<!-- Marcar pregunta como pendiente -->


							</xsl:for-each>
						</xsl:if>
						<!-- Termina question -->
					</xsl:for-each>
				</xsl:if>

				<TR><TD width="10">&#160;</TD><TD colspan="2" align="right">
				<xsl:for-each select="buttons">
					<xsl:if test="@isBack ='1'">
						<input type="button" onClick="DoBack()" value="Anterior" name="btnBack" style="color:#FFFFFF; background-color:#222D43; border-color:#D7DBEB; FONT-FAMILY: verdana,arial,helvetica,sans-serif; FONT-SIZE: 7pt; FONT-WEIGHT: normal"></input>&#160;&#160;
					</xsl:if>
					<xsl:if test="@isNext ='1'">
						<input type="button" onClick="DoNext()" value="Siguiente" name="btnNext" style="color:#FFFFFF; background-color:#222D43; border-color:#D7DBEB; FONT-FAMILY: verdana,arial,helvetica,sans-serif; FONT-SIZE: 7pt; FONT-WEIGHT: normal"></input>&#160;&#160;
					</xsl:if>
					<xsl:if test="@isFinished ='1'">
						<input type="button" onClick="DoFinish()" value="Terminar" name="btnNext" style="color:#FFFFFF; background-color:#222D43; border-color:#D7DBEB; FONT-FAMILY: verdana,arial,helvetica,sans-serif; FONT-SIZE: 7pt; FONT-WEIGHT: normal"></input>&#160;&#160;
					</xsl:if>
					<xsl:if test="@isRemaining ='1'">
						<input type="hidden" name="wb_remaining_question" value="{@isRemaining}"></input>
						<input type="button" onClick="DoFinish()" value="Pendientes" name="btnRemaining" style="color:#FFFFFF; background-color:#222D43; border-color:#D7DBEB; FONT-FAMILY: verdana,arial,helvetica,sans-serif; FONT-SIZE: 7pt; FONT-WEIGHT: normal"></input>&#160;&#160;
					</xsl:if>
				</xsl:for-each>
				</TD></TR>
				</form>
				<!-- Codigo java script -->
				<xsl:for-each select="jsdatecode">
					<SCRIPT language="javascript" src="{@src}"></SCRIPT>
				</xsl:for-each>
				<SCRIPT language="javascript">
				<xsl:for-each select="jsmaincode">

					<xsl:apply-templates />

				</xsl:for-each>
				</SCRIPT>
				<!-- Termina codigo java script -->
		</xsl:if>
		<!-- Ends question code -->

		<!-- Starts remaining message -->
		<xsl:if test="@page ='remaining'">
			<form method="Post" action="{@address}" id="frmSurvey" name="frmSurvey">
			<xsl:for-each select="remaining">
				<input type="hidden" name="wb_survey_question" value="{@surveyQuestion}"></input>
				<input type="hidden" name="wb_move" value="{@move}"></input>
				<input type="hidden" name="wb_marked_questions" value="{@markedQuestions}"></input>
				<input type="hidden" name="wb_isfinished" value="{@isFinished}"></input>
				<input type="hidden" name="wb_surveyinit" value="{@surveyInit}"></input>
				<input type="hidden" name="wb_remaining_question" value="{@remainingQuestion}"></input>
				<input type="hidden" name="wb_section" value="{@surveySection}"></input>
				<tr>
				<td colspan="3"></td>
				</tr>
				<xsl:if test="@hasRemaining ='1'">
					<tr>
					<td colspan="2">&#160;</td>
					<td align="left"><font face="Verdana, Arial, Helvetica, sans-serif" size="1" color="black">
					Estas son las preguntas que dejaste pendientes. ¡ Aún estás a tiempo para contestarlas! Para volver a ellas, da clic en las ligas que se muestran a continuación.
					</font>
					</td>
					</tr>
					<tr>
					<td colspan="3">&#160;</td>
					</tr>
					<xsl:for-each select="remainQuestion">
						<tr>
						<td colspan="2">&#160;</td>
						<td><span class="Estilo6"><a href="javascript:GoRemaining({@id},{@sec})"><xsl:value-of select="@opt" />.&#160;<xsl:value-of select="." /></a><br/></span></td>
						</tr>
					</xsl:for-each>
				</xsl:if>
				<xsl:if test="@hasRemaining ='0'">
					<tr>
					<td colspan="2">&#160;</td>
					<td align="left"><font face="Verdana, Arial, Helvetica, sans-serif" size="1" color="black">
					No hay preguntas pendientes pendientes, al presionar "Cerrar Encuesta" obtendrá su calificación
					</font></td>
					</tr>
				</xsl:if>
				<tr>
				<td colspan="3">&#160;</td>
				</tr>
				<tr>
				<td colspan="3" align="center">
				<input type="button" class="boton" onClick="DoBack()" value="Anterior" name="btnBack" style="color:#FFFFFF; background-color:#222D43; border-color:#D7DBEB; FONT-FAMILY: verdana,arial,helvetica,sans-serif; FONT-SIZE: 7pt; FONT-WEIGHT: normal"></input>&#160;&#160;
				<input type="button" class="boton" onClick="DoCloseSurvey()" value="Cerrar encuesta" name="btnBack" style="color:#FFFFFF; background-color:#222D43; border-color:#D7DBEB; FONT-FAMILY: verdana,arial,helvetica,sans-serif; FONT-SIZE: 7pt; FONT-WEIGHT: normal"></input>&#160;&#160;
				</td>
				</tr>
			</xsl:for-each>
			</form>
		</xsl:if>
		<!-- Ends remaining message -->

		<!-- Starts finish message -->
		<xsl:if test="@page ='finished'">
			<xsl:if test="@redirect !='0'">
				<form method="Post" action="{@redirect}" id="frmSurvey" name="frmSurvey">
				</form>
				<script type="text/javascript">
					window.document.frmSurvey.submit();
				</script>
			</xsl:if>

			<xsl:if test="@redirect ='0'">
				<form method="Post" action="{@address}" id="frmSurvey" name="frmSurvey">
				<xsl:for-each select="finished">
					<tr>
					<td width="40" rowspan="2">
					</td>
					<td>
					<p align="center"><font face="Verdana, Arial, Helvetica, sans-serif" size="1" color="black">
					Has contestado todas las preguntas, con ello se da por terminada la encuesta.
					</font></p>
					<p align="center"><font face="Verdana, Arial, Helvetica, sans-serif" size="1" color="black">
					Tu calificación es de <strong><xsl:value-of select="@score" /></strong>,
					<xsl:if test="@aprove ='1'">
						por lo que has aprobado.
					</xsl:if>
					<xsl:if test="@aprove ='0'">
						   desgraciadamente no has logrado alcanzar la calificación mínima aprobatoria.
					</xsl:if>
					</font></p>
					</td>
					</tr>
					<xsl:if test="@print ='1'">
						<tr>
						<td align="center">
						<br/><input type="button" class="boton" onClick="DoPrintResult('{@window}','{@id}')" value="Imprimir comprobante" name="btnPrint" style="color:#FFFFFF; background-color:#222D43; border-color:#D7DBEB; FONT-FAMILY: verdana,arial,helvetica,sans-serif; FONT-SIZE: 7pt; FONT-WEIGHT: normal"></input>
						</td>
						</tr>
					</xsl:if>
				</xsl:for-each>
				</form>
			</xsl:if>
		</xsl:if>
		<!-- Ends finish message -->

		<!-- Starts develop message -->
		<xsl:if test="@page ='develop'">
			<tr>
			<td>
			<p align="center"><font face="Verdana, Arial, Helvetica, sans-serif" size="1" color="black">
			Por el momento la encuesta <strong><xsl:value-of select="@title" /></strong> se encuentra en temporalmente inhabilitada.
			</font></p>
			<div align="center"><span><font face="Verdana, Arial, Helvetica, sans-serif" size="1" color="black">
			Le suplicamos acceder mas tarde.
			</font></span></div>
			</td>
			</tr>
		</xsl:if>
		<!-- Ends develop message -->

		<!-- Starts denied message -->
		<xsl:if test="@page ='denied'">
			<tr>
			<td width="40" rowspan="2">
			</td>
			<td>
			<p align="center"><font face="Verdana, Arial, Helvetica, sans-serif" size="1" color="black">
			Lo sentimos, el número de oportunidades con las que contabas para presentar la encuesta se han agotado.
			</font></p>
			<p align="center"><font face="Verdana, Arial, Helvetica, sans-serif" size="1" color="black">
			*Para mayor información, consulta al administrador.
			</font></p>
			</td>
			</tr>
		</xsl:if>
		<!-- Ends denied message -->

		</table>

		<xsl:if test="@page ='link'">
			<table>
			<tr>
			<td>
			<a href="{@url}">Ir a la encuesta</a>
			</td>
			</tr>
			</table>
		</xsl:if>

	</xsl:for-each>
</xsl:for-each>
</xsl:template>
</xsl:stylesheet>