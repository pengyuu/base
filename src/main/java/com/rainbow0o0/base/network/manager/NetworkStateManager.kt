package com.rainbow0o0.base.network.manager

import com.kunminx.architecture.ui.callback.UnPeekLiveData

/**
 * Author : pengyu
 * Date   : 2022/7/29
 * Desc   :
 */
class NetworkStateManager private constructor() {

    val mNetworkStateCallback = UnPeekLiveData<NetState>()

    companion object {
        val instance: NetworkStateManager by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            NetworkStateManager()
        }
    }

}