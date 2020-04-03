package dk.easj.anbo.showallapps;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.List;
import java.util.stream.Collectors;

// https://www.version2.dk/artikel/android-apps-giver-informationer-dine-apps-videre-tredjeparter-1090295
// https://www.zdnet.com/article/android-lets-advertisers-get-a-list-of-all-your-apps-and-this-api-feature-is-broadly-used/
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PackageManager packageManager = getPackageManager();
        List<ApplicationInfo> appInfos = packageManager.getInstalledApplications(PackageManager.GET_META_DATA);
        Log.d("UHA", appInfos.toString());


       /* List<String> appNames = new ArrayList<>(appInfos.size());
        for (int i = 0; i < appInfos.size(); i++) {
            ApplicationInfo appInfo = appInfos.get(i);
            //String appName = appInfo.name == null ? appInfo.toString() : appInfo.name;
            if (appInfo.name != null)
                appNames.add(appInfo.name);
        }*/

        // map function etc requires Java 8.
        // Java 8 set in the top of build.gradle
        List<String> appNames = appInfos.stream()
                .filter(applicationInfo -> applicationInfo.name != null)
                .map(applicationInfo -> applicationInfo.name)
                .sorted()
                .collect(Collectors.toList());

        Log.d("UHA", appNames.toString());

        TextView messageView = findViewById(R.id.mainMessageTextView);
        messageView.setText(appNames.size() + " apps on the device");

        RecyclerView recyclerView = findViewById(R.id.mainRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //RecyclerViewSimpleAdapter<ApplicationInfo> adapter = new RecyclerViewSimpleAdapter<>(appInfos);
        RecyclerViewSimpleAdapter<String> adapter = new RecyclerViewSimpleAdapter<>(appNames);
        recyclerView.setAdapter(adapter);

    }
}
