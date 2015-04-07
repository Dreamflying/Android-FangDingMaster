package com.utils.http;

import java.util.ArrayList;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

/**
 * @author liyuan
 * @Description webservice 调用工具类
 * @Email 1522651962@qq.com
 * @date 2014年8月6日下午5:43:17
 */
public class WebServiceUtil {
	/**
	 * 命名空间
	 */
	private static final String NAMESPACE = "";

	/**
	 * WebService调用地址
	 */
	private static String URL = "";
	/**
	 * 调用方法
	 */
	private static final String METHOD_NAME = "";

	/**
	 *SQAP ACTION
	 */
	private static String SOAP_ACTION = "";

	
	
	
	
	/**获取信息
	 *2014年8月6日下午5:53:03
	 * @param key
	 * @param Value
	 * @return
	 */
	public String getWebServiceInfo(ArrayList<String> key,ArrayList<String> Value){
		// 指定WebService的命名空间和调用的方法名  
        SoapObject rpc = new SoapObject(NAMESPACE, METHOD_NAME);  
  
        // 设置需调用WebService接口需要传入的两个参数mobileCode、userId 
        for (int i = 0; i < key.size(); i++) {
        	  rpc.addProperty(key.get(i), Value.get(i));  
		}
      
        // 生成调用WebService方法的SOAP请求信息,并指定SOAP的版本  
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);  
  
        envelope.bodyOut = rpc;  
        // 设置是否调用的是dotNet开发的WebService  
        envelope.dotNet = true;  
        // 等价于envelope.bodyOut = rpc;  
        envelope.setOutputSoapObject(rpc);  
  
        HttpTransportSE transport = new HttpTransportSE(URL);  
        try {  
            // 调用WebService  
            transport.call(SOAP_ACTION, envelope);  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
  
        // 获取返回的数据  
        SoapObject object = (SoapObject) envelope.bodyIn;  
        // 获取返回的结果  
        String result = object.getProperty(0).toString();  
        return result;
        
	}
		
	}
