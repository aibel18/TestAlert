/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import java.io.IOException;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

/**
 *
 * @author abel
 */

@ServerEndpoint("/server")
public class ServerAlert {
    static Queue<Session> usuarios = new ConcurrentLinkedQueue<>();
    
    @OnMessage
    public void message (Session session,String msg) {
        
        //replicando el mensaje a todos los usuarios
        System.out.println("user :" +session.getId() +" msg: "+msg);
        
        int tipo = Integer.valueOf(msg);
        
        switch(tipo){
            case -1: Experiment.starTest(); break;//iniciar test
            case 0: Experiment.endTest();break;//finalizar test
        }
    }

    @OnOpen
    public void openConnection(Session session) {
        usuarios.add(session);
        System.out.println("Coneccion abierta");
    }
    
    @OnClose
    public void closedConnection(Session session) {
        usuarios.remove(session);
        System.out.println( "Coneccion cerrada.");
    }
    
    @OnError
    public void error(Session session, Throwable t) {
        usuarios.remove(session);
        System.out.println( t.toString());
        System.out.println( "Coneccion error");
    }
    //metodo para enviar a todos usuarios conectados
    public static synchronized void enviarTodos(Object msg) {
        try {
            for (Session s : usuarios) {
                if (s.isOpen()) {
                    s.getBasicRemote().sendObject(msg);
                }
            }
        } catch (IOException | EncodeException e) {
            System.out.println( e.toString());
        }   
    }
    //metodo para enviar a todos menos yo
    public static synchronized void enviarOtros(Session session,Object msg) {
        try {
            for (Session s : usuarios) {
                if (s.isOpen() && s.getId()!= session.getId() ) {
                    s.getBasicRemote().sendObject(msg);
                }
            }
        } catch (IOException | EncodeException e) {
            System.out.println( e.toString());
        }   
    }
    
}
