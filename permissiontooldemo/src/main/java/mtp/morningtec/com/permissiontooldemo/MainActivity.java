package mtp.morningtec.com.permissiontooldemo;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import yang.developtools.toollibrary.common.util.permission.PermissionApply;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final PermissionApply apply = new PermissionApply(this);
        TextView text = (TextView)findViewById(R.id.text);
        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                apply.requestLocationPermission(new PermissionApply.OnPermissResponse() {
                    @Override
                    public void onPermissionSuccess() {
                        Log.i("mtsdk","success");
                    }
                });

//                apply.requestPhonePermission(new PermissionApply.OnPermissResponse() {
//                    @Override
//                    public void onPermissionSuccess() {
//                        Intent callIntent = new Intent(Intent.ACTION_CALL);
//                        callIntent.setData(Uri.parse("tel:10086"));
//                        callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                        startActivity(callIntent);
//                    }
//                });
            }
        });
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.i("permission","222222222222222222");
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
