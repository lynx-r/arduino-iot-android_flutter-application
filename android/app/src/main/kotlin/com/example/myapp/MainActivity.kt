package com.example.myapp

import com.amazonaws.services.iot.client.AWSIotException
import com.amazonaws.services.iot.client.AWSIotMqttClient
import com.amazonaws.services.iot.client.AWSIotQos
import com.amazonaws.services.iot.client.AWSIotMessage

import android.os.Bundle
import android.util.Log

import io.flutter.plugin.common.MethodChannel
import io.flutter.app.FlutterActivity
import io.flutter.plugins.GeneratedPluginRegistrant

class MyMessage(topic: String, qos: AWSIotQos, payload: String) : AWSIotMessage(topic, qos, payload) {

    override fun onSuccess() {
        // called when message publishing succeeded
    }

    override fun onFailure() {
        // called when message publishing failed
    }

    override fun onTimeout() {
        // called when message publishing timed out
    }
}

class MainActivity() : FlutterActivity() {
    private val CHANNEL = "samples.flutter.io/iot"

    private val TAG = "DEBUG"
    private val topic = "sdk/test/java"

    private val client: AWSIotMqttClient by lazy { connectToAws() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        GeneratedPluginRegistrant.registerWith(this)

        MethodChannel(flutterView, CHANNEL).setMethodCallHandler { call, result ->
            if (call.method == "publishToDevice") {
                publish(call.argument("payload"))
                result.success(true)
            } else {
                result.notImplemented()
            }
        }
    }

    fun connectToAws(): AWSIotMqttClient {
        Log.d(TAG, "connect")
        val client = AWSIotMqttClient("a1lv5xlogg9711.iot.eu-west-1.amazonaws.com", "123", "AKIAIKGUGEIUPJDR7QWQ", "hC/CJkCDj6fMb7IOFmfzXEmgAAnKRDReWA2l+n5t")
        client.connect()
        Log.d(TAG, client.getConnectionStatus().toString())
        return client
    }

    fun publish(payload: String) {
        val msg = MyMessage(topic, AWSIotQos.QOS0, payload)
        client.publish(msg, 3000)
    }
}
