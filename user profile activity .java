public class Userprofile_activity extends AppCompatActivity {
    EditText usernameedittext, phonenumber, college, bio;
    ImageView userprofile;
    FirebaseUser currentuser;
    Button signupbutton;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userprofile_activity);
        usernameedittext = findViewById(R.id.display_name);
        currentuser = FirebaseAuth.getInstance().getCurrentUser();
        phonenumber = findViewById(R.id.phone_num);
        college = findViewById(R.id.college_name);
        bio = findViewById(R.id.bio);
        userprofile = findViewById(R.id.profile_image);
        signupbutton = findViewById(R.id.signupbtn);
        if (currentuser != null)
            loaduserdata();

        signupbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getdata();
            }
        });


    }

    private void getdata() {
        String username = usernameedittext.getText().toString();
        String phonenum = phonenumber.getText().toString();
        String colloegename = college.getText().toString();
        String biodata = bio.getText().toString();
        String imageurl = currentuser.getPhotoUrl().toString();

        userdata datasentclass= new userdata();
        datasentclass.setBio(biodata);
        datasentclass.setCollogename(colloegename);
        datasentclass.setPhonenumber(phonenum);
        datasentclass.setUsername(username);
        datasentclass.setUser_id(currentuser.getUid());
        datasentclass.setProfile_photo(imageurl);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users").document().set(datasentclass).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(Userprofile_activity.this, "data saved", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Userprofile_activity.this, MainActivity.class));
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Userprofile_activity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void loaduserdata() {

        Uri imageurl = currentuser.getPhotoUrl();
        String username = currentuser.getDisplayName();
        String id = currentuser.getUid();

        Glide.with(Userprofile_activity.this).load(imageurl).into(userprofile);
        usernameedittext.setText(username);

    }
}
