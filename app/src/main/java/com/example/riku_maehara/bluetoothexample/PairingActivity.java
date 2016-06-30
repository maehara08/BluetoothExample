package com.example.riku_maehara.bluetoothexample;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Set;

public class PairingActivity extends AppCompatActivity {
    private static final String TAG = PairingActivity.class.getSimpleName();

    /**
     * Return Intent extra
     */
    public static String EXTRA_DEVICE_ADDRESS = "device_address";


    private BluetoothAdapter mBluetoothAdapter;
    private ArrayAdapter<String> mArrayAdapter;
    private ListView mPairedListView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pairing);
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        mArrayAdapter = new ArrayAdapter<String>(this, R.layout.device_name);
        mPairedListView = (ListView) findViewById(R.id.listView);

        Set<BluetoothDevice> devices = mBluetoothAdapter.getBondedDevices();

        if (devices.size() > 0) {
            for (BluetoothDevice bluetoothDevice : devices) {
                Log.d(TAG, bluetoothDevice.getName());
                mPairedListView.setAdapter(mArrayAdapter);
                mArrayAdapter.add(bluetoothDevice.getName() + "\n" + bluetoothDevice.getAddress());
                mPairedListView.setOnItemClickListener(mOnItemClickListener);
            }

        }
    }

    private AdapterView.OnItemClickListener mOnItemClickListener =
            new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    // Get the device MAC address, which is the last 17 chars in the View
                    String info = ((TextView) view).getText().toString();
                    String address = info.substring(info.length() - 17);
                    Log.d(TAG, "address  " + address);
                    Log.d(TAG, "info  " + info);

                    // Create the result Intent and include the MAC address
                    Intent intent = new Intent();
                    intent.putExtra(EXTRA_DEVICE_ADDRESS, address);

                    // Set result and finish this Activity
                    setResult(RESULT_OK,intent);
                    finish();


                }
            };


}
