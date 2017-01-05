package com.id.controller;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.PasswordAuthentication;
import java.net.Proxy;
import java.net.URL;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.LinkedHashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class Main {
	public static Proxy proxy;
	public static int secondTimeout = 60;
	public static HttpURLConnection connection = null;
	// public static final String
	// url="http://eservice.dopa.go.th/Election/Elecenter/interenqabs/";
	public static final String urlString = "http://eservice.dopa.go.th/Election/Elecenter/interenqabs/detailList.php";

	public static void initAuthenticator() {
		proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(
				"proxy.scb.co.th", 8080));
		Authenticator authenticator = new Authenticator() {
			public PasswordAuthentication getPasswordAuthentication() {
				return (new PasswordAuthentication("s45440",
						"Workplace@77".toCharArray()));
			}
		};
		Authenticator.setDefault(authenticator);
	}

	public static void main(String[] args) {
		// URLConnection connection=null;

		try {
			// initAuthenticator();

			/*
			 * Map<String,Object> params = new LinkedHashMap<>();
			 * params.put("txtPid", "1849900088442"); byte[]
			 * postDataBytes=convertToByte(params);
			 * //connection.setRequestMethod("POST");
			 * //connection.setRequestProperty("Content-Type",
			 * "application/x-www-form-urlencoded");
			 * //connection.setRequestProperty("Content-Length",
			 * String.valueOf(postDataBytes.length));
			 * connection.setDoOutput(true);
			 * connection.getOutputStream().write(postDataBytes);
			 * 
			 * //Reader in = new BufferedReader(new
			 * InputStreamReader(connection.getInputStream(), "UTF-8"));
			 * 
			 * BufferedReader bufferedReader = new BufferedReader(new
			 * InputStreamReader(connection.getInputStream(),"UTF-8"));
			 * 
			 * String line = null; StringBuffer stringBuffer = new
			 * StringBuffer(); while ((line = bufferedReader.readLine()) !=
			 * null) { stringBuffer.append(line); } Document document =
			 * Jsoup.parse(String.valueOf(stringBuffer));
			 * //System.out.println(document.html());
			 * //System.out.println(document
			 * .select("<span class=\"txtLabel\">")); for(Element
			 * e:document.getAllElements()){
			 * System.out.println(e.cssSelector()+":"+e.ownText()); } String
			 * keyword="ชื่อ-สกุล"; String data=document.select(
			 * "html > body > table:nth-child(2) > tbody > tr:nth-child(1) > td"
			 * ).text(); String id=(data.substring(0,
			 * data.indexOf(keyword)).split(":",-1)[1]).replace("\u00A0", "");
			 * //char 160 String
			 * name=(data.substring(data.indexOf(keyword)).split
			 * (":",-1)[1]).replace("\u00A0", ""); //char 160 StringBuilder sb =
			 * new StringBuilder(); for (char c : id.toCharArray())
			 * sb.append((int)c+","); System.out.println(sb);
			 * System.out.println(id); System.out.println(name);
			 * 
			 * //connection.setDoOutput(true);
			 * //System.out.println(connection.getOutputStream());
			 * //System.out.println(document.select("divPid").html());
			 */

			// System.out.println(checkDigit("184990008814"));
			String str10 = "184010009"; // 237//18498บ้านนาสาร//18417พุนพิน//18416พระแสง//18415เวียงสระ//18414เคียนซา
			// 18413บ้านนาเดิม//18412นาสาร//18411ท่าฉาง//18410พนม//18407ท่าชนะ//18406ไชยา
			// 18405พะงัน//18404เกาะสมุย//18403ดอนสัก//18402กาญจนดิษฐ์
			// int i=0;
			DecimalFormat decimalFormat2 = new DecimalFormat("0");
			DecimalFormat decimalFormat = new DecimalFormat("00");
			// System.out.println(decimalFormat.format(i));
			for (int j = 0; j <= 9; j++)
				for (int i = 0; i <= 99; i++) {
					// String id=str10+decimalFormat.format(i);
					String id = str10 + decimalFormat2.format(j)
							+ decimalFormat.format(i);

					String idWithCheckedDigit = id + checkDigit(id);
					// System.out.println(idWithCheckedDigit+"<"+checkDigit(id));
					getPersonById(idWithCheckedDigit);
				}
			// getPersonById("1500900143847");
			// getPersonById("1849900088434");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static int checkDigit(String id) {
		// 12 digit passed
		int total = 0;
		char[] c = id.toCharArray();

		for (int i = 13; i > 1; i--) {
			total += Integer.parseInt(c[13 - i] + "") * i;
		}
		int checkDigit = 11 - (total % 11);
		if (checkDigit == 10)
			checkDigit = 0;
		if (checkDigit == 11)
			checkDigit = 1;
		// System.out.println("total:"+total);
		// System.out.println("total%11:"+total%11);
		// System.out.println(checkDigit);

		return checkDigit;
	}

	public static PersonalDetail getPersonById(String id) throws IOException {
		PersonalDetail personalDetail = new PersonalDetail();

		URL url = new URL(urlString);
		if (proxy != null)
			connection = (HttpURLConnection) url.openConnection(proxy);
		else
			connection = (HttpURLConnection) url.openConnection();

		connection.setConnectTimeout(secondTimeout * 1000);
		connection.setDoOutput(true);

		Map<String, Object> params = new LinkedHashMap<>();
		params.put("txtPid", id);
		byte[] postDataBytes = convertToByte(params);
		// connection.setRequestMethod("POST");
		// connection.setRequestProperty("Content-Type",
		// "application/x-www-form-urlencoded");
		// connection.setRequestProperty("Content-Length",
		// String.valueOf(postDataBytes.length));
		// connection.setDoOutput(true);

		OutputStream os = connection.getOutputStream();
		os.write(postDataBytes);

		// Reader in = new BufferedReader(new
		// InputStreamReader(connection.getInputStream(), "UTF-8"));

		BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(connection.getInputStream(), "UTF-8"));

		String line = null;
		StringBuffer stringBuffer = new StringBuffer();
		while ((line = bufferedReader.readLine()) != null) {
			stringBuffer.append(line);
		}
		bufferedReader.close();

		// connection.getInputStream().close();
		Document document = Jsoup.parse(String.valueOf(stringBuffer));
		// System.out.println(document.html());
		// System.out.println(document.select("<span class=\"txtLabel\">"));
		for (Element e : document.getAllElements()) {
			// System.out.println(e.cssSelector()+":"+e.ownText());

			if (e.cssSelector()
					.equals("html > body > table.tableborder > tbody > tr:nth-child(1)")) {
				// System.out.println(e.cssSelector()+":"+e.ownText()+":");
				// System.out.println(e.siblingElements().get(0).getAllElements());
				String text = e.siblingElements().get(0).getAllElements()
						.get(0).attributes().get("onclick");
				// System.out.println(text);
				/*
				 * for(Element e2:e.siblingElements().get(0).getAllElements()){
				 * System
				 * .out.println(e2.getAllElements().get(0).attributes().get
				 * ("onclick")); }
				 */
				// System.out.println(e.siblingElements().get(0).getAllElements());
				String firstText = "javascript:getPersonDetail('";
				String lastText = "');";
				if (text.startsWith(firstText))
					text = text.substring(firstText.length());
				if (text.endsWith(lastText))
					text = text.substring(0, text.length() - lastText.length());
				String[] content = text.split("[|]", -1);
				String idContent = content[0];
				String titleName = content[3];
				String name = content[4];
				// String middleName=content[5];
				String lastName = content[6];
				String birthDate = content[8];
				String addressId = content[9];
				String electedAddress = content[10];
				String electedAddress2 = content[11];

				System.out.println(idContent + "\t" + titleName + "\t" + name
						+ "\t" + lastName + "\t" + birthDate + "\t" + addressId
						+ "\t" + electedAddress + "\t" + electedAddress2);
			}
		}
		// System.out.println(document.html());
		String keyword = "ชื่อ-สกุล";
		String data = document
				.select("html > body > table:nth-child(2) > tbody > tr:nth-child(1) > td")
				.text();
		// String rsId="";
		String name = "";

		// DecimalFormat formattedId=new DecimalFormat("#-####-#####-##-#");

		// try{rsId=id.replaceFirst("(\\d{1})(\\d{4})(\\d{5})(\\d{2})(\\d+)",
		// "$1-$2-$3-$4-$5");}catch(Exception e){}
		try {
			// rsId=(data.substring(0,
			// data.indexOf(keyword)).split(":",-1)[1]).replace("\u00A0", "");
			// //char 160
			name = (data.substring(data.indexOf(keyword)).split(":", -1)[1])
					.replace("\u00A0", ""); // char 160
		} catch (Exception e) {
			name = data;
		}

		/*
		 * StringBuilder sb = new StringBuilder(); for (char c :
		 * id.toCharArray()) sb.append((int)c+","); System.out.println(sb);
		 */
		// System.out.println(rsId+" "+name);

		// System.out.println(rsId+" "+name);
		personalDetail.setId(id);
		personalDetail.setName(name);

		return personalDetail;

	}

	public static class PersonalDetail {
		String id;
		String name;

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

	}

	public static byte[] convertToByte(Map<String, Object> params)
			throws UnsupportedEncodingException {
		StringBuilder postData = new StringBuilder();
		for (Map.Entry<String, Object> param : params.entrySet()) {
			if (postData.length() != 0)
				postData.append('&');
			postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
			postData.append('=');
			postData.append(URLEncoder.encode(String.valueOf(param.getValue()),
					"UTF-8"));
		}
		byte[] postDataBytes = postData.toString().getBytes("UTF-8");
		return postDataBytes;
	}
}
