package jp.kyam.handlersample;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.lang.ref.WeakReference;


public class MainActivity extends Activity{
    private MyHandler mMyHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMyHandler = new MyHandler(this);
        final Button mBtnSendMessage = (Button)findViewById(R.id.btn_send_message);
        mBtnSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message msg = new Message();
                msg.what = MyHandler.TYPE_HOGE;
                msg.obj = "Hello!";
                mMyHandler.sendMessage(msg);
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

    private static class MyHandler extends Handler{
        public static final int TYPE_HOGE = 0;

        //Activityの弱参照
        private WeakReference<Activity> mActivityWeakReference;

        private MyHandler(Activity activity){
            mActivityWeakReference = new WeakReference<Activity>(activity);
        }

        @Override
        public void handleMessage(Message msg){
            Activity activity = mActivityWeakReference.get();
            if(activity == null){
                //Activityが既に破棄されている時
                return;
            }

            switch (msg.what){
                case TYPE_HOGE:
                    Toast.makeText(activity.getBaseContext(), msg.obj.toString(), Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    }
}
