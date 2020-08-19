public class LoginActivity extends AppCompatActivity {
    private static final int RC_SIGN_IN = 121;
    Button loginbutton;
    FirebaseUser currentuser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginbutton = findViewById(R.id.login);
        currentuser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentuser != null) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }
        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checklogin();
            }
        });
    }

    private void checklogin() {
        if (currentuser == null) {

            final List<AuthUI.IdpConfig> providers = Arrays.asList(
//                    new AuthUI.IdpConfig.EmailBuilder().build(),
//                    new AuthUI.IdpConfig.PhoneBuilder().build(),
                    new AuthUI.IdpConfig.GoogleBuilder().build()
//                    new AuthUI.IdpConfig.FacebookBuilder().build()
            );
            startActivityForResult(
                    AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setAvailableProviders(providers)
                            .setLogo(R.mipmap.ic_launcher)
                            .build(),
                    RC_SIGN_IN);
        } else {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            if (resultCode == RESULT_OK) {
                IdpResponse response = IdpResponse.fromResultIntent(data);
                startActivity(new Intent(LoginActivity.this, Userprofile_activity.class));
                finish();

            } else {
                IdpResponse response = IdpResponse.fromResultIntent(data);
                Toast.makeText(this, String.valueOf(response.getError()), Toast.LENGTH_SHORT).show();
            }
        }
    }
}
