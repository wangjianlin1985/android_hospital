package com.mobileclient.handler;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.mobileclient.domain.LeaveWord;
public class LeaveWordListHandler extends DefaultHandler {
	private List<LeaveWord> leaveWordList = null;
	private LeaveWord leaveWord;
	private String tempString;
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);
		if (leaveWord != null) { 
            String valueString = new String(ch, start, length); 
            if ("leaveWordId".equals(tempString)) 
            	leaveWord.setLeaveWordId(new Integer(valueString).intValue());
            else if ("title".equals(tempString)) 
            	leaveWord.setTitle(valueString); 
            else if ("content".equals(tempString)) 
            	leaveWord.setContent(valueString); 
            else if ("addTime".equals(tempString)) 
            	leaveWord.setAddTime(valueString); 
            else if ("userObj".equals(tempString)) 
            	leaveWord.setUserObj(valueString); 
            else if ("replyContent".equals(tempString)) 
            	leaveWord.setReplyContent(valueString); 
            else if ("replyTime".equals(tempString)) 
            	leaveWord.setReplyTime(valueString); 
            else if ("replyDoctor".equals(tempString)) 
            	leaveWord.setReplyDoctor(valueString); 
        } 
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		if("LeaveWord".equals(localName)&&leaveWord!=null){
			leaveWordList.add(leaveWord);
			leaveWord = null; 
		}
		tempString = null;
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		leaveWordList = new ArrayList<LeaveWord>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
        if ("LeaveWord".equals(localName)) {
            leaveWord = new LeaveWord(); 
        }
        tempString = localName; 
	}

	public List<LeaveWord> getLeaveWordList() {
		return this.leaveWordList;
	}
}
