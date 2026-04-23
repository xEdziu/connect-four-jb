package dev.adrian.goral.connect_four_jb.components.ui

import androidx.compose.runtime.Composable
import org.jetbrains.compose.web.attributes.*
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Input
import org.jetbrains.compose.web.dom.Label
import org.jetbrains.compose.web.dom.Text

@Composable
fun ConfigField(
    label: String,
    value: String,
    min: String,
    max: String,
    onValueChange: (String) -> Unit
) {
    Div(attrs = { classes("field") }) {
        Label(attrs = { classes("field-label") }) { Text(label) }
        Input(type = InputType.Number, attrs = {
            classes("field-input")
            value(value)
            this.min(min)
            this.max(max)
            onInput { event -> onValueChange(event.value?.toString().orEmpty()) }
        })
    }
}
