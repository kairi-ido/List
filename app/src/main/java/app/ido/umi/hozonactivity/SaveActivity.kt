package app.ido.umi.hozonactivity

import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_save.*
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

            imageButton.setOnClickListener {
                selectPicture()
            }

        }


        //登録ボタンを押したらnameEditTextの内容を登録する
        hozonButton.setOnClickListener {
            val name:String = nameEditText.text.toString()
            val date:String = selectText.text.toString()
            create(name,date)

            Toast.makeText(applicationContext, "登録しました", Toast.LENGTH_LONG).show()

        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != RESULT_OK) {
            return
        }
        when (requestCode) {
            READ_REQUEST_CODE -> {
                try {
                    data?.data?.also { uri ->
                        val inputStream = contentResolver?.openInputStream(uri)
                        val image = BitmapFactory.decodeStream(inputStream)
                        val imageView = findViewById<ImageView>(R.id.imageView)
                        imageView.setImageBitmap(image)
                    }
                } catch (e: Exception) {
                    Toast.makeText(this, "エラーが発生しました", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
    private fun selectPicture() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "image/*"
        }
        startActivityForResult(intent, READ_REQUEST_CODE)
    }

    companion object {
        private const val READ_REQUEST_CODE: Int = 42
    }

    //MainActivityへ戻ります
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }


    //realmに新規リストとしてnameEditTextで書いたことを追加
    fun create(name:String,date:String) {

            realm.executeTransaction {
                val task = it.createObject(Item::class.java, UUID.randomUUID().toString())

                task.name = name
                task.date = date

            }
        }


    }










