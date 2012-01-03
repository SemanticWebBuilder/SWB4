//Constant definitions
var MARKER_SIGNAL = "images/n_senal_b.png";
var MARKER_MESSAGE = "images/n_msj_b.png";
var MARKER_TIMER = "images/n_tmp.png";
var MARKER_RULE = "images/n_cond.png";
var MARKER_MULTI = "images/n_multi_b.png";
var MARKER_PARALLEL = "images/n_paralelo_b.png";
var MARKER_SCALATION = "images/n_escala_b.png";
var MARKER_ERROR = "images/n_error_b.png";
var MARKER_COMPENSATION = "images/n_compensa_b.png";

var ALIGN_CENTER = "center";
var ALIGN_LEFT = "left";
var ALIGN_RIGHT = "right";
var ALIGN_JUSTIFY = "start";

//------------------------------------------------------------------------------

//EditableLabel definition
function EditableLabel(txt) {
    this.text = txt;
    this.multiLine = false;
    this.maxWidth = 50;
    this.textAlign = "center";
}

//EditableLabel basic getters and setters
EditableLabel.prototype.setText = function (txt) {this.text = txt;}
EditableLabel.prototype.getText = function() {return this.text;}

EditableLabel.prototype.render = function (context, x, y) {
    context.save();
    context.textBaseline = 'middle';
    context.fillText(this.text, x, y);
    context.restore();
}

//------------------------------------------------------------------------------

//Point definition
function Point (x, y) {
    this.x = x; //Posición X del punto
    this.y = y; //Posición Y del punto
}

//------------------------------------------------------------------------------

//ColorAdjust definition
function ColorAdjust(red, green, blue) {
    this.r = red;
    this.g = green;
    this.b = blue;
}

//------------------------------------------------------------------------------

//Marker definition
function Marker (image, offset, scale, ca) {
    this.image = new Image();   //Imagen
    this.image.src = image;     
    this.offsetX = offset.x;    //Offset respecto al origen de la forma en X
    this.offsetY = offset.y;    //Offset respecto al origen de la forma en Y
    if (scale <= 0) {
        this.scale = 1;         //Valor para escalar el marcador
    } else {
        this.scale = scale;
    }
    this.colorAdjust = ca;      //Ajuste de color para la imagen del marcador
}

//Marker basic geters and setters
Marker.prototype.setColorAdjust = function (colorAdjust) {this.colorAdjust = colorAdjust;}
Marker.prototype.getColorAdjust = function () {return this.colorAdjust;}

//Render
//Dibuja el marcador sobre el canvas
Marker.prototype.render = function(context, x, y) {
    var imgX = x + this.offsetX;
    var imgY = y + this.offsetY;
    var imgW = this.image.width * this.scale;
    var imgH = this.image.height * this.scale;
    context.save();
    context.drawImage(this.image, imgX, imgY, imgW, imgH);
    context.restore();
}

//------------------------------------------------------------------------------

//Circle definition
function Circle(x, y, r) {
    this.x = x;         //Posición X del orígen del círculo
    this.y = y;         //Posición Y del orígen del círculo
    this.radius = r;    //Radio del círculo
}

//Circle basic getters and setters
Circle.prototype.getRadius = function()  {return this.radius;}
Circle.prototype.setRadius = function(r) {this.radius = r;}
Circle.prototype.getCoords = function()  {return new Point(this.x, this.y);}
Circle.prototype.setCoords = function(p) {this.x = p.x;this.y = p.y;}
Circle.prototype.getSroke  = function()  {return this.stroke;}
Circle.prototype.setStroke = function(s) {this.stroke = s;}
Circle.prototype.getFill   = function()  {return this.fill;}
Circle.prototype.setFill   = function(f) {this.fill = f;}
Circle.prototype.getWidth  = function()  {return this.radius * 2;}
Circle.prototype.getHeight = function()  {return this.radius * 2;}
Circle.prototype.setStrokeWidth = function(sw) {this.strokeWidth = sw;}

