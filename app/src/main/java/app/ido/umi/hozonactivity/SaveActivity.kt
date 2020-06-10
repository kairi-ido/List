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
    //SaveActivityが起動した時に起こる
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_save)

        //ActionBarにMainActivityへ戻る矢印をつけます
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        //datepicker 宣言
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        //タップボタンを押したらdatepickerがでできて、selectTextに表示
        dateButton.setOnClickListener {
            val dtp = DatePickerDialog(
                this, DatePickerDialog.OnDateSetListener { view, y, m, d ->
                    selectText.text = "${y}年" + "${m + 1}月" + "${d}日"
                }, year, month, day
            )

            dtp.show()
        }
        //画像を選択する処理
        imageButton.setOnClickListener {
            selectPicture()
        }


        //登録ボタンを押したらnameEditTextの内容を登録する
        hozonButton.setOnClickListener {
            val name:String = nameEditText.text.toString()
            val date:String = selectText.text.toString()

            //create（）というメソッドに引数をして渡す
            create(name,date)
            //Toastで登録できたと表示する
            Toast.makeText(applicationContext, "登録しました", Toast.LENGTH_LONG).show()

        }
    }
    //ユーザーがピッカーでドキュメントを選択すると、onActivityResult()が呼び出しされる
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        //ギャラリーから戻ってきたことを識別する
        if (resultCode != RESULT_OK) {
            return
        }
        //結果コード:操作が成功した場合RESULT_OKが、ユーザーがバックアウトしたり、何らかの理由で失敗したりした場合はRESULT_CANCELEDが返る
        //画像ファイルからBitmapを生成し、ImageViewにセットする
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
    }//第３引数：resultDataは取得したデータで、選択したドキュメントを指すURIにはresultData.dataでアクセスできる


    //クライアントアプリ（ドキュメント プロバイダが返したファイルを受け取るカスタムアプリ):ACTION_OPEN_DOCUMENT
    //CATEGORY_OPENABLE:画像ファイルのみが開くことのできるintent
    //type:結果をさらにフィルタリング
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
    fun create(name: String, date: String) {

            //realm.executeTransactionというブロックを作り、その中でrealmを使ってデータベースの操作をする。
            //これを書くことでデータベースへの書き込み(データの作成、更新、削除)ができるようになる。
            realm.executeTransaction {
                //新規リスト作成。it.createObject(データ型::class.java)と書くことでDBに新しいオブジェクトを作って保存。
                val task = it.createObject(Item::class.java, UUID.randomUUID().toString())
                task.name = name
                task.date = date
            }
        }
    }


