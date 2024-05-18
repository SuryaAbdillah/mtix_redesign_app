package com.example.mtix

import android.graphics.BlurMaskFilter
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.HorizontalAlignmentLine
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mtix.data.Movie
import com.example.mtix.data.movies
import com.example.mtix.ui.theme.MtixTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MtixTheme {
                window.statusBarColor = getColor(R.color.darkgreen)
                window.navigationBarColor = getColor(R.color.appnav)

                MainApp()
            }
        }
    }
}

enum class Screen {
    Load, Login, Home, Detail
}

@Composable
fun MainApp() {
    var currentScreen by remember { mutableStateOf(Screen.Load) }

    LaunchedEffect(Unit) {
        delay(3000)
        currentScreen = Screen.Login
    }

    Crossfade(targetState = currentScreen) { screen ->
        when (screen) {
            Screen.Load -> LoadScreen()
            Screen.Login -> LoginScreen(onLoginSuccess = { currentScreen = Screen.Home })
            Screen.Home -> HomeScreen { currentScreen = Screen.Detail }
            Screen.Detail -> DetailScreen(navigateBack = { currentScreen = Screen.Home }) // Pass navigateBack callback
        }
    }
}

@Composable
fun LoadScreen() {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val padding = screenWidth * 0.2F

    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = colorResource(id = R.color.lightgreen)
    ) {
        Image(
            modifier = Modifier
                .size(dimensionResource(id = R.dimen.image_size))
                .padding(padding),
            painter = painterResource(id = R.drawable.logoxxi),
            contentDescription = stringResource(id = R.string.company_name)
        )
    }
}

@Preview
@Composable
fun LoadScreenPreview() {
    LoadScreen()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(onLoginSuccess: () -> Unit) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val logoSize = screenWidth * 0.6F

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = colorResource(id = R.color.lightgreen)
    ) {
        Column(
            modifier = Modifier
                .width(logoSize)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier.width(logoSize),
                painter = painterResource(id = R.drawable.logoxxi),
                contentDescription = stringResource(id = R.string.company_name)
            )
            Spacer(modifier = Modifier.height(50.dp))
            Text(
                text = "Email",
                style = MaterialTheme.typography.titleSmall,
                color = colorResource(id = R.color.purewhite),
                modifier = Modifier.width(logoSize)
            )
            Spacer(modifier = Modifier.height(5.dp))
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                shape = RoundedCornerShape(10.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = colorResource(id = R.color.purewhite),
                    unfocusedBorderColor = colorResource(id = R.color.gold)
                ),
                placeholder = {
                    Text(
                        text = "example@gmail.com",
                        style = MaterialTheme.typography.titleSmall,
                        color = colorResource(id = R.color.placeholder)
                    )
                },
                modifier = Modifier
                    .clip(shape = RoundedCornerShape(10.dp))
                    .background(color = colorResource(id = R.color.purewhite))
                    .height(50.dp)
            )
            Spacer(modifier = Modifier.height(15.dp))
            Text(
                text = "Password",
                style = MaterialTheme.typography.titleSmall,
                color = colorResource(id = R.color.purewhite),
                modifier = Modifier.width(logoSize)
            )
            Spacer(modifier = Modifier.height(5.dp))
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                shape = RoundedCornerShape(10.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = colorResource(id = R.color.purewhite),
                    unfocusedBorderColor = colorResource(id = R.color.gold)
                ),
                visualTransformation = PasswordVisualTransformation(),
                placeholder = {
                    Text(
                        text = "******",
                        style = MaterialTheme.typography.titleSmall,
                        color = colorResource(id = R.color.placeholder)
                    )
                },
                modifier = Modifier
                    .clip(shape = RoundedCornerShape(10.dp))
                    .background(color = colorResource(id = R.color.purewhite))
                    .height(50.dp)
            )
            Spacer(modifier = Modifier.height(30.dp))
            Button(
                onClick = { onLoginSuccess() },
                modifier = Modifier.height(35.dp),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.gold)
                ),
                border = BorderStroke(width = 0.5.dp, color = colorResource(id = R.color.darkgold))
            ) {
                Text(
                    text = "Login",
                    style = MaterialTheme.typography.titleSmall
                )
            }
        }
    }
}

