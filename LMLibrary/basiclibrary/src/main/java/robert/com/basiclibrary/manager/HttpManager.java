/*Copyright ©2015 TommyLemon(https://github.com/TommyLemon)

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.*/

package robert.com.basiclibrary.manager;

import android.content.Context;
import android.os.AsyncTask;
import android.text.TextUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.CookieHandler;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLSocketFactory;


import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import robert.com.basiclibrary.base.BaseApplication;
import robert.com.basiclibrary.model.Parameter;
import robert.com.basiclibrary.util.Log;
import robert.com.basiclibrary.util.SSLUtil;
import robert.com.basiclibrary.util.StringUtil;

/**HTTP请求管理类
 * @author Lemon
 * @use HttpManager.getInstance().get(...)或HttpManager.getInstance().post(...)  > 在回调方法onHttpRequestSuccess和onHttpRequestError处理HTTP请求结果
 * @must 解决getToken，getResponseCode，getResponseData中的TODO
 */
public class HttpManager {
	private static final String TAG = "HttpManager";

	/**网络请求回调接口
	 */
	public interface OnHttpResponseListener {
		/**
		 * @param requestCode 请求码，自定义，在发起请求的类中可以用requestCode来区分各个请求
		 * @param resultJson 服务器返回的Json串
		 * @param e 异常
		 */
		void onHttpResponse(int requestCode, String resultJson, Exception e);
	}



	private Context context;
	private static HttpManager instance;// 单例
	private static SSLSocketFactory socketFactory;// 单例
	private HttpManager(Context context) {
		this.context = context;

	}

	public synchronized static HttpManager getInstance() {
		if (instance == null) {
			instance = new HttpManager(BaseApplication.getInstance());
		}
		return instance;
	}



	/**
	 * 列表首页页码。有些服务器设置为1，即列表页码从1开始
	 */
	public static final int PAGE_NUM_0 = 0;

	public static final String KEY_TOKEN = "token";
	public static final String KEY_COOKIE = "cookie";


	/**GET请求
	 * @param paramList 请求参数列表，（可以一个键对应多个值）
	 * @param url 接口url
	 * @param requestCode
	 *            请求码，类似onActivityResult中请求码，当同一activity中以实现接口方式发起多个网络请求时，请求结束后都会回调
	 *            {@link OnHttpResponseListener#onHttpResponse(int, String, Exception)}<br>
	 *            在发起请求的类中可以用requestCode来区分各个请求
	 * @param listener
	 */
	public void get(final List<Parameter> paramList, final List<Parameter> headList , final String url,
                    final int requestCode, final OnHttpResponseListener listener) {

		new AsyncTask<Void, Void, Exception>() {

			String result;
			@Override
			protected Exception doInBackground(Void... params) {
				OkHttpClient client = getHttpClient(url);
				if (client == null) {
					return new Exception(TAG + ".get  AsyncTask.doInBackground  client == null >> return;");
				}

				StringBuffer sb = new StringBuffer();
				sb.append(StringUtil.getNoBlankString(url));
				if (paramList != null) {
					Parameter parameter;
					for (int i = 0; i < paramList.size(); i++) {
						parameter = paramList.get(i);
						sb.append(i <= 0 ? "?" : "&");
						sb.append(StringUtil.getTrimedString(parameter.key));
						sb.append("=");
						sb.append(StringUtil.getTrimedString(parameter.value));
					}
				}

				try {
					Request.Builder request = new Request.Builder();
					if (headList != null) {
						for (Parameter p : headList) {
							request.addHeader(StringUtil.getTrimedString(p.key), StringUtil.getTrimedString(p.value));
						}
					}
					request.url(sb.toString());
					result = getResponseJson(client, request.build());

				} catch (Exception e) {
					Log.e(TAG, "get  AsyncTask.doInBackground  try {  result = getResponseJson(..." +
							"} catch (Exception e) {\n" + e.getMessage());
					return e;
				}

				return null;
			}

			@Override
			protected void onPostExecute(Exception exception) {
				super.onPostExecute(exception);
				listener.onHttpResponse(requestCode, result, exception);
			}

		}.execute();

	}


