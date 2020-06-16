package app.ido.umi.Limit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_explanation.*

class ExplanationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_explanation)



        //タイトル、アイコン表示
        supportActionBar?.title = "ホーム"
        getSupportActionBar()?.setDisplayShowHomeEnabled(true)
        getSupportActionBar()?.setIcon(R.mipmap.ic_launcher_foreground)

        /// フラグメントのリストを作成
        val fragmentList = arrayListOf<Fragment>(
           ExplanationFragment1(),
            BlankFragment(),
            BlankFragment3(),
            BlankFragment4()
        )

        /// adapterのインスタンス生成
        val adapter = SamplePagerAdapter(supportFragmentManager, fragmentList)
        /// adapterをセット
        viewPager.adapter = adapter



        //戻る矢印表示
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
        //MainActivityに戻る
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }
}
