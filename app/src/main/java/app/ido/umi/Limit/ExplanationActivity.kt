package app.ido.umi.Limit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class ExplanationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_explanation)

        //タイトル、アイコン表示
        supportActionBar?.title = "ホーム"
        getSupportActionBar()?.setDisplayShowHomeEnabled(true)
        getSupportActionBar()?.setIcon(R.mipmap.ic_launcher_foreground)

        //戻る矢印表示
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
        //MainActivityに戻る
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }
}
