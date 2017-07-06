/**
 * 
 */

var URL = "ws://"+document.location.host+"/TestAlert/server";

var coneccion = null;
conectar();
function conectar(){
    if ("WebSocket" in window){
        coneccion = new WebSocket(URL);

        coneccion.onopen = function(){
            console.log('video: conexion establecida');
        };        
        coneccion.onclose = function(){
            if(coneccion.readyState==3){
                alert("SIN CONEXION");
                return;
            }
            coneccion = null;
        };
        coneccion.onmessage = receivedMessage;
    }
    else{
       console.log("WebSocket NO SUPORT!");
    }
}
function sendMessage(val){
    if(coneccion.readyState!=1){
        alert("NO SE ENVIO MENSAJE, SIN CONEXION");
        return;
    }
    //send
    coneccion.send(val);
}
function receivedMessage(evt){
    switch(Number(evt.data)){

        case -1:console.log("Start Test");return;
        case 0:console.log("End Test");return;
        case 1:alertVisual();return;
        case 2:alertSonora();return;
        case 3:;return;
    }
}

function startTest(){
    sendMessage(-1);
}
function endTest(){
    sendMessage(0);
}

function alertSonora(){
    alert("Alerta Sonora");
}
function alertVisual(){
    alert("Alerta Visual");
}