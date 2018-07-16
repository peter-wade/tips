package com.phfund.aplus.cms.oms.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class HttpUtil {

	private static final Log LOG = LogFactory.getLog(HttpUtil.class);

	private CloseableHttpClient httpclient;

	private RequestConfig requestConfig;

	public HttpUtil(String timeout) {
		int iTime = 0;
		if (StringUtils.isNotBlank(timeout)) {
			iTime = Integer.parseInt(timeout);
		} else {
			iTime = 30 * 1000; // 默认30s
		}

		httpclient = HttpClients.createDefault();
		/*
		 * setConnectTimeout：设置连接超时时间，单位毫秒。 setConnectionRequestTimeout：设置从connect
		 * Manager获取Connection 超时时间，单位毫秒。这个属性是新加的属性，因为目前版本是可以共享连接池的。
		 * setSocketTimeout：请求获取数据的超时时间，单位毫秒。 如果访问一个接口，多少时间内无法返回数据，就直接放弃此次调用。
		 */
		requestConfig = RequestConfig.custom().setConnectTimeout(iTime).setConnectionRequestTimeout(iTime)
				.setSocketTimeout(iTime).build();

		// httpclient = (DefaultHttpClient) HttpClientConnectionManager
		// .getSSLInstance(httpclient);
	}

	public String doGet(String url) {
		String result = "";
		// 创建HttpGet或HttpPost对象，将要请求的URL通过构造方法传入HttpGet或HttpPost对象。
		HttpGet httpRequst = new HttpGet(url);
		httpRequst.setConfig(requestConfig);
		CloseableHttpResponse httpResponse = null;
		// new DefaultHttpClient().execute(HttpUriRequst requst);
		try {
			// 使用DefaultHttpClient类的execute方法发送HTTP GET请求，并返回HttpResponse对象。
			httpResponse = httpclient.execute(httpRequst);// 其中HttpGet是HttpUriRequst的子类
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				HttpEntity httpEntity = httpResponse.getEntity();
				result = EntityUtils.toString(httpEntity);// 取出应答字符串
				// 一般来说都要删除多余的字符
				result.replaceAll("\r", "");// 去掉返回结果中的"\r"字符，否则会在结果字符串后面显示一个小方格
			} else
				httpRequst.abort();

			httpclient.close();
		} catch (ClientProtocolException e) {
			LOG.error(e);
			result = e.getMessage().toString();
		} catch (IOException e) {
			LOG.error(e);
			result = e.getMessage().toString();
		} finally {
			if (httpResponse != null) {
				try {
					httpResponse.close();
				} catch (IOException e) {
					LOG.error(e);
				}
			}
		}
		return result;
	}

	public String doPost(String url, String params) {
		String result = "";
		HttpPost post = new HttpPost(url);// 创建HttpPost对象
		// post.addHeader("Content-Length", String.valueOf(params.length()) );
		post.setConfig(requestConfig);
		CloseableHttpResponse httpResponse = null;
		try {
			HttpEntity httpEntity = null;
			StringEntity s = new StringEntity(params, "utf-8");
			post.setEntity(s);

			httpResponse = httpclient.execute(post);
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				httpEntity = httpResponse.getEntity();
				result = EntityUtils.toString(httpEntity);// 取出应答字符串
			} else {
				// http 请求报错
				httpEntity = httpResponse.getEntity();
				result = EntityUtils.toString(httpEntity);// 取出应答字符串
				result = "{\"respCode\":\"9999\",\"respMsg\":\"" + result + "\"}";
			}

		} catch (UnsupportedEncodingException e) {
			LOG.error(e);
			result = e.getMessage().toString();
		} catch (ClientProtocolException e) {
			LOG.error(e);
			result = e.getMessage().toString();
		} catch (IOException e) {
			LOG.error(e);
			result = e.getMessage().toString();
		} finally {
			if (httpResponse != null) {
				try {
					httpResponse.close();
				} catch (IOException e) {
					LOG.error(e);
				}
			}
			if (httpclient != null) {
				try {
					httpclient.close();
				} catch (IOException e) {
					LOG.error(e);
				}
			}
		}
		return result;
	}

	/**
	 * 模拟浏览器post提交
	 * 
	 * @param url
	 * @return
	 */
	public static HttpPost getPostMethod(String url) {
		HttpPost pmethod = new HttpPost(url); // 设置响应头信息
		pmethod.addHeader("Connection", "keep-alive");
		pmethod.addHeader("Accept", "*/*");
		pmethod.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
		pmethod.addHeader("Host", "api.mch.weixin.qq.com");
		pmethod.addHeader("X-Requested-With", "XMLHttpRequest");
		pmethod.addHeader("Cache-Control", "max-age=0");
		pmethod.addHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.0) ");
		return pmethod;
	}

	/**
	 * 模拟浏览器GET提交
	 * 
	 * @param url
	 * @return
	 */
	public static HttpGet getGetMethod(String url) {
		HttpGet pmethod = new HttpGet(url);
		// 设置响应头信息
		pmethod.addHeader("Connection", "keep-alive");
		pmethod.addHeader("Cache-Control", "max-age=0");
		pmethod.addHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.0) ");
		pmethod.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/;q=0.8");
		return pmethod;
	}

}
