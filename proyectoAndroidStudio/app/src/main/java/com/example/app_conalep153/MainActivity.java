package com.example.app_conalep153;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.widget.TextView;
import android.widget.ImageView;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.app_conalep153.databinding.ActivityMainBinding;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    private TextView navHeaderName, navHeaderRole, navHeaderSubtitle;
    private ImageView navHeaderImage;
    private String fotoUrl;
    private ImageView userImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);

        binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null)
                        .setAnchorView(R.id.fab).show();
            }
        });

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;

        View headerView = navigationView.getHeaderView(0);
        navHeaderName = headerView.findViewById(R.id.textView);
        navHeaderRole = headerView.findViewById(R.id.textViewRole);
        navHeaderImage = headerView.findViewById(R.id.imageView);

        Intent intent = getIntent();
        String nombre = intent.getStringExtra("nombre");
        String rol = intent.getStringExtra("rol");
        fotoUrl = intent.getStringExtra("foto");

        navHeaderName.setText(nombre);
        navHeaderRole.setText(rol);

        if (fotoUrl != null && !fotoUrl.isEmpty()) {
            Picasso.get()
                    .load(fotoUrl)
                    .transform(new com.squareup.picasso.Transformation() {
                        @Override
                        public Bitmap transform(Bitmap source) {
                            int size = Math.min(source.getWidth(), source.getHeight());
                            Bitmap result = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
                            Canvas canvas = new Canvas(result);

                            Paint paint = new Paint();
                            paint.setAntiAlias(true);
                            paint.setFilterBitmap(true);
                            paint.setDither(true);

                            canvas.drawCircle(size / 2f, size / 2f, size / 2f, paint);

                            Rect srcRect = new Rect(0, 0, source.getWidth(), source.getHeight());
                            Rect dstRect = new Rect(0, 0, size, size);
                            paint.setXfermode(new android.graphics.PorterDuffXfermode(android.graphics.PorterDuff.Mode.SRC_IN));
                            canvas.drawBitmap(source, srcRect, dstRect, paint);

                            source.recycle();

                            return result;
                        }

                        @Override
                        public String key() {
                            return "circle";
                        }
                    })
                    .into(navHeaderImage);

        }

        userImage = binding.appBarMain.toolbar.findViewById(R.id.fotoUsuario);

        Picasso.get()
                .load(fotoUrl)
                .transform(new com.squareup.picasso.Transformation() {
                    @Override
                    public Bitmap transform(Bitmap source) {
                        int size = Math.min(source.getWidth(), source.getHeight());
                        Bitmap result = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
                        Canvas canvas = new Canvas(result);

                        Paint paint = new Paint();
                        paint.setAntiAlias(true);
                        paint.setFilterBitmap(true);
                        paint.setDither(true);

                        canvas.drawCircle(size / 2f, size / 2f, size / 2f, paint);

                        Rect srcRect = new Rect(0, 0, source.getWidth(), source.getHeight());
                        Rect dstRect = new Rect(0, 0, size, size);
                        paint.setXfermode(new android.graphics.PorterDuffXfermode(android.graphics.PorterDuff.Mode.SRC_IN));
                        canvas.drawBitmap(source, srcRect, dstRect, paint);

                        source.recycle();

                        return result;
                    }

                    @Override
                    public String key() {
                        return "circle";
                    }
                })
                .into(userImage);

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_actividades, R.id.nav_calendario,R.id.nav_docentes,R.id.nav_grupos, R.id.nav_horario)
                .setOpenableLayout(drawer)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

 /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }*/

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);

        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}
