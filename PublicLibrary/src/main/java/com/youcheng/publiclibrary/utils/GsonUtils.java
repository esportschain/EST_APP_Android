package com.youcheng.publiclibrary.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class GsonUtils {

	/**
	 * success
	 */
	public static String SUCCESS = "success";

	/**
	 * RESULT
	 */
	public static String RESULT = "result";

	/**
	 * falid
	 */
	public static String FALID = "failed";

	/**
	 * reason
	 */
	public static String REASON = "reason";

	/**
	 * Code
	 */
	public static String CODE = "retCode";

	public static String jsonArrayToString(String str, String key) {
		Gson gson = new Gson();
		JsonObject json = stringToGson(str);
		String string = gson.toJson(json.getAsJsonArray(key));
		return string;
	}

	public static String getJsonValue(String str, String key) {
		JsonObject json = stringToGson(str);

		String re = "";
		if (json.get(key) != null) {
			re = json.get(key).toString();
		}

		return removeTips(re);
	}

	public static String getJsonValue(JsonObject jsonObject, String key) {
		try {
			String re = jsonObject.get(key).toString();
			// 去掉前后双引号
			String result = removeTips(re);
			return result;

		} catch (NullPointerException e) {
			return "";
		}
	}

	public static <T> List<T> toObjectList(String json, Type typeToken) {
		Gson gson = new Gson();
		 List<T> listT = gson.fromJson(json, new TypeToken<List<T>>() {
		 }.getType());
		 return listT;
	}

	public static JsonObject stringToGson(String str) {
		JsonParser jsonParse = new JsonParser();
		JsonObject json = jsonParse.parse(str).getAsJsonObject();
		return json;
	}

	public static <T> String toJson(T t) {
		Gson gson = new Gson();
		return gson.toJson(t);
	}

	public static <T> T toObject(String json, Class<T> cls) {
		Gson gson = new Gson();
		return gson.fromJson(json, cls);
	}

	public static <T> T toObject(String json, Class<T> cls, int dateFormat) {
		Gson gson = new GsonBuilder().setDateFormat(dateFormat).create();
		return gson.fromJson(json, cls);
	}

	public static String getJsonResult(String str) {
		if (null == str || "".equals(str) || str.length() < 5) {
			return GsonUtils.FALID;
		}
		String mKey = GsonUtils.RESULT;
		return getJsonValue(removeTips(str), mKey);
	}

	public static String jsonArrayToString(String str) {
		String mKey = "data";
		return jsonArrayToString(removeTips(str), mKey);
	}

	private static String removeTips(String str) {
		String result = "";
		if (str.startsWith("\"") && str.endsWith("\""))
			result = str.substring(1, str.length() - 1);
		else
			result = str;
		return result;
	}

	public static String JSONTokener(String in) {
		// consume an optional byte order mark (BOM) if it exists
		if (!"".equals(in) && in != null && in.startsWith("\ufeff")) {
			int index = in.indexOf("{");
			if (index != -1)
				in = in.substring(index).toString();
		}
		return in;
	}

}
