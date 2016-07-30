package com.zz.backend.view;

import java.util.Set;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.log4j.Logger;

import com.zz.utils.BeanFactory;
import com.zz.utils.ChannelsSetConverter;
import com.zz.utils.PropertiesBeanFactory;

public class InitBeanFactoryServlet extends HttpServlet{

	public static final String INIT_FACTORY_NAME = "_my_bean_factory";
	
	private Logger logger = Logger.getLogger(InitBeanFactoryServlet.class);
	
	@Override
	public void init(ServletConfig config) throws ServletException{
		super.init(config);
		BeanFactory factory = null;
		String configLocation = config.getInitParameter("configLocation");
		logger.debug("�õ��������ļ���"+configLocation);
		if(configLocation == null){
			factory = new PropertiesBeanFactory();
		}else{
			factory = new PropertiesBeanFactory(configLocation);
		}
		getServletContext().setAttribute(INIT_FACTORY_NAME, factory);
		logger.info("BeanFactory�Ѿ���ʼ��");
		
		//��ʼ��BeanUtils��ת����
		ConvertUtils.register(new ChannelsSetConverter(), Set.class);
		logger.debug("ChannelsSetConverter�Ѿ���ע�ᣡ");
		
	}
}
