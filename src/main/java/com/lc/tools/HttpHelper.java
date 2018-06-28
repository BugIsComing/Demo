package com.lc.tools;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.InputStreamRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.lang.StringUtils;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import java.io.*;
import java.nio.charset.Charset;
import java.util.Map;

public class HttpHelper {


	public static String PostSOAP(String url, String content,
                                  Map<String, String> header) {

		String soapMsg = "";
		PostMethod postMethod = null;
		try {
			postMethod = new PostMethod(url);
			byte[] b = content.getBytes("utf-8");
			InputStream stream = new ByteArrayInputStream(b, 0, b.length);
			RequestEntity re = new InputStreamRequestEntity(stream, b.length,
					"application/soap+xml; charset=utf-8");
			stream.close();
			postMethod.setRequestEntity(re);
			if (header != null) {
				for (String key : header.keySet()) {
					postMethod.setRequestHeader(key, header.get(key));
				}
			}
			postMethod
					.setRequestHeader(
							"User-Agent",
							"Mozilla/4.0 (compatible; MSIE 6.0; MS Web Services Client Protocol 4.0.30319.18051)");
			postMethod.setRequestHeader("Content-Type",
					"text/xml; charset=utf-8");
			postMethod.setRequestHeader("SOAPAction", "");

			HttpClient httpClient = new HttpClient();
			int statusCode = httpClient.executeMethod(postMethod);
			if (statusCode == HttpStatus.SC_OK) {
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(
								postMethod.getResponseBodyAsStream(), "UTF-8"));
				StringBuffer stringBuffer = new StringBuffer();
				String str = "";
				while ((str = reader.readLine()) != null) {
					stringBuffer.append(str);
				}
				soapMsg = stringBuffer.toString();
				/*
				 * 报WARN的条件是 ((contentLength == -1) || (contentLength >
				 * limit))，也就是说
				 * ，或者是返回的HTTP头没有指定contentLength，或者是contentLength大于上限（默认是1M）。
				 */
				// soapMsg = postMethod.getResponseBodyAsString();
			}

			return soapMsg;
		} catch (Exception ex) {
			return "";
		} finally {
			if (postMethod != null) {
				postMethod.releaseConnection();
			}
		}
	}

	/**
	 * <把soap字符串格式化为SOAPMessage> <功能详细描述>
	 * 
	 * @param soapString
	 * @return
	 * @throws SOAPException
	 * @throws IOException
	 * @see [类、类#方法、类#成员]
	 */
	public static SOAPMessage formartSoapString(String soapString)
			throws SOAPException, IOException {
		MessageFactory msgFactory;
		msgFactory = MessageFactory.newInstance();
		SOAPMessage reqMsg = msgFactory.createMessage(
				new MimeHeaders(),
				new ByteArrayInputStream(soapString.getBytes(Charset
						.forName("UTF-8"))));
		reqMsg.saveChanges();
		return reqMsg;

	}

	public static String PostRequest(String url, String content,
                                     Map<String, String> header) throws UnsupportedEncodingException,
            IOException {

		String resultMsg = "";
		PostMethod postMethod = null;
		try {
			postMethod = new PostMethod(url);
			byte[] b = content.getBytes("utf-8");
			InputStream stream = new ByteArrayInputStream(b, 0, b.length);
			RequestEntity re = new InputStreamRequestEntity(stream, b.length,
					"application/json; charset=utf-8");
			stream.close();
			postMethod.setRequestEntity(re);
			if (header != null) {
				for (String key : header.keySet()) {
					postMethod.setRequestHeader(key, header.get(key));
				}
			}
			postMethod
					.setRequestHeader(
							"User-Agent",
							"Mozilla/4.0 (compatible; MSIE 6.0; MS Web Services Client Protocol 4.0.30319.18051)");
			postMethod.setRequestHeader("Content-Type",
					"text/json; charset=utf-8");
			postMethod.setRequestHeader("SOAPAction", "");

			HttpClient httpClient = new HttpClient();
			int statusCode = httpClient.executeMethod(postMethod);
			if (statusCode == HttpStatus.SC_OK) {
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(
								postMethod.getResponseBodyAsStream(), "UTF-8"));
				StringBuffer stringBuffer = new StringBuffer();
				String str = "";
				while ((str = reader.readLine()) != null) {
					stringBuffer.append(str);
				}
				resultMsg = stringBuffer.toString();
				/*
				 * 报WARN的条件是 ((contentLength == -1) || (contentLength >
				 * limit))，也就是说
				 * ，或者是返回的HTTP头没有指定contentLength，或者是contentLength大于上限（默认是1M）。
				 */
				// soapMsg = postMethod.getResponseBodyAsString();
			}
			return resultMsg;
		} catch (Exception ex) {
			return "";
		} finally {
			if (postMethod != null) {
				postMethod.releaseConnection();
			}
		}
	}

	public static String Post(String url, String content,
                              Map<String, String> header) throws UnsupportedEncodingException,
            IOException {

		String resultMsg = "";
		PostMethod postMethod = null;
		try {
			postMethod = new PostMethod(url);
			if (!StringUtils.isEmpty(content)) {
				byte[] b = content.getBytes("utf-8");
				InputStream stream = new ByteArrayInputStream(b, 0, b.length);
				RequestEntity re = new InputStreamRequestEntity(stream,
						b.length, "application/json; charset=utf-8");
				stream.close();
				postMethod.setRequestEntity(re);
			}
			if (header != null) {
				for (String key : header.keySet()) {
					postMethod.setRequestHeader(key, header.get(key));
				}
			}

			HttpClient httpClient = new HttpClient();
			int statusCode = httpClient.executeMethod(postMethod);
			if (statusCode == HttpStatus.SC_OK) {
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(
								postMethod.getResponseBodyAsStream(), "UTF-8"));
				StringBuffer stringBuffer = new StringBuffer();
				String str = "";
				while ((str = reader.readLine()) != null) {
					stringBuffer.append(str);
				}
				resultMsg = stringBuffer.toString();
			}
			return resultMsg;
		} catch (Exception ex) {
			return "";
		} finally {
			if (postMethod != null) {
				postMethod.releaseConnection();
			}
		}
	}
}