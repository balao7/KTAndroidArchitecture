package com.sysdata.kt.ktandroidarchitecture.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.viewModelScope
import com.sysdata.kt.ktandroidarchitecture.R
import com.sysdata.kt.ktandroidarchitecture.repository.model.UIUserLogged
import com.sysdata.kt.ktandroidarchitecture.usecase.LoginActionParams
import com.sysdata.kt.ktandroidarchitecture.viewmodel.LoginViewModel
import it.sysdata.ktandroidarchitecturecore.exception.Failure
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : FragmentActivity(), View.OnClickListener, TextWatcher {

    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.actionLogin.observe(this, ::onUserLoggged)
        viewModel.actionLogin.observeFailure(this, ::onLoginFailed)

        setContentView(R.layout.activity_login)
        loginBtn.setOnClickListener(this)
        loginBtn.isEnabled = validateForm()

        usernameValue.addTextChangedListener(this)
        passwordValue.addTextChangedListener(this)
    }


    private fun onLoginFailed(failure: Failure?) {
        Toast.makeText(this, "failure : ${failure.toString()}", Toast.LENGTH_SHORT).show()
    }

    private fun onUserLoggged(userLogged: UIUserLogged?) {
        Toast.makeText(this, "user : ${userLogged?.email}", Toast.LENGTH_SHORT).show()
    }


    override fun onClick(p0: View?) {
        viewModel.login(usernameValue.text.toString(),passwordValue.text.toString())
    }

    fun validateForm(): Boolean {
        return usernameValue.text.isNotEmpty() && passwordValue.text.isNotEmpty() && usernameValue.text.isNotBlank() && passwordValue.text.isNotBlank()
    }

    override fun afterTextChanged(p0: Editable?) {
        loginBtn.isEnabled = validateForm()
    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

}