package app.ido.umi.Limit


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import io.realm.Realm
import io.realm.RealmResults
import io.realm.Sort
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


class ShoppingActivity : AppCompatActivity() {
    //realmを開く
    private val realm: Realm by lazy {
        Realm.getDefaultInstance()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shopping)

        //taskListという変数に取得したデータを代入
        val taskList = readAll()

        // タスクリストが空だったときにダミーデータを生成する

        //クリック関連
        val adapter =
            ItemAdapter(this, taskList, object : ItemAdapter.OnItemClickListener {
                override fun onItemClick(item: Item) {
                    //リストを押したら消える
                    Toast.makeText(applicationContext, item.name + "を削除しました", Toast.LENGTH_SHORT)
                        .show()
                    delete(item.id)
                }

                override fun  onChosenItemsClick(seasoning: Item, checked: Boolean) {
                    //checkboxをクリックした時の処理
                    //Toastで表示

                    Toast.makeText(applicationContext, "買い物リストに移行します", Toast.LENGTH_LONG).show()

                    //realm.executeTransactionというブロックを作り、その中でrealmを使ってデータベースの操作をする。
                    //これを書くことでデータベースへの書き込み(データの作成、更新、削除)ができるようになる。
                    realm.executeTransaction {

                            //needPurchaseをtrueにするよ
                            seasoning.needPurchase = checked


                    }
                }

                }, true)



        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        //ActionBarにMainActivityへ戻る矢印をつけます
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }
    //画面終了時にrealm終了
    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }
    fun createDummyData() {
        for (i in 0..10) {
            create(R.drawable.ic_launcher_background, "商品名 $i")
        }
    }


    fun create(imageId: Int, content: String) {

        realm.executeTransaction {
            val seasoning = it.createObject(Item::class.java, UUID.randomUUID().toString())
            seasoning.imageId=imageId
            seasoning.content = content
        }
    }



    //realmの呼び出し
    //メソッド名（readAll)と返り値RealmResults<Item>を指定
    //データの読み取り
    fun readAll(): RealmResults<Item> {

        //realm.where(Item::class.java).equalTo()→needPurchaseがtrueになったらrealmを使ってデータベースの中のItemリストから全部のデータを取り出している
        //データを昇順に並べてくれるぞ
        return realm.where(Item::class.java).equalTo("needPurchase",true).findAll().sort("date", Sort.ASCENDING)
    }

    fun update(id: String, content: String) {
        realm.executeTransaction {
            val seasoning = realm.where(Item::class.java).equalTo("id", id).findFirst()
                ?: return@executeTransaction
            seasoning.content = content
        }
    }

    fun update(seasoning: Item, content: String) {
        realm.executeTransaction {
            seasoning.content = content
        }
    }

    fun delete(id: String) {
        realm.executeTransaction {
            val seasoning = realm.where(Item::class.java).equalTo("id", id).findFirst()
                ?: return@executeTransaction
            seasoning.deleteFromRealm()
        }
    }

    fun delete(seasoning: Item) {
        realm.executeTransaction {
            seasoning.deleteFromRealm()
        }
    }

    fun deleteAll() {
        realm.executeTransaction {
            realm.deleteAll()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
            finish()
            return super.onSupportNavigateUp()
        }



    }