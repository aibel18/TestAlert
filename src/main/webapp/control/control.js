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
            console.log('control: conexion establecida');
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
//message para enviar a server
function sendMessage(val){
    if(coneccion.readyState!=1){
        alert("NO SE ENVIO MENSAJE, SIN CONEXION");
        return;
    }
    //send
    coneccion.send(val);
}
//message enviado por el server
function receivedMessage(evt){
    switch(Number(evt.data)){

        case 1:;return;
        case 2:;return;
        case 3:alertVibratoria();return;
    }
}

function startTest(){    
    sendMessage(-1);
}
function endTest(){
    sendMessage(0);
}

function alertVibratoria(){
    var time  = 1000;
    if (window.navigator && window.navigator.vibrate) {
        navigator.vibrate(time);
        console.log("vibrate!!");
    } else {
        // Not supported
        console.log("vibrate not supported");
    }
}