package com.sumino.xyz.ui.screen.termsofuse

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sumino.xyz.ui.theme.SuminoLabTheme

/**
 * Sumino Apps
 * @author Rohitraj Khorwal
 * Created 17-01-2026 at 4:47 PM
 */

@Preview(showBackground = true)
@Composable
fun TermsOfUseScreenPreview() {
    SuminoLabTheme() {
        TermsOfUseScreen(onNext = {}, onClose = {})
    }
}


@Composable
fun TermsOfUseScreen(
    onNext: () -> Unit,
    onClose: () -> Unit
) {

    val scrollState = rememberScrollState()
    var accepted by remember { mutableStateOf(false) }


    Scaffold(
        bottomBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 16.dp, top = 2.dp)
            ) {

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = accepted,
                        onCheckedChange = { accepted = it }
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "I accept the Terms of Use")
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {

                    OutlinedButton(
                        onClick = onClose,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Close")
                    }

                    Button(
                        onClick = onNext,
                        enabled = accepted,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Next")
                    }
                }
            }
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text("Terms of Use", fontSize = 26.sp, fontWeight = FontWeight.Bold)
                Text("Last updated on 1/12/2021")
            }
            HorizontalDivider()


            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(scrollState)
            ) {
                Clause("1. Clause 1")
                Clause("2. Clause 2")
                Clause("3. Clause 3")

                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }

}

@Composable
fun Clause(title: String) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = title,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. " +
                    "Viverra condimentum eget purus in. Consectetur eget id morbi amet amet, in. " +
                    "Ipsum viverra pretium tellus neque. Ullamcorper suspendisse aenean leo pharetra in sit semper et. " +
                    "Amet quam placerat sem.\n\n" +
                    "Lorem ipsum dolor sit amet, consectetur adipiscing elit. " +
                    "Viverra condimentum eget purus in. Consectetur eget id morbi amet amet, in.",
            color = Color.DarkGray,
            lineHeight = 20.sp
        )
    }
}




