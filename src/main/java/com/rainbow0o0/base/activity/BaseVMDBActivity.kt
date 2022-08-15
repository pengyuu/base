package com.rainbow0o0.base.activity

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.rainbow0o0.base.ext.getVmClazz
import com.rainbow0o0.base.ext.inflateBindingWithGeneric
import com.rainbow0o0.base.ext.util.logD
import com.rainbow0o0.base.network.manager.NetState
import com.rainbow0o0.base.network.manager.NetworkStateManager
import com.rainbow0o0.base.vm.BaseVM

/**
 * Author : pengyu
 * Date   : 2022/7/29
 * Desc   :
 */
abstract class BaseVMDBActivity<VM : BaseVM, DB : ViewDataBinding> : AppCompatActivity() {

    lateinit var mViewModel: VM
    lateinit var mDatabind: DB

    abstract fun initView(savedInstanceState: Bundle?)

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 设置竖屏显示，防止横竖屏切换时activity和fragment重新创建导致一些空指针异常
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        setContentView(initDataBind())
        init(savedInstanceState)
    }

    open fun init(savedInstanceState: Bundle?) {
        mViewModel = createViewModel()
        registerUiChange()
        initView(savedInstanceState)
        createObserver()
        NetworkStateManager.instance.mNetworkStateCallback.observeSticky(this) {
            onNetworkStateChanged(it)
        }
    }

    abstract fun showLoading(message: String = "...")

    abstract fun dismissLoading()

    /**
     * 网络变化监听 子类重写
     */
    open fun onNetworkStateChanged(netState: NetState) {}

    /**
     * 创建viewModel
     */
    private fun createViewModel(): VM {
        return ViewModelProvider(this)[getVmClazz(this)]
    }

    /**
     * 创建LiveData数据观察者
     */
    abstract fun createObserver()

    /**
     * 注册UI 事件
     */
    private fun registerUiChange() {
        //显示弹窗
        mViewModel.loadingChange.showDialog.observeSticky(this) {
            showLoading(it)
        }
        //关闭弹窗
        mViewModel.loadingChange.dismissDialog.observeSticky(this) {
            "取消弹窗".logD()
            dismissLoading()
        }
    }

    /**
     * 将非该Activity绑定的ViewModel添加 loading回调 防止出现请求时不显示 loading 弹窗bug
     * @param viewModels Array<out BaseViewModel>
     */
    protected fun addLoadingObserve(vararg viewModels: BaseVM) {
        viewModels.forEach { viewModel ->
            //显示弹窗
            viewModel.loadingChange.showDialog.observeSticky(this) {
                showLoading(it)
            }
            //关闭弹窗
            viewModel.loadingChange.dismissDialog.observeSticky(this) {
                dismissLoading()
            }
        }
    }

    /**
     * 供子类BaseVmDbActivity 初始化Databinding操作
     */
    open fun initDataBind(): View? {
        mDatabind = inflateBindingWithGeneric(layoutInflater)
        return mDatabind.root
    }
}