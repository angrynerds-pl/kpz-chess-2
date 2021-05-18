package com.example.kpz_chess_2.activities.game

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import com.example.kpz_chess_2.MainActivity
import com.example.kpz_chess_2.R
import com.google.android.material.navigation.NavigationView
import com.google.ar.core.*
import com.google.ar.core.ArCoreApk.Availability.*
import com.google.ar.core.examples.java.common.helpers.CameraPermissionHelper
import com.google.ar.core.exceptions.*
import com.google.ar.sceneform.AnchorNode
import com.google.ar.sceneform.rendering.MaterialFactory
import com.google.ar.sceneform.ux.ArFragment
import kotlinx.android.synthetic.main.activity_game.*
import kotlinx.android.synthetic.main.content_game.*
import java.util.*


class GameActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    //private var mSession: Session? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(
            this,
            drawer_layout,
            toolbar,
            R.string.open,
            R.string.close
        )
        toggle.isDrawerIndicatorEnabled = true
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_menu.setNavigationItemSelectedListener(this)

        //maybeEnableArButton()
        val arFragment: ArFragment = supportFragmentManager.findFragmentById(R.id.fragment) as ArFragment
        arFragment.setOnTapArPlaneListener{ hitResult, plane, motionEvent ->
            val anchor = hitResult.createAnchor()

        }
        /*arFragment.setOnTapArPlaneListener((hitResult, plane, motionEvent) -> {
            Anchor anchor = hitResult.createAnchor();

            MaterialFactory.makeOpaqueWithColor(this, new Color(android.graphics.Color.RED))
                    .thenAccept(material -> {
            ModeRenderable renderable - ShapeFacotry.makeSphere(1.0f, new Vector3(0f, 1f, 1f), material);

            AnchorNode. anchorNode = new AnchorNode(anchor);
            anchorNode.setRenderable(renderable);
            arFragment.gerArSceneView().getScene().addChild(anchorNode);
        })
        })*/
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.nav_save_btn) {
            Toast.makeText(applicationContext, "Game saved!", Toast.LENGTH_SHORT).show()
        } else if (item.itemId == R.id.nav_menu_btn) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        } else if (item.itemId == R.id.nav_exit_game_btn) {
            finishAffinity()
        }
        return true
    }

    /*private fun ArFragment.setOnTapArPlaneListener((hitResult: HitResult, plane: Plane, motionEvent: MotionEvent) {
    anchor: Anchor = hitResult.
});*/

    /*fun maybeEnableArButton() {
        val availability = ArCoreApk.getInstance().checkAvailability(this)
        if (availability.isTransient) {
            // Continue to query availability at 5Hz while compatibility is checked in the background.
            Handler().postDelayed({
                maybeEnableArButton()
            }, 200)
        }
        if (availability.isSupported) {
            Log.d("AR", "ar supported")
            mArButton.visibility = View.VISIBLE
            mArButton.isEnabled = true
        } else { // The device is unsupported or unknown.
            mArButton.visibility = View.INVISIBLE
            mArButton.isEnabled = false
        }
    }

    var mUserRequestedInstall = true

    override fun onDestroy() {
        if (mSession != null) {
            // Explicitly close ARCore Session to release native resources.
            // Review the API reference for important considerations before calling close() in apps with
            // more complicated lifecycle requirements:
            // https://developers.google.com/ar/reference/java/arcore/reference/com/google/ar/core/Session#close()
            mSession!!.close()
            mSession = null
        }
        super.onDestroy()
    }
    override fun onResume() {
        super.onResume()

        // ARCore requires camera permission to operate.
        if (!CameraPermissionHelper.hasCameraPermission(this)) {
            CameraPermissionHelper.requestCameraPermission(this)
            return
        }
        // Ensure that Google Play Services for AR and ARCore device profile data are
        // installed and up to date.
        try {
            if (mSession == null) {
                when (ArCoreApk.getInstance().requestInstall(this, mUserRequestedInstall)) {
                    ArCoreApk.InstallStatus.INSTALLED -> {
                        // Success: Safe to create the AR session.
                        mSession = Session(this)
                        // Create a session config.
                        //val config = Config(mSession)

                        val filter = CameraConfigFilter(mSession)

                        // Return only camera configs that target 30 fps camera capture frame rate.
                        filter.targetFps = EnumSet.of(CameraConfig.TargetFps.TARGET_FPS_30)

                        // Return only camera configs that will not use the depth sensor.
                        filter.depthSensorUsage = EnumSet.of(CameraConfig.DepthSensorUsage.DO_NOT_USE)

                        // Get list of configs that match filter settings.
                        // In this case, this list is guaranteed to contain at least one element,
                        // because both TargetFps.TARGET_FPS_30 and DepthSensorUsage.DO_NOT_USE
                        // are supported on all ARCore supported devices.
                        val cameraConfigList = mSession!!.getSupportedCameraConfigs(filter)

                        // Use element 0 from the list of returned camera configs. This is because
                        // it contains the camera config that best matches the specified filter
                        // settings.
                        mSession!!.cameraConfig = cameraConfigList[0]

                        // Do feature-specific operations here, such as enabling depth or turning on
                        // support for Augmented Faces.

                        // Configure the session.
                        //mSession.configure(config)
                    }
                    ArCoreApk.InstallStatus.INSTALL_REQUESTED -> {
                        // When this method returns `INSTALL_REQUESTED`:
                        // 1. ARCore pauses this activity.
                        // 2. ARCore prompts the user to install or update Google Play
                        //    Services for AR (market://details?id=com.google.ar.core).
                        // 3. ARCore downloads the latest device profile data.
                        // 4. ARCore resumes this activity. The next invocation of
                        //    requestInstall() will either return `INSTALLED` or throw an
                        //    exception if the installation or update did not succeed.
                        mUserRequestedInstall = false
                        return
                    }
                }
            }
        } catch (e: UnavailableUserDeclinedInstallationException) {
            // Display an appropriate message to the user and return gracefully.
            Toast.makeText(this, "Please install ARCore " + e, Toast.LENGTH_LONG)
                .show()
            Log.d("AR", "Please install ARCore")
            return
        } catch (e: UnavailableApkTooOldException) {
            Toast.makeText(this, "Please update ARCore " + e, Toast.LENGTH_LONG)
                .show()
            Log.d("AR", "Please update ARCore")
            return
        } catch (e: UnavailableSdkTooOldException) {
            Toast.makeText(this, "Please update this app " + e, Toast.LENGTH_LONG)
                .show()
            Log.d("AR", "Please update this app")
            return
        } catch (e: UnavailableDeviceNotCompatibleException) {
            Toast.makeText(this, "This device does not support AR " + e, Toast.LENGTH_LONG)
                .show()
            Log.d("AR", "This device does not support AR")
            return
        } catch (e: Exception) {
            Toast.makeText(this, "Failed to create AR session " + e, Toast.LENGTH_LONG)
                .show()
            Log.d("AR", "TFailed to create AR session")
            return
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        results: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, results)
        if (!CameraPermissionHelper.hasCameraPermission(this)) {
            Toast.makeText(
                this,
                "Camera permission is needed to run this application",
                Toast.LENGTH_LONG
            )
                .show()
            if (!CameraPermissionHelper.shouldShowRequestPermissionRationale(this)) {
                // Permission denied with checking "Do not ask again".
                CameraPermissionHelper.launchPermissionSettings(this)
            }
            finish()
        }
    }


    // Verify that ARCore is installed and using the current version.
    fun isARCoreSupportedAndUpToDate(): Boolean {
        return when (ArCoreApk.getInstance().checkAvailability(this)) {
            SUPPORTED_INSTALLED -> true
            SUPPORTED_APK_TOO_OLD, ArCoreApk.Availability.SUPPORTED_NOT_INSTALLED -> {
                try {
                    // Request ARCore installation or update if needed.
                    when (ArCoreApk.getInstance().requestInstall(this, true)) {
                        ArCoreApk.InstallStatus.INSTALL_REQUESTED -> {
                            Log.i(TAG, "ARCore installation requested.")
                            false
                        }
                        ArCoreApk.InstallStatus.INSTALLED -> true
                    }
                } catch (e: UnavailableException) {
                    Log.e(TAG, "ARCore not installed", e)
                    false
                }
            }

            UNSUPPORTED_DEVICE_NOT_CAPABLE ->
                // This device is not supported for AR.
                false

            UNKNOWN_CHECKING -> {
                TODO()
                // ARCore is checking the availability with a remote query.
                // This function should be called again after waiting 200 ms to determine the query result.
            }
            UNKNOWN_ERROR, UNKNOWN_TIMED_OUT -> {
                TODO()
                // There was an error checking for AR availability. This may be due to the device being offline.
                // Handle the error appropriately.
            }
            SUPPORTED_NOT_INSTALLED -> TODO()
        }
    }*/
}





