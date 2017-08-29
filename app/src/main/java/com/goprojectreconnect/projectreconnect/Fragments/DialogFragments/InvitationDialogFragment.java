package com.goprojectreconnect.projectreconnect.Fragments.DialogFragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.goprojectreconnect.projectreconnect.Models.Notification;
import com.goprojectreconnect.projectreconnect.R;
import com.goprojectreconnect.projectreconnect.ReConnectApplication;
import com.parse.ParseUser;

/**
 * A simple {@link Fragment} subclass.
 */
public class InvitationDialogFragment extends DialogFragment {

    private EditText etMessage;
    ImageButton btSend;
    private ParseUser currentUser;

    public InvitationDialogFragment() {
        // Required empty public constructor
    }

    public static InvitationDialogFragment newInstance(ParseUser recipient) {
        InvitationDialogFragment frag = new InvitationDialogFragment();

        Bundle args = new Bundle();
        args.putParcelable("recipient", recipient);
        frag.setArguments(args);

        return frag;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        currentUser = ReConnectApplication.getCurrentUser();
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_invitation_dialog_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Get field from view
        etMessage = (EditText) view.findViewById(R.id.etMessage);
        btSend = (ImageButton) view.findViewById(R.id.ivSend);

        // set listener
        btSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Notification notification = new Notification();

                // Fetch arguments from bundle and set title
                ParseUser recipient = getArguments().getParcelable("recipient");

                notification.setRecipient(recipient);
                notification.setSender(currentUser);
                notification.setRecipientName(currentUser.getString("name"));
                notification.setNotificationImageUrl(currentUser.getString("profile_image_url"));
                notification.setMessage(etMessage.getText().toString());

                notification.saveInBackground();

                Toast.makeText(getContext(), "Successfully sent invitation", Toast.LENGTH_SHORT).show();
                // dismiss dialog
                dismiss();
            }
        });

        getDialog().setTitle("Invite to ReConnect");
        // Show soft keyboard automatically and request focus to field
        etMessage.requestFocus();
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }
}

