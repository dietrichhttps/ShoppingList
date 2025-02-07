package com.example.shoppinglist.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.ViewModelProvider
import com.example.shoppinglist.R
import com.example.shoppinglist.domain.ShopItem
import com.google.android.material.textfield.TextInputLayout

class ShopItemActivity : AppCompatActivity() {

//    private lateinit var viewModel: ShopItemViewModel

//    private lateinit var tilName: TextInputLayout
//    private lateinit var tilCount: TextInputLayout
//    private lateinit var etName: EditText
//    private lateinit var etCount: EditText
//    private lateinit var buttonSave: Button

    private var screenMode = MODE_UNKNOWN
    private var shopItemId = ShopItem.UNDEFINED_ID
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop_item)
        parseIntent()
//        viewModel = ViewModelProvider(this)[ShopItemViewModel::class.java]
//        initViews()
//        addTextChangedListeners()
        launchRightMode()
//        observeViewModel()
    }

    private fun launchRightMode() {
        val fragment = when (screenMode) {
            MODE_EDIT -> ShopItemFragment.newInstanceEditItem(shopItemId)
            MODE_ADD -> ShopItemFragment.newInstanceAddItem()
            else -> throw RuntimeException("Unknown screen mode $screenMode")
        }
        supportFragmentManager.beginTransaction()
            .add(R.id.shop_item_container, fragment)
            .commit()
    }
//
//    private fun launchEditMode() {
//        viewModel.getShopItem(shopItemId)
//        buttonSave.setOnClickListener {
//            val name = etName.text?.toString()
//            val count = etCount.text?.toString()
//            viewModel.editShopItem(name, count)
//        }
//    }
//
//    private fun launchAddMode() {
//        buttonSave.setOnClickListener {
//            val name = etName.text?.toString()
//            val count = etCount.text?.toString()
//            viewModel.addShopItem(name, count)
//        }
//    }
//
//    private fun addTextChangedListeners() {
//        etName.doOnTextChanged() { _, _, _, _ ->
//            viewModel.resetErrorInputName()
//        }
//
//        etCount.doOnTextChanged() { _, _, _, _ ->
//            viewModel.resetErrorInputCount()
//        }
//    }
//
//    private fun observeViewModel() {
//        viewModel.shouldCloseScreen.observe(this) {
//            finish()
//        }
//
//        viewModel.shopItem.observe(this) {
//            etName.setText(it.name)
//            etCount.setText(it.count.toString())
//        }
//
//        viewModel.errorInputName.observe(this) {
//            tilName.error = if (it) {
//                getString(R.string.error_input_name)
//            } else {
//                null
//            }
//        }
//
//        viewModel.errorInputCount.observe(this) {
//            tilCount.error = if (it) {
//                getString(R.string.error_input_count)
//            } else {
//                null
//            }
//        }
//    }
//
    private fun parseIntent() {
        if (!intent.hasExtra(EXTRA_SCREEN_MODE)) {
            throw RuntimeException("Param screen mode is absent")
        }
        val mode = intent.getStringExtra(EXTRA_SCREEN_MODE)
        if (mode != MODE_EDIT && mode != MODE_ADD) {
            throw RuntimeException("Unknown screen mode $mode")
        }
        screenMode = mode
        if (screenMode == MODE_EDIT) {
            if (!intent.hasExtra(EXTRA_SHOP_ITEM_ID)) {
                throw RuntimeException("Param shop item id is absent")
            }
            shopItemId = intent.getIntExtra(EXTRA_SHOP_ITEM_ID, ShopItem.UNDEFINED_ID)
        }
    }
//
//    private fun initViews() {
//        tilName = findViewById(R.id.til_name)
//        tilCount = findViewById(R.id.til_count)
//        etName = findViewById(R.id.et_name)
//        etCount = findViewById(R.id.et_count)
//        buttonSave = findViewById(R.id.save_button)
//
//    }

    companion object {

        private const val EXTRA_SCREEN_MODE = "extra_mode"
        private const val EXTRA_SHOP_ITEM_ID = "extra_shop_item_id"
        private const val MODE_ADD = "mode_add"
        private const val MODE_EDIT = "mode_edit"
        private const val MODE_UNKNOWN = ""

        private const val TAG = "ShopItemActivity"


        fun newIntentAddItem(context: Context): Intent {
            val intent = Intent(context, ShopItemActivity::class.java)
            intent.putExtra(EXTRA_SCREEN_MODE, MODE_ADD)
            return intent
        }

        fun newIntentEditItem(context: Context, shopItemId: Int): Intent {
            val intent = Intent(context, ShopItemActivity::class.java)
            intent.putExtra(EXTRA_SHOP_ITEM_ID, shopItemId)
            intent.putExtra(EXTRA_SCREEN_MODE, MODE_EDIT)
            return intent
        }
    }
}