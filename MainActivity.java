package com.wony.kotech.androidyes;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private boolean close_flag = false;
    private Handler close_handler;

    public Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        replaceFragment(0);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        close_handler = new Handler() {
            @Override
            public void handleMessage(Message msg)	{
                if(msg.what == 0) {
                    close_flag = false;
                }
            }
        };
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            replaceFragment(0);
        } else if (id == R.id.nav_profile) {
            replaceFragment(1);
        } else if (id == R.id.nav_transfer) {
            replaceFragment(2);
        } else if (id == R.id.nav_information) {
            replaceFragment(3);
        } else if (id == R.id.nav_customer) {
            replaceFragment(4);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void replaceFragment(int pos) {
        Fragment fragment = null;
        switch (pos) {
            case 0:
                fragment = new HomeFragment();
                break;
            case 1:
                fragment = new ProfileFragment();
                break;
            case 2:
                fragment = new TransferFragment();
                break;
            case 3:
                fragment = new InformationFragment();
                break;
            default:
                fragment = new NoticeFragment();
                break;
        }
        if (null != fragment) {
            //FragmentTransaction의 인스턴스는 FragmentManager 클래스로부터 가져올 수 있음.
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            //FragmentTransaction의 replace() 메소드를 통해 동적으로 Fragment 교체.
            transaction.replace(R.id.main_content, fragment);
            //뒤로가기버튼 누르면 이전 Fragment를 복원 해줌.
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if(event.getKeyCode()==KeyEvent.KEYCODE_BACK) {
            if(!close_flag) {
//                Toast.makeText(this, "Press again shut down.", Toast.LENGTH_SHORT).show();
                close_flag = true;
                close_handler.sendEmptyMessageDelayed(0, 2000);
                return false;
            }else {
                moveTaskToBack(true);
                finish();
//                System.exit(0);
                android.os.Process.killProcess(android.os.Process.myPid());
            }
        }
        return super.onKeyUp(keyCode, event);
    }
}
