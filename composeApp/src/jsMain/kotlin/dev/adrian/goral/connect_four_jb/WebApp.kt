package dev.adrian.goral.connect_four_jb

import androidx.compose.runtime.*
import dev.adrian.goral.connect_four_jb.components.ui.BoardView
import dev.adrian.goral.connect_four_jb.components.ui.ConfigField
import dev.adrian.goral.connect_four_jb.components.ui.GameResultBanner
import dev.adrian.goral.connect_four_jb.components.ui.StatusBar
import dev.adrian.goral.connect_four_jb.domain.GameConfig
import dev.adrian.goral.connect_four_jb.presentation.GameViewModel
import dev.adrian.goral.connect_four_jb.presentation.PersistedGameSnapshot
import kotlinx.browser.window
import kotlinx.coroutines.delay
import kotlin.js.JSON
import org.jetbrains.compose.web.dom.Button
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.H1
import org.jetbrains.compose.web.dom.Text

@Composable
fun WebApp() {
    val viewModel = remember {
        GameViewModel(
            initialSnapshot = loadSnapshotFromStorage(),
            onPersistenceChanged = { snapshot ->
                if (snapshot == null) {
                    clearSnapshotInStorage()
                } else {
                    saveSnapshotToStorage(snapshot)
                }
            }
        )
    }
    val state by viewModel.state.collectAsState()
    val uiMessage by viewModel.uiMessage.collectAsState()

    LaunchedEffect(uiMessage) {
        if (uiMessage != null) {
            delay(1500)
            viewModel.clearUiMessage()
        }
    }

    var rowsInput by remember { mutableStateOf(state.config.rows.toString()) }
    var columnsInput by remember { mutableStateOf(state.config.columns.toString()) }
    var winConditionInput by remember { mutableStateOf(state.config.winCondition.toString()) }

    Div(attrs = { classes("page") }) {
        H1(attrs = { classes("title") }) { Text("Connect Four") }

        Div(attrs = { classes("panel") }) {
            ConfigField(
                label = "Rows",
                value = rowsInput,
                min = "4",
                max = "20",
                onValueChange = { rowsInput = it }
            )
            ConfigField(
                label = "Columns",
                value = columnsInput,
                min = "4",
                max = "20",
                onValueChange = { columnsInput = it }
            )
            ConfigField(
                label = "Win condition",
                value = winConditionInput,
                min = "3",
                max = "10",
                onValueChange = { winConditionInput = it }
            )

            Button(attrs = {
                classes("btn", "btn-primary")
                onClick {
                    val config = buildConfig(rowsInput, columnsInput, winConditionInput)
                    rowsInput = config.rows.toString()
                    columnsInput = config.columns.toString()
                    winConditionInput = config.winCondition.toString()
                    viewModel.startNewGame(config)
                }
            }) {
                Text("New game")
            }

            Button(attrs = {
                classes("btn")
                onClick { viewModel.resetGame() }
            }) {
                Text("Reset board")
            }
        }

        StatusBar(state = state, message = uiMessage)
        GameResultBanner(state = state, onPlayAgain = viewModel::resetGame)
        BoardView(state = state, onColumnClick = viewModel::onColumnClicked)
    }
}

private const val STORAGE_KEY = "connect_four.state.v1"

private fun loadSnapshotFromStorage(): PersistedGameSnapshot? {
    val raw = window.localStorage.getItem(STORAGE_KEY) ?: return null
    return try {
        val data = JSON.parse<dynamic>(raw)
        val schemaVersion = (data.schemaVersion as? Number)?.toInt() ?: return null
        if (schemaVersion != PersistedGameSnapshot.SCHEMA_VERSION) return null

        val configData = data.config ?: return null
        val rows = (configData.rows as? Number)?.toInt() ?: return null
        val columns = (configData.columns as? Number)?.toInt() ?: return null
        val winCondition = (configData.winCondition as? Number)?.toInt() ?: return null
        val config = GameConfig(rows = rows, columns = columns, winCondition = winCondition)

        val movesDynamic = data.moves as? Array<*> ?: return null
        val moves = movesDynamic.mapNotNull { (it as? Number)?.toInt() }
        if (moves.size != movesDynamic.size) return null

        PersistedGameSnapshot(
            schemaVersion = schemaVersion,
            config = config,
            moves = moves
        )
    } catch (_: Throwable) {
        null
    }.also { snapshot ->
        if (snapshot == null) {
            clearSnapshotInStorage()
        }
    }
}

private fun saveSnapshotToStorage(snapshot: PersistedGameSnapshot) {
    val payload = js("({})")
    payload.schemaVersion = snapshot.schemaVersion

    val configPayload = js("({})")
    configPayload.rows = snapshot.config.rows
    configPayload.columns = snapshot.config.columns
    configPayload.winCondition = snapshot.config.winCondition

    payload.config = configPayload
    payload.moves = snapshot.moves.toTypedArray()

    val encoded = JSON.stringify(payload)
    window.localStorage.setItem(STORAGE_KEY, encoded)
}

private fun clearSnapshotInStorage() {
    window.localStorage.removeItem(STORAGE_KEY)
}

private fun buildConfig(rowsInput: String, columnsInput: String, winConditionInput: String): GameConfig {
    val rows = rowsInput.toIntOrNull()?.coerceIn(4, 20) ?: 6
    val columns = columnsInput.toIntOrNull()?.coerceIn(4, 20) ?: 7
    val maxWin = minOf(10, rows, columns)
    val winCondition = winConditionInput.toIntOrNull()?.coerceIn(3, maxWin) ?: minOf(4, maxWin)

    return GameConfig(rows = rows, columns = columns, winCondition = winCondition)
}
