package com.example.appfororg.fragment;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.appfororg.OpenHelper;
import com.example.appfororg.R;
import com.example.appfororg.domain.Organization;
import com.example.appfororg.rest.AppApiVolley;

public class OrgProfileFragment extends Fragment {

    ImageView iv_orgAva;
    EditText et_desc, et_needs;
    TextView tv_name, tv_type, tv_address, tv_link;
    ImageView bt_createDesc, bt_createNeeds;
    ImageView bt_listOfChats;
    static boolean isDescCompleted = true;
    static boolean isNeedsCompleted = true;
    private final int height  = Resources.getSystem().getDisplayMetrics().heightPixels;
    private final int width  = Resources.getSystem().getDisplayMetrics().widthPixels;
    private float scale = Resources.getSystem().getDisplayMetrics().density;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.org_profile_fragment, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        iv_orgAva = getActivity().findViewById(R.id.iv_org_profile_photoOrg);
        et_desc = getActivity().findViewById(R.id.ed_org_profile_descOrg);
        et_needs = getActivity().findViewById(R.id.ed_org_profile_needsOrg);
        tv_address = getActivity().findViewById(R.id.tv_org_profile_addressOrg);
        tv_name = getActivity().findViewById(R.id.tv_org_profile_nameOrg);
        bt_listOfChats = getActivity().findViewById(R.id.bt_org_profile_chat);
        tv_type = getActivity().findViewById(R.id.tv_org_profile_typeOrg);
        tv_link = getActivity().findViewById(R.id.tv_org_profile_linkToWeb);
        bt_createDesc = getActivity().findViewById(R.id.iv_org_profile_createDesc);
        bt_createNeeds = getActivity().findViewById(R.id.iv_org_profile_createNeeds);
        TextView prof = getActivity().findViewById(R.id.tv_prof_profile);
        LinearLayout linearLayoutPhotoName = getActivity().findViewById(R.id.ll_prof_photoAndName);
        OpenHelper openHelper = new OpenHelper
                (getContext(), "op", null, OpenHelper.VERSION);
        Organization organization = openHelper.findOrgByLogin(getArguments().getString("LOG"));
        int data = Math.max(width, height);

            int size20 = (int) (scale * (data / 80) + 0.5f);
            int size10 = (int) (scale * (data / 140) + 0.5f);
            int size50 = (int) (scale * (data / 37) + 0.5f);
            int size5 = (int) (scale * (data / 320) + 0.5f);
            int size30 = (int) (scale * (data / 60) + 0.5f);
            int size45 = (int) (scale * (data / 44) + 0.5f);
            int size60 = (int) (scale * (data / 30) + 0.5f);
            linearLayoutPhotoName.setPadding(size20, size30, size20, size20);
            prof.setPadding(size30, size60, size30, 0);
            prof.setTextSize((float) data / 85);
            tv_name.setTextSize((float)data / 111);
            tv_name.setPadding(size30, size45, size30, size45);
            float sizeForTV15 = (float) data / 160;
            tv_type.setTextSize(sizeForTV15);
            tv_type.setPadding(size30, 0, size20, size10);
            TextView tv_forType = getActivity().findViewById(R.id.tv_prof_forType);
            tv_forType.setPadding(size30, 0, size20, size10);
            tv_forType.setTextSize(sizeForTV15);
            TextView tv_forDesc = getActivity().findViewById(R.id.tv_prof_forDesc);
            tv_forDesc.setPadding(size30, 0, size20, size10);
            tv_forDesc.setTextSize(sizeForTV15);
            LinearLayout linearLayoutForEditDesc = getActivity().findViewById(R.id.ll_prof_forEdit);
            linearLayoutForEditDesc.setPadding(size10, 0, 0, size10);
            TextView tv_forNeeds = getActivity().findViewById(R.id.tv_prof_forNeeds);
            tv_forNeeds.setTextSize(sizeForTV15);
            tv_forNeeds.setPadding(size30, 0, size20, size10);
            LinearLayout linearLayoutForEditNeeds = getActivity().findViewById(R.id.ll_prof_forEditNeeds);
            linearLayoutForEditNeeds.setPadding(size10, 0, 0, size10);
            tv_address.setTextSize(sizeForTV15);
            tv_address.setPadding(size30, 0, size20, size10);
            TextView tv_forAddress = getActivity().findViewById(R.id.tv_prof_forAddress);
            tv_forAddress.setPadding(size30, 0, size20, size10);
            tv_forAddress.setTextSize(sizeForTV15);
            tv_link.setTextSize(sizeForTV15);
            tv_link.setPadding(size30, 0, size20, size45);
            TextView tv_forLink = getActivity().findViewById(R.id.tv_prof_forLink);
            tv_forLink.setPadding(size30, 0, size20, size10);
            tv_forLink.setTextSize(sizeForTV15);
            bt_listOfChats.setMaxHeight(data / 20);
            bt_listOfChats.setPadding(0, size10, 0, size5);
            ImageView bt_prof = getActivity().findViewById(R.id.bt_org_profile_profile);
            bt_prof.setPadding(0, size5, 0, size5);




