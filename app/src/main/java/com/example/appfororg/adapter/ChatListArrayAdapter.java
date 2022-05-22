package com.example.appfororg.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.database.CursorIndexOutOfBoundsException;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appfororg.OpenHelper;
import com.example.appfororg.R;
import com.example.appfororg.fragment.ListOfChatsFragment;
import com.example.appfororg.domain.Person;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;

public class ChatListArrayAdapter extends RecyclerView.Adapter<ChatListArrayAdapter.ViewHolder>{

    private Context context;
    private LayoutInflater inflater;
    private ListOfChatsFragment fragment;
    private String log;
    private final int width  = Resources.getSystem().getDisplayMetrics().widthPixels;
    private final int height  = Resources.getSystem().getDisplayMetrics().heightPixels;
    private float scale = Resources.getSystem().getDisplayMetrics().density;

    public ChatListArrayAdapter(Context context, ListOfChatsFragment fragment, String log) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.fragment = fragment;
        this.log = log;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.small_chat_window, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String curTime = new SimpleDateFormat(
                "HH:mm:ss:mm", Locale.getDefault()).format(new Date());
        Log.e(curTime, "НАЧАЛО РАБОТЫ АДАПТЕРА");
        OpenHelper openHelper = new OpenHelper(context, "op", null, OpenHelper.VERSION);
        ArrayList<String> arrListLastMsg = openHelper.findLastMsgValuesByOrgLog(log);
        ArrayList<Integer> arrListChatId = openHelper.findLastChatId(log);
        Log.e("LastChatId", arrListChatId.toString());
        Log.e("LastMSG", arrListLastMsg.toString());

        curTime = new SimpleDateFormat(
                "HH:mm:ss:mm", Locale.getDefault()).format(new Date());
        Log.e(curTime, "ПОСЛЕ ПОЛУЧЕНИЯ ПОСЛЕДНИХ СОО И ЧАТОВ");
        ArrayList<Person> arrayListLastPer = new ArrayList<>();
        for (int i = 0; i < arrListChatId.size(); i++) {
                arrayListLastPer.add(openHelper.findPersonByChatId(arrListChatId.get(i)));
        }
        curTime = new SimpleDateFormat(
                "HH:mm:ss:mm", Locale.getDefault()).format(new Date());
        Log.e(curTime, "ПОСЛЕ СОЗДАНИЯ ЛИСТА ЛЮДЕЙ");
        try {
        Collections.reverse(arrayListLastPer);
            holder.ivPerAva.setImageBitmap(BitmapFactory.decodeByteArray(arrayListLastPer.get
                            (position).getPhotoPer(), 0,
                    arrayListLastPer.get(position).getPhotoPer().length));
            holder.lastMsg.setText(arrListLastMsg.get(position));
            holder.tvNamePer.setText(arrayListLastPer.get(position).getName());
            curTime = new SimpleDateFormat(
                    curTime, Locale.getDefault()).format(new Date());
            Log.e("FOURTH", "ПОСЛЕ ПОМЕЩЕНИЯ ТЕКСТА");
            Bundle bundle = new Bundle();
            bundle.putString("LOG", log);
            bundle.putString("NamePer", arrayListLastPer.get(arrayListLastPer.size() - position - 1).getName());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    holder.itemView.setOnClickListener((view1) -> {
                        NavHostFragment.
                                findNavController(fragment).navigate(
                                R.id.action_listOfChatsFragment_to_chatFragment, bundle);
                    });
                    holder.itemView.performClick();
                }
            });
        } catch (Exception e){
            Log.e("TAG2", e.getMessage() + e.toString());
        }
    }

    @Override
    public int getItemCount() {
        OpenHelper openHelper = new OpenHelper(context, "op", null, OpenHelper.VERSION);
        try{
            return openHelper.findLastMsgValuesByOrgLog(log).size();
        }catch (CursorIndexOutOfBoundsException e){return 0;}
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView ivPerAva;
        TextView tvNamePer, lastMsg;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivPerAva = itemView.findViewById(R.id.iv_SmallChatWin_avaPer);
            tvNamePer = itemView.findViewById(R.id.tv_SmallChatWin_nameOfPer);
            lastMsg = itemView.findViewById(R.id.tv_SmallChatWin_lastMsg);
            int data = Math.max(width, height);
            int size20 = (int) (scale * (data / 80) + 0.5f);
            int size10 = (int) (scale * (data / 140) + 0.5f);
            tvNamePer.setTextSize((float) data / 140);
            lastMsg.setTextSize((float) data / 170);
            tvNamePer.setPadding(size10, size20, size10, size10);
            lastMsg.setPadding(size10, 0, size10, size10);
        }
    }
}
