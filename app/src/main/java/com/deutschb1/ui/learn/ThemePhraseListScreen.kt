package com.deutschb1.ui.learn

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.deutschb1.data.LearnPhrase
import com.deutschb1.data.LearnThemeContent
import com.deutschb1.data.hexToColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ThemePhraseListScreen(content: LearnThemeContent, navController: NavController) {
    val themeColor = hexToColor(content.theme.colorHex)
    val darkColor = themeColor.copy(
        red = (themeColor.red * 0.7f).coerceIn(0f, 1f),
        green = (themeColor.green * 0.7f).coerceIn(0f, 1f),
        blue = (themeColor.blue * 0.7f).coerceIn(0f, 1f)
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        content.theme.displayName,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.headlineMedium,
                        color = Color.White
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            "Zurück",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                )
            )
        },
        containerColor = Color.Black
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color.Black),
            contentPadding = PaddingValues(horizontal = 20.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Hero banner
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .border(
                            width = 1.dp,
                            color = Color.White.copy(alpha = 0.15f),
                            shape = RoundedCornerShape(20.dp)
                        )
                ) {
                    if (content.theme.imageRes != null) {
                        Image(
                            painter = painterResource(id = content.theme.imageRes),
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    Brush.verticalGradient(
                                        colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.7f)),
                                        startY = 50f
                                    )
                                )
                        )
                    } else {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Brush.linearGradient(listOf(themeColor, darkColor)))
                        )
                    }

                    Row(
                        modifier = Modifier.padding(20.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(content.theme.emoji, fontSize = 52.sp)
                        Spacer(modifier = Modifier.width(16.dp))
                        Column {
                            Text(
                                content.theme.displayName,
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                style = MaterialTheme.typography.headlineLarge
                            )
                            Text(
                                content.theme.description,
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.White.copy(alpha = 0.85f)
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                "${content.phrases.size} Phrasen • Tippe zum Umdrehen",
                                style = MaterialTheme.typography.labelMedium,
                                color = Color.White.copy(alpha = 0.9f),
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }
            }

            // Flip phrase cards
            itemsIndexed(content.phrases) { index, phrase ->
                FlipPhraseCard(
                    phrase = phrase,
                    index = index + 1,
                    accentColor = themeColor
                )
            }

            item { Spacer(modifier = Modifier.height(80.dp)) }
        }
    }
}

@Composable
fun FlipPhraseCard(
    phrase: LearnPhrase,
    index: Int,
    accentColor: Color
) {
    var flipped by remember { mutableStateOf(false) }

    // Animate rotation from 0 → 180 (flip)
    val rotation by animateFloatAsState(
        targetValue = if (flipped) 180f else 0f,
        animationSpec = tween(durationMillis = 420, easing = FastOutSlowInEasing),
        label = "flip_$index"
    )

    // Determine which face to show based on rotation angle
    val showBack = rotation > 90f

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 110.dp)
            .graphicsLayer {
                rotationY = rotation
                cameraDistance = 12f * density
            }
            .clip(RoundedCornerShape(18.dp))
            .border(
                width = 1.dp,
                color = if (showBack)
                    accentColor.copy(alpha = 0.5f)
                else
                    Color.White.copy(alpha = 0.13f),
                shape = RoundedCornerShape(18.dp)
            )
            .background(
                if (showBack)
                    accentColor.copy(alpha = 0.12f)
                else
                    Color(0xFF1C1C1E).copy(alpha = 0.75f)
            )
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { flipped = !flipped }
    ) {
        if (!showBack) {
            // FRONT: German phrase
            FrontFace(phrase = phrase, index = index, accentColor = accentColor)
        } else {
            // BACK: English + example + tip (counter-rotated so it reads correctly)
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .graphicsLayer { rotationY = 180f }
            ) {
                BackFace(phrase = phrase, accentColor = accentColor)
            }
        }
    }
}

