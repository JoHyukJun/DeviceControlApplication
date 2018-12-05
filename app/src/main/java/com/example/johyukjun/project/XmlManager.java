package com.example.johyukjun.project;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import org.xmlpull.v1.XmlSerializer;
import android.os.Environment;
import android.util.Log;
import android.util.Xml;

/*
import android.widget.TextView;
import android.app.Activity;
import android.os.Bundle;
*/

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

    // xml string을 만들어서 리턴하는 함수 필요

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

    public static String MakeXmlStr(String id, String pw) {

        String finalString = "";

        //we create a XmlSerializer in order to write xml data
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        XmlSerializer serializer = Xml.newSerializer();

        try {
            // use outputstream
            //we set the FileOutputStream as output for the serializer, using UTF-8 encoding
            serializer.setOutput(stream, "UTF-8");

            //Write <?xml declaration with encoding (if encoding not null) and standalone flag (if standalone not null)
            serializer.startDocument(null, true);

            //set indentation option
            serializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);

            //start a tag called "root"
            serializer.startTag(null, "root");

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

            //write xml data into the OutputStream
            serializer.flush();

            finalString = new String(stream.toByteArray());

            stream.close();

        } catch (Exception e) {
            Log.e("Exception", "error occurred");

        }
        return finalString;
    }

}