package com.amazonaws.lambda.task.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import com.amazonaws.util.StringUtils;

public class DateUtil {

	private static final String FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ";

	public DateUtil() {
		// TODO Auto-generated constructor stub
	}

	public static String dateToString(Long dateObject) {
		if(dateObject==null || dateObject.longValue()==0) {
			return "";
		} else {
			return dateToString(new Date(dateObject));
		}
	}
	
	public static String dateToString(Date dateObject) {
		SimpleDateFormat format = new SimpleDateFormat(FORMAT);
		String dateString = format.format(dateObject);
		return dateString;
	}

	public static Date stringToDateAsDate(String dateString) throws Exception {

		DateTimeFormatter parser2 = ISODateTimeFormat.dateTimeNoMillis();
		
		DateTime date = parser2.parseDateTime(dateString);

		return date.toDate();
	}
	
	public static Long stringToDate(String dateString) throws Exception {

		if(StringUtils.isNullOrEmpty(dateString)) {
			return 0L;
		} else {
			return stringToDateAsDate(dateString).getTime();
		}
	}
}
