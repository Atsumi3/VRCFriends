package jp.bizen.vrcfriends.android.extensions

import android.view.ViewGroup
import androidx.annotation.MainThread
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

// https://qiita.com/kuluna/items/2207ee5706c4fe7e87d3

/**
 * DataBindingを遅延初期化します。
 * このDelegateを使うにはAppCompatActivityのコンストラクタにレイアウトIDを指定ください。
 *
 * ex:
 * ```
 * class MainActivity : AppCompatActivity(R.layout.activity_main) {
 *   private val binding: ActivityMainBinding by dataBinding()
 * }
 * ```
 */
@MainThread
fun <V : ViewDataBinding> AppCompatActivity.dataBinding(): Lazy<V> {
    return object : Lazy<V> {
        private var holder: V? = null

        override val value: V
            get() = holder ?: run {
                val binding = DataBindingUtil.bind<V>(findViewById<ViewGroup>(android.R.id.content)[0])!!
                holder = binding
                binding
            }

        override fun isInitialized(): Boolean = holder != null
    }
}

/**
 * DataBindingを遅延初期化します。
 * このDelegateを使うにはFragmentのコンストラクタにレイアウトIDを指定ください。
 *
 * ex:
 * ```
 * class MainFragment : Fragment(R.layout.fragment_main) {
 *   private val binding: FragmentMainBinding by dataBinding()
 * }
 */
fun <V : ViewDataBinding> Fragment.dataBinding(): ReadOnlyProperty<Fragment, V> {
    return object : ReadOnlyProperty<Fragment, V> {
        private var holder: V? = null

        override fun getValue(thisRef: Fragment, property: KProperty<*>): V {
            return holder ?: DataBindingUtil.bind<V>(view!!)!!.also {
                holder = it
                // ライフサイクルの終了時にViewを破棄
                viewLifecycleOwner.lifecycle.addObserver(object : LifecycleObserver {
                    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
                    fun onDestroyView() {
                        viewLifecycleOwner.lifecycle.removeObserver(this)
                        holder = null
                    }
                })
            }
        }
    }
}