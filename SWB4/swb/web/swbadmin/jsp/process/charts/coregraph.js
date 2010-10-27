(function(){function a(){if(a.is(arguments[0],G)){var b=arguments[0],d=bV[m](a,b.splice(0,3+a.is(b[0],E))),e=d.set();for(var g=0,h=b[w];g<h;g++){var i=b[g]||{};c[f](i.type)&&e[L](d[i.type]().attr(i))}return e}return bV[m](a,arguments)}a.version="1.5.2";var b=/[, ]+/,c={circle:1,rect:1,path:1,ellipse:1,text:1,image:1},d=/\{(\d+)\}/g,e="prototype",f="hasOwnProperty",g=document,h=window,i={was:Object[e][f].call(h,"Raphael"),is:h.Raphael},j=function(){this.customAttributes={}},k,l="appendChild",m="apply",n="concat",o="createTouch"in g,p="",q=" ",r=String,s="split",t="click dblclick mousedown mousemove mouseout mouseover mouseup touchstart touchmove touchend orientationchange touchcancel gesturestart gesturechange gestureend"[s](q),u={mousedown:"touchstart",mousemove:"touchmove",mouseup:"touchend"},v="join",w="length",x=r[e].toLowerCase,y=Math,z=y.max,A=y.min,B=y.abs,C=y.pow,D=y.PI,E="number",F="string",G="array",H="toString",I="fill",J=Object[e][H],K={},L="push",M=/^url\(['"]?([^\)]+?)['"]?\)$/i,N=/^\s*((#[a-f\d]{6})|(#[a-f\d]{3})|rgba?\(\s*([\d\.]+%?\s*,\s*[\d\.]+%?\s*,\s*[\d\.]+(?:%?\s*,\s*[\d\.]+)?)%?\s*\)|hsba?\(\s*([\d\.]+(?:deg|\xb0|%)?\s*,\s*[\d\.]+%?\s*,\s*[\d\.]+(?:%?\s*,\s*[\d\.]+)?)%?\s*\)|hsla?\(\s*([\d\.]+(?:deg|\xb0|%)?\s*,\s*[\d\.]+%?\s*,\s*[\d\.]+(?:%?\s*,\s*[\d\.]+)?)%?\s*\))\s*$/i,O={"NaN":1,Infinity:1,"-Infinity":1},P=/^(?:cubic-)?bezier\(([^,]+),([^,]+),([^,]+),([^\)]+)\)/,Q=y.round,R="setAttribute",S=parseFloat,T=parseInt,U=" progid:DXImageTransform.Microsoft",V=r[e].toUpperCase,W={blur:0,"clip-rect":"0 0 1e9 1e9",cursor:"default",cx:0,cy:0,fill:"#fff","fill-opacity":1,font:"10px \"Arial\"","font-family":"\"Arial\"","font-size":"10","font-style":"normal","font-weight":400,gradient:0,height:0,href:"http://raphaeljs.com/",opacity:1,path:"M0,0",r:0,rotation:0,rx:0,ry:0,scale:"1 1",src:"",stroke:"#000","stroke-dasharray":"","stroke-linecap":"butt","stroke-linejoin":"butt","stroke-miterlimit":0,"stroke-opacity":1,"stroke-width":1,target:"_blank","text-anchor":"middle",title:"Raphael",translation:"0 0",width:0,x:0,y:0},X={along:"along",blur:E,"clip-rect":"csv",cx:E,cy:E,fill:"colour","fill-opacity":E,"font-size":E,height:E,opacity:E,path:"path",r:E,rotation:"csv",rx:E,ry:E,scale:"csv",stroke:"colour","stroke-opacity":E,"stroke-width":E,translation:"csv",width:E,x:E,y:E},Y="replace",Z=/^(from|to|\d+%?)$/,$=/\s*,\s*/,_={hs:1,rg:1},ba=/,?([achlmqrstvxz]),?/gi,bb=/([achlmqstvz])[\s,]*((-?\d*\.?\d*(?:e[-+]?\d+)?\s*,?\s*)+)/ig,bc=/(-?\d*\.?\d*(?:e[-+]?\d+)?)\s*,?\s*/ig,bd=/^r(?:\(([^,]+?)\s*,\s*([^\)]+?)\))?/,be=function(a,b){return a.key-b.key};a.type=h.SVGAngle||g.implementation.hasFeature("http://www.w3.org/TR/SVG11/feature#BasicStructure","1.1")?"SVG":"VML";if(a.type=="VML"){var bf=g.createElement("div"),bg;bf.innerHTML="<v:shape adj=\"1\"/>";bg=bf.firstChild;bg.style.behavior="url(#default#VML)";if(!(bg&&typeof bg.adj=="object"))return a.type=null;bf=null}a.svg=!(a.vml=a.type=="VML");j[e]=a[e];k=j[e];a._id=0;a._oid=0;a.fn={};a.is=function(a,b){b=x.call(b);if(b=="finite")return!O[f](+a);return b=="null"&&a===null||b==typeof a||b=="object"&&a===Object(a)||b=="array"&&Array.isArray&&Array.isArray(a)||J.call(a).slice(8,-1).toLowerCase()==b};a.angle=function(b,c,d,e,f,g){{if(f==null){var h=b-d,i=c-e;if(!h&&!i)return 0;return((h<0)*180+y.atan(-i/-h)*180/D+360)%360}return a.angle(b,c,f,g)-a.angle(d,e,f,g)}};a.rad=function(a){return a%360*D/180};a.deg=function(a){return a*180/D%360};a.snapTo=function(b,c,d){d=a.is(d,"finite")?d:10;if(a.is(b,G)){var e=b.length;while(e--)if(B(b[e]-c)<=d)return b[e]}else{b=+b;var f=c%b;if(f<d)return c-f;if(f>b-d)return c-f+b}return c};function bh(){var a=[],b=0;for(;b<32;b++)a[b]=(~(~(y.random()*16)))[H](16);a[12]=4;a[16]=(a[16]&3|8)[H](16);return"r-"+a[v]("")}a.setWindow=function(a){h=a;g=h.document};var bi=function(b){if(a.vml){var c=/^\s+|\s+$/g,d;try{var e=new ActiveXObject("htmlfile");e.write("<body>");e.close();d=e.body}catch(a){d=createPopup().document.body}var f=d.createTextRange();bi=bm(function(a){try{d.style.color=r(a)[Y](c,p);var b=f.queryCommandValue("ForeColor");b=(b&255)<<16|b&65280|(b&16711680)>>>16;return"#"+("000000"+b[H](16)).slice(-6)}catch(a){return"none"}})}else{var h=g.createElement("i");h.title="Raphaël Colour Picker";h.style.display="none";g.body[l](h);bi=bm(function(a){h.style.color=a;return g.defaultView.getComputedStyle(h,p).getPropertyValue("color")})}return bi(b)},bj=function(){return"hsb("+[this.h,this.s,this.b]+")"},bk=function(){return"hsl("+[this.h,this.s,this.l]+")"},bl=function(){return this.hex};a.hsb2rgb=function(b,c,d,e){if(a.is(b,"object")&&"h"in b&&"s"in b&&"b"in b){d=b.b;c=b.s;b=b.h;e=b.o}return a.hsl2rgb(b,c,d/2,e)};a.hsl2rgb=function(b,c,d,e){if(a.is(b,"object")&&"h"in b&&"s"in b&&"l"in b){d=b.l;c=b.s;b=b.h}if(b>1||c>1||d>1){b/=360;c/=100;d/=100}var f={},g=["r","g","b"],h,i,j,k,l,m;if(c){d<0.5?h=d*(1+c):h=d+c-d*c;i=2*d-h;for(var n=0;n<3;n++){j=b+1/3*-(n-1);j<0&&j++;j>1&&j--;j*6<1?f[g[n]]=i+(h-i)*6*j:j*2<1?f[g[n]]=h:j*3<2?f[g[n]]=i+(h-i)*(2/3-j)*6:f[g[n]]=i}}else f={r:d,g:d,b:d};f.r*=255;f.g*=255;f.b*=255;f.hex="#"+(16777216|f.b|f.g<<8|f.r<<16).toString(16).slice(1);a.is(e,"finite")&&(f.opacity=e);f.toString=bl;return f};a.rgb2hsb=function(b,c,d){if(c==null&&a.is(b,"object")&&"r"in b&&"g"in b&&"b"in b){d=b.b;c=b.g;b=b.r}if(c==null&&a.is(b,F)){var e=a.getRGB(b);b=e.r;c=e.g;d=e.b}if(b>1||c>1||d>1){b/=255;c/=255;d/=255}var f=z(b,c,d),g=A(b,c,d),h,i,j=f;{if(g==f)return{h:0,s:0,b:f,toString:bj};var k=f-g;i=k/f;b==f?h=(c-d)/k:c==f?h=2+(d-b)/k:h=4+(b-c)/k;h/=6;h<0&&h++;h>1&&h--}return{h:h,s:i,b:j,toString:bj}};a.rgb2hsl=function(b,c,d){if(c==null&&a.is(b,"object")&&"r"in b&&"g"in b&&"b"in b){d=b.b;c=b.g;b=b.r}if(c==null&&a.is(b,F)){var e=a.getRGB(b);b=e.r;c=e.g;d=e.b}if(b>1||c>1||d>1){b/=255;c/=255;d/=255}var f=z(b,c,d),g=A(b,c,d),h,i,j=(f+g)/2,k;if(g==f)k={h:0,s:0,l:j};else{var l=f-g;i=j<0.5?l/(f+g):l/(2-f-g);b==f?h=(c-d)/l:c==f?h=2+(d-b)/l:h=4+(b-c)/l;h/=6;h<0&&h++;h>1&&h--;k={h:h,s:i,l:j}}k.toString=bk;return k};a._path2string=function(){return this.join(",")[Y](ba,"$1")};function bm(a,b,c){function d(){var g=Array[e].slice.call(arguments,0),h=g[v]("?"),i=d.cache=d.cache||{},j=d.count=d.count||[];if(i[f](h))return c?c(i[h]):i[h];j[w]>=1000&&delete i[j.shift()];j[L](h);i[h]=a[m](b,g);return c?c(i[h]):i[h]}return d}a.getRGB=bm(function(b){if(!b||!(!((b=r(b)).indexOf("-")+1)))return{r:-1,g:-1,b:-1,hex:"none",error:1};if(b=="none")return{r:-1,g:-1,b:-1,hex:"none"};!(_[f](b.toLowerCase().substring(0,2))||b.charAt()=="#")&&(b=bi(b));var c,d,e,g,h,i,j,k=b.match(N);if(k){if(k[2]){g=T(k[2].substring(5),16);e=T(k[2].substring(3,5),16);d=T(k[2].substring(1,3),16)}if(k[3]){g=T((i=k[3].charAt(3))+i,16);e=T((i=k[3].charAt(2))+i,16);d=T((i=k[3].charAt(1))+i,16)}if(k[4]){j=k[4][s]($);d=S(j[0]);j[0].slice(-1)=="%"&&(d*=2.55);e=S(j[1]);j[1].slice(-1)=="%"&&(e*=2.55);g=S(j[2]);j[2].slice(-1)=="%"&&(g*=2.55);k[1].toLowerCase().slice(0,4)=="rgba"&&(h=S(j[3]));j[3]&&j[3].slice(-1)=="%"&&(h/=100)}if(k[5]){j=k[5][s]($);d=S(j[0]);j[0].slice(-1)=="%"&&(d*=2.55);e=S(j[1]);j[1].slice(-1)=="%"&&(e*=2.55);g=S(j[2]);j[2].slice(-1)=="%"&&(g*=2.55);(j[0].slice(-3)=="deg"||j[0].slice(-1)=="°")&&(d/=360);k[1].toLowerCase().slice(0,4)=="hsba"&&(h=S(j[3]));j[3]&&j[3].slice(-1)=="%"&&(h/=100);return a.hsb2rgb(d,e,g,h)}if(k[6]){j=k[6][s]($);d=S(j[0]);j[0].slice(-1)=="%"&&(d*=2.55);e=S(j[1]);j[1].slice(-1)=="%"&&(e*=2.55);g=S(j[2]);j[2].slice(-1)=="%"&&(g*=2.55);(j[0].slice(-3)=="deg"||j[0].slice(-1)=="°")&&(d/=360);k[1].toLowerCase().slice(0,4)=="hsla"&&(h=S(j[3]));j[3]&&j[3].slice(-1)=="%"&&(h/=100);return a.hsl2rgb(d,e,g,h)}k={r:d,g:e,b:g};k.hex="#"+(16777216|g|e<<8|d<<16).toString(16).slice(1);a.is(h,"finite")&&(k.opacity=h);return k}return{r:-1,g:-1,b:-1,hex:"none",error:1}},a);a.getColor=function(a){var b=this.getColor.start=this.getColor.start||{h:0,s:1,b:a||0.75},c=this.hsb2rgb(b.h,b.s,b.b);b.h+=0.075;if(b.h>1){b.h=0;b.s-=0.2;b.s<=0&&(this.getColor.start={h:0,s:1,b:b.b})}return c.hex};a.getColor.reset=function(){delete this.start};a.parsePathString=bm(function(b){if(!b)return null;var c={a:7,c:6,h:1,l:2,m:2,q:4,s:4,t:2,v:1,z:0},d=[];a.is(b,G)&&a.is(b[0],G)&&(d=bo(b));d[w]||r(b)[Y](bb,function(a,b,e){var f=[],g=x.call(b);e[Y](bc,function(a,b){b&&f[L](+b)});if(g=="m"&&f[w]>2){d[L]([b][n](f.splice(0,2)));g="l";b=b=="m"?"l":"L"}while(f[w]>=c[g]){d[L]([b][n](f.splice(0,c[g])));if(!c[g])break}});d[H]=a._path2string;return d});a.findDotsAtSegment=function(a,b,c,d,e,f,g,h,i){var j=1-i,k=C(j,3)*a+C(j,2)*3*i*c+j*3*i*i*e+C(i,3)*g,l=C(j,3)*b+C(j,2)*3*i*d+j*3*i*i*f+C(i,3)*h,m=a+2*i*(c-a)+i*i*(e-2*c+a),n=b+2*i*(d-b)+i*i*(f-2*d+b),o=c+2*i*(e-c)+i*i*(g-2*e+c),p=d+2*i*(f-d)+i*i*(h-2*f+d),q=(1-i)*a+i*c,r=(1-i)*b+i*d,s=(1-i)*e+i*g,t=(1-i)*f+i*h,u=90-y.atan((m-o)/(n-p))*180/D;(m>o||n<p)&&(u+=180);return{x:k,y:l,m:{x:m,y:n},n:{x:o,y:p},start:{x:q,y:r},end:{x:s,y:t},alpha:u}};var bn=bm(function(a){if(!a)return{x:0,y:0,width:0,height:0};a=bw(a);var b=0,c=0,d=[],e=[],f;for(var g=0,h=a[w];g<h;g++){f=a[g];if(f[0]=="M"){b=f[1];c=f[2];d[L](b);e[L](c)}else{var i=bv(b,c,f[1],f[2],f[3],f[4],f[5],f[6]);d=d[n](i.min.x,i.max.x);e=e[n](i.min.y,i.max.y);b=f[5];c=f[6]}}var j=A[m](0,d),k=A[m](0,e);return{x:j,y:k,width:z[m](0,d)-j,height:z[m](0,e)-k}}),bo=function(b){var c=[];if(!a.is(b,G)||!a.is(b&&b[0],G))b=a.parsePathString(b);for(var d=0,e=b[w];d<e;d++){c[d]=[];for(var f=0,g=b[d][w];f<g;f++)c[d][f]=b[d][f]}c[H]=a._path2string;return c},bp=bm(function(b){if(!a.is(b,G)||!a.is(b&&b[0],G))b=a.parsePathString(b);var c=[],d=0,e=0,f=0,g=0,h=0;if(b[0][0]=="M"){d=b[0][1];e=b[0][2];f=d;g=e;h++;c[L](["M",d,e])}for(var i=h,j=b[w];i<j;i++){var k=c[i]=[],l=b[i];if(l[0]!=x.call(l[0])){k[0]=x.call(l[0]);switch(k[0]){case"a":k[1]=l[1];k[2]=l[2];k[3]=l[3];k[4]=l[4];k[5]=l[5];k[6]=+(l[6]-d).toFixed(3);k[7]=+(l[7]-e).toFixed(3);break;case"v":k[1]=+(l[1]-e).toFixed(3);break;case"m":f=l[1];g=l[2];default:for(var m=1,n=l[w];m<n;m++)k[m]=+(l[m]-(m%2?d:e)).toFixed(3)}}else{k=c[i]=[];if(l[0]=="m"){f=l[1]+d;g=l[2]+e}for(var o=0,p=l[w];o<p;o++)c[i][o]=l[o]}var q=c[i][w];switch(c[i][0]){case"z":d=f;e=g;break;case"h":d+=+c[i][q-1];break;case"v":e+=+c[i][q-1];break;default:d+=+c[i][q-2];e+=+c[i][q-1]}}c[H]=a._path2string;return c},0,bo),bq=bm(function(b){if(!a.is(b,G)||!a.is(b&&b[0],G))b=a.parsePathString(b);var c=[],d=0,e=0,f=0,g=0,h=0;if(b[0][0]=="M"){d=+b[0][1];e=+b[0][2];f=d;g=e;h++;c[0]=["M",d,e]}for(var i=h,j=b[w];i<j;i++){var k=c[i]=[],l=b[i];if(l[0]!=V.call(l[0])){k[0]=V.call(l[0]);switch(k[0]){case"A":k[1]=l[1];k[2]=l[2];k[3]=l[3];k[4]=l[4];k[5]=l[5];k[6]=+(l[6]+d);k[7]=+(l[7]+e);break;case"V":k[1]=+l[1]+e;break;case"H":k[1]=+l[1]+d;break;case"M":f=+l[1]+d;g=+l[2]+e;default:for(var m=1,n=l[w];m<n;m++)k[m]=+l[m]+(m%2?d:e)}}else for(var o=0,p=l[w];o<p;o++)c[i][o]=l[o];switch(k[0]){case"Z":d=f;e=g;break;case"H":d=k[1];break;case"V":e=k[1];break;case"M":f=c[i][c[i][w]-2];g=c[i][c[i][w]-1];default:d=c[i][c[i][w]-2];e=c[i][c[i][w]-1]}}c[H]=a._path2string;return c},null,bo),br=function(a,b,c,d){return[a,b,c,d,c,d]},bs=function(a,b,c,d,e,f){var g=1/3,h=2/3;return[g*a+h*c,g*b+h*d,g*e+h*c,g*f+h*d,e,f]},bt=function(a,b,c,d,e,f,g,h,i,j){var k=D*120/180,l=D/180*(+e||0),m=[],o,p=bm(function(a,b,c){var d=a*y.cos(c)-b*y.sin(c),e=a*y.sin(c)+b*y.cos(c);return{x:d,y:e}});if(j){G=j[0];H=j[1];E=j[2];F=j[3]}else{o=p(a,b,-l);a=o.x;b=o.y;o=p(h,i,-l);h=o.x;i=o.y;var q=y.cos(D/180*e),r=y.sin(D/180*e),t=(a-h)/2,u=(b-i)/2,x=t*t/(c*c)+u*u/(d*d);if(x>1){x=y.sqrt(x);c=x*c;d=x*d}var z=c*c,A=d*d,C=(f==g?-1:1)*y.sqrt(B((z*A-z*u*u-A*t*t)/(z*u*u+A*t*t))),E=C*c*u/d+(a+h)/2,F=C*-d*t/c+(b+i)/2,G=y.asin(((b-F)/d).toFixed(9)),H=y.asin(((i-F)/d).toFixed(9));G=a<E?D-G:G;H=h<E?D-H:H;G<0&&(G=D*2+G);H<0&&(H=D*2+H);g&&G>H&&(G=G-D*2);!g&&H>G&&(H=H-D*2)}var I=H-G;if(B(I)>k){var J=H,K=h,L=i;H=G+k*(g&&H>G?1:-1);h=E+c*y.cos(H);i=F+d*y.sin(H);m=bt(h,i,c,d,e,0,g,K,L,[H,J,E,F])}I=H-G;var M=y.cos(G),N=y.sin(G),O=y.cos(H),P=y.sin(H),Q=y.tan(I/4),R=4/3*c*Q,S=4/3*d*Q,T=[a,b],U=[a+R*N,b-S*M],V=[h+R*P,i-S*O],W=[h,i];U[0]=2*T[0]-U[0];U[1]=2*T[1]-U[1];{if(j)return[U,V,W][n](m);m=[U,V,W][n](m)[v]()[s](",");var X=[];for(var Y=0,Z=m[w];Y<Z;Y++)X[Y]=Y%2?p(m[Y-1],m[Y],l).y:p(m[Y],m[Y+1],l).x;return X}},bu=function(a,b,c,d,e,f,g,h,i){var j=1-i;return{x:C(j,3)*a+C(j,2)*3*i*c+j*3*i*i*e+C(i,3)*g,y:C(j,3)*b+C(j,2)*3*i*d+j*3*i*i*f+C(i,3)*h}},bv=bm(function(a,b,c,d,e,f,g,h){var i=e-2*c+a-(g-2*e+c),j=2*(c-a)-2*(e-c),k=a-c,l=(-j+y.sqrt(j*j-4*i*k))/2/i,n=(-j-y.sqrt(j*j-4*i*k))/2/i,o=[b,h],p=[a,g],q;B(l)>"1e12"&&(l=0.5);B(n)>"1e12"&&(n=0.5);if(l>0&&l<1){q=bu(a,b,c,d,e,f,g,h,l);p[L](q.x);o[L](q.y)}if(n>0&&n<1){q=bu(a,b,c,d,e,f,g,h,n);p[L](q.x);o[L](q.y)}i=f-2*d+b-(h-2*f+d);j=2*(d-b)-2*(f-d);k=b-d;l=(-j+y.sqrt(j*j-4*i*k))/2/i;n=(-j-y.sqrt(j*j-4*i*k))/2/i;B(l)>"1e12"&&(l=0.5);B(n)>"1e12"&&(n=0.5);if(l>0&&l<1){q=bu(a,b,c,d,e,f,g,h,l);p[L](q.x);o[L](q.y)}if(n>0&&n<1){q=bu(a,b,c,d,e,f,g,h,n);p[L](q.x);o[L](q.y)}return{min:{x:A[m](0,p),y:A[m](0,o)},max:{x:z[m](0,p),y:z[m](0,o)}}}),bw=bm(function(a,b){var c=bq(a),d=b&&bq(b),e={x:0,y:0,bx:0,by:0,X:0,Y:0,qx:null,qy:null},f={x:0,y:0,bx:0,by:0,X:0,Y:0,qx:null,qy:null},g=function(a,b){var c,d;if(!a)return["C",b.x,b.y,b.x,b.y,b.x,b.y];!(a[0]in{T:1,Q:1})&&(b.qx=b.qy=null);switch(a[0]){case"M":b.X=a[1];b.Y=a[2];break;case"A":a=["C"][n](bt[m](0,[b.x,b.y][n](a.slice(1))));break;case"S":c=b.x+(b.x-(b.bx||b.x));d=b.y+(b.y-(b.by||b.y));a=["C",c,d][n](a.slice(1));break;case"T":b.qx=b.x+(b.x-(b.qx||b.x));b.qy=b.y+(b.y-(b.qy||b.y));a=["C"][n](bs(b.x,b.y,b.qx,b.qy,a[1],a[2]));break;case"Q":b.qx=a[1];b.qy=a[2];a=["C"][n](bs(b.x,b.y,a[1],a[2],a[3],a[4]));break;case"L":a=["C"][n](br(b.x,b.y,a[1],a[2]));break;case"H":a=["C"][n](br(b.x,b.y,a[1],b.y));break;case"V":a=["C"][n](br(b.x,b.y,b.x,a[1]));break;case"Z":a=["C"][n](br(b.x,b.y,b.X,b.Y));break}return a},h=function(a,b){if(a[b][w]>7){a[b].shift();var e=a[b];while(e[w])a.splice(b++,0,["C"][n](e.splice(0,6)));a.splice(b,1);k=z(c[w],d&&d[w]||0)}},i=function(a,b,e,f,g){if(a&&b&&a[g][0]=="M"&&b[g][0]!="M"){b.splice(g,0,["M",f.x,f.y]);e.bx=0;e.by=0;e.x=a[g][1];e.y=a[g][2];k=z(c[w],d&&d[w]||0)}};for(var j=0,k=z(c[w],d&&d[w]||0);j<k;j++){c[j]=g(c[j],e);h(c,j);d&&(d[j]=g(d[j],f));d&&h(d,j);i(c,d,e,f,j);i(d,c,f,e,j);var l=c[j],o=d&&d[j],p=l[w],q=d&&o[w];e.x=l[p-2];e.y=l[p-1];e.bx=S(l[p-4])||e.x;e.by=S(l[p-3])||e.y;f.bx=d&&(S(o[q-4])||f.x);f.by=d&&(S(o[q-3])||f.y);f.x=d&&o[q-2];f.y=d&&o[q-1]}return d?[c,d]:c},null,bo),bx=bm(function(b){var c=[];for(var d=0,e=b[w];d<e;d++){var f={},g=b[d].match(/^([^:]*):?([\d\.]*)/);f.color=a.getRGB(g[1]);if(f.color.error)return null;f.color=f.color.hex;g[2]&&(f.offset=g[2]+"%");c[L](f)}for(d=1,e=c[w]-1;d<e;d++){if(!c[d].offset){var h=S(c[d-1].offset||0),i=0;for(var j=d+1;j<e;j++){if(c[j].offset){i=c[j].offset;break}}if(!i){i=100;j=e}i=S(i);var k=(i-h)/(j-d+1);for(;d<j;d++){h+=k;c[d].offset=h+"%"}}}return c}),by=function(b,c,d,e){var f;if(a.is(b,F)||a.is(b,"object")){f=a.is(b,F)?g.getElementById(b):b;if(f.tagName)return c==null?{container:f,width:f.style.pixelWidth||f.offsetWidth,height:f.style.pixelHeight||f.offsetHeight}:{container:f,width:c,height:d}}else return{container:1,x:b,y:c,width:d,height:e}},bz=function(a,b){var c=this;for(var d in b){if(b[f](d)&&!(d in a))switch(typeof b[d]){case"function":(function(b){a[d]=a===c?b:function(){return b[m](c,arguments)}})(b[d]);break;case"object":a[d]=a[d]||{};bz.call(this,a[d],b[d]);break;default:a[d]=b[d];break}}},bA=function(a,b){a==b.top&&(b.top=a.prev);a==b.bottom&&(b.bottom=a.next);a.next&&(a.next.prev=a.prev);a.prev&&(a.prev.next=a.next)},bB=function(a,b){if(b.top===a)return;bA(a,b);a.next=null;a.prev=b.top;b.top.next=a;b.top=a},bC=function(a,b){if(b.bottom===a)return;bA(a,b);a.next=b.bottom;a.prev=null;b.bottom.prev=a;b.bottom=a},bD=function(a,b,c){bA(a,c);b==c.top&&(c.top=a);b.next&&(b.next.prev=a);a.next=b.next;a.prev=b;b.next=a},bE=function(a,b,c){bA(a,c);b==c.bottom&&(c.bottom=a);b.prev&&(b.prev.next=a);a.prev=b.prev;b.prev=a;a.next=b},bF=function(a){return function(){throw new Error("Raphaël: you are calling to method “"+a+"” of removed object")}};a.pathToRelative=bp;if(a.svg){k.svgns="http://www.w3.org/2000/svg";k.xlink="http://www.w3.org/1999/xlink";Q=function(a){return+a+(~(~a)===a)*0.5};var bG=function(a,b){if(b)for(var c in b)b[f](c)&&a[R](c,r(b[c]));else{a=g.createElementNS(k.svgns,a);a.style.webkitTapHighlightColor="rgba(0,0,0,0)";return a}};a[H]=function(){return"Your browser supports SVG.\nYou are running Raphaël "+this.version};var bH=function(a,b){var c=bG("path");b.canvas&&b.canvas[l](c);var d=new bN(c,b);d.type="path";bK(d,{fill:"none",stroke:"#000",path:a});return d},bI=function(a,b,c){var d="linear",e=0.5,f=0.5,h=a.style;b=r(b)[Y](bd,function(a,b,c){d="radial";if(b&&c){e=S(b);f=S(c);var g=(f>0.5)*2-1;C(e-0.5,2)+C(f-0.5,2)>0.25&&(f=y.sqrt(0.25-C(e-0.5,2))*g+0.5)&&f!=0.5&&(f=f.toFixed(5)-0.00001*g)}return p});b=b[s](/\s*\-\s*/);if(d=="linear"){var i=b.shift();i=-S(i);if(isNaN(i))return null;var j=[0,0,y.cos(i*D/180),y.sin(i*D/180)],k=1/(z(B(j[2]),B(j[3]))||1);j[2]*=k;j[3]*=k;if(j[2]<0){j[0]=-j[2];j[2]=0}if(j[3]<0){j[1]=-j[3];j[3]=0}}var m=bx(b);if(!m)return null;var n=a.getAttribute(I);n=n.match(/^url\(#(.*)\)$/);n&&c.defs.removeChild(g.getElementById(n[1]));var o=bG(d+"Gradient");o.id=bh();bG(o,d=="radial"?{fx:e,fy:f}:{x1:j[0],y1:j[1],x2:j[2],y2:j[3]});c.defs[l](o);for(var q=0,t=m[w];q<t;q++){var u=bG("stop");bG(u,{offset:m[q].offset?m[q].offset:q?"100%":"0%","stop-color":m[q].color||"#fff"});o[l](u)}bG(a,{fill:"url(#"+o.id+")",opacity:1,"fill-opacity":1});h.fill=p;h.opacity=1;h.fillOpacity=1;return 1},bJ=function(b){var c=b.getBBox();bG(b.pattern,{patternTransform:a.format("translate({0},{1})",c.x,c.y)})},bK=function(c,d){var e={"":[0],none:[0],"-":[3,1],".":[1,1],"-.":[3,1,1,1],"-..":[3,1,1,1,1,1],". ":[1,3],"- ":[4,3],"--":[8,3],"- .":[4,3,1,3],"--.":[8,3,1,3],"--..":[8,3,1,3,1,3]},h=c.node,i=c.attrs,j=c.rotate(),k=function(a,b){b=e[x.call(b)];if(b){var c=a.attrs["stroke-width"]||"1",f=({round:c,square:c,butt:0})[a.attrs["stroke-linecap"]||d["stroke-linecap"]]||0,g=[],i=b[w];while(i--)g[i]=b[i]*c+(i%2?1:-1)*f;bG(h,{"stroke-dasharray":g[v](",")})}};d[f]("rotation")&&(j=d.rotation);var m=r(j)[s](b);if(m.length-1){m[1]=+m[1];m[2]=+m[2]}else m=null;S(j)&&c.rotate(0,true);for(var n in d){if(d[f](n)){if(!W[f](n))continue;var o=d[n];i[n]=o;switch(n){case"blur":c.blur(o);break;case"rotation":c.rotate(o,true);break;case"href":case"title":case"target":var t=h.parentNode;if(x.call(t.tagName)!="a"){var u=bG("a");t.insertBefore(u,h);u[l](h);t=u}n=="target"&&o=="blank"?t.setAttributeNS(c.paper.xlink,"show","new"):t.setAttributeNS(c.paper.xlink,n,o);break;case"cursor":h.style.cursor=o;break;case"clip-rect":var y=r(o)[s](b);if(y[w]==4){c.clip&&c.clip.parentNode.parentNode.removeChild(c.clip.parentNode);var z=bG("clipPath"),A=bG("rect");z.id=bh();bG(A,{x:y[0],y:y[1],width:y[2],height:y[3]});z[l](A);c.paper.defs[l](z);bG(h,{"clip-path":"url(#"+z.id+")"});c.clip=A}if(!o){var B=g.getElementById(h.getAttribute("clip-path")[Y](/(^url\(#|\)$)/g,p));B&&B.parentNode.removeChild(B);bG(h,{"clip-path":p});delete c.clip}break;case"path":c.type=="path"&&bG(h,{d:o?i.path=bq(o):"M0,0"});break;case"width":h[R](n,o);if(i.fx){n="x";o=i.x}else break;case"x":i.fx&&(o=-i.x-(i.width||0));case"rx":if(n=="rx"&&c.type=="rect")break;case"cx":m&&(n=="x"||n=="cx")&&(m[1]+=o-i[n]);h[R](n,o);c.pattern&&bJ(c);break;case"height":h[R](n,o);if(i.fy){n="y";o=i.y}else break;case"y":i.fy&&(o=-i.y-(i.height||0));case"ry":if(n=="ry"&&c.type=="rect")break;case"cy":m&&(n=="y"||n=="cy")&&(m[2]+=o-i[n]);h[R](n,o);c.pattern&&bJ(c);break;case"r":c.type=="rect"?bG(h,{rx:o,ry:o}):h[R](n,o);break;case"src":c.type=="image"&&h.setAttributeNS(c.paper.xlink,"href",o);break;case"stroke-width":h.style.strokeWidth=o;h[R](n,o);i["stroke-dasharray"]&&k(c,i["stroke-dasharray"]);break;case"stroke-dasharray":k(c,o);break;case"translation":var C=r(o)[s](b);C[0]=+C[0]||0;C[1]=+C[1]||0;if(m){m[1]+=C[0];m[2]+=C[1]}cz.call(c,C[0],C[1]);break;case"scale":C=r(o)[s](b);c.scale(+C[0]||1,+C[1]||+C[0]||1,isNaN(S(C[2]))?null:+C[2],isNaN(S(C[3]))?null:+C[3]);break;case I:var D=r(o).match(M);if(D){z=bG("pattern");var E=bG("image");z.id=bh();bG(z,{x:0,y:0,patternUnits:"userSpaceOnUse",height:1,width:1});bG(E,{x:0,y:0});E.setAttributeNS(c.paper.xlink,"href",D[1]);z[l](E);var F=g.createElement("img");F.style.cssText="position:absolute;left:-9999em;top-9999em";F.onload=function(){bG(z,{width:this.offsetWidth,height:this.offsetHeight});bG(E,{width:this.offsetWidth,height:this.offsetHeight});g.body.removeChild(this);c.paper.safari()};g.body[l](F);F.src=D[1];c.paper.defs[l](z);h.style.fill="url(#"+z.id+")";bG(h,{fill:"url(#"+z.id+")"});c.pattern=z;c.pattern&&bJ(c);break}var G=a.getRGB(o);if(G.error)if((({circle:1,ellipse:1})[f](c.type)||r(o).charAt()!="r")&&bI(h,o,c.paper)){i.gradient=o;i.fill="none";break}else{delete d.gradient;delete i.gradient;!a.is(i.opacity,"undefined")&&a.is(d.opacity,"undefined")&&bG(h,{opacity:i.opacity});!a.is(i["fill-opacity"],"undefined")&&a.is(d["fill-opacity"],"undefined")&&bG(h,{"fill-opacity":i["fill-opacity"]})}G[f]("opacity")&&bG(h,{"fill-opacity":G.opacity>1?G.opacity/100:G.opacity});case"stroke":G=a.getRGB(o);h[R](n,G.hex);n=="stroke"&&G[f]("opacity")&&bG(h,{"stroke-opacity":G.opacity>1?G.opacity/100:G.opacity});break;case"gradient":(({circle:1,ellipse:1})[f](c.type)||r(o).charAt()!="r")&&bI(h,o,c.paper);break;case"opacity":i.gradient&&!i[f]("stroke-opacity")&&bG(h,{"stroke-opacity":o>1?o/100:o});case"fill-opacity":if(i.gradient){var H=g.getElementById(h.getAttribute(I)[Y](/^url\(#|\)$/g,p));if(H){var J=H.getElementsByTagName("stop");J[J[w]-1][R]("stop-opacity",o)}break}default:n=="font-size"&&(o=T(o,10)+"px");var K=n[Y](/(\-.)/g,function(a){return V.call(a.substring(1))});h.style[K]=o;h[R](n,o);break}}}bM(c,d);m?c.rotate(m.join(q)):S(j)&&c.rotate(j,true)},bL=1.2,bM=function(b,c){if(b.type!="text"||!(c[f]("text")||c[f]("font")||c[f]("font-size")||c[f]("x")||c[f]("y")))return;var d=b.attrs,e=b.node,h=e.firstChild?T(g.defaultView.getComputedStyle(e.firstChild,p).getPropertyValue("font-size"),10):10;if(c[f]("text")){d.text=c.text;while(e.firstChild)e.removeChild(e.firstChild);var i=r(c.text)[s]("\n");for(var j=0,k=i[w];j<k;j++)if(i[j]){var m=bG("tspan");j&&bG(m,{dy:h*bL,x:d.x});m[l](g.createTextNode(i[j]));e[l](m)}}else{i=e.getElementsByTagName("tspan");for(j=0,k=i[w];j<k;j++)j&&bG(i[j],{dy:h*bL,x:d.x})}bG(e,{y:d.y});var n=b.getBBox(),o=d.y-(n.y+n.height/2);o&&a.is(o,"finite")&&bG(e,{y:d.y+o})},bN=function(b,c){var d=0,e=0;this[0]=b;this.id=a._oid++;this.node=b;b.raphael=this;this.paper=c;this.attrs=this.attrs||{};this.transformations=[];this._={tx:0,ty:0,rt:{deg:0,cx:0,cy:0},sx:1,sy:1};!c.bottom&&(c.bottom=this);this.prev=c.top;c.top&&(c.top.next=this);c.top=this;this.next=null},bO=bN[e];bN[e].rotate=function(c,d,e){if(this.removed)return this;if(c==null){if(this._.rt.cx)return[this._.rt.deg,this._.rt.cx,this._.rt.cy][v](q);return this._.rt.deg}var f=this.getBBox();c=r(c)[s](b);if(c[w]-1){d=S(c[1]);e=S(c[2])}c=S(c[0]);d!=null&&d!==false?this._.rt.deg=c:this._.rt.deg+=c;e==null&&(d=null);this._.rt.cx=d;this._.rt.cy=e;d=d==null?f.x+f.width/2:d;e=e==null?f.y+f.height/2:e;if(this._.rt.deg){this.transformations[0]=a.format("rotate({0} {1} {2})",this._.rt.deg,d,e);this.clip&&bG(this.clip,{transform:a.format("rotate({0} {1} {2})",-this._.rt.deg,d,e)})}else{this.transformations[0]=p;this.clip&&bG(this.clip,{transform:p})}bG(this.node,{transform:this.transformations[v](q)});return this};bN[e].hide=function(){!this.removed&&(this.node.style.display="none");return this};bN[e].show=function(){!this.removed&&(this.node.style.display="");return this};bN[e].remove=function(){if(this.removed)return;bA(this,this.paper);this.node.parentNode.removeChild(this.node);for(var a in this)delete this[a];this.removed=true};bN[e].getBBox=function(){if(this.removed)return this;if(this.type=="path")return bn(this.attrs.path);if(this.node.style.display=="none"){this.show();var a=true}var b={};try{b=this.node.getBBox()}catch(a){}finally{b=b||{}}if(this.type=="text"){b={x:b.x,y:Infinity,width:0,height:0};for(var c=0,d=this.node.getNumberOfChars();c<d;c++){var e=this.node.getExtentOfChar(c);e.y<b.y&&(b.y=e.y);e.y+e.height-b.y>b.height&&(b.height=e.y+e.height-b.y);e.x+e.width-b.x>b.width&&(b.width=e.x+e.width-b.x)}}a&&this.hide();return b};bN[e].attr=function(b,c){if(this.removed)return this;if(b==null){var d={};for(var e in this.attrs)this.attrs[f](e)&&(d[e]=this.attrs[e]);this._.rt.deg&&(d.rotation=this.rotate());(this._.sx!=1||this._.sy!=1)&&(d.scale=this.scale());d.gradient&&d.fill=="none"&&(d.fill=d.gradient)&&delete d.gradient;return d}if(c==null&&a.is(b,F)){if(b=="translation")return cz.call(this);if(b=="rotation")return this.rotate();if(b=="scale")return this.scale();if(b==I&&this.attrs.fill=="none"&&this.attrs.gradient)return this.attrs.gradient;return this.attrs[b]}if(c==null&&a.is(b,G)){var g={};for(var h=0,i=b.length;h<i;h++)g[b[h]]=this.attr(b[h]);return g}if(c!=null){var j={};j[b]=c}else b!=null&&a.is(b,"object")&&(j=b);for(var k in this.paper.customAttributes)if(this.paper.customAttributes[f](k)&&j[f](k)&&a.is(this.paper.customAttributes[k],"function")){var l=this.paper.customAttributes[k].apply(this,[][n](j[k]));this.attrs[k]=j[k];for(var m in l)l[f](m)&&(j[m]=l[m])}bK(this,j);return this};bN[e].toFront=function(){if(this.removed)return this;this.node.parentNode[l](this.node);var a=this.paper;a.top!=this&&bB(this,a);return this};bN[e].toBack=function(){if(this.removed)return this;if(this.node.parentNode.firstChild!=this.node){this.node.parentNode.insertBefore(this.node,this.node.parentNode.firstChild);bC(this,this.paper);var a=this.paper}return this};bN[e].insertAfter=function(a){if(this.removed)return this;var b=a.node||a[a.length-1].node;b.nextSibling?b.parentNode.insertBefore(this.node,b.nextSibling):b.parentNode[l](this.node);bD(this,a,this.paper);return this};bN[e].insertBefore=function(a){if(this.removed)return this;var b=a.node||a[0].node;b.parentNode.insertBefore(this.node,b);bE(this,a,this.paper);return this};bN[e].blur=function(a){var b=this;if(+a!==0){var c=bG("filter"),d=bG("feGaussianBlur");b.attrs.blur=a;c.id=bh();bG(d,{stdDeviation:+a||1.5});c.appendChild(d);b.paper.defs.appendChild(c);b._blur=c;bG(b.node,{filter:"url(#"+c.id+")"})}else{if(b._blur){b._blur.parentNode.removeChild(b._blur);delete b._blur;delete b.attrs.blur}b.node.removeAttribute("filter")}};var bP=function(a,b,c,d){var e=bG("circle");a.canvas&&a.canvas[l](e);var f=new bN(e,a);f.attrs={cx:b,cy:c,r:d,fill:"none",stroke:"#000"};f.type="circle";bG(e,f.attrs);return f},bQ=function(a,b,c,d,e,f){var g=bG("rect");a.canvas&&a.canvas[l](g);var h=new bN(g,a);h.attrs={x:b,y:c,width:d,height:e,r:f||0,rx:f||0,ry:f||0,fill:"none",stroke:"#000"};h.type="rect";bG(g,h.attrs);return h},bR=function(a,b,c,d,e){var f=bG("ellipse");a.canvas&&a.canvas[l](f);var g=new bN(f,a);g.attrs={cx:b,cy:c,rx:d,ry:e,fill:"none",stroke:"#000"};g.type="ellipse";bG(f,g.attrs);return g},bS=function(a,b,c,d,e,f){var g=bG("image");bG(g,{x:c,y:d,width:e,height:f,preserveAspectRatio:"none"});g.setAttributeNS(a.xlink,"href",b);a.canvas&&a.canvas[l](g);var h=new bN(g,a);h.attrs={x:c,y:d,width:e,height:f,src:b};h.type="image";return h},bT=function(a,b,c,d){var e=bG("text");bG(e,{x:b,y:c,"text-anchor":"middle"});a.canvas&&a.canvas[l](e);var f=new bN(e,a);f.attrs={x:b,y:c,"text-anchor":"middle",text:d,font:W.font,stroke:"none",fill:"#000"};f.type="text";bK(f,f.attrs);return f},bU=function(a,b){this.width=a||this.width;this.height=b||this.height;this.canvas[R]("width",this.width);this.canvas[R]("height",this.height);return this},bV=function(){var b=by[m](0,arguments),c=b&&b.container,d=b.x,e=b.y,f=b.width,h=b.height;if(!c)throw new Error("SVG container not found.");var i=bG("svg");d=d||0;e=e||0;f=f||512;h=h||342;bG(i,{xmlns:"http://www.w3.org/2000/svg",version:1.1,width:f,height:h});if(c==1){i.style.cssText="position:absolute;left:"+d+"px;top:"+e+"px";g.body[l](i)}else c.firstChild?c.insertBefore(i,c.firstChild):c[l](i);c=new j;c.width=f;c.height=h;c.canvas=i;bz.call(c,c,a.fn);c.clear();return c};k.clear=function(){var a=this.canvas;while(a.firstChild)a.removeChild(a.firstChild);this.bottom=this.top=null;(this.desc=bG("desc"))[l](g.createTextNode("Created with Raphaël"));a[l](this.desc);a[l](this.defs=bG("defs"))};k.remove=function(){this.canvas.parentNode&&this.canvas.parentNode.removeChild(this.canvas);for(var a in this)this[a]=bF(a)}}if(a.vml){var bW={M:"m",L:"l",C:"c",Z:"x",m:"t",l:"r",c:"v",z:"x"},bX=/([clmz]),?([^clmz]*)/gi,bY=/ progid:\S+Blur\([^\)]+\)/g,bZ=/-?[^,\s-]+/g,b$=1000+q+1000,b_=10,ca={path:1,rect:1},cb=function(a){var b=/[ahqstv]/ig,c=bq;r(a).match(b)&&(c=bw);b=/[clmz]/g;if(c==bq&&!r(a).match(b)){var d=r(a)[Y](bX,function(a,b,c){var d=[],e=x.call(b)=="m",f=bW[b];c[Y](bZ,function(a){if(e&&d[w]==2){f+=d+bW[b=="m"?"l":"L"];d=[]}d[L](Q(a*b_))});return f+d});return d}var e=c(a),f,g;d=[];for(var h=0,i=e[w];h<i;h++){f=e[h];g=x.call(e[h][0]);g=="z"&&(g="x");for(var j=1,k=f[w];j<k;j++)g+=Q(f[j]*b_)+(j!=k-1?",":p);d[L](g)}return d[v](q)};a[H]=function(){return"Your browser doesn’t support SVG. Falling down to VML.\nYou are running Raphaël "+this.version};bH=function(a,b){var c=cd("group");c.style.cssText="position:absolute;left:0;top:0;width:"+b.width+"px;height:"+b.height+"px";c.coordsize=b.coordsize;c.coordorigin=b.coordorigin;var d=cd("shape"),e=d.style;e.width=b.width+"px";e.height=b.height+"px";d.coordsize=b$;d.coordorigin=b.coordorigin;c[l](d);var f=new bN(d,c,b),g={fill:"none",stroke:"#000"};a&&(g.path=a);f.type="path";f.path=[];f.Path=p;bK(f,g);b.canvas[l](c);return f};bK=function(c,d){c.attrs=c.attrs||{};var e=c.node,h=c.attrs,i=e.style,j,k=(d.x!=h.x||d.y!=h.y||d.width!=h.width||d.height!=h.height||d.r!=h.r)&&c.type=="rect",m=c;for(var n in d)d[f](n)&&(h[n]=d[n]);if(k){h.path=cc(h.x,h.y,h.width,h.height,h.r);c.X=h.x;c.Y=h.y;c.W=h.width;c.H=h.height}d.href&&(e.href=d.href);d.title&&(e.title=d.title);d.target&&(e.target=d.target);d.cursor&&(i.cursor=d.cursor);"blur"in d&&c.blur(d.blur);if(d.path&&c.type=="path"||k)e.path=cb(h.path);d.rotation!=null&&c.rotate(d.rotation,true);if(d.translation){j=r(d.translation)[s](b);cz.call(c,j[0],j[1]);if(c._.rt.cx!=null){c._.rt.cx+=+j[0];c._.rt.cy+=+j[1];c.setBox(c.attrs,j[0],j[1])}}if(d.scale){j=r(d.scale)[s](b);c.scale(+j[0]||1,+j[1]||+j[0]||1,+j[2]||null,+j[3]||null)}if("clip-rect"in d){var o=r(d["clip-rect"])[s](b);if(o[w]==4){o[2]=+o[2]+ +o[0];o[3]=+o[3]+ +o[1];var q=e.clipRect||g.createElement("div"),t=q.style,u=e.parentNode;t.clip=a.format("rect({1}px {2}px {3}px {0}px)",o);if(!e.clipRect){t.position="absolute";t.top=0;t.left=0;t.width=c.paper.width+"px";t.height=c.paper.height+"px";u.parentNode.insertBefore(q,u);q[l](u);e.clipRect=q}}d["clip-rect"]||e.clipRect&&(e.clipRect.style.clip=p)}c.type=="image"&&d.src&&(e.src=d.src);if(c.type=="image"&&d.opacity){e.filterOpacity=U+".Alpha(opacity="+d.opacity*100+")";i.filter=(e.filterMatrix||p)+(e.filterOpacity||p)}d.font&&(i.font=d.font);d["font-family"]&&(i.fontFamily="\""+d["font-family"][s](",")[0][Y](/^['"]+|['"]+$/g,p)+"\"");d["font-size"]&&(i.fontSize=d["font-size"]);d["font-weight"]&&(i.fontWeight=d["font-weight"]);d["font-style"]&&(i.fontStyle=d["font-style"]);if(d.opacity!=null||d["stroke-width"]!=null||d.fill!=null||d.stroke!=null||d["stroke-width"]!=null||d["stroke-opacity"]!=null||d["fill-opacity"]!=null||d["stroke-dasharray"]!=null||d["stroke-miterlimit"]!=null||d["stroke-linejoin"]!=null||d["stroke-linecap"]!=null){e=c.shape||e;var v=e.getElementsByTagName(I)&&e.getElementsByTagName(I)[0],x=false;!v&&(x=v=cd(I));if("fill-opacity"in d||"opacity"in d){var y=((+h["fill-opacity"]+1||2)-1)*((+h.opacity+1||2)-1)*((+a.getRGB(d.fill).o+1||2)-1);y=A(z(y,0),1);v.opacity=y}d.fill&&(v.on=true);if(v.on==null||d.fill=="none")v.on=false;if(v.on&&d.fill){var B=d.fill.match(M);if(B){v.src=B[1];v.type="tile"}else{v.color=a.getRGB(d.fill).hex;v.src=p;v.type="solid";if(a.getRGB(d.fill).error&&(m.type in{circle:1,ellipse:1}||r(d.fill).charAt()!="r")&&bI(m,d.fill)){h.fill="none";h.gradient=d.fill}}}x&&e[l](v);var C=e.getElementsByTagName("stroke")&&e.getElementsByTagName("stroke")[0],D=false;!C&&(D=C=cd("stroke"));if(d.stroke&&d.stroke!="none"||d["stroke-width"]||d["stroke-opacity"]!=null||d["stroke-dasharray"]||d["stroke-miterlimit"]||d["stroke-linejoin"]||d["stroke-linecap"])C.on=true;(d.stroke=="none"||C.on==null||d.stroke==0||d["stroke-width"]==0)&&(C.on=false);var E=a.getRGB(d.stroke);C.on&&d.stroke&&(C.color=E.hex);y=((+h["stroke-opacity"]+1||2)-1)*((+h.opacity+1||2)-1)*((+E.o+1||2)-1);var F=(S(d["stroke-width"])||1)*0.75;y=A(z(y,0),1);d["stroke-width"]==null&&(F=h["stroke-width"]);d["stroke-width"]&&(C.weight=F);F&&F<1&&(y*=F)&&(C.weight=1);C.opacity=y;d["stroke-linejoin"]&&(C.joinstyle=d["stroke-linejoin"]||"miter");C.miterlimit=d["stroke-miterlimit"]||8;d["stroke-linecap"]&&(C.endcap=d["stroke-linecap"]=="butt"?"flat":d["stroke-linecap"]=="square"?"square":"round");if(d["stroke-dasharray"]){var G={"-":"shortdash",".":"shortdot","-.":"shortdashdot","-..":"shortdashdotdot",". ":"dot","- ":"dash","--":"longdash","- .":"dashdot","--.":"longdashdot","--..":"longdashdotdot"};C.dashstyle=G[f](d["stroke-dasharray"])?G[d["stroke-dasharray"]]:p}D&&e[l](C)}if(m.type=="text"){i=m.paper.span.style;h.font&&(i.font=h.font);h["font-family"]&&(i.fontFamily=h["font-family"]);h["font-size"]&&(i.fontSize=h["font-size"]);h["font-weight"]&&(i.fontWeight=h["font-weight"]);h["font-style"]&&(i.fontStyle=h["font-style"]);m.node.string&&(m.paper.span.innerHTML=r(m.node.string)[Y](/</g,"&#60;")[Y](/&/g,"&#38;")[Y](/\n/g,"<br>"));m.W=h.w=m.paper.span.offsetWidth;m.H=h.h=m.paper.span.offsetHeight;m.X=h.x;m.Y=h.y+Q(m.H/2);switch(h["text-anchor"]){case"start":m.node.style["v-text-align"]="left";m.bbx=Q(m.W/2);break;case"end":m.node.style["v-text-align"]="right";m.bbx=-Q(m.W/2);break;default:m.node.style["v-text-align"]="center";break}}};bI=function(a,b){a.attrs=a.attrs||{};var c=a.attrs,d,e="linear",f=".5 .5";a.attrs.gradient=b;b=r(b)[Y](bd,function(a,b,c){e="radial";if(b&&c){b=S(b);c=S(c);C(b-0.5,2)+C(c-0.5,2)>0.25&&(c=y.sqrt(0.25-C(b-0.5,2))*((c>0.5)*2-1)+0.5);f=b+q+c}return p});b=b[s](/\s*\-\s*/);if(e=="linear"){var g=b.shift();g=-S(g);if(isNaN(g))return null}var h=bx(b);if(!h)return null;a=a.shape||a.node;d=a.getElementsByTagName(I)[0]||cd(I);!d.parentNode&&a.appendChild(d);if(h[w]){d.on=true;d.method="none";d.color=h[0].color;d.color2=h[h[w]-1].color;var i=[];for(var j=0,k=h[w];j<k;j++)h[j].offset&&i[L](h[j].offset+q+h[j].color);d.colors&&(d.colors.value=i[w]?i[v]():"0% "+d.color);if(e=="radial"){d.type="gradientradial";d.focus="100%";d.focussize=f;d.focusposition=f}else{d.type="gradient";d.angle=(270-g)%360}}return 1};bN=function(b,c,d){var e=0,f=0,g=0,h=1;this[0]=b;this.id=a._oid++;this.node=b;b.raphael=this;this.X=0;this.Y=0;this.attrs={};this.Group=c;this.paper=d;this._={tx:0,ty:0,rt:{deg:0},sx:1,sy:1};!d.bottom&&(d.bottom=this);this.prev=d.top;d.top&&(d.top.next=this);d.top=this;this.next=null};bO=bN[e];bO.rotate=function(a,c,d){if(this.removed)return this;if(a==null){if(this._.rt.cx)return[this._.rt.deg,this._.rt.cx,this._.rt.cy][v](q);return this._.rt.deg}a=r(a)[s](b);if(a[w]-1){c=S(a[1]);d=S(a[2])}a=S(a[0]);c!=null?this._.rt.deg=a:this._.rt.deg+=a;d==null&&(c=null);this._.rt.cx=c;this._.rt.cy=d;this.setBox(this.attrs,c,d);this.Group.style.rotation=this._.rt.deg;return this};bO.setBox=function(a,b,c){if(this.removed)return this;var d=this.Group.style,e=this.shape&&this.shape.style||this.node.style;a=a||{};for(var g in a)a[f](g)&&(this.attrs[g]=a[g]);b=b||this._.rt.cx;c=c||this._.rt.cy;var h=this.attrs,i,j,k,l;switch(this.type){case"circle":i=h.cx-h.r;j=h.cy-h.r;k=l=h.r*2;break;case"ellipse":i=h.cx-h.rx;j=h.cy-h.ry;k=h.rx*2;l=h.ry*2;break;case"image":i=+h.x;j=+h.y;k=h.width||0;l=h.height||0;break;case"text":this.textpath.v=["m",Q(h.x),", ",Q(h.y-2),"l",Q(h.x)+1,", ",Q(h.y-2)][v](p);i=h.x-Q(this.W/2);j=h.y-this.H/2;k=this.W;l=this.H;break;case"rect":case"path":if(this.attrs.path){var m=bn(this.attrs.path);i=m.x;j=m.y;k=m.width;l=m.height}else{i=0;j=0;k=this.paper.width;l=this.paper.height}break;default:i=0;j=0;k=this.paper.width;l=this.paper.height;break}b=b==null?i+k/2:b;c=c==null?j+l/2:c;var n=b-this.paper.width/2,o=c-this.paper.height/2,q;d.left!=(q=n+"px")&&(d.left=q);d.top!=(q=o+"px")&&(d.top=q);this.X=ca[f](this.type)?-n:i;this.Y=ca[f](this.type)?-o:j;this.W=k;this.H=l;if(ca[f](this.type)){e.left!=(q=-n*b_+"px")&&(e.left=q);e.top!=(q=-o*b_+"px")&&(e.top=q)}else if(this.type=="text"){e.left!=(q=-n+"px")&&(e.left=q);e.top!=(q=-o+"px")&&(e.top=q)}else{d.width!=(q=this.paper.width+"px")&&(d.width=q);d.height!=(q=this.paper.height+"px")&&(d.height=q);e.left!=(q=i-n+"px")&&(e.left=q);e.top!=(q=j-o+"px")&&(e.top=q);e.width!=(q=k+"px")&&(e.width=q);e.height!=(q=l+"px")&&(e.height=q)}};bO.hide=function(){!this.removed&&(this.Group.style.display="none");return this};bO.show=function(){!this.removed&&(this.Group.style.display="block");return this};bO.getBBox=function(){if(this.removed)return this;if(ca[f](this.type))return bn(this.attrs.path);return{x:this.X+(this.bbx||0),y:this.Y,width:this.W,height:this.H}};bO.remove=function(){if(this.removed)return;bA(this,this.paper);this.node.parentNode.removeChild(this.node);this.Group.parentNode.removeChild(this.Group);this.shape&&this.shape.parentNode.removeChild(this.shape);for(var a in this)delete this[a];this.removed=true};bO.attr=function(b,c){if(this.removed)return this;if(b==null){var d={};for(var e in this.attrs)this.attrs[f](e)&&(d[e]=this.attrs[e]);this._.rt.deg&&(d.rotation=this.rotate());(this._.sx!=1||this._.sy!=1)&&(d.scale=this.scale());d.gradient&&d.fill=="none"&&(d.fill=d.gradient)&&delete d.gradient;return d}if(c==null&&a.is(b,"string")){if(b=="translation")return cz.call(this);if(b=="rotation")return this.rotate();if(b=="scale")return this.scale();if(b==I&&this.attrs.fill=="none"&&this.attrs.gradient)return this.attrs.gradient;return this.attrs[b]}if(this.attrs&&c==null&&a.is(b,G)){var g,h={};for(e=0,g=b[w];e<g;e++)h[b[e]]=this.attr(b[e]);return h}var i;if(c!=null){i={};i[b]=c}c==null&&a.is(b,"object")&&(i=b);if(i){for(var j in this.paper.customAttributes)if(this.paper.customAttributes[f](j)&&i[f](j)&&a.is(this.paper.customAttributes[j],"function")){var k=this.paper.customAttributes[j].apply(this,[][n](i[j]));this.attrs[j]=i[j];for(var l in k)k[f](l)&&(i[l]=k[l])}i.text&&this.type=="text"&&(this.node.string=i.text);bK(this,i);i.gradient&&(({circle:1,ellipse:1})[f](this.type)||r(i.gradient).charAt()!="r")&&bI(this,i.gradient);(!ca[f](this.type)||this._.rt.deg)&&this.setBox(this.attrs)}return this};bO.toFront=function(){!this.removed&&this.Group.parentNode[l](this.Group);this.paper.top!=this&&bB(this,this.paper);return this};bO.toBack=function(){if(this.removed)return this;if(this.Group.parentNode.firstChild!=this.Group){this.Group.parentNode.insertBefore(this.Group,this.Group.parentNode.firstChild);bC(this,this.paper)}return this};bO.insertAfter=function(a){if(this.removed)return this;a.constructor==cC&&(a=a[a.length-1]);a.Group.nextSibling?a.Group.parentNode.insertBefore(this.Group,a.Group.nextSibling):a.Group.parentNode[l](this.Group);bD(this,a,this.paper);return this};bO.insertBefore=function(a){if(this.removed)return this;a.constructor==cC&&(a=a[0]);a.Group.parentNode.insertBefore(this.Group,a.Group);bE(this,a,this.paper);return this};bO.blur=function(b){var c=this.node.runtimeStyle,d=c.filter;d=d.replace(bY,p);if(+b!==0){this.attrs.blur=b;c.filter=d+q+U+".Blur(pixelradius="+(+b||1.5)+")";c.margin=a.format("-{0}px 0 0 -{0}px",Q(+b||1.5))}else{c.filter=d;c.margin=0;delete this.attrs.blur}};bP=function(a,b,c,d){var e=cd("group"),f=cd("oval"),g=f.style;e.style.cssText="position:absolute;left:0;top:0;width:"+a.width+"px;height:"+a.height+"px";e.coordsize=b$;e.coordorigin=a.coordorigin;e[l](f);var h=new bN(f,e,a);h.type="circle";bK(h,{stroke:"#000",fill:"none"});h.attrs.cx=b;h.attrs.cy=c;h.attrs.r=d;h.setBox({x:b-d,y:c-d,width:d*2,height:d*2});a.canvas[l](e);return h};function cc(b,c,d,e,f){return f?a.format("M{0},{1}l{2},0a{3},{3},0,0,1,{3},{3}l0,{5}a{3},{3},0,0,1,{4},{3}l{6},0a{3},{3},0,0,1,{4},{4}l0,{7}a{3},{3},0,0,1,{3},{4}z",b+f,c,d-f*2,f,-f,e-f*2,f*2-d,f*2-e):a.format("M{0},{1}l{2},0,0,{3},{4},0z",b,c,d,e,-d)}bQ=function(a,b,c,d,e,f){var g=cc(b,c,d,e,f),h=a.path(g),i=h.attrs;h.X=i.x=b;h.Y=i.y=c;h.W=i.width=d;h.H=i.height=e;i.r=f;i.path=g;h.type="rect";return h};bR=function(a,b,c,d,e){var f=cd("group"),g=cd("oval"),h=g.style;f.style.cssText="position:absolute;left:0;top:0;width:"+a.width+"px;height:"+a.height+"px";f.coordsize=b$;f.coordorigin=a.coordorigin;f[l](g);var i=new bN(g,f,a);i.type="ellipse";bK(i,{stroke:"#000"});i.attrs.cx=b;i.attrs.cy=c;i.attrs.rx=d;i.attrs.ry=e;i.setBox({x:b-d,y:c-e,width:d*2,height:e*2});a.canvas[l](f);return i};bS=function(a,b,c,d,e,f){var g=cd("group"),h=cd("image");g.style.cssText="position:absolute;left:0;top:0;width:"+a.width+"px;height:"+a.height+"px";g.coordsize=b$;g.coordorigin=a.coordorigin;h.src=b;g[l](h);var i=new bN(h,g,a);i.type="image";i.attrs.src=b;i.attrs.x=c;i.attrs.y=d;i.attrs.w=e;i.attrs.h=f;i.setBox({x:c,y:d,width:e,height:f});a.canvas[l](g);return i};bT=function(b,c,d,e){var f=cd("group"),g=cd("shape"),h=g.style,i=cd("path"),j=i.style,k=cd("textpath");f.style.cssText="position:absolute;left:0;top:0;width:"+b.width+"px;height:"+b.height+"px";f.coordsize=b$;f.coordorigin=b.coordorigin;i.v=a.format("m{0},{1}l{2},{1}",Q(c*10),Q(d*10),Q(c*10)+1);i.textpathok=true;h.width=b.width;h.height=b.height;k.string=r(e);k.on=true;g[l](k);g[l](i);f[l](g);var m=new bN(k,f,b);m.shape=g;m.textpath=i;m.type="text";m.attrs.text=e;m.attrs.x=c;m.attrs.y=d;m.attrs.w=1;m.attrs.h=1;bK(m,{font:W.font,stroke:"none",fill:"#000"});m.setBox();b.canvas[l](f);return m};bU=function(a,b){var c=this.canvas.style;a==+a&&(a+="px");b==+b&&(b+="px");c.width=a;c.height=b;c.clip="rect(0 "+a+" "+b+" 0)";return this};var cd;g.createStyleSheet().addRule(".rvml","behavior:url(#default#VML)");try{!g.namespaces.rvml&&g.namespaces.add("rvml","urn:schemas-microsoft-com:vml");cd=function(a){return g.createElement("<rvml:"+a+" class=\"rvml\">")}}catch(a){cd=function(a){return g.createElement("<"+a+" xmlns=\"urn:schemas-microsoft.com:vml\" class=\"rvml\">")}}bV=function(){var b=by[m](0,arguments),c=b.container,d=b.height,e,f=b.width,h=b.x,i=b.y;if(!c)throw new Error("VML container not found.");var k=new j,n=k.canvas=g.createElement("div"),o=n.style;h=h||0;i=i||0;f=f||512;d=d||342;f==+f&&(f+="px");d==+d&&(d+="px");k.width=1000;k.height=1000;k.coordsize=b_*1000+q+b_*1000;k.coordorigin="0 0";k.span=g.createElement("span");k.span.style.cssText="position:absolute;left:-9999em;top:-9999em;padding:0;margin:0;line-height:1;display:inline;";n[l](k.span);o.cssText=a.format("top:0;left:0;width:{0};height:{1};display:inline-block;position:relative;clip:rect(0 {0} {1} 0);overflow:hidden",f,d);if(c==1){g.body[l](n);o.left=h+"px";o.top=i+"px";o.position="absolute"}else c.firstChild?c.insertBefore(n,c.firstChild):c[l](n);bz.call(k,k,a.fn);return k};k.clear=function(){this.canvas.innerHTML=p;this.span=g.createElement("span");this.span.style.cssText="position:absolute;left:-9999em;top:-9999em;padding:0;margin:0;line-height:1;display:inline;";this.canvas[l](this.span);this.bottom=this.top=null};k.remove=function(){this.canvas.parentNode.removeChild(this.canvas);for(var a in this)this[a]=bF(a);return true}}var ce=navigator.userAgent.match(/Version\\x2f(.*?)\s/);navigator.vendor=="Apple Computer, Inc."&&(ce&&ce[1]<4||navigator.platform.slice(0,2)=="iP")?k.safari=function(){var a=this.rect(-99,-99,this.width+99,this.height+99).attr({stroke:"none"});h.setTimeout(function(){a.remove()})}:k.safari=function(){};var cf=function(){this.returnValue=false},cg=function(){return this.originalEvent.preventDefault()},ch=function(){this.cancelBubble=true},ci=function(){return this.originalEvent.stopPropagation()},cj=(function(){{if(g.addEventListener)return function(a,b,c,d){var e=o&&u[b]?u[b]:b,g=function(e){if(o&&u[f](b))for(var g=0,h=e.targetTouches&&e.targetTouches.length;g<h;g++){if(e.targetTouches[g].target==a){var i=e;e=e.targetTouches[g];e.originalEvent=i;e.preventDefault=cg;e.stopPropagation=ci;break}}return c.call(d,e)};a.addEventListener(e,g,false);return function(){a.removeEventListener(e,g,false);return true}};if(g.attachEvent)return function(a,b,c,d){var e=function(a){a=a||h.event;a.preventDefault=a.preventDefault||cf;a.stopPropagation=a.stopPropagation||ch;return c.call(d,a)};a.attachEvent("on"+b,e);var f=function(){a.detachEvent("on"+b,e);return true};return f}}})(),ck=[],cl=function(a){var b=a.clientX,c=a.clientY,d=g.documentElement.scrollTop||g.body.scrollTop,e=g.documentElement.scrollLeft||g.body.scrollLeft,f,h=ck.length;while(h--){f=ck[h];if(o){var i=a.touches.length,j;while(i--){j=a.touches[i];if(j.identifier==f.el._drag.id){b=j.clientX;c=j.clientY;(a.originalEvent?a.originalEvent:a).preventDefault();break}}}else a.preventDefault();b+=e;c+=d;f.move&&f.move.call(f.move_scope||f.el,b-f.el._drag.x,c-f.el._drag.y,b,c,a)}},cm=function(b){a.unmousemove(cl).unmouseup(cm);var c=ck.length,d;while(c--){d=ck[c];d.el._drag={};d.end&&d.end.call(d.end_scope||d.start_scope||d.move_scope||d.el,b)}ck=[]};for(var cn=t[w];cn--;)(function(b){a[b]=bN[e][b]=function(c,d){if(a.is(c,"function")){this.events=this.events||[];this.events.push({name:b,f:c,unbind:cj(this.shape||this.node||g,b,c,d||this)})}return this};a["un"+b]=bN[e]["un"+b]=function(a){var c=this.events,d=c[w];while(d--)if(c[d].name==b&&c[d].f==a){c[d].unbind();c.splice(d,1);!c.length&&delete this.events;return this}return this}})(t[cn]);bO.hover=function(a,b,c,d){return this.mouseover(a,c).mouseout(b,d||c)};bO.unhover=function(a,b){return this.unmouseover(a).unmouseout(b)};bO.drag=function(b,c,d,e,f,h){this._drag={};this.mousedown(function(i){(i.originalEvent||i).preventDefault();var j=g.documentElement.scrollTop||g.body.scrollTop,k=g.documentElement.scrollLeft||g.body.scrollLeft;this._drag.x=i.clientX+k;this._drag.y=i.clientY+j;this._drag.id=i.identifier;c&&c.call(f||e||this,i.clientX+k,i.clientY+j,i);!ck.length&&a.mousemove(cl).mouseup(cm);ck.push({el:this,move:b,end:d,move_scope:e,start_scope:f,end_scope:h})});return this};bO.undrag=function(b,c,d){var e=ck.length;while(e--)ck[e].el==this&&(ck[e].move==b&&ck[e].end==d)&&ck.splice(e++,1);!ck.length&&a.unmousemove(cl).unmouseup(cm)};k.circle=function(a,b,c){return bP(this,a||0,b||0,c||0)};k.rect=function(a,b,c,d,e){return bQ(this,a||0,b||0,c||0,d||0,e||0)};k.ellipse=function(a,b,c,d){return bR(this,a||0,b||0,c||0,d||0)};k.path=function(b){b&&!a.is(b,F)&&!a.is(b[0],G)&&(b+=p);return bH(a.format[m](a,arguments),this)};k.image=function(a,b,c,d,e){return bS(this,a||"about:blank",b||0,c||0,d||0,e||0)};k.text=function(a,b,c){return bT(this,a||0,b||0,r(c))};k.set=function(a){arguments[w]>1&&(a=Array[e].splice.call(arguments,0,arguments[w]));return new cC(a)};k.setSize=bU;k.top=k.bottom=null;k.raphael=a;function co(){return this.x+q+this.y}bO.resetScale=function(){if(this.removed)return this;this._.sx=1;this._.sy=1;this.attrs.scale="1 1"};bO.scale=function(a,b,c,d){if(this.removed)return this;if(a==null&&b==null)return{x:this._.sx,y:this._.sy,toString:co};b=b||a;!(+b)&&(b=a);var e,f,g,h,i=this.attrs;if(a!=0){var j=this.getBBox(),k=j.x+j.width/2,l=j.y+j.height/2,m=B(a/this._.sx),o=B(b/this._.sy);c=+c||c==0?c:k;d=+d||d==0?d:l;var r=this._.sx>0,s=this._.sy>0,t=~(~(a/B(a))),u=~(~(b/B(b))),x=m*t,y=o*u,z=this.node.style,A=c+B(k-c)*x*(k>c==r?1:-1),C=d+B(l-d)*y*(l>d==s?1:-1),D=a*t>b*u?o:m;switch(this.type){case"rect":case"image":var E=i.width*m,F=i.height*o;this.attr({height:F,r:i.r*D,width:E,x:A-E/2,y:C-F/2});break;case"circle":case"ellipse":this.attr({rx:i.rx*m,ry:i.ry*o,r:i.r*D,cx:A,cy:C});break;case"text":this.attr({x:A,y:C});break;case"path":var G=bp(i.path),H=true,I=r?x:m,J=s?y:o;for(var K=0,L=G[w];K<L;K++){var M=G[K],N=V.call(M[0]);{if(N=="M"&&H)continue;H=false}if(N=="A"){M[G[K][w]-2]*=I;M[G[K][w]-1]*=J;M[1]*=m;M[2]*=o;M[5]=+(t+u?!(!(+M[5])):!(+M[5]))}else if(N=="H")for(var O=1,P=M[w];O<P;O++)M[O]*=I;else if(N=="V")for(O=1,P=M[w];O<P;O++)M[O]*=J;else for(O=1,P=M[w];O<P;O++)M[O]*=O%2?I:J}var Q=bn(G);e=A-Q.x-Q.width/2;f=C-Q.y-Q.height/2;G[0][1]+=e;G[0][2]+=f;this.attr({path:G});break}if(this.type in{text:1,image:1}&&(t!=1||u!=1))if(this.transformations){this.transformations[2]="scale("[n](t,",",u,")");this.node[R]("transform",this.transformations[v](q));e=t==-1?-i.x-(E||0):i.x;f=u==-1?-i.y-(F||0):i.y;this.attr({x:e,y:f});i.fx=t-1;i.fy=u-1}else{this.node.filterMatrix=U+".Matrix(M11="[n](t,", M12=0, M21=0, M22=",u,", Dx=0, Dy=0, sizingmethod='auto expand', filtertype='bilinear')");z.filter=(this.node.filterMatrix||p)+(this.node.filterOpacity||p)}else if(this.transformations){this.transformations[2]=p;this.node[R]("transform",this.transformations[v](q));i.fx=0;i.fy=0}else{this.node.filterMatrix=p;z.filter=(this.node.filterMatrix||p)+(this.node.filterOpacity||p)}i.scale=[a,b,c,d][v](q);this._.sx=a;this._.sy=b}return this};bO.clone=function(){if(this.removed)return null;var a=this.attr();delete a.scale;delete a.translation;return this.paper[this.type]().attr(a)};var cp={},cq=function(b,c,d,e,f,g,h,i,j){var k=0,l=100,m=[b,c,d,e,f,g,h,i].join(),n=cp[m],o,p;!n&&(cp[m]=n={data:[]});n.timer&&clearTimeout(n.timer);n.timer=setTimeout(function(){delete cp[m]},2000);if(j!=null){var q=cq(b,c,d,e,f,g,h,i);l=~(~q)*10}for(var r=0;r<l+1;r++){if(n.data[j]>r)p=n.data[r*l];else{p=a.findDotsAtSegment(b,c,d,e,f,g,h,i,r/l);n.data[r]=p}r&&(k+=C(C(o.x-p.x,2)+C(o.y-p.y,2),0.5));if(j!=null&&k>=j)return p;o=p}if(j==null)return k},cr=function(b,c){return function(d,e,f){d=bw(d);var g,h,i,j,k="",l={},m,n=0;for(var o=0,p=d.length;o<p;o++){i=d[o];if(i[0]=="M"){g=+i[1];h=+i[2]}else{j=cq(g,h,i[1],i[2],i[3],i[4],i[5],i[6]);if(n+j>e){if(c&&!l.start){m=cq(g,h,i[1],i[2],i[3],i[4],i[5],i[6],e-n);k+=["C",m.start.x,m.start.y,m.m.x,m.m.y,m.x,m.y];if(f)return k;l.start=k;k=["M",m.x,m.y+"C",m.n.x,m.n.y,m.end.x,m.end.y,i[5],i[6]][v]();n+=j;g=+i[5];h=+i[6];continue}if(!b&&!c){m=cq(g,h,i[1],i[2],i[3],i[4],i[5],i[6],e-n);return{x:m.x,y:m.y,alpha:m.alpha}}}n+=j;g=+i[5];h=+i[6]}k+=i}l.end=k;m=b?n:c?l:a.findDotsAtSegment(g,h,i[1],i[2],i[3],i[4],i[5],i[6],1);m.alpha&&(m={x:m.x,y:m.y,alpha:m.alpha});return m}},cs=cr(1),ct=cr(),cu=cr(0,1);bO.getTotalLength=function(){if(this.type!="path")return;if(this.node.getTotalLength)return this.node.getTotalLength();return cs(this.attrs.path)};bO.getPointAtLength=function(a){if(this.type!="path")return;return ct(this.attrs.path,a)};bO.getSubpath=function(a,b){if(this.type!="path")return;if(B(this.getTotalLength()-b)<"1e-6")return cu(this.attrs.path,a).end;var c=cu(this.attrs.path,b,1);return a?cu(c,a).end:c};a.easing_formulas={linear:function(a){return a},"<":function(a){return C(a,3)},">":function(a){return C(a-1,3)+1},"<>":function(a){a=a*2;if(a<1)return C(a,3)/2;a-=2;return(C(a,3)+2)/2},backIn:function(a){var b=1.70158;return a*a*((b+1)*a-b)},backOut:function(a){a=a-1;var b=1.70158;return a*a*((b+1)*a+b)+1},elastic:function(a){if(a==0||a==1)return a;var b=0.3,c=b/4;return C(2,-10*a)*y.sin((a-c)*(2*D)/b)+1},bounce:function(a){var b=7.5625,c=2.75,d;if(a<1/c)d=b*a*a;else if(a<2/c){a-=1.5/c;d=b*a*a+0.75}else if(a<2.5/c){a-=2.25/c;d=b*a*a+0.9375}else{a-=2.625/c;d=b*a*a+0.984375}return d}};var cv=[],cw=function(){var b=+(new Date);for(var c=0;c<cv[w];c++){var d=cv[c];if(d.stop||d.el.removed)continue;var e=b-d.start,g=d.ms,h=d.easing,i=d.from,j=d.diff,k=d.to,l=d.t,m=d.el,n={},o;if(e<g){var r=h(e/g);for(var s in i)if(i[f](s)){switch(X[s]){case"along":o=r*g*j[s];k.back&&(o=k.len-o);var t=ct(k[s],o);m.translate(j.sx-j.x||0,j.sy-j.y||0);j.x=t.x;j.y=t.y;m.translate(t.x-j.sx,t.y-j.sy);k.rot&&m.rotate(j.r+t.alpha,t.x,t.y);break;case E:o=+i[s]+r*g*j[s];break;case"colour":o="rgb("+[cy(Q(i[s].r+r*g*j[s].r)),cy(Q(i[s].g+r*g*j[s].g)),cy(Q(i[s].b+r*g*j[s].b))][v](",")+")";break;case"path":o=[];for(var u=0,x=i[s][w];u<x;u++){o[u]=[i[s][u][0]];for(var y=1,z=i[s][u][w];y<z;y++)o[u][y]=+i[s][u][y]+r*g*j[s][u][y];o[u]=o[u][v](q)}o=o[v](q);break;case"csv":switch(s){case"translation":var A=r*g*j[s][0]-l.x,B=r*g*j[s][1]-l.y;l.x+=A;l.y+=B;o=A+q+B;break;case"rotation":o=+i[s][0]+r*g*j[s][0];i[s][1]&&(o+=","+i[s][1]+","+i[s][2]);break;case"scale":o=[+i[s][0]+r*g*j[s][0],+i[s][1]+r*g*j[s][1],2 in k[s]?k[s][2]:p,3 in k[s]?k[s][3]:p][v](q);break;case"clip-rect":o=[];u=4;while(u--)o[u]=+i[s][u]+r*g*j[s][u];break}break;default:var C=[].concat(i[s]);o=[];u=m.paper.customAttributes[s].length;while(u--)o[u]=+C[u]+r*g*j[s][u];break}n[s]=o}m.attr(n);m._run&&m._run.call(m)}else{if(k.along){t=ct(k.along,k.len*!k.back);m.translate(j.sx-(j.x||0)+t.x-j.sx,j.sy-(j.y||0)+t.y-j.sy);k.rot&&m.rotate(j.r+t.alpha,t.x,t.y)}(l.x||l.y)&&m.translate(-l.x,-l.y);k.scale&&(k.scale+=p);m.attr(k);cv.splice(c--,1)}}a.svg&&m&&m.paper&&m.paper.safari();cv[w]&&setTimeout(cw)},cx=function(b,c,d,e,f){var g=d-e;c.timeouts.push(setTimeout(function(){a.is(f,"function")&&f.call(c);c.animate(b,g,b.easing)},e))},cy=function(a){return z(A(a,255),0)},cz=function(a,b){if(a==null)return{x:this._.tx,y:this._.ty,toString:co};this._.tx+=+a;this._.ty+=+b;switch(this.type){case"circle":case"ellipse":this.attr({cx:+a+this.attrs.cx,cy:+b+this.attrs.cy});break;case"rect":case"image":case"text":this.attr({x:+a+this.attrs.x,y:+b+this.attrs.y});break;case"path":var c=bp(this.attrs.path);c[0][1]+=+a;c[0][2]+=+b;this.attr({path:c});break}return this};bO.animateWith=function(a,b,c,d,e){for(var f=0,g=cv.length;f<g;f++)cv[f].el.id==a.id&&(b.start=cv[f].start);return this.animate(b,c,d,e)};bO.animateAlong=cA();bO.animateAlongBack=cA(1);function cA(b){return function(c,d,e,f){var g={back:b};a.is(e,"function")?f=e:g.rot=e;c&&c.constructor==bN&&(c=c.attrs.path);c&&(g.along=c);return this.animate(g,d,f)}}function cB(a,b,c,d,e,f){var g=3*b,h=3*(d-b)-g,i=1-g-h,j=3*c,k=3*(e-c)-j,l=1-j-k;function m(a){return((i*a+h)*a+g)*a}function n(a,b){var c=o(a,b);return((l*c+k)*c+j)*c}function o(a,b){var c,d,e,f,j,k;for(e=a,k=0;k<8;k++){f=m(e)-a;if(B(f)<b)return e;j=(3*i*e+2*h)*e+g;if(B(j)<0.000001)break;e=e-f/j}c=0;d=1;e=a;if(e<c)return c;if(e>d)return d;while(c<d){f=m(e);if(B(f-a)<b)return e;a>f?c=e:d=e;e=(d-c)/2+c}return e}return n(a,1/(200*f))}bO.onAnimation=function(a){this._run=a||0;return this};bO.animate=function(c,d,e,g){var h=this;h.timeouts=h.timeouts||[];if(a.is(e,"function")||!e)g=e||null;if(h.removed){g&&g.call(h);return h}var i={},j={},k=false,l={};for(var m in c)if(c[f](m)){if(X[f](m)||h.paper.customAttributes[f](m)){k=true;i[m]=h.attr(m);i[m]==null&&(i[m]=W[m]);j[m]=c[m];switch(X[m]){case"along":var n=cs(c[m]),o=ct(c[m],n*!(!c.back)),p=h.getBBox();l[m]=n/d;l.tx=p.x;l.ty=p.y;l.sx=o.x;l.sy=o.y;j.rot=c.rot;j.back=c.back;j.len=n;c.rot&&(l.r=S(h.rotate())||0);break;case E:l[m]=(j[m]-i[m])/d;break;case"colour":i[m]=a.getRGB(i[m]);var q=a.getRGB(j[m]);l[m]={r:(q.r-i[m].r)/d,g:(q.g-i[m].g)/d,b:(q.b-i[m].b)/d};break;case"path":var t=bw(i[m],j[m]);i[m]=t[0];var u=t[1];l[m]=[];for(var v=0,x=i[m][w];v<x;v++){l[m][v]=[0];for(var y=1,z=i[m][v][w];y<z;y++)l[m][v][y]=(u[v][y]-i[m][v][y])/d}break;case"csv":var A=r(c[m])[s](b),B=r(i[m])[s](b);switch(m){case"translation":i[m]=[0,0];l[m]=[A[0]/d,A[1]/d];break;case"rotation":i[m]=B[1]==A[1]&&B[2]==A[2]?B:[0,A[1],A[2]];l[m]=[(A[0]-i[m][0])/d,0,0];break;case"scale":c[m]=A;i[m]=r(i[m])[s](b);l[m]=[(A[0]-i[m][0])/d,(A[1]-i[m][1])/d,0,0];break;case"clip-rect":i[m]=r(i[m])[s](b);l[m]=[];v=4;while(v--)l[m][v]=(A[v]-i[m][v])/d;break}j[m]=A;break;default:A=[].concat(c[m]);B=[].concat(i[m]);l[m]=[];v=h.paper.customAttributes[m][w];while(v--)l[m][v]=((A[v]||0)-(B[v]||0))/d;break}}}if(k){var G=a.easing_formulas[e];if(!G){G=r(e).match(P);if(G&&G[w]==5){var H=G;G=function(a){return cB(a,+H[1],+H[2],+H[3],+H[4],d)}}else G=function(a){return a}}cv.push({start:c.start||+(new Date),ms:d,easing:G,from:i,diff:l,to:j,el:h,t:{x:0,y:0}});a.is(g,"function")&&(h._ac=setTimeout(function(){g.call(h)},d));cv[w]==1&&setTimeout(cw)}else{var C=[],D;for(var F in c)if(c[f](F)&&Z.test(F)){m={value:c[F]};F=="from"&&(F=0);F=="to"&&(F=100);m.key=T(F,10);C.push(m)}C.sort(be);C[0].key&&C.unshift({key:0,value:h.attrs});for(v=0,x=C[w];v<x;v++)cx(C[v].value,h,d/100*C[v].key,d/100*(C[v-1]&&C[v-1].key||0),C[v-1]&&C[v-1].value.callback);D=C[C[w]-1].value.callback;D&&h.timeouts.push(setTimeout(function(){D.call(h)},d))}return this};bO.stop=function(){for(var a=0;a<cv.length;a++)cv[a].el.id==this.id&&cv.splice(a--,1);for(a=0,ii=this.timeouts&&this.timeouts.length;a<ii;a++)clearTimeout(this.timeouts[a]);this.timeouts=[];clearTimeout(this._ac);delete this._ac;return this};bO.translate=function(a,b){return this.attr({translation:a+" "+b})};bO[H]=function(){return"Raphaël’s object"};a.ae=cv;var cC=function(a){this.items=[];this[w]=0;this.type="set";if(a)for(var b=0,c=a[w];b<c;b++){if(a[b]&&(a[b].constructor==bN||a[b].constructor==cC)){this[this.items[w]]=this.items[this.items[w]]=a[b];this[w]++}}};cC[e][L]=function(){var a,b;for(var c=0,d=arguments[w];c<d;c++){a=arguments[c];if(a&&(a.constructor==bN||a.constructor==cC)){b=this.items[w];this[b]=this.items[b]=a;this[w]++}}return this};cC[e].pop=function(){delete this[this[w]--];return this.items.pop()};for(var cD in bO)bO[f](cD)&&(cC[e][cD]=(function(a){return function(){for(var b=0,c=this.items[w];b<c;b++)this.items[b][a][m](this.items[b],arguments);return this}})(cD));cC[e].attr=function(b,c){if(b&&a.is(b,G)&&a.is(b[0],"object"))for(var d=0,e=b[w];d<e;d++)this.items[d].attr(b[d]);else for(var f=0,g=this.items[w];f<g;f++)this.items[f].attr(b,c);return this};cC[e].animate=function(b,c,d,e){(a.is(d,"function")||!d)&&(e=d||null);var f=this.items[w],g=f,h,i=this,j;e&&(j=function(){!(--f)&&e.call(i)});d=a.is(d,F)?d:j;h=this.items[--g].animate(b,c,d,j);while(g--)this.items[g]&&!this.items[g].removed&&this.items[g].animateWith(h,b,c,d,j);return this};cC[e].insertAfter=function(a){var b=this.items[w];while(b--)this.items[b].insertAfter(a);return this};cC[e].getBBox=function(){var a=[],b=[],c=[],d=[];for(var e=this.items[w];e--;){var f=this.items[e].getBBox();a[L](f.x);b[L](f.y);c[L](f.x+f.width);d[L](f.y+f.height)}a=A[m](0,a);b=A[m](0,b);return{x:a,y:b,width:z[m](0,c)-a,height:z[m](0,d)-b}};cC[e].clone=function(a){a=new cC;for(var b=0,c=this.items[w];b<c;b++)a[L](this.items[b].clone());return a};a.registerFont=function(a){if(!a.face)return a;this.fonts=this.fonts||{};var b={w:a.w,face:{},glyphs:{}},c=a.face["font-family"];for(var d in a.face)a.face[f](d)&&(b.face[d]=a.face[d]);this.fonts[c]?this.fonts[c][L](b):this.fonts[c]=[b];if(!a.svg){b.face["units-per-em"]=T(a.face["units-per-em"],10);for(var e in a.glyphs)if(a.glyphs[f](e)){var g=a.glyphs[e];b.glyphs[e]={w:g.w,k:{},d:g.d&&"M"+g.d[Y](/[mlcxtrv]/g,function(a){return({l:"L",c:"C",x:"z",t:"m",r:"l",v:"c"})[a]||"M"})+"z"};if(g.k)for(var h in g.k)g[f](h)&&(b.glyphs[e].k[h]=g.k[h])}}return a};k.getFont=function(b,c,d,e){e=e||"normal";d=d||"normal";c=+c||({normal:400,bold:700,lighter:300,bolder:800})[c]||400;if(!a.fonts)return;var g=a.fonts[b];if(!g){var h=new RegExp("(^|\\s)"+b[Y](/[^\w\d\s+!~.:_-]/g,p)+"(\\s|$)","i");for(var i in a.fonts)if(a.fonts[f](i)){if(h.test(i)){g=a.fonts[i];break}}}var j;if(g)for(var k=0,l=g[w];k<l;k++){j=g[k];if(j.face["font-weight"]==c&&(j.face["font-style"]==d||!j.face["font-style"])&&j.face["font-stretch"]==e)break}return j};k.print=function(c,d,e,f,g,h,i){h=h||"middle";i=z(A(i||0,1),-1);var j=this.set(),k=r(e)[s](p),l=0,m=p,n;a.is(f,e)&&(f=this.getFont(f));if(f){n=(g||16)/f.face["units-per-em"];var o=f.face.bbox.split(b),q=+o[0],t=+o[1]+(h=="baseline"?o[3]-o[1]+ +f.face.descent:(o[3]-o[1])/2);for(var u=0,v=k[w];u<v;u++){var x=u&&f.glyphs[k[u-1]]||{},y=f.glyphs[k[u]];l+=u?(x.w||f.w)+(x.k&&x.k[k[u]]||0)+f.w*i:0;y&&y.d&&j[L](this.path(y.d).attr({fill:"#000",stroke:"none",translation:[l,0]}))}j.scale(n,n,q,t).translate(c-q,d-t)}return j};a.format=function(b,c){var e=a.is(c,G)?[0][n](c):arguments;b&&a.is(b,F)&&e[w]-1&&(b=b[Y](d,function(a,b){return e[++b]==null?p:e[b]}));return b||p};a.ninja=function(){i.was?h.Raphael=i.is:delete Raphael;return a};a.el=bO;a.st=cC[e];i.was?h.Raphael=a:Raphael=a})()

/*  Prototype JavaScript framework, version 1.6.1
 *  (c) 2005-2009 Sam Stephenson
 *
 *  Prototype is freely distributable under the terms of an MIT-style license.
 *  For details, see the Prototype web site: http://www.prototypejs.org/
 *
 *--------------------------------------------------------------------------*/

var Prototype = {
  Version: '1.6.1',

  Browser: (function(){
    var ua = navigator.userAgent;
    var isOpera = Object.prototype.toString.call(window.opera) == '[object Opera]';
    return {
      IE:             !!window.attachEvent && !isOpera,
      Opera:          isOpera,
      WebKit:         ua.indexOf('AppleWebKit/') > -1,
      Gecko:          ua.indexOf('Gecko') > -1 && ua.indexOf('KHTML') === -1,
      MobileSafari:   /Apple.*Mobile.*Safari/.test(ua)
    }
  })(),

  BrowserFeatures: {
    XPath: !!document.evaluate,
    SelectorsAPI: !!document.querySelector,
    ElementExtensions: (function() {
      var constructor = window.Element || window.HTMLElement;
      return !!(constructor && constructor.prototype);
    })(),
    SpecificElementExtensions: (function() {
      if (typeof window.HTMLDivElement !== 'undefined')
        return true;

      var div = document.createElement('div');
      var form = document.createElement('form');
      var isSupported = false;

      if (div['__proto__'] && (div['__proto__'] !== form['__proto__'])) {
        isSupported = true;
      }

      div = form = null;

      return isSupported;
    })()
  },

  ScriptFragment: '<script[^>]*>([\\S\\s]*?)<\/script>',
  JSONFilter: /^\/\*-secure-([\s\S]*)\*\/\s*$/,

  emptyFunction: function() { },
  K: function(x) { return x }
};

if (Prototype.Browser.MobileSafari)
  Prototype.BrowserFeatures.SpecificElementExtensions = false;


var Abstract = { };


var Try = {
  these: function() {
    var returnValue;

    for (var i = 0, length = arguments.length; i < length; i++) {
      var lambda = arguments[i];
      try {
        returnValue = lambda();
        break;
      } catch (e) { }
    }

    return returnValue;
  }
};

/* Based on Alex Arnell's inheritance implementation. */

var Class = (function() {
  function subclass() {};
  function create() {
    var parent = null, properties = $A(arguments);
    if (Object.isFunction(properties[0]))
      parent = properties.shift();

    function klass() {
      this.initialize.apply(this, arguments);
    }

    Object.extend(klass, Class.Methods);
    klass.superclass = parent;
    klass.subclasses = [];

    if (parent) {
      subclass.prototype = parent.prototype;
      klass.prototype = new subclass;
      parent.subclasses.push(klass);
    }

    for (var i = 0; i < properties.length; i++)
      klass.addMethods(properties[i]);

    if (!klass.prototype.initialize)
      klass.prototype.initialize = Prototype.emptyFunction;

    klass.prototype.constructor = klass;
    return klass;
  }

  function addMethods(source) {
    var ancestor   = this.superclass && this.superclass.prototype;
    var properties = Object.keys(source);

    if (!Object.keys({ toString: true }).length) {
      if (source.toString != Object.prototype.toString)
        properties.push("toString");
      if (source.valueOf != Object.prototype.valueOf)
        properties.push("valueOf");
    }

    for (var i = 0, length = properties.length; i < length; i++) {
      var property = properties[i], value = source[property];
      if (ancestor && Object.isFunction(value) &&
          value.argumentNames().first() == "$super") {
        var method = value;
        value = (function(m) {
          return function() { return ancestor[m].apply(this, arguments); };
        })(property).wrap(method);

        value.valueOf = method.valueOf.bind(method);
        value.toString = method.toString.bind(method);
      }
      this.prototype[property] = value;
    }

    return this;
  }

  return {
    create: create,
    Methods: {
      addMethods: addMethods
    }
  };
})();
(function() {

  var _toString = Object.prototype.toString;

  function extend(destination, source) {
    for (var property in source)
      destination[property] = source[property];
    return destination;
  }

  function inspect(object) {
    try {
      if (isUndefined(object)) return 'undefined';
      if (object === null) return 'null';
      return object.inspect ? object.inspect() : String(object);
    } catch (e) {
      if (e instanceof RangeError) return '...';
      throw e;
    }
  }

  function toJSON(object) {
    var type = typeof object;
    switch (type) {
      case 'undefined':
      case 'function':
      case 'unknown': return;
      case 'boolean': return object.toString();
    }

    if (object === null) return 'null';
    if (object.toJSON) return object.toJSON();
    if (isElement(object)) return;

    var results = [];
    for (var property in object) {
      var value = toJSON(object[property]);
      if (!isUndefined(value))
        results.push(property.toJSON() + ': ' + value);
    }

    return '{' + results.join(', ') + '}';
  }

  function toQueryString(object) {
    return $H(object).toQueryString();
  }

  function toHTML(object) {
    return object && object.toHTML ? object.toHTML() : String.interpret(object);
  }

  function keys(object) {
    var results = [];
    for (var property in object)
      results.push(property);
    return results;
  }

  function values(object) {
    var results = [];
    for (var property in object)
      results.push(object[property]);
    return results;
  }

  function clone(object) {
    return extend({ }, object);
  }

  function isElement(object) {
    return !!(object && object.nodeType == 1);
  }

  function isArray(object) {
    return _toString.call(object) == "[object Array]";
  }


  function isHash(object) {
    return object instanceof Hash;
  }

  function isFunction(object) {
    return typeof object === "function";
  }

  function isString(object) {
    return _toString.call(object) == "[object String]";
  }

  function isNumber(object) {
    return _toString.call(object) == "[object Number]";
  }

  function isUndefined(object) {
    return typeof object === "undefined";
  }

  extend(Object, {
    extend:        extend,
    inspect:       inspect,
    toJSON:        toJSON,
    toQueryString: toQueryString,
    toHTML:        toHTML,
    keys:          keys,
    values:        values,
    clone:         clone,
    isElement:     isElement,
    isArray:       isArray,
    isHash:        isHash,
    isFunction:    isFunction,
    isString:      isString,
    isNumber:      isNumber,
    isUndefined:   isUndefined
  });
})();
Object.extend(Function.prototype, (function() {
  var slice = Array.prototype.slice;

  function update(array, args) {
    var arrayLength = array.length, length = args.length;
    while (length--) array[arrayLength + length] = args[length];
    return array;
  }

  function merge(array, args) {
    array = slice.call(array, 0);
    return update(array, args);
  }

  function argumentNames() {
    var names = this.toString().match(/^[\s\(]*function[^(]*\(([^)]*)\)/)[1]
      .replace(/\/\/.*?[\r\n]|\/\*(?:.|[\r\n])*?\*\//g, '')
      .replace(/\s+/g, '').split(',');
    return names.length == 1 && !names[0] ? [] : names;
  }

  function bind(context) {
    if (arguments.length < 2 && Object.isUndefined(arguments[0])) return this;
    var __method = this, args = slice.call(arguments, 1);
    return function() {
      var a = merge(args, arguments);
      return __method.apply(context, a);
    }
  }

  function bindAsEventListener(context) {
    var __method = this, args = slice.call(arguments, 1);
    return function(event) {
      var a = update([event || window.event], args);
      return __method.apply(context, a);
    }
  }

  function curry() {
    if (!arguments.length) return this;
    var __method = this, args = slice.call(arguments, 0);
    return function() {
      var a = merge(args, arguments);
      return __method.apply(this, a);
    }
  }

  function delay(timeout) {
    var __method = this, args = slice.call(arguments, 1);
    timeout = timeout * 1000
    return window.setTimeout(function() {
      return __method.apply(__method, args);
    }, timeout);
  }

  function defer() {
    var args = update([0.01], arguments);
    return this.delay.apply(this, args);
  }

  function wrap(wrapper) {
    var __method = this;
    return function() {
      var a = update([__method.bind(this)], arguments);
      return wrapper.apply(this, a);
    }
  }

  function methodize() {
    if (this._methodized) return this._methodized;
    var __method = this;
    return this._methodized = function() {
      var a = update([this], arguments);
      return __method.apply(null, a);
    };
  }

  return {
    argumentNames:       argumentNames,
    bind:                bind,
    bindAsEventListener: bindAsEventListener,
    curry:               curry,
    delay:               delay,
    defer:               defer,
    wrap:                wrap,
    methodize:           methodize
  }
})());


Date.prototype.toJSON = function() {
  return '"' + this.getUTCFullYear() + '-' +
    (this.getUTCMonth() + 1).toPaddedString(2) + '-' +
    this.getUTCDate().toPaddedString(2) + 'T' +
    this.getUTCHours().toPaddedString(2) + ':' +
    this.getUTCMinutes().toPaddedString(2) + ':' +
    this.getUTCSeconds().toPaddedString(2) + 'Z"';
};


RegExp.prototype.match = RegExp.prototype.test;

RegExp.escape = function(str) {
  return String(str).replace(/([.*+?^=!:${}()|[\]\/\\])/g, '\\$1');
};
var PeriodicalExecuter = Class.create({
  initialize: function(callback, frequency) {
    this.callback = callback;
    this.frequency = frequency;
    this.currentlyExecuting = false;

    this.registerCallback();
  },

  registerCallback: function() {
    this.timer = setInterval(this.onTimerEvent.bind(this), this.frequency * 1000);
  },

  execute: function() {
    this.callback(this);
  },

  stop: function() {
    if (!this.timer) return;
    clearInterval(this.timer);
    this.timer = null;
  },

  onTimerEvent: function() {
    if (!this.currentlyExecuting) {
      try {
        this.currentlyExecuting = true;
        this.execute();
        this.currentlyExecuting = false;
      } catch(e) {
        this.currentlyExecuting = false;
        throw e;
      }
    }
  }
});
Object.extend(String, {
  interpret: function(value) {
    return value == null ? '' : String(value);
  },
  specialChar: {
    '\b': '\\b',
    '\t': '\\t',
    '\n': '\\n',
    '\f': '\\f',
    '\r': '\\r',
    '\\': '\\\\'
  }
});

Object.extend(String.prototype, (function() {

  function prepareReplacement(replacement) {
    if (Object.isFunction(replacement)) return replacement;
    var template = new Template(replacement);
    return function(match) { return template.evaluate(match) };
  }

  function gsub(pattern, replacement) {
    var result = '', source = this, match;
    replacement = prepareReplacement(replacement);

    if (Object.isString(pattern))
      pattern = RegExp.escape(pattern);

    if (!(pattern.length || pattern.source)) {
      replacement = replacement('');
      return replacement + source.split('').join(replacement) + replacement;
    }

    while (source.length > 0) {
      if (match = source.match(pattern)) {
        result += source.slice(0, match.index);
        result += String.interpret(replacement(match));
        source  = source.slice(match.index + match[0].length);
      } else {
        result += source, source = '';
      }
    }
    return result;
  }

  function sub(pattern, replacement, count) {
    replacement = prepareReplacement(replacement);
    count = Object.isUndefined(count) ? 1 : count;

    return this.gsub(pattern, function(match) {
      if (--count < 0) return match[0];
      return replacement(match);
    });
  }

  function scan(pattern, iterator) {
    this.gsub(pattern, iterator);
    return String(this);
  }

  function truncate(length, truncation) {
    length = length || 30;
    truncation = Object.isUndefined(truncation) ? '...' : truncation;
    return this.length > length ?
      this.slice(0, length - truncation.length) + truncation : String(this);
  }

  function strip() {
    return this.replace(/^\s+/, '').replace(/\s+$/, '');
  }

  function stripTags() {
    return this.replace(/<\w+(\s+("[^"]*"|'[^']*'|[^>])+)?>|<\/\w+>/gi, '');
  }

  function stripScripts() {
    return this.replace(new RegExp(Prototype.ScriptFragment, 'img'), '');
  }

  function extractScripts() {
    var matchAll = new RegExp(Prototype.ScriptFragment, 'img');
    var matchOne = new RegExp(Prototype.ScriptFragment, 'im');
    return (this.match(matchAll) || []).map(function(scriptTag) {
      return (scriptTag.match(matchOne) || ['', ''])[1];
    });
  }

  function evalScripts() {
    return this.extractScripts().map(function(script) { return eval(script) });
  }

  function escapeHTML() {
    return this.replace(/&/g,'&amp;').replace(/</g,'&lt;').replace(/>/g,'&gt;');
  }

  function unescapeHTML() {
    return this.stripTags().replace(/&lt;/g,'<').replace(/&gt;/g,'>').replace(/&amp;/g,'&');
  }


  function toQueryParams(separator) {
    var match = this.strip().match(/([^?#]*)(#.*)?$/);
    if (!match) return { };

    return match[1].split(separator || '&').inject({ }, function(hash, pair) {
      if ((pair = pair.split('='))[0]) {
        var key = decodeURIComponent(pair.shift());
        var value = pair.length > 1 ? pair.join('=') : pair[0];
        if (value != undefined) value = decodeURIComponent(value);

        if (key in hash) {
          if (!Object.isArray(hash[key])) hash[key] = [hash[key]];
          hash[key].push(value);
        }
        else hash[key] = value;
      }
      return hash;
    });
  }

  function toArray() {
    return this.split('');
  }

  function succ() {
    return this.slice(0, this.length - 1) +
      String.fromCharCode(this.charCodeAt(this.length - 1) + 1);
  }

  function times(count) {
    return count < 1 ? '' : new Array(count + 1).join(this);
  }

  function camelize() {
    var parts = this.split('-'), len = parts.length;
    if (len == 1) return parts[0];

    var camelized = this.charAt(0) == '-'
      ? parts[0].charAt(0).toUpperCase() + parts[0].substring(1)
      : parts[0];

    for (var i = 1; i < len; i++)
      camelized += parts[i].charAt(0).toUpperCase() + parts[i].substring(1);

    return camelized;
  }

  function capitalize() {
    return this.charAt(0).toUpperCase() + this.substring(1).toLowerCase();
  }

  function underscore() {
    return this.replace(/::/g, '/')
               .replace(/([A-Z]+)([A-Z][a-z])/g, '$1_$2')
               .replace(/([a-z\d])([A-Z])/g, '$1_$2')
               .replace(/-/g, '_')
               .toLowerCase();
  }

  function dasherize() {
    return this.replace(/_/g, '-');
  }

  function inspect(useDoubleQuotes) {
    var escapedString = this.replace(/[\x00-\x1f\\]/g, function(character) {
      if (character in String.specialChar) {
        return String.specialChar[character];
      }
      return '\\u00' + character.charCodeAt().toPaddedString(2, 16);
    });
    if (useDoubleQuotes) return '"' + escapedString.replace(/"/g, '\\"') + '"';
    return "'" + escapedString.replace(/'/g, '\\\'') + "'";
  }

  function toJSON() {
    return this.inspect(true);
  }

  function unfilterJSON(filter) {
    return this.replace(filter || Prototype.JSONFilter, '$1');
  }

  function isJSON() {
    var str = this;
    if (str.blank()) return false;
    str = this.replace(/\\./g, '@').replace(/"[^"\\\n\r]*"/g, '');
    return (/^[,:{}\[\]0-9.\-+Eaeflnr-u \n\r\t]*$/).test(str);
  }

  function evalJSON(sanitize) {
    var json = this.unfilterJSON();
    try {
      if (!sanitize || json.isJSON()) return eval('(' + json + ')');
    } catch (e) { }
    throw new SyntaxError('Badly formed JSON string: ' + this.inspect());
  }

  function include(pattern) {
    return this.indexOf(pattern) > -1;
  }

  function startsWith(pattern) {
    return this.indexOf(pattern) === 0;
  }

  function endsWith(pattern) {
    var d = this.length - pattern.length;
    return d >= 0 && this.lastIndexOf(pattern) === d;
  }

  function empty() {
    return this == '';
  }

  function blank() {
    return /^\s*$/.test(this);
  }

  function interpolate(object, pattern) {
    return new Template(this, pattern).evaluate(object);
  }

  return {
    gsub:           gsub,
    sub:            sub,
    scan:           scan,
    truncate:       truncate,
    strip:          String.prototype.trim ? String.prototype.trim : strip,
    stripTags:      stripTags,
    stripScripts:   stripScripts,
    extractScripts: extractScripts,
    evalScripts:    evalScripts,
    escapeHTML:     escapeHTML,
    unescapeHTML:   unescapeHTML,
    toQueryParams:  toQueryParams,
    parseQuery:     toQueryParams,
    toArray:        toArray,
    succ:           succ,
    times:          times,
    camelize:       camelize,
    capitalize:     capitalize,
    underscore:     underscore,
    dasherize:      dasherize,
    inspect:        inspect,
    toJSON:         toJSON,
    unfilterJSON:   unfilterJSON,
    isJSON:         isJSON,
    evalJSON:       evalJSON,
    include:        include,
    startsWith:     startsWith,
    endsWith:       endsWith,
    empty:          empty,
    blank:          blank,
    interpolate:    interpolate
  };
})());

var Template = Class.create({
  initialize: function(template, pattern) {
    this.template = template.toString();
    this.pattern = pattern || Template.Pattern;
  },

  evaluate: function(object) {
    if (object && Object.isFunction(object.toTemplateReplacements))
      object = object.toTemplateReplacements();

    return this.template.gsub(this.pattern, function(match) {
      if (object == null) return (match[1] + '');

      var before = match[1] || '';
      if (before == '\\') return match[2];

      var ctx = object, expr = match[3];
      var pattern = /^([^.[]+|\[((?:.*?[^\\])?)\])(\.|\[|$)/;
      match = pattern.exec(expr);
      if (match == null) return before;

      while (match != null) {
        var comp = match[1].startsWith('[') ? match[2].replace(/\\\\]/g, ']') : match[1];
        ctx = ctx[comp];
        if (null == ctx || '' == match[3]) break;
        expr = expr.substring('[' == match[3] ? match[1].length : match[0].length);
        match = pattern.exec(expr);
      }

      return before + String.interpret(ctx);
    });
  }
});
Template.Pattern = /(^|.|\r|\n)(#\{(.*?)\})/;

var $break = { };

var Enumerable = (function() {
  function each(iterator, context) {
    var index = 0;
    try {
      this._each(function(value) {
        iterator.call(context, value, index++);
      });
    } catch (e) {
      if (e != $break) throw e;
    }
    return this;
  }

  function eachSlice(number, iterator, context) {
    var index = -number, slices = [], array = this.toArray();
    if (number < 1) return array;
    while ((index += number) < array.length)
      slices.push(array.slice(index, index+number));
    return slices.collect(iterator, context);
  }

  function all(iterator, context) {
    iterator = iterator || Prototype.K;
    var result = true;
    this.each(function(value, index) {
      result = result && !!iterator.call(context, value, index);
      if (!result) throw $break;
    });
    return result;
  }

  function any(iterator, context) {
    iterator = iterator || Prototype.K;
    var result = false;
    this.each(function(value, index) {
      if (result = !!iterator.call(context, value, index))
        throw $break;
    });
    return result;
  }

  function collect(iterator, context) {
    iterator = iterator || Prototype.K;
    var results = [];
    this.each(function(value, index) {
      results.push(iterator.call(context, value, index));
    });
    return results;
  }

  function detect(iterator, context) {
    var result;
    this.each(function(value, index) {
      if (iterator.call(context, value, index)) {
        result = value;
        throw $break;
      }
    });
    return result;
  }

  function findAll(iterator, context) {
    var results = [];
    this.each(function(value, index) {
      if (iterator.call(context, value, index))
        results.push(value);
    });
    return results;
  }

  function grep(filter, iterator, context) {
    iterator = iterator || Prototype.K;
    var results = [];

    if (Object.isString(filter))
      filter = new RegExp(RegExp.escape(filter));

    this.each(function(value, index) {
      if (filter.match(value))
        results.push(iterator.call(context, value, index));
    });
    return results;
  }

  function include(object) {
    if (Object.isFunction(this.indexOf))
      if (this.indexOf(object) != -1) return true;

    var found = false;
    this.each(function(value) {
      if (value == object) {
        found = true;
        throw $break;
      }
    });
    return found;
  }

  function inGroupsOf(number, fillWith) {
    fillWith = Object.isUndefined(fillWith) ? null : fillWith;
    return this.eachSlice(number, function(slice) {
      while(slice.length < number) slice.push(fillWith);
      return slice;
    });
  }

  function inject(memo, iterator, context) {
    this.each(function(value, index) {
      memo = iterator.call(context, memo, value, index);
    });
    return memo;
  }

  function invoke(method) {
    var args = $A(arguments).slice(1);
    return this.map(function(value) {
      return value[method].apply(value, args);
    });
  }

  function max(iterator, context) {
    iterator = iterator || Prototype.K;
    var result;
    this.each(function(value, index) {
      value = iterator.call(context, value, index);
      if (result == null || value >= result)
        result = value;
    });
    return result;
  }

  function min(iterator, context) {
    iterator = iterator || Prototype.K;
    var result;
    this.each(function(value, index) {
      value = iterator.call(context, value, index);
      if (result == null || value < result)
        result = value;
    });
    return result;
  }

  function partition(iterator, context) {
    iterator = iterator || Prototype.K;
    var trues = [], falses = [];
    this.each(function(value, index) {
      (iterator.call(context, value, index) ?
        trues : falses).push(value);
    });
    return [trues, falses];
  }

  function pluck(property) {
    var results = [];
    this.each(function(value) {
      results.push(value[property]);
    });
    return results;
  }

  function reject(iterator, context) {
    var results = [];
    this.each(function(value, index) {
      if (!iterator.call(context, value, index))
        results.push(value);
    });
    return results;
  }

  function sortBy(iterator, context) {
    return this.map(function(value, index) {
      return {
        value: value,
        criteria: iterator.call(context, value, index)
      };
    }).sort(function(left, right) {
      var a = left.criteria, b = right.criteria;
      return a < b ? -1 : a > b ? 1 : 0;
    }).pluck('value');
  }

  function toArray() {
    return this.map();
  }

  function zip() {
    var iterator = Prototype.K, args = $A(arguments);
    if (Object.isFunction(args.last()))
      iterator = args.pop();

    var collections = [this].concat(args).map($A);
    return this.map(function(value, index) {
      return iterator(collections.pluck(index));
    });
  }

  function size() {
    return this.toArray().length;
  }

  function inspect() {
    return '#<Enumerable:' + this.toArray().inspect() + '>';
  }









  return {
    each:       each,
    eachSlice:  eachSlice,
    all:        all,
    every:      all,
    any:        any,
    some:       any,
    collect:    collect,
    map:        collect,
    detect:     detect,
    findAll:    findAll,
    select:     findAll,
    filter:     findAll,
    grep:       grep,
    include:    include,
    member:     include,
    inGroupsOf: inGroupsOf,
    inject:     inject,
    invoke:     invoke,
    max:        max,
    min:        min,
    partition:  partition,
    pluck:      pluck,
    reject:     reject,
    sortBy:     sortBy,
    toArray:    toArray,
    entries:    toArray,
    zip:        zip,
    size:       size,
    inspect:    inspect,
    find:       detect
  };
})();
function $A(iterable) {
  if (!iterable) return [];
  if ('toArray' in Object(iterable)) return iterable.toArray();
  var length = iterable.length || 0, results = new Array(length);
  while (length--) results[length] = iterable[length];
  return results;
}

function $w(string) {
  if (!Object.isString(string)) return [];
  string = string.strip();
  return string ? string.split(/\s+/) : [];
}

Array.from = $A;


(function() {
  var arrayProto = Array.prototype,
      slice = arrayProto.slice,
      _each = arrayProto.forEach; // use native browser JS 1.6 implementation if available

  function each(iterator) {
    for (var i = 0, length = this.length; i < length; i++)
      iterator(this[i]);
  }
  if (!_each) _each = each;

  function clear() {
    this.length = 0;
    return this;
  }

  function first() {
    return this[0];
  }

  function last() {
    return this[this.length - 1];
  }

  function compact() {
    return this.select(function(value) {
      return value != null;
    });
  }

  function flatten() {
    return this.inject([], function(array, value) {
      if (Object.isArray(value))
        return array.concat(value.flatten());
      array.push(value);
      return array;
    });
  }

  function without() {
    var values = slice.call(arguments, 0);
    return this.select(function(value) {
      return !values.include(value);
    });
  }

  function reverse(inline) {
    return (inline !== false ? this : this.toArray())._reverse();
  }

  function uniq(sorted) {
    return this.inject([], function(array, value, index) {
      if (0 == index || (sorted ? array.last() != value : !array.include(value)))
        array.push(value);
      return array;
    });
  }

  function intersect(array) {
    return this.uniq().findAll(function(item) {
      return array.detect(function(value) { return item === value });
    });
  }


  function clone() {
    return slice.call(this, 0);
  }

  function size() {
    return this.length;
  }

  function inspect() {
    return '[' + this.map(Object.inspect).join(', ') + ']';
  }

  function toJSON() {
    var results = [];
    this.each(function(object) {
      var value = Object.toJSON(object);
      if (!Object.isUndefined(value)) results.push(value);
    });
    return '[' + results.join(', ') + ']';
  }

  function indexOf(item, i) {
    i || (i = 0);
    var length = this.length;
    if (i < 0) i = length + i;
    for (; i < length; i++)
      if (this[i] === item) return i;
    return -1;
  }

  function lastIndexOf(item, i) {
    i = isNaN(i) ? this.length : (i < 0 ? this.length + i : i) + 1;
    var n = this.slice(0, i).reverse().indexOf(item);
    return (n < 0) ? n : i - n - 1;
  }

  function concat() {
    var array = slice.call(this, 0), item;
    for (var i = 0, length = arguments.length; i < length; i++) {
      item = arguments[i];
      if (Object.isArray(item) && !('callee' in item)) {
        for (var j = 0, arrayLength = item.length; j < arrayLength; j++)
          array.push(item[j]);
      } else {
        array.push(item);
      }
    }
    return array;
  }

  Object.extend(arrayProto, Enumerable);

  if (!arrayProto._reverse)
    arrayProto._reverse = arrayProto.reverse;

  Object.extend(arrayProto, {
    _each:     _each,
    clear:     clear,
    first:     first,
    last:      last,
    compact:   compact,
    flatten:   flatten,
    without:   without,
    reverse:   reverse,
    uniq:      uniq,
    intersect: intersect,
    clone:     clone,
    toArray:   clone,
    size:      size,
    inspect:   inspect,
    toJSON:    toJSON
  });

  var CONCAT_ARGUMENTS_BUGGY = (function() {
    return [].concat(arguments)[0][0] !== 1;
  })(1,2)

  if (CONCAT_ARGUMENTS_BUGGY) arrayProto.concat = concat;

  if (!arrayProto.indexOf) arrayProto.indexOf = indexOf;
  if (!arrayProto.lastIndexOf) arrayProto.lastIndexOf = lastIndexOf;
})();
function $H(object) {
  return new Hash(object);
};

var Hash = Class.create(Enumerable, (function() {
  function initialize(object) {
    this._object = Object.isHash(object) ? object.toObject() : Object.clone(object);
  }

  function _each(iterator) {
    for (var key in this._object) {
      var value = this._object[key], pair = [key, value];
      pair.key = key;
      pair.value = value;
      iterator(pair);
    }
  }

  function set(key, value) {
    return this._object[key] = value;
  }

  function get(key) {
    if (this._object[key] !== Object.prototype[key])
      return this._object[key];
  }

  function unset(key) {
    var value = this._object[key];
    delete this._object[key];
    return value;
  }

  function toObject() {
    return Object.clone(this._object);
  }

  function keys() {
    return this.pluck('key');
  }

  function values() {
    return this.pluck('value');
  }

  function index(value) {
    var match = this.detect(function(pair) {
      return pair.value === value;
    });
    return match && match.key;
  }

  function merge(object) {
    return this.clone().update(object);
  }

  function update(object) {
    return new Hash(object).inject(this, function(result, pair) {
      result.set(pair.key, pair.value);
      return result;
    });
  }

  function toQueryPair(key, value) {
    if (Object.isUndefined(value)) return key;
    return key + '=' + encodeURIComponent(String.interpret(value));
  }

  function toQueryString() {
    return this.inject([], function(results, pair) {
      var key = encodeURIComponent(pair.key), values = pair.value;

      if (values && typeof values == 'object') {
        if (Object.isArray(values))
          return results.concat(values.map(toQueryPair.curry(key)));
      } else results.push(toQueryPair(key, values));
      return results;
    }).join('&');
  }

  function inspect() {
    return '#<Hash:{' + this.map(function(pair) {
      return pair.map(Object.inspect).join(': ');
    }).join(', ') + '}>';
  }

  function toJSON() {
    return Object.toJSON(this.toObject());
  }

  function clone() {
    return new Hash(this);
  }

  return {
    initialize:             initialize,
    _each:                  _each,
    set:                    set,
    get:                    get,
    unset:                  unset,
    toObject:               toObject,
    toTemplateReplacements: toObject,
    keys:                   keys,
    values:                 values,
    index:                  index,
    merge:                  merge,
    update:                 update,
    toQueryString:          toQueryString,
    inspect:                inspect,
    toJSON:                 toJSON,
    clone:                  clone
  };
})());

Hash.from = $H;
Object.extend(Number.prototype, (function() {
  function toColorPart() {
    return this.toPaddedString(2, 16);
  }

  function succ() {
    return this + 1;
  }

  function times(iterator, context) {
    $R(0, this, true).each(iterator, context);
    return this;
  }

  function toPaddedString(length, radix) {
    var string = this.toString(radix || 10);
    return '0'.times(length - string.length) + string;
  }

  function toJSON() {
    return isFinite(this) ? this.toString() : 'null';
  }

  function abs() {
    return Math.abs(this);
  }

  function round() {
    return Math.round(this);
  }

  function ceil() {
    return Math.ceil(this);
  }

  function floor() {
    return Math.floor(this);
  }

  return {
    toColorPart:    toColorPart,
    succ:           succ,
    times:          times,
    toPaddedString: toPaddedString,
    toJSON:         toJSON,
    abs:            abs,
    round:          round,
    ceil:           ceil,
    floor:          floor
  };
})());

function $R(start, end, exclusive) {
  return new ObjectRange(start, end, exclusive);
}

var ObjectRange = Class.create(Enumerable, (function() {
  function initialize(start, end, exclusive) {
    this.start = start;
    this.end = end;
    this.exclusive = exclusive;
  }

  function _each(iterator) {
    var value = this.start;
    while (this.include(value)) {
      iterator(value);
      value = value.succ();
    }
  }

  function include(value) {
    if (value < this.start)
      return false;
    if (this.exclusive)
      return value < this.end;
    return value <= this.end;
  }

  return {
    initialize: initialize,
    _each:      _each,
    include:    include
  };
})());



var Ajax = {
  getTransport: function() {
    return Try.these(
      function() {return new XMLHttpRequest()},
      function() {return new ActiveXObject('Msxml2.XMLHTTP')},
      function() {return new ActiveXObject('Microsoft.XMLHTTP')}
    ) || false;
  },

  activeRequestCount: 0
};

Ajax.Responders = {
  responders: [],

  _each: function(iterator) {
    this.responders._each(iterator);
  },

  register: function(responder) {
    if (!this.include(responder))
      this.responders.push(responder);
  },

  unregister: function(responder) {
    this.responders = this.responders.without(responder);
  },

  dispatch: function(callback, request, transport, json) {
    this.each(function(responder) {
      if (Object.isFunction(responder[callback])) {
        try {
          responder[callback].apply(responder, [request, transport, json]);
        } catch (e) { }
      }
    });
  }
};

Object.extend(Ajax.Responders, Enumerable);

Ajax.Responders.register({
  onCreate:   function() { Ajax.activeRequestCount++ },
  onComplete: function() { Ajax.activeRequestCount-- }
});
Ajax.Base = Class.create({
  initialize: function(options) {
    this.options = {
      method:       'post',
      asynchronous: true,
      contentType:  'application/x-www-form-urlencoded',
      encoding:     'UTF-8',
      parameters:   '',
      evalJSON:     true,
      evalJS:       true
    };
    Object.extend(this.options, options || { });

    this.options.method = this.options.method.toLowerCase();

    if (Object.isString(this.options.parameters))
      this.options.parameters = this.options.parameters.toQueryParams();
    else if (Object.isHash(this.options.parameters))
      this.options.parameters = this.options.parameters.toObject();
  }
});
Ajax.Request = Class.create(Ajax.Base, {
  _complete: false,

  initialize: function($super, url, options) {
    $super(options);
    this.transport = Ajax.getTransport();
    this.request(url);
  },

  request: function(url) {
    this.url = url;
    this.method = this.options.method;
    var params = Object.clone(this.options.parameters);

    if (!['get', 'post'].include(this.method)) {
      params['_method'] = this.method;
      this.method = 'post';
    }

    this.parameters = params;

    if (params = Object.toQueryString(params)) {
      if (this.method == 'get')
        this.url += (this.url.include('?') ? '&' : '?') + params;
      else if (/Konqueror|Safari|KHTML/.test(navigator.userAgent))
        params += '&_=';
    }

    try {
      var response = new Ajax.Response(this);
      if (this.options.onCreate) this.options.onCreate(response);
      Ajax.Responders.dispatch('onCreate', this, response);

      this.transport.open(this.method.toUpperCase(), this.url,
        this.options.asynchronous);

      if (this.options.asynchronous) this.respondToReadyState.bind(this).defer(1);

      this.transport.onreadystatechange = this.onStateChange.bind(this);
      this.setRequestHeaders();

      this.body = this.method == 'post' ? (this.options.postBody || params) : null;
      this.transport.send(this.body);

      /* Force Firefox to handle ready state 4 for synchronous requests */
      if (!this.options.asynchronous && this.transport.overrideMimeType)
        this.onStateChange();

    }
    catch (e) {
      this.dispatchException(e);
    }
  },

  onStateChange: function() {
    var readyState = this.transport.readyState;
    if (readyState > 1 && !((readyState == 4) && this._complete))
      this.respondToReadyState(this.transport.readyState);
  },

  setRequestHeaders: function() {
    var headers = {
      'X-Requested-With': 'XMLHttpRequest',
      'X-Prototype-Version': Prototype.Version,
      'Accept': 'text/javascript, text/html, application/xml, text/xml, */*'
    };

    if (this.method == 'post') {
      headers['Content-type'] = this.options.contentType +
        (this.options.encoding ? '; charset=' + this.options.encoding : '');

      /* Force "Connection: close" for older Mozilla browsers to work
       * around a bug where XMLHttpRequest sends an incorrect
       * Content-length header. See Mozilla Bugzilla #246651.
       */
      if (this.transport.overrideMimeType &&
          (navigator.userAgent.match(/Gecko\/(\d{4})/) || [0,2005])[1] < 2005)
            headers['Connection'] = 'close';
    }

    if (typeof this.options.requestHeaders == 'object') {
      var extras = this.options.requestHeaders;

      if (Object.isFunction(extras.push))
        for (var i = 0, length = extras.length; i < length; i += 2)
          headers[extras[i]] = extras[i+1];
      else
        $H(extras).each(function(pair) { headers[pair.key] = pair.value });
    }

    for (var name in headers)
      this.transport.setRequestHeader(name, headers[name]);
  },

  success: function() {
    var status = this.getStatus();
    return !status || (status >= 200 && status < 300);
  },

  getStatus: function() {
    try {
      return this.transport.status || 0;
    } catch (e) { return 0 }
  },

  respondToReadyState: function(readyState) {
    var state = Ajax.Request.Events[readyState], response = new Ajax.Response(this);

    if (state == 'Complete') {
      try {
        this._complete = true;
        (this.options['on' + response.status]
         || this.options['on' + (this.success() ? 'Success' : 'Failure')]
         || Prototype.emptyFunction)(response, response.headerJSON);
      } catch (e) {
        this.dispatchException(e);
      }

      var contentType = response.getHeader('Content-type');
      if (this.options.evalJS == 'force'
          || (this.options.evalJS && this.isSameOrigin() && contentType
          && contentType.match(/^\s*(text|application)\/(x-)?(java|ecma)script(;.*)?\s*$/i)))
        this.evalResponse();
    }

    try {
      (this.options['on' + state] || Prototype.emptyFunction)(response, response.headerJSON);
      Ajax.Responders.dispatch('on' + state, this, response, response.headerJSON);
    } catch (e) {
      this.dispatchException(e);
    }

    if (state == 'Complete') {
      this.transport.onreadystatechange = Prototype.emptyFunction;
    }
  },

  isSameOrigin: function() {
    var m = this.url.match(/^\s*https?:\/\/[^\/]*/);
    return !m || (m[0] == '#{protocol}//#{domain}#{port}'.interpolate({
      protocol: location.protocol,
      domain: document.domain,
      port: location.port ? ':' + location.port : ''
    }));
  },

  getHeader: function(name) {
    try {
      return this.transport.getResponseHeader(name) || null;
    } catch (e) { return null; }
  },

  evalResponse: function() {
    try {
      return eval((this.transport.responseText || '').unfilterJSON());
    } catch (e) {
      this.dispatchException(e);
    }
  },

  dispatchException: function(exception) {
    (this.options.onException || Prototype.emptyFunction)(this, exception);
    Ajax.Responders.dispatch('onException', this, exception);
  }
});

Ajax.Request.Events =
  ['Uninitialized', 'Loading', 'Loaded', 'Interactive', 'Complete'];








Ajax.Response = Class.create({
  initialize: function(request){
    this.request = request;
    var transport  = this.transport  = request.transport,
        readyState = this.readyState = transport.readyState;

    if((readyState > 2 && !Prototype.Browser.IE) || readyState == 4) {
      this.status       = this.getStatus();
      this.statusText   = this.getStatusText();
      this.responseText = String.interpret(transport.responseText);
      this.headerJSON   = this._getHeaderJSON();
    }

    if(readyState == 4) {
      var xml = transport.responseXML;
      this.responseXML  = Object.isUndefined(xml) ? null : xml;
      this.responseJSON = this._getResponseJSON();
    }
  },

  status:      0,

  statusText: '',

  getStatus: Ajax.Request.prototype.getStatus,

  getStatusText: function() {
    try {
      return this.transport.statusText || '';
    } catch (e) { return '' }
  },

  getHeader: Ajax.Request.prototype.getHeader,

  getAllHeaders: function() {
    try {
      return this.getAllResponseHeaders();
    } catch (e) { return null }
  },

  getResponseHeader: function(name) {
    return this.transport.getResponseHeader(name);
  },

  getAllResponseHeaders: function() {
    return this.transport.getAllResponseHeaders();
  },

  _getHeaderJSON: function() {
    var json = this.getHeader('X-JSON');
    if (!json) return null;
    json = decodeURIComponent(escape(json));
    try {
      return json.evalJSON(this.request.options.sanitizeJSON ||
        !this.request.isSameOrigin());
    } catch (e) {
      this.request.dispatchException(e);
    }
  },

  _getResponseJSON: function() {
    var options = this.request.options;
    if (!options.evalJSON || (options.evalJSON != 'force' &&
      !(this.getHeader('Content-type') || '').include('application/json')) ||
        this.responseText.blank())
          return null;
    try {
      return this.responseText.evalJSON(options.sanitizeJSON ||
        !this.request.isSameOrigin());
    } catch (e) {
      this.request.dispatchException(e);
    }
  }
});

Ajax.Updater = Class.create(Ajax.Request, {
  initialize: function($super, container, url, options) {
    this.container = {
      success: (container.success || container),
      failure: (container.failure || (container.success ? null : container))
    };

    options = Object.clone(options);
    var onComplete = options.onComplete;
    options.onComplete = (function(response, json) {
      this.updateContent(response.responseText);
      if (Object.isFunction(onComplete)) onComplete(response, json);
    }).bind(this);

    $super(url, options);
  },

  updateContent: function(responseText) {
    var receiver = this.container[this.success() ? 'success' : 'failure'],
        options = this.options;

    if (!options.evalScripts) responseText = responseText.stripScripts();

    if (receiver = $(receiver)) {
      if (options.insertion) {
        if (Object.isString(options.insertion)) {
          var insertion = { }; insertion[options.insertion] = responseText;
          receiver.insert(insertion);
        }
        else options.insertion(receiver, responseText);
      }
      else receiver.update(responseText);
    }
  }
});

Ajax.PeriodicalUpdater = Class.create(Ajax.Base, {
  initialize: function($super, container, url, options) {
    $super(options);
    this.onComplete = this.options.onComplete;

    this.frequency = (this.options.frequency || 2);
    this.decay = (this.options.decay || 1);

    this.updater = { };
    this.container = container;
    this.url = url;

    this.start();
  },

  start: function() {
    this.options.onComplete = this.updateComplete.bind(this);
    this.onTimerEvent();
  },

  stop: function() {
    this.updater.options.onComplete = undefined;
    clearTimeout(this.timer);
    (this.onComplete || Prototype.emptyFunction).apply(this, arguments);
  },

  updateComplete: function(response) {
    if (this.options.decay) {
      this.decay = (response.responseText == this.lastText ?
        this.decay * this.options.decay : 1);

      this.lastText = response.responseText;
    }
    this.timer = this.onTimerEvent.bind(this).delay(this.decay * this.frequency);
  },

  onTimerEvent: function() {
    this.updater = new Ajax.Updater(this.container, this.url, this.options);
  }
});



function $(element) {
  if (arguments.length > 1) {
    for (var i = 0, elements = [], length = arguments.length; i < length; i++)
      elements.push($(arguments[i]));
    return elements;
  }
  if (Object.isString(element))
    element = document.getElementById(element);
  return Element.extend(element);
}

if (Prototype.BrowserFeatures.XPath) {
  document._getElementsByXPath = function(expression, parentElement) {
    var results = [];
    var query = document.evaluate(expression, $(parentElement) || document,
      null, XPathResult.ORDERED_NODE_SNAPSHOT_TYPE, null);
    for (var i = 0, length = query.snapshotLength; i < length; i++)
      results.push(Element.extend(query.snapshotItem(i)));
    return results;
  };
}

/*--------------------------------------------------------------------------*/

if (!window.Node) var Node = { };

if (!Node.ELEMENT_NODE) {
  Object.extend(Node, {
    ELEMENT_NODE: 1,
    ATTRIBUTE_NODE: 2,
    TEXT_NODE: 3,
    CDATA_SECTION_NODE: 4,
    ENTITY_REFERENCE_NODE: 5,
    ENTITY_NODE: 6,
    PROCESSING_INSTRUCTION_NODE: 7,
    COMMENT_NODE: 8,
    DOCUMENT_NODE: 9,
    DOCUMENT_TYPE_NODE: 10,
    DOCUMENT_FRAGMENT_NODE: 11,
    NOTATION_NODE: 12
  });
}


(function(global) {

  var SETATTRIBUTE_IGNORES_NAME = (function(){
    var elForm = document.createElement("form");
    var elInput = document.createElement("input");
    var root = document.documentElement;
    elInput.setAttribute("name", "test");
    elForm.appendChild(elInput);
    root.appendChild(elForm);
    var isBuggy = elForm.elements
      ? (typeof elForm.elements.test == "undefined")
      : null;
    root.removeChild(elForm);
    elForm = elInput = null;
    return isBuggy;
  })();

  var element = global.Element;
  global.Element = function(tagName, attributes) {
    attributes = attributes || { };
    tagName = tagName.toLowerCase();
    var cache = Element.cache;
    if (SETATTRIBUTE_IGNORES_NAME && attributes.name) {
      tagName = '<' + tagName + ' name="' + attributes.name + '">';
      delete attributes.name;
      return Element.writeAttribute(document.createElement(tagName), attributes);
    }
    if (!cache[tagName]) cache[tagName] = Element.extend(document.createElement(tagName));
    return Element.writeAttribute(cache[tagName].cloneNode(false), attributes);
  };
  Object.extend(global.Element, element || { });
  if (element) global.Element.prototype = element.prototype;
})(this);

Element.cache = { };
Element.idCounter = 1;

Element.Methods = {
  visible: function(element) {
    return $(element).style.display != 'none';
  },

  toggle: function(element) {
    element = $(element);
    Element[Element.visible(element) ? 'hide' : 'show'](element);
    return element;
  },


  hide: function(element) {
    element = $(element);
    element.style.display = 'none';
    return element;
  },

  show: function(element) {
    element = $(element);
    element.style.display = '';
    return element;
  },

  remove: function(element) {
    element = $(element);
    element.parentNode.removeChild(element);
    return element;
  },

  update: (function(){

    var SELECT_ELEMENT_INNERHTML_BUGGY = (function(){
      var el = document.createElement("select"),
          isBuggy = true;
      el.innerHTML = "<option value=\"test\">test</option>";
      if (el.options && el.options[0]) {
        isBuggy = el.options[0].nodeName.toUpperCase() !== "OPTION";
      }
      el = null;
      return isBuggy;
    })();

    var TABLE_ELEMENT_INNERHTML_BUGGY = (function(){
      try {
        var el = document.createElement("table");
        if (el && el.tBodies) {
          el.innerHTML = "<tbody><tr><td>test</td></tr></tbody>";
          var isBuggy = typeof el.tBodies[0] == "undefined";
          el = null;
          return isBuggy;
        }
      } catch (e) {
        return true;
      }
    })();

    var SCRIPT_ELEMENT_REJECTS_TEXTNODE_APPENDING = (function () {
      var s = document.createElement("script"),
          isBuggy = false;
      try {
        s.appendChild(document.createTextNode(""));
        isBuggy = !s.firstChild ||
          s.firstChild && s.firstChild.nodeType !== 3;
      } catch (e) {
        isBuggy = true;
      }
      s = null;
      return isBuggy;
    })();

    function update(element, content) {
      element = $(element);

      if (content && content.toElement)
        content = content.toElement();

      if (Object.isElement(content))
        return element.update().insert(content);

      content = Object.toHTML(content);

      var tagName = element.tagName.toUpperCase();

      if (tagName === 'SCRIPT' && SCRIPT_ELEMENT_REJECTS_TEXTNODE_APPENDING) {
        element.text = content;
        return element;
      }

      if (SELECT_ELEMENT_INNERHTML_BUGGY || TABLE_ELEMENT_INNERHTML_BUGGY) {
        if (tagName in Element._insertionTranslations.tags) {
          while (element.firstChild) {
            element.removeChild(element.firstChild);
          }
          Element._getContentFromAnonymousElement(tagName, content.stripScripts())
            .each(function(node) {
              element.appendChild(node)
            });
        }
        else {
          element.innerHTML = content.stripScripts();
        }
      }
      else {
        element.innerHTML = content.stripScripts();
      }

      content.evalScripts.bind(content).defer();
      return element;
    }

    return update;
  })(),

  replace: function(element, content) {
    element = $(element);
    if (content && content.toElement) content = content.toElement();
    else if (!Object.isElement(content)) {
      content = Object.toHTML(content);
      var range = element.ownerDocument.createRange();
      range.selectNode(element);
      content.evalScripts.bind(content).defer();
      content = range.createContextualFragment(content.stripScripts());
    }
    element.parentNode.replaceChild(content, element);
    return element;
  },

  insert: function(element, insertions) {
    element = $(element);

    if (Object.isString(insertions) || Object.isNumber(insertions) ||
        Object.isElement(insertions) || (insertions && (insertions.toElement || insertions.toHTML)))
          insertions = {bottom:insertions};

    var content, insert, tagName, childNodes;

    for (var position in insertions) {
      content  = insertions[position];
      position = position.toLowerCase();
      insert = Element._insertionTranslations[position];

      if (content && content.toElement) content = content.toElement();
      if (Object.isElement(content)) {
        insert(element, content);
        continue;
      }

      content = Object.toHTML(content);

      tagName = ((position == 'before' || position == 'after')
        ? element.parentNode : element).tagName.toUpperCase();

      childNodes = Element._getContentFromAnonymousElement(tagName, content.stripScripts());

      if (position == 'top' || position == 'after') childNodes.reverse();
      childNodes.each(insert.curry(element));

      content.evalScripts.bind(content).defer();
    }

    return element;
  },

  wrap: function(element, wrapper, attributes) {
    element = $(element);
    if (Object.isElement(wrapper))
      $(wrapper).writeAttribute(attributes || { });
    else if (Object.isString(wrapper)) wrapper = new Element(wrapper, attributes);
    else wrapper = new Element('div', wrapper);
    if (element.parentNode)
      element.parentNode.replaceChild(wrapper, element);
    wrapper.appendChild(element);
    return wrapper;
  },

  inspect: function(element) {
    element = $(element);
    var result = '<' + element.tagName.toLowerCase();
    $H({'id': 'id', 'className': 'class'}).each(function(pair) {
      var property = pair.first(), attribute = pair.last();
      var value = (element[property] || '').toString();
      if (value) result += ' ' + attribute + '=' + value.inspect(true);
    });
    return result + '>';
  },

  recursivelyCollect: function(element, property) {
    element = $(element);
    var elements = [];
    while (element = element[property])
      if (element.nodeType == 1)
        elements.push(Element.extend(element));
    return elements;
  },

  ancestors: function(element) {
    return Element.recursivelyCollect(element, 'parentNode');
  },

  descendants: function(element) {
    return Element.select(element, "*");
  },

  firstDescendant: function(element) {
    element = $(element).firstChild;
    while (element && element.nodeType != 1) element = element.nextSibling;
    return $(element);
  },

  immediateDescendants: function(element) {
    if (!(element = $(element).firstChild)) return [];
    while (element && element.nodeType != 1) element = element.nextSibling;
    if (element) return [element].concat($(element).nextSiblings());
    return [];
  },

  previousSiblings: function(element) {
    return Element.recursivelyCollect(element, 'previousSibling');
  },

  nextSiblings: function(element) {
    return Element.recursivelyCollect(element, 'nextSibling');
  },

  siblings: function(element) {
    element = $(element);
    return Element.previousSiblings(element).reverse()
      .concat(Element.nextSiblings(element));
  },

  match: function(element, selector) {
    if (Object.isString(selector))
      selector = new Selector(selector);
    return selector.match($(element));
  },

  up: function(element, expression, index) {
    element = $(element);
    if (arguments.length == 1) return $(element.parentNode);
    var ancestors = Element.ancestors(element);
    return Object.isNumber(expression) ? ancestors[expression] :
      Selector.findElement(ancestors, expression, index);
  },

  down: function(element, expression, index) {
    element = $(element);
    if (arguments.length == 1) return Element.firstDescendant(element);
    return Object.isNumber(expression) ? Element.descendants(element)[expression] :
      Element.select(element, expression)[index || 0];
  },

  previous: function(element, expression, index) {
    element = $(element);
    if (arguments.length == 1) return $(Selector.handlers.previousElementSibling(element));
    var previousSiblings = Element.previousSiblings(element);
    return Object.isNumber(expression) ? previousSiblings[expression] :
      Selector.findElement(previousSiblings, expression, index);
  },

  next: function(element, expression, index) {
    element = $(element);
    if (arguments.length == 1) return $(Selector.handlers.nextElementSibling(element));
    var nextSiblings = Element.nextSiblings(element);
    return Object.isNumber(expression) ? nextSiblings[expression] :
      Selector.findElement(nextSiblings, expression, index);
  },


  select: function(element) {
    var args = Array.prototype.slice.call(arguments, 1);
    return Selector.findChildElements(element, args);
  },

  adjacent: function(element) {
    var args = Array.prototype.slice.call(arguments, 1);
    return Selector.findChildElements(element.parentNode, args).without(element);
  },

  identify: function(element) {
    element = $(element);
    var id = Element.readAttribute(element, 'id');
    if (id) return id;
    do { id = 'anonymous_element_' + Element.idCounter++ } while ($(id));
    Element.writeAttribute(element, 'id', id);
    return id;
  },

  readAttribute: function(element, name) {
    element = $(element);
    if (Prototype.Browser.IE) {
      var t = Element._attributeTranslations.read;
      if (t.values[name]) return t.values[name](element, name);
      if (t.names[name]) name = t.names[name];
      if (name.include(':')) {
        return (!element.attributes || !element.attributes[name]) ? null :
         element.attributes[name].value;
      }
    }
    return element.getAttribute(name);
  },

  writeAttribute: function(element, name, value) {
    element = $(element);
    var attributes = { }, t = Element._attributeTranslations.write;

    if (typeof name == 'object') attributes = name;
    else attributes[name] = Object.isUndefined(value) ? true : value;

    for (var attr in attributes) {
      name = t.names[attr] || attr;
      value = attributes[attr];
      if (t.values[attr]) name = t.values[attr](element, value);
      if (value === false || value === null)
        element.removeAttribute(name);
      else if (value === true)
        element.setAttribute(name, name);
      else element.setAttribute(name, value);
    }
    return element;
  },

  getHeight: function(element) {
    return Element.getDimensions(element).height;
  },

  getWidth: function(element) {
    return Element.getDimensions(element).width;
  },

  classNames: function(element) {
    return new Element.ClassNames(element);
  },

  hasClassName: function(element, className) {
    if (!(element = $(element))) return;
    var elementClassName = element.className;
    return (elementClassName.length > 0 && (elementClassName == className ||
      new RegExp("(^|\\s)" + className + "(\\s|$)").test(elementClassName)));
  },

  addClassName: function(element, className) {
    if (!(element = $(element))) return;
    if (!Element.hasClassName(element, className))
      element.className += (element.className ? ' ' : '') + className;
    return element;
  },

  removeClassName: function(element, className) {
    if (!(element = $(element))) return;
    element.className = element.className.replace(
      new RegExp("(^|\\s+)" + className + "(\\s+|$)"), ' ').strip();
    return element;
  },

  toggleClassName: function(element, className) {
    if (!(element = $(element))) return;
    return Element[Element.hasClassName(element, className) ?
      'removeClassName' : 'addClassName'](element, className);
  },

  cleanWhitespace: function(element) {
    element = $(element);
    var node = element.firstChild;
    while (node) {
      var nextNode = node.nextSibling;
      if (node.nodeType == 3 && !/\S/.test(node.nodeValue))
        element.removeChild(node);
      node = nextNode;
    }
    return element;
  },

  empty: function(element) {
    return $(element).innerHTML.blank();
  },

  descendantOf: function(element, ancestor) {
    element = $(element), ancestor = $(ancestor);

    if (element.compareDocumentPosition)
      return (element.compareDocumentPosition(ancestor) & 8) === 8;

    if (ancestor.contains)
      return ancestor.contains(element) && ancestor !== element;

    while (element = element.parentNode)
      if (element == ancestor) return true;

    return false;
  },

  scrollTo: function(element) {
    element = $(element);
    var pos = Element.cumulativeOffset(element);
    window.scrollTo(pos[0], pos[1]);
    return element;
  },

  getStyle: function(element, style) {
    element = $(element);
    style = style == 'float' ? 'cssFloat' : style.camelize();
    var value = element.style[style];
    if (!value || value == 'auto') {
      var css = document.defaultView.getComputedStyle(element, null);
      value = css ? css[style] : null;
    }
    if (style == 'opacity') return value ? parseFloat(value) : 1.0;
    return value == 'auto' ? null : value;
  },

  getOpacity: function(element) {
    return $(element).getStyle('opacity');
  },

  setStyle: function(element, styles) {
    element = $(element);
    var elementStyle = element.style, match;
    if (Object.isString(styles)) {
      element.style.cssText += ';' + styles;
      return styles.include('opacity') ?
        element.setOpacity(styles.match(/opacity:\s*(\d?\.?\d*)/)[1]) : element;
    }
    for (var property in styles)
      if (property == 'opacity') element.setOpacity(styles[property]);
      else
        elementStyle[(property == 'float' || property == 'cssFloat') ?
          (Object.isUndefined(elementStyle.styleFloat) ? 'cssFloat' : 'styleFloat') :
            property] = styles[property];

    return element;
  },

  setOpacity: function(element, value) {
    element = $(element);
    element.style.opacity = (value == 1 || value === '') ? '' :
      (value < 0.00001) ? 0 : value;
    return element;
  },

  getDimensions: function(element) {
    element = $(element);
    var display = Element.getStyle(element, 'display');
    if (display != 'none' && display != null) // Safari bug
      return {width: element.offsetWidth, height: element.offsetHeight};

    var els = element.style;
    var originalVisibility = els.visibility;
    var originalPosition = els.position;
    var originalDisplay = els.display;
    els.visibility = 'hidden';
    if (originalPosition != 'fixed') // Switching fixed to absolute causes issues in Safari
      els.position = 'absolute';
    els.display = 'block';
    var originalWidth = element.clientWidth;
    var originalHeight = element.clientHeight;
    els.display = originalDisplay;
    els.position = originalPosition;
    els.visibility = originalVisibility;
    return {width: originalWidth, height: originalHeight};
  },

  makePositioned: function(element) {
    element = $(element);
    var pos = Element.getStyle(element, 'position');
    if (pos == 'static' || !pos) {
      element._madePositioned = true;
      element.style.position = 'relative';
      if (Prototype.Browser.Opera) {
        element.style.top = 0;
        element.style.left = 0;
      }
    }
    return element;
  },

  undoPositioned: function(element) {
    element = $(element);
    if (element._madePositioned) {
      element._madePositioned = undefined;
      element.style.position =
        element.style.top =
        element.style.left =
        element.style.bottom =
        element.style.right = '';
    }
    return element;
  },

  makeClipping: function(element) {
    element = $(element);
    if (element._overflow) return element;
    element._overflow = Element.getStyle(element, 'overflow') || 'auto';
    if (element._overflow !== 'hidden')
      element.style.overflow = 'hidden';
    return element;
  },

  undoClipping: function(element) {
    element = $(element);
    if (!element._overflow) return element;
    element.style.overflow = element._overflow == 'auto' ? '' : element._overflow;
    element._overflow = null;
    return element;
  },

  cumulativeOffset: function(element) {
    var valueT = 0, valueL = 0;
    do {
      valueT += element.offsetTop  || 0;
      valueL += element.offsetLeft || 0;
      element = element.offsetParent;
    } while (element);
    return Element._returnOffset(valueL, valueT);
  },

  positionedOffset: function(element) {
    var valueT = 0, valueL = 0;
    do {
      valueT += element.offsetTop  || 0;
      valueL += element.offsetLeft || 0;
      element = element.offsetParent;
      if (element) {
        if (element.tagName.toUpperCase() == 'BODY') break;
        var p = Element.getStyle(element, 'position');
        if (p !== 'static') break;
      }
    } while (element);
    return Element._returnOffset(valueL, valueT);
  },

  absolutize: function(element) {
    element = $(element);
    if (Element.getStyle(element, 'position') == 'absolute') return element;

    var offsets = Element.positionedOffset(element);
    var top     = offsets[1];
    var left    = offsets[0];
    var width   = element.clientWidth;
    var height  = element.clientHeight;

    element._originalLeft   = left - parseFloat(element.style.left  || 0);
    element._originalTop    = top  - parseFloat(element.style.top || 0);
    element._originalWidth  = element.style.width;
    element._originalHeight = element.style.height;

    element.style.position = 'absolute';
    element.style.top    = top + 'px';
    element.style.left   = left + 'px';
    element.style.width  = width + 'px';
    element.style.height = height + 'px';
    return element;
  },

  relativize: function(element) {
    element = $(element);
    if (Element.getStyle(element, 'position') == 'relative') return element;

    element.style.position = 'relative';
    var top  = parseFloat(element.style.top  || 0) - (element._originalTop || 0);
    var left = parseFloat(element.style.left || 0) - (element._originalLeft || 0);

    element.style.top    = top + 'px';
    element.style.left   = left + 'px';
    element.style.height = element._originalHeight;
    element.style.width  = element._originalWidth;
    return element;
  },

  cumulativeScrollOffset: function(element) {
    var valueT = 0, valueL = 0;
    do {
      valueT += element.scrollTop  || 0;
      valueL += element.scrollLeft || 0;
      element = element.parentNode;
    } while (element);
    return Element._returnOffset(valueL, valueT);
  },

  getOffsetParent: function(element) {
    if (element.offsetParent) return $(element.offsetParent);
    if (element == document.body) return $(element);

    while ((element = element.parentNode) && element != document.body)
      if (Element.getStyle(element, 'position') != 'static')
        return $(element);

    return $(document.body);
  },

  viewportOffset: function(forElement) {
    var valueT = 0, valueL = 0;

    var element = forElement;
    do {
      valueT += element.offsetTop  || 0;
      valueL += element.offsetLeft || 0;

      if (element.offsetParent == document.body &&
        Element.getStyle(element, 'position') == 'absolute') break;

    } while (element = element.offsetParent);

    element = forElement;
    do {
      if (!Prototype.Browser.Opera || (element.tagName && (element.tagName.toUpperCase() == 'BODY'))) {
        valueT -= element.scrollTop  || 0;
        valueL -= element.scrollLeft || 0;
      }
    } while (element = element.parentNode);

    return Element._returnOffset(valueL, valueT);
  },

  clonePosition: function(element, source) {
    var options = Object.extend({
      setLeft:    true,
      setTop:     true,
      setWidth:   true,
      setHeight:  true,
      offsetTop:  0,
      offsetLeft: 0
    }, arguments[2] || { });

    source = $(source);
    var p = Element.viewportOffset(source);

    element = $(element);
    var delta = [0, 0];
    var parent = null;
    if (Element.getStyle(element, 'position') == 'absolute') {
      parent = Element.getOffsetParent(element);
      delta = Element.viewportOffset(parent);
    }

    if (parent == document.body) {
      delta[0] -= document.body.offsetLeft;
      delta[1] -= document.body.offsetTop;
    }

    if (options.setLeft)   element.style.left  = (p[0] - delta[0] + options.offsetLeft) + 'px';
    if (options.setTop)    element.style.top   = (p[1] - delta[1] + options.offsetTop) + 'px';
    if (options.setWidth)  element.style.width = source.offsetWidth + 'px';
    if (options.setHeight) element.style.height = source.offsetHeight + 'px';
    return element;
  }
};

Object.extend(Element.Methods, {
  getElementsBySelector: Element.Methods.select,

  childElements: Element.Methods.immediateDescendants
});

Element._attributeTranslations = {
  write: {
    names: {
      className: 'class',
      htmlFor:   'for'
    },
    values: { }
  }
};

if (Prototype.Browser.Opera) {
  Element.Methods.getStyle = Element.Methods.getStyle.wrap(
    function(proceed, element, style) {
      switch (style) {
        case 'left': case 'top': case 'right': case 'bottom':
          if (proceed(element, 'position') === 'static') return null;
        case 'height': case 'width':
          if (!Element.visible(element)) return null;

          var dim = parseInt(proceed(element, style), 10);

          if (dim !== element['offset' + style.capitalize()])
            return dim + 'px';

          var properties;
          if (style === 'height') {
            properties = ['border-top-width', 'padding-top',
             'padding-bottom', 'border-bottom-width'];
          }
          else {
            properties = ['border-left-width', 'padding-left',
             'padding-right', 'border-right-width'];
          }
          return properties.inject(dim, function(memo, property) {
            var val = proceed(element, property);
            return val === null ? memo : memo - parseInt(val, 10);
          }) + 'px';
        default: return proceed(element, style);
      }
    }
  );

  Element.Methods.readAttribute = Element.Methods.readAttribute.wrap(
    function(proceed, element, attribute) {
      if (attribute === 'title') return element.title;
      return proceed(element, attribute);
    }
  );
}

else if (Prototype.Browser.IE) {
  Element.Methods.getOffsetParent = Element.Methods.getOffsetParent.wrap(
    function(proceed, element) {
      element = $(element);
      try { element.offsetParent }
      catch(e) { return $(document.body) }
      var position = element.getStyle('position');
      if (position !== 'static') return proceed(element);
      element.setStyle({ position: 'relative' });
      var value = proceed(element);
      element.setStyle({ position: position });
      return value;
    }
  );

  $w('positionedOffset viewportOffset').each(function(method) {
    Element.Methods[method] = Element.Methods[method].wrap(
      function(proceed, element) {
        element = $(element);
        try { element.offsetParent }
        catch(e) { return Element._returnOffset(0,0) }
        var position = element.getStyle('position');
        if (position !== 'static') return proceed(element);
        var offsetParent = element.getOffsetParent();
        if (offsetParent && offsetParent.getStyle('position') === 'fixed')
          offsetParent.setStyle({ zoom: 1 });
        element.setStyle({ position: 'relative' });
        var value = proceed(element);
        element.setStyle({ position: position });
        return value;
      }
    );
  });

  Element.Methods.cumulativeOffset = Element.Methods.cumulativeOffset.wrap(
    function(proceed, element) {
      try { element.offsetParent }
      catch(e) { return Element._returnOffset(0,0) }
      return proceed(element);
    }
  );

  Element.Methods.getStyle = function(element, style) {
    element = $(element);
    style = (style == 'float' || style == 'cssFloat') ? 'styleFloat' : style.camelize();
    var value = element.style[style];
    if (!value && element.currentStyle) value = element.currentStyle[style];

    if (style == 'opacity') {
      if (value = (element.getStyle('filter') || '').match(/alpha\(opacity=(.*)\)/))
        if (value[1]) return parseFloat(value[1]) / 100;
      return 1.0;
    }

    if (value == 'auto') {
      if ((style == 'width' || style == 'height') && (element.getStyle('display') != 'none'))
        return element['offset' + style.capitalize()] + 'px';
      return null;
    }
    return value;
  };

  Element.Methods.setOpacity = function(element, value) {
    function stripAlpha(filter){
      return filter.replace(/alpha\([^\)]*\)/gi,'');
    }
    element = $(element);
    var currentStyle = element.currentStyle;
    if ((currentStyle && !currentStyle.hasLayout) ||
      (!currentStyle && element.style.zoom == 'normal'))
        element.style.zoom = 1;

    var filter = element.getStyle('filter'), style = element.style;
    if (value == 1 || value === '') {
      (filter = stripAlpha(filter)) ?
        style.filter = filter : style.removeAttribute('filter');
      return element;
    } else if (value < 0.00001) value = 0;
    style.filter = stripAlpha(filter) +
      'alpha(opacity=' + (value * 100) + ')';
    return element;
  };

  Element._attributeTranslations = (function(){

    var classProp = 'className';
    var forProp = 'for';

    var el = document.createElement('div');

    el.setAttribute(classProp, 'x');

    if (el.className !== 'x') {
      el.setAttribute('class', 'x');
      if (el.className === 'x') {
        classProp = 'class';
      }
    }
    el = null;

    el = document.createElement('label');
    el.setAttribute(forProp, 'x');
    if (el.htmlFor !== 'x') {
      el.setAttribute('htmlFor', 'x');
      if (el.htmlFor === 'x') {
        forProp = 'htmlFor';
      }
    }
    el = null;

    return {
      read: {
        names: {
          'class':      classProp,
          'className':  classProp,
          'for':        forProp,
          'htmlFor':    forProp
        },
        values: {
          _getAttr: function(element, attribute) {
            return element.getAttribute(attribute);
          },
          _getAttr2: function(element, attribute) {
            return element.getAttribute(attribute, 2);
          },
          _getAttrNode: function(element, attribute) {
            var node = element.getAttributeNode(attribute);
            return node ? node.value : "";
          },
          _getEv: (function(){

            var el = document.createElement('div');
            el.onclick = Prototype.emptyFunction;
            var value = el.getAttribute('onclick');
            var f;

            if (String(value).indexOf('{') > -1) {
              f = function(element, attribute) {
                attribute = element.getAttribute(attribute);
                if (!attribute) return null;
                attribute = attribute.toString();
                attribute = attribute.split('{')[1];
                attribute = attribute.split('}')[0];
                return attribute.strip();
              };
            }
            else if (value === '') {
              f = function(element, attribute) {
                attribute = element.getAttribute(attribute);
                if (!attribute) return null;
                return attribute.strip();
              };
            }
            el = null;
            return f;
          })(),
          _flag: function(element, attribute) {
            return $(element).hasAttribute(attribute) ? attribute : null;
          },
          style: function(element) {
            return element.style.cssText.toLowerCase();
          },
          title: function(element) {
            return element.title;
          }
        }
      }
    }
  })();

  Element._attributeTranslations.write = {
    names: Object.extend({
      cellpadding: 'cellPadding',
      cellspacing: 'cellSpacing'
    }, Element._attributeTranslations.read.names),
    values: {
      checked: function(element, value) {
        element.checked = !!value;
      },

      style: function(element, value) {
        element.style.cssText = value ? value : '';
      }
    }
  };

  Element._attributeTranslations.has = {};

  $w('colSpan rowSpan vAlign dateTime accessKey tabIndex ' +
      'encType maxLength readOnly longDesc frameBorder').each(function(attr) {
    Element._attributeTranslations.write.names[attr.toLowerCase()] = attr;
    Element._attributeTranslations.has[attr.toLowerCase()] = attr;
  });

  (function(v) {
    Object.extend(v, {
      href:        v._getAttr2,
      src:         v._getAttr2,
      type:        v._getAttr,
      action:      v._getAttrNode,
      disabled:    v._flag,
      checked:     v._flag,
      readonly:    v._flag,
      multiple:    v._flag,
      onload:      v._getEv,
      onunload:    v._getEv,
      onclick:     v._getEv,
      ondblclick:  v._getEv,
      onmousedown: v._getEv,
      onmouseup:   v._getEv,
      onmouseover: v._getEv,
      onmousemove: v._getEv,
      onmouseout:  v._getEv,
      onfocus:     v._getEv,
      onblur:      v._getEv,
      onkeypress:  v._getEv,
      onkeydown:   v._getEv,
      onkeyup:     v._getEv,
      onsubmit:    v._getEv,
      onreset:     v._getEv,
      onselect:    v._getEv,
      onchange:    v._getEv
    });
  })(Element._attributeTranslations.read.values);

  if (Prototype.BrowserFeatures.ElementExtensions) {
    (function() {
      function _descendants(element) {
        var nodes = element.getElementsByTagName('*'), results = [];
        for (var i = 0, node; node = nodes[i]; i++)
          if (node.tagName !== "!") // Filter out comment nodes.
            results.push(node);
        return results;
      }

      Element.Methods.down = function(element, expression, index) {
        element = $(element);
        if (arguments.length == 1) return element.firstDescendant();
        return Object.isNumber(expression) ? _descendants(element)[expression] :
          Element.select(element, expression)[index || 0];
      }
    })();
  }

}

else if (Prototype.Browser.Gecko && /rv:1\.8\.0/.test(navigator.userAgent)) {
  Element.Methods.setOpacity = function(element, value) {
    element = $(element);
    element.style.opacity = (value == 1) ? 0.999999 :
      (value === '') ? '' : (value < 0.00001) ? 0 : value;
    return element;
  };
}

else if (Prototype.Browser.WebKit) {
  Element.Methods.setOpacity = function(element, value) {
    element = $(element);
    element.style.opacity = (value == 1 || value === '') ? '' :
      (value < 0.00001) ? 0 : value;

    if (value == 1)
      if(element.tagName.toUpperCase() == 'IMG' && element.width) {
        element.width++; element.width--;
      } else try {
        var n = document.createTextNode(' ');
        element.appendChild(n);
        element.removeChild(n);
      } catch (e) { }

    return element;
  };

  Element.Methods.cumulativeOffset = function(element) {
    var valueT = 0, valueL = 0;
    do {
      valueT += element.offsetTop  || 0;
      valueL += element.offsetLeft || 0;
      if (element.offsetParent == document.body)
        if (Element.getStyle(element, 'position') == 'absolute') break;

      element = element.offsetParent;
    } while (element);

    return Element._returnOffset(valueL, valueT);
  };
}

if ('outerHTML' in document.documentElement) {
  Element.Methods.replace = function(element, content) {
    element = $(element);

    if (content && content.toElement) content = content.toElement();
    if (Object.isElement(content)) {
      element.parentNode.replaceChild(content, element);
      return element;
    }

    content = Object.toHTML(content);
    var parent = element.parentNode, tagName = parent.tagName.toUpperCase();

    if (Element._insertionTranslations.tags[tagName]) {
      var nextSibling = element.next();
      var fragments = Element._getContentFromAnonymousElement(tagName, content.stripScripts());
      parent.removeChild(element);
      if (nextSibling)
        fragments.each(function(node) { parent.insertBefore(node, nextSibling) });
      else
        fragments.each(function(node) { parent.appendChild(node) });
    }
    else element.outerHTML = content.stripScripts();

    content.evalScripts.bind(content).defer();
    return element;
  };
}

Element._returnOffset = function(l, t) {
  var result = [l, t];
  result.left = l;
  result.top = t;
  return result;
};

Element._getContentFromAnonymousElement = function(tagName, html) {
  var div = new Element('div'), t = Element._insertionTranslations.tags[tagName];
  if (t) {
    div.innerHTML = t[0] + html + t[1];
    t[2].times(function() { div = div.firstChild });
  } else div.innerHTML = html;
  return $A(div.childNodes);
};

Element._insertionTranslations = {
  before: function(element, node) {
    element.parentNode.insertBefore(node, element);
  },
  top: function(element, node) {
    element.insertBefore(node, element.firstChild);
  },
  bottom: function(element, node) {
    element.appendChild(node);
  },
  after: function(element, node) {
    element.parentNode.insertBefore(node, element.nextSibling);
  },
  tags: {
    TABLE:  ['<table>',                '</table>',                   1],
    TBODY:  ['<table><tbody>',         '</tbody></table>',           2],
    TR:     ['<table><tbody><tr>',     '</tr></tbody></table>',      3],
    TD:     ['<table><tbody><tr><td>', '</td></tr></tbody></table>', 4],
    SELECT: ['<select>',               '</select>',                  1]
  }
};

(function() {
  var tags = Element._insertionTranslations.tags;
  Object.extend(tags, {
    THEAD: tags.TBODY,
    TFOOT: tags.TBODY,
    TH:    tags.TD
  });
})();

Element.Methods.Simulated = {
  hasAttribute: function(element, attribute) {
    attribute = Element._attributeTranslations.has[attribute] || attribute;
    var node = $(element).getAttributeNode(attribute);
    return !!(node && node.specified);
  }
};

Element.Methods.ByTag = { };

Object.extend(Element, Element.Methods);

(function(div) {

  if (!Prototype.BrowserFeatures.ElementExtensions && div['__proto__']) {
    window.HTMLElement = { };
    window.HTMLElement.prototype = div['__proto__'];
    Prototype.BrowserFeatures.ElementExtensions = true;
  }

  div = null;

})(document.createElement('div'))

Element.extend = (function() {

  function checkDeficiency(tagName) {
    if (typeof window.Element != 'undefined') {
      var proto = window.Element.prototype;
      if (proto) {
        var id = '_' + (Math.random()+'').slice(2);
        var el = document.createElement(tagName);
        proto[id] = 'x';
        var isBuggy = (el[id] !== 'x');
        delete proto[id];
        el = null;
        return isBuggy;
      }
    }
    return false;
  }

  function extendElementWith(element, methods) {
    for (var property in methods) {
      var value = methods[property];
      if (Object.isFunction(value) && !(property in element))
        element[property] = value.methodize();
    }
  }

  var HTMLOBJECTELEMENT_PROTOTYPE_BUGGY = checkDeficiency('object');

  if (Prototype.BrowserFeatures.SpecificElementExtensions) {
    if (HTMLOBJECTELEMENT_PROTOTYPE_BUGGY) {
      return function(element) {
        if (element && typeof element._extendedByPrototype == 'undefined') {
          var t = element.tagName;
          if (t && (/^(?:object|applet|embed)$/i.test(t))) {
            extendElementWith(element, Element.Methods);
            extendElementWith(element, Element.Methods.Simulated);
            extendElementWith(element, Element.Methods.ByTag[t.toUpperCase()]);
          }
        }
        return element;
      }
    }
    return Prototype.K;
  }

  var Methods = { }, ByTag = Element.Methods.ByTag;

  var extend = Object.extend(function(element) {
    if (!element || typeof element._extendedByPrototype != 'undefined' ||
        element.nodeType != 1 || element == window) return element;

    var methods = Object.clone(Methods),
        tagName = element.tagName.toUpperCase();

    if (ByTag[tagName]) Object.extend(methods, ByTag[tagName]);

    extendElementWith(element, methods);

    element._extendedByPrototype = Prototype.emptyFunction;
    return element;

  }, {
    refresh: function() {
      if (!Prototype.BrowserFeatures.ElementExtensions) {
        Object.extend(Methods, Element.Methods);
        Object.extend(Methods, Element.Methods.Simulated);
      }
    }
  });

  extend.refresh();
  return extend;
})();

Element.hasAttribute = function(element, attribute) {
  if (element.hasAttribute) return element.hasAttribute(attribute);
  return Element.Methods.Simulated.hasAttribute(element, attribute);
};

Element.addMethods = function(methods) {
  var F = Prototype.BrowserFeatures, T = Element.Methods.ByTag;

  if (!methods) {
    Object.extend(Form, Form.Methods);
    Object.extend(Form.Element, Form.Element.Methods);
    Object.extend(Element.Methods.ByTag, {
      "FORM":     Object.clone(Form.Methods),
      "INPUT":    Object.clone(Form.Element.Methods),
      "SELECT":   Object.clone(Form.Element.Methods),
      "TEXTAREA": Object.clone(Form.Element.Methods)
    });
  }

  if (arguments.length == 2) {
    var tagName = methods;
    methods = arguments[1];
  }

  if (!tagName) Object.extend(Element.Methods, methods || { });
  else {
    if (Object.isArray(tagName)) tagName.each(extend);
    else extend(tagName);
  }

  function extend(tagName) {
    tagName = tagName.toUpperCase();
    if (!Element.Methods.ByTag[tagName])
      Element.Methods.ByTag[tagName] = { };
    Object.extend(Element.Methods.ByTag[tagName], methods);
  }

  function copy(methods, destination, onlyIfAbsent) {
    onlyIfAbsent = onlyIfAbsent || false;
    for (var property in methods) {
      var value = methods[property];
      if (!Object.isFunction(value)) continue;
      if (!onlyIfAbsent || !(property in destination))
        destination[property] = value.methodize();
    }
  }

  function findDOMClass(tagName) {
    var klass;
    var trans = {
      "OPTGROUP": "OptGroup", "TEXTAREA": "TextArea", "P": "Paragraph",
      "FIELDSET": "FieldSet", "UL": "UList", "OL": "OList", "DL": "DList",
      "DIR": "Directory", "H1": "Heading", "H2": "Heading", "H3": "Heading",
      "H4": "Heading", "H5": "Heading", "H6": "Heading", "Q": "Quote",
      "INS": "Mod", "DEL": "Mod", "A": "Anchor", "IMG": "Image", "CAPTION":
      "TableCaption", "COL": "TableCol", "COLGROUP": "TableCol", "THEAD":
      "TableSection", "TFOOT": "TableSection", "TBODY": "TableSection", "TR":
      "TableRow", "TH": "TableCell", "TD": "TableCell", "FRAMESET":
      "FrameSet", "IFRAME": "IFrame"
    };
    if (trans[tagName]) klass = 'HTML' + trans[tagName] + 'Element';
    if (window[klass]) return window[klass];
    klass = 'HTML' + tagName + 'Element';
    if (window[klass]) return window[klass];
    klass = 'HTML' + tagName.capitalize() + 'Element';
    if (window[klass]) return window[klass];

    var element = document.createElement(tagName);
    var proto = element['__proto__'] || element.constructor.prototype;
    element = null;
    return proto;
  }

  var elementPrototype = window.HTMLElement ? HTMLElement.prototype :
   Element.prototype;

  if (F.ElementExtensions) {
    copy(Element.Methods, elementPrototype);
    copy(Element.Methods.Simulated, elementPrototype, true);
  }

  if (F.SpecificElementExtensions) {
    for (var tag in Element.Methods.ByTag) {
      var klass = findDOMClass(tag);
      if (Object.isUndefined(klass)) continue;
      copy(T[tag], klass.prototype);
    }
  }

  Object.extend(Element, Element.Methods);
  delete Element.ByTag;

  if (Element.extend.refresh) Element.extend.refresh();
  Element.cache = { };
};


document.viewport = {

  getDimensions: function() {
    return { width: this.getWidth(), height: this.getHeight() };
  },

  getScrollOffsets: function() {
    return Element._returnOffset(
      window.pageXOffset || document.documentElement.scrollLeft || document.body.scrollLeft,
      window.pageYOffset || document.documentElement.scrollTop  || document.body.scrollTop);
  }
};

(function(viewport) {
  var B = Prototype.Browser, doc = document, element, property = {};

  function getRootElement() {
    if (B.WebKit && !doc.evaluate)
      return document;

    if (B.Opera && window.parseFloat(window.opera.version()) < 9.5)
      return document.body;

    return document.documentElement;
  }

  function define(D) {
    if (!element) element = getRootElement();

    property[D] = 'client' + D;

    viewport['get' + D] = function() { return element[property[D]] };
    return viewport['get' + D]();
  }

  viewport.getWidth  = define.curry('Width');

  viewport.getHeight = define.curry('Height');
})(document.viewport);


Element.Storage = {
  UID: 1
};

Element.addMethods({
  getStorage: function(element) {
    if (!(element = $(element))) return;

    var uid;
    if (element === window) {
      uid = 0;
    } else {
      if (typeof element._prototypeUID === "undefined")
        element._prototypeUID = [Element.Storage.UID++];
      uid = element._prototypeUID[0];
    }

    if (!Element.Storage[uid])
      Element.Storage[uid] = $H();

    return Element.Storage[uid];
  },

  store: function(element, key, value) {
    if (!(element = $(element))) return;

    if (arguments.length === 2) {
      Element.getStorage(element).update(key);
    } else {
      Element.getStorage(element).set(key, value);
    }

    return element;
  },

  retrieve: function(element, key, defaultValue) {
    if (!(element = $(element))) return;
    var hash = Element.getStorage(element), value = hash.get(key);

    if (Object.isUndefined(value)) {
      hash.set(key, defaultValue);
      value = defaultValue;
    }

    return value;
  },

  clone: function(element, deep) {
    if (!(element = $(element))) return;
    var clone = element.cloneNode(deep);
    clone._prototypeUID = void 0;
    if (deep) {
      var descendants = Element.select(clone, '*'),
          i = descendants.length;
      while (i--) {
        descendants[i]._prototypeUID = void 0;
      }
    }
    return Element.extend(clone);
  }
});
/* Portions of the Selector class are derived from Jack Slocum's DomQuery,
 * part of YUI-Ext version 0.40, distributed under the terms of an MIT-style
 * license.  Please see http://www.yui-ext.com/ for more information. */

var Selector = Class.create({
  initialize: function(expression) {
    this.expression = expression.strip();

    if (this.shouldUseSelectorsAPI()) {
      this.mode = 'selectorsAPI';
    } else if (this.shouldUseXPath()) {
      this.mode = 'xpath';
      this.compileXPathMatcher();
    } else {
      this.mode = "normal";
      this.compileMatcher();
    }

  },

  shouldUseXPath: (function() {

    var IS_DESCENDANT_SELECTOR_BUGGY = (function(){
      var isBuggy = false;
      if (document.evaluate && window.XPathResult) {
        var el = document.createElement('div');
        el.innerHTML = '<ul><li></li></ul><div><ul><li></li></ul></div>';

        var xpath = ".//*[local-name()='ul' or local-name()='UL']" +
          "//*[local-name()='li' or local-name()='LI']";

        var result = document.evaluate(xpath, el, null,
          XPathResult.ORDERED_NODE_SNAPSHOT_TYPE, null);

        isBuggy = (result.snapshotLength !== 2);
        el = null;
      }
      return isBuggy;
    })();

    return function() {
      if (!Prototype.BrowserFeatures.XPath) return false;

      var e = this.expression;

      if (Prototype.Browser.WebKit &&
       (e.include("-of-type") || e.include(":empty")))
        return false;

      if ((/(\[[\w-]*?:|:checked)/).test(e))
        return false;

      if (IS_DESCENDANT_SELECTOR_BUGGY) return false;

      return true;
    }

  })(),

  shouldUseSelectorsAPI: function() {
    if (!Prototype.BrowserFeatures.SelectorsAPI) return false;

    if (Selector.CASE_INSENSITIVE_CLASS_NAMES) return false;

    if (!Selector._div) Selector._div = new Element('div');

    try {
      Selector._div.querySelector(this.expression);
    } catch(e) {
      return false;
    }

    return true;
  },

  compileMatcher: function() {
    var e = this.expression, ps = Selector.patterns, h = Selector.handlers,
        c = Selector.criteria, le, p, m, len = ps.length, name;

    if (Selector._cache[e]) {
      this.matcher = Selector._cache[e];
      return;
    }

    this.matcher = ["this.matcher = function(root) {",
                    "var r = root, h = Selector.handlers, c = false, n;"];

    while (e && le != e && (/\S/).test(e)) {
      le = e;
      for (var i = 0; i<len; i++) {
        p = ps[i].re;
        name = ps[i].name;
        if (m = e.match(p)) {
          this.matcher.push(Object.isFunction(c[name]) ? c[name](m) :
            new Template(c[name]).evaluate(m));
          e = e.replace(m[0], '');
          break;
        }
      }
    }

    this.matcher.push("return h.unique(n);\n}");
    eval(this.matcher.join('\n'));
    Selector._cache[this.expression] = this.matcher;
  },

  compileXPathMatcher: function() {
    var e = this.expression, ps = Selector.patterns,
        x = Selector.xpath, le, m, len = ps.length, name;

    if (Selector._cache[e]) {
      this.xpath = Selector._cache[e]; return;
    }

    this.matcher = ['.//*'];
    while (e && le != e && (/\S/).test(e)) {
      le = e;
      for (var i = 0; i<len; i++) {
        name = ps[i].name;
        if (m = e.match(ps[i].re)) {
          this.matcher.push(Object.isFunction(x[name]) ? x[name](m) :
            new Template(x[name]).evaluate(m));
          e = e.replace(m[0], '');
          break;
        }
      }
    }

    this.xpath = this.matcher.join('');
    Selector._cache[this.expression] = this.xpath;
  },

  findElements: function(root) {
    root = root || document;
    var e = this.expression, results;

    switch (this.mode) {
      case 'selectorsAPI':
        if (root !== document) {
          var oldId = root.id, id = $(root).identify();
          id = id.replace(/([\.:])/g, "\\$1");
          e = "#" + id + " " + e;
        }

        results = $A(root.querySelectorAll(e)).map(Element.extend);
        root.id = oldId;

        return results;
      case 'xpath':
        return document._getElementsByXPath(this.xpath, root);
      default:
       return this.matcher(root);
    }
  },

  match: function(element) {
    this.tokens = [];

    var e = this.expression, ps = Selector.patterns, as = Selector.assertions;
    var le, p, m, len = ps.length, name;

    while (e && le !== e && (/\S/).test(e)) {
      le = e;
      for (var i = 0; i<len; i++) {
        p = ps[i].re;
        name = ps[i].name;
        if (m = e.match(p)) {
          if (as[name]) {
            this.tokens.push([name, Object.clone(m)]);
            e = e.replace(m[0], '');
          } else {
            return this.findElements(document).include(element);
          }
        }
      }
    }

    var match = true, name, matches;
    for (var i = 0, token; token = this.tokens[i]; i++) {
      name = token[0], matches = token[1];
      if (!Selector.assertions[name](element, matches)) {
        match = false; break;
      }
    }

    return match;
  },

  toString: function() {
    return this.expression;
  },

  inspect: function() {
    return "#<Selector:" + this.expression.inspect() + ">";
  }
});

if (Prototype.BrowserFeatures.SelectorsAPI &&
 document.compatMode === 'BackCompat') {
  Selector.CASE_INSENSITIVE_CLASS_NAMES = (function(){
    var div = document.createElement('div'),
     span = document.createElement('span');

    div.id = "prototype_test_id";
    span.className = 'Test';
    div.appendChild(span);
    var isIgnored = (div.querySelector('#prototype_test_id .test') !== null);
    div = span = null;
    return isIgnored;
  })();
}

Object.extend(Selector, {
  _cache: { },

  xpath: {
    descendant:   "//*",
    child:        "/*",
    adjacent:     "/following-sibling::*[1]",
    laterSibling: '/following-sibling::*',
    tagName:      function(m) {
      if (m[1] == '*') return '';
      return "[local-name()='" + m[1].toLowerCase() +
             "' or local-name()='" + m[1].toUpperCase() + "']";
    },
    className:    "[contains(concat(' ', @class, ' '), ' #{1} ')]",
    id:           "[@id='#{1}']",
    attrPresence: function(m) {
      m[1] = m[1].toLowerCase();
      return new Template("[@#{1}]").evaluate(m);
    },
    attr: function(m) {
      m[1] = m[1].toLowerCase();
      m[3] = m[5] || m[6];
      return new Template(Selector.xpath.operators[m[2]]).evaluate(m);
    },
    pseudo: function(m) {
      var h = Selector.xpath.pseudos[m[1]];
      if (!h) return '';
      if (Object.isFunction(h)) return h(m);
      return new Template(Selector.xpath.pseudos[m[1]]).evaluate(m);
    },
    operators: {
      '=':  "[@#{1}='#{3}']",
      '!=': "[@#{1}!='#{3}']",
      '^=': "[starts-with(@#{1}, '#{3}')]",
      '$=': "[substring(@#{1}, (string-length(@#{1}) - string-length('#{3}') + 1))='#{3}']",
      '*=': "[contains(@#{1}, '#{3}')]",
      '~=': "[contains(concat(' ', @#{1}, ' '), ' #{3} ')]",
      '|=': "[contains(concat('-', @#{1}, '-'), '-#{3}-')]"
    },
    pseudos: {
      'first-child': '[not(preceding-sibling::*)]',
      'last-child':  '[not(following-sibling::*)]',
      'only-child':  '[not(preceding-sibling::* or following-sibling::*)]',
      'empty':       "[count(*) = 0 and (count(text()) = 0)]",
      'checked':     "[@checked]",
      'disabled':    "[(@disabled) and (@type!='hidden')]",
      'enabled':     "[not(@disabled) and (@type!='hidden')]",
      'not': function(m) {
        var e = m[6], p = Selector.patterns,
            x = Selector.xpath, le, v, len = p.length, name;

        var exclusion = [];
        while (e && le != e && (/\S/).test(e)) {
          le = e;
          for (var i = 0; i<len; i++) {
            name = p[i].name
            if (m = e.match(p[i].re)) {
              v = Object.isFunction(x[name]) ? x[name](m) : new Template(x[name]).evaluate(m);
              exclusion.push("(" + v.substring(1, v.length - 1) + ")");
              e = e.replace(m[0], '');
              break;
            }
          }
        }
        return "[not(" + exclusion.join(" and ") + ")]";
      },
      'nth-child':      function(m) {
        return Selector.xpath.pseudos.nth("(count(./preceding-sibling::*) + 1) ", m);
      },
      'nth-last-child': function(m) {
        return Selector.xpath.pseudos.nth("(count(./following-sibling::*) + 1) ", m);
      },
      'nth-of-type':    function(m) {
        return Selector.xpath.pseudos.nth("position() ", m);
      },
      'nth-last-of-type': function(m) {
        return Selector.xpath.pseudos.nth("(last() + 1 - position()) ", m);
      },
      'first-of-type':  function(m) {
        m[6] = "1"; return Selector.xpath.pseudos['nth-of-type'](m);
      },
      'last-of-type':   function(m) {
        m[6] = "1"; return Selector.xpath.pseudos['nth-last-of-type'](m);
      },
      'only-of-type':   function(m) {
        var p = Selector.xpath.pseudos; return p['first-of-type'](m) + p['last-of-type'](m);
      },
      nth: function(fragment, m) {
        var mm, formula = m[6], predicate;
        if (formula == 'even') formula = '2n+0';
        if (formula == 'odd')  formula = '2n+1';
        if (mm = formula.match(/^(\d+)$/)) // digit only
          return '[' + fragment + "= " + mm[1] + ']';
        if (mm = formula.match(/^(-?\d*)?n(([+-])(\d+))?/)) { // an+b
          if (mm[1] == "-") mm[1] = -1;
          var a = mm[1] ? Number(mm[1]) : 1;
          var b = mm[2] ? Number(mm[2]) : 0;
          predicate = "[((#{fragment} - #{b}) mod #{a} = 0) and " +
          "((#{fragment} - #{b}) div #{a} >= 0)]";
          return new Template(predicate).evaluate({
            fragment: fragment, a: a, b: b });
        }
      }
    }
  },

  criteria: {
    tagName:      'n = h.tagName(n, r, "#{1}", c);      c = false;',
    className:    'n = h.className(n, r, "#{1}", c);    c = false;',
    id:           'n = h.id(n, r, "#{1}", c);           c = false;',
    attrPresence: 'n = h.attrPresence(n, r, "#{1}", c); c = false;',
    attr: function(m) {
      m[3] = (m[5] || m[6]);
      return new Template('n = h.attr(n, r, "#{1}", "#{3}", "#{2}", c); c = false;').evaluate(m);
    },
    pseudo: function(m) {
      if (m[6]) m[6] = m[6].replace(/"/g, '\\"');
      return new Template('n = h.pseudo(n, "#{1}", "#{6}", r, c); c = false;').evaluate(m);
    },
    descendant:   'c = "descendant";',
    child:        'c = "child";',
    adjacent:     'c = "adjacent";',
    laterSibling: 'c = "laterSibling";'
  },

  patterns: [
    { name: 'laterSibling', re: /^\s*~\s*/ },
    { name: 'child',        re: /^\s*>\s*/ },
    { name: 'adjacent',     re: /^\s*\+\s*/ },
    { name: 'descendant',   re: /^\s/ },

    { name: 'tagName',      re: /^\s*(\*|[\w\-]+)(\b|$)?/ },
    { name: 'id',           re: /^#([\w\-\*]+)(\b|$)/ },
    { name: 'className',    re: /^\.([\w\-\*]+)(\b|$)/ },
    { name: 'pseudo',       re: /^:((first|last|nth|nth-last|only)(-child|-of-type)|empty|checked|(en|dis)abled|not)(\((.*?)\))?(\b|$|(?=\s|[:+~>]))/ },
    { name: 'attrPresence', re: /^\[((?:[\w-]+:)?[\w-]+)\]/ },
    { name: 'attr',         re: /\[((?:[\w-]*:)?[\w-]+)\s*(?:([!^$*~|]?=)\s*((['"])([^\4]*?)\4|([^'"][^\]]*?)))?\]/ }
  ],

  assertions: {
    tagName: function(element, matches) {
      return matches[1].toUpperCase() == element.tagName.toUpperCase();
    },

    className: function(element, matches) {
      return Element.hasClassName(element, matches[1]);
    },

    id: function(element, matches) {
      return element.id === matches[1];
    },

    attrPresence: function(element, matches) {
      return Element.hasAttribute(element, matches[1]);
    },

    attr: function(element, matches) {
      var nodeValue = Element.readAttribute(element, matches[1]);
      return nodeValue && Selector.operators[matches[2]](nodeValue, matches[5] || matches[6]);
    }
  },

  handlers: {
    concat: function(a, b) {
      for (var i = 0, node; node = b[i]; i++)
        a.push(node);
      return a;
    },

    mark: function(nodes) {
      var _true = Prototype.emptyFunction;
      for (var i = 0, node; node = nodes[i]; i++)
        node._countedByPrototype = _true;
      return nodes;
    },

    unmark: (function(){

      var PROPERTIES_ATTRIBUTES_MAP = (function(){
        var el = document.createElement('div'),
            isBuggy = false,
            propName = '_countedByPrototype',
            value = 'x'
        el[propName] = value;
        isBuggy = (el.getAttribute(propName) === value);
        el = null;
        return isBuggy;
      })();

      return PROPERTIES_ATTRIBUTES_MAP ?
        function(nodes) {
          for (var i = 0, node; node = nodes[i]; i++)
            node.removeAttribute('_countedByPrototype');
          return nodes;
        } :
        function(nodes) {
          for (var i = 0, node; node = nodes[i]; i++)
            node._countedByPrototype = void 0;
          return nodes;
        }
    })(),

    index: function(parentNode, reverse, ofType) {
      parentNode._countedByPrototype = Prototype.emptyFunction;
      if (reverse) {
        for (var nodes = parentNode.childNodes, i = nodes.length - 1, j = 1; i >= 0; i--) {
          var node = nodes[i];
          if (node.nodeType == 1 && (!ofType || node._countedByPrototype)) node.nodeIndex = j++;
        }
      } else {
        for (var i = 0, j = 1, nodes = parentNode.childNodes; node = nodes[i]; i++)
          if (node.nodeType == 1 && (!ofType || node._countedByPrototype)) node.nodeIndex = j++;
      }
    },

    unique: function(nodes) {
      if (nodes.length == 0) return nodes;
      var results = [], n;
      for (var i = 0, l = nodes.length; i < l; i++)
        if (typeof (n = nodes[i])._countedByPrototype == 'undefined') {
          n._countedByPrototype = Prototype.emptyFunction;
          results.push(Element.extend(n));
        }
      return Selector.handlers.unmark(results);
    },

    descendant: function(nodes) {
      var h = Selector.handlers;
      for (var i = 0, results = [], node; node = nodes[i]; i++)
        h.concat(results, node.getElementsByTagName('*'));
      return results;
    },

    child: function(nodes) {
      var h = Selector.handlers;
      for (var i = 0, results = [], node; node = nodes[i]; i++) {
        for (var j = 0, child; child = node.childNodes[j]; j++)
          if (child.nodeType == 1 && child.tagName != '!') results.push(child);
      }
      return results;
    },

    adjacent: function(nodes) {
      for (var i = 0, results = [], node; node = nodes[i]; i++) {
        var next = this.nextElementSibling(node);
        if (next) results.push(next);
      }
      return results;
    },

    laterSibling: function(nodes) {
      var h = Selector.handlers;
      for (var i = 0, results = [], node; node = nodes[i]; i++)
        h.concat(results, Element.nextSiblings(node));
      return results;
    },

    nextElementSibling: function(node) {
      while (node = node.nextSibling)
        if (node.nodeType == 1) return node;
      return null;
    },

    previousElementSibling: function(node) {
      while (node = node.previousSibling)
        if (node.nodeType == 1) return node;
      return null;
    },

    tagName: function(nodes, root, tagName, combinator) {
      var uTagName = tagName.toUpperCase();
      var results = [], h = Selector.handlers;
      if (nodes) {
        if (combinator) {
          if (combinator == "descendant") {
            for (var i = 0, node; node = nodes[i]; i++)
              h.concat(results, node.getElementsByTagName(tagName));
            return results;
          } else nodes = this[combinator](nodes);
          if (tagName == "*") return nodes;
        }
        for (var i = 0, node; node = nodes[i]; i++)
          if (node.tagName.toUpperCase() === uTagName) results.push(node);
        return results;
      } else return root.getElementsByTagName(tagName);
    },

    id: function(nodes, root, id, combinator) {
      var targetNode = $(id), h = Selector.handlers;

      if (root == document) {
        if (!targetNode) return [];
        if (!nodes) return [targetNode];
      } else {
        if (!root.sourceIndex || root.sourceIndex < 1) {
          var nodes = root.getElementsByTagName('*');
          for (var j = 0, node; node = nodes[j]; j++) {
            if (node.id === id) return [node];
          }
        }
      }

      if (nodes) {
        if (combinator) {
          if (combinator == 'child') {
            for (var i = 0, node; node = nodes[i]; i++)
              if (targetNode.parentNode == node) return [targetNode];
          } else if (combinator == 'descendant') {
            for (var i = 0, node; node = nodes[i]; i++)
              if (Element.descendantOf(targetNode, node)) return [targetNode];
          } else if (combinator == 'adjacent') {
            for (var i = 0, node; node = nodes[i]; i++)
              if (Selector.handlers.previousElementSibling(targetNode) == node)
                return [targetNode];
          } else nodes = h[combinator](nodes);
        }
        for (var i = 0, node; node = nodes[i]; i++)
          if (node == targetNode) return [targetNode];
        return [];
      }
      return (targetNode && Element.descendantOf(targetNode, root)) ? [targetNode] : [];
    },

    className: function(nodes, root, className, combinator) {
      if (nodes && combinator) nodes = this[combinator](nodes);
      return Selector.handlers.byClassName(nodes, root, className);
    },

    byClassName: function(nodes, root, className) {
      if (!nodes) nodes = Selector.handlers.descendant([root]);
      var needle = ' ' + className + ' ';
      for (var i = 0, results = [], node, nodeClassName; node = nodes[i]; i++) {
        nodeClassName = node.className;
        if (nodeClassName.length == 0) continue;
        if (nodeClassName == className || (' ' + nodeClassName + ' ').include(needle))
          results.push(node);
      }
      return results;
    },

    attrPresence: function(nodes, root, attr, combinator) {
      if (!nodes) nodes = root.getElementsByTagName("*");
      if (nodes && combinator) nodes = this[combinator](nodes);
      var results = [];
      for (var i = 0, node; node = nodes[i]; i++)
        if (Element.hasAttribute(node, attr)) results.push(node);
      return results;
    },

    attr: function(nodes, root, attr, value, operator, combinator) {
      if (!nodes) nodes = root.getElementsByTagName("*");
      if (nodes && combinator) nodes = this[combinator](nodes);
      var handler = Selector.operators[operator], results = [];
      for (var i = 0, node; node = nodes[i]; i++) {
        var nodeValue = Element.readAttribute(node, attr);
        if (nodeValue === null) continue;
        if (handler(nodeValue, value)) results.push(node);
      }
      return results;
    },

    pseudo: function(nodes, name, value, root, combinator) {
      if (nodes && combinator) nodes = this[combinator](nodes);
      if (!nodes) nodes = root.getElementsByTagName("*");
      return Selector.pseudos[name](nodes, value, root);
    }
  },

  pseudos: {
    'first-child': function(nodes, value, root) {
      for (var i = 0, results = [], node; node = nodes[i]; i++) {
        if (Selector.handlers.previousElementSibling(node)) continue;
          results.push(node);
      }
      return results;
    },
    'last-child': function(nodes, value, root) {
      for (var i = 0, results = [], node; node = nodes[i]; i++) {
        if (Selector.handlers.nextElementSibling(node)) continue;
          results.push(node);
      }
      return results;
    },
    'only-child': function(nodes, value, root) {
      var h = Selector.handlers;
      for (var i = 0, results = [], node; node = nodes[i]; i++)
        if (!h.previousElementSibling(node) && !h.nextElementSibling(node))
          results.push(node);
      return results;
    },
    'nth-child':        function(nodes, formula, root) {
      return Selector.pseudos.nth(nodes, formula, root);
    },
    'nth-last-child':   function(nodes, formula, root) {
      return Selector.pseudos.nth(nodes, formula, root, true);
    },
    'nth-of-type':      function(nodes, formula, root) {
      return Selector.pseudos.nth(nodes, formula, root, false, true);
    },
    'nth-last-of-type': function(nodes, formula, root) {
      return Selector.pseudos.nth(nodes, formula, root, true, true);
    },
    'first-of-type':    function(nodes, formula, root) {
      return Selector.pseudos.nth(nodes, "1", root, false, true);
    },
    'last-of-type':     function(nodes, formula, root) {
      return Selector.pseudos.nth(nodes, "1", root, true, true);
    },
    'only-of-type':     function(nodes, formula, root) {
      var p = Selector.pseudos;
      return p['last-of-type'](p['first-of-type'](nodes, formula, root), formula, root);
    },

    getIndices: function(a, b, total) {
      if (a == 0) return b > 0 ? [b] : [];
      return $R(1, total).inject([], function(memo, i) {
        if (0 == (i - b) % a && (i - b) / a >= 0) memo.push(i);
        return memo;
      });
    },

    nth: function(nodes, formula, root, reverse, ofType) {
      if (nodes.length == 0) return [];
      if (formula == 'even') formula = '2n+0';
      if (formula == 'odd')  formula = '2n+1';
      var h = Selector.handlers, results = [], indexed = [], m;
      h.mark(nodes);
      for (var i = 0, node; node = nodes[i]; i++) {
        if (!node.parentNode._countedByPrototype) {
          h.index(node.parentNode, reverse, ofType);
          indexed.push(node.parentNode);
        }
      }
      if (formula.match(/^\d+$/)) { // just a number
        formula = Number(formula);
        for (var i = 0, node; node = nodes[i]; i++)
          if (node.nodeIndex == formula) results.push(node);
      } else if (m = formula.match(/^(-?\d*)?n(([+-])(\d+))?/)) { // an+b
        if (m[1] == "-") m[1] = -1;
        var a = m[1] ? Number(m[1]) : 1;
        var b = m[2] ? Number(m[2]) : 0;
        var indices = Selector.pseudos.getIndices(a, b, nodes.length);
        for (var i = 0, node, l = indices.length; node = nodes[i]; i++) {
          for (var j = 0; j < l; j++)
            if (node.nodeIndex == indices[j]) results.push(node);
        }
      }
      h.unmark(nodes);
      h.unmark(indexed);
      return results;
    },

    'empty': function(nodes, value, root) {
      for (var i = 0, results = [], node; node = nodes[i]; i++) {
        if (node.tagName == '!' || node.firstChild) continue;
        results.push(node);
      }
      return results;
    },

    'not': function(nodes, selector, root) {
      var h = Selector.handlers, selectorType, m;
      var exclusions = new Selector(selector).findElements(root);
      h.mark(exclusions);
      for (var i = 0, results = [], node; node = nodes[i]; i++)
        if (!node._countedByPrototype) results.push(node);
      h.unmark(exclusions);
      return results;
    },

    'enabled': function(nodes, value, root) {
      for (var i = 0, results = [], node; node = nodes[i]; i++)
        if (!node.disabled && (!node.type || node.type !== 'hidden'))
          results.push(node);
      return results;
    },

    'disabled': function(nodes, value, root) {
      for (var i = 0, results = [], node; node = nodes[i]; i++)
        if (node.disabled) results.push(node);
      return results;
    },

    'checked': function(nodes, value, root) {
      for (var i = 0, results = [], node; node = nodes[i]; i++)
        if (node.checked) results.push(node);
      return results;
    }
  },

  operators: {
    '=':  function(nv, v) { return nv == v; },
    '!=': function(nv, v) { return nv != v; },
    '^=': function(nv, v) { return nv == v || nv && nv.startsWith(v); },
    '$=': function(nv, v) { return nv == v || nv && nv.endsWith(v); },
    '*=': function(nv, v) { return nv == v || nv && nv.include(v); },
    '~=': function(nv, v) { return (' ' + nv + ' ').include(' ' + v + ' '); },
    '|=': function(nv, v) { return ('-' + (nv || "").toUpperCase() +
     '-').include('-' + (v || "").toUpperCase() + '-'); }
  },

  split: function(expression) {
    var expressions = [];
    expression.scan(/(([\w#:.~>+()\s-]+|\*|\[.*?\])+)\s*(,|$)/, function(m) {
      expressions.push(m[1].strip());
    });
    return expressions;
  },

  matchElements: function(elements, expression) {
    var matches = $$(expression), h = Selector.handlers;
    h.mark(matches);
    for (var i = 0, results = [], element; element = elements[i]; i++)
      if (element._countedByPrototype) results.push(element);
    h.unmark(matches);
    return results;
  },

  findElement: function(elements, expression, index) {
    if (Object.isNumber(expression)) {
      index = expression; expression = false;
    }
    return Selector.matchElements(elements, expression || '*')[index || 0];
  },

  findChildElements: function(element, expressions) {
    expressions = Selector.split(expressions.join(','));
    var results = [], h = Selector.handlers;
    for (var i = 0, l = expressions.length, selector; i < l; i++) {
      selector = new Selector(expressions[i].strip());
      h.concat(results, selector.findElements(element));
    }
    return (l > 1) ? h.unique(results) : results;
  }
});

if (Prototype.Browser.IE) {
  Object.extend(Selector.handlers, {
    concat: function(a, b) {
      for (var i = 0, node; node = b[i]; i++)
        if (node.tagName !== "!") a.push(node);
      return a;
    }
  });
}

function $$() {
  return Selector.findChildElements(document, $A(arguments));
}

var Form = {
  reset: function(form) {
    form = $(form);
    form.reset();
    return form;
  },

  serializeElements: function(elements, options) {
    if (typeof options != 'object') options = { hash: !!options };
    else if (Object.isUndefined(options.hash)) options.hash = true;
    var key, value, submitted = false, submit = options.submit;

    var data = elements.inject({ }, function(result, element) {
      if (!element.disabled && element.name) {
        key = element.name; value = $(element).getValue();
        if (value != null && element.type != 'file' && (element.type != 'submit' || (!submitted &&
            submit !== false && (!submit || key == submit) && (submitted = true)))) {
          if (key in result) {
            if (!Object.isArray(result[key])) result[key] = [result[key]];
            result[key].push(value);
          }
          else result[key] = value;
        }
      }
      return result;
    });

    return options.hash ? data : Object.toQueryString(data);
  }
};

Form.Methods = {
  serialize: function(form, options) {
    return Form.serializeElements(Form.getElements(form), options);
  },

  getElements: function(form) {
    var elements = $(form).getElementsByTagName('*'),
        element,
        arr = [ ],
        serializers = Form.Element.Serializers;
    for (var i = 0; element = elements[i]; i++) {
      arr.push(element);
    }
    return arr.inject([], function(elements, child) {
      if (serializers[child.tagName.toLowerCase()])
        elements.push(Element.extend(child));
      return elements;
    })
  },

  getInputs: function(form, typeName, name) {
    form = $(form);
    var inputs = form.getElementsByTagName('input');

    if (!typeName && !name) return $A(inputs).map(Element.extend);

    for (var i = 0, matchingInputs = [], length = inputs.length; i < length; i++) {
      var input = inputs[i];
      if ((typeName && input.type != typeName) || (name && input.name != name))
        continue;
      matchingInputs.push(Element.extend(input));
    }

    return matchingInputs;
  },

  disable: function(form) {
    form = $(form);
    Form.getElements(form).invoke('disable');
    return form;
  },

  enable: function(form) {
    form = $(form);
    Form.getElements(form).invoke('enable');
    return form;
  },

  findFirstElement: function(form) {
    var elements = $(form).getElements().findAll(function(element) {
      return 'hidden' != element.type && !element.disabled;
    });
    var firstByIndex = elements.findAll(function(element) {
      return element.hasAttribute('tabIndex') && element.tabIndex >= 0;
    }).sortBy(function(element) { return element.tabIndex }).first();

    return firstByIndex ? firstByIndex : elements.find(function(element) {
      return /^(?:input|select|textarea)$/i.test(element.tagName);
    });
  },

  focusFirstElement: function(form) {
    form = $(form);
    form.findFirstElement().activate();
    return form;
  },

  request: function(form, options) {
    form = $(form), options = Object.clone(options || { });

    var params = options.parameters, action = form.readAttribute('action') || '';
    if (action.blank()) action = window.location.href;
    options.parameters = form.serialize(true);

    if (params) {
      if (Object.isString(params)) params = params.toQueryParams();
      Object.extend(options.parameters, params);
    }

    if (form.hasAttribute('method') && !options.method)
      options.method = form.method;

    return new Ajax.Request(action, options);
  }
};

/*--------------------------------------------------------------------------*/


Form.Element = {
  focus: function(element) {
    $(element).focus();
    return element;
  },

  select: function(element) {
    $(element).select();
    return element;
  }
};

Form.Element.Methods = {

  serialize: function(element) {
    element = $(element);
    if (!element.disabled && element.name) {
      var value = element.getValue();
      if (value != undefined) {
        var pair = { };
        pair[element.name] = value;
        return Object.toQueryString(pair);
      }
    }
    return '';
  },

  getValue: function(element) {
    element = $(element);
    var method = element.tagName.toLowerCase();
    return Form.Element.Serializers[method](element);
  },

  setValue: function(element, value) {
    element = $(element);
    var method = element.tagName.toLowerCase();
    Form.Element.Serializers[method](element, value);
    return element;
  },

  clear: function(element) {
    $(element).value = '';
    return element;
  },

  present: function(element) {
    return $(element).value != '';
  },

  activate: function(element) {
    element = $(element);
    try {
      element.focus();
      if (element.select && (element.tagName.toLowerCase() != 'input' ||
          !(/^(?:button|reset|submit)$/i.test(element.type))))
        element.select();
    } catch (e) { }
    return element;
  },

  disable: function(element) {
    element = $(element);
    element.disabled = true;
    return element;
  },

  enable: function(element) {
    element = $(element);
    element.disabled = false;
    return element;
  }
};

/*--------------------------------------------------------------------------*/

var Field = Form.Element;

var $F = Form.Element.Methods.getValue;

/*--------------------------------------------------------------------------*/

Form.Element.Serializers = {
  input: function(element, value) {
    switch (element.type.toLowerCase()) {
      case 'checkbox':
      case 'radio':
        return Form.Element.Serializers.inputSelector(element, value);
      default:
        return Form.Element.Serializers.textarea(element, value);
    }
  },

  inputSelector: function(element, value) {
    if (Object.isUndefined(value)) return element.checked ? element.value : null;
    else element.checked = !!value;
  },

  textarea: function(element, value) {
    if (Object.isUndefined(value)) return element.value;
    else element.value = value;
  },

  select: function(element, value) {
    if (Object.isUndefined(value))
      return this[element.type == 'select-one' ?
        'selectOne' : 'selectMany'](element);
    else {
      var opt, currentValue, single = !Object.isArray(value);
      for (var i = 0, length = element.length; i < length; i++) {
        opt = element.options[i];
        currentValue = this.optionValue(opt);
        if (single) {
          if (currentValue == value) {
            opt.selected = true;
            return;
          }
        }
        else opt.selected = value.include(currentValue);
      }
    }
  },

  selectOne: function(element) {
    var index = element.selectedIndex;
    return index >= 0 ? this.optionValue(element.options[index]) : null;
  },

  selectMany: function(element) {
    var values, length = element.length;
    if (!length) return null;

    for (var i = 0, values = []; i < length; i++) {
      var opt = element.options[i];
      if (opt.selected) values.push(this.optionValue(opt));
    }
    return values;
  },

  optionValue: function(opt) {
    return Element.extend(opt).hasAttribute('value') ? opt.value : opt.text;
  }
};

/*--------------------------------------------------------------------------*/


Abstract.TimedObserver = Class.create(PeriodicalExecuter, {
  initialize: function($super, element, frequency, callback) {
    $super(callback, frequency);
    this.element   = $(element);
    this.lastValue = this.getValue();
  },

  execute: function() {
    var value = this.getValue();
    if (Object.isString(this.lastValue) && Object.isString(value) ?
        this.lastValue != value : String(this.lastValue) != String(value)) {
      this.callback(this.element, value);
      this.lastValue = value;
    }
  }
});

Form.Element.Observer = Class.create(Abstract.TimedObserver, {
  getValue: function() {
    return Form.Element.getValue(this.element);
  }
});

Form.Observer = Class.create(Abstract.TimedObserver, {
  getValue: function() {
    return Form.serialize(this.element);
  }
});

/*--------------------------------------------------------------------------*/

Abstract.EventObserver = Class.create({
  initialize: function(element, callback) {
    this.element  = $(element);
    this.callback = callback;

    this.lastValue = this.getValue();
    if (this.element.tagName.toLowerCase() == 'form')
      this.registerFormCallbacks();
    else
      this.registerCallback(this.element);
  },

  onElementEvent: function() {
    var value = this.getValue();
    if (this.lastValue != value) {
      this.callback(this.element, value);
      this.lastValue = value;
    }
  },

  registerFormCallbacks: function() {
    Form.getElements(this.element).each(this.registerCallback, this);
  },

  registerCallback: function(element) {
    if (element.type) {
      switch (element.type.toLowerCase()) {
        case 'checkbox':
        case 'radio':
          Event.observe(element, 'click', this.onElementEvent.bind(this));
          break;
        default:
          Event.observe(element, 'change', this.onElementEvent.bind(this));
          break;
      }
    }
  }
});

Form.Element.EventObserver = Class.create(Abstract.EventObserver, {
  getValue: function() {
    return Form.Element.getValue(this.element);
  }
});

Form.EventObserver = Class.create(Abstract.EventObserver, {
  getValue: function() {
    return Form.serialize(this.element);
  }
});
(function() {

  var Event = {
    KEY_BACKSPACE: 8,
    KEY_TAB:       9,
    KEY_RETURN:   13,
    KEY_ESC:      27,
    KEY_LEFT:     37,
    KEY_UP:       38,
    KEY_RIGHT:    39,
    KEY_DOWN:     40,
    KEY_DELETE:   46,
    KEY_HOME:     36,
    KEY_END:      35,
    KEY_PAGEUP:   33,
    KEY_PAGEDOWN: 34,
    KEY_INSERT:   45,

    cache: {}
  };

  var docEl = document.documentElement;
  var MOUSEENTER_MOUSELEAVE_EVENTS_SUPPORTED = 'onmouseenter' in docEl
    && 'onmouseleave' in docEl;

  var _isButton;
  if (Prototype.Browser.IE) {
    var buttonMap = { 0: 1, 1: 4, 2: 2 };
    _isButton = function(event, code) {
      return event.button === buttonMap[code];
    };
  } else if (Prototype.Browser.WebKit) {
    _isButton = function(event, code) {
      switch (code) {
        case 0: return event.which == 1 && !event.metaKey;
        case 1: return event.which == 1 && event.metaKey;
        default: return false;
      }
    };
  } else {
    _isButton = function(event, code) {
      return event.which ? (event.which === code + 1) : (event.button === code);
    };
  }

  function isLeftClick(event)   { return _isButton(event, 0) }

  function isMiddleClick(event) { return _isButton(event, 1) }

  function isRightClick(event)  { return _isButton(event, 2) }

  function element(event) {
    event = Event.extend(event);

    var node = event.target, type = event.type,
     currentTarget = event.currentTarget;

    if (currentTarget && currentTarget.tagName) {
      if (type === 'load' || type === 'error' ||
        (type === 'click' && currentTarget.tagName.toLowerCase() === 'input'
          && currentTarget.type === 'radio'))
            node = currentTarget;
    }

    if (node.nodeType == Node.TEXT_NODE)
      node = node.parentNode;

    return Element.extend(node);
  }

  function findElement(event, expression) {
    var element = Event.element(event);
    if (!expression) return element;
    var elements = [element].concat(element.ancestors());
    return Selector.findElement(elements, expression, 0);
  }

  function pointer(event) {
    return { x: pointerX(event), y: pointerY(event) };
  }

  function pointerX(event) {
    var docElement = document.documentElement,
     body = document.body || { scrollLeft: 0 };

    return event.pageX || (event.clientX +
      (docElement.scrollLeft || body.scrollLeft) -
      (docElement.clientLeft || 0));
  }

  function pointerY(event) {
    var docElement = document.documentElement,
     body = document.body || { scrollTop: 0 };

    return  event.pageY || (event.clientY +
       (docElement.scrollTop || body.scrollTop) -
       (docElement.clientTop || 0));
  }


  function stop(event) {
    Event.extend(event);
    event.preventDefault();
    event.stopPropagation();

    event.stopped = true;
  }

  Event.Methods = {
    isLeftClick: isLeftClick,
    isMiddleClick: isMiddleClick,
    isRightClick: isRightClick,

    element: element,
    findElement: findElement,

    pointer: pointer,
    pointerX: pointerX,
    pointerY: pointerY,

    stop: stop
  };


  var methods = Object.keys(Event.Methods).inject({ }, function(m, name) {
    m[name] = Event.Methods[name].methodize();
    return m;
  });

  if (Prototype.Browser.IE) {
    function _relatedTarget(event) {
      var element;
      switch (event.type) {
        case 'mouseover': element = event.fromElement; break;
        case 'mouseout':  element = event.toElement;   break;
        default: return null;
      }
      return Element.extend(element);
    }

    Object.extend(methods, {
      stopPropagation: function() { this.cancelBubble = true },
      preventDefault:  function() { this.returnValue = false },
      inspect: function() { return '[object Event]' }
    });

    Event.extend = function(event, element) {
      if (!event) return false;
      if (event._extendedByPrototype) return event;

      event._extendedByPrototype = Prototype.emptyFunction;
      var pointer = Event.pointer(event);

      Object.extend(event, {
        target: event.srcElement || element,
        relatedTarget: _relatedTarget(event),
        pageX:  pointer.x,
        pageY:  pointer.y
      });

      return Object.extend(event, methods);
    };
  } else {
    Event.prototype = window.Event.prototype || document.createEvent('HTMLEvents').__proto__;
    Object.extend(Event.prototype, methods);
    Event.extend = Prototype.K;
  }

  function _createResponder(element, eventName, handler) {
    var registry = Element.retrieve(element, 'prototype_event_registry');

    if (Object.isUndefined(registry)) {
      CACHE.push(element);
      registry = Element.retrieve(element, 'prototype_event_registry', $H());
    }

    var respondersForEvent = registry.get(eventName);
    if (Object.isUndefined(respondersForEvent)) {
      respondersForEvent = [];
      registry.set(eventName, respondersForEvent);
    }

    if (respondersForEvent.pluck('handler').include(handler)) return false;

    var responder;
    if (eventName.include(":")) {
      responder = function(event) {
        if (Object.isUndefined(event.eventName))
          return false;

        if (event.eventName !== eventName)
          return false;

        Event.extend(event, element);
        handler.call(element, event);
      };
    } else {
      if (!MOUSEENTER_MOUSELEAVE_EVENTS_SUPPORTED &&
       (eventName === "mouseenter" || eventName === "mouseleave")) {
        if (eventName === "mouseenter" || eventName === "mouseleave") {
          responder = function(event) {
            Event.extend(event, element);

            var parent = event.relatedTarget;
            while (parent && parent !== element) {
              try { parent = parent.parentNode; }
              catch(e) { parent = element; }
            }

            if (parent === element) return;

            handler.call(element, event);
          };
        }
      } else {
        responder = function(event) {
          Event.extend(event, element);
          handler.call(element, event);
        };
      }
    }

    responder.handler = handler;
    respondersForEvent.push(responder);
    return responder;
  }

  function _destroyCache() {
    for (var i = 0, length = CACHE.length; i < length; i++) {
      Event.stopObserving(CACHE[i]);
      CACHE[i] = null;
    }
  }

  var CACHE = [];

  if (Prototype.Browser.IE)
    window.attachEvent('onunload', _destroyCache);

  if (Prototype.Browser.WebKit)
    window.addEventListener('unload', Prototype.emptyFunction, false);


  var _getDOMEventName = Prototype.K;

  if (!MOUSEENTER_MOUSELEAVE_EVENTS_SUPPORTED) {
    _getDOMEventName = function(eventName) {
      var translations = { mouseenter: "mouseover", mouseleave: "mouseout" };
      return eventName in translations ? translations[eventName] : eventName;
    };
  }

  function observe(element, eventName, handler) {
    element = $(element);

    var responder = _createResponder(element, eventName, handler);

    if (!responder) return element;

    if (eventName.include(':')) {
      if (element.addEventListener)
        element.addEventListener("dataavailable", responder, false);
      else {
        element.attachEvent("ondataavailable", responder);
        element.attachEvent("onfilterchange", responder);
      }
    } else {
      var actualEventName = _getDOMEventName(eventName);

      if (element.addEventListener)
        element.addEventListener(actualEventName, responder, false);
      else
        element.attachEvent("on" + actualEventName, responder);
    }

    return element;
  }

  function stopObserving(element, eventName, handler) {
    element = $(element);

    var registry = Element.retrieve(element, 'prototype_event_registry');

    if (Object.isUndefined(registry)) return element;

    if (eventName && !handler) {
      var responders = registry.get(eventName);

      if (Object.isUndefined(responders)) return element;

      responders.each( function(r) {
        Element.stopObserving(element, eventName, r.handler);
      });
      return element;
    } else if (!eventName) {
      registry.each( function(pair) {
        var eventName = pair.key, responders = pair.value;

        responders.each( function(r) {
          Element.stopObserving(element, eventName, r.handler);
        });
      });
      return element;
    }

    var responders = registry.get(eventName);

    if (!responders) return;

    var responder = responders.find( function(r) { return r.handler === handler; });
    if (!responder) return element;

    var actualEventName = _getDOMEventName(eventName);

    if (eventName.include(':')) {
      if (element.removeEventListener)
        element.removeEventListener("dataavailable", responder, false);
      else {
        element.detachEvent("ondataavailable", responder);
        element.detachEvent("onfilterchange",  responder);
      }
    } else {
      if (element.removeEventListener)
        element.removeEventListener(actualEventName, responder, false);
      else
        element.detachEvent('on' + actualEventName, responder);
    }

    registry.set(eventName, responders.without(responder));

    return element;
  }

  function fire(element, eventName, memo, bubble) {
    element = $(element);

    if (Object.isUndefined(bubble))
      bubble = true;

    if (element == document && document.createEvent && !element.dispatchEvent)
      element = document.documentElement;

    var event;
    if (document.createEvent) {
      event = document.createEvent('HTMLEvents');
      event.initEvent('dataavailable', true, true);
    } else {
      event = document.createEventObject();
      event.eventType = bubble ? 'ondataavailable' : 'onfilterchange';
    }

    event.eventName = eventName;
    event.memo = memo || { };

    if (document.createEvent)
      element.dispatchEvent(event);
    else
      element.fireEvent(event.eventType, event);

    return Event.extend(event);
  }


  Object.extend(Event, Event.Methods);

  Object.extend(Event, {
    fire:          fire,
    observe:       observe,
    stopObserving: stopObserving
  });

  Element.addMethods({
    fire:          fire,

    observe:       observe,

    stopObserving: stopObserving
  });

  Object.extend(document, {
    fire:          fire.methodize(),

    observe:       observe.methodize(),

    stopObserving: stopObserving.methodize(),

    loaded:        false
  });

  if (window.Event) Object.extend(window.Event, Event);
  else window.Event = Event;
})();

(function() {
  /* Support for the DOMContentLoaded event is based on work by Dan Webb,
     Matthias Miller, Dean Edwards, John Resig, and Diego Perini. */

  var timer;

  function fireContentLoadedEvent() {
    if (document.loaded) return;
    if (timer) window.clearTimeout(timer);
    document.loaded = true;
    document.fire('dom:loaded');
  }

  function checkReadyState() {
    if (document.readyState === 'complete') {
      document.stopObserving('readystatechange', checkReadyState);
      fireContentLoadedEvent();
    }
  }

  function pollDoScroll() {
    try { document.documentElement.doScroll('left'); }
    catch(e) {
      timer = pollDoScroll.defer();
      return;
    }
    fireContentLoadedEvent();
  }

  if (document.addEventListener) {
    document.addEventListener('DOMContentLoaded', fireContentLoadedEvent, false);
  } else {
    document.observe('readystatechange', checkReadyState);
    if (window == top)
      timer = pollDoScroll.defer();
  }

  Event.observe(window, 'load', fireContentLoadedEvent);
})();

Element.addMethods();

/*------------------------------- DEPRECATED -------------------------------*/

Hash.toQueryString = Object.toQueryString;

var Toggle = { display: Element.toggle };

Element.Methods.childOf = Element.Methods.descendantOf;

var Insertion = {
  Before: function(element, content) {
    return Element.insert(element, {before:content});
  },

  Top: function(element, content) {
    return Element.insert(element, {top:content});
  },

  Bottom: function(element, content) {
    return Element.insert(element, {bottom:content});
  },

  After: function(element, content) {
    return Element.insert(element, {after:content});
  }
};

var $continue = new Error('"throw $continue" is deprecated, use "return" instead');

var Position = {
  includeScrollOffsets: false,

  prepare: function() {
    this.deltaX =  window.pageXOffset
                || document.documentElement.scrollLeft
                || document.body.scrollLeft
                || 0;
    this.deltaY =  window.pageYOffset
                || document.documentElement.scrollTop
                || document.body.scrollTop
                || 0;
  },

  within: function(element, x, y) {
    if (this.includeScrollOffsets)
      return this.withinIncludingScrolloffsets(element, x, y);
    this.xcomp = x;
    this.ycomp = y;
    this.offset = Element.cumulativeOffset(element);

    return (y >= this.offset[1] &&
            y <  this.offset[1] + element.offsetHeight &&
            x >= this.offset[0] &&
            x <  this.offset[0] + element.offsetWidth);
  },

  withinIncludingScrolloffsets: function(element, x, y) {
    var offsetcache = Element.cumulativeScrollOffset(element);

    this.xcomp = x + offsetcache[0] - this.deltaX;
    this.ycomp = y + offsetcache[1] - this.deltaY;
    this.offset = Element.cumulativeOffset(element);

    return (this.ycomp >= this.offset[1] &&
            this.ycomp <  this.offset[1] + element.offsetHeight &&
            this.xcomp >= this.offset[0] &&
            this.xcomp <  this.offset[0] + element.offsetWidth);
  },

  overlap: function(mode, element) {
    if (!mode) return 0;
    if (mode == 'vertical')
      return ((this.offset[1] + element.offsetHeight) - this.ycomp) /
        element.offsetHeight;
    if (mode == 'horizontal')
      return ((this.offset[0] + element.offsetWidth) - this.xcomp) /
        element.offsetWidth;
  },


  cumulativeOffset: Element.Methods.cumulativeOffset,

  positionedOffset: Element.Methods.positionedOffset,

  absolutize: function(element) {
    Position.prepare();
    return Element.absolutize(element);
  },

  relativize: function(element) {
    Position.prepare();
    return Element.relativize(element);
  },

  realOffset: Element.Methods.cumulativeScrollOffset,

  offsetParent: Element.Methods.getOffsetParent,

  page: Element.Methods.viewportOffset,

  clone: function(source, target, options) {
    options = options || { };
    return Element.clonePosition(target, source, options);
  }
};

/*--------------------------------------------------------------------------*/

if (!document.getElementsByClassName) document.getElementsByClassName = function(instanceMethods){
  function iter(name) {
    return name.blank() ? null : "[contains(concat(' ', @class, ' '), ' " + name + " ')]";
  }

  instanceMethods.getElementsByClassName = Prototype.BrowserFeatures.XPath ?
  function(element, className) {
    className = className.toString().strip();
    var cond = /\s/.test(className) ? $w(className).map(iter).join('') : iter(className);
    return cond ? document._getElementsByXPath('.//*' + cond, element) : [];
  } : function(element, className) {
    className = className.toString().strip();
    var elements = [], classNames = (/\s/.test(className) ? $w(className) : null);
    if (!classNames && !className) return elements;

    var nodes = $(element).getElementsByTagName('*');
    className = ' ' + className + ' ';

    for (var i = 0, child, cn; child = nodes[i]; i++) {
      if (child.className && (cn = ' ' + child.className + ' ') && (cn.include(className) ||
          (classNames && classNames.all(function(name) {
            return !name.toString().blank() && cn.include(' ' + name + ' ');
          }))))
        elements.push(Element.extend(child));
    }
    return elements;
  };

  return function(className, parentElement) {
    return $(parentElement || document.body).getElementsByClassName(className);
  };
}(Element.Methods);

/*--------------------------------------------------------------------------*/

Element.ClassNames = Class.create();
Element.ClassNames.prototype = {
  initialize: function(element) {
    this.element = $(element);
  },

  _each: function(iterator) {
    this.element.className.split(/\s+/).select(function(name) {
      return name.length > 0;
    })._each(iterator);
  },

  set: function(className) {
    this.element.className = className;
  },

  add: function(classNameToAdd) {
    if (this.include(classNameToAdd)) return;
    this.set($A(this).concat(classNameToAdd).join(' '));
  },

  remove: function(classNameToRemove) {
    if (!this.include(classNameToRemove)) return;
    this.set($A(this).without(classNameToRemove).join(' '));
  },

  toString: function() {
    return $A(this).join(' ');
  }
};

Object.extend(Element.ClassNames.prototype, Enumerable);

/*--------------------------------------------------------------------------*/

/**
 * Grafico - SVG graphing library - base file
 *
 * Copyright (c) 2009 - 2010 Kilian Valkhof (kilianvalkhof.com) - Originally developed by Alex Young (http://alexyoung.org)
 * Visit grafico.kilianvalkhof.com for more information and changelogs.
 * Licensed under the MIT license. http://www.opensource.org/licenses/mit-license.php
 * Modified by Sergio Téllez August 2010
 */
"use strict";
var Grafico = {
  Version: "0.9",

  Base: {},
  BaseGraph: {},
  Normaliser: {},
  LineGraph: {},
  AreaGraph: {},
  StackGraph: {},
  StreamGraph: {},
  BarGraph: {},
  StackedBarGraph: {},
  HorizontalBarGraph: {},
  SparkLine: {},
  SparkBar: {}
};

Grafico.Base = Class.create({
  normaliseData: function (data) {
    return $A(data).collect(function (value) {
      return this.normalise(value);
    }.bind(this));
  },

  deepCopy: function (obj) {
    var out, i, len;
    if (Object.prototype.toString.call(obj) === '[object Array]') {
      out = [];
      len = obj.length;
      for (i = 0; i < len; i++) {
        out[i] = arguments.callee(obj[i]);
      }
      return out;
    }
    if (typeof obj === 'object') {
      out = {};
      for (i in obj) {
        out[i] = arguments.callee(obj[i]);
      }
      return out;
    }
    return obj;
  }
});

Grafico.Normaliser = Class.create({
  initialize: function(data, options) {
    this.options = {
      start_value: undefined, // override start_value
      number_of_tickmarks: 10 // number of labels to aim for
    };
    Object.extend(this.options, options || {});

    // Data range
    this.min = data.min();
    this.max = data.max();

    // Bottom of graph
    this.start_value = undefined;
    // Used in barcharts
    this.zero_value  = undefined;
    // Range displayed on the axis
    this.range = undefined;
    // Difference between labels
    this.step = undefined;

    // Override start_value with options value
    if (this.options.start_value !== undefined) {
      this.start_value = this.options.start_value;
      this.zero_value  = this.options.start_value;
    }

    // Edgecase of zero values
    if (this.min == 0 && this.min == this.max) {
      this.start_value = 0;
      this.zero_value  = 0;
      this.range       = 1;
      this.step        = 1;
    } else {
      var normalized_min;

      if (this.start_value !== undefined && this.min >= 0) {
        normalized_min = this.start_value;
      } else {
        normalized_min = this.min;
      }
      this.looseLabels(normalized_min, this.max);
    }
  },

  // Based on:
  // http://books.google.com/books?id=fvA7zLEFWZgC&pg=PA61&lpg=PA61#v=onepage&q&f=false
  looseLabels: function(min, max) {
    var range = min == max ? this.niceNumber(Math.abs(max), false) : this.niceNumber(max - min, false),
        d     = this.niceNumber(range / (this.options.number_of_tickmarks - 1), true),
        precision = [(-Math.floor(Math.LOG10E * Math.log(d))), 0].max(),
        graphmin  = this.floorToPrecision(min / d , precision) * d,
        graphmax  = this.ceilToPrecision(max / d, precision) * d,
        margin    = this.roundToPrecision(this.niceNumber(0.5 * d, true), precision);

    this.step = this.roundToPrecision(d, precision);

    // Leave some headroom on top
    // handle cases of negative values
    if (this.max <= 0) {
      graphmax = [graphmax + margin, 0].min();
    } else {
      graphmax = graphmax + margin;
    }

    // Add some headroom to the bottom
    if (this.min < 0) {
      graphmin = graphmin - margin;
    } else {
      graphmin = [graphmin - margin, 0].max();
    }

    // Round to a proper origin value
    if (min !== max) {
      graphmin = this.roundToOrigin(graphmin, 1);
    }

    this.range = this.roundToPrecision(Math.abs( this.ceilToPrecision(graphmax, precision) - this.floorToPrecision(graphmin, precision)), precision);
    this.start_value = this.floorToPrecision(graphmin, precision);
    this.zero_value = this.roundToPrecision(Math.abs(graphmin) / this.step, precision);
  },

  roundToPrecision: function(x, precision) {
    var exp = Math.pow(10, precision);
    return Math.round(x * exp) / exp;
  },

  floorToPrecision: function(x, precision) {
    var exp = Math.pow(10, precision);
    return Math.floor(x * exp) / exp;
  },

  ceilToPrecision: function(x, precision) {
    var exp = Math.pow(10, precision);
    return Math.ceil(x * exp) / exp;
  },

  niceNumber: function(x, round) {
    var exp = Math.floor(Math.LOG10E * Math.log(x)), // exponent of x
        p, f, nf;

    // Fix for inaccuracies calculating negative powers
    if (exp < 0) {
      p = parseFloat(Math.pow(10, exp).toFixed(Math.abs(exp)));
    } else {
      p = Math.pow(10, exp);
    }
    f = x / p

    if (round) {
      if (f < 1.5) {
        nf = 1;
      } else if (f < 3) {
        nf = 2;
      } else if (f < 7) {
        nf = 5;
      } else {
        nf = 10;
      }
    } else {
      if (f <= 1) {
        nf = 1;
      } else if (f <= 2) {
        nf = 2;
      } else if (f <= 5) {
        nf = 5;
      } else {
        nf = 10;
      }
    }

    return nf * p;
  },

  roundToOrigin: function (value, offset) {
    var rounded_value = value,
        multiplier;

    offset = offset || 1;
    multiplier = Math.pow(10, -offset);
    rounded_value = Math.round(value * multiplier) / multiplier;
    return (rounded_value > this.min) ? this.roundToOrigin(value - this.step) : rounded_value;
  }
});

Grafico.BaseGraph = Class.create(Grafico.Base, {
  initialize: function (element, data, options) {
    this.options = {
      width:                  parseInt(element.getStyle('width'), 10),
      height:                 parseInt(element.getStyle('height'), 10),
      grid:                   true,
      show_vertical_grid:     true,
      show_horizontal_grid:   true,
      plot_padding:           10,                                   // Padding for the graph line/bar plots
      font_size:              10,                                   // Label font size
      show_horizontal_labels: true,
      show_vertical_labels:   true,
      show_ticks:             true,
      vertical_label_unit:    '',
      background_color:       'none',
	  /*background_color:      '#fff',*/
      label_color:           '#000',                               // Label text color
      grid_color:            '#ccc',                               // Grid line color
      hover_text_color:      '#fff',                               // hover color
      markers:                false,                                // false, circle, value
      marker_size:            5,
      meanline:               false,
      padding_top:            20,
      draw_axis:              true,
      datalabels:             '',                                    // interactive, filled with same # of elements as graph items.
      hover_color:            '',                                    // hover color if there are datalabels
      watermark:              false,
      watermark_location:     false,                                 // determine position of watermark. currently available is bottomright and middle
      hide_empty_label_grid:  false,                                 // hide gridlines for labels with no value
      left_padding:           false,                                  // set a standard leftpadding regardless of label width
      label_rotation:         0,
      label_max_size:         false,
      focus_hint:             true,
      min:                    0,
      max:                    null
    };

    Object.extend(this.options, this.chartDefaults() || { });
    Object.extend(this.options, options || { });

    this.element = element;
    this.data_sets = Object.isArray(data) ? new Hash({ one: data }) : $H(data);
    if (this.chartDefaults().stacked === true) {
      this.real_data = this.deepCopy(this.data_sets);
      this.data_sets = this.stackData(this.data_sets);
    }

    this.flat_data = this.data_sets.collect(function (data_set) { return data_set[1]; }).flatten();
    if (this.hasBaseLine()) {
      this.flat_data.push(this.base_line);
      this.flat_data = this.flat_data.flatten();
    }
    this.normaliser = new Grafico.Normaliser(this.flat_data, this.normaliserOptions());
    this.label_step = this.normaliser.step;
    this.range = this.normaliser.range;
    this.start_value = this.normaliser.start_value;
    this.zero_value = this.normaliser.zero_value;
    this.data_size = this.longestDataSetLength();

    /* If one color is specified, map it to a compatible set */
    if (options && options.color) {
      options.colors = {};
      this.data_sets.keys().each(function (key) {
        options.colors[key] = options.color;
      });
    }

    /* overwrite some defaults and then add the options AGAIN */
    this.options.colors = this.makeRandomColors();
    this.options.labels = $A($R(1, this.data_size));

    /* add the options again, because some defaults (labels, colors) could not be
     * generated withouth first knowing the user options (which is kind of a
     * chicken-and-egg problem)
     */
    Object.extend(this.options, this.chartDefaults() || { });
    Object.extend(this.options, options || { });

    /* Padding around the graph area to make room for labels */
    this.x_padding_left = 10 + this.paddingLeftOffset();
    this.x_padding_left += this.options.vertical_label_unit ? 6 : 0;
    this.x_padding_left = this.options.left_padding ? this.options.left_padding : this.x_padding_left;
    this.x_padding_right = 20;
    this.x_padding = this.x_padding_left + this.x_padding_right;
    this.y_padding_top = this.options.padding_top;
    this.y_padding_bottom = 20 + this.paddingBottomOffset();
    this.y_padding = this.y_padding_top + this.y_padding_bottom;

    this.graph_width = this.options.width - this.x_padding;
    this.graph_height = this.options.height - this.y_padding;

    this.step = this.calculateStep();

    /* Calculate how many labels are required */
    this.y_label_count = (this.range / this.label_step).round();
    if (isNaN(this.y_label_count)) {
      this.y_label_count = 1;
      this.options.show_vertical_labels = false;
    }

    if ((this.normaliser.min + (this.y_label_count * this.normaliser.step)) < this.normaliser.max) {
      this.y_label_count += 1;
    }

    this.value_labels = this.makeValueLabels(this.y_label_count);

    this.top_value = this.value_labels.last();
    /* Grid control options */
    this.grid_start_offset = -1;

    /* Drawing */
    this.paper = new Raphael(this.element, this.options.width, this.options.height);
    this.background = this.paper.rect(this.x_padding_left, this.y_padding_top, this.graph_width, this.graph_height);
    this.background.attr({fill: this.options.background_color, stroke: 'none' });
    this.options.meanline = (this.options.meanline === true) ? { 'stroke-width': '2px', stroke: '#BBBBBB' } : this.options.meanline;

    /* global Sets */
    this.globalMarkerSet = this.paper.set();
    this.globalHoverSet = this.paper.set();
    this.globalBlockSet = this.paper.set();
    this.globalAreaLineSet = this.paper.set();

    this.setChartSpecificOptions();
    this.draw();

    this.globalAreaLineSet.toFront();
    this.globalMarkerSet.toFront();
    this.globalHoverSet.toFront();
    this.globalBlockSet.toFront();
  },

  normaliserOptions: function () {
    return {graph_height : parseInt(this.element.getStyle('height'), 10)};
  },

  hasBaseLine: function () {
    return false;
  },

  getNormalizedBaseLine: function () {
    if (this.normalized_base_line == undefined) {
      this.normalized_base_line = this.normaliseData(this.base_line);
    }
    return this.normalized_base_line;
  },

  getNormalizedRealData: function () {
    if (this.normalized_real_data == undefined) {
      this.normalized_real_data = this.real_data.collect(function(data) { return this.normaliseData(data[1]); }.bind(this));
    }
    return this.normalized_real_data;
  },

  chartDefaults: function () {
    /* Define in child class */
  },

  drawPlot: function (index, cursor, x, y, color, coords, datalabel, element, graphindex) {
    /* Define in child class */
  },

  calculateStep: function () {
    /* Define in child classes */
  },

  getMousePos: function (e) {
    var posx = 0,
        posy = 0,
        mousepos;
    if (!e) {
      e = window.event;
    }
    if (e.pageX || e.pageY) {
      posx = e.pageX;
      posy = e.pageY;
    }
    else if (e.clientX || e.clientY) {
      posx = e.clientX + document.body.scrollLeft - document.documentElement.scrollLeft;
      posy = e.clientY + document.body.scrollTop - document.documentElement.scrollTop;
    }
    mousepos = {x : posx , y : posy};
    return mousepos;
  },

  makeRandomColors: function (number) {
    var colors = {};
    var step = 1/this.data_sets.size();
    var hue = Math.random();
    this.data_sets.each(function (data) {
      colors[data[0]] = Raphael.hsb2rgb(hue, 0.85, 0.75).hex;
      hue = (hue + step)%1;
    });
    return colors;
  },

  longestDataSetLength: function () {
    var length = 0;
    this.data_sets.each(function (data_set) {
      length = data_set[1].length > length ? data_set[1].length : length;
    });
    return length;
  },

  roundValue: function (value, length) {
    var multiplier = Math.pow(10, length);
    value *= multiplier;
    value = Math.round(value) / multiplier;
    return value;
  },

  roundValues: function (data, length) {
    return $A(data).collect(function (value) { return this.roundValue(value, length); }.bind(this));
  },

  paddingLeftOffset: function () {
    var result = 0;
    if (this.options.show_vertical_labels) {
      /* Find the longest label and multiply it by the font size */
      var data = this.flat_data,
           longest_label_length;

        // Round values
      data = this.roundValues(data, 2);
      longest_label_length = $A(data).max(function (value,i) { return value.toString().length; });
      longest_label_length = longest_label_length > 2 ? longest_label_length - 1 : longest_label_length;
      result = longest_label_length * this.options.font_size;
    }
    return result;
  },

  paddingBottomOffset: function () {
    /* height of the text */
    return this.options.font_size;
  },

  normalise: function (value) {
    var total = this.start_value === 0 ? this.top_value : this.range;
    if (total === 0) {total = 1;}
    return ((value / total) * this.graph_height);
  },

  draw: function () {
    if (this.options.grid) {
      this.drawGrid();
    }
    if (this.options.watermark) {
      this.drawWatermark();
    }

    if (this.options.show_vertical_labels) {
      this.drawVerticalLabels();
    }

    if (this.options.show_horizontal_labels) {
      this.drawHorizontalLabels();
    }

    if (!this.options.watermark) {
      this.drawLinesInit(this);
    }

    if (this.options.draw_axis) {
      this.drawAxis();
    }

    if (this.start_value !== 0 && this.options.focus_hint) {
      this.drawFocusHint();
    }

    if (this.options.meanline) {
      this.drawMeanLine(this.normaliseData(this.flat_data));
    }
  },

  drawLinesInit: function (thisgraph) {
    thisgraph.data_sets.each(function (data, index) {
      thisgraph.drawLines(data[0], thisgraph.options.colors[data[0]], thisgraph.normaliseData(data[1]), thisgraph.options.datalabels[data[0]], thisgraph.element, index);
    }.bind(thisgraph));
  },

  drawWatermark: function () {
    var watermark = this.options.watermark,
        watermarkimg = new Image(),
        thisgraph = this;

    watermarkimg.onload = function (){
      var right, bottom, image;
      if (thisgraph.options.watermark_location === "middle") {
        right = (thisgraph.graph_width - watermarkimg.width) / 2 + thisgraph.x_padding_left;
        bottom = (thisgraph.graph_height - watermarkimg.height) / 2 + thisgraph.y_padding_top;
      } else {
        right = thisgraph.graph_width - watermarkimg.width + thisgraph.x_padding_left - 2;
        bottom = thisgraph.graph_height - watermarkimg.height + thisgraph.y_padding_top - 2;
      }
      image = thisgraph.paper.image(watermarkimg.src, right, bottom, watermarkimg.width, watermarkimg.height).attr({'opacity': '0.4'});

      thisgraph.drawLinesInit(thisgraph, thisgraph.data);

      if (thisgraph.options.stacked_fill||thisgraph.options.area) {
        image.toFront();
      }
    };
    watermarkimg.src = watermark.src || watermark;
  },

  drawGrid: function () {
    var path = this.paper.path().attr({ stroke: this.options.grid_color}),
        y, x, x_labels, i;

    if (this.options.show_horizontal_grid) {
      y = this.graph_height + this.y_padding_top;
      for (i = 0; i < this.y_label_count + 1; i++) {
        path.moveTo(this.x_padding_left - 0.5, parseInt(y, 10) + 0.5);
        path.lineTo(this.x_padding_left + this.graph_width - 0.5, parseInt(y, 10) + 0.5);
        y = y - (this.graph_height / this.y_label_count);
      }
    }
    if (this.options.show_vertical_grid) {
      x = this.x_padding_left + this.options.plot_padding + this.grid_start_offset;
      x_labels = this.options.labels.length;

      for (i = 0; i < x_labels; i++) {
        if ((this.options.hide_empty_label_grid === true && this.options.labels[i] !== "") || this.options.hide_empty_label_grid === false) {
          path.moveTo(parseInt(x, 10), this.y_padding_top);
          path.lineTo(parseInt(x, 10), this.y_padding_top + this.graph_height);
        }
        x = x + this.step;
      }
    }
  },

  drawLines: function (label, color, data, datalabel, element, graphindex) {
    var coords = this.calculateCoords(data),
        y_offset = (this.graph_height + this.y_padding_top),
        cursor,
        cursor2,
        odd_horizontal_offset,
        rel_opacity;

    if (this.options.start_at_zero === false) {
      odd_horizontal_offset = 0;
      $A(coords).each(function (coord, index) {
        if (coord[1] === y_offset) {odd_horizontal_offset++;}
      });
      this.options.odd_horizontal_offset = odd_horizontal_offset;

      if (this.options.odd_horizontal_offset > 1) {
        coords.splice(0, this.options.odd_horizontal_offset);
      }
    }

    if (this.options.stacked_fill || this.options.area) {
      if (this.options.area) {
        rel_opacity = this.options.area_opacity ? this.options.area_opacity : 1.5 / this.data_sets.collect(function (data_set) { return data_set.length; }).length;
        cursor = this.paper.path().attr({stroke: color, fill: color, 'stroke-width': '0', opacity: rel_opacity, 'stroke-opacity': 0});
      } else {
        cursor = this.paper.path().attr({stroke: color, fill: color, 'stroke-width': '0'});
      }

      /* add first and last to fill the area */
      if (!this.hasBaseLine()) {
        coords.unshift([coords[0][0] , y_offset]);
        coords.push([coords[coords.length-1][0] , y_offset]);
      }
    } else {
      cursor = this.paper.path().attr({stroke: color, 'stroke-width': this.options.stroke_width + "px"});
    }

    $A(coords).each(function (coord, index) {
      var x = coord[0],
      y = coord[1];
      if (color instanceof Array) {
        var color_index = index % color.length;
        this.drawPlot(index, cursor, x, y, color[color_index], coords, datalabel, element, graphindex);
      } else {
        this.drawPlot(index, cursor, x, y, color, coords, datalabel, element, graphindex);
      }
    }.bind(this));

    if (this.options.area && this.options.stroke_width > 0) {
      cursor2 = this.paper.path().attr({stroke: color, 'stroke-width': this.options.stroke_width + "px"});
      coords.remove(0);
      coords.remove(-1);
      $A(coords).each(function (coord, index) {
          var x = coord[0],
              y = coord[1];
          this.drawPlot(index, cursor2, x, y, color, coords, datalabel, element, graphindex, true);
      }.bind(this));
      this.globalAreaLineSet.push(cursor2);
    }

    if (this.options.datalabels && this.options.draw_hovers) {
      this.drawHover(cursor, datalabel, element, color);
      this.globalHoverSet.toFront();
    }

  },

  calculateCoords: function (data) {
    var x = this.x_padding_left + this.options.plot_padding - this.step,
        y_offset = (this.graph_height + this.y_padding_top) + this.normalise(this.start_value);

    var top = $A(data).collect(function (value) {
      x += this.step;
      return [x, y_offset - value];
    }.bind(this));

    if (!this.hasBaseLine()) {
      return top;
    }

    x += this.step;
    var bottom = this.getNormalizedBaseLine();

    for (var i = bottom.length - 1; i >= 0; i--) {
      x -= this.step;
      top.push([x, y_offset - bottom[i]]);
    }
    return top;
  },

  drawFocusHint: function () {
    var length = 5,
        x = this.x_padding_left + (length / 2) - 1,
        y = this.options.height - this.y_padding_bottom,
        cursor = this.paper.path().attr({stroke: this.options.label_color, 'stroke-width': 2});

    cursor.moveTo(x, y);
    cursor.lineTo(x - length, y - length);
    cursor.moveTo(x, y - length);
    cursor.lineTo(x - length, y - (length * 2));
  },

  drawMeanLine: function (data) {
    var cursor = this.paper.path().attr(this.options.meanline),
        offset = $A(data).inject(0, function (value, sum) { return sum + value; }) / data.length - 0.5;
        offset = this.options.bar ? offset + (this.zero_value * (this.graph_height / this.y_label_count)) : offset;

    cursor.moveTo(this.x_padding_left - 1, this.options.height - this.y_padding_bottom - offset).
           lineTo(this.graph_width + this.x_padding_left, this.options.height - this.y_padding_bottom - offset);
  },

  drawAxis: function () {
    var cursor = this.paper.path().attr({stroke: this.options.label_color});

    //horizontal
    cursor.moveTo(parseInt(this.x_padding_left, 10) - 0.5, this.options.height - parseInt(this.y_padding_bottom, 10) + 0.5);
    cursor.lineTo(parseInt(this.graph_width + this.x_padding_left, 10) - 0.5, this.options.height - parseInt(this.y_padding_bottom, 10) + 0.5);

    //vertical
    cursor.moveTo(parseInt(this.x_padding_left, 10) - 0.5, parseInt(this.options.height - this.y_padding_bottom, 10) + 0.5);
    cursor.lineTo(parseInt(this.x_padding_left, 10) - 0.5, parseInt(this.y_padding_top, 10));
  },

  makeValueLabels: function (steps) {
    var step = this.label_step,
        label = this.start_value,
        labels = [];
    for (var i = 0; i < steps; i++) {
      label = this.roundValue((label + step), 3);
      labels.push(label);
    }
    return labels;
  },

  drawMarkers: function (labels, direction, step, start_offset, font_offsets, extra_font_options) {
  /* Axis label markers */
    function x_offset(value) {
      return value * direction[0];
    }

    function y_offset(value) {
      return value * direction[1];
    }

    /* Start at the origin */
    var x = parseInt(this.x_padding_left, 10) - 0.5 + x_offset(start_offset),
        y = this.options.height - this.y_padding_bottom + y_offset(start_offset),
        cursor = this.paper.path().attr({stroke: this.options.label_color}),
        font_options = {"font": this.options.font_size + 'px "Arial"', stroke: "none", fill: this.options.label_color};

    Object.extend(font_options, extra_font_options || {});

    labels.each(function (label) {
      if (this.options.draw_axis &&
          ((this.options.hide_empty_label_grid === true && label !== "") || this.options.hide_empty_label_grid === false) &&
          this.options.show_ticks) {
        cursor.moveTo(parseInt(x, 10), parseInt(y, 10) + 0.5);
        cursor.lineTo(parseInt(x, 10) + y_offset(5), parseInt(y, 10) + 0.5 + x_offset(5));
      }

      if (label !== "") {
        this.paper.text(x + font_offsets[0], y - 2 - font_offsets[1], label.toString()).attr(font_options);
      }

      x = x + x_offset(step);
      y = y + y_offset(step);
    }.bind(this));
  },

  drawVerticalLabels: function () {
    var y_step = this.graph_height / this.y_label_count;
    var vertical_label_unit = this.options.vertical_label_unit ? " " + this.options.vertical_label_unit : "";
    for (var i = 0; i < this.value_labels.length; i++) {
      this.value_labels[i] += vertical_label_unit;
    }
    this.drawMarkers(this.value_labels, [0, -1], y_step, y_step, [-8, -2], { "text-anchor": 'end' });
  },

  drawHorizontalLabels: function () {
    var extra_options = this.options.label_rotation ? {rotation: this.options.label_rotation, translation: -this.options.font_size + " 0"} : {},
        labels = this.options.labels;

    if (this.options.label_max_size) {
      for (var i = 0; i < labels.length; i++) {
        labels[i] = labels[i].truncate(this.options.label_max_size + 1, "…");
      }
    }

    this.drawMarkers(labels, [1, 0], this.step, this.options.plot_padding, [0, (this.options.font_size + 7) * -1], extra_options);
  },

  drawHover: function(cursor, datalabel, element, color) {
    var thisgraph = this,
        colorattr = (this.options.stacked_fill || this.options.area) ? "fill" : "stroke",
        hover_color = this.options.hover_color || color,
        hoverSet = this.paper.set(),
        textpadding = 4,
        text = this.paper.text(cursor.attrs.x, cursor.attrs.y - (this.options.font_size * 1.5) - textpadding, datalabel).
                          attr({'font-size': this.options.font_size, fill:this.options.hover_text_color,opacity: 1}),
        textbox = text.getBBox(),
        roundRect = this.drawRoundRect(text, textbox, textpadding);

    hoverSet.push(roundRect,text).attr({opacity: 0});
    this.checkHoverPos({rect: roundRect, set: hoverSet});
    this.globalHoverSet.push(hoverSet);

    cursor.hover(function (event) {
      if (colorattr === "fill") {
        cursor.animate({fill : hover_color,stroke : hover_color}, 200);
      } else {
        cursor.animate({stroke : hover_color}, 200);
      }

      var mousepos = thisgraph.getMousePos(event);
      hoverSet[0].attr({
        x:mousepos.x - (textbox.width / 2 ) - textpadding - element.offsetLeft,
        y:mousepos.y - (textbox.height / 2) - (thisgraph.options.font_size * 1.5) - textpadding - element.offsetTop,
        opacity:1});
      hoverSet[1].attr({
        x:mousepos.x - element.offsetLeft,
        y:mousepos.y - (thisgraph.options.font_size * 1.5) - element.offsetTop,
        opacity:1});

      cursor.mousemove(function (event) {
        var mousepos = thisgraph.getMousePos(event);
        hoverSet[0].attr({
          x:mousepos.x - (textbox.width / 2) - textpadding-element.offsetLeft,
          y:mousepos.y - (textbox.height / 2) - (thisgraph.options.font_size * 1.5) - textpadding-element.offsetTop,
          opacity:1});
        hoverSet[1].attr({
          x:mousepos.x-element.offsetLeft,
          y:mousepos.y-(thisgraph.options.font_size*1.5)-element.offsetTop,
          opacity:1});
        thisgraph.checkHoverPos(roundRect,hoverSet);
      });

    }, function (event) {
      if (colorattr === "fill") {
        cursor.animate({fill : color,stroke : color}, 200);
      } else {
        cursor.animate({stroke : color}, 200);
      }
      hoverSet.attr({opacity:0});
    });
  },

  checkHoverPos: function (elements) {
    var diff, rect, rectsize, set, setbox, marker, nib, textpadding;
    if (elements.rect) {
      rect = elements.rect;
      rectsize = rect.getBBox();
    }
    if (elements.set) { set = elements.set; }
    if (elements.marker) { marker = elements.marker;}
    if (elements.nib) {    nib = elements.nib;}
    if (elements.textpadding) { textpadding = elements.textpadding;}

    if (rect && set) {
      /* top */
      if (rect.attrs.y < 0) {
        if (nib && marker) {
          setbox = set.getBBox();
          set.translate(0,     setbox.height + (textpadding * 2));
          marker.translate(0, -setbox.height - (textpadding * 2));
          nib.translate(0,    -rectsize.height - textpadding + 1.5).scale(1, -1);
        } else {
          diff = rect.attrs.y;
          set.translate(0, 1 - diff);
        }
      }
      /* bottom */
      if ((rect.attrs.y + rectsize.height) > this.options.height) {
        diff = (rect.attrs.y + rectsize.height) - this.options.height;
        set.translate(0, -diff - 1);
        if (marker) { marker.translate(0, diff + 1); }
      }
      /* left */
      if (rect.attrs.x < 0) {
        diff = rect.attrs.x;
        set.translate(-diff + 1, 0);
        if (nib)    { nib.translate(diff - 1, 0); }
        if (marker) { marker.translate(diff - 1, 0); }
      }
      /* right */
      if ((rect.attrs.x + rectsize.width) > this.options.width) {
        diff = (rect.attrs.x + rectsize.width) - this.options.width;
        set.translate(-diff - 1, 0);
        if (nib)    { nib.translate(diff + 1, 0); }
        if (marker) { marker.translate(diff + 1, 0); }
      }
    }
  },

  drawNib: function (text, textbox, textpadding) {
    return this.paper.path()
    .attr({fill: this.options.label_color, opacity: 1, stroke: this.options.label_color, 'stroke-width':'0px'})
    .moveTo(text.attrs.x - textpadding, text.attrs.y + (textbox.height / 2) + textpadding - 1)
    .lineTo(text.attrs.x, text.attrs.y + (textbox.height / 2) + (textpadding * 2))
    .lineTo(text.attrs.x + textpadding, text.attrs.y + (textbox.height / 2) + textpadding - 1)
    .andClose();
  },

  drawRoundRect : function(text, textbox, textpadding) {
    return this.paper.rect(
    text.attrs.x - (textbox.width / 2) - textpadding,
    text.attrs.y - (textbox.height / 2) - textpadding,
    textbox.width + (textpadding * 2),
    textbox.height + (textpadding * 2),
    textpadding * 1.5).attr({fill: this.options.label_color,opacity: 1, stroke: this.options.label_color, 'stroke-width':'0px'});
  }
});

/* Supporting methods to make dealing with arrays easier */
/* Note that some of this work to reduce framework dependencies */
Array.prototype.sum = function () {
  for (var i = 0, sum = 0; i < this.length; sum += this[i++]) {}
  return sum;
};

if (typeof Array.prototype.max === 'undefined') {
  Array.prototype.max = function () {
    return Math.max.apply({}, this);
  };
}

if (typeof Array.prototype.min === 'undefined') {
  Array.prototype.min = function () {
    return Math.min.apply({}, this);
  };
}

Array.prototype.mean = function () {
  return this.sum() / this.length;
};

Array.prototype.variance = function () {
  var mean = this.mean(),
      variance = 0;
  for (var i = 0; i < this.length; i++) {
    variance += Math.pow(this[i] - mean, 2);
  }
  return variance / (this.length - 1);
};

Array.prototype.standard_deviation = function () {
  return Math.sqrt(this.variance());
};

Array.prototype.remove = function(from, to) {
  var rest = this.slice((to || from) + 1 || this.length);
  this.length = from < 0 ? this.length + from : from;
  return this.push.apply(this, rest);
};

/* Raphael path methods. Supporting methods to make dealing with arrays easier */
Raphael.el.isAbsolute = true;
Raphael.el.absolutely = function () {
  this.isAbsolute = 1;
  return this;
};

Raphael.el.relatively = function () {
  this.isAbsolute = 0;
  return this;
};

Raphael.el.moveTo = function (x, y) {
  this._last = {x: x, y: y};
  return this.attr({path: this.attrs.path + ["m", "M"][+this.isAbsolute] + parseFloat(x) + " " + parseFloat(y)});
};

Raphael.el.lineTo = function (x, y) {
  this._last = {x: x, y: y};
  return this.attr({path: this.attrs.path + ["l", "L"][+this.isAbsolute] + parseFloat(x) + " " + parseFloat(y)});
};

Raphael.el.cplineTo = function (x, y, w) {
  if (x > this._last.x) {
    this.attr({path: this.attrs.path + ["C", this._last.x + w, this._last.y, x - w, y, x, y]});
  }
  else if (x == this._last.x) {
    this.lineTo(x, y);
  }
  else {
    this.attr({path: this.attrs.path + ["C", this._last.x - w, this._last.y, x + w, y, x, y]});
  }
  this._last = {x: x, y: y};
  return this;
};

Raphael.el.andClose = function () {
  return this.attr({path: this.attrs.path + "z"});
};

/**
 * Grafico - SVG graphing library - linegraph, areagraph and stacked areagraph file
 *
 * Copyright (c) 2009 - 2010 Kilian Valkhof (kilianvalkhof.com) - Originally developed by Alex Young (http://alexyoung.org)
 * Visit grafico.kilianvalkhof.com for more information and changelogs.
 * Licensed under the MIT license. http://www.opensource.org/licenses/mit-license.php
 *
 */

Grafico.LineGraph = Class.create(Grafico.BaseGraph, {
  chartDefaults: function () {
    return {
      line: true,
      start_at_zero: true,
      stroke_width: 5,
      curve_amount: 10
    };
  },

  setChartSpecificOptions: function () {
  },

  calculateStep: function () {
    return (this.graph_width - (this.options.plot_padding * 2)) / (this.data_size - 1);
  },

  startPlot: function (cursor, x, y, color) {
    cursor.moveTo(x, y);
  },

  drawPlot: function (index, cursor, x, y, color, coords, datalabel, element, graphindex) {
    if (index === 0) {
      return this.startPlot(cursor, x - 0.5, y, color);
    }

    if (this.options.curve_amount) {
      cursor.cplineTo(x, y, this.options.curve_amount);
    } else {
      cursor.lineTo(x, y);
    }

    if (this.options.markers === 'circle') {
      this.drawGraphMarkers(index, x, y, color, datalabel, element);
    } else if (this.options.markers === 'value') {
      this.drawGraphValueMarkers(index, x, y, color, datalabel, element, graphindex);
    }
  },

  drawGraphMarkers: function (index, x, y, color, datalabel, element) {
    var circle = this.paper.circle(x, y, this.options.marker_size),
        old_marker_size = this.options.marker_size,
        color2 = this.options.hover_color || color,
        new_marker_size = parseInt(1.7 * old_marker_size, 10);

    circle.attr({ 'stroke-width': '1px', stroke: this.options.background_color, fill: color });
    this.globalMarkerSet.push(circle);

    circle.hover(function (event) {
      circle.animate({r: new_marker_size, fill: color2}, 200);
    }, function (event) {
      circle.animate({r: old_marker_size, fill: color}, 200);
    });
  },

  drawGraphValueMarkers: function (index, x, y, color, datalabel, element, graphindex) {
    index += this.options.odd_horizontal_offset>1 ? this.options.odd_horizontal_offset : 0;
    index -= this.options.stacked_fill || this.options.area ? 1 : 0;

    var currentset = this.options.stacked ? this.real_data : this.data_sets,
        currentvalue = currentset.collect(function (data_set) { return data_set[1][index]; })[graphindex];

    if (currentvalue) {
      currentvalue = "" + currentvalue.toString().split('.');
      if (currentvalue[1]) {
        currentvalue[1] = currentvalue[1].truncate(3, '');
      }
    }
    if (
      (this.options.line||this.options.stacked) || //if the option is a line graph
      ((this.options.stacked_fill||this.options.area) && index != -1) && //if it's stacked or an area and it's not the first
      typeof currentvalue != "undefined") { //if there is a current value

      var rectx = x-(this.step/2),
          recty = this.options.stacked ? y-(this.graph_height/18): y-(this.graph_height/6),
          rectw = this.step,
          recth = this.options.stacked ? this.graph_height/9     : this.graph_height/3,
          circle = this.paper.circle(x, y, this.options.marker_size).attr({ 'stroke-width': '1px', stroke: this.options.background_color, fill: color,opacity:0}),
          block = this.paper.rect(rectx, recty, rectw, recth).attr({fill:color, 'stroke-width': 0, stroke : color,opacity:0});

      if (this.options.datalabels) {
        datalabel = datalabel + ": " + currentvalue;
      } else {
        datalabel = "" + currentvalue;
      }
      datalabel += this.options.vertical_label_unit ? " " + this.options.vertical_label_unit : "";

      var hoverSet = this.paper.set(),
          textpadding = 4,
          text = this.paper.text(circle.attrs.cx, circle.attrs.cy - (this.options.font_size * 1.5) -2 * textpadding, datalabel).attr({'font-size': this.options.font_size, fill:this.options.hover_text_color, opacity: 1}),
          textbox = text.getBBox(),
          roundRect= this.drawRoundRect(text, textbox, textpadding),
          nib = this.drawNib(text, textbox, textpadding);

      hoverSet.push(circle,roundRect,nib,text).attr({opacity:0});
      this.checkHoverPos({rect:roundRect,set:hoverSet,marker:circle,nib:nib,textpadding:textpadding});
      this.globalHoverSet.push(hoverSet);
      this.globalBlockSet.push(block);

      block.hover(function (event) {
        hoverSet.animate({opacity:1},200);
      }, function (event) {
        hoverSet.animate({opacity:0},200);
      });
    }
  }
});

Grafico.AreaGraph = Class.create(Grafico.LineGraph, {
  chartDefaults: function () {
    return {
      area: true,
      area_opacity: false,
      stroke_width: 0,
      curve_amount: 10
    };
  },

  drawPlot: function (index, cursor, x, y, color, coords, datalabel, element, graphindex, dontdraw) {
    var filltype = this.options.area || this.options.stacked_fill;

    if (!dontdraw) {
      if (filltype === true) {
        if (index !== 0 && index !== coords.length-1) {
          if (this.options.markers === 'circle') {
            this.drawGraphMarkers(index, x, y, color, datalabel, element);
          } else if (this.options.markers === 'value') {
            this.drawGraphValueMarkers(index, x, y, color, datalabel, element, graphindex);
          }
        }
      } else {
        if (this.options.markers === 'circle') {
          this.drawGraphMarkers(index, x, y, color, datalabel, element);
        } else if (this.options.markers === 'value') {
          this.drawGraphValueMarkers(index, x, y, color, datalabel, element, graphindex);
        }
      }
    }
    x -= 0.5;
    if (index === 0) {
      return this.startPlot(cursor, x, y, color);
    }

    if (this.options.curve_amount && index > 1 && (index < coords.length - 1)) {
      cursor.cplineTo(x, y, this.options.curve_amount);
    } else if (this.options.curve_amount && !filltype && (index = 1 || (index = coords.length - 1))) {
      cursor.cplineTo(x, y, this.options.curve_amount);
    } else {
      cursor.lineTo(x, y);
    }
  }
});

Grafico.StackGraph = Class.create(Grafico.AreaGraph, {
  chartDefaults: function () {
    return {
      stacked: true,
      stacked_fill: true,
      stroke_width: 0,
      curve_amount: 10
    };
  },

  setChartSpecificOptions: function () {
    if (!this.options.stacked_fill) {
      this.options.stroke_width = 5;
    }
  },

  stackData: function (stacked_data) {
    // apparently the arrays are pointers
    // which is the only reason this actually works
    var stacked_data_array = stacked_data.collect(
      function (data_set) {
        return data_set[1];
      });

    for (var i = stacked_data_array.length - 2; i >= 0; i--) {
      for (var j = 0; j < stacked_data_array[0].length; j++) {
        stacked_data_array[i][j] += stacked_data_array[i + 1][j];
      }
    }
    return stacked_data;
  }
});

Grafico.StreamGraph = Class.create(Grafico.StackGraph, {
  chartDefaults: function () {
    return {
      stacked: true,
      stacked_fill: true,
      stroke_width: 5,
      grid: false,
      draw_axis: false,
      show_horizontal_labels: false,
      show_vertical_labels:   false,
      stream: true,
      stream_line_smoothing:  false, // false, simple, weighted
      stream_smart_insertion: false,
      curve_amount: 4,
      stream_label_threshold: 0
    };
  },

  hasBaseLine: function() {
    return true;
  },

  calcBaseLine: function (stacked_data) {
    var base_line_data = stacked_data.collect(
      function (data_set) {
        return data_set[1];
      });

    var base_line = [], i, j;
    for (j = 0; j < base_line_data[0].length; j++) {
      sum = 0;
      for (i = 0; i < base_line_data.length; i++) {
        if (this.options.stream_line_smoothing == false) {
          sum += base_line_data[i][j];
        } else {
          sum += (i + 1) * base_line_data[i][j];
        }
      }

      if (this.options.stream_line_smoothing == false) {
        base_line[j] = -sum / 2;
      } else {
        base_line[j] = -sum / (base_line_data.length + 1);
      }
    }

    var base_line_min = base_line.min();
    for (i = 0; i < base_line.length; i++) {
      base_line[i] -= base_line_min;
    }

    this.base_line = base_line;
  },

  stackData: function (stacked_data) {
    var i,j;
    
    if (this.options.stream_smart_insertion) {
      stacked_data = $A(stacked_data);
      stacked_data.each(function(data_set) {
        i = 0;
        while (i < data_set[1].length && data_set[1][i] <= 0.0000001) {
          i++;
        }
        data_set[2] = i;
      });

      var sorted_data = stacked_data.sortBy(function(data_set) {
        return data_set[2];
      });

      var final_data = [];
      var bottom = false;
      sorted_data.each(function(data_set) {
        if (bottom) {
          final_data.push(data_set);
        } else {
          final_data.unshift(data_set);
        }
        bottom = !bottom;
      });

      stacked_data = $H();
      final_data.each(function(data_set) {
        stacked_data.set(data_set[0], data_set[1]);
      });
    }

    this.real_data = this.deepCopy(stacked_data);
    this.calcBaseLine(stacked_data);

    var stacked_data_array = stacked_data.collect(
      function (data_set) {
        return data_set[1];
      });

    for (j = 0; j < stacked_data_array[0].length; j++) {
      stacked_data_array[stacked_data_array.length - 1][j] += this.base_line[j];
    }

    for (i = stacked_data_array.length - 2; i >= 0; i--) {
      for (j = 0; j < stacked_data_array[0].length; j++) {
        stacked_data_array[i][j] += stacked_data_array[i + 1][j];
      }
    }

    return stacked_data;
  },

  drawPlot: function (index, cursor, x, y, color, coords, datalabel, element, graphindex, dontdraw) {
    if (this.options.datalabels && !dontdraw) {
      var real_data = this.getNormalizedRealData();
      var best_positions = this.bestMarkerPositions();

      if (index < coords.length/2) {
        if (best_positions[graphindex] >= index && best_positions[graphindex] < index + 1) {
          this.drawStreamMarker(index + 1, x + (best_positions[graphindex] - index) * this.step,
              y + real_data[graphindex][index] / 2, color, datalabel, element);
        }
      }
    }

    x -= 0.5;
    if (index === 0) {
      return this.startPlot(cursor, x, y, color);
    }

    cursor.cplineTo(x, y, this.options.curve_amount);
  },

  bestMarkerPositions: function () {
    if (this.best_marker_positions == undefined) {
      this.best_marker_positions = this.real_data.collect(function (data) {
        var best_index = -1, best_value = 0, streak_counter = 0;
        for (var i = 0; i < data[1].length; i++) {
          var value = data[1][i];
          if (value > best_value && value >= this.options.stream_label_threshold) {
            best_value = value;
            best_index = i;
            streak_counter = 0;
          }
          else if (value == best_value && best_value > 0) {
            streak_counter++;
          }
        }
        return best_index + streak_counter / 2;
      }.bind(this));
    }
    return this.best_marker_positions;
  },

  drawStreamMarker: function (index, x, y, color, datalabel, element, graphindex) {
    if (this.options.datalabels) {
      var hoverSet = this.paper.set(),
          textpadding = 4,
          text = this.paper.text(x, y - 2 * textpadding + this.options.font_size / 2, datalabel).attr(
      {'font-size': this.options.font_size,
        fill: this.options.hover_text_color,
        opacity: 1}),
          textbox = text.getBBox(),
          roundRect = this.drawRoundRect(text, textbox, textpadding);

      hoverSet.push(roundRect,text);
      this.checkHoverPos({rect: roundRect, set: hoverSet, textpadding: textpadding});
      this.globalHoverSet.push(hoverSet);
    }
  }
});

/**
 * Grafico - SVG graphing library - bargraph and horizontal bar graph file
 *
 * Copyright (c) 2009 - 2010 Kilian Valkhof (kilianvalkhof.com) - Originally developed by Alex Young (http://alexyoung.org)
 * Visit grafico.kilianvalkhof.com for more information and changelogs.
 * Licensed under the MIT license. http://www.opensource.org/licenses/mit-license.php
 *
 */

"use strict";
Grafico.BarGraph = Class.create(Grafico.BaseGraph, {
  chartDefaults: function () {
    return {
      bar : true,
      plot_padding : 0,
      bargraph_lastcolor : false,
      bargraph_negativecolor : false
    };
  },

  normaliserOptions: function () {
    return {
      start_value : 0,
      bar : true
    };
  },

  setChartSpecificOptions: function () {
    this.bar_padding = 5;
    this.bar_width = this.calculateBarWidth();
    this.options.plot_padding = (this.bar_width / 2);
    this.step = this.calculateStep();
    this.grid_start_offset = this.bar_padding - 1;
  },

  calculateBarWidth: function () {
    return (this.graph_width / this.data_size) - this.bar_padding;
  },

  calculateStep: function () {
    this.data_size = this.data_size === 1 ? 2 : this.data_size;
    return (this.graph_width - (this.options.plot_padding * 2) - (this.bar_padding * 2)) / (this.data_size - 1);
  },

  drawPlot: function (index, cursor, x, y, color, coords, datalabel, element) {
    var start_y = this.options.height - this.y_padding_bottom - (this.zero_value * (this.graph_height / this.y_label_count)),
        lastcolor = this.options.bargraph_lastcolor,
        negativecolor = this.options.bargraph_negativecolor || color,
        color2;

    x = x + this.bar_padding;
    y = this.options.height - this.y_padding_bottom - y - (this.zero_value * (this.graph_height / this.y_label_count));

    if (lastcolor && index === coords.length-1){
      color2 = lastcolor;
    } else {
      color2 = y < 0 ? negativecolor : color;
    }

    var bargraph = this.paper.rect(x - (this.bar_width / 2), start_y, this.bar_width, y).attr({fill: color2, 'stroke-width': this.options.stroke_width, stroke : color2, 'stroke-opacity' : 0});

    if (y < 0) {
      bargraph.attr({height:-bargraph.attrs.height});
    } else {
      bargraph.translate(0, -y);
    }

    if (this.options.datalabels) {
      this.drawGraphValueMarkers(x, index, bargraph, datalabel, color2);
    }

    x = x + this.step;
    this.options.count++;
  },

  drawHorizontalLabels: function () {
    /* Change the standard options to correctly offset against the bars */
    var x_start = this.bar_padding + this.options.plot_padding,
        extra_options = this.options.label_rotation ? {rotation:this.options.label_rotation, translation: -this.options.font_size + " 0"} : {},
        labels = this.options.labels;

    if (this.options.label_max_size) {
      for (var i = 0; i < labels.length; i++) {
         labels[i] = labels[i].truncate(this.options.label_max_size + 1, "…");
      }
    }

    this.drawMarkers(this.options.labels, [1, 0], this.step, x_start, [0, (this.options.font_size + 7) * -1], extra_options);
  },

  drawGrid: function () {
    var path = this.paper.path().attr({ stroke: this.options.grid_color, 'stroke-width': 1}),
        y = this.graph_height + this.y_padding_top,
        x, x_labels, x_step;

    if (!this.options.horizontalbar) {
      if (this.options.show_horizontal_grid) {
        for (var i = 0; i < this.y_label_count+1; i++) {
          path.moveTo(this.x_padding_left - 0.5, parseInt(y, 10) + 0.5);
          path.lineTo(this.x_padding_left + this.graph_width - 0.5, parseInt(y, 10) + 0.5);
          y = y - (this.graph_height / this.y_label_count);
        }
      }
    } else {
      path.moveTo(this.x_padding_left - 0.5, parseInt(y, 10) + 0.5);
      path.lineTo(this.x_padding_left + this.graph_width - 0.5, parseInt(y, 10) + 0.5);

      y -= this.graph_height;
      path.moveTo(this.x_padding_left - 0.5, parseInt(y, 10) + 0.5);
      path.lineTo(this.x_padding_left + this.graph_width - 0.5, parseInt(y, 10) + 0.5);
    }

    if (this.options.horizontalbar) {
      x = this.x_padding_left + this.options.plot_padding + this.grid_start_offset;
      x_labels = this.y_label_count;
      x_step = this.options.horizontalbar ? this.graph_width / this.y_label_count : this.step;

      for (i = 0; i < x_labels; i++) {
        if ((this.options.hide_empty_label_grid === true && this.options.labels[i] !== "") || this.options.hide_empty_label_grid === false) {
          path.moveTo(parseInt(x, 10) + 0.5, this.y_padding_top);
          path.lineTo(parseInt(x, 10) + 0.5, this.y_padding_top + this.graph_height);
        }
        x = x + x_step;
      }
    }
    //left side
    path.moveTo(parseInt(this.x_padding_left, 10) - 0.5, this.y_padding_top);
    path.lineTo(parseInt(this.x_padding_left, 10) - 0.5, this.y_padding_top + this.graph_height);
    //right side
    path.moveTo(parseInt(this.x_padding_left + this.graph_width, 10) - 0.5, this.y_padding_top);
    path.lineTo(parseInt(this.x_padding_left + this.graph_width, 10) - 0.5, this.y_padding_top + this.graph_height);
  },

  drawGraphValueMarkers: function(x, index, bargraph, datalabel, color) {
    var hover_color = this.options.hover_color || color,
        hoverSet = this.paper.set(),
        text,
        hoverbar = this.paper.rect(x - (this.bar_width / 2), this.y_padding_top, this.bar_width, this.options.height);

    datalabel = datalabel ? datalabel[index].toString() : '';
    text = this.paper.text(bargraph.attrs.x + (this.bar_width / 2), bargraph.attrs.y - (this.options.font_size * 1.5), datalabel);
    hoverbar.attr({fill: color, 'stroke-width': 0, stroke : color,opacity:0});
    text.attr({'font-size': this.options.font_size, fill: this.options.hover_text_color, opacity: 1});

    var textbox = text.getBBox(),
        textpadding = 4,
        roundRect= this.drawRoundRect(text, textbox, textpadding),
        nib = this.drawNib(text, textbox, textpadding);

    hoverSet.push(roundRect,nib,text).attr({opacity:0});
    this.checkHoverPos({rect:roundRect,set:hoverSet,nib:nib});
    this.globalHoverSet.push(hoverSet);
    this.globalBlockSet.push(hoverbar);

    if (roundRect.attrs.y < 0) {
      hoverSet.translate(0, 1 + (roundRect.attrs.y * -1));
    }

    hoverbar.hover(function (event) {
      bargraph.animate({fill: hover_color, stroke: hover_color}, 200);
      hoverSet.animate({opacity: 1}, 200);
    }, function (event) {
      bargraph.animate({fill: color,stroke: color}, 200);
      hoverSet.animate({opacity: 0}, 200);
    });
  }
});

Grafico.StackedBarGraph = Class.create(Grafico.BarGraph, {
  chartDefaults: function () {
    return {
      opacity: 100,
      bar : true,
      plot_padding : 0,
      stacked: true,
      stacked_fill: true
    };
  },

  setChartSpecificOptions: function () {
    this.bar_padding = 5;
    this.bar_width = this.calculateBarWidth();
    this.options.plot_padding = (this.bar_width / 2);
    this.step = this.calculateStep();
    this.grid_start_offset = this.bar_padding - 1;
    this.options.stroke_width = 1;
  },

  stackData: function (stacked_data) {
    var stacked_data_array = stacked_data.collect(
      function (data_set) {
        return data_set[1];
      });

    for (var i = stacked_data_array.length - 2; i >= 0; i--) {
      for (var j = 0; j < stacked_data_array[0].length; j++) {
        stacked_data_array[i][j] += stacked_data_array[i + 1][j];
      }
    }
    return stacked_data;
  },

  calculateCoords: function (data) {
    var x = this.x_padding_left + this.options.plot_padding - this.step,
        y_offset = (this.graph_height + this.y_padding_top) + this.normalise(this.start_value);

    var top = $A(data).collect(function (value) {
      x += this.step;
      return [x, y_offset - value];
    }.bind(this));

    x += this.step;
    var bottom = this.getNormalizedBaseLine();

    for (var i = bottom.length - 1; i >= 0; i--) {
      x -= this.step;
      top.push([x, y_offset - bottom[i]]);
    }
    return top;
  },

  drawLines: function (label, color, data, datalabel, element, graphindex) {
    var coords = this.calculateCoords(data),
        y_offset = (this.graph_height + this.y_padding_top),
        cursor,
        cursor2,
        odd_horizontal_offset,
        rel_opacity;

    cursor = this.paper.path().attr({stroke: color, 'stroke-width': 0});

    $A(coords).each(function (coord, index) {
      var x = coord[0],
          y = coord[1];
      this.drawPlot(index, cursor, x, y, color, coords, datalabel, element, graphindex);
    }.bind(this));

    if (this.options.datalabels && this.options.draw_hovers) {
      this.drawHover(cursor, datalabel, element, color);
      this.globalHoverSet.toFront();
    }

  },

  drawPlot: function (index, cursor, x, y, color, coords, datalabel, element, graphindex) {
    var start_y = this.options.height - this.y_padding_bottom - (this.zero_value * (this.graph_height / this.y_label_count)),
        color2 = color;

    x = x + this.bar_padding;
    y = this.options.height - this.y_padding_bottom - y - (this.zero_value * (this.graph_height / this.y_label_count));

    var attributes = {
          fill: color2,
          'stroke-width': 0,
          stroke : this.options.color2,
          opacity: this.options.opacity
        };
    var bargraph = this.paper.rect(x - (this.bar_width / 2), start_y, this.bar_width, y).attr(attributes);

    if (y < 0) {
      bargraph.attr({height: -bargraph.attrs.height});
    } else {
      bargraph.translate(0, -y);
    }

    if (this.options.datalabels) {
      this.drawGraphValueMarkers(x, index, bargraph, datalabel, color2);
    }

    x = x + this.step;
    this.options.count++;
  },

  drawGraphValueMarkers: function(x, index, bargraph, datalabel, color) {
    var hx = x - (this.bar_width / 2),
        hy = bargraph.attrs.y,
        hw = this.bar_width,
        hh = bargraph.attrs.height;

    var hover_color = this.options.hover_color || color,
        hoverSet = this.paper.set(),
        text,
        hoverbar = this.paper.rect(hx, hy, hw, hh);

    datalabel = datalabel && datalabel.length > index ? datalabel[index].toString() : '';
    text = this.paper.text(bargraph.attrs.x + (this.bar_width / 2), bargraph.attrs.y - (this.options.font_size * 1.5), datalabel);
    hoverbar.attr({fill: color, 'stroke-width': 0, stroke : color, opacity:0});
    text.attr({'font-size': this.options.font_size, fill: this.options.hover_text_color, opacity: 1});

    var textbox = text.getBBox(),
        textpadding = 4,
        roundRect= this.drawRoundRect(text, textbox, textpadding),
        nib = this.drawNib(text, textbox, textpadding);

    hoverSet.push(roundRect,nib,text).attr({opacity:0});
    this.checkHoverPos({rect:roundRect,set:hoverSet,nib:nib});
    this.globalHoverSet.push(hoverSet);
    this.globalBlockSet.push(hoverbar);

    if (roundRect.attrs.y < 0) {
      hoverSet.translate(0, 1 + (roundRect.attrs.y * -1));
    }

    hoverbar.hover(function (event) {
      bargraph.animate({fill: hover_color}, 200);
      hoverSet.animate({opacity: 1}, 200);
    }, function (event) {
      bargraph.animate({fill: color}, 200);
      hoverSet.animate({opacity: 0}, 200);
    });
  }
});

Grafico.HorizontalBarGraph = Class.create(Grafico.BarGraph, {
  chartDefaults: function () {
    return {
      bar : true,
      horizontalbar : true,
      plot_padding : 0,
      horizontal_rounded : false,
      bargraph_lastcolor : false
    };
  },

  setChartSpecificOptions: function () {
    // Approximate the width required by the labels
    this.x_padding_left = 20 + this.longestLabel() * (this.options.font_size / 2);
    this.bar_padding = 5;
    this.bar_width = this.calculateBarHeight();
    this.step = this.calculateStep();
    this.graph_width = this.options.width - this.x_padding_right - this.x_padding_left;
  },

  normalise: function (value) {
    var range = this.makeValueLabels(this.y_label_count);
    range = range[range.length-1];
    return ((value / range) * this.graph_width);
  },

  longestLabel: function () {
    return $A(this.options.labels).sort(function (a, b) { return a.toString().length < b.toString().length; }).first().toString().length;
  },

  /* Height */
  calculateBarHeight: function () {
    return (this.graph_height / this.data_size) - this.bar_padding;
  },

  calculateStep: function () {
    return (this.graph_height - (this.options.plot_padding * 2)) / this.data_size;
  },

  drawLines: function (label, color, data, datalabel, element, graphindex) {
    var y = this.y_padding_top + (this.bar_padding / 2) -0.5,
        offset = this.zero_value * (this.graph_width / this.y_label_count),
        x = this.x_padding_left + offset - 0.5,
        lastcolor = this.options.bargraph_lastcolor,
        negativecolor = this.options.bargraph_negativecolor || color;
    this.datalabel = datalabel;

    $A(data).each(function (value, index) {
      var color2,
          horizontal_rounded = this.options.horizontal_rounded ? this.bar_width / 2 : 0,
          bargraph;

      if (lastcolor && index === data.length-1) {
        color2 = lastcolor;
      } else {
        color2 = value < 0 ? negativecolor : color;
      }

      value = value / this.graph_width * (this.graph_width - offset);
      bargraph = this.paper.rect(x, y, value, this.bar_width, horizontal_rounded).attr({fill: color2, 'stroke-width': 0, stroke : color2, 'stroke-opacity' : 0});

      if (value < 0) {
        bargraph.attr({width: -bargraph.attrs.width}).translate(value, 0);
      }

      if (horizontal_rounded) {
        var bargraphset = this.paper.set(),
            bargraph2 = this.paper.rect(x, y, value - this.bar_width/2, this.bar_width).attr({fill: color2, 'stroke-width': 0, stroke : color2, 'stroke-opacity' : 0});

        bargraphset.push(bargraph2, bargraph);

        if (value < 0) {
          bargraph2.attr({width: -bargraph2.attrs.width - this.bar_width}).translate(value + this.bar_width/2, 0);
        }
      }

      if (this.options.datalabels) {
        var hover_color = this.options.hover_color || color2,
            hoverSet = this.paper.set(),
            datalabel = this.datalabel[index].toString(),
            text = this.paper.text(offset + value + this.x_padding_left / 2, bargraph.attrs.y - (this.options.font_size * 1.5), datalabel)
                             .attr({'font-size': this.options.font_size, fill: this.options.hover_text_color, opacity: 1}),
            hoverbar = this.paper.rect(
                         this.x_padding_left,
                         y,
                         this.graph_width,
                         this.bar_width).attr({fill: color2, 'stroke-width': 0, stroke: color2, opacity: 0}
                       ),
            textbox = text.getBBox();

        if (value < 0) { text.translate(textbox.width, 0); }

        var textpadding = 4,
            roundRect = this.drawRoundRect(text, textbox, textpadding),
            nib = this.drawNib(text, textbox, textpadding);

        hoverSet.push(roundRect,nib,text).attr({opacity: 0});
        this.checkHoverPos({rect: roundRect, set: hoverSet, nib: nib});
        this.globalHoverSet.push(hoverSet);
        this.globalBlockSet.push(hoverbar);
        if (roundRect.attrs.y < 0) {
          hoverSet.translate(0, 1 - roundRect.attrs.y);
        }

        hoverbar.hover(function (event) {
          if (horizontal_rounded) {
            bargraphset.animate({fill: hover_color, stroke: hover_color}, 200);
          } else {
            bargraph.animate({fill: hover_color, stroke: over_color}, 200);
          }
          hoverSet.animate({opacity: 1}, 200);
        }, function (event) {
          if (horizontal_rounded) {
            bargraphset.animate({fill: color2, stroke: color2}, 200);
          } else {
            bargraph.animate({fill: color2, stroke: color2}, 200);
          }
          hoverSet.animate({opacity: 0}, 200);
        });
      }
      y = y + this.step;
    }.bind(this));
  },

  /* Horizontal version */
  drawFocusHint: function () {
    var length = 5,
        x = this.x_padding_left + length * 2,
        y = this.options.height - this.y_padding_bottom - length / 2;

    this.paper.path()
      .attr({stroke: this.options.label_color, 'stroke-width': 2})
      .moveTo(x, y)
      .lineTo(x - length, y + length)
      .moveTo(x - length, y)
      .lineTo(x - (length * 2), y + length);
  },

  drawVerticalLabels: function () {
    var y_start = (this.step / 2),
        extra_options = this.options.label_rotation ? {"text-anchor": 'end', rotation:this.options.label_rotation, translation: "0 " + this.options.font_size/2} : {"text-anchor": 'end'},
        labels = this.options.labels;

    if (this.options.label_max_size) {
      for (var i = 0; i < labels.length; i++) {
        labels[i] = labels[i].truncate(this.options.label_max_size + 1, "…");
      }
    }

    this.drawMarkers(this.options.labels.reverse(), [0, -1], this.step, y_start, [-8, -(this.options.font_size / 5)], extra_options);
  },

  drawHorizontalLabels: function () {
    var x_step = this.graph_width / this.y_label_count,
        x_labels = this.makeValueLabels(this.y_label_count);

    if (this.options.vertical_label_unit) {
      for (var i = 0; i < x_labels.length; i++) {
        x_labels[i] += this.options.vertical_label_unit;
      }
    }
    this.drawMarkers(x_labels, [1, 0], x_step, x_step, [0, (this.options.font_size + 7) * -1]);
  },

  drawMeanLine: function (data) {
    var offset = $A(data).inject(0, function (value, sum) { return sum + value; }) / data.length;
        offset = this.options.bar ? offset + (this.zero_value * (this.graph_height / this.y_label_count)) : offset;

    this.paper.path()
      .attr(this.options.meanline)
      .moveTo(this.x_padding_left - 1 + offset, this.y_padding_top).
      lineTo(this.x_padding_left - 1 + offset, this.y_padding_top + this.graph_height);
  }
});