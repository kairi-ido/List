package app.ido.umi.hozonactivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.system.Os.read
import io.realm.Realm
import io.realm.RealmResults
import io.realm.Sort
import kotlinx.android.synthetic.main.activity_save.*
import kotlinx.android.synthetic.main.list_item.*
import java.util.*

class SaveActivity : AppCompatActivity() {

    private val realm: Realm by lazy {
        Realm.getDefaultInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_save)

        backButton.setOnClickListener {
            finish()
        }


        hozonButton.setOnClickListener {
            val name:String = nameEditText.text.toString()
            create(name)
        }

    }






    fun create(name:String) {

            realm.executeTransaction {
                val task = it.createObject(Task::class.java, UUID.randomUUID().toString())

                task.name = name


            }
        }

    }








