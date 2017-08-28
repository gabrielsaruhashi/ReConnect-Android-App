package com.goprojectreconnect.projectreconnect.Activities;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.goprojectreconnect.projectreconnect.Fragments.DialogFragments.InvitationDialogFragment;
import com.goprojectreconnect.projectreconnect.R;

public class AddReconnectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reconnection);
    }

    public void showInvitationDialog() {
        FragmentManager fm = getSupportFragmentManager();
        InvitationDialogFragment invitationDialogFragment = InvitationDialogFragment.newInstance();
        invitationDialogFragment.show(fm, "fragment_invitation_dialog_fragment");
    }

}
