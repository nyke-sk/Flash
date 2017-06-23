package com.nikhil.flash;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Bundle;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MainActivity extends Activity{

    private ToggleButton FlashController; //Controls the toggle button on the screen
    private Camera camera;      //Object of the camera API


    //Safely relaease the camera resources
//    @Override
//    protected void onStop() {
//        super.onStop();                 //
//
//        if (camera != null) {
//            camera.release();
//        }
//    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FlashController = (ToggleButton)findViewById(R.id.toggleButton);

        Context context = this;
        PackageManager PM = context.getPackageManager(); //Retrieve the list of packages on a particular device.

        //To check if a given device has flash or not.
        if(!PM.hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)){
            Log.e("ERROR!","No flah present");
            Toast.makeText(this, "Has No Flash", Toast.LENGTH_SHORT).show();
            return;
        }

        camera = Camera.open();
        final Camera.Parameters p = camera.getParameters();


        //Based on the position of the toggle to turn the flash light ON or OFF.
        FlashController.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                //Turn ON  flash
                if (isChecked) {
                    p.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                    camera.setParameters(p);
                    camera.startPreview();
                    Log.i("ON","Flash is turned ON");
                }

                //Turn OFF flash
                else {
                    p.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                    camera.setParameters(p);
                    camera.stopPreview();
                    Log.i("OFF","Flash is turned OFF!");
                }
            }
        });
    }

}
