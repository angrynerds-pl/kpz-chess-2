package com.example.kpz_chess_2.activities.game

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.view.MotionEvent
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import com.example.kpz_chess_2.MainActivity
import com.example.kpz_chess_2.R
import com.google.android.filament.ColorGrading
import com.google.android.material.navigation.NavigationView
import com.google.ar.core.HitResult
import com.google.ar.core.Plane
import com.google.ar.sceneform.AnchorNode
import com.google.ar.sceneform.ArSceneView
import com.google.ar.sceneform.Node
import com.google.ar.sceneform.Sceneform
import com.google.ar.sceneform.math.Vector3
import com.google.ar.sceneform.rendering.EngineInstance
import com.google.ar.sceneform.rendering.ModelRenderable
import com.google.ar.sceneform.rendering.Renderable
import com.google.ar.sceneform.ux.ArFragment
import com.google.ar.sceneform.ux.BaseArFragment.OnTapArPlaneListener
import com.google.ar.sceneform.ux.TransformableNode
import kotlinx.android.synthetic.main.activity_game.*
import kotlinx.android.synthetic.main.content_game.*
import java.lang.ref.WeakReference


class GameActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, OnTapArPlaneListener, ArFragment.OnViewCreatedListener {

    private lateinit var arFragment: ArFragment
    private lateinit var model : Renderable
    private lateinit var modelPawn : Renderable
    private lateinit var modelRook : Renderable


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

        supportFragmentManager.addFragmentOnAttachListener { fragmentManager, fragment ->
            if (fragment.getId() === R.id.fragment) {
                arFragment = fragment as ArFragment
                arFragment.setOnTapArPlaneListener(this@GameActivity)
                arFragment.setOnViewCreatedListener(this@GameActivity)
            }
        }

        if (savedInstanceState == null) {
            if (Sceneform.isSupported(this)) {
                supportFragmentManager.beginTransaction()
                    .add(R.id.fragment, ArFragment::class.java, null)
                    .commit()
            }
        }

        loadModels()

        /*
        arFragment = supportFragmentManager.findFragmentById(R.id.fragment) as ArFragment

        setModelPath("model.sfb")

        arFragment.setOnTapArPlaneListener { hitResult, plane, _ ->
            if(plane.type != Plane.Type.HORIZONTAL_UPWARD_FACING) {
                return@setOnTapArPlaneListener
            }
            val anchor = hitResult.createAnchor()
            placeObject(arFragment, anchor, selectedObject)
        }*/
    }

    override fun onViewCreated(arFragment: ArFragment?, arSceneView: ArSceneView) {
        // Currently, the tone-mapping should be changed to FILMIC
        // because with other tone-mapping operators except LINEAR
        // the inverseTonemapSRGB function in the materials can produce incorrect results.
        // The LINEAR tone-mapping cannot be used together with the inverseTonemapSRGB function.
        val renderer = arSceneView.renderer
        if (renderer != null) {
            renderer.filamentView.colorGrading = ColorGrading.Builder()
                .toneMapping(ColorGrading.ToneMapping.FILMIC)
                .build(EngineInstance.getEngine().filamentEngine)
        }
    }

    fun loadModels() {
        val weakActivity = WeakReference<GameActivity>(this)
        ModelRenderable.builder()
            .setSource(this, Uri.parse("models/board.glb"))
            .setIsFilamentGltf(true)
            .build()
            .thenAccept { model: ModelRenderable ->
                val activity = weakActivity.get()
                if (activity != null) {
                    activity.model = model
                }
            }
            .exceptionally { throwable: Throwable? ->
                Toast.makeText(this, "Unable to load chessboard model", Toast.LENGTH_LONG).show()
                null
            }
        ModelRenderable.builder()
            .setSource(this, Uri.parse("models/pawn.glb"))
            .setIsFilamentGltf(true)
            .build()
            .thenAccept { model: ModelRenderable ->
                val activity = weakActivity.get()
                if (activity != null) {
                    activity.modelPawn = model
                }
            }
            .exceptionally { throwable: Throwable? ->
                Toast.makeText(this, "Unable to load chessboard model", Toast.LENGTH_LONG).show()
                null
            }

        ModelRenderable.builder()
            .setSource(this, Uri.parse("models/rook.glb"))
            .setIsFilamentGltf(true)
            .build()
            .thenAccept { model: ModelRenderable ->
                val activity = weakActivity.get()
                if (activity != null) {
                    activity.modelRook = model
                }
            }
            .exceptionally { throwable: Throwable? ->
                Toast.makeText(this, "Unable to load chessboard model", Toast.LENGTH_LONG).show()
                null
            }
    }

    override fun onTapPlane(hitResult: HitResult, plane: Plane?, motionEvent: MotionEvent?) {
        if (model == null) {
            Toast.makeText(this, "Loading...", Toast.LENGTH_SHORT).show()
            return
        }

        // Create the Anchor.
        val anchor = hitResult.createAnchor()
        val anchorNode = AnchorNode(anchor)
        anchorNode.setParent(arFragment.arSceneView.scene)

        // Create the transformable model and add it to the anchor.
        val modelNode = TransformableNode(arFragment.transformationSystem)
        modelNode.setParent(anchorNode)
        modelNode.renderable = model
        modelNode.select()

        val pawnNode = Node()
        pawnNode.setParent(modelNode)
        pawnNode.isEnabled = false
        pawnNode.localPosition = Vector3(0.0f, 0.1f, 0.0f)
        pawnNode.localScale = Vector3(0.2f, 0.2f, 0.2f)
        pawnNode.renderable = modelPawn
        pawnNode.isEnabled = true

        val rookNode = Node()
        rookNode.setParent(modelNode)
        rookNode.isEnabled = false
        rookNode.localPosition = Vector3(0.2f, 0.1f, 0.0f)
        rookNode.localScale = Vector3(0.2f, 0.2f, 0.2f)
        rookNode.renderable = modelRook
        rookNode.isEnabled = true
    }

    /*
    private fun placeObject(fragment: ArFragment, anchor: Anchor, modelUri: Uri) {
        val modelRenderable = ModelRenderable.builder()
            .setSource((fragment.requireContext()), modelUri)
            .build()

        modelRenderable.thenAccept { renderableObject -> addNodeToScene(fragment, anchor, renderableObject) }

        modelRenderable.exceptionally {
            val toast = Toast.makeText(applicationContext, "Error", Toast.LENGTH_SHORT)
            toast.show()
            null
        }
    }

    private fun addNodeToScene(fragment: ArFragment, anchor: Anchor, renderableObject: Renderable) {
        val anchorNode = AnchorNode(anchor)
        val transformableNode = TransformableNode(fragment.transformationSystem)
        transformableNode.renderable = renderableObject
        transformableNode.setParent(anchorNode)
        fragment.arSceneView.scene.addChild(anchorNode)
        transformableNode.select()
    }

    private fun setModelPath(modelFileName: String) {
        selectedObject = Uri.parse(modelFileName)
        val toast = Toast.makeText(applicationContext, modelFileName, Toast.LENGTH_SHORT)
        toast.show()
    } */

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
}





