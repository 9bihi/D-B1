package com.deutschb1.ui.translation

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.deutschb1.data.ApiRepository
import com.deutschb1.data.ApiResult
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

data class TranslationLangPair(val displayName: String, val code: String, val emoji: String)

val supportedPairs = listOf(
    TranslationLangPair("Deutsch → English", "de|en", "🇩🇪→🇬🇧"),
    TranslationLangPair("English → Deutsch", "en|de", "🇬🇧→🇩🇪"),
    TranslationLangPair("Deutsch → العربية", "de|ar", "🇩🇪→🇸🇦"),
    TranslationLangPair("Deutsch → Français", "de|fr", "🇩🇪→🇫🇷"),
    TranslationLangPair("Deutsch → Türkçe", "de|tr", "🇩🇪→🇹🇷")
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TranslationScreen(navController: NavController) {
    val scope = rememberCoroutineScope()
    val clipboard = LocalClipboardManager.current
    val context = LocalContext.current

    var inputText by remember { mutableStateOf("") }
    var resultState by remember { mutableStateOf<ApiResult<String>?>(null) }
    var selectedPair by remember { mutableStateOf(supportedPairs[0]) }
    var hasSearched by remember { mutableStateOf(false) }

    val gradientBrush = Brush.verticalGradient(
        colors = listOf(Color(0xFF0A0A0A), Color(0xFF111827))
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(gradientBrush)
            .padding(horizontal = 20.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Spacer(Modifier.height(56.dp))

            // Header
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
                }
                Spacer(Modifier.width(8.dp))
                Column {
                    Text(
                        "Übersetzer",
                        style = MaterialTheme.typography.displaySmall,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        "Google Translate (gtx)",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray
                    )
                }
            }

            // Language Selector
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(top = 8.dp)
            ) {
                items(supportedPairs) { pair ->
                    FilterChip(
                        selected = (pair == selectedPair),
                        onClick = { selectedPair = pair },
                        label = { Text(pair.emoji + " " + pair.displayName, fontSize = 12.sp) }
                    )
                }
            }

            // Input Field
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF1C1C1E).copy(alpha = 0.65f))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    TextField(
                        value = inputText,
                        onValueChange = { if (it.length <= 300) inputText = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(min = 140.dp),
                        placeholder = { Text("Text zum Übersetzen eingeben...", color = Color.Gray) },
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            cursorColor = Color(0xFF5856D6)
                        ),
                        textStyle = MaterialTheme.typography.bodyLarge.copy(fontSize = 18.sp)
                    )
                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterEnd) {
                        Text(
                            text = "${inputText.length}/300",
                            style = MaterialTheme.typography.labelSmall,
                            color = if (inputText.length >= 280) Color(0xFFFF6B6B) else Color.Gray
                        )
                    }
                }
            }

            // Translate button
            Button(
                onClick = {
                    hasSearched = true
                    resultState = ApiResult.Loading
                    scope.launch {
                        resultState = ApiRepository.translate(inputText, selectedPair.code)
                    }
                },
                enabled = inputText.isNotBlank(),
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF5856D6)),
                shape = RoundedCornerShape(14.dp)
            ) {
                Text("Übersetzen", fontWeight = FontWeight.Bold)
            }

            // Result Area
            if (hasSearched) {
                when (val res = resultState) {
                    is ApiResult.Loading -> {
                        com.deutschb1.ui.components.ApiShimmerCard()
                    }
                    is ApiResult.Success -> {
                        ResultContent(
                            text = res.data,
                            onCopy = {
                                clipboard.setText(AnnotatedString(res.data))
                                Toast.makeText(context, "Kopiert!", Toast.LENGTH_SHORT).show()
                            }
                        )
                    }
                    is ApiResult.Error -> {
                        com.deutschb1.ui.components.ApiErrorCard(res.message, onRetry = {
                            scope.launch {
                                resultState = ApiResult.Loading
                                resultState = ApiRepository.translate(inputText, selectedPair.code)
                            }
                        })
                    }
                    null -> {}
                }
            }
            
            Spacer(Modifier.height(100.dp))
        }
    }
}


@Composable
fun ResultContent(text: String, onCopy: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1C1C1E).copy(alpha = 0.85f))
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, Color(0xFF5856D6).copy(alpha = 0.4f), RoundedCornerShape(24.dp))
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "Übersetzung",
                        style = MaterialTheme.typography.labelLarge,
                        color = Color(0xFF8E8CE1),
                        fontWeight = FontWeight.Bold
                    )
                    IconButton(onClick = onCopy) {
                        Icon(Icons.Default.ContentCopy, contentDescription = "Copy", tint = Color.LightGray)
                    }
                }
                Spacer(Modifier.height(8.dp))
                Text(
                    text = text,
                    color = Color.White,
                    style = MaterialTheme.typography.bodyLarge,
                    fontSize = 20.sp,
                    lineHeight = 28.sp
                )
                
                // Backup info placeholder (if ever needed)
                // Spacer(Modifier.height(16.dp))
                // Row(verticalAlignment = Alignment.CenterVertically) {
                //     Icon(Icons.Default.Info, null, tint = Color.Gray, modifier = Modifier.size(14.dp))
                //     Spacer(Modifier.width(4.dp))
                //     Text("Backup service used", color = Color.Gray, fontSize = 11.sp)
                // }
            }
        }
    }
}