//Circle.render
//Dibuja un círculo en el canvas
Circle.prototype.render = function (context) {
    context.save();
    context.strokeStyle = this.stroke;
    context.lineWidth = this.strokeWidth;
    context.fillStyle = this.fill;//"#9DD49D";
    context.beginPath();
    context.arc(this.x,this.y,this.radius,0,Math.PI*2,true);
    context.closePath();
    context.stroke();
    context.translate(this.x-this.radius, this.y-this.radius);
    context.fill();
    context.restore();
}

//Circle.mouseInbounds
//Determia si la posición del mouse se encuentra dentro del círculo
Circle.prototype.mouseInBounds = function (x, y) {
    if (Math.pow(x-this.x, 2) + Math.pow(y-this.y,2) <= Math.pow(this.radius, 2)) {
        return true;
    } else {
        return false;
    }
}

//------------------------------------------------------------------------------

//GraphicalElement definition
function GraphicalElement() {
    this.Id = 0;                    //Identificador único del evento
    this.selected = false;          //Indica si el evento está seleccionado
    this.shape = null;
    this.parent = null;             //Padre del elemento en el modelo
    this.container = null;          //Contenedor del elemento en el modelo
    this.label = null;
    this.STROKE_NORMAL = "#006600"; //Color de línea para el elemento en estado normal
    this.STROKE_OVER = "#FF6060";   //Color de línea para el elemento en estado over
    this.STROKE_FOCUSED = "#98FF00";//Color de línea para el elemento en estado focused
    this.name = "GraphicalElement";
}

//GraphicalElement basic getters and setters
GraphicalElement.prototype.getId = function() {return this.Id;}
GraphicalElement.prototype.setId = function(id) {this.Id = id;}
GraphicalElement.prototype.getLabel = function() {return this.label;}
GraphicalElement.prototype.setLabel = function(lbl) {this.label = lbl;}
GraphicalElement.prototype.setCoords = function(p) {this.shape.setCoords(p);}
GraphicalElement.prototype.getCoords = function() {return this.shape.getCoords();}
GraphicalElement.prototype.setMarker = function(imageMarker, colorAdjust, offset, scale) {
    this.marker = new Marker(imageMarker, offset, scale, colorAdjust);
}

//GraphicalElement.render
//Dibuja el elemento en el canvas
GraphicalElement.prototype.render = function(context) {
    var coords = this.shape.getCoords();
    this.shape.render(context);
    if (this.marker != null) {
        this.marker.render(context, coords.x-this.shape.radius, coords.y-this.shape.radius);
    }
    if (this.label != null) {
        this.label.render(context, coords.x, coords.y);
    }
}

//GraphicalElement.mouseInBounds
//Determina si el cursor se encuentra sobre este elemento
GraphicalElement.prototype.mouseInBounds = function(x, y) {
    return this.shape.mouseInBounds(x,y);
}

//GraphicalElement.hover
//Cambia el estado del elemento a 'hover'
GraphicalElement.prototype.hover = function () {
    this.shape.setStroke(this.STROKE_OVER);
    if (!this.selected) {
        this.over = true;
    }
}

//GraphicalElement.normal
//Cambia el estado del elemento a 'normal'
GraphicalElement.prototype.normal = function () {
    this.shape.setStroke(this.STROKE_NORMAL);
    this.over = this.selected = false;
}

//GraphicalElement.focus
//Cambia el estado del elemento a 'focus'
GraphicalElement.prototype.focus = function () {
    this.shape.setStroke(this.STROKE_FOCUSED);
    this.selected = true;
}

//GraphicalElement.mousePressed
//Manejador del evento 'mousePressed' del elemento
GraphicalElement.prototype.mousePressed = function (e) {
    var mouseButton = getMousePressedButton(e);
    
    if (mouseButton == "LEFT") {
        modeler.unSelectAll();
        this.focus();
    } else if (mouseButton == "RIGHT") {

    }
}

