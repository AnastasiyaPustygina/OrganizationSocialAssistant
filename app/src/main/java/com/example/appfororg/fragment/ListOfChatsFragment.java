package com.example.appfororg.fragment;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appfororg.OpenHelper;
import com.example.appfororg.adapter.ChatListArrayAdapter;
import com.example.appfororg.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ListOfChatsFragment extends Fragment {
    private ChatListArrayAdapter chatListArrayAdaptor;
    private final int height  = Resources.getSystem().getDisplayMetrics().heightPixels;
    private final int width  = Resources.getSystem().getDisplayMetrics().widthPixels;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.list_of_chats_fragment, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        TextView tv_chats = getActivity().findViewById(R.id.tv_listOfChats_chats);
        if (width > height) {
            tv_chats.setTextSize((float) (width / 80));
        } else {
            tv_chats.setTextSize((float) (height / 80));
        }
        OpenHelper openHelper = new OpenHelper(getContext(),
                "op", null, OpenHelper.VERSION);
        try {
            Log.e("ALL CHAT ID", "ID: " + openHelper.findAllChatId().toString());
        }catch (Exception e){
            Log.e("FIND ALL CHAT ID", e.getMessage());
        }
        String curTime = new SimpleDateFormat(
                "HH:mm:ss:mm", Locale.getDefault()).format(new Date());
        Log.e(curTime, "СОЗДАНИЕ ФРАГМЕНТА");
        RecyclerView recyclerView = getActivity().findViewById(R.id.rec_listOfChats);
        chatListArrayAdaptor = new ChatListArrayAdapter(getContext(),
                ListOfChatsFragment.this, getArguments().getString("LOG"));
        recyclerView.setAdapter(chatListArrayAdaptor);
        ImageView bt_prof;
        float scale = Resources.getSystem().getDisplayMetrics().density;
        int data = Math.max(width, height);
        int size10 = (int) (scale * (data / 140) + 0.5f);
        int size5 = (int) (scale * (data / 320) + 0.5f);
        bt_prof = getActivity().findViewById(R.id.bt_listOfChats_profile);
        bt_prof.setPadding(0, size5, 0, size5);
        ImageView bt_listOfChats = getActivity().findViewById(R.id.bt_listOfChats_chat);
        ConstraintLayout constraintLayout = getActivity().findViewById(R.id.cl_listOfChats);
        int size50 = (int) (scale * (data / 37) + 0.5f);
        constraintLayout.setMinHeight(size50);
        bt_listOfChats.setPadding(0, size10, 0, size5);
        Bundle bundleLog = new Bundle();
        bundleLog.putString("LOG", getArguments().getString("LOG"));



        bt_prof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bt_prof.setOnClickListener((view1) -> {
                    NavHostFragment.
                            findNavController(ListOfChatsFragment.this).navigate(
                            R.id.action_listOfChatsFragment_to_orgProfileFragment, bundleLog);
                });
                bt_prof.performClick();
            }
        });
    }
}
