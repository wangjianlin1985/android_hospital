package com.mobileclient.handler;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.mobileclient.domain.Department;
public class DepartmentListHandler extends DefaultHandler {
	private List<Department> departmentList = null;
	private Department department;
	private String tempString;
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);
		if (department != null) { 
            String valueString = new String(ch, start, length); 
            if ("departmentId".equals(tempString)) 
            	department.setDepartmentId(new Integer(valueString).intValue());
            else if ("departmentName".equals(tempString)) 
            	department.setDepartmentName(valueString); 
        } 
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		if("Department".equals(localName)&&department!=null){
			departmentList.add(department);
			department = null; 
		}
		tempString = null;
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		departmentList = new ArrayList<Department>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
        if ("Department".equals(localName)) {
            department = new Department(); 
        }
        tempString = localName; 
	}

	public List<Department> getDepartmentList() {
		return this.departmentList;
	}
}
