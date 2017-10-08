package com.example.steve.humanity_hacks;

import android.app.Activity;
import android.app.Dialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.nearby.Nearby;
import com.google.android.gms.nearby.messages.Message;
import com.google.android.gms.nearby.messages.MessageListener;
import com.google.android.gms.nearby.messages.Strategy;
import com.google.android.gms.nearby.messages.SubscribeOptions;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.UUID;

public class MainActivity extends AppCompatActivity implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener{

    // Google API client variables
    GoogleApiClient mGoogleApiClient;
    Message activeMessage;
    MessageListener messageListener;

    // Component variables
    Button submitButton;
    EditText textEdit;
    EditText textEditUsername;
    EditText textEditProfession;
    ListView list;
    ProgressBar bar;
    View layout;

    // list init
    ArrayList<String> listItems = new ArrayList<String>();
    ArrayAdapter<String> adapter;
    private String android_id = UUID.randomUUID().toString();
    ArrayList<String[]> data;
    ArrayList<String> names;

    private String username;
    private String profession;

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

        textEdit = (EditText) findViewById(R.id.editText);
        textEditUsername = (EditText) findViewById(R.id.editTextUsername);
        textEditProfession = (EditText) findViewById(R.id.editTextProfession);
        list = (ListView) findViewById(R.id.list);
        bar = (ProgressBar) findViewById(R.id.spinner);
        layout = findViewById(R.id.mainLayout);

        names = new ArrayList<String>();
        data = new ArrayList<String[]>();

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, names);
        list.setAdapter(adapter);

        setupUI(layout);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id){
                final String[] itemset = data.get(position);

                //set up item send
                final Dialog dialog = new Dialog(MainActivity.this);
                dialog.setContentView(R.layout.dialog_display);

                TextView tvId = (TextView) dialog.findViewById(R.id.dialog_id);
                TextView tvName = (TextView) dialog.findViewById(R.id.dialog_name);
                TextView tvProfession = (TextView) dialog.findViewById(R.id.dialog_profession);

                tvId.setText(itemset[0]);
                tvName.setText(itemset[1]);
                tvProfession.setText(itemset[2]);

                Button dialogButton = (Button) dialog.findViewById(R.id.dialog_button);
                dialogButton.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        dialog.dismiss();
                    }
                });

                dialog.setTitle("Information");

                dialog.show();

                view.animate().setDuration(2000).alpha(0)
                        .withEndAction(new Runnable() {
                            @Override
                            public void run() {
                                adapter.notifyDataSetChanged();
                                view.setAlpha(1);
                            }
                        });
            }
        });

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Nearby.MESSAGES_API)
                .addConnectionCallbacks(this)
                .enableAutoManage(this, this)
                .addOnConnectionFailedListener(this)
                .build();

        messageListener = new MessageListener() {
            @Override
            public void onFound(Message message){
                String currMessage =  new String(message.getContent());
                addElementToList(currMessage);
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
        unpublish();
        unsubscribe();

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

        username = textEditUsername.getText().toString();
        profession = textEditProfession.getText().toString();

        bar.setVisibility(View.VISIBLE);

        message = android_id + "," + username + "," + profession + "," + message;
        activeMessage = new Message(message.getBytes());
        Nearby.Messages.publish(mGoogleApiClient, activeMessage);
    }

    private void unpublish(){
        Log.i("Unpublishing", "Unpublished");

        bar.setVisibility(View.GONE);


        if (activeMessage != null){
            Nearby.Messages.unpublish(mGoogleApiClient, activeMessage);
            activeMessage = null;
        }
    }

    // ===================== CONNECTION CALLBACKS ===========================

    @Override
    public void onConnected(Bundle connectionHint) {
        Log.e("connected", "successfully connected");
        subscribe();
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
    // when button is clicked
    public void click() {
        String currText = textEdit.getText().toString();
        publish(currText);
    }

    // adding element to list
    public void addElementToList(String currstr){
        listItems.add(currstr);

        String[] str = currstr.split(",");
        names.add(str[str.length - 1]);
        data.add(str);

        adapter.notifyDataSetChanged();
    }

    // ================== HIDE THE KEYBOARD ==========================
    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(), 0);
    }

    public void setupUI(View view) {

        // Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard(MainActivity.this);
                    return false;
                }
            });
        }

        //If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                setupUI(innerView);
            }
        }
    }

    //==================== OTHER METHODS =============================
    public void sendMessageToServer(String userFrom, String userTo, String message){
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                //URL url = new URL("");
                HttpURLConnection client = null;
                try{
                    //client = (HttpURLConnection) url.openConnection();

                    client.setRequestMethod("POST");
                    client.setDoOutput(true);

                    OutputStream outputPost = new BufferedOutputStream(client.getOutputStream());
                   /// writeStream(outputPost);
                    outputPost.flush();
                    outputPost.close();

                } catch(MalformedURLException error) {
                    Log.e("ERROR", "MalformedURLException Request");
                }
                catch(SocketTimeoutException error) {
                    Log.e("ERROR", "SocketTimeoutException Request");
                }
                catch (IOException error) {
                    Log.e("ERROR", "IOException Request");
                }
                finally {
                    if(client != null) // Make sure the connection is not null.
                        client.disconnect();
                }
            }
        });
    }
}