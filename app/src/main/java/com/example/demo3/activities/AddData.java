package com.example.demo3.activities;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.demo3.R;
import com.example.demo3.model.ModelJam1;
import com.example.demo3.model.ModelJam2;
import com.example.demo3.model.Modelhari;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddData extends AppCompatActivity {
    String apikey ="AIzaSyCBDX3N7GdHxMWMbyPLuVr2oCbKaoL2Xeo";
    final int PICK_IMAGE_REQUEST = 5;
    private Uri mImageUri;
    private StorageReference mStorageRef;
    String TAG="";
    Toolbar tbAdddata;
    private EditText addnama;
    private EditText addgambar;
    private EditText addlat;
    private EditText addlong;
    private Button addimg;
    private Button addupload;
    private Button addloc;
    private Button addsemua;
    private Spinner spHari;
    private Spinner spJam1;
    private Spinner spJam2;
    final int PLACE_PICKER_REQUEST = 10;
    private List<Place.Field> placeFields;
    boolean isValid = false;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DocumentReference noteRefi = db.document("update/additerasi");
    private DocumentReference noteRefz = db.document("update/syaratnama");
    List<String> ceknama;
    double latitude, longitude;
    int a=0 , b=1, c=2, d=3, e=4, f=5;
    String hari1, hari2, inijam1, inijam2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adddata);

        tbAdddata = findViewById(R.id.tbAdddata);
        tbAdddata.setTitle("ADD Data SIM Keliling");
        setSupportActionBar(tbAdddata);
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mStorageRef = FirebaseStorage.getInstance().getReference("uploads");

        addnama = findViewById(R.id.etAddnama);
        addgambar = findViewById(R.id.etAddimg);
        addlat = findViewById(R.id.etinputlat);
        addlong = findViewById(R.id.etinputlong);
        addimg = findViewById(R.id.btnAddimg);
        addupload = findViewById(R.id.btnupload);
        addloc = findViewById(R.id.btnAddloc);
        addsemua = findViewById(R.id.btnAddsemua);
        spHari = findViewById(R.id.spinneraddHari);
        spJam1 = findViewById(R.id.spinneraddJam1);
        spJam2 = findViewById(R.id.spinneraddJam2);
        noteRefz.get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        DocumentSnapshot document = task.getResult();
                        List<String> group = (List<String>) document.get("name");
                        ceknama = group;
                    }
                });

        List<Modelhari> hariList = new ArrayList<>();
        Modelhari hari = new Modelhari(null, null, null);
        hariList.add(hari);
        Modelhari hari0 = new Modelhari("Senin-Minggu", "Senin", "Minggu");
        hariList.add(hari0);
        Modelhari hari1 = new Modelhari("Senin-Sabtu", "Senin", "Sabtu");
        hariList.add(hari1);
        Modelhari hari2 = new Modelhari("Senin-Jumat", "Senin", "Jumat");
        hariList.add(hari2);
        Modelhari hari3 = new Modelhari("Sabtu-Minggu", "Sabtu", "Minggu");
        hariList.add(hari3);
        Modelhari hari4 = new Modelhari("Senin", "Senin", "nihil");
        hariList.add(hari4);
        Modelhari hari5 = new Modelhari("Selasa", "Selasa", "nihil");
        hariList.add(hari5);
        Modelhari hari6 = new Modelhari("Rabu", "Rabu", "nihil");
        hariList.add(hari6);
        Modelhari hari7 = new Modelhari("Kamis", "Kamis", "nihil");
        hariList.add(hari7);
        Modelhari hari8 = new Modelhari("Jumat", "Jumat", "nihil");
        hariList.add(hari8);
        Modelhari hari9 = new Modelhari("Sabtu", "Sabtu", "nihil");
        hariList.add(hari9);
        Modelhari hari10 = new Modelhari("Minggu", "Minggu", "nihil");
        hariList.add(hari10);

        List<ModelJam1> jamList1 = new ArrayList<>();
        ModelJam1 jam0 = new ModelJam1(null, null);
        jamList1.add(jam0);
        ModelJam1 jam1 = new ModelJam1("07.00", "07.00");
        jamList1.add(jam1);
        ModelJam1 jam2 = new ModelJam1("08.00", "08.00");
        jamList1.add(jam2);
        ModelJam1 jam3 = new ModelJam1("09.00", "09.00");
        jamList1.add(jam3);
        ModelJam1 jam4 = new ModelJam1("10.00", "10.00");
        jamList1.add(jam4);
        ModelJam1 jam5 = new ModelJam1("11.00", "11.00");
        jamList1.add(jam5);
        ModelJam1 jam6 = new ModelJam1("12.00", "12.00");
        jamList1.add(jam6);
        ModelJam1 jam7 = new ModelJam1("13.00", "13.00");
        jamList1.add(jam7);
        ModelJam1 jam8 = new ModelJam1("14.00", "14.00");
        jamList1.add(jam8);
        ModelJam1 jam9 = new ModelJam1("15.00", "15.00");
        jamList1.add(jam9);
        ModelJam1 jam10 = new ModelJam1("16.00", "16.00");
        jamList1.add(jam10);
        ModelJam1 jam11 = new ModelJam1("17.00", "17.00");
        jamList1.add(jam11);

        List<ModelJam2> jamList2 = new ArrayList<>();
        ModelJam2 jjam0 = new ModelJam2(null, null);
        jamList2.add(jjam0);
        ModelJam2 jjam1 = new ModelJam2("07.00", "07.00");
        jamList2.add(jjam1);
        ModelJam2 jjam2 = new ModelJam2("08.00", "08.00");
        jamList2.add(jjam2);
        ModelJam2 jjam3 = new ModelJam2("09.00", "09.00");
        jamList2.add(jjam3);
        ModelJam2 jjam4 = new ModelJam2("10.00", "10.00");
        jamList2.add(jjam4);
        ModelJam2 jjam5 = new ModelJam2("11.00", "11.00");
        jamList2.add(jjam5);
        ModelJam2 jjam6 = new ModelJam2("12.00", "12.00");
        jamList2.add(jjam6);
        ModelJam2 jjam7 = new ModelJam2("13.00", "13.00");
        jamList2.add(jjam7);
        ModelJam2 jjam8 = new ModelJam2("14.00", "14.00");
        jamList2.add(jjam8);
        ModelJam2 jjam9 = new ModelJam2("15.00", "15.00");
        jamList2.add(jjam9);
        ModelJam2 jjam10 = new ModelJam2("16.00", "16.00");
        jamList2.add(jjam10);
        ModelJam2 jjam11 = new ModelJam2("17.00", "17.00");
        jamList2.add(jjam11);

        ArrayAdapter<Modelhari> adapter = new ArrayAdapter<Modelhari>(this,
                android.R.layout.simple_spinner_item, hariList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter<ModelJam1> adapter1 = new ArrayAdapter<ModelJam1>(this,
                android.R.layout.simple_spinner_item, jamList1);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter<ModelJam2> adapter2 = new ArrayAdapter<ModelJam2>(this,
                android.R.layout.simple_spinner_item, jamList2);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spHari.setAdapter(adapter);
        spJam1.setAdapter(adapter1);
        spJam2.setAdapter(adapter2);

        spHari.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Modelhari hari = (Modelhari) parent.getSelectedItem();
                displayhari(hari);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spJam1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ModelJam1 jjam1 = (ModelJam1) parent.getSelectedItem();
                displayjam1(jjam1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spJam2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ModelJam2 jjam2 = (ModelJam2) parent.getSelectedItem();
                displayjam2(jjam2);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        addimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFileChooser();
            }
        });

        addupload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadFile();
            }
        });

        addsemua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startAdddata();
            }
        });

        //array
        placeFields = Arrays.asList(Place.Field.ID,Place.Field.NAME,Place.Field.LAT_LNG);
        // initialized place
        Places.initialize(getApplicationContext(), apikey);
        //place Client
        PlacesClient placesClient = Places.createClient(this);
    }

    public void startAutocompleteActivity(View view) {
        Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, placeFields).build(AddData.this);
        startActivityForResult(intent, PLACE_PICKER_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = Autocomplete.getPlaceFromIntent(data);
                addlat.setText(String.valueOf(place.getLatLng().latitude));
                addlong.setText(String.valueOf(place.getLatLng().longitude));
            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                //Handle the error.
                Status status = Autocomplete.getStatusFromIntent(data);
                Log.i(TAG, status.getStatusMessage());
            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            mImageUri = data.getData();
        }
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        Intent myIntent = new Intent(getApplicationContext(), MenuAdmin.class);
        startActivityForResult(myIntent, 0);
        return true;
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    private void startAdddata() {
        String hasilnama = addnama.getText().toString();
        String hasilimg = addgambar.getText().toString();
        String hasillat = addlat.getText().toString();
        String hasillong = addlong.getText().toString();
        String buatdb = toAlphaLowerCase(hasilnama);
        latitude = ParseDouble(hasillat);
        longitude = ParseDouble(hasillong);
        if (spHari.getSelectedItemPosition() == 0 || spJam1.getSelectedItemPosition() == 0 || spJam2.getSelectedItemPosition() == 0)
        {
            /* Display a message toast to user to enter the details */
            Toast.makeText(AddData.this, "Anda belum memasukkan dengan lengkap", Toast.LENGTH_LONG).show();
        } else if (spJam1.getSelectedItemPosition() >= spJam2.getSelectedItemPosition()) {
            Toast.makeText(AddData.this, "Maaf jam yang anda masukkan tidak benar", Toast.LENGTH_LONG).show();
        }else {
            if (hasilnama.isEmpty() || hasilimg.isEmpty() || latitude == 0 || longitude == 0)
            {
                /* Display a message toast to user to enter the details */
                Toast.makeText(AddData.this, "Anda belum memasukkan dengan lengkap", Toast.LENGTH_LONG).show();
            } else {
                isValid = validate(buatdb);
                if (isValid == true) {
                    Toast.makeText(AddData.this, "Maaf, nama sudah ada!", Toast.LENGTH_LONG).show();
                }
                /* If valid */
                else {
                    Map<String, Object> data = new HashMap<>();
                    data.put("nama", hasilnama);
                    data.put("haribuka", hari1);    //Ini baru
                    data.put("haritutup", hari2);   //Ini baru
                    data.put("jambuka", inijam1);   //Ini baru
                    data.put("jamtutup", inijam2);  //Ini baru
                    data.put("imgurl", hasilimg);
                    data.put("latitude", latitude);
                    data.put("longitude", longitude);
                    data.put("latPeng", 0);
                    data.put("longPeng", 0);
                    data.put("jarak", 0);
                    Map<String, Object> update = new HashMap<>();
                    update.put("nama", buatdb);
                    update.put("latitude", latitude);
                    update.put("longitude", longitude);
                    db.collection("simkel0").document(buatdb + a).set(data);
                    db.collection("simkel1").document(buatdb + b).set(data);
                    db.collection("simkel2").document(buatdb + c).set(data);
                    db.collection("simkel3").document(buatdb + d).set(data);
                    db.collection("simkel4").document(buatdb + e).set(data);
                    db.collection("simkel5").document(buatdb + f).set(data);
                    noteRefz.update("name", FieldValue.arrayUnion(buatdb));
                    noteRefi.get()
                            .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                    if (documentSnapshot.exists()) {
                                        String iterasi = documentSnapshot.getString("iterasi");
                                        db.collection("update").document("dokumen"+ iterasi).set(update);
                                    } else {
                                        Toast.makeText(AddData.this, "Document does not exist", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                    Toast.makeText(AddData.this, "Data berhasil dimasukkan", Toast.LENGTH_LONG).show();
                    addnama.setText("");
                    addgambar.setText("");
                    addlat.setText("");
                    addlong.setText("");
                    spHari.setSelection(0);
                    spJam1.setSelection(0);
                    spJam2.setSelection(0);
                    noteRefi.get()
                            .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                    if (documentSnapshot.exists()) {
                                        String iterasi = documentSnapshot.getString("iterasi");
                                        int z = Integer.parseInt(iterasi);
                                        if (z == 1){
                                            Map<String, Object> data1 = new HashMap<>();
                                            data1.put("iterasi", "2");
                                            db.collection("update").document("additerasi").set(data1);
                                        } else if (z == 2){
                                            Map<String, Object> data1 = new HashMap<>();
                                            data1.put("iterasi", "3");
                                            db.collection("update").document("additerasi").set(data1);
                                        } else if (z == 3){
                                            Map<String, Object> data1 = new HashMap<>();
                                            data1.put("iterasi", "4");
                                            db.collection("update").document("additerasi").set(data1);
                                        } else if (z == 4){
                                            Map<String, Object> data1 = new HashMap<>();
                                            data1.put("iterasi", "5");
                                            db.collection("update").document("additerasi").set(data1);
                                        } else if (z == 5){
                                            Map<String, Object> data1 = new HashMap<>();
                                            data1.put("iterasi", "6");
                                            db.collection("update").document("additerasi").set(data1);
                                        } else if (z == 6){
                                            Map<String, Object> data1 = new HashMap<>();
                                            data1.put("iterasi", "7");
                                            db.collection("update").document("additerasi").set(data1);
                                        } else if (z == 7){
                                            Map<String, Object> data1 = new HashMap<>();
                                            data1.put("iterasi", "8");
                                            db.collection("update").document("additerasi").set(data1);
                                        } else if (z == 8){
                                            Map<String, Object> data1 = new HashMap<>();
                                            data1.put("iterasi", "9");
                                            db.collection("update").document("additerasi").set(data1);
                                        } else if (z == 9){
                                            Map<String, Object> data1 = new HashMap<>();
                                            data1.put("iterasi", "10");
                                            db.collection("update").document("additerasi").set(data1);
                                        }
                                    }
                                }
                            });
                }
            }
        }
    }

    class Credentials
    {
        List<String> creditnama = ceknama;
    }

    private boolean validate(String userinput)
    {
        /* Get the object of Credentials class */
        AddData.Credentials credentials = new AddData.Credentials();
        /* Check the credentials */
        for(String s : credentials.creditnama)
            if(userinput.contains(s))
            {
                return true;
            }

        return false;
    }

    static String toAlphaLowerCase(String s) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (Character.isLetter(c)) {
                sb.append(Character.toLowerCase(c));
            }
        }
        return sb.toString();
    }

    double ParseDouble(String strNumber) {
        if (strNumber != null && strNumber.length() > 0) {
            try {
                return Double.parseDouble(strNumber);
            } catch (Exception e) {
                return -1;   // or some value to mark this field is wrong. or make a function validates field first ...
            }
        } else return 0;
    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void uploadFile() {
        if (mImageUri != null){
            StorageReference fileReference = mStorageRef.child(System.currentTimeMillis()
                    + "." + getFileExtension(mImageUri));

            fileReference.putFile(mImageUri).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()){
                        throw task.getException();
                    }
                    return fileReference.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()){
                        Uri downUri = task.getResult();
                        addgambar.setText(downUri.toString());
                    }
                }
            });
        } else {
            Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show();
        }
    }
    private void displayhari(Modelhari user) {
        hari1 = user.getHari1();
        hari2 = user.getHari2();
    }

    private void displayjam1(ModelJam1 user) {
        inijam1 = user.getJam1();
    }

    private void displayjam2(ModelJam2 user) {
        inijam2 = user.getJam2();
    }
}
