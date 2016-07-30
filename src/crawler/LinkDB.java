package crawler;

import java.util.HashSet;
import java.util.Set;

/**
 *  ���������Ѿ����ʵ� url �ʹ���ȡ�� url ���࣬�ṩurl������Ӳ�����
 *  @author Bob Hu
 */
public class LinkDB 
{

    //����һ��set���ϣ�����Ѿ����ʵ�url
    private static Set<String> visitedUrl = new HashSet<String>();
    //����һ�����У���Ŵ����ʵ�url
    private static Queue<String> unVisitedUrl = new Queue<String>();

    public static Queue<String> getUnVisitedUrl() 
    {
        return unVisitedUrl;
    }
    
    public static void addVisitedUrl(String url) 
    {
        visitedUrl.add(url);
    }

    public static void removeVisitedUrl(String url) 
    {
        visitedUrl.remove(url);
    }

    public static String unVisitedUrlDeQueue() 
    {
        return unVisitedUrl.deQueue();
    }

    /**
     * ��url��ӵ������ʵĶ�����
     * @param url
     */
    public static void addUnvisitedUrl(String url) 
    {
    	//��֤url�ǿգ��Ҽ����в������ظ���url
        if ( url != null 
             && !url.trim().equals("") 
        	 && !visitedUrl.contains(url) 
        	 && !unVisitedUrl.contians(url) ) 
        {
            unVisitedUrl.enQueue(url);
        }
    }

    public static int getVisitedUrlNum() 
    {
        return visitedUrl.size();
    }

    public static boolean unVisitedUrlsEmpty() 
    {
        return unVisitedUrl.empty();
    }
    
    public static void clearUnVisitedUrl()
    {
    	unVisitedUrl.clear();
    }
    
    public static void clearVisitedUrl()
    {
    	visitedUrl.clear();
    }
}

