package core;

import java.util.Date;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class Experiment {
	
    private static Thread myThread =null;
    private static boolean isTest = false;
    
    @PostConstruct
    public void start() {
    }
    @PreDestroy
    public void end() {
        endTest();
    }
    public static synchronized void starTest() {
    	isTest = true;
    	myThread = new Thread(new Runnable() {

            public void run() {
                ServerAlert.enviarTodos("-1");
                System.out.println("start experiment\t"+new Date());
                while(isTest){

                    try {
                        Thread.sleep(5000);
                        System.out.println("alert vibratoria\t"+new Date());
                        ServerAlert.enviarTodos("3");
                        
                        Thread.sleep(5000);
                        System.out.println("alert sonora\t"+new Date());
                        ServerAlert.enviarTodos("2");
                        
                        Thread.sleep(5000);
                        System.out.println("alert visual\t"+new Date());
                        ServerAlert.enviarTodos("1");
                    }catch(InterruptedException e) {
                        System.out.println(e);
                    }
                }                
            }
        });
    	//myThread.setPriority(Thread.MIN_PRIORITY);
    	//myThread.setDaemon(true);        
        
    	myThread.start();        
    }
    public static synchronized void endTest() {
        isTest = false;
    	if(myThread!=null) {
            try {
                myThread.interrupt();
                myThread.join();                
                System.out.println("end experiment\t"+new Date());
                ServerAlert.enviarTodos("0");
                myThread = null;
            }
            catch (Exception e) {
                System.out.println(e);
            }	
    	}
    }

}
