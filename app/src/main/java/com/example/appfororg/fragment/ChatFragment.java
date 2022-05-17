package com.example.appfororg.fragment;

import android.content.res.Resources;
import android.database.CursorIndexOutOfBoundsException;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appfororg.OpenHelper;
import com.example.appfororg.R;
import com.example.appfororg.adapter.ChatArrayAdapter;
import com.example.appfororg.domain.Message;
import com.example.appfororg.domain.Person;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ChatFragment extends Fragment {

    private ImageView imOrg, ivMicro;
    private TextView namePer;
    private TextView status;
    private EditText et_msg;
    private ImageView bt_arrow_back;
    private final int width  = Resources.getSystem().getDisplayMetrics().widthPixels;
    private final int height  = Resources.getSystem().getDisplayMetrics().heightPixels;
    private float scale = Resources.getSystem().getDisplayMetrics().density;



    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.chat_fragment, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        bt_arrow_back = getActivity().findViewById(R.id.bt_chat_arrowBack);
        et_msg = getActivity().findViewById(R.id.et_chat_msg);
        imOrg = getActivity().findViewById(R.id.iv_ch_imPer);
        ivMicro = getActivity().findViewById(R.id.iv_chat_micro);
        status = getActivity().findViewById(R.id.tv_chat_status);
        namePer = getActivity().findViewById(R.id.tv_ch_namePer);
        int data = Math.max(width, height);
        int size20 = (int) (scale * (data / 80) + 0.5f);
        int size10 = (int) (scale * (data / 140) + 0.5f);
        int size15 = (int) (scale * (data / 100) + 0.5f);
        int size5 = (int) (scale * (data / 320) + 0.5f);
        et_msg.setTextSize((float) data / 160);
        et_msg.setPadding(size15, size15, size5, size15);
        ConstraintLayout clForPhoto = getActivity().findViewById(R.id.cl_chat_forPhotoAndArrow);
        clForPhoto.setPadding(size10, 0, size20, 0);
        ConstraintLayout clForMicro = getActivity().findViewById(R.id.cl_chat_microAndClip);
        clForMicro.setPadding(size10, 0, size20, 0);
        clForMicro.setMinHeight(size20);
        imOrg.setPadding(size15, 0, 0, 0);
        namePer.setPadding(size15, size15, size15, size5);
        namePer.setTextSize((float) data / 160);
        status.setTextSize((float) data / 180);
        status.setPadding(size15, 0, size15, size15);
        ivMicro.setPadding(size5, 0, 0, 0);
        ImageView clip = getActivity().findViewById(R.id.iv_chat_clip);
        clip.setPadding(0, 0, size5,0);


        OpenHelper openHelper = new OpenHelper(getContext(), "op", null, OpenHelper.VERSION);
        int orgId = openHelper.findOrgByLogin(
                getArguments().getString("LOG")).getId();
        Person per = openHelper.findPersonByLogin(openHelper.findPersonById(1).getName());
        ChatArrayAdapter recyclerAdapter;
        RecyclerView rec = getActivity().findViewById(R.id.rec_chat);
        try {
            recyclerAdapter = new ChatArrayAdapter(getContext(),
                    ChatFragment.this, openHelper.findChatIdByOrgIdAndPerId(orgId, per.getId()));
            rec.setAdapter(recyclerAdapter);
            rec.scrollToPosition(openHelper.findMsgByChatId(
                    openHelper.findChatIdByOrgIdAndPerId(per.getId(),orgId)).size() - 1);
        }catch (CursorIndexOutOfBoundsException e){
            Log.e("TAG1", e.getMessage());}
        imOrg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundleForFullDesc = new Bundle();
                bundleForFullDesc.putString("LOG", getArguments().getString("LOG"));
                bundleForFullDesc.putString("NamePer", getArguments().getString("NamePer"));
                imOrg.setOnClickListener((view1) -> {
                    NavHostFragment.
                            findNavController(ChatFragment.this).navigate(
                            R.id.action_chatFragment_to_personProfileFragment, bundleForFullDesc);
                });
                imOrg.performClick();

            }
        });
        bt_arrow_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundleLog = new Bundle();
                bundleLog.putString("LOG", getArguments().getString("LOG"));
                bt_arrow_back.setOnClickListener((view1) -> {
                    NavHostFragment.
                            findNavController(ChatFragment.this).navigate(
                            R.id.action_chatFragment_to_listOfChatsFragment, bundleLog);
                });
                bt_arrow_back.performClick();
            }
        });

        imOrg.setImageBitmap(BitmapFactory.
                decodeByteArray(per.getPhotoPer(), 0, per.getPhotoPer().length));
        namePer.setText(per.getName());

        et_msg.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                ivMicro.setImageDrawable(getResources().getDrawable(R.drawable.bt_send_msg));
                ivMicro.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String curTime = new SimpleDateFormat(
                                "HH:mm", Locale.getDefault()).format(new Date());
                        Message myMsg = new Message("me",
                                openHelper.findChatIdByOrgIdAndPerId(orgId, per.getId()), et_msg.getText().toString(),
                                curTime);
                        openHelper.insertMsg(myMsg);
                        ChatArrayAdapter recyclerAdapter1 = new ChatArrayAdapter(getContext(),
                                ChatFragment.this, openHelper.
                                findChatIdByOrgIdAndPerId(orgId, per.getId()));
                        rec.setAdapter(recyclerAdapter1);
                        rec.scrollToPosition(openHelper.findMsgByChatId(
                                openHelper.findChatIdByOrgIdAndPerId(orgId, per.getId())).size() - 1);
                        et_msg.setText("");
                    }
                });
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(et_msg.getText().toString().isEmpty()){
                    ivMicro.setImageDrawable(getResources().getDrawable(R.drawable.microphone));
                    ivMicro.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                        }
                    });
                }
            }
        });

    }

}
