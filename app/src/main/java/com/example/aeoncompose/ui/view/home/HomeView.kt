package com.example.aeoncompose.ui.view.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import com.example.aeoncompose.R

@Composable
fun HomeScreen(navHostController: NavHostController) {

    Column {
        ConstraintLayout(modifier = Modifier) {
            val (imgAvatar, vDivider, tvName, tvPoint) = createRefs()

            Image(
                painter = painterResource(id = R.drawable.logo_member),
                contentDescription = null,
                modifier = Modifier
                    .background(Color.Transparent, CircleShape)
                    .size(50.dp)
                    .constrainAs(imgAvatar) {
                        centerVerticallyTo(parent)
                        start.linkTo(parent.start)
                    }
            )

            Divider(modifier = Modifier
                .fillMaxWidth()
                .constrainAs(vDivider) {
                    centerTo(parent)
                })

            Text(text = "Name", modifier = Modifier.constrainAs(tvName) {
                start.linkTo(imgAvatar.end)
                bottom.linkTo(vDivider.top)
            })

            Text(text = "6 points", modifier = Modifier.constrainAs(tvPoint) {
                start.linkTo(imgAvatar.end)
                top.linkTo(vDivider.bottom)
            })
        }

        LazyColumn {

        }
    }
}