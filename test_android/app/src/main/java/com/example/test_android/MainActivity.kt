package com.example.test_android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.widget.Button
import android.widget.TextView
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.android.FlutterActivityLaunchConfigs
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.embedding.engine.FlutterEngineCache
import io.flutter.embedding.engine.dart.DartExecutor
import io.flutter.plugin.common.MethodChannel
import org.json.JSONObject

private const val FLUTTER_ENGINE_NAME = "test_flutter_engine_name"

private const val CHANNEL = "com.example.test_android/result"

class MainActivity : AppCompatActivity() {

    private lateinit var flutterEngine: FlutterEngine

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        warmupFlutterEngine()

        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE)

        setContentView(R.layout.activity_main)

        val flutterLaunchBtn = findViewById<Button>(R.id.flutter_btn)
        val flutterNumber = findViewById<TextView>(R.id.flutter_number)

        flutterLaunchBtn.setOnClickListener {
            var flutNumVal = flutterNumber.text?.toString()?.toIntOrNull()

            val json = JSONObject()

            json.put("amount", flutNumVal)

            println(json)

            val methodChannel = MethodChannel(flutterEngine.dartExecutor.binaryMessenger, CHANNEL)

            methodChannel.invokeMethod("getNumber", json.toString())

            runFlutterNPS()
        }

    }

    private fun runFlutterNPS() {
        startActivity(
            FlutterActivity.withCachedEngine(FLUTTER_ENGINE_NAME)
                .backgroundMode(FlutterActivityLaunchConfigs.BackgroundMode.transparent)
                .build(this)
        )
    }

    private fun warmupFlutterEngine() {
        flutterEngine = FlutterEngine(this)

        flutterEngine.dartExecutor.executeDartEntrypoint(
            DartExecutor.DartEntrypoint.createDefault()
        )

        FlutterEngineCache
            .getInstance()
            .put(FLUTTER_ENGINE_NAME, flutterEngine)
    }
}