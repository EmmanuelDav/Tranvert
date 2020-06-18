package com.rad5techhub.tranvertsapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.assist.AssistStructure;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {

    ImageView image;
    Uri uri;
    EditText Title, price, describtion;
    private DatabaseReference mdatabasetef;
    private StorageReference mStorageref;
    public static final int Gallery = 101;
    TravertsDeal deal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mdatabasetef = FirebaseDatabase.getInstance().getReference().child("Travel deal");
        mStorageref = FirebaseStorage.getInstance().getReference().child("me Upload");

        Title = findViewById(R.id.id);
        price = findViewById(R.id.price);
        describtion = findViewById(R.id.description);
        image = findViewById(R.id.post_image);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, Gallery);
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Gallery && resultCode == RESULT_OK && data != null && data.getData() != null) {
            uri = data.getData();
            Picasso.with(this)
                    .load(uri)
                    .into(image);

        }
    }


    private void cleansdeal() {
        Title.setText("");
        price.setText("");
        describtion.setText("");
        image.setImageURI(Uri.parse(""));
        Title.requestFocus();
    }

    private void savedeal() {
        final String title = Title.getText().toString();
        final String Price = price.getText().toString();
        final String description = describtion.getText().toString();


        if (!TextUtils.isEmpty(title) && !TextUtils.isEmpty(Price) && uri != null && !TextUtils.isEmpty(description)) {

            StorageReference fileref = mStorageref.child(uri.getLastPathSegment());
            fileref.putFile(uri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            Toast.makeText(MainActivity.this, "deal saved ", Toast.LENGTH_LONG).show();

                            Task<Uri> loadUri = taskSnapshot.getStorage().getDownloadUrl();
                            while (!loadUri.isSuccessful()) ;
                            Uri downloadUri = loadUri.getResult();
                            final String sdownload_url = String.valueOf(downloadUri);

                            TravertsDeal deal = new TravertsDeal(title, Price, description, sdownload_url);
                            mdatabasetef.push().setValue(deal);


                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {

                }
            });

        } else {
            Toast.makeText(this, "No item selected", Toast.LENGTH_SHORT).show();
        }
    }

    private String getfileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mine = MimeTypeMap.getSingleton();
        return mine.getExtensionFromMimeType(cR.getType(uri));
    }

    public void submito(View view) {
        savedeal();
        cleansdeal();
    }
}