	/**POST请求
	 * @param paramList 请求参数列表，（可以一个键对应多个值）
	 * @param url 接口url
	 * @param requestCode
	 *            请求码，类似onActivityResult中请求码，当同一activity中以实现接口方式发起多个网络请求时，请求结束后都会回调
	 *            {@link OnHttpResponseListener#onHttpResponse(int, String, Exception)}<br>
	 *            在发起请求的类中可以用requestCode来区分各个请求
	 * @param listener
	 */
	public void post(final List<Parameter> paramList, final List<Parameter> headList, final String url,
                     final int requestCode, final OnHttpResponseListener listener) {

		new AsyncTask<Void, Void, Exception>() {

			String result;
			@Override
			protected Exception doInBackground(Void... params) {
				OkHttpClient client = getHttpClient(url);
				if (client == null) {
					return new Exception(TAG + ".post  AsyncTask.doInBackground  client == null >> return;");
				}

				FormBody.Builder fBuilder =  new FormBody.Builder();
				if (paramList != null) {
					for (Parameter p : paramList) {
						fBuilder.add(StringUtil.getTrimedString(p.key), StringUtil.getTrimedString(p.value));
					}
				}

				try {
					Request.Builder request = new Request.Builder();
					if (headList != null) {
						for (Parameter p : headList) {
							request.addHeader(StringUtil.getTrimedString(p.key), StringUtil.getTrimedString(p.value));
						}
					}
					request.url(StringUtil.getNoBlankString(url));
					request.post(fBuilder.build());
					result = getResponseJson(client, request.build());

				} catch (Exception e) {
					Log.e(TAG, "post  AsyncTask.doInBackground  try {  result = getResponseJson(..." +
							"} catch (Exception e) {\n" + e.getMessage());
					return e;
				}

				return null;
			}

			@Override
			protected void onPostExecute(Exception exception) {
				super.onPostExecute(exception);
				listener.onHttpResponse(requestCode, result, exception);
			}

		}.execute();
	}


	//httpGet/httpPost 内调用方法 <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	/**
	 * @param url
	 * @return
	 */
	private OkHttpClient getHttpClient(String url) {
		Log.i(TAG, "getHttpClient  url = " + url);
		if (StringUtil.isNotEmpty(url, true) == false) {
			Log.e(TAG, "getHttpClient  StringUtil.isNotEmpty(url, true) == false >> return null;");
			return null;
		}

		OkHttpClient client = new OkHttpClient();
		client.newBuilder().connectTimeout(15, TimeUnit.SECONDS);
		client.newBuilder().writeTimeout(10, TimeUnit.SECONDS);
		client.newBuilder().readTimeout(10, TimeUnit.SECONDS);


		return client;
	}

	/**
	 * @param
	 * @return
	 */
	public String getToken(String tag) {
		return context.getSharedPreferences(KEY_TOKEN, Context.MODE_PRIVATE).getString(KEY_TOKEN + tag, "");
	}
	/**
	 * @param tag
	 * @param value
	 */
	public void saveToken(String tag, String value) {
		context.getSharedPreferences(KEY_TOKEN, Context.MODE_PRIVATE)
		.edit()
		.remove(KEY_TOKEN + tag)
		.putString(KEY_TOKEN + tag, value)
		.commit();
	}


	/**
	 * @return
	 */
	public String getCookie() {
		return context.getSharedPreferences(KEY_COOKIE, Context.MODE_PRIVATE).getString(KEY_COOKIE, "");
	}
	/**
	 * @param value
	 */
	public void saveCookie(String value) {
		context.getSharedPreferences(KEY_COOKIE, Context.MODE_PRIVATE)
		.edit()
		.remove(KEY_COOKIE)
		.putString(KEY_COOKIE, value)
		.commit();
	}


	/**
	 * @param client
	 * @param request
	 * @return
	 * @throws Exception
	 */
	private String getResponseJson(OkHttpClient client, Request request) throws Exception {
		if (client == null || request == null) {
			Log.e(TAG, "getResponseJson  client == null || request == null >> return null;");
			return null;
		}
		Response response = client.newCall(request).execute();
		return response.isSuccessful() ? response.body().string() : null;
	}

	/**从object中获取key对应的值
	 * *获取如果T是基本类型容易崩溃，所以需要try-catch
	 * @param json
	 * @param key
	 * @return
	 * @throws JSONException
	 */
	public <T> T getValue(String json, String key) throws JSONException {
		return getValue(new JSONObject(json), key);
	}
	/**从object中获取key对应的值
	 * *获取如果T是基本类型容易崩溃，所以需要try-catch
	 * @param object
	 * @param key
	 * @return
	 * @throws JSONException
	 */
	@SuppressWarnings("unchecked")
	public <T> T getValue(JSONObject object, String key) throws JSONException {
		return (T) object.get(key);
	}

	//httpGet/httpPost 内调用方法 >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>




	/**http请求头
	 */
	public class HttpHead extends CookieHandler {
		public HttpHead() {
		}

		@Override
		public Map<String, List<String>> get(URI uri, Map<String, List<String>> requestHeaders) throws IOException {
			String cookie = getCookie();
			Map<String, List<String>> map = new HashMap<String, List<String>>();
			map.putAll(requestHeaders);
			if (!TextUtils.isEmpty(cookie)) {
				List<String> cList = new ArrayList<String>();
				cList.add(cookie);
				map.put("Cookie", cList);
			}
			return map;
		}

		@Override
		public void put(URI uri, Map<String, List<String>> responseHeaders) throws IOException {
			List<String> list = responseHeaders.get("Set-Cookie");
			if (list != null) {
				for (int i = 0; i < list.size(); i++) {
					String cookie = list.get(i);
					if (cookie.startsWith("JSESSIONID")) {
						saveCookie(list.get(i));
						break;
					}
				}
			}
		}

	}




}