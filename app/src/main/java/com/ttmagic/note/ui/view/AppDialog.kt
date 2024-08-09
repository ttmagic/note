package com.ttmagic.note.ui.view

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ttmagic.note.R
import com.ttmagic.note.ui.theme.NoteTheme
import com.ttmagic.note.ui.theme.Typography

@Composable
fun AppDialog(
    onDismiss: () -> Unit,
    @StringRes title: Int,
    @StringRes confirmBtn: Int = R.string.ok,
    @StringRes dismissBtn: Int = R.string.cancel,
    onClickConfirm: () -> Unit,
) {
    AlertDialog(onDismissRequest = {
        onDismiss()
    }, title = {
        Text(text = stringResource(id = title), style = Typography.titleLarge)
    }, confirmButton = {
        Text(
            text = stringResource(id = confirmBtn),
            modifier = Modifier.clickable {
                onClickConfirm()
                onDismiss()
            }
        )
    }, dismissButton = {
        Text(
            text = stringResource(id = dismissBtn),
            modifier = Modifier
                .padding(end = 20.dp)
                .clickable {
                    onDismiss()
                }
        )
    })
}


@Preview
@Composable
fun PreviewAppDialog() {
    NoteTheme {
        AppDialog(onDismiss = { }, title = R.string.app_name) {

        }
    }
}