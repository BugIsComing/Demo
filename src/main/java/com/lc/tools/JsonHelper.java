package com.lc.tools;

import antlr.RecognitionException;
import antlr.TokenStreamException;
import com.sdicons.json.mapper.JSONMapper;
import com.sdicons.json.mapper.MapperException;
import com.sdicons.json.model.JSONValue;
import com.sdicons.json.parser.JSONParser;

import java.io.StringReader;

public class JsonHelper {

	private final static String NEWLINE_WINDOWS = "\n\r";
	private final static String NEWLINE_UNIX = "\n";
	private final static String NEWLINE_MAC = "\r";
	private final static String EMPTY_STRING = "";
	private final static String DOUBLE_QUOTATION = "\"";
	private final static String SINGLE_QUOTATION = "\'";
	private final static String TAB_STRING = "\t";

	public static String getJsonString(Object obj) {

		JSONValue jsonValue = null;
		try {
			jsonValue = JSONMapper.toJSON(obj);
		} catch (MapperException e) {
			throw new RuntimeException(e);
		}

		String jsonStr = jsonValue.render(false)
				.replace(NEWLINE_WINDOWS, EMPTY_STRING)
				.replace(NEWLINE_UNIX, EMPTY_STRING)
				.replace(NEWLINE_MAC, EMPTY_STRING)
				.replace(TAB_STRING, EMPTY_STRING);
		return jsonStr;

	}

	public static Object getObjFromJson(String jsonvalue, Class clazz) {
		JSONParser parser = new JSONParser(new StringReader(jsonvalue));
		try {
			Object o = JSONMapper.toJava(parser.nextValue(), clazz);
			return o;
		} catch (TokenStreamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RecognitionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MapperException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
