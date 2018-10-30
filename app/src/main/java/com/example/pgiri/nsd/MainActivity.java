package com.example.pgiri.nsd;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;


import io.resourcepool.ssdp.client.SsdpClient;
import io.resourcepool.ssdp.model.DiscoveryListener;
import io.resourcepool.ssdp.model.DiscoveryRequest;
import io.resourcepool.ssdp.model.SsdpService;
import io.resourcepool.ssdp.model.SsdpServiceAnnouncement;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        searchNetwork();
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

    private void searchNetwork() {

        SsdpClient client = SsdpClient.create();
        DiscoveryRequest networkStorageDevice = DiscoveryRequest.builder()
                .serviceType("urn:blocklink:pigrid:web:0")
                .build();
        client.discoverServices(networkStorageDevice, new DiscoveryListener() {
            @Override
            public void onServiceDiscovered(SsdpService service) {
                System.out.println("Found service: " + service);
                System.out.println("Found ip: " + service.getLocation());
                Intent intent = new Intent(getBaseContext(), WebViewActivity.class);
                intent.putExtra("piIP", service.getLocation());
                startActivity(intent);
            }

            @Override
            public void onFailed(Exception ex) {
                System.out.println("Failed service: " + ex);
            }

            @Override
            public void onServiceAnnouncement(SsdpServiceAnnouncement announcement) {
                System.out.println("Service announced something: " + announcement);
            }
        });
    }
}