//GraphicalElement.mouseClicked
//Manejador del evento 'mouseClicked' del elemento
GraphicalElement.prototype.mouseClicked = function (e) {
    var mouseButton = getMousePressedButton(e);
    
    if (mouseButton == "LEFT") {
        console.log("Botón izquierdo clickado sobre " + this.Id);
    } else if (mouseButton == "RIGHT") {

    }
}

//GraphicalElement.mouseReleased
//Manejador del evento 'mouseReleased' del elemento
GraphicalElement.prototype.mouseReleased = function (e) {
    var mouseButton = getMousePressedButton(e);
    
    if (mouseButton == "LEFT") {
        console.log("Botón izquierdo liberado sobre " + this.Id);
    } else if (mouseButton == "RIGHT") {

    }
}

//GraphicalElement.mouseMoved
//Manejador del evento 'mouseMoved' del elemento
GraphicalElement.prototype.mouseMoved = function (e) {
    var mouseButton = getMousePressedButton(e);
    
    if (mouseButton == "LEFT") {
        if (!this.selected) {
            modeler.unHoverAll();
            this.hover();
        }
    } else if (mouseButton == "RIGHT") {

    }
}

//------------------------------------------------------------------------------

//Event definition
function Event() {
    GraphicalElement.call(this);
    this.shape = new Circle(0,0,15);//Figura principal del evento
}
Event.prototype = GraphicalElement.prototype;

//------------------------------------------------------------------------------

//CatchEvent definition
function CatchEvent() {
    this.interruptor = false;   //Indica si el evento puede ser interruptor
    this.interrupts = false;    //Indica si el evento interrumpe su tarea asociada
}
CatchEvent.prototype = Event.prototype;

//------------------------------------------------------------------------------

//StartEvent definition
function StartEvent() {
    CatchEvent.call(this);

    //Gradiente para el relleno del evento
    this.gr = modeler.getContext().createLinearGradient(0,0,15,0);
    this.gr.addColorStop(0,"#ffffff");
    this.gr.addColorStop(1,"#9DD49D");
    this.shape.setFill(this.gr);
    this.shape.setStroke(this.STROKE_NORMAL);
    this.shape.setStrokeWidth(3);
}
StartEvent.prototype = Event.prototype;

//------------------------------------------------------------------------------

//NormalStartEvent definition
function NormalStartEvent () {
    StartEvent.call(this);
    this.name = "Normal Start Event";
    this.label = new EditableLabel(this.name);
}
NormalStartEvent.prototype = StartEvent.prototype;

//------------------------------------------------------------------------------

//SigalStartEvent definition
function SignalStartEvent () {
    StartEvent.call(this);
    this.setMarker(MARKER_SIGNAL, new ColorAdjust(10,10,10), new Point(2.5,2.2), 1.15);
}
SignalStartEvent.prototype = StartEvent.prototype;

//------------------------------------------------------------------------------

//SigalStartEvent definition
function MessageStartEvent () {
    StartEvent.call(this);
    this.setMarker(MARKER_MESSAGE, new ColorAdjust(10,10,10), new Point(3.5,5.2), 1.15);
}
MessageStartEvent.prototype = StartEvent.prototype;

//------------------------------------------------------------------------------

//TimerStartEvent definition
function TimerStartEvent () {
    StartEvent.call(this);
    this.setMarker(MARKER_TIMER, new ColorAdjust(10,10,10), new Point(3.6,4.1), 0.95);
}
TimerStartEvent.prototype = StartEvent.prototype;

//------------------------------------------------------------------------------

//RuleStartEvent definition
function RuleStartEvent () {
    StartEvent.call(this);
    this.setMarker(MARKER_RULE, new ColorAdjust(10,10,10), new Point(4.5,4.7), 1);
}
RuleStartEvent.prototype = StartEvent.prototype;

//------------------------------------------------------------------------------

