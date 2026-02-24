package com.deutschb1.ui.learn

import androidx.compose.animation.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.deutschb1.data.LearnPhrase
import com.deutschb1.data.LearnTheme
import com.deutschb1.data.LearnThemeContent
import com.deutschb1.data.hexToColor
import androidx.compose.foundation.Image

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ThemePhraseListScreen(content: LearnThemeContent, navController: NavController) {
    val themeColor = hexToColor(content.theme.colorHex)
    val darkColor = themeColor.copy(
        red = (themeColor.red * 0.7f).coerceIn(0f, 1f),
        green = (themeColor.green * 0.7f).coerceIn(0f, 1f),
        blue = (themeColor.blue * 0.7f).coerceIn(0f, 1f)
    )

    // Determine if this theme should use enhanced cards
    val isEnhanced = isEnhancedTheme(content.theme)

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        content.theme.displayName,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.headlineMedium
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Zurück")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background)
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { padding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(padding),
            contentPadding = PaddingValues(horizontal = 20.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            // Hero banner (unchanged)
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp)
                        .clip(RoundedCornerShape(20.dp)),
                    contentAlignment = Alignment.CenterStart
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
                                "${content.phrases.size} Phrasen",
                                style = MaterialTheme.typography.labelMedium,
                                color = Color.White.copy(alpha = 0.9f),
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
            }

            // Phrase cards
            itemsIndexed(content.phrases) { index, phrase ->
                PhraseCard(
                    phrase = phrase,
                    index = index + 1,
                    accentColor = themeColor,
                    enhanced = isEnhanced
                )
            }

            item { Spacer(modifier = Modifier.height(80.dp)) }
        }
    }
}

// Helper to identify enhanced categories
fun isEnhancedTheme(theme: LearnTheme): Boolean {
    return when (theme) {
        LearnTheme.SCHREIBEN_TEIL1,
        LearnTheme.SCHREIBEN_TEIL2,
        LearnTheme.SCHREIBEN_TEIL3,
        LearnTheme.KONJUNKTIONEN,
        LearnTheme.SUBJUNKTIONEN,
        LearnTheme.KONJUNKTIONALADVERBIEN,
        LearnTheme.GRAM_PERFEKT,
        LearnTheme.GRAM_PRAETERITUM,
        LearnTheme.GRAM_MODALVERBEN,
        LearnTheme.GRAM_PASSIV,
        LearnTheme.GRAM_KONJUNKTIV_II,
        LearnTheme.GRAM_RELATIVSATZ,
        LearnTheme.GRAM_ADJEKTIVDEKLINATION,
        LearnTheme.GRAM_PRAEPOSITIONEN,
        LearnTheme.GRAM_WORTSTELLUNG,
        LearnTheme.GRAM_INFINITIV_MIT_ZU,
        LearnTheme.GRAM_KOMPARATIV,
        LearnTheme.GRAM_NEGATION,
        LearnTheme.GRAM_REFLEXIVE_VERBEN,
        LearnTheme.GRAM_FRAGEN,
        LearnTheme.GRAM_TRENNBARE_VERBEN,
        LearnTheme.GRAM_GENITIV,
        LearnTheme.GRAM_IMPERATIV,
        LearnTheme.SPRECHEN_B1 -> true
        else -> false
    }
}

@Composable
fun PhraseCard(
    phrase: LearnPhrase,
    index: Int,
    accentColor: Color,
    enhanced: Boolean
) {
    var expanded by remember { mutableStateOf(false) }

    if (enhanced) {
        EnhancedPhraseCard(
            phrase = phrase,
            index = index,
            accentColor = accentColor,
            expanded = expanded,
            onExpandChange = { expanded = !expanded }
        )
    } else {
        SimplePhraseCard(
            phrase = phrase,
            index = index,
            accentColor = accentColor,
            expanded = expanded,
            onExpandChange = { expanded = !expanded }
        )
    }
}

@Composable
fun SimplePhraseCard(
    phrase: LearnPhrase,
    index: Int,
    accentColor: Color,
    expanded: Boolean,
    onExpandChange: () -> Unit
) {
    // Original design for vocabulary (unchanged)
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onExpandChange() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Header with badge
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Number badge
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(accentColor.copy(alpha = 0.15f)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        "$index",
                        fontWeight = FontWeight.Bold,
                        color = accentColor,
                        style = MaterialTheme.typography.labelLarge
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        phrase.german,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        phrase.english,
                        style = MaterialTheme.typography.bodySmall,
                        color = accentColor,
                        fontWeight = FontWeight.Medium
                    )
                }
                Icon(
                    if (expanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            // Expanded content
            if (expanded) {
                Spacer(modifier = Modifier.height(14.dp))
                HorizontalDivider(color = accentColor.copy(alpha = 0.2f))
                Spacer(modifier = Modifier.height(12.dp))

                RichText(
                    text = phrase.exampleSentence,
                    modifier = Modifier.fillMaxWidth(),
                    accentColor = accentColor
                )

                if (phrase.usageTip.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(12.dp))
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = accentColor.copy(alpha = 0.08f)
                        ),
                        elevation = CardDefaults.cardElevation(0.dp)
                    ) {
                        Text(
                            text = "💡 ${phrase.usageTip}",
                            modifier = Modifier.padding(12.dp),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun EnhancedPhraseCard(
    phrase: LearnPhrase,
    index: Int,
    accentColor: Color,
    expanded: Boolean,
    onExpandChange: () -> Unit
) {
    // Enhanced design for grammar, connectors, writing, speaking
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onExpandChange() },
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column {
            // Header with gradient background
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(accentColor, accentColor.copy(alpha = 0.7f)),
                            start = androidx.compose.ui.geometry.Offset(0f, 0f),
                            end = androidx.compose.ui.geometry.Offset(Float.POSITIVE_INFINITY, 0f)
                        )
                    )
                    .padding(16.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        // Larger index badge
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = Color.White.copy(alpha = 0.2f),
                            modifier = Modifier.size(40.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text(
                                    "$index",
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 18.sp
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        Column {
                            Text(
                                phrase.german,
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp
                            )
                            Text(
                                phrase.english,
                                color = Color.White.copy(alpha = 0.9f),
                                fontSize = 14.sp
                            )
                        }
                    }
                    Icon(
                        if (expanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                        contentDescription = null,
                        tint = Color.White
                    )
                }
            }

            // Expanded content
            if (expanded) {
                Column(modifier = Modifier.padding(16.dp)) {
                    // Main content with rich text
                    RichText(
                        text = phrase.exampleSentence,
                        modifier = Modifier.fillMaxWidth(),
                        accentColor = accentColor
                    )

                    // Usage tip in a distinct card
                    if (phrase.usageTip.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(12.dp))
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = accentColor.copy(alpha = 0.08f)
                            ),
                            elevation = CardDefaults.cardElevation(0.dp)
                        ) {
                            Text(
                                text = "💡 ${phrase.usageTip}",
                                modifier = Modifier.padding(12.dp),
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }
        }
    }
}

// RichText remains the same as before
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
                                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
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
                        lineHeight = 22.sp,
                        modifier = Modifier.padding(vertical = 2.dp)
                    )
                }
            }
        }
    }
}