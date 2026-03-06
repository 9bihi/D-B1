package com.deutschb1.ui.learn

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.deutschb1.data.GeschichtenData
import com.deutschb1.data.MultipleChoiceQuestion
import com.deutschb1.data.db.DatabaseProvider
import com.deutschb1.data.db.entities.SavedWord
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GeschichteReaderScreen(storyId: String, navController: NavController) {
    val story = GeschichtenData.stories.find { it.id == storyId } ?: return
    var selectedWordHint by remember { mutableStateOf<GeschichtenData.VocabHint?>(null) }
    var showQuiz by remember { mutableStateOf(false) }
    var quizFinished by remember { mutableStateOf(false) }
    var score by remember { mutableStateOf(0) }

    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val db = DatabaseProvider.getDatabase(context)
    val savedWordDao = db.savedWordDao()

    val sheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(false) }

    val annotatedText = buildAnnotatedString {
        val words = story.bodyText.split(" ")
        words.forEach { word ->
            val cleanWord = word.trim(',', '.', '!', '?', '(', ')', '"', '„', '“')
            val hint = story.vocabHints.find { it.word.equals(cleanWord, ignoreCase = true) }
            
            if (hint != null) {
                pushStringAnnotation(tag = "HINT", annotation = hint.word)
                withStyle(style = SpanStyle(color = Color(0xFF667EEA), fontWeight = FontWeight.Bold)) {
                    append(word)
                }
                pop()
            } else {
                append(word)
            }
            append(" ")
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(story.title, color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        },
        containerColor = Color.Black
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(20.dp)
        ) {
            if (!showQuiz) {
                Text(
                    text = story.level,
                    style = MaterialTheme.typography.labelLarge,
                    color = if (story.level == "B1") Color(0xFF667EEA) else Color(0xFF4CAF50),
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                ClickableText(
                    text = annotatedText,
                    style = TextStyle(
                        color = Color.White.copy(alpha = 0.9f),
                        fontSize = 20.sp,
                        lineHeight = 32.sp
                    ),
                    onClick = { offset ->
                        annotatedText.getStringAnnotations(tag = "HINT", start = offset, end = offset)
                            .firstOrNull()?.let { annotation ->
                                selectedWordHint = story.vocabHints.find { it.word == annotation.item }
                                showBottomSheet = true
                            }
                    }
                )

                Spacer(modifier = Modifier.height(32.dp))

                Button(
                    onClick = { showQuiz = true },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF667EEA)),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Zum Quiz gehen", color = Color.White, fontWeight = FontWeight.Bold)
                }
                
                Spacer(modifier = Modifier.height(80.dp))
            } else {
                if (!quizFinished) {
                    StoryQuiz(
                        questions = story.questions,
                        onFinished = { finalScore ->
                            score = finalScore
                            quizFinished = true
                        }
                    )
                } else {
                    QuizResult(
                        score = score,
                        total = story.questions.size,
                        onBack = { navController.popBackStack() }
                    )
                }
            }
        }
    }

    if (showBottomSheet && selectedWordHint != null) {
        ModalBottomSheet(
            onDismissRequest = { showBottomSheet = false },
            sheetState = sheetState,
            containerColor = Color(0xFF1C1C1E)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 32.dp)
            ) {
                Text(
                    text = selectedWordHint!!.word,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = selectedWordHint!!.definition,
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color(0xFF667EEA)
                )
                Spacer(modifier = Modifier.height(24.dp))
                Button(
                    onClick = {
                        scope.launch {
                            savedWordDao.saveWord(
                                SavedWord(
                                    word = selectedWordHint!!.word,
                                    translation = selectedWordHint!!.definition,
                                    example = story.bodyText.substring(
                                        maxOf(0, story.bodyText.indexOf(selectedWordHint!!.word) - 30),
                                        minOf(story.bodyText.length, story.bodyText.indexOf(selectedWordHint!!.word) + selectedWordHint!!.word.length + 30)
                                    ).trim() + "..."
                                )
                            )
                            Toast.makeText(context, "Gespeichert!", Toast.LENGTH_SHORT).show()
                            showBottomSheet = false
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(Icons.Default.FavoriteBorder, contentDescription = null, tint = Color.Black)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Zu Word Vault hinzufügen", color = Color.Black, fontWeight = FontWeight.Bold)
                }
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}

@Composable
fun StoryQuiz(questions: List<MultipleChoiceQuestion>, onFinished: (Int) -> Unit) {
    var currentQuestionIndex by remember { mutableStateOf(0) }
    var score by remember { mutableStateOf(0) }
    var selectedOption by remember { mutableStateOf<Int?>(null) }
    var showFeedback by remember { mutableStateOf(false) }

    val question = questions[currentQuestionIndex]

    Column {
        Text(
            "Frage ${currentQuestionIndex + 1} von ${questions.size}",
            style = MaterialTheme.typography.labelLarge,
            color = Color.Gray
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            question.text,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
        Spacer(modifier = Modifier.height(24.dp))

        question.options.forEachIndexed { index, option ->
            val isSelected = selectedOption == index
            val isCorrect = index == question.correctIndex
            
            val borderColor = when {
                showFeedback && isCorrect -> Color.Green
                showFeedback && isSelected && !isCorrect -> Color.Red
                isSelected -> Color(0xFF667EEA)
                else -> Color.White.copy(alpha = 0.1f)
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
                    .clickable(enabled = !showFeedback) { selectedOption = index },
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF1C1C1E))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, borderColor, RoundedCornerShape(12.dp))
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = isSelected,
                        onClick = { if (!showFeedback) selectedOption = index },
                        colors = RadioButtonDefaults.colors(selectedColor = Color(0xFF667EEA))
                    )
                    Text(option, color = Color.White, modifier = Modifier.padding(start = 8.dp))
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                if (!showFeedback) {
                    if (selectedOption != null) {
                        if (selectedOption == question.correctIndex) score++
                        showFeedback = true
                    }
                } else {
                    if (currentQuestionIndex < questions.size - 1) {
                        currentQuestionIndex++
                        selectedOption = null
                        showFeedback = false
                    } else {
                        onFinished(score)
                    }
                }
            },
            enabled = selectedOption != null,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF667EEA))
        ) {
            Text(if (!showFeedback) "Prüfen" else if (currentQuestionIndex < questions.size - 1) "Nächste" else "Abschließen")
        }
    }
}

@Composable
fun QuizResult(score: Int, total: Int, onBack: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            Icons.Default.CheckCircle,
            contentDescription = null,
            tint = Color.Green,
            modifier = Modifier.size(80.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            "Ausgezeichnet!",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            "Du hast $score von $total Fragen richtig beantwortet.",
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Gray
        )
        Spacer(modifier = Modifier.height(32.dp))
        Button(
            onClick = onBack,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color.White),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Zurück zum Lernen", color = Color.Black, fontWeight = FontWeight.Bold)
        }
    }
}
