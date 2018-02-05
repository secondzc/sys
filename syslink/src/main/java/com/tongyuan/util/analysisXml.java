package com.tongyuan.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import de.odysseus.staxon.json.JsonXMLConfig;
import de.odysseus.staxon.json.JsonXMLConfigBuilder;
import de.odysseus.staxon.json.JsonXMLOutputFactory;
import org.dom4j.*;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLInputFactory;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Created by Administrator on 2018-1-19.
 */
public class analysisXml {
//    public static void main(String[] args) throws Exception {
//        String xmlStr= readFile("C:/Temp/FileLibrary/admin/2018-01-26_10-01-46/MyPkg/MyPkg.dom.xml");
//        JSONObject jsonobject = analysisXmlFile(xmlStr);
//        Set<String> keys = jsonobject.keySet();
//        Iterator iterator = keys.iterator();
//        while(iterator.hasNext()){
//            String key = (String) iterator.next();
//            System.out.print(key);
//        }
//    }


    public static String readFile(String path) throws Exception {
        File file=new File(path);
        FileInputStream fis = new FileInputStream(file);
        FileChannel fc = fis.getChannel();
        ByteBuffer bb = ByteBuffer.allocate(new Long(file.length()).intValue());
        //fc向buffer中读入数据
        fc.read(bb);
        bb.flip();
        String str=new String(bb.array(),"UTF8");
        fc.close();
        fis.close();
        return str;
    }

    public static JSONObject analysisXmlFile(String xmlPath){
        JSONObject jsonobject = new JSONObject();
        StringReader input = new StringReader(xmlPath);
        StringWriter output = new StringWriter();
        JsonXMLConfig config = new JsonXMLConfigBuilder().autoArray(true).autoPrimitive(true).prettyPrint(true).build();
        try {
            XMLEventReader reader = XMLInputFactory.newInstance().createXMLEventReader(input);
            XMLEventWriter writer = new JsonXMLOutputFactory(config).createXMLEventWriter(output);
            writer.add(reader);
            reader.close();
            writer.close();
        } catch( Exception e){
            e.printStackTrace();
        } finally {
            try {
                output.close();
                input.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        jsonobject = JSONObject.parseObject(output.toString());
        return jsonobject;
    }


}
