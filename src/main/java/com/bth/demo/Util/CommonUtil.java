package com.bth.demo.Util;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class CommonUtil
{
	private static Gson gson = new Gson();

	public static JsonObject createJsonObject(String json)
	{
		try
		{
			return gson.fromJson(json, JsonObject.class);
		}
		catch (Exception e)
		{
			return null;
		}
	}
}
