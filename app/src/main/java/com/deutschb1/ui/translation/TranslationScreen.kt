package com.deutschb1.ui.translation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.deutschb1.data.ApiRepository
import com.deutschb1.data.ApiResult
import kotlinx.coroutines.launch
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext

@Composable
fun TranslationScreen(navController: NavController) {
    val scope = rememberCoroutineScope()
    val clipboard = LocalClipboardManager.current
    val context = LocalContext.current

    var inputText by remember { mutableStateOf("") }
    var outputText by remember { mutableStateOf("") }
    var sourceIsGerman by remember { mutableStateOf(true) }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    val gradientBrush = Brush.verticalGradient(
        colors = listOf(Color(0xFF0A0A0A), Color(0xFF111827))
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = gradientBrush)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Spacer(Modifier.height(56.dp))

            // Back + Header
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
                }
                Spacer(Modifier.width(8.dp))
                Column {
                    Text(
                        "Translation",
                        style = MaterialTheme.typography.displaySmall,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        "Deutsch ↔ English · LibreTranslate",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray
                    )
                }
            }

            // Language selector row
            GlassCard {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    LanguagePill(
                        flag = "🇩🇪",
                        label = "Deutsch",
                        isActive = sourceIsGerman
                    )

                    IconButton(
                        onClick = {
                            sourceIsGerman = !sourceIsGerman
                            // swap input/output
                            val tmp = inputText
                            inputText = outputText
                            outputText = tmp
                        },
                        modifier = Modifier
                            .size(44.dp)
                            .clip(CircleShape)
                            .background(
                                Brush.linearGradient(
                                    listOf(Color(0xFF5856D6), Color(0xFF8E8CE1))
                                )
                            )
                    ) {
                        Icon(
                            Icons.Default.SwapHoriz,
                            contentDescription = "Swap languages",
                            tint = Color.White
                        )
                    }

                    LanguagePill(
                        flag = "🇬🇧",
                        label = "English",
                        isActive = !sourceIsGerman
                    )
                }
            }

            // Input field
            GlassCard {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        text = if (sourceIsGerman) "Deutsch eingeben" else "Enter English",
                        style = MaterialTheme.typography.labelMedium,
                        color = Color.White.copy(alpha = 0.5f)
                    )
                    Spacer(Modifier.height(8.dp))
                    TextField(
                        value = inputText,
                        onValueChange = {
                            inputText = it
                            outputText = ""
                            errorMessage = null
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(min = 120.dp),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            cursorColor = Color(0xFF5856D6)
                        ),
                        placeholder = {
                            Text(
                                if (sourceIsGerman) "z. B. Guten Morgen …" else "e.g. Good morning …",
                                color = Color.Gray
                            )
                        },
                        textStyle = MaterialTheme.typography.bodyLarge.copy(
                            color = Color.White,
                            fontSize = 18.sp
                        ),
                        maxLines = 6
                    )
                }
            }

            // Translate button
            Button(
                onClick = {
                    if (inputText.isBlank()) return@Button
                    scope.launch {
                        isLoading = true
                        errorMessage = null
                        outputText = ""
                        val src = if (sourceIsGerman) "de" else "en"
                        val tgt = if (sourceIsGerman) "en" else "de"
                        val result = ApiRepository.translateText(inputText.trim(), src, tgt)
                        when (result) {
                            is ApiResult.Success -> {
                                outputText = result.data
                                errorMessage = null
                            }
                            is ApiResult.Error -> {
                                errorMessage = "Translation failed: ${result.message}"
                            }
                            else -> {}
                        }
                        isLoading = false
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF5856D6)
                ),
                enabled = inputText.isNotBlank() && !isLoading
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(22.dp),
                        color = Color.White,
                        strokeWidth = 2.dp
                    )
                } else {
                    Text("Übersetzen", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                }
            }

            // Error
            errorMessage?.let { err ->
                GlassCard {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("⚠️", fontSize = 20.sp)
                        Spacer(Modifier.width(12.dp))
                        Text(err, color = Color(0xFFFF6B6B), style = MaterialTheme.typography.bodyMedium)
                    }
                }
            }

            // Output
            AnimatedVisibility(
                visible = outputText.isNotBlank(),
                enter = fadeIn(tween(300)),
                exit = fadeOut(tween(200))
            ) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFF1C1C1E).copy(alpha = 0.8f)
                    ),
                    elevation = CardDefaults.cardElevation(8.dp)
                ) {
                    Box(
                        Modifier
                            .fillMaxWidth()
                            .border(1.dp, Color(0xFF5856D6).copy(alpha = 0.5f), RoundedCornerShape(20.dp))
                    ) {
                        Column(modifier = Modifier.padding(20.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    if (sourceIsGerman) "English" else "Deutsch",
                                    style = MaterialTheme.typography.labelMedium,
                                    color = Color(0xFF8E8CE1)
                                )
                                IconButton(
                                    onClick = {
                                        clipboard.setText(AnnotatedString(outputText))
                                        Toast.makeText(context, "Kopiert!", Toast.LENGTH_SHORT).show()
                                    },
                                    modifier = Modifier.size(32.dp)
                                ) {
                                    Icon(
                                        Icons.Default.ContentCopy,
                                        contentDescription = "Copy",
                                        tint = Color.White.copy(alpha = 0.5f),
                                        modifier = Modifier.size(18.dp)
                                    )
                                }
                            }
                            Spacer(Modifier.height(10.dp))
                            Text(
                                outputText,
                                color = Color.White,
                                style = MaterialTheme.typography.bodyLarge,
                                fontSize = 20.sp,
                                lineHeight = 28.sp
                            )
                        }
                    }
                }
            }

            Spacer(Modifier.height(80.dp))
        }
    }
}

@Composable
private fun GlassCard(content: @Composable () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF1C1C1E).copy(alpha = 0.65f)
        ),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Box(
            Modifier
                .fillMaxWidth()
                .border(1.dp, Color.White.copy(alpha = 0.12f), RoundedCornerShape(20.dp))
        ) {
            content()
        }
    }
}

@Composable
private fun LanguagePill(flag: String, label: String, isActive: Boolean) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(
                if (isActive) Color(0xFF5856D6).copy(alpha = 0.25f)
                else Color.Transparent
            )
            .border(
                width = 1.dp,
                color = if (isActive) Color(0xFF8E8CE1) else Color.White.copy(alpha = 0.15f),
                shape = RoundedCornerShape(12.dp)
            )
            .padding(horizontal = 20.dp, vertical = 10.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(flag, fontSize = 22.sp)
            Spacer(Modifier.width(8.dp))
            Text(
                label,
                color = if (isActive) Color.White else Color.Gray,
                fontWeight = if (isActive) FontWeight.SemiBold else FontWeight.Normal,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
