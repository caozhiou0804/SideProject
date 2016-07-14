package com.junit.caozhiou.sideproject.util;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;

import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import static android.R.id.list;

/**
 * User: caozhiou(1195002650@qq.com)
 * Date: 2016-07-13
 * Time: 16:47
 * Description:
 */
public class PubUtil {
    /**
     * @param ctx
     */
    public static XmlParserHandler getAssetsFile(Activity ctx, String filePath) {

        AssetManager asset = ctx.getAssets();
        XmlParserHandler handler = new XmlParserHandler();
        try {
            InputStream input = asset.open(filePath);
            SAXParserFactory spf = SAXParserFactory.newInstance();
            SAXParser parser = spf.newSAXParser();
//            XmlParserHandler handler = new XmlParserHandler();
            parser.parse(input, handler);
            input.close();
//            handler.getDataList();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
        return handler;
    }
}
