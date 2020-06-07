package app.ido.umi.hozonactivity


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu


class ShoppingActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shopping)


        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }



        override fun onSupportNavigateUp(): Boolean {
            finish()
            return super.onSupportNavigateUp()
        }

    }