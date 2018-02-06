package com.example.mostafa.aman.connection;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;

public class HttpManager {
	final static String BASE_URI = "http://waelsabry.net/aman_app/public/index.php/";

	public static String getData(RequestPackage p) {

		BufferedReader reader = null;

		String uri = p.getUri();
		if (p.getMethod().equals("GET")) {
			uri += "?" + p.getEncodedParams();
		}

		try {
			URL url = new URL(BASE_URI + uri);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestProperty("Content-Type", "text/html; charset=UTF-8");
			 con = (HttpURLConnection) url.openConnection();

			if (p.getMethod().equals("PUT") || p.getMethod().equals("DELETE")) {
				p.getParams().put("_method", p.getMethod());
				p.setMethod("POST");
			}

			con.setRequestMethod(p.getMethod());
			if (p.getMethod().equals("POST")) {
				con.setDoOutput(true);
				OutputStreamWriter writer = new OutputStreamWriter(
						con.getOutputStream(),Charset.forName("utf-8"));
//
//				OutputStreamWriter writer = new OutputStreamWriter(
//						con.getOutputStream());
				writer.write(p.getEncodedParams());
				writer.flush();
			}

			StringBuilder sb = new StringBuilder();
			try {
//				reader = new BufferedReader(new InputStreamReader(
//						con.getInputStream(),"UTF-8"));
				reader = new BufferedReader(new InputStreamReader(
						con.getInputStream(),"UTF-8"));
			} catch (Exception ex) {
				ex.printStackTrace();
			}

			String line;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}

			String result = sb.toString();
//			byte[] utf8 = result.getBytes("UTF-8");
//			result = new String(utf8, "UTF-8");
			Log.d("CarTest","this is res"+result);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
					return null;
				}
			}
		}

	}

}
