package com.bth.demo.Util;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class TEST
{
	private static Gson gson = new Gson();
	public String check(CharSequence[] jsonArr)
	{

		return String.join(",", jsonArr);
	}

	public String checks(JsonArray jsonArr)
	{
		List<String> exampleList = new ArrayList<String>();
		for (JsonElement em : jsonArr)
		{
			exampleList.add(em.getAsJsonObject().get("maduthuong").getAsString());
		}
		return String.join(",", exampleList.toArray(new String[exampleList.size()]));
	}

	boolean isStringEmpty(String string, String str)
	{
		if (!((string == null || string.isEmpty()) & (str == null || str.isEmpty())))
		{
			if ((string == null || string.isEmpty()))
			{
				return true;
			}
			return false;
		}
		return false;
	}

	public static JsonObject createJsonObject(String json)
	{
		if (!StringUtils.isBlank(json))
		{

		try
		{
			System.out.println("addd");
			return gson.fromJson(json, JsonObject.class);
		}
		catch (Exception e)
		{
			return null;
		}
	}
	return null;
	}

	public String formatShowingAmount(double amount)
	{
		NumberFormat NumFrm = new DecimalFormat("###,###,###,###,###");
		return NumFrm.format(Math.abs(amount));
	}
	public static void main(String[] args)
	{
		TEST test = new TEST();
		System.out.println(Base64.getDecoder().decode(""));

//		System.out.println("amout: " + test.formatShowingAmount(2000000));
//		JsonArray jsonSumArr = new JsonArray();
//		JsonArray jsonArr = new JsonArray();
//		JsonObject jsonE1 = new JsonObject();
//		jsonE1.addProperty("maduthuong", "a");
//		JsonObject jsonE2 = new JsonObject();
//		jsonE2.addProperty("maduthuong", "b");
//		JsonObject jsonE3 = new JsonObject();
//		jsonE3.addProperty("maduthuong", "c");
////		jsonArr.add(jsonE1);
////		jsonArr.add(jsonE2);
////		jsonArr.add(jsonE3);
//		jsonSumArr.add(jsonE2);
//		jsonSumArr.addAll(jsonArr);
//		jsonArr.getAsJsonNull();
//		System.out.println(jsonSumArr);
//		System.out.println(test.checks(jsonArr));
//		test.createJsonObject("gffd");
//		System.out.println(test.isStringEmpty("", "tr"));
//
//		String str = "aHR0cHM6Ly9kb2NzLmdvb2dsZS5jb20vc3ByZWFkc2hlZXRzL2QvMS1SZ3lYMUUxVHRkZUZuaHhJc09tbEJmVmF2SXNlazE2R2pHNVBEZmFFWjgvZWRpdD91c3A9c2hhcmluZw==";
//		System.out.println(test.decode(str));
	}

}
