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

        case 1:;return;
        case 2:;return;
        case 3:alertVibratoria();return;
    }
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