//MultiStartEvent definition
function MultiStartEvent () {
    StartEvent.call(this);
    this.setMarker(MARKER_MULTI, new ColorAdjust(10,10,10), new Point(3.2,2.5), 1);
}
MultiStartEvent.prototype = StartEvent.prototype;

//------------------------------------------------------------------------------

//ParallelStartEvent definition
function ParallelStartEvent () {
    StartEvent.call(this);
    this.setMarker(MARKER_PARALLEL, new ColorAdjust(10,10,10), new Point(4.8,5), 1);
}
ParallelStartEvent.prototype = StartEvent.prototype;

//------------------------------------------------------------------------------

//ScalationStartEvent definition
function ScalationStartEvent () {
    StartEvent.call(this);
    this.setMarker(MARKER_SCALATION, new ColorAdjust(10,10,10), new Point(4.7,3.3), 1);
}
ScalationStartEvent.prototype = StartEvent.prototype;

//------------------------------------------------------------------------------

//ErrorStartEvent definition
function ErrorStartEvent () {
    StartEvent.call(this);
    this.setMarker(MARKER_ERROR, new ColorAdjust(10,10,10), new Point(4.7,5), 1);
}
ErrorStartEvent.prototype = StartEvent.prototype;

//------------------------------------------------------------------------------

//CompensationStartEvent definition
function CompensationStartEvent () {
    StartEvent.call(this);
    this.setMarker(MARKER_COMPENSATION, new ColorAdjust(10,10,10), new Point(3,8), 1);
}
CompensationStartEvent.prototype = StartEvent.prototype;

//------------------------------------------------------------------------------

//IntermediateCatchEventEvent definition
function StartEvent() {
    Event.call(this);

    //Gradiente para el relleno del evento
    this.gr = modeler.getContext().createLinearGradient(0,0,15,0);
    this.gr.addColorStop(0,"#ffffff");
    this.gr.addColorStop(1,"#9DD49D");
    this.shape.setFill(this.gr);
    this.shape.setStroke(this.STROKE_NORMAL);
    this.shape.setStrokeWidth(3);
}
StartEvent.prototype = Event.prototype;

//------------------------------------------------------------------------------

//Modeler definition
function Modeler() {
    this.childs = [];           //Elementos del modelo
    this.selectedElements = []; //Elementos seleccionados del modelo
    this.count = 0;             //Contador de elementos para IDs
    this.cx = 0;                //Posición X actual del mouse en el canvas
    this.cy = 0;                //Posición Y actual del mouse en el canvas
    this.context = null;        //Cotexto de dibujo del canvas
    this.stylePaddingLeft = 0;
    this.stylePaddingTop = 0;
    this.styleBorderLeft = 0;
    this.styleBorderTop = 0;
}

Modeler.prototype.getCanvasElement = function() {return this.drawingCanvas;}
Modeler.prototype.getNewId = function() {return this.count++;}
Modeler.prototype.getContext = function() {return this.context;}
Modeler.prototype.setSelectedTool = function(option) {this.selectedTool = option;}

Modeler.prototype.add = function(ge) {
    this.childs.push(ge);
    ge.render(this.context);
}

//Modeler.addSelectedElement
//Agrega un elemento al conjunto de elementos seleccionados
Modeler.prototype.addSelectedElement = function(ge) {
    this.selectedElements.push(ge);
}

Modeler.prototype.setSelectedElement = function (ge) {
    
}

//Modeler.addSelectedElement
//Agrega un elemento al conjunto de elementos seleccionados
Modeler.prototype.unSelectAll = function() {
    for (var i = this.childs.length - 1; i >= 0; i--) {
        this.childs[i].normal();
    }
}

//Modeler.unHoverAll
//Regresa todos los elementos del modelo a su estatus normal
Modeler.prototype.unHoverAll = function() {
    for (var i = this.childs.length - 1; i >= 0; i--) {
        if (!this.childs[i].selected) {
            this.childs[i].normal();
        }
    }
}