@Composable
private fun FrontFace(phrase: LearnPhrase, index: Int, accentColor: Color) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(18.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Number badge
        Box(
            modifier = Modifier
                .size(38.dp)
                .clip(CircleShape)
                .background(accentColor),
            contentAlignment = Alignment.Center
        ) {
            Text(
                "$index",
                fontWeight = FontWeight.Bold,
                color = Color.White,
                style = MaterialTheme.typography.labelLarge
            )
        }
        Spacer(modifier = Modifier.width(14.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                phrase.german,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.headlineSmall,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(3.dp))
            Text(
                "Tippe zum Umdrehen →",
                style = MaterialTheme.typography.labelSmall,
                color = Color.White.copy(alpha = 0.35f),
                fontSize = 11.sp
            )
        }
    }
}

@Composable
private fun BackFace(phrase: LearnPhrase, accentColor: Color) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(18.dp)
    ) {
        // English translation
        Text(
            phrase.english,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.titleLarge,
            color = accentColor
        )
        Spacer(modifier = Modifier.height(10.dp))
        HorizontalDivider(color = accentColor.copy(alpha = 0.25f))
        Spacer(modifier = Modifier.height(10.dp))

        // Example sentence (rich text)
        RichText(
            text = phrase.exampleSentence,
            modifier = Modifier.fillMaxWidth(),
            accentColor = accentColor
        )

        // Usage tip
        if (phrase.usageTip.isNotEmpty()) {
            Spacer(modifier = Modifier.height(10.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .background(accentColor.copy(alpha = 0.1f))
                    .border(
                        width = 0.8.dp,
                        color = accentColor.copy(alpha = 0.25f),
                        shape = RoundedCornerShape(10.dp)
                    )
                    .padding(10.dp)
            ) {
                Text(
                    "💡 ${phrase.usageTip}",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.White.copy(alpha = 0.8f)
                )
            }
        }

        Spacer(modifier = Modifier.height(6.dp))
        Text(
            "← Tippe zum Zurückdrehen",
            style = MaterialTheme.typography.labelSmall,
            color = Color.White.copy(alpha = 0.3f),
            fontSize = 11.sp,
            modifier = Modifier.align(Alignment.End)
        )
    }
}

@Composable
fun RichText(text: String, modifier: Modifier = Modifier, accentColor: Color) {
    val lines = text.split("\n")
    Column(modifier = modifier) {
        lines.forEach { line ->
            when {
                line.startsWith("**") && line.endsWith("**") -> {
                    val header = line.removeSurrounding("**")
                    Text(
                        text = header,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = accentColor,
                        modifier = Modifier.padding(top = 8.dp, bottom = 4.dp)
                    )
                }
                line.startsWith("•") -> {
                    Row(modifier = Modifier.padding(start = 8.dp, top = 2.dp, bottom = 2.dp)) {
                        Text("•", color = accentColor, fontSize = 14.sp)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = line.drop(1).trim(),
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.White,
                            lineHeight = 20.sp
                        )
                    }
                }
                line.startsWith("→") -> {
                    Text(
                        text = line,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Medium,
                        color = accentColor,
                        modifier = Modifier.padding(start = 8.dp, top = 2.dp, bottom = 2.dp)
                    )
                }
                line.isBlank() -> {
                    Spacer(modifier = Modifier.height(4.dp))
                }
                else -> {
                    val annotatedString = buildAnnotatedString {
                        var remaining = line
                        while (remaining.isNotEmpty()) {
                            val boldStart = remaining.indexOf("**")
                            if (boldStart >= 0) {
                                val boldEnd = remaining.indexOf("**", boldStart + 2)
                                if (boldEnd > boldStart) {
                                    append(remaining.substring(0, boldStart))
                                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, color = Color.White)) {
                                        append(remaining.substring(boldStart + 2, boldEnd))
                                    }
                                    remaining = remaining.substring(boldEnd + 2)
                                } else {
                                    append(remaining)
                                    remaining = ""
                                }
                            } else {
                                append(remaining)
                                remaining = ""
                            }
                        }
                    }
                    Text(
                        text = annotatedString,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White,
                        lineHeight = 22.sp,
                        modifier = Modifier.padding(vertical = 2.dp)
                    )
                }
            }
        }
    }
}