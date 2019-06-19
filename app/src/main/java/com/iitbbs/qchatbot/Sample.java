package com.iitbbs.qchatbot;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.iitbbs.qchatbot.Adapter.CustomAdapter;
import com.iitbbs.qchatbot.model.ChatModel;

import java.util.ArrayList;
import java.util.List;

import ai.api.AIServiceException;
import ai.api.android.AIConfiguration;
import ai.api.android.AIDataService;
import ai.api.model.AIRequest;
import ai.api.model.AIResponse;

public class Sample extends AppCompatActivity {

    ListView listView;
    EditText editText;
    List<ChatModel> listChatModels = new ArrayList<>();
    FloatingActionButton btn_send_message;

    private AIDataService aiDataService;
    private AIRequest aiRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);

        listView = (ListView)findViewById(R.id.list_of_message);
        editText = (EditText)findViewById(R.id.user_message);
        btn_send_message = (FloatingActionButton)findViewById(R.id.fab);

        init();

        btn_send_message.setOnClickListener(new View.OnClickListener() {
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
            ChatModel chatModel = new ChatModel(aiResponse.getResult().getFulfillment().getSpeech(), false);
            listChatModels.add(chatModel);
            CustomAdapter adapter = new CustomAdapter(listChatModels, getApplicationContext());
            listView.setAdapter(adapter);

        }

    }



    // sending the query message to dialogflow
    private void  sendMessage(View view) {

        String msg = editText.getText().toString();
        if(msg.trim().isEmpty()) {
            Toast.makeText(this, "Please Enter Your Query first", Toast.LENGTH_SHORT).show();
        } else {
            ChatModel model = new ChatModel(msg, true);
            listChatModels.add(model);

            aiRequest.setQuery(msg);
            editText.setText("");
            RequestTask requestTask = new RequestTask(Sample.this, aiDataService);
            requestTask.execute(aiRequest);
        }
    }


}
