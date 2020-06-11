package app.ido.umi.hozonactivity


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import io.realm.OrderedRealmCollection
import io.realm.RealmRecyclerViewAdapter
import kotlinx.android.synthetic.main.list_item.view.*

class TaskAdapter(
    private val context: Context,
    private var taskList: OrderedRealmCollection<Item>?,
    private var listener: OnItemClickListener,
    private val autoUpdate: Boolean
) :

//TaskAdapterクラスにItem, TaskAdapterクラスを継承する
//autoUpdateというパラメータ（変数の関係）trueにして渡すと、DBを更新した際にRecyclerViewを自動で更新してくれる
    RealmRecyclerViewAdapter<Item, TaskAdapter.TaskViewHolder>(taskList, autoUpdate) {

    //リストの要素数を返すメソッド
    //RecyclerViewで表示するアイテムの個数
    override fun getItemCount(): Int = taskList?.size ?: 0

    //Layoutの画像や文字を設定する
    //onBindViewHolderの中にItemのposition(番目)の要素をViewHolderに表示する
    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task: Item = taskList?.get(position) ?: return

        holder.container.setOnClickListener{
            listener.onItemClick(task)
        }
        //imageViewをString型にする・・・これ？？

        holder.imageView.load(task.imageUri)

        holder.contentTextView.text = task.name
        holder.dateTextView.text =task.date
        //checkboxの処理
        holder.check.setOnCheckedChangeListener { _, isChecked ->
            listener.onChosenItemsClick(task, isChecked)
        }
    }

    //Layoutを設定する
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): TaskViewHolder {
        val v = LayoutInflater.from(context).inflate(R.layout.list_item, viewGroup, false)
        return TaskViewHolder(v)
    }
    //ViewHolderのクラスを定義して、idを関連付ける
    //ViewHolderとは複数のViewを保持するクラスのことで、Adapterを使うときに必要!
    //idをもとにレイアウトを使う準備をしている
    class TaskViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val container : LinearLayout = view.container
        val imageView:ImageView =view.imageView2
        val contentTextView: TextView = view.contentTextView
        val dateTextView: TextView = view.dateTextView
        val check:CheckBox = view.check
    }

    //RecyclerViewでクリックイベントを実装する
    interface OnItemClickListener {
        //Itemクリック
        fun onItemClick(item: Item)
        //checkboxクリック
        fun onChosenItemsClick(item: Item, checked: Boolean)
    }

}


