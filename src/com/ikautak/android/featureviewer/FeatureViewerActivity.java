package com.ikautak.android.featureviewer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.pm.FeatureInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.SimpleExpandableListAdapter;

public class FeatureViewerActivity extends Activity {

    private static final String GROUP = "group";
    private static final String CHILD = "child";
    private static final String VALUE = "value";

    private ExpandableListView mListView = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main);

        mListView = (ExpandableListView)findViewById(R.id.list);

        // get feature list
        PackageManager pm = this.getPackageManager();
        FeatureInfo[] fi = pm.getSystemAvailableFeatures();

        // create ExpandableListView
        List<Map<String, String>> groupDataList = new ArrayList<Map<String,String>>();
        List<List<Map<String, String>>> childDataList = new ArrayList<List<Map<String,String>>>();

        for (int i = 0; i < fi.length; i++) {
            if (fi[i].name != null) {
                // group
                Map<String, String> groupData = new HashMap<String, String>();
                groupData.put(GROUP, fi[i].name);
                groupDataList.add(groupData);

                // child
                List<Map<String, String>> childDataSubList = new ArrayList<Map<String,String>>();
                Map<String, String> childData = new HashMap<String, String>();
                childData.put(CHILD, getFeatureDetailText(fi[i].name));

                childDataSubList.add(childData);
                childDataList.add(childDataSubList);
            } else if (fi[i].reqGlEsVersion != 0) {
                //gl es
                int glver = fi[i].reqGlEsVersion;

                // group
                Map<String, String> groupData = new HashMap<String, String>();
                groupData.put(GROUP, "reqGlEsVersion:" + (glver >> 16) + "." + (0x0000ffff & glver));
                groupDataList.add(groupData);

                // child
                List<Map<String, String>> childDataSubList = new ArrayList<Map<String,String>>();
                Map<String, String> childData = new HashMap<String, String>();
                childData.put(CHILD, mListView.getResources().getString(R.string.gl_es_version));

                childDataSubList.add(childData);
                childDataList.add(childDataSubList);
            }
        }

        ExpandableListAdapter adapter = new SimpleExpandableListAdapter(
            this,
            groupDataList,
            android.R.layout.simple_expandable_list_item_1,
            new String[] { GROUP },
            new int[] { android.R.id.text1 },
            childDataList,
            R.layout.expandable_list_child,
            new String[] { CHILD, VALUE },
            new int[] { android.R.id.text1, android.R.id.text2 }
        );

        mListView.setAdapter(adapter);
    }

    private String getFeatureDetailText(String feature) {
        int resId = R.string.unknown;

        if ("android.hardware.audio.low_latency".equals(feature)) {
            resId = R.string.audio_low_latency;
        } else if ("android.hardware.bluetooth".equals(feature)) {
            resId = R.string.bluetooth;
        } else if ("android.hardware.camera".equals(feature)) {
            resId = R.string.camera;
        } else if ("android.hardware.camera.autofocus".equals(feature)) {
            resId = R.string.camera_autofocus;
        } else if ("android.hardware.camera.flash".equals(feature)) {
            resId = R.string.camera_flash;
        } else if ("android.hardware.camera.front".equals(feature)) {
            resId = R.string.camera_front;
        } else if ("android.hardware.location".equals(feature)) {
            resId = R.string.location;
        } else if ("android.hardware.location.network".equals(feature)) {
            resId = R.string.location_network;
        } else if ("android.hardware.location.gps".equals(feature)) {
            resId = R.string.location_gps;
        } else if ("android.hardware.microphone".equals(feature)) {
            resId = R.string.microphone;
        } else if ("android.hardware.nfc".equals(feature)) {
            resId = R.string.nfc;
        } else if ("android.hardware.sensor.accelerometer".equals(feature)) {
            resId = R.string.sensor_accelerometer;
        } else if ("android.hardware.sensor.barometer".equals(feature)) {
            resId = R.string.sensor_barometer;
        } else if ("android.hardware.sensor.compass".equals(feature)) {
            resId = R.string.sensor_compass;
        } else if ("android.hardware.sensor.gyroscope".equals(feature)) {
            resId = R.string.sensor_gyroscope;
        } else if ("android.hardware.sensor.light".equals(feature)) {
            resId = R.string.sensor_light;
        } else if ("android.hardware.sensor.proximity".equals(feature)) {
            resId = R.string.sensor_proximity;
        } else if ("android.hardware.telephony".equals(feature)) {
            resId = R.string.telephony;
        } else if ("android.hardware.telephony.cdma".equals(feature)) {
            resId = R.string.telephony_cdma;
        } else if ("android.hardware.telephony.gsm".equals(feature)) {
            resId = R.string.telephony_gsm;
        } else if ("android.hardware.touchscreen".equals(feature)) {
            resId = R.string.touchscreen;
        } else if ("android.hardware.touchscreen.multitouch".equals(feature)) {
            resId = R.string.touchscreen_multitouch;
        } else if ("android.hardware.touchscreen.multitouch.distinct".equals(feature)) {
            resId = R.string.touchscreen_multitouch_distinct;
        } else if ("android.hardware.touchscreen.multitouch.jazzhand".equals(feature)) {
            resId = R.string.touchscreen_multitouch_jazzhand;
        } else if ("android.hardware.wifi".equals(feature)) {
            resId = R.string.wifi;
        } else if ("android.software.live_wallpaper".equals(feature)) {
            resId = R.string.live_wallpaper;
        } else if ("android.software.sip".equals(feature)) {
            resId = R.string.sip;
        } else if ("android.software.sip.voip".equals(feature)) {
            resId = R.string.sip_voip;
        }

        return mListView.getResources().getString(resId);
    }
}
