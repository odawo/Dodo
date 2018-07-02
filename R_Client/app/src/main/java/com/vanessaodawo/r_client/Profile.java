package com.vanessaodawo.r_client;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.vanessaodawo.r_client.POJO.Clients;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;

public class Profile extends AppCompatActivity {

    TextView email, tel, address, other;
    CircleImageView prof_pric;
    ImageView savedata;

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    StorageReference storePicReference;
    DatabaseReference profileReference;

    Bitmap bitmap;

    String userChoosenTask;
    static final int SELECT_FILE = 1; //gallery choice
    static final int REQUEST_CAMERA = 1;
    private static final String IMAGE_DIRECTORY = "/dodoprofilepics"; //name of location where the pics will be stored
    String getImgUrl, TAG;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        email = findViewById(R.id.pemail);
        address = findViewById(R.id.paddress);
        tel = findViewById(R.id.pphone);
        other = findViewById(R.id.pother);
        savedata = findViewById(R.id.save_data);
        prof_pric = findViewById(R.id.profile_image);
//
//        firebaseAuth = FirebaseAuth.getInstance();
//        firebaseUser = firebaseAuth.getCurrentUser();
//        storePicReference = FirebaseStorage.getInstance().getReference().child("CLIENTPROFILEPIC");

//        if (firebaseUser != null) {
//            String pr_email = firebaseUser.getEmail();
//            email.setText(pr_email);
//            String pr_phone = firebaseUser.getPhoneNumber();
//            tel.setText(pr_phone);
////            Uri picUrl = firebaseUser.getPhotoUrl();
////
////            String uid = firebaseUser.getUid();
//        }

        savedata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveEdits();
            }
        });

        prof_pric.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(Profile.this, "clicked", Toast.LENGTH_SHORT).show();
                selectPic();
            }
        });
//
        prof_pric.setBorderColor(getResources().getColor(R.color.profileradius));
//        prof_pric.setBorderWidth(10);
// Add Shadow with default param
//        prof_pric.addShadow();
// or with custom param
//        prof_pric.setShadowRadius(10);
//        prof_pric.setShadowColor(Color.RED);

    }

    private void selectPic() {
        final CharSequence[] choice = {"Open Camera", "Open Gallery", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Set From :");
        builder.setItems(choice, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (choice[item].equals("Take Photo")){
                    userChoosenTask = "Take Photo";
                    cameraIntent();
                } else if (choice[item].equals("Choose from Gallery")) {
                    userChoosenTask="Choose from Gallery";
                    galleryIntent();
                } else if (choice[item].equals("Cancel")) { //cancels the process
                    finish();
                }
            }
        });
        builder.show();
    }

    private void galleryIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"),SELECT_FILE);
    }

    private void cameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_FILE && resultCode == this.RESULT_OK) {
            if (data != null) {
//                for transferring data in the form of bundles
                Bundle extras = data.getExtras();
//                bitmap is the form which data is transferred
                bitmap = (Bitmap) extras.get("data");
                saveImage(bitmap);
                Toast.makeText(Profile.this, "Profile Picture Set!", Toast.LENGTH_SHORT).show();
//                    picture taken is picked and saved in the product image view
                prof_pric.setImageBitmap(bitmap);

//                    method for saving the image taken or picked and save to firebase db
//                encodeImageandSavetoFirebase(bitmap);

            }

        } else if (requestCode == REQUEST_CAMERA) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data"); //change to thumbnail baadaye
            prof_pric.setImageBitmap(bitmap);
            saveImage(bitmap);
            Toast.makeText(Profile.this, "Profile Picture Set!", Toast.LENGTH_SHORT).show();
//            encodeImageandSavetoFirebase(bitmap);
        }
    }

    private String saveImage(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File dodorDirectory = new File(
                Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY); //save images to specified location called mahitajipics
        // have the object build the directory structure, if needed.
        if (!dodorDirectory.exists()) {
            dodorDirectory.mkdirs();
        }

        try {
            File f = new File(dodorDirectory, Calendar.getInstance()
                    .getTimeInMillis() + ".jpg");
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(this,
                    new String[]{f.getPath()},
                    new String[]{"image/jpeg"}, null);
            fo.close();
            Log.d("TAG", "File Saved::--->" + f.getAbsolutePath());

            return f.getAbsolutePath();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";
    }

//    private void encodeImageandSavetoFirebase(Bitmap bitmap) {
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
//        String imgEncoded = Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);
//        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().
//                child(FirebaseAuth.getInstance().getCurrentUser().getUid()).
//                child("imgUrl"); //.child(sth.getPushId())
//        Log.d(TAG, "encodeImageandSavetoFirebase: image saved to firebase db");
//        getImgUrl = imgEncoded;
//        ref.setValue(imgEncoded);
//
////        byte[] data = baos.toByteArray();
////        UploadTask uploadTask = storePicReference.putBytes(data);
////        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
////            @Override
////            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
////                Uri photoUrl = taskSnapshot.getDownloadUrl();
//////                Toast.makeText(Profile.this, "uploading data", Toast.LENGTH_SHORT).show();
////            }
////        }).addOnFailureListener(new OnFailureListener() {
////            @Override
////            public void onFailure(@NonNull Exception e) {
////                Toast.makeText(Profile.this, "unable to upload image to database. please retry.", Toast.LENGTH_SHORT).show();
////            }
////        });
//
//        UserProfileChangeRequest profileChange = new UserProfileChangeRequest.Builder().setPhotoUri(Uri.parse(imgEncoded)).build();
//        firebaseUser.updateProfile(profileChange).addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//                if (task.isSuccessful()) {
//                    Log.d(TAG, "User profile updated.");
//                    Snackbar.make(findViewById(R.id.lLayout2), "Profile Picture Set", Snackbar.LENGTH_SHORT).show();
//                }
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Snackbar.make(findViewById(R.id.lLayout2), e.getMessage(), Snackbar.LENGTH_SHORT).show();
//            }
//        });
//    }

    private void saveEdits() {
//
        Toast.makeText(this, "saves data to db. ", Toast.LENGTH_SHORT).show();
////
////        firebaseUser = firebaseAuth.getCurrentUser();
////
////        String p_email = email.getText().toString().trim();
////        String p_address = address.getText().toString().trim();
////        String p_phone = tel.getText().toString().trim();
////        String p_other = other.getText().toString().trim();
////
////        Clients cl = new Clients();
////        cl.setC_email(p_email);
////        cl.setC_address(p_address);
////        cl.setC_tel(p_phone);
////        cl.setC_other(p_other);
////
////        if (firebaseUser != null) {
////
////            profileReference.child("PROFILE").setValue(cl, new DatabaseReference.CompletionListener() {
////                @Override
////                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
////                    if (databaseError == null) {
////                        Toast.makeText(Profile.this, "PROFILE UPDATED",Toast.LENGTH_SHORT).show();
////                    } else {
////                        Toast.makeText(Profile.this, "ERROR IN UPDATE! RETRY",Toast.LENGTH_SHORT).show();
////                    }
////                }
////            });
////
////        } else {
////
////            Snackbar.make(findViewById(R.id.lLayout2), "YOU NEED TO BE LOGGED IN TO EDIT", Snackbar.LENGTH_LONG)
////                    .setAction("LOGIN", new View.OnClickListener() {
////                        @Override
////                        public void onClick(View v) {
////                            startActivity(new Intent(Profile.this, MainActivity.class));
////                        }
////                    });
////        }
////
    }
}
