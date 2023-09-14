package com.bth.demo.Util;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * @author hopbt0500 It represents a year-month-day in the ISO calendar and is useful for representing a date without a
 *         time. It can be used to represent a date only information such as a birth date or wedding date
 */
public class TimeUtil
{
	/**
	 * get a {@code Date} minimum time "00:00:00". This is the time of midnight at the start of the day.
	 * 
	 * @param {@code Date} date
	 * @return {@code Date} date minimum
	 */
	public static Date atStartOfDate(Date date)
	{
		LocalDateTime localDate = convertDateToLocalDate(date);
		LocalDateTime startOfDate = localDate.with(LocalTime.MIN);
		return convertLocalDateToDate(startOfDate);

	}

	/**
	 * convert a {@code LocalDateTime} to {@code Date} date
	 * 
	 * @param {@code LocalDateTime} localDateTime
	 * @return {@code Date} date
	 */
	private static Date convertLocalDateToDate(LocalDateTime localDateTime)
	{
		return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
	}

	/**
	 * convert a {@code Date} to {@code LocalDateTime} date
	 * 
	 * @param {@code Date} date
	 * @return {@code LocalDateTime} localDateTime
	 */
	private static LocalDateTime convertDateToLocalDate(Date date)
	{
		return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
	}

	/**
	 * get a {@code Date} maximum time "23:59:59". This is the time of midnight at the end of the day.
	 * 
	 * @param {@code Date} date
	 * @return {@code Date} date maximum
	 */

	public static Date atEndOfDate(Date date)
	{
		LocalDateTime localDate = convertDateToLocalDate(date);
		LocalDateTime endOfDayTime = localDate.with(LocalTime.MAX);
		return convertLocalDateToDate(endOfDayTime);
	}

	/**
	 * check date before date now
	 * 
	 * @param {@code Date} date
	 * @return {@code Boolean} yes/no
	 */
	public static Boolean beforeDateNow(Date date)
	{
		LocalDateTime localDate = convertDateToLocalDate(date);
		LocalDateTime DateNow = LocalDateTime.now();
		return localDate.isBefore(DateNow) ? true : false;

	}

	/**
	 * check year Enough 18 Year Old
	 * 
	 * @param {@code Date} date
	 * @return {@code Boolean} yes/no
	 */
	public static Boolean Enough18YearOld(Date date)
	{
		LocalDateTime localDate = convertDateToLocalDate(date);
		LocalDateTime DateNow = LocalDateTime.now();
		return DateNow.minusYears(18).isBefore(localDate) ? true : false;
	}

	public static void main(String[] args)
	{
		Date date = new Date();
		date.setTime(1660554843);
		System.out.println(atStartOfDate(date));
		System.out.println(atEndOfDate(date));
		System.out.println(beforeDateNow(date));
		System.out.println(Enough18YearOld(date));
	}
}
