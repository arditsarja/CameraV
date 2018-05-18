package org.witness.informacam.app;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Process;
import android.preference.PreferenceManager;
import android.util.Log;

import org.witness.informacam.InformaCam;
import org.witness.informacam.app.screens.wizard.OriginalImagePreference;
import org.witness.informacam.app.utils.Constants.App;
import org.witness.informacam.app.utils.Constants.App.Camera;
import org.witness.informacam.app.utils.Constants.App.Editor;
import org.witness.informacam.app.utils.Constants.App.Home;
import org.witness.informacam.app.utils.Constants.App.Login;
import org.witness.informacam.app.utils.Constants.App.Wizard;
import org.witness.informacam.app.utils.Constants.Codes;
import org.witness.informacam.app.utils.Constants.Preferences;
import org.witness.informacam.crypto.KeyUtility;
import org.witness.informacam.models.utils.ILanguageMap;
import org.witness.informacam.ui.CameraActivity;
import org.witness.informacam.utils.Constants.Logger;
import org.witness.informacam.utils.InformaCamBroadcaster.InformaCamStatusListener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class InformaActivity extends Activity implements InformaCamStatusListener {
    Intent init = null;
    Intent route = null;
    int routeCode;

    InformaCam informaCam;
    private Handler mHandler;

    private final static String LOG = "InformaApp";

    public final static String HOCKEY_APP_ID = "dafbc649fcf585d7867866d5375b6495";

    private boolean prefStealthIcon = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPremission();
        prefStealthIcon = PreferenceManager.getDefaultSharedPreferences(this).getBoolean("prefStealthIcon", false);
        setIcon(prefStealthIcon);

        init = getIntent();

        setContentView(R.layout.activity_main);

        informaCam = (InformaCam) getApplication();

        mHandler = new Handler();

        if (getIntent().hasExtra(Codes.Extras.CHANGE_LOCALE) && getIntent().getBooleanExtra(Codes.Extras.CHANGE_LOCALE, false)) {
            onInformaCamStart(getIntent());
        }
    }

    private void getPremission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.CAMERA},
                        1000);
            }
            if (checkSelfPermission(Manifest.permission.INTERNET)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.INTERNET},
                        1000);
            }
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        1000);
            }
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        1000);
            }
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        1000);
            }
            if (checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                        1000);
            }
            if (checkSelfPermission(Manifest.permission.READ_PHONE_STATE)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE},
                        1000);
            }
            if (checkSelfPermission(Manifest.permission.BLUETOOTH)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.BLUETOOTH},
                        1000);
            }
            if (checkSelfPermission(Manifest.permission.BLUETOOTH_ADMIN)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.BLUETOOTH_ADMIN},
                        1000);
            }
            if (checkSelfPermission(Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.CAMERA},
                        1000);
            }
            if (checkSelfPermission(Manifest.permission.WAKE_LOCK)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.WAKE_LOCK},
                        1000);
            }
            if (checkSelfPermission(Manifest.permission.RECORD_AUDIO)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.RECORD_AUDIO},
                        1000);
            }
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        1000);
            }
            if (checkSelfPermission(Manifest.permission.ACCESS_WIFI_STATE)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_WIFI_STATE},
                        1000);
            }
            if (checkSelfPermission(Manifest.permission.CHANGE_WIFI_STATE)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.CHANGE_WIFI_STATE},
                        1000);
            }
            if (checkSelfPermission(Manifest.permission.ACCESS_NETWORK_STATE)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_NETWORK_STATE},
                        1000);
            }
            if (checkSelfPermission(Manifest.permission.VIBRATE)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.VIBRATE},
                        1000);
            }
            if (checkSelfPermission(Manifest.permission.KILL_BACKGROUND_PROCESSES)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.KILL_BACKGROUND_PROCESSES},
                        1000);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        informaCam = (InformaCam) getApplication();
        informaCam.setStatusListener(this);

        //Log.d(LOG, "AND HELLO onResume()!!");

        try {
            if (route != null) {
                routeByIntent();
            } else {
                Log.d(LOG, "route is null now, please wait");
                Log.d(LOG, "hasCredentialManager? " + String.valueOf(informaCam.hasCredentialManager()));

                if (informaCam.hasCredentialManager()) {
                    Log.d(LOG, "NOW ASKING FOR CM STATUS...");

                    switch (informaCam.getCredentialManagerStatus()) {
                        case org.witness.informacam.utils.Constants.Codes.Status.UNLOCKED:
                            route = new Intent(this, HomeActivity.class);

                            if (prefStealthIcon)
                                route.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);

                            routeCode = Home.ROUTE_CODE;
                            break;
                        case org.witness.informacam.utils.Constants.Codes.Status.LOCKED:
                            route = new Intent(this, LoginActivity.class);
                            routeCode = Login.ROUTE_CODE;
                            break;
                    }

                    routeByIntent();
                } else {
                    Log.d(LOG, "no, not logged in");
                }
            }

        } catch (NullPointerException e) {
            Logger.e(LOG, e);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(App.TAG, true);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        informaCam.setStatusListener(this);

        if (resultCode == Activity.RESULT_CANCELED) {

            if (informaCam.isOutsideTheLoop(init.getAction())) {
                //Logger.d(LOG, "coming back from VMM call WITH NOOOOO MEDIA, and i shoudl finish.");
                setResult(resultCode, getIntent());
                finish();
                return;
            } else if (data != null && data.hasExtra(Codes.Extras.LOGOUT_USER) && data.getBooleanExtra(Codes.Extras.LOGOUT_USER, false)) {
                Logger.d(LOG, "Logout the user and close.");
                informaCam.setStatusListener(null);
                //informaCam.stopInforma();
                route = null;
                setResult(resultCode, getIntent());
                finish();

                if (data.hasExtra(Codes.Extras.PERFORM_WIPE) && data.getBooleanExtra(Codes.Extras.PERFORM_WIPE, false)) {
                    wipe();
                }

                return;
            }

            // XXX: DOES THIS BREAK LOGOUT?
            setResult(resultCode, data);
            finish();

        } else if (resultCode == Activity.RESULT_OK) {
            Log.d(LOG, "returning with request code " + requestCode);

			/*
            if(informaCam.isOutsideTheLoop(init.getAction())) {
				Logger.d(LOG, "coming back from VMM call with SOME media, and i shoudl finish.");
				
				// TODO:
				 immediately 
				 1) chooser
				 2) encrypt (all? selected?) to org
				 3) start up a transport for each returned media
				
				finish();
				return;
			}
			*/

            route = new Intent(this, HomeActivity.class);
            if (prefStealthIcon)
                route.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);

            routeCode = Home.ROUTE_CODE;

            switch (requestCode) {
                case Codes.Routes.CAMERA:
                    route = new Intent(this, EditorActivity.class);
                    routeCode = Editor.ROUTE_CODE;

                    break;
                case Codes.Routes.EDITOR:

                    break;
                case Codes.Routes.LOGIN:

                    break;
                case Codes.Routes.HOME:
                    if (data != null && data.hasExtra(Codes.Extras.CHANGE_LOCALE)) {
                        route.putExtra(Codes.Extras.CHANGE_LOCALE, true);
                    }

                    break;
                case Codes.Routes.WIZARD:
                    route = new Intent(this, HomeActivity.class);
                    if (prefStealthIcon)
                        route.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);

                    routeCode = Home.ROUTE_CODE;
                    route.putExtra(Codes.Extras.GENERATING_KEY, true);

                    if (KeyUtility.initDevice()) {

                        informaCam.user.hasCompletedWizard = true;
                        informaCam.user.lastLogIn = System.currentTimeMillis();
                        informaCam.user.isLoggedIn = true;

                        informaCam.saveState(informaCam.user);

                    }

                    break;
            }

            routeByIntent();
        } else if (resultCode == Activity.RESULT_FIRST_USER) {
            if (data.hasExtra(Codes.Extras.CHANGE_LOCALE) && data.getBooleanExtra(Codes.Extras.CHANGE_LOCALE, false)) {
                if (route != null)
                    route.putExtra(Codes.Extras.CHANGE_LOCALE, true);
            }

        }
    }

    private void routeByIntent() {
        if (route == null) {
            return;
        }

        Log.d(LOG, "intent is: " + init.getAction());

        if (Intent.ACTION_MAIN.equals(init.getAction())) {

        } else if ("android.media.action.IMAGE_CAPTURE".equals(init.getAction())) {
            route = new Intent(this, CameraActivity.class);
            routeCode = Camera.ROUTE_CODE;
        } else if (Intent.ACTION_VIEW.equals(init.getAction())) {
            route.setData(init.getData());
        } else if ("info.guardianproject.action.VERIFIED_MOBILE_MEDIA".equals(init.getAction())) {
            route = new Intent(this, CameraActivity.class);
            routeCode = Camera.ROUTE_CODE;
        }

        route.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivityForResult(route, routeCode);
    }

    @Override
    public void onInformaCamStart(Intent intent) {
        int code = intent.getBundleExtra(org.witness.informacam.utils.Constants.Codes.Keys.SERVICE).getInt(
                org.witness.informacam.app.utils.Constants.Codes.Extras.MESSAGE_CODE);
        //Log.d(LOG, "STARTING INFORMACAM ON IWITNESS (routeCode = " + code + ")");

        switch (code) {
            case org.witness.informacam.utils.Constants.Codes.Messages.Wizard.INIT:
                ArrayList<String> wizardFragments = new ArrayList<String>();
                wizardFragments.add(OriginalImagePreference.class.getName());

                route = new Intent(this, org.witness.informacam.app.WizardActivity.class);

                route.putStringArrayListExtra(Codes.Extras.WIZARD_SUPPLEMENT, wizardFragments);
                ILanguageMap languageMap = new ILanguageMap();

                for (int l = 0; l < getResources().getStringArray(R.array.languages_).length; l++) {
                    languageMap.add(getResources().getStringArray(R.array.locales)[l], getResources().getStringArray(R.array.languages_)[l]);
                }

                route.putExtra(Codes.Extras.SET_LOCALES, languageMap);
                route.putExtra(Codes.Extras.LOCALE_PREF_KEY, Preferences.Keys.LANGUAGE);
                routeCode = Wizard.ROUTE_CODE;
                break;
            case org.witness.informacam.utils.Constants.Codes.Messages.Login.DO_LOGIN:
                route = new Intent(this, LoginActivity.class);
                routeCode = Login.ROUTE_CODE;
                break;
            case org.witness.informacam.utils.Constants.Codes.Messages.Home.INIT:
                route = new Intent(this, HomeActivity.class);
                if (prefStealthIcon)
                    route.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);

                routeCode = Home.ROUTE_CODE;
                break;
        }

        routeByIntent();
    }

    @Override
    public void onInformaCamStop(Intent intent) {
    }

    @Override
    public void onInformaStop(Intent intent) {
    }

    @Override
    public void onInformaStart(Intent intent) {
    }


    public void wipe() {
        String action = PreferenceManager.getDefaultSharedPreferences(this).getString(Preferences.Keys.PANIC_ACTION, "0");
        boolean wipeEntireApp = (Integer.parseInt(action) == 1);

        dataWipe();
        if (wipeEntireApp) {
            deleteApp();
        } else {
            Process.killProcess(Process.myPid());
        }
    }

    private void deleteApp() {
        Uri packageURI = Uri.parse("package:" + getApplicationContext().getPackageName());
        Intent uninstallIntent = new Intent(Intent.ACTION_DELETE, packageURI);
        uninstallIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getApplicationContext().startActivity(uninstallIntent);
    }

    private void dataWipe() {
        Log.v(LOG, "Delete data");
        File cache = getCacheDir();
        File appDir = new File(cache.getParent());
        if (appDir.exists()) {
            String[] children = appDir.list();
            for (String s : children) {
                if (!s.equals("lib")) {
                    deleteDir(new File(appDir, s));
                }
            }
        }

        // Delete all possible locations
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            appDir = getApplicationContext().getExternalFilesDir(null);
            if (appDir.exists())
                deleteDir(appDir);
        }


    }

    public void deleteDir(File dir) {
        if (dir != null && dir.exists() && dir.isDirectory()) {
            File[] children = dir.listFiles();
            for (File f : children) {
                if (f.isDirectory())
                    deleteDir(f);
                else {
                    f.delete();
                    Log.v(LOG, "Deleted file " + f.getAbsolutePath());
                }
            }
            dir.delete();
            Log.v(LOG, "Deleted dir " + dir.getAbsolutePath());
        }
    }

    private void setIcon(boolean enableAltIcon) {
        Context ctx = this;
        PackageManager pm = getPackageManager();
        ActivityManager am = (ActivityManager) getSystemService(Activity.ACTIVITY_SERVICE);

        // Enable/disable activity-aliases


        pm.setComponentEnabledSetting(
                new ComponentName(ctx, "org.witness.informacam.app.InformaActivity-Alt"),
                enableAltIcon ? PackageManager.COMPONENT_ENABLED_STATE_ENABLED : PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP
        );

        pm.setComponentEnabledSetting(
                new ComponentName(ctx, "org.witness.informacam.app.InformaActivity"),
                (!enableAltIcon) ? PackageManager.COMPONENT_ENABLED_STATE_ENABLED : PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP
        );

        // Find launcher and kill it

        Intent i = new Intent(Intent.ACTION_MAIN);
        i.addCategory(Intent.CATEGORY_HOME);
        i.addCategory(Intent.CATEGORY_DEFAULT);
        List<ResolveInfo> resolves = pm.queryIntentActivities(i, 0);
        for (ResolveInfo res : resolves) {
            if (res.activityInfo != null) {
                am.killBackgroundProcesses(res.activityInfo.packageName);
            }
        }

        // Change ActionBar icon

    }

}