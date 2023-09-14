package com.bth.demo.Util;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

public class StringUtil
{
	public String insertCharacterToJsonArray(JsonArray jsArray, String memberName, String character)
	{
		List<String> exampleList = new ArrayList<String>();
		for (JsonElement je : jsArray)
		{
			exampleList.add(je.getAsJsonObject().get(memberName).getAsString());
		}
		return String.join(character, exampleList.toArray(new String[exampleList.size()]));
	}
}