        ConstraintLayout constraintLayout = getActivity().findViewById(R.id.cl_prof);
        constraintLayout.setMinHeight(size50);
        bt_createDesc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isDescCompleted) {
                    et_desc.setEnabled(true);
                    et_desc.requestFocus();
                    et_desc.setFocusableInTouchMode(true);
                    InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(
                            Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.showSoftInput(et_desc, InputMethodManager.SHOW_FORCED);
                    bt_createDesc.setImageDrawable(getResources().getDrawable(R.drawable.check_mark));
                    isDescCompleted = !isDescCompleted;
                }
                else{
                    new AppApiVolley(getContext()).updateOrganization(
                            organization.getId(), organization.getName(), organization.getLogin(),
                            organization.getType(), organization.getPhotoOrg(), et_desc.getText().toString(),
                            organization.getAddress(), organization.getNeeds(), organization.getLinkToWebsite(),
                            organization.getPass()
                    );
                    bt_createDesc.setImageDrawable(getResources().getDrawable(R.drawable.iv_write));
                    et_desc.setEnabled(false);
                    et_desc.setFocusableInTouchMode(false);
                    et_desc.setImeOptions(EditorInfo.IME_ACTION_DONE);
                    isDescCompleted = !isDescCompleted;
                    openHelper.changeDescByLog(getArguments().getString("LOG"),
                            et_desc.getText().toString());
                }
            }
        });
        bt_createNeeds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isNeedsCompleted) {
                    et_needs.setEnabled(true);
                    et_needs.requestFocus();
                    et_needs.setFocusableInTouchMode(true);
                    InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(
                            Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.showSoftInput(et_needs, InputMethodManager.SHOW_FORCED);
                    bt_createNeeds.setImageDrawable(getResources().getDrawable(R.drawable.check_mark));
                    isNeedsCompleted = !isNeedsCompleted;

                }
                else{
                    new AppApiVolley(getContext()).updateOrganization(
                            organization.getId(), organization.getName(), organization.getLogin(),
                            organization.getType(), organization.getPhotoOrg(), organization.getDescription(),
                            organization.getAddress(), et_needs.getText().toString(), organization.getLinkToWebsite(),
                            organization.getPass()
                    );
                    bt_createNeeds.setImageDrawable(getResources().getDrawable(R.drawable.iv_write));
                    et_needs.setEnabled(false);
                    et_needs.setFocusableInTouchMode(false);
                    et_needs.setImeOptions(EditorInfo.IME_ACTION_DONE);
                    isNeedsCompleted = !isNeedsCompleted;
                    openHelper.changeNeedsByLog(getArguments().getString("LOG"),
                            et_needs.getText().toString());
                }
            }
        });

        Bitmap bitmap = BitmapFactory.
                decodeByteArray(organization.getPhotoOrg(), 0, organization.getPhotoOrg().length);
        RoundedBitmapDrawable roundDrawable = RoundedBitmapDrawableFactory.create(getResources(), bitmap);
        roundDrawable.setCornerRadius(Math.max(roundDrawable.getMinimumWidth(),
                roundDrawable.getMinimumHeight()));
        roundDrawable.setCircular(true);
        iv_orgAva.setImageDrawable(roundDrawable);
        tv_type.setText(organization.getType());
        tv_address.setText(organization.getAddress());
        tv_name.setText(organization.getName());
        tv_link.setText(organization.getLinkToWebsite() == null ? "(не указан)"
                : organization.getLinkToWebsite());
        et_desc.setText(organization.getDescription(), null);
        et_needs.setText(organization.getNeeds(), null);
        bt_listOfChats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundleLog = new Bundle();
                bundleLog.putString("LOG", getArguments().getString("LOG"));
                bt_listOfChats.setOnClickListener((view1) -> {
                    NavHostFragment.
                            findNavController(OrgProfileFragment.this).navigate(
                            R.id.action_orgProfileFragment_to_listOfChatsFragment, bundleLog);
                });
                bt_listOfChats.performClick();
            }
        });
    }
}
