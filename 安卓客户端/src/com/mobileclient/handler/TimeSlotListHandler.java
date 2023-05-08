package com.mobileclient.handler;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.mobileclient.domain.TimeSlot;
public class TimeSlotListHandler extends DefaultHandler {
	private List<TimeSlot> timeSlotList = null;
	private TimeSlot timeSlot;
	private String tempString;
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);
		if (timeSlot != null) { 
            String valueString = new String(ch, start, length); 
            if ("timeSlotId".equals(tempString)) 
            	timeSlot.setTimeSlotId(new Integer(valueString).intValue());
            else if ("timeSlotName".equals(tempString)) 
            	timeSlot.setTimeSlotName(valueString); 
        } 
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		if("TimeSlot".equals(localName)&&timeSlot!=null){
			timeSlotList.add(timeSlot);
			timeSlot = null; 
		}
		tempString = null;
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		timeSlotList = new ArrayList<TimeSlot>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
        if ("TimeSlot".equals(localName)) {
            timeSlot = new TimeSlot(); 
        }
        tempString = localName; 
	}

	public List<TimeSlot> getTimeSlotList() {
		return this.timeSlotList;
	}
}
