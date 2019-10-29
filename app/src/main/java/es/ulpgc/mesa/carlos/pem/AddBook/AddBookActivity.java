package es.ulpgc.mesa.carlos.pem.AddBook;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import es.ulpgc.mesa.carlos.pem.R;

public class AddBookActivity
        extends AppCompatActivity implements AddBookContract.View {

    public static String TAG = AddBookActivity.class.getSimpleName();

    private AddBookContract.Presenter presenter;
    private Button cancel,add;
    private ImageView addImage;
    Integer REQUEST_CAMERA=1,SELECT_FILE=0;
    private EditText isbn,title,author;
    private Uri filePath;
    private final int READ_EXTERNAL_STORAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);
        askPermission(Manifest.permission.READ_EXTERNAL_STORAGE,READ_EXTERNAL_STORAGE);
        isbn= findViewById(R.id.isbn);
        title= findViewById(R.id.title);
        author= findViewById(R.id.author);
        cancel= findViewById(R.id.buttonCancel);
        add=findViewById(R.id.buttonAddBook);
        addImage=findViewById(R.id.addImage);
        AddBookScreen.configure(this);


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.cancelAddBook();
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.addBook(isbn.getText().toString(),author.getText().toString(),title.getText().toString(), addImage);
            }
        });

        addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SelectImage();
            }
        });


    }

    /**
     * Method that creates a window where you select the way to get an image
     */
    private void SelectImage(){
        final CharSequence[] items= {"Camera", "Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(AddBookActivity.this);
        builder.setTitle("Add Image");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                if(items[i].equals("Camera")){
                    Intent intent= new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent,REQUEST_CAMERA);

                }else if(items[i].equals("Gallery")){
                    Intent intent= new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(intent.createChooser(intent,"Select File"),SELECT_FILE);
                }else if(items[i].equals("Cancel")){
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    /**
     * Method that sets the ItemView with the image selected by the user
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode,int resultCode,Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(resultCode== Activity.RESULT_OK){
            if(requestCode==REQUEST_CAMERA){
                Bundle bundle= data.getExtras();
                final Bitmap bitmap=(Bitmap) bundle.get("data");
                addImage.setImageBitmap(bitmap);
            }else if(requestCode== SELECT_FILE){
                Uri selectedImage= data.getData();
                filePath= selectedImage;
                addImage.setImageURI(selectedImage);
            }

        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        // load the data

    }

    @Override
    public void displayData(AddBookViewModel viewModel) {
        //Log.e(TAG, "displayData()");
        Toast.makeText(getApplicationContext(), viewModel.message, Toast.LENGTH_LONG).show();

        // deal with the data
    }

    @Override
    public void injectPresenter(AddBookContract.Presenter presenter) {
        this.presenter = presenter;
    }
    private void askPermission(String permission, int requestCode){
        if(ContextCompat.checkSelfPermission(this, permission)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{permission}, requestCode);
        }else{
            Toast.makeText(this, "Ya tienes permisos", Toast.LENGTH_SHORT).show();
        }
    }
}
