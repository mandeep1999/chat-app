package co.mandeep_singh.chatapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.widget.ImageButton;

import com.google.android.material.tabs.TabLayout;

import java.util.Objects;

import co.mandeep_singh.chatapp.Adapter.VPadapter;
import co.mandeep_singh.chatapp.Auth.Auth;


public class HomeActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ImageButton logOut;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_home);

        logOut = (ImageButton) findViewById(R.id.logout);
        logOut.setOnClickListener(view -> setLogOut());

        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);

        tabLayout.setupWithViewPager(viewPager);

        VPadapter vPadapter = new VPadapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        vPadapter.addFragment(new HomeFragment(), "JOBS");
        vPadapter.addFragment(new ChatsFragment(), "CHATS");
        viewPager.setAdapter(vPadapter);
    }

    public void setLogOut(){
        Auth auth = new Auth();
        auth.signOut(this);
    }


}