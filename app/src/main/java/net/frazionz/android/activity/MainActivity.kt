package net.frazionz.android.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import fr.northenflo.webiauth.utils.FZPermission
import net.frazionz.android.R
import net.frazionz.android.auth.FzProfileItem
import net.frazionz.android.utils.FZUtils
import net.frazionz.android.utils.FZUtils.*
import net.frazionz.android.utils.Post
import org.json.JSONArray
import java.io.FileWriter
import java.io.IOException
import java.util.concurrent.Executor


class MainActivity : AppCompatActivity() {

    var listPresObj: java.util.ArrayList<PresentationObject> = ArrayList();

    private lateinit var executor: Executor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val policy = ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        val permsCheck = ArrayList<String>()
        FZPermission.values().forEach {
            if(ContextCompat.checkSelfPermission(this@MainActivity, it.permission) != PackageManager.PERMISSION_GRANTED)
                permsCheck.add(it.permission)
        }

        if(permsCheck.size > 0)
            ActivityCompat.requestPermissions(this@MainActivity, permsCheck.toTypedArray(), 101)
        else {
            loadApp()
        }
    }

    private fun loadApp(){
        if(!FZUtils.dirApp.exists())
            FZUtils.dirApp.mkdirs()

        if (!fileProfiles.exists()) {
            try {
                fileProfiles.createNewFile()
                try {
                    val authProfiles = FileWriter(fileProfiles)
                    authProfiles.write(JSONArray().toString())
                    authProfiles.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            } catch (ignored: IOException) {
            }
        }

        if (doListProfiles(this)) {
            loadListProfiles(this)
        }

        listPresObj.add(PresentationObject("Bienvenue sur l'application FrazionZ !", "Découvrez l’application dès maintenant !"))
        listPresObj.add(PresentationObject("Gestion de votre compte", "Afficher et éditer les infos de votre compte ainsi\nque modifier votre apparence via l’app est possible !"))
        listPresObj.add(PresentationObject("Boutique", "Acheter des items ou des grades\nou revendre directement sans être connecté !"))
        listPresObj.add(PresentationObject("Sécurité", "FrazionZ Authentificator\nest implenté  dans cette App !\nVous pouvez en savoir plus via la page dédiée"))
        listPresObj.add(PresentationObject("Votre Faction", "Vous pouvez voir les informations\net l’état actuelle de votre Faction\nsur l’app !"))

        val appSettings: SharedPreferences = FZUtils.getSharedPreferences(this, "appSettings");
        if(appSettings != null){
            //CHECK IF APP ALREADY SHOW PRESENTATION
            val showPresentation: Boolean = appSettings.getBoolean("showPresentation", false);
            if(!showPresentation)
                //SHOW PRESENTATION APP
                showPresentation(appSettings);
            else{

                val policy = ThreadPolicy.Builder().permitAll().build()
                StrictMode.setThreadPolicy(policy)

                Post.getPosts().clear()

                FZUtils.loadMorePosts(this, "1", true);

                FZUtils.loadItemsMC(this);

                setContentView(R.layout.activity_main_login)
                //CONTINUE LOAD APP
                if(fzAuthProfiles.size == 1){
                    //LOGIN AUTO PROFILE
                    authLastAccount()
                }else if(fzAuthProfiles.size > 1){
                    //SHOW LIST PROFILE
                }else{
                    startActivity(Intent(this, AddAccountActivity::class.java))
                    finish()
                }
            }
        }else{
            //SHOW PRESENTATION APP
            showPresentation(appSettings);
        }
    }

    fun authLastAccount(){
        val profilObject: FzProfileItem = FZUtils.getFzAuthProfiles().get(0)
        FZUtils.authToken(
            this,
            "logToken",
            profilObject,
            false,
            true
        )
    }

    @SuppressLint("CommitPrefEdits")
    private fun showPresentation(appSettings: SharedPreferences) {
        Toast.makeText(this, "Load Presentation", Toast.LENGTH_LONG).show();
        val editor: SharedPreferences.Editor? = appSettings.edit()
        editor?.putBoolean("showPresentation", true);
        editor?.apply();

        setContentView(R.layout.activity_main)

        val titleText: TextView = findViewById<TextView>(R.id.titlePres)
        val subtitleText: TextView = findViewById<TextView>(R.id.subtitlePres)
        val btnNextPres: Button = findViewById<Button>(R.id.btnNextPres)

        var i: Int = 0;
        val iFinal: Int = listPresObj.size;
        if(listPresObj.size > 0){
            titleText.text = listPresObj.get(0).title
            subtitleText.text = listPresObj.get(0).subtitle
            titleText.startAnimation(FZUtils.fadeAnimation(0))
            subtitleText.startAnimation(FZUtils.fadeAnimation(0))
            val instance: MainActivity = this;
            btnNextPres.setOnClickListener {
                i++;
                if (i >= iFinal){
                    instance.startActivity(Intent(this, AddAccountActivity::class.java))
                    finish()
                }else {
                    val presObj: PresentationObject = listPresObj.get(i);

                    titleText.startAnimation(FZUtils.fadeAnimation(1))
                    subtitleText.startAnimation(FZUtils.fadeAnimation(1))
                    titleText.text = presObj.title
                    subtitleText.text = presObj.subtitle
                    titleText.startAnimation(FZUtils.fadeAnimation(0))
                    subtitleText.startAnimation(FZUtils.fadeAnimation(0))
                }
            }
        }
    }

    class PresentationObject(title: String, subtitle: String) {

        var title: String? = title;
        var subtitle: String? = subtitle;

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode ==  101) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                loadApp()
            } else {
                Toast.makeText(this, "Impossible de continuer, l'app doit s'arrêter.", Toast.LENGTH_LONG).show()
                this.finish()
            }
        }
    }

}