package com.example.aeoncompose.ui.view.start_kyc

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.aeoncompose.R
import com.example.aeoncompose.ui.navigation.EnumKYCScreen
import com.example.aeoncompose.ui.base_view.AeonButtonText
import com.example.aeoncompose.utils.ScreenUtils
import com.example.aeoncompose.utils.ScreenUtils.rdp


@Composable
fun StartKYC(navHostController: NavHostController) {
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.background_login),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Image(
            painter = painterResource(id = R.drawable.logo_white),
            contentDescription = null,
            modifier = Modifier.height(45.rdp).padding(top = 15.rdp, start = 15.rdp),
            alignment = Alignment.TopStart
        )
        Image(
            painter = painterResource(id = R.drawable.logo_member),
            contentDescription = null,
            modifier = Modifier.align(Alignment.TopEnd).padding(top = 15.rdp, end = 15.rdp),
            alignment = Alignment.TopEnd
        )
        Column(
            modifier = Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.Bottom,
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 15.rdp, end = 15.rdp),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_language_login),
                    modifier = Modifier.size(15.rdp),
                    contentDescription = null
                )

                Spacer(modifier = Modifier.width(2.rdp))

                Text(
                    text = "Ngôn ngữ",
                    style = MaterialTheme.typography.h1,
                    fontSize = 14.sp,
                    color = Color.White
                )
            }
            Spacer(modifier = Modifier.height(10.rdp))
            Card(
                backgroundColor = MaterialTheme.colors.primary,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 15.rdp, end = 15.rdp), elevation = 5.rdp
            ) {
                Column(modifier = Modifier.padding(10.rdp)) {
                    AeonButtonText("Đăng nhập") {
                        navHostController.navigate(EnumKYCScreen.Login.name) {
                        }
                    }
                    Spacer(modifier = Modifier.height(5.rdp))
                    AeonButtonText("Đăng ký thành viên mới") {

                    }
                    Spacer(modifier = Modifier.height(5.rdp))
                    AeonButtonText("Bạn đã có thẻ thành viên") {

                    }
                }
            }

            Spacer(modifier = Modifier.height(ScreenUtils.screenHeight.rdp /10))

            Image(
                painter = painterResource(id = R.drawable.footer_login),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(height = (ScreenUtils.screenHeight * 7 / 20).rdp)
            )
        }
    }
}