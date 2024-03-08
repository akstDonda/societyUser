package com.nothing.societyuser

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.nothing.societyuser.databinding.ActivityWalletBinding

class WalletActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWalletBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWalletBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //TODO: Show Total amount to firebase

        //add money editText
        var editTextTAddWalletMoney=binding.editTextTAddWalletMoney.text.toString().trim()
//        var edtAddMoneyWallet = editTextTAddWalletMoney.toIntOrNull() ?: 0



        //Intent for Pay Button
        binding.payBtnWallet.setOnClickListener(){
            intentFun(PayAdmin::class.java)
        }

        //TODO: Intent for history button
        binding.historyBtnWallet.setOnClickListener(){
            var intent = Intent(this, TransactionHistory::class.java)
            startActivity(intent)

        }

        //PromoCode Intent
        binding.ll.setOnClickListener(){
            showDialog("Temporary not working")
        }

        //Add money via button 100,200,500
        //100
        binding.add100BtnWallet.setOnClickListener(){
            amountIncrease(100)
        }
        //200
        binding.add200BtnWallet.setOnClickListener(){
            amountIncrease(200)
        }
        //500
        binding.add500BtnWallet.setOnClickListener(){
            amountIncrease(500)
        }

        //addMoneyToWallet  button
        binding.addBtnWallet.setOnClickListener(){
            //TODO:check edittext is not empty
            var intent = Intent(this@WalletActivity, DabitCardActivity::class.java)
            intent.putExtra("amount", binding.editTextTAddWalletMoney.text.toString())
            startActivity(intent)
        }

        //Go For Payment
        binding.FastPayBtnWallet.setOnClickListener(){
            intentFun(PayAdmin::class.java)
        }

    }

    //intent
    fun intentFun(destination : Class<*>){
        var intent = Intent(this, destination)
        startActivity(intent)

    }

    //toast
    fun toastFun(message : String) {
        var toast = Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    fun amountIncrease(addAmount:Int){
        val getAmountToEditText = binding.editTextTAddWalletMoney.text.toString().trim()
            // If the editText is empty, default to 0
            var amountAsInteger: Int = if (getAmountToEditText.isNotEmpty()) getAmountToEditText.toInt() else 0
            var newAmount: Int = amountAsInteger + addAmount
            binding.editTextTAddWalletMoney.text.clear()
            binding.editTextTAddWalletMoney.setText(newAmount.toString())
    }

    //show dialog box function
    private fun showDialog(message: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("!!!Information!!!")
        builder.setMessage(message)
        builder.setPositiveButton("Done") { dialog, which ->
            // Handle the OK button click if needed
            dialog.dismiss()
        }
        builder.setCancelable(false)

        val dialog = builder.create()
        dialog.show()
    }
}