// REFERENCE: https://www.youtube.com/watch?v=LqvaiNmVIaI
fun Modifier.shadow(
    color: Color = Color.Black,
    borderRadius: Dp = 10.dp,
    blurRadius: Dp = 3.dp,
    offsetY: Dp = 0.dp,
    offsetX: Dp = 0.dp,
    spread: Dp = 0.dp,
    modifier: Modifier = Modifier
) = this.then(
    modifier.drawBehind {
        this.drawIntoCanvas {
            val paint = Paint()
            val frameworkPaint = paint.asFrameworkPaint()
            val spreadPixel = spread.toPx()
            val leftPixel = (0f - spreadPixel) + offsetX.toPx()
            val topPixel = (0f - spreadPixel) + offsetY.toPx()
            val rightPixel = (this.size.width + spreadPixel)
            val bottomPixel = (this.size.height + spreadPixel)

            if (blurRadius != 0.dp) {
                frameworkPaint.maskFilter =
                    (BlurMaskFilter(blurRadius.toPx(), BlurMaskFilter.Blur.NORMAL))
            }

            frameworkPaint.color = color.toArgb()
            it.drawRoundRect(
                left = leftPixel,
                top = topPixel,
                right = rightPixel,
                bottom = bottomPixel,
                radiusX = borderRadius.toPx(),
                radiusY = borderRadius.toPx(),
                paint
            )
        }
    }
)

@Composable
fun HomeScreen(navigateToDetail: () -> Unit) {
    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = colorResource(id = R.color.background)
    ) {
        Column (
            modifier = Modifier
                .fillMaxSize()
        ) {
            AppTopBar()
            LazyVerticalStaggeredGrid(
                columns = StaggeredGridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalItemSpacing = 0.dp
            ) {
                items(movies) { movie ->
                    MovieItem(movie = movie) {
                        navigateToDetail()
                    }
                }
            }
        }
    }
}

@Composable
fun DetailScreen(navigateBack: () -> Unit) {
    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = colorResource(id = R.color.background)
    ) {
        Column (
            modifier = Modifier
                .fillMaxSize()
        ) {
            Row (
                modifier = Modifier.fillMaxWidth()
            ) {
                AppTopBarDetail(navigateBack = navigateBack)
            }
            Row (
                modifier = Modifier
                    .padding(16.dp)
            ) {
                CardMovieDetail()
            }
            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = {},
                    modifier = Modifier
                        .height(35.dp)
                        .width(240.dp)
                        .shadow(spread = 0.1.dp, blurRadius = 20.dp, offsetY = 20.dp),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(id = R.color.searchbg)
                    )
                ) {
                    Icon (
                        painter = painterResource(id = R.drawable.ticket),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )
                    Text(
                        text = "Cari Lokasi Penayangan",
                        style = MaterialTheme.typography.titleSmall,
                        color = colorResource(id = R.color.purewhite),
                        textAlign = TextAlign.Center
                    )
                }
            }
            Row (
                modifier = Modifier
                    .padding(top = 16.dp, bottom = 6.dp, start = 24.dp, end = 24.dp)
            ) {
                Text(
                    text = "SINOPSIS",
                    style = MaterialTheme.typography.headlineMedium,
                    textAlign = TextAlign.Start,
                    color = colorResource(id = R.color.darkgold)
                )
            }
            Row (
                modifier = Modifier
                    .padding(horizontal = 24.dp)
            ) {
                Text (
                    modifier = Modifier,
                    text = stringResource(id = R.string.synopsis),
                    style = MaterialTheme.typography.titleSmall,
                    color = colorResource(id = R.color.graytogold),
                    minLines = 4,
                    maxLines = 10,
                    textAlign = TextAlign.Justify
                )
            }
            Row (
                modifier = Modifier
                    .padding(top = 16.dp, bottom = 2.dp, start = 24.dp, end = 24.dp)
            ) {
                Text(
                    text = "TENTANG FILM",
                    style = MaterialTheme.typography.headlineMedium,
                    textAlign = TextAlign.Start,
                    color = colorResource(id = R.color.darkgold)
                )
            }
            Row (
                modifier = Modifier
                    .padding(top = 4.dp, bottom = 6.dp, start = 24.dp, end = 24.dp)
            ) {
                Text(
                    text = "Pemain",
                    style = MaterialTheme.typography.headlineSmall,
                    textAlign = TextAlign.Start,
                    color = colorResource(id = R.color.darkgold)
                )
            }
            Row (
                modifier = Modifier
                    .padding(top = 2.dp, bottom = 6.dp, start = 24.dp, end = 24.dp)
            ) {
                Text(
                    text = "Putthipong Assaratanakul",
                    style = MaterialTheme.typography.titleSmall,
                    color = colorResource(id = R.color.graytogold),
                    textAlign = TextAlign.Start
                )
            }
        }
    }
}

