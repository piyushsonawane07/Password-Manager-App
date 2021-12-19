package com.example.passwordmanager

data class DataModel(
    val name: String? = null,
) {
    var title: String? = null
    var username: String? = null
    var password: String? = null
    var masterPass: String? = null
    var id: String? =
        null //this id is the firebase node id that is generated while storing the password

    //for adding Password
    constructor(name: String, title: String, username: String, password: String, id: String) : this(
        name
    ) {
        this.title = title
        this.username = username
        this.password = password
        this.id = id
    }

    //for adding masterPassword
    constructor(name: String, masterPass: String) : this(name) {
        this.masterPass = masterPass
    }

}