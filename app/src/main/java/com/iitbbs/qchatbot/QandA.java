/*
This section for testing purpose, it has no
connection with other parts.
 */


package com.iitbbs.qchatbot;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import ai.api.AIServiceException;
import ai.api.android.AIConfiguration;
import ai.api.android.AIDataService;
import ai.api.model.AIRequest;
import ai.api.model.AIResponse;

public class QandA extends AppCompatActivity{

    private EditText query;
    private TextView response;
    private Button responseButton;

    private AIDataService aiDataService;
    private AIRequest aiRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qand);

        query = (EditText)findViewById(R.id.query);
        responseButton = (Button)findViewById(R.id.responsebtn);
        response = (TextView)findViewById(R.id.responseText);

        init();

        responseButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                sendMessage(v);
            }
        });

        
    }

    // intializing
    private void init() {
        final AIConfiguration config = new AIConfiguration("181eb55f857949bd94094e0610890817",
                AIConfiguration.SupportedLanguages.English,
                AIConfiguration.RecognitionEngine.System);

        aiDataService = new AIDataService(this, config);
        aiRequest = new AIRequest();
    }

    // excuting task asynchronously
    private class RequestTask extends AsyncTask<AIRequest, Void, AIResponse> {

        private Activity activity;
        private AIDataService aiDataService;
        public RequestTask(Activity activity, AIDataService aiDataService) {
            this.activity = activity;
            this.aiDataService = aiDataService;
        }

        @Override
        protected AIResponse doInBackground(AIRequest... requests) {

            final AIRequest request = requests[0];
            try {
                final AIResponse airesponse = aiDataService.request(aiRequest);
                return airesponse;
            } catch (AIServiceException e) {
            }
            return null;
        }

        protected void onPostExecute(AIResponse aiResponse) {
            ((QandA)activity).callback(aiResponse);
        }

    }
    public void callback(AIResponse aiResponse) {
        if(aiResponse != null){
            String reply = aiResponse.getResult().getFulfillment().getSpeech();
            response.setText(reply);
        }
    }



    // sending the query message to dialogflow
    private void  sendMessage(View view) {

        String msg = query.getText().toString();
        if(msg.trim().isEmpty()) {
            Toast.makeText(this, "Please Enter Your Query first", Toast.LENGTH_SHORT).show();
        } else {
            aiRequest.setQuery(msg);
            query.setText("");
            RequestTask requestTask = new RequestTask(QandA.this, aiDataService);
            requestTask.execute(aiRequest);
        }
    }


    // same way as above but above code is more structured
    /*private class ResponseTask extends AsyncTask<AIRequest, Void, AIResponse> {
        protected AIResponse doInBackground(AIRequest... requests) {
            final AIRequest request = requests[0];
            try {
                final AIResponse airesponse = aiDataService.request(aiRequest);
                return airesponse;
            } catch (AIServiceException e) {
            }
            return null;
        }

        protected void onPostExecute(AIResponse aiResponse) {
            if (aiResponse != null) {
                /*Result result = aiResponse.getResult();
                String speech = result.getFulfillment().getSpeech();

                response.setText(speech);
                query.setText("");
                response.setText("processing....");
                onResult(aiResponse);
            }
        }
    }*/

}
