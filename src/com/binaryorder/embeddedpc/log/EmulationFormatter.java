package com.binaryorder.embeddedpc.log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class EmulationFormatter extends Formatter {

	public synchronized String formatMessage(LogRecord record) {
		return this.format(record);
	}

	public String format(LogRecord record) {
		StringBuffer buf = new StringBuffer();
		SimpleDateFormat sf = new SimpleDateFormat("DD/mm/YY");
		buf.append(sf.format(new Date(record.getMillis())));
		return buf.toString();
	}
}
