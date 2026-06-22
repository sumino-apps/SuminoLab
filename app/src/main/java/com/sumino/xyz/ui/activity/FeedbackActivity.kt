package com.sumino.xyz.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.DefaultLifecycleObserver
import com.sumino.xyz.ui.components.AppToolbar
import com.sumino.xyz.ui.theme.SetStatusBarColor
import com.sumino.xyz.ui.theme.SuminoLabTheme

/**
 * Sumino Apps
 * @author Rohitraj Khorwal
 * Created 08-01-2026 at 8:31 PM
 */
class FeedbackActivity : ComponentActivity(), DefaultLifecycleObserver {

    override fun onCreate(savedInstanceState: Bundle?) {
        super<ComponentActivity>.onCreate(savedInstanceState)
        lifecycle.addObserver(this)


        val crashReport = intent.getStringExtra("ERROR_MESSAGE")

        onBackPressedDispatcher.addCallback(
            this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    finish()
                }
            }
        )
        setContent {
            SuminoLabTheme {
                SetStatusBarColor()
                FeedbackScreen(onBack = {
                    onBackPressedDispatcher.onBackPressed()
                }, onSubmit = { issue, comment, quickSupport ->

                }, crashReport ?: "")

            }
        }
    }


    override fun onDestroy() {
        lifecycle.removeObserver(this)
        super<ComponentActivity>.onDestroy()
    }


}


//@Preview(showBackground = true)
//@Preview(
//    uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true
//)
//@Composable
//fun FeedbackPreview() {
//    SuminoLabTheme {
//        FeedbackScreen(onBack = {
//
//
//        }, onSubmit = { issue, comment, quickSupport ->
//
//        },"")
//    }
//}

@Composable
fun FeedbackScreen(
    onBack: () -> Unit,
    onSubmit: (
        issue: List<FeedbackIssue>,
        comment: String,
        quickSupport: Boolean
    ) -> Unit,
    string: String
) {
    val selectedIssues = remember { mutableStateListOf<FeedbackIssue>() }
    var comment by remember { mutableStateOf(string) }
    var quickSupport by remember { mutableStateOf(false) }

    val isSubmitEnabled = selectedIssues.isNotEmpty() && comment.length >= 10

    Scaffold(
        topBar = {
            AppToolbar(title = { Text("") }, navigation = {
                IconButton(onClick = {
                    onBack.invoke()
                }) {
                    Icon(Icons.Default.ArrowBack, null)
                }
            })
        }, bottomBar = {
            Button(
                onClick = {
                    onSubmit(
                        selectedIssues.toList(),
                        comment,
                        quickSupport
                    )
                },
                enabled = isSubmitEnabled,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 16.dp
                    )
                    .padding(bottom = 10.dp)
                    .height(50.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Submit Feedback")
            }
        }) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .padding(horizontal = 16.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .imePadding()
        ) {

            // Title
            Text(
                text = "Share your\nfeedback",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold
            )

            Spacer(Modifier.height(6.dp))

            Text(
                text = "Your feedback helps us improve the app!",
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(Modifier.height(2.dp))

            Text(
                text = "Please select the issues you've experienced:",
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(Modifier.height(10.dp))

            // Radio List
            FeedbackIssue.entries.forEach { issue ->
                FeedbackCheckboxItem(
                    title = issue.title,
                    checked = issue in selectedIssues,
                    onCheckedChange = { checked ->
                        if (checked) {
                            selectedIssues.add(issue)
                        } else {
                            selectedIssues.remove(issue)
                        }
                    }
                )
            }

            Spacer(Modifier.height(10.dp))

            // Comment + Quick Support
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Your Comment",
                    style = MaterialTheme.typography.labelLarge,
                    modifier = Modifier.weight(1f)
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = quickSupport,
                        onCheckedChange = { quickSupport = it },
                        modifier = Modifier.scale(0.8f)
                    )
                    Text(
                        text = "Need Quick Support",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }

            Spacer(Modifier.height(8.dp))

            OutlinedTextField(
                value = comment,
                onValueChange = { comment = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp),
                placeholder = {
                    Text("Describe your experience here")
                },
                shape = RoundedCornerShape(16.dp)
            )

            Spacer(Modifier.height(50.dp))
        }
    }
}


@Composable
fun FeedbackCheckboxItem(
    title: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onCheckedChange(!checked) },
        verticalAlignment = Alignment.CenterVertically
    ) {

        Checkbox(
            checked = checked,
            onCheckedChange = onCheckedChange,
            modifier = Modifier.scale(0.8f)
        )

        Spacer(Modifier.width(12.dp))

        Text(
            text = title,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}


enum class FeedbackIssue(val title: String) {
    CRASH("App Crashes & Freezing"),
    DOWNLOAD("Poor Download Quality"),
    LOGIN("Login issue / Blocked"),
    PERFORMANCE("Slow Performance"),
    OTHER("Other")
}


