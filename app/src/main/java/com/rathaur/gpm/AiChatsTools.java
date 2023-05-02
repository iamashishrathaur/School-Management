package com.rathaur.gpm;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rathaur.gpm.Adepter.AiAdepter;
import com.rathaur.gpm.DataBaseModal.AiMessageModal;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AiChatsTools extends AppCompatActivity {
   String API_KEY="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";
   RecyclerView recyclerView;
   EditText editText;
   ImageView send;
   RelativeLayout back;
   List<AiMessageModal> messageModalList;
   AiAdepter adepter;
   public static final MediaType JSON
            = MediaType.get("application/json; charset=utf-8");

   final OkHttpClient client = new OkHttpClient();

    @Override
   protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ai_chats_tools);
        Objects.requireNonNull(getSupportActionBar()).hide();
        recyclerView=findViewById(R.id.chat_bot_show);
        editText=findViewById(R.id.chat_bot_edit_text);
        send=findViewById(R.id.chat_bot_send_button);
        back=findViewById(R.id.chat_bot_back_pressed);
        messageModalList=new ArrayList<>();
        adepter=new AiAdepter(messageModalList);
        recyclerView.setAdapter(adepter);
        LinearLayoutManager manager=new LinearLayoutManager(this);
        manager.setStackFromEnd(true);
        recyclerView.setLayoutManager(manager);

        send.setOnClickListener(view -> {
            String question=editText.getText().toString().trim();
            if (!question.trim().isEmpty()){
                addToChat(question,AiMessageModal.SENT_BY_ME);
                editText.setText("");
                callApi(question);
            }
        });
        back.setOnClickListener(view -> onBackPressed());
    }

    @SuppressLint("NotifyDataSetChanged")
    private void addToChat(String message, String sentBy) {
        runOnUiThread(() -> {
            messageModalList.add(new AiMessageModal(message,sentBy));
            adepter.notifyDataSetChanged();
            recyclerView.scrollToPosition(messageModalList.size());
        });

    }
    void addResponse(String response){
      messageModalList.remove(messageModalList.size()-1);
      addToChat(response,AiMessageModal.SENT_BY_BOT);
    }

    void callApi(String question){
        messageModalList.add(new AiMessageModal("Typing...",AiMessageModal.SENT_BY_BOT));
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("model","text-davinci-003");
            jsonObject.put("prompt",question);
            jsonObject.put("max_tokens",4000);
            jsonObject.put("temperature",0);

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        RequestBody body=RequestBody.create(jsonObject.toString(),JSON);
        Request request=new Request.Builder().url("https://api.openai.com/v1/completions")
                .header("Authorization","Bearer "+API_KEY)
                .post(body).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                addResponse("Failed to load response due "+e.getMessage());
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                 if (response.isSuccessful()){
                     JSONObject jsonObject1;
                     try {
                         jsonObject1 = new JSONObject(Objects.requireNonNull(response.body()).string());
                         JSONArray jsonArray=jsonObject1.getJSONArray("choices");
                         String result=jsonArray.getJSONObject(0).getString("text");
                         addResponse(result.trim());
                     } catch (JSONException e) {
                         throw new RuntimeException(e);
                     }


                 }
                 else {
                     addResponse("Failed to load response due "+ Objects.requireNonNull(response.body()).string());
                 }
            }
        });
    }
}