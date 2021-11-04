package com.example.aeoncompose.ui.view.login

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.aeoncompose.R
import com.example.aeoncompose.api.RequestState
import com.example.aeoncompose.api.UiState
import com.example.aeoncompose.extensions.onClick
import com.example.aeoncompose.ui.AeonDialog
import com.example.aeoncompose.ui.HomeScreen
import com.example.aeoncompose.ui.KYCScreen
import com.example.aeoncompose.ui.base_view.AeonButtonText
import com.example.aeoncompose.ui.base_view.AeonTextField
import com.example.aeoncompose.ui.base_view.DialogLoading
import com.example.aeoncompose.utils.ScreenUtils

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun LoginView(navHostController: NavHostController, loginViewModel: LoginViewModel = hiltViewModel()) {
    val loginState = loginViewModel.uiStateLogin.value
    val isLoading = loginViewModel.isLoading.value
    val focusManager = LocalFocusManager.current
    var phone by remember { mutableStateOf("0901169215") }
    var password by remember { mutableStateOf("ab123123") }
    val focusRequester = remember { FocusRequester() }

    Box(modifier = Modifier
        .background(MaterialTheme.colors.primary)
        .padding(top = 20.dp)
        .fillMaxSize()
        .onClick {
            focusManager.clearFocus()
        }) {
        Image(
            painter = painterResource(id = R.drawable.background_register), contentDescription = null,
            modifier = Modifier.matchParentSize(),
            contentScale = ContentScale.Crop
        )
        Image(
            painter = painterResource(id = R.drawable.ic_arrow_back), contentDescription = null,
            modifier = Modifier
                .size(65.dp)
                .padding(10.dp)
                .clickable { navHostController.popBackStack() },
            alignment = Alignment.TopStart
        )
        Image(
            painter = painterResource(id = R.drawable.logo_member),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 20.dp),
        )

        Column(
            modifier = Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.Bottom,
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 15.dp, end = 15.dp),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_language_login),
                    modifier = Modifier.size(15.dp),
                    contentDescription = null
                )

                Spacer(modifier = Modifier.width(2.dp))

                Text(
                    text = "Ngôn ngữ",
                    style = MaterialTheme.typography.h1,
                    fontSize = 14.sp,
                    color = Color.White
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Card(
                backgroundColor = MaterialTheme.colors.primary,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 15.dp, end = 15.dp), elevation = 5.dp
            ) {
                Column(modifier = Modifier.padding(10.dp)) {
                    AeonTextField(
                        label = "Số điện thoại",
                        value = phone,
                        onValueChange = {
                            phone = it
                        }, modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                        keyboardActions = KeyboardActions(onNext = { focusRequester.requestFocus() })
                    )
                    AeonTextField(
                        label = "Mật khẩu",
                        value = password,
                        onValueChange = {
                            password = it
                        }, modifier = Modifier
                            .fillMaxWidth()
                            .focusRequester(focusRequester),
                        keyboardActions = KeyboardActions(
                            onDone = { focusManager.clearFocus() },
                        ),
                        inputType = PasswordVisualTransformation()
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    AeonButtonText("Đăng nhập") { loginViewModel.postLogin(phone, password) }
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(
                        text = "Quên mật khẩu",
                        modifier = Modifier
                            .clickable {

                            }
                            .fillMaxWidth(),
                        textAlign = TextAlign.Center, color = Color.White
                    )
                    Spacer(modifier = Modifier.height(3.dp))
                }
            }

            Spacer(modifier = Modifier.height(30.dp))

            Image(
                painter = painterResource(id = R.drawable.footer_login),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(height = (ScreenUtils.screenHeight * 7 / 20).dp)
            )
        }
        AnimatedVisibility(visible = isLoading) {
            DialogLoading()
        }

        when (loginState.state) {
            RequestState.SUCCESS -> {
                LaunchedEffect(key1 = loginState.state, block = {
                    navHostController.navigate(HomeScreen.Home.name) {
                        popUpTo(KYCScreen.getName()) {
                            inclusive = true
                        }
                    }
                })
            }
            RequestState.FAIL -> {
                LaunchedEffect(key1 = loginState.state, block = {
                    navHostController.navigate(AeonDialog.DialogSingle.name + "/${loginState.message}")
                })
            }
        }
        loginViewModel._uiStateLogin.value = UiState(RequestState.NON)
    }
}