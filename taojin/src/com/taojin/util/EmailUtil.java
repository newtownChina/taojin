//package com.taojin.util;
//import java.io.UnsupportedEncodingException;
//import java.util.Date;
//import java.util.List;
//import java.util.Properties;
//import java.util.ResourceBundle;
//import javax.activation.DataHandler;
//import javax.activation.FileDataSource;
//import javax.mail.Authenticator;
//import javax.mail.MessagingException;
//import javax.mail.Multipart;
////import javax.mail.SimpleSendEmail;
//import javax.mail.internet.InternetAddress;
//import javax.mail.internet.MimeBodyPart;
//import javax.mail.internet.MimeMultipart;
//import javax.mail.internet.MimeUtility;
//import org.apache.commons.lang.StringUtils;
///**
// * 带附加功能的邮件发送接口，可以同时发送给多个用户，采用javaMail方式实现
// * 
// * auther zhou
// */
//public class EmailUtil{
//
//	private User getEmailUser(){
//		ResourceBundle bundle = ResourceBundle.getBundle("common");
//		User user = new User(bundle.getString("smtp.qq.com"), bundle.getString("smtp.qq.email"), bundle.getString("smtp.qq.emailpwd"));
//		return user;
//	}
//	//发送邮件
//	public void toemile(String emil,String msg,String msgtitle){
//		ResourceBundle br = ResourceBundle.getBundle("common");
//		String useremil=br.getString("mail.smtp.email");//读取发送人邮箱账号
//		String useremilpwd=br.getString("mail.smtp.emailpwd");//读取发送人邮箱密码
//		//发件人邮箱      发件人密码
//		SimpleSendEmail send = new SimpleSendEmail(useremil,useremilpwd);
//		try {
//			//接受者邮箱   抄送给谁    密送给谁    邮件标题   邮件内容    附件   邮件content type
//			boolean result = send.send(emil, null, null, msgtitle, msg, null,"text/html;charset=utf-8");
//			if (result) {
//				System.out.println("发送成功"); 
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//	public void execute(String title, List<String> sendToMail, String content) throws UnsupportedEncodingException, MessagingException {
//		
////		String title = "titleTest";// 所发送邮件的标题
////		String from = "307354344@qq.com";// 从那里发送
//		//String[] sendTo = sendToMail;// 发送到那里
//		// 邮件的文本内容，可以包含html标记则显示为html页面
//		
//		// 所包含的附件，及附件的重新命名
//		String[] fileNames = {};//"D:\\dd.xls"
//		User user = getEmailUser();
//		// MailSender mailsender = new MailSender();
//		sendmail(title, user.getUsername(), sendToMail, content, fileNames,
//				"text/html;charset=gb2312", user);
//
//	}
//	
//	/**
//	 * 邮件发送接口
//	 * @param subject
//	 * @param from
//	 * @param to
//	 * @param text
//	 * @param filenames
//	 * @param mimeType
//	 * @param smtp
//	 * @throws MessagingException 
//	 * @throws UnsupportedEncodingException 
//	 * @throws Exception
//	 */
//	@SuppressWarnings("all")
//	public static void sendmail(String subject, String from, List<String> to,
//			String text, String[] filenames, String mimeType, User user) throws MessagingException, UnsupportedEncodingException{
//		// ResourceBundle mailProps = ResourceBundle.getBundle("mail");
//		// 可以从配置文件读取相应的参数
//		Properties props = new Properties();
//		
//		//String smtp = "smtp.163.com"; // 设置发送邮件所用到的smtp
//
//		javax.mail.Session mailSession; // 邮件会话对象
//		javax.mail.internet.MimeMessage mimeMsg; // MIME邮件对象
//
//		props = new Properties(); // 获得系统属性对象
//		props.put("mail.smtp.host", user.getHost()); // 设置SMTP主机
//		props.put("mail.smtp.auth", "true"); // 是否到服务器用户名和密码验证
//		props.put("mail.smtp.starttls.enable","true");
//		//props.setProperty("mail.smtp.auth", "true");
////		MyAuthenticator myauth = new MyAuthenticator(from, user.getPassword()); 
////		mailSession = Session.getDefaultInstance(props, myauth);
//		// 到服务器验证发送的用户名和密码是否正确
////		User user = new User(
////				userName, userPasswd);
//		// 设置邮件会话
//		mailSession = javax.mail.Session.getInstance(props,
//				(Authenticator) user);
//		// 设置传输协议
//		javax.mail.Transport transport = null;
//		transport = mailSession.getTransport("smtp");
//		
//		// 设置from、to等信息
//		mimeMsg = new javax.mail.internet.MimeMessage(mailSession);
//		if (!StringUtils.isEmpty(from)) {
//			InternetAddress sentFrom = null;
//			sentFrom = new InternetAddress(from);
//			mimeMsg.setFrom(sentFrom);
//
//		}
//
//		for (int i = 0; i < to.size(); i++) {
//			System.out.println("发送到:" + to.get(i));
//			if(!com.neusoft.framework.common.StringUtils.isEmptyOrNull(to.get(i))){
//				InternetAddress[] sendTo = new InternetAddress[1];
//				sendTo[0] = new InternetAddress(to.get(i));
//				try{
//					sendEmail(mimeMsg, sendTo, subject, text, mimeType, filenames, transport);
//				}catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		}
//		
//	}
//	
//	private static void sendEmail(javax.mail.internet.MimeMessage mimeMsg, InternetAddress[] sendTo, String subject, 
//				String text, String mimeType, String[] filenames, javax.mail.Transport transport) throws MessagingException, UnsupportedEncodingException{
//		MimeBodyPart messageBodyPart1 = new MimeBodyPart();
//		Multipart multipart = new MimeMultipart();// 附件传输格式
//		
//		mimeMsg.setRecipients(javax.mail.internet.MimeMessage.RecipientType.TO, sendTo);
//		mimeMsg.setSubject(subject, "gb2312");
//		messageBodyPart1.setContent(text, mimeType);
//		multipart.addBodyPart(messageBodyPart1);
//
//
//		if(filenames.length > 0) {
//			for (int i = 0; i < filenames.length; i++) {
//				MimeBodyPart messageBodyPart2 = new MimeBodyPart();
//				// 选择出每一个附件名
//				String filename = filenames[i].split(",")[0];
//				System.out.println("附件名：" + filename);
//				String displayname = filenames[i].split(",")[1];
//				// 得到数据源
//				FileDataSource fds = new FileDataSource(filename);
//				// 得到附件本身并至入BodyPart
//				messageBodyPart2.setDataHandler(new DataHandler(fds));
//
//				messageBodyPart2.setFileName(MimeUtility.encodeText(displayname));
//				multipart.addBodyPart(messageBodyPart2);
//			}
//		}
//		mimeMsg.setContent(multipart);
//		mimeMsg.setSentDate(new Date());
//		mimeMsg.saveChanges();
//		transport.send(mimeMsg);
//		transport.close();
//		// 发送邮件
//	}
//}