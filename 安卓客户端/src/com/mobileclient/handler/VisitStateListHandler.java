package com.mobileclient.handler;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.mobileclient.domain.VisitState;
public class VisitStateListHandler extends DefaultHandler {
	private List<VisitState> visitStateList = null;
	private VisitState visitState;
	private String tempString;
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);
		if (visitState != null) { 
            String valueString = new String(ch, start, length); 
            if ("visitStateId".equals(tempString)) 
            	visitState.setVisitStateId(new Integer(valueString).intValue());
            else if ("visitStateName".equals(tempString)) 
            	visitState.setVisitStateName(valueString); 
        } 
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		if("VisitState".equals(localName)&&visitState!=null){
			visitStateList.add(visitState);
			visitState = null; 
		}
		tempString = null;
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		visitStateList = new ArrayList<VisitState>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
        if ("VisitState".equals(localName)) {
            visitState = new VisitState(); 
        }
        tempString = localName; 
	}

	public List<VisitState> getVisitStateList() {
		return this.visitStateList;
	}
}