// function to show header application
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar() {
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .background(color = colorResource(id = R.color.lightgreen))
            .padding(0.dp),
        horizontalArrangement = Arrangement.Center
    ){
        Image(
            modifier = Modifier
                .size(dimensionResource(id = R.dimen.image_size))
                .padding(dimensionResource(id = R.dimen.padding_small)),
            painter = painterResource(id = R.drawable.logoxxi),
            contentDescription = stringResource(id = R.string.company_name)
        )
    }
}

@Composable
fun AppTopBarDetail(navigateBack: () -> Unit) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val paddingHorizontal = screenWidth * 0.05F

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = colorResource(id = R.color.lightgreen))
            .padding(vertical = 0.dp, horizontal = paddingHorizontal)
    ) {
        Image(
            painter = painterResource(id = R.drawable.back_arrow),
            contentDescription = stringResource(id = R.string.back),
            modifier = Modifier
                .size(24.dp)
                .align(Alignment.CenterStart)
                .clickable { navigateBack() }
        )

        Image(
            painter = painterResource(id = R.drawable.logoxxi),
            contentDescription = stringResource(id = R.string.company_name),
            modifier = Modifier
                .size(dimensionResource(id = R.dimen.image_size))
                .padding(dimensionResource(id = R.dimen.padding_small))
                .align(Alignment.Center)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AppTopBarPreview() {
    AppTopBar()
}

// function for create movie card
// content: poster
@Composable
fun CardMovie() {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val cardWidth = screenWidth * 0.397F

    Card(
        modifier = Modifier
            .width(cardWidth)
            .height(270.dp)
            .padding(8.dp)
            .shadow(spread = 0.7.dp, blurRadius = 20.dp, offsetY = 245.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = colorResource(id = R.color.gold))
                .padding(top = 13.dp, bottom = 8.dp, end = 13.dp, start = 13.dp)
                .clip(shape = RoundedCornerShape(20.dp))
        ) {
            Column {
                Image(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(162.dp)
                        .clip(shape = RoundedCornerShape(10.dp)),
                    painter = painterResource(id = R.drawable.poster),
                    contentDescription = stringResource(id = R.string.poster),
                    contentScale = ContentScale.Crop,
                )
                Spacer(modifier = Modifier.height(9.dp))
                Text(
                    text = stringResource(id = R.string.title),
                    style = MaterialTheme.typography.bodySmall,
                    color = colorResource(id = R.color.purewhite),
                    textAlign = TextAlign.Center,
                    lineHeight = 12.sp,
                    minLines = 2,
                    maxLines = 2
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxHeight()
                        ) {
                            Image(
                                modifier = Modifier
                                    .size(12.dp)
                                    .align(Alignment.CenterVertically),
                                painter = painterResource(id = R.drawable.star),
                                contentDescription = stringResource(id = R.string.star)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = stringResource(id = R.string.rate),
                                style = MaterialTheme.typography.labelSmall,
                                textAlign = TextAlign.Center,
                                color = colorResource(id = R.color.purewhite),
                                modifier = Modifier.align(Alignment.CenterVertically)
                            )
                        }
                    }
                    Text(
                        text = stringResource(id = R.string.age),
                        style = MaterialTheme.typography.labelSmall,
                        textAlign = TextAlign.Center,
                        color = colorResource(id = R.color.purewhite)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun CardMoviePreview() {
    CardMovie()
}

@Composable
fun MovieItem(
    movie: Movie,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val cardWidth = screenWidth * 0.397F

    Card(
        modifier = modifier
            .width(cardWidth)
            .height(270.dp)
            .padding(8.dp)
            .shadow(spread = 0.7.dp, blurRadius = 20.dp, offsetY = 245.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = colorResource(id = R.color.gold))
                .padding(top = 13.dp, bottom = 8.dp, end = 13.dp, start = 13.dp)
                .clip(shape = RoundedCornerShape(20.dp))
        ) {
            Column {
                Image(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(162.dp)
                        .clip(shape = RoundedCornerShape(10.dp)),
                    painter = painterResource(id = movie.imagePosterId),
                    contentDescription = stringResource(id = R.string.poster),
                    contentScale = ContentScale.Crop,
                )
                Spacer(modifier = Modifier.height(9.dp))
                Text(
                    text = stringResource(id = movie.title),
                    style = MaterialTheme.typography.bodySmall,
                    color = colorResource(id = R.color.purewhite),
                    textAlign = TextAlign.Center,
                    lineHeight = 12.sp,
                    minLines = 2,
                    maxLines = 2
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxHeight()
                        ) {
                            Image(
                                modifier = Modifier
                                    .size(12.dp)
                                    .align(Alignment.CenterVertically),
                                painter = painterResource(id = R.drawable.star),
                                contentDescription = stringResource(id = R.string.star)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = stringResource(id = movie.rate),
                                style = MaterialTheme.typography.labelSmall,
                                textAlign = TextAlign.Center,
                                color = colorResource(id = R.color.purewhite),
                                modifier = Modifier.align(Alignment.CenterVertically)
                            )
                        }
                    }
                    Text(
                        text = stringResource(id = movie.age),
                        style = MaterialTheme.typography.labelSmall,
                        textAlign = TextAlign.Center,
                        color = colorResource(id = R.color.purewhite)
                    )
                }
            }
        }
    }
}

// function to create card movie inside detail movie page
@Composable
fun CardMovieDetail() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(270.dp)
            .clip(shape = RoundedCornerShape(20.dp))
            .background(color = colorResource(id = R.color.bgdetail))
    ) {
        Column(
        ) {
            CardMovie()
        }
        Spacer(modifier = Modifier.width(10.dp))
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(
                text = stringResource(id = R.string.title),
                style = MaterialTheme.typography.headlineLarge,
                color = colorResource(id = R.color.darkgold),
                minLines = 2,
                maxLines = 4
            )
            Box(
                modifier = Modifier
                    .width(200.dp)
                    .height(1.dp)
                    .background(color = colorResource(id = R.color.purewhite))
                    .clip(shape = RoundedCornerShape(10.dp))
            )
            Text (
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(vertical = 5.dp),
                text = stringResource(id = R.string.synopsis),
                style = MaterialTheme.typography.bodySmall,
                color = colorResource(id = R.color.graytogold),
                minLines = 4,
                maxLines = 10,
                textAlign = TextAlign.Justify
            )
        }
    }
}


@Preview
@Composable
fun CardMovieDetailPreview() {
    CardMovieDetail()
}