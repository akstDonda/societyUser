package com.nothing.societyuser.Model

class complainModel{
    var type:String = ""
    var title:String = ""
    var description:String = ""
    var imageUrl:String = ""

    constructor(type: String, title: String, description: String, imageUrl: String) {
        this.type = type
        this.title = title
        this.description = description
        this.imageUrl = imageUrl
    }
    constructor()
}
