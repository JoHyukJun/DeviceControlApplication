package com.example.johyukjun.project;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlSerializer;
import android.os.Environment;
import android.util.Log;
import android.util.Xml;

public class XmlManager {

    private static XmlManager one;
    private XmlManager() {
    }

    public static XmlManager getInstance() {
        if(one == null) {
            one = new XmlManager();
        }
        return one;
    }

    public static String MakeLoginXmlStr(String id, String pw) {
        //we create a XmlSerializer in order to write xml data
        String returnStr = "";
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        XmlSerializer serializer = Xml.newSerializer();

        try {
            //we set the FileOutputStream as output for the serializer, using UTF-8 encoding
            serializer.setOutput(stream, "UTF-8");
            //Write <?xml declaration with encoding (if encoding not null) and standalone flag (if standalone not null)
            serializer.startDocument(null, true);
            //set indentation option
            serializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);


            serializer.startTag(null, "IoTPacket, \"packetType = 1\"");
            serializer.startTag(null, "LoginClient");

            serializer.startTag(null, "Id");
            serializer.text(id); //write some text inside
            serializer.endTag(null, "Id");

            serializer.startTag(null, "Pw");
            serializer.text(pw); //write some text inside
            serializer.endTag(null, "Pw");

            serializer.endTag(null, "LoginClient");
            serializer.endTag(null, "IoTPacket, \"packetType = 1\"");


            serializer.endDocument();
            //write xml data into the OutputStream
            serializer.flush();
            returnStr = new String(stream.toByteArray());
            stream.close();
        } catch (Exception e) {
            Log.e("Exception", "error occurred");
        }
        return returnStr;
    }

    public static String ParseLoginXmlStr(String xmlStr) {

        String returnStr = "";

        XmlPullParserFactory parserFactory;
        try {
            parserFactory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = parserFactory.newPullParser() ;

            InputStream inStream = new ByteArrayInputStream(xmlStr.getBytes());
            parser.setInput(inStream, "UTF-8");

            int eventType = parser.getEventType();
            String startTag;
            String endTag;
            String text;

            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType)
                {
                    case XmlPullParser.START_DOCUMENT:
                        break;
                    case XmlPullParser.START_TAG:
                        startTag = parser.getName();
                        if (startTag.equals("IoTPacket, \"packetType = 2\"")) {
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        break;
                    case XmlPullParser.TEXT:
                        returnStr = parser.getText();
                        break;
                    default:
                        break;
                }
                try {
                    eventType = parser.next();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }

        return returnStr;
    }

    public static void ParseXmlStr(String xmlStr) {

        final int STEP_NONE = 0 ;
        final int STEP_NO = 1 ;
        final int STEP_NAME = 2 ;
        final int STEP_PHONE = 3 ;
        final int STEP_OVER20 = 4 ;

        int no = -1 ;
        String name = null ;
        String phone = null ;
        boolean isOver20 = false ;

        XmlPullParserFactory parserFactory = null;
        try {
            parserFactory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = parserFactory.newPullParser() ;

            InputStream inStream = new ByteArrayInputStream(xmlStr.getBytes());
            parser.setInput(inStream, "UTF-8");

            int eventType = parser.getEventType() ;
            int step = STEP_NONE ;
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_DOCUMENT) {
                    // XML 데이터 시작
                } else if (eventType == XmlPullParser.START_TAG) {
                    String startTag = parser.getName() ;
                    if (startTag.equals("NO")) {
                        step = STEP_NO ;
                    } else if (startTag.equals("NAME")) {
                        step = STEP_NAME ;
                    } else if (startTag.equals("PHONE")) {
                        step = STEP_PHONE ;
                    } else if (startTag.equals("OVER20")) {
                        step = STEP_OVER20 ;
                    } else {
                        step = STEP_NONE ;
                    }
                } else if (eventType == XmlPullParser.END_TAG) {
                    String endTag = parser.getName() ;
                    if ((endTag.equals("NO") && step != STEP_NO) ||
                            (endTag.equals("NAME") && step != STEP_NAME) ||
                            (endTag.equals("PHONE") && step != STEP_PHONE) ||
                            (endTag.equals("OVER20") && step != STEP_OVER20))
                    {
                        // TODO : error
                    }
                    step = STEP_NONE ;
                } else if (eventType == XmlPullParser.TEXT) {
                    String text = parser.getText() ;
                    if (step == STEP_NO) {
                        try {
                            no = Integer.parseInt(text);
                        } catch (Exception e) {
                            no = 0 ;
                        }
                    } else if (step == STEP_NAME) {
                        name = text ;
                    } else if (step == STEP_PHONE) {
                        phone = text ;
                    } else if (step == STEP_OVER20) {
                        isOver20 = Boolean.parseBoolean(text);
                    }
                }

                try {
                    eventType = parser.next();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }





    }


    // make xml file to android filesystem
    public static void MakeXmlFIle(String id, String pw) {

        //create a new file called "new.xml" in the SD card

        File newxmlfile = new File(Environment.getExternalStorageDirectory() + "/new.xml");

        try {

            newxmlfile.createNewFile();

        } catch (IOException e) {

            Log.e("IOException", "exception in createNewFile() method");

        }

        //we have to bind the new file with a FileOutputStream

        FileOutputStream fileos = null;

        try {

            fileos = new FileOutputStream(newxmlfile);

        } catch (FileNotFoundException e) {

            Log.e("FileNotFoundException", "can't create FileOutputStream");

        }

        //we create a XmlSerializer in order to write xml data

        XmlSerializer serializer = Xml.newSerializer();

        try {

            //we set the FileOutputStream as output for the serializer, using UTF-8 encoding


            // use outputstream
            // newtwork manager needed
            serializer.setOutput(fileos, "UTF-8");

            //Write <?xml declaration with encoding (if encoding not null) and standalone flag (if standalone not null)

            serializer.startDocument(null, true);

            //set indentation option

            serializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);

            //start a tag called "root"

            serializer.startTag(null, "root");

            //i indent code just to have a view similar to xml-tree

            /*
            serializer.startTag(null, "child1");

            serializer.endTag(null, "child1");


            serializer.startTag(null, "child2");

            //set an attribute called "attribute" with a "value" for

            serializer.attribute(null, "attribute", "value");

            serializer.endTag(null, "child2");



            serializer.startTag(null, "child3");

            //write some text inside

            serializer.text("some text inside child3");

            serializer.endTag(null, "child3");
            */

            serializer.startTag(null, "Id");

            //write some text inside

            serializer.text(id);

            serializer.endTag(null, "Id");

            serializer.startTag(null, "Pw");

            //write some text inside

            serializer.text(pw);

            serializer.endTag(null, "Pw");

            serializer.endTag(null, "root");

            serializer.endDocument();

            //write xml data into the FileOutputStream

            serializer.flush();

            //finally we close the file stream

            fileos.close();


        } catch (Exception e) {

            Log.e("Exception", "error occurred while creating xml file");

        }
    }
}