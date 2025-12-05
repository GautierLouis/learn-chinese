package com.louisgautier.composeApp.dictionary

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.louisgautier.apicontracts.dto.SimpleDictionary
import com.louisgautier.composeApp.design.page.ErrorFullPage
import com.louisgautier.composeApp.design.previewSimpleDataList
import com.louisgautier.composeApp.design.previewSimpleDictionary
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.ui.tooling.preview.PreviewParameter
import org.jetbrains.compose.ui.tooling.preview.PreviewParameterProvider

@Composable
fun DictionaryPage(
    items: LazyPagingItems<SimpleDictionary>,
    modifier: Modifier = Modifier,
    onItemClick: (Int?) -> Unit = {}
) {
    Box(modifier = modifier.clipToBounds()) {
        when {
            items.loadState.refresh is LoadState.Error && items.itemCount == 0 -> {
                ErrorFullPage(action = { items.retry() })
            }
            // Initial loading
            items.loadState.refresh is LoadState.Loading && items.itemCount == 0 -> {
                LoadingPageContent()
            }

            else -> DictionaryPageContent(items, onItemClick = onItemClick)
        }
    }
}

@Preview
@Composable
fun DictionaryPagePreview(
    @PreviewParameter(DictionaryPageProvider::class) pagingData: PagingData<SimpleDictionary>
) {
    DictionaryPage(flowOf(pagingData).collectAsLazyPagingItems())
}

class DictionaryPageProvider() : PreviewParameterProvider<PagingData<SimpleDictionary>> {
    private fun createLoadStates(state: LoadState) = LoadStates(
        refresh = state,
        prepend = state,
        append = state,
    )

    //TODO : Empty, Load more
    override val values: Sequence<PagingData<SimpleDictionary>>
        get() = sequenceOf(
            PagingData.from(emptyList(), createLoadStates(LoadState.Error(Exception()))),
            PagingData.from(emptyList(), createLoadStates(LoadState.Loading)),
            PagingData.from(previewSimpleDataList, createLoadStates(LoadState.Loading)),
        )
}
