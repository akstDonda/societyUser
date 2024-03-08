package com.nothing.societyuser.Model

import java.util.Date

data class TransactionHistoryModel(
    var date: Date,
    var amount: Int,
    var status: Boolean,
    val id: String
)

public fun createTransactionHistoryModel(date: Date, amount: Int, status: Boolean, id: String): TransactionHistoryModel {
    return TransactionHistoryModel(date, amount, status, id)
}
