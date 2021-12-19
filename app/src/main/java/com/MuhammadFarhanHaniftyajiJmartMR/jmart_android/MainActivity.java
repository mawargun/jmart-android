package com.MuhammadFarhanHaniftyajiJmartMR.jmart_android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

/**
 * Class MainActivity here
 * class to display a list of products registered on the back-end
 * @author Muhammad Farhan Haniftyaji
 * @version 1.0
 *
 */

public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private MenuItem search;
    private SearchView searchView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabLayout = findViewById(R.id.tablayout);
        viewPager = findViewById(R.id.viewp);

        tabLayout.setupWithViewPager(viewPager);
        VPAdapter vpAdapter = new VPAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        vpAdapter.addFragment(new ProductFragment(), "PRODUCT");
        vpAdapter.addFragment(new FilterFragment(), "FILTER");
        viewPager.setAdapter(vpAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        search = menu.findItem(R.id.search);

        searchView = (SearchView) search.getActionView();
        searchView.setQueryHint("Butuh apa hari ini?");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                ProductFragment.listViewAdapter.getFilter().filter(newText);

                return false;
            }
        });
        return true;
    }

    /**
     * @description
     * method onOptionsItemSelected in here
     * you can choose to view account information, create products, view invoices,
     * and search for products on certain pages
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.search) {
            Toast.makeText(this, "Search Selected", Toast.LENGTH_SHORT);
        }
        if (item.getItemId() == R.id.add) {
            Toast.makeText(this, "Create Selected", Toast.LENGTH_SHORT);
            Intent intent = new Intent(MainActivity.this, CreateProductActivity.class);
            startActivity(intent);
        }
        if (item.getItemId() == R.id.parent) {
            Toast.makeText(this, "About Me Selected", Toast.LENGTH_SHORT);
            Intent intent = new Intent(MainActivity.this, AboutMeActivity.class);
            startActivity(intent);
        }
        if (item.getItemId() == R.id.history_personal_button) {
            Toast.makeText(this, "History Account Selected", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this, PersonalInvoiceActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}