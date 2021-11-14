/******************************************************************************

Multithreading :
Producer - Consumer Problem
*******************************************************************************/
import java.util.*;
class Producer implements Runnable{
    private final List<Integer>  taskQueue;
    private final int MAX_CAPACITY;
    
    Producer(List<Integer> taskQueue, int size){
        this.taskQueue=taskQueue;
        this.MAX_CAPACITY=size;
    }
    
    @Override
    public void run(){
        int counter=0;
        while(true)
        {
            try{
                
            produce(counter++);
            }
            catch(InterruptedException ie)
            {
                System.out.println("exception in producer thread "+ie);
            }
        }
    }
    
    public  void produce(int i) throws InterruptedException{
        
        synchronized(taskQueue)
        {
            while(taskQueue.size()==MAX_CAPACITY)
            {
                System.out.println("Queue is full "+ Thread.currentThread().getName()+" size is: "+taskQueue.size());
                taskQueue.wait();
            }
            Thread.sleep(1000);
            taskQueue.add(i);
            System.out.println("Produced: "+i);
            taskQueue.notifyAll();
        }
    }
    
}


class Consumer implements Runnable{
    private final List<Integer>  taskQueue;
    private final int MAX_CAPACITY;
    
    Consumer(List<Integer> taskQueue, int size){
        this.taskQueue=taskQueue;
        this.MAX_CAPACITY=size;
    }
    
    @Override
    public void run(){
       
        while(true)
        {
            try{
                
            consume();
            }
            catch(InterruptedException ie)
            {
                System.out.println("exception in consumer thread "+ie);
            }
        }
    }
    
    public  void consume() throws InterruptedException{
        
        synchronized(taskQueue)
        {
            while(taskQueue.isEmpty())
            {
                System.out.println("Queue is empty "+ Thread.currentThread().getName()+" size is: "+taskQueue.size());
                taskQueue.wait();
            }
            Thread.sleep(1000);
            int i=(Integer)taskQueue.remove(0);
            System.out.println("Consumed: "+i);
            taskQueue.notifyAll();
        }
    }
    
}

public class ProducerConsumer
{
	public static void main(String[] args) {
	  List<Integer> taskQueue =new ArrayList<Integer>();
	  int MAX_CAPACITY=5;
	  Thread t1=new Thread(new Producer(taskQueue,MAX_CAPACITY),"Producer");
	  Thread t2=new Thread(new Consumer(taskQueue,MAX_CAPACITY),"Consumer"); 
	  t1.start();
	  t2.start();
	}
}
