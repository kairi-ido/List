package app.ido.umi.hozonactivity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import io.realm.Realm
import io.realm.RealmResults
import io.realm.Sort
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

@Suppress("UNREACHABLE_CODE")
class MainActivity : AppCompatActivity() {
    //realm変数
    private val realm: Realm by lazy {
        Realm.getDefaultInstance()
    }
    //アプリをはじめに起動したときに起こる
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        //アクションバーにアイコンを表示
        supportActionBar?.title = "とりみっと"
        getSupportActionBar()?.setDisplayShowHomeEnabled(true)
        getSupportActionBar()?.setIcon(R.mipmap.ic_launcher_foreground)


        //フローティングアクションボタン機能
        val fab: View = findViewById(R.id.fab)
        fab.setOnClickListener {
            //賞味期限、商品名登録画面へ
            val intent = Intent(applicationContext, SaveActivity::class.java)
            startActivity(intent)
        }
        //taskListという変数に取得したデータを代入
        val taskList = readAll()

        // タスクリストが空だったときにダミーデータを生成する
        if (taskList.isEmpty()) {
            createDummyData()
        }
        //リストを削除する
        val adapter =
            TaskAdapter(this, taskList, object : TaskAdapter.OnItemClickListener {
                override fun onItemClick(item: Item) {
                    // クリック時の処理

                    Toast.makeText(applicationContext, item.name + "を削除しました", Toast.LENGTH_SHORT)
                        .show()
                    delete(item.id)
                }
                //checkbox関連
                override fun  onChosenItemsClick(task: Item, checked: Boolean){
                    // クリック時の処理
                    Toast.makeText(applicationContext, "買い物リストに移行します", Toast.LENGTH_LONG).show()

                    realm.executeTransaction {
                        val task = it.createObject(Item::class.java, UUID.randomUUID().toString())
                        task.needPurchase = checked
                    }
                }

            }, true)

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter


    }
    //メニュー関連
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)

        val inflater = menuInflater
        //メニューのリソース選択
        inflater.inflate(R.menu.bottom_navigation_menu, menu)
        return true
    }

    //メニューのアイテムを押下した時の処理の関数
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            //ホームを押したときの処理
            R.id.navi_home -> {

                val intent = Intent(applicationContext, ExplanationActivity::class.java)
                startActivity(intent)
                return true
            }
            //残り物リストを押した時の処理
            R.id.navi_list -> {

                val intent = Intent(applicationContext, MainActivity::class.java)
                startActivity(intent)
                return true
            }
            //買い物リストを押した時の処理
            R.id.navi_shopping -> {

                val intent = Intent(applicationContext, ShoppingActivity::class.java)
                startActivity(intent)
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
    //画面終了時にrealm終了
    override fun onDestroy() {
        super.onDestroy()
        realm.close()

    }

    //ダミーデータを作る詳細
    fun createDummyData() {
        for (i in 0..10) {
            create(R.drawable.ic_launcher_background, "商品名 $i")
        }
    }

    //ダミーデータを作ります
    fun create(imageId: Int, content: String) {
        realm.executeTransaction {
            val task = it.createObject(Item::class.java, UUID.randomUUID().toString())
            task.imageId = imageId
            task.content = content
        }
    }
    //メソッド名（readAll)と返り値RealmResults<Item>を指定
    fun readAll(): RealmResults<Item> {
        //realm.where(Item::class.java).findAll()→realmを使ってデータベースの中のItemリストから全部のデータを取り出している
        //データを昇順に並べてくれるぞ
        return realm.where(Item::class.java).findAll().sort("date", Sort.ASCENDING)
    }

    fun update(id: String, content: String) {
        realm.executeTransaction {
            val task = realm.where(Item::class.java).equalTo("id", id).findFirst()
                ?: return@executeTransaction
            task.content = content
        }
    }

    fun update(task: Item, content: String) {
        realm.executeTransaction {
            task.content = content
        }
    }

    fun delete(id: String) {
        realm.executeTransaction {
            val task = realm.where(Item::class.java).equalTo("id", id).findFirst()
                ?: return@executeTransaction
            task.deleteFromRealm()
        }
    }

    fun delete(task: Item) {
        realm.executeTransaction {
            task.deleteFromRealm()
        }
    }

    fun deleteAll() {
        realm.executeTransaction {
            realm.deleteAll()
        }
    }


}