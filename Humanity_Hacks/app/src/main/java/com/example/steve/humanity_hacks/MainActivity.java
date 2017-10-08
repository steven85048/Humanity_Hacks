package com.example.steve.humanity_hacks;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.nearby.Nearby;
import com.google.android.gms.nearby.messages.Message;
import com.google.android.gms.nearby.messages.MessageListener;
import com.google.android.gms.nearby.messages.Strategy;
import com.google.android.gms.nearby.messages.SubscribeOptions;

public class MainActivity extends AppCompatActivity implements
                                                        GoogleApiClient.ConnectionCallbacks,
                                                        GoogleApiClient.OnConnectionFailedListener{

    // Google API client variables
    GoogleApiClient mGoogleApiClient;
    Message activeMessage;
    MessageListener messageListener;

    // Component variables
    Button submitButton;
    TextView textDisplay;
    EditText textEdit;

    // ========================== MAIN THREAD THREADS ==============================


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Component set up
        submitButton = (Button) findViewById(R.id.submitButton);
        submitButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                click();
            }
        });
        textDisplay = (TextView) findViewById(R.id.textDisplay);
        textEdit = (EditText) findViewById(R.id.editText);

        // Google Client set up
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Nearby.MESSAGES_API)
                .addConnectionCallbacks(this)
                .enableAutoManage(this, this)
                .addOnConnectionFailedListener(this)
                .build();

        // Message Listener
        messageListener = new MessageListener() {
            @Override
            public void onFound(Message message){
                String currMessage =  new String(message.getContent());
                textDisplay.setText(currMessage);
                Log.e("Found message: ", currMessage);
            }

            @Override
            public void onLost(Message message){
                String currMessage = new String(message.getContent());
                Log.e("Lost message: ", currMessage);
            }
        };
    }

    @Override
    public void onStart(){
        Log.e("Starting", "Starting");
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    public void onStop() {
        unsubscribe();
        unpublish();

        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }

        super.onStop();
    }

    // ============================== MESSAGE RECEPTION SERVICE ==========================

    // Subscribe to receive messages.
    private void subscribe() {
        Log.i("Subscribing", "Subscribing.");
        SubscribeOptions options = new SubscribeOptions.Builder()
                            .setStrategy(Strategy.BLE_ONLY)
                            .build();
        Nearby.Messages.subscribe(mGoogleApiClient,messageListener, options);
    }

    // Unsubscribe to receiving messages
    private void unsubscribe(){
        Log.i("Unsubscribing", "Unsubscribing");
        Nearby.Messages.unsubscribe(mGoogleApiClient, messageListener);
    }

    // ============================== MESSAGE PUBLISHING SERVICE =========================

    private void publish(String message){
        Log.e("Message", message);
        activeMessage = new Message(message.getBytes());
        Nearby.Messages.publish(mGoogleApiClient, activeMessage);
    }

    private void unpublish(){
        Log.i("Unpublishing", "Unpublished");
        if (activeMessage != null){
            Nearby.Messages.unpublish(mGoogleApiClient, activeMessage);
            activeMessage = null;
        }
    }

    // ===================== CONNECTION CALLBACKS ===========================

    @Override
    public void onConnected(Bundle connectionHint) {
        Log.e("fuck", "fuck me in the asshole");
        subscribe();
        // TODO
    }

    @Override
    public void onConnectionSuspended(int cause) {
        Log.e("connection suspended", "suspended");
        // TODO
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        String s = result.getErrorMessage();
        if (s == null)
            Log.e("error", "failed");
        // TODO
    }

    // ===================== Component Events ========================
    public void click() {
        String currText = textEdit.getText().toString();
        publish(currText);
    }
}
