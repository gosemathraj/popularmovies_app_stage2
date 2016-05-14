package com.gosemathraj.popularmoviesapp.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.gosemathraj.popularmoviesapp.R;
import com.gosemathraj.popularmoviesapp.fragments.Fragment_All_Movies;
import com.gosemathraj.popularmoviesapp.fragments.Fragment_Movies_Home;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fragmentManager = getSupportFragmentManager();
        Fragment f1 = fragmentManager.findFragmentById(R.id.relative);
        if(f1 == null) {
            fragmentManager.beginTransaction().add(R.id.relative, new Fragment_Movies_Home(), "FIRST_FRAGMENT").addToBackStack("back").commit();
        }

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        setNavDrawer();
        navigationView.getMenu().getItem(0).setChecked(true);
    }

    private void setNavDrawer() {

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {

                navigationView.setCheckedItem(item.getItemId());

                drawerLayout.closeDrawers();

                switch(item.getItemId()){

                    case R.id.home:
                        Toast.makeText(getApplicationContext(),"Home is clicked",Toast.LENGTH_LONG).show();
                        Fragment homeFragment1 = fragmentManager.findFragmentById(R.id.relative);
                        if(homeFragment1 != null){

                        }else{

                            fragmentManager.beginTransaction().replace(R.id.relative,new Fragment_Movies_Home()).addToBackStack("back").commit();
                        }
                        break;

                    case R.id.favourites:
                        Toast.makeText(getApplicationContext(),"favourites is clicked",Toast.LENGTH_LONG).show();

                        Bundle bundle_three = new Bundle();
                        bundle_three.putString("variable", "favourites");
                        Fragment_All_Movies fragment_all_movies_three = new Fragment_All_Movies();
                        fragment_all_movies_three.setArguments(bundle_three);
                        fragmentManager.beginTransaction().replace(R.id.relative, fragment_all_movies_three).addToBackStack("back").commit();
                        break;


                    case R.id.watched:
                        Toast.makeText(getApplicationContext(),"watched is clicked",Toast.LENGTH_LONG).show();
                        break;

                    case R.id.rate:
                        Toast.makeText(getApplicationContext(),"rate is clicked",Toast.LENGTH_LONG).show();

                        Bundle bundle_one = new Bundle();
                        bundle_one.putString("variable", "highestrated");
                        Fragment_All_Movies fragment_all_movies_one = new Fragment_All_Movies();
                        fragment_all_movies_one.setArguments(bundle_one);
                        fragmentManager.beginTransaction().replace(R.id.relative, fragment_all_movies_one).addToBackStack("back").commit();
                        break;

                    case R.id.popularity:
                        Toast.makeText(getApplicationContext(),"popularity is clicked",Toast.LENGTH_LONG).show();

                        Bundle bundle_two = new Bundle();
                        bundle_two.putString("variable", "popular");
                        Fragment_All_Movies fragment_all_movies_two = new Fragment_All_Movies();
                        fragment_all_movies_two.setArguments(bundle_two);
                        fragmentManager.beginTransaction().replace(R.id.relative,fragment_all_movies_two).addToBackStack("back").commit();

                        break;
                }

                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
