package com.taojin.controller;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
public class MobileMessageSendController {
	/*发送验证码*/
	@RequestMapping("sendVerifyCode.do")
    public void sendVerifyCode(HttpServletRequest req,HttpServletResponse resp) {
		String phoneNum = req.getParameter("phoneNum");
        String url = "https://api.netease.im/sms/sendcode.action"; // 获取验证码的固定请求路径
        String appKey = "418f73ad895a8036249934f56da841ff";    //    应用的 key
        String appSecret = "2c453eab786f";    // 应用的 密码
        String nonce = Math.floor(Math.random()*1000000)+"";  //    随机数
        String mobile = phoneNum;    //    目标手机号码
        String curTime=String.valueOf((new Date().getTime()/1000L));
        String checkSum = getCheckSum(appSecret, nonce, curTime);    //    得到 发送验证码必须的参数checkSum
        CloseableHttpClient httpclient = HttpClients.createDefault();  
        // 将参数保存到请求头中
        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader("AppKey",appKey);
        httpPost.addHeader("Nonce",nonce);
        httpPost.addHeader("CurTime",curTime);
        httpPost.addHeader("CheckSum",checkSum);
        httpPost.addHeader("Content-Type","application/x-www-form-urlencoded;charset=utf-8");
        
        
        List<NameValuePair> formparams = new ArrayList<NameValuePair>();  
        formparams.add(new BasicNameValuePair("mobile", mobile));  
        UrlEncodedFormEntity uefEntity;  
        try {  
            uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8");  
            httpPost.setEntity(uefEntity);  
            System.out.println("executing request " + httpPost.getURI());  
            CloseableHttpResponse response = httpclient.execute(httpPost);  
            try {  
                HttpEntity entity = response.getEntity();  
                if (entity != null) {   
                	String response_content = EntityUtils.toString(entity, "UTF-8");
                    System.out.println("Response content: " + response_content);
                    resp.getWriter().print(response_content);
                }  
            } finally {  
                response.close();  
            }  
        } catch (ClientProtocolException e) {  
            e.printStackTrace();  
        } catch (UnsupportedEncodingException e1) {  
            e1.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        } finally {  
            // 关闭连接,释放资源    
            try {  
                httpclient.close(); 
            } catch (IOException e) {  
                e.printStackTrace();  
            } 
        }
    }
	/*校验验证码*/
	@RequestMapping("checkVerifyCode.do")
    public void checkVerifyCode(HttpServletRequest req,HttpServletResponse resp) {
		String mobile = req.getParameter("mobile");
		String code = req.getParameter("code");
        String url = "https://api.netease.im/sms/verifycode.action"; // 获取验证码的固定请求路径
        String appKey = "418f73ad895a8036249934f56da841ff";    //    应用的 key
        String appSecret = "2c453eab786f";    // 应用的 密码
        String nonce = Math.floor(Math.random()*1000000)+"";  //    随机数
        String curTime=String.valueOf((new Date().getTime()/1000L));
        String checkSum = getCheckSum(appSecret, nonce, curTime);    //    得到 发送验证码必须的参数checkSum
        CloseableHttpClient httpclient = HttpClients.createDefault();  
        // 将参数保存到请求头中
        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader("AppKey",appKey);
        httpPost.addHeader("Nonce",nonce);
        httpPost.addHeader("CurTime",curTime);
        httpPost.addHeader("CheckSum",checkSum);
        httpPost.addHeader("Content-Type","application/x-www-form-urlencoded;charset=utf-8");
        List<NameValuePair> formparams = new ArrayList<NameValuePair>();  
        formparams.add(new BasicNameValuePair("mobile", mobile));  
        formparams.add(new BasicNameValuePair("code", code));  
        UrlEncodedFormEntity uefEntity;  
        try {  
            uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8");  
            httpPost.setEntity(uefEntity);  
            System.out.println("executing request " + httpPost.getURI());  
            CloseableHttpResponse response = httpclient.execute(httpPost);  
            try {  
                HttpEntity entity = response.getEntity();  
                if (entity != null) {   
                	String response_content = EntityUtils.toString(entity, "UTF-8");
                    System.out.println("Response content: " + response_content);
                    resp.getWriter().print(response_content);
                }  
            } finally {  
                response.close();  
            }  
        } catch (ClientProtocolException e) {  
            e.printStackTrace();  
        } catch (UnsupportedEncodingException e1) {  
            e1.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        } finally {  
            // 关闭连接,释放资源    
            try {  
                httpclient.close(); 
            } catch (IOException e) {  
                e.printStackTrace();  
            } 
        }
    }
    // 计算并获取CheckSum
    public static String getCheckSum(String appSecret, String nonce, String curTime) {
        return encode("sha1", appSecret + nonce + curTime);
    }

    // 计算并获取md5值
    public static String getMD5(String requestBody) {
        return encode("md5", requestBody);
    }

    private static String encode(String algorithm, String value) {
        if (value == null) {
            return null;
        }
        try {
            MessageDigest messageDigest
                    = MessageDigest.getInstance(algorithm);
            messageDigest.update(value.getBytes());
            return getFormattedText(messageDigest.digest());//从二进制转成十六进制
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    private static String getFormattedText(byte[] bytes) {
        int len = bytes.length;
        StringBuilder buf = new StringBuilder(len * 2);
        for (int j = 0; j < len; j++) {
            buf.append(HEX_DIGITS[(bytes[j] >> 4) & 0x0f]);
            buf.append(HEX_DIGITS[bytes[j] & 0x0f]);
        }
        return buf.toString();
    }
    private static final char[] HEX_DIGITS = { '0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
}