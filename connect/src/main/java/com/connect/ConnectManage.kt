package com.connect

val connectManage: ConnectManage by lazy {
    ConnectManage()
}

class ConnectManage {
    private var iAccountConnect: IAccountConnect? = null
    public fun getAccountConnect(): IAccountConnect {
        if (iAccountConnect == null) {
            iAccountConnect = EmptyAccountConnect()
        }
        return iAccountConnect!!
    }

    public fun setAccountConnect(iAccountConnect: IAccountConnect) {
        this.iAccountConnect = iAccountConnect
    }

}
