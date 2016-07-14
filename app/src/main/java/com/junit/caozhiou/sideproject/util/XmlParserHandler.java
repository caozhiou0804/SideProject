package com.junit.caozhiou.sideproject.util;

/**
 * User: caozhiou(1195002650@qq.com)
 * Date: 2016-07-13
 * Time: 16:52
 * Description: xml解析handler
 */

import com.junit.caozhiou.sideproject.entity.CityModel;
import com.junit.caozhiou.sideproject.entity.DistrictModel;
import com.junit.caozhiou.sideproject.entity.ProvinceModel;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


public class XmlParserHandler extends DefaultHandler {

    private List<ProvinceModel> provinceList = new ArrayList<ProvinceModel>();
    ProvinceModel provinceModel = new ProvinceModel();
    CityModel cityModel = new CityModel();
    DistrictModel districtModel = new DistrictModel();

    public XmlParserHandler() {

    }

    public List<ProvinceModel> getDataList() {
        return provinceList;
    }


    @Override
    public void startDocument() throws SAXException {
    }


    @Override
    public void startElement(String uri, String localName, String qName,
                             Attributes attributes) throws SAXException {
        if (qName.equals("province")) {
            provinceModel = new ProvinceModel();
            provinceModel.setName(attributes.getValue(0));
            provinceModel.setCityList(new ArrayList<CityModel>());
        } else if (qName.equals("city")) {
            cityModel = new CityModel();
            cityModel.setName(attributes.getValue(0));
            cityModel.setDistrictList(new ArrayList<DistrictModel>());
        } else if (qName.equals("district")) {
            districtModel = new DistrictModel();
            districtModel.setName(attributes.getValue(0));
            districtModel.setZipcode(attributes.getValue(1));
        } else if (qName.equals("data")) {
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName)
            throws SAXException {
        if (qName.equals("district")) {
            cityModel.getDistrictList().add(districtModel);
        } else if (qName.equals("city")) {
            provinceModel.getCityList().add(cityModel);
        } else if (qName.equals("province")) {
            provinceList.add(provinceModel);
        } else if (qName.equals("data")) {
        }
    }

    @Override
    public void characters(char[] ch, int start, int length)
            throws SAXException {
    }

}
