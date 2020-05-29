package com.saurabh.tinder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;
import com.saurabh.tinder.Matches.MatchesActivity;
import com.saurabh.tinder.Cards.arrayAdapter;
import com.saurabh.tinder.Cards.cards;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private cards card_data[];
    private com.saurabh.tinder.Cards.arrayAdapter arrayAdapter;

    private FirebaseAuth mAuth;

    private String currentUid;
    private String userSex, oppositeUserSex;

    private DatabaseReference usersDb;

    ListView listView;
    List<cards> rowItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usersDb = FirebaseDatabase.getInstance().getReference().child("Users");

        mAuth = FirebaseAuth.getInstance();
        currentUid = mAuth.getCurrentUser().getUid();

        checkUserSex();

        rowItems = new ArrayList<cards>();

        arrayAdapter = new arrayAdapter(this, R.layout.item, rowItems );


        SwipeFlingAdapterView flingContainer = (SwipeFlingAdapterView) findViewById(R.id.frame);

        flingContainer.setAdapter(arrayAdapter);
        flingContainer.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {
                // this is the simplest way to delete an object from the Adapter (/AdapterView)
                Log.d("LIST", "removed object!");
                rowItems.remove(0);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onLeftCardExit(Object dataObject) {
                cards obj = (cards) dataObject;
                String userId  = obj.getUserId();
                usersDb.child(userId).child("connections").child("nope").child(currentUid).setValue(true);
                Toast.makeText(MainActivity.this, "Left", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRightCardExit(Object dataObject) {
                cards obj = (cards) dataObject;
                String userId  = obj.getUserId();
                usersDb.child(userId).child("connections").child("yeps").child(currentUid).setValue(true);
                isConnectionMatch(userId);
                Toast.makeText(MainActivity.this, "Right", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdapterAboutToEmpty(int itemsInAdapter) {

            }

            @Override
            public void onScroll(float scrollProgressPercent) {

            }
        });


        flingContainer.setOnItemClickListener(new SwipeFlingAdapterView.OnItemClickListener() {
            @Override
            public void onItemClicked(int itemPosition, Object dataObject) {
                Toast.makeText(MainActivity.this, "Click", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void isConnectionMatch(String userId) {
        DatabaseReference currentUserDatabaseReference = usersDb.child(currentUid).child("connections").child("yeps").child(userId);
        currentUserDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {
                    Toast.makeText(MainActivity.this, "It's a match!!!", Toast.LENGTH_LONG).show();
                    String key = FirebaseDatabase.getInstance().getReference().child("Chat").push().getKey();
                    usersDb.child(dataSnapshot.getKey()).child("connections").child("matches").child(currentUid).child("Chat ID").setValue(key);
                    usersDb.child(currentUid).child("connections").child("matches").child(dataSnapshot.getKey()).child("Chat ID").setValue(key);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void checkUserSex()
    {
        final FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference userDB = usersDb.child(user.getUid());
        userDB.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists())
                        if (dataSnapshot.child("Sex").getValue()!=null) {
                            userSex = dataSnapshot.child("Sex").getValue().toString();
                            switch (userSex) {
                                case "Male":
                                    oppositeUserSex = "Female";
                                    break;
                                case "Female":
                                    oppositeUserSex = "Male";
                                    break;
                            }
                            getOppositeSexUsers();
                        }
                }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void getOppositeSexUsers()
    {
        usersDb.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if(dataSnapshot.exists())
                {
                    if (dataSnapshot.child("Profile Image URL").getValue()!=null && dataSnapshot.child("Name").getValue()!=null && !dataSnapshot.child("connections").child("nope").hasChild(currentUid) && dataSnapshot.child("Sex").getValue().toString().equals(oppositeUserSex) && !dataSnapshot.child("connections").child("yeps").hasChild(currentUid))
                    {
                        cards item = new cards(dataSnapshot.getKey(),dataSnapshot.child("Name").getValue().toString(),dataSnapshot.child("Profile Image URL").getValue().toString());
                        rowItems.add(item);
                        arrayAdapter.notifyDataSetChanged();
                    }
                }
            }
            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }
            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }
            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void logout(View view)
    {
        mAuth.signOut();
        Intent intent = new Intent(MainActivity.this,ChooseLoginRegistrationActivity.class);
        startActivity(intent);
        finish();
        return;
    }

    public void goToSettings(View view) {
        Intent intent = new Intent(MainActivity.this,SettingsActivity.class);
        startActivity(intent);
        return;
    }

    public void goToMatches(View view) {
        Intent intent = new Intent(MainActivity.this, MatchesActivity.class);
        startActivity(intent);
        return;
    }
}