//Modeler.removeSelectedElement
////Elimina un elemento del conjunto de elementos seleccionados
Modeler.prototype.removeSelectedElement = function (ge) {
    for(var i = 0; i<this.selectedElements.length;i++ )
    {
        if(this.selectedElements[i]==ge) {
            this.selectedElements.splice(i,1); 
        }
    } 
}

//Modeler.getOverElement
//Obtiene el elemento del diagrama que se encuentra bajo el puntero del ratón
Modeler.prototype.getOverElement = function(x, y) {
    for (var i = this.childs.length - 1; i >= 0; i--) {
        if (this.childs[i].mouseInBounds(x, y)) {
            return this.childs[i];
        }
    }
    return null;
}

//Modeler.getMousePosition
//Obtiene las coordenadas X,Y del puntero del ratón sobre el canvas
Modeler.prototype.getMousePosition = function (e) { 
    var element = this.drawingCanvas;
    var offsetX = 0, offsetY = 0;

    if (element.offsetParent) {
        do {
            offsetX += element.offsetLeft;
            offsetY += element.offsetTop;
        } while ((element = element.offsetParent));
    }

    offsetX += this.stylePaddingLeft;
    offsetY += this.stylePaddingTop;

    offsetX += this.styleBorderLeft;
    offsetY += this.styleBorderTop;

    this.cx = e.pageX - offsetX;
    this.cy = e.pageY - offsetY

    if (this.cx < 0) this.cx = 0;
    if (this.cy < 0) this.cy = 0;

    return new Point(this.cx, this.cy);
}

//Modeler.init
//Inicializa el modelador de procesos de negocio
Modeler.prototype.init = function(canvasElement) {
    this.drawingCanvas = document.getElementById(canvasElement);

    if(this.drawingCanvas.getContext) {
        this.context = this.drawingCanvas.getContext('2d');
    }

    if (document.defaultView && document.defaultView.getComputedStyle) {
        this.stylePaddingLeft = parseInt(document.defaultView.getComputedStyle(this.drawingCanvas, null)['paddingLeft'], 10) || 0;
        this.stylePaddingTop = parseInt(document.defaultView.getComputedStyle(this.drawingCanvas, null)['paddingTop'], 10) || 0;
        this.styleBorderLeft = parseInt(document.defaultView.getComputedStyle(this.drawingCanvas, null)['borderLeftWidth'], 10) || 0;
        this.styleBorderTop = parseInt(document.defaultView.getComputedStyle(this.drawingCanvas, null)['borderTopWidth'], 10) || 0;
    }

    this.drawingCanvas.addEventListener('mousemove', onMouseMoved, false);
    this.drawingCanvas.addEventListener('click', onMouseClicked, false);
    this.drawingCanvas.addEventListener('mousedown', onMousePressed, false);
    this.drawingCanvas.addEventListener('mouseup', onMouseReleased, false);
    //Inhibe la aparición del menú contextual del navegador al presionar el botón derecho del ratón
    this.drawingCanvas.oncontextmenu = function () {return false;}
}

//Modeler.render
//Actualiza el canvas y dibuja todos los elementos del diagrama
Modeler.prototype.render = function () {
    this.context.clearRect(0,0,this.drawingCanvas.width, this.drawingCanvas.height);
    for (var i = 0; i < this.childs.length; i++) {
        this.childs[i].render(this.context);
    }
}

Modeler.prototype.getMousePosition = function (e) { 
	var element = this.drawingCanvas;
	var offsetX = 0, offsetY = 0;
	
	if (element.offsetParent) {
		do {
		  offsetX += element.offsetLeft;
		  offsetY += element.offsetTop;
		} while ((element = element.offsetParent));
	}

	offsetX += this.stylePaddingLeft;
	offsetY += this.stylePaddingTop;

	offsetX += this.styleBorderLeft;
	offsetY += this.styleBorderTop;

	this.cx = e.pageX - offsetX;
	this.cy = e.pageY - offsetY
	
	if (this.cx < 0) this.cx = 0;
	if (this.cy < 0) this.cy = 0;
	
	return new Point(this.cx, this.cy);
}


