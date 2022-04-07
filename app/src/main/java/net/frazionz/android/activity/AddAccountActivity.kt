package net.frazionz.android.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import net.frazionz.android.R
import net.frazionz.android.activity.LoginActivity
import net.frazionz.android.auth.FzAuthProfile
import net.frazionz.android.auth.FzLoginAuth
import net.frazionz.android.utils.FZUtils

class AddAccountActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addaccount)

        val btnAddProfile: Button = findViewById(R.id.btnAddProfile)
        btnAddProfile.setOnClickListener {
            val loginActivity: Intent = Intent(this, LoginActivity::class.java)
            startActivity(loginActivity)
        }
    }

}