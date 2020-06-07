package app.ido.umi.hozonactivity

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.system.Os.read
import android.widget.Toast
import io.realm.Realm
import io.realm.RealmResults
import io.realm.Sort
import kotlinx.android.synthetic.main.activity_save.*
import kotlinx.android.synthetic.main.list_item.*
import java.util.*

class SaveActivity : AppCompatActivity() {
    //realmの変数
    private val realm: Realm by lazy {
        Realm.getDefaultInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_save)

        //ActionBarにMainActivityへ戻る矢印をつけます
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        //datepicker 宣言
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val  month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        //タップボタンを押したらdatepickerがでできて、selectTextに表示
        dateButton.setOnClickListener {
            val dtp = DatePickerDialog(this, DatePickerDialog.OnDateSetListener{ view, y, m, d ->
                selectText.text = "${y}年"+"${m+1}月"+"${d}日"
            },year,month,day
            )

            dtp.show()

        }


        //登録ボタンを押したらnameEditTextの内容を登録する
        hozonButton.setOnClickListener {
            val name:String = nameEditText.text.toString()
            val date:String = selectText.text.toString()
            create(name,date)

            Toast.makeText(applicationContext, "登録しました", Toast.LENGTH_LONG).show()

        }
    }
    //MainActivityへ戻ります
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }


    //realmに新規リストとしてnameEditTextで書いたことを追加
    fun create(name:String,date:String) {

            realm.executeTransaction {
                val task = it.createObject(Task::class.java, UUID.randomUUID().toString())

                task.name = name
                task.date = date
            }
        }


    }








