package com.example.voicedistancetracker;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class MainActivity extends AppCompatActivity {
    private static final int SAMPLE_RATE = 44100;
    private static final int BUFFER_SIZE = AudioRecord.getMinBufferSize(SAMPLE_RATE, AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT);
    private static final double SOUND_SPEED = 343.0; // m/s
    private static final int AMPLITUDE_THRESHOLD = 5000;

    private AudioRecord audioRecord;
    private boolean isRecording = false;
    private boolean isSpeaking = false;
    private long speakingStartTime = 0;
    private double totalVoiceDuration = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, 1);
        }

        startRecording();
    }

    private void startRecording() {
        audioRecord = new AudioRecord(
            MediaRecorder.AudioSource.MIC,
            SAMPLE_RATE,
            AudioFormat.CHANNEL_IN_MONO,
            AudioFormat.ENCODING_PCM_16BIT,
            BUFFER_SIZE
        );

        isRecording = true;
        audioRecord.startRecording();

        new Thread(() -> {
            short[] buffer = new short[BUFFER_SIZE];
            while (isRecording) {
                int read = audioRecord.read(buffer, 0, BUFFER_SIZE);
                if (read > 0) {
                    double amplitude = calculateAmplitude(buffer);
                    boolean voiceDetected = amplitude > AMPLITUDE_THRESHOLD;

                    if (voiceDetected && !isSpeaking) {
                        isSpeaking = true;
                        speakingStartTime = System.currentTimeMillis();
                        Log.d("VoiceDetection", "Speaking started at: " + speakingStartTime);
                    } else if (!voiceDetected && isSpeaking) {
                        isSpeaking = false;
                        long speakingEndTime = System.currentTimeMillis();
                        long speakingDuration = speakingEndTime - speakingStartTime;
                        totalVoiceDuration += speakingDuration;
                        Log.d("VoiceDetection", "Speaking ended. Duration: " + speakingDuration + " ms");
                        updateDistance();
                    }
                }
            }
        }).start();
    }

    private double calculateAmplitude(short[] buffer) {
        double sum = 0;
        for (short sample : buffer) {
            sum += Math.abs(sample);
        }
        return sum / buffer.length;
    }

    private void updateDistance() {
        double seconds = totalVoiceDuration / 1000.0;
        double meters = seconds * SOUND_SPEED;

        runOnUiThread(() -> {
            TextView tv = findViewById(R.id.tvDistance);
            if (meters > 1000) {
                tv.setText(String.format("Distance: %.2f kilometers", meters / 1000));
            } else {
                tv.setText(String.format("Distance: %.2f meters", meters));
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isRecording = false;
        if (audioRecord != null) {
            audioRecord.stop();
            audioRecord.release();
        }
    }
}