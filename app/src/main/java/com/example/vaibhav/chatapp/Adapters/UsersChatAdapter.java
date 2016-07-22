package com.example.vaibhav.chatapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;

import com.example.vaibhav.chatapp.POJO.*;
import com.example.vaibhav.chatapp.ChatActivity;
import com.example.vaibhav.chatapp.R;

/**
 * Created by vaibhav on 20-Jul-16.
 */
public class UsersChatAdapter extends RecyclerView.Adapter<UsersChatAdapter.ViewHolderUsers> {
    private List<UsersChatModel> mFireChatUsers;
    private Context mContext;
    private String mCurrentUserName;
    private String mCurrentUserCreatedAt;

    public UsersChatAdapter(Context context, List<UsersChatModel> fireChatUsers) {
        mFireChatUsers=fireChatUsers;
        mContext=context;
    }

    @Override
    public ViewHolderUsers onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflate layout for each row
        return new ViewHolderUsers(mContext,LayoutInflater.from(parent.getContext()).inflate(R.layout.user_profile, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolderUsers holder, int position) {

        UsersChatModel fireChatUser=mFireChatUsers.get(position);

        // Set avatar
        int userAvatarId= ChatHelper.getDrawableAvatarId(fireChatUser.getAvatarId());
        Drawable  avatarDrawable= ContextCompat.getDrawable(mContext,userAvatarId);
        holder.getUserPhoto().setImageDrawable(avatarDrawable);

        // Set username
        holder.getUserFirstName().setText(fireChatUser.getFirstName());

        // Set presence status
        holder.getStatusConnection().setText(fireChatUser.getConnection());

        // Set presence text color
        if(fireChatUser.getConnection().equals(ReferenceClass.KEY_ONLINE)) {
            // Green color
            holder.getStatusConnection().setTextColor(Color.parseColor("#00FF00"));
        }else {
            // Red color
            holder.getStatusConnection().setTextColor(Color.parseColor("#FF0000"));
        }

    }

    @Override
    public int getItemCount() {
        return mFireChatUsers.size();
    }

    public void refill(UsersChatModel users) {

        // Add each user and notify recyclerView about change
        mFireChatUsers.add(users);
        notifyDataSetChanged();
    }

    public void setNameAndCreatedAt(String userName, String createdAt) {

        // Set current user name and time account created at
        mCurrentUserName=userName;
        mCurrentUserCreatedAt=createdAt;
    }

    public void changeUser(int index, UsersChatModel user) {

        // Handle change on each user and notify change
        mFireChatUsers.set(index,user);
        notifyDataSetChanged();
    }



    /* ViewHolder for RecyclerView */
    public class ViewHolderUsers extends RecyclerView.ViewHolder implements View.OnClickListener{

        private ImageView mUserPhoto; // User avatar
        private TextView mUserFirstName; // User first name
        private TextView mStatusConnection; // User presence
        private Context mContextViewHolder;

        public ViewHolderUsers(Context context, View itemView) {
            super(itemView);
            mUserPhoto=(ImageView)itemView.findViewById(R.id.userPhotoProfile);
            mUserFirstName=(TextView)itemView.findViewById(R.id.userFirstNameProfile);
            mStatusConnection=(TextView)itemView.findViewById(R.id.connectionStatus);
            mContextViewHolder=context;

            // Attach a click listener to the entire row view
            itemView.setOnClickListener(this);
        }

        public ImageView getUserPhoto() {
            return mUserPhoto;
        }

        public TextView getUserFirstName() {
            return mUserFirstName;
        }
        public TextView getStatusConnection() {
            return mStatusConnection;
        }


        @Override
        public void onClick(View view) {

            // Handle click on each row

            int position=getLayoutPosition(); // Get row position

            UsersChatModel user=mFireChatUsers.get(position); // Get use object

            // Provide current user username and time created at
            user.setCurrentUserName(mCurrentUserName);
            user.setCurrentUserCreatedAt(mCurrentUserCreatedAt);

            Intent intent=new Intent(mContextViewHolder, ChatActivity.class);
            intent.putExtra(ReferenceClass.KEY_PASS_USERS_INFO,user);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            mContextViewHolder.startActivity(intent);
        }
    }
}
