package net.frazionz.android.activity

import android.content.Intent
import android.os.Bundle
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import net.frazionz.android.R
import net.frazionz.android.auth.JTrixUtil
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL


class Login2FActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_twofactor)

        val policy = ThreadPolicy.Builder()
            .permitAll().build()
        StrictMode.setThreadPolicy(policy)

        val accessToken: String? = intent.getStringExtra("accessToken")
        val codeInput: TextInputEditText = findViewById(R.id.loginActivity_code)
        val loginActivitySubmit: MaterialButton = findViewById(R.id.loginActivity_submit)
        loginActivitySubmit.setOnClickListener {
            val url = URL("https://auth.frazionz.net/login/ul2fa");
            val con: HttpURLConnection = url.openConnection() as HttpURLConnection
            con.requestMethod = "POST";
            con.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:56.0) Gecko/20100101 Firefox/56.0");
            con.setRequestProperty("Content-Type", "application/json; utf-8");
            con.setRequestProperty("Accept", "application/json");
            con.setChunkedStreamingMode(0);
            con.doOutput = true;
            val paramsPost: JSONObject = JSONObject();
            paramsPost.put("accessToken", accessToken)
            paramsPost.put("client", "android")
            paramsPost.put("code-only", codeInput.text)
            val test: String = paramsPost.toString();
            con.outputStream.use { os ->
                val input: ByteArray = test.encodeToByteArray()
                os.write(input, 0, input.size)
            }
            BufferedReader(InputStreamReader(con.inputStream, "utf-8")
            ).use { br ->
                val response = StringBuilder()
                var responseLine: String? = null
                while (br.readLine().also { responseLine = it } != null) {
                    response.append(responseLine!!.trim { it <= ' ' })
                }
                if(response.toString().isNotEmpty()){
                    var responseJson: JSONObject = JSONObject(response.toString())
                    if(responseJson.has("error")) {
                        if (responseJson.getBoolean("error"))
                            if (responseJson.has("datas")) {
                                responseJson = responseJson.getJSONObject("datas")
                                Toast.makeText(
                                    this,
                                    responseJson.getString("result"),
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                    }else {
                        responseJson = responseJson.getJSONObject("original")
                        if(responseJson.getBoolean("exist"))
                            JTrixUtil.proccessFinishLogged(this, responseJson)
                    }
                }
            }
        }
    }
}