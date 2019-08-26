package com.connect

class EmptyAccountConnect : IAccountConnect {
    override fun isLogin(): Boolean {
        return false
    }

}
