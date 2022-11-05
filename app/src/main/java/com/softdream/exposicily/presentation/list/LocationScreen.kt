package com.softdream.exposicily


import androidx.compose.foundation.Image
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.softdream.exposicily.data.remote.DtoLocation

import com.softdream.exposicily.presentation.list.LocationViewModel


@Composable
@Preview(showBackground = true)
fun LocationScreen() {
    var viewModel: LocationViewModel = viewModel()
    val locations = viewModel.state.value
    val isLoading = locations.isEmpty()
    LazyColumn(contentPadding = PaddingValues()) {
        items(locations) { location ->
            LocationItem(item = location)
        }
    }
    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
        if (isLoading) {
            CircularProgressIndicator()
        }
    }
}

@Composable

fun LocationItem(item: DtoLocation) {
    Card(
        elevation = CardDefaults.cardElevation(),
        modifier = Modifier.padding(dimensionResource(R.dimen.mediumPadding))
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
fun LocationDetails(title: String, message: String, weight: Modifier) {
    Column(modifier = weight) {
        Text(text = title, style = MaterialTheme.typography.titleMedium)
        Text(
            text = message,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.alpha(0.8f)
        )
    }
}

@Composable
fun LocationIcon(icon: ImageVector, weight: Modifier) {
    Image(
        imageVector = icon,
        contentDescription = stringResource(id = R.string.icon_expo_sicily_location),
        modifier = Modifier.padding(dimensionResource(id = R.dimen.mediumPadding))
    )
}
