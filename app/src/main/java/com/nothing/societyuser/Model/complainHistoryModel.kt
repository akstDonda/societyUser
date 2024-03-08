package com.nothing.societyuser.Model

import java.util.Date

data class complainHistoryModel(
    var img:Int,
    var type:String,
    var title:String,
    var date:Date,
    var status:String,
    var description:String,
)
