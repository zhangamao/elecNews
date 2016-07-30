package crawler;

import java.util.LinkedList;

/**
 *  ʵ����һ���򵥵Ķ��У��� LinkDb.java ��ʹ���˴��ࡣ
 *  @author Bob Hu
 */
public class Queue<T> 
{
    private LinkedList<T> queue = new LinkedList<T>();
    
    public void clear()
    {
    	queue.clear();
    }

    public void enQueue(T t) 
    {
        queue.addLast(t);
    }

    public T deQueue() 
    {
        return queue.removeFirst();
    }

    public boolean isQueueEmpty() 
    {
        return queue.isEmpty();
    }

    public boolean contians(T t) 
    {
        return queue.contains(t);
    }

    public boolean empty() 
    {
        return queue.isEmpty();
    }
}
