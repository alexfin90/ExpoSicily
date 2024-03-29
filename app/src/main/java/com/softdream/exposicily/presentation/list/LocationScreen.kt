package com.softdream.exposicily


import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.ViewModel
import com.softdream.exposicily.domain.Location
import com.softdream.exposicily.presentation.detail.LocationDetailViewModel
import com.softdream.exposicily.presentation.list.LocationScreenState
import com.softdream.exposicily.presentation.list.LocationViewModel


@Composable
fun LocationScreen(
    state: LocationScreenState,
    viewModel: ViewModel,
    onItemClick: (id: Int) -> Unit = {}
) {

    when {
        state.locations.isNotEmpty() -> {
            LazyColumn(contentPadding = PaddingValues()) {
                items(state.locations) { location ->
                    LocationItem(item = location, onItemClick)
                }
            }
        }
        state.isLoading -> Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) { CircularProgressIndicator() }
        state.error.isNotBlank() -> Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            ErrorButton(errorText = state.error, viewModel)
        }
    }

}

@Composable
fun ErrorButton(errorText: String, viewModel: ViewModel) {
    Column(
        modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Button(
            onClick = {
                when (viewModel) {
                    is LocationViewModel -> viewModel.retryGetLocations()
                    is LocationDetailViewModel -> viewModel.retryGetLocation()
                }
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.errorContainer,
                contentColor = MaterialTheme.colorScheme.onErrorContainer
            ),
            shape = MaterialTheme.shapes.small
        ) {
            Text(
                text = errorText,
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun LocationItem(item: Location, onItemClick: (id: Int) -> Unit) {
    Card(
        elevation = CardDefaults.cardElevation(),
        modifier = Modifier
            .padding(dimensionResource(R.dimen.mediumPadding))
            .clickable { onItemClick(item.id) }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(dimensionResource(R.dimen.mediumPadding))
        ) {
            LocationIcon(Icons.Filled.Place, Modifier.weight(0.15f))
            LocationDetails(item.property.site, item.property.location, Modifier.weight(0.85f))
        }
    }
}

@Composable
fun LocationDetails(
    title: String,
    message: String,
    modifier: Modifier,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start
) {
    Column(modifier = modifier, horizontalAlignment = horizontalAlignment) {
        Text(text = title, style = MaterialTheme.typography.titleMedium)
        Text(
            text = message,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .alpha(0.8f)
                .padding(top = dimensionResource(id = R.dimen.mediumPadding))
        )
    }
}

@Composable
fun LocationIcon(icon: ImageVector, modifier: Modifier) {
    Image(
        imageVector = icon,
        contentDescription = stringResource(id = R.string.icon_expo_sicily_location),
        modifier = Modifier.padding(dimensionResource(id = R.dimen.mediumPadding))
    )
}