Modeler.prototype.mouseClicked = function(e) {
    var mouseCoords = this.getMousePosition(e);
    var ele = this.getOverElement(mouseCoords.x, mouseCoords.y);
    
    if (ele != null) {
        ele.mouseClicked(e);
    } else {
        this.unSelectAll();
    }
    this.render();
}

//Modeler.mousePressed
//Manejador del evento onMousePressed del canvas
Modeler.prototype.mousePressed = function(e) {
    var mouseButton = getMousePressedButton(e);
    var mouseCoords = this.getMousePosition(e);
    var ele = this.getOverElement(mouseCoords.x, mouseCoords.y);
    
    if (mouseButton == "LEFT") {
        if (this.selectedTool != null) {
            this.selectedTool.setCoords(new Point(this.cx, this.cy));
            this.add(this.selectedTool);
            this.selectedTool.render(this.context);
            this.selectedTool = null;
        } else {
            if (ele != null) {
                ele.mousePressed(e);
            }
        }
    }
    this.render();
}

Modeler.prototype.mouseMoved = function(e) {
    var mouseCoords = this.getMousePosition(e);
    var ele = this.getOverElement(mouseCoords.x, mouseCoords.y);
    
    if (ele != null) {
        ele.mouseMoved(e);
    } else {
        this.unHoverAll();
    }
    
//	this.getMousePosition(e);
//	if (!this.isDragging) {
//		var hovered = false;
//		for (var i = this.childs.length - 1; i >= 0; i--) {
//			if (this.childs[i].mouseInBounds(this.cx, this.cy) && !hovered) {
//				//console.log(this.childs[i].getId() + " selected= " + this.childs[i].selected);
//				if (!this.childs[i].selected) {
//					this.childs[i].hover();
//					hovered = true;
//				}
//			} else {
//				if (!this.childs[i].selected) {
//					this.childs[i].normal();
//				}
//			}
//		}
//	} else {
//		console.log("Modeler está en modo drag");
//		for (var i = this.childs.length - 1; i >= 0; i--) {
//			if (this.childs[i].mouseInBounds(this.dx, this.dy)) {
//				var deltaX = this.cx - this.dx;
//				var deltaY = this.cy - this.dy;
//				var cc = this.childs[i].getCoords();
//				this.childs[i].setCoords(new Point(cc.x + deltaX, cc.y + deltaY));
//				console.log("Hay que actualizar posicion de "+this.childs[i].getId());
//			}
//		}
//	}
	this.render();
}

Modeler.prototype.mouseReleased = function(e) {
    var mouseCoords = this.getMousePosition(e);
    var ele = this.getOverElement(mouseCoords.x, mouseCoords.y);
    
    if (ele != null) {
        ele.mouseReleased(e);
    }
	//this.dx = this.cx - this.dx;
	//this.dy = this.cy - this.dy;
	//console.log("End point: "+this.cx+", "+this.cy);
	//console.log("Diferencia X,Y:"+this.dx+", "+this.dy);
//	this.isDragging = false;
    this.render();
}

function onMouseMoved(e) {
    modeler.mouseMoved(e);
}

function onMousePressed(e) {
    modeler.mousePressed(e);
}

function onMouseClicked(e) {
    modeler.mouseClicked(e);
}

function onMouseReleased(e) {
    modeler.mouseReleased(e);
}

function getMousePressedButton(e) {
    if (e.button) {
        return (e.button == 0) ? "LEFT" :
                ((e.button == 1) ? "MIDDLE" : "RIGHT");
    } else if (e.which) {
        return (e.which == 1) ? "LEFT" :
                ((e.which == 2) ? "MIDDLE" : "RIGHT");
    }
}