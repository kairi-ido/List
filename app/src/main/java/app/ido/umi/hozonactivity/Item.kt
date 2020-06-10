package app.ido.umi.hozonactivity

import android.media.Image
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.*


//データの要素となる変数（プロパティ）を定義
open class Item(
    //@Primarykey（アノテーション：注釈）をつけると一意なプロパティを定義することができる
    @PrimaryKey open var id: String = UUID.randomUUID().toString(),
    open var imageId: Int = 0 ,
    open var content: String = "",
    open var name:String="",
    open var date: String ="",
    open var needPurchase:Boolean=false

//RealmObject():realmで保存できる型にする
) : RealmObject()