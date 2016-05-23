package com.daydayup.magictelebook.weather;

import android.content.Context;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by haha on 2016/5/17.
 */
public class XML {


    public static int parse(Context context,String County) {

        InputStream inputStream = null;
        //获得XmlPullParser解析器
        XmlPullParser xmlParser = Xml.newPullParser();

        try {
            inputStream = context.getResources().getAssets().open("Code.xml");
            //获得解析到的事件类别，这里有开始文档，结束文档，开始标签，结束标签，文本等等事件。
            int evtType = xmlParser.getEventType();
            xmlParser.setInput(inputStream, "UTF-8");

            while ((evtType != XmlPullParser.END_DOCUMENT)) {
                switch (evtType) {
                    case XmlPullParser.START_TAG:
                        //如果是river标签开始，则说明需要实例化对象
                        if (xmlParser.getName().equals("county") && (xmlParser.getAttributeValue(1).equals(County))) {

                            return Integer.parseInt(xmlParser.getAttributeValue(null, "weatherCode"));
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        //tag = xmlParser.getName();
                        //如果遇到river标签结束，则把river对象添加进集合中
                        break;
                    default:
                        break;
                }
                //如果xml没有结束，则导航到下一个river节点
                evtType = xmlParser.next();
            }
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        return -1;
    